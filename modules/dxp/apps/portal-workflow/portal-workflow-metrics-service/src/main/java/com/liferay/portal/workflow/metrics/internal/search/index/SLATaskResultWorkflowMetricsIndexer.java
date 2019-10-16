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

package com.liferay.portal.workflow.metrics.internal.search.index;

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.search.engine.adapter.document.BulkDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;
import com.liferay.portal.workflow.metrics.internal.sla.processor.WorkflowMetricsSLATaskResult;

import java.sql.Timestamp;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Inácio Nery
 */
@Component(
	immediate = true, service = SLATaskResultWorkflowMetricsIndexer.class
)
public class SLATaskResultWorkflowMetricsIndexer
	extends BaseSLAWorkflowMetricsIndexer {

	public void addDocuments(
		List<WorkflowMetricsSLATaskResult> workflowMetricsSLATaskResults) {

		if (searchEngineAdapter == null) {
			return;
		}

		BulkDocumentRequest bulkDocumentRequest = new BulkDocumentRequest();

		workflowMetricsSLATaskResults.forEach(
			workflowMetricsSLATaskResult -> {
				Document document = createDocument(
					workflowMetricsSLATaskResult);

				bulkDocumentRequest.addBulkableDocumentRequest(
					new IndexDocumentRequest(
						getIndexName(), document.getUID(), document) {

						{
							setType(getIndexType());
						}
					});
			});

		if (ListUtil.isNotEmpty(
				bulkDocumentRequest.getBulkableDocumentRequests())) {

			searchEngineAdapter.execute(bulkDocumentRequest);
		}
	}

	public Document createDocument(
		WorkflowMetricsSLATaskResult workflowMetricsSLATaskResult) {

		Document document = new DocumentImpl();

		document.addUID(
			"WorkflowMetricsSLATaskResult",
			digest(
				workflowMetricsSLATaskResult.getCompanyId(),
				workflowMetricsSLATaskResult.getInstanceId(),
				workflowMetricsSLATaskResult.getProcessId(),
				workflowMetricsSLATaskResult.getSLADefinitionId(),
				workflowMetricsSLATaskResult.getTaskId(),
				workflowMetricsSLATaskResult.getTokenId()));

		document.addKeyword(
			"companyId", workflowMetricsSLATaskResult.getCompanyId());
		document.addKeyword(
			"breached", workflowMetricsSLATaskResult.isBreached());
		document.addKeyword("deleted", false);
		document.addKeyword(
			"instanceId", workflowMetricsSLATaskResult.getInstanceId());
		document.addDateSortable(
			"lastCheckDate",
			Timestamp.valueOf(
				workflowMetricsSLATaskResult.getLastCheckLocalDateTime()));
		document.addKeyword("onTime", workflowMetricsSLATaskResult.isOnTime());
		document.addKeyword(
			"processId", workflowMetricsSLATaskResult.getProcessId());
		document.addKeyword(
			"slaDefinitionId",
			workflowMetricsSLATaskResult.getSLADefinitionId());
		document.addKeyword(
			"status",
			String.valueOf(
				workflowMetricsSLATaskResult.getWorkfowMetricsSLAStatus()));
		document.addKeyword("taskId", workflowMetricsSLATaskResult.getTaskId());
		document.addKeyword(
			"taskName", workflowMetricsSLATaskResult.getTaskName());
		document.addKeyword(
			"tokenId", workflowMetricsSLATaskResult.getTokenId());

		return document;
	}

	@Override
	public void reindex(long companyId) {
	}

	@Override
	protected String getIndexName() {
		return "workflow-metrics-sla-task-results";
	}

	@Override
	protected String getIndexType() {
		return "WorkflowMetricsSLATaskResultType";
	}

}