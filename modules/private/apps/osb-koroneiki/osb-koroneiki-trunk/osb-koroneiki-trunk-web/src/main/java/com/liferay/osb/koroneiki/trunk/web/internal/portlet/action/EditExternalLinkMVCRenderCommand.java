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

package com.liferay.osb.koroneiki.trunk.web.internal.portlet.action;

import com.liferay.osb.koroneiki.root.constants.RootWebKeys;
import com.liferay.osb.koroneiki.root.service.ExternalLinkLocalService;
import com.liferay.osb.koroneiki.trunk.constants.TrunkPortletKeys;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;

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
		"javax.portlet.name=" + TrunkPortletKeys.PRODUCTS_ADMIN,
		"mvc.command.name=/edit_external_link"
	},
	service = MVCRenderCommand.class
)
public class EditExternalLinkMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			long externalLinkId = ParamUtil.getLong(
				renderRequest, "externalLinkId");

			if (externalLinkId > 0) {
				renderRequest.setAttribute(
					RootWebKeys.EXTERNAL_LINK,
					_externalLinkLocalService.getExternalLink(externalLinkId));
			}

			return "/edit_external_link.jsp";
		}
		catch (Exception e) {
			SessionErrors.add(renderRequest, e.getClass());

			throw new PortletException(e);
		}
	}

	@Reference
	private ExternalLinkLocalService _externalLinkLocalService;

}