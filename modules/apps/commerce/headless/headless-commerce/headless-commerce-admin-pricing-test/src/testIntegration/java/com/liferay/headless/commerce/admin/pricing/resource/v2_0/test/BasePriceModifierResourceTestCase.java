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

import com.liferay.headless.commerce.admin.pricing.client.dto.v2_0.PriceModifier;
import com.liferay.headless.commerce.admin.pricing.client.http.HttpInvoker;
import com.liferay.headless.commerce.admin.pricing.client.pagination.Page;
import com.liferay.headless.commerce.admin.pricing.client.pagination.Pagination;
import com.liferay.headless.commerce.admin.pricing.client.resource.v2_0.PriceModifierResource;
import com.liferay.headless.commerce.admin.pricing.client.serdes.v2_0.PriceModifierSerDes;
import com.liferay.petra.function.UnsafeTriConsumer;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONArray;
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
import com.liferay.portal.test.log.CaptureAppender;
import com.liferay.portal.test.log.Log4JLoggerTestUtil;
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
import org.apache.log4j.Level;

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
public abstract class BasePriceModifierResourceTestCase {

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

		_priceModifierResource.setContextCompany(testCompany);

		PriceModifierResource.Builder builder = PriceModifierResource.builder();

		priceModifierResource = builder.authentication(
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

		PriceModifier priceModifier1 = randomPriceModifier();

		String json = objectMapper.writeValueAsString(priceModifier1);

		PriceModifier priceModifier2 = PriceModifierSerDes.toDTO(json);

		Assert.assertTrue(equals(priceModifier1, priceModifier2));
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

		PriceModifier priceModifier = randomPriceModifier();

		String json1 = objectMapper.writeValueAsString(priceModifier);
		String json2 = PriceModifierSerDes.toJSON(priceModifier);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		PriceModifier priceModifier = randomPriceModifier();

		priceModifier.setExternalReferenceCode(regex);
		priceModifier.setModifierType(regex);
		priceModifier.setPriceListExternalReferenceCode(regex);
		priceModifier.setTarget(regex);
		priceModifier.setTitle(regex);

		String json = PriceModifierSerDes.toJSON(priceModifier);

		Assert.assertFalse(json.contains(regex));

		priceModifier = PriceModifierSerDes.toDTO(json);

		Assert.assertEquals(regex, priceModifier.getExternalReferenceCode());
		Assert.assertEquals(regex, priceModifier.getModifierType());
		Assert.assertEquals(
			regex, priceModifier.getPriceListExternalReferenceCode());
		Assert.assertEquals(regex, priceModifier.getTarget());
		Assert.assertEquals(regex, priceModifier.getTitle());
	}

	@Test
	public void testGetPriceListByExternalReferenceCodePriceModifiersPage()
		throws Exception {

		Page<PriceModifier> page =
			priceModifierResource.
				getPriceListByExternalReferenceCodePriceModifiersPage(
					testGetPriceListByExternalReferenceCodePriceModifiersPage_getExternalReferenceCode(),
					Pagination.of(1, 2));

		Assert.assertEquals(0, page.getTotalCount());

		String externalReferenceCode =
			testGetPriceListByExternalReferenceCodePriceModifiersPage_getExternalReferenceCode();
		String irrelevantExternalReferenceCode =
			testGetPriceListByExternalReferenceCodePriceModifiersPage_getIrrelevantExternalReferenceCode();

		if ((irrelevantExternalReferenceCode != null)) {
			PriceModifier irrelevantPriceModifier =
				testGetPriceListByExternalReferenceCodePriceModifiersPage_addPriceModifier(
					irrelevantExternalReferenceCode,
					randomIrrelevantPriceModifier());

			page =
				priceModifierResource.
					getPriceListByExternalReferenceCodePriceModifiersPage(
						irrelevantExternalReferenceCode, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantPriceModifier),
				(List<PriceModifier>)page.getItems());
			assertValid(page);
		}

		PriceModifier priceModifier1 =
			testGetPriceListByExternalReferenceCodePriceModifiersPage_addPriceModifier(
				externalReferenceCode, randomPriceModifier());

		PriceModifier priceModifier2 =
			testGetPriceListByExternalReferenceCodePriceModifiersPage_addPriceModifier(
				externalReferenceCode, randomPriceModifier());

		page =
			priceModifierResource.
				getPriceListByExternalReferenceCodePriceModifiersPage(
					externalReferenceCode, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(priceModifier1, priceModifier2),
			(List<PriceModifier>)page.getItems());
		assertValid(page);

		priceModifierResource.deletePriceModifier(priceModifier1.getId());

		priceModifierResource.deletePriceModifier(priceModifier2.getId());
	}

	@Test
	public void testGetPriceListByExternalReferenceCodePriceModifiersPageWithPagination()
		throws Exception {

		String externalReferenceCode =
			testGetPriceListByExternalReferenceCodePriceModifiersPage_getExternalReferenceCode();

		PriceModifier priceModifier1 =
			testGetPriceListByExternalReferenceCodePriceModifiersPage_addPriceModifier(
				externalReferenceCode, randomPriceModifier());

		PriceModifier priceModifier2 =
			testGetPriceListByExternalReferenceCodePriceModifiersPage_addPriceModifier(
				externalReferenceCode, randomPriceModifier());

		PriceModifier priceModifier3 =
			testGetPriceListByExternalReferenceCodePriceModifiersPage_addPriceModifier(
				externalReferenceCode, randomPriceModifier());

		Page<PriceModifier> page1 =
			priceModifierResource.
				getPriceListByExternalReferenceCodePriceModifiersPage(
					externalReferenceCode, Pagination.of(1, 2));

		List<PriceModifier> priceModifiers1 =
			(List<PriceModifier>)page1.getItems();

		Assert.assertEquals(
			priceModifiers1.toString(), 2, priceModifiers1.size());

		Page<PriceModifier> page2 =
			priceModifierResource.
				getPriceListByExternalReferenceCodePriceModifiersPage(
					externalReferenceCode, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<PriceModifier> priceModifiers2 =
			(List<PriceModifier>)page2.getItems();

		Assert.assertEquals(
			priceModifiers2.toString(), 1, priceModifiers2.size());

		Page<PriceModifier> page3 =
			priceModifierResource.
				getPriceListByExternalReferenceCodePriceModifiersPage(
					externalReferenceCode, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(priceModifier1, priceModifier2, priceModifier3),
			(List<PriceModifier>)page3.getItems());
	}

	protected PriceModifier
			testGetPriceListByExternalReferenceCodePriceModifiersPage_addPriceModifier(
				String externalReferenceCode, PriceModifier priceModifier)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetPriceListByExternalReferenceCodePriceModifiersPage_getExternalReferenceCode()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetPriceListByExternalReferenceCodePriceModifiersPage_getIrrelevantExternalReferenceCode()
		throws Exception {

		return null;
	}

	@Test
	public void testPostPriceListByExternalReferenceCodePriceModifier()
		throws Exception {

		PriceModifier randomPriceModifier = randomPriceModifier();

		PriceModifier postPriceModifier =
			testPostPriceListByExternalReferenceCodePriceModifier_addPriceModifier(
				randomPriceModifier);

		assertEquals(randomPriceModifier, postPriceModifier);
		assertValid(postPriceModifier);

		randomPriceModifier = randomPriceModifier();

		assertHttpResponseStatusCode(
			404,
			priceModifierResource.
				getPriceModifierByExternalReferenceCodeHttpResponse(
					randomPriceModifier.getExternalReferenceCode()));

		testPostPriceListByExternalReferenceCodePriceModifier_addPriceModifier(
			randomPriceModifier);

		assertHttpResponseStatusCode(
			200,
			priceModifierResource.
				getPriceModifierByExternalReferenceCodeHttpResponse(
					randomPriceModifier.getExternalReferenceCode()));
	}

	protected PriceModifier
			testPostPriceListByExternalReferenceCodePriceModifier_addPriceModifier(
				PriceModifier priceModifier)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetPriceListIdPriceModifiersPage() throws Exception {
		Page<PriceModifier> page =
			priceModifierResource.getPriceListIdPriceModifiersPage(
				testGetPriceListIdPriceModifiersPage_getId(),
				RandomTestUtil.randomString(), null, Pagination.of(1, 2), null);

		Assert.assertEquals(0, page.getTotalCount());

		Long id = testGetPriceListIdPriceModifiersPage_getId();
		Long irrelevantId =
			testGetPriceListIdPriceModifiersPage_getIrrelevantId();

		if ((irrelevantId != null)) {
			PriceModifier irrelevantPriceModifier =
				testGetPriceListIdPriceModifiersPage_addPriceModifier(
					irrelevantId, randomIrrelevantPriceModifier());

			page = priceModifierResource.getPriceListIdPriceModifiersPage(
				irrelevantId, null, null, Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantPriceModifier),
				(List<PriceModifier>)page.getItems());
			assertValid(page);
		}

		PriceModifier priceModifier1 =
			testGetPriceListIdPriceModifiersPage_addPriceModifier(
				id, randomPriceModifier());

		PriceModifier priceModifier2 =
			testGetPriceListIdPriceModifiersPage_addPriceModifier(
				id, randomPriceModifier());

		page = priceModifierResource.getPriceListIdPriceModifiersPage(
			id, null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(priceModifier1, priceModifier2),
			(List<PriceModifier>)page.getItems());
		assertValid(page);

		priceModifierResource.deletePriceModifier(priceModifier1.getId());

		priceModifierResource.deletePriceModifier(priceModifier2.getId());
	}

	@Test
	public void testGetPriceListIdPriceModifiersPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long id = testGetPriceListIdPriceModifiersPage_getId();

		PriceModifier priceModifier1 = randomPriceModifier();

		priceModifier1 = testGetPriceListIdPriceModifiersPage_addPriceModifier(
			id, priceModifier1);

		for (EntityField entityField : entityFields) {
			Page<PriceModifier> page =
				priceModifierResource.getPriceListIdPriceModifiersPage(
					id, null,
					getFilterString(entityField, "between", priceModifier1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(priceModifier1),
				(List<PriceModifier>)page.getItems());
		}
	}

	@Test
	public void testGetPriceListIdPriceModifiersPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long id = testGetPriceListIdPriceModifiersPage_getId();

		PriceModifier priceModifier1 =
			testGetPriceListIdPriceModifiersPage_addPriceModifier(
				id, randomPriceModifier());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		PriceModifier priceModifier2 =
			testGetPriceListIdPriceModifiersPage_addPriceModifier(
				id, randomPriceModifier());

		for (EntityField entityField : entityFields) {
			Page<PriceModifier> page =
				priceModifierResource.getPriceListIdPriceModifiersPage(
					id, null,
					getFilterString(entityField, "eq", priceModifier1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(priceModifier1),
				(List<PriceModifier>)page.getItems());
		}
	}

	@Test
	public void testGetPriceListIdPriceModifiersPageWithPagination()
		throws Exception {

		Long id = testGetPriceListIdPriceModifiersPage_getId();

		PriceModifier priceModifier1 =
			testGetPriceListIdPriceModifiersPage_addPriceModifier(
				id, randomPriceModifier());

		PriceModifier priceModifier2 =
			testGetPriceListIdPriceModifiersPage_addPriceModifier(
				id, randomPriceModifier());

		PriceModifier priceModifier3 =
			testGetPriceListIdPriceModifiersPage_addPriceModifier(
				id, randomPriceModifier());

		Page<PriceModifier> page1 =
			priceModifierResource.getPriceListIdPriceModifiersPage(
				id, null, null, Pagination.of(1, 2), null);

		List<PriceModifier> priceModifiers1 =
			(List<PriceModifier>)page1.getItems();

		Assert.assertEquals(
			priceModifiers1.toString(), 2, priceModifiers1.size());

		Page<PriceModifier> page2 =
			priceModifierResource.getPriceListIdPriceModifiersPage(
				id, null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<PriceModifier> priceModifiers2 =
			(List<PriceModifier>)page2.getItems();

		Assert.assertEquals(
			priceModifiers2.toString(), 1, priceModifiers2.size());

		Page<PriceModifier> page3 =
			priceModifierResource.getPriceListIdPriceModifiersPage(
				id, null, null, Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(priceModifier1, priceModifier2, priceModifier3),
			(List<PriceModifier>)page3.getItems());
	}

	@Test
	public void testGetPriceListIdPriceModifiersPageWithSortDateTime()
		throws Exception {

		testGetPriceListIdPriceModifiersPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, priceModifier1, priceModifier2) -> {
				BeanUtils.setProperty(
					priceModifier1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetPriceListIdPriceModifiersPageWithSortInteger()
		throws Exception {

		testGetPriceListIdPriceModifiersPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, priceModifier1, priceModifier2) -> {
				BeanUtils.setProperty(priceModifier1, entityField.getName(), 0);
				BeanUtils.setProperty(priceModifier2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetPriceListIdPriceModifiersPageWithSortString()
		throws Exception {

		testGetPriceListIdPriceModifiersPageWithSort(
			EntityField.Type.STRING,
			(entityField, priceModifier1, priceModifier2) -> {
				Class<?> clazz = priceModifier1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						priceModifier1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						priceModifier2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanUtils.setProperty(
						priceModifier1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanUtils.setProperty(
						priceModifier2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanUtils.setProperty(
						priceModifier1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanUtils.setProperty(
						priceModifier2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetPriceListIdPriceModifiersPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer
				<EntityField, PriceModifier, PriceModifier, Exception>
					unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long id = testGetPriceListIdPriceModifiersPage_getId();

		PriceModifier priceModifier1 = randomPriceModifier();
		PriceModifier priceModifier2 = randomPriceModifier();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(
				entityField, priceModifier1, priceModifier2);
		}

		priceModifier1 = testGetPriceListIdPriceModifiersPage_addPriceModifier(
			id, priceModifier1);

		priceModifier2 = testGetPriceListIdPriceModifiersPage_addPriceModifier(
			id, priceModifier2);

		for (EntityField entityField : entityFields) {
			Page<PriceModifier> ascPage =
				priceModifierResource.getPriceListIdPriceModifiersPage(
					id, null, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(priceModifier1, priceModifier2),
				(List<PriceModifier>)ascPage.getItems());

			Page<PriceModifier> descPage =
				priceModifierResource.getPriceListIdPriceModifiersPage(
					id, null, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(priceModifier2, priceModifier1),
				(List<PriceModifier>)descPage.getItems());
		}
	}

	protected PriceModifier
			testGetPriceListIdPriceModifiersPage_addPriceModifier(
				Long id, PriceModifier priceModifier)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetPriceListIdPriceModifiersPage_getId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetPriceListIdPriceModifiersPage_getIrrelevantId()
		throws Exception {

		return null;
	}

	@Test
	public void testPostPriceListIdPriceModifier() throws Exception {
		PriceModifier randomPriceModifier = randomPriceModifier();

		PriceModifier postPriceModifier =
			testPostPriceListIdPriceModifier_addPriceModifier(
				randomPriceModifier);

		assertEquals(randomPriceModifier, postPriceModifier);
		assertValid(postPriceModifier);

		randomPriceModifier = randomPriceModifier();

		assertHttpResponseStatusCode(
			404,
			priceModifierResource.
				getPriceModifierByExternalReferenceCodeHttpResponse(
					randomPriceModifier.getExternalReferenceCode()));

		testPostPriceListIdPriceModifier_addPriceModifier(randomPriceModifier);

		assertHttpResponseStatusCode(
			200,
			priceModifierResource.
				getPriceModifierByExternalReferenceCodeHttpResponse(
					randomPriceModifier.getExternalReferenceCode()));
	}

	protected PriceModifier testPostPriceListIdPriceModifier_addPriceModifier(
			PriceModifier priceModifier)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testDeletePriceModifierByExternalReferenceCode()
		throws Exception {

		@SuppressWarnings("PMD.UnusedLocalVariable")
		PriceModifier priceModifier =
			testDeletePriceModifierByExternalReferenceCode_addPriceModifier();

		assertHttpResponseStatusCode(
			204,
			priceModifierResource.
				deletePriceModifierByExternalReferenceCodeHttpResponse(
					priceModifier.getExternalReferenceCode()));

		assertHttpResponseStatusCode(
			404,
			priceModifierResource.
				getPriceModifierByExternalReferenceCodeHttpResponse(
					priceModifier.getExternalReferenceCode()));

		assertHttpResponseStatusCode(
			404,
			priceModifierResource.
				getPriceModifierByExternalReferenceCodeHttpResponse(
					priceModifier.getExternalReferenceCode()));
	}

	protected PriceModifier
			testDeletePriceModifierByExternalReferenceCode_addPriceModifier()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetPriceModifierByExternalReferenceCode() throws Exception {
		PriceModifier postPriceModifier =
			testGetPriceModifierByExternalReferenceCode_addPriceModifier();

		PriceModifier getPriceModifier =
			priceModifierResource.getPriceModifierByExternalReferenceCode(
				postPriceModifier.getExternalReferenceCode());

		assertEquals(postPriceModifier, getPriceModifier);
		assertValid(getPriceModifier);
	}

	protected PriceModifier
			testGetPriceModifierByExternalReferenceCode_addPriceModifier()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetPriceModifierByExternalReferenceCode()
		throws Exception {

		PriceModifier priceModifier =
			testGraphQLPriceModifier_addPriceModifier();

		Assert.assertTrue(
			equals(
				priceModifier,
				PriceModifierSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"priceModifierByExternalReferenceCode",
								new HashMap<String, Object>() {
									{
										put(
											"externalReferenceCode",
											"\"" +
												priceModifier.
													getExternalReferenceCode() +
														"\"");
									}
								},
								getGraphQLFields())),
						"JSONObject/data",
						"Object/priceModifierByExternalReferenceCode"))));
	}

	@Test
	public void testGraphQLGetPriceModifierByExternalReferenceCodeNotFound()
		throws Exception {

		String irrelevantExternalReferenceCode =
			"\"" + RandomTestUtil.randomString() + "\"";

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"priceModifierByExternalReferenceCode",
						new HashMap<String, Object>() {
							{
								put(
									"externalReferenceCode",
									irrelevantExternalReferenceCode);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	@Test
	public void testPatchPriceModifierByExternalReferenceCode()
		throws Exception {

		Assert.assertTrue(false);
	}

	@Test
	public void testDeletePriceModifier() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		PriceModifier priceModifier =
			testDeletePriceModifier_addPriceModifier();

		assertHttpResponseStatusCode(
			204,
			priceModifierResource.deletePriceModifierHttpResponse(
				priceModifier.getId()));

		assertHttpResponseStatusCode(
			404,
			priceModifierResource.getPriceModifierHttpResponse(
				priceModifier.getId()));

		assertHttpResponseStatusCode(
			404,
			priceModifierResource.getPriceModifierHttpResponse(
				priceModifier.getId()));
	}

	protected PriceModifier testDeletePriceModifier_addPriceModifier()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLDeletePriceModifier() throws Exception {
		PriceModifier priceModifier =
			testGraphQLPriceModifier_addPriceModifier();

		Assert.assertTrue(
			JSONUtil.getValueAsBoolean(
				invokeGraphQLMutation(
					new GraphQLField(
						"deletePriceModifier",
						new HashMap<String, Object>() {
							{
								put("id", priceModifier.getId());
							}
						})),
				"JSONObject/data", "Object/deletePriceModifier"));

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					"graphql.execution.SimpleDataFetcherExceptionHandler",
					Level.WARN)) {

			JSONArray errorsJSONArray = JSONUtil.getValueAsJSONArray(
				invokeGraphQLQuery(
					new GraphQLField(
						"priceModifier",
						new HashMap<String, Object>() {
							{
								put("id", priceModifier.getId());
							}
						},
						new GraphQLField("id"))),
				"JSONArray/errors");

			Assert.assertTrue(errorsJSONArray.length() > 0);
		}
	}

	@Test
	public void testGetPriceModifier() throws Exception {
		PriceModifier postPriceModifier =
			testGetPriceModifier_addPriceModifier();

		PriceModifier getPriceModifier = priceModifierResource.getPriceModifier(
			postPriceModifier.getId());

		assertEquals(postPriceModifier, getPriceModifier);
		assertValid(getPriceModifier);
	}

	protected PriceModifier testGetPriceModifier_addPriceModifier()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetPriceModifier() throws Exception {
		PriceModifier priceModifier =
			testGraphQLPriceModifier_addPriceModifier();

		Assert.assertTrue(
			equals(
				priceModifier,
				PriceModifierSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"priceModifier",
								new HashMap<String, Object>() {
									{
										put("id", priceModifier.getId());
									}
								},
								getGraphQLFields())),
						"JSONObject/data", "Object/priceModifier"))));
	}

	@Test
	public void testGraphQLGetPriceModifierNotFound() throws Exception {
		Long irrelevantId = RandomTestUtil.randomLong();

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"priceModifier",
						new HashMap<String, Object>() {
							{
								put("id", irrelevantId);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	@Test
	public void testPatchPriceModifier() throws Exception {
		Assert.assertTrue(false);
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	protected PriceModifier testGraphQLPriceModifier_addPriceModifier()
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
		PriceModifier priceModifier1, PriceModifier priceModifier2) {

		Assert.assertTrue(
			priceModifier1 + " does not equal " + priceModifier2,
			equals(priceModifier1, priceModifier2));
	}

	protected void assertEquals(
		List<PriceModifier> priceModifiers1,
		List<PriceModifier> priceModifiers2) {

		Assert.assertEquals(priceModifiers1.size(), priceModifiers2.size());

		for (int i = 0; i < priceModifiers1.size(); i++) {
			PriceModifier priceModifier1 = priceModifiers1.get(i);
			PriceModifier priceModifier2 = priceModifiers2.get(i);

			assertEquals(priceModifier1, priceModifier2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<PriceModifier> priceModifiers1,
		List<PriceModifier> priceModifiers2) {

		Assert.assertEquals(priceModifiers1.size(), priceModifiers2.size());

		for (PriceModifier priceModifier1 : priceModifiers1) {
			boolean contains = false;

			for (PriceModifier priceModifier2 : priceModifiers2) {
				if (equals(priceModifier1, priceModifier2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				priceModifiers2 + " does not contain " + priceModifier1,
				contains);
		}
	}

	protected void assertValid(PriceModifier priceModifier) throws Exception {
		boolean valid = true;

		if (priceModifier.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (priceModifier.getActions() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("active", additionalAssertFieldName)) {
				if (priceModifier.getActive() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("displayDate", additionalAssertFieldName)) {
				if (priceModifier.getDisplayDate() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("expirationDate", additionalAssertFieldName)) {
				if (priceModifier.getExpirationDate() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"externalReferenceCode", additionalAssertFieldName)) {

				if (priceModifier.getExternalReferenceCode() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("modifierAmount", additionalAssertFieldName)) {
				if (priceModifier.getModifierAmount() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("modifierType", additionalAssertFieldName)) {
				if (priceModifier.getModifierType() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("neverExpire", additionalAssertFieldName)) {
				if (priceModifier.getNeverExpire() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"priceListExternalReferenceCode",
					additionalAssertFieldName)) {

				if (priceModifier.getPriceListExternalReferenceCode() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("priceListId", additionalAssertFieldName)) {
				if (priceModifier.getPriceListId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"priceModifierCategories", additionalAssertFieldName)) {

				if (priceModifier.getPriceModifierCategories() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"priceModifierProductGroups", additionalAssertFieldName)) {

				if (priceModifier.getPriceModifierProductGroups() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"priceModifierProducts", additionalAssertFieldName)) {

				if (priceModifier.getPriceModifierProducts() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("priority", additionalAssertFieldName)) {
				if (priceModifier.getPriority() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("target", additionalAssertFieldName)) {
				if (priceModifier.getTarget() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("title", additionalAssertFieldName)) {
				if (priceModifier.getTitle() == null) {
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

	protected void assertValid(Page<PriceModifier> page) {
		boolean valid = false;

		java.util.Collection<PriceModifier> priceModifiers = page.getItems();

		int size = priceModifiers.size();

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
						PriceModifier.class)) {

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
		PriceModifier priceModifier1, PriceModifier priceModifier2) {

		if (priceModifier1 == priceModifier2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (!equals(
						(Map)priceModifier1.getActions(),
						(Map)priceModifier2.getActions())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("active", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						priceModifier1.getActive(),
						priceModifier2.getActive())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("displayDate", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						priceModifier1.getDisplayDate(),
						priceModifier2.getDisplayDate())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("expirationDate", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						priceModifier1.getExpirationDate(),
						priceModifier2.getExpirationDate())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"externalReferenceCode", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						priceModifier1.getExternalReferenceCode(),
						priceModifier2.getExternalReferenceCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						priceModifier1.getId(), priceModifier2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("modifierAmount", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						priceModifier1.getModifierAmount(),
						priceModifier2.getModifierAmount())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("modifierType", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						priceModifier1.getModifierType(),
						priceModifier2.getModifierType())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("neverExpire", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						priceModifier1.getNeverExpire(),
						priceModifier2.getNeverExpire())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"priceListExternalReferenceCode",
					additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						priceModifier1.getPriceListExternalReferenceCode(),
						priceModifier2.getPriceListExternalReferenceCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("priceListId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						priceModifier1.getPriceListId(),
						priceModifier2.getPriceListId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"priceModifierCategories", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						priceModifier1.getPriceModifierCategories(),
						priceModifier2.getPriceModifierCategories())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"priceModifierProductGroups", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						priceModifier1.getPriceModifierProductGroups(),
						priceModifier2.getPriceModifierProductGroups())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"priceModifierProducts", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						priceModifier1.getPriceModifierProducts(),
						priceModifier2.getPriceModifierProducts())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("priority", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						priceModifier1.getPriority(),
						priceModifier2.getPriority())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("target", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						priceModifier1.getTarget(),
						priceModifier2.getTarget())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("title", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						priceModifier1.getTitle(), priceModifier2.getTitle())) {

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

		if (!(_priceModifierResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_priceModifierResource;

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
		EntityField entityField, String operator, PriceModifier priceModifier) {

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

		if (entityFieldName.equals("active")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("displayDate")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							priceModifier.getDisplayDate(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							priceModifier.getDisplayDate(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(priceModifier.getDisplayDate()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("expirationDate")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							priceModifier.getExpirationDate(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							priceModifier.getExpirationDate(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(
					_dateFormat.format(priceModifier.getExpirationDate()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("externalReferenceCode")) {
			sb.append("'");
			sb.append(String.valueOf(priceModifier.getExternalReferenceCode()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("modifierAmount")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("modifierType")) {
			sb.append("'");
			sb.append(String.valueOf(priceModifier.getModifierType()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("neverExpire")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("priceListExternalReferenceCode")) {
			sb.append("'");
			sb.append(
				String.valueOf(
					priceModifier.getPriceListExternalReferenceCode()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("priceListId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("priceModifierCategories")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("priceModifierProductGroups")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("priceModifierProducts")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("priority")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("target")) {
			sb.append("'");
			sb.append(String.valueOf(priceModifier.getTarget()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("title")) {
			sb.append("'");
			sb.append(String.valueOf(priceModifier.getTitle()));
			sb.append("'");

			return sb.toString();
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

	protected PriceModifier randomPriceModifier() throws Exception {
		return new PriceModifier() {
			{
				active = RandomTestUtil.randomBoolean();
				displayDate = RandomTestUtil.nextDate();
				expirationDate = RandomTestUtil.nextDate();
				externalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				id = RandomTestUtil.randomLong();
				modifierType = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				neverExpire = RandomTestUtil.randomBoolean();
				priceListExternalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				priceListId = RandomTestUtil.randomLong();
				priority = RandomTestUtil.randomDouble();
				target = StringUtil.toLowerCase(RandomTestUtil.randomString());
				title = StringUtil.toLowerCase(RandomTestUtil.randomString());
			}
		};
	}

	protected PriceModifier randomIrrelevantPriceModifier() throws Exception {
		PriceModifier randomIrrelevantPriceModifier = randomPriceModifier();

		return randomIrrelevantPriceModifier;
	}

	protected PriceModifier randomPatchPriceModifier() throws Exception {
		return randomPriceModifier();
	}

	protected PriceModifierResource priceModifierResource;
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
		BasePriceModifierResourceTestCase.class);

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
		PriceModifierResource _priceModifierResource;

}