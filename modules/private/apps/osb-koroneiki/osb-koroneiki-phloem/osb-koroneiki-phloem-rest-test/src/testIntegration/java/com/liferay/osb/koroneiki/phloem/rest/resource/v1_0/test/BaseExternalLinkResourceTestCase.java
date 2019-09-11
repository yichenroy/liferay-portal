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

import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.ExternalLink;
import com.liferay.osb.koroneiki.phloem.rest.client.http.HttpInvoker;
import com.liferay.osb.koroneiki.phloem.rest.client.pagination.Page;
import com.liferay.osb.koroneiki.phloem.rest.client.pagination.Pagination;
import com.liferay.osb.koroneiki.phloem.rest.client.resource.v1_0.ExternalLinkResource;
import com.liferay.osb.koroneiki.phloem.rest.client.serdes.v1_0.ExternalLinkSerDes;
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
import org.apache.commons.lang.time.DateUtils;

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
public abstract class BaseExternalLinkResourceTestCase {

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

		_externalLinkResource.setContextCompany(testCompany);

		ExternalLinkResource.Builder builder = ExternalLinkResource.builder();

		externalLinkResource = builder.locale(
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

		ExternalLink externalLink1 = randomExternalLink();

		String json = objectMapper.writeValueAsString(externalLink1);

		ExternalLink externalLink2 = ExternalLinkSerDes.toDTO(json);

		Assert.assertTrue(equals(externalLink1, externalLink2));
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

		ExternalLink externalLink = randomExternalLink();

		String json1 = objectMapper.writeValueAsString(externalLink);
		String json2 = ExternalLinkSerDes.toJSON(externalLink);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		ExternalLink externalLink = randomExternalLink();

		externalLink.setDomain(regex);
		externalLink.setEntityId(regex);
		externalLink.setEntityName(regex);
		externalLink.setKey(regex);

		String json = ExternalLinkSerDes.toJSON(externalLink);

		Assert.assertFalse(json.contains(regex));

		externalLink = ExternalLinkSerDes.toDTO(json);

		Assert.assertEquals(regex, externalLink.getDomain());
		Assert.assertEquals(regex, externalLink.getEntityId());
		Assert.assertEquals(regex, externalLink.getEntityName());
		Assert.assertEquals(regex, externalLink.getKey());
	}

	@Test
	public void testGetAccountAccountKeyExternalLinksPage() throws Exception {
		Page<ExternalLink> page =
			externalLinkResource.getAccountAccountKeyExternalLinksPage(
				testGetAccountAccountKeyExternalLinksPage_getAccountKey(),
				Pagination.of(1, 2));

		Assert.assertEquals(0, page.getTotalCount());

		String accountKey =
			testGetAccountAccountKeyExternalLinksPage_getAccountKey();
		String irrelevantAccountKey =
			testGetAccountAccountKeyExternalLinksPage_getIrrelevantAccountKey();

		if ((irrelevantAccountKey != null)) {
			ExternalLink irrelevantExternalLink =
				testGetAccountAccountKeyExternalLinksPage_addExternalLink(
					irrelevantAccountKey, randomIrrelevantExternalLink());

			page = externalLinkResource.getAccountAccountKeyExternalLinksPage(
				irrelevantAccountKey, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantExternalLink),
				(List<ExternalLink>)page.getItems());
			assertValid(page);
		}

		ExternalLink externalLink1 =
			testGetAccountAccountKeyExternalLinksPage_addExternalLink(
				accountKey, randomExternalLink());

		ExternalLink externalLink2 =
			testGetAccountAccountKeyExternalLinksPage_addExternalLink(
				accountKey, randomExternalLink());

		page = externalLinkResource.getAccountAccountKeyExternalLinksPage(
			accountKey, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(externalLink1, externalLink2),
			(List<ExternalLink>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetAccountAccountKeyExternalLinksPageWithPagination()
		throws Exception {

		String accountKey =
			testGetAccountAccountKeyExternalLinksPage_getAccountKey();

		ExternalLink externalLink1 =
			testGetAccountAccountKeyExternalLinksPage_addExternalLink(
				accountKey, randomExternalLink());

		ExternalLink externalLink2 =
			testGetAccountAccountKeyExternalLinksPage_addExternalLink(
				accountKey, randomExternalLink());

		ExternalLink externalLink3 =
			testGetAccountAccountKeyExternalLinksPage_addExternalLink(
				accountKey, randomExternalLink());

		Page<ExternalLink> page1 =
			externalLinkResource.getAccountAccountKeyExternalLinksPage(
				accountKey, Pagination.of(1, 2));

		List<ExternalLink> externalLinks1 =
			(List<ExternalLink>)page1.getItems();

		Assert.assertEquals(
			externalLinks1.toString(), 2, externalLinks1.size());

		Page<ExternalLink> page2 =
			externalLinkResource.getAccountAccountKeyExternalLinksPage(
				accountKey, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<ExternalLink> externalLinks2 =
			(List<ExternalLink>)page2.getItems();

		Assert.assertEquals(
			externalLinks2.toString(), 1, externalLinks2.size());

		Page<ExternalLink> page3 =
			externalLinkResource.getAccountAccountKeyExternalLinksPage(
				accountKey, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(externalLink1, externalLink2, externalLink3),
			(List<ExternalLink>)page3.getItems());
	}

	protected ExternalLink
			testGetAccountAccountKeyExternalLinksPage_addExternalLink(
				String accountKey, ExternalLink externalLink)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String testGetAccountAccountKeyExternalLinksPage_getAccountKey()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetAccountAccountKeyExternalLinksPage_getIrrelevantAccountKey()
		throws Exception {

		return null;
	}

	@Test
	public void testPostAccountAccountKeyExternalLink() throws Exception {
		ExternalLink randomExternalLink = randomExternalLink();

		ExternalLink postExternalLink =
			testPostAccountAccountKeyExternalLink_addExternalLink(
				randomExternalLink);

		assertEquals(randomExternalLink, postExternalLink);
		assertValid(postExternalLink);
	}

	protected ExternalLink
			testPostAccountAccountKeyExternalLink_addExternalLink(
				ExternalLink externalLink)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetContactByOktaExternalLinksPage() throws Exception {
		Page<ExternalLink> page =
			externalLinkResource.getContactByOktaExternalLinksPage(
				testGetContactByOktaExternalLinksPage_getOktaId(),
				Pagination.of(1, 2));

		Assert.assertEquals(0, page.getTotalCount());

		String oktaId = testGetContactByOktaExternalLinksPage_getOktaId();
		String irrelevantOktaId =
			testGetContactByOktaExternalLinksPage_getIrrelevantOktaId();

		if ((irrelevantOktaId != null)) {
			ExternalLink irrelevantExternalLink =
				testGetContactByOktaExternalLinksPage_addExternalLink(
					irrelevantOktaId, randomIrrelevantExternalLink());

			page = externalLinkResource.getContactByOktaExternalLinksPage(
				irrelevantOktaId, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantExternalLink),
				(List<ExternalLink>)page.getItems());
			assertValid(page);
		}

		ExternalLink externalLink1 =
			testGetContactByOktaExternalLinksPage_addExternalLink(
				oktaId, randomExternalLink());

		ExternalLink externalLink2 =
			testGetContactByOktaExternalLinksPage_addExternalLink(
				oktaId, randomExternalLink());

		page = externalLinkResource.getContactByOktaExternalLinksPage(
			oktaId, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(externalLink1, externalLink2),
			(List<ExternalLink>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetContactByOktaExternalLinksPageWithPagination()
		throws Exception {

		String oktaId = testGetContactByOktaExternalLinksPage_getOktaId();

		ExternalLink externalLink1 =
			testGetContactByOktaExternalLinksPage_addExternalLink(
				oktaId, randomExternalLink());

		ExternalLink externalLink2 =
			testGetContactByOktaExternalLinksPage_addExternalLink(
				oktaId, randomExternalLink());

		ExternalLink externalLink3 =
			testGetContactByOktaExternalLinksPage_addExternalLink(
				oktaId, randomExternalLink());

		Page<ExternalLink> page1 =
			externalLinkResource.getContactByOktaExternalLinksPage(
				oktaId, Pagination.of(1, 2));

		List<ExternalLink> externalLinks1 =
			(List<ExternalLink>)page1.getItems();

		Assert.assertEquals(
			externalLinks1.toString(), 2, externalLinks1.size());

		Page<ExternalLink> page2 =
			externalLinkResource.getContactByOktaExternalLinksPage(
				oktaId, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<ExternalLink> externalLinks2 =
			(List<ExternalLink>)page2.getItems();

		Assert.assertEquals(
			externalLinks2.toString(), 1, externalLinks2.size());

		Page<ExternalLink> page3 =
			externalLinkResource.getContactByOktaExternalLinksPage(
				oktaId, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(externalLink1, externalLink2, externalLink3),
			(List<ExternalLink>)page3.getItems());
	}

	protected ExternalLink
			testGetContactByOktaExternalLinksPage_addExternalLink(
				String oktaId, ExternalLink externalLink)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String testGetContactByOktaExternalLinksPage_getOktaId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String testGetContactByOktaExternalLinksPage_getIrrelevantOktaId()
		throws Exception {

		return null;
	}

	@Test
	public void testPostContactByOktaExternalLink() throws Exception {
		ExternalLink randomExternalLink = randomExternalLink();

		ExternalLink postExternalLink =
			testPostContactByOktaExternalLink_addExternalLink(
				randomExternalLink);

		assertEquals(randomExternalLink, postExternalLink);
		assertValid(postExternalLink);
	}

	protected ExternalLink testPostContactByOktaExternalLink_addExternalLink(
			ExternalLink externalLink)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetContactByUuidExternalLinksPage() throws Exception {
		Page<ExternalLink> page =
			externalLinkResource.getContactByUuidExternalLinksPage(
				testGetContactByUuidExternalLinksPage_getUuid(),
				Pagination.of(1, 2));

		Assert.assertEquals(0, page.getTotalCount());

		String uuid = testGetContactByUuidExternalLinksPage_getUuid();
		String irrelevantUuid =
			testGetContactByUuidExternalLinksPage_getIrrelevantUuid();

		if ((irrelevantUuid != null)) {
			ExternalLink irrelevantExternalLink =
				testGetContactByUuidExternalLinksPage_addExternalLink(
					irrelevantUuid, randomIrrelevantExternalLink());

			page = externalLinkResource.getContactByUuidExternalLinksPage(
				irrelevantUuid, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantExternalLink),
				(List<ExternalLink>)page.getItems());
			assertValid(page);
		}

		ExternalLink externalLink1 =
			testGetContactByUuidExternalLinksPage_addExternalLink(
				uuid, randomExternalLink());

		ExternalLink externalLink2 =
			testGetContactByUuidExternalLinksPage_addExternalLink(
				uuid, randomExternalLink());

		page = externalLinkResource.getContactByUuidExternalLinksPage(
			uuid, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(externalLink1, externalLink2),
			(List<ExternalLink>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetContactByUuidExternalLinksPageWithPagination()
		throws Exception {

		String uuid = testGetContactByUuidExternalLinksPage_getUuid();

		ExternalLink externalLink1 =
			testGetContactByUuidExternalLinksPage_addExternalLink(
				uuid, randomExternalLink());

		ExternalLink externalLink2 =
			testGetContactByUuidExternalLinksPage_addExternalLink(
				uuid, randomExternalLink());

		ExternalLink externalLink3 =
			testGetContactByUuidExternalLinksPage_addExternalLink(
				uuid, randomExternalLink());

		Page<ExternalLink> page1 =
			externalLinkResource.getContactByUuidExternalLinksPage(
				uuid, Pagination.of(1, 2));

		List<ExternalLink> externalLinks1 =
			(List<ExternalLink>)page1.getItems();

		Assert.assertEquals(
			externalLinks1.toString(), 2, externalLinks1.size());

		Page<ExternalLink> page2 =
			externalLinkResource.getContactByUuidExternalLinksPage(
				uuid, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<ExternalLink> externalLinks2 =
			(List<ExternalLink>)page2.getItems();

		Assert.assertEquals(
			externalLinks2.toString(), 1, externalLinks2.size());

		Page<ExternalLink> page3 =
			externalLinkResource.getContactByUuidExternalLinksPage(
				uuid, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(externalLink1, externalLink2, externalLink3),
			(List<ExternalLink>)page3.getItems());
	}

	protected ExternalLink
			testGetContactByUuidExternalLinksPage_addExternalLink(
				String uuid, ExternalLink externalLink)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String testGetContactByUuidExternalLinksPage_getUuid()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String testGetContactByUuidExternalLinksPage_getIrrelevantUuid()
		throws Exception {

		return null;
	}

	@Test
	public void testPostContactByUuidExternalLink() throws Exception {
		ExternalLink randomExternalLink = randomExternalLink();

		ExternalLink postExternalLink =
			testPostContactByUuidExternalLink_addExternalLink(
				randomExternalLink);

		assertEquals(randomExternalLink, postExternalLink);
		assertValid(postExternalLink);
	}

	protected ExternalLink testPostContactByUuidExternalLink_addExternalLink(
			ExternalLink externalLink)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testDeleteExternalLink() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testGetExternalLink() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testGetProductConsumptionProductConsumptionKeyExternalLinksPage()
		throws Exception {

		Page<ExternalLink> page =
			externalLinkResource.
				getProductConsumptionProductConsumptionKeyExternalLinksPage(
					testGetProductConsumptionProductConsumptionKeyExternalLinksPage_getProductConsumptionKey(),
					Pagination.of(1, 2));

		Assert.assertEquals(0, page.getTotalCount());

		String productConsumptionKey =
			testGetProductConsumptionProductConsumptionKeyExternalLinksPage_getProductConsumptionKey();
		String irrelevantProductConsumptionKey =
			testGetProductConsumptionProductConsumptionKeyExternalLinksPage_getIrrelevantProductConsumptionKey();

		if ((irrelevantProductConsumptionKey != null)) {
			ExternalLink irrelevantExternalLink =
				testGetProductConsumptionProductConsumptionKeyExternalLinksPage_addExternalLink(
					irrelevantProductConsumptionKey,
					randomIrrelevantExternalLink());

			page =
				externalLinkResource.
					getProductConsumptionProductConsumptionKeyExternalLinksPage(
						irrelevantProductConsumptionKey, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantExternalLink),
				(List<ExternalLink>)page.getItems());
			assertValid(page);
		}

		ExternalLink externalLink1 =
			testGetProductConsumptionProductConsumptionKeyExternalLinksPage_addExternalLink(
				productConsumptionKey, randomExternalLink());

		ExternalLink externalLink2 =
			testGetProductConsumptionProductConsumptionKeyExternalLinksPage_addExternalLink(
				productConsumptionKey, randomExternalLink());

		page =
			externalLinkResource.
				getProductConsumptionProductConsumptionKeyExternalLinksPage(
					productConsumptionKey, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(externalLink1, externalLink2),
			(List<ExternalLink>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetProductConsumptionProductConsumptionKeyExternalLinksPageWithPagination()
		throws Exception {

		String productConsumptionKey =
			testGetProductConsumptionProductConsumptionKeyExternalLinksPage_getProductConsumptionKey();

		ExternalLink externalLink1 =
			testGetProductConsumptionProductConsumptionKeyExternalLinksPage_addExternalLink(
				productConsumptionKey, randomExternalLink());

		ExternalLink externalLink2 =
			testGetProductConsumptionProductConsumptionKeyExternalLinksPage_addExternalLink(
				productConsumptionKey, randomExternalLink());

		ExternalLink externalLink3 =
			testGetProductConsumptionProductConsumptionKeyExternalLinksPage_addExternalLink(
				productConsumptionKey, randomExternalLink());

		Page<ExternalLink> page1 =
			externalLinkResource.
				getProductConsumptionProductConsumptionKeyExternalLinksPage(
					productConsumptionKey, Pagination.of(1, 2));

		List<ExternalLink> externalLinks1 =
			(List<ExternalLink>)page1.getItems();

		Assert.assertEquals(
			externalLinks1.toString(), 2, externalLinks1.size());

		Page<ExternalLink> page2 =
			externalLinkResource.
				getProductConsumptionProductConsumptionKeyExternalLinksPage(
					productConsumptionKey, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<ExternalLink> externalLinks2 =
			(List<ExternalLink>)page2.getItems();

		Assert.assertEquals(
			externalLinks2.toString(), 1, externalLinks2.size());

		Page<ExternalLink> page3 =
			externalLinkResource.
				getProductConsumptionProductConsumptionKeyExternalLinksPage(
					productConsumptionKey, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(externalLink1, externalLink2, externalLink3),
			(List<ExternalLink>)page3.getItems());
	}

	protected ExternalLink
			testGetProductConsumptionProductConsumptionKeyExternalLinksPage_addExternalLink(
				String productConsumptionKey, ExternalLink externalLink)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetProductConsumptionProductConsumptionKeyExternalLinksPage_getProductConsumptionKey()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetProductConsumptionProductConsumptionKeyExternalLinksPage_getIrrelevantProductConsumptionKey()
		throws Exception {

		return null;
	}

	@Test
	public void testPostProductConsumptionProductConsumptionKeyExternalLink()
		throws Exception {

		ExternalLink randomExternalLink = randomExternalLink();

		ExternalLink postExternalLink =
			testPostProductConsumptionProductConsumptionKeyExternalLink_addExternalLink(
				randomExternalLink);

		assertEquals(randomExternalLink, postExternalLink);
		assertValid(postExternalLink);
	}

	protected ExternalLink
			testPostProductConsumptionProductConsumptionKeyExternalLink_addExternalLink(
				ExternalLink externalLink)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetProductPurchaseProductPurchaseKeyExternalLinksPage()
		throws Exception {

		Page<ExternalLink> page =
			externalLinkResource.
				getProductPurchaseProductPurchaseKeyExternalLinksPage(
					testGetProductPurchaseProductPurchaseKeyExternalLinksPage_getProductPurchaseKey(),
					Pagination.of(1, 2));

		Assert.assertEquals(0, page.getTotalCount());

		String productPurchaseKey =
			testGetProductPurchaseProductPurchaseKeyExternalLinksPage_getProductPurchaseKey();
		String irrelevantProductPurchaseKey =
			testGetProductPurchaseProductPurchaseKeyExternalLinksPage_getIrrelevantProductPurchaseKey();

		if ((irrelevantProductPurchaseKey != null)) {
			ExternalLink irrelevantExternalLink =
				testGetProductPurchaseProductPurchaseKeyExternalLinksPage_addExternalLink(
					irrelevantProductPurchaseKey,
					randomIrrelevantExternalLink());

			page =
				externalLinkResource.
					getProductPurchaseProductPurchaseKeyExternalLinksPage(
						irrelevantProductPurchaseKey, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantExternalLink),
				(List<ExternalLink>)page.getItems());
			assertValid(page);
		}

		ExternalLink externalLink1 =
			testGetProductPurchaseProductPurchaseKeyExternalLinksPage_addExternalLink(
				productPurchaseKey, randomExternalLink());

		ExternalLink externalLink2 =
			testGetProductPurchaseProductPurchaseKeyExternalLinksPage_addExternalLink(
				productPurchaseKey, randomExternalLink());

		page =
			externalLinkResource.
				getProductPurchaseProductPurchaseKeyExternalLinksPage(
					productPurchaseKey, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(externalLink1, externalLink2),
			(List<ExternalLink>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetProductPurchaseProductPurchaseKeyExternalLinksPageWithPagination()
		throws Exception {

		String productPurchaseKey =
			testGetProductPurchaseProductPurchaseKeyExternalLinksPage_getProductPurchaseKey();

		ExternalLink externalLink1 =
			testGetProductPurchaseProductPurchaseKeyExternalLinksPage_addExternalLink(
				productPurchaseKey, randomExternalLink());

		ExternalLink externalLink2 =
			testGetProductPurchaseProductPurchaseKeyExternalLinksPage_addExternalLink(
				productPurchaseKey, randomExternalLink());

		ExternalLink externalLink3 =
			testGetProductPurchaseProductPurchaseKeyExternalLinksPage_addExternalLink(
				productPurchaseKey, randomExternalLink());

		Page<ExternalLink> page1 =
			externalLinkResource.
				getProductPurchaseProductPurchaseKeyExternalLinksPage(
					productPurchaseKey, Pagination.of(1, 2));

		List<ExternalLink> externalLinks1 =
			(List<ExternalLink>)page1.getItems();

		Assert.assertEquals(
			externalLinks1.toString(), 2, externalLinks1.size());

		Page<ExternalLink> page2 =
			externalLinkResource.
				getProductPurchaseProductPurchaseKeyExternalLinksPage(
					productPurchaseKey, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<ExternalLink> externalLinks2 =
			(List<ExternalLink>)page2.getItems();

		Assert.assertEquals(
			externalLinks2.toString(), 1, externalLinks2.size());

		Page<ExternalLink> page3 =
			externalLinkResource.
				getProductPurchaseProductPurchaseKeyExternalLinksPage(
					productPurchaseKey, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(externalLink1, externalLink2, externalLink3),
			(List<ExternalLink>)page3.getItems());
	}

	protected ExternalLink
			testGetProductPurchaseProductPurchaseKeyExternalLinksPage_addExternalLink(
				String productPurchaseKey, ExternalLink externalLink)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetProductPurchaseProductPurchaseKeyExternalLinksPage_getProductPurchaseKey()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetProductPurchaseProductPurchaseKeyExternalLinksPage_getIrrelevantProductPurchaseKey()
		throws Exception {

		return null;
	}

	@Test
	public void testPostProductPurchaseProductPurchaseKeyExternalLink()
		throws Exception {

		ExternalLink randomExternalLink = randomExternalLink();

		ExternalLink postExternalLink =
			testPostProductPurchaseProductPurchaseKeyExternalLink_addExternalLink(
				randomExternalLink);

		assertEquals(randomExternalLink, postExternalLink);
		assertValid(postExternalLink);
	}

	protected ExternalLink
			testPostProductPurchaseProductPurchaseKeyExternalLink_addExternalLink(
				ExternalLink externalLink)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetProductProductKeyExternalLinksPage() throws Exception {
		Page<ExternalLink> page =
			externalLinkResource.getProductProductKeyExternalLinksPage(
				testGetProductProductKeyExternalLinksPage_getProductKey(),
				Pagination.of(1, 2));

		Assert.assertEquals(0, page.getTotalCount());

		String productKey =
			testGetProductProductKeyExternalLinksPage_getProductKey();
		String irrelevantProductKey =
			testGetProductProductKeyExternalLinksPage_getIrrelevantProductKey();

		if ((irrelevantProductKey != null)) {
			ExternalLink irrelevantExternalLink =
				testGetProductProductKeyExternalLinksPage_addExternalLink(
					irrelevantProductKey, randomIrrelevantExternalLink());

			page = externalLinkResource.getProductProductKeyExternalLinksPage(
				irrelevantProductKey, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantExternalLink),
				(List<ExternalLink>)page.getItems());
			assertValid(page);
		}

		ExternalLink externalLink1 =
			testGetProductProductKeyExternalLinksPage_addExternalLink(
				productKey, randomExternalLink());

		ExternalLink externalLink2 =
			testGetProductProductKeyExternalLinksPage_addExternalLink(
				productKey, randomExternalLink());

		page = externalLinkResource.getProductProductKeyExternalLinksPage(
			productKey, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(externalLink1, externalLink2),
			(List<ExternalLink>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetProductProductKeyExternalLinksPageWithPagination()
		throws Exception {

		String productKey =
			testGetProductProductKeyExternalLinksPage_getProductKey();

		ExternalLink externalLink1 =
			testGetProductProductKeyExternalLinksPage_addExternalLink(
				productKey, randomExternalLink());

		ExternalLink externalLink2 =
			testGetProductProductKeyExternalLinksPage_addExternalLink(
				productKey, randomExternalLink());

		ExternalLink externalLink3 =
			testGetProductProductKeyExternalLinksPage_addExternalLink(
				productKey, randomExternalLink());

		Page<ExternalLink> page1 =
			externalLinkResource.getProductProductKeyExternalLinksPage(
				productKey, Pagination.of(1, 2));

		List<ExternalLink> externalLinks1 =
			(List<ExternalLink>)page1.getItems();

		Assert.assertEquals(
			externalLinks1.toString(), 2, externalLinks1.size());

		Page<ExternalLink> page2 =
			externalLinkResource.getProductProductKeyExternalLinksPage(
				productKey, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<ExternalLink> externalLinks2 =
			(List<ExternalLink>)page2.getItems();

		Assert.assertEquals(
			externalLinks2.toString(), 1, externalLinks2.size());

		Page<ExternalLink> page3 =
			externalLinkResource.getProductProductKeyExternalLinksPage(
				productKey, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(externalLink1, externalLink2, externalLink3),
			(List<ExternalLink>)page3.getItems());
	}

	protected ExternalLink
			testGetProductProductKeyExternalLinksPage_addExternalLink(
				String productKey, ExternalLink externalLink)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String testGetProductProductKeyExternalLinksPage_getProductKey()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetProductProductKeyExternalLinksPage_getIrrelevantProductKey()
		throws Exception {

		return null;
	}

	@Test
	public void testPostProductProductKeyExternalLink() throws Exception {
		ExternalLink randomExternalLink = randomExternalLink();

		ExternalLink postExternalLink =
			testPostProductProductKeyExternalLink_addExternalLink(
				randomExternalLink);

		assertEquals(randomExternalLink, postExternalLink);
		assertValid(postExternalLink);
	}

	protected ExternalLink
			testPostProductProductKeyExternalLink_addExternalLink(
				ExternalLink externalLink)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetTeamTeamKeyExternalLinksPage() throws Exception {
		Page<ExternalLink> page =
			externalLinkResource.getTeamTeamKeyExternalLinksPage(
				testGetTeamTeamKeyExternalLinksPage_getTeamKey(),
				Pagination.of(1, 2));

		Assert.assertEquals(0, page.getTotalCount());

		String teamKey = testGetTeamTeamKeyExternalLinksPage_getTeamKey();
		String irrelevantTeamKey =
			testGetTeamTeamKeyExternalLinksPage_getIrrelevantTeamKey();

		if ((irrelevantTeamKey != null)) {
			ExternalLink irrelevantExternalLink =
				testGetTeamTeamKeyExternalLinksPage_addExternalLink(
					irrelevantTeamKey, randomIrrelevantExternalLink());

			page = externalLinkResource.getTeamTeamKeyExternalLinksPage(
				irrelevantTeamKey, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantExternalLink),
				(List<ExternalLink>)page.getItems());
			assertValid(page);
		}

		ExternalLink externalLink1 =
			testGetTeamTeamKeyExternalLinksPage_addExternalLink(
				teamKey, randomExternalLink());

		ExternalLink externalLink2 =
			testGetTeamTeamKeyExternalLinksPage_addExternalLink(
				teamKey, randomExternalLink());

		page = externalLinkResource.getTeamTeamKeyExternalLinksPage(
			teamKey, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(externalLink1, externalLink2),
			(List<ExternalLink>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetTeamTeamKeyExternalLinksPageWithPagination()
		throws Exception {

		String teamKey = testGetTeamTeamKeyExternalLinksPage_getTeamKey();

		ExternalLink externalLink1 =
			testGetTeamTeamKeyExternalLinksPage_addExternalLink(
				teamKey, randomExternalLink());

		ExternalLink externalLink2 =
			testGetTeamTeamKeyExternalLinksPage_addExternalLink(
				teamKey, randomExternalLink());

		ExternalLink externalLink3 =
			testGetTeamTeamKeyExternalLinksPage_addExternalLink(
				teamKey, randomExternalLink());

		Page<ExternalLink> page1 =
			externalLinkResource.getTeamTeamKeyExternalLinksPage(
				teamKey, Pagination.of(1, 2));

		List<ExternalLink> externalLinks1 =
			(List<ExternalLink>)page1.getItems();

		Assert.assertEquals(
			externalLinks1.toString(), 2, externalLinks1.size());

		Page<ExternalLink> page2 =
			externalLinkResource.getTeamTeamKeyExternalLinksPage(
				teamKey, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<ExternalLink> externalLinks2 =
			(List<ExternalLink>)page2.getItems();

		Assert.assertEquals(
			externalLinks2.toString(), 1, externalLinks2.size());

		Page<ExternalLink> page3 =
			externalLinkResource.getTeamTeamKeyExternalLinksPage(
				teamKey, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(externalLink1, externalLink2, externalLink3),
			(List<ExternalLink>)page3.getItems());
	}

	protected ExternalLink testGetTeamTeamKeyExternalLinksPage_addExternalLink(
			String teamKey, ExternalLink externalLink)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String testGetTeamTeamKeyExternalLinksPage_getTeamKey()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String testGetTeamTeamKeyExternalLinksPage_getIrrelevantTeamKey()
		throws Exception {

		return null;
	}

	@Test
	public void testPostTeamTeamKeyExternalLink() throws Exception {
		ExternalLink randomExternalLink = randomExternalLink();

		ExternalLink postExternalLink =
			testPostTeamTeamKeyExternalLink_addExternalLink(randomExternalLink);

		assertEquals(randomExternalLink, postExternalLink);
		assertValid(postExternalLink);
	}

	protected ExternalLink testPostTeamTeamKeyExternalLink_addExternalLink(
			ExternalLink externalLink)
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
		ExternalLink externalLink1, ExternalLink externalLink2) {

		Assert.assertTrue(
			externalLink1 + " does not equal " + externalLink2,
			equals(externalLink1, externalLink2));
	}

	protected void assertEquals(
		List<ExternalLink> externalLinks1, List<ExternalLink> externalLinks2) {

		Assert.assertEquals(externalLinks1.size(), externalLinks2.size());

		for (int i = 0; i < externalLinks1.size(); i++) {
			ExternalLink externalLink1 = externalLinks1.get(i);
			ExternalLink externalLink2 = externalLinks2.get(i);

			assertEquals(externalLink1, externalLink2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<ExternalLink> externalLinks1, List<ExternalLink> externalLinks2) {

		Assert.assertEquals(externalLinks1.size(), externalLinks2.size());

		for (ExternalLink externalLink1 : externalLinks1) {
			boolean contains = false;

			for (ExternalLink externalLink2 : externalLinks2) {
				if (equals(externalLink1, externalLink2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				externalLinks2 + " does not contain " + externalLink1,
				contains);
		}
	}

	protected void assertValid(ExternalLink externalLink) {
		boolean valid = true;

		if (externalLink.getDateCreated() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("domain", additionalAssertFieldName)) {
				if (externalLink.getDomain() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("entityId", additionalAssertFieldName)) {
				if (externalLink.getEntityId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("entityName", additionalAssertFieldName)) {
				if (externalLink.getEntityName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("key", additionalAssertFieldName)) {
				if (externalLink.getKey() == null) {
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

	protected void assertValid(Page<ExternalLink> page) {
		boolean valid = false;

		java.util.Collection<ExternalLink> externalLinks = page.getItems();

		int size = externalLinks.size();

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
		ExternalLink externalLink1, ExternalLink externalLink2) {

		if (externalLink1 == externalLink2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("dateCreated", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						externalLink1.getDateCreated(),
						externalLink2.getDateCreated())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("domain", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						externalLink1.getDomain(), externalLink2.getDomain())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("entityId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						externalLink1.getEntityId(),
						externalLink2.getEntityId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("entityName", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						externalLink1.getEntityName(),
						externalLink2.getEntityName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("key", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						externalLink1.getKey(), externalLink2.getKey())) {

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

		if (!(_externalLinkResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_externalLinkResource;

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
		EntityField entityField, String operator, ExternalLink externalLink) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("dateCreated")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							externalLink.getDateCreated(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							externalLink.getDateCreated(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(externalLink.getDateCreated()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("domain")) {
			sb.append("'");
			sb.append(String.valueOf(externalLink.getDomain()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("entityId")) {
			sb.append("'");
			sb.append(String.valueOf(externalLink.getEntityId()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("entityName")) {
			sb.append("'");
			sb.append(String.valueOf(externalLink.getEntityName()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("key")) {
			sb.append("'");
			sb.append(String.valueOf(externalLink.getKey()));
			sb.append("'");

			return sb.toString();
		}

		throw new IllegalArgumentException(
			"Invalid entity field " + entityFieldName);
	}

	protected ExternalLink randomExternalLink() throws Exception {
		return new ExternalLink() {
			{
				dateCreated = RandomTestUtil.nextDate();
				domain = RandomTestUtil.randomString();
				entityId = RandomTestUtil.randomString();
				entityName = RandomTestUtil.randomString();
				key = RandomTestUtil.randomString();
			}
		};
	}

	protected ExternalLink randomIrrelevantExternalLink() throws Exception {
		ExternalLink randomIrrelevantExternalLink = randomExternalLink();

		return randomIrrelevantExternalLink;
	}

	protected ExternalLink randomPatchExternalLink() throws Exception {
		return randomExternalLink();
	}

	protected ExternalLinkResource externalLinkResource;
	protected Group irrelevantGroup;
	protected Company testCompany;
	protected Group testGroup;

	private static final Log _log = LogFactoryUtil.getLog(
		BaseExternalLinkResourceTestCase.class);

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
		com.liferay.osb.koroneiki.phloem.rest.resource.v1_0.ExternalLinkResource
			_externalLinkResource;

}