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

package com.liferay.data.engine.service.persistence.impl;

import com.liferay.data.engine.exception.NoSuchDataListViewException;
import com.liferay.data.engine.model.DEDataListView;
import com.liferay.data.engine.model.DEDataListViewTable;
import com.liferay.data.engine.model.impl.DEDataListViewImpl;
import com.liferay.data.engine.model.impl.DEDataListViewModelImpl;
import com.liferay.data.engine.service.persistence.DEDataListViewPersistence;
import com.liferay.data.engine.service.persistence.impl.constants.DEPersistenceConstants;
import com.liferay.petra.string.StringBundler;
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
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Date;
import java.util.HashMap;
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
 * The persistence implementation for the de data list view service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = DEDataListViewPersistence.class)
public class DEDataListViewPersistenceImpl
	extends BasePersistenceImpl<DEDataListView>
	implements DEDataListViewPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>DEDataListViewUtil</code> to access the de data list view persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		DEDataListViewImpl.class.getName();

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
	 * Returns all the de data list views where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching de data list views
	 */
	@Override
	public List<DEDataListView> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the de data list views where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DEDataListViewModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of de data list views
	 * @param end the upper bound of the range of de data list views (not inclusive)
	 * @return the range of matching de data list views
	 */
	@Override
	public List<DEDataListView> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the de data list views where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DEDataListViewModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of de data list views
	 * @param end the upper bound of the range of de data list views (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching de data list views
	 */
	@Override
	public List<DEDataListView> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<DEDataListView> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the de data list views where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DEDataListViewModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of de data list views
	 * @param end the upper bound of the range of de data list views (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching de data list views
	 */
	@Override
	public List<DEDataListView> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<DEDataListView> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUuid;
				finderArgs = new Object[] {uuid};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUuid;
			finderArgs = new Object[] {uuid, start, end, orderByComparator};
		}

		List<DEDataListView> list = null;

		if (useFinderCache) {
			list = (List<DEDataListView>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DEDataListView deDataListView : list) {
					if (!uuid.equals(deDataListView.getUuid())) {
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

			sb.append(_SQL_SELECT_DEDATALISTVIEW_WHERE);

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
				sb.append(DEDataListViewModelImpl.ORDER_BY_JPQL);
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

				list = (List<DEDataListView>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
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
	 * Returns the first de data list view in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching de data list view
	 * @throws NoSuchDataListViewException if a matching de data list view could not be found
	 */
	@Override
	public DEDataListView findByUuid_First(
			String uuid, OrderByComparator<DEDataListView> orderByComparator)
		throws NoSuchDataListViewException {

		DEDataListView deDataListView = fetchByUuid_First(
			uuid, orderByComparator);

		if (deDataListView != null) {
			return deDataListView;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchDataListViewException(sb.toString());
	}

	/**
	 * Returns the first de data list view in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching de data list view, or <code>null</code> if a matching de data list view could not be found
	 */
	@Override
	public DEDataListView fetchByUuid_First(
		String uuid, OrderByComparator<DEDataListView> orderByComparator) {

		List<DEDataListView> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last de data list view in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching de data list view
	 * @throws NoSuchDataListViewException if a matching de data list view could not be found
	 */
	@Override
	public DEDataListView findByUuid_Last(
			String uuid, OrderByComparator<DEDataListView> orderByComparator)
		throws NoSuchDataListViewException {

		DEDataListView deDataListView = fetchByUuid_Last(
			uuid, orderByComparator);

		if (deDataListView != null) {
			return deDataListView;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchDataListViewException(sb.toString());
	}

	/**
	 * Returns the last de data list view in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching de data list view, or <code>null</code> if a matching de data list view could not be found
	 */
	@Override
	public DEDataListView fetchByUuid_Last(
		String uuid, OrderByComparator<DEDataListView> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<DEDataListView> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the de data list views before and after the current de data list view in the ordered set where uuid = &#63;.
	 *
	 * @param deDataListViewId the primary key of the current de data list view
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next de data list view
	 * @throws NoSuchDataListViewException if a de data list view with the primary key could not be found
	 */
	@Override
	public DEDataListView[] findByUuid_PrevAndNext(
			long deDataListViewId, String uuid,
			OrderByComparator<DEDataListView> orderByComparator)
		throws NoSuchDataListViewException {

		uuid = Objects.toString(uuid, "");

		DEDataListView deDataListView = findByPrimaryKey(deDataListViewId);

		Session session = null;

		try {
			session = openSession();

			DEDataListView[] array = new DEDataListViewImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, deDataListView, uuid, orderByComparator, true);

			array[1] = deDataListView;

			array[2] = getByUuid_PrevAndNext(
				session, deDataListView, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected DEDataListView getByUuid_PrevAndNext(
		Session session, DEDataListView deDataListView, String uuid,
		OrderByComparator<DEDataListView> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_DEDATALISTVIEW_WHERE);

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
			sb.append(DEDataListViewModelImpl.ORDER_BY_JPQL);
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
						deDataListView)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<DEDataListView> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the de data list views where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (DEDataListView deDataListView :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(deDataListView);
		}
	}

	/**
	 * Returns the number of de data list views where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching de data list views
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_DEDATALISTVIEW_WHERE);

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

				finderCache.putResult(finderPath, finderArgs, count);
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
		"deDataListView.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(deDataListView.uuid IS NULL OR deDataListView.uuid = '')";

	private FinderPath _finderPathFetchByUUID_G;
	private FinderPath _finderPathCountByUUID_G;

	/**
	 * Returns the de data list view where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchDataListViewException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching de data list view
	 * @throws NoSuchDataListViewException if a matching de data list view could not be found
	 */
	@Override
	public DEDataListView findByUUID_G(String uuid, long groupId)
		throws NoSuchDataListViewException {

		DEDataListView deDataListView = fetchByUUID_G(uuid, groupId);

		if (deDataListView == null) {
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

			throw new NoSuchDataListViewException(sb.toString());
		}

		return deDataListView;
	}

	/**
	 * Returns the de data list view where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching de data list view, or <code>null</code> if a matching de data list view could not be found
	 */
	@Override
	public DEDataListView fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the de data list view where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching de data list view, or <code>null</code> if a matching de data list view could not be found
	 */
	@Override
	public DEDataListView fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {uuid, groupId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByUUID_G, finderArgs, this);
		}

		if (result instanceof DEDataListView) {
			DEDataListView deDataListView = (DEDataListView)result;

			if (!Objects.equals(uuid, deDataListView.getUuid()) ||
				(groupId != deDataListView.getGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_DEDATALISTVIEW_WHERE);

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

				List<DEDataListView> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByUUID_G, finderArgs, list);
					}
				}
				else {
					DEDataListView deDataListView = list.get(0);

					result = deDataListView;

					cacheResult(deDataListView);
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
			return (DEDataListView)result;
		}
	}

	/**
	 * Removes the de data list view where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the de data list view that was removed
	 */
	@Override
	public DEDataListView removeByUUID_G(String uuid, long groupId)
		throws NoSuchDataListViewException {

		DEDataListView deDataListView = findByUUID_G(uuid, groupId);

		return remove(deDataListView);
	}

	/**
	 * Returns the number of de data list views where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching de data list views
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUUID_G;

		Object[] finderArgs = new Object[] {uuid, groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_DEDATALISTVIEW_WHERE);

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

				finderCache.putResult(finderPath, finderArgs, count);
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
		"deDataListView.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(deDataListView.uuid IS NULL OR deDataListView.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"deDataListView.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the de data list views where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching de data list views
	 */
	@Override
	public List<DEDataListView> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the de data list views where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DEDataListViewModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of de data list views
	 * @param end the upper bound of the range of de data list views (not inclusive)
	 * @return the range of matching de data list views
	 */
	@Override
	public List<DEDataListView> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the de data list views where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DEDataListViewModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of de data list views
	 * @param end the upper bound of the range of de data list views (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching de data list views
	 */
	@Override
	public List<DEDataListView> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<DEDataListView> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the de data list views where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DEDataListViewModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of de data list views
	 * @param end the upper bound of the range of de data list views (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching de data list views
	 */
	@Override
	public List<DEDataListView> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<DEDataListView> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUuid_C;
				finderArgs = new Object[] {uuid, companyId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUuid_C;
			finderArgs = new Object[] {
				uuid, companyId, start, end, orderByComparator
			};
		}

		List<DEDataListView> list = null;

		if (useFinderCache) {
			list = (List<DEDataListView>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DEDataListView deDataListView : list) {
					if (!uuid.equals(deDataListView.getUuid()) ||
						(companyId != deDataListView.getCompanyId())) {

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

			sb.append(_SQL_SELECT_DEDATALISTVIEW_WHERE);

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
				sb.append(DEDataListViewModelImpl.ORDER_BY_JPQL);
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

				list = (List<DEDataListView>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
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
	 * Returns the first de data list view in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching de data list view
	 * @throws NoSuchDataListViewException if a matching de data list view could not be found
	 */
	@Override
	public DEDataListView findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<DEDataListView> orderByComparator)
		throws NoSuchDataListViewException {

		DEDataListView deDataListView = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (deDataListView != null) {
			return deDataListView;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchDataListViewException(sb.toString());
	}

	/**
	 * Returns the first de data list view in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching de data list view, or <code>null</code> if a matching de data list view could not be found
	 */
	@Override
	public DEDataListView fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<DEDataListView> orderByComparator) {

		List<DEDataListView> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last de data list view in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching de data list view
	 * @throws NoSuchDataListViewException if a matching de data list view could not be found
	 */
	@Override
	public DEDataListView findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<DEDataListView> orderByComparator)
		throws NoSuchDataListViewException {

		DEDataListView deDataListView = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (deDataListView != null) {
			return deDataListView;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchDataListViewException(sb.toString());
	}

	/**
	 * Returns the last de data list view in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching de data list view, or <code>null</code> if a matching de data list view could not be found
	 */
	@Override
	public DEDataListView fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<DEDataListView> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<DEDataListView> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the de data list views before and after the current de data list view in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param deDataListViewId the primary key of the current de data list view
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next de data list view
	 * @throws NoSuchDataListViewException if a de data list view with the primary key could not be found
	 */
	@Override
	public DEDataListView[] findByUuid_C_PrevAndNext(
			long deDataListViewId, String uuid, long companyId,
			OrderByComparator<DEDataListView> orderByComparator)
		throws NoSuchDataListViewException {

		uuid = Objects.toString(uuid, "");

		DEDataListView deDataListView = findByPrimaryKey(deDataListViewId);

		Session session = null;

		try {
			session = openSession();

			DEDataListView[] array = new DEDataListViewImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, deDataListView, uuid, companyId, orderByComparator,
				true);

			array[1] = deDataListView;

			array[2] = getByUuid_C_PrevAndNext(
				session, deDataListView, uuid, companyId, orderByComparator,
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

	protected DEDataListView getByUuid_C_PrevAndNext(
		Session session, DEDataListView deDataListView, String uuid,
		long companyId, OrderByComparator<DEDataListView> orderByComparator,
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

		sb.append(_SQL_SELECT_DEDATALISTVIEW_WHERE);

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
			sb.append(DEDataListViewModelImpl.ORDER_BY_JPQL);
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
						deDataListView)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<DEDataListView> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the de data list views where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (DEDataListView deDataListView :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(deDataListView);
		}
	}

	/**
	 * Returns the number of de data list views where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching de data list views
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_DEDATALISTVIEW_WHERE);

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

				finderCache.putResult(finderPath, finderArgs, count);
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
		"deDataListView.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(deDataListView.uuid IS NULL OR deDataListView.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"deDataListView.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByDDMStructureId;
	private FinderPath _finderPathWithoutPaginationFindByDDMStructureId;
	private FinderPath _finderPathCountByDDMStructureId;

	/**
	 * Returns all the de data list views where ddmStructureId = &#63;.
	 *
	 * @param ddmStructureId the ddm structure ID
	 * @return the matching de data list views
	 */
	@Override
	public List<DEDataListView> findByDDMStructureId(long ddmStructureId) {
		return findByDDMStructureId(
			ddmStructureId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the de data list views where ddmStructureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DEDataListViewModelImpl</code>.
	 * </p>
	 *
	 * @param ddmStructureId the ddm structure ID
	 * @param start the lower bound of the range of de data list views
	 * @param end the upper bound of the range of de data list views (not inclusive)
	 * @return the range of matching de data list views
	 */
	@Override
	public List<DEDataListView> findByDDMStructureId(
		long ddmStructureId, int start, int end) {

		return findByDDMStructureId(ddmStructureId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the de data list views where ddmStructureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DEDataListViewModelImpl</code>.
	 * </p>
	 *
	 * @param ddmStructureId the ddm structure ID
	 * @param start the lower bound of the range of de data list views
	 * @param end the upper bound of the range of de data list views (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching de data list views
	 */
	@Override
	public List<DEDataListView> findByDDMStructureId(
		long ddmStructureId, int start, int end,
		OrderByComparator<DEDataListView> orderByComparator) {

		return findByDDMStructureId(
			ddmStructureId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the de data list views where ddmStructureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DEDataListViewModelImpl</code>.
	 * </p>
	 *
	 * @param ddmStructureId the ddm structure ID
	 * @param start the lower bound of the range of de data list views
	 * @param end the upper bound of the range of de data list views (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching de data list views
	 */
	@Override
	public List<DEDataListView> findByDDMStructureId(
		long ddmStructureId, int start, int end,
		OrderByComparator<DEDataListView> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByDDMStructureId;
				finderArgs = new Object[] {ddmStructureId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByDDMStructureId;
			finderArgs = new Object[] {
				ddmStructureId, start, end, orderByComparator
			};
		}

		List<DEDataListView> list = null;

		if (useFinderCache) {
			list = (List<DEDataListView>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DEDataListView deDataListView : list) {
					if (ddmStructureId != deDataListView.getDdmStructureId()) {
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

			sb.append(_SQL_SELECT_DEDATALISTVIEW_WHERE);

			sb.append(_FINDER_COLUMN_DDMSTRUCTUREID_DDMSTRUCTUREID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(DEDataListViewModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(ddmStructureId);

				list = (List<DEDataListView>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
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
	 * Returns the first de data list view in the ordered set where ddmStructureId = &#63;.
	 *
	 * @param ddmStructureId the ddm structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching de data list view
	 * @throws NoSuchDataListViewException if a matching de data list view could not be found
	 */
	@Override
	public DEDataListView findByDDMStructureId_First(
			long ddmStructureId,
			OrderByComparator<DEDataListView> orderByComparator)
		throws NoSuchDataListViewException {

		DEDataListView deDataListView = fetchByDDMStructureId_First(
			ddmStructureId, orderByComparator);

		if (deDataListView != null) {
			return deDataListView;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("ddmStructureId=");
		sb.append(ddmStructureId);

		sb.append("}");

		throw new NoSuchDataListViewException(sb.toString());
	}

	/**
	 * Returns the first de data list view in the ordered set where ddmStructureId = &#63;.
	 *
	 * @param ddmStructureId the ddm structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching de data list view, or <code>null</code> if a matching de data list view could not be found
	 */
	@Override
	public DEDataListView fetchByDDMStructureId_First(
		long ddmStructureId,
		OrderByComparator<DEDataListView> orderByComparator) {

		List<DEDataListView> list = findByDDMStructureId(
			ddmStructureId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last de data list view in the ordered set where ddmStructureId = &#63;.
	 *
	 * @param ddmStructureId the ddm structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching de data list view
	 * @throws NoSuchDataListViewException if a matching de data list view could not be found
	 */
	@Override
	public DEDataListView findByDDMStructureId_Last(
			long ddmStructureId,
			OrderByComparator<DEDataListView> orderByComparator)
		throws NoSuchDataListViewException {

		DEDataListView deDataListView = fetchByDDMStructureId_Last(
			ddmStructureId, orderByComparator);

		if (deDataListView != null) {
			return deDataListView;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("ddmStructureId=");
		sb.append(ddmStructureId);

		sb.append("}");

		throw new NoSuchDataListViewException(sb.toString());
	}

	/**
	 * Returns the last de data list view in the ordered set where ddmStructureId = &#63;.
	 *
	 * @param ddmStructureId the ddm structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching de data list view, or <code>null</code> if a matching de data list view could not be found
	 */
	@Override
	public DEDataListView fetchByDDMStructureId_Last(
		long ddmStructureId,
		OrderByComparator<DEDataListView> orderByComparator) {

		int count = countByDDMStructureId(ddmStructureId);

		if (count == 0) {
			return null;
		}

		List<DEDataListView> list = findByDDMStructureId(
			ddmStructureId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the de data list views before and after the current de data list view in the ordered set where ddmStructureId = &#63;.
	 *
	 * @param deDataListViewId the primary key of the current de data list view
	 * @param ddmStructureId the ddm structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next de data list view
	 * @throws NoSuchDataListViewException if a de data list view with the primary key could not be found
	 */
	@Override
	public DEDataListView[] findByDDMStructureId_PrevAndNext(
			long deDataListViewId, long ddmStructureId,
			OrderByComparator<DEDataListView> orderByComparator)
		throws NoSuchDataListViewException {

		DEDataListView deDataListView = findByPrimaryKey(deDataListViewId);

		Session session = null;

		try {
			session = openSession();

			DEDataListView[] array = new DEDataListViewImpl[3];

			array[0] = getByDDMStructureId_PrevAndNext(
				session, deDataListView, ddmStructureId, orderByComparator,
				true);

			array[1] = deDataListView;

			array[2] = getByDDMStructureId_PrevAndNext(
				session, deDataListView, ddmStructureId, orderByComparator,
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

	protected DEDataListView getByDDMStructureId_PrevAndNext(
		Session session, DEDataListView deDataListView, long ddmStructureId,
		OrderByComparator<DEDataListView> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_DEDATALISTVIEW_WHERE);

		sb.append(_FINDER_COLUMN_DDMSTRUCTUREID_DDMSTRUCTUREID_2);

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
			sb.append(DEDataListViewModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(ddmStructureId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						deDataListView)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<DEDataListView> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the de data list views where ddmStructureId = &#63; from the database.
	 *
	 * @param ddmStructureId the ddm structure ID
	 */
	@Override
	public void removeByDDMStructureId(long ddmStructureId) {
		for (DEDataListView deDataListView :
				findByDDMStructureId(
					ddmStructureId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(deDataListView);
		}
	}

	/**
	 * Returns the number of de data list views where ddmStructureId = &#63;.
	 *
	 * @param ddmStructureId the ddm structure ID
	 * @return the number of matching de data list views
	 */
	@Override
	public int countByDDMStructureId(long ddmStructureId) {
		FinderPath finderPath = _finderPathCountByDDMStructureId;

		Object[] finderArgs = new Object[] {ddmStructureId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_DEDATALISTVIEW_WHERE);

			sb.append(_FINDER_COLUMN_DDMSTRUCTUREID_DDMSTRUCTUREID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(ddmStructureId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
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

	private static final String _FINDER_COLUMN_DDMSTRUCTUREID_DDMSTRUCTUREID_2 =
		"deDataListView.ddmStructureId = ?";

	private FinderPath _finderPathWithPaginationFindByG_C_DDMSI;
	private FinderPath _finderPathWithoutPaginationFindByG_C_DDMSI;
	private FinderPath _finderPathCountByG_C_DDMSI;

	/**
	 * Returns all the de data list views where groupId = &#63; and companyId = &#63; and ddmStructureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param ddmStructureId the ddm structure ID
	 * @return the matching de data list views
	 */
	@Override
	public List<DEDataListView> findByG_C_DDMSI(
		long groupId, long companyId, long ddmStructureId) {

		return findByG_C_DDMSI(
			groupId, companyId, ddmStructureId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the de data list views where groupId = &#63; and companyId = &#63; and ddmStructureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DEDataListViewModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param ddmStructureId the ddm structure ID
	 * @param start the lower bound of the range of de data list views
	 * @param end the upper bound of the range of de data list views (not inclusive)
	 * @return the range of matching de data list views
	 */
	@Override
	public List<DEDataListView> findByG_C_DDMSI(
		long groupId, long companyId, long ddmStructureId, int start, int end) {

		return findByG_C_DDMSI(
			groupId, companyId, ddmStructureId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the de data list views where groupId = &#63; and companyId = &#63; and ddmStructureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DEDataListViewModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param ddmStructureId the ddm structure ID
	 * @param start the lower bound of the range of de data list views
	 * @param end the upper bound of the range of de data list views (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching de data list views
	 */
	@Override
	public List<DEDataListView> findByG_C_DDMSI(
		long groupId, long companyId, long ddmStructureId, int start, int end,
		OrderByComparator<DEDataListView> orderByComparator) {

		return findByG_C_DDMSI(
			groupId, companyId, ddmStructureId, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the de data list views where groupId = &#63; and companyId = &#63; and ddmStructureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DEDataListViewModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param ddmStructureId the ddm structure ID
	 * @param start the lower bound of the range of de data list views
	 * @param end the upper bound of the range of de data list views (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching de data list views
	 */
	@Override
	public List<DEDataListView> findByG_C_DDMSI(
		long groupId, long companyId, long ddmStructureId, int start, int end,
		OrderByComparator<DEDataListView> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByG_C_DDMSI;
				finderArgs = new Object[] {groupId, companyId, ddmStructureId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByG_C_DDMSI;
			finderArgs = new Object[] {
				groupId, companyId, ddmStructureId, start, end,
				orderByComparator
			};
		}

		List<DEDataListView> list = null;

		if (useFinderCache) {
			list = (List<DEDataListView>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DEDataListView deDataListView : list) {
					if ((groupId != deDataListView.getGroupId()) ||
						(companyId != deDataListView.getCompanyId()) ||
						(ddmStructureId !=
							deDataListView.getDdmStructureId())) {

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

			sb.append(_SQL_SELECT_DEDATALISTVIEW_WHERE);

			sb.append(_FINDER_COLUMN_G_C_DDMSI_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_DDMSI_COMPANYID_2);

			sb.append(_FINDER_COLUMN_G_C_DDMSI_DDMSTRUCTUREID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(DEDataListViewModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(companyId);

				queryPos.add(ddmStructureId);

				list = (List<DEDataListView>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
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
	 * Returns the first de data list view in the ordered set where groupId = &#63; and companyId = &#63; and ddmStructureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param ddmStructureId the ddm structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching de data list view
	 * @throws NoSuchDataListViewException if a matching de data list view could not be found
	 */
	@Override
	public DEDataListView findByG_C_DDMSI_First(
			long groupId, long companyId, long ddmStructureId,
			OrderByComparator<DEDataListView> orderByComparator)
		throws NoSuchDataListViewException {

		DEDataListView deDataListView = fetchByG_C_DDMSI_First(
			groupId, companyId, ddmStructureId, orderByComparator);

		if (deDataListView != null) {
			return deDataListView;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append(", ddmStructureId=");
		sb.append(ddmStructureId);

		sb.append("}");

		throw new NoSuchDataListViewException(sb.toString());
	}

	/**
	 * Returns the first de data list view in the ordered set where groupId = &#63; and companyId = &#63; and ddmStructureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param ddmStructureId the ddm structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching de data list view, or <code>null</code> if a matching de data list view could not be found
	 */
	@Override
	public DEDataListView fetchByG_C_DDMSI_First(
		long groupId, long companyId, long ddmStructureId,
		OrderByComparator<DEDataListView> orderByComparator) {

		List<DEDataListView> list = findByG_C_DDMSI(
			groupId, companyId, ddmStructureId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last de data list view in the ordered set where groupId = &#63; and companyId = &#63; and ddmStructureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param ddmStructureId the ddm structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching de data list view
	 * @throws NoSuchDataListViewException if a matching de data list view could not be found
	 */
	@Override
	public DEDataListView findByG_C_DDMSI_Last(
			long groupId, long companyId, long ddmStructureId,
			OrderByComparator<DEDataListView> orderByComparator)
		throws NoSuchDataListViewException {

		DEDataListView deDataListView = fetchByG_C_DDMSI_Last(
			groupId, companyId, ddmStructureId, orderByComparator);

		if (deDataListView != null) {
			return deDataListView;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append(", ddmStructureId=");
		sb.append(ddmStructureId);

		sb.append("}");

		throw new NoSuchDataListViewException(sb.toString());
	}

	/**
	 * Returns the last de data list view in the ordered set where groupId = &#63; and companyId = &#63; and ddmStructureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param ddmStructureId the ddm structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching de data list view, or <code>null</code> if a matching de data list view could not be found
	 */
	@Override
	public DEDataListView fetchByG_C_DDMSI_Last(
		long groupId, long companyId, long ddmStructureId,
		OrderByComparator<DEDataListView> orderByComparator) {

		int count = countByG_C_DDMSI(groupId, companyId, ddmStructureId);

		if (count == 0) {
			return null;
		}

		List<DEDataListView> list = findByG_C_DDMSI(
			groupId, companyId, ddmStructureId, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the de data list views before and after the current de data list view in the ordered set where groupId = &#63; and companyId = &#63; and ddmStructureId = &#63;.
	 *
	 * @param deDataListViewId the primary key of the current de data list view
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param ddmStructureId the ddm structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next de data list view
	 * @throws NoSuchDataListViewException if a de data list view with the primary key could not be found
	 */
	@Override
	public DEDataListView[] findByG_C_DDMSI_PrevAndNext(
			long deDataListViewId, long groupId, long companyId,
			long ddmStructureId,
			OrderByComparator<DEDataListView> orderByComparator)
		throws NoSuchDataListViewException {

		DEDataListView deDataListView = findByPrimaryKey(deDataListViewId);

		Session session = null;

		try {
			session = openSession();

			DEDataListView[] array = new DEDataListViewImpl[3];

			array[0] = getByG_C_DDMSI_PrevAndNext(
				session, deDataListView, groupId, companyId, ddmStructureId,
				orderByComparator, true);

			array[1] = deDataListView;

			array[2] = getByG_C_DDMSI_PrevAndNext(
				session, deDataListView, groupId, companyId, ddmStructureId,
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

	protected DEDataListView getByG_C_DDMSI_PrevAndNext(
		Session session, DEDataListView deDataListView, long groupId,
		long companyId, long ddmStructureId,
		OrderByComparator<DEDataListView> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		sb.append(_SQL_SELECT_DEDATALISTVIEW_WHERE);

		sb.append(_FINDER_COLUMN_G_C_DDMSI_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_DDMSI_COMPANYID_2);

		sb.append(_FINDER_COLUMN_G_C_DDMSI_DDMSTRUCTUREID_2);

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
			sb.append(DEDataListViewModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(companyId);

		queryPos.add(ddmStructureId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						deDataListView)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<DEDataListView> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the de data list views where groupId = &#63; and companyId = &#63; and ddmStructureId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param ddmStructureId the ddm structure ID
	 */
	@Override
	public void removeByG_C_DDMSI(
		long groupId, long companyId, long ddmStructureId) {

		for (DEDataListView deDataListView :
				findByG_C_DDMSI(
					groupId, companyId, ddmStructureId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(deDataListView);
		}
	}

	/**
	 * Returns the number of de data list views where groupId = &#63; and companyId = &#63; and ddmStructureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param ddmStructureId the ddm structure ID
	 * @return the number of matching de data list views
	 */
	@Override
	public int countByG_C_DDMSI(
		long groupId, long companyId, long ddmStructureId) {

		FinderPath finderPath = _finderPathCountByG_C_DDMSI;

		Object[] finderArgs = new Object[] {groupId, companyId, ddmStructureId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_DEDATALISTVIEW_WHERE);

			sb.append(_FINDER_COLUMN_G_C_DDMSI_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_DDMSI_COMPANYID_2);

			sb.append(_FINDER_COLUMN_G_C_DDMSI_DDMSTRUCTUREID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(companyId);

				queryPos.add(ddmStructureId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
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

	private static final String _FINDER_COLUMN_G_C_DDMSI_GROUPID_2 =
		"deDataListView.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_DDMSI_COMPANYID_2 =
		"deDataListView.companyId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_DDMSI_DDMSTRUCTUREID_2 =
		"deDataListView.ddmStructureId = ?";

	public DEDataListViewPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(DEDataListView.class);

		setModelImplClass(DEDataListViewImpl.class);
		setModelPKClass(long.class);

		setTable(DEDataListViewTable.INSTANCE);
	}

	/**
	 * Caches the de data list view in the entity cache if it is enabled.
	 *
	 * @param deDataListView the de data list view
	 */
	@Override
	public void cacheResult(DEDataListView deDataListView) {
		entityCache.putResult(
			DEDataListViewImpl.class, deDataListView.getPrimaryKey(),
			deDataListView);

		finderCache.putResult(
			_finderPathFetchByUUID_G,
			new Object[] {
				deDataListView.getUuid(), deDataListView.getGroupId()
			},
			deDataListView);
	}

	/**
	 * Caches the de data list views in the entity cache if it is enabled.
	 *
	 * @param deDataListViews the de data list views
	 */
	@Override
	public void cacheResult(List<DEDataListView> deDataListViews) {
		for (DEDataListView deDataListView : deDataListViews) {
			if (entityCache.getResult(
					DEDataListViewImpl.class, deDataListView.getPrimaryKey()) ==
						null) {

				cacheResult(deDataListView);
			}
		}
	}

	/**
	 * Clears the cache for all de data list views.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(DEDataListViewImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the de data list view.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(DEDataListView deDataListView) {
		entityCache.removeResult(
			DEDataListViewImpl.class, deDataListView.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((DEDataListViewModelImpl)deDataListView, true);
	}

	@Override
	public void clearCache(List<DEDataListView> deDataListViews) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (DEDataListView deDataListView : deDataListViews) {
			entityCache.removeResult(
				DEDataListViewImpl.class, deDataListView.getPrimaryKey());

			clearUniqueFindersCache(
				(DEDataListViewModelImpl)deDataListView, true);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(DEDataListViewImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		DEDataListViewModelImpl deDataListViewModelImpl) {

		Object[] args = new Object[] {
			deDataListViewModelImpl.getUuid(),
			deDataListViewModelImpl.getGroupId()
		};

		finderCache.putResult(
			_finderPathCountByUUID_G, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByUUID_G, args, deDataListViewModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		DEDataListViewModelImpl deDataListViewModelImpl, boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				deDataListViewModelImpl.getUuid(),
				deDataListViewModelImpl.getGroupId()
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(_finderPathFetchByUUID_G, args);
		}

		if ((deDataListViewModelImpl.getColumnBitmask() &
			 _finderPathFetchByUUID_G.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				deDataListViewModelImpl.getColumnOriginalValue("uuid_"),
				deDataListViewModelImpl.getColumnOriginalValue("groupId")
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(_finderPathFetchByUUID_G, args);
		}
	}

	/**
	 * Creates a new de data list view with the primary key. Does not add the de data list view to the database.
	 *
	 * @param deDataListViewId the primary key for the new de data list view
	 * @return the new de data list view
	 */
	@Override
	public DEDataListView create(long deDataListViewId) {
		DEDataListView deDataListView = new DEDataListViewImpl();

		deDataListView.setNew(true);
		deDataListView.setPrimaryKey(deDataListViewId);

		String uuid = PortalUUIDUtil.generate();

		deDataListView.setUuid(uuid);

		deDataListView.setCompanyId(CompanyThreadLocal.getCompanyId());

		return deDataListView;
	}

	/**
	 * Removes the de data list view with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param deDataListViewId the primary key of the de data list view
	 * @return the de data list view that was removed
	 * @throws NoSuchDataListViewException if a de data list view with the primary key could not be found
	 */
	@Override
	public DEDataListView remove(long deDataListViewId)
		throws NoSuchDataListViewException {

		return remove((Serializable)deDataListViewId);
	}

	/**
	 * Removes the de data list view with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the de data list view
	 * @return the de data list view that was removed
	 * @throws NoSuchDataListViewException if a de data list view with the primary key could not be found
	 */
	@Override
	public DEDataListView remove(Serializable primaryKey)
		throws NoSuchDataListViewException {

		Session session = null;

		try {
			session = openSession();

			DEDataListView deDataListView = (DEDataListView)session.get(
				DEDataListViewImpl.class, primaryKey);

			if (deDataListView == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchDataListViewException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(deDataListView);
		}
		catch (NoSuchDataListViewException noSuchEntityException) {
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
	protected DEDataListView removeImpl(DEDataListView deDataListView) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(deDataListView)) {
				deDataListView = (DEDataListView)session.get(
					DEDataListViewImpl.class,
					deDataListView.getPrimaryKeyObj());
			}

			if (deDataListView != null) {
				session.delete(deDataListView);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (deDataListView != null) {
			clearCache(deDataListView);
		}

		return deDataListView;
	}

	@Override
	public DEDataListView updateImpl(DEDataListView deDataListView) {
		boolean isNew = deDataListView.isNew();

		if (!(deDataListView instanceof DEDataListViewModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(deDataListView.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					deDataListView);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in deDataListView proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom DEDataListView implementation " +
					deDataListView.getClass());
		}

		DEDataListViewModelImpl deDataListViewModelImpl =
			(DEDataListViewModelImpl)deDataListView;

		if (Validator.isNull(deDataListView.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			deDataListView.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (deDataListView.getCreateDate() == null)) {
			if (serviceContext == null) {
				deDataListView.setCreateDate(now);
			}
			else {
				deDataListView.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!deDataListViewModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				deDataListView.setModifiedDate(now);
			}
			else {
				deDataListView.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (deDataListView.isNew()) {
				session.save(deDataListView);

				deDataListView.setNew(false);
			}
			else {
				deDataListView = (DEDataListView)session.merge(deDataListView);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew) {
			Object[] args = new Object[] {deDataListViewModelImpl.getUuid()};

			finderCache.removeResult(_finderPathCountByUuid, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid, args);

			args = new Object[] {
				deDataListViewModelImpl.getUuid(),
				deDataListViewModelImpl.getCompanyId()
			};

			finderCache.removeResult(_finderPathCountByUuid_C, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid_C, args);

			args = new Object[] {deDataListViewModelImpl.getDdmStructureId()};

			finderCache.removeResult(_finderPathCountByDDMStructureId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByDDMStructureId, args);

			args = new Object[] {
				deDataListViewModelImpl.getGroupId(),
				deDataListViewModelImpl.getCompanyId(),
				deDataListViewModelImpl.getDdmStructureId()
			};

			finderCache.removeResult(_finderPathCountByG_C_DDMSI, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByG_C_DDMSI, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((deDataListViewModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					deDataListViewModelImpl.getColumnOriginalValue("uuid_")
				};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);

				args = new Object[] {deDataListViewModelImpl.getUuid()};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);
			}

			if ((deDataListViewModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					deDataListViewModelImpl.getColumnOriginalValue("uuid_"),
					deDataListViewModelImpl.getColumnOriginalValue("companyId")
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);

				args = new Object[] {
					deDataListViewModelImpl.getUuid(),
					deDataListViewModelImpl.getCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);
			}

			if ((deDataListViewModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByDDMStructureId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					deDataListViewModelImpl.getColumnOriginalValue(
						"ddmStructureId")
				};

				finderCache.removeResult(
					_finderPathCountByDDMStructureId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByDDMStructureId, args);

				args = new Object[] {
					deDataListViewModelImpl.getDdmStructureId()
				};

				finderCache.removeResult(
					_finderPathCountByDDMStructureId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByDDMStructureId, args);
			}

			if ((deDataListViewModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_C_DDMSI.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					deDataListViewModelImpl.getColumnOriginalValue("groupId"),
					deDataListViewModelImpl.getColumnOriginalValue("companyId"),
					deDataListViewModelImpl.getColumnOriginalValue(
						"ddmStructureId")
				};

				finderCache.removeResult(_finderPathCountByG_C_DDMSI, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_C_DDMSI, args);

				args = new Object[] {
					deDataListViewModelImpl.getGroupId(),
					deDataListViewModelImpl.getCompanyId(),
					deDataListViewModelImpl.getDdmStructureId()
				};

				finderCache.removeResult(_finderPathCountByG_C_DDMSI, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_C_DDMSI, args);
			}
		}

		entityCache.putResult(
			DEDataListViewImpl.class, deDataListView.getPrimaryKey(),
			deDataListView, false);

		clearUniqueFindersCache(deDataListViewModelImpl, false);
		cacheUniqueFindersCache(deDataListViewModelImpl);

		deDataListView.resetOriginalValues();

		return deDataListView;
	}

	/**
	 * Returns the de data list view with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the de data list view
	 * @return the de data list view
	 * @throws NoSuchDataListViewException if a de data list view with the primary key could not be found
	 */
	@Override
	public DEDataListView findByPrimaryKey(Serializable primaryKey)
		throws NoSuchDataListViewException {

		DEDataListView deDataListView = fetchByPrimaryKey(primaryKey);

		if (deDataListView == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchDataListViewException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return deDataListView;
	}

	/**
	 * Returns the de data list view with the primary key or throws a <code>NoSuchDataListViewException</code> if it could not be found.
	 *
	 * @param deDataListViewId the primary key of the de data list view
	 * @return the de data list view
	 * @throws NoSuchDataListViewException if a de data list view with the primary key could not be found
	 */
	@Override
	public DEDataListView findByPrimaryKey(long deDataListViewId)
		throws NoSuchDataListViewException {

		return findByPrimaryKey((Serializable)deDataListViewId);
	}

	/**
	 * Returns the de data list view with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param deDataListViewId the primary key of the de data list view
	 * @return the de data list view, or <code>null</code> if a de data list view with the primary key could not be found
	 */
	@Override
	public DEDataListView fetchByPrimaryKey(long deDataListViewId) {
		return fetchByPrimaryKey((Serializable)deDataListViewId);
	}

	/**
	 * Returns all the de data list views.
	 *
	 * @return the de data list views
	 */
	@Override
	public List<DEDataListView> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the de data list views.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DEDataListViewModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of de data list views
	 * @param end the upper bound of the range of de data list views (not inclusive)
	 * @return the range of de data list views
	 */
	@Override
	public List<DEDataListView> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the de data list views.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DEDataListViewModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of de data list views
	 * @param end the upper bound of the range of de data list views (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of de data list views
	 */
	@Override
	public List<DEDataListView> findAll(
		int start, int end,
		OrderByComparator<DEDataListView> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the de data list views.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DEDataListViewModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of de data list views
	 * @param end the upper bound of the range of de data list views (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of de data list views
	 */
	@Override
	public List<DEDataListView> findAll(
		int start, int end, OrderByComparator<DEDataListView> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindAll;
				finderArgs = FINDER_ARGS_EMPTY;
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<DEDataListView> list = null;

		if (useFinderCache) {
			list = (List<DEDataListView>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_DEDATALISTVIEW);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_DEDATALISTVIEW;

				sql = sql.concat(DEDataListViewModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<DEDataListView>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
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
	 * Removes all the de data list views from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (DEDataListView deDataListView : findAll()) {
			remove(deDataListView);
		}
	}

	/**
	 * Returns the number of de data list views.
	 *
	 * @return the number of de data list views
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_DEDATALISTVIEW);

				count = (Long)query.uniqueResult();

				finderCache.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
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
		return "deDataListViewId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_DEDATALISTVIEW;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return DEDataListViewModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the de data list view persistence.
	 */
	@Activate
	public void activate() {
		_finderPathWithPaginationFindAll = new FinderPath(
			DEDataListViewImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			DEDataListViewImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findAll", new String[0]);

		_finderPathCountAll = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByUuid = new FinderPath(
			DEDataListViewImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			DEDataListViewImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByUuid", new String[] {String.class.getName()},
			DEDataListViewModelImpl.getColumnBitmask("uuid_"));

		_finderPathCountByUuid = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid", new String[] {String.class.getName()});

		_finderPathFetchByUUID_G = new FinderPath(
			DEDataListViewImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			DEDataListViewModelImpl.getColumnBitmask("uuid_") |
			DEDataListViewModelImpl.getColumnBitmask("groupId"));

		_finderPathCountByUUID_G = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			DEDataListViewImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			DEDataListViewImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			DEDataListViewModelImpl.getColumnBitmask("uuid_") |
			DEDataListViewModelImpl.getColumnBitmask("companyId"));

		_finderPathCountByUuid_C = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByDDMStructureId = new FinderPath(
			DEDataListViewImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByDDMStructureId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByDDMStructureId = new FinderPath(
			DEDataListViewImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByDDMStructureId", new String[] {Long.class.getName()},
			DEDataListViewModelImpl.getColumnBitmask("ddmStructureId"));

		_finderPathCountByDDMStructureId = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByDDMStructureId", new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByG_C_DDMSI = new FinderPath(
			DEDataListViewImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_C_DDMSI",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_C_DDMSI = new FinderPath(
			DEDataListViewImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByG_C_DDMSI",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			DEDataListViewModelImpl.getColumnBitmask("groupId") |
			DEDataListViewModelImpl.getColumnBitmask("companyId") |
			DEDataListViewModelImpl.getColumnBitmask("ddmStructureId"));

		_finderPathCountByG_C_DDMSI = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_C_DDMSI",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(DEDataListViewImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	@Reference(
		target = DEPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = DEPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = DEPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_DEDATALISTVIEW =
		"SELECT deDataListView FROM DEDataListView deDataListView";

	private static final String _SQL_SELECT_DEDATALISTVIEW_WHERE =
		"SELECT deDataListView FROM DEDataListView deDataListView WHERE ";

	private static final String _SQL_COUNT_DEDATALISTVIEW =
		"SELECT COUNT(deDataListView) FROM DEDataListView deDataListView";

	private static final String _SQL_COUNT_DEDATALISTVIEW_WHERE =
		"SELECT COUNT(deDataListView) FROM DEDataListView deDataListView WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "deDataListView.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No DEDataListView exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No DEDataListView exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		DEDataListViewPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	static {
		try {
			Class.forName(DEPersistenceConstants.class.getName());
		}
		catch (ClassNotFoundException classNotFoundException) {
			throw new ExceptionInInitializerError(classNotFoundException);
		}
	}

}