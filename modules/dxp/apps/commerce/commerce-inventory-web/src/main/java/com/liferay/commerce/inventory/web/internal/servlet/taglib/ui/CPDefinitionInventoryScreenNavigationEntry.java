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

package com.liferay.commerce.inventory.web.internal.servlet.taglib.ui;

import com.liferay.commerce.inventory.CommerceInventoryEngineRegistry;
import com.liferay.commerce.inventory.web.internal.display.context.CommerceInventoryDisplayContext;
import com.liferay.commerce.inventory.web.internal.portlet.action.CommerceInventoryActionHelper;
import com.liferay.commerce.product.definitions.web.portlet.action.ActionHelper;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.service.CPDefinitionAvailabilityRangeService;
import com.liferay.commerce.service.CommerceAvailabilityRangeService;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationCategory;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = {
		"screen.navigation.category.order:Integer=25",
		"screen.navigation.entry.order:Integer=25"
	},
	service = {ScreenNavigationCategory.class, ScreenNavigationEntry.class}
)
public class CPDefinitionInventoryScreenNavigationEntry
	implements ScreenNavigationCategory, ScreenNavigationEntry<CPDefinition> {

	@Override
	public String getCategoryKey() {
		return "inventory";
	}

	@Override
	public String getEntryKey() {
		return "inventory";
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(resourceBundle, "inventory");
	}

	@Override
	public String getScreenNavigationKey() {
		return "cp.definition.general";
	}

	@Override
	public boolean isVisible(User user, CPDefinition cpDefinition) {
		if (cpDefinition == null) {
			return false;
		}

		return true;
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		try {
			CommerceInventoryDisplayContext commerceInventoryDisplayContext =
				new CommerceInventoryDisplayContext(
					_actionHelper, httpServletRequest,
					_commerceAvailabilityRangeService,
					_commerceInventoryActionHelper,
					_commerceInventoryEngineRegistry,
					_cpDefinitionAvailabilityRangeService);

			httpServletRequest.setAttribute(
				WebKeys.PORTLET_DISPLAY_CONTEXT,
				commerceInventoryDisplayContext);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		_jspRenderer.renderJSP(
			_setServletContext, httpServletRequest, httpServletResponse,
			"/edit_definition_inventory.jsp");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CPDefinitionInventoryScreenNavigationEntry.class);

	@Reference
	private ActionHelper _actionHelper;

	@Reference
	private CommerceAvailabilityRangeService _commerceAvailabilityRangeService;

	@Reference
	private CommerceInventoryActionHelper _commerceInventoryActionHelper;

	@Reference
	private CommerceInventoryEngineRegistry _commerceInventoryEngineRegistry;

	@Reference
	private CPDefinitionAvailabilityRangeService
		_cpDefinitionAvailabilityRangeService;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.inventory.web)"
	)
	private ServletContext _setServletContext;

}