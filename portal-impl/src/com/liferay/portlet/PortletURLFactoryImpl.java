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

package com.liferay.portlet;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.PortletURLFactory;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.internal.ActionURLImpl;
import com.liferay.portlet.internal.RenderURLImpl;

import javax.portlet.MimeResponse;
import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 * @author Neil Griffin
 */
@DoPrivileged
@ProviderType
public class PortletURLFactoryImpl implements PortletURLFactory {

	@Override
	public LiferayPortletURL create(
		HttpServletRequest request, Portlet portlet, Layout layout,
		String lifecycle) {

		return _create(request, portlet, layout, lifecycle, null);
	}

	@Override
	public LiferayPortletURL create(
		HttpServletRequest request, Portlet portlet, Layout layout,
		String lifecycle, MimeResponse.Copy copy) {

		return _create(request, portlet, layout, lifecycle, copy);
	}

	@Override
	public LiferayPortletURL create(
		HttpServletRequest request, Portlet portlet, String lifecycle) {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		if (layout == null) {
			layout = _getLayout(
				(Layout)request.getAttribute(WebKeys.LAYOUT),
				themeDisplay.getPlid());
		}

		return _create(request, portlet, layout, lifecycle, null);
	}

	@Override
	public LiferayPortletURL create(
		HttpServletRequest request, String portletId, Layout layout,
		String lifecycle) {

		return _create(
			request,
			PortletLocalServiceUtil.getPortletById(
				PortalUtil.getCompanyId(request), portletId),
			layout, lifecycle, null);
	}

	@Override
	public LiferayPortletURL create(
		HttpServletRequest request, String portletId, long plid,
		String lifecycle) {

		return _create(
			request,
			PortletLocalServiceUtil.getPortletById(
				PortalUtil.getCompanyId(request), portletId),
			_getLayout((Layout)request.getAttribute(WebKeys.LAYOUT), plid),
			lifecycle, null);
	}

	@Override
	public LiferayPortletURL create(
		HttpServletRequest request, String portletId, String lifecycle) {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		if (layout == null) {
			layout = _getLayout(
				(Layout)request.getAttribute(WebKeys.LAYOUT),
				themeDisplay.getPlid());
		}

		return _create(
			request,
			PortletLocalServiceUtil.getPortletById(
				PortalUtil.getCompanyId(request), portletId),
			layout, lifecycle, null);
	}

	@Override
	public LiferayPortletURL create(
		PortletRequest portletRequest, Portlet portlet, Layout layout,
		String lifecycle) {

		return _create(portletRequest, portlet, layout, lifecycle, null);
	}

	@Override
	public LiferayPortletURL create(
		PortletRequest portletRequest, Portlet portlet, long plid,
		String lifecycle) {

		return _create(
			portletRequest, portlet,
			_getLayout(
				(Layout)portletRequest.getAttribute(WebKeys.LAYOUT), plid),
			lifecycle, null);
	}

	@Override
	public LiferayPortletURL create(
		PortletRequest portletRequest, Portlet portlet, long plid,
		String lifecycle, MimeResponse.Copy copy) {

		return _create(
			portletRequest, portlet,
			_getLayout(
				(Layout)portletRequest.getAttribute(WebKeys.LAYOUT), plid),
			lifecycle, copy);
	}

	@Override
	public LiferayPortletURL create(
		PortletRequest portletRequest, String portletId, Layout layout,
		String lifecycle) {

		return _create(
			portletRequest,
			PortletLocalServiceUtil.getPortletById(
				PortalUtil.getCompanyId(portletRequest), portletId),
			layout, lifecycle, null);
	}

	@Override
	public LiferayPortletURL create(
		PortletRequest portletRequest, String portletId, long plid,
		String lifecycle) {

		return _create(
			portletRequest, portletId,
			_getLayout(
				(Layout)portletRequest.getAttribute(WebKeys.LAYOUT), plid),
			lifecycle, null);
	}

	@Override
	public LiferayPortletURL create(
		PortletRequest portletRequest, String portletId, long plid,
		String lifecycle, MimeResponse.Copy copy) {

		return _create(
			portletRequest, portletId,
			_getLayout(
				(Layout)portletRequest.getAttribute(WebKeys.LAYOUT), plid),
			lifecycle, copy);
	}

	@Override
	public LiferayPortletURL create(
		PortletRequest portletRequest, String portletId, String lifecycle) {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		if (layout == null) {
			layout = _getLayout(
				(Layout)portletRequest.getAttribute(WebKeys.LAYOUT),
				themeDisplay.getPlid());
		}

		return _create(portletRequest, portletId, layout, lifecycle, null);
	}

	private LiferayPortletURL _create(
		HttpServletRequest request, Portlet portlet, Layout layout,
		String lifecycle, MimeResponse.Copy copy) {

		if (PortletRequest.ACTION_PHASE.equals(lifecycle)) {
			return new ActionURLImpl(request, portlet, layout, lifecycle, copy);
		}
		else if (PortletRequest.RENDER_PHASE.equals(lifecycle)) {
			return new RenderURLImpl(request, portlet, layout, lifecycle, copy);
		}

		return new PortletURLImpl(request, portlet, layout, lifecycle, copy);
	}

	private LiferayPortletURL _create(
		PortletRequest portletRequest, Portlet portlet, Layout layout,
		String lifecycle, MimeResponse.Copy copy) {

		if (PortletRequest.ACTION_PHASE.equals(lifecycle)) {
			return new ActionURLImpl(
				portletRequest, portlet, layout, lifecycle, copy);
		}
		else if (PortletRequest.RENDER_PHASE.equals(lifecycle)) {
			return new RenderURLImpl(
				portletRequest, portlet, layout, lifecycle, copy);
		}

		return new PortletURLImpl(
			portletRequest, portlet, layout, lifecycle, copy);
	}

	private LiferayPortletURL _create(
		PortletRequest portletRequest, String portletId, Layout layout,
		String lifecycle, MimeResponse.Copy copy) {

		return _create(
			portletRequest,
			PortletLocalServiceUtil.getPortletById(
				PortalUtil.getCompanyId(portletRequest), portletId),
			layout, lifecycle, copy);
	}

	private Layout _getLayout(Layout layout, long plid) {
		if ((layout != null) && (layout.getPlid() == plid)) {
			return layout;
		}

		if (plid > 0) {
			return LayoutLocalServiceUtil.fetchLayout(plid);
		}

		return null;
	}

}