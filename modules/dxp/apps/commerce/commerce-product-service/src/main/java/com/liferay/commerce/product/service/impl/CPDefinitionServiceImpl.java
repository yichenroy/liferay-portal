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
import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.base.CPDefinitionServiceBaseImpl;
import com.liferay.commerce.product.service.permission.CPDefinitionPermission;
import com.liferay.commerce.product.service.permission.CPPermission;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.Serializable;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Marco Leo
 */
public class CPDefinitionServiceImpl extends CPDefinitionServiceBaseImpl {

	@Override
	public CPDefinition addCPDefinition(
			String baseSKU, Map<Locale, String> titleMap,
			Map<Locale, String> shortDescriptionMap,
			Map<Locale, String> descriptionMap, Map<Locale, String> urlTitleMap,
			String layoutUuid, String productTypeName, String ddmStructureKey,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, ServiceContext serviceContext)
		throws PortalException {

		CPPermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			CPActionKeys.ADD_COMMERCE_PRODUCT_DEFINITION);

		return cpDefinitionLocalService.addCPDefinition(
			baseSKU, titleMap, shortDescriptionMap, descriptionMap, urlTitleMap,
			layoutUuid, productTypeName, ddmStructureKey, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			serviceContext);
	}

	@Override
	public CPDefinition deleteCPDefinition(CPDefinition cpDefinition)
		throws PortalException {

		CPDefinitionPermission.check(
			getPermissionChecker(), cpDefinition, ActionKeys.DELETE);

		return cpDefinitionLocalService.deleteCPDefinition(cpDefinition);
	}

	@Override
	public CPDefinition deleteCPDefinition(long cpDefinitionId)
		throws PortalException {

		CPDefinitionPermission.check(
			getPermissionChecker(), cpDefinitionId, ActionKeys.DELETE);

		return cpDefinitionLocalService.deleteCPDefinition(cpDefinitionId);
	}

	@Override
	public CPDefinition fetchCPDefinition(long cpDefinitionId)
		throws PortalException {

		CPDefinition cpDefinition = cpDefinitionLocalService.fetchCPDefinition(
			cpDefinitionId);

		if (cpDefinition != null) {
			CPDefinitionPermission.check(
				getPermissionChecker(), cpDefinition, ActionKeys.VIEW);
		}

		return cpDefinition;
	}

	@Override
	public CPDefinition getCPDefinition(long cpDefinitionId)
		throws PortalException {

		CPDefinitionPermission.check(
			getPermissionChecker(), cpDefinitionId, ActionKeys.VIEW);

		return cpDefinitionLocalService.getCPDefinition(cpDefinitionId);
	}

	@Override
	public List<CPDefinition> getCPDefinitions(
			long groupId, String productTypeName, String languageId, int status,
			int start, int end,
			OrderByComparator<CPDefinition> orderByComparator)
		throws PortalException {

		QueryDefinition<CPDefinition> queryDefinition = new QueryDefinition<>(
			status, start, end, orderByComparator);

		if (status == WorkflowConstants.STATUS_ANY) {
			queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH, true);
		}

		return cpDefinitionFinder.filterFindByG_P_S(
			groupId, productTypeName, languageId, queryDefinition);
	}

	@Override
	public int getCPDefinitionsCount(
		long groupId, String productTypeName, String languageId, int status) {

		QueryDefinition<CPDefinition> queryDefinition = new QueryDefinition<>(
			status);

		if (status == WorkflowConstants.STATUS_ANY) {
			queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH, true);
		}

		return cpDefinitionFinder.filterCountByG_P_S(
			groupId, productTypeName, languageId, queryDefinition);
	}

	@Override
	public CPAttachmentFileEntry getDefaultImage(long cpDefinitionId)
		throws PortalException {

		CPDefinitionPermission.check(
			getPermissionChecker(), cpDefinitionId, ActionKeys.VIEW);

		return cpDefinitionLocalService.getDefaultImage(cpDefinitionId);
	}

	@Override
	public String getDisplayPage(CPDefinition cpDefinition)
		throws PortalException {

		return cpDefinitionLocalService.getDisplayPage(cpDefinition);
	}

	@Override
	public String getUrlTitleMapAsXML(CPDefinition cpDefinition) {
		return cpDefinitionLocalService.getUrlTitleMapAsXML(cpDefinition);
	}

	@Override
	public CPDefinition moveCPDefinitionToTrash(long cpDefinitionId)
		throws PortalException {

		CPDefinitionPermission.check(
			getPermissionChecker(), cpDefinitionId, ActionKeys.DELETE);

		return cpDefinitionLocalService.moveCPDefinitionToTrash(
			getUserId(), cpDefinitionId);
	}

	@Override
	public void restoreCPDefinitionFromTrash(long cpDefinitionId)
		throws PortalException {

		CPDefinitionPermission.check(
			getPermissionChecker(), cpDefinitionId, ActionKeys.DELETE);

		cpDefinitionLocalService.restoreCPDefinitionFromTrash(
			getUserId(), cpDefinitionId);
	}

	@Override
	public Hits search(SearchContext searchContext) {
		return cpDefinitionLocalService.search(searchContext);
	}

	@Override
	public BaseModelSearchResult<CPDefinition> searchCPDefinitions(
			long companyId, long groupId, String keywords, int status,
			int start, int end, Sort sort)
		throws PortalException {

		return cpDefinitionLocalService.searchCPDefinitions(
			companyId, groupId, keywords, status, start, end, sort);
	}

	@Override
	public CPDefinition updateCPDefinition(
			long cpDefinitionId, String baseSKU, Map<Locale, String> titleMap,
			Map<Locale, String> shortDescriptionMap,
			Map<Locale, String> descriptionMap, Map<Locale, String> urlTitleMap,
			String layoutUuid, String ddmStructureKey, int displayDateMonth,
			int displayDateDay, int displayDateYear, int displayDateHour,
			int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, ServiceContext serviceContext)
		throws PortalException {

		CPDefinitionPermission.check(
			getPermissionChecker(), cpDefinitionId, ActionKeys.UPDATE);

		return cpDefinitionLocalService.updateCPDefinition(
			cpDefinitionId, baseSKU, titleMap, shortDescriptionMap,
			descriptionMap, urlTitleMap, urlTitleMap, urlTitleMap, urlTitleMap,
			urlTitleMap, layoutUuid, ddmStructureKey, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			serviceContext);
	}

	@Override
	public CPDefinition updateStatus(
			long userId, long cpDefinitionId, int status,
			ServiceContext serviceContext,
			Map<String, Serializable> workflowContext)
		throws PortalException {

		CPDefinitionPermission.check(
			getPermissionChecker(), cpDefinitionId, ActionKeys.UPDATE);

		return cpDefinitionLocalService.updateStatus(
			userId, cpDefinitionId, status, serviceContext, workflowContext);
	}

}