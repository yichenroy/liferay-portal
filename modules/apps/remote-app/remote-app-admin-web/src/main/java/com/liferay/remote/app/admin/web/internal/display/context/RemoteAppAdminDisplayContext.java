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

package com.liferay.remote.app.admin.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.remote.app.service.RemoteAppEntryLocalService;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Iván Zaera Avellón
 */
public class RemoteAppAdminDisplayContext {

	public RemoteAppAdminDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse,
		RemoteAppEntryLocalService remoteAppEntryLocalService) {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
	}

	public CreationMenu getCreationMenu() {
		return CreationMenuBuilder.addDropdownItem(
			dropdownItem -> {
				PortletURL addRemoteAppEntryURL =
					_renderResponse.createRenderURL();

				addRemoteAppEntryURL.setParameter(
					"mvcRenderCommandName", "/edit_remote_app_entry");
				addRemoteAppEntryURL.setParameter("redirect", _getRedirect());

				dropdownItem.setHref(addRemoteAppEntryURL);

				dropdownItem.setLabel(_getLabel("add-remote-web-app"));
			}
		).build();
	}

	public PortletURL getCurrentPortletURL() {
		return PortletURLUtil.getCurrent(_renderRequest, _renderResponse);
	}

	private HttpServletRequest _getHttpServletRequest() {
		return PortalUtil.getHttpServletRequest(_renderRequest);
	}

	private String _getLabel(String label) {
		return LanguageUtil.get(_getHttpServletRequest(), label);
	}

	private String _getRedirect() {
		return PortalUtil.getCurrentURL(_getHttpServletRequest());
	}

	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;

}