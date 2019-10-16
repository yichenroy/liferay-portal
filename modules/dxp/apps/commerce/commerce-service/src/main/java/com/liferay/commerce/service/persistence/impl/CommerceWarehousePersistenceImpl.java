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

import com.liferay.commerce.exception.NoSuchWarehouseException;
import com.liferay.commerce.model.CommerceWarehouse;
import com.liferay.commerce.model.impl.CommerceWarehouseImpl;
import com.liferay.commerce.model.impl.CommerceWarehouseModelImpl;
import com.liferay.commerce.service.persistence.CommerceWarehousePersistence;

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
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the commerce warehouse service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceWarehousePersistence
 * @see com.liferay.commerce.service.persistence.CommerceWarehouseUtil
 * @generated
 */
@ProviderType
public class CommerceWarehousePersistenceImpl extends BasePersistenceImpl<CommerceWarehouse>
	implements CommerceWarehousePersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CommerceWarehouseUtil} to access the commerce warehouse persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CommerceWarehouseImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CommerceWarehouseModelImpl.ENTITY_CACHE_ENABLED,
			CommerceWarehouseModelImpl.FINDER_CACHE_ENABLED,
			CommerceWarehouseImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CommerceWarehouseModelImpl.ENTITY_CACHE_ENABLED,
			CommerceWarehouseModelImpl.FINDER_CACHE_ENABLED,
			CommerceWarehouseImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CommerceWarehouseModelImpl.ENTITY_CACHE_ENABLED,
			CommerceWarehouseModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(CommerceWarehouseModelImpl.ENTITY_CACHE_ENABLED,
			CommerceWarehouseModelImpl.FINDER_CACHE_ENABLED,
			CommerceWarehouseImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(CommerceWarehouseModelImpl.ENTITY_CACHE_ENABLED,
			CommerceWarehouseModelImpl.FINDER_CACHE_ENABLED,
			CommerceWarehouseImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			CommerceWarehouseModelImpl.GROUPID_COLUMN_BITMASK |
			CommerceWarehouseModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(CommerceWarehouseModelImpl.ENTITY_CACHE_ENABLED,
			CommerceWarehouseModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the commerce warehouses where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching commerce warehouses
	 */
	@Override
	public List<CommerceWarehouse> findByGroupId(long groupId) {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce warehouses where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceWarehouseModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce warehouses
	 * @param end the upper bound of the range of commerce warehouses (not inclusive)
	 * @return the range of matching commerce warehouses
	 */
	@Override
	public List<CommerceWarehouse> findByGroupId(long groupId, int start,
		int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce warehouses where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceWarehouseModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce warehouses
	 * @param end the upper bound of the range of commerce warehouses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce warehouses
	 */
	@Override
	public List<CommerceWarehouse> findByGroupId(long groupId, int start,
		int end, OrderByComparator<CommerceWarehouse> orderByComparator) {
		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce warehouses where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceWarehouseModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce warehouses
	 * @param end the upper bound of the range of commerce warehouses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce warehouses
	 */
	@Override
	public List<CommerceWarehouse> findByGroupId(long groupId, int start,
		int end, OrderByComparator<CommerceWarehouse> orderByComparator,
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

		List<CommerceWarehouse> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceWarehouse>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceWarehouse commerceWarehouse : list) {
					if ((groupId != commerceWarehouse.getGroupId())) {
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

			query.append(_SQL_SELECT_COMMERCEWAREHOUSE_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceWarehouseModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<CommerceWarehouse>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceWarehouse>)QueryUtil.list(q,
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
	 * Returns the first commerce warehouse in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce warehouse
	 * @throws NoSuchWarehouseException if a matching commerce warehouse could not be found
	 */
	@Override
	public CommerceWarehouse findByGroupId_First(long groupId,
		OrderByComparator<CommerceWarehouse> orderByComparator)
		throws NoSuchWarehouseException {
		CommerceWarehouse commerceWarehouse = fetchByGroupId_First(groupId,
				orderByComparator);

		if (commerceWarehouse != null) {
			return commerceWarehouse;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchWarehouseException(msg.toString());
	}

	/**
	 * Returns the first commerce warehouse in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce warehouse, or <code>null</code> if a matching commerce warehouse could not be found
	 */
	@Override
	public CommerceWarehouse fetchByGroupId_First(long groupId,
		OrderByComparator<CommerceWarehouse> orderByComparator) {
		List<CommerceWarehouse> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce warehouse in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce warehouse
	 * @throws NoSuchWarehouseException if a matching commerce warehouse could not be found
	 */
	@Override
	public CommerceWarehouse findByGroupId_Last(long groupId,
		OrderByComparator<CommerceWarehouse> orderByComparator)
		throws NoSuchWarehouseException {
		CommerceWarehouse commerceWarehouse = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (commerceWarehouse != null) {
			return commerceWarehouse;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchWarehouseException(msg.toString());
	}

	/**
	 * Returns the last commerce warehouse in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce warehouse, or <code>null</code> if a matching commerce warehouse could not be found
	 */
	@Override
	public CommerceWarehouse fetchByGroupId_Last(long groupId,
		OrderByComparator<CommerceWarehouse> orderByComparator) {
		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<CommerceWarehouse> list = findByGroupId(groupId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce warehouses before and after the current commerce warehouse in the ordered set where groupId = &#63;.
	 *
	 * @param commerceWarehouseId the primary key of the current commerce warehouse
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce warehouse
	 * @throws NoSuchWarehouseException if a commerce warehouse with the primary key could not be found
	 */
	@Override
	public CommerceWarehouse[] findByGroupId_PrevAndNext(
		long commerceWarehouseId, long groupId,
		OrderByComparator<CommerceWarehouse> orderByComparator)
		throws NoSuchWarehouseException {
		CommerceWarehouse commerceWarehouse = findByPrimaryKey(commerceWarehouseId);

		Session session = null;

		try {
			session = openSession();

			CommerceWarehouse[] array = new CommerceWarehouseImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, commerceWarehouse,
					groupId, orderByComparator, true);

			array[1] = commerceWarehouse;

			array[2] = getByGroupId_PrevAndNext(session, commerceWarehouse,
					groupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceWarehouse getByGroupId_PrevAndNext(Session session,
		CommerceWarehouse commerceWarehouse, long groupId,
		OrderByComparator<CommerceWarehouse> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_COMMERCEWAREHOUSE_WHERE);

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
			query.append(CommerceWarehouseModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceWarehouse);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceWarehouse> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce warehouses where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (CommerceWarehouse commerceWarehouse : findByGroupId(groupId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commerceWarehouse);
		}
	}

	/**
	 * Returns the number of commerce warehouses where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching commerce warehouses
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_GROUPID;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCEWAREHOUSE_WHERE);

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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "commerceWarehouse.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C = new FinderPath(CommerceWarehouseModelImpl.ENTITY_CACHE_ENABLED,
			CommerceWarehouseModelImpl.FINDER_CACHE_ENABLED,
			CommerceWarehouseImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C = new FinderPath(CommerceWarehouseModelImpl.ENTITY_CACHE_ENABLED,
			CommerceWarehouseModelImpl.FINDER_CACHE_ENABLED,
			CommerceWarehouseImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_C",
			new String[] { Long.class.getName(), Long.class.getName() },
			CommerceWarehouseModelImpl.GROUPID_COLUMN_BITMASK |
			CommerceWarehouseModelImpl.COMMERCECOUNTRYID_COLUMN_BITMASK |
			CommerceWarehouseModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_C = new FinderPath(CommerceWarehouseModelImpl.ENTITY_CACHE_ENABLED,
			CommerceWarehouseModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns all the commerce warehouses where groupId = &#63; and commerceCountryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param commerceCountryId the commerce country ID
	 * @return the matching commerce warehouses
	 */
	@Override
	public List<CommerceWarehouse> findByG_C(long groupId,
		long commerceCountryId) {
		return findByG_C(groupId, commerceCountryId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce warehouses where groupId = &#63; and commerceCountryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceWarehouseModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param commerceCountryId the commerce country ID
	 * @param start the lower bound of the range of commerce warehouses
	 * @param end the upper bound of the range of commerce warehouses (not inclusive)
	 * @return the range of matching commerce warehouses
	 */
	@Override
	public List<CommerceWarehouse> findByG_C(long groupId,
		long commerceCountryId, int start, int end) {
		return findByG_C(groupId, commerceCountryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce warehouses where groupId = &#63; and commerceCountryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceWarehouseModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param commerceCountryId the commerce country ID
	 * @param start the lower bound of the range of commerce warehouses
	 * @param end the upper bound of the range of commerce warehouses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce warehouses
	 */
	@Override
	public List<CommerceWarehouse> findByG_C(long groupId,
		long commerceCountryId, int start, int end,
		OrderByComparator<CommerceWarehouse> orderByComparator) {
		return findByG_C(groupId, commerceCountryId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce warehouses where groupId = &#63; and commerceCountryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceWarehouseModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param commerceCountryId the commerce country ID
	 * @param start the lower bound of the range of commerce warehouses
	 * @param end the upper bound of the range of commerce warehouses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce warehouses
	 */
	@Override
	public List<CommerceWarehouse> findByG_C(long groupId,
		long commerceCountryId, int start, int end,
		OrderByComparator<CommerceWarehouse> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C;
			finderArgs = new Object[] { groupId, commerceCountryId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C;
			finderArgs = new Object[] {
					groupId, commerceCountryId,
					
					start, end, orderByComparator
				};
		}

		List<CommerceWarehouse> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceWarehouse>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceWarehouse commerceWarehouse : list) {
					if ((groupId != commerceWarehouse.getGroupId()) ||
							(commerceCountryId != commerceWarehouse.getCommerceCountryId())) {
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

			query.append(_SQL_SELECT_COMMERCEWAREHOUSE_WHERE);

			query.append(_FINDER_COLUMN_G_C_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_COMMERCECOUNTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceWarehouseModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(commerceCountryId);

				if (!pagination) {
					list = (List<CommerceWarehouse>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceWarehouse>)QueryUtil.list(q,
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
	 * Returns the first commerce warehouse in the ordered set where groupId = &#63; and commerceCountryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param commerceCountryId the commerce country ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce warehouse
	 * @throws NoSuchWarehouseException if a matching commerce warehouse could not be found
	 */
	@Override
	public CommerceWarehouse findByG_C_First(long groupId,
		long commerceCountryId,
		OrderByComparator<CommerceWarehouse> orderByComparator)
		throws NoSuchWarehouseException {
		CommerceWarehouse commerceWarehouse = fetchByG_C_First(groupId,
				commerceCountryId, orderByComparator);

		if (commerceWarehouse != null) {
			return commerceWarehouse;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", commerceCountryId=");
		msg.append(commerceCountryId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchWarehouseException(msg.toString());
	}

	/**
	 * Returns the first commerce warehouse in the ordered set where groupId = &#63; and commerceCountryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param commerceCountryId the commerce country ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce warehouse, or <code>null</code> if a matching commerce warehouse could not be found
	 */
	@Override
	public CommerceWarehouse fetchByG_C_First(long groupId,
		long commerceCountryId,
		OrderByComparator<CommerceWarehouse> orderByComparator) {
		List<CommerceWarehouse> list = findByG_C(groupId, commerceCountryId, 0,
				1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce warehouse in the ordered set where groupId = &#63; and commerceCountryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param commerceCountryId the commerce country ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce warehouse
	 * @throws NoSuchWarehouseException if a matching commerce warehouse could not be found
	 */
	@Override
	public CommerceWarehouse findByG_C_Last(long groupId,
		long commerceCountryId,
		OrderByComparator<CommerceWarehouse> orderByComparator)
		throws NoSuchWarehouseException {
		CommerceWarehouse commerceWarehouse = fetchByG_C_Last(groupId,
				commerceCountryId, orderByComparator);

		if (commerceWarehouse != null) {
			return commerceWarehouse;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", commerceCountryId=");
		msg.append(commerceCountryId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchWarehouseException(msg.toString());
	}

	/**
	 * Returns the last commerce warehouse in the ordered set where groupId = &#63; and commerceCountryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param commerceCountryId the commerce country ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce warehouse, or <code>null</code> if a matching commerce warehouse could not be found
	 */
	@Override
	public CommerceWarehouse fetchByG_C_Last(long groupId,
		long commerceCountryId,
		OrderByComparator<CommerceWarehouse> orderByComparator) {
		int count = countByG_C(groupId, commerceCountryId);

		if (count == 0) {
			return null;
		}

		List<CommerceWarehouse> list = findByG_C(groupId, commerceCountryId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce warehouses before and after the current commerce warehouse in the ordered set where groupId = &#63; and commerceCountryId = &#63;.
	 *
	 * @param commerceWarehouseId the primary key of the current commerce warehouse
	 * @param groupId the group ID
	 * @param commerceCountryId the commerce country ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce warehouse
	 * @throws NoSuchWarehouseException if a commerce warehouse with the primary key could not be found
	 */
	@Override
	public CommerceWarehouse[] findByG_C_PrevAndNext(long commerceWarehouseId,
		long groupId, long commerceCountryId,
		OrderByComparator<CommerceWarehouse> orderByComparator)
		throws NoSuchWarehouseException {
		CommerceWarehouse commerceWarehouse = findByPrimaryKey(commerceWarehouseId);

		Session session = null;

		try {
			session = openSession();

			CommerceWarehouse[] array = new CommerceWarehouseImpl[3];

			array[0] = getByG_C_PrevAndNext(session, commerceWarehouse,
					groupId, commerceCountryId, orderByComparator, true);

			array[1] = commerceWarehouse;

			array[2] = getByG_C_PrevAndNext(session, commerceWarehouse,
					groupId, commerceCountryId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceWarehouse getByG_C_PrevAndNext(Session session,
		CommerceWarehouse commerceWarehouse, long groupId,
		long commerceCountryId,
		OrderByComparator<CommerceWarehouse> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_COMMERCEWAREHOUSE_WHERE);

		query.append(_FINDER_COLUMN_G_C_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_COMMERCECOUNTRYID_2);

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
			query.append(CommerceWarehouseModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(commerceCountryId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceWarehouse);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceWarehouse> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce warehouses where groupId = &#63; and commerceCountryId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param commerceCountryId the commerce country ID
	 */
	@Override
	public void removeByG_C(long groupId, long commerceCountryId) {
		for (CommerceWarehouse commerceWarehouse : findByG_C(groupId,
				commerceCountryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commerceWarehouse);
		}
	}

	/**
	 * Returns the number of commerce warehouses where groupId = &#63; and commerceCountryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param commerceCountryId the commerce country ID
	 * @return the number of matching commerce warehouses
	 */
	@Override
	public int countByG_C(long groupId, long commerceCountryId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_C;

		Object[] finderArgs = new Object[] { groupId, commerceCountryId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_COMMERCEWAREHOUSE_WHERE);

			query.append(_FINDER_COLUMN_G_C_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_COMMERCECOUNTRYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(commerceCountryId);

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

	private static final String _FINDER_COLUMN_G_C_GROUPID_2 = "commerceWarehouse.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_COMMERCECOUNTRYID_2 = "commerceWarehouse.commerceCountryId = ?";

	public CommerceWarehousePersistenceImpl() {
		setModelClass(CommerceWarehouse.class);
	}

	/**
	 * Caches the commerce warehouse in the entity cache if it is enabled.
	 *
	 * @param commerceWarehouse the commerce warehouse
	 */
	@Override
	public void cacheResult(CommerceWarehouse commerceWarehouse) {
		entityCache.putResult(CommerceWarehouseModelImpl.ENTITY_CACHE_ENABLED,
			CommerceWarehouseImpl.class, commerceWarehouse.getPrimaryKey(),
			commerceWarehouse);

		commerceWarehouse.resetOriginalValues();
	}

	/**
	 * Caches the commerce warehouses in the entity cache if it is enabled.
	 *
	 * @param commerceWarehouses the commerce warehouses
	 */
	@Override
	public void cacheResult(List<CommerceWarehouse> commerceWarehouses) {
		for (CommerceWarehouse commerceWarehouse : commerceWarehouses) {
			if (entityCache.getResult(
						CommerceWarehouseModelImpl.ENTITY_CACHE_ENABLED,
						CommerceWarehouseImpl.class,
						commerceWarehouse.getPrimaryKey()) == null) {
				cacheResult(commerceWarehouse);
			}
			else {
				commerceWarehouse.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all commerce warehouses.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceWarehouseImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the commerce warehouse.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CommerceWarehouse commerceWarehouse) {
		entityCache.removeResult(CommerceWarehouseModelImpl.ENTITY_CACHE_ENABLED,
			CommerceWarehouseImpl.class, commerceWarehouse.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<CommerceWarehouse> commerceWarehouses) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CommerceWarehouse commerceWarehouse : commerceWarehouses) {
			entityCache.removeResult(CommerceWarehouseModelImpl.ENTITY_CACHE_ENABLED,
				CommerceWarehouseImpl.class, commerceWarehouse.getPrimaryKey());
		}
	}

	/**
	 * Creates a new commerce warehouse with the primary key. Does not add the commerce warehouse to the database.
	 *
	 * @param commerceWarehouseId the primary key for the new commerce warehouse
	 * @return the new commerce warehouse
	 */
	@Override
	public CommerceWarehouse create(long commerceWarehouseId) {
		CommerceWarehouse commerceWarehouse = new CommerceWarehouseImpl();

		commerceWarehouse.setNew(true);
		commerceWarehouse.setPrimaryKey(commerceWarehouseId);

		commerceWarehouse.setCompanyId(companyProvider.getCompanyId());

		return commerceWarehouse;
	}

	/**
	 * Removes the commerce warehouse with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceWarehouseId the primary key of the commerce warehouse
	 * @return the commerce warehouse that was removed
	 * @throws NoSuchWarehouseException if a commerce warehouse with the primary key could not be found
	 */
	@Override
	public CommerceWarehouse remove(long commerceWarehouseId)
		throws NoSuchWarehouseException {
		return remove((Serializable)commerceWarehouseId);
	}

	/**
	 * Removes the commerce warehouse with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce warehouse
	 * @return the commerce warehouse that was removed
	 * @throws NoSuchWarehouseException if a commerce warehouse with the primary key could not be found
	 */
	@Override
	public CommerceWarehouse remove(Serializable primaryKey)
		throws NoSuchWarehouseException {
		Session session = null;

		try {
			session = openSession();

			CommerceWarehouse commerceWarehouse = (CommerceWarehouse)session.get(CommerceWarehouseImpl.class,
					primaryKey);

			if (commerceWarehouse == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchWarehouseException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(commerceWarehouse);
		}
		catch (NoSuchWarehouseException nsee) {
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
	protected CommerceWarehouse removeImpl(CommerceWarehouse commerceWarehouse) {
		commerceWarehouse = toUnwrappedModel(commerceWarehouse);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceWarehouse)) {
				commerceWarehouse = (CommerceWarehouse)session.get(CommerceWarehouseImpl.class,
						commerceWarehouse.getPrimaryKeyObj());
			}

			if (commerceWarehouse != null) {
				session.delete(commerceWarehouse);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (commerceWarehouse != null) {
			clearCache(commerceWarehouse);
		}

		return commerceWarehouse;
	}

	@Override
	public CommerceWarehouse updateImpl(CommerceWarehouse commerceWarehouse) {
		commerceWarehouse = toUnwrappedModel(commerceWarehouse);

		boolean isNew = commerceWarehouse.isNew();

		CommerceWarehouseModelImpl commerceWarehouseModelImpl = (CommerceWarehouseModelImpl)commerceWarehouse;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (commerceWarehouse.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceWarehouse.setCreateDate(now);
			}
			else {
				commerceWarehouse.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!commerceWarehouseModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceWarehouse.setModifiedDate(now);
			}
			else {
				commerceWarehouse.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (commerceWarehouse.isNew()) {
				session.save(commerceWarehouse);

				commerceWarehouse.setNew(false);
			}
			else {
				commerceWarehouse = (CommerceWarehouse)session.merge(commerceWarehouse);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!CommerceWarehouseModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] { commerceWarehouseModelImpl.getGroupId() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
				args);

			args = new Object[] {
					commerceWarehouseModelImpl.getGroupId(),
					commerceWarehouseModelImpl.getCommerceCountryId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_C, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((commerceWarehouseModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceWarehouseModelImpl.getOriginalGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] { commerceWarehouseModelImpl.getGroupId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((commerceWarehouseModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceWarehouseModelImpl.getOriginalGroupId(),
						commerceWarehouseModelImpl.getOriginalCommerceCountryId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C,
					args);

				args = new Object[] {
						commerceWarehouseModelImpl.getGroupId(),
						commerceWarehouseModelImpl.getCommerceCountryId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C,
					args);
			}
		}

		entityCache.putResult(CommerceWarehouseModelImpl.ENTITY_CACHE_ENABLED,
			CommerceWarehouseImpl.class, commerceWarehouse.getPrimaryKey(),
			commerceWarehouse, false);

		commerceWarehouse.resetOriginalValues();

		return commerceWarehouse;
	}

	protected CommerceWarehouse toUnwrappedModel(
		CommerceWarehouse commerceWarehouse) {
		if (commerceWarehouse instanceof CommerceWarehouseImpl) {
			return commerceWarehouse;
		}

		CommerceWarehouseImpl commerceWarehouseImpl = new CommerceWarehouseImpl();

		commerceWarehouseImpl.setNew(commerceWarehouse.isNew());
		commerceWarehouseImpl.setPrimaryKey(commerceWarehouse.getPrimaryKey());

		commerceWarehouseImpl.setCommerceWarehouseId(commerceWarehouse.getCommerceWarehouseId());
		commerceWarehouseImpl.setGroupId(commerceWarehouse.getGroupId());
		commerceWarehouseImpl.setCompanyId(commerceWarehouse.getCompanyId());
		commerceWarehouseImpl.setUserId(commerceWarehouse.getUserId());
		commerceWarehouseImpl.setUserName(commerceWarehouse.getUserName());
		commerceWarehouseImpl.setCreateDate(commerceWarehouse.getCreateDate());
		commerceWarehouseImpl.setModifiedDate(commerceWarehouse.getModifiedDate());
		commerceWarehouseImpl.setName(commerceWarehouse.getName());
		commerceWarehouseImpl.setDescription(commerceWarehouse.getDescription());
		commerceWarehouseImpl.setStreet1(commerceWarehouse.getStreet1());
		commerceWarehouseImpl.setStreet2(commerceWarehouse.getStreet2());
		commerceWarehouseImpl.setStreet3(commerceWarehouse.getStreet3());
		commerceWarehouseImpl.setCity(commerceWarehouse.getCity());
		commerceWarehouseImpl.setZip(commerceWarehouse.getZip());
		commerceWarehouseImpl.setCommerceRegionId(commerceWarehouse.getCommerceRegionId());
		commerceWarehouseImpl.setCommerceCountryId(commerceWarehouse.getCommerceCountryId());

		return commerceWarehouseImpl;
	}

	/**
	 * Returns the commerce warehouse with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce warehouse
	 * @return the commerce warehouse
	 * @throws NoSuchWarehouseException if a commerce warehouse with the primary key could not be found
	 */
	@Override
	public CommerceWarehouse findByPrimaryKey(Serializable primaryKey)
		throws NoSuchWarehouseException {
		CommerceWarehouse commerceWarehouse = fetchByPrimaryKey(primaryKey);

		if (commerceWarehouse == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchWarehouseException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return commerceWarehouse;
	}

	/**
	 * Returns the commerce warehouse with the primary key or throws a {@link NoSuchWarehouseException} if it could not be found.
	 *
	 * @param commerceWarehouseId the primary key of the commerce warehouse
	 * @return the commerce warehouse
	 * @throws NoSuchWarehouseException if a commerce warehouse with the primary key could not be found
	 */
	@Override
	public CommerceWarehouse findByPrimaryKey(long commerceWarehouseId)
		throws NoSuchWarehouseException {
		return findByPrimaryKey((Serializable)commerceWarehouseId);
	}

	/**
	 * Returns the commerce warehouse with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce warehouse
	 * @return the commerce warehouse, or <code>null</code> if a commerce warehouse with the primary key could not be found
	 */
	@Override
	public CommerceWarehouse fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(CommerceWarehouseModelImpl.ENTITY_CACHE_ENABLED,
				CommerceWarehouseImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		CommerceWarehouse commerceWarehouse = (CommerceWarehouse)serializable;

		if (commerceWarehouse == null) {
			Session session = null;

			try {
				session = openSession();

				commerceWarehouse = (CommerceWarehouse)session.get(CommerceWarehouseImpl.class,
						primaryKey);

				if (commerceWarehouse != null) {
					cacheResult(commerceWarehouse);
				}
				else {
					entityCache.putResult(CommerceWarehouseModelImpl.ENTITY_CACHE_ENABLED,
						CommerceWarehouseImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(CommerceWarehouseModelImpl.ENTITY_CACHE_ENABLED,
					CommerceWarehouseImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return commerceWarehouse;
	}

	/**
	 * Returns the commerce warehouse with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceWarehouseId the primary key of the commerce warehouse
	 * @return the commerce warehouse, or <code>null</code> if a commerce warehouse with the primary key could not be found
	 */
	@Override
	public CommerceWarehouse fetchByPrimaryKey(long commerceWarehouseId) {
		return fetchByPrimaryKey((Serializable)commerceWarehouseId);
	}

	@Override
	public Map<Serializable, CommerceWarehouse> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CommerceWarehouse> map = new HashMap<Serializable, CommerceWarehouse>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CommerceWarehouse commerceWarehouse = fetchByPrimaryKey(primaryKey);

			if (commerceWarehouse != null) {
				map.put(primaryKey, commerceWarehouse);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(CommerceWarehouseModelImpl.ENTITY_CACHE_ENABLED,
					CommerceWarehouseImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (CommerceWarehouse)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_COMMERCEWAREHOUSE_WHERE_PKS_IN);

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

			for (CommerceWarehouse commerceWarehouse : (List<CommerceWarehouse>)q.list()) {
				map.put(commerceWarehouse.getPrimaryKeyObj(), commerceWarehouse);

				cacheResult(commerceWarehouse);

				uncachedPrimaryKeys.remove(commerceWarehouse.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(CommerceWarehouseModelImpl.ENTITY_CACHE_ENABLED,
					CommerceWarehouseImpl.class, primaryKey, nullModel);
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
	 * Returns all the commerce warehouses.
	 *
	 * @return the commerce warehouses
	 */
	@Override
	public List<CommerceWarehouse> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce warehouses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceWarehouseModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce warehouses
	 * @param end the upper bound of the range of commerce warehouses (not inclusive)
	 * @return the range of commerce warehouses
	 */
	@Override
	public List<CommerceWarehouse> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce warehouses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceWarehouseModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce warehouses
	 * @param end the upper bound of the range of commerce warehouses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce warehouses
	 */
	@Override
	public List<CommerceWarehouse> findAll(int start, int end,
		OrderByComparator<CommerceWarehouse> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce warehouses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceWarehouseModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce warehouses
	 * @param end the upper bound of the range of commerce warehouses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of commerce warehouses
	 */
	@Override
	public List<CommerceWarehouse> findAll(int start, int end,
		OrderByComparator<CommerceWarehouse> orderByComparator,
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

		List<CommerceWarehouse> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceWarehouse>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_COMMERCEWAREHOUSE);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCEWAREHOUSE;

				if (pagination) {
					sql = sql.concat(CommerceWarehouseModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CommerceWarehouse>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceWarehouse>)QueryUtil.list(q,
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
	 * Removes all the commerce warehouses from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceWarehouse commerceWarehouse : findAll()) {
			remove(commerceWarehouse);
		}
	}

	/**
	 * Returns the number of commerce warehouses.
	 *
	 * @return the number of commerce warehouses
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_COMMERCEWAREHOUSE);

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
	protected Map<String, Integer> getTableColumnsMap() {
		return CommerceWarehouseModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce warehouse persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(CommerceWarehouseImpl.class.getName());
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
	private static final String _SQL_SELECT_COMMERCEWAREHOUSE = "SELECT commerceWarehouse FROM CommerceWarehouse commerceWarehouse";
	private static final String _SQL_SELECT_COMMERCEWAREHOUSE_WHERE_PKS_IN = "SELECT commerceWarehouse FROM CommerceWarehouse commerceWarehouse WHERE commerceWarehouseId IN (";
	private static final String _SQL_SELECT_COMMERCEWAREHOUSE_WHERE = "SELECT commerceWarehouse FROM CommerceWarehouse commerceWarehouse WHERE ";
	private static final String _SQL_COUNT_COMMERCEWAREHOUSE = "SELECT COUNT(commerceWarehouse) FROM CommerceWarehouse commerceWarehouse";
	private static final String _SQL_COUNT_COMMERCEWAREHOUSE_WHERE = "SELECT COUNT(commerceWarehouse) FROM CommerceWarehouse commerceWarehouse WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "commerceWarehouse.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CommerceWarehouse exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No CommerceWarehouse exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(CommerceWarehousePersistenceImpl.class);
}