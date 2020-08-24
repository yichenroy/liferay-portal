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

import com.liferay.account.exception.NoSuchEntryException;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalServiceUtil;
import com.liferay.account.service.persistence.AccountEntryPersistence;
import com.liferay.account.service.persistence.AccountEntryUtil;
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
public class AccountEntryPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.account.service"));

	@Before
	public void setUp() {
		_persistence = AccountEntryUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<AccountEntry> iterator = _accountEntries.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AccountEntry accountEntry = _persistence.create(pk);

		Assert.assertNotNull(accountEntry);

		Assert.assertEquals(accountEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		AccountEntry newAccountEntry = addAccountEntry();

		_persistence.remove(newAccountEntry);

		AccountEntry existingAccountEntry = _persistence.fetchByPrimaryKey(
			newAccountEntry.getPrimaryKey());

		Assert.assertNull(existingAccountEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addAccountEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AccountEntry newAccountEntry = _persistence.create(pk);

		newAccountEntry.setMvccVersion(RandomTestUtil.nextLong());

		newAccountEntry.setExternalReferenceCode(RandomTestUtil.randomString());

		newAccountEntry.setCompanyId(RandomTestUtil.nextLong());

		newAccountEntry.setUserId(RandomTestUtil.nextLong());

		newAccountEntry.setUserName(RandomTestUtil.randomString());

		newAccountEntry.setCreateDate(RandomTestUtil.nextDate());

		newAccountEntry.setModifiedDate(RandomTestUtil.nextDate());

		newAccountEntry.setParentAccountEntryId(RandomTestUtil.nextLong());

		newAccountEntry.setName(RandomTestUtil.randomString());

		newAccountEntry.setDescription(RandomTestUtil.randomString());

		newAccountEntry.setDomains(RandomTestUtil.randomString());

		newAccountEntry.setLogoId(RandomTestUtil.nextLong());

		newAccountEntry.setTaxIdNumber(RandomTestUtil.randomString());

		newAccountEntry.setType(RandomTestUtil.randomString());

		newAccountEntry.setStatus(RandomTestUtil.nextInt());

		_accountEntries.add(_persistence.update(newAccountEntry));

		AccountEntry existingAccountEntry = _persistence.findByPrimaryKey(
			newAccountEntry.getPrimaryKey());

		Assert.assertEquals(
			existingAccountEntry.getMvccVersion(),
			newAccountEntry.getMvccVersion());
		Assert.assertEquals(
			existingAccountEntry.getExternalReferenceCode(),
			newAccountEntry.getExternalReferenceCode());
		Assert.assertEquals(
			existingAccountEntry.getAccountEntryId(),
			newAccountEntry.getAccountEntryId());
		Assert.assertEquals(
			existingAccountEntry.getCompanyId(),
			newAccountEntry.getCompanyId());
		Assert.assertEquals(
			existingAccountEntry.getUserId(), newAccountEntry.getUserId());
		Assert.assertEquals(
			existingAccountEntry.getUserName(), newAccountEntry.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(existingAccountEntry.getCreateDate()),
			Time.getShortTimestamp(newAccountEntry.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(existingAccountEntry.getModifiedDate()),
			Time.getShortTimestamp(newAccountEntry.getModifiedDate()));
		Assert.assertEquals(
			existingAccountEntry.getParentAccountEntryId(),
			newAccountEntry.getParentAccountEntryId());
		Assert.assertEquals(
			existingAccountEntry.getName(), newAccountEntry.getName());
		Assert.assertEquals(
			existingAccountEntry.getDescription(),
			newAccountEntry.getDescription());
		Assert.assertEquals(
			existingAccountEntry.getDomains(), newAccountEntry.getDomains());
		Assert.assertEquals(
			existingAccountEntry.getLogoId(), newAccountEntry.getLogoId());
		Assert.assertEquals(
			existingAccountEntry.getTaxIdNumber(),
			newAccountEntry.getTaxIdNumber());
		Assert.assertEquals(
			existingAccountEntry.getType(), newAccountEntry.getType());
		Assert.assertEquals(
			existingAccountEntry.getStatus(), newAccountEntry.getStatus());
	}

	@Test
	public void testCountByCompanyId() throws Exception {
		_persistence.countByCompanyId(RandomTestUtil.nextLong());

		_persistence.countByCompanyId(0L);
	}

	@Test
	public void testCountByC_S() throws Exception {
		_persistence.countByC_S(
			RandomTestUtil.nextLong(), RandomTestUtil.nextInt());

		_persistence.countByC_S(0L, 0);
	}

	@Test
	public void testCountByC_ERC() throws Exception {
		_persistence.countByC_ERC(RandomTestUtil.nextLong(), "");

		_persistence.countByC_ERC(0L, "null");

		_persistence.countByC_ERC(0L, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		AccountEntry newAccountEntry = addAccountEntry();

		AccountEntry existingAccountEntry = _persistence.findByPrimaryKey(
			newAccountEntry.getPrimaryKey());

		Assert.assertEquals(existingAccountEntry, newAccountEntry);
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

	protected OrderByComparator<AccountEntry> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"AccountEntry", "mvccVersion", true, "externalReferenceCode", true,
			"accountEntryId", true, "companyId", true, "userId", true,
			"userName", true, "createDate", true, "modifiedDate", true,
			"parentAccountEntryId", true, "name", true, "description", true,
			"domains", true, "logoId", true, "taxIdNumber", true, "type", true,
			"status", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		AccountEntry newAccountEntry = addAccountEntry();

		AccountEntry existingAccountEntry = _persistence.fetchByPrimaryKey(
			newAccountEntry.getPrimaryKey());

		Assert.assertEquals(existingAccountEntry, newAccountEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AccountEntry missingAccountEntry = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingAccountEntry);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		AccountEntry newAccountEntry1 = addAccountEntry();
		AccountEntry newAccountEntry2 = addAccountEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAccountEntry1.getPrimaryKey());
		primaryKeys.add(newAccountEntry2.getPrimaryKey());

		Map<Serializable, AccountEntry> accountEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, accountEntries.size());
		Assert.assertEquals(
			newAccountEntry1,
			accountEntries.get(newAccountEntry1.getPrimaryKey()));
		Assert.assertEquals(
			newAccountEntry2,
			accountEntries.get(newAccountEntry2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, AccountEntry> accountEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(accountEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		AccountEntry newAccountEntry = addAccountEntry();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAccountEntry.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, AccountEntry> accountEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, accountEntries.size());
		Assert.assertEquals(
			newAccountEntry,
			accountEntries.get(newAccountEntry.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, AccountEntry> accountEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(accountEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		AccountEntry newAccountEntry = addAccountEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAccountEntry.getPrimaryKey());

		Map<Serializable, AccountEntry> accountEntries =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, accountEntries.size());
		Assert.assertEquals(
			newAccountEntry,
			accountEntries.get(newAccountEntry.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			AccountEntryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<AccountEntry>() {

				@Override
				public void performAction(AccountEntry accountEntry) {
					Assert.assertNotNull(accountEntry);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		AccountEntry newAccountEntry = addAccountEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AccountEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"accountEntryId", newAccountEntry.getAccountEntryId()));

		List<AccountEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		AccountEntry existingAccountEntry = result.get(0);

		Assert.assertEquals(existingAccountEntry, newAccountEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AccountEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"accountEntryId", RandomTestUtil.nextLong()));

		List<AccountEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		AccountEntry newAccountEntry = addAccountEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AccountEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("accountEntryId"));

		Object newAccountEntryId = newAccountEntry.getAccountEntryId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"accountEntryId", new Object[] {newAccountEntryId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingAccountEntryId = result.get(0);

		Assert.assertEquals(existingAccountEntryId, newAccountEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AccountEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("accountEntryId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"accountEntryId", new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		AccountEntry newAccountEntry = addAccountEntry();

		_persistence.clearCache();

		_assertOriginalValues(
			_persistence.findByPrimaryKey(newAccountEntry.getPrimaryKey()));
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

		AccountEntry newAccountEntry = addAccountEntry();

		if (clearSession) {
			Session session = _persistence.openSession();

			session.flush();

			session.clear();
		}

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AccountEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"accountEntryId", newAccountEntry.getAccountEntryId()));

		List<AccountEntry> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		_assertOriginalValues(result.get(0));
	}

	private void _assertOriginalValues(AccountEntry accountEntry) {
		Assert.assertEquals(
			Long.valueOf(accountEntry.getCompanyId()),
			ReflectionTestUtil.<Long>invoke(
				accountEntry, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "companyId"));
		Assert.assertEquals(
			accountEntry.getExternalReferenceCode(),
			ReflectionTestUtil.invoke(
				accountEntry, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "externalReferenceCode"));
	}

	protected AccountEntry addAccountEntry() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AccountEntry accountEntry = _persistence.create(pk);

		accountEntry.setMvccVersion(RandomTestUtil.nextLong());

		accountEntry.setExternalReferenceCode(RandomTestUtil.randomString());

		accountEntry.setCompanyId(RandomTestUtil.nextLong());

		accountEntry.setUserId(RandomTestUtil.nextLong());

		accountEntry.setUserName(RandomTestUtil.randomString());

		accountEntry.setCreateDate(RandomTestUtil.nextDate());

		accountEntry.setModifiedDate(RandomTestUtil.nextDate());

		accountEntry.setParentAccountEntryId(RandomTestUtil.nextLong());

		accountEntry.setName(RandomTestUtil.randomString());

		accountEntry.setDescription(RandomTestUtil.randomString());

		accountEntry.setDomains(RandomTestUtil.randomString());

		accountEntry.setLogoId(RandomTestUtil.nextLong());

		accountEntry.setTaxIdNumber(RandomTestUtil.randomString());

		accountEntry.setType(RandomTestUtil.randomString());

		accountEntry.setStatus(RandomTestUtil.nextInt());

		_accountEntries.add(_persistence.update(accountEntry));

		return accountEntry;
	}

	private List<AccountEntry> _accountEntries = new ArrayList<AccountEntry>();
	private AccountEntryPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}