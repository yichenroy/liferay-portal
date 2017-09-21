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

package com.liferay.commerce.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.exception.NoSuchCPDefinitionAvailabilityRangeException;
import com.liferay.commerce.model.CPDefinitionAvailabilityRange;
import com.liferay.commerce.model.impl.CPDefinitionAvailabilityRangeImpl;
import com.liferay.commerce.model.impl.CPDefinitionAvailabilityRangeModelImpl;
import com.liferay.commerce.service.persistence.CPDefinitionAvailabilityRangePersistence;

import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.CompanyProvider;
import com.liferay.portal.kernel.service.persistence.CompanyProviderWrapper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.Field;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the cp definition availability range service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CPDefinitionAvailabilityRangePersistence
 * @see com.liferay.commerce.service.persistence.CPDefinitionAvailabilityRangeUtil
 * @generated
 */
@ProviderType
public class CPDefinitionAvailabilityRangePersistenceImpl
	extends BasePersistenceImpl<CPDefinitionAvailabilityRange>
	implements CPDefinitionAvailabilityRangePersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CPDefinitionAvailabilityRangeUtil} to access the cp definition availability range persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CPDefinitionAvailabilityRangeImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CPDefinitionAvailabilityRangeModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionAvailabilityRangeModelImpl.FINDER_CACHE_ENABLED,
			CPDefinitionAvailabilityRangeImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CPDefinitionAvailabilityRangeModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionAvailabilityRangeModelImpl.FINDER_CACHE_ENABLED,
			CPDefinitionAvailabilityRangeImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CPDefinitionAvailabilityRangeModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionAvailabilityRangeModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(CPDefinitionAvailabilityRangeModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionAvailabilityRangeModelImpl.FINDER_CACHE_ENABLED,
			CPDefinitionAvailabilityRangeImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(CPDefinitionAvailabilityRangeModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionAvailabilityRangeModelImpl.FINDER_CACHE_ENABLED,
			CPDefinitionAvailabilityRangeImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			CPDefinitionAvailabilityRangeModelImpl.UUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(CPDefinitionAvailabilityRangeModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionAvailabilityRangeModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid", new String[] { String.class.getName() });

	/**
	 * Returns all the cp definition availability ranges where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching cp definition availability ranges
	 */
	@Override
	public List<CPDefinitionAvailabilityRange> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definition availability ranges where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionAvailabilityRangeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp definition availability ranges
	 * @param end the upper bound of the range of cp definition availability ranges (not inclusive)
	 * @return the range of matching cp definition availability ranges
	 */
	@Override
	public List<CPDefinitionAvailabilityRange> findByUuid(String uuid,
		int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definition availability ranges where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionAvailabilityRangeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp definition availability ranges
	 * @param end the upper bound of the range of cp definition availability ranges (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definition availability ranges
	 */
	@Override
	public List<CPDefinitionAvailabilityRange> findByUuid(String uuid,
		int start, int end,
		OrderByComparator<CPDefinitionAvailabilityRange> orderByComparator) {
		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definition availability ranges where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionAvailabilityRangeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp definition availability ranges
	 * @param end the upper bound of the range of cp definition availability ranges (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching cp definition availability ranges
	 */
	@Override
	public List<CPDefinitionAvailabilityRange> findByUuid(String uuid,
		int start, int end,
		OrderByComparator<CPDefinitionAvailabilityRange> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID;
			finderArgs = new Object[] { uuid };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID;
			finderArgs = new Object[] { uuid, start, end, orderByComparator };
		}

		List<CPDefinitionAvailabilityRange> list = null;

		if (retrieveFromCache) {
			list = (List<CPDefinitionAvailabilityRange>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CPDefinitionAvailabilityRange cpDefinitionAvailabilityRange : list) {
					if (!Objects.equals(uuid,
								cpDefinitionAvailabilityRange.getUuid())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_CPDEFINITIONAVAILABILITYRANGE_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CPDefinitionAvailabilityRangeModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				if (!pagination) {
					list = (List<CPDefinitionAvailabilityRange>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CPDefinitionAvailabilityRange>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first cp definition availability range in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition availability range
	 * @throws NoSuchCPDefinitionAvailabilityRangeException if a matching cp definition availability range could not be found
	 */
	@Override
	public CPDefinitionAvailabilityRange findByUuid_First(String uuid,
		OrderByComparator<CPDefinitionAvailabilityRange> orderByComparator)
		throws NoSuchCPDefinitionAvailabilityRangeException {
		CPDefinitionAvailabilityRange cpDefinitionAvailabilityRange = fetchByUuid_First(uuid,
				orderByComparator);

		if (cpDefinitionAvailabilityRange != null) {
			return cpDefinitionAvailabilityRange;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCPDefinitionAvailabilityRangeException(msg.toString());
	}

	/**
	 * Returns the first cp definition availability range in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition availability range, or <code>null</code> if a matching cp definition availability range could not be found
	 */
	@Override
	public CPDefinitionAvailabilityRange fetchByUuid_First(String uuid,
		OrderByComparator<CPDefinitionAvailabilityRange> orderByComparator) {
		List<CPDefinitionAvailabilityRange> list = findByUuid(uuid, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp definition availability range in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition availability range
	 * @throws NoSuchCPDefinitionAvailabilityRangeException if a matching cp definition availability range could not be found
	 */
	@Override
	public CPDefinitionAvailabilityRange findByUuid_Last(String uuid,
		OrderByComparator<CPDefinitionAvailabilityRange> orderByComparator)
		throws NoSuchCPDefinitionAvailabilityRangeException {
		CPDefinitionAvailabilityRange cpDefinitionAvailabilityRange = fetchByUuid_Last(uuid,
				orderByComparator);

		if (cpDefinitionAvailabilityRange != null) {
			return cpDefinitionAvailabilityRange;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCPDefinitionAvailabilityRangeException(msg.toString());
	}

	/**
	 * Returns the last cp definition availability range in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition availability range, or <code>null</code> if a matching cp definition availability range could not be found
	 */
	@Override
	public CPDefinitionAvailabilityRange fetchByUuid_Last(String uuid,
		OrderByComparator<CPDefinitionAvailabilityRange> orderByComparator) {
		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<CPDefinitionAvailabilityRange> list = findByUuid(uuid, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp definition availability ranges before and after the current cp definition availability range in the ordered set where uuid = &#63;.
	 *
	 * @param CPDefinitionAvailabilityRangeId the primary key of the current cp definition availability range
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp definition availability range
	 * @throws NoSuchCPDefinitionAvailabilityRangeException if a cp definition availability range with the primary key could not be found
	 */
	@Override
	public CPDefinitionAvailabilityRange[] findByUuid_PrevAndNext(
		long CPDefinitionAvailabilityRangeId, String uuid,
		OrderByComparator<CPDefinitionAvailabilityRange> orderByComparator)
		throws NoSuchCPDefinitionAvailabilityRangeException {
		CPDefinitionAvailabilityRange cpDefinitionAvailabilityRange = findByPrimaryKey(CPDefinitionAvailabilityRangeId);

		Session session = null;

		try {
			session = openSession();

			CPDefinitionAvailabilityRange[] array = new CPDefinitionAvailabilityRangeImpl[3];

			array[0] = getByUuid_PrevAndNext(session,
					cpDefinitionAvailabilityRange, uuid, orderByComparator, true);

			array[1] = cpDefinitionAvailabilityRange;

			array[2] = getByUuid_PrevAndNext(session,
					cpDefinitionAvailabilityRange, uuid, orderByComparator,
					false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CPDefinitionAvailabilityRange getByUuid_PrevAndNext(
		Session session,
		CPDefinitionAvailabilityRange cpDefinitionAvailabilityRange,
		String uuid,
		OrderByComparator<CPDefinitionAvailabilityRange> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_CPDEFINITIONAVAILABILITYRANGE_WHERE);

		boolean bindUuid = false;

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_UUID_1);
		}
		else if (uuid.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_UUID_UUID_3);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_UUID_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(CPDefinitionAvailabilityRangeModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindUuid) {
			qPos.add(uuid);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(cpDefinitionAvailabilityRange);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CPDefinitionAvailabilityRange> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp definition availability ranges where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (CPDefinitionAvailabilityRange cpDefinitionAvailabilityRange : findByUuid(
				uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(cpDefinitionAvailabilityRange);
		}
	}

	/**
	 * Returns the number of cp definition availability ranges where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching cp definition availability ranges
	 */
	@Override
	public int countByUuid(String uuid) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID;

		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_CPDEFINITIONAVAILABILITYRANGE_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_UUID_1 = "cpDefinitionAvailabilityRange.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "cpDefinitionAvailabilityRange.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(cpDefinitionAvailabilityRange.uuid IS NULL OR cpDefinitionAvailabilityRange.uuid = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(CPDefinitionAvailabilityRangeModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionAvailabilityRangeModelImpl.FINDER_CACHE_ENABLED,
			CPDefinitionAvailabilityRangeImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() },
			CPDefinitionAvailabilityRangeModelImpl.UUID_COLUMN_BITMASK |
			CPDefinitionAvailabilityRangeModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(CPDefinitionAvailabilityRangeModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionAvailabilityRangeModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns the cp definition availability range where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchCPDefinitionAvailabilityRangeException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching cp definition availability range
	 * @throws NoSuchCPDefinitionAvailabilityRangeException if a matching cp definition availability range could not be found
	 */
	@Override
	public CPDefinitionAvailabilityRange findByUUID_G(String uuid, long groupId)
		throws NoSuchCPDefinitionAvailabilityRangeException {
		CPDefinitionAvailabilityRange cpDefinitionAvailabilityRange = fetchByUUID_G(uuid,
				groupId);

		if (cpDefinitionAvailabilityRange == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(", groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchCPDefinitionAvailabilityRangeException(msg.toString());
		}

		return cpDefinitionAvailabilityRange;
	}

	/**
	 * Returns the cp definition availability range where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching cp definition availability range, or <code>null</code> if a matching cp definition availability range could not be found
	 */
	@Override
	public CPDefinitionAvailabilityRange fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the cp definition availability range where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching cp definition availability range, or <code>null</code> if a matching cp definition availability range could not be found
	 */
	@Override
	public CPDefinitionAvailabilityRange fetchByUUID_G(String uuid,
		long groupId, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result instanceof CPDefinitionAvailabilityRange) {
			CPDefinitionAvailabilityRange cpDefinitionAvailabilityRange = (CPDefinitionAvailabilityRange)result;

			if (!Objects.equals(uuid, cpDefinitionAvailabilityRange.getUuid()) ||
					(groupId != cpDefinitionAvailabilityRange.getGroupId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_CPDEFINITIONAVAILABILITYRANGE_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_G_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				List<CPDefinitionAvailabilityRange> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					CPDefinitionAvailabilityRange cpDefinitionAvailabilityRange = list.get(0);

					result = cpDefinitionAvailabilityRange;

					cacheResult(cpDefinitionAvailabilityRange);

					if ((cpDefinitionAvailabilityRange.getUuid() == null) ||
							!cpDefinitionAvailabilityRange.getUuid().equals(uuid) ||
							(cpDefinitionAvailabilityRange.getGroupId() != groupId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, cpDefinitionAvailabilityRange);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (CPDefinitionAvailabilityRange)result;
		}
	}

	/**
	 * Removes the cp definition availability range where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the cp definition availability range that was removed
	 */
	@Override
	public CPDefinitionAvailabilityRange removeByUUID_G(String uuid,
		long groupId) throws NoSuchCPDefinitionAvailabilityRangeException {
		CPDefinitionAvailabilityRange cpDefinitionAvailabilityRange = findByUUID_G(uuid,
				groupId);

		return remove(cpDefinitionAvailabilityRange);
	}

	/**
	 * Returns the number of cp definition availability ranges where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching cp definition availability ranges
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_G;

		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_CPDEFINITIONAVAILABILITYRANGE_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_G_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "cpDefinitionAvailabilityRange.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "cpDefinitionAvailabilityRange.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(cpDefinitionAvailabilityRange.uuid IS NULL OR cpDefinitionAvailabilityRange.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "cpDefinitionAvailabilityRange.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(CPDefinitionAvailabilityRangeModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionAvailabilityRangeModelImpl.FINDER_CACHE_ENABLED,
			CPDefinitionAvailabilityRangeImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(CPDefinitionAvailabilityRangeModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionAvailabilityRangeModelImpl.FINDER_CACHE_ENABLED,
			CPDefinitionAvailabilityRangeImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			CPDefinitionAvailabilityRangeModelImpl.UUID_COLUMN_BITMASK |
			CPDefinitionAvailabilityRangeModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(CPDefinitionAvailabilityRangeModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionAvailabilityRangeModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns all the cp definition availability ranges where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching cp definition availability ranges
	 */
	@Override
	public List<CPDefinitionAvailabilityRange> findByUuid_C(String uuid,
		long companyId) {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definition availability ranges where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionAvailabilityRangeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp definition availability ranges
	 * @param end the upper bound of the range of cp definition availability ranges (not inclusive)
	 * @return the range of matching cp definition availability ranges
	 */
	@Override
	public List<CPDefinitionAvailabilityRange> findByUuid_C(String uuid,
		long companyId, int start, int end) {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definition availability ranges where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionAvailabilityRangeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp definition availability ranges
	 * @param end the upper bound of the range of cp definition availability ranges (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definition availability ranges
	 */
	@Override
	public List<CPDefinitionAvailabilityRange> findByUuid_C(String uuid,
		long companyId, int start, int end,
		OrderByComparator<CPDefinitionAvailabilityRange> orderByComparator) {
		return findByUuid_C(uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definition availability ranges where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionAvailabilityRangeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp definition availability ranges
	 * @param end the upper bound of the range of cp definition availability ranges (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching cp definition availability ranges
	 */
	@Override
	public List<CPDefinitionAvailabilityRange> findByUuid_C(String uuid,
		long companyId, int start, int end,
		OrderByComparator<CPDefinitionAvailabilityRange> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C;
			finderArgs = new Object[] { uuid, companyId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C;
			finderArgs = new Object[] {
					uuid, companyId,
					
					start, end, orderByComparator
				};
		}

		List<CPDefinitionAvailabilityRange> list = null;

		if (retrieveFromCache) {
			list = (List<CPDefinitionAvailabilityRange>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CPDefinitionAvailabilityRange cpDefinitionAvailabilityRange : list) {
					if (!Objects.equals(uuid,
								cpDefinitionAvailabilityRange.getUuid()) ||
							(companyId != cpDefinitionAvailabilityRange.getCompanyId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_CPDEFINITIONAVAILABILITYRANGE_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CPDefinitionAvailabilityRangeModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(companyId);

				if (!pagination) {
					list = (List<CPDefinitionAvailabilityRange>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CPDefinitionAvailabilityRange>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first cp definition availability range in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition availability range
	 * @throws NoSuchCPDefinitionAvailabilityRangeException if a matching cp definition availability range could not be found
	 */
	@Override
	public CPDefinitionAvailabilityRange findByUuid_C_First(String uuid,
		long companyId,
		OrderByComparator<CPDefinitionAvailabilityRange> orderByComparator)
		throws NoSuchCPDefinitionAvailabilityRangeException {
		CPDefinitionAvailabilityRange cpDefinitionAvailabilityRange = fetchByUuid_C_First(uuid,
				companyId, orderByComparator);

		if (cpDefinitionAvailabilityRange != null) {
			return cpDefinitionAvailabilityRange;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCPDefinitionAvailabilityRangeException(msg.toString());
	}

	/**
	 * Returns the first cp definition availability range in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition availability range, or <code>null</code> if a matching cp definition availability range could not be found
	 */
	@Override
	public CPDefinitionAvailabilityRange fetchByUuid_C_First(String uuid,
		long companyId,
		OrderByComparator<CPDefinitionAvailabilityRange> orderByComparator) {
		List<CPDefinitionAvailabilityRange> list = findByUuid_C(uuid,
				companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp definition availability range in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition availability range
	 * @throws NoSuchCPDefinitionAvailabilityRangeException if a matching cp definition availability range could not be found
	 */
	@Override
	public CPDefinitionAvailabilityRange findByUuid_C_Last(String uuid,
		long companyId,
		OrderByComparator<CPDefinitionAvailabilityRange> orderByComparator)
		throws NoSuchCPDefinitionAvailabilityRangeException {
		CPDefinitionAvailabilityRange cpDefinitionAvailabilityRange = fetchByUuid_C_Last(uuid,
				companyId, orderByComparator);

		if (cpDefinitionAvailabilityRange != null) {
			return cpDefinitionAvailabilityRange;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCPDefinitionAvailabilityRangeException(msg.toString());
	}

	/**
	 * Returns the last cp definition availability range in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition availability range, or <code>null</code> if a matching cp definition availability range could not be found
	 */
	@Override
	public CPDefinitionAvailabilityRange fetchByUuid_C_Last(String uuid,
		long companyId,
		OrderByComparator<CPDefinitionAvailabilityRange> orderByComparator) {
		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<CPDefinitionAvailabilityRange> list = findByUuid_C(uuid,
				companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp definition availability ranges before and after the current cp definition availability range in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param CPDefinitionAvailabilityRangeId the primary key of the current cp definition availability range
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp definition availability range
	 * @throws NoSuchCPDefinitionAvailabilityRangeException if a cp definition availability range with the primary key could not be found
	 */
	@Override
	public CPDefinitionAvailabilityRange[] findByUuid_C_PrevAndNext(
		long CPDefinitionAvailabilityRangeId, String uuid, long companyId,
		OrderByComparator<CPDefinitionAvailabilityRange> orderByComparator)
		throws NoSuchCPDefinitionAvailabilityRangeException {
		CPDefinitionAvailabilityRange cpDefinitionAvailabilityRange = findByPrimaryKey(CPDefinitionAvailabilityRangeId);

		Session session = null;

		try {
			session = openSession();

			CPDefinitionAvailabilityRange[] array = new CPDefinitionAvailabilityRangeImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session,
					cpDefinitionAvailabilityRange, uuid, companyId,
					orderByComparator, true);

			array[1] = cpDefinitionAvailabilityRange;

			array[2] = getByUuid_C_PrevAndNext(session,
					cpDefinitionAvailabilityRange, uuid, companyId,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CPDefinitionAvailabilityRange getByUuid_C_PrevAndNext(
		Session session,
		CPDefinitionAvailabilityRange cpDefinitionAvailabilityRange,
		String uuid, long companyId,
		OrderByComparator<CPDefinitionAvailabilityRange> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_CPDEFINITIONAVAILABILITYRANGE_WHERE);

		boolean bindUuid = false;

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_1);
		}
		else if (uuid.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_3);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_C_UUID_2);
		}

		query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(CPDefinitionAvailabilityRangeModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindUuid) {
			qPos.add(uuid);
		}

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(cpDefinitionAvailabilityRange);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CPDefinitionAvailabilityRange> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp definition availability ranges where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (CPDefinitionAvailabilityRange cpDefinitionAvailabilityRange : findByUuid_C(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(cpDefinitionAvailabilityRange);
		}
	}

	/**
	 * Returns the number of cp definition availability ranges where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching cp definition availability ranges
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_C;

		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_CPDEFINITIONAVAILABILITYRANGE_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(companyId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "cpDefinitionAvailabilityRange.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "cpDefinitionAvailabilityRange.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(cpDefinitionAvailabilityRange.uuid IS NULL OR cpDefinitionAvailabilityRange.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "cpDefinitionAvailabilityRange.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(CPDefinitionAvailabilityRangeModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionAvailabilityRangeModelImpl.FINDER_CACHE_ENABLED,
			CPDefinitionAvailabilityRangeImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(CPDefinitionAvailabilityRangeModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionAvailabilityRangeModelImpl.FINDER_CACHE_ENABLED,
			CPDefinitionAvailabilityRangeImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			CPDefinitionAvailabilityRangeModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(CPDefinitionAvailabilityRangeModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionAvailabilityRangeModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByGroupId", new String[] { Long.class.getName() });

	/**
	 * Returns all the cp definition availability ranges where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching cp definition availability ranges
	 */
	@Override
	public List<CPDefinitionAvailabilityRange> findByGroupId(long groupId) {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definition availability ranges where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionAvailabilityRangeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of cp definition availability ranges
	 * @param end the upper bound of the range of cp definition availability ranges (not inclusive)
	 * @return the range of matching cp definition availability ranges
	 */
	@Override
	public List<CPDefinitionAvailabilityRange> findByGroupId(long groupId,
		int start, int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definition availability ranges where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionAvailabilityRangeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of cp definition availability ranges
	 * @param end the upper bound of the range of cp definition availability ranges (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definition availability ranges
	 */
	@Override
	public List<CPDefinitionAvailabilityRange> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<CPDefinitionAvailabilityRange> orderByComparator) {
		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definition availability ranges where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionAvailabilityRangeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of cp definition availability ranges
	 * @param end the upper bound of the range of cp definition availability ranges (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching cp definition availability ranges
	 */
	@Override
	public List<CPDefinitionAvailabilityRange> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<CPDefinitionAvailabilityRange> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID;
			finderArgs = new Object[] { groupId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID;
			finderArgs = new Object[] { groupId, start, end, orderByComparator };
		}

		List<CPDefinitionAvailabilityRange> list = null;

		if (retrieveFromCache) {
			list = (List<CPDefinitionAvailabilityRange>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CPDefinitionAvailabilityRange cpDefinitionAvailabilityRange : list) {
					if ((groupId != cpDefinitionAvailabilityRange.getGroupId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_CPDEFINITIONAVAILABILITYRANGE_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CPDefinitionAvailabilityRangeModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<CPDefinitionAvailabilityRange>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CPDefinitionAvailabilityRange>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first cp definition availability range in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition availability range
	 * @throws NoSuchCPDefinitionAvailabilityRangeException if a matching cp definition availability range could not be found
	 */
	@Override
	public CPDefinitionAvailabilityRange findByGroupId_First(long groupId,
		OrderByComparator<CPDefinitionAvailabilityRange> orderByComparator)
		throws NoSuchCPDefinitionAvailabilityRangeException {
		CPDefinitionAvailabilityRange cpDefinitionAvailabilityRange = fetchByGroupId_First(groupId,
				orderByComparator);

		if (cpDefinitionAvailabilityRange != null) {
			return cpDefinitionAvailabilityRange;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCPDefinitionAvailabilityRangeException(msg.toString());
	}

	/**
	 * Returns the first cp definition availability range in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition availability range, or <code>null</code> if a matching cp definition availability range could not be found
	 */
	@Override
	public CPDefinitionAvailabilityRange fetchByGroupId_First(long groupId,
		OrderByComparator<CPDefinitionAvailabilityRange> orderByComparator) {
		List<CPDefinitionAvailabilityRange> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp definition availability range in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition availability range
	 * @throws NoSuchCPDefinitionAvailabilityRangeException if a matching cp definition availability range could not be found
	 */
	@Override
	public CPDefinitionAvailabilityRange findByGroupId_Last(long groupId,
		OrderByComparator<CPDefinitionAvailabilityRange> orderByComparator)
		throws NoSuchCPDefinitionAvailabilityRangeException {
		CPDefinitionAvailabilityRange cpDefinitionAvailabilityRange = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (cpDefinitionAvailabilityRange != null) {
			return cpDefinitionAvailabilityRange;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCPDefinitionAvailabilityRangeException(msg.toString());
	}

	/**
	 * Returns the last cp definition availability range in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition availability range, or <code>null</code> if a matching cp definition availability range could not be found
	 */
	@Override
	public CPDefinitionAvailabilityRange fetchByGroupId_Last(long groupId,
		OrderByComparator<CPDefinitionAvailabilityRange> orderByComparator) {
		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<CPDefinitionAvailabilityRange> list = findByGroupId(groupId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp definition availability ranges before and after the current cp definition availability range in the ordered set where groupId = &#63;.
	 *
	 * @param CPDefinitionAvailabilityRangeId the primary key of the current cp definition availability range
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp definition availability range
	 * @throws NoSuchCPDefinitionAvailabilityRangeException if a cp definition availability range with the primary key could not be found
	 */
	@Override
	public CPDefinitionAvailabilityRange[] findByGroupId_PrevAndNext(
		long CPDefinitionAvailabilityRangeId, long groupId,
		OrderByComparator<CPDefinitionAvailabilityRange> orderByComparator)
		throws NoSuchCPDefinitionAvailabilityRangeException {
		CPDefinitionAvailabilityRange cpDefinitionAvailabilityRange = findByPrimaryKey(CPDefinitionAvailabilityRangeId);

		Session session = null;

		try {
			session = openSession();

			CPDefinitionAvailabilityRange[] array = new CPDefinitionAvailabilityRangeImpl[3];

			array[0] = getByGroupId_PrevAndNext(session,
					cpDefinitionAvailabilityRange, groupId, orderByComparator,
					true);

			array[1] = cpDefinitionAvailabilityRange;

			array[2] = getByGroupId_PrevAndNext(session,
					cpDefinitionAvailabilityRange, groupId, orderByComparator,
					false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CPDefinitionAvailabilityRange getByGroupId_PrevAndNext(
		Session session,
		CPDefinitionAvailabilityRange cpDefinitionAvailabilityRange,
		long groupId,
		OrderByComparator<CPDefinitionAvailabilityRange> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_CPDEFINITIONAVAILABILITYRANGE_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(CPDefinitionAvailabilityRangeModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(cpDefinitionAvailabilityRange);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CPDefinitionAvailabilityRange> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp definition availability ranges where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (CPDefinitionAvailabilityRange cpDefinitionAvailabilityRange : findByGroupId(
				groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(cpDefinitionAvailabilityRange);
		}
	}

	/**
	 * Returns the number of cp definition availability ranges where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching cp definition availability ranges
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_GROUPID;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_CPDEFINITIONAVAILABILITYRANGE_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "cpDefinitionAvailabilityRange.groupId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_CPDEFINITIONID = new FinderPath(CPDefinitionAvailabilityRangeModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionAvailabilityRangeModelImpl.FINDER_CACHE_ENABLED,
			CPDefinitionAvailabilityRangeImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByCPDefinitionId", new String[] { Long.class.getName() },
			CPDefinitionAvailabilityRangeModelImpl.CPDEFINITIONID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_CPDEFINITIONID = new FinderPath(CPDefinitionAvailabilityRangeModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionAvailabilityRangeModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCPDefinitionId", new String[] { Long.class.getName() });

	/**
	 * Returns the cp definition availability range where CPDefinitionId = &#63; or throws a {@link NoSuchCPDefinitionAvailabilityRangeException} if it could not be found.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @return the matching cp definition availability range
	 * @throws NoSuchCPDefinitionAvailabilityRangeException if a matching cp definition availability range could not be found
	 */
	@Override
	public CPDefinitionAvailabilityRange findByCPDefinitionId(
		long CPDefinitionId)
		throws NoSuchCPDefinitionAvailabilityRangeException {
		CPDefinitionAvailabilityRange cpDefinitionAvailabilityRange = fetchByCPDefinitionId(CPDefinitionId);

		if (cpDefinitionAvailabilityRange == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("CPDefinitionId=");
			msg.append(CPDefinitionId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchCPDefinitionAvailabilityRangeException(msg.toString());
		}

		return cpDefinitionAvailabilityRange;
	}

	/**
	 * Returns the cp definition availability range where CPDefinitionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @return the matching cp definition availability range, or <code>null</code> if a matching cp definition availability range could not be found
	 */
	@Override
	public CPDefinitionAvailabilityRange fetchByCPDefinitionId(
		long CPDefinitionId) {
		return fetchByCPDefinitionId(CPDefinitionId, true);
	}

	/**
	 * Returns the cp definition availability range where CPDefinitionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching cp definition availability range, or <code>null</code> if a matching cp definition availability range could not be found
	 */
	@Override
	public CPDefinitionAvailabilityRange fetchByCPDefinitionId(
		long CPDefinitionId, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { CPDefinitionId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_CPDEFINITIONID,
					finderArgs, this);
		}

		if (result instanceof CPDefinitionAvailabilityRange) {
			CPDefinitionAvailabilityRange cpDefinitionAvailabilityRange = (CPDefinitionAvailabilityRange)result;

			if ((CPDefinitionId != cpDefinitionAvailabilityRange.getCPDefinitionId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_CPDEFINITIONAVAILABILITYRANGE_WHERE);

			query.append(_FINDER_COLUMN_CPDEFINITIONID_CPDEFINITIONID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(CPDefinitionId);

				List<CPDefinitionAvailabilityRange> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_CPDEFINITIONID,
						finderArgs, list);
				}
				else {
					CPDefinitionAvailabilityRange cpDefinitionAvailabilityRange = list.get(0);

					result = cpDefinitionAvailabilityRange;

					cacheResult(cpDefinitionAvailabilityRange);

					if ((cpDefinitionAvailabilityRange.getCPDefinitionId() != CPDefinitionId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_CPDEFINITIONID,
							finderArgs, cpDefinitionAvailabilityRange);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_CPDEFINITIONID,
					finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (CPDefinitionAvailabilityRange)result;
		}
	}

	/**
	 * Removes the cp definition availability range where CPDefinitionId = &#63; from the database.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @return the cp definition availability range that was removed
	 */
	@Override
	public CPDefinitionAvailabilityRange removeByCPDefinitionId(
		long CPDefinitionId)
		throws NoSuchCPDefinitionAvailabilityRangeException {
		CPDefinitionAvailabilityRange cpDefinitionAvailabilityRange = findByCPDefinitionId(CPDefinitionId);

		return remove(cpDefinitionAvailabilityRange);
	}

	/**
	 * Returns the number of cp definition availability ranges where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @return the number of matching cp definition availability ranges
	 */
	@Override
	public int countByCPDefinitionId(long CPDefinitionId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_CPDEFINITIONID;

		Object[] finderArgs = new Object[] { CPDefinitionId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_CPDEFINITIONAVAILABILITYRANGE_WHERE);

			query.append(_FINDER_COLUMN_CPDEFINITIONID_CPDEFINITIONID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(CPDefinitionId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_CPDEFINITIONID_CPDEFINITIONID_2 = "cpDefinitionAvailabilityRange.CPDefinitionId = ?";

	public CPDefinitionAvailabilityRangePersistenceImpl() {
		setModelClass(CPDefinitionAvailabilityRange.class);

		try {
			Field field = ReflectionUtil.getDeclaredField(BasePersistenceImpl.class,
					"_dbColumnNames");

			Map<String, String> dbColumnNames = new HashMap<String, String>();

			dbColumnNames.put("uuid", "uuid_");
			dbColumnNames.put("CPDefinitionAvailabilityRangeId",
				"CPDAvailabilityRangeId");

			field.set(this, dbColumnNames);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}
	}

	/**
	 * Caches the cp definition availability range in the entity cache if it is enabled.
	 *
	 * @param cpDefinitionAvailabilityRange the cp definition availability range
	 */
	@Override
	public void cacheResult(
		CPDefinitionAvailabilityRange cpDefinitionAvailabilityRange) {
		entityCache.putResult(CPDefinitionAvailabilityRangeModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionAvailabilityRangeImpl.class,
			cpDefinitionAvailabilityRange.getPrimaryKey(),
			cpDefinitionAvailabilityRange);

		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				cpDefinitionAvailabilityRange.getUuid(),
				cpDefinitionAvailabilityRange.getGroupId()
			}, cpDefinitionAvailabilityRange);

		finderCache.putResult(FINDER_PATH_FETCH_BY_CPDEFINITIONID,
			new Object[] { cpDefinitionAvailabilityRange.getCPDefinitionId() },
			cpDefinitionAvailabilityRange);

		cpDefinitionAvailabilityRange.resetOriginalValues();
	}

	/**
	 * Caches the cp definition availability ranges in the entity cache if it is enabled.
	 *
	 * @param cpDefinitionAvailabilityRanges the cp definition availability ranges
	 */
	@Override
	public void cacheResult(
		List<CPDefinitionAvailabilityRange> cpDefinitionAvailabilityRanges) {
		for (CPDefinitionAvailabilityRange cpDefinitionAvailabilityRange : cpDefinitionAvailabilityRanges) {
			if (entityCache.getResult(
						CPDefinitionAvailabilityRangeModelImpl.ENTITY_CACHE_ENABLED,
						CPDefinitionAvailabilityRangeImpl.class,
						cpDefinitionAvailabilityRange.getPrimaryKey()) == null) {
				cacheResult(cpDefinitionAvailabilityRange);
			}
			else {
				cpDefinitionAvailabilityRange.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all cp definition availability ranges.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CPDefinitionAvailabilityRangeImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the cp definition availability range.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		CPDefinitionAvailabilityRange cpDefinitionAvailabilityRange) {
		entityCache.removeResult(CPDefinitionAvailabilityRangeModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionAvailabilityRangeImpl.class,
			cpDefinitionAvailabilityRange.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((CPDefinitionAvailabilityRangeModelImpl)cpDefinitionAvailabilityRange,
			true);
	}

	@Override
	public void clearCache(
		List<CPDefinitionAvailabilityRange> cpDefinitionAvailabilityRanges) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CPDefinitionAvailabilityRange cpDefinitionAvailabilityRange : cpDefinitionAvailabilityRanges) {
			entityCache.removeResult(CPDefinitionAvailabilityRangeModelImpl.ENTITY_CACHE_ENABLED,
				CPDefinitionAvailabilityRangeImpl.class,
				cpDefinitionAvailabilityRange.getPrimaryKey());

			clearUniqueFindersCache((CPDefinitionAvailabilityRangeModelImpl)cpDefinitionAvailabilityRange,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		CPDefinitionAvailabilityRangeModelImpl cpDefinitionAvailabilityRangeModelImpl) {
		Object[] args = new Object[] {
				cpDefinitionAvailabilityRangeModelImpl.getUuid(),
				cpDefinitionAvailabilityRangeModelImpl.getGroupId()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_UUID_G, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G, args,
			cpDefinitionAvailabilityRangeModelImpl, false);

		args = new Object[] {
				cpDefinitionAvailabilityRangeModelImpl.getCPDefinitionId()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_CPDEFINITIONID, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_CPDEFINITIONID, args,
			cpDefinitionAvailabilityRangeModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		CPDefinitionAvailabilityRangeModelImpl cpDefinitionAvailabilityRangeModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					cpDefinitionAvailabilityRangeModelImpl.getUuid(),
					cpDefinitionAvailabilityRangeModelImpl.getGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}

		if ((cpDefinitionAvailabilityRangeModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					cpDefinitionAvailabilityRangeModelImpl.getOriginalUuid(),
					cpDefinitionAvailabilityRangeModelImpl.getOriginalGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
					cpDefinitionAvailabilityRangeModelImpl.getCPDefinitionId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_CPDEFINITIONID, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_CPDEFINITIONID, args);
		}

		if ((cpDefinitionAvailabilityRangeModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_CPDEFINITIONID.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					cpDefinitionAvailabilityRangeModelImpl.getOriginalCPDefinitionId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_CPDEFINITIONID, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_CPDEFINITIONID, args);
		}
	}

	/**
	 * Creates a new cp definition availability range with the primary key. Does not add the cp definition availability range to the database.
	 *
	 * @param CPDefinitionAvailabilityRangeId the primary key for the new cp definition availability range
	 * @return the new cp definition availability range
	 */
	@Override
	public CPDefinitionAvailabilityRange create(
		long CPDefinitionAvailabilityRangeId) {
		CPDefinitionAvailabilityRange cpDefinitionAvailabilityRange = new CPDefinitionAvailabilityRangeImpl();

		cpDefinitionAvailabilityRange.setNew(true);
		cpDefinitionAvailabilityRange.setPrimaryKey(CPDefinitionAvailabilityRangeId);

		String uuid = PortalUUIDUtil.generate();

		cpDefinitionAvailabilityRange.setUuid(uuid);

		cpDefinitionAvailabilityRange.setCompanyId(companyProvider.getCompanyId());

		return cpDefinitionAvailabilityRange;
	}

	/**
	 * Removes the cp definition availability range with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CPDefinitionAvailabilityRangeId the primary key of the cp definition availability range
	 * @return the cp definition availability range that was removed
	 * @throws NoSuchCPDefinitionAvailabilityRangeException if a cp definition availability range with the primary key could not be found
	 */
	@Override
	public CPDefinitionAvailabilityRange remove(
		long CPDefinitionAvailabilityRangeId)
		throws NoSuchCPDefinitionAvailabilityRangeException {
		return remove((Serializable)CPDefinitionAvailabilityRangeId);
	}

	/**
	 * Removes the cp definition availability range with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the cp definition availability range
	 * @return the cp definition availability range that was removed
	 * @throws NoSuchCPDefinitionAvailabilityRangeException if a cp definition availability range with the primary key could not be found
	 */
	@Override
	public CPDefinitionAvailabilityRange remove(Serializable primaryKey)
		throws NoSuchCPDefinitionAvailabilityRangeException {
		Session session = null;

		try {
			session = openSession();

			CPDefinitionAvailabilityRange cpDefinitionAvailabilityRange = (CPDefinitionAvailabilityRange)session.get(CPDefinitionAvailabilityRangeImpl.class,
					primaryKey);

			if (cpDefinitionAvailabilityRange == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCPDefinitionAvailabilityRangeException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(cpDefinitionAvailabilityRange);
		}
		catch (NoSuchCPDefinitionAvailabilityRangeException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected CPDefinitionAvailabilityRange removeImpl(
		CPDefinitionAvailabilityRange cpDefinitionAvailabilityRange) {
		cpDefinitionAvailabilityRange = toUnwrappedModel(cpDefinitionAvailabilityRange);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(cpDefinitionAvailabilityRange)) {
				cpDefinitionAvailabilityRange = (CPDefinitionAvailabilityRange)session.get(CPDefinitionAvailabilityRangeImpl.class,
						cpDefinitionAvailabilityRange.getPrimaryKeyObj());
			}

			if (cpDefinitionAvailabilityRange != null) {
				session.delete(cpDefinitionAvailabilityRange);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (cpDefinitionAvailabilityRange != null) {
			clearCache(cpDefinitionAvailabilityRange);
		}

		return cpDefinitionAvailabilityRange;
	}

	@Override
	public CPDefinitionAvailabilityRange updateImpl(
		CPDefinitionAvailabilityRange cpDefinitionAvailabilityRange) {
		cpDefinitionAvailabilityRange = toUnwrappedModel(cpDefinitionAvailabilityRange);

		boolean isNew = cpDefinitionAvailabilityRange.isNew();

		CPDefinitionAvailabilityRangeModelImpl cpDefinitionAvailabilityRangeModelImpl =
			(CPDefinitionAvailabilityRangeModelImpl)cpDefinitionAvailabilityRange;

		if (Validator.isNull(cpDefinitionAvailabilityRange.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			cpDefinitionAvailabilityRange.setUuid(uuid);
		}

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (cpDefinitionAvailabilityRange.getCreateDate() == null)) {
			if (serviceContext == null) {
				cpDefinitionAvailabilityRange.setCreateDate(now);
			}
			else {
				cpDefinitionAvailabilityRange.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!cpDefinitionAvailabilityRangeModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				cpDefinitionAvailabilityRange.setModifiedDate(now);
			}
			else {
				cpDefinitionAvailabilityRange.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (cpDefinitionAvailabilityRange.isNew()) {
				session.save(cpDefinitionAvailabilityRange);

				cpDefinitionAvailabilityRange.setNew(false);
			}
			else {
				cpDefinitionAvailabilityRange = (CPDefinitionAvailabilityRange)session.merge(cpDefinitionAvailabilityRange);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!CPDefinitionAvailabilityRangeModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					cpDefinitionAvailabilityRangeModelImpl.getUuid()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
				args);

			args = new Object[] {
					cpDefinitionAvailabilityRangeModelImpl.getUuid(),
					cpDefinitionAvailabilityRangeModelImpl.getCompanyId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
				args);

			args = new Object[] {
					cpDefinitionAvailabilityRangeModelImpl.getGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((cpDefinitionAvailabilityRangeModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						cpDefinitionAvailabilityRangeModelImpl.getOriginalUuid()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] {
						cpDefinitionAvailabilityRangeModelImpl.getUuid()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((cpDefinitionAvailabilityRangeModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						cpDefinitionAvailabilityRangeModelImpl.getOriginalUuid(),
						cpDefinitionAvailabilityRangeModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						cpDefinitionAvailabilityRangeModelImpl.getUuid(),
						cpDefinitionAvailabilityRangeModelImpl.getCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((cpDefinitionAvailabilityRangeModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						cpDefinitionAvailabilityRangeModelImpl.getOriginalGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] {
						cpDefinitionAvailabilityRangeModelImpl.getGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}
		}

		entityCache.putResult(CPDefinitionAvailabilityRangeModelImpl.ENTITY_CACHE_ENABLED,
			CPDefinitionAvailabilityRangeImpl.class,
			cpDefinitionAvailabilityRange.getPrimaryKey(),
			cpDefinitionAvailabilityRange, false);

		clearUniqueFindersCache(cpDefinitionAvailabilityRangeModelImpl, false);
		cacheUniqueFindersCache(cpDefinitionAvailabilityRangeModelImpl);

		cpDefinitionAvailabilityRange.resetOriginalValues();

		return cpDefinitionAvailabilityRange;
	}

	protected CPDefinitionAvailabilityRange toUnwrappedModel(
		CPDefinitionAvailabilityRange cpDefinitionAvailabilityRange) {
		if (cpDefinitionAvailabilityRange instanceof CPDefinitionAvailabilityRangeImpl) {
			return cpDefinitionAvailabilityRange;
		}

		CPDefinitionAvailabilityRangeImpl cpDefinitionAvailabilityRangeImpl = new CPDefinitionAvailabilityRangeImpl();

		cpDefinitionAvailabilityRangeImpl.setNew(cpDefinitionAvailabilityRange.isNew());
		cpDefinitionAvailabilityRangeImpl.setPrimaryKey(cpDefinitionAvailabilityRange.getPrimaryKey());

		cpDefinitionAvailabilityRangeImpl.setUuid(cpDefinitionAvailabilityRange.getUuid());
		cpDefinitionAvailabilityRangeImpl.setCPDefinitionAvailabilityRangeId(cpDefinitionAvailabilityRange.getCPDefinitionAvailabilityRangeId());
		cpDefinitionAvailabilityRangeImpl.setGroupId(cpDefinitionAvailabilityRange.getGroupId());
		cpDefinitionAvailabilityRangeImpl.setCompanyId(cpDefinitionAvailabilityRange.getCompanyId());
		cpDefinitionAvailabilityRangeImpl.setUserId(cpDefinitionAvailabilityRange.getUserId());
		cpDefinitionAvailabilityRangeImpl.setUserName(cpDefinitionAvailabilityRange.getUserName());
		cpDefinitionAvailabilityRangeImpl.setCreateDate(cpDefinitionAvailabilityRange.getCreateDate());
		cpDefinitionAvailabilityRangeImpl.setModifiedDate(cpDefinitionAvailabilityRange.getModifiedDate());
		cpDefinitionAvailabilityRangeImpl.setCPDefinitionId(cpDefinitionAvailabilityRange.getCPDefinitionId());
		cpDefinitionAvailabilityRangeImpl.setCommerceAvailabilityRangeId(cpDefinitionAvailabilityRange.getCommerceAvailabilityRangeId());
		cpDefinitionAvailabilityRangeImpl.setLastPublishDate(cpDefinitionAvailabilityRange.getLastPublishDate());

		return cpDefinitionAvailabilityRangeImpl;
	}

	/**
	 * Returns the cp definition availability range with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the cp definition availability range
	 * @return the cp definition availability range
	 * @throws NoSuchCPDefinitionAvailabilityRangeException if a cp definition availability range with the primary key could not be found
	 */
	@Override
	public CPDefinitionAvailabilityRange findByPrimaryKey(
		Serializable primaryKey)
		throws NoSuchCPDefinitionAvailabilityRangeException {
		CPDefinitionAvailabilityRange cpDefinitionAvailabilityRange = fetchByPrimaryKey(primaryKey);

		if (cpDefinitionAvailabilityRange == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCPDefinitionAvailabilityRangeException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return cpDefinitionAvailabilityRange;
	}

	/**
	 * Returns the cp definition availability range with the primary key or throws a {@link NoSuchCPDefinitionAvailabilityRangeException} if it could not be found.
	 *
	 * @param CPDefinitionAvailabilityRangeId the primary key of the cp definition availability range
	 * @return the cp definition availability range
	 * @throws NoSuchCPDefinitionAvailabilityRangeException if a cp definition availability range with the primary key could not be found
	 */
	@Override
	public CPDefinitionAvailabilityRange findByPrimaryKey(
		long CPDefinitionAvailabilityRangeId)
		throws NoSuchCPDefinitionAvailabilityRangeException {
		return findByPrimaryKey((Serializable)CPDefinitionAvailabilityRangeId);
	}

	/**
	 * Returns the cp definition availability range with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the cp definition availability range
	 * @return the cp definition availability range, or <code>null</code> if a cp definition availability range with the primary key could not be found
	 */
	@Override
	public CPDefinitionAvailabilityRange fetchByPrimaryKey(
		Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(CPDefinitionAvailabilityRangeModelImpl.ENTITY_CACHE_ENABLED,
				CPDefinitionAvailabilityRangeImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		CPDefinitionAvailabilityRange cpDefinitionAvailabilityRange = (CPDefinitionAvailabilityRange)serializable;

		if (cpDefinitionAvailabilityRange == null) {
			Session session = null;

			try {
				session = openSession();

				cpDefinitionAvailabilityRange = (CPDefinitionAvailabilityRange)session.get(CPDefinitionAvailabilityRangeImpl.class,
						primaryKey);

				if (cpDefinitionAvailabilityRange != null) {
					cacheResult(cpDefinitionAvailabilityRange);
				}
				else {
					entityCache.putResult(CPDefinitionAvailabilityRangeModelImpl.ENTITY_CACHE_ENABLED,
						CPDefinitionAvailabilityRangeImpl.class, primaryKey,
						nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(CPDefinitionAvailabilityRangeModelImpl.ENTITY_CACHE_ENABLED,
					CPDefinitionAvailabilityRangeImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return cpDefinitionAvailabilityRange;
	}

	/**
	 * Returns the cp definition availability range with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CPDefinitionAvailabilityRangeId the primary key of the cp definition availability range
	 * @return the cp definition availability range, or <code>null</code> if a cp definition availability range with the primary key could not be found
	 */
	@Override
	public CPDefinitionAvailabilityRange fetchByPrimaryKey(
		long CPDefinitionAvailabilityRangeId) {
		return fetchByPrimaryKey((Serializable)CPDefinitionAvailabilityRangeId);
	}

	@Override
	public Map<Serializable, CPDefinitionAvailabilityRange> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CPDefinitionAvailabilityRange> map = new HashMap<Serializable, CPDefinitionAvailabilityRange>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CPDefinitionAvailabilityRange cpDefinitionAvailabilityRange = fetchByPrimaryKey(primaryKey);

			if (cpDefinitionAvailabilityRange != null) {
				map.put(primaryKey, cpDefinitionAvailabilityRange);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(CPDefinitionAvailabilityRangeModelImpl.ENTITY_CACHE_ENABLED,
					CPDefinitionAvailabilityRangeImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey,
						(CPDefinitionAvailabilityRange)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_CPDEFINITIONAVAILABILITYRANGE_WHERE_PKS_IN);

		for (Serializable primaryKey : uncachedPrimaryKeys) {
			query.append((long)primaryKey);

			query.append(StringPool.COMMA);
		}

		query.setIndex(query.index() - 1);

		query.append(StringPool.CLOSE_PARENTHESIS);

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			for (CPDefinitionAvailabilityRange cpDefinitionAvailabilityRange : (List<CPDefinitionAvailabilityRange>)q.list()) {
				map.put(cpDefinitionAvailabilityRange.getPrimaryKeyObj(),
					cpDefinitionAvailabilityRange);

				cacheResult(cpDefinitionAvailabilityRange);

				uncachedPrimaryKeys.remove(cpDefinitionAvailabilityRange.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(CPDefinitionAvailabilityRangeModelImpl.ENTITY_CACHE_ENABLED,
					CPDefinitionAvailabilityRangeImpl.class, primaryKey,
					nullModel);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		return map;
	}

	/**
	 * Returns all the cp definition availability ranges.
	 *
	 * @return the cp definition availability ranges
	 */
	@Override
	public List<CPDefinitionAvailabilityRange> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definition availability ranges.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionAvailabilityRangeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definition availability ranges
	 * @param end the upper bound of the range of cp definition availability ranges (not inclusive)
	 * @return the range of cp definition availability ranges
	 */
	@Override
	public List<CPDefinitionAvailabilityRange> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definition availability ranges.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionAvailabilityRangeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definition availability ranges
	 * @param end the upper bound of the range of cp definition availability ranges (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cp definition availability ranges
	 */
	@Override
	public List<CPDefinitionAvailabilityRange> findAll(int start, int end,
		OrderByComparator<CPDefinitionAvailabilityRange> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definition availability ranges.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDefinitionAvailabilityRangeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definition availability ranges
	 * @param end the upper bound of the range of cp definition availability ranges (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of cp definition availability ranges
	 */
	@Override
	public List<CPDefinitionAvailabilityRange> findAll(int start, int end,
		OrderByComparator<CPDefinitionAvailabilityRange> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_ALL;
			finderArgs = new Object[] { start, end, orderByComparator };
		}

		List<CPDefinitionAvailabilityRange> list = null;

		if (retrieveFromCache) {
			list = (List<CPDefinitionAvailabilityRange>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_CPDEFINITIONAVAILABILITYRANGE);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_CPDEFINITIONAVAILABILITYRANGE;

				if (pagination) {
					sql = sql.concat(CPDefinitionAvailabilityRangeModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CPDefinitionAvailabilityRange>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CPDefinitionAvailabilityRange>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the cp definition availability ranges from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CPDefinitionAvailabilityRange cpDefinitionAvailabilityRange : findAll()) {
			remove(cpDefinitionAvailabilityRange);
		}
	}

	/**
	 * Returns the number of cp definition availability ranges.
	 *
	 * @return the number of cp definition availability ranges
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_CPDEFINITIONAVAILABILITYRANGE);

				count = (Long)q.uniqueResult();

				finderCache.putResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY,
					count);
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY);

				throw processException(e);
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
	protected Map<String, Integer> getTableColumnsMap() {
		return CPDefinitionAvailabilityRangeModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the cp definition availability range persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(CPDefinitionAvailabilityRangeImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = CompanyProviderWrapper.class)
	protected CompanyProvider companyProvider;
	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;
	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;
	private static final String _SQL_SELECT_CPDEFINITIONAVAILABILITYRANGE = "SELECT cpDefinitionAvailabilityRange FROM CPDefinitionAvailabilityRange cpDefinitionAvailabilityRange";
	private static final String _SQL_SELECT_CPDEFINITIONAVAILABILITYRANGE_WHERE_PKS_IN =
		"SELECT cpDefinitionAvailabilityRange FROM CPDefinitionAvailabilityRange cpDefinitionAvailabilityRange WHERE CPDAvailabilityRangeId IN (";
	private static final String _SQL_SELECT_CPDEFINITIONAVAILABILITYRANGE_WHERE = "SELECT cpDefinitionAvailabilityRange FROM CPDefinitionAvailabilityRange cpDefinitionAvailabilityRange WHERE ";
	private static final String _SQL_COUNT_CPDEFINITIONAVAILABILITYRANGE = "SELECT COUNT(cpDefinitionAvailabilityRange) FROM CPDefinitionAvailabilityRange cpDefinitionAvailabilityRange";
	private static final String _SQL_COUNT_CPDEFINITIONAVAILABILITYRANGE_WHERE = "SELECT COUNT(cpDefinitionAvailabilityRange) FROM CPDefinitionAvailabilityRange cpDefinitionAvailabilityRange WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "cpDefinitionAvailabilityRange.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CPDefinitionAvailabilityRange exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No CPDefinitionAvailabilityRange exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(CPDefinitionAvailabilityRangePersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"uuid", "CPDefinitionAvailabilityRangeId"
			});
}