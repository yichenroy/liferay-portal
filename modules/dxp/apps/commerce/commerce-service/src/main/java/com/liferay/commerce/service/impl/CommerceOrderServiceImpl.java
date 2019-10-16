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

package com.liferay.commerce.service.impl;

import com.liferay.commerce.constants.CommerceActionKeys;
import com.liferay.commerce.model.CommerceCart;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.service.base.CommerceOrderServiceBaseImpl;
import com.liferay.commerce.service.permission.CommercePermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.ServiceContext;

/**
 * @author Andrea Di Giorgi
 */
public class CommerceOrderServiceImpl extends CommerceOrderServiceBaseImpl {

	@Override
	public CommerceOrder addCommerceOrderFromCart(
			long commerceCartId, ServiceContext serviceContext)
		throws PortalException {

		CommerceCart commerceCart = commerceCartLocalService.getCommerceCart(
			commerceCartId);

		if (commerceCart.getUserId() != serviceContext.getUserId()) {
			throw new PrincipalException();
		}

		return commerceOrderLocalService.addCommerceOrderFromCart(
			commerceCart.getCommerceCartId(), serviceContext);
	}

	@Override
	public CommerceOrder getCommerceOrder(long commerceOrderId)
		throws PortalException {

		CommerceOrder commerceOrder = commerceOrderPersistence.findByPrimaryKey(
			commerceOrderId);

		PermissionChecker permissionChecker = getPermissionChecker();

		if ((commerceOrder.getUserId() != permissionChecker.getUserId()) &&
			(commerceOrder.getOrderUserId() != permissionChecker.getUserId()) &&
			!CommercePermission.contains(
				permissionChecker, commerceOrder.getGroupId(),
				CommerceActionKeys.MANAGE_COMMERCE_ORDERS)) {

			throw new PrincipalException();
		}

		return commerceOrder;
	}

}