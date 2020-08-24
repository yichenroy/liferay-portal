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

package com.liferay.fragment.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.fragment.exception.NoSuchCompositionException;
import com.liferay.fragment.model.FragmentComposition;
import com.liferay.fragment.service.FragmentCompositionLocalServiceUtil;
import com.liferay.fragment.service.persistence.FragmentCompositionPersistence;
import com.liferay.fragment.service.persistence.FragmentCompositionUtil;
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
public class FragmentCompositionPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.fragment.service"));

	@Before
	public void setUp() {
		_persistence = FragmentCompositionUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<FragmentComposition> iterator =
			_fragmentCompositions.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		FragmentComposition fragmentComposition = _persistence.create(pk);

		Assert.assertNotNull(fragmentComposition);

		Assert.assertEquals(fragmentComposition.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		FragmentComposition newFragmentComposition = addFragmentComposition();

		_persistence.remove(newFragmentComposition);

		FragmentComposition existingFragmentComposition =
			_persistence.fetchByPrimaryKey(
				newFragmentComposition.getPrimaryKey());

		Assert.assertNull(existingFragmentComposition);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addFragmentComposition();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		FragmentComposition newFragmentComposition = _persistence.create(pk);

		newFragmentComposition.setMvccVersion(RandomTestUtil.nextLong());

		newFragmentComposition.setCtCollectionId(RandomTestUtil.nextLong());

		newFragmentComposition.setUuid(RandomTestUtil.randomString());

		newFragmentComposition.setGroupId(RandomTestUtil.nextLong());

		newFragmentComposition.setCompanyId(RandomTestUtil.nextLong());

		newFragmentComposition.setUserId(RandomTestUtil.nextLong());

		newFragmentComposition.setUserName(RandomTestUtil.randomString());

		newFragmentComposition.setCreateDate(RandomTestUtil.nextDate());

		newFragmentComposition.setModifiedDate(RandomTestUtil.nextDate());

		newFragmentComposition.setFragmentCollectionId(
			RandomTestUtil.nextLong());

		newFragmentComposition.setFragmentCompositionKey(
			RandomTestUtil.randomString());

		newFragmentComposition.setName(RandomTestUtil.randomString());

		newFragmentComposition.setDescription(RandomTestUtil.randomString());

		newFragmentComposition.setData(RandomTestUtil.randomString());

		newFragmentComposition.setPreviewFileEntryId(RandomTestUtil.nextLong());

		newFragmentComposition.setLastPublishDate(RandomTestUtil.nextDate());

		newFragmentComposition.setStatus(RandomTestUtil.nextInt());

		newFragmentComposition.setStatusByUserId(RandomTestUtil.nextLong());

		newFragmentComposition.setStatusByUserName(
			RandomTestUtil.randomString());

		newFragmentComposition.setStatusDate(RandomTestUtil.nextDate());

		_fragmentCompositions.add(_persistence.update(newFragmentComposition));

		FragmentComposition existingFragmentComposition =
			_persistence.findByPrimaryKey(
				newFragmentComposition.getPrimaryKey());

		Assert.assertEquals(
			existingFragmentComposition.getMvccVersion(),
			newFragmentComposition.getMvccVersion());
		Assert.assertEquals(
			existingFragmentComposition.getCtCollectionId(),
			newFragmentComposition.getCtCollectionId());
		Assert.assertEquals(
			existingFragmentComposition.getUuid(),
			newFragmentComposition.getUuid());
		Assert.assertEquals(
			existingFragmentComposition.getFragmentCompositionId(),
			newFragmentComposition.getFragmentCompositionId());
		Assert.assertEquals(
			existingFragmentComposition.getGroupId(),
			newFragmentComposition.getGroupId());
		Assert.assertEquals(
			existingFragmentComposition.getCompanyId(),
			newFragmentComposition.getCompanyId());
		Assert.assertEquals(
			existingFragmentComposition.getUserId(),
			newFragmentComposition.getUserId());
		Assert.assertEquals(
			existingFragmentComposition.getUserName(),
			newFragmentComposition.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(existingFragmentComposition.getCreateDate()),
			Time.getShortTimestamp(newFragmentComposition.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingFragmentComposition.getModifiedDate()),
			Time.getShortTimestamp(newFragmentComposition.getModifiedDate()));
		Assert.assertEquals(
			existingFragmentComposition.getFragmentCollectionId(),
			newFragmentComposition.getFragmentCollectionId());
		Assert.assertEquals(
			existingFragmentComposition.getFragmentCompositionKey(),
			newFragmentComposition.getFragmentCompositionKey());
		Assert.assertEquals(
			existingFragmentComposition.getName(),
			newFragmentComposition.getName());
		Assert.assertEquals(
			existingFragmentComposition.getDescription(),
			newFragmentComposition.getDescription());
		Assert.assertEquals(
			existingFragmentComposition.getData(),
			newFragmentComposition.getData());
		Assert.assertEquals(
			existingFragmentComposition.getPreviewFileEntryId(),
			newFragmentComposition.getPreviewFileEntryId());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingFragmentComposition.getLastPublishDate()),
			Time.getShortTimestamp(
				newFragmentComposition.getLastPublishDate()));
		Assert.assertEquals(
			existingFragmentComposition.getStatus(),
			newFragmentComposition.getStatus());
		Assert.assertEquals(
			existingFragmentComposition.getStatusByUserId(),
			newFragmentComposition.getStatusByUserId());
		Assert.assertEquals(
			existingFragmentComposition.getStatusByUserName(),
			newFragmentComposition.getStatusByUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(existingFragmentComposition.getStatusDate()),
			Time.getShortTimestamp(newFragmentComposition.getStatusDate()));
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
	public void testCountByFragmentCollectionId() throws Exception {
		_persistence.countByFragmentCollectionId(RandomTestUtil.nextLong());

		_persistence.countByFragmentCollectionId(0L);
	}

	@Test
	public void testCountByG_FCI() throws Exception {
		_persistence.countByG_FCI(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		_persistence.countByG_FCI(0L, 0L);
	}

	@Test
	public void testCountByG_FCK() throws Exception {
		_persistence.countByG_FCK(RandomTestUtil.nextLong(), "");

		_persistence.countByG_FCK(0L, "null");

		_persistence.countByG_FCK(0L, (String)null);
	}

	@Test
	public void testCountByG_FCI_LikeN() throws Exception {
		_persistence.countByG_FCI_LikeN(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(), "");

		_persistence.countByG_FCI_LikeN(0L, 0L, "null");

		_persistence.countByG_FCI_LikeN(0L, 0L, (String)null);
	}

	@Test
	public void testCountByG_FCI_S() throws Exception {
		_persistence.countByG_FCI_S(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(),
			RandomTestUtil.nextInt());

		_persistence.countByG_FCI_S(0L, 0L, 0);
	}

	@Test
	public void testCountByG_FCI_LikeN_S() throws Exception {
		_persistence.countByG_FCI_LikeN_S(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(), "",
			RandomTestUtil.nextInt());

		_persistence.countByG_FCI_LikeN_S(0L, 0L, "null", 0);

		_persistence.countByG_FCI_LikeN_S(0L, 0L, (String)null, 0);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		FragmentComposition newFragmentComposition = addFragmentComposition();

		FragmentComposition existingFragmentComposition =
			_persistence.findByPrimaryKey(
				newFragmentComposition.getPrimaryKey());

		Assert.assertEquals(
			existingFragmentComposition, newFragmentComposition);
	}

	@Test(expected = NoSuchCompositionException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<FragmentComposition> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"FragmentComposition", "mvccVersion", true, "ctCollectionId", true,
			"uuid", true, "fragmentCompositionId", true, "groupId", true,
			"companyId", true, "userId", true, "userName", true, "createDate",
			true, "modifiedDate", true, "fragmentCollectionId", true,
			"fragmentCompositionKey", true, "name", true, "description", true,
			"previewFileEntryId", true, "lastPublishDate", true, "status", true,
			"statusByUserId", true, "statusByUserName", true, "statusDate",
			true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		FragmentComposition newFragmentComposition = addFragmentComposition();

		FragmentComposition existingFragmentComposition =
			_persistence.fetchByPrimaryKey(
				newFragmentComposition.getPrimaryKey());

		Assert.assertEquals(
			existingFragmentComposition, newFragmentComposition);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		FragmentComposition missingFragmentComposition =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingFragmentComposition);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		FragmentComposition newFragmentComposition1 = addFragmentComposition();
		FragmentComposition newFragmentComposition2 = addFragmentComposition();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newFragmentComposition1.getPrimaryKey());
		primaryKeys.add(newFragmentComposition2.getPrimaryKey());

		Map<Serializable, FragmentComposition> fragmentCompositions =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, fragmentCompositions.size());
		Assert.assertEquals(
			newFragmentComposition1,
			fragmentCompositions.get(newFragmentComposition1.getPrimaryKey()));
		Assert.assertEquals(
			newFragmentComposition2,
			fragmentCompositions.get(newFragmentComposition2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, FragmentComposition> fragmentCompositions =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(fragmentCompositions.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		FragmentComposition newFragmentComposition = addFragmentComposition();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newFragmentComposition.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, FragmentComposition> fragmentCompositions =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, fragmentCompositions.size());
		Assert.assertEquals(
			newFragmentComposition,
			fragmentCompositions.get(newFragmentComposition.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, FragmentComposition> fragmentCompositions =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(fragmentCompositions.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		FragmentComposition newFragmentComposition = addFragmentComposition();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newFragmentComposition.getPrimaryKey());

		Map<Serializable, FragmentComposition> fragmentCompositions =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, fragmentCompositions.size());
		Assert.assertEquals(
			newFragmentComposition,
			fragmentCompositions.get(newFragmentComposition.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			FragmentCompositionLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<FragmentComposition>() {

				@Override
				public void performAction(
					FragmentComposition fragmentComposition) {

					Assert.assertNotNull(fragmentComposition);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		FragmentComposition newFragmentComposition = addFragmentComposition();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			FragmentComposition.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"fragmentCompositionId",
				newFragmentComposition.getFragmentCompositionId()));

		List<FragmentComposition> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		FragmentComposition existingFragmentComposition = result.get(0);

		Assert.assertEquals(
			existingFragmentComposition, newFragmentComposition);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			FragmentComposition.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"fragmentCompositionId", RandomTestUtil.nextLong()));

		List<FragmentComposition> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		FragmentComposition newFragmentComposition = addFragmentComposition();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			FragmentComposition.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("fragmentCompositionId"));

		Object newFragmentCompositionId =
			newFragmentComposition.getFragmentCompositionId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"fragmentCompositionId",
				new Object[] {newFragmentCompositionId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingFragmentCompositionId = result.get(0);

		Assert.assertEquals(
			existingFragmentCompositionId, newFragmentCompositionId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			FragmentComposition.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("fragmentCompositionId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"fragmentCompositionId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		FragmentComposition newFragmentComposition = addFragmentComposition();

		_persistence.clearCache();

		_assertOriginalValues(
			_persistence.findByPrimaryKey(
				newFragmentComposition.getPrimaryKey()));
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

		FragmentComposition newFragmentComposition = addFragmentComposition();

		if (clearSession) {
			Session session = _persistence.openSession();

			session.flush();

			session.clear();
		}

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			FragmentComposition.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"fragmentCompositionId",
				newFragmentComposition.getFragmentCompositionId()));

		List<FragmentComposition> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		_assertOriginalValues(result.get(0));
	}

	private void _assertOriginalValues(
		FragmentComposition fragmentComposition) {

		Assert.assertEquals(
			fragmentComposition.getUuid(),
			ReflectionTestUtil.invoke(
				fragmentComposition, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "uuid_"));
		Assert.assertEquals(
			Long.valueOf(fragmentComposition.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				fragmentComposition, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "groupId"));

		Assert.assertEquals(
			Long.valueOf(fragmentComposition.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				fragmentComposition, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "groupId"));
		Assert.assertEquals(
			fragmentComposition.getFragmentCompositionKey(),
			ReflectionTestUtil.invoke(
				fragmentComposition, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "fragmentCompositionKey"));
	}

	protected FragmentComposition addFragmentComposition() throws Exception {
		long pk = RandomTestUtil.nextLong();

		FragmentComposition fragmentComposition = _persistence.create(pk);

		fragmentComposition.setMvccVersion(RandomTestUtil.nextLong());

		fragmentComposition.setCtCollectionId(RandomTestUtil.nextLong());

		fragmentComposition.setUuid(RandomTestUtil.randomString());

		fragmentComposition.setGroupId(RandomTestUtil.nextLong());

		fragmentComposition.setCompanyId(RandomTestUtil.nextLong());

		fragmentComposition.setUserId(RandomTestUtil.nextLong());

		fragmentComposition.setUserName(RandomTestUtil.randomString());

		fragmentComposition.setCreateDate(RandomTestUtil.nextDate());

		fragmentComposition.setModifiedDate(RandomTestUtil.nextDate());

		fragmentComposition.setFragmentCollectionId(RandomTestUtil.nextLong());

		fragmentComposition.setFragmentCompositionKey(
			RandomTestUtil.randomString());

		fragmentComposition.setName(RandomTestUtil.randomString());

		fragmentComposition.setDescription(RandomTestUtil.randomString());

		fragmentComposition.setData(RandomTestUtil.randomString());

		fragmentComposition.setPreviewFileEntryId(RandomTestUtil.nextLong());

		fragmentComposition.setLastPublishDate(RandomTestUtil.nextDate());

		fragmentComposition.setStatus(RandomTestUtil.nextInt());

		fragmentComposition.setStatusByUserId(RandomTestUtil.nextLong());

		fragmentComposition.setStatusByUserName(RandomTestUtil.randomString());

		fragmentComposition.setStatusDate(RandomTestUtil.nextDate());

		_fragmentCompositions.add(_persistence.update(fragmentComposition));

		return fragmentComposition;
	}

	private List<FragmentComposition> _fragmentCompositions =
		new ArrayList<FragmentComposition>();
	private FragmentCompositionPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}