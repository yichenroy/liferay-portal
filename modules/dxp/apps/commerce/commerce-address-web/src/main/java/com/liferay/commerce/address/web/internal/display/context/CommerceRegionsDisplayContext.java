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

package com.liferay.commerce.address.web.internal.display.context;

import com.liferay.commerce.address.model.CommerceRegion;
import com.liferay.commerce.address.service.CommerceRegionService;
import com.liferay.commerce.address.web.internal.portlet.action.ActionHelper;
import com.liferay.commerce.address.web.internal.util.CommerceAddressUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceRegionsDisplayContext
	extends BaseCommerceCountriesDisplayContext<CommerceRegion> {

	public CommerceRegionsDisplayContext(
		ActionHelper actionHelper, CommerceRegionService commerceRegionService,
		RenderRequest renderRequest, RenderResponse renderResponse) {

		super(actionHelper, renderRequest, renderResponse);

		_commerceRegionService = commerceRegionService;
	}

	public CommerceRegion getCommerceRegion() throws PortalException {
		if (_commerceRegion != null) {
			return _commerceRegion;
		}

		_commerceRegion = actionHelper.getCommerceRegion(renderRequest);

		return _commerceRegion;
	}

	public long getCommerceRegionId() throws PortalException {
		CommerceRegion commerceRegion = getCommerceRegion();

		if (commerceRegion == null) {
			return 0;
		}

		return commerceRegion.getCommerceRegionId();
	}

	@Override
	public PortletURL getPortletURL() throws PortalException {
		PortletURL portletURL = super.getPortletURL();

		portletURL.setParameter("mvcRenderCommandName", "editCommerceCountry");
		portletURL.setParameter(
			"screenNavigationCategoryKey", getScreenNavigationCategoryKey());

		long commerceCountryId = getCommerceCountryId();

		if (commerceCountryId > 0) {
			portletURL.setParameter(
				"commerceCountryId", String.valueOf(commerceCountryId));
		}

		return portletURL;
	}

	public String getScreenNavigationCategoryKey() {
		return ParamUtil.getString(
			renderRequest, "screenNavigationCategoryKey", "regions");
	}

	@Override
	public SearchContainer<CommerceRegion> getSearchContainer()
		throws PortalException {

		if (searchContainer != null) {
			return searchContainer;
		}

		String emptyResultsMessage = "no-regions-were-found";

		searchContainer = new SearchContainer<>(
			renderRequest, getPortletURL(), null, emptyResultsMessage);

		String orderByCol = getOrderByCol();
		String orderByType = getOrderByType();

		OrderByComparator<CommerceRegion> orderByComparator =
			CommerceAddressUtil.getCommerceRegionOrderByComparator(
				orderByCol, orderByType);

		searchContainer.setOrderByCol(orderByCol);
		searchContainer.setOrderByComparator(orderByComparator);
		searchContainer.setOrderByType(orderByType);
		searchContainer.setRowChecker(getRowChecker());

		int total = _commerceRegionService.getCommerceRegionsCount(
			getCommerceCountryId());

		List<CommerceRegion> results =
			_commerceRegionService.getCommerceRegions(
				getCommerceCountryId(), searchContainer.getStart(),
				searchContainer.getEnd(), orderByComparator);

		searchContainer.setTotal(total);
		searchContainer.setResults(results);

		return searchContainer;
	}

	private CommerceRegion _commerceRegion;
	private final CommerceRegionService _commerceRegionService;

}