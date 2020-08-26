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

package com.liferay.asset.list.service.persistence.impl;

import com.liferay.asset.list.exception.NoSuchEntryException;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.model.AssetListEntryTable;
import com.liferay.asset.list.model.impl.AssetListEntryImpl;
import com.liferay.asset.list.model.impl.AssetListEntryModelImpl;
import com.liferay.asset.list.service.persistence.AssetListEntryPersistence;
import com.liferay.asset.list.service.persistence.impl.constants.AssetListPersistenceConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.change.tracking.CTColumnResolutionType;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.change.tracking.helper.CTPersistenceHelper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the asset list entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = AssetListEntryPersistence.class)
public class AssetListEntryPersistenceImpl
	extends BasePersistenceImpl<AssetListEntry>
	implements AssetListEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>AssetListEntryUtil</code> to access the asset list entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		AssetListEntryImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByUuid;
	private FinderPath _finderPathWithoutPaginationFindByUuid;
	private FinderPath _finderPathCountByUuid;

	/**
	 * Returns all the asset list entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching asset list entries
	 */
	@Override
	public List<AssetListEntry> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset list entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @return the range of matching asset list entries
	 */
	@Override
	public List<AssetListEntry> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset list entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset list entries
	 */
	@Override
	public List<AssetListEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<AssetListEntry> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset list entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset list entries
	 */
	@Override
	public List<AssetListEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<AssetListEntry> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetListEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByUuid;
				finderArgs = new Object[] {uuid};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByUuid;
			finderArgs = new Object[] {uuid, start, end, orderByComparator};
		}

		List<AssetListEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<AssetListEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetListEntry assetListEntry : list) {
					if (!uuid.equals(assetListEntry.getUuid())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(3);
			}

			sb.append(_SQL_SELECT_ASSETLISTENTRY_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AssetListEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				list = (List<AssetListEntry>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first asset list entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry
	 * @throws NoSuchEntryException if a matching asset list entry could not be found
	 */
	@Override
	public AssetListEntry findByUuid_First(
			String uuid, OrderByComparator<AssetListEntry> orderByComparator)
		throws NoSuchEntryException {

		AssetListEntry assetListEntry = fetchByUuid_First(
			uuid, orderByComparator);

		if (assetListEntry != null) {
			return assetListEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first asset list entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry, or <code>null</code> if a matching asset list entry could not be found
	 */
	@Override
	public AssetListEntry fetchByUuid_First(
		String uuid, OrderByComparator<AssetListEntry> orderByComparator) {

		List<AssetListEntry> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset list entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry
	 * @throws NoSuchEntryException if a matching asset list entry could not be found
	 */
	@Override
	public AssetListEntry findByUuid_Last(
			String uuid, OrderByComparator<AssetListEntry> orderByComparator)
		throws NoSuchEntryException {

		AssetListEntry assetListEntry = fetchByUuid_Last(
			uuid, orderByComparator);

		if (assetListEntry != null) {
			return assetListEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last asset list entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry, or <code>null</code> if a matching asset list entry could not be found
	 */
	@Override
	public AssetListEntry fetchByUuid_Last(
		String uuid, OrderByComparator<AssetListEntry> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<AssetListEntry> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset list entries before and after the current asset list entry in the ordered set where uuid = &#63;.
	 *
	 * @param assetListEntryId the primary key of the current asset list entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset list entry
	 * @throws NoSuchEntryException if a asset list entry with the primary key could not be found
	 */
	@Override
	public AssetListEntry[] findByUuid_PrevAndNext(
			long assetListEntryId, String uuid,
			OrderByComparator<AssetListEntry> orderByComparator)
		throws NoSuchEntryException {

		uuid = Objects.toString(uuid, "");

		AssetListEntry assetListEntry = findByPrimaryKey(assetListEntryId);

		Session session = null;

		try {
			session = openSession();

			AssetListEntry[] array = new AssetListEntryImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, assetListEntry, uuid, orderByComparator, true);

			array[1] = assetListEntry;

			array[2] = getByUuid_PrevAndNext(
				session, assetListEntry, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetListEntry getByUuid_PrevAndNext(
		Session session, AssetListEntry assetListEntry, String uuid,
		OrderByComparator<AssetListEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_ASSETLISTENTRY_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_UUID_3);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_UUID_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(AssetListEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindUuid) {
			queryPos.add(uuid);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						assetListEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AssetListEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset list entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (AssetListEntry assetListEntry :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(assetListEntry);
		}
	}

	/**
	 * Returns the number of asset list entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching asset list entries
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetListEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByUuid;

			finderArgs = new Object[] {uuid};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_ASSETLISTENTRY_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_UUID_2 =
		"assetListEntry.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(assetListEntry.uuid IS NULL OR assetListEntry.uuid = '')";

	private FinderPath _finderPathFetchByUUID_G;
	private FinderPath _finderPathCountByUUID_G;

	/**
	 * Returns the asset list entry where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching asset list entry
	 * @throws NoSuchEntryException if a matching asset list entry could not be found
	 */
	@Override
	public AssetListEntry findByUUID_G(String uuid, long groupId)
		throws NoSuchEntryException {

		AssetListEntry assetListEntry = fetchByUUID_G(uuid, groupId);

		if (assetListEntry == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("uuid=");
			sb.append(uuid);

			sb.append(", groupId=");
			sb.append(groupId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchEntryException(sb.toString());
		}

		return assetListEntry;
	}

	/**
	 * Returns the asset list entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching asset list entry, or <code>null</code> if a matching asset list entry could not be found
	 */
	@Override
	public AssetListEntry fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the asset list entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching asset list entry, or <code>null</code> if a matching asset list entry could not be found
	 */
	@Override
	public AssetListEntry fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetListEntry.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {uuid, groupId};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(
				_finderPathFetchByUUID_G, finderArgs, this);
		}

		if (result instanceof AssetListEntry) {
			AssetListEntry assetListEntry = (AssetListEntry)result;

			if (!Objects.equals(uuid, assetListEntry.getUuid()) ||
				(groupId != assetListEntry.getGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_ASSETLISTENTRY_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_G_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_G_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(groupId);

				List<AssetListEntry> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByUUID_G, finderArgs, list);
					}
				}
				else {
					AssetListEntry assetListEntry = list.get(0);

					result = assetListEntry;

					cacheResult(assetListEntry);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (AssetListEntry)result;
		}
	}

	/**
	 * Removes the asset list entry where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the asset list entry that was removed
	 */
	@Override
	public AssetListEntry removeByUUID_G(String uuid, long groupId)
		throws NoSuchEntryException {

		AssetListEntry assetListEntry = findByUUID_G(uuid, groupId);

		return remove(assetListEntry);
	}

	/**
	 * Returns the number of asset list entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching asset list entries
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetListEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByUUID_G;

			finderArgs = new Object[] {uuid, groupId};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_ASSETLISTENTRY_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_G_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_G_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(groupId);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_G_UUID_2 =
		"assetListEntry.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(assetListEntry.uuid IS NULL OR assetListEntry.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"assetListEntry.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the asset list entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching asset list entries
	 */
	@Override
	public List<AssetListEntry> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset list entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @return the range of matching asset list entries
	 */
	@Override
	public List<AssetListEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset list entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset list entries
	 */
	@Override
	public List<AssetListEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<AssetListEntry> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset list entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset list entries
	 */
	@Override
	public List<AssetListEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<AssetListEntry> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetListEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByUuid_C;
				finderArgs = new Object[] {uuid, companyId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByUuid_C;
			finderArgs = new Object[] {
				uuid, companyId, start, end, orderByComparator
			};
		}

		List<AssetListEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<AssetListEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetListEntry assetListEntry : list) {
					if (!uuid.equals(assetListEntry.getUuid()) ||
						(companyId != assetListEntry.getCompanyId())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(4);
			}

			sb.append(_SQL_SELECT_ASSETLISTENTRY_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AssetListEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(companyId);

				list = (List<AssetListEntry>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first asset list entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry
	 * @throws NoSuchEntryException if a matching asset list entry could not be found
	 */
	@Override
	public AssetListEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<AssetListEntry> orderByComparator)
		throws NoSuchEntryException {

		AssetListEntry assetListEntry = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (assetListEntry != null) {
			return assetListEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first asset list entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry, or <code>null</code> if a matching asset list entry could not be found
	 */
	@Override
	public AssetListEntry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<AssetListEntry> orderByComparator) {

		List<AssetListEntry> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset list entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry
	 * @throws NoSuchEntryException if a matching asset list entry could not be found
	 */
	@Override
	public AssetListEntry findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<AssetListEntry> orderByComparator)
		throws NoSuchEntryException {

		AssetListEntry assetListEntry = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (assetListEntry != null) {
			return assetListEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last asset list entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry, or <code>null</code> if a matching asset list entry could not be found
	 */
	@Override
	public AssetListEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<AssetListEntry> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<AssetListEntry> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset list entries before and after the current asset list entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param assetListEntryId the primary key of the current asset list entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset list entry
	 * @throws NoSuchEntryException if a asset list entry with the primary key could not be found
	 */
	@Override
	public AssetListEntry[] findByUuid_C_PrevAndNext(
			long assetListEntryId, String uuid, long companyId,
			OrderByComparator<AssetListEntry> orderByComparator)
		throws NoSuchEntryException {

		uuid = Objects.toString(uuid, "");

		AssetListEntry assetListEntry = findByPrimaryKey(assetListEntryId);

		Session session = null;

		try {
			session = openSession();

			AssetListEntry[] array = new AssetListEntryImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, assetListEntry, uuid, companyId, orderByComparator,
				true);

			array[1] = assetListEntry;

			array[2] = getByUuid_C_PrevAndNext(
				session, assetListEntry, uuid, companyId, orderByComparator,
				false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetListEntry getByUuid_C_PrevAndNext(
		Session session, AssetListEntry assetListEntry, String uuid,
		long companyId, OrderByComparator<AssetListEntry> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_ASSETLISTENTRY_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_C_UUID_3);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_C_UUID_2);
		}

		sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(AssetListEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindUuid) {
			queryPos.add(uuid);
		}

		queryPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						assetListEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AssetListEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset list entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (AssetListEntry assetListEntry :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(assetListEntry);
		}
	}

	/**
	 * Returns the number of asset list entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching asset list entries
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetListEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByUuid_C;

			finderArgs = new Object[] {uuid, companyId};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_ASSETLISTENTRY_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(companyId);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_C_UUID_2 =
		"assetListEntry.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(assetListEntry.uuid IS NULL OR assetListEntry.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"assetListEntry.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByGroupId;
	private FinderPath _finderPathWithoutPaginationFindByGroupId;
	private FinderPath _finderPathCountByGroupId;
	private FinderPath _finderPathWithPaginationCountByGroupId;

	/**
	 * Returns all the asset list entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching asset list entries
	 */
	@Override
	public List<AssetListEntry> findByGroupId(long groupId) {
		return findByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset list entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @return the range of matching asset list entries
	 */
	@Override
	public List<AssetListEntry> findByGroupId(
		long groupId, int start, int end) {

		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset list entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset list entries
	 */
	@Override
	public List<AssetListEntry> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<AssetListEntry> orderByComparator) {

		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset list entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset list entries
	 */
	@Override
	public List<AssetListEntry> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<AssetListEntry> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetListEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByGroupId;
				finderArgs = new Object[] {groupId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByGroupId;
			finderArgs = new Object[] {groupId, start, end, orderByComparator};
		}

		List<AssetListEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<AssetListEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetListEntry assetListEntry : list) {
					if (groupId != assetListEntry.getGroupId()) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(3);
			}

			sb.append(_SQL_SELECT_ASSETLISTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AssetListEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				list = (List<AssetListEntry>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first asset list entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry
	 * @throws NoSuchEntryException if a matching asset list entry could not be found
	 */
	@Override
	public AssetListEntry findByGroupId_First(
			long groupId, OrderByComparator<AssetListEntry> orderByComparator)
		throws NoSuchEntryException {

		AssetListEntry assetListEntry = fetchByGroupId_First(
			groupId, orderByComparator);

		if (assetListEntry != null) {
			return assetListEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first asset list entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry, or <code>null</code> if a matching asset list entry could not be found
	 */
	@Override
	public AssetListEntry fetchByGroupId_First(
		long groupId, OrderByComparator<AssetListEntry> orderByComparator) {

		List<AssetListEntry> list = findByGroupId(
			groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset list entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry
	 * @throws NoSuchEntryException if a matching asset list entry could not be found
	 */
	@Override
	public AssetListEntry findByGroupId_Last(
			long groupId, OrderByComparator<AssetListEntry> orderByComparator)
		throws NoSuchEntryException {

		AssetListEntry assetListEntry = fetchByGroupId_Last(
			groupId, orderByComparator);

		if (assetListEntry != null) {
			return assetListEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last asset list entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry, or <code>null</code> if a matching asset list entry could not be found
	 */
	@Override
	public AssetListEntry fetchByGroupId_Last(
		long groupId, OrderByComparator<AssetListEntry> orderByComparator) {

		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<AssetListEntry> list = findByGroupId(
			groupId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset list entries before and after the current asset list entry in the ordered set where groupId = &#63;.
	 *
	 * @param assetListEntryId the primary key of the current asset list entry
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset list entry
	 * @throws NoSuchEntryException if a asset list entry with the primary key could not be found
	 */
	@Override
	public AssetListEntry[] findByGroupId_PrevAndNext(
			long assetListEntryId, long groupId,
			OrderByComparator<AssetListEntry> orderByComparator)
		throws NoSuchEntryException {

		AssetListEntry assetListEntry = findByPrimaryKey(assetListEntryId);

		Session session = null;

		try {
			session = openSession();

			AssetListEntry[] array = new AssetListEntryImpl[3];

			array[0] = getByGroupId_PrevAndNext(
				session, assetListEntry, groupId, orderByComparator, true);

			array[1] = assetListEntry;

			array[2] = getByGroupId_PrevAndNext(
				session, assetListEntry, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetListEntry getByGroupId_PrevAndNext(
		Session session, AssetListEntry assetListEntry, long groupId,
		OrderByComparator<AssetListEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_ASSETLISTENTRY_WHERE);

		sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(AssetListEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						assetListEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AssetListEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the asset list entries that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching asset list entries that the user has permission to view
	 */
	@Override
	public List<AssetListEntry> filterFindByGroupId(long groupId) {
		return filterFindByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset list entries that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @return the range of matching asset list entries that the user has permission to view
	 */
	@Override
	public List<AssetListEntry> filterFindByGroupId(
		long groupId, int start, int end) {

		return filterFindByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset list entries that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset list entries that the user has permission to view
	 */
	@Override
	public List<AssetListEntry> filterFindByGroupId(
		long groupId, int start, int end,
		OrderByComparator<AssetListEntry> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId(groupId, start, end, orderByComparator);
		}

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				3 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_ASSETLISTENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_ASSETLISTENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_ASSETLISTENTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(AssetListEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(AssetListEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), AssetListEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, AssetListEntryImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, AssetListEntryImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			return (List<AssetListEntry>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the asset list entries before and after the current asset list entry in the ordered set of asset list entries that the user has permission to view where groupId = &#63;.
	 *
	 * @param assetListEntryId the primary key of the current asset list entry
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset list entry
	 * @throws NoSuchEntryException if a asset list entry with the primary key could not be found
	 */
	@Override
	public AssetListEntry[] filterFindByGroupId_PrevAndNext(
			long assetListEntryId, long groupId,
			OrderByComparator<AssetListEntry> orderByComparator)
		throws NoSuchEntryException {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId_PrevAndNext(
				assetListEntryId, groupId, orderByComparator);
		}

		AssetListEntry assetListEntry = findByPrimaryKey(assetListEntryId);

		Session session = null;

		try {
			session = openSession();

			AssetListEntry[] array = new AssetListEntryImpl[3];

			array[0] = filterGetByGroupId_PrevAndNext(
				session, assetListEntry, groupId, orderByComparator, true);

			array[1] = assetListEntry;

			array[2] = filterGetByGroupId_PrevAndNext(
				session, assetListEntry, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetListEntry filterGetByGroupId_PrevAndNext(
		Session session, AssetListEntry assetListEntry, long groupId,
		OrderByComparator<AssetListEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_ASSETLISTENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_ASSETLISTENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_ASSETLISTENTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByConditionFields[i],
							true));
				}
				else {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByConditionFields[i],
							true));
				}

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByFields[i], true));
				}
				else {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByFields[i], true));
				}

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(AssetListEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(AssetListEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), AssetListEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(_FILTER_ENTITY_ALIAS, AssetListEntryImpl.class);
		}
		else {
			sqlQuery.addEntity(_FILTER_ENTITY_TABLE, AssetListEntryImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(groupId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						assetListEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AssetListEntry> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the asset list entries that the user has permission to view where groupId = any &#63;.
	 *
	 * @param groupIds the group IDs
	 * @return the matching asset list entries that the user has permission to view
	 */
	@Override
	public List<AssetListEntry> filterFindByGroupId(long[] groupIds) {
		return filterFindByGroupId(
			groupIds, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset list entries that the user has permission to view where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @return the range of matching asset list entries that the user has permission to view
	 */
	@Override
	public List<AssetListEntry> filterFindByGroupId(
		long[] groupIds, int start, int end) {

		return filterFindByGroupId(groupIds, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset list entries that the user has permission to view where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset list entries that the user has permission to view
	 */
	@Override
	public List<AssetListEntry> filterFindByGroupId(
		long[] groupIds, int start, int end,
		OrderByComparator<AssetListEntry> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupIds)) {
			return findByGroupId(groupIds, start, end, orderByComparator);
		}

		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.sortedUnique(groupIds);
		}

		StringBundler sb = new StringBundler();

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_ASSETLISTENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_ASSETLISTENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		if (groupIds.length > 0) {
			sb.append("(");

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_7);

			sb.append(StringUtil.merge(groupIds));

			sb.append(")");

			sb.append(")");
		}

		sb.setStringAt(
			removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_ASSETLISTENTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(AssetListEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(AssetListEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), AssetListEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupIds);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, AssetListEntryImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, AssetListEntryImpl.class);
			}

			return (List<AssetListEntry>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns all the asset list entries where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @return the matching asset list entries
	 */
	@Override
	public List<AssetListEntry> findByGroupId(long[] groupIds) {
		return findByGroupId(
			groupIds, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset list entries where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @return the range of matching asset list entries
	 */
	@Override
	public List<AssetListEntry> findByGroupId(
		long[] groupIds, int start, int end) {

		return findByGroupId(groupIds, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset list entries where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset list entries
	 */
	@Override
	public List<AssetListEntry> findByGroupId(
		long[] groupIds, int start, int end,
		OrderByComparator<AssetListEntry> orderByComparator) {

		return findByGroupId(groupIds, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset list entries where groupId = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset list entries
	 */
	@Override
	public List<AssetListEntry> findByGroupId(
		long[] groupIds, int start, int end,
		OrderByComparator<AssetListEntry> orderByComparator,
		boolean useFinderCache) {

		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.sortedUnique(groupIds);
		}

		if (groupIds.length == 1) {
			return findByGroupId(groupIds[0], start, end, orderByComparator);
		}

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetListEntry.class);

		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderArgs = new Object[] {StringUtil.merge(groupIds)};
			}
		}
		else if (useFinderCache && productionMode) {
			finderArgs = new Object[] {
				StringUtil.merge(groupIds), start, end, orderByComparator
			};
		}

		List<AssetListEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<AssetListEntry>)finderCache.getResult(
				_finderPathWithPaginationFindByGroupId, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetListEntry assetListEntry : list) {
					if (!ArrayUtil.contains(
							groupIds, assetListEntry.getGroupId())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_SELECT_ASSETLISTENTRY_WHERE);

			if (groupIds.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_GROUPID_GROUPID_7);

				sb.append(StringUtil.merge(groupIds));

				sb.append(")");

				sb.append(")");
			}

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AssetListEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<AssetListEntry>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					finderCache.putResult(
						_finderPathWithPaginationFindByGroupId, finderArgs,
						list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the asset list entries where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (AssetListEntry assetListEntry :
				findByGroupId(
					groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(assetListEntry);
		}
	}

	/**
	 * Returns the number of asset list entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching asset list entries
	 */
	@Override
	public int countByGroupId(long groupId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetListEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByGroupId;

			finderArgs = new Object[] {groupId};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_ASSETLISTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of asset list entries where groupId = any &#63;.
	 *
	 * @param groupIds the group IDs
	 * @return the number of matching asset list entries
	 */
	@Override
	public int countByGroupId(long[] groupIds) {
		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.sortedUnique(groupIds);
		}

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetListEntry.class);

		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderArgs = new Object[] {StringUtil.merge(groupIds)};

			count = (Long)finderCache.getResult(
				_finderPathWithPaginationCountByGroupId, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_COUNT_ASSETLISTENTRY_WHERE);

			if (groupIds.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_GROUPID_GROUPID_7);

				sb.append(StringUtil.merge(groupIds));

				sb.append(")");

				sb.append(")");
			}

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(
						_finderPathWithPaginationCountByGroupId, finderArgs,
						count);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of asset list entries that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching asset list entries that the user has permission to view
	 */
	@Override
	public int filterCountByGroupId(long groupId) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByGroupId(groupId);
		}

		StringBundler sb = new StringBundler(2);

		sb.append(_FILTER_SQL_COUNT_ASSETLISTENTRY_WHERE);

		sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), AssetListEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			Long count = (Long)sqlQuery.uniqueResult();

			return count.intValue();
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the number of asset list entries that the user has permission to view where groupId = any &#63;.
	 *
	 * @param groupIds the group IDs
	 * @return the number of matching asset list entries that the user has permission to view
	 */
	@Override
	public int filterCountByGroupId(long[] groupIds) {
		if (!InlineSQLHelperUtil.isEnabled(groupIds)) {
			return countByGroupId(groupIds);
		}

		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.sortedUnique(groupIds);
		}

		StringBundler sb = new StringBundler();

		sb.append(_FILTER_SQL_COUNT_ASSETLISTENTRY_WHERE);

		if (groupIds.length > 0) {
			sb.append("(");

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_7);

			sb.append(StringUtil.merge(groupIds));

			sb.append(")");

			sb.append(")");
		}

		sb.setStringAt(
			removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), AssetListEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupIds);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			Long count = (Long)sqlQuery.uniqueResult();

			return count.intValue();
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 =
		"assetListEntry.groupId = ?";

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_7 =
		"assetListEntry.groupId IN (";

	private FinderPath _finderPathFetchByG_ALEK;
	private FinderPath _finderPathCountByG_ALEK;

	/**
	 * Returns the asset list entry where groupId = &#63; and assetListEntryKey = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param assetListEntryKey the asset list entry key
	 * @return the matching asset list entry
	 * @throws NoSuchEntryException if a matching asset list entry could not be found
	 */
	@Override
	public AssetListEntry findByG_ALEK(long groupId, String assetListEntryKey)
		throws NoSuchEntryException {

		AssetListEntry assetListEntry = fetchByG_ALEK(
			groupId, assetListEntryKey);

		if (assetListEntry == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("groupId=");
			sb.append(groupId);

			sb.append(", assetListEntryKey=");
			sb.append(assetListEntryKey);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchEntryException(sb.toString());
		}

		return assetListEntry;
	}

	/**
	 * Returns the asset list entry where groupId = &#63; and assetListEntryKey = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param assetListEntryKey the asset list entry key
	 * @return the matching asset list entry, or <code>null</code> if a matching asset list entry could not be found
	 */
	@Override
	public AssetListEntry fetchByG_ALEK(
		long groupId, String assetListEntryKey) {

		return fetchByG_ALEK(groupId, assetListEntryKey, true);
	}

	/**
	 * Returns the asset list entry where groupId = &#63; and assetListEntryKey = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param assetListEntryKey the asset list entry key
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching asset list entry, or <code>null</code> if a matching asset list entry could not be found
	 */
	@Override
	public AssetListEntry fetchByG_ALEK(
		long groupId, String assetListEntryKey, boolean useFinderCache) {

		assetListEntryKey = Objects.toString(assetListEntryKey, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetListEntry.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {groupId, assetListEntryKey};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(
				_finderPathFetchByG_ALEK, finderArgs, this);
		}

		if (result instanceof AssetListEntry) {
			AssetListEntry assetListEntry = (AssetListEntry)result;

			if ((groupId != assetListEntry.getGroupId()) ||
				!Objects.equals(
					assetListEntryKey, assetListEntry.getAssetListEntryKey())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_ASSETLISTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_ALEK_GROUPID_2);

			boolean bindAssetListEntryKey = false;

			if (assetListEntryKey.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_ALEK_ASSETLISTENTRYKEY_3);
			}
			else {
				bindAssetListEntryKey = true;

				sb.append(_FINDER_COLUMN_G_ALEK_ASSETLISTENTRYKEY_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindAssetListEntryKey) {
					queryPos.add(assetListEntryKey);
				}

				List<AssetListEntry> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByG_ALEK, finderArgs, list);
					}
				}
				else {
					AssetListEntry assetListEntry = list.get(0);

					result = assetListEntry;

					cacheResult(assetListEntry);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (AssetListEntry)result;
		}
	}

	/**
	 * Removes the asset list entry where groupId = &#63; and assetListEntryKey = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param assetListEntryKey the asset list entry key
	 * @return the asset list entry that was removed
	 */
	@Override
	public AssetListEntry removeByG_ALEK(long groupId, String assetListEntryKey)
		throws NoSuchEntryException {

		AssetListEntry assetListEntry = findByG_ALEK(
			groupId, assetListEntryKey);

		return remove(assetListEntry);
	}

	/**
	 * Returns the number of asset list entries where groupId = &#63; and assetListEntryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param assetListEntryKey the asset list entry key
	 * @return the number of matching asset list entries
	 */
	@Override
	public int countByG_ALEK(long groupId, String assetListEntryKey) {
		assetListEntryKey = Objects.toString(assetListEntryKey, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetListEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_ALEK;

			finderArgs = new Object[] {groupId, assetListEntryKey};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_ASSETLISTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_ALEK_GROUPID_2);

			boolean bindAssetListEntryKey = false;

			if (assetListEntryKey.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_ALEK_ASSETLISTENTRYKEY_3);
			}
			else {
				bindAssetListEntryKey = true;

				sb.append(_FINDER_COLUMN_G_ALEK_ASSETLISTENTRYKEY_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindAssetListEntryKey) {
					queryPos.add(assetListEntryKey);
				}

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_G_ALEK_GROUPID_2 =
		"assetListEntry.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_ALEK_ASSETLISTENTRYKEY_2 =
		"assetListEntry.assetListEntryKey = ?";

	private static final String _FINDER_COLUMN_G_ALEK_ASSETLISTENTRYKEY_3 =
		"(assetListEntry.assetListEntryKey IS NULL OR assetListEntry.assetListEntryKey = '')";

	private FinderPath _finderPathFetchByG_T;
	private FinderPath _finderPathCountByG_T;

	/**
	 * Returns the asset list entry where groupId = &#63; and title = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param title the title
	 * @return the matching asset list entry
	 * @throws NoSuchEntryException if a matching asset list entry could not be found
	 */
	@Override
	public AssetListEntry findByG_T(long groupId, String title)
		throws NoSuchEntryException {

		AssetListEntry assetListEntry = fetchByG_T(groupId, title);

		if (assetListEntry == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("groupId=");
			sb.append(groupId);

			sb.append(", title=");
			sb.append(title);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchEntryException(sb.toString());
		}

		return assetListEntry;
	}

	/**
	 * Returns the asset list entry where groupId = &#63; and title = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param title the title
	 * @return the matching asset list entry, or <code>null</code> if a matching asset list entry could not be found
	 */
	@Override
	public AssetListEntry fetchByG_T(long groupId, String title) {
		return fetchByG_T(groupId, title, true);
	}

	/**
	 * Returns the asset list entry where groupId = &#63; and title = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param title the title
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching asset list entry, or <code>null</code> if a matching asset list entry could not be found
	 */
	@Override
	public AssetListEntry fetchByG_T(
		long groupId, String title, boolean useFinderCache) {

		title = Objects.toString(title, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetListEntry.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {groupId, title};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(
				_finderPathFetchByG_T, finderArgs, this);
		}

		if (result instanceof AssetListEntry) {
			AssetListEntry assetListEntry = (AssetListEntry)result;

			if ((groupId != assetListEntry.getGroupId()) ||
				!Objects.equals(title, assetListEntry.getTitle())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_ASSETLISTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_T_GROUPID_2);

			boolean bindTitle = false;

			if (title.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_T_TITLE_3);
			}
			else {
				bindTitle = true;

				sb.append(_FINDER_COLUMN_G_T_TITLE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindTitle) {
					queryPos.add(title);
				}

				List<AssetListEntry> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByG_T, finderArgs, list);
					}
				}
				else {
					AssetListEntry assetListEntry = list.get(0);

					result = assetListEntry;

					cacheResult(assetListEntry);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (AssetListEntry)result;
		}
	}

	/**
	 * Removes the asset list entry where groupId = &#63; and title = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param title the title
	 * @return the asset list entry that was removed
	 */
	@Override
	public AssetListEntry removeByG_T(long groupId, String title)
		throws NoSuchEntryException {

		AssetListEntry assetListEntry = findByG_T(groupId, title);

		return remove(assetListEntry);
	}

	/**
	 * Returns the number of asset list entries where groupId = &#63; and title = &#63;.
	 *
	 * @param groupId the group ID
	 * @param title the title
	 * @return the number of matching asset list entries
	 */
	@Override
	public int countByG_T(long groupId, String title) {
		title = Objects.toString(title, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetListEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_T;

			finderArgs = new Object[] {groupId, title};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_ASSETLISTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_T_GROUPID_2);

			boolean bindTitle = false;

			if (title.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_T_TITLE_3);
			}
			else {
				bindTitle = true;

				sb.append(_FINDER_COLUMN_G_T_TITLE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindTitle) {
					queryPos.add(title);
				}

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_G_T_GROUPID_2 =
		"assetListEntry.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_T_TITLE_2 =
		"assetListEntry.title = ?";

	private static final String _FINDER_COLUMN_G_T_TITLE_3 =
		"(assetListEntry.title IS NULL OR assetListEntry.title = '')";

	private FinderPath _finderPathWithPaginationFindByG_LikeT;
	private FinderPath _finderPathWithPaginationCountByG_LikeT;

	/**
	 * Returns all the asset list entries where groupId = &#63; and title LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param title the title
	 * @return the matching asset list entries
	 */
	@Override
	public List<AssetListEntry> findByG_LikeT(long groupId, String title) {
		return findByG_LikeT(
			groupId, title, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset list entries where groupId = &#63; and title LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param title the title
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @return the range of matching asset list entries
	 */
	@Override
	public List<AssetListEntry> findByG_LikeT(
		long groupId, String title, int start, int end) {

		return findByG_LikeT(groupId, title, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset list entries where groupId = &#63; and title LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param title the title
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset list entries
	 */
	@Override
	public List<AssetListEntry> findByG_LikeT(
		long groupId, String title, int start, int end,
		OrderByComparator<AssetListEntry> orderByComparator) {

		return findByG_LikeT(
			groupId, title, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset list entries where groupId = &#63; and title LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param title the title
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset list entries
	 */
	@Override
	public List<AssetListEntry> findByG_LikeT(
		long groupId, String title, int start, int end,
		OrderByComparator<AssetListEntry> orderByComparator,
		boolean useFinderCache) {

		title = Objects.toString(title, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetListEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByG_LikeT;
		finderArgs = new Object[] {
			groupId, title, start, end, orderByComparator
		};

		List<AssetListEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<AssetListEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetListEntry assetListEntry : list) {
					if ((groupId != assetListEntry.getGroupId()) ||
						!StringUtil.wildcardMatches(
							assetListEntry.getTitle(), title, '_', '%', '\\',
							true)) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(4);
			}

			sb.append(_SQL_SELECT_ASSETLISTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_LIKET_GROUPID_2);

			boolean bindTitle = false;

			if (title.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_LIKET_TITLE_3);
			}
			else {
				bindTitle = true;

				sb.append(_FINDER_COLUMN_G_LIKET_TITLE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AssetListEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindTitle) {
					queryPos.add(title);
				}

				list = (List<AssetListEntry>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first asset list entry in the ordered set where groupId = &#63; and title LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param title the title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry
	 * @throws NoSuchEntryException if a matching asset list entry could not be found
	 */
	@Override
	public AssetListEntry findByG_LikeT_First(
			long groupId, String title,
			OrderByComparator<AssetListEntry> orderByComparator)
		throws NoSuchEntryException {

		AssetListEntry assetListEntry = fetchByG_LikeT_First(
			groupId, title, orderByComparator);

		if (assetListEntry != null) {
			return assetListEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", titleLIKE");
		sb.append(title);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first asset list entry in the ordered set where groupId = &#63; and title LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param title the title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry, or <code>null</code> if a matching asset list entry could not be found
	 */
	@Override
	public AssetListEntry fetchByG_LikeT_First(
		long groupId, String title,
		OrderByComparator<AssetListEntry> orderByComparator) {

		List<AssetListEntry> list = findByG_LikeT(
			groupId, title, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset list entry in the ordered set where groupId = &#63; and title LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param title the title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry
	 * @throws NoSuchEntryException if a matching asset list entry could not be found
	 */
	@Override
	public AssetListEntry findByG_LikeT_Last(
			long groupId, String title,
			OrderByComparator<AssetListEntry> orderByComparator)
		throws NoSuchEntryException {

		AssetListEntry assetListEntry = fetchByG_LikeT_Last(
			groupId, title, orderByComparator);

		if (assetListEntry != null) {
			return assetListEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", titleLIKE");
		sb.append(title);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last asset list entry in the ordered set where groupId = &#63; and title LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param title the title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry, or <code>null</code> if a matching asset list entry could not be found
	 */
	@Override
	public AssetListEntry fetchByG_LikeT_Last(
		long groupId, String title,
		OrderByComparator<AssetListEntry> orderByComparator) {

		int count = countByG_LikeT(groupId, title);

		if (count == 0) {
			return null;
		}

		List<AssetListEntry> list = findByG_LikeT(
			groupId, title, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset list entries before and after the current asset list entry in the ordered set where groupId = &#63; and title LIKE &#63;.
	 *
	 * @param assetListEntryId the primary key of the current asset list entry
	 * @param groupId the group ID
	 * @param title the title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset list entry
	 * @throws NoSuchEntryException if a asset list entry with the primary key could not be found
	 */
	@Override
	public AssetListEntry[] findByG_LikeT_PrevAndNext(
			long assetListEntryId, long groupId, String title,
			OrderByComparator<AssetListEntry> orderByComparator)
		throws NoSuchEntryException {

		title = Objects.toString(title, "");

		AssetListEntry assetListEntry = findByPrimaryKey(assetListEntryId);

		Session session = null;

		try {
			session = openSession();

			AssetListEntry[] array = new AssetListEntryImpl[3];

			array[0] = getByG_LikeT_PrevAndNext(
				session, assetListEntry, groupId, title, orderByComparator,
				true);

			array[1] = assetListEntry;

			array[2] = getByG_LikeT_PrevAndNext(
				session, assetListEntry, groupId, title, orderByComparator,
				false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetListEntry getByG_LikeT_PrevAndNext(
		Session session, AssetListEntry assetListEntry, long groupId,
		String title, OrderByComparator<AssetListEntry> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_ASSETLISTENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_LIKET_GROUPID_2);

		boolean bindTitle = false;

		if (title.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_LIKET_TITLE_3);
		}
		else {
			bindTitle = true;

			sb.append(_FINDER_COLUMN_G_LIKET_TITLE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(AssetListEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		if (bindTitle) {
			queryPos.add(title);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						assetListEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AssetListEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the asset list entries that the user has permission to view where groupId = &#63; and title LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param title the title
	 * @return the matching asset list entries that the user has permission to view
	 */
	@Override
	public List<AssetListEntry> filterFindByG_LikeT(
		long groupId, String title) {

		return filterFindByG_LikeT(
			groupId, title, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset list entries that the user has permission to view where groupId = &#63; and title LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param title the title
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @return the range of matching asset list entries that the user has permission to view
	 */
	@Override
	public List<AssetListEntry> filterFindByG_LikeT(
		long groupId, String title, int start, int end) {

		return filterFindByG_LikeT(groupId, title, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset list entries that the user has permissions to view where groupId = &#63; and title LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param title the title
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset list entries that the user has permission to view
	 */
	@Override
	public List<AssetListEntry> filterFindByG_LikeT(
		long groupId, String title, int start, int end,
		OrderByComparator<AssetListEntry> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_LikeT(groupId, title, start, end, orderByComparator);
		}

		title = Objects.toString(title, "");

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_ASSETLISTENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_ASSETLISTENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_LIKET_GROUPID_2);

		boolean bindTitle = false;

		if (title.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_LIKET_TITLE_3);
		}
		else {
			bindTitle = true;

			sb.append(_FINDER_COLUMN_G_LIKET_TITLE_2);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_ASSETLISTENTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(AssetListEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(AssetListEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), AssetListEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, AssetListEntryImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, AssetListEntryImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			if (bindTitle) {
				queryPos.add(title);
			}

			return (List<AssetListEntry>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the asset list entries before and after the current asset list entry in the ordered set of asset list entries that the user has permission to view where groupId = &#63; and title LIKE &#63;.
	 *
	 * @param assetListEntryId the primary key of the current asset list entry
	 * @param groupId the group ID
	 * @param title the title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset list entry
	 * @throws NoSuchEntryException if a asset list entry with the primary key could not be found
	 */
	@Override
	public AssetListEntry[] filterFindByG_LikeT_PrevAndNext(
			long assetListEntryId, long groupId, String title,
			OrderByComparator<AssetListEntry> orderByComparator)
		throws NoSuchEntryException {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_LikeT_PrevAndNext(
				assetListEntryId, groupId, title, orderByComparator);
		}

		title = Objects.toString(title, "");

		AssetListEntry assetListEntry = findByPrimaryKey(assetListEntryId);

		Session session = null;

		try {
			session = openSession();

			AssetListEntry[] array = new AssetListEntryImpl[3];

			array[0] = filterGetByG_LikeT_PrevAndNext(
				session, assetListEntry, groupId, title, orderByComparator,
				true);

			array[1] = assetListEntry;

			array[2] = filterGetByG_LikeT_PrevAndNext(
				session, assetListEntry, groupId, title, orderByComparator,
				false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetListEntry filterGetByG_LikeT_PrevAndNext(
		Session session, AssetListEntry assetListEntry, long groupId,
		String title, OrderByComparator<AssetListEntry> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_ASSETLISTENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_ASSETLISTENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_LIKET_GROUPID_2);

		boolean bindTitle = false;

		if (title.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_LIKET_TITLE_3);
		}
		else {
			bindTitle = true;

			sb.append(_FINDER_COLUMN_G_LIKET_TITLE_2);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_ASSETLISTENTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByConditionFields[i],
							true));
				}
				else {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByConditionFields[i],
							true));
				}

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByFields[i], true));
				}
				else {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByFields[i], true));
				}

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(AssetListEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(AssetListEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), AssetListEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(_FILTER_ENTITY_ALIAS, AssetListEntryImpl.class);
		}
		else {
			sqlQuery.addEntity(_FILTER_ENTITY_TABLE, AssetListEntryImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(groupId);

		if (bindTitle) {
			queryPos.add(title);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						assetListEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AssetListEntry> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the asset list entries that the user has permission to view where groupId = any &#63; and title LIKE &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param title the title
	 * @return the matching asset list entries that the user has permission to view
	 */
	@Override
	public List<AssetListEntry> filterFindByG_LikeT(
		long[] groupIds, String title) {

		return filterFindByG_LikeT(
			groupIds, title, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset list entries that the user has permission to view where groupId = any &#63; and title LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param title the title
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @return the range of matching asset list entries that the user has permission to view
	 */
	@Override
	public List<AssetListEntry> filterFindByG_LikeT(
		long[] groupIds, String title, int start, int end) {

		return filterFindByG_LikeT(groupIds, title, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset list entries that the user has permission to view where groupId = any &#63; and title LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param title the title
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset list entries that the user has permission to view
	 */
	@Override
	public List<AssetListEntry> filterFindByG_LikeT(
		long[] groupIds, String title, int start, int end,
		OrderByComparator<AssetListEntry> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupIds)) {
			return findByG_LikeT(
				groupIds, title, start, end, orderByComparator);
		}

		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.sortedUnique(groupIds);
		}

		title = Objects.toString(title, "");

		StringBundler sb = new StringBundler();

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_ASSETLISTENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_ASSETLISTENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		if (groupIds.length > 0) {
			sb.append("(");

			sb.append(_FINDER_COLUMN_G_LIKET_GROUPID_7);

			sb.append(StringUtil.merge(groupIds));

			sb.append(")");

			sb.append(")");

			sb.append(WHERE_AND);
		}

		boolean bindTitle = false;

		if (title.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_LIKET_TITLE_3);
		}
		else {
			bindTitle = true;

			sb.append(_FINDER_COLUMN_G_LIKET_TITLE_2);
		}

		sb.setStringAt(
			removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_ASSETLISTENTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(AssetListEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(AssetListEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), AssetListEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupIds);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, AssetListEntryImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, AssetListEntryImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			if (bindTitle) {
				queryPos.add(title);
			}

			return (List<AssetListEntry>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns all the asset list entries where groupId = any &#63; and title LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param title the title
	 * @return the matching asset list entries
	 */
	@Override
	public List<AssetListEntry> findByG_LikeT(long[] groupIds, String title) {
		return findByG_LikeT(
			groupIds, title, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset list entries where groupId = any &#63; and title LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param title the title
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @return the range of matching asset list entries
	 */
	@Override
	public List<AssetListEntry> findByG_LikeT(
		long[] groupIds, String title, int start, int end) {

		return findByG_LikeT(groupIds, title, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset list entries where groupId = any &#63; and title LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param title the title
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset list entries
	 */
	@Override
	public List<AssetListEntry> findByG_LikeT(
		long[] groupIds, String title, int start, int end,
		OrderByComparator<AssetListEntry> orderByComparator) {

		return findByG_LikeT(
			groupIds, title, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset list entries where groupId = &#63; and title LIKE &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param title the title
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset list entries
	 */
	@Override
	public List<AssetListEntry> findByG_LikeT(
		long[] groupIds, String title, int start, int end,
		OrderByComparator<AssetListEntry> orderByComparator,
		boolean useFinderCache) {

		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.sortedUnique(groupIds);
		}

		title = Objects.toString(title, "");

		if (groupIds.length == 1) {
			return findByG_LikeT(
				groupIds[0], title, start, end, orderByComparator);
		}

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetListEntry.class);

		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderArgs = new Object[] {StringUtil.merge(groupIds), title};
			}
		}
		else if (useFinderCache && productionMode) {
			finderArgs = new Object[] {
				StringUtil.merge(groupIds), title, start, end, orderByComparator
			};
		}

		List<AssetListEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<AssetListEntry>)finderCache.getResult(
				_finderPathWithPaginationFindByG_LikeT, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetListEntry assetListEntry : list) {
					if (!ArrayUtil.contains(
							groupIds, assetListEntry.getGroupId()) ||
						!StringUtil.wildcardMatches(
							assetListEntry.getTitle(), title, '_', '%', '\\',
							true)) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_SELECT_ASSETLISTENTRY_WHERE);

			if (groupIds.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_G_LIKET_GROUPID_7);

				sb.append(StringUtil.merge(groupIds));

				sb.append(")");

				sb.append(")");

				sb.append(WHERE_AND);
			}

			boolean bindTitle = false;

			if (title.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_LIKET_TITLE_3);
			}
			else {
				bindTitle = true;

				sb.append(_FINDER_COLUMN_G_LIKET_TITLE_2);
			}

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AssetListEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindTitle) {
					queryPos.add(title);
				}

				list = (List<AssetListEntry>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					finderCache.putResult(
						_finderPathWithPaginationFindByG_LikeT, finderArgs,
						list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the asset list entries where groupId = &#63; and title LIKE &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param title the title
	 */
	@Override
	public void removeByG_LikeT(long groupId, String title) {
		for (AssetListEntry assetListEntry :
				findByG_LikeT(
					groupId, title, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(assetListEntry);
		}
	}

	/**
	 * Returns the number of asset list entries where groupId = &#63; and title LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param title the title
	 * @return the number of matching asset list entries
	 */
	@Override
	public int countByG_LikeT(long groupId, String title) {
		title = Objects.toString(title, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetListEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathWithPaginationCountByG_LikeT;

			finderArgs = new Object[] {groupId, title};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_ASSETLISTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_LIKET_GROUPID_2);

			boolean bindTitle = false;

			if (title.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_LIKET_TITLE_3);
			}
			else {
				bindTitle = true;

				sb.append(_FINDER_COLUMN_G_LIKET_TITLE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindTitle) {
					queryPos.add(title);
				}

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of asset list entries where groupId = any &#63; and title LIKE &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param title the title
	 * @return the number of matching asset list entries
	 */
	@Override
	public int countByG_LikeT(long[] groupIds, String title) {
		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.sortedUnique(groupIds);
		}

		title = Objects.toString(title, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetListEntry.class);

		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderArgs = new Object[] {StringUtil.merge(groupIds), title};

			count = (Long)finderCache.getResult(
				_finderPathWithPaginationCountByG_LikeT, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_COUNT_ASSETLISTENTRY_WHERE);

			if (groupIds.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_G_LIKET_GROUPID_7);

				sb.append(StringUtil.merge(groupIds));

				sb.append(")");

				sb.append(")");

				sb.append(WHERE_AND);
			}

			boolean bindTitle = false;

			if (title.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_LIKET_TITLE_3);
			}
			else {
				bindTitle = true;

				sb.append(_FINDER_COLUMN_G_LIKET_TITLE_2);
			}

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindTitle) {
					queryPos.add(title);
				}

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(
						_finderPathWithPaginationCountByG_LikeT, finderArgs,
						count);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of asset list entries that the user has permission to view where groupId = &#63; and title LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param title the title
	 * @return the number of matching asset list entries that the user has permission to view
	 */
	@Override
	public int filterCountByG_LikeT(long groupId, String title) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_LikeT(groupId, title);
		}

		title = Objects.toString(title, "");

		StringBundler sb = new StringBundler(3);

		sb.append(_FILTER_SQL_COUNT_ASSETLISTENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_LIKET_GROUPID_2);

		boolean bindTitle = false;

		if (title.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_LIKET_TITLE_3);
		}
		else {
			bindTitle = true;

			sb.append(_FINDER_COLUMN_G_LIKET_TITLE_2);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), AssetListEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			if (bindTitle) {
				queryPos.add(title);
			}

			Long count = (Long)sqlQuery.uniqueResult();

			return count.intValue();
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the number of asset list entries that the user has permission to view where groupId = any &#63; and title LIKE &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param title the title
	 * @return the number of matching asset list entries that the user has permission to view
	 */
	@Override
	public int filterCountByG_LikeT(long[] groupIds, String title) {
		if (!InlineSQLHelperUtil.isEnabled(groupIds)) {
			return countByG_LikeT(groupIds, title);
		}

		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.sortedUnique(groupIds);
		}

		title = Objects.toString(title, "");

		StringBundler sb = new StringBundler();

		sb.append(_FILTER_SQL_COUNT_ASSETLISTENTRY_WHERE);

		if (groupIds.length > 0) {
			sb.append("(");

			sb.append(_FINDER_COLUMN_G_LIKET_GROUPID_7);

			sb.append(StringUtil.merge(groupIds));

			sb.append(")");

			sb.append(")");

			sb.append(WHERE_AND);
		}

		boolean bindTitle = false;

		if (title.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_LIKET_TITLE_3);
		}
		else {
			bindTitle = true;

			sb.append(_FINDER_COLUMN_G_LIKET_TITLE_2);
		}

		sb.setStringAt(
			removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), AssetListEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupIds);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			if (bindTitle) {
				queryPos.add(title);
			}

			Long count = (Long)sqlQuery.uniqueResult();

			return count.intValue();
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_G_LIKET_GROUPID_2 =
		"assetListEntry.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_LIKET_GROUPID_7 =
		"assetListEntry.groupId IN (";

	private static final String _FINDER_COLUMN_G_LIKET_TITLE_2 =
		"assetListEntry.title LIKE ?";

	private static final String _FINDER_COLUMN_G_LIKET_TITLE_3 =
		"(assetListEntry.title IS NULL OR assetListEntry.title LIKE '')";

	private FinderPath _finderPathWithPaginationFindByG_TY;
	private FinderPath _finderPathWithoutPaginationFindByG_TY;
	private FinderPath _finderPathCountByG_TY;

	/**
	 * Returns all the asset list entries where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @return the matching asset list entries
	 */
	@Override
	public List<AssetListEntry> findByG_TY(long groupId, int type) {
		return findByG_TY(
			groupId, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset list entries where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @return the range of matching asset list entries
	 */
	@Override
	public List<AssetListEntry> findByG_TY(
		long groupId, int type, int start, int end) {

		return findByG_TY(groupId, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset list entries where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset list entries
	 */
	@Override
	public List<AssetListEntry> findByG_TY(
		long groupId, int type, int start, int end,
		OrderByComparator<AssetListEntry> orderByComparator) {

		return findByG_TY(groupId, type, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset list entries where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset list entries
	 */
	@Override
	public List<AssetListEntry> findByG_TY(
		long groupId, int type, int start, int end,
		OrderByComparator<AssetListEntry> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetListEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByG_TY;
				finderArgs = new Object[] {groupId, type};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByG_TY;
			finderArgs = new Object[] {
				groupId, type, start, end, orderByComparator
			};
		}

		List<AssetListEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<AssetListEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetListEntry assetListEntry : list) {
					if ((groupId != assetListEntry.getGroupId()) ||
						(type != assetListEntry.getType())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(4);
			}

			sb.append(_SQL_SELECT_ASSETLISTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_TY_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_TY_TYPE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AssetListEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(type);

				list = (List<AssetListEntry>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first asset list entry in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry
	 * @throws NoSuchEntryException if a matching asset list entry could not be found
	 */
	@Override
	public AssetListEntry findByG_TY_First(
			long groupId, int type,
			OrderByComparator<AssetListEntry> orderByComparator)
		throws NoSuchEntryException {

		AssetListEntry assetListEntry = fetchByG_TY_First(
			groupId, type, orderByComparator);

		if (assetListEntry != null) {
			return assetListEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first asset list entry in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry, or <code>null</code> if a matching asset list entry could not be found
	 */
	@Override
	public AssetListEntry fetchByG_TY_First(
		long groupId, int type,
		OrderByComparator<AssetListEntry> orderByComparator) {

		List<AssetListEntry> list = findByG_TY(
			groupId, type, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset list entry in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry
	 * @throws NoSuchEntryException if a matching asset list entry could not be found
	 */
	@Override
	public AssetListEntry findByG_TY_Last(
			long groupId, int type,
			OrderByComparator<AssetListEntry> orderByComparator)
		throws NoSuchEntryException {

		AssetListEntry assetListEntry = fetchByG_TY_Last(
			groupId, type, orderByComparator);

		if (assetListEntry != null) {
			return assetListEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last asset list entry in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry, or <code>null</code> if a matching asset list entry could not be found
	 */
	@Override
	public AssetListEntry fetchByG_TY_Last(
		long groupId, int type,
		OrderByComparator<AssetListEntry> orderByComparator) {

		int count = countByG_TY(groupId, type);

		if (count == 0) {
			return null;
		}

		List<AssetListEntry> list = findByG_TY(
			groupId, type, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset list entries before and after the current asset list entry in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param assetListEntryId the primary key of the current asset list entry
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset list entry
	 * @throws NoSuchEntryException if a asset list entry with the primary key could not be found
	 */
	@Override
	public AssetListEntry[] findByG_TY_PrevAndNext(
			long assetListEntryId, long groupId, int type,
			OrderByComparator<AssetListEntry> orderByComparator)
		throws NoSuchEntryException {

		AssetListEntry assetListEntry = findByPrimaryKey(assetListEntryId);

		Session session = null;

		try {
			session = openSession();

			AssetListEntry[] array = new AssetListEntryImpl[3];

			array[0] = getByG_TY_PrevAndNext(
				session, assetListEntry, groupId, type, orderByComparator,
				true);

			array[1] = assetListEntry;

			array[2] = getByG_TY_PrevAndNext(
				session, assetListEntry, groupId, type, orderByComparator,
				false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetListEntry getByG_TY_PrevAndNext(
		Session session, AssetListEntry assetListEntry, long groupId, int type,
		OrderByComparator<AssetListEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_ASSETLISTENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_TY_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_TY_TYPE_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(AssetListEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(type);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						assetListEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AssetListEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the asset list entries that the user has permission to view where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @return the matching asset list entries that the user has permission to view
	 */
	@Override
	public List<AssetListEntry> filterFindByG_TY(long groupId, int type) {
		return filterFindByG_TY(
			groupId, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset list entries that the user has permission to view where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @return the range of matching asset list entries that the user has permission to view
	 */
	@Override
	public List<AssetListEntry> filterFindByG_TY(
		long groupId, int type, int start, int end) {

		return filterFindByG_TY(groupId, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset list entries that the user has permissions to view where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset list entries that the user has permission to view
	 */
	@Override
	public List<AssetListEntry> filterFindByG_TY(
		long groupId, int type, int start, int end,
		OrderByComparator<AssetListEntry> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_TY(groupId, type, start, end, orderByComparator);
		}

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_ASSETLISTENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_ASSETLISTENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_TY_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_TY_TYPE_2_SQL);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_ASSETLISTENTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(AssetListEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(AssetListEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), AssetListEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, AssetListEntryImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, AssetListEntryImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(type);

			return (List<AssetListEntry>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the asset list entries before and after the current asset list entry in the ordered set of asset list entries that the user has permission to view where groupId = &#63; and type = &#63;.
	 *
	 * @param assetListEntryId the primary key of the current asset list entry
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset list entry
	 * @throws NoSuchEntryException if a asset list entry with the primary key could not be found
	 */
	@Override
	public AssetListEntry[] filterFindByG_TY_PrevAndNext(
			long assetListEntryId, long groupId, int type,
			OrderByComparator<AssetListEntry> orderByComparator)
		throws NoSuchEntryException {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_TY_PrevAndNext(
				assetListEntryId, groupId, type, orderByComparator);
		}

		AssetListEntry assetListEntry = findByPrimaryKey(assetListEntryId);

		Session session = null;

		try {
			session = openSession();

			AssetListEntry[] array = new AssetListEntryImpl[3];

			array[0] = filterGetByG_TY_PrevAndNext(
				session, assetListEntry, groupId, type, orderByComparator,
				true);

			array[1] = assetListEntry;

			array[2] = filterGetByG_TY_PrevAndNext(
				session, assetListEntry, groupId, type, orderByComparator,
				false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetListEntry filterGetByG_TY_PrevAndNext(
		Session session, AssetListEntry assetListEntry, long groupId, int type,
		OrderByComparator<AssetListEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_ASSETLISTENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_ASSETLISTENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_TY_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_TY_TYPE_2_SQL);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_ASSETLISTENTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByConditionFields[i],
							true));
				}
				else {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByConditionFields[i],
							true));
				}

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByFields[i], true));
				}
				else {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByFields[i], true));
				}

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(AssetListEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(AssetListEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), AssetListEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(_FILTER_ENTITY_ALIAS, AssetListEntryImpl.class);
		}
		else {
			sqlQuery.addEntity(_FILTER_ENTITY_TABLE, AssetListEntryImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(groupId);

		queryPos.add(type);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						assetListEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AssetListEntry> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset list entries where groupId = &#63; and type = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 */
	@Override
	public void removeByG_TY(long groupId, int type) {
		for (AssetListEntry assetListEntry :
				findByG_TY(
					groupId, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(assetListEntry);
		}
	}

	/**
	 * Returns the number of asset list entries where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @return the number of matching asset list entries
	 */
	@Override
	public int countByG_TY(long groupId, int type) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetListEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_TY;

			finderArgs = new Object[] {groupId, type};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_ASSETLISTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_TY_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_TY_TYPE_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(type);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of asset list entries that the user has permission to view where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @return the number of matching asset list entries that the user has permission to view
	 */
	@Override
	public int filterCountByG_TY(long groupId, int type) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_TY(groupId, type);
		}

		StringBundler sb = new StringBundler(3);

		sb.append(_FILTER_SQL_COUNT_ASSETLISTENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_TY_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_TY_TYPE_2_SQL);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), AssetListEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(type);

			Long count = (Long)sqlQuery.uniqueResult();

			return count.intValue();
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_G_TY_GROUPID_2 =
		"assetListEntry.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_TY_TYPE_2 =
		"assetListEntry.type = ?";

	private static final String _FINDER_COLUMN_G_TY_TYPE_2_SQL =
		"assetListEntry.type_ = ?";

	private FinderPath _finderPathWithPaginationFindByG_AET;
	private FinderPath _finderPathWithoutPaginationFindByG_AET;
	private FinderPath _finderPathCountByG_AET;
	private FinderPath _finderPathWithPaginationCountByG_AET;

	/**
	 * Returns all the asset list entries where groupId = &#63; and assetEntryType = &#63;.
	 *
	 * @param groupId the group ID
	 * @param assetEntryType the asset entry type
	 * @return the matching asset list entries
	 */
	@Override
	public List<AssetListEntry> findByG_AET(
		long groupId, String assetEntryType) {

		return findByG_AET(
			groupId, assetEntryType, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the asset list entries where groupId = &#63; and assetEntryType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param assetEntryType the asset entry type
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @return the range of matching asset list entries
	 */
	@Override
	public List<AssetListEntry> findByG_AET(
		long groupId, String assetEntryType, int start, int end) {

		return findByG_AET(groupId, assetEntryType, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset list entries where groupId = &#63; and assetEntryType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param assetEntryType the asset entry type
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset list entries
	 */
	@Override
	public List<AssetListEntry> findByG_AET(
		long groupId, String assetEntryType, int start, int end,
		OrderByComparator<AssetListEntry> orderByComparator) {

		return findByG_AET(
			groupId, assetEntryType, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset list entries where groupId = &#63; and assetEntryType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param assetEntryType the asset entry type
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset list entries
	 */
	@Override
	public List<AssetListEntry> findByG_AET(
		long groupId, String assetEntryType, int start, int end,
		OrderByComparator<AssetListEntry> orderByComparator,
		boolean useFinderCache) {

		assetEntryType = Objects.toString(assetEntryType, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetListEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByG_AET;
				finderArgs = new Object[] {groupId, assetEntryType};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByG_AET;
			finderArgs = new Object[] {
				groupId, assetEntryType, start, end, orderByComparator
			};
		}

		List<AssetListEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<AssetListEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetListEntry assetListEntry : list) {
					if ((groupId != assetListEntry.getGroupId()) ||
						!assetEntryType.equals(
							assetListEntry.getAssetEntryType())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(4);
			}

			sb.append(_SQL_SELECT_ASSETLISTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_AET_GROUPID_2);

			boolean bindAssetEntryType = false;

			if (assetEntryType.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_AET_ASSETENTRYTYPE_3);
			}
			else {
				bindAssetEntryType = true;

				sb.append(_FINDER_COLUMN_G_AET_ASSETENTRYTYPE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AssetListEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindAssetEntryType) {
					queryPos.add(assetEntryType);
				}

				list = (List<AssetListEntry>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first asset list entry in the ordered set where groupId = &#63; and assetEntryType = &#63;.
	 *
	 * @param groupId the group ID
	 * @param assetEntryType the asset entry type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry
	 * @throws NoSuchEntryException if a matching asset list entry could not be found
	 */
	@Override
	public AssetListEntry findByG_AET_First(
			long groupId, String assetEntryType,
			OrderByComparator<AssetListEntry> orderByComparator)
		throws NoSuchEntryException {

		AssetListEntry assetListEntry = fetchByG_AET_First(
			groupId, assetEntryType, orderByComparator);

		if (assetListEntry != null) {
			return assetListEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", assetEntryType=");
		sb.append(assetEntryType);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first asset list entry in the ordered set where groupId = &#63; and assetEntryType = &#63;.
	 *
	 * @param groupId the group ID
	 * @param assetEntryType the asset entry type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry, or <code>null</code> if a matching asset list entry could not be found
	 */
	@Override
	public AssetListEntry fetchByG_AET_First(
		long groupId, String assetEntryType,
		OrderByComparator<AssetListEntry> orderByComparator) {

		List<AssetListEntry> list = findByG_AET(
			groupId, assetEntryType, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset list entry in the ordered set where groupId = &#63; and assetEntryType = &#63;.
	 *
	 * @param groupId the group ID
	 * @param assetEntryType the asset entry type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry
	 * @throws NoSuchEntryException if a matching asset list entry could not be found
	 */
	@Override
	public AssetListEntry findByG_AET_Last(
			long groupId, String assetEntryType,
			OrderByComparator<AssetListEntry> orderByComparator)
		throws NoSuchEntryException {

		AssetListEntry assetListEntry = fetchByG_AET_Last(
			groupId, assetEntryType, orderByComparator);

		if (assetListEntry != null) {
			return assetListEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", assetEntryType=");
		sb.append(assetEntryType);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last asset list entry in the ordered set where groupId = &#63; and assetEntryType = &#63;.
	 *
	 * @param groupId the group ID
	 * @param assetEntryType the asset entry type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry, or <code>null</code> if a matching asset list entry could not be found
	 */
	@Override
	public AssetListEntry fetchByG_AET_Last(
		long groupId, String assetEntryType,
		OrderByComparator<AssetListEntry> orderByComparator) {

		int count = countByG_AET(groupId, assetEntryType);

		if (count == 0) {
			return null;
		}

		List<AssetListEntry> list = findByG_AET(
			groupId, assetEntryType, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset list entries before and after the current asset list entry in the ordered set where groupId = &#63; and assetEntryType = &#63;.
	 *
	 * @param assetListEntryId the primary key of the current asset list entry
	 * @param groupId the group ID
	 * @param assetEntryType the asset entry type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset list entry
	 * @throws NoSuchEntryException if a asset list entry with the primary key could not be found
	 */
	@Override
	public AssetListEntry[] findByG_AET_PrevAndNext(
			long assetListEntryId, long groupId, String assetEntryType,
			OrderByComparator<AssetListEntry> orderByComparator)
		throws NoSuchEntryException {

		assetEntryType = Objects.toString(assetEntryType, "");

		AssetListEntry assetListEntry = findByPrimaryKey(assetListEntryId);

		Session session = null;

		try {
			session = openSession();

			AssetListEntry[] array = new AssetListEntryImpl[3];

			array[0] = getByG_AET_PrevAndNext(
				session, assetListEntry, groupId, assetEntryType,
				orderByComparator, true);

			array[1] = assetListEntry;

			array[2] = getByG_AET_PrevAndNext(
				session, assetListEntry, groupId, assetEntryType,
				orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetListEntry getByG_AET_PrevAndNext(
		Session session, AssetListEntry assetListEntry, long groupId,
		String assetEntryType,
		OrderByComparator<AssetListEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_ASSETLISTENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_AET_GROUPID_2);

		boolean bindAssetEntryType = false;

		if (assetEntryType.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_AET_ASSETENTRYTYPE_3);
		}
		else {
			bindAssetEntryType = true;

			sb.append(_FINDER_COLUMN_G_AET_ASSETENTRYTYPE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(AssetListEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		if (bindAssetEntryType) {
			queryPos.add(assetEntryType);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						assetListEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AssetListEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the asset list entries that the user has permission to view where groupId = &#63; and assetEntryType = &#63;.
	 *
	 * @param groupId the group ID
	 * @param assetEntryType the asset entry type
	 * @return the matching asset list entries that the user has permission to view
	 */
	@Override
	public List<AssetListEntry> filterFindByG_AET(
		long groupId, String assetEntryType) {

		return filterFindByG_AET(
			groupId, assetEntryType, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the asset list entries that the user has permission to view where groupId = &#63; and assetEntryType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param assetEntryType the asset entry type
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @return the range of matching asset list entries that the user has permission to view
	 */
	@Override
	public List<AssetListEntry> filterFindByG_AET(
		long groupId, String assetEntryType, int start, int end) {

		return filterFindByG_AET(groupId, assetEntryType, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset list entries that the user has permissions to view where groupId = &#63; and assetEntryType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param assetEntryType the asset entry type
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset list entries that the user has permission to view
	 */
	@Override
	public List<AssetListEntry> filterFindByG_AET(
		long groupId, String assetEntryType, int start, int end,
		OrderByComparator<AssetListEntry> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_AET(
				groupId, assetEntryType, start, end, orderByComparator);
		}

		assetEntryType = Objects.toString(assetEntryType, "");

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_ASSETLISTENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_ASSETLISTENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_AET_GROUPID_2);

		boolean bindAssetEntryType = false;

		if (assetEntryType.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_AET_ASSETENTRYTYPE_3);
		}
		else {
			bindAssetEntryType = true;

			sb.append(_FINDER_COLUMN_G_AET_ASSETENTRYTYPE_2);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_ASSETLISTENTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(AssetListEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(AssetListEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), AssetListEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, AssetListEntryImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, AssetListEntryImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			if (bindAssetEntryType) {
				queryPos.add(assetEntryType);
			}

			return (List<AssetListEntry>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the asset list entries before and after the current asset list entry in the ordered set of asset list entries that the user has permission to view where groupId = &#63; and assetEntryType = &#63;.
	 *
	 * @param assetListEntryId the primary key of the current asset list entry
	 * @param groupId the group ID
	 * @param assetEntryType the asset entry type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset list entry
	 * @throws NoSuchEntryException if a asset list entry with the primary key could not be found
	 */
	@Override
	public AssetListEntry[] filterFindByG_AET_PrevAndNext(
			long assetListEntryId, long groupId, String assetEntryType,
			OrderByComparator<AssetListEntry> orderByComparator)
		throws NoSuchEntryException {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_AET_PrevAndNext(
				assetListEntryId, groupId, assetEntryType, orderByComparator);
		}

		assetEntryType = Objects.toString(assetEntryType, "");

		AssetListEntry assetListEntry = findByPrimaryKey(assetListEntryId);

		Session session = null;

		try {
			session = openSession();

			AssetListEntry[] array = new AssetListEntryImpl[3];

			array[0] = filterGetByG_AET_PrevAndNext(
				session, assetListEntry, groupId, assetEntryType,
				orderByComparator, true);

			array[1] = assetListEntry;

			array[2] = filterGetByG_AET_PrevAndNext(
				session, assetListEntry, groupId, assetEntryType,
				orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetListEntry filterGetByG_AET_PrevAndNext(
		Session session, AssetListEntry assetListEntry, long groupId,
		String assetEntryType,
		OrderByComparator<AssetListEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_ASSETLISTENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_ASSETLISTENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_AET_GROUPID_2);

		boolean bindAssetEntryType = false;

		if (assetEntryType.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_AET_ASSETENTRYTYPE_3);
		}
		else {
			bindAssetEntryType = true;

			sb.append(_FINDER_COLUMN_G_AET_ASSETENTRYTYPE_2);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_ASSETLISTENTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByConditionFields[i],
							true));
				}
				else {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByConditionFields[i],
							true));
				}

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByFields[i], true));
				}
				else {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByFields[i], true));
				}

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(AssetListEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(AssetListEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), AssetListEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(_FILTER_ENTITY_ALIAS, AssetListEntryImpl.class);
		}
		else {
			sqlQuery.addEntity(_FILTER_ENTITY_TABLE, AssetListEntryImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(groupId);

		if (bindAssetEntryType) {
			queryPos.add(assetEntryType);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						assetListEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AssetListEntry> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the asset list entries that the user has permission to view where groupId = any &#63; and assetEntryType = any &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param assetEntryTypes the asset entry types
	 * @return the matching asset list entries that the user has permission to view
	 */
	@Override
	public List<AssetListEntry> filterFindByG_AET(
		long[] groupIds, String[] assetEntryTypes) {

		return filterFindByG_AET(
			groupIds, assetEntryTypes, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the asset list entries that the user has permission to view where groupId = any &#63; and assetEntryType = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param assetEntryTypes the asset entry types
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @return the range of matching asset list entries that the user has permission to view
	 */
	@Override
	public List<AssetListEntry> filterFindByG_AET(
		long[] groupIds, String[] assetEntryTypes, int start, int end) {

		return filterFindByG_AET(groupIds, assetEntryTypes, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset list entries that the user has permission to view where groupId = any &#63; and assetEntryType = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param assetEntryTypes the asset entry types
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset list entries that the user has permission to view
	 */
	@Override
	public List<AssetListEntry> filterFindByG_AET(
		long[] groupIds, String[] assetEntryTypes, int start, int end,
		OrderByComparator<AssetListEntry> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupIds)) {
			return findByG_AET(
				groupIds, assetEntryTypes, start, end, orderByComparator);
		}

		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.sortedUnique(groupIds);
		}

		if (assetEntryTypes == null) {
			assetEntryTypes = new String[0];
		}
		else if (assetEntryTypes.length > 1) {
			for (int i = 0; i < assetEntryTypes.length; i++) {
				assetEntryTypes[i] = Objects.toString(assetEntryTypes[i], "");
			}

			assetEntryTypes = ArrayUtil.sortedUnique(assetEntryTypes);
		}

		StringBundler sb = new StringBundler();

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_ASSETLISTENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_ASSETLISTENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		if (groupIds.length > 0) {
			sb.append("(");

			sb.append(_FINDER_COLUMN_G_AET_GROUPID_7);

			sb.append(StringUtil.merge(groupIds));

			sb.append(")");

			sb.append(")");

			sb.append(WHERE_AND);
		}

		if (assetEntryTypes.length > 0) {
			sb.append("(");

			for (int i = 0; i < assetEntryTypes.length; i++) {
				String assetEntryType = assetEntryTypes[i];

				if (assetEntryType.isEmpty()) {
					sb.append(_FINDER_COLUMN_G_AET_ASSETENTRYTYPE_3);
				}
				else {
					sb.append(_FINDER_COLUMN_G_AET_ASSETENTRYTYPE_2);
				}

				if ((i + 1) < assetEntryTypes.length) {
					sb.append(WHERE_OR);
				}
			}

			sb.append(")");
		}

		sb.setStringAt(
			removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_ASSETLISTENTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(AssetListEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(AssetListEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), AssetListEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupIds);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, AssetListEntryImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, AssetListEntryImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			for (String assetEntryType : assetEntryTypes) {
				if ((assetEntryType != null) && !assetEntryType.isEmpty()) {
					queryPos.add(assetEntryType);
				}
			}

			return (List<AssetListEntry>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns all the asset list entries where groupId = any &#63; and assetEntryType = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param assetEntryTypes the asset entry types
	 * @return the matching asset list entries
	 */
	@Override
	public List<AssetListEntry> findByG_AET(
		long[] groupIds, String[] assetEntryTypes) {

		return findByG_AET(
			groupIds, assetEntryTypes, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the asset list entries where groupId = any &#63; and assetEntryType = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param assetEntryTypes the asset entry types
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @return the range of matching asset list entries
	 */
	@Override
	public List<AssetListEntry> findByG_AET(
		long[] groupIds, String[] assetEntryTypes, int start, int end) {

		return findByG_AET(groupIds, assetEntryTypes, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset list entries where groupId = any &#63; and assetEntryType = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param assetEntryTypes the asset entry types
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset list entries
	 */
	@Override
	public List<AssetListEntry> findByG_AET(
		long[] groupIds, String[] assetEntryTypes, int start, int end,
		OrderByComparator<AssetListEntry> orderByComparator) {

		return findByG_AET(
			groupIds, assetEntryTypes, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset list entries where groupId = &#63; and assetEntryType = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param assetEntryType the asset entry type
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset list entries
	 */
	@Override
	public List<AssetListEntry> findByG_AET(
		long[] groupIds, String[] assetEntryTypes, int start, int end,
		OrderByComparator<AssetListEntry> orderByComparator,
		boolean useFinderCache) {

		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.sortedUnique(groupIds);
		}

		if (assetEntryTypes == null) {
			assetEntryTypes = new String[0];
		}
		else if (assetEntryTypes.length > 1) {
			for (int i = 0; i < assetEntryTypes.length; i++) {
				assetEntryTypes[i] = Objects.toString(assetEntryTypes[i], "");
			}

			assetEntryTypes = ArrayUtil.sortedUnique(assetEntryTypes);
		}

		if ((groupIds.length == 1) && (assetEntryTypes.length == 1)) {
			return findByG_AET(
				groupIds[0], assetEntryTypes[0], start, end, orderByComparator);
		}

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetListEntry.class);

		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderArgs = new Object[] {
					StringUtil.merge(groupIds),
					StringUtil.merge(assetEntryTypes)
				};
			}
		}
		else if (useFinderCache && productionMode) {
			finderArgs = new Object[] {
				StringUtil.merge(groupIds), StringUtil.merge(assetEntryTypes),
				start, end, orderByComparator
			};
		}

		List<AssetListEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<AssetListEntry>)finderCache.getResult(
				_finderPathWithPaginationFindByG_AET, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetListEntry assetListEntry : list) {
					if (!ArrayUtil.contains(
							groupIds, assetListEntry.getGroupId()) ||
						!ArrayUtil.contains(
							assetEntryTypes,
							assetListEntry.getAssetEntryType())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_SELECT_ASSETLISTENTRY_WHERE);

			if (groupIds.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_G_AET_GROUPID_7);

				sb.append(StringUtil.merge(groupIds));

				sb.append(")");

				sb.append(")");

				sb.append(WHERE_AND);
			}

			if (assetEntryTypes.length > 0) {
				sb.append("(");

				for (int i = 0; i < assetEntryTypes.length; i++) {
					String assetEntryType = assetEntryTypes[i];

					if (assetEntryType.isEmpty()) {
						sb.append(_FINDER_COLUMN_G_AET_ASSETENTRYTYPE_3);
					}
					else {
						sb.append(_FINDER_COLUMN_G_AET_ASSETENTRYTYPE_2);
					}

					if ((i + 1) < assetEntryTypes.length) {
						sb.append(WHERE_OR);
					}
				}

				sb.append(")");
			}

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AssetListEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				for (String assetEntryType : assetEntryTypes) {
					if ((assetEntryType != null) && !assetEntryType.isEmpty()) {
						queryPos.add(assetEntryType);
					}
				}

				list = (List<AssetListEntry>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					finderCache.putResult(
						_finderPathWithPaginationFindByG_AET, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the asset list entries where groupId = &#63; and assetEntryType = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param assetEntryType the asset entry type
	 */
	@Override
	public void removeByG_AET(long groupId, String assetEntryType) {
		for (AssetListEntry assetListEntry :
				findByG_AET(
					groupId, assetEntryType, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(assetListEntry);
		}
	}

	/**
	 * Returns the number of asset list entries where groupId = &#63; and assetEntryType = &#63;.
	 *
	 * @param groupId the group ID
	 * @param assetEntryType the asset entry type
	 * @return the number of matching asset list entries
	 */
	@Override
	public int countByG_AET(long groupId, String assetEntryType) {
		assetEntryType = Objects.toString(assetEntryType, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetListEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_AET;

			finderArgs = new Object[] {groupId, assetEntryType};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_ASSETLISTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_AET_GROUPID_2);

			boolean bindAssetEntryType = false;

			if (assetEntryType.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_AET_ASSETENTRYTYPE_3);
			}
			else {
				bindAssetEntryType = true;

				sb.append(_FINDER_COLUMN_G_AET_ASSETENTRYTYPE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindAssetEntryType) {
					queryPos.add(assetEntryType);
				}

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of asset list entries where groupId = any &#63; and assetEntryType = any &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param assetEntryTypes the asset entry types
	 * @return the number of matching asset list entries
	 */
	@Override
	public int countByG_AET(long[] groupIds, String[] assetEntryTypes) {
		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.sortedUnique(groupIds);
		}

		if (assetEntryTypes == null) {
			assetEntryTypes = new String[0];
		}
		else if (assetEntryTypes.length > 1) {
			for (int i = 0; i < assetEntryTypes.length; i++) {
				assetEntryTypes[i] = Objects.toString(assetEntryTypes[i], "");
			}

			assetEntryTypes = ArrayUtil.sortedUnique(assetEntryTypes);
		}

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetListEntry.class);

		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderArgs = new Object[] {
				StringUtil.merge(groupIds), StringUtil.merge(assetEntryTypes)
			};

			count = (Long)finderCache.getResult(
				_finderPathWithPaginationCountByG_AET, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_COUNT_ASSETLISTENTRY_WHERE);

			if (groupIds.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_G_AET_GROUPID_7);

				sb.append(StringUtil.merge(groupIds));

				sb.append(")");

				sb.append(")");

				sb.append(WHERE_AND);
			}

			if (assetEntryTypes.length > 0) {
				sb.append("(");

				for (int i = 0; i < assetEntryTypes.length; i++) {
					String assetEntryType = assetEntryTypes[i];

					if (assetEntryType.isEmpty()) {
						sb.append(_FINDER_COLUMN_G_AET_ASSETENTRYTYPE_3);
					}
					else {
						sb.append(_FINDER_COLUMN_G_AET_ASSETENTRYTYPE_2);
					}

					if ((i + 1) < assetEntryTypes.length) {
						sb.append(WHERE_OR);
					}
				}

				sb.append(")");
			}

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				for (String assetEntryType : assetEntryTypes) {
					if ((assetEntryType != null) && !assetEntryType.isEmpty()) {
						queryPos.add(assetEntryType);
					}
				}

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(
						_finderPathWithPaginationCountByG_AET, finderArgs,
						count);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of asset list entries that the user has permission to view where groupId = &#63; and assetEntryType = &#63;.
	 *
	 * @param groupId the group ID
	 * @param assetEntryType the asset entry type
	 * @return the number of matching asset list entries that the user has permission to view
	 */
	@Override
	public int filterCountByG_AET(long groupId, String assetEntryType) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_AET(groupId, assetEntryType);
		}

		assetEntryType = Objects.toString(assetEntryType, "");

		StringBundler sb = new StringBundler(3);

		sb.append(_FILTER_SQL_COUNT_ASSETLISTENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_AET_GROUPID_2);

		boolean bindAssetEntryType = false;

		if (assetEntryType.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_AET_ASSETENTRYTYPE_3);
		}
		else {
			bindAssetEntryType = true;

			sb.append(_FINDER_COLUMN_G_AET_ASSETENTRYTYPE_2);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), AssetListEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			if (bindAssetEntryType) {
				queryPos.add(assetEntryType);
			}

			Long count = (Long)sqlQuery.uniqueResult();

			return count.intValue();
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the number of asset list entries that the user has permission to view where groupId = any &#63; and assetEntryType = any &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param assetEntryTypes the asset entry types
	 * @return the number of matching asset list entries that the user has permission to view
	 */
	@Override
	public int filterCountByG_AET(long[] groupIds, String[] assetEntryTypes) {
		if (!InlineSQLHelperUtil.isEnabled(groupIds)) {
			return countByG_AET(groupIds, assetEntryTypes);
		}

		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.sortedUnique(groupIds);
		}

		if (assetEntryTypes == null) {
			assetEntryTypes = new String[0];
		}
		else if (assetEntryTypes.length > 1) {
			for (int i = 0; i < assetEntryTypes.length; i++) {
				assetEntryTypes[i] = Objects.toString(assetEntryTypes[i], "");
			}

			assetEntryTypes = ArrayUtil.sortedUnique(assetEntryTypes);
		}

		StringBundler sb = new StringBundler();

		sb.append(_FILTER_SQL_COUNT_ASSETLISTENTRY_WHERE);

		if (groupIds.length > 0) {
			sb.append("(");

			sb.append(_FINDER_COLUMN_G_AET_GROUPID_7);

			sb.append(StringUtil.merge(groupIds));

			sb.append(")");

			sb.append(")");

			sb.append(WHERE_AND);
		}

		if (assetEntryTypes.length > 0) {
			sb.append("(");

			for (int i = 0; i < assetEntryTypes.length; i++) {
				String assetEntryType = assetEntryTypes[i];

				if (assetEntryType.isEmpty()) {
					sb.append(_FINDER_COLUMN_G_AET_ASSETENTRYTYPE_3);
				}
				else {
					sb.append(_FINDER_COLUMN_G_AET_ASSETENTRYTYPE_2);
				}

				if ((i + 1) < assetEntryTypes.length) {
					sb.append(WHERE_OR);
				}
			}

			sb.append(")");
		}

		sb.setStringAt(
			removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), AssetListEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupIds);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			for (String assetEntryType : assetEntryTypes) {
				if ((assetEntryType != null) && !assetEntryType.isEmpty()) {
					queryPos.add(assetEntryType);
				}
			}

			Long count = (Long)sqlQuery.uniqueResult();

			return count.intValue();
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_G_AET_GROUPID_2 =
		"assetListEntry.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_AET_GROUPID_7 =
		"assetListEntry.groupId IN (";

	private static final String _FINDER_COLUMN_G_AET_ASSETENTRYTYPE_2 =
		"assetListEntry.assetEntryType = ?";

	private static final String _FINDER_COLUMN_G_AET_ASSETENTRYTYPE_3 =
		"(assetListEntry.assetEntryType IS NULL OR assetListEntry.assetEntryType = '')";

	private FinderPath _finderPathWithPaginationFindByG_LikeT_AET;
	private FinderPath _finderPathWithPaginationCountByG_LikeT_AET;

	/**
	 * Returns all the asset list entries where groupId = &#63; and title LIKE &#63; and assetEntryType = &#63;.
	 *
	 * @param groupId the group ID
	 * @param title the title
	 * @param assetEntryType the asset entry type
	 * @return the matching asset list entries
	 */
	@Override
	public List<AssetListEntry> findByG_LikeT_AET(
		long groupId, String title, String assetEntryType) {

		return findByG_LikeT_AET(
			groupId, title, assetEntryType, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset list entries where groupId = &#63; and title LIKE &#63; and assetEntryType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param title the title
	 * @param assetEntryType the asset entry type
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @return the range of matching asset list entries
	 */
	@Override
	public List<AssetListEntry> findByG_LikeT_AET(
		long groupId, String title, String assetEntryType, int start, int end) {

		return findByG_LikeT_AET(
			groupId, title, assetEntryType, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset list entries where groupId = &#63; and title LIKE &#63; and assetEntryType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param title the title
	 * @param assetEntryType the asset entry type
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset list entries
	 */
	@Override
	public List<AssetListEntry> findByG_LikeT_AET(
		long groupId, String title, String assetEntryType, int start, int end,
		OrderByComparator<AssetListEntry> orderByComparator) {

		return findByG_LikeT_AET(
			groupId, title, assetEntryType, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the asset list entries where groupId = &#63; and title LIKE &#63; and assetEntryType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param title the title
	 * @param assetEntryType the asset entry type
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset list entries
	 */
	@Override
	public List<AssetListEntry> findByG_LikeT_AET(
		long groupId, String title, String assetEntryType, int start, int end,
		OrderByComparator<AssetListEntry> orderByComparator,
		boolean useFinderCache) {

		title = Objects.toString(title, "");
		assetEntryType = Objects.toString(assetEntryType, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetListEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByG_LikeT_AET;
		finderArgs = new Object[] {
			groupId, title, assetEntryType, start, end, orderByComparator
		};

		List<AssetListEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<AssetListEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetListEntry assetListEntry : list) {
					if ((groupId != assetListEntry.getGroupId()) ||
						!StringUtil.wildcardMatches(
							assetListEntry.getTitle(), title, '_', '%', '\\',
							true) ||
						!assetEntryType.equals(
							assetListEntry.getAssetEntryType())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					5 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(5);
			}

			sb.append(_SQL_SELECT_ASSETLISTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_LIKET_AET_GROUPID_2);

			boolean bindTitle = false;

			if (title.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_LIKET_AET_TITLE_3);
			}
			else {
				bindTitle = true;

				sb.append(_FINDER_COLUMN_G_LIKET_AET_TITLE_2);
			}

			boolean bindAssetEntryType = false;

			if (assetEntryType.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_LIKET_AET_ASSETENTRYTYPE_3);
			}
			else {
				bindAssetEntryType = true;

				sb.append(_FINDER_COLUMN_G_LIKET_AET_ASSETENTRYTYPE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AssetListEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindTitle) {
					queryPos.add(title);
				}

				if (bindAssetEntryType) {
					queryPos.add(assetEntryType);
				}

				list = (List<AssetListEntry>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first asset list entry in the ordered set where groupId = &#63; and title LIKE &#63; and assetEntryType = &#63;.
	 *
	 * @param groupId the group ID
	 * @param title the title
	 * @param assetEntryType the asset entry type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry
	 * @throws NoSuchEntryException if a matching asset list entry could not be found
	 */
	@Override
	public AssetListEntry findByG_LikeT_AET_First(
			long groupId, String title, String assetEntryType,
			OrderByComparator<AssetListEntry> orderByComparator)
		throws NoSuchEntryException {

		AssetListEntry assetListEntry = fetchByG_LikeT_AET_First(
			groupId, title, assetEntryType, orderByComparator);

		if (assetListEntry != null) {
			return assetListEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", titleLIKE");
		sb.append(title);

		sb.append(", assetEntryType=");
		sb.append(assetEntryType);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first asset list entry in the ordered set where groupId = &#63; and title LIKE &#63; and assetEntryType = &#63;.
	 *
	 * @param groupId the group ID
	 * @param title the title
	 * @param assetEntryType the asset entry type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry, or <code>null</code> if a matching asset list entry could not be found
	 */
	@Override
	public AssetListEntry fetchByG_LikeT_AET_First(
		long groupId, String title, String assetEntryType,
		OrderByComparator<AssetListEntry> orderByComparator) {

		List<AssetListEntry> list = findByG_LikeT_AET(
			groupId, title, assetEntryType, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset list entry in the ordered set where groupId = &#63; and title LIKE &#63; and assetEntryType = &#63;.
	 *
	 * @param groupId the group ID
	 * @param title the title
	 * @param assetEntryType the asset entry type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry
	 * @throws NoSuchEntryException if a matching asset list entry could not be found
	 */
	@Override
	public AssetListEntry findByG_LikeT_AET_Last(
			long groupId, String title, String assetEntryType,
			OrderByComparator<AssetListEntry> orderByComparator)
		throws NoSuchEntryException {

		AssetListEntry assetListEntry = fetchByG_LikeT_AET_Last(
			groupId, title, assetEntryType, orderByComparator);

		if (assetListEntry != null) {
			return assetListEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", titleLIKE");
		sb.append(title);

		sb.append(", assetEntryType=");
		sb.append(assetEntryType);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last asset list entry in the ordered set where groupId = &#63; and title LIKE &#63; and assetEntryType = &#63;.
	 *
	 * @param groupId the group ID
	 * @param title the title
	 * @param assetEntryType the asset entry type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry, or <code>null</code> if a matching asset list entry could not be found
	 */
	@Override
	public AssetListEntry fetchByG_LikeT_AET_Last(
		long groupId, String title, String assetEntryType,
		OrderByComparator<AssetListEntry> orderByComparator) {

		int count = countByG_LikeT_AET(groupId, title, assetEntryType);

		if (count == 0) {
			return null;
		}

		List<AssetListEntry> list = findByG_LikeT_AET(
			groupId, title, assetEntryType, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset list entries before and after the current asset list entry in the ordered set where groupId = &#63; and title LIKE &#63; and assetEntryType = &#63;.
	 *
	 * @param assetListEntryId the primary key of the current asset list entry
	 * @param groupId the group ID
	 * @param title the title
	 * @param assetEntryType the asset entry type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset list entry
	 * @throws NoSuchEntryException if a asset list entry with the primary key could not be found
	 */
	@Override
	public AssetListEntry[] findByG_LikeT_AET_PrevAndNext(
			long assetListEntryId, long groupId, String title,
			String assetEntryType,
			OrderByComparator<AssetListEntry> orderByComparator)
		throws NoSuchEntryException {

		title = Objects.toString(title, "");
		assetEntryType = Objects.toString(assetEntryType, "");

		AssetListEntry assetListEntry = findByPrimaryKey(assetListEntryId);

		Session session = null;

		try {
			session = openSession();

			AssetListEntry[] array = new AssetListEntryImpl[3];

			array[0] = getByG_LikeT_AET_PrevAndNext(
				session, assetListEntry, groupId, title, assetEntryType,
				orderByComparator, true);

			array[1] = assetListEntry;

			array[2] = getByG_LikeT_AET_PrevAndNext(
				session, assetListEntry, groupId, title, assetEntryType,
				orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetListEntry getByG_LikeT_AET_PrevAndNext(
		Session session, AssetListEntry assetListEntry, long groupId,
		String title, String assetEntryType,
		OrderByComparator<AssetListEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		sb.append(_SQL_SELECT_ASSETLISTENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_LIKET_AET_GROUPID_2);

		boolean bindTitle = false;

		if (title.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_LIKET_AET_TITLE_3);
		}
		else {
			bindTitle = true;

			sb.append(_FINDER_COLUMN_G_LIKET_AET_TITLE_2);
		}

		boolean bindAssetEntryType = false;

		if (assetEntryType.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_LIKET_AET_ASSETENTRYTYPE_3);
		}
		else {
			bindAssetEntryType = true;

			sb.append(_FINDER_COLUMN_G_LIKET_AET_ASSETENTRYTYPE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(AssetListEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		if (bindTitle) {
			queryPos.add(title);
		}

		if (bindAssetEntryType) {
			queryPos.add(assetEntryType);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						assetListEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AssetListEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the asset list entries that the user has permission to view where groupId = &#63; and title LIKE &#63; and assetEntryType = &#63;.
	 *
	 * @param groupId the group ID
	 * @param title the title
	 * @param assetEntryType the asset entry type
	 * @return the matching asset list entries that the user has permission to view
	 */
	@Override
	public List<AssetListEntry> filterFindByG_LikeT_AET(
		long groupId, String title, String assetEntryType) {

		return filterFindByG_LikeT_AET(
			groupId, title, assetEntryType, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset list entries that the user has permission to view where groupId = &#63; and title LIKE &#63; and assetEntryType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param title the title
	 * @param assetEntryType the asset entry type
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @return the range of matching asset list entries that the user has permission to view
	 */
	@Override
	public List<AssetListEntry> filterFindByG_LikeT_AET(
		long groupId, String title, String assetEntryType, int start, int end) {

		return filterFindByG_LikeT_AET(
			groupId, title, assetEntryType, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset list entries that the user has permissions to view where groupId = &#63; and title LIKE &#63; and assetEntryType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param title the title
	 * @param assetEntryType the asset entry type
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset list entries that the user has permission to view
	 */
	@Override
	public List<AssetListEntry> filterFindByG_LikeT_AET(
		long groupId, String title, String assetEntryType, int start, int end,
		OrderByComparator<AssetListEntry> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_LikeT_AET(
				groupId, title, assetEntryType, start, end, orderByComparator);
		}

		title = Objects.toString(title, "");
		assetEntryType = Objects.toString(assetEntryType, "");

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(6);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_ASSETLISTENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_ASSETLISTENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_LIKET_AET_GROUPID_2);

		boolean bindTitle = false;

		if (title.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_LIKET_AET_TITLE_3);
		}
		else {
			bindTitle = true;

			sb.append(_FINDER_COLUMN_G_LIKET_AET_TITLE_2);
		}

		boolean bindAssetEntryType = false;

		if (assetEntryType.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_LIKET_AET_ASSETENTRYTYPE_3);
		}
		else {
			bindAssetEntryType = true;

			sb.append(_FINDER_COLUMN_G_LIKET_AET_ASSETENTRYTYPE_2);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_ASSETLISTENTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(AssetListEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(AssetListEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), AssetListEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, AssetListEntryImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, AssetListEntryImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			if (bindTitle) {
				queryPos.add(title);
			}

			if (bindAssetEntryType) {
				queryPos.add(assetEntryType);
			}

			return (List<AssetListEntry>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the asset list entries before and after the current asset list entry in the ordered set of asset list entries that the user has permission to view where groupId = &#63; and title LIKE &#63; and assetEntryType = &#63;.
	 *
	 * @param assetListEntryId the primary key of the current asset list entry
	 * @param groupId the group ID
	 * @param title the title
	 * @param assetEntryType the asset entry type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset list entry
	 * @throws NoSuchEntryException if a asset list entry with the primary key could not be found
	 */
	@Override
	public AssetListEntry[] filterFindByG_LikeT_AET_PrevAndNext(
			long assetListEntryId, long groupId, String title,
			String assetEntryType,
			OrderByComparator<AssetListEntry> orderByComparator)
		throws NoSuchEntryException {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_LikeT_AET_PrevAndNext(
				assetListEntryId, groupId, title, assetEntryType,
				orderByComparator);
		}

		title = Objects.toString(title, "");
		assetEntryType = Objects.toString(assetEntryType, "");

		AssetListEntry assetListEntry = findByPrimaryKey(assetListEntryId);

		Session session = null;

		try {
			session = openSession();

			AssetListEntry[] array = new AssetListEntryImpl[3];

			array[0] = filterGetByG_LikeT_AET_PrevAndNext(
				session, assetListEntry, groupId, title, assetEntryType,
				orderByComparator, true);

			array[1] = assetListEntry;

			array[2] = filterGetByG_LikeT_AET_PrevAndNext(
				session, assetListEntry, groupId, title, assetEntryType,
				orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetListEntry filterGetByG_LikeT_AET_PrevAndNext(
		Session session, AssetListEntry assetListEntry, long groupId,
		String title, String assetEntryType,
		OrderByComparator<AssetListEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				7 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(6);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_ASSETLISTENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_ASSETLISTENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_LIKET_AET_GROUPID_2);

		boolean bindTitle = false;

		if (title.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_LIKET_AET_TITLE_3);
		}
		else {
			bindTitle = true;

			sb.append(_FINDER_COLUMN_G_LIKET_AET_TITLE_2);
		}

		boolean bindAssetEntryType = false;

		if (assetEntryType.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_LIKET_AET_ASSETENTRYTYPE_3);
		}
		else {
			bindAssetEntryType = true;

			sb.append(_FINDER_COLUMN_G_LIKET_AET_ASSETENTRYTYPE_2);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_ASSETLISTENTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByConditionFields[i],
							true));
				}
				else {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByConditionFields[i],
							true));
				}

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByFields[i], true));
				}
				else {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByFields[i], true));
				}

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(AssetListEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(AssetListEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), AssetListEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(_FILTER_ENTITY_ALIAS, AssetListEntryImpl.class);
		}
		else {
			sqlQuery.addEntity(_FILTER_ENTITY_TABLE, AssetListEntryImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(groupId);

		if (bindTitle) {
			queryPos.add(title);
		}

		if (bindAssetEntryType) {
			queryPos.add(assetEntryType);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						assetListEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AssetListEntry> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the asset list entries that the user has permission to view where groupId = any &#63; and title LIKE &#63; and assetEntryType = any &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param title the title
	 * @param assetEntryTypes the asset entry types
	 * @return the matching asset list entries that the user has permission to view
	 */
	@Override
	public List<AssetListEntry> filterFindByG_LikeT_AET(
		long[] groupIds, String title, String[] assetEntryTypes) {

		return filterFindByG_LikeT_AET(
			groupIds, title, assetEntryTypes, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset list entries that the user has permission to view where groupId = any &#63; and title LIKE &#63; and assetEntryType = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param title the title
	 * @param assetEntryTypes the asset entry types
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @return the range of matching asset list entries that the user has permission to view
	 */
	@Override
	public List<AssetListEntry> filterFindByG_LikeT_AET(
		long[] groupIds, String title, String[] assetEntryTypes, int start,
		int end) {

		return filterFindByG_LikeT_AET(
			groupIds, title, assetEntryTypes, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset list entries that the user has permission to view where groupId = any &#63; and title LIKE &#63; and assetEntryType = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param title the title
	 * @param assetEntryTypes the asset entry types
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset list entries that the user has permission to view
	 */
	@Override
	public List<AssetListEntry> filterFindByG_LikeT_AET(
		long[] groupIds, String title, String[] assetEntryTypes, int start,
		int end, OrderByComparator<AssetListEntry> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupIds)) {
			return findByG_LikeT_AET(
				groupIds, title, assetEntryTypes, start, end,
				orderByComparator);
		}

		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.sortedUnique(groupIds);
		}

		title = Objects.toString(title, "");

		if (assetEntryTypes == null) {
			assetEntryTypes = new String[0];
		}
		else if (assetEntryTypes.length > 1) {
			for (int i = 0; i < assetEntryTypes.length; i++) {
				assetEntryTypes[i] = Objects.toString(assetEntryTypes[i], "");
			}

			assetEntryTypes = ArrayUtil.sortedUnique(assetEntryTypes);
		}

		StringBundler sb = new StringBundler();

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_ASSETLISTENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_ASSETLISTENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		if (groupIds.length > 0) {
			sb.append("(");

			sb.append(_FINDER_COLUMN_G_LIKET_AET_GROUPID_7);

			sb.append(StringUtil.merge(groupIds));

			sb.append(")");

			sb.append(")");

			sb.append(WHERE_AND);
		}

		boolean bindTitle = false;

		if (title.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_LIKET_AET_TITLE_3);
		}
		else {
			bindTitle = true;

			sb.append(_FINDER_COLUMN_G_LIKET_AET_TITLE_2);
		}

		if (assetEntryTypes.length > 0) {
			sb.append("(");

			for (int i = 0; i < assetEntryTypes.length; i++) {
				String assetEntryType = assetEntryTypes[i];

				if (assetEntryType.isEmpty()) {
					sb.append(_FINDER_COLUMN_G_LIKET_AET_ASSETENTRYTYPE_3);
				}
				else {
					sb.append(_FINDER_COLUMN_G_LIKET_AET_ASSETENTRYTYPE_2);
				}

				if ((i + 1) < assetEntryTypes.length) {
					sb.append(WHERE_OR);
				}
			}

			sb.append(")");
		}

		sb.setStringAt(
			removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_ASSETLISTENTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(AssetListEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(AssetListEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), AssetListEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupIds);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, AssetListEntryImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, AssetListEntryImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			if (bindTitle) {
				queryPos.add(title);
			}

			for (String assetEntryType : assetEntryTypes) {
				if ((assetEntryType != null) && !assetEntryType.isEmpty()) {
					queryPos.add(assetEntryType);
				}
			}

			return (List<AssetListEntry>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns all the asset list entries where groupId = any &#63; and title LIKE &#63; and assetEntryType = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param title the title
	 * @param assetEntryTypes the asset entry types
	 * @return the matching asset list entries
	 */
	@Override
	public List<AssetListEntry> findByG_LikeT_AET(
		long[] groupIds, String title, String[] assetEntryTypes) {

		return findByG_LikeT_AET(
			groupIds, title, assetEntryTypes, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset list entries where groupId = any &#63; and title LIKE &#63; and assetEntryType = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param title the title
	 * @param assetEntryTypes the asset entry types
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @return the range of matching asset list entries
	 */
	@Override
	public List<AssetListEntry> findByG_LikeT_AET(
		long[] groupIds, String title, String[] assetEntryTypes, int start,
		int end) {

		return findByG_LikeT_AET(
			groupIds, title, assetEntryTypes, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset list entries where groupId = any &#63; and title LIKE &#63; and assetEntryType = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param title the title
	 * @param assetEntryTypes the asset entry types
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset list entries
	 */
	@Override
	public List<AssetListEntry> findByG_LikeT_AET(
		long[] groupIds, String title, String[] assetEntryTypes, int start,
		int end, OrderByComparator<AssetListEntry> orderByComparator) {

		return findByG_LikeT_AET(
			groupIds, title, assetEntryTypes, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the asset list entries where groupId = &#63; and title LIKE &#63; and assetEntryType = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param title the title
	 * @param assetEntryType the asset entry type
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset list entries
	 */
	@Override
	public List<AssetListEntry> findByG_LikeT_AET(
		long[] groupIds, String title, String[] assetEntryTypes, int start,
		int end, OrderByComparator<AssetListEntry> orderByComparator,
		boolean useFinderCache) {

		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.sortedUnique(groupIds);
		}

		title = Objects.toString(title, "");

		if (assetEntryTypes == null) {
			assetEntryTypes = new String[0];
		}
		else if (assetEntryTypes.length > 1) {
			for (int i = 0; i < assetEntryTypes.length; i++) {
				assetEntryTypes[i] = Objects.toString(assetEntryTypes[i], "");
			}

			assetEntryTypes = ArrayUtil.sortedUnique(assetEntryTypes);
		}

		if ((groupIds.length == 1) && (assetEntryTypes.length == 1)) {
			return findByG_LikeT_AET(
				groupIds[0], title, assetEntryTypes[0], start, end,
				orderByComparator);
		}

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetListEntry.class);

		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderArgs = new Object[] {
					StringUtil.merge(groupIds), title,
					StringUtil.merge(assetEntryTypes)
				};
			}
		}
		else if (useFinderCache && productionMode) {
			finderArgs = new Object[] {
				StringUtil.merge(groupIds), title,
				StringUtil.merge(assetEntryTypes), start, end, orderByComparator
			};
		}

		List<AssetListEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<AssetListEntry>)finderCache.getResult(
				_finderPathWithPaginationFindByG_LikeT_AET, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetListEntry assetListEntry : list) {
					if (!ArrayUtil.contains(
							groupIds, assetListEntry.getGroupId()) ||
						!StringUtil.wildcardMatches(
							assetListEntry.getTitle(), title, '_', '%', '\\',
							true) ||
						!ArrayUtil.contains(
							assetEntryTypes,
							assetListEntry.getAssetEntryType())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_SELECT_ASSETLISTENTRY_WHERE);

			if (groupIds.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_G_LIKET_AET_GROUPID_7);

				sb.append(StringUtil.merge(groupIds));

				sb.append(")");

				sb.append(")");

				sb.append(WHERE_AND);
			}

			boolean bindTitle = false;

			if (title.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_LIKET_AET_TITLE_3);
			}
			else {
				bindTitle = true;

				sb.append(_FINDER_COLUMN_G_LIKET_AET_TITLE_2);
			}

			if (assetEntryTypes.length > 0) {
				sb.append("(");

				for (int i = 0; i < assetEntryTypes.length; i++) {
					String assetEntryType = assetEntryTypes[i];

					if (assetEntryType.isEmpty()) {
						sb.append(_FINDER_COLUMN_G_LIKET_AET_ASSETENTRYTYPE_3);
					}
					else {
						sb.append(_FINDER_COLUMN_G_LIKET_AET_ASSETENTRYTYPE_2);
					}

					if ((i + 1) < assetEntryTypes.length) {
						sb.append(WHERE_OR);
					}
				}

				sb.append(")");
			}

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AssetListEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindTitle) {
					queryPos.add(title);
				}

				for (String assetEntryType : assetEntryTypes) {
					if ((assetEntryType != null) && !assetEntryType.isEmpty()) {
						queryPos.add(assetEntryType);
					}
				}

				list = (List<AssetListEntry>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					finderCache.putResult(
						_finderPathWithPaginationFindByG_LikeT_AET, finderArgs,
						list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the asset list entries where groupId = &#63; and title LIKE &#63; and assetEntryType = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param title the title
	 * @param assetEntryType the asset entry type
	 */
	@Override
	public void removeByG_LikeT_AET(
		long groupId, String title, String assetEntryType) {

		for (AssetListEntry assetListEntry :
				findByG_LikeT_AET(
					groupId, title, assetEntryType, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(assetListEntry);
		}
	}

	/**
	 * Returns the number of asset list entries where groupId = &#63; and title LIKE &#63; and assetEntryType = &#63;.
	 *
	 * @param groupId the group ID
	 * @param title the title
	 * @param assetEntryType the asset entry type
	 * @return the number of matching asset list entries
	 */
	@Override
	public int countByG_LikeT_AET(
		long groupId, String title, String assetEntryType) {

		title = Objects.toString(title, "");
		assetEntryType = Objects.toString(assetEntryType, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetListEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathWithPaginationCountByG_LikeT_AET;

			finderArgs = new Object[] {groupId, title, assetEntryType};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_ASSETLISTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_LIKET_AET_GROUPID_2);

			boolean bindTitle = false;

			if (title.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_LIKET_AET_TITLE_3);
			}
			else {
				bindTitle = true;

				sb.append(_FINDER_COLUMN_G_LIKET_AET_TITLE_2);
			}

			boolean bindAssetEntryType = false;

			if (assetEntryType.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_LIKET_AET_ASSETENTRYTYPE_3);
			}
			else {
				bindAssetEntryType = true;

				sb.append(_FINDER_COLUMN_G_LIKET_AET_ASSETENTRYTYPE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindTitle) {
					queryPos.add(title);
				}

				if (bindAssetEntryType) {
					queryPos.add(assetEntryType);
				}

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of asset list entries where groupId = any &#63; and title LIKE &#63; and assetEntryType = any &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param title the title
	 * @param assetEntryTypes the asset entry types
	 * @return the number of matching asset list entries
	 */
	@Override
	public int countByG_LikeT_AET(
		long[] groupIds, String title, String[] assetEntryTypes) {

		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.sortedUnique(groupIds);
		}

		title = Objects.toString(title, "");

		if (assetEntryTypes == null) {
			assetEntryTypes = new String[0];
		}
		else if (assetEntryTypes.length > 1) {
			for (int i = 0; i < assetEntryTypes.length; i++) {
				assetEntryTypes[i] = Objects.toString(assetEntryTypes[i], "");
			}

			assetEntryTypes = ArrayUtil.sortedUnique(assetEntryTypes);
		}

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetListEntry.class);

		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderArgs = new Object[] {
				StringUtil.merge(groupIds), title,
				StringUtil.merge(assetEntryTypes)
			};

			count = (Long)finderCache.getResult(
				_finderPathWithPaginationCountByG_LikeT_AET, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_COUNT_ASSETLISTENTRY_WHERE);

			if (groupIds.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_G_LIKET_AET_GROUPID_7);

				sb.append(StringUtil.merge(groupIds));

				sb.append(")");

				sb.append(")");

				sb.append(WHERE_AND);
			}

			boolean bindTitle = false;

			if (title.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_LIKET_AET_TITLE_3);
			}
			else {
				bindTitle = true;

				sb.append(_FINDER_COLUMN_G_LIKET_AET_TITLE_2);
			}

			if (assetEntryTypes.length > 0) {
				sb.append("(");

				for (int i = 0; i < assetEntryTypes.length; i++) {
					String assetEntryType = assetEntryTypes[i];

					if (assetEntryType.isEmpty()) {
						sb.append(_FINDER_COLUMN_G_LIKET_AET_ASSETENTRYTYPE_3);
					}
					else {
						sb.append(_FINDER_COLUMN_G_LIKET_AET_ASSETENTRYTYPE_2);
					}

					if ((i + 1) < assetEntryTypes.length) {
						sb.append(WHERE_OR);
					}
				}

				sb.append(")");
			}

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindTitle) {
					queryPos.add(title);
				}

				for (String assetEntryType : assetEntryTypes) {
					if ((assetEntryType != null) && !assetEntryType.isEmpty()) {
						queryPos.add(assetEntryType);
					}
				}

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(
						_finderPathWithPaginationCountByG_LikeT_AET, finderArgs,
						count);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of asset list entries that the user has permission to view where groupId = &#63; and title LIKE &#63; and assetEntryType = &#63;.
	 *
	 * @param groupId the group ID
	 * @param title the title
	 * @param assetEntryType the asset entry type
	 * @return the number of matching asset list entries that the user has permission to view
	 */
	@Override
	public int filterCountByG_LikeT_AET(
		long groupId, String title, String assetEntryType) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_LikeT_AET(groupId, title, assetEntryType);
		}

		title = Objects.toString(title, "");
		assetEntryType = Objects.toString(assetEntryType, "");

		StringBundler sb = new StringBundler(4);

		sb.append(_FILTER_SQL_COUNT_ASSETLISTENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_LIKET_AET_GROUPID_2);

		boolean bindTitle = false;

		if (title.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_LIKET_AET_TITLE_3);
		}
		else {
			bindTitle = true;

			sb.append(_FINDER_COLUMN_G_LIKET_AET_TITLE_2);
		}

		boolean bindAssetEntryType = false;

		if (assetEntryType.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_LIKET_AET_ASSETENTRYTYPE_3);
		}
		else {
			bindAssetEntryType = true;

			sb.append(_FINDER_COLUMN_G_LIKET_AET_ASSETENTRYTYPE_2);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), AssetListEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			if (bindTitle) {
				queryPos.add(title);
			}

			if (bindAssetEntryType) {
				queryPos.add(assetEntryType);
			}

			Long count = (Long)sqlQuery.uniqueResult();

			return count.intValue();
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the number of asset list entries that the user has permission to view where groupId = any &#63; and title LIKE &#63; and assetEntryType = any &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param title the title
	 * @param assetEntryTypes the asset entry types
	 * @return the number of matching asset list entries that the user has permission to view
	 */
	@Override
	public int filterCountByG_LikeT_AET(
		long[] groupIds, String title, String[] assetEntryTypes) {

		if (!InlineSQLHelperUtil.isEnabled(groupIds)) {
			return countByG_LikeT_AET(groupIds, title, assetEntryTypes);
		}

		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.sortedUnique(groupIds);
		}

		title = Objects.toString(title, "");

		if (assetEntryTypes == null) {
			assetEntryTypes = new String[0];
		}
		else if (assetEntryTypes.length > 1) {
			for (int i = 0; i < assetEntryTypes.length; i++) {
				assetEntryTypes[i] = Objects.toString(assetEntryTypes[i], "");
			}

			assetEntryTypes = ArrayUtil.sortedUnique(assetEntryTypes);
		}

		StringBundler sb = new StringBundler();

		sb.append(_FILTER_SQL_COUNT_ASSETLISTENTRY_WHERE);

		if (groupIds.length > 0) {
			sb.append("(");

			sb.append(_FINDER_COLUMN_G_LIKET_AET_GROUPID_7);

			sb.append(StringUtil.merge(groupIds));

			sb.append(")");

			sb.append(")");

			sb.append(WHERE_AND);
		}

		boolean bindTitle = false;

		if (title.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_LIKET_AET_TITLE_3);
		}
		else {
			bindTitle = true;

			sb.append(_FINDER_COLUMN_G_LIKET_AET_TITLE_2);
		}

		if (assetEntryTypes.length > 0) {
			sb.append("(");

			for (int i = 0; i < assetEntryTypes.length; i++) {
				String assetEntryType = assetEntryTypes[i];

				if (assetEntryType.isEmpty()) {
					sb.append(_FINDER_COLUMN_G_LIKET_AET_ASSETENTRYTYPE_3);
				}
				else {
					sb.append(_FINDER_COLUMN_G_LIKET_AET_ASSETENTRYTYPE_2);
				}

				if ((i + 1) < assetEntryTypes.length) {
					sb.append(WHERE_OR);
				}
			}

			sb.append(")");
		}

		sb.setStringAt(
			removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), AssetListEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupIds);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			if (bindTitle) {
				queryPos.add(title);
			}

			for (String assetEntryType : assetEntryTypes) {
				if ((assetEntryType != null) && !assetEntryType.isEmpty()) {
					queryPos.add(assetEntryType);
				}
			}

			Long count = (Long)sqlQuery.uniqueResult();

			return count.intValue();
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_G_LIKET_AET_GROUPID_2 =
		"assetListEntry.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_LIKET_AET_GROUPID_7 =
		"assetListEntry.groupId IN (";

	private static final String _FINDER_COLUMN_G_LIKET_AET_TITLE_2 =
		"assetListEntry.title LIKE ? AND ";

	private static final String _FINDER_COLUMN_G_LIKET_AET_TITLE_3 =
		"(assetListEntry.title IS NULL OR assetListEntry.title LIKE '') AND ";

	private static final String _FINDER_COLUMN_G_LIKET_AET_ASSETENTRYTYPE_2 =
		"assetListEntry.assetEntryType = ?";

	private static final String _FINDER_COLUMN_G_LIKET_AET_ASSETENTRYTYPE_3 =
		"(assetListEntry.assetEntryType IS NULL OR assetListEntry.assetEntryType = '')";

	private FinderPath _finderPathWithPaginationFindByG_AES_AET;
	private FinderPath _finderPathWithoutPaginationFindByG_AES_AET;
	private FinderPath _finderPathCountByG_AES_AET;
	private FinderPath _finderPathWithPaginationCountByG_AES_AET;

	/**
	 * Returns all the asset list entries where groupId = &#63; and assetEntrySubtype = &#63; and assetEntryType = &#63;.
	 *
	 * @param groupId the group ID
	 * @param assetEntrySubtype the asset entry subtype
	 * @param assetEntryType the asset entry type
	 * @return the matching asset list entries
	 */
	@Override
	public List<AssetListEntry> findByG_AES_AET(
		long groupId, String assetEntrySubtype, String assetEntryType) {

		return findByG_AES_AET(
			groupId, assetEntrySubtype, assetEntryType, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset list entries where groupId = &#63; and assetEntrySubtype = &#63; and assetEntryType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param assetEntrySubtype the asset entry subtype
	 * @param assetEntryType the asset entry type
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @return the range of matching asset list entries
	 */
	@Override
	public List<AssetListEntry> findByG_AES_AET(
		long groupId, String assetEntrySubtype, String assetEntryType,
		int start, int end) {

		return findByG_AES_AET(
			groupId, assetEntrySubtype, assetEntryType, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset list entries where groupId = &#63; and assetEntrySubtype = &#63; and assetEntryType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param assetEntrySubtype the asset entry subtype
	 * @param assetEntryType the asset entry type
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset list entries
	 */
	@Override
	public List<AssetListEntry> findByG_AES_AET(
		long groupId, String assetEntrySubtype, String assetEntryType,
		int start, int end,
		OrderByComparator<AssetListEntry> orderByComparator) {

		return findByG_AES_AET(
			groupId, assetEntrySubtype, assetEntryType, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset list entries where groupId = &#63; and assetEntrySubtype = &#63; and assetEntryType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param assetEntrySubtype the asset entry subtype
	 * @param assetEntryType the asset entry type
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset list entries
	 */
	@Override
	public List<AssetListEntry> findByG_AES_AET(
		long groupId, String assetEntrySubtype, String assetEntryType,
		int start, int end, OrderByComparator<AssetListEntry> orderByComparator,
		boolean useFinderCache) {

		assetEntrySubtype = Objects.toString(assetEntrySubtype, "");
		assetEntryType = Objects.toString(assetEntryType, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetListEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByG_AES_AET;
				finderArgs = new Object[] {
					groupId, assetEntrySubtype, assetEntryType
				};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByG_AES_AET;
			finderArgs = new Object[] {
				groupId, assetEntrySubtype, assetEntryType, start, end,
				orderByComparator
			};
		}

		List<AssetListEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<AssetListEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetListEntry assetListEntry : list) {
					if ((groupId != assetListEntry.getGroupId()) ||
						!assetEntrySubtype.equals(
							assetListEntry.getAssetEntrySubtype()) ||
						!assetEntryType.equals(
							assetListEntry.getAssetEntryType())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					5 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(5);
			}

			sb.append(_SQL_SELECT_ASSETLISTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_AES_AET_GROUPID_2);

			boolean bindAssetEntrySubtype = false;

			if (assetEntrySubtype.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_AES_AET_ASSETENTRYSUBTYPE_3);
			}
			else {
				bindAssetEntrySubtype = true;

				sb.append(_FINDER_COLUMN_G_AES_AET_ASSETENTRYSUBTYPE_2);
			}

			boolean bindAssetEntryType = false;

			if (assetEntryType.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_AES_AET_ASSETENTRYTYPE_3);
			}
			else {
				bindAssetEntryType = true;

				sb.append(_FINDER_COLUMN_G_AES_AET_ASSETENTRYTYPE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AssetListEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindAssetEntrySubtype) {
					queryPos.add(assetEntrySubtype);
				}

				if (bindAssetEntryType) {
					queryPos.add(assetEntryType);
				}

				list = (List<AssetListEntry>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first asset list entry in the ordered set where groupId = &#63; and assetEntrySubtype = &#63; and assetEntryType = &#63;.
	 *
	 * @param groupId the group ID
	 * @param assetEntrySubtype the asset entry subtype
	 * @param assetEntryType the asset entry type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry
	 * @throws NoSuchEntryException if a matching asset list entry could not be found
	 */
	@Override
	public AssetListEntry findByG_AES_AET_First(
			long groupId, String assetEntrySubtype, String assetEntryType,
			OrderByComparator<AssetListEntry> orderByComparator)
		throws NoSuchEntryException {

		AssetListEntry assetListEntry = fetchByG_AES_AET_First(
			groupId, assetEntrySubtype, assetEntryType, orderByComparator);

		if (assetListEntry != null) {
			return assetListEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", assetEntrySubtype=");
		sb.append(assetEntrySubtype);

		sb.append(", assetEntryType=");
		sb.append(assetEntryType);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first asset list entry in the ordered set where groupId = &#63; and assetEntrySubtype = &#63; and assetEntryType = &#63;.
	 *
	 * @param groupId the group ID
	 * @param assetEntrySubtype the asset entry subtype
	 * @param assetEntryType the asset entry type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry, or <code>null</code> if a matching asset list entry could not be found
	 */
	@Override
	public AssetListEntry fetchByG_AES_AET_First(
		long groupId, String assetEntrySubtype, String assetEntryType,
		OrderByComparator<AssetListEntry> orderByComparator) {

		List<AssetListEntry> list = findByG_AES_AET(
			groupId, assetEntrySubtype, assetEntryType, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset list entry in the ordered set where groupId = &#63; and assetEntrySubtype = &#63; and assetEntryType = &#63;.
	 *
	 * @param groupId the group ID
	 * @param assetEntrySubtype the asset entry subtype
	 * @param assetEntryType the asset entry type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry
	 * @throws NoSuchEntryException if a matching asset list entry could not be found
	 */
	@Override
	public AssetListEntry findByG_AES_AET_Last(
			long groupId, String assetEntrySubtype, String assetEntryType,
			OrderByComparator<AssetListEntry> orderByComparator)
		throws NoSuchEntryException {

		AssetListEntry assetListEntry = fetchByG_AES_AET_Last(
			groupId, assetEntrySubtype, assetEntryType, orderByComparator);

		if (assetListEntry != null) {
			return assetListEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", assetEntrySubtype=");
		sb.append(assetEntrySubtype);

		sb.append(", assetEntryType=");
		sb.append(assetEntryType);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last asset list entry in the ordered set where groupId = &#63; and assetEntrySubtype = &#63; and assetEntryType = &#63;.
	 *
	 * @param groupId the group ID
	 * @param assetEntrySubtype the asset entry subtype
	 * @param assetEntryType the asset entry type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry, or <code>null</code> if a matching asset list entry could not be found
	 */
	@Override
	public AssetListEntry fetchByG_AES_AET_Last(
		long groupId, String assetEntrySubtype, String assetEntryType,
		OrderByComparator<AssetListEntry> orderByComparator) {

		int count = countByG_AES_AET(
			groupId, assetEntrySubtype, assetEntryType);

		if (count == 0) {
			return null;
		}

		List<AssetListEntry> list = findByG_AES_AET(
			groupId, assetEntrySubtype, assetEntryType, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset list entries before and after the current asset list entry in the ordered set where groupId = &#63; and assetEntrySubtype = &#63; and assetEntryType = &#63;.
	 *
	 * @param assetListEntryId the primary key of the current asset list entry
	 * @param groupId the group ID
	 * @param assetEntrySubtype the asset entry subtype
	 * @param assetEntryType the asset entry type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset list entry
	 * @throws NoSuchEntryException if a asset list entry with the primary key could not be found
	 */
	@Override
	public AssetListEntry[] findByG_AES_AET_PrevAndNext(
			long assetListEntryId, long groupId, String assetEntrySubtype,
			String assetEntryType,
			OrderByComparator<AssetListEntry> orderByComparator)
		throws NoSuchEntryException {

		assetEntrySubtype = Objects.toString(assetEntrySubtype, "");
		assetEntryType = Objects.toString(assetEntryType, "");

		AssetListEntry assetListEntry = findByPrimaryKey(assetListEntryId);

		Session session = null;

		try {
			session = openSession();

			AssetListEntry[] array = new AssetListEntryImpl[3];

			array[0] = getByG_AES_AET_PrevAndNext(
				session, assetListEntry, groupId, assetEntrySubtype,
				assetEntryType, orderByComparator, true);

			array[1] = assetListEntry;

			array[2] = getByG_AES_AET_PrevAndNext(
				session, assetListEntry, groupId, assetEntrySubtype,
				assetEntryType, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetListEntry getByG_AES_AET_PrevAndNext(
		Session session, AssetListEntry assetListEntry, long groupId,
		String assetEntrySubtype, String assetEntryType,
		OrderByComparator<AssetListEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		sb.append(_SQL_SELECT_ASSETLISTENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_AES_AET_GROUPID_2);

		boolean bindAssetEntrySubtype = false;

		if (assetEntrySubtype.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_AES_AET_ASSETENTRYSUBTYPE_3);
		}
		else {
			bindAssetEntrySubtype = true;

			sb.append(_FINDER_COLUMN_G_AES_AET_ASSETENTRYSUBTYPE_2);
		}

		boolean bindAssetEntryType = false;

		if (assetEntryType.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_AES_AET_ASSETENTRYTYPE_3);
		}
		else {
			bindAssetEntryType = true;

			sb.append(_FINDER_COLUMN_G_AES_AET_ASSETENTRYTYPE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(AssetListEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		if (bindAssetEntrySubtype) {
			queryPos.add(assetEntrySubtype);
		}

		if (bindAssetEntryType) {
			queryPos.add(assetEntryType);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						assetListEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AssetListEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the asset list entries that the user has permission to view where groupId = &#63; and assetEntrySubtype = &#63; and assetEntryType = &#63;.
	 *
	 * @param groupId the group ID
	 * @param assetEntrySubtype the asset entry subtype
	 * @param assetEntryType the asset entry type
	 * @return the matching asset list entries that the user has permission to view
	 */
	@Override
	public List<AssetListEntry> filterFindByG_AES_AET(
		long groupId, String assetEntrySubtype, String assetEntryType) {

		return filterFindByG_AES_AET(
			groupId, assetEntrySubtype, assetEntryType, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset list entries that the user has permission to view where groupId = &#63; and assetEntrySubtype = &#63; and assetEntryType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param assetEntrySubtype the asset entry subtype
	 * @param assetEntryType the asset entry type
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @return the range of matching asset list entries that the user has permission to view
	 */
	@Override
	public List<AssetListEntry> filterFindByG_AES_AET(
		long groupId, String assetEntrySubtype, String assetEntryType,
		int start, int end) {

		return filterFindByG_AES_AET(
			groupId, assetEntrySubtype, assetEntryType, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset list entries that the user has permissions to view where groupId = &#63; and assetEntrySubtype = &#63; and assetEntryType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param assetEntrySubtype the asset entry subtype
	 * @param assetEntryType the asset entry type
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset list entries that the user has permission to view
	 */
	@Override
	public List<AssetListEntry> filterFindByG_AES_AET(
		long groupId, String assetEntrySubtype, String assetEntryType,
		int start, int end,
		OrderByComparator<AssetListEntry> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_AES_AET(
				groupId, assetEntrySubtype, assetEntryType, start, end,
				orderByComparator);
		}

		assetEntrySubtype = Objects.toString(assetEntrySubtype, "");
		assetEntryType = Objects.toString(assetEntryType, "");

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(6);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_ASSETLISTENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_ASSETLISTENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_AES_AET_GROUPID_2);

		boolean bindAssetEntrySubtype = false;

		if (assetEntrySubtype.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_AES_AET_ASSETENTRYSUBTYPE_3);
		}
		else {
			bindAssetEntrySubtype = true;

			sb.append(_FINDER_COLUMN_G_AES_AET_ASSETENTRYSUBTYPE_2);
		}

		boolean bindAssetEntryType = false;

		if (assetEntryType.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_AES_AET_ASSETENTRYTYPE_3);
		}
		else {
			bindAssetEntryType = true;

			sb.append(_FINDER_COLUMN_G_AES_AET_ASSETENTRYTYPE_2);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_ASSETLISTENTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(AssetListEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(AssetListEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), AssetListEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, AssetListEntryImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, AssetListEntryImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			if (bindAssetEntrySubtype) {
				queryPos.add(assetEntrySubtype);
			}

			if (bindAssetEntryType) {
				queryPos.add(assetEntryType);
			}

			return (List<AssetListEntry>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the asset list entries before and after the current asset list entry in the ordered set of asset list entries that the user has permission to view where groupId = &#63; and assetEntrySubtype = &#63; and assetEntryType = &#63;.
	 *
	 * @param assetListEntryId the primary key of the current asset list entry
	 * @param groupId the group ID
	 * @param assetEntrySubtype the asset entry subtype
	 * @param assetEntryType the asset entry type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset list entry
	 * @throws NoSuchEntryException if a asset list entry with the primary key could not be found
	 */
	@Override
	public AssetListEntry[] filterFindByG_AES_AET_PrevAndNext(
			long assetListEntryId, long groupId, String assetEntrySubtype,
			String assetEntryType,
			OrderByComparator<AssetListEntry> orderByComparator)
		throws NoSuchEntryException {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_AES_AET_PrevAndNext(
				assetListEntryId, groupId, assetEntrySubtype, assetEntryType,
				orderByComparator);
		}

		assetEntrySubtype = Objects.toString(assetEntrySubtype, "");
		assetEntryType = Objects.toString(assetEntryType, "");

		AssetListEntry assetListEntry = findByPrimaryKey(assetListEntryId);

		Session session = null;

		try {
			session = openSession();

			AssetListEntry[] array = new AssetListEntryImpl[3];

			array[0] = filterGetByG_AES_AET_PrevAndNext(
				session, assetListEntry, groupId, assetEntrySubtype,
				assetEntryType, orderByComparator, true);

			array[1] = assetListEntry;

			array[2] = filterGetByG_AES_AET_PrevAndNext(
				session, assetListEntry, groupId, assetEntrySubtype,
				assetEntryType, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetListEntry filterGetByG_AES_AET_PrevAndNext(
		Session session, AssetListEntry assetListEntry, long groupId,
		String assetEntrySubtype, String assetEntryType,
		OrderByComparator<AssetListEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				7 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(6);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_ASSETLISTENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_ASSETLISTENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_AES_AET_GROUPID_2);

		boolean bindAssetEntrySubtype = false;

		if (assetEntrySubtype.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_AES_AET_ASSETENTRYSUBTYPE_3);
		}
		else {
			bindAssetEntrySubtype = true;

			sb.append(_FINDER_COLUMN_G_AES_AET_ASSETENTRYSUBTYPE_2);
		}

		boolean bindAssetEntryType = false;

		if (assetEntryType.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_AES_AET_ASSETENTRYTYPE_3);
		}
		else {
			bindAssetEntryType = true;

			sb.append(_FINDER_COLUMN_G_AES_AET_ASSETENTRYTYPE_2);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_ASSETLISTENTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByConditionFields[i],
							true));
				}
				else {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByConditionFields[i],
							true));
				}

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByFields[i], true));
				}
				else {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByFields[i], true));
				}

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(AssetListEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(AssetListEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), AssetListEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(_FILTER_ENTITY_ALIAS, AssetListEntryImpl.class);
		}
		else {
			sqlQuery.addEntity(_FILTER_ENTITY_TABLE, AssetListEntryImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(groupId);

		if (bindAssetEntrySubtype) {
			queryPos.add(assetEntrySubtype);
		}

		if (bindAssetEntryType) {
			queryPos.add(assetEntryType);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						assetListEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AssetListEntry> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the asset list entries that the user has permission to view where groupId = any &#63; and assetEntrySubtype = &#63; and assetEntryType = &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param assetEntrySubtype the asset entry subtype
	 * @param assetEntryType the asset entry type
	 * @return the matching asset list entries that the user has permission to view
	 */
	@Override
	public List<AssetListEntry> filterFindByG_AES_AET(
		long[] groupIds, String assetEntrySubtype, String assetEntryType) {

		return filterFindByG_AES_AET(
			groupIds, assetEntrySubtype, assetEntryType, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset list entries that the user has permission to view where groupId = any &#63; and assetEntrySubtype = &#63; and assetEntryType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param assetEntrySubtype the asset entry subtype
	 * @param assetEntryType the asset entry type
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @return the range of matching asset list entries that the user has permission to view
	 */
	@Override
	public List<AssetListEntry> filterFindByG_AES_AET(
		long[] groupIds, String assetEntrySubtype, String assetEntryType,
		int start, int end) {

		return filterFindByG_AES_AET(
			groupIds, assetEntrySubtype, assetEntryType, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset list entries that the user has permission to view where groupId = any &#63; and assetEntrySubtype = &#63; and assetEntryType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param assetEntrySubtype the asset entry subtype
	 * @param assetEntryType the asset entry type
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset list entries that the user has permission to view
	 */
	@Override
	public List<AssetListEntry> filterFindByG_AES_AET(
		long[] groupIds, String assetEntrySubtype, String assetEntryType,
		int start, int end,
		OrderByComparator<AssetListEntry> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupIds)) {
			return findByG_AES_AET(
				groupIds, assetEntrySubtype, assetEntryType, start, end,
				orderByComparator);
		}

		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.sortedUnique(groupIds);
		}

		assetEntrySubtype = Objects.toString(assetEntrySubtype, "");
		assetEntryType = Objects.toString(assetEntryType, "");

		StringBundler sb = new StringBundler();

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_ASSETLISTENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_ASSETLISTENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		if (groupIds.length > 0) {
			sb.append("(");

			sb.append(_FINDER_COLUMN_G_AES_AET_GROUPID_7);

			sb.append(StringUtil.merge(groupIds));

			sb.append(")");

			sb.append(")");

			sb.append(WHERE_AND);
		}

		boolean bindAssetEntrySubtype = false;

		if (assetEntrySubtype.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_AES_AET_ASSETENTRYSUBTYPE_3);
		}
		else {
			bindAssetEntrySubtype = true;

			sb.append(_FINDER_COLUMN_G_AES_AET_ASSETENTRYSUBTYPE_2);
		}

		boolean bindAssetEntryType = false;

		if (assetEntryType.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_AES_AET_ASSETENTRYTYPE_3);
		}
		else {
			bindAssetEntryType = true;

			sb.append(_FINDER_COLUMN_G_AES_AET_ASSETENTRYTYPE_2);
		}

		sb.setStringAt(
			removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_ASSETLISTENTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(AssetListEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(AssetListEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), AssetListEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupIds);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, AssetListEntryImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, AssetListEntryImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			if (bindAssetEntrySubtype) {
				queryPos.add(assetEntrySubtype);
			}

			if (bindAssetEntryType) {
				queryPos.add(assetEntryType);
			}

			return (List<AssetListEntry>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns all the asset list entries where groupId = any &#63; and assetEntrySubtype = &#63; and assetEntryType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param assetEntrySubtype the asset entry subtype
	 * @param assetEntryType the asset entry type
	 * @return the matching asset list entries
	 */
	@Override
	public List<AssetListEntry> findByG_AES_AET(
		long[] groupIds, String assetEntrySubtype, String assetEntryType) {

		return findByG_AES_AET(
			groupIds, assetEntrySubtype, assetEntryType, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset list entries where groupId = any &#63; and assetEntrySubtype = &#63; and assetEntryType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param assetEntrySubtype the asset entry subtype
	 * @param assetEntryType the asset entry type
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @return the range of matching asset list entries
	 */
	@Override
	public List<AssetListEntry> findByG_AES_AET(
		long[] groupIds, String assetEntrySubtype, String assetEntryType,
		int start, int end) {

		return findByG_AES_AET(
			groupIds, assetEntrySubtype, assetEntryType, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset list entries where groupId = any &#63; and assetEntrySubtype = &#63; and assetEntryType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param assetEntrySubtype the asset entry subtype
	 * @param assetEntryType the asset entry type
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset list entries
	 */
	@Override
	public List<AssetListEntry> findByG_AES_AET(
		long[] groupIds, String assetEntrySubtype, String assetEntryType,
		int start, int end,
		OrderByComparator<AssetListEntry> orderByComparator) {

		return findByG_AES_AET(
			groupIds, assetEntrySubtype, assetEntryType, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset list entries where groupId = &#63; and assetEntrySubtype = &#63; and assetEntryType = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param assetEntrySubtype the asset entry subtype
	 * @param assetEntryType the asset entry type
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset list entries
	 */
	@Override
	public List<AssetListEntry> findByG_AES_AET(
		long[] groupIds, String assetEntrySubtype, String assetEntryType,
		int start, int end, OrderByComparator<AssetListEntry> orderByComparator,
		boolean useFinderCache) {

		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.sortedUnique(groupIds);
		}

		assetEntrySubtype = Objects.toString(assetEntrySubtype, "");
		assetEntryType = Objects.toString(assetEntryType, "");

		if (groupIds.length == 1) {
			return findByG_AES_AET(
				groupIds[0], assetEntrySubtype, assetEntryType, start, end,
				orderByComparator);
		}

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetListEntry.class);

		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderArgs = new Object[] {
					StringUtil.merge(groupIds), assetEntrySubtype,
					assetEntryType
				};
			}
		}
		else if (useFinderCache && productionMode) {
			finderArgs = new Object[] {
				StringUtil.merge(groupIds), assetEntrySubtype, assetEntryType,
				start, end, orderByComparator
			};
		}

		List<AssetListEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<AssetListEntry>)finderCache.getResult(
				_finderPathWithPaginationFindByG_AES_AET, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetListEntry assetListEntry : list) {
					if (!ArrayUtil.contains(
							groupIds, assetListEntry.getGroupId()) ||
						!assetEntrySubtype.equals(
							assetListEntry.getAssetEntrySubtype()) ||
						!assetEntryType.equals(
							assetListEntry.getAssetEntryType())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_SELECT_ASSETLISTENTRY_WHERE);

			if (groupIds.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_G_AES_AET_GROUPID_7);

				sb.append(StringUtil.merge(groupIds));

				sb.append(")");

				sb.append(")");

				sb.append(WHERE_AND);
			}

			boolean bindAssetEntrySubtype = false;

			if (assetEntrySubtype.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_AES_AET_ASSETENTRYSUBTYPE_3);
			}
			else {
				bindAssetEntrySubtype = true;

				sb.append(_FINDER_COLUMN_G_AES_AET_ASSETENTRYSUBTYPE_2);
			}

			boolean bindAssetEntryType = false;

			if (assetEntryType.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_AES_AET_ASSETENTRYTYPE_3);
			}
			else {
				bindAssetEntryType = true;

				sb.append(_FINDER_COLUMN_G_AES_AET_ASSETENTRYTYPE_2);
			}

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AssetListEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindAssetEntrySubtype) {
					queryPos.add(assetEntrySubtype);
				}

				if (bindAssetEntryType) {
					queryPos.add(assetEntryType);
				}

				list = (List<AssetListEntry>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					finderCache.putResult(
						_finderPathWithPaginationFindByG_AES_AET, finderArgs,
						list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the asset list entries where groupId = &#63; and assetEntrySubtype = &#63; and assetEntryType = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param assetEntrySubtype the asset entry subtype
	 * @param assetEntryType the asset entry type
	 */
	@Override
	public void removeByG_AES_AET(
		long groupId, String assetEntrySubtype, String assetEntryType) {

		for (AssetListEntry assetListEntry :
				findByG_AES_AET(
					groupId, assetEntrySubtype, assetEntryType,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(assetListEntry);
		}
	}

	/**
	 * Returns the number of asset list entries where groupId = &#63; and assetEntrySubtype = &#63; and assetEntryType = &#63;.
	 *
	 * @param groupId the group ID
	 * @param assetEntrySubtype the asset entry subtype
	 * @param assetEntryType the asset entry type
	 * @return the number of matching asset list entries
	 */
	@Override
	public int countByG_AES_AET(
		long groupId, String assetEntrySubtype, String assetEntryType) {

		assetEntrySubtype = Objects.toString(assetEntrySubtype, "");
		assetEntryType = Objects.toString(assetEntryType, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetListEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_AES_AET;

			finderArgs = new Object[] {
				groupId, assetEntrySubtype, assetEntryType
			};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_ASSETLISTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_AES_AET_GROUPID_2);

			boolean bindAssetEntrySubtype = false;

			if (assetEntrySubtype.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_AES_AET_ASSETENTRYSUBTYPE_3);
			}
			else {
				bindAssetEntrySubtype = true;

				sb.append(_FINDER_COLUMN_G_AES_AET_ASSETENTRYSUBTYPE_2);
			}

			boolean bindAssetEntryType = false;

			if (assetEntryType.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_AES_AET_ASSETENTRYTYPE_3);
			}
			else {
				bindAssetEntryType = true;

				sb.append(_FINDER_COLUMN_G_AES_AET_ASSETENTRYTYPE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindAssetEntrySubtype) {
					queryPos.add(assetEntrySubtype);
				}

				if (bindAssetEntryType) {
					queryPos.add(assetEntryType);
				}

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of asset list entries where groupId = any &#63; and assetEntrySubtype = &#63; and assetEntryType = &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param assetEntrySubtype the asset entry subtype
	 * @param assetEntryType the asset entry type
	 * @return the number of matching asset list entries
	 */
	@Override
	public int countByG_AES_AET(
		long[] groupIds, String assetEntrySubtype, String assetEntryType) {

		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.sortedUnique(groupIds);
		}

		assetEntrySubtype = Objects.toString(assetEntrySubtype, "");
		assetEntryType = Objects.toString(assetEntryType, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetListEntry.class);

		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderArgs = new Object[] {
				StringUtil.merge(groupIds), assetEntrySubtype, assetEntryType
			};

			count = (Long)finderCache.getResult(
				_finderPathWithPaginationCountByG_AES_AET, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_COUNT_ASSETLISTENTRY_WHERE);

			if (groupIds.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_G_AES_AET_GROUPID_7);

				sb.append(StringUtil.merge(groupIds));

				sb.append(")");

				sb.append(")");

				sb.append(WHERE_AND);
			}

			boolean bindAssetEntrySubtype = false;

			if (assetEntrySubtype.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_AES_AET_ASSETENTRYSUBTYPE_3);
			}
			else {
				bindAssetEntrySubtype = true;

				sb.append(_FINDER_COLUMN_G_AES_AET_ASSETENTRYSUBTYPE_2);
			}

			boolean bindAssetEntryType = false;

			if (assetEntryType.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_AES_AET_ASSETENTRYTYPE_3);
			}
			else {
				bindAssetEntryType = true;

				sb.append(_FINDER_COLUMN_G_AES_AET_ASSETENTRYTYPE_2);
			}

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindAssetEntrySubtype) {
					queryPos.add(assetEntrySubtype);
				}

				if (bindAssetEntryType) {
					queryPos.add(assetEntryType);
				}

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(
						_finderPathWithPaginationCountByG_AES_AET, finderArgs,
						count);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of asset list entries that the user has permission to view where groupId = &#63; and assetEntrySubtype = &#63; and assetEntryType = &#63;.
	 *
	 * @param groupId the group ID
	 * @param assetEntrySubtype the asset entry subtype
	 * @param assetEntryType the asset entry type
	 * @return the number of matching asset list entries that the user has permission to view
	 */
	@Override
	public int filterCountByG_AES_AET(
		long groupId, String assetEntrySubtype, String assetEntryType) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_AES_AET(groupId, assetEntrySubtype, assetEntryType);
		}

		assetEntrySubtype = Objects.toString(assetEntrySubtype, "");
		assetEntryType = Objects.toString(assetEntryType, "");

		StringBundler sb = new StringBundler(4);

		sb.append(_FILTER_SQL_COUNT_ASSETLISTENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_AES_AET_GROUPID_2);

		boolean bindAssetEntrySubtype = false;

		if (assetEntrySubtype.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_AES_AET_ASSETENTRYSUBTYPE_3);
		}
		else {
			bindAssetEntrySubtype = true;

			sb.append(_FINDER_COLUMN_G_AES_AET_ASSETENTRYSUBTYPE_2);
		}

		boolean bindAssetEntryType = false;

		if (assetEntryType.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_AES_AET_ASSETENTRYTYPE_3);
		}
		else {
			bindAssetEntryType = true;

			sb.append(_FINDER_COLUMN_G_AES_AET_ASSETENTRYTYPE_2);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), AssetListEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			if (bindAssetEntrySubtype) {
				queryPos.add(assetEntrySubtype);
			}

			if (bindAssetEntryType) {
				queryPos.add(assetEntryType);
			}

			Long count = (Long)sqlQuery.uniqueResult();

			return count.intValue();
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the number of asset list entries that the user has permission to view where groupId = any &#63; and assetEntrySubtype = &#63; and assetEntryType = &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param assetEntrySubtype the asset entry subtype
	 * @param assetEntryType the asset entry type
	 * @return the number of matching asset list entries that the user has permission to view
	 */
	@Override
	public int filterCountByG_AES_AET(
		long[] groupIds, String assetEntrySubtype, String assetEntryType) {

		if (!InlineSQLHelperUtil.isEnabled(groupIds)) {
			return countByG_AES_AET(
				groupIds, assetEntrySubtype, assetEntryType);
		}

		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.sortedUnique(groupIds);
		}

		assetEntrySubtype = Objects.toString(assetEntrySubtype, "");
		assetEntryType = Objects.toString(assetEntryType, "");

		StringBundler sb = new StringBundler();

		sb.append(_FILTER_SQL_COUNT_ASSETLISTENTRY_WHERE);

		if (groupIds.length > 0) {
			sb.append("(");

			sb.append(_FINDER_COLUMN_G_AES_AET_GROUPID_7);

			sb.append(StringUtil.merge(groupIds));

			sb.append(")");

			sb.append(")");

			sb.append(WHERE_AND);
		}

		boolean bindAssetEntrySubtype = false;

		if (assetEntrySubtype.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_AES_AET_ASSETENTRYSUBTYPE_3);
		}
		else {
			bindAssetEntrySubtype = true;

			sb.append(_FINDER_COLUMN_G_AES_AET_ASSETENTRYSUBTYPE_2);
		}

		boolean bindAssetEntryType = false;

		if (assetEntryType.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_AES_AET_ASSETENTRYTYPE_3);
		}
		else {
			bindAssetEntryType = true;

			sb.append(_FINDER_COLUMN_G_AES_AET_ASSETENTRYTYPE_2);
		}

		sb.setStringAt(
			removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), AssetListEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupIds);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			if (bindAssetEntrySubtype) {
				queryPos.add(assetEntrySubtype);
			}

			if (bindAssetEntryType) {
				queryPos.add(assetEntryType);
			}

			Long count = (Long)sqlQuery.uniqueResult();

			return count.intValue();
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_G_AES_AET_GROUPID_2 =
		"assetListEntry.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_AES_AET_GROUPID_7 =
		"assetListEntry.groupId IN (";

	private static final String _FINDER_COLUMN_G_AES_AET_ASSETENTRYSUBTYPE_2 =
		"assetListEntry.assetEntrySubtype = ? AND ";

	private static final String _FINDER_COLUMN_G_AES_AET_ASSETENTRYSUBTYPE_3 =
		"(assetListEntry.assetEntrySubtype IS NULL OR assetListEntry.assetEntrySubtype = '') AND ";

	private static final String _FINDER_COLUMN_G_AES_AET_ASSETENTRYTYPE_2 =
		"assetListEntry.assetEntryType = ?";

	private static final String _FINDER_COLUMN_G_AES_AET_ASSETENTRYTYPE_3 =
		"(assetListEntry.assetEntryType IS NULL OR assetListEntry.assetEntryType = '')";

	private FinderPath _finderPathWithPaginationFindByG_LikeT_AES_AET;
	private FinderPath _finderPathWithPaginationCountByG_LikeT_AES_AET;

	/**
	 * Returns all the asset list entries where groupId = &#63; and title LIKE &#63; and assetEntrySubtype = &#63; and assetEntryType = &#63;.
	 *
	 * @param groupId the group ID
	 * @param title the title
	 * @param assetEntrySubtype the asset entry subtype
	 * @param assetEntryType the asset entry type
	 * @return the matching asset list entries
	 */
	@Override
	public List<AssetListEntry> findByG_LikeT_AES_AET(
		long groupId, String title, String assetEntrySubtype,
		String assetEntryType) {

		return findByG_LikeT_AES_AET(
			groupId, title, assetEntrySubtype, assetEntryType,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset list entries where groupId = &#63; and title LIKE &#63; and assetEntrySubtype = &#63; and assetEntryType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param title the title
	 * @param assetEntrySubtype the asset entry subtype
	 * @param assetEntryType the asset entry type
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @return the range of matching asset list entries
	 */
	@Override
	public List<AssetListEntry> findByG_LikeT_AES_AET(
		long groupId, String title, String assetEntrySubtype,
		String assetEntryType, int start, int end) {

		return findByG_LikeT_AES_AET(
			groupId, title, assetEntrySubtype, assetEntryType, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the asset list entries where groupId = &#63; and title LIKE &#63; and assetEntrySubtype = &#63; and assetEntryType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param title the title
	 * @param assetEntrySubtype the asset entry subtype
	 * @param assetEntryType the asset entry type
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset list entries
	 */
	@Override
	public List<AssetListEntry> findByG_LikeT_AES_AET(
		long groupId, String title, String assetEntrySubtype,
		String assetEntryType, int start, int end,
		OrderByComparator<AssetListEntry> orderByComparator) {

		return findByG_LikeT_AES_AET(
			groupId, title, assetEntrySubtype, assetEntryType, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset list entries where groupId = &#63; and title LIKE &#63; and assetEntrySubtype = &#63; and assetEntryType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param title the title
	 * @param assetEntrySubtype the asset entry subtype
	 * @param assetEntryType the asset entry type
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset list entries
	 */
	@Override
	public List<AssetListEntry> findByG_LikeT_AES_AET(
		long groupId, String title, String assetEntrySubtype,
		String assetEntryType, int start, int end,
		OrderByComparator<AssetListEntry> orderByComparator,
		boolean useFinderCache) {

		title = Objects.toString(title, "");
		assetEntrySubtype = Objects.toString(assetEntrySubtype, "");
		assetEntryType = Objects.toString(assetEntryType, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetListEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByG_LikeT_AES_AET;
		finderArgs = new Object[] {
			groupId, title, assetEntrySubtype, assetEntryType, start, end,
			orderByComparator
		};

		List<AssetListEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<AssetListEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetListEntry assetListEntry : list) {
					if ((groupId != assetListEntry.getGroupId()) ||
						!StringUtil.wildcardMatches(
							assetListEntry.getTitle(), title, '_', '%', '\\',
							true) ||
						!assetEntrySubtype.equals(
							assetListEntry.getAssetEntrySubtype()) ||
						!assetEntryType.equals(
							assetListEntry.getAssetEntryType())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					6 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(6);
			}

			sb.append(_SQL_SELECT_ASSETLISTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_LIKET_AES_AET_GROUPID_2);

			boolean bindTitle = false;

			if (title.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_LIKET_AES_AET_TITLE_3);
			}
			else {
				bindTitle = true;

				sb.append(_FINDER_COLUMN_G_LIKET_AES_AET_TITLE_2);
			}

			boolean bindAssetEntrySubtype = false;

			if (assetEntrySubtype.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_LIKET_AES_AET_ASSETENTRYSUBTYPE_3);
			}
			else {
				bindAssetEntrySubtype = true;

				sb.append(_FINDER_COLUMN_G_LIKET_AES_AET_ASSETENTRYSUBTYPE_2);
			}

			boolean bindAssetEntryType = false;

			if (assetEntryType.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_LIKET_AES_AET_ASSETENTRYTYPE_3);
			}
			else {
				bindAssetEntryType = true;

				sb.append(_FINDER_COLUMN_G_LIKET_AES_AET_ASSETENTRYTYPE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AssetListEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindTitle) {
					queryPos.add(title);
				}

				if (bindAssetEntrySubtype) {
					queryPos.add(assetEntrySubtype);
				}

				if (bindAssetEntryType) {
					queryPos.add(assetEntryType);
				}

				list = (List<AssetListEntry>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first asset list entry in the ordered set where groupId = &#63; and title LIKE &#63; and assetEntrySubtype = &#63; and assetEntryType = &#63;.
	 *
	 * @param groupId the group ID
	 * @param title the title
	 * @param assetEntrySubtype the asset entry subtype
	 * @param assetEntryType the asset entry type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry
	 * @throws NoSuchEntryException if a matching asset list entry could not be found
	 */
	@Override
	public AssetListEntry findByG_LikeT_AES_AET_First(
			long groupId, String title, String assetEntrySubtype,
			String assetEntryType,
			OrderByComparator<AssetListEntry> orderByComparator)
		throws NoSuchEntryException {

		AssetListEntry assetListEntry = fetchByG_LikeT_AES_AET_First(
			groupId, title, assetEntrySubtype, assetEntryType,
			orderByComparator);

		if (assetListEntry != null) {
			return assetListEntry;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", titleLIKE");
		sb.append(title);

		sb.append(", assetEntrySubtype=");
		sb.append(assetEntrySubtype);

		sb.append(", assetEntryType=");
		sb.append(assetEntryType);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first asset list entry in the ordered set where groupId = &#63; and title LIKE &#63; and assetEntrySubtype = &#63; and assetEntryType = &#63;.
	 *
	 * @param groupId the group ID
	 * @param title the title
	 * @param assetEntrySubtype the asset entry subtype
	 * @param assetEntryType the asset entry type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset list entry, or <code>null</code> if a matching asset list entry could not be found
	 */
	@Override
	public AssetListEntry fetchByG_LikeT_AES_AET_First(
		long groupId, String title, String assetEntrySubtype,
		String assetEntryType,
		OrderByComparator<AssetListEntry> orderByComparator) {

		List<AssetListEntry> list = findByG_LikeT_AES_AET(
			groupId, title, assetEntrySubtype, assetEntryType, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset list entry in the ordered set where groupId = &#63; and title LIKE &#63; and assetEntrySubtype = &#63; and assetEntryType = &#63;.
	 *
	 * @param groupId the group ID
	 * @param title the title
	 * @param assetEntrySubtype the asset entry subtype
	 * @param assetEntryType the asset entry type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry
	 * @throws NoSuchEntryException if a matching asset list entry could not be found
	 */
	@Override
	public AssetListEntry findByG_LikeT_AES_AET_Last(
			long groupId, String title, String assetEntrySubtype,
			String assetEntryType,
			OrderByComparator<AssetListEntry> orderByComparator)
		throws NoSuchEntryException {

		AssetListEntry assetListEntry = fetchByG_LikeT_AES_AET_Last(
			groupId, title, assetEntrySubtype, assetEntryType,
			orderByComparator);

		if (assetListEntry != null) {
			return assetListEntry;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", titleLIKE");
		sb.append(title);

		sb.append(", assetEntrySubtype=");
		sb.append(assetEntrySubtype);

		sb.append(", assetEntryType=");
		sb.append(assetEntryType);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last asset list entry in the ordered set where groupId = &#63; and title LIKE &#63; and assetEntrySubtype = &#63; and assetEntryType = &#63;.
	 *
	 * @param groupId the group ID
	 * @param title the title
	 * @param assetEntrySubtype the asset entry subtype
	 * @param assetEntryType the asset entry type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset list entry, or <code>null</code> if a matching asset list entry could not be found
	 */
	@Override
	public AssetListEntry fetchByG_LikeT_AES_AET_Last(
		long groupId, String title, String assetEntrySubtype,
		String assetEntryType,
		OrderByComparator<AssetListEntry> orderByComparator) {

		int count = countByG_LikeT_AES_AET(
			groupId, title, assetEntrySubtype, assetEntryType);

		if (count == 0) {
			return null;
		}

		List<AssetListEntry> list = findByG_LikeT_AES_AET(
			groupId, title, assetEntrySubtype, assetEntryType, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset list entries before and after the current asset list entry in the ordered set where groupId = &#63; and title LIKE &#63; and assetEntrySubtype = &#63; and assetEntryType = &#63;.
	 *
	 * @param assetListEntryId the primary key of the current asset list entry
	 * @param groupId the group ID
	 * @param title the title
	 * @param assetEntrySubtype the asset entry subtype
	 * @param assetEntryType the asset entry type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset list entry
	 * @throws NoSuchEntryException if a asset list entry with the primary key could not be found
	 */
	@Override
	public AssetListEntry[] findByG_LikeT_AES_AET_PrevAndNext(
			long assetListEntryId, long groupId, String title,
			String assetEntrySubtype, String assetEntryType,
			OrderByComparator<AssetListEntry> orderByComparator)
		throws NoSuchEntryException {

		title = Objects.toString(title, "");
		assetEntrySubtype = Objects.toString(assetEntrySubtype, "");
		assetEntryType = Objects.toString(assetEntryType, "");

		AssetListEntry assetListEntry = findByPrimaryKey(assetListEntryId);

		Session session = null;

		try {
			session = openSession();

			AssetListEntry[] array = new AssetListEntryImpl[3];

			array[0] = getByG_LikeT_AES_AET_PrevAndNext(
				session, assetListEntry, groupId, title, assetEntrySubtype,
				assetEntryType, orderByComparator, true);

			array[1] = assetListEntry;

			array[2] = getByG_LikeT_AES_AET_PrevAndNext(
				session, assetListEntry, groupId, title, assetEntrySubtype,
				assetEntryType, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetListEntry getByG_LikeT_AES_AET_PrevAndNext(
		Session session, AssetListEntry assetListEntry, long groupId,
		String title, String assetEntrySubtype, String assetEntryType,
		OrderByComparator<AssetListEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				7 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(6);
		}

		sb.append(_SQL_SELECT_ASSETLISTENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_LIKET_AES_AET_GROUPID_2);

		boolean bindTitle = false;

		if (title.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_LIKET_AES_AET_TITLE_3);
		}
		else {
			bindTitle = true;

			sb.append(_FINDER_COLUMN_G_LIKET_AES_AET_TITLE_2);
		}

		boolean bindAssetEntrySubtype = false;

		if (assetEntrySubtype.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_LIKET_AES_AET_ASSETENTRYSUBTYPE_3);
		}
		else {
			bindAssetEntrySubtype = true;

			sb.append(_FINDER_COLUMN_G_LIKET_AES_AET_ASSETENTRYSUBTYPE_2);
		}

		boolean bindAssetEntryType = false;

		if (assetEntryType.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_LIKET_AES_AET_ASSETENTRYTYPE_3);
		}
		else {
			bindAssetEntryType = true;

			sb.append(_FINDER_COLUMN_G_LIKET_AES_AET_ASSETENTRYTYPE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(AssetListEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		if (bindTitle) {
			queryPos.add(title);
		}

		if (bindAssetEntrySubtype) {
			queryPos.add(assetEntrySubtype);
		}

		if (bindAssetEntryType) {
			queryPos.add(assetEntryType);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						assetListEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AssetListEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the asset list entries that the user has permission to view where groupId = &#63; and title LIKE &#63; and assetEntrySubtype = &#63; and assetEntryType = &#63;.
	 *
	 * @param groupId the group ID
	 * @param title the title
	 * @param assetEntrySubtype the asset entry subtype
	 * @param assetEntryType the asset entry type
	 * @return the matching asset list entries that the user has permission to view
	 */
	@Override
	public List<AssetListEntry> filterFindByG_LikeT_AES_AET(
		long groupId, String title, String assetEntrySubtype,
		String assetEntryType) {

		return filterFindByG_LikeT_AES_AET(
			groupId, title, assetEntrySubtype, assetEntryType,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset list entries that the user has permission to view where groupId = &#63; and title LIKE &#63; and assetEntrySubtype = &#63; and assetEntryType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param title the title
	 * @param assetEntrySubtype the asset entry subtype
	 * @param assetEntryType the asset entry type
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @return the range of matching asset list entries that the user has permission to view
	 */
	@Override
	public List<AssetListEntry> filterFindByG_LikeT_AES_AET(
		long groupId, String title, String assetEntrySubtype,
		String assetEntryType, int start, int end) {

		return filterFindByG_LikeT_AES_AET(
			groupId, title, assetEntrySubtype, assetEntryType, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the asset list entries that the user has permissions to view where groupId = &#63; and title LIKE &#63; and assetEntrySubtype = &#63; and assetEntryType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param title the title
	 * @param assetEntrySubtype the asset entry subtype
	 * @param assetEntryType the asset entry type
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset list entries that the user has permission to view
	 */
	@Override
	public List<AssetListEntry> filterFindByG_LikeT_AES_AET(
		long groupId, String title, String assetEntrySubtype,
		String assetEntryType, int start, int end,
		OrderByComparator<AssetListEntry> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_LikeT_AES_AET(
				groupId, title, assetEntrySubtype, assetEntryType, start, end,
				orderByComparator);
		}

		title = Objects.toString(title, "");
		assetEntrySubtype = Objects.toString(assetEntrySubtype, "");
		assetEntryType = Objects.toString(assetEntryType, "");

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(7);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_ASSETLISTENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_ASSETLISTENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_LIKET_AES_AET_GROUPID_2);

		boolean bindTitle = false;

		if (title.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_LIKET_AES_AET_TITLE_3);
		}
		else {
			bindTitle = true;

			sb.append(_FINDER_COLUMN_G_LIKET_AES_AET_TITLE_2);
		}

		boolean bindAssetEntrySubtype = false;

		if (assetEntrySubtype.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_LIKET_AES_AET_ASSETENTRYSUBTYPE_3);
		}
		else {
			bindAssetEntrySubtype = true;

			sb.append(_FINDER_COLUMN_G_LIKET_AES_AET_ASSETENTRYSUBTYPE_2);
		}

		boolean bindAssetEntryType = false;

		if (assetEntryType.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_LIKET_AES_AET_ASSETENTRYTYPE_3);
		}
		else {
			bindAssetEntryType = true;

			sb.append(_FINDER_COLUMN_G_LIKET_AES_AET_ASSETENTRYTYPE_2);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_ASSETLISTENTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(AssetListEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(AssetListEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), AssetListEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, AssetListEntryImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, AssetListEntryImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			if (bindTitle) {
				queryPos.add(title);
			}

			if (bindAssetEntrySubtype) {
				queryPos.add(assetEntrySubtype);
			}

			if (bindAssetEntryType) {
				queryPos.add(assetEntryType);
			}

			return (List<AssetListEntry>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the asset list entries before and after the current asset list entry in the ordered set of asset list entries that the user has permission to view where groupId = &#63; and title LIKE &#63; and assetEntrySubtype = &#63; and assetEntryType = &#63;.
	 *
	 * @param assetListEntryId the primary key of the current asset list entry
	 * @param groupId the group ID
	 * @param title the title
	 * @param assetEntrySubtype the asset entry subtype
	 * @param assetEntryType the asset entry type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset list entry
	 * @throws NoSuchEntryException if a asset list entry with the primary key could not be found
	 */
	@Override
	public AssetListEntry[] filterFindByG_LikeT_AES_AET_PrevAndNext(
			long assetListEntryId, long groupId, String title,
			String assetEntrySubtype, String assetEntryType,
			OrderByComparator<AssetListEntry> orderByComparator)
		throws NoSuchEntryException {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_LikeT_AES_AET_PrevAndNext(
				assetListEntryId, groupId, title, assetEntrySubtype,
				assetEntryType, orderByComparator);
		}

		title = Objects.toString(title, "");
		assetEntrySubtype = Objects.toString(assetEntrySubtype, "");
		assetEntryType = Objects.toString(assetEntryType, "");

		AssetListEntry assetListEntry = findByPrimaryKey(assetListEntryId);

		Session session = null;

		try {
			session = openSession();

			AssetListEntry[] array = new AssetListEntryImpl[3];

			array[0] = filterGetByG_LikeT_AES_AET_PrevAndNext(
				session, assetListEntry, groupId, title, assetEntrySubtype,
				assetEntryType, orderByComparator, true);

			array[1] = assetListEntry;

			array[2] = filterGetByG_LikeT_AES_AET_PrevAndNext(
				session, assetListEntry, groupId, title, assetEntrySubtype,
				assetEntryType, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetListEntry filterGetByG_LikeT_AES_AET_PrevAndNext(
		Session session, AssetListEntry assetListEntry, long groupId,
		String title, String assetEntrySubtype, String assetEntryType,
		OrderByComparator<AssetListEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				8 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(7);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_ASSETLISTENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_ASSETLISTENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_LIKET_AES_AET_GROUPID_2);

		boolean bindTitle = false;

		if (title.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_LIKET_AES_AET_TITLE_3);
		}
		else {
			bindTitle = true;

			sb.append(_FINDER_COLUMN_G_LIKET_AES_AET_TITLE_2);
		}

		boolean bindAssetEntrySubtype = false;

		if (assetEntrySubtype.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_LIKET_AES_AET_ASSETENTRYSUBTYPE_3);
		}
		else {
			bindAssetEntrySubtype = true;

			sb.append(_FINDER_COLUMN_G_LIKET_AES_AET_ASSETENTRYSUBTYPE_2);
		}

		boolean bindAssetEntryType = false;

		if (assetEntryType.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_LIKET_AES_AET_ASSETENTRYTYPE_3);
		}
		else {
			bindAssetEntryType = true;

			sb.append(_FINDER_COLUMN_G_LIKET_AES_AET_ASSETENTRYTYPE_2);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_ASSETLISTENTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByConditionFields[i],
							true));
				}
				else {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByConditionFields[i],
							true));
				}

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByFields[i], true));
				}
				else {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByFields[i], true));
				}

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(AssetListEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(AssetListEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), AssetListEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(_FILTER_ENTITY_ALIAS, AssetListEntryImpl.class);
		}
		else {
			sqlQuery.addEntity(_FILTER_ENTITY_TABLE, AssetListEntryImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(groupId);

		if (bindTitle) {
			queryPos.add(title);
		}

		if (bindAssetEntrySubtype) {
			queryPos.add(assetEntrySubtype);
		}

		if (bindAssetEntryType) {
			queryPos.add(assetEntryType);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						assetListEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AssetListEntry> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the asset list entries that the user has permission to view where groupId = any &#63; and title LIKE &#63; and assetEntrySubtype = &#63; and assetEntryType = &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param title the title
	 * @param assetEntrySubtype the asset entry subtype
	 * @param assetEntryType the asset entry type
	 * @return the matching asset list entries that the user has permission to view
	 */
	@Override
	public List<AssetListEntry> filterFindByG_LikeT_AES_AET(
		long[] groupIds, String title, String assetEntrySubtype,
		String assetEntryType) {

		return filterFindByG_LikeT_AES_AET(
			groupIds, title, assetEntrySubtype, assetEntryType,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset list entries that the user has permission to view where groupId = any &#63; and title LIKE &#63; and assetEntrySubtype = &#63; and assetEntryType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param title the title
	 * @param assetEntrySubtype the asset entry subtype
	 * @param assetEntryType the asset entry type
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @return the range of matching asset list entries that the user has permission to view
	 */
	@Override
	public List<AssetListEntry> filterFindByG_LikeT_AES_AET(
		long[] groupIds, String title, String assetEntrySubtype,
		String assetEntryType, int start, int end) {

		return filterFindByG_LikeT_AES_AET(
			groupIds, title, assetEntrySubtype, assetEntryType, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the asset list entries that the user has permission to view where groupId = any &#63; and title LIKE &#63; and assetEntrySubtype = &#63; and assetEntryType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param title the title
	 * @param assetEntrySubtype the asset entry subtype
	 * @param assetEntryType the asset entry type
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset list entries that the user has permission to view
	 */
	@Override
	public List<AssetListEntry> filterFindByG_LikeT_AES_AET(
		long[] groupIds, String title, String assetEntrySubtype,
		String assetEntryType, int start, int end,
		OrderByComparator<AssetListEntry> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupIds)) {
			return findByG_LikeT_AES_AET(
				groupIds, title, assetEntrySubtype, assetEntryType, start, end,
				orderByComparator);
		}

		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.sortedUnique(groupIds);
		}

		title = Objects.toString(title, "");
		assetEntrySubtype = Objects.toString(assetEntrySubtype, "");
		assetEntryType = Objects.toString(assetEntryType, "");

		StringBundler sb = new StringBundler();

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_ASSETLISTENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_ASSETLISTENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		if (groupIds.length > 0) {
			sb.append("(");

			sb.append(_FINDER_COLUMN_G_LIKET_AES_AET_GROUPID_7);

			sb.append(StringUtil.merge(groupIds));

			sb.append(")");

			sb.append(")");

			sb.append(WHERE_AND);
		}

		boolean bindTitle = false;

		if (title.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_LIKET_AES_AET_TITLE_3);
		}
		else {
			bindTitle = true;

			sb.append(_FINDER_COLUMN_G_LIKET_AES_AET_TITLE_2);
		}

		boolean bindAssetEntrySubtype = false;

		if (assetEntrySubtype.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_LIKET_AES_AET_ASSETENTRYSUBTYPE_3);
		}
		else {
			bindAssetEntrySubtype = true;

			sb.append(_FINDER_COLUMN_G_LIKET_AES_AET_ASSETENTRYSUBTYPE_2);
		}

		boolean bindAssetEntryType = false;

		if (assetEntryType.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_LIKET_AES_AET_ASSETENTRYTYPE_3);
		}
		else {
			bindAssetEntryType = true;

			sb.append(_FINDER_COLUMN_G_LIKET_AES_AET_ASSETENTRYTYPE_2);
		}

		sb.setStringAt(
			removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_ASSETLISTENTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(AssetListEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(AssetListEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), AssetListEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupIds);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, AssetListEntryImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, AssetListEntryImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			if (bindTitle) {
				queryPos.add(title);
			}

			if (bindAssetEntrySubtype) {
				queryPos.add(assetEntrySubtype);
			}

			if (bindAssetEntryType) {
				queryPos.add(assetEntryType);
			}

			return (List<AssetListEntry>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns all the asset list entries where groupId = any &#63; and title LIKE &#63; and assetEntrySubtype = &#63; and assetEntryType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param title the title
	 * @param assetEntrySubtype the asset entry subtype
	 * @param assetEntryType the asset entry type
	 * @return the matching asset list entries
	 */
	@Override
	public List<AssetListEntry> findByG_LikeT_AES_AET(
		long[] groupIds, String title, String assetEntrySubtype,
		String assetEntryType) {

		return findByG_LikeT_AES_AET(
			groupIds, title, assetEntrySubtype, assetEntryType,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset list entries where groupId = any &#63; and title LIKE &#63; and assetEntrySubtype = &#63; and assetEntryType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param title the title
	 * @param assetEntrySubtype the asset entry subtype
	 * @param assetEntryType the asset entry type
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @return the range of matching asset list entries
	 */
	@Override
	public List<AssetListEntry> findByG_LikeT_AES_AET(
		long[] groupIds, String title, String assetEntrySubtype,
		String assetEntryType, int start, int end) {

		return findByG_LikeT_AES_AET(
			groupIds, title, assetEntrySubtype, assetEntryType, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the asset list entries where groupId = any &#63; and title LIKE &#63; and assetEntrySubtype = &#63; and assetEntryType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param title the title
	 * @param assetEntrySubtype the asset entry subtype
	 * @param assetEntryType the asset entry type
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset list entries
	 */
	@Override
	public List<AssetListEntry> findByG_LikeT_AES_AET(
		long[] groupIds, String title, String assetEntrySubtype,
		String assetEntryType, int start, int end,
		OrderByComparator<AssetListEntry> orderByComparator) {

		return findByG_LikeT_AES_AET(
			groupIds, title, assetEntrySubtype, assetEntryType, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset list entries where groupId = &#63; and title LIKE &#63; and assetEntrySubtype = &#63; and assetEntryType = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param title the title
	 * @param assetEntrySubtype the asset entry subtype
	 * @param assetEntryType the asset entry type
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset list entries
	 */
	@Override
	public List<AssetListEntry> findByG_LikeT_AES_AET(
		long[] groupIds, String title, String assetEntrySubtype,
		String assetEntryType, int start, int end,
		OrderByComparator<AssetListEntry> orderByComparator,
		boolean useFinderCache) {

		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.sortedUnique(groupIds);
		}

		title = Objects.toString(title, "");
		assetEntrySubtype = Objects.toString(assetEntrySubtype, "");
		assetEntryType = Objects.toString(assetEntryType, "");

		if (groupIds.length == 1) {
			return findByG_LikeT_AES_AET(
				groupIds[0], title, assetEntrySubtype, assetEntryType, start,
				end, orderByComparator);
		}

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetListEntry.class);

		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderArgs = new Object[] {
					StringUtil.merge(groupIds), title, assetEntrySubtype,
					assetEntryType
				};
			}
		}
		else if (useFinderCache && productionMode) {
			finderArgs = new Object[] {
				StringUtil.merge(groupIds), title, assetEntrySubtype,
				assetEntryType, start, end, orderByComparator
			};
		}

		List<AssetListEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<AssetListEntry>)finderCache.getResult(
				_finderPathWithPaginationFindByG_LikeT_AES_AET, finderArgs,
				this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetListEntry assetListEntry : list) {
					if (!ArrayUtil.contains(
							groupIds, assetListEntry.getGroupId()) ||
						!StringUtil.wildcardMatches(
							assetListEntry.getTitle(), title, '_', '%', '\\',
							true) ||
						!assetEntrySubtype.equals(
							assetListEntry.getAssetEntrySubtype()) ||
						!assetEntryType.equals(
							assetListEntry.getAssetEntryType())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_SELECT_ASSETLISTENTRY_WHERE);

			if (groupIds.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_G_LIKET_AES_AET_GROUPID_7);

				sb.append(StringUtil.merge(groupIds));

				sb.append(")");

				sb.append(")");

				sb.append(WHERE_AND);
			}

			boolean bindTitle = false;

			if (title.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_LIKET_AES_AET_TITLE_3);
			}
			else {
				bindTitle = true;

				sb.append(_FINDER_COLUMN_G_LIKET_AES_AET_TITLE_2);
			}

			boolean bindAssetEntrySubtype = false;

			if (assetEntrySubtype.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_LIKET_AES_AET_ASSETENTRYSUBTYPE_3);
			}
			else {
				bindAssetEntrySubtype = true;

				sb.append(_FINDER_COLUMN_G_LIKET_AES_AET_ASSETENTRYSUBTYPE_2);
			}

			boolean bindAssetEntryType = false;

			if (assetEntryType.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_LIKET_AES_AET_ASSETENTRYTYPE_3);
			}
			else {
				bindAssetEntryType = true;

				sb.append(_FINDER_COLUMN_G_LIKET_AES_AET_ASSETENTRYTYPE_2);
			}

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AssetListEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindTitle) {
					queryPos.add(title);
				}

				if (bindAssetEntrySubtype) {
					queryPos.add(assetEntrySubtype);
				}

				if (bindAssetEntryType) {
					queryPos.add(assetEntryType);
				}

				list = (List<AssetListEntry>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					finderCache.putResult(
						_finderPathWithPaginationFindByG_LikeT_AES_AET,
						finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the asset list entries where groupId = &#63; and title LIKE &#63; and assetEntrySubtype = &#63; and assetEntryType = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param title the title
	 * @param assetEntrySubtype the asset entry subtype
	 * @param assetEntryType the asset entry type
	 */
	@Override
	public void removeByG_LikeT_AES_AET(
		long groupId, String title, String assetEntrySubtype,
		String assetEntryType) {

		for (AssetListEntry assetListEntry :
				findByG_LikeT_AES_AET(
					groupId, title, assetEntrySubtype, assetEntryType,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(assetListEntry);
		}
	}

	/**
	 * Returns the number of asset list entries where groupId = &#63; and title LIKE &#63; and assetEntrySubtype = &#63; and assetEntryType = &#63;.
	 *
	 * @param groupId the group ID
	 * @param title the title
	 * @param assetEntrySubtype the asset entry subtype
	 * @param assetEntryType the asset entry type
	 * @return the number of matching asset list entries
	 */
	@Override
	public int countByG_LikeT_AES_AET(
		long groupId, String title, String assetEntrySubtype,
		String assetEntryType) {

		title = Objects.toString(title, "");
		assetEntrySubtype = Objects.toString(assetEntrySubtype, "");
		assetEntryType = Objects.toString(assetEntryType, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetListEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathWithPaginationCountByG_LikeT_AES_AET;

			finderArgs = new Object[] {
				groupId, title, assetEntrySubtype, assetEntryType
			};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_COUNT_ASSETLISTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_LIKET_AES_AET_GROUPID_2);

			boolean bindTitle = false;

			if (title.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_LIKET_AES_AET_TITLE_3);
			}
			else {
				bindTitle = true;

				sb.append(_FINDER_COLUMN_G_LIKET_AES_AET_TITLE_2);
			}

			boolean bindAssetEntrySubtype = false;

			if (assetEntrySubtype.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_LIKET_AES_AET_ASSETENTRYSUBTYPE_3);
			}
			else {
				bindAssetEntrySubtype = true;

				sb.append(_FINDER_COLUMN_G_LIKET_AES_AET_ASSETENTRYSUBTYPE_2);
			}

			boolean bindAssetEntryType = false;

			if (assetEntryType.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_LIKET_AES_AET_ASSETENTRYTYPE_3);
			}
			else {
				bindAssetEntryType = true;

				sb.append(_FINDER_COLUMN_G_LIKET_AES_AET_ASSETENTRYTYPE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindTitle) {
					queryPos.add(title);
				}

				if (bindAssetEntrySubtype) {
					queryPos.add(assetEntrySubtype);
				}

				if (bindAssetEntryType) {
					queryPos.add(assetEntryType);
				}

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of asset list entries where groupId = any &#63; and title LIKE &#63; and assetEntrySubtype = &#63; and assetEntryType = &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param title the title
	 * @param assetEntrySubtype the asset entry subtype
	 * @param assetEntryType the asset entry type
	 * @return the number of matching asset list entries
	 */
	@Override
	public int countByG_LikeT_AES_AET(
		long[] groupIds, String title, String assetEntrySubtype,
		String assetEntryType) {

		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.sortedUnique(groupIds);
		}

		title = Objects.toString(title, "");
		assetEntrySubtype = Objects.toString(assetEntrySubtype, "");
		assetEntryType = Objects.toString(assetEntryType, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetListEntry.class);

		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderArgs = new Object[] {
				StringUtil.merge(groupIds), title, assetEntrySubtype,
				assetEntryType
			};

			count = (Long)finderCache.getResult(
				_finderPathWithPaginationCountByG_LikeT_AES_AET, finderArgs,
				this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_COUNT_ASSETLISTENTRY_WHERE);

			if (groupIds.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_G_LIKET_AES_AET_GROUPID_7);

				sb.append(StringUtil.merge(groupIds));

				sb.append(")");

				sb.append(")");

				sb.append(WHERE_AND);
			}

			boolean bindTitle = false;

			if (title.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_LIKET_AES_AET_TITLE_3);
			}
			else {
				bindTitle = true;

				sb.append(_FINDER_COLUMN_G_LIKET_AES_AET_TITLE_2);
			}

			boolean bindAssetEntrySubtype = false;

			if (assetEntrySubtype.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_LIKET_AES_AET_ASSETENTRYSUBTYPE_3);
			}
			else {
				bindAssetEntrySubtype = true;

				sb.append(_FINDER_COLUMN_G_LIKET_AES_AET_ASSETENTRYSUBTYPE_2);
			}

			boolean bindAssetEntryType = false;

			if (assetEntryType.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_LIKET_AES_AET_ASSETENTRYTYPE_3);
			}
			else {
				bindAssetEntryType = true;

				sb.append(_FINDER_COLUMN_G_LIKET_AES_AET_ASSETENTRYTYPE_2);
			}

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindTitle) {
					queryPos.add(title);
				}

				if (bindAssetEntrySubtype) {
					queryPos.add(assetEntrySubtype);
				}

				if (bindAssetEntryType) {
					queryPos.add(assetEntryType);
				}

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(
						_finderPathWithPaginationCountByG_LikeT_AES_AET,
						finderArgs, count);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of asset list entries that the user has permission to view where groupId = &#63; and title LIKE &#63; and assetEntrySubtype = &#63; and assetEntryType = &#63;.
	 *
	 * @param groupId the group ID
	 * @param title the title
	 * @param assetEntrySubtype the asset entry subtype
	 * @param assetEntryType the asset entry type
	 * @return the number of matching asset list entries that the user has permission to view
	 */
	@Override
	public int filterCountByG_LikeT_AES_AET(
		long groupId, String title, String assetEntrySubtype,
		String assetEntryType) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_LikeT_AES_AET(
				groupId, title, assetEntrySubtype, assetEntryType);
		}

		title = Objects.toString(title, "");
		assetEntrySubtype = Objects.toString(assetEntrySubtype, "");
		assetEntryType = Objects.toString(assetEntryType, "");

		StringBundler sb = new StringBundler(5);

		sb.append(_FILTER_SQL_COUNT_ASSETLISTENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_LIKET_AES_AET_GROUPID_2);

		boolean bindTitle = false;

		if (title.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_LIKET_AES_AET_TITLE_3);
		}
		else {
			bindTitle = true;

			sb.append(_FINDER_COLUMN_G_LIKET_AES_AET_TITLE_2);
		}

		boolean bindAssetEntrySubtype = false;

		if (assetEntrySubtype.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_LIKET_AES_AET_ASSETENTRYSUBTYPE_3);
		}
		else {
			bindAssetEntrySubtype = true;

			sb.append(_FINDER_COLUMN_G_LIKET_AES_AET_ASSETENTRYSUBTYPE_2);
		}

		boolean bindAssetEntryType = false;

		if (assetEntryType.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_LIKET_AES_AET_ASSETENTRYTYPE_3);
		}
		else {
			bindAssetEntryType = true;

			sb.append(_FINDER_COLUMN_G_LIKET_AES_AET_ASSETENTRYTYPE_2);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), AssetListEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			if (bindTitle) {
				queryPos.add(title);
			}

			if (bindAssetEntrySubtype) {
				queryPos.add(assetEntrySubtype);
			}

			if (bindAssetEntryType) {
				queryPos.add(assetEntryType);
			}

			Long count = (Long)sqlQuery.uniqueResult();

			return count.intValue();
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the number of asset list entries that the user has permission to view where groupId = any &#63; and title LIKE &#63; and assetEntrySubtype = &#63; and assetEntryType = &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param title the title
	 * @param assetEntrySubtype the asset entry subtype
	 * @param assetEntryType the asset entry type
	 * @return the number of matching asset list entries that the user has permission to view
	 */
	@Override
	public int filterCountByG_LikeT_AES_AET(
		long[] groupIds, String title, String assetEntrySubtype,
		String assetEntryType) {

		if (!InlineSQLHelperUtil.isEnabled(groupIds)) {
			return countByG_LikeT_AES_AET(
				groupIds, title, assetEntrySubtype, assetEntryType);
		}

		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.sortedUnique(groupIds);
		}

		title = Objects.toString(title, "");
		assetEntrySubtype = Objects.toString(assetEntrySubtype, "");
		assetEntryType = Objects.toString(assetEntryType, "");

		StringBundler sb = new StringBundler();

		sb.append(_FILTER_SQL_COUNT_ASSETLISTENTRY_WHERE);

		if (groupIds.length > 0) {
			sb.append("(");

			sb.append(_FINDER_COLUMN_G_LIKET_AES_AET_GROUPID_7);

			sb.append(StringUtil.merge(groupIds));

			sb.append(")");

			sb.append(")");

			sb.append(WHERE_AND);
		}

		boolean bindTitle = false;

		if (title.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_LIKET_AES_AET_TITLE_3);
		}
		else {
			bindTitle = true;

			sb.append(_FINDER_COLUMN_G_LIKET_AES_AET_TITLE_2);
		}

		boolean bindAssetEntrySubtype = false;

		if (assetEntrySubtype.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_LIKET_AES_AET_ASSETENTRYSUBTYPE_3);
		}
		else {
			bindAssetEntrySubtype = true;

			sb.append(_FINDER_COLUMN_G_LIKET_AES_AET_ASSETENTRYSUBTYPE_2);
		}

		boolean bindAssetEntryType = false;

		if (assetEntryType.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_LIKET_AES_AET_ASSETENTRYTYPE_3);
		}
		else {
			bindAssetEntryType = true;

			sb.append(_FINDER_COLUMN_G_LIKET_AES_AET_ASSETENTRYTYPE_2);
		}

		sb.setStringAt(
			removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), AssetListEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupIds);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			if (bindTitle) {
				queryPos.add(title);
			}

			if (bindAssetEntrySubtype) {
				queryPos.add(assetEntrySubtype);
			}

			if (bindAssetEntryType) {
				queryPos.add(assetEntryType);
			}

			Long count = (Long)sqlQuery.uniqueResult();

			return count.intValue();
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_G_LIKET_AES_AET_GROUPID_2 =
		"assetListEntry.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_LIKET_AES_AET_GROUPID_7 =
		"assetListEntry.groupId IN (";

	private static final String _FINDER_COLUMN_G_LIKET_AES_AET_TITLE_2 =
		"assetListEntry.title LIKE ? AND ";

	private static final String _FINDER_COLUMN_G_LIKET_AES_AET_TITLE_3 =
		"(assetListEntry.title IS NULL OR assetListEntry.title LIKE '') AND ";

	private static final String
		_FINDER_COLUMN_G_LIKET_AES_AET_ASSETENTRYSUBTYPE_2 =
			"assetListEntry.assetEntrySubtype = ? AND ";

	private static final String
		_FINDER_COLUMN_G_LIKET_AES_AET_ASSETENTRYSUBTYPE_3 =
			"(assetListEntry.assetEntrySubtype IS NULL OR assetListEntry.assetEntrySubtype = '') AND ";

	private static final String
		_FINDER_COLUMN_G_LIKET_AES_AET_ASSETENTRYTYPE_2 =
			"assetListEntry.assetEntryType = ?";

	private static final String
		_FINDER_COLUMN_G_LIKET_AES_AET_ASSETENTRYTYPE_3 =
			"(assetListEntry.assetEntryType IS NULL OR assetListEntry.assetEntryType = '')";

	public AssetListEntryPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put("type", "type_");

		setDBColumnNames(dbColumnNames);

		setModelClass(AssetListEntry.class);

		setModelImplClass(AssetListEntryImpl.class);
		setModelPKClass(long.class);

		setTable(AssetListEntryTable.INSTANCE);
	}

	/**
	 * Caches the asset list entry in the entity cache if it is enabled.
	 *
	 * @param assetListEntry the asset list entry
	 */
	@Override
	public void cacheResult(AssetListEntry assetListEntry) {
		if (assetListEntry.getCtCollectionId() != 0) {
			assetListEntry.resetOriginalValues();

			return;
		}

		entityCache.putResult(
			AssetListEntryImpl.class, assetListEntry.getPrimaryKey(),
			assetListEntry);

		finderCache.putResult(
			_finderPathFetchByUUID_G,
			new Object[] {
				assetListEntry.getUuid(), assetListEntry.getGroupId()
			},
			assetListEntry);

		finderCache.putResult(
			_finderPathFetchByG_ALEK,
			new Object[] {
				assetListEntry.getGroupId(),
				assetListEntry.getAssetListEntryKey()
			},
			assetListEntry);

		finderCache.putResult(
			_finderPathFetchByG_T,
			new Object[] {
				assetListEntry.getGroupId(), assetListEntry.getTitle()
			},
			assetListEntry);

		assetListEntry.resetOriginalValues();
	}

	/**
	 * Caches the asset list entries in the entity cache if it is enabled.
	 *
	 * @param assetListEntries the asset list entries
	 */
	@Override
	public void cacheResult(List<AssetListEntry> assetListEntries) {
		for (AssetListEntry assetListEntry : assetListEntries) {
			if (assetListEntry.getCtCollectionId() != 0) {
				assetListEntry.resetOriginalValues();

				continue;
			}

			if (entityCache.getResult(
					AssetListEntryImpl.class, assetListEntry.getPrimaryKey()) ==
						null) {

				cacheResult(assetListEntry);
			}
			else {
				assetListEntry.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all asset list entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(AssetListEntryImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the asset list entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(AssetListEntry assetListEntry) {
		entityCache.removeResult(
			AssetListEntryImpl.class, assetListEntry.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((AssetListEntryModelImpl)assetListEntry, true);
	}

	@Override
	public void clearCache(List<AssetListEntry> assetListEntries) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (AssetListEntry assetListEntry : assetListEntries) {
			entityCache.removeResult(
				AssetListEntryImpl.class, assetListEntry.getPrimaryKey());

			clearUniqueFindersCache(
				(AssetListEntryModelImpl)assetListEntry, true);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(AssetListEntryImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		AssetListEntryModelImpl assetListEntryModelImpl) {

		Object[] args = new Object[] {
			assetListEntryModelImpl.getUuid(),
			assetListEntryModelImpl.getGroupId()
		};

		finderCache.putResult(
			_finderPathCountByUUID_G, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByUUID_G, args, assetListEntryModelImpl, false);

		args = new Object[] {
			assetListEntryModelImpl.getGroupId(),
			assetListEntryModelImpl.getAssetListEntryKey()
		};

		finderCache.putResult(
			_finderPathCountByG_ALEK, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByG_ALEK, args, assetListEntryModelImpl, false);

		args = new Object[] {
			assetListEntryModelImpl.getGroupId(),
			assetListEntryModelImpl.getTitle()
		};

		finderCache.putResult(
			_finderPathCountByG_T, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByG_T, args, assetListEntryModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		AssetListEntryModelImpl assetListEntryModelImpl, boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				assetListEntryModelImpl.getUuid(),
				assetListEntryModelImpl.getGroupId()
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(_finderPathFetchByUUID_G, args);
		}

		if ((assetListEntryModelImpl.getColumnBitmask() &
			 _finderPathFetchByUUID_G.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				assetListEntryModelImpl.getOriginalUuid(),
				assetListEntryModelImpl.getOriginalGroupId()
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(_finderPathFetchByUUID_G, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
				assetListEntryModelImpl.getGroupId(),
				assetListEntryModelImpl.getAssetListEntryKey()
			};

			finderCache.removeResult(_finderPathCountByG_ALEK, args);
			finderCache.removeResult(_finderPathFetchByG_ALEK, args);
		}

		if ((assetListEntryModelImpl.getColumnBitmask() &
			 _finderPathFetchByG_ALEK.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				assetListEntryModelImpl.getOriginalGroupId(),
				assetListEntryModelImpl.getOriginalAssetListEntryKey()
			};

			finderCache.removeResult(_finderPathCountByG_ALEK, args);
			finderCache.removeResult(_finderPathFetchByG_ALEK, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
				assetListEntryModelImpl.getGroupId(),
				assetListEntryModelImpl.getTitle()
			};

			finderCache.removeResult(_finderPathCountByG_T, args);
			finderCache.removeResult(_finderPathFetchByG_T, args);
		}

		if ((assetListEntryModelImpl.getColumnBitmask() &
			 _finderPathFetchByG_T.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				assetListEntryModelImpl.getOriginalGroupId(),
				assetListEntryModelImpl.getOriginalTitle()
			};

			finderCache.removeResult(_finderPathCountByG_T, args);
			finderCache.removeResult(_finderPathFetchByG_T, args);
		}
	}

	/**
	 * Creates a new asset list entry with the primary key. Does not add the asset list entry to the database.
	 *
	 * @param assetListEntryId the primary key for the new asset list entry
	 * @return the new asset list entry
	 */
	@Override
	public AssetListEntry create(long assetListEntryId) {
		AssetListEntry assetListEntry = new AssetListEntryImpl();

		assetListEntry.setNew(true);
		assetListEntry.setPrimaryKey(assetListEntryId);

		String uuid = PortalUUIDUtil.generate();

		assetListEntry.setUuid(uuid);

		assetListEntry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return assetListEntry;
	}

	/**
	 * Removes the asset list entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetListEntryId the primary key of the asset list entry
	 * @return the asset list entry that was removed
	 * @throws NoSuchEntryException if a asset list entry with the primary key could not be found
	 */
	@Override
	public AssetListEntry remove(long assetListEntryId)
		throws NoSuchEntryException {

		return remove((Serializable)assetListEntryId);
	}

	/**
	 * Removes the asset list entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the asset list entry
	 * @return the asset list entry that was removed
	 * @throws NoSuchEntryException if a asset list entry with the primary key could not be found
	 */
	@Override
	public AssetListEntry remove(Serializable primaryKey)
		throws NoSuchEntryException {

		Session session = null;

		try {
			session = openSession();

			AssetListEntry assetListEntry = (AssetListEntry)session.get(
				AssetListEntryImpl.class, primaryKey);

			if (assetListEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(assetListEntry);
		}
		catch (NoSuchEntryException noSuchEntityException) {
			throw noSuchEntityException;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected AssetListEntry removeImpl(AssetListEntry assetListEntry) {
		if (!ctPersistenceHelper.isRemove(assetListEntry)) {
			return assetListEntry;
		}

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(assetListEntry)) {
				assetListEntry = (AssetListEntry)session.get(
					AssetListEntryImpl.class,
					assetListEntry.getPrimaryKeyObj());
			}

			if (assetListEntry != null) {
				session.delete(assetListEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (assetListEntry != null) {
			clearCache(assetListEntry);
		}

		return assetListEntry;
	}

	@Override
	public AssetListEntry updateImpl(AssetListEntry assetListEntry) {
		boolean isNew = assetListEntry.isNew();

		if (!(assetListEntry instanceof AssetListEntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(assetListEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					assetListEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in assetListEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom AssetListEntry implementation " +
					assetListEntry.getClass());
		}

		AssetListEntryModelImpl assetListEntryModelImpl =
			(AssetListEntryModelImpl)assetListEntry;

		if (Validator.isNull(assetListEntry.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			assetListEntry.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (assetListEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				assetListEntry.setCreateDate(now);
			}
			else {
				assetListEntry.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!assetListEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				assetListEntry.setModifiedDate(now);
			}
			else {
				assetListEntry.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(assetListEntry)) {
				if (!isNew) {
					session.evict(
						AssetListEntryImpl.class,
						assetListEntry.getPrimaryKeyObj());
				}

				session.save(assetListEntry);

				assetListEntry.setNew(false);
			}
			else {
				assetListEntry = (AssetListEntry)session.merge(assetListEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (assetListEntry.getCtCollectionId() != 0) {
			assetListEntry.resetOriginalValues();

			return assetListEntry;
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew) {
			Object[] args = new Object[] {assetListEntryModelImpl.getUuid()};

			finderCache.removeResult(_finderPathCountByUuid, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid, args);

			args = new Object[] {
				assetListEntryModelImpl.getUuid(),
				assetListEntryModelImpl.getCompanyId()
			};

			finderCache.removeResult(_finderPathCountByUuid_C, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid_C, args);

			args = new Object[] {assetListEntryModelImpl.getGroupId()};

			finderCache.removeResult(_finderPathCountByGroupId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByGroupId, args);

			args = new Object[] {
				assetListEntryModelImpl.getGroupId(),
				assetListEntryModelImpl.getType()
			};

			finderCache.removeResult(_finderPathCountByG_TY, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByG_TY, args);

			args = new Object[] {
				assetListEntryModelImpl.getGroupId(),
				assetListEntryModelImpl.getAssetEntryType()
			};

			finderCache.removeResult(_finderPathCountByG_AET, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByG_AET, args);

			args = new Object[] {
				assetListEntryModelImpl.getGroupId(),
				assetListEntryModelImpl.getAssetEntrySubtype(),
				assetListEntryModelImpl.getAssetEntryType()
			};

			finderCache.removeResult(_finderPathCountByG_AES_AET, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByG_AES_AET, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((assetListEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					assetListEntryModelImpl.getOriginalUuid()
				};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);

				args = new Object[] {assetListEntryModelImpl.getUuid()};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);
			}

			if ((assetListEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					assetListEntryModelImpl.getOriginalUuid(),
					assetListEntryModelImpl.getOriginalCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);

				args = new Object[] {
					assetListEntryModelImpl.getUuid(),
					assetListEntryModelImpl.getCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);
			}

			if ((assetListEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByGroupId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					assetListEntryModelImpl.getOriginalGroupId()
				};

				finderCache.removeResult(_finderPathCountByGroupId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByGroupId, args);

				args = new Object[] {assetListEntryModelImpl.getGroupId()};

				finderCache.removeResult(_finderPathCountByGroupId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByGroupId, args);
			}

			if ((assetListEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_TY.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					assetListEntryModelImpl.getOriginalGroupId(),
					assetListEntryModelImpl.getOriginalType()
				};

				finderCache.removeResult(_finderPathCountByG_TY, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_TY, args);

				args = new Object[] {
					assetListEntryModelImpl.getGroupId(),
					assetListEntryModelImpl.getType()
				};

				finderCache.removeResult(_finderPathCountByG_TY, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_TY, args);
			}

			if ((assetListEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_AET.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					assetListEntryModelImpl.getOriginalGroupId(),
					assetListEntryModelImpl.getOriginalAssetEntryType()
				};

				finderCache.removeResult(_finderPathCountByG_AET, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_AET, args);

				args = new Object[] {
					assetListEntryModelImpl.getGroupId(),
					assetListEntryModelImpl.getAssetEntryType()
				};

				finderCache.removeResult(_finderPathCountByG_AET, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_AET, args);
			}

			if ((assetListEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_AES_AET.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					assetListEntryModelImpl.getOriginalGroupId(),
					assetListEntryModelImpl.getOriginalAssetEntrySubtype(),
					assetListEntryModelImpl.getOriginalAssetEntryType()
				};

				finderCache.removeResult(_finderPathCountByG_AES_AET, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_AES_AET, args);

				args = new Object[] {
					assetListEntryModelImpl.getGroupId(),
					assetListEntryModelImpl.getAssetEntrySubtype(),
					assetListEntryModelImpl.getAssetEntryType()
				};

				finderCache.removeResult(_finderPathCountByG_AES_AET, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_AES_AET, args);
			}
		}

		entityCache.putResult(
			AssetListEntryImpl.class, assetListEntry.getPrimaryKey(),
			assetListEntry, false);

		clearUniqueFindersCache(assetListEntryModelImpl, false);
		cacheUniqueFindersCache(assetListEntryModelImpl);

		assetListEntry.resetOriginalValues();

		return assetListEntry;
	}

	/**
	 * Returns the asset list entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the asset list entry
	 * @return the asset list entry
	 * @throws NoSuchEntryException if a asset list entry with the primary key could not be found
	 */
	@Override
	public AssetListEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchEntryException {

		AssetListEntry assetListEntry = fetchByPrimaryKey(primaryKey);

		if (assetListEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return assetListEntry;
	}

	/**
	 * Returns the asset list entry with the primary key or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param assetListEntryId the primary key of the asset list entry
	 * @return the asset list entry
	 * @throws NoSuchEntryException if a asset list entry with the primary key could not be found
	 */
	@Override
	public AssetListEntry findByPrimaryKey(long assetListEntryId)
		throws NoSuchEntryException {

		return findByPrimaryKey((Serializable)assetListEntryId);
	}

	/**
	 * Returns the asset list entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the asset list entry
	 * @return the asset list entry, or <code>null</code> if a asset list entry with the primary key could not be found
	 */
	@Override
	public AssetListEntry fetchByPrimaryKey(Serializable primaryKey) {
		if (ctPersistenceHelper.isProductionMode(AssetListEntry.class)) {
			return super.fetchByPrimaryKey(primaryKey);
		}

		AssetListEntry assetListEntry = null;

		Session session = null;

		try {
			session = openSession();

			assetListEntry = (AssetListEntry)session.get(
				AssetListEntryImpl.class, primaryKey);

			if (assetListEntry != null) {
				cacheResult(assetListEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return assetListEntry;
	}

	/**
	 * Returns the asset list entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param assetListEntryId the primary key of the asset list entry
	 * @return the asset list entry, or <code>null</code> if a asset list entry with the primary key could not be found
	 */
	@Override
	public AssetListEntry fetchByPrimaryKey(long assetListEntryId) {
		return fetchByPrimaryKey((Serializable)assetListEntryId);
	}

	@Override
	public Map<Serializable, AssetListEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (ctPersistenceHelper.isProductionMode(AssetListEntry.class)) {
			return super.fetchByPrimaryKeys(primaryKeys);
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, AssetListEntry> map =
			new HashMap<Serializable, AssetListEntry>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			AssetListEntry assetListEntry = fetchByPrimaryKey(primaryKey);

			if (assetListEntry != null) {
				map.put(primaryKey, assetListEntry);
			}

			return map;
		}

		StringBundler sb = new StringBundler(primaryKeys.size() * 2 + 1);

		sb.append(getSelectSQL());
		sb.append(" WHERE ");
		sb.append(getPKDBName());
		sb.append(" IN (");

		for (Serializable primaryKey : primaryKeys) {
			sb.append((long)primaryKey);

			sb.append(",");
		}

		sb.setIndex(sb.index() - 1);

		sb.append(")");

		String sql = sb.toString();

		Session session = null;

		try {
			session = openSession();

			Query query = session.createQuery(sql);

			for (AssetListEntry assetListEntry :
					(List<AssetListEntry>)query.list()) {

				map.put(assetListEntry.getPrimaryKeyObj(), assetListEntry);

				cacheResult(assetListEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return map;
	}

	/**
	 * Returns all the asset list entries.
	 *
	 * @return the asset list entries
	 */
	@Override
	public List<AssetListEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset list entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @return the range of asset list entries
	 */
	@Override
	public List<AssetListEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset list entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of asset list entries
	 */
	@Override
	public List<AssetListEntry> findAll(
		int start, int end,
		OrderByComparator<AssetListEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset list entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetListEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset list entries
	 * @param end the upper bound of the range of asset list entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of asset list entries
	 */
	@Override
	public List<AssetListEntry> findAll(
		int start, int end, OrderByComparator<AssetListEntry> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetListEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindAll;
				finderArgs = FINDER_ARGS_EMPTY;
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<AssetListEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<AssetListEntry>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_ASSETLISTENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_ASSETLISTENTRY;

				sql = sql.concat(AssetListEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<AssetListEntry>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the asset list entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (AssetListEntry assetListEntry : findAll()) {
			remove(assetListEntry);
		}
	}

	/**
	 * Returns the number of asset list entries.
	 *
	 * @return the number of asset list entries
	 */
	@Override
	public int countAll() {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			AssetListEntry.class);

		Long count = null;

		if (productionMode) {
			count = (Long)finderCache.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY, this);
		}

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_ASSETLISTENTRY);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(
						_finderPathCountAll, FINDER_ARGS_EMPTY, count);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "assetListEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_ASSETLISTENTRY;
	}

	@Override
	public Set<String> getCTColumnNames(
		CTColumnResolutionType ctColumnResolutionType) {

		return _ctColumnNamesMap.get(ctColumnResolutionType);
	}

	@Override
	public List<String> getMappingTableNames() {
		return _mappingTableNames;
	}

	@Override
	public Map<String, Integer> getTableColumnsMap() {
		return AssetListEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "AssetListEntry";
	}

	@Override
	public List<String[]> getUniqueIndexColumnNames() {
		return _uniqueIndexColumnNames;
	}

	private static final Map<CTColumnResolutionType, Set<String>>
		_ctColumnNamesMap = new EnumMap<CTColumnResolutionType, Set<String>>(
			CTColumnResolutionType.class);
	private static final List<String> _mappingTableNames =
		new ArrayList<String>();
	private static final List<String[]> _uniqueIndexColumnNames =
		new ArrayList<String[]>();

	static {
		Set<String> ctControlColumnNames = new HashSet<String>();
		Set<String> ctIgnoreColumnNames = new HashSet<String>();
		Set<String> ctMergeColumnNames = new HashSet<String>();
		Set<String> ctStrictColumnNames = new HashSet<String>();

		ctControlColumnNames.add("mvccVersion");
		ctControlColumnNames.add("ctCollectionId");
		ctStrictColumnNames.add("uuid_");
		ctStrictColumnNames.add("groupId");
		ctStrictColumnNames.add("companyId");
		ctStrictColumnNames.add("userId");
		ctStrictColumnNames.add("userName");
		ctStrictColumnNames.add("createDate");
		ctIgnoreColumnNames.add("modifiedDate");
		ctStrictColumnNames.add("assetListEntryKey");
		ctStrictColumnNames.add("title");
		ctStrictColumnNames.add("type_");
		ctStrictColumnNames.add("assetEntrySubtype");
		ctStrictColumnNames.add("assetEntryType");
		ctStrictColumnNames.add("lastPublishDate");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK,
			Collections.singleton("assetListEntryId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(new String[] {"uuid_", "groupId"});

		_uniqueIndexColumnNames.add(
			new String[] {"groupId", "assetListEntryKey"});

		_uniqueIndexColumnNames.add(new String[] {"groupId", "title"});
	}

	/**
	 * Initializes the asset list entry persistence.
	 */
	@Activate
	public void activate() {
		_finderPathWithPaginationFindAll = new FinderPath(
			AssetListEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			AssetListEntryImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findAll", new String[0]);

		_finderPathCountAll = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByUuid = new FinderPath(
			AssetListEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			AssetListEntryImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByUuid", new String[] {String.class.getName()},
			AssetListEntryModelImpl.UUID_COLUMN_BITMASK);

		_finderPathCountByUuid = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid", new String[] {String.class.getName()});

		_finderPathFetchByUUID_G = new FinderPath(
			AssetListEntryImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			AssetListEntryModelImpl.UUID_COLUMN_BITMASK |
			AssetListEntryModelImpl.GROUPID_COLUMN_BITMASK);

		_finderPathCountByUUID_G = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			AssetListEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			AssetListEntryImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			AssetListEntryModelImpl.UUID_COLUMN_BITMASK |
			AssetListEntryModelImpl.COMPANYID_COLUMN_BITMASK);

		_finderPathCountByUuid_C = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByGroupId = new FinderPath(
			AssetListEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByGroupId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByGroupId = new FinderPath(
			AssetListEntryImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByGroupId", new String[] {Long.class.getName()},
			AssetListEntryModelImpl.GROUPID_COLUMN_BITMASK);

		_finderPathCountByGroupId = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByGroupId", new String[] {Long.class.getName()});

		_finderPathWithPaginationCountByGroupId = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"countByGroupId", new String[] {Long.class.getName()});

		_finderPathFetchByG_ALEK = new FinderPath(
			AssetListEntryImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByG_ALEK",
			new String[] {Long.class.getName(), String.class.getName()},
			AssetListEntryModelImpl.GROUPID_COLUMN_BITMASK |
			AssetListEntryModelImpl.ASSETLISTENTRYKEY_COLUMN_BITMASK);

		_finderPathCountByG_ALEK = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_ALEK",
			new String[] {Long.class.getName(), String.class.getName()});

		_finderPathFetchByG_T = new FinderPath(
			AssetListEntryImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByG_T",
			new String[] {Long.class.getName(), String.class.getName()},
			AssetListEntryModelImpl.GROUPID_COLUMN_BITMASK |
			AssetListEntryModelImpl.TITLE_COLUMN_BITMASK);

		_finderPathCountByG_T = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_T",
			new String[] {Long.class.getName(), String.class.getName()});

		_finderPathWithPaginationFindByG_LikeT = new FinderPath(
			AssetListEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_LikeT",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithPaginationCountByG_LikeT = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"countByG_LikeT",
			new String[] {Long.class.getName(), String.class.getName()});

		_finderPathWithPaginationFindByG_TY = new FinderPath(
			AssetListEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_TY",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_TY = new FinderPath(
			AssetListEntryImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByG_TY",
			new String[] {Long.class.getName(), Integer.class.getName()},
			AssetListEntryModelImpl.GROUPID_COLUMN_BITMASK |
			AssetListEntryModelImpl.TYPE_COLUMN_BITMASK);

		_finderPathCountByG_TY = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_TY",
			new String[] {Long.class.getName(), Integer.class.getName()});

		_finderPathWithPaginationFindByG_AET = new FinderPath(
			AssetListEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_AET",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_AET = new FinderPath(
			AssetListEntryImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByG_AET",
			new String[] {Long.class.getName(), String.class.getName()},
			AssetListEntryModelImpl.GROUPID_COLUMN_BITMASK |
			AssetListEntryModelImpl.ASSETENTRYTYPE_COLUMN_BITMASK);

		_finderPathCountByG_AET = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_AET",
			new String[] {Long.class.getName(), String.class.getName()});

		_finderPathWithPaginationCountByG_AET = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_AET",
			new String[] {Long.class.getName(), String.class.getName()});

		_finderPathWithPaginationFindByG_LikeT_AET = new FinderPath(
			AssetListEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_LikeT_AET",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithPaginationCountByG_LikeT_AET = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"countByG_LikeT_AET",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			});

		_finderPathWithPaginationFindByG_AES_AET = new FinderPath(
			AssetListEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_AES_AET",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_AES_AET = new FinderPath(
			AssetListEntryImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByG_AES_AET",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			},
			AssetListEntryModelImpl.GROUPID_COLUMN_BITMASK |
			AssetListEntryModelImpl.ASSETENTRYSUBTYPE_COLUMN_BITMASK |
			AssetListEntryModelImpl.ASSETENTRYTYPE_COLUMN_BITMASK);

		_finderPathCountByG_AES_AET = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_AES_AET",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			});

		_finderPathWithPaginationCountByG_AES_AET = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"countByG_AES_AET",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			});

		_finderPathWithPaginationFindByG_LikeT_AES_AET = new FinderPath(
			AssetListEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_LikeT_AES_AET",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithPaginationCountByG_LikeT_AES_AET = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"countByG_LikeT_AES_AET",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName(), String.class.getName()
			});
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(AssetListEntryImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	@Reference(
		target = AssetListPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = AssetListPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = AssetListPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected CTPersistenceHelper ctPersistenceHelper;

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_ASSETLISTENTRY =
		"SELECT assetListEntry FROM AssetListEntry assetListEntry";

	private static final String _SQL_SELECT_ASSETLISTENTRY_WHERE =
		"SELECT assetListEntry FROM AssetListEntry assetListEntry WHERE ";

	private static final String _SQL_COUNT_ASSETLISTENTRY =
		"SELECT COUNT(assetListEntry) FROM AssetListEntry assetListEntry";

	private static final String _SQL_COUNT_ASSETLISTENTRY_WHERE =
		"SELECT COUNT(assetListEntry) FROM AssetListEntry assetListEntry WHERE ";

	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN =
		"assetListEntry.assetListEntryId";

	private static final String _FILTER_SQL_SELECT_ASSETLISTENTRY_WHERE =
		"SELECT DISTINCT {assetListEntry.*} FROM AssetListEntry assetListEntry WHERE ";

	private static final String
		_FILTER_SQL_SELECT_ASSETLISTENTRY_NO_INLINE_DISTINCT_WHERE_1 =
			"SELECT {AssetListEntry.*} FROM (SELECT DISTINCT assetListEntry.assetListEntryId FROM AssetListEntry assetListEntry WHERE ";

	private static final String
		_FILTER_SQL_SELECT_ASSETLISTENTRY_NO_INLINE_DISTINCT_WHERE_2 =
			") TEMP_TABLE INNER JOIN AssetListEntry ON TEMP_TABLE.assetListEntryId = AssetListEntry.assetListEntryId";

	private static final String _FILTER_SQL_COUNT_ASSETLISTENTRY_WHERE =
		"SELECT COUNT(DISTINCT assetListEntry.assetListEntryId) AS COUNT_VALUE FROM AssetListEntry assetListEntry WHERE ";

	private static final String _FILTER_ENTITY_ALIAS = "assetListEntry";

	private static final String _FILTER_ENTITY_TABLE = "AssetListEntry";

	private static final String _ORDER_BY_ENTITY_ALIAS = "assetListEntry.";

	private static final String _ORDER_BY_ENTITY_TABLE = "AssetListEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No AssetListEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No AssetListEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		AssetListEntryPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid", "type"});

	static {
		try {
			Class.forName(AssetListPersistenceConstants.class.getName());
		}
		catch (ClassNotFoundException classNotFoundException) {
			throw new ExceptionInInitializerError(classNotFoundException);
		}
	}

}