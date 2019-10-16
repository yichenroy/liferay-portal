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

package com.liferay.commerce.shipping.method.web.internal.display.context;

import com.liferay.commerce.model.CommerceShippingEngine;
import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.service.CommerceShippingMethodLocalService;
import com.liferay.commerce.shipping.method.web.internal.util.ShippingMethodsCommerceAdminModule;
import com.liferay.commerce.util.CommerceShippingEngineRegistry;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Andrea Di Giorgi
 */
public class CommerceShippingMethodsDisplayContext {

	public CommerceShippingMethodsDisplayContext(
		CommerceShippingEngineRegistry commerceShippingEngineRegistry,
		CommerceShippingMethodLocalService commerceShippingMethodLocalService,
		RenderRequest renderRequest, RenderResponse renderResponse) {

		_commerceShippingEngineRegistry = commerceShippingEngineRegistry;
		_commerceShippingMethodLocalService =
			commerceShippingMethodLocalService;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
	}

	public CommerceShippingEngine getCommerceShippingEngine()
		throws PortalException {

		String key = null;

		CommerceShippingMethod commerceShippingMethod =
			getCommerceShippingMethod();

		if (commerceShippingMethod != null) {
			key = commerceShippingMethod.getEngineKey();
		}
		else {
			key = ParamUtil.getString(_renderRequest, "engineKey");

			if (Validator.isNull(key)) {
				return null;
			}
		}

		return _commerceShippingEngineRegistry.getCommerceShippingEngine(key);
	}

	public CommerceShippingMethod getCommerceShippingMethod()
		throws PortalException {

		if (_commerceShippingMethod != null) {
			return _commerceShippingMethod;
		}

		long commerceShippingMethodId = ParamUtil.getLong(
			_renderRequest, "commerceShippingMethodId");
		String engineKey = ParamUtil.getString(_renderRequest, "engineKey");

		if (commerceShippingMethodId > 0) {
			_commerceShippingMethod =
				_commerceShippingMethodLocalService.getCommerceShippingMethod(
					commerceShippingMethodId);
		}
		else if (Validator.isNotNull(engineKey)) {
			_commerceShippingMethod = getDefaultCommerceShippingMethod(
				engineKey);
		}

		return _commerceShippingMethod;
	}

	public PortletURL getPortletURL() {
		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter(
			"commerceAdminModuleKey", ShippingMethodsCommerceAdminModule.KEY);

		String delta = ParamUtil.getString(_renderRequest, "delta");

		if (Validator.isNotNull(delta)) {
			portletURL.setParameter("delta", delta);
		}

		portletURL.setParameter("navigation", getNavigation());

		return portletURL;
	}

	public SearchContainer<CommerceShippingMethod> getSearchContainer() {
		if (_searchContainer != null) {
			return _searchContainer;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Boolean active = null;
		String emptyResultsMessage = "there-are-no-shipping-methods";

		String navigation = getNavigation();

		if (navigation.equals("active")) {
			active = Boolean.TRUE;
			emptyResultsMessage = "there-are-no-active-shipping-methods";
		}
		else if (navigation.equals("inactive")) {
			active = Boolean.FALSE;
			emptyResultsMessage = "there-are-no-inactive-shipping-methods";
		}

		_searchContainer = new SearchContainer<>(
			_renderRequest, getPortletURL(), null, emptyResultsMessage);

		List<CommerceShippingMethod> results;

		if (active != null) {
			results =
				_commerceShippingMethodLocalService.getCommerceShippingMethods(
					themeDisplay.getScopeGroupId(), active);
		}
		else {
			results =
				_commerceShippingMethodLocalService.getCommerceShippingMethods(
					themeDisplay.getScopeGroupId());
		}

		if ((active == null) || !active) {
			results = addDefaultCommerceShippingMethods(results);
		}

		_searchContainer.setTotal(results.size());
		_searchContainer.setResults(results);

		return _searchContainer;
	}

	protected List<CommerceShippingMethod> addDefaultCommerceShippingMethods(
		List<CommerceShippingMethod> commerceShippingMethods) {

		commerceShippingMethods = ListUtil.copy(commerceShippingMethods);

		Map<String, CommerceShippingEngine> commerceShippingEngines =
			_commerceShippingEngineRegistry.getCommerceShippingEngines();

		Set<String> commerceEngineKeys = new TreeSet<>(
			commerceShippingEngines.keySet());

		for (CommerceShippingMethod commerceShippingMethod :
				commerceShippingMethods) {

			commerceEngineKeys.remove(commerceShippingMethod.getEngineKey());
		}

		for (String name : commerceEngineKeys) {
			CommerceShippingMethod commerceShippingMethod =
				getDefaultCommerceShippingMethod(name);

			commerceShippingMethods.add(commerceShippingMethod);
		}

		return commerceShippingMethods;
	}

	protected CommerceShippingMethod getDefaultCommerceShippingMethod(
		String engineKey) {

		CommerceShippingEngine commerceShippingEngine =
			_commerceShippingEngineRegistry.getCommerceShippingEngine(
				engineKey);

		CommerceShippingMethod commerceShippingMethod =
			_commerceShippingMethodLocalService.createCommerceShippingMethod(0);

		Locale locale = LocaleUtil.getSiteDefault();

		commerceShippingMethod.setName(
			commerceShippingEngine.getName(locale), locale);
		commerceShippingMethod.setDescription(
			commerceShippingEngine.getDescription(locale), locale);

		commerceShippingMethod.setEngineKey(engineKey);

		return commerceShippingMethod;
	}

	protected String getNavigation() {
		return ParamUtil.getString(_renderRequest, "navigation");
	}

	private final CommerceShippingEngineRegistry
		_commerceShippingEngineRegistry;
	private CommerceShippingMethod _commerceShippingMethod;
	private final CommerceShippingMethodLocalService
		_commerceShippingMethodLocalService;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private SearchContainer<CommerceShippingMethod> _searchContainer;

}