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

package com.liferay.osb.koroneiki.phloem.rest.internal.resource.v1_0;

import com.liferay.osb.koroneiki.phloem.rest.dto.v1_0.TeamRole;
import com.liferay.osb.koroneiki.phloem.rest.dto.v1_0.util.TeamRoleUtil;
import com.liferay.osb.koroneiki.phloem.rest.resource.v1_0.TeamRoleResource;
import com.liferay.osb.koroneiki.taproot.constants.TeamRoleType;
import com.liferay.osb.koroneiki.taproot.service.TeamRoleLocalService;
import com.liferay.osb.koroneiki.taproot.service.TeamRoleService;
import com.liferay.portal.kernel.util.GetterUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Amos Fong
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/team-role.properties",
	scope = ServiceScope.PROTOTYPE, service = TeamRoleResource.class
)
public class TeamRoleResourceImpl extends BaseTeamRoleResourceImpl {

	@Override
	public void deleteTeamRole(String teamRoleKey) throws Exception {
		_teamRoleService.deleteTeamRole(teamRoleKey);
	}

	@Override
	public TeamRole getTeamRole(String teamRoleKey) throws Exception {
		return TeamRoleUtil.toTeamRole(
			_teamRoleService.getTeamRole(teamRoleKey));
	}

	@Override
	public TeamRole postTeamRole(TeamRole teamRole) throws Exception {
		TeamRole.Type teamRoleType = teamRole.getType();

		int type = TeamRoleType.fromLabel(teamRoleType.toString());

		return TeamRoleUtil.toTeamRole(
			_teamRoleService.addTeamRole(
				teamRole.getName(), teamRole.getDescription(), type));
	}

	@Override
	public TeamRole putTeamRole(String teamRoleKey, TeamRole teamRole)
		throws Exception {

		com.liferay.osb.koroneiki.taproot.model.TeamRole curTeamRole =
			_teamRoleLocalService.getTeamRole(teamRoleKey);

		String description = GetterUtil.getString(
			teamRole.getDescription(), curTeamRole.getDescription());

		return TeamRoleUtil.toTeamRole(
			_teamRoleService.updateTeamRole(
				curTeamRole.getTeamRoleId(), teamRole.getName(), description));
	}

	@Reference
	private TeamRoleLocalService _teamRoleLocalService;

	@Reference
	private TeamRoleService _teamRoleService;

}