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

import com.liferay.headless.commerce.admin.pricing.client.dto.v2_0.PriceListChannel;
import com.liferay.headless.commerce.admin.pricing.client.http.HttpInvoker;
import com.liferay.headless.commerce.admin.pricing.client.pagination.Page;
import com.liferay.headless.commerce.admin.pricing.client.pagination.Pagination;
import com.liferay.headless.commerce.admin.pricing.client.resource.v2_0.PriceListChannelResource;
import com.liferay.headless.commerce.admin.pricing.client.serdes.v2_0.PriceListChannelSerDes;
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
public abstract class BasePriceListChannelResourceTestCase {

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

		_priceListChannelResource.setContextCompany(testCompany);

		PriceListChannelResource.Builder builder =
			PriceListChannelResource.builder();

		priceListChannelResource = builder.authentication(
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

		PriceListChannel priceListChannel1 = randomPriceListChannel();

		String json = objectMapper.writeValueAsString(priceListChannel1);

		PriceListChannel priceListChannel2 = PriceListChannelSerDes.toDTO(json);

		Assert.assertTrue(equals(priceListChannel1, priceListChannel2));
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

		PriceListChannel priceListChannel = randomPriceListChannel();

		String json1 = objectMapper.writeValueAsString(priceListChannel);
		String json2 = PriceListChannelSerDes.toJSON(priceListChannel);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		PriceListChannel priceListChannel = randomPriceListChannel();

		priceListChannel.setChannelExternalReferenceCode(regex);
		priceListChannel.setPriceListExternalReferenceCode(regex);

		String json = PriceListChannelSerDes.toJSON(priceListChannel);

		Assert.assertFalse(json.contains(regex));

		priceListChannel = PriceListChannelSerDes.toDTO(json);

		Assert.assertEquals(
			regex, priceListChannel.getChannelExternalReferenceCode());
		Assert.assertEquals(
			regex, priceListChannel.getPriceListExternalReferenceCode());
	}

	@Test
	public void testDeletePriceListChannel() throws Exception {
		Assert.assertTrue(false);
	}

	@Test
	public void testGraphQLDeletePriceListChannel() throws Exception {
		Assert.assertTrue(false);
	}

	@Test
	public void testGetPriceListByExternalReferenceCodePriceListChannelsPage()
		throws Exception {

		Page<PriceListChannel> page =
			priceListChannelResource.
				getPriceListByExternalReferenceCodePriceListChannelsPage(
					testGetPriceListByExternalReferenceCodePriceListChannelsPage_getExternalReferenceCode(),
					Pagination.of(1, 2));

		Assert.assertEquals(0, page.getTotalCount());

		String externalReferenceCode =
			testGetPriceListByExternalReferenceCodePriceListChannelsPage_getExternalReferenceCode();
		String irrelevantExternalReferenceCode =
			testGetPriceListByExternalReferenceCodePriceListChannelsPage_getIrrelevantExternalReferenceCode();

		if ((irrelevantExternalReferenceCode != null)) {
			PriceListChannel irrelevantPriceListChannel =
				testGetPriceListByExternalReferenceCodePriceListChannelsPage_addPriceListChannel(
					irrelevantExternalReferenceCode,
					randomIrrelevantPriceListChannel());

			page =
				priceListChannelResource.
					getPriceListByExternalReferenceCodePriceListChannelsPage(
						irrelevantExternalReferenceCode, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantPriceListChannel),
				(List<PriceListChannel>)page.getItems());
			assertValid(page);
		}

		PriceListChannel priceListChannel1 =
			testGetPriceListByExternalReferenceCodePriceListChannelsPage_addPriceListChannel(
				externalReferenceCode, randomPriceListChannel());

		PriceListChannel priceListChannel2 =
			testGetPriceListByExternalReferenceCodePriceListChannelsPage_addPriceListChannel(
				externalReferenceCode, randomPriceListChannel());

		page =
			priceListChannelResource.
				getPriceListByExternalReferenceCodePriceListChannelsPage(
					externalReferenceCode, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(priceListChannel1, priceListChannel2),
			(List<PriceListChannel>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetPriceListByExternalReferenceCodePriceListChannelsPageWithPagination()
		throws Exception {

		String externalReferenceCode =
			testGetPriceListByExternalReferenceCodePriceListChannelsPage_getExternalReferenceCode();

		PriceListChannel priceListChannel1 =
			testGetPriceListByExternalReferenceCodePriceListChannelsPage_addPriceListChannel(
				externalReferenceCode, randomPriceListChannel());

		PriceListChannel priceListChannel2 =
			testGetPriceListByExternalReferenceCodePriceListChannelsPage_addPriceListChannel(
				externalReferenceCode, randomPriceListChannel());

		PriceListChannel priceListChannel3 =
			testGetPriceListByExternalReferenceCodePriceListChannelsPage_addPriceListChannel(
				externalReferenceCode, randomPriceListChannel());

		Page<PriceListChannel> page1 =
			priceListChannelResource.
				getPriceListByExternalReferenceCodePriceListChannelsPage(
					externalReferenceCode, Pagination.of(1, 2));

		List<PriceListChannel> priceListChannels1 =
			(List<PriceListChannel>)page1.getItems();

		Assert.assertEquals(
			priceListChannels1.toString(), 2, priceListChannels1.size());

		Page<PriceListChannel> page2 =
			priceListChannelResource.
				getPriceListByExternalReferenceCodePriceListChannelsPage(
					externalReferenceCode, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<PriceListChannel> priceListChannels2 =
			(List<PriceListChannel>)page2.getItems();

		Assert.assertEquals(
			priceListChannels2.toString(), 1, priceListChannels2.size());

		Page<PriceListChannel> page3 =
			priceListChannelResource.
				getPriceListByExternalReferenceCodePriceListChannelsPage(
					externalReferenceCode, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(
				priceListChannel1, priceListChannel2, priceListChannel3),
			(List<PriceListChannel>)page3.getItems());
	}

	protected PriceListChannel
			testGetPriceListByExternalReferenceCodePriceListChannelsPage_addPriceListChannel(
				String externalReferenceCode, PriceListChannel priceListChannel)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetPriceListByExternalReferenceCodePriceListChannelsPage_getExternalReferenceCode()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetPriceListByExternalReferenceCodePriceListChannelsPage_getIrrelevantExternalReferenceCode()
		throws Exception {

		return null;
	}

	@Test
	public void testPostPriceListByExternalReferenceCodePriceListChannel()
		throws Exception {

		PriceListChannel randomPriceListChannel = randomPriceListChannel();

		PriceListChannel postPriceListChannel =
			testPostPriceListByExternalReferenceCodePriceListChannel_addPriceListChannel(
				randomPriceListChannel);

		assertEquals(randomPriceListChannel, postPriceListChannel);
		assertValid(postPriceListChannel);
	}

	protected PriceListChannel
			testPostPriceListByExternalReferenceCodePriceListChannel_addPriceListChannel(
				PriceListChannel priceListChannel)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetPriceListIdPriceListChannelsPage() throws Exception {
		Page<PriceListChannel> page =
			priceListChannelResource.getPriceListIdPriceListChannelsPage(
				testGetPriceListIdPriceListChannelsPage_getId(),
				RandomTestUtil.randomString(), null, Pagination.of(1, 2), null);

		Assert.assertEquals(0, page.getTotalCount());

		Long id = testGetPriceListIdPriceListChannelsPage_getId();
		Long irrelevantId =
			testGetPriceListIdPriceListChannelsPage_getIrrelevantId();

		if ((irrelevantId != null)) {
			PriceListChannel irrelevantPriceListChannel =
				testGetPriceListIdPriceListChannelsPage_addPriceListChannel(
					irrelevantId, randomIrrelevantPriceListChannel());

			page = priceListChannelResource.getPriceListIdPriceListChannelsPage(
				irrelevantId, null, null, Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantPriceListChannel),
				(List<PriceListChannel>)page.getItems());
			assertValid(page);
		}

		PriceListChannel priceListChannel1 =
			testGetPriceListIdPriceListChannelsPage_addPriceListChannel(
				id, randomPriceListChannel());

		PriceListChannel priceListChannel2 =
			testGetPriceListIdPriceListChannelsPage_addPriceListChannel(
				id, randomPriceListChannel());

		page = priceListChannelResource.getPriceListIdPriceListChannelsPage(
			id, null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(priceListChannel1, priceListChannel2),
			(List<PriceListChannel>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetPriceListIdPriceListChannelsPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long id = testGetPriceListIdPriceListChannelsPage_getId();

		PriceListChannel priceListChannel1 = randomPriceListChannel();

		priceListChannel1 =
			testGetPriceListIdPriceListChannelsPage_addPriceListChannel(
				id, priceListChannel1);

		for (EntityField entityField : entityFields) {
			Page<PriceListChannel> page =
				priceListChannelResource.getPriceListIdPriceListChannelsPage(
					id, null,
					getFilterString(entityField, "between", priceListChannel1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(priceListChannel1),
				(List<PriceListChannel>)page.getItems());
		}
	}

	@Test
	public void testGetPriceListIdPriceListChannelsPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long id = testGetPriceListIdPriceListChannelsPage_getId();

		PriceListChannel priceListChannel1 =
			testGetPriceListIdPriceListChannelsPage_addPriceListChannel(
				id, randomPriceListChannel());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		PriceListChannel priceListChannel2 =
			testGetPriceListIdPriceListChannelsPage_addPriceListChannel(
				id, randomPriceListChannel());

		for (EntityField entityField : entityFields) {
			Page<PriceListChannel> page =
				priceListChannelResource.getPriceListIdPriceListChannelsPage(
					id, null,
					getFilterString(entityField, "eq", priceListChannel1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(priceListChannel1),
				(List<PriceListChannel>)page.getItems());
		}
	}

	@Test
	public void testGetPriceListIdPriceListChannelsPageWithPagination()
		throws Exception {

		Long id = testGetPriceListIdPriceListChannelsPage_getId();

		PriceListChannel priceListChannel1 =
			testGetPriceListIdPriceListChannelsPage_addPriceListChannel(
				id, randomPriceListChannel());

		PriceListChannel priceListChannel2 =
			testGetPriceListIdPriceListChannelsPage_addPriceListChannel(
				id, randomPriceListChannel());

		PriceListChannel priceListChannel3 =
			testGetPriceListIdPriceListChannelsPage_addPriceListChannel(
				id, randomPriceListChannel());

		Page<PriceListChannel> page1 =
			priceListChannelResource.getPriceListIdPriceListChannelsPage(
				id, null, null, Pagination.of(1, 2), null);

		List<PriceListChannel> priceListChannels1 =
			(List<PriceListChannel>)page1.getItems();

		Assert.assertEquals(
			priceListChannels1.toString(), 2, priceListChannels1.size());

		Page<PriceListChannel> page2 =
			priceListChannelResource.getPriceListIdPriceListChannelsPage(
				id, null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<PriceListChannel> priceListChannels2 =
			(List<PriceListChannel>)page2.getItems();

		Assert.assertEquals(
			priceListChannels2.toString(), 1, priceListChannels2.size());

		Page<PriceListChannel> page3 =
			priceListChannelResource.getPriceListIdPriceListChannelsPage(
				id, null, null, Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(
				priceListChannel1, priceListChannel2, priceListChannel3),
			(List<PriceListChannel>)page3.getItems());
	}

	@Test
	public void testGetPriceListIdPriceListChannelsPageWithSortDateTime()
		throws Exception {

		testGetPriceListIdPriceListChannelsPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, priceListChannel1, priceListChannel2) -> {
				BeanUtils.setProperty(
					priceListChannel1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetPriceListIdPriceListChannelsPageWithSortInteger()
		throws Exception {

		testGetPriceListIdPriceListChannelsPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, priceListChannel1, priceListChannel2) -> {
				BeanUtils.setProperty(
					priceListChannel1, entityField.getName(), 0);
				BeanUtils.setProperty(
					priceListChannel2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetPriceListIdPriceListChannelsPageWithSortString()
		throws Exception {

		testGetPriceListIdPriceListChannelsPageWithSort(
			EntityField.Type.STRING,
			(entityField, priceListChannel1, priceListChannel2) -> {
				Class<?> clazz = priceListChannel1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						priceListChannel1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						priceListChannel2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanUtils.setProperty(
						priceListChannel1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanUtils.setProperty(
						priceListChannel2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanUtils.setProperty(
						priceListChannel1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanUtils.setProperty(
						priceListChannel2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetPriceListIdPriceListChannelsPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer
				<EntityField, PriceListChannel, PriceListChannel, Exception>
					unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long id = testGetPriceListIdPriceListChannelsPage_getId();

		PriceListChannel priceListChannel1 = randomPriceListChannel();
		PriceListChannel priceListChannel2 = randomPriceListChannel();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(
				entityField, priceListChannel1, priceListChannel2);
		}

		priceListChannel1 =
			testGetPriceListIdPriceListChannelsPage_addPriceListChannel(
				id, priceListChannel1);

		priceListChannel2 =
			testGetPriceListIdPriceListChannelsPage_addPriceListChannel(
				id, priceListChannel2);

		for (EntityField entityField : entityFields) {
			Page<PriceListChannel> ascPage =
				priceListChannelResource.getPriceListIdPriceListChannelsPage(
					id, null, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(priceListChannel1, priceListChannel2),
				(List<PriceListChannel>)ascPage.getItems());

			Page<PriceListChannel> descPage =
				priceListChannelResource.getPriceListIdPriceListChannelsPage(
					id, null, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(priceListChannel2, priceListChannel1),
				(List<PriceListChannel>)descPage.getItems());
		}
	}

	protected PriceListChannel
			testGetPriceListIdPriceListChannelsPage_addPriceListChannel(
				Long id, PriceListChannel priceListChannel)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetPriceListIdPriceListChannelsPage_getId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetPriceListIdPriceListChannelsPage_getIrrelevantId()
		throws Exception {

		return null;
	}

	@Test
	public void testPostPriceListIdPriceListChannel() throws Exception {
		PriceListChannel randomPriceListChannel = randomPriceListChannel();

		PriceListChannel postPriceListChannel =
			testPostPriceListIdPriceListChannel_addPriceListChannel(
				randomPriceListChannel);

		assertEquals(randomPriceListChannel, postPriceListChannel);
		assertValid(postPriceListChannel);
	}

	protected PriceListChannel
			testPostPriceListIdPriceListChannel_addPriceListChannel(
				PriceListChannel priceListChannel)
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
		PriceListChannel priceListChannel1,
		PriceListChannel priceListChannel2) {

		Assert.assertTrue(
			priceListChannel1 + " does not equal " + priceListChannel2,
			equals(priceListChannel1, priceListChannel2));
	}

	protected void assertEquals(
		List<PriceListChannel> priceListChannels1,
		List<PriceListChannel> priceListChannels2) {

		Assert.assertEquals(
			priceListChannels1.size(), priceListChannels2.size());

		for (int i = 0; i < priceListChannels1.size(); i++) {
			PriceListChannel priceListChannel1 = priceListChannels1.get(i);
			PriceListChannel priceListChannel2 = priceListChannels2.get(i);

			assertEquals(priceListChannel1, priceListChannel2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<PriceListChannel> priceListChannels1,
		List<PriceListChannel> priceListChannels2) {

		Assert.assertEquals(
			priceListChannels1.size(), priceListChannels2.size());

		for (PriceListChannel priceListChannel1 : priceListChannels1) {
			boolean contains = false;

			for (PriceListChannel priceListChannel2 : priceListChannels2) {
				if (equals(priceListChannel1, priceListChannel2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				priceListChannels2 + " does not contain " + priceListChannel1,
				contains);
		}
	}

	protected void assertValid(PriceListChannel priceListChannel)
		throws Exception {

		boolean valid = true;

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (priceListChannel.getActions() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("channel", additionalAssertFieldName)) {
				if (priceListChannel.getChannel() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"channelExternalReferenceCode",
					additionalAssertFieldName)) {

				if (priceListChannel.getChannelExternalReferenceCode() ==
						null) {

					valid = false;
				}

				continue;
			}

			if (Objects.equals("channelId", additionalAssertFieldName)) {
				if (priceListChannel.getChannelId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("order", additionalAssertFieldName)) {
				if (priceListChannel.getOrder() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"priceListChannelId", additionalAssertFieldName)) {

				if (priceListChannel.getPriceListChannelId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"priceListExternalReferenceCode",
					additionalAssertFieldName)) {

				if (priceListChannel.getPriceListExternalReferenceCode() ==
						null) {

					valid = false;
				}

				continue;
			}

			if (Objects.equals("priceListId", additionalAssertFieldName)) {
				if (priceListChannel.getPriceListId() == null) {
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

	protected void assertValid(Page<PriceListChannel> page) {
		boolean valid = false;

		java.util.Collection<PriceListChannel> priceListChannels =
			page.getItems();

		int size = priceListChannels.size();

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
						PriceListChannel.class)) {

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
		PriceListChannel priceListChannel1,
		PriceListChannel priceListChannel2) {

		if (priceListChannel1 == priceListChannel2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (!equals(
						(Map)priceListChannel1.getActions(),
						(Map)priceListChannel2.getActions())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("channel", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						priceListChannel1.getChannel(),
						priceListChannel2.getChannel())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"channelExternalReferenceCode",
					additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						priceListChannel1.getChannelExternalReferenceCode(),
						priceListChannel2.getChannelExternalReferenceCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("channelId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						priceListChannel1.getChannelId(),
						priceListChannel2.getChannelId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("order", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						priceListChannel1.getOrder(),
						priceListChannel2.getOrder())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"priceListChannelId", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						priceListChannel1.getPriceListChannelId(),
						priceListChannel2.getPriceListChannelId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"priceListExternalReferenceCode",
					additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						priceListChannel1.getPriceListExternalReferenceCode(),
						priceListChannel2.
							getPriceListExternalReferenceCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("priceListId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						priceListChannel1.getPriceListId(),
						priceListChannel2.getPriceListId())) {

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

		if (!(_priceListChannelResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_priceListChannelResource;

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
		PriceListChannel priceListChannel) {

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

		if (entityFieldName.equals("channel")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("channelExternalReferenceCode")) {
			sb.append("'");
			sb.append(
				String.valueOf(
					priceListChannel.getChannelExternalReferenceCode()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("channelId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("order")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("priceListChannelId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("priceListExternalReferenceCode")) {
			sb.append("'");
			sb.append(
				String.valueOf(
					priceListChannel.getPriceListExternalReferenceCode()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("priceListId")) {
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

	protected PriceListChannel randomPriceListChannel() throws Exception {
		return new PriceListChannel() {
			{
				channelExternalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				channelId = RandomTestUtil.randomLong();
				order = RandomTestUtil.randomInt();
				priceListChannelId = RandomTestUtil.randomLong();
				priceListExternalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				priceListId = RandomTestUtil.randomLong();
			}
		};
	}

	protected PriceListChannel randomIrrelevantPriceListChannel()
		throws Exception {

		PriceListChannel randomIrrelevantPriceListChannel =
			randomPriceListChannel();

		return randomIrrelevantPriceListChannel;
	}

	protected PriceListChannel randomPatchPriceListChannel() throws Exception {
		return randomPriceListChannel();
	}

	protected PriceListChannelResource priceListChannelResource;
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
		BasePriceListChannelResourceTestCase.class);

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
		PriceListChannelResource _priceListChannelResource;

}