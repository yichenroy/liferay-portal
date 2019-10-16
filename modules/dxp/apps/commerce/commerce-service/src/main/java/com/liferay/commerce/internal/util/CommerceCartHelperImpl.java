/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.commerce.internal.util;

import com.liferay.commerce.constants.CommerceConstants;
import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.model.CommerceCart;
import com.liferay.commerce.service.CommerceCartItemService;
import com.liferay.commerce.service.CommerceCartService;
import com.liferay.commerce.util.CommerceCartHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.util.CookieKeys;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(immediate = true)
public class CommerceCartHelperImpl implements CommerceCartHelper {

	@Override
	public CommerceCart getCurrentCommerceCart(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws PortalException {

		int type = ParamUtil.getInteger(
			httpServletRequest, "type",
			CommerceConstants.COMMERCE_CART_TYPE_CART);

		return getCurrentCommerceCart(
			httpServletRequest, httpServletResponse, type);
	}

	@Override
	public CommerceCart getCurrentCommerceCart(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, int type)
		throws PortalException {

		long groupId = _portal.getScopeGroupId(httpServletRequest);

		String uuid = _getCurrentCommerceCartUuid(
			httpServletRequest, type, groupId);

		if (Validator.isNull(uuid)) {
			return null;
		}

		CommerceCart commerceCart = _commerceCartService.fetchCommerceCart(
			uuid, groupId);

		commerceCart = _checkGuestCart(
			httpServletRequest, httpServletResponse, commerceCart);

		return commerceCart;
	}

	@Override
	public int getCurrentCommerceCartItemsCount(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, int type)
		throws PortalException {

		CommerceCart commerceCart = getCurrentCommerceCart(
			httpServletRequest, httpServletResponse, type);

		if (commerceCart == null) {
			return 0;
		}

		return _commerceCartItemService.getCommerceCartItemsCount(
			commerceCart.getCommerceCartId());
	}

	@Override
	public void setGuestCommerceCart(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, CommerceCart commerceCart)
		throws PortalException {

		User user = _portal.getUser(httpServletRequest);

		if ((user != null) && !user.isDefaultUser()) {
			return;
		}

		long groupId = _portal.getScopeGroupId(httpServletRequest);

		String commerceCartUuidWebKey = _getCommerceCartUuidWebKey(
			commerceCart.getType(), groupId);

		Cookie cookie = new Cookie(
			commerceCartUuidWebKey, commerceCart.getUuid());

		String domain = CookieKeys.getDomain(httpServletRequest);

		if (Validator.isNotNull(domain)) {
			cookie.setDomain(domain);
		}

		cookie.setMaxAge(CookieKeys.MAX_AGE);
		cookie.setPath(StringPool.SLASH);

		CookieKeys.addCookie(httpServletRequest, httpServletResponse, cookie);
	}

	private CommerceCart _checkGuestCart(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, CommerceCart commerceCart)
		throws PortalException {

		if (commerceCart == null) {
			return null;
		}

		User user = _portal.getUser(httpServletRequest);

		long groupId = _portal.getScopeGroupId(httpServletRequest);

		if ((user == null) || user.isDefaultUser()) {
			return commerceCart;
		}

		if (commerceCart.isGuestCart()) {
			return _commerceCartService.updateUser(
				commerceCart.getCommerceCartId(), user.getUserId());
		}
		else {
			String commerceCartUuidWebKey = _getCommerceCartUuidWebKey(
				commerceCart.getType(), groupId);

			String commerceCartUuid = CookieKeys.getCookie(
				httpServletRequest, commerceCartUuidWebKey, false);

			if (Validator.isNull(commerceCartUuid)) {
				return commerceCart;
			}

			CommerceCart guestCommerceCart =
				_commerceCartService.fetchCommerceCart(
					commerceCartUuid, groupId);

			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				httpServletRequest);

			_commerceCartService.mergeGuestCommerceCart(
				guestCommerceCart.getCommerceCartId(),
				commerceCart.getCommerceCartId(), serviceContext);

			String domain = CookieKeys.getDomain(httpServletRequest);

			CookieKeys.deleteCookies(
				httpServletRequest, httpServletResponse, domain,
				commerceCartUuidWebKey);

			return commerceCart;
		}
	}

	private String _getCommerceCartUuidWebKey(int type, long groupId) {
		return CommerceCartWebKeys.COMMERCE_CART_UUID + StringPool.UNDERLINE +
			type + StringPool.UNDERLINE + groupId;
	}

	private String _getCurrentCommerceCartUuid(
			HttpServletRequest httpServletRequest, int type, long groupId)
		throws PortalException {

		String commerceCartUuid = CommerceCartThreadLocal.getCommerceCartUuid(
			type);

		if (Validator.isNotNull(commerceCartUuid)) {
			return commerceCartUuid;
		}

		String commerceCartUuidWebKey = _getCommerceCartUuidWebKey(
			type, groupId);

		User user = _portal.getUser(httpServletRequest);

		if ((user != null) && !user.isDefaultUser()) {
			CommerceCart commerceCart =
				_commerceCartService.fetchDefaultCommerceCart(
					groupId, user.getUserId(), type,
					CommerceCartConstants.COMMERCE_CART_DEFAULT_TITLE);

			if (commerceCart != null) {
				CommerceCartThreadLocal.setCommerceCartUuid(
					type, commerceCart.getUuid());

				return commerceCart.getUuid();
			}
		}

		commerceCartUuid = CookieKeys.getCookie(
			httpServletRequest, commerceCartUuidWebKey, false);

		if (Validator.isNotNull(commerceCartUuid)) {
			CommerceCartThreadLocal.setCommerceCartUuid(type, commerceCartUuid);

			return commerceCartUuid;
		}

		return null;
	}

	@Reference
	private CommerceCartItemService _commerceCartItemService;

	@Reference
	private CommerceCartService _commerceCartService;

	@Reference
	private Portal _portal;

}
