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
 * Provides the remote service utility for TeamRole. This utility wraps
 * <code>com.liferay.osb.koroneiki.taproot.service.impl.TeamRoleServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see TeamRoleService
 * @generated
 */
public class TeamRoleServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.osb.koroneiki.taproot.service.impl.TeamRoleServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link TeamRoleServiceUtil} to access the team role remote service. Add custom service methods to <code>com.liferay.osb.koroneiki.taproot.service.impl.TeamRoleServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static com.liferay.osb.koroneiki.taproot.model.TeamRole addTeamRole(
			String name, String description, int type)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addTeamRole(name, description, type);
	}

	public static com.liferay.osb.koroneiki.taproot.model.TeamRole
			deleteTeamRole(long teamRoleId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteTeamRole(teamRoleId);
	}

	public static com.liferay.osb.koroneiki.taproot.model.TeamRole
			deleteTeamRole(String teamRoleKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteTeamRole(teamRoleKey);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.osb.koroneiki.taproot.model.TeamRole getTeamRole(
			long teamRoleId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getTeamRole(teamRoleId);
	}

	public static com.liferay.osb.koroneiki.taproot.model.TeamRole getTeamRole(
			String teamRoleKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getTeamRole(teamRoleKey);
	}

	public static com.liferay.osb.koroneiki.taproot.model.TeamRole
			updateTeamRole(long teamRoleId, String name, String description)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateTeamRole(teamRoleId, name, description);
	}

	public static TeamRoleService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<TeamRoleService, TeamRoleService>
		_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(TeamRoleService.class);

		ServiceTracker<TeamRoleService, TeamRoleService> serviceTracker =
			new ServiceTracker<TeamRoleService, TeamRoleService>(
				bundle.getBundleContext(), TeamRoleService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}