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

package com.liferay.segments.service.persistence.impl;

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
import com.liferay.segments.exception.NoSuchExperimentException;
import com.liferay.segments.model.SegmentsExperiment;
import com.liferay.segments.model.SegmentsExperimentTable;
import com.liferay.segments.model.impl.SegmentsExperimentImpl;
import com.liferay.segments.model.impl.SegmentsExperimentModelImpl;
import com.liferay.segments.service.persistence.SegmentsExperimentPersistence;
import com.liferay.segments.service.persistence.impl.constants.SegmentsPersistenceConstants;

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
 * The persistence implementation for the segments experiment service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Eduardo Garcia
 * @generated
 */
@Component(service = SegmentsExperimentPersistence.class)
public class SegmentsExperimentPersistenceImpl
	extends BasePersistenceImpl<SegmentsExperiment>
	implements SegmentsExperimentPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>SegmentsExperimentUtil</code> to access the segments experiment persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		SegmentsExperimentImpl.class.getName();

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
	 * Returns all the segments experiments where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching segments experiments
	 */
	@Override
	public List<SegmentsExperiment> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the segments experiments where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperimentModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of segments experiments
	 * @param end the upper bound of the range of segments experiments (not inclusive)
	 * @return the range of matching segments experiments
	 */
	@Override
	public List<SegmentsExperiment> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the segments experiments where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperimentModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of segments experiments
	 * @param end the upper bound of the range of segments experiments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments experiments
	 */
	@Override
	public List<SegmentsExperiment> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<SegmentsExperiment> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the segments experiments where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperimentModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of segments experiments
	 * @param end the upper bound of the range of segments experiments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching segments experiments
	 */
	@Override
	public List<SegmentsExperiment> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<SegmentsExperiment> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			SegmentsExperiment.class);

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

		List<SegmentsExperiment> list = null;

		if (useFinderCache && productionMode) {
			list = (List<SegmentsExperiment>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SegmentsExperiment segmentsExperiment : list) {
					if (!uuid.equals(segmentsExperiment.getUuid())) {
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

			sb.append(_SQL_SELECT_SEGMENTSEXPERIMENT_WHERE);

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
				sb.append(SegmentsExperimentModelImpl.ORDER_BY_JPQL);
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

				list = (List<SegmentsExperiment>)QueryUtil.list(
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
	 * Returns the first segments experiment in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments experiment
	 * @throws NoSuchExperimentException if a matching segments experiment could not be found
	 */
	@Override
	public SegmentsExperiment findByUuid_First(
			String uuid,
			OrderByComparator<SegmentsExperiment> orderByComparator)
		throws NoSuchExperimentException {

		SegmentsExperiment segmentsExperiment = fetchByUuid_First(
			uuid, orderByComparator);

		if (segmentsExperiment != null) {
			return segmentsExperiment;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchExperimentException(sb.toString());
	}

	/**
	 * Returns the first segments experiment in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments experiment, or <code>null</code> if a matching segments experiment could not be found
	 */
	@Override
	public SegmentsExperiment fetchByUuid_First(
		String uuid, OrderByComparator<SegmentsExperiment> orderByComparator) {

		List<SegmentsExperiment> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last segments experiment in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments experiment
	 * @throws NoSuchExperimentException if a matching segments experiment could not be found
	 */
	@Override
	public SegmentsExperiment findByUuid_Last(
			String uuid,
			OrderByComparator<SegmentsExperiment> orderByComparator)
		throws NoSuchExperimentException {

		SegmentsExperiment segmentsExperiment = fetchByUuid_Last(
			uuid, orderByComparator);

		if (segmentsExperiment != null) {
			return segmentsExperiment;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchExperimentException(sb.toString());
	}

	/**
	 * Returns the last segments experiment in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments experiment, or <code>null</code> if a matching segments experiment could not be found
	 */
	@Override
	public SegmentsExperiment fetchByUuid_Last(
		String uuid, OrderByComparator<SegmentsExperiment> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<SegmentsExperiment> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the segments experiments before and after the current segments experiment in the ordered set where uuid = &#63;.
	 *
	 * @param segmentsExperimentId the primary key of the current segments experiment
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next segments experiment
	 * @throws NoSuchExperimentException if a segments experiment with the primary key could not be found
	 */
	@Override
	public SegmentsExperiment[] findByUuid_PrevAndNext(
			long segmentsExperimentId, String uuid,
			OrderByComparator<SegmentsExperiment> orderByComparator)
		throws NoSuchExperimentException {

		uuid = Objects.toString(uuid, "");

		SegmentsExperiment segmentsExperiment = findByPrimaryKey(
			segmentsExperimentId);

		Session session = null;

		try {
			session = openSession();

			SegmentsExperiment[] array = new SegmentsExperimentImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, segmentsExperiment, uuid, orderByComparator, true);

			array[1] = segmentsExperiment;

			array[2] = getByUuid_PrevAndNext(
				session, segmentsExperiment, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected SegmentsExperiment getByUuid_PrevAndNext(
		Session session, SegmentsExperiment segmentsExperiment, String uuid,
		OrderByComparator<SegmentsExperiment> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_SEGMENTSEXPERIMENT_WHERE);

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
			sb.append(SegmentsExperimentModelImpl.ORDER_BY_JPQL);
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
						segmentsExperiment)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SegmentsExperiment> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the segments experiments where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (SegmentsExperiment segmentsExperiment :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(segmentsExperiment);
		}
	}

	/**
	 * Returns the number of segments experiments where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching segments experiments
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			SegmentsExperiment.class);

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

			sb.append(_SQL_COUNT_SEGMENTSEXPERIMENT_WHERE);

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
		"segmentsExperiment.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(segmentsExperiment.uuid IS NULL OR segmentsExperiment.uuid = '')";

	private FinderPath _finderPathFetchByUUID_G;
	private FinderPath _finderPathCountByUUID_G;

	/**
	 * Returns the segments experiment where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchExperimentException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching segments experiment
	 * @throws NoSuchExperimentException if a matching segments experiment could not be found
	 */
	@Override
	public SegmentsExperiment findByUUID_G(String uuid, long groupId)
		throws NoSuchExperimentException {

		SegmentsExperiment segmentsExperiment = fetchByUUID_G(uuid, groupId);

		if (segmentsExperiment == null) {
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

			throw new NoSuchExperimentException(sb.toString());
		}

		return segmentsExperiment;
	}

	/**
	 * Returns the segments experiment where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching segments experiment, or <code>null</code> if a matching segments experiment could not be found
	 */
	@Override
	public SegmentsExperiment fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the segments experiment where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching segments experiment, or <code>null</code> if a matching segments experiment could not be found
	 */
	@Override
	public SegmentsExperiment fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			SegmentsExperiment.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {uuid, groupId};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(
				_finderPathFetchByUUID_G, finderArgs, this);
		}

		if (result instanceof SegmentsExperiment) {
			SegmentsExperiment segmentsExperiment = (SegmentsExperiment)result;

			if (!Objects.equals(uuid, segmentsExperiment.getUuid()) ||
				(groupId != segmentsExperiment.getGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_SEGMENTSEXPERIMENT_WHERE);

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

				List<SegmentsExperiment> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByUUID_G, finderArgs, list);
					}
				}
				else {
					SegmentsExperiment segmentsExperiment = list.get(0);

					result = segmentsExperiment;

					cacheResult(segmentsExperiment);
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
			return (SegmentsExperiment)result;
		}
	}

	/**
	 * Removes the segments experiment where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the segments experiment that was removed
	 */
	@Override
	public SegmentsExperiment removeByUUID_G(String uuid, long groupId)
		throws NoSuchExperimentException {

		SegmentsExperiment segmentsExperiment = findByUUID_G(uuid, groupId);

		return remove(segmentsExperiment);
	}

	/**
	 * Returns the number of segments experiments where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching segments experiments
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			SegmentsExperiment.class);

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

			sb.append(_SQL_COUNT_SEGMENTSEXPERIMENT_WHERE);

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
		"segmentsExperiment.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(segmentsExperiment.uuid IS NULL OR segmentsExperiment.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"segmentsExperiment.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the segments experiments where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching segments experiments
	 */
	@Override
	public List<SegmentsExperiment> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the segments experiments where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperimentModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of segments experiments
	 * @param end the upper bound of the range of segments experiments (not inclusive)
	 * @return the range of matching segments experiments
	 */
	@Override
	public List<SegmentsExperiment> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the segments experiments where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperimentModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of segments experiments
	 * @param end the upper bound of the range of segments experiments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments experiments
	 */
	@Override
	public List<SegmentsExperiment> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<SegmentsExperiment> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the segments experiments where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperimentModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of segments experiments
	 * @param end the upper bound of the range of segments experiments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching segments experiments
	 */
	@Override
	public List<SegmentsExperiment> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<SegmentsExperiment> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			SegmentsExperiment.class);

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

		List<SegmentsExperiment> list = null;

		if (useFinderCache && productionMode) {
			list = (List<SegmentsExperiment>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SegmentsExperiment segmentsExperiment : list) {
					if (!uuid.equals(segmentsExperiment.getUuid()) ||
						(companyId != segmentsExperiment.getCompanyId())) {

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

			sb.append(_SQL_SELECT_SEGMENTSEXPERIMENT_WHERE);

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
				sb.append(SegmentsExperimentModelImpl.ORDER_BY_JPQL);
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

				list = (List<SegmentsExperiment>)QueryUtil.list(
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
	 * Returns the first segments experiment in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments experiment
	 * @throws NoSuchExperimentException if a matching segments experiment could not be found
	 */
	@Override
	public SegmentsExperiment findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<SegmentsExperiment> orderByComparator)
		throws NoSuchExperimentException {

		SegmentsExperiment segmentsExperiment = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (segmentsExperiment != null) {
			return segmentsExperiment;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchExperimentException(sb.toString());
	}

	/**
	 * Returns the first segments experiment in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments experiment, or <code>null</code> if a matching segments experiment could not be found
	 */
	@Override
	public SegmentsExperiment fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<SegmentsExperiment> orderByComparator) {

		List<SegmentsExperiment> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last segments experiment in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments experiment
	 * @throws NoSuchExperimentException if a matching segments experiment could not be found
	 */
	@Override
	public SegmentsExperiment findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<SegmentsExperiment> orderByComparator)
		throws NoSuchExperimentException {

		SegmentsExperiment segmentsExperiment = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (segmentsExperiment != null) {
			return segmentsExperiment;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchExperimentException(sb.toString());
	}

	/**
	 * Returns the last segments experiment in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments experiment, or <code>null</code> if a matching segments experiment could not be found
	 */
	@Override
	public SegmentsExperiment fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<SegmentsExperiment> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<SegmentsExperiment> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the segments experiments before and after the current segments experiment in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param segmentsExperimentId the primary key of the current segments experiment
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next segments experiment
	 * @throws NoSuchExperimentException if a segments experiment with the primary key could not be found
	 */
	@Override
	public SegmentsExperiment[] findByUuid_C_PrevAndNext(
			long segmentsExperimentId, String uuid, long companyId,
			OrderByComparator<SegmentsExperiment> orderByComparator)
		throws NoSuchExperimentException {

		uuid = Objects.toString(uuid, "");

		SegmentsExperiment segmentsExperiment = findByPrimaryKey(
			segmentsExperimentId);

		Session session = null;

		try {
			session = openSession();

			SegmentsExperiment[] array = new SegmentsExperimentImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, segmentsExperiment, uuid, companyId, orderByComparator,
				true);

			array[1] = segmentsExperiment;

			array[2] = getByUuid_C_PrevAndNext(
				session, segmentsExperiment, uuid, companyId, orderByComparator,
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

	protected SegmentsExperiment getByUuid_C_PrevAndNext(
		Session session, SegmentsExperiment segmentsExperiment, String uuid,
		long companyId, OrderByComparator<SegmentsExperiment> orderByComparator,
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

		sb.append(_SQL_SELECT_SEGMENTSEXPERIMENT_WHERE);

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
			sb.append(SegmentsExperimentModelImpl.ORDER_BY_JPQL);
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
						segmentsExperiment)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SegmentsExperiment> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the segments experiments where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (SegmentsExperiment segmentsExperiment :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(segmentsExperiment);
		}
	}

	/**
	 * Returns the number of segments experiments where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching segments experiments
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			SegmentsExperiment.class);

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

			sb.append(_SQL_COUNT_SEGMENTSEXPERIMENT_WHERE);

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
		"segmentsExperiment.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(segmentsExperiment.uuid IS NULL OR segmentsExperiment.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"segmentsExperiment.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByGroupId;
	private FinderPath _finderPathWithoutPaginationFindByGroupId;
	private FinderPath _finderPathCountByGroupId;

	/**
	 * Returns all the segments experiments where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching segments experiments
	 */
	@Override
	public List<SegmentsExperiment> findByGroupId(long groupId) {
		return findByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the segments experiments where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperimentModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of segments experiments
	 * @param end the upper bound of the range of segments experiments (not inclusive)
	 * @return the range of matching segments experiments
	 */
	@Override
	public List<SegmentsExperiment> findByGroupId(
		long groupId, int start, int end) {

		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the segments experiments where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperimentModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of segments experiments
	 * @param end the upper bound of the range of segments experiments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments experiments
	 */
	@Override
	public List<SegmentsExperiment> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<SegmentsExperiment> orderByComparator) {

		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the segments experiments where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperimentModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of segments experiments
	 * @param end the upper bound of the range of segments experiments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching segments experiments
	 */
	@Override
	public List<SegmentsExperiment> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<SegmentsExperiment> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			SegmentsExperiment.class);

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

		List<SegmentsExperiment> list = null;

		if (useFinderCache && productionMode) {
			list = (List<SegmentsExperiment>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SegmentsExperiment segmentsExperiment : list) {
					if (groupId != segmentsExperiment.getGroupId()) {
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

			sb.append(_SQL_SELECT_SEGMENTSEXPERIMENT_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(SegmentsExperimentModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				list = (List<SegmentsExperiment>)QueryUtil.list(
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
	 * Returns the first segments experiment in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments experiment
	 * @throws NoSuchExperimentException if a matching segments experiment could not be found
	 */
	@Override
	public SegmentsExperiment findByGroupId_First(
			long groupId,
			OrderByComparator<SegmentsExperiment> orderByComparator)
		throws NoSuchExperimentException {

		SegmentsExperiment segmentsExperiment = fetchByGroupId_First(
			groupId, orderByComparator);

		if (segmentsExperiment != null) {
			return segmentsExperiment;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchExperimentException(sb.toString());
	}

	/**
	 * Returns the first segments experiment in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments experiment, or <code>null</code> if a matching segments experiment could not be found
	 */
	@Override
	public SegmentsExperiment fetchByGroupId_First(
		long groupId, OrderByComparator<SegmentsExperiment> orderByComparator) {

		List<SegmentsExperiment> list = findByGroupId(
			groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last segments experiment in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments experiment
	 * @throws NoSuchExperimentException if a matching segments experiment could not be found
	 */
	@Override
	public SegmentsExperiment findByGroupId_Last(
			long groupId,
			OrderByComparator<SegmentsExperiment> orderByComparator)
		throws NoSuchExperimentException {

		SegmentsExperiment segmentsExperiment = fetchByGroupId_Last(
			groupId, orderByComparator);

		if (segmentsExperiment != null) {
			return segmentsExperiment;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchExperimentException(sb.toString());
	}

	/**
	 * Returns the last segments experiment in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments experiment, or <code>null</code> if a matching segments experiment could not be found
	 */
	@Override
	public SegmentsExperiment fetchByGroupId_Last(
		long groupId, OrderByComparator<SegmentsExperiment> orderByComparator) {

		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<SegmentsExperiment> list = findByGroupId(
			groupId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the segments experiments before and after the current segments experiment in the ordered set where groupId = &#63;.
	 *
	 * @param segmentsExperimentId the primary key of the current segments experiment
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next segments experiment
	 * @throws NoSuchExperimentException if a segments experiment with the primary key could not be found
	 */
	@Override
	public SegmentsExperiment[] findByGroupId_PrevAndNext(
			long segmentsExperimentId, long groupId,
			OrderByComparator<SegmentsExperiment> orderByComparator)
		throws NoSuchExperimentException {

		SegmentsExperiment segmentsExperiment = findByPrimaryKey(
			segmentsExperimentId);

		Session session = null;

		try {
			session = openSession();

			SegmentsExperiment[] array = new SegmentsExperimentImpl[3];

			array[0] = getByGroupId_PrevAndNext(
				session, segmentsExperiment, groupId, orderByComparator, true);

			array[1] = segmentsExperiment;

			array[2] = getByGroupId_PrevAndNext(
				session, segmentsExperiment, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected SegmentsExperiment getByGroupId_PrevAndNext(
		Session session, SegmentsExperiment segmentsExperiment, long groupId,
		OrderByComparator<SegmentsExperiment> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_SEGMENTSEXPERIMENT_WHERE);

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
			sb.append(SegmentsExperimentModelImpl.ORDER_BY_JPQL);
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
						segmentsExperiment)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SegmentsExperiment> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the segments experiments that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching segments experiments that the user has permission to view
	 */
	@Override
	public List<SegmentsExperiment> filterFindByGroupId(long groupId) {
		return filterFindByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the segments experiments that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperimentModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of segments experiments
	 * @param end the upper bound of the range of segments experiments (not inclusive)
	 * @return the range of matching segments experiments that the user has permission to view
	 */
	@Override
	public List<SegmentsExperiment> filterFindByGroupId(
		long groupId, int start, int end) {

		return filterFindByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the segments experiments that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperimentModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of segments experiments
	 * @param end the upper bound of the range of segments experiments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments experiments that the user has permission to view
	 */
	@Override
	public List<SegmentsExperiment> filterFindByGroupId(
		long groupId, int start, int end,
		OrderByComparator<SegmentsExperiment> orderByComparator) {

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
			sb.append(_FILTER_SQL_SELECT_SEGMENTSEXPERIMENT_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_SEGMENTSEXPERIMENT_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_SEGMENTSEXPERIMENT_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(SegmentsExperimentModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(SegmentsExperimentModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), SegmentsExperiment.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, SegmentsExperimentImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, SegmentsExperimentImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			return (List<SegmentsExperiment>)QueryUtil.list(
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
	 * Returns the segments experiments before and after the current segments experiment in the ordered set of segments experiments that the user has permission to view where groupId = &#63;.
	 *
	 * @param segmentsExperimentId the primary key of the current segments experiment
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next segments experiment
	 * @throws NoSuchExperimentException if a segments experiment with the primary key could not be found
	 */
	@Override
	public SegmentsExperiment[] filterFindByGroupId_PrevAndNext(
			long segmentsExperimentId, long groupId,
			OrderByComparator<SegmentsExperiment> orderByComparator)
		throws NoSuchExperimentException {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId_PrevAndNext(
				segmentsExperimentId, groupId, orderByComparator);
		}

		SegmentsExperiment segmentsExperiment = findByPrimaryKey(
			segmentsExperimentId);

		Session session = null;

		try {
			session = openSession();

			SegmentsExperiment[] array = new SegmentsExperimentImpl[3];

			array[0] = filterGetByGroupId_PrevAndNext(
				session, segmentsExperiment, groupId, orderByComparator, true);

			array[1] = segmentsExperiment;

			array[2] = filterGetByGroupId_PrevAndNext(
				session, segmentsExperiment, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected SegmentsExperiment filterGetByGroupId_PrevAndNext(
		Session session, SegmentsExperiment segmentsExperiment, long groupId,
		OrderByComparator<SegmentsExperiment> orderByComparator,
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

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_SEGMENTSEXPERIMENT_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_SEGMENTSEXPERIMENT_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_SEGMENTSEXPERIMENT_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(SegmentsExperimentModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(SegmentsExperimentModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), SegmentsExperiment.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(
				_FILTER_ENTITY_ALIAS, SegmentsExperimentImpl.class);
		}
		else {
			sqlQuery.addEntity(
				_FILTER_ENTITY_TABLE, SegmentsExperimentImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(groupId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						segmentsExperiment)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SegmentsExperiment> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the segments experiments where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (SegmentsExperiment segmentsExperiment :
				findByGroupId(
					groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(segmentsExperiment);
		}
	}

	/**
	 * Returns the number of segments experiments where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching segments experiments
	 */
	@Override
	public int countByGroupId(long groupId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			SegmentsExperiment.class);

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

			sb.append(_SQL_COUNT_SEGMENTSEXPERIMENT_WHERE);

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
	 * Returns the number of segments experiments that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching segments experiments that the user has permission to view
	 */
	@Override
	public int filterCountByGroupId(long groupId) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByGroupId(groupId);
		}

		StringBundler sb = new StringBundler(2);

		sb.append(_FILTER_SQL_COUNT_SEGMENTSEXPERIMENT_WHERE);

		sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), SegmentsExperiment.class.getName(),
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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 =
		"segmentsExperiment.groupId = ?";

	private FinderPath _finderPathWithPaginationFindBySegmentsExperimentKey;
	private FinderPath _finderPathWithoutPaginationFindBySegmentsExperimentKey;
	private FinderPath _finderPathCountBySegmentsExperimentKey;

	/**
	 * Returns all the segments experiments where segmentsExperimentKey = &#63;.
	 *
	 * @param segmentsExperimentKey the segments experiment key
	 * @return the matching segments experiments
	 */
	@Override
	public List<SegmentsExperiment> findBySegmentsExperimentKey(
		String segmentsExperimentKey) {

		return findBySegmentsExperimentKey(
			segmentsExperimentKey, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the segments experiments where segmentsExperimentKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperimentModelImpl</code>.
	 * </p>
	 *
	 * @param segmentsExperimentKey the segments experiment key
	 * @param start the lower bound of the range of segments experiments
	 * @param end the upper bound of the range of segments experiments (not inclusive)
	 * @return the range of matching segments experiments
	 */
	@Override
	public List<SegmentsExperiment> findBySegmentsExperimentKey(
		String segmentsExperimentKey, int start, int end) {

		return findBySegmentsExperimentKey(
			segmentsExperimentKey, start, end, null);
	}

	/**
	 * Returns an ordered range of all the segments experiments where segmentsExperimentKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperimentModelImpl</code>.
	 * </p>
	 *
	 * @param segmentsExperimentKey the segments experiment key
	 * @param start the lower bound of the range of segments experiments
	 * @param end the upper bound of the range of segments experiments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments experiments
	 */
	@Override
	public List<SegmentsExperiment> findBySegmentsExperimentKey(
		String segmentsExperimentKey, int start, int end,
		OrderByComparator<SegmentsExperiment> orderByComparator) {

		return findBySegmentsExperimentKey(
			segmentsExperimentKey, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the segments experiments where segmentsExperimentKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperimentModelImpl</code>.
	 * </p>
	 *
	 * @param segmentsExperimentKey the segments experiment key
	 * @param start the lower bound of the range of segments experiments
	 * @param end the upper bound of the range of segments experiments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching segments experiments
	 */
	@Override
	public List<SegmentsExperiment> findBySegmentsExperimentKey(
		String segmentsExperimentKey, int start, int end,
		OrderByComparator<SegmentsExperiment> orderByComparator,
		boolean useFinderCache) {

		segmentsExperimentKey = Objects.toString(segmentsExperimentKey, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			SegmentsExperiment.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath =
					_finderPathWithoutPaginationFindBySegmentsExperimentKey;
				finderArgs = new Object[] {segmentsExperimentKey};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindBySegmentsExperimentKey;
			finderArgs = new Object[] {
				segmentsExperimentKey, start, end, orderByComparator
			};
		}

		List<SegmentsExperiment> list = null;

		if (useFinderCache && productionMode) {
			list = (List<SegmentsExperiment>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SegmentsExperiment segmentsExperiment : list) {
					if (!segmentsExperimentKey.equals(
							segmentsExperiment.getSegmentsExperimentKey())) {

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

			sb.append(_SQL_SELECT_SEGMENTSEXPERIMENT_WHERE);

			boolean bindSegmentsExperimentKey = false;

			if (segmentsExperimentKey.isEmpty()) {
				sb.append(
					_FINDER_COLUMN_SEGMENTSEXPERIMENTKEY_SEGMENTSEXPERIMENTKEY_3);
			}
			else {
				bindSegmentsExperimentKey = true;

				sb.append(
					_FINDER_COLUMN_SEGMENTSEXPERIMENTKEY_SEGMENTSEXPERIMENTKEY_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(SegmentsExperimentModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindSegmentsExperimentKey) {
					queryPos.add(segmentsExperimentKey);
				}

				list = (List<SegmentsExperiment>)QueryUtil.list(
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
	 * Returns the first segments experiment in the ordered set where segmentsExperimentKey = &#63;.
	 *
	 * @param segmentsExperimentKey the segments experiment key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments experiment
	 * @throws NoSuchExperimentException if a matching segments experiment could not be found
	 */
	@Override
	public SegmentsExperiment findBySegmentsExperimentKey_First(
			String segmentsExperimentKey,
			OrderByComparator<SegmentsExperiment> orderByComparator)
		throws NoSuchExperimentException {

		SegmentsExperiment segmentsExperiment =
			fetchBySegmentsExperimentKey_First(
				segmentsExperimentKey, orderByComparator);

		if (segmentsExperiment != null) {
			return segmentsExperiment;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("segmentsExperimentKey=");
		sb.append(segmentsExperimentKey);

		sb.append("}");

		throw new NoSuchExperimentException(sb.toString());
	}

	/**
	 * Returns the first segments experiment in the ordered set where segmentsExperimentKey = &#63;.
	 *
	 * @param segmentsExperimentKey the segments experiment key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments experiment, or <code>null</code> if a matching segments experiment could not be found
	 */
	@Override
	public SegmentsExperiment fetchBySegmentsExperimentKey_First(
		String segmentsExperimentKey,
		OrderByComparator<SegmentsExperiment> orderByComparator) {

		List<SegmentsExperiment> list = findBySegmentsExperimentKey(
			segmentsExperimentKey, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last segments experiment in the ordered set where segmentsExperimentKey = &#63;.
	 *
	 * @param segmentsExperimentKey the segments experiment key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments experiment
	 * @throws NoSuchExperimentException if a matching segments experiment could not be found
	 */
	@Override
	public SegmentsExperiment findBySegmentsExperimentKey_Last(
			String segmentsExperimentKey,
			OrderByComparator<SegmentsExperiment> orderByComparator)
		throws NoSuchExperimentException {

		SegmentsExperiment segmentsExperiment =
			fetchBySegmentsExperimentKey_Last(
				segmentsExperimentKey, orderByComparator);

		if (segmentsExperiment != null) {
			return segmentsExperiment;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("segmentsExperimentKey=");
		sb.append(segmentsExperimentKey);

		sb.append("}");

		throw new NoSuchExperimentException(sb.toString());
	}

	/**
	 * Returns the last segments experiment in the ordered set where segmentsExperimentKey = &#63;.
	 *
	 * @param segmentsExperimentKey the segments experiment key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments experiment, or <code>null</code> if a matching segments experiment could not be found
	 */
	@Override
	public SegmentsExperiment fetchBySegmentsExperimentKey_Last(
		String segmentsExperimentKey,
		OrderByComparator<SegmentsExperiment> orderByComparator) {

		int count = countBySegmentsExperimentKey(segmentsExperimentKey);

		if (count == 0) {
			return null;
		}

		List<SegmentsExperiment> list = findBySegmentsExperimentKey(
			segmentsExperimentKey, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the segments experiments before and after the current segments experiment in the ordered set where segmentsExperimentKey = &#63;.
	 *
	 * @param segmentsExperimentId the primary key of the current segments experiment
	 * @param segmentsExperimentKey the segments experiment key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next segments experiment
	 * @throws NoSuchExperimentException if a segments experiment with the primary key could not be found
	 */
	@Override
	public SegmentsExperiment[] findBySegmentsExperimentKey_PrevAndNext(
			long segmentsExperimentId, String segmentsExperimentKey,
			OrderByComparator<SegmentsExperiment> orderByComparator)
		throws NoSuchExperimentException {

		segmentsExperimentKey = Objects.toString(segmentsExperimentKey, "");

		SegmentsExperiment segmentsExperiment = findByPrimaryKey(
			segmentsExperimentId);

		Session session = null;

		try {
			session = openSession();

			SegmentsExperiment[] array = new SegmentsExperimentImpl[3];

			array[0] = getBySegmentsExperimentKey_PrevAndNext(
				session, segmentsExperiment, segmentsExperimentKey,
				orderByComparator, true);

			array[1] = segmentsExperiment;

			array[2] = getBySegmentsExperimentKey_PrevAndNext(
				session, segmentsExperiment, segmentsExperimentKey,
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

	protected SegmentsExperiment getBySegmentsExperimentKey_PrevAndNext(
		Session session, SegmentsExperiment segmentsExperiment,
		String segmentsExperimentKey,
		OrderByComparator<SegmentsExperiment> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_SEGMENTSEXPERIMENT_WHERE);

		boolean bindSegmentsExperimentKey = false;

		if (segmentsExperimentKey.isEmpty()) {
			sb.append(
				_FINDER_COLUMN_SEGMENTSEXPERIMENTKEY_SEGMENTSEXPERIMENTKEY_3);
		}
		else {
			bindSegmentsExperimentKey = true;

			sb.append(
				_FINDER_COLUMN_SEGMENTSEXPERIMENTKEY_SEGMENTSEXPERIMENTKEY_2);
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
			sb.append(SegmentsExperimentModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindSegmentsExperimentKey) {
			queryPos.add(segmentsExperimentKey);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						segmentsExperiment)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SegmentsExperiment> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the segments experiments where segmentsExperimentKey = &#63; from the database.
	 *
	 * @param segmentsExperimentKey the segments experiment key
	 */
	@Override
	public void removeBySegmentsExperimentKey(String segmentsExperimentKey) {
		for (SegmentsExperiment segmentsExperiment :
				findBySegmentsExperimentKey(
					segmentsExperimentKey, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(segmentsExperiment);
		}
	}

	/**
	 * Returns the number of segments experiments where segmentsExperimentKey = &#63;.
	 *
	 * @param segmentsExperimentKey the segments experiment key
	 * @return the number of matching segments experiments
	 */
	@Override
	public int countBySegmentsExperimentKey(String segmentsExperimentKey) {
		segmentsExperimentKey = Objects.toString(segmentsExperimentKey, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			SegmentsExperiment.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountBySegmentsExperimentKey;

			finderArgs = new Object[] {segmentsExperimentKey};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_SEGMENTSEXPERIMENT_WHERE);

			boolean bindSegmentsExperimentKey = false;

			if (segmentsExperimentKey.isEmpty()) {
				sb.append(
					_FINDER_COLUMN_SEGMENTSEXPERIMENTKEY_SEGMENTSEXPERIMENTKEY_3);
			}
			else {
				bindSegmentsExperimentKey = true;

				sb.append(
					_FINDER_COLUMN_SEGMENTSEXPERIMENTKEY_SEGMENTSEXPERIMENTKEY_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindSegmentsExperimentKey) {
					queryPos.add(segmentsExperimentKey);
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

	private static final String
		_FINDER_COLUMN_SEGMENTSEXPERIMENTKEY_SEGMENTSEXPERIMENTKEY_2 =
			"segmentsExperiment.segmentsExperimentKey = ?";

	private static final String
		_FINDER_COLUMN_SEGMENTSEXPERIMENTKEY_SEGMENTSEXPERIMENTKEY_3 =
			"(segmentsExperiment.segmentsExperimentKey IS NULL OR segmentsExperiment.segmentsExperimentKey = '')";

	private FinderPath _finderPathFetchByG_S;
	private FinderPath _finderPathCountByG_S;

	/**
	 * Returns the segments experiment where groupId = &#63; and segmentsExperimentKey = &#63; or throws a <code>NoSuchExperimentException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param segmentsExperimentKey the segments experiment key
	 * @return the matching segments experiment
	 * @throws NoSuchExperimentException if a matching segments experiment could not be found
	 */
	@Override
	public SegmentsExperiment findByG_S(
			long groupId, String segmentsExperimentKey)
		throws NoSuchExperimentException {

		SegmentsExperiment segmentsExperiment = fetchByG_S(
			groupId, segmentsExperimentKey);

		if (segmentsExperiment == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("groupId=");
			sb.append(groupId);

			sb.append(", segmentsExperimentKey=");
			sb.append(segmentsExperimentKey);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchExperimentException(sb.toString());
		}

		return segmentsExperiment;
	}

	/**
	 * Returns the segments experiment where groupId = &#63; and segmentsExperimentKey = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param segmentsExperimentKey the segments experiment key
	 * @return the matching segments experiment, or <code>null</code> if a matching segments experiment could not be found
	 */
	@Override
	public SegmentsExperiment fetchByG_S(
		long groupId, String segmentsExperimentKey) {

		return fetchByG_S(groupId, segmentsExperimentKey, true);
	}

	/**
	 * Returns the segments experiment where groupId = &#63; and segmentsExperimentKey = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param segmentsExperimentKey the segments experiment key
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching segments experiment, or <code>null</code> if a matching segments experiment could not be found
	 */
	@Override
	public SegmentsExperiment fetchByG_S(
		long groupId, String segmentsExperimentKey, boolean useFinderCache) {

		segmentsExperimentKey = Objects.toString(segmentsExperimentKey, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			SegmentsExperiment.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {groupId, segmentsExperimentKey};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(
				_finderPathFetchByG_S, finderArgs, this);
		}

		if (result instanceof SegmentsExperiment) {
			SegmentsExperiment segmentsExperiment = (SegmentsExperiment)result;

			if ((groupId != segmentsExperiment.getGroupId()) ||
				!Objects.equals(
					segmentsExperimentKey,
					segmentsExperiment.getSegmentsExperimentKey())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_SEGMENTSEXPERIMENT_WHERE);

			sb.append(_FINDER_COLUMN_G_S_GROUPID_2);

			boolean bindSegmentsExperimentKey = false;

			if (segmentsExperimentKey.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_S_SEGMENTSEXPERIMENTKEY_3);
			}
			else {
				bindSegmentsExperimentKey = true;

				sb.append(_FINDER_COLUMN_G_S_SEGMENTSEXPERIMENTKEY_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindSegmentsExperimentKey) {
					queryPos.add(segmentsExperimentKey);
				}

				List<SegmentsExperiment> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByG_S, finderArgs, list);
					}
				}
				else {
					SegmentsExperiment segmentsExperiment = list.get(0);

					result = segmentsExperiment;

					cacheResult(segmentsExperiment);
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
			return (SegmentsExperiment)result;
		}
	}

	/**
	 * Removes the segments experiment where groupId = &#63; and segmentsExperimentKey = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param segmentsExperimentKey the segments experiment key
	 * @return the segments experiment that was removed
	 */
	@Override
	public SegmentsExperiment removeByG_S(
			long groupId, String segmentsExperimentKey)
		throws NoSuchExperimentException {

		SegmentsExperiment segmentsExperiment = findByG_S(
			groupId, segmentsExperimentKey);

		return remove(segmentsExperiment);
	}

	/**
	 * Returns the number of segments experiments where groupId = &#63; and segmentsExperimentKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param segmentsExperimentKey the segments experiment key
	 * @return the number of matching segments experiments
	 */
	@Override
	public int countByG_S(long groupId, String segmentsExperimentKey) {
		segmentsExperimentKey = Objects.toString(segmentsExperimentKey, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			SegmentsExperiment.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_S;

			finderArgs = new Object[] {groupId, segmentsExperimentKey};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_SEGMENTSEXPERIMENT_WHERE);

			sb.append(_FINDER_COLUMN_G_S_GROUPID_2);

			boolean bindSegmentsExperimentKey = false;

			if (segmentsExperimentKey.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_S_SEGMENTSEXPERIMENTKEY_3);
			}
			else {
				bindSegmentsExperimentKey = true;

				sb.append(_FINDER_COLUMN_G_S_SEGMENTSEXPERIMENTKEY_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindSegmentsExperimentKey) {
					queryPos.add(segmentsExperimentKey);
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

	private static final String _FINDER_COLUMN_G_S_GROUPID_2 =
		"segmentsExperiment.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_S_SEGMENTSEXPERIMENTKEY_2 =
		"segmentsExperiment.segmentsExperimentKey = ?";

	private static final String _FINDER_COLUMN_G_S_SEGMENTSEXPERIMENTKEY_3 =
		"(segmentsExperiment.segmentsExperimentKey IS NULL OR segmentsExperiment.segmentsExperimentKey = '')";

	private FinderPath _finderPathWithPaginationFindByG_C_C;
	private FinderPath _finderPathWithoutPaginationFindByG_C_C;
	private FinderPath _finderPathCountByG_C_C;

	/**
	 * Returns all the segments experiments where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching segments experiments
	 */
	@Override
	public List<SegmentsExperiment> findByG_C_C(
		long groupId, long classNameId, long classPK) {

		return findByG_C_C(
			groupId, classNameId, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the segments experiments where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperimentModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of segments experiments
	 * @param end the upper bound of the range of segments experiments (not inclusive)
	 * @return the range of matching segments experiments
	 */
	@Override
	public List<SegmentsExperiment> findByG_C_C(
		long groupId, long classNameId, long classPK, int start, int end) {

		return findByG_C_C(groupId, classNameId, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the segments experiments where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperimentModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of segments experiments
	 * @param end the upper bound of the range of segments experiments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments experiments
	 */
	@Override
	public List<SegmentsExperiment> findByG_C_C(
		long groupId, long classNameId, long classPK, int start, int end,
		OrderByComparator<SegmentsExperiment> orderByComparator) {

		return findByG_C_C(
			groupId, classNameId, classPK, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the segments experiments where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperimentModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of segments experiments
	 * @param end the upper bound of the range of segments experiments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching segments experiments
	 */
	@Override
	public List<SegmentsExperiment> findByG_C_C(
		long groupId, long classNameId, long classPK, int start, int end,
		OrderByComparator<SegmentsExperiment> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			SegmentsExperiment.class);

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

		List<SegmentsExperiment> list = null;

		if (useFinderCache && productionMode) {
			list = (List<SegmentsExperiment>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SegmentsExperiment segmentsExperiment : list) {
					if ((groupId != segmentsExperiment.getGroupId()) ||
						(classNameId != segmentsExperiment.getClassNameId()) ||
						(classPK != segmentsExperiment.getClassPK())) {

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

			sb.append(_SQL_SELECT_SEGMENTSEXPERIMENT_WHERE);

			sb.append(_FINDER_COLUMN_G_C_C_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_G_C_C_CLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(SegmentsExperimentModelImpl.ORDER_BY_JPQL);
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

				list = (List<SegmentsExperiment>)QueryUtil.list(
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
	 * Returns the first segments experiment in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments experiment
	 * @throws NoSuchExperimentException if a matching segments experiment could not be found
	 */
	@Override
	public SegmentsExperiment findByG_C_C_First(
			long groupId, long classNameId, long classPK,
			OrderByComparator<SegmentsExperiment> orderByComparator)
		throws NoSuchExperimentException {

		SegmentsExperiment segmentsExperiment = fetchByG_C_C_First(
			groupId, classNameId, classPK, orderByComparator);

		if (segmentsExperiment != null) {
			return segmentsExperiment;
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

		throw new NoSuchExperimentException(sb.toString());
	}

	/**
	 * Returns the first segments experiment in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments experiment, or <code>null</code> if a matching segments experiment could not be found
	 */
	@Override
	public SegmentsExperiment fetchByG_C_C_First(
		long groupId, long classNameId, long classPK,
		OrderByComparator<SegmentsExperiment> orderByComparator) {

		List<SegmentsExperiment> list = findByG_C_C(
			groupId, classNameId, classPK, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last segments experiment in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments experiment
	 * @throws NoSuchExperimentException if a matching segments experiment could not be found
	 */
	@Override
	public SegmentsExperiment findByG_C_C_Last(
			long groupId, long classNameId, long classPK,
			OrderByComparator<SegmentsExperiment> orderByComparator)
		throws NoSuchExperimentException {

		SegmentsExperiment segmentsExperiment = fetchByG_C_C_Last(
			groupId, classNameId, classPK, orderByComparator);

		if (segmentsExperiment != null) {
			return segmentsExperiment;
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

		throw new NoSuchExperimentException(sb.toString());
	}

	/**
	 * Returns the last segments experiment in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments experiment, or <code>null</code> if a matching segments experiment could not be found
	 */
	@Override
	public SegmentsExperiment fetchByG_C_C_Last(
		long groupId, long classNameId, long classPK,
		OrderByComparator<SegmentsExperiment> orderByComparator) {

		int count = countByG_C_C(groupId, classNameId, classPK);

		if (count == 0) {
			return null;
		}

		List<SegmentsExperiment> list = findByG_C_C(
			groupId, classNameId, classPK, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the segments experiments before and after the current segments experiment in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param segmentsExperimentId the primary key of the current segments experiment
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next segments experiment
	 * @throws NoSuchExperimentException if a segments experiment with the primary key could not be found
	 */
	@Override
	public SegmentsExperiment[] findByG_C_C_PrevAndNext(
			long segmentsExperimentId, long groupId, long classNameId,
			long classPK,
			OrderByComparator<SegmentsExperiment> orderByComparator)
		throws NoSuchExperimentException {

		SegmentsExperiment segmentsExperiment = findByPrimaryKey(
			segmentsExperimentId);

		Session session = null;

		try {
			session = openSession();

			SegmentsExperiment[] array = new SegmentsExperimentImpl[3];

			array[0] = getByG_C_C_PrevAndNext(
				session, segmentsExperiment, groupId, classNameId, classPK,
				orderByComparator, true);

			array[1] = segmentsExperiment;

			array[2] = getByG_C_C_PrevAndNext(
				session, segmentsExperiment, groupId, classNameId, classPK,
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

	protected SegmentsExperiment getByG_C_C_PrevAndNext(
		Session session, SegmentsExperiment segmentsExperiment, long groupId,
		long classNameId, long classPK,
		OrderByComparator<SegmentsExperiment> orderByComparator,
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

		sb.append(_SQL_SELECT_SEGMENTSEXPERIMENT_WHERE);

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
			sb.append(SegmentsExperimentModelImpl.ORDER_BY_JPQL);
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
					orderByComparator.getOrderByConditionValues(
						segmentsExperiment)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SegmentsExperiment> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the segments experiments that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching segments experiments that the user has permission to view
	 */
	@Override
	public List<SegmentsExperiment> filterFindByG_C_C(
		long groupId, long classNameId, long classPK) {

		return filterFindByG_C_C(
			groupId, classNameId, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the segments experiments that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperimentModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of segments experiments
	 * @param end the upper bound of the range of segments experiments (not inclusive)
	 * @return the range of matching segments experiments that the user has permission to view
	 */
	@Override
	public List<SegmentsExperiment> filterFindByG_C_C(
		long groupId, long classNameId, long classPK, int start, int end) {

		return filterFindByG_C_C(
			groupId, classNameId, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the segments experiments that the user has permissions to view where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperimentModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of segments experiments
	 * @param end the upper bound of the range of segments experiments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments experiments that the user has permission to view
	 */
	@Override
	public List<SegmentsExperiment> filterFindByG_C_C(
		long groupId, long classNameId, long classPK, int start, int end,
		OrderByComparator<SegmentsExperiment> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C_C(
				groupId, classNameId, classPK, start, end, orderByComparator);
		}

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(6);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_SEGMENTSEXPERIMENT_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_SEGMENTSEXPERIMENT_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_C_C_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_C_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_G_C_C_CLASSPK_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_SEGMENTSEXPERIMENT_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(SegmentsExperimentModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(SegmentsExperimentModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), SegmentsExperiment.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, SegmentsExperimentImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, SegmentsExperimentImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(classNameId);

			queryPos.add(classPK);

			return (List<SegmentsExperiment>)QueryUtil.list(
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
	 * Returns the segments experiments before and after the current segments experiment in the ordered set of segments experiments that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param segmentsExperimentId the primary key of the current segments experiment
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next segments experiment
	 * @throws NoSuchExperimentException if a segments experiment with the primary key could not be found
	 */
	@Override
	public SegmentsExperiment[] filterFindByG_C_C_PrevAndNext(
			long segmentsExperimentId, long groupId, long classNameId,
			long classPK,
			OrderByComparator<SegmentsExperiment> orderByComparator)
		throws NoSuchExperimentException {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C_C_PrevAndNext(
				segmentsExperimentId, groupId, classNameId, classPK,
				orderByComparator);
		}

		SegmentsExperiment segmentsExperiment = findByPrimaryKey(
			segmentsExperimentId);

		Session session = null;

		try {
			session = openSession();

			SegmentsExperiment[] array = new SegmentsExperimentImpl[3];

			array[0] = filterGetByG_C_C_PrevAndNext(
				session, segmentsExperiment, groupId, classNameId, classPK,
				orderByComparator, true);

			array[1] = segmentsExperiment;

			array[2] = filterGetByG_C_C_PrevAndNext(
				session, segmentsExperiment, groupId, classNameId, classPK,
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

	protected SegmentsExperiment filterGetByG_C_C_PrevAndNext(
		Session session, SegmentsExperiment segmentsExperiment, long groupId,
		long classNameId, long classPK,
		OrderByComparator<SegmentsExperiment> orderByComparator,
		boolean previous) {

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
			sb.append(_FILTER_SQL_SELECT_SEGMENTSEXPERIMENT_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_SEGMENTSEXPERIMENT_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_C_C_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_C_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_G_C_C_CLASSPK_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_SEGMENTSEXPERIMENT_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(SegmentsExperimentModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(SegmentsExperimentModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), SegmentsExperiment.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(
				_FILTER_ENTITY_ALIAS, SegmentsExperimentImpl.class);
		}
		else {
			sqlQuery.addEntity(
				_FILTER_ENTITY_TABLE, SegmentsExperimentImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(groupId);

		queryPos.add(classNameId);

		queryPos.add(classPK);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						segmentsExperiment)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SegmentsExperiment> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the segments experiments where groupId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	@Override
	public void removeByG_C_C(long groupId, long classNameId, long classPK) {
		for (SegmentsExperiment segmentsExperiment :
				findByG_C_C(
					groupId, classNameId, classPK, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(segmentsExperiment);
		}
	}

	/**
	 * Returns the number of segments experiments where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching segments experiments
	 */
	@Override
	public int countByG_C_C(long groupId, long classNameId, long classPK) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			SegmentsExperiment.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_C_C;

			finderArgs = new Object[] {groupId, classNameId, classPK};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_SEGMENTSEXPERIMENT_WHERE);

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
	 * Returns the number of segments experiments that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching segments experiments that the user has permission to view
	 */
	@Override
	public int filterCountByG_C_C(
		long groupId, long classNameId, long classPK) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_C_C(groupId, classNameId, classPK);
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_FILTER_SQL_COUNT_SEGMENTSEXPERIMENT_WHERE);

		sb.append(_FINDER_COLUMN_G_C_C_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_C_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_G_C_C_CLASSPK_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), SegmentsExperiment.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(classNameId);

			queryPos.add(classPK);

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

	private static final String _FINDER_COLUMN_G_C_C_GROUPID_2 =
		"segmentsExperiment.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_CLASSNAMEID_2 =
		"segmentsExperiment.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_CLASSPK_2 =
		"segmentsExperiment.classPK = ?";

	private FinderPath _finderPathWithPaginationFindByS_C_C;
	private FinderPath _finderPathWithoutPaginationFindByS_C_C;
	private FinderPath _finderPathCountByS_C_C;

	/**
	 * Returns all the segments experiments where segmentsExperienceId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param segmentsExperienceId the segments experience ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching segments experiments
	 */
	@Override
	public List<SegmentsExperiment> findByS_C_C(
		long segmentsExperienceId, long classNameId, long classPK) {

		return findByS_C_C(
			segmentsExperienceId, classNameId, classPK, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the segments experiments where segmentsExperienceId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperimentModelImpl</code>.
	 * </p>
	 *
	 * @param segmentsExperienceId the segments experience ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of segments experiments
	 * @param end the upper bound of the range of segments experiments (not inclusive)
	 * @return the range of matching segments experiments
	 */
	@Override
	public List<SegmentsExperiment> findByS_C_C(
		long segmentsExperienceId, long classNameId, long classPK, int start,
		int end) {

		return findByS_C_C(
			segmentsExperienceId, classNameId, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the segments experiments where segmentsExperienceId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperimentModelImpl</code>.
	 * </p>
	 *
	 * @param segmentsExperienceId the segments experience ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of segments experiments
	 * @param end the upper bound of the range of segments experiments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments experiments
	 */
	@Override
	public List<SegmentsExperiment> findByS_C_C(
		long segmentsExperienceId, long classNameId, long classPK, int start,
		int end, OrderByComparator<SegmentsExperiment> orderByComparator) {

		return findByS_C_C(
			segmentsExperienceId, classNameId, classPK, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the segments experiments where segmentsExperienceId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperimentModelImpl</code>.
	 * </p>
	 *
	 * @param segmentsExperienceId the segments experience ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of segments experiments
	 * @param end the upper bound of the range of segments experiments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching segments experiments
	 */
	@Override
	public List<SegmentsExperiment> findByS_C_C(
		long segmentsExperienceId, long classNameId, long classPK, int start,
		int end, OrderByComparator<SegmentsExperiment> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			SegmentsExperiment.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByS_C_C;
				finderArgs = new Object[] {
					segmentsExperienceId, classNameId, classPK
				};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByS_C_C;
			finderArgs = new Object[] {
				segmentsExperienceId, classNameId, classPK, start, end,
				orderByComparator
			};
		}

		List<SegmentsExperiment> list = null;

		if (useFinderCache && productionMode) {
			list = (List<SegmentsExperiment>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SegmentsExperiment segmentsExperiment : list) {
					if ((segmentsExperienceId !=
							segmentsExperiment.getSegmentsExperienceId()) ||
						(classNameId != segmentsExperiment.getClassNameId()) ||
						(classPK != segmentsExperiment.getClassPK())) {

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

			sb.append(_SQL_SELECT_SEGMENTSEXPERIMENT_WHERE);

			sb.append(_FINDER_COLUMN_S_C_C_SEGMENTSEXPERIENCEID_2);

			sb.append(_FINDER_COLUMN_S_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_S_C_C_CLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(SegmentsExperimentModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(segmentsExperienceId);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				list = (List<SegmentsExperiment>)QueryUtil.list(
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
	 * Returns the first segments experiment in the ordered set where segmentsExperienceId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param segmentsExperienceId the segments experience ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments experiment
	 * @throws NoSuchExperimentException if a matching segments experiment could not be found
	 */
	@Override
	public SegmentsExperiment findByS_C_C_First(
			long segmentsExperienceId, long classNameId, long classPK,
			OrderByComparator<SegmentsExperiment> orderByComparator)
		throws NoSuchExperimentException {

		SegmentsExperiment segmentsExperiment = fetchByS_C_C_First(
			segmentsExperienceId, classNameId, classPK, orderByComparator);

		if (segmentsExperiment != null) {
			return segmentsExperiment;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("segmentsExperienceId=");
		sb.append(segmentsExperienceId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append("}");

		throw new NoSuchExperimentException(sb.toString());
	}

	/**
	 * Returns the first segments experiment in the ordered set where segmentsExperienceId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param segmentsExperienceId the segments experience ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments experiment, or <code>null</code> if a matching segments experiment could not be found
	 */
	@Override
	public SegmentsExperiment fetchByS_C_C_First(
		long segmentsExperienceId, long classNameId, long classPK,
		OrderByComparator<SegmentsExperiment> orderByComparator) {

		List<SegmentsExperiment> list = findByS_C_C(
			segmentsExperienceId, classNameId, classPK, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last segments experiment in the ordered set where segmentsExperienceId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param segmentsExperienceId the segments experience ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments experiment
	 * @throws NoSuchExperimentException if a matching segments experiment could not be found
	 */
	@Override
	public SegmentsExperiment findByS_C_C_Last(
			long segmentsExperienceId, long classNameId, long classPK,
			OrderByComparator<SegmentsExperiment> orderByComparator)
		throws NoSuchExperimentException {

		SegmentsExperiment segmentsExperiment = fetchByS_C_C_Last(
			segmentsExperienceId, classNameId, classPK, orderByComparator);

		if (segmentsExperiment != null) {
			return segmentsExperiment;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("segmentsExperienceId=");
		sb.append(segmentsExperienceId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append("}");

		throw new NoSuchExperimentException(sb.toString());
	}

	/**
	 * Returns the last segments experiment in the ordered set where segmentsExperienceId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param segmentsExperienceId the segments experience ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments experiment, or <code>null</code> if a matching segments experiment could not be found
	 */
	@Override
	public SegmentsExperiment fetchByS_C_C_Last(
		long segmentsExperienceId, long classNameId, long classPK,
		OrderByComparator<SegmentsExperiment> orderByComparator) {

		int count = countByS_C_C(segmentsExperienceId, classNameId, classPK);

		if (count == 0) {
			return null;
		}

		List<SegmentsExperiment> list = findByS_C_C(
			segmentsExperienceId, classNameId, classPK, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the segments experiments before and after the current segments experiment in the ordered set where segmentsExperienceId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param segmentsExperimentId the primary key of the current segments experiment
	 * @param segmentsExperienceId the segments experience ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next segments experiment
	 * @throws NoSuchExperimentException if a segments experiment with the primary key could not be found
	 */
	@Override
	public SegmentsExperiment[] findByS_C_C_PrevAndNext(
			long segmentsExperimentId, long segmentsExperienceId,
			long classNameId, long classPK,
			OrderByComparator<SegmentsExperiment> orderByComparator)
		throws NoSuchExperimentException {

		SegmentsExperiment segmentsExperiment = findByPrimaryKey(
			segmentsExperimentId);

		Session session = null;

		try {
			session = openSession();

			SegmentsExperiment[] array = new SegmentsExperimentImpl[3];

			array[0] = getByS_C_C_PrevAndNext(
				session, segmentsExperiment, segmentsExperienceId, classNameId,
				classPK, orderByComparator, true);

			array[1] = segmentsExperiment;

			array[2] = getByS_C_C_PrevAndNext(
				session, segmentsExperiment, segmentsExperienceId, classNameId,
				classPK, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected SegmentsExperiment getByS_C_C_PrevAndNext(
		Session session, SegmentsExperiment segmentsExperiment,
		long segmentsExperienceId, long classNameId, long classPK,
		OrderByComparator<SegmentsExperiment> orderByComparator,
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

		sb.append(_SQL_SELECT_SEGMENTSEXPERIMENT_WHERE);

		sb.append(_FINDER_COLUMN_S_C_C_SEGMENTSEXPERIENCEID_2);

		sb.append(_FINDER_COLUMN_S_C_C_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_S_C_C_CLASSPK_2);

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
			sb.append(SegmentsExperimentModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(segmentsExperienceId);

		queryPos.add(classNameId);

		queryPos.add(classPK);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						segmentsExperiment)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SegmentsExperiment> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the segments experiments where segmentsExperienceId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param segmentsExperienceId the segments experience ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	@Override
	public void removeByS_C_C(
		long segmentsExperienceId, long classNameId, long classPK) {

		for (SegmentsExperiment segmentsExperiment :
				findByS_C_C(
					segmentsExperienceId, classNameId, classPK,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(segmentsExperiment);
		}
	}

	/**
	 * Returns the number of segments experiments where segmentsExperienceId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param segmentsExperienceId the segments experience ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching segments experiments
	 */
	@Override
	public int countByS_C_C(
		long segmentsExperienceId, long classNameId, long classPK) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			SegmentsExperiment.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByS_C_C;

			finderArgs = new Object[] {
				segmentsExperienceId, classNameId, classPK
			};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_SEGMENTSEXPERIMENT_WHERE);

			sb.append(_FINDER_COLUMN_S_C_C_SEGMENTSEXPERIENCEID_2);

			sb.append(_FINDER_COLUMN_S_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_S_C_C_CLASSPK_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(segmentsExperienceId);

				queryPos.add(classNameId);

				queryPos.add(classPK);

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

	private static final String _FINDER_COLUMN_S_C_C_SEGMENTSEXPERIENCEID_2 =
		"segmentsExperiment.segmentsExperienceId = ? AND ";

	private static final String _FINDER_COLUMN_S_C_C_CLASSNAMEID_2 =
		"segmentsExperiment.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_S_C_C_CLASSPK_2 =
		"segmentsExperiment.classPK = ?";

	private FinderPath _finderPathWithPaginationFindByS_C_C_S;
	private FinderPath _finderPathWithoutPaginationFindByS_C_C_S;
	private FinderPath _finderPathCountByS_C_C_S;
	private FinderPath _finderPathWithPaginationCountByS_C_C_S;

	/**
	 * Returns all the segments experiments where segmentsExperienceId = &#63; and classNameId = &#63; and classPK = &#63; and status = &#63;.
	 *
	 * @param segmentsExperienceId the segments experience ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param status the status
	 * @return the matching segments experiments
	 */
	@Override
	public List<SegmentsExperiment> findByS_C_C_S(
		long segmentsExperienceId, long classNameId, long classPK, int status) {

		return findByS_C_C_S(
			segmentsExperienceId, classNameId, classPK, status,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the segments experiments where segmentsExperienceId = &#63; and classNameId = &#63; and classPK = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperimentModelImpl</code>.
	 * </p>
	 *
	 * @param segmentsExperienceId the segments experience ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param status the status
	 * @param start the lower bound of the range of segments experiments
	 * @param end the upper bound of the range of segments experiments (not inclusive)
	 * @return the range of matching segments experiments
	 */
	@Override
	public List<SegmentsExperiment> findByS_C_C_S(
		long segmentsExperienceId, long classNameId, long classPK, int status,
		int start, int end) {

		return findByS_C_C_S(
			segmentsExperienceId, classNameId, classPK, status, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the segments experiments where segmentsExperienceId = &#63; and classNameId = &#63; and classPK = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperimentModelImpl</code>.
	 * </p>
	 *
	 * @param segmentsExperienceId the segments experience ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param status the status
	 * @param start the lower bound of the range of segments experiments
	 * @param end the upper bound of the range of segments experiments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments experiments
	 */
	@Override
	public List<SegmentsExperiment> findByS_C_C_S(
		long segmentsExperienceId, long classNameId, long classPK, int status,
		int start, int end,
		OrderByComparator<SegmentsExperiment> orderByComparator) {

		return findByS_C_C_S(
			segmentsExperienceId, classNameId, classPK, status, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the segments experiments where segmentsExperienceId = &#63; and classNameId = &#63; and classPK = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperimentModelImpl</code>.
	 * </p>
	 *
	 * @param segmentsExperienceId the segments experience ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param status the status
	 * @param start the lower bound of the range of segments experiments
	 * @param end the upper bound of the range of segments experiments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching segments experiments
	 */
	@Override
	public List<SegmentsExperiment> findByS_C_C_S(
		long segmentsExperienceId, long classNameId, long classPK, int status,
		int start, int end,
		OrderByComparator<SegmentsExperiment> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			SegmentsExperiment.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByS_C_C_S;
				finderArgs = new Object[] {
					segmentsExperienceId, classNameId, classPK, status
				};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByS_C_C_S;
			finderArgs = new Object[] {
				segmentsExperienceId, classNameId, classPK, status, start, end,
				orderByComparator
			};
		}

		List<SegmentsExperiment> list = null;

		if (useFinderCache && productionMode) {
			list = (List<SegmentsExperiment>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SegmentsExperiment segmentsExperiment : list) {
					if ((segmentsExperienceId !=
							segmentsExperiment.getSegmentsExperienceId()) ||
						(classNameId != segmentsExperiment.getClassNameId()) ||
						(classPK != segmentsExperiment.getClassPK()) ||
						(status != segmentsExperiment.getStatus())) {

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

			sb.append(_SQL_SELECT_SEGMENTSEXPERIMENT_WHERE);

			sb.append(_FINDER_COLUMN_S_C_C_S_SEGMENTSEXPERIENCEID_2);

			sb.append(_FINDER_COLUMN_S_C_C_S_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_S_C_C_S_CLASSPK_2);

			sb.append(_FINDER_COLUMN_S_C_C_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(SegmentsExperimentModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(segmentsExperienceId);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				queryPos.add(status);

				list = (List<SegmentsExperiment>)QueryUtil.list(
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
	 * Returns the first segments experiment in the ordered set where segmentsExperienceId = &#63; and classNameId = &#63; and classPK = &#63; and status = &#63;.
	 *
	 * @param segmentsExperienceId the segments experience ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments experiment
	 * @throws NoSuchExperimentException if a matching segments experiment could not be found
	 */
	@Override
	public SegmentsExperiment findByS_C_C_S_First(
			long segmentsExperienceId, long classNameId, long classPK,
			int status, OrderByComparator<SegmentsExperiment> orderByComparator)
		throws NoSuchExperimentException {

		SegmentsExperiment segmentsExperiment = fetchByS_C_C_S_First(
			segmentsExperienceId, classNameId, classPK, status,
			orderByComparator);

		if (segmentsExperiment != null) {
			return segmentsExperiment;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("segmentsExperienceId=");
		sb.append(segmentsExperienceId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchExperimentException(sb.toString());
	}

	/**
	 * Returns the first segments experiment in the ordered set where segmentsExperienceId = &#63; and classNameId = &#63; and classPK = &#63; and status = &#63;.
	 *
	 * @param segmentsExperienceId the segments experience ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching segments experiment, or <code>null</code> if a matching segments experiment could not be found
	 */
	@Override
	public SegmentsExperiment fetchByS_C_C_S_First(
		long segmentsExperienceId, long classNameId, long classPK, int status,
		OrderByComparator<SegmentsExperiment> orderByComparator) {

		List<SegmentsExperiment> list = findByS_C_C_S(
			segmentsExperienceId, classNameId, classPK, status, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last segments experiment in the ordered set where segmentsExperienceId = &#63; and classNameId = &#63; and classPK = &#63; and status = &#63;.
	 *
	 * @param segmentsExperienceId the segments experience ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments experiment
	 * @throws NoSuchExperimentException if a matching segments experiment could not be found
	 */
	@Override
	public SegmentsExperiment findByS_C_C_S_Last(
			long segmentsExperienceId, long classNameId, long classPK,
			int status, OrderByComparator<SegmentsExperiment> orderByComparator)
		throws NoSuchExperimentException {

		SegmentsExperiment segmentsExperiment = fetchByS_C_C_S_Last(
			segmentsExperienceId, classNameId, classPK, status,
			orderByComparator);

		if (segmentsExperiment != null) {
			return segmentsExperiment;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("segmentsExperienceId=");
		sb.append(segmentsExperienceId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchExperimentException(sb.toString());
	}

	/**
	 * Returns the last segments experiment in the ordered set where segmentsExperienceId = &#63; and classNameId = &#63; and classPK = &#63; and status = &#63;.
	 *
	 * @param segmentsExperienceId the segments experience ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching segments experiment, or <code>null</code> if a matching segments experiment could not be found
	 */
	@Override
	public SegmentsExperiment fetchByS_C_C_S_Last(
		long segmentsExperienceId, long classNameId, long classPK, int status,
		OrderByComparator<SegmentsExperiment> orderByComparator) {

		int count = countByS_C_C_S(
			segmentsExperienceId, classNameId, classPK, status);

		if (count == 0) {
			return null;
		}

		List<SegmentsExperiment> list = findByS_C_C_S(
			segmentsExperienceId, classNameId, classPK, status, count - 1,
			count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the segments experiments before and after the current segments experiment in the ordered set where segmentsExperienceId = &#63; and classNameId = &#63; and classPK = &#63; and status = &#63;.
	 *
	 * @param segmentsExperimentId the primary key of the current segments experiment
	 * @param segmentsExperienceId the segments experience ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next segments experiment
	 * @throws NoSuchExperimentException if a segments experiment with the primary key could not be found
	 */
	@Override
	public SegmentsExperiment[] findByS_C_C_S_PrevAndNext(
			long segmentsExperimentId, long segmentsExperienceId,
			long classNameId, long classPK, int status,
			OrderByComparator<SegmentsExperiment> orderByComparator)
		throws NoSuchExperimentException {

		SegmentsExperiment segmentsExperiment = findByPrimaryKey(
			segmentsExperimentId);

		Session session = null;

		try {
			session = openSession();

			SegmentsExperiment[] array = new SegmentsExperimentImpl[3];

			array[0] = getByS_C_C_S_PrevAndNext(
				session, segmentsExperiment, segmentsExperienceId, classNameId,
				classPK, status, orderByComparator, true);

			array[1] = segmentsExperiment;

			array[2] = getByS_C_C_S_PrevAndNext(
				session, segmentsExperiment, segmentsExperienceId, classNameId,
				classPK, status, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected SegmentsExperiment getByS_C_C_S_PrevAndNext(
		Session session, SegmentsExperiment segmentsExperiment,
		long segmentsExperienceId, long classNameId, long classPK, int status,
		OrderByComparator<SegmentsExperiment> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				7 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(6);
		}

		sb.append(_SQL_SELECT_SEGMENTSEXPERIMENT_WHERE);

		sb.append(_FINDER_COLUMN_S_C_C_S_SEGMENTSEXPERIENCEID_2);

		sb.append(_FINDER_COLUMN_S_C_C_S_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_S_C_C_S_CLASSPK_2);

		sb.append(_FINDER_COLUMN_S_C_C_S_STATUS_2);

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
			sb.append(SegmentsExperimentModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(segmentsExperienceId);

		queryPos.add(classNameId);

		queryPos.add(classPK);

		queryPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						segmentsExperiment)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SegmentsExperiment> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the segments experiments where segmentsExperienceId = any &#63; and classNameId = &#63; and classPK = &#63; and status = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperimentModelImpl</code>.
	 * </p>
	 *
	 * @param segmentsExperienceIds the segments experience IDs
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param statuses the statuses
	 * @return the matching segments experiments
	 */
	@Override
	public List<SegmentsExperiment> findByS_C_C_S(
		long[] segmentsExperienceIds, long classNameId, long classPK,
		int[] statuses) {

		return findByS_C_C_S(
			segmentsExperienceIds, classNameId, classPK, statuses,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the segments experiments where segmentsExperienceId = any &#63; and classNameId = &#63; and classPK = &#63; and status = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperimentModelImpl</code>.
	 * </p>
	 *
	 * @param segmentsExperienceIds the segments experience IDs
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param statuses the statuses
	 * @param start the lower bound of the range of segments experiments
	 * @param end the upper bound of the range of segments experiments (not inclusive)
	 * @return the range of matching segments experiments
	 */
	@Override
	public List<SegmentsExperiment> findByS_C_C_S(
		long[] segmentsExperienceIds, long classNameId, long classPK,
		int[] statuses, int start, int end) {

		return findByS_C_C_S(
			segmentsExperienceIds, classNameId, classPK, statuses, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the segments experiments where segmentsExperienceId = any &#63; and classNameId = &#63; and classPK = &#63; and status = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperimentModelImpl</code>.
	 * </p>
	 *
	 * @param segmentsExperienceIds the segments experience IDs
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param statuses the statuses
	 * @param start the lower bound of the range of segments experiments
	 * @param end the upper bound of the range of segments experiments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching segments experiments
	 */
	@Override
	public List<SegmentsExperiment> findByS_C_C_S(
		long[] segmentsExperienceIds, long classNameId, long classPK,
		int[] statuses, int start, int end,
		OrderByComparator<SegmentsExperiment> orderByComparator) {

		return findByS_C_C_S(
			segmentsExperienceIds, classNameId, classPK, statuses, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the segments experiments where segmentsExperienceId = &#63; and classNameId = &#63; and classPK = &#63; and status = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperimentModelImpl</code>.
	 * </p>
	 *
	 * @param segmentsExperienceId the segments experience ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param status the status
	 * @param start the lower bound of the range of segments experiments
	 * @param end the upper bound of the range of segments experiments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching segments experiments
	 */
	@Override
	public List<SegmentsExperiment> findByS_C_C_S(
		long[] segmentsExperienceIds, long classNameId, long classPK,
		int[] statuses, int start, int end,
		OrderByComparator<SegmentsExperiment> orderByComparator,
		boolean useFinderCache) {

		if (segmentsExperienceIds == null) {
			segmentsExperienceIds = new long[0];
		}
		else if (segmentsExperienceIds.length > 1) {
			segmentsExperienceIds = ArrayUtil.sortedUnique(
				segmentsExperienceIds);
		}

		if (statuses == null) {
			statuses = new int[0];
		}
		else if (statuses.length > 1) {
			statuses = ArrayUtil.sortedUnique(statuses);
		}

		if ((segmentsExperienceIds.length == 1) && (statuses.length == 1)) {
			return findByS_C_C_S(
				segmentsExperienceIds[0], classNameId, classPK, statuses[0],
				start, end, orderByComparator);
		}

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			SegmentsExperiment.class);

		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderArgs = new Object[] {
					StringUtil.merge(segmentsExperienceIds), classNameId,
					classPK, StringUtil.merge(statuses)
				};
			}
		}
		else if (useFinderCache && productionMode) {
			finderArgs = new Object[] {
				StringUtil.merge(segmentsExperienceIds), classNameId, classPK,
				StringUtil.merge(statuses), start, end, orderByComparator
			};
		}

		List<SegmentsExperiment> list = null;

		if (useFinderCache && productionMode) {
			list = (List<SegmentsExperiment>)finderCache.getResult(
				_finderPathWithPaginationFindByS_C_C_S, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SegmentsExperiment segmentsExperiment : list) {
					if (!ArrayUtil.contains(
							segmentsExperienceIds,
							segmentsExperiment.getSegmentsExperienceId()) ||
						(classNameId != segmentsExperiment.getClassNameId()) ||
						(classPK != segmentsExperiment.getClassPK()) ||
						!ArrayUtil.contains(
							statuses, segmentsExperiment.getStatus())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_SELECT_SEGMENTSEXPERIMENT_WHERE);

			if (segmentsExperienceIds.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_S_C_C_S_SEGMENTSEXPERIENCEID_7);

				sb.append(StringUtil.merge(segmentsExperienceIds));

				sb.append(")");

				sb.append(")");

				sb.append(WHERE_AND);
			}

			sb.append(_FINDER_COLUMN_S_C_C_S_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_S_C_C_S_CLASSPK_2);

			if (statuses.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_S_C_C_S_STATUS_7);

				sb.append(StringUtil.merge(statuses));

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
				sb.append(SegmentsExperimentModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				list = (List<SegmentsExperiment>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					finderCache.putResult(
						_finderPathWithPaginationFindByS_C_C_S, finderArgs,
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
	 * Removes all the segments experiments where segmentsExperienceId = &#63; and classNameId = &#63; and classPK = &#63; and status = &#63; from the database.
	 *
	 * @param segmentsExperienceId the segments experience ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param status the status
	 */
	@Override
	public void removeByS_C_C_S(
		long segmentsExperienceId, long classNameId, long classPK, int status) {

		for (SegmentsExperiment segmentsExperiment :
				findByS_C_C_S(
					segmentsExperienceId, classNameId, classPK, status,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(segmentsExperiment);
		}
	}

	/**
	 * Returns the number of segments experiments where segmentsExperienceId = &#63; and classNameId = &#63; and classPK = &#63; and status = &#63;.
	 *
	 * @param segmentsExperienceId the segments experience ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param status the status
	 * @return the number of matching segments experiments
	 */
	@Override
	public int countByS_C_C_S(
		long segmentsExperienceId, long classNameId, long classPK, int status) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			SegmentsExperiment.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByS_C_C_S;

			finderArgs = new Object[] {
				segmentsExperienceId, classNameId, classPK, status
			};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_COUNT_SEGMENTSEXPERIMENT_WHERE);

			sb.append(_FINDER_COLUMN_S_C_C_S_SEGMENTSEXPERIENCEID_2);

			sb.append(_FINDER_COLUMN_S_C_C_S_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_S_C_C_S_CLASSPK_2);

			sb.append(_FINDER_COLUMN_S_C_C_S_STATUS_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(segmentsExperienceId);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				queryPos.add(status);

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
	 * Returns the number of segments experiments where segmentsExperienceId = any &#63; and classNameId = &#63; and classPK = &#63; and status = any &#63;.
	 *
	 * @param segmentsExperienceIds the segments experience IDs
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param statuses the statuses
	 * @return the number of matching segments experiments
	 */
	@Override
	public int countByS_C_C_S(
		long[] segmentsExperienceIds, long classNameId, long classPK,
		int[] statuses) {

		if (segmentsExperienceIds == null) {
			segmentsExperienceIds = new long[0];
		}
		else if (segmentsExperienceIds.length > 1) {
			segmentsExperienceIds = ArrayUtil.sortedUnique(
				segmentsExperienceIds);
		}

		if (statuses == null) {
			statuses = new int[0];
		}
		else if (statuses.length > 1) {
			statuses = ArrayUtil.sortedUnique(statuses);
		}

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			SegmentsExperiment.class);

		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderArgs = new Object[] {
				StringUtil.merge(segmentsExperienceIds), classNameId, classPK,
				StringUtil.merge(statuses)
			};

			count = (Long)finderCache.getResult(
				_finderPathWithPaginationCountByS_C_C_S, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_COUNT_SEGMENTSEXPERIMENT_WHERE);

			if (segmentsExperienceIds.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_S_C_C_S_SEGMENTSEXPERIENCEID_7);

				sb.append(StringUtil.merge(segmentsExperienceIds));

				sb.append(")");

				sb.append(")");

				sb.append(WHERE_AND);
			}

			sb.append(_FINDER_COLUMN_S_C_C_S_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_S_C_C_S_CLASSPK_2);

			if (statuses.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_S_C_C_S_STATUS_7);

				sb.append(StringUtil.merge(statuses));

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

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(
						_finderPathWithPaginationCountByS_C_C_S, finderArgs,
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

	private static final String _FINDER_COLUMN_S_C_C_S_SEGMENTSEXPERIENCEID_2 =
		"segmentsExperiment.segmentsExperienceId = ? AND ";

	private static final String _FINDER_COLUMN_S_C_C_S_SEGMENTSEXPERIENCEID_7 =
		"segmentsExperiment.segmentsExperienceId IN (";

	private static final String _FINDER_COLUMN_S_C_C_S_CLASSNAMEID_2 =
		"segmentsExperiment.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_S_C_C_S_CLASSPK_2 =
		"segmentsExperiment.classPK = ? AND ";

	private static final String _FINDER_COLUMN_S_C_C_S_STATUS_2 =
		"segmentsExperiment.status = ?";

	private static final String _FINDER_COLUMN_S_C_C_S_STATUS_7 =
		"segmentsExperiment.status IN (";

	public SegmentsExperimentPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(SegmentsExperiment.class);

		setModelImplClass(SegmentsExperimentImpl.class);
		setModelPKClass(long.class);

		setTable(SegmentsExperimentTable.INSTANCE);
	}

	/**
	 * Caches the segments experiment in the entity cache if it is enabled.
	 *
	 * @param segmentsExperiment the segments experiment
	 */
	@Override
	public void cacheResult(SegmentsExperiment segmentsExperiment) {
		if (segmentsExperiment.getCtCollectionId() != 0) {
			return;
		}

		entityCache.putResult(
			SegmentsExperimentImpl.class, segmentsExperiment.getPrimaryKey(),
			segmentsExperiment);

		finderCache.putResult(
			_finderPathFetchByUUID_G,
			new Object[] {
				segmentsExperiment.getUuid(), segmentsExperiment.getGroupId()
			},
			segmentsExperiment);

		finderCache.putResult(
			_finderPathFetchByG_S,
			new Object[] {
				segmentsExperiment.getGroupId(),
				segmentsExperiment.getSegmentsExperimentKey()
			},
			segmentsExperiment);
	}

	/**
	 * Caches the segments experiments in the entity cache if it is enabled.
	 *
	 * @param segmentsExperiments the segments experiments
	 */
	@Override
	public void cacheResult(List<SegmentsExperiment> segmentsExperiments) {
		for (SegmentsExperiment segmentsExperiment : segmentsExperiments) {
			if (segmentsExperiment.getCtCollectionId() != 0) {
				continue;
			}

			if (entityCache.getResult(
					SegmentsExperimentImpl.class,
					segmentsExperiment.getPrimaryKey()) == null) {

				cacheResult(segmentsExperiment);
			}
		}
	}

	/**
	 * Clears the cache for all segments experiments.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(SegmentsExperimentImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the segments experiment.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(SegmentsExperiment segmentsExperiment) {
		entityCache.removeResult(
			SegmentsExperimentImpl.class, segmentsExperiment.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(SegmentsExperimentModelImpl)segmentsExperiment, true);
	}

	@Override
	public void clearCache(List<SegmentsExperiment> segmentsExperiments) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (SegmentsExperiment segmentsExperiment : segmentsExperiments) {
			entityCache.removeResult(
				SegmentsExperimentImpl.class,
				segmentsExperiment.getPrimaryKey());

			clearUniqueFindersCache(
				(SegmentsExperimentModelImpl)segmentsExperiment, true);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(SegmentsExperimentImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		SegmentsExperimentModelImpl segmentsExperimentModelImpl) {

		Object[] args = new Object[] {
			segmentsExperimentModelImpl.getUuid(),
			segmentsExperimentModelImpl.getGroupId()
		};

		finderCache.putResult(
			_finderPathCountByUUID_G, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByUUID_G, args, segmentsExperimentModelImpl, false);

		args = new Object[] {
			segmentsExperimentModelImpl.getGroupId(),
			segmentsExperimentModelImpl.getSegmentsExperimentKey()
		};

		finderCache.putResult(
			_finderPathCountByG_S, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByG_S, args, segmentsExperimentModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		SegmentsExperimentModelImpl segmentsExperimentModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				segmentsExperimentModelImpl.getUuid(),
				segmentsExperimentModelImpl.getGroupId()
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(_finderPathFetchByUUID_G, args);
		}

		if ((segmentsExperimentModelImpl.getColumnBitmask() &
			 _finderPathFetchByUUID_G.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				segmentsExperimentModelImpl.getColumnOriginalValue("uuid_"),
				segmentsExperimentModelImpl.getColumnOriginalValue("groupId")
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(_finderPathFetchByUUID_G, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
				segmentsExperimentModelImpl.getGroupId(),
				segmentsExperimentModelImpl.getSegmentsExperimentKey()
			};

			finderCache.removeResult(_finderPathCountByG_S, args);
			finderCache.removeResult(_finderPathFetchByG_S, args);
		}

		if ((segmentsExperimentModelImpl.getColumnBitmask() &
			 _finderPathFetchByG_S.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				segmentsExperimentModelImpl.getColumnOriginalValue("groupId"),
				segmentsExperimentModelImpl.getColumnOriginalValue(
					"segmentsExperimentKey")
			};

			finderCache.removeResult(_finderPathCountByG_S, args);
			finderCache.removeResult(_finderPathFetchByG_S, args);
		}
	}

	/**
	 * Creates a new segments experiment with the primary key. Does not add the segments experiment to the database.
	 *
	 * @param segmentsExperimentId the primary key for the new segments experiment
	 * @return the new segments experiment
	 */
	@Override
	public SegmentsExperiment create(long segmentsExperimentId) {
		SegmentsExperiment segmentsExperiment = new SegmentsExperimentImpl();

		segmentsExperiment.setNew(true);
		segmentsExperiment.setPrimaryKey(segmentsExperimentId);

		String uuid = PortalUUIDUtil.generate();

		segmentsExperiment.setUuid(uuid);

		segmentsExperiment.setCompanyId(CompanyThreadLocal.getCompanyId());

		return segmentsExperiment;
	}

	/**
	 * Removes the segments experiment with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param segmentsExperimentId the primary key of the segments experiment
	 * @return the segments experiment that was removed
	 * @throws NoSuchExperimentException if a segments experiment with the primary key could not be found
	 */
	@Override
	public SegmentsExperiment remove(long segmentsExperimentId)
		throws NoSuchExperimentException {

		return remove((Serializable)segmentsExperimentId);
	}

	/**
	 * Removes the segments experiment with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the segments experiment
	 * @return the segments experiment that was removed
	 * @throws NoSuchExperimentException if a segments experiment with the primary key could not be found
	 */
	@Override
	public SegmentsExperiment remove(Serializable primaryKey)
		throws NoSuchExperimentException {

		Session session = null;

		try {
			session = openSession();

			SegmentsExperiment segmentsExperiment =
				(SegmentsExperiment)session.get(
					SegmentsExperimentImpl.class, primaryKey);

			if (segmentsExperiment == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchExperimentException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(segmentsExperiment);
		}
		catch (NoSuchExperimentException noSuchEntityException) {
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
	protected SegmentsExperiment removeImpl(
		SegmentsExperiment segmentsExperiment) {

		if (!ctPersistenceHelper.isRemove(segmentsExperiment)) {
			return segmentsExperiment;
		}

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(segmentsExperiment)) {
				segmentsExperiment = (SegmentsExperiment)session.get(
					SegmentsExperimentImpl.class,
					segmentsExperiment.getPrimaryKeyObj());
			}

			if (segmentsExperiment != null) {
				session.delete(segmentsExperiment);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (segmentsExperiment != null) {
			clearCache(segmentsExperiment);
		}

		return segmentsExperiment;
	}

	@Override
	public SegmentsExperiment updateImpl(
		SegmentsExperiment segmentsExperiment) {

		boolean isNew = segmentsExperiment.isNew();

		if (!(segmentsExperiment instanceof SegmentsExperimentModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(segmentsExperiment.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					segmentsExperiment);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in segmentsExperiment proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom SegmentsExperiment implementation " +
					segmentsExperiment.getClass());
		}

		SegmentsExperimentModelImpl segmentsExperimentModelImpl =
			(SegmentsExperimentModelImpl)segmentsExperiment;

		if (Validator.isNull(segmentsExperiment.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			segmentsExperiment.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (segmentsExperiment.getCreateDate() == null)) {
			if (serviceContext == null) {
				segmentsExperiment.setCreateDate(now);
			}
			else {
				segmentsExperiment.setCreateDate(
					serviceContext.getCreateDate(now));
			}
		}

		if (!segmentsExperimentModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				segmentsExperiment.setModifiedDate(now);
			}
			else {
				segmentsExperiment.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(segmentsExperiment)) {
				if (!isNew) {
					session.evict(
						SegmentsExperimentImpl.class,
						segmentsExperiment.getPrimaryKeyObj());
				}

				session.save(segmentsExperiment);

				segmentsExperiment.setNew(false);
			}
			else {
				segmentsExperiment = (SegmentsExperiment)session.merge(
					segmentsExperiment);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (segmentsExperiment.getCtCollectionId() != 0) {
			segmentsExperiment.resetOriginalValues();

			return segmentsExperiment;
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew) {
			Object[] args = new Object[] {
				segmentsExperimentModelImpl.getUuid()
			};

			finderCache.removeResult(_finderPathCountByUuid, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid, args);

			args = new Object[] {
				segmentsExperimentModelImpl.getUuid(),
				segmentsExperimentModelImpl.getCompanyId()
			};

			finderCache.removeResult(_finderPathCountByUuid_C, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid_C, args);

			args = new Object[] {segmentsExperimentModelImpl.getGroupId()};

			finderCache.removeResult(_finderPathCountByGroupId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByGroupId, args);

			args = new Object[] {
				segmentsExperimentModelImpl.getSegmentsExperimentKey()
			};

			finderCache.removeResult(
				_finderPathCountBySegmentsExperimentKey, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindBySegmentsExperimentKey, args);

			args = new Object[] {
				segmentsExperimentModelImpl.getGroupId(),
				segmentsExperimentModelImpl.getClassNameId(),
				segmentsExperimentModelImpl.getClassPK()
			};

			finderCache.removeResult(_finderPathCountByG_C_C, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByG_C_C, args);

			args = new Object[] {
				segmentsExperimentModelImpl.getSegmentsExperienceId(),
				segmentsExperimentModelImpl.getClassNameId(),
				segmentsExperimentModelImpl.getClassPK()
			};

			finderCache.removeResult(_finderPathCountByS_C_C, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByS_C_C, args);

			args = new Object[] {
				segmentsExperimentModelImpl.getSegmentsExperienceId(),
				segmentsExperimentModelImpl.getClassNameId(),
				segmentsExperimentModelImpl.getClassPK(),
				segmentsExperimentModelImpl.getStatus()
			};

			finderCache.removeResult(_finderPathCountByS_C_C_S, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByS_C_C_S, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((segmentsExperimentModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					segmentsExperimentModelImpl.getColumnOriginalValue("uuid_")
				};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);

				args = new Object[] {segmentsExperimentModelImpl.getUuid()};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);
			}

			if ((segmentsExperimentModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					segmentsExperimentModelImpl.getColumnOriginalValue("uuid_"),
					segmentsExperimentModelImpl.getColumnOriginalValue(
						"companyId")
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);

				args = new Object[] {
					segmentsExperimentModelImpl.getUuid(),
					segmentsExperimentModelImpl.getCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);
			}

			if ((segmentsExperimentModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByGroupId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					segmentsExperimentModelImpl.getColumnOriginalValue(
						"groupId")
				};

				finderCache.removeResult(_finderPathCountByGroupId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByGroupId, args);

				args = new Object[] {segmentsExperimentModelImpl.getGroupId()};

				finderCache.removeResult(_finderPathCountByGroupId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByGroupId, args);
			}

			if ((segmentsExperimentModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindBySegmentsExperimentKey.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					segmentsExperimentModelImpl.getColumnOriginalValue(
						"segmentsExperimentKey")
				};

				finderCache.removeResult(
					_finderPathCountBySegmentsExperimentKey, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindBySegmentsExperimentKey,
					args);

				args = new Object[] {
					segmentsExperimentModelImpl.getSegmentsExperimentKey()
				};

				finderCache.removeResult(
					_finderPathCountBySegmentsExperimentKey, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindBySegmentsExperimentKey,
					args);
			}

			if ((segmentsExperimentModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_C_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					segmentsExperimentModelImpl.getColumnOriginalValue(
						"groupId"),
					segmentsExperimentModelImpl.getColumnOriginalValue(
						"classNameId"),
					segmentsExperimentModelImpl.getColumnOriginalValue(
						"classPK")
				};

				finderCache.removeResult(_finderPathCountByG_C_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_C_C, args);

				args = new Object[] {
					segmentsExperimentModelImpl.getGroupId(),
					segmentsExperimentModelImpl.getClassNameId(),
					segmentsExperimentModelImpl.getClassPK()
				};

				finderCache.removeResult(_finderPathCountByG_C_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_C_C, args);
			}

			if ((segmentsExperimentModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByS_C_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					segmentsExperimentModelImpl.getColumnOriginalValue(
						"segmentsExperienceId"),
					segmentsExperimentModelImpl.getColumnOriginalValue(
						"classNameId"),
					segmentsExperimentModelImpl.getColumnOriginalValue(
						"classPK")
				};

				finderCache.removeResult(_finderPathCountByS_C_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByS_C_C, args);

				args = new Object[] {
					segmentsExperimentModelImpl.getSegmentsExperienceId(),
					segmentsExperimentModelImpl.getClassNameId(),
					segmentsExperimentModelImpl.getClassPK()
				};

				finderCache.removeResult(_finderPathCountByS_C_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByS_C_C, args);
			}

			if ((segmentsExperimentModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByS_C_C_S.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					segmentsExperimentModelImpl.getColumnOriginalValue(
						"segmentsExperienceId"),
					segmentsExperimentModelImpl.getColumnOriginalValue(
						"classNameId"),
					segmentsExperimentModelImpl.getColumnOriginalValue(
						"classPK"),
					segmentsExperimentModelImpl.getColumnOriginalValue("status")
				};

				finderCache.removeResult(_finderPathCountByS_C_C_S, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByS_C_C_S, args);

				args = new Object[] {
					segmentsExperimentModelImpl.getSegmentsExperienceId(),
					segmentsExperimentModelImpl.getClassNameId(),
					segmentsExperimentModelImpl.getClassPK(),
					segmentsExperimentModelImpl.getStatus()
				};

				finderCache.removeResult(_finderPathCountByS_C_C_S, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByS_C_C_S, args);
			}
		}

		entityCache.putResult(
			SegmentsExperimentImpl.class, segmentsExperiment.getPrimaryKey(),
			segmentsExperiment, false);

		clearUniqueFindersCache(segmentsExperimentModelImpl, false);
		cacheUniqueFindersCache(segmentsExperimentModelImpl);

		segmentsExperiment.resetOriginalValues();

		return segmentsExperiment;
	}

	/**
	 * Returns the segments experiment with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the segments experiment
	 * @return the segments experiment
	 * @throws NoSuchExperimentException if a segments experiment with the primary key could not be found
	 */
	@Override
	public SegmentsExperiment findByPrimaryKey(Serializable primaryKey)
		throws NoSuchExperimentException {

		SegmentsExperiment segmentsExperiment = fetchByPrimaryKey(primaryKey);

		if (segmentsExperiment == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchExperimentException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return segmentsExperiment;
	}

	/**
	 * Returns the segments experiment with the primary key or throws a <code>NoSuchExperimentException</code> if it could not be found.
	 *
	 * @param segmentsExperimentId the primary key of the segments experiment
	 * @return the segments experiment
	 * @throws NoSuchExperimentException if a segments experiment with the primary key could not be found
	 */
	@Override
	public SegmentsExperiment findByPrimaryKey(long segmentsExperimentId)
		throws NoSuchExperimentException {

		return findByPrimaryKey((Serializable)segmentsExperimentId);
	}

	/**
	 * Returns the segments experiment with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the segments experiment
	 * @return the segments experiment, or <code>null</code> if a segments experiment with the primary key could not be found
	 */
	@Override
	public SegmentsExperiment fetchByPrimaryKey(Serializable primaryKey) {
		if (ctPersistenceHelper.isProductionMode(SegmentsExperiment.class)) {
			return super.fetchByPrimaryKey(primaryKey);
		}

		SegmentsExperiment segmentsExperiment = null;

		Session session = null;

		try {
			session = openSession();

			segmentsExperiment = (SegmentsExperiment)session.get(
				SegmentsExperimentImpl.class, primaryKey);

			if (segmentsExperiment != null) {
				cacheResult(segmentsExperiment);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return segmentsExperiment;
	}

	/**
	 * Returns the segments experiment with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param segmentsExperimentId the primary key of the segments experiment
	 * @return the segments experiment, or <code>null</code> if a segments experiment with the primary key could not be found
	 */
	@Override
	public SegmentsExperiment fetchByPrimaryKey(long segmentsExperimentId) {
		return fetchByPrimaryKey((Serializable)segmentsExperimentId);
	}

	@Override
	public Map<Serializable, SegmentsExperiment> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (ctPersistenceHelper.isProductionMode(SegmentsExperiment.class)) {
			return super.fetchByPrimaryKeys(primaryKeys);
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, SegmentsExperiment> map =
			new HashMap<Serializable, SegmentsExperiment>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			SegmentsExperiment segmentsExperiment = fetchByPrimaryKey(
				primaryKey);

			if (segmentsExperiment != null) {
				map.put(primaryKey, segmentsExperiment);
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

			for (SegmentsExperiment segmentsExperiment :
					(List<SegmentsExperiment>)query.list()) {

				map.put(
					segmentsExperiment.getPrimaryKeyObj(), segmentsExperiment);

				cacheResult(segmentsExperiment);
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
	 * Returns all the segments experiments.
	 *
	 * @return the segments experiments
	 */
	@Override
	public List<SegmentsExperiment> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the segments experiments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperimentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of segments experiments
	 * @param end the upper bound of the range of segments experiments (not inclusive)
	 * @return the range of segments experiments
	 */
	@Override
	public List<SegmentsExperiment> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the segments experiments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperimentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of segments experiments
	 * @param end the upper bound of the range of segments experiments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of segments experiments
	 */
	@Override
	public List<SegmentsExperiment> findAll(
		int start, int end,
		OrderByComparator<SegmentsExperiment> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the segments experiments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SegmentsExperimentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of segments experiments
	 * @param end the upper bound of the range of segments experiments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of segments experiments
	 */
	@Override
	public List<SegmentsExperiment> findAll(
		int start, int end,
		OrderByComparator<SegmentsExperiment> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			SegmentsExperiment.class);

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

		List<SegmentsExperiment> list = null;

		if (useFinderCache && productionMode) {
			list = (List<SegmentsExperiment>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_SEGMENTSEXPERIMENT);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_SEGMENTSEXPERIMENT;

				sql = sql.concat(SegmentsExperimentModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<SegmentsExperiment>)QueryUtil.list(
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
	 * Removes all the segments experiments from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (SegmentsExperiment segmentsExperiment : findAll()) {
			remove(segmentsExperiment);
		}
	}

	/**
	 * Returns the number of segments experiments.
	 *
	 * @return the number of segments experiments
	 */
	@Override
	public int countAll() {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			SegmentsExperiment.class);

		Long count = null;

		if (productionMode) {
			count = (Long)finderCache.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY, this);
		}

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(
					_SQL_COUNT_SEGMENTSEXPERIMENT);

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
		return "segmentsExperimentId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_SEGMENTSEXPERIMENT;
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
		return SegmentsExperimentModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "SegmentsExperiment";
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
		ctStrictColumnNames.add("segmentsEntryId");
		ctStrictColumnNames.add("segmentsExperienceId");
		ctStrictColumnNames.add("segmentsExperimentKey");
		ctStrictColumnNames.add("classNameId");
		ctStrictColumnNames.add("classPK");
		ctStrictColumnNames.add("name");
		ctStrictColumnNames.add("description");
		ctStrictColumnNames.add("typeSettings");
		ctStrictColumnNames.add("status");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK,
			Collections.singleton("segmentsExperimentId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(new String[] {"uuid_", "groupId"});

		_uniqueIndexColumnNames.add(
			new String[] {"groupId", "segmentsExperimentKey"});
	}

	/**
	 * Initializes the segments experiment persistence.
	 */
	@Activate
	public void activate() {
		_finderPathWithPaginationFindAll = new FinderPath(
			SegmentsExperimentImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			SegmentsExperimentImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByUuid = new FinderPath(
			SegmentsExperimentImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			SegmentsExperimentImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] {String.class.getName()},
			SegmentsExperimentModelImpl.getColumnBitmask("uuid_") |
			SegmentsExperimentModelImpl.getColumnBitmask("createDate"));

		_finderPathCountByUuid = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid", new String[] {String.class.getName()});

		_finderPathFetchByUUID_G = new FinderPath(
			SegmentsExperimentImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			SegmentsExperimentModelImpl.getColumnBitmask("uuid_") |
			SegmentsExperimentModelImpl.getColumnBitmask("groupId"));

		_finderPathCountByUUID_G = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			SegmentsExperimentImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			SegmentsExperimentImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			SegmentsExperimentModelImpl.getColumnBitmask("uuid_") |
			SegmentsExperimentModelImpl.getColumnBitmask("companyId") |
			SegmentsExperimentModelImpl.getColumnBitmask("createDate"));

		_finderPathCountByUuid_C = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByGroupId = new FinderPath(
			SegmentsExperimentImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByGroupId = new FinderPath(
			SegmentsExperimentImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] {Long.class.getName()},
			SegmentsExperimentModelImpl.getColumnBitmask("groupId") |
			SegmentsExperimentModelImpl.getColumnBitmask("createDate"));

		_finderPathCountByGroupId = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByGroupId", new String[] {Long.class.getName()});

		_finderPathWithPaginationFindBySegmentsExperimentKey = new FinderPath(
			SegmentsExperimentImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findBySegmentsExperimentKey",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindBySegmentsExperimentKey =
			new FinderPath(
				SegmentsExperimentImpl.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findBySegmentsExperimentKey",
				new String[] {String.class.getName()},
				SegmentsExperimentModelImpl.getColumnBitmask(
					"segmentsExperimentKey") |
				SegmentsExperimentModelImpl.getColumnBitmask("createDate"));

		_finderPathCountBySegmentsExperimentKey = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countBySegmentsExperimentKey",
			new String[] {String.class.getName()});

		_finderPathFetchByG_S = new FinderPath(
			SegmentsExperimentImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByG_S",
			new String[] {Long.class.getName(), String.class.getName()},
			SegmentsExperimentModelImpl.getColumnBitmask("groupId") |
			SegmentsExperimentModelImpl.getColumnBitmask(
				"segmentsExperimentKey"));

		_finderPathCountByG_S = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_S",
			new String[] {Long.class.getName(), String.class.getName()});

		_finderPathWithPaginationFindByG_C_C = new FinderPath(
			SegmentsExperimentImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_C_C = new FinderPath(
			SegmentsExperimentImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			SegmentsExperimentModelImpl.getColumnBitmask("groupId") |
			SegmentsExperimentModelImpl.getColumnBitmask("classNameId") |
			SegmentsExperimentModelImpl.getColumnBitmask("classPK") |
			SegmentsExperimentModelImpl.getColumnBitmask("createDate"));

		_finderPathCountByG_C_C = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});

		_finderPathWithPaginationFindByS_C_C = new FinderPath(
			SegmentsExperimentImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByS_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByS_C_C = new FinderPath(
			SegmentsExperimentImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByS_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			SegmentsExperimentModelImpl.getColumnBitmask(
				"segmentsExperienceId") |
			SegmentsExperimentModelImpl.getColumnBitmask("classNameId") |
			SegmentsExperimentModelImpl.getColumnBitmask("classPK") |
			SegmentsExperimentModelImpl.getColumnBitmask("createDate"));

		_finderPathCountByS_C_C = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByS_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});

		_finderPathWithPaginationFindByS_C_C_S = new FinderPath(
			SegmentsExperimentImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByS_C_C_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByS_C_C_S = new FinderPath(
			SegmentsExperimentImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByS_C_C_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName()
			},
			SegmentsExperimentModelImpl.getColumnBitmask(
				"segmentsExperienceId") |
			SegmentsExperimentModelImpl.getColumnBitmask("classNameId") |
			SegmentsExperimentModelImpl.getColumnBitmask("classPK") |
			SegmentsExperimentModelImpl.getColumnBitmask("status") |
			SegmentsExperimentModelImpl.getColumnBitmask("createDate"));

		_finderPathCountByS_C_C_S = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByS_C_C_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName()
			});

		_finderPathWithPaginationCountByS_C_C_S = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"countByS_C_C_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName()
			});
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(SegmentsExperimentImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	@Reference(
		target = SegmentsPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = SegmentsPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = SegmentsPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
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

	private static final String _SQL_SELECT_SEGMENTSEXPERIMENT =
		"SELECT segmentsExperiment FROM SegmentsExperiment segmentsExperiment";

	private static final String _SQL_SELECT_SEGMENTSEXPERIMENT_WHERE =
		"SELECT segmentsExperiment FROM SegmentsExperiment segmentsExperiment WHERE ";

	private static final String _SQL_COUNT_SEGMENTSEXPERIMENT =
		"SELECT COUNT(segmentsExperiment) FROM SegmentsExperiment segmentsExperiment";

	private static final String _SQL_COUNT_SEGMENTSEXPERIMENT_WHERE =
		"SELECT COUNT(segmentsExperiment) FROM SegmentsExperiment segmentsExperiment WHERE ";

	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN =
		"segmentsExperiment.segmentsExperimentId";

	private static final String _FILTER_SQL_SELECT_SEGMENTSEXPERIMENT_WHERE =
		"SELECT DISTINCT {segmentsExperiment.*} FROM SegmentsExperiment segmentsExperiment WHERE ";

	private static final String
		_FILTER_SQL_SELECT_SEGMENTSEXPERIMENT_NO_INLINE_DISTINCT_WHERE_1 =
			"SELECT {SegmentsExperiment.*} FROM (SELECT DISTINCT segmentsExperiment.segmentsExperimentId FROM SegmentsExperiment segmentsExperiment WHERE ";

	private static final String
		_FILTER_SQL_SELECT_SEGMENTSEXPERIMENT_NO_INLINE_DISTINCT_WHERE_2 =
			") TEMP_TABLE INNER JOIN SegmentsExperiment ON TEMP_TABLE.segmentsExperimentId = SegmentsExperiment.segmentsExperimentId";

	private static final String _FILTER_SQL_COUNT_SEGMENTSEXPERIMENT_WHERE =
		"SELECT COUNT(DISTINCT segmentsExperiment.segmentsExperimentId) AS COUNT_VALUE FROM SegmentsExperiment segmentsExperiment WHERE ";

	private static final String _FILTER_ENTITY_ALIAS = "segmentsExperiment";

	private static final String _FILTER_ENTITY_TABLE = "SegmentsExperiment";

	private static final String _ORDER_BY_ENTITY_ALIAS = "segmentsExperiment.";

	private static final String _ORDER_BY_ENTITY_TABLE = "SegmentsExperiment.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No SegmentsExperiment exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No SegmentsExperiment exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		SegmentsExperimentPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	static {
		try {
			Class.forName(SegmentsPersistenceConstants.class.getName());
		}
		catch (ClassNotFoundException classNotFoundException) {
			throw new ExceptionInInitializerError(classNotFoundException);
		}
	}

}