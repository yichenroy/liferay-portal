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
 * Provides a wrapper for {@link TeamService}.
 *
 * @author Brian Wing Shun Chan
 * @see TeamService
 * @generated
 */
public class TeamServiceWrapper
	implements TeamService, ServiceWrapper<TeamService> {

	public TeamServiceWrapper(TeamService teamService) {
		_teamService = teamService;
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link TeamServiceUtil} to access the team remote service. Add custom service methods to <code>com.liferay.osb.koroneiki.taproot.service.impl.TeamServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Override
	public com.liferay.osb.koroneiki.taproot.model.Team addTeam(
			long accountId, String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _teamService.addTeam(accountId, name);
	}

	@Override
	public com.liferay.osb.koroneiki.taproot.model.Team addTeam(
			String accountKey, String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _teamService.addTeam(accountKey, name);
	}

	@Override
	public com.liferay.osb.koroneiki.taproot.model.Team deleteTeam(long teamId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _teamService.deleteTeam(teamId);
	}

	@Override
	public com.liferay.osb.koroneiki.taproot.model.Team deleteTeam(
			String teamKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _teamService.deleteTeam(teamKey);
	}

	@Override
	public java.util.List<com.liferay.osb.koroneiki.taproot.model.Team>
			getAccountTeams(long accountId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _teamService.getAccountTeams(accountId, start, end);
	}

	@Override
	public java.util.List<com.liferay.osb.koroneiki.taproot.model.Team>
			getAccountTeams(String accountKey, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _teamService.getAccountTeams(accountKey, start, end);
	}

	@Override
	public int getAccountTeamsCount(long accountId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _teamService.getAccountTeamsCount(accountId);
	}

	@Override
	public int getAccountTeamsCount(String accountKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _teamService.getAccountTeamsCount(accountKey);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _teamService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.osb.koroneiki.taproot.model.Team getTeam(long teamId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _teamService.getTeam(teamId);
	}

	@Override
	public com.liferay.osb.koroneiki.taproot.model.Team getTeam(String teamKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _teamService.getTeam(teamKey);
	}

	@Override
	public java.util.List<com.liferay.osb.koroneiki.taproot.model.Team>
			getTeams(
				String domain, String entityName, String entityId, int start,
				int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _teamService.getTeams(domain, entityName, entityId, start, end);
	}

	@Override
	public int getTeamsCount(String domain, String entityName, String entityId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _teamService.getTeamsCount(domain, entityName, entityId);
	}

	@Override
	public com.liferay.osb.koroneiki.taproot.model.Team updateTeam(
			long teamId, String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _teamService.updateTeam(teamId, name);
	}

	@Override
	public com.liferay.osb.koroneiki.taproot.model.Team updateTeam(
			String teamKey, String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _teamService.updateTeam(teamKey, name);
	}

	@Override
	public TeamService getWrappedService() {
		return _teamService;
	}

	@Override
	public void setWrappedService(TeamService teamService) {
		_teamService = teamService;
	}

	private TeamService _teamService;

}