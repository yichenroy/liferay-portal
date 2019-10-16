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

package com.liferay.portal.workflow.metrics.rest.internal.resource.v1_0;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.search.aggregation.AggregationResult;
import com.liferay.portal.search.aggregation.Aggregations;
import com.liferay.portal.search.aggregation.bucket.Bucket;
import com.liferay.portal.search.aggregation.bucket.FilterAggregation;
import com.liferay.portal.search.aggregation.bucket.FilterAggregationResult;
import com.liferay.portal.search.aggregation.bucket.TermsAggregation;
import com.liferay.portal.search.aggregation.bucket.TermsAggregationResult;
import com.liferay.portal.search.aggregation.metrics.CardinalityAggregation;
import com.liferay.portal.search.aggregation.metrics.CardinalityAggregationResult;
import com.liferay.portal.search.aggregation.pipeline.BucketSortPipelineAggregation;
import com.liferay.portal.search.engine.adapter.search.SearchRequestExecutor;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.query.TermsQuery;
import com.liferay.portal.search.sort.FieldSort;
import com.liferay.portal.search.sort.SortOrder;
import com.liferay.portal.search.sort.Sorts;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.Task;
import com.liferay.portal.workflow.metrics.rest.internal.odata.entity.v1_0.TaskEntityModel;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.TaskResource;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ws.rs.core.MultivaluedMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Rafael Praxedes
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/task.properties",
	scope = ServiceScope.PROTOTYPE,
	service = {EntityModelResource.class, TaskResource.class}
)
public class TaskResourceImpl
	extends BaseTaskResourceImpl implements EntityModelResource {

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap)
		throws Exception {

		return _entityModel;
	}

	@Override
	public Page<Task> getProcessTasksPage(
		Long processId, Pagination pagination, Sort[] sorts) {

		FieldSort fieldSort = _toFieldSort(sorts);

		Map<String, List<String>> instanceIdsMap = _getInstanceIdsMap(
			fieldSort, pagination, processId);

		Map<String, Task> tasksMap = _getTasksMap(
			processId, instanceIdsMap.keySet());

		long count = tasksMap.size();

		if (count > 0) {
			return Page.of(
				_getTasks(fieldSort, instanceIdsMap, pagination, tasksMap),
				pagination, count);
		}

		return Page.of(Collections.emptyList());
	}

	private BooleanQuery _createBooleanQuery(long processId) {
		BooleanQuery booleanQuery = _queries.booleanQuery();

		return booleanQuery.addMustQueryClauses(
			_queries.term("processId", processId),
			_queries.term("version", _getProcessVersion(processId)));
	}

	private BooleanQuery _createBooleanQuery(
		long processId, Set<String> taskNames) {

		BooleanQuery booleanQuery = _queries.booleanQuery();

		booleanQuery.addFilterQueryClauses(
			_createFilterBooleanQuery(processId, taskNames));

		return booleanQuery.addMustQueryClauses(
			_queries.term("companyId", contextCompany.getCompanyId()),
			_queries.term("deleted", false), _queries.term("type", "TASK"));
	}

	private BooleanQuery _createBooleanQuery(Set<String> taskNames) {
		BooleanQuery booleanQuery = _queries.booleanQuery();

		TermsQuery termsQuery = _queries.terms("taskName");

		termsQuery.addValues(taskNames.toArray(new String[taskNames.size()]));

		return booleanQuery.addMustQueryClauses(
			_queries.term("companyId", contextCompany.getCompanyId()),
			_queries.term("deleted", false), termsQuery);
	}

	private BucketSortPipelineAggregation _createBucketSortPipelineAggregation(
		FieldSort fieldSort, Pagination pagination) {

		BucketSortPipelineAggregation bucketSortPipelineAggregation =
			_aggregations.bucketSort("sort");

		bucketSortPipelineAggregation.addSortFields(fieldSort);
		bucketSortPipelineAggregation.setFrom(pagination.getStartPosition());
		bucketSortPipelineAggregation.setSize(pagination.getPageSize() + 1);

		return bucketSortPipelineAggregation;
	}

	private BooleanQuery _createFilterBooleanQuery(
		long processId, Set<String> taskNames) {

		BooleanQuery booleanQuery = _queries.booleanQuery();

		if (!taskNames.isEmpty()) {
			TermsQuery termsQuery = _queries.terms("name");

			termsQuery.addValues(
				taskNames.toArray(new String[taskNames.size()]));

			booleanQuery.addShouldQueryClauses(termsQuery);
		}

		return booleanQuery.addShouldQueryClauses(
			_createBooleanQuery(processId));
	}

	private Task _createTask(String taskName) {
		return new Task() {
			{
				instanceCount = 0L;
				name = taskName;
			}
		};
	}

	private List<String> _getBucketKeys(Bucket bucket, String name) {
		TermsAggregationResult termsAggregationResult =
			(TermsAggregationResult)bucket.getChildAggregationResult(name);

		Collection<Bucket> buckets = termsAggregationResult.getBuckets();

		Stream<Bucket> stream = buckets.stream();

		return stream.map(
			Bucket::getKey
		).collect(
			Collectors.toList()
		);
	}

	private Map<String, List<String>> _getInstanceIdsMap(
		FieldSort fieldSort, Pagination pagination, long processId) {

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		TermsAggregation taskIdTermsAggregation = _aggregations.terms(
			"taskName", "taskName");

		TermsAggregation instanceIdTermsAggregation = _aggregations.terms(
			"instanceId", "instanceId");

		instanceIdTermsAggregation.setSize(10000);

		taskIdTermsAggregation.addChildrenAggregations(
			_aggregations.cardinality("instanceCount", "instanceId"),
			instanceIdTermsAggregation);

		if ((fieldSort != null) &&
			_isOrderByInstanceCount(fieldSort.getField())) {

			taskIdTermsAggregation.addPipelineAggregation(
				_createBucketSortPipelineAggregation(fieldSort, pagination));
		}

		taskIdTermsAggregation.setSize(10000);

		searchSearchRequest.addAggregation(taskIdTermsAggregation);

		searchSearchRequest.setIndexNames("workflow-metrics-tokens");

		BooleanQuery booleanQuery = _queries.booleanQuery();

		searchSearchRequest.setQuery(
			booleanQuery.addMustQueryClauses(
				_queries.term("companyId", contextCompany.getCompanyId()),
				_queries.term("completed", false),
				_queries.term("deleted", false),
				_queries.term("processId", processId)));

		SearchSearchResponse searchSearchResponse =
			_searchRequestExecutor.executeSearchRequest(searchSearchRequest);

		Map<String, AggregationResult> aggregationResultsMap =
			searchSearchResponse.getAggregationResultsMap();

		TermsAggregationResult termsAggregationResult =
			(TermsAggregationResult)aggregationResultsMap.get("taskName");

		Collection<Bucket> buckets = termsAggregationResult.getBuckets();

		Stream<Bucket> stream = buckets.stream();

		return stream.collect(
			LinkedHashMap::new,
			(map, bucket) -> map.put(
				bucket.getKey(), _getBucketKeys(bucket, "instanceId")),
			Map::putAll);
	}

	private String _getProcessVersion(long processId) {
		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		searchSearchRequest.setIndexNames("workflow-metrics-processes");

		BooleanQuery booleanQuery = _queries.booleanQuery();

		searchSearchRequest.setQuery(
			booleanQuery.addMustQueryClauses(
				_queries.term("processId", processId)));

		searchSearchRequest.setSelectedFieldNames("version");

		SearchSearchResponse searchSearchResponse =
			_searchRequestExecutor.executeSearchRequest(searchSearchRequest);

		SearchHits searchHits = searchSearchResponse.getSearchHits();

		return Stream.of(
			searchHits.getSearchHits()
		).flatMap(
			List::stream
		).map(
			SearchHit::getDocument
		).map(
			document -> document.getString("version")
		).findFirst(
		).orElse(
			StringPool.BLANK
		);
	}

	private TermsAggregationResult _getSLATermsAggregationResult(
		FieldSort fieldSort, Pagination pagination, Set<String> taskNames) {

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		TermsAggregation termsAggregation = _aggregations.terms(
			"taskName", "taskName");

		CardinalityAggregation cardinalityAggregation =
			_aggregations.cardinality("instanceCount", "instanceId");

		FilterAggregation onTimeFilterAggregation = _aggregations.filter(
			"onTime", _queries.term("onTime", true));

		onTimeFilterAggregation.addChildAggregation(cardinalityAggregation);

		FilterAggregation overdueFilterAggregation = _aggregations.filter(
			"overdue", _queries.term("onTime", false));

		overdueFilterAggregation.addChildAggregation(cardinalityAggregation);

		termsAggregation.addChildrenAggregations(
			cardinalityAggregation, onTimeFilterAggregation,
			overdueFilterAggregation);

		if ((fieldSort != null) &&
			!_isOrderByInstanceCount(fieldSort.getField())) {

			termsAggregation.addPipelineAggregation(
				_createBucketSortPipelineAggregation(fieldSort, pagination));
		}

		termsAggregation.setSize(taskNames.size());

		searchSearchRequest.addAggregation(termsAggregation);
		searchSearchRequest.setIndexNames("workflow-metrics-sla-task-result");
		searchSearchRequest.setQuery(_createBooleanQuery(taskNames));

		SearchSearchResponse searchSearchResponse =
			_searchRequestExecutor.executeSearchRequest(searchSearchRequest);

		Map<String, AggregationResult> aggregationResultsMap =
			searchSearchResponse.getAggregationResultsMap();

		return (TermsAggregationResult)aggregationResultsMap.get("taskName");
	}

	private Map<String, Task> _getTasksMap(
		long processId, Set<String> taskNames) {

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		TermsAggregation termsAggregation = _aggregations.terms("name", "name");

		termsAggregation.setSize(10000);

		searchSearchRequest.addAggregation(termsAggregation);

		searchSearchRequest.setIndexNames("workflow-metrics-nodes");
		searchSearchRequest.setQuery(_createBooleanQuery(processId, taskNames));

		SearchSearchResponse searchSearchResponse =
			_searchRequestExecutor.executeSearchRequest(searchSearchRequest);

		Map<String, AggregationResult> aggregationResultsMap =
			searchSearchResponse.getAggregationResultsMap();

		TermsAggregationResult termsAggregationResult =
			(TermsAggregationResult)aggregationResultsMap.get("name");

		Collection<Bucket> buckets = termsAggregationResult.getBuckets();

		Stream<Bucket> stream = buckets.stream();

		return stream.map(
			Bucket::getKey
		).map(
			this::_createTask
		).collect(
			LinkedHashMap::new, (map, task) -> map.put(task.getName(), task),
			Map::putAll
		);
	}

	private Collection<Task> _getTasks(
		FieldSort fieldSort, Map<String, List<String>> instanceIdsMap,
		Pagination pagination, Map<String, Task> tasksMap) {

		List<Task> tasks = new LinkedList<>();

		TermsAggregationResult slaTermsAggregationResult =
			_getSLATermsAggregationResult(
				fieldSort, pagination, tasksMap.keySet());

		if (_isOrderByInstanceCount(fieldSort.getField())) {
			instanceIdsMap.forEach(
				(taskName, instanceIds) -> {
					Task task = tasksMap.remove(taskName);

					_populateTaskWithSLAMetrics(
						slaTermsAggregationResult.getBucket(taskName), task);
					_setInstanceCount(instanceIds, task);

					tasks.add(task);
				});
		}
		else {
			for (Bucket bucket : slaTermsAggregationResult.getBuckets()) {
				Task task = tasksMap.remove(bucket.getKey());

				_populateTaskWithSLAMetrics(bucket, task);
				_setInstanceCount(instanceIdsMap.get(bucket.getKey()), task);

				tasks.add(task);
			}
		}

		if (tasks.size() > pagination.getPageSize()) {
			return tasks.subList(0, tasks.size() - 1);
		}

		return tasks;
	}

	private boolean _isOrderByInstanceCount(String fieldName) {
		return StringUtil.startsWith(fieldName, "instanceCount");
	}

	private void _populateTaskWithSLAMetrics(Bucket bucket, Task task) {
		CardinalityAggregationResult cardinalityAggregationResult =
			(CardinalityAggregationResult)bucket.getChildAggregationResult(
				"instanceCount");

		if (cardinalityAggregationResult.getValue() <= 1) {
			return;
		}

		_setOnTimeInstanceCount(bucket, task);
		_setOverdueInstanceCount(bucket, task);
	}

	private void _setInstanceCount(List<String> instanceIds, Task task) {
		if (ListUtil.isEmpty(instanceIds)) {
			return;
		}

		task.setInstanceCount(Long.valueOf(instanceIds.size()) - 1);
	}

	private void _setOnTimeInstanceCount(Bucket bucket, Task task) {
		if (bucket == null) {
			return;
		}

		FilterAggregationResult filterAggregationResult =
			(FilterAggregationResult)bucket.getChildAggregationResult("onTime");

		CardinalityAggregationResult cardinalityAggregationResult =
			(CardinalityAggregationResult)
				filterAggregationResult.getChildAggregationResult(
					"instanceCount");

		task.setOnTimeInstanceCount(cardinalityAggregationResult.getValue());
	}

	private void _setOverdueInstanceCount(Bucket bucket, Task task) {
		if (bucket == null) {
			return;
		}

		FilterAggregationResult filterAggregationResult =
			(FilterAggregationResult)bucket.getChildAggregationResult(
				"overdue");

		CardinalityAggregationResult cardinalityAggregationResult =
			(CardinalityAggregationResult)
				filterAggregationResult.getChildAggregationResult(
					"instanceCount");

		task.setOverdueInstanceCount(cardinalityAggregationResult.getValue());
	}

	private FieldSort _toFieldSort(Sort[] sorts) {
		Sort sort = new Sort("instanceCount", false);

		if (sorts != null) {
			sort = sorts[0];
		}

		String fieldName = sort.getFieldName();

		if (!_isOrderByInstanceCount(fieldName)) {
			fieldName = StringUtil.extractFirst(fieldName, "InstanceCount");

			fieldName = fieldName.concat(
				StringPool.PERIOD
			).concat(
				"instanceCount"
			);
		}

		FieldSort fieldSort = _sorts.field(fieldName);

		fieldSort.setSortOrder(
			sort.isReverse() ? SortOrder.DESC : SortOrder.ASC);

		return fieldSort;
	}

	private static final EntityModel _entityModel = new TaskEntityModel();

	@Reference
	private Aggregations _aggregations;

	@Reference
	private Queries _queries;

	@Reference
	private SearchRequestExecutor _searchRequestExecutor;

	@Reference
	private Sorts _sorts;

}