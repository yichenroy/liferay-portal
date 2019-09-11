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

import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Team;
import com.liferay.osb.koroneiki.phloem.rest.client.http.HttpInvoker;
import com.liferay.osb.koroneiki.phloem.rest.client.pagination.Page;
import com.liferay.osb.koroneiki.phloem.rest.client.pagination.Pagination;
import com.liferay.osb.koroneiki.phloem.rest.client.resource.v1_0.TeamResource;
import com.liferay.osb.koroneiki.phloem.rest.client.serdes.v1_0.TeamSerDes;
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
public abstract class BaseTeamResourceTestCase {

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

		_teamResource.setContextCompany(testCompany);

		TeamResource.Builder builder = TeamResource.builder();

		teamResource = builder.locale(
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

		Team team1 = randomTeam();

		String json = objectMapper.writeValueAsString(team1);

		Team team2 = TeamSerDes.toDTO(json);

		Assert.assertTrue(equals(team1, team2));
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

		Team team = randomTeam();

		String json1 = objectMapper.writeValueAsString(team);
		String json2 = TeamSerDes.toJSON(team);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		Team team = randomTeam();

		team.setAccountKey(regex);
		team.setKey(regex);
		team.setName(regex);

		String json = TeamSerDes.toJSON(team);

		Assert.assertFalse(json.contains(regex));

		team = TeamSerDes.toDTO(json);

		Assert.assertEquals(regex, team.getAccountKey());
		Assert.assertEquals(regex, team.getKey());
		Assert.assertEquals(regex, team.getName());
	}

	@Test
	public void testGetAccountAccountKeyTeamsPage() throws Exception {
		Page<Team> page = teamResource.getAccountAccountKeyTeamsPage(
			testGetAccountAccountKeyTeamsPage_getAccountKey(),
			Pagination.of(1, 2));

		Assert.assertEquals(0, page.getTotalCount());

		String accountKey = testGetAccountAccountKeyTeamsPage_getAccountKey();
		String irrelevantAccountKey =
			testGetAccountAccountKeyTeamsPage_getIrrelevantAccountKey();

		if ((irrelevantAccountKey != null)) {
			Team irrelevantTeam = testGetAccountAccountKeyTeamsPage_addTeam(
				irrelevantAccountKey, randomIrrelevantTeam());

			page = teamResource.getAccountAccountKeyTeamsPage(
				irrelevantAccountKey, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantTeam), (List<Team>)page.getItems());
			assertValid(page);
		}

		Team team1 = testGetAccountAccountKeyTeamsPage_addTeam(
			accountKey, randomTeam());

		Team team2 = testGetAccountAccountKeyTeamsPage_addTeam(
			accountKey, randomTeam());

		page = teamResource.getAccountAccountKeyTeamsPage(
			accountKey, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(team1, team2), (List<Team>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetAccountAccountKeyTeamsPageWithPagination()
		throws Exception {

		String accountKey = testGetAccountAccountKeyTeamsPage_getAccountKey();

		Team team1 = testGetAccountAccountKeyTeamsPage_addTeam(
			accountKey, randomTeam());

		Team team2 = testGetAccountAccountKeyTeamsPage_addTeam(
			accountKey, randomTeam());

		Team team3 = testGetAccountAccountKeyTeamsPage_addTeam(
			accountKey, randomTeam());

		Page<Team> page1 = teamResource.getAccountAccountKeyTeamsPage(
			accountKey, Pagination.of(1, 2));

		List<Team> teams1 = (List<Team>)page1.getItems();

		Assert.assertEquals(teams1.toString(), 2, teams1.size());

		Page<Team> page2 = teamResource.getAccountAccountKeyTeamsPage(
			accountKey, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<Team> teams2 = (List<Team>)page2.getItems();

		Assert.assertEquals(teams2.toString(), 1, teams2.size());

		Page<Team> page3 = teamResource.getAccountAccountKeyTeamsPage(
			accountKey, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(team1, team2, team3), (List<Team>)page3.getItems());
	}

	protected Team testGetAccountAccountKeyTeamsPage_addTeam(
			String accountKey, Team team)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String testGetAccountAccountKeyTeamsPage_getAccountKey()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String testGetAccountAccountKeyTeamsPage_getIrrelevantAccountKey()
		throws Exception {

		return null;
	}

	@Test
	public void testPostAccountAccountKeyTeam() throws Exception {
		Team randomTeam = randomTeam();

		Team postTeam = testPostAccountAccountKeyTeam_addTeam(randomTeam);

		assertEquals(randomTeam, postTeam);
		assertValid(postTeam);
	}

	protected Team testPostAccountAccountKeyTeam_addTeam(Team team)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetTeamsPage() throws Exception {
		Page<Team> page = teamResource.getTeamsPage(
			RandomTestUtil.randomString(), null, Pagination.of(1, 2), null);

		Assert.assertEquals(0, page.getTotalCount());

		Team team1 = testGetTeamsPage_addTeam(randomTeam());

		Team team2 = testGetTeamsPage_addTeam(randomTeam());

		page = teamResource.getTeamsPage(null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(team1, team2), (List<Team>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetTeamsPageWithFilterDateTimeEquals() throws Exception {
		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Team team1 = randomTeam();

		team1 = testGetTeamsPage_addTeam(team1);

		for (EntityField entityField : entityFields) {
			Page<Team> page = teamResource.getTeamsPage(
				null, getFilterString(entityField, "between", team1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(team1), (List<Team>)page.getItems());
		}
	}

	@Test
	public void testGetTeamsPageWithFilterStringEquals() throws Exception {
		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Team team1 = testGetTeamsPage_addTeam(randomTeam());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		Team team2 = testGetTeamsPage_addTeam(randomTeam());

		for (EntityField entityField : entityFields) {
			Page<Team> page = teamResource.getTeamsPage(
				null, getFilterString(entityField, "eq", team1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(team1), (List<Team>)page.getItems());
		}
	}

	@Test
	public void testGetTeamsPageWithPagination() throws Exception {
		Team team1 = testGetTeamsPage_addTeam(randomTeam());

		Team team2 = testGetTeamsPage_addTeam(randomTeam());

		Team team3 = testGetTeamsPage_addTeam(randomTeam());

		Page<Team> page1 = teamResource.getTeamsPage(
			null, null, Pagination.of(1, 2), null);

		List<Team> teams1 = (List<Team>)page1.getItems();

		Assert.assertEquals(teams1.toString(), 2, teams1.size());

		Page<Team> page2 = teamResource.getTeamsPage(
			null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<Team> teams2 = (List<Team>)page2.getItems();

		Assert.assertEquals(teams2.toString(), 1, teams2.size());

		Page<Team> page3 = teamResource.getTeamsPage(
			null, null, Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(team1, team2, team3), (List<Team>)page3.getItems());
	}

	@Test
	public void testGetTeamsPageWithSortDateTime() throws Exception {
		testGetTeamsPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, team1, team2) -> {
				BeanUtils.setProperty(
					team1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetTeamsPageWithSortInteger() throws Exception {
		testGetTeamsPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, team1, team2) -> {
				BeanUtils.setProperty(team1, entityField.getName(), 0);
				BeanUtils.setProperty(team2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetTeamsPageWithSortString() throws Exception {
		testGetTeamsPageWithSort(
			EntityField.Type.STRING,
			(entityField, team1, team2) -> {
				BeanUtils.setProperty(team1, entityField.getName(), "Aaa");
				BeanUtils.setProperty(team2, entityField.getName(), "Bbb");
			});
	}

	protected void testGetTeamsPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer<EntityField, Team, Team, Exception>
				unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Team team1 = randomTeam();
		Team team2 = randomTeam();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(entityField, team1, team2);
		}

		team1 = testGetTeamsPage_addTeam(team1);

		team2 = testGetTeamsPage_addTeam(team2);

		for (EntityField entityField : entityFields) {
			Page<Team> ascPage = teamResource.getTeamsPage(
				null, null, Pagination.of(1, 2),
				entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(team1, team2), (List<Team>)ascPage.getItems());

			Page<Team> descPage = teamResource.getTeamsPage(
				null, null, Pagination.of(1, 2),
				entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(team2, team1), (List<Team>)descPage.getItems());
		}
	}

	protected Team testGetTeamsPage_addTeam(Team team) throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetTeamByExternalLinkDomainEntityNameEntity()
		throws Exception {

		Page<Team> page =
			teamResource.getTeamByExternalLinkDomainEntityNameEntity(
				testGetTeamByExternalLinkDomainEntityNameEntity_getDomain(),
				testGetTeamByExternalLinkDomainEntityNameEntity_getEntityName(),
				testGetTeamByExternalLinkDomainEntityNameEntity_getEntityId(),
				Pagination.of(1, 2));

		Assert.assertEquals(0, page.getTotalCount());

		String domain =
			testGetTeamByExternalLinkDomainEntityNameEntity_getDomain();
		String irrelevantDomain =
			testGetTeamByExternalLinkDomainEntityNameEntity_getIrrelevantDomain();
		String entityName =
			testGetTeamByExternalLinkDomainEntityNameEntity_getEntityName();
		String irrelevantEntityName =
			testGetTeamByExternalLinkDomainEntityNameEntity_getIrrelevantEntityName();
		String entityId =
			testGetTeamByExternalLinkDomainEntityNameEntity_getEntityId();
		String irrelevantEntityId =
			testGetTeamByExternalLinkDomainEntityNameEntity_getIrrelevantEntityId();

		if ((irrelevantDomain != null) && (irrelevantEntityName != null) &&
			(irrelevantEntityId != null)) {

			Team irrelevantTeam =
				testGetTeamByExternalLinkDomainEntityNameEntity_addTeam(
					irrelevantDomain, irrelevantEntityName, irrelevantEntityId,
					randomIrrelevantTeam());

			page = teamResource.getTeamByExternalLinkDomainEntityNameEntity(
				irrelevantDomain, irrelevantEntityName, irrelevantEntityId,
				Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantTeam), (List<Team>)page.getItems());
			assertValid(page);
		}

		Team team1 = testGetTeamByExternalLinkDomainEntityNameEntity_addTeam(
			domain, entityName, entityId, randomTeam());

		Team team2 = testGetTeamByExternalLinkDomainEntityNameEntity_addTeam(
			domain, entityName, entityId, randomTeam());

		page = teamResource.getTeamByExternalLinkDomainEntityNameEntity(
			domain, entityName, entityId, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(team1, team2), (List<Team>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetTeamByExternalLinkDomainEntityNameEntityWithPagination()
		throws Exception {

		String domain =
			testGetTeamByExternalLinkDomainEntityNameEntity_getDomain();
		String entityName =
			testGetTeamByExternalLinkDomainEntityNameEntity_getEntityName();
		String entityId =
			testGetTeamByExternalLinkDomainEntityNameEntity_getEntityId();

		Team team1 = testGetTeamByExternalLinkDomainEntityNameEntity_addTeam(
			domain, entityName, entityId, randomTeam());

		Team team2 = testGetTeamByExternalLinkDomainEntityNameEntity_addTeam(
			domain, entityName, entityId, randomTeam());

		Team team3 = testGetTeamByExternalLinkDomainEntityNameEntity_addTeam(
			domain, entityName, entityId, randomTeam());

		Page<Team> page1 =
			teamResource.getTeamByExternalLinkDomainEntityNameEntity(
				domain, entityName, entityId, Pagination.of(1, 2));

		List<Team> teams1 = (List<Team>)page1.getItems();

		Assert.assertEquals(teams1.toString(), 2, teams1.size());

		Page<Team> page2 =
			teamResource.getTeamByExternalLinkDomainEntityNameEntity(
				domain, entityName, entityId, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<Team> teams2 = (List<Team>)page2.getItems();

		Assert.assertEquals(teams2.toString(), 1, teams2.size());

		Page<Team> page3 =
			teamResource.getTeamByExternalLinkDomainEntityNameEntity(
				domain, entityName, entityId, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(team1, team2, team3), (List<Team>)page3.getItems());
	}

	protected Team testGetTeamByExternalLinkDomainEntityNameEntity_addTeam(
			String domain, String entityName, String entityId, Team team)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String testGetTeamByExternalLinkDomainEntityNameEntity_getDomain()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetTeamByExternalLinkDomainEntityNameEntity_getIrrelevantDomain()
		throws Exception {

		return null;
	}

	protected String
			testGetTeamByExternalLinkDomainEntityNameEntity_getEntityName()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetTeamByExternalLinkDomainEntityNameEntity_getIrrelevantEntityName()
		throws Exception {

		return null;
	}

	protected String
			testGetTeamByExternalLinkDomainEntityNameEntity_getEntityId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetTeamByExternalLinkDomainEntityNameEntity_getIrrelevantEntityId()
		throws Exception {

		return null;
	}

	@Test
	public void testDeleteTeam() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testGetTeam() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testPutTeam() throws Exception {
		Assert.assertTrue(true);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(Team team1, Team team2) {
		Assert.assertTrue(
			team1 + " does not equal " + team2, equals(team1, team2));
	}

	protected void assertEquals(List<Team> teams1, List<Team> teams2) {
		Assert.assertEquals(teams1.size(), teams2.size());

		for (int i = 0; i < teams1.size(); i++) {
			Team team1 = teams1.get(i);
			Team team2 = teams2.get(i);

			assertEquals(team1, team2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<Team> teams1, List<Team> teams2) {

		Assert.assertEquals(teams1.size(), teams2.size());

		for (Team team1 : teams1) {
			boolean contains = false;

			for (Team team2 : teams2) {
				if (equals(team1, team2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(teams2 + " does not contain " + team1, contains);
		}
	}

	protected void assertValid(Team team) {
		boolean valid = true;

		if (team.getDateCreated() == null) {
			valid = false;
		}

		if (team.getDateModified() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("accountKey", additionalAssertFieldName)) {
				if (team.getAccountKey() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("externalLinks", additionalAssertFieldName)) {
				if (team.getExternalLinks() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("key", additionalAssertFieldName)) {
				if (team.getKey() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (team.getName() == null) {
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

	protected void assertValid(Page<Team> page) {
		boolean valid = false;

		java.util.Collection<Team> teams = page.getItems();

		int size = teams.size();

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

	protected boolean equals(Team team1, Team team2) {
		if (team1 == team2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("accountKey", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						team1.getAccountKey(), team2.getAccountKey())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateCreated", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						team1.getDateCreated(), team2.getDateCreated())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateModified", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						team1.getDateModified(), team2.getDateModified())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("externalLinks", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						team1.getExternalLinks(), team2.getExternalLinks())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("key", additionalAssertFieldName)) {
				if (!Objects.deepEquals(team1.getKey(), team2.getKey())) {
					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!Objects.deepEquals(team1.getName(), team2.getName())) {
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

		if (!(_teamResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_teamResource;

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
		EntityField entityField, String operator, Team team) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("accountKey")) {
			sb.append("'");
			sb.append(String.valueOf(team.getAccountKey()));
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
						DateUtils.addSeconds(team.getDateCreated(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(team.getDateCreated(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(team.getDateCreated()));
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
						DateUtils.addSeconds(team.getDateModified(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(team.getDateModified(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(team.getDateModified()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("externalLinks")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("key")) {
			sb.append("'");
			sb.append(String.valueOf(team.getKey()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("name")) {
			sb.append("'");
			sb.append(String.valueOf(team.getName()));
			sb.append("'");

			return sb.toString();
		}

		throw new IllegalArgumentException(
			"Invalid entity field " + entityFieldName);
	}

	protected Team randomTeam() throws Exception {
		return new Team() {
			{
				accountKey = RandomTestUtil.randomString();
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				key = RandomTestUtil.randomString();
				name = RandomTestUtil.randomString();
			}
		};
	}

	protected Team randomIrrelevantTeam() throws Exception {
		Team randomIrrelevantTeam = randomTeam();

		return randomIrrelevantTeam;
	}

	protected Team randomPatchTeam() throws Exception {
		return randomTeam();
	}

	protected TeamResource teamResource;
	protected Group irrelevantGroup;
	protected Company testCompany;
	protected Group testGroup;

	private static final Log _log = LogFactoryUtil.getLog(
		BaseTeamResourceTestCase.class);

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
	private com.liferay.osb.koroneiki.phloem.rest.resource.v1_0.TeamResource
		_teamResource;

}