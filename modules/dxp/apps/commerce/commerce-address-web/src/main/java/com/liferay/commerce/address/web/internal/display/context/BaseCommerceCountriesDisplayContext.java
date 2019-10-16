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

import com.liferay.commerce.address.web.internal.portlet.action.ActionHelper;
import com.liferay.commerce.address.web.internal.util.CountriesCommerceAdminModule;
import com.liferay.commerce.model.CommerceCountry;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Alessio Antonio Rendina
 */
public abstract class BaseCommerceCountriesDisplayContext<T> {

	public BaseCommerceCountriesDisplayContext(
		ActionHelper actionHelper, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		this.actionHelper = actionHelper;
		this.renderRequest = renderRequest;
		this.renderResponse = renderResponse;

		_defaultOrderByCol = "priority";
		_defaultOrderByType = "asc";
	}

	public CommerceCountry getCommerceCountry() throws PortalException {
		if (_commerceCountry != null) {
			return _commerceCountry;
		}

		return actionHelper.getCommerceCountry(renderRequest);
	}

	public long getCommerceCountryId() throws PortalException {
		CommerceCountry commerceCountry = getCommerceCountry();

		if (commerceCountry == null) {
			return 0;
		}

		return commerceCountry.getCommerceCountryId();
	}

	public String getOrderByCol() {
		return ParamUtil.getString(
			renderRequest, SearchContainer.DEFAULT_ORDER_BY_COL_PARAM,
			_defaultOrderByCol);
	}

	public String getOrderByType() {
		return ParamUtil.getString(
			renderRequest, SearchContainer.DEFAULT_ORDER_BY_TYPE_PARAM,
			_defaultOrderByType);
	}

	public PortletURL getPortletURL() throws PortalException {
		PortletURL portletURL = renderResponse.createRenderURL();

		portletURL.setParameter(
			"commerceAdminModuleKey", CountriesCommerceAdminModule.KEY);
		portletURL.setParameter("navigation", getNavigation());
		portletURL.setParameter("orderByCol", getOrderByCol());
		portletURL.setParameter("orderByType", getOrderByType());

		return portletURL;
	}

	public RowChecker getRowChecker() {
		if (_rowChecker != null) {
			return _rowChecker;
		}

		RowChecker rowChecker = new EmptyOnClickRowChecker(renderResponse);

		_rowChecker = rowChecker;

		return _rowChecker;
	}

	public abstract SearchContainer<T> getSearchContainer()
		throws PortalException;

	public void setDefaultOrderByCol(String defaultOrderByCol) {
		_defaultOrderByCol = defaultOrderByCol;
	}

	public void setDefaultOrderByType(String defaultOrderByType) {
		_defaultOrderByType = defaultOrderByType;
	}

	protected String getNavigation() {
		return ParamUtil.getString(renderRequest, "navigation");
	}

	protected final ActionHelper actionHelper;
	protected final RenderRequest renderRequest;
	protected final RenderResponse renderResponse;
	protected SearchContainer<T> searchContainer;

	private CommerceCountry _commerceCountry;
	private String _defaultOrderByCol;
	private String _defaultOrderByType;
	private RowChecker _rowChecker;

}