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

package com.liferay.osb.koroneiki.phloem.rest.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.PostalAddress;
import com.liferay.osb.koroneiki.phloem.rest.client.http.HttpInvoker;
import com.liferay.osb.koroneiki.phloem.rest.client.pagination.Page;
import com.liferay.osb.koroneiki.phloem.rest.client.resource.v1_0.PostalAddressResource;
import com.liferay.osb.koroneiki.phloem.rest.client.serdes.v1_0.PostalAddressSerDes;
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

import java.lang.reflect.InvocationTargetException;

import java.text.DateFormat;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Generated;

import javax.ws.rs.core.MultivaluedHashMap;

import org.apache.commons.beanutils.BeanUtilsBean;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Amos Fong
 * @generated
 */
@Generated("")
public abstract class BasePostalAddressResourceTestCase {

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

		testCompany = CompanyLocalServiceUtil.getCompany(
			testGroup.getCompanyId());

		_postalAddressResource.setContextCompany(testCompany);

		PostalAddressResource.Builder builder = PostalAddressResource.builder();

		postalAddressResource = builder.locale(
			LocaleUtil.getDefault()
		).build();
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

		PostalAddress postalAddress1 = randomPostalAddress();

		String json = objectMapper.writeValueAsString(postalAddress1);

		PostalAddress postalAddress2 = PostalAddressSerDes.toDTO(json);

		Assert.assertTrue(equals(postalAddress1, postalAddress2));
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

		PostalAddress postalAddress = randomPostalAddress();

		String json1 = objectMapper.writeValueAsString(postalAddress);
		String json2 = PostalAddressSerDes.toJSON(postalAddress);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		PostalAddress postalAddress = randomPostalAddress();

		postalAddress.setAddressCountry(regex);
		postalAddress.setAddressLocality(regex);
		postalAddress.setAddressRegion(regex);
		postalAddress.setAddressType(regex);
		postalAddress.setPostalCode(regex);
		postalAddress.setStreetAddressLine1(regex);
		postalAddress.setStreetAddressLine2(regex);
		postalAddress.setStreetAddressLine3(regex);

		String json = PostalAddressSerDes.toJSON(postalAddress);

		Assert.assertFalse(json.contains(regex));

		postalAddress = PostalAddressSerDes.toDTO(json);

		Assert.assertEquals(regex, postalAddress.getAddressCountry());
		Assert.assertEquals(regex, postalAddress.getAddressLocality());
		Assert.assertEquals(regex, postalAddress.getAddressRegion());
		Assert.assertEquals(regex, postalAddress.getAddressType());
		Assert.assertEquals(regex, postalAddress.getPostalCode());
		Assert.assertEquals(regex, postalAddress.getStreetAddressLine1());
		Assert.assertEquals(regex, postalAddress.getStreetAddressLine2());
		Assert.assertEquals(regex, postalAddress.getStreetAddressLine3());
	}

	@Test
	public void testGetAccountAccountKeyPostalAddressesPage() throws Exception {
		Page<PostalAddress> page =
			postalAddressResource.getAccountAccountKeyPostalAddressesPage(
				testGetAccountAccountKeyPostalAddressesPage_getAccountKey());

		Assert.assertEquals(0, page.getTotalCount());

		String accountKey =
			testGetAccountAccountKeyPostalAddressesPage_getAccountKey();
		String irrelevantAccountKey =
			testGetAccountAccountKeyPostalAddressesPage_getIrrelevantAccountKey();

		if ((irrelevantAccountKey != null)) {
			PostalAddress irrelevantPostalAddress =
				testGetAccountAccountKeyPostalAddressesPage_addPostalAddress(
					irrelevantAccountKey, randomIrrelevantPostalAddress());

			page =
				postalAddressResource.getAccountAccountKeyPostalAddressesPage(
					irrelevantAccountKey);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantPostalAddress),
				(List<PostalAddress>)page.getItems());
			assertValid(page);
		}

		PostalAddress postalAddress1 =
			testGetAccountAccountKeyPostalAddressesPage_addPostalAddress(
				accountKey, randomPostalAddress());

		PostalAddress postalAddress2 =
			testGetAccountAccountKeyPostalAddressesPage_addPostalAddress(
				accountKey, randomPostalAddress());

		page = postalAddressResource.getAccountAccountKeyPostalAddressesPage(
			accountKey);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(postalAddress1, postalAddress2),
			(List<PostalAddress>)page.getItems());
		assertValid(page);
	}

	protected PostalAddress
			testGetAccountAccountKeyPostalAddressesPage_addPostalAddress(
				String accountKey, PostalAddress postalAddress)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String testGetAccountAccountKeyPostalAddressesPage_getAccountKey()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetAccountAccountKeyPostalAddressesPage_getIrrelevantAccountKey()
		throws Exception {

		return null;
	}

	@Test
	public void testPostAccountAccountKeyPostalAddress() throws Exception {
		PostalAddress randomPostalAddress = randomPostalAddress();

		PostalAddress postPostalAddress =
			testPostAccountAccountKeyPostalAddress_addPostalAddress(
				randomPostalAddress);

		assertEquals(randomPostalAddress, postPostalAddress);
		assertValid(postPostalAddress);
	}

	protected PostalAddress
			testPostAccountAccountKeyPostalAddress_addPostalAddress(
				PostalAddress postalAddress)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testDeletePostalAddress() throws Exception {
		PostalAddress postalAddress =
			testDeletePostalAddress_addPostalAddress();

		assertHttpResponseStatusCode(
			204,
			postalAddressResource.deletePostalAddressHttpResponse(
				postalAddress.getId()));

		assertHttpResponseStatusCode(
			404,
			postalAddressResource.getPostalAddressHttpResponse(
				postalAddress.getId()));

		assertHttpResponseStatusCode(
			404, postalAddressResource.getPostalAddressHttpResponse(0L));
	}

	protected PostalAddress testDeletePostalAddress_addPostalAddress()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetPostalAddress() throws Exception {
		PostalAddress postPostalAddress =
			testGetPostalAddress_addPostalAddress();

		PostalAddress getPostalAddress = postalAddressResource.getPostalAddress(
			postPostalAddress.getId());

		assertEquals(postPostalAddress, getPostalAddress);
		assertValid(getPostalAddress);
	}

	protected PostalAddress testGetPostalAddress_addPostalAddress()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPutPostalAddress() throws Exception {
		PostalAddress postPostalAddress =
			testPutPostalAddress_addPostalAddress();

		PostalAddress randomPostalAddress = randomPostalAddress();

		PostalAddress putPostalAddress = postalAddressResource.putPostalAddress(
			postPostalAddress.getId(), randomPostalAddress);

		assertEquals(randomPostalAddress, putPostalAddress);
		assertValid(putPostalAddress);

		PostalAddress getPostalAddress = postalAddressResource.getPostalAddress(
			putPostalAddress.getId());

		assertEquals(randomPostalAddress, getPostalAddress);
		assertValid(getPostalAddress);
	}

	protected PostalAddress testPutPostalAddress_addPostalAddress()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(
		PostalAddress postalAddress1, PostalAddress postalAddress2) {

		Assert.assertTrue(
			postalAddress1 + " does not equal " + postalAddress2,
			equals(postalAddress1, postalAddress2));
	}

	protected void assertEquals(
		List<PostalAddress> postalAddresses1,
		List<PostalAddress> postalAddresses2) {

		Assert.assertEquals(postalAddresses1.size(), postalAddresses2.size());

		for (int i = 0; i < postalAddresses1.size(); i++) {
			PostalAddress postalAddress1 = postalAddresses1.get(i);
			PostalAddress postalAddress2 = postalAddresses2.get(i);

			assertEquals(postalAddress1, postalAddress2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<PostalAddress> postalAddresses1,
		List<PostalAddress> postalAddresses2) {

		Assert.assertEquals(postalAddresses1.size(), postalAddresses2.size());

		for (PostalAddress postalAddress1 : postalAddresses1) {
			boolean contains = false;

			for (PostalAddress postalAddress2 : postalAddresses2) {
				if (equals(postalAddress1, postalAddress2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				postalAddresses2 + " does not contain " + postalAddress1,
				contains);
		}
	}

	protected void assertValid(PostalAddress postalAddress) {
		boolean valid = true;

		if (postalAddress.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("addressCountry", additionalAssertFieldName)) {
				if (postalAddress.getAddressCountry() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("addressLocality", additionalAssertFieldName)) {
				if (postalAddress.getAddressLocality() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("addressRegion", additionalAssertFieldName)) {
				if (postalAddress.getAddressRegion() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("addressType", additionalAssertFieldName)) {
				if (postalAddress.getAddressType() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("mailing", additionalAssertFieldName)) {
				if (postalAddress.getMailing() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("postalCode", additionalAssertFieldName)) {
				if (postalAddress.getPostalCode() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("primary", additionalAssertFieldName)) {
				if (postalAddress.getPrimary() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"streetAddressLine1", additionalAssertFieldName)) {

				if (postalAddress.getStreetAddressLine1() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"streetAddressLine2", additionalAssertFieldName)) {

				if (postalAddress.getStreetAddressLine2() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"streetAddressLine3", additionalAssertFieldName)) {

				if (postalAddress.getStreetAddressLine3() == null) {
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

	protected void assertValid(Page<PostalAddress> page) {
		boolean valid = false;

		java.util.Collection<PostalAddress> postalAddresses = page.getItems();

		int size = postalAddresses.size();

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

	protected boolean equals(
		PostalAddress postalAddress1, PostalAddress postalAddress2) {

		if (postalAddress1 == postalAddress2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("addressCountry", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						postalAddress1.getAddressCountry(),
						postalAddress2.getAddressCountry())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("addressLocality", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						postalAddress1.getAddressLocality(),
						postalAddress2.getAddressLocality())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("addressRegion", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						postalAddress1.getAddressRegion(),
						postalAddress2.getAddressRegion())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("addressType", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						postalAddress1.getAddressType(),
						postalAddress2.getAddressType())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						postalAddress1.getId(), postalAddress2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("mailing", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						postalAddress1.getMailing(),
						postalAddress2.getMailing())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("postalCode", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						postalAddress1.getPostalCode(),
						postalAddress2.getPostalCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("primary", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						postalAddress1.getPrimary(),
						postalAddress2.getPrimary())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"streetAddressLine1", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						postalAddress1.getStreetAddressLine1(),
						postalAddress2.getStreetAddressLine1())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"streetAddressLine2", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						postalAddress1.getStreetAddressLine2(),
						postalAddress2.getStreetAddressLine2())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"streetAddressLine3", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						postalAddress1.getStreetAddressLine3(),
						postalAddress2.getStreetAddressLine3())) {

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

	protected java.util.Collection<EntityField> getEntityFields()
		throws Exception {

		if (!(_postalAddressResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_postalAddressResource;

		EntityModel entityModel = entityModelResource.getEntityModel(
			new MultivaluedHashMap());

		Map<String, EntityField> entityFieldsMap =
			entityModel.getEntityFieldsMap();

		return entityFieldsMap.values();
	}

	protected List<EntityField> getEntityFields(EntityField.Type type)
		throws Exception {

		java.util.Collection<EntityField> entityFields = getEntityFields();

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
		EntityField entityField, String operator, PostalAddress postalAddress) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("addressCountry")) {
			sb.append("'");
			sb.append(String.valueOf(postalAddress.getAddressCountry()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("addressLocality")) {
			sb.append("'");
			sb.append(String.valueOf(postalAddress.getAddressLocality()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("addressRegion")) {
			sb.append("'");
			sb.append(String.valueOf(postalAddress.getAddressRegion()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("addressType")) {
			sb.append("'");
			sb.append(String.valueOf(postalAddress.getAddressType()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("mailing")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("postalCode")) {
			sb.append("'");
			sb.append(String.valueOf(postalAddress.getPostalCode()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("primary")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("streetAddressLine1")) {
			sb.append("'");
			sb.append(String.valueOf(postalAddress.getStreetAddressLine1()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("streetAddressLine2")) {
			sb.append("'");
			sb.append(String.valueOf(postalAddress.getStreetAddressLine2()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("streetAddressLine3")) {
			sb.append("'");
			sb.append(String.valueOf(postalAddress.getStreetAddressLine3()));
			sb.append("'");

			return sb.toString();
		}

		throw new IllegalArgumentException(
			"Invalid entity field " + entityFieldName);
	}

	protected PostalAddress randomPostalAddress() throws Exception {
		return new PostalAddress() {
			{
				addressCountry = RandomTestUtil.randomString();
				addressLocality = RandomTestUtil.randomString();
				addressRegion = RandomTestUtil.randomString();
				addressType = RandomTestUtil.randomString();
				id = RandomTestUtil.randomLong();
				mailing = RandomTestUtil.randomBoolean();
				postalCode = RandomTestUtil.randomString();
				primary = RandomTestUtil.randomBoolean();
				streetAddressLine1 = RandomTestUtil.randomString();
				streetAddressLine2 = RandomTestUtil.randomString();
				streetAddressLine3 = RandomTestUtil.randomString();
			}
		};
	}

	protected PostalAddress randomIrrelevantPostalAddress() throws Exception {
		PostalAddress randomIrrelevantPostalAddress = randomPostalAddress();

		return randomIrrelevantPostalAddress;
	}

	protected PostalAddress randomPatchPostalAddress() throws Exception {
		return randomPostalAddress();
	}

	protected PostalAddressResource postalAddressResource;
	protected Group irrelevantGroup;
	protected Company testCompany;
	protected Group testGroup;

	private static final Log _log = LogFactoryUtil.getLog(
		BasePostalAddressResourceTestCase.class);

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
		com.liferay.osb.koroneiki.phloem.rest.resource.v1_0.
			PostalAddressResource _postalAddressResource;

}