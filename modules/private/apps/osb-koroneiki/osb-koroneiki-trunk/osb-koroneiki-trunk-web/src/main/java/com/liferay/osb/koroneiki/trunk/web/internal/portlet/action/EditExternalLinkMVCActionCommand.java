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

import com.liferay.osb.koroneiki.root.exception.ExternalLinkDomainException;
import com.liferay.osb.koroneiki.root.exception.ExternalLinkEntityIdException;
import com.liferay.osb.koroneiki.root.exception.ExternalLinkEntityNameException;
import com.liferay.osb.koroneiki.root.service.ExternalLinkService;
import com.liferay.osb.koroneiki.trunk.constants.TrunkPortletKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

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
	service = MVCActionCommand.class
)
public class EditExternalLinkMVCActionCommand extends BaseMVCActionCommand {

	protected void deleteExternalLink(ActionRequest actionRequest)
		throws PortalException {

		long externalLinkId = ParamUtil.getLong(
			actionRequest, "externalLinkId");

		_externalLinkService.deleteExternalLink(externalLinkId);
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.DELETE)) {
				deleteExternalLink(actionRequest);
			}
			else {
				updateExternalLink(actionRequest);
			}

			sendRedirect(actionRequest, actionResponse);
		}
		catch (Exception e) {
			if (e instanceof ExternalLinkDomainException ||
				e instanceof ExternalLinkEntityIdException ||
				e instanceof ExternalLinkEntityNameException) {

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter(
					"mvcRenderCommandName", "/edit_external_link");
			}
			else {
				_log.error(e, e);

				throw e;
			}
		}
	}

	protected void updateExternalLink(ActionRequest actionRequest)
		throws PortalException {

		long externalLinkId = ParamUtil.getLong(
			actionRequest, "externalLinkId");

		long classNameId = ParamUtil.getLong(actionRequest, "classNameId");
		long classPK = ParamUtil.getLong(actionRequest, "classPK");
		String domain = ParamUtil.getString(actionRequest, "domain");
		String entityName = ParamUtil.getString(actionRequest, "entityName");
		String entityId = ParamUtil.getString(actionRequest, "entityId");

		if (externalLinkId <= 0) {
			_externalLinkService.addExternalLink(
				classNameId, classPK, domain, entityName, entityId);
		}
		else {
			_externalLinkService.updateExternalLink(externalLinkId, entityId);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditExternalLinkMVCActionCommand.class);

	@Reference
	private ExternalLinkService _externalLinkService;

}