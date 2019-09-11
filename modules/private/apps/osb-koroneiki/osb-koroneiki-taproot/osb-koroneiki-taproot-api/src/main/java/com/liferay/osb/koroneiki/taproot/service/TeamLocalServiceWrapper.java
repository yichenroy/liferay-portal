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
 * Provides a wrapper for {@link TeamLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see TeamLocalService
 * @generated
 */
public class TeamLocalServiceWrapper
	implements TeamLocalService, ServiceWrapper<TeamLocalService> {

	public TeamLocalServiceWrapper(TeamLocalService teamLocalService) {
		_teamLocalService = teamLocalService;
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link TeamLocalServiceUtil} to access the team local service. Add custom service methods to <code>com.liferay.osb.koroneiki.taproot.service.impl.TeamLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Override
	public com.liferay.osb.koroneiki.taproot.model.Team addTeam(
			long userId, long accountId, String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _teamLocalService.addTeam(userId, accountId, name);
	}

	/**
	 * Adds the team to the database. Also notifies the appropriate model listeners.
	 *
	 * @param team the team
	 * @return the team that was added
	 */
	@Override
	public com.liferay.osb.koroneiki.taproot.model.Team addTeam(
		com.liferay.osb.koroneiki.taproot.model.Team team) {

		return _teamLocalService.addTeam(team);
	}

	/**
	 * Creates a new team with the primary key. Does not add the team to the database.
	 *
	 * @param teamId the primary key for the new team
	 * @return the new team
	 */
	@Override
	public com.liferay.osb.koroneiki.taproot.model.Team createTeam(
		long teamId) {

		return _teamLocalService.createTeam(teamId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _teamLocalService.deletePersistedModel(persistedModel);
	}

	/**
	 * Deletes the team with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param teamId the primary key of the team
	 * @return the team that was removed
	 * @throws PortalException if a team with the primary key could not be found
	 */
	@Override
	public com.liferay.osb.koroneiki.taproot.model.Team deleteTeam(long teamId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _teamLocalService.deleteTeam(teamId);
	}

	/**
	 * Deletes the team from the database. Also notifies the appropriate model listeners.
	 *
	 * @param team the team
	 * @return the team that was removed
	 * @throws PortalException
	 */
	@Override
	public com.liferay.osb.koroneiki.taproot.model.Team deleteTeam(
			com.liferay.osb.koroneiki.taproot.model.Team team)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _teamLocalService.deleteTeam(team);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _teamLocalService.dynamicQuery();
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

		return _teamLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.osb.koroneiki.taproot.model.impl.TeamModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

		return _teamLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.osb.koroneiki.taproot.model.impl.TeamModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

		return _teamLocalService.dynamicQuery(
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

		return _teamLocalService.dynamicQueryCount(dynamicQuery);
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

		return _teamLocalService.dynamicQueryCount(dynamicQuery, projection);
	}

	@Override
	public com.liferay.osb.koroneiki.taproot.model.Team fetchTeam(long teamId) {
		return _teamLocalService.fetchTeam(teamId);
	}

	/**
	 * Returns the team with the matching UUID and company.
	 *
	 * @param uuid the team's UUID
	 * @param companyId the primary key of the company
	 * @return the matching team, or <code>null</code> if a matching team could not be found
	 */
	@Override
	public com.liferay.osb.koroneiki.taproot.model.Team
		fetchTeamByUuidAndCompanyId(String uuid, long companyId) {

		return _teamLocalService.fetchTeamByUuidAndCompanyId(uuid, companyId);
	}

	@Override
	public java.util.List<com.liferay.osb.koroneiki.taproot.model.Team>
		getAccountAssignedTeams(long accountId, int start, int end) {

		return _teamLocalService.getAccountAssignedTeams(accountId, start, end);
	}

	@Override
	public int getAccountAssignedTeamsCount(long accountId) {
		return _teamLocalService.getAccountAssignedTeamsCount(accountId);
	}

	@Override
	public java.util.List<com.liferay.osb.koroneiki.taproot.model.Team>
		getAccountTeams(long accountId, int start, int end) {

		return _teamLocalService.getAccountTeams(accountId, start, end);
	}

	@Override
	public int getAccountTeamsCount(long accountId) {
		return _teamLocalService.getAccountTeamsCount(accountId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _teamLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _teamLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _teamLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _teamLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _teamLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Returns the team with the primary key.
	 *
	 * @param teamId the primary key of the team
	 * @return the team
	 * @throws PortalException if a team with the primary key could not be found
	 */
	@Override
	public com.liferay.osb.koroneiki.taproot.model.Team getTeam(long teamId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _teamLocalService.getTeam(teamId);
	}

	@Override
	public com.liferay.osb.koroneiki.taproot.model.Team getTeam(String teamKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _teamLocalService.getTeam(teamKey);
	}

	/**
	 * Returns the team with the matching UUID and company.
	 *
	 * @param uuid the team's UUID
	 * @param companyId the primary key of the company
	 * @return the matching team
	 * @throws PortalException if a matching team could not be found
	 */
	@Override
	public com.liferay.osb.koroneiki.taproot.model.Team
			getTeamByUuidAndCompanyId(String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _teamLocalService.getTeamByUuidAndCompanyId(uuid, companyId);
	}

	/**
	 * Returns a range of all the teams.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.osb.koroneiki.taproot.model.impl.TeamModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of teams
	 * @param end the upper bound of the range of teams (not inclusive)
	 * @return the range of teams
	 */
	@Override
	public java.util.List<com.liferay.osb.koroneiki.taproot.model.Team>
		getTeams(int start, int end) {

		return _teamLocalService.getTeams(start, end);
	}

	/**
	 * Returns the number of teams.
	 *
	 * @return the number of teams
	 */
	@Override
	public int getTeamsCount() {
		return _teamLocalService.getTeamsCount();
	}

	@Override
	public com.liferay.osb.koroneiki.taproot.model.Team reindex(long teamId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _teamLocalService.reindex(teamId);
	}

	@Override
	public com.liferay.portal.kernel.search.Hits search(
			long companyId, String keywords, int start, int end,
			com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _teamLocalService.search(companyId, keywords, start, end, sort);
	}

	@Override
	public com.liferay.osb.koroneiki.taproot.model.Team updateTeam(
			long teamId, String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _teamLocalService.updateTeam(teamId, name);
	}

	/**
	 * Updates the team in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param team the team
	 * @return the team that was updated
	 */
	@Override
	public com.liferay.osb.koroneiki.taproot.model.Team updateTeam(
		com.liferay.osb.koroneiki.taproot.model.Team team) {

		return _teamLocalService.updateTeam(team);
	}

	@Override
	public TeamLocalService getWrappedService() {
		return _teamLocalService;
	}

	@Override
	public void setWrappedService(TeamLocalService teamLocalService) {
		_teamLocalService = teamLocalService;
	}

	private TeamLocalService _teamLocalService;

}