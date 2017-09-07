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

import com.liferay.commerce.model.CommerceCart;
import com.liferay.commerce.model.CommerceCartItem;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.service.base.CommerceOrderLocalServiceBaseImpl;
import com.liferay.commerce.util.CommercePriceCalculationHelper;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.util.List;

/**
 * @author Andrea Di Giorgi
 * @author Alessio Antonio Rendina
 */
public class CommerceOrderLocalServiceImpl
	extends CommerceOrderLocalServiceBaseImpl {

	@Override
	public CommerceOrder addCommerceOrder(
			long orderUserId, double total, int status,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		long commerceOrderId = counterLocalService.increment();

		CommerceOrder commerceOrder = commerceOrderPersistence.create(
			commerceOrderId);

		commerceOrder.setGroupId(groupId);
		commerceOrder.setCompanyId(user.getCompanyId());
		commerceOrder.setUserId(user.getUserId());
		commerceOrder.setUserName(user.getFullName());
		commerceOrder.setOrderUserId(orderUserId);
		commerceOrder.setTotal(total);
		commerceOrder.setStatus(status);
		commerceOrder.setExpandoBridgeAttributes(serviceContext);

		commerceOrderPersistence.update(commerceOrder);

		return commerceOrder;
	}

	@Override
	public CommerceOrder addCommerceOrderFromCart(
			long commerceCartId, ServiceContext serviceContext)
		throws PortalException {

		// Commerce order

		CommerceCart commerceCart = commerceCartLocalService.getCommerceCart(
			commerceCartId);

		CommerceOrder commerceOrder =
			commerceOrderLocalService.addCommerceOrder(
				commerceCart.getUserId(),
				_commercePriceCalculationHelper.getTotal(commerceCart),
				WorkflowConstants.STATUS_APPROVED, serviceContext);

		// Commerce order items

		List<CommerceCartItem> commerceCartItems =
			commerceCartItemLocalService.getCommerceCartItems(
				commerceCart.getCommerceCartId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		for (CommerceCartItem commerceCartItem : commerceCartItems) {
			double price = _commercePriceCalculationHelper.getPrice(
				commerceCartItem.getCPInstanceId(),
				commerceCartItem.getQuantity());

			commerceOrderItemLocalService.addCommerceOrderItem(
				commerceOrder.getCommerceOrderId(),
				commerceCartItem.getCPDefinitionId(),
				commerceCartItem.getCPInstanceId(),
				commerceCartItem.getQuantity(), commerceCartItem.getJson(),
				price, serviceContext);
		}

		// Commerce cart

		commerceCartLocalService.deleteCommerceCart(commerceCart);

		return commerceOrder;
	}

	@Override
	public CommerceOrder deleteCommerceOrder(CommerceOrder commerceOrder)
		throws PortalException {

		// Commerce order

		commerceOrderPersistence.remove(commerceOrder);

		// Commerce order items

		commerceOrderItemLocalService.deleteCommerceOrderItems(
			commerceOrder.getCommerceOrderId());

		// Expando

		expandoRowLocalService.deleteRows(commerceOrder.getCommerceOrderId());

		return commerceOrder;
	}

	@Override
	public CommerceOrder deleteCommerceOrder(long commerceOrderId)
		throws PortalException {

		CommerceOrder commerceOrder = commerceOrderPersistence.findByPrimaryKey(
			commerceOrderId);

		return commerceOrderLocalService.deleteCommerceOrder(commerceOrder);
	}

	@Override
	public List<CommerceOrder> getCommerceOrders(
		long groupId, int start, int end) {

		return commerceOrderLocalService.getCommerceOrders(
			groupId, start, end, null);
	}

	@Override
	public List<CommerceOrder> getCommerceOrders(
		long groupId, int start, int end,
		OrderByComparator<CommerceOrder> orderByComparator) {

		return commerceOrderPersistence.findByGroupId(
			groupId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceOrdersCount(long groupId) {
		return commerceOrderPersistence.countByGroupId(groupId);
	}

	@ServiceReference(type = CommercePriceCalculationHelper.class)
	private CommercePriceCalculationHelper _commercePriceCalculationHelper;

}