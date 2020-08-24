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

package com.liferay.segments.service.persistence.test;

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
import com.liferay.segments.exception.NoSuchExperimentException;
import com.liferay.segments.model.SegmentsExperiment;
import com.liferay.segments.service.SegmentsExperimentLocalServiceUtil;
import com.liferay.segments.service.persistence.SegmentsExperimentPersistence;
import com.liferay.segments.service.persistence.SegmentsExperimentUtil;

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
public class SegmentsExperimentPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.segments.service"));

	@Before
	public void setUp() {
		_persistence = SegmentsExperimentUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<SegmentsExperiment> iterator = _segmentsExperiments.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SegmentsExperiment segmentsExperiment = _persistence.create(pk);

		Assert.assertNotNull(segmentsExperiment);

		Assert.assertEquals(segmentsExperiment.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		SegmentsExperiment newSegmentsExperiment = addSegmentsExperiment();

		_persistence.remove(newSegmentsExperiment);

		SegmentsExperiment existingSegmentsExperiment =
			_persistence.fetchByPrimaryKey(
				newSegmentsExperiment.getPrimaryKey());

		Assert.assertNull(existingSegmentsExperiment);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addSegmentsExperiment();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SegmentsExperiment newSegmentsExperiment = _persistence.create(pk);

		newSegmentsExperiment.setMvccVersion(RandomTestUtil.nextLong());

		newSegmentsExperiment.setCtCollectionId(RandomTestUtil.nextLong());

		newSegmentsExperiment.setUuid(RandomTestUtil.randomString());

		newSegmentsExperiment.setGroupId(RandomTestUtil.nextLong());

		newSegmentsExperiment.setCompanyId(RandomTestUtil.nextLong());

		newSegmentsExperiment.setUserId(RandomTestUtil.nextLong());

		newSegmentsExperiment.setUserName(RandomTestUtil.randomString());

		newSegmentsExperiment.setCreateDate(RandomTestUtil.nextDate());

		newSegmentsExperiment.setModifiedDate(RandomTestUtil.nextDate());

		newSegmentsExperiment.setSegmentsEntryId(RandomTestUtil.nextLong());

		newSegmentsExperiment.setSegmentsExperienceId(
			RandomTestUtil.nextLong());

		newSegmentsExperiment.setSegmentsExperimentKey(
			RandomTestUtil.randomString());

		newSegmentsExperiment.setClassNameId(RandomTestUtil.nextLong());

		newSegmentsExperiment.setClassPK(RandomTestUtil.nextLong());

		newSegmentsExperiment.setName(RandomTestUtil.randomString());

		newSegmentsExperiment.setDescription(RandomTestUtil.randomString());

		newSegmentsExperiment.setTypeSettings(RandomTestUtil.randomString());

		newSegmentsExperiment.setStatus(RandomTestUtil.nextInt());

		_segmentsExperiments.add(_persistence.update(newSegmentsExperiment));

		SegmentsExperiment existingSegmentsExperiment =
			_persistence.findByPrimaryKey(
				newSegmentsExperiment.getPrimaryKey());

		Assert.assertEquals(
			existingSegmentsExperiment.getMvccVersion(),
			newSegmentsExperiment.getMvccVersion());
		Assert.assertEquals(
			existingSegmentsExperiment.getCtCollectionId(),
			newSegmentsExperiment.getCtCollectionId());
		Assert.assertEquals(
			existingSegmentsExperiment.getUuid(),
			newSegmentsExperiment.getUuid());
		Assert.assertEquals(
			existingSegmentsExperiment.getSegmentsExperimentId(),
			newSegmentsExperiment.getSegmentsExperimentId());
		Assert.assertEquals(
			existingSegmentsExperiment.getGroupId(),
			newSegmentsExperiment.getGroupId());
		Assert.assertEquals(
			existingSegmentsExperiment.getCompanyId(),
			newSegmentsExperiment.getCompanyId());
		Assert.assertEquals(
			existingSegmentsExperiment.getUserId(),
			newSegmentsExperiment.getUserId());
		Assert.assertEquals(
			existingSegmentsExperiment.getUserName(),
			newSegmentsExperiment.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(existingSegmentsExperiment.getCreateDate()),
			Time.getShortTimestamp(newSegmentsExperiment.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingSegmentsExperiment.getModifiedDate()),
			Time.getShortTimestamp(newSegmentsExperiment.getModifiedDate()));
		Assert.assertEquals(
			existingSegmentsExperiment.getSegmentsEntryId(),
			newSegmentsExperiment.getSegmentsEntryId());
		Assert.assertEquals(
			existingSegmentsExperiment.getSegmentsExperienceId(),
			newSegmentsExperiment.getSegmentsExperienceId());
		Assert.assertEquals(
			existingSegmentsExperiment.getSegmentsExperimentKey(),
			newSegmentsExperiment.getSegmentsExperimentKey());
		Assert.assertEquals(
			existingSegmentsExperiment.getClassNameId(),
			newSegmentsExperiment.getClassNameId());
		Assert.assertEquals(
			existingSegmentsExperiment.getClassPK(),
			newSegmentsExperiment.getClassPK());
		Assert.assertEquals(
			existingSegmentsExperiment.getName(),
			newSegmentsExperiment.getName());
		Assert.assertEquals(
			existingSegmentsExperiment.getDescription(),
			newSegmentsExperiment.getDescription());
		Assert.assertEquals(
			existingSegmentsExperiment.getTypeSettings(),
			newSegmentsExperiment.getTypeSettings());
		Assert.assertEquals(
			existingSegmentsExperiment.getStatus(),
			newSegmentsExperiment.getStatus());
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
	public void testCountBySegmentsExperimentKey() throws Exception {
		_persistence.countBySegmentsExperimentKey("");

		_persistence.countBySegmentsExperimentKey("null");

		_persistence.countBySegmentsExperimentKey((String)null);
	}

	@Test
	public void testCountByG_S() throws Exception {
		_persistence.countByG_S(RandomTestUtil.nextLong(), "");

		_persistence.countByG_S(0L, "null");

		_persistence.countByG_S(0L, (String)null);
	}

	@Test
	public void testCountByG_C_C() throws Exception {
		_persistence.countByG_C_C(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong());

		_persistence.countByG_C_C(0L, 0L, 0L);
	}

	@Test
	public void testCountByS_C_C() throws Exception {
		_persistence.countByS_C_C(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong());

		_persistence.countByS_C_C(0L, 0L, 0L);
	}

	@Test
	public void testCountByS_C_C_S() throws Exception {
		_persistence.countByS_C_C_S(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong(), RandomTestUtil.nextInt());

		_persistence.countByS_C_C_S(0L, 0L, 0L, 0);
	}

	@Test
	public void testCountByS_C_C_SArrayable() throws Exception {
		_persistence.countByS_C_C_S(
			new long[] {RandomTestUtil.nextLong(), 0L},
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(),
			new int[] {RandomTestUtil.nextInt(), 0});
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		SegmentsExperiment newSegmentsExperiment = addSegmentsExperiment();

		SegmentsExperiment existingSegmentsExperiment =
			_persistence.findByPrimaryKey(
				newSegmentsExperiment.getPrimaryKey());

		Assert.assertEquals(existingSegmentsExperiment, newSegmentsExperiment);
	}

	@Test(expected = NoSuchExperimentException.class)
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

	protected OrderByComparator<SegmentsExperiment> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"SegmentsExperiment", "mvccVersion", true, "ctCollectionId", true,
			"uuid", true, "segmentsExperimentId", true, "groupId", true,
			"companyId", true, "userId", true, "userName", true, "createDate",
			true, "modifiedDate", true, "segmentsEntryId", true,
			"segmentsExperienceId", true, "segmentsExperimentKey", true,
			"classNameId", true, "classPK", true, "name", true, "description",
			true, "status", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		SegmentsExperiment newSegmentsExperiment = addSegmentsExperiment();

		SegmentsExperiment existingSegmentsExperiment =
			_persistence.fetchByPrimaryKey(
				newSegmentsExperiment.getPrimaryKey());

		Assert.assertEquals(existingSegmentsExperiment, newSegmentsExperiment);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SegmentsExperiment missingSegmentsExperiment =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingSegmentsExperiment);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		SegmentsExperiment newSegmentsExperiment1 = addSegmentsExperiment();
		SegmentsExperiment newSegmentsExperiment2 = addSegmentsExperiment();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newSegmentsExperiment1.getPrimaryKey());
		primaryKeys.add(newSegmentsExperiment2.getPrimaryKey());

		Map<Serializable, SegmentsExperiment> segmentsExperiments =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, segmentsExperiments.size());
		Assert.assertEquals(
			newSegmentsExperiment1,
			segmentsExperiments.get(newSegmentsExperiment1.getPrimaryKey()));
		Assert.assertEquals(
			newSegmentsExperiment2,
			segmentsExperiments.get(newSegmentsExperiment2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, SegmentsExperiment> segmentsExperiments =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(segmentsExperiments.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		SegmentsExperiment newSegmentsExperiment = addSegmentsExperiment();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newSegmentsExperiment.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, SegmentsExperiment> segmentsExperiments =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, segmentsExperiments.size());
		Assert.assertEquals(
			newSegmentsExperiment,
			segmentsExperiments.get(newSegmentsExperiment.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, SegmentsExperiment> segmentsExperiments =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(segmentsExperiments.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		SegmentsExperiment newSegmentsExperiment = addSegmentsExperiment();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newSegmentsExperiment.getPrimaryKey());

		Map<Serializable, SegmentsExperiment> segmentsExperiments =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, segmentsExperiments.size());
		Assert.assertEquals(
			newSegmentsExperiment,
			segmentsExperiments.get(newSegmentsExperiment.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			SegmentsExperimentLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<SegmentsExperiment>() {

				@Override
				public void performAction(
					SegmentsExperiment segmentsExperiment) {

					Assert.assertNotNull(segmentsExperiment);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		SegmentsExperiment newSegmentsExperiment = addSegmentsExperiment();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			SegmentsExperiment.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"segmentsExperimentId",
				newSegmentsExperiment.getSegmentsExperimentId()));

		List<SegmentsExperiment> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		SegmentsExperiment existingSegmentsExperiment = result.get(0);

		Assert.assertEquals(existingSegmentsExperiment, newSegmentsExperiment);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			SegmentsExperiment.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"segmentsExperimentId", RandomTestUtil.nextLong()));

		List<SegmentsExperiment> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		SegmentsExperiment newSegmentsExperiment = addSegmentsExperiment();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			SegmentsExperiment.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("segmentsExperimentId"));

		Object newSegmentsExperimentId =
			newSegmentsExperiment.getSegmentsExperimentId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"segmentsExperimentId",
				new Object[] {newSegmentsExperimentId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingSegmentsExperimentId = result.get(0);

		Assert.assertEquals(
			existingSegmentsExperimentId, newSegmentsExperimentId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			SegmentsExperiment.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("segmentsExperimentId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"segmentsExperimentId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		SegmentsExperiment newSegmentsExperiment = addSegmentsExperiment();

		_persistence.clearCache();

		_assertOriginalValues(
			_persistence.findByPrimaryKey(
				newSegmentsExperiment.getPrimaryKey()));
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

		SegmentsExperiment newSegmentsExperiment = addSegmentsExperiment();

		if (clearSession) {
			Session session = _persistence.openSession();

			session.flush();

			session.clear();
		}

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			SegmentsExperiment.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"segmentsExperimentId",
				newSegmentsExperiment.getSegmentsExperimentId()));

		List<SegmentsExperiment> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		_assertOriginalValues(result.get(0));
	}

	private void _assertOriginalValues(SegmentsExperiment segmentsExperiment) {
		Assert.assertEquals(
			segmentsExperiment.getUuid(),
			ReflectionTestUtil.invoke(
				segmentsExperiment, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "uuid_"));
		Assert.assertEquals(
			Long.valueOf(segmentsExperiment.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				segmentsExperiment, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "groupId"));

		Assert.assertEquals(
			Long.valueOf(segmentsExperiment.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				segmentsExperiment, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "groupId"));
		Assert.assertEquals(
			segmentsExperiment.getSegmentsExperimentKey(),
			ReflectionTestUtil.invoke(
				segmentsExperiment, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "segmentsExperimentKey"));
	}

	protected SegmentsExperiment addSegmentsExperiment() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SegmentsExperiment segmentsExperiment = _persistence.create(pk);

		segmentsExperiment.setMvccVersion(RandomTestUtil.nextLong());

		segmentsExperiment.setCtCollectionId(RandomTestUtil.nextLong());

		segmentsExperiment.setUuid(RandomTestUtil.randomString());

		segmentsExperiment.setGroupId(RandomTestUtil.nextLong());

		segmentsExperiment.setCompanyId(RandomTestUtil.nextLong());

		segmentsExperiment.setUserId(RandomTestUtil.nextLong());

		segmentsExperiment.setUserName(RandomTestUtil.randomString());

		segmentsExperiment.setCreateDate(RandomTestUtil.nextDate());

		segmentsExperiment.setModifiedDate(RandomTestUtil.nextDate());

		segmentsExperiment.setSegmentsEntryId(RandomTestUtil.nextLong());

		segmentsExperiment.setSegmentsExperienceId(RandomTestUtil.nextLong());

		segmentsExperiment.setSegmentsExperimentKey(
			RandomTestUtil.randomString());

		segmentsExperiment.setClassNameId(RandomTestUtil.nextLong());

		segmentsExperiment.setClassPK(RandomTestUtil.nextLong());

		segmentsExperiment.setName(RandomTestUtil.randomString());

		segmentsExperiment.setDescription(RandomTestUtil.randomString());

		segmentsExperiment.setTypeSettings(RandomTestUtil.randomString());

		segmentsExperiment.setStatus(RandomTestUtil.nextInt());

		_segmentsExperiments.add(_persistence.update(segmentsExperiment));

		return segmentsExperiment;
	}

	private List<SegmentsExperiment> _segmentsExperiments =
		new ArrayList<SegmentsExperiment>();
	private SegmentsExperimentPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}