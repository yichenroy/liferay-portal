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

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.saml.constants.SamlWebKeys;
import com.liferay.saml.persistence.model.SamlIdpSpConnection;
import com.liferay.saml.persistence.service.SamlIdpSpConnectionLocalService;
import com.liferay.saml.runtime.configuration.SamlProviderConfiguration;
import com.liferay.saml.runtime.configuration.SamlProviderConfigurationHelper;
import com.liferay.saml.web.internal.constants.SamlAdminPortletKeys;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + SamlAdminPortletKeys.SAML_ADMIN,
		"mvc.command.name=/admin/edit_service_provider_connection"
	},
	service = MVCRenderCommand.class
)
public class EditServiceProviderConnectionMVCRenderCommand
	implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		long samlIdpSpConnectionId = ParamUtil.getLong(
			renderRequest, "samlIdpSpConnectionId");

		renderRequest.setAttribute(
			ClassUtil.getClassName(SamlProviderConfigurationHelper.class),
			_samlProviderConfigurationHelper);

		SamlProviderConfiguration samlProviderConfiguration =
			_samlProviderConfigurationHelper.getSamlProviderConfiguration();

		int assertionLifetime = ParamUtil.getInteger(
			renderRequest, "assertionLifetime",
			samlProviderConfiguration.defaultAssertionLifetime());

		if (samlIdpSpConnectionId > 0) {
			SamlIdpSpConnection samlIdpSpConnection =
				_samlIdpSpConnectionLocalService.fetchSamlIdpSpConnection(
					samlIdpSpConnectionId);

			if (samlIdpSpConnection != null) {
				assertionLifetime = ParamUtil.getInteger(
					renderRequest, "assertionLifetime",
					samlIdpSpConnection.getAssertionLifetime());

				renderRequest.setAttribute(
					SamlWebKeys.SAML_IDP_SP_CONNECTION, samlIdpSpConnection);
			}
		}

		renderRequest.setAttribute(
			SamlWebKeys.SAML_ASSERTION_LIFETIME, assertionLifetime);

		return "/admin/edit_service_provider_connection.jsp";
	}

	@Reference
	private Portal _portal;

	@Reference
	private SamlIdpSpConnectionLocalService _samlIdpSpConnectionLocalService;

	@Reference
	private SamlProviderConfigurationHelper _samlProviderConfigurationHelper;

}