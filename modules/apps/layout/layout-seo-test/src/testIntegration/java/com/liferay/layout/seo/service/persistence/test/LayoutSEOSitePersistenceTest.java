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

package com.liferay.layout.seo.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.seo.exception.NoSuchSiteException;
import com.liferay.layout.seo.model.LayoutSEOSite;
import com.liferay.layout.seo.service.LayoutSEOSiteLocalServiceUtil;
import com.liferay.layout.seo.service.persistence.LayoutSEOSitePersistence;
import com.liferay.layout.seo.service.persistence.LayoutSEOSiteUtil;
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
public class LayoutSEOSitePersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.layout.seo.service"));

	@Before
	public void setUp() {
		_persistence = LayoutSEOSiteUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<LayoutSEOSite> iterator = _layoutSEOSites.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LayoutSEOSite layoutSEOSite = _persistence.create(pk);

		Assert.assertNotNull(layoutSEOSite);

		Assert.assertEquals(layoutSEOSite.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		LayoutSEOSite newLayoutSEOSite = addLayoutSEOSite();

		_persistence.remove(newLayoutSEOSite);

		LayoutSEOSite existingLayoutSEOSite = _persistence.fetchByPrimaryKey(
			newLayoutSEOSite.getPrimaryKey());

		Assert.assertNull(existingLayoutSEOSite);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addLayoutSEOSite();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LayoutSEOSite newLayoutSEOSite = _persistence.create(pk);

		newLayoutSEOSite.setMvccVersion(RandomTestUtil.nextLong());

		newLayoutSEOSite.setUuid(RandomTestUtil.randomString());

		newLayoutSEOSite.setGroupId(RandomTestUtil.nextLong());

		newLayoutSEOSite.setCompanyId(RandomTestUtil.nextLong());

		newLayoutSEOSite.setUserId(RandomTestUtil.nextLong());

		newLayoutSEOSite.setUserName(RandomTestUtil.randomString());

		newLayoutSEOSite.setCreateDate(RandomTestUtil.nextDate());

		newLayoutSEOSite.setModifiedDate(RandomTestUtil.nextDate());

		newLayoutSEOSite.setOpenGraphEnabled(RandomTestUtil.randomBoolean());

		newLayoutSEOSite.setOpenGraphImageAlt(RandomTestUtil.randomString());

		newLayoutSEOSite.setOpenGraphImageFileEntryId(
			RandomTestUtil.nextLong());

		_layoutSEOSites.add(_persistence.update(newLayoutSEOSite));

		LayoutSEOSite existingLayoutSEOSite = _persistence.findByPrimaryKey(
			newLayoutSEOSite.getPrimaryKey());

		Assert.assertEquals(
			existingLayoutSEOSite.getMvccVersion(),
			newLayoutSEOSite.getMvccVersion());
		Assert.assertEquals(
			existingLayoutSEOSite.getUuid(), newLayoutSEOSite.getUuid());
		Assert.assertEquals(
			existingLayoutSEOSite.getLayoutSEOSiteId(),
			newLayoutSEOSite.getLayoutSEOSiteId());
		Assert.assertEquals(
			existingLayoutSEOSite.getGroupId(), newLayoutSEOSite.getGroupId());
		Assert.assertEquals(
			existingLayoutSEOSite.getCompanyId(),
			newLayoutSEOSite.getCompanyId());
		Assert.assertEquals(
			existingLayoutSEOSite.getUserId(), newLayoutSEOSite.getUserId());
		Assert.assertEquals(
			existingLayoutSEOSite.getUserName(),
			newLayoutSEOSite.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(existingLayoutSEOSite.getCreateDate()),
			Time.getShortTimestamp(newLayoutSEOSite.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(existingLayoutSEOSite.getModifiedDate()),
			Time.getShortTimestamp(newLayoutSEOSite.getModifiedDate()));
		Assert.assertEquals(
			existingLayoutSEOSite.isOpenGraphEnabled(),
			newLayoutSEOSite.isOpenGraphEnabled());
		Assert.assertEquals(
			existingLayoutSEOSite.getOpenGraphImageAlt(),
			newLayoutSEOSite.getOpenGraphImageAlt());
		Assert.assertEquals(
			existingLayoutSEOSite.getOpenGraphImageFileEntryId(),
			newLayoutSEOSite.getOpenGraphImageFileEntryId());
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
	public void testCountByGroupId() throws Exception {
		_persistence.countByGroupId(RandomTestUtil.nextLong());

		_persistence.countByGroupId(0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		LayoutSEOSite newLayoutSEOSite = addLayoutSEOSite();

		LayoutSEOSite existingLayoutSEOSite = _persistence.findByPrimaryKey(
			newLayoutSEOSite.getPrimaryKey());

		Assert.assertEquals(existingLayoutSEOSite, newLayoutSEOSite);
	}

	@Test(expected = NoSuchSiteException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<LayoutSEOSite> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"LayoutSEOSite", "mvccVersion", true, "uuid", true,
			"layoutSEOSiteId", true, "groupId", true, "companyId", true,
			"userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "openGraphEnabled", true, "openGraphImageAlt",
			true, "openGraphImageFileEntryId", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		LayoutSEOSite newLayoutSEOSite = addLayoutSEOSite();

		LayoutSEOSite existingLayoutSEOSite = _persistence.fetchByPrimaryKey(
			newLayoutSEOSite.getPrimaryKey());

		Assert.assertEquals(existingLayoutSEOSite, newLayoutSEOSite);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LayoutSEOSite missingLayoutSEOSite = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingLayoutSEOSite);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		LayoutSEOSite newLayoutSEOSite1 = addLayoutSEOSite();
		LayoutSEOSite newLayoutSEOSite2 = addLayoutSEOSite();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLayoutSEOSite1.getPrimaryKey());
		primaryKeys.add(newLayoutSEOSite2.getPrimaryKey());

		Map<Serializable, LayoutSEOSite> layoutSEOSites =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, layoutSEOSites.size());
		Assert.assertEquals(
			newLayoutSEOSite1,
			layoutSEOSites.get(newLayoutSEOSite1.getPrimaryKey()));
		Assert.assertEquals(
			newLayoutSEOSite2,
			layoutSEOSites.get(newLayoutSEOSite2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, LayoutSEOSite> layoutSEOSites =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(layoutSEOSites.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		LayoutSEOSite newLayoutSEOSite = addLayoutSEOSite();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLayoutSEOSite.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, LayoutSEOSite> layoutSEOSites =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, layoutSEOSites.size());
		Assert.assertEquals(
			newLayoutSEOSite,
			layoutSEOSites.get(newLayoutSEOSite.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, LayoutSEOSite> layoutSEOSites =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(layoutSEOSites.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		LayoutSEOSite newLayoutSEOSite = addLayoutSEOSite();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newLayoutSEOSite.getPrimaryKey());

		Map<Serializable, LayoutSEOSite> layoutSEOSites =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, layoutSEOSites.size());
		Assert.assertEquals(
			newLayoutSEOSite,
			layoutSEOSites.get(newLayoutSEOSite.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			LayoutSEOSiteLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<LayoutSEOSite>() {

				@Override
				public void performAction(LayoutSEOSite layoutSEOSite) {
					Assert.assertNotNull(layoutSEOSite);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		LayoutSEOSite newLayoutSEOSite = addLayoutSEOSite();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			LayoutSEOSite.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"layoutSEOSiteId", newLayoutSEOSite.getLayoutSEOSiteId()));

		List<LayoutSEOSite> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		LayoutSEOSite existingLayoutSEOSite = result.get(0);

		Assert.assertEquals(existingLayoutSEOSite, newLayoutSEOSite);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			LayoutSEOSite.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"layoutSEOSiteId", RandomTestUtil.nextLong()));

		List<LayoutSEOSite> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		LayoutSEOSite newLayoutSEOSite = addLayoutSEOSite();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			LayoutSEOSite.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("layoutSEOSiteId"));

		Object newLayoutSEOSiteId = newLayoutSEOSite.getLayoutSEOSiteId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"layoutSEOSiteId", new Object[] {newLayoutSEOSiteId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingLayoutSEOSiteId = result.get(0);

		Assert.assertEquals(existingLayoutSEOSiteId, newLayoutSEOSiteId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			LayoutSEOSite.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("layoutSEOSiteId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"layoutSEOSiteId", new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		LayoutSEOSite newLayoutSEOSite = addLayoutSEOSite();

		_persistence.clearCache();

		_assertOriginalValues(
			_persistence.findByPrimaryKey(newLayoutSEOSite.getPrimaryKey()));
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

		LayoutSEOSite newLayoutSEOSite = addLayoutSEOSite();

		if (clearSession) {
			Session session = _persistence.openSession();

			session.flush();

			session.clear();
		}

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			LayoutSEOSite.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"layoutSEOSiteId", newLayoutSEOSite.getLayoutSEOSiteId()));

		List<LayoutSEOSite> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		_assertOriginalValues(result.get(0));
	}

	private void _assertOriginalValues(LayoutSEOSite layoutSEOSite) {
		Assert.assertEquals(
			layoutSEOSite.getUuid(),
			ReflectionTestUtil.invoke(
				layoutSEOSite, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "uuid_"));
		Assert.assertEquals(
			Long.valueOf(layoutSEOSite.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				layoutSEOSite, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "groupId"));

		Assert.assertEquals(
			Long.valueOf(layoutSEOSite.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				layoutSEOSite, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "groupId"));
	}

	protected LayoutSEOSite addLayoutSEOSite() throws Exception {
		long pk = RandomTestUtil.nextLong();

		LayoutSEOSite layoutSEOSite = _persistence.create(pk);

		layoutSEOSite.setMvccVersion(RandomTestUtil.nextLong());

		layoutSEOSite.setUuid(RandomTestUtil.randomString());

		layoutSEOSite.setGroupId(RandomTestUtil.nextLong());

		layoutSEOSite.setCompanyId(RandomTestUtil.nextLong());

		layoutSEOSite.setUserId(RandomTestUtil.nextLong());

		layoutSEOSite.setUserName(RandomTestUtil.randomString());

		layoutSEOSite.setCreateDate(RandomTestUtil.nextDate());

		layoutSEOSite.setModifiedDate(RandomTestUtil.nextDate());

		layoutSEOSite.setOpenGraphEnabled(RandomTestUtil.randomBoolean());

		layoutSEOSite.setOpenGraphImageAlt(RandomTestUtil.randomString());

		layoutSEOSite.setOpenGraphImageFileEntryId(RandomTestUtil.nextLong());

		_layoutSEOSites.add(_persistence.update(layoutSEOSite));

		return layoutSEOSite;
	}

	private List<LayoutSEOSite> _layoutSEOSites =
		new ArrayList<LayoutSEOSite>();
	private LayoutSEOSitePersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}