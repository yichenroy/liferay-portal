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

import com.liferay.commerce.model.CommerceCartItem;
import com.liferay.commerce.product.exception.NoSuchCPInstanceException;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.commerce.service.base.CommerceCartItemLocalServiceBaseImpl;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.util.List;

/**
 * @author Marco Leo
 */
public class CommerceCartItemLocalServiceImpl
	extends CommerceCartItemLocalServiceBaseImpl {

	@Override
	public CommerceCartItem addCommerceCartItem(
			long commerceCartId, long cpDefinitionId, long cpInstanceId,
			int quantity, String json, ServiceContext serviceContext)
		throws PortalException {

		validate(cpDefinitionId, cpInstanceId);

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		long commerceCartItemId = counterLocalService.increment();

		CommerceCartItem commerceCartItem = commerceCartItemPersistence.create(
			commerceCartItemId);

		commerceCartItem.setGroupId(groupId);
		commerceCartItem.setCompanyId(user.getCompanyId());
		commerceCartItem.setUserId(user.getUserId());
		commerceCartItem.setUserName(user.getFullName());
		commerceCartItem.setCommerceCartId(commerceCartId);
		commerceCartItem.setCPDefinitionId(cpDefinitionId);
		commerceCartItem.setCPInstanceId(cpInstanceId);
		commerceCartItem.setQuantity(quantity);
		commerceCartItem.setJson(json);
		commerceCartItem.setExpandoBridgeAttributes(serviceContext);

		commerceCartItemPersistence.update(commerceCartItem);

		return commerceCartItem;
	}

	@Override
	public CommerceCartItem deleteCommerceCartItem(
			CommerceCartItem commerceCartItem)
		throws PortalException {

		// Commerce cart item

		commerceCartItemPersistence.remove(commerceCartItem);

		// Expando

		expandoRowLocalService.deleteRows(
			commerceCartItem.getCommerceCartItemId());

		return commerceCartItem;
	}

	@Override
	public CommerceCartItem deleteCommerceCartItem(long commerceCartItemId)
		throws PortalException {

		CommerceCartItem commerceCartItem =
			commerceCartItemPersistence.findByPrimaryKey(commerceCartItemId);

		return commerceCartItemLocalService.deleteCommerceCartItem(
			commerceCartItem);
	}

	@Override
	public void deleteCommerceCartItems(long commerceCartId)
		throws PortalException {

		List<CommerceCartItem> commerceCartItems =
			commerceCartItemPersistence.findByCommerceCartId(
				commerceCartId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (CommerceCartItem commerceCartItem : commerceCartItems) {
			deleteCommerceCartItem(commerceCartItem);
		}
	}

	@Override
	public void deleteCommerceCartItemsByCPDefinitionId(long cpDefinitionId)
		throws PortalException {

		List<CommerceCartItem> commerceCartItems =
			commerceCartItemPersistence.findByCPDefinitionId(cpDefinitionId);

		for (CommerceCartItem commerceCartItem : commerceCartItems) {
			deleteCommerceCartItem(commerceCartItem);
		}
	}

	@Override
	public void deleteCommerceCartItemsByCPInstanceId(long cpInstanceId)
		throws PortalException {

		List<CommerceCartItem> commerceCartItems =
			commerceCartItemPersistence.findByCPInstanceId(cpInstanceId);

		for (CommerceCartItem commerceCartItem : commerceCartItems) {
			deleteCommerceCartItem(commerceCartItem);
		}
	}

	@Override
	public List<CommerceCartItem> getCommerceCartItems(
		long commerceCartId, int start, int end) {

		return commerceCartItemPersistence.findByCommerceCartId(
			commerceCartId, start, end);
	}

	@Override
	public List<CommerceCartItem> getCommerceCartItems(
		long commerceCartId, int start, int end,
		OrderByComparator<CommerceCartItem> orderByComparator) {

		return commerceCartItemPersistence.findByCommerceCartId(
			commerceCartId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceCartItemsCount(long commerceCartId) {
		return commerceCartItemPersistence.countByCommerceCartId(
			commerceCartId);
	}

	@Override
	public CommerceCartItem updateCommerceCartItem(
			long commerceCartItemId, int quantity, String json)
		throws PortalException {

		CommerceCartItem commerceCartItem =
			commerceCartItemPersistence.findByPrimaryKey(commerceCartItemId);

		commerceCartItem.setQuantity(quantity);
		commerceCartItem.setJson(json);

		commerceCartItemPersistence.update(commerceCartItem);

		return commerceCartItem;
	}

	public void validate(long cpDefinitionId, long cpInstanceId)
		throws PortalException {

		CPDefinition cpDefinition = _cpDefinitionLocalService.getCPDefinition(
			cpDefinitionId);

		if (cpInstanceId > 0) {
			CPInstance cpInstance = _cpInstanceLocalService.getCPInstance(
				cpInstanceId);

			if (cpInstance.getCPDefinitionId() !=
					cpDefinition.getCPDefinitionId()) {

				throw new NoSuchCPInstanceException(
					"CPInstance " + cpInstance.getCPInstanceId() +
						" belongs to a different CPDefinition than " +
							cpDefinition.getCPDefinitionId());
			}
		}
	}

	@ServiceReference(type = CPDefinitionLocalService.class)
	private CPDefinitionLocalService _cpDefinitionLocalService;

	@ServiceReference(type = CPInstanceLocalService.class)
	private CPInstanceLocalService _cpInstanceLocalService;

}