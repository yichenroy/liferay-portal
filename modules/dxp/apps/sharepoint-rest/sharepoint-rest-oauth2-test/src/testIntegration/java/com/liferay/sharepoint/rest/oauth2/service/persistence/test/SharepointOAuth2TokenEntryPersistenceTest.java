/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.sharepoint.rest.oauth2.service.persistence.test;

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
import com.liferay.sharepoint.rest.oauth2.exception.NoSuch2TokenEntryException;
import com.liferay.sharepoint.rest.oauth2.model.SharepointOAuth2TokenEntry;
import com.liferay.sharepoint.rest.oauth2.service.SharepointOAuth2TokenEntryLocalServiceUtil;
import com.liferay.sharepoint.rest.oauth2.service.persistence.SharepointOAuth2TokenEntryPersistence;
import com.liferay.sharepoint.rest.oauth2.service.persistence.SharepointOAuth2TokenEntryUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
public class SharepointOAuth2TokenEntryPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED,
				"com.liferay.sharepoint.rest.oauth2.service"));

	@Before
	public void setUp() {
		_persistence = SharepointOAuth2TokenEntryUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<SharepointOAuth2TokenEntry> iterator =
			_sharepointOAuth2TokenEntries.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SharepointOAuth2TokenEntry sharepointOAuth2TokenEntry =
			_persistence.create(pk);

		Assert.assertNotNull(sharepointOAuth2TokenEntry);

		Assert.assertEquals(sharepointOAuth2TokenEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		SharepointOAuth2TokenEntry newSharepointOAuth2TokenEntry =
			addSharepointOAuth2TokenEntry();

		_persistence.remove(newSharepointOAuth2TokenEntry);

		SharepointOAuth2TokenEntry existingSharepointOAuth2TokenEntry =
			_persistence.fetchByPrimaryKey(
				newSharepointOAuth2TokenEntry.getPrimaryKey());

		Assert.assertNull(existingSharepointOAuth2TokenEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addSharepointOAuth2TokenEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SharepointOAuth2TokenEntry newSharepointOAuth2TokenEntry =
			_persistence.create(pk);

		newSharepointOAuth2TokenEntry.setUserId(RandomTestUtil.nextLong());

		newSharepointOAuth2TokenEntry.setUserName(
			RandomTestUtil.randomString());

		newSharepointOAuth2TokenEntry.setCreateDate(RandomTestUtil.nextDate());

		newSharepointOAuth2TokenEntry.setAccessToken(
			RandomTestUtil.randomString());

		newSharepointOAuth2TokenEntry.setConfigurationPid(
			RandomTestUtil.randomString());

		newSharepointOAuth2TokenEntry.setExpirationDate(
			RandomTestUtil.nextDate());

		newSharepointOAuth2TokenEntry.setRefreshToken(
			RandomTestUtil.randomString());

		_sharepointOAuth2TokenEntries.add(
			_persistence.update(newSharepointOAuth2TokenEntry));

		SharepointOAuth2TokenEntry existingSharepointOAuth2TokenEntry =
			_persistence.findByPrimaryKey(
				newSharepointOAuth2TokenEntry.getPrimaryKey());

		Assert.assertEquals(
			existingSharepointOAuth2TokenEntry.
				getSharepointOAuth2TokenEntryId(),
			newSharepointOAuth2TokenEntry.getSharepointOAuth2TokenEntryId());
		Assert.assertEquals(
			existingSharepointOAuth2TokenEntry.getUserId(),
			newSharepointOAuth2TokenEntry.getUserId());
		Assert.assertEquals(
			existingSharepointOAuth2TokenEntry.getUserName(),
			newSharepointOAuth2TokenEntry.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingSharepointOAuth2TokenEntry.getCreateDate()),
			Time.getShortTimestamp(
				newSharepointOAuth2TokenEntry.getCreateDate()));
		Assert.assertEquals(
			existingSharepointOAuth2TokenEntry.getAccessToken(),
			newSharepointOAuth2TokenEntry.getAccessToken());
		Assert.assertEquals(
			existingSharepointOAuth2TokenEntry.getConfigurationPid(),
			newSharepointOAuth2TokenEntry.getConfigurationPid());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingSharepointOAuth2TokenEntry.getExpirationDate()),
			Time.getShortTimestamp(
				newSharepointOAuth2TokenEntry.getExpirationDate()));
		Assert.assertEquals(
			existingSharepointOAuth2TokenEntry.getRefreshToken(),
			newSharepointOAuth2TokenEntry.getRefreshToken());
	}

	@Test
	public void testCountByU_C() throws Exception {
		_persistence.countByU_C(RandomTestUtil.nextLong(), "");

		_persistence.countByU_C(0L, "null");

		_persistence.countByU_C(0L, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		SharepointOAuth2TokenEntry newSharepointOAuth2TokenEntry =
			addSharepointOAuth2TokenEntry();

		SharepointOAuth2TokenEntry existingSharepointOAuth2TokenEntry =
			_persistence.findByPrimaryKey(
				newSharepointOAuth2TokenEntry.getPrimaryKey());

		Assert.assertEquals(
			existingSharepointOAuth2TokenEntry, newSharepointOAuth2TokenEntry);
	}

	@Test(expected = NoSuch2TokenEntryException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<SharepointOAuth2TokenEntry>
		getOrderByComparator() {

		return OrderByComparatorFactoryUtil.create(
			"SharepointOAuth2TokenEntry", "sharepointOAuth2TokenEntryId", true,
			"userId", true, "userName", true, "createDate", true,
			"configurationPid", true, "expirationDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		SharepointOAuth2TokenEntry newSharepointOAuth2TokenEntry =
			addSharepointOAuth2TokenEntry();

		SharepointOAuth2TokenEntry existingSharepointOAuth2TokenEntry =
			_persistence.fetchByPrimaryKey(
				newSharepointOAuth2TokenEntry.getPrimaryKey());

		Assert.assertEquals(
			existingSharepointOAuth2TokenEntry, newSharepointOAuth2TokenEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		SharepointOAuth2TokenEntry missingSharepointOAuth2TokenEntry =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingSharepointOAuth2TokenEntry);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		SharepointOAuth2TokenEntry newSharepointOAuth2TokenEntry1 =
			addSharepointOAuth2TokenEntry();
		SharepointOAuth2TokenEntry newSharepointOAuth2TokenEntry2 =
			addSharepointOAuth2TokenEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newSharepointOAuth2TokenEntry1.getPrimaryKey());
		primaryKeys.add(newSharepointOAuth2TokenEntry2.getPrimaryKey());

		Map<Serializable, SharepointOAuth2TokenEntry>
			sharepointOAuth2TokenEntries = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(2, sharepointOAuth2TokenEntries.size());
		Assert.assertEquals(
			newSharepointOAuth2TokenEntry1,
			sharepointOAuth2TokenEntries.get(
				newSharepointOAuth2TokenEntry1.getPrimaryKey()));
		Assert.assertEquals(
			newSharepointOAuth2TokenEntry2,
			sharepointOAuth2TokenEntries.get(
				newSharepointOAuth2TokenEntry2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, SharepointOAuth2TokenEntry>
			sharepointOAuth2TokenEntries = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertTrue(sharepointOAuth2TokenEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		SharepointOAuth2TokenEntry newSharepointOAuth2TokenEntry =
			addSharepointOAuth2TokenEntry();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newSharepointOAuth2TokenEntry.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, SharepointOAuth2TokenEntry>
			sharepointOAuth2TokenEntries = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(1, sharepointOAuth2TokenEntries.size());
		Assert.assertEquals(
			newSharepointOAuth2TokenEntry,
			sharepointOAuth2TokenEntries.get(
				newSharepointOAuth2TokenEntry.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, SharepointOAuth2TokenEntry>
			sharepointOAuth2TokenEntries = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertTrue(sharepointOAuth2TokenEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		SharepointOAuth2TokenEntry newSharepointOAuth2TokenEntry =
			addSharepointOAuth2TokenEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newSharepointOAuth2TokenEntry.getPrimaryKey());

		Map<Serializable, SharepointOAuth2TokenEntry>
			sharepointOAuth2TokenEntries = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(1, sharepointOAuth2TokenEntries.size());
		Assert.assertEquals(
			newSharepointOAuth2TokenEntry,
			sharepointOAuth2TokenEntries.get(
				newSharepointOAuth2TokenEntry.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			SharepointOAuth2TokenEntryLocalServiceUtil.
				getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<SharepointOAuth2TokenEntry>() {

				@Override
				public void performAction(
					SharepointOAuth2TokenEntry sharepointOAuth2TokenEntry) {

					Assert.assertNotNull(sharepointOAuth2TokenEntry);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		SharepointOAuth2TokenEntry newSharepointOAuth2TokenEntry =
			addSharepointOAuth2TokenEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			SharepointOAuth2TokenEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"sharepointOAuth2TokenEntryId",
				newSharepointOAuth2TokenEntry.
					getSharepointOAuth2TokenEntryId()));

		List<SharepointOAuth2TokenEntry> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		SharepointOAuth2TokenEntry existingSharepointOAuth2TokenEntry =
			result.get(0);

		Assert.assertEquals(
			existingSharepointOAuth2TokenEntry, newSharepointOAuth2TokenEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			SharepointOAuth2TokenEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"sharepointOAuth2TokenEntryId", RandomTestUtil.nextLong()));

		List<SharepointOAuth2TokenEntry> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		SharepointOAuth2TokenEntry newSharepointOAuth2TokenEntry =
			addSharepointOAuth2TokenEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			SharepointOAuth2TokenEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("sharepointOAuth2TokenEntryId"));

		Object newSharepointOAuth2TokenEntryId =
			newSharepointOAuth2TokenEntry.getSharepointOAuth2TokenEntryId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"sharepointOAuth2TokenEntryId",
				new Object[] {newSharepointOAuth2TokenEntryId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingSharepointOAuth2TokenEntryId = result.get(0);

		Assert.assertEquals(
			existingSharepointOAuth2TokenEntryId,
			newSharepointOAuth2TokenEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			SharepointOAuth2TokenEntry.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("sharepointOAuth2TokenEntryId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"sharepointOAuth2TokenEntryId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		SharepointOAuth2TokenEntry newSharepointOAuth2TokenEntry =
			addSharepointOAuth2TokenEntry();

		_persistence.clearCache();

		SharepointOAuth2TokenEntry existingSharepointOAuth2TokenEntry =
			_persistence.findByPrimaryKey(
				newSharepointOAuth2TokenEntry.getPrimaryKey());

		Assert.assertEquals(
			Long.valueOf(existingSharepointOAuth2TokenEntry.getUserId()),
			ReflectionTestUtil.<Long>invoke(
				existingSharepointOAuth2TokenEntry, "getOriginalUserId",
				new Class<?>[0]));
		Assert.assertTrue(
			Objects.equals(
				existingSharepointOAuth2TokenEntry.getConfigurationPid(),
				ReflectionTestUtil.invoke(
					existingSharepointOAuth2TokenEntry,
					"getOriginalConfigurationPid", new Class<?>[0])));
	}

	protected SharepointOAuth2TokenEntry addSharepointOAuth2TokenEntry()
		throws Exception {

		long pk = RandomTestUtil.nextLong();

		SharepointOAuth2TokenEntry sharepointOAuth2TokenEntry =
			_persistence.create(pk);

		sharepointOAuth2TokenEntry.setUserId(RandomTestUtil.nextLong());

		sharepointOAuth2TokenEntry.setUserName(RandomTestUtil.randomString());

		sharepointOAuth2TokenEntry.setCreateDate(RandomTestUtil.nextDate());

		sharepointOAuth2TokenEntry.setAccessToken(
			RandomTestUtil.randomString());

		sharepointOAuth2TokenEntry.setConfigurationPid(
			RandomTestUtil.randomString());

		sharepointOAuth2TokenEntry.setExpirationDate(RandomTestUtil.nextDate());

		sharepointOAuth2TokenEntry.setRefreshToken(
			RandomTestUtil.randomString());

		_sharepointOAuth2TokenEntries.add(
			_persistence.update(sharepointOAuth2TokenEntry));

		return sharepointOAuth2TokenEntry;
	}

	private List<SharepointOAuth2TokenEntry> _sharepointOAuth2TokenEntries =
		new ArrayList<SharepointOAuth2TokenEntry>();
	private SharepointOAuth2TokenEntryPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}