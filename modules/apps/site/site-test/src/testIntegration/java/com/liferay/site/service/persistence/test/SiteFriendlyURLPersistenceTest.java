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

package com.liferay.site.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;
import com.liferay.site.exception.NoSuchFriendlyURLException;
import com.liferay.site.model.SiteFriendlyURL;
import com.liferay.site.service.SiteFriendlyURLLocalServiceUtil;
import com.liferay.site.service.persistence.SiteFriendlyURLPersistence;
import com.liferay.site.service.persistence.SiteFriendlyURLUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @generated
 */
@RunWith(Arquillian.class)
public class SiteFriendlyURLPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.site.service"));

	@Before
	public void setUp() {
		_persistence = SiteFriendlyURLUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<SiteFriendlyURL> iterator = _siteFriendlyURLs.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SiteFriendlyURL siteFriendlyURL = _persistence.create(pk);

		Assert.assertNotNull(siteFriendlyURL);

		Assert.assertEquals(siteFriendlyURL.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		SiteFriendlyURL newSiteFriendlyURL = addSiteFriendlyURL();

		_persistence.remove(newSiteFriendlyURL);

		SiteFriendlyURL existingSiteFriendlyURL =
			_persistence.fetchByPrimaryKey(newSiteFriendlyURL.getPrimaryKey());

		Assert.assertNull(existingSiteFriendlyURL);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addSiteFriendlyURL();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SiteFriendlyURL newSiteFriendlyURL = _persistence.create(pk);

		newSiteFriendlyURL.setMvccVersion(RandomTestUtil.nextLong());

		newSiteFriendlyURL.setUuid(RandomTestUtil.randomString());

		newSiteFriendlyURL.setCompanyId(RandomTestUtil.nextLong());

		newSiteFriendlyURL.setUserId(RandomTestUtil.nextLong());

		newSiteFriendlyURL.setUserName(RandomTestUtil.randomString());

		newSiteFriendlyURL.setCreateDate(RandomTestUtil.nextDate());

		newSiteFriendlyURL.setModifiedDate(RandomTestUtil.nextDate());

		newSiteFriendlyURL.setGroupId(RandomTestUtil.nextLong());

		newSiteFriendlyURL.setFriendlyURL(RandomTestUtil.randomString());

		newSiteFriendlyURL.setLanguageId(RandomTestUtil.randomString());

		newSiteFriendlyURL.setLastPublishDate(RandomTestUtil.nextDate());

		_siteFriendlyURLs.add(_persistence.update(newSiteFriendlyURL));

		SiteFriendlyURL existingSiteFriendlyURL = _persistence.findByPrimaryKey(
			newSiteFriendlyURL.getPrimaryKey());

		Assert.assertEquals(
			existingSiteFriendlyURL.getMvccVersion(),
			newSiteFriendlyURL.getMvccVersion());
		Assert.assertEquals(
			existingSiteFriendlyURL.getUuid(), newSiteFriendlyURL.getUuid());
		Assert.assertEquals(
			existingSiteFriendlyURL.getSiteFriendlyURLId(),
			newSiteFriendlyURL.getSiteFriendlyURLId());
		Assert.assertEquals(
			existingSiteFriendlyURL.getCompanyId(),
			newSiteFriendlyURL.getCompanyId());
		Assert.assertEquals(
			existingSiteFriendlyURL.getUserId(),
			newSiteFriendlyURL.getUserId());
		Assert.assertEquals(
			existingSiteFriendlyURL.getUserName(),
			newSiteFriendlyURL.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(existingSiteFriendlyURL.getCreateDate()),
			Time.getShortTimestamp(newSiteFriendlyURL.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(existingSiteFriendlyURL.getModifiedDate()),
			Time.getShortTimestamp(newSiteFriendlyURL.getModifiedDate()));
		Assert.assertEquals(
			existingSiteFriendlyURL.getGroupId(),
			newSiteFriendlyURL.getGroupId());
		Assert.assertEquals(
			existingSiteFriendlyURL.getFriendlyURL(),
			newSiteFriendlyURL.getFriendlyURL());
		Assert.assertEquals(
			existingSiteFriendlyURL.getLanguageId(),
			newSiteFriendlyURL.getLanguageId());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingSiteFriendlyURL.getLastPublishDate()),
			Time.getShortTimestamp(newSiteFriendlyURL.getLastPublishDate()));
	}

	@Test
	public void testCountByUuid() throws Exception {
		_persistence.countByUuid("");

		_persistence.countByUuid("null");

		_persistence.countByUuid((String)null);
	}

	@Test
	public void testCountByUUID_G() throws Exception {
		_persistence.countByUUID_G("", RandomTestUtil.nextLong());

		_persistence.countByUUID_G("null", 0L);

		_persistence.countByUUID_G((String)null, 0L);
	}

	@Test
	public void testCountByUuid_C() throws Exception {
		_persistence.countByUuid_C("", RandomTestUtil.nextLong());

		_persistence.countByUuid_C("null", 0L);

		_persistence.countByUuid_C((String)null, 0L);
	}

	@Test
	public void testCountByC_G() throws Exception {
		_persistence.countByC_G(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		_persistence.countByC_G(0L, 0L);
	}

	@Test
	public void testCountByC_F() throws Exception {
		_persistence.countByC_F(RandomTestUtil.nextLong(), "");

		_persistence.countByC_F(0L, "null");

		_persistence.countByC_F(0L, (String)null);
	}

	@Test
	public void testCountByC_G_L() throws Exception {
		_persistence.countByC_G_L(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(), "");

		_persistence.countByC_G_L(0L, 0L, "null");

		_persistence.countByC_G_L(0L, 0L, (String)null);
	}

	@Test
	public void testCountByC_F_L() throws Exception {
		_persistence.countByC_F_L(RandomTestUtil.nextLong(), "", "");

		_persistence.countByC_F_L(0L, "null", "null");

		_persistence.countByC_F_L(0L, (String)null, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		SiteFriendlyURL newSiteFriendlyURL = addSiteFriendlyURL();

		SiteFriendlyURL existingSiteFriendlyURL = _persistence.findByPrimaryKey(
			newSiteFriendlyURL.getPrimaryKey());

		Assert.assertEquals(existingSiteFriendlyURL, newSiteFriendlyURL);
	}

	@Test(expected = NoSuchFriendlyURLException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<SiteFriendlyURL> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"SiteFriendlyURL", "mvccVersion", true, "uuid", true,
			"siteFriendlyURLId", true, "companyId", true, "userId", true,
			"userName", true, "createDate", true, "modifiedDate", true,
			"groupId", true, "friendlyURL", true, "languageId", true,
			"lastPublishDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		SiteFriendlyURL newSiteFriendlyURL = addSiteFriendlyURL();

		SiteFriendlyURL existingSiteFriendlyURL =
			_persistence.fetchByPrimaryKey(newSiteFriendlyURL.getPrimaryKey());

		Assert.assertEquals(existingSiteFriendlyURL, newSiteFriendlyURL);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SiteFriendlyURL missingSiteFriendlyURL = _persistence.fetchByPrimaryKey(
			pk);

		Assert.assertNull(missingSiteFriendlyURL);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		SiteFriendlyURL newSiteFriendlyURL1 = addSiteFriendlyURL();
		SiteFriendlyURL newSiteFriendlyURL2 = addSiteFriendlyURL();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newSiteFriendlyURL1.getPrimaryKey());
		primaryKeys.add(newSiteFriendlyURL2.getPrimaryKey());

		Map<Serializable, SiteFriendlyURL> siteFriendlyURLs =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, siteFriendlyURLs.size());
		Assert.assertEquals(
			newSiteFriendlyURL1,
			siteFriendlyURLs.get(newSiteFriendlyURL1.getPrimaryKey()));
		Assert.assertEquals(
			newSiteFriendlyURL2,
			siteFriendlyURLs.get(newSiteFriendlyURL2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, SiteFriendlyURL> siteFriendlyURLs =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(siteFriendlyURLs.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		SiteFriendlyURL newSiteFriendlyURL = addSiteFriendlyURL();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newSiteFriendlyURL.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, SiteFriendlyURL> siteFriendlyURLs =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, siteFriendlyURLs.size());
		Assert.assertEquals(
			newSiteFriendlyURL,
			siteFriendlyURLs.get(newSiteFriendlyURL.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, SiteFriendlyURL> siteFriendlyURLs =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(siteFriendlyURLs.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		SiteFriendlyURL newSiteFriendlyURL = addSiteFriendlyURL();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newSiteFriendlyURL.getPrimaryKey());

		Map<Serializable, SiteFriendlyURL> siteFriendlyURLs =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, siteFriendlyURLs.size());
		Assert.assertEquals(
			newSiteFriendlyURL,
			siteFriendlyURLs.get(newSiteFriendlyURL.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			SiteFriendlyURLLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<SiteFriendlyURL>() {

				@Override
				public void performAction(SiteFriendlyURL siteFriendlyURL) {
					Assert.assertNotNull(siteFriendlyURL);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		SiteFriendlyURL newSiteFriendlyURL = addSiteFriendlyURL();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			SiteFriendlyURL.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"siteFriendlyURLId",
				newSiteFriendlyURL.getSiteFriendlyURLId()));

		List<SiteFriendlyURL> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		SiteFriendlyURL existingSiteFriendlyURL = result.get(0);

		Assert.assertEquals(existingSiteFriendlyURL, newSiteFriendlyURL);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			SiteFriendlyURL.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"siteFriendlyURLId", RandomTestUtil.nextLong()));

		List<SiteFriendlyURL> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		SiteFriendlyURL newSiteFriendlyURL = addSiteFriendlyURL();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			SiteFriendlyURL.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("siteFriendlyURLId"));

		Object newSiteFriendlyURLId = newSiteFriendlyURL.getSiteFriendlyURLId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"siteFriendlyURLId", new Object[] {newSiteFriendlyURLId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingSiteFriendlyURLId = result.get(0);

		Assert.assertEquals(existingSiteFriendlyURLId, newSiteFriendlyURLId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			SiteFriendlyURL.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("siteFriendlyURLId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"siteFriendlyURLId", new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		SiteFriendlyURL newSiteFriendlyURL = addSiteFriendlyURL();

		_persistence.clearCache();

		_assertOriginalValues(
			_persistence.findByPrimaryKey(newSiteFriendlyURL.getPrimaryKey()));
	}

	@Test
	public void testResetOriginalValuesWithDynamicQueryLoadFromDatabase()
		throws Exception {

		_testResetOriginalValuesWithDynamicQuery(true);
	}

	@Test
	public void testResetOriginalValuesWithDynamicQueryLoadFromSession()
		throws Exception {

		_testResetOriginalValuesWithDynamicQuery(false);
	}

	private void _testResetOriginalValuesWithDynamicQuery(boolean clearSession)
		throws Exception {

		SiteFriendlyURL newSiteFriendlyURL = addSiteFriendlyURL();

		if (clearSession) {
			Session session = _persistence.openSession();

			session.flush();

			session.clear();
		}

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			SiteFriendlyURL.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"siteFriendlyURLId",
				newSiteFriendlyURL.getSiteFriendlyURLId()));

		List<SiteFriendlyURL> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		_assertOriginalValues(result.get(0));
	}

	private void _assertOriginalValues(SiteFriendlyURL siteFriendlyURL) {
		Assert.assertEquals(
			siteFriendlyURL.getUuid(),
			ReflectionTestUtil.invoke(
				siteFriendlyURL, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "uuid_"));
		Assert.assertEquals(
			Long.valueOf(siteFriendlyURL.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				siteFriendlyURL, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "groupId"));

		Assert.assertEquals(
			Long.valueOf(siteFriendlyURL.getCompanyId()),
			ReflectionTestUtil.<Long>invoke(
				siteFriendlyURL, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "companyId"));
		Assert.assertEquals(
			siteFriendlyURL.getFriendlyURL(),
			ReflectionTestUtil.invoke(
				siteFriendlyURL, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "friendlyURL"));

		Assert.assertEquals(
			Long.valueOf(siteFriendlyURL.getCompanyId()),
			ReflectionTestUtil.<Long>invoke(
				siteFriendlyURL, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "companyId"));
		Assert.assertEquals(
			Long.valueOf(siteFriendlyURL.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				siteFriendlyURL, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "groupId"));
		Assert.assertEquals(
			siteFriendlyURL.getLanguageId(),
			ReflectionTestUtil.invoke(
				siteFriendlyURL, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "languageId"));

		Assert.assertEquals(
			Long.valueOf(siteFriendlyURL.getCompanyId()),
			ReflectionTestUtil.<Long>invoke(
				siteFriendlyURL, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "companyId"));
		Assert.assertEquals(
			siteFriendlyURL.getFriendlyURL(),
			ReflectionTestUtil.invoke(
				siteFriendlyURL, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "friendlyURL"));
		Assert.assertEquals(
			siteFriendlyURL.getLanguageId(),
			ReflectionTestUtil.invoke(
				siteFriendlyURL, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "languageId"));
	}

	protected SiteFriendlyURL addSiteFriendlyURL() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SiteFriendlyURL siteFriendlyURL = _persistence.create(pk);

		siteFriendlyURL.setMvccVersion(RandomTestUtil.nextLong());

		siteFriendlyURL.setUuid(RandomTestUtil.randomString());

		siteFriendlyURL.setCompanyId(RandomTestUtil.nextLong());

		siteFriendlyURL.setUserId(RandomTestUtil.nextLong());

		siteFriendlyURL.setUserName(RandomTestUtil.randomString());

		siteFriendlyURL.setCreateDate(RandomTestUtil.nextDate());

		siteFriendlyURL.setModifiedDate(RandomTestUtil.nextDate());

		siteFriendlyURL.setGroupId(RandomTestUtil.nextLong());

		siteFriendlyURL.setFriendlyURL(RandomTestUtil.randomString());

		siteFriendlyURL.setLanguageId(RandomTestUtil.randomString());

		siteFriendlyURL.setLastPublishDate(RandomTestUtil.nextDate());

		_siteFriendlyURLs.add(_persistence.update(siteFriendlyURL));

		return siteFriendlyURL;
	}

	private List<SiteFriendlyURL> _siteFriendlyURLs =
		new ArrayList<SiteFriendlyURL>();
	private SiteFriendlyURLPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}