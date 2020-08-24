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

package com.liferay.site.navigation.service.persistence.impl;

import com.liferay.petra.string.StringBundler;
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
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.site.navigation.exception.NoSuchMenuException;
import com.liferay.site.navigation.model.SiteNavigationMenu;
import com.liferay.site.navigation.model.SiteNavigationMenuTable;
import com.liferay.site.navigation.model.impl.SiteNavigationMenuImpl;
import com.liferay.site.navigation.model.impl.SiteNavigationMenuModelImpl;
import com.liferay.site.navigation.service.persistence.SiteNavigationMenuPersistence;
import com.liferay.site.navigation.service.persistence.impl.constants.SiteNavigationPersistenceConstants;

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
 * The persistence implementation for the site navigation menu service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = SiteNavigationMenuPersistence.class)
public class SiteNavigationMenuPersistenceImpl
	extends BasePersistenceImpl<SiteNavigationMenu>
	implements SiteNavigationMenuPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>SiteNavigationMenuUtil</code> to access the site navigation menu persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		SiteNavigationMenuImpl.class.getName();

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
	 * Returns all the site navigation menus where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching site navigation menus
	 */
	@Override
	public List<SiteNavigationMenu> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the site navigation menus where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @return the range of matching site navigation menus
	 */
	@Override
	public List<SiteNavigationMenu> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the site navigation menus where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching site navigation menus
	 */
	@Override
	public List<SiteNavigationMenu> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<SiteNavigationMenu> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the site navigation menus where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching site navigation menus
	 */
	@Override
	public List<SiteNavigationMenu> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<SiteNavigationMenu> orderByComparator,
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

		List<SiteNavigationMenu> list = null;

		if (useFinderCache) {
			list = (List<SiteNavigationMenu>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SiteNavigationMenu siteNavigationMenu : list) {
					if (!uuid.equals(siteNavigationMenu.getUuid())) {
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

			sb.append(_SQL_SELECT_SITENAVIGATIONMENU_WHERE);

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
				sb.append(SiteNavigationMenuModelImpl.ORDER_BY_JPQL);
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

				list = (List<SiteNavigationMenu>)QueryUtil.list(
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
	 * Returns the first site navigation menu in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching site navigation menu
	 * @throws NoSuchMenuException if a matching site navigation menu could not be found
	 */
	@Override
	public SiteNavigationMenu findByUuid_First(
			String uuid,
			OrderByComparator<SiteNavigationMenu> orderByComparator)
		throws NoSuchMenuException {

		SiteNavigationMenu siteNavigationMenu = fetchByUuid_First(
			uuid, orderByComparator);

		if (siteNavigationMenu != null) {
			return siteNavigationMenu;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchMenuException(sb.toString());
	}

	/**
	 * Returns the first site navigation menu in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching site navigation menu, or <code>null</code> if a matching site navigation menu could not be found
	 */
	@Override
	public SiteNavigationMenu fetchByUuid_First(
		String uuid, OrderByComparator<SiteNavigationMenu> orderByComparator) {

		List<SiteNavigationMenu> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last site navigation menu in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching site navigation menu
	 * @throws NoSuchMenuException if a matching site navigation menu could not be found
	 */
	@Override
	public SiteNavigationMenu findByUuid_Last(
			String uuid,
			OrderByComparator<SiteNavigationMenu> orderByComparator)
		throws NoSuchMenuException {

		SiteNavigationMenu siteNavigationMenu = fetchByUuid_Last(
			uuid, orderByComparator);

		if (siteNavigationMenu != null) {
			return siteNavigationMenu;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchMenuException(sb.toString());
	}

	/**
	 * Returns the last site navigation menu in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching site navigation menu, or <code>null</code> if a matching site navigation menu could not be found
	 */
	@Override
	public SiteNavigationMenu fetchByUuid_Last(
		String uuid, OrderByComparator<SiteNavigationMenu> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<SiteNavigationMenu> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the site navigation menus before and after the current site navigation menu in the ordered set where uuid = &#63;.
	 *
	 * @param siteNavigationMenuId the primary key of the current site navigation menu
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next site navigation menu
	 * @throws NoSuchMenuException if a site navigation menu with the primary key could not be found
	 */
	@Override
	public SiteNavigationMenu[] findByUuid_PrevAndNext(
			long siteNavigationMenuId, String uuid,
			OrderByComparator<SiteNavigationMenu> orderByComparator)
		throws NoSuchMenuException {

		uuid = Objects.toString(uuid, "");

		SiteNavigationMenu siteNavigationMenu = findByPrimaryKey(
			siteNavigationMenuId);

		Session session = null;

		try {
			session = openSession();

			SiteNavigationMenu[] array = new SiteNavigationMenuImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, siteNavigationMenu, uuid, orderByComparator, true);

			array[1] = siteNavigationMenu;

			array[2] = getByUuid_PrevAndNext(
				session, siteNavigationMenu, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected SiteNavigationMenu getByUuid_PrevAndNext(
		Session session, SiteNavigationMenu siteNavigationMenu, String uuid,
		OrderByComparator<SiteNavigationMenu> orderByComparator,
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

		sb.append(_SQL_SELECT_SITENAVIGATIONMENU_WHERE);

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
			sb.append(SiteNavigationMenuModelImpl.ORDER_BY_JPQL);
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
						siteNavigationMenu)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SiteNavigationMenu> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the site navigation menus where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (SiteNavigationMenu siteNavigationMenu :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(siteNavigationMenu);
		}
	}

	/**
	 * Returns the number of site navigation menus where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching site navigation menus
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_SITENAVIGATIONMENU_WHERE);

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
		"siteNavigationMenu.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(siteNavigationMenu.uuid IS NULL OR siteNavigationMenu.uuid = '')";

	private FinderPath _finderPathFetchByUUID_G;
	private FinderPath _finderPathCountByUUID_G;

	/**
	 * Returns the site navigation menu where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchMenuException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching site navigation menu
	 * @throws NoSuchMenuException if a matching site navigation menu could not be found
	 */
	@Override
	public SiteNavigationMenu findByUUID_G(String uuid, long groupId)
		throws NoSuchMenuException {

		SiteNavigationMenu siteNavigationMenu = fetchByUUID_G(uuid, groupId);

		if (siteNavigationMenu == null) {
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

			throw new NoSuchMenuException(sb.toString());
		}

		return siteNavigationMenu;
	}

	/**
	 * Returns the site navigation menu where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching site navigation menu, or <code>null</code> if a matching site navigation menu could not be found
	 */
	@Override
	public SiteNavigationMenu fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the site navigation menu where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching site navigation menu, or <code>null</code> if a matching site navigation menu could not be found
	 */
	@Override
	public SiteNavigationMenu fetchByUUID_G(
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

		if (result instanceof SiteNavigationMenu) {
			SiteNavigationMenu siteNavigationMenu = (SiteNavigationMenu)result;

			if (!Objects.equals(uuid, siteNavigationMenu.getUuid()) ||
				(groupId != siteNavigationMenu.getGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_SITENAVIGATIONMENU_WHERE);

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

				List<SiteNavigationMenu> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByUUID_G, finderArgs, list);
					}
				}
				else {
					SiteNavigationMenu siteNavigationMenu = list.get(0);

					result = siteNavigationMenu;

					cacheResult(siteNavigationMenu);
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
			return (SiteNavigationMenu)result;
		}
	}

	/**
	 * Removes the site navigation menu where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the site navigation menu that was removed
	 */
	@Override
	public SiteNavigationMenu removeByUUID_G(String uuid, long groupId)
		throws NoSuchMenuException {

		SiteNavigationMenu siteNavigationMenu = findByUUID_G(uuid, groupId);

		return remove(siteNavigationMenu);
	}

	/**
	 * Returns the number of site navigation menus where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching site navigation menus
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUUID_G;

		Object[] finderArgs = new Object[] {uuid, groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_SITENAVIGATIONMENU_WHERE);

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
		"siteNavigationMenu.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(siteNavigationMenu.uuid IS NULL OR siteNavigationMenu.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"siteNavigationMenu.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the site navigation menus where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching site navigation menus
	 */
	@Override
	public List<SiteNavigationMenu> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the site navigation menus where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @return the range of matching site navigation menus
	 */
	@Override
	public List<SiteNavigationMenu> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the site navigation menus where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching site navigation menus
	 */
	@Override
	public List<SiteNavigationMenu> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<SiteNavigationMenu> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the site navigation menus where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching site navigation menus
	 */
	@Override
	public List<SiteNavigationMenu> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<SiteNavigationMenu> orderByComparator,
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

		List<SiteNavigationMenu> list = null;

		if (useFinderCache) {
			list = (List<SiteNavigationMenu>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SiteNavigationMenu siteNavigationMenu : list) {
					if (!uuid.equals(siteNavigationMenu.getUuid()) ||
						(companyId != siteNavigationMenu.getCompanyId())) {

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

			sb.append(_SQL_SELECT_SITENAVIGATIONMENU_WHERE);

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
				sb.append(SiteNavigationMenuModelImpl.ORDER_BY_JPQL);
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

				list = (List<SiteNavigationMenu>)QueryUtil.list(
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
	 * Returns the first site navigation menu in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching site navigation menu
	 * @throws NoSuchMenuException if a matching site navigation menu could not be found
	 */
	@Override
	public SiteNavigationMenu findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<SiteNavigationMenu> orderByComparator)
		throws NoSuchMenuException {

		SiteNavigationMenu siteNavigationMenu = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (siteNavigationMenu != null) {
			return siteNavigationMenu;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchMenuException(sb.toString());
	}

	/**
	 * Returns the first site navigation menu in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching site navigation menu, or <code>null</code> if a matching site navigation menu could not be found
	 */
	@Override
	public SiteNavigationMenu fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<SiteNavigationMenu> orderByComparator) {

		List<SiteNavigationMenu> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last site navigation menu in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching site navigation menu
	 * @throws NoSuchMenuException if a matching site navigation menu could not be found
	 */
	@Override
	public SiteNavigationMenu findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<SiteNavigationMenu> orderByComparator)
		throws NoSuchMenuException {

		SiteNavigationMenu siteNavigationMenu = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (siteNavigationMenu != null) {
			return siteNavigationMenu;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchMenuException(sb.toString());
	}

	/**
	 * Returns the last site navigation menu in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching site navigation menu, or <code>null</code> if a matching site navigation menu could not be found
	 */
	@Override
	public SiteNavigationMenu fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<SiteNavigationMenu> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<SiteNavigationMenu> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the site navigation menus before and after the current site navigation menu in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param siteNavigationMenuId the primary key of the current site navigation menu
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next site navigation menu
	 * @throws NoSuchMenuException if a site navigation menu with the primary key could not be found
	 */
	@Override
	public SiteNavigationMenu[] findByUuid_C_PrevAndNext(
			long siteNavigationMenuId, String uuid, long companyId,
			OrderByComparator<SiteNavigationMenu> orderByComparator)
		throws NoSuchMenuException {

		uuid = Objects.toString(uuid, "");

		SiteNavigationMenu siteNavigationMenu = findByPrimaryKey(
			siteNavigationMenuId);

		Session session = null;

		try {
			session = openSession();

			SiteNavigationMenu[] array = new SiteNavigationMenuImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, siteNavigationMenu, uuid, companyId, orderByComparator,
				true);

			array[1] = siteNavigationMenu;

			array[2] = getByUuid_C_PrevAndNext(
				session, siteNavigationMenu, uuid, companyId, orderByComparator,
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

	protected SiteNavigationMenu getByUuid_C_PrevAndNext(
		Session session, SiteNavigationMenu siteNavigationMenu, String uuid,
		long companyId, OrderByComparator<SiteNavigationMenu> orderByComparator,
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

		sb.append(_SQL_SELECT_SITENAVIGATIONMENU_WHERE);

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
			sb.append(SiteNavigationMenuModelImpl.ORDER_BY_JPQL);
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
						siteNavigationMenu)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SiteNavigationMenu> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the site navigation menus where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (SiteNavigationMenu siteNavigationMenu :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(siteNavigationMenu);
		}
	}

	/**
	 * Returns the number of site navigation menus where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching site navigation menus
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_SITENAVIGATIONMENU_WHERE);

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
		"siteNavigationMenu.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(siteNavigationMenu.uuid IS NULL OR siteNavigationMenu.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"siteNavigationMenu.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByGroupId;
	private FinderPath _finderPathWithoutPaginationFindByGroupId;
	private FinderPath _finderPathCountByGroupId;
	private FinderPath _finderPathWithPaginationCountByGroupId;

	/**
	 * Returns all the site navigation menus where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching site navigation menus
	 */
	@Override
	public List<SiteNavigationMenu> findByGroupId(long groupId) {
		return findByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the site navigation menus where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @return the range of matching site navigation menus
	 */
	@Override
	public List<SiteNavigationMenu> findByGroupId(
		long groupId, int start, int end) {

		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the site navigation menus where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching site navigation menus
	 */
	@Override
	public List<SiteNavigationMenu> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<SiteNavigationMenu> orderByComparator) {

		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the site navigation menus where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching site navigation menus
	 */
	@Override
	public List<SiteNavigationMenu> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<SiteNavigationMenu> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByGroupId;
				finderArgs = new Object[] {groupId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByGroupId;
			finderArgs = new Object[] {groupId, start, end, orderByComparator};
		}

		List<SiteNavigationMenu> list = null;

		if (useFinderCache) {
			list = (List<SiteNavigationMenu>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SiteNavigationMenu siteNavigationMenu : list) {
					if (groupId != siteNavigationMenu.getGroupId()) {
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

			sb.append(_SQL_SELECT_SITENAVIGATIONMENU_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(SiteNavigationMenuModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				list = (List<SiteNavigationMenu>)QueryUtil.list(
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
	 * Returns the first site navigation menu in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching site navigation menu
	 * @throws NoSuchMenuException if a matching site navigation menu could not be found
	 */
	@Override
	public SiteNavigationMenu findByGroupId_First(
			long groupId,
			OrderByComparator<SiteNavigationMenu> orderByComparator)
		throws NoSuchMenuException {

		SiteNavigationMenu siteNavigationMenu = fetchByGroupId_First(
			groupId, orderByComparator);

		if (siteNavigationMenu != null) {
			return siteNavigationMenu;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchMenuException(sb.toString());
	}

	/**
	 * Returns the first site navigation menu in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching site navigation menu, or <code>null</code> if a matching site navigation menu could not be found
	 */
	@Override
	public SiteNavigationMenu fetchByGroupId_First(
		long groupId, OrderByComparator<SiteNavigationMenu> orderByComparator) {

		List<SiteNavigationMenu> list = findByGroupId(
			groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last site navigation menu in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching site navigation menu
	 * @throws NoSuchMenuException if a matching site navigation menu could not be found
	 */
	@Override
	public SiteNavigationMenu findByGroupId_Last(
			long groupId,
			OrderByComparator<SiteNavigationMenu> orderByComparator)
		throws NoSuchMenuException {

		SiteNavigationMenu siteNavigationMenu = fetchByGroupId_Last(
			groupId, orderByComparator);

		if (siteNavigationMenu != null) {
			return siteNavigationMenu;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchMenuException(sb.toString());
	}

	/**
	 * Returns the last site navigation menu in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching site navigation menu, or <code>null</code> if a matching site navigation menu could not be found
	 */
	@Override
	public SiteNavigationMenu fetchByGroupId_Last(
		long groupId, OrderByComparator<SiteNavigationMenu> orderByComparator) {

		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<SiteNavigationMenu> list = findByGroupId(
			groupId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the site navigation menus before and after the current site navigation menu in the ordered set where groupId = &#63;.
	 *
	 * @param siteNavigationMenuId the primary key of the current site navigation menu
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next site navigation menu
	 * @throws NoSuchMenuException if a site navigation menu with the primary key could not be found
	 */
	@Override
	public SiteNavigationMenu[] findByGroupId_PrevAndNext(
			long siteNavigationMenuId, long groupId,
			OrderByComparator<SiteNavigationMenu> orderByComparator)
		throws NoSuchMenuException {

		SiteNavigationMenu siteNavigationMenu = findByPrimaryKey(
			siteNavigationMenuId);

		Session session = null;

		try {
			session = openSession();

			SiteNavigationMenu[] array = new SiteNavigationMenuImpl[3];

			array[0] = getByGroupId_PrevAndNext(
				session, siteNavigationMenu, groupId, orderByComparator, true);

			array[1] = siteNavigationMenu;

			array[2] = getByGroupId_PrevAndNext(
				session, siteNavigationMenu, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected SiteNavigationMenu getByGroupId_PrevAndNext(
		Session session, SiteNavigationMenu siteNavigationMenu, long groupId,
		OrderByComparator<SiteNavigationMenu> orderByComparator,
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

		sb.append(_SQL_SELECT_SITENAVIGATIONMENU_WHERE);

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
			sb.append(SiteNavigationMenuModelImpl.ORDER_BY_JPQL);
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
						siteNavigationMenu)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SiteNavigationMenu> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the site navigation menus that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching site navigation menus that the user has permission to view
	 */
	@Override
	public List<SiteNavigationMenu> filterFindByGroupId(long groupId) {
		return filterFindByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the site navigation menus that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @return the range of matching site navigation menus that the user has permission to view
	 */
	@Override
	public List<SiteNavigationMenu> filterFindByGroupId(
		long groupId, int start, int end) {

		return filterFindByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the site navigation menus that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching site navigation menus that the user has permission to view
	 */
	@Override
	public List<SiteNavigationMenu> filterFindByGroupId(
		long groupId, int start, int end,
		OrderByComparator<SiteNavigationMenu> orderByComparator) {

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
			sb.append(_FILTER_SQL_SELECT_SITENAVIGATIONMENU_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_SITENAVIGATIONMENU_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_SITENAVIGATIONMENU_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(SiteNavigationMenuModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(SiteNavigationMenuModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), SiteNavigationMenu.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, SiteNavigationMenuImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, SiteNavigationMenuImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			return (List<SiteNavigationMenu>)QueryUtil.list(
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
	 * Returns the site navigation menus before and after the current site navigation menu in the ordered set of site navigation menus that the user has permission to view where groupId = &#63;.
	 *
	 * @param siteNavigationMenuId the primary key of the current site navigation menu
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next site navigation menu
	 * @throws NoSuchMenuException if a site navigation menu with the primary key could not be found
	 */
	@Override
	public SiteNavigationMenu[] filterFindByGroupId_PrevAndNext(
			long siteNavigationMenuId, long groupId,
			OrderByComparator<SiteNavigationMenu> orderByComparator)
		throws NoSuchMenuException {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId_PrevAndNext(
				siteNavigationMenuId, groupId, orderByComparator);
		}

		SiteNavigationMenu siteNavigationMenu = findByPrimaryKey(
			siteNavigationMenuId);

		Session session = null;

		try {
			session = openSession();

			SiteNavigationMenu[] array = new SiteNavigationMenuImpl[3];

			array[0] = filterGetByGroupId_PrevAndNext(
				session, siteNavigationMenu, groupId, orderByComparator, true);

			array[1] = siteNavigationMenu;

			array[2] = filterGetByGroupId_PrevAndNext(
				session, siteNavigationMenu, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected SiteNavigationMenu filterGetByGroupId_PrevAndNext(
		Session session, SiteNavigationMenu siteNavigationMenu, long groupId,
		OrderByComparator<SiteNavigationMenu> orderByComparator,
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
			sb.append(_FILTER_SQL_SELECT_SITENAVIGATIONMENU_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_SITENAVIGATIONMENU_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_SITENAVIGATIONMENU_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(SiteNavigationMenuModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(SiteNavigationMenuModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), SiteNavigationMenu.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(
				_FILTER_ENTITY_ALIAS, SiteNavigationMenuImpl.class);
		}
		else {
			sqlQuery.addEntity(
				_FILTER_ENTITY_TABLE, SiteNavigationMenuImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(groupId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						siteNavigationMenu)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SiteNavigationMenu> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the site navigation menus that the user has permission to view where groupId = any &#63;.
	 *
	 * @param groupIds the group IDs
	 * @return the matching site navigation menus that the user has permission to view
	 */
	@Override
	public List<SiteNavigationMenu> filterFindByGroupId(long[] groupIds) {
		return filterFindByGroupId(
			groupIds, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the site navigation menus that the user has permission to view where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @return the range of matching site navigation menus that the user has permission to view
	 */
	@Override
	public List<SiteNavigationMenu> filterFindByGroupId(
		long[] groupIds, int start, int end) {

		return filterFindByGroupId(groupIds, start, end, null);
	}

	/**
	 * Returns an ordered range of all the site navigation menus that the user has permission to view where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching site navigation menus that the user has permission to view
	 */
	@Override
	public List<SiteNavigationMenu> filterFindByGroupId(
		long[] groupIds, int start, int end,
		OrderByComparator<SiteNavigationMenu> orderByComparator) {

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
			sb.append(_FILTER_SQL_SELECT_SITENAVIGATIONMENU_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_SITENAVIGATIONMENU_NO_INLINE_DISTINCT_WHERE_1);
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
				_FILTER_SQL_SELECT_SITENAVIGATIONMENU_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(SiteNavigationMenuModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(SiteNavigationMenuModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), SiteNavigationMenu.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupIds);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, SiteNavigationMenuImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, SiteNavigationMenuImpl.class);
			}

			return (List<SiteNavigationMenu>)QueryUtil.list(
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
	 * Returns all the site navigation menus where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @return the matching site navigation menus
	 */
	@Override
	public List<SiteNavigationMenu> findByGroupId(long[] groupIds) {
		return findByGroupId(
			groupIds, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the site navigation menus where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @return the range of matching site navigation menus
	 */
	@Override
	public List<SiteNavigationMenu> findByGroupId(
		long[] groupIds, int start, int end) {

		return findByGroupId(groupIds, start, end, null);
	}

	/**
	 * Returns an ordered range of all the site navigation menus where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching site navigation menus
	 */
	@Override
	public List<SiteNavigationMenu> findByGroupId(
		long[] groupIds, int start, int end,
		OrderByComparator<SiteNavigationMenu> orderByComparator) {

		return findByGroupId(groupIds, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the site navigation menus where groupId = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching site navigation menus
	 */
	@Override
	public List<SiteNavigationMenu> findByGroupId(
		long[] groupIds, int start, int end,
		OrderByComparator<SiteNavigationMenu> orderByComparator,
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

		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderArgs = new Object[] {StringUtil.merge(groupIds)};
			}
		}
		else if (useFinderCache) {
			finderArgs = new Object[] {
				StringUtil.merge(groupIds), start, end, orderByComparator
			};
		}

		List<SiteNavigationMenu> list = null;

		if (useFinderCache) {
			list = (List<SiteNavigationMenu>)finderCache.getResult(
				_finderPathWithPaginationFindByGroupId, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SiteNavigationMenu siteNavigationMenu : list) {
					if (!ArrayUtil.contains(
							groupIds, siteNavigationMenu.getGroupId())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_SELECT_SITENAVIGATIONMENU_WHERE);

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
				sb.append(SiteNavigationMenuModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<SiteNavigationMenu>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
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
	 * Removes all the site navigation menus where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (SiteNavigationMenu siteNavigationMenu :
				findByGroupId(
					groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(siteNavigationMenu);
		}
	}

	/**
	 * Returns the number of site navigation menus where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching site navigation menus
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = _finderPathCountByGroupId;

		Object[] finderArgs = new Object[] {groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_SITENAVIGATIONMENU_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

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

	/**
	 * Returns the number of site navigation menus where groupId = any &#63;.
	 *
	 * @param groupIds the group IDs
	 * @return the number of matching site navigation menus
	 */
	@Override
	public int countByGroupId(long[] groupIds) {
		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.sortedUnique(groupIds);
		}

		Object[] finderArgs = new Object[] {StringUtil.merge(groupIds)};

		Long count = (Long)finderCache.getResult(
			_finderPathWithPaginationCountByGroupId, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_COUNT_SITENAVIGATIONMENU_WHERE);

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

				finderCache.putResult(
					_finderPathWithPaginationCountByGroupId, finderArgs, count);
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
	 * Returns the number of site navigation menus that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching site navigation menus that the user has permission to view
	 */
	@Override
	public int filterCountByGroupId(long groupId) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByGroupId(groupId);
		}

		StringBundler sb = new StringBundler(2);

		sb.append(_FILTER_SQL_COUNT_SITENAVIGATIONMENU_WHERE);

		sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), SiteNavigationMenu.class.getName(),
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
	 * Returns the number of site navigation menus that the user has permission to view where groupId = any &#63;.
	 *
	 * @param groupIds the group IDs
	 * @return the number of matching site navigation menus that the user has permission to view
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

		sb.append(_FILTER_SQL_COUNT_SITENAVIGATIONMENU_WHERE);

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
			sb.toString(), SiteNavigationMenu.class.getName(),
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
		"siteNavigationMenu.groupId = ?";

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_7 =
		"siteNavigationMenu.groupId IN (";

	private FinderPath _finderPathFetchByG_N;
	private FinderPath _finderPathCountByG_N;

	/**
	 * Returns the site navigation menu where groupId = &#63; and name = &#63; or throws a <code>NoSuchMenuException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the matching site navigation menu
	 * @throws NoSuchMenuException if a matching site navigation menu could not be found
	 */
	@Override
	public SiteNavigationMenu findByG_N(long groupId, String name)
		throws NoSuchMenuException {

		SiteNavigationMenu siteNavigationMenu = fetchByG_N(groupId, name);

		if (siteNavigationMenu == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("groupId=");
			sb.append(groupId);

			sb.append(", name=");
			sb.append(name);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchMenuException(sb.toString());
		}

		return siteNavigationMenu;
	}

	/**
	 * Returns the site navigation menu where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the matching site navigation menu, or <code>null</code> if a matching site navigation menu could not be found
	 */
	@Override
	public SiteNavigationMenu fetchByG_N(long groupId, String name) {
		return fetchByG_N(groupId, name, true);
	}

	/**
	 * Returns the site navigation menu where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching site navigation menu, or <code>null</code> if a matching site navigation menu could not be found
	 */
	@Override
	public SiteNavigationMenu fetchByG_N(
		long groupId, String name, boolean useFinderCache) {

		name = Objects.toString(name, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {groupId, name};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByG_N, finderArgs, this);
		}

		if (result instanceof SiteNavigationMenu) {
			SiteNavigationMenu siteNavigationMenu = (SiteNavigationMenu)result;

			if ((groupId != siteNavigationMenu.getGroupId()) ||
				!Objects.equals(name, siteNavigationMenu.getName())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_SITENAVIGATIONMENU_WHERE);

			sb.append(_FINDER_COLUMN_G_N_GROUPID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_N_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_G_N_NAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindName) {
					queryPos.add(name);
				}

				List<SiteNavigationMenu> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByG_N, finderArgs, list);
					}
				}
				else {
					SiteNavigationMenu siteNavigationMenu = list.get(0);

					result = siteNavigationMenu;

					cacheResult(siteNavigationMenu);
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
			return (SiteNavigationMenu)result;
		}
	}

	/**
	 * Removes the site navigation menu where groupId = &#63; and name = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the site navigation menu that was removed
	 */
	@Override
	public SiteNavigationMenu removeByG_N(long groupId, String name)
		throws NoSuchMenuException {

		SiteNavigationMenu siteNavigationMenu = findByG_N(groupId, name);

		return remove(siteNavigationMenu);
	}

	/**
	 * Returns the number of site navigation menus where groupId = &#63; and name = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the number of matching site navigation menus
	 */
	@Override
	public int countByG_N(long groupId, String name) {
		name = Objects.toString(name, "");

		FinderPath finderPath = _finderPathCountByG_N;

		Object[] finderArgs = new Object[] {groupId, name};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_SITENAVIGATIONMENU_WHERE);

			sb.append(_FINDER_COLUMN_G_N_GROUPID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_N_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_G_N_NAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindName) {
					queryPos.add(name);
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

	private static final String _FINDER_COLUMN_G_N_GROUPID_2 =
		"siteNavigationMenu.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_N_NAME_2 =
		"siteNavigationMenu.name = ?";

	private static final String _FINDER_COLUMN_G_N_NAME_3 =
		"(siteNavigationMenu.name IS NULL OR siteNavigationMenu.name = '')";

	private FinderPath _finderPathWithPaginationFindByG_LikeN;
	private FinderPath _finderPathWithPaginationCountByG_LikeN;

	/**
	 * Returns all the site navigation menus where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the matching site navigation menus
	 */
	@Override
	public List<SiteNavigationMenu> findByG_LikeN(long groupId, String name) {
		return findByG_LikeN(
			groupId, name, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the site navigation menus where groupId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @return the range of matching site navigation menus
	 */
	@Override
	public List<SiteNavigationMenu> findByG_LikeN(
		long groupId, String name, int start, int end) {

		return findByG_LikeN(groupId, name, start, end, null);
	}

	/**
	 * Returns an ordered range of all the site navigation menus where groupId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching site navigation menus
	 */
	@Override
	public List<SiteNavigationMenu> findByG_LikeN(
		long groupId, String name, int start, int end,
		OrderByComparator<SiteNavigationMenu> orderByComparator) {

		return findByG_LikeN(
			groupId, name, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the site navigation menus where groupId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching site navigation menus
	 */
	@Override
	public List<SiteNavigationMenu> findByG_LikeN(
		long groupId, String name, int start, int end,
		OrderByComparator<SiteNavigationMenu> orderByComparator,
		boolean useFinderCache) {

		name = Objects.toString(name, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByG_LikeN;
		finderArgs = new Object[] {
			groupId, name, start, end, orderByComparator
		};

		List<SiteNavigationMenu> list = null;

		if (useFinderCache) {
			list = (List<SiteNavigationMenu>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SiteNavigationMenu siteNavigationMenu : list) {
					if ((groupId != siteNavigationMenu.getGroupId()) ||
						!StringUtil.wildcardMatches(
							siteNavigationMenu.getName(), name, '_', '%', '\\',
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

			sb.append(_SQL_SELECT_SITENAVIGATIONMENU_WHERE);

			sb.append(_FINDER_COLUMN_G_LIKEN_GROUPID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_LIKEN_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_G_LIKEN_NAME_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(SiteNavigationMenuModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindName) {
					queryPos.add(name);
				}

				list = (List<SiteNavigationMenu>)QueryUtil.list(
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
	 * Returns the first site navigation menu in the ordered set where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching site navigation menu
	 * @throws NoSuchMenuException if a matching site navigation menu could not be found
	 */
	@Override
	public SiteNavigationMenu findByG_LikeN_First(
			long groupId, String name,
			OrderByComparator<SiteNavigationMenu> orderByComparator)
		throws NoSuchMenuException {

		SiteNavigationMenu siteNavigationMenu = fetchByG_LikeN_First(
			groupId, name, orderByComparator);

		if (siteNavigationMenu != null) {
			return siteNavigationMenu;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", nameLIKE");
		sb.append(name);

		sb.append("}");

		throw new NoSuchMenuException(sb.toString());
	}

	/**
	 * Returns the first site navigation menu in the ordered set where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching site navigation menu, or <code>null</code> if a matching site navigation menu could not be found
	 */
	@Override
	public SiteNavigationMenu fetchByG_LikeN_First(
		long groupId, String name,
		OrderByComparator<SiteNavigationMenu> orderByComparator) {

		List<SiteNavigationMenu> list = findByG_LikeN(
			groupId, name, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last site navigation menu in the ordered set where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching site navigation menu
	 * @throws NoSuchMenuException if a matching site navigation menu could not be found
	 */
	@Override
	public SiteNavigationMenu findByG_LikeN_Last(
			long groupId, String name,
			OrderByComparator<SiteNavigationMenu> orderByComparator)
		throws NoSuchMenuException {

		SiteNavigationMenu siteNavigationMenu = fetchByG_LikeN_Last(
			groupId, name, orderByComparator);

		if (siteNavigationMenu != null) {
			return siteNavigationMenu;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", nameLIKE");
		sb.append(name);

		sb.append("}");

		throw new NoSuchMenuException(sb.toString());
	}

	/**
	 * Returns the last site navigation menu in the ordered set where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching site navigation menu, or <code>null</code> if a matching site navigation menu could not be found
	 */
	@Override
	public SiteNavigationMenu fetchByG_LikeN_Last(
		long groupId, String name,
		OrderByComparator<SiteNavigationMenu> orderByComparator) {

		int count = countByG_LikeN(groupId, name);

		if (count == 0) {
			return null;
		}

		List<SiteNavigationMenu> list = findByG_LikeN(
			groupId, name, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the site navigation menus before and after the current site navigation menu in the ordered set where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param siteNavigationMenuId the primary key of the current site navigation menu
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next site navigation menu
	 * @throws NoSuchMenuException if a site navigation menu with the primary key could not be found
	 */
	@Override
	public SiteNavigationMenu[] findByG_LikeN_PrevAndNext(
			long siteNavigationMenuId, long groupId, String name,
			OrderByComparator<SiteNavigationMenu> orderByComparator)
		throws NoSuchMenuException {

		name = Objects.toString(name, "");

		SiteNavigationMenu siteNavigationMenu = findByPrimaryKey(
			siteNavigationMenuId);

		Session session = null;

		try {
			session = openSession();

			SiteNavigationMenu[] array = new SiteNavigationMenuImpl[3];

			array[0] = getByG_LikeN_PrevAndNext(
				session, siteNavigationMenu, groupId, name, orderByComparator,
				true);

			array[1] = siteNavigationMenu;

			array[2] = getByG_LikeN_PrevAndNext(
				session, siteNavigationMenu, groupId, name, orderByComparator,
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

	protected SiteNavigationMenu getByG_LikeN_PrevAndNext(
		Session session, SiteNavigationMenu siteNavigationMenu, long groupId,
		String name, OrderByComparator<SiteNavigationMenu> orderByComparator,
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

		sb.append(_SQL_SELECT_SITENAVIGATIONMENU_WHERE);

		sb.append(_FINDER_COLUMN_G_LIKEN_GROUPID_2);

		boolean bindName = false;

		if (name.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_LIKEN_NAME_3);
		}
		else {
			bindName = true;

			sb.append(_FINDER_COLUMN_G_LIKEN_NAME_2);
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
			sb.append(SiteNavigationMenuModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		if (bindName) {
			queryPos.add(name);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						siteNavigationMenu)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SiteNavigationMenu> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the site navigation menus that the user has permission to view where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the matching site navigation menus that the user has permission to view
	 */
	@Override
	public List<SiteNavigationMenu> filterFindByG_LikeN(
		long groupId, String name) {

		return filterFindByG_LikeN(
			groupId, name, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the site navigation menus that the user has permission to view where groupId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @return the range of matching site navigation menus that the user has permission to view
	 */
	@Override
	public List<SiteNavigationMenu> filterFindByG_LikeN(
		long groupId, String name, int start, int end) {

		return filterFindByG_LikeN(groupId, name, start, end, null);
	}

	/**
	 * Returns an ordered range of all the site navigation menus that the user has permissions to view where groupId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching site navigation menus that the user has permission to view
	 */
	@Override
	public List<SiteNavigationMenu> filterFindByG_LikeN(
		long groupId, String name, int start, int end,
		OrderByComparator<SiteNavigationMenu> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_LikeN(groupId, name, start, end, orderByComparator);
		}

		name = Objects.toString(name, "");

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_SITENAVIGATIONMENU_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_SITENAVIGATIONMENU_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_LIKEN_GROUPID_2);

		boolean bindName = false;

		if (name.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_LIKEN_NAME_3);
		}
		else {
			bindName = true;

			sb.append(_FINDER_COLUMN_G_LIKEN_NAME_2);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_SITENAVIGATIONMENU_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(SiteNavigationMenuModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(SiteNavigationMenuModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), SiteNavigationMenu.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, SiteNavigationMenuImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, SiteNavigationMenuImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			if (bindName) {
				queryPos.add(name);
			}

			return (List<SiteNavigationMenu>)QueryUtil.list(
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
	 * Returns the site navigation menus before and after the current site navigation menu in the ordered set of site navigation menus that the user has permission to view where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param siteNavigationMenuId the primary key of the current site navigation menu
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next site navigation menu
	 * @throws NoSuchMenuException if a site navigation menu with the primary key could not be found
	 */
	@Override
	public SiteNavigationMenu[] filterFindByG_LikeN_PrevAndNext(
			long siteNavigationMenuId, long groupId, String name,
			OrderByComparator<SiteNavigationMenu> orderByComparator)
		throws NoSuchMenuException {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_LikeN_PrevAndNext(
				siteNavigationMenuId, groupId, name, orderByComparator);
		}

		name = Objects.toString(name, "");

		SiteNavigationMenu siteNavigationMenu = findByPrimaryKey(
			siteNavigationMenuId);

		Session session = null;

		try {
			session = openSession();

			SiteNavigationMenu[] array = new SiteNavigationMenuImpl[3];

			array[0] = filterGetByG_LikeN_PrevAndNext(
				session, siteNavigationMenu, groupId, name, orderByComparator,
				true);

			array[1] = siteNavigationMenu;

			array[2] = filterGetByG_LikeN_PrevAndNext(
				session, siteNavigationMenu, groupId, name, orderByComparator,
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

	protected SiteNavigationMenu filterGetByG_LikeN_PrevAndNext(
		Session session, SiteNavigationMenu siteNavigationMenu, long groupId,
		String name, OrderByComparator<SiteNavigationMenu> orderByComparator,
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
			sb.append(_FILTER_SQL_SELECT_SITENAVIGATIONMENU_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_SITENAVIGATIONMENU_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_LIKEN_GROUPID_2);

		boolean bindName = false;

		if (name.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_LIKEN_NAME_3);
		}
		else {
			bindName = true;

			sb.append(_FINDER_COLUMN_G_LIKEN_NAME_2);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_SITENAVIGATIONMENU_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(SiteNavigationMenuModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(SiteNavigationMenuModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), SiteNavigationMenu.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(
				_FILTER_ENTITY_ALIAS, SiteNavigationMenuImpl.class);
		}
		else {
			sqlQuery.addEntity(
				_FILTER_ENTITY_TABLE, SiteNavigationMenuImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(groupId);

		if (bindName) {
			queryPos.add(name);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						siteNavigationMenu)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SiteNavigationMenu> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the site navigation menus that the user has permission to view where groupId = any &#63; and name LIKE &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param name the name
	 * @return the matching site navigation menus that the user has permission to view
	 */
	@Override
	public List<SiteNavigationMenu> filterFindByG_LikeN(
		long[] groupIds, String name) {

		return filterFindByG_LikeN(
			groupIds, name, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the site navigation menus that the user has permission to view where groupId = any &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param name the name
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @return the range of matching site navigation menus that the user has permission to view
	 */
	@Override
	public List<SiteNavigationMenu> filterFindByG_LikeN(
		long[] groupIds, String name, int start, int end) {

		return filterFindByG_LikeN(groupIds, name, start, end, null);
	}

	/**
	 * Returns an ordered range of all the site navigation menus that the user has permission to view where groupId = any &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param name the name
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching site navigation menus that the user has permission to view
	 */
	@Override
	public List<SiteNavigationMenu> filterFindByG_LikeN(
		long[] groupIds, String name, int start, int end,
		OrderByComparator<SiteNavigationMenu> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupIds)) {
			return findByG_LikeN(groupIds, name, start, end, orderByComparator);
		}

		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.sortedUnique(groupIds);
		}

		name = Objects.toString(name, "");

		StringBundler sb = new StringBundler();

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_SITENAVIGATIONMENU_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_SITENAVIGATIONMENU_NO_INLINE_DISTINCT_WHERE_1);
		}

		if (groupIds.length > 0) {
			sb.append("(");

			sb.append(_FINDER_COLUMN_G_LIKEN_GROUPID_7);

			sb.append(StringUtil.merge(groupIds));

			sb.append(")");

			sb.append(")");

			sb.append(WHERE_AND);
		}

		boolean bindName = false;

		if (name.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_LIKEN_NAME_3);
		}
		else {
			bindName = true;

			sb.append(_FINDER_COLUMN_G_LIKEN_NAME_2);
		}

		sb.setStringAt(
			removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_SITENAVIGATIONMENU_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(SiteNavigationMenuModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(SiteNavigationMenuModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), SiteNavigationMenu.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupIds);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, SiteNavigationMenuImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, SiteNavigationMenuImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			if (bindName) {
				queryPos.add(name);
			}

			return (List<SiteNavigationMenu>)QueryUtil.list(
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
	 * Returns all the site navigation menus where groupId = any &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param name the name
	 * @return the matching site navigation menus
	 */
	@Override
	public List<SiteNavigationMenu> findByG_LikeN(
		long[] groupIds, String name) {

		return findByG_LikeN(
			groupIds, name, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the site navigation menus where groupId = any &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param name the name
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @return the range of matching site navigation menus
	 */
	@Override
	public List<SiteNavigationMenu> findByG_LikeN(
		long[] groupIds, String name, int start, int end) {

		return findByG_LikeN(groupIds, name, start, end, null);
	}

	/**
	 * Returns an ordered range of all the site navigation menus where groupId = any &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param name the name
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching site navigation menus
	 */
	@Override
	public List<SiteNavigationMenu> findByG_LikeN(
		long[] groupIds, String name, int start, int end,
		OrderByComparator<SiteNavigationMenu> orderByComparator) {

		return findByG_LikeN(
			groupIds, name, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the site navigation menus where groupId = &#63; and name LIKE &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching site navigation menus
	 */
	@Override
	public List<SiteNavigationMenu> findByG_LikeN(
		long[] groupIds, String name, int start, int end,
		OrderByComparator<SiteNavigationMenu> orderByComparator,
		boolean useFinderCache) {

		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.sortedUnique(groupIds);
		}

		name = Objects.toString(name, "");

		if (groupIds.length == 1) {
			return findByG_LikeN(
				groupIds[0], name, start, end, orderByComparator);
		}

		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderArgs = new Object[] {StringUtil.merge(groupIds), name};
			}
		}
		else if (useFinderCache) {
			finderArgs = new Object[] {
				StringUtil.merge(groupIds), name, start, end, orderByComparator
			};
		}

		List<SiteNavigationMenu> list = null;

		if (useFinderCache) {
			list = (List<SiteNavigationMenu>)finderCache.getResult(
				_finderPathWithPaginationFindByG_LikeN, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SiteNavigationMenu siteNavigationMenu : list) {
					if (!ArrayUtil.contains(
							groupIds, siteNavigationMenu.getGroupId()) ||
						!StringUtil.wildcardMatches(
							siteNavigationMenu.getName(), name, '_', '%', '\\',
							true)) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_SELECT_SITENAVIGATIONMENU_WHERE);

			if (groupIds.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_G_LIKEN_GROUPID_7);

				sb.append(StringUtil.merge(groupIds));

				sb.append(")");

				sb.append(")");

				sb.append(WHERE_AND);
			}

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_LIKEN_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_G_LIKEN_NAME_2);
			}

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(SiteNavigationMenuModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindName) {
					queryPos.add(name);
				}

				list = (List<SiteNavigationMenu>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(
						_finderPathWithPaginationFindByG_LikeN, finderArgs,
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
	 * Removes all the site navigation menus where groupId = &#63; and name LIKE &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 */
	@Override
	public void removeByG_LikeN(long groupId, String name) {
		for (SiteNavigationMenu siteNavigationMenu :
				findByG_LikeN(
					groupId, name, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(siteNavigationMenu);
		}
	}

	/**
	 * Returns the number of site navigation menus where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the number of matching site navigation menus
	 */
	@Override
	public int countByG_LikeN(long groupId, String name) {
		name = Objects.toString(name, "");

		FinderPath finderPath = _finderPathWithPaginationCountByG_LikeN;

		Object[] finderArgs = new Object[] {groupId, name};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_SITENAVIGATIONMENU_WHERE);

			sb.append(_FINDER_COLUMN_G_LIKEN_GROUPID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_LIKEN_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_G_LIKEN_NAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindName) {
					queryPos.add(name);
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

	/**
	 * Returns the number of site navigation menus where groupId = any &#63; and name LIKE &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param name the name
	 * @return the number of matching site navigation menus
	 */
	@Override
	public int countByG_LikeN(long[] groupIds, String name) {
		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.sortedUnique(groupIds);
		}

		name = Objects.toString(name, "");

		Object[] finderArgs = new Object[] {StringUtil.merge(groupIds), name};

		Long count = (Long)finderCache.getResult(
			_finderPathWithPaginationCountByG_LikeN, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_COUNT_SITENAVIGATIONMENU_WHERE);

			if (groupIds.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_G_LIKEN_GROUPID_7);

				sb.append(StringUtil.merge(groupIds));

				sb.append(")");

				sb.append(")");

				sb.append(WHERE_AND);
			}

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_LIKEN_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_G_LIKEN_NAME_2);
			}

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindName) {
					queryPos.add(name);
				}

				count = (Long)query.uniqueResult();

				finderCache.putResult(
					_finderPathWithPaginationCountByG_LikeN, finderArgs, count);
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
	 * Returns the number of site navigation menus that the user has permission to view where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the number of matching site navigation menus that the user has permission to view
	 */
	@Override
	public int filterCountByG_LikeN(long groupId, String name) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_LikeN(groupId, name);
		}

		name = Objects.toString(name, "");

		StringBundler sb = new StringBundler(3);

		sb.append(_FILTER_SQL_COUNT_SITENAVIGATIONMENU_WHERE);

		sb.append(_FINDER_COLUMN_G_LIKEN_GROUPID_2);

		boolean bindName = false;

		if (name.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_LIKEN_NAME_3);
		}
		else {
			bindName = true;

			sb.append(_FINDER_COLUMN_G_LIKEN_NAME_2);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), SiteNavigationMenu.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			if (bindName) {
				queryPos.add(name);
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
	 * Returns the number of site navigation menus that the user has permission to view where groupId = any &#63; and name LIKE &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param name the name
	 * @return the number of matching site navigation menus that the user has permission to view
	 */
	@Override
	public int filterCountByG_LikeN(long[] groupIds, String name) {
		if (!InlineSQLHelperUtil.isEnabled(groupIds)) {
			return countByG_LikeN(groupIds, name);
		}

		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.sortedUnique(groupIds);
		}

		name = Objects.toString(name, "");

		StringBundler sb = new StringBundler();

		sb.append(_FILTER_SQL_COUNT_SITENAVIGATIONMENU_WHERE);

		if (groupIds.length > 0) {
			sb.append("(");

			sb.append(_FINDER_COLUMN_G_LIKEN_GROUPID_7);

			sb.append(StringUtil.merge(groupIds));

			sb.append(")");

			sb.append(")");

			sb.append(WHERE_AND);
		}

		boolean bindName = false;

		if (name.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_LIKEN_NAME_3);
		}
		else {
			bindName = true;

			sb.append(_FINDER_COLUMN_G_LIKEN_NAME_2);
		}

		sb.setStringAt(
			removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), SiteNavigationMenu.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupIds);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			if (bindName) {
				queryPos.add(name);
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

	private static final String _FINDER_COLUMN_G_LIKEN_GROUPID_2 =
		"siteNavigationMenu.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_LIKEN_GROUPID_7 =
		"siteNavigationMenu.groupId IN (";

	private static final String _FINDER_COLUMN_G_LIKEN_NAME_2 =
		"siteNavigationMenu.name LIKE ?";

	private static final String _FINDER_COLUMN_G_LIKEN_NAME_3 =
		"(siteNavigationMenu.name IS NULL OR siteNavigationMenu.name LIKE '')";

	private FinderPath _finderPathWithPaginationFindByG_T;
	private FinderPath _finderPathWithoutPaginationFindByG_T;
	private FinderPath _finderPathCountByG_T;

	/**
	 * Returns all the site navigation menus where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @return the matching site navigation menus
	 */
	@Override
	public List<SiteNavigationMenu> findByG_T(long groupId, int type) {
		return findByG_T(
			groupId, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the site navigation menus where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @return the range of matching site navigation menus
	 */
	@Override
	public List<SiteNavigationMenu> findByG_T(
		long groupId, int type, int start, int end) {

		return findByG_T(groupId, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the site navigation menus where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching site navigation menus
	 */
	@Override
	public List<SiteNavigationMenu> findByG_T(
		long groupId, int type, int start, int end,
		OrderByComparator<SiteNavigationMenu> orderByComparator) {

		return findByG_T(groupId, type, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the site navigation menus where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching site navigation menus
	 */
	@Override
	public List<SiteNavigationMenu> findByG_T(
		long groupId, int type, int start, int end,
		OrderByComparator<SiteNavigationMenu> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByG_T;
				finderArgs = new Object[] {groupId, type};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByG_T;
			finderArgs = new Object[] {
				groupId, type, start, end, orderByComparator
			};
		}

		List<SiteNavigationMenu> list = null;

		if (useFinderCache) {
			list = (List<SiteNavigationMenu>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SiteNavigationMenu siteNavigationMenu : list) {
					if ((groupId != siteNavigationMenu.getGroupId()) ||
						(type != siteNavigationMenu.getType())) {

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

			sb.append(_SQL_SELECT_SITENAVIGATIONMENU_WHERE);

			sb.append(_FINDER_COLUMN_G_T_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_T_TYPE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(SiteNavigationMenuModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(type);

				list = (List<SiteNavigationMenu>)QueryUtil.list(
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
	 * Returns the first site navigation menu in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching site navigation menu
	 * @throws NoSuchMenuException if a matching site navigation menu could not be found
	 */
	@Override
	public SiteNavigationMenu findByG_T_First(
			long groupId, int type,
			OrderByComparator<SiteNavigationMenu> orderByComparator)
		throws NoSuchMenuException {

		SiteNavigationMenu siteNavigationMenu = fetchByG_T_First(
			groupId, type, orderByComparator);

		if (siteNavigationMenu != null) {
			return siteNavigationMenu;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchMenuException(sb.toString());
	}

	/**
	 * Returns the first site navigation menu in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching site navigation menu, or <code>null</code> if a matching site navigation menu could not be found
	 */
	@Override
	public SiteNavigationMenu fetchByG_T_First(
		long groupId, int type,
		OrderByComparator<SiteNavigationMenu> orderByComparator) {

		List<SiteNavigationMenu> list = findByG_T(
			groupId, type, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last site navigation menu in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching site navigation menu
	 * @throws NoSuchMenuException if a matching site navigation menu could not be found
	 */
	@Override
	public SiteNavigationMenu findByG_T_Last(
			long groupId, int type,
			OrderByComparator<SiteNavigationMenu> orderByComparator)
		throws NoSuchMenuException {

		SiteNavigationMenu siteNavigationMenu = fetchByG_T_Last(
			groupId, type, orderByComparator);

		if (siteNavigationMenu != null) {
			return siteNavigationMenu;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchMenuException(sb.toString());
	}

	/**
	 * Returns the last site navigation menu in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching site navigation menu, or <code>null</code> if a matching site navigation menu could not be found
	 */
	@Override
	public SiteNavigationMenu fetchByG_T_Last(
		long groupId, int type,
		OrderByComparator<SiteNavigationMenu> orderByComparator) {

		int count = countByG_T(groupId, type);

		if (count == 0) {
			return null;
		}

		List<SiteNavigationMenu> list = findByG_T(
			groupId, type, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the site navigation menus before and after the current site navigation menu in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param siteNavigationMenuId the primary key of the current site navigation menu
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next site navigation menu
	 * @throws NoSuchMenuException if a site navigation menu with the primary key could not be found
	 */
	@Override
	public SiteNavigationMenu[] findByG_T_PrevAndNext(
			long siteNavigationMenuId, long groupId, int type,
			OrderByComparator<SiteNavigationMenu> orderByComparator)
		throws NoSuchMenuException {

		SiteNavigationMenu siteNavigationMenu = findByPrimaryKey(
			siteNavigationMenuId);

		Session session = null;

		try {
			session = openSession();

			SiteNavigationMenu[] array = new SiteNavigationMenuImpl[3];

			array[0] = getByG_T_PrevAndNext(
				session, siteNavigationMenu, groupId, type, orderByComparator,
				true);

			array[1] = siteNavigationMenu;

			array[2] = getByG_T_PrevAndNext(
				session, siteNavigationMenu, groupId, type, orderByComparator,
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

	protected SiteNavigationMenu getByG_T_PrevAndNext(
		Session session, SiteNavigationMenu siteNavigationMenu, long groupId,
		int type, OrderByComparator<SiteNavigationMenu> orderByComparator,
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

		sb.append(_SQL_SELECT_SITENAVIGATIONMENU_WHERE);

		sb.append(_FINDER_COLUMN_G_T_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_T_TYPE_2);

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
			sb.append(SiteNavigationMenuModelImpl.ORDER_BY_JPQL);
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
						siteNavigationMenu)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SiteNavigationMenu> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the site navigation menus that the user has permission to view where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @return the matching site navigation menus that the user has permission to view
	 */
	@Override
	public List<SiteNavigationMenu> filterFindByG_T(long groupId, int type) {
		return filterFindByG_T(
			groupId, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the site navigation menus that the user has permission to view where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @return the range of matching site navigation menus that the user has permission to view
	 */
	@Override
	public List<SiteNavigationMenu> filterFindByG_T(
		long groupId, int type, int start, int end) {

		return filterFindByG_T(groupId, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the site navigation menus that the user has permissions to view where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching site navigation menus that the user has permission to view
	 */
	@Override
	public List<SiteNavigationMenu> filterFindByG_T(
		long groupId, int type, int start, int end,
		OrderByComparator<SiteNavigationMenu> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_T(groupId, type, start, end, orderByComparator);
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
			sb.append(_FILTER_SQL_SELECT_SITENAVIGATIONMENU_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_SITENAVIGATIONMENU_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_T_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_T_TYPE_2_SQL);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_SITENAVIGATIONMENU_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(SiteNavigationMenuModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(SiteNavigationMenuModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), SiteNavigationMenu.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, SiteNavigationMenuImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, SiteNavigationMenuImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(type);

			return (List<SiteNavigationMenu>)QueryUtil.list(
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
	 * Returns the site navigation menus before and after the current site navigation menu in the ordered set of site navigation menus that the user has permission to view where groupId = &#63; and type = &#63;.
	 *
	 * @param siteNavigationMenuId the primary key of the current site navigation menu
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next site navigation menu
	 * @throws NoSuchMenuException if a site navigation menu with the primary key could not be found
	 */
	@Override
	public SiteNavigationMenu[] filterFindByG_T_PrevAndNext(
			long siteNavigationMenuId, long groupId, int type,
			OrderByComparator<SiteNavigationMenu> orderByComparator)
		throws NoSuchMenuException {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_T_PrevAndNext(
				siteNavigationMenuId, groupId, type, orderByComparator);
		}

		SiteNavigationMenu siteNavigationMenu = findByPrimaryKey(
			siteNavigationMenuId);

		Session session = null;

		try {
			session = openSession();

			SiteNavigationMenu[] array = new SiteNavigationMenuImpl[3];

			array[0] = filterGetByG_T_PrevAndNext(
				session, siteNavigationMenu, groupId, type, orderByComparator,
				true);

			array[1] = siteNavigationMenu;

			array[2] = filterGetByG_T_PrevAndNext(
				session, siteNavigationMenu, groupId, type, orderByComparator,
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

	protected SiteNavigationMenu filterGetByG_T_PrevAndNext(
		Session session, SiteNavigationMenu siteNavigationMenu, long groupId,
		int type, OrderByComparator<SiteNavigationMenu> orderByComparator,
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
			sb.append(_FILTER_SQL_SELECT_SITENAVIGATIONMENU_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_SITENAVIGATIONMENU_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_T_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_T_TYPE_2_SQL);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_SITENAVIGATIONMENU_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(SiteNavigationMenuModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(SiteNavigationMenuModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), SiteNavigationMenu.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(
				_FILTER_ENTITY_ALIAS, SiteNavigationMenuImpl.class);
		}
		else {
			sqlQuery.addEntity(
				_FILTER_ENTITY_TABLE, SiteNavigationMenuImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(groupId);

		queryPos.add(type);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						siteNavigationMenu)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SiteNavigationMenu> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the site navigation menus where groupId = &#63; and type = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 */
	@Override
	public void removeByG_T(long groupId, int type) {
		for (SiteNavigationMenu siteNavigationMenu :
				findByG_T(
					groupId, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(siteNavigationMenu);
		}
	}

	/**
	 * Returns the number of site navigation menus where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @return the number of matching site navigation menus
	 */
	@Override
	public int countByG_T(long groupId, int type) {
		FinderPath finderPath = _finderPathCountByG_T;

		Object[] finderArgs = new Object[] {groupId, type};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_SITENAVIGATIONMENU_WHERE);

			sb.append(_FINDER_COLUMN_G_T_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_T_TYPE_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(type);

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

	/**
	 * Returns the number of site navigation menus that the user has permission to view where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @return the number of matching site navigation menus that the user has permission to view
	 */
	@Override
	public int filterCountByG_T(long groupId, int type) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_T(groupId, type);
		}

		StringBundler sb = new StringBundler(3);

		sb.append(_FILTER_SQL_COUNT_SITENAVIGATIONMENU_WHERE);

		sb.append(_FINDER_COLUMN_G_T_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_T_TYPE_2_SQL);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), SiteNavigationMenu.class.getName(),
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

	private static final String _FINDER_COLUMN_G_T_GROUPID_2 =
		"siteNavigationMenu.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_T_TYPE_2 =
		"siteNavigationMenu.type = ?";

	private static final String _FINDER_COLUMN_G_T_TYPE_2_SQL =
		"siteNavigationMenu.type_ = ?";

	private FinderPath _finderPathWithPaginationFindByG_A;
	private FinderPath _finderPathWithoutPaginationFindByG_A;
	private FinderPath _finderPathCountByG_A;

	/**
	 * Returns all the site navigation menus where groupId = &#63; and auto = &#63;.
	 *
	 * @param groupId the group ID
	 * @param auto the auto
	 * @return the matching site navigation menus
	 */
	@Override
	public List<SiteNavigationMenu> findByG_A(long groupId, boolean auto) {
		return findByG_A(
			groupId, auto, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the site navigation menus where groupId = &#63; and auto = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param auto the auto
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @return the range of matching site navigation menus
	 */
	@Override
	public List<SiteNavigationMenu> findByG_A(
		long groupId, boolean auto, int start, int end) {

		return findByG_A(groupId, auto, start, end, null);
	}

	/**
	 * Returns an ordered range of all the site navigation menus where groupId = &#63; and auto = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param auto the auto
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching site navigation menus
	 */
	@Override
	public List<SiteNavigationMenu> findByG_A(
		long groupId, boolean auto, int start, int end,
		OrderByComparator<SiteNavigationMenu> orderByComparator) {

		return findByG_A(groupId, auto, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the site navigation menus where groupId = &#63; and auto = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param auto the auto
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching site navigation menus
	 */
	@Override
	public List<SiteNavigationMenu> findByG_A(
		long groupId, boolean auto, int start, int end,
		OrderByComparator<SiteNavigationMenu> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByG_A;
				finderArgs = new Object[] {groupId, auto};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByG_A;
			finderArgs = new Object[] {
				groupId, auto, start, end, orderByComparator
			};
		}

		List<SiteNavigationMenu> list = null;

		if (useFinderCache) {
			list = (List<SiteNavigationMenu>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SiteNavigationMenu siteNavigationMenu : list) {
					if ((groupId != siteNavigationMenu.getGroupId()) ||
						(auto != siteNavigationMenu.isAuto())) {

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

			sb.append(_SQL_SELECT_SITENAVIGATIONMENU_WHERE);

			sb.append(_FINDER_COLUMN_G_A_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_A_AUTO_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(SiteNavigationMenuModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(auto);

				list = (List<SiteNavigationMenu>)QueryUtil.list(
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
	 * Returns the first site navigation menu in the ordered set where groupId = &#63; and auto = &#63;.
	 *
	 * @param groupId the group ID
	 * @param auto the auto
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching site navigation menu
	 * @throws NoSuchMenuException if a matching site navigation menu could not be found
	 */
	@Override
	public SiteNavigationMenu findByG_A_First(
			long groupId, boolean auto,
			OrderByComparator<SiteNavigationMenu> orderByComparator)
		throws NoSuchMenuException {

		SiteNavigationMenu siteNavigationMenu = fetchByG_A_First(
			groupId, auto, orderByComparator);

		if (siteNavigationMenu != null) {
			return siteNavigationMenu;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", auto=");
		sb.append(auto);

		sb.append("}");

		throw new NoSuchMenuException(sb.toString());
	}

	/**
	 * Returns the first site navigation menu in the ordered set where groupId = &#63; and auto = &#63;.
	 *
	 * @param groupId the group ID
	 * @param auto the auto
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching site navigation menu, or <code>null</code> if a matching site navigation menu could not be found
	 */
	@Override
	public SiteNavigationMenu fetchByG_A_First(
		long groupId, boolean auto,
		OrderByComparator<SiteNavigationMenu> orderByComparator) {

		List<SiteNavigationMenu> list = findByG_A(
			groupId, auto, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last site navigation menu in the ordered set where groupId = &#63; and auto = &#63;.
	 *
	 * @param groupId the group ID
	 * @param auto the auto
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching site navigation menu
	 * @throws NoSuchMenuException if a matching site navigation menu could not be found
	 */
	@Override
	public SiteNavigationMenu findByG_A_Last(
			long groupId, boolean auto,
			OrderByComparator<SiteNavigationMenu> orderByComparator)
		throws NoSuchMenuException {

		SiteNavigationMenu siteNavigationMenu = fetchByG_A_Last(
			groupId, auto, orderByComparator);

		if (siteNavigationMenu != null) {
			return siteNavigationMenu;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", auto=");
		sb.append(auto);

		sb.append("}");

		throw new NoSuchMenuException(sb.toString());
	}

	/**
	 * Returns the last site navigation menu in the ordered set where groupId = &#63; and auto = &#63;.
	 *
	 * @param groupId the group ID
	 * @param auto the auto
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching site navigation menu, or <code>null</code> if a matching site navigation menu could not be found
	 */
	@Override
	public SiteNavigationMenu fetchByG_A_Last(
		long groupId, boolean auto,
		OrderByComparator<SiteNavigationMenu> orderByComparator) {

		int count = countByG_A(groupId, auto);

		if (count == 0) {
			return null;
		}

		List<SiteNavigationMenu> list = findByG_A(
			groupId, auto, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the site navigation menus before and after the current site navigation menu in the ordered set where groupId = &#63; and auto = &#63;.
	 *
	 * @param siteNavigationMenuId the primary key of the current site navigation menu
	 * @param groupId the group ID
	 * @param auto the auto
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next site navigation menu
	 * @throws NoSuchMenuException if a site navigation menu with the primary key could not be found
	 */
	@Override
	public SiteNavigationMenu[] findByG_A_PrevAndNext(
			long siteNavigationMenuId, long groupId, boolean auto,
			OrderByComparator<SiteNavigationMenu> orderByComparator)
		throws NoSuchMenuException {

		SiteNavigationMenu siteNavigationMenu = findByPrimaryKey(
			siteNavigationMenuId);

		Session session = null;

		try {
			session = openSession();

			SiteNavigationMenu[] array = new SiteNavigationMenuImpl[3];

			array[0] = getByG_A_PrevAndNext(
				session, siteNavigationMenu, groupId, auto, orderByComparator,
				true);

			array[1] = siteNavigationMenu;

			array[2] = getByG_A_PrevAndNext(
				session, siteNavigationMenu, groupId, auto, orderByComparator,
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

	protected SiteNavigationMenu getByG_A_PrevAndNext(
		Session session, SiteNavigationMenu siteNavigationMenu, long groupId,
		boolean auto, OrderByComparator<SiteNavigationMenu> orderByComparator,
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

		sb.append(_SQL_SELECT_SITENAVIGATIONMENU_WHERE);

		sb.append(_FINDER_COLUMN_G_A_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_A_AUTO_2);

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
			sb.append(SiteNavigationMenuModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(auto);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						siteNavigationMenu)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SiteNavigationMenu> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the site navigation menus that the user has permission to view where groupId = &#63; and auto = &#63;.
	 *
	 * @param groupId the group ID
	 * @param auto the auto
	 * @return the matching site navigation menus that the user has permission to view
	 */
	@Override
	public List<SiteNavigationMenu> filterFindByG_A(
		long groupId, boolean auto) {

		return filterFindByG_A(
			groupId, auto, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the site navigation menus that the user has permission to view where groupId = &#63; and auto = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param auto the auto
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @return the range of matching site navigation menus that the user has permission to view
	 */
	@Override
	public List<SiteNavigationMenu> filterFindByG_A(
		long groupId, boolean auto, int start, int end) {

		return filterFindByG_A(groupId, auto, start, end, null);
	}

	/**
	 * Returns an ordered range of all the site navigation menus that the user has permissions to view where groupId = &#63; and auto = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param auto the auto
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching site navigation menus that the user has permission to view
	 */
	@Override
	public List<SiteNavigationMenu> filterFindByG_A(
		long groupId, boolean auto, int start, int end,
		OrderByComparator<SiteNavigationMenu> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_A(groupId, auto, start, end, orderByComparator);
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
			sb.append(_FILTER_SQL_SELECT_SITENAVIGATIONMENU_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_SITENAVIGATIONMENU_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_A_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_A_AUTO_2_SQL);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_SITENAVIGATIONMENU_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(SiteNavigationMenuModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(SiteNavigationMenuModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), SiteNavigationMenu.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, SiteNavigationMenuImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, SiteNavigationMenuImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(auto);

			return (List<SiteNavigationMenu>)QueryUtil.list(
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
	 * Returns the site navigation menus before and after the current site navigation menu in the ordered set of site navigation menus that the user has permission to view where groupId = &#63; and auto = &#63;.
	 *
	 * @param siteNavigationMenuId the primary key of the current site navigation menu
	 * @param groupId the group ID
	 * @param auto the auto
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next site navigation menu
	 * @throws NoSuchMenuException if a site navigation menu with the primary key could not be found
	 */
	@Override
	public SiteNavigationMenu[] filterFindByG_A_PrevAndNext(
			long siteNavigationMenuId, long groupId, boolean auto,
			OrderByComparator<SiteNavigationMenu> orderByComparator)
		throws NoSuchMenuException {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_A_PrevAndNext(
				siteNavigationMenuId, groupId, auto, orderByComparator);
		}

		SiteNavigationMenu siteNavigationMenu = findByPrimaryKey(
			siteNavigationMenuId);

		Session session = null;

		try {
			session = openSession();

			SiteNavigationMenu[] array = new SiteNavigationMenuImpl[3];

			array[0] = filterGetByG_A_PrevAndNext(
				session, siteNavigationMenu, groupId, auto, orderByComparator,
				true);

			array[1] = siteNavigationMenu;

			array[2] = filterGetByG_A_PrevAndNext(
				session, siteNavigationMenu, groupId, auto, orderByComparator,
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

	protected SiteNavigationMenu filterGetByG_A_PrevAndNext(
		Session session, SiteNavigationMenu siteNavigationMenu, long groupId,
		boolean auto, OrderByComparator<SiteNavigationMenu> orderByComparator,
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
			sb.append(_FILTER_SQL_SELECT_SITENAVIGATIONMENU_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_SITENAVIGATIONMENU_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_A_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_A_AUTO_2_SQL);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_SITENAVIGATIONMENU_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(SiteNavigationMenuModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(SiteNavigationMenuModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), SiteNavigationMenu.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(
				_FILTER_ENTITY_ALIAS, SiteNavigationMenuImpl.class);
		}
		else {
			sqlQuery.addEntity(
				_FILTER_ENTITY_TABLE, SiteNavigationMenuImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(groupId);

		queryPos.add(auto);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						siteNavigationMenu)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SiteNavigationMenu> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the site navigation menus where groupId = &#63; and auto = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param auto the auto
	 */
	@Override
	public void removeByG_A(long groupId, boolean auto) {
		for (SiteNavigationMenu siteNavigationMenu :
				findByG_A(
					groupId, auto, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(siteNavigationMenu);
		}
	}

	/**
	 * Returns the number of site navigation menus where groupId = &#63; and auto = &#63;.
	 *
	 * @param groupId the group ID
	 * @param auto the auto
	 * @return the number of matching site navigation menus
	 */
	@Override
	public int countByG_A(long groupId, boolean auto) {
		FinderPath finderPath = _finderPathCountByG_A;

		Object[] finderArgs = new Object[] {groupId, auto};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_SITENAVIGATIONMENU_WHERE);

			sb.append(_FINDER_COLUMN_G_A_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_A_AUTO_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(auto);

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

	/**
	 * Returns the number of site navigation menus that the user has permission to view where groupId = &#63; and auto = &#63;.
	 *
	 * @param groupId the group ID
	 * @param auto the auto
	 * @return the number of matching site navigation menus that the user has permission to view
	 */
	@Override
	public int filterCountByG_A(long groupId, boolean auto) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_A(groupId, auto);
		}

		StringBundler sb = new StringBundler(3);

		sb.append(_FILTER_SQL_COUNT_SITENAVIGATIONMENU_WHERE);

		sb.append(_FINDER_COLUMN_G_A_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_A_AUTO_2_SQL);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), SiteNavigationMenu.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(auto);

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

	private static final String _FINDER_COLUMN_G_A_GROUPID_2 =
		"siteNavigationMenu.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_A_AUTO_2 =
		"siteNavigationMenu.auto = ?";

	private static final String _FINDER_COLUMN_G_A_AUTO_2_SQL =
		"siteNavigationMenu.auto_ = ?";

	public SiteNavigationMenuPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put("type", "type_");
		dbColumnNames.put("auto", "auto_");

		setDBColumnNames(dbColumnNames);

		setModelClass(SiteNavigationMenu.class);

		setModelImplClass(SiteNavigationMenuImpl.class);
		setModelPKClass(long.class);

		setTable(SiteNavigationMenuTable.INSTANCE);
	}

	/**
	 * Caches the site navigation menu in the entity cache if it is enabled.
	 *
	 * @param siteNavigationMenu the site navigation menu
	 */
	@Override
	public void cacheResult(SiteNavigationMenu siteNavigationMenu) {
		entityCache.putResult(
			SiteNavigationMenuImpl.class, siteNavigationMenu.getPrimaryKey(),
			siteNavigationMenu);

		finderCache.putResult(
			_finderPathFetchByUUID_G,
			new Object[] {
				siteNavigationMenu.getUuid(), siteNavigationMenu.getGroupId()
			},
			siteNavigationMenu);

		finderCache.putResult(
			_finderPathFetchByG_N,
			new Object[] {
				siteNavigationMenu.getGroupId(), siteNavigationMenu.getName()
			},
			siteNavigationMenu);
	}

	/**
	 * Caches the site navigation menus in the entity cache if it is enabled.
	 *
	 * @param siteNavigationMenus the site navigation menus
	 */
	@Override
	public void cacheResult(List<SiteNavigationMenu> siteNavigationMenus) {
		for (SiteNavigationMenu siteNavigationMenu : siteNavigationMenus) {
			if (entityCache.getResult(
					SiteNavigationMenuImpl.class,
					siteNavigationMenu.getPrimaryKey()) == null) {

				cacheResult(siteNavigationMenu);
			}
		}
	}

	/**
	 * Clears the cache for all site navigation menus.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(SiteNavigationMenuImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the site navigation menu.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(SiteNavigationMenu siteNavigationMenu) {
		entityCache.removeResult(
			SiteNavigationMenuImpl.class, siteNavigationMenu.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(SiteNavigationMenuModelImpl)siteNavigationMenu, true);
	}

	@Override
	public void clearCache(List<SiteNavigationMenu> siteNavigationMenus) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (SiteNavigationMenu siteNavigationMenu : siteNavigationMenus) {
			entityCache.removeResult(
				SiteNavigationMenuImpl.class,
				siteNavigationMenu.getPrimaryKey());

			clearUniqueFindersCache(
				(SiteNavigationMenuModelImpl)siteNavigationMenu, true);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(SiteNavigationMenuImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		SiteNavigationMenuModelImpl siteNavigationMenuModelImpl) {

		Object[] args = new Object[] {
			siteNavigationMenuModelImpl.getUuid(),
			siteNavigationMenuModelImpl.getGroupId()
		};

		finderCache.putResult(
			_finderPathCountByUUID_G, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByUUID_G, args, siteNavigationMenuModelImpl, false);

		args = new Object[] {
			siteNavigationMenuModelImpl.getGroupId(),
			siteNavigationMenuModelImpl.getName()
		};

		finderCache.putResult(
			_finderPathCountByG_N, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByG_N, args, siteNavigationMenuModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		SiteNavigationMenuModelImpl siteNavigationMenuModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				siteNavigationMenuModelImpl.getUuid(),
				siteNavigationMenuModelImpl.getGroupId()
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(_finderPathFetchByUUID_G, args);
		}

		if ((siteNavigationMenuModelImpl.getColumnBitmask() &
			 _finderPathFetchByUUID_G.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				siteNavigationMenuModelImpl.getColumnOriginalValue("uuid_"),
				siteNavigationMenuModelImpl.getColumnOriginalValue("groupId")
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(_finderPathFetchByUUID_G, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
				siteNavigationMenuModelImpl.getGroupId(),
				siteNavigationMenuModelImpl.getName()
			};

			finderCache.removeResult(_finderPathCountByG_N, args);
			finderCache.removeResult(_finderPathFetchByG_N, args);
		}

		if ((siteNavigationMenuModelImpl.getColumnBitmask() &
			 _finderPathFetchByG_N.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				siteNavigationMenuModelImpl.getColumnOriginalValue("groupId"),
				siteNavigationMenuModelImpl.getColumnOriginalValue("name")
			};

			finderCache.removeResult(_finderPathCountByG_N, args);
			finderCache.removeResult(_finderPathFetchByG_N, args);
		}
	}

	/**
	 * Creates a new site navigation menu with the primary key. Does not add the site navigation menu to the database.
	 *
	 * @param siteNavigationMenuId the primary key for the new site navigation menu
	 * @return the new site navigation menu
	 */
	@Override
	public SiteNavigationMenu create(long siteNavigationMenuId) {
		SiteNavigationMenu siteNavigationMenu = new SiteNavigationMenuImpl();

		siteNavigationMenu.setNew(true);
		siteNavigationMenu.setPrimaryKey(siteNavigationMenuId);

		String uuid = PortalUUIDUtil.generate();

		siteNavigationMenu.setUuid(uuid);

		siteNavigationMenu.setCompanyId(CompanyThreadLocal.getCompanyId());

		return siteNavigationMenu;
	}

	/**
	 * Removes the site navigation menu with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param siteNavigationMenuId the primary key of the site navigation menu
	 * @return the site navigation menu that was removed
	 * @throws NoSuchMenuException if a site navigation menu with the primary key could not be found
	 */
	@Override
	public SiteNavigationMenu remove(long siteNavigationMenuId)
		throws NoSuchMenuException {

		return remove((Serializable)siteNavigationMenuId);
	}

	/**
	 * Removes the site navigation menu with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the site navigation menu
	 * @return the site navigation menu that was removed
	 * @throws NoSuchMenuException if a site navigation menu with the primary key could not be found
	 */
	@Override
	public SiteNavigationMenu remove(Serializable primaryKey)
		throws NoSuchMenuException {

		Session session = null;

		try {
			session = openSession();

			SiteNavigationMenu siteNavigationMenu =
				(SiteNavigationMenu)session.get(
					SiteNavigationMenuImpl.class, primaryKey);

			if (siteNavigationMenu == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchMenuException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(siteNavigationMenu);
		}
		catch (NoSuchMenuException noSuchEntityException) {
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
	protected SiteNavigationMenu removeImpl(
		SiteNavigationMenu siteNavigationMenu) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(siteNavigationMenu)) {
				siteNavigationMenu = (SiteNavigationMenu)session.get(
					SiteNavigationMenuImpl.class,
					siteNavigationMenu.getPrimaryKeyObj());
			}

			if (siteNavigationMenu != null) {
				session.delete(siteNavigationMenu);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (siteNavigationMenu != null) {
			clearCache(siteNavigationMenu);
		}

		return siteNavigationMenu;
	}

	@Override
	public SiteNavigationMenu updateImpl(
		SiteNavigationMenu siteNavigationMenu) {

		boolean isNew = siteNavigationMenu.isNew();

		if (!(siteNavigationMenu instanceof SiteNavigationMenuModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(siteNavigationMenu.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					siteNavigationMenu);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in siteNavigationMenu proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom SiteNavigationMenu implementation " +
					siteNavigationMenu.getClass());
		}

		SiteNavigationMenuModelImpl siteNavigationMenuModelImpl =
			(SiteNavigationMenuModelImpl)siteNavigationMenu;

		if (Validator.isNull(siteNavigationMenu.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			siteNavigationMenu.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (siteNavigationMenu.getCreateDate() == null)) {
			if (serviceContext == null) {
				siteNavigationMenu.setCreateDate(now);
			}
			else {
				siteNavigationMenu.setCreateDate(
					serviceContext.getCreateDate(now));
			}
		}

		if (!siteNavigationMenuModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				siteNavigationMenu.setModifiedDate(now);
			}
			else {
				siteNavigationMenu.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (siteNavigationMenu.isNew()) {
				session.save(siteNavigationMenu);

				siteNavigationMenu.setNew(false);
			}
			else {
				siteNavigationMenu = (SiteNavigationMenu)session.merge(
					siteNavigationMenu);
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
			Object[] args = new Object[] {
				siteNavigationMenuModelImpl.getUuid()
			};

			finderCache.removeResult(_finderPathCountByUuid, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid, args);

			args = new Object[] {
				siteNavigationMenuModelImpl.getUuid(),
				siteNavigationMenuModelImpl.getCompanyId()
			};

			finderCache.removeResult(_finderPathCountByUuid_C, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid_C, args);

			args = new Object[] {siteNavigationMenuModelImpl.getGroupId()};

			finderCache.removeResult(_finderPathCountByGroupId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByGroupId, args);

			args = new Object[] {
				siteNavigationMenuModelImpl.getGroupId(),
				siteNavigationMenuModelImpl.getType()
			};

			finderCache.removeResult(_finderPathCountByG_T, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByG_T, args);

			args = new Object[] {
				siteNavigationMenuModelImpl.getGroupId(),
				siteNavigationMenuModelImpl.isAuto()
			};

			finderCache.removeResult(_finderPathCountByG_A, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByG_A, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((siteNavigationMenuModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					siteNavigationMenuModelImpl.getColumnOriginalValue("uuid_")
				};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);

				args = new Object[] {siteNavigationMenuModelImpl.getUuid()};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);
			}

			if ((siteNavigationMenuModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					siteNavigationMenuModelImpl.getColumnOriginalValue("uuid_"),
					siteNavigationMenuModelImpl.getColumnOriginalValue(
						"companyId")
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);

				args = new Object[] {
					siteNavigationMenuModelImpl.getUuid(),
					siteNavigationMenuModelImpl.getCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);
			}

			if ((siteNavigationMenuModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByGroupId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					siteNavigationMenuModelImpl.getColumnOriginalValue(
						"groupId")
				};

				finderCache.removeResult(_finderPathCountByGroupId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByGroupId, args);

				args = new Object[] {siteNavigationMenuModelImpl.getGroupId()};

				finderCache.removeResult(_finderPathCountByGroupId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByGroupId, args);
			}

			if ((siteNavigationMenuModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_T.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					siteNavigationMenuModelImpl.getColumnOriginalValue(
						"groupId"),
					siteNavigationMenuModelImpl.getColumnOriginalValue("type_")
				};

				finderCache.removeResult(_finderPathCountByG_T, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_T, args);

				args = new Object[] {
					siteNavigationMenuModelImpl.getGroupId(),
					siteNavigationMenuModelImpl.getType()
				};

				finderCache.removeResult(_finderPathCountByG_T, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_T, args);
			}

			if ((siteNavigationMenuModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_A.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					siteNavigationMenuModelImpl.getColumnOriginalValue(
						"groupId"),
					siteNavigationMenuModelImpl.getColumnOriginalValue("auto_")
				};

				finderCache.removeResult(_finderPathCountByG_A, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_A, args);

				args = new Object[] {
					siteNavigationMenuModelImpl.getGroupId(),
					siteNavigationMenuModelImpl.isAuto()
				};

				finderCache.removeResult(_finderPathCountByG_A, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_A, args);
			}
		}

		entityCache.putResult(
			SiteNavigationMenuImpl.class, siteNavigationMenu.getPrimaryKey(),
			siteNavigationMenu, false);

		clearUniqueFindersCache(siteNavigationMenuModelImpl, false);
		cacheUniqueFindersCache(siteNavigationMenuModelImpl);

		siteNavigationMenu.resetOriginalValues();

		return siteNavigationMenu;
	}

	/**
	 * Returns the site navigation menu with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the site navigation menu
	 * @return the site navigation menu
	 * @throws NoSuchMenuException if a site navigation menu with the primary key could not be found
	 */
	@Override
	public SiteNavigationMenu findByPrimaryKey(Serializable primaryKey)
		throws NoSuchMenuException {

		SiteNavigationMenu siteNavigationMenu = fetchByPrimaryKey(primaryKey);

		if (siteNavigationMenu == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchMenuException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return siteNavigationMenu;
	}

	/**
	 * Returns the site navigation menu with the primary key or throws a <code>NoSuchMenuException</code> if it could not be found.
	 *
	 * @param siteNavigationMenuId the primary key of the site navigation menu
	 * @return the site navigation menu
	 * @throws NoSuchMenuException if a site navigation menu with the primary key could not be found
	 */
	@Override
	public SiteNavigationMenu findByPrimaryKey(long siteNavigationMenuId)
		throws NoSuchMenuException {

		return findByPrimaryKey((Serializable)siteNavigationMenuId);
	}

	/**
	 * Returns the site navigation menu with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param siteNavigationMenuId the primary key of the site navigation menu
	 * @return the site navigation menu, or <code>null</code> if a site navigation menu with the primary key could not be found
	 */
	@Override
	public SiteNavigationMenu fetchByPrimaryKey(long siteNavigationMenuId) {
		return fetchByPrimaryKey((Serializable)siteNavigationMenuId);
	}

	/**
	 * Returns all the site navigation menus.
	 *
	 * @return the site navigation menus
	 */
	@Override
	public List<SiteNavigationMenu> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the site navigation menus.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @return the range of site navigation menus
	 */
	@Override
	public List<SiteNavigationMenu> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the site navigation menus.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of site navigation menus
	 */
	@Override
	public List<SiteNavigationMenu> findAll(
		int start, int end,
		OrderByComparator<SiteNavigationMenu> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the site navigation menus.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SiteNavigationMenuModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of site navigation menus
	 * @param end the upper bound of the range of site navigation menus (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of site navigation menus
	 */
	@Override
	public List<SiteNavigationMenu> findAll(
		int start, int end,
		OrderByComparator<SiteNavigationMenu> orderByComparator,
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

		List<SiteNavigationMenu> list = null;

		if (useFinderCache) {
			list = (List<SiteNavigationMenu>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_SITENAVIGATIONMENU);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_SITENAVIGATIONMENU;

				sql = sql.concat(SiteNavigationMenuModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<SiteNavigationMenu>)QueryUtil.list(
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
	 * Removes all the site navigation menus from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (SiteNavigationMenu siteNavigationMenu : findAll()) {
			remove(siteNavigationMenu);
		}
	}

	/**
	 * Returns the number of site navigation menus.
	 *
	 * @return the number of site navigation menus
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(
					_SQL_COUNT_SITENAVIGATIONMENU);

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
		return "siteNavigationMenuId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_SITENAVIGATIONMENU;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return SiteNavigationMenuModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the site navigation menu persistence.
	 */
	@Activate
	public void activate() {
		_finderPathWithPaginationFindAll = new FinderPath(
			SiteNavigationMenuImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			SiteNavigationMenuImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByUuid = new FinderPath(
			SiteNavigationMenuImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			SiteNavigationMenuImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] {String.class.getName()},
			SiteNavigationMenuModelImpl.getColumnBitmask("uuid_"));

		_finderPathCountByUuid = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid", new String[] {String.class.getName()});

		_finderPathFetchByUUID_G = new FinderPath(
			SiteNavigationMenuImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			SiteNavigationMenuModelImpl.getColumnBitmask("uuid_") |
			SiteNavigationMenuModelImpl.getColumnBitmask("groupId"));

		_finderPathCountByUUID_G = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			SiteNavigationMenuImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			SiteNavigationMenuImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			SiteNavigationMenuModelImpl.getColumnBitmask("uuid_") |
			SiteNavigationMenuModelImpl.getColumnBitmask("companyId"));

		_finderPathCountByUuid_C = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByGroupId = new FinderPath(
			SiteNavigationMenuImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByGroupId = new FinderPath(
			SiteNavigationMenuImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] {Long.class.getName()},
			SiteNavigationMenuModelImpl.getColumnBitmask("groupId"));

		_finderPathCountByGroupId = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByGroupId", new String[] {Long.class.getName()});

		_finderPathWithPaginationCountByGroupId = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"countByGroupId", new String[] {Long.class.getName()});

		_finderPathFetchByG_N = new FinderPath(
			SiteNavigationMenuImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByG_N",
			new String[] {Long.class.getName(), String.class.getName()},
			SiteNavigationMenuModelImpl.getColumnBitmask("groupId") |
			SiteNavigationMenuModelImpl.getColumnBitmask("name"));

		_finderPathCountByG_N = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_N",
			new String[] {Long.class.getName(), String.class.getName()});

		_finderPathWithPaginationFindByG_LikeN = new FinderPath(
			SiteNavigationMenuImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_LikeN",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithPaginationCountByG_LikeN = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"countByG_LikeN",
			new String[] {Long.class.getName(), String.class.getName()});

		_finderPathWithPaginationFindByG_T = new FinderPath(
			SiteNavigationMenuImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_T",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_T = new FinderPath(
			SiteNavigationMenuImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_T",
			new String[] {Long.class.getName(), Integer.class.getName()},
			SiteNavigationMenuModelImpl.getColumnBitmask("groupId") |
			SiteNavigationMenuModelImpl.getColumnBitmask("type_"));

		_finderPathCountByG_T = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_T",
			new String[] {Long.class.getName(), Integer.class.getName()});

		_finderPathWithPaginationFindByG_A = new FinderPath(
			SiteNavigationMenuImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_A",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_A = new FinderPath(
			SiteNavigationMenuImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_A",
			new String[] {Long.class.getName(), Boolean.class.getName()},
			SiteNavigationMenuModelImpl.getColumnBitmask("groupId") |
			SiteNavigationMenuModelImpl.getColumnBitmask("auto_"));

		_finderPathCountByG_A = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_A",
			new String[] {Long.class.getName(), Boolean.class.getName()});
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(SiteNavigationMenuImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	@Reference(
		target = SiteNavigationPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = SiteNavigationPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = SiteNavigationPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_SITENAVIGATIONMENU =
		"SELECT siteNavigationMenu FROM SiteNavigationMenu siteNavigationMenu";

	private static final String _SQL_SELECT_SITENAVIGATIONMENU_WHERE =
		"SELECT siteNavigationMenu FROM SiteNavigationMenu siteNavigationMenu WHERE ";

	private static final String _SQL_COUNT_SITENAVIGATIONMENU =
		"SELECT COUNT(siteNavigationMenu) FROM SiteNavigationMenu siteNavigationMenu";

	private static final String _SQL_COUNT_SITENAVIGATIONMENU_WHERE =
		"SELECT COUNT(siteNavigationMenu) FROM SiteNavigationMenu siteNavigationMenu WHERE ";

	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN =
		"siteNavigationMenu.siteNavigationMenuId";

	private static final String _FILTER_SQL_SELECT_SITENAVIGATIONMENU_WHERE =
		"SELECT DISTINCT {siteNavigationMenu.*} FROM SiteNavigationMenu siteNavigationMenu WHERE ";

	private static final String
		_FILTER_SQL_SELECT_SITENAVIGATIONMENU_NO_INLINE_DISTINCT_WHERE_1 =
			"SELECT {SiteNavigationMenu.*} FROM (SELECT DISTINCT siteNavigationMenu.siteNavigationMenuId FROM SiteNavigationMenu siteNavigationMenu WHERE ";

	private static final String
		_FILTER_SQL_SELECT_SITENAVIGATIONMENU_NO_INLINE_DISTINCT_WHERE_2 =
			") TEMP_TABLE INNER JOIN SiteNavigationMenu ON TEMP_TABLE.siteNavigationMenuId = SiteNavigationMenu.siteNavigationMenuId";

	private static final String _FILTER_SQL_COUNT_SITENAVIGATIONMENU_WHERE =
		"SELECT COUNT(DISTINCT siteNavigationMenu.siteNavigationMenuId) AS COUNT_VALUE FROM SiteNavigationMenu siteNavigationMenu WHERE ";

	private static final String _FILTER_ENTITY_ALIAS = "siteNavigationMenu";

	private static final String _FILTER_ENTITY_TABLE = "SiteNavigationMenu";

	private static final String _ORDER_BY_ENTITY_ALIAS = "siteNavigationMenu.";

	private static final String _ORDER_BY_ENTITY_TABLE = "SiteNavigationMenu.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No SiteNavigationMenu exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No SiteNavigationMenu exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		SiteNavigationMenuPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid", "type", "auto"});

	static {
		try {
			Class.forName(SiteNavigationPersistenceConstants.class.getName());
		}
		catch (ClassNotFoundException classNotFoundException) {
			throw new ExceptionInInitializerError(classNotFoundException);
		}
	}

}