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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.Process;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.ProcessResource;

import java.lang.reflect.InvocationTargetException;

import java.net.URL;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Generated;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.Response;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.lang.time.DateUtils;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Rafael Praxedes
 * @generated
 */
@Generated("")
public abstract class BaseProcessResourceTestCase {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");
	}

	@Before
	public void setUp() throws Exception {
		irrelevantGroup = GroupTestUtil.addGroup();
		testGroup = GroupTestUtil.addGroup();
		testLocale = LocaleUtil.getDefault();

		_resourceURL = new URL(
			"http://localhost:8080/o/portal-workflow-metrics/v1.0");
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(irrelevantGroup);
		GroupTestUtil.deleteGroup(testGroup);
	}

	@Test
	public void testGetProcessesPage() throws Exception {
		Process process1 = testGetProcessesPage_addProcess(randomProcess());

		Process process2 = testGetProcessesPage_addProcess(randomProcess());

		Page<Process> page = invokeGetProcessesPage(
			null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(process1, process2), (List<Process>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetProcessesPageWithPagination() throws Exception {
		Process process1 = testGetProcessesPage_addProcess(randomProcess());

		Process process2 = testGetProcessesPage_addProcess(randomProcess());

		Process process3 = testGetProcessesPage_addProcess(randomProcess());

		Page<Process> page1 = invokeGetProcessesPage(
			null, Pagination.of(1, 2), null);

		List<Process> processes1 = (List<Process>)page1.getItems();

		Assert.assertEquals(processes1.toString(), 2, processes1.size());

		Page<Process> page2 = invokeGetProcessesPage(
			null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<Process> processes2 = (List<Process>)page2.getItems();

		Assert.assertEquals(processes2.toString(), 1, processes2.size());

		assertEqualsIgnoringOrder(
			Arrays.asList(process1, process2, process3),
			new ArrayList<Process>() {
				{
					addAll(processes1);
					addAll(processes2);
				}
			});
	}

	@Test
	public void testGetProcessesPageWithSortDateTime() throws Exception {
		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Process process1 = randomProcess();
		Process process2 = randomProcess();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				process1, entityField.getName(),
				DateUtils.addMinutes(new Date(), -2));
		}

		process1 = testGetProcessesPage_addProcess(process1);

		Thread.sleep(1000);

		process2 = testGetProcessesPage_addProcess(process2);

		for (EntityField entityField : entityFields) {
			Page<Process> ascPage = invokeGetProcessesPage(
				null, Pagination.of(1, 2), entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(process1, process2),
				(List<Process>)ascPage.getItems());

			Page<Process> descPage = invokeGetProcessesPage(
				null, Pagination.of(1, 2), entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(process2, process1),
				(List<Process>)descPage.getItems());
		}
	}

	@Test
	public void testGetProcessesPageWithSortString() throws Exception {
		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Process process1 = randomProcess();
		Process process2 = randomProcess();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(process1, entityField.getName(), "Aaa");
			BeanUtils.setProperty(process2, entityField.getName(), "Bbb");
		}

		process1 = testGetProcessesPage_addProcess(process1);

		process2 = testGetProcessesPage_addProcess(process2);

		for (EntityField entityField : entityFields) {
			Page<Process> ascPage = invokeGetProcessesPage(
				null, Pagination.of(1, 2), entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(process1, process2),
				(List<Process>)ascPage.getItems());

			Page<Process> descPage = invokeGetProcessesPage(
				null, Pagination.of(1, 2), entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(process2, process1),
				(List<Process>)descPage.getItems());
		}
	}

	protected Process testGetProcessesPage_addProcess(Process process)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Page<Process> invokeGetProcessesPage(
			String title, Pagination pagination, String sortString)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location = _resourceURL + _toPath("/processes");

		location = HttpUtil.addParameter(
			location, "page", pagination.getPage());
		location = HttpUtil.addParameter(
			location, "pageSize", pagination.getPageSize());

		location = HttpUtil.addParameter(location, "sort", sortString);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		return outputObjectMapper.readValue(
			string,
			new TypeReference<Page<Process>>() {
			});
	}

	protected Http.Response invokeGetProcessesPageResponse(
			String title, Pagination pagination, String sortString)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location = _resourceURL + _toPath("/processes");

		location = HttpUtil.addParameter(
			location, "page", pagination.getPage());
		location = HttpUtil.addParameter(
			location, "pageSize", pagination.getPageSize());

		location = HttpUtil.addParameter(location, "sort", sortString);

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testGetProcess() throws Exception {
		Process postProcess = testGetProcess_addProcess();

		Process getProcess = invokeGetProcess(postProcess.getId());

		assertEquals(postProcess, getProcess);
		assertValid(getProcess);
	}

	protected Process testGetProcess_addProcess() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Process invokeGetProcess(Long processId) throws Exception {
		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL + _toPath("/processes/{processId}", processId);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return outputObjectMapper.readValue(string, Process.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response invokeGetProcessResponse(Long processId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL + _toPath("/processes/{processId}", processId);

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	protected void assertResponseCode(
		int expectedResponseCode, Http.Response actualResponse) {

		Assert.assertEquals(
			expectedResponseCode, actualResponse.getResponseCode());
	}

	protected void assertEquals(Process process1, Process process2) {
		Assert.assertTrue(
			process1 + " does not equal " + process2,
			equals(process1, process2));
	}

	protected void assertEquals(
		List<Process> processes1, List<Process> processes2) {

		Assert.assertEquals(processes1.size(), processes2.size());

		for (int i = 0; i < processes1.size(); i++) {
			Process process1 = processes1.get(i);
			Process process2 = processes2.get(i);

			assertEquals(process1, process2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<Process> processes1, List<Process> processes2) {

		Assert.assertEquals(processes1.size(), processes2.size());

		for (Process process1 : processes1) {
			boolean contains = false;

			for (Process process2 : processes2) {
				if (equals(process1, process2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				processes2 + " does not contain " + process1, contains);
		}
	}

	protected void assertValid(Process process) {
		boolean valid = true;

		if (process.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals(
					"dueAfterInstanceCount", additionalAssertFieldName)) {

				if (process.getDueAfterInstanceCount() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"dueInInstanceCount", additionalAssertFieldName)) {

				if (process.getDueInInstanceCount() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("instanceCount", additionalAssertFieldName)) {
				if (process.getInstanceCount() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"onTimeInstanceCount", additionalAssertFieldName)) {

				if (process.getOnTimeInstanceCount() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"overdueInstanceCount", additionalAssertFieldName)) {

				if (process.getOverdueInstanceCount() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("title", additionalAssertFieldName)) {
				if (process.getTitle() == null) {
					valid = false;
				}

				continue;
			}

			throw new IllegalArgumentException(
				"Invalid additional assert field name " +
					additionalAssertFieldName);
		}

		Assert.assertTrue(valid);
	}

	protected void assertValid(Page<Process> page) {
		boolean valid = false;

		Collection<Process> processes = page.getItems();

		int size = processes.size();

		if ((page.getLastPage() > 0) && (page.getPage() > 0) &&
			(page.getPageSize() > 0) && (page.getTotalCount() > 0) &&
			(size > 0)) {

			valid = true;
		}

		Assert.assertTrue(valid);
	}

	protected String[] getAdditionalAssertFieldNames() {
		return new String[0];
	}

	protected boolean equals(Process process1, Process process2) {
		if (process1 == process2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals(
					"dueAfterInstanceCount", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						process1.getDueAfterInstanceCount(),
						process2.getDueAfterInstanceCount())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"dueInInstanceCount", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						process1.getDueInInstanceCount(),
						process2.getDueInInstanceCount())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(process1.getId(), process2.getId())) {
					return false;
				}

				continue;
			}

			if (Objects.equals("instanceCount", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						process1.getInstanceCount(),
						process2.getInstanceCount())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"onTimeInstanceCount", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						process1.getOnTimeInstanceCount(),
						process2.getOnTimeInstanceCount())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"overdueInstanceCount", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						process1.getOverdueInstanceCount(),
						process2.getOverdueInstanceCount())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("title", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						process1.getTitle(), process2.getTitle())) {

					return false;
				}

				continue;
			}

			throw new IllegalArgumentException(
				"Invalid additional assert field name " +
					additionalAssertFieldName);
		}

		return true;
	}

	protected Collection<EntityField> getEntityFields() throws Exception {
		if (!(_processResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_processResource;

		EntityModel entityModel = entityModelResource.getEntityModel(
			new MultivaluedHashMap());

		Map<String, EntityField> entityFieldsMap =
			entityModel.getEntityFieldsMap();

		return entityFieldsMap.values();
	}

	protected List<EntityField> getEntityFields(EntityField.Type type)
		throws Exception {

		Collection<EntityField> entityFields = getEntityFields();

		Stream<EntityField> stream = entityFields.stream();

		return stream.filter(
			entityField -> Objects.equals(entityField.getType(), type)
		).collect(
			Collectors.toList()
		);
	}

	protected String getFilterString(
		EntityField entityField, String operator, Process process) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("dueAfterInstanceCount")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("dueInInstanceCount")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("instanceCount")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("onTimeInstanceCount")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("overdueInstanceCount")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("title")) {
			sb.append("'");
			sb.append(String.valueOf(process.getTitle()));
			sb.append("'");

			return sb.toString();
		}

		throw new IllegalArgumentException(
			"Invalid entity field " + entityFieldName);
	}

	protected Process randomProcess() {
		return new Process() {
			{
				dueAfterInstanceCount = RandomTestUtil.randomLong();
				dueInInstanceCount = RandomTestUtil.randomLong();
				id = RandomTestUtil.randomLong();
				instanceCount = RandomTestUtil.randomLong();
				onTimeInstanceCount = RandomTestUtil.randomLong();
				overdueInstanceCount = RandomTestUtil.randomLong();
				title = RandomTestUtil.randomString();
			}
		};
	}

	protected Process randomIrrelevantProcess() {
		Process randomIrrelevantProcess = randomProcess();

		return randomIrrelevantProcess;
	}

	protected Process randomPatchProcess() {
		return randomProcess();
	}

	protected static final ObjectMapper inputObjectMapper = new ObjectMapper() {
		{
			setFilterProvider(
				new SimpleFilterProvider() {
					{
						addFilter(
							"Liferay.Vulcan",
							SimpleBeanPropertyFilter.serializeAll());
					}
				});
			setSerializationInclusion(JsonInclude.Include.NON_NULL);
		}
	};
	protected static final ObjectMapper outputObjectMapper =
		new ObjectMapper() {
			{
				addMixIn(Process.class, ProcessMixin.class);
				setFilterProvider(
					new SimpleFilterProvider() {
						{
							addFilter(
								"Liferay.Vulcan",
								SimpleBeanPropertyFilter.serializeAll());
						}
					});
			}
		};

	protected Group irrelevantGroup;
	protected String testContentType = "application/json";
	protected Group testGroup;
	protected Locale testLocale;
	protected String testUserNameAndPassword = "test@liferay.com:test";

	protected static class ProcessMixin {

		@JsonProperty
		Long dueAfterInstanceCount;
		@JsonProperty
		Long dueInInstanceCount;
		@JsonProperty
		Long id;
		@JsonProperty
		Long instanceCount;
		@JsonProperty
		Long onTimeInstanceCount;
		@JsonProperty
		Long overdueInstanceCount;
		@JsonProperty
		String title;

	}

	protected static class Page<T> {

		public Collection<T> getItems() {
			return new ArrayList<>(items);
		}

		public long getLastPage() {
			return lastPage;
		}

		public long getPage() {
			return page;
		}

		public long getPageSize() {
			return pageSize;
		}

		public long getTotalCount() {
			return totalCount;
		}

		@JsonProperty
		protected Collection<T> items;

		@JsonProperty
		protected long lastPage;

		@JsonProperty
		protected long page;

		@JsonProperty
		protected long pageSize;

		@JsonProperty
		protected long totalCount;

	}

	private Http.Options _createHttpOptions() {
		Http.Options options = new Http.Options();

		options.addHeader("Accept", "application/json");
		options.addHeader(
			"Accept-Language", LocaleUtil.toW3cLanguageId(testLocale));

		String encodedTestUserNameAndPassword = Base64.encode(
			testUserNameAndPassword.getBytes());

		options.addHeader(
			"Authorization", "Basic " + encodedTestUserNameAndPassword);

		options.addHeader("Content-Type", testContentType);

		return options;
	}

	private String _toPath(String template, Object... values) {
		if (ArrayUtil.isEmpty(values)) {
			return template;
		}

		for (int i = 0; i < values.length; i++) {
			template = template.replaceFirst(
				"\\{.*?\\}", String.valueOf(values[i]));
		}

		return template;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseProcessResourceTestCase.class);

	private static BeanUtilsBean _beanUtilsBean = new BeanUtilsBean() {

		@Override
		public void copyProperty(Object bean, String name, Object value)
			throws IllegalAccessException, InvocationTargetException {

			if (value != null) {
				super.copyProperty(bean, name, value);
			}
		}

	};
	private static DateFormat _dateFormat;

	@Inject
	private ProcessResource _processResource;

	private URL _resourceURL;

}