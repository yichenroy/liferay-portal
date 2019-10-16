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

package com.liferay.portal.workflow.metrics.rest.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.function.UnsafeTriConsumer;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Node;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.NodeKey;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Process;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.SLA;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.StartNodeKeys;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.StopNodeKeys;
import com.liferay.portal.workflow.metrics.rest.client.pagination.Page;
import com.liferay.portal.workflow.metrics.rest.client.pagination.Pagination;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.test.helper.WorkflowMetricsRESTTestHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rafael Praxedes
 */
@RunWith(Arquillian.class)
public class SLAResourceTest extends BaseSLAResourceTestCase {

	@BeforeClass
	public static void setUpClass() throws Exception {
		BaseTaskResourceTestCase.setUpClass();

		_workflowMetricsRESTTestHelper = new WorkflowMetricsRESTTestHelper(
			_queries, _searchEngineAdapter);
	}

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_process = _workflowMetricsRESTTestHelper.addProcess(
			testGroup.getCompanyId());

		_node = _workflowMetricsRESTTestHelper.addNode(
			testGroup.getCompanyId(), _process.getId(), "1.0");
	}

	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		if (_node != null) {
			_workflowMetricsRESTTestHelper.deleteNode(
				testGroup.getCompanyId(), _node, _process.getId());
		}

		if (_process != null) {
			_workflowMetricsRESTTestHelper.deleteProcess(
				testGroup.getCompanyId(), _process);
		}

		_deleteSLAs();
	}

	@Override
	@Test
	public void testGetProcessSLAsPage() throws Exception {
		super.testGetProcessSLAsPage();

		_testGetProcessSLAsPage(
			WorkflowConstants.STATUS_APPROVED,
			(sla1, sla2, page) -> assertEquals(
				Collections.singletonList(sla1), (List<SLA>)page.getItems()));
		_testGetProcessSLAsPage(
			WorkflowConstants.STATUS_DRAFT,
			(sla1, sla2, page) -> assertEquals(
				Collections.singletonList(sla2), (List<SLA>)page.getItems()));
		_testGetProcessSLAsPage(
			null,
			(sla1, sla2, page) -> assertEquals(
				Arrays.asList(sla2, sla1), (List<SLA>)page.getItems()));
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {
			"duration", "name", "processId", "startNodeKeys", "status",
			"stopNodeKeys"
		};
	}

	@Override
	protected SLA randomSLA() throws Exception {
		SLA sla = super.randomSLA();

		sla.setCalendarKey(StringPool.BLANK);
		sla.setProcessId(_process.getId());
		sla.setStartNodeKeys(
			new StartNodeKeys() {
				{
					nodeKeys = new NodeKey[] {
						new NodeKey() {
							{
								id = String.valueOf(_node.getId());
								executionType = "enter";
							}
						}
					};
					status = WorkflowConstants.STATUS_APPROVED;
				}
			});
		sla.setStatus(WorkflowConstants.STATUS_APPROVED);
		sla.setStopNodeKeys(
			new StopNodeKeys() {
				{
					nodeKeys = new NodeKey[] {
						new NodeKey() {
							{
								id = String.valueOf(_node.getId());
								executionType = "leave";
							}
						}
					};
					status = WorkflowConstants.STATUS_APPROVED;
				}
			});

		return sla;
	}

	@Override
	protected SLA testDeleteSLA_addSLA() throws Exception {
		return super.testPostProcessSLA_addSLA(randomSLA());
	}

	@Override
	protected SLA testGetProcessSLAsPage_addSLA(Long processId, SLA sla)
		throws Exception {

		sla = super.testGetProcessSLAsPage_addSLA(processId, sla);

		_slas.add(sla);

		return sla;
	}

	@Override
	protected Long testGetProcessSLAsPage_getProcessId() throws Exception {
		return _process.getId();
	}

	@Override
	protected SLA testGetSLA_addSLA() throws Exception {
		SLA sla = super.testPostProcessSLA_addSLA(randomSLA());

		_slas.add(sla);

		return sla;
	}

	@Override
	protected SLA testPostProcessSLA_addSLA(SLA sla) throws Exception {
		sla = super.testPostProcessSLA_addSLA(sla);

		_slas.add(sla);

		return sla;
	}

	@Override
	protected SLA testPutSLA_addSLA() throws Exception {
		SLA sla = super.testPostProcessSLA_addSLA(randomSLA());

		_slas.add(sla);

		return sla;
	}

	private void _deleteSLAs() throws Exception {
		for (SLA sla : _slas) {
			slaResource.deleteSLA(sla.getId());
		}
	}

	private void _testGetProcessSLAsPage(
			Integer status,
			UnsafeTriConsumer<SLA, SLA, Page<SLA>, Exception> unsafeTriConsumer)
		throws Exception {

		_deleteSLAs();

		SLA sla1 = testGetProcessSLAsPage_addSLA(_process.getId(), randomSLA());

		SLA sla2 = testGetProcessSLAsPage_addSLA(_process.getId(), randomSLA());

		sla2.setStatus(WorkflowConstants.STATUS_DRAFT);

		sla2 = slaResource.putSLA(sla2.getId(), sla2);

		Page<SLA> page = slaResource.getProcessSLAsPage(
			_process.getId(), status, Pagination.of(1, 2));

		unsafeTriConsumer.accept(sla1, sla2, page);
	}

	@Inject
	private static Queries _queries;

	@Inject(blocking = false, filter = "search.engine.impl=Elasticsearch")
	private static SearchEngineAdapter _searchEngineAdapter;

	private static WorkflowMetricsRESTTestHelper _workflowMetricsRESTTestHelper;

	private Node _node;
	private Process _process;
	private final List<SLA> _slas = new ArrayList<>();

}