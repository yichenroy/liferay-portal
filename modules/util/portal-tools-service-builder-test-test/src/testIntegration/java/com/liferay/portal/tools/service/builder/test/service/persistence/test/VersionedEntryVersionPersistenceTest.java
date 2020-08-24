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

package com.liferay.portal.tools.service.builder.test.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
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
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;
import com.liferay.portal.tools.service.builder.test.exception.NoSuchVersionedEntryVersionException;
import com.liferay.portal.tools.service.builder.test.model.VersionedEntryVersion;
import com.liferay.portal.tools.service.builder.test.service.persistence.VersionedEntryVersionPersistence;
import com.liferay.portal.tools.service.builder.test.service.persistence.VersionedEntryVersionUtil;

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
public class VersionedEntryVersionPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED,
				"com.liferay.portal.tools.service.builder.test.service"));

	@Before
	public void setUp() {
		_persistence = VersionedEntryVersionUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<VersionedEntryVersion> iterator =
			_versionedEntryVersions.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		VersionedEntryVersion versionedEntryVersion = _persistence.create(pk);

		Assert.assertNotNull(versionedEntryVersion);

		Assert.assertEquals(versionedEntryVersion.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		VersionedEntryVersion newVersionedEntryVersion =
			addVersionedEntryVersion();

		_persistence.remove(newVersionedEntryVersion);

		VersionedEntryVersion existingVersionedEntryVersion =
			_persistence.fetchByPrimaryKey(
				newVersionedEntryVersion.getPrimaryKey());

		Assert.assertNull(existingVersionedEntryVersion);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addVersionedEntryVersion();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		VersionedEntryVersion newVersionedEntryVersion = _persistence.create(
			pk);

		newVersionedEntryVersion.setVersion(RandomTestUtil.nextInt());

		newVersionedEntryVersion.setVersionedEntryId(RandomTestUtil.nextLong());

		newVersionedEntryVersion.setGroupId(RandomTestUtil.nextLong());

		_versionedEntryVersions.add(
			_persistence.update(newVersionedEntryVersion));

		VersionedEntryVersion existingVersionedEntryVersion =
			_persistence.findByPrimaryKey(
				newVersionedEntryVersion.getPrimaryKey());

		Assert.assertEquals(
			existingVersionedEntryVersion.getVersionedEntryVersionId(),
			newVersionedEntryVersion.getVersionedEntryVersionId());
		Assert.assertEquals(
			existingVersionedEntryVersion.getVersion(),
			newVersionedEntryVersion.getVersion());
		Assert.assertEquals(
			existingVersionedEntryVersion.getVersionedEntryId(),
			newVersionedEntryVersion.getVersionedEntryId());
		Assert.assertEquals(
			existingVersionedEntryVersion.getGroupId(),
			newVersionedEntryVersion.getGroupId());
	}

	@Test
	public void testCountByVersionedEntryId() throws Exception {
		_persistence.countByVersionedEntryId(RandomTestUtil.nextLong());

		_persistence.countByVersionedEntryId(0L);
	}

	@Test
	public void testCountByVersionedEntryId_Version() throws Exception {
		_persistence.countByVersionedEntryId_Version(
			RandomTestUtil.nextLong(), RandomTestUtil.nextInt());

		_persistence.countByVersionedEntryId_Version(0L, 0);
	}

	@Test
	public void testCountByGroupId() throws Exception {
		_persistence.countByGroupId(RandomTestUtil.nextLong());

		_persistence.countByGroupId(0L);
	}

	@Test
	public void testCountByGroupId_Version() throws Exception {
		_persistence.countByGroupId_Version(
			RandomTestUtil.nextLong(), RandomTestUtil.nextInt());

		_persistence.countByGroupId_Version(0L, 0);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		VersionedEntryVersion newVersionedEntryVersion =
			addVersionedEntryVersion();

		VersionedEntryVersion existingVersionedEntryVersion =
			_persistence.findByPrimaryKey(
				newVersionedEntryVersion.getPrimaryKey());

		Assert.assertEquals(
			existingVersionedEntryVersion, newVersionedEntryVersion);
	}

	@Test(expected = NoSuchVersionedEntryVersionException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<VersionedEntryVersion> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"VersionedEntryVersion", "versionedEntryVersionId", true, "version",
			true, "versionedEntryId", true, "groupId", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		VersionedEntryVersion newVersionedEntryVersion =
			addVersionedEntryVersion();

		VersionedEntryVersion existingVersionedEntryVersion =
			_persistence.fetchByPrimaryKey(
				newVersionedEntryVersion.getPrimaryKey());

		Assert.assertEquals(
			existingVersionedEntryVersion, newVersionedEntryVersion);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		VersionedEntryVersion missingVersionedEntryVersion =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingVersionedEntryVersion);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		VersionedEntryVersion newVersionedEntryVersion1 =
			addVersionedEntryVersion();
		VersionedEntryVersion newVersionedEntryVersion2 =
			addVersionedEntryVersion();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newVersionedEntryVersion1.getPrimaryKey());
		primaryKeys.add(newVersionedEntryVersion2.getPrimaryKey());

		Map<Serializable, VersionedEntryVersion> versionedEntryVersions =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, versionedEntryVersions.size());
		Assert.assertEquals(
			newVersionedEntryVersion1,
			versionedEntryVersions.get(
				newVersionedEntryVersion1.getPrimaryKey()));
		Assert.assertEquals(
			newVersionedEntryVersion2,
			versionedEntryVersions.get(
				newVersionedEntryVersion2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, VersionedEntryVersion> versionedEntryVersions =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(versionedEntryVersions.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		VersionedEntryVersion newVersionedEntryVersion =
			addVersionedEntryVersion();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newVersionedEntryVersion.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, VersionedEntryVersion> versionedEntryVersions =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, versionedEntryVersions.size());
		Assert.assertEquals(
			newVersionedEntryVersion,
			versionedEntryVersions.get(
				newVersionedEntryVersion.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, VersionedEntryVersion> versionedEntryVersions =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(versionedEntryVersions.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		VersionedEntryVersion newVersionedEntryVersion =
			addVersionedEntryVersion();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newVersionedEntryVersion.getPrimaryKey());

		Map<Serializable, VersionedEntryVersion> versionedEntryVersions =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, versionedEntryVersions.size());
		Assert.assertEquals(
			newVersionedEntryVersion,
			versionedEntryVersions.get(
				newVersionedEntryVersion.getPrimaryKey()));
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		VersionedEntryVersion newVersionedEntryVersion =
			addVersionedEntryVersion();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			VersionedEntryVersion.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"versionedEntryVersionId",
				newVersionedEntryVersion.getVersionedEntryVersionId()));

		List<VersionedEntryVersion> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		VersionedEntryVersion existingVersionedEntryVersion = result.get(0);

		Assert.assertEquals(
			existingVersionedEntryVersion, newVersionedEntryVersion);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			VersionedEntryVersion.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"versionedEntryVersionId", RandomTestUtil.nextLong()));

		List<VersionedEntryVersion> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		VersionedEntryVersion newVersionedEntryVersion =
			addVersionedEntryVersion();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			VersionedEntryVersion.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("versionedEntryVersionId"));

		Object newVersionedEntryVersionId =
			newVersionedEntryVersion.getVersionedEntryVersionId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"versionedEntryVersionId",
				new Object[] {newVersionedEntryVersionId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingVersionedEntryVersionId = result.get(0);

		Assert.assertEquals(
			existingVersionedEntryVersionId, newVersionedEntryVersionId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			VersionedEntryVersion.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("versionedEntryVersionId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"versionedEntryVersionId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		VersionedEntryVersion newVersionedEntryVersion =
			addVersionedEntryVersion();

		_persistence.clearCache();

		_assertOriginalValues(
			_persistence.findByPrimaryKey(
				newVersionedEntryVersion.getPrimaryKey()));
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

		VersionedEntryVersion newVersionedEntryVersion =
			addVersionedEntryVersion();

		if (clearSession) {
			Session session = _persistence.openSession();

			session.flush();

			session.clear();
		}

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			VersionedEntryVersion.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"versionedEntryVersionId",
				newVersionedEntryVersion.getVersionedEntryVersionId()));

		List<VersionedEntryVersion> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		_assertOriginalValues(result.get(0));
	}

	private void _assertOriginalValues(
		VersionedEntryVersion versionedEntryVersion) {

		Assert.assertEquals(
			Long.valueOf(versionedEntryVersion.getVersionedEntryId()),
			ReflectionTestUtil.<Long>invoke(
				versionedEntryVersion, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "versionedEntryId"));
		Assert.assertEquals(
			Integer.valueOf(versionedEntryVersion.getVersion()),
			ReflectionTestUtil.<Integer>invoke(
				versionedEntryVersion, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "version"));
	}

	protected VersionedEntryVersion addVersionedEntryVersion()
		throws Exception {

		long pk = RandomTestUtil.nextLong();

		VersionedEntryVersion versionedEntryVersion = _persistence.create(pk);

		versionedEntryVersion.setVersion(RandomTestUtil.nextInt());

		versionedEntryVersion.setVersionedEntryId(RandomTestUtil.nextLong());

		versionedEntryVersion.setGroupId(RandomTestUtil.nextLong());

		_versionedEntryVersions.add(_persistence.update(versionedEntryVersion));

		return versionedEntryVersion;
	}

	private List<VersionedEntryVersion> _versionedEntryVersions =
		new ArrayList<VersionedEntryVersion>();
	private VersionedEntryVersionPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}