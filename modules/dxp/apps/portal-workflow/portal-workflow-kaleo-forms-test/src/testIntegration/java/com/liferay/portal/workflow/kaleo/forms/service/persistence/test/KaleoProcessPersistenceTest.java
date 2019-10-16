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

package com.liferay.portal.workflow.kaleo.forms.service.persistence.test;

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
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;
import com.liferay.portal.workflow.kaleo.forms.exception.NoSuchKaleoProcessException;
import com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess;
import com.liferay.portal.workflow.kaleo.forms.service.KaleoProcessLocalServiceUtil;
import com.liferay.portal.workflow.kaleo.forms.service.persistence.KaleoProcessPersistence;
import com.liferay.portal.workflow.kaleo.forms.service.persistence.KaleoProcessUtil;

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
public class KaleoProcessPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.portal.workflow.kaleo.forms.service"));

	@Before
	public void setUp() {
		_persistence = KaleoProcessUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<KaleoProcess> iterator = _kaleoProcesses.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		KaleoProcess kaleoProcess = _persistence.create(pk);

		Assert.assertNotNull(kaleoProcess);

		Assert.assertEquals(kaleoProcess.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		KaleoProcess newKaleoProcess = addKaleoProcess();

		_persistence.remove(newKaleoProcess);

		KaleoProcess existingKaleoProcess = _persistence.fetchByPrimaryKey(newKaleoProcess.getPrimaryKey());

		Assert.assertNull(existingKaleoProcess);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addKaleoProcess();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		KaleoProcess newKaleoProcess = _persistence.create(pk);

		newKaleoProcess.setUuid(RandomTestUtil.randomString());

		newKaleoProcess.setGroupId(RandomTestUtil.nextLong());

		newKaleoProcess.setCompanyId(RandomTestUtil.nextLong());

		newKaleoProcess.setUserId(RandomTestUtil.nextLong());

		newKaleoProcess.setUserName(RandomTestUtil.randomString());

		newKaleoProcess.setCreateDate(RandomTestUtil.nextDate());

		newKaleoProcess.setModifiedDate(RandomTestUtil.nextDate());

		newKaleoProcess.setDDLRecordSetId(RandomTestUtil.nextLong());

		newKaleoProcess.setDDMTemplateId(RandomTestUtil.nextLong());

		newKaleoProcess.setWorkflowDefinitionName(RandomTestUtil.randomString());

		newKaleoProcess.setWorkflowDefinitionVersion(RandomTestUtil.nextInt());

		_kaleoProcesses.add(_persistence.update(newKaleoProcess));

		KaleoProcess existingKaleoProcess = _persistence.findByPrimaryKey(newKaleoProcess.getPrimaryKey());

		Assert.assertEquals(existingKaleoProcess.getUuid(),
			newKaleoProcess.getUuid());
		Assert.assertEquals(existingKaleoProcess.getKaleoProcessId(),
			newKaleoProcess.getKaleoProcessId());
		Assert.assertEquals(existingKaleoProcess.getGroupId(),
			newKaleoProcess.getGroupId());
		Assert.assertEquals(existingKaleoProcess.getCompanyId(),
			newKaleoProcess.getCompanyId());
		Assert.assertEquals(existingKaleoProcess.getUserId(),
			newKaleoProcess.getUserId());
		Assert.assertEquals(existingKaleoProcess.getUserName(),
			newKaleoProcess.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingKaleoProcess.getCreateDate()),
			Time.getShortTimestamp(newKaleoProcess.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingKaleoProcess.getModifiedDate()),
			Time.getShortTimestamp(newKaleoProcess.getModifiedDate()));
		Assert.assertEquals(existingKaleoProcess.getDDLRecordSetId(),
			newKaleoProcess.getDDLRecordSetId());
		Assert.assertEquals(existingKaleoProcess.getDDMTemplateId(),
			newKaleoProcess.getDDMTemplateId());
		Assert.assertEquals(existingKaleoProcess.getWorkflowDefinitionName(),
			newKaleoProcess.getWorkflowDefinitionName());
		Assert.assertEquals(existingKaleoProcess.getWorkflowDefinitionVersion(),
			newKaleoProcess.getWorkflowDefinitionVersion());
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
	public void testCountByDDLRecordSetId() throws Exception {
		_persistence.countByDDLRecordSetId(RandomTestUtil.nextLong());

		_persistence.countByDDLRecordSetId(0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		KaleoProcess newKaleoProcess = addKaleoProcess();

		KaleoProcess existingKaleoProcess = _persistence.findByPrimaryKey(newKaleoProcess.getPrimaryKey());

		Assert.assertEquals(existingKaleoProcess, newKaleoProcess);
	}

	@Test(expected = NoSuchKaleoProcessException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	@Test
	public void testFilterFindByGroupId() throws Exception {
		_persistence.filterFindByGroupId(0, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<KaleoProcess> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("KaleoProcess", "uuid",
			true, "kaleoProcessId", true, "groupId", true, "companyId", true,
			"userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "DDLRecordSetId", true, "DDMTemplateId",
			true, "workflowDefinitionName", true, "workflowDefinitionVersion",
			true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		KaleoProcess newKaleoProcess = addKaleoProcess();

		KaleoProcess existingKaleoProcess = _persistence.fetchByPrimaryKey(newKaleoProcess.getPrimaryKey());

		Assert.assertEquals(existingKaleoProcess, newKaleoProcess);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		KaleoProcess missingKaleoProcess = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingKaleoProcess);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		KaleoProcess newKaleoProcess1 = addKaleoProcess();
		KaleoProcess newKaleoProcess2 = addKaleoProcess();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newKaleoProcess1.getPrimaryKey());
		primaryKeys.add(newKaleoProcess2.getPrimaryKey());

		Map<Serializable, KaleoProcess> kaleoProcesses = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, kaleoProcesses.size());
		Assert.assertEquals(newKaleoProcess1,
			kaleoProcesses.get(newKaleoProcess1.getPrimaryKey()));
		Assert.assertEquals(newKaleoProcess2,
			kaleoProcesses.get(newKaleoProcess2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, KaleoProcess> kaleoProcesses = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(kaleoProcesses.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		KaleoProcess newKaleoProcess = addKaleoProcess();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newKaleoProcess.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, KaleoProcess> kaleoProcesses = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, kaleoProcesses.size());
		Assert.assertEquals(newKaleoProcess,
			kaleoProcesses.get(newKaleoProcess.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, KaleoProcess> kaleoProcesses = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(kaleoProcesses.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		KaleoProcess newKaleoProcess = addKaleoProcess();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newKaleoProcess.getPrimaryKey());

		Map<Serializable, KaleoProcess> kaleoProcesses = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, kaleoProcesses.size());
		Assert.assertEquals(newKaleoProcess,
			kaleoProcesses.get(newKaleoProcess.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = KaleoProcessLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<KaleoProcess>() {
				@Override
				public void performAction(KaleoProcess kaleoProcess) {
					Assert.assertNotNull(kaleoProcess);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		KaleoProcess newKaleoProcess = addKaleoProcess();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(KaleoProcess.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("kaleoProcessId",
				newKaleoProcess.getKaleoProcessId()));

		List<KaleoProcess> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		KaleoProcess existingKaleoProcess = result.get(0);

		Assert.assertEquals(existingKaleoProcess, newKaleoProcess);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(KaleoProcess.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("kaleoProcessId",
				RandomTestUtil.nextLong()));

		List<KaleoProcess> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		KaleoProcess newKaleoProcess = addKaleoProcess();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(KaleoProcess.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"kaleoProcessId"));

		Object newKaleoProcessId = newKaleoProcess.getKaleoProcessId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("kaleoProcessId",
				new Object[] { newKaleoProcessId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingKaleoProcessId = result.get(0);

		Assert.assertEquals(existingKaleoProcessId, newKaleoProcessId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(KaleoProcess.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"kaleoProcessId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("kaleoProcessId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		KaleoProcess newKaleoProcess = addKaleoProcess();

		_persistence.clearCache();

		KaleoProcess existingKaleoProcess = _persistence.findByPrimaryKey(newKaleoProcess.getPrimaryKey());

		Assert.assertTrue(Objects.equals(existingKaleoProcess.getUuid(),
				ReflectionTestUtil.invoke(existingKaleoProcess,
					"getOriginalUuid", new Class<?>[0])));
		Assert.assertEquals(Long.valueOf(existingKaleoProcess.getGroupId()),
			ReflectionTestUtil.<Long>invoke(existingKaleoProcess,
				"getOriginalGroupId", new Class<?>[0]));

		Assert.assertEquals(Long.valueOf(
				existingKaleoProcess.getDDLRecordSetId()),
			ReflectionTestUtil.<Long>invoke(existingKaleoProcess,
				"getOriginalDDLRecordSetId", new Class<?>[0]));
	}

	protected KaleoProcess addKaleoProcess() throws Exception {
		long pk = RandomTestUtil.nextLong();

		KaleoProcess kaleoProcess = _persistence.create(pk);

		kaleoProcess.setUuid(RandomTestUtil.randomString());

		kaleoProcess.setGroupId(RandomTestUtil.nextLong());

		kaleoProcess.setCompanyId(RandomTestUtil.nextLong());

		kaleoProcess.setUserId(RandomTestUtil.nextLong());

		kaleoProcess.setUserName(RandomTestUtil.randomString());

		kaleoProcess.setCreateDate(RandomTestUtil.nextDate());

		kaleoProcess.setModifiedDate(RandomTestUtil.nextDate());

		kaleoProcess.setDDLRecordSetId(RandomTestUtil.nextLong());

		kaleoProcess.setDDMTemplateId(RandomTestUtil.nextLong());

		kaleoProcess.setWorkflowDefinitionName(RandomTestUtil.randomString());

		kaleoProcess.setWorkflowDefinitionVersion(RandomTestUtil.nextInt());

		_kaleoProcesses.add(_persistence.update(kaleoProcess));

		return kaleoProcess;
	}

	private List<KaleoProcess> _kaleoProcesses = new ArrayList<KaleoProcess>();
	private KaleoProcessPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}