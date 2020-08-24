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

package com.liferay.trash.service.persistence.impl;

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
import com.liferay.portal.kernel.service.persistence.change.tracking.helper.CTPersistenceHelper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.trash.exception.NoSuchVersionException;
import com.liferay.trash.model.TrashVersion;
import com.liferay.trash.model.TrashVersionTable;
import com.liferay.trash.model.impl.TrashVersionImpl;
import com.liferay.trash.model.impl.TrashVersionModelImpl;
import com.liferay.trash.service.persistence.TrashVersionPersistence;
import com.liferay.trash.service.persistence.impl.constants.TrashPersistenceConstants;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the trash version service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = TrashVersionPersistence.class)
public class TrashVersionPersistenceImpl
	extends BasePersistenceImpl<TrashVersion>
	implements TrashVersionPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>TrashVersionUtil</code> to access the trash version persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		TrashVersionImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByEntryId;
	private FinderPath _finderPathWithoutPaginationFindByEntryId;
	private FinderPath _finderPathCountByEntryId;

	/**
	 * Returns all the trash versions where entryId = &#63;.
	 *
	 * @param entryId the entry ID
	 * @return the matching trash versions
	 */
	@Override
	public List<TrashVersion> findByEntryId(long entryId) {
		return findByEntryId(
			entryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the trash versions where entryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TrashVersionModelImpl</code>.
	 * </p>
	 *
	 * @param entryId the entry ID
	 * @param start the lower bound of the range of trash versions
	 * @param end the upper bound of the range of trash versions (not inclusive)
	 * @return the range of matching trash versions
	 */
	@Override
	public List<TrashVersion> findByEntryId(long entryId, int start, int end) {
		return findByEntryId(entryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the trash versions where entryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TrashVersionModelImpl</code>.
	 * </p>
	 *
	 * @param entryId the entry ID
	 * @param start the lower bound of the range of trash versions
	 * @param end the upper bound of the range of trash versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching trash versions
	 */
	@Override
	public List<TrashVersion> findByEntryId(
		long entryId, int start, int end,
		OrderByComparator<TrashVersion> orderByComparator) {

		return findByEntryId(entryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the trash versions where entryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TrashVersionModelImpl</code>.
	 * </p>
	 *
	 * @param entryId the entry ID
	 * @param start the lower bound of the range of trash versions
	 * @param end the upper bound of the range of trash versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching trash versions
	 */
	@Override
	public List<TrashVersion> findByEntryId(
		long entryId, int start, int end,
		OrderByComparator<TrashVersion> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			TrashVersion.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByEntryId;
				finderArgs = new Object[] {entryId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByEntryId;
			finderArgs = new Object[] {entryId, start, end, orderByComparator};
		}

		List<TrashVersion> list = null;

		if (useFinderCache && productionMode) {
			list = (List<TrashVersion>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (TrashVersion trashVersion : list) {
					if (entryId != trashVersion.getEntryId()) {
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

			sb.append(_SQL_SELECT_TRASHVERSION_WHERE);

			sb.append(_FINDER_COLUMN_ENTRYID_ENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(TrashVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(entryId);

				list = (List<TrashVersion>)QueryUtil.list(
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
	 * Returns the first trash version in the ordered set where entryId = &#63;.
	 *
	 * @param entryId the entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching trash version
	 * @throws NoSuchVersionException if a matching trash version could not be found
	 */
	@Override
	public TrashVersion findByEntryId_First(
			long entryId, OrderByComparator<TrashVersion> orderByComparator)
		throws NoSuchVersionException {

		TrashVersion trashVersion = fetchByEntryId_First(
			entryId, orderByComparator);

		if (trashVersion != null) {
			return trashVersion;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("entryId=");
		sb.append(entryId);

		sb.append("}");

		throw new NoSuchVersionException(sb.toString());
	}

	/**
	 * Returns the first trash version in the ordered set where entryId = &#63;.
	 *
	 * @param entryId the entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching trash version, or <code>null</code> if a matching trash version could not be found
	 */
	@Override
	public TrashVersion fetchByEntryId_First(
		long entryId, OrderByComparator<TrashVersion> orderByComparator) {

		List<TrashVersion> list = findByEntryId(
			entryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last trash version in the ordered set where entryId = &#63;.
	 *
	 * @param entryId the entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching trash version
	 * @throws NoSuchVersionException if a matching trash version could not be found
	 */
	@Override
	public TrashVersion findByEntryId_Last(
			long entryId, OrderByComparator<TrashVersion> orderByComparator)
		throws NoSuchVersionException {

		TrashVersion trashVersion = fetchByEntryId_Last(
			entryId, orderByComparator);

		if (trashVersion != null) {
			return trashVersion;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("entryId=");
		sb.append(entryId);

		sb.append("}");

		throw new NoSuchVersionException(sb.toString());
	}

	/**
	 * Returns the last trash version in the ordered set where entryId = &#63;.
	 *
	 * @param entryId the entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching trash version, or <code>null</code> if a matching trash version could not be found
	 */
	@Override
	public TrashVersion fetchByEntryId_Last(
		long entryId, OrderByComparator<TrashVersion> orderByComparator) {

		int count = countByEntryId(entryId);

		if (count == 0) {
			return null;
		}

		List<TrashVersion> list = findByEntryId(
			entryId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the trash versions before and after the current trash version in the ordered set where entryId = &#63;.
	 *
	 * @param versionId the primary key of the current trash version
	 * @param entryId the entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next trash version
	 * @throws NoSuchVersionException if a trash version with the primary key could not be found
	 */
	@Override
	public TrashVersion[] findByEntryId_PrevAndNext(
			long versionId, long entryId,
			OrderByComparator<TrashVersion> orderByComparator)
		throws NoSuchVersionException {

		TrashVersion trashVersion = findByPrimaryKey(versionId);

		Session session = null;

		try {
			session = openSession();

			TrashVersion[] array = new TrashVersionImpl[3];

			array[0] = getByEntryId_PrevAndNext(
				session, trashVersion, entryId, orderByComparator, true);

			array[1] = trashVersion;

			array[2] = getByEntryId_PrevAndNext(
				session, trashVersion, entryId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected TrashVersion getByEntryId_PrevAndNext(
		Session session, TrashVersion trashVersion, long entryId,
		OrderByComparator<TrashVersion> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_TRASHVERSION_WHERE);

		sb.append(_FINDER_COLUMN_ENTRYID_ENTRYID_2);

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
			sb.append(TrashVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(entryId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(trashVersion)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<TrashVersion> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the trash versions where entryId = &#63; from the database.
	 *
	 * @param entryId the entry ID
	 */
	@Override
	public void removeByEntryId(long entryId) {
		for (TrashVersion trashVersion :
				findByEntryId(
					entryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(trashVersion);
		}
	}

	/**
	 * Returns the number of trash versions where entryId = &#63;.
	 *
	 * @param entryId the entry ID
	 * @return the number of matching trash versions
	 */
	@Override
	public int countByEntryId(long entryId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			TrashVersion.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByEntryId;

			finderArgs = new Object[] {entryId};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_TRASHVERSION_WHERE);

			sb.append(_FINDER_COLUMN_ENTRYID_ENTRYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(entryId);

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

	private static final String _FINDER_COLUMN_ENTRYID_ENTRYID_2 =
		"trashVersion.entryId = ?";

	private FinderPath _finderPathWithPaginationFindByE_C;
	private FinderPath _finderPathWithoutPaginationFindByE_C;
	private FinderPath _finderPathCountByE_C;

	/**
	 * Returns all the trash versions where entryId = &#63; and classNameId = &#63;.
	 *
	 * @param entryId the entry ID
	 * @param classNameId the class name ID
	 * @return the matching trash versions
	 */
	@Override
	public List<TrashVersion> findByE_C(long entryId, long classNameId) {
		return findByE_C(
			entryId, classNameId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the trash versions where entryId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TrashVersionModelImpl</code>.
	 * </p>
	 *
	 * @param entryId the entry ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of trash versions
	 * @param end the upper bound of the range of trash versions (not inclusive)
	 * @return the range of matching trash versions
	 */
	@Override
	public List<TrashVersion> findByE_C(
		long entryId, long classNameId, int start, int end) {

		return findByE_C(entryId, classNameId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the trash versions where entryId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TrashVersionModelImpl</code>.
	 * </p>
	 *
	 * @param entryId the entry ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of trash versions
	 * @param end the upper bound of the range of trash versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching trash versions
	 */
	@Override
	public List<TrashVersion> findByE_C(
		long entryId, long classNameId, int start, int end,
		OrderByComparator<TrashVersion> orderByComparator) {

		return findByE_C(
			entryId, classNameId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the trash versions where entryId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TrashVersionModelImpl</code>.
	 * </p>
	 *
	 * @param entryId the entry ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of trash versions
	 * @param end the upper bound of the range of trash versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching trash versions
	 */
	@Override
	public List<TrashVersion> findByE_C(
		long entryId, long classNameId, int start, int end,
		OrderByComparator<TrashVersion> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			TrashVersion.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByE_C;
				finderArgs = new Object[] {entryId, classNameId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByE_C;
			finderArgs = new Object[] {
				entryId, classNameId, start, end, orderByComparator
			};
		}

		List<TrashVersion> list = null;

		if (useFinderCache && productionMode) {
			list = (List<TrashVersion>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (TrashVersion trashVersion : list) {
					if ((entryId != trashVersion.getEntryId()) ||
						(classNameId != trashVersion.getClassNameId())) {

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

			sb.append(_SQL_SELECT_TRASHVERSION_WHERE);

			sb.append(_FINDER_COLUMN_E_C_ENTRYID_2);

			sb.append(_FINDER_COLUMN_E_C_CLASSNAMEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(TrashVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(entryId);

				queryPos.add(classNameId);

				list = (List<TrashVersion>)QueryUtil.list(
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
	 * Returns the first trash version in the ordered set where entryId = &#63; and classNameId = &#63;.
	 *
	 * @param entryId the entry ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching trash version
	 * @throws NoSuchVersionException if a matching trash version could not be found
	 */
	@Override
	public TrashVersion findByE_C_First(
			long entryId, long classNameId,
			OrderByComparator<TrashVersion> orderByComparator)
		throws NoSuchVersionException {

		TrashVersion trashVersion = fetchByE_C_First(
			entryId, classNameId, orderByComparator);

		if (trashVersion != null) {
			return trashVersion;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("entryId=");
		sb.append(entryId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append("}");

		throw new NoSuchVersionException(sb.toString());
	}

	/**
	 * Returns the first trash version in the ordered set where entryId = &#63; and classNameId = &#63;.
	 *
	 * @param entryId the entry ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching trash version, or <code>null</code> if a matching trash version could not be found
	 */
	@Override
	public TrashVersion fetchByE_C_First(
		long entryId, long classNameId,
		OrderByComparator<TrashVersion> orderByComparator) {

		List<TrashVersion> list = findByE_C(
			entryId, classNameId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last trash version in the ordered set where entryId = &#63; and classNameId = &#63;.
	 *
	 * @param entryId the entry ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching trash version
	 * @throws NoSuchVersionException if a matching trash version could not be found
	 */
	@Override
	public TrashVersion findByE_C_Last(
			long entryId, long classNameId,
			OrderByComparator<TrashVersion> orderByComparator)
		throws NoSuchVersionException {

		TrashVersion trashVersion = fetchByE_C_Last(
			entryId, classNameId, orderByComparator);

		if (trashVersion != null) {
			return trashVersion;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("entryId=");
		sb.append(entryId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append("}");

		throw new NoSuchVersionException(sb.toString());
	}

	/**
	 * Returns the last trash version in the ordered set where entryId = &#63; and classNameId = &#63;.
	 *
	 * @param entryId the entry ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching trash version, or <code>null</code> if a matching trash version could not be found
	 */
	@Override
	public TrashVersion fetchByE_C_Last(
		long entryId, long classNameId,
		OrderByComparator<TrashVersion> orderByComparator) {

		int count = countByE_C(entryId, classNameId);

		if (count == 0) {
			return null;
		}

		List<TrashVersion> list = findByE_C(
			entryId, classNameId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the trash versions before and after the current trash version in the ordered set where entryId = &#63; and classNameId = &#63;.
	 *
	 * @param versionId the primary key of the current trash version
	 * @param entryId the entry ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next trash version
	 * @throws NoSuchVersionException if a trash version with the primary key could not be found
	 */
	@Override
	public TrashVersion[] findByE_C_PrevAndNext(
			long versionId, long entryId, long classNameId,
			OrderByComparator<TrashVersion> orderByComparator)
		throws NoSuchVersionException {

		TrashVersion trashVersion = findByPrimaryKey(versionId);

		Session session = null;

		try {
			session = openSession();

			TrashVersion[] array = new TrashVersionImpl[3];

			array[0] = getByE_C_PrevAndNext(
				session, trashVersion, entryId, classNameId, orderByComparator,
				true);

			array[1] = trashVersion;

			array[2] = getByE_C_PrevAndNext(
				session, trashVersion, entryId, classNameId, orderByComparator,
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

	protected TrashVersion getByE_C_PrevAndNext(
		Session session, TrashVersion trashVersion, long entryId,
		long classNameId, OrderByComparator<TrashVersion> orderByComparator,
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

		sb.append(_SQL_SELECT_TRASHVERSION_WHERE);

		sb.append(_FINDER_COLUMN_E_C_ENTRYID_2);

		sb.append(_FINDER_COLUMN_E_C_CLASSNAMEID_2);

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
			sb.append(TrashVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(entryId);

		queryPos.add(classNameId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(trashVersion)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<TrashVersion> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the trash versions where entryId = &#63; and classNameId = &#63; from the database.
	 *
	 * @param entryId the entry ID
	 * @param classNameId the class name ID
	 */
	@Override
	public void removeByE_C(long entryId, long classNameId) {
		for (TrashVersion trashVersion :
				findByE_C(
					entryId, classNameId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(trashVersion);
		}
	}

	/**
	 * Returns the number of trash versions where entryId = &#63; and classNameId = &#63;.
	 *
	 * @param entryId the entry ID
	 * @param classNameId the class name ID
	 * @return the number of matching trash versions
	 */
	@Override
	public int countByE_C(long entryId, long classNameId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			TrashVersion.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByE_C;

			finderArgs = new Object[] {entryId, classNameId};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_TRASHVERSION_WHERE);

			sb.append(_FINDER_COLUMN_E_C_ENTRYID_2);

			sb.append(_FINDER_COLUMN_E_C_CLASSNAMEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(entryId);

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

	private static final String _FINDER_COLUMN_E_C_ENTRYID_2 =
		"trashVersion.entryId = ? AND ";

	private static final String _FINDER_COLUMN_E_C_CLASSNAMEID_2 =
		"trashVersion.classNameId = ?";

	private FinderPath _finderPathFetchByC_C;
	private FinderPath _finderPathCountByC_C;

	/**
	 * Returns the trash version where classNameId = &#63; and classPK = &#63; or throws a <code>NoSuchVersionException</code> if it could not be found.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching trash version
	 * @throws NoSuchVersionException if a matching trash version could not be found
	 */
	@Override
	public TrashVersion findByC_C(long classNameId, long classPK)
		throws NoSuchVersionException {

		TrashVersion trashVersion = fetchByC_C(classNameId, classPK);

		if (trashVersion == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("classNameId=");
			sb.append(classNameId);

			sb.append(", classPK=");
			sb.append(classPK);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchVersionException(sb.toString());
		}

		return trashVersion;
	}

	/**
	 * Returns the trash version where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching trash version, or <code>null</code> if a matching trash version could not be found
	 */
	@Override
	public TrashVersion fetchByC_C(long classNameId, long classPK) {
		return fetchByC_C(classNameId, classPK, true);
	}

	/**
	 * Returns the trash version where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching trash version, or <code>null</code> if a matching trash version could not be found
	 */
	@Override
	public TrashVersion fetchByC_C(
		long classNameId, long classPK, boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			TrashVersion.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {classNameId, classPK};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(
				_finderPathFetchByC_C, finderArgs, this);
		}

		if (result instanceof TrashVersion) {
			TrashVersion trashVersion = (TrashVersion)result;

			if ((classNameId != trashVersion.getClassNameId()) ||
				(classPK != trashVersion.getClassPK())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_TRASHVERSION_WHERE);

			sb.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				List<TrashVersion> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByC_C, finderArgs, list);
					}
				}
				else {
					TrashVersion trashVersion = list.get(0);

					result = trashVersion;

					cacheResult(trashVersion);
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
			return (TrashVersion)result;
		}
	}

	/**
	 * Removes the trash version where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the trash version that was removed
	 */
	@Override
	public TrashVersion removeByC_C(long classNameId, long classPK)
		throws NoSuchVersionException {

		TrashVersion trashVersion = findByC_C(classNameId, classPK);

		return remove(trashVersion);
	}

	/**
	 * Returns the number of trash versions where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching trash versions
	 */
	@Override
	public int countByC_C(long classNameId, long classPK) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			TrashVersion.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByC_C;

			finderArgs = new Object[] {classNameId, classPK};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_TRASHVERSION_WHERE);

			sb.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

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

	private static final String _FINDER_COLUMN_C_C_CLASSNAMEID_2 =
		"trashVersion.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_CLASSPK_2 =
		"trashVersion.classPK = ?";

	public TrashVersionPersistenceImpl() {
		setModelClass(TrashVersion.class);

		setModelImplClass(TrashVersionImpl.class);
		setModelPKClass(long.class);

		setTable(TrashVersionTable.INSTANCE);
	}

	/**
	 * Caches the trash version in the entity cache if it is enabled.
	 *
	 * @param trashVersion the trash version
	 */
	@Override
	public void cacheResult(TrashVersion trashVersion) {
		if (trashVersion.getCtCollectionId() != 0) {
			return;
		}

		entityCache.putResult(
			TrashVersionImpl.class, trashVersion.getPrimaryKey(), trashVersion);

		finderCache.putResult(
			_finderPathFetchByC_C,
			new Object[] {
				trashVersion.getClassNameId(), trashVersion.getClassPK()
			},
			trashVersion);
	}

	/**
	 * Caches the trash versions in the entity cache if it is enabled.
	 *
	 * @param trashVersions the trash versions
	 */
	@Override
	public void cacheResult(List<TrashVersion> trashVersions) {
		for (TrashVersion trashVersion : trashVersions) {
			if (trashVersion.getCtCollectionId() != 0) {
				continue;
			}

			if (entityCache.getResult(
					TrashVersionImpl.class, trashVersion.getPrimaryKey()) ==
						null) {

				cacheResult(trashVersion);
			}
		}
	}

	/**
	 * Clears the cache for all trash versions.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(TrashVersionImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the trash version.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(TrashVersion trashVersion) {
		entityCache.removeResult(
			TrashVersionImpl.class, trashVersion.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((TrashVersionModelImpl)trashVersion, true);
	}

	@Override
	public void clearCache(List<TrashVersion> trashVersions) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (TrashVersion trashVersion : trashVersions) {
			entityCache.removeResult(
				TrashVersionImpl.class, trashVersion.getPrimaryKey());

			clearUniqueFindersCache((TrashVersionModelImpl)trashVersion, true);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(TrashVersionImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		TrashVersionModelImpl trashVersionModelImpl) {

		Object[] args = new Object[] {
			trashVersionModelImpl.getClassNameId(),
			trashVersionModelImpl.getClassPK()
		};

		finderCache.putResult(
			_finderPathCountByC_C, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByC_C, args, trashVersionModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		TrashVersionModelImpl trashVersionModelImpl, boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				trashVersionModelImpl.getClassNameId(),
				trashVersionModelImpl.getClassPK()
			};

			finderCache.removeResult(_finderPathCountByC_C, args);
			finderCache.removeResult(_finderPathFetchByC_C, args);
		}

		if ((trashVersionModelImpl.getColumnBitmask() &
			 _finderPathFetchByC_C.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				trashVersionModelImpl.getColumnOriginalValue("classNameId"),
				trashVersionModelImpl.getColumnOriginalValue("classPK")
			};

			finderCache.removeResult(_finderPathCountByC_C, args);
			finderCache.removeResult(_finderPathFetchByC_C, args);
		}
	}

	/**
	 * Creates a new trash version with the primary key. Does not add the trash version to the database.
	 *
	 * @param versionId the primary key for the new trash version
	 * @return the new trash version
	 */
	@Override
	public TrashVersion create(long versionId) {
		TrashVersion trashVersion = new TrashVersionImpl();

		trashVersion.setNew(true);
		trashVersion.setPrimaryKey(versionId);

		trashVersion.setCompanyId(CompanyThreadLocal.getCompanyId());

		return trashVersion;
	}

	/**
	 * Removes the trash version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param versionId the primary key of the trash version
	 * @return the trash version that was removed
	 * @throws NoSuchVersionException if a trash version with the primary key could not be found
	 */
	@Override
	public TrashVersion remove(long versionId) throws NoSuchVersionException {
		return remove((Serializable)versionId);
	}

	/**
	 * Removes the trash version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the trash version
	 * @return the trash version that was removed
	 * @throws NoSuchVersionException if a trash version with the primary key could not be found
	 */
	@Override
	public TrashVersion remove(Serializable primaryKey)
		throws NoSuchVersionException {

		Session session = null;

		try {
			session = openSession();

			TrashVersion trashVersion = (TrashVersion)session.get(
				TrashVersionImpl.class, primaryKey);

			if (trashVersion == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchVersionException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(trashVersion);
		}
		catch (NoSuchVersionException noSuchEntityException) {
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
	protected TrashVersion removeImpl(TrashVersion trashVersion) {
		if (!ctPersistenceHelper.isRemove(trashVersion)) {
			return trashVersion;
		}

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(trashVersion)) {
				trashVersion = (TrashVersion)session.get(
					TrashVersionImpl.class, trashVersion.getPrimaryKeyObj());
			}

			if (trashVersion != null) {
				session.delete(trashVersion);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (trashVersion != null) {
			clearCache(trashVersion);
		}

		return trashVersion;
	}

	@Override
	public TrashVersion updateImpl(TrashVersion trashVersion) {
		boolean isNew = trashVersion.isNew();

		if (!(trashVersion instanceof TrashVersionModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(trashVersion.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					trashVersion);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in trashVersion proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom TrashVersion implementation " +
					trashVersion.getClass());
		}

		TrashVersionModelImpl trashVersionModelImpl =
			(TrashVersionModelImpl)trashVersion;

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(trashVersion)) {
				if (!isNew) {
					session.evict(
						TrashVersionImpl.class,
						trashVersion.getPrimaryKeyObj());
				}

				session.save(trashVersion);

				trashVersion.setNew(false);
			}
			else {
				trashVersion = (TrashVersion)session.merge(trashVersion);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (trashVersion.getCtCollectionId() != 0) {
			trashVersion.resetOriginalValues();

			return trashVersion;
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew) {
			Object[] args = new Object[] {trashVersionModelImpl.getEntryId()};

			finderCache.removeResult(_finderPathCountByEntryId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByEntryId, args);

			args = new Object[] {
				trashVersionModelImpl.getEntryId(),
				trashVersionModelImpl.getClassNameId()
			};

			finderCache.removeResult(_finderPathCountByE_C, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByE_C, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((trashVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByEntryId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					trashVersionModelImpl.getColumnOriginalValue("entryId")
				};

				finderCache.removeResult(_finderPathCountByEntryId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByEntryId, args);

				args = new Object[] {trashVersionModelImpl.getEntryId()};

				finderCache.removeResult(_finderPathCountByEntryId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByEntryId, args);
			}

			if ((trashVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByE_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					trashVersionModelImpl.getColumnOriginalValue("entryId"),
					trashVersionModelImpl.getColumnOriginalValue("classNameId")
				};

				finderCache.removeResult(_finderPathCountByE_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByE_C, args);

				args = new Object[] {
					trashVersionModelImpl.getEntryId(),
					trashVersionModelImpl.getClassNameId()
				};

				finderCache.removeResult(_finderPathCountByE_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByE_C, args);
			}
		}

		entityCache.putResult(
			TrashVersionImpl.class, trashVersion.getPrimaryKey(), trashVersion,
			false);

		clearUniqueFindersCache(trashVersionModelImpl, false);
		cacheUniqueFindersCache(trashVersionModelImpl);

		trashVersion.resetOriginalValues();

		return trashVersion;
	}

	/**
	 * Returns the trash version with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the trash version
	 * @return the trash version
	 * @throws NoSuchVersionException if a trash version with the primary key could not be found
	 */
	@Override
	public TrashVersion findByPrimaryKey(Serializable primaryKey)
		throws NoSuchVersionException {

		TrashVersion trashVersion = fetchByPrimaryKey(primaryKey);

		if (trashVersion == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchVersionException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return trashVersion;
	}

	/**
	 * Returns the trash version with the primary key or throws a <code>NoSuchVersionException</code> if it could not be found.
	 *
	 * @param versionId the primary key of the trash version
	 * @return the trash version
	 * @throws NoSuchVersionException if a trash version with the primary key could not be found
	 */
	@Override
	public TrashVersion findByPrimaryKey(long versionId)
		throws NoSuchVersionException {

		return findByPrimaryKey((Serializable)versionId);
	}

	/**
	 * Returns the trash version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the trash version
	 * @return the trash version, or <code>null</code> if a trash version with the primary key could not be found
	 */
	@Override
	public TrashVersion fetchByPrimaryKey(Serializable primaryKey) {
		if (ctPersistenceHelper.isProductionMode(TrashVersion.class)) {
			return super.fetchByPrimaryKey(primaryKey);
		}

		TrashVersion trashVersion = null;

		Session session = null;

		try {
			session = openSession();

			trashVersion = (TrashVersion)session.get(
				TrashVersionImpl.class, primaryKey);

			if (trashVersion != null) {
				cacheResult(trashVersion);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return trashVersion;
	}

	/**
	 * Returns the trash version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param versionId the primary key of the trash version
	 * @return the trash version, or <code>null</code> if a trash version with the primary key could not be found
	 */
	@Override
	public TrashVersion fetchByPrimaryKey(long versionId) {
		return fetchByPrimaryKey((Serializable)versionId);
	}

	@Override
	public Map<Serializable, TrashVersion> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (ctPersistenceHelper.isProductionMode(TrashVersion.class)) {
			return super.fetchByPrimaryKeys(primaryKeys);
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, TrashVersion> map =
			new HashMap<Serializable, TrashVersion>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			TrashVersion trashVersion = fetchByPrimaryKey(primaryKey);

			if (trashVersion != null) {
				map.put(primaryKey, trashVersion);
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

			for (TrashVersion trashVersion : (List<TrashVersion>)query.list()) {
				map.put(trashVersion.getPrimaryKeyObj(), trashVersion);

				cacheResult(trashVersion);
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
	 * Returns all the trash versions.
	 *
	 * @return the trash versions
	 */
	@Override
	public List<TrashVersion> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the trash versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TrashVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of trash versions
	 * @param end the upper bound of the range of trash versions (not inclusive)
	 * @return the range of trash versions
	 */
	@Override
	public List<TrashVersion> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the trash versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TrashVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of trash versions
	 * @param end the upper bound of the range of trash versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of trash versions
	 */
	@Override
	public List<TrashVersion> findAll(
		int start, int end, OrderByComparator<TrashVersion> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the trash versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>TrashVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of trash versions
	 * @param end the upper bound of the range of trash versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of trash versions
	 */
	@Override
	public List<TrashVersion> findAll(
		int start, int end, OrderByComparator<TrashVersion> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			TrashVersion.class);

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

		List<TrashVersion> list = null;

		if (useFinderCache && productionMode) {
			list = (List<TrashVersion>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_TRASHVERSION);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_TRASHVERSION;

				sql = sql.concat(TrashVersionModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<TrashVersion>)QueryUtil.list(
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
	 * Removes all the trash versions from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (TrashVersion trashVersion : findAll()) {
			remove(trashVersion);
		}
	}

	/**
	 * Returns the number of trash versions.
	 *
	 * @return the number of trash versions
	 */
	@Override
	public int countAll() {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			TrashVersion.class);

		Long count = null;

		if (productionMode) {
			count = (Long)finderCache.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY, this);
		}

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_TRASHVERSION);

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
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "versionId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_TRASHVERSION;
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
		return TrashVersionModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "TrashVersion";
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
		ctStrictColumnNames.add("companyId");
		ctStrictColumnNames.add("entryId");
		ctStrictColumnNames.add("classNameId");
		ctStrictColumnNames.add("classPK");
		ctStrictColumnNames.add("typeSettings");
		ctStrictColumnNames.add("status");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK, Collections.singleton("versionId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(new String[] {"classNameId", "classPK"});
	}

	/**
	 * Initializes the trash version persistence.
	 */
	@Activate
	public void activate() {
		_finderPathWithPaginationFindAll = new FinderPath(
			TrashVersionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			TrashVersionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findAll", new String[0]);

		_finderPathCountAll = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByEntryId = new FinderPath(
			TrashVersionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByEntryId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByEntryId = new FinderPath(
			TrashVersionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByEntryId", new String[] {Long.class.getName()},
			TrashVersionModelImpl.getColumnBitmask("entryId"));

		_finderPathCountByEntryId = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByEntryId", new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByE_C = new FinderPath(
			TrashVersionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByE_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByE_C = new FinderPath(
			TrashVersionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByE_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			TrashVersionModelImpl.getColumnBitmask("entryId") |
			TrashVersionModelImpl.getColumnBitmask("classNameId"));

		_finderPathCountByE_C = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByE_C",
			new String[] {Long.class.getName(), Long.class.getName()});

		_finderPathFetchByC_C = new FinderPath(
			TrashVersionImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByC_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			TrashVersionModelImpl.getColumnBitmask("classNameId") |
			TrashVersionModelImpl.getColumnBitmask("classPK"));

		_finderPathCountByC_C = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C",
			new String[] {Long.class.getName(), Long.class.getName()});
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(TrashVersionImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	@Reference(
		target = TrashPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = TrashPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = TrashPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
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

	private static final String _SQL_SELECT_TRASHVERSION =
		"SELECT trashVersion FROM TrashVersion trashVersion";

	private static final String _SQL_SELECT_TRASHVERSION_WHERE =
		"SELECT trashVersion FROM TrashVersion trashVersion WHERE ";

	private static final String _SQL_COUNT_TRASHVERSION =
		"SELECT COUNT(trashVersion) FROM TrashVersion trashVersion";

	private static final String _SQL_COUNT_TRASHVERSION_WHERE =
		"SELECT COUNT(trashVersion) FROM TrashVersion trashVersion WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "trashVersion.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No TrashVersion exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No TrashVersion exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		TrashVersionPersistenceImpl.class);

	static {
		try {
			Class.forName(TrashPersistenceConstants.class.getName());
		}
		catch (ClassNotFoundException classNotFoundException) {
			throw new ExceptionInInitializerError(classNotFoundException);
		}
	}

}