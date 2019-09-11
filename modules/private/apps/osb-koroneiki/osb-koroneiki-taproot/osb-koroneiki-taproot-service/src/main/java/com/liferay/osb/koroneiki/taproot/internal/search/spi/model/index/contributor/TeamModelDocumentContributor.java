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

package com.liferay.osb.koroneiki.taproot.internal.search.spi.model.index.contributor;

import com.liferay.osb.koroneiki.root.model.ExternalLink;
import com.liferay.osb.koroneiki.root.service.ExternalLinkLocalService;
import com.liferay.osb.koroneiki.taproot.model.Account;
import com.liferay.osb.koroneiki.taproot.model.Team;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Amos Fong
 */
@Component(
	immediate = true,
	property = "indexer.class.name=com.liferay.osb.koroneiki.taproot.model.Team",
	service = ModelDocumentContributor.class
)
public class TeamModelDocumentContributor
	implements ModelDocumentContributor<Team> {

	@Override
	public void contribute(Document document, Team team) {
		try {
			_contribute(document, team);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	private void _contribute(Document document, Team team)
		throws PortalException {

		document.addKeyword(Field.COMPANY_ID, team.getCompanyId());
		document.addDate(Field.CREATE_DATE, team.getCreateDate());
		document.addDate(Field.MODIFIED_DATE, team.getModifiedDate());
		document.addText(Field.NAME, team.getName());

		Account account = team.getAccount();

		document.addKeyword("accountKey", account.getAccountKey());

		document.addKeyword("teamKey", team.getTeamKey());

		document.addTextSortable(Field.NAME, team.getName());

		_contributeExternalLinks(document, team.getTeamId());
	}

	private void _contributeExternalLinks(Document document, long teamId)
		throws PortalException {

		Set<String> externalLinkDomains = new HashSet<>();
		Set<String> externalLinkEntityIds = new HashSet<>();
		Set<String> externalLinkEntityNames = new HashSet<>();

		List<ExternalLink> externalLinks =
			_externalLinkLocalService.getExternalLinks(
				Team.class.getName(), teamId, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		for (ExternalLink externalLink : externalLinks) {
			externalLinkDomains.add(externalLink.getDomain());
			externalLinkEntityIds.add(externalLink.getEntityId());
			externalLinkEntityNames.add(externalLink.getEntityName());
		}

		document.addKeyword(
			"externalLinkDomains",
			ArrayUtil.toStringArray(externalLinkDomains.toArray()));
		document.addKeyword(
			"externalLinkEntityIds",
			ArrayUtil.toStringArray(externalLinkEntityIds.toArray()));
		document.addKeyword(
			"externalLinkEntityNames",
			ArrayUtil.toStringArray(externalLinkEntityNames.toArray()));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TeamModelDocumentContributor.class);

	@Reference
	private ExternalLinkLocalService _externalLinkLocalService;

}