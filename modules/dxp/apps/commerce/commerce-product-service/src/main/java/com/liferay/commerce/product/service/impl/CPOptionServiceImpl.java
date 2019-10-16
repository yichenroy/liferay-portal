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
import com.liferay.commerce.product.model.CPOption;
import com.liferay.commerce.product.service.base.CPOptionServiceBaseImpl;
import com.liferay.commerce.product.service.permission.CPOptionPermission;
import com.liferay.commerce.product.service.permission.CPPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Marco Leo
 */
public class CPOptionServiceImpl extends CPOptionServiceBaseImpl {

	@Override
	public CPOption addCPOption(
			String name, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, String ddmFormFieldTypeName,
			boolean facetable, boolean skuContributor,
			ServiceContext serviceContext)
		throws PortalException {

		CPPermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			CPActionKeys.ADD_COMMERCE_PRODUCT_OPTION);

		return cpOptionLocalService.addCPOption(
			name, titleMap, descriptionMap, ddmFormFieldTypeName, facetable,
			skuContributor, serviceContext);
	}

	@Override
	public CPOption deleteCPOption(CPOption cpOption) throws PortalException {
		CPOptionPermission.check(
			getPermissionChecker(), cpOption, ActionKeys.DELETE);

		return cpOptionLocalService.deleteCPOption(cpOption);
	}

	@Override
	public CPOption deleteCPOption(long cpOptionId) throws PortalException {
		CPOptionPermission.check(
			getPermissionChecker(), cpOptionId, ActionKeys.DELETE);

		return cpOptionLocalService.deleteCPOption(cpOptionId);
	}

	@Override
	public CPOption fetchCPOption(long cpOptionId) throws PortalException {
		CPOption cpOption = cpOptionLocalService.fetchCPOption(cpOptionId);

		if (cpOption != null) {
			CPOptionPermission.check(
				getPermissionChecker(), cpOption, ActionKeys.VIEW);
		}

		return cpOption;
	}

	@Override
	public CPOption getCPOption(long cpOptionId) throws PortalException {
		CPOptionPermission.check(
			getPermissionChecker(), cpOptionId, ActionKeys.VIEW);

		return cpOptionLocalService.getCPOption(cpOptionId);
	}

	@Override
	public List<CPOption> getCPOptions(long groupId, int start, int end) {
		return cpOptionLocalService.getCPOptions(groupId, start, end);
	}

	@Override
	public List<CPOption> getCPOptions(
		long groupId, int start, int end,
		OrderByComparator<CPOption> orderByComparator) {

		return cpOptionLocalService.getCPOptions(
			groupId, start, end, orderByComparator);
	}

	@Override
	public int getCPOptionsCount(long groupId) {
		return cpOptionLocalService.getCPOptionsCount(groupId);
	}

	@Override
	public Hits search(SearchContext searchContext) {
		return cpOptionLocalService.search(searchContext);
	}

	@Override
	public BaseModelSearchResult<CPOption> searchCPOptions(
			long companyId, long groupId, String keywords, int start, int end,
			Sort sort)
		throws PortalException {

		return cpOptionLocalService.searchCPOptions(
			companyId, groupId, keywords, start, end, sort);
	}

	@Override
	public CPOption updateCPOption(
			long cpOptionId, String name, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, String ddmFormFieldTypeName,
			boolean facetable, boolean skuContributor,
			ServiceContext serviceContext)
		throws PortalException {

		CPOptionPermission.check(
			getPermissionChecker(), cpOptionId, ActionKeys.UPDATE);

		return cpOptionLocalService.updateCPOption(
			cpOptionId, name, titleMap, descriptionMap, ddmFormFieldTypeName,
			facetable, skuContributor, serviceContext);
	}

}