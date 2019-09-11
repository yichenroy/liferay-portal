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
 * Provides a wrapper for {@link TeamAccountRoleService}.
 *
 * @author Brian Wing Shun Chan
 * @see TeamAccountRoleService
 * @generated
 */
public class TeamAccountRoleServiceWrapper
	implements TeamAccountRoleService, ServiceWrapper<TeamAccountRoleService> {

	public TeamAccountRoleServiceWrapper(
		TeamAccountRoleService teamAccountRoleService) {

		_teamAccountRoleService = teamAccountRoleService;
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link TeamAccountRoleServiceUtil} to access the team account role remote service. Add custom service methods to <code>com.liferay.osb.koroneiki.taproot.service.impl.TeamAccountRoleServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Override
	public com.liferay.osb.koroneiki.taproot.model.TeamAccountRole
			addTeamAccountRole(long teamId, long accountId, long teamRoleId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _teamAccountRoleService.addTeamAccountRole(
			teamId, accountId, teamRoleId);
	}

	@Override
	public com.liferay.osb.koroneiki.taproot.model.TeamAccountRole
			addTeamAccountRole(
				String teamKey, String accountKey, String teamRoleKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _teamAccountRoleService.addTeamAccountRole(
			teamKey, accountKey, teamRoleKey);
	}

	@Override
	public com.liferay.osb.koroneiki.taproot.model.TeamAccountRole
			deleteTeamAccountRole(long teamId, long accountId, long teamRoleId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _teamAccountRoleService.deleteTeamAccountRole(
			teamId, accountId, teamRoleId);
	}

	@Override
	public com.liferay.osb.koroneiki.taproot.model.TeamAccountRole
			deleteTeamAccountRole(
				String teamKey, String accountKey, String teamRoleKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _teamAccountRoleService.deleteTeamAccountRole(
			teamKey, accountKey, teamRoleKey);
	}

	@Override
	public void deleteTeamAccountRoles(long teamId, long accountId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_teamAccountRoleService.deleteTeamAccountRoles(teamId, accountId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _teamAccountRoleService.getOSGiServiceIdentifier();
	}

	@Override
	public TeamAccountRoleService getWrappedService() {
		return _teamAccountRoleService;
	}

	@Override
	public void setWrappedService(
		TeamAccountRoleService teamAccountRoleService) {

		_teamAccountRoleService = teamAccountRoleService;
	}

	private TeamAccountRoleService _teamAccountRoleService;

}