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
import com.liferay.commerce.product.constants.CPConstants;
import com.liferay.commerce.product.constants.CPOptionCategoryConstants;
import com.liferay.commerce.product.model.CPOptionCategory;
import com.liferay.commerce.product.model.CPSpecificationOption;
import com.liferay.commerce.product.service.base.CPSpecificationOptionServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermissionFactory;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Andrea Di Giorgi
 */
public class CPSpecificationOptionServiceImpl
	extends CPSpecificationOptionServiceBaseImpl {

	@Override
	public CPSpecificationOption addCPSpecificationOption(
			long cpOptionCategoryId, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, boolean facetable, String key,
			ServiceContext serviceContext)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			CPActionKeys.ADD_COMMERCE_PRODUCT_SPECIFICATION_OPTION);

		if (cpOptionCategoryId !=
				CPOptionCategoryConstants.DEFAULT_CP_OPTION_CATEGORY_ID) {

			_cpOptionCategoryModelResourcePermission.check(
				getPermissionChecker(), cpOptionCategoryId, ActionKeys.VIEW);
		}

		return cpSpecificationOptionLocalService.addCPSpecificationOption(
			cpOptionCategoryId, titleMap, descriptionMap, facetable, key,
			serviceContext);
	}

	@Override
	public void deleteCPSpecificationOption(long cpSpecificationOptionId)
		throws PortalException {

		_cpSpecificationOptionModelResourcePermission.check(
			getPermissionChecker(), cpSpecificationOptionId, ActionKeys.DELETE);

		cpSpecificationOptionLocalService.deleteCPSpecificationOption(
			cpSpecificationOptionId);
	}

	@Override
	public CPSpecificationOption fetchCPSpecificationOption(
			long cpSpecificationOptionId)
		throws PortalException {

		_cpSpecificationOptionModelResourcePermission.check(
			getPermissionChecker(), cpSpecificationOptionId, ActionKeys.VIEW);

		return cpSpecificationOptionLocalService.fetchCPSpecificationOption(
			cpSpecificationOptionId);
	}

	@Override
	public CPSpecificationOption fetchCPSpecificationOption(
			long groupId, String key)
		throws PortalException {

		CPSpecificationOption cpSpecificationOption =
			cpSpecificationOptionLocalService.fetchCPSpecificationOption(
				groupId, key);

		if (cpSpecificationOption != null) {
			_cpSpecificationOptionModelResourcePermission.check(
				getPermissionChecker(), cpSpecificationOption, ActionKeys.VIEW);
		}

		return cpSpecificationOption;
	}

	@Override
	public CPSpecificationOption getCPSpecificationOption(
			long cpSpecificationOptionId)
		throws PortalException {

		_cpSpecificationOptionModelResourcePermission.check(
			getPermissionChecker(), cpSpecificationOptionId, ActionKeys.VIEW);

		return cpSpecificationOptionLocalService.getCPSpecificationOption(
			cpSpecificationOptionId);
	}

	@Override
	public List<CPSpecificationOption> getCPSpecificationOptions(
			long groupId, int start, int end,
			OrderByComparator<CPSpecificationOption> orderByComparator)
		throws PortalException {

		return cpSpecificationOptionPersistence.filterFindByGroupId(
			groupId, start, end, orderByComparator);
	}

	@Override
	public int getCPSpecificationOptionsCount(long groupId) {
		return cpSpecificationOptionPersistence.filterCountByGroupId(groupId);
	}

	@Override
	public BaseModelSearchResult<CPSpecificationOption>
			searchCPSpecificationOptions(
				long companyId, long groupId, Boolean facetable,
				String keywords, int start, int end, Sort sort)
		throws PortalException {

		return cpSpecificationOptionLocalService.searchCPSpecificationOptions(
			companyId, groupId, facetable, keywords, start, end, sort);
	}

	@Override
	public CPSpecificationOption updateCPSpecificationOption(
			long cpSpecificationOptionId, long cpOptionCategoryId,
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			boolean facetable, String key, ServiceContext serviceContext)
		throws PortalException {

		_cpSpecificationOptionModelResourcePermission.check(
			getPermissionChecker(), cpSpecificationOptionId, ActionKeys.UPDATE);

		if (cpOptionCategoryId !=
				CPOptionCategoryConstants.DEFAULT_CP_OPTION_CATEGORY_ID) {

			_cpOptionCategoryModelResourcePermission.check(
				getPermissionChecker(), cpOptionCategoryId, ActionKeys.VIEW);
		}

		return cpSpecificationOptionLocalService.updateCPSpecificationOption(
			cpSpecificationOptionId, cpOptionCategoryId, titleMap,
			descriptionMap, facetable, key, serviceContext);
	}

	private static volatile ModelResourcePermission<CPOptionCategory>
		_cpOptionCategoryModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				CPSpecificationOptionServiceImpl.class,
				"_cpOptionCategoryModelResourcePermission",
				CPOptionCategory.class);
	private static volatile ModelResourcePermission<CPSpecificationOption>
		_cpSpecificationOptionModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				CPSpecificationOptionServiceImpl.class,
				"_cpSpecificationOptionModelResourcePermission",
				CPSpecificationOption.class);
	private static volatile PortletResourcePermission
		_portletResourcePermission =
			PortletResourcePermissionFactory.getInstance(
				CPSpecificationOptionServiceImpl.class,
				"_portletResourcePermission", CPConstants.RESOURCE_NAME);

}