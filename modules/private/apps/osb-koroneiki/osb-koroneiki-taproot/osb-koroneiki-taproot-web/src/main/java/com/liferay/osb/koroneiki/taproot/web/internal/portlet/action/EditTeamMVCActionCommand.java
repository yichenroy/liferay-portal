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
import com.liferay.osb.koroneiki.taproot.exception.NoSuchTeamException;
import com.liferay.osb.koroneiki.taproot.exception.TeamNameException;
import com.liferay.osb.koroneiki.taproot.service.TeamService;
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
		"javax.portlet.name=" + TaprootPortletKeys.TEAMS_ADMIN,
		"mvc.command.name=/teams_admin/edit_team"
	},
	service = MVCActionCommand.class
)
public class EditTeamMVCActionCommand extends BaseMVCActionCommand {

	protected void deleteTeam(ActionRequest actionRequest)
		throws PortalException {

		long teamId = ParamUtil.getLong(actionRequest, "teamId");

		_teamService.deleteTeam(teamId);
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.DELETE)) {
				deleteTeam(actionRequest);
			}
			else {
				updateTeam(actionRequest);
			}

			sendRedirect(actionRequest, actionResponse);
		}
		catch (Exception e) {
			if (e instanceof NoSuchTeamException ||
				e instanceof TeamNameException) {

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter(
					"mvcRenderCommandName", "/teams_admin/edit_team");
			}
			else {
				throw e;
			}
		}
	}

	protected void updateTeam(ActionRequest actionRequest)
		throws PortalException {

		long teamId = ParamUtil.getLong(actionRequest, "teamId");

		long accountId = ParamUtil.getLong(actionRequest, "accountId");
		String name = ParamUtil.getString(actionRequest, "name");

		if (teamId <= 0) {
			_teamService.addTeam(accountId, name);
		}
		else {
			_teamService.updateTeam(teamId, name);
		}
	}

	@Reference
	private TeamService _teamService;

}