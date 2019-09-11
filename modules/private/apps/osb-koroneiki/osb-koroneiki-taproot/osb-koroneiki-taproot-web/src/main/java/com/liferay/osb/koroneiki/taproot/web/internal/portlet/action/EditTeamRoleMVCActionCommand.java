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
import com.liferay.osb.koroneiki.taproot.exception.NoSuchTeamRoleException;
import com.liferay.osb.koroneiki.taproot.exception.TeamRoleNameException;
import com.liferay.osb.koroneiki.taproot.exception.TeamRoleTypeException;
import com.liferay.osb.koroneiki.taproot.service.TeamRoleService;
import com.liferay.portal.kernel.exception.PortalException;
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
 * @author Kyle Bischof
 */
@Component(
	property = {
		"javax.portlet.name=" + TaprootPortletKeys.TEAM_ROLES_ADMIN,
		"mvc.command.name=/team_roles_admin/edit_team_role"
	},
	service = MVCActionCommand.class
)
public class EditTeamRoleMVCActionCommand extends BaseMVCActionCommand {

	protected void deleteTeamRole(ActionRequest actionRequest)
		throws PortalException {

		long teamRoleId = ParamUtil.getLong(actionRequest, "teamRoleId");

		_teamRoleService.deleteTeamRole(teamRoleId);
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.DELETE)) {
				deleteTeamRole(actionRequest);
			}
			else {
				updateTeamRole(actionRequest);
			}

			sendRedirect(actionRequest, actionResponse);
		}
		catch (Exception e) {
			if (e instanceof NoSuchTeamRoleException ||
				e instanceof TeamRoleNameException ||
				e instanceof TeamRoleTypeException) {

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter(
					"mvcRenderCommandName", "/team_roles_admin/edit_team_role");
			}
			else {
				throw e;
			}
		}
	}

	protected void updateTeamRole(ActionRequest actionRequest)
		throws PortalException {

		long teamRoleId = ParamUtil.getLong(actionRequest, "teamRoleId");

		String name = ParamUtil.getString(actionRequest, "name");
		String description = ParamUtil.getString(actionRequest, "description");
		int type = ParamUtil.getInteger(actionRequest, "type");

		if (teamRoleId <= 0) {
			_teamRoleService.addTeamRole(name, description, type);
		}
		else {
			_teamRoleService.updateTeamRole(teamRoleId, name, description);
		}
	}

	@Reference
	private TeamRoleService _teamRoleService;

}