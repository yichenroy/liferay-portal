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
import com.liferay.osb.koroneiki.taproot.service.TeamAccountRoleService;
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
		"javax.portlet.name=" + TaprootPortletKeys.ACCOUNTS_ADMIN,
		"mvc.command.name=/accounts_admin/assign_account_team_roles"
	},
	service = MVCActionCommand.class
)
public class AssignAccountTeamRolesMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			long teamId = ParamUtil.getLong(actionRequest, "teamId");
			long accountId = ParamUtil.getLong(actionRequest, "accountId");
			long[] addTeamRoleIds = ParamUtil.getLongValues(
				actionRequest, "addTeamRoleIds");
			long[] deleteTeamRoleIds = ParamUtil.getLongValues(
				actionRequest, "deleteTeamRoleIds");

			for (long teamRoleId : addTeamRoleIds) {
				_teamAccountRoleService.addTeamAccountRole(
					teamId, accountId, teamRoleId);
			}

			for (long teamRoleId : deleteTeamRoleIds) {
				_teamAccountRoleService.deleteTeamAccountRole(
					teamId, accountId, teamRoleId);
			}

			sendRedirect(actionRequest, actionResponse);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw e;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssignAccountTeamRolesMVCActionCommand.class);

	@Reference
	private TeamAccountRoleService _teamAccountRoleService;

}