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

package com.liferay.portal.service.persistence.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.change.tracking.CTColumnResolutionType;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.NoSuchSystemEventException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.SystemEvent;
import com.liferay.portal.kernel.model.SystemEventTable;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.persistence.SystemEventPersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.helper.CTPersistenceHelperUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.model.impl.SystemEventImpl;
import com.liferay.portal.model.impl.SystemEventModelImpl;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the system event service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class SystemEventPersistenceImpl
	extends BasePersistenceImpl<SystemEvent> implements SystemEventPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>SystemEventUtil</code> to access the system event persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		SystemEventImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByGroupId;
	private FinderPath _finderPathWithoutPaginationFindByGroupId;
	private FinderPath _finderPathCountByGroupId;

	/**
	 * Returns all the system events where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching system events
	 */
	@Override
	public List<SystemEvent> findByGroupId(long groupId) {
		return findByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the system events where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SystemEventModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of system events
	 * @param end the upper bound of the range of system events (not inclusive)
	 * @return the range of matching system events
	 */
	@Override
	public List<SystemEvent> findByGroupId(long groupId, int start, int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the system events where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SystemEventModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of system events
	 * @param end the upper bound of the range of system events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching system events
	 */
	@Override
	public List<SystemEvent> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<SystemEvent> orderByComparator) {

		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the system events where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SystemEventModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of system events
	 * @param end the upper bound of the range of system events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching system events
	 */
	@Override
	public List<SystemEvent> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<SystemEvent> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			SystemEvent.class);

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

		List<SystemEvent> list = null;

		if (useFinderCache && productionMode) {
			list = (List<SystemEvent>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SystemEvent systemEvent : list) {
					if (groupId != systemEvent.getGroupId()) {
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

			sb.append(_SQL_SELECT_SYSTEMEVENT_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(SystemEventModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				list = (List<SystemEvent>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
	 * Returns the first system event in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching system event
	 * @throws NoSuchSystemEventException if a matching system event could not be found
	 */
	@Override
	public SystemEvent findByGroupId_First(
			long groupId, OrderByComparator<SystemEvent> orderByComparator)
		throws NoSuchSystemEventException {

		SystemEvent systemEvent = fetchByGroupId_First(
			groupId, orderByComparator);

		if (systemEvent != null) {
			return systemEvent;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchSystemEventException(sb.toString());
	}

	/**
	 * Returns the first system event in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching system event, or <code>null</code> if a matching system event could not be found
	 */
	@Override
	public SystemEvent fetchByGroupId_First(
		long groupId, OrderByComparator<SystemEvent> orderByComparator) {

		List<SystemEvent> list = findByGroupId(
			groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last system event in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching system event
	 * @throws NoSuchSystemEventException if a matching system event could not be found
	 */
	@Override
	public SystemEvent findByGroupId_Last(
			long groupId, OrderByComparator<SystemEvent> orderByComparator)
		throws NoSuchSystemEventException {

		SystemEvent systemEvent = fetchByGroupId_Last(
			groupId, orderByComparator);

		if (systemEvent != null) {
			return systemEvent;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchSystemEventException(sb.toString());
	}

	/**
	 * Returns the last system event in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching system event, or <code>null</code> if a matching system event could not be found
	 */
	@Override
	public SystemEvent fetchByGroupId_Last(
		long groupId, OrderByComparator<SystemEvent> orderByComparator) {

		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<SystemEvent> list = findByGroupId(
			groupId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the system events before and after the current system event in the ordered set where groupId = &#63;.
	 *
	 * @param systemEventId the primary key of the current system event
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next system event
	 * @throws NoSuchSystemEventException if a system event with the primary key could not be found
	 */
	@Override
	public SystemEvent[] findByGroupId_PrevAndNext(
			long systemEventId, long groupId,
			OrderByComparator<SystemEvent> orderByComparator)
		throws NoSuchSystemEventException {

		SystemEvent systemEvent = findByPrimaryKey(systemEventId);

		Session session = null;

		try {
			session = openSession();

			SystemEvent[] array = new SystemEventImpl[3];

			array[0] = getByGroupId_PrevAndNext(
				session, systemEvent, groupId, orderByComparator, true);

			array[1] = systemEvent;

			array[2] = getByGroupId_PrevAndNext(
				session, systemEvent, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected SystemEvent getByGroupId_PrevAndNext(
		Session session, SystemEvent systemEvent, long groupId,
		OrderByComparator<SystemEvent> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_SYSTEMEVENT_WHERE);

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
			sb.append(SystemEventModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(systemEvent)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SystemEvent> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the system events where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (SystemEvent systemEvent :
				findByGroupId(
					groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(systemEvent);
		}
	}

	/**
	 * Returns the number of system events where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching system events
	 */
	@Override
	public int countByGroupId(long groupId) {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			SystemEvent.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByGroupId;

			finderArgs = new Object[] {groupId};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_SYSTEMEVENT_WHERE);

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
					FinderCacheUtil.putResult(finderPath, finderArgs, count);
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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 =
		"systemEvent.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByG_S;
	private FinderPath _finderPathWithoutPaginationFindByG_S;
	private FinderPath _finderPathCountByG_S;

	/**
	 * Returns all the system events where groupId = &#63; and systemEventSetKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param systemEventSetKey the system event set key
	 * @return the matching system events
	 */
	@Override
	public List<SystemEvent> findByG_S(long groupId, long systemEventSetKey) {
		return findByG_S(
			groupId, systemEventSetKey, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the system events where groupId = &#63; and systemEventSetKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SystemEventModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param systemEventSetKey the system event set key
	 * @param start the lower bound of the range of system events
	 * @param end the upper bound of the range of system events (not inclusive)
	 * @return the range of matching system events
	 */
	@Override
	public List<SystemEvent> findByG_S(
		long groupId, long systemEventSetKey, int start, int end) {

		return findByG_S(groupId, systemEventSetKey, start, end, null);
	}

	/**
	 * Returns an ordered range of all the system events where groupId = &#63; and systemEventSetKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SystemEventModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param systemEventSetKey the system event set key
	 * @param start the lower bound of the range of system events
	 * @param end the upper bound of the range of system events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching system events
	 */
	@Override
	public List<SystemEvent> findByG_S(
		long groupId, long systemEventSetKey, int start, int end,
		OrderByComparator<SystemEvent> orderByComparator) {

		return findByG_S(
			groupId, systemEventSetKey, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the system events where groupId = &#63; and systemEventSetKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SystemEventModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param systemEventSetKey the system event set key
	 * @param start the lower bound of the range of system events
	 * @param end the upper bound of the range of system events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching system events
	 */
	@Override
	public List<SystemEvent> findByG_S(
		long groupId, long systemEventSetKey, int start, int end,
		OrderByComparator<SystemEvent> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			SystemEvent.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByG_S;
				finderArgs = new Object[] {groupId, systemEventSetKey};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByG_S;
			finderArgs = new Object[] {
				groupId, systemEventSetKey, start, end, orderByComparator
			};
		}

		List<SystemEvent> list = null;

		if (useFinderCache && productionMode) {
			list = (List<SystemEvent>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SystemEvent systemEvent : list) {
					if ((groupId != systemEvent.getGroupId()) ||
						(systemEventSetKey !=
							systemEvent.getSystemEventSetKey())) {

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

			sb.append(_SQL_SELECT_SYSTEMEVENT_WHERE);

			sb.append(_FINDER_COLUMN_G_S_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_S_SYSTEMEVENTSETKEY_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(SystemEventModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(systemEventSetKey);

				list = (List<SystemEvent>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
	 * Returns the first system event in the ordered set where groupId = &#63; and systemEventSetKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param systemEventSetKey the system event set key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching system event
	 * @throws NoSuchSystemEventException if a matching system event could not be found
	 */
	@Override
	public SystemEvent findByG_S_First(
			long groupId, long systemEventSetKey,
			OrderByComparator<SystemEvent> orderByComparator)
		throws NoSuchSystemEventException {

		SystemEvent systemEvent = fetchByG_S_First(
			groupId, systemEventSetKey, orderByComparator);

		if (systemEvent != null) {
			return systemEvent;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", systemEventSetKey=");
		sb.append(systemEventSetKey);

		sb.append("}");

		throw new NoSuchSystemEventException(sb.toString());
	}

	/**
	 * Returns the first system event in the ordered set where groupId = &#63; and systemEventSetKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param systemEventSetKey the system event set key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching system event, or <code>null</code> if a matching system event could not be found
	 */
	@Override
	public SystemEvent fetchByG_S_First(
		long groupId, long systemEventSetKey,
		OrderByComparator<SystemEvent> orderByComparator) {

		List<SystemEvent> list = findByG_S(
			groupId, systemEventSetKey, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last system event in the ordered set where groupId = &#63; and systemEventSetKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param systemEventSetKey the system event set key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching system event
	 * @throws NoSuchSystemEventException if a matching system event could not be found
	 */
	@Override
	public SystemEvent findByG_S_Last(
			long groupId, long systemEventSetKey,
			OrderByComparator<SystemEvent> orderByComparator)
		throws NoSuchSystemEventException {

		SystemEvent systemEvent = fetchByG_S_Last(
			groupId, systemEventSetKey, orderByComparator);

		if (systemEvent != null) {
			return systemEvent;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", systemEventSetKey=");
		sb.append(systemEventSetKey);

		sb.append("}");

		throw new NoSuchSystemEventException(sb.toString());
	}

	/**
	 * Returns the last system event in the ordered set where groupId = &#63; and systemEventSetKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param systemEventSetKey the system event set key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching system event, or <code>null</code> if a matching system event could not be found
	 */
	@Override
	public SystemEvent fetchByG_S_Last(
		long groupId, long systemEventSetKey,
		OrderByComparator<SystemEvent> orderByComparator) {

		int count = countByG_S(groupId, systemEventSetKey);

		if (count == 0) {
			return null;
		}

		List<SystemEvent> list = findByG_S(
			groupId, systemEventSetKey, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the system events before and after the current system event in the ordered set where groupId = &#63; and systemEventSetKey = &#63;.
	 *
	 * @param systemEventId the primary key of the current system event
	 * @param groupId the group ID
	 * @param systemEventSetKey the system event set key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next system event
	 * @throws NoSuchSystemEventException if a system event with the primary key could not be found
	 */
	@Override
	public SystemEvent[] findByG_S_PrevAndNext(
			long systemEventId, long groupId, long systemEventSetKey,
			OrderByComparator<SystemEvent> orderByComparator)
		throws NoSuchSystemEventException {

		SystemEvent systemEvent = findByPrimaryKey(systemEventId);

		Session session = null;

		try {
			session = openSession();

			SystemEvent[] array = new SystemEventImpl[3];

			array[0] = getByG_S_PrevAndNext(
				session, systemEvent, groupId, systemEventSetKey,
				orderByComparator, true);

			array[1] = systemEvent;

			array[2] = getByG_S_PrevAndNext(
				session, systemEvent, groupId, systemEventSetKey,
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

	protected SystemEvent getByG_S_PrevAndNext(
		Session session, SystemEvent systemEvent, long groupId,
		long systemEventSetKey,
		OrderByComparator<SystemEvent> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_SYSTEMEVENT_WHERE);

		sb.append(_FINDER_COLUMN_G_S_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_S_SYSTEMEVENTSETKEY_2);

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
			sb.append(SystemEventModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(systemEventSetKey);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(systemEvent)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SystemEvent> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the system events where groupId = &#63; and systemEventSetKey = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param systemEventSetKey the system event set key
	 */
	@Override
	public void removeByG_S(long groupId, long systemEventSetKey) {
		for (SystemEvent systemEvent :
				findByG_S(
					groupId, systemEventSetKey, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(systemEvent);
		}
	}

	/**
	 * Returns the number of system events where groupId = &#63; and systemEventSetKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param systemEventSetKey the system event set key
	 * @return the number of matching system events
	 */
	@Override
	public int countByG_S(long groupId, long systemEventSetKey) {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			SystemEvent.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_S;

			finderArgs = new Object[] {groupId, systemEventSetKey};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_SYSTEMEVENT_WHERE);

			sb.append(_FINDER_COLUMN_G_S_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_S_SYSTEMEVENTSETKEY_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(systemEventSetKey);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, count);
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

	private static final String _FINDER_COLUMN_G_S_GROUPID_2 =
		"systemEvent.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_S_SYSTEMEVENTSETKEY_2 =
		"systemEvent.systemEventSetKey = ?";

	private FinderPath _finderPathWithPaginationFindByG_C_C;
	private FinderPath _finderPathWithoutPaginationFindByG_C_C;
	private FinderPath _finderPathCountByG_C_C;

	/**
	 * Returns all the system events where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching system events
	 */
	@Override
	public List<SystemEvent> findByG_C_C(
		long groupId, long classNameId, long classPK) {

		return findByG_C_C(
			groupId, classNameId, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the system events where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SystemEventModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of system events
	 * @param end the upper bound of the range of system events (not inclusive)
	 * @return the range of matching system events
	 */
	@Override
	public List<SystemEvent> findByG_C_C(
		long groupId, long classNameId, long classPK, int start, int end) {

		return findByG_C_C(groupId, classNameId, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the system events where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SystemEventModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of system events
	 * @param end the upper bound of the range of system events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching system events
	 */
	@Override
	public List<SystemEvent> findByG_C_C(
		long groupId, long classNameId, long classPK, int start, int end,
		OrderByComparator<SystemEvent> orderByComparator) {

		return findByG_C_C(
			groupId, classNameId, classPK, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the system events where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SystemEventModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of system events
	 * @param end the upper bound of the range of system events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching system events
	 */
	@Override
	public List<SystemEvent> findByG_C_C(
		long groupId, long classNameId, long classPK, int start, int end,
		OrderByComparator<SystemEvent> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			SystemEvent.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByG_C_C;
				finderArgs = new Object[] {groupId, classNameId, classPK};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByG_C_C;
			finderArgs = new Object[] {
				groupId, classNameId, classPK, start, end, orderByComparator
			};
		}

		List<SystemEvent> list = null;

		if (useFinderCache && productionMode) {
			list = (List<SystemEvent>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SystemEvent systemEvent : list) {
					if ((groupId != systemEvent.getGroupId()) ||
						(classNameId != systemEvent.getClassNameId()) ||
						(classPK != systemEvent.getClassPK())) {

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

			sb.append(_SQL_SELECT_SYSTEMEVENT_WHERE);

			sb.append(_FINDER_COLUMN_G_C_C_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_G_C_C_CLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(SystemEventModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				list = (List<SystemEvent>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
	 * Returns the first system event in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching system event
	 * @throws NoSuchSystemEventException if a matching system event could not be found
	 */
	@Override
	public SystemEvent findByG_C_C_First(
			long groupId, long classNameId, long classPK,
			OrderByComparator<SystemEvent> orderByComparator)
		throws NoSuchSystemEventException {

		SystemEvent systemEvent = fetchByG_C_C_First(
			groupId, classNameId, classPK, orderByComparator);

		if (systemEvent != null) {
			return systemEvent;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append("}");

		throw new NoSuchSystemEventException(sb.toString());
	}

	/**
	 * Returns the first system event in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching system event, or <code>null</code> if a matching system event could not be found
	 */
	@Override
	public SystemEvent fetchByG_C_C_First(
		long groupId, long classNameId, long classPK,
		OrderByComparator<SystemEvent> orderByComparator) {

		List<SystemEvent> list = findByG_C_C(
			groupId, classNameId, classPK, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last system event in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching system event
	 * @throws NoSuchSystemEventException if a matching system event could not be found
	 */
	@Override
	public SystemEvent findByG_C_C_Last(
			long groupId, long classNameId, long classPK,
			OrderByComparator<SystemEvent> orderByComparator)
		throws NoSuchSystemEventException {

		SystemEvent systemEvent = fetchByG_C_C_Last(
			groupId, classNameId, classPK, orderByComparator);

		if (systemEvent != null) {
			return systemEvent;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append("}");

		throw new NoSuchSystemEventException(sb.toString());
	}

	/**
	 * Returns the last system event in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching system event, or <code>null</code> if a matching system event could not be found
	 */
	@Override
	public SystemEvent fetchByG_C_C_Last(
		long groupId, long classNameId, long classPK,
		OrderByComparator<SystemEvent> orderByComparator) {

		int count = countByG_C_C(groupId, classNameId, classPK);

		if (count == 0) {
			return null;
		}

		List<SystemEvent> list = findByG_C_C(
			groupId, classNameId, classPK, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the system events before and after the current system event in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param systemEventId the primary key of the current system event
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next system event
	 * @throws NoSuchSystemEventException if a system event with the primary key could not be found
	 */
	@Override
	public SystemEvent[] findByG_C_C_PrevAndNext(
			long systemEventId, long groupId, long classNameId, long classPK,
			OrderByComparator<SystemEvent> orderByComparator)
		throws NoSuchSystemEventException {

		SystemEvent systemEvent = findByPrimaryKey(systemEventId);

		Session session = null;

		try {
			session = openSession();

			SystemEvent[] array = new SystemEventImpl[3];

			array[0] = getByG_C_C_PrevAndNext(
				session, systemEvent, groupId, classNameId, classPK,
				orderByComparator, true);

			array[1] = systemEvent;

			array[2] = getByG_C_C_PrevAndNext(
				session, systemEvent, groupId, classNameId, classPK,
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

	protected SystemEvent getByG_C_C_PrevAndNext(
		Session session, SystemEvent systemEvent, long groupId,
		long classNameId, long classPK,
		OrderByComparator<SystemEvent> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		sb.append(_SQL_SELECT_SYSTEMEVENT_WHERE);

		sb.append(_FINDER_COLUMN_G_C_C_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_C_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_G_C_C_CLASSPK_2);

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
			sb.append(SystemEventModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(classNameId);

		queryPos.add(classPK);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(systemEvent)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SystemEvent> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the system events where groupId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	@Override
	public void removeByG_C_C(long groupId, long classNameId, long classPK) {
		for (SystemEvent systemEvent :
				findByG_C_C(
					groupId, classNameId, classPK, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(systemEvent);
		}
	}

	/**
	 * Returns the number of system events where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching system events
	 */
	@Override
	public int countByG_C_C(long groupId, long classNameId, long classPK) {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			SystemEvent.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_C_C;

			finderArgs = new Object[] {groupId, classNameId, classPK};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_SYSTEMEVENT_WHERE);

			sb.append(_FINDER_COLUMN_G_C_C_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_G_C_C_CLASSPK_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, count);
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

	private static final String _FINDER_COLUMN_G_C_C_GROUPID_2 =
		"systemEvent.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_CLASSNAMEID_2 =
		"systemEvent.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_CLASSPK_2 =
		"systemEvent.classPK = ?";

	private FinderPath _finderPathWithPaginationFindByG_C_C_T;
	private FinderPath _finderPathWithoutPaginationFindByG_C_C_T;
	private FinderPath _finderPathCountByG_C_C_T;

	/**
	 * Returns all the system events where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @return the matching system events
	 */
	@Override
	public List<SystemEvent> findByG_C_C_T(
		long groupId, long classNameId, long classPK, int type) {

		return findByG_C_C_T(
			groupId, classNameId, classPK, type, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the system events where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SystemEventModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param start the lower bound of the range of system events
	 * @param end the upper bound of the range of system events (not inclusive)
	 * @return the range of matching system events
	 */
	@Override
	public List<SystemEvent> findByG_C_C_T(
		long groupId, long classNameId, long classPK, int type, int start,
		int end) {

		return findByG_C_C_T(
			groupId, classNameId, classPK, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the system events where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SystemEventModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param start the lower bound of the range of system events
	 * @param end the upper bound of the range of system events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching system events
	 */
	@Override
	public List<SystemEvent> findByG_C_C_T(
		long groupId, long classNameId, long classPK, int type, int start,
		int end, OrderByComparator<SystemEvent> orderByComparator) {

		return findByG_C_C_T(
			groupId, classNameId, classPK, type, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the system events where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SystemEventModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param start the lower bound of the range of system events
	 * @param end the upper bound of the range of system events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching system events
	 */
	@Override
	public List<SystemEvent> findByG_C_C_T(
		long groupId, long classNameId, long classPK, int type, int start,
		int end, OrderByComparator<SystemEvent> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			SystemEvent.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByG_C_C_T;
				finderArgs = new Object[] {groupId, classNameId, classPK, type};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByG_C_C_T;
			finderArgs = new Object[] {
				groupId, classNameId, classPK, type, start, end,
				orderByComparator
			};
		}

		List<SystemEvent> list = null;

		if (useFinderCache && productionMode) {
			list = (List<SystemEvent>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SystemEvent systemEvent : list) {
					if ((groupId != systemEvent.getGroupId()) ||
						(classNameId != systemEvent.getClassNameId()) ||
						(classPK != systemEvent.getClassPK()) ||
						(type != systemEvent.getType())) {

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

			sb.append(_SQL_SELECT_SYSTEMEVENT_WHERE);

			sb.append(_FINDER_COLUMN_G_C_C_T_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_C_T_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_G_C_C_T_CLASSPK_2);

			sb.append(_FINDER_COLUMN_G_C_C_T_TYPE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(SystemEventModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				queryPos.add(type);

				list = (List<SystemEvent>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
	 * Returns the first system event in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching system event
	 * @throws NoSuchSystemEventException if a matching system event could not be found
	 */
	@Override
	public SystemEvent findByG_C_C_T_First(
			long groupId, long classNameId, long classPK, int type,
			OrderByComparator<SystemEvent> orderByComparator)
		throws NoSuchSystemEventException {

		SystemEvent systemEvent = fetchByG_C_C_T_First(
			groupId, classNameId, classPK, type, orderByComparator);

		if (systemEvent != null) {
			return systemEvent;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchSystemEventException(sb.toString());
	}

	/**
	 * Returns the first system event in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching system event, or <code>null</code> if a matching system event could not be found
	 */
	@Override
	public SystemEvent fetchByG_C_C_T_First(
		long groupId, long classNameId, long classPK, int type,
		OrderByComparator<SystemEvent> orderByComparator) {

		List<SystemEvent> list = findByG_C_C_T(
			groupId, classNameId, classPK, type, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last system event in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching system event
	 * @throws NoSuchSystemEventException if a matching system event could not be found
	 */
	@Override
	public SystemEvent findByG_C_C_T_Last(
			long groupId, long classNameId, long classPK, int type,
			OrderByComparator<SystemEvent> orderByComparator)
		throws NoSuchSystemEventException {

		SystemEvent systemEvent = fetchByG_C_C_T_Last(
			groupId, classNameId, classPK, type, orderByComparator);

		if (systemEvent != null) {
			return systemEvent;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchSystemEventException(sb.toString());
	}

	/**
	 * Returns the last system event in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching system event, or <code>null</code> if a matching system event could not be found
	 */
	@Override
	public SystemEvent fetchByG_C_C_T_Last(
		long groupId, long classNameId, long classPK, int type,
		OrderByComparator<SystemEvent> orderByComparator) {

		int count = countByG_C_C_T(groupId, classNameId, classPK, type);

		if (count == 0) {
			return null;
		}

		List<SystemEvent> list = findByG_C_C_T(
			groupId, classNameId, classPK, type, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the system events before and after the current system event in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param systemEventId the primary key of the current system event
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next system event
	 * @throws NoSuchSystemEventException if a system event with the primary key could not be found
	 */
	@Override
	public SystemEvent[] findByG_C_C_T_PrevAndNext(
			long systemEventId, long groupId, long classNameId, long classPK,
			int type, OrderByComparator<SystemEvent> orderByComparator)
		throws NoSuchSystemEventException {

		SystemEvent systemEvent = findByPrimaryKey(systemEventId);

		Session session = null;

		try {
			session = openSession();

			SystemEvent[] array = new SystemEventImpl[3];

			array[0] = getByG_C_C_T_PrevAndNext(
				session, systemEvent, groupId, classNameId, classPK, type,
				orderByComparator, true);

			array[1] = systemEvent;

			array[2] = getByG_C_C_T_PrevAndNext(
				session, systemEvent, groupId, classNameId, classPK, type,
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

	protected SystemEvent getByG_C_C_T_PrevAndNext(
		Session session, SystemEvent systemEvent, long groupId,
		long classNameId, long classPK, int type,
		OrderByComparator<SystemEvent> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				7 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(6);
		}

		sb.append(_SQL_SELECT_SYSTEMEVENT_WHERE);

		sb.append(_FINDER_COLUMN_G_C_C_T_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_C_T_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_G_C_C_T_CLASSPK_2);

		sb.append(_FINDER_COLUMN_G_C_C_T_TYPE_2);

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
			sb.append(SystemEventModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(classNameId);

		queryPos.add(classPK);

		queryPos.add(type);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(systemEvent)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SystemEvent> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the system events where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 */
	@Override
	public void removeByG_C_C_T(
		long groupId, long classNameId, long classPK, int type) {

		for (SystemEvent systemEvent :
				findByG_C_C_T(
					groupId, classNameId, classPK, type, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(systemEvent);
		}
	}

	/**
	 * Returns the number of system events where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param type the type
	 * @return the number of matching system events
	 */
	@Override
	public int countByG_C_C_T(
		long groupId, long classNameId, long classPK, int type) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			SystemEvent.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_C_C_T;

			finderArgs = new Object[] {groupId, classNameId, classPK, type};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_COUNT_SYSTEMEVENT_WHERE);

			sb.append(_FINDER_COLUMN_G_C_C_T_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_C_T_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_G_C_C_T_CLASSPK_2);

			sb.append(_FINDER_COLUMN_G_C_C_T_TYPE_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				queryPos.add(type);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, count);
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

	private static final String _FINDER_COLUMN_G_C_C_T_GROUPID_2 =
		"systemEvent.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_T_CLASSNAMEID_2 =
		"systemEvent.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_T_CLASSPK_2 =
		"systemEvent.classPK = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_T_TYPE_2 =
		"systemEvent.type = ?";

	public SystemEventPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("type", "type_");

		setDBColumnNames(dbColumnNames);

		setModelClass(SystemEvent.class);

		setModelImplClass(SystemEventImpl.class);
		setModelPKClass(long.class);

		setTable(SystemEventTable.INSTANCE);
	}

	/**
	 * Caches the system event in the entity cache if it is enabled.
	 *
	 * @param systemEvent the system event
	 */
	@Override
	public void cacheResult(SystemEvent systemEvent) {
		if (systemEvent.getCtCollectionId() != 0) {
			return;
		}

		EntityCacheUtil.putResult(
			SystemEventImpl.class, systemEvent.getPrimaryKey(), systemEvent);
	}

	/**
	 * Caches the system events in the entity cache if it is enabled.
	 *
	 * @param systemEvents the system events
	 */
	@Override
	public void cacheResult(List<SystemEvent> systemEvents) {
		for (SystemEvent systemEvent : systemEvents) {
			if (systemEvent.getCtCollectionId() != 0) {
				continue;
			}

			if (EntityCacheUtil.getResult(
					SystemEventImpl.class, systemEvent.getPrimaryKey()) ==
						null) {

				cacheResult(systemEvent);
			}
		}
	}

	/**
	 * Clears the cache for all system events.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>com.liferay.portal.kernel.dao.orm.FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		EntityCacheUtil.clearCache(SystemEventImpl.class);

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the system event.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>com.liferay.portal.kernel.dao.orm.FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(SystemEvent systemEvent) {
		EntityCacheUtil.removeResult(
			SystemEventImpl.class, systemEvent.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<SystemEvent> systemEvents) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (SystemEvent systemEvent : systemEvents) {
			EntityCacheUtil.removeResult(
				SystemEventImpl.class, systemEvent.getPrimaryKey());
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			EntityCacheUtil.removeResult(SystemEventImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new system event with the primary key. Does not add the system event to the database.
	 *
	 * @param systemEventId the primary key for the new system event
	 * @return the new system event
	 */
	@Override
	public SystemEvent create(long systemEventId) {
		SystemEvent systemEvent = new SystemEventImpl();

		systemEvent.setNew(true);
		systemEvent.setPrimaryKey(systemEventId);

		systemEvent.setCompanyId(CompanyThreadLocal.getCompanyId());

		return systemEvent;
	}

	/**
	 * Removes the system event with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param systemEventId the primary key of the system event
	 * @return the system event that was removed
	 * @throws NoSuchSystemEventException if a system event with the primary key could not be found
	 */
	@Override
	public SystemEvent remove(long systemEventId)
		throws NoSuchSystemEventException {

		return remove((Serializable)systemEventId);
	}

	/**
	 * Removes the system event with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the system event
	 * @return the system event that was removed
	 * @throws NoSuchSystemEventException if a system event with the primary key could not be found
	 */
	@Override
	public SystemEvent remove(Serializable primaryKey)
		throws NoSuchSystemEventException {

		Session session = null;

		try {
			session = openSession();

			SystemEvent systemEvent = (SystemEvent)session.get(
				SystemEventImpl.class, primaryKey);

			if (systemEvent == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchSystemEventException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(systemEvent);
		}
		catch (NoSuchSystemEventException noSuchEntityException) {
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
	protected SystemEvent removeImpl(SystemEvent systemEvent) {
		if (!CTPersistenceHelperUtil.isRemove(systemEvent)) {
			return systemEvent;
		}

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(systemEvent)) {
				systemEvent = (SystemEvent)session.get(
					SystemEventImpl.class, systemEvent.getPrimaryKeyObj());
			}

			if (systemEvent != null) {
				session.delete(systemEvent);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (systemEvent != null) {
			clearCache(systemEvent);
		}

		return systemEvent;
	}

	@Override
	public SystemEvent updateImpl(SystemEvent systemEvent) {
		boolean isNew = systemEvent.isNew();

		if (!(systemEvent instanceof SystemEventModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(systemEvent.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(systemEvent);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in systemEvent proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom SystemEvent implementation " +
					systemEvent.getClass());
		}

		SystemEventModelImpl systemEventModelImpl =
			(SystemEventModelImpl)systemEvent;

		Session session = null;

		try {
			session = openSession();

			if (CTPersistenceHelperUtil.isInsert(systemEvent)) {
				if (!isNew) {
					session.evict(
						SystemEventImpl.class, systemEvent.getPrimaryKeyObj());
				}

				session.save(systemEvent);

				systemEvent.setNew(false);
			}
			else {
				systemEvent = (SystemEvent)session.merge(systemEvent);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (systemEvent.getCtCollectionId() != 0) {
			systemEvent.resetOriginalValues();

			return systemEvent;
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew) {
			Object[] args = new Object[] {systemEventModelImpl.getGroupId()};

			FinderCacheUtil.removeResult(_finderPathCountByGroupId, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByGroupId, args);

			args = new Object[] {
				systemEventModelImpl.getGroupId(),
				systemEventModelImpl.getSystemEventSetKey()
			};

			FinderCacheUtil.removeResult(_finderPathCountByG_S, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByG_S, args);

			args = new Object[] {
				systemEventModelImpl.getGroupId(),
				systemEventModelImpl.getClassNameId(),
				systemEventModelImpl.getClassPK()
			};

			FinderCacheUtil.removeResult(_finderPathCountByG_C_C, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByG_C_C, args);

			args = new Object[] {
				systemEventModelImpl.getGroupId(),
				systemEventModelImpl.getClassNameId(),
				systemEventModelImpl.getClassPK(),
				systemEventModelImpl.getType()
			};

			FinderCacheUtil.removeResult(_finderPathCountByG_C_C_T, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByG_C_C_T, args);

			FinderCacheUtil.removeResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((systemEventModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByGroupId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					systemEventModelImpl.getColumnOriginalValue("groupId")
				};

				FinderCacheUtil.removeResult(_finderPathCountByGroupId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByGroupId, args);

				args = new Object[] {systemEventModelImpl.getGroupId()};

				FinderCacheUtil.removeResult(_finderPathCountByGroupId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByGroupId, args);
			}

			if ((systemEventModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_S.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					systemEventModelImpl.getColumnOriginalValue("groupId"),
					systemEventModelImpl.getColumnOriginalValue(
						"systemEventSetKey")
				};

				FinderCacheUtil.removeResult(_finderPathCountByG_S, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByG_S, args);

				args = new Object[] {
					systemEventModelImpl.getGroupId(),
					systemEventModelImpl.getSystemEventSetKey()
				};

				FinderCacheUtil.removeResult(_finderPathCountByG_S, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByG_S, args);
			}

			if ((systemEventModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_C_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					systemEventModelImpl.getColumnOriginalValue("groupId"),
					systemEventModelImpl.getColumnOriginalValue("classNameId"),
					systemEventModelImpl.getColumnOriginalValue("classPK")
				};

				FinderCacheUtil.removeResult(_finderPathCountByG_C_C, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByG_C_C, args);

				args = new Object[] {
					systemEventModelImpl.getGroupId(),
					systemEventModelImpl.getClassNameId(),
					systemEventModelImpl.getClassPK()
				};

				FinderCacheUtil.removeResult(_finderPathCountByG_C_C, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByG_C_C, args);
			}

			if ((systemEventModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_C_C_T.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					systemEventModelImpl.getColumnOriginalValue("groupId"),
					systemEventModelImpl.getColumnOriginalValue("classNameId"),
					systemEventModelImpl.getColumnOriginalValue("classPK"),
					systemEventModelImpl.getColumnOriginalValue("type_")
				};

				FinderCacheUtil.removeResult(_finderPathCountByG_C_C_T, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByG_C_C_T, args);

				args = new Object[] {
					systemEventModelImpl.getGroupId(),
					systemEventModelImpl.getClassNameId(),
					systemEventModelImpl.getClassPK(),
					systemEventModelImpl.getType()
				};

				FinderCacheUtil.removeResult(_finderPathCountByG_C_C_T, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByG_C_C_T, args);
			}
		}

		EntityCacheUtil.putResult(
			SystemEventImpl.class, systemEvent.getPrimaryKey(), systemEvent,
			false);

		systemEvent.resetOriginalValues();

		return systemEvent;
	}

	/**
	 * Returns the system event with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the system event
	 * @return the system event
	 * @throws NoSuchSystemEventException if a system event with the primary key could not be found
	 */
	@Override
	public SystemEvent findByPrimaryKey(Serializable primaryKey)
		throws NoSuchSystemEventException {

		SystemEvent systemEvent = fetchByPrimaryKey(primaryKey);

		if (systemEvent == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchSystemEventException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return systemEvent;
	}

	/**
	 * Returns the system event with the primary key or throws a <code>NoSuchSystemEventException</code> if it could not be found.
	 *
	 * @param systemEventId the primary key of the system event
	 * @return the system event
	 * @throws NoSuchSystemEventException if a system event with the primary key could not be found
	 */
	@Override
	public SystemEvent findByPrimaryKey(long systemEventId)
		throws NoSuchSystemEventException {

		return findByPrimaryKey((Serializable)systemEventId);
	}

	/**
	 * Returns the system event with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the system event
	 * @return the system event, or <code>null</code> if a system event with the primary key could not be found
	 */
	@Override
	public SystemEvent fetchByPrimaryKey(Serializable primaryKey) {
		if (CTPersistenceHelperUtil.isProductionMode(SystemEvent.class)) {
			return super.fetchByPrimaryKey(primaryKey);
		}

		SystemEvent systemEvent = null;

		Session session = null;

		try {
			session = openSession();

			systemEvent = (SystemEvent)session.get(
				SystemEventImpl.class, primaryKey);

			if (systemEvent != null) {
				cacheResult(systemEvent);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return systemEvent;
	}

	/**
	 * Returns the system event with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param systemEventId the primary key of the system event
	 * @return the system event, or <code>null</code> if a system event with the primary key could not be found
	 */
	@Override
	public SystemEvent fetchByPrimaryKey(long systemEventId) {
		return fetchByPrimaryKey((Serializable)systemEventId);
	}

	@Override
	public Map<Serializable, SystemEvent> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (CTPersistenceHelperUtil.isProductionMode(SystemEvent.class)) {
			return super.fetchByPrimaryKeys(primaryKeys);
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, SystemEvent> map =
			new HashMap<Serializable, SystemEvent>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			SystemEvent systemEvent = fetchByPrimaryKey(primaryKey);

			if (systemEvent != null) {
				map.put(primaryKey, systemEvent);
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

			for (SystemEvent systemEvent : (List<SystemEvent>)query.list()) {
				map.put(systemEvent.getPrimaryKeyObj(), systemEvent);

				cacheResult(systemEvent);
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
	 * Returns all the system events.
	 *
	 * @return the system events
	 */
	@Override
	public List<SystemEvent> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the system events.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SystemEventModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of system events
	 * @param end the upper bound of the range of system events (not inclusive)
	 * @return the range of system events
	 */
	@Override
	public List<SystemEvent> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the system events.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SystemEventModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of system events
	 * @param end the upper bound of the range of system events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of system events
	 */
	@Override
	public List<SystemEvent> findAll(
		int start, int end, OrderByComparator<SystemEvent> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the system events.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SystemEventModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of system events
	 * @param end the upper bound of the range of system events (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of system events
	 */
	@Override
	public List<SystemEvent> findAll(
		int start, int end, OrderByComparator<SystemEvent> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			SystemEvent.class);

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

		List<SystemEvent> list = null;

		if (useFinderCache && productionMode) {
			list = (List<SystemEvent>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_SYSTEMEVENT);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_SYSTEMEVENT;

				sql = sql.concat(SystemEventModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<SystemEvent>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
	 * Removes all the system events from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (SystemEvent systemEvent : findAll()) {
			remove(systemEvent);
		}
	}

	/**
	 * Returns the number of system events.
	 *
	 * @return the number of system events
	 */
	@Override
	public int countAll() {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			SystemEvent.class);

		Long count = null;

		if (productionMode) {
			count = (Long)FinderCacheUtil.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY, this);
		}

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_SYSTEMEVENT);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(
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
		return EntityCacheUtil.getEntityCache();
	}

	@Override
	protected String getPKDBName() {
		return "systemEventId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_SYSTEMEVENT;
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
		return SystemEventModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "SystemEvent";
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
		ctStrictColumnNames.add("groupId");
		ctStrictColumnNames.add("companyId");
		ctStrictColumnNames.add("userId");
		ctStrictColumnNames.add("userName");
		ctStrictColumnNames.add("createDate");
		ctStrictColumnNames.add("classNameId");
		ctStrictColumnNames.add("classPK");
		ctStrictColumnNames.add("classUuid");
		ctStrictColumnNames.add("referrerClassNameId");
		ctStrictColumnNames.add("parentSystemEventId");
		ctStrictColumnNames.add("systemEventSetKey");
		ctStrictColumnNames.add("type_");
		ctStrictColumnNames.add("extraData");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK, Collections.singleton("systemEventId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);
	}

	/**
	 * Initializes the system event persistence.
	 */
	public void afterPropertiesSet() {
		_finderPathWithPaginationFindAll = new FinderPath(
			SystemEventImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			SystemEventImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findAll", new String[0]);

		_finderPathCountAll = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByGroupId = new FinderPath(
			SystemEventImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByGroupId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByGroupId = new FinderPath(
			SystemEventImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByGroupId", new String[] {Long.class.getName()},
			SystemEventModelImpl.getColumnBitmask("groupId") |
			SystemEventModelImpl.getColumnBitmask("createDate"));

		_finderPathCountByGroupId = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByGroupId", new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByG_S = new FinderPath(
			SystemEventImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_S = new FinderPath(
			SystemEventImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByG_S",
			new String[] {Long.class.getName(), Long.class.getName()},
			SystemEventModelImpl.getColumnBitmask("groupId") |
			SystemEventModelImpl.getColumnBitmask("systemEventSetKey") |
			SystemEventModelImpl.getColumnBitmask("createDate"));

		_finderPathCountByG_S = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_S",
			new String[] {Long.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByG_C_C = new FinderPath(
			SystemEventImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_C_C = new FinderPath(
			SystemEventImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByG_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			SystemEventModelImpl.getColumnBitmask("groupId") |
			SystemEventModelImpl.getColumnBitmask("classNameId") |
			SystemEventModelImpl.getColumnBitmask("classPK") |
			SystemEventModelImpl.getColumnBitmask("createDate"));

		_finderPathCountByG_C_C = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});

		_finderPathWithPaginationFindByG_C_C_T = new FinderPath(
			SystemEventImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_C_C_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_C_C_T = new FinderPath(
			SystemEventImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByG_C_C_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName()
			},
			SystemEventModelImpl.getColumnBitmask("groupId") |
			SystemEventModelImpl.getColumnBitmask("classNameId") |
			SystemEventModelImpl.getColumnBitmask("classPK") |
			SystemEventModelImpl.getColumnBitmask("type_") |
			SystemEventModelImpl.getColumnBitmask("createDate"));

		_finderPathCountByG_C_C_T = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_C_C_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName()
			});
	}

	public void destroy() {
		EntityCacheUtil.removeCache(SystemEventImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_SYSTEMEVENT =
		"SELECT systemEvent FROM SystemEvent systemEvent";

	private static final String _SQL_SELECT_SYSTEMEVENT_WHERE =
		"SELECT systemEvent FROM SystemEvent systemEvent WHERE ";

	private static final String _SQL_COUNT_SYSTEMEVENT =
		"SELECT COUNT(systemEvent) FROM SystemEvent systemEvent";

	private static final String _SQL_COUNT_SYSTEMEVENT_WHERE =
		"SELECT COUNT(systemEvent) FROM SystemEvent systemEvent WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "systemEvent.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No SystemEvent exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No SystemEvent exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		SystemEventPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"type"});

}