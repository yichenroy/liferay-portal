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

package com.liferay.depot.service.persistence.impl;

import com.liferay.depot.exception.NoSuchAppCustomizationException;
import com.liferay.depot.model.DepotAppCustomization;
import com.liferay.depot.model.DepotAppCustomizationTable;
import com.liferay.depot.model.impl.DepotAppCustomizationImpl;
import com.liferay.depot.model.impl.DepotAppCustomizationModelImpl;
import com.liferay.depot.service.persistence.DepotAppCustomizationPersistence;
import com.liferay.depot.service.persistence.impl.constants.DepotPersistenceConstants;
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
import com.liferay.portal.kernel.util.StringUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
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
 * The persistence implementation for the depot app customization service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = DepotAppCustomizationPersistence.class)
public class DepotAppCustomizationPersistenceImpl
	extends BasePersistenceImpl<DepotAppCustomization>
	implements DepotAppCustomizationPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>DepotAppCustomizationUtil</code> to access the depot app customization persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		DepotAppCustomizationImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByDepotEntryId;
	private FinderPath _finderPathWithoutPaginationFindByDepotEntryId;
	private FinderPath _finderPathCountByDepotEntryId;

	/**
	 * Returns all the depot app customizations where depotEntryId = &#63;.
	 *
	 * @param depotEntryId the depot entry ID
	 * @return the matching depot app customizations
	 */
	@Override
	public List<DepotAppCustomization> findByDepotEntryId(long depotEntryId) {
		return findByDepotEntryId(
			depotEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the depot app customizations where depotEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DepotAppCustomizationModelImpl</code>.
	 * </p>
	 *
	 * @param depotEntryId the depot entry ID
	 * @param start the lower bound of the range of depot app customizations
	 * @param end the upper bound of the range of depot app customizations (not inclusive)
	 * @return the range of matching depot app customizations
	 */
	@Override
	public List<DepotAppCustomization> findByDepotEntryId(
		long depotEntryId, int start, int end) {

		return findByDepotEntryId(depotEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the depot app customizations where depotEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DepotAppCustomizationModelImpl</code>.
	 * </p>
	 *
	 * @param depotEntryId the depot entry ID
	 * @param start the lower bound of the range of depot app customizations
	 * @param end the upper bound of the range of depot app customizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching depot app customizations
	 */
	@Override
	public List<DepotAppCustomization> findByDepotEntryId(
		long depotEntryId, int start, int end,
		OrderByComparator<DepotAppCustomization> orderByComparator) {

		return findByDepotEntryId(
			depotEntryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the depot app customizations where depotEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DepotAppCustomizationModelImpl</code>.
	 * </p>
	 *
	 * @param depotEntryId the depot entry ID
	 * @param start the lower bound of the range of depot app customizations
	 * @param end the upper bound of the range of depot app customizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching depot app customizations
	 */
	@Override
	public List<DepotAppCustomization> findByDepotEntryId(
		long depotEntryId, int start, int end,
		OrderByComparator<DepotAppCustomization> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByDepotEntryId;
				finderArgs = new Object[] {depotEntryId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByDepotEntryId;
			finderArgs = new Object[] {
				depotEntryId, start, end, orderByComparator
			};
		}

		List<DepotAppCustomization> list = null;

		if (useFinderCache) {
			list = (List<DepotAppCustomization>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DepotAppCustomization depotAppCustomization : list) {
					if (depotEntryId !=
							depotAppCustomization.getDepotEntryId()) {

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

			sb.append(_SQL_SELECT_DEPOTAPPCUSTOMIZATION_WHERE);

			sb.append(_FINDER_COLUMN_DEPOTENTRYID_DEPOTENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(DepotAppCustomizationModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(depotEntryId);

				list = (List<DepotAppCustomization>)QueryUtil.list(
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
	 * Returns the first depot app customization in the ordered set where depotEntryId = &#63;.
	 *
	 * @param depotEntryId the depot entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching depot app customization
	 * @throws NoSuchAppCustomizationException if a matching depot app customization could not be found
	 */
	@Override
	public DepotAppCustomization findByDepotEntryId_First(
			long depotEntryId,
			OrderByComparator<DepotAppCustomization> orderByComparator)
		throws NoSuchAppCustomizationException {

		DepotAppCustomization depotAppCustomization = fetchByDepotEntryId_First(
			depotEntryId, orderByComparator);

		if (depotAppCustomization != null) {
			return depotAppCustomization;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("depotEntryId=");
		sb.append(depotEntryId);

		sb.append("}");

		throw new NoSuchAppCustomizationException(sb.toString());
	}

	/**
	 * Returns the first depot app customization in the ordered set where depotEntryId = &#63;.
	 *
	 * @param depotEntryId the depot entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching depot app customization, or <code>null</code> if a matching depot app customization could not be found
	 */
	@Override
	public DepotAppCustomization fetchByDepotEntryId_First(
		long depotEntryId,
		OrderByComparator<DepotAppCustomization> orderByComparator) {

		List<DepotAppCustomization> list = findByDepotEntryId(
			depotEntryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last depot app customization in the ordered set where depotEntryId = &#63;.
	 *
	 * @param depotEntryId the depot entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching depot app customization
	 * @throws NoSuchAppCustomizationException if a matching depot app customization could not be found
	 */
	@Override
	public DepotAppCustomization findByDepotEntryId_Last(
			long depotEntryId,
			OrderByComparator<DepotAppCustomization> orderByComparator)
		throws NoSuchAppCustomizationException {

		DepotAppCustomization depotAppCustomization = fetchByDepotEntryId_Last(
			depotEntryId, orderByComparator);

		if (depotAppCustomization != null) {
			return depotAppCustomization;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("depotEntryId=");
		sb.append(depotEntryId);

		sb.append("}");

		throw new NoSuchAppCustomizationException(sb.toString());
	}

	/**
	 * Returns the last depot app customization in the ordered set where depotEntryId = &#63;.
	 *
	 * @param depotEntryId the depot entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching depot app customization, or <code>null</code> if a matching depot app customization could not be found
	 */
	@Override
	public DepotAppCustomization fetchByDepotEntryId_Last(
		long depotEntryId,
		OrderByComparator<DepotAppCustomization> orderByComparator) {

		int count = countByDepotEntryId(depotEntryId);

		if (count == 0) {
			return null;
		}

		List<DepotAppCustomization> list = findByDepotEntryId(
			depotEntryId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the depot app customizations before and after the current depot app customization in the ordered set where depotEntryId = &#63;.
	 *
	 * @param depotAppCustomizationId the primary key of the current depot app customization
	 * @param depotEntryId the depot entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next depot app customization
	 * @throws NoSuchAppCustomizationException if a depot app customization with the primary key could not be found
	 */
	@Override
	public DepotAppCustomization[] findByDepotEntryId_PrevAndNext(
			long depotAppCustomizationId, long depotEntryId,
			OrderByComparator<DepotAppCustomization> orderByComparator)
		throws NoSuchAppCustomizationException {

		DepotAppCustomization depotAppCustomization = findByPrimaryKey(
			depotAppCustomizationId);

		Session session = null;

		try {
			session = openSession();

			DepotAppCustomization[] array = new DepotAppCustomizationImpl[3];

			array[0] = getByDepotEntryId_PrevAndNext(
				session, depotAppCustomization, depotEntryId, orderByComparator,
				true);

			array[1] = depotAppCustomization;

			array[2] = getByDepotEntryId_PrevAndNext(
				session, depotAppCustomization, depotEntryId, orderByComparator,
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

	protected DepotAppCustomization getByDepotEntryId_PrevAndNext(
		Session session, DepotAppCustomization depotAppCustomization,
		long depotEntryId,
		OrderByComparator<DepotAppCustomization> orderByComparator,
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

		sb.append(_SQL_SELECT_DEPOTAPPCUSTOMIZATION_WHERE);

		sb.append(_FINDER_COLUMN_DEPOTENTRYID_DEPOTENTRYID_2);

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
			sb.append(DepotAppCustomizationModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(depotEntryId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						depotAppCustomization)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<DepotAppCustomization> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the depot app customizations where depotEntryId = &#63; from the database.
	 *
	 * @param depotEntryId the depot entry ID
	 */
	@Override
	public void removeByDepotEntryId(long depotEntryId) {
		for (DepotAppCustomization depotAppCustomization :
				findByDepotEntryId(
					depotEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(depotAppCustomization);
		}
	}

	/**
	 * Returns the number of depot app customizations where depotEntryId = &#63;.
	 *
	 * @param depotEntryId the depot entry ID
	 * @return the number of matching depot app customizations
	 */
	@Override
	public int countByDepotEntryId(long depotEntryId) {
		FinderPath finderPath = _finderPathCountByDepotEntryId;

		Object[] finderArgs = new Object[] {depotEntryId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_DEPOTAPPCUSTOMIZATION_WHERE);

			sb.append(_FINDER_COLUMN_DEPOTENTRYID_DEPOTENTRYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(depotEntryId);

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

	private static final String _FINDER_COLUMN_DEPOTENTRYID_DEPOTENTRYID_2 =
		"depotAppCustomization.depotEntryId = ?";

	private FinderPath _finderPathFetchByD_E;
	private FinderPath _finderPathCountByD_E;

	/**
	 * Returns the depot app customization where depotEntryId = &#63; and enabled = &#63; or throws a <code>NoSuchAppCustomizationException</code> if it could not be found.
	 *
	 * @param depotEntryId the depot entry ID
	 * @param enabled the enabled
	 * @return the matching depot app customization
	 * @throws NoSuchAppCustomizationException if a matching depot app customization could not be found
	 */
	@Override
	public DepotAppCustomization findByD_E(long depotEntryId, boolean enabled)
		throws NoSuchAppCustomizationException {

		DepotAppCustomization depotAppCustomization = fetchByD_E(
			depotEntryId, enabled);

		if (depotAppCustomization == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("depotEntryId=");
			sb.append(depotEntryId);

			sb.append(", enabled=");
			sb.append(enabled);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchAppCustomizationException(sb.toString());
		}

		return depotAppCustomization;
	}

	/**
	 * Returns the depot app customization where depotEntryId = &#63; and enabled = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param depotEntryId the depot entry ID
	 * @param enabled the enabled
	 * @return the matching depot app customization, or <code>null</code> if a matching depot app customization could not be found
	 */
	@Override
	public DepotAppCustomization fetchByD_E(
		long depotEntryId, boolean enabled) {

		return fetchByD_E(depotEntryId, enabled, true);
	}

	/**
	 * Returns the depot app customization where depotEntryId = &#63; and enabled = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param depotEntryId the depot entry ID
	 * @param enabled the enabled
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching depot app customization, or <code>null</code> if a matching depot app customization could not be found
	 */
	@Override
	public DepotAppCustomization fetchByD_E(
		long depotEntryId, boolean enabled, boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {depotEntryId, enabled};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByD_E, finderArgs, this);
		}

		if (result instanceof DepotAppCustomization) {
			DepotAppCustomization depotAppCustomization =
				(DepotAppCustomization)result;

			if ((depotEntryId != depotAppCustomization.getDepotEntryId()) ||
				(enabled != depotAppCustomization.isEnabled())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_DEPOTAPPCUSTOMIZATION_WHERE);

			sb.append(_FINDER_COLUMN_D_E_DEPOTENTRYID_2);

			sb.append(_FINDER_COLUMN_D_E_ENABLED_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(depotEntryId);

				queryPos.add(enabled);

				List<DepotAppCustomization> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByD_E, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {
									depotEntryId, enabled
								};
							}

							_log.warn(
								"DepotAppCustomizationPersistenceImpl.fetchByD_E(long, boolean, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					DepotAppCustomization depotAppCustomization = list.get(0);

					result = depotAppCustomization;

					cacheResult(depotAppCustomization);
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
			return (DepotAppCustomization)result;
		}
	}

	/**
	 * Removes the depot app customization where depotEntryId = &#63; and enabled = &#63; from the database.
	 *
	 * @param depotEntryId the depot entry ID
	 * @param enabled the enabled
	 * @return the depot app customization that was removed
	 */
	@Override
	public DepotAppCustomization removeByD_E(long depotEntryId, boolean enabled)
		throws NoSuchAppCustomizationException {

		DepotAppCustomization depotAppCustomization = findByD_E(
			depotEntryId, enabled);

		return remove(depotAppCustomization);
	}

	/**
	 * Returns the number of depot app customizations where depotEntryId = &#63; and enabled = &#63;.
	 *
	 * @param depotEntryId the depot entry ID
	 * @param enabled the enabled
	 * @return the number of matching depot app customizations
	 */
	@Override
	public int countByD_E(long depotEntryId, boolean enabled) {
		FinderPath finderPath = _finderPathCountByD_E;

		Object[] finderArgs = new Object[] {depotEntryId, enabled};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_DEPOTAPPCUSTOMIZATION_WHERE);

			sb.append(_FINDER_COLUMN_D_E_DEPOTENTRYID_2);

			sb.append(_FINDER_COLUMN_D_E_ENABLED_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(depotEntryId);

				queryPos.add(enabled);

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

	private static final String _FINDER_COLUMN_D_E_DEPOTENTRYID_2 =
		"depotAppCustomization.depotEntryId = ? AND ";

	private static final String _FINDER_COLUMN_D_E_ENABLED_2 =
		"depotAppCustomization.enabled = ?";

	private FinderPath _finderPathFetchByD_P;
	private FinderPath _finderPathCountByD_P;

	/**
	 * Returns the depot app customization where depotEntryId = &#63; and portletId = &#63; or throws a <code>NoSuchAppCustomizationException</code> if it could not be found.
	 *
	 * @param depotEntryId the depot entry ID
	 * @param portletId the portlet ID
	 * @return the matching depot app customization
	 * @throws NoSuchAppCustomizationException if a matching depot app customization could not be found
	 */
	@Override
	public DepotAppCustomization findByD_P(long depotEntryId, String portletId)
		throws NoSuchAppCustomizationException {

		DepotAppCustomization depotAppCustomization = fetchByD_P(
			depotEntryId, portletId);

		if (depotAppCustomization == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("depotEntryId=");
			sb.append(depotEntryId);

			sb.append(", portletId=");
			sb.append(portletId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchAppCustomizationException(sb.toString());
		}

		return depotAppCustomization;
	}

	/**
	 * Returns the depot app customization where depotEntryId = &#63; and portletId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param depotEntryId the depot entry ID
	 * @param portletId the portlet ID
	 * @return the matching depot app customization, or <code>null</code> if a matching depot app customization could not be found
	 */
	@Override
	public DepotAppCustomization fetchByD_P(
		long depotEntryId, String portletId) {

		return fetchByD_P(depotEntryId, portletId, true);
	}

	/**
	 * Returns the depot app customization where depotEntryId = &#63; and portletId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param depotEntryId the depot entry ID
	 * @param portletId the portlet ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching depot app customization, or <code>null</code> if a matching depot app customization could not be found
	 */
	@Override
	public DepotAppCustomization fetchByD_P(
		long depotEntryId, String portletId, boolean useFinderCache) {

		portletId = Objects.toString(portletId, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {depotEntryId, portletId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByD_P, finderArgs, this);
		}

		if (result instanceof DepotAppCustomization) {
			DepotAppCustomization depotAppCustomization =
				(DepotAppCustomization)result;

			if ((depotEntryId != depotAppCustomization.getDepotEntryId()) ||
				!Objects.equals(
					portletId, depotAppCustomization.getPortletId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_DEPOTAPPCUSTOMIZATION_WHERE);

			sb.append(_FINDER_COLUMN_D_P_DEPOTENTRYID_2);

			boolean bindPortletId = false;

			if (portletId.isEmpty()) {
				sb.append(_FINDER_COLUMN_D_P_PORTLETID_3);
			}
			else {
				bindPortletId = true;

				sb.append(_FINDER_COLUMN_D_P_PORTLETID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(depotEntryId);

				if (bindPortletId) {
					queryPos.add(portletId);
				}

				List<DepotAppCustomization> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByD_P, finderArgs, list);
					}
				}
				else {
					DepotAppCustomization depotAppCustomization = list.get(0);

					result = depotAppCustomization;

					cacheResult(depotAppCustomization);
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
			return (DepotAppCustomization)result;
		}
	}

	/**
	 * Removes the depot app customization where depotEntryId = &#63; and portletId = &#63; from the database.
	 *
	 * @param depotEntryId the depot entry ID
	 * @param portletId the portlet ID
	 * @return the depot app customization that was removed
	 */
	@Override
	public DepotAppCustomization removeByD_P(
			long depotEntryId, String portletId)
		throws NoSuchAppCustomizationException {

		DepotAppCustomization depotAppCustomization = findByD_P(
			depotEntryId, portletId);

		return remove(depotAppCustomization);
	}

	/**
	 * Returns the number of depot app customizations where depotEntryId = &#63; and portletId = &#63;.
	 *
	 * @param depotEntryId the depot entry ID
	 * @param portletId the portlet ID
	 * @return the number of matching depot app customizations
	 */
	@Override
	public int countByD_P(long depotEntryId, String portletId) {
		portletId = Objects.toString(portletId, "");

		FinderPath finderPath = _finderPathCountByD_P;

		Object[] finderArgs = new Object[] {depotEntryId, portletId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_DEPOTAPPCUSTOMIZATION_WHERE);

			sb.append(_FINDER_COLUMN_D_P_DEPOTENTRYID_2);

			boolean bindPortletId = false;

			if (portletId.isEmpty()) {
				sb.append(_FINDER_COLUMN_D_P_PORTLETID_3);
			}
			else {
				bindPortletId = true;

				sb.append(_FINDER_COLUMN_D_P_PORTLETID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(depotEntryId);

				if (bindPortletId) {
					queryPos.add(portletId);
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

	private static final String _FINDER_COLUMN_D_P_DEPOTENTRYID_2 =
		"depotAppCustomization.depotEntryId = ? AND ";

	private static final String _FINDER_COLUMN_D_P_PORTLETID_2 =
		"depotAppCustomization.portletId = ?";

	private static final String _FINDER_COLUMN_D_P_PORTLETID_3 =
		"(depotAppCustomization.portletId IS NULL OR depotAppCustomization.portletId = '')";

	public DepotAppCustomizationPersistenceImpl() {
		setModelClass(DepotAppCustomization.class);

		setModelImplClass(DepotAppCustomizationImpl.class);
		setModelPKClass(long.class);

		setTable(DepotAppCustomizationTable.INSTANCE);
	}

	/**
	 * Caches the depot app customization in the entity cache if it is enabled.
	 *
	 * @param depotAppCustomization the depot app customization
	 */
	@Override
	public void cacheResult(DepotAppCustomization depotAppCustomization) {
		entityCache.putResult(
			DepotAppCustomizationImpl.class,
			depotAppCustomization.getPrimaryKey(), depotAppCustomization);

		finderCache.putResult(
			_finderPathFetchByD_E,
			new Object[] {
				depotAppCustomization.getDepotEntryId(),
				depotAppCustomization.isEnabled()
			},
			depotAppCustomization);

		finderCache.putResult(
			_finderPathFetchByD_P,
			new Object[] {
				depotAppCustomization.getDepotEntryId(),
				depotAppCustomization.getPortletId()
			},
			depotAppCustomization);
	}

	/**
	 * Caches the depot app customizations in the entity cache if it is enabled.
	 *
	 * @param depotAppCustomizations the depot app customizations
	 */
	@Override
	public void cacheResult(
		List<DepotAppCustomization> depotAppCustomizations) {

		for (DepotAppCustomization depotAppCustomization :
				depotAppCustomizations) {

			if (entityCache.getResult(
					DepotAppCustomizationImpl.class,
					depotAppCustomization.getPrimaryKey()) == null) {

				cacheResult(depotAppCustomization);
			}
		}
	}

	/**
	 * Clears the cache for all depot app customizations.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(DepotAppCustomizationImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the depot app customization.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(DepotAppCustomization depotAppCustomization) {
		entityCache.removeResult(
			DepotAppCustomizationImpl.class,
			depotAppCustomization.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(DepotAppCustomizationModelImpl)depotAppCustomization, true);
	}

	@Override
	public void clearCache(List<DepotAppCustomization> depotAppCustomizations) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (DepotAppCustomization depotAppCustomization :
				depotAppCustomizations) {

			entityCache.removeResult(
				DepotAppCustomizationImpl.class,
				depotAppCustomization.getPrimaryKey());

			clearUniqueFindersCache(
				(DepotAppCustomizationModelImpl)depotAppCustomization, true);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				DepotAppCustomizationImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		DepotAppCustomizationModelImpl depotAppCustomizationModelImpl) {

		Object[] args = new Object[] {
			depotAppCustomizationModelImpl.getDepotEntryId(),
			depotAppCustomizationModelImpl.isEnabled()
		};

		finderCache.putResult(
			_finderPathCountByD_E, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByD_E, args, depotAppCustomizationModelImpl, false);

		args = new Object[] {
			depotAppCustomizationModelImpl.getDepotEntryId(),
			depotAppCustomizationModelImpl.getPortletId()
		};

		finderCache.putResult(
			_finderPathCountByD_P, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByD_P, args, depotAppCustomizationModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		DepotAppCustomizationModelImpl depotAppCustomizationModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				depotAppCustomizationModelImpl.getDepotEntryId(),
				depotAppCustomizationModelImpl.isEnabled()
			};

			finderCache.removeResult(_finderPathCountByD_E, args);
			finderCache.removeResult(_finderPathFetchByD_E, args);
		}

		if ((depotAppCustomizationModelImpl.getColumnBitmask() &
			 _finderPathFetchByD_E.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				depotAppCustomizationModelImpl.getColumnOriginalValue(
					"depotEntryId"),
				depotAppCustomizationModelImpl.getColumnOriginalValue("enabled")
			};

			finderCache.removeResult(_finderPathCountByD_E, args);
			finderCache.removeResult(_finderPathFetchByD_E, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
				depotAppCustomizationModelImpl.getDepotEntryId(),
				depotAppCustomizationModelImpl.getPortletId()
			};

			finderCache.removeResult(_finderPathCountByD_P, args);
			finderCache.removeResult(_finderPathFetchByD_P, args);
		}

		if ((depotAppCustomizationModelImpl.getColumnBitmask() &
			 _finderPathFetchByD_P.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				depotAppCustomizationModelImpl.getColumnOriginalValue(
					"depotEntryId"),
				depotAppCustomizationModelImpl.getColumnOriginalValue(
					"portletId")
			};

			finderCache.removeResult(_finderPathCountByD_P, args);
			finderCache.removeResult(_finderPathFetchByD_P, args);
		}
	}

	/**
	 * Creates a new depot app customization with the primary key. Does not add the depot app customization to the database.
	 *
	 * @param depotAppCustomizationId the primary key for the new depot app customization
	 * @return the new depot app customization
	 */
	@Override
	public DepotAppCustomization create(long depotAppCustomizationId) {
		DepotAppCustomization depotAppCustomization =
			new DepotAppCustomizationImpl();

		depotAppCustomization.setNew(true);
		depotAppCustomization.setPrimaryKey(depotAppCustomizationId);

		depotAppCustomization.setCompanyId(CompanyThreadLocal.getCompanyId());

		return depotAppCustomization;
	}

	/**
	 * Removes the depot app customization with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param depotAppCustomizationId the primary key of the depot app customization
	 * @return the depot app customization that was removed
	 * @throws NoSuchAppCustomizationException if a depot app customization with the primary key could not be found
	 */
	@Override
	public DepotAppCustomization remove(long depotAppCustomizationId)
		throws NoSuchAppCustomizationException {

		return remove((Serializable)depotAppCustomizationId);
	}

	/**
	 * Removes the depot app customization with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the depot app customization
	 * @return the depot app customization that was removed
	 * @throws NoSuchAppCustomizationException if a depot app customization with the primary key could not be found
	 */
	@Override
	public DepotAppCustomization remove(Serializable primaryKey)
		throws NoSuchAppCustomizationException {

		Session session = null;

		try {
			session = openSession();

			DepotAppCustomization depotAppCustomization =
				(DepotAppCustomization)session.get(
					DepotAppCustomizationImpl.class, primaryKey);

			if (depotAppCustomization == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchAppCustomizationException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(depotAppCustomization);
		}
		catch (NoSuchAppCustomizationException noSuchEntityException) {
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
	protected DepotAppCustomization removeImpl(
		DepotAppCustomization depotAppCustomization) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(depotAppCustomization)) {
				depotAppCustomization = (DepotAppCustomization)session.get(
					DepotAppCustomizationImpl.class,
					depotAppCustomization.getPrimaryKeyObj());
			}

			if (depotAppCustomization != null) {
				session.delete(depotAppCustomization);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (depotAppCustomization != null) {
			clearCache(depotAppCustomization);
		}

		return depotAppCustomization;
	}

	@Override
	public DepotAppCustomization updateImpl(
		DepotAppCustomization depotAppCustomization) {

		boolean isNew = depotAppCustomization.isNew();

		if (!(depotAppCustomization instanceof
				DepotAppCustomizationModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(depotAppCustomization.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					depotAppCustomization);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in depotAppCustomization proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom DepotAppCustomization implementation " +
					depotAppCustomization.getClass());
		}

		DepotAppCustomizationModelImpl depotAppCustomizationModelImpl =
			(DepotAppCustomizationModelImpl)depotAppCustomization;

		Session session = null;

		try {
			session = openSession();

			if (depotAppCustomization.isNew()) {
				session.save(depotAppCustomization);

				depotAppCustomization.setNew(false);
			}
			else {
				depotAppCustomization = (DepotAppCustomization)session.merge(
					depotAppCustomization);
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
				depotAppCustomizationModelImpl.getDepotEntryId()
			};

			finderCache.removeResult(_finderPathCountByDepotEntryId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByDepotEntryId, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((depotAppCustomizationModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByDepotEntryId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					depotAppCustomizationModelImpl.getColumnOriginalValue(
						"depotEntryId")
				};

				finderCache.removeResult(_finderPathCountByDepotEntryId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByDepotEntryId, args);

				args = new Object[] {
					depotAppCustomizationModelImpl.getDepotEntryId()
				};

				finderCache.removeResult(_finderPathCountByDepotEntryId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByDepotEntryId, args);
			}
		}

		entityCache.putResult(
			DepotAppCustomizationImpl.class,
			depotAppCustomization.getPrimaryKey(), depotAppCustomization,
			false);

		clearUniqueFindersCache(depotAppCustomizationModelImpl, false);
		cacheUniqueFindersCache(depotAppCustomizationModelImpl);

		depotAppCustomization.resetOriginalValues();

		return depotAppCustomization;
	}

	/**
	 * Returns the depot app customization with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the depot app customization
	 * @return the depot app customization
	 * @throws NoSuchAppCustomizationException if a depot app customization with the primary key could not be found
	 */
	@Override
	public DepotAppCustomization findByPrimaryKey(Serializable primaryKey)
		throws NoSuchAppCustomizationException {

		DepotAppCustomization depotAppCustomization = fetchByPrimaryKey(
			primaryKey);

		if (depotAppCustomization == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchAppCustomizationException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return depotAppCustomization;
	}

	/**
	 * Returns the depot app customization with the primary key or throws a <code>NoSuchAppCustomizationException</code> if it could not be found.
	 *
	 * @param depotAppCustomizationId the primary key of the depot app customization
	 * @return the depot app customization
	 * @throws NoSuchAppCustomizationException if a depot app customization with the primary key could not be found
	 */
	@Override
	public DepotAppCustomization findByPrimaryKey(long depotAppCustomizationId)
		throws NoSuchAppCustomizationException {

		return findByPrimaryKey((Serializable)depotAppCustomizationId);
	}

	/**
	 * Returns the depot app customization with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param depotAppCustomizationId the primary key of the depot app customization
	 * @return the depot app customization, or <code>null</code> if a depot app customization with the primary key could not be found
	 */
	@Override
	public DepotAppCustomization fetchByPrimaryKey(
		long depotAppCustomizationId) {

		return fetchByPrimaryKey((Serializable)depotAppCustomizationId);
	}

	/**
	 * Returns all the depot app customizations.
	 *
	 * @return the depot app customizations
	 */
	@Override
	public List<DepotAppCustomization> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the depot app customizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DepotAppCustomizationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of depot app customizations
	 * @param end the upper bound of the range of depot app customizations (not inclusive)
	 * @return the range of depot app customizations
	 */
	@Override
	public List<DepotAppCustomization> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the depot app customizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DepotAppCustomizationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of depot app customizations
	 * @param end the upper bound of the range of depot app customizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of depot app customizations
	 */
	@Override
	public List<DepotAppCustomization> findAll(
		int start, int end,
		OrderByComparator<DepotAppCustomization> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the depot app customizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DepotAppCustomizationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of depot app customizations
	 * @param end the upper bound of the range of depot app customizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of depot app customizations
	 */
	@Override
	public List<DepotAppCustomization> findAll(
		int start, int end,
		OrderByComparator<DepotAppCustomization> orderByComparator,
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

		List<DepotAppCustomization> list = null;

		if (useFinderCache) {
			list = (List<DepotAppCustomization>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_DEPOTAPPCUSTOMIZATION);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_DEPOTAPPCUSTOMIZATION;

				sql = sql.concat(DepotAppCustomizationModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<DepotAppCustomization>)QueryUtil.list(
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
	 * Removes all the depot app customizations from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (DepotAppCustomization depotAppCustomization : findAll()) {
			remove(depotAppCustomization);
		}
	}

	/**
	 * Returns the number of depot app customizations.
	 *
	 * @return the number of depot app customizations
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
					_SQL_COUNT_DEPOTAPPCUSTOMIZATION);

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
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "depotAppCustomizationId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_DEPOTAPPCUSTOMIZATION;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return DepotAppCustomizationModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the depot app customization persistence.
	 */
	@Activate
	public void activate() {
		_finderPathWithPaginationFindAll = new FinderPath(
			DepotAppCustomizationImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			DepotAppCustomizationImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByDepotEntryId = new FinderPath(
			DepotAppCustomizationImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByDepotEntryId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByDepotEntryId = new FinderPath(
			DepotAppCustomizationImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByDepotEntryId",
			new String[] {Long.class.getName()},
			DepotAppCustomizationModelImpl.getColumnBitmask("depotEntryId"));

		_finderPathCountByDepotEntryId = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByDepotEntryId", new String[] {Long.class.getName()});

		_finderPathFetchByD_E = new FinderPath(
			DepotAppCustomizationImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByD_E",
			new String[] {Long.class.getName(), Boolean.class.getName()},
			DepotAppCustomizationModelImpl.getColumnBitmask("depotEntryId") |
			DepotAppCustomizationModelImpl.getColumnBitmask("enabled"));

		_finderPathCountByD_E = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByD_E",
			new String[] {Long.class.getName(), Boolean.class.getName()});

		_finderPathFetchByD_P = new FinderPath(
			DepotAppCustomizationImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByD_P",
			new String[] {Long.class.getName(), String.class.getName()},
			DepotAppCustomizationModelImpl.getColumnBitmask("depotEntryId") |
			DepotAppCustomizationModelImpl.getColumnBitmask("portletId"));

		_finderPathCountByD_P = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByD_P",
			new String[] {Long.class.getName(), String.class.getName()});
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(DepotAppCustomizationImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	@Reference(
		target = DepotPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = DepotPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = DepotPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_DEPOTAPPCUSTOMIZATION =
		"SELECT depotAppCustomization FROM DepotAppCustomization depotAppCustomization";

	private static final String _SQL_SELECT_DEPOTAPPCUSTOMIZATION_WHERE =
		"SELECT depotAppCustomization FROM DepotAppCustomization depotAppCustomization WHERE ";

	private static final String _SQL_COUNT_DEPOTAPPCUSTOMIZATION =
		"SELECT COUNT(depotAppCustomization) FROM DepotAppCustomization depotAppCustomization";

	private static final String _SQL_COUNT_DEPOTAPPCUSTOMIZATION_WHERE =
		"SELECT COUNT(depotAppCustomization) FROM DepotAppCustomization depotAppCustomization WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"depotAppCustomization.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No DepotAppCustomization exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No DepotAppCustomization exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		DepotAppCustomizationPersistenceImpl.class);

	static {
		try {
			Class.forName(DepotPersistenceConstants.class.getName());
		}
		catch (ClassNotFoundException classNotFoundException) {
			throw new ExceptionInInitializerError(classNotFoundException);
		}
	}

}