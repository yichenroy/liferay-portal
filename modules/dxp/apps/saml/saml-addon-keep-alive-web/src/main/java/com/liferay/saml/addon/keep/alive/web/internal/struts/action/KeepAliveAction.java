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

package com.liferay.saml.addon.keep.alive.web.internal.struts.action;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.struts.BaseStrutsAction;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.CookieKeys;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.saml.addon.keep.alive.web.internal.constants.SamlKeepAliveConstants;
import com.liferay.saml.constants.SamlWebKeys;
import com.liferay.saml.persistence.model.SamlIdpSpConnection;
import com.liferay.saml.persistence.model.SamlIdpSpSession;
import com.liferay.saml.persistence.model.SamlIdpSsoSession;
import com.liferay.saml.persistence.service.SamlIdpSpConnectionLocalService;
import com.liferay.saml.persistence.service.SamlIdpSpSessionLocalService;
import com.liferay.saml.persistence.service.SamlIdpSsoSessionLocalService;
import com.liferay.saml.runtime.configuration.SamlProviderConfigurationHelper;

import java.io.OutputStream;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mika Koivisto
 */
@Component(
	immediate = true, property = "path=/portal/saml/keep_alive",
	service = StrutsAction.class
)
public class KeepAliveAction extends BaseStrutsAction {

	@Override
	public String execute(
			HttpServletRequest httpServletRequest, HttpServletResponse response)
		throws Exception {

		if (!_samlProviderConfigurationHelper.isEnabled()) {
			return "/common/referer_js.jsp";
		}

		if (_samlProviderConfigurationHelper.isRoleIdp()) {
			executeIdpKeepAlive(httpServletRequest, response);
		}
		else if (_samlProviderConfigurationHelper.isRoleSp()) {
			executeSpKeepAlive(httpServletRequest, response);
		}

		return null;
	}

	protected void executeIdpKeepAlive(
			HttpServletRequest httpServletRequest, HttpServletResponse response)
		throws Exception {

		response.addHeader(
			HttpHeaders.CACHE_CONTROL,
			HttpHeaders.CACHE_CONTROL_NO_CACHE_VALUE);
		response.addHeader(
			HttpHeaders.PRAGMA, HttpHeaders.PRAGMA_NO_CACHE_VALUE);

		response.setContentType(ContentTypes.TEXT_JAVASCRIPT);

		String randomString = StringUtil.randomString();
		PrintWriter printWriter = response.getWriter();

		List<String> keepAliveURLs = getSPsKeepAliveURLs(httpServletRequest);

		for (String keepAliveURL : keepAliveURLs) {
			keepAliveURL = _http.addParameter(keepAliveURL, "r", randomString);

			printWriter.write("document.write('<img alt=\"\" src=\"");
			printWriter.write(
				HtmlUtil.escapeJS(HtmlUtil.escapeHREF(keepAliveURL)));
			printWriter.write("\"/>');");
		}
	}

	protected void executeSpKeepAlive(
			HttpServletRequest httpServletRequest, HttpServletResponse response)
		throws Exception {

		response.setHeader(
			HttpHeaders.CACHE_CONTROL,
			HttpHeaders.CACHE_CONTROL_NO_CACHE_VALUE);
		response.setHeader(
			HttpHeaders.PRAGMA, HttpHeaders.PRAGMA_NO_CACHE_VALUE);

		response.setContentType(ContentTypes.IMAGE_GIF);

		OutputStream outputStream = response.getOutputStream();

		outputStream.write(Base64.decode(_BASE64_1X1_GIF));
	}

	protected List<String> getSPsKeepAliveURLs(
			HttpServletRequest httpServletRequest)
		throws Exception {

		String samlSsoSessionId = CookieKeys.getCookie(
			httpServletRequest, SamlWebKeys.SAML_SSO_SESSION_ID);

		SamlIdpSsoSession samlIdpSsoSession =
			_samlIdpSsoSessionLocalService.fetchSamlIdpSso(samlSsoSessionId);

		if (samlIdpSsoSession == null) {
			return Collections.emptyList();
		}

		List<String> keepAliveURLs = new ArrayList<>();

		String entityId = ParamUtil.getString(httpServletRequest, "entityId");

		List<SamlIdpSpSession> samlIdpSpSessions =
			_samlIdpSpSessionLocalService.getSamlIdpSpSessions(
				samlIdpSsoSession.getSamlIdpSsoSessionId());

		for (SamlIdpSpSession samlIdpSpSession : samlIdpSpSessions) {
			if (entityId.equals(samlIdpSpSession.getSamlSpEntityId())) {
				continue;
			}

			SamlIdpSpConnection samlIdpSpConnection =
				_samlIdpSpConnectionLocalService.getSamlIdpSpConnection(
					samlIdpSpSession.getCompanyId(),
					samlIdpSpSession.getSamlSpEntityId());

			ExpandoBridge expandoBridge =
				samlIdpSpConnection.getExpandoBridge();

			String keepAliveURL = (String)expandoBridge.getAttribute(
				SamlKeepAliveConstants.EXPANDO_COLUMN_NAME_KEEP_ALIVE_URL);

			if (!Validator.isBlank(keepAliveURL) &&
				!keepAliveURL.equals(
					SamlKeepAliveConstants.
						EXPANDO_COLUMN_NAME_KEEP_ALIVE_URL)) {

				keepAliveURLs.add(keepAliveURL);
			}
		}

		return keepAliveURLs;
	}

	private static final String _BASE64_1X1_GIF =
		"R0lGODdhAQABAIAAAP///////ywAAAAAAQABAAACAkQBADs=";

	@Reference
	private Http _http;

	@Reference
	private SamlIdpSpConnectionLocalService _samlIdpSpConnectionLocalService;

	@Reference
	private SamlIdpSpSessionLocalService _samlIdpSpSessionLocalService;

	@Reference
	private SamlIdpSsoSessionLocalService _samlIdpSsoSessionLocalService;

	@Reference
	private SamlProviderConfigurationHelper _samlProviderConfigurationHelper;

}