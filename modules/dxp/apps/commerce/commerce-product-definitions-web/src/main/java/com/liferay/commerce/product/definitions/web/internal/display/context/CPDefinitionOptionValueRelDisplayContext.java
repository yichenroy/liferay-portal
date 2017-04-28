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

import com.liferay.commerce.product.definitions.web.internal.portlet.action.ActionHelper;
import com.liferay.commerce.product.definitions.web.internal.util.CPDefinitionsPortletUtil;
import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.service.CPDefinitionOptionValueRelService;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marco Leo
 */
public class CPDefinitionOptionValueRelDisplayContext
	extends BaseCPDefinitionsDisplayContext {

	public CPDefinitionOptionValueRelDisplayContext(
			ActionHelper actionHelper, HttpServletRequest httpServletRequest,
			CPDefinitionOptionValueRelService
				cpDefinitionOptionValueRelService)
		throws PortalException {

		super(actionHelper, httpServletRequest, "CPDefinitionOptionValueRel");

		setDefaultOrderByCol("priority");

		_cpDefinitionOptionValueRelService = cpDefinitionOptionValueRelService;
	}

	public CPDefinitionOptionRel
		getCPDefinitionOptionRel() throws PortalException {

		if (_cpDefinitionOptionRel != null) {
			return _cpDefinitionOptionRel;
		}

		_cpDefinitionOptionRel = actionHelper.getCPDefinitionOptionRel(
			cpRequestHelper.getRenderRequest());

		return _cpDefinitionOptionRel;
	}

	public long getCPDefinitionOptionRelId() throws PortalException {
		CPDefinitionOptionRel cpDefinitionOptionRel =
			getCPDefinitionOptionRel();

		if (cpDefinitionOptionRel == null) {
			return 0;
		}

		return cpDefinitionOptionRel.getCPDefinitionOptionRelId();
	}

	public CPDefinitionOptionValueRel
		getCPDefinitionOptionValueRel() throws PortalException {

		if (_cpDefinitionOptionValueRel != null) {
			return _cpDefinitionOptionValueRel;
		}

		_cpDefinitionOptionValueRel =
			actionHelper.getCPDefinitionOptionValueRel(
				cpRequestHelper.getRenderRequest());

		return _cpDefinitionOptionValueRel;
	}

	public long getCPDefinitionOptionValueRelId() throws PortalException {
		CPDefinitionOptionValueRel cpDefinitionOptionValueRel =
			getCPDefinitionOptionValueRel();

		if (cpDefinitionOptionValueRel == null) {
			return 0;
		}

		return cpDefinitionOptionValueRel.getCPDefinitionOptionValueRelId();
	}

	@Override
	public PortletURL getPortletURL() throws PortalException {
		PortletURL portletURL = super.getPortletURL();

		portletURL.setParameter(
			"mvcRenderCommandName", "viewProductDefinitionOptionValueRels");
		portletURL.setParameter(
			"cpDefinitionOptionRelId",
			String.valueOf(getCPDefinitionOptionRelId()));

		return portletURL;
	}

	@Override
	public SearchContainer
		getSearchContainer() throws PortalException {

		if (searchContainer != null) {
			return searchContainer;
		}

		SearchContainer<CPDefinitionOptionValueRel> searchContainer =
			new SearchContainer<>(
				liferayPortletRequest, getPortletURL(), null, null);

		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator =
			CPDefinitionsPortletUtil.
				getCPDefinitionOptionValueRelOrderByComparator(
					getOrderByCol(), getOrderByType());

		searchContainer.setOrderByCol(getOrderByCol());
		searchContainer.setOrderByComparator(orderByComparator);
		searchContainer.setOrderByType(getOrderByType());
		searchContainer.setEmptyResultsMessage("no-option-values-were-found");
		searchContainer.setRowChecker(getRowChecker());

		int total =
			_cpDefinitionOptionValueRelService.
				getCPDefinitionOptionValueRelsCount(
					getCPDefinitionOptionRelId());

		searchContainer.setTotal(total);

		List<CPDefinitionOptionValueRel> results =
			_cpDefinitionOptionValueRelService.getCPDefinitionOptionValueRels(
				getCPDefinitionOptionRelId(), searchContainer.getStart(),
				searchContainer.getEnd(), orderByComparator);

		searchContainer.setResults(results);

		this.searchContainer = searchContainer;

		return this.searchContainer;
	}

	private CPDefinitionOptionRel _cpDefinitionOptionRel;
	private CPDefinitionOptionValueRel _cpDefinitionOptionValueRel;
	private final CPDefinitionOptionValueRelService
		_cpDefinitionOptionValueRelService;

}