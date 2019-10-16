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

package com.liferay.commerce.cart.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.cart.exception.NoSuchCartException;
import com.liferay.commerce.cart.model.CommerceCart;
import com.liferay.commerce.cart.model.impl.CommerceCartImpl;
import com.liferay.commerce.cart.model.impl.CommerceCartModelImpl;
import com.liferay.commerce.cart.service.persistence.CommerceCartPersistence;

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
import com.liferay.portal.kernel.util.StringUtil;
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
 * The persistence implementation for the commerce cart service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceCartPersistence
 * @see com.liferay.commerce.cart.service.persistence.CommerceCartUtil
 * @generated
 */
@ProviderType
public class CommerceCartPersistenceImpl extends BasePersistenceImpl<CommerceCart>
	implements CommerceCartPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CommerceCartUtil} to access the commerce cart persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CommerceCartImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CommerceCartModelImpl.ENTITY_CACHE_ENABLED,
			CommerceCartModelImpl.FINDER_CACHE_ENABLED, CommerceCartImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CommerceCartModelImpl.ENTITY_CACHE_ENABLED,
			CommerceCartModelImpl.FINDER_CACHE_ENABLED, CommerceCartImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CommerceCartModelImpl.ENTITY_CACHE_ENABLED,
			CommerceCartModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(CommerceCartModelImpl.ENTITY_CACHE_ENABLED,
			CommerceCartModelImpl.FINDER_CACHE_ENABLED, CommerceCartImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(CommerceCartModelImpl.ENTITY_CACHE_ENABLED,
			CommerceCartModelImpl.FINDER_CACHE_ENABLED, CommerceCartImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			CommerceCartModelImpl.UUID_COLUMN_BITMASK |
			CommerceCartModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(CommerceCartModelImpl.ENTITY_CACHE_ENABLED,
			CommerceCartModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });

	/**
	 * Returns all the commerce carts where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching commerce carts
	 */
	@Override
	public List<CommerceCart> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce carts where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce carts
	 * @param end the upper bound of the range of commerce carts (not inclusive)
	 * @return the range of matching commerce carts
	 */
	@Override
	public List<CommerceCart> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce carts where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce carts
	 * @param end the upper bound of the range of commerce carts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce carts
	 */
	@Override
	public List<CommerceCart> findByUuid(String uuid, int start, int end,
		OrderByComparator<CommerceCart> orderByComparator) {
		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce carts where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce carts
	 * @param end the upper bound of the range of commerce carts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce carts
	 */
	@Override
	public List<CommerceCart> findByUuid(String uuid, int start, int end,
		OrderByComparator<CommerceCart> orderByComparator,
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

		List<CommerceCart> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceCart>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceCart commerceCart : list) {
					if (!Objects.equals(uuid, commerceCart.getUuid())) {
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

			query.append(_SQL_SELECT_COMMERCECART_WHERE);

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
				query.append(CommerceCartModelImpl.ORDER_BY_JPQL);
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
					list = (List<CommerceCart>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceCart>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Returns the first commerce cart in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce cart
	 * @throws NoSuchCartException if a matching commerce cart could not be found
	 */
	@Override
	public CommerceCart findByUuid_First(String uuid,
		OrderByComparator<CommerceCart> orderByComparator)
		throws NoSuchCartException {
		CommerceCart commerceCart = fetchByUuid_First(uuid, orderByComparator);

		if (commerceCart != null) {
			return commerceCart;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCartException(msg.toString());
	}

	/**
	 * Returns the first commerce cart in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce cart, or <code>null</code> if a matching commerce cart could not be found
	 */
	@Override
	public CommerceCart fetchByUuid_First(String uuid,
		OrderByComparator<CommerceCart> orderByComparator) {
		List<CommerceCart> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce cart in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce cart
	 * @throws NoSuchCartException if a matching commerce cart could not be found
	 */
	@Override
	public CommerceCart findByUuid_Last(String uuid,
		OrderByComparator<CommerceCart> orderByComparator)
		throws NoSuchCartException {
		CommerceCart commerceCart = fetchByUuid_Last(uuid, orderByComparator);

		if (commerceCart != null) {
			return commerceCart;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCartException(msg.toString());
	}

	/**
	 * Returns the last commerce cart in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce cart, or <code>null</code> if a matching commerce cart could not be found
	 */
	@Override
	public CommerceCart fetchByUuid_Last(String uuid,
		OrderByComparator<CommerceCart> orderByComparator) {
		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<CommerceCart> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce carts before and after the current commerce cart in the ordered set where uuid = &#63;.
	 *
	 * @param CommerceCartId the primary key of the current commerce cart
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce cart
	 * @throws NoSuchCartException if a commerce cart with the primary key could not be found
	 */
	@Override
	public CommerceCart[] findByUuid_PrevAndNext(long CommerceCartId,
		String uuid, OrderByComparator<CommerceCart> orderByComparator)
		throws NoSuchCartException {
		CommerceCart commerceCart = findByPrimaryKey(CommerceCartId);

		Session session = null;

		try {
			session = openSession();

			CommerceCart[] array = new CommerceCartImpl[3];

			array[0] = getByUuid_PrevAndNext(session, commerceCart, uuid,
					orderByComparator, true);

			array[1] = commerceCart;

			array[2] = getByUuid_PrevAndNext(session, commerceCart, uuid,
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

	protected CommerceCart getByUuid_PrevAndNext(Session session,
		CommerceCart commerceCart, String uuid,
		OrderByComparator<CommerceCart> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_COMMERCECART_WHERE);

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
			query.append(CommerceCartModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(commerceCart);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceCart> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce carts where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (CommerceCart commerceCart : findByUuid(uuid, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(commerceCart);
		}
	}

	/**
	 * Returns the number of commerce carts where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce carts
	 */
	@Override
	public int countByUuid(String uuid) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID;

		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCECART_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_UUID_1 = "commerceCart.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "commerceCart.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(commerceCart.uuid IS NULL OR commerceCart.uuid = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(CommerceCartModelImpl.ENTITY_CACHE_ENABLED,
			CommerceCartModelImpl.FINDER_CACHE_ENABLED, CommerceCartImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() },
			CommerceCartModelImpl.UUID_COLUMN_BITMASK |
			CommerceCartModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(CommerceCartModelImpl.ENTITY_CACHE_ENABLED,
			CommerceCartModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns the commerce cart where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchCartException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching commerce cart
	 * @throws NoSuchCartException if a matching commerce cart could not be found
	 */
	@Override
	public CommerceCart findByUUID_G(String uuid, long groupId)
		throws NoSuchCartException {
		CommerceCart commerceCart = fetchByUUID_G(uuid, groupId);

		if (commerceCart == null) {
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

			throw new NoSuchCartException(msg.toString());
		}

		return commerceCart;
	}

	/**
	 * Returns the commerce cart where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching commerce cart, or <code>null</code> if a matching commerce cart could not be found
	 */
	@Override
	public CommerceCart fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the commerce cart where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching commerce cart, or <code>null</code> if a matching commerce cart could not be found
	 */
	@Override
	public CommerceCart fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result instanceof CommerceCart) {
			CommerceCart commerceCart = (CommerceCart)result;

			if (!Objects.equals(uuid, commerceCart.getUuid()) ||
					(groupId != commerceCart.getGroupId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_COMMERCECART_WHERE);

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

				List<CommerceCart> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					CommerceCart commerceCart = list.get(0);

					result = commerceCart;

					cacheResult(commerceCart);

					if ((commerceCart.getUuid() == null) ||
							!commerceCart.getUuid().equals(uuid) ||
							(commerceCart.getGroupId() != groupId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, commerceCart);
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
			return (CommerceCart)result;
		}
	}

	/**
	 * Removes the commerce cart where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the commerce cart that was removed
	 */
	@Override
	public CommerceCart removeByUUID_G(String uuid, long groupId)
		throws NoSuchCartException {
		CommerceCart commerceCart = findByUUID_G(uuid, groupId);

		return remove(commerceCart);
	}

	/**
	 * Returns the number of commerce carts where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching commerce carts
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_G;

		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_COMMERCECART_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "commerceCart.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "commerceCart.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(commerceCart.uuid IS NULL OR commerceCart.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "commerceCart.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(CommerceCartModelImpl.ENTITY_CACHE_ENABLED,
			CommerceCartModelImpl.FINDER_CACHE_ENABLED, CommerceCartImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(CommerceCartModelImpl.ENTITY_CACHE_ENABLED,
			CommerceCartModelImpl.FINDER_CACHE_ENABLED, CommerceCartImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			CommerceCartModelImpl.UUID_COLUMN_BITMASK |
			CommerceCartModelImpl.COMPANYID_COLUMN_BITMASK |
			CommerceCartModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(CommerceCartModelImpl.ENTITY_CACHE_ENABLED,
			CommerceCartModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns all the commerce carts where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce carts
	 */
	@Override
	public List<CommerceCart> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce carts where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce carts
	 * @param end the upper bound of the range of commerce carts (not inclusive)
	 * @return the range of matching commerce carts
	 */
	@Override
	public List<CommerceCart> findByUuid_C(String uuid, long companyId,
		int start, int end) {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce carts where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce carts
	 * @param end the upper bound of the range of commerce carts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce carts
	 */
	@Override
	public List<CommerceCart> findByUuid_C(String uuid, long companyId,
		int start, int end, OrderByComparator<CommerceCart> orderByComparator) {
		return findByUuid_C(uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce carts where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce carts
	 * @param end the upper bound of the range of commerce carts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce carts
	 */
	@Override
	public List<CommerceCart> findByUuid_C(String uuid, long companyId,
		int start, int end, OrderByComparator<CommerceCart> orderByComparator,
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

		List<CommerceCart> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceCart>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceCart commerceCart : list) {
					if (!Objects.equals(uuid, commerceCart.getUuid()) ||
							(companyId != commerceCart.getCompanyId())) {
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

			query.append(_SQL_SELECT_COMMERCECART_WHERE);

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
				query.append(CommerceCartModelImpl.ORDER_BY_JPQL);
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
					list = (List<CommerceCart>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceCart>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Returns the first commerce cart in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce cart
	 * @throws NoSuchCartException if a matching commerce cart could not be found
	 */
	@Override
	public CommerceCart findByUuid_C_First(String uuid, long companyId,
		OrderByComparator<CommerceCart> orderByComparator)
		throws NoSuchCartException {
		CommerceCart commerceCart = fetchByUuid_C_First(uuid, companyId,
				orderByComparator);

		if (commerceCart != null) {
			return commerceCart;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCartException(msg.toString());
	}

	/**
	 * Returns the first commerce cart in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce cart, or <code>null</code> if a matching commerce cart could not be found
	 */
	@Override
	public CommerceCart fetchByUuid_C_First(String uuid, long companyId,
		OrderByComparator<CommerceCart> orderByComparator) {
		List<CommerceCart> list = findByUuid_C(uuid, companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce cart in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce cart
	 * @throws NoSuchCartException if a matching commerce cart could not be found
	 */
	@Override
	public CommerceCart findByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<CommerceCart> orderByComparator)
		throws NoSuchCartException {
		CommerceCart commerceCart = fetchByUuid_C_Last(uuid, companyId,
				orderByComparator);

		if (commerceCart != null) {
			return commerceCart;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCartException(msg.toString());
	}

	/**
	 * Returns the last commerce cart in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce cart, or <code>null</code> if a matching commerce cart could not be found
	 */
	@Override
	public CommerceCart fetchByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<CommerceCart> orderByComparator) {
		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<CommerceCart> list = findByUuid_C(uuid, companyId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce carts before and after the current commerce cart in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param CommerceCartId the primary key of the current commerce cart
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce cart
	 * @throws NoSuchCartException if a commerce cart with the primary key could not be found
	 */
	@Override
	public CommerceCart[] findByUuid_C_PrevAndNext(long CommerceCartId,
		String uuid, long companyId,
		OrderByComparator<CommerceCart> orderByComparator)
		throws NoSuchCartException {
		CommerceCart commerceCart = findByPrimaryKey(CommerceCartId);

		Session session = null;

		try {
			session = openSession();

			CommerceCart[] array = new CommerceCartImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session, commerceCart, uuid,
					companyId, orderByComparator, true);

			array[1] = commerceCart;

			array[2] = getByUuid_C_PrevAndNext(session, commerceCart, uuid,
					companyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceCart getByUuid_C_PrevAndNext(Session session,
		CommerceCart commerceCart, String uuid, long companyId,
		OrderByComparator<CommerceCart> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_COMMERCECART_WHERE);

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
			query.append(CommerceCartModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(commerceCart);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceCart> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce carts where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (CommerceCart commerceCart : findByUuid_C(uuid, companyId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commerceCart);
		}
	}

	/**
	 * Returns the number of commerce carts where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce carts
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_C;

		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_COMMERCECART_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "commerceCart.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "commerceCart.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(commerceCart.uuid IS NULL OR commerceCart.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "commerceCart.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_TYPE = new FinderPath(CommerceCartModelImpl.ENTITY_CACHE_ENABLED,
			CommerceCartModelImpl.FINDER_CACHE_ENABLED, CommerceCartImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByType",
			new String[] {
				Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TYPE = new FinderPath(CommerceCartModelImpl.ENTITY_CACHE_ENABLED,
			CommerceCartModelImpl.FINDER_CACHE_ENABLED, CommerceCartImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByType",
			new String[] { Integer.class.getName() },
			CommerceCartModelImpl.TYPE_COLUMN_BITMASK |
			CommerceCartModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_TYPE = new FinderPath(CommerceCartModelImpl.ENTITY_CACHE_ENABLED,
			CommerceCartModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByType",
			new String[] { Integer.class.getName() });

	/**
	 * Returns all the commerce carts where type = &#63;.
	 *
	 * @param type the type
	 * @return the matching commerce carts
	 */
	@Override
	public List<CommerceCart> findByType(int type) {
		return findByType(type, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce carts where type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param type the type
	 * @param start the lower bound of the range of commerce carts
	 * @param end the upper bound of the range of commerce carts (not inclusive)
	 * @return the range of matching commerce carts
	 */
	@Override
	public List<CommerceCart> findByType(int type, int start, int end) {
		return findByType(type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce carts where type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param type the type
	 * @param start the lower bound of the range of commerce carts
	 * @param end the upper bound of the range of commerce carts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce carts
	 */
	@Override
	public List<CommerceCart> findByType(int type, int start, int end,
		OrderByComparator<CommerceCart> orderByComparator) {
		return findByType(type, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce carts where type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param type the type
	 * @param start the lower bound of the range of commerce carts
	 * @param end the upper bound of the range of commerce carts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce carts
	 */
	@Override
	public List<CommerceCart> findByType(int type, int start, int end,
		OrderByComparator<CommerceCart> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TYPE;
			finderArgs = new Object[] { type };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_TYPE;
			finderArgs = new Object[] { type, start, end, orderByComparator };
		}

		List<CommerceCart> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceCart>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceCart commerceCart : list) {
					if ((type != commerceCart.getType())) {
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

			query.append(_SQL_SELECT_COMMERCECART_WHERE);

			query.append(_FINDER_COLUMN_TYPE_TYPE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceCartModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(type);

				if (!pagination) {
					list = (List<CommerceCart>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceCart>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Returns the first commerce cart in the ordered set where type = &#63;.
	 *
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce cart
	 * @throws NoSuchCartException if a matching commerce cart could not be found
	 */
	@Override
	public CommerceCart findByType_First(int type,
		OrderByComparator<CommerceCart> orderByComparator)
		throws NoSuchCartException {
		CommerceCart commerceCart = fetchByType_First(type, orderByComparator);

		if (commerceCart != null) {
			return commerceCart;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("type=");
		msg.append(type);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCartException(msg.toString());
	}

	/**
	 * Returns the first commerce cart in the ordered set where type = &#63;.
	 *
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce cart, or <code>null</code> if a matching commerce cart could not be found
	 */
	@Override
	public CommerceCart fetchByType_First(int type,
		OrderByComparator<CommerceCart> orderByComparator) {
		List<CommerceCart> list = findByType(type, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce cart in the ordered set where type = &#63;.
	 *
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce cart
	 * @throws NoSuchCartException if a matching commerce cart could not be found
	 */
	@Override
	public CommerceCart findByType_Last(int type,
		OrderByComparator<CommerceCart> orderByComparator)
		throws NoSuchCartException {
		CommerceCart commerceCart = fetchByType_Last(type, orderByComparator);

		if (commerceCart != null) {
			return commerceCart;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("type=");
		msg.append(type);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCartException(msg.toString());
	}

	/**
	 * Returns the last commerce cart in the ordered set where type = &#63;.
	 *
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce cart, or <code>null</code> if a matching commerce cart could not be found
	 */
	@Override
	public CommerceCart fetchByType_Last(int type,
		OrderByComparator<CommerceCart> orderByComparator) {
		int count = countByType(type);

		if (count == 0) {
			return null;
		}

		List<CommerceCart> list = findByType(type, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce carts before and after the current commerce cart in the ordered set where type = &#63;.
	 *
	 * @param CommerceCartId the primary key of the current commerce cart
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce cart
	 * @throws NoSuchCartException if a commerce cart with the primary key could not be found
	 */
	@Override
	public CommerceCart[] findByType_PrevAndNext(long CommerceCartId, int type,
		OrderByComparator<CommerceCart> orderByComparator)
		throws NoSuchCartException {
		CommerceCart commerceCart = findByPrimaryKey(CommerceCartId);

		Session session = null;

		try {
			session = openSession();

			CommerceCart[] array = new CommerceCartImpl[3];

			array[0] = getByType_PrevAndNext(session, commerceCart, type,
					orderByComparator, true);

			array[1] = commerceCart;

			array[2] = getByType_PrevAndNext(session, commerceCart, type,
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

	protected CommerceCart getByType_PrevAndNext(Session session,
		CommerceCart commerceCart, int type,
		OrderByComparator<CommerceCart> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_COMMERCECART_WHERE);

		query.append(_FINDER_COLUMN_TYPE_TYPE_2);

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
			query.append(CommerceCartModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(type);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceCart);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceCart> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce carts where type = &#63; from the database.
	 *
	 * @param type the type
	 */
	@Override
	public void removeByType(int type) {
		for (CommerceCart commerceCart : findByType(type, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(commerceCart);
		}
	}

	/**
	 * Returns the number of commerce carts where type = &#63;.
	 *
	 * @param type the type
	 * @return the number of matching commerce carts
	 */
	@Override
	public int countByType(int type) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_TYPE;

		Object[] finderArgs = new Object[] { type };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCECART_WHERE);

			query.append(_FINDER_COLUMN_TYPE_TYPE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(type);

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

	private static final String _FINDER_COLUMN_TYPE_TYPE_2 = "commerceCart.type = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_G_U_T = new FinderPath(CommerceCartModelImpl.ENTITY_CACHE_ENABLED,
			CommerceCartModelImpl.FINDER_CACHE_ENABLED, CommerceCartImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByG_U_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			CommerceCartModelImpl.GROUPID_COLUMN_BITMASK |
			CommerceCartModelImpl.USERID_COLUMN_BITMASK |
			CommerceCartModelImpl.TYPE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_U_T = new FinderPath(CommerceCartModelImpl.ENTITY_CACHE_ENABLED,
			CommerceCartModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_U_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});

	/**
	 * Returns the commerce cart where groupId = &#63; and userId = &#63; and type = &#63; or throws a {@link NoSuchCartException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param type the type
	 * @return the matching commerce cart
	 * @throws NoSuchCartException if a matching commerce cart could not be found
	 */
	@Override
	public CommerceCart findByG_U_T(long groupId, long userId, int type)
		throws NoSuchCartException {
		CommerceCart commerceCart = fetchByG_U_T(groupId, userId, type);

		if (commerceCart == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", userId=");
			msg.append(userId);

			msg.append(", type=");
			msg.append(type);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchCartException(msg.toString());
		}

		return commerceCart;
	}

	/**
	 * Returns the commerce cart where groupId = &#63; and userId = &#63; and type = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param type the type
	 * @return the matching commerce cart, or <code>null</code> if a matching commerce cart could not be found
	 */
	@Override
	public CommerceCart fetchByG_U_T(long groupId, long userId, int type) {
		return fetchByG_U_T(groupId, userId, type, true);
	}

	/**
	 * Returns the commerce cart where groupId = &#63; and userId = &#63; and type = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param type the type
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching commerce cart, or <code>null</code> if a matching commerce cart could not be found
	 */
	@Override
	public CommerceCart fetchByG_U_T(long groupId, long userId, int type,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { groupId, userId, type };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_G_U_T,
					finderArgs, this);
		}

		if (result instanceof CommerceCart) {
			CommerceCart commerceCart = (CommerceCart)result;

			if ((groupId != commerceCart.getGroupId()) ||
					(userId != commerceCart.getUserId()) ||
					(type != commerceCart.getType())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_COMMERCECART_WHERE);

			query.append(_FINDER_COLUMN_G_U_T_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_T_USERID_2);

			query.append(_FINDER_COLUMN_G_U_T_TYPE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				qPos.add(type);

				List<CommerceCart> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_G_U_T,
						finderArgs, list);
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							_log.warn(
								"CommerceCartPersistenceImpl.fetchByG_U_T(long, long, int, boolean) with parameters (" +
								StringUtil.merge(finderArgs) +
								") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					CommerceCart commerceCart = list.get(0);

					result = commerceCart;

					cacheResult(commerceCart);

					if ((commerceCart.getGroupId() != groupId) ||
							(commerceCart.getUserId() != userId) ||
							(commerceCart.getType() != type)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_G_U_T,
							finderArgs, commerceCart);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_G_U_T, finderArgs);

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
			return (CommerceCart)result;
		}
	}

	/**
	 * Removes the commerce cart where groupId = &#63; and userId = &#63; and type = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param type the type
	 * @return the commerce cart that was removed
	 */
	@Override
	public CommerceCart removeByG_U_T(long groupId, long userId, int type)
		throws NoSuchCartException {
		CommerceCart commerceCart = findByG_U_T(groupId, userId, type);

		return remove(commerceCart);
	}

	/**
	 * Returns the number of commerce carts where groupId = &#63; and userId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param type the type
	 * @return the number of matching commerce carts
	 */
	@Override
	public int countByG_U_T(long groupId, long userId, int type) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_U_T;

		Object[] finderArgs = new Object[] { groupId, userId, type };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_COMMERCECART_WHERE);

			query.append(_FINDER_COLUMN_G_U_T_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_T_USERID_2);

			query.append(_FINDER_COLUMN_G_U_T_TYPE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				qPos.add(type);

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

	private static final String _FINDER_COLUMN_G_U_T_GROUPID_2 = "commerceCart.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_T_USERID_2 = "commerceCart.userId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_T_TYPE_2 = "commerceCart.type = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_U_N_T = new FinderPath(CommerceCartModelImpl.ENTITY_CACHE_ENABLED,
			CommerceCartModelImpl.FINDER_CACHE_ENABLED, CommerceCartImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByU_N_T",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName()
			},
			CommerceCartModelImpl.USERID_COLUMN_BITMASK |
			CommerceCartModelImpl.NAME_COLUMN_BITMASK |
			CommerceCartModelImpl.TYPE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_U_N_T = new FinderPath(CommerceCartModelImpl.ENTITY_CACHE_ENABLED,
			CommerceCartModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_N_T",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName()
			});

	/**
	 * Returns the commerce cart where userId = &#63; and name = &#63; and type = &#63; or throws a {@link NoSuchCartException} if it could not be found.
	 *
	 * @param userId the user ID
	 * @param name the name
	 * @param type the type
	 * @return the matching commerce cart
	 * @throws NoSuchCartException if a matching commerce cart could not be found
	 */
	@Override
	public CommerceCart findByU_N_T(long userId, String name, int type)
		throws NoSuchCartException {
		CommerceCart commerceCart = fetchByU_N_T(userId, name, type);

		if (commerceCart == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(", name=");
			msg.append(name);

			msg.append(", type=");
			msg.append(type);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchCartException(msg.toString());
		}

		return commerceCart;
	}

	/**
	 * Returns the commerce cart where userId = &#63; and name = &#63; and type = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param userId the user ID
	 * @param name the name
	 * @param type the type
	 * @return the matching commerce cart, or <code>null</code> if a matching commerce cart could not be found
	 */
	@Override
	public CommerceCart fetchByU_N_T(long userId, String name, int type) {
		return fetchByU_N_T(userId, name, type, true);
	}

	/**
	 * Returns the commerce cart where userId = &#63; and name = &#63; and type = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param userId the user ID
	 * @param name the name
	 * @param type the type
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching commerce cart, or <code>null</code> if a matching commerce cart could not be found
	 */
	@Override
	public CommerceCart fetchByU_N_T(long userId, String name, int type,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { userId, name, type };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_U_N_T,
					finderArgs, this);
		}

		if (result instanceof CommerceCart) {
			CommerceCart commerceCart = (CommerceCart)result;

			if ((userId != commerceCart.getUserId()) ||
					!Objects.equals(name, commerceCart.getName()) ||
					(type != commerceCart.getType())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_COMMERCECART_WHERE);

			query.append(_FINDER_COLUMN_U_N_T_USERID_2);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_U_N_T_NAME_1);
			}
			else if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_U_N_T_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_U_N_T_NAME_2);
			}

			query.append(_FINDER_COLUMN_U_N_T_TYPE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				if (bindName) {
					qPos.add(name);
				}

				qPos.add(type);

				List<CommerceCart> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_U_N_T,
						finderArgs, list);
				}
				else {
					CommerceCart commerceCart = list.get(0);

					result = commerceCart;

					cacheResult(commerceCart);

					if ((commerceCart.getUserId() != userId) ||
							(commerceCart.getName() == null) ||
							!commerceCart.getName().equals(name) ||
							(commerceCart.getType() != type)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_U_N_T,
							finderArgs, commerceCart);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_U_N_T, finderArgs);

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
			return (CommerceCart)result;
		}
	}

	/**
	 * Removes the commerce cart where userId = &#63; and name = &#63; and type = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param name the name
	 * @param type the type
	 * @return the commerce cart that was removed
	 */
	@Override
	public CommerceCart removeByU_N_T(long userId, String name, int type)
		throws NoSuchCartException {
		CommerceCart commerceCart = findByU_N_T(userId, name, type);

		return remove(commerceCart);
	}

	/**
	 * Returns the number of commerce carts where userId = &#63; and name = &#63; and type = &#63;.
	 *
	 * @param userId the user ID
	 * @param name the name
	 * @param type the type
	 * @return the number of matching commerce carts
	 */
	@Override
	public int countByU_N_T(long userId, String name, int type) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_U_N_T;

		Object[] finderArgs = new Object[] { userId, name, type };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_COMMERCECART_WHERE);

			query.append(_FINDER_COLUMN_U_N_T_USERID_2);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_U_N_T_NAME_1);
			}
			else if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_U_N_T_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_U_N_T_NAME_2);
			}

			query.append(_FINDER_COLUMN_U_N_T_TYPE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				if (bindName) {
					qPos.add(name);
				}

				qPos.add(type);

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

	private static final String _FINDER_COLUMN_U_N_T_USERID_2 = "commerceCart.userId = ? AND ";
	private static final String _FINDER_COLUMN_U_N_T_NAME_1 = "commerceCart.name IS NULL AND ";
	private static final String _FINDER_COLUMN_U_N_T_NAME_2 = "commerceCart.name = ? AND ";
	private static final String _FINDER_COLUMN_U_N_T_NAME_3 = "(commerceCart.name IS NULL OR commerceCart.name = '') AND ";
	private static final String _FINDER_COLUMN_U_N_T_TYPE_2 = "commerceCart.type = ?";

	public CommerceCartPersistenceImpl() {
		setModelClass(CommerceCart.class);

		try {
			Field field = ReflectionUtil.getDeclaredField(BasePersistenceImpl.class,
					"_dbColumnNames");

			Map<String, String> dbColumnNames = new HashMap<String, String>();

			dbColumnNames.put("uuid", "uuid_");
			dbColumnNames.put("type", "type_");

			field.set(this, dbColumnNames);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}
	}

	/**
	 * Caches the commerce cart in the entity cache if it is enabled.
	 *
	 * @param commerceCart the commerce cart
	 */
	@Override
	public void cacheResult(CommerceCart commerceCart) {
		entityCache.putResult(CommerceCartModelImpl.ENTITY_CACHE_ENABLED,
			CommerceCartImpl.class, commerceCart.getPrimaryKey(), commerceCart);

		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] { commerceCart.getUuid(), commerceCart.getGroupId() },
			commerceCart);

		finderCache.putResult(FINDER_PATH_FETCH_BY_G_U_T,
			new Object[] {
				commerceCart.getGroupId(), commerceCart.getUserId(),
				commerceCart.getType()
			}, commerceCart);

		finderCache.putResult(FINDER_PATH_FETCH_BY_U_N_T,
			new Object[] {
				commerceCart.getUserId(), commerceCart.getName(),
				commerceCart.getType()
			}, commerceCart);

		commerceCart.resetOriginalValues();
	}

	/**
	 * Caches the commerce carts in the entity cache if it is enabled.
	 *
	 * @param commerceCarts the commerce carts
	 */
	@Override
	public void cacheResult(List<CommerceCart> commerceCarts) {
		for (CommerceCart commerceCart : commerceCarts) {
			if (entityCache.getResult(
						CommerceCartModelImpl.ENTITY_CACHE_ENABLED,
						CommerceCartImpl.class, commerceCart.getPrimaryKey()) == null) {
				cacheResult(commerceCart);
			}
			else {
				commerceCart.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all commerce carts.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceCartImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the commerce cart.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CommerceCart commerceCart) {
		entityCache.removeResult(CommerceCartModelImpl.ENTITY_CACHE_ENABLED,
			CommerceCartImpl.class, commerceCart.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((CommerceCartModelImpl)commerceCart, true);
	}

	@Override
	public void clearCache(List<CommerceCart> commerceCarts) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CommerceCart commerceCart : commerceCarts) {
			entityCache.removeResult(CommerceCartModelImpl.ENTITY_CACHE_ENABLED,
				CommerceCartImpl.class, commerceCart.getPrimaryKey());

			clearUniqueFindersCache((CommerceCartModelImpl)commerceCart, true);
		}
	}

	protected void cacheUniqueFindersCache(
		CommerceCartModelImpl commerceCartModelImpl) {
		Object[] args = new Object[] {
				commerceCartModelImpl.getUuid(),
				commerceCartModelImpl.getGroupId()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_UUID_G, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G, args,
			commerceCartModelImpl, false);

		args = new Object[] {
				commerceCartModelImpl.getGroupId(),
				commerceCartModelImpl.getUserId(),
				commerceCartModelImpl.getType()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_G_U_T, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_G_U_T, args,
			commerceCartModelImpl, false);

		args = new Object[] {
				commerceCartModelImpl.getUserId(),
				commerceCartModelImpl.getName(), commerceCartModelImpl.getType()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_U_N_T, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_U_N_T, args,
			commerceCartModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		CommerceCartModelImpl commerceCartModelImpl, boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					commerceCartModelImpl.getUuid(),
					commerceCartModelImpl.getGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}

		if ((commerceCartModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					commerceCartModelImpl.getOriginalUuid(),
					commerceCartModelImpl.getOriginalGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
					commerceCartModelImpl.getGroupId(),
					commerceCartModelImpl.getUserId(),
					commerceCartModelImpl.getType()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_U_T, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_U_T, args);
		}

		if ((commerceCartModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_G_U_T.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					commerceCartModelImpl.getOriginalGroupId(),
					commerceCartModelImpl.getOriginalUserId(),
					commerceCartModelImpl.getOriginalType()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_U_T, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_U_T, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
					commerceCartModelImpl.getUserId(),
					commerceCartModelImpl.getName(),
					commerceCartModelImpl.getType()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_U_N_T, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_U_N_T, args);
		}

		if ((commerceCartModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_U_N_T.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					commerceCartModelImpl.getOriginalUserId(),
					commerceCartModelImpl.getOriginalName(),
					commerceCartModelImpl.getOriginalType()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_U_N_T, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_U_N_T, args);
		}
	}

	/**
	 * Creates a new commerce cart with the primary key. Does not add the commerce cart to the database.
	 *
	 * @param CommerceCartId the primary key for the new commerce cart
	 * @return the new commerce cart
	 */
	@Override
	public CommerceCart create(long CommerceCartId) {
		CommerceCart commerceCart = new CommerceCartImpl();

		commerceCart.setNew(true);
		commerceCart.setPrimaryKey(CommerceCartId);

		String uuid = PortalUUIDUtil.generate();

		commerceCart.setUuid(uuid);

		commerceCart.setCompanyId(companyProvider.getCompanyId());

		return commerceCart;
	}

	/**
	 * Removes the commerce cart with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CommerceCartId the primary key of the commerce cart
	 * @return the commerce cart that was removed
	 * @throws NoSuchCartException if a commerce cart with the primary key could not be found
	 */
	@Override
	public CommerceCart remove(long CommerceCartId) throws NoSuchCartException {
		return remove((Serializable)CommerceCartId);
	}

	/**
	 * Removes the commerce cart with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce cart
	 * @return the commerce cart that was removed
	 * @throws NoSuchCartException if a commerce cart with the primary key could not be found
	 */
	@Override
	public CommerceCart remove(Serializable primaryKey)
		throws NoSuchCartException {
		Session session = null;

		try {
			session = openSession();

			CommerceCart commerceCart = (CommerceCart)session.get(CommerceCartImpl.class,
					primaryKey);

			if (commerceCart == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCartException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(commerceCart);
		}
		catch (NoSuchCartException nsee) {
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
	protected CommerceCart removeImpl(CommerceCart commerceCart) {
		commerceCart = toUnwrappedModel(commerceCart);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceCart)) {
				commerceCart = (CommerceCart)session.get(CommerceCartImpl.class,
						commerceCart.getPrimaryKeyObj());
			}

			if (commerceCart != null) {
				session.delete(commerceCart);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (commerceCart != null) {
			clearCache(commerceCart);
		}

		return commerceCart;
	}

	@Override
	public CommerceCart updateImpl(CommerceCart commerceCart) {
		commerceCart = toUnwrappedModel(commerceCart);

		boolean isNew = commerceCart.isNew();

		CommerceCartModelImpl commerceCartModelImpl = (CommerceCartModelImpl)commerceCart;

		if (Validator.isNull(commerceCart.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			commerceCart.setUuid(uuid);
		}

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (commerceCart.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceCart.setCreateDate(now);
			}
			else {
				commerceCart.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!commerceCartModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceCart.setModifiedDate(now);
			}
			else {
				commerceCart.setModifiedDate(serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (commerceCart.isNew()) {
				session.save(commerceCart);

				commerceCart.setNew(false);
			}
			else {
				commerceCart = (CommerceCart)session.merge(commerceCart);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!CommerceCartModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] { commerceCartModelImpl.getUuid() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
				args);

			args = new Object[] {
					commerceCartModelImpl.getUuid(),
					commerceCartModelImpl.getCompanyId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
				args);

			args = new Object[] { commerceCartModelImpl.getType() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_TYPE, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TYPE,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((commerceCartModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceCartModelImpl.getOriginalUuid()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { commerceCartModelImpl.getUuid() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((commerceCartModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceCartModelImpl.getOriginalUuid(),
						commerceCartModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						commerceCartModelImpl.getUuid(),
						commerceCartModelImpl.getCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((commerceCartModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TYPE.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceCartModelImpl.getOriginalType()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_TYPE, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TYPE,
					args);

				args = new Object[] { commerceCartModelImpl.getType() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_TYPE, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TYPE,
					args);
			}
		}

		entityCache.putResult(CommerceCartModelImpl.ENTITY_CACHE_ENABLED,
			CommerceCartImpl.class, commerceCart.getPrimaryKey(), commerceCart,
			false);

		clearUniqueFindersCache(commerceCartModelImpl, false);
		cacheUniqueFindersCache(commerceCartModelImpl);

		commerceCart.resetOriginalValues();

		return commerceCart;
	}

	protected CommerceCart toUnwrappedModel(CommerceCart commerceCart) {
		if (commerceCart instanceof CommerceCartImpl) {
			return commerceCart;
		}

		CommerceCartImpl commerceCartImpl = new CommerceCartImpl();

		commerceCartImpl.setNew(commerceCart.isNew());
		commerceCartImpl.setPrimaryKey(commerceCart.getPrimaryKey());

		commerceCartImpl.setUuid(commerceCart.getUuid());
		commerceCartImpl.setCommerceCartId(commerceCart.getCommerceCartId());
		commerceCartImpl.setGroupId(commerceCart.getGroupId());
		commerceCartImpl.setCompanyId(commerceCart.getCompanyId());
		commerceCartImpl.setUserId(commerceCart.getUserId());
		commerceCartImpl.setUserName(commerceCart.getUserName());
		commerceCartImpl.setCreateDate(commerceCart.getCreateDate());
		commerceCartImpl.setModifiedDate(commerceCart.getModifiedDate());
		commerceCartImpl.setCartUserId(commerceCart.getCartUserId());
		commerceCartImpl.setName(commerceCart.getName());
		commerceCartImpl.setType(commerceCart.getType());

		return commerceCartImpl;
	}

	/**
	 * Returns the commerce cart with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce cart
	 * @return the commerce cart
	 * @throws NoSuchCartException if a commerce cart with the primary key could not be found
	 */
	@Override
	public CommerceCart findByPrimaryKey(Serializable primaryKey)
		throws NoSuchCartException {
		CommerceCart commerceCart = fetchByPrimaryKey(primaryKey);

		if (commerceCart == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCartException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return commerceCart;
	}

	/**
	 * Returns the commerce cart with the primary key or throws a {@link NoSuchCartException} if it could not be found.
	 *
	 * @param CommerceCartId the primary key of the commerce cart
	 * @return the commerce cart
	 * @throws NoSuchCartException if a commerce cart with the primary key could not be found
	 */
	@Override
	public CommerceCart findByPrimaryKey(long CommerceCartId)
		throws NoSuchCartException {
		return findByPrimaryKey((Serializable)CommerceCartId);
	}

	/**
	 * Returns the commerce cart with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce cart
	 * @return the commerce cart, or <code>null</code> if a commerce cart with the primary key could not be found
	 */
	@Override
	public CommerceCart fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(CommerceCartModelImpl.ENTITY_CACHE_ENABLED,
				CommerceCartImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		CommerceCart commerceCart = (CommerceCart)serializable;

		if (commerceCart == null) {
			Session session = null;

			try {
				session = openSession();

				commerceCart = (CommerceCart)session.get(CommerceCartImpl.class,
						primaryKey);

				if (commerceCart != null) {
					cacheResult(commerceCart);
				}
				else {
					entityCache.putResult(CommerceCartModelImpl.ENTITY_CACHE_ENABLED,
						CommerceCartImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(CommerceCartModelImpl.ENTITY_CACHE_ENABLED,
					CommerceCartImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return commerceCart;
	}

	/**
	 * Returns the commerce cart with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CommerceCartId the primary key of the commerce cart
	 * @return the commerce cart, or <code>null</code> if a commerce cart with the primary key could not be found
	 */
	@Override
	public CommerceCart fetchByPrimaryKey(long CommerceCartId) {
		return fetchByPrimaryKey((Serializable)CommerceCartId);
	}

	@Override
	public Map<Serializable, CommerceCart> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CommerceCart> map = new HashMap<Serializable, CommerceCart>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CommerceCart commerceCart = fetchByPrimaryKey(primaryKey);

			if (commerceCart != null) {
				map.put(primaryKey, commerceCart);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(CommerceCartModelImpl.ENTITY_CACHE_ENABLED,
					CommerceCartImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (CommerceCart)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_COMMERCECART_WHERE_PKS_IN);

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

			for (CommerceCart commerceCart : (List<CommerceCart>)q.list()) {
				map.put(commerceCart.getPrimaryKeyObj(), commerceCart);

				cacheResult(commerceCart);

				uncachedPrimaryKeys.remove(commerceCart.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(CommerceCartModelImpl.ENTITY_CACHE_ENABLED,
					CommerceCartImpl.class, primaryKey, nullModel);
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
	 * Returns all the commerce carts.
	 *
	 * @return the commerce carts
	 */
	@Override
	public List<CommerceCart> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce carts.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce carts
	 * @param end the upper bound of the range of commerce carts (not inclusive)
	 * @return the range of commerce carts
	 */
	@Override
	public List<CommerceCart> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce carts.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce carts
	 * @param end the upper bound of the range of commerce carts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce carts
	 */
	@Override
	public List<CommerceCart> findAll(int start, int end,
		OrderByComparator<CommerceCart> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce carts.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce carts
	 * @param end the upper bound of the range of commerce carts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of commerce carts
	 */
	@Override
	public List<CommerceCart> findAll(int start, int end,
		OrderByComparator<CommerceCart> orderByComparator,
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

		List<CommerceCart> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceCart>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_COMMERCECART);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCECART;

				if (pagination) {
					sql = sql.concat(CommerceCartModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CommerceCart>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceCart>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Removes all the commerce carts from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceCart commerceCart : findAll()) {
			remove(commerceCart);
		}
	}

	/**
	 * Returns the number of commerce carts.
	 *
	 * @return the number of commerce carts
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_COMMERCECART);

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
		return CommerceCartModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce cart persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(CommerceCartImpl.class.getName());
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
	private static final String _SQL_SELECT_COMMERCECART = "SELECT commerceCart FROM CommerceCart commerceCart";
	private static final String _SQL_SELECT_COMMERCECART_WHERE_PKS_IN = "SELECT commerceCart FROM CommerceCart commerceCart WHERE CommerceCartId IN (";
	private static final String _SQL_SELECT_COMMERCECART_WHERE = "SELECT commerceCart FROM CommerceCart commerceCart WHERE ";
	private static final String _SQL_COUNT_COMMERCECART = "SELECT COUNT(commerceCart) FROM CommerceCart commerceCart";
	private static final String _SQL_COUNT_COMMERCECART_WHERE = "SELECT COUNT(commerceCart) FROM CommerceCart commerceCart WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "commerceCart.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CommerceCart exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No CommerceCart exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(CommerceCartPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"uuid", "type"
			});
}