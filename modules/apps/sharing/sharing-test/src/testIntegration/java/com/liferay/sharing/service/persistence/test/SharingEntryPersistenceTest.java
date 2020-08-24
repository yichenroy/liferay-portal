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

package com.liferay.sharing.service.persistence.test;

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
import com.liferay.sharing.exception.NoSuchEntryException;
import com.liferay.sharing.model.SharingEntry;
import com.liferay.sharing.service.SharingEntryLocalServiceUtil;
import com.liferay.sharing.service.persistence.SharingEntryPersistence;
import com.liferay.sharing.service.persistence.SharingEntryUtil;

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
public class SharingEntryPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.sharing.service"));

	@Before
	public void setUp() {
		_persistence = SharingEntryUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<SharingEntry> iterator = _sharingEntries.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SharingEntry sharingEntry = _persistence.create(pk);

		Assert.assertNotNull(sharingEntry);

		Assert.assertEquals(sharingEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		SharingEntry newSharingEntry = addSharingEntry();

		_persistence.remove(newSharingEntry);

		SharingEntry existingSharingEntry = _persistence.fetchByPrimaryKey(
			newSharingEntry.getPrimaryKey());

		Assert.assertNull(existingSharingEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addSharingEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SharingEntry newSharingEntry = _persistence.create(pk);

		newSharingEntry.setUuid(RandomTestUtil.randomString());

		newSharingEntry.setGroupId(RandomTestUtil.nextLong());

		newSharingEntry.setCompanyId(RandomTestUtil.nextLong());

		newSharingEntry.setUserId(RandomTestUtil.nextLong());

		newSharingEntry.setUserName(RandomTestUtil.randomString());

		newSharingEntry.setCreateDate(RandomTestUtil.nextDate());

		newSharingEntry.setModifiedDate(RandomTestUtil.nextDate());

		newSharingEntry.setToUserId(RandomTestUtil.nextLong());

		newSharingEntry.setClassNameId(RandomTestUtil.nextLong());

		newSharingEntry.setClassPK(RandomTestUtil.nextLong());

		newSharingEntry.setShareable(RandomTestUtil.randomBoolean());

		newSharingEntry.setActionIds(RandomTestUtil.nextLong());

		newSharingEntry.setExpirationDate(RandomTestUtil.nextDate());

		_sharingEntries.add(_persistence.update(newSharingEntry));

		SharingEntry existingSharingEntry = _persistence.findByPrimaryKey(
			newSharingEntry.getPrimaryKey());

		Assert.assertEquals(
			existingSharingEntry.getUuid(), newSharingEntry.getUuid());
		Assert.assertEquals(
			existingSharingEntry.getSharingEntryId(),
			newSharingEntry.getSharingEntryId());
		Assert.assertEquals(
			existingSharingEntry.getGroupId(), newSharingEntry.getGroupId());
		Assert.assertEquals(
			existingSharingEntry.getCompanyId(),
			newSharingEntry.getCompanyId());
		Assert.assertEquals(
			existingSharingEntry.getUserId(), newSharingEntry.getUserId());
		Assert.assertEquals(
			existingSharingEntry.getUserName(), newSharingEntry.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(existingSharingEntry.getCreateDate()),
			Time.getShortTimestamp(newSharingEntry.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(existingSharingEntry.getModifiedDate()),
			Time.getShortTimestamp(newSharingEntry.getModifiedDate()));
		Assert.assertEquals(
			existingSharingEntry.getToUserId(), newSharingEntry.getToUserId());
		Assert.assertEquals(
			existingSharingEntry.getClassNameId(),
			newSharingEntry.getClassNameId());
		Assert.assertEquals(
			existingSharingEntry.getClassPK(), newSharingEntry.getClassPK());
		Assert.assertEquals(
			existingSharingEntry.isShareable(), newSharingEntry.isShareable());
		Assert.assertEquals(
			existingSharingEntry.getActionIds(),
			newSharingEntry.getActionIds());
		Assert.assertEquals(
			Time.getShortTimestamp(existingSharingEntry.getExpirationDate()),
			Time.getShortTimestamp(newSharingEntry.getExpirationDate()));
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
	public void testCountByUserId() throws Exception {
		_persistence.countByUserId(RandomTestUtil.nextLong());

		_persistence.countByUserId(0L);
	}

	@Test
	public void testCountByToUserId() throws Exception {
		_persistence.countByToUserId(RandomTestUtil.nextLong());

		_persistence.countByToUserId(0L);
	}

	@Test
	public void testCountByExpirationDate() throws Exception {
		_persistence.countByExpirationDate(RandomTestUtil.nextDate());

		_persistence.countByExpirationDate(RandomTestUtil.nextDate());
	}

	@Test
	public void testCountByU_C() throws Exception {
		_persistence.countByU_C(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		_persistence.countByU_C(0L, 0L);
	}

	@Test
	public void testCountByTU_C() throws Exception {
		_persistence.countByTU_C(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		_persistence.countByTU_C(0L, 0L);
	}

	@Test
	public void testCountByC_C() throws Exception {
		_persistence.countByC_C(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		_persistence.countByC_C(0L, 0L);
	}

	@Test
	public void testCountByTU_C_C() throws Exception {
		_persistence.countByTU_C_C(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong());

		_persistence.countByTU_C_C(0L, 0L, 0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		SharingEntry newSharingEntry = addSharingEntry();

		SharingEntry existingSharingEntry = _persistence.findByPrimaryKey(
			newSharingEntry.getPrimaryKey());

		Assert.assertEquals(existingSharingEntry, newSharingEntry);
	}

	@Test(expected = NoSuchEntryException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<SharingEntry> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"SharingEntry", "uuid", true, "sharingEntryId", true, "groupId",
			true, "companyId", true, "userId", true, "userName", true,
			"createDate", true, "modifiedDate", true, "toUserId", true,
			"classNameId", true, "classPK", true, "shareable", true,
			"actionIds", true, "expirationDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		SharingEntry newSharingEntry = addSharingEntry();

		SharingEntry existingSharingEntry = _persistence.fetchByPrimaryKey(
			newSharingEntry.getPrimaryKey());

		Assert.assertEquals(existingSharingEntry, newSharingEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SharingEntry missingSharingEntry = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingSharingEntry);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		SharingEntry newSharingEntry1 = addSharingEntry();
		SharingEntry newSharingEntry2 = addSharingEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newSharingEntry1.getPrimaryKey());
		primaryKeys.add(newSharingEntry2.getPrimaryKey());

		Map<Serializable, SharingEntry> sharingEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, sharingEntries.size());
		Assert.assertEquals(
			newSharingEntry1,
			sharingEntries.get(newSharingEntry1.getPrimaryKey()));
		Assert.assertEquals(
			newSharingEntry2,
			sharingEntries.get(newSharingEntry2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, SharingEntry> sharingEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(sharingEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		SharingEntry newSharingEntry = addSharingEntry();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newSharingEntry.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, SharingEntry> sharingEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, sharingEntries.size());
		Assert.assertEquals(
			newSharingEntry,
			sharingEntries.get(newSharingEntry.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, SharingEntry> sharingEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(sharingEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		SharingEntry newSharingEntry = addSharingEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newSharingEntry.getPrimaryKey());

		Map<Serializable, SharingEntry> sharingEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, sharingEntries.size());
		Assert.assertEquals(
			newSharingEntry,
			sharingEntries.get(newSharingEntry.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			SharingEntryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<SharingEntry>() {

				@Override
				public void performAction(SharingEntry sharingEntry) {
					Assert.assertNotNull(sharingEntry);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		SharingEntry newSharingEntry = addSharingEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			SharingEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"sharingEntryId", newSharingEntry.getSharingEntryId()));

		List<SharingEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		SharingEntry existingSharingEntry = result.get(0);

		Assert.assertEquals(existingSharingEntry, newSharingEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			SharingEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"sharingEntryId", RandomTestUtil.nextLong()));

		List<SharingEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		SharingEntry newSharingEntry = addSharingEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			SharingEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("sharingEntryId"));

		Object newSharingEntryId = newSharingEntry.getSharingEntryId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"sharingEntryId", new Object[] {newSharingEntryId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingSharingEntryId = result.get(0);

		Assert.assertEquals(existingSharingEntryId, newSharingEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			SharingEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("sharingEntryId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"sharingEntryId", new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		SharingEntry newSharingEntry = addSharingEntry();

		_persistence.clearCache();

		_assertOriginalValues(
			_persistence.findByPrimaryKey(newSharingEntry.getPrimaryKey()));
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

		SharingEntry newSharingEntry = addSharingEntry();

		if (clearSession) {
			Session session = _persistence.openSession();

			session.flush();

			session.clear();
		}

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			SharingEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"sharingEntryId", newSharingEntry.getSharingEntryId()));

		List<SharingEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		_assertOriginalValues(result.get(0));
	}

	private void _assertOriginalValues(SharingEntry sharingEntry) {
		Assert.assertEquals(
			sharingEntry.getUuid(),
			ReflectionTestUtil.invoke(
				sharingEntry, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "uuid_"));
		Assert.assertEquals(
			Long.valueOf(sharingEntry.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				sharingEntry, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "groupId"));

		Assert.assertEquals(
			Long.valueOf(sharingEntry.getToUserId()),
			ReflectionTestUtil.<Long>invoke(
				sharingEntry, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "toUserId"));
		Assert.assertEquals(
			Long.valueOf(sharingEntry.getClassNameId()),
			ReflectionTestUtil.<Long>invoke(
				sharingEntry, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "classNameId"));
		Assert.assertEquals(
			Long.valueOf(sharingEntry.getClassPK()),
			ReflectionTestUtil.<Long>invoke(
				sharingEntry, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "classPK"));
	}

	protected SharingEntry addSharingEntry() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SharingEntry sharingEntry = _persistence.create(pk);

		sharingEntry.setUuid(RandomTestUtil.randomString());

		sharingEntry.setGroupId(RandomTestUtil.nextLong());

		sharingEntry.setCompanyId(RandomTestUtil.nextLong());

		sharingEntry.setUserId(RandomTestUtil.nextLong());

		sharingEntry.setUserName(RandomTestUtil.randomString());

		sharingEntry.setCreateDate(RandomTestUtil.nextDate());

		sharingEntry.setModifiedDate(RandomTestUtil.nextDate());

		sharingEntry.setToUserId(RandomTestUtil.nextLong());

		sharingEntry.setClassNameId(RandomTestUtil.nextLong());

		sharingEntry.setClassPK(RandomTestUtil.nextLong());

		sharingEntry.setShareable(RandomTestUtil.randomBoolean());

		sharingEntry.setActionIds(RandomTestUtil.nextLong());

		sharingEntry.setExpirationDate(RandomTestUtil.nextDate());

		_sharingEntries.add(_persistence.update(sharingEntry));

		return sharingEntry;
	}

	private List<SharingEntry> _sharingEntries = new ArrayList<SharingEntry>();
	private SharingEntryPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}