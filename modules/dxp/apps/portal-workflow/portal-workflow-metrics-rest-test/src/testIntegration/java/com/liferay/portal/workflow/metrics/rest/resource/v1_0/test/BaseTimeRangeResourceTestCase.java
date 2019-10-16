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

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.TimeRange;
import com.liferay.portal.workflow.metrics.rest.client.http.HttpInvoker;
import com.liferay.portal.workflow.metrics.rest.client.pagination.Page;
import com.liferay.portal.workflow.metrics.rest.client.resource.v1_0.TimeRangeResource;
import com.liferay.portal.workflow.metrics.rest.client.serdes.v1_0.TimeRangeSerDes;

import java.lang.reflect.InvocationTargetException;

import java.text.DateFormat;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Generated;

import javax.ws.rs.core.MultivaluedHashMap;

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
public abstract class BaseTimeRangeResourceTestCase {

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

		testCompany = CompanyLocalServiceUtil.getCompany(
			testGroup.getCompanyId());

		_timeRangeResource.setContextCompany(testCompany);
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(irrelevantGroup);
		GroupTestUtil.deleteGroup(testGroup);
	}

	@Test
	public void testClientSerDesToDTO() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper() {
			{
				configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
				configure(
					SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
				enable(SerializationFeature.INDENT_OUTPUT);
				setDateFormat(new ISO8601DateFormat());
				setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
				setSerializationInclusion(JsonInclude.Include.NON_NULL);
				setVisibility(
					PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
				setVisibility(
					PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
			}
		};

		TimeRange timeRange1 = randomTimeRange();

		String json = objectMapper.writeValueAsString(timeRange1);

		TimeRange timeRange2 = TimeRangeSerDes.toDTO(json);

		Assert.assertTrue(equals(timeRange1, timeRange2));
	}

	@Test
	public void testClientSerDesToJSON() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper() {
			{
				configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
				configure(
					SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
				setDateFormat(new ISO8601DateFormat());
				setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
				setSerializationInclusion(JsonInclude.Include.NON_NULL);
				setVisibility(
					PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
				setVisibility(
					PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
			}
		};

		TimeRange timeRange = randomTimeRange();

		String json1 = objectMapper.writeValueAsString(timeRange);
		String json2 = TimeRangeSerDes.toJSON(timeRange);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		TimeRange timeRange = randomTimeRange();

		timeRange.setName(regex);

		String json = TimeRangeSerDes.toJSON(timeRange);

		Assert.assertFalse(json.contains(regex));

		timeRange = TimeRangeSerDes.toDTO(json);

		Assert.assertEquals(regex, timeRange.getName());
	}

	@Test
	public void testGetTimeRangesPage() throws Exception {
		Assert.assertTrue(true);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(TimeRange timeRange1, TimeRange timeRange2) {
		Assert.assertTrue(
			timeRange1 + " does not equal " + timeRange2,
			equals(timeRange1, timeRange2));
	}

	protected void assertEquals(
		List<TimeRange> timeRanges1, List<TimeRange> timeRanges2) {

		Assert.assertEquals(timeRanges1.size(), timeRanges2.size());

		for (int i = 0; i < timeRanges1.size(); i++) {
			TimeRange timeRange1 = timeRanges1.get(i);
			TimeRange timeRange2 = timeRanges2.get(i);

			assertEquals(timeRange1, timeRange2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<TimeRange> timeRanges1, List<TimeRange> timeRanges2) {

		Assert.assertEquals(timeRanges1.size(), timeRanges2.size());

		for (TimeRange timeRange1 : timeRanges1) {
			boolean contains = false;

			for (TimeRange timeRange2 : timeRanges2) {
				if (equals(timeRange1, timeRange2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				timeRanges2 + " does not contain " + timeRange1, contains);
		}
	}

	protected void assertValid(TimeRange timeRange) {
		boolean valid = true;

		if (timeRange.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("dateEnd", additionalAssertFieldName)) {
				if (timeRange.getDateEnd() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("dateStart", additionalAssertFieldName)) {
				if (timeRange.getDateStart() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("defaultTimeRange", additionalAssertFieldName)) {
				if (timeRange.getDefaultTimeRange() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (timeRange.getName() == null) {
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

	protected void assertValid(Page<TimeRange> page) {
		boolean valid = false;

		Collection<TimeRange> timeRanges = page.getItems();

		int size = timeRanges.size();

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

	protected String[] getIgnoredEntityFieldNames() {
		return new String[0];
	}

	protected boolean equals(TimeRange timeRange1, TimeRange timeRange2) {
		if (timeRange1 == timeRange2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("dateEnd", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						timeRange1.getDateEnd(), timeRange2.getDateEnd())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateStart", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						timeRange1.getDateStart(), timeRange2.getDateStart())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("defaultTimeRange", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						timeRange1.getDefaultTimeRange(),
						timeRange2.getDefaultTimeRange())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						timeRange1.getId(), timeRange2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						timeRange1.getName(), timeRange2.getName())) {

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
		if (!(_timeRangeResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_timeRangeResource;

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
			entityField ->
				Objects.equals(entityField.getType(), type) &&
				!ArrayUtil.contains(
					getIgnoredEntityFieldNames(), entityField.getName())
		).collect(
			Collectors.toList()
		);
	}

	protected String getFilterString(
		EntityField entityField, String operator, TimeRange timeRange) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("dateEnd")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(timeRange.getDateEnd(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(timeRange.getDateEnd(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(timeRange.getDateEnd()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("dateStart")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(timeRange.getDateStart(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(timeRange.getDateStart(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(timeRange.getDateStart()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("defaultTimeRange")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("name")) {
			sb.append("'");
			sb.append(String.valueOf(timeRange.getName()));
			sb.append("'");

			return sb.toString();
		}

		throw new IllegalArgumentException(
			"Invalid entity field " + entityFieldName);
	}

	protected TimeRange randomTimeRange() throws Exception {
		return new TimeRange() {
			{
				dateEnd = RandomTestUtil.nextDate();
				dateStart = RandomTestUtil.nextDate();
				defaultTimeRange = RandomTestUtil.randomBoolean();
				name = RandomTestUtil.randomString();
			}
		};
	}

	protected TimeRange randomIrrelevantTimeRange() throws Exception {
		TimeRange randomIrrelevantTimeRange = randomTimeRange();

		return randomIrrelevantTimeRange;
	}

	protected TimeRange randomPatchTimeRange() throws Exception {
		return randomTimeRange();
	}

	protected Group irrelevantGroup;
	protected Company testCompany;
	protected Group testGroup;
	protected Locale testLocale;
	protected String testUserNameAndPassword = "test@liferay.com:test";

	private static final Log _log = LogFactoryUtil.getLog(
		BaseTimeRangeResourceTestCase.class);

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
	private
		com.liferay.portal.workflow.metrics.rest.resource.v1_0.TimeRangeResource
			_timeRangeResource;

}