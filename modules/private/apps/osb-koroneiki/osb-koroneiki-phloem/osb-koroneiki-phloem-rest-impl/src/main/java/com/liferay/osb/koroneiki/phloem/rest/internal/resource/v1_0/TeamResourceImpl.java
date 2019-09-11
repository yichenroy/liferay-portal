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

package com.liferay.osb.koroneiki.phloem.rest.internal.resource.v1_0;

import com.liferay.osb.koroneiki.phloem.rest.dto.v1_0.Team;
import com.liferay.osb.koroneiki.phloem.rest.dto.v1_0.util.TeamUtil;
import com.liferay.osb.koroneiki.phloem.rest.internal.odata.entity.v1_0.TeamEntityModel;
import com.liferay.osb.koroneiki.phloem.rest.resource.v1_0.TeamResource;
import com.liferay.osb.koroneiki.taproot.service.TeamLocalService;
import com.liferay.osb.koroneiki.taproot.service.TeamService;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.vulcan.util.SearchUtil;

import javax.ws.rs.core.MultivaluedMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Amos Fong
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/team.properties",
	scope = ServiceScope.PROTOTYPE, service = TeamResource.class
)
public class TeamResourceImpl
	extends BaseTeamResourceImpl implements EntityModelResource {

	@Override
	public void deleteTeam(String teamKey) throws Exception {
		_teamService.deleteTeam(teamKey);
	}

	@Override
	public Page<Team> getAccountAccountKeyTeamsPage(
			String accountKey, Pagination pagination)
		throws Exception {

		return Page.of(
			transform(
				_teamService.getAccountTeams(
					accountKey, pagination.getStartPosition(),
					pagination.getEndPosition()),
				TeamUtil::toTeam),
			pagination, _teamService.getAccountTeamsCount(accountKey));
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap) {
		return _entityModel;
	}

	@Override
	public Team getTeam(String teamKey) throws Exception {
		return TeamUtil.toTeam(_teamService.getTeam(teamKey));
	}

	@Override
	public Page<Team> getTeamByExternalLinkDomainEntityNameEntity(
			String domain, String entityName, String entityId,
			Pagination pagination)
		throws Exception {

		return Page.of(
			transform(
				_teamService.getTeams(
					domain, entityName, entityId, pagination.getStartPosition(),
					pagination.getEndPosition()),
				team -> TeamUtil.toTeam(team)),
			pagination,
			_teamService.getTeamsCount(domain, entityName, entityId));
	}

	@Override
	public Page<Team> getTeamsPage(
			String search, Filter filter, Pagination pagination, Sort[] sorts)
		throws Exception {

		return SearchUtil.search(
			booleanQuery -> {
			},
			filter, com.liferay.osb.koroneiki.taproot.model.Team.class, search,
			pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> searchContext.setCompanyId(
				contextCompany.getCompanyId()),
			document -> TeamUtil.toTeam(
				_teamLocalService.getTeam(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)))),
			sorts);
	}

	@Override
	public Team postAccountAccountKeyTeam(String accountKey, Team team)
		throws Exception {

		return TeamUtil.toTeam(
			_teamService.addTeam(accountKey, team.getName()));
	}

	@Override
	public Team putTeam(String teamKey, Team team) throws Exception {
		return TeamUtil.toTeam(
			_teamService.updateTeam(teamKey, team.getName()));
	}

	private static final EntityModel _entityModel = new TeamEntityModel();

	@Reference
	private TeamLocalService _teamLocalService;

	@Reference
	private TeamService _teamService;

}