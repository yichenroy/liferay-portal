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
import com.liferay.commerce.constants.CommerceConstants;
import com.liferay.commerce.model.CommerceShipmentItem;
import com.liferay.commerce.service.base.CommerceShipmentItemServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermissionFactory;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceShipmentItemServiceImpl
	extends CommerceShipmentItemServiceBaseImpl {

	@Override
	public CommerceShipmentItem addCommerceShipmentItem(
			long commerceShipmentId, long commerceOrderItemId,
			long commerceWarehouseId, int quantity,
			ServiceContext serviceContext)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			CommerceActionKeys.MANAGE_COMMERCE_SHIPMENTS);

		return commerceShipmentItemLocalService.addCommerceShipmentItem(
			commerceShipmentId, commerceOrderItemId, commerceWarehouseId,
			quantity, serviceContext);
	}

	@Override
	public void deleteCommerceShipmentItem(long commerceShipmentItemId)
		throws PortalException {

		CommerceShipmentItem commerceShipmentItem =
			commerceShipmentItemPersistence.findByPrimaryKey(
				commerceShipmentItemId);

		_portletResourcePermission.check(
			getPermissionChecker(), commerceShipmentItem.getGroupId(),
			CommerceActionKeys.MANAGE_COMMERCE_SHIPMENTS);

		commerceShipmentItemLocalService.deleteCommerceShipmentItem(
			commerceShipmentItem);
	}

	@Override
	public CommerceShipmentItem fetchCommerceShipmentItem(
		long commerceShipmentItemId) {

		return commerceShipmentItemLocalService.fetchCommerceShipmentItem(
			commerceShipmentItemId);
	}

	@Override
	public List<CommerceShipmentItem> getCommerceShipmentItems(
		long commerceOrderItemId) {

		return commerceShipmentItemLocalService.getCommerceShipmentItems(
			commerceOrderItemId);
	}

	@Override
	public List<CommerceShipmentItem> getCommerceShipmentItems(
		long commerceShipmentId, int start, int end,
		OrderByComparator<CommerceShipmentItem> orderByComparator) {

		return commerceShipmentItemLocalService.getCommerceShipmentItems(
			commerceShipmentId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceShipmentItemsCount(long commerceShipmentId) {
		return commerceShipmentItemLocalService.getCommerceShipmentItemsCount(
			commerceShipmentId);
	}

	@Override
	public CommerceShipmentItem updateCommerceShipmentItem(
			long commerceShipmentItemId, int quantity)
		throws PortalException {

		CommerceShipmentItem commerceShipmentItem =
			commerceShipmentItemPersistence.findByPrimaryKey(
				commerceShipmentItemId);

		_portletResourcePermission.check(
			getPermissionChecker(), commerceShipmentItem.getGroupId(),
			CommerceActionKeys.MANAGE_COMMERCE_SHIPMENTS);

		return commerceShipmentItemLocalService.updateCommerceShipmentItem(
			commerceShipmentItemId, quantity);
	}

	private static volatile PortletResourcePermission
		_portletResourcePermission =
			PortletResourcePermissionFactory.getInstance(
				CommerceShipmentItemServiceImpl.class,
				"_portletResourcePermission", CommerceConstants.RESOURCE_NAME);

}