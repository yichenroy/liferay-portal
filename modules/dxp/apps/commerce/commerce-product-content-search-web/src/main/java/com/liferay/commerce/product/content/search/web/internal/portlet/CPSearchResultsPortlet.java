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

package com.liferay.commerce.product.content.search.web.internal.portlet;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.content.search.web.internal.configuration.CPSearchResultsPortletInstanceConfiguration;
import com.liferay.commerce.product.content.search.web.internal.display.context.CPSearchResultsDisplayContext;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SortFactoryUtil;
import com.liferay.portal.kernel.search.generic.BooleanClauseImpl;
import com.liferay.portal.kernel.search.generic.TermQueryImpl;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchContributor;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchRequest;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchResponse;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchSettings;

import java.io.IOException;

import java.util.Optional;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.css-class-wrapper=portlet-cp-search-results",
		"com.liferay.portlet.display-category=commerce",
		"com.liferay.portlet.icon=/icons/search.png",
		"com.liferay.portlet.instanceable=true",
		"com.liferay.portlet.layout-cacheable=true",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.restore-current-view=false",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Search Results",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + CPPortletKeys.CP_SEARCH_RESULTS,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=guest,power-user,user",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = {Portlet.class, PortletSharedSearchContributor.class}
)
public class CPSearchResultsPortlet
	extends MVCPortlet implements PortletSharedSearchContributor {

	@Override
	public void contribute(
		PortletSharedSearchSettings portletSharedSearchSettings) {

		RenderRequest renderRequest =
			portletSharedSearchSettings.getRenderRequest();

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		try {
			_cpSearchResultsPortletInstanceConfiguration =
				portletDisplay.getPortletInstanceConfiguration(
					CPSearchResultsPortletInstanceConfiguration.class);
		}
		catch (ConfigurationException ce) {
			_log.error(ce, ce);
		}

		portletSharedSearchSettings.setKeywords("*");

		portletSharedSearchSettings.addCondition(
			new BooleanClauseImpl(
				new TermQueryImpl(
					Field.ENTRY_CLASS_NAME, CPDefinition.class.getName()),
				BooleanClauseOccur.MUST));

		if (_cpSearchResultsPortletInstanceConfiguration.useAssetCategories()) {
			String[] assetCategoryIds =
				_cpSearchResultsPortletInstanceConfiguration.assetCategoryIds();

			portletSharedSearchSettings.addCondition(
				new BooleanClauseImpl(
					new TermQueryImpl(
						Field.ASSET_CATEGORY_IDS,
						StringUtil.merge(assetCategoryIds, StringPool.COMMA)),
					BooleanClauseOccur.MUST));
		}
		else {
			AssetCategory assetCategory =
				(AssetCategory)renderRequest.getAttribute(
					WebKeys.ASSET_CATEGORY);

			if (assetCategory != null) {
				portletSharedSearchSettings.addCondition(
					new BooleanClauseImpl(
						new TermQueryImpl(
							Field.ASSET_CATEGORY_IDS,
							String.valueOf(assetCategory.getCategoryId())),
						BooleanClauseOccur.MUST));
			}
		}

		SearchContext searchContext =
			portletSharedSearchSettings.getSearchContext();

		QueryConfig queryConfig = portletSharedSearchSettings.getQueryConfig();

		queryConfig.setHighlightEnabled(false);

		searchContext.setSorts(SortFactoryUtil.create(Field.TITLE, false));

		filterByThisSite(portletSharedSearchSettings);

		paginate(portletSharedSearchSettings);
	}

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			renderRequest);

		PortletSharedSearchResponse portletSharedSearchResponse =
			_portletSharedSearchRequest.search(renderRequest);

		try {
			CPSearchResultsDisplayContext cpSearchResultsDisplayContext =
				new CPSearchResultsDisplayContext(
					_dlAppService, httpServletRequest,
					portletSharedSearchResponse);

			renderRequest.setAttribute(
				WebKeys.PORTLET_DISPLAY_CONTEXT, cpSearchResultsDisplayContext);
		}
		catch (ConfigurationException ce) {
			_log.error(ce, ce);
		}

		super.render(renderRequest, renderResponse);
	}

	protected void filterByThisSite(
		PortletSharedSearchSettings portletSharedSearchSettings) {

		long groupIdOptional = getScopeGroupId(portletSharedSearchSettings);

		portletSharedSearchSettings.addCondition(
			new BooleanClauseImpl(
				new TermQueryImpl(
					Field.GROUP_ID, String.valueOf(groupIdOptional)),
				BooleanClauseOccur.MUST));
	}

	protected long getScopeGroupId(
		PortletSharedSearchSettings portletSharedSearchSettings) {

		ThemeDisplay themeDisplay =
			portletSharedSearchSettings.getThemeDisplay();

		return themeDisplay.getScopeGroupId();
	}

	protected void paginate(
		PortletSharedSearchSettings portletSharedSearchSettings) {

		String paginationStartParameterName = "start";

		portletSharedSearchSettings.setPaginationStartParameterName(
			paginationStartParameterName);

		Optional<String> paginationStartParameterValueOptional =
			portletSharedSearchSettings.getParameter(
				paginationStartParameterName);

		Optional<Integer> paginationStartOptional =
			paginationStartParameterValueOptional.map(Integer::valueOf);

		paginationStartOptional.ifPresent(
			portletSharedSearchSettings::setPaginationStart);

		int paginationDelta = 20;

		portletSharedSearchSettings.setPaginationDeltaParameterName("delta");
		portletSharedSearchSettings.setPaginationDelta(paginationDelta);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CPSearchResultsPortlet.class);

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	private CPSearchResultsPortletInstanceConfiguration
		_cpSearchResultsPortletInstanceConfiguration;

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private Portal _portal;

	@Reference
	private PortletSharedSearchRequest _portletSharedSearchRequest;

}