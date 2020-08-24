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

import com.liferay.account.exception.NoSuchGroupException;
import com.liferay.account.model.AccountGroup;
import com.liferay.account.service.AccountGroupLocalServiceUtil;
import com.liferay.account.service.persistence.AccountGroupPersistence;
import com.liferay.account.service.persistence.AccountGroupUtil;
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
public class AccountGroupPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.account.service"));

	@Before
	public void setUp() {
		_persistence = AccountGroupUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<AccountGroup> iterator = _accountGroups.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AccountGroup accountGroup = _persistence.create(pk);

		Assert.assertNotNull(accountGroup);

		Assert.assertEquals(accountGroup.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		AccountGroup newAccountGroup = addAccountGroup();

		_persistence.remove(newAccountGroup);

		AccountGroup existingAccountGroup = _persistence.fetchByPrimaryKey(
			newAccountGroup.getPrimaryKey());

		Assert.assertNull(existingAccountGroup);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addAccountGroup();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AccountGroup newAccountGroup = _persistence.create(pk);

		newAccountGroup.setMvccVersion(RandomTestUtil.nextLong());

		newAccountGroup.setExternalReferenceCode(RandomTestUtil.randomString());

		newAccountGroup.setCompanyId(RandomTestUtil.nextLong());

		newAccountGroup.setUserId(RandomTestUtil.nextLong());

		newAccountGroup.setUserName(RandomTestUtil.randomString());

		newAccountGroup.setCreateDate(RandomTestUtil.nextDate());

		newAccountGroup.setModifiedDate(RandomTestUtil.nextDate());

		newAccountGroup.setName(RandomTestUtil.randomString());

		newAccountGroup.setDescription(RandomTestUtil.randomString());

		_accountGroups.add(_persistence.update(newAccountGroup));

		AccountGroup existingAccountGroup = _persistence.findByPrimaryKey(
			newAccountGroup.getPrimaryKey());

		Assert.assertEquals(
			existingAccountGroup.getMvccVersion(),
			newAccountGroup.getMvccVersion());
		Assert.assertEquals(
			existingAccountGroup.getExternalReferenceCode(),
			newAccountGroup.getExternalReferenceCode());
		Assert.assertEquals(
			existingAccountGroup.getAccountGroupId(),
			newAccountGroup.getAccountGroupId());
		Assert.assertEquals(
			existingAccountGroup.getCompanyId(),
			newAccountGroup.getCompanyId());
		Assert.assertEquals(
			existingAccountGroup.getUserId(), newAccountGroup.getUserId());
		Assert.assertEquals(
			existingAccountGroup.getUserName(), newAccountGroup.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(existingAccountGroup.getCreateDate()),
			Time.getShortTimestamp(newAccountGroup.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(existingAccountGroup.getModifiedDate()),
			Time.getShortTimestamp(newAccountGroup.getModifiedDate()));
		Assert.assertEquals(
			existingAccountGroup.getName(), newAccountGroup.getName());
		Assert.assertEquals(
			existingAccountGroup.getDescription(),
			newAccountGroup.getDescription());
	}

	@Test
	public void testCountByCompanyId() throws Exception {
		_persistence.countByCompanyId(RandomTestUtil.nextLong());

		_persistence.countByCompanyId(0L);
	}

	@Test
	public void testCountByC_ERC() throws Exception {
		_persistence.countByC_ERC(RandomTestUtil.nextLong(), "");

		_persistence.countByC_ERC(0L, "null");

		_persistence.countByC_ERC(0L, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		AccountGroup newAccountGroup = addAccountGroup();

		AccountGroup existingAccountGroup = _persistence.findByPrimaryKey(
			newAccountGroup.getPrimaryKey());

		Assert.assertEquals(existingAccountGroup, newAccountGroup);
	}

	@Test(expected = NoSuchGroupException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<AccountGroup> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"AccountGroup", "mvccVersion", true, "externalReferenceCode", true,
			"accountGroupId", true, "companyId", true, "userId", true,
			"userName", true, "createDate", true, "modifiedDate", true, "name",
			true, "description", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		AccountGroup newAccountGroup = addAccountGroup();

		AccountGroup existingAccountGroup = _persistence.fetchByPrimaryKey(
			newAccountGroup.getPrimaryKey());

		Assert.assertEquals(existingAccountGroup, newAccountGroup);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AccountGroup missingAccountGroup = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingAccountGroup);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		AccountGroup newAccountGroup1 = addAccountGroup();
		AccountGroup newAccountGroup2 = addAccountGroup();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAccountGroup1.getPrimaryKey());
		primaryKeys.add(newAccountGroup2.getPrimaryKey());

		Map<Serializable, AccountGroup> accountGroups =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, accountGroups.size());
		Assert.assertEquals(
			newAccountGroup1,
			accountGroups.get(newAccountGroup1.getPrimaryKey()));
		Assert.assertEquals(
			newAccountGroup2,
			accountGroups.get(newAccountGroup2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, AccountGroup> accountGroups =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(accountGroups.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		AccountGroup newAccountGroup = addAccountGroup();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAccountGroup.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, AccountGroup> accountGroups =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, accountGroups.size());
		Assert.assertEquals(
			newAccountGroup,
			accountGroups.get(newAccountGroup.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, AccountGroup> accountGroups =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(accountGroups.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		AccountGroup newAccountGroup = addAccountGroup();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAccountGroup.getPrimaryKey());

		Map<Serializable, AccountGroup> accountGroups =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, accountGroups.size());
		Assert.assertEquals(
			newAccountGroup,
			accountGroups.get(newAccountGroup.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			AccountGroupLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<AccountGroup>() {

				@Override
				public void performAction(AccountGroup accountGroup) {
					Assert.assertNotNull(accountGroup);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		AccountGroup newAccountGroup = addAccountGroup();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AccountGroup.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"accountGroupId", newAccountGroup.getAccountGroupId()));

		List<AccountGroup> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		AccountGroup existingAccountGroup = result.get(0);

		Assert.assertEquals(existingAccountGroup, newAccountGroup);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AccountGroup.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"accountGroupId", RandomTestUtil.nextLong()));

		List<AccountGroup> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		AccountGroup newAccountGroup = addAccountGroup();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AccountGroup.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("accountGroupId"));

		Object newAccountGroupId = newAccountGroup.getAccountGroupId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"accountGroupId", new Object[] {newAccountGroupId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingAccountGroupId = result.get(0);

		Assert.assertEquals(existingAccountGroupId, newAccountGroupId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AccountGroup.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("accountGroupId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"accountGroupId", new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		AccountGroup newAccountGroup = addAccountGroup();

		_persistence.clearCache();

		_assertOriginalValues(
			_persistence.findByPrimaryKey(newAccountGroup.getPrimaryKey()));
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

		AccountGroup newAccountGroup = addAccountGroup();

		if (clearSession) {
			Session session = _persistence.openSession();

			session.flush();

			session.clear();
		}

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AccountGroup.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"accountGroupId", newAccountGroup.getAccountGroupId()));

		List<AccountGroup> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		_assertOriginalValues(result.get(0));
	}

	private void _assertOriginalValues(AccountGroup accountGroup) {
		Assert.assertEquals(
			Long.valueOf(accountGroup.getCompanyId()),
			ReflectionTestUtil.<Long>invoke(
				accountGroup, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "companyId"));
		Assert.assertEquals(
			accountGroup.getExternalReferenceCode(),
			ReflectionTestUtil.invoke(
				accountGroup, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "externalReferenceCode"));
	}

	protected AccountGroup addAccountGroup() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AccountGroup accountGroup = _persistence.create(pk);

		accountGroup.setMvccVersion(RandomTestUtil.nextLong());

		accountGroup.setExternalReferenceCode(RandomTestUtil.randomString());

		accountGroup.setCompanyId(RandomTestUtil.nextLong());

		accountGroup.setUserId(RandomTestUtil.nextLong());

		accountGroup.setUserName(RandomTestUtil.randomString());

		accountGroup.setCreateDate(RandomTestUtil.nextDate());

		accountGroup.setModifiedDate(RandomTestUtil.nextDate());

		accountGroup.setName(RandomTestUtil.randomString());

		accountGroup.setDescription(RandomTestUtil.randomString());

		_accountGroups.add(_persistence.update(accountGroup));

		return accountGroup;
	}

	private List<AccountGroup> _accountGroups = new ArrayList<AccountGroup>();
	private AccountGroupPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}