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

package com.liferay.commerce.product.service.impl;

import com.liferay.commerce.product.constants.CPActionKeys;
import com.liferay.commerce.product.model.CPOptionValue;
import com.liferay.commerce.product.service.base.CPOptionValueServiceBaseImpl;
import com.liferay.commerce.product.service.permission.CPOptionPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Marco Leo
 */
public class CPOptionValueServiceImpl extends CPOptionValueServiceBaseImpl {

	@Override
	public CPOptionValue addCPOptionValue(
			long cpOptionId, Map<Locale, String> titleMap, int priority,
			ServiceContext serviceContext)
		throws PortalException {

		CPOptionPermission.check(
			getPermissionChecker(), cpOptionId,
			CPActionKeys.ADD_COMMERCE_PRODUCT_OPTION_VALUE);

		return cpOptionValueLocalService.
			addCPOptionValue(cpOptionId, titleMap, priority, serviceContext);
	}

	@Override
	public CPOptionValue deleteCPOptionValue(CPOptionValue cpOptionValue)
		throws PortalException {

		CPOptionPermission.checkCPOptionValue(
			getPermissionChecker(), cpOptionValue.getCPOptionValueId(),
			CPActionKeys.DELETE_COMMERCE_PRODUCT_OPTION_VALUE);

		return cpOptionValueLocalService.deleteCPOptionValue(cpOptionValue);
	}

	@Override
	public CPOptionValue deleteCPOptionValue(long cpOptionValueId)
		throws PortalException {

		CPOptionPermission.checkCPOptionValue(
			getPermissionChecker(), cpOptionValueId,
			CPActionKeys.DELETE_COMMERCE_PRODUCT_OPTION_VALUE);

		return cpOptionValueLocalService.deleteCPOptionValue(cpOptionValueId);
	}

	@Override
	public CPOptionValue fetchCPOptionValue(long cpOptionValueId)
		throws PortalException {

		CPOptionValue cpOptionValue =
			cpOptionValueLocalService.fetchCPOptionValue(cpOptionValueId);

		if (cpOptionValue != null) {
			CPOptionPermission.checkCPOptionValue(
				getPermissionChecker(), cpOptionValue, ActionKeys.VIEW);
		}

		return cpOptionValue;
	}

	@Override
	public CPOptionValue getCPOptionValue(long cpOptionValueId)
		throws PortalException {

		CPOptionPermission.checkCPOptionValue(
			getPermissionChecker(), cpOptionValueId, ActionKeys.VIEW);

		return cpOptionValueLocalService.getCPOptionValue(cpOptionValueId);
	}

	@Override
	public List<CPOptionValue> getCPOptionValues(
			long cpOptionId, int start, int end)
		throws PortalException {

		CPOptionPermission.check(
			getPermissionChecker(), cpOptionId, ActionKeys.VIEW);
		return cpOptionValueLocalService.
			getCPOptionValues(cpOptionId, start, end);
	}

	@Override
	public List<CPOptionValue> getCPOptionValues(
			long cpOptionId, int start, int end,
			OrderByComparator<CPOptionValue> orderByComparator)
		throws PortalException {

		CPOptionPermission.check(
			getPermissionChecker(), cpOptionId, ActionKeys.VIEW);
		return cpOptionValueLocalService.
			getCPOptionValues(cpOptionId, start, end, orderByComparator);
	}

	@Override
	public int getCPOptionValuesCount(long cpOptionId) throws PortalException {
		CPOptionPermission.check(
			getPermissionChecker(), cpOptionId, ActionKeys.VIEW);

		return cpOptionValueLocalService.getCPOptionValuesCount(cpOptionId);
	}

	@Override
	public CPOptionValue updateCPOptionValue(
			long cpOptionValueId, Map<Locale, String> titleMap, int priority,
			ServiceContext serviceContext)
		throws PortalException {

		CPOptionPermission.checkCPOptionValue(
			getPermissionChecker(), cpOptionValueId,
			CPActionKeys.UPDATE_COMMERCE_PRODUCT_OPTION_VALUE);

		return cpOptionValueLocalService.
			updateCPOptionValue(
				cpOptionValueId, titleMap, priority, serviceContext);
	}

}