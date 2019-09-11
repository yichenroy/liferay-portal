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

package com.liferay.osb.koroneiki.taproot.web.internal.portlet.action;

import com.liferay.osb.koroneiki.taproot.constants.TaprootPortletKeys;
import com.liferay.osb.koroneiki.taproot.service.ContactTeamRoleService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Kyle Bischof
 */
@Component(
	property = {
		"javax.portlet.name=" + TaprootPortletKeys.TEAMS_ADMIN,
		"mvc.command.name=/teams_admin/assign_team_contact_roles"
	},
	service = MVCActionCommand.class
)
public class AssignTeamContactRolesMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			long contactId = ParamUtil.getLong(actionRequest, "contactId");
			long teamId = ParamUtil.getLong(actionRequest, "teamId");
			long[] addContactRoleIds = ParamUtil.getLongValues(
				actionRequest, "addContactRoleIds");
			long[] deleteContactRoleIds = ParamUtil.getLongValues(
				actionRequest, "deleteContactRoleIds");

			for (long contactRoleId : addContactRoleIds) {
				_contactTeamRoleService.addContactTeamRole(
					contactId, teamId, contactRoleId);
			}

			for (long contactRoleId : deleteContactRoleIds) {
				_contactTeamRoleService.deleteContactTeamRole(
					contactId, teamId, contactRoleId);
			}

			sendRedirect(actionRequest, actionResponse);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw e;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssignTeamContactRolesMVCActionCommand.class);

	@Reference
	private ContactTeamRoleService _contactTeamRoleService;

}