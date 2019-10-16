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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.ContentTypes;
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
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.SLA;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.SLAResource;

import java.lang.reflect.InvocationTargetException;

import java.net.URL;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Generated;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.Response;

import org.apache.commons.beanutils.BeanUtilsBean;

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
public abstract class BaseSLAResourceTestCase {

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
	public void testGetProcessSLAsPage() throws Exception {
		Long processId = testGetProcessSLAsPage_getProcessId();
		Long irrelevantProcessId =
			testGetProcessSLAsPage_getIrrelevantProcessId();

		if ((irrelevantProcessId != null)) {
			SLA irrelevantSLA = testGetProcessSLAsPage_addSLA(
				irrelevantProcessId, randomIrrelevantSLA());

			Page<SLA> page = invokeGetProcessSLAsPage(
				irrelevantProcessId, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantSLA), (List<SLA>)page.getItems());
			assertValid(page);
		}

		SLA sla1 = testGetProcessSLAsPage_addSLA(processId, randomSLA());

		SLA sla2 = testGetProcessSLAsPage_addSLA(processId, randomSLA());

		Page<SLA> page = invokeGetProcessSLAsPage(
			processId, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(sla1, sla2), (List<SLA>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetProcessSLAsPageWithPagination() throws Exception {
		Long processId = testGetProcessSLAsPage_getProcessId();

		SLA sla1 = testGetProcessSLAsPage_addSLA(processId, randomSLA());

		SLA sla2 = testGetProcessSLAsPage_addSLA(processId, randomSLA());

		SLA sla3 = testGetProcessSLAsPage_addSLA(processId, randomSLA());

		Page<SLA> page1 = invokeGetProcessSLAsPage(
			processId, Pagination.of(1, 2));

		List<SLA> slas1 = (List<SLA>)page1.getItems();

		Assert.assertEquals(slas1.toString(), 2, slas1.size());

		Page<SLA> page2 = invokeGetProcessSLAsPage(
			processId, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<SLA> slas2 = (List<SLA>)page2.getItems();

		Assert.assertEquals(slas2.toString(), 1, slas2.size());

		assertEqualsIgnoringOrder(
			Arrays.asList(sla1, sla2, sla3),
			new ArrayList<SLA>() {
				{
					addAll(slas1);
					addAll(slas2);
				}
			});
	}

	protected SLA testGetProcessSLAsPage_addSLA(Long processId, SLA sla)
		throws Exception {

		return invokePostProcessSLA(processId, sla);
	}

	protected Long testGetProcessSLAsPage_getProcessId() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetProcessSLAsPage_getIrrelevantProcessId()
		throws Exception {

		return null;
	}

	protected Page<SLA> invokeGetProcessSLAsPage(
			Long processId, Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL + _toPath("/processes/{processId}/slas", processId);

		location = HttpUtil.addParameter(
			location, "page", pagination.getPage());
		location = HttpUtil.addParameter(
			location, "pageSize", pagination.getPageSize());

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		return outputObjectMapper.readValue(
			string,
			new TypeReference<Page<SLA>>() {
			});
	}

	protected Http.Response invokeGetProcessSLAsPageResponse(
			Long processId, Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL + _toPath("/processes/{processId}/slas", processId);

		location = HttpUtil.addParameter(
			location, "page", pagination.getPage());
		location = HttpUtil.addParameter(
			location, "pageSize", pagination.getPageSize());

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testPostProcessSLA() throws Exception {
		SLA randomSLA = randomSLA();

		SLA postSLA = testPostProcessSLA_addSLA(randomSLA);

		assertEquals(randomSLA, postSLA);
		assertValid(postSLA);
	}

	protected SLA testPostProcessSLA_addSLA(SLA sla) throws Exception {
		return invokePostProcessSLA(testGetProcessSLAsPage_getProcessId(), sla);
	}

	protected SLA invokePostProcessSLA(Long processId, SLA sla)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			inputObjectMapper.writeValueAsString(sla),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL + _toPath("/processes/{processId}/slas", processId);

		options.setLocation(location);

		options.setPost(true);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return outputObjectMapper.readValue(string, SLA.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response invokePostProcessSLAResponse(
			Long processId, SLA sla)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			inputObjectMapper.writeValueAsString(sla),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL + _toPath("/processes/{processId}/slas", processId);

		options.setLocation(location);

		options.setPost(true);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testDeleteSLA() throws Exception {
		SLA sla = testDeleteSLA_addSLA();

		assertResponseCode(204, invokeDeleteSLAResponse(sla.getId()));

		assertResponseCode(404, invokeGetSLAResponse(sla.getId()));
	}

	protected SLA testDeleteSLA_addSLA() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void invokeDeleteSLA(Long slaId) throws Exception {
		Http.Options options = _createHttpOptions();

		options.setDelete(true);

		String location = _resourceURL + _toPath("/slas/{slaId}", slaId);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}
	}

	protected Http.Response invokeDeleteSLAResponse(Long slaId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setDelete(true);

		String location = _resourceURL + _toPath("/slas/{slaId}", slaId);

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testGetSLA() throws Exception {
		SLA postSLA = testGetSLA_addSLA();

		SLA getSLA = invokeGetSLA(postSLA.getId());

		assertEquals(postSLA, getSLA);
		assertValid(getSLA);
	}

	protected SLA testGetSLA_addSLA() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected SLA invokeGetSLA(Long slaId) throws Exception {
		Http.Options options = _createHttpOptions();

		String location = _resourceURL + _toPath("/slas/{slaId}", slaId);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return outputObjectMapper.readValue(string, SLA.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response invokeGetSLAResponse(Long slaId) throws Exception {
		Http.Options options = _createHttpOptions();

		String location = _resourceURL + _toPath("/slas/{slaId}", slaId);

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testPutSLA() throws Exception {
		SLA postSLA = testPutSLA_addSLA();

		SLA randomSLA = randomSLA();

		SLA putSLA = invokePutSLA(postSLA.getId(), randomSLA);

		assertEquals(randomSLA, putSLA);
		assertValid(putSLA);

		SLA getSLA = invokeGetSLA(putSLA.getId());

		assertEquals(randomSLA, getSLA);
		assertValid(getSLA);
	}

	protected SLA testPutSLA_addSLA() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected SLA invokePutSLA(Long slaId, SLA sla) throws Exception {
		Http.Options options = _createHttpOptions();

		options.setBody(
			inputObjectMapper.writeValueAsString(sla),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location = _resourceURL + _toPath("/slas/{slaId}", slaId);

		options.setLocation(location);

		options.setPut(true);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return outputObjectMapper.readValue(string, SLA.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response invokePutSLAResponse(Long slaId, SLA sla)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			inputObjectMapper.writeValueAsString(sla),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location = _resourceURL + _toPath("/slas/{slaId}", slaId);

		options.setLocation(location);

		options.setPut(true);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	protected void assertResponseCode(
		int expectedResponseCode, Http.Response actualResponse) {

		Assert.assertEquals(
			expectedResponseCode, actualResponse.getResponseCode());
	}

	protected void assertEquals(SLA sla1, SLA sla2) {
		Assert.assertTrue(sla1 + " does not equal " + sla2, equals(sla1, sla2));
	}

	protected void assertEquals(List<SLA> slas1, List<SLA> slas2) {
		Assert.assertEquals(slas1.size(), slas2.size());

		for (int i = 0; i < slas1.size(); i++) {
			SLA sla1 = slas1.get(i);
			SLA sla2 = slas2.get(i);

			assertEquals(sla1, sla2);
		}
	}

	protected void assertEqualsIgnoringOrder(List<SLA> slas1, List<SLA> slas2) {
		Assert.assertEquals(slas1.size(), slas2.size());

		for (SLA sla1 : slas1) {
			boolean contains = false;

			for (SLA sla2 : slas2) {
				if (equals(sla1, sla2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(slas2 + " does not contain " + sla1, contains);
		}
	}

	protected void assertValid(SLA sla) {
		boolean valid = true;

		if (sla.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (sla.getDescription() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("duration", additionalAssertFieldName)) {
				if (sla.getDuration() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (sla.getName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("pauseNodeKeys", additionalAssertFieldName)) {
				if (sla.getPauseNodeKeys() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("processId", additionalAssertFieldName)) {
				if (sla.getProcessId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("startNodeKeys", additionalAssertFieldName)) {
				if (sla.getStartNodeKeys() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("stopNodeKeys", additionalAssertFieldName)) {
				if (sla.getStopNodeKeys() == null) {
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

	protected void assertValid(Page<SLA> page) {
		boolean valid = false;

		Collection<SLA> slas = page.getItems();

		int size = slas.size();

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

	protected boolean equals(SLA sla1, SLA sla2) {
		if (sla1 == sla2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						sla1.getDescription(), sla2.getDescription())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("duration", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						sla1.getDuration(), sla2.getDuration())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(sla1.getId(), sla2.getId())) {
					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!Objects.deepEquals(sla1.getName(), sla2.getName())) {
					return false;
				}

				continue;
			}

			if (Objects.equals("pauseNodeKeys", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						sla1.getPauseNodeKeys(), sla2.getPauseNodeKeys())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("processId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						sla1.getProcessId(), sla2.getProcessId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("startNodeKeys", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						sla1.getStartNodeKeys(), sla2.getStartNodeKeys())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("stopNodeKeys", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						sla1.getStopNodeKeys(), sla2.getStopNodeKeys())) {

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
		if (!(_slaResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_slaResource;

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
		EntityField entityField, String operator, SLA sla) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("description")) {
			sb.append("'");
			sb.append(String.valueOf(sla.getDescription()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("duration")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("name")) {
			sb.append("'");
			sb.append(String.valueOf(sla.getName()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("pauseNodeKeys")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("processId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("startNodeKeys")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("stopNodeKeys")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		throw new IllegalArgumentException(
			"Invalid entity field " + entityFieldName);
	}

	protected SLA randomSLA() {
		return new SLA() {
			{
				description = RandomTestUtil.randomString();
				duration = RandomTestUtil.randomLong();
				id = RandomTestUtil.randomLong();
				name = RandomTestUtil.randomString();
				processId = RandomTestUtil.randomLong();
			}
		};
	}

	protected SLA randomIrrelevantSLA() {
		SLA randomIrrelevantSLA = randomSLA();

		return randomIrrelevantSLA;
	}

	protected SLA randomPatchSLA() {
		return randomSLA();
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
				addMixIn(SLA.class, SLAMixin.class);
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

	protected static class SLAMixin {

		@JsonProperty
		String description;
		@JsonProperty
		Long duration;
		@JsonProperty
		Long id;
		@JsonProperty
		String name;
		@JsonProperty
		String[] pauseNodeKeys;
		@JsonProperty
		Long processId;
		@JsonProperty
		String[] startNodeKeys;
		@JsonProperty
		String[] stopNodeKeys;

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
		BaseSLAResourceTestCase.class);

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
	private SLAResource _slaResource;

	private URL _resourceURL;

}