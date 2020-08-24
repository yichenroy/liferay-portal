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

package com.liferay.portal.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.NoSuchUserGroupGroupRoleException;
import com.liferay.portal.kernel.model.UserGroupGroupRole;
import com.liferay.portal.kernel.service.UserGroupGroupRoleLocalServiceUtil;
import com.liferay.portal.kernel.service.persistence.UserGroupGroupRolePersistence;
import com.liferay.portal.kernel.service.persistence.UserGroupGroupRoleUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
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
public class UserGroupGroupRolePersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = UserGroupGroupRoleUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<UserGroupGroupRole> iterator = _userGroupGroupRoles.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		UserGroupGroupRole userGroupGroupRole = _persistence.create(pk);

		Assert.assertNotNull(userGroupGroupRole);

		Assert.assertEquals(userGroupGroupRole.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		UserGroupGroupRole newUserGroupGroupRole = addUserGroupGroupRole();

		_persistence.remove(newUserGroupGroupRole);

		UserGroupGroupRole existingUserGroupGroupRole =
			_persistence.fetchByPrimaryKey(
				newUserGroupGroupRole.getPrimaryKey());

		Assert.assertNull(existingUserGroupGroupRole);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addUserGroupGroupRole();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		UserGroupGroupRole newUserGroupGroupRole = _persistence.create(pk);

		newUserGroupGroupRole.setMvccVersion(RandomTestUtil.nextLong());

		newUserGroupGroupRole.setCtCollectionId(RandomTestUtil.nextLong());

		newUserGroupGroupRole.setCompanyId(RandomTestUtil.nextLong());

		newUserGroupGroupRole.setUserGroupId(RandomTestUtil.nextLong());

		newUserGroupGroupRole.setGroupId(RandomTestUtil.nextLong());

		newUserGroupGroupRole.setRoleId(RandomTestUtil.nextLong());

		_userGroupGroupRoles.add(_persistence.update(newUserGroupGroupRole));

		UserGroupGroupRole existingUserGroupGroupRole =
			_persistence.findByPrimaryKey(
				newUserGroupGroupRole.getPrimaryKey());

		Assert.assertEquals(
			existingUserGroupGroupRole.getMvccVersion(),
			newUserGroupGroupRole.getMvccVersion());
		Assert.assertEquals(
			existingUserGroupGroupRole.getCtCollectionId(),
			newUserGroupGroupRole.getCtCollectionId());
		Assert.assertEquals(
			existingUserGroupGroupRole.getUserGroupGroupRoleId(),
			newUserGroupGroupRole.getUserGroupGroupRoleId());
		Assert.assertEquals(
			existingUserGroupGroupRole.getCompanyId(),
			newUserGroupGroupRole.getCompanyId());
		Assert.assertEquals(
			existingUserGroupGroupRole.getUserGroupId(),
			newUserGroupGroupRole.getUserGroupId());
		Assert.assertEquals(
			existingUserGroupGroupRole.getGroupId(),
			newUserGroupGroupRole.getGroupId());
		Assert.assertEquals(
			existingUserGroupGroupRole.getRoleId(),
			newUserGroupGroupRole.getRoleId());
	}

	@Test
	public void testCountByUserGroupId() throws Exception {
		_persistence.countByUserGroupId(RandomTestUtil.nextLong());

		_persistence.countByUserGroupId(0L);
	}

	@Test
	public void testCountByGroupId() throws Exception {
		_persistence.countByGroupId(RandomTestUtil.nextLong());

		_persistence.countByGroupId(0L);
	}

	@Test
	public void testCountByRoleId() throws Exception {
		_persistence.countByRoleId(RandomTestUtil.nextLong());

		_persistence.countByRoleId(0L);
	}

	@Test
	public void testCountByU_G() throws Exception {
		_persistence.countByU_G(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		_persistence.countByU_G(0L, 0L);
	}

	@Test
	public void testCountByG_R() throws Exception {
		_persistence.countByG_R(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		_persistence.countByG_R(0L, 0L);
	}

	@Test
	public void testCountByU_G_R() throws Exception {
		_persistence.countByU_G_R(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong());

		_persistence.countByU_G_R(0L, 0L, 0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		UserGroupGroupRole newUserGroupGroupRole = addUserGroupGroupRole();

		UserGroupGroupRole existingUserGroupGroupRole =
			_persistence.findByPrimaryKey(
				newUserGroupGroupRole.getPrimaryKey());

		Assert.assertEquals(existingUserGroupGroupRole, newUserGroupGroupRole);
	}

	@Test(expected = NoSuchUserGroupGroupRoleException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<UserGroupGroupRole> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"UserGroupGroupRole", "mvccVersion", true, "ctCollectionId", true,
			"userGroupGroupRoleId", true, "companyId", true, "userGroupId",
			true, "groupId", true, "roleId", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		UserGroupGroupRole newUserGroupGroupRole = addUserGroupGroupRole();

		UserGroupGroupRole existingUserGroupGroupRole =
			_persistence.fetchByPrimaryKey(
				newUserGroupGroupRole.getPrimaryKey());

		Assert.assertEquals(existingUserGroupGroupRole, newUserGroupGroupRole);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		UserGroupGroupRole missingUserGroupGroupRole =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingUserGroupGroupRole);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		UserGroupGroupRole newUserGroupGroupRole1 = addUserGroupGroupRole();
		UserGroupGroupRole newUserGroupGroupRole2 = addUserGroupGroupRole();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newUserGroupGroupRole1.getPrimaryKey());
		primaryKeys.add(newUserGroupGroupRole2.getPrimaryKey());

		Map<Serializable, UserGroupGroupRole> userGroupGroupRoles =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, userGroupGroupRoles.size());
		Assert.assertEquals(
			newUserGroupGroupRole1,
			userGroupGroupRoles.get(newUserGroupGroupRole1.getPrimaryKey()));
		Assert.assertEquals(
			newUserGroupGroupRole2,
			userGroupGroupRoles.get(newUserGroupGroupRole2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, UserGroupGroupRole> userGroupGroupRoles =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(userGroupGroupRoles.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		UserGroupGroupRole newUserGroupGroupRole = addUserGroupGroupRole();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newUserGroupGroupRole.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, UserGroupGroupRole> userGroupGroupRoles =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, userGroupGroupRoles.size());
		Assert.assertEquals(
			newUserGroupGroupRole,
			userGroupGroupRoles.get(newUserGroupGroupRole.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, UserGroupGroupRole> userGroupGroupRoles =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(userGroupGroupRoles.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		UserGroupGroupRole newUserGroupGroupRole = addUserGroupGroupRole();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newUserGroupGroupRole.getPrimaryKey());

		Map<Serializable, UserGroupGroupRole> userGroupGroupRoles =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, userGroupGroupRoles.size());
		Assert.assertEquals(
			newUserGroupGroupRole,
			userGroupGroupRoles.get(newUserGroupGroupRole.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			UserGroupGroupRoleLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<UserGroupGroupRole>() {

				@Override
				public void performAction(
					UserGroupGroupRole userGroupGroupRole) {

					Assert.assertNotNull(userGroupGroupRole);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		UserGroupGroupRole newUserGroupGroupRole = addUserGroupGroupRole();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			UserGroupGroupRole.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"userGroupGroupRoleId",
				newUserGroupGroupRole.getUserGroupGroupRoleId()));

		List<UserGroupGroupRole> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		UserGroupGroupRole existingUserGroupGroupRole = result.get(0);

		Assert.assertEquals(existingUserGroupGroupRole, newUserGroupGroupRole);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			UserGroupGroupRole.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"userGroupGroupRoleId", RandomTestUtil.nextLong()));

		List<UserGroupGroupRole> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		UserGroupGroupRole newUserGroupGroupRole = addUserGroupGroupRole();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			UserGroupGroupRole.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("userGroupGroupRoleId"));

		Object newUserGroupGroupRoleId =
			newUserGroupGroupRole.getUserGroupGroupRoleId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"userGroupGroupRoleId",
				new Object[] {newUserGroupGroupRoleId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingUserGroupGroupRoleId = result.get(0);

		Assert.assertEquals(
			existingUserGroupGroupRoleId, newUserGroupGroupRoleId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			UserGroupGroupRole.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("userGroupGroupRoleId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"userGroupGroupRoleId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		UserGroupGroupRole newUserGroupGroupRole = addUserGroupGroupRole();

		_persistence.clearCache();

		_assertOriginalValues(
			_persistence.findByPrimaryKey(
				newUserGroupGroupRole.getPrimaryKey()));
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

		UserGroupGroupRole newUserGroupGroupRole = addUserGroupGroupRole();

		if (clearSession) {
			Session session = _persistence.openSession();

			session.flush();

			session.clear();
		}

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			UserGroupGroupRole.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"userGroupGroupRoleId",
				newUserGroupGroupRole.getUserGroupGroupRoleId()));

		List<UserGroupGroupRole> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		_assertOriginalValues(result.get(0));
	}

	private void _assertOriginalValues(UserGroupGroupRole userGroupGroupRole) {
		Assert.assertEquals(
			Long.valueOf(userGroupGroupRole.getUserGroupId()),
			ReflectionTestUtil.<Long>invoke(
				userGroupGroupRole, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "userGroupId"));
		Assert.assertEquals(
			Long.valueOf(userGroupGroupRole.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				userGroupGroupRole, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "groupId"));
		Assert.assertEquals(
			Long.valueOf(userGroupGroupRole.getRoleId()),
			ReflectionTestUtil.<Long>invoke(
				userGroupGroupRole, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "roleId"));
	}

	protected UserGroupGroupRole addUserGroupGroupRole() throws Exception {
		long pk = RandomTestUtil.nextLong();

		UserGroupGroupRole userGroupGroupRole = _persistence.create(pk);

		userGroupGroupRole.setMvccVersion(RandomTestUtil.nextLong());

		userGroupGroupRole.setCtCollectionId(RandomTestUtil.nextLong());

		userGroupGroupRole.setCompanyId(RandomTestUtil.nextLong());

		userGroupGroupRole.setUserGroupId(RandomTestUtil.nextLong());

		userGroupGroupRole.setGroupId(RandomTestUtil.nextLong());

		userGroupGroupRole.setRoleId(RandomTestUtil.nextLong());

		_userGroupGroupRoles.add(_persistence.update(userGroupGroupRole));

		return userGroupGroupRole;
	}

	private List<UserGroupGroupRole> _userGroupGroupRoles =
		new ArrayList<UserGroupGroupRole>();
	private UserGroupGroupRolePersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}