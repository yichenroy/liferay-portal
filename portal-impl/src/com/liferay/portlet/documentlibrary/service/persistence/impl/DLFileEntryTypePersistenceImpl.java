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

package com.liferay.portlet.documentlibrary.service.persistence.impl;

import com.liferay.document.library.kernel.exception.NoSuchFileEntryTypeException;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.model.DLFileEntryTypeTable;
import com.liferay.document.library.kernel.service.persistence.DLFileEntryTypePersistence;
import com.liferay.document.library.kernel.service.persistence.DLFolderPersistence;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.change.tracking.CTColumnResolutionType;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.change.tracking.helper.CTPersistenceHelperUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.service.persistence.impl.TableMapper;
import com.liferay.portal.kernel.service.persistence.impl.TableMapperFactory;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portlet.documentlibrary.model.impl.DLFileEntryTypeImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFileEntryTypeModelImpl;

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

/**
 * The persistence implementation for the document library file entry type service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class DLFileEntryTypePersistenceImpl
	extends BasePersistenceImpl<DLFileEntryType>
	implements DLFileEntryTypePersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>DLFileEntryTypeUtil</code> to access the document library file entry type persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		DLFileEntryTypeImpl.class.getName();

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
	 * Returns all the document library file entry types where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching document library file entry types
	 */
	@Override
	public List<DLFileEntryType> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entry types where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryTypeModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of document library file entry types
	 * @param end the upper bound of the range of document library file entry types (not inclusive)
	 * @return the range of matching document library file entry types
	 */
	@Override
	public List<DLFileEntryType> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entry types where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryTypeModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of document library file entry types
	 * @param end the upper bound of the range of document library file entry types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entry types
	 */
	@Override
	public List<DLFileEntryType> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<DLFileEntryType> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file entry types where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryTypeModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of document library file entry types
	 * @param end the upper bound of the range of document library file entry types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching document library file entry types
	 */
	@Override
	public List<DLFileEntryType> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<DLFileEntryType> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			DLFileEntryType.class);

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

		List<DLFileEntryType> list = null;

		if (useFinderCache && productionMode) {
			list = (List<DLFileEntryType>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DLFileEntryType dlFileEntryType : list) {
					if (!uuid.equals(dlFileEntryType.getUuid())) {
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

			sb.append(_SQL_SELECT_DLFILEENTRYTYPE_WHERE);

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
				sb.append(DLFileEntryTypeModelImpl.ORDER_BY_JPQL);
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

				list = (List<DLFileEntryType>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
	 * Returns the first document library file entry type in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry type
	 * @throws NoSuchFileEntryTypeException if a matching document library file entry type could not be found
	 */
	@Override
	public DLFileEntryType findByUuid_First(
			String uuid, OrderByComparator<DLFileEntryType> orderByComparator)
		throws NoSuchFileEntryTypeException {

		DLFileEntryType dlFileEntryType = fetchByUuid_First(
			uuid, orderByComparator);

		if (dlFileEntryType != null) {
			return dlFileEntryType;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchFileEntryTypeException(sb.toString());
	}

	/**
	 * Returns the first document library file entry type in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry type, or <code>null</code> if a matching document library file entry type could not be found
	 */
	@Override
	public DLFileEntryType fetchByUuid_First(
		String uuid, OrderByComparator<DLFileEntryType> orderByComparator) {

		List<DLFileEntryType> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library file entry type in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file entry type
	 * @throws NoSuchFileEntryTypeException if a matching document library file entry type could not be found
	 */
	@Override
	public DLFileEntryType findByUuid_Last(
			String uuid, OrderByComparator<DLFileEntryType> orderByComparator)
		throws NoSuchFileEntryTypeException {

		DLFileEntryType dlFileEntryType = fetchByUuid_Last(
			uuid, orderByComparator);

		if (dlFileEntryType != null) {
			return dlFileEntryType;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchFileEntryTypeException(sb.toString());
	}

	/**
	 * Returns the last document library file entry type in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file entry type, or <code>null</code> if a matching document library file entry type could not be found
	 */
	@Override
	public DLFileEntryType fetchByUuid_Last(
		String uuid, OrderByComparator<DLFileEntryType> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<DLFileEntryType> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library file entry types before and after the current document library file entry type in the ordered set where uuid = &#63;.
	 *
	 * @param fileEntryTypeId the primary key of the current document library file entry type
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file entry type
	 * @throws NoSuchFileEntryTypeException if a document library file entry type with the primary key could not be found
	 */
	@Override
	public DLFileEntryType[] findByUuid_PrevAndNext(
			long fileEntryTypeId, String uuid,
			OrderByComparator<DLFileEntryType> orderByComparator)
		throws NoSuchFileEntryTypeException {

		uuid = Objects.toString(uuid, "");

		DLFileEntryType dlFileEntryType = findByPrimaryKey(fileEntryTypeId);

		Session session = null;

		try {
			session = openSession();

			DLFileEntryType[] array = new DLFileEntryTypeImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, dlFileEntryType, uuid, orderByComparator, true);

			array[1] = dlFileEntryType;

			array[2] = getByUuid_PrevAndNext(
				session, dlFileEntryType, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFileEntryType getByUuid_PrevAndNext(
		Session session, DLFileEntryType dlFileEntryType, String uuid,
		OrderByComparator<DLFileEntryType> orderByComparator,
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

		sb.append(_SQL_SELECT_DLFILEENTRYTYPE_WHERE);

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
			sb.append(DLFileEntryTypeModelImpl.ORDER_BY_JPQL);
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
						dlFileEntryType)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<DLFileEntryType> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the document library file entry types where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (DLFileEntryType dlFileEntryType :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(dlFileEntryType);
		}
	}

	/**
	 * Returns the number of document library file entry types where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching document library file entry types
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			DLFileEntryType.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByUuid;

			finderArgs = new Object[] {uuid};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_DLFILEENTRYTYPE_WHERE);

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
					FinderCacheUtil.putResult(finderPath, finderArgs, count);
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
		"dlFileEntryType.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(dlFileEntryType.uuid IS NULL OR dlFileEntryType.uuid = '')";

	private FinderPath _finderPathFetchByUUID_G;
	private FinderPath _finderPathCountByUUID_G;

	/**
	 * Returns the document library file entry type where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchFileEntryTypeException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching document library file entry type
	 * @throws NoSuchFileEntryTypeException if a matching document library file entry type could not be found
	 */
	@Override
	public DLFileEntryType findByUUID_G(String uuid, long groupId)
		throws NoSuchFileEntryTypeException {

		DLFileEntryType dlFileEntryType = fetchByUUID_G(uuid, groupId);

		if (dlFileEntryType == null) {
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

			throw new NoSuchFileEntryTypeException(sb.toString());
		}

		return dlFileEntryType;
	}

	/**
	 * Returns the document library file entry type where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching document library file entry type, or <code>null</code> if a matching document library file entry type could not be found
	 */
	@Override
	public DLFileEntryType fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the document library file entry type where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching document library file entry type, or <code>null</code> if a matching document library file entry type could not be found
	 */
	@Override
	public DLFileEntryType fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			DLFileEntryType.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {uuid, groupId};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = FinderCacheUtil.getResult(
				_finderPathFetchByUUID_G, finderArgs, this);
		}

		if (result instanceof DLFileEntryType) {
			DLFileEntryType dlFileEntryType = (DLFileEntryType)result;

			if (!Objects.equals(uuid, dlFileEntryType.getUuid()) ||
				(groupId != dlFileEntryType.getGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_DLFILEENTRYTYPE_WHERE);

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

				List<DLFileEntryType> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						FinderCacheUtil.putResult(
							_finderPathFetchByUUID_G, finderArgs, list);
					}
				}
				else {
					DLFileEntryType dlFileEntryType = list.get(0);

					result = dlFileEntryType;

					cacheResult(dlFileEntryType);
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
			return (DLFileEntryType)result;
		}
	}

	/**
	 * Removes the document library file entry type where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the document library file entry type that was removed
	 */
	@Override
	public DLFileEntryType removeByUUID_G(String uuid, long groupId)
		throws NoSuchFileEntryTypeException {

		DLFileEntryType dlFileEntryType = findByUUID_G(uuid, groupId);

		return remove(dlFileEntryType);
	}

	/**
	 * Returns the number of document library file entry types where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching document library file entry types
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			DLFileEntryType.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByUUID_G;

			finderArgs = new Object[] {uuid, groupId};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_DLFILEENTRYTYPE_WHERE);

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
					FinderCacheUtil.putResult(finderPath, finderArgs, count);
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
		"dlFileEntryType.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(dlFileEntryType.uuid IS NULL OR dlFileEntryType.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"dlFileEntryType.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the document library file entry types where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching document library file entry types
	 */
	@Override
	public List<DLFileEntryType> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entry types where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryTypeModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of document library file entry types
	 * @param end the upper bound of the range of document library file entry types (not inclusive)
	 * @return the range of matching document library file entry types
	 */
	@Override
	public List<DLFileEntryType> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entry types where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryTypeModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of document library file entry types
	 * @param end the upper bound of the range of document library file entry types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entry types
	 */
	@Override
	public List<DLFileEntryType> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<DLFileEntryType> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file entry types where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryTypeModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of document library file entry types
	 * @param end the upper bound of the range of document library file entry types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching document library file entry types
	 */
	@Override
	public List<DLFileEntryType> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<DLFileEntryType> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			DLFileEntryType.class);

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

		List<DLFileEntryType> list = null;

		if (useFinderCache && productionMode) {
			list = (List<DLFileEntryType>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DLFileEntryType dlFileEntryType : list) {
					if (!uuid.equals(dlFileEntryType.getUuid()) ||
						(companyId != dlFileEntryType.getCompanyId())) {

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

			sb.append(_SQL_SELECT_DLFILEENTRYTYPE_WHERE);

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
				sb.append(DLFileEntryTypeModelImpl.ORDER_BY_JPQL);
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

				list = (List<DLFileEntryType>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
	 * Returns the first document library file entry type in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry type
	 * @throws NoSuchFileEntryTypeException if a matching document library file entry type could not be found
	 */
	@Override
	public DLFileEntryType findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<DLFileEntryType> orderByComparator)
		throws NoSuchFileEntryTypeException {

		DLFileEntryType dlFileEntryType = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (dlFileEntryType != null) {
			return dlFileEntryType;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchFileEntryTypeException(sb.toString());
	}

	/**
	 * Returns the first document library file entry type in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry type, or <code>null</code> if a matching document library file entry type could not be found
	 */
	@Override
	public DLFileEntryType fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<DLFileEntryType> orderByComparator) {

		List<DLFileEntryType> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library file entry type in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file entry type
	 * @throws NoSuchFileEntryTypeException if a matching document library file entry type could not be found
	 */
	@Override
	public DLFileEntryType findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<DLFileEntryType> orderByComparator)
		throws NoSuchFileEntryTypeException {

		DLFileEntryType dlFileEntryType = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (dlFileEntryType != null) {
			return dlFileEntryType;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchFileEntryTypeException(sb.toString());
	}

	/**
	 * Returns the last document library file entry type in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file entry type, or <code>null</code> if a matching document library file entry type could not be found
	 */
	@Override
	public DLFileEntryType fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<DLFileEntryType> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<DLFileEntryType> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library file entry types before and after the current document library file entry type in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param fileEntryTypeId the primary key of the current document library file entry type
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file entry type
	 * @throws NoSuchFileEntryTypeException if a document library file entry type with the primary key could not be found
	 */
	@Override
	public DLFileEntryType[] findByUuid_C_PrevAndNext(
			long fileEntryTypeId, String uuid, long companyId,
			OrderByComparator<DLFileEntryType> orderByComparator)
		throws NoSuchFileEntryTypeException {

		uuid = Objects.toString(uuid, "");

		DLFileEntryType dlFileEntryType = findByPrimaryKey(fileEntryTypeId);

		Session session = null;

		try {
			session = openSession();

			DLFileEntryType[] array = new DLFileEntryTypeImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, dlFileEntryType, uuid, companyId, orderByComparator,
				true);

			array[1] = dlFileEntryType;

			array[2] = getByUuid_C_PrevAndNext(
				session, dlFileEntryType, uuid, companyId, orderByComparator,
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

	protected DLFileEntryType getByUuid_C_PrevAndNext(
		Session session, DLFileEntryType dlFileEntryType, String uuid,
		long companyId, OrderByComparator<DLFileEntryType> orderByComparator,
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

		sb.append(_SQL_SELECT_DLFILEENTRYTYPE_WHERE);

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
			sb.append(DLFileEntryTypeModelImpl.ORDER_BY_JPQL);
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
						dlFileEntryType)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<DLFileEntryType> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the document library file entry types where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (DLFileEntryType dlFileEntryType :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(dlFileEntryType);
		}
	}

	/**
	 * Returns the number of document library file entry types where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching document library file entry types
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			DLFileEntryType.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByUuid_C;

			finderArgs = new Object[] {uuid, companyId};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_DLFILEENTRYTYPE_WHERE);

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
					FinderCacheUtil.putResult(finderPath, finderArgs, count);
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
		"dlFileEntryType.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(dlFileEntryType.uuid IS NULL OR dlFileEntryType.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"dlFileEntryType.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByGroupId;
	private FinderPath _finderPathWithoutPaginationFindByGroupId;
	private FinderPath _finderPathCountByGroupId;
	private FinderPath _finderPathWithPaginationCountByGroupId;

	/**
	 * Returns all the document library file entry types where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching document library file entry types
	 */
	@Override
	public List<DLFileEntryType> findByGroupId(long groupId) {
		return findByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entry types where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryTypeModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of document library file entry types
	 * @param end the upper bound of the range of document library file entry types (not inclusive)
	 * @return the range of matching document library file entry types
	 */
	@Override
	public List<DLFileEntryType> findByGroupId(
		long groupId, int start, int end) {

		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entry types where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryTypeModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of document library file entry types
	 * @param end the upper bound of the range of document library file entry types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entry types
	 */
	@Override
	public List<DLFileEntryType> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<DLFileEntryType> orderByComparator) {

		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file entry types where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryTypeModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of document library file entry types
	 * @param end the upper bound of the range of document library file entry types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching document library file entry types
	 */
	@Override
	public List<DLFileEntryType> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<DLFileEntryType> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			DLFileEntryType.class);

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

		List<DLFileEntryType> list = null;

		if (useFinderCache && productionMode) {
			list = (List<DLFileEntryType>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DLFileEntryType dlFileEntryType : list) {
					if (groupId != dlFileEntryType.getGroupId()) {
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

			sb.append(_SQL_SELECT_DLFILEENTRYTYPE_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(DLFileEntryTypeModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				list = (List<DLFileEntryType>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
	 * Returns the first document library file entry type in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry type
	 * @throws NoSuchFileEntryTypeException if a matching document library file entry type could not be found
	 */
	@Override
	public DLFileEntryType findByGroupId_First(
			long groupId, OrderByComparator<DLFileEntryType> orderByComparator)
		throws NoSuchFileEntryTypeException {

		DLFileEntryType dlFileEntryType = fetchByGroupId_First(
			groupId, orderByComparator);

		if (dlFileEntryType != null) {
			return dlFileEntryType;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchFileEntryTypeException(sb.toString());
	}

	/**
	 * Returns the first document library file entry type in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry type, or <code>null</code> if a matching document library file entry type could not be found
	 */
	@Override
	public DLFileEntryType fetchByGroupId_First(
		long groupId, OrderByComparator<DLFileEntryType> orderByComparator) {

		List<DLFileEntryType> list = findByGroupId(
			groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library file entry type in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file entry type
	 * @throws NoSuchFileEntryTypeException if a matching document library file entry type could not be found
	 */
	@Override
	public DLFileEntryType findByGroupId_Last(
			long groupId, OrderByComparator<DLFileEntryType> orderByComparator)
		throws NoSuchFileEntryTypeException {

		DLFileEntryType dlFileEntryType = fetchByGroupId_Last(
			groupId, orderByComparator);

		if (dlFileEntryType != null) {
			return dlFileEntryType;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchFileEntryTypeException(sb.toString());
	}

	/**
	 * Returns the last document library file entry type in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file entry type, or <code>null</code> if a matching document library file entry type could not be found
	 */
	@Override
	public DLFileEntryType fetchByGroupId_Last(
		long groupId, OrderByComparator<DLFileEntryType> orderByComparator) {

		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<DLFileEntryType> list = findByGroupId(
			groupId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library file entry types before and after the current document library file entry type in the ordered set where groupId = &#63;.
	 *
	 * @param fileEntryTypeId the primary key of the current document library file entry type
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file entry type
	 * @throws NoSuchFileEntryTypeException if a document library file entry type with the primary key could not be found
	 */
	@Override
	public DLFileEntryType[] findByGroupId_PrevAndNext(
			long fileEntryTypeId, long groupId,
			OrderByComparator<DLFileEntryType> orderByComparator)
		throws NoSuchFileEntryTypeException {

		DLFileEntryType dlFileEntryType = findByPrimaryKey(fileEntryTypeId);

		Session session = null;

		try {
			session = openSession();

			DLFileEntryType[] array = new DLFileEntryTypeImpl[3];

			array[0] = getByGroupId_PrevAndNext(
				session, dlFileEntryType, groupId, orderByComparator, true);

			array[1] = dlFileEntryType;

			array[2] = getByGroupId_PrevAndNext(
				session, dlFileEntryType, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFileEntryType getByGroupId_PrevAndNext(
		Session session, DLFileEntryType dlFileEntryType, long groupId,
		OrderByComparator<DLFileEntryType> orderByComparator,
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

		sb.append(_SQL_SELECT_DLFILEENTRYTYPE_WHERE);

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
			sb.append(DLFileEntryTypeModelImpl.ORDER_BY_JPQL);
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
						dlFileEntryType)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<DLFileEntryType> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the document library file entry types that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching document library file entry types that the user has permission to view
	 */
	@Override
	public List<DLFileEntryType> filterFindByGroupId(long groupId) {
		return filterFindByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entry types that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryTypeModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of document library file entry types
	 * @param end the upper bound of the range of document library file entry types (not inclusive)
	 * @return the range of matching document library file entry types that the user has permission to view
	 */
	@Override
	public List<DLFileEntryType> filterFindByGroupId(
		long groupId, int start, int end) {

		return filterFindByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entry types that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryTypeModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of document library file entry types
	 * @param end the upper bound of the range of document library file entry types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entry types that the user has permission to view
	 */
	@Override
	public List<DLFileEntryType> filterFindByGroupId(
		long groupId, int start, int end,
		OrderByComparator<DLFileEntryType> orderByComparator) {

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
			sb.append(_FILTER_SQL_SELECT_DLFILEENTRYTYPE_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_DLFILEENTRYTYPE_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_DLFILEENTRYTYPE_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(DLFileEntryTypeModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(DLFileEntryTypeModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), DLFileEntryType.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, DLFileEntryTypeImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, DLFileEntryTypeImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(groupId);

			return (List<DLFileEntryType>)QueryUtil.list(
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
	 * Returns the document library file entry types before and after the current document library file entry type in the ordered set of document library file entry types that the user has permission to view where groupId = &#63;.
	 *
	 * @param fileEntryTypeId the primary key of the current document library file entry type
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file entry type
	 * @throws NoSuchFileEntryTypeException if a document library file entry type with the primary key could not be found
	 */
	@Override
	public DLFileEntryType[] filterFindByGroupId_PrevAndNext(
			long fileEntryTypeId, long groupId,
			OrderByComparator<DLFileEntryType> orderByComparator)
		throws NoSuchFileEntryTypeException {

		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId_PrevAndNext(
				fileEntryTypeId, groupId, orderByComparator);
		}

		DLFileEntryType dlFileEntryType = findByPrimaryKey(fileEntryTypeId);

		Session session = null;

		try {
			session = openSession();

			DLFileEntryType[] array = new DLFileEntryTypeImpl[3];

			array[0] = filterGetByGroupId_PrevAndNext(
				session, dlFileEntryType, groupId, orderByComparator, true);

			array[1] = dlFileEntryType;

			array[2] = filterGetByGroupId_PrevAndNext(
				session, dlFileEntryType, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFileEntryType filterGetByGroupId_PrevAndNext(
		Session session, DLFileEntryType dlFileEntryType, long groupId,
		OrderByComparator<DLFileEntryType> orderByComparator,
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
			sb.append(_FILTER_SQL_SELECT_DLFILEENTRYTYPE_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_DLFILEENTRYTYPE_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_DLFILEENTRYTYPE_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(DLFileEntryTypeModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(DLFileEntryTypeModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), DLFileEntryType.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(_FILTER_ENTITY_ALIAS, DLFileEntryTypeImpl.class);
		}
		else {
			sqlQuery.addEntity(_FILTER_ENTITY_TABLE, DLFileEntryTypeImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(groupId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						dlFileEntryType)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<DLFileEntryType> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the document library file entry types that the user has permission to view where groupId = any &#63;.
	 *
	 * @param groupIds the group IDs
	 * @return the matching document library file entry types that the user has permission to view
	 */
	@Override
	public List<DLFileEntryType> filterFindByGroupId(long[] groupIds) {
		return filterFindByGroupId(
			groupIds, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entry types that the user has permission to view where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryTypeModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param start the lower bound of the range of document library file entry types
	 * @param end the upper bound of the range of document library file entry types (not inclusive)
	 * @return the range of matching document library file entry types that the user has permission to view
	 */
	@Override
	public List<DLFileEntryType> filterFindByGroupId(
		long[] groupIds, int start, int end) {

		return filterFindByGroupId(groupIds, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entry types that the user has permission to view where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryTypeModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param start the lower bound of the range of document library file entry types
	 * @param end the upper bound of the range of document library file entry types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entry types that the user has permission to view
	 */
	@Override
	public List<DLFileEntryType> filterFindByGroupId(
		long[] groupIds, int start, int end,
		OrderByComparator<DLFileEntryType> orderByComparator) {

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
			sb.append(_FILTER_SQL_SELECT_DLFILEENTRYTYPE_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_DLFILEENTRYTYPE_NO_INLINE_DISTINCT_WHERE_1);
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
				_FILTER_SQL_SELECT_DLFILEENTRYTYPE_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(DLFileEntryTypeModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(DLFileEntryTypeModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), DLFileEntryType.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupIds);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, DLFileEntryTypeImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, DLFileEntryTypeImpl.class);
			}

			return (List<DLFileEntryType>)QueryUtil.list(
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
	 * Returns all the document library file entry types where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryTypeModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @return the matching document library file entry types
	 */
	@Override
	public List<DLFileEntryType> findByGroupId(long[] groupIds) {
		return findByGroupId(
			groupIds, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entry types where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryTypeModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param start the lower bound of the range of document library file entry types
	 * @param end the upper bound of the range of document library file entry types (not inclusive)
	 * @return the range of matching document library file entry types
	 */
	@Override
	public List<DLFileEntryType> findByGroupId(
		long[] groupIds, int start, int end) {

		return findByGroupId(groupIds, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entry types where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryTypeModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param start the lower bound of the range of document library file entry types
	 * @param end the upper bound of the range of document library file entry types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entry types
	 */
	@Override
	public List<DLFileEntryType> findByGroupId(
		long[] groupIds, int start, int end,
		OrderByComparator<DLFileEntryType> orderByComparator) {

		return findByGroupId(groupIds, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file entry types where groupId = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryTypeModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of document library file entry types
	 * @param end the upper bound of the range of document library file entry types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching document library file entry types
	 */
	@Override
	public List<DLFileEntryType> findByGroupId(
		long[] groupIds, int start, int end,
		OrderByComparator<DLFileEntryType> orderByComparator,
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

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			DLFileEntryType.class);

		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderArgs = new Object[] {StringUtil.merge(groupIds)};
			}
		}
		else if (useFinderCache && productionMode) {
			finderArgs = new Object[] {
				StringUtil.merge(groupIds), start, end, orderByComparator
			};
		}

		List<DLFileEntryType> list = null;

		if (useFinderCache && productionMode) {
			list = (List<DLFileEntryType>)FinderCacheUtil.getResult(
				_finderPathWithPaginationFindByGroupId, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DLFileEntryType dlFileEntryType : list) {
					if (!ArrayUtil.contains(
							groupIds, dlFileEntryType.getGroupId())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_SELECT_DLFILEENTRYTYPE_WHERE);

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
				sb.append(DLFileEntryTypeModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<DLFileEntryType>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					FinderCacheUtil.putResult(
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
	 * Removes all the document library file entry types where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (DLFileEntryType dlFileEntryType :
				findByGroupId(
					groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(dlFileEntryType);
		}
	}

	/**
	 * Returns the number of document library file entry types where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching document library file entry types
	 */
	@Override
	public int countByGroupId(long groupId) {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			DLFileEntryType.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByGroupId;

			finderArgs = new Object[] {groupId};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_DLFILEENTRYTYPE_WHERE);

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
					FinderCacheUtil.putResult(finderPath, finderArgs, count);
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
	 * Returns the number of document library file entry types where groupId = any &#63;.
	 *
	 * @param groupIds the group IDs
	 * @return the number of matching document library file entry types
	 */
	@Override
	public int countByGroupId(long[] groupIds) {
		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.sortedUnique(groupIds);
		}

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			DLFileEntryType.class);

		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderArgs = new Object[] {StringUtil.merge(groupIds)};

			count = (Long)FinderCacheUtil.getResult(
				_finderPathWithPaginationCountByGroupId, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_COUNT_DLFILEENTRYTYPE_WHERE);

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

				if (productionMode) {
					FinderCacheUtil.putResult(
						_finderPathWithPaginationCountByGroupId, finderArgs,
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

	/**
	 * Returns the number of document library file entry types that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching document library file entry types that the user has permission to view
	 */
	@Override
	public int filterCountByGroupId(long groupId) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByGroupId(groupId);
		}

		StringBundler sb = new StringBundler(2);

		sb.append(_FILTER_SQL_COUNT_DLFILEENTRYTYPE_WHERE);

		sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), DLFileEntryType.class.getName(),
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
	 * Returns the number of document library file entry types that the user has permission to view where groupId = any &#63;.
	 *
	 * @param groupIds the group IDs
	 * @return the number of matching document library file entry types that the user has permission to view
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

		sb.append(_FILTER_SQL_COUNT_DLFILEENTRYTYPE_WHERE);

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
			sb.toString(), DLFileEntryType.class.getName(),
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
		"dlFileEntryType.groupId = ?";

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_7 =
		"dlFileEntryType.groupId IN (";

	private FinderPath _finderPathFetchByG_F;
	private FinderPath _finderPathCountByG_F;

	/**
	 * Returns the document library file entry type where groupId = &#63; and fileEntryTypeKey = &#63; or throws a <code>NoSuchFileEntryTypeException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param fileEntryTypeKey the file entry type key
	 * @return the matching document library file entry type
	 * @throws NoSuchFileEntryTypeException if a matching document library file entry type could not be found
	 */
	@Override
	public DLFileEntryType findByG_F(long groupId, String fileEntryTypeKey)
		throws NoSuchFileEntryTypeException {

		DLFileEntryType dlFileEntryType = fetchByG_F(groupId, fileEntryTypeKey);

		if (dlFileEntryType == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("groupId=");
			sb.append(groupId);

			sb.append(", fileEntryTypeKey=");
			sb.append(fileEntryTypeKey);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchFileEntryTypeException(sb.toString());
		}

		return dlFileEntryType;
	}

	/**
	 * Returns the document library file entry type where groupId = &#63; and fileEntryTypeKey = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param fileEntryTypeKey the file entry type key
	 * @return the matching document library file entry type, or <code>null</code> if a matching document library file entry type could not be found
	 */
	@Override
	public DLFileEntryType fetchByG_F(long groupId, String fileEntryTypeKey) {
		return fetchByG_F(groupId, fileEntryTypeKey, true);
	}

	/**
	 * Returns the document library file entry type where groupId = &#63; and fileEntryTypeKey = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param fileEntryTypeKey the file entry type key
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching document library file entry type, or <code>null</code> if a matching document library file entry type could not be found
	 */
	@Override
	public DLFileEntryType fetchByG_F(
		long groupId, String fileEntryTypeKey, boolean useFinderCache) {

		fileEntryTypeKey = Objects.toString(fileEntryTypeKey, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			DLFileEntryType.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {groupId, fileEntryTypeKey};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = FinderCacheUtil.getResult(
				_finderPathFetchByG_F, finderArgs, this);
		}

		if (result instanceof DLFileEntryType) {
			DLFileEntryType dlFileEntryType = (DLFileEntryType)result;

			if ((groupId != dlFileEntryType.getGroupId()) ||
				!Objects.equals(
					fileEntryTypeKey, dlFileEntryType.getFileEntryTypeKey())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_DLFILEENTRYTYPE_WHERE);

			sb.append(_FINDER_COLUMN_G_F_GROUPID_2);

			boolean bindFileEntryTypeKey = false;

			if (fileEntryTypeKey.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_F_FILEENTRYTYPEKEY_3);
			}
			else {
				bindFileEntryTypeKey = true;

				sb.append(_FINDER_COLUMN_G_F_FILEENTRYTYPEKEY_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindFileEntryTypeKey) {
					queryPos.add(fileEntryTypeKey);
				}

				List<DLFileEntryType> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						FinderCacheUtil.putResult(
							_finderPathFetchByG_F, finderArgs, list);
					}
				}
				else {
					DLFileEntryType dlFileEntryType = list.get(0);

					result = dlFileEntryType;

					cacheResult(dlFileEntryType);
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
			return (DLFileEntryType)result;
		}
	}

	/**
	 * Removes the document library file entry type where groupId = &#63; and fileEntryTypeKey = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fileEntryTypeKey the file entry type key
	 * @return the document library file entry type that was removed
	 */
	@Override
	public DLFileEntryType removeByG_F(long groupId, String fileEntryTypeKey)
		throws NoSuchFileEntryTypeException {

		DLFileEntryType dlFileEntryType = findByG_F(groupId, fileEntryTypeKey);

		return remove(dlFileEntryType);
	}

	/**
	 * Returns the number of document library file entry types where groupId = &#63; and fileEntryTypeKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fileEntryTypeKey the file entry type key
	 * @return the number of matching document library file entry types
	 */
	@Override
	public int countByG_F(long groupId, String fileEntryTypeKey) {
		fileEntryTypeKey = Objects.toString(fileEntryTypeKey, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			DLFileEntryType.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_F;

			finderArgs = new Object[] {groupId, fileEntryTypeKey};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_DLFILEENTRYTYPE_WHERE);

			sb.append(_FINDER_COLUMN_G_F_GROUPID_2);

			boolean bindFileEntryTypeKey = false;

			if (fileEntryTypeKey.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_F_FILEENTRYTYPEKEY_3);
			}
			else {
				bindFileEntryTypeKey = true;

				sb.append(_FINDER_COLUMN_G_F_FILEENTRYTYPEKEY_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindFileEntryTypeKey) {
					queryPos.add(fileEntryTypeKey);
				}

				count = (Long)query.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, count);
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

	private static final String _FINDER_COLUMN_G_F_GROUPID_2 =
		"dlFileEntryType.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_F_FILEENTRYTYPEKEY_2 =
		"dlFileEntryType.fileEntryTypeKey = ?";

	private static final String _FINDER_COLUMN_G_F_FILEENTRYTYPEKEY_3 =
		"(dlFileEntryType.fileEntryTypeKey IS NULL OR dlFileEntryType.fileEntryTypeKey = '')";

	public DLFileEntryTypePersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(DLFileEntryType.class);

		setModelImplClass(DLFileEntryTypeImpl.class);
		setModelPKClass(long.class);

		setTable(DLFileEntryTypeTable.INSTANCE);
	}

	/**
	 * Caches the document library file entry type in the entity cache if it is enabled.
	 *
	 * @param dlFileEntryType the document library file entry type
	 */
	@Override
	public void cacheResult(DLFileEntryType dlFileEntryType) {
		if (dlFileEntryType.getCtCollectionId() != 0) {
			dlFileEntryType.resetOriginalValues();

			return;
		}

		EntityCacheUtil.putResult(
			DLFileEntryTypeImpl.class, dlFileEntryType.getPrimaryKey(),
			dlFileEntryType);

		FinderCacheUtil.putResult(
			_finderPathFetchByUUID_G,
			new Object[] {
				dlFileEntryType.getUuid(), dlFileEntryType.getGroupId()
			},
			dlFileEntryType);

		FinderCacheUtil.putResult(
			_finderPathFetchByG_F,
			new Object[] {
				dlFileEntryType.getGroupId(),
				dlFileEntryType.getFileEntryTypeKey()
			},
			dlFileEntryType);

		dlFileEntryType.resetOriginalValues();
	}

	/**
	 * Caches the document library file entry types in the entity cache if it is enabled.
	 *
	 * @param dlFileEntryTypes the document library file entry types
	 */
	@Override
	public void cacheResult(List<DLFileEntryType> dlFileEntryTypes) {
		for (DLFileEntryType dlFileEntryType : dlFileEntryTypes) {
			if (dlFileEntryType.getCtCollectionId() != 0) {
				dlFileEntryType.resetOriginalValues();

				continue;
			}

			if (EntityCacheUtil.getResult(
					DLFileEntryTypeImpl.class,
					dlFileEntryType.getPrimaryKey()) == null) {

				cacheResult(dlFileEntryType);
			}
			else {
				dlFileEntryType.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all document library file entry types.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>com.liferay.portal.kernel.dao.orm.FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		EntityCacheUtil.clearCache(DLFileEntryTypeImpl.class);

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the document library file entry type.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>com.liferay.portal.kernel.dao.orm.FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(DLFileEntryType dlFileEntryType) {
		EntityCacheUtil.removeResult(
			DLFileEntryTypeImpl.class, dlFileEntryType.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(DLFileEntryTypeModelImpl)dlFileEntryType, true);
	}

	@Override
	public void clearCache(List<DLFileEntryType> dlFileEntryTypes) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (DLFileEntryType dlFileEntryType : dlFileEntryTypes) {
			EntityCacheUtil.removeResult(
				DLFileEntryTypeImpl.class, dlFileEntryType.getPrimaryKey());

			clearUniqueFindersCache(
				(DLFileEntryTypeModelImpl)dlFileEntryType, true);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			EntityCacheUtil.removeResult(DLFileEntryTypeImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		DLFileEntryTypeModelImpl dlFileEntryTypeModelImpl) {

		Object[] args = new Object[] {
			dlFileEntryTypeModelImpl.getUuid(),
			dlFileEntryTypeModelImpl.getGroupId()
		};

		FinderCacheUtil.putResult(
			_finderPathCountByUUID_G, args, Long.valueOf(1), false);
		FinderCacheUtil.putResult(
			_finderPathFetchByUUID_G, args, dlFileEntryTypeModelImpl, false);

		args = new Object[] {
			dlFileEntryTypeModelImpl.getGroupId(),
			dlFileEntryTypeModelImpl.getFileEntryTypeKey()
		};

		FinderCacheUtil.putResult(
			_finderPathCountByG_F, args, Long.valueOf(1), false);
		FinderCacheUtil.putResult(
			_finderPathFetchByG_F, args, dlFileEntryTypeModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		DLFileEntryTypeModelImpl dlFileEntryTypeModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				dlFileEntryTypeModelImpl.getUuid(),
				dlFileEntryTypeModelImpl.getGroupId()
			};

			FinderCacheUtil.removeResult(_finderPathCountByUUID_G, args);
			FinderCacheUtil.removeResult(_finderPathFetchByUUID_G, args);
		}

		if ((dlFileEntryTypeModelImpl.getColumnBitmask() &
			 _finderPathFetchByUUID_G.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				dlFileEntryTypeModelImpl.getOriginalUuid(),
				dlFileEntryTypeModelImpl.getOriginalGroupId()
			};

			FinderCacheUtil.removeResult(_finderPathCountByUUID_G, args);
			FinderCacheUtil.removeResult(_finderPathFetchByUUID_G, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
				dlFileEntryTypeModelImpl.getGroupId(),
				dlFileEntryTypeModelImpl.getFileEntryTypeKey()
			};

			FinderCacheUtil.removeResult(_finderPathCountByG_F, args);
			FinderCacheUtil.removeResult(_finderPathFetchByG_F, args);
		}

		if ((dlFileEntryTypeModelImpl.getColumnBitmask() &
			 _finderPathFetchByG_F.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				dlFileEntryTypeModelImpl.getOriginalGroupId(),
				dlFileEntryTypeModelImpl.getOriginalFileEntryTypeKey()
			};

			FinderCacheUtil.removeResult(_finderPathCountByG_F, args);
			FinderCacheUtil.removeResult(_finderPathFetchByG_F, args);
		}
	}

	/**
	 * Creates a new document library file entry type with the primary key. Does not add the document library file entry type to the database.
	 *
	 * @param fileEntryTypeId the primary key for the new document library file entry type
	 * @return the new document library file entry type
	 */
	@Override
	public DLFileEntryType create(long fileEntryTypeId) {
		DLFileEntryType dlFileEntryType = new DLFileEntryTypeImpl();

		dlFileEntryType.setNew(true);
		dlFileEntryType.setPrimaryKey(fileEntryTypeId);

		String uuid = PortalUUIDUtil.generate();

		dlFileEntryType.setUuid(uuid);

		dlFileEntryType.setCompanyId(CompanyThreadLocal.getCompanyId());

		return dlFileEntryType;
	}

	/**
	 * Removes the document library file entry type with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param fileEntryTypeId the primary key of the document library file entry type
	 * @return the document library file entry type that was removed
	 * @throws NoSuchFileEntryTypeException if a document library file entry type with the primary key could not be found
	 */
	@Override
	public DLFileEntryType remove(long fileEntryTypeId)
		throws NoSuchFileEntryTypeException {

		return remove((Serializable)fileEntryTypeId);
	}

	/**
	 * Removes the document library file entry type with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the document library file entry type
	 * @return the document library file entry type that was removed
	 * @throws NoSuchFileEntryTypeException if a document library file entry type with the primary key could not be found
	 */
	@Override
	public DLFileEntryType remove(Serializable primaryKey)
		throws NoSuchFileEntryTypeException {

		Session session = null;

		try {
			session = openSession();

			DLFileEntryType dlFileEntryType = (DLFileEntryType)session.get(
				DLFileEntryTypeImpl.class, primaryKey);

			if (dlFileEntryType == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchFileEntryTypeException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(dlFileEntryType);
		}
		catch (NoSuchFileEntryTypeException noSuchEntityException) {
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
	protected DLFileEntryType removeImpl(DLFileEntryType dlFileEntryType) {
		dlFileEntryTypeToDLFolderTableMapper.deleteLeftPrimaryKeyTableMappings(
			dlFileEntryType.getPrimaryKey());

		if (!CTPersistenceHelperUtil.isRemove(dlFileEntryType)) {
			return dlFileEntryType;
		}

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(dlFileEntryType)) {
				dlFileEntryType = (DLFileEntryType)session.get(
					DLFileEntryTypeImpl.class,
					dlFileEntryType.getPrimaryKeyObj());
			}

			if (dlFileEntryType != null) {
				session.delete(dlFileEntryType);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (dlFileEntryType != null) {
			clearCache(dlFileEntryType);
		}

		return dlFileEntryType;
	}

	@Override
	public DLFileEntryType updateImpl(DLFileEntryType dlFileEntryType) {
		boolean isNew = dlFileEntryType.isNew();

		if (!(dlFileEntryType instanceof DLFileEntryTypeModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(dlFileEntryType.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					dlFileEntryType);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in dlFileEntryType proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom DLFileEntryType implementation " +
					dlFileEntryType.getClass());
		}

		DLFileEntryTypeModelImpl dlFileEntryTypeModelImpl =
			(DLFileEntryTypeModelImpl)dlFileEntryType;

		if (Validator.isNull(dlFileEntryType.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			dlFileEntryType.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (dlFileEntryType.getCreateDate() == null)) {
			if (serviceContext == null) {
				dlFileEntryType.setCreateDate(now);
			}
			else {
				dlFileEntryType.setCreateDate(
					serviceContext.getCreateDate(now));
			}
		}

		if (!dlFileEntryTypeModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				dlFileEntryType.setModifiedDate(now);
			}
			else {
				dlFileEntryType.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (CTPersistenceHelperUtil.isInsert(dlFileEntryType)) {
				if (!isNew) {
					session.evict(
						DLFileEntryTypeImpl.class,
						dlFileEntryType.getPrimaryKeyObj());
				}

				session.save(dlFileEntryType);

				dlFileEntryType.setNew(false);
			}
			else {
				dlFileEntryType = (DLFileEntryType)session.merge(
					dlFileEntryType);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (dlFileEntryType.getCtCollectionId() != 0) {
			dlFileEntryType.resetOriginalValues();

			return dlFileEntryType;
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew) {
			Object[] args = new Object[] {dlFileEntryTypeModelImpl.getUuid()};

			FinderCacheUtil.removeResult(_finderPathCountByUuid, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByUuid, args);

			args = new Object[] {
				dlFileEntryTypeModelImpl.getUuid(),
				dlFileEntryTypeModelImpl.getCompanyId()
			};

			FinderCacheUtil.removeResult(_finderPathCountByUuid_C, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByUuid_C, args);

			args = new Object[] {dlFileEntryTypeModelImpl.getGroupId()};

			FinderCacheUtil.removeResult(_finderPathCountByGroupId, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByGroupId, args);

			FinderCacheUtil.removeResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((dlFileEntryTypeModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					dlFileEntryTypeModelImpl.getOriginalUuid()
				};

				FinderCacheUtil.removeResult(_finderPathCountByUuid, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);

				args = new Object[] {dlFileEntryTypeModelImpl.getUuid()};

				FinderCacheUtil.removeResult(_finderPathCountByUuid, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);
			}

			if ((dlFileEntryTypeModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					dlFileEntryTypeModelImpl.getOriginalUuid(),
					dlFileEntryTypeModelImpl.getOriginalCompanyId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByUuid_C, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);

				args = new Object[] {
					dlFileEntryTypeModelImpl.getUuid(),
					dlFileEntryTypeModelImpl.getCompanyId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByUuid_C, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);
			}

			if ((dlFileEntryTypeModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByGroupId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					dlFileEntryTypeModelImpl.getOriginalGroupId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByGroupId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByGroupId, args);

				args = new Object[] {dlFileEntryTypeModelImpl.getGroupId()};

				FinderCacheUtil.removeResult(_finderPathCountByGroupId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByGroupId, args);
			}
		}

		EntityCacheUtil.putResult(
			DLFileEntryTypeImpl.class, dlFileEntryType.getPrimaryKey(),
			dlFileEntryType, false);

		clearUniqueFindersCache(dlFileEntryTypeModelImpl, false);
		cacheUniqueFindersCache(dlFileEntryTypeModelImpl);

		dlFileEntryType.resetOriginalValues();

		return dlFileEntryType;
	}

	/**
	 * Returns the document library file entry type with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the document library file entry type
	 * @return the document library file entry type
	 * @throws NoSuchFileEntryTypeException if a document library file entry type with the primary key could not be found
	 */
	@Override
	public DLFileEntryType findByPrimaryKey(Serializable primaryKey)
		throws NoSuchFileEntryTypeException {

		DLFileEntryType dlFileEntryType = fetchByPrimaryKey(primaryKey);

		if (dlFileEntryType == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchFileEntryTypeException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return dlFileEntryType;
	}

	/**
	 * Returns the document library file entry type with the primary key or throws a <code>NoSuchFileEntryTypeException</code> if it could not be found.
	 *
	 * @param fileEntryTypeId the primary key of the document library file entry type
	 * @return the document library file entry type
	 * @throws NoSuchFileEntryTypeException if a document library file entry type with the primary key could not be found
	 */
	@Override
	public DLFileEntryType findByPrimaryKey(long fileEntryTypeId)
		throws NoSuchFileEntryTypeException {

		return findByPrimaryKey((Serializable)fileEntryTypeId);
	}

	/**
	 * Returns the document library file entry type with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the document library file entry type
	 * @return the document library file entry type, or <code>null</code> if a document library file entry type with the primary key could not be found
	 */
	@Override
	public DLFileEntryType fetchByPrimaryKey(Serializable primaryKey) {
		if (CTPersistenceHelperUtil.isProductionMode(DLFileEntryType.class)) {
			return super.fetchByPrimaryKey(primaryKey);
		}

		DLFileEntryType dlFileEntryType = null;

		Session session = null;

		try {
			session = openSession();

			dlFileEntryType = (DLFileEntryType)session.get(
				DLFileEntryTypeImpl.class, primaryKey);

			if (dlFileEntryType != null) {
				cacheResult(dlFileEntryType);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return dlFileEntryType;
	}

	/**
	 * Returns the document library file entry type with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param fileEntryTypeId the primary key of the document library file entry type
	 * @return the document library file entry type, or <code>null</code> if a document library file entry type with the primary key could not be found
	 */
	@Override
	public DLFileEntryType fetchByPrimaryKey(long fileEntryTypeId) {
		return fetchByPrimaryKey((Serializable)fileEntryTypeId);
	}

	@Override
	public Map<Serializable, DLFileEntryType> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (CTPersistenceHelperUtil.isProductionMode(DLFileEntryType.class)) {
			return super.fetchByPrimaryKeys(primaryKeys);
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, DLFileEntryType> map =
			new HashMap<Serializable, DLFileEntryType>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			DLFileEntryType dlFileEntryType = fetchByPrimaryKey(primaryKey);

			if (dlFileEntryType != null) {
				map.put(primaryKey, dlFileEntryType);
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

			for (DLFileEntryType dlFileEntryType :
					(List<DLFileEntryType>)query.list()) {

				map.put(dlFileEntryType.getPrimaryKeyObj(), dlFileEntryType);

				cacheResult(dlFileEntryType);
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
	 * Returns all the document library file entry types.
	 *
	 * @return the document library file entry types
	 */
	@Override
	public List<DLFileEntryType> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entry types.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryTypeModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of document library file entry types
	 * @param end the upper bound of the range of document library file entry types (not inclusive)
	 * @return the range of document library file entry types
	 */
	@Override
	public List<DLFileEntryType> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entry types.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryTypeModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of document library file entry types
	 * @param end the upper bound of the range of document library file entry types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of document library file entry types
	 */
	@Override
	public List<DLFileEntryType> findAll(
		int start, int end,
		OrderByComparator<DLFileEntryType> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file entry types.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryTypeModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of document library file entry types
	 * @param end the upper bound of the range of document library file entry types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of document library file entry types
	 */
	@Override
	public List<DLFileEntryType> findAll(
		int start, int end,
		OrderByComparator<DLFileEntryType> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			DLFileEntryType.class);

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

		List<DLFileEntryType> list = null;

		if (useFinderCache && productionMode) {
			list = (List<DLFileEntryType>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_DLFILEENTRYTYPE);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_DLFILEENTRYTYPE;

				sql = sql.concat(DLFileEntryTypeModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<DLFileEntryType>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
	 * Removes all the document library file entry types from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (DLFileEntryType dlFileEntryType : findAll()) {
			remove(dlFileEntryType);
		}
	}

	/**
	 * Returns the number of document library file entry types.
	 *
	 * @return the number of document library file entry types
	 */
	@Override
	public int countAll() {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			DLFileEntryType.class);

		Long count = null;

		if (productionMode) {
			count = (Long)FinderCacheUtil.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY, this);
		}

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_DLFILEENTRYTYPE);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(
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

	/**
	 * Returns the primaryKeys of document library folders associated with the document library file entry type.
	 *
	 * @param pk the primary key of the document library file entry type
	 * @return long[] of the primaryKeys of document library folders associated with the document library file entry type
	 */
	@Override
	public long[] getDLFolderPrimaryKeys(long pk) {
		long[] pks = dlFileEntryTypeToDLFolderTableMapper.getRightPrimaryKeys(
			pk);

		return pks.clone();
	}

	/**
	 * Returns all the document library folders associated with the document library file entry type.
	 *
	 * @param pk the primary key of the document library file entry type
	 * @return the document library folders associated with the document library file entry type
	 */
	@Override
	public List<com.liferay.document.library.kernel.model.DLFolder>
		getDLFolders(long pk) {

		return getDLFolders(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the document library folders associated with the document library file entry type.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryTypeModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the document library file entry type
	 * @param start the lower bound of the range of document library file entry types
	 * @param end the upper bound of the range of document library file entry types (not inclusive)
	 * @return the range of document library folders associated with the document library file entry type
	 */
	@Override
	public List<com.liferay.document.library.kernel.model.DLFolder>
		getDLFolders(long pk, int start, int end) {

		return getDLFolders(pk, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library folders associated with the document library file entry type.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DLFileEntryTypeModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the document library file entry type
	 * @param start the lower bound of the range of document library file entry types
	 * @param end the upper bound of the range of document library file entry types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of document library folders associated with the document library file entry type
	 */
	@Override
	public List<com.liferay.document.library.kernel.model.DLFolder>
		getDLFolders(
			long pk, int start, int end,
			OrderByComparator
				<com.liferay.document.library.kernel.model.DLFolder>
					orderByComparator) {

		return dlFileEntryTypeToDLFolderTableMapper.getRightBaseModels(
			pk, start, end, orderByComparator);
	}

	/**
	 * Returns the number of document library folders associated with the document library file entry type.
	 *
	 * @param pk the primary key of the document library file entry type
	 * @return the number of document library folders associated with the document library file entry type
	 */
	@Override
	public int getDLFoldersSize(long pk) {
		long[] pks = dlFileEntryTypeToDLFolderTableMapper.getRightPrimaryKeys(
			pk);

		return pks.length;
	}

	/**
	 * Returns <code>true</code> if the document library folder is associated with the document library file entry type.
	 *
	 * @param pk the primary key of the document library file entry type
	 * @param dlFolderPK the primary key of the document library folder
	 * @return <code>true</code> if the document library folder is associated with the document library file entry type; <code>false</code> otherwise
	 */
	@Override
	public boolean containsDLFolder(long pk, long dlFolderPK) {
		return dlFileEntryTypeToDLFolderTableMapper.containsTableMapping(
			pk, dlFolderPK);
	}

	/**
	 * Returns <code>true</code> if the document library file entry type has any document library folders associated with it.
	 *
	 * @param pk the primary key of the document library file entry type to check for associations with document library folders
	 * @return <code>true</code> if the document library file entry type has any document library folders associated with it; <code>false</code> otherwise
	 */
	@Override
	public boolean containsDLFolders(long pk) {
		if (getDLFoldersSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the document library file entry type and the document library folder. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the document library file entry type
	 * @param dlFolderPK the primary key of the document library folder
	 */
	@Override
	public void addDLFolder(long pk, long dlFolderPK) {
		DLFileEntryType dlFileEntryType = fetchByPrimaryKey(pk);

		if (dlFileEntryType == null) {
			dlFileEntryTypeToDLFolderTableMapper.addTableMapping(
				CompanyThreadLocal.getCompanyId(), pk, dlFolderPK);
		}
		else {
			dlFileEntryTypeToDLFolderTableMapper.addTableMapping(
				dlFileEntryType.getCompanyId(), pk, dlFolderPK);
		}
	}

	/**
	 * Adds an association between the document library file entry type and the document library folder. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the document library file entry type
	 * @param dlFolder the document library folder
	 */
	@Override
	public void addDLFolder(
		long pk, com.liferay.document.library.kernel.model.DLFolder dlFolder) {

		DLFileEntryType dlFileEntryType = fetchByPrimaryKey(pk);

		if (dlFileEntryType == null) {
			dlFileEntryTypeToDLFolderTableMapper.addTableMapping(
				CompanyThreadLocal.getCompanyId(), pk,
				dlFolder.getPrimaryKey());
		}
		else {
			dlFileEntryTypeToDLFolderTableMapper.addTableMapping(
				dlFileEntryType.getCompanyId(), pk, dlFolder.getPrimaryKey());
		}
	}

	/**
	 * Adds an association between the document library file entry type and the document library folders. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the document library file entry type
	 * @param dlFolderPKs the primary keys of the document library folders
	 */
	@Override
	public void addDLFolders(long pk, long[] dlFolderPKs) {
		long companyId = 0;

		DLFileEntryType dlFileEntryType = fetchByPrimaryKey(pk);

		if (dlFileEntryType == null) {
			companyId = CompanyThreadLocal.getCompanyId();
		}
		else {
			companyId = dlFileEntryType.getCompanyId();
		}

		dlFileEntryTypeToDLFolderTableMapper.addTableMappings(
			companyId, pk, dlFolderPKs);
	}

	/**
	 * Adds an association between the document library file entry type and the document library folders. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the document library file entry type
	 * @param dlFolders the document library folders
	 */
	@Override
	public void addDLFolders(
		long pk,
		List<com.liferay.document.library.kernel.model.DLFolder> dlFolders) {

		addDLFolders(
			pk,
			ListUtil.toLongArray(
				dlFolders,
				com.liferay.document.library.kernel.model.DLFolder.
					FOLDER_ID_ACCESSOR));
	}

	/**
	 * Clears all associations between the document library file entry type and its document library folders. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the document library file entry type to clear the associated document library folders from
	 */
	@Override
	public void clearDLFolders(long pk) {
		dlFileEntryTypeToDLFolderTableMapper.deleteLeftPrimaryKeyTableMappings(
			pk);
	}

	/**
	 * Removes the association between the document library file entry type and the document library folder. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the document library file entry type
	 * @param dlFolderPK the primary key of the document library folder
	 */
	@Override
	public void removeDLFolder(long pk, long dlFolderPK) {
		dlFileEntryTypeToDLFolderTableMapper.deleteTableMapping(pk, dlFolderPK);
	}

	/**
	 * Removes the association between the document library file entry type and the document library folder. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the document library file entry type
	 * @param dlFolder the document library folder
	 */
	@Override
	public void removeDLFolder(
		long pk, com.liferay.document.library.kernel.model.DLFolder dlFolder) {

		dlFileEntryTypeToDLFolderTableMapper.deleteTableMapping(
			pk, dlFolder.getPrimaryKey());
	}

	/**
	 * Removes the association between the document library file entry type and the document library folders. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the document library file entry type
	 * @param dlFolderPKs the primary keys of the document library folders
	 */
	@Override
	public void removeDLFolders(long pk, long[] dlFolderPKs) {
		dlFileEntryTypeToDLFolderTableMapper.deleteTableMappings(
			pk, dlFolderPKs);
	}

	/**
	 * Removes the association between the document library file entry type and the document library folders. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the document library file entry type
	 * @param dlFolders the document library folders
	 */
	@Override
	public void removeDLFolders(
		long pk,
		List<com.liferay.document.library.kernel.model.DLFolder> dlFolders) {

		removeDLFolders(
			pk,
			ListUtil.toLongArray(
				dlFolders,
				com.liferay.document.library.kernel.model.DLFolder.
					FOLDER_ID_ACCESSOR));
	}

	/**
	 * Sets the document library folders associated with the document library file entry type, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the document library file entry type
	 * @param dlFolderPKs the primary keys of the document library folders to be associated with the document library file entry type
	 */
	@Override
	public void setDLFolders(long pk, long[] dlFolderPKs) {
		Set<Long> newDLFolderPKsSet = SetUtil.fromArray(dlFolderPKs);
		Set<Long> oldDLFolderPKsSet = SetUtil.fromArray(
			dlFileEntryTypeToDLFolderTableMapper.getRightPrimaryKeys(pk));

		Set<Long> removeDLFolderPKsSet = new HashSet<Long>(oldDLFolderPKsSet);

		removeDLFolderPKsSet.removeAll(newDLFolderPKsSet);

		dlFileEntryTypeToDLFolderTableMapper.deleteTableMappings(
			pk, ArrayUtil.toLongArray(removeDLFolderPKsSet));

		newDLFolderPKsSet.removeAll(oldDLFolderPKsSet);

		long companyId = 0;

		DLFileEntryType dlFileEntryType = fetchByPrimaryKey(pk);

		if (dlFileEntryType == null) {
			companyId = CompanyThreadLocal.getCompanyId();
		}
		else {
			companyId = dlFileEntryType.getCompanyId();
		}

		dlFileEntryTypeToDLFolderTableMapper.addTableMappings(
			companyId, pk, ArrayUtil.toLongArray(newDLFolderPKsSet));
	}

	/**
	 * Sets the document library folders associated with the document library file entry type, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the document library file entry type
	 * @param dlFolders the document library folders to be associated with the document library file entry type
	 */
	@Override
	public void setDLFolders(
		long pk,
		List<com.liferay.document.library.kernel.model.DLFolder> dlFolders) {

		try {
			long[] dlFolderPKs = new long[dlFolders.size()];

			for (int i = 0; i < dlFolders.size(); i++) {
				com.liferay.document.library.kernel.model.DLFolder dlFolder =
					dlFolders.get(i);

				dlFolderPKs[i] = dlFolder.getPrimaryKey();
			}

			setDLFolders(pk, dlFolderPKs);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
	}

	@Override
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected EntityCache getEntityCache() {
		return EntityCacheUtil.getEntityCache();
	}

	@Override
	protected String getPKDBName() {
		return "fileEntryTypeId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_DLFILEENTRYTYPE;
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
		return DLFileEntryTypeModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "DLFileEntryType";
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
		ctStrictColumnNames.add("dataDefinitionId");
		ctStrictColumnNames.add("fileEntryTypeKey");
		ctStrictColumnNames.add("name");
		ctStrictColumnNames.add("description");
		ctStrictColumnNames.add("lastPublishDate");
		ctStrictColumnNames.add("folders");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK,
			Collections.singleton("fileEntryTypeId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_mappingTableNames.add("DLFileEntryTypes_DLFolders");

		_uniqueIndexColumnNames.add(new String[] {"uuid_", "groupId"});

		_uniqueIndexColumnNames.add(
			new String[] {"groupId", "fileEntryTypeKey"});
	}

	/**
	 * Initializes the document library file entry type persistence.
	 */
	public void afterPropertiesSet() {
		dlFileEntryTypeToDLFolderTableMapper =
			TableMapperFactory.getTableMapper(
				"DLFileEntryTypes_DLFolders", "companyId", "fileEntryTypeId",
				"folderId", this, dlFolderPersistence);

		_finderPathWithPaginationFindAll = new FinderPath(
			DLFileEntryTypeImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			DLFileEntryTypeImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByUuid = new FinderPath(
			DLFileEntryTypeImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			DLFileEntryTypeImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] {String.class.getName()},
			DLFileEntryTypeModelImpl.UUID_COLUMN_BITMASK);

		_finderPathCountByUuid = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid", new String[] {String.class.getName()});

		_finderPathFetchByUUID_G = new FinderPath(
			DLFileEntryTypeImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			DLFileEntryTypeModelImpl.UUID_COLUMN_BITMASK |
			DLFileEntryTypeModelImpl.GROUPID_COLUMN_BITMASK);

		_finderPathCountByUUID_G = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			DLFileEntryTypeImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			DLFileEntryTypeImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			DLFileEntryTypeModelImpl.UUID_COLUMN_BITMASK |
			DLFileEntryTypeModelImpl.COMPANYID_COLUMN_BITMASK);

		_finderPathCountByUuid_C = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByGroupId = new FinderPath(
			DLFileEntryTypeImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByGroupId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByGroupId = new FinderPath(
			DLFileEntryTypeImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] {Long.class.getName()},
			DLFileEntryTypeModelImpl.GROUPID_COLUMN_BITMASK);

		_finderPathCountByGroupId = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByGroupId", new String[] {Long.class.getName()});

		_finderPathWithPaginationCountByGroupId = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"countByGroupId", new String[] {Long.class.getName()});

		_finderPathFetchByG_F = new FinderPath(
			DLFileEntryTypeImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByG_F",
			new String[] {Long.class.getName(), String.class.getName()},
			DLFileEntryTypeModelImpl.GROUPID_COLUMN_BITMASK |
			DLFileEntryTypeModelImpl.FILEENTRYTYPEKEY_COLUMN_BITMASK);

		_finderPathCountByG_F = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_F",
			new String[] {Long.class.getName(), String.class.getName()});
	}

	public void destroy() {
		EntityCacheUtil.removeCache(DLFileEntryTypeImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		TableMapperFactory.removeTableMapper("DLFileEntryTypes_DLFolders");
	}

	@BeanReference(type = DLFolderPersistence.class)
	protected DLFolderPersistence dlFolderPersistence;

	protected TableMapper
		<DLFileEntryType, com.liferay.document.library.kernel.model.DLFolder>
			dlFileEntryTypeToDLFolderTableMapper;

	private static final String _SQL_SELECT_DLFILEENTRYTYPE =
		"SELECT dlFileEntryType FROM DLFileEntryType dlFileEntryType";

	private static final String _SQL_SELECT_DLFILEENTRYTYPE_WHERE =
		"SELECT dlFileEntryType FROM DLFileEntryType dlFileEntryType WHERE ";

	private static final String _SQL_COUNT_DLFILEENTRYTYPE =
		"SELECT COUNT(dlFileEntryType) FROM DLFileEntryType dlFileEntryType";

	private static final String _SQL_COUNT_DLFILEENTRYTYPE_WHERE =
		"SELECT COUNT(dlFileEntryType) FROM DLFileEntryType dlFileEntryType WHERE ";

	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN =
		"dlFileEntryType.fileEntryTypeId";

	private static final String _FILTER_SQL_SELECT_DLFILEENTRYTYPE_WHERE =
		"SELECT DISTINCT {dlFileEntryType.*} FROM DLFileEntryType dlFileEntryType WHERE ";

	private static final String
		_FILTER_SQL_SELECT_DLFILEENTRYTYPE_NO_INLINE_DISTINCT_WHERE_1 =
			"SELECT {DLFileEntryType.*} FROM (SELECT DISTINCT dlFileEntryType.fileEntryTypeId FROM DLFileEntryType dlFileEntryType WHERE ";

	private static final String
		_FILTER_SQL_SELECT_DLFILEENTRYTYPE_NO_INLINE_DISTINCT_WHERE_2 =
			") TEMP_TABLE INNER JOIN DLFileEntryType ON TEMP_TABLE.fileEntryTypeId = DLFileEntryType.fileEntryTypeId";

	private static final String _FILTER_SQL_COUNT_DLFILEENTRYTYPE_WHERE =
		"SELECT COUNT(DISTINCT dlFileEntryType.fileEntryTypeId) AS COUNT_VALUE FROM DLFileEntryType dlFileEntryType WHERE ";

	private static final String _FILTER_ENTITY_ALIAS = "dlFileEntryType";

	private static final String _FILTER_ENTITY_TABLE = "DLFileEntryType";

	private static final String _ORDER_BY_ENTITY_ALIAS = "dlFileEntryType.";

	private static final String _ORDER_BY_ENTITY_TABLE = "DLFileEntryType.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No DLFileEntryType exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No DLFileEntryType exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		DLFileEntryTypePersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

}