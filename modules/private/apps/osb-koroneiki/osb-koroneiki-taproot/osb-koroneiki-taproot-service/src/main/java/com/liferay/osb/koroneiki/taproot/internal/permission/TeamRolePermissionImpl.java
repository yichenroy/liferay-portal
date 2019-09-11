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

package com.liferay.osb.koroneiki.taproot.internal.permission;

import com.liferay.osb.koroneiki.root.permission.ModelPermission;
import com.liferay.osb.koroneiki.taproot.model.TeamRole;
import com.liferay.osb.koroneiki.taproot.permission.TeamRolePermission;
import com.liferay.osb.koroneiki.taproot.service.TeamRoleLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.ArrayUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Kyle Bischof
 */
@Component(
	immediate = true,
	service = {ModelPermission.class, TeamRolePermission.class}
)
public class TeamRolePermissionImpl
	implements ModelPermission, TeamRolePermission {

	public static final String RESOURCE_NAME_TEAM_ROLES =
		"com.liferay.osb.koroneiki.taproot.team.roles";

	@Override
	public void check(
			PermissionChecker permissionChecker, long teamRoleId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, teamRoleId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, TeamRole.class.getName(), teamRoleId,
				actionId);
		}
	}

	@Override
	public void check(PermissionChecker permissionChecker, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, RESOURCE_NAME_TEAM_ROLES, 0, actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, TeamRole teamRole,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, teamRole, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, TeamRole.class.getName(),
				teamRole.getTeamRoleId(), actionId);
		}
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long teamRoleId,
			String actionId)
		throws PortalException {

		if (contains(
				permissionChecker,
				_teamRoleLocalService.getTeamRole(teamRoleId), actionId)) {

			return true;
		}

		return false;
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long[] teamRoleIds,
			String actionId)
		throws PortalException {

		if (ArrayUtil.isEmpty(teamRoleIds)) {
			return false;
		}

		for (long teamRoleId : teamRoleIds) {
			if (!contains(permissionChecker, teamRoleId, actionId)) {
				return false;
			}
		}

		return true;
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, String actionId)
		throws PortalException {

		return permissionChecker.hasPermission(
			0, RESOURCE_NAME_TEAM_ROLES, RESOURCE_NAME_TEAM_ROLES, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, TeamRole teamRole,
			String actionId)
		throws PortalException {

		if (permissionChecker.hasOwnerPermission(
				teamRole.getCompanyId(), TeamRole.class.getName(),
				teamRole.getTeamRoleId(), teamRole.getUserId(), actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			0, TeamRole.class.getName(), teamRole.getTeamRoleId(), actionId);
	}

	@Override
	public String getClassName() {
		return TeamRole.class.getName();
	}

	@Reference
	private TeamRoleLocalService _teamRoleLocalService;

}