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

package com.liferay.osb.koroneiki.taproot.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link TeamRoleService}.
 *
 * @author Brian Wing Shun Chan
 * @see TeamRoleService
 * @generated
 */
public class TeamRoleServiceWrapper
	implements TeamRoleService, ServiceWrapper<TeamRoleService> {

	public TeamRoleServiceWrapper(TeamRoleService teamRoleService) {
		_teamRoleService = teamRoleService;
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link TeamRoleServiceUtil} to access the team role remote service. Add custom service methods to <code>com.liferay.osb.koroneiki.taproot.service.impl.TeamRoleServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Override
	public com.liferay.osb.koroneiki.taproot.model.TeamRole addTeamRole(
			String name, String description, int type)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _teamRoleService.addTeamRole(name, description, type);
	}

	@Override
	public com.liferay.osb.koroneiki.taproot.model.TeamRole deleteTeamRole(
			long teamRoleId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _teamRoleService.deleteTeamRole(teamRoleId);
	}

	@Override
	public com.liferay.osb.koroneiki.taproot.model.TeamRole deleteTeamRole(
			String teamRoleKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _teamRoleService.deleteTeamRole(teamRoleKey);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _teamRoleService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.osb.koroneiki.taproot.model.TeamRole getTeamRole(
			long teamRoleId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _teamRoleService.getTeamRole(teamRoleId);
	}

	@Override
	public com.liferay.osb.koroneiki.taproot.model.TeamRole getTeamRole(
			String teamRoleKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _teamRoleService.getTeamRole(teamRoleKey);
	}

	@Override
	public com.liferay.osb.koroneiki.taproot.model.TeamRole updateTeamRole(
			long teamRoleId, String name, String description)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _teamRoleService.updateTeamRole(teamRoleId, name, description);
	}

	@Override
	public TeamRoleService getWrappedService() {
		return _teamRoleService;
	}

	@Override
	public void setWrappedService(TeamRoleService teamRoleService) {
		_teamRoleService = teamRoleService;
	}

	private TeamRoleService _teamRoleService;

}