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

package com.liferay.portal.workflow.metrics.service.internal.messaging.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionManager;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinition;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionLocalService;
import com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLADefinition;
import com.liferay.portal.workflow.metrics.service.WorkflowMetricsSLADefinitionLocalService;
import com.liferay.portal.workflow.metrics.service.internal.util.WorkflowDefinitionUtil;
import com.liferay.portal.workflow.metrics.service.test.base.BaseWorkflowMetricsTestCase;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rafael Praxedes
 */
@RunWith(Arquillian.class)
public class WorkflowMetricsSLADefinitionTransformerMessageListenerTest
	extends BaseWorkflowMetricsTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_workflowDefinition =
			_workflowDefinitionManager.deployWorkflowDefinition(
				TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
				StringUtil.randomId(), StringUtil.randomId(),
				WorkflowDefinitionUtil.getBytes());
	}

	@Test
	public void testTransform() throws Exception {
		KaleoDefinition kaleoDefinition =
			_kaleoDefinitionLocalService.getKaleoDefinition(
				_workflowDefinition.getName(),
				ServiceContextTestUtil.getServiceContext());

		retryAssertCount(
			4, "workflow-metrics-nodes", "WorkflowMetricsNodeType", "companyId",
			kaleoDefinition.getCompanyId(), "deleted", false, "processId",
			kaleoDefinition.getKaleoDefinitionId(), "version",
			kaleoDefinition.getVersion() + ".0");
		retryAssertCount(
			"workflow-metrics-processes", "WorkflowMetricsProcessType",
			"companyId", kaleoDefinition.getCompanyId(), "processId",
			kaleoDefinition.getKaleoDefinitionId(), "version",
			kaleoDefinition.getVersion() + ".0");

		_workflowMetricsSLADefinition =
			_workflowMetricsSLADefinitionLocalService.
				addWorkflowMetricsSLADefinition(
					StringPool.BLANK, StringPool.BLANK, 5000, "Abc",
					new String[0], kaleoDefinition.getKaleoDefinitionId(),
					new String[] {getInitialNodeKey(kaleoDefinition)},
					new String[] {getTerminalNodeKey(kaleoDefinition)},
					ServiceContextTestUtil.getServiceContext());

		_workflowDefinitionManager.deployWorkflowDefinition(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			kaleoDefinition.getTitle(), kaleoDefinition.getName(),
			WorkflowDefinitionUtil.getBytes());

		kaleoDefinition = _kaleoDefinitionLocalService.getKaleoDefinition(
			_workflowDefinition.getName(),
			ServiceContextTestUtil.getServiceContext());

		retryAssertCount(
			"workflow-metrics-processes", "WorkflowMetricsProcessType",
			"companyId", kaleoDefinition.getCompanyId(), "processId",
			kaleoDefinition.getKaleoDefinitionId(), "version",
			kaleoDefinition.getVersion() + ".0");
		retryAssertCount(
			4, "workflow-metrics-nodes", "WorkflowMetricsNodeType", "companyId",
			kaleoDefinition.getCompanyId(), "deleted", false, "processId",
			kaleoDefinition.getKaleoDefinitionId(), "version",
			kaleoDefinition.getVersion() + ".0");

		_workflowMetricsSLADefinitionTransformerMessageListener.receive(
			new Message());

		_workflowMetricsSLADefinition =
			_workflowMetricsSLADefinitionLocalService.
				getWorkflowMetricsSLADefinition(
					_workflowMetricsSLADefinition.
						getWorkflowMetricsSLADefinitionId());

		Assert.assertEquals(
			kaleoDefinition.getKaleoDefinitionId(),
			_workflowMetricsSLADefinition.getProcessId());
		Assert.assertEquals(
			getInitialNodeKey(kaleoDefinition),
			_workflowMetricsSLADefinition.getStartNodeKeys());
		Assert.assertEquals(
			getTerminalNodeKey(kaleoDefinition),
			_workflowMetricsSLADefinition.getStopNodeKeys());
	}

	@Inject
	private KaleoDefinitionLocalService _kaleoDefinitionLocalService;

	private WorkflowDefinition _workflowDefinition;

	@Inject
	private WorkflowDefinitionManager _workflowDefinitionManager;

	@DeleteAfterTestRun
	private WorkflowMetricsSLADefinition _workflowMetricsSLADefinition;

	@Inject
	private WorkflowMetricsSLADefinitionLocalService
		_workflowMetricsSLADefinitionLocalService;

	@Inject(
		filter = "(&(objectClass=com.liferay.portal.workflow.metrics.internal.messaging.WorkflowMetricsSLADefinitionTransformerMessageListener))"
	)
	private MessageListener
		_workflowMetricsSLADefinitionTransformerMessageListener;

}