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

package com.liferay.commerce.cart.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceCartService}.
 *
 * @author Marco Leo
 * @see CommerceCartService
 * @generated
 */
@ProviderType
public class CommerceCartServiceWrapper implements CommerceCartService,
	ServiceWrapper<CommerceCartService> {
	public CommerceCartServiceWrapper(CommerceCartService commerceCartService) {
		_commerceCartService = commerceCartService;
	}

	@Override
	public com.liferay.commerce.cart.model.CommerceCart addCommerceCart(
		java.lang.String name, int type,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceCartService.addCommerceCart(name, type, serviceContext);
	}

	@Override
	public com.liferay.commerce.cart.model.CommerceCart fetchCommerceCart(
		long commerceCartId) {
		return _commerceCartService.fetchCommerceCart(commerceCartId);
	}

	@Override
	public com.liferay.commerce.cart.model.CommerceCart fetchCommerceCart(
		long groupId, java.lang.String uuid) {
		return _commerceCartService.fetchCommerceCart(groupId, uuid);
	}

	@Override
	public com.liferay.commerce.cart.model.CommerceCart fetchDefaultCommerceCart(
		long groupId, long userId, int type, java.lang.String name) {
		return _commerceCartService.fetchDefaultCommerceCart(groupId, userId,
			type, name);
	}

	@Override
	public com.liferay.commerce.cart.model.CommerceCart getCommerceCart(
		long commerceCartId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceCartService.getCommerceCart(commerceCartId);
	}

	@Override
	public int getCommerceCartsCount(long groupId, int type) {
		return _commerceCartService.getCommerceCartsCount(groupId, type);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _commerceCartService.getOSGiServiceIdentifier();
	}

	@Override
	public java.util.List<com.liferay.commerce.cart.model.CommerceCart> getCommerceCarts(
		long groupId, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.cart.model.CommerceCart> orderByComparator) {
		return _commerceCartService.getCommerceCarts(groupId, type, start, end,
			orderByComparator);
	}

	@Override
	public void deleteCommerceCart(long commerceCartId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_commerceCartService.deleteCommerceCart(commerceCartId);
	}

	@Override
	public CommerceCartService getWrappedService() {
		return _commerceCartService;
	}

	@Override
	public void setWrappedService(CommerceCartService commerceCartService) {
		_commerceCartService = commerceCartService;
	}

	private CommerceCartService _commerceCartService;
}