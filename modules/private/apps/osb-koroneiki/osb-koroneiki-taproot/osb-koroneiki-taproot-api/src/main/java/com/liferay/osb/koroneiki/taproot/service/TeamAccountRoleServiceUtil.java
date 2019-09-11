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
 * Provides the remote service utility for TeamAccountRole. This utility wraps
 * <code>com.liferay.osb.koroneiki.taproot.service.impl.TeamAccountRoleServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see TeamAccountRoleService
 * @generated
 */
public class TeamAccountRoleServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.osb.koroneiki.taproot.service.impl.TeamAccountRoleServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link TeamAccountRoleServiceUtil} to access the team account role remote service. Add custom service methods to <code>com.liferay.osb.koroneiki.taproot.service.impl.TeamAccountRoleServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static com.liferay.osb.koroneiki.taproot.model.TeamAccountRole
			addTeamAccountRole(long teamId, long accountId, long teamRoleId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addTeamAccountRole(teamId, accountId, teamRoleId);
	}

	public static com.liferay.osb.koroneiki.taproot.model.TeamAccountRole
			addTeamAccountRole(
				String teamKey, String accountKey, String teamRoleKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addTeamAccountRole(
			teamKey, accountKey, teamRoleKey);
	}

	public static com.liferay.osb.koroneiki.taproot.model.TeamAccountRole
			deleteTeamAccountRole(long teamId, long accountId, long teamRoleId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteTeamAccountRole(
			teamId, accountId, teamRoleId);
	}

	public static com.liferay.osb.koroneiki.taproot.model.TeamAccountRole
			deleteTeamAccountRole(
				String teamKey, String accountKey, String teamRoleKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteTeamAccountRole(
			teamKey, accountKey, teamRoleKey);
	}

	public static void deleteTeamAccountRoles(long teamId, long accountId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteTeamAccountRoles(teamId, accountId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static TeamAccountRoleService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<TeamAccountRoleService, TeamAccountRoleService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(TeamAccountRoleService.class);

		ServiceTracker<TeamAccountRoleService, TeamAccountRoleService>
			serviceTracker =
				new ServiceTracker
					<TeamAccountRoleService, TeamAccountRoleService>(
						bundle.getBundleContext(), TeamAccountRoleService.class,
						null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}