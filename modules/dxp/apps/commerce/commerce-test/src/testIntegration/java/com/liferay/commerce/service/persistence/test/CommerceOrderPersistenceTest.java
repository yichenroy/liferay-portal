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

package com.liferay.commerce.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.commerce.exception.NoSuchOrderException;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.service.CommerceOrderLocalServiceUtil;
import com.liferay.commerce.service.persistence.CommerceOrderPersistence;
import com.liferay.commerce.service.persistence.CommerceOrderUtil;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
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

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.junit.runner.RunWith;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @generated
 */
@RunWith(Arquillian.class)
public class CommerceOrderPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.commerce.service"));

	@Before
	public void setUp() {
		_persistence = CommerceOrderUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CommerceOrder> iterator = _commerceOrders.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceOrder commerceOrder = _persistence.create(pk);

		Assert.assertNotNull(commerceOrder);

		Assert.assertEquals(commerceOrder.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CommerceOrder newCommerceOrder = addCommerceOrder();

		_persistence.remove(newCommerceOrder);

		CommerceOrder existingCommerceOrder = _persistence.fetchByPrimaryKey(newCommerceOrder.getPrimaryKey());

		Assert.assertNull(existingCommerceOrder);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCommerceOrder();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceOrder newCommerceOrder = _persistence.create(pk);

		newCommerceOrder.setGroupId(RandomTestUtil.nextLong());

		newCommerceOrder.setCompanyId(RandomTestUtil.nextLong());

		newCommerceOrder.setUserId(RandomTestUtil.nextLong());

		newCommerceOrder.setUserName(RandomTestUtil.randomString());

		newCommerceOrder.setCreateDate(RandomTestUtil.nextDate());

		newCommerceOrder.setModifiedDate(RandomTestUtil.nextDate());

		newCommerceOrder.setOrderUserId(RandomTestUtil.nextLong());

		newCommerceOrder.setStatus(RandomTestUtil.nextInt());

		_commerceOrders.add(_persistence.update(newCommerceOrder));

		CommerceOrder existingCommerceOrder = _persistence.findByPrimaryKey(newCommerceOrder.getPrimaryKey());

		Assert.assertEquals(existingCommerceOrder.getCommerceOrderId(),
			newCommerceOrder.getCommerceOrderId());
		Assert.assertEquals(existingCommerceOrder.getGroupId(),
			newCommerceOrder.getGroupId());
		Assert.assertEquals(existingCommerceOrder.getCompanyId(),
			newCommerceOrder.getCompanyId());
		Assert.assertEquals(existingCommerceOrder.getUserId(),
			newCommerceOrder.getUserId());
		Assert.assertEquals(existingCommerceOrder.getUserName(),
			newCommerceOrder.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceOrder.getCreateDate()),
			Time.getShortTimestamp(newCommerceOrder.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingCommerceOrder.getModifiedDate()),
			Time.getShortTimestamp(newCommerceOrder.getModifiedDate()));
		Assert.assertEquals(existingCommerceOrder.getOrderUserId(),
			newCommerceOrder.getOrderUserId());
		Assert.assertEquals(existingCommerceOrder.getStatus(),
			newCommerceOrder.getStatus());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CommerceOrder newCommerceOrder = addCommerceOrder();

		CommerceOrder existingCommerceOrder = _persistence.findByPrimaryKey(newCommerceOrder.getPrimaryKey());

		Assert.assertEquals(existingCommerceOrder, newCommerceOrder);
	}

	@Test(expected = NoSuchOrderException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<CommerceOrder> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("CommerceOrder",
			"commerceOrderId", true, "groupId", true, "companyId", true,
			"userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "orderUserId", true, "status", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CommerceOrder newCommerceOrder = addCommerceOrder();

		CommerceOrder existingCommerceOrder = _persistence.fetchByPrimaryKey(newCommerceOrder.getPrimaryKey());

		Assert.assertEquals(existingCommerceOrder, newCommerceOrder);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceOrder missingCommerceOrder = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCommerceOrder);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		CommerceOrder newCommerceOrder1 = addCommerceOrder();
		CommerceOrder newCommerceOrder2 = addCommerceOrder();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceOrder1.getPrimaryKey());
		primaryKeys.add(newCommerceOrder2.getPrimaryKey());

		Map<Serializable, CommerceOrder> commerceOrders = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, commerceOrders.size());
		Assert.assertEquals(newCommerceOrder1,
			commerceOrders.get(newCommerceOrder1.getPrimaryKey()));
		Assert.assertEquals(newCommerceOrder2,
			commerceOrders.get(newCommerceOrder2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CommerceOrder> commerceOrders = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceOrders.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		CommerceOrder newCommerceOrder = addCommerceOrder();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceOrder.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CommerceOrder> commerceOrders = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceOrders.size());
		Assert.assertEquals(newCommerceOrder,
			commerceOrders.get(newCommerceOrder.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CommerceOrder> commerceOrders = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commerceOrders.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		CommerceOrder newCommerceOrder = addCommerceOrder();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceOrder.getPrimaryKey());

		Map<Serializable, CommerceOrder> commerceOrders = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commerceOrders.size());
		Assert.assertEquals(newCommerceOrder,
			commerceOrders.get(newCommerceOrder.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = CommerceOrderLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<CommerceOrder>() {
				@Override
				public void performAction(CommerceOrder commerceOrder) {
					Assert.assertNotNull(commerceOrder);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		CommerceOrder newCommerceOrder = addCommerceOrder();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceOrder.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("commerceOrderId",
				newCommerceOrder.getCommerceOrderId()));

		List<CommerceOrder> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		CommerceOrder existingCommerceOrder = result.get(0);

		Assert.assertEquals(existingCommerceOrder, newCommerceOrder);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceOrder.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("commerceOrderId",
				RandomTestUtil.nextLong()));

		List<CommerceOrder> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		CommerceOrder newCommerceOrder = addCommerceOrder();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceOrder.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"commerceOrderId"));

		Object newCommerceOrderId = newCommerceOrder.getCommerceOrderId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("commerceOrderId",
				new Object[] { newCommerceOrderId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCommerceOrderId = result.get(0);

		Assert.assertEquals(existingCommerceOrderId, newCommerceOrderId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CommerceOrder.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"commerceOrderId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("commerceOrderId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected CommerceOrder addCommerceOrder() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceOrder commerceOrder = _persistence.create(pk);

		commerceOrder.setGroupId(RandomTestUtil.nextLong());

		commerceOrder.setCompanyId(RandomTestUtil.nextLong());

		commerceOrder.setUserId(RandomTestUtil.nextLong());

		commerceOrder.setUserName(RandomTestUtil.randomString());

		commerceOrder.setCreateDate(RandomTestUtil.nextDate());

		commerceOrder.setModifiedDate(RandomTestUtil.nextDate());

		commerceOrder.setOrderUserId(RandomTestUtil.nextLong());

		commerceOrder.setStatus(RandomTestUtil.nextInt());

		_commerceOrders.add(_persistence.update(commerceOrder));

		return commerceOrder;
	}

	private List<CommerceOrder> _commerceOrders = new ArrayList<CommerceOrder>();
	private CommerceOrderPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}