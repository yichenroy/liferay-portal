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

package com.liferay.account.service.persistence.test;

import com.liferay.account.exception.NoSuchRoleException;
import com.liferay.account.model.AccountRole;
import com.liferay.account.service.AccountRoleLocalServiceUtil;
import com.liferay.account.service.persistence.AccountRolePersistence;
import com.liferay.account.service.persistence.AccountRoleUtil;
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
public class AccountRolePersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.account.service"));

	@Before
	public void setUp() {
		_persistence = AccountRoleUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<AccountRole> iterator = _accountRoles.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AccountRole accountRole = _persistence.create(pk);

		Assert.assertNotNull(accountRole);

		Assert.assertEquals(accountRole.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		AccountRole newAccountRole = addAccountRole();

		_persistence.remove(newAccountRole);

		AccountRole existingAccountRole = _persistence.fetchByPrimaryKey(
			newAccountRole.getPrimaryKey());

		Assert.assertNull(existingAccountRole);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addAccountRole();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AccountRole newAccountRole = _persistence.create(pk);

		newAccountRole.setMvccVersion(RandomTestUtil.nextLong());

		newAccountRole.setCompanyId(RandomTestUtil.nextLong());

		newAccountRole.setAccountEntryId(RandomTestUtil.nextLong());

		newAccountRole.setRoleId(RandomTestUtil.nextLong());

		_accountRoles.add(_persistence.update(newAccountRole));

		AccountRole existingAccountRole = _persistence.findByPrimaryKey(
			newAccountRole.getPrimaryKey());

		Assert.assertEquals(
			existingAccountRole.getMvccVersion(),
			newAccountRole.getMvccVersion());
		Assert.assertEquals(
			existingAccountRole.getAccountRoleId(),
			newAccountRole.getAccountRoleId());
		Assert.assertEquals(
			existingAccountRole.getCompanyId(), newAccountRole.getCompanyId());
		Assert.assertEquals(
			existingAccountRole.getAccountEntryId(),
			newAccountRole.getAccountEntryId());
		Assert.assertEquals(
			existingAccountRole.getRoleId(), newAccountRole.getRoleId());
	}

	@Test
	public void testCountByCompanyId() throws Exception {
		_persistence.countByCompanyId(RandomTestUtil.nextLong());

		_persistence.countByCompanyId(0L);
	}

	@Test
	public void testCountByAccountEntryId() throws Exception {
		_persistence.countByAccountEntryId(RandomTestUtil.nextLong());

		_persistence.countByAccountEntryId(0L);
	}

	@Test
	public void testCountByAccountEntryIdArrayable() throws Exception {
		_persistence.countByAccountEntryId(
			new long[] {RandomTestUtil.nextLong(), 0L});
	}

	@Test
	public void testCountByRoleId() throws Exception {
		_persistence.countByRoleId(RandomTestUtil.nextLong());

		_persistence.countByRoleId(0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		AccountRole newAccountRole = addAccountRole();

		AccountRole existingAccountRole = _persistence.findByPrimaryKey(
			newAccountRole.getPrimaryKey());

		Assert.assertEquals(existingAccountRole, newAccountRole);
	}

	@Test(expected = NoSuchRoleException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<AccountRole> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"AccountRole", "mvccVersion", true, "accountRoleId", true,
			"companyId", true, "accountEntryId", true, "roleId", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		AccountRole newAccountRole = addAccountRole();

		AccountRole existingAccountRole = _persistence.fetchByPrimaryKey(
			newAccountRole.getPrimaryKey());

		Assert.assertEquals(existingAccountRole, newAccountRole);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AccountRole missingAccountRole = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingAccountRole);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		AccountRole newAccountRole1 = addAccountRole();
		AccountRole newAccountRole2 = addAccountRole();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAccountRole1.getPrimaryKey());
		primaryKeys.add(newAccountRole2.getPrimaryKey());

		Map<Serializable, AccountRole> accountRoles =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, accountRoles.size());
		Assert.assertEquals(
			newAccountRole1, accountRoles.get(newAccountRole1.getPrimaryKey()));
		Assert.assertEquals(
			newAccountRole2, accountRoles.get(newAccountRole2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, AccountRole> accountRoles =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(accountRoles.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		AccountRole newAccountRole = addAccountRole();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAccountRole.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, AccountRole> accountRoles =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, accountRoles.size());
		Assert.assertEquals(
			newAccountRole, accountRoles.get(newAccountRole.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, AccountRole> accountRoles =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(accountRoles.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		AccountRole newAccountRole = addAccountRole();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAccountRole.getPrimaryKey());

		Map<Serializable, AccountRole> accountRoles =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, accountRoles.size());
		Assert.assertEquals(
			newAccountRole, accountRoles.get(newAccountRole.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			AccountRoleLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<AccountRole>() {

				@Override
				public void performAction(AccountRole accountRole) {
					Assert.assertNotNull(accountRole);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		AccountRole newAccountRole = addAccountRole();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AccountRole.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"accountRoleId", newAccountRole.getAccountRoleId()));

		List<AccountRole> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		AccountRole existingAccountRole = result.get(0);

		Assert.assertEquals(existingAccountRole, newAccountRole);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AccountRole.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"accountRoleId", RandomTestUtil.nextLong()));

		List<AccountRole> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		AccountRole newAccountRole = addAccountRole();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AccountRole.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("accountRoleId"));

		Object newAccountRoleId = newAccountRole.getAccountRoleId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"accountRoleId", new Object[] {newAccountRoleId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingAccountRoleId = result.get(0);

		Assert.assertEquals(existingAccountRoleId, newAccountRoleId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AccountRole.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("accountRoleId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"accountRoleId", new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		AccountRole newAccountRole = addAccountRole();

		_persistence.clearCache();

		_assertOriginalValues(
			_persistence.findByPrimaryKey(newAccountRole.getPrimaryKey()));
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

		AccountRole newAccountRole = addAccountRole();

		if (clearSession) {
			Session session = _persistence.openSession();

			session.flush();

			session.clear();
		}

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AccountRole.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"accountRoleId", newAccountRole.getAccountRoleId()));

		List<AccountRole> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		_assertOriginalValues(result.get(0));
	}

	private void _assertOriginalValues(AccountRole accountRole) {
		Assert.assertEquals(
			Long.valueOf(accountRole.getRoleId()),
			ReflectionTestUtil.<Long>invoke(
				accountRole, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "roleId"));
	}

	protected AccountRole addAccountRole() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AccountRole accountRole = _persistence.create(pk);

		accountRole.setMvccVersion(RandomTestUtil.nextLong());

		accountRole.setCompanyId(RandomTestUtil.nextLong());

		accountRole.setAccountEntryId(RandomTestUtil.nextLong());

		accountRole.setRoleId(RandomTestUtil.nextLong());

		_accountRoles.add(_persistence.update(accountRole));

		return accountRole;
	}

	private List<AccountRole> _accountRoles = new ArrayList<AccountRole>();
	private AccountRolePersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}