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

package com.liferay.portal.workflow.metrics.service.test.base;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.search.CountSearchRequest;
import com.liferay.portal.search.engine.adapter.search.CountSearchResponse;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.test.util.IdempotentRetryAssert;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinition;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;
import com.liferay.portal.workflow.kaleo.model.KaleoNode;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionVersionLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoNodeLocalService;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import org.junit.Assert;

/**
 * @author Rafael Praxedes
 */
public abstract class BaseWorkflowMetricsTestCase {

	protected String getInitialNodeKey(KaleoDefinition kaleoDefinition)
		throws Exception {

		KaleoDefinitionVersion latestKaleoDefinitionVersion =
			_kaleoDefinitionVersionLocalService.getLatestKaleoDefinitionVersion(
				kaleoDefinition.getCompanyId(), kaleoDefinition.getName());

		List<KaleoNode> kaleoNodes =
			_kaleoNodeLocalService.getKaleoDefinitionVersionKaleoNodes(
				latestKaleoDefinitionVersion.getKaleoDefinitionVersionId());

		Stream<KaleoNode> stream = kaleoNodes.stream();

		return stream.filter(
			KaleoNode::isInitial
		).findFirst(
		).map(
			KaleoNode::getKaleoNodeId
		).map(
			String::valueOf
		).orElseGet(
			() -> StringPool.BLANK
		);
	}

	protected String getTerminalNodeKey(KaleoDefinition kaleoDefinition)
		throws Exception {

		KaleoDefinitionVersion latestKaleoDefinitionVersion =
			_kaleoDefinitionVersionLocalService.getLatestKaleoDefinitionVersion(
				kaleoDefinition.getCompanyId(), kaleoDefinition.getName());

		List<KaleoNode> kaleoNodes =
			_kaleoNodeLocalService.getKaleoDefinitionVersionKaleoNodes(
				latestKaleoDefinitionVersion.getKaleoDefinitionVersionId());

		Stream<KaleoNode> stream = kaleoNodes.stream();

		return stream.filter(
			KaleoNode::isTerminal
		).findFirst(
		).map(
			KaleoNode::getKaleoNodeId
		).map(
			String::valueOf
		).orElseGet(
			() -> StringPool.BLANK
		);
	}

	protected void retryAssertCount(
			long expectedCount, String indexName, String indexType,
			Object... parameters)
		throws Exception {

		if (searchEngineAdapter == null) {
			return;
		}

		if (parameters != null) {
			if ((parameters.length % 2) != 0) {
				throw new IllegalArgumentException(
					"Parameters length is not an even number");
			}

			IdempotentRetryAssert.retryAssert(
				15, TimeUnit.SECONDS,
				() -> {
					CountSearchRequest countSearchRequest =
						new CountSearchRequest();

					countSearchRequest.setIndexNames(indexName);

					BooleanQuery booleanQuery = queries.booleanQuery();

					for (int i = 0; i < parameters.length; i = i + 2) {
						booleanQuery.addMustQueryClauses(
							queries.term(
								String.valueOf(parameters[i]),
								parameters[i + 1]));
					}

					countSearchRequest.setQuery(booleanQuery);

					countSearchRequest.setTypes(indexType);

					CountSearchResponse countSearchResponse =
						searchEngineAdapter.execute(countSearchRequest);

					Assert.assertEquals(
						indexName + " " + indexType + " " +
							countSearchResponse.getSearchRequestString(),
						expectedCount, countSearchResponse.getCount());

					return null;
				});
		}
	}

	protected void retryAssertCount(
			String indexName, String indexType, Object... parameters)
		throws Exception {

		retryAssertCount(1, indexName, indexType, parameters);
	}

	@Inject
	protected Queries queries;

	@Inject(blocking = false, filter = "search.engine.impl=Elasticsearch")
	protected SearchEngineAdapter searchEngineAdapter;

	@Inject
	private KaleoDefinitionVersionLocalService
		_kaleoDefinitionVersionLocalService;

	@Inject
	private KaleoNodeLocalService _kaleoNodeLocalService;

}