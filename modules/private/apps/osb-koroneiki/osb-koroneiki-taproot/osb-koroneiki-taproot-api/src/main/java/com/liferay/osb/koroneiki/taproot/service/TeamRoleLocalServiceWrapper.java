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
 * Provides a wrapper for {@link TeamRoleLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see TeamRoleLocalService
 * @generated
 */
public class TeamRoleLocalServiceWrapper
	implements TeamRoleLocalService, ServiceWrapper<TeamRoleLocalService> {

	public TeamRoleLocalServiceWrapper(
		TeamRoleLocalService teamRoleLocalService) {

		_teamRoleLocalService = teamRoleLocalService;
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link TeamRoleLocalServiceUtil} to access the team role local service. Add custom service methods to <code>com.liferay.osb.koroneiki.taproot.service.impl.TeamRoleLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Override
	public com.liferay.osb.koroneiki.taproot.model.TeamRole addTeamRole(
			long userId, String name, String description, int type)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _teamRoleLocalService.addTeamRole(
			userId, name, description, type);
	}

	/**
	 * Adds the team role to the database. Also notifies the appropriate model listeners.
	 *
	 * @param teamRole the team role
	 * @return the team role that was added
	 */
	@Override
	public com.liferay.osb.koroneiki.taproot.model.TeamRole addTeamRole(
		com.liferay.osb.koroneiki.taproot.model.TeamRole teamRole) {

		return _teamRoleLocalService.addTeamRole(teamRole);
	}

	/**
	 * Creates a new team role with the primary key. Does not add the team role to the database.
	 *
	 * @param teamRoleId the primary key for the new team role
	 * @return the new team role
	 */
	@Override
	public com.liferay.osb.koroneiki.taproot.model.TeamRole createTeamRole(
		long teamRoleId) {

		return _teamRoleLocalService.createTeamRole(teamRoleId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _teamRoleLocalService.deletePersistedModel(persistedModel);
	}

	/**
	 * Deletes the team role with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param teamRoleId the primary key of the team role
	 * @return the team role that was removed
	 * @throws PortalException if a team role with the primary key could not be found
	 */
	@Override
	public com.liferay.osb.koroneiki.taproot.model.TeamRole deleteTeamRole(
			long teamRoleId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _teamRoleLocalService.deleteTeamRole(teamRoleId);
	}

	/**
	 * Deletes the team role from the database. Also notifies the appropriate model listeners.
	 *
	 * @param teamRole the team role
	 * @return the team role that was removed
	 * @throws PortalException
	 */
	@Override
	public com.liferay.osb.koroneiki.taproot.model.TeamRole deleteTeamRole(
			com.liferay.osb.koroneiki.taproot.model.TeamRole teamRole)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _teamRoleLocalService.deleteTeamRole(teamRole);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _teamRoleLocalService.dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _teamRoleLocalService.dynamicQuery(dynamicQuery);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return _teamRoleLocalService.dynamicQuery(dynamicQuery, start, end);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return _teamRoleLocalService.dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _teamRoleLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return _teamRoleLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.osb.koroneiki.taproot.model.TeamRole fetchTeamRole(
		long teamRoleId) {

		return _teamRoleLocalService.fetchTeamRole(teamRoleId);
	}

	/**
	 * Returns the team role with the matching UUID and company.
	 *
	 * @param uuid the team role's UUID
	 * @param companyId the primary key of the company
	 * @return the matching team role, or <code>null</code> if a matching team role could not be found
	 */
	@Override
	public com.liferay.osb.koroneiki.taproot.model.TeamRole
		fetchTeamRoleByUuidAndCompanyId(String uuid, long companyId) {

		return _teamRoleLocalService.fetchTeamRoleByUuidAndCompanyId(
			uuid, companyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _teamRoleLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _teamRoleLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _teamRoleLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _teamRoleLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _teamRoleLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public java.util.List<com.liferay.osb.koroneiki.taproot.model.TeamRole>
		getTeamAccountTeamRoles(long accountId, long teamId) {

		return _teamRoleLocalService.getTeamAccountTeamRoles(accountId, teamId);
	}

	/**
	 * Returns the team role with the primary key.
	 *
	 * @param teamRoleId the primary key of the team role
	 * @return the team role
	 * @throws PortalException if a team role with the primary key could not be found
	 */
	@Override
	public com.liferay.osb.koroneiki.taproot.model.TeamRole getTeamRole(
			long teamRoleId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _teamRoleLocalService.getTeamRole(teamRoleId);
	}

	@Override
	public com.liferay.osb.koroneiki.taproot.model.TeamRole getTeamRole(
			String teamRoleKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _teamRoleLocalService.getTeamRole(teamRoleKey);
	}

	/**
	 * Returns the team role with the matching UUID and company.
	 *
	 * @param uuid the team role's UUID
	 * @param companyId the primary key of the company
	 * @return the matching team role
	 * @throws PortalException if a matching team role could not be found
	 */
	@Override
	public com.liferay.osb.koroneiki.taproot.model.TeamRole
			getTeamRoleByUuidAndCompanyId(String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _teamRoleLocalService.getTeamRoleByUuidAndCompanyId(
			uuid, companyId);
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
	@Override
	public java.util.List<com.liferay.osb.koroneiki.taproot.model.TeamRole>
		getTeamRoles(int start, int end) {

		return _teamRoleLocalService.getTeamRoles(start, end);
	}

	@Override
	public java.util.List<com.liferay.osb.koroneiki.taproot.model.TeamRole>
		getTeamRoles(int type, int start, int end) {

		return _teamRoleLocalService.getTeamRoles(type, start, end);
	}

	/**
	 * Returns the number of team roles.
	 *
	 * @return the number of team roles
	 */
	@Override
	public int getTeamRolesCount() {
		return _teamRoleLocalService.getTeamRolesCount();
	}

	@Override
	public int getTeamRolesCount(int type) {
		return _teamRoleLocalService.getTeamRolesCount(type);
	}

	@Override
	public com.liferay.osb.koroneiki.taproot.model.TeamRole updateTeamRole(
			long userId, long teamRoleId, String name, String description)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _teamRoleLocalService.updateTeamRole(
			userId, teamRoleId, name, description);
	}

	/**
	 * Updates the team role in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param teamRole the team role
	 * @return the team role that was updated
	 */
	@Override
	public com.liferay.osb.koroneiki.taproot.model.TeamRole updateTeamRole(
		com.liferay.osb.koroneiki.taproot.model.TeamRole teamRole) {

		return _teamRoleLocalService.updateTeamRole(teamRole);
	}

	@Override
	public TeamRoleLocalService getWrappedService() {
		return _teamRoleLocalService;
	}

	@Override
	public void setWrappedService(TeamRoleLocalService teamRoleLocalService) {
		_teamRoleLocalService = teamRoleLocalService;
	}

	private TeamRoleLocalService _teamRoleLocalService;

}