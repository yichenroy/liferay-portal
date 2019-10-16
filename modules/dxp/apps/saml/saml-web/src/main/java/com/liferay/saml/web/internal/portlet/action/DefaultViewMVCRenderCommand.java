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

package com.liferay.saml.web.internal.portlet.action;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.saml.constants.SamlWebKeys;
import com.liferay.saml.persistence.model.SamlIdpSpConnection;
import com.liferay.saml.persistence.model.SamlSpIdpConnection;
import com.liferay.saml.persistence.service.SamlIdpSpConnectionLocalService;
import com.liferay.saml.persistence.service.SamlSpIdpConnectionLocalService;
import com.liferay.saml.runtime.certificate.CertificateTool;
import com.liferay.saml.runtime.configuration.SamlProviderConfigurationHelper;
import com.liferay.saml.runtime.metadata.LocalEntityManager;
import com.liferay.saml.web.internal.constants.SamlAdminPortletKeys;

import java.security.KeyStoreException;
import java.security.UnrecoverableKeyException;
import java.security.cert.X509Certificate;

import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + SamlAdminPortletKeys.SAML_ADMIN,
		"mvc.command.name=/", "mvc.command.name=/admin"
	},
	service = MVCRenderCommand.class
)
public class DefaultViewMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		renderRequest.setAttribute(
			ClassUtil.getClassName(SamlProviderConfigurationHelper.class),
			_samlProviderConfigurationHelper);

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			renderRequest);

		String tabs1 = ParamUtil.getString(
			httpServletRequest, "tabs1", "general");

		if (tabs1.equals("general")) {
			renderGeneralTab(renderRequest, renderResponse);
		}
		else if (tabs1.equals("identity-provider-connections")) {
			renderViewIdentityProviderConnections(
				renderRequest, renderResponse, httpServletRequest);
		}
		else if (tabs1.equals("service-provider-connections")) {
			renderViewServiceProviderConnections(
				renderRequest, renderResponse, httpServletRequest);
		}

		return "/admin/view.jsp";
	}

	protected void renderGeneralTab(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		String entityId = _localEntityManager.getLocalEntityId();

		renderRequest.setAttribute(SamlWebKeys.SAML_ENTITY_ID, entityId);

		if (renderRequest.getAttribute(SamlWebKeys.SAML_X509_CERTIFICATE) !=
				null) {

			return;
		}

		try {
			X509Certificate x509Certificate =
				_localEntityManager.getLocalEntityCertificate();

			if (x509Certificate != null) {
				renderRequest.setAttribute(
					SamlWebKeys.SAML_X509_CERTIFICATE, x509Certificate);
			}

			renderRequest.setAttribute(
				SamlWebKeys.SAML_CERTIFICATE_TOOL, _certificateTool);
		}
		catch (Exception e) {
			Throwable cause = _getCause(e, KeyStoreException.class);

			if (cause != null) {
				Throwable unrecoverableKeyException;

				unrecoverableKeyException = _getCause(
					cause, UnrecoverableKeyException.class);

				if (unrecoverableKeyException != null) {
					if (_log.isDebugEnabled()) {
						_log.debug(
							"Unable to get local entity certificate because " +
								"of incorrect keystore password",
							cause);
					}

					renderRequest.setAttribute(
						SamlWebKeys.SAML_KEYSTORE_PASSWORD_INCORRECT,
						Boolean.TRUE);
				}
				else {
					if (_log.isDebugEnabled()) {
						_log.debug(
							"Unable to get local entity certificate because " +
								"of keystore loading issue",
							cause);
					}

					renderRequest.setAttribute(
						SamlWebKeys.SAML_KEYSTORE_EXCEPTION, Boolean.TRUE);
				}
			}
			else {
				cause = _getCause(e, UnrecoverableKeyException.class);

				if (cause != null) {
					if (_log.isDebugEnabled()) {
						_log.debug(
							"Unable to get local entity certificate because " +
								"of incorrect key credential password",
							cause);
					}

					renderRequest.setAttribute(
						SamlWebKeys.SAML_X509_CERTIFICATE_AUTH_NEEDED,
						Boolean.TRUE);
				}
				else {
					String message =
						"Unable to get local entity certificate: " +
							e.getMessage();

					if (_log.isDebugEnabled()) {
						_log.debug(message, e);
					}
					else if (_log.isWarnEnabled()) {
						_log.warn(message);
					}
				}
			}
		}
	}

	protected void renderViewIdentityProviderConnections(
		RenderRequest renderRequest, RenderResponse renderResponse,
		HttpServletRequest httpServletRequest) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		int samlSpIdpConnectionsCount =
			_samlSpIdpConnectionLocalService.getSamlSpIdpConnectionsCount(
				themeDisplay.getCompanyId());

		PortletURL portletURL = renderResponse.createRenderURL();

		SearchContainer searchContainer = new SearchContainer(
			renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, 0,
			SearchContainer.DEFAULT_DELTA, portletURL, null, null);

		List<SamlSpIdpConnection> samlSpIdpConnections =
			_samlSpIdpConnectionLocalService.getSamlSpIdpConnections(
				themeDisplay.getCompanyId(), searchContainer.getStart(),
				searchContainer.getEnd());

		renderRequest.setAttribute(
			SamlWebKeys.SAML_SP_IDP_CONNECTIONS, samlSpIdpConnections);

		renderRequest.setAttribute(
			SamlWebKeys.SAML_SP_IDP_CONNECTIONS_COUNT,
			samlSpIdpConnectionsCount);
	}

	protected void renderViewServiceProviderConnections(
		RenderRequest renderRequest, RenderResponse renderResponse,
		HttpServletRequest httpServletRequest) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		int samlIdpSpConnectionsCount =
			_samlIdpSpConnectionLocalService.getSamlIdpSpConnectionsCount(
				themeDisplay.getCompanyId());

		PortletURL portletURL = renderResponse.createRenderURL();

		SearchContainer searchContainer = new SearchContainer(
			renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, 0,
			SearchContainer.DEFAULT_DELTA, portletURL, null, null);

		List<SamlIdpSpConnection> samlIdpSpConnections =
			_samlIdpSpConnectionLocalService.getSamlIdpSpConnections(
				themeDisplay.getCompanyId(), searchContainer.getStart(),
				searchContainer.getEnd());

		renderRequest.setAttribute(
			SamlWebKeys.SAML_IDP_SP_CONNECTIONS, samlIdpSpConnections);

		renderRequest.setAttribute(
			SamlWebKeys.SAML_IDP_SP_CONNECTIONS_COUNT,
			samlIdpSpConnectionsCount);
	}

	private Throwable _getCause(Throwable e, Class<?> exceptionType) {
		if (e == null) {
			return null;
		}

		Throwable cause = e.getCause();

		while (cause != null) {
			if (exceptionType.isInstance(cause)) {
				return cause;
			}

			cause = cause.getCause();
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DefaultViewMVCRenderCommand.class);

	@Reference
	private CertificateTool _certificateTool;

	@Reference
	private LocalEntityManager _localEntityManager;

	@Reference
	private Portal _portal;

	@Reference
	private SamlIdpSpConnectionLocalService _samlIdpSpConnectionLocalService;

	@Reference
	private SamlProviderConfigurationHelper _samlProviderConfigurationHelper;

	@Reference
	private SamlSpIdpConnectionLocalService _samlSpIdpConnectionLocalService;

}