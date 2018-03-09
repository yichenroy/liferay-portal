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

package com.liferay.portal.workflow.kaleo.designer.web.internal.portlet.configuration.icon;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.portlet.configuration.icon.PortletConfigurationIcon;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.workflow.kaleo.designer.web.constants.KaleoDesignerPortletKeys;
import com.liferay.portal.workflow.kaleo.designer.web.internal.constants.KaleoDesignerWebKeys;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinition;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;

import java.util.ResourceBundle;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Defines the icon triggering duplication of the workflow definition.
 *
 * @author Jeyvison Nascimento
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + KaleoDesignerPortletKeys.KALEO_DESIGNER,
		"path=/designer/edit_kaleo_definition_version.jsp"
	},
	service = PortletConfigurationIcon.class
)
public class DuplicateDefinitionPortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	@Override
	public String getMessage(PortletRequest portletRequest) {
		ResourceBundle resourceBundle =
			_resourceBundleLoader.loadResourceBundle(getLocale(portletRequest));

		return LanguageUtil.get(resourceBundle, "duplicate");
	}

	@Override
	public String getOnClick(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		String portletId = _portal.getPortletId(portletRequest);

		String portletNamespace = _portal.getPortletNamespace(portletId);

		return "Liferay.fire('" + portletNamespace + "duplicateDefinition');";
	}

	@Override
	public String getURL(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		return "javascript:;";
	}

	@Override
	public boolean isShow(PortletRequest portletRequest) {
		KaleoDefinitionVersion kaleoDefinitionVersion =
			(KaleoDefinitionVersion)portletRequest.getAttribute(
				KaleoDesignerWebKeys.KALEO_DRAFT_DEFINITION);

		KaleoDefinition kaleoDefinition = _getKaleoDefinition(
			kaleoDefinitionVersion);

		if ((kaleoDefinition != null) && kaleoDefinition.isActive()) {
			return true;
		}

		return false;
	}

	@Reference(
		target = "(bundle.symbolic.name=com.liferay.portal.workflow.web)",
		unbind = "-"
	)
	protected void setResourceBundleLoader(
		ResourceBundleLoader resourceBundleLoader) {

		_resourceBundleLoader = resourceBundleLoader;
	}

	private KaleoDefinition _getKaleoDefinition(
		KaleoDefinitionVersion kaleoDefinitionVersion) {

		try {
			if (kaleoDefinitionVersion != null) {
				return kaleoDefinitionVersion.getKaleoDefinition();
			}
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DuplicateDefinitionPortletConfigurationIcon.class);

	@Reference
	private Portal _portal;

	private ResourceBundleLoader _resourceBundleLoader;

}