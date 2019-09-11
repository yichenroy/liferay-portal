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

package com.liferay.osb.koroneiki.taproot.service.impl;

import com.liferay.osb.koroneiki.root.service.ExternalLinkLocalService;
import com.liferay.osb.koroneiki.root.util.ModelKeyGenerator;
import com.liferay.osb.koroneiki.taproot.exception.TeamNameException;
import com.liferay.osb.koroneiki.taproot.model.Account;
import com.liferay.osb.koroneiki.taproot.model.Team;
import com.liferay.osb.koroneiki.taproot.service.AccountLocalService;
import com.liferay.osb.koroneiki.taproot.service.base.TeamLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Kyle Bischof
 */
@Component(
	property = "model.class.name=com.liferay.osb.koroneiki.taproot.model.Team",
	service = AopService.class
)
public class TeamLocalServiceImpl extends TeamLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	public Team addTeam(long userId, long accountId, String name)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		validate(0, accountId, name);

		long teamId = counterLocalService.increment();

		Team team = teamPersistence.create(teamId);

		team.setCompanyId(user.getCompanyId());
		team.setUserId(userId);
		team.setTeamKey(ModelKeyGenerator.generate(teamId));
		team.setAccountId(accountId);
		team.setName(name);

		teamPersistence.update(team);

		// Resources

		resourceLocalService.addResources(
			team.getCompanyId(), 0, userId, Team.class.getName(),
			team.getTeamId(), false, false, false);

		return team;
	}

	@Override
	public Team deleteTeam(long teamId) throws PortalException {
		return deleteTeam(teamLocalService.getTeam(teamId));
	}

	@Override
	public Team deleteTeam(Team team) throws PortalException {

		// External links

		long classNameId = classNameLocalService.getClassNameId(Team.class);

		_externalLinkLocalService.deleteExternalLinks(
			classNameId, team.getTeamId());

		// Resources

		resourceLocalService.deleteResource(
			team.getCompanyId(), Team.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, team.getTeamId());

		// Team account roles

		teamAccountRolePersistence.removeByTeamId(team.getTeamId());

		return teamPersistence.remove(team);
	}

	public List<Team> getAccountAssignedTeams(
		long accountId, int start, int end) {

		LinkedHashMap<String, Object> params = new LinkedHashMap<>();

		params.put("account", accountId);

		return teamFinder.findByName(null, params, start, end);
	}

	public int getAccountAssignedTeamsCount(long accountId) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<>();

		params.put("account", accountId);

		return teamFinder.countByName(null, params);
	}

	public List<Team> getAccountTeams(long accountId, int start, int end) {
		return teamPersistence.findByAccountId(accountId, start, end);
	}

	public int getAccountTeamsCount(long accountId) {
		return teamPersistence.countByAccountId(accountId);
	}

	public Team getTeam(String teamKey) throws PortalException {
		return teamPersistence.findByTeamKey(teamKey);
	}

	@Indexable(type = IndexableType.REINDEX)
	public Team reindex(long teamId) throws PortalException {
		return teamPersistence.findByPrimaryKey(teamId);
	}

	public Hits search(
			long companyId, String keywords, int start, int end, Sort sort)
		throws PortalException {

		try {
			Indexer<Team> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
				Team.class);

			SearchContext searchContext = new SearchContext();

			searchContext.setAndSearch(false);

			Map<String, Serializable> attributes = new HashMap<>();

			attributes.put("accountKey", keywords);
			attributes.put("externalLinkEntityIds", keywords);
			attributes.put("name", keywords);

			searchContext.setAttributes(attributes);

			searchContext.setCompanyId(companyId);
			searchContext.setEnd(end);

			if (sort != null) {
				searchContext.setSorts(sort);
			}

			searchContext.setStart(start);

			QueryConfig queryConfig = searchContext.getQueryConfig();

			queryConfig.setHighlightEnabled(false);
			queryConfig.setScoreEnabled(false);

			return indexer.search(searchContext);
		}
		catch (Exception e) {
			throw new PortalException(e);
		}
	}

	@Indexable(type = IndexableType.REINDEX)
	public Team updateTeam(long teamId, String name) throws PortalException {
		Team team = teamPersistence.findByPrimaryKey(teamId);

		validate(teamId, team.getAccountId(), name);

		team.setName(name);

		return teamPersistence.update(team);
	}

	protected void validate(long teamId, long accountId, String name)
		throws PortalException {

		Account account = _accountLocalService.getAccount(accountId);

		if (Validator.isNull(name)) {
			throw new TeamNameException();
		}

		Team team = teamPersistence.fetchByAI_N(accountId, name);

		if ((team != null) && (team.getTeamId() != teamId)) {
			throw new TeamNameException.MustNotBeDuplicate(
				name, account.getName());
		}
	}

	@Reference
	private AccountLocalService _accountLocalService;

	@Reference
	private ExternalLinkLocalService _externalLinkLocalService;

}