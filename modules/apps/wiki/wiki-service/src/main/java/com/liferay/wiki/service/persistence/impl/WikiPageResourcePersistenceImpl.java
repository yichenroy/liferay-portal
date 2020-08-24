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

package com.liferay.wiki.service.persistence.impl;

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
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.wiki.exception.NoSuchPageResourceException;
import com.liferay.wiki.model.WikiPageResource;
import com.liferay.wiki.model.WikiPageResourceTable;
import com.liferay.wiki.model.impl.WikiPageResourceImpl;
import com.liferay.wiki.model.impl.WikiPageResourceModelImpl;
import com.liferay.wiki.service.persistence.WikiPageResourcePersistence;
import com.liferay.wiki.service.persistence.impl.constants.WikiPersistenceConstants;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

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
 * The persistence implementation for the wiki page resource service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = WikiPageResourcePersistence.class)
public class WikiPageResourcePersistenceImpl
	extends BasePersistenceImpl<WikiPageResource>
	implements WikiPageResourcePersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>WikiPageResourceUtil</code> to access the wiki page resource persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		WikiPageResourceImpl.class.getName();

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
	 * Returns all the wiki page resources where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching wiki page resources
	 */
	@Override
	public List<WikiPageResource> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the wiki page resources where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageResourceModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of wiki page resources
	 * @param end the upper bound of the range of wiki page resources (not inclusive)
	 * @return the range of matching wiki page resources
	 */
	@Override
	public List<WikiPageResource> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the wiki page resources where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageResourceModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of wiki page resources
	 * @param end the upper bound of the range of wiki page resources (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching wiki page resources
	 */
	@Override
	public List<WikiPageResource> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<WikiPageResource> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the wiki page resources where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageResourceModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of wiki page resources
	 * @param end the upper bound of the range of wiki page resources (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching wiki page resources
	 */
	@Override
	public List<WikiPageResource> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<WikiPageResource> orderByComparator,
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

		List<WikiPageResource> list = null;

		if (useFinderCache) {
			list = (List<WikiPageResource>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (WikiPageResource wikiPageResource : list) {
					if (!uuid.equals(wikiPageResource.getUuid())) {
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

			sb.append(_SQL_SELECT_WIKIPAGERESOURCE_WHERE);

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
				sb.append(WikiPageResourceModelImpl.ORDER_BY_JPQL);
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

				list = (List<WikiPageResource>)QueryUtil.list(
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
	 * Returns the first wiki page resource in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page resource
	 * @throws NoSuchPageResourceException if a matching wiki page resource could not be found
	 */
	@Override
	public WikiPageResource findByUuid_First(
			String uuid, OrderByComparator<WikiPageResource> orderByComparator)
		throws NoSuchPageResourceException {

		WikiPageResource wikiPageResource = fetchByUuid_First(
			uuid, orderByComparator);

		if (wikiPageResource != null) {
			return wikiPageResource;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchPageResourceException(sb.toString());
	}

	/**
	 * Returns the first wiki page resource in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page resource, or <code>null</code> if a matching wiki page resource could not be found
	 */
	@Override
	public WikiPageResource fetchByUuid_First(
		String uuid, OrderByComparator<WikiPageResource> orderByComparator) {

		List<WikiPageResource> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last wiki page resource in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page resource
	 * @throws NoSuchPageResourceException if a matching wiki page resource could not be found
	 */
	@Override
	public WikiPageResource findByUuid_Last(
			String uuid, OrderByComparator<WikiPageResource> orderByComparator)
		throws NoSuchPageResourceException {

		WikiPageResource wikiPageResource = fetchByUuid_Last(
			uuid, orderByComparator);

		if (wikiPageResource != null) {
			return wikiPageResource;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchPageResourceException(sb.toString());
	}

	/**
	 * Returns the last wiki page resource in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page resource, or <code>null</code> if a matching wiki page resource could not be found
	 */
	@Override
	public WikiPageResource fetchByUuid_Last(
		String uuid, OrderByComparator<WikiPageResource> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<WikiPageResource> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the wiki page resources before and after the current wiki page resource in the ordered set where uuid = &#63;.
	 *
	 * @param resourcePrimKey the primary key of the current wiki page resource
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next wiki page resource
	 * @throws NoSuchPageResourceException if a wiki page resource with the primary key could not be found
	 */
	@Override
	public WikiPageResource[] findByUuid_PrevAndNext(
			long resourcePrimKey, String uuid,
			OrderByComparator<WikiPageResource> orderByComparator)
		throws NoSuchPageResourceException {

		uuid = Objects.toString(uuid, "");

		WikiPageResource wikiPageResource = findByPrimaryKey(resourcePrimKey);

		Session session = null;

		try {
			session = openSession();

			WikiPageResource[] array = new WikiPageResourceImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, wikiPageResource, uuid, orderByComparator, true);

			array[1] = wikiPageResource;

			array[2] = getByUuid_PrevAndNext(
				session, wikiPageResource, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected WikiPageResource getByUuid_PrevAndNext(
		Session session, WikiPageResource wikiPageResource, String uuid,
		OrderByComparator<WikiPageResource> orderByComparator,
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

		sb.append(_SQL_SELECT_WIKIPAGERESOURCE_WHERE);

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
			sb.append(WikiPageResourceModelImpl.ORDER_BY_JPQL);
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
						wikiPageResource)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<WikiPageResource> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the wiki page resources where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (WikiPageResource wikiPageResource :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(wikiPageResource);
		}
	}

	/**
	 * Returns the number of wiki page resources where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching wiki page resources
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_WIKIPAGERESOURCE_WHERE);

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
		"wikiPageResource.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(wikiPageResource.uuid IS NULL OR wikiPageResource.uuid = '')";

	private FinderPath _finderPathFetchByUUID_G;
	private FinderPath _finderPathCountByUUID_G;

	/**
	 * Returns the wiki page resource where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchPageResourceException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching wiki page resource
	 * @throws NoSuchPageResourceException if a matching wiki page resource could not be found
	 */
	@Override
	public WikiPageResource findByUUID_G(String uuid, long groupId)
		throws NoSuchPageResourceException {

		WikiPageResource wikiPageResource = fetchByUUID_G(uuid, groupId);

		if (wikiPageResource == null) {
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

			throw new NoSuchPageResourceException(sb.toString());
		}

		return wikiPageResource;
	}

	/**
	 * Returns the wiki page resource where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching wiki page resource, or <code>null</code> if a matching wiki page resource could not be found
	 */
	@Override
	public WikiPageResource fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the wiki page resource where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching wiki page resource, or <code>null</code> if a matching wiki page resource could not be found
	 */
	@Override
	public WikiPageResource fetchByUUID_G(
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

		if (result instanceof WikiPageResource) {
			WikiPageResource wikiPageResource = (WikiPageResource)result;

			if (!Objects.equals(uuid, wikiPageResource.getUuid()) ||
				(groupId != wikiPageResource.getGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_WIKIPAGERESOURCE_WHERE);

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

				List<WikiPageResource> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByUUID_G, finderArgs, list);
					}
				}
				else {
					WikiPageResource wikiPageResource = list.get(0);

					result = wikiPageResource;

					cacheResult(wikiPageResource);
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
			return (WikiPageResource)result;
		}
	}

	/**
	 * Removes the wiki page resource where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the wiki page resource that was removed
	 */
	@Override
	public WikiPageResource removeByUUID_G(String uuid, long groupId)
		throws NoSuchPageResourceException {

		WikiPageResource wikiPageResource = findByUUID_G(uuid, groupId);

		return remove(wikiPageResource);
	}

	/**
	 * Returns the number of wiki page resources where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching wiki page resources
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUUID_G;

		Object[] finderArgs = new Object[] {uuid, groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_WIKIPAGERESOURCE_WHERE);

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
		"wikiPageResource.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(wikiPageResource.uuid IS NULL OR wikiPageResource.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"wikiPageResource.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the wiki page resources where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching wiki page resources
	 */
	@Override
	public List<WikiPageResource> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the wiki page resources where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageResourceModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of wiki page resources
	 * @param end the upper bound of the range of wiki page resources (not inclusive)
	 * @return the range of matching wiki page resources
	 */
	@Override
	public List<WikiPageResource> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the wiki page resources where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageResourceModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of wiki page resources
	 * @param end the upper bound of the range of wiki page resources (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching wiki page resources
	 */
	@Override
	public List<WikiPageResource> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<WikiPageResource> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the wiki page resources where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageResourceModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of wiki page resources
	 * @param end the upper bound of the range of wiki page resources (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching wiki page resources
	 */
	@Override
	public List<WikiPageResource> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<WikiPageResource> orderByComparator,
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

		List<WikiPageResource> list = null;

		if (useFinderCache) {
			list = (List<WikiPageResource>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (WikiPageResource wikiPageResource : list) {
					if (!uuid.equals(wikiPageResource.getUuid()) ||
						(companyId != wikiPageResource.getCompanyId())) {

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

			sb.append(_SQL_SELECT_WIKIPAGERESOURCE_WHERE);

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
				sb.append(WikiPageResourceModelImpl.ORDER_BY_JPQL);
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

				list = (List<WikiPageResource>)QueryUtil.list(
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
	 * Returns the first wiki page resource in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page resource
	 * @throws NoSuchPageResourceException if a matching wiki page resource could not be found
	 */
	@Override
	public WikiPageResource findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<WikiPageResource> orderByComparator)
		throws NoSuchPageResourceException {

		WikiPageResource wikiPageResource = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (wikiPageResource != null) {
			return wikiPageResource;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchPageResourceException(sb.toString());
	}

	/**
	 * Returns the first wiki page resource in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching wiki page resource, or <code>null</code> if a matching wiki page resource could not be found
	 */
	@Override
	public WikiPageResource fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<WikiPageResource> orderByComparator) {

		List<WikiPageResource> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last wiki page resource in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page resource
	 * @throws NoSuchPageResourceException if a matching wiki page resource could not be found
	 */
	@Override
	public WikiPageResource findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<WikiPageResource> orderByComparator)
		throws NoSuchPageResourceException {

		WikiPageResource wikiPageResource = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (wikiPageResource != null) {
			return wikiPageResource;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchPageResourceException(sb.toString());
	}

	/**
	 * Returns the last wiki page resource in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching wiki page resource, or <code>null</code> if a matching wiki page resource could not be found
	 */
	@Override
	public WikiPageResource fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<WikiPageResource> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<WikiPageResource> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the wiki page resources before and after the current wiki page resource in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param resourcePrimKey the primary key of the current wiki page resource
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next wiki page resource
	 * @throws NoSuchPageResourceException if a wiki page resource with the primary key could not be found
	 */
	@Override
	public WikiPageResource[] findByUuid_C_PrevAndNext(
			long resourcePrimKey, String uuid, long companyId,
			OrderByComparator<WikiPageResource> orderByComparator)
		throws NoSuchPageResourceException {

		uuid = Objects.toString(uuid, "");

		WikiPageResource wikiPageResource = findByPrimaryKey(resourcePrimKey);

		Session session = null;

		try {
			session = openSession();

			WikiPageResource[] array = new WikiPageResourceImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, wikiPageResource, uuid, companyId, orderByComparator,
				true);

			array[1] = wikiPageResource;

			array[2] = getByUuid_C_PrevAndNext(
				session, wikiPageResource, uuid, companyId, orderByComparator,
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

	protected WikiPageResource getByUuid_C_PrevAndNext(
		Session session, WikiPageResource wikiPageResource, String uuid,
		long companyId, OrderByComparator<WikiPageResource> orderByComparator,
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

		sb.append(_SQL_SELECT_WIKIPAGERESOURCE_WHERE);

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
			sb.append(WikiPageResourceModelImpl.ORDER_BY_JPQL);
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
						wikiPageResource)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<WikiPageResource> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the wiki page resources where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (WikiPageResource wikiPageResource :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(wikiPageResource);
		}
	}

	/**
	 * Returns the number of wiki page resources where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching wiki page resources
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_WIKIPAGERESOURCE_WHERE);

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
		"wikiPageResource.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(wikiPageResource.uuid IS NULL OR wikiPageResource.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"wikiPageResource.companyId = ?";

	private FinderPath _finderPathFetchByN_T;
	private FinderPath _finderPathCountByN_T;

	/**
	 * Returns the wiki page resource where nodeId = &#63; and title = &#63; or throws a <code>NoSuchPageResourceException</code> if it could not be found.
	 *
	 * @param nodeId the node ID
	 * @param title the title
	 * @return the matching wiki page resource
	 * @throws NoSuchPageResourceException if a matching wiki page resource could not be found
	 */
	@Override
	public WikiPageResource findByN_T(long nodeId, String title)
		throws NoSuchPageResourceException {

		WikiPageResource wikiPageResource = fetchByN_T(nodeId, title);

		if (wikiPageResource == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("nodeId=");
			sb.append(nodeId);

			sb.append(", title=");
			sb.append(title);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchPageResourceException(sb.toString());
		}

		return wikiPageResource;
	}

	/**
	 * Returns the wiki page resource where nodeId = &#63; and title = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param nodeId the node ID
	 * @param title the title
	 * @return the matching wiki page resource, or <code>null</code> if a matching wiki page resource could not be found
	 */
	@Override
	public WikiPageResource fetchByN_T(long nodeId, String title) {
		return fetchByN_T(nodeId, title, true);
	}

	/**
	 * Returns the wiki page resource where nodeId = &#63; and title = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param nodeId the node ID
	 * @param title the title
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching wiki page resource, or <code>null</code> if a matching wiki page resource could not be found
	 */
	@Override
	public WikiPageResource fetchByN_T(
		long nodeId, String title, boolean useFinderCache) {

		title = Objects.toString(title, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {nodeId, title};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByN_T, finderArgs, this);
		}

		if (result instanceof WikiPageResource) {
			WikiPageResource wikiPageResource = (WikiPageResource)result;

			if ((nodeId != wikiPageResource.getNodeId()) ||
				!Objects.equals(title, wikiPageResource.getTitle())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_WIKIPAGERESOURCE_WHERE);

			sb.append(_FINDER_COLUMN_N_T_NODEID_2);

			boolean bindTitle = false;

			if (title.isEmpty()) {
				sb.append(_FINDER_COLUMN_N_T_TITLE_3);
			}
			else {
				bindTitle = true;

				sb.append(_FINDER_COLUMN_N_T_TITLE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(nodeId);

				if (bindTitle) {
					queryPos.add(title);
				}

				List<WikiPageResource> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByN_T, finderArgs, list);
					}
				}
				else {
					WikiPageResource wikiPageResource = list.get(0);

					result = wikiPageResource;

					cacheResult(wikiPageResource);
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
			return (WikiPageResource)result;
		}
	}

	/**
	 * Removes the wiki page resource where nodeId = &#63; and title = &#63; from the database.
	 *
	 * @param nodeId the node ID
	 * @param title the title
	 * @return the wiki page resource that was removed
	 */
	@Override
	public WikiPageResource removeByN_T(long nodeId, String title)
		throws NoSuchPageResourceException {

		WikiPageResource wikiPageResource = findByN_T(nodeId, title);

		return remove(wikiPageResource);
	}

	/**
	 * Returns the number of wiki page resources where nodeId = &#63; and title = &#63;.
	 *
	 * @param nodeId the node ID
	 * @param title the title
	 * @return the number of matching wiki page resources
	 */
	@Override
	public int countByN_T(long nodeId, String title) {
		title = Objects.toString(title, "");

		FinderPath finderPath = _finderPathCountByN_T;

		Object[] finderArgs = new Object[] {nodeId, title};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_WIKIPAGERESOURCE_WHERE);

			sb.append(_FINDER_COLUMN_N_T_NODEID_2);

			boolean bindTitle = false;

			if (title.isEmpty()) {
				sb.append(_FINDER_COLUMN_N_T_TITLE_3);
			}
			else {
				bindTitle = true;

				sb.append(_FINDER_COLUMN_N_T_TITLE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(nodeId);

				if (bindTitle) {
					queryPos.add(title);
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

	private static final String _FINDER_COLUMN_N_T_NODEID_2 =
		"wikiPageResource.nodeId = ? AND ";

	private static final String _FINDER_COLUMN_N_T_TITLE_2 =
		"wikiPageResource.title = ?";

	private static final String _FINDER_COLUMN_N_T_TITLE_3 =
		"(wikiPageResource.title IS NULL OR wikiPageResource.title = '')";

	public WikiPageResourcePersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(WikiPageResource.class);

		setModelImplClass(WikiPageResourceImpl.class);
		setModelPKClass(long.class);

		setTable(WikiPageResourceTable.INSTANCE);
	}

	/**
	 * Caches the wiki page resource in the entity cache if it is enabled.
	 *
	 * @param wikiPageResource the wiki page resource
	 */
	@Override
	public void cacheResult(WikiPageResource wikiPageResource) {
		entityCache.putResult(
			WikiPageResourceImpl.class, wikiPageResource.getPrimaryKey(),
			wikiPageResource);

		finderCache.putResult(
			_finderPathFetchByUUID_G,
			new Object[] {
				wikiPageResource.getUuid(), wikiPageResource.getGroupId()
			},
			wikiPageResource);

		finderCache.putResult(
			_finderPathFetchByN_T,
			new Object[] {
				wikiPageResource.getNodeId(), wikiPageResource.getTitle()
			},
			wikiPageResource);
	}

	/**
	 * Caches the wiki page resources in the entity cache if it is enabled.
	 *
	 * @param wikiPageResources the wiki page resources
	 */
	@Override
	public void cacheResult(List<WikiPageResource> wikiPageResources) {
		for (WikiPageResource wikiPageResource : wikiPageResources) {
			if (entityCache.getResult(
					WikiPageResourceImpl.class,
					wikiPageResource.getPrimaryKey()) == null) {

				cacheResult(wikiPageResource);
			}
		}
	}

	/**
	 * Clears the cache for all wiki page resources.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(WikiPageResourceImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the wiki page resource.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(WikiPageResource wikiPageResource) {
		entityCache.removeResult(
			WikiPageResourceImpl.class, wikiPageResource.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(WikiPageResourceModelImpl)wikiPageResource, true);
	}

	@Override
	public void clearCache(List<WikiPageResource> wikiPageResources) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (WikiPageResource wikiPageResource : wikiPageResources) {
			entityCache.removeResult(
				WikiPageResourceImpl.class, wikiPageResource.getPrimaryKey());

			clearUniqueFindersCache(
				(WikiPageResourceModelImpl)wikiPageResource, true);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(WikiPageResourceImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		WikiPageResourceModelImpl wikiPageResourceModelImpl) {

		Object[] args = new Object[] {
			wikiPageResourceModelImpl.getUuid(),
			wikiPageResourceModelImpl.getGroupId()
		};

		finderCache.putResult(
			_finderPathCountByUUID_G, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByUUID_G, args, wikiPageResourceModelImpl, false);

		args = new Object[] {
			wikiPageResourceModelImpl.getNodeId(),
			wikiPageResourceModelImpl.getTitle()
		};

		finderCache.putResult(
			_finderPathCountByN_T, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByN_T, args, wikiPageResourceModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		WikiPageResourceModelImpl wikiPageResourceModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				wikiPageResourceModelImpl.getUuid(),
				wikiPageResourceModelImpl.getGroupId()
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(_finderPathFetchByUUID_G, args);
		}

		if ((wikiPageResourceModelImpl.getColumnBitmask() &
			 _finderPathFetchByUUID_G.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				wikiPageResourceModelImpl.getColumnOriginalValue("uuid_"),
				wikiPageResourceModelImpl.getColumnOriginalValue("groupId")
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(_finderPathFetchByUUID_G, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
				wikiPageResourceModelImpl.getNodeId(),
				wikiPageResourceModelImpl.getTitle()
			};

			finderCache.removeResult(_finderPathCountByN_T, args);
			finderCache.removeResult(_finderPathFetchByN_T, args);
		}

		if ((wikiPageResourceModelImpl.getColumnBitmask() &
			 _finderPathFetchByN_T.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				wikiPageResourceModelImpl.getColumnOriginalValue("nodeId"),
				wikiPageResourceModelImpl.getColumnOriginalValue("title")
			};

			finderCache.removeResult(_finderPathCountByN_T, args);
			finderCache.removeResult(_finderPathFetchByN_T, args);
		}
	}

	/**
	 * Creates a new wiki page resource with the primary key. Does not add the wiki page resource to the database.
	 *
	 * @param resourcePrimKey the primary key for the new wiki page resource
	 * @return the new wiki page resource
	 */
	@Override
	public WikiPageResource create(long resourcePrimKey) {
		WikiPageResource wikiPageResource = new WikiPageResourceImpl();

		wikiPageResource.setNew(true);
		wikiPageResource.setPrimaryKey(resourcePrimKey);

		String uuid = PortalUUIDUtil.generate();

		wikiPageResource.setUuid(uuid);

		wikiPageResource.setCompanyId(CompanyThreadLocal.getCompanyId());

		return wikiPageResource;
	}

	/**
	 * Removes the wiki page resource with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param resourcePrimKey the primary key of the wiki page resource
	 * @return the wiki page resource that was removed
	 * @throws NoSuchPageResourceException if a wiki page resource with the primary key could not be found
	 */
	@Override
	public WikiPageResource remove(long resourcePrimKey)
		throws NoSuchPageResourceException {

		return remove((Serializable)resourcePrimKey);
	}

	/**
	 * Removes the wiki page resource with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the wiki page resource
	 * @return the wiki page resource that was removed
	 * @throws NoSuchPageResourceException if a wiki page resource with the primary key could not be found
	 */
	@Override
	public WikiPageResource remove(Serializable primaryKey)
		throws NoSuchPageResourceException {

		Session session = null;

		try {
			session = openSession();

			WikiPageResource wikiPageResource = (WikiPageResource)session.get(
				WikiPageResourceImpl.class, primaryKey);

			if (wikiPageResource == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchPageResourceException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(wikiPageResource);
		}
		catch (NoSuchPageResourceException noSuchEntityException) {
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
	protected WikiPageResource removeImpl(WikiPageResource wikiPageResource) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(wikiPageResource)) {
				wikiPageResource = (WikiPageResource)session.get(
					WikiPageResourceImpl.class,
					wikiPageResource.getPrimaryKeyObj());
			}

			if (wikiPageResource != null) {
				session.delete(wikiPageResource);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (wikiPageResource != null) {
			clearCache(wikiPageResource);
		}

		return wikiPageResource;
	}

	@Override
	public WikiPageResource updateImpl(WikiPageResource wikiPageResource) {
		boolean isNew = wikiPageResource.isNew();

		if (!(wikiPageResource instanceof WikiPageResourceModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(wikiPageResource.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					wikiPageResource);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in wikiPageResource proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom WikiPageResource implementation " +
					wikiPageResource.getClass());
		}

		WikiPageResourceModelImpl wikiPageResourceModelImpl =
			(WikiPageResourceModelImpl)wikiPageResource;

		if (Validator.isNull(wikiPageResource.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			wikiPageResource.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			if (wikiPageResource.isNew()) {
				session.save(wikiPageResource);

				wikiPageResource.setNew(false);
			}
			else {
				wikiPageResource = (WikiPageResource)session.merge(
					wikiPageResource);
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
			Object[] args = new Object[] {wikiPageResourceModelImpl.getUuid()};

			finderCache.removeResult(_finderPathCountByUuid, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid, args);

			args = new Object[] {
				wikiPageResourceModelImpl.getUuid(),
				wikiPageResourceModelImpl.getCompanyId()
			};

			finderCache.removeResult(_finderPathCountByUuid_C, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid_C, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((wikiPageResourceModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					wikiPageResourceModelImpl.getColumnOriginalValue("uuid_")
				};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);

				args = new Object[] {wikiPageResourceModelImpl.getUuid()};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);
			}

			if ((wikiPageResourceModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					wikiPageResourceModelImpl.getColumnOriginalValue("uuid_"),
					wikiPageResourceModelImpl.getColumnOriginalValue(
						"companyId")
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);

				args = new Object[] {
					wikiPageResourceModelImpl.getUuid(),
					wikiPageResourceModelImpl.getCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);
			}
		}

		entityCache.putResult(
			WikiPageResourceImpl.class, wikiPageResource.getPrimaryKey(),
			wikiPageResource, false);

		clearUniqueFindersCache(wikiPageResourceModelImpl, false);
		cacheUniqueFindersCache(wikiPageResourceModelImpl);

		wikiPageResource.resetOriginalValues();

		return wikiPageResource;
	}

	/**
	 * Returns the wiki page resource with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the wiki page resource
	 * @return the wiki page resource
	 * @throws NoSuchPageResourceException if a wiki page resource with the primary key could not be found
	 */
	@Override
	public WikiPageResource findByPrimaryKey(Serializable primaryKey)
		throws NoSuchPageResourceException {

		WikiPageResource wikiPageResource = fetchByPrimaryKey(primaryKey);

		if (wikiPageResource == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchPageResourceException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return wikiPageResource;
	}

	/**
	 * Returns the wiki page resource with the primary key or throws a <code>NoSuchPageResourceException</code> if it could not be found.
	 *
	 * @param resourcePrimKey the primary key of the wiki page resource
	 * @return the wiki page resource
	 * @throws NoSuchPageResourceException if a wiki page resource with the primary key could not be found
	 */
	@Override
	public WikiPageResource findByPrimaryKey(long resourcePrimKey)
		throws NoSuchPageResourceException {

		return findByPrimaryKey((Serializable)resourcePrimKey);
	}

	/**
	 * Returns the wiki page resource with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param resourcePrimKey the primary key of the wiki page resource
	 * @return the wiki page resource, or <code>null</code> if a wiki page resource with the primary key could not be found
	 */
	@Override
	public WikiPageResource fetchByPrimaryKey(long resourcePrimKey) {
		return fetchByPrimaryKey((Serializable)resourcePrimKey);
	}

	/**
	 * Returns all the wiki page resources.
	 *
	 * @return the wiki page resources
	 */
	@Override
	public List<WikiPageResource> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the wiki page resources.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageResourceModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of wiki page resources
	 * @param end the upper bound of the range of wiki page resources (not inclusive)
	 * @return the range of wiki page resources
	 */
	@Override
	public List<WikiPageResource> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the wiki page resources.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageResourceModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of wiki page resources
	 * @param end the upper bound of the range of wiki page resources (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of wiki page resources
	 */
	@Override
	public List<WikiPageResource> findAll(
		int start, int end,
		OrderByComparator<WikiPageResource> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the wiki page resources.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>WikiPageResourceModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of wiki page resources
	 * @param end the upper bound of the range of wiki page resources (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of wiki page resources
	 */
	@Override
	public List<WikiPageResource> findAll(
		int start, int end,
		OrderByComparator<WikiPageResource> orderByComparator,
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

		List<WikiPageResource> list = null;

		if (useFinderCache) {
			list = (List<WikiPageResource>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_WIKIPAGERESOURCE);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_WIKIPAGERESOURCE;

				sql = sql.concat(WikiPageResourceModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<WikiPageResource>)QueryUtil.list(
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
	 * Removes all the wiki page resources from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (WikiPageResource wikiPageResource : findAll()) {
			remove(wikiPageResource);
		}
	}

	/**
	 * Returns the number of wiki page resources.
	 *
	 * @return the number of wiki page resources
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_WIKIPAGERESOURCE);

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
		return "resourcePrimKey";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_WIKIPAGERESOURCE;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return WikiPageResourceModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the wiki page resource persistence.
	 */
	@Activate
	public void activate() {
		_finderPathWithPaginationFindAll = new FinderPath(
			WikiPageResourceImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			WikiPageResourceImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByUuid = new FinderPath(
			WikiPageResourceImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			WikiPageResourceImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] {String.class.getName()},
			WikiPageResourceModelImpl.getColumnBitmask("uuid_"));

		_finderPathCountByUuid = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid", new String[] {String.class.getName()});

		_finderPathFetchByUUID_G = new FinderPath(
			WikiPageResourceImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			WikiPageResourceModelImpl.getColumnBitmask("uuid_") |
			WikiPageResourceModelImpl.getColumnBitmask("groupId"));

		_finderPathCountByUUID_G = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			WikiPageResourceImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			WikiPageResourceImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			WikiPageResourceModelImpl.getColumnBitmask("uuid_") |
			WikiPageResourceModelImpl.getColumnBitmask("companyId"));

		_finderPathCountByUuid_C = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathFetchByN_T = new FinderPath(
			WikiPageResourceImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByN_T",
			new String[] {Long.class.getName(), String.class.getName()},
			WikiPageResourceModelImpl.getColumnBitmask("nodeId") |
			WikiPageResourceModelImpl.getColumnBitmask("title"));

		_finderPathCountByN_T = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByN_T",
			new String[] {Long.class.getName(), String.class.getName()});
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(WikiPageResourceImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	@Reference(
		target = WikiPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = WikiPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = WikiPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_WIKIPAGERESOURCE =
		"SELECT wikiPageResource FROM WikiPageResource wikiPageResource";

	private static final String _SQL_SELECT_WIKIPAGERESOURCE_WHERE =
		"SELECT wikiPageResource FROM WikiPageResource wikiPageResource WHERE ";

	private static final String _SQL_COUNT_WIKIPAGERESOURCE =
		"SELECT COUNT(wikiPageResource) FROM WikiPageResource wikiPageResource";

	private static final String _SQL_COUNT_WIKIPAGERESOURCE_WHERE =
		"SELECT COUNT(wikiPageResource) FROM WikiPageResource wikiPageResource WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "wikiPageResource.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No WikiPageResource exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No WikiPageResource exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		WikiPageResourcePersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	static {
		try {
			Class.forName(WikiPersistenceConstants.class.getName());
		}
		catch (ClassNotFoundException classNotFoundException) {
			throw new ExceptionInInitializerError(classNotFoundException);
		}
	}

}