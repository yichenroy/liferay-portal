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

package com.liferay.osb.koroneiki.taproot.service.impl;

import com.liferay.osb.koroneiki.taproot.constants.TaprootActionKeys;
import com.liferay.osb.koroneiki.taproot.model.Account;
import com.liferay.osb.koroneiki.taproot.model.Team;
import com.liferay.osb.koroneiki.taproot.model.TeamAccountRole;
import com.liferay.osb.koroneiki.taproot.model.TeamRole;
import com.liferay.osb.koroneiki.taproot.permission.AccountPermission;
import com.liferay.osb.koroneiki.taproot.service.AccountLocalService;
import com.liferay.osb.koroneiki.taproot.service.TeamLocalService;
import com.liferay.osb.koroneiki.taproot.service.TeamRoleLocalService;
import com.liferay.osb.koroneiki.taproot.service.base.TeamAccountRoleServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Kyle Bischof
 */
@Component(
	property = {
		"json.web.service.context.name=koroneiki",
		"json.web.service.context.path=TeamAccountRole"
	},
	service = AopService.class
)
public class TeamAccountRoleServiceImpl extends TeamAccountRoleServiceBaseImpl {

	public TeamAccountRole addTeamAccountRole(
			long teamId, long accountId, long teamRoleId)
		throws PortalException {

		_accountPermission.check(
			getPermissionChecker(), accountId, TaprootActionKeys.ASSIGN_TEAM);

		return teamAccountRoleLocalService.addTeamAccountRole(
			teamId, accountId, teamRoleId);
	}

	public TeamAccountRole addTeamAccountRole(
			String teamKey, String accountKey, String teamRoleKey)
		throws PortalException {

		Team team = _teamLocalService.getTeam(teamKey);
		Account account = _accountLocalService.getAccount(accountKey);
		TeamRole teamRole = _teamRoleLocalService.getTeamRole(teamRoleKey);

		_accountPermission.check(
			getPermissionChecker(), account, TaprootActionKeys.ASSIGN_TEAM);

		return teamAccountRoleLocalService.addTeamAccountRole(
			team.getTeamId(), account.getAccountId(), teamRole.getTeamRoleId());
	}

	public TeamAccountRole deleteTeamAccountRole(
			long teamId, long accountId, long teamRoleId)
		throws PortalException {

		_accountPermission.check(
			getPermissionChecker(), accountId, TaprootActionKeys.ASSIGN_TEAM);

		return teamAccountRoleLocalService.deleteTeamAccountRole(
			teamId, accountId, teamRoleId);
	}

	public TeamAccountRole deleteTeamAccountRole(
			String teamKey, String accountKey, String teamRoleKey)
		throws PortalException {

		Team team = _teamLocalService.getTeam(teamKey);
		Account account = _accountLocalService.getAccount(accountKey);
		TeamRole teamRole = _teamRoleLocalService.getTeamRole(teamRoleKey);

		_accountPermission.check(
			getPermissionChecker(), account, TaprootActionKeys.ASSIGN_TEAM);

		return teamAccountRoleLocalService.deleteTeamAccountRole(
			team.getTeamId(), account.getAccountId(), teamRole.getTeamRoleId());
	}

	public void deleteTeamAccountRoles(long teamId, long accountId)
		throws PortalException {

		_accountPermission.check(
			getPermissionChecker(), accountId, TaprootActionKeys.ASSIGN_TEAM);

		teamAccountRoleLocalService.deleteTeamAccountRoles(teamId, accountId);
	}

	@Reference
	private AccountLocalService _accountLocalService;

	@Reference
	private AccountPermission _accountPermission;

	@Reference
	private TeamLocalService _teamLocalService;

	@Reference
	private TeamRoleLocalService _teamRoleLocalService;

}