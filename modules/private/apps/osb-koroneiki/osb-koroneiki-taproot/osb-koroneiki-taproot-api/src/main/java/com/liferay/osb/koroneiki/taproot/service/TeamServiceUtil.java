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

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for Team. This utility wraps
 * <code>com.liferay.osb.koroneiki.taproot.service.impl.TeamServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see TeamService
 * @generated
 */
public class TeamServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.osb.koroneiki.taproot.service.impl.TeamServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link TeamServiceUtil} to access the team remote service. Add custom service methods to <code>com.liferay.osb.koroneiki.taproot.service.impl.TeamServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static com.liferay.osb.koroneiki.taproot.model.Team addTeam(
			long accountId, String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addTeam(accountId, name);
	}

	public static com.liferay.osb.koroneiki.taproot.model.Team addTeam(
			String accountKey, String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addTeam(accountKey, name);
	}

	public static com.liferay.osb.koroneiki.taproot.model.Team deleteTeam(
			long teamId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteTeam(teamId);
	}

	public static com.liferay.osb.koroneiki.taproot.model.Team deleteTeam(
			String teamKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteTeam(teamKey);
	}

	public static java.util.List<com.liferay.osb.koroneiki.taproot.model.Team>
			getAccountTeams(long accountId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getAccountTeams(accountId, start, end);
	}

	public static java.util.List<com.liferay.osb.koroneiki.taproot.model.Team>
			getAccountTeams(String accountKey, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getAccountTeams(accountKey, start, end);
	}

	public static int getAccountTeamsCount(long accountId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getAccountTeamsCount(accountId);
	}

	public static int getAccountTeamsCount(String accountKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getAccountTeamsCount(accountKey);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.osb.koroneiki.taproot.model.Team getTeam(
			long teamId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getTeam(teamId);
	}

	public static com.liferay.osb.koroneiki.taproot.model.Team getTeam(
			String teamKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getTeam(teamKey);
	}

	public static java.util.List<com.liferay.osb.koroneiki.taproot.model.Team>
			getTeams(
				String domain, String entityName, String entityId, int start,
				int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getTeams(domain, entityName, entityId, start, end);
	}

	public static int getTeamsCount(
			String domain, String entityName, String entityId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getTeamsCount(domain, entityName, entityId);
	}

	public static com.liferay.osb.koroneiki.taproot.model.Team updateTeam(
			long teamId, String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateTeam(teamId, name);
	}

	public static com.liferay.osb.koroneiki.taproot.model.Team updateTeam(
			String teamKey, String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateTeam(teamKey, name);
	}

	public static TeamService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<TeamService, TeamService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(TeamService.class);

		ServiceTracker<TeamService, TeamService> serviceTracker =
			new ServiceTracker<TeamService, TeamService>(
				bundle.getBundleContext(), TeamService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}