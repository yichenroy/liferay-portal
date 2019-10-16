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
import com.liferay.commerce.product.model.CPOption;
import com.liferay.commerce.product.service.base.CPOptionServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
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
 * @author Marco Leo
 */
public class CPOptionServiceImpl extends CPOptionServiceBaseImpl {

	@Override
	public CPOption addCPOption(
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			String ddmFormFieldTypeName, boolean facetable, boolean required,
			boolean skuContributor, String key, ServiceContext serviceContext)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			CPActionKeys.ADD_COMMERCE_PRODUCT_OPTION);

		return cpOptionLocalService.addCPOption(
			nameMap, descriptionMap, ddmFormFieldTypeName, facetable, required,
			skuContributor, key, serviceContext);
	}

	@Override
	public void deleteCPOption(long cpOptionId) throws PortalException {
		_cpOptionModelResourcePermission.check(
			getPermissionChecker(), cpOptionId, ActionKeys.DELETE);

		cpOptionLocalService.deleteCPOption(cpOptionId);
	}

	@Override
	public CPOption fetchCPOption(long cpOptionId) throws PortalException {
		CPOption cpOption = cpOptionLocalService.fetchCPOption(cpOptionId);

		if (cpOption != null) {
			_cpOptionModelResourcePermission.check(
				getPermissionChecker(), cpOption, ActionKeys.VIEW);
		}

		return cpOption;
	}

	@Override
	public CPOption fetchCPOption(long groupId, String key)
		throws PortalException {

		CPOption cpOption = cpOptionLocalService.fetchCPOption(groupId, key);

		if (cpOption != null) {
			_cpOptionModelResourcePermission.check(
				getPermissionChecker(), cpOption, ActionKeys.VIEW);
		}

		return cpOption;
	}

	@Override
	public CPOption getCPOption(long cpOptionId) throws PortalException {
		_cpOptionModelResourcePermission.check(
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
	public CPOption setFacetable(long cpOptionId, boolean facetable)
		throws PortalException {

		_cpOptionModelResourcePermission.check(
			getPermissionChecker(), cpOptionId, ActionKeys.UPDATE);

		return cpOptionLocalService.setFacetable(cpOptionId, facetable);
	}

	@Override
	public CPOption setRequired(long cpOptionId, boolean required)
		throws PortalException {

		_cpOptionModelResourcePermission.check(
			getPermissionChecker(), cpOptionId, ActionKeys.UPDATE);

		return cpOptionLocalService.setRequired(cpOptionId, required);
	}

	@Override
	public CPOption setSkuContributor(long cpOptionId, boolean skuContributor)
		throws PortalException {

		_cpOptionModelResourcePermission.check(
			getPermissionChecker(), cpOptionId, ActionKeys.UPDATE);

		return cpOptionLocalService.setSkuContributor(
			cpOptionId, skuContributor);
	}

	@Override
	public CPOption updateCPOption(
			long cpOptionId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, String ddmFormFieldTypeName,
			boolean facetable, boolean required, boolean skuContributor,
			String key, ServiceContext serviceContext)
		throws PortalException {

		_cpOptionModelResourcePermission.check(
			getPermissionChecker(), cpOptionId, ActionKeys.UPDATE);

		return cpOptionLocalService.updateCPOption(
			cpOptionId, nameMap, descriptionMap, ddmFormFieldTypeName,
			facetable, required, skuContributor, key, serviceContext);
	}

	private static volatile ModelResourcePermission<CPOption>
		_cpOptionModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				CPOptionServiceImpl.class, "_cpOptionModelResourcePermission",
				CPOption.class);
	private static volatile PortletResourcePermission
		_portletResourcePermission =
			PortletResourcePermissionFactory.getInstance(
				CPOptionServiceImpl.class, "_portletResourcePermission",
				CPConstants.RESOURCE_NAME);

}