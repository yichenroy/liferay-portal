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

package com.liferay.osb.koroneiki.scion.web.internal.portlet.action;

import com.liferay.osb.koroneiki.scion.constants.ScionPortletKeys;
import com.liferay.osb.koroneiki.scion.constants.ScionWebKeys;
import com.liferay.osb.koroneiki.scion.service.AuthenticationTokenLocalService;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PwdGenerator;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Amos Fong
 */
@Component(
	property = {
		"javax.portlet.name=" + ScionPortletKeys.AUTHENTICATION_TOKEN_MANAGER,
		"mvc.command.name=/authentication_token_manager/edit_authentication_token"
	},
	service = MVCRenderCommand.class
)
public class EditAuthenticationTokenMVCRenderCommand
	implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			return doRender(renderRequest);
		}
		catch (Exception e) {
			SessionErrors.add(renderRequest, e.getClass());

			return "/authentication_token_manager/error.jsp";
		}
	}

	protected String doRender(RenderRequest renderRequest) throws Exception {
		long authenticationTokenId = ParamUtil.getLong(
			renderRequest, "authenticationTokenId");

		if (authenticationTokenId > 0) {
			renderRequest.setAttribute(
				ScionWebKeys.AUTHENTICATION_TOKEN,
				_authenticationTokenLocalService.getAuthenticationToken(
					authenticationTokenId));
		}
		else {
			String token = PwdGenerator.getPassword(
				64, PwdGenerator.KEY1, PwdGenerator.KEY2, PwdGenerator.KEY3);

			renderRequest.setAttribute(ScionWebKeys.TOKEN, token);
		}

		return "/authentication_token_manager/edit_authentication_token.jsp";
	}

	@Reference
	private AuthenticationTokenLocalService _authenticationTokenLocalService;

}