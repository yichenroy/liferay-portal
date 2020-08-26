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

package com.liferay.dynamic.data.mapping.service.persistence.impl;

import com.liferay.dynamic.data.mapping.exception.NoSuchStructureLayoutException;
import com.liferay.dynamic.data.mapping.model.DDMStructureLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructureLayoutTable;
import com.liferay.dynamic.data.mapping.model.impl.DDMStructureLayoutImpl;
import com.liferay.dynamic.data.mapping.model.impl.DDMStructureLayoutModelImpl;
import com.liferay.dynamic.data.mapping.service.persistence.DDMStructureLayoutPersistence;
import com.liferay.dynamic.data.mapping.service.persistence.impl.constants.DDMPersistenceConstants;
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
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.change.tracking.helper.CTPersistenceHelper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
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
 * The persistence implementation for the ddm structure layout service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = DDMStructureLayoutPersistence.class)
public class DDMStructureLayoutPersistenceImpl
	extends BasePersistenceImpl<DDMStructureLayout>
	implements DDMStructureLayoutPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>DDMStructureLayoutUtil</code> to access the ddm structure layout persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		DDMStructureLayoutImpl.class.getName();

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
	 * Returns all the ddm structure layouts where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching ddm structure layouts
	 */
	@Override
	public List<DDMStructureLayout> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ddm structure layouts where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of ddm structure layouts
	 * @param end the upper bound of the range of ddm structure layouts (not inclusive)
	 * @return the range of matching ddm structure layouts
	 */
	@Override
	public List<DDMStructureLayout> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ddm structure layouts where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of ddm structure layouts
	 * @param end the upper bound of the range of ddm structure layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm structure layouts
	 */
	@Override
	public List<DDMStructureLayout> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<DDMStructureLayout> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ddm structure layouts where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of ddm structure layouts
	 * @param end the upper bound of the range of ddm structure layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddm structure layouts
	 */
	@Override
	public List<DDMStructureLayout> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<DDMStructureLayout> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDMStructureLayout.class);

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

		List<DDMStructureLayout> list = null;

		if (useFinderCache && productionMode) {
			list = (List<DDMStructureLayout>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DDMStructureLayout ddmStructureLayout : list) {
					if (!uuid.equals(ddmStructureLayout.getUuid())) {
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

			sb.append(_SQL_SELECT_DDMSTRUCTURELAYOUT_WHERE);

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
				sb.append(DDMStructureLayoutModelImpl.ORDER_BY_JPQL);
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

				list = (List<DDMStructureLayout>)QueryUtil.list(
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
	 * Returns the first ddm structure layout in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm structure layout
	 * @throws NoSuchStructureLayoutException if a matching ddm structure layout could not be found
	 */
	@Override
	public DDMStructureLayout findByUuid_First(
			String uuid,
			OrderByComparator<DDMStructureLayout> orderByComparator)
		throws NoSuchStructureLayoutException {

		DDMStructureLayout ddmStructureLayout = fetchByUuid_First(
			uuid, orderByComparator);

		if (ddmStructureLayout != null) {
			return ddmStructureLayout;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchStructureLayoutException(sb.toString());
	}

	/**
	 * Returns the first ddm structure layout in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm structure layout, or <code>null</code> if a matching ddm structure layout could not be found
	 */
	@Override
	public DDMStructureLayout fetchByUuid_First(
		String uuid, OrderByComparator<DDMStructureLayout> orderByComparator) {

		List<DDMStructureLayout> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last ddm structure layout in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm structure layout
	 * @throws NoSuchStructureLayoutException if a matching ddm structure layout could not be found
	 */
	@Override
	public DDMStructureLayout findByUuid_Last(
			String uuid,
			OrderByComparator<DDMStructureLayout> orderByComparator)
		throws NoSuchStructureLayoutException {

		DDMStructureLayout ddmStructureLayout = fetchByUuid_Last(
			uuid, orderByComparator);

		if (ddmStructureLayout != null) {
			return ddmStructureLayout;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchStructureLayoutException(sb.toString());
	}

	/**
	 * Returns the last ddm structure layout in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm structure layout, or <code>null</code> if a matching ddm structure layout could not be found
	 */
	@Override
	public DDMStructureLayout fetchByUuid_Last(
		String uuid, OrderByComparator<DDMStructureLayout> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<DDMStructureLayout> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the ddm structure layouts before and after the current ddm structure layout in the ordered set where uuid = &#63;.
	 *
	 * @param structureLayoutId the primary key of the current ddm structure layout
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm structure layout
	 * @throws NoSuchStructureLayoutException if a ddm structure layout with the primary key could not be found
	 */
	@Override
	public DDMStructureLayout[] findByUuid_PrevAndNext(
			long structureLayoutId, String uuid,
			OrderByComparator<DDMStructureLayout> orderByComparator)
		throws NoSuchStructureLayoutException {

		uuid = Objects.toString(uuid, "");

		DDMStructureLayout ddmStructureLayout = findByPrimaryKey(
			structureLayoutId);

		Session session = null;

		try {
			session = openSession();

			DDMStructureLayout[] array = new DDMStructureLayoutImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, ddmStructureLayout, uuid, orderByComparator, true);

			array[1] = ddmStructureLayout;

			array[2] = getByUuid_PrevAndNext(
				session, ddmStructureLayout, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected DDMStructureLayout getByUuid_PrevAndNext(
		Session session, DDMStructureLayout ddmStructureLayout, String uuid,
		OrderByComparator<DDMStructureLayout> orderByComparator,
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

		sb.append(_SQL_SELECT_DDMSTRUCTURELAYOUT_WHERE);

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
			sb.append(DDMStructureLayoutModelImpl.ORDER_BY_JPQL);
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
						ddmStructureLayout)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<DDMStructureLayout> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the ddm structure layouts where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (DDMStructureLayout ddmStructureLayout :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(ddmStructureLayout);
		}
	}

	/**
	 * Returns the number of ddm structure layouts where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching ddm structure layouts
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDMStructureLayout.class);

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

			sb.append(_SQL_COUNT_DDMSTRUCTURELAYOUT_WHERE);

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
		"ddmStructureLayout.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(ddmStructureLayout.uuid IS NULL OR ddmStructureLayout.uuid = '')";

	private FinderPath _finderPathFetchByUUID_G;
	private FinderPath _finderPathCountByUUID_G;

	/**
	 * Returns the ddm structure layout where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchStructureLayoutException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching ddm structure layout
	 * @throws NoSuchStructureLayoutException if a matching ddm structure layout could not be found
	 */
	@Override
	public DDMStructureLayout findByUUID_G(String uuid, long groupId)
		throws NoSuchStructureLayoutException {

		DDMStructureLayout ddmStructureLayout = fetchByUUID_G(uuid, groupId);

		if (ddmStructureLayout == null) {
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

			throw new NoSuchStructureLayoutException(sb.toString());
		}

		return ddmStructureLayout;
	}

	/**
	 * Returns the ddm structure layout where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching ddm structure layout, or <code>null</code> if a matching ddm structure layout could not be found
	 */
	@Override
	public DDMStructureLayout fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the ddm structure layout where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching ddm structure layout, or <code>null</code> if a matching ddm structure layout could not be found
	 */
	@Override
	public DDMStructureLayout fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDMStructureLayout.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {uuid, groupId};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(
				_finderPathFetchByUUID_G, finderArgs, this);
		}

		if (result instanceof DDMStructureLayout) {
			DDMStructureLayout ddmStructureLayout = (DDMStructureLayout)result;

			if (!Objects.equals(uuid, ddmStructureLayout.getUuid()) ||
				(groupId != ddmStructureLayout.getGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_DDMSTRUCTURELAYOUT_WHERE);

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

				List<DDMStructureLayout> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByUUID_G, finderArgs, list);
					}
				}
				else {
					DDMStructureLayout ddmStructureLayout = list.get(0);

					result = ddmStructureLayout;

					cacheResult(ddmStructureLayout);
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
			return (DDMStructureLayout)result;
		}
	}

	/**
	 * Removes the ddm structure layout where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the ddm structure layout that was removed
	 */
	@Override
	public DDMStructureLayout removeByUUID_G(String uuid, long groupId)
		throws NoSuchStructureLayoutException {

		DDMStructureLayout ddmStructureLayout = findByUUID_G(uuid, groupId);

		return remove(ddmStructureLayout);
	}

	/**
	 * Returns the number of ddm structure layouts where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching ddm structure layouts
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDMStructureLayout.class);

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

			sb.append(_SQL_COUNT_DDMSTRUCTURELAYOUT_WHERE);

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
		"ddmStructureLayout.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(ddmStructureLayout.uuid IS NULL OR ddmStructureLayout.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"ddmStructureLayout.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the ddm structure layouts where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching ddm structure layouts
	 */
	@Override
	public List<DDMStructureLayout> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ddm structure layouts where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ddm structure layouts
	 * @param end the upper bound of the range of ddm structure layouts (not inclusive)
	 * @return the range of matching ddm structure layouts
	 */
	@Override
	public List<DDMStructureLayout> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ddm structure layouts where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ddm structure layouts
	 * @param end the upper bound of the range of ddm structure layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm structure layouts
	 */
	@Override
	public List<DDMStructureLayout> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<DDMStructureLayout> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ddm structure layouts where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ddm structure layouts
	 * @param end the upper bound of the range of ddm structure layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddm structure layouts
	 */
	@Override
	public List<DDMStructureLayout> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<DDMStructureLayout> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDMStructureLayout.class);

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

		List<DDMStructureLayout> list = null;

		if (useFinderCache && productionMode) {
			list = (List<DDMStructureLayout>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DDMStructureLayout ddmStructureLayout : list) {
					if (!uuid.equals(ddmStructureLayout.getUuid()) ||
						(companyId != ddmStructureLayout.getCompanyId())) {

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

			sb.append(_SQL_SELECT_DDMSTRUCTURELAYOUT_WHERE);

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
				sb.append(DDMStructureLayoutModelImpl.ORDER_BY_JPQL);
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

				list = (List<DDMStructureLayout>)QueryUtil.list(
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
	 * Returns the first ddm structure layout in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm structure layout
	 * @throws NoSuchStructureLayoutException if a matching ddm structure layout could not be found
	 */
	@Override
	public DDMStructureLayout findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<DDMStructureLayout> orderByComparator)
		throws NoSuchStructureLayoutException {

		DDMStructureLayout ddmStructureLayout = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (ddmStructureLayout != null) {
			return ddmStructureLayout;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchStructureLayoutException(sb.toString());
	}

	/**
	 * Returns the first ddm structure layout in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm structure layout, or <code>null</code> if a matching ddm structure layout could not be found
	 */
	@Override
	public DDMStructureLayout fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<DDMStructureLayout> orderByComparator) {

		List<DDMStructureLayout> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last ddm structure layout in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm structure layout
	 * @throws NoSuchStructureLayoutException if a matching ddm structure layout could not be found
	 */
	@Override
	public DDMStructureLayout findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<DDMStructureLayout> orderByComparator)
		throws NoSuchStructureLayoutException {

		DDMStructureLayout ddmStructureLayout = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (ddmStructureLayout != null) {
			return ddmStructureLayout;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchStructureLayoutException(sb.toString());
	}

	/**
	 * Returns the last ddm structure layout in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm structure layout, or <code>null</code> if a matching ddm structure layout could not be found
	 */
	@Override
	public DDMStructureLayout fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<DDMStructureLayout> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<DDMStructureLayout> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the ddm structure layouts before and after the current ddm structure layout in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param structureLayoutId the primary key of the current ddm structure layout
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm structure layout
	 * @throws NoSuchStructureLayoutException if a ddm structure layout with the primary key could not be found
	 */
	@Override
	public DDMStructureLayout[] findByUuid_C_PrevAndNext(
			long structureLayoutId, String uuid, long companyId,
			OrderByComparator<DDMStructureLayout> orderByComparator)
		throws NoSuchStructureLayoutException {

		uuid = Objects.toString(uuid, "");

		DDMStructureLayout ddmStructureLayout = findByPrimaryKey(
			structureLayoutId);

		Session session = null;

		try {
			session = openSession();

			DDMStructureLayout[] array = new DDMStructureLayoutImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, ddmStructureLayout, uuid, companyId, orderByComparator,
				true);

			array[1] = ddmStructureLayout;

			array[2] = getByUuid_C_PrevAndNext(
				session, ddmStructureLayout, uuid, companyId, orderByComparator,
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

	protected DDMStructureLayout getByUuid_C_PrevAndNext(
		Session session, DDMStructureLayout ddmStructureLayout, String uuid,
		long companyId, OrderByComparator<DDMStructureLayout> orderByComparator,
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

		sb.append(_SQL_SELECT_DDMSTRUCTURELAYOUT_WHERE);

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
			sb.append(DDMStructureLayoutModelImpl.ORDER_BY_JPQL);
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
						ddmStructureLayout)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<DDMStructureLayout> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the ddm structure layouts where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (DDMStructureLayout ddmStructureLayout :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(ddmStructureLayout);
		}
	}

	/**
	 * Returns the number of ddm structure layouts where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching ddm structure layouts
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDMStructureLayout.class);

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

			sb.append(_SQL_COUNT_DDMSTRUCTURELAYOUT_WHERE);

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
		"ddmStructureLayout.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(ddmStructureLayout.uuid IS NULL OR ddmStructureLayout.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"ddmStructureLayout.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByGroupId;
	private FinderPath _finderPathWithoutPaginationFindByGroupId;
	private FinderPath _finderPathCountByGroupId;

	/**
	 * Returns all the ddm structure layouts where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching ddm structure layouts
	 */
	@Override
	public List<DDMStructureLayout> findByGroupId(long groupId) {
		return findByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ddm structure layouts where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of ddm structure layouts
	 * @param end the upper bound of the range of ddm structure layouts (not inclusive)
	 * @return the range of matching ddm structure layouts
	 */
	@Override
	public List<DDMStructureLayout> findByGroupId(
		long groupId, int start, int end) {

		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ddm structure layouts where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of ddm structure layouts
	 * @param end the upper bound of the range of ddm structure layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm structure layouts
	 */
	@Override
	public List<DDMStructureLayout> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<DDMStructureLayout> orderByComparator) {

		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ddm structure layouts where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of ddm structure layouts
	 * @param end the upper bound of the range of ddm structure layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddm structure layouts
	 */
	@Override
	public List<DDMStructureLayout> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<DDMStructureLayout> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDMStructureLayout.class);

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

		List<DDMStructureLayout> list = null;

		if (useFinderCache && productionMode) {
			list = (List<DDMStructureLayout>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DDMStructureLayout ddmStructureLayout : list) {
					if (groupId != ddmStructureLayout.getGroupId()) {
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

			sb.append(_SQL_SELECT_DDMSTRUCTURELAYOUT_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(DDMStructureLayoutModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				list = (List<DDMStructureLayout>)QueryUtil.list(
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
	 * Returns the first ddm structure layout in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm structure layout
	 * @throws NoSuchStructureLayoutException if a matching ddm structure layout could not be found
	 */
	@Override
	public DDMStructureLayout findByGroupId_First(
			long groupId,
			OrderByComparator<DDMStructureLayout> orderByComparator)
		throws NoSuchStructureLayoutException {

		DDMStructureLayout ddmStructureLayout = fetchByGroupId_First(
			groupId, orderByComparator);

		if (ddmStructureLayout != null) {
			return ddmStructureLayout;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchStructureLayoutException(sb.toString());
	}

	/**
	 * Returns the first ddm structure layout in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm structure layout, or <code>null</code> if a matching ddm structure layout could not be found
	 */
	@Override
	public DDMStructureLayout fetchByGroupId_First(
		long groupId, OrderByComparator<DDMStructureLayout> orderByComparator) {

		List<DDMStructureLayout> list = findByGroupId(
			groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last ddm structure layout in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm structure layout
	 * @throws NoSuchStructureLayoutException if a matching ddm structure layout could not be found
	 */
	@Override
	public DDMStructureLayout findByGroupId_Last(
			long groupId,
			OrderByComparator<DDMStructureLayout> orderByComparator)
		throws NoSuchStructureLayoutException {

		DDMStructureLayout ddmStructureLayout = fetchByGroupId_Last(
			groupId, orderByComparator);

		if (ddmStructureLayout != null) {
			return ddmStructureLayout;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchStructureLayoutException(sb.toString());
	}

	/**
	 * Returns the last ddm structure layout in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm structure layout, or <code>null</code> if a matching ddm structure layout could not be found
	 */
	@Override
	public DDMStructureLayout fetchByGroupId_Last(
		long groupId, OrderByComparator<DDMStructureLayout> orderByComparator) {

		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<DDMStructureLayout> list = findByGroupId(
			groupId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the ddm structure layouts before and after the current ddm structure layout in the ordered set where groupId = &#63;.
	 *
	 * @param structureLayoutId the primary key of the current ddm structure layout
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm structure layout
	 * @throws NoSuchStructureLayoutException if a ddm structure layout with the primary key could not be found
	 */
	@Override
	public DDMStructureLayout[] findByGroupId_PrevAndNext(
			long structureLayoutId, long groupId,
			OrderByComparator<DDMStructureLayout> orderByComparator)
		throws NoSuchStructureLayoutException {

		DDMStructureLayout ddmStructureLayout = findByPrimaryKey(
			structureLayoutId);

		Session session = null;

		try {
			session = openSession();

			DDMStructureLayout[] array = new DDMStructureLayoutImpl[3];

			array[0] = getByGroupId_PrevAndNext(
				session, ddmStructureLayout, groupId, orderByComparator, true);

			array[1] = ddmStructureLayout;

			array[2] = getByGroupId_PrevAndNext(
				session, ddmStructureLayout, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected DDMStructureLayout getByGroupId_PrevAndNext(
		Session session, DDMStructureLayout ddmStructureLayout, long groupId,
		OrderByComparator<DDMStructureLayout> orderByComparator,
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

		sb.append(_SQL_SELECT_DDMSTRUCTURELAYOUT_WHERE);

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
			sb.append(DDMStructureLayoutModelImpl.ORDER_BY_JPQL);
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
						ddmStructureLayout)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<DDMStructureLayout> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the ddm structure layouts where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (DDMStructureLayout ddmStructureLayout :
				findByGroupId(
					groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(ddmStructureLayout);
		}
	}

	/**
	 * Returns the number of ddm structure layouts where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching ddm structure layouts
	 */
	@Override
	public int countByGroupId(long groupId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDMStructureLayout.class);

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

			sb.append(_SQL_COUNT_DDMSTRUCTURELAYOUT_WHERE);

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
		"ddmStructureLayout.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByStructureLayoutKey;
	private FinderPath _finderPathWithoutPaginationFindByStructureLayoutKey;
	private FinderPath _finderPathCountByStructureLayoutKey;

	/**
	 * Returns all the ddm structure layouts where structureLayoutKey = &#63;.
	 *
	 * @param structureLayoutKey the structure layout key
	 * @return the matching ddm structure layouts
	 */
	@Override
	public List<DDMStructureLayout> findByStructureLayoutKey(
		String structureLayoutKey) {

		return findByStructureLayoutKey(
			structureLayoutKey, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ddm structure layouts where structureLayoutKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param structureLayoutKey the structure layout key
	 * @param start the lower bound of the range of ddm structure layouts
	 * @param end the upper bound of the range of ddm structure layouts (not inclusive)
	 * @return the range of matching ddm structure layouts
	 */
	@Override
	public List<DDMStructureLayout> findByStructureLayoutKey(
		String structureLayoutKey, int start, int end) {

		return findByStructureLayoutKey(structureLayoutKey, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ddm structure layouts where structureLayoutKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param structureLayoutKey the structure layout key
	 * @param start the lower bound of the range of ddm structure layouts
	 * @param end the upper bound of the range of ddm structure layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm structure layouts
	 */
	@Override
	public List<DDMStructureLayout> findByStructureLayoutKey(
		String structureLayoutKey, int start, int end,
		OrderByComparator<DDMStructureLayout> orderByComparator) {

		return findByStructureLayoutKey(
			structureLayoutKey, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ddm structure layouts where structureLayoutKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param structureLayoutKey the structure layout key
	 * @param start the lower bound of the range of ddm structure layouts
	 * @param end the upper bound of the range of ddm structure layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddm structure layouts
	 */
	@Override
	public List<DDMStructureLayout> findByStructureLayoutKey(
		String structureLayoutKey, int start, int end,
		OrderByComparator<DDMStructureLayout> orderByComparator,
		boolean useFinderCache) {

		structureLayoutKey = Objects.toString(structureLayoutKey, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDMStructureLayout.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath =
					_finderPathWithoutPaginationFindByStructureLayoutKey;
				finderArgs = new Object[] {structureLayoutKey};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByStructureLayoutKey;
			finderArgs = new Object[] {
				structureLayoutKey, start, end, orderByComparator
			};
		}

		List<DDMStructureLayout> list = null;

		if (useFinderCache && productionMode) {
			list = (List<DDMStructureLayout>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DDMStructureLayout ddmStructureLayout : list) {
					if (!structureLayoutKey.equals(
							ddmStructureLayout.getStructureLayoutKey())) {

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

			sb.append(_SQL_SELECT_DDMSTRUCTURELAYOUT_WHERE);

			boolean bindStructureLayoutKey = false;

			if (structureLayoutKey.isEmpty()) {
				sb.append(
					_FINDER_COLUMN_STRUCTURELAYOUTKEY_STRUCTURELAYOUTKEY_3);
			}
			else {
				bindStructureLayoutKey = true;

				sb.append(
					_FINDER_COLUMN_STRUCTURELAYOUTKEY_STRUCTURELAYOUTKEY_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(DDMStructureLayoutModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindStructureLayoutKey) {
					queryPos.add(structureLayoutKey);
				}

				list = (List<DDMStructureLayout>)QueryUtil.list(
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
	 * Returns the first ddm structure layout in the ordered set where structureLayoutKey = &#63;.
	 *
	 * @param structureLayoutKey the structure layout key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm structure layout
	 * @throws NoSuchStructureLayoutException if a matching ddm structure layout could not be found
	 */
	@Override
	public DDMStructureLayout findByStructureLayoutKey_First(
			String structureLayoutKey,
			OrderByComparator<DDMStructureLayout> orderByComparator)
		throws NoSuchStructureLayoutException {

		DDMStructureLayout ddmStructureLayout = fetchByStructureLayoutKey_First(
			structureLayoutKey, orderByComparator);

		if (ddmStructureLayout != null) {
			return ddmStructureLayout;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("structureLayoutKey=");
		sb.append(structureLayoutKey);

		sb.append("}");

		throw new NoSuchStructureLayoutException(sb.toString());
	}

	/**
	 * Returns the first ddm structure layout in the ordered set where structureLayoutKey = &#63;.
	 *
	 * @param structureLayoutKey the structure layout key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm structure layout, or <code>null</code> if a matching ddm structure layout could not be found
	 */
	@Override
	public DDMStructureLayout fetchByStructureLayoutKey_First(
		String structureLayoutKey,
		OrderByComparator<DDMStructureLayout> orderByComparator) {

		List<DDMStructureLayout> list = findByStructureLayoutKey(
			structureLayoutKey, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last ddm structure layout in the ordered set where structureLayoutKey = &#63;.
	 *
	 * @param structureLayoutKey the structure layout key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm structure layout
	 * @throws NoSuchStructureLayoutException if a matching ddm structure layout could not be found
	 */
	@Override
	public DDMStructureLayout findByStructureLayoutKey_Last(
			String structureLayoutKey,
			OrderByComparator<DDMStructureLayout> orderByComparator)
		throws NoSuchStructureLayoutException {

		DDMStructureLayout ddmStructureLayout = fetchByStructureLayoutKey_Last(
			structureLayoutKey, orderByComparator);

		if (ddmStructureLayout != null) {
			return ddmStructureLayout;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("structureLayoutKey=");
		sb.append(structureLayoutKey);

		sb.append("}");

		throw new NoSuchStructureLayoutException(sb.toString());
	}

	/**
	 * Returns the last ddm structure layout in the ordered set where structureLayoutKey = &#63;.
	 *
	 * @param structureLayoutKey the structure layout key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm structure layout, or <code>null</code> if a matching ddm structure layout could not be found
	 */
	@Override
	public DDMStructureLayout fetchByStructureLayoutKey_Last(
		String structureLayoutKey,
		OrderByComparator<DDMStructureLayout> orderByComparator) {

		int count = countByStructureLayoutKey(structureLayoutKey);

		if (count == 0) {
			return null;
		}

		List<DDMStructureLayout> list = findByStructureLayoutKey(
			structureLayoutKey, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the ddm structure layouts before and after the current ddm structure layout in the ordered set where structureLayoutKey = &#63;.
	 *
	 * @param structureLayoutId the primary key of the current ddm structure layout
	 * @param structureLayoutKey the structure layout key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm structure layout
	 * @throws NoSuchStructureLayoutException if a ddm structure layout with the primary key could not be found
	 */
	@Override
	public DDMStructureLayout[] findByStructureLayoutKey_PrevAndNext(
			long structureLayoutId, String structureLayoutKey,
			OrderByComparator<DDMStructureLayout> orderByComparator)
		throws NoSuchStructureLayoutException {

		structureLayoutKey = Objects.toString(structureLayoutKey, "");

		DDMStructureLayout ddmStructureLayout = findByPrimaryKey(
			structureLayoutId);

		Session session = null;

		try {
			session = openSession();

			DDMStructureLayout[] array = new DDMStructureLayoutImpl[3];

			array[0] = getByStructureLayoutKey_PrevAndNext(
				session, ddmStructureLayout, structureLayoutKey,
				orderByComparator, true);

			array[1] = ddmStructureLayout;

			array[2] = getByStructureLayoutKey_PrevAndNext(
				session, ddmStructureLayout, structureLayoutKey,
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

	protected DDMStructureLayout getByStructureLayoutKey_PrevAndNext(
		Session session, DDMStructureLayout ddmStructureLayout,
		String structureLayoutKey,
		OrderByComparator<DDMStructureLayout> orderByComparator,
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

		sb.append(_SQL_SELECT_DDMSTRUCTURELAYOUT_WHERE);

		boolean bindStructureLayoutKey = false;

		if (structureLayoutKey.isEmpty()) {
			sb.append(_FINDER_COLUMN_STRUCTURELAYOUTKEY_STRUCTURELAYOUTKEY_3);
		}
		else {
			bindStructureLayoutKey = true;

			sb.append(_FINDER_COLUMN_STRUCTURELAYOUTKEY_STRUCTURELAYOUTKEY_2);
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
			sb.append(DDMStructureLayoutModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindStructureLayoutKey) {
			queryPos.add(structureLayoutKey);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						ddmStructureLayout)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<DDMStructureLayout> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the ddm structure layouts where structureLayoutKey = &#63; from the database.
	 *
	 * @param structureLayoutKey the structure layout key
	 */
	@Override
	public void removeByStructureLayoutKey(String structureLayoutKey) {
		for (DDMStructureLayout ddmStructureLayout :
				findByStructureLayoutKey(
					structureLayoutKey, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(ddmStructureLayout);
		}
	}

	/**
	 * Returns the number of ddm structure layouts where structureLayoutKey = &#63;.
	 *
	 * @param structureLayoutKey the structure layout key
	 * @return the number of matching ddm structure layouts
	 */
	@Override
	public int countByStructureLayoutKey(String structureLayoutKey) {
		structureLayoutKey = Objects.toString(structureLayoutKey, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDMStructureLayout.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByStructureLayoutKey;

			finderArgs = new Object[] {structureLayoutKey};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_DDMSTRUCTURELAYOUT_WHERE);

			boolean bindStructureLayoutKey = false;

			if (structureLayoutKey.isEmpty()) {
				sb.append(
					_FINDER_COLUMN_STRUCTURELAYOUTKEY_STRUCTURELAYOUTKEY_3);
			}
			else {
				bindStructureLayoutKey = true;

				sb.append(
					_FINDER_COLUMN_STRUCTURELAYOUTKEY_STRUCTURELAYOUTKEY_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindStructureLayoutKey) {
					queryPos.add(structureLayoutKey);
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
		_FINDER_COLUMN_STRUCTURELAYOUTKEY_STRUCTURELAYOUTKEY_2 =
			"ddmStructureLayout.structureLayoutKey = ?";

	private static final String
		_FINDER_COLUMN_STRUCTURELAYOUTKEY_STRUCTURELAYOUTKEY_3 =
			"(ddmStructureLayout.structureLayoutKey IS NULL OR ddmStructureLayout.structureLayoutKey = '')";

	private FinderPath _finderPathFetchByStructureVersionId;
	private FinderPath _finderPathCountByStructureVersionId;

	/**
	 * Returns the ddm structure layout where structureVersionId = &#63; or throws a <code>NoSuchStructureLayoutException</code> if it could not be found.
	 *
	 * @param structureVersionId the structure version ID
	 * @return the matching ddm structure layout
	 * @throws NoSuchStructureLayoutException if a matching ddm structure layout could not be found
	 */
	@Override
	public DDMStructureLayout findByStructureVersionId(long structureVersionId)
		throws NoSuchStructureLayoutException {

		DDMStructureLayout ddmStructureLayout = fetchByStructureVersionId(
			structureVersionId);

		if (ddmStructureLayout == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("structureVersionId=");
			sb.append(structureVersionId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchStructureLayoutException(sb.toString());
		}

		return ddmStructureLayout;
	}

	/**
	 * Returns the ddm structure layout where structureVersionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param structureVersionId the structure version ID
	 * @return the matching ddm structure layout, or <code>null</code> if a matching ddm structure layout could not be found
	 */
	@Override
	public DDMStructureLayout fetchByStructureVersionId(
		long structureVersionId) {

		return fetchByStructureVersionId(structureVersionId, true);
	}

	/**
	 * Returns the ddm structure layout where structureVersionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param structureVersionId the structure version ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching ddm structure layout, or <code>null</code> if a matching ddm structure layout could not be found
	 */
	@Override
	public DDMStructureLayout fetchByStructureVersionId(
		long structureVersionId, boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDMStructureLayout.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {structureVersionId};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(
				_finderPathFetchByStructureVersionId, finderArgs, this);
		}

		if (result instanceof DDMStructureLayout) {
			DDMStructureLayout ddmStructureLayout = (DDMStructureLayout)result;

			if (structureVersionId !=
					ddmStructureLayout.getStructureVersionId()) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_SELECT_DDMSTRUCTURELAYOUT_WHERE);

			sb.append(_FINDER_COLUMN_STRUCTUREVERSIONID_STRUCTUREVERSIONID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(structureVersionId);

				List<DDMStructureLayout> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByStructureVersionId, finderArgs,
							list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!productionMode || !useFinderCache) {
								finderArgs = new Object[] {structureVersionId};
							}

							_log.warn(
								"DDMStructureLayoutPersistenceImpl.fetchByStructureVersionId(long, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					DDMStructureLayout ddmStructureLayout = list.get(0);

					result = ddmStructureLayout;

					cacheResult(ddmStructureLayout);
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
			return (DDMStructureLayout)result;
		}
	}

	/**
	 * Removes the ddm structure layout where structureVersionId = &#63; from the database.
	 *
	 * @param structureVersionId the structure version ID
	 * @return the ddm structure layout that was removed
	 */
	@Override
	public DDMStructureLayout removeByStructureVersionId(
			long structureVersionId)
		throws NoSuchStructureLayoutException {

		DDMStructureLayout ddmStructureLayout = findByStructureVersionId(
			structureVersionId);

		return remove(ddmStructureLayout);
	}

	/**
	 * Returns the number of ddm structure layouts where structureVersionId = &#63;.
	 *
	 * @param structureVersionId the structure version ID
	 * @return the number of matching ddm structure layouts
	 */
	@Override
	public int countByStructureVersionId(long structureVersionId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDMStructureLayout.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByStructureVersionId;

			finderArgs = new Object[] {structureVersionId};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_DDMSTRUCTURELAYOUT_WHERE);

			sb.append(_FINDER_COLUMN_STRUCTUREVERSIONID_STRUCTUREVERSIONID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(structureVersionId);

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
		_FINDER_COLUMN_STRUCTUREVERSIONID_STRUCTUREVERSIONID_2 =
			"ddmStructureLayout.structureVersionId = ?";

	private FinderPath _finderPathWithPaginationFindByG_C;
	private FinderPath _finderPathWithoutPaginationFindByG_C;
	private FinderPath _finderPathCountByG_C;

	/**
	 * Returns all the ddm structure layouts where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @return the matching ddm structure layouts
	 */
	@Override
	public List<DDMStructureLayout> findByG_C(long groupId, long classNameId) {
		return findByG_C(
			groupId, classNameId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ddm structure layouts where groupId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of ddm structure layouts
	 * @param end the upper bound of the range of ddm structure layouts (not inclusive)
	 * @return the range of matching ddm structure layouts
	 */
	@Override
	public List<DDMStructureLayout> findByG_C(
		long groupId, long classNameId, int start, int end) {

		return findByG_C(groupId, classNameId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ddm structure layouts where groupId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of ddm structure layouts
	 * @param end the upper bound of the range of ddm structure layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm structure layouts
	 */
	@Override
	public List<DDMStructureLayout> findByG_C(
		long groupId, long classNameId, int start, int end,
		OrderByComparator<DDMStructureLayout> orderByComparator) {

		return findByG_C(
			groupId, classNameId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ddm structure layouts where groupId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of ddm structure layouts
	 * @param end the upper bound of the range of ddm structure layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddm structure layouts
	 */
	@Override
	public List<DDMStructureLayout> findByG_C(
		long groupId, long classNameId, int start, int end,
		OrderByComparator<DDMStructureLayout> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDMStructureLayout.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByG_C;
				finderArgs = new Object[] {groupId, classNameId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByG_C;
			finderArgs = new Object[] {
				groupId, classNameId, start, end, orderByComparator
			};
		}

		List<DDMStructureLayout> list = null;

		if (useFinderCache && productionMode) {
			list = (List<DDMStructureLayout>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DDMStructureLayout ddmStructureLayout : list) {
					if ((groupId != ddmStructureLayout.getGroupId()) ||
						(classNameId != ddmStructureLayout.getClassNameId())) {

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

			sb.append(_SQL_SELECT_DDMSTRUCTURELAYOUT_WHERE);

			sb.append(_FINDER_COLUMN_G_C_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_CLASSNAMEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(DDMStructureLayoutModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(classNameId);

				list = (List<DDMStructureLayout>)QueryUtil.list(
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
	 * Returns the first ddm structure layout in the ordered set where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm structure layout
	 * @throws NoSuchStructureLayoutException if a matching ddm structure layout could not be found
	 */
	@Override
	public DDMStructureLayout findByG_C_First(
			long groupId, long classNameId,
			OrderByComparator<DDMStructureLayout> orderByComparator)
		throws NoSuchStructureLayoutException {

		DDMStructureLayout ddmStructureLayout = fetchByG_C_First(
			groupId, classNameId, orderByComparator);

		if (ddmStructureLayout != null) {
			return ddmStructureLayout;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append("}");

		throw new NoSuchStructureLayoutException(sb.toString());
	}

	/**
	 * Returns the first ddm structure layout in the ordered set where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm structure layout, or <code>null</code> if a matching ddm structure layout could not be found
	 */
	@Override
	public DDMStructureLayout fetchByG_C_First(
		long groupId, long classNameId,
		OrderByComparator<DDMStructureLayout> orderByComparator) {

		List<DDMStructureLayout> list = findByG_C(
			groupId, classNameId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last ddm structure layout in the ordered set where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm structure layout
	 * @throws NoSuchStructureLayoutException if a matching ddm structure layout could not be found
	 */
	@Override
	public DDMStructureLayout findByG_C_Last(
			long groupId, long classNameId,
			OrderByComparator<DDMStructureLayout> orderByComparator)
		throws NoSuchStructureLayoutException {

		DDMStructureLayout ddmStructureLayout = fetchByG_C_Last(
			groupId, classNameId, orderByComparator);

		if (ddmStructureLayout != null) {
			return ddmStructureLayout;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append("}");

		throw new NoSuchStructureLayoutException(sb.toString());
	}

	/**
	 * Returns the last ddm structure layout in the ordered set where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm structure layout, or <code>null</code> if a matching ddm structure layout could not be found
	 */
	@Override
	public DDMStructureLayout fetchByG_C_Last(
		long groupId, long classNameId,
		OrderByComparator<DDMStructureLayout> orderByComparator) {

		int count = countByG_C(groupId, classNameId);

		if (count == 0) {
			return null;
		}

		List<DDMStructureLayout> list = findByG_C(
			groupId, classNameId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the ddm structure layouts before and after the current ddm structure layout in the ordered set where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param structureLayoutId the primary key of the current ddm structure layout
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm structure layout
	 * @throws NoSuchStructureLayoutException if a ddm structure layout with the primary key could not be found
	 */
	@Override
	public DDMStructureLayout[] findByG_C_PrevAndNext(
			long structureLayoutId, long groupId, long classNameId,
			OrderByComparator<DDMStructureLayout> orderByComparator)
		throws NoSuchStructureLayoutException {

		DDMStructureLayout ddmStructureLayout = findByPrimaryKey(
			structureLayoutId);

		Session session = null;

		try {
			session = openSession();

			DDMStructureLayout[] array = new DDMStructureLayoutImpl[3];

			array[0] = getByG_C_PrevAndNext(
				session, ddmStructureLayout, groupId, classNameId,
				orderByComparator, true);

			array[1] = ddmStructureLayout;

			array[2] = getByG_C_PrevAndNext(
				session, ddmStructureLayout, groupId, classNameId,
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

	protected DDMStructureLayout getByG_C_PrevAndNext(
		Session session, DDMStructureLayout ddmStructureLayout, long groupId,
		long classNameId,
		OrderByComparator<DDMStructureLayout> orderByComparator,
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

		sb.append(_SQL_SELECT_DDMSTRUCTURELAYOUT_WHERE);

		sb.append(_FINDER_COLUMN_G_C_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_CLASSNAMEID_2);

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
			sb.append(DDMStructureLayoutModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(classNameId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						ddmStructureLayout)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<DDMStructureLayout> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the ddm structure layouts where groupId = &#63; and classNameId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 */
	@Override
	public void removeByG_C(long groupId, long classNameId) {
		for (DDMStructureLayout ddmStructureLayout :
				findByG_C(
					groupId, classNameId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(ddmStructureLayout);
		}
	}

	/**
	 * Returns the number of ddm structure layouts where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @return the number of matching ddm structure layouts
	 */
	@Override
	public int countByG_C(long groupId, long classNameId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDMStructureLayout.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_C;

			finderArgs = new Object[] {groupId, classNameId};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_DDMSTRUCTURELAYOUT_WHERE);

			sb.append(_FINDER_COLUMN_G_C_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_CLASSNAMEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(classNameId);

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

	private static final String _FINDER_COLUMN_G_C_GROUPID_2 =
		"ddmStructureLayout.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_CLASSNAMEID_2 =
		"ddmStructureLayout.classNameId = ?";

	private FinderPath _finderPathFetchByG_C_S;
	private FinderPath _finderPathCountByG_C_S;

	/**
	 * Returns the ddm structure layout where groupId = &#63; and classNameId = &#63; and structureLayoutKey = &#63; or throws a <code>NoSuchStructureLayoutException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param structureLayoutKey the structure layout key
	 * @return the matching ddm structure layout
	 * @throws NoSuchStructureLayoutException if a matching ddm structure layout could not be found
	 */
	@Override
	public DDMStructureLayout findByG_C_S(
			long groupId, long classNameId, String structureLayoutKey)
		throws NoSuchStructureLayoutException {

		DDMStructureLayout ddmStructureLayout = fetchByG_C_S(
			groupId, classNameId, structureLayoutKey);

		if (ddmStructureLayout == null) {
			StringBundler sb = new StringBundler(8);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("groupId=");
			sb.append(groupId);

			sb.append(", classNameId=");
			sb.append(classNameId);

			sb.append(", structureLayoutKey=");
			sb.append(structureLayoutKey);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchStructureLayoutException(sb.toString());
		}

		return ddmStructureLayout;
	}

	/**
	 * Returns the ddm structure layout where groupId = &#63; and classNameId = &#63; and structureLayoutKey = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param structureLayoutKey the structure layout key
	 * @return the matching ddm structure layout, or <code>null</code> if a matching ddm structure layout could not be found
	 */
	@Override
	public DDMStructureLayout fetchByG_C_S(
		long groupId, long classNameId, String structureLayoutKey) {

		return fetchByG_C_S(groupId, classNameId, structureLayoutKey, true);
	}

	/**
	 * Returns the ddm structure layout where groupId = &#63; and classNameId = &#63; and structureLayoutKey = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param structureLayoutKey the structure layout key
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching ddm structure layout, or <code>null</code> if a matching ddm structure layout could not be found
	 */
	@Override
	public DDMStructureLayout fetchByG_C_S(
		long groupId, long classNameId, String structureLayoutKey,
		boolean useFinderCache) {

		structureLayoutKey = Objects.toString(structureLayoutKey, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDMStructureLayout.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {
				groupId, classNameId, structureLayoutKey
			};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(
				_finderPathFetchByG_C_S, finderArgs, this);
		}

		if (result instanceof DDMStructureLayout) {
			DDMStructureLayout ddmStructureLayout = (DDMStructureLayout)result;

			if ((groupId != ddmStructureLayout.getGroupId()) ||
				(classNameId != ddmStructureLayout.getClassNameId()) ||
				!Objects.equals(
					structureLayoutKey,
					ddmStructureLayout.getStructureLayoutKey())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_SELECT_DDMSTRUCTURELAYOUT_WHERE);

			sb.append(_FINDER_COLUMN_G_C_S_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_S_CLASSNAMEID_2);

			boolean bindStructureLayoutKey = false;

			if (structureLayoutKey.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_C_S_STRUCTURELAYOUTKEY_3);
			}
			else {
				bindStructureLayoutKey = true;

				sb.append(_FINDER_COLUMN_G_C_S_STRUCTURELAYOUTKEY_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(classNameId);

				if (bindStructureLayoutKey) {
					queryPos.add(structureLayoutKey);
				}

				List<DDMStructureLayout> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByG_C_S, finderArgs, list);
					}
				}
				else {
					DDMStructureLayout ddmStructureLayout = list.get(0);

					result = ddmStructureLayout;

					cacheResult(ddmStructureLayout);
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
			return (DDMStructureLayout)result;
		}
	}

	/**
	 * Removes the ddm structure layout where groupId = &#63; and classNameId = &#63; and structureLayoutKey = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param structureLayoutKey the structure layout key
	 * @return the ddm structure layout that was removed
	 */
	@Override
	public DDMStructureLayout removeByG_C_S(
			long groupId, long classNameId, String structureLayoutKey)
		throws NoSuchStructureLayoutException {

		DDMStructureLayout ddmStructureLayout = findByG_C_S(
			groupId, classNameId, structureLayoutKey);

		return remove(ddmStructureLayout);
	}

	/**
	 * Returns the number of ddm structure layouts where groupId = &#63; and classNameId = &#63; and structureLayoutKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param structureLayoutKey the structure layout key
	 * @return the number of matching ddm structure layouts
	 */
	@Override
	public int countByG_C_S(
		long groupId, long classNameId, String structureLayoutKey) {

		structureLayoutKey = Objects.toString(structureLayoutKey, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDMStructureLayout.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_C_S;

			finderArgs = new Object[] {
				groupId, classNameId, structureLayoutKey
			};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_DDMSTRUCTURELAYOUT_WHERE);

			sb.append(_FINDER_COLUMN_G_C_S_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_S_CLASSNAMEID_2);

			boolean bindStructureLayoutKey = false;

			if (structureLayoutKey.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_C_S_STRUCTURELAYOUTKEY_3);
			}
			else {
				bindStructureLayoutKey = true;

				sb.append(_FINDER_COLUMN_G_C_S_STRUCTURELAYOUTKEY_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(classNameId);

				if (bindStructureLayoutKey) {
					queryPos.add(structureLayoutKey);
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

	private static final String _FINDER_COLUMN_G_C_S_GROUPID_2 =
		"ddmStructureLayout.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_S_CLASSNAMEID_2 =
		"ddmStructureLayout.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_S_STRUCTURELAYOUTKEY_2 =
		"ddmStructureLayout.structureLayoutKey = ?";

	private static final String _FINDER_COLUMN_G_C_S_STRUCTURELAYOUTKEY_3 =
		"(ddmStructureLayout.structureLayoutKey IS NULL OR ddmStructureLayout.structureLayoutKey = '')";

	private FinderPath _finderPathWithPaginationFindByG_C_SV;
	private FinderPath _finderPathWithoutPaginationFindByG_C_SV;
	private FinderPath _finderPathCountByG_C_SV;

	/**
	 * Returns all the ddm structure layouts where groupId = &#63; and classNameId = &#63; and structureVersionId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param structureVersionId the structure version ID
	 * @return the matching ddm structure layouts
	 */
	@Override
	public List<DDMStructureLayout> findByG_C_SV(
		long groupId, long classNameId, long structureVersionId) {

		return findByG_C_SV(
			groupId, classNameId, structureVersionId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ddm structure layouts where groupId = &#63; and classNameId = &#63; and structureVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param structureVersionId the structure version ID
	 * @param start the lower bound of the range of ddm structure layouts
	 * @param end the upper bound of the range of ddm structure layouts (not inclusive)
	 * @return the range of matching ddm structure layouts
	 */
	@Override
	public List<DDMStructureLayout> findByG_C_SV(
		long groupId, long classNameId, long structureVersionId, int start,
		int end) {

		return findByG_C_SV(
			groupId, classNameId, structureVersionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ddm structure layouts where groupId = &#63; and classNameId = &#63; and structureVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param structureVersionId the structure version ID
	 * @param start the lower bound of the range of ddm structure layouts
	 * @param end the upper bound of the range of ddm structure layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm structure layouts
	 */
	@Override
	public List<DDMStructureLayout> findByG_C_SV(
		long groupId, long classNameId, long structureVersionId, int start,
		int end, OrderByComparator<DDMStructureLayout> orderByComparator) {

		return findByG_C_SV(
			groupId, classNameId, structureVersionId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ddm structure layouts where groupId = &#63; and classNameId = &#63; and structureVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param structureVersionId the structure version ID
	 * @param start the lower bound of the range of ddm structure layouts
	 * @param end the upper bound of the range of ddm structure layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ddm structure layouts
	 */
	@Override
	public List<DDMStructureLayout> findByG_C_SV(
		long groupId, long classNameId, long structureVersionId, int start,
		int end, OrderByComparator<DDMStructureLayout> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDMStructureLayout.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByG_C_SV;
				finderArgs = new Object[] {
					groupId, classNameId, structureVersionId
				};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByG_C_SV;
			finderArgs = new Object[] {
				groupId, classNameId, structureVersionId, start, end,
				orderByComparator
			};
		}

		List<DDMStructureLayout> list = null;

		if (useFinderCache && productionMode) {
			list = (List<DDMStructureLayout>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DDMStructureLayout ddmStructureLayout : list) {
					if ((groupId != ddmStructureLayout.getGroupId()) ||
						(classNameId != ddmStructureLayout.getClassNameId()) ||
						(structureVersionId !=
							ddmStructureLayout.getStructureVersionId())) {

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

			sb.append(_SQL_SELECT_DDMSTRUCTURELAYOUT_WHERE);

			sb.append(_FINDER_COLUMN_G_C_SV_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_SV_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_G_C_SV_STRUCTUREVERSIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(DDMStructureLayoutModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(classNameId);

				queryPos.add(structureVersionId);

				list = (List<DDMStructureLayout>)QueryUtil.list(
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
	 * Returns the first ddm structure layout in the ordered set where groupId = &#63; and classNameId = &#63; and structureVersionId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param structureVersionId the structure version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm structure layout
	 * @throws NoSuchStructureLayoutException if a matching ddm structure layout could not be found
	 */
	@Override
	public DDMStructureLayout findByG_C_SV_First(
			long groupId, long classNameId, long structureVersionId,
			OrderByComparator<DDMStructureLayout> orderByComparator)
		throws NoSuchStructureLayoutException {

		DDMStructureLayout ddmStructureLayout = fetchByG_C_SV_First(
			groupId, classNameId, structureVersionId, orderByComparator);

		if (ddmStructureLayout != null) {
			return ddmStructureLayout;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append(", structureVersionId=");
		sb.append(structureVersionId);

		sb.append("}");

		throw new NoSuchStructureLayoutException(sb.toString());
	}

	/**
	 * Returns the first ddm structure layout in the ordered set where groupId = &#63; and classNameId = &#63; and structureVersionId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param structureVersionId the structure version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm structure layout, or <code>null</code> if a matching ddm structure layout could not be found
	 */
	@Override
	public DDMStructureLayout fetchByG_C_SV_First(
		long groupId, long classNameId, long structureVersionId,
		OrderByComparator<DDMStructureLayout> orderByComparator) {

		List<DDMStructureLayout> list = findByG_C_SV(
			groupId, classNameId, structureVersionId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last ddm structure layout in the ordered set where groupId = &#63; and classNameId = &#63; and structureVersionId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param structureVersionId the structure version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm structure layout
	 * @throws NoSuchStructureLayoutException if a matching ddm structure layout could not be found
	 */
	@Override
	public DDMStructureLayout findByG_C_SV_Last(
			long groupId, long classNameId, long structureVersionId,
			OrderByComparator<DDMStructureLayout> orderByComparator)
		throws NoSuchStructureLayoutException {

		DDMStructureLayout ddmStructureLayout = fetchByG_C_SV_Last(
			groupId, classNameId, structureVersionId, orderByComparator);

		if (ddmStructureLayout != null) {
			return ddmStructureLayout;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append(", structureVersionId=");
		sb.append(structureVersionId);

		sb.append("}");

		throw new NoSuchStructureLayoutException(sb.toString());
	}

	/**
	 * Returns the last ddm structure layout in the ordered set where groupId = &#63; and classNameId = &#63; and structureVersionId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param structureVersionId the structure version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm structure layout, or <code>null</code> if a matching ddm structure layout could not be found
	 */
	@Override
	public DDMStructureLayout fetchByG_C_SV_Last(
		long groupId, long classNameId, long structureVersionId,
		OrderByComparator<DDMStructureLayout> orderByComparator) {

		int count = countByG_C_SV(groupId, classNameId, structureVersionId);

		if (count == 0) {
			return null;
		}

		List<DDMStructureLayout> list = findByG_C_SV(
			groupId, classNameId, structureVersionId, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the ddm structure layouts before and after the current ddm structure layout in the ordered set where groupId = &#63; and classNameId = &#63; and structureVersionId = &#63;.
	 *
	 * @param structureLayoutId the primary key of the current ddm structure layout
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param structureVersionId the structure version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm structure layout
	 * @throws NoSuchStructureLayoutException if a ddm structure layout with the primary key could not be found
	 */
	@Override
	public DDMStructureLayout[] findByG_C_SV_PrevAndNext(
			long structureLayoutId, long groupId, long classNameId,
			long structureVersionId,
			OrderByComparator<DDMStructureLayout> orderByComparator)
		throws NoSuchStructureLayoutException {

		DDMStructureLayout ddmStructureLayout = findByPrimaryKey(
			structureLayoutId);

		Session session = null;

		try {
			session = openSession();

			DDMStructureLayout[] array = new DDMStructureLayoutImpl[3];

			array[0] = getByG_C_SV_PrevAndNext(
				session, ddmStructureLayout, groupId, classNameId,
				structureVersionId, orderByComparator, true);

			array[1] = ddmStructureLayout;

			array[2] = getByG_C_SV_PrevAndNext(
				session, ddmStructureLayout, groupId, classNameId,
				structureVersionId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected DDMStructureLayout getByG_C_SV_PrevAndNext(
		Session session, DDMStructureLayout ddmStructureLayout, long groupId,
		long classNameId, long structureVersionId,
		OrderByComparator<DDMStructureLayout> orderByComparator,
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

		sb.append(_SQL_SELECT_DDMSTRUCTURELAYOUT_WHERE);

		sb.append(_FINDER_COLUMN_G_C_SV_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_SV_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_G_C_SV_STRUCTUREVERSIONID_2);

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
			sb.append(DDMStructureLayoutModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(classNameId);

		queryPos.add(structureVersionId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						ddmStructureLayout)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<DDMStructureLayout> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the ddm structure layouts where groupId = &#63; and classNameId = &#63; and structureVersionId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param structureVersionId the structure version ID
	 */
	@Override
	public void removeByG_C_SV(
		long groupId, long classNameId, long structureVersionId) {

		for (DDMStructureLayout ddmStructureLayout :
				findByG_C_SV(
					groupId, classNameId, structureVersionId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(ddmStructureLayout);
		}
	}

	/**
	 * Returns the number of ddm structure layouts where groupId = &#63; and classNameId = &#63; and structureVersionId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param structureVersionId the structure version ID
	 * @return the number of matching ddm structure layouts
	 */
	@Override
	public int countByG_C_SV(
		long groupId, long classNameId, long structureVersionId) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDMStructureLayout.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_C_SV;

			finderArgs = new Object[] {
				groupId, classNameId, structureVersionId
			};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_DDMSTRUCTURELAYOUT_WHERE);

			sb.append(_FINDER_COLUMN_G_C_SV_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_SV_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_G_C_SV_STRUCTUREVERSIONID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(classNameId);

				queryPos.add(structureVersionId);

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

	private static final String _FINDER_COLUMN_G_C_SV_GROUPID_2 =
		"ddmStructureLayout.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_SV_CLASSNAMEID_2 =
		"ddmStructureLayout.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_SV_STRUCTUREVERSIONID_2 =
		"ddmStructureLayout.structureVersionId = ?";

	public DDMStructureLayoutPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(DDMStructureLayout.class);

		setModelImplClass(DDMStructureLayoutImpl.class);
		setModelPKClass(long.class);

		setTable(DDMStructureLayoutTable.INSTANCE);
	}

	/**
	 * Caches the ddm structure layout in the entity cache if it is enabled.
	 *
	 * @param ddmStructureLayout the ddm structure layout
	 */
	@Override
	public void cacheResult(DDMStructureLayout ddmStructureLayout) {
		if (ddmStructureLayout.getCtCollectionId() != 0) {
			ddmStructureLayout.resetOriginalValues();

			return;
		}

		entityCache.putResult(
			DDMStructureLayoutImpl.class, ddmStructureLayout.getPrimaryKey(),
			ddmStructureLayout);

		finderCache.putResult(
			_finderPathFetchByUUID_G,
			new Object[] {
				ddmStructureLayout.getUuid(), ddmStructureLayout.getGroupId()
			},
			ddmStructureLayout);

		finderCache.putResult(
			_finderPathFetchByStructureVersionId,
			new Object[] {ddmStructureLayout.getStructureVersionId()},
			ddmStructureLayout);

		finderCache.putResult(
			_finderPathFetchByG_C_S,
			new Object[] {
				ddmStructureLayout.getGroupId(),
				ddmStructureLayout.getClassNameId(),
				ddmStructureLayout.getStructureLayoutKey()
			},
			ddmStructureLayout);

		ddmStructureLayout.resetOriginalValues();
	}

	/**
	 * Caches the ddm structure layouts in the entity cache if it is enabled.
	 *
	 * @param ddmStructureLayouts the ddm structure layouts
	 */
	@Override
	public void cacheResult(List<DDMStructureLayout> ddmStructureLayouts) {
		for (DDMStructureLayout ddmStructureLayout : ddmStructureLayouts) {
			if (ddmStructureLayout.getCtCollectionId() != 0) {
				ddmStructureLayout.resetOriginalValues();

				continue;
			}

			if (entityCache.getResult(
					DDMStructureLayoutImpl.class,
					ddmStructureLayout.getPrimaryKey()) == null) {

				cacheResult(ddmStructureLayout);
			}
			else {
				ddmStructureLayout.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all ddm structure layouts.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(DDMStructureLayoutImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the ddm structure layout.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(DDMStructureLayout ddmStructureLayout) {
		entityCache.removeResult(
			DDMStructureLayoutImpl.class, ddmStructureLayout.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(DDMStructureLayoutModelImpl)ddmStructureLayout, true);
	}

	@Override
	public void clearCache(List<DDMStructureLayout> ddmStructureLayouts) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (DDMStructureLayout ddmStructureLayout : ddmStructureLayouts) {
			entityCache.removeResult(
				DDMStructureLayoutImpl.class,
				ddmStructureLayout.getPrimaryKey());

			clearUniqueFindersCache(
				(DDMStructureLayoutModelImpl)ddmStructureLayout, true);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(DDMStructureLayoutImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		DDMStructureLayoutModelImpl ddmStructureLayoutModelImpl) {

		Object[] args = new Object[] {
			ddmStructureLayoutModelImpl.getUuid(),
			ddmStructureLayoutModelImpl.getGroupId()
		};

		finderCache.putResult(
			_finderPathCountByUUID_G, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByUUID_G, args, ddmStructureLayoutModelImpl, false);

		args = new Object[] {
			ddmStructureLayoutModelImpl.getStructureVersionId()
		};

		finderCache.putResult(
			_finderPathCountByStructureVersionId, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByStructureVersionId, args,
			ddmStructureLayoutModelImpl, false);

		args = new Object[] {
			ddmStructureLayoutModelImpl.getGroupId(),
			ddmStructureLayoutModelImpl.getClassNameId(),
			ddmStructureLayoutModelImpl.getStructureLayoutKey()
		};

		finderCache.putResult(
			_finderPathCountByG_C_S, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByG_C_S, args, ddmStructureLayoutModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		DDMStructureLayoutModelImpl ddmStructureLayoutModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				ddmStructureLayoutModelImpl.getUuid(),
				ddmStructureLayoutModelImpl.getGroupId()
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(_finderPathFetchByUUID_G, args);
		}

		if ((ddmStructureLayoutModelImpl.getColumnBitmask() &
			 _finderPathFetchByUUID_G.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				ddmStructureLayoutModelImpl.getOriginalUuid(),
				ddmStructureLayoutModelImpl.getOriginalGroupId()
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(_finderPathFetchByUUID_G, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
				ddmStructureLayoutModelImpl.getStructureVersionId()
			};

			finderCache.removeResult(
				_finderPathCountByStructureVersionId, args);
			finderCache.removeResult(
				_finderPathFetchByStructureVersionId, args);
		}

		if ((ddmStructureLayoutModelImpl.getColumnBitmask() &
			 _finderPathFetchByStructureVersionId.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				ddmStructureLayoutModelImpl.getOriginalStructureVersionId()
			};

			finderCache.removeResult(
				_finderPathCountByStructureVersionId, args);
			finderCache.removeResult(
				_finderPathFetchByStructureVersionId, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
				ddmStructureLayoutModelImpl.getGroupId(),
				ddmStructureLayoutModelImpl.getClassNameId(),
				ddmStructureLayoutModelImpl.getStructureLayoutKey()
			};

			finderCache.removeResult(_finderPathCountByG_C_S, args);
			finderCache.removeResult(_finderPathFetchByG_C_S, args);
		}

		if ((ddmStructureLayoutModelImpl.getColumnBitmask() &
			 _finderPathFetchByG_C_S.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				ddmStructureLayoutModelImpl.getOriginalGroupId(),
				ddmStructureLayoutModelImpl.getOriginalClassNameId(),
				ddmStructureLayoutModelImpl.getOriginalStructureLayoutKey()
			};

			finderCache.removeResult(_finderPathCountByG_C_S, args);
			finderCache.removeResult(_finderPathFetchByG_C_S, args);
		}
	}

	/**
	 * Creates a new ddm structure layout with the primary key. Does not add the ddm structure layout to the database.
	 *
	 * @param structureLayoutId the primary key for the new ddm structure layout
	 * @return the new ddm structure layout
	 */
	@Override
	public DDMStructureLayout create(long structureLayoutId) {
		DDMStructureLayout ddmStructureLayout = new DDMStructureLayoutImpl();

		ddmStructureLayout.setNew(true);
		ddmStructureLayout.setPrimaryKey(structureLayoutId);

		String uuid = PortalUUIDUtil.generate();

		ddmStructureLayout.setUuid(uuid);

		ddmStructureLayout.setCompanyId(CompanyThreadLocal.getCompanyId());

		return ddmStructureLayout;
	}

	/**
	 * Removes the ddm structure layout with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param structureLayoutId the primary key of the ddm structure layout
	 * @return the ddm structure layout that was removed
	 * @throws NoSuchStructureLayoutException if a ddm structure layout with the primary key could not be found
	 */
	@Override
	public DDMStructureLayout remove(long structureLayoutId)
		throws NoSuchStructureLayoutException {

		return remove((Serializable)structureLayoutId);
	}

	/**
	 * Removes the ddm structure layout with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the ddm structure layout
	 * @return the ddm structure layout that was removed
	 * @throws NoSuchStructureLayoutException if a ddm structure layout with the primary key could not be found
	 */
	@Override
	public DDMStructureLayout remove(Serializable primaryKey)
		throws NoSuchStructureLayoutException {

		Session session = null;

		try {
			session = openSession();

			DDMStructureLayout ddmStructureLayout =
				(DDMStructureLayout)session.get(
					DDMStructureLayoutImpl.class, primaryKey);

			if (ddmStructureLayout == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchStructureLayoutException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(ddmStructureLayout);
		}
		catch (NoSuchStructureLayoutException noSuchEntityException) {
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
	protected DDMStructureLayout removeImpl(
		DDMStructureLayout ddmStructureLayout) {

		if (!ctPersistenceHelper.isRemove(ddmStructureLayout)) {
			return ddmStructureLayout;
		}

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(ddmStructureLayout)) {
				ddmStructureLayout = (DDMStructureLayout)session.get(
					DDMStructureLayoutImpl.class,
					ddmStructureLayout.getPrimaryKeyObj());
			}

			if (ddmStructureLayout != null) {
				session.delete(ddmStructureLayout);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (ddmStructureLayout != null) {
			clearCache(ddmStructureLayout);
		}

		return ddmStructureLayout;
	}

	@Override
	public DDMStructureLayout updateImpl(
		DDMStructureLayout ddmStructureLayout) {

		boolean isNew = ddmStructureLayout.isNew();

		if (!(ddmStructureLayout instanceof DDMStructureLayoutModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(ddmStructureLayout.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					ddmStructureLayout);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in ddmStructureLayout proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom DDMStructureLayout implementation " +
					ddmStructureLayout.getClass());
		}

		DDMStructureLayoutModelImpl ddmStructureLayoutModelImpl =
			(DDMStructureLayoutModelImpl)ddmStructureLayout;

		if (Validator.isNull(ddmStructureLayout.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			ddmStructureLayout.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (ddmStructureLayout.getCreateDate() == null)) {
			if (serviceContext == null) {
				ddmStructureLayout.setCreateDate(now);
			}
			else {
				ddmStructureLayout.setCreateDate(
					serviceContext.getCreateDate(now));
			}
		}

		if (!ddmStructureLayoutModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				ddmStructureLayout.setModifiedDate(now);
			}
			else {
				ddmStructureLayout.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(ddmStructureLayout)) {
				if (!isNew) {
					session.evict(
						DDMStructureLayoutImpl.class,
						ddmStructureLayout.getPrimaryKeyObj());
				}

				session.save(ddmStructureLayout);

				ddmStructureLayout.setNew(false);
			}
			else {
				ddmStructureLayout = (DDMStructureLayout)session.merge(
					ddmStructureLayout);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (ddmStructureLayout.getCtCollectionId() != 0) {
			ddmStructureLayout.resetOriginalValues();

			return ddmStructureLayout;
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew) {
			Object[] args = new Object[] {
				ddmStructureLayoutModelImpl.getUuid()
			};

			finderCache.removeResult(_finderPathCountByUuid, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid, args);

			args = new Object[] {
				ddmStructureLayoutModelImpl.getUuid(),
				ddmStructureLayoutModelImpl.getCompanyId()
			};

			finderCache.removeResult(_finderPathCountByUuid_C, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid_C, args);

			args = new Object[] {ddmStructureLayoutModelImpl.getGroupId()};

			finderCache.removeResult(_finderPathCountByGroupId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByGroupId, args);

			args = new Object[] {
				ddmStructureLayoutModelImpl.getStructureLayoutKey()
			};

			finderCache.removeResult(
				_finderPathCountByStructureLayoutKey, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByStructureLayoutKey, args);

			args = new Object[] {
				ddmStructureLayoutModelImpl.getGroupId(),
				ddmStructureLayoutModelImpl.getClassNameId()
			};

			finderCache.removeResult(_finderPathCountByG_C, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByG_C, args);

			args = new Object[] {
				ddmStructureLayoutModelImpl.getGroupId(),
				ddmStructureLayoutModelImpl.getClassNameId(),
				ddmStructureLayoutModelImpl.getStructureVersionId()
			};

			finderCache.removeResult(_finderPathCountByG_C_SV, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByG_C_SV, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((ddmStructureLayoutModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					ddmStructureLayoutModelImpl.getOriginalUuid()
				};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);

				args = new Object[] {ddmStructureLayoutModelImpl.getUuid()};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);
			}

			if ((ddmStructureLayoutModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					ddmStructureLayoutModelImpl.getOriginalUuid(),
					ddmStructureLayoutModelImpl.getOriginalCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);

				args = new Object[] {
					ddmStructureLayoutModelImpl.getUuid(),
					ddmStructureLayoutModelImpl.getCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);
			}

			if ((ddmStructureLayoutModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByGroupId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					ddmStructureLayoutModelImpl.getOriginalGroupId()
				};

				finderCache.removeResult(_finderPathCountByGroupId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByGroupId, args);

				args = new Object[] {ddmStructureLayoutModelImpl.getGroupId()};

				finderCache.removeResult(_finderPathCountByGroupId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByGroupId, args);
			}

			if ((ddmStructureLayoutModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByStructureLayoutKey.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					ddmStructureLayoutModelImpl.getOriginalStructureLayoutKey()
				};

				finderCache.removeResult(
					_finderPathCountByStructureLayoutKey, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByStructureLayoutKey, args);

				args = new Object[] {
					ddmStructureLayoutModelImpl.getStructureLayoutKey()
				};

				finderCache.removeResult(
					_finderPathCountByStructureLayoutKey, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByStructureLayoutKey, args);
			}

			if ((ddmStructureLayoutModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					ddmStructureLayoutModelImpl.getOriginalGroupId(),
					ddmStructureLayoutModelImpl.getOriginalClassNameId()
				};

				finderCache.removeResult(_finderPathCountByG_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_C, args);

				args = new Object[] {
					ddmStructureLayoutModelImpl.getGroupId(),
					ddmStructureLayoutModelImpl.getClassNameId()
				};

				finderCache.removeResult(_finderPathCountByG_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_C, args);
			}

			if ((ddmStructureLayoutModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_C_SV.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					ddmStructureLayoutModelImpl.getOriginalGroupId(),
					ddmStructureLayoutModelImpl.getOriginalClassNameId(),
					ddmStructureLayoutModelImpl.getOriginalStructureVersionId()
				};

				finderCache.removeResult(_finderPathCountByG_C_SV, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_C_SV, args);

				args = new Object[] {
					ddmStructureLayoutModelImpl.getGroupId(),
					ddmStructureLayoutModelImpl.getClassNameId(),
					ddmStructureLayoutModelImpl.getStructureVersionId()
				};

				finderCache.removeResult(_finderPathCountByG_C_SV, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_C_SV, args);
			}
		}

		entityCache.putResult(
			DDMStructureLayoutImpl.class, ddmStructureLayout.getPrimaryKey(),
			ddmStructureLayout, false);

		clearUniqueFindersCache(ddmStructureLayoutModelImpl, false);
		cacheUniqueFindersCache(ddmStructureLayoutModelImpl);

		ddmStructureLayout.resetOriginalValues();

		return ddmStructureLayout;
	}

	/**
	 * Returns the ddm structure layout with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the ddm structure layout
	 * @return the ddm structure layout
	 * @throws NoSuchStructureLayoutException if a ddm structure layout with the primary key could not be found
	 */
	@Override
	public DDMStructureLayout findByPrimaryKey(Serializable primaryKey)
		throws NoSuchStructureLayoutException {

		DDMStructureLayout ddmStructureLayout = fetchByPrimaryKey(primaryKey);

		if (ddmStructureLayout == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchStructureLayoutException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return ddmStructureLayout;
	}

	/**
	 * Returns the ddm structure layout with the primary key or throws a <code>NoSuchStructureLayoutException</code> if it could not be found.
	 *
	 * @param structureLayoutId the primary key of the ddm structure layout
	 * @return the ddm structure layout
	 * @throws NoSuchStructureLayoutException if a ddm structure layout with the primary key could not be found
	 */
	@Override
	public DDMStructureLayout findByPrimaryKey(long structureLayoutId)
		throws NoSuchStructureLayoutException {

		return findByPrimaryKey((Serializable)structureLayoutId);
	}

	/**
	 * Returns the ddm structure layout with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the ddm structure layout
	 * @return the ddm structure layout, or <code>null</code> if a ddm structure layout with the primary key could not be found
	 */
	@Override
	public DDMStructureLayout fetchByPrimaryKey(Serializable primaryKey) {
		if (ctPersistenceHelper.isProductionMode(DDMStructureLayout.class)) {
			return super.fetchByPrimaryKey(primaryKey);
		}

		DDMStructureLayout ddmStructureLayout = null;

		Session session = null;

		try {
			session = openSession();

			ddmStructureLayout = (DDMStructureLayout)session.get(
				DDMStructureLayoutImpl.class, primaryKey);

			if (ddmStructureLayout != null) {
				cacheResult(ddmStructureLayout);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return ddmStructureLayout;
	}

	/**
	 * Returns the ddm structure layout with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param structureLayoutId the primary key of the ddm structure layout
	 * @return the ddm structure layout, or <code>null</code> if a ddm structure layout with the primary key could not be found
	 */
	@Override
	public DDMStructureLayout fetchByPrimaryKey(long structureLayoutId) {
		return fetchByPrimaryKey((Serializable)structureLayoutId);
	}

	@Override
	public Map<Serializable, DDMStructureLayout> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (ctPersistenceHelper.isProductionMode(DDMStructureLayout.class)) {
			return super.fetchByPrimaryKeys(primaryKeys);
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, DDMStructureLayout> map =
			new HashMap<Serializable, DDMStructureLayout>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			DDMStructureLayout ddmStructureLayout = fetchByPrimaryKey(
				primaryKey);

			if (ddmStructureLayout != null) {
				map.put(primaryKey, ddmStructureLayout);
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

			for (DDMStructureLayout ddmStructureLayout :
					(List<DDMStructureLayout>)query.list()) {

				map.put(
					ddmStructureLayout.getPrimaryKeyObj(), ddmStructureLayout);

				cacheResult(ddmStructureLayout);
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
	 * Returns all the ddm structure layouts.
	 *
	 * @return the ddm structure layouts
	 */
	@Override
	public List<DDMStructureLayout> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ddm structure layouts.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm structure layouts
	 * @param end the upper bound of the range of ddm structure layouts (not inclusive)
	 * @return the range of ddm structure layouts
	 */
	@Override
	public List<DDMStructureLayout> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the ddm structure layouts.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm structure layouts
	 * @param end the upper bound of the range of ddm structure layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ddm structure layouts
	 */
	@Override
	public List<DDMStructureLayout> findAll(
		int start, int end,
		OrderByComparator<DDMStructureLayout> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ddm structure layouts.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DDMStructureLayoutModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm structure layouts
	 * @param end the upper bound of the range of ddm structure layouts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of ddm structure layouts
	 */
	@Override
	public List<DDMStructureLayout> findAll(
		int start, int end,
		OrderByComparator<DDMStructureLayout> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDMStructureLayout.class);

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

		List<DDMStructureLayout> list = null;

		if (useFinderCache && productionMode) {
			list = (List<DDMStructureLayout>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_DDMSTRUCTURELAYOUT);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_DDMSTRUCTURELAYOUT;

				sql = sql.concat(DDMStructureLayoutModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<DDMStructureLayout>)QueryUtil.list(
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
	 * Removes all the ddm structure layouts from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (DDMStructureLayout ddmStructureLayout : findAll()) {
			remove(ddmStructureLayout);
		}
	}

	/**
	 * Returns the number of ddm structure layouts.
	 *
	 * @return the number of ddm structure layouts
	 */
	@Override
	public int countAll() {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			DDMStructureLayout.class);

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
					_SQL_COUNT_DDMSTRUCTURELAYOUT);

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
		return "structureLayoutId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_DDMSTRUCTURELAYOUT;
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
		return DDMStructureLayoutModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "DDMStructureLayout";
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
		ctStrictColumnNames.add("classNameId");
		ctStrictColumnNames.add("structureLayoutKey");
		ctStrictColumnNames.add("structureVersionId");
		ctStrictColumnNames.add("name");
		ctStrictColumnNames.add("description");
		ctStrictColumnNames.add("definition");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK,
			Collections.singleton("structureLayoutId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(new String[] {"uuid_", "groupId"});

		_uniqueIndexColumnNames.add(
			new String[] {"groupId", "classNameId", "structureLayoutKey"});
	}

	/**
	 * Initializes the ddm structure layout persistence.
	 */
	@Activate
	public void activate() {
		_finderPathWithPaginationFindAll = new FinderPath(
			DDMStructureLayoutImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			DDMStructureLayoutImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByUuid = new FinderPath(
			DDMStructureLayoutImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			DDMStructureLayoutImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] {String.class.getName()},
			DDMStructureLayoutModelImpl.UUID_COLUMN_BITMASK);

		_finderPathCountByUuid = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid", new String[] {String.class.getName()});

		_finderPathFetchByUUID_G = new FinderPath(
			DDMStructureLayoutImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			DDMStructureLayoutModelImpl.UUID_COLUMN_BITMASK |
			DDMStructureLayoutModelImpl.GROUPID_COLUMN_BITMASK);

		_finderPathCountByUUID_G = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			DDMStructureLayoutImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			DDMStructureLayoutImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			DDMStructureLayoutModelImpl.UUID_COLUMN_BITMASK |
			DDMStructureLayoutModelImpl.COMPANYID_COLUMN_BITMASK);

		_finderPathCountByUuid_C = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByGroupId = new FinderPath(
			DDMStructureLayoutImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByGroupId = new FinderPath(
			DDMStructureLayoutImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] {Long.class.getName()},
			DDMStructureLayoutModelImpl.GROUPID_COLUMN_BITMASK);

		_finderPathCountByGroupId = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByGroupId", new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByStructureLayoutKey = new FinderPath(
			DDMStructureLayoutImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByStructureLayoutKey",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByStructureLayoutKey = new FinderPath(
			DDMStructureLayoutImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByStructureLayoutKey", new String[] {String.class.getName()},
			DDMStructureLayoutModelImpl.STRUCTURELAYOUTKEY_COLUMN_BITMASK);

		_finderPathCountByStructureLayoutKey = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByStructureLayoutKey", new String[] {String.class.getName()});

		_finderPathFetchByStructureVersionId = new FinderPath(
			DDMStructureLayoutImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByStructureVersionId", new String[] {Long.class.getName()},
			DDMStructureLayoutModelImpl.STRUCTUREVERSIONID_COLUMN_BITMASK);

		_finderPathCountByStructureVersionId = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByStructureVersionId", new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByG_C = new FinderPath(
			DDMStructureLayoutImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_C = new FinderPath(
			DDMStructureLayoutImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			DDMStructureLayoutModelImpl.GROUPID_COLUMN_BITMASK |
			DDMStructureLayoutModelImpl.CLASSNAMEID_COLUMN_BITMASK);

		_finderPathCountByG_C = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C",
			new String[] {Long.class.getName(), Long.class.getName()});

		_finderPathFetchByG_C_S = new FinderPath(
			DDMStructureLayoutImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByG_C_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			DDMStructureLayoutModelImpl.GROUPID_COLUMN_BITMASK |
			DDMStructureLayoutModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			DDMStructureLayoutModelImpl.STRUCTURELAYOUTKEY_COLUMN_BITMASK);

		_finderPathCountByG_C_S = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_C_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});

		_finderPathWithPaginationFindByG_C_SV = new FinderPath(
			DDMStructureLayoutImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_C_SV",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_C_SV = new FinderPath(
			DDMStructureLayoutImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_C_SV",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			DDMStructureLayoutModelImpl.GROUPID_COLUMN_BITMASK |
			DDMStructureLayoutModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			DDMStructureLayoutModelImpl.STRUCTUREVERSIONID_COLUMN_BITMASK);

		_finderPathCountByG_C_SV = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_C_SV",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(DDMStructureLayoutImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	@Reference(
		target = DDMPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = DDMPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = DDMPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
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

	private static final String _SQL_SELECT_DDMSTRUCTURELAYOUT =
		"SELECT ddmStructureLayout FROM DDMStructureLayout ddmStructureLayout";

	private static final String _SQL_SELECT_DDMSTRUCTURELAYOUT_WHERE =
		"SELECT ddmStructureLayout FROM DDMStructureLayout ddmStructureLayout WHERE ";

	private static final String _SQL_COUNT_DDMSTRUCTURELAYOUT =
		"SELECT COUNT(ddmStructureLayout) FROM DDMStructureLayout ddmStructureLayout";

	private static final String _SQL_COUNT_DDMSTRUCTURELAYOUT_WHERE =
		"SELECT COUNT(ddmStructureLayout) FROM DDMStructureLayout ddmStructureLayout WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "ddmStructureLayout.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No DDMStructureLayout exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No DDMStructureLayout exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		DDMStructureLayoutPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	static {
		try {
			Class.forName(DDMPersistenceConstants.class.getName());
		}
		catch (ClassNotFoundException classNotFoundException) {
			throw new ExceptionInInitializerError(classNotFoundException);
		}
	}

}