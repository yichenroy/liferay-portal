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

package com.liferay.message.boards.service.persistence.impl;

import com.liferay.message.boards.exception.NoSuchStatsUserException;
import com.liferay.message.boards.model.MBStatsUser;
import com.liferay.message.boards.model.MBStatsUserTable;
import com.liferay.message.boards.model.impl.MBStatsUserImpl;
import com.liferay.message.boards.model.impl.MBStatsUserModelImpl;
import com.liferay.message.boards.service.persistence.MBStatsUserPersistence;
import com.liferay.message.boards.service.persistence.impl.constants.MBPersistenceConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.change.tracking.CTColumnResolutionType;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.persistence.change.tracking.helper.CTPersistenceHelper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;

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

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the message boards stats user service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = MBStatsUserPersistence.class)
public class MBStatsUserPersistenceImpl
	extends BasePersistenceImpl<MBStatsUser> implements MBStatsUserPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>MBStatsUserUtil</code> to access the message boards stats user persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		MBStatsUserImpl.class.getName();

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
	 * Returns all the message boards stats users where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching message boards stats users
	 */
	@Override
	public List<MBStatsUser> findByGroupId(long groupId) {
		return findByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards stats users where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBStatsUserModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of message boards stats users
	 * @param end the upper bound of the range of message boards stats users (not inclusive)
	 * @return the range of matching message boards stats users
	 */
	@Override
	public List<MBStatsUser> findByGroupId(long groupId, int start, int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards stats users where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBStatsUserModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of message boards stats users
	 * @param end the upper bound of the range of message boards stats users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards stats users
	 */
	@Override
	public List<MBStatsUser> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<MBStatsUser> orderByComparator) {

		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the message boards stats users where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBStatsUserModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of message boards stats users
	 * @param end the upper bound of the range of message boards stats users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching message boards stats users
	 */
	@Override
	public List<MBStatsUser> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<MBStatsUser> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBStatsUser.class);

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

		List<MBStatsUser> list = null;

		if (useFinderCache && productionMode) {
			list = (List<MBStatsUser>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (MBStatsUser mbStatsUser : list) {
					if (groupId != mbStatsUser.getGroupId()) {
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

			sb.append(_SQL_SELECT_MBSTATSUSER_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(MBStatsUserModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				list = (List<MBStatsUser>)QueryUtil.list(
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
	 * Returns the first message boards stats user in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards stats user
	 * @throws NoSuchStatsUserException if a matching message boards stats user could not be found
	 */
	@Override
	public MBStatsUser findByGroupId_First(
			long groupId, OrderByComparator<MBStatsUser> orderByComparator)
		throws NoSuchStatsUserException {

		MBStatsUser mbStatsUser = fetchByGroupId_First(
			groupId, orderByComparator);

		if (mbStatsUser != null) {
			return mbStatsUser;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchStatsUserException(sb.toString());
	}

	/**
	 * Returns the first message boards stats user in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards stats user, or <code>null</code> if a matching message boards stats user could not be found
	 */
	@Override
	public MBStatsUser fetchByGroupId_First(
		long groupId, OrderByComparator<MBStatsUser> orderByComparator) {

		List<MBStatsUser> list = findByGroupId(
			groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last message boards stats user in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards stats user
	 * @throws NoSuchStatsUserException if a matching message boards stats user could not be found
	 */
	@Override
	public MBStatsUser findByGroupId_Last(
			long groupId, OrderByComparator<MBStatsUser> orderByComparator)
		throws NoSuchStatsUserException {

		MBStatsUser mbStatsUser = fetchByGroupId_Last(
			groupId, orderByComparator);

		if (mbStatsUser != null) {
			return mbStatsUser;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchStatsUserException(sb.toString());
	}

	/**
	 * Returns the last message boards stats user in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards stats user, or <code>null</code> if a matching message boards stats user could not be found
	 */
	@Override
	public MBStatsUser fetchByGroupId_Last(
		long groupId, OrderByComparator<MBStatsUser> orderByComparator) {

		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<MBStatsUser> list = findByGroupId(
			groupId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the message boards stats users before and after the current message boards stats user in the ordered set where groupId = &#63;.
	 *
	 * @param statsUserId the primary key of the current message boards stats user
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards stats user
	 * @throws NoSuchStatsUserException if a message boards stats user with the primary key could not be found
	 */
	@Override
	public MBStatsUser[] findByGroupId_PrevAndNext(
			long statsUserId, long groupId,
			OrderByComparator<MBStatsUser> orderByComparator)
		throws NoSuchStatsUserException {

		MBStatsUser mbStatsUser = findByPrimaryKey(statsUserId);

		Session session = null;

		try {
			session = openSession();

			MBStatsUser[] array = new MBStatsUserImpl[3];

			array[0] = getByGroupId_PrevAndNext(
				session, mbStatsUser, groupId, orderByComparator, true);

			array[1] = mbStatsUser;

			array[2] = getByGroupId_PrevAndNext(
				session, mbStatsUser, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected MBStatsUser getByGroupId_PrevAndNext(
		Session session, MBStatsUser mbStatsUser, long groupId,
		OrderByComparator<MBStatsUser> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_MBSTATSUSER_WHERE);

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
			sb.append(MBStatsUserModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(mbStatsUser)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<MBStatsUser> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the message boards stats users where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (MBStatsUser mbStatsUser :
				findByGroupId(
					groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(mbStatsUser);
		}
	}

	/**
	 * Returns the number of message boards stats users where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching message boards stats users
	 */
	@Override
	public int countByGroupId(long groupId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBStatsUser.class);

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

			sb.append(_SQL_COUNT_MBSTATSUSER_WHERE);

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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 =
		"mbStatsUser.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUserId;
	private FinderPath _finderPathWithoutPaginationFindByUserId;
	private FinderPath _finderPathCountByUserId;

	/**
	 * Returns all the message boards stats users where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching message boards stats users
	 */
	@Override
	public List<MBStatsUser> findByUserId(long userId) {
		return findByUserId(userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards stats users where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBStatsUserModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of message boards stats users
	 * @param end the upper bound of the range of message boards stats users (not inclusive)
	 * @return the range of matching message boards stats users
	 */
	@Override
	public List<MBStatsUser> findByUserId(long userId, int start, int end) {
		return findByUserId(userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards stats users where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBStatsUserModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of message boards stats users
	 * @param end the upper bound of the range of message boards stats users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards stats users
	 */
	@Override
	public List<MBStatsUser> findByUserId(
		long userId, int start, int end,
		OrderByComparator<MBStatsUser> orderByComparator) {

		return findByUserId(userId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the message boards stats users where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBStatsUserModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of message boards stats users
	 * @param end the upper bound of the range of message boards stats users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching message boards stats users
	 */
	@Override
	public List<MBStatsUser> findByUserId(
		long userId, int start, int end,
		OrderByComparator<MBStatsUser> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBStatsUser.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByUserId;
				finderArgs = new Object[] {userId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByUserId;
			finderArgs = new Object[] {userId, start, end, orderByComparator};
		}

		List<MBStatsUser> list = null;

		if (useFinderCache && productionMode) {
			list = (List<MBStatsUser>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (MBStatsUser mbStatsUser : list) {
					if (userId != mbStatsUser.getUserId()) {
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

			sb.append(_SQL_SELECT_MBSTATSUSER_WHERE);

			sb.append(_FINDER_COLUMN_USERID_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(MBStatsUserModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				list = (List<MBStatsUser>)QueryUtil.list(
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
	 * Returns the first message boards stats user in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards stats user
	 * @throws NoSuchStatsUserException if a matching message boards stats user could not be found
	 */
	@Override
	public MBStatsUser findByUserId_First(
			long userId, OrderByComparator<MBStatsUser> orderByComparator)
		throws NoSuchStatsUserException {

		MBStatsUser mbStatsUser = fetchByUserId_First(
			userId, orderByComparator);

		if (mbStatsUser != null) {
			return mbStatsUser;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId=");
		sb.append(userId);

		sb.append("}");

		throw new NoSuchStatsUserException(sb.toString());
	}

	/**
	 * Returns the first message boards stats user in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards stats user, or <code>null</code> if a matching message boards stats user could not be found
	 */
	@Override
	public MBStatsUser fetchByUserId_First(
		long userId, OrderByComparator<MBStatsUser> orderByComparator) {

		List<MBStatsUser> list = findByUserId(userId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last message boards stats user in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards stats user
	 * @throws NoSuchStatsUserException if a matching message boards stats user could not be found
	 */
	@Override
	public MBStatsUser findByUserId_Last(
			long userId, OrderByComparator<MBStatsUser> orderByComparator)
		throws NoSuchStatsUserException {

		MBStatsUser mbStatsUser = fetchByUserId_Last(userId, orderByComparator);

		if (mbStatsUser != null) {
			return mbStatsUser;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId=");
		sb.append(userId);

		sb.append("}");

		throw new NoSuchStatsUserException(sb.toString());
	}

	/**
	 * Returns the last message boards stats user in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards stats user, or <code>null</code> if a matching message boards stats user could not be found
	 */
	@Override
	public MBStatsUser fetchByUserId_Last(
		long userId, OrderByComparator<MBStatsUser> orderByComparator) {

		int count = countByUserId(userId);

		if (count == 0) {
			return null;
		}

		List<MBStatsUser> list = findByUserId(
			userId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the message boards stats users before and after the current message boards stats user in the ordered set where userId = &#63;.
	 *
	 * @param statsUserId the primary key of the current message boards stats user
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards stats user
	 * @throws NoSuchStatsUserException if a message boards stats user with the primary key could not be found
	 */
	@Override
	public MBStatsUser[] findByUserId_PrevAndNext(
			long statsUserId, long userId,
			OrderByComparator<MBStatsUser> orderByComparator)
		throws NoSuchStatsUserException {

		MBStatsUser mbStatsUser = findByPrimaryKey(statsUserId);

		Session session = null;

		try {
			session = openSession();

			MBStatsUser[] array = new MBStatsUserImpl[3];

			array[0] = getByUserId_PrevAndNext(
				session, mbStatsUser, userId, orderByComparator, true);

			array[1] = mbStatsUser;

			array[2] = getByUserId_PrevAndNext(
				session, mbStatsUser, userId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected MBStatsUser getByUserId_PrevAndNext(
		Session session, MBStatsUser mbStatsUser, long userId,
		OrderByComparator<MBStatsUser> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_MBSTATSUSER_WHERE);

		sb.append(_FINDER_COLUMN_USERID_USERID_2);

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
			sb.append(MBStatsUserModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(userId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(mbStatsUser)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<MBStatsUser> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the message boards stats users where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 */
	@Override
	public void removeByUserId(long userId) {
		for (MBStatsUser mbStatsUser :
				findByUserId(
					userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(mbStatsUser);
		}
	}

	/**
	 * Returns the number of message boards stats users where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching message boards stats users
	 */
	@Override
	public int countByUserId(long userId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBStatsUser.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByUserId;

			finderArgs = new Object[] {userId};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_MBSTATSUSER_WHERE);

			sb.append(_FINDER_COLUMN_USERID_USERID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

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

	private static final String _FINDER_COLUMN_USERID_USERID_2 =
		"mbStatsUser.userId = ?";

	private FinderPath _finderPathFetchByG_U;
	private FinderPath _finderPathCountByG_U;

	/**
	 * Returns the message boards stats user where groupId = &#63; and userId = &#63; or throws a <code>NoSuchStatsUserException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the matching message boards stats user
	 * @throws NoSuchStatsUserException if a matching message boards stats user could not be found
	 */
	@Override
	public MBStatsUser findByG_U(long groupId, long userId)
		throws NoSuchStatsUserException {

		MBStatsUser mbStatsUser = fetchByG_U(groupId, userId);

		if (mbStatsUser == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("groupId=");
			sb.append(groupId);

			sb.append(", userId=");
			sb.append(userId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchStatsUserException(sb.toString());
		}

		return mbStatsUser;
	}

	/**
	 * Returns the message boards stats user where groupId = &#63; and userId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the matching message boards stats user, or <code>null</code> if a matching message boards stats user could not be found
	 */
	@Override
	public MBStatsUser fetchByG_U(long groupId, long userId) {
		return fetchByG_U(groupId, userId, true);
	}

	/**
	 * Returns the message boards stats user where groupId = &#63; and userId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching message boards stats user, or <code>null</code> if a matching message boards stats user could not be found
	 */
	@Override
	public MBStatsUser fetchByG_U(
		long groupId, long userId, boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBStatsUser.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {groupId, userId};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(
				_finderPathFetchByG_U, finderArgs, this);
		}

		if (result instanceof MBStatsUser) {
			MBStatsUser mbStatsUser = (MBStatsUser)result;

			if ((groupId != mbStatsUser.getGroupId()) ||
				(userId != mbStatsUser.getUserId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_MBSTATSUSER_WHERE);

			sb.append(_FINDER_COLUMN_G_U_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_U_USERID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(userId);

				List<MBStatsUser> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByG_U, finderArgs, list);
					}
				}
				else {
					MBStatsUser mbStatsUser = list.get(0);

					result = mbStatsUser;

					cacheResult(mbStatsUser);
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
			return (MBStatsUser)result;
		}
	}

	/**
	 * Removes the message boards stats user where groupId = &#63; and userId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the message boards stats user that was removed
	 */
	@Override
	public MBStatsUser removeByG_U(long groupId, long userId)
		throws NoSuchStatsUserException {

		MBStatsUser mbStatsUser = findByG_U(groupId, userId);

		return remove(mbStatsUser);
	}

	/**
	 * Returns the number of message boards stats users where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the number of matching message boards stats users
	 */
	@Override
	public int countByG_U(long groupId, long userId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBStatsUser.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_U;

			finderArgs = new Object[] {groupId, userId};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_MBSTATSUSER_WHERE);

			sb.append(_FINDER_COLUMN_G_U_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_U_USERID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(userId);

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

	private static final String _FINDER_COLUMN_G_U_GROUPID_2 =
		"mbStatsUser.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_U_USERID_2 =
		"mbStatsUser.userId = ?";

	private FinderPath _finderPathWithPaginationFindByG_NotU_NotM;
	private FinderPath _finderPathWithPaginationCountByG_NotU_NotM;

	/**
	 * Returns all the message boards stats users where groupId = &#63; and userId &ne; &#63; and messageCount &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param messageCount the message count
	 * @return the matching message boards stats users
	 */
	@Override
	public List<MBStatsUser> findByG_NotU_NotM(
		long groupId, long userId, int messageCount) {

		return findByG_NotU_NotM(
			groupId, userId, messageCount, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the message boards stats users where groupId = &#63; and userId &ne; &#63; and messageCount &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBStatsUserModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param messageCount the message count
	 * @param start the lower bound of the range of message boards stats users
	 * @param end the upper bound of the range of message boards stats users (not inclusive)
	 * @return the range of matching message boards stats users
	 */
	@Override
	public List<MBStatsUser> findByG_NotU_NotM(
		long groupId, long userId, int messageCount, int start, int end) {

		return findByG_NotU_NotM(
			groupId, userId, messageCount, start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards stats users where groupId = &#63; and userId &ne; &#63; and messageCount &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBStatsUserModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param messageCount the message count
	 * @param start the lower bound of the range of message boards stats users
	 * @param end the upper bound of the range of message boards stats users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching message boards stats users
	 */
	@Override
	public List<MBStatsUser> findByG_NotU_NotM(
		long groupId, long userId, int messageCount, int start, int end,
		OrderByComparator<MBStatsUser> orderByComparator) {

		return findByG_NotU_NotM(
			groupId, userId, messageCount, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the message boards stats users where groupId = &#63; and userId &ne; &#63; and messageCount &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBStatsUserModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param messageCount the message count
	 * @param start the lower bound of the range of message boards stats users
	 * @param end the upper bound of the range of message boards stats users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching message boards stats users
	 */
	@Override
	public List<MBStatsUser> findByG_NotU_NotM(
		long groupId, long userId, int messageCount, int start, int end,
		OrderByComparator<MBStatsUser> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBStatsUser.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByG_NotU_NotM;
		finderArgs = new Object[] {
			groupId, userId, messageCount, start, end, orderByComparator
		};

		List<MBStatsUser> list = null;

		if (useFinderCache && productionMode) {
			list = (List<MBStatsUser>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (MBStatsUser mbStatsUser : list) {
					if ((groupId != mbStatsUser.getGroupId()) ||
						(userId == mbStatsUser.getUserId()) ||
						(messageCount == mbStatsUser.getMessageCount())) {

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

			sb.append(_SQL_SELECT_MBSTATSUSER_WHERE);

			sb.append(_FINDER_COLUMN_G_NOTU_NOTM_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_NOTU_NOTM_USERID_2);

			sb.append(_FINDER_COLUMN_G_NOTU_NOTM_MESSAGECOUNT_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(MBStatsUserModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(userId);

				queryPos.add(messageCount);

				list = (List<MBStatsUser>)QueryUtil.list(
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
	 * Returns the first message boards stats user in the ordered set where groupId = &#63; and userId &ne; &#63; and messageCount &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param messageCount the message count
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards stats user
	 * @throws NoSuchStatsUserException if a matching message boards stats user could not be found
	 */
	@Override
	public MBStatsUser findByG_NotU_NotM_First(
			long groupId, long userId, int messageCount,
			OrderByComparator<MBStatsUser> orderByComparator)
		throws NoSuchStatsUserException {

		MBStatsUser mbStatsUser = fetchByG_NotU_NotM_First(
			groupId, userId, messageCount, orderByComparator);

		if (mbStatsUser != null) {
			return mbStatsUser;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", userId!=");
		sb.append(userId);

		sb.append(", messageCount!=");
		sb.append(messageCount);

		sb.append("}");

		throw new NoSuchStatsUserException(sb.toString());
	}

	/**
	 * Returns the first message boards stats user in the ordered set where groupId = &#63; and userId &ne; &#63; and messageCount &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param messageCount the message count
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching message boards stats user, or <code>null</code> if a matching message boards stats user could not be found
	 */
	@Override
	public MBStatsUser fetchByG_NotU_NotM_First(
		long groupId, long userId, int messageCount,
		OrderByComparator<MBStatsUser> orderByComparator) {

		List<MBStatsUser> list = findByG_NotU_NotM(
			groupId, userId, messageCount, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last message boards stats user in the ordered set where groupId = &#63; and userId &ne; &#63; and messageCount &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param messageCount the message count
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards stats user
	 * @throws NoSuchStatsUserException if a matching message boards stats user could not be found
	 */
	@Override
	public MBStatsUser findByG_NotU_NotM_Last(
			long groupId, long userId, int messageCount,
			OrderByComparator<MBStatsUser> orderByComparator)
		throws NoSuchStatsUserException {

		MBStatsUser mbStatsUser = fetchByG_NotU_NotM_Last(
			groupId, userId, messageCount, orderByComparator);

		if (mbStatsUser != null) {
			return mbStatsUser;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", userId!=");
		sb.append(userId);

		sb.append(", messageCount!=");
		sb.append(messageCount);

		sb.append("}");

		throw new NoSuchStatsUserException(sb.toString());
	}

	/**
	 * Returns the last message boards stats user in the ordered set where groupId = &#63; and userId &ne; &#63; and messageCount &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param messageCount the message count
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching message boards stats user, or <code>null</code> if a matching message boards stats user could not be found
	 */
	@Override
	public MBStatsUser fetchByG_NotU_NotM_Last(
		long groupId, long userId, int messageCount,
		OrderByComparator<MBStatsUser> orderByComparator) {

		int count = countByG_NotU_NotM(groupId, userId, messageCount);

		if (count == 0) {
			return null;
		}

		List<MBStatsUser> list = findByG_NotU_NotM(
			groupId, userId, messageCount, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the message boards stats users before and after the current message boards stats user in the ordered set where groupId = &#63; and userId &ne; &#63; and messageCount &ne; &#63;.
	 *
	 * @param statsUserId the primary key of the current message boards stats user
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param messageCount the message count
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next message boards stats user
	 * @throws NoSuchStatsUserException if a message boards stats user with the primary key could not be found
	 */
	@Override
	public MBStatsUser[] findByG_NotU_NotM_PrevAndNext(
			long statsUserId, long groupId, long userId, int messageCount,
			OrderByComparator<MBStatsUser> orderByComparator)
		throws NoSuchStatsUserException {

		MBStatsUser mbStatsUser = findByPrimaryKey(statsUserId);

		Session session = null;

		try {
			session = openSession();

			MBStatsUser[] array = new MBStatsUserImpl[3];

			array[0] = getByG_NotU_NotM_PrevAndNext(
				session, mbStatsUser, groupId, userId, messageCount,
				orderByComparator, true);

			array[1] = mbStatsUser;

			array[2] = getByG_NotU_NotM_PrevAndNext(
				session, mbStatsUser, groupId, userId, messageCount,
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

	protected MBStatsUser getByG_NotU_NotM_PrevAndNext(
		Session session, MBStatsUser mbStatsUser, long groupId, long userId,
		int messageCount, OrderByComparator<MBStatsUser> orderByComparator,
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

		sb.append(_SQL_SELECT_MBSTATSUSER_WHERE);

		sb.append(_FINDER_COLUMN_G_NOTU_NOTM_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_NOTU_NOTM_USERID_2);

		sb.append(_FINDER_COLUMN_G_NOTU_NOTM_MESSAGECOUNT_2);

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
			sb.append(MBStatsUserModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(userId);

		queryPos.add(messageCount);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(mbStatsUser)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<MBStatsUser> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the message boards stats users where groupId = &#63; and userId &ne; &#63; and messageCount &ne; &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param messageCount the message count
	 */
	@Override
	public void removeByG_NotU_NotM(
		long groupId, long userId, int messageCount) {

		for (MBStatsUser mbStatsUser :
				findByG_NotU_NotM(
					groupId, userId, messageCount, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(mbStatsUser);
		}
	}

	/**
	 * Returns the number of message boards stats users where groupId = &#63; and userId &ne; &#63; and messageCount &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param messageCount the message count
	 * @return the number of matching message boards stats users
	 */
	@Override
	public int countByG_NotU_NotM(long groupId, long userId, int messageCount) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBStatsUser.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathWithPaginationCountByG_NotU_NotM;

			finderArgs = new Object[] {groupId, userId, messageCount};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_MBSTATSUSER_WHERE);

			sb.append(_FINDER_COLUMN_G_NOTU_NOTM_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_NOTU_NOTM_USERID_2);

			sb.append(_FINDER_COLUMN_G_NOTU_NOTM_MESSAGECOUNT_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(userId);

				queryPos.add(messageCount);

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

	private static final String _FINDER_COLUMN_G_NOTU_NOTM_GROUPID_2 =
		"mbStatsUser.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_NOTU_NOTM_USERID_2 =
		"mbStatsUser.userId != ? AND ";

	private static final String _FINDER_COLUMN_G_NOTU_NOTM_MESSAGECOUNT_2 =
		"mbStatsUser.messageCount != ?";

	public MBStatsUserPersistenceImpl() {
		setModelClass(MBStatsUser.class);

		setModelImplClass(MBStatsUserImpl.class);
		setModelPKClass(long.class);

		setTable(MBStatsUserTable.INSTANCE);
	}

	/**
	 * Caches the message boards stats user in the entity cache if it is enabled.
	 *
	 * @param mbStatsUser the message boards stats user
	 */
	@Override
	public void cacheResult(MBStatsUser mbStatsUser) {
		if (mbStatsUser.getCtCollectionId() != 0) {
			mbStatsUser.resetOriginalValues();

			return;
		}

		entityCache.putResult(
			MBStatsUserImpl.class, mbStatsUser.getPrimaryKey(), mbStatsUser);

		finderCache.putResult(
			_finderPathFetchByG_U,
			new Object[] {mbStatsUser.getGroupId(), mbStatsUser.getUserId()},
			mbStatsUser);

		mbStatsUser.resetOriginalValues();
	}

	/**
	 * Caches the message boards stats users in the entity cache if it is enabled.
	 *
	 * @param mbStatsUsers the message boards stats users
	 */
	@Override
	public void cacheResult(List<MBStatsUser> mbStatsUsers) {
		for (MBStatsUser mbStatsUser : mbStatsUsers) {
			if (mbStatsUser.getCtCollectionId() != 0) {
				mbStatsUser.resetOriginalValues();

				continue;
			}

			if (entityCache.getResult(
					MBStatsUserImpl.class, mbStatsUser.getPrimaryKey()) ==
						null) {

				cacheResult(mbStatsUser);
			}
			else {
				mbStatsUser.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all message boards stats users.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(MBStatsUserImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the message boards stats user.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(MBStatsUser mbStatsUser) {
		entityCache.removeResult(
			MBStatsUserImpl.class, mbStatsUser.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((MBStatsUserModelImpl)mbStatsUser, true);
	}

	@Override
	public void clearCache(List<MBStatsUser> mbStatsUsers) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (MBStatsUser mbStatsUser : mbStatsUsers) {
			entityCache.removeResult(
				MBStatsUserImpl.class, mbStatsUser.getPrimaryKey());

			clearUniqueFindersCache((MBStatsUserModelImpl)mbStatsUser, true);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(MBStatsUserImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		MBStatsUserModelImpl mbStatsUserModelImpl) {

		Object[] args = new Object[] {
			mbStatsUserModelImpl.getGroupId(), mbStatsUserModelImpl.getUserId()
		};

		finderCache.putResult(
			_finderPathCountByG_U, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByG_U, args, mbStatsUserModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		MBStatsUserModelImpl mbStatsUserModelImpl, boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				mbStatsUserModelImpl.getGroupId(),
				mbStatsUserModelImpl.getUserId()
			};

			finderCache.removeResult(_finderPathCountByG_U, args);
			finderCache.removeResult(_finderPathFetchByG_U, args);
		}

		if ((mbStatsUserModelImpl.getColumnBitmask() &
			 _finderPathFetchByG_U.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				mbStatsUserModelImpl.getOriginalGroupId(),
				mbStatsUserModelImpl.getOriginalUserId()
			};

			finderCache.removeResult(_finderPathCountByG_U, args);
			finderCache.removeResult(_finderPathFetchByG_U, args);
		}
	}

	/**
	 * Creates a new message boards stats user with the primary key. Does not add the message boards stats user to the database.
	 *
	 * @param statsUserId the primary key for the new message boards stats user
	 * @return the new message boards stats user
	 */
	@Override
	public MBStatsUser create(long statsUserId) {
		MBStatsUser mbStatsUser = new MBStatsUserImpl();

		mbStatsUser.setNew(true);
		mbStatsUser.setPrimaryKey(statsUserId);

		mbStatsUser.setCompanyId(CompanyThreadLocal.getCompanyId());

		return mbStatsUser;
	}

	/**
	 * Removes the message boards stats user with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param statsUserId the primary key of the message boards stats user
	 * @return the message boards stats user that was removed
	 * @throws NoSuchStatsUserException if a message boards stats user with the primary key could not be found
	 */
	@Override
	public MBStatsUser remove(long statsUserId)
		throws NoSuchStatsUserException {

		return remove((Serializable)statsUserId);
	}

	/**
	 * Removes the message boards stats user with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the message boards stats user
	 * @return the message boards stats user that was removed
	 * @throws NoSuchStatsUserException if a message boards stats user with the primary key could not be found
	 */
	@Override
	public MBStatsUser remove(Serializable primaryKey)
		throws NoSuchStatsUserException {

		Session session = null;

		try {
			session = openSession();

			MBStatsUser mbStatsUser = (MBStatsUser)session.get(
				MBStatsUserImpl.class, primaryKey);

			if (mbStatsUser == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchStatsUserException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(mbStatsUser);
		}
		catch (NoSuchStatsUserException noSuchEntityException) {
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
	protected MBStatsUser removeImpl(MBStatsUser mbStatsUser) {
		if (!ctPersistenceHelper.isRemove(mbStatsUser)) {
			return mbStatsUser;
		}

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(mbStatsUser)) {
				mbStatsUser = (MBStatsUser)session.get(
					MBStatsUserImpl.class, mbStatsUser.getPrimaryKeyObj());
			}

			if (mbStatsUser != null) {
				session.delete(mbStatsUser);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (mbStatsUser != null) {
			clearCache(mbStatsUser);
		}

		return mbStatsUser;
	}

	@Override
	public MBStatsUser updateImpl(MBStatsUser mbStatsUser) {
		boolean isNew = mbStatsUser.isNew();

		if (!(mbStatsUser instanceof MBStatsUserModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(mbStatsUser.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(mbStatsUser);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in mbStatsUser proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom MBStatsUser implementation " +
					mbStatsUser.getClass());
		}

		MBStatsUserModelImpl mbStatsUserModelImpl =
			(MBStatsUserModelImpl)mbStatsUser;

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(mbStatsUser)) {
				if (!isNew) {
					session.evict(
						MBStatsUserImpl.class, mbStatsUser.getPrimaryKeyObj());
				}

				session.save(mbStatsUser);

				mbStatsUser.setNew(false);
			}
			else {
				mbStatsUser = (MBStatsUser)session.merge(mbStatsUser);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (mbStatsUser.getCtCollectionId() != 0) {
			mbStatsUser.resetOriginalValues();

			return mbStatsUser;
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew) {
			Object[] args = new Object[] {mbStatsUserModelImpl.getGroupId()};

			finderCache.removeResult(_finderPathCountByGroupId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByGroupId, args);

			args = new Object[] {mbStatsUserModelImpl.getUserId()};

			finderCache.removeResult(_finderPathCountByUserId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUserId, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((mbStatsUserModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByGroupId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					mbStatsUserModelImpl.getOriginalGroupId()
				};

				finderCache.removeResult(_finderPathCountByGroupId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByGroupId, args);

				args = new Object[] {mbStatsUserModelImpl.getGroupId()};

				finderCache.removeResult(_finderPathCountByGroupId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByGroupId, args);
			}

			if ((mbStatsUserModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUserId.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					mbStatsUserModelImpl.getOriginalUserId()
				};

				finderCache.removeResult(_finderPathCountByUserId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUserId, args);

				args = new Object[] {mbStatsUserModelImpl.getUserId()};

				finderCache.removeResult(_finderPathCountByUserId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUserId, args);
			}
		}

		entityCache.putResult(
			MBStatsUserImpl.class, mbStatsUser.getPrimaryKey(), mbStatsUser,
			false);

		clearUniqueFindersCache(mbStatsUserModelImpl, false);
		cacheUniqueFindersCache(mbStatsUserModelImpl);

		mbStatsUser.resetOriginalValues();

		return mbStatsUser;
	}

	/**
	 * Returns the message boards stats user with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the message boards stats user
	 * @return the message boards stats user
	 * @throws NoSuchStatsUserException if a message boards stats user with the primary key could not be found
	 */
	@Override
	public MBStatsUser findByPrimaryKey(Serializable primaryKey)
		throws NoSuchStatsUserException {

		MBStatsUser mbStatsUser = fetchByPrimaryKey(primaryKey);

		if (mbStatsUser == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchStatsUserException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return mbStatsUser;
	}

	/**
	 * Returns the message boards stats user with the primary key or throws a <code>NoSuchStatsUserException</code> if it could not be found.
	 *
	 * @param statsUserId the primary key of the message boards stats user
	 * @return the message boards stats user
	 * @throws NoSuchStatsUserException if a message boards stats user with the primary key could not be found
	 */
	@Override
	public MBStatsUser findByPrimaryKey(long statsUserId)
		throws NoSuchStatsUserException {

		return findByPrimaryKey((Serializable)statsUserId);
	}

	/**
	 * Returns the message boards stats user with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the message boards stats user
	 * @return the message boards stats user, or <code>null</code> if a message boards stats user with the primary key could not be found
	 */
	@Override
	public MBStatsUser fetchByPrimaryKey(Serializable primaryKey) {
		if (ctPersistenceHelper.isProductionMode(MBStatsUser.class)) {
			return super.fetchByPrimaryKey(primaryKey);
		}

		MBStatsUser mbStatsUser = null;

		Session session = null;

		try {
			session = openSession();

			mbStatsUser = (MBStatsUser)session.get(
				MBStatsUserImpl.class, primaryKey);

			if (mbStatsUser != null) {
				cacheResult(mbStatsUser);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return mbStatsUser;
	}

	/**
	 * Returns the message boards stats user with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param statsUserId the primary key of the message boards stats user
	 * @return the message boards stats user, or <code>null</code> if a message boards stats user with the primary key could not be found
	 */
	@Override
	public MBStatsUser fetchByPrimaryKey(long statsUserId) {
		return fetchByPrimaryKey((Serializable)statsUserId);
	}

	@Override
	public Map<Serializable, MBStatsUser> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (ctPersistenceHelper.isProductionMode(MBStatsUser.class)) {
			return super.fetchByPrimaryKeys(primaryKeys);
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, MBStatsUser> map =
			new HashMap<Serializable, MBStatsUser>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			MBStatsUser mbStatsUser = fetchByPrimaryKey(primaryKey);

			if (mbStatsUser != null) {
				map.put(primaryKey, mbStatsUser);
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

			for (MBStatsUser mbStatsUser : (List<MBStatsUser>)query.list()) {
				map.put(mbStatsUser.getPrimaryKeyObj(), mbStatsUser);

				cacheResult(mbStatsUser);
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
	 * Returns all the message boards stats users.
	 *
	 * @return the message boards stats users
	 */
	@Override
	public List<MBStatsUser> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the message boards stats users.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBStatsUserModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of message boards stats users
	 * @param end the upper bound of the range of message boards stats users (not inclusive)
	 * @return the range of message boards stats users
	 */
	@Override
	public List<MBStatsUser> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the message boards stats users.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBStatsUserModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of message boards stats users
	 * @param end the upper bound of the range of message boards stats users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of message boards stats users
	 */
	@Override
	public List<MBStatsUser> findAll(
		int start, int end, OrderByComparator<MBStatsUser> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the message boards stats users.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MBStatsUserModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of message boards stats users
	 * @param end the upper bound of the range of message boards stats users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of message boards stats users
	 */
	@Override
	public List<MBStatsUser> findAll(
		int start, int end, OrderByComparator<MBStatsUser> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBStatsUser.class);

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

		List<MBStatsUser> list = null;

		if (useFinderCache && productionMode) {
			list = (List<MBStatsUser>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_MBSTATSUSER);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_MBSTATSUSER;

				sql = sql.concat(MBStatsUserModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<MBStatsUser>)QueryUtil.list(
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
	 * Removes all the message boards stats users from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (MBStatsUser mbStatsUser : findAll()) {
			remove(mbStatsUser);
		}
	}

	/**
	 * Returns the number of message boards stats users.
	 *
	 * @return the number of message boards stats users
	 */
	@Override
	public int countAll() {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			MBStatsUser.class);

		Long count = null;

		if (productionMode) {
			count = (Long)finderCache.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY, this);
		}

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_MBSTATSUSER);

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
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "statsUserId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_MBSTATSUSER;
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
		return MBStatsUserModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "MBStatsUser";
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
		ctStrictColumnNames.add("messageCount");
		ctStrictColumnNames.add("lastPostDate");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK, Collections.singleton("statsUserId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(new String[] {"groupId", "userId"});
	}

	/**
	 * Initializes the message boards stats user persistence.
	 */
	@Activate
	public void activate() {
		_finderPathWithPaginationFindAll = new FinderPath(
			MBStatsUserImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			MBStatsUserImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findAll", new String[0]);

		_finderPathCountAll = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByGroupId = new FinderPath(
			MBStatsUserImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByGroupId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByGroupId = new FinderPath(
			MBStatsUserImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByGroupId", new String[] {Long.class.getName()},
			MBStatsUserModelImpl.GROUPID_COLUMN_BITMASK |
			MBStatsUserModelImpl.MESSAGECOUNT_COLUMN_BITMASK);

		_finderPathCountByGroupId = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByGroupId", new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByUserId = new FinderPath(
			MBStatsUserImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUserId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUserId = new FinderPath(
			MBStatsUserImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByUserId", new String[] {Long.class.getName()},
			MBStatsUserModelImpl.USERID_COLUMN_BITMASK |
			MBStatsUserModelImpl.MESSAGECOUNT_COLUMN_BITMASK);

		_finderPathCountByUserId = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUserId", new String[] {Long.class.getName()});

		_finderPathFetchByG_U = new FinderPath(
			MBStatsUserImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByG_U",
			new String[] {Long.class.getName(), Long.class.getName()},
			MBStatsUserModelImpl.GROUPID_COLUMN_BITMASK |
			MBStatsUserModelImpl.USERID_COLUMN_BITMASK);

		_finderPathCountByG_U = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_U",
			new String[] {Long.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByG_NotU_NotM = new FinderPath(
			MBStatsUserImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_NotU_NotM",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithPaginationCountByG_NotU_NotM = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"countByG_NotU_NotM",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(MBStatsUserImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	@Reference(
		target = MBPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = MBPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = MBPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
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

	private static final String _SQL_SELECT_MBSTATSUSER =
		"SELECT mbStatsUser FROM MBStatsUser mbStatsUser";

	private static final String _SQL_SELECT_MBSTATSUSER_WHERE =
		"SELECT mbStatsUser FROM MBStatsUser mbStatsUser WHERE ";

	private static final String _SQL_COUNT_MBSTATSUSER =
		"SELECT COUNT(mbStatsUser) FROM MBStatsUser mbStatsUser";

	private static final String _SQL_COUNT_MBSTATSUSER_WHERE =
		"SELECT COUNT(mbStatsUser) FROM MBStatsUser mbStatsUser WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "mbStatsUser.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No MBStatsUser exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No MBStatsUser exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		MBStatsUserPersistenceImpl.class);

	static {
		try {
			Class.forName(MBPersistenceConstants.class.getName());
		}
		catch (ClassNotFoundException classNotFoundException) {
			throw new ExceptionInInitializerError(classNotFoundException);
		}
	}

}