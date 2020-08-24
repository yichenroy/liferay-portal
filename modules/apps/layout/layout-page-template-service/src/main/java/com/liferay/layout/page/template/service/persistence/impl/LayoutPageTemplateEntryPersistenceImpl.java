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

package com.liferay.layout.page.template.service.persistence.impl;

import com.liferay.layout.page.template.exception.NoSuchPageTemplateEntryException;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntryTable;
import com.liferay.layout.page.template.model.impl.LayoutPageTemplateEntryImpl;
import com.liferay.layout.page.template.model.impl.LayoutPageTemplateEntryModelImpl;
import com.liferay.layout.page.template.service.persistence.LayoutPageTemplateEntryPersistence;
import com.liferay.layout.page.template.service.persistence.impl.constants.LayoutPersistenceConstants;
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
 * The persistence implementation for the layout page template entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = LayoutPageTemplateEntryPersistence.class)
public class LayoutPageTemplateEntryPersistenceImpl
	extends BasePersistenceImpl<LayoutPageTemplateEntry>
	implements LayoutPageTemplateEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>LayoutPageTemplateEntryUtil</code> to access the layout page template entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		LayoutPageTemplateEntryImpl.class.getName();

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
	 * Returns all the layout page template entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout page template entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @return the range of matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout page template entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout page template entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			LayoutPageTemplateEntry.class);

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

		List<LayoutPageTemplateEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<LayoutPageTemplateEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutPageTemplateEntry layoutPageTemplateEntry : list) {
					if (!uuid.equals(layoutPageTemplateEntry.getUuid())) {
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

			sb.append(_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);

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
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
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

				list = (List<LayoutPageTemplateEntry>)QueryUtil.list(
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
	 * Returns the first layout page template entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry findByUuid_First(
			String uuid,
			OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {

		LayoutPageTemplateEntry layoutPageTemplateEntry = fetchByUuid_First(
			uuid, orderByComparator);

		if (layoutPageTemplateEntry != null) {
			return layoutPageTemplateEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchPageTemplateEntryException(sb.toString());
	}

	/**
	 * Returns the first layout page template entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template entry, or <code>null</code> if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry fetchByUuid_First(
		String uuid,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		List<LayoutPageTemplateEntry> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout page template entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry findByUuid_Last(
			String uuid,
			OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {

		LayoutPageTemplateEntry layoutPageTemplateEntry = fetchByUuid_Last(
			uuid, orderByComparator);

		if (layoutPageTemplateEntry != null) {
			return layoutPageTemplateEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchPageTemplateEntryException(sb.toString());
	}

	/**
	 * Returns the last layout page template entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template entry, or <code>null</code> if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry fetchByUuid_Last(
		String uuid,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<LayoutPageTemplateEntry> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout page template entries before and after the current layout page template entry in the ordered set where uuid = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the primary key of the current layout page template entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a layout page template entry with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateEntry[] findByUuid_PrevAndNext(
			long layoutPageTemplateEntryId, String uuid,
			OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {

		uuid = Objects.toString(uuid, "");

		LayoutPageTemplateEntry layoutPageTemplateEntry = findByPrimaryKey(
			layoutPageTemplateEntryId);

		Session session = null;

		try {
			session = openSession();

			LayoutPageTemplateEntry[] array =
				new LayoutPageTemplateEntryImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, layoutPageTemplateEntry, uuid, orderByComparator,
				true);

			array[1] = layoutPageTemplateEntry;

			array[2] = getByUuid_PrevAndNext(
				session, layoutPageTemplateEntry, uuid, orderByComparator,
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

	protected LayoutPageTemplateEntry getByUuid_PrevAndNext(
		Session session, LayoutPageTemplateEntry layoutPageTemplateEntry,
		String uuid,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);

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
			sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
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
						layoutPageTemplateEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<LayoutPageTemplateEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout page template entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (LayoutPageTemplateEntry layoutPageTemplateEntry :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(layoutPageTemplateEntry);
		}
	}

	/**
	 * Returns the number of layout page template entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching layout page template entries
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			LayoutPageTemplateEntry.class);

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

			sb.append(_SQL_COUNT_LAYOUTPAGETEMPLATEENTRY_WHERE);

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
		"layoutPageTemplateEntry.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(layoutPageTemplateEntry.uuid IS NULL OR layoutPageTemplateEntry.uuid = '')";

	private FinderPath _finderPathFetchByUUID_G;
	private FinderPath _finderPathCountByUUID_G;

	/**
	 * Returns the layout page template entry where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchPageTemplateEntryException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry findByUUID_G(String uuid, long groupId)
		throws NoSuchPageTemplateEntryException {

		LayoutPageTemplateEntry layoutPageTemplateEntry = fetchByUUID_G(
			uuid, groupId);

		if (layoutPageTemplateEntry == null) {
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

			throw new NoSuchPageTemplateEntryException(sb.toString());
		}

		return layoutPageTemplateEntry;
	}

	/**
	 * Returns the layout page template entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching layout page template entry, or <code>null</code> if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the layout page template entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching layout page template entry, or <code>null</code> if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			LayoutPageTemplateEntry.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {uuid, groupId};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(
				_finderPathFetchByUUID_G, finderArgs, this);
		}

		if (result instanceof LayoutPageTemplateEntry) {
			LayoutPageTemplateEntry layoutPageTemplateEntry =
				(LayoutPageTemplateEntry)result;

			if (!Objects.equals(uuid, layoutPageTemplateEntry.getUuid()) ||
				(groupId != layoutPageTemplateEntry.getGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);

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

				List<LayoutPageTemplateEntry> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByUUID_G, finderArgs, list);
					}
				}
				else {
					LayoutPageTemplateEntry layoutPageTemplateEntry = list.get(
						0);

					result = layoutPageTemplateEntry;

					cacheResult(layoutPageTemplateEntry);
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
			return (LayoutPageTemplateEntry)result;
		}
	}

	/**
	 * Removes the layout page template entry where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the layout page template entry that was removed
	 */
	@Override
	public LayoutPageTemplateEntry removeByUUID_G(String uuid, long groupId)
		throws NoSuchPageTemplateEntryException {

		LayoutPageTemplateEntry layoutPageTemplateEntry = findByUUID_G(
			uuid, groupId);

		return remove(layoutPageTemplateEntry);
	}

	/**
	 * Returns the number of layout page template entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching layout page template entries
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			LayoutPageTemplateEntry.class);

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

			sb.append(_SQL_COUNT_LAYOUTPAGETEMPLATEENTRY_WHERE);

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
		"layoutPageTemplateEntry.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(layoutPageTemplateEntry.uuid IS NULL OR layoutPageTemplateEntry.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"layoutPageTemplateEntry.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the layout page template entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout page template entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @return the range of matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout page template entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout page template entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			LayoutPageTemplateEntry.class);

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

		List<LayoutPageTemplateEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<LayoutPageTemplateEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutPageTemplateEntry layoutPageTemplateEntry : list) {
					if (!uuid.equals(layoutPageTemplateEntry.getUuid()) ||
						(companyId != layoutPageTemplateEntry.getCompanyId())) {

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

			sb.append(_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);

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
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
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

				list = (List<LayoutPageTemplateEntry>)QueryUtil.list(
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
	 * Returns the first layout page template entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {

		LayoutPageTemplateEntry layoutPageTemplateEntry = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (layoutPageTemplateEntry != null) {
			return layoutPageTemplateEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchPageTemplateEntryException(sb.toString());
	}

	/**
	 * Returns the first layout page template entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template entry, or <code>null</code> if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		List<LayoutPageTemplateEntry> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout page template entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {

		LayoutPageTemplateEntry layoutPageTemplateEntry = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (layoutPageTemplateEntry != null) {
			return layoutPageTemplateEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchPageTemplateEntryException(sb.toString());
	}

	/**
	 * Returns the last layout page template entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template entry, or <code>null</code> if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<LayoutPageTemplateEntry> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout page template entries before and after the current layout page template entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the primary key of the current layout page template entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a layout page template entry with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateEntry[] findByUuid_C_PrevAndNext(
			long layoutPageTemplateEntryId, String uuid, long companyId,
			OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {

		uuid = Objects.toString(uuid, "");

		LayoutPageTemplateEntry layoutPageTemplateEntry = findByPrimaryKey(
			layoutPageTemplateEntryId);

		Session session = null;

		try {
			session = openSession();

			LayoutPageTemplateEntry[] array =
				new LayoutPageTemplateEntryImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, layoutPageTemplateEntry, uuid, companyId,
				orderByComparator, true);

			array[1] = layoutPageTemplateEntry;

			array[2] = getByUuid_C_PrevAndNext(
				session, layoutPageTemplateEntry, uuid, companyId,
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

	protected LayoutPageTemplateEntry getByUuid_C_PrevAndNext(
		Session session, LayoutPageTemplateEntry layoutPageTemplateEntry,
		String uuid, long companyId,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);

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
			sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
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
						layoutPageTemplateEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<LayoutPageTemplateEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout page template entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (LayoutPageTemplateEntry layoutPageTemplateEntry :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(layoutPageTemplateEntry);
		}
	}

	/**
	 * Returns the number of layout page template entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching layout page template entries
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			LayoutPageTemplateEntry.class);

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

			sb.append(_SQL_COUNT_LAYOUTPAGETEMPLATEENTRY_WHERE);

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
		"layoutPageTemplateEntry.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(layoutPageTemplateEntry.uuid IS NULL OR layoutPageTemplateEntry.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"layoutPageTemplateEntry.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByGroupId;
	private FinderPath _finderPathWithoutPaginationFindByGroupId;
	private FinderPath _finderPathCountByGroupId;

	/**
	 * Returns all the layout page template entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByGroupId(long groupId) {
		return findByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout page template entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @return the range of matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByGroupId(
		long groupId, int start, int end) {

		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout page template entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout page template entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			LayoutPageTemplateEntry.class);

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

		List<LayoutPageTemplateEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<LayoutPageTemplateEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutPageTemplateEntry layoutPageTemplateEntry : list) {
					if (groupId != layoutPageTemplateEntry.getGroupId()) {
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

			sb.append(_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				list = (List<LayoutPageTemplateEntry>)QueryUtil.list(
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
	 * Returns the first layout page template entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry findByGroupId_First(
			long groupId,
			OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {

		LayoutPageTemplateEntry layoutPageTemplateEntry = fetchByGroupId_First(
			groupId, orderByComparator);

		if (layoutPageTemplateEntry != null) {
			return layoutPageTemplateEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchPageTemplateEntryException(sb.toString());
	}

	/**
	 * Returns the first layout page template entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template entry, or <code>null</code> if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry fetchByGroupId_First(
		long groupId,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		List<LayoutPageTemplateEntry> list = findByGroupId(
			groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout page template entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry findByGroupId_Last(
			long groupId,
			OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {

		LayoutPageTemplateEntry layoutPageTemplateEntry = fetchByGroupId_Last(
			groupId, orderByComparator);

		if (layoutPageTemplateEntry != null) {
			return layoutPageTemplateEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchPageTemplateEntryException(sb.toString());
	}

	/**
	 * Returns the last layout page template entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template entry, or <code>null</code> if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry fetchByGroupId_Last(
		long groupId,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<LayoutPageTemplateEntry> list = findByGroupId(
			groupId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout page template entries before and after the current layout page template entry in the ordered set where groupId = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the primary key of the current layout page template entry
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a layout page template entry with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateEntry[] findByGroupId_PrevAndNext(
			long layoutPageTemplateEntryId, long groupId,
			OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {

		LayoutPageTemplateEntry layoutPageTemplateEntry = findByPrimaryKey(
			layoutPageTemplateEntryId);

		Session session = null;

		try {
			session = openSession();

			LayoutPageTemplateEntry[] array =
				new LayoutPageTemplateEntryImpl[3];

			array[0] = getByGroupId_PrevAndNext(
				session, layoutPageTemplateEntry, groupId, orderByComparator,
				true);

			array[1] = layoutPageTemplateEntry;

			array[2] = getByGroupId_PrevAndNext(
				session, layoutPageTemplateEntry, groupId, orderByComparator,
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

	protected LayoutPageTemplateEntry getByGroupId_PrevAndNext(
		Session session, LayoutPageTemplateEntry layoutPageTemplateEntry,
		long groupId,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);

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
			sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
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
						layoutPageTemplateEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<LayoutPageTemplateEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the layout page template entries that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching layout page template entries that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateEntry> filterFindByGroupId(long groupId) {
		return filterFindByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout page template entries that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @return the range of matching layout page template entries that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateEntry> filterFindByGroupId(
		long groupId, int start, int end) {

		return filterFindByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout page template entries that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template entries that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateEntry> filterFindByGroupId(
		long groupId, int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

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
			sb.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), LayoutPageTemplateEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, LayoutPageTemplateEntryImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, LayoutPageTemplateEntryImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			return (List<LayoutPageTemplateEntry>)QueryUtil.list(
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
	 * Returns the layout page template entries before and after the current layout page template entry in the ordered set of layout page template entries that the user has permission to view where groupId = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the primary key of the current layout page template entry
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a layout page template entry with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateEntry[] filterFindByGroupId_PrevAndNext(
			long layoutPageTemplateEntryId, long groupId,
			OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId_PrevAndNext(
				layoutPageTemplateEntryId, groupId, orderByComparator);
		}

		LayoutPageTemplateEntry layoutPageTemplateEntry = findByPrimaryKey(
			layoutPageTemplateEntryId);

		Session session = null;

		try {
			session = openSession();

			LayoutPageTemplateEntry[] array =
				new LayoutPageTemplateEntryImpl[3];

			array[0] = filterGetByGroupId_PrevAndNext(
				session, layoutPageTemplateEntry, groupId, orderByComparator,
				true);

			array[1] = layoutPageTemplateEntry;

			array[2] = filterGetByGroupId_PrevAndNext(
				session, layoutPageTemplateEntry, groupId, orderByComparator,
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

	protected LayoutPageTemplateEntry filterGetByGroupId_PrevAndNext(
		Session session, LayoutPageTemplateEntry layoutPageTemplateEntry,
		long groupId,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator,
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
			sb.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), LayoutPageTemplateEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(
				_FILTER_ENTITY_ALIAS, LayoutPageTemplateEntryImpl.class);
		}
		else {
			sqlQuery.addEntity(
				_FILTER_ENTITY_TABLE, LayoutPageTemplateEntryImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(groupId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutPageTemplateEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<LayoutPageTemplateEntry> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout page template entries where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (LayoutPageTemplateEntry layoutPageTemplateEntry :
				findByGroupId(
					groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(layoutPageTemplateEntry);
		}
	}

	/**
	 * Returns the number of layout page template entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching layout page template entries
	 */
	@Override
	public int countByGroupId(long groupId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			LayoutPageTemplateEntry.class);

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

			sb.append(_SQL_COUNT_LAYOUTPAGETEMPLATEENTRY_WHERE);

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
	 * Returns the number of layout page template entries that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching layout page template entries that the user has permission to view
	 */
	@Override
	public int filterCountByGroupId(long groupId) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByGroupId(groupId);
		}

		StringBundler sb = new StringBundler(2);

		sb.append(_FILTER_SQL_COUNT_LAYOUTPAGETEMPLATEENTRY_WHERE);

		sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), LayoutPageTemplateEntry.class.getName(),
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
		"layoutPageTemplateEntry.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByLayoutPrototypeId;
	private FinderPath _finderPathWithoutPaginationFindByLayoutPrototypeId;
	private FinderPath _finderPathCountByLayoutPrototypeId;

	/**
	 * Returns all the layout page template entries where layoutPrototypeId = &#63;.
	 *
	 * @param layoutPrototypeId the layout prototype ID
	 * @return the matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByLayoutPrototypeId(
		long layoutPrototypeId) {

		return findByLayoutPrototypeId(
			layoutPrototypeId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout page template entries where layoutPrototypeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param layoutPrototypeId the layout prototype ID
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @return the range of matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByLayoutPrototypeId(
		long layoutPrototypeId, int start, int end) {

		return findByLayoutPrototypeId(layoutPrototypeId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout page template entries where layoutPrototypeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param layoutPrototypeId the layout prototype ID
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByLayoutPrototypeId(
		long layoutPrototypeId, int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		return findByLayoutPrototypeId(
			layoutPrototypeId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout page template entries where layoutPrototypeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param layoutPrototypeId the layout prototype ID
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByLayoutPrototypeId(
		long layoutPrototypeId, int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			LayoutPageTemplateEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath =
					_finderPathWithoutPaginationFindByLayoutPrototypeId;
				finderArgs = new Object[] {layoutPrototypeId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByLayoutPrototypeId;
			finderArgs = new Object[] {
				layoutPrototypeId, start, end, orderByComparator
			};
		}

		List<LayoutPageTemplateEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<LayoutPageTemplateEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutPageTemplateEntry layoutPageTemplateEntry : list) {
					if (layoutPrototypeId !=
							layoutPageTemplateEntry.getLayoutPrototypeId()) {

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

			sb.append(_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_LAYOUTPROTOTYPEID_LAYOUTPROTOTYPEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(layoutPrototypeId);

				list = (List<LayoutPageTemplateEntry>)QueryUtil.list(
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
	 * Returns the first layout page template entry in the ordered set where layoutPrototypeId = &#63;.
	 *
	 * @param layoutPrototypeId the layout prototype ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry findByLayoutPrototypeId_First(
			long layoutPrototypeId,
			OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			fetchByLayoutPrototypeId_First(
				layoutPrototypeId, orderByComparator);

		if (layoutPageTemplateEntry != null) {
			return layoutPageTemplateEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("layoutPrototypeId=");
		sb.append(layoutPrototypeId);

		sb.append("}");

		throw new NoSuchPageTemplateEntryException(sb.toString());
	}

	/**
	 * Returns the first layout page template entry in the ordered set where layoutPrototypeId = &#63;.
	 *
	 * @param layoutPrototypeId the layout prototype ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template entry, or <code>null</code> if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry fetchByLayoutPrototypeId_First(
		long layoutPrototypeId,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		List<LayoutPageTemplateEntry> list = findByLayoutPrototypeId(
			layoutPrototypeId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout page template entry in the ordered set where layoutPrototypeId = &#63;.
	 *
	 * @param layoutPrototypeId the layout prototype ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry findByLayoutPrototypeId_Last(
			long layoutPrototypeId,
			OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			fetchByLayoutPrototypeId_Last(layoutPrototypeId, orderByComparator);

		if (layoutPageTemplateEntry != null) {
			return layoutPageTemplateEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("layoutPrototypeId=");
		sb.append(layoutPrototypeId);

		sb.append("}");

		throw new NoSuchPageTemplateEntryException(sb.toString());
	}

	/**
	 * Returns the last layout page template entry in the ordered set where layoutPrototypeId = &#63;.
	 *
	 * @param layoutPrototypeId the layout prototype ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template entry, or <code>null</code> if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry fetchByLayoutPrototypeId_Last(
		long layoutPrototypeId,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		int count = countByLayoutPrototypeId(layoutPrototypeId);

		if (count == 0) {
			return null;
		}

		List<LayoutPageTemplateEntry> list = findByLayoutPrototypeId(
			layoutPrototypeId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout page template entries before and after the current layout page template entry in the ordered set where layoutPrototypeId = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the primary key of the current layout page template entry
	 * @param layoutPrototypeId the layout prototype ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a layout page template entry with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateEntry[] findByLayoutPrototypeId_PrevAndNext(
			long layoutPageTemplateEntryId, long layoutPrototypeId,
			OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {

		LayoutPageTemplateEntry layoutPageTemplateEntry = findByPrimaryKey(
			layoutPageTemplateEntryId);

		Session session = null;

		try {
			session = openSession();

			LayoutPageTemplateEntry[] array =
				new LayoutPageTemplateEntryImpl[3];

			array[0] = getByLayoutPrototypeId_PrevAndNext(
				session, layoutPageTemplateEntry, layoutPrototypeId,
				orderByComparator, true);

			array[1] = layoutPageTemplateEntry;

			array[2] = getByLayoutPrototypeId_PrevAndNext(
				session, layoutPageTemplateEntry, layoutPrototypeId,
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

	protected LayoutPageTemplateEntry getByLayoutPrototypeId_PrevAndNext(
		Session session, LayoutPageTemplateEntry layoutPageTemplateEntry,
		long layoutPrototypeId,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);

		sb.append(_FINDER_COLUMN_LAYOUTPROTOTYPEID_LAYOUTPROTOTYPEID_2);

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
			sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(layoutPrototypeId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutPageTemplateEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<LayoutPageTemplateEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout page template entries where layoutPrototypeId = &#63; from the database.
	 *
	 * @param layoutPrototypeId the layout prototype ID
	 */
	@Override
	public void removeByLayoutPrototypeId(long layoutPrototypeId) {
		for (LayoutPageTemplateEntry layoutPageTemplateEntry :
				findByLayoutPrototypeId(
					layoutPrototypeId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(layoutPageTemplateEntry);
		}
	}

	/**
	 * Returns the number of layout page template entries where layoutPrototypeId = &#63;.
	 *
	 * @param layoutPrototypeId the layout prototype ID
	 * @return the number of matching layout page template entries
	 */
	@Override
	public int countByLayoutPrototypeId(long layoutPrototypeId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			LayoutPageTemplateEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByLayoutPrototypeId;

			finderArgs = new Object[] {layoutPrototypeId};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_LAYOUTPAGETEMPLATEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_LAYOUTPROTOTYPEID_LAYOUTPROTOTYPEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(layoutPrototypeId);

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
		_FINDER_COLUMN_LAYOUTPROTOTYPEID_LAYOUTPROTOTYPEID_2 =
			"layoutPageTemplateEntry.layoutPrototypeId = ?";

	private FinderPath _finderPathFetchByPlid;
	private FinderPath _finderPathCountByPlid;

	/**
	 * Returns the layout page template entry where plid = &#63; or throws a <code>NoSuchPageTemplateEntryException</code> if it could not be found.
	 *
	 * @param plid the plid
	 * @return the matching layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry findByPlid(long plid)
		throws NoSuchPageTemplateEntryException {

		LayoutPageTemplateEntry layoutPageTemplateEntry = fetchByPlid(plid);

		if (layoutPageTemplateEntry == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("plid=");
			sb.append(plid);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchPageTemplateEntryException(sb.toString());
		}

		return layoutPageTemplateEntry;
	}

	/**
	 * Returns the layout page template entry where plid = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param plid the plid
	 * @return the matching layout page template entry, or <code>null</code> if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry fetchByPlid(long plid) {
		return fetchByPlid(plid, true);
	}

	/**
	 * Returns the layout page template entry where plid = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param plid the plid
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching layout page template entry, or <code>null</code> if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry fetchByPlid(
		long plid, boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			LayoutPageTemplateEntry.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {plid};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(
				_finderPathFetchByPlid, finderArgs, this);
		}

		if (result instanceof LayoutPageTemplateEntry) {
			LayoutPageTemplateEntry layoutPageTemplateEntry =
				(LayoutPageTemplateEntry)result;

			if (plid != layoutPageTemplateEntry.getPlid()) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_PLID_PLID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(plid);

				List<LayoutPageTemplateEntry> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByPlid, finderArgs, list);
					}
				}
				else {
					LayoutPageTemplateEntry layoutPageTemplateEntry = list.get(
						0);

					result = layoutPageTemplateEntry;

					cacheResult(layoutPageTemplateEntry);
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
			return (LayoutPageTemplateEntry)result;
		}
	}

	/**
	 * Removes the layout page template entry where plid = &#63; from the database.
	 *
	 * @param plid the plid
	 * @return the layout page template entry that was removed
	 */
	@Override
	public LayoutPageTemplateEntry removeByPlid(long plid)
		throws NoSuchPageTemplateEntryException {

		LayoutPageTemplateEntry layoutPageTemplateEntry = findByPlid(plid);

		return remove(layoutPageTemplateEntry);
	}

	/**
	 * Returns the number of layout page template entries where plid = &#63;.
	 *
	 * @param plid the plid
	 * @return the number of matching layout page template entries
	 */
	@Override
	public int countByPlid(long plid) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			LayoutPageTemplateEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByPlid;

			finderArgs = new Object[] {plid};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_LAYOUTPAGETEMPLATEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_PLID_PLID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(plid);

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

	private static final String _FINDER_COLUMN_PLID_PLID_2 =
		"layoutPageTemplateEntry.plid = ?";

	private FinderPath _finderPathWithPaginationFindByG_L;
	private FinderPath _finderPathWithoutPaginationFindByG_L;
	private FinderPath _finderPathCountByG_L;

	/**
	 * Returns all the layout page template entries where groupId = &#63; and layoutPageTemplateCollectionId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @return the matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_L(
		long groupId, long layoutPageTemplateCollectionId) {

		return findByG_L(
			groupId, layoutPageTemplateCollectionId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout page template entries where groupId = &#63; and layoutPageTemplateCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @return the range of matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_L(
		long groupId, long layoutPageTemplateCollectionId, int start, int end) {

		return findByG_L(
			groupId, layoutPageTemplateCollectionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout page template entries where groupId = &#63; and layoutPageTemplateCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_L(
		long groupId, long layoutPageTemplateCollectionId, int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		return findByG_L(
			groupId, layoutPageTemplateCollectionId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout page template entries where groupId = &#63; and layoutPageTemplateCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_L(
		long groupId, long layoutPageTemplateCollectionId, int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			LayoutPageTemplateEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByG_L;
				finderArgs = new Object[] {
					groupId, layoutPageTemplateCollectionId
				};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByG_L;
			finderArgs = new Object[] {
				groupId, layoutPageTemplateCollectionId, start, end,
				orderByComparator
			};
		}

		List<LayoutPageTemplateEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<LayoutPageTemplateEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutPageTemplateEntry layoutPageTemplateEntry : list) {
					if ((groupId != layoutPageTemplateEntry.getGroupId()) ||
						(layoutPageTemplateCollectionId !=
							layoutPageTemplateEntry.
								getLayoutPageTemplateCollectionId())) {

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

			sb.append(_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_L_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_L_LAYOUTPAGETEMPLATECOLLECTIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(layoutPageTemplateCollectionId);

				list = (List<LayoutPageTemplateEntry>)QueryUtil.list(
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
	 * Returns the first layout page template entry in the ordered set where groupId = &#63; and layoutPageTemplateCollectionId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry findByG_L_First(
			long groupId, long layoutPageTemplateCollectionId,
			OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {

		LayoutPageTemplateEntry layoutPageTemplateEntry = fetchByG_L_First(
			groupId, layoutPageTemplateCollectionId, orderByComparator);

		if (layoutPageTemplateEntry != null) {
			return layoutPageTemplateEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", layoutPageTemplateCollectionId=");
		sb.append(layoutPageTemplateCollectionId);

		sb.append("}");

		throw new NoSuchPageTemplateEntryException(sb.toString());
	}

	/**
	 * Returns the first layout page template entry in the ordered set where groupId = &#63; and layoutPageTemplateCollectionId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template entry, or <code>null</code> if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry fetchByG_L_First(
		long groupId, long layoutPageTemplateCollectionId,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		List<LayoutPageTemplateEntry> list = findByG_L(
			groupId, layoutPageTemplateCollectionId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout page template entry in the ordered set where groupId = &#63; and layoutPageTemplateCollectionId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry findByG_L_Last(
			long groupId, long layoutPageTemplateCollectionId,
			OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {

		LayoutPageTemplateEntry layoutPageTemplateEntry = fetchByG_L_Last(
			groupId, layoutPageTemplateCollectionId, orderByComparator);

		if (layoutPageTemplateEntry != null) {
			return layoutPageTemplateEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", layoutPageTemplateCollectionId=");
		sb.append(layoutPageTemplateCollectionId);

		sb.append("}");

		throw new NoSuchPageTemplateEntryException(sb.toString());
	}

	/**
	 * Returns the last layout page template entry in the ordered set where groupId = &#63; and layoutPageTemplateCollectionId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template entry, or <code>null</code> if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry fetchByG_L_Last(
		long groupId, long layoutPageTemplateCollectionId,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		int count = countByG_L(groupId, layoutPageTemplateCollectionId);

		if (count == 0) {
			return null;
		}

		List<LayoutPageTemplateEntry> list = findByG_L(
			groupId, layoutPageTemplateCollectionId, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout page template entries before and after the current layout page template entry in the ordered set where groupId = &#63; and layoutPageTemplateCollectionId = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the primary key of the current layout page template entry
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a layout page template entry with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateEntry[] findByG_L_PrevAndNext(
			long layoutPageTemplateEntryId, long groupId,
			long layoutPageTemplateCollectionId,
			OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {

		LayoutPageTemplateEntry layoutPageTemplateEntry = findByPrimaryKey(
			layoutPageTemplateEntryId);

		Session session = null;

		try {
			session = openSession();

			LayoutPageTemplateEntry[] array =
				new LayoutPageTemplateEntryImpl[3];

			array[0] = getByG_L_PrevAndNext(
				session, layoutPageTemplateEntry, groupId,
				layoutPageTemplateCollectionId, orderByComparator, true);

			array[1] = layoutPageTemplateEntry;

			array[2] = getByG_L_PrevAndNext(
				session, layoutPageTemplateEntry, groupId,
				layoutPageTemplateCollectionId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutPageTemplateEntry getByG_L_PrevAndNext(
		Session session, LayoutPageTemplateEntry layoutPageTemplateEntry,
		long groupId, long layoutPageTemplateCollectionId,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_L_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_L_LAYOUTPAGETEMPLATECOLLECTIONID_2);

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
			sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(layoutPageTemplateCollectionId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutPageTemplateEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<LayoutPageTemplateEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the layout page template entries that the user has permission to view where groupId = &#63; and layoutPageTemplateCollectionId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @return the matching layout page template entries that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateEntry> filterFindByG_L(
		long groupId, long layoutPageTemplateCollectionId) {

		return filterFindByG_L(
			groupId, layoutPageTemplateCollectionId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout page template entries that the user has permission to view where groupId = &#63; and layoutPageTemplateCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @return the range of matching layout page template entries that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateEntry> filterFindByG_L(
		long groupId, long layoutPageTemplateCollectionId, int start, int end) {

		return filterFindByG_L(
			groupId, layoutPageTemplateCollectionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout page template entries that the user has permissions to view where groupId = &#63; and layoutPageTemplateCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template entries that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateEntry> filterFindByG_L(
		long groupId, long layoutPageTemplateCollectionId, int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_L(
				groupId, layoutPageTemplateCollectionId, start, end,
				orderByComparator);
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
			sb.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_L_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_L_LAYOUTPAGETEMPLATECOLLECTIONID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), LayoutPageTemplateEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, LayoutPageTemplateEntryImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, LayoutPageTemplateEntryImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(layoutPageTemplateCollectionId);

			return (List<LayoutPageTemplateEntry>)QueryUtil.list(
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
	 * Returns the layout page template entries before and after the current layout page template entry in the ordered set of layout page template entries that the user has permission to view where groupId = &#63; and layoutPageTemplateCollectionId = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the primary key of the current layout page template entry
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a layout page template entry with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateEntry[] filterFindByG_L_PrevAndNext(
			long layoutPageTemplateEntryId, long groupId,
			long layoutPageTemplateCollectionId,
			OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_L_PrevAndNext(
				layoutPageTemplateEntryId, groupId,
				layoutPageTemplateCollectionId, orderByComparator);
		}

		LayoutPageTemplateEntry layoutPageTemplateEntry = findByPrimaryKey(
			layoutPageTemplateEntryId);

		Session session = null;

		try {
			session = openSession();

			LayoutPageTemplateEntry[] array =
				new LayoutPageTemplateEntryImpl[3];

			array[0] = filterGetByG_L_PrevAndNext(
				session, layoutPageTemplateEntry, groupId,
				layoutPageTemplateCollectionId, orderByComparator, true);

			array[1] = layoutPageTemplateEntry;

			array[2] = filterGetByG_L_PrevAndNext(
				session, layoutPageTemplateEntry, groupId,
				layoutPageTemplateCollectionId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutPageTemplateEntry filterGetByG_L_PrevAndNext(
		Session session, LayoutPageTemplateEntry layoutPageTemplateEntry,
		long groupId, long layoutPageTemplateCollectionId,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator,
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
			sb.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_L_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_L_LAYOUTPAGETEMPLATECOLLECTIONID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), LayoutPageTemplateEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(
				_FILTER_ENTITY_ALIAS, LayoutPageTemplateEntryImpl.class);
		}
		else {
			sqlQuery.addEntity(
				_FILTER_ENTITY_TABLE, LayoutPageTemplateEntryImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(groupId);

		queryPos.add(layoutPageTemplateCollectionId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutPageTemplateEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<LayoutPageTemplateEntry> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout page template entries where groupId = &#63; and layoutPageTemplateCollectionId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 */
	@Override
	public void removeByG_L(long groupId, long layoutPageTemplateCollectionId) {
		for (LayoutPageTemplateEntry layoutPageTemplateEntry :
				findByG_L(
					groupId, layoutPageTemplateCollectionId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(layoutPageTemplateEntry);
		}
	}

	/**
	 * Returns the number of layout page template entries where groupId = &#63; and layoutPageTemplateCollectionId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @return the number of matching layout page template entries
	 */
	@Override
	public int countByG_L(long groupId, long layoutPageTemplateCollectionId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			LayoutPageTemplateEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_L;

			finderArgs = new Object[] {groupId, layoutPageTemplateCollectionId};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_LAYOUTPAGETEMPLATEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_L_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_L_LAYOUTPAGETEMPLATECOLLECTIONID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(layoutPageTemplateCollectionId);

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
	 * Returns the number of layout page template entries that the user has permission to view where groupId = &#63; and layoutPageTemplateCollectionId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @return the number of matching layout page template entries that the user has permission to view
	 */
	@Override
	public int filterCountByG_L(
		long groupId, long layoutPageTemplateCollectionId) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_L(groupId, layoutPageTemplateCollectionId);
		}

		StringBundler sb = new StringBundler(3);

		sb.append(_FILTER_SQL_COUNT_LAYOUTPAGETEMPLATEENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_L_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_L_LAYOUTPAGETEMPLATECOLLECTIONID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), LayoutPageTemplateEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(layoutPageTemplateCollectionId);

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

	private static final String _FINDER_COLUMN_G_L_GROUPID_2 =
		"layoutPageTemplateEntry.groupId = ? AND ";

	private static final String
		_FINDER_COLUMN_G_L_LAYOUTPAGETEMPLATECOLLECTIONID_2 =
			"layoutPageTemplateEntry.layoutPageTemplateCollectionId = ?";

	private FinderPath _finderPathFetchByG_LPTEK;
	private FinderPath _finderPathCountByG_LPTEK;

	/**
	 * Returns the layout page template entry where groupId = &#63; and layoutPageTemplateEntryKey = &#63; or throws a <code>NoSuchPageTemplateEntryException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateEntryKey the layout page template entry key
	 * @return the matching layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry findByG_LPTEK(
			long groupId, String layoutPageTemplateEntryKey)
		throws NoSuchPageTemplateEntryException {

		LayoutPageTemplateEntry layoutPageTemplateEntry = fetchByG_LPTEK(
			groupId, layoutPageTemplateEntryKey);

		if (layoutPageTemplateEntry == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("groupId=");
			sb.append(groupId);

			sb.append(", layoutPageTemplateEntryKey=");
			sb.append(layoutPageTemplateEntryKey);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchPageTemplateEntryException(sb.toString());
		}

		return layoutPageTemplateEntry;
	}

	/**
	 * Returns the layout page template entry where groupId = &#63; and layoutPageTemplateEntryKey = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateEntryKey the layout page template entry key
	 * @return the matching layout page template entry, or <code>null</code> if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry fetchByG_LPTEK(
		long groupId, String layoutPageTemplateEntryKey) {

		return fetchByG_LPTEK(groupId, layoutPageTemplateEntryKey, true);
	}

	/**
	 * Returns the layout page template entry where groupId = &#63; and layoutPageTemplateEntryKey = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateEntryKey the layout page template entry key
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching layout page template entry, or <code>null</code> if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry fetchByG_LPTEK(
		long groupId, String layoutPageTemplateEntryKey,
		boolean useFinderCache) {

		layoutPageTemplateEntryKey = Objects.toString(
			layoutPageTemplateEntryKey, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			LayoutPageTemplateEntry.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {groupId, layoutPageTemplateEntryKey};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(
				_finderPathFetchByG_LPTEK, finderArgs, this);
		}

		if (result instanceof LayoutPageTemplateEntry) {
			LayoutPageTemplateEntry layoutPageTemplateEntry =
				(LayoutPageTemplateEntry)result;

			if ((groupId != layoutPageTemplateEntry.getGroupId()) ||
				!Objects.equals(
					layoutPageTemplateEntryKey,
					layoutPageTemplateEntry.getLayoutPageTemplateEntryKey())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_LPTEK_GROUPID_2);

			boolean bindLayoutPageTemplateEntryKey = false;

			if (layoutPageTemplateEntryKey.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_LPTEK_LAYOUTPAGETEMPLATEENTRYKEY_3);
			}
			else {
				bindLayoutPageTemplateEntryKey = true;

				sb.append(_FINDER_COLUMN_G_LPTEK_LAYOUTPAGETEMPLATEENTRYKEY_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindLayoutPageTemplateEntryKey) {
					queryPos.add(layoutPageTemplateEntryKey);
				}

				List<LayoutPageTemplateEntry> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByG_LPTEK, finderArgs, list);
					}
				}
				else {
					LayoutPageTemplateEntry layoutPageTemplateEntry = list.get(
						0);

					result = layoutPageTemplateEntry;

					cacheResult(layoutPageTemplateEntry);
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
			return (LayoutPageTemplateEntry)result;
		}
	}

	/**
	 * Removes the layout page template entry where groupId = &#63; and layoutPageTemplateEntryKey = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateEntryKey the layout page template entry key
	 * @return the layout page template entry that was removed
	 */
	@Override
	public LayoutPageTemplateEntry removeByG_LPTEK(
			long groupId, String layoutPageTemplateEntryKey)
		throws NoSuchPageTemplateEntryException {

		LayoutPageTemplateEntry layoutPageTemplateEntry = findByG_LPTEK(
			groupId, layoutPageTemplateEntryKey);

		return remove(layoutPageTemplateEntry);
	}

	/**
	 * Returns the number of layout page template entries where groupId = &#63; and layoutPageTemplateEntryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateEntryKey the layout page template entry key
	 * @return the number of matching layout page template entries
	 */
	@Override
	public int countByG_LPTEK(long groupId, String layoutPageTemplateEntryKey) {
		layoutPageTemplateEntryKey = Objects.toString(
			layoutPageTemplateEntryKey, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			LayoutPageTemplateEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_LPTEK;

			finderArgs = new Object[] {groupId, layoutPageTemplateEntryKey};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_LAYOUTPAGETEMPLATEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_LPTEK_GROUPID_2);

			boolean bindLayoutPageTemplateEntryKey = false;

			if (layoutPageTemplateEntryKey.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_LPTEK_LAYOUTPAGETEMPLATEENTRYKEY_3);
			}
			else {
				bindLayoutPageTemplateEntryKey = true;

				sb.append(_FINDER_COLUMN_G_LPTEK_LAYOUTPAGETEMPLATEENTRYKEY_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindLayoutPageTemplateEntryKey) {
					queryPos.add(layoutPageTemplateEntryKey);
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

	private static final String _FINDER_COLUMN_G_LPTEK_GROUPID_2 =
		"layoutPageTemplateEntry.groupId = ? AND ";

	private static final String
		_FINDER_COLUMN_G_LPTEK_LAYOUTPAGETEMPLATEENTRYKEY_2 =
			"layoutPageTemplateEntry.layoutPageTemplateEntryKey = ?";

	private static final String
		_FINDER_COLUMN_G_LPTEK_LAYOUTPAGETEMPLATEENTRYKEY_3 =
			"(layoutPageTemplateEntry.layoutPageTemplateEntryKey IS NULL OR layoutPageTemplateEntry.layoutPageTemplateEntryKey = '')";

	private FinderPath _finderPathWithPaginationFindByG_N;
	private FinderPath _finderPathWithoutPaginationFindByG_N;
	private FinderPath _finderPathCountByG_N;

	/**
	 * Returns all the layout page template entries where groupId = &#63; and name = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_N(long groupId, String name) {
		return findByG_N(
			groupId, name, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout page template entries where groupId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @return the range of matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_N(
		long groupId, String name, int start, int end) {

		return findByG_N(groupId, name, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout page template entries where groupId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_N(
		long groupId, String name, int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		return findByG_N(groupId, name, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout page template entries where groupId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_N(
		long groupId, String name, int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator,
		boolean useFinderCache) {

		name = Objects.toString(name, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			LayoutPageTemplateEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByG_N;
				finderArgs = new Object[] {groupId, name};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByG_N;
			finderArgs = new Object[] {
				groupId, name, start, end, orderByComparator
			};
		}

		List<LayoutPageTemplateEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<LayoutPageTemplateEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutPageTemplateEntry layoutPageTemplateEntry : list) {
					if ((groupId != layoutPageTemplateEntry.getGroupId()) ||
						!name.equals(layoutPageTemplateEntry.getName())) {

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

			sb.append(_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_N_GROUPID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_N_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_G_N_NAME_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
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

				list = (List<LayoutPageTemplateEntry>)QueryUtil.list(
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
	 * Returns the first layout page template entry in the ordered set where groupId = &#63; and name = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry findByG_N_First(
			long groupId, String name,
			OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {

		LayoutPageTemplateEntry layoutPageTemplateEntry = fetchByG_N_First(
			groupId, name, orderByComparator);

		if (layoutPageTemplateEntry != null) {
			return layoutPageTemplateEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", name=");
		sb.append(name);

		sb.append("}");

		throw new NoSuchPageTemplateEntryException(sb.toString());
	}

	/**
	 * Returns the first layout page template entry in the ordered set where groupId = &#63; and name = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template entry, or <code>null</code> if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry fetchByG_N_First(
		long groupId, String name,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		List<LayoutPageTemplateEntry> list = findByG_N(
			groupId, name, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout page template entry in the ordered set where groupId = &#63; and name = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry findByG_N_Last(
			long groupId, String name,
			OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {

		LayoutPageTemplateEntry layoutPageTemplateEntry = fetchByG_N_Last(
			groupId, name, orderByComparator);

		if (layoutPageTemplateEntry != null) {
			return layoutPageTemplateEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", name=");
		sb.append(name);

		sb.append("}");

		throw new NoSuchPageTemplateEntryException(sb.toString());
	}

	/**
	 * Returns the last layout page template entry in the ordered set where groupId = &#63; and name = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template entry, or <code>null</code> if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry fetchByG_N_Last(
		long groupId, String name,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		int count = countByG_N(groupId, name);

		if (count == 0) {
			return null;
		}

		List<LayoutPageTemplateEntry> list = findByG_N(
			groupId, name, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout page template entries before and after the current layout page template entry in the ordered set where groupId = &#63; and name = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the primary key of the current layout page template entry
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a layout page template entry with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateEntry[] findByG_N_PrevAndNext(
			long layoutPageTemplateEntryId, long groupId, String name,
			OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {

		name = Objects.toString(name, "");

		LayoutPageTemplateEntry layoutPageTemplateEntry = findByPrimaryKey(
			layoutPageTemplateEntryId);

		Session session = null;

		try {
			session = openSession();

			LayoutPageTemplateEntry[] array =
				new LayoutPageTemplateEntryImpl[3];

			array[0] = getByG_N_PrevAndNext(
				session, layoutPageTemplateEntry, groupId, name,
				orderByComparator, true);

			array[1] = layoutPageTemplateEntry;

			array[2] = getByG_N_PrevAndNext(
				session, layoutPageTemplateEntry, groupId, name,
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

	protected LayoutPageTemplateEntry getByG_N_PrevAndNext(
		Session session, LayoutPageTemplateEntry layoutPageTemplateEntry,
		long groupId, String name,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_N_GROUPID_2);

		boolean bindName = false;

		if (name.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_N_NAME_3);
		}
		else {
			bindName = true;

			sb.append(_FINDER_COLUMN_G_N_NAME_2);
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
			sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
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
						layoutPageTemplateEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<LayoutPageTemplateEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the layout page template entries that the user has permission to view where groupId = &#63; and name = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the matching layout page template entries that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateEntry> filterFindByG_N(
		long groupId, String name) {

		return filterFindByG_N(
			groupId, name, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout page template entries that the user has permission to view where groupId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @return the range of matching layout page template entries that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateEntry> filterFindByG_N(
		long groupId, String name, int start, int end) {

		return filterFindByG_N(groupId, name, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout page template entries that the user has permissions to view where groupId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template entries that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateEntry> filterFindByG_N(
		long groupId, String name, int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_N(groupId, name, start, end, orderByComparator);
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
			sb.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_N_GROUPID_2);

		boolean bindName = false;

		if (name.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_N_NAME_3);
		}
		else {
			bindName = true;

			sb.append(_FINDER_COLUMN_G_N_NAME_2);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), LayoutPageTemplateEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, LayoutPageTemplateEntryImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, LayoutPageTemplateEntryImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			if (bindName) {
				queryPos.add(name);
			}

			return (List<LayoutPageTemplateEntry>)QueryUtil.list(
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
	 * Returns the layout page template entries before and after the current layout page template entry in the ordered set of layout page template entries that the user has permission to view where groupId = &#63; and name = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the primary key of the current layout page template entry
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a layout page template entry with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateEntry[] filterFindByG_N_PrevAndNext(
			long layoutPageTemplateEntryId, long groupId, String name,
			OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_N_PrevAndNext(
				layoutPageTemplateEntryId, groupId, name, orderByComparator);
		}

		name = Objects.toString(name, "");

		LayoutPageTemplateEntry layoutPageTemplateEntry = findByPrimaryKey(
			layoutPageTemplateEntryId);

		Session session = null;

		try {
			session = openSession();

			LayoutPageTemplateEntry[] array =
				new LayoutPageTemplateEntryImpl[3];

			array[0] = filterGetByG_N_PrevAndNext(
				session, layoutPageTemplateEntry, groupId, name,
				orderByComparator, true);

			array[1] = layoutPageTemplateEntry;

			array[2] = filterGetByG_N_PrevAndNext(
				session, layoutPageTemplateEntry, groupId, name,
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

	protected LayoutPageTemplateEntry filterGetByG_N_PrevAndNext(
		Session session, LayoutPageTemplateEntry layoutPageTemplateEntry,
		long groupId, String name,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator,
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
			sb.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_N_GROUPID_2);

		boolean bindName = false;

		if (name.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_N_NAME_3);
		}
		else {
			bindName = true;

			sb.append(_FINDER_COLUMN_G_N_NAME_2);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), LayoutPageTemplateEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(
				_FILTER_ENTITY_ALIAS, LayoutPageTemplateEntryImpl.class);
		}
		else {
			sqlQuery.addEntity(
				_FILTER_ENTITY_TABLE, LayoutPageTemplateEntryImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(groupId);

		if (bindName) {
			queryPos.add(name);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutPageTemplateEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<LayoutPageTemplateEntry> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout page template entries where groupId = &#63; and name = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 */
	@Override
	public void removeByG_N(long groupId, String name) {
		for (LayoutPageTemplateEntry layoutPageTemplateEntry :
				findByG_N(
					groupId, name, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(layoutPageTemplateEntry);
		}
	}

	/**
	 * Returns the number of layout page template entries where groupId = &#63; and name = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the number of matching layout page template entries
	 */
	@Override
	public int countByG_N(long groupId, String name) {
		name = Objects.toString(name, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			LayoutPageTemplateEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_N;

			finderArgs = new Object[] {groupId, name};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_LAYOUTPAGETEMPLATEENTRY_WHERE);

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
	 * Returns the number of layout page template entries that the user has permission to view where groupId = &#63; and name = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the number of matching layout page template entries that the user has permission to view
	 */
	@Override
	public int filterCountByG_N(long groupId, String name) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_N(groupId, name);
		}

		name = Objects.toString(name, "");

		StringBundler sb = new StringBundler(3);

		sb.append(_FILTER_SQL_COUNT_LAYOUTPAGETEMPLATEENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_N_GROUPID_2);

		boolean bindName = false;

		if (name.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_N_NAME_3);
		}
		else {
			bindName = true;

			sb.append(_FINDER_COLUMN_G_N_NAME_2);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), LayoutPageTemplateEntry.class.getName(),
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

	private static final String _FINDER_COLUMN_G_N_GROUPID_2 =
		"layoutPageTemplateEntry.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_N_NAME_2 =
		"layoutPageTemplateEntry.name = ?";

	private static final String _FINDER_COLUMN_G_N_NAME_3 =
		"(layoutPageTemplateEntry.name IS NULL OR layoutPageTemplateEntry.name = '')";

	private FinderPath _finderPathWithPaginationFindByG_T;
	private FinderPath _finderPathWithoutPaginationFindByG_T;
	private FinderPath _finderPathCountByG_T;

	/**
	 * Returns all the layout page template entries where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @return the matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_T(long groupId, int type) {
		return findByG_T(
			groupId, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout page template entries where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @return the range of matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_T(
		long groupId, int type, int start, int end) {

		return findByG_T(groupId, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout page template entries where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_T(
		long groupId, int type, int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		return findByG_T(groupId, type, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout page template entries where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_T(
		long groupId, int type, int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			LayoutPageTemplateEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByG_T;
				finderArgs = new Object[] {groupId, type};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByG_T;
			finderArgs = new Object[] {
				groupId, type, start, end, orderByComparator
			};
		}

		List<LayoutPageTemplateEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<LayoutPageTemplateEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutPageTemplateEntry layoutPageTemplateEntry : list) {
					if ((groupId != layoutPageTemplateEntry.getGroupId()) ||
						(type != layoutPageTemplateEntry.getType())) {

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

			sb.append(_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_T_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_T_TYPE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(type);

				list = (List<LayoutPageTemplateEntry>)QueryUtil.list(
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
	 * Returns the first layout page template entry in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry findByG_T_First(
			long groupId, int type,
			OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {

		LayoutPageTemplateEntry layoutPageTemplateEntry = fetchByG_T_First(
			groupId, type, orderByComparator);

		if (layoutPageTemplateEntry != null) {
			return layoutPageTemplateEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchPageTemplateEntryException(sb.toString());
	}

	/**
	 * Returns the first layout page template entry in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template entry, or <code>null</code> if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry fetchByG_T_First(
		long groupId, int type,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		List<LayoutPageTemplateEntry> list = findByG_T(
			groupId, type, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout page template entry in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry findByG_T_Last(
			long groupId, int type,
			OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {

		LayoutPageTemplateEntry layoutPageTemplateEntry = fetchByG_T_Last(
			groupId, type, orderByComparator);

		if (layoutPageTemplateEntry != null) {
			return layoutPageTemplateEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchPageTemplateEntryException(sb.toString());
	}

	/**
	 * Returns the last layout page template entry in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template entry, or <code>null</code> if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry fetchByG_T_Last(
		long groupId, int type,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		int count = countByG_T(groupId, type);

		if (count == 0) {
			return null;
		}

		List<LayoutPageTemplateEntry> list = findByG_T(
			groupId, type, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout page template entries before and after the current layout page template entry in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the primary key of the current layout page template entry
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a layout page template entry with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateEntry[] findByG_T_PrevAndNext(
			long layoutPageTemplateEntryId, long groupId, int type,
			OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {

		LayoutPageTemplateEntry layoutPageTemplateEntry = findByPrimaryKey(
			layoutPageTemplateEntryId);

		Session session = null;

		try {
			session = openSession();

			LayoutPageTemplateEntry[] array =
				new LayoutPageTemplateEntryImpl[3];

			array[0] = getByG_T_PrevAndNext(
				session, layoutPageTemplateEntry, groupId, type,
				orderByComparator, true);

			array[1] = layoutPageTemplateEntry;

			array[2] = getByG_T_PrevAndNext(
				session, layoutPageTemplateEntry, groupId, type,
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

	protected LayoutPageTemplateEntry getByG_T_PrevAndNext(
		Session session, LayoutPageTemplateEntry layoutPageTemplateEntry,
		long groupId, int type,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);

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
			sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
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
						layoutPageTemplateEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<LayoutPageTemplateEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the layout page template entries that the user has permission to view where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @return the matching layout page template entries that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateEntry> filterFindByG_T(
		long groupId, int type) {

		return filterFindByG_T(
			groupId, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout page template entries that the user has permission to view where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @return the range of matching layout page template entries that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateEntry> filterFindByG_T(
		long groupId, int type, int start, int end) {

		return filterFindByG_T(groupId, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout page template entries that the user has permissions to view where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template entries that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateEntry> filterFindByG_T(
		long groupId, int type, int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

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
			sb.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_T_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_T_TYPE_2_SQL);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), LayoutPageTemplateEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, LayoutPageTemplateEntryImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, LayoutPageTemplateEntryImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(type);

			return (List<LayoutPageTemplateEntry>)QueryUtil.list(
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
	 * Returns the layout page template entries before and after the current layout page template entry in the ordered set of layout page template entries that the user has permission to view where groupId = &#63; and type = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the primary key of the current layout page template entry
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a layout page template entry with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateEntry[] filterFindByG_T_PrevAndNext(
			long layoutPageTemplateEntryId, long groupId, int type,
			OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_T_PrevAndNext(
				layoutPageTemplateEntryId, groupId, type, orderByComparator);
		}

		LayoutPageTemplateEntry layoutPageTemplateEntry = findByPrimaryKey(
			layoutPageTemplateEntryId);

		Session session = null;

		try {
			session = openSession();

			LayoutPageTemplateEntry[] array =
				new LayoutPageTemplateEntryImpl[3];

			array[0] = filterGetByG_T_PrevAndNext(
				session, layoutPageTemplateEntry, groupId, type,
				orderByComparator, true);

			array[1] = layoutPageTemplateEntry;

			array[2] = filterGetByG_T_PrevAndNext(
				session, layoutPageTemplateEntry, groupId, type,
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

	protected LayoutPageTemplateEntry filterGetByG_T_PrevAndNext(
		Session session, LayoutPageTemplateEntry layoutPageTemplateEntry,
		long groupId, int type,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator,
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
			sb.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_T_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_T_TYPE_2_SQL);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), LayoutPageTemplateEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(
				_FILTER_ENTITY_ALIAS, LayoutPageTemplateEntryImpl.class);
		}
		else {
			sqlQuery.addEntity(
				_FILTER_ENTITY_TABLE, LayoutPageTemplateEntryImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(groupId);

		queryPos.add(type);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutPageTemplateEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<LayoutPageTemplateEntry> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout page template entries where groupId = &#63; and type = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 */
	@Override
	public void removeByG_T(long groupId, int type) {
		for (LayoutPageTemplateEntry layoutPageTemplateEntry :
				findByG_T(
					groupId, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(layoutPageTemplateEntry);
		}
	}

	/**
	 * Returns the number of layout page template entries where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @return the number of matching layout page template entries
	 */
	@Override
	public int countByG_T(long groupId, int type) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			LayoutPageTemplateEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_T;

			finderArgs = new Object[] {groupId, type};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_LAYOUTPAGETEMPLATEENTRY_WHERE);

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
	 * Returns the number of layout page template entries that the user has permission to view where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @return the number of matching layout page template entries that the user has permission to view
	 */
	@Override
	public int filterCountByG_T(long groupId, int type) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_T(groupId, type);
		}

		StringBundler sb = new StringBundler(3);

		sb.append(_FILTER_SQL_COUNT_LAYOUTPAGETEMPLATEENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_T_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_T_TYPE_2_SQL);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), LayoutPageTemplateEntry.class.getName(),
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
		"layoutPageTemplateEntry.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_T_TYPE_2 =
		"layoutPageTemplateEntry.type = ?";

	private static final String _FINDER_COLUMN_G_T_TYPE_2_SQL =
		"layoutPageTemplateEntry.type_ = ?";

	private FinderPath _finderPathWithPaginationFindByG_L_LikeN;
	private FinderPath _finderPathWithPaginationCountByG_L_LikeN;

	/**
	 * Returns all the layout page template entries where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param name the name
	 * @return the matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_L_LikeN(
		long groupId, long layoutPageTemplateCollectionId, String name) {

		return findByG_L_LikeN(
			groupId, layoutPageTemplateCollectionId, name, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout page template entries where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param name the name
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @return the range of matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_L_LikeN(
		long groupId, long layoutPageTemplateCollectionId, String name,
		int start, int end) {

		return findByG_L_LikeN(
			groupId, layoutPageTemplateCollectionId, name, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout page template entries where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param name the name
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_L_LikeN(
		long groupId, long layoutPageTemplateCollectionId, String name,
		int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		return findByG_L_LikeN(
			groupId, layoutPageTemplateCollectionId, name, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout page template entries where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param name the name
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_L_LikeN(
		long groupId, long layoutPageTemplateCollectionId, String name,
		int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator,
		boolean useFinderCache) {

		name = Objects.toString(name, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			LayoutPageTemplateEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByG_L_LikeN;
		finderArgs = new Object[] {
			groupId, layoutPageTemplateCollectionId, name, start, end,
			orderByComparator
		};

		List<LayoutPageTemplateEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<LayoutPageTemplateEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutPageTemplateEntry layoutPageTemplateEntry : list) {
					if ((groupId != layoutPageTemplateEntry.getGroupId()) ||
						(layoutPageTemplateCollectionId !=
							layoutPageTemplateEntry.
								getLayoutPageTemplateCollectionId()) ||
						!StringUtil.wildcardMatches(
							layoutPageTemplateEntry.getName(), name, '_', '%',
							'\\', true)) {

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

			sb.append(_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_L_LIKEN_GROUPID_2);

			sb.append(
				_FINDER_COLUMN_G_L_LIKEN_LAYOUTPAGETEMPLATECOLLECTIONID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_L_LIKEN_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_G_L_LIKEN_NAME_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(layoutPageTemplateCollectionId);

				if (bindName) {
					queryPos.add(name);
				}

				list = (List<LayoutPageTemplateEntry>)QueryUtil.list(
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
	 * Returns the first layout page template entry in the ordered set where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry findByG_L_LikeN_First(
			long groupId, long layoutPageTemplateCollectionId, String name,
			OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			fetchByG_L_LikeN_First(
				groupId, layoutPageTemplateCollectionId, name,
				orderByComparator);

		if (layoutPageTemplateEntry != null) {
			return layoutPageTemplateEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", layoutPageTemplateCollectionId=");
		sb.append(layoutPageTemplateCollectionId);

		sb.append(", nameLIKE");
		sb.append(name);

		sb.append("}");

		throw new NoSuchPageTemplateEntryException(sb.toString());
	}

	/**
	 * Returns the first layout page template entry in the ordered set where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template entry, or <code>null</code> if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry fetchByG_L_LikeN_First(
		long groupId, long layoutPageTemplateCollectionId, String name,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		List<LayoutPageTemplateEntry> list = findByG_L_LikeN(
			groupId, layoutPageTemplateCollectionId, name, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout page template entry in the ordered set where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry findByG_L_LikeN_Last(
			long groupId, long layoutPageTemplateCollectionId, String name,
			OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {

		LayoutPageTemplateEntry layoutPageTemplateEntry = fetchByG_L_LikeN_Last(
			groupId, layoutPageTemplateCollectionId, name, orderByComparator);

		if (layoutPageTemplateEntry != null) {
			return layoutPageTemplateEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", layoutPageTemplateCollectionId=");
		sb.append(layoutPageTemplateCollectionId);

		sb.append(", nameLIKE");
		sb.append(name);

		sb.append("}");

		throw new NoSuchPageTemplateEntryException(sb.toString());
	}

	/**
	 * Returns the last layout page template entry in the ordered set where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template entry, or <code>null</code> if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry fetchByG_L_LikeN_Last(
		long groupId, long layoutPageTemplateCollectionId, String name,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		int count = countByG_L_LikeN(
			groupId, layoutPageTemplateCollectionId, name);

		if (count == 0) {
			return null;
		}

		List<LayoutPageTemplateEntry> list = findByG_L_LikeN(
			groupId, layoutPageTemplateCollectionId, name, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout page template entries before and after the current layout page template entry in the ordered set where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and name LIKE &#63;.
	 *
	 * @param layoutPageTemplateEntryId the primary key of the current layout page template entry
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a layout page template entry with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateEntry[] findByG_L_LikeN_PrevAndNext(
			long layoutPageTemplateEntryId, long groupId,
			long layoutPageTemplateCollectionId, String name,
			OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {

		name = Objects.toString(name, "");

		LayoutPageTemplateEntry layoutPageTemplateEntry = findByPrimaryKey(
			layoutPageTemplateEntryId);

		Session session = null;

		try {
			session = openSession();

			LayoutPageTemplateEntry[] array =
				new LayoutPageTemplateEntryImpl[3];

			array[0] = getByG_L_LikeN_PrevAndNext(
				session, layoutPageTemplateEntry, groupId,
				layoutPageTemplateCollectionId, name, orderByComparator, true);

			array[1] = layoutPageTemplateEntry;

			array[2] = getByG_L_LikeN_PrevAndNext(
				session, layoutPageTemplateEntry, groupId,
				layoutPageTemplateCollectionId, name, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutPageTemplateEntry getByG_L_LikeN_PrevAndNext(
		Session session, LayoutPageTemplateEntry layoutPageTemplateEntry,
		long groupId, long layoutPageTemplateCollectionId, String name,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_L_LIKEN_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_L_LIKEN_LAYOUTPAGETEMPLATECOLLECTIONID_2);

		boolean bindName = false;

		if (name.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_L_LIKEN_NAME_3);
		}
		else {
			bindName = true;

			sb.append(_FINDER_COLUMN_G_L_LIKEN_NAME_2);
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
			sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(layoutPageTemplateCollectionId);

		if (bindName) {
			queryPos.add(name);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutPageTemplateEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<LayoutPageTemplateEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the layout page template entries that the user has permission to view where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param name the name
	 * @return the matching layout page template entries that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateEntry> filterFindByG_L_LikeN(
		long groupId, long layoutPageTemplateCollectionId, String name) {

		return filterFindByG_L_LikeN(
			groupId, layoutPageTemplateCollectionId, name, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout page template entries that the user has permission to view where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param name the name
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @return the range of matching layout page template entries that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateEntry> filterFindByG_L_LikeN(
		long groupId, long layoutPageTemplateCollectionId, String name,
		int start, int end) {

		return filterFindByG_L_LikeN(
			groupId, layoutPageTemplateCollectionId, name, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout page template entries that the user has permissions to view where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param name the name
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template entries that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateEntry> filterFindByG_L_LikeN(
		long groupId, long layoutPageTemplateCollectionId, String name,
		int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_L_LikeN(
				groupId, layoutPageTemplateCollectionId, name, start, end,
				orderByComparator);
		}

		name = Objects.toString(name, "");

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(6);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_L_LIKEN_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_L_LIKEN_LAYOUTPAGETEMPLATECOLLECTIONID_2);

		boolean bindName = false;

		if (name.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_L_LIKEN_NAME_3);
		}
		else {
			bindName = true;

			sb.append(_FINDER_COLUMN_G_L_LIKEN_NAME_2);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), LayoutPageTemplateEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, LayoutPageTemplateEntryImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, LayoutPageTemplateEntryImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(layoutPageTemplateCollectionId);

			if (bindName) {
				queryPos.add(name);
			}

			return (List<LayoutPageTemplateEntry>)QueryUtil.list(
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
	 * Returns the layout page template entries before and after the current layout page template entry in the ordered set of layout page template entries that the user has permission to view where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and name LIKE &#63;.
	 *
	 * @param layoutPageTemplateEntryId the primary key of the current layout page template entry
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a layout page template entry with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateEntry[] filterFindByG_L_LikeN_PrevAndNext(
			long layoutPageTemplateEntryId, long groupId,
			long layoutPageTemplateCollectionId, String name,
			OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_L_LikeN_PrevAndNext(
				layoutPageTemplateEntryId, groupId,
				layoutPageTemplateCollectionId, name, orderByComparator);
		}

		name = Objects.toString(name, "");

		LayoutPageTemplateEntry layoutPageTemplateEntry = findByPrimaryKey(
			layoutPageTemplateEntryId);

		Session session = null;

		try {
			session = openSession();

			LayoutPageTemplateEntry[] array =
				new LayoutPageTemplateEntryImpl[3];

			array[0] = filterGetByG_L_LikeN_PrevAndNext(
				session, layoutPageTemplateEntry, groupId,
				layoutPageTemplateCollectionId, name, orderByComparator, true);

			array[1] = layoutPageTemplateEntry;

			array[2] = filterGetByG_L_LikeN_PrevAndNext(
				session, layoutPageTemplateEntry, groupId,
				layoutPageTemplateCollectionId, name, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutPageTemplateEntry filterGetByG_L_LikeN_PrevAndNext(
		Session session, LayoutPageTemplateEntry layoutPageTemplateEntry,
		long groupId, long layoutPageTemplateCollectionId, String name,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator,
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
			sb.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_L_LIKEN_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_L_LIKEN_LAYOUTPAGETEMPLATECOLLECTIONID_2);

		boolean bindName = false;

		if (name.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_L_LIKEN_NAME_3);
		}
		else {
			bindName = true;

			sb.append(_FINDER_COLUMN_G_L_LIKEN_NAME_2);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), LayoutPageTemplateEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(
				_FILTER_ENTITY_ALIAS, LayoutPageTemplateEntryImpl.class);
		}
		else {
			sqlQuery.addEntity(
				_FILTER_ENTITY_TABLE, LayoutPageTemplateEntryImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(groupId);

		queryPos.add(layoutPageTemplateCollectionId);

		if (bindName) {
			queryPos.add(name);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutPageTemplateEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<LayoutPageTemplateEntry> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout page template entries where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and name LIKE &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param name the name
	 */
	@Override
	public void removeByG_L_LikeN(
		long groupId, long layoutPageTemplateCollectionId, String name) {

		for (LayoutPageTemplateEntry layoutPageTemplateEntry :
				findByG_L_LikeN(
					groupId, layoutPageTemplateCollectionId, name,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(layoutPageTemplateEntry);
		}
	}

	/**
	 * Returns the number of layout page template entries where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param name the name
	 * @return the number of matching layout page template entries
	 */
	@Override
	public int countByG_L_LikeN(
		long groupId, long layoutPageTemplateCollectionId, String name) {

		name = Objects.toString(name, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			LayoutPageTemplateEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathWithPaginationCountByG_L_LikeN;

			finderArgs = new Object[] {
				groupId, layoutPageTemplateCollectionId, name
			};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_LAYOUTPAGETEMPLATEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_L_LIKEN_GROUPID_2);

			sb.append(
				_FINDER_COLUMN_G_L_LIKEN_LAYOUTPAGETEMPLATECOLLECTIONID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_L_LIKEN_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_G_L_LIKEN_NAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(layoutPageTemplateCollectionId);

				if (bindName) {
					queryPos.add(name);
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
	 * Returns the number of layout page template entries that the user has permission to view where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param name the name
	 * @return the number of matching layout page template entries that the user has permission to view
	 */
	@Override
	public int filterCountByG_L_LikeN(
		long groupId, long layoutPageTemplateCollectionId, String name) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_L_LikeN(
				groupId, layoutPageTemplateCollectionId, name);
		}

		name = Objects.toString(name, "");

		StringBundler sb = new StringBundler(4);

		sb.append(_FILTER_SQL_COUNT_LAYOUTPAGETEMPLATEENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_L_LIKEN_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_L_LIKEN_LAYOUTPAGETEMPLATECOLLECTIONID_2);

		boolean bindName = false;

		if (name.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_L_LIKEN_NAME_3);
		}
		else {
			bindName = true;

			sb.append(_FINDER_COLUMN_G_L_LIKEN_NAME_2);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), LayoutPageTemplateEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(layoutPageTemplateCollectionId);

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

	private static final String _FINDER_COLUMN_G_L_LIKEN_GROUPID_2 =
		"layoutPageTemplateEntry.groupId = ? AND ";

	private static final String
		_FINDER_COLUMN_G_L_LIKEN_LAYOUTPAGETEMPLATECOLLECTIONID_2 =
			"layoutPageTemplateEntry.layoutPageTemplateCollectionId = ? AND ";

	private static final String _FINDER_COLUMN_G_L_LIKEN_NAME_2 =
		"layoutPageTemplateEntry.name LIKE ?";

	private static final String _FINDER_COLUMN_G_L_LIKEN_NAME_3 =
		"(layoutPageTemplateEntry.name IS NULL OR layoutPageTemplateEntry.name LIKE '')";

	private FinderPath _finderPathWithPaginationFindByG_L_T;
	private FinderPath _finderPathWithoutPaginationFindByG_L_T;
	private FinderPath _finderPathCountByG_L_T;

	/**
	 * Returns all the layout page template entries where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param type the type
	 * @return the matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_L_T(
		long groupId, long layoutPageTemplateCollectionId, int type) {

		return findByG_L_T(
			groupId, layoutPageTemplateCollectionId, type, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout page template entries where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param type the type
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @return the range of matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_L_T(
		long groupId, long layoutPageTemplateCollectionId, int type, int start,
		int end) {

		return findByG_L_T(
			groupId, layoutPageTemplateCollectionId, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout page template entries where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param type the type
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_L_T(
		long groupId, long layoutPageTemplateCollectionId, int type, int start,
		int end, OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		return findByG_L_T(
			groupId, layoutPageTemplateCollectionId, type, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout page template entries where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param type the type
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_L_T(
		long groupId, long layoutPageTemplateCollectionId, int type, int start,
		int end, OrderByComparator<LayoutPageTemplateEntry> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			LayoutPageTemplateEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByG_L_T;
				finderArgs = new Object[] {
					groupId, layoutPageTemplateCollectionId, type
				};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByG_L_T;
			finderArgs = new Object[] {
				groupId, layoutPageTemplateCollectionId, type, start, end,
				orderByComparator
			};
		}

		List<LayoutPageTemplateEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<LayoutPageTemplateEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutPageTemplateEntry layoutPageTemplateEntry : list) {
					if ((groupId != layoutPageTemplateEntry.getGroupId()) ||
						(layoutPageTemplateCollectionId !=
							layoutPageTemplateEntry.
								getLayoutPageTemplateCollectionId()) ||
						(type != layoutPageTemplateEntry.getType())) {

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

			sb.append(_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_L_T_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_L_T_LAYOUTPAGETEMPLATECOLLECTIONID_2);

			sb.append(_FINDER_COLUMN_G_L_T_TYPE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(layoutPageTemplateCollectionId);

				queryPos.add(type);

				list = (List<LayoutPageTemplateEntry>)QueryUtil.list(
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
	 * Returns the first layout page template entry in the ordered set where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry findByG_L_T_First(
			long groupId, long layoutPageTemplateCollectionId, int type,
			OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {

		LayoutPageTemplateEntry layoutPageTemplateEntry = fetchByG_L_T_First(
			groupId, layoutPageTemplateCollectionId, type, orderByComparator);

		if (layoutPageTemplateEntry != null) {
			return layoutPageTemplateEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", layoutPageTemplateCollectionId=");
		sb.append(layoutPageTemplateCollectionId);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchPageTemplateEntryException(sb.toString());
	}

	/**
	 * Returns the first layout page template entry in the ordered set where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template entry, or <code>null</code> if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry fetchByG_L_T_First(
		long groupId, long layoutPageTemplateCollectionId, int type,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		List<LayoutPageTemplateEntry> list = findByG_L_T(
			groupId, layoutPageTemplateCollectionId, type, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout page template entry in the ordered set where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry findByG_L_T_Last(
			long groupId, long layoutPageTemplateCollectionId, int type,
			OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {

		LayoutPageTemplateEntry layoutPageTemplateEntry = fetchByG_L_T_Last(
			groupId, layoutPageTemplateCollectionId, type, orderByComparator);

		if (layoutPageTemplateEntry != null) {
			return layoutPageTemplateEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", layoutPageTemplateCollectionId=");
		sb.append(layoutPageTemplateCollectionId);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchPageTemplateEntryException(sb.toString());
	}

	/**
	 * Returns the last layout page template entry in the ordered set where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template entry, or <code>null</code> if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry fetchByG_L_T_Last(
		long groupId, long layoutPageTemplateCollectionId, int type,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		int count = countByG_L_T(groupId, layoutPageTemplateCollectionId, type);

		if (count == 0) {
			return null;
		}

		List<LayoutPageTemplateEntry> list = findByG_L_T(
			groupId, layoutPageTemplateCollectionId, type, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout page template entries before and after the current layout page template entry in the ordered set where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and type = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the primary key of the current layout page template entry
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a layout page template entry with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateEntry[] findByG_L_T_PrevAndNext(
			long layoutPageTemplateEntryId, long groupId,
			long layoutPageTemplateCollectionId, int type,
			OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {

		LayoutPageTemplateEntry layoutPageTemplateEntry = findByPrimaryKey(
			layoutPageTemplateEntryId);

		Session session = null;

		try {
			session = openSession();

			LayoutPageTemplateEntry[] array =
				new LayoutPageTemplateEntryImpl[3];

			array[0] = getByG_L_T_PrevAndNext(
				session, layoutPageTemplateEntry, groupId,
				layoutPageTemplateCollectionId, type, orderByComparator, true);

			array[1] = layoutPageTemplateEntry;

			array[2] = getByG_L_T_PrevAndNext(
				session, layoutPageTemplateEntry, groupId,
				layoutPageTemplateCollectionId, type, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutPageTemplateEntry getByG_L_T_PrevAndNext(
		Session session, LayoutPageTemplateEntry layoutPageTemplateEntry,
		long groupId, long layoutPageTemplateCollectionId, int type,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_L_T_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_L_T_LAYOUTPAGETEMPLATECOLLECTIONID_2);

		sb.append(_FINDER_COLUMN_G_L_T_TYPE_2);

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
			sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(layoutPageTemplateCollectionId);

		queryPos.add(type);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutPageTemplateEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<LayoutPageTemplateEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the layout page template entries that the user has permission to view where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param type the type
	 * @return the matching layout page template entries that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateEntry> filterFindByG_L_T(
		long groupId, long layoutPageTemplateCollectionId, int type) {

		return filterFindByG_L_T(
			groupId, layoutPageTemplateCollectionId, type, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout page template entries that the user has permission to view where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param type the type
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @return the range of matching layout page template entries that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateEntry> filterFindByG_L_T(
		long groupId, long layoutPageTemplateCollectionId, int type, int start,
		int end) {

		return filterFindByG_L_T(
			groupId, layoutPageTemplateCollectionId, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout page template entries that the user has permissions to view where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param type the type
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template entries that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateEntry> filterFindByG_L_T(
		long groupId, long layoutPageTemplateCollectionId, int type, int start,
		int end, OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_L_T(
				groupId, layoutPageTemplateCollectionId, type, start, end,
				orderByComparator);
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
			sb.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_L_T_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_L_T_LAYOUTPAGETEMPLATECOLLECTIONID_2);

		sb.append(_FINDER_COLUMN_G_L_T_TYPE_2_SQL);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), LayoutPageTemplateEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, LayoutPageTemplateEntryImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, LayoutPageTemplateEntryImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(layoutPageTemplateCollectionId);

			queryPos.add(type);

			return (List<LayoutPageTemplateEntry>)QueryUtil.list(
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
	 * Returns the layout page template entries before and after the current layout page template entry in the ordered set of layout page template entries that the user has permission to view where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and type = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the primary key of the current layout page template entry
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a layout page template entry with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateEntry[] filterFindByG_L_T_PrevAndNext(
			long layoutPageTemplateEntryId, long groupId,
			long layoutPageTemplateCollectionId, int type,
			OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_L_T_PrevAndNext(
				layoutPageTemplateEntryId, groupId,
				layoutPageTemplateCollectionId, type, orderByComparator);
		}

		LayoutPageTemplateEntry layoutPageTemplateEntry = findByPrimaryKey(
			layoutPageTemplateEntryId);

		Session session = null;

		try {
			session = openSession();

			LayoutPageTemplateEntry[] array =
				new LayoutPageTemplateEntryImpl[3];

			array[0] = filterGetByG_L_T_PrevAndNext(
				session, layoutPageTemplateEntry, groupId,
				layoutPageTemplateCollectionId, type, orderByComparator, true);

			array[1] = layoutPageTemplateEntry;

			array[2] = filterGetByG_L_T_PrevAndNext(
				session, layoutPageTemplateEntry, groupId,
				layoutPageTemplateCollectionId, type, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutPageTemplateEntry filterGetByG_L_T_PrevAndNext(
		Session session, LayoutPageTemplateEntry layoutPageTemplateEntry,
		long groupId, long layoutPageTemplateCollectionId, int type,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator,
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
			sb.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_L_T_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_L_T_LAYOUTPAGETEMPLATECOLLECTIONID_2);

		sb.append(_FINDER_COLUMN_G_L_T_TYPE_2_SQL);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), LayoutPageTemplateEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(
				_FILTER_ENTITY_ALIAS, LayoutPageTemplateEntryImpl.class);
		}
		else {
			sqlQuery.addEntity(
				_FILTER_ENTITY_TABLE, LayoutPageTemplateEntryImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(groupId);

		queryPos.add(layoutPageTemplateCollectionId);

		queryPos.add(type);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutPageTemplateEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<LayoutPageTemplateEntry> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout page template entries where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and type = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param type the type
	 */
	@Override
	public void removeByG_L_T(
		long groupId, long layoutPageTemplateCollectionId, int type) {

		for (LayoutPageTemplateEntry layoutPageTemplateEntry :
				findByG_L_T(
					groupId, layoutPageTemplateCollectionId, type,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(layoutPageTemplateEntry);
		}
	}

	/**
	 * Returns the number of layout page template entries where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param type the type
	 * @return the number of matching layout page template entries
	 */
	@Override
	public int countByG_L_T(
		long groupId, long layoutPageTemplateCollectionId, int type) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			LayoutPageTemplateEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_L_T;

			finderArgs = new Object[] {
				groupId, layoutPageTemplateCollectionId, type
			};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_LAYOUTPAGETEMPLATEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_L_T_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_L_T_LAYOUTPAGETEMPLATECOLLECTIONID_2);

			sb.append(_FINDER_COLUMN_G_L_T_TYPE_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(layoutPageTemplateCollectionId);

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
	 * Returns the number of layout page template entries that the user has permission to view where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param type the type
	 * @return the number of matching layout page template entries that the user has permission to view
	 */
	@Override
	public int filterCountByG_L_T(
		long groupId, long layoutPageTemplateCollectionId, int type) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_L_T(groupId, layoutPageTemplateCollectionId, type);
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_FILTER_SQL_COUNT_LAYOUTPAGETEMPLATEENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_L_T_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_L_T_LAYOUTPAGETEMPLATECOLLECTIONID_2);

		sb.append(_FINDER_COLUMN_G_L_T_TYPE_2_SQL);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), LayoutPageTemplateEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(layoutPageTemplateCollectionId);

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

	private static final String _FINDER_COLUMN_G_L_T_GROUPID_2 =
		"layoutPageTemplateEntry.groupId = ? AND ";

	private static final String
		_FINDER_COLUMN_G_L_T_LAYOUTPAGETEMPLATECOLLECTIONID_2 =
			"layoutPageTemplateEntry.layoutPageTemplateCollectionId = ? AND ";

	private static final String _FINDER_COLUMN_G_L_T_TYPE_2 =
		"layoutPageTemplateEntry.type = ?";

	private static final String _FINDER_COLUMN_G_L_T_TYPE_2_SQL =
		"layoutPageTemplateEntry.type_ = ?";

	private FinderPath _finderPathWithPaginationFindByG_L_S;
	private FinderPath _finderPathWithoutPaginationFindByG_L_S;
	private FinderPath _finderPathCountByG_L_S;

	/**
	 * Returns all the layout page template entries where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param status the status
	 * @return the matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_L_S(
		long groupId, long layoutPageTemplateCollectionId, int status) {

		return findByG_L_S(
			groupId, layoutPageTemplateCollectionId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout page template entries where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param status the status
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @return the range of matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_L_S(
		long groupId, long layoutPageTemplateCollectionId, int status,
		int start, int end) {

		return findByG_L_S(
			groupId, layoutPageTemplateCollectionId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout page template entries where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param status the status
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_L_S(
		long groupId, long layoutPageTemplateCollectionId, int status,
		int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		return findByG_L_S(
			groupId, layoutPageTemplateCollectionId, status, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout page template entries where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param status the status
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_L_S(
		long groupId, long layoutPageTemplateCollectionId, int status,
		int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			LayoutPageTemplateEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByG_L_S;
				finderArgs = new Object[] {
					groupId, layoutPageTemplateCollectionId, status
				};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByG_L_S;
			finderArgs = new Object[] {
				groupId, layoutPageTemplateCollectionId, status, start, end,
				orderByComparator
			};
		}

		List<LayoutPageTemplateEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<LayoutPageTemplateEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutPageTemplateEntry layoutPageTemplateEntry : list) {
					if ((groupId != layoutPageTemplateEntry.getGroupId()) ||
						(layoutPageTemplateCollectionId !=
							layoutPageTemplateEntry.
								getLayoutPageTemplateCollectionId()) ||
						(status != layoutPageTemplateEntry.getStatus())) {

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

			sb.append(_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_L_S_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_L_S_LAYOUTPAGETEMPLATECOLLECTIONID_2);

			sb.append(_FINDER_COLUMN_G_L_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(layoutPageTemplateCollectionId);

				queryPos.add(status);

				list = (List<LayoutPageTemplateEntry>)QueryUtil.list(
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
	 * Returns the first layout page template entry in the ordered set where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry findByG_L_S_First(
			long groupId, long layoutPageTemplateCollectionId, int status,
			OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {

		LayoutPageTemplateEntry layoutPageTemplateEntry = fetchByG_L_S_First(
			groupId, layoutPageTemplateCollectionId, status, orderByComparator);

		if (layoutPageTemplateEntry != null) {
			return layoutPageTemplateEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", layoutPageTemplateCollectionId=");
		sb.append(layoutPageTemplateCollectionId);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchPageTemplateEntryException(sb.toString());
	}

	/**
	 * Returns the first layout page template entry in the ordered set where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template entry, or <code>null</code> if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry fetchByG_L_S_First(
		long groupId, long layoutPageTemplateCollectionId, int status,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		List<LayoutPageTemplateEntry> list = findByG_L_S(
			groupId, layoutPageTemplateCollectionId, status, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout page template entry in the ordered set where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry findByG_L_S_Last(
			long groupId, long layoutPageTemplateCollectionId, int status,
			OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {

		LayoutPageTemplateEntry layoutPageTemplateEntry = fetchByG_L_S_Last(
			groupId, layoutPageTemplateCollectionId, status, orderByComparator);

		if (layoutPageTemplateEntry != null) {
			return layoutPageTemplateEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", layoutPageTemplateCollectionId=");
		sb.append(layoutPageTemplateCollectionId);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchPageTemplateEntryException(sb.toString());
	}

	/**
	 * Returns the last layout page template entry in the ordered set where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template entry, or <code>null</code> if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry fetchByG_L_S_Last(
		long groupId, long layoutPageTemplateCollectionId, int status,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		int count = countByG_L_S(
			groupId, layoutPageTemplateCollectionId, status);

		if (count == 0) {
			return null;
		}

		List<LayoutPageTemplateEntry> list = findByG_L_S(
			groupId, layoutPageTemplateCollectionId, status, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout page template entries before and after the current layout page template entry in the ordered set where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and status = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the primary key of the current layout page template entry
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a layout page template entry with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateEntry[] findByG_L_S_PrevAndNext(
			long layoutPageTemplateEntryId, long groupId,
			long layoutPageTemplateCollectionId, int status,
			OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {

		LayoutPageTemplateEntry layoutPageTemplateEntry = findByPrimaryKey(
			layoutPageTemplateEntryId);

		Session session = null;

		try {
			session = openSession();

			LayoutPageTemplateEntry[] array =
				new LayoutPageTemplateEntryImpl[3];

			array[0] = getByG_L_S_PrevAndNext(
				session, layoutPageTemplateEntry, groupId,
				layoutPageTemplateCollectionId, status, orderByComparator,
				true);

			array[1] = layoutPageTemplateEntry;

			array[2] = getByG_L_S_PrevAndNext(
				session, layoutPageTemplateEntry, groupId,
				layoutPageTemplateCollectionId, status, orderByComparator,
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

	protected LayoutPageTemplateEntry getByG_L_S_PrevAndNext(
		Session session, LayoutPageTemplateEntry layoutPageTemplateEntry,
		long groupId, long layoutPageTemplateCollectionId, int status,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_L_S_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_L_S_LAYOUTPAGETEMPLATECOLLECTIONID_2);

		sb.append(_FINDER_COLUMN_G_L_S_STATUS_2);

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
			sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(layoutPageTemplateCollectionId);

		queryPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutPageTemplateEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<LayoutPageTemplateEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the layout page template entries that the user has permission to view where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param status the status
	 * @return the matching layout page template entries that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateEntry> filterFindByG_L_S(
		long groupId, long layoutPageTemplateCollectionId, int status) {

		return filterFindByG_L_S(
			groupId, layoutPageTemplateCollectionId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout page template entries that the user has permission to view where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param status the status
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @return the range of matching layout page template entries that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateEntry> filterFindByG_L_S(
		long groupId, long layoutPageTemplateCollectionId, int status,
		int start, int end) {

		return filterFindByG_L_S(
			groupId, layoutPageTemplateCollectionId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout page template entries that the user has permissions to view where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param status the status
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template entries that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateEntry> filterFindByG_L_S(
		long groupId, long layoutPageTemplateCollectionId, int status,
		int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_L_S(
				groupId, layoutPageTemplateCollectionId, status, start, end,
				orderByComparator);
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
			sb.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_L_S_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_L_S_LAYOUTPAGETEMPLATECOLLECTIONID_2);

		sb.append(_FINDER_COLUMN_G_L_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), LayoutPageTemplateEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, LayoutPageTemplateEntryImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, LayoutPageTemplateEntryImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(layoutPageTemplateCollectionId);

			queryPos.add(status);

			return (List<LayoutPageTemplateEntry>)QueryUtil.list(
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
	 * Returns the layout page template entries before and after the current layout page template entry in the ordered set of layout page template entries that the user has permission to view where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and status = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the primary key of the current layout page template entry
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a layout page template entry with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateEntry[] filterFindByG_L_S_PrevAndNext(
			long layoutPageTemplateEntryId, long groupId,
			long layoutPageTemplateCollectionId, int status,
			OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_L_S_PrevAndNext(
				layoutPageTemplateEntryId, groupId,
				layoutPageTemplateCollectionId, status, orderByComparator);
		}

		LayoutPageTemplateEntry layoutPageTemplateEntry = findByPrimaryKey(
			layoutPageTemplateEntryId);

		Session session = null;

		try {
			session = openSession();

			LayoutPageTemplateEntry[] array =
				new LayoutPageTemplateEntryImpl[3];

			array[0] = filterGetByG_L_S_PrevAndNext(
				session, layoutPageTemplateEntry, groupId,
				layoutPageTemplateCollectionId, status, orderByComparator,
				true);

			array[1] = layoutPageTemplateEntry;

			array[2] = filterGetByG_L_S_PrevAndNext(
				session, layoutPageTemplateEntry, groupId,
				layoutPageTemplateCollectionId, status, orderByComparator,
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

	protected LayoutPageTemplateEntry filterGetByG_L_S_PrevAndNext(
		Session session, LayoutPageTemplateEntry layoutPageTemplateEntry,
		long groupId, long layoutPageTemplateCollectionId, int status,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator,
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
			sb.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_L_S_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_L_S_LAYOUTPAGETEMPLATECOLLECTIONID_2);

		sb.append(_FINDER_COLUMN_G_L_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), LayoutPageTemplateEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(
				_FILTER_ENTITY_ALIAS, LayoutPageTemplateEntryImpl.class);
		}
		else {
			sqlQuery.addEntity(
				_FILTER_ENTITY_TABLE, LayoutPageTemplateEntryImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(groupId);

		queryPos.add(layoutPageTemplateCollectionId);

		queryPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutPageTemplateEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<LayoutPageTemplateEntry> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout page template entries where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param status the status
	 */
	@Override
	public void removeByG_L_S(
		long groupId, long layoutPageTemplateCollectionId, int status) {

		for (LayoutPageTemplateEntry layoutPageTemplateEntry :
				findByG_L_S(
					groupId, layoutPageTemplateCollectionId, status,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(layoutPageTemplateEntry);
		}
	}

	/**
	 * Returns the number of layout page template entries where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param status the status
	 * @return the number of matching layout page template entries
	 */
	@Override
	public int countByG_L_S(
		long groupId, long layoutPageTemplateCollectionId, int status) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			LayoutPageTemplateEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_L_S;

			finderArgs = new Object[] {
				groupId, layoutPageTemplateCollectionId, status
			};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_LAYOUTPAGETEMPLATEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_L_S_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_L_S_LAYOUTPAGETEMPLATECOLLECTIONID_2);

			sb.append(_FINDER_COLUMN_G_L_S_STATUS_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(layoutPageTemplateCollectionId);

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
	 * Returns the number of layout page template entries that the user has permission to view where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param status the status
	 * @return the number of matching layout page template entries that the user has permission to view
	 */
	@Override
	public int filterCountByG_L_S(
		long groupId, long layoutPageTemplateCollectionId, int status) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_L_S(
				groupId, layoutPageTemplateCollectionId, status);
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_FILTER_SQL_COUNT_LAYOUTPAGETEMPLATEENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_L_S_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_L_S_LAYOUTPAGETEMPLATECOLLECTIONID_2);

		sb.append(_FINDER_COLUMN_G_L_S_STATUS_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), LayoutPageTemplateEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(layoutPageTemplateCollectionId);

			queryPos.add(status);

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

	private static final String _FINDER_COLUMN_G_L_S_GROUPID_2 =
		"layoutPageTemplateEntry.groupId = ? AND ";

	private static final String
		_FINDER_COLUMN_G_L_S_LAYOUTPAGETEMPLATECOLLECTIONID_2 =
			"layoutPageTemplateEntry.layoutPageTemplateCollectionId = ? AND ";

	private static final String _FINDER_COLUMN_G_L_S_STATUS_2 =
		"layoutPageTemplateEntry.status = ?";

	private FinderPath _finderPathFetchByG_N_T;
	private FinderPath _finderPathCountByG_N_T;

	/**
	 * Returns the layout page template entry where groupId = &#63; and name = &#63; and type = &#63; or throws a <code>NoSuchPageTemplateEntryException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param type the type
	 * @return the matching layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry findByG_N_T(
			long groupId, String name, int type)
		throws NoSuchPageTemplateEntryException {

		LayoutPageTemplateEntry layoutPageTemplateEntry = fetchByG_N_T(
			groupId, name, type);

		if (layoutPageTemplateEntry == null) {
			StringBundler sb = new StringBundler(8);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("groupId=");
			sb.append(groupId);

			sb.append(", name=");
			sb.append(name);

			sb.append(", type=");
			sb.append(type);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchPageTemplateEntryException(sb.toString());
		}

		return layoutPageTemplateEntry;
	}

	/**
	 * Returns the layout page template entry where groupId = &#63; and name = &#63; and type = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param type the type
	 * @return the matching layout page template entry, or <code>null</code> if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry fetchByG_N_T(
		long groupId, String name, int type) {

		return fetchByG_N_T(groupId, name, type, true);
	}

	/**
	 * Returns the layout page template entry where groupId = &#63; and name = &#63; and type = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param type the type
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching layout page template entry, or <code>null</code> if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry fetchByG_N_T(
		long groupId, String name, int type, boolean useFinderCache) {

		name = Objects.toString(name, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			LayoutPageTemplateEntry.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {groupId, name, type};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(
				_finderPathFetchByG_N_T, finderArgs, this);
		}

		if (result instanceof LayoutPageTemplateEntry) {
			LayoutPageTemplateEntry layoutPageTemplateEntry =
				(LayoutPageTemplateEntry)result;

			if ((groupId != layoutPageTemplateEntry.getGroupId()) ||
				!Objects.equals(name, layoutPageTemplateEntry.getName()) ||
				(type != layoutPageTemplateEntry.getType())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_N_T_GROUPID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_N_T_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_G_N_T_NAME_2);
			}

			sb.append(_FINDER_COLUMN_G_N_T_TYPE_2);

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

				queryPos.add(type);

				List<LayoutPageTemplateEntry> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByG_N_T, finderArgs, list);
					}
				}
				else {
					LayoutPageTemplateEntry layoutPageTemplateEntry = list.get(
						0);

					result = layoutPageTemplateEntry;

					cacheResult(layoutPageTemplateEntry);
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
			return (LayoutPageTemplateEntry)result;
		}
	}

	/**
	 * Removes the layout page template entry where groupId = &#63; and name = &#63; and type = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param type the type
	 * @return the layout page template entry that was removed
	 */
	@Override
	public LayoutPageTemplateEntry removeByG_N_T(
			long groupId, String name, int type)
		throws NoSuchPageTemplateEntryException {

		LayoutPageTemplateEntry layoutPageTemplateEntry = findByG_N_T(
			groupId, name, type);

		return remove(layoutPageTemplateEntry);
	}

	/**
	 * Returns the number of layout page template entries where groupId = &#63; and name = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param type the type
	 * @return the number of matching layout page template entries
	 */
	@Override
	public int countByG_N_T(long groupId, String name, int type) {
		name = Objects.toString(name, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			LayoutPageTemplateEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_N_T;

			finderArgs = new Object[] {groupId, name, type};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_LAYOUTPAGETEMPLATEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_N_T_GROUPID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_N_T_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_G_N_T_NAME_2);
			}

			sb.append(_FINDER_COLUMN_G_N_T_TYPE_2);

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

	private static final String _FINDER_COLUMN_G_N_T_GROUPID_2 =
		"layoutPageTemplateEntry.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_N_T_NAME_2 =
		"layoutPageTemplateEntry.name = ? AND ";

	private static final String _FINDER_COLUMN_G_N_T_NAME_3 =
		"(layoutPageTemplateEntry.name IS NULL OR layoutPageTemplateEntry.name = '') AND ";

	private static final String _FINDER_COLUMN_G_N_T_TYPE_2 =
		"layoutPageTemplateEntry.type = ?";

	private FinderPath _finderPathWithPaginationFindByG_T_LikeN;
	private FinderPath _finderPathWithPaginationCountByG_T_LikeN;

	/**
	 * Returns all the layout page template entries where groupId = &#63; and name LIKE &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param type the type
	 * @return the matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_T_LikeN(
		long groupId, String name, int type) {

		return findByG_T_LikeN(
			groupId, name, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout page template entries where groupId = &#63; and name LIKE &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param type the type
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @return the range of matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_T_LikeN(
		long groupId, String name, int type, int start, int end) {

		return findByG_T_LikeN(groupId, name, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout page template entries where groupId = &#63; and name LIKE &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param type the type
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_T_LikeN(
		long groupId, String name, int type, int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		return findByG_T_LikeN(
			groupId, name, type, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout page template entries where groupId = &#63; and name LIKE &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param type the type
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_T_LikeN(
		long groupId, String name, int type, int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator,
		boolean useFinderCache) {

		name = Objects.toString(name, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			LayoutPageTemplateEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByG_T_LikeN;
		finderArgs = new Object[] {
			groupId, name, type, start, end, orderByComparator
		};

		List<LayoutPageTemplateEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<LayoutPageTemplateEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutPageTemplateEntry layoutPageTemplateEntry : list) {
					if ((groupId != layoutPageTemplateEntry.getGroupId()) ||
						!StringUtil.wildcardMatches(
							layoutPageTemplateEntry.getName(), name, '_', '%',
							'\\', true) ||
						(type != layoutPageTemplateEntry.getType())) {

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

			sb.append(_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_T_LIKEN_GROUPID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_T_LIKEN_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_G_T_LIKEN_NAME_2);
			}

			sb.append(_FINDER_COLUMN_G_T_LIKEN_TYPE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
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

				queryPos.add(type);

				list = (List<LayoutPageTemplateEntry>)QueryUtil.list(
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
	 * Returns the first layout page template entry in the ordered set where groupId = &#63; and name LIKE &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry findByG_T_LikeN_First(
			long groupId, String name, int type,
			OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			fetchByG_T_LikeN_First(groupId, name, type, orderByComparator);

		if (layoutPageTemplateEntry != null) {
			return layoutPageTemplateEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", nameLIKE");
		sb.append(name);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchPageTemplateEntryException(sb.toString());
	}

	/**
	 * Returns the first layout page template entry in the ordered set where groupId = &#63; and name LIKE &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template entry, or <code>null</code> if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry fetchByG_T_LikeN_First(
		long groupId, String name, int type,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		List<LayoutPageTemplateEntry> list = findByG_T_LikeN(
			groupId, name, type, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout page template entry in the ordered set where groupId = &#63; and name LIKE &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry findByG_T_LikeN_Last(
			long groupId, String name, int type,
			OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {

		LayoutPageTemplateEntry layoutPageTemplateEntry = fetchByG_T_LikeN_Last(
			groupId, name, type, orderByComparator);

		if (layoutPageTemplateEntry != null) {
			return layoutPageTemplateEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", nameLIKE");
		sb.append(name);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchPageTemplateEntryException(sb.toString());
	}

	/**
	 * Returns the last layout page template entry in the ordered set where groupId = &#63; and name LIKE &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template entry, or <code>null</code> if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry fetchByG_T_LikeN_Last(
		long groupId, String name, int type,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		int count = countByG_T_LikeN(groupId, name, type);

		if (count == 0) {
			return null;
		}

		List<LayoutPageTemplateEntry> list = findByG_T_LikeN(
			groupId, name, type, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout page template entries before and after the current layout page template entry in the ordered set where groupId = &#63; and name LIKE &#63; and type = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the primary key of the current layout page template entry
	 * @param groupId the group ID
	 * @param name the name
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a layout page template entry with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateEntry[] findByG_T_LikeN_PrevAndNext(
			long layoutPageTemplateEntryId, long groupId, String name, int type,
			OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {

		name = Objects.toString(name, "");

		LayoutPageTemplateEntry layoutPageTemplateEntry = findByPrimaryKey(
			layoutPageTemplateEntryId);

		Session session = null;

		try {
			session = openSession();

			LayoutPageTemplateEntry[] array =
				new LayoutPageTemplateEntryImpl[3];

			array[0] = getByG_T_LikeN_PrevAndNext(
				session, layoutPageTemplateEntry, groupId, name, type,
				orderByComparator, true);

			array[1] = layoutPageTemplateEntry;

			array[2] = getByG_T_LikeN_PrevAndNext(
				session, layoutPageTemplateEntry, groupId, name, type,
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

	protected LayoutPageTemplateEntry getByG_T_LikeN_PrevAndNext(
		Session session, LayoutPageTemplateEntry layoutPageTemplateEntry,
		long groupId, String name, int type,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_T_LIKEN_GROUPID_2);

		boolean bindName = false;

		if (name.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_T_LIKEN_NAME_3);
		}
		else {
			bindName = true;

			sb.append(_FINDER_COLUMN_G_T_LIKEN_NAME_2);
		}

		sb.append(_FINDER_COLUMN_G_T_LIKEN_TYPE_2);

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
			sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
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

		queryPos.add(type);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutPageTemplateEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<LayoutPageTemplateEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the layout page template entries that the user has permission to view where groupId = &#63; and name LIKE &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param type the type
	 * @return the matching layout page template entries that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateEntry> filterFindByG_T_LikeN(
		long groupId, String name, int type) {

		return filterFindByG_T_LikeN(
			groupId, name, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout page template entries that the user has permission to view where groupId = &#63; and name LIKE &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param type the type
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @return the range of matching layout page template entries that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateEntry> filterFindByG_T_LikeN(
		long groupId, String name, int type, int start, int end) {

		return filterFindByG_T_LikeN(groupId, name, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout page template entries that the user has permissions to view where groupId = &#63; and name LIKE &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param type the type
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template entries that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateEntry> filterFindByG_T_LikeN(
		long groupId, String name, int type, int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_T_LikeN(
				groupId, name, type, start, end, orderByComparator);
		}

		name = Objects.toString(name, "");

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(6);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_T_LIKEN_GROUPID_2);

		boolean bindName = false;

		if (name.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_T_LIKEN_NAME_3);
		}
		else {
			bindName = true;

			sb.append(_FINDER_COLUMN_G_T_LIKEN_NAME_2);
		}

		sb.append(_FINDER_COLUMN_G_T_LIKEN_TYPE_2_SQL);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), LayoutPageTemplateEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, LayoutPageTemplateEntryImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, LayoutPageTemplateEntryImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			if (bindName) {
				queryPos.add(name);
			}

			queryPos.add(type);

			return (List<LayoutPageTemplateEntry>)QueryUtil.list(
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
	 * Returns the layout page template entries before and after the current layout page template entry in the ordered set of layout page template entries that the user has permission to view where groupId = &#63; and name LIKE &#63; and type = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the primary key of the current layout page template entry
	 * @param groupId the group ID
	 * @param name the name
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a layout page template entry with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateEntry[] filterFindByG_T_LikeN_PrevAndNext(
			long layoutPageTemplateEntryId, long groupId, String name, int type,
			OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_T_LikeN_PrevAndNext(
				layoutPageTemplateEntryId, groupId, name, type,
				orderByComparator);
		}

		name = Objects.toString(name, "");

		LayoutPageTemplateEntry layoutPageTemplateEntry = findByPrimaryKey(
			layoutPageTemplateEntryId);

		Session session = null;

		try {
			session = openSession();

			LayoutPageTemplateEntry[] array =
				new LayoutPageTemplateEntryImpl[3];

			array[0] = filterGetByG_T_LikeN_PrevAndNext(
				session, layoutPageTemplateEntry, groupId, name, type,
				orderByComparator, true);

			array[1] = layoutPageTemplateEntry;

			array[2] = filterGetByG_T_LikeN_PrevAndNext(
				session, layoutPageTemplateEntry, groupId, name, type,
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

	protected LayoutPageTemplateEntry filterGetByG_T_LikeN_PrevAndNext(
		Session session, LayoutPageTemplateEntry layoutPageTemplateEntry,
		long groupId, String name, int type,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator,
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
			sb.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_T_LIKEN_GROUPID_2);

		boolean bindName = false;

		if (name.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_T_LIKEN_NAME_3);
		}
		else {
			bindName = true;

			sb.append(_FINDER_COLUMN_G_T_LIKEN_NAME_2);
		}

		sb.append(_FINDER_COLUMN_G_T_LIKEN_TYPE_2_SQL);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), LayoutPageTemplateEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(
				_FILTER_ENTITY_ALIAS, LayoutPageTemplateEntryImpl.class);
		}
		else {
			sqlQuery.addEntity(
				_FILTER_ENTITY_TABLE, LayoutPageTemplateEntryImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(groupId);

		if (bindName) {
			queryPos.add(name);
		}

		queryPos.add(type);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutPageTemplateEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<LayoutPageTemplateEntry> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout page template entries where groupId = &#63; and name LIKE &#63; and type = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param type the type
	 */
	@Override
	public void removeByG_T_LikeN(long groupId, String name, int type) {
		for (LayoutPageTemplateEntry layoutPageTemplateEntry :
				findByG_T_LikeN(
					groupId, name, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(layoutPageTemplateEntry);
		}
	}

	/**
	 * Returns the number of layout page template entries where groupId = &#63; and name LIKE &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param type the type
	 * @return the number of matching layout page template entries
	 */
	@Override
	public int countByG_T_LikeN(long groupId, String name, int type) {
		name = Objects.toString(name, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			LayoutPageTemplateEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathWithPaginationCountByG_T_LikeN;

			finderArgs = new Object[] {groupId, name, type};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_LAYOUTPAGETEMPLATEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_T_LIKEN_GROUPID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_T_LIKEN_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_G_T_LIKEN_NAME_2);
			}

			sb.append(_FINDER_COLUMN_G_T_LIKEN_TYPE_2);

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
	 * Returns the number of layout page template entries that the user has permission to view where groupId = &#63; and name LIKE &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param type the type
	 * @return the number of matching layout page template entries that the user has permission to view
	 */
	@Override
	public int filterCountByG_T_LikeN(long groupId, String name, int type) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_T_LikeN(groupId, name, type);
		}

		name = Objects.toString(name, "");

		StringBundler sb = new StringBundler(4);

		sb.append(_FILTER_SQL_COUNT_LAYOUTPAGETEMPLATEENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_T_LIKEN_GROUPID_2);

		boolean bindName = false;

		if (name.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_T_LIKEN_NAME_3);
		}
		else {
			bindName = true;

			sb.append(_FINDER_COLUMN_G_T_LIKEN_NAME_2);
		}

		sb.append(_FINDER_COLUMN_G_T_LIKEN_TYPE_2_SQL);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), LayoutPageTemplateEntry.class.getName(),
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

	private static final String _FINDER_COLUMN_G_T_LIKEN_GROUPID_2 =
		"layoutPageTemplateEntry.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_T_LIKEN_NAME_2 =
		"layoutPageTemplateEntry.name LIKE ? AND ";

	private static final String _FINDER_COLUMN_G_T_LIKEN_NAME_3 =
		"(layoutPageTemplateEntry.name IS NULL OR layoutPageTemplateEntry.name LIKE '') AND ";

	private static final String _FINDER_COLUMN_G_T_LIKEN_TYPE_2 =
		"layoutPageTemplateEntry.type = ?";

	private static final String _FINDER_COLUMN_G_T_LIKEN_TYPE_2_SQL =
		"layoutPageTemplateEntry.type_ = ?";

	private FinderPath _finderPathWithPaginationFindByG_T_S;
	private FinderPath _finderPathWithoutPaginationFindByG_T_S;
	private FinderPath _finderPathCountByG_T_S;

	/**
	 * Returns all the layout page template entries where groupId = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param status the status
	 * @return the matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_T_S(
		long groupId, int type, int status) {

		return findByG_T_S(
			groupId, type, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout page template entries where groupId = &#63; and type = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param status the status
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @return the range of matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_T_S(
		long groupId, int type, int status, int start, int end) {

		return findByG_T_S(groupId, type, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout page template entries where groupId = &#63; and type = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param status the status
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_T_S(
		long groupId, int type, int status, int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		return findByG_T_S(
			groupId, type, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout page template entries where groupId = &#63; and type = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param status the status
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_T_S(
		long groupId, int type, int status, int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			LayoutPageTemplateEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByG_T_S;
				finderArgs = new Object[] {groupId, type, status};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByG_T_S;
			finderArgs = new Object[] {
				groupId, type, status, start, end, orderByComparator
			};
		}

		List<LayoutPageTemplateEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<LayoutPageTemplateEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutPageTemplateEntry layoutPageTemplateEntry : list) {
					if ((groupId != layoutPageTemplateEntry.getGroupId()) ||
						(type != layoutPageTemplateEntry.getType()) ||
						(status != layoutPageTemplateEntry.getStatus())) {

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

			sb.append(_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_T_S_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_T_S_TYPE_2);

			sb.append(_FINDER_COLUMN_G_T_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(type);

				queryPos.add(status);

				list = (List<LayoutPageTemplateEntry>)QueryUtil.list(
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
	 * Returns the first layout page template entry in the ordered set where groupId = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry findByG_T_S_First(
			long groupId, int type, int status,
			OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {

		LayoutPageTemplateEntry layoutPageTemplateEntry = fetchByG_T_S_First(
			groupId, type, status, orderByComparator);

		if (layoutPageTemplateEntry != null) {
			return layoutPageTemplateEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", type=");
		sb.append(type);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchPageTemplateEntryException(sb.toString());
	}

	/**
	 * Returns the first layout page template entry in the ordered set where groupId = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template entry, or <code>null</code> if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry fetchByG_T_S_First(
		long groupId, int type, int status,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		List<LayoutPageTemplateEntry> list = findByG_T_S(
			groupId, type, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout page template entry in the ordered set where groupId = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry findByG_T_S_Last(
			long groupId, int type, int status,
			OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {

		LayoutPageTemplateEntry layoutPageTemplateEntry = fetchByG_T_S_Last(
			groupId, type, status, orderByComparator);

		if (layoutPageTemplateEntry != null) {
			return layoutPageTemplateEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", type=");
		sb.append(type);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchPageTemplateEntryException(sb.toString());
	}

	/**
	 * Returns the last layout page template entry in the ordered set where groupId = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template entry, or <code>null</code> if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry fetchByG_T_S_Last(
		long groupId, int type, int status,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		int count = countByG_T_S(groupId, type, status);

		if (count == 0) {
			return null;
		}

		List<LayoutPageTemplateEntry> list = findByG_T_S(
			groupId, type, status, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout page template entries before and after the current layout page template entry in the ordered set where groupId = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the primary key of the current layout page template entry
	 * @param groupId the group ID
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a layout page template entry with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateEntry[] findByG_T_S_PrevAndNext(
			long layoutPageTemplateEntryId, long groupId, int type, int status,
			OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {

		LayoutPageTemplateEntry layoutPageTemplateEntry = findByPrimaryKey(
			layoutPageTemplateEntryId);

		Session session = null;

		try {
			session = openSession();

			LayoutPageTemplateEntry[] array =
				new LayoutPageTemplateEntryImpl[3];

			array[0] = getByG_T_S_PrevAndNext(
				session, layoutPageTemplateEntry, groupId, type, status,
				orderByComparator, true);

			array[1] = layoutPageTemplateEntry;

			array[2] = getByG_T_S_PrevAndNext(
				session, layoutPageTemplateEntry, groupId, type, status,
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

	protected LayoutPageTemplateEntry getByG_T_S_PrevAndNext(
		Session session, LayoutPageTemplateEntry layoutPageTemplateEntry,
		long groupId, int type, int status,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_T_S_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_T_S_TYPE_2);

		sb.append(_FINDER_COLUMN_G_T_S_STATUS_2);

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
			sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(type);

		queryPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutPageTemplateEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<LayoutPageTemplateEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the layout page template entries that the user has permission to view where groupId = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param status the status
	 * @return the matching layout page template entries that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateEntry> filterFindByG_T_S(
		long groupId, int type, int status) {

		return filterFindByG_T_S(
			groupId, type, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout page template entries that the user has permission to view where groupId = &#63; and type = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param status the status
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @return the range of matching layout page template entries that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateEntry> filterFindByG_T_S(
		long groupId, int type, int status, int start, int end) {

		return filterFindByG_T_S(groupId, type, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout page template entries that the user has permissions to view where groupId = &#63; and type = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param status the status
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template entries that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateEntry> filterFindByG_T_S(
		long groupId, int type, int status, int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_T_S(
				groupId, type, status, start, end, orderByComparator);
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
			sb.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_T_S_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_T_S_TYPE_2_SQL);

		sb.append(_FINDER_COLUMN_G_T_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), LayoutPageTemplateEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, LayoutPageTemplateEntryImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, LayoutPageTemplateEntryImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(type);

			queryPos.add(status);

			return (List<LayoutPageTemplateEntry>)QueryUtil.list(
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
	 * Returns the layout page template entries before and after the current layout page template entry in the ordered set of layout page template entries that the user has permission to view where groupId = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the primary key of the current layout page template entry
	 * @param groupId the group ID
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a layout page template entry with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateEntry[] filterFindByG_T_S_PrevAndNext(
			long layoutPageTemplateEntryId, long groupId, int type, int status,
			OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_T_S_PrevAndNext(
				layoutPageTemplateEntryId, groupId, type, status,
				orderByComparator);
		}

		LayoutPageTemplateEntry layoutPageTemplateEntry = findByPrimaryKey(
			layoutPageTemplateEntryId);

		Session session = null;

		try {
			session = openSession();

			LayoutPageTemplateEntry[] array =
				new LayoutPageTemplateEntryImpl[3];

			array[0] = filterGetByG_T_S_PrevAndNext(
				session, layoutPageTemplateEntry, groupId, type, status,
				orderByComparator, true);

			array[1] = layoutPageTemplateEntry;

			array[2] = filterGetByG_T_S_PrevAndNext(
				session, layoutPageTemplateEntry, groupId, type, status,
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

	protected LayoutPageTemplateEntry filterGetByG_T_S_PrevAndNext(
		Session session, LayoutPageTemplateEntry layoutPageTemplateEntry,
		long groupId, int type, int status,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator,
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
			sb.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_T_S_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_T_S_TYPE_2_SQL);

		sb.append(_FINDER_COLUMN_G_T_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), LayoutPageTemplateEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(
				_FILTER_ENTITY_ALIAS, LayoutPageTemplateEntryImpl.class);
		}
		else {
			sqlQuery.addEntity(
				_FILTER_ENTITY_TABLE, LayoutPageTemplateEntryImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(groupId);

		queryPos.add(type);

		queryPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutPageTemplateEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<LayoutPageTemplateEntry> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout page template entries where groupId = &#63; and type = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param status the status
	 */
	@Override
	public void removeByG_T_S(long groupId, int type, int status) {
		for (LayoutPageTemplateEntry layoutPageTemplateEntry :
				findByG_T_S(
					groupId, type, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(layoutPageTemplateEntry);
		}
	}

	/**
	 * Returns the number of layout page template entries where groupId = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param status the status
	 * @return the number of matching layout page template entries
	 */
	@Override
	public int countByG_T_S(long groupId, int type, int status) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			LayoutPageTemplateEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_T_S;

			finderArgs = new Object[] {groupId, type, status};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_LAYOUTPAGETEMPLATEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_T_S_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_T_S_TYPE_2);

			sb.append(_FINDER_COLUMN_G_T_S_STATUS_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(type);

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
	 * Returns the number of layout page template entries that the user has permission to view where groupId = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param status the status
	 * @return the number of matching layout page template entries that the user has permission to view
	 */
	@Override
	public int filterCountByG_T_S(long groupId, int type, int status) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_T_S(groupId, type, status);
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_FILTER_SQL_COUNT_LAYOUTPAGETEMPLATEENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_T_S_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_T_S_TYPE_2_SQL);

		sb.append(_FINDER_COLUMN_G_T_S_STATUS_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), LayoutPageTemplateEntry.class.getName(),
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

			queryPos.add(status);

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

	private static final String _FINDER_COLUMN_G_T_S_GROUPID_2 =
		"layoutPageTemplateEntry.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_T_S_TYPE_2 =
		"layoutPageTemplateEntry.type = ? AND ";

	private static final String _FINDER_COLUMN_G_T_S_TYPE_2_SQL =
		"layoutPageTemplateEntry.type_ = ? AND ";

	private static final String _FINDER_COLUMN_G_T_S_STATUS_2 =
		"layoutPageTemplateEntry.status = ?";

	private FinderPath _finderPathWithPaginationFindByG_L_LikeN_S;
	private FinderPath _finderPathWithPaginationCountByG_L_LikeN_S;

	/**
	 * Returns all the layout page template entries where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and name LIKE &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param name the name
	 * @param status the status
	 * @return the matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_L_LikeN_S(
		long groupId, long layoutPageTemplateCollectionId, String name,
		int status) {

		return findByG_L_LikeN_S(
			groupId, layoutPageTemplateCollectionId, name, status,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout page template entries where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and name LIKE &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param name the name
	 * @param status the status
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @return the range of matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_L_LikeN_S(
		long groupId, long layoutPageTemplateCollectionId, String name,
		int status, int start, int end) {

		return findByG_L_LikeN_S(
			groupId, layoutPageTemplateCollectionId, name, status, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the layout page template entries where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and name LIKE &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param name the name
	 * @param status the status
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_L_LikeN_S(
		long groupId, long layoutPageTemplateCollectionId, String name,
		int status, int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		return findByG_L_LikeN_S(
			groupId, layoutPageTemplateCollectionId, name, status, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout page template entries where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and name LIKE &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param name the name
	 * @param status the status
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_L_LikeN_S(
		long groupId, long layoutPageTemplateCollectionId, String name,
		int status, int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator,
		boolean useFinderCache) {

		name = Objects.toString(name, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			LayoutPageTemplateEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByG_L_LikeN_S;
		finderArgs = new Object[] {
			groupId, layoutPageTemplateCollectionId, name, status, start, end,
			orderByComparator
		};

		List<LayoutPageTemplateEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<LayoutPageTemplateEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutPageTemplateEntry layoutPageTemplateEntry : list) {
					if ((groupId != layoutPageTemplateEntry.getGroupId()) ||
						(layoutPageTemplateCollectionId !=
							layoutPageTemplateEntry.
								getLayoutPageTemplateCollectionId()) ||
						!StringUtil.wildcardMatches(
							layoutPageTemplateEntry.getName(), name, '_', '%',
							'\\', true) ||
						(status != layoutPageTemplateEntry.getStatus())) {

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

			sb.append(_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_L_LIKEN_S_GROUPID_2);

			sb.append(
				_FINDER_COLUMN_G_L_LIKEN_S_LAYOUTPAGETEMPLATECOLLECTIONID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_L_LIKEN_S_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_G_L_LIKEN_S_NAME_2);
			}

			sb.append(_FINDER_COLUMN_G_L_LIKEN_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(layoutPageTemplateCollectionId);

				if (bindName) {
					queryPos.add(name);
				}

				queryPos.add(status);

				list = (List<LayoutPageTemplateEntry>)QueryUtil.list(
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
	 * Returns the first layout page template entry in the ordered set where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and name LIKE &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param name the name
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry findByG_L_LikeN_S_First(
			long groupId, long layoutPageTemplateCollectionId, String name,
			int status,
			OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			fetchByG_L_LikeN_S_First(
				groupId, layoutPageTemplateCollectionId, name, status,
				orderByComparator);

		if (layoutPageTemplateEntry != null) {
			return layoutPageTemplateEntry;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", layoutPageTemplateCollectionId=");
		sb.append(layoutPageTemplateCollectionId);

		sb.append(", nameLIKE");
		sb.append(name);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchPageTemplateEntryException(sb.toString());
	}

	/**
	 * Returns the first layout page template entry in the ordered set where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and name LIKE &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param name the name
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template entry, or <code>null</code> if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry fetchByG_L_LikeN_S_First(
		long groupId, long layoutPageTemplateCollectionId, String name,
		int status,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		List<LayoutPageTemplateEntry> list = findByG_L_LikeN_S(
			groupId, layoutPageTemplateCollectionId, name, status, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout page template entry in the ordered set where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and name LIKE &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param name the name
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry findByG_L_LikeN_S_Last(
			long groupId, long layoutPageTemplateCollectionId, String name,
			int status,
			OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			fetchByG_L_LikeN_S_Last(
				groupId, layoutPageTemplateCollectionId, name, status,
				orderByComparator);

		if (layoutPageTemplateEntry != null) {
			return layoutPageTemplateEntry;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", layoutPageTemplateCollectionId=");
		sb.append(layoutPageTemplateCollectionId);

		sb.append(", nameLIKE");
		sb.append(name);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchPageTemplateEntryException(sb.toString());
	}

	/**
	 * Returns the last layout page template entry in the ordered set where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and name LIKE &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param name the name
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template entry, or <code>null</code> if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry fetchByG_L_LikeN_S_Last(
		long groupId, long layoutPageTemplateCollectionId, String name,
		int status,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		int count = countByG_L_LikeN_S(
			groupId, layoutPageTemplateCollectionId, name, status);

		if (count == 0) {
			return null;
		}

		List<LayoutPageTemplateEntry> list = findByG_L_LikeN_S(
			groupId, layoutPageTemplateCollectionId, name, status, count - 1,
			count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout page template entries before and after the current layout page template entry in the ordered set where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and name LIKE &#63; and status = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the primary key of the current layout page template entry
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param name the name
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a layout page template entry with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateEntry[] findByG_L_LikeN_S_PrevAndNext(
			long layoutPageTemplateEntryId, long groupId,
			long layoutPageTemplateCollectionId, String name, int status,
			OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {

		name = Objects.toString(name, "");

		LayoutPageTemplateEntry layoutPageTemplateEntry = findByPrimaryKey(
			layoutPageTemplateEntryId);

		Session session = null;

		try {
			session = openSession();

			LayoutPageTemplateEntry[] array =
				new LayoutPageTemplateEntryImpl[3];

			array[0] = getByG_L_LikeN_S_PrevAndNext(
				session, layoutPageTemplateEntry, groupId,
				layoutPageTemplateCollectionId, name, status, orderByComparator,
				true);

			array[1] = layoutPageTemplateEntry;

			array[2] = getByG_L_LikeN_S_PrevAndNext(
				session, layoutPageTemplateEntry, groupId,
				layoutPageTemplateCollectionId, name, status, orderByComparator,
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

	protected LayoutPageTemplateEntry getByG_L_LikeN_S_PrevAndNext(
		Session session, LayoutPageTemplateEntry layoutPageTemplateEntry,
		long groupId, long layoutPageTemplateCollectionId, String name,
		int status,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_L_LIKEN_S_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_L_LIKEN_S_LAYOUTPAGETEMPLATECOLLECTIONID_2);

		boolean bindName = false;

		if (name.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_L_LIKEN_S_NAME_3);
		}
		else {
			bindName = true;

			sb.append(_FINDER_COLUMN_G_L_LIKEN_S_NAME_2);
		}

		sb.append(_FINDER_COLUMN_G_L_LIKEN_S_STATUS_2);

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
			sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(layoutPageTemplateCollectionId);

		if (bindName) {
			queryPos.add(name);
		}

		queryPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutPageTemplateEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<LayoutPageTemplateEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the layout page template entries that the user has permission to view where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and name LIKE &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param name the name
	 * @param status the status
	 * @return the matching layout page template entries that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateEntry> filterFindByG_L_LikeN_S(
		long groupId, long layoutPageTemplateCollectionId, String name,
		int status) {

		return filterFindByG_L_LikeN_S(
			groupId, layoutPageTemplateCollectionId, name, status,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout page template entries that the user has permission to view where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and name LIKE &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param name the name
	 * @param status the status
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @return the range of matching layout page template entries that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateEntry> filterFindByG_L_LikeN_S(
		long groupId, long layoutPageTemplateCollectionId, String name,
		int status, int start, int end) {

		return filterFindByG_L_LikeN_S(
			groupId, layoutPageTemplateCollectionId, name, status, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the layout page template entries that the user has permissions to view where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and name LIKE &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param name the name
	 * @param status the status
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template entries that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateEntry> filterFindByG_L_LikeN_S(
		long groupId, long layoutPageTemplateCollectionId, String name,
		int status, int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_L_LikeN_S(
				groupId, layoutPageTemplateCollectionId, name, status, start,
				end, orderByComparator);
		}

		name = Objects.toString(name, "");

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(7);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_L_LIKEN_S_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_L_LIKEN_S_LAYOUTPAGETEMPLATECOLLECTIONID_2);

		boolean bindName = false;

		if (name.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_L_LIKEN_S_NAME_3);
		}
		else {
			bindName = true;

			sb.append(_FINDER_COLUMN_G_L_LIKEN_S_NAME_2);
		}

		sb.append(_FINDER_COLUMN_G_L_LIKEN_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), LayoutPageTemplateEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, LayoutPageTemplateEntryImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, LayoutPageTemplateEntryImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(layoutPageTemplateCollectionId);

			if (bindName) {
				queryPos.add(name);
			}

			queryPos.add(status);

			return (List<LayoutPageTemplateEntry>)QueryUtil.list(
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
	 * Returns the layout page template entries before and after the current layout page template entry in the ordered set of layout page template entries that the user has permission to view where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and name LIKE &#63; and status = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the primary key of the current layout page template entry
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param name the name
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a layout page template entry with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateEntry[] filterFindByG_L_LikeN_S_PrevAndNext(
			long layoutPageTemplateEntryId, long groupId,
			long layoutPageTemplateCollectionId, String name, int status,
			OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_L_LikeN_S_PrevAndNext(
				layoutPageTemplateEntryId, groupId,
				layoutPageTemplateCollectionId, name, status,
				orderByComparator);
		}

		name = Objects.toString(name, "");

		LayoutPageTemplateEntry layoutPageTemplateEntry = findByPrimaryKey(
			layoutPageTemplateEntryId);

		Session session = null;

		try {
			session = openSession();

			LayoutPageTemplateEntry[] array =
				new LayoutPageTemplateEntryImpl[3];

			array[0] = filterGetByG_L_LikeN_S_PrevAndNext(
				session, layoutPageTemplateEntry, groupId,
				layoutPageTemplateCollectionId, name, status, orderByComparator,
				true);

			array[1] = layoutPageTemplateEntry;

			array[2] = filterGetByG_L_LikeN_S_PrevAndNext(
				session, layoutPageTemplateEntry, groupId,
				layoutPageTemplateCollectionId, name, status, orderByComparator,
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

	protected LayoutPageTemplateEntry filterGetByG_L_LikeN_S_PrevAndNext(
		Session session, LayoutPageTemplateEntry layoutPageTemplateEntry,
		long groupId, long layoutPageTemplateCollectionId, String name,
		int status,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator,
		boolean previous) {

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
			sb.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_L_LIKEN_S_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_L_LIKEN_S_LAYOUTPAGETEMPLATECOLLECTIONID_2);

		boolean bindName = false;

		if (name.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_L_LIKEN_S_NAME_3);
		}
		else {
			bindName = true;

			sb.append(_FINDER_COLUMN_G_L_LIKEN_S_NAME_2);
		}

		sb.append(_FINDER_COLUMN_G_L_LIKEN_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), LayoutPageTemplateEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(
				_FILTER_ENTITY_ALIAS, LayoutPageTemplateEntryImpl.class);
		}
		else {
			sqlQuery.addEntity(
				_FILTER_ENTITY_TABLE, LayoutPageTemplateEntryImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(groupId);

		queryPos.add(layoutPageTemplateCollectionId);

		if (bindName) {
			queryPos.add(name);
		}

		queryPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutPageTemplateEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<LayoutPageTemplateEntry> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout page template entries where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and name LIKE &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param name the name
	 * @param status the status
	 */
	@Override
	public void removeByG_L_LikeN_S(
		long groupId, long layoutPageTemplateCollectionId, String name,
		int status) {

		for (LayoutPageTemplateEntry layoutPageTemplateEntry :
				findByG_L_LikeN_S(
					groupId, layoutPageTemplateCollectionId, name, status,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(layoutPageTemplateEntry);
		}
	}

	/**
	 * Returns the number of layout page template entries where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and name LIKE &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param name the name
	 * @param status the status
	 * @return the number of matching layout page template entries
	 */
	@Override
	public int countByG_L_LikeN_S(
		long groupId, long layoutPageTemplateCollectionId, String name,
		int status) {

		name = Objects.toString(name, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			LayoutPageTemplateEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathWithPaginationCountByG_L_LikeN_S;

			finderArgs = new Object[] {
				groupId, layoutPageTemplateCollectionId, name, status
			};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_COUNT_LAYOUTPAGETEMPLATEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_L_LIKEN_S_GROUPID_2);

			sb.append(
				_FINDER_COLUMN_G_L_LIKEN_S_LAYOUTPAGETEMPLATECOLLECTIONID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_L_LIKEN_S_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_G_L_LIKEN_S_NAME_2);
			}

			sb.append(_FINDER_COLUMN_G_L_LIKEN_S_STATUS_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(layoutPageTemplateCollectionId);

				if (bindName) {
					queryPos.add(name);
				}

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
	 * Returns the number of layout page template entries that the user has permission to view where groupId = &#63; and layoutPageTemplateCollectionId = &#63; and name LIKE &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutPageTemplateCollectionId the layout page template collection ID
	 * @param name the name
	 * @param status the status
	 * @return the number of matching layout page template entries that the user has permission to view
	 */
	@Override
	public int filterCountByG_L_LikeN_S(
		long groupId, long layoutPageTemplateCollectionId, String name,
		int status) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_L_LikeN_S(
				groupId, layoutPageTemplateCollectionId, name, status);
		}

		name = Objects.toString(name, "");

		StringBundler sb = new StringBundler(5);

		sb.append(_FILTER_SQL_COUNT_LAYOUTPAGETEMPLATEENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_L_LIKEN_S_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_L_LIKEN_S_LAYOUTPAGETEMPLATECOLLECTIONID_2);

		boolean bindName = false;

		if (name.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_L_LIKEN_S_NAME_3);
		}
		else {
			bindName = true;

			sb.append(_FINDER_COLUMN_G_L_LIKEN_S_NAME_2);
		}

		sb.append(_FINDER_COLUMN_G_L_LIKEN_S_STATUS_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), LayoutPageTemplateEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(layoutPageTemplateCollectionId);

			if (bindName) {
				queryPos.add(name);
			}

			queryPos.add(status);

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

	private static final String _FINDER_COLUMN_G_L_LIKEN_S_GROUPID_2 =
		"layoutPageTemplateEntry.groupId = ? AND ";

	private static final String
		_FINDER_COLUMN_G_L_LIKEN_S_LAYOUTPAGETEMPLATECOLLECTIONID_2 =
			"layoutPageTemplateEntry.layoutPageTemplateCollectionId = ? AND ";

	private static final String _FINDER_COLUMN_G_L_LIKEN_S_NAME_2 =
		"layoutPageTemplateEntry.name LIKE ? AND ";

	private static final String _FINDER_COLUMN_G_L_LIKEN_S_NAME_3 =
		"(layoutPageTemplateEntry.name IS NULL OR layoutPageTemplateEntry.name LIKE '') AND ";

	private static final String _FINDER_COLUMN_G_L_LIKEN_S_STATUS_2 =
		"layoutPageTemplateEntry.status = ?";

	private FinderPath _finderPathWithPaginationFindByG_C_C_T;
	private FinderPath _finderPathWithoutPaginationFindByG_C_C_T;
	private FinderPath _finderPathCountByG_C_C_T;

	/**
	 * Returns all the layout page template entries where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param type the type
	 * @return the matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_C_C_T(
		long groupId, long classNameId, long classTypeId, int type) {

		return findByG_C_C_T(
			groupId, classNameId, classTypeId, type, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout page template entries where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param type the type
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @return the range of matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_C_C_T(
		long groupId, long classNameId, long classTypeId, int type, int start,
		int end) {

		return findByG_C_C_T(
			groupId, classNameId, classTypeId, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout page template entries where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param type the type
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_C_C_T(
		long groupId, long classNameId, long classTypeId, int type, int start,
		int end, OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		return findByG_C_C_T(
			groupId, classNameId, classTypeId, type, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout page template entries where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param type the type
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_C_C_T(
		long groupId, long classNameId, long classTypeId, int type, int start,
		int end, OrderByComparator<LayoutPageTemplateEntry> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			LayoutPageTemplateEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByG_C_C_T;
				finderArgs = new Object[] {
					groupId, classNameId, classTypeId, type
				};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByG_C_C_T;
			finderArgs = new Object[] {
				groupId, classNameId, classTypeId, type, start, end,
				orderByComparator
			};
		}

		List<LayoutPageTemplateEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<LayoutPageTemplateEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutPageTemplateEntry layoutPageTemplateEntry : list) {
					if ((groupId != layoutPageTemplateEntry.getGroupId()) ||
						(classNameId !=
							layoutPageTemplateEntry.getClassNameId()) ||
						(classTypeId !=
							layoutPageTemplateEntry.getClassTypeId()) ||
						(type != layoutPageTemplateEntry.getType())) {

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

			sb.append(_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_C_C_T_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_C_T_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_G_C_C_T_CLASSTYPEID_2);

			sb.append(_FINDER_COLUMN_G_C_C_T_TYPE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(classNameId);

				queryPos.add(classTypeId);

				queryPos.add(type);

				list = (List<LayoutPageTemplateEntry>)QueryUtil.list(
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
	 * Returns the first layout page template entry in the ordered set where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry findByG_C_C_T_First(
			long groupId, long classNameId, long classTypeId, int type,
			OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {

		LayoutPageTemplateEntry layoutPageTemplateEntry = fetchByG_C_C_T_First(
			groupId, classNameId, classTypeId, type, orderByComparator);

		if (layoutPageTemplateEntry != null) {
			return layoutPageTemplateEntry;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append(", classTypeId=");
		sb.append(classTypeId);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchPageTemplateEntryException(sb.toString());
	}

	/**
	 * Returns the first layout page template entry in the ordered set where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template entry, or <code>null</code> if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry fetchByG_C_C_T_First(
		long groupId, long classNameId, long classTypeId, int type,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		List<LayoutPageTemplateEntry> list = findByG_C_C_T(
			groupId, classNameId, classTypeId, type, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout page template entry in the ordered set where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry findByG_C_C_T_Last(
			long groupId, long classNameId, long classTypeId, int type,
			OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {

		LayoutPageTemplateEntry layoutPageTemplateEntry = fetchByG_C_C_T_Last(
			groupId, classNameId, classTypeId, type, orderByComparator);

		if (layoutPageTemplateEntry != null) {
			return layoutPageTemplateEntry;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append(", classTypeId=");
		sb.append(classTypeId);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchPageTemplateEntryException(sb.toString());
	}

	/**
	 * Returns the last layout page template entry in the ordered set where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template entry, or <code>null</code> if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry fetchByG_C_C_T_Last(
		long groupId, long classNameId, long classTypeId, int type,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		int count = countByG_C_C_T(groupId, classNameId, classTypeId, type);

		if (count == 0) {
			return null;
		}

		List<LayoutPageTemplateEntry> list = findByG_C_C_T(
			groupId, classNameId, classTypeId, type, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout page template entries before and after the current layout page template entry in the ordered set where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and type = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the primary key of the current layout page template entry
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a layout page template entry with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateEntry[] findByG_C_C_T_PrevAndNext(
			long layoutPageTemplateEntryId, long groupId, long classNameId,
			long classTypeId, int type,
			OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {

		LayoutPageTemplateEntry layoutPageTemplateEntry = findByPrimaryKey(
			layoutPageTemplateEntryId);

		Session session = null;

		try {
			session = openSession();

			LayoutPageTemplateEntry[] array =
				new LayoutPageTemplateEntryImpl[3];

			array[0] = getByG_C_C_T_PrevAndNext(
				session, layoutPageTemplateEntry, groupId, classNameId,
				classTypeId, type, orderByComparator, true);

			array[1] = layoutPageTemplateEntry;

			array[2] = getByG_C_C_T_PrevAndNext(
				session, layoutPageTemplateEntry, groupId, classNameId,
				classTypeId, type, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutPageTemplateEntry getByG_C_C_T_PrevAndNext(
		Session session, LayoutPageTemplateEntry layoutPageTemplateEntry,
		long groupId, long classNameId, long classTypeId, int type,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_C_C_T_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_C_T_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_G_C_C_T_CLASSTYPEID_2);

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
			sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(classNameId);

		queryPos.add(classTypeId);

		queryPos.add(type);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutPageTemplateEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<LayoutPageTemplateEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the layout page template entries that the user has permission to view where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param type the type
	 * @return the matching layout page template entries that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateEntry> filterFindByG_C_C_T(
		long groupId, long classNameId, long classTypeId, int type) {

		return filterFindByG_C_C_T(
			groupId, classNameId, classTypeId, type, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout page template entries that the user has permission to view where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param type the type
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @return the range of matching layout page template entries that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateEntry> filterFindByG_C_C_T(
		long groupId, long classNameId, long classTypeId, int type, int start,
		int end) {

		return filterFindByG_C_C_T(
			groupId, classNameId, classTypeId, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout page template entries that the user has permissions to view where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param type the type
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template entries that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateEntry> filterFindByG_C_C_T(
		long groupId, long classNameId, long classTypeId, int type, int start,
		int end, OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C_C_T(
				groupId, classNameId, classTypeId, type, start, end,
				orderByComparator);
		}

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(7);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_C_C_T_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_C_T_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_G_C_C_T_CLASSTYPEID_2);

		sb.append(_FINDER_COLUMN_G_C_C_T_TYPE_2_SQL);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), LayoutPageTemplateEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, LayoutPageTemplateEntryImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, LayoutPageTemplateEntryImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(classNameId);

			queryPos.add(classTypeId);

			queryPos.add(type);

			return (List<LayoutPageTemplateEntry>)QueryUtil.list(
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
	 * Returns the layout page template entries before and after the current layout page template entry in the ordered set of layout page template entries that the user has permission to view where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and type = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the primary key of the current layout page template entry
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a layout page template entry with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateEntry[] filterFindByG_C_C_T_PrevAndNext(
			long layoutPageTemplateEntryId, long groupId, long classNameId,
			long classTypeId, int type,
			OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C_C_T_PrevAndNext(
				layoutPageTemplateEntryId, groupId, classNameId, classTypeId,
				type, orderByComparator);
		}

		LayoutPageTemplateEntry layoutPageTemplateEntry = findByPrimaryKey(
			layoutPageTemplateEntryId);

		Session session = null;

		try {
			session = openSession();

			LayoutPageTemplateEntry[] array =
				new LayoutPageTemplateEntryImpl[3];

			array[0] = filterGetByG_C_C_T_PrevAndNext(
				session, layoutPageTemplateEntry, groupId, classNameId,
				classTypeId, type, orderByComparator, true);

			array[1] = layoutPageTemplateEntry;

			array[2] = filterGetByG_C_C_T_PrevAndNext(
				session, layoutPageTemplateEntry, groupId, classNameId,
				classTypeId, type, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutPageTemplateEntry filterGetByG_C_C_T_PrevAndNext(
		Session session, LayoutPageTemplateEntry layoutPageTemplateEntry,
		long groupId, long classNameId, long classTypeId, int type,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator,
		boolean previous) {

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
			sb.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_C_C_T_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_C_T_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_G_C_C_T_CLASSTYPEID_2);

		sb.append(_FINDER_COLUMN_G_C_C_T_TYPE_2_SQL);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), LayoutPageTemplateEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(
				_FILTER_ENTITY_ALIAS, LayoutPageTemplateEntryImpl.class);
		}
		else {
			sqlQuery.addEntity(
				_FILTER_ENTITY_TABLE, LayoutPageTemplateEntryImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(groupId);

		queryPos.add(classNameId);

		queryPos.add(classTypeId);

		queryPos.add(type);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutPageTemplateEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<LayoutPageTemplateEntry> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout page template entries where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and type = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param type the type
	 */
	@Override
	public void removeByG_C_C_T(
		long groupId, long classNameId, long classTypeId, int type) {

		for (LayoutPageTemplateEntry layoutPageTemplateEntry :
				findByG_C_C_T(
					groupId, classNameId, classTypeId, type, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(layoutPageTemplateEntry);
		}
	}

	/**
	 * Returns the number of layout page template entries where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param type the type
	 * @return the number of matching layout page template entries
	 */
	@Override
	public int countByG_C_C_T(
		long groupId, long classNameId, long classTypeId, int type) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			LayoutPageTemplateEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_C_C_T;

			finderArgs = new Object[] {groupId, classNameId, classTypeId, type};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_COUNT_LAYOUTPAGETEMPLATEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_C_C_T_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_C_T_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_G_C_C_T_CLASSTYPEID_2);

			sb.append(_FINDER_COLUMN_G_C_C_T_TYPE_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(classNameId);

				queryPos.add(classTypeId);

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
	 * Returns the number of layout page template entries that the user has permission to view where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param type the type
	 * @return the number of matching layout page template entries that the user has permission to view
	 */
	@Override
	public int filterCountByG_C_C_T(
		long groupId, long classNameId, long classTypeId, int type) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_C_C_T(groupId, classNameId, classTypeId, type);
		}

		StringBundler sb = new StringBundler(5);

		sb.append(_FILTER_SQL_COUNT_LAYOUTPAGETEMPLATEENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_C_C_T_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_C_T_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_G_C_C_T_CLASSTYPEID_2);

		sb.append(_FINDER_COLUMN_G_C_C_T_TYPE_2_SQL);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), LayoutPageTemplateEntry.class.getName(),
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

			queryPos.add(classTypeId);

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

	private static final String _FINDER_COLUMN_G_C_C_T_GROUPID_2 =
		"layoutPageTemplateEntry.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_T_CLASSNAMEID_2 =
		"layoutPageTemplateEntry.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_T_CLASSTYPEID_2 =
		"layoutPageTemplateEntry.classTypeId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_T_TYPE_2 =
		"layoutPageTemplateEntry.type = ?";

	private static final String _FINDER_COLUMN_G_C_C_T_TYPE_2_SQL =
		"layoutPageTemplateEntry.type_ = ?";

	private FinderPath _finderPathWithPaginationFindByG_C_C_D;
	private FinderPath _finderPathWithoutPaginationFindByG_C_C_D;
	private FinderPath _finderPathCountByG_C_C_D;

	/**
	 * Returns all the layout page template entries where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and defaultTemplate = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param defaultTemplate the default template
	 * @return the matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_C_C_D(
		long groupId, long classNameId, long classTypeId,
		boolean defaultTemplate) {

		return findByG_C_C_D(
			groupId, classNameId, classTypeId, defaultTemplate,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout page template entries where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and defaultTemplate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param defaultTemplate the default template
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @return the range of matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_C_C_D(
		long groupId, long classNameId, long classTypeId,
		boolean defaultTemplate, int start, int end) {

		return findByG_C_C_D(
			groupId, classNameId, classTypeId, defaultTemplate, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the layout page template entries where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and defaultTemplate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param defaultTemplate the default template
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_C_C_D(
		long groupId, long classNameId, long classTypeId,
		boolean defaultTemplate, int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		return findByG_C_C_D(
			groupId, classNameId, classTypeId, defaultTemplate, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout page template entries where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and defaultTemplate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param defaultTemplate the default template
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_C_C_D(
		long groupId, long classNameId, long classTypeId,
		boolean defaultTemplate, int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			LayoutPageTemplateEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByG_C_C_D;
				finderArgs = new Object[] {
					groupId, classNameId, classTypeId, defaultTemplate
				};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByG_C_C_D;
			finderArgs = new Object[] {
				groupId, classNameId, classTypeId, defaultTemplate, start, end,
				orderByComparator
			};
		}

		List<LayoutPageTemplateEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<LayoutPageTemplateEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutPageTemplateEntry layoutPageTemplateEntry : list) {
					if ((groupId != layoutPageTemplateEntry.getGroupId()) ||
						(classNameId !=
							layoutPageTemplateEntry.getClassNameId()) ||
						(classTypeId !=
							layoutPageTemplateEntry.getClassTypeId()) ||
						(defaultTemplate !=
							layoutPageTemplateEntry.isDefaultTemplate())) {

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

			sb.append(_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_C_C_D_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_C_D_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_G_C_C_D_CLASSTYPEID_2);

			sb.append(_FINDER_COLUMN_G_C_C_D_DEFAULTTEMPLATE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(classNameId);

				queryPos.add(classTypeId);

				queryPos.add(defaultTemplate);

				list = (List<LayoutPageTemplateEntry>)QueryUtil.list(
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
	 * Returns the first layout page template entry in the ordered set where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and defaultTemplate = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param defaultTemplate the default template
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry findByG_C_C_D_First(
			long groupId, long classNameId, long classTypeId,
			boolean defaultTemplate,
			OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {

		LayoutPageTemplateEntry layoutPageTemplateEntry = fetchByG_C_C_D_First(
			groupId, classNameId, classTypeId, defaultTemplate,
			orderByComparator);

		if (layoutPageTemplateEntry != null) {
			return layoutPageTemplateEntry;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append(", classTypeId=");
		sb.append(classTypeId);

		sb.append(", defaultTemplate=");
		sb.append(defaultTemplate);

		sb.append("}");

		throw new NoSuchPageTemplateEntryException(sb.toString());
	}

	/**
	 * Returns the first layout page template entry in the ordered set where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and defaultTemplate = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param defaultTemplate the default template
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template entry, or <code>null</code> if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry fetchByG_C_C_D_First(
		long groupId, long classNameId, long classTypeId,
		boolean defaultTemplate,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		List<LayoutPageTemplateEntry> list = findByG_C_C_D(
			groupId, classNameId, classTypeId, defaultTemplate, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout page template entry in the ordered set where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and defaultTemplate = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param defaultTemplate the default template
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry findByG_C_C_D_Last(
			long groupId, long classNameId, long classTypeId,
			boolean defaultTemplate,
			OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {

		LayoutPageTemplateEntry layoutPageTemplateEntry = fetchByG_C_C_D_Last(
			groupId, classNameId, classTypeId, defaultTemplate,
			orderByComparator);

		if (layoutPageTemplateEntry != null) {
			return layoutPageTemplateEntry;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append(", classTypeId=");
		sb.append(classTypeId);

		sb.append(", defaultTemplate=");
		sb.append(defaultTemplate);

		sb.append("}");

		throw new NoSuchPageTemplateEntryException(sb.toString());
	}

	/**
	 * Returns the last layout page template entry in the ordered set where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and defaultTemplate = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param defaultTemplate the default template
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template entry, or <code>null</code> if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry fetchByG_C_C_D_Last(
		long groupId, long classNameId, long classTypeId,
		boolean defaultTemplate,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		int count = countByG_C_C_D(
			groupId, classNameId, classTypeId, defaultTemplate);

		if (count == 0) {
			return null;
		}

		List<LayoutPageTemplateEntry> list = findByG_C_C_D(
			groupId, classNameId, classTypeId, defaultTemplate, count - 1,
			count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout page template entries before and after the current layout page template entry in the ordered set where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and defaultTemplate = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the primary key of the current layout page template entry
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param defaultTemplate the default template
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a layout page template entry with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateEntry[] findByG_C_C_D_PrevAndNext(
			long layoutPageTemplateEntryId, long groupId, long classNameId,
			long classTypeId, boolean defaultTemplate,
			OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {

		LayoutPageTemplateEntry layoutPageTemplateEntry = findByPrimaryKey(
			layoutPageTemplateEntryId);

		Session session = null;

		try {
			session = openSession();

			LayoutPageTemplateEntry[] array =
				new LayoutPageTemplateEntryImpl[3];

			array[0] = getByG_C_C_D_PrevAndNext(
				session, layoutPageTemplateEntry, groupId, classNameId,
				classTypeId, defaultTemplate, orderByComparator, true);

			array[1] = layoutPageTemplateEntry;

			array[2] = getByG_C_C_D_PrevAndNext(
				session, layoutPageTemplateEntry, groupId, classNameId,
				classTypeId, defaultTemplate, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutPageTemplateEntry getByG_C_C_D_PrevAndNext(
		Session session, LayoutPageTemplateEntry layoutPageTemplateEntry,
		long groupId, long classNameId, long classTypeId,
		boolean defaultTemplate,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_C_C_D_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_C_D_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_G_C_C_D_CLASSTYPEID_2);

		sb.append(_FINDER_COLUMN_G_C_C_D_DEFAULTTEMPLATE_2);

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
			sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(classNameId);

		queryPos.add(classTypeId);

		queryPos.add(defaultTemplate);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutPageTemplateEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<LayoutPageTemplateEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the layout page template entries that the user has permission to view where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and defaultTemplate = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param defaultTemplate the default template
	 * @return the matching layout page template entries that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateEntry> filterFindByG_C_C_D(
		long groupId, long classNameId, long classTypeId,
		boolean defaultTemplate) {

		return filterFindByG_C_C_D(
			groupId, classNameId, classTypeId, defaultTemplate,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout page template entries that the user has permission to view where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and defaultTemplate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param defaultTemplate the default template
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @return the range of matching layout page template entries that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateEntry> filterFindByG_C_C_D(
		long groupId, long classNameId, long classTypeId,
		boolean defaultTemplate, int start, int end) {

		return filterFindByG_C_C_D(
			groupId, classNameId, classTypeId, defaultTemplate, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the layout page template entries that the user has permissions to view where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and defaultTemplate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param defaultTemplate the default template
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template entries that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateEntry> filterFindByG_C_C_D(
		long groupId, long classNameId, long classTypeId,
		boolean defaultTemplate, int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C_C_D(
				groupId, classNameId, classTypeId, defaultTemplate, start, end,
				orderByComparator);
		}

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(7);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_C_C_D_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_C_D_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_G_C_C_D_CLASSTYPEID_2);

		sb.append(_FINDER_COLUMN_G_C_C_D_DEFAULTTEMPLATE_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), LayoutPageTemplateEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, LayoutPageTemplateEntryImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, LayoutPageTemplateEntryImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(classNameId);

			queryPos.add(classTypeId);

			queryPos.add(defaultTemplate);

			return (List<LayoutPageTemplateEntry>)QueryUtil.list(
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
	 * Returns the layout page template entries before and after the current layout page template entry in the ordered set of layout page template entries that the user has permission to view where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and defaultTemplate = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the primary key of the current layout page template entry
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param defaultTemplate the default template
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a layout page template entry with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateEntry[] filterFindByG_C_C_D_PrevAndNext(
			long layoutPageTemplateEntryId, long groupId, long classNameId,
			long classTypeId, boolean defaultTemplate,
			OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C_C_D_PrevAndNext(
				layoutPageTemplateEntryId, groupId, classNameId, classTypeId,
				defaultTemplate, orderByComparator);
		}

		LayoutPageTemplateEntry layoutPageTemplateEntry = findByPrimaryKey(
			layoutPageTemplateEntryId);

		Session session = null;

		try {
			session = openSession();

			LayoutPageTemplateEntry[] array =
				new LayoutPageTemplateEntryImpl[3];

			array[0] = filterGetByG_C_C_D_PrevAndNext(
				session, layoutPageTemplateEntry, groupId, classNameId,
				classTypeId, defaultTemplate, orderByComparator, true);

			array[1] = layoutPageTemplateEntry;

			array[2] = filterGetByG_C_C_D_PrevAndNext(
				session, layoutPageTemplateEntry, groupId, classNameId,
				classTypeId, defaultTemplate, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutPageTemplateEntry filterGetByG_C_C_D_PrevAndNext(
		Session session, LayoutPageTemplateEntry layoutPageTemplateEntry,
		long groupId, long classNameId, long classTypeId,
		boolean defaultTemplate,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator,
		boolean previous) {

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
			sb.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_C_C_D_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_C_D_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_G_C_C_D_CLASSTYPEID_2);

		sb.append(_FINDER_COLUMN_G_C_C_D_DEFAULTTEMPLATE_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), LayoutPageTemplateEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(
				_FILTER_ENTITY_ALIAS, LayoutPageTemplateEntryImpl.class);
		}
		else {
			sqlQuery.addEntity(
				_FILTER_ENTITY_TABLE, LayoutPageTemplateEntryImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(groupId);

		queryPos.add(classNameId);

		queryPos.add(classTypeId);

		queryPos.add(defaultTemplate);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutPageTemplateEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<LayoutPageTemplateEntry> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout page template entries where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and defaultTemplate = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param defaultTemplate the default template
	 */
	@Override
	public void removeByG_C_C_D(
		long groupId, long classNameId, long classTypeId,
		boolean defaultTemplate) {

		for (LayoutPageTemplateEntry layoutPageTemplateEntry :
				findByG_C_C_D(
					groupId, classNameId, classTypeId, defaultTemplate,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(layoutPageTemplateEntry);
		}
	}

	/**
	 * Returns the number of layout page template entries where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and defaultTemplate = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param defaultTemplate the default template
	 * @return the number of matching layout page template entries
	 */
	@Override
	public int countByG_C_C_D(
		long groupId, long classNameId, long classTypeId,
		boolean defaultTemplate) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			LayoutPageTemplateEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_C_C_D;

			finderArgs = new Object[] {
				groupId, classNameId, classTypeId, defaultTemplate
			};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_COUNT_LAYOUTPAGETEMPLATEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_C_C_D_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_C_D_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_G_C_C_D_CLASSTYPEID_2);

			sb.append(_FINDER_COLUMN_G_C_C_D_DEFAULTTEMPLATE_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(classNameId);

				queryPos.add(classTypeId);

				queryPos.add(defaultTemplate);

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
	 * Returns the number of layout page template entries that the user has permission to view where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and defaultTemplate = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param defaultTemplate the default template
	 * @return the number of matching layout page template entries that the user has permission to view
	 */
	@Override
	public int filterCountByG_C_C_D(
		long groupId, long classNameId, long classTypeId,
		boolean defaultTemplate) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_C_C_D(
				groupId, classNameId, classTypeId, defaultTemplate);
		}

		StringBundler sb = new StringBundler(5);

		sb.append(_FILTER_SQL_COUNT_LAYOUTPAGETEMPLATEENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_C_C_D_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_C_D_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_G_C_C_D_CLASSTYPEID_2);

		sb.append(_FINDER_COLUMN_G_C_C_D_DEFAULTTEMPLATE_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), LayoutPageTemplateEntry.class.getName(),
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

			queryPos.add(classTypeId);

			queryPos.add(defaultTemplate);

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

	private static final String _FINDER_COLUMN_G_C_C_D_GROUPID_2 =
		"layoutPageTemplateEntry.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_D_CLASSNAMEID_2 =
		"layoutPageTemplateEntry.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_D_CLASSTYPEID_2 =
		"layoutPageTemplateEntry.classTypeId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_D_DEFAULTTEMPLATE_2 =
		"layoutPageTemplateEntry.defaultTemplate = ?";

	private FinderPath _finderPathWithPaginationFindByG_C_T_D;
	private FinderPath _finderPathWithoutPaginationFindByG_C_T_D;
	private FinderPath _finderPathCountByG_C_T_D;

	/**
	 * Returns all the layout page template entries where groupId = &#63; and classNameId = &#63; and type = &#63; and defaultTemplate = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param type the type
	 * @param defaultTemplate the default template
	 * @return the matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_C_T_D(
		long groupId, long classNameId, int type, boolean defaultTemplate) {

		return findByG_C_T_D(
			groupId, classNameId, type, defaultTemplate, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout page template entries where groupId = &#63; and classNameId = &#63; and type = &#63; and defaultTemplate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param type the type
	 * @param defaultTemplate the default template
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @return the range of matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_C_T_D(
		long groupId, long classNameId, int type, boolean defaultTemplate,
		int start, int end) {

		return findByG_C_T_D(
			groupId, classNameId, type, defaultTemplate, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout page template entries where groupId = &#63; and classNameId = &#63; and type = &#63; and defaultTemplate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param type the type
	 * @param defaultTemplate the default template
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_C_T_D(
		long groupId, long classNameId, int type, boolean defaultTemplate,
		int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		return findByG_C_T_D(
			groupId, classNameId, type, defaultTemplate, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout page template entries where groupId = &#63; and classNameId = &#63; and type = &#63; and defaultTemplate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param type the type
	 * @param defaultTemplate the default template
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_C_T_D(
		long groupId, long classNameId, int type, boolean defaultTemplate,
		int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			LayoutPageTemplateEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByG_C_T_D;
				finderArgs = new Object[] {
					groupId, classNameId, type, defaultTemplate
				};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByG_C_T_D;
			finderArgs = new Object[] {
				groupId, classNameId, type, defaultTemplate, start, end,
				orderByComparator
			};
		}

		List<LayoutPageTemplateEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<LayoutPageTemplateEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutPageTemplateEntry layoutPageTemplateEntry : list) {
					if ((groupId != layoutPageTemplateEntry.getGroupId()) ||
						(classNameId !=
							layoutPageTemplateEntry.getClassNameId()) ||
						(type != layoutPageTemplateEntry.getType()) ||
						(defaultTemplate !=
							layoutPageTemplateEntry.isDefaultTemplate())) {

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

			sb.append(_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_C_T_D_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_T_D_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_G_C_T_D_TYPE_2);

			sb.append(_FINDER_COLUMN_G_C_T_D_DEFAULTTEMPLATE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(classNameId);

				queryPos.add(type);

				queryPos.add(defaultTemplate);

				list = (List<LayoutPageTemplateEntry>)QueryUtil.list(
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
	 * Returns the first layout page template entry in the ordered set where groupId = &#63; and classNameId = &#63; and type = &#63; and defaultTemplate = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param type the type
	 * @param defaultTemplate the default template
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry findByG_C_T_D_First(
			long groupId, long classNameId, int type, boolean defaultTemplate,
			OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {

		LayoutPageTemplateEntry layoutPageTemplateEntry = fetchByG_C_T_D_First(
			groupId, classNameId, type, defaultTemplate, orderByComparator);

		if (layoutPageTemplateEntry != null) {
			return layoutPageTemplateEntry;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append(", type=");
		sb.append(type);

		sb.append(", defaultTemplate=");
		sb.append(defaultTemplate);

		sb.append("}");

		throw new NoSuchPageTemplateEntryException(sb.toString());
	}

	/**
	 * Returns the first layout page template entry in the ordered set where groupId = &#63; and classNameId = &#63; and type = &#63; and defaultTemplate = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param type the type
	 * @param defaultTemplate the default template
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template entry, or <code>null</code> if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry fetchByG_C_T_D_First(
		long groupId, long classNameId, int type, boolean defaultTemplate,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		List<LayoutPageTemplateEntry> list = findByG_C_T_D(
			groupId, classNameId, type, defaultTemplate, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout page template entry in the ordered set where groupId = &#63; and classNameId = &#63; and type = &#63; and defaultTemplate = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param type the type
	 * @param defaultTemplate the default template
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry findByG_C_T_D_Last(
			long groupId, long classNameId, int type, boolean defaultTemplate,
			OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {

		LayoutPageTemplateEntry layoutPageTemplateEntry = fetchByG_C_T_D_Last(
			groupId, classNameId, type, defaultTemplate, orderByComparator);

		if (layoutPageTemplateEntry != null) {
			return layoutPageTemplateEntry;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append(", type=");
		sb.append(type);

		sb.append(", defaultTemplate=");
		sb.append(defaultTemplate);

		sb.append("}");

		throw new NoSuchPageTemplateEntryException(sb.toString());
	}

	/**
	 * Returns the last layout page template entry in the ordered set where groupId = &#63; and classNameId = &#63; and type = &#63; and defaultTemplate = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param type the type
	 * @param defaultTemplate the default template
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template entry, or <code>null</code> if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry fetchByG_C_T_D_Last(
		long groupId, long classNameId, int type, boolean defaultTemplate,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		int count = countByG_C_T_D(groupId, classNameId, type, defaultTemplate);

		if (count == 0) {
			return null;
		}

		List<LayoutPageTemplateEntry> list = findByG_C_T_D(
			groupId, classNameId, type, defaultTemplate, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout page template entries before and after the current layout page template entry in the ordered set where groupId = &#63; and classNameId = &#63; and type = &#63; and defaultTemplate = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the primary key of the current layout page template entry
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param type the type
	 * @param defaultTemplate the default template
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a layout page template entry with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateEntry[] findByG_C_T_D_PrevAndNext(
			long layoutPageTemplateEntryId, long groupId, long classNameId,
			int type, boolean defaultTemplate,
			OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {

		LayoutPageTemplateEntry layoutPageTemplateEntry = findByPrimaryKey(
			layoutPageTemplateEntryId);

		Session session = null;

		try {
			session = openSession();

			LayoutPageTemplateEntry[] array =
				new LayoutPageTemplateEntryImpl[3];

			array[0] = getByG_C_T_D_PrevAndNext(
				session, layoutPageTemplateEntry, groupId, classNameId, type,
				defaultTemplate, orderByComparator, true);

			array[1] = layoutPageTemplateEntry;

			array[2] = getByG_C_T_D_PrevAndNext(
				session, layoutPageTemplateEntry, groupId, classNameId, type,
				defaultTemplate, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutPageTemplateEntry getByG_C_T_D_PrevAndNext(
		Session session, LayoutPageTemplateEntry layoutPageTemplateEntry,
		long groupId, long classNameId, int type, boolean defaultTemplate,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_C_T_D_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_T_D_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_G_C_T_D_TYPE_2);

		sb.append(_FINDER_COLUMN_G_C_T_D_DEFAULTTEMPLATE_2);

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
			sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(classNameId);

		queryPos.add(type);

		queryPos.add(defaultTemplate);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutPageTemplateEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<LayoutPageTemplateEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the layout page template entries that the user has permission to view where groupId = &#63; and classNameId = &#63; and type = &#63; and defaultTemplate = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param type the type
	 * @param defaultTemplate the default template
	 * @return the matching layout page template entries that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateEntry> filterFindByG_C_T_D(
		long groupId, long classNameId, int type, boolean defaultTemplate) {

		return filterFindByG_C_T_D(
			groupId, classNameId, type, defaultTemplate, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout page template entries that the user has permission to view where groupId = &#63; and classNameId = &#63; and type = &#63; and defaultTemplate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param type the type
	 * @param defaultTemplate the default template
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @return the range of matching layout page template entries that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateEntry> filterFindByG_C_T_D(
		long groupId, long classNameId, int type, boolean defaultTemplate,
		int start, int end) {

		return filterFindByG_C_T_D(
			groupId, classNameId, type, defaultTemplate, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout page template entries that the user has permissions to view where groupId = &#63; and classNameId = &#63; and type = &#63; and defaultTemplate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param type the type
	 * @param defaultTemplate the default template
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template entries that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateEntry> filterFindByG_C_T_D(
		long groupId, long classNameId, int type, boolean defaultTemplate,
		int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C_T_D(
				groupId, classNameId, type, defaultTemplate, start, end,
				orderByComparator);
		}

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(7);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_C_T_D_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_T_D_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_G_C_T_D_TYPE_2_SQL);

		sb.append(_FINDER_COLUMN_G_C_T_D_DEFAULTTEMPLATE_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), LayoutPageTemplateEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, LayoutPageTemplateEntryImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, LayoutPageTemplateEntryImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(classNameId);

			queryPos.add(type);

			queryPos.add(defaultTemplate);

			return (List<LayoutPageTemplateEntry>)QueryUtil.list(
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
	 * Returns the layout page template entries before and after the current layout page template entry in the ordered set of layout page template entries that the user has permission to view where groupId = &#63; and classNameId = &#63; and type = &#63; and defaultTemplate = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the primary key of the current layout page template entry
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param type the type
	 * @param defaultTemplate the default template
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a layout page template entry with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateEntry[] filterFindByG_C_T_D_PrevAndNext(
			long layoutPageTemplateEntryId, long groupId, long classNameId,
			int type, boolean defaultTemplate,
			OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C_T_D_PrevAndNext(
				layoutPageTemplateEntryId, groupId, classNameId, type,
				defaultTemplate, orderByComparator);
		}

		LayoutPageTemplateEntry layoutPageTemplateEntry = findByPrimaryKey(
			layoutPageTemplateEntryId);

		Session session = null;

		try {
			session = openSession();

			LayoutPageTemplateEntry[] array =
				new LayoutPageTemplateEntryImpl[3];

			array[0] = filterGetByG_C_T_D_PrevAndNext(
				session, layoutPageTemplateEntry, groupId, classNameId, type,
				defaultTemplate, orderByComparator, true);

			array[1] = layoutPageTemplateEntry;

			array[2] = filterGetByG_C_T_D_PrevAndNext(
				session, layoutPageTemplateEntry, groupId, classNameId, type,
				defaultTemplate, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutPageTemplateEntry filterGetByG_C_T_D_PrevAndNext(
		Session session, LayoutPageTemplateEntry layoutPageTemplateEntry,
		long groupId, long classNameId, int type, boolean defaultTemplate,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator,
		boolean previous) {

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
			sb.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_C_T_D_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_T_D_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_G_C_T_D_TYPE_2_SQL);

		sb.append(_FINDER_COLUMN_G_C_T_D_DEFAULTTEMPLATE_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), LayoutPageTemplateEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(
				_FILTER_ENTITY_ALIAS, LayoutPageTemplateEntryImpl.class);
		}
		else {
			sqlQuery.addEntity(
				_FILTER_ENTITY_TABLE, LayoutPageTemplateEntryImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(groupId);

		queryPos.add(classNameId);

		queryPos.add(type);

		queryPos.add(defaultTemplate);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutPageTemplateEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<LayoutPageTemplateEntry> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout page template entries where groupId = &#63; and classNameId = &#63; and type = &#63; and defaultTemplate = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param type the type
	 * @param defaultTemplate the default template
	 */
	@Override
	public void removeByG_C_T_D(
		long groupId, long classNameId, int type, boolean defaultTemplate) {

		for (LayoutPageTemplateEntry layoutPageTemplateEntry :
				findByG_C_T_D(
					groupId, classNameId, type, defaultTemplate,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(layoutPageTemplateEntry);
		}
	}

	/**
	 * Returns the number of layout page template entries where groupId = &#63; and classNameId = &#63; and type = &#63; and defaultTemplate = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param type the type
	 * @param defaultTemplate the default template
	 * @return the number of matching layout page template entries
	 */
	@Override
	public int countByG_C_T_D(
		long groupId, long classNameId, int type, boolean defaultTemplate) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			LayoutPageTemplateEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_C_T_D;

			finderArgs = new Object[] {
				groupId, classNameId, type, defaultTemplate
			};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_COUNT_LAYOUTPAGETEMPLATEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_C_T_D_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_T_D_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_G_C_T_D_TYPE_2);

			sb.append(_FINDER_COLUMN_G_C_T_D_DEFAULTTEMPLATE_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(classNameId);

				queryPos.add(type);

				queryPos.add(defaultTemplate);

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
	 * Returns the number of layout page template entries that the user has permission to view where groupId = &#63; and classNameId = &#63; and type = &#63; and defaultTemplate = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param type the type
	 * @param defaultTemplate the default template
	 * @return the number of matching layout page template entries that the user has permission to view
	 */
	@Override
	public int filterCountByG_C_T_D(
		long groupId, long classNameId, int type, boolean defaultTemplate) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_C_T_D(groupId, classNameId, type, defaultTemplate);
		}

		StringBundler sb = new StringBundler(5);

		sb.append(_FILTER_SQL_COUNT_LAYOUTPAGETEMPLATEENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_C_T_D_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_T_D_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_G_C_T_D_TYPE_2_SQL);

		sb.append(_FINDER_COLUMN_G_C_T_D_DEFAULTTEMPLATE_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), LayoutPageTemplateEntry.class.getName(),
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

			queryPos.add(type);

			queryPos.add(defaultTemplate);

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

	private static final String _FINDER_COLUMN_G_C_T_D_GROUPID_2 =
		"layoutPageTemplateEntry.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_T_D_CLASSNAMEID_2 =
		"layoutPageTemplateEntry.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_T_D_TYPE_2 =
		"layoutPageTemplateEntry.type = ? AND ";

	private static final String _FINDER_COLUMN_G_C_T_D_TYPE_2_SQL =
		"layoutPageTemplateEntry.type_ = ? AND ";

	private static final String _FINDER_COLUMN_G_C_T_D_DEFAULTTEMPLATE_2 =
		"layoutPageTemplateEntry.defaultTemplate = ?";

	private FinderPath _finderPathWithPaginationFindByG_T_LikeN_S;
	private FinderPath _finderPathWithPaginationCountByG_T_LikeN_S;

	/**
	 * Returns all the layout page template entries where groupId = &#63; and name LIKE &#63; and type = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param type the type
	 * @param status the status
	 * @return the matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_T_LikeN_S(
		long groupId, String name, int type, int status) {

		return findByG_T_LikeN_S(
			groupId, name, type, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the layout page template entries where groupId = &#63; and name LIKE &#63; and type = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param type the type
	 * @param status the status
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @return the range of matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_T_LikeN_S(
		long groupId, String name, int type, int status, int start, int end) {

		return findByG_T_LikeN_S(groupId, name, type, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout page template entries where groupId = &#63; and name LIKE &#63; and type = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param type the type
	 * @param status the status
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_T_LikeN_S(
		long groupId, String name, int type, int status, int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		return findByG_T_LikeN_S(
			groupId, name, type, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout page template entries where groupId = &#63; and name LIKE &#63; and type = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param type the type
	 * @param status the status
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_T_LikeN_S(
		long groupId, String name, int type, int status, int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator,
		boolean useFinderCache) {

		name = Objects.toString(name, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			LayoutPageTemplateEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByG_T_LikeN_S;
		finderArgs = new Object[] {
			groupId, name, type, status, start, end, orderByComparator
		};

		List<LayoutPageTemplateEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<LayoutPageTemplateEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutPageTemplateEntry layoutPageTemplateEntry : list) {
					if ((groupId != layoutPageTemplateEntry.getGroupId()) ||
						!StringUtil.wildcardMatches(
							layoutPageTemplateEntry.getName(), name, '_', '%',
							'\\', true) ||
						(type != layoutPageTemplateEntry.getType()) ||
						(status != layoutPageTemplateEntry.getStatus())) {

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

			sb.append(_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_T_LIKEN_S_GROUPID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_T_LIKEN_S_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_G_T_LIKEN_S_NAME_2);
			}

			sb.append(_FINDER_COLUMN_G_T_LIKEN_S_TYPE_2);

			sb.append(_FINDER_COLUMN_G_T_LIKEN_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
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

				queryPos.add(type);

				queryPos.add(status);

				list = (List<LayoutPageTemplateEntry>)QueryUtil.list(
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
	 * Returns the first layout page template entry in the ordered set where groupId = &#63; and name LIKE &#63; and type = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry findByG_T_LikeN_S_First(
			long groupId, String name, int type, int status,
			OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			fetchByG_T_LikeN_S_First(
				groupId, name, type, status, orderByComparator);

		if (layoutPageTemplateEntry != null) {
			return layoutPageTemplateEntry;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", nameLIKE");
		sb.append(name);

		sb.append(", type=");
		sb.append(type);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchPageTemplateEntryException(sb.toString());
	}

	/**
	 * Returns the first layout page template entry in the ordered set where groupId = &#63; and name LIKE &#63; and type = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template entry, or <code>null</code> if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry fetchByG_T_LikeN_S_First(
		long groupId, String name, int type, int status,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		List<LayoutPageTemplateEntry> list = findByG_T_LikeN_S(
			groupId, name, type, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout page template entry in the ordered set where groupId = &#63; and name LIKE &#63; and type = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry findByG_T_LikeN_S_Last(
			long groupId, String name, int type, int status,
			OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			fetchByG_T_LikeN_S_Last(
				groupId, name, type, status, orderByComparator);

		if (layoutPageTemplateEntry != null) {
			return layoutPageTemplateEntry;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", nameLIKE");
		sb.append(name);

		sb.append(", type=");
		sb.append(type);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchPageTemplateEntryException(sb.toString());
	}

	/**
	 * Returns the last layout page template entry in the ordered set where groupId = &#63; and name LIKE &#63; and type = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template entry, or <code>null</code> if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry fetchByG_T_LikeN_S_Last(
		long groupId, String name, int type, int status,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		int count = countByG_T_LikeN_S(groupId, name, type, status);

		if (count == 0) {
			return null;
		}

		List<LayoutPageTemplateEntry> list = findByG_T_LikeN_S(
			groupId, name, type, status, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout page template entries before and after the current layout page template entry in the ordered set where groupId = &#63; and name LIKE &#63; and type = &#63; and status = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the primary key of the current layout page template entry
	 * @param groupId the group ID
	 * @param name the name
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a layout page template entry with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateEntry[] findByG_T_LikeN_S_PrevAndNext(
			long layoutPageTemplateEntryId, long groupId, String name, int type,
			int status,
			OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {

		name = Objects.toString(name, "");

		LayoutPageTemplateEntry layoutPageTemplateEntry = findByPrimaryKey(
			layoutPageTemplateEntryId);

		Session session = null;

		try {
			session = openSession();

			LayoutPageTemplateEntry[] array =
				new LayoutPageTemplateEntryImpl[3];

			array[0] = getByG_T_LikeN_S_PrevAndNext(
				session, layoutPageTemplateEntry, groupId, name, type, status,
				orderByComparator, true);

			array[1] = layoutPageTemplateEntry;

			array[2] = getByG_T_LikeN_S_PrevAndNext(
				session, layoutPageTemplateEntry, groupId, name, type, status,
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

	protected LayoutPageTemplateEntry getByG_T_LikeN_S_PrevAndNext(
		Session session, LayoutPageTemplateEntry layoutPageTemplateEntry,
		long groupId, String name, int type, int status,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_T_LIKEN_S_GROUPID_2);

		boolean bindName = false;

		if (name.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_T_LIKEN_S_NAME_3);
		}
		else {
			bindName = true;

			sb.append(_FINDER_COLUMN_G_T_LIKEN_S_NAME_2);
		}

		sb.append(_FINDER_COLUMN_G_T_LIKEN_S_TYPE_2);

		sb.append(_FINDER_COLUMN_G_T_LIKEN_S_STATUS_2);

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
			sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
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

		queryPos.add(type);

		queryPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutPageTemplateEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<LayoutPageTemplateEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the layout page template entries that the user has permission to view where groupId = &#63; and name LIKE &#63; and type = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param type the type
	 * @param status the status
	 * @return the matching layout page template entries that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateEntry> filterFindByG_T_LikeN_S(
		long groupId, String name, int type, int status) {

		return filterFindByG_T_LikeN_S(
			groupId, name, type, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the layout page template entries that the user has permission to view where groupId = &#63; and name LIKE &#63; and type = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param type the type
	 * @param status the status
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @return the range of matching layout page template entries that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateEntry> filterFindByG_T_LikeN_S(
		long groupId, String name, int type, int status, int start, int end) {

		return filterFindByG_T_LikeN_S(
			groupId, name, type, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout page template entries that the user has permissions to view where groupId = &#63; and name LIKE &#63; and type = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param type the type
	 * @param status the status
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template entries that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateEntry> filterFindByG_T_LikeN_S(
		long groupId, String name, int type, int status, int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_T_LikeN_S(
				groupId, name, type, status, start, end, orderByComparator);
		}

		name = Objects.toString(name, "");

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(7);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_T_LIKEN_S_GROUPID_2);

		boolean bindName = false;

		if (name.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_T_LIKEN_S_NAME_3);
		}
		else {
			bindName = true;

			sb.append(_FINDER_COLUMN_G_T_LIKEN_S_NAME_2);
		}

		sb.append(_FINDER_COLUMN_G_T_LIKEN_S_TYPE_2_SQL);

		sb.append(_FINDER_COLUMN_G_T_LIKEN_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), LayoutPageTemplateEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, LayoutPageTemplateEntryImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, LayoutPageTemplateEntryImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			if (bindName) {
				queryPos.add(name);
			}

			queryPos.add(type);

			queryPos.add(status);

			return (List<LayoutPageTemplateEntry>)QueryUtil.list(
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
	 * Returns the layout page template entries before and after the current layout page template entry in the ordered set of layout page template entries that the user has permission to view where groupId = &#63; and name LIKE &#63; and type = &#63; and status = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the primary key of the current layout page template entry
	 * @param groupId the group ID
	 * @param name the name
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a layout page template entry with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateEntry[] filterFindByG_T_LikeN_S_PrevAndNext(
			long layoutPageTemplateEntryId, long groupId, String name, int type,
			int status,
			OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_T_LikeN_S_PrevAndNext(
				layoutPageTemplateEntryId, groupId, name, type, status,
				orderByComparator);
		}

		name = Objects.toString(name, "");

		LayoutPageTemplateEntry layoutPageTemplateEntry = findByPrimaryKey(
			layoutPageTemplateEntryId);

		Session session = null;

		try {
			session = openSession();

			LayoutPageTemplateEntry[] array =
				new LayoutPageTemplateEntryImpl[3];

			array[0] = filterGetByG_T_LikeN_S_PrevAndNext(
				session, layoutPageTemplateEntry, groupId, name, type, status,
				orderByComparator, true);

			array[1] = layoutPageTemplateEntry;

			array[2] = filterGetByG_T_LikeN_S_PrevAndNext(
				session, layoutPageTemplateEntry, groupId, name, type, status,
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

	protected LayoutPageTemplateEntry filterGetByG_T_LikeN_S_PrevAndNext(
		Session session, LayoutPageTemplateEntry layoutPageTemplateEntry,
		long groupId, String name, int type, int status,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator,
		boolean previous) {

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
			sb.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_T_LIKEN_S_GROUPID_2);

		boolean bindName = false;

		if (name.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_T_LIKEN_S_NAME_3);
		}
		else {
			bindName = true;

			sb.append(_FINDER_COLUMN_G_T_LIKEN_S_NAME_2);
		}

		sb.append(_FINDER_COLUMN_G_T_LIKEN_S_TYPE_2_SQL);

		sb.append(_FINDER_COLUMN_G_T_LIKEN_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), LayoutPageTemplateEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(
				_FILTER_ENTITY_ALIAS, LayoutPageTemplateEntryImpl.class);
		}
		else {
			sqlQuery.addEntity(
				_FILTER_ENTITY_TABLE, LayoutPageTemplateEntryImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(groupId);

		if (bindName) {
			queryPos.add(name);
		}

		queryPos.add(type);

		queryPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutPageTemplateEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<LayoutPageTemplateEntry> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout page template entries where groupId = &#63; and name LIKE &#63; and type = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param type the type
	 * @param status the status
	 */
	@Override
	public void removeByG_T_LikeN_S(
		long groupId, String name, int type, int status) {

		for (LayoutPageTemplateEntry layoutPageTemplateEntry :
				findByG_T_LikeN_S(
					groupId, name, type, status, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(layoutPageTemplateEntry);
		}
	}

	/**
	 * Returns the number of layout page template entries where groupId = &#63; and name LIKE &#63; and type = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param type the type
	 * @param status the status
	 * @return the number of matching layout page template entries
	 */
	@Override
	public int countByG_T_LikeN_S(
		long groupId, String name, int type, int status) {

		name = Objects.toString(name, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			LayoutPageTemplateEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathWithPaginationCountByG_T_LikeN_S;

			finderArgs = new Object[] {groupId, name, type, status};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_COUNT_LAYOUTPAGETEMPLATEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_T_LIKEN_S_GROUPID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_T_LIKEN_S_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_G_T_LIKEN_S_NAME_2);
			}

			sb.append(_FINDER_COLUMN_G_T_LIKEN_S_TYPE_2);

			sb.append(_FINDER_COLUMN_G_T_LIKEN_S_STATUS_2);

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

				queryPos.add(type);

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
	 * Returns the number of layout page template entries that the user has permission to view where groupId = &#63; and name LIKE &#63; and type = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param type the type
	 * @param status the status
	 * @return the number of matching layout page template entries that the user has permission to view
	 */
	@Override
	public int filterCountByG_T_LikeN_S(
		long groupId, String name, int type, int status) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_T_LikeN_S(groupId, name, type, status);
		}

		name = Objects.toString(name, "");

		StringBundler sb = new StringBundler(5);

		sb.append(_FILTER_SQL_COUNT_LAYOUTPAGETEMPLATEENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_T_LIKEN_S_GROUPID_2);

		boolean bindName = false;

		if (name.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_T_LIKEN_S_NAME_3);
		}
		else {
			bindName = true;

			sb.append(_FINDER_COLUMN_G_T_LIKEN_S_NAME_2);
		}

		sb.append(_FINDER_COLUMN_G_T_LIKEN_S_TYPE_2_SQL);

		sb.append(_FINDER_COLUMN_G_T_LIKEN_S_STATUS_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), LayoutPageTemplateEntry.class.getName(),
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

			queryPos.add(type);

			queryPos.add(status);

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

	private static final String _FINDER_COLUMN_G_T_LIKEN_S_GROUPID_2 =
		"layoutPageTemplateEntry.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_T_LIKEN_S_NAME_2 =
		"layoutPageTemplateEntry.name LIKE ? AND ";

	private static final String _FINDER_COLUMN_G_T_LIKEN_S_NAME_3 =
		"(layoutPageTemplateEntry.name IS NULL OR layoutPageTemplateEntry.name LIKE '') AND ";

	private static final String _FINDER_COLUMN_G_T_LIKEN_S_TYPE_2 =
		"layoutPageTemplateEntry.type = ? AND ";

	private static final String _FINDER_COLUMN_G_T_LIKEN_S_TYPE_2_SQL =
		"layoutPageTemplateEntry.type_ = ? AND ";

	private static final String _FINDER_COLUMN_G_T_LIKEN_S_STATUS_2 =
		"layoutPageTemplateEntry.status = ?";

	private FinderPath _finderPathWithPaginationFindByG_T_D_S;
	private FinderPath _finderPathWithoutPaginationFindByG_T_D_S;
	private FinderPath _finderPathCountByG_T_D_S;

	/**
	 * Returns all the layout page template entries where groupId = &#63; and type = &#63; and defaultTemplate = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param defaultTemplate the default template
	 * @param status the status
	 * @return the matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_T_D_S(
		long groupId, int type, boolean defaultTemplate, int status) {

		return findByG_T_D_S(
			groupId, type, defaultTemplate, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout page template entries where groupId = &#63; and type = &#63; and defaultTemplate = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param defaultTemplate the default template
	 * @param status the status
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @return the range of matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_T_D_S(
		long groupId, int type, boolean defaultTemplate, int status, int start,
		int end) {

		return findByG_T_D_S(
			groupId, type, defaultTemplate, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout page template entries where groupId = &#63; and type = &#63; and defaultTemplate = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param defaultTemplate the default template
	 * @param status the status
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_T_D_S(
		long groupId, int type, boolean defaultTemplate, int status, int start,
		int end, OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		return findByG_T_D_S(
			groupId, type, defaultTemplate, status, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout page template entries where groupId = &#63; and type = &#63; and defaultTemplate = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param defaultTemplate the default template
	 * @param status the status
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_T_D_S(
		long groupId, int type, boolean defaultTemplate, int status, int start,
		int end, OrderByComparator<LayoutPageTemplateEntry> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			LayoutPageTemplateEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByG_T_D_S;
				finderArgs = new Object[] {
					groupId, type, defaultTemplate, status
				};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByG_T_D_S;
			finderArgs = new Object[] {
				groupId, type, defaultTemplate, status, start, end,
				orderByComparator
			};
		}

		List<LayoutPageTemplateEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<LayoutPageTemplateEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutPageTemplateEntry layoutPageTemplateEntry : list) {
					if ((groupId != layoutPageTemplateEntry.getGroupId()) ||
						(type != layoutPageTemplateEntry.getType()) ||
						(defaultTemplate !=
							layoutPageTemplateEntry.isDefaultTemplate()) ||
						(status != layoutPageTemplateEntry.getStatus())) {

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

			sb.append(_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_T_D_S_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_T_D_S_TYPE_2);

			sb.append(_FINDER_COLUMN_G_T_D_S_DEFAULTTEMPLATE_2);

			sb.append(_FINDER_COLUMN_G_T_D_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(type);

				queryPos.add(defaultTemplate);

				queryPos.add(status);

				list = (List<LayoutPageTemplateEntry>)QueryUtil.list(
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
	 * Returns the first layout page template entry in the ordered set where groupId = &#63; and type = &#63; and defaultTemplate = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param defaultTemplate the default template
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry findByG_T_D_S_First(
			long groupId, int type, boolean defaultTemplate, int status,
			OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {

		LayoutPageTemplateEntry layoutPageTemplateEntry = fetchByG_T_D_S_First(
			groupId, type, defaultTemplate, status, orderByComparator);

		if (layoutPageTemplateEntry != null) {
			return layoutPageTemplateEntry;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", type=");
		sb.append(type);

		sb.append(", defaultTemplate=");
		sb.append(defaultTemplate);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchPageTemplateEntryException(sb.toString());
	}

	/**
	 * Returns the first layout page template entry in the ordered set where groupId = &#63; and type = &#63; and defaultTemplate = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param defaultTemplate the default template
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template entry, or <code>null</code> if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry fetchByG_T_D_S_First(
		long groupId, int type, boolean defaultTemplate, int status,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		List<LayoutPageTemplateEntry> list = findByG_T_D_S(
			groupId, type, defaultTemplate, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout page template entry in the ordered set where groupId = &#63; and type = &#63; and defaultTemplate = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param defaultTemplate the default template
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry findByG_T_D_S_Last(
			long groupId, int type, boolean defaultTemplate, int status,
			OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {

		LayoutPageTemplateEntry layoutPageTemplateEntry = fetchByG_T_D_S_Last(
			groupId, type, defaultTemplate, status, orderByComparator);

		if (layoutPageTemplateEntry != null) {
			return layoutPageTemplateEntry;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", type=");
		sb.append(type);

		sb.append(", defaultTemplate=");
		sb.append(defaultTemplate);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchPageTemplateEntryException(sb.toString());
	}

	/**
	 * Returns the last layout page template entry in the ordered set where groupId = &#63; and type = &#63; and defaultTemplate = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param defaultTemplate the default template
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template entry, or <code>null</code> if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry fetchByG_T_D_S_Last(
		long groupId, int type, boolean defaultTemplate, int status,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		int count = countByG_T_D_S(groupId, type, defaultTemplate, status);

		if (count == 0) {
			return null;
		}

		List<LayoutPageTemplateEntry> list = findByG_T_D_S(
			groupId, type, defaultTemplate, status, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout page template entries before and after the current layout page template entry in the ordered set where groupId = &#63; and type = &#63; and defaultTemplate = &#63; and status = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the primary key of the current layout page template entry
	 * @param groupId the group ID
	 * @param type the type
	 * @param defaultTemplate the default template
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a layout page template entry with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateEntry[] findByG_T_D_S_PrevAndNext(
			long layoutPageTemplateEntryId, long groupId, int type,
			boolean defaultTemplate, int status,
			OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {

		LayoutPageTemplateEntry layoutPageTemplateEntry = findByPrimaryKey(
			layoutPageTemplateEntryId);

		Session session = null;

		try {
			session = openSession();

			LayoutPageTemplateEntry[] array =
				new LayoutPageTemplateEntryImpl[3];

			array[0] = getByG_T_D_S_PrevAndNext(
				session, layoutPageTemplateEntry, groupId, type,
				defaultTemplate, status, orderByComparator, true);

			array[1] = layoutPageTemplateEntry;

			array[2] = getByG_T_D_S_PrevAndNext(
				session, layoutPageTemplateEntry, groupId, type,
				defaultTemplate, status, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutPageTemplateEntry getByG_T_D_S_PrevAndNext(
		Session session, LayoutPageTemplateEntry layoutPageTemplateEntry,
		long groupId, int type, boolean defaultTemplate, int status,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_T_D_S_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_T_D_S_TYPE_2);

		sb.append(_FINDER_COLUMN_G_T_D_S_DEFAULTTEMPLATE_2);

		sb.append(_FINDER_COLUMN_G_T_D_S_STATUS_2);

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
			sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(type);

		queryPos.add(defaultTemplate);

		queryPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutPageTemplateEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<LayoutPageTemplateEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the layout page template entries that the user has permission to view where groupId = &#63; and type = &#63; and defaultTemplate = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param defaultTemplate the default template
	 * @param status the status
	 * @return the matching layout page template entries that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateEntry> filterFindByG_T_D_S(
		long groupId, int type, boolean defaultTemplate, int status) {

		return filterFindByG_T_D_S(
			groupId, type, defaultTemplate, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout page template entries that the user has permission to view where groupId = &#63; and type = &#63; and defaultTemplate = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param defaultTemplate the default template
	 * @param status the status
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @return the range of matching layout page template entries that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateEntry> filterFindByG_T_D_S(
		long groupId, int type, boolean defaultTemplate, int status, int start,
		int end) {

		return filterFindByG_T_D_S(
			groupId, type, defaultTemplate, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout page template entries that the user has permissions to view where groupId = &#63; and type = &#63; and defaultTemplate = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param defaultTemplate the default template
	 * @param status the status
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template entries that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateEntry> filterFindByG_T_D_S(
		long groupId, int type, boolean defaultTemplate, int status, int start,
		int end, OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_T_D_S(
				groupId, type, defaultTemplate, status, start, end,
				orderByComparator);
		}

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(7);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_T_D_S_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_T_D_S_TYPE_2_SQL);

		sb.append(_FINDER_COLUMN_G_T_D_S_DEFAULTTEMPLATE_2);

		sb.append(_FINDER_COLUMN_G_T_D_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), LayoutPageTemplateEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, LayoutPageTemplateEntryImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, LayoutPageTemplateEntryImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(type);

			queryPos.add(defaultTemplate);

			queryPos.add(status);

			return (List<LayoutPageTemplateEntry>)QueryUtil.list(
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
	 * Returns the layout page template entries before and after the current layout page template entry in the ordered set of layout page template entries that the user has permission to view where groupId = &#63; and type = &#63; and defaultTemplate = &#63; and status = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the primary key of the current layout page template entry
	 * @param groupId the group ID
	 * @param type the type
	 * @param defaultTemplate the default template
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a layout page template entry with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateEntry[] filterFindByG_T_D_S_PrevAndNext(
			long layoutPageTemplateEntryId, long groupId, int type,
			boolean defaultTemplate, int status,
			OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_T_D_S_PrevAndNext(
				layoutPageTemplateEntryId, groupId, type, defaultTemplate,
				status, orderByComparator);
		}

		LayoutPageTemplateEntry layoutPageTemplateEntry = findByPrimaryKey(
			layoutPageTemplateEntryId);

		Session session = null;

		try {
			session = openSession();

			LayoutPageTemplateEntry[] array =
				new LayoutPageTemplateEntryImpl[3];

			array[0] = filterGetByG_T_D_S_PrevAndNext(
				session, layoutPageTemplateEntry, groupId, type,
				defaultTemplate, status, orderByComparator, true);

			array[1] = layoutPageTemplateEntry;

			array[2] = filterGetByG_T_D_S_PrevAndNext(
				session, layoutPageTemplateEntry, groupId, type,
				defaultTemplate, status, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutPageTemplateEntry filterGetByG_T_D_S_PrevAndNext(
		Session session, LayoutPageTemplateEntry layoutPageTemplateEntry,
		long groupId, int type, boolean defaultTemplate, int status,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator,
		boolean previous) {

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
			sb.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_T_D_S_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_T_D_S_TYPE_2_SQL);

		sb.append(_FINDER_COLUMN_G_T_D_S_DEFAULTTEMPLATE_2);

		sb.append(_FINDER_COLUMN_G_T_D_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), LayoutPageTemplateEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(
				_FILTER_ENTITY_ALIAS, LayoutPageTemplateEntryImpl.class);
		}
		else {
			sqlQuery.addEntity(
				_FILTER_ENTITY_TABLE, LayoutPageTemplateEntryImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(groupId);

		queryPos.add(type);

		queryPos.add(defaultTemplate);

		queryPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutPageTemplateEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<LayoutPageTemplateEntry> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout page template entries where groupId = &#63; and type = &#63; and defaultTemplate = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param defaultTemplate the default template
	 * @param status the status
	 */
	@Override
	public void removeByG_T_D_S(
		long groupId, int type, boolean defaultTemplate, int status) {

		for (LayoutPageTemplateEntry layoutPageTemplateEntry :
				findByG_T_D_S(
					groupId, type, defaultTemplate, status, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(layoutPageTemplateEntry);
		}
	}

	/**
	 * Returns the number of layout page template entries where groupId = &#63; and type = &#63; and defaultTemplate = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param defaultTemplate the default template
	 * @param status the status
	 * @return the number of matching layout page template entries
	 */
	@Override
	public int countByG_T_D_S(
		long groupId, int type, boolean defaultTemplate, int status) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			LayoutPageTemplateEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_T_D_S;

			finderArgs = new Object[] {groupId, type, defaultTemplate, status};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_COUNT_LAYOUTPAGETEMPLATEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_T_D_S_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_T_D_S_TYPE_2);

			sb.append(_FINDER_COLUMN_G_T_D_S_DEFAULTTEMPLATE_2);

			sb.append(_FINDER_COLUMN_G_T_D_S_STATUS_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(type);

				queryPos.add(defaultTemplate);

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
	 * Returns the number of layout page template entries that the user has permission to view where groupId = &#63; and type = &#63; and defaultTemplate = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param defaultTemplate the default template
	 * @param status the status
	 * @return the number of matching layout page template entries that the user has permission to view
	 */
	@Override
	public int filterCountByG_T_D_S(
		long groupId, int type, boolean defaultTemplate, int status) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_T_D_S(groupId, type, defaultTemplate, status);
		}

		StringBundler sb = new StringBundler(5);

		sb.append(_FILTER_SQL_COUNT_LAYOUTPAGETEMPLATEENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_T_D_S_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_T_D_S_TYPE_2_SQL);

		sb.append(_FINDER_COLUMN_G_T_D_S_DEFAULTTEMPLATE_2);

		sb.append(_FINDER_COLUMN_G_T_D_S_STATUS_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), LayoutPageTemplateEntry.class.getName(),
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

			queryPos.add(defaultTemplate);

			queryPos.add(status);

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

	private static final String _FINDER_COLUMN_G_T_D_S_GROUPID_2 =
		"layoutPageTemplateEntry.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_T_D_S_TYPE_2 =
		"layoutPageTemplateEntry.type = ? AND ";

	private static final String _FINDER_COLUMN_G_T_D_S_TYPE_2_SQL =
		"layoutPageTemplateEntry.type_ = ? AND ";

	private static final String _FINDER_COLUMN_G_T_D_S_DEFAULTTEMPLATE_2 =
		"layoutPageTemplateEntry.defaultTemplate = ? AND ";

	private static final String _FINDER_COLUMN_G_T_D_S_STATUS_2 =
		"layoutPageTemplateEntry.status = ?";

	private FinderPath _finderPathWithPaginationFindByG_C_C_LikeN_T;
	private FinderPath _finderPathWithPaginationCountByG_C_C_LikeN_T;

	/**
	 * Returns all the layout page template entries where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and name LIKE &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param name the name
	 * @param type the type
	 * @return the matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_C_C_LikeN_T(
		long groupId, long classNameId, long classTypeId, String name,
		int type) {

		return findByG_C_C_LikeN_T(
			groupId, classNameId, classTypeId, name, type, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout page template entries where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and name LIKE &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param name the name
	 * @param type the type
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @return the range of matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_C_C_LikeN_T(
		long groupId, long classNameId, long classTypeId, String name, int type,
		int start, int end) {

		return findByG_C_C_LikeN_T(
			groupId, classNameId, classTypeId, name, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout page template entries where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and name LIKE &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param name the name
	 * @param type the type
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_C_C_LikeN_T(
		long groupId, long classNameId, long classTypeId, String name, int type,
		int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		return findByG_C_C_LikeN_T(
			groupId, classNameId, classTypeId, name, type, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout page template entries where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and name LIKE &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param name the name
	 * @param type the type
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_C_C_LikeN_T(
		long groupId, long classNameId, long classTypeId, String name, int type,
		int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator,
		boolean useFinderCache) {

		name = Objects.toString(name, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			LayoutPageTemplateEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByG_C_C_LikeN_T;
		finderArgs = new Object[] {
			groupId, classNameId, classTypeId, name, type, start, end,
			orderByComparator
		};

		List<LayoutPageTemplateEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<LayoutPageTemplateEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutPageTemplateEntry layoutPageTemplateEntry : list) {
					if ((groupId != layoutPageTemplateEntry.getGroupId()) ||
						(classNameId !=
							layoutPageTemplateEntry.getClassNameId()) ||
						(classTypeId !=
							layoutPageTemplateEntry.getClassTypeId()) ||
						!StringUtil.wildcardMatches(
							layoutPageTemplateEntry.getName(), name, '_', '%',
							'\\', true) ||
						(type != layoutPageTemplateEntry.getType())) {

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
					7 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(7);
			}

			sb.append(_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_C_C_LIKEN_T_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_C_LIKEN_T_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_G_C_C_LIKEN_T_CLASSTYPEID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_C_C_LIKEN_T_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_G_C_C_LIKEN_T_NAME_2);
			}

			sb.append(_FINDER_COLUMN_G_C_C_LIKEN_T_TYPE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(classNameId);

				queryPos.add(classTypeId);

				if (bindName) {
					queryPos.add(name);
				}

				queryPos.add(type);

				list = (List<LayoutPageTemplateEntry>)QueryUtil.list(
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
	 * Returns the first layout page template entry in the ordered set where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and name LIKE &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param name the name
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry findByG_C_C_LikeN_T_First(
			long groupId, long classNameId, long classTypeId, String name,
			int type,
			OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			fetchByG_C_C_LikeN_T_First(
				groupId, classNameId, classTypeId, name, type,
				orderByComparator);

		if (layoutPageTemplateEntry != null) {
			return layoutPageTemplateEntry;
		}

		StringBundler sb = new StringBundler(12);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append(", classTypeId=");
		sb.append(classTypeId);

		sb.append(", nameLIKE");
		sb.append(name);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchPageTemplateEntryException(sb.toString());
	}

	/**
	 * Returns the first layout page template entry in the ordered set where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and name LIKE &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param name the name
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template entry, or <code>null</code> if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry fetchByG_C_C_LikeN_T_First(
		long groupId, long classNameId, long classTypeId, String name, int type,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		List<LayoutPageTemplateEntry> list = findByG_C_C_LikeN_T(
			groupId, classNameId, classTypeId, name, type, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout page template entry in the ordered set where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and name LIKE &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param name the name
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry findByG_C_C_LikeN_T_Last(
			long groupId, long classNameId, long classTypeId, String name,
			int type,
			OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			fetchByG_C_C_LikeN_T_Last(
				groupId, classNameId, classTypeId, name, type,
				orderByComparator);

		if (layoutPageTemplateEntry != null) {
			return layoutPageTemplateEntry;
		}

		StringBundler sb = new StringBundler(12);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append(", classTypeId=");
		sb.append(classTypeId);

		sb.append(", nameLIKE");
		sb.append(name);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchPageTemplateEntryException(sb.toString());
	}

	/**
	 * Returns the last layout page template entry in the ordered set where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and name LIKE &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param name the name
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template entry, or <code>null</code> if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry fetchByG_C_C_LikeN_T_Last(
		long groupId, long classNameId, long classTypeId, String name, int type,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		int count = countByG_C_C_LikeN_T(
			groupId, classNameId, classTypeId, name, type);

		if (count == 0) {
			return null;
		}

		List<LayoutPageTemplateEntry> list = findByG_C_C_LikeN_T(
			groupId, classNameId, classTypeId, name, type, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout page template entries before and after the current layout page template entry in the ordered set where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and name LIKE &#63; and type = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the primary key of the current layout page template entry
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param name the name
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a layout page template entry with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateEntry[] findByG_C_C_LikeN_T_PrevAndNext(
			long layoutPageTemplateEntryId, long groupId, long classNameId,
			long classTypeId, String name, int type,
			OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {

		name = Objects.toString(name, "");

		LayoutPageTemplateEntry layoutPageTemplateEntry = findByPrimaryKey(
			layoutPageTemplateEntryId);

		Session session = null;

		try {
			session = openSession();

			LayoutPageTemplateEntry[] array =
				new LayoutPageTemplateEntryImpl[3];

			array[0] = getByG_C_C_LikeN_T_PrevAndNext(
				session, layoutPageTemplateEntry, groupId, classNameId,
				classTypeId, name, type, orderByComparator, true);

			array[1] = layoutPageTemplateEntry;

			array[2] = getByG_C_C_LikeN_T_PrevAndNext(
				session, layoutPageTemplateEntry, groupId, classNameId,
				classTypeId, name, type, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutPageTemplateEntry getByG_C_C_LikeN_T_PrevAndNext(
		Session session, LayoutPageTemplateEntry layoutPageTemplateEntry,
		long groupId, long classNameId, long classTypeId, String name, int type,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				8 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(7);
		}

		sb.append(_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_C_C_LIKEN_T_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_C_LIKEN_T_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_G_C_C_LIKEN_T_CLASSTYPEID_2);

		boolean bindName = false;

		if (name.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_C_C_LIKEN_T_NAME_3);
		}
		else {
			bindName = true;

			sb.append(_FINDER_COLUMN_G_C_C_LIKEN_T_NAME_2);
		}

		sb.append(_FINDER_COLUMN_G_C_C_LIKEN_T_TYPE_2);

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
			sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(classNameId);

		queryPos.add(classTypeId);

		if (bindName) {
			queryPos.add(name);
		}

		queryPos.add(type);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutPageTemplateEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<LayoutPageTemplateEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the layout page template entries that the user has permission to view where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and name LIKE &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param name the name
	 * @param type the type
	 * @return the matching layout page template entries that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateEntry> filterFindByG_C_C_LikeN_T(
		long groupId, long classNameId, long classTypeId, String name,
		int type) {

		return filterFindByG_C_C_LikeN_T(
			groupId, classNameId, classTypeId, name, type, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout page template entries that the user has permission to view where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and name LIKE &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param name the name
	 * @param type the type
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @return the range of matching layout page template entries that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateEntry> filterFindByG_C_C_LikeN_T(
		long groupId, long classNameId, long classTypeId, String name, int type,
		int start, int end) {

		return filterFindByG_C_C_LikeN_T(
			groupId, classNameId, classTypeId, name, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout page template entries that the user has permissions to view where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and name LIKE &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param name the name
	 * @param type the type
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template entries that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateEntry> filterFindByG_C_C_LikeN_T(
		long groupId, long classNameId, long classTypeId, String name, int type,
		int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C_C_LikeN_T(
				groupId, classNameId, classTypeId, name, type, start, end,
				orderByComparator);
		}

		name = Objects.toString(name, "");

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				7 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(8);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_C_C_LIKEN_T_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_C_LIKEN_T_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_G_C_C_LIKEN_T_CLASSTYPEID_2);

		boolean bindName = false;

		if (name.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_C_C_LIKEN_T_NAME_3);
		}
		else {
			bindName = true;

			sb.append(_FINDER_COLUMN_G_C_C_LIKEN_T_NAME_2);
		}

		sb.append(_FINDER_COLUMN_G_C_C_LIKEN_T_TYPE_2_SQL);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), LayoutPageTemplateEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, LayoutPageTemplateEntryImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, LayoutPageTemplateEntryImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(classNameId);

			queryPos.add(classTypeId);

			if (bindName) {
				queryPos.add(name);
			}

			queryPos.add(type);

			return (List<LayoutPageTemplateEntry>)QueryUtil.list(
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
	 * Returns the layout page template entries before and after the current layout page template entry in the ordered set of layout page template entries that the user has permission to view where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and name LIKE &#63; and type = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the primary key of the current layout page template entry
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param name the name
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a layout page template entry with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateEntry[] filterFindByG_C_C_LikeN_T_PrevAndNext(
			long layoutPageTemplateEntryId, long groupId, long classNameId,
			long classTypeId, String name, int type,
			OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C_C_LikeN_T_PrevAndNext(
				layoutPageTemplateEntryId, groupId, classNameId, classTypeId,
				name, type, orderByComparator);
		}

		name = Objects.toString(name, "");

		LayoutPageTemplateEntry layoutPageTemplateEntry = findByPrimaryKey(
			layoutPageTemplateEntryId);

		Session session = null;

		try {
			session = openSession();

			LayoutPageTemplateEntry[] array =
				new LayoutPageTemplateEntryImpl[3];

			array[0] = filterGetByG_C_C_LikeN_T_PrevAndNext(
				session, layoutPageTemplateEntry, groupId, classNameId,
				classTypeId, name, type, orderByComparator, true);

			array[1] = layoutPageTemplateEntry;

			array[2] = filterGetByG_C_C_LikeN_T_PrevAndNext(
				session, layoutPageTemplateEntry, groupId, classNameId,
				classTypeId, name, type, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutPageTemplateEntry filterGetByG_C_C_LikeN_T_PrevAndNext(
		Session session, LayoutPageTemplateEntry layoutPageTemplateEntry,
		long groupId, long classNameId, long classTypeId, String name, int type,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				9 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(8);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_C_C_LIKEN_T_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_C_LIKEN_T_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_G_C_C_LIKEN_T_CLASSTYPEID_2);

		boolean bindName = false;

		if (name.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_C_C_LIKEN_T_NAME_3);
		}
		else {
			bindName = true;

			sb.append(_FINDER_COLUMN_G_C_C_LIKEN_T_NAME_2);
		}

		sb.append(_FINDER_COLUMN_G_C_C_LIKEN_T_TYPE_2_SQL);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), LayoutPageTemplateEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(
				_FILTER_ENTITY_ALIAS, LayoutPageTemplateEntryImpl.class);
		}
		else {
			sqlQuery.addEntity(
				_FILTER_ENTITY_TABLE, LayoutPageTemplateEntryImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(groupId);

		queryPos.add(classNameId);

		queryPos.add(classTypeId);

		if (bindName) {
			queryPos.add(name);
		}

		queryPos.add(type);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutPageTemplateEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<LayoutPageTemplateEntry> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout page template entries where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and name LIKE &#63; and type = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param name the name
	 * @param type the type
	 */
	@Override
	public void removeByG_C_C_LikeN_T(
		long groupId, long classNameId, long classTypeId, String name,
		int type) {

		for (LayoutPageTemplateEntry layoutPageTemplateEntry :
				findByG_C_C_LikeN_T(
					groupId, classNameId, classTypeId, name, type,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(layoutPageTemplateEntry);
		}
	}

	/**
	 * Returns the number of layout page template entries where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and name LIKE &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param name the name
	 * @param type the type
	 * @return the number of matching layout page template entries
	 */
	@Override
	public int countByG_C_C_LikeN_T(
		long groupId, long classNameId, long classTypeId, String name,
		int type) {

		name = Objects.toString(name, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			LayoutPageTemplateEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathWithPaginationCountByG_C_C_LikeN_T;

			finderArgs = new Object[] {
				groupId, classNameId, classTypeId, name, type
			};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_SQL_COUNT_LAYOUTPAGETEMPLATEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_C_C_LIKEN_T_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_C_LIKEN_T_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_G_C_C_LIKEN_T_CLASSTYPEID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_C_C_LIKEN_T_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_G_C_C_LIKEN_T_NAME_2);
			}

			sb.append(_FINDER_COLUMN_G_C_C_LIKEN_T_TYPE_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(classNameId);

				queryPos.add(classTypeId);

				if (bindName) {
					queryPos.add(name);
				}

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
	 * Returns the number of layout page template entries that the user has permission to view where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and name LIKE &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param name the name
	 * @param type the type
	 * @return the number of matching layout page template entries that the user has permission to view
	 */
	@Override
	public int filterCountByG_C_C_LikeN_T(
		long groupId, long classNameId, long classTypeId, String name,
		int type) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_C_C_LikeN_T(
				groupId, classNameId, classTypeId, name, type);
		}

		name = Objects.toString(name, "");

		StringBundler sb = new StringBundler(6);

		sb.append(_FILTER_SQL_COUNT_LAYOUTPAGETEMPLATEENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_C_C_LIKEN_T_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_C_LIKEN_T_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_G_C_C_LIKEN_T_CLASSTYPEID_2);

		boolean bindName = false;

		if (name.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_C_C_LIKEN_T_NAME_3);
		}
		else {
			bindName = true;

			sb.append(_FINDER_COLUMN_G_C_C_LIKEN_T_NAME_2);
		}

		sb.append(_FINDER_COLUMN_G_C_C_LIKEN_T_TYPE_2_SQL);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), LayoutPageTemplateEntry.class.getName(),
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

			queryPos.add(classTypeId);

			if (bindName) {
				queryPos.add(name);
			}

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

	private static final String _FINDER_COLUMN_G_C_C_LIKEN_T_GROUPID_2 =
		"layoutPageTemplateEntry.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_LIKEN_T_CLASSNAMEID_2 =
		"layoutPageTemplateEntry.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_LIKEN_T_CLASSTYPEID_2 =
		"layoutPageTemplateEntry.classTypeId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_LIKEN_T_NAME_2 =
		"layoutPageTemplateEntry.name LIKE ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_LIKEN_T_NAME_3 =
		"(layoutPageTemplateEntry.name IS NULL OR layoutPageTemplateEntry.name LIKE '') AND ";

	private static final String _FINDER_COLUMN_G_C_C_LIKEN_T_TYPE_2 =
		"layoutPageTemplateEntry.type = ?";

	private static final String _FINDER_COLUMN_G_C_C_LIKEN_T_TYPE_2_SQL =
		"layoutPageTemplateEntry.type_ = ?";

	private FinderPath _finderPathWithPaginationFindByG_C_C_T_S;
	private FinderPath _finderPathWithoutPaginationFindByG_C_C_T_S;
	private FinderPath _finderPathCountByG_C_C_T_S;

	/**
	 * Returns all the layout page template entries where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param type the type
	 * @param status the status
	 * @return the matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_C_C_T_S(
		long groupId, long classNameId, long classTypeId, int type,
		int status) {

		return findByG_C_C_T_S(
			groupId, classNameId, classTypeId, type, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout page template entries where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and type = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param type the type
	 * @param status the status
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @return the range of matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_C_C_T_S(
		long groupId, long classNameId, long classTypeId, int type, int status,
		int start, int end) {

		return findByG_C_C_T_S(
			groupId, classNameId, classTypeId, type, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout page template entries where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and type = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param type the type
	 * @param status the status
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_C_C_T_S(
		long groupId, long classNameId, long classTypeId, int type, int status,
		int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		return findByG_C_C_T_S(
			groupId, classNameId, classTypeId, type, status, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout page template entries where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and type = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param type the type
	 * @param status the status
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_C_C_T_S(
		long groupId, long classNameId, long classTypeId, int type, int status,
		int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			LayoutPageTemplateEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByG_C_C_T_S;
				finderArgs = new Object[] {
					groupId, classNameId, classTypeId, type, status
				};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByG_C_C_T_S;
			finderArgs = new Object[] {
				groupId, classNameId, classTypeId, type, status, start, end,
				orderByComparator
			};
		}

		List<LayoutPageTemplateEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<LayoutPageTemplateEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutPageTemplateEntry layoutPageTemplateEntry : list) {
					if ((groupId != layoutPageTemplateEntry.getGroupId()) ||
						(classNameId !=
							layoutPageTemplateEntry.getClassNameId()) ||
						(classTypeId !=
							layoutPageTemplateEntry.getClassTypeId()) ||
						(type != layoutPageTemplateEntry.getType()) ||
						(status != layoutPageTemplateEntry.getStatus())) {

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
					7 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(7);
			}

			sb.append(_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_C_C_T_S_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_C_T_S_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_G_C_C_T_S_CLASSTYPEID_2);

			sb.append(_FINDER_COLUMN_G_C_C_T_S_TYPE_2);

			sb.append(_FINDER_COLUMN_G_C_C_T_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(classNameId);

				queryPos.add(classTypeId);

				queryPos.add(type);

				queryPos.add(status);

				list = (List<LayoutPageTemplateEntry>)QueryUtil.list(
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
	 * Returns the first layout page template entry in the ordered set where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry findByG_C_C_T_S_First(
			long groupId, long classNameId, long classTypeId, int type,
			int status,
			OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			fetchByG_C_C_T_S_First(
				groupId, classNameId, classTypeId, type, status,
				orderByComparator);

		if (layoutPageTemplateEntry != null) {
			return layoutPageTemplateEntry;
		}

		StringBundler sb = new StringBundler(12);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append(", classTypeId=");
		sb.append(classTypeId);

		sb.append(", type=");
		sb.append(type);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchPageTemplateEntryException(sb.toString());
	}

	/**
	 * Returns the first layout page template entry in the ordered set where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template entry, or <code>null</code> if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry fetchByG_C_C_T_S_First(
		long groupId, long classNameId, long classTypeId, int type, int status,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		List<LayoutPageTemplateEntry> list = findByG_C_C_T_S(
			groupId, classNameId, classTypeId, type, status, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout page template entry in the ordered set where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry findByG_C_C_T_S_Last(
			long groupId, long classNameId, long classTypeId, int type,
			int status,
			OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {

		LayoutPageTemplateEntry layoutPageTemplateEntry = fetchByG_C_C_T_S_Last(
			groupId, classNameId, classTypeId, type, status, orderByComparator);

		if (layoutPageTemplateEntry != null) {
			return layoutPageTemplateEntry;
		}

		StringBundler sb = new StringBundler(12);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append(", classTypeId=");
		sb.append(classTypeId);

		sb.append(", type=");
		sb.append(type);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchPageTemplateEntryException(sb.toString());
	}

	/**
	 * Returns the last layout page template entry in the ordered set where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template entry, or <code>null</code> if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry fetchByG_C_C_T_S_Last(
		long groupId, long classNameId, long classTypeId, int type, int status,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		int count = countByG_C_C_T_S(
			groupId, classNameId, classTypeId, type, status);

		if (count == 0) {
			return null;
		}

		List<LayoutPageTemplateEntry> list = findByG_C_C_T_S(
			groupId, classNameId, classTypeId, type, status, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout page template entries before and after the current layout page template entry in the ordered set where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the primary key of the current layout page template entry
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a layout page template entry with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateEntry[] findByG_C_C_T_S_PrevAndNext(
			long layoutPageTemplateEntryId, long groupId, long classNameId,
			long classTypeId, int type, int status,
			OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {

		LayoutPageTemplateEntry layoutPageTemplateEntry = findByPrimaryKey(
			layoutPageTemplateEntryId);

		Session session = null;

		try {
			session = openSession();

			LayoutPageTemplateEntry[] array =
				new LayoutPageTemplateEntryImpl[3];

			array[0] = getByG_C_C_T_S_PrevAndNext(
				session, layoutPageTemplateEntry, groupId, classNameId,
				classTypeId, type, status, orderByComparator, true);

			array[1] = layoutPageTemplateEntry;

			array[2] = getByG_C_C_T_S_PrevAndNext(
				session, layoutPageTemplateEntry, groupId, classNameId,
				classTypeId, type, status, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutPageTemplateEntry getByG_C_C_T_S_PrevAndNext(
		Session session, LayoutPageTemplateEntry layoutPageTemplateEntry,
		long groupId, long classNameId, long classTypeId, int type, int status,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				8 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(7);
		}

		sb.append(_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_C_C_T_S_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_C_T_S_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_G_C_C_T_S_CLASSTYPEID_2);

		sb.append(_FINDER_COLUMN_G_C_C_T_S_TYPE_2);

		sb.append(_FINDER_COLUMN_G_C_C_T_S_STATUS_2);

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
			sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(classNameId);

		queryPos.add(classTypeId);

		queryPos.add(type);

		queryPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutPageTemplateEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<LayoutPageTemplateEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the layout page template entries that the user has permission to view where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param type the type
	 * @param status the status
	 * @return the matching layout page template entries that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateEntry> filterFindByG_C_C_T_S(
		long groupId, long classNameId, long classTypeId, int type,
		int status) {

		return filterFindByG_C_C_T_S(
			groupId, classNameId, classTypeId, type, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout page template entries that the user has permission to view where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and type = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param type the type
	 * @param status the status
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @return the range of matching layout page template entries that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateEntry> filterFindByG_C_C_T_S(
		long groupId, long classNameId, long classTypeId, int type, int status,
		int start, int end) {

		return filterFindByG_C_C_T_S(
			groupId, classNameId, classTypeId, type, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout page template entries that the user has permissions to view where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and type = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param type the type
	 * @param status the status
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template entries that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateEntry> filterFindByG_C_C_T_S(
		long groupId, long classNameId, long classTypeId, int type, int status,
		int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C_C_T_S(
				groupId, classNameId, classTypeId, type, status, start, end,
				orderByComparator);
		}

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				7 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(8);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_C_C_T_S_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_C_T_S_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_G_C_C_T_S_CLASSTYPEID_2);

		sb.append(_FINDER_COLUMN_G_C_C_T_S_TYPE_2_SQL);

		sb.append(_FINDER_COLUMN_G_C_C_T_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), LayoutPageTemplateEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, LayoutPageTemplateEntryImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, LayoutPageTemplateEntryImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(classNameId);

			queryPos.add(classTypeId);

			queryPos.add(type);

			queryPos.add(status);

			return (List<LayoutPageTemplateEntry>)QueryUtil.list(
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
	 * Returns the layout page template entries before and after the current layout page template entry in the ordered set of layout page template entries that the user has permission to view where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the primary key of the current layout page template entry
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a layout page template entry with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateEntry[] filterFindByG_C_C_T_S_PrevAndNext(
			long layoutPageTemplateEntryId, long groupId, long classNameId,
			long classTypeId, int type, int status,
			OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C_C_T_S_PrevAndNext(
				layoutPageTemplateEntryId, groupId, classNameId, classTypeId,
				type, status, orderByComparator);
		}

		LayoutPageTemplateEntry layoutPageTemplateEntry = findByPrimaryKey(
			layoutPageTemplateEntryId);

		Session session = null;

		try {
			session = openSession();

			LayoutPageTemplateEntry[] array =
				new LayoutPageTemplateEntryImpl[3];

			array[0] = filterGetByG_C_C_T_S_PrevAndNext(
				session, layoutPageTemplateEntry, groupId, classNameId,
				classTypeId, type, status, orderByComparator, true);

			array[1] = layoutPageTemplateEntry;

			array[2] = filterGetByG_C_C_T_S_PrevAndNext(
				session, layoutPageTemplateEntry, groupId, classNameId,
				classTypeId, type, status, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutPageTemplateEntry filterGetByG_C_C_T_S_PrevAndNext(
		Session session, LayoutPageTemplateEntry layoutPageTemplateEntry,
		long groupId, long classNameId, long classTypeId, int type, int status,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				9 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(8);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_C_C_T_S_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_C_T_S_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_G_C_C_T_S_CLASSTYPEID_2);

		sb.append(_FINDER_COLUMN_G_C_C_T_S_TYPE_2_SQL);

		sb.append(_FINDER_COLUMN_G_C_C_T_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), LayoutPageTemplateEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(
				_FILTER_ENTITY_ALIAS, LayoutPageTemplateEntryImpl.class);
		}
		else {
			sqlQuery.addEntity(
				_FILTER_ENTITY_TABLE, LayoutPageTemplateEntryImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(groupId);

		queryPos.add(classNameId);

		queryPos.add(classTypeId);

		queryPos.add(type);

		queryPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutPageTemplateEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<LayoutPageTemplateEntry> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout page template entries where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and type = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param type the type
	 * @param status the status
	 */
	@Override
	public void removeByG_C_C_T_S(
		long groupId, long classNameId, long classTypeId, int type,
		int status) {

		for (LayoutPageTemplateEntry layoutPageTemplateEntry :
				findByG_C_C_T_S(
					groupId, classNameId, classTypeId, type, status,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(layoutPageTemplateEntry);
		}
	}

	/**
	 * Returns the number of layout page template entries where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param type the type
	 * @param status the status
	 * @return the number of matching layout page template entries
	 */
	@Override
	public int countByG_C_C_T_S(
		long groupId, long classNameId, long classTypeId, int type,
		int status) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			LayoutPageTemplateEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_C_C_T_S;

			finderArgs = new Object[] {
				groupId, classNameId, classTypeId, type, status
			};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_SQL_COUNT_LAYOUTPAGETEMPLATEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_C_C_T_S_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_C_T_S_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_G_C_C_T_S_CLASSTYPEID_2);

			sb.append(_FINDER_COLUMN_G_C_C_T_S_TYPE_2);

			sb.append(_FINDER_COLUMN_G_C_C_T_S_STATUS_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(classNameId);

				queryPos.add(classTypeId);

				queryPos.add(type);

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
	 * Returns the number of layout page template entries that the user has permission to view where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param type the type
	 * @param status the status
	 * @return the number of matching layout page template entries that the user has permission to view
	 */
	@Override
	public int filterCountByG_C_C_T_S(
		long groupId, long classNameId, long classTypeId, int type,
		int status) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_C_C_T_S(
				groupId, classNameId, classTypeId, type, status);
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_FILTER_SQL_COUNT_LAYOUTPAGETEMPLATEENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_C_C_T_S_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_C_T_S_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_G_C_C_T_S_CLASSTYPEID_2);

		sb.append(_FINDER_COLUMN_G_C_C_T_S_TYPE_2_SQL);

		sb.append(_FINDER_COLUMN_G_C_C_T_S_STATUS_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), LayoutPageTemplateEntry.class.getName(),
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

			queryPos.add(classTypeId);

			queryPos.add(type);

			queryPos.add(status);

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

	private static final String _FINDER_COLUMN_G_C_C_T_S_GROUPID_2 =
		"layoutPageTemplateEntry.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_T_S_CLASSNAMEID_2 =
		"layoutPageTemplateEntry.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_T_S_CLASSTYPEID_2 =
		"layoutPageTemplateEntry.classTypeId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_T_S_TYPE_2 =
		"layoutPageTemplateEntry.type = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_T_S_TYPE_2_SQL =
		"layoutPageTemplateEntry.type_ = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_T_S_STATUS_2 =
		"layoutPageTemplateEntry.status = ?";

	private FinderPath _finderPathWithPaginationFindByG_C_C_D_S;
	private FinderPath _finderPathWithoutPaginationFindByG_C_C_D_S;
	private FinderPath _finderPathCountByG_C_C_D_S;

	/**
	 * Returns all the layout page template entries where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and defaultTemplate = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param defaultTemplate the default template
	 * @param status the status
	 * @return the matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_C_C_D_S(
		long groupId, long classNameId, long classTypeId,
		boolean defaultTemplate, int status) {

		return findByG_C_C_D_S(
			groupId, classNameId, classTypeId, defaultTemplate, status,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout page template entries where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and defaultTemplate = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param defaultTemplate the default template
	 * @param status the status
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @return the range of matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_C_C_D_S(
		long groupId, long classNameId, long classTypeId,
		boolean defaultTemplate, int status, int start, int end) {

		return findByG_C_C_D_S(
			groupId, classNameId, classTypeId, defaultTemplate, status, start,
			end, null);
	}

	/**
	 * Returns an ordered range of all the layout page template entries where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and defaultTemplate = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param defaultTemplate the default template
	 * @param status the status
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_C_C_D_S(
		long groupId, long classNameId, long classTypeId,
		boolean defaultTemplate, int status, int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		return findByG_C_C_D_S(
			groupId, classNameId, classTypeId, defaultTemplate, status, start,
			end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout page template entries where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and defaultTemplate = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param defaultTemplate the default template
	 * @param status the status
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_C_C_D_S(
		long groupId, long classNameId, long classTypeId,
		boolean defaultTemplate, int status, int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			LayoutPageTemplateEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByG_C_C_D_S;
				finderArgs = new Object[] {
					groupId, classNameId, classTypeId, defaultTemplate, status
				};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByG_C_C_D_S;
			finderArgs = new Object[] {
				groupId, classNameId, classTypeId, defaultTemplate, status,
				start, end, orderByComparator
			};
		}

		List<LayoutPageTemplateEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<LayoutPageTemplateEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutPageTemplateEntry layoutPageTemplateEntry : list) {
					if ((groupId != layoutPageTemplateEntry.getGroupId()) ||
						(classNameId !=
							layoutPageTemplateEntry.getClassNameId()) ||
						(classTypeId !=
							layoutPageTemplateEntry.getClassTypeId()) ||
						(defaultTemplate !=
							layoutPageTemplateEntry.isDefaultTemplate()) ||
						(status != layoutPageTemplateEntry.getStatus())) {

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
					7 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(7);
			}

			sb.append(_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_C_C_D_S_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_C_D_S_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_G_C_C_D_S_CLASSTYPEID_2);

			sb.append(_FINDER_COLUMN_G_C_C_D_S_DEFAULTTEMPLATE_2);

			sb.append(_FINDER_COLUMN_G_C_C_D_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(classNameId);

				queryPos.add(classTypeId);

				queryPos.add(defaultTemplate);

				queryPos.add(status);

				list = (List<LayoutPageTemplateEntry>)QueryUtil.list(
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
	 * Returns the first layout page template entry in the ordered set where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and defaultTemplate = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param defaultTemplate the default template
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry findByG_C_C_D_S_First(
			long groupId, long classNameId, long classTypeId,
			boolean defaultTemplate, int status,
			OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			fetchByG_C_C_D_S_First(
				groupId, classNameId, classTypeId, defaultTemplate, status,
				orderByComparator);

		if (layoutPageTemplateEntry != null) {
			return layoutPageTemplateEntry;
		}

		StringBundler sb = new StringBundler(12);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append(", classTypeId=");
		sb.append(classTypeId);

		sb.append(", defaultTemplate=");
		sb.append(defaultTemplate);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchPageTemplateEntryException(sb.toString());
	}

	/**
	 * Returns the first layout page template entry in the ordered set where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and defaultTemplate = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param defaultTemplate the default template
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template entry, or <code>null</code> if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry fetchByG_C_C_D_S_First(
		long groupId, long classNameId, long classTypeId,
		boolean defaultTemplate, int status,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		List<LayoutPageTemplateEntry> list = findByG_C_C_D_S(
			groupId, classNameId, classTypeId, defaultTemplate, status, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout page template entry in the ordered set where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and defaultTemplate = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param defaultTemplate the default template
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry findByG_C_C_D_S_Last(
			long groupId, long classNameId, long classTypeId,
			boolean defaultTemplate, int status,
			OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {

		LayoutPageTemplateEntry layoutPageTemplateEntry = fetchByG_C_C_D_S_Last(
			groupId, classNameId, classTypeId, defaultTemplate, status,
			orderByComparator);

		if (layoutPageTemplateEntry != null) {
			return layoutPageTemplateEntry;
		}

		StringBundler sb = new StringBundler(12);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append(", classTypeId=");
		sb.append(classTypeId);

		sb.append(", defaultTemplate=");
		sb.append(defaultTemplate);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchPageTemplateEntryException(sb.toString());
	}

	/**
	 * Returns the last layout page template entry in the ordered set where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and defaultTemplate = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param defaultTemplate the default template
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template entry, or <code>null</code> if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry fetchByG_C_C_D_S_Last(
		long groupId, long classNameId, long classTypeId,
		boolean defaultTemplate, int status,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		int count = countByG_C_C_D_S(
			groupId, classNameId, classTypeId, defaultTemplate, status);

		if (count == 0) {
			return null;
		}

		List<LayoutPageTemplateEntry> list = findByG_C_C_D_S(
			groupId, classNameId, classTypeId, defaultTemplate, status,
			count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout page template entries before and after the current layout page template entry in the ordered set where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and defaultTemplate = &#63; and status = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the primary key of the current layout page template entry
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param defaultTemplate the default template
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a layout page template entry with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateEntry[] findByG_C_C_D_S_PrevAndNext(
			long layoutPageTemplateEntryId, long groupId, long classNameId,
			long classTypeId, boolean defaultTemplate, int status,
			OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {

		LayoutPageTemplateEntry layoutPageTemplateEntry = findByPrimaryKey(
			layoutPageTemplateEntryId);

		Session session = null;

		try {
			session = openSession();

			LayoutPageTemplateEntry[] array =
				new LayoutPageTemplateEntryImpl[3];

			array[0] = getByG_C_C_D_S_PrevAndNext(
				session, layoutPageTemplateEntry, groupId, classNameId,
				classTypeId, defaultTemplate, status, orderByComparator, true);

			array[1] = layoutPageTemplateEntry;

			array[2] = getByG_C_C_D_S_PrevAndNext(
				session, layoutPageTemplateEntry, groupId, classNameId,
				classTypeId, defaultTemplate, status, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutPageTemplateEntry getByG_C_C_D_S_PrevAndNext(
		Session session, LayoutPageTemplateEntry layoutPageTemplateEntry,
		long groupId, long classNameId, long classTypeId,
		boolean defaultTemplate, int status,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				8 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(7);
		}

		sb.append(_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_C_C_D_S_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_C_D_S_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_G_C_C_D_S_CLASSTYPEID_2);

		sb.append(_FINDER_COLUMN_G_C_C_D_S_DEFAULTTEMPLATE_2);

		sb.append(_FINDER_COLUMN_G_C_C_D_S_STATUS_2);

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
			sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(classNameId);

		queryPos.add(classTypeId);

		queryPos.add(defaultTemplate);

		queryPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutPageTemplateEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<LayoutPageTemplateEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the layout page template entries that the user has permission to view where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and defaultTemplate = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param defaultTemplate the default template
	 * @param status the status
	 * @return the matching layout page template entries that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateEntry> filterFindByG_C_C_D_S(
		long groupId, long classNameId, long classTypeId,
		boolean defaultTemplate, int status) {

		return filterFindByG_C_C_D_S(
			groupId, classNameId, classTypeId, defaultTemplate, status,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout page template entries that the user has permission to view where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and defaultTemplate = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param defaultTemplate the default template
	 * @param status the status
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @return the range of matching layout page template entries that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateEntry> filterFindByG_C_C_D_S(
		long groupId, long classNameId, long classTypeId,
		boolean defaultTemplate, int status, int start, int end) {

		return filterFindByG_C_C_D_S(
			groupId, classNameId, classTypeId, defaultTemplate, status, start,
			end, null);
	}

	/**
	 * Returns an ordered range of all the layout page template entries that the user has permissions to view where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and defaultTemplate = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param defaultTemplate the default template
	 * @param status the status
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template entries that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateEntry> filterFindByG_C_C_D_S(
		long groupId, long classNameId, long classTypeId,
		boolean defaultTemplate, int status, int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C_C_D_S(
				groupId, classNameId, classTypeId, defaultTemplate, status,
				start, end, orderByComparator);
		}

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				7 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(8);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_C_C_D_S_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_C_D_S_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_G_C_C_D_S_CLASSTYPEID_2);

		sb.append(_FINDER_COLUMN_G_C_C_D_S_DEFAULTTEMPLATE_2);

		sb.append(_FINDER_COLUMN_G_C_C_D_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), LayoutPageTemplateEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, LayoutPageTemplateEntryImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, LayoutPageTemplateEntryImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(classNameId);

			queryPos.add(classTypeId);

			queryPos.add(defaultTemplate);

			queryPos.add(status);

			return (List<LayoutPageTemplateEntry>)QueryUtil.list(
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
	 * Returns the layout page template entries before and after the current layout page template entry in the ordered set of layout page template entries that the user has permission to view where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and defaultTemplate = &#63; and status = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the primary key of the current layout page template entry
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param defaultTemplate the default template
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a layout page template entry with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateEntry[] filterFindByG_C_C_D_S_PrevAndNext(
			long layoutPageTemplateEntryId, long groupId, long classNameId,
			long classTypeId, boolean defaultTemplate, int status,
			OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C_C_D_S_PrevAndNext(
				layoutPageTemplateEntryId, groupId, classNameId, classTypeId,
				defaultTemplate, status, orderByComparator);
		}

		LayoutPageTemplateEntry layoutPageTemplateEntry = findByPrimaryKey(
			layoutPageTemplateEntryId);

		Session session = null;

		try {
			session = openSession();

			LayoutPageTemplateEntry[] array =
				new LayoutPageTemplateEntryImpl[3];

			array[0] = filterGetByG_C_C_D_S_PrevAndNext(
				session, layoutPageTemplateEntry, groupId, classNameId,
				classTypeId, defaultTemplate, status, orderByComparator, true);

			array[1] = layoutPageTemplateEntry;

			array[2] = filterGetByG_C_C_D_S_PrevAndNext(
				session, layoutPageTemplateEntry, groupId, classNameId,
				classTypeId, defaultTemplate, status, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutPageTemplateEntry filterGetByG_C_C_D_S_PrevAndNext(
		Session session, LayoutPageTemplateEntry layoutPageTemplateEntry,
		long groupId, long classNameId, long classTypeId,
		boolean defaultTemplate, int status,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				9 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(8);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_C_C_D_S_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_C_D_S_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_G_C_C_D_S_CLASSTYPEID_2);

		sb.append(_FINDER_COLUMN_G_C_C_D_S_DEFAULTTEMPLATE_2);

		sb.append(_FINDER_COLUMN_G_C_C_D_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), LayoutPageTemplateEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(
				_FILTER_ENTITY_ALIAS, LayoutPageTemplateEntryImpl.class);
		}
		else {
			sqlQuery.addEntity(
				_FILTER_ENTITY_TABLE, LayoutPageTemplateEntryImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(groupId);

		queryPos.add(classNameId);

		queryPos.add(classTypeId);

		queryPos.add(defaultTemplate);

		queryPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutPageTemplateEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<LayoutPageTemplateEntry> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout page template entries where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and defaultTemplate = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param defaultTemplate the default template
	 * @param status the status
	 */
	@Override
	public void removeByG_C_C_D_S(
		long groupId, long classNameId, long classTypeId,
		boolean defaultTemplate, int status) {

		for (LayoutPageTemplateEntry layoutPageTemplateEntry :
				findByG_C_C_D_S(
					groupId, classNameId, classTypeId, defaultTemplate, status,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(layoutPageTemplateEntry);
		}
	}

	/**
	 * Returns the number of layout page template entries where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and defaultTemplate = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param defaultTemplate the default template
	 * @param status the status
	 * @return the number of matching layout page template entries
	 */
	@Override
	public int countByG_C_C_D_S(
		long groupId, long classNameId, long classTypeId,
		boolean defaultTemplate, int status) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			LayoutPageTemplateEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_C_C_D_S;

			finderArgs = new Object[] {
				groupId, classNameId, classTypeId, defaultTemplate, status
			};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_SQL_COUNT_LAYOUTPAGETEMPLATEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_C_C_D_S_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_C_D_S_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_G_C_C_D_S_CLASSTYPEID_2);

			sb.append(_FINDER_COLUMN_G_C_C_D_S_DEFAULTTEMPLATE_2);

			sb.append(_FINDER_COLUMN_G_C_C_D_S_STATUS_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(classNameId);

				queryPos.add(classTypeId);

				queryPos.add(defaultTemplate);

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
	 * Returns the number of layout page template entries that the user has permission to view where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and defaultTemplate = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param defaultTemplate the default template
	 * @param status the status
	 * @return the number of matching layout page template entries that the user has permission to view
	 */
	@Override
	public int filterCountByG_C_C_D_S(
		long groupId, long classNameId, long classTypeId,
		boolean defaultTemplate, int status) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_C_C_D_S(
				groupId, classNameId, classTypeId, defaultTemplate, status);
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_FILTER_SQL_COUNT_LAYOUTPAGETEMPLATEENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_C_C_D_S_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_C_D_S_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_G_C_C_D_S_CLASSTYPEID_2);

		sb.append(_FINDER_COLUMN_G_C_C_D_S_DEFAULTTEMPLATE_2);

		sb.append(_FINDER_COLUMN_G_C_C_D_S_STATUS_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), LayoutPageTemplateEntry.class.getName(),
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

			queryPos.add(classTypeId);

			queryPos.add(defaultTemplate);

			queryPos.add(status);

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

	private static final String _FINDER_COLUMN_G_C_C_D_S_GROUPID_2 =
		"layoutPageTemplateEntry.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_D_S_CLASSNAMEID_2 =
		"layoutPageTemplateEntry.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_D_S_CLASSTYPEID_2 =
		"layoutPageTemplateEntry.classTypeId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_D_S_DEFAULTTEMPLATE_2 =
		"layoutPageTemplateEntry.defaultTemplate = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_D_S_STATUS_2 =
		"layoutPageTemplateEntry.status = ?";

	private FinderPath _finderPathWithPaginationFindByG_C_C_LikeN_T_S;
	private FinderPath _finderPathWithPaginationCountByG_C_C_LikeN_T_S;

	/**
	 * Returns all the layout page template entries where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and name LIKE &#63; and type = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param name the name
	 * @param type the type
	 * @param status the status
	 * @return the matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_C_C_LikeN_T_S(
		long groupId, long classNameId, long classTypeId, String name, int type,
		int status) {

		return findByG_C_C_LikeN_T_S(
			groupId, classNameId, classTypeId, name, type, status,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout page template entries where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and name LIKE &#63; and type = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param name the name
	 * @param type the type
	 * @param status the status
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @return the range of matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_C_C_LikeN_T_S(
		long groupId, long classNameId, long classTypeId, String name, int type,
		int status, int start, int end) {

		return findByG_C_C_LikeN_T_S(
			groupId, classNameId, classTypeId, name, type, status, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the layout page template entries where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and name LIKE &#63; and type = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param name the name
	 * @param type the type
	 * @param status the status
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_C_C_LikeN_T_S(
		long groupId, long classNameId, long classTypeId, String name, int type,
		int status, int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		return findByG_C_C_LikeN_T_S(
			groupId, classNameId, classTypeId, name, type, status, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout page template entries where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and name LIKE &#63; and type = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param name the name
	 * @param type the type
	 * @param status the status
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findByG_C_C_LikeN_T_S(
		long groupId, long classNameId, long classTypeId, String name, int type,
		int status, int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator,
		boolean useFinderCache) {

		name = Objects.toString(name, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			LayoutPageTemplateEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByG_C_C_LikeN_T_S;
		finderArgs = new Object[] {
			groupId, classNameId, classTypeId, name, type, status, start, end,
			orderByComparator
		};

		List<LayoutPageTemplateEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<LayoutPageTemplateEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutPageTemplateEntry layoutPageTemplateEntry : list) {
					if ((groupId != layoutPageTemplateEntry.getGroupId()) ||
						(classNameId !=
							layoutPageTemplateEntry.getClassNameId()) ||
						(classTypeId !=
							layoutPageTemplateEntry.getClassTypeId()) ||
						!StringUtil.wildcardMatches(
							layoutPageTemplateEntry.getName(), name, '_', '%',
							'\\', true) ||
						(type != layoutPageTemplateEntry.getType()) ||
						(status != layoutPageTemplateEntry.getStatus())) {

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
					8 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(8);
			}

			sb.append(_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_C_C_LIKEN_T_S_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_C_LIKEN_T_S_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_G_C_C_LIKEN_T_S_CLASSTYPEID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_C_C_LIKEN_T_S_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_G_C_C_LIKEN_T_S_NAME_2);
			}

			sb.append(_FINDER_COLUMN_G_C_C_LIKEN_T_S_TYPE_2);

			sb.append(_FINDER_COLUMN_G_C_C_LIKEN_T_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(classNameId);

				queryPos.add(classTypeId);

				if (bindName) {
					queryPos.add(name);
				}

				queryPos.add(type);

				queryPos.add(status);

				list = (List<LayoutPageTemplateEntry>)QueryUtil.list(
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
	 * Returns the first layout page template entry in the ordered set where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and name LIKE &#63; and type = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param name the name
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry findByG_C_C_LikeN_T_S_First(
			long groupId, long classNameId, long classTypeId, String name,
			int type, int status,
			OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			fetchByG_C_C_LikeN_T_S_First(
				groupId, classNameId, classTypeId, name, type, status,
				orderByComparator);

		if (layoutPageTemplateEntry != null) {
			return layoutPageTemplateEntry;
		}

		StringBundler sb = new StringBundler(14);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append(", classTypeId=");
		sb.append(classTypeId);

		sb.append(", nameLIKE");
		sb.append(name);

		sb.append(", type=");
		sb.append(type);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchPageTemplateEntryException(sb.toString());
	}

	/**
	 * Returns the first layout page template entry in the ordered set where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and name LIKE &#63; and type = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param name the name
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout page template entry, or <code>null</code> if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry fetchByG_C_C_LikeN_T_S_First(
		long groupId, long classNameId, long classTypeId, String name, int type,
		int status,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		List<LayoutPageTemplateEntry> list = findByG_C_C_LikeN_T_S(
			groupId, classNameId, classTypeId, name, type, status, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout page template entry in the ordered set where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and name LIKE &#63; and type = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param name the name
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry findByG_C_C_LikeN_T_S_Last(
			long groupId, long classNameId, long classTypeId, String name,
			int type, int status,
			OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			fetchByG_C_C_LikeN_T_S_Last(
				groupId, classNameId, classTypeId, name, type, status,
				orderByComparator);

		if (layoutPageTemplateEntry != null) {
			return layoutPageTemplateEntry;
		}

		StringBundler sb = new StringBundler(14);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append(", classTypeId=");
		sb.append(classTypeId);

		sb.append(", nameLIKE");
		sb.append(name);

		sb.append(", type=");
		sb.append(type);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchPageTemplateEntryException(sb.toString());
	}

	/**
	 * Returns the last layout page template entry in the ordered set where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and name LIKE &#63; and type = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param name the name
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout page template entry, or <code>null</code> if a matching layout page template entry could not be found
	 */
	@Override
	public LayoutPageTemplateEntry fetchByG_C_C_LikeN_T_S_Last(
		long groupId, long classNameId, long classTypeId, String name, int type,
		int status,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		int count = countByG_C_C_LikeN_T_S(
			groupId, classNameId, classTypeId, name, type, status);

		if (count == 0) {
			return null;
		}

		List<LayoutPageTemplateEntry> list = findByG_C_C_LikeN_T_S(
			groupId, classNameId, classTypeId, name, type, status, count - 1,
			count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout page template entries before and after the current layout page template entry in the ordered set where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and name LIKE &#63; and type = &#63; and status = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the primary key of the current layout page template entry
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param name the name
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a layout page template entry with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateEntry[] findByG_C_C_LikeN_T_S_PrevAndNext(
			long layoutPageTemplateEntryId, long groupId, long classNameId,
			long classTypeId, String name, int type, int status,
			OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {

		name = Objects.toString(name, "");

		LayoutPageTemplateEntry layoutPageTemplateEntry = findByPrimaryKey(
			layoutPageTemplateEntryId);

		Session session = null;

		try {
			session = openSession();

			LayoutPageTemplateEntry[] array =
				new LayoutPageTemplateEntryImpl[3];

			array[0] = getByG_C_C_LikeN_T_S_PrevAndNext(
				session, layoutPageTemplateEntry, groupId, classNameId,
				classTypeId, name, type, status, orderByComparator, true);

			array[1] = layoutPageTemplateEntry;

			array[2] = getByG_C_C_LikeN_T_S_PrevAndNext(
				session, layoutPageTemplateEntry, groupId, classNameId,
				classTypeId, name, type, status, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutPageTemplateEntry getByG_C_C_LikeN_T_S_PrevAndNext(
		Session session, LayoutPageTemplateEntry layoutPageTemplateEntry,
		long groupId, long classNameId, long classTypeId, String name, int type,
		int status,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				9 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(8);
		}

		sb.append(_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_C_C_LIKEN_T_S_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_C_LIKEN_T_S_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_G_C_C_LIKEN_T_S_CLASSTYPEID_2);

		boolean bindName = false;

		if (name.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_C_C_LIKEN_T_S_NAME_3);
		}
		else {
			bindName = true;

			sb.append(_FINDER_COLUMN_G_C_C_LIKEN_T_S_NAME_2);
		}

		sb.append(_FINDER_COLUMN_G_C_C_LIKEN_T_S_TYPE_2);

		sb.append(_FINDER_COLUMN_G_C_C_LIKEN_T_S_STATUS_2);

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
			sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(classNameId);

		queryPos.add(classTypeId);

		if (bindName) {
			queryPos.add(name);
		}

		queryPos.add(type);

		queryPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutPageTemplateEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<LayoutPageTemplateEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the layout page template entries that the user has permission to view where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and name LIKE &#63; and type = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param name the name
	 * @param type the type
	 * @param status the status
	 * @return the matching layout page template entries that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateEntry> filterFindByG_C_C_LikeN_T_S(
		long groupId, long classNameId, long classTypeId, String name, int type,
		int status) {

		return filterFindByG_C_C_LikeN_T_S(
			groupId, classNameId, classTypeId, name, type, status,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout page template entries that the user has permission to view where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and name LIKE &#63; and type = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param name the name
	 * @param type the type
	 * @param status the status
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @return the range of matching layout page template entries that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateEntry> filterFindByG_C_C_LikeN_T_S(
		long groupId, long classNameId, long classTypeId, String name, int type,
		int status, int start, int end) {

		return filterFindByG_C_C_LikeN_T_S(
			groupId, classNameId, classTypeId, name, type, status, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the layout page template entries that the user has permissions to view where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and name LIKE &#63; and type = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param name the name
	 * @param type the type
	 * @param status the status
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout page template entries that the user has permission to view
	 */
	@Override
	public List<LayoutPageTemplateEntry> filterFindByG_C_C_LikeN_T_S(
		long groupId, long classNameId, long classTypeId, String name, int type,
		int status, int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C_C_LikeN_T_S(
				groupId, classNameId, classTypeId, name, type, status, start,
				end, orderByComparator);
		}

		name = Objects.toString(name, "");

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				8 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(9);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_C_C_LIKEN_T_S_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_C_LIKEN_T_S_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_G_C_C_LIKEN_T_S_CLASSTYPEID_2);

		boolean bindName = false;

		if (name.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_C_C_LIKEN_T_S_NAME_3);
		}
		else {
			bindName = true;

			sb.append(_FINDER_COLUMN_G_C_C_LIKEN_T_S_NAME_2);
		}

		sb.append(_FINDER_COLUMN_G_C_C_LIKEN_T_S_TYPE_2_SQL);

		sb.append(_FINDER_COLUMN_G_C_C_LIKEN_T_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), LayoutPageTemplateEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, LayoutPageTemplateEntryImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, LayoutPageTemplateEntryImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			queryPos.add(classNameId);

			queryPos.add(classTypeId);

			if (bindName) {
				queryPos.add(name);
			}

			queryPos.add(type);

			queryPos.add(status);

			return (List<LayoutPageTemplateEntry>)QueryUtil.list(
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
	 * Returns the layout page template entries before and after the current layout page template entry in the ordered set of layout page template entries that the user has permission to view where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and name LIKE &#63; and type = &#63; and status = &#63;.
	 *
	 * @param layoutPageTemplateEntryId the primary key of the current layout page template entry
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param name the name
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a layout page template entry with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateEntry[] filterFindByG_C_C_LikeN_T_S_PrevAndNext(
			long layoutPageTemplateEntryId, long groupId, long classNameId,
			long classTypeId, String name, int type, int status,
			OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws NoSuchPageTemplateEntryException {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C_C_LikeN_T_S_PrevAndNext(
				layoutPageTemplateEntryId, groupId, classNameId, classTypeId,
				name, type, status, orderByComparator);
		}

		name = Objects.toString(name, "");

		LayoutPageTemplateEntry layoutPageTemplateEntry = findByPrimaryKey(
			layoutPageTemplateEntryId);

		Session session = null;

		try {
			session = openSession();

			LayoutPageTemplateEntry[] array =
				new LayoutPageTemplateEntryImpl[3];

			array[0] = filterGetByG_C_C_LikeN_T_S_PrevAndNext(
				session, layoutPageTemplateEntry, groupId, classNameId,
				classTypeId, name, type, status, orderByComparator, true);

			array[1] = layoutPageTemplateEntry;

			array[2] = filterGetByG_C_C_LikeN_T_S_PrevAndNext(
				session, layoutPageTemplateEntry, groupId, classNameId,
				classTypeId, name, type, status, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutPageTemplateEntry filterGetByG_C_C_LikeN_T_S_PrevAndNext(
		Session session, LayoutPageTemplateEntry layoutPageTemplateEntry,
		long groupId, long classNameId, long classTypeId, String name, int type,
		int status,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				10 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
						(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(9);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_G_C_C_LIKEN_T_S_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_C_LIKEN_T_S_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_G_C_C_LIKEN_T_S_CLASSTYPEID_2);

		boolean bindName = false;

		if (name.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_C_C_LIKEN_T_S_NAME_3);
		}
		else {
			bindName = true;

			sb.append(_FINDER_COLUMN_G_C_C_LIKEN_T_S_NAME_2);
		}

		sb.append(_FINDER_COLUMN_G_C_C_LIKEN_T_S_TYPE_2_SQL);

		sb.append(_FINDER_COLUMN_G_C_C_LIKEN_T_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(LayoutPageTemplateEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), LayoutPageTemplateEntry.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(
				_FILTER_ENTITY_ALIAS, LayoutPageTemplateEntryImpl.class);
		}
		else {
			sqlQuery.addEntity(
				_FILTER_ENTITY_TABLE, LayoutPageTemplateEntryImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(groupId);

		queryPos.add(classNameId);

		queryPos.add(classTypeId);

		if (bindName) {
			queryPos.add(name);
		}

		queryPos.add(type);

		queryPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutPageTemplateEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<LayoutPageTemplateEntry> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout page template entries where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and name LIKE &#63; and type = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param name the name
	 * @param type the type
	 * @param status the status
	 */
	@Override
	public void removeByG_C_C_LikeN_T_S(
		long groupId, long classNameId, long classTypeId, String name, int type,
		int status) {

		for (LayoutPageTemplateEntry layoutPageTemplateEntry :
				findByG_C_C_LikeN_T_S(
					groupId, classNameId, classTypeId, name, type, status,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(layoutPageTemplateEntry);
		}
	}

	/**
	 * Returns the number of layout page template entries where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and name LIKE &#63; and type = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param name the name
	 * @param type the type
	 * @param status the status
	 * @return the number of matching layout page template entries
	 */
	@Override
	public int countByG_C_C_LikeN_T_S(
		long groupId, long classNameId, long classTypeId, String name, int type,
		int status) {

		name = Objects.toString(name, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			LayoutPageTemplateEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathWithPaginationCountByG_C_C_LikeN_T_S;

			finderArgs = new Object[] {
				groupId, classNameId, classTypeId, name, type, status
			};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(7);

			sb.append(_SQL_COUNT_LAYOUTPAGETEMPLATEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_C_C_LIKEN_T_S_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_C_LIKEN_T_S_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_G_C_C_LIKEN_T_S_CLASSTYPEID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_C_C_LIKEN_T_S_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_G_C_C_LIKEN_T_S_NAME_2);
			}

			sb.append(_FINDER_COLUMN_G_C_C_LIKEN_T_S_TYPE_2);

			sb.append(_FINDER_COLUMN_G_C_C_LIKEN_T_S_STATUS_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(classNameId);

				queryPos.add(classTypeId);

				if (bindName) {
					queryPos.add(name);
				}

				queryPos.add(type);

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
	 * Returns the number of layout page template entries that the user has permission to view where groupId = &#63; and classNameId = &#63; and classTypeId = &#63; and name LIKE &#63; and type = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classTypeId the class type ID
	 * @param name the name
	 * @param type the type
	 * @param status the status
	 * @return the number of matching layout page template entries that the user has permission to view
	 */
	@Override
	public int filterCountByG_C_C_LikeN_T_S(
		long groupId, long classNameId, long classTypeId, String name, int type,
		int status) {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_C_C_LikeN_T_S(
				groupId, classNameId, classTypeId, name, type, status);
		}

		name = Objects.toString(name, "");

		StringBundler sb = new StringBundler(7);

		sb.append(_FILTER_SQL_COUNT_LAYOUTPAGETEMPLATEENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_C_C_LIKEN_T_S_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_C_LIKEN_T_S_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_G_C_C_LIKEN_T_S_CLASSTYPEID_2);

		boolean bindName = false;

		if (name.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_C_C_LIKEN_T_S_NAME_3);
		}
		else {
			bindName = true;

			sb.append(_FINDER_COLUMN_G_C_C_LIKEN_T_S_NAME_2);
		}

		sb.append(_FINDER_COLUMN_G_C_C_LIKEN_T_S_TYPE_2_SQL);

		sb.append(_FINDER_COLUMN_G_C_C_LIKEN_T_S_STATUS_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), LayoutPageTemplateEntry.class.getName(),
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

			queryPos.add(classTypeId);

			if (bindName) {
				queryPos.add(name);
			}

			queryPos.add(type);

			queryPos.add(status);

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

	private static final String _FINDER_COLUMN_G_C_C_LIKEN_T_S_GROUPID_2 =
		"layoutPageTemplateEntry.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_LIKEN_T_S_CLASSNAMEID_2 =
		"layoutPageTemplateEntry.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_LIKEN_T_S_CLASSTYPEID_2 =
		"layoutPageTemplateEntry.classTypeId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_LIKEN_T_S_NAME_2 =
		"layoutPageTemplateEntry.name LIKE ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_LIKEN_T_S_NAME_3 =
		"(layoutPageTemplateEntry.name IS NULL OR layoutPageTemplateEntry.name LIKE '') AND ";

	private static final String _FINDER_COLUMN_G_C_C_LIKEN_T_S_TYPE_2 =
		"layoutPageTemplateEntry.type = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_LIKEN_T_S_TYPE_2_SQL =
		"layoutPageTemplateEntry.type_ = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_LIKEN_T_S_STATUS_2 =
		"layoutPageTemplateEntry.status = ?";

	public LayoutPageTemplateEntryPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put("type", "type_");

		setDBColumnNames(dbColumnNames);

		setModelClass(LayoutPageTemplateEntry.class);

		setModelImplClass(LayoutPageTemplateEntryImpl.class);
		setModelPKClass(long.class);

		setTable(LayoutPageTemplateEntryTable.INSTANCE);
	}

	/**
	 * Caches the layout page template entry in the entity cache if it is enabled.
	 *
	 * @param layoutPageTemplateEntry the layout page template entry
	 */
	@Override
	public void cacheResult(LayoutPageTemplateEntry layoutPageTemplateEntry) {
		if (layoutPageTemplateEntry.getCtCollectionId() != 0) {
			return;
		}

		entityCache.putResult(
			LayoutPageTemplateEntryImpl.class,
			layoutPageTemplateEntry.getPrimaryKey(), layoutPageTemplateEntry);

		finderCache.putResult(
			_finderPathFetchByUUID_G,
			new Object[] {
				layoutPageTemplateEntry.getUuid(),
				layoutPageTemplateEntry.getGroupId()
			},
			layoutPageTemplateEntry);

		finderCache.putResult(
			_finderPathFetchByPlid,
			new Object[] {layoutPageTemplateEntry.getPlid()},
			layoutPageTemplateEntry);

		finderCache.putResult(
			_finderPathFetchByG_LPTEK,
			new Object[] {
				layoutPageTemplateEntry.getGroupId(),
				layoutPageTemplateEntry.getLayoutPageTemplateEntryKey()
			},
			layoutPageTemplateEntry);

		finderCache.putResult(
			_finderPathFetchByG_N_T,
			new Object[] {
				layoutPageTemplateEntry.getGroupId(),
				layoutPageTemplateEntry.getName(),
				layoutPageTemplateEntry.getType()
			},
			layoutPageTemplateEntry);
	}

	/**
	 * Caches the layout page template entries in the entity cache if it is enabled.
	 *
	 * @param layoutPageTemplateEntries the layout page template entries
	 */
	@Override
	public void cacheResult(
		List<LayoutPageTemplateEntry> layoutPageTemplateEntries) {

		for (LayoutPageTemplateEntry layoutPageTemplateEntry :
				layoutPageTemplateEntries) {

			if (layoutPageTemplateEntry.getCtCollectionId() != 0) {
				continue;
			}

			if (entityCache.getResult(
					LayoutPageTemplateEntryImpl.class,
					layoutPageTemplateEntry.getPrimaryKey()) == null) {

				cacheResult(layoutPageTemplateEntry);
			}
		}
	}

	/**
	 * Clears the cache for all layout page template entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(LayoutPageTemplateEntryImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the layout page template entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(LayoutPageTemplateEntry layoutPageTemplateEntry) {
		entityCache.removeResult(
			LayoutPageTemplateEntryImpl.class,
			layoutPageTemplateEntry.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(LayoutPageTemplateEntryModelImpl)layoutPageTemplateEntry, true);
	}

	@Override
	public void clearCache(
		List<LayoutPageTemplateEntry> layoutPageTemplateEntries) {

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (LayoutPageTemplateEntry layoutPageTemplateEntry :
				layoutPageTemplateEntries) {

			entityCache.removeResult(
				LayoutPageTemplateEntryImpl.class,
				layoutPageTemplateEntry.getPrimaryKey());

			clearUniqueFindersCache(
				(LayoutPageTemplateEntryModelImpl)layoutPageTemplateEntry,
				true);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				LayoutPageTemplateEntryImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		LayoutPageTemplateEntryModelImpl layoutPageTemplateEntryModelImpl) {

		Object[] args = new Object[] {
			layoutPageTemplateEntryModelImpl.getUuid(),
			layoutPageTemplateEntryModelImpl.getGroupId()
		};

		finderCache.putResult(
			_finderPathCountByUUID_G, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByUUID_G, args, layoutPageTemplateEntryModelImpl,
			false);

		args = new Object[] {layoutPageTemplateEntryModelImpl.getPlid()};

		finderCache.putResult(
			_finderPathCountByPlid, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByPlid, args, layoutPageTemplateEntryModelImpl,
			false);

		args = new Object[] {
			layoutPageTemplateEntryModelImpl.getGroupId(),
			layoutPageTemplateEntryModelImpl.getLayoutPageTemplateEntryKey()
		};

		finderCache.putResult(
			_finderPathCountByG_LPTEK, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByG_LPTEK, args, layoutPageTemplateEntryModelImpl,
			false);

		args = new Object[] {
			layoutPageTemplateEntryModelImpl.getGroupId(),
			layoutPageTemplateEntryModelImpl.getName(),
			layoutPageTemplateEntryModelImpl.getType()
		};

		finderCache.putResult(
			_finderPathCountByG_N_T, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByG_N_T, args, layoutPageTemplateEntryModelImpl,
			false);
	}

	protected void clearUniqueFindersCache(
		LayoutPageTemplateEntryModelImpl layoutPageTemplateEntryModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				layoutPageTemplateEntryModelImpl.getUuid(),
				layoutPageTemplateEntryModelImpl.getGroupId()
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(_finderPathFetchByUUID_G, args);
		}

		if ((layoutPageTemplateEntryModelImpl.getColumnBitmask() &
			 _finderPathFetchByUUID_G.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				layoutPageTemplateEntryModelImpl.getColumnOriginalValue(
					"uuid_"),
				layoutPageTemplateEntryModelImpl.getColumnOriginalValue(
					"groupId")
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(_finderPathFetchByUUID_G, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
				layoutPageTemplateEntryModelImpl.getPlid()
			};

			finderCache.removeResult(_finderPathCountByPlid, args);
			finderCache.removeResult(_finderPathFetchByPlid, args);
		}

		if ((layoutPageTemplateEntryModelImpl.getColumnBitmask() &
			 _finderPathFetchByPlid.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				layoutPageTemplateEntryModelImpl.getColumnOriginalValue("plid")
			};

			finderCache.removeResult(_finderPathCountByPlid, args);
			finderCache.removeResult(_finderPathFetchByPlid, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
				layoutPageTemplateEntryModelImpl.getGroupId(),
				layoutPageTemplateEntryModelImpl.getLayoutPageTemplateEntryKey()
			};

			finderCache.removeResult(_finderPathCountByG_LPTEK, args);
			finderCache.removeResult(_finderPathFetchByG_LPTEK, args);
		}

		if ((layoutPageTemplateEntryModelImpl.getColumnBitmask() &
			 _finderPathFetchByG_LPTEK.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				layoutPageTemplateEntryModelImpl.getColumnOriginalValue(
					"groupId"),
				layoutPageTemplateEntryModelImpl.getColumnOriginalValue(
					"layoutPageTemplateEntryKey")
			};

			finderCache.removeResult(_finderPathCountByG_LPTEK, args);
			finderCache.removeResult(_finderPathFetchByG_LPTEK, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
				layoutPageTemplateEntryModelImpl.getGroupId(),
				layoutPageTemplateEntryModelImpl.getName(),
				layoutPageTemplateEntryModelImpl.getType()
			};

			finderCache.removeResult(_finderPathCountByG_N_T, args);
			finderCache.removeResult(_finderPathFetchByG_N_T, args);
		}

		if ((layoutPageTemplateEntryModelImpl.getColumnBitmask() &
			 _finderPathFetchByG_N_T.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				layoutPageTemplateEntryModelImpl.getColumnOriginalValue(
					"groupId"),
				layoutPageTemplateEntryModelImpl.getColumnOriginalValue("name"),
				layoutPageTemplateEntryModelImpl.getColumnOriginalValue("type_")
			};

			finderCache.removeResult(_finderPathCountByG_N_T, args);
			finderCache.removeResult(_finderPathFetchByG_N_T, args);
		}
	}

	/**
	 * Creates a new layout page template entry with the primary key. Does not add the layout page template entry to the database.
	 *
	 * @param layoutPageTemplateEntryId the primary key for the new layout page template entry
	 * @return the new layout page template entry
	 */
	@Override
	public LayoutPageTemplateEntry create(long layoutPageTemplateEntryId) {
		LayoutPageTemplateEntry layoutPageTemplateEntry =
			new LayoutPageTemplateEntryImpl();

		layoutPageTemplateEntry.setNew(true);
		layoutPageTemplateEntry.setPrimaryKey(layoutPageTemplateEntryId);

		String uuid = PortalUUIDUtil.generate();

		layoutPageTemplateEntry.setUuid(uuid);

		layoutPageTemplateEntry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return layoutPageTemplateEntry;
	}

	/**
	 * Removes the layout page template entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutPageTemplateEntryId the primary key of the layout page template entry
	 * @return the layout page template entry that was removed
	 * @throws NoSuchPageTemplateEntryException if a layout page template entry with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateEntry remove(long layoutPageTemplateEntryId)
		throws NoSuchPageTemplateEntryException {

		return remove((Serializable)layoutPageTemplateEntryId);
	}

	/**
	 * Removes the layout page template entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the layout page template entry
	 * @return the layout page template entry that was removed
	 * @throws NoSuchPageTemplateEntryException if a layout page template entry with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateEntry remove(Serializable primaryKey)
		throws NoSuchPageTemplateEntryException {

		Session session = null;

		try {
			session = openSession();

			LayoutPageTemplateEntry layoutPageTemplateEntry =
				(LayoutPageTemplateEntry)session.get(
					LayoutPageTemplateEntryImpl.class, primaryKey);

			if (layoutPageTemplateEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchPageTemplateEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(layoutPageTemplateEntry);
		}
		catch (NoSuchPageTemplateEntryException noSuchEntityException) {
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
	protected LayoutPageTemplateEntry removeImpl(
		LayoutPageTemplateEntry layoutPageTemplateEntry) {

		if (!ctPersistenceHelper.isRemove(layoutPageTemplateEntry)) {
			return layoutPageTemplateEntry;
		}

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(layoutPageTemplateEntry)) {
				layoutPageTemplateEntry = (LayoutPageTemplateEntry)session.get(
					LayoutPageTemplateEntryImpl.class,
					layoutPageTemplateEntry.getPrimaryKeyObj());
			}

			if (layoutPageTemplateEntry != null) {
				session.delete(layoutPageTemplateEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (layoutPageTemplateEntry != null) {
			clearCache(layoutPageTemplateEntry);
		}

		return layoutPageTemplateEntry;
	}

	@Override
	public LayoutPageTemplateEntry updateImpl(
		LayoutPageTemplateEntry layoutPageTemplateEntry) {

		boolean isNew = layoutPageTemplateEntry.isNew();

		if (!(layoutPageTemplateEntry instanceof
				LayoutPageTemplateEntryModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(layoutPageTemplateEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					layoutPageTemplateEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in layoutPageTemplateEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom LayoutPageTemplateEntry implementation " +
					layoutPageTemplateEntry.getClass());
		}

		LayoutPageTemplateEntryModelImpl layoutPageTemplateEntryModelImpl =
			(LayoutPageTemplateEntryModelImpl)layoutPageTemplateEntry;

		if (Validator.isNull(layoutPageTemplateEntry.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			layoutPageTemplateEntry.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (layoutPageTemplateEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				layoutPageTemplateEntry.setCreateDate(now);
			}
			else {
				layoutPageTemplateEntry.setCreateDate(
					serviceContext.getCreateDate(now));
			}
		}

		if (!layoutPageTemplateEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				layoutPageTemplateEntry.setModifiedDate(now);
			}
			else {
				layoutPageTemplateEntry.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(layoutPageTemplateEntry)) {
				if (!isNew) {
					session.evict(
						LayoutPageTemplateEntryImpl.class,
						layoutPageTemplateEntry.getPrimaryKeyObj());
				}

				session.save(layoutPageTemplateEntry);

				layoutPageTemplateEntry.setNew(false);
			}
			else {
				layoutPageTemplateEntry =
					(LayoutPageTemplateEntry)session.merge(
						layoutPageTemplateEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (layoutPageTemplateEntry.getCtCollectionId() != 0) {
			layoutPageTemplateEntry.resetOriginalValues();

			return layoutPageTemplateEntry;
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew) {
			Object[] args = new Object[] {
				layoutPageTemplateEntryModelImpl.getUuid()
			};

			finderCache.removeResult(_finderPathCountByUuid, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid, args);

			args = new Object[] {
				layoutPageTemplateEntryModelImpl.getUuid(),
				layoutPageTemplateEntryModelImpl.getCompanyId()
			};

			finderCache.removeResult(_finderPathCountByUuid_C, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid_C, args);

			args = new Object[] {layoutPageTemplateEntryModelImpl.getGroupId()};

			finderCache.removeResult(_finderPathCountByGroupId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByGroupId, args);

			args = new Object[] {
				layoutPageTemplateEntryModelImpl.getLayoutPrototypeId()
			};

			finderCache.removeResult(_finderPathCountByLayoutPrototypeId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByLayoutPrototypeId, args);

			args = new Object[] {
				layoutPageTemplateEntryModelImpl.getGroupId(),
				layoutPageTemplateEntryModelImpl.
					getLayoutPageTemplateCollectionId()
			};

			finderCache.removeResult(_finderPathCountByG_L, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByG_L, args);

			args = new Object[] {
				layoutPageTemplateEntryModelImpl.getGroupId(),
				layoutPageTemplateEntryModelImpl.getName()
			};

			finderCache.removeResult(_finderPathCountByG_N, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByG_N, args);

			args = new Object[] {
				layoutPageTemplateEntryModelImpl.getGroupId(),
				layoutPageTemplateEntryModelImpl.getType()
			};

			finderCache.removeResult(_finderPathCountByG_T, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByG_T, args);

			args = new Object[] {
				layoutPageTemplateEntryModelImpl.getGroupId(),
				layoutPageTemplateEntryModelImpl.
					getLayoutPageTemplateCollectionId(),
				layoutPageTemplateEntryModelImpl.getType()
			};

			finderCache.removeResult(_finderPathCountByG_L_T, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByG_L_T, args);

			args = new Object[] {
				layoutPageTemplateEntryModelImpl.getGroupId(),
				layoutPageTemplateEntryModelImpl.
					getLayoutPageTemplateCollectionId(),
				layoutPageTemplateEntryModelImpl.getStatus()
			};

			finderCache.removeResult(_finderPathCountByG_L_S, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByG_L_S, args);

			args = new Object[] {
				layoutPageTemplateEntryModelImpl.getGroupId(),
				layoutPageTemplateEntryModelImpl.getType(),
				layoutPageTemplateEntryModelImpl.getStatus()
			};

			finderCache.removeResult(_finderPathCountByG_T_S, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByG_T_S, args);

			args = new Object[] {
				layoutPageTemplateEntryModelImpl.getGroupId(),
				layoutPageTemplateEntryModelImpl.getClassNameId(),
				layoutPageTemplateEntryModelImpl.getClassTypeId(),
				layoutPageTemplateEntryModelImpl.getType()
			};

			finderCache.removeResult(_finderPathCountByG_C_C_T, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByG_C_C_T, args);

			args = new Object[] {
				layoutPageTemplateEntryModelImpl.getGroupId(),
				layoutPageTemplateEntryModelImpl.getClassNameId(),
				layoutPageTemplateEntryModelImpl.getClassTypeId(),
				layoutPageTemplateEntryModelImpl.isDefaultTemplate()
			};

			finderCache.removeResult(_finderPathCountByG_C_C_D, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByG_C_C_D, args);

			args = new Object[] {
				layoutPageTemplateEntryModelImpl.getGroupId(),
				layoutPageTemplateEntryModelImpl.getClassNameId(),
				layoutPageTemplateEntryModelImpl.getType(),
				layoutPageTemplateEntryModelImpl.isDefaultTemplate()
			};

			finderCache.removeResult(_finderPathCountByG_C_T_D, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByG_C_T_D, args);

			args = new Object[] {
				layoutPageTemplateEntryModelImpl.getGroupId(),
				layoutPageTemplateEntryModelImpl.getType(),
				layoutPageTemplateEntryModelImpl.isDefaultTemplate(),
				layoutPageTemplateEntryModelImpl.getStatus()
			};

			finderCache.removeResult(_finderPathCountByG_T_D_S, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByG_T_D_S, args);

			args = new Object[] {
				layoutPageTemplateEntryModelImpl.getGroupId(),
				layoutPageTemplateEntryModelImpl.getClassNameId(),
				layoutPageTemplateEntryModelImpl.getClassTypeId(),
				layoutPageTemplateEntryModelImpl.getType(),
				layoutPageTemplateEntryModelImpl.getStatus()
			};

			finderCache.removeResult(_finderPathCountByG_C_C_T_S, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByG_C_C_T_S, args);

			args = new Object[] {
				layoutPageTemplateEntryModelImpl.getGroupId(),
				layoutPageTemplateEntryModelImpl.getClassNameId(),
				layoutPageTemplateEntryModelImpl.getClassTypeId(),
				layoutPageTemplateEntryModelImpl.isDefaultTemplate(),
				layoutPageTemplateEntryModelImpl.getStatus()
			};

			finderCache.removeResult(_finderPathCountByG_C_C_D_S, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByG_C_C_D_S, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((layoutPageTemplateEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					layoutPageTemplateEntryModelImpl.getColumnOriginalValue(
						"uuid_")
				};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);

				args = new Object[] {
					layoutPageTemplateEntryModelImpl.getUuid()
				};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);
			}

			if ((layoutPageTemplateEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					layoutPageTemplateEntryModelImpl.getColumnOriginalValue(
						"uuid_"),
					layoutPageTemplateEntryModelImpl.getColumnOriginalValue(
						"companyId")
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);

				args = new Object[] {
					layoutPageTemplateEntryModelImpl.getUuid(),
					layoutPageTemplateEntryModelImpl.getCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);
			}

			if ((layoutPageTemplateEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByGroupId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					layoutPageTemplateEntryModelImpl.getColumnOriginalValue(
						"groupId")
				};

				finderCache.removeResult(_finderPathCountByGroupId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByGroupId, args);

				args = new Object[] {
					layoutPageTemplateEntryModelImpl.getGroupId()
				};

				finderCache.removeResult(_finderPathCountByGroupId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByGroupId, args);
			}

			if ((layoutPageTemplateEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByLayoutPrototypeId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					layoutPageTemplateEntryModelImpl.getColumnOriginalValue(
						"layoutPrototypeId")
				};

				finderCache.removeResult(
					_finderPathCountByLayoutPrototypeId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByLayoutPrototypeId, args);

				args = new Object[] {
					layoutPageTemplateEntryModelImpl.getLayoutPrototypeId()
				};

				finderCache.removeResult(
					_finderPathCountByLayoutPrototypeId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByLayoutPrototypeId, args);
			}

			if ((layoutPageTemplateEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_L.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					layoutPageTemplateEntryModelImpl.getColumnOriginalValue(
						"groupId"),
					layoutPageTemplateEntryModelImpl.getColumnOriginalValue(
						"layoutPageTemplateCollectionId")
				};

				finderCache.removeResult(_finderPathCountByG_L, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_L, args);

				args = new Object[] {
					layoutPageTemplateEntryModelImpl.getGroupId(),
					layoutPageTemplateEntryModelImpl.
						getLayoutPageTemplateCollectionId()
				};

				finderCache.removeResult(_finderPathCountByG_L, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_L, args);
			}

			if ((layoutPageTemplateEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_N.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					layoutPageTemplateEntryModelImpl.getColumnOriginalValue(
						"groupId"),
					layoutPageTemplateEntryModelImpl.getColumnOriginalValue(
						"name")
				};

				finderCache.removeResult(_finderPathCountByG_N, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_N, args);

				args = new Object[] {
					layoutPageTemplateEntryModelImpl.getGroupId(),
					layoutPageTemplateEntryModelImpl.getName()
				};

				finderCache.removeResult(_finderPathCountByG_N, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_N, args);
			}

			if ((layoutPageTemplateEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_T.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					layoutPageTemplateEntryModelImpl.getColumnOriginalValue(
						"groupId"),
					layoutPageTemplateEntryModelImpl.getColumnOriginalValue(
						"type_")
				};

				finderCache.removeResult(_finderPathCountByG_T, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_T, args);

				args = new Object[] {
					layoutPageTemplateEntryModelImpl.getGroupId(),
					layoutPageTemplateEntryModelImpl.getType()
				};

				finderCache.removeResult(_finderPathCountByG_T, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_T, args);
			}

			if ((layoutPageTemplateEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_L_T.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					layoutPageTemplateEntryModelImpl.getColumnOriginalValue(
						"groupId"),
					layoutPageTemplateEntryModelImpl.getColumnOriginalValue(
						"layoutPageTemplateCollectionId"),
					layoutPageTemplateEntryModelImpl.getColumnOriginalValue(
						"type_")
				};

				finderCache.removeResult(_finderPathCountByG_L_T, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_L_T, args);

				args = new Object[] {
					layoutPageTemplateEntryModelImpl.getGroupId(),
					layoutPageTemplateEntryModelImpl.
						getLayoutPageTemplateCollectionId(),
					layoutPageTemplateEntryModelImpl.getType()
				};

				finderCache.removeResult(_finderPathCountByG_L_T, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_L_T, args);
			}

			if ((layoutPageTemplateEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_L_S.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					layoutPageTemplateEntryModelImpl.getColumnOriginalValue(
						"groupId"),
					layoutPageTemplateEntryModelImpl.getColumnOriginalValue(
						"layoutPageTemplateCollectionId"),
					layoutPageTemplateEntryModelImpl.getColumnOriginalValue(
						"status")
				};

				finderCache.removeResult(_finderPathCountByG_L_S, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_L_S, args);

				args = new Object[] {
					layoutPageTemplateEntryModelImpl.getGroupId(),
					layoutPageTemplateEntryModelImpl.
						getLayoutPageTemplateCollectionId(),
					layoutPageTemplateEntryModelImpl.getStatus()
				};

				finderCache.removeResult(_finderPathCountByG_L_S, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_L_S, args);
			}

			if ((layoutPageTemplateEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_T_S.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					layoutPageTemplateEntryModelImpl.getColumnOriginalValue(
						"groupId"),
					layoutPageTemplateEntryModelImpl.getColumnOriginalValue(
						"type_"),
					layoutPageTemplateEntryModelImpl.getColumnOriginalValue(
						"status")
				};

				finderCache.removeResult(_finderPathCountByG_T_S, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_T_S, args);

				args = new Object[] {
					layoutPageTemplateEntryModelImpl.getGroupId(),
					layoutPageTemplateEntryModelImpl.getType(),
					layoutPageTemplateEntryModelImpl.getStatus()
				};

				finderCache.removeResult(_finderPathCountByG_T_S, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_T_S, args);
			}

			if ((layoutPageTemplateEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_C_C_T.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					layoutPageTemplateEntryModelImpl.getColumnOriginalValue(
						"groupId"),
					layoutPageTemplateEntryModelImpl.getColumnOriginalValue(
						"classNameId"),
					layoutPageTemplateEntryModelImpl.getColumnOriginalValue(
						"classTypeId"),
					layoutPageTemplateEntryModelImpl.getColumnOriginalValue(
						"type_")
				};

				finderCache.removeResult(_finderPathCountByG_C_C_T, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_C_C_T, args);

				args = new Object[] {
					layoutPageTemplateEntryModelImpl.getGroupId(),
					layoutPageTemplateEntryModelImpl.getClassNameId(),
					layoutPageTemplateEntryModelImpl.getClassTypeId(),
					layoutPageTemplateEntryModelImpl.getType()
				};

				finderCache.removeResult(_finderPathCountByG_C_C_T, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_C_C_T, args);
			}

			if ((layoutPageTemplateEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_C_C_D.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					layoutPageTemplateEntryModelImpl.getColumnOriginalValue(
						"groupId"),
					layoutPageTemplateEntryModelImpl.getColumnOriginalValue(
						"classNameId"),
					layoutPageTemplateEntryModelImpl.getColumnOriginalValue(
						"classTypeId"),
					layoutPageTemplateEntryModelImpl.getColumnOriginalValue(
						"defaultTemplate")
				};

				finderCache.removeResult(_finderPathCountByG_C_C_D, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_C_C_D, args);

				args = new Object[] {
					layoutPageTemplateEntryModelImpl.getGroupId(),
					layoutPageTemplateEntryModelImpl.getClassNameId(),
					layoutPageTemplateEntryModelImpl.getClassTypeId(),
					layoutPageTemplateEntryModelImpl.isDefaultTemplate()
				};

				finderCache.removeResult(_finderPathCountByG_C_C_D, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_C_C_D, args);
			}

			if ((layoutPageTemplateEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_C_T_D.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					layoutPageTemplateEntryModelImpl.getColumnOriginalValue(
						"groupId"),
					layoutPageTemplateEntryModelImpl.getColumnOriginalValue(
						"classNameId"),
					layoutPageTemplateEntryModelImpl.getColumnOriginalValue(
						"type_"),
					layoutPageTemplateEntryModelImpl.getColumnOriginalValue(
						"defaultTemplate")
				};

				finderCache.removeResult(_finderPathCountByG_C_T_D, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_C_T_D, args);

				args = new Object[] {
					layoutPageTemplateEntryModelImpl.getGroupId(),
					layoutPageTemplateEntryModelImpl.getClassNameId(),
					layoutPageTemplateEntryModelImpl.getType(),
					layoutPageTemplateEntryModelImpl.isDefaultTemplate()
				};

				finderCache.removeResult(_finderPathCountByG_C_T_D, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_C_T_D, args);
			}

			if ((layoutPageTemplateEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_T_D_S.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					layoutPageTemplateEntryModelImpl.getColumnOriginalValue(
						"groupId"),
					layoutPageTemplateEntryModelImpl.getColumnOriginalValue(
						"type_"),
					layoutPageTemplateEntryModelImpl.getColumnOriginalValue(
						"defaultTemplate"),
					layoutPageTemplateEntryModelImpl.getColumnOriginalValue(
						"status")
				};

				finderCache.removeResult(_finderPathCountByG_T_D_S, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_T_D_S, args);

				args = new Object[] {
					layoutPageTemplateEntryModelImpl.getGroupId(),
					layoutPageTemplateEntryModelImpl.getType(),
					layoutPageTemplateEntryModelImpl.isDefaultTemplate(),
					layoutPageTemplateEntryModelImpl.getStatus()
				};

				finderCache.removeResult(_finderPathCountByG_T_D_S, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_T_D_S, args);
			}

			if ((layoutPageTemplateEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_C_C_T_S.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					layoutPageTemplateEntryModelImpl.getColumnOriginalValue(
						"groupId"),
					layoutPageTemplateEntryModelImpl.getColumnOriginalValue(
						"classNameId"),
					layoutPageTemplateEntryModelImpl.getColumnOriginalValue(
						"classTypeId"),
					layoutPageTemplateEntryModelImpl.getColumnOriginalValue(
						"type_"),
					layoutPageTemplateEntryModelImpl.getColumnOriginalValue(
						"status")
				};

				finderCache.removeResult(_finderPathCountByG_C_C_T_S, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_C_C_T_S, args);

				args = new Object[] {
					layoutPageTemplateEntryModelImpl.getGroupId(),
					layoutPageTemplateEntryModelImpl.getClassNameId(),
					layoutPageTemplateEntryModelImpl.getClassTypeId(),
					layoutPageTemplateEntryModelImpl.getType(),
					layoutPageTemplateEntryModelImpl.getStatus()
				};

				finderCache.removeResult(_finderPathCountByG_C_C_T_S, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_C_C_T_S, args);
			}

			if ((layoutPageTemplateEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_C_C_D_S.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					layoutPageTemplateEntryModelImpl.getColumnOriginalValue(
						"groupId"),
					layoutPageTemplateEntryModelImpl.getColumnOriginalValue(
						"classNameId"),
					layoutPageTemplateEntryModelImpl.getColumnOriginalValue(
						"classTypeId"),
					layoutPageTemplateEntryModelImpl.getColumnOriginalValue(
						"defaultTemplate"),
					layoutPageTemplateEntryModelImpl.getColumnOriginalValue(
						"status")
				};

				finderCache.removeResult(_finderPathCountByG_C_C_D_S, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_C_C_D_S, args);

				args = new Object[] {
					layoutPageTemplateEntryModelImpl.getGroupId(),
					layoutPageTemplateEntryModelImpl.getClassNameId(),
					layoutPageTemplateEntryModelImpl.getClassTypeId(),
					layoutPageTemplateEntryModelImpl.isDefaultTemplate(),
					layoutPageTemplateEntryModelImpl.getStatus()
				};

				finderCache.removeResult(_finderPathCountByG_C_C_D_S, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_C_C_D_S, args);
			}
		}

		entityCache.putResult(
			LayoutPageTemplateEntryImpl.class,
			layoutPageTemplateEntry.getPrimaryKey(), layoutPageTemplateEntry,
			false);

		clearUniqueFindersCache(layoutPageTemplateEntryModelImpl, false);
		cacheUniqueFindersCache(layoutPageTemplateEntryModelImpl);

		layoutPageTemplateEntry.resetOriginalValues();

		return layoutPageTemplateEntry;
	}

	/**
	 * Returns the layout page template entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the layout page template entry
	 * @return the layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a layout page template entry with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchPageTemplateEntryException {

		LayoutPageTemplateEntry layoutPageTemplateEntry = fetchByPrimaryKey(
			primaryKey);

		if (layoutPageTemplateEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchPageTemplateEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return layoutPageTemplateEntry;
	}

	/**
	 * Returns the layout page template entry with the primary key or throws a <code>NoSuchPageTemplateEntryException</code> if it could not be found.
	 *
	 * @param layoutPageTemplateEntryId the primary key of the layout page template entry
	 * @return the layout page template entry
	 * @throws NoSuchPageTemplateEntryException if a layout page template entry with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateEntry findByPrimaryKey(
			long layoutPageTemplateEntryId)
		throws NoSuchPageTemplateEntryException {

		return findByPrimaryKey((Serializable)layoutPageTemplateEntryId);
	}

	/**
	 * Returns the layout page template entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the layout page template entry
	 * @return the layout page template entry, or <code>null</code> if a layout page template entry with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateEntry fetchByPrimaryKey(Serializable primaryKey) {
		if (ctPersistenceHelper.isProductionMode(
				LayoutPageTemplateEntry.class)) {

			return super.fetchByPrimaryKey(primaryKey);
		}

		LayoutPageTemplateEntry layoutPageTemplateEntry = null;

		Session session = null;

		try {
			session = openSession();

			layoutPageTemplateEntry = (LayoutPageTemplateEntry)session.get(
				LayoutPageTemplateEntryImpl.class, primaryKey);

			if (layoutPageTemplateEntry != null) {
				cacheResult(layoutPageTemplateEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return layoutPageTemplateEntry;
	}

	/**
	 * Returns the layout page template entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param layoutPageTemplateEntryId the primary key of the layout page template entry
	 * @return the layout page template entry, or <code>null</code> if a layout page template entry with the primary key could not be found
	 */
	@Override
	public LayoutPageTemplateEntry fetchByPrimaryKey(
		long layoutPageTemplateEntryId) {

		return fetchByPrimaryKey((Serializable)layoutPageTemplateEntryId);
	}

	@Override
	public Map<Serializable, LayoutPageTemplateEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (ctPersistenceHelper.isProductionMode(
				LayoutPageTemplateEntry.class)) {

			return super.fetchByPrimaryKeys(primaryKeys);
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, LayoutPageTemplateEntry> map =
			new HashMap<Serializable, LayoutPageTemplateEntry>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			LayoutPageTemplateEntry layoutPageTemplateEntry = fetchByPrimaryKey(
				primaryKey);

			if (layoutPageTemplateEntry != null) {
				map.put(primaryKey, layoutPageTemplateEntry);
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

			for (LayoutPageTemplateEntry layoutPageTemplateEntry :
					(List<LayoutPageTemplateEntry>)query.list()) {

				map.put(
					layoutPageTemplateEntry.getPrimaryKeyObj(),
					layoutPageTemplateEntry);

				cacheResult(layoutPageTemplateEntry);
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
	 * Returns all the layout page template entries.
	 *
	 * @return the layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout page template entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @return the range of layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout page template entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findAll(
		int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout page template entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutPageTemplateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout page template entries
	 * @param end the upper bound of the range of layout page template entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of layout page template entries
	 */
	@Override
	public List<LayoutPageTemplateEntry> findAll(
		int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			LayoutPageTemplateEntry.class);

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

		List<LayoutPageTemplateEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<LayoutPageTemplateEntry>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_LAYOUTPAGETEMPLATEENTRY;

				sql = sql.concat(
					LayoutPageTemplateEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<LayoutPageTemplateEntry>)QueryUtil.list(
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
	 * Removes all the layout page template entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (LayoutPageTemplateEntry layoutPageTemplateEntry : findAll()) {
			remove(layoutPageTemplateEntry);
		}
	}

	/**
	 * Returns the number of layout page template entries.
	 *
	 * @return the number of layout page template entries
	 */
	@Override
	public int countAll() {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			LayoutPageTemplateEntry.class);

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
					_SQL_COUNT_LAYOUTPAGETEMPLATEENTRY);

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
		return "layoutPageTemplateEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_LAYOUTPAGETEMPLATEENTRY;
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
		return LayoutPageTemplateEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "LayoutPageTemplateEntry";
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
		ctStrictColumnNames.add("layoutPageTemplateCollectionId");
		ctStrictColumnNames.add("layoutPageTemplateEntryKey");
		ctStrictColumnNames.add("classNameId");
		ctStrictColumnNames.add("classTypeId");
		ctStrictColumnNames.add("name");
		ctStrictColumnNames.add("type_");
		ctStrictColumnNames.add("previewFileEntryId");
		ctStrictColumnNames.add("defaultTemplate");
		ctStrictColumnNames.add("layoutPrototypeId");
		ctStrictColumnNames.add("plid");
		ctStrictColumnNames.add("lastPublishDate");
		ctStrictColumnNames.add("status");
		ctStrictColumnNames.add("statusByUserId");
		ctStrictColumnNames.add("statusByUserName");
		ctStrictColumnNames.add("statusDate");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK,
			Collections.singleton("layoutPageTemplateEntryId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(new String[] {"uuid_", "groupId"});

		_uniqueIndexColumnNames.add(new String[] {"plid"});

		_uniqueIndexColumnNames.add(
			new String[] {"groupId", "layoutPageTemplateEntryKey"});

		_uniqueIndexColumnNames.add(new String[] {"groupId", "name", "type_"});
	}

	/**
	 * Initializes the layout page template entry persistence.
	 */
	@Activate
	public void activate() {
		_finderPathWithPaginationFindAll = new FinderPath(
			LayoutPageTemplateEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			LayoutPageTemplateEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByUuid = new FinderPath(
			LayoutPageTemplateEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			LayoutPageTemplateEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] {String.class.getName()},
			LayoutPageTemplateEntryModelImpl.getColumnBitmask("uuid_") |
			LayoutPageTemplateEntryModelImpl.getColumnBitmask("name"));

		_finderPathCountByUuid = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid", new String[] {String.class.getName()});

		_finderPathFetchByUUID_G = new FinderPath(
			LayoutPageTemplateEntryImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			LayoutPageTemplateEntryModelImpl.getColumnBitmask("uuid_") |
			LayoutPageTemplateEntryModelImpl.getColumnBitmask("groupId"));

		_finderPathCountByUUID_G = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			LayoutPageTemplateEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			LayoutPageTemplateEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			LayoutPageTemplateEntryModelImpl.getColumnBitmask("uuid_") |
			LayoutPageTemplateEntryModelImpl.getColumnBitmask("companyId") |
			LayoutPageTemplateEntryModelImpl.getColumnBitmask("name"));

		_finderPathCountByUuid_C = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByGroupId = new FinderPath(
			LayoutPageTemplateEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByGroupId = new FinderPath(
			LayoutPageTemplateEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] {Long.class.getName()},
			LayoutPageTemplateEntryModelImpl.getColumnBitmask("groupId") |
			LayoutPageTemplateEntryModelImpl.getColumnBitmask("name"));

		_finderPathCountByGroupId = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByGroupId", new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByLayoutPrototypeId = new FinderPath(
			LayoutPageTemplateEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByLayoutPrototypeId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByLayoutPrototypeId = new FinderPath(
			LayoutPageTemplateEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByLayoutPrototypeId", new String[] {Long.class.getName()},
			LayoutPageTemplateEntryModelImpl.getColumnBitmask(
				"layoutPrototypeId") |
			LayoutPageTemplateEntryModelImpl.getColumnBitmask("name"));

		_finderPathCountByLayoutPrototypeId = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByLayoutPrototypeId", new String[] {Long.class.getName()});

		_finderPathFetchByPlid = new FinderPath(
			LayoutPageTemplateEntryImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByPlid", new String[] {Long.class.getName()},
			LayoutPageTemplateEntryModelImpl.getColumnBitmask("plid"));

		_finderPathCountByPlid = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByPlid", new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByG_L = new FinderPath(
			LayoutPageTemplateEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_L",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_L = new FinderPath(
			LayoutPageTemplateEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_L",
			new String[] {Long.class.getName(), Long.class.getName()},
			LayoutPageTemplateEntryModelImpl.getColumnBitmask("groupId") |
			LayoutPageTemplateEntryModelImpl.getColumnBitmask(
				"layoutPageTemplateCollectionId") |
			LayoutPageTemplateEntryModelImpl.getColumnBitmask("name"));

		_finderPathCountByG_L = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_L",
			new String[] {Long.class.getName(), Long.class.getName()});

		_finderPathFetchByG_LPTEK = new FinderPath(
			LayoutPageTemplateEntryImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByG_LPTEK",
			new String[] {Long.class.getName(), String.class.getName()},
			LayoutPageTemplateEntryModelImpl.getColumnBitmask("groupId") |
			LayoutPageTemplateEntryModelImpl.getColumnBitmask(
				"layoutPageTemplateEntryKey"));

		_finderPathCountByG_LPTEK = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_LPTEK",
			new String[] {Long.class.getName(), String.class.getName()});

		_finderPathWithPaginationFindByG_N = new FinderPath(
			LayoutPageTemplateEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_N",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_N = new FinderPath(
			LayoutPageTemplateEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_N",
			new String[] {Long.class.getName(), String.class.getName()},
			LayoutPageTemplateEntryModelImpl.getColumnBitmask("groupId") |
			LayoutPageTemplateEntryModelImpl.getColumnBitmask("name"));

		_finderPathCountByG_N = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_N",
			new String[] {Long.class.getName(), String.class.getName()});

		_finderPathWithPaginationFindByG_T = new FinderPath(
			LayoutPageTemplateEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_T",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_T = new FinderPath(
			LayoutPageTemplateEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_T",
			new String[] {Long.class.getName(), Integer.class.getName()},
			LayoutPageTemplateEntryModelImpl.getColumnBitmask("groupId") |
			LayoutPageTemplateEntryModelImpl.getColumnBitmask("type_") |
			LayoutPageTemplateEntryModelImpl.getColumnBitmask("name"));

		_finderPathCountByG_T = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_T",
			new String[] {Long.class.getName(), Integer.class.getName()});

		_finderPathWithPaginationFindByG_L_LikeN = new FinderPath(
			LayoutPageTemplateEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_L_LikeN",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithPaginationCountByG_L_LikeN = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"countByG_L_LikeN",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});

		_finderPathWithPaginationFindByG_L_T = new FinderPath(
			LayoutPageTemplateEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_L_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_L_T = new FinderPath(
			LayoutPageTemplateEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_L_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			LayoutPageTemplateEntryModelImpl.getColumnBitmask("groupId") |
			LayoutPageTemplateEntryModelImpl.getColumnBitmask(
				"layoutPageTemplateCollectionId") |
			LayoutPageTemplateEntryModelImpl.getColumnBitmask("type_") |
			LayoutPageTemplateEntryModelImpl.getColumnBitmask("name"));

		_finderPathCountByG_L_T = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_L_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});

		_finderPathWithPaginationFindByG_L_S = new FinderPath(
			LayoutPageTemplateEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_L_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_L_S = new FinderPath(
			LayoutPageTemplateEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_L_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			LayoutPageTemplateEntryModelImpl.getColumnBitmask("groupId") |
			LayoutPageTemplateEntryModelImpl.getColumnBitmask(
				"layoutPageTemplateCollectionId") |
			LayoutPageTemplateEntryModelImpl.getColumnBitmask("status") |
			LayoutPageTemplateEntryModelImpl.getColumnBitmask("name"));

		_finderPathCountByG_L_S = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_L_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});

		_finderPathFetchByG_N_T = new FinderPath(
			LayoutPageTemplateEntryImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByG_N_T",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName()
			},
			LayoutPageTemplateEntryModelImpl.getColumnBitmask("groupId") |
			LayoutPageTemplateEntryModelImpl.getColumnBitmask("name") |
			LayoutPageTemplateEntryModelImpl.getColumnBitmask("type_"));

		_finderPathCountByG_N_T = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_N_T",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName()
			});

		_finderPathWithPaginationFindByG_T_LikeN = new FinderPath(
			LayoutPageTemplateEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_T_LikeN",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithPaginationCountByG_T_LikeN = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"countByG_T_LikeN",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName()
			});

		_finderPathWithPaginationFindByG_T_S = new FinderPath(
			LayoutPageTemplateEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_T_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_T_S = new FinderPath(
			LayoutPageTemplateEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_T_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName()
			},
			LayoutPageTemplateEntryModelImpl.getColumnBitmask("groupId") |
			LayoutPageTemplateEntryModelImpl.getColumnBitmask("type_") |
			LayoutPageTemplateEntryModelImpl.getColumnBitmask("status") |
			LayoutPageTemplateEntryModelImpl.getColumnBitmask("name"));

		_finderPathCountByG_T_S = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_T_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName()
			});

		_finderPathWithPaginationFindByG_L_LikeN_S = new FinderPath(
			LayoutPageTemplateEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_L_LikeN_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithPaginationCountByG_L_LikeN_S = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"countByG_L_LikeN_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Integer.class.getName()
			});

		_finderPathWithPaginationFindByG_C_C_T = new FinderPath(
			LayoutPageTemplateEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_C_C_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_C_C_T = new FinderPath(
			LayoutPageTemplateEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_C_C_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName()
			},
			LayoutPageTemplateEntryModelImpl.getColumnBitmask("groupId") |
			LayoutPageTemplateEntryModelImpl.getColumnBitmask("classNameId") |
			LayoutPageTemplateEntryModelImpl.getColumnBitmask("classTypeId") |
			LayoutPageTemplateEntryModelImpl.getColumnBitmask("type_") |
			LayoutPageTemplateEntryModelImpl.getColumnBitmask("name"));

		_finderPathCountByG_C_C_T = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_C_C_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName()
			});

		_finderPathWithPaginationFindByG_C_C_D = new FinderPath(
			LayoutPageTemplateEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_C_C_D",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_C_C_D = new FinderPath(
			LayoutPageTemplateEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_C_C_D",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Boolean.class.getName()
			},
			LayoutPageTemplateEntryModelImpl.getColumnBitmask("groupId") |
			LayoutPageTemplateEntryModelImpl.getColumnBitmask("classNameId") |
			LayoutPageTemplateEntryModelImpl.getColumnBitmask("classTypeId") |
			LayoutPageTemplateEntryModelImpl.getColumnBitmask(
				"defaultTemplate") |
			LayoutPageTemplateEntryModelImpl.getColumnBitmask("name"));

		_finderPathCountByG_C_C_D = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_C_C_D",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Boolean.class.getName()
			});

		_finderPathWithPaginationFindByG_C_T_D = new FinderPath(
			LayoutPageTemplateEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_C_T_D",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_C_T_D = new FinderPath(
			LayoutPageTemplateEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_C_T_D",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Boolean.class.getName()
			},
			LayoutPageTemplateEntryModelImpl.getColumnBitmask("groupId") |
			LayoutPageTemplateEntryModelImpl.getColumnBitmask("classNameId") |
			LayoutPageTemplateEntryModelImpl.getColumnBitmask("type_") |
			LayoutPageTemplateEntryModelImpl.getColumnBitmask(
				"defaultTemplate") |
			LayoutPageTemplateEntryModelImpl.getColumnBitmask("name"));

		_finderPathCountByG_C_T_D = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_C_T_D",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Boolean.class.getName()
			});

		_finderPathWithPaginationFindByG_T_LikeN_S = new FinderPath(
			LayoutPageTemplateEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_T_LikeN_S",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithPaginationCountByG_T_LikeN_S = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"countByG_T_LikeN_S",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName()
			});

		_finderPathWithPaginationFindByG_T_D_S = new FinderPath(
			LayoutPageTemplateEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_T_D_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Boolean.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_T_D_S = new FinderPath(
			LayoutPageTemplateEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_T_D_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Boolean.class.getName(), Integer.class.getName()
			},
			LayoutPageTemplateEntryModelImpl.getColumnBitmask("groupId") |
			LayoutPageTemplateEntryModelImpl.getColumnBitmask("type_") |
			LayoutPageTemplateEntryModelImpl.getColumnBitmask(
				"defaultTemplate") |
			LayoutPageTemplateEntryModelImpl.getColumnBitmask("status") |
			LayoutPageTemplateEntryModelImpl.getColumnBitmask("name"));

		_finderPathCountByG_T_D_S = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_T_D_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Boolean.class.getName(), Integer.class.getName()
			});

		_finderPathWithPaginationFindByG_C_C_LikeN_T = new FinderPath(
			LayoutPageTemplateEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_C_C_LikeN_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithPaginationCountByG_C_C_LikeN_T = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"countByG_C_C_LikeN_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), String.class.getName(),
				Integer.class.getName()
			});

		_finderPathWithPaginationFindByG_C_C_T_S = new FinderPath(
			LayoutPageTemplateEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_C_C_T_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_C_C_T_S = new FinderPath(
			LayoutPageTemplateEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_C_C_T_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName()
			},
			LayoutPageTemplateEntryModelImpl.getColumnBitmask("groupId") |
			LayoutPageTemplateEntryModelImpl.getColumnBitmask("classNameId") |
			LayoutPageTemplateEntryModelImpl.getColumnBitmask("classTypeId") |
			LayoutPageTemplateEntryModelImpl.getColumnBitmask("type_") |
			LayoutPageTemplateEntryModelImpl.getColumnBitmask("status") |
			LayoutPageTemplateEntryModelImpl.getColumnBitmask("name"));

		_finderPathCountByG_C_C_T_S = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_C_C_T_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName()
			});

		_finderPathWithPaginationFindByG_C_C_D_S = new FinderPath(
			LayoutPageTemplateEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_C_C_D_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_C_C_D_S = new FinderPath(
			LayoutPageTemplateEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_C_C_D_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName()
			},
			LayoutPageTemplateEntryModelImpl.getColumnBitmask("groupId") |
			LayoutPageTemplateEntryModelImpl.getColumnBitmask("classNameId") |
			LayoutPageTemplateEntryModelImpl.getColumnBitmask("classTypeId") |
			LayoutPageTemplateEntryModelImpl.getColumnBitmask(
				"defaultTemplate") |
			LayoutPageTemplateEntryModelImpl.getColumnBitmask("status") |
			LayoutPageTemplateEntryModelImpl.getColumnBitmask("name"));

		_finderPathCountByG_C_C_D_S = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_C_C_D_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName()
			});

		_finderPathWithPaginationFindByG_C_C_LikeN_T_S = new FinderPath(
			LayoutPageTemplateEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_C_C_LikeN_T_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithPaginationCountByG_C_C_LikeN_T_S = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"countByG_C_C_LikeN_T_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName()
			});
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(LayoutPageTemplateEntryImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	@Reference(
		target = LayoutPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = LayoutPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = LayoutPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
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

	private static final String _SQL_SELECT_LAYOUTPAGETEMPLATEENTRY =
		"SELECT layoutPageTemplateEntry FROM LayoutPageTemplateEntry layoutPageTemplateEntry";

	private static final String _SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE =
		"SELECT layoutPageTemplateEntry FROM LayoutPageTemplateEntry layoutPageTemplateEntry WHERE ";

	private static final String _SQL_COUNT_LAYOUTPAGETEMPLATEENTRY =
		"SELECT COUNT(layoutPageTemplateEntry) FROM LayoutPageTemplateEntry layoutPageTemplateEntry";

	private static final String _SQL_COUNT_LAYOUTPAGETEMPLATEENTRY_WHERE =
		"SELECT COUNT(layoutPageTemplateEntry) FROM LayoutPageTemplateEntry layoutPageTemplateEntry WHERE ";

	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN =
		"layoutPageTemplateEntry.layoutPageTemplateEntryId";

	private static final String
		_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_WHERE =
			"SELECT DISTINCT {layoutPageTemplateEntry.*} FROM LayoutPageTemplateEntry layoutPageTemplateEntry WHERE ";

	private static final String
		_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_1 =
			"SELECT {LayoutPageTemplateEntry.*} FROM (SELECT DISTINCT layoutPageTemplateEntry.layoutPageTemplateEntryId FROM LayoutPageTemplateEntry layoutPageTemplateEntry WHERE ";

	private static final String
		_FILTER_SQL_SELECT_LAYOUTPAGETEMPLATEENTRY_NO_INLINE_DISTINCT_WHERE_2 =
			") TEMP_TABLE INNER JOIN LayoutPageTemplateEntry ON TEMP_TABLE.layoutPageTemplateEntryId = LayoutPageTemplateEntry.layoutPageTemplateEntryId";

	private static final String
		_FILTER_SQL_COUNT_LAYOUTPAGETEMPLATEENTRY_WHERE =
			"SELECT COUNT(DISTINCT layoutPageTemplateEntry.layoutPageTemplateEntryId) AS COUNT_VALUE FROM LayoutPageTemplateEntry layoutPageTemplateEntry WHERE ";

	private static final String _FILTER_ENTITY_ALIAS =
		"layoutPageTemplateEntry";

	private static final String _FILTER_ENTITY_TABLE =
		"LayoutPageTemplateEntry";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"layoutPageTemplateEntry.";

	private static final String _ORDER_BY_ENTITY_TABLE =
		"LayoutPageTemplateEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No LayoutPageTemplateEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No LayoutPageTemplateEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutPageTemplateEntryPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid", "type"});

	static {
		try {
			Class.forName(LayoutPersistenceConstants.class.getName());
		}
		catch (ClassNotFoundException classNotFoundException) {
			throw new ExceptionInInitializerError(classNotFoundException);
		}
	}

}