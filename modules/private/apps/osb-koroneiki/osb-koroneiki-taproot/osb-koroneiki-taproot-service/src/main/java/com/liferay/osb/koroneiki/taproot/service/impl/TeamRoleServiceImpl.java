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
import com.liferay.osb.koroneiki.taproot.model.TeamRole;
import com.liferay.osb.koroneiki.taproot.permission.TeamRolePermission;
import com.liferay.osb.koroneiki.taproot.service.base.TeamRoleServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Kyle Bischof
 */
@Component(
	property = {
		"json.web.service.context.name=koroneiki",
		"json.web.service.context.path=TeamRole"
	},
	service = AopService.class
)
public class TeamRoleServiceImpl extends TeamRoleServiceBaseImpl {

	public TeamRole addTeamRole(String name, String description, int type)
		throws PortalException {

		_teamRolePermission.check(
			getPermissionChecker(), TaprootActionKeys.ADD_TEAM_ROLE);

		return teamRoleLocalService.addTeamRole(
			getUserId(), name, description, type);
	}

	public TeamRole deleteTeamRole(long teamRoleId) throws PortalException {
		_teamRolePermission.check(
			getPermissionChecker(), teamRoleId, ActionKeys.DELETE);

		return teamRoleLocalService.deleteTeamRole(teamRoleId);
	}

	public TeamRole deleteTeamRole(String teamRoleKey) throws PortalException {
		TeamRole teamRole = teamRoleLocalService.getTeamRole(teamRoleKey);

		_teamRolePermission.check(
			getPermissionChecker(), teamRole, ActionKeys.DELETE);

		return teamRoleLocalService.deleteTeamRole(teamRole);
	}

	public TeamRole getTeamRole(long teamRoleId) throws PortalException {
		_teamRolePermission.check(
			getPermissionChecker(), teamRoleId, ActionKeys.VIEW);

		return teamRoleLocalService.getTeamRole(teamRoleId);
	}

	public TeamRole getTeamRole(String teamRoleKey) throws PortalException {
		TeamRole teamRole = teamRoleLocalService.getTeamRole(teamRoleKey);

		_teamRolePermission.check(
			getPermissionChecker(), teamRole, ActionKeys.VIEW);

		return teamRole;
	}

	public TeamRole updateTeamRole(
			long teamRoleId, String name, String description)
		throws PortalException {

		_teamRolePermission.check(
			getPermissionChecker(), teamRoleId, ActionKeys.UPDATE);

		return teamRoleLocalService.updateTeamRole(
			getUserId(), teamRoleId, name, description);
	}

	@Reference
	private TeamRolePermission _teamRolePermission;

}