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

package com.liferay.portlet.internal;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.struts.StrutsActionPortletURL;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.PortletResponseImpl;
import com.liferay.portlet.PortletURLImpl;

import java.lang.reflect.Constructor;

import java.security.PrivilegedAction;

import java.util.Map;

import javax.portlet.MimeResponse;
import javax.portlet.PortletModeException;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.WindowStateException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 * @author Neil Griffin
 */
public class LiferayPortletURLPrivilegedAction
	implements PrivilegedAction<LiferayPortletURL> {

	public LiferayPortletURLPrivilegedAction(
		long plid, String portletName, String lifecycle, MimeResponse.Copy copy,
		boolean includeLinkToLayoutUuid, Layout layout, Portlet portlet,
		PortletPreferences portletPreferences, PortletRequest portletRequest,
		PortletResponseImpl portletResponseImpl, long requestPlid,
		Map<String, Constructor<? extends PortletURLImpl>> constructors) {

		_plid = plid;
		_portletName = portletName;
		_lifecycle = lifecycle;
		_copy = copy;
		_includeLinkToLayoutUuid = includeLinkToLayoutUuid;
		_layout = layout;
		_portlet = portlet;
		_portletPreferences = portletPreferences;
		_portletRequest = portletRequest;
		_portletResponseImpl = portletResponseImpl;

		_request = null;
		_requestPlid = requestPlid;
		_constructors = constructors;
	}

	public LiferayPortletURLPrivilegedAction(
		String portletName, String lifecycle, MimeResponse.Copy copy,
		Layout layout, Portlet portlet, HttpServletRequest request) {

		_portletName = portletName;
		_lifecycle = lifecycle;
		_copy = copy;
		_layout = layout;
		_portlet = portlet;
		_request = request;

		_constructors = null;
		_includeLinkToLayoutUuid = false;
		_plid = 0;
		_portletPreferences = null;
		_portletRequest = null;
		_portletResponseImpl = null;
		_requestPlid = 0;
	}

	@Override
	public LiferayPortletURL run() {
		if (_request != null) {
			return PortletURLFactoryUtil.create(
				_request, _portlet, _layout, _lifecycle, _copy);
		}

		boolean includeLinkToLayoutUuid = _includeLinkToLayoutUuid;
		long plid = _plid;

		try {
			String linkToLayoutUuid = GetterUtil.getString(
				_portletPreferences.getValue(
					"portletSetupLinkToLayoutUuid", null));

			if (PropsValues.PORTLET_CROSS_LAYOUT_INVOCATION_MODE.equals(
					"render") &&
				!PortletRequest.RENDER_PHASE.equals(_lifecycle)) {

				includeLinkToLayoutUuid = false;
			}

			if (Validator.isNotNull(linkToLayoutUuid) &&
				includeLinkToLayoutUuid) {

				try {
					Layout linkedLayout =
						LayoutLocalServiceUtil.getLayoutByUuidAndGroupId(
							linkToLayoutUuid, _layout.getGroupId(),
							_layout.isPrivateLayout());

					plid = linkedLayout.getPlid();
				}
				catch (PortalException pe) {

					// LPS-52675

					if (_log.isDebugEnabled()) {
						_log.debug(pe, pe);
					}
				}
			}
		}
		catch (SystemException se) {
			if (_log.isWarnEnabled()) {
				_log.warn(se);
			}
		}

		if (plid == LayoutConstants.DEFAULT_PLID) {
			plid = _requestPlid;
		}

		LiferayPortletURL portletURL = null;

		String portletURLClass = _portlet.getPortletURLClass();

		String portletId = _portlet.getPortletId();

		if (portletId.equals(_portletName) &&
			Validator.isNotNull(portletURLClass)) {

			if (portletURLClass.equals(
					StrutsActionPortletURL.class.getName())) {

				portletURL = new StrutsActionPortletURL(
					_portletResponseImpl, plid, _lifecycle);
			}
			else {
				try {
					Constructor<? extends PortletURLImpl> constructor =
						_constructors.get(portletURLClass);

					if (constructor == null) {
						Class<?> portletURLClassObj = Class.forName(
							portletURLClass);

						constructor = (Constructor<? extends PortletURLImpl>)
							portletURLClassObj.getConstructor(
								new Class<?>[] {
									PortletResponseImpl.class, long.class,
									String.class
								});

						_constructors.put(portletURLClass, constructor);
					}

					portletURL = constructor.newInstance(
						new Object[] {_portletResponseImpl, plid, _lifecycle});
				}
				catch (Exception e) {
					_log.error("Unable to create portlet URL", e);
				}
			}
		}

		if (portletURL == null) {
			if (_portletName.equals(portletId)) {
				portletURL = PortletURLFactoryUtil.create(
					_portletRequest, _portlet, plid, _lifecycle, _copy);
			}
			else {
				portletURL = PortletURLFactoryUtil.create(
					_portletRequest, _portletName, plid, _lifecycle, _copy);
			}
		}

		try {
			if (_portlet.hasWindowState(
					_portletRequest.getResponseContentType(),
					_portletRequest.getWindowState())) {

				portletURL.setWindowState(_portletRequest.getWindowState());
			}
		}
		catch (WindowStateException wse) {
			_log.error(wse.getMessage());
		}

		try {
			if (_portlet.hasPortletMode(
					_portletRequest.getResponseContentType(),
					_portletRequest.getPortletMode())) {

				portletURL.setPortletMode(_portletRequest.getPortletMode());
			}
		}
		catch (PortletModeException pme) {
			_log.error(pme.getMessage());
		}

		return portletURL;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LiferayPortletURLPrivilegedAction.class);

	private final Map<String, Constructor<? extends PortletURLImpl>>
		_constructors;
	private final MimeResponse.Copy _copy;
	private final boolean _includeLinkToLayoutUuid;
	private final Layout _layout;
	private final String _lifecycle;
	private final long _plid;
	private final Portlet _portlet;
	private final String _portletName;
	private final PortletPreferences _portletPreferences;
	private final PortletRequest _portletRequest;
	private final PortletResponseImpl _portletResponseImpl;
	private final HttpServletRequest _request;
	private final long _requestPlid;

}