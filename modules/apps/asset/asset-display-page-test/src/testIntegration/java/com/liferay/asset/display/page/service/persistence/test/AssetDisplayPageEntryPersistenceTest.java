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

package com.liferay.asset.display.page.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.display.page.exception.NoSuchDisplayPageEntryException;
import com.liferay.asset.display.page.model.AssetDisplayPageEntry;
import com.liferay.asset.display.page.service.AssetDisplayPageEntryLocalServiceUtil;
import com.liferay.asset.display.page.service.persistence.AssetDisplayPageEntryPersistence;
import com.liferay.asset.display.page.service.persistence.AssetDisplayPageEntryUtil;
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
public class AssetDisplayPageEntryPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED,
				"com.liferay.asset.display.page.service"));

	@Before
	public void setUp() {
		_persistence = AssetDisplayPageEntryUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<AssetDisplayPageEntry> iterator =
			_assetDisplayPageEntries.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AssetDisplayPageEntry assetDisplayPageEntry = _persistence.create(pk);

		Assert.assertNotNull(assetDisplayPageEntry);

		Assert.assertEquals(assetDisplayPageEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		AssetDisplayPageEntry newAssetDisplayPageEntry =
			addAssetDisplayPageEntry();

		_persistence.remove(newAssetDisplayPageEntry);

		AssetDisplayPageEntry existingAssetDisplayPageEntry =
			_persistence.fetchByPrimaryKey(
				newAssetDisplayPageEntry.getPrimaryKey());

		Assert.assertNull(existingAssetDisplayPageEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addAssetDisplayPageEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AssetDisplayPageEntry newAssetDisplayPageEntry = _persistence.create(
			pk);

		newAssetDisplayPageEntry.setMvccVersion(RandomTestUtil.nextLong());

		newAssetDisplayPageEntry.setCtCollectionId(RandomTestUtil.nextLong());

		newAssetDisplayPageEntry.setUuid(RandomTestUtil.randomString());

		newAssetDisplayPageEntry.setGroupId(RandomTestUtil.nextLong());

		newAssetDisplayPageEntry.setCompanyId(RandomTestUtil.nextLong());

		newAssetDisplayPageEntry.setUserId(RandomTestUtil.nextLong());

		newAssetDisplayPageEntry.setUserName(RandomTestUtil.randomString());

		newAssetDisplayPageEntry.setCreateDate(RandomTestUtil.nextDate());

		newAssetDisplayPageEntry.setModifiedDate(RandomTestUtil.nextDate());

		newAssetDisplayPageEntry.setClassNameId(RandomTestUtil.nextLong());

		newAssetDisplayPageEntry.setClassPK(RandomTestUtil.nextLong());

		newAssetDisplayPageEntry.setLayoutPageTemplateEntryId(
			RandomTestUtil.nextLong());

		newAssetDisplayPageEntry.setType(RandomTestUtil.nextInt());

		newAssetDisplayPageEntry.setPlid(RandomTestUtil.nextLong());

		_assetDisplayPageEntries.add(
			_persistence.update(newAssetDisplayPageEntry));

		AssetDisplayPageEntry existingAssetDisplayPageEntry =
			_persistence.findByPrimaryKey(
				newAssetDisplayPageEntry.getPrimaryKey());

		Assert.assertEquals(
			existingAssetDisplayPageEntry.getMvccVersion(),
			newAssetDisplayPageEntry.getMvccVersion());
		Assert.assertEquals(
			existingAssetDisplayPageEntry.getCtCollectionId(),
			newAssetDisplayPageEntry.getCtCollectionId());
		Assert.assertEquals(
			existingAssetDisplayPageEntry.getUuid(),
			newAssetDisplayPageEntry.getUuid());
		Assert.assertEquals(
			existingAssetDisplayPageEntry.getAssetDisplayPageEntryId(),
			newAssetDisplayPageEntry.getAssetDisplayPageEntryId());
		Assert.assertEquals(
			existingAssetDisplayPageEntry.getGroupId(),
			newAssetDisplayPageEntry.getGroupId());
		Assert.assertEquals(
			existingAssetDisplayPageEntry.getCompanyId(),
			newAssetDisplayPageEntry.getCompanyId());
		Assert.assertEquals(
			existingAssetDisplayPageEntry.getUserId(),
			newAssetDisplayPageEntry.getUserId());
		Assert.assertEquals(
			existingAssetDisplayPageEntry.getUserName(),
			newAssetDisplayPageEntry.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingAssetDisplayPageEntry.getCreateDate()),
			Time.getShortTimestamp(newAssetDisplayPageEntry.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingAssetDisplayPageEntry.getModifiedDate()),
			Time.getShortTimestamp(newAssetDisplayPageEntry.getModifiedDate()));
		Assert.assertEquals(
			existingAssetDisplayPageEntry.getClassNameId(),
			newAssetDisplayPageEntry.getClassNameId());
		Assert.assertEquals(
			existingAssetDisplayPageEntry.getClassPK(),
			newAssetDisplayPageEntry.getClassPK());
		Assert.assertEquals(
			existingAssetDisplayPageEntry.getLayoutPageTemplateEntryId(),
			newAssetDisplayPageEntry.getLayoutPageTemplateEntryId());
		Assert.assertEquals(
			existingAssetDisplayPageEntry.getType(),
			newAssetDisplayPageEntry.getType());
		Assert.assertEquals(
			existingAssetDisplayPageEntry.getPlid(),
			newAssetDisplayPageEntry.getPlid());
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
	public void testCountByLayoutPageTemplateEntryId() throws Exception {
		_persistence.countByLayoutPageTemplateEntryId(
			RandomTestUtil.nextLong());

		_persistence.countByLayoutPageTemplateEntryId(0L);
	}

	@Test
	public void testCountByG_C_C() throws Exception {
		_persistence.countByG_C_C(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong());

		_persistence.countByG_C_C(0L, 0L, 0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		AssetDisplayPageEntry newAssetDisplayPageEntry =
			addAssetDisplayPageEntry();

		AssetDisplayPageEntry existingAssetDisplayPageEntry =
			_persistence.findByPrimaryKey(
				newAssetDisplayPageEntry.getPrimaryKey());

		Assert.assertEquals(
			existingAssetDisplayPageEntry, newAssetDisplayPageEntry);
	}

	@Test(expected = NoSuchDisplayPageEntryException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<AssetDisplayPageEntry> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"AssetDisplayPageEntry", "mvccVersion", true, "ctCollectionId",
			true, "uuid", true, "assetDisplayPageEntryId", true, "groupId",
			true, "companyId", true, "userId", true, "userName", true,
			"createDate", true, "modifiedDate", true, "classNameId", true,
			"classPK", true, "layoutPageTemplateEntryId", true, "type", true,
			"plid", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		AssetDisplayPageEntry newAssetDisplayPageEntry =
			addAssetDisplayPageEntry();

		AssetDisplayPageEntry existingAssetDisplayPageEntry =
			_persistence.fetchByPrimaryKey(
				newAssetDisplayPageEntry.getPrimaryKey());

		Assert.assertEquals(
			existingAssetDisplayPageEntry, newAssetDisplayPageEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AssetDisplayPageEntry missingAssetDisplayPageEntry =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingAssetDisplayPageEntry);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		AssetDisplayPageEntry newAssetDisplayPageEntry1 =
			addAssetDisplayPageEntry();
		AssetDisplayPageEntry newAssetDisplayPageEntry2 =
			addAssetDisplayPageEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAssetDisplayPageEntry1.getPrimaryKey());
		primaryKeys.add(newAssetDisplayPageEntry2.getPrimaryKey());

		Map<Serializable, AssetDisplayPageEntry> assetDisplayPageEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, assetDisplayPageEntries.size());
		Assert.assertEquals(
			newAssetDisplayPageEntry1,
			assetDisplayPageEntries.get(
				newAssetDisplayPageEntry1.getPrimaryKey()));
		Assert.assertEquals(
			newAssetDisplayPageEntry2,
			assetDisplayPageEntries.get(
				newAssetDisplayPageEntry2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, AssetDisplayPageEntry> assetDisplayPageEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(assetDisplayPageEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		AssetDisplayPageEntry newAssetDisplayPageEntry =
			addAssetDisplayPageEntry();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAssetDisplayPageEntry.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, AssetDisplayPageEntry> assetDisplayPageEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, assetDisplayPageEntries.size());
		Assert.assertEquals(
			newAssetDisplayPageEntry,
			assetDisplayPageEntries.get(
				newAssetDisplayPageEntry.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, AssetDisplayPageEntry> assetDisplayPageEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(assetDisplayPageEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		AssetDisplayPageEntry newAssetDisplayPageEntry =
			addAssetDisplayPageEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAssetDisplayPageEntry.getPrimaryKey());

		Map<Serializable, AssetDisplayPageEntry> assetDisplayPageEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, assetDisplayPageEntries.size());
		Assert.assertEquals(
			newAssetDisplayPageEntry,
			assetDisplayPageEntries.get(
				newAssetDisplayPageEntry.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			AssetDisplayPageEntryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<AssetDisplayPageEntry>() {

				@Override
				public void performAction(
					AssetDisplayPageEntry assetDisplayPageEntry) {

					Assert.assertNotNull(assetDisplayPageEntry);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		AssetDisplayPageEntry newAssetDisplayPageEntry =
			addAssetDisplayPageEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AssetDisplayPageEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"assetDisplayPageEntryId",
				newAssetDisplayPageEntry.getAssetDisplayPageEntryId()));

		List<AssetDisplayPageEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		AssetDisplayPageEntry existingAssetDisplayPageEntry = result.get(0);

		Assert.assertEquals(
			existingAssetDisplayPageEntry, newAssetDisplayPageEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AssetDisplayPageEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"assetDisplayPageEntryId", RandomTestUtil.nextLong()));

		List<AssetDisplayPageEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		AssetDisplayPageEntry newAssetDisplayPageEntry =
			addAssetDisplayPageEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AssetDisplayPageEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("assetDisplayPageEntryId"));

		Object newAssetDisplayPageEntryId =
			newAssetDisplayPageEntry.getAssetDisplayPageEntryId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"assetDisplayPageEntryId",
				new Object[] {newAssetDisplayPageEntryId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingAssetDisplayPageEntryId = result.get(0);

		Assert.assertEquals(
			existingAssetDisplayPageEntryId, newAssetDisplayPageEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AssetDisplayPageEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("assetDisplayPageEntryId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"assetDisplayPageEntryId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		AssetDisplayPageEntry newAssetDisplayPageEntry =
			addAssetDisplayPageEntry();

		_persistence.clearCache();

		_assertOriginalValues(
			_persistence.findByPrimaryKey(
				newAssetDisplayPageEntry.getPrimaryKey()));
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

		AssetDisplayPageEntry newAssetDisplayPageEntry =
			addAssetDisplayPageEntry();

		if (clearSession) {
			Session session = _persistence.openSession();

			session.flush();

			session.clear();
		}

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AssetDisplayPageEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"assetDisplayPageEntryId",
				newAssetDisplayPageEntry.getAssetDisplayPageEntryId()));

		List<AssetDisplayPageEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		_assertOriginalValues(result.get(0));
	}

	private void _assertOriginalValues(
		AssetDisplayPageEntry assetDisplayPageEntry) {

		Assert.assertEquals(
			assetDisplayPageEntry.getUuid(),
			ReflectionTestUtil.invoke(
				assetDisplayPageEntry, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "uuid_"));
		Assert.assertEquals(
			Long.valueOf(assetDisplayPageEntry.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				assetDisplayPageEntry, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "groupId"));

		Assert.assertEquals(
			Long.valueOf(assetDisplayPageEntry.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				assetDisplayPageEntry, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "groupId"));
		Assert.assertEquals(
			Long.valueOf(assetDisplayPageEntry.getClassNameId()),
			ReflectionTestUtil.<Long>invoke(
				assetDisplayPageEntry, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "classNameId"));
		Assert.assertEquals(
			Long.valueOf(assetDisplayPageEntry.getClassPK()),
			ReflectionTestUtil.<Long>invoke(
				assetDisplayPageEntry, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "classPK"));
	}

	protected AssetDisplayPageEntry addAssetDisplayPageEntry()
		throws Exception {

		long pk = RandomTestUtil.nextLong();

		AssetDisplayPageEntry assetDisplayPageEntry = _persistence.create(pk);

		assetDisplayPageEntry.setMvccVersion(RandomTestUtil.nextLong());

		assetDisplayPageEntry.setCtCollectionId(RandomTestUtil.nextLong());

		assetDisplayPageEntry.setUuid(RandomTestUtil.randomString());

		assetDisplayPageEntry.setGroupId(RandomTestUtil.nextLong());

		assetDisplayPageEntry.setCompanyId(RandomTestUtil.nextLong());

		assetDisplayPageEntry.setUserId(RandomTestUtil.nextLong());

		assetDisplayPageEntry.setUserName(RandomTestUtil.randomString());

		assetDisplayPageEntry.setCreateDate(RandomTestUtil.nextDate());

		assetDisplayPageEntry.setModifiedDate(RandomTestUtil.nextDate());

		assetDisplayPageEntry.setClassNameId(RandomTestUtil.nextLong());

		assetDisplayPageEntry.setClassPK(RandomTestUtil.nextLong());

		assetDisplayPageEntry.setLayoutPageTemplateEntryId(
			RandomTestUtil.nextLong());

		assetDisplayPageEntry.setType(RandomTestUtil.nextInt());

		assetDisplayPageEntry.setPlid(RandomTestUtil.nextLong());

		_assetDisplayPageEntries.add(
			_persistence.update(assetDisplayPageEntry));

		return assetDisplayPageEntry;
	}

	private List<AssetDisplayPageEntry> _assetDisplayPageEntries =
		new ArrayList<AssetDisplayPageEntry>();
	private AssetDisplayPageEntryPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}