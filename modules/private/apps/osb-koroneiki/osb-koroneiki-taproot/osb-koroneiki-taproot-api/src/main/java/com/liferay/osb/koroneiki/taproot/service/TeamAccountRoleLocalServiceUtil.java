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
 * Provides the local service utility for TeamAccountRole. This utility wraps
 * <code>com.liferay.osb.koroneiki.taproot.service.impl.TeamAccountRoleLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see TeamAccountRoleLocalService
 * @generated
 */
public class TeamAccountRoleLocalServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.osb.koroneiki.taproot.service.impl.TeamAccountRoleLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link TeamAccountRoleLocalServiceUtil} to access the team account role local service. Add custom service methods to <code>com.liferay.osb.koroneiki.taproot.service.impl.TeamAccountRoleLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static com.liferay.osb.koroneiki.taproot.model.TeamAccountRole
			addTeamAccountRole(long teamId, long accountId, long teamRoleId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addTeamAccountRole(teamId, accountId, teamRoleId);
	}

	/**
	 * Adds the team account role to the database. Also notifies the appropriate model listeners.
	 *
	 * @param teamAccountRole the team account role
	 * @return the team account role that was added
	 */
	public static com.liferay.osb.koroneiki.taproot.model.TeamAccountRole
		addTeamAccountRole(
			com.liferay.osb.koroneiki.taproot.model.TeamAccountRole
				teamAccountRole) {

		return getService().addTeamAccountRole(teamAccountRole);
	}

	/**
	 * Creates a new team account role with the primary key. Does not add the team account role to the database.
	 *
	 * @param teamAccountRolePK the primary key for the new team account role
	 * @return the new team account role
	 */
	public static com.liferay.osb.koroneiki.taproot.model.TeamAccountRole
		createTeamAccountRole(
			com.liferay.osb.koroneiki.taproot.service.persistence.
				TeamAccountRolePK teamAccountRolePK) {

		return getService().createTeamAccountRole(teamAccountRolePK);
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			deletePersistedModel(
				com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	public static com.liferay.osb.koroneiki.taproot.model.TeamAccountRole
		deleteTeamAccountRole(long teamId, long accountId, long teamRoleId) {

		return getService().deleteTeamAccountRole(
			teamId, accountId, teamRoleId);
	}

	/**
	 * Deletes the team account role from the database. Also notifies the appropriate model listeners.
	 *
	 * @param teamAccountRole the team account role
	 * @return the team account role that was removed
	 */
	public static com.liferay.osb.koroneiki.taproot.model.TeamAccountRole
		deleteTeamAccountRole(
			com.liferay.osb.koroneiki.taproot.model.TeamAccountRole
				teamAccountRole) {

		return getService().deleteTeamAccountRole(teamAccountRole);
	}

	/**
	 * Deletes the team account role with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param teamAccountRolePK the primary key of the team account role
	 * @return the team account role that was removed
	 * @throws PortalException if a team account role with the primary key could not be found
	 */
	public static com.liferay.osb.koroneiki.taproot.model.TeamAccountRole
			deleteTeamAccountRole(
				com.liferay.osb.koroneiki.taproot.service.persistence.
					TeamAccountRolePK teamAccountRolePK)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteTeamAccountRole(teamAccountRolePK);
	}

	public static void deleteTeamAccountRoles(long teamId, long accountId) {
		getService().deleteTeamAccountRoles(teamId, accountId);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery
		dynamicQuery() {

		return getService().dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.osb.koroneiki.taproot.model.impl.TeamAccountRoleModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.osb.koroneiki.taproot.model.impl.TeamAccountRoleModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static com.liferay.osb.koroneiki.taproot.model.TeamAccountRole
		fetchTeamAccountRole(
			com.liferay.osb.koroneiki.taproot.service.persistence.
				TeamAccountRolePK teamAccountRolePK) {

		return getService().fetchTeamAccountRole(teamAccountRolePK);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.model.PersistedModel
			getPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	 * Returns the team account role with the primary key.
	 *
	 * @param teamAccountRolePK the primary key of the team account role
	 * @return the team account role
	 * @throws PortalException if a team account role with the primary key could not be found
	 */
	public static com.liferay.osb.koroneiki.taproot.model.TeamAccountRole
			getTeamAccountRole(
				com.liferay.osb.koroneiki.taproot.service.persistence.
					TeamAccountRolePK teamAccountRolePK)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getTeamAccountRole(teamAccountRolePK);
	}

	/**
	 * Returns a range of all the team account roles.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.osb.koroneiki.taproot.model.impl.TeamAccountRoleModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of team account roles
	 * @param end the upper bound of the range of team account roles (not inclusive)
	 * @return the range of team account roles
	 */
	public static java.util.List
		<com.liferay.osb.koroneiki.taproot.model.TeamAccountRole>
			getTeamAccountRoles(int start, int end) {

		return getService().getTeamAccountRoles(start, end);
	}

	/**
	 * Returns the number of team account roles.
	 *
	 * @return the number of team account roles
	 */
	public static int getTeamAccountRolesCount() {
		return getService().getTeamAccountRolesCount();
	}

	/**
	 * Updates the team account role in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param teamAccountRole the team account role
	 * @return the team account role that was updated
	 */
	public static com.liferay.osb.koroneiki.taproot.model.TeamAccountRole
		updateTeamAccountRole(
			com.liferay.osb.koroneiki.taproot.model.TeamAccountRole
				teamAccountRole) {

		return getService().updateTeamAccountRole(teamAccountRole);
	}

	public static TeamAccountRoleLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<TeamAccountRoleLocalService, TeamAccountRoleLocalService>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			TeamAccountRoleLocalService.class);

		ServiceTracker<TeamAccountRoleLocalService, TeamAccountRoleLocalService>
			serviceTracker =
				new ServiceTracker
					<TeamAccountRoleLocalService, TeamAccountRoleLocalService>(
						bundle.getBundleContext(),
						TeamAccountRoleLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}