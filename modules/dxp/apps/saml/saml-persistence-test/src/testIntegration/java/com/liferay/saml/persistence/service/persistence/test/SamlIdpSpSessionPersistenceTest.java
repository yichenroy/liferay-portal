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

package com.liferay.saml.persistence.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;

import com.liferay.saml.persistence.exception.NoSuchIdpSpSessionException;
import com.liferay.saml.persistence.model.SamlIdpSpSession;
import com.liferay.saml.persistence.service.SamlIdpSpSessionLocalServiceUtil;
import com.liferay.saml.persistence.service.persistence.SamlIdpSpSessionPersistence;
import com.liferay.saml.persistence.service.persistence.SamlIdpSpSessionUtil;

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
import java.util.Objects;
import java.util.Set;

/**
 * @generated
 */
@RunWith(Arquillian.class)
public class SamlIdpSpSessionPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.saml.persistence.service"));

	@Before
	public void setUp() {
		_persistence = SamlIdpSpSessionUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<SamlIdpSpSession> iterator = _samlIdpSpSessions.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SamlIdpSpSession samlIdpSpSession = _persistence.create(pk);

		Assert.assertNotNull(samlIdpSpSession);

		Assert.assertEquals(samlIdpSpSession.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		SamlIdpSpSession newSamlIdpSpSession = addSamlIdpSpSession();

		_persistence.remove(newSamlIdpSpSession);

		SamlIdpSpSession existingSamlIdpSpSession = _persistence.fetchByPrimaryKey(newSamlIdpSpSession.getPrimaryKey());

		Assert.assertNull(existingSamlIdpSpSession);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addSamlIdpSpSession();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SamlIdpSpSession newSamlIdpSpSession = _persistence.create(pk);

		newSamlIdpSpSession.setCompanyId(RandomTestUtil.nextLong());

		newSamlIdpSpSession.setUserId(RandomTestUtil.nextLong());

		newSamlIdpSpSession.setUserName(RandomTestUtil.randomString());

		newSamlIdpSpSession.setCreateDate(RandomTestUtil.nextDate());

		newSamlIdpSpSession.setModifiedDate(RandomTestUtil.nextDate());

		newSamlIdpSpSession.setSamlIdpSsoSessionId(RandomTestUtil.nextLong());

		newSamlIdpSpSession.setSamlSpEntityId(RandomTestUtil.randomString());

		newSamlIdpSpSession.setNameIdFormat(RandomTestUtil.randomString());

		newSamlIdpSpSession.setNameIdValue(RandomTestUtil.randomString());

		_samlIdpSpSessions.add(_persistence.update(newSamlIdpSpSession));

		SamlIdpSpSession existingSamlIdpSpSession = _persistence.findByPrimaryKey(newSamlIdpSpSession.getPrimaryKey());

		Assert.assertEquals(existingSamlIdpSpSession.getSamlIdpSpSessionId(),
			newSamlIdpSpSession.getSamlIdpSpSessionId());
		Assert.assertEquals(existingSamlIdpSpSession.getCompanyId(),
			newSamlIdpSpSession.getCompanyId());
		Assert.assertEquals(existingSamlIdpSpSession.getUserId(),
			newSamlIdpSpSession.getUserId());
		Assert.assertEquals(existingSamlIdpSpSession.getUserName(),
			newSamlIdpSpSession.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingSamlIdpSpSession.getCreateDate()),
			Time.getShortTimestamp(newSamlIdpSpSession.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingSamlIdpSpSession.getModifiedDate()),
			Time.getShortTimestamp(newSamlIdpSpSession.getModifiedDate()));
		Assert.assertEquals(existingSamlIdpSpSession.getSamlIdpSsoSessionId(),
			newSamlIdpSpSession.getSamlIdpSsoSessionId());
		Assert.assertEquals(existingSamlIdpSpSession.getSamlSpEntityId(),
			newSamlIdpSpSession.getSamlSpEntityId());
		Assert.assertEquals(existingSamlIdpSpSession.getNameIdFormat(),
			newSamlIdpSpSession.getNameIdFormat());
		Assert.assertEquals(existingSamlIdpSpSession.getNameIdValue(),
			newSamlIdpSpSession.getNameIdValue());
	}

	@Test
	public void testCountByCreateDate() throws Exception {
		_persistence.countByCreateDate(RandomTestUtil.nextDate());

		_persistence.countByCreateDate(RandomTestUtil.nextDate());
	}

	@Test
	public void testCountBySamlIdpSsoSessionId() throws Exception {
		_persistence.countBySamlIdpSsoSessionId(RandomTestUtil.nextLong());

		_persistence.countBySamlIdpSsoSessionId(0L);
	}

	@Test
	public void testCountBySISSI_SSEI() throws Exception {
		_persistence.countBySISSI_SSEI(RandomTestUtil.nextLong(),
			StringPool.BLANK);

		_persistence.countBySISSI_SSEI(0L, StringPool.NULL);

		_persistence.countBySISSI_SSEI(0L, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		SamlIdpSpSession newSamlIdpSpSession = addSamlIdpSpSession();

		SamlIdpSpSession existingSamlIdpSpSession = _persistence.findByPrimaryKey(newSamlIdpSpSession.getPrimaryKey());

		Assert.assertEquals(existingSamlIdpSpSession, newSamlIdpSpSession);
	}

	@Test(expected = NoSuchIdpSpSessionException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<SamlIdpSpSession> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("SamlIdpSpSession",
			"samlIdpSpSessionId", true, "companyId", true, "userId", true,
			"userName", true, "createDate", true, "modifiedDate", true,
			"samlIdpSsoSessionId", true, "samlSpEntityId", true,
			"nameIdFormat", true, "nameIdValue", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		SamlIdpSpSession newSamlIdpSpSession = addSamlIdpSpSession();

		SamlIdpSpSession existingSamlIdpSpSession = _persistence.fetchByPrimaryKey(newSamlIdpSpSession.getPrimaryKey());

		Assert.assertEquals(existingSamlIdpSpSession, newSamlIdpSpSession);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SamlIdpSpSession missingSamlIdpSpSession = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingSamlIdpSpSession);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		SamlIdpSpSession newSamlIdpSpSession1 = addSamlIdpSpSession();
		SamlIdpSpSession newSamlIdpSpSession2 = addSamlIdpSpSession();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newSamlIdpSpSession1.getPrimaryKey());
		primaryKeys.add(newSamlIdpSpSession2.getPrimaryKey());

		Map<Serializable, SamlIdpSpSession> samlIdpSpSessions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, samlIdpSpSessions.size());
		Assert.assertEquals(newSamlIdpSpSession1,
			samlIdpSpSessions.get(newSamlIdpSpSession1.getPrimaryKey()));
		Assert.assertEquals(newSamlIdpSpSession2,
			samlIdpSpSessions.get(newSamlIdpSpSession2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, SamlIdpSpSession> samlIdpSpSessions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(samlIdpSpSessions.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		SamlIdpSpSession newSamlIdpSpSession = addSamlIdpSpSession();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newSamlIdpSpSession.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, SamlIdpSpSession> samlIdpSpSessions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, samlIdpSpSessions.size());
		Assert.assertEquals(newSamlIdpSpSession,
			samlIdpSpSessions.get(newSamlIdpSpSession.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, SamlIdpSpSession> samlIdpSpSessions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(samlIdpSpSessions.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		SamlIdpSpSession newSamlIdpSpSession = addSamlIdpSpSession();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newSamlIdpSpSession.getPrimaryKey());

		Map<Serializable, SamlIdpSpSession> samlIdpSpSessions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, samlIdpSpSessions.size());
		Assert.assertEquals(newSamlIdpSpSession,
			samlIdpSpSessions.get(newSamlIdpSpSession.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = SamlIdpSpSessionLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<SamlIdpSpSession>() {
				@Override
				public void performAction(SamlIdpSpSession samlIdpSpSession) {
					Assert.assertNotNull(samlIdpSpSession);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		SamlIdpSpSession newSamlIdpSpSession = addSamlIdpSpSession();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SamlIdpSpSession.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("samlIdpSpSessionId",
				newSamlIdpSpSession.getSamlIdpSpSessionId()));

		List<SamlIdpSpSession> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		SamlIdpSpSession existingSamlIdpSpSession = result.get(0);

		Assert.assertEquals(existingSamlIdpSpSession, newSamlIdpSpSession);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SamlIdpSpSession.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("samlIdpSpSessionId",
				RandomTestUtil.nextLong()));

		List<SamlIdpSpSession> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		SamlIdpSpSession newSamlIdpSpSession = addSamlIdpSpSession();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SamlIdpSpSession.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"samlIdpSpSessionId"));

		Object newSamlIdpSpSessionId = newSamlIdpSpSession.getSamlIdpSpSessionId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("samlIdpSpSessionId",
				new Object[] { newSamlIdpSpSessionId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingSamlIdpSpSessionId = result.get(0);

		Assert.assertEquals(existingSamlIdpSpSessionId, newSamlIdpSpSessionId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SamlIdpSpSession.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"samlIdpSpSessionId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("samlIdpSpSessionId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		SamlIdpSpSession newSamlIdpSpSession = addSamlIdpSpSession();

		_persistence.clearCache();

		SamlIdpSpSession existingSamlIdpSpSession = _persistence.findByPrimaryKey(newSamlIdpSpSession.getPrimaryKey());

		Assert.assertEquals(Long.valueOf(
				existingSamlIdpSpSession.getSamlIdpSsoSessionId()),
			ReflectionTestUtil.<Long>invoke(existingSamlIdpSpSession,
				"getOriginalSamlIdpSsoSessionId", new Class<?>[0]));
		Assert.assertTrue(Objects.equals(
				existingSamlIdpSpSession.getSamlSpEntityId(),
				ReflectionTestUtil.invoke(existingSamlIdpSpSession,
					"getOriginalSamlSpEntityId", new Class<?>[0])));
	}

	protected SamlIdpSpSession addSamlIdpSpSession() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SamlIdpSpSession samlIdpSpSession = _persistence.create(pk);

		samlIdpSpSession.setCompanyId(RandomTestUtil.nextLong());

		samlIdpSpSession.setUserId(RandomTestUtil.nextLong());

		samlIdpSpSession.setUserName(RandomTestUtil.randomString());

		samlIdpSpSession.setCreateDate(RandomTestUtil.nextDate());

		samlIdpSpSession.setModifiedDate(RandomTestUtil.nextDate());

		samlIdpSpSession.setSamlIdpSsoSessionId(RandomTestUtil.nextLong());

		samlIdpSpSession.setSamlSpEntityId(RandomTestUtil.randomString());

		samlIdpSpSession.setNameIdFormat(RandomTestUtil.randomString());

		samlIdpSpSession.setNameIdValue(RandomTestUtil.randomString());

		_samlIdpSpSessions.add(_persistence.update(samlIdpSpSession));

		return samlIdpSpSession;
	}

	private List<SamlIdpSpSession> _samlIdpSpSessions = new ArrayList<SamlIdpSpSession>();
	private SamlIdpSpSessionPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}