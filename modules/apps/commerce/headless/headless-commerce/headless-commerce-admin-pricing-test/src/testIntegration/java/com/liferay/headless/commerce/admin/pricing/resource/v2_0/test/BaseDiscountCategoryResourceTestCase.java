/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.headless.commerce.admin.pricing.resource.v2_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.headless.commerce.admin.pricing.client.dto.v2_0.DiscountCategory;
import com.liferay.headless.commerce.admin.pricing.client.http.HttpInvoker;
import com.liferay.headless.commerce.admin.pricing.client.pagination.Page;
import com.liferay.headless.commerce.admin.pricing.client.pagination.Pagination;
import com.liferay.headless.commerce.admin.pricing.client.resource.v2_0.DiscountCategoryResource;
import com.liferay.headless.commerce.admin.pricing.client.serdes.v2_0.DiscountCategorySerDes;
import com.liferay.petra.function.UnsafeTriConsumer;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.search.test.util.SearchTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Generated;

import javax.ws.rs.core.MultivaluedHashMap;

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
 * @author Zoltán Takács
 * @generated
 */
@Generated("")
public abstract class BaseDiscountCategoryResourceTestCase {

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

		_discountCategoryResource.setContextCompany(testCompany);

		DiscountCategoryResource.Builder builder =
			DiscountCategoryResource.builder();

		discountCategoryResource = builder.authentication(
			"test@liferay.com", "test"
		).locale(
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

		DiscountCategory discountCategory1 = randomDiscountCategory();

		String json = objectMapper.writeValueAsString(discountCategory1);

		DiscountCategory discountCategory2 = DiscountCategorySerDes.toDTO(json);

		Assert.assertTrue(equals(discountCategory1, discountCategory2));
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

		DiscountCategory discountCategory = randomDiscountCategory();

		String json1 = objectMapper.writeValueAsString(discountCategory);
		String json2 = DiscountCategorySerDes.toJSON(discountCategory);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		DiscountCategory discountCategory = randomDiscountCategory();

		discountCategory.setCategoryExternalReferenceCode(regex);
		discountCategory.setDiscountExternalReferenceCode(regex);

		String json = DiscountCategorySerDes.toJSON(discountCategory);

		Assert.assertFalse(json.contains(regex));

		discountCategory = DiscountCategorySerDes.toDTO(json);

		Assert.assertEquals(
			regex, discountCategory.getCategoryExternalReferenceCode());
		Assert.assertEquals(
			regex, discountCategory.getDiscountExternalReferenceCode());
	}

	@Test
	public void testDeleteDiscountCategory() throws Exception {
		Assert.assertTrue(false);
	}

	@Test
	public void testGraphQLDeleteDiscountCategory() throws Exception {
		Assert.assertTrue(false);
	}

	@Test
	public void testGetDiscountByExternalReferenceCodeDiscountCategoriesPage()
		throws Exception {

		Page<DiscountCategory> page =
			discountCategoryResource.
				getDiscountByExternalReferenceCodeDiscountCategoriesPage(
					testGetDiscountByExternalReferenceCodeDiscountCategoriesPage_getExternalReferenceCode(),
					Pagination.of(1, 2));

		Assert.assertEquals(0, page.getTotalCount());

		String externalReferenceCode =
			testGetDiscountByExternalReferenceCodeDiscountCategoriesPage_getExternalReferenceCode();
		String irrelevantExternalReferenceCode =
			testGetDiscountByExternalReferenceCodeDiscountCategoriesPage_getIrrelevantExternalReferenceCode();

		if ((irrelevantExternalReferenceCode != null)) {
			DiscountCategory irrelevantDiscountCategory =
				testGetDiscountByExternalReferenceCodeDiscountCategoriesPage_addDiscountCategory(
					irrelevantExternalReferenceCode,
					randomIrrelevantDiscountCategory());

			page =
				discountCategoryResource.
					getDiscountByExternalReferenceCodeDiscountCategoriesPage(
						irrelevantExternalReferenceCode, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantDiscountCategory),
				(List<DiscountCategory>)page.getItems());
			assertValid(page);
		}

		DiscountCategory discountCategory1 =
			testGetDiscountByExternalReferenceCodeDiscountCategoriesPage_addDiscountCategory(
				externalReferenceCode, randomDiscountCategory());

		DiscountCategory discountCategory2 =
			testGetDiscountByExternalReferenceCodeDiscountCategoriesPage_addDiscountCategory(
				externalReferenceCode, randomDiscountCategory());

		page =
			discountCategoryResource.
				getDiscountByExternalReferenceCodeDiscountCategoriesPage(
					externalReferenceCode, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(discountCategory1, discountCategory2),
			(List<DiscountCategory>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetDiscountByExternalReferenceCodeDiscountCategoriesPageWithPagination()
		throws Exception {

		String externalReferenceCode =
			testGetDiscountByExternalReferenceCodeDiscountCategoriesPage_getExternalReferenceCode();

		DiscountCategory discountCategory1 =
			testGetDiscountByExternalReferenceCodeDiscountCategoriesPage_addDiscountCategory(
				externalReferenceCode, randomDiscountCategory());

		DiscountCategory discountCategory2 =
			testGetDiscountByExternalReferenceCodeDiscountCategoriesPage_addDiscountCategory(
				externalReferenceCode, randomDiscountCategory());

		DiscountCategory discountCategory3 =
			testGetDiscountByExternalReferenceCodeDiscountCategoriesPage_addDiscountCategory(
				externalReferenceCode, randomDiscountCategory());

		Page<DiscountCategory> page1 =
			discountCategoryResource.
				getDiscountByExternalReferenceCodeDiscountCategoriesPage(
					externalReferenceCode, Pagination.of(1, 2));

		List<DiscountCategory> discountCategories1 =
			(List<DiscountCategory>)page1.getItems();

		Assert.assertEquals(
			discountCategories1.toString(), 2, discountCategories1.size());

		Page<DiscountCategory> page2 =
			discountCategoryResource.
				getDiscountByExternalReferenceCodeDiscountCategoriesPage(
					externalReferenceCode, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<DiscountCategory> discountCategories2 =
			(List<DiscountCategory>)page2.getItems();

		Assert.assertEquals(
			discountCategories2.toString(), 1, discountCategories2.size());

		Page<DiscountCategory> page3 =
			discountCategoryResource.
				getDiscountByExternalReferenceCodeDiscountCategoriesPage(
					externalReferenceCode, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(
				discountCategory1, discountCategory2, discountCategory3),
			(List<DiscountCategory>)page3.getItems());
	}

	protected DiscountCategory
			testGetDiscountByExternalReferenceCodeDiscountCategoriesPage_addDiscountCategory(
				String externalReferenceCode, DiscountCategory discountCategory)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetDiscountByExternalReferenceCodeDiscountCategoriesPage_getExternalReferenceCode()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetDiscountByExternalReferenceCodeDiscountCategoriesPage_getIrrelevantExternalReferenceCode()
		throws Exception {

		return null;
	}

	@Test
	public void testPostDiscountByExternalReferenceCodeDiscountCategory()
		throws Exception {

		DiscountCategory randomDiscountCategory = randomDiscountCategory();

		DiscountCategory postDiscountCategory =
			testPostDiscountByExternalReferenceCodeDiscountCategory_addDiscountCategory(
				randomDiscountCategory);

		assertEquals(randomDiscountCategory, postDiscountCategory);
		assertValid(postDiscountCategory);
	}

	protected DiscountCategory
			testPostDiscountByExternalReferenceCodeDiscountCategory_addDiscountCategory(
				DiscountCategory discountCategory)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetDiscountIdDiscountCategoriesPage() throws Exception {
		Page<DiscountCategory> page =
			discountCategoryResource.getDiscountIdDiscountCategoriesPage(
				testGetDiscountIdDiscountCategoriesPage_getId(),
				RandomTestUtil.randomString(), null, Pagination.of(1, 2), null);

		Assert.assertEquals(0, page.getTotalCount());

		Long id = testGetDiscountIdDiscountCategoriesPage_getId();
		Long irrelevantId =
			testGetDiscountIdDiscountCategoriesPage_getIrrelevantId();

		if ((irrelevantId != null)) {
			DiscountCategory irrelevantDiscountCategory =
				testGetDiscountIdDiscountCategoriesPage_addDiscountCategory(
					irrelevantId, randomIrrelevantDiscountCategory());

			page = discountCategoryResource.getDiscountIdDiscountCategoriesPage(
				irrelevantId, null, null, Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantDiscountCategory),
				(List<DiscountCategory>)page.getItems());
			assertValid(page);
		}

		DiscountCategory discountCategory1 =
			testGetDiscountIdDiscountCategoriesPage_addDiscountCategory(
				id, randomDiscountCategory());

		DiscountCategory discountCategory2 =
			testGetDiscountIdDiscountCategoriesPage_addDiscountCategory(
				id, randomDiscountCategory());

		page = discountCategoryResource.getDiscountIdDiscountCategoriesPage(
			id, null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(discountCategory1, discountCategory2),
			(List<DiscountCategory>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetDiscountIdDiscountCategoriesPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long id = testGetDiscountIdDiscountCategoriesPage_getId();

		DiscountCategory discountCategory1 = randomDiscountCategory();

		discountCategory1 =
			testGetDiscountIdDiscountCategoriesPage_addDiscountCategory(
				id, discountCategory1);

		for (EntityField entityField : entityFields) {
			Page<DiscountCategory> page =
				discountCategoryResource.getDiscountIdDiscountCategoriesPage(
					id, null,
					getFilterString(entityField, "between", discountCategory1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(discountCategory1),
				(List<DiscountCategory>)page.getItems());
		}
	}

	@Test
	public void testGetDiscountIdDiscountCategoriesPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long id = testGetDiscountIdDiscountCategoriesPage_getId();

		DiscountCategory discountCategory1 =
			testGetDiscountIdDiscountCategoriesPage_addDiscountCategory(
				id, randomDiscountCategory());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		DiscountCategory discountCategory2 =
			testGetDiscountIdDiscountCategoriesPage_addDiscountCategory(
				id, randomDiscountCategory());

		for (EntityField entityField : entityFields) {
			Page<DiscountCategory> page =
				discountCategoryResource.getDiscountIdDiscountCategoriesPage(
					id, null,
					getFilterString(entityField, "eq", discountCategory1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(discountCategory1),
				(List<DiscountCategory>)page.getItems());
		}
	}

	@Test
	public void testGetDiscountIdDiscountCategoriesPageWithPagination()
		throws Exception {

		Long id = testGetDiscountIdDiscountCategoriesPage_getId();

		DiscountCategory discountCategory1 =
			testGetDiscountIdDiscountCategoriesPage_addDiscountCategory(
				id, randomDiscountCategory());

		DiscountCategory discountCategory2 =
			testGetDiscountIdDiscountCategoriesPage_addDiscountCategory(
				id, randomDiscountCategory());

		DiscountCategory discountCategory3 =
			testGetDiscountIdDiscountCategoriesPage_addDiscountCategory(
				id, randomDiscountCategory());

		Page<DiscountCategory> page1 =
			discountCategoryResource.getDiscountIdDiscountCategoriesPage(
				id, null, null, Pagination.of(1, 2), null);

		List<DiscountCategory> discountCategories1 =
			(List<DiscountCategory>)page1.getItems();

		Assert.assertEquals(
			discountCategories1.toString(), 2, discountCategories1.size());

		Page<DiscountCategory> page2 =
			discountCategoryResource.getDiscountIdDiscountCategoriesPage(
				id, null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<DiscountCategory> discountCategories2 =
			(List<DiscountCategory>)page2.getItems();

		Assert.assertEquals(
			discountCategories2.toString(), 1, discountCategories2.size());

		Page<DiscountCategory> page3 =
			discountCategoryResource.getDiscountIdDiscountCategoriesPage(
				id, null, null, Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(
				discountCategory1, discountCategory2, discountCategory3),
			(List<DiscountCategory>)page3.getItems());
	}

	@Test
	public void testGetDiscountIdDiscountCategoriesPageWithSortDateTime()
		throws Exception {

		testGetDiscountIdDiscountCategoriesPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, discountCategory1, discountCategory2) -> {
				BeanUtils.setProperty(
					discountCategory1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetDiscountIdDiscountCategoriesPageWithSortInteger()
		throws Exception {

		testGetDiscountIdDiscountCategoriesPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, discountCategory1, discountCategory2) -> {
				BeanUtils.setProperty(
					discountCategory1, entityField.getName(), 0);
				BeanUtils.setProperty(
					discountCategory2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetDiscountIdDiscountCategoriesPageWithSortString()
		throws Exception {

		testGetDiscountIdDiscountCategoriesPageWithSort(
			EntityField.Type.STRING,
			(entityField, discountCategory1, discountCategory2) -> {
				Class<?> clazz = discountCategory1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						discountCategory1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						discountCategory2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanUtils.setProperty(
						discountCategory1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanUtils.setProperty(
						discountCategory2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanUtils.setProperty(
						discountCategory1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanUtils.setProperty(
						discountCategory2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetDiscountIdDiscountCategoriesPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer
				<EntityField, DiscountCategory, DiscountCategory, Exception>
					unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long id = testGetDiscountIdDiscountCategoriesPage_getId();

		DiscountCategory discountCategory1 = randomDiscountCategory();
		DiscountCategory discountCategory2 = randomDiscountCategory();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(
				entityField, discountCategory1, discountCategory2);
		}

		discountCategory1 =
			testGetDiscountIdDiscountCategoriesPage_addDiscountCategory(
				id, discountCategory1);

		discountCategory2 =
			testGetDiscountIdDiscountCategoriesPage_addDiscountCategory(
				id, discountCategory2);

		for (EntityField entityField : entityFields) {
			Page<DiscountCategory> ascPage =
				discountCategoryResource.getDiscountIdDiscountCategoriesPage(
					id, null, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(discountCategory1, discountCategory2),
				(List<DiscountCategory>)ascPage.getItems());

			Page<DiscountCategory> descPage =
				discountCategoryResource.getDiscountIdDiscountCategoriesPage(
					id, null, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(discountCategory2, discountCategory1),
				(List<DiscountCategory>)descPage.getItems());
		}
	}

	protected DiscountCategory
			testGetDiscountIdDiscountCategoriesPage_addDiscountCategory(
				Long id, DiscountCategory discountCategory)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetDiscountIdDiscountCategoriesPage_getId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetDiscountIdDiscountCategoriesPage_getIrrelevantId()
		throws Exception {

		return null;
	}

	@Test
	public void testPostDiscountIdDiscountCategory() throws Exception {
		DiscountCategory randomDiscountCategory = randomDiscountCategory();

		DiscountCategory postDiscountCategory =
			testPostDiscountIdDiscountCategory_addDiscountCategory(
				randomDiscountCategory);

		assertEquals(randomDiscountCategory, postDiscountCategory);
		assertValid(postDiscountCategory);
	}

	protected DiscountCategory
			testPostDiscountIdDiscountCategory_addDiscountCategory(
				DiscountCategory discountCategory)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(
		DiscountCategory discountCategory1,
		DiscountCategory discountCategory2) {

		Assert.assertTrue(
			discountCategory1 + " does not equal " + discountCategory2,
			equals(discountCategory1, discountCategory2));
	}

	protected void assertEquals(
		List<DiscountCategory> discountCategories1,
		List<DiscountCategory> discountCategories2) {

		Assert.assertEquals(
			discountCategories1.size(), discountCategories2.size());

		for (int i = 0; i < discountCategories1.size(); i++) {
			DiscountCategory discountCategory1 = discountCategories1.get(i);
			DiscountCategory discountCategory2 = discountCategories2.get(i);

			assertEquals(discountCategory1, discountCategory2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<DiscountCategory> discountCategories1,
		List<DiscountCategory> discountCategories2) {

		Assert.assertEquals(
			discountCategories1.size(), discountCategories2.size());

		for (DiscountCategory discountCategory1 : discountCategories1) {
			boolean contains = false;

			for (DiscountCategory discountCategory2 : discountCategories2) {
				if (equals(discountCategory1, discountCategory2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				discountCategories2 + " does not contain " + discountCategory1,
				contains);
		}
	}

	protected void assertValid(DiscountCategory discountCategory)
		throws Exception {

		boolean valid = true;

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (discountCategory.getActions() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("category", additionalAssertFieldName)) {
				if (discountCategory.getCategory() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"categoryExternalReferenceCode",
					additionalAssertFieldName)) {

				if (discountCategory.getCategoryExternalReferenceCode() ==
						null) {

					valid = false;
				}

				continue;
			}

			if (Objects.equals("categoryId", additionalAssertFieldName)) {
				if (discountCategory.getCategoryId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"discountCategoryId", additionalAssertFieldName)) {

				if (discountCategory.getDiscountCategoryId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"discountExternalReferenceCode",
					additionalAssertFieldName)) {

				if (discountCategory.getDiscountExternalReferenceCode() ==
						null) {

					valid = false;
				}

				continue;
			}

			if (Objects.equals("discountId", additionalAssertFieldName)) {
				if (discountCategory.getDiscountId() == null) {
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

	protected void assertValid(Page<DiscountCategory> page) {
		boolean valid = false;

		java.util.Collection<DiscountCategory> discountCategories =
			page.getItems();

		int size = discountCategories.size();

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

	protected List<GraphQLField> getGraphQLFields() throws Exception {
		List<GraphQLField> graphQLFields = new ArrayList<>();

		for (Field field :
				ReflectionUtil.getDeclaredFields(
					com.liferay.headless.commerce.admin.pricing.dto.v2_0.
						DiscountCategory.class)) {

			if (!ArrayUtil.contains(
					getAdditionalAssertFieldNames(), field.getName())) {

				continue;
			}

			graphQLFields.addAll(getGraphQLFields(field));
		}

		return graphQLFields;
	}

	protected List<GraphQLField> getGraphQLFields(Field... fields)
		throws Exception {

		List<GraphQLField> graphQLFields = new ArrayList<>();

		for (Field field : fields) {
			com.liferay.portal.vulcan.graphql.annotation.GraphQLField
				vulcanGraphQLField = field.getAnnotation(
					com.liferay.portal.vulcan.graphql.annotation.GraphQLField.
						class);

			if (vulcanGraphQLField != null) {
				Class<?> clazz = field.getType();

				if (clazz.isArray()) {
					clazz = clazz.getComponentType();
				}

				List<GraphQLField> childrenGraphQLFields = getGraphQLFields(
					ReflectionUtil.getDeclaredFields(clazz));

				graphQLFields.add(
					new GraphQLField(field.getName(), childrenGraphQLFields));
			}
		}

		return graphQLFields;
	}

	protected String[] getIgnoredEntityFieldNames() {
		return new String[0];
	}

	protected boolean equals(
		DiscountCategory discountCategory1,
		DiscountCategory discountCategory2) {

		if (discountCategory1 == discountCategory2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (!equals(
						(Map)discountCategory1.getActions(),
						(Map)discountCategory2.getActions())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("category", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						discountCategory1.getCategory(),
						discountCategory2.getCategory())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"categoryExternalReferenceCode",
					additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						discountCategory1.getCategoryExternalReferenceCode(),
						discountCategory2.getCategoryExternalReferenceCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("categoryId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						discountCategory1.getCategoryId(),
						discountCategory2.getCategoryId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"discountCategoryId", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						discountCategory1.getDiscountCategoryId(),
						discountCategory2.getDiscountCategoryId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"discountExternalReferenceCode",
					additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						discountCategory1.getDiscountExternalReferenceCode(),
						discountCategory2.getDiscountExternalReferenceCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("discountId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						discountCategory1.getDiscountId(),
						discountCategory2.getDiscountId())) {

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

	protected boolean equals(
		Map<String, Object> map1, Map<String, Object> map2) {

		if (Objects.equals(map1.keySet(), map2.keySet())) {
			for (Map.Entry<String, Object> entry : map1.entrySet()) {
				if (entry.getValue() instanceof Map) {
					if (!equals(
							(Map)entry.getValue(),
							(Map)map2.get(entry.getKey()))) {

						return false;
					}
				}
				else if (!Objects.deepEquals(
							entry.getValue(), map2.get(entry.getKey()))) {

					return false;
				}
			}

			return true;
		}

		return false;
	}

	protected java.util.Collection<EntityField> getEntityFields()
		throws Exception {

		if (!(_discountCategoryResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_discountCategoryResource;

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
		EntityField entityField, String operator,
		DiscountCategory discountCategory) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("actions")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("category")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("categoryExternalReferenceCode")) {
			sb.append("'");
			sb.append(
				String.valueOf(
					discountCategory.getCategoryExternalReferenceCode()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("categoryId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("discountCategoryId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("discountExternalReferenceCode")) {
			sb.append("'");
			sb.append(
				String.valueOf(
					discountCategory.getDiscountExternalReferenceCode()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("discountId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		throw new IllegalArgumentException(
			"Invalid entity field " + entityFieldName);
	}

	protected String invoke(String query) throws Exception {
		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.body(
			JSONUtil.put(
				"query", query
			).toString(),
			"application/json");
		httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);
		httpInvoker.path("http://localhost:8080/o/graphql");
		httpInvoker.userNameAndPassword("test@liferay.com:test");

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		return httpResponse.getContent();
	}

	protected JSONObject invokeGraphQLMutation(GraphQLField graphQLField)
		throws Exception {

		GraphQLField mutationGraphQLField = new GraphQLField(
			"mutation", graphQLField);

		return JSONFactoryUtil.createJSONObject(
			invoke(mutationGraphQLField.toString()));
	}

	protected JSONObject invokeGraphQLQuery(GraphQLField graphQLField)
		throws Exception {

		GraphQLField queryGraphQLField = new GraphQLField(
			"query", graphQLField);

		return JSONFactoryUtil.createJSONObject(
			invoke(queryGraphQLField.toString()));
	}

	protected DiscountCategory randomDiscountCategory() throws Exception {
		return new DiscountCategory() {
			{
				categoryExternalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				categoryId = RandomTestUtil.randomLong();
				discountCategoryId = RandomTestUtil.randomLong();
				discountExternalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				discountId = RandomTestUtil.randomLong();
			}
		};
	}

	protected DiscountCategory randomIrrelevantDiscountCategory()
		throws Exception {

		DiscountCategory randomIrrelevantDiscountCategory =
			randomDiscountCategory();

		return randomIrrelevantDiscountCategory;
	}

	protected DiscountCategory randomPatchDiscountCategory() throws Exception {
		return randomDiscountCategory();
	}

	protected DiscountCategoryResource discountCategoryResource;
	protected Group irrelevantGroup;
	protected Company testCompany;
	protected Group testGroup;

	protected class GraphQLField {

		public GraphQLField(String key, GraphQLField... graphQLFields) {
			this(key, new HashMap<>(), graphQLFields);
		}

		public GraphQLField(String key, List<GraphQLField> graphQLFields) {
			this(key, new HashMap<>(), graphQLFields);
		}

		public GraphQLField(
			String key, Map<String, Object> parameterMap,
			GraphQLField... graphQLFields) {

			_key = key;
			_parameterMap = parameterMap;
			_graphQLFields = Arrays.asList(graphQLFields);
		}

		public GraphQLField(
			String key, Map<String, Object> parameterMap,
			List<GraphQLField> graphQLFields) {

			_key = key;
			_parameterMap = parameterMap;
			_graphQLFields = graphQLFields;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder(_key);

			if (!_parameterMap.isEmpty()) {
				sb.append("(");

				for (Map.Entry<String, Object> entry :
						_parameterMap.entrySet()) {

					sb.append(entry.getKey());
					sb.append(":");
					sb.append(entry.getValue());
					sb.append(",");
				}

				sb.setLength(sb.length() - 1);

				sb.append(")");
			}

			if (!_graphQLFields.isEmpty()) {
				sb.append("{");

				for (GraphQLField graphQLField : _graphQLFields) {
					sb.append(graphQLField.toString());
					sb.append(",");
				}

				sb.setLength(sb.length() - 1);

				sb.append("}");
			}

			return sb.toString();
		}

		private final List<GraphQLField> _graphQLFields;
		private final String _key;
		private final Map<String, Object> _parameterMap;

	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseDiscountCategoryResourceTestCase.class);

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
	private com.liferay.headless.commerce.admin.pricing.resource.v2_0.
		DiscountCategoryResource _discountCategoryResource;

}