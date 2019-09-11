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
import com.liferay.osb.koroneiki.taproot.model.Team;
import com.liferay.osb.koroneiki.taproot.permission.TeamPermission;
import com.liferay.osb.koroneiki.taproot.service.TeamLocalService;
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
	immediate = true, service = {ModelPermission.class, TeamPermission.class}
)
public class TeamPermissionImpl implements ModelPermission, TeamPermission {

	public static final String RESOURCE_NAME_TEAMS =
		"com.liferay.osb.koroneiki.taproot.teams";

	@Override
	public void check(
			PermissionChecker permissionChecker, long teamId, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, teamId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, Team.class.getName(), teamId, actionId);
		}
	}

	@Override
	public void check(PermissionChecker permissionChecker, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, RESOURCE_NAME_TEAMS, 0, actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, Team team, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, team, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, Team.class.getName(), team.getTeamId(),
				actionId);
		}
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long teamId, String actionId)
		throws PortalException {

		if (contains(
				permissionChecker, _teamLocalService.getTeam(teamId),
				actionId)) {

			return true;
		}

		return false;
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long[] teamIds,
			String actionId)
		throws PortalException {

		if (ArrayUtil.isEmpty(teamIds)) {
			return false;
		}

		for (long teamId : teamIds) {
			if (!contains(permissionChecker, teamId, actionId)) {
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
			0, RESOURCE_NAME_TEAMS, RESOURCE_NAME_TEAMS, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, Team team, String actionId)
		throws PortalException {

		if (permissionChecker.hasOwnerPermission(
				team.getCompanyId(), Team.class.getName(), team.getTeamId(),
				team.getUserId(), actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			0, Team.class.getName(), team.getTeamId(), actionId);
	}

	@Override
	public String getClassName() {
		return Team.class.getName();
	}

	@Reference
	private TeamLocalService _teamLocalService;

}