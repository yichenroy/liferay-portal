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

package com.liferay.portal.search.tuning.rankings.web.internal.searcher;

import com.liferay.portal.kernel.search.SearchEngine;
import com.liferay.portal.kernel.search.SearchEngineHelper;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.spi.searcher.SearchRequestContributor;
import com.liferay.portal.search.tuning.rankings.web.internal.index.Ranking;
import com.liferay.portal.search.tuning.rankings.web.internal.index.RankingIndexReader;

import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author André de Oliveira
 */
@Component(
	immediate = true,
	property = "search.request.contributor.id=com.liferay.portal.search.ranking",
	service = SearchRequestContributor.class
)
public class RankingSearchRequestContributor
	implements SearchRequestContributor {

	@Override
	public SearchRequest contribute(SearchRequest searchRequest) {
		if (isSearchEngine("Solr")) {
			return searchRequest;
		}

		Optional<Ranking> optional =
			rankingIndexReader.fetchByQueryStringOptional(
				searchRequest.getQueryString());

		return optional.map(
			ranking -> contribute(searchRequest, ranking)
		).orElse(
			searchRequest
		);
	}

	protected SearchRequest contribute(
		SearchRequest searchRequest, Ranking ranking) {

		SearchRequestBuilder searchRequestBuilder =
			searchRequestBuilderFactory.builder(searchRequest);

		rankingSearchRequestHelper.contribute(searchRequestBuilder, ranking);

		return searchRequestBuilder.build();
	}

	protected boolean isSearchEngine(String engine) {
		SearchEngine searchEngine = searchEngineHelper.getSearchEngine(
			searchEngineHelper.getDefaultSearchEngineId());

		String vendor = searchEngine.getVendor();

		return vendor.equals(engine);
	}

	@Reference
	protected RankingIndexReader rankingIndexReader;

	@Reference
	protected RankingSearchRequestHelper rankingSearchRequestHelper;

	@Reference
	protected SearchEngineHelper searchEngineHelper;

	@Reference
	protected SearchRequestBuilderFactory searchRequestBuilderFactory;

}