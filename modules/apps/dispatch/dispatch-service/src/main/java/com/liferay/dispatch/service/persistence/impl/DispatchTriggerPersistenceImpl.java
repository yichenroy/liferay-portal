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

package com.liferay.dispatch.service.persistence.impl;

import com.liferay.dispatch.exception.NoSuchTriggerException;
import com.liferay.dispatch.model.DispatchTrigger;
import com.liferay.dispatch.model.DispatchTriggerTable;
import com.liferay.dispatch.model.impl.DispatchTriggerImpl;
import com.liferay.dispatch.model.impl.DispatchTriggerModelImpl;
import com.liferay.dispatch.service.persistence.DispatchTriggerPersistence;
import com.liferay.dispatch.service.persistence.impl.constants.DispatchPersistenceConstants;
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
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;

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
 * The persistence implementation for the dispatch trigger service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @generated
 */
@Component(service = DispatchTriggerPersistence.class)
public class DispatchTriggerPersistenceImpl
	extends BasePersistenceImpl<DispatchTrigger>
	implements DispatchTriggerPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>DispatchTriggerUtil</code> to access the dispatch trigger persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		DispatchTriggerImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByCompanyId;
	private FinderPath _finderPathWithoutPaginationFindByCompanyId;
	private FinderPath _finderPathCountByCompanyId;

	/**
	 * Returns all the dispatch triggers where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching dispatch triggers
	 */
	@Override
	public List<DispatchTrigger> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the dispatch triggers where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DispatchTriggerModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of dispatch triggers
	 * @param end the upper bound of the range of dispatch triggers (not inclusive)
	 * @return the range of matching dispatch triggers
	 */
	@Override
	public List<DispatchTrigger> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the dispatch triggers where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DispatchTriggerModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of dispatch triggers
	 * @param end the upper bound of the range of dispatch triggers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching dispatch triggers
	 */
	@Override
	public List<DispatchTrigger> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<DispatchTrigger> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the dispatch triggers where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DispatchTriggerModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of dispatch triggers
	 * @param end the upper bound of the range of dispatch triggers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching dispatch triggers
	 */
	@Override
	public List<DispatchTrigger> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<DispatchTrigger> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByCompanyId;
				finderArgs = new Object[] {companyId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByCompanyId;
			finderArgs = new Object[] {
				companyId, start, end, orderByComparator
			};
		}

		List<DispatchTrigger> list = null;

		if (useFinderCache) {
			list = (List<DispatchTrigger>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DispatchTrigger dispatchTrigger : list) {
					if (companyId != dispatchTrigger.getCompanyId()) {
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

			sb.append(_SQL_SELECT_DISPATCHTRIGGER_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(DispatchTriggerModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				list = (List<DispatchTrigger>)QueryUtil.list(
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
	 * Returns the first dispatch trigger in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching dispatch trigger
	 * @throws NoSuchTriggerException if a matching dispatch trigger could not be found
	 */
	@Override
	public DispatchTrigger findByCompanyId_First(
			long companyId,
			OrderByComparator<DispatchTrigger> orderByComparator)
		throws NoSuchTriggerException {

		DispatchTrigger dispatchTrigger = fetchByCompanyId_First(
			companyId, orderByComparator);

		if (dispatchTrigger != null) {
			return dispatchTrigger;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchTriggerException(sb.toString());
	}

	/**
	 * Returns the first dispatch trigger in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching dispatch trigger, or <code>null</code> if a matching dispatch trigger could not be found
	 */
	@Override
	public DispatchTrigger fetchByCompanyId_First(
		long companyId, OrderByComparator<DispatchTrigger> orderByComparator) {

		List<DispatchTrigger> list = findByCompanyId(
			companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last dispatch trigger in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching dispatch trigger
	 * @throws NoSuchTriggerException if a matching dispatch trigger could not be found
	 */
	@Override
	public DispatchTrigger findByCompanyId_Last(
			long companyId,
			OrderByComparator<DispatchTrigger> orderByComparator)
		throws NoSuchTriggerException {

		DispatchTrigger dispatchTrigger = fetchByCompanyId_Last(
			companyId, orderByComparator);

		if (dispatchTrigger != null) {
			return dispatchTrigger;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchTriggerException(sb.toString());
	}

	/**
	 * Returns the last dispatch trigger in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching dispatch trigger, or <code>null</code> if a matching dispatch trigger could not be found
	 */
	@Override
	public DispatchTrigger fetchByCompanyId_Last(
		long companyId, OrderByComparator<DispatchTrigger> orderByComparator) {

		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<DispatchTrigger> list = findByCompanyId(
			companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the dispatch triggers before and after the current dispatch trigger in the ordered set where companyId = &#63;.
	 *
	 * @param dispatchTriggerId the primary key of the current dispatch trigger
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next dispatch trigger
	 * @throws NoSuchTriggerException if a dispatch trigger with the primary key could not be found
	 */
	@Override
	public DispatchTrigger[] findByCompanyId_PrevAndNext(
			long dispatchTriggerId, long companyId,
			OrderByComparator<DispatchTrigger> orderByComparator)
		throws NoSuchTriggerException {

		DispatchTrigger dispatchTrigger = findByPrimaryKey(dispatchTriggerId);

		Session session = null;

		try {
			session = openSession();

			DispatchTrigger[] array = new DispatchTriggerImpl[3];

			array[0] = getByCompanyId_PrevAndNext(
				session, dispatchTrigger, companyId, orderByComparator, true);

			array[1] = dispatchTrigger;

			array[2] = getByCompanyId_PrevAndNext(
				session, dispatchTrigger, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected DispatchTrigger getByCompanyId_PrevAndNext(
		Session session, DispatchTrigger dispatchTrigger, long companyId,
		OrderByComparator<DispatchTrigger> orderByComparator,
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

		sb.append(_SQL_SELECT_DISPATCHTRIGGER_WHERE);

		sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

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
			sb.append(DispatchTriggerModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						dispatchTrigger)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<DispatchTrigger> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the dispatch triggers that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching dispatch triggers that the user has permission to view
	 */
	@Override
	public List<DispatchTrigger> filterFindByCompanyId(long companyId) {
		return filterFindByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the dispatch triggers that the user has permission to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DispatchTriggerModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of dispatch triggers
	 * @param end the upper bound of the range of dispatch triggers (not inclusive)
	 * @return the range of matching dispatch triggers that the user has permission to view
	 */
	@Override
	public List<DispatchTrigger> filterFindByCompanyId(
		long companyId, int start, int end) {

		return filterFindByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the dispatch triggers that the user has permissions to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DispatchTriggerModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of dispatch triggers
	 * @param end the upper bound of the range of dispatch triggers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching dispatch triggers that the user has permission to view
	 */
	@Override
	public List<DispatchTrigger> filterFindByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<DispatchTrigger> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByCompanyId(companyId, start, end, orderByComparator);
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
			sb.append(_FILTER_SQL_SELECT_DISPATCHTRIGGER_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_DISPATCHTRIGGER_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_DISPATCHTRIGGER_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(DispatchTriggerModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(DispatchTriggerModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), DispatchTrigger.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, DispatchTriggerImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, DispatchTriggerImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			return (List<DispatchTrigger>)QueryUtil.list(
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
	 * Returns the dispatch triggers before and after the current dispatch trigger in the ordered set of dispatch triggers that the user has permission to view where companyId = &#63;.
	 *
	 * @param dispatchTriggerId the primary key of the current dispatch trigger
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next dispatch trigger
	 * @throws NoSuchTriggerException if a dispatch trigger with the primary key could not be found
	 */
	@Override
	public DispatchTrigger[] filterFindByCompanyId_PrevAndNext(
			long dispatchTriggerId, long companyId,
			OrderByComparator<DispatchTrigger> orderByComparator)
		throws NoSuchTriggerException {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByCompanyId_PrevAndNext(
				dispatchTriggerId, companyId, orderByComparator);
		}

		DispatchTrigger dispatchTrigger = findByPrimaryKey(dispatchTriggerId);

		Session session = null;

		try {
			session = openSession();

			DispatchTrigger[] array = new DispatchTriggerImpl[3];

			array[0] = filterGetByCompanyId_PrevAndNext(
				session, dispatchTrigger, companyId, orderByComparator, true);

			array[1] = dispatchTrigger;

			array[2] = filterGetByCompanyId_PrevAndNext(
				session, dispatchTrigger, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected DispatchTrigger filterGetByCompanyId_PrevAndNext(
		Session session, DispatchTrigger dispatchTrigger, long companyId,
		OrderByComparator<DispatchTrigger> orderByComparator,
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
			sb.append(_FILTER_SQL_SELECT_DISPATCHTRIGGER_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_DISPATCHTRIGGER_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_DISPATCHTRIGGER_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(DispatchTriggerModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(DispatchTriggerModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), DispatchTrigger.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(_FILTER_ENTITY_ALIAS, DispatchTriggerImpl.class);
		}
		else {
			sqlQuery.addEntity(_FILTER_ENTITY_TABLE, DispatchTriggerImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						dispatchTrigger)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<DispatchTrigger> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the dispatch triggers where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (DispatchTrigger dispatchTrigger :
				findByCompanyId(
					companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(dispatchTrigger);
		}
	}

	/**
	 * Returns the number of dispatch triggers where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching dispatch triggers
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = _finderPathCountByCompanyId;

		Object[] finderArgs = new Object[] {companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_DISPATCHTRIGGER_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

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

	/**
	 * Returns the number of dispatch triggers that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching dispatch triggers that the user has permission to view
	 */
	@Override
	public int filterCountByCompanyId(long companyId) {
		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return countByCompanyId(companyId);
		}

		StringBundler sb = new StringBundler(2);

		sb.append(_FILTER_SQL_COUNT_DISPATCHTRIGGER_WHERE);

		sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), DispatchTrigger.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

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

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 =
		"dispatchTrigger.companyId = ?";

	private FinderPath _finderPathFetchByC_N;
	private FinderPath _finderPathCountByC_N;

	/**
	 * Returns the dispatch trigger where companyId = &#63; and name = &#63; or throws a <code>NoSuchTriggerException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching dispatch trigger
	 * @throws NoSuchTriggerException if a matching dispatch trigger could not be found
	 */
	@Override
	public DispatchTrigger findByC_N(long companyId, String name)
		throws NoSuchTriggerException {

		DispatchTrigger dispatchTrigger = fetchByC_N(companyId, name);

		if (dispatchTrigger == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("companyId=");
			sb.append(companyId);

			sb.append(", name=");
			sb.append(name);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchTriggerException(sb.toString());
		}

		return dispatchTrigger;
	}

	/**
	 * Returns the dispatch trigger where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching dispatch trigger, or <code>null</code> if a matching dispatch trigger could not be found
	 */
	@Override
	public DispatchTrigger fetchByC_N(long companyId, String name) {
		return fetchByC_N(companyId, name, true);
	}

	/**
	 * Returns the dispatch trigger where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching dispatch trigger, or <code>null</code> if a matching dispatch trigger could not be found
	 */
	@Override
	public DispatchTrigger fetchByC_N(
		long companyId, String name, boolean useFinderCache) {

		name = Objects.toString(name, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {companyId, name};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByC_N, finderArgs, this);
		}

		if (result instanceof DispatchTrigger) {
			DispatchTrigger dispatchTrigger = (DispatchTrigger)result;

			if ((companyId != dispatchTrigger.getCompanyId()) ||
				!Objects.equals(name, dispatchTrigger.getName())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_DISPATCHTRIGGER_WHERE);

			sb.append(_FINDER_COLUMN_C_N_COMPANYID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_N_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_C_N_NAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindName) {
					queryPos.add(name);
				}

				List<DispatchTrigger> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByC_N, finderArgs, list);
					}
				}
				else {
					DispatchTrigger dispatchTrigger = list.get(0);

					result = dispatchTrigger;

					cacheResult(dispatchTrigger);
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
			return (DispatchTrigger)result;
		}
	}

	/**
	 * Removes the dispatch trigger where companyId = &#63; and name = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the dispatch trigger that was removed
	 */
	@Override
	public DispatchTrigger removeByC_N(long companyId, String name)
		throws NoSuchTriggerException {

		DispatchTrigger dispatchTrigger = findByC_N(companyId, name);

		return remove(dispatchTrigger);
	}

	/**
	 * Returns the number of dispatch triggers where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the number of matching dispatch triggers
	 */
	@Override
	public int countByC_N(long companyId, String name) {
		name = Objects.toString(name, "");

		FinderPath finderPath = _finderPathCountByC_N;

		Object[] finderArgs = new Object[] {companyId, name};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_DISPATCHTRIGGER_WHERE);

			sb.append(_FINDER_COLUMN_C_N_COMPANYID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_N_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_C_N_NAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

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

	private static final String _FINDER_COLUMN_C_N_COMPANYID_2 =
		"dispatchTrigger.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_N_NAME_2 =
		"dispatchTrigger.name = ?";

	private static final String _FINDER_COLUMN_C_N_NAME_3 =
		"(dispatchTrigger.name IS NULL OR dispatchTrigger.name = '')";

	private FinderPath _finderPathWithPaginationFindByC_T;
	private FinderPath _finderPathWithoutPaginationFindByC_T;
	private FinderPath _finderPathCountByC_T;

	/**
	 * Returns all the dispatch triggers where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the matching dispatch triggers
	 */
	@Override
	public List<DispatchTrigger> findByC_T(long companyId, String type) {
		return findByC_T(
			companyId, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the dispatch triggers where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DispatchTriggerModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of dispatch triggers
	 * @param end the upper bound of the range of dispatch triggers (not inclusive)
	 * @return the range of matching dispatch triggers
	 */
	@Override
	public List<DispatchTrigger> findByC_T(
		long companyId, String type, int start, int end) {

		return findByC_T(companyId, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the dispatch triggers where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DispatchTriggerModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of dispatch triggers
	 * @param end the upper bound of the range of dispatch triggers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching dispatch triggers
	 */
	@Override
	public List<DispatchTrigger> findByC_T(
		long companyId, String type, int start, int end,
		OrderByComparator<DispatchTrigger> orderByComparator) {

		return findByC_T(companyId, type, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the dispatch triggers where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DispatchTriggerModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of dispatch triggers
	 * @param end the upper bound of the range of dispatch triggers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching dispatch triggers
	 */
	@Override
	public List<DispatchTrigger> findByC_T(
		long companyId, String type, int start, int end,
		OrderByComparator<DispatchTrigger> orderByComparator,
		boolean useFinderCache) {

		type = Objects.toString(type, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC_T;
				finderArgs = new Object[] {companyId, type};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC_T;
			finderArgs = new Object[] {
				companyId, type, start, end, orderByComparator
			};
		}

		List<DispatchTrigger> list = null;

		if (useFinderCache) {
			list = (List<DispatchTrigger>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DispatchTrigger dispatchTrigger : list) {
					if ((companyId != dispatchTrigger.getCompanyId()) ||
						!type.equals(dispatchTrigger.getType())) {

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

			sb.append(_SQL_SELECT_DISPATCHTRIGGER_WHERE);

			sb.append(_FINDER_COLUMN_C_T_COMPANYID_2);

			boolean bindType = false;

			if (type.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_T_TYPE_3);
			}
			else {
				bindType = true;

				sb.append(_FINDER_COLUMN_C_T_TYPE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(DispatchTriggerModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindType) {
					queryPos.add(type);
				}

				list = (List<DispatchTrigger>)QueryUtil.list(
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
	 * Returns the first dispatch trigger in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching dispatch trigger
	 * @throws NoSuchTriggerException if a matching dispatch trigger could not be found
	 */
	@Override
	public DispatchTrigger findByC_T_First(
			long companyId, String type,
			OrderByComparator<DispatchTrigger> orderByComparator)
		throws NoSuchTriggerException {

		DispatchTrigger dispatchTrigger = fetchByC_T_First(
			companyId, type, orderByComparator);

		if (dispatchTrigger != null) {
			return dispatchTrigger;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchTriggerException(sb.toString());
	}

	/**
	 * Returns the first dispatch trigger in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching dispatch trigger, or <code>null</code> if a matching dispatch trigger could not be found
	 */
	@Override
	public DispatchTrigger fetchByC_T_First(
		long companyId, String type,
		OrderByComparator<DispatchTrigger> orderByComparator) {

		List<DispatchTrigger> list = findByC_T(
			companyId, type, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last dispatch trigger in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching dispatch trigger
	 * @throws NoSuchTriggerException if a matching dispatch trigger could not be found
	 */
	@Override
	public DispatchTrigger findByC_T_Last(
			long companyId, String type,
			OrderByComparator<DispatchTrigger> orderByComparator)
		throws NoSuchTriggerException {

		DispatchTrigger dispatchTrigger = fetchByC_T_Last(
			companyId, type, orderByComparator);

		if (dispatchTrigger != null) {
			return dispatchTrigger;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchTriggerException(sb.toString());
	}

	/**
	 * Returns the last dispatch trigger in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching dispatch trigger, or <code>null</code> if a matching dispatch trigger could not be found
	 */
	@Override
	public DispatchTrigger fetchByC_T_Last(
		long companyId, String type,
		OrderByComparator<DispatchTrigger> orderByComparator) {

		int count = countByC_T(companyId, type);

		if (count == 0) {
			return null;
		}

		List<DispatchTrigger> list = findByC_T(
			companyId, type, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the dispatch triggers before and after the current dispatch trigger in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param dispatchTriggerId the primary key of the current dispatch trigger
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next dispatch trigger
	 * @throws NoSuchTriggerException if a dispatch trigger with the primary key could not be found
	 */
	@Override
	public DispatchTrigger[] findByC_T_PrevAndNext(
			long dispatchTriggerId, long companyId, String type,
			OrderByComparator<DispatchTrigger> orderByComparator)
		throws NoSuchTriggerException {

		type = Objects.toString(type, "");

		DispatchTrigger dispatchTrigger = findByPrimaryKey(dispatchTriggerId);

		Session session = null;

		try {
			session = openSession();

			DispatchTrigger[] array = new DispatchTriggerImpl[3];

			array[0] = getByC_T_PrevAndNext(
				session, dispatchTrigger, companyId, type, orderByComparator,
				true);

			array[1] = dispatchTrigger;

			array[2] = getByC_T_PrevAndNext(
				session, dispatchTrigger, companyId, type, orderByComparator,
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

	protected DispatchTrigger getByC_T_PrevAndNext(
		Session session, DispatchTrigger dispatchTrigger, long companyId,
		String type, OrderByComparator<DispatchTrigger> orderByComparator,
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

		sb.append(_SQL_SELECT_DISPATCHTRIGGER_WHERE);

		sb.append(_FINDER_COLUMN_C_T_COMPANYID_2);

		boolean bindType = false;

		if (type.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_T_TYPE_3);
		}
		else {
			bindType = true;

			sb.append(_FINDER_COLUMN_C_T_TYPE_2);
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
			sb.append(DispatchTriggerModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		if (bindType) {
			queryPos.add(type);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						dispatchTrigger)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<DispatchTrigger> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the dispatch triggers that the user has permission to view where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the matching dispatch triggers that the user has permission to view
	 */
	@Override
	public List<DispatchTrigger> filterFindByC_T(long companyId, String type) {
		return filterFindByC_T(
			companyId, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the dispatch triggers that the user has permission to view where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DispatchTriggerModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of dispatch triggers
	 * @param end the upper bound of the range of dispatch triggers (not inclusive)
	 * @return the range of matching dispatch triggers that the user has permission to view
	 */
	@Override
	public List<DispatchTrigger> filterFindByC_T(
		long companyId, String type, int start, int end) {

		return filterFindByC_T(companyId, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the dispatch triggers that the user has permissions to view where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DispatchTriggerModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of dispatch triggers
	 * @param end the upper bound of the range of dispatch triggers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching dispatch triggers that the user has permission to view
	 */
	@Override
	public List<DispatchTrigger> filterFindByC_T(
		long companyId, String type, int start, int end,
		OrderByComparator<DispatchTrigger> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByC_T(companyId, type, start, end, orderByComparator);
		}

		type = Objects.toString(type, "");

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_DISPATCHTRIGGER_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_DISPATCHTRIGGER_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_C_T_COMPANYID_2);

		boolean bindType = false;

		if (type.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_T_TYPE_3_SQL);
		}
		else {
			bindType = true;

			sb.append(_FINDER_COLUMN_C_T_TYPE_2_SQL);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_DISPATCHTRIGGER_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(DispatchTriggerModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(DispatchTriggerModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), DispatchTrigger.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, DispatchTriggerImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, DispatchTriggerImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			if (bindType) {
				queryPos.add(type);
			}

			return (List<DispatchTrigger>)QueryUtil.list(
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
	 * Returns the dispatch triggers before and after the current dispatch trigger in the ordered set of dispatch triggers that the user has permission to view where companyId = &#63; and type = &#63;.
	 *
	 * @param dispatchTriggerId the primary key of the current dispatch trigger
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next dispatch trigger
	 * @throws NoSuchTriggerException if a dispatch trigger with the primary key could not be found
	 */
	@Override
	public DispatchTrigger[] filterFindByC_T_PrevAndNext(
			long dispatchTriggerId, long companyId, String type,
			OrderByComparator<DispatchTrigger> orderByComparator)
		throws NoSuchTriggerException {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByC_T_PrevAndNext(
				dispatchTriggerId, companyId, type, orderByComparator);
		}

		type = Objects.toString(type, "");

		DispatchTrigger dispatchTrigger = findByPrimaryKey(dispatchTriggerId);

		Session session = null;

		try {
			session = openSession();

			DispatchTrigger[] array = new DispatchTriggerImpl[3];

			array[0] = filterGetByC_T_PrevAndNext(
				session, dispatchTrigger, companyId, type, orderByComparator,
				true);

			array[1] = dispatchTrigger;

			array[2] = filterGetByC_T_PrevAndNext(
				session, dispatchTrigger, companyId, type, orderByComparator,
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

	protected DispatchTrigger filterGetByC_T_PrevAndNext(
		Session session, DispatchTrigger dispatchTrigger, long companyId,
		String type, OrderByComparator<DispatchTrigger> orderByComparator,
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
			sb.append(_FILTER_SQL_SELECT_DISPATCHTRIGGER_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_DISPATCHTRIGGER_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_C_T_COMPANYID_2);

		boolean bindType = false;

		if (type.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_T_TYPE_3_SQL);
		}
		else {
			bindType = true;

			sb.append(_FINDER_COLUMN_C_T_TYPE_2_SQL);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_DISPATCHTRIGGER_NO_INLINE_DISTINCT_WHERE_2);
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
				sb.append(DispatchTriggerModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(DispatchTriggerModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), DispatchTrigger.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(_FILTER_ENTITY_ALIAS, DispatchTriggerImpl.class);
		}
		else {
			sqlQuery.addEntity(_FILTER_ENTITY_TABLE, DispatchTriggerImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(companyId);

		if (bindType) {
			queryPos.add(type);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						dispatchTrigger)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<DispatchTrigger> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the dispatch triggers where companyId = &#63; and type = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 */
	@Override
	public void removeByC_T(long companyId, String type) {
		for (DispatchTrigger dispatchTrigger :
				findByC_T(
					companyId, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(dispatchTrigger);
		}
	}

	/**
	 * Returns the number of dispatch triggers where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the number of matching dispatch triggers
	 */
	@Override
	public int countByC_T(long companyId, String type) {
		type = Objects.toString(type, "");

		FinderPath finderPath = _finderPathCountByC_T;

		Object[] finderArgs = new Object[] {companyId, type};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_DISPATCHTRIGGER_WHERE);

			sb.append(_FINDER_COLUMN_C_T_COMPANYID_2);

			boolean bindType = false;

			if (type.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_T_TYPE_3);
			}
			else {
				bindType = true;

				sb.append(_FINDER_COLUMN_C_T_TYPE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindType) {
					queryPos.add(type);
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
	 * Returns the number of dispatch triggers that the user has permission to view where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the number of matching dispatch triggers that the user has permission to view
	 */
	@Override
	public int filterCountByC_T(long companyId, String type) {
		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return countByC_T(companyId, type);
		}

		type = Objects.toString(type, "");

		StringBundler sb = new StringBundler(3);

		sb.append(_FILTER_SQL_COUNT_DISPATCHTRIGGER_WHERE);

		sb.append(_FINDER_COLUMN_C_T_COMPANYID_2);

		boolean bindType = false;

		if (type.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_T_TYPE_3_SQL);
		}
		else {
			bindType = true;

			sb.append(_FINDER_COLUMN_C_T_TYPE_2_SQL);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), DispatchTrigger.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			if (bindType) {
				queryPos.add(type);
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

	private static final String _FINDER_COLUMN_C_T_COMPANYID_2 =
		"dispatchTrigger.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_T_TYPE_2 =
		"dispatchTrigger.type = ?";

	private static final String _FINDER_COLUMN_C_T_TYPE_3 =
		"(dispatchTrigger.type IS NULL OR dispatchTrigger.type = '')";

	private static final String _FINDER_COLUMN_C_T_TYPE_2_SQL =
		"dispatchTrigger.type_ = ?";

	private static final String _FINDER_COLUMN_C_T_TYPE_3_SQL =
		"(dispatchTrigger.type_ IS NULL OR dispatchTrigger.type_ = '')";

	public DispatchTriggerPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("active", "active_");
		dbColumnNames.put("system", "system_");
		dbColumnNames.put("type", "type_");

		setDBColumnNames(dbColumnNames);

		setModelClass(DispatchTrigger.class);

		setModelImplClass(DispatchTriggerImpl.class);
		setModelPKClass(long.class);

		setTable(DispatchTriggerTable.INSTANCE);
	}

	/**
	 * Caches the dispatch trigger in the entity cache if it is enabled.
	 *
	 * @param dispatchTrigger the dispatch trigger
	 */
	@Override
	public void cacheResult(DispatchTrigger dispatchTrigger) {
		entityCache.putResult(
			DispatchTriggerImpl.class, dispatchTrigger.getPrimaryKey(),
			dispatchTrigger);

		finderCache.putResult(
			_finderPathFetchByC_N,
			new Object[] {
				dispatchTrigger.getCompanyId(), dispatchTrigger.getName()
			},
			dispatchTrigger);
	}

	/**
	 * Caches the dispatch triggers in the entity cache if it is enabled.
	 *
	 * @param dispatchTriggers the dispatch triggers
	 */
	@Override
	public void cacheResult(List<DispatchTrigger> dispatchTriggers) {
		for (DispatchTrigger dispatchTrigger : dispatchTriggers) {
			if (entityCache.getResult(
					DispatchTriggerImpl.class,
					dispatchTrigger.getPrimaryKey()) == null) {

				cacheResult(dispatchTrigger);
			}
		}
	}

	/**
	 * Clears the cache for all dispatch triggers.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(DispatchTriggerImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the dispatch trigger.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(DispatchTrigger dispatchTrigger) {
		entityCache.removeResult(
			DispatchTriggerImpl.class, dispatchTrigger.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(DispatchTriggerModelImpl)dispatchTrigger, true);
	}

	@Override
	public void clearCache(List<DispatchTrigger> dispatchTriggers) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (DispatchTrigger dispatchTrigger : dispatchTriggers) {
			entityCache.removeResult(
				DispatchTriggerImpl.class, dispatchTrigger.getPrimaryKey());

			clearUniqueFindersCache(
				(DispatchTriggerModelImpl)dispatchTrigger, true);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(DispatchTriggerImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		DispatchTriggerModelImpl dispatchTriggerModelImpl) {

		Object[] args = new Object[] {
			dispatchTriggerModelImpl.getCompanyId(),
			dispatchTriggerModelImpl.getName()
		};

		finderCache.putResult(
			_finderPathCountByC_N, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByC_N, args, dispatchTriggerModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		DispatchTriggerModelImpl dispatchTriggerModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				dispatchTriggerModelImpl.getCompanyId(),
				dispatchTriggerModelImpl.getName()
			};

			finderCache.removeResult(_finderPathCountByC_N, args);
			finderCache.removeResult(_finderPathFetchByC_N, args);
		}

		if ((dispatchTriggerModelImpl.getColumnBitmask() &
			 _finderPathFetchByC_N.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				dispatchTriggerModelImpl.getColumnOriginalValue("companyId"),
				dispatchTriggerModelImpl.getColumnOriginalValue("name")
			};

			finderCache.removeResult(_finderPathCountByC_N, args);
			finderCache.removeResult(_finderPathFetchByC_N, args);
		}
	}

	/**
	 * Creates a new dispatch trigger with the primary key. Does not add the dispatch trigger to the database.
	 *
	 * @param dispatchTriggerId the primary key for the new dispatch trigger
	 * @return the new dispatch trigger
	 */
	@Override
	public DispatchTrigger create(long dispatchTriggerId) {
		DispatchTrigger dispatchTrigger = new DispatchTriggerImpl();

		dispatchTrigger.setNew(true);
		dispatchTrigger.setPrimaryKey(dispatchTriggerId);

		dispatchTrigger.setCompanyId(CompanyThreadLocal.getCompanyId());

		return dispatchTrigger;
	}

	/**
	 * Removes the dispatch trigger with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param dispatchTriggerId the primary key of the dispatch trigger
	 * @return the dispatch trigger that was removed
	 * @throws NoSuchTriggerException if a dispatch trigger with the primary key could not be found
	 */
	@Override
	public DispatchTrigger remove(long dispatchTriggerId)
		throws NoSuchTriggerException {

		return remove((Serializable)dispatchTriggerId);
	}

	/**
	 * Removes the dispatch trigger with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the dispatch trigger
	 * @return the dispatch trigger that was removed
	 * @throws NoSuchTriggerException if a dispatch trigger with the primary key could not be found
	 */
	@Override
	public DispatchTrigger remove(Serializable primaryKey)
		throws NoSuchTriggerException {

		Session session = null;

		try {
			session = openSession();

			DispatchTrigger dispatchTrigger = (DispatchTrigger)session.get(
				DispatchTriggerImpl.class, primaryKey);

			if (dispatchTrigger == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchTriggerException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(dispatchTrigger);
		}
		catch (NoSuchTriggerException noSuchEntityException) {
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
	protected DispatchTrigger removeImpl(DispatchTrigger dispatchTrigger) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(dispatchTrigger)) {
				dispatchTrigger = (DispatchTrigger)session.get(
					DispatchTriggerImpl.class,
					dispatchTrigger.getPrimaryKeyObj());
			}

			if (dispatchTrigger != null) {
				session.delete(dispatchTrigger);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (dispatchTrigger != null) {
			clearCache(dispatchTrigger);
		}

		return dispatchTrigger;
	}

	@Override
	public DispatchTrigger updateImpl(DispatchTrigger dispatchTrigger) {
		boolean isNew = dispatchTrigger.isNew();

		if (!(dispatchTrigger instanceof DispatchTriggerModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(dispatchTrigger.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					dispatchTrigger);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in dispatchTrigger proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom DispatchTrigger implementation " +
					dispatchTrigger.getClass());
		}

		DispatchTriggerModelImpl dispatchTriggerModelImpl =
			(DispatchTriggerModelImpl)dispatchTrigger;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (dispatchTrigger.getCreateDate() == null)) {
			if (serviceContext == null) {
				dispatchTrigger.setCreateDate(now);
			}
			else {
				dispatchTrigger.setCreateDate(
					serviceContext.getCreateDate(now));
			}
		}

		if (!dispatchTriggerModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				dispatchTrigger.setModifiedDate(now);
			}
			else {
				dispatchTrigger.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (dispatchTrigger.isNew()) {
				session.save(dispatchTrigger);

				dispatchTrigger.setNew(false);
			}
			else {
				dispatchTrigger = (DispatchTrigger)session.merge(
					dispatchTrigger);
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
				dispatchTriggerModelImpl.getCompanyId()
			};

			finderCache.removeResult(_finderPathCountByCompanyId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByCompanyId, args);

			args = new Object[] {
				dispatchTriggerModelImpl.getCompanyId(),
				dispatchTriggerModelImpl.getType()
			};

			finderCache.removeResult(_finderPathCountByC_T, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByC_T, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((dispatchTriggerModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByCompanyId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					dispatchTriggerModelImpl.getColumnOriginalValue("companyId")
				};

				finderCache.removeResult(_finderPathCountByCompanyId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByCompanyId, args);

				args = new Object[] {dispatchTriggerModelImpl.getCompanyId()};

				finderCache.removeResult(_finderPathCountByCompanyId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByCompanyId, args);
			}

			if ((dispatchTriggerModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByC_T.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					dispatchTriggerModelImpl.getColumnOriginalValue(
						"companyId"),
					dispatchTriggerModelImpl.getColumnOriginalValue("type_")
				};

				finderCache.removeResult(_finderPathCountByC_T, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByC_T, args);

				args = new Object[] {
					dispatchTriggerModelImpl.getCompanyId(),
					dispatchTriggerModelImpl.getType()
				};

				finderCache.removeResult(_finderPathCountByC_T, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByC_T, args);
			}
		}

		entityCache.putResult(
			DispatchTriggerImpl.class, dispatchTrigger.getPrimaryKey(),
			dispatchTrigger, false);

		clearUniqueFindersCache(dispatchTriggerModelImpl, false);
		cacheUniqueFindersCache(dispatchTriggerModelImpl);

		dispatchTrigger.resetOriginalValues();

		return dispatchTrigger;
	}

	/**
	 * Returns the dispatch trigger with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the dispatch trigger
	 * @return the dispatch trigger
	 * @throws NoSuchTriggerException if a dispatch trigger with the primary key could not be found
	 */
	@Override
	public DispatchTrigger findByPrimaryKey(Serializable primaryKey)
		throws NoSuchTriggerException {

		DispatchTrigger dispatchTrigger = fetchByPrimaryKey(primaryKey);

		if (dispatchTrigger == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchTriggerException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return dispatchTrigger;
	}

	/**
	 * Returns the dispatch trigger with the primary key or throws a <code>NoSuchTriggerException</code> if it could not be found.
	 *
	 * @param dispatchTriggerId the primary key of the dispatch trigger
	 * @return the dispatch trigger
	 * @throws NoSuchTriggerException if a dispatch trigger with the primary key could not be found
	 */
	@Override
	public DispatchTrigger findByPrimaryKey(long dispatchTriggerId)
		throws NoSuchTriggerException {

		return findByPrimaryKey((Serializable)dispatchTriggerId);
	}

	/**
	 * Returns the dispatch trigger with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param dispatchTriggerId the primary key of the dispatch trigger
	 * @return the dispatch trigger, or <code>null</code> if a dispatch trigger with the primary key could not be found
	 */
	@Override
	public DispatchTrigger fetchByPrimaryKey(long dispatchTriggerId) {
		return fetchByPrimaryKey((Serializable)dispatchTriggerId);
	}

	/**
	 * Returns all the dispatch triggers.
	 *
	 * @return the dispatch triggers
	 */
	@Override
	public List<DispatchTrigger> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the dispatch triggers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DispatchTriggerModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of dispatch triggers
	 * @param end the upper bound of the range of dispatch triggers (not inclusive)
	 * @return the range of dispatch triggers
	 */
	@Override
	public List<DispatchTrigger> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the dispatch triggers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DispatchTriggerModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of dispatch triggers
	 * @param end the upper bound of the range of dispatch triggers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of dispatch triggers
	 */
	@Override
	public List<DispatchTrigger> findAll(
		int start, int end,
		OrderByComparator<DispatchTrigger> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the dispatch triggers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DispatchTriggerModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of dispatch triggers
	 * @param end the upper bound of the range of dispatch triggers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of dispatch triggers
	 */
	@Override
	public List<DispatchTrigger> findAll(
		int start, int end,
		OrderByComparator<DispatchTrigger> orderByComparator,
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

		List<DispatchTrigger> list = null;

		if (useFinderCache) {
			list = (List<DispatchTrigger>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_DISPATCHTRIGGER);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_DISPATCHTRIGGER;

				sql = sql.concat(DispatchTriggerModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<DispatchTrigger>)QueryUtil.list(
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
	 * Removes all the dispatch triggers from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (DispatchTrigger dispatchTrigger : findAll()) {
			remove(dispatchTrigger);
		}
	}

	/**
	 * Returns the number of dispatch triggers.
	 *
	 * @return the number of dispatch triggers
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_DISPATCHTRIGGER);

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
		return "dispatchTriggerId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_DISPATCHTRIGGER;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return DispatchTriggerModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the dispatch trigger persistence.
	 */
	@Activate
	public void activate() {
		_finderPathWithPaginationFindAll = new FinderPath(
			DispatchTriggerImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			DispatchTriggerImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByCompanyId = new FinderPath(
			DispatchTriggerImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCompanyId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByCompanyId = new FinderPath(
			DispatchTriggerImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] {Long.class.getName()},
			DispatchTriggerModelImpl.getColumnBitmask("companyId") |
			DispatchTriggerModelImpl.getColumnBitmask("modifiedDate"));

		_finderPathCountByCompanyId = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCompanyId", new String[] {Long.class.getName()});

		_finderPathFetchByC_N = new FinderPath(
			DispatchTriggerImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByC_N",
			new String[] {Long.class.getName(), String.class.getName()},
			DispatchTriggerModelImpl.getColumnBitmask("companyId") |
			DispatchTriggerModelImpl.getColumnBitmask("name"));

		_finderPathCountByC_N = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_N",
			new String[] {Long.class.getName(), String.class.getName()});

		_finderPathWithPaginationFindByC_T = new FinderPath(
			DispatchTriggerImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByC_T",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByC_T = new FinderPath(
			DispatchTriggerImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_T",
			new String[] {Long.class.getName(), String.class.getName()},
			DispatchTriggerModelImpl.getColumnBitmask("companyId") |
			DispatchTriggerModelImpl.getColumnBitmask("type_") |
			DispatchTriggerModelImpl.getColumnBitmask("modifiedDate"));

		_finderPathCountByC_T = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_T",
			new String[] {Long.class.getName(), String.class.getName()});
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(DispatchTriggerImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	@Reference(
		target = DispatchPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = DispatchPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = DispatchPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_DISPATCHTRIGGER =
		"SELECT dispatchTrigger FROM DispatchTrigger dispatchTrigger";

	private static final String _SQL_SELECT_DISPATCHTRIGGER_WHERE =
		"SELECT dispatchTrigger FROM DispatchTrigger dispatchTrigger WHERE ";

	private static final String _SQL_COUNT_DISPATCHTRIGGER =
		"SELECT COUNT(dispatchTrigger) FROM DispatchTrigger dispatchTrigger";

	private static final String _SQL_COUNT_DISPATCHTRIGGER_WHERE =
		"SELECT COUNT(dispatchTrigger) FROM DispatchTrigger dispatchTrigger WHERE ";

	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN =
		"dispatchTrigger.dispatchTriggerId";

	private static final String _FILTER_SQL_SELECT_DISPATCHTRIGGER_WHERE =
		"SELECT DISTINCT {dispatchTrigger.*} FROM DispatchTrigger dispatchTrigger WHERE ";

	private static final String
		_FILTER_SQL_SELECT_DISPATCHTRIGGER_NO_INLINE_DISTINCT_WHERE_1 =
			"SELECT {DispatchTrigger.*} FROM (SELECT DISTINCT dispatchTrigger.dispatchTriggerId FROM DispatchTrigger dispatchTrigger WHERE ";

	private static final String
		_FILTER_SQL_SELECT_DISPATCHTRIGGER_NO_INLINE_DISTINCT_WHERE_2 =
			") TEMP_TABLE INNER JOIN DispatchTrigger ON TEMP_TABLE.dispatchTriggerId = DispatchTrigger.dispatchTriggerId";

	private static final String _FILTER_SQL_COUNT_DISPATCHTRIGGER_WHERE =
		"SELECT COUNT(DISTINCT dispatchTrigger.dispatchTriggerId) AS COUNT_VALUE FROM DispatchTrigger dispatchTrigger WHERE ";

	private static final String _FILTER_ENTITY_ALIAS = "dispatchTrigger";

	private static final String _FILTER_ENTITY_TABLE = "DispatchTrigger";

	private static final String _ORDER_BY_ENTITY_ALIAS = "dispatchTrigger.";

	private static final String _ORDER_BY_ENTITY_TABLE = "DispatchTrigger.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No DispatchTrigger exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No DispatchTrigger exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		DispatchTriggerPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"active", "system", "type"});

	static {
		try {
			Class.forName(DispatchPersistenceConstants.class.getName());
		}
		catch (ClassNotFoundException classNotFoundException) {
			throw new ExceptionInInitializerError(classNotFoundException);
		}
	}

}