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

package com.liferay.portal.search.tuning.rankings.web.internal.index;

import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.document.DeleteDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author André de Oliveira
 */
@Component(service = RankingIndexWriter.class)
public class RankingIndexWriterImpl implements RankingIndexWriter {

	@Override
	public String create(Ranking ranking) {
		IndexDocumentResponse indexDocumentResponse =
			_searchEngineAdapter.execute(
				new IndexDocumentRequest(
					RankingIndexDefinition.INDEX_NAME,
					_rankingToDocumentTranslator.translate(ranking)));

		return indexDocumentResponse.getUid();
	}

	@Override
	public void remove(String id) {
		DeleteDocumentRequest deleteDocumentRequest = new DeleteDocumentRequest(
			RankingIndexDefinition.INDEX_NAME, id);

		deleteDocumentRequest.setRefresh(true);

		_searchEngineAdapter.execute(deleteDocumentRequest);
	}

	@Override
	public void update(Ranking ranking) {
		IndexDocumentRequest indexDocumentRequest = new IndexDocumentRequest(
			RankingIndexDefinition.INDEX_NAME, ranking.getId(),
			_rankingToDocumentTranslator.translate(ranking));

		indexDocumentRequest.setRefresh(true);

		_searchEngineAdapter.execute(indexDocumentRequest);
	}

	@Reference(unbind = "-")
	protected void setRankingToDocumentTranslator(
		RankingToDocumentTranslator rankingToDocumentTranslator) {

		_rankingToDocumentTranslator = rankingToDocumentTranslator;
	}

	@Reference(unbind = "-")
	protected void setSearchEngineAdapter(
		SearchEngineAdapter searchEngineAdapter) {

		_searchEngineAdapter = searchEngineAdapter;
	}

	private RankingToDocumentTranslator _rankingToDocumentTranslator;
	private SearchEngineAdapter _searchEngineAdapter;

}