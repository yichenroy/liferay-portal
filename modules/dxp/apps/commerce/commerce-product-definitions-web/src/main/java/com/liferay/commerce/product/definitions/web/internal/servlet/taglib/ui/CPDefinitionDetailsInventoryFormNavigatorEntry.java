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

package com.liferay.commerce.product.definitions.web.internal.servlet.taglib.ui;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.servlet.taglib.ui.BaseJSPFormNavigatorEntry;
import com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorEntry;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = "form.navigator.entry.order:Integer=60",
	service = FormNavigatorEntry.class
)
public class CPDefinitionDetailsInventoryFormNavigatorEntry
	extends BaseJSPFormNavigatorEntry<CPDefinition> {

	@Override
	public String getCategoryKey() {
		return CPDefinitionFormNavigatorConstants.
			CATEGORY_KEY_COMMERCE_PRODUCT_DETAILS;
	}

	@Override
	public String getFormNavigatorId() {
		return CPDefinitionFormNavigatorConstants.
			FORM_NAVIGATOR_ID_COMMERCE_PRODUCT_DEFINITION;
	}

	@Override
	public String getKey() {
		return "inventory";
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(resourceBundle, "inventory");
	}

	@Override
	protected String getJspPath() {
		return "/definition/inventory.jsp";
	}

}