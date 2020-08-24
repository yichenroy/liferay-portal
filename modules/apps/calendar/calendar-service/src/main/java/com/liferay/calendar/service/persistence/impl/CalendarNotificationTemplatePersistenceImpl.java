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

package com.liferay.calendar.service.persistence.impl;

import com.liferay.calendar.exception.NoSuchNotificationTemplateException;
import com.liferay.calendar.model.CalendarNotificationTemplate;
import com.liferay.calendar.model.CalendarNotificationTemplateTable;
import com.liferay.calendar.model.impl.CalendarNotificationTemplateImpl;
import com.liferay.calendar.model.impl.CalendarNotificationTemplateModelImpl;
import com.liferay.calendar.service.persistence.CalendarNotificationTemplatePersistence;
import com.liferay.calendar.service.persistence.impl.constants.CalendarPersistenceConstants;
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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
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
 * The persistence implementation for the calendar notification template service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Eduardo Lundgren
 * @generated
 */
@Component(service = CalendarNotificationTemplatePersistence.class)
public class CalendarNotificationTemplatePersistenceImpl
	extends BasePersistenceImpl<CalendarNotificationTemplate>
	implements CalendarNotificationTemplatePersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CalendarNotificationTemplateUtil</code> to access the calendar notification template persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CalendarNotificationTemplateImpl.class.getName();

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
	 * Returns all the calendar notification templates where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching calendar notification templates
	 */
	@Override
	public List<CalendarNotificationTemplate> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the calendar notification templates where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CalendarNotificationTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of calendar notification templates
	 * @param end the upper bound of the range of calendar notification templates (not inclusive)
	 * @return the range of matching calendar notification templates
	 */
	@Override
	public List<CalendarNotificationTemplate> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the calendar notification templates where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CalendarNotificationTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of calendar notification templates
	 * @param end the upper bound of the range of calendar notification templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching calendar notification templates
	 */
	@Override
	public List<CalendarNotificationTemplate> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CalendarNotificationTemplate> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the calendar notification templates where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CalendarNotificationTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of calendar notification templates
	 * @param end the upper bound of the range of calendar notification templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching calendar notification templates
	 */
	@Override
	public List<CalendarNotificationTemplate> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CalendarNotificationTemplate> orderByComparator,
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

		List<CalendarNotificationTemplate> list = null;

		if (useFinderCache) {
			list = (List<CalendarNotificationTemplate>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CalendarNotificationTemplate calendarNotificationTemplate :
						list) {

					if (!uuid.equals(calendarNotificationTemplate.getUuid())) {
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

			sb.append(_SQL_SELECT_CALENDARNOTIFICATIONTEMPLATE_WHERE);

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
				sb.append(CalendarNotificationTemplateModelImpl.ORDER_BY_JPQL);
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

				list = (List<CalendarNotificationTemplate>)QueryUtil.list(
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
	 * Returns the first calendar notification template in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching calendar notification template
	 * @throws NoSuchNotificationTemplateException if a matching calendar notification template could not be found
	 */
	@Override
	public CalendarNotificationTemplate findByUuid_First(
			String uuid,
			OrderByComparator<CalendarNotificationTemplate> orderByComparator)
		throws NoSuchNotificationTemplateException {

		CalendarNotificationTemplate calendarNotificationTemplate =
			fetchByUuid_First(uuid, orderByComparator);

		if (calendarNotificationTemplate != null) {
			return calendarNotificationTemplate;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchNotificationTemplateException(sb.toString());
	}

	/**
	 * Returns the first calendar notification template in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching calendar notification template, or <code>null</code> if a matching calendar notification template could not be found
	 */
	@Override
	public CalendarNotificationTemplate fetchByUuid_First(
		String uuid,
		OrderByComparator<CalendarNotificationTemplate> orderByComparator) {

		List<CalendarNotificationTemplate> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last calendar notification template in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching calendar notification template
	 * @throws NoSuchNotificationTemplateException if a matching calendar notification template could not be found
	 */
	@Override
	public CalendarNotificationTemplate findByUuid_Last(
			String uuid,
			OrderByComparator<CalendarNotificationTemplate> orderByComparator)
		throws NoSuchNotificationTemplateException {

		CalendarNotificationTemplate calendarNotificationTemplate =
			fetchByUuid_Last(uuid, orderByComparator);

		if (calendarNotificationTemplate != null) {
			return calendarNotificationTemplate;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchNotificationTemplateException(sb.toString());
	}

	/**
	 * Returns the last calendar notification template in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching calendar notification template, or <code>null</code> if a matching calendar notification template could not be found
	 */
	@Override
	public CalendarNotificationTemplate fetchByUuid_Last(
		String uuid,
		OrderByComparator<CalendarNotificationTemplate> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<CalendarNotificationTemplate> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the calendar notification templates before and after the current calendar notification template in the ordered set where uuid = &#63;.
	 *
	 * @param calendarNotificationTemplateId the primary key of the current calendar notification template
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next calendar notification template
	 * @throws NoSuchNotificationTemplateException if a calendar notification template with the primary key could not be found
	 */
	@Override
	public CalendarNotificationTemplate[] findByUuid_PrevAndNext(
			long calendarNotificationTemplateId, String uuid,
			OrderByComparator<CalendarNotificationTemplate> orderByComparator)
		throws NoSuchNotificationTemplateException {

		uuid = Objects.toString(uuid, "");

		CalendarNotificationTemplate calendarNotificationTemplate =
			findByPrimaryKey(calendarNotificationTemplateId);

		Session session = null;

		try {
			session = openSession();

			CalendarNotificationTemplate[] array =
				new CalendarNotificationTemplateImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, calendarNotificationTemplate, uuid, orderByComparator,
				true);

			array[1] = calendarNotificationTemplate;

			array[2] = getByUuid_PrevAndNext(
				session, calendarNotificationTemplate, uuid, orderByComparator,
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

	protected CalendarNotificationTemplate getByUuid_PrevAndNext(
		Session session,
		CalendarNotificationTemplate calendarNotificationTemplate, String uuid,
		OrderByComparator<CalendarNotificationTemplate> orderByComparator,
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

		sb.append(_SQL_SELECT_CALENDARNOTIFICATIONTEMPLATE_WHERE);

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
			sb.append(CalendarNotificationTemplateModelImpl.ORDER_BY_JPQL);
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
						calendarNotificationTemplate)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CalendarNotificationTemplate> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the calendar notification templates where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (CalendarNotificationTemplate calendarNotificationTemplate :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(calendarNotificationTemplate);
		}
	}

	/**
	 * Returns the number of calendar notification templates where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching calendar notification templates
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_CALENDARNOTIFICATIONTEMPLATE_WHERE);

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
		"calendarNotificationTemplate.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(calendarNotificationTemplate.uuid IS NULL OR calendarNotificationTemplate.uuid = '')";

	private FinderPath _finderPathFetchByUUID_G;
	private FinderPath _finderPathCountByUUID_G;

	/**
	 * Returns the calendar notification template where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchNotificationTemplateException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching calendar notification template
	 * @throws NoSuchNotificationTemplateException if a matching calendar notification template could not be found
	 */
	@Override
	public CalendarNotificationTemplate findByUUID_G(String uuid, long groupId)
		throws NoSuchNotificationTemplateException {

		CalendarNotificationTemplate calendarNotificationTemplate =
			fetchByUUID_G(uuid, groupId);

		if (calendarNotificationTemplate == null) {
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

			throw new NoSuchNotificationTemplateException(sb.toString());
		}

		return calendarNotificationTemplate;
	}

	/**
	 * Returns the calendar notification template where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching calendar notification template, or <code>null</code> if a matching calendar notification template could not be found
	 */
	@Override
	public CalendarNotificationTemplate fetchByUUID_G(
		String uuid, long groupId) {

		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the calendar notification template where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching calendar notification template, or <code>null</code> if a matching calendar notification template could not be found
	 */
	@Override
	public CalendarNotificationTemplate fetchByUUID_G(
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

		if (result instanceof CalendarNotificationTemplate) {
			CalendarNotificationTemplate calendarNotificationTemplate =
				(CalendarNotificationTemplate)result;

			if (!Objects.equals(uuid, calendarNotificationTemplate.getUuid()) ||
				(groupId != calendarNotificationTemplate.getGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_CALENDARNOTIFICATIONTEMPLATE_WHERE);

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

				List<CalendarNotificationTemplate> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByUUID_G, finderArgs, list);
					}
				}
				else {
					CalendarNotificationTemplate calendarNotificationTemplate =
						list.get(0);

					result = calendarNotificationTemplate;

					cacheResult(calendarNotificationTemplate);
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
			return (CalendarNotificationTemplate)result;
		}
	}

	/**
	 * Removes the calendar notification template where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the calendar notification template that was removed
	 */
	@Override
	public CalendarNotificationTemplate removeByUUID_G(
			String uuid, long groupId)
		throws NoSuchNotificationTemplateException {

		CalendarNotificationTemplate calendarNotificationTemplate =
			findByUUID_G(uuid, groupId);

		return remove(calendarNotificationTemplate);
	}

	/**
	 * Returns the number of calendar notification templates where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching calendar notification templates
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUUID_G;

		Object[] finderArgs = new Object[] {uuid, groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_CALENDARNOTIFICATIONTEMPLATE_WHERE);

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
		"calendarNotificationTemplate.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(calendarNotificationTemplate.uuid IS NULL OR calendarNotificationTemplate.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"calendarNotificationTemplate.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the calendar notification templates where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching calendar notification templates
	 */
	@Override
	public List<CalendarNotificationTemplate> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the calendar notification templates where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CalendarNotificationTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of calendar notification templates
	 * @param end the upper bound of the range of calendar notification templates (not inclusive)
	 * @return the range of matching calendar notification templates
	 */
	@Override
	public List<CalendarNotificationTemplate> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the calendar notification templates where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CalendarNotificationTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of calendar notification templates
	 * @param end the upper bound of the range of calendar notification templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching calendar notification templates
	 */
	@Override
	public List<CalendarNotificationTemplate> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CalendarNotificationTemplate> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the calendar notification templates where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CalendarNotificationTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of calendar notification templates
	 * @param end the upper bound of the range of calendar notification templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching calendar notification templates
	 */
	@Override
	public List<CalendarNotificationTemplate> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CalendarNotificationTemplate> orderByComparator,
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

		List<CalendarNotificationTemplate> list = null;

		if (useFinderCache) {
			list = (List<CalendarNotificationTemplate>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CalendarNotificationTemplate calendarNotificationTemplate :
						list) {

					if (!uuid.equals(calendarNotificationTemplate.getUuid()) ||
						(companyId !=
							calendarNotificationTemplate.getCompanyId())) {

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

			sb.append(_SQL_SELECT_CALENDARNOTIFICATIONTEMPLATE_WHERE);

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
				sb.append(CalendarNotificationTemplateModelImpl.ORDER_BY_JPQL);
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

				list = (List<CalendarNotificationTemplate>)QueryUtil.list(
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
	 * Returns the first calendar notification template in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching calendar notification template
	 * @throws NoSuchNotificationTemplateException if a matching calendar notification template could not be found
	 */
	@Override
	public CalendarNotificationTemplate findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<CalendarNotificationTemplate> orderByComparator)
		throws NoSuchNotificationTemplateException {

		CalendarNotificationTemplate calendarNotificationTemplate =
			fetchByUuid_C_First(uuid, companyId, orderByComparator);

		if (calendarNotificationTemplate != null) {
			return calendarNotificationTemplate;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchNotificationTemplateException(sb.toString());
	}

	/**
	 * Returns the first calendar notification template in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching calendar notification template, or <code>null</code> if a matching calendar notification template could not be found
	 */
	@Override
	public CalendarNotificationTemplate fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<CalendarNotificationTemplate> orderByComparator) {

		List<CalendarNotificationTemplate> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last calendar notification template in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching calendar notification template
	 * @throws NoSuchNotificationTemplateException if a matching calendar notification template could not be found
	 */
	@Override
	public CalendarNotificationTemplate findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<CalendarNotificationTemplate> orderByComparator)
		throws NoSuchNotificationTemplateException {

		CalendarNotificationTemplate calendarNotificationTemplate =
			fetchByUuid_C_Last(uuid, companyId, orderByComparator);

		if (calendarNotificationTemplate != null) {
			return calendarNotificationTemplate;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchNotificationTemplateException(sb.toString());
	}

	/**
	 * Returns the last calendar notification template in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching calendar notification template, or <code>null</code> if a matching calendar notification template could not be found
	 */
	@Override
	public CalendarNotificationTemplate fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<CalendarNotificationTemplate> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<CalendarNotificationTemplate> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the calendar notification templates before and after the current calendar notification template in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param calendarNotificationTemplateId the primary key of the current calendar notification template
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next calendar notification template
	 * @throws NoSuchNotificationTemplateException if a calendar notification template with the primary key could not be found
	 */
	@Override
	public CalendarNotificationTemplate[] findByUuid_C_PrevAndNext(
			long calendarNotificationTemplateId, String uuid, long companyId,
			OrderByComparator<CalendarNotificationTemplate> orderByComparator)
		throws NoSuchNotificationTemplateException {

		uuid = Objects.toString(uuid, "");

		CalendarNotificationTemplate calendarNotificationTemplate =
			findByPrimaryKey(calendarNotificationTemplateId);

		Session session = null;

		try {
			session = openSession();

			CalendarNotificationTemplate[] array =
				new CalendarNotificationTemplateImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, calendarNotificationTemplate, uuid, companyId,
				orderByComparator, true);

			array[1] = calendarNotificationTemplate;

			array[2] = getByUuid_C_PrevAndNext(
				session, calendarNotificationTemplate, uuid, companyId,
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

	protected CalendarNotificationTemplate getByUuid_C_PrevAndNext(
		Session session,
		CalendarNotificationTemplate calendarNotificationTemplate, String uuid,
		long companyId,
		OrderByComparator<CalendarNotificationTemplate> orderByComparator,
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

		sb.append(_SQL_SELECT_CALENDARNOTIFICATIONTEMPLATE_WHERE);

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
			sb.append(CalendarNotificationTemplateModelImpl.ORDER_BY_JPQL);
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
						calendarNotificationTemplate)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CalendarNotificationTemplate> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the calendar notification templates where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (CalendarNotificationTemplate calendarNotificationTemplate :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(calendarNotificationTemplate);
		}
	}

	/**
	 * Returns the number of calendar notification templates where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching calendar notification templates
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_CALENDARNOTIFICATIONTEMPLATE_WHERE);

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
		"calendarNotificationTemplate.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(calendarNotificationTemplate.uuid IS NULL OR calendarNotificationTemplate.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"calendarNotificationTemplate.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByCalendarId;
	private FinderPath _finderPathWithoutPaginationFindByCalendarId;
	private FinderPath _finderPathCountByCalendarId;

	/**
	 * Returns all the calendar notification templates where calendarId = &#63;.
	 *
	 * @param calendarId the calendar ID
	 * @return the matching calendar notification templates
	 */
	@Override
	public List<CalendarNotificationTemplate> findByCalendarId(
		long calendarId) {

		return findByCalendarId(
			calendarId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the calendar notification templates where calendarId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CalendarNotificationTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param calendarId the calendar ID
	 * @param start the lower bound of the range of calendar notification templates
	 * @param end the upper bound of the range of calendar notification templates (not inclusive)
	 * @return the range of matching calendar notification templates
	 */
	@Override
	public List<CalendarNotificationTemplate> findByCalendarId(
		long calendarId, int start, int end) {

		return findByCalendarId(calendarId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the calendar notification templates where calendarId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CalendarNotificationTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param calendarId the calendar ID
	 * @param start the lower bound of the range of calendar notification templates
	 * @param end the upper bound of the range of calendar notification templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching calendar notification templates
	 */
	@Override
	public List<CalendarNotificationTemplate> findByCalendarId(
		long calendarId, int start, int end,
		OrderByComparator<CalendarNotificationTemplate> orderByComparator) {

		return findByCalendarId(
			calendarId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the calendar notification templates where calendarId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CalendarNotificationTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param calendarId the calendar ID
	 * @param start the lower bound of the range of calendar notification templates
	 * @param end the upper bound of the range of calendar notification templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching calendar notification templates
	 */
	@Override
	public List<CalendarNotificationTemplate> findByCalendarId(
		long calendarId, int start, int end,
		OrderByComparator<CalendarNotificationTemplate> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByCalendarId;
				finderArgs = new Object[] {calendarId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByCalendarId;
			finderArgs = new Object[] {
				calendarId, start, end, orderByComparator
			};
		}

		List<CalendarNotificationTemplate> list = null;

		if (useFinderCache) {
			list = (List<CalendarNotificationTemplate>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CalendarNotificationTemplate calendarNotificationTemplate :
						list) {

					if (calendarId !=
							calendarNotificationTemplate.getCalendarId()) {

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

			sb.append(_SQL_SELECT_CALENDARNOTIFICATIONTEMPLATE_WHERE);

			sb.append(_FINDER_COLUMN_CALENDARID_CALENDARID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CalendarNotificationTemplateModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(calendarId);

				list = (List<CalendarNotificationTemplate>)QueryUtil.list(
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
	 * Returns the first calendar notification template in the ordered set where calendarId = &#63;.
	 *
	 * @param calendarId the calendar ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching calendar notification template
	 * @throws NoSuchNotificationTemplateException if a matching calendar notification template could not be found
	 */
	@Override
	public CalendarNotificationTemplate findByCalendarId_First(
			long calendarId,
			OrderByComparator<CalendarNotificationTemplate> orderByComparator)
		throws NoSuchNotificationTemplateException {

		CalendarNotificationTemplate calendarNotificationTemplate =
			fetchByCalendarId_First(calendarId, orderByComparator);

		if (calendarNotificationTemplate != null) {
			return calendarNotificationTemplate;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("calendarId=");
		sb.append(calendarId);

		sb.append("}");

		throw new NoSuchNotificationTemplateException(sb.toString());
	}

	/**
	 * Returns the first calendar notification template in the ordered set where calendarId = &#63;.
	 *
	 * @param calendarId the calendar ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching calendar notification template, or <code>null</code> if a matching calendar notification template could not be found
	 */
	@Override
	public CalendarNotificationTemplate fetchByCalendarId_First(
		long calendarId,
		OrderByComparator<CalendarNotificationTemplate> orderByComparator) {

		List<CalendarNotificationTemplate> list = findByCalendarId(
			calendarId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last calendar notification template in the ordered set where calendarId = &#63;.
	 *
	 * @param calendarId the calendar ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching calendar notification template
	 * @throws NoSuchNotificationTemplateException if a matching calendar notification template could not be found
	 */
	@Override
	public CalendarNotificationTemplate findByCalendarId_Last(
			long calendarId,
			OrderByComparator<CalendarNotificationTemplate> orderByComparator)
		throws NoSuchNotificationTemplateException {

		CalendarNotificationTemplate calendarNotificationTemplate =
			fetchByCalendarId_Last(calendarId, orderByComparator);

		if (calendarNotificationTemplate != null) {
			return calendarNotificationTemplate;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("calendarId=");
		sb.append(calendarId);

		sb.append("}");

		throw new NoSuchNotificationTemplateException(sb.toString());
	}

	/**
	 * Returns the last calendar notification template in the ordered set where calendarId = &#63;.
	 *
	 * @param calendarId the calendar ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching calendar notification template, or <code>null</code> if a matching calendar notification template could not be found
	 */
	@Override
	public CalendarNotificationTemplate fetchByCalendarId_Last(
		long calendarId,
		OrderByComparator<CalendarNotificationTemplate> orderByComparator) {

		int count = countByCalendarId(calendarId);

		if (count == 0) {
			return null;
		}

		List<CalendarNotificationTemplate> list = findByCalendarId(
			calendarId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the calendar notification templates before and after the current calendar notification template in the ordered set where calendarId = &#63;.
	 *
	 * @param calendarNotificationTemplateId the primary key of the current calendar notification template
	 * @param calendarId the calendar ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next calendar notification template
	 * @throws NoSuchNotificationTemplateException if a calendar notification template with the primary key could not be found
	 */
	@Override
	public CalendarNotificationTemplate[] findByCalendarId_PrevAndNext(
			long calendarNotificationTemplateId, long calendarId,
			OrderByComparator<CalendarNotificationTemplate> orderByComparator)
		throws NoSuchNotificationTemplateException {

		CalendarNotificationTemplate calendarNotificationTemplate =
			findByPrimaryKey(calendarNotificationTemplateId);

		Session session = null;

		try {
			session = openSession();

			CalendarNotificationTemplate[] array =
				new CalendarNotificationTemplateImpl[3];

			array[0] = getByCalendarId_PrevAndNext(
				session, calendarNotificationTemplate, calendarId,
				orderByComparator, true);

			array[1] = calendarNotificationTemplate;

			array[2] = getByCalendarId_PrevAndNext(
				session, calendarNotificationTemplate, calendarId,
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

	protected CalendarNotificationTemplate getByCalendarId_PrevAndNext(
		Session session,
		CalendarNotificationTemplate calendarNotificationTemplate,
		long calendarId,
		OrderByComparator<CalendarNotificationTemplate> orderByComparator,
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

		sb.append(_SQL_SELECT_CALENDARNOTIFICATIONTEMPLATE_WHERE);

		sb.append(_FINDER_COLUMN_CALENDARID_CALENDARID_2);

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
			sb.append(CalendarNotificationTemplateModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(calendarId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						calendarNotificationTemplate)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CalendarNotificationTemplate> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the calendar notification templates where calendarId = &#63; from the database.
	 *
	 * @param calendarId the calendar ID
	 */
	@Override
	public void removeByCalendarId(long calendarId) {
		for (CalendarNotificationTemplate calendarNotificationTemplate :
				findByCalendarId(
					calendarId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(calendarNotificationTemplate);
		}
	}

	/**
	 * Returns the number of calendar notification templates where calendarId = &#63;.
	 *
	 * @param calendarId the calendar ID
	 * @return the number of matching calendar notification templates
	 */
	@Override
	public int countByCalendarId(long calendarId) {
		FinderPath finderPath = _finderPathCountByCalendarId;

		Object[] finderArgs = new Object[] {calendarId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_CALENDARNOTIFICATIONTEMPLATE_WHERE);

			sb.append(_FINDER_COLUMN_CALENDARID_CALENDARID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(calendarId);

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

	private static final String _FINDER_COLUMN_CALENDARID_CALENDARID_2 =
		"calendarNotificationTemplate.calendarId = ?";

	private FinderPath _finderPathFetchByC_NT_NTT;
	private FinderPath _finderPathCountByC_NT_NTT;

	/**
	 * Returns the calendar notification template where calendarId = &#63; and notificationType = &#63; and notificationTemplateType = &#63; or throws a <code>NoSuchNotificationTemplateException</code> if it could not be found.
	 *
	 * @param calendarId the calendar ID
	 * @param notificationType the notification type
	 * @param notificationTemplateType the notification template type
	 * @return the matching calendar notification template
	 * @throws NoSuchNotificationTemplateException if a matching calendar notification template could not be found
	 */
	@Override
	public CalendarNotificationTemplate findByC_NT_NTT(
			long calendarId, String notificationType,
			String notificationTemplateType)
		throws NoSuchNotificationTemplateException {

		CalendarNotificationTemplate calendarNotificationTemplate =
			fetchByC_NT_NTT(
				calendarId, notificationType, notificationTemplateType);

		if (calendarNotificationTemplate == null) {
			StringBundler sb = new StringBundler(8);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("calendarId=");
			sb.append(calendarId);

			sb.append(", notificationType=");
			sb.append(notificationType);

			sb.append(", notificationTemplateType=");
			sb.append(notificationTemplateType);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchNotificationTemplateException(sb.toString());
		}

		return calendarNotificationTemplate;
	}

	/**
	 * Returns the calendar notification template where calendarId = &#63; and notificationType = &#63; and notificationTemplateType = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param calendarId the calendar ID
	 * @param notificationType the notification type
	 * @param notificationTemplateType the notification template type
	 * @return the matching calendar notification template, or <code>null</code> if a matching calendar notification template could not be found
	 */
	@Override
	public CalendarNotificationTemplate fetchByC_NT_NTT(
		long calendarId, String notificationType,
		String notificationTemplateType) {

		return fetchByC_NT_NTT(
			calendarId, notificationType, notificationTemplateType, true);
	}

	/**
	 * Returns the calendar notification template where calendarId = &#63; and notificationType = &#63; and notificationTemplateType = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param calendarId the calendar ID
	 * @param notificationType the notification type
	 * @param notificationTemplateType the notification template type
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching calendar notification template, or <code>null</code> if a matching calendar notification template could not be found
	 */
	@Override
	public CalendarNotificationTemplate fetchByC_NT_NTT(
		long calendarId, String notificationType,
		String notificationTemplateType, boolean useFinderCache) {

		notificationType = Objects.toString(notificationType, "");
		notificationTemplateType = Objects.toString(
			notificationTemplateType, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {
				calendarId, notificationType, notificationTemplateType
			};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByC_NT_NTT, finderArgs, this);
		}

		if (result instanceof CalendarNotificationTemplate) {
			CalendarNotificationTemplate calendarNotificationTemplate =
				(CalendarNotificationTemplate)result;

			if ((calendarId != calendarNotificationTemplate.getCalendarId()) ||
				!Objects.equals(
					notificationType,
					calendarNotificationTemplate.getNotificationType()) ||
				!Objects.equals(
					notificationTemplateType,
					calendarNotificationTemplate.
						getNotificationTemplateType())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_SELECT_CALENDARNOTIFICATIONTEMPLATE_WHERE);

			sb.append(_FINDER_COLUMN_C_NT_NTT_CALENDARID_2);

			boolean bindNotificationType = false;

			if (notificationType.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_NT_NTT_NOTIFICATIONTYPE_3);
			}
			else {
				bindNotificationType = true;

				sb.append(_FINDER_COLUMN_C_NT_NTT_NOTIFICATIONTYPE_2);
			}

			boolean bindNotificationTemplateType = false;

			if (notificationTemplateType.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_NT_NTT_NOTIFICATIONTEMPLATETYPE_3);
			}
			else {
				bindNotificationTemplateType = true;

				sb.append(_FINDER_COLUMN_C_NT_NTT_NOTIFICATIONTEMPLATETYPE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(calendarId);

				if (bindNotificationType) {
					queryPos.add(notificationType);
				}

				if (bindNotificationTemplateType) {
					queryPos.add(notificationTemplateType);
				}

				List<CalendarNotificationTemplate> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByC_NT_NTT, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {
									calendarId, notificationType,
									notificationTemplateType
								};
							}

							_log.warn(
								"CalendarNotificationTemplatePersistenceImpl.fetchByC_NT_NTT(long, String, String, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					CalendarNotificationTemplate calendarNotificationTemplate =
						list.get(0);

					result = calendarNotificationTemplate;

					cacheResult(calendarNotificationTemplate);
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
			return (CalendarNotificationTemplate)result;
		}
	}

	/**
	 * Removes the calendar notification template where calendarId = &#63; and notificationType = &#63; and notificationTemplateType = &#63; from the database.
	 *
	 * @param calendarId the calendar ID
	 * @param notificationType the notification type
	 * @param notificationTemplateType the notification template type
	 * @return the calendar notification template that was removed
	 */
	@Override
	public CalendarNotificationTemplate removeByC_NT_NTT(
			long calendarId, String notificationType,
			String notificationTemplateType)
		throws NoSuchNotificationTemplateException {

		CalendarNotificationTemplate calendarNotificationTemplate =
			findByC_NT_NTT(
				calendarId, notificationType, notificationTemplateType);

		return remove(calendarNotificationTemplate);
	}

	/**
	 * Returns the number of calendar notification templates where calendarId = &#63; and notificationType = &#63; and notificationTemplateType = &#63;.
	 *
	 * @param calendarId the calendar ID
	 * @param notificationType the notification type
	 * @param notificationTemplateType the notification template type
	 * @return the number of matching calendar notification templates
	 */
	@Override
	public int countByC_NT_NTT(
		long calendarId, String notificationType,
		String notificationTemplateType) {

		notificationType = Objects.toString(notificationType, "");
		notificationTemplateType = Objects.toString(
			notificationTemplateType, "");

		FinderPath finderPath = _finderPathCountByC_NT_NTT;

		Object[] finderArgs = new Object[] {
			calendarId, notificationType, notificationTemplateType
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_CALENDARNOTIFICATIONTEMPLATE_WHERE);

			sb.append(_FINDER_COLUMN_C_NT_NTT_CALENDARID_2);

			boolean bindNotificationType = false;

			if (notificationType.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_NT_NTT_NOTIFICATIONTYPE_3);
			}
			else {
				bindNotificationType = true;

				sb.append(_FINDER_COLUMN_C_NT_NTT_NOTIFICATIONTYPE_2);
			}

			boolean bindNotificationTemplateType = false;

			if (notificationTemplateType.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_NT_NTT_NOTIFICATIONTEMPLATETYPE_3);
			}
			else {
				bindNotificationTemplateType = true;

				sb.append(_FINDER_COLUMN_C_NT_NTT_NOTIFICATIONTEMPLATETYPE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(calendarId);

				if (bindNotificationType) {
					queryPos.add(notificationType);
				}

				if (bindNotificationTemplateType) {
					queryPos.add(notificationTemplateType);
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

	private static final String _FINDER_COLUMN_C_NT_NTT_CALENDARID_2 =
		"calendarNotificationTemplate.calendarId = ? AND ";

	private static final String _FINDER_COLUMN_C_NT_NTT_NOTIFICATIONTYPE_2 =
		"calendarNotificationTemplate.notificationType = ? AND ";

	private static final String _FINDER_COLUMN_C_NT_NTT_NOTIFICATIONTYPE_3 =
		"(calendarNotificationTemplate.notificationType IS NULL OR calendarNotificationTemplate.notificationType = '') AND ";

	private static final String
		_FINDER_COLUMN_C_NT_NTT_NOTIFICATIONTEMPLATETYPE_2 =
			"calendarNotificationTemplate.notificationTemplateType = ?";

	private static final String
		_FINDER_COLUMN_C_NT_NTT_NOTIFICATIONTEMPLATETYPE_3 =
			"(calendarNotificationTemplate.notificationTemplateType IS NULL OR calendarNotificationTemplate.notificationTemplateType = '')";

	public CalendarNotificationTemplatePersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(CalendarNotificationTemplate.class);

		setModelImplClass(CalendarNotificationTemplateImpl.class);
		setModelPKClass(long.class);

		setTable(CalendarNotificationTemplateTable.INSTANCE);
	}

	/**
	 * Caches the calendar notification template in the entity cache if it is enabled.
	 *
	 * @param calendarNotificationTemplate the calendar notification template
	 */
	@Override
	public void cacheResult(
		CalendarNotificationTemplate calendarNotificationTemplate) {

		entityCache.putResult(
			CalendarNotificationTemplateImpl.class,
			calendarNotificationTemplate.getPrimaryKey(),
			calendarNotificationTemplate);

		finderCache.putResult(
			_finderPathFetchByUUID_G,
			new Object[] {
				calendarNotificationTemplate.getUuid(),
				calendarNotificationTemplate.getGroupId()
			},
			calendarNotificationTemplate);

		finderCache.putResult(
			_finderPathFetchByC_NT_NTT,
			new Object[] {
				calendarNotificationTemplate.getCalendarId(),
				calendarNotificationTemplate.getNotificationType(),
				calendarNotificationTemplate.getNotificationTemplateType()
			},
			calendarNotificationTemplate);
	}

	/**
	 * Caches the calendar notification templates in the entity cache if it is enabled.
	 *
	 * @param calendarNotificationTemplates the calendar notification templates
	 */
	@Override
	public void cacheResult(
		List<CalendarNotificationTemplate> calendarNotificationTemplates) {

		for (CalendarNotificationTemplate calendarNotificationTemplate :
				calendarNotificationTemplates) {

			if (entityCache.getResult(
					CalendarNotificationTemplateImpl.class,
					calendarNotificationTemplate.getPrimaryKey()) == null) {

				cacheResult(calendarNotificationTemplate);
			}
		}
	}

	/**
	 * Clears the cache for all calendar notification templates.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CalendarNotificationTemplateImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the calendar notification template.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		CalendarNotificationTemplate calendarNotificationTemplate) {

		entityCache.removeResult(
			CalendarNotificationTemplateImpl.class,
			calendarNotificationTemplate.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(CalendarNotificationTemplateModelImpl)calendarNotificationTemplate,
			true);
	}

	@Override
	public void clearCache(
		List<CalendarNotificationTemplate> calendarNotificationTemplates) {

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CalendarNotificationTemplate calendarNotificationTemplate :
				calendarNotificationTemplates) {

			entityCache.removeResult(
				CalendarNotificationTemplateImpl.class,
				calendarNotificationTemplate.getPrimaryKey());

			clearUniqueFindersCache(
				(CalendarNotificationTemplateModelImpl)
					calendarNotificationTemplate,
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
				CalendarNotificationTemplateImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		CalendarNotificationTemplateModelImpl
			calendarNotificationTemplateModelImpl) {

		Object[] args = new Object[] {
			calendarNotificationTemplateModelImpl.getUuid(),
			calendarNotificationTemplateModelImpl.getGroupId()
		};

		finderCache.putResult(
			_finderPathCountByUUID_G, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByUUID_G, args,
			calendarNotificationTemplateModelImpl, false);

		args = new Object[] {
			calendarNotificationTemplateModelImpl.getCalendarId(),
			calendarNotificationTemplateModelImpl.getNotificationType(),
			calendarNotificationTemplateModelImpl.getNotificationTemplateType()
		};

		finderCache.putResult(
			_finderPathCountByC_NT_NTT, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByC_NT_NTT, args,
			calendarNotificationTemplateModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		CalendarNotificationTemplateModelImpl
			calendarNotificationTemplateModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				calendarNotificationTemplateModelImpl.getUuid(),
				calendarNotificationTemplateModelImpl.getGroupId()
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(_finderPathFetchByUUID_G, args);
		}

		if ((calendarNotificationTemplateModelImpl.getColumnBitmask() &
			 _finderPathFetchByUUID_G.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				calendarNotificationTemplateModelImpl.getColumnOriginalValue(
					"uuid_"),
				calendarNotificationTemplateModelImpl.getColumnOriginalValue(
					"groupId")
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(_finderPathFetchByUUID_G, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
				calendarNotificationTemplateModelImpl.getCalendarId(),
				calendarNotificationTemplateModelImpl.getNotificationType(),
				calendarNotificationTemplateModelImpl.
					getNotificationTemplateType()
			};

			finderCache.removeResult(_finderPathCountByC_NT_NTT, args);
			finderCache.removeResult(_finderPathFetchByC_NT_NTT, args);
		}

		if ((calendarNotificationTemplateModelImpl.getColumnBitmask() &
			 _finderPathFetchByC_NT_NTT.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				calendarNotificationTemplateModelImpl.getColumnOriginalValue(
					"calendarId"),
				calendarNotificationTemplateModelImpl.getColumnOriginalValue(
					"notificationType"),
				calendarNotificationTemplateModelImpl.getColumnOriginalValue(
					"notificationTemplateType")
			};

			finderCache.removeResult(_finderPathCountByC_NT_NTT, args);
			finderCache.removeResult(_finderPathFetchByC_NT_NTT, args);
		}
	}

	/**
	 * Creates a new calendar notification template with the primary key. Does not add the calendar notification template to the database.
	 *
	 * @param calendarNotificationTemplateId the primary key for the new calendar notification template
	 * @return the new calendar notification template
	 */
	@Override
	public CalendarNotificationTemplate create(
		long calendarNotificationTemplateId) {

		CalendarNotificationTemplate calendarNotificationTemplate =
			new CalendarNotificationTemplateImpl();

		calendarNotificationTemplate.setNew(true);
		calendarNotificationTemplate.setPrimaryKey(
			calendarNotificationTemplateId);

		String uuid = PortalUUIDUtil.generate();

		calendarNotificationTemplate.setUuid(uuid);

		calendarNotificationTemplate.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return calendarNotificationTemplate;
	}

	/**
	 * Removes the calendar notification template with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param calendarNotificationTemplateId the primary key of the calendar notification template
	 * @return the calendar notification template that was removed
	 * @throws NoSuchNotificationTemplateException if a calendar notification template with the primary key could not be found
	 */
	@Override
	public CalendarNotificationTemplate remove(
			long calendarNotificationTemplateId)
		throws NoSuchNotificationTemplateException {

		return remove((Serializable)calendarNotificationTemplateId);
	}

	/**
	 * Removes the calendar notification template with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the calendar notification template
	 * @return the calendar notification template that was removed
	 * @throws NoSuchNotificationTemplateException if a calendar notification template with the primary key could not be found
	 */
	@Override
	public CalendarNotificationTemplate remove(Serializable primaryKey)
		throws NoSuchNotificationTemplateException {

		Session session = null;

		try {
			session = openSession();

			CalendarNotificationTemplate calendarNotificationTemplate =
				(CalendarNotificationTemplate)session.get(
					CalendarNotificationTemplateImpl.class, primaryKey);

			if (calendarNotificationTemplate == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchNotificationTemplateException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(calendarNotificationTemplate);
		}
		catch (NoSuchNotificationTemplateException noSuchEntityException) {
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
	protected CalendarNotificationTemplate removeImpl(
		CalendarNotificationTemplate calendarNotificationTemplate) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(calendarNotificationTemplate)) {
				calendarNotificationTemplate =
					(CalendarNotificationTemplate)session.get(
						CalendarNotificationTemplateImpl.class,
						calendarNotificationTemplate.getPrimaryKeyObj());
			}

			if (calendarNotificationTemplate != null) {
				session.delete(calendarNotificationTemplate);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (calendarNotificationTemplate != null) {
			clearCache(calendarNotificationTemplate);
		}

		return calendarNotificationTemplate;
	}

	@Override
	public CalendarNotificationTemplate updateImpl(
		CalendarNotificationTemplate calendarNotificationTemplate) {

		boolean isNew = calendarNotificationTemplate.isNew();

		if (!(calendarNotificationTemplate instanceof
				CalendarNotificationTemplateModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(
					calendarNotificationTemplate.getClass())) {

				invocationHandler = ProxyUtil.getInvocationHandler(
					calendarNotificationTemplate);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in calendarNotificationTemplate proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CalendarNotificationTemplate implementation " +
					calendarNotificationTemplate.getClass());
		}

		CalendarNotificationTemplateModelImpl
			calendarNotificationTemplateModelImpl =
				(CalendarNotificationTemplateModelImpl)
					calendarNotificationTemplate;

		if (Validator.isNull(calendarNotificationTemplate.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			calendarNotificationTemplate.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (calendarNotificationTemplate.getCreateDate() == null)) {
			if (serviceContext == null) {
				calendarNotificationTemplate.setCreateDate(now);
			}
			else {
				calendarNotificationTemplate.setCreateDate(
					serviceContext.getCreateDate(now));
			}
		}

		if (!calendarNotificationTemplateModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				calendarNotificationTemplate.setModifiedDate(now);
			}
			else {
				calendarNotificationTemplate.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (calendarNotificationTemplate.isNew()) {
				session.save(calendarNotificationTemplate);

				calendarNotificationTemplate.setNew(false);
			}
			else {
				calendarNotificationTemplate =
					(CalendarNotificationTemplate)session.merge(
						calendarNotificationTemplate);
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
				calendarNotificationTemplateModelImpl.getUuid()
			};

			finderCache.removeResult(_finderPathCountByUuid, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid, args);

			args = new Object[] {
				calendarNotificationTemplateModelImpl.getUuid(),
				calendarNotificationTemplateModelImpl.getCompanyId()
			};

			finderCache.removeResult(_finderPathCountByUuid_C, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid_C, args);

			args = new Object[] {
				calendarNotificationTemplateModelImpl.getCalendarId()
			};

			finderCache.removeResult(_finderPathCountByCalendarId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByCalendarId, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((calendarNotificationTemplateModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					calendarNotificationTemplateModelImpl.
						getColumnOriginalValue("uuid_")
				};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);

				args = new Object[] {
					calendarNotificationTemplateModelImpl.getUuid()
				};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);
			}

			if ((calendarNotificationTemplateModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					calendarNotificationTemplateModelImpl.
						getColumnOriginalValue("uuid_"),
					calendarNotificationTemplateModelImpl.
						getColumnOriginalValue("companyId")
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);

				args = new Object[] {
					calendarNotificationTemplateModelImpl.getUuid(),
					calendarNotificationTemplateModelImpl.getCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);
			}

			if ((calendarNotificationTemplateModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByCalendarId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					calendarNotificationTemplateModelImpl.
						getColumnOriginalValue("calendarId")
				};

				finderCache.removeResult(_finderPathCountByCalendarId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByCalendarId, args);

				args = new Object[] {
					calendarNotificationTemplateModelImpl.getCalendarId()
				};

				finderCache.removeResult(_finderPathCountByCalendarId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByCalendarId, args);
			}
		}

		entityCache.putResult(
			CalendarNotificationTemplateImpl.class,
			calendarNotificationTemplate.getPrimaryKey(),
			calendarNotificationTemplate, false);

		clearUniqueFindersCache(calendarNotificationTemplateModelImpl, false);
		cacheUniqueFindersCache(calendarNotificationTemplateModelImpl);

		calendarNotificationTemplate.resetOriginalValues();

		return calendarNotificationTemplate;
	}

	/**
	 * Returns the calendar notification template with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the calendar notification template
	 * @return the calendar notification template
	 * @throws NoSuchNotificationTemplateException if a calendar notification template with the primary key could not be found
	 */
	@Override
	public CalendarNotificationTemplate findByPrimaryKey(
			Serializable primaryKey)
		throws NoSuchNotificationTemplateException {

		CalendarNotificationTemplate calendarNotificationTemplate =
			fetchByPrimaryKey(primaryKey);

		if (calendarNotificationTemplate == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchNotificationTemplateException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return calendarNotificationTemplate;
	}

	/**
	 * Returns the calendar notification template with the primary key or throws a <code>NoSuchNotificationTemplateException</code> if it could not be found.
	 *
	 * @param calendarNotificationTemplateId the primary key of the calendar notification template
	 * @return the calendar notification template
	 * @throws NoSuchNotificationTemplateException if a calendar notification template with the primary key could not be found
	 */
	@Override
	public CalendarNotificationTemplate findByPrimaryKey(
			long calendarNotificationTemplateId)
		throws NoSuchNotificationTemplateException {

		return findByPrimaryKey((Serializable)calendarNotificationTemplateId);
	}

	/**
	 * Returns the calendar notification template with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param calendarNotificationTemplateId the primary key of the calendar notification template
	 * @return the calendar notification template, or <code>null</code> if a calendar notification template with the primary key could not be found
	 */
	@Override
	public CalendarNotificationTemplate fetchByPrimaryKey(
		long calendarNotificationTemplateId) {

		return fetchByPrimaryKey((Serializable)calendarNotificationTemplateId);
	}

	/**
	 * Returns all the calendar notification templates.
	 *
	 * @return the calendar notification templates
	 */
	@Override
	public List<CalendarNotificationTemplate> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the calendar notification templates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CalendarNotificationTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of calendar notification templates
	 * @param end the upper bound of the range of calendar notification templates (not inclusive)
	 * @return the range of calendar notification templates
	 */
	@Override
	public List<CalendarNotificationTemplate> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the calendar notification templates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CalendarNotificationTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of calendar notification templates
	 * @param end the upper bound of the range of calendar notification templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of calendar notification templates
	 */
	@Override
	public List<CalendarNotificationTemplate> findAll(
		int start, int end,
		OrderByComparator<CalendarNotificationTemplate> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the calendar notification templates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CalendarNotificationTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of calendar notification templates
	 * @param end the upper bound of the range of calendar notification templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of calendar notification templates
	 */
	@Override
	public List<CalendarNotificationTemplate> findAll(
		int start, int end,
		OrderByComparator<CalendarNotificationTemplate> orderByComparator,
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

		List<CalendarNotificationTemplate> list = null;

		if (useFinderCache) {
			list = (List<CalendarNotificationTemplate>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_CALENDARNOTIFICATIONTEMPLATE);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_CALENDARNOTIFICATIONTEMPLATE;

				sql = sql.concat(
					CalendarNotificationTemplateModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CalendarNotificationTemplate>)QueryUtil.list(
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
	 * Removes all the calendar notification templates from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CalendarNotificationTemplate calendarNotificationTemplate :
				findAll()) {

			remove(calendarNotificationTemplate);
		}
	}

	/**
	 * Returns the number of calendar notification templates.
	 *
	 * @return the number of calendar notification templates
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
					_SQL_COUNT_CALENDARNOTIFICATIONTEMPLATE);

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
		return "calendarNotificationTemplateId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_CALENDARNOTIFICATIONTEMPLATE;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CalendarNotificationTemplateModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the calendar notification template persistence.
	 */
	@Activate
	public void activate() {
		_finderPathWithPaginationFindAll = new FinderPath(
			CalendarNotificationTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			CalendarNotificationTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByUuid = new FinderPath(
			CalendarNotificationTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			CalendarNotificationTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] {String.class.getName()},
			CalendarNotificationTemplateModelImpl.getColumnBitmask("uuid_"));

		_finderPathCountByUuid = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid", new String[] {String.class.getName()});

		_finderPathFetchByUUID_G = new FinderPath(
			CalendarNotificationTemplateImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			CalendarNotificationTemplateModelImpl.getColumnBitmask("uuid_") |
			CalendarNotificationTemplateModelImpl.getColumnBitmask("groupId"));

		_finderPathCountByUUID_G = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			CalendarNotificationTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			CalendarNotificationTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			CalendarNotificationTemplateModelImpl.getColumnBitmask("uuid_") |
			CalendarNotificationTemplateModelImpl.getColumnBitmask(
				"companyId"));

		_finderPathCountByUuid_C = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByCalendarId = new FinderPath(
			CalendarNotificationTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCalendarId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByCalendarId = new FinderPath(
			CalendarNotificationTemplateImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCalendarId",
			new String[] {Long.class.getName()},
			CalendarNotificationTemplateModelImpl.getColumnBitmask(
				"calendarId"));

		_finderPathCountByCalendarId = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCalendarId", new String[] {Long.class.getName()});

		_finderPathFetchByC_NT_NTT = new FinderPath(
			CalendarNotificationTemplateImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByC_NT_NTT",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			},
			CalendarNotificationTemplateModelImpl.getColumnBitmask(
				"calendarId") |
			CalendarNotificationTemplateModelImpl.getColumnBitmask(
				"notificationType") |
			CalendarNotificationTemplateModelImpl.getColumnBitmask(
				"notificationTemplateType"));

		_finderPathCountByC_NT_NTT = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByC_NT_NTT",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			});
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(
			CalendarNotificationTemplateImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	@Reference(
		target = CalendarPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = CalendarPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = CalendarPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_CALENDARNOTIFICATIONTEMPLATE =
		"SELECT calendarNotificationTemplate FROM CalendarNotificationTemplate calendarNotificationTemplate";

	private static final String _SQL_SELECT_CALENDARNOTIFICATIONTEMPLATE_WHERE =
		"SELECT calendarNotificationTemplate FROM CalendarNotificationTemplate calendarNotificationTemplate WHERE ";

	private static final String _SQL_COUNT_CALENDARNOTIFICATIONTEMPLATE =
		"SELECT COUNT(calendarNotificationTemplate) FROM CalendarNotificationTemplate calendarNotificationTemplate";

	private static final String _SQL_COUNT_CALENDARNOTIFICATIONTEMPLATE_WHERE =
		"SELECT COUNT(calendarNotificationTemplate) FROM CalendarNotificationTemplate calendarNotificationTemplate WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"calendarNotificationTemplate.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CalendarNotificationTemplate exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CalendarNotificationTemplate exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CalendarNotificationTemplatePersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	static {
		try {
			Class.forName(CalendarPersistenceConstants.class.getName());
		}
		catch (ClassNotFoundException classNotFoundException) {
			throw new ExceptionInInitializerError(classNotFoundException);
		}
	}

}