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

import com.liferay.osb.koroneiki.taproot.constants.TeamRoleType;
import com.liferay.osb.koroneiki.taproot.exception.TeamRoleTypeException;
import com.liferay.osb.koroneiki.taproot.model.TeamAccountRole;
import com.liferay.osb.koroneiki.taproot.model.TeamRole;
import com.liferay.osb.koroneiki.taproot.service.base.TeamAccountRoleLocalServiceBaseImpl;
import com.liferay.osb.koroneiki.taproot.service.persistence.TeamAccountRolePK;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;

import org.osgi.service.component.annotations.Component;

/**
 * @author Kyle Bischof
 */
@Component(
	property = "model.class.name=com.liferay.osb.koroneiki.taproot.model.TeamAccountRole",
	service = AopService.class
)
public class TeamAccountRoleLocalServiceImpl
	extends TeamAccountRoleLocalServiceBaseImpl {

	public TeamAccountRole addTeamAccountRole(
			long teamId, long accountId, long teamRoleId)
		throws PortalException {

		validate(teamId, accountId, teamRoleId);

		TeamAccountRolePK teamAccountRolePK = new TeamAccountRolePK(
			teamId, accountId, teamRoleId);

		TeamAccountRole teamAccountRole =
			teamAccountRolePersistence.fetchByPrimaryKey(teamAccountRolePK);

		if (teamAccountRole == null) {
			teamAccountRole = teamAccountRolePersistence.create(
				teamAccountRolePK);

			teamAccountRolePersistence.update(teamAccountRole);
		}

		return teamAccountRole;
	}

	public TeamAccountRole deleteTeamAccountRole(
		long teamId, long accountId, long teamRoleId) {

		TeamAccountRolePK teamAccountRolePK = new TeamAccountRolePK(
			teamId, accountId, teamRoleId);

		TeamAccountRole teamAccountRole =
			teamAccountRolePersistence.fetchByPrimaryKey(teamAccountRolePK);

		if (teamAccountRole != null) {
			deleteTeamAccountRole(teamAccountRole);
		}

		return teamAccountRole;
	}

	public void deleteTeamAccountRoles(long teamId, long accountId) {
		teamAccountRolePersistence.removeByTI_AI(teamId, accountId);
	}

	protected void validate(long teamId, long accountId, long teamRoleId)
		throws PortalException {

		teamPersistence.findByPrimaryKey(teamId);

		accountPersistence.findByPrimaryKey(accountId);

		TeamRole teamRole = teamRolePersistence.findByPrimaryKey(teamRoleId);

		if (teamRole.getType() != TeamRoleType.ACCOUNT) {
			throw new TeamRoleTypeException();
		}
	}

}