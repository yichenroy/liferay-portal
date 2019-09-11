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
 * Provides the local service utility for TeamRole. This utility wraps
 * <code>com.liferay.osb.koroneiki.taproot.service.impl.TeamRoleLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see TeamRoleLocalService
 * @generated
 */
public class TeamRoleLocalServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.osb.koroneiki.taproot.service.impl.TeamRoleLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link TeamRoleLocalServiceUtil} to access the team role local service. Add custom service methods to <code>com.liferay.osb.koroneiki.taproot.service.impl.TeamRoleLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static com.liferay.osb.koroneiki.taproot.model.TeamRole addTeamRole(
			long userId, String name, String description, int type)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addTeamRole(userId, name, description, type);
	}

	/**
	 * Adds the team role to the database. Also notifies the appropriate model listeners.
	 *
	 * @param teamRole the team role
	 * @return the team role that was added
	 */
	public static com.liferay.osb.koroneiki.taproot.model.TeamRole addTeamRole(
		com.liferay.osb.koroneiki.taproot.model.TeamRole teamRole) {

		return getService().addTeamRole(teamRole);
	}

	/**
	 * Creates a new team role with the primary key. Does not add the team role to the database.
	 *
	 * @param teamRoleId the primary key for the new team role
	 * @return the new team role
	 */
	public static com.liferay.osb.koroneiki.taproot.model.TeamRole
		createTeamRole(long teamRoleId) {

		return getService().createTeamRole(teamRoleId);
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

	/**
	 * Deletes the team role with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param teamRoleId the primary key of the team role
	 * @return the team role that was removed
	 * @throws PortalException if a team role with the primary key could not be found
	 */
	public static com.liferay.osb.koroneiki.taproot.model.TeamRole
			deleteTeamRole(long teamRoleId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteTeamRole(teamRoleId);
	}

	/**
	 * Deletes the team role from the database. Also notifies the appropriate model listeners.
	 *
	 * @param teamRole the team role
	 * @return the team role that was removed
	 * @throws PortalException
	 */
	public static com.liferay.osb.koroneiki.taproot.model.TeamRole
			deleteTeamRole(
				com.liferay.osb.koroneiki.taproot.model.TeamRole teamRole)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteTeamRole(teamRole);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.osb.koroneiki.taproot.model.impl.TeamRoleModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.osb.koroneiki.taproot.model.impl.TeamRoleModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.osb.koroneiki.taproot.model.TeamRole
		fetchTeamRole(long teamRoleId) {

		return getService().fetchTeamRole(teamRoleId);
	}

	/**
	 * Returns the team role with the matching UUID and company.
	 *
	 * @param uuid the team role's UUID
	 * @param companyId the primary key of the company
	 * @return the matching team role, or <code>null</code> if a matching team role could not be found
	 */
	public static com.liferay.osb.koroneiki.taproot.model.TeamRole
		fetchTeamRoleByUuidAndCompanyId(String uuid, long companyId) {

		return getService().fetchTeamRoleByUuidAndCompanyId(uuid, companyId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return getService().getExportActionableDynamicQuery(portletDataContext);
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

	public static java.util.List
		<com.liferay.osb.koroneiki.taproot.model.TeamRole>
			getTeamAccountTeamRoles(long accountId, long teamId) {

		return getService().getTeamAccountTeamRoles(accountId, teamId);
	}

	/**
	 * Returns the team role with the primary key.
	 *
	 * @param teamRoleId the primary key of the team role
	 * @return the team role
	 * @throws PortalException if a team role with the primary key could not be found
	 */
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

	/**
	 * Returns the team role with the matching UUID and company.
	 *
	 * @param uuid the team role's UUID
	 * @param companyId the primary key of the company
	 * @return the matching team role
	 * @throws PortalException if a matching team role could not be found
	 */
	public static com.liferay.osb.koroneiki.taproot.model.TeamRole
			getTeamRoleByUuidAndCompanyId(String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getTeamRoleByUuidAndCompanyId(uuid, companyId);
	}

	/**
	 * Returns a range of all the team roles.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.osb.koroneiki.taproot.model.impl.TeamRoleModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of team roles
	 * @param end the upper bound of the range of team roles (not inclusive)
	 * @return the range of team roles
	 */
	public static java.util.List
		<com.liferay.osb.koroneiki.taproot.model.TeamRole> getTeamRoles(
			int start, int end) {

		return getService().getTeamRoles(start, end);
	}

	public static java.util.List
		<com.liferay.osb.koroneiki.taproot.model.TeamRole> getTeamRoles(
			int type, int start, int end) {

		return getService().getTeamRoles(type, start, end);
	}

	/**
	 * Returns the number of team roles.
	 *
	 * @return the number of team roles
	 */
	public static int getTeamRolesCount() {
		return getService().getTeamRolesCount();
	}

	public static int getTeamRolesCount(int type) {
		return getService().getTeamRolesCount(type);
	}

	public static com.liferay.osb.koroneiki.taproot.model.TeamRole
			updateTeamRole(
				long userId, long teamRoleId, String name, String description)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateTeamRole(
			userId, teamRoleId, name, description);
	}

	/**
	 * Updates the team role in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param teamRole the team role
	 * @return the team role that was updated
	 */
	public static com.liferay.osb.koroneiki.taproot.model.TeamRole
		updateTeamRole(
			com.liferay.osb.koroneiki.taproot.model.TeamRole teamRole) {

		return getService().updateTeamRole(teamRole);
	}

	public static TeamRoleLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<TeamRoleLocalService, TeamRoleLocalService>
		_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(TeamRoleLocalService.class);

		ServiceTracker<TeamRoleLocalService, TeamRoleLocalService>
			serviceTracker =
				new ServiceTracker<TeamRoleLocalService, TeamRoleLocalService>(
					bundle.getBundleContext(), TeamRoleLocalService.class,
					null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}