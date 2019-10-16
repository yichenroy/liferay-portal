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

package com.liferay.commerce.product.definitions.web.internal.display.context;

import com.liferay.commerce.product.definitions.web.display.context.BaseCPDefinitionsSearchContainerDisplayContext;
import com.liferay.commerce.product.definitions.web.internal.servlet.taglib.ui.CPDefinitionScreenNavigationConstants;
import com.liferay.commerce.product.definitions.web.internal.util.CPDefinitionsPortletUtil;
import com.liferay.commerce.product.definitions.web.portlet.action.ActionHelper;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPDefinitionOptionRelService;
import com.liferay.commerce.product.service.CPInstanceService;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.frontend.taglib.servlet.taglib.ManagementBarFilterItem;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletException;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 * @author Marco Leo
 */
public class CPInstanceDisplayContext
	extends BaseCPDefinitionsSearchContainerDisplayContext<CPInstance> {

	public CPInstanceDisplayContext(
			ActionHelper actionHelper, HttpServletRequest httpServletRequest,
			CPDefinitionOptionRelService cpDefinitionOptionRelService,
			CPInstanceService cpInstanceService,
			CPInstanceHelper cpInstanceHelper)
		throws PortalException {

		super(
			actionHelper, httpServletRequest, CPInstance.class.getSimpleName());

		setDefaultOrderByCol("sku");

		_cpDefinitionOptionRelService = cpDefinitionOptionRelService;
		_cpInstanceService = cpInstanceService;
		_cpInstanceHelper = cpInstanceHelper;
	}

	public List<CPDefinitionOptionRel> getCPDefinitionOptionRels()
		throws PortalException {

		CPDefinition cpDefinition = getCPDefinition();

		if (cpDefinition == null) {
			return Collections.emptyList();
		}

		return cpDefinition.getCPDefinitionOptionRels();
	}

	public List<CPDefinitionOptionValueRel> getCPDefinitionOptionValueRels(
			long cpDefinitionOptionValueRelId)
		throws PortalException {

		return actionHelper.getCPDefinitionOptionValueRels(
			cpDefinitionOptionValueRelId);
	}

	public CPInstance getCPInstance() throws PortalException {
		if (_cpInstance != null) {
			return _cpInstance;
		}

		_cpInstance = actionHelper.getCPInstance(
			cpRequestHelper.getRenderRequest());

		return _cpInstance;
	}

	public long getCPInstanceId() throws PortalException {
		CPInstance cpInstance = getCPInstance();

		if (cpInstance == null) {
			return 0;
		}

		return cpInstance.getCPInstanceId();
	}

	@Override
	public List<ManagementBarFilterItem> getManagementBarStatusFilterItems()
		throws PortalException, PortletException {

		List<ManagementBarFilterItem> managementBarFilterItems =
			super.getManagementBarStatusFilterItems();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		int workflowDefinitionLinksCount =
			WorkflowDefinitionLinkLocalServiceUtil.
				getWorkflowDefinitionLinksCount(
					themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(),
					CPInstance.class.getName());

		if (workflowDefinitionLinksCount == 0) {
			workflowDefinitionLinksCount =
				WorkflowDefinitionLinkLocalServiceUtil.
					getWorkflowDefinitionLinksCount(
						themeDisplay.getCompanyId(),
						WorkflowConstants.DEFAULT_GROUP_ID,
						CPInstance.class.getName());
		}

		if (workflowDefinitionLinksCount > 0) {
			managementBarFilterItems.add(
				getManagementBarFilterItem(WorkflowConstants.STATUS_PENDING));
			managementBarFilterItems.add(
				getManagementBarFilterItem(WorkflowConstants.STATUS_DENIED));
		}

		return managementBarFilterItems;
	}

	@Override
	public PortletURL getPortletURL() throws PortalException {
		PortletURL portletURL = super.getPortletURL();

		portletURL.setParameter(
			"mvcRenderCommandName", "editProductDefinition");
		portletURL.setParameter(
			"screenNavigationCategoryKey", getScreenNavigationCategoryKey());

		return portletURL;
	}

	@Override
	public String getScreenNavigationCategoryKey() throws PortalException {
		return CPDefinitionScreenNavigationConstants.CATEGORY_KEY_SKUS;
	}

	@Override
	public SearchContainer<CPInstance> getSearchContainer()
		throws PortalException {

		if (searchContainer != null) {
			return searchContainer;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		searchContainer = new SearchContainer<>(
			liferayPortletRequest, getPortletURL(), null, null);

		searchContainer.setEmptyResultsMessage("no-skus-were-found");

		OrderByComparator<CPInstance> orderByComparator =
			CPDefinitionsPortletUtil.getCPInstanceOrderByComparator(
				getOrderByCol(), getOrderByType());

		searchContainer.setOrderByCol(getOrderByCol());
		searchContainer.setOrderByComparator(orderByComparator);
		searchContainer.setOrderByType(getOrderByType());
		searchContainer.setRowChecker(getRowChecker());

		if (isSearch()) {
			Sort sort = CPDefinitionsPortletUtil.getCPInstanceSort(
				getOrderByCol(), getOrderByType());

			BaseModelSearchResult<CPInstance> cpInstanceBaseModelSearchResult =
				_cpInstanceService.searchCPInstances(
					themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(),
					getCPDefinitionId(), getKeywords(), getStatus(),
					searchContainer.getStart(), searchContainer.getEnd(), sort);

			searchContainer.setTotal(
				cpInstanceBaseModelSearchResult.getLength());
			searchContainer.setResults(
				cpInstanceBaseModelSearchResult.getBaseModels());
		}
		else {
			int total = _cpInstanceService.getCPInstancesCount(
				getCPDefinitionId(), getStatus());

			searchContainer.setTotal(total);

			List<CPInstance> results = _cpInstanceService.getCPInstances(
				getCPDefinitionId(), getStatus(), searchContainer.getStart(),
				searchContainer.getEnd(), orderByComparator);

			searchContainer.setResults(results);
		}

		return searchContainer;
	}

	public List<CPDefinitionOptionRel> getSkuContributorCPDefinitionOptionRels()
		throws PortalException {

		return actionHelper.getSkuContributorCPDefinitionOptionRels(
			getCPDefinitionId());
	}

	public boolean hasSkuContributorOption() throws PortalException {
		List<CPDefinitionOptionRel> cpDefinitionOptionRels =
			_cpDefinitionOptionRelService.
				getSkuContributorCPDefinitionOptionRels(getCPDefinitionId());

		if (cpDefinitionOptionRels.isEmpty()) {
			return false;
		}

		return true;
	}

	public Map<CPDefinitionOptionRel, List<CPDefinitionOptionValueRel>>
			parseCPInstanceDDMContent(long cpInstanceId)
		throws PortalException {

		if (cpInstanceId <= 0) {
			return Collections.emptyMap();
		}

		CPInstance cpInstance = _cpInstanceService.getCPInstance(cpInstanceId);

		return _cpInstanceHelper.parseJSONString(cpInstance.getDDMContent());
	}

	public String renderOptions(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortalException {

		return _cpInstanceHelper.render(
			getCPDefinitionId(), renderRequest, renderResponse);
	}

	private final CPDefinitionOptionRelService _cpDefinitionOptionRelService;
	private CPInstance _cpInstance;
	private final CPInstanceHelper _cpInstanceHelper;
	private final CPInstanceService _cpInstanceService;

}