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

package com.liferay.commerce.product.content.web;

import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.constants.CPWebKeys;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPFriendlyURLEntry;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.commerce.product.service.CPFriendlyURLEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutFriendlyURLComposite;
import com.liferay.portal.kernel.portlet.FriendlyURLResolver;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.InheritableMap;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(service = FriendlyURLResolver.class)
public class ProductFriendlyURLResolver implements FriendlyURLResolver {

	@Override
	public String getActualURL(
			long companyId, long groupId, boolean privateLayout,
			String mainPath, String friendlyURL, Map<String, String[]> params,
			Map<String, Object> requestContext)
		throws PortalException {

		HttpServletRequest request = (HttpServletRequest)requestContext.get(
			"request");
		HttpServletResponse response = (HttpServletResponse)requestContext.get(
			"response");

		HttpSession session = request.getSession();

		Locale locale = _portal.getLocale(request);
		String languageId = LanguageUtil.getLanguageId(locale);

		String urlTitle = friendlyURL.substring("/p/".length());

		long classNameId = _portal.getClassNameId(CPDefinition.class);

		CPFriendlyURLEntry cpFriendlyURLEntry =
			_cpFriendlyURLEntryLocalService.getCPFriendlyURLEntry(
				groupId, companyId, classNameId, languageId, urlTitle);

		if (!cpFriendlyURLEntry.isMain()) {
			cpFriendlyURLEntry =
				_cpFriendlyURLEntryLocalService.getCPFriendlyURLEntry(
					groupId, companyId, classNameId,
				cpFriendlyURLEntry.getPrimaryKey(), languageId, true);
		}

		CPDefinition cpDefinition = _cpDefinitionService.getCPDefinition(
			cpFriendlyURLEntry.getClassPK());

		Layout layout = getProductLayout(groupId, privateLayout, cpDefinition);

		String layoutActualURL = _portal.getLayoutActualURL(layout, mainPath);

		InheritableMap<String, String[]> actualParams = new InheritableMap<>();

		if (params != null) {
			actualParams.setParentMap(params);
		}

		UnicodeProperties typeSettingsProperties =
			layout.getTypeSettingsProperties();

		actualParams.put("p_p_id", new String[] {CPPortletKeys.CP_CONTENT_WEB});
		actualParams.put("p_p_lifecycle", new String[] {"0"});

		actualParams.put("p_p_mode", new String[] {"view"});

		String namespace = _portal.getPortletNamespace(
			CPPortletKeys.CP_CONTENT_WEB);

		actualParams.put(
			namespace + "cpDefinitionId",
			new String[] {String.valueOf(cpDefinition.getCPDefinitionId())});

		actualParams.put(namespace + "mvcPath", new String[] {"/view.jsp"});

		actualParams.put(
			namespace + "urlTitle",
			new String[] {cpFriendlyURLEntry.getUrlTitle()});

		request.setAttribute(CPWebKeys.CP_DEFINITION, cpDefinition);

		String queryString = HttpUtil.parameterMapToString(actualParams, false);

		if (layoutActualURL.contains(StringPool.QUESTION)) {
			layoutActualURL =
				layoutActualURL + StringPool.AMPERSAND + queryString;
		}
		else {
			layoutActualURL =
				layoutActualURL + StringPool.QUESTION + queryString;
		}

		_portal.addPageSubtitle(cpDefinition.getTitle(languageId), request);
		_portal.addPageDescription(
			cpDefinition.getShortDescription(languageId), request);

		List<AssetTag> assetTags = _assetTagLocalService.getTags(
			CPDefinition.class.getName(), cpDefinition.getPrimaryKey());

		if (!assetTags.isEmpty()) {
			_portal.addPageKeywords(
				ListUtil.toString(assetTags, AssetTag.NAME_ACCESSOR), request);
		}

		return layoutActualURL;
	}

	@Override
	public LayoutFriendlyURLComposite getLayoutFriendlyURLComposite(
			long companyId, long groupId, boolean privateLayout,
			String friendlyURL, Map<String, String[]> params,
			Map<String, Object> requestContext)
		throws PortalException {

		HttpServletRequest request = (HttpServletRequest)requestContext.get(
			"request");

		String urlTitle = friendlyURL;

		Locale locale = _portal.getLocale(request);
		String languageId = LanguageUtil.getLanguageId(locale);
		urlTitle = urlTitle.substring("/p/".length());
		long classNameId = _portal.getClassNameId(CPDefinition.class);

		CPFriendlyURLEntry cpFriendlyURLEntry =
			_cpFriendlyURLEntryLocalService.getCPFriendlyURLEntry(
				groupId, companyId, classNameId, languageId, urlTitle);

		if (!cpFriendlyURLEntry.isMain()) {
			cpFriendlyURLEntry =
				_cpFriendlyURLEntryLocalService.getCPFriendlyURLEntry(
					groupId, companyId, classNameId,
					cpFriendlyURLEntry.getPrimaryKey(), languageId, true);
		}

		CPDefinition cpDefinition = _cpDefinitionService.getCPDefinition(
			cpFriendlyURLEntry.getClassPK());

		Layout layout = getProductLayout(groupId, privateLayout, cpDefinition);

		return new LayoutFriendlyURLComposite(
			layout, getURLSeparator() + cpFriendlyURLEntry.getUrlTitle());
	}

	@Override
	public String getURLSeparator() {
		return "/p/";
	}

	protected Layout getProductLayout(
			long groupId, boolean privateLayout, CPDefinition cpDefinition)
		throws PortalException {

		String layoutUuid = _cpDefinitionService.getDisplayPage(cpDefinition);

		return _layoutLocalService.getLayoutByUuidAndGroupId(
			layoutUuid, groupId, privateLayout);
	}

	@Reference
	private AssetTagLocalService _assetTagLocalService;

	@Reference
	private CPDefinitionService _cpDefinitionService;

	@Reference
	private CPFriendlyURLEntryLocalService _cpFriendlyURLEntryLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

}