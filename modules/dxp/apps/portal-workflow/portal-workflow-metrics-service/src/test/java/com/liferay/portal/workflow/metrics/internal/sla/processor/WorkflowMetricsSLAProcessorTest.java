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

package com.liferay.portal.workflow.metrics.internal.sla.processor;

import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.document.DocumentBuilder;
import com.liferay.portal.search.internal.document.DocumentBuilderImpl;
import com.liferay.portal.util.PropsImpl;
import com.liferay.portal.workflow.metrics.internal.sla.WorkfowMetricsSLAStatus;
import com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLADefinition;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Rafael Praxedes
 */
@RunWith(PowerMockRunner.class)
public class WorkflowMetricsSLAProcessorTest extends PowerMockito {

	@BeforeClass
	public static void setUpClass() throws Exception {
		PropsUtil.setProps(new PropsImpl());
	}

	@Test
	public void testProcessOnTimeInstance() {
		LocalDateTime localDateTime = _createLocalDateTime();

		LocalDateTime createDateLocalDateTime = localDateTime.minus(
			5, ChronoUnit.SECONDS);

		_test(
			createDateLocalDateTime, 5000, 5000, localDateTime, true, 0,
			WorkfowMetricsSLAStatus.RUNNING,
			_createDocument(
				new HashMap<String, Object>() {
					{
						put("taskId", 1);
						put("createDate", _format(createDateLocalDateTime));
					}
				}));
	}

	@Test
	public void testProcessOnTimeInstanceWithParallelTasks() {
		LocalDateTime localDateTime = _createLocalDateTime();

		LocalDateTime createDateLocalDateTime = localDateTime.minus(
			10, ChronoUnit.SECONDS);

		_test(
			createDateLocalDateTime, 10000, 10000, localDateTime, true, 0,
			WorkfowMetricsSLAStatus.RUNNING,
			_createDocument(
				new HashMap<String, Object>() {
					{
						put("taskId", 1);
						put(
							"completionDate",
							_format(
								localDateTime.minus(4, ChronoUnit.SECONDS)));
						put("createDate", _format(createDateLocalDateTime));
					}
				}),
			_createDocument(
				new HashMap<String, Object>() {
					{
						put("taskId", 2);
						put(
							"createDate",
							_format(
								localDateTime.minus(5, ChronoUnit.SECONDS)));
					}
				}));
	}

	@Test
	public void testProcessOnTimeSLACompleted() {
		LocalDateTime localDateTime = _createLocalDateTime();

		LocalDateTime createDateLocalDateTime = localDateTime.minus(
			10, ChronoUnit.SECONDS);

		WorkflowMetricsSLADefinition workflowMetricsSLADefinition = mock(
			WorkflowMetricsSLADefinition.class);

		when(
			workflowMetricsSLADefinition.getDuration()
		).thenReturn(
			10000L
		);

		when(
			workflowMetricsSLADefinition.getPauseNodeNames()
		).thenReturn(
			"2"
		);

		when(
			workflowMetricsSLADefinition.getStartNodeNames()
		).thenReturn(
			"1:enter"
		);

		when(
			workflowMetricsSLADefinition.getStopNodeNames()
		).thenReturn(
			"2:leave"
		);

		_test(
			createDateLocalDateTime, 10000, null, localDateTime, true, 0, 1,
			workflowMetricsSLADefinition, WorkfowMetricsSLAStatus.COMPLETED,
			_createDocument(
				new HashMap<String, Object>() {
					{
						put("taskId", 1);
						put(
							"completionDate",
							_format(
								localDateTime.minus(5, ChronoUnit.SECONDS)));
						put("createDate", _format(createDateLocalDateTime));
					}
				}),
			_createDocument(
				new HashMap<String, Object>() {
					{
						put("taskId", 2);
						put("completionDate", _format(localDateTime));
						put(
							"createDate",
							_format(
								localDateTime.minus(5, ChronoUnit.SECONDS)));
					}
				}));
	}

	@Test
	public void testProcessOnTimeSLANotStarted() {
		LocalDateTime localDateTime = _createLocalDateTime();

		LocalDateTime createDateLocalDateTime = localDateTime.minus(
			5, ChronoUnit.SECONDS);

		WorkflowMetricsSLADefinition workflowMetricsSLADefinition = mock(
			WorkflowMetricsSLADefinition.class);

		_test(
			createDateLocalDateTime, 0, null, localDateTime, true, 0, 0,
			workflowMetricsSLADefinition, WorkfowMetricsSLAStatus.NEW,
			_createDocument(
				new HashMap<String, Object>() {
					{
						put("taskId", 1);
						put("createDate", _format(createDateLocalDateTime));
					}
				}));
	}

	@Test
	public void testProcessOnTimeSLAPaused() {
		LocalDateTime localDateTime = _createLocalDateTime();

		LocalDateTime createDateLocalDateTime = localDateTime.minus(
			10, ChronoUnit.SECONDS);

		WorkflowMetricsSLADefinition workflowMetricsSLADefinition = mock(
			WorkflowMetricsSLADefinition.class);

		when(
			workflowMetricsSLADefinition.getDuration()
		).thenReturn(
			5000L
		);

		when(
			workflowMetricsSLADefinition.getStartNodeNames()
		).thenReturn(
			"1:enter"
		);

		when(
			workflowMetricsSLADefinition.getPauseNodeNames()
		).thenReturn(
			"2:enter"
		);

		_test(
			createDateLocalDateTime, 5000, null, localDateTime, true, 0, 1,
			workflowMetricsSLADefinition, WorkfowMetricsSLAStatus.PAUSED,
			_createDocument(
				new HashMap<String, Object>() {
					{
						put("taskId", 1);
						put(
							"completionDate",
							_format(
								localDateTime.minus(5, ChronoUnit.SECONDS)));
						put("createDate", _format(createDateLocalDateTime));
					}
				}),
			_createDocument(
				new HashMap<String, Object>() {
					{
						put("taskId", 2);
						put(
							"createDate",
							_format(
								localDateTime.minus(5, ChronoUnit.SECONDS)));
					}
				}));
	}

	@Test
	public void testProcessOverdueInstance() {
		LocalDateTime localDateTime = _createLocalDateTime();

		LocalDateTime createDateLocalDateTime = localDateTime.minus(
			6, ChronoUnit.SECONDS);

		_test(
			createDateLocalDateTime, 5000, 6000, localDateTime, false, -1000,
			WorkfowMetricsSLAStatus.RUNNING,
			_createDocument(
				new HashMap<String, Object>() {
					{
						put("taskId", 1);
						put("createDate", _format(createDateLocalDateTime));
					}
				}));
	}

	@Test
	public void testProcessOverdueInstanceWithParallelTasks() {
		LocalDateTime localDateTime = _createLocalDateTime();

		LocalDateTime createDateLocalDateTime = localDateTime.minus(
			10, ChronoUnit.SECONDS);

		_test(
			createDateLocalDateTime, 5000, 10000, localDateTime, false, -5000,
			WorkfowMetricsSLAStatus.RUNNING,
			_createDocument(
				new HashMap<String, Object>() {
					{
						put("taskId", 1);
						put(
							"completionDate",
							_format(
								localDateTime.minus(4, ChronoUnit.SECONDS)));
						put("createDate", _format(createDateLocalDateTime));
					}
				}),
			_createDocument(
				new HashMap<String, Object>() {
					{
						put("taskId", 2);
						put("createDate", _format(createDateLocalDateTime));
					}
				}));
	}

	private Document _createDocument(Map<String, Object> values) {
		DocumentBuilder documentBuilder = new DocumentBuilderImpl();

		for (Entry<String, Object> entry : values.entrySet()) {
			documentBuilder.setValue(entry.getKey(), entry.getValue());
		}

		return documentBuilder.build();
	}

	private LocalDateTime _createLocalDateTime() {
		LocalDateTime localDateTime = LocalDateTime.now();

		return localDateTime.withNano(0);
	}

	private String _format(LocalDateTime localDateTime) {
		return _dateTimeFormatter.format(localDateTime);
	}

	private void _test(
		LocalDateTime createDateLocalDateTime, long elapsedTime,
		WorkflowMetricsSLAProcessResult lastWorkflowMetricsSLAProcessResult,
		LocalDateTime localDateTime, boolean onTime, long remainingTime,
		long startNodeId,
		WorkflowMetricsSLADefinition workflowMetricsSLADefinition,
		WorkfowMetricsSLAStatus workfowMetricsSLAStatus,
		Document... documents) {

		WorkflowMetricsSLAProcessor workflowMetricsSLAProcessor =
			new WorkflowMetricsSLAProcessor() {

				@Override
				protected WorkflowMetricsSLAProcessResult
					fetchLastWorkflowMetricsSLAProcessResult(
						WorkflowMetricsSLADefinition
							workflowMetricsSLADefinition,
						long instanceId) {

					return lastWorkflowMetricsSLAProcessResult;
				}

				@Override
				protected List<Document> getDocuments(
					long companyId, long instanceId,
					LocalDateTime lastCheckLocalDateTime) {

					return Arrays.asList(documents);
				}

			};

		Optional<WorkflowMetricsSLAProcessResult> optional =
			workflowMetricsSLAProcessor.process(
				0, createDateLocalDateTime, 0, localDateTime, startNodeId,
				workflowMetricsSLADefinition);

		WorkflowMetricsSLAProcessResult workflowMetricsSLAProcessResult =
			optional.get();

		Assert.assertEquals(
			elapsedTime, workflowMetricsSLAProcessResult.getElapsedTime());
		Assert.assertEquals(
			remainingTime, workflowMetricsSLAProcessResult.getRemainingTime());
		Assert.assertEquals(workflowMetricsSLAProcessResult.isOnTime(), onTime);
		Assert.assertEquals(
			workflowMetricsSLAProcessResult.getWorkfowMetricsSLAStatus(),
			workfowMetricsSLAStatus);
	}

	private void _test(
		LocalDateTime createDateLocalDateTime, long duration, long elapsedTime,
		LocalDateTime localDateTime, boolean onTime, long remainingTime,
		WorkfowMetricsSLAStatus workfowMetricsSLAStatus,
		Document... documents) {

		WorkflowMetricsSLADefinition workflowMetricsSLADefinition = mock(
			WorkflowMetricsSLADefinition.class);

		when(
			workflowMetricsSLADefinition.getDuration()
		).thenReturn(
			duration
		);

		when(
			workflowMetricsSLADefinition.getStartNodeNames()
		).thenReturn(
			"0"
		);

		_test(
			createDateLocalDateTime, elapsedTime, null, localDateTime, onTime,
			remainingTime, 0, workflowMetricsSLADefinition,
			workfowMetricsSLAStatus, documents);
	}

	private final DateTimeFormatter _dateTimeFormatter =
		DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

}