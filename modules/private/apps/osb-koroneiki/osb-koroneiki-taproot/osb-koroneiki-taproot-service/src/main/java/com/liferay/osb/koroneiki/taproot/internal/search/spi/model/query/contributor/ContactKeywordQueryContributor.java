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

package com.liferay.osb.koroneiki.taproot.internal.search.spi.model.query.contributor;

import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.search.query.QueryHelper;
import com.liferay.portal.search.spi.model.query.contributor.KeywordQueryContributor;
import com.liferay.portal.search.spi.model.query.contributor.helper.KeywordQueryContributorHelper;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Amos Fong
 */
@Component(
	immediate = true,
	property = "indexer.class.name=com.liferay.osb.koroneiki.taproot.model.Contact",
	service = KeywordQueryContributor.class
)
public class ContactKeywordQueryContributor implements KeywordQueryContributor {

	@Override
	public void contribute(
		String keywords, BooleanQuery booleanQuery,
		KeywordQueryContributorHelper keywordQueryContributorHelper) {

		SearchContext searchContext =
			keywordQueryContributorHelper.getSearchContext();

		queryHelper.addSearchTerm(
			booleanQuery, searchContext, "contactRoleKeys", false);
		queryHelper.addSearchTerm(
			booleanQuery, searchContext, "emailAddress", false);
		queryHelper.addSearchTerm(
			booleanQuery, searchContext, "externalLinkEntityIds", false);
		queryHelper.addSearchTerm(
			booleanQuery, searchContext, "firstName", false);
		queryHelper.addSearchTerm(
			booleanQuery, searchContext, "lastName", false);
		queryHelper.addSearchTerm(
			booleanQuery, searchContext, "middleName", false);
		queryHelper.addSearchTerm(booleanQuery, searchContext, "oktaId", false);
		queryHelper.addSearchTerm(booleanQuery, searchContext, "uuid", false);
	}

	@Reference
	protected QueryHelper queryHelper;

}