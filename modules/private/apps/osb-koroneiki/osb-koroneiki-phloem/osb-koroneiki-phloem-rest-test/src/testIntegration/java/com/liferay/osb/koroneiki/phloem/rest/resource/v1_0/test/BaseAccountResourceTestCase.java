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

import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Account;
import com.liferay.osb.koroneiki.phloem.rest.client.http.HttpInvoker;
import com.liferay.osb.koroneiki.phloem.rest.client.pagination.Page;
import com.liferay.osb.koroneiki.phloem.rest.client.pagination.Pagination;
import com.liferay.osb.koroneiki.phloem.rest.client.resource.v1_0.AccountResource;
import com.liferay.osb.koroneiki.phloem.rest.client.serdes.v1_0.AccountSerDes;
import com.liferay.petra.function.UnsafeTriConsumer;
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
import java.util.Collections;
import java.util.Date;
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
 * @author Amos Fong
 * @generated
 */
@Generated("")
public abstract class BaseAccountResourceTestCase {

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

		_accountResource.setContextCompany(testCompany);

		AccountResource.Builder builder = AccountResource.builder();

		accountResource = builder.locale(
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

		Account account1 = randomAccount();

		String json = objectMapper.writeValueAsString(account1);

		Account account2 = AccountSerDes.toDTO(json);

		Assert.assertTrue(equals(account1, account2));
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

		Account account = randomAccount();

		String json1 = objectMapper.writeValueAsString(account);
		String json2 = AccountSerDes.toJSON(account);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		Account account = randomAccount();

		account.setCode(regex);
		account.setContactEmailAddress(regex);
		account.setDescription(regex);
		account.setFaxNumber(regex);
		account.setKey(regex);
		account.setName(regex);
		account.setNotes(regex);
		account.setParentAccountKey(regex);
		account.setPhoneNumber(regex);
		account.setProfileEmailAddress(regex);
		account.setSoldBy(regex);
		account.setWebsite(regex);

		String json = AccountSerDes.toJSON(account);

		Assert.assertFalse(json.contains(regex));

		account = AccountSerDes.toDTO(json);

		Assert.assertEquals(regex, account.getCode());
		Assert.assertEquals(regex, account.getContactEmailAddress());
		Assert.assertEquals(regex, account.getDescription());
		Assert.assertEquals(regex, account.getFaxNumber());
		Assert.assertEquals(regex, account.getKey());
		Assert.assertEquals(regex, account.getName());
		Assert.assertEquals(regex, account.getNotes());
		Assert.assertEquals(regex, account.getParentAccountKey());
		Assert.assertEquals(regex, account.getPhoneNumber());
		Assert.assertEquals(regex, account.getProfileEmailAddress());
		Assert.assertEquals(regex, account.getSoldBy());
		Assert.assertEquals(regex, account.getWebsite());
	}

	@Test
	public void testGetAccountsPage() throws Exception {
		Page<Account> page = accountResource.getAccountsPage(
			RandomTestUtil.randomString(), null, Pagination.of(1, 2), null);

		Assert.assertEquals(0, page.getTotalCount());

		Account account1 = testGetAccountsPage_addAccount(randomAccount());

		Account account2 = testGetAccountsPage_addAccount(randomAccount());

		page = accountResource.getAccountsPage(
			null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(account1, account2), (List<Account>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetAccountsPageWithFilterDateTimeEquals() throws Exception {
		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Account account1 = randomAccount();

		account1 = testGetAccountsPage_addAccount(account1);

		for (EntityField entityField : entityFields) {
			Page<Account> page = accountResource.getAccountsPage(
				null, getFilterString(entityField, "between", account1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(account1),
				(List<Account>)page.getItems());
		}
	}

	@Test
	public void testGetAccountsPageWithFilterStringEquals() throws Exception {
		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Account account1 = testGetAccountsPage_addAccount(randomAccount());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		Account account2 = testGetAccountsPage_addAccount(randomAccount());

		for (EntityField entityField : entityFields) {
			Page<Account> page = accountResource.getAccountsPage(
				null, getFilterString(entityField, "eq", account1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(account1),
				(List<Account>)page.getItems());
		}
	}

	@Test
	public void testGetAccountsPageWithPagination() throws Exception {
		Account account1 = testGetAccountsPage_addAccount(randomAccount());

		Account account2 = testGetAccountsPage_addAccount(randomAccount());

		Account account3 = testGetAccountsPage_addAccount(randomAccount());

		Page<Account> page1 = accountResource.getAccountsPage(
			null, null, Pagination.of(1, 2), null);

		List<Account> accounts1 = (List<Account>)page1.getItems();

		Assert.assertEquals(accounts1.toString(), 2, accounts1.size());

		Page<Account> page2 = accountResource.getAccountsPage(
			null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<Account> accounts2 = (List<Account>)page2.getItems();

		Assert.assertEquals(accounts2.toString(), 1, accounts2.size());

		Page<Account> page3 = accountResource.getAccountsPage(
			null, null, Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(account1, account2, account3),
			(List<Account>)page3.getItems());
	}

	@Test
	public void testGetAccountsPageWithSortDateTime() throws Exception {
		testGetAccountsPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, account1, account2) -> {
				BeanUtils.setProperty(
					account1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetAccountsPageWithSortInteger() throws Exception {
		testGetAccountsPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, account1, account2) -> {
				BeanUtils.setProperty(account1, entityField.getName(), 0);
				BeanUtils.setProperty(account2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetAccountsPageWithSortString() throws Exception {
		testGetAccountsPageWithSort(
			EntityField.Type.STRING,
			(entityField, account1, account2) -> {
				BeanUtils.setProperty(account1, entityField.getName(), "Aaa");
				BeanUtils.setProperty(account2, entityField.getName(), "Bbb");
			});
	}

	protected void testGetAccountsPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer<EntityField, Account, Account, Exception>
				unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Account account1 = randomAccount();
		Account account2 = randomAccount();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(entityField, account1, account2);
		}

		account1 = testGetAccountsPage_addAccount(account1);

		account2 = testGetAccountsPage_addAccount(account2);

		for (EntityField entityField : entityFields) {
			Page<Account> ascPage = accountResource.getAccountsPage(
				null, null, Pagination.of(1, 2),
				entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(account1, account2),
				(List<Account>)ascPage.getItems());

			Page<Account> descPage = accountResource.getAccountsPage(
				null, null, Pagination.of(1, 2),
				entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(account2, account1),
				(List<Account>)descPage.getItems());
		}
	}

	protected Account testGetAccountsPage_addAccount(Account account)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPostAccount() throws Exception {
		Account randomAccount = randomAccount();

		Account postAccount = testPostAccount_addAccount(randomAccount);

		assertEquals(randomAccount, postAccount);
		assertValid(postAccount);
	}

	protected Account testPostAccount_addAccount(Account account)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetAccountByExternalLinkDomainEntityNameEntity()
		throws Exception {

		Page<Account> page =
			accountResource.getAccountByExternalLinkDomainEntityNameEntity(
				testGetAccountByExternalLinkDomainEntityNameEntity_getDomain(),
				testGetAccountByExternalLinkDomainEntityNameEntity_getEntityName(),
				testGetAccountByExternalLinkDomainEntityNameEntity_getEntityId(),
				Pagination.of(1, 2));

		Assert.assertEquals(0, page.getTotalCount());

		String domain =
			testGetAccountByExternalLinkDomainEntityNameEntity_getDomain();
		String irrelevantDomain =
			testGetAccountByExternalLinkDomainEntityNameEntity_getIrrelevantDomain();
		String entityName =
			testGetAccountByExternalLinkDomainEntityNameEntity_getEntityName();
		String irrelevantEntityName =
			testGetAccountByExternalLinkDomainEntityNameEntity_getIrrelevantEntityName();
		String entityId =
			testGetAccountByExternalLinkDomainEntityNameEntity_getEntityId();
		String irrelevantEntityId =
			testGetAccountByExternalLinkDomainEntityNameEntity_getIrrelevantEntityId();

		if ((irrelevantDomain != null) && (irrelevantEntityName != null) &&
			(irrelevantEntityId != null)) {

			Account irrelevantAccount =
				testGetAccountByExternalLinkDomainEntityNameEntity_addAccount(
					irrelevantDomain, irrelevantEntityName, irrelevantEntityId,
					randomIrrelevantAccount());

			page =
				accountResource.getAccountByExternalLinkDomainEntityNameEntity(
					irrelevantDomain, irrelevantEntityName, irrelevantEntityId,
					Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantAccount),
				(List<Account>)page.getItems());
			assertValid(page);
		}

		Account account1 =
			testGetAccountByExternalLinkDomainEntityNameEntity_addAccount(
				domain, entityName, entityId, randomAccount());

		Account account2 =
			testGetAccountByExternalLinkDomainEntityNameEntity_addAccount(
				domain, entityName, entityId, randomAccount());

		page = accountResource.getAccountByExternalLinkDomainEntityNameEntity(
			domain, entityName, entityId, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(account1, account2), (List<Account>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetAccountByExternalLinkDomainEntityNameEntityWithPagination()
		throws Exception {

		String domain =
			testGetAccountByExternalLinkDomainEntityNameEntity_getDomain();
		String entityName =
			testGetAccountByExternalLinkDomainEntityNameEntity_getEntityName();
		String entityId =
			testGetAccountByExternalLinkDomainEntityNameEntity_getEntityId();

		Account account1 =
			testGetAccountByExternalLinkDomainEntityNameEntity_addAccount(
				domain, entityName, entityId, randomAccount());

		Account account2 =
			testGetAccountByExternalLinkDomainEntityNameEntity_addAccount(
				domain, entityName, entityId, randomAccount());

		Account account3 =
			testGetAccountByExternalLinkDomainEntityNameEntity_addAccount(
				domain, entityName, entityId, randomAccount());

		Page<Account> page1 =
			accountResource.getAccountByExternalLinkDomainEntityNameEntity(
				domain, entityName, entityId, Pagination.of(1, 2));

		List<Account> accounts1 = (List<Account>)page1.getItems();

		Assert.assertEquals(accounts1.toString(), 2, accounts1.size());

		Page<Account> page2 =
			accountResource.getAccountByExternalLinkDomainEntityNameEntity(
				domain, entityName, entityId, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<Account> accounts2 = (List<Account>)page2.getItems();

		Assert.assertEquals(accounts2.toString(), 1, accounts2.size());

		Page<Account> page3 =
			accountResource.getAccountByExternalLinkDomainEntityNameEntity(
				domain, entityName, entityId, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(account1, account2, account3),
			(List<Account>)page3.getItems());
	}

	protected Account
			testGetAccountByExternalLinkDomainEntityNameEntity_addAccount(
				String domain, String entityName, String entityId,
				Account account)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetAccountByExternalLinkDomainEntityNameEntity_getDomain()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetAccountByExternalLinkDomainEntityNameEntity_getIrrelevantDomain()
		throws Exception {

		return null;
	}

	protected String
			testGetAccountByExternalLinkDomainEntityNameEntity_getEntityName()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetAccountByExternalLinkDomainEntityNameEntity_getIrrelevantEntityName()
		throws Exception {

		return null;
	}

	protected String
			testGetAccountByExternalLinkDomainEntityNameEntity_getEntityId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetAccountByExternalLinkDomainEntityNameEntity_getIrrelevantEntityId()
		throws Exception {

		return null;
	}

	@Test
	public void testDeleteAccount() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testGetAccount() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testPutAccount() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testGetAccountChildAccountsPage() throws Exception {
		Page<Account> page = accountResource.getAccountChildAccountsPage(
			testGetAccountChildAccountsPage_getAccountKey(), null,
			Pagination.of(1, 2));

		Assert.assertEquals(0, page.getTotalCount());

		String accountKey = testGetAccountChildAccountsPage_getAccountKey();
		String irrelevantAccountKey =
			testGetAccountChildAccountsPage_getIrrelevantAccountKey();

		if ((irrelevantAccountKey != null)) {
			Account irrelevantAccount =
				testGetAccountChildAccountsPage_addAccount(
					irrelevantAccountKey, randomIrrelevantAccount());

			page = accountResource.getAccountChildAccountsPage(
				irrelevantAccountKey, null, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantAccount),
				(List<Account>)page.getItems());
			assertValid(page);
		}

		Account account1 = testGetAccountChildAccountsPage_addAccount(
			accountKey, randomAccount());

		Account account2 = testGetAccountChildAccountsPage_addAccount(
			accountKey, randomAccount());

		page = accountResource.getAccountChildAccountsPage(
			accountKey, null, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(account1, account2), (List<Account>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetAccountChildAccountsPageWithPagination()
		throws Exception {

		String accountKey = testGetAccountChildAccountsPage_getAccountKey();

		Account account1 = testGetAccountChildAccountsPage_addAccount(
			accountKey, randomAccount());

		Account account2 = testGetAccountChildAccountsPage_addAccount(
			accountKey, randomAccount());

		Account account3 = testGetAccountChildAccountsPage_addAccount(
			accountKey, randomAccount());

		Page<Account> page1 = accountResource.getAccountChildAccountsPage(
			accountKey, null, Pagination.of(1, 2));

		List<Account> accounts1 = (List<Account>)page1.getItems();

		Assert.assertEquals(accounts1.toString(), 2, accounts1.size());

		Page<Account> page2 = accountResource.getAccountChildAccountsPage(
			accountKey, null, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<Account> accounts2 = (List<Account>)page2.getItems();

		Assert.assertEquals(accounts2.toString(), 1, accounts2.size());

		Page<Account> page3 = accountResource.getAccountChildAccountsPage(
			accountKey, null, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(account1, account2, account3),
			(List<Account>)page3.getItems());
	}

	protected Account testGetAccountChildAccountsPage_addAccount(
			String accountKey, Account account)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String testGetAccountChildAccountsPage_getAccountKey()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String testGetAccountChildAccountsPage_getIrrelevantAccountKey()
		throws Exception {

		return null;
	}

	@Test
	public void testPostAccountChildAccount() throws Exception {
		Account randomAccount = randomAccount();

		Account postAccount = testPostAccountChildAccount_addAccount(
			randomAccount);

		assertEquals(randomAccount, postAccount);
		assertValid(postAccount);
	}

	protected Account testPostAccountChildAccount_addAccount(Account account)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testDeleteAccountContact() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testPutAccountContact() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testDeleteAccountContactContactUuidRole() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testPutAccountContactContactUuidRole() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testDeleteAccountTeamTeamKeyRole() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testPutAccountTeamTeamKeyRole() throws Exception {
		Assert.assertTrue(true);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(Account account1, Account account2) {
		Assert.assertTrue(
			account1 + " does not equal " + account2,
			equals(account1, account2));
	}

	protected void assertEquals(
		List<Account> accounts1, List<Account> accounts2) {

		Assert.assertEquals(accounts1.size(), accounts2.size());

		for (int i = 0; i < accounts1.size(); i++) {
			Account account1 = accounts1.get(i);
			Account account2 = accounts2.get(i);

			assertEquals(account1, account2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<Account> accounts1, List<Account> accounts2) {

		Assert.assertEquals(accounts1.size(), accounts2.size());

		for (Account account1 : accounts1) {
			boolean contains = false;

			for (Account account2 : accounts2) {
				if (equals(account1, account2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				accounts2 + " does not contain " + account1, contains);
		}
	}

	protected void assertValid(Account account) {
		boolean valid = true;

		if (account.getDateCreated() == null) {
			valid = false;
		}

		if (account.getDateModified() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("addresses", additionalAssertFieldName)) {
				if (account.getAddresses() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("code", additionalAssertFieldName)) {
				if (account.getCode() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"contactEmailAddress", additionalAssertFieldName)) {

				if (account.getContactEmailAddress() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (account.getDescription() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("externalLinks", additionalAssertFieldName)) {
				if (account.getExternalLinks() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("faxNumber", additionalAssertFieldName)) {
				if (account.getFaxNumber() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("industry", additionalAssertFieldName)) {
				if (account.getIndustry() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("key", additionalAssertFieldName)) {
				if (account.getKey() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("logoId", additionalAssertFieldName)) {
				if (account.getLogoId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (account.getName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("notes", additionalAssertFieldName)) {
				if (account.getNotes() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("parentAccountKey", additionalAssertFieldName)) {
				if (account.getParentAccountKey() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("phoneNumber", additionalAssertFieldName)) {
				if (account.getPhoneNumber() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("productPurchases", additionalAssertFieldName)) {
				if (account.getProductPurchases() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"profileEmailAddress", additionalAssertFieldName)) {

				if (account.getProfileEmailAddress() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("soldBy", additionalAssertFieldName)) {
				if (account.getSoldBy() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("status", additionalAssertFieldName)) {
				if (account.getStatus() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("tier", additionalAssertFieldName)) {
				if (account.getTier() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("website", additionalAssertFieldName)) {
				if (account.getWebsite() == null) {
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

	protected void assertValid(Page<Account> page) {
		boolean valid = false;

		java.util.Collection<Account> accounts = page.getItems();

		int size = accounts.size();

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

	protected boolean equals(Account account1, Account account2) {
		if (account1 == account2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("addresses", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						account1.getAddresses(), account2.getAddresses())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("code", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						account1.getCode(), account2.getCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"contactEmailAddress", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						account1.getContactEmailAddress(),
						account2.getContactEmailAddress())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateCreated", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						account1.getDateCreated(), account2.getDateCreated())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateModified", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						account1.getDateModified(),
						account2.getDateModified())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						account1.getDescription(), account2.getDescription())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("externalLinks", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						account1.getExternalLinks(),
						account2.getExternalLinks())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("faxNumber", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						account1.getFaxNumber(), account2.getFaxNumber())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("industry", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						account1.getIndustry(), account2.getIndustry())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("key", additionalAssertFieldName)) {
				if (!Objects.deepEquals(account1.getKey(), account2.getKey())) {
					return false;
				}

				continue;
			}

			if (Objects.equals("logoId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						account1.getLogoId(), account2.getLogoId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						account1.getName(), account2.getName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("notes", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						account1.getNotes(), account2.getNotes())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("parentAccountKey", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						account1.getParentAccountKey(),
						account2.getParentAccountKey())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("phoneNumber", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						account1.getPhoneNumber(), account2.getPhoneNumber())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("productPurchases", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						account1.getProductPurchases(),
						account2.getProductPurchases())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"profileEmailAddress", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						account1.getProfileEmailAddress(),
						account2.getProfileEmailAddress())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("soldBy", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						account1.getSoldBy(), account2.getSoldBy())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("status", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						account1.getStatus(), account2.getStatus())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("tier", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						account1.getTier(), account2.getTier())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("website", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						account1.getWebsite(), account2.getWebsite())) {

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

		if (!(_accountResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_accountResource;

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
		EntityField entityField, String operator, Account account) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("addresses")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("code")) {
			sb.append("'");
			sb.append(String.valueOf(account.getCode()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("contactEmailAddress")) {
			sb.append("'");
			sb.append(String.valueOf(account.getContactEmailAddress()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("dateCreated")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(account.getDateCreated(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(account.getDateCreated(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(account.getDateCreated()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("dateModified")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(account.getDateModified(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(account.getDateModified(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(account.getDateModified()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("description")) {
			sb.append("'");
			sb.append(String.valueOf(account.getDescription()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("externalLinks")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("faxNumber")) {
			sb.append("'");
			sb.append(String.valueOf(account.getFaxNumber()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("industry")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("key")) {
			sb.append("'");
			sb.append(String.valueOf(account.getKey()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("logoId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("name")) {
			sb.append("'");
			sb.append(String.valueOf(account.getName()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("notes")) {
			sb.append("'");
			sb.append(String.valueOf(account.getNotes()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("parentAccountKey")) {
			sb.append("'");
			sb.append(String.valueOf(account.getParentAccountKey()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("phoneNumber")) {
			sb.append("'");
			sb.append(String.valueOf(account.getPhoneNumber()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("productPurchases")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("profileEmailAddress")) {
			sb.append("'");
			sb.append(String.valueOf(account.getProfileEmailAddress()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("soldBy")) {
			sb.append("'");
			sb.append(String.valueOf(account.getSoldBy()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("status")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("tier")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("website")) {
			sb.append("'");
			sb.append(String.valueOf(account.getWebsite()));
			sb.append("'");

			return sb.toString();
		}

		throw new IllegalArgumentException(
			"Invalid entity field " + entityFieldName);
	}

	protected Account randomAccount() throws Exception {
		return new Account() {
			{
				code = RandomTestUtil.randomString();
				contactEmailAddress = RandomTestUtil.randomString();
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				description = RandomTestUtil.randomString();
				faxNumber = RandomTestUtil.randomString();
				key = RandomTestUtil.randomString();
				logoId = RandomTestUtil.randomLong();
				name = RandomTestUtil.randomString();
				notes = RandomTestUtil.randomString();
				parentAccountKey = RandomTestUtil.randomString();
				phoneNumber = RandomTestUtil.randomString();
				profileEmailAddress = RandomTestUtil.randomString();
				soldBy = RandomTestUtil.randomString();
				website = RandomTestUtil.randomString();
			}
		};
	}

	protected Account randomIrrelevantAccount() throws Exception {
		Account randomIrrelevantAccount = randomAccount();

		return randomIrrelevantAccount;
	}

	protected Account randomPatchAccount() throws Exception {
		return randomAccount();
	}

	protected AccountResource accountResource;
	protected Group irrelevantGroup;
	protected Company testCompany;
	protected Group testGroup;

	private static final Log _log = LogFactoryUtil.getLog(
		BaseAccountResourceTestCase.class);

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
	private com.liferay.osb.koroneiki.phloem.rest.resource.v1_0.AccountResource
		_accountResource;

}