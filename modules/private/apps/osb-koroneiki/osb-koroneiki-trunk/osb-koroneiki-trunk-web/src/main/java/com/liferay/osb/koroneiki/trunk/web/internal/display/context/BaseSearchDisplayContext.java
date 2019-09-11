/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.osb.koroneiki.trunk.web.internal.display.context;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Amos Fong
 */
public abstract class BaseSearchDisplayContext {

	public BaseSearchDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse,
		HttpServletRequest httpServletRequest) {

		this.renderRequest = renderRequest;
		this.renderResponse = renderResponse;
		request = httpServletRequest;

		themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public String getKeywords() {
		if (Validator.isNull(_keywords)) {
			_keywords = ParamUtil.getString(request, "keywords");
		}

		return _keywords;
	}

	public String getOrderByCol() {
		if (Validator.isNull(_orderByCol)) {
			_orderByCol = ParamUtil.getString(
				request, "orderByCol", getDefaultOrderByCol());
		}

		return _orderByCol;
	}

	public String getOrderByType() {
		if (Validator.isNull(_orderByType)) {
			_orderByType = ParamUtil.getString(request, "orderByType", "asc");
		}

		return _orderByType;
	}

	public PortletURL getPortletURL() {
		PortletURL portletURL = renderResponse.createRenderURL();

		if (Validator.isNotNull(getKeywords())) {
			portletURL.setParameter("keywords", getKeywords());
		}

		portletURL.setParameter("orderByCol", getOrderByCol());
		portletURL.setParameter("orderByType", getOrderByType());
		portletURL.setParameter(
			"tabs1", ParamUtil.getString(renderRequest, "tabs1"));

		return portletURL;
	}

	public SearchContainer getSearchContainer() throws Exception {
		if (searchContainer == null) {
			searchContainer = createSearchContainer();

			searchContainer.setOrderByCol(getOrderByCol());
			searchContainer.setOrderByType(getOrderByType());
		}

		return searchContainer;
	}

	protected abstract SearchContainer createSearchContainer() throws Exception;

	protected String getDefaultOrderByCol() {
		return "name";
	}

	protected final RenderRequest renderRequest;
	protected final RenderResponse renderResponse;
	protected final HttpServletRequest request;
	protected SearchContainer searchContainer;
	protected final ThemeDisplay themeDisplay;

	private String _keywords;
	private String _orderByCol;
	private String _orderByType;

}