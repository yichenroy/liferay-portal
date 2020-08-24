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

package com.liferay.dynamic.data.mapping.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.exception.NoSuchFormInstanceException;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceLocalServiceUtil;
import com.liferay.dynamic.data.mapping.service.persistence.DDMFormInstancePersistence;
import com.liferay.dynamic.data.mapping.service.persistence.DDMFormInstanceUtil;
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
public class DDMFormInstancePersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED,
				"com.liferay.dynamic.data.mapping.service"));

	@Before
	public void setUp() {
		_persistence = DDMFormInstanceUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<DDMFormInstance> iterator = _ddmFormInstances.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DDMFormInstance ddmFormInstance = _persistence.create(pk);

		Assert.assertNotNull(ddmFormInstance);

		Assert.assertEquals(ddmFormInstance.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		DDMFormInstance newDDMFormInstance = addDDMFormInstance();

		_persistence.remove(newDDMFormInstance);

		DDMFormInstance existingDDMFormInstance =
			_persistence.fetchByPrimaryKey(newDDMFormInstance.getPrimaryKey());

		Assert.assertNull(existingDDMFormInstance);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addDDMFormInstance();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DDMFormInstance newDDMFormInstance = _persistence.create(pk);

		newDDMFormInstance.setMvccVersion(RandomTestUtil.nextLong());

		newDDMFormInstance.setCtCollectionId(RandomTestUtil.nextLong());

		newDDMFormInstance.setUuid(RandomTestUtil.randomString());

		newDDMFormInstance.setGroupId(RandomTestUtil.nextLong());

		newDDMFormInstance.setCompanyId(RandomTestUtil.nextLong());

		newDDMFormInstance.setUserId(RandomTestUtil.nextLong());

		newDDMFormInstance.setUserName(RandomTestUtil.randomString());

		newDDMFormInstance.setVersionUserId(RandomTestUtil.nextLong());

		newDDMFormInstance.setVersionUserName(RandomTestUtil.randomString());

		newDDMFormInstance.setCreateDate(RandomTestUtil.nextDate());

		newDDMFormInstance.setModifiedDate(RandomTestUtil.nextDate());

		newDDMFormInstance.setStructureId(RandomTestUtil.nextLong());

		newDDMFormInstance.setVersion(RandomTestUtil.randomString());

		newDDMFormInstance.setName(RandomTestUtil.randomString());

		newDDMFormInstance.setDescription(RandomTestUtil.randomString());

		newDDMFormInstance.setSettings(RandomTestUtil.randomString());

		newDDMFormInstance.setLastPublishDate(RandomTestUtil.nextDate());

		_ddmFormInstances.add(_persistence.update(newDDMFormInstance));

		DDMFormInstance existingDDMFormInstance = _persistence.findByPrimaryKey(
			newDDMFormInstance.getPrimaryKey());

		Assert.assertEquals(
			existingDDMFormInstance.getMvccVersion(),
			newDDMFormInstance.getMvccVersion());
		Assert.assertEquals(
			existingDDMFormInstance.getCtCollectionId(),
			newDDMFormInstance.getCtCollectionId());
		Assert.assertEquals(
			existingDDMFormInstance.getUuid(), newDDMFormInstance.getUuid());
		Assert.assertEquals(
			existingDDMFormInstance.getFormInstanceId(),
			newDDMFormInstance.getFormInstanceId());
		Assert.assertEquals(
			existingDDMFormInstance.getGroupId(),
			newDDMFormInstance.getGroupId());
		Assert.assertEquals(
			existingDDMFormInstance.getCompanyId(),
			newDDMFormInstance.getCompanyId());
		Assert.assertEquals(
			existingDDMFormInstance.getUserId(),
			newDDMFormInstance.getUserId());
		Assert.assertEquals(
			existingDDMFormInstance.getUserName(),
			newDDMFormInstance.getUserName());
		Assert.assertEquals(
			existingDDMFormInstance.getVersionUserId(),
			newDDMFormInstance.getVersionUserId());
		Assert.assertEquals(
			existingDDMFormInstance.getVersionUserName(),
			newDDMFormInstance.getVersionUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(existingDDMFormInstance.getCreateDate()),
			Time.getShortTimestamp(newDDMFormInstance.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(existingDDMFormInstance.getModifiedDate()),
			Time.getShortTimestamp(newDDMFormInstance.getModifiedDate()));
		Assert.assertEquals(
			existingDDMFormInstance.getStructureId(),
			newDDMFormInstance.getStructureId());
		Assert.assertEquals(
			existingDDMFormInstance.getVersion(),
			newDDMFormInstance.getVersion());
		Assert.assertEquals(
			existingDDMFormInstance.getName(), newDDMFormInstance.getName());
		Assert.assertEquals(
			existingDDMFormInstance.getDescription(),
			newDDMFormInstance.getDescription());
		Assert.assertEquals(
			existingDDMFormInstance.getSettings(),
			newDDMFormInstance.getSettings());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingDDMFormInstance.getLastPublishDate()),
			Time.getShortTimestamp(newDDMFormInstance.getLastPublishDate()));
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
	public void testCountByGroupIdArrayable() throws Exception {
		_persistence.countByGroupId(new long[] {RandomTestUtil.nextLong(), 0L});
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		DDMFormInstance newDDMFormInstance = addDDMFormInstance();

		DDMFormInstance existingDDMFormInstance = _persistence.findByPrimaryKey(
			newDDMFormInstance.getPrimaryKey());

		Assert.assertEquals(existingDDMFormInstance, newDDMFormInstance);
	}

	@Test(expected = NoSuchFormInstanceException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	@Test
	public void testFilterFindByGroupId() throws Exception {
		_persistence.filterFindByGroupId(
			0, QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<DDMFormInstance> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"DDMFormInstance", "mvccVersion", true, "ctCollectionId", true,
			"uuid", true, "formInstanceId", true, "groupId", true, "companyId",
			true, "userId", true, "userName", true, "versionUserId", true,
			"versionUserName", true, "createDate", true, "modifiedDate", true,
			"structureId", true, "version", true, "name", true,
			"lastPublishDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		DDMFormInstance newDDMFormInstance = addDDMFormInstance();

		DDMFormInstance existingDDMFormInstance =
			_persistence.fetchByPrimaryKey(newDDMFormInstance.getPrimaryKey());

		Assert.assertEquals(existingDDMFormInstance, newDDMFormInstance);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DDMFormInstance missingDDMFormInstance = _persistence.fetchByPrimaryKey(
			pk);

		Assert.assertNull(missingDDMFormInstance);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		DDMFormInstance newDDMFormInstance1 = addDDMFormInstance();
		DDMFormInstance newDDMFormInstance2 = addDDMFormInstance();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newDDMFormInstance1.getPrimaryKey());
		primaryKeys.add(newDDMFormInstance2.getPrimaryKey());

		Map<Serializable, DDMFormInstance> ddmFormInstances =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, ddmFormInstances.size());
		Assert.assertEquals(
			newDDMFormInstance1,
			ddmFormInstances.get(newDDMFormInstance1.getPrimaryKey()));
		Assert.assertEquals(
			newDDMFormInstance2,
			ddmFormInstances.get(newDDMFormInstance2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, DDMFormInstance> ddmFormInstances =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(ddmFormInstances.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		DDMFormInstance newDDMFormInstance = addDDMFormInstance();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newDDMFormInstance.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, DDMFormInstance> ddmFormInstances =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, ddmFormInstances.size());
		Assert.assertEquals(
			newDDMFormInstance,
			ddmFormInstances.get(newDDMFormInstance.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, DDMFormInstance> ddmFormInstances =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(ddmFormInstances.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		DDMFormInstance newDDMFormInstance = addDDMFormInstance();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newDDMFormInstance.getPrimaryKey());

		Map<Serializable, DDMFormInstance> ddmFormInstances =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, ddmFormInstances.size());
		Assert.assertEquals(
			newDDMFormInstance,
			ddmFormInstances.get(newDDMFormInstance.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			DDMFormInstanceLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<DDMFormInstance>() {

				@Override
				public void performAction(DDMFormInstance ddmFormInstance) {
					Assert.assertNotNull(ddmFormInstance);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		DDMFormInstance newDDMFormInstance = addDDMFormInstance();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			DDMFormInstance.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"formInstanceId", newDDMFormInstance.getFormInstanceId()));

		List<DDMFormInstance> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		DDMFormInstance existingDDMFormInstance = result.get(0);

		Assert.assertEquals(existingDDMFormInstance, newDDMFormInstance);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			DDMFormInstance.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"formInstanceId", RandomTestUtil.nextLong()));

		List<DDMFormInstance> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		DDMFormInstance newDDMFormInstance = addDDMFormInstance();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			DDMFormInstance.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("formInstanceId"));

		Object newFormInstanceId = newDDMFormInstance.getFormInstanceId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"formInstanceId", new Object[] {newFormInstanceId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingFormInstanceId = result.get(0);

		Assert.assertEquals(existingFormInstanceId, newFormInstanceId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			DDMFormInstance.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("formInstanceId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"formInstanceId", new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		DDMFormInstance newDDMFormInstance = addDDMFormInstance();

		_persistence.clearCache();

		_assertOriginalValues(
			_persistence.findByPrimaryKey(newDDMFormInstance.getPrimaryKey()));
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

		DDMFormInstance newDDMFormInstance = addDDMFormInstance();

		if (clearSession) {
			Session session = _persistence.openSession();

			session.flush();

			session.clear();
		}

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			DDMFormInstance.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"formInstanceId", newDDMFormInstance.getFormInstanceId()));

		List<DDMFormInstance> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		_assertOriginalValues(result.get(0));
	}

	private void _assertOriginalValues(DDMFormInstance ddmFormInstance) {
		Assert.assertEquals(
			ddmFormInstance.getUuid(),
			ReflectionTestUtil.invoke(
				ddmFormInstance, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "uuid_"));
		Assert.assertEquals(
			Long.valueOf(ddmFormInstance.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				ddmFormInstance, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "groupId"));
	}

	protected DDMFormInstance addDDMFormInstance() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DDMFormInstance ddmFormInstance = _persistence.create(pk);

		ddmFormInstance.setMvccVersion(RandomTestUtil.nextLong());

		ddmFormInstance.setCtCollectionId(RandomTestUtil.nextLong());

		ddmFormInstance.setUuid(RandomTestUtil.randomString());

		ddmFormInstance.setGroupId(RandomTestUtil.nextLong());

		ddmFormInstance.setCompanyId(RandomTestUtil.nextLong());

		ddmFormInstance.setUserId(RandomTestUtil.nextLong());

		ddmFormInstance.setUserName(RandomTestUtil.randomString());

		ddmFormInstance.setVersionUserId(RandomTestUtil.nextLong());

		ddmFormInstance.setVersionUserName(RandomTestUtil.randomString());

		ddmFormInstance.setCreateDate(RandomTestUtil.nextDate());

		ddmFormInstance.setModifiedDate(RandomTestUtil.nextDate());

		ddmFormInstance.setStructureId(RandomTestUtil.nextLong());

		ddmFormInstance.setVersion(RandomTestUtil.randomString());

		ddmFormInstance.setName(RandomTestUtil.randomString());

		ddmFormInstance.setDescription(RandomTestUtil.randomString());

		ddmFormInstance.setSettings(RandomTestUtil.randomString());

		ddmFormInstance.setLastPublishDate(RandomTestUtil.nextDate());

		_ddmFormInstances.add(_persistence.update(ddmFormInstance));

		return ddmFormInstance;
	}

	private List<DDMFormInstance> _ddmFormInstances =
		new ArrayList<DDMFormInstance>();
	private DDMFormInstancePersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}