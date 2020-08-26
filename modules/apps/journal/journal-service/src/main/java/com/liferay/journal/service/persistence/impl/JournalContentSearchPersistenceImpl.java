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

package com.liferay.journal.service.persistence.impl;

import com.liferay.journal.exception.NoSuchContentSearchException;
import com.liferay.journal.model.JournalContentSearch;
import com.liferay.journal.model.JournalContentSearchTable;
import com.liferay.journal.model.impl.JournalContentSearchImpl;
import com.liferay.journal.model.impl.JournalContentSearchModelImpl;
import com.liferay.journal.service.persistence.JournalContentSearchPersistence;
import com.liferay.journal.service.persistence.impl.constants.JournalPersistenceConstants;
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
import java.util.Objects;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the journal content search service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = JournalContentSearchPersistence.class)
public class JournalContentSearchPersistenceImpl
	extends BasePersistenceImpl<JournalContentSearch>
	implements JournalContentSearchPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>JournalContentSearchUtil</code> to access the journal content search persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		JournalContentSearchImpl.class.getName();

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
	 * Returns all the journal content searches where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching journal content searches
	 */
	@Override
	public List<JournalContentSearch> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal content searches where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalContentSearchModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of journal content searches
	 * @param end the upper bound of the range of journal content searches (not inclusive)
	 * @return the range of matching journal content searches
	 */
	@Override
	public List<JournalContentSearch> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal content searches where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalContentSearchModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of journal content searches
	 * @param end the upper bound of the range of journal content searches (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal content searches
	 */
	@Override
	public List<JournalContentSearch> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<JournalContentSearch> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the journal content searches where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalContentSearchModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of journal content searches
	 * @param end the upper bound of the range of journal content searches (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal content searches
	 */
	@Override
	public List<JournalContentSearch> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<JournalContentSearch> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			JournalContentSearch.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByCompanyId;
				finderArgs = new Object[] {companyId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByCompanyId;
			finderArgs = new Object[] {
				companyId, start, end, orderByComparator
			};
		}

		List<JournalContentSearch> list = null;

		if (useFinderCache && productionMode) {
			list = (List<JournalContentSearch>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (JournalContentSearch journalContentSearch : list) {
					if (companyId != journalContentSearch.getCompanyId()) {
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

			sb.append(_SQL_SELECT_JOURNALCONTENTSEARCH_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(JournalContentSearchModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				list = (List<JournalContentSearch>)QueryUtil.list(
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
	 * Returns the first journal content search in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal content search
	 * @throws NoSuchContentSearchException if a matching journal content search could not be found
	 */
	@Override
	public JournalContentSearch findByCompanyId_First(
			long companyId,
			OrderByComparator<JournalContentSearch> orderByComparator)
		throws NoSuchContentSearchException {

		JournalContentSearch journalContentSearch = fetchByCompanyId_First(
			companyId, orderByComparator);

		if (journalContentSearch != null) {
			return journalContentSearch;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchContentSearchException(sb.toString());
	}

	/**
	 * Returns the first journal content search in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal content search, or <code>null</code> if a matching journal content search could not be found
	 */
	@Override
	public JournalContentSearch fetchByCompanyId_First(
		long companyId,
		OrderByComparator<JournalContentSearch> orderByComparator) {

		List<JournalContentSearch> list = findByCompanyId(
			companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last journal content search in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal content search
	 * @throws NoSuchContentSearchException if a matching journal content search could not be found
	 */
	@Override
	public JournalContentSearch findByCompanyId_Last(
			long companyId,
			OrderByComparator<JournalContentSearch> orderByComparator)
		throws NoSuchContentSearchException {

		JournalContentSearch journalContentSearch = fetchByCompanyId_Last(
			companyId, orderByComparator);

		if (journalContentSearch != null) {
			return journalContentSearch;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchContentSearchException(sb.toString());
	}

	/**
	 * Returns the last journal content search in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal content search, or <code>null</code> if a matching journal content search could not be found
	 */
	@Override
	public JournalContentSearch fetchByCompanyId_Last(
		long companyId,
		OrderByComparator<JournalContentSearch> orderByComparator) {

		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<JournalContentSearch> list = findByCompanyId(
			companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the journal content searches before and after the current journal content search in the ordered set where companyId = &#63;.
	 *
	 * @param contentSearchId the primary key of the current journal content search
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal content search
	 * @throws NoSuchContentSearchException if a journal content search with the primary key could not be found
	 */
	@Override
	public JournalContentSearch[] findByCompanyId_PrevAndNext(
			long contentSearchId, long companyId,
			OrderByComparator<JournalContentSearch> orderByComparator)
		throws NoSuchContentSearchException {

		JournalContentSearch journalContentSearch = findByPrimaryKey(
			contentSearchId);

		Session session = null;

		try {
			session = openSession();

			JournalContentSearch[] array = new JournalContentSearchImpl[3];

			array[0] = getByCompanyId_PrevAndNext(
				session, journalContentSearch, companyId, orderByComparator,
				true);

			array[1] = journalContentSearch;

			array[2] = getByCompanyId_PrevAndNext(
				session, journalContentSearch, companyId, orderByComparator,
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

	protected JournalContentSearch getByCompanyId_PrevAndNext(
		Session session, JournalContentSearch journalContentSearch,
		long companyId,
		OrderByComparator<JournalContentSearch> orderByComparator,
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

		sb.append(_SQL_SELECT_JOURNALCONTENTSEARCH_WHERE);

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
			sb.append(JournalContentSearchModelImpl.ORDER_BY_JPQL);
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
						journalContentSearch)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<JournalContentSearch> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the journal content searches where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (JournalContentSearch journalContentSearch :
				findByCompanyId(
					companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(journalContentSearch);
		}
	}

	/**
	 * Returns the number of journal content searches where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching journal content searches
	 */
	@Override
	public int countByCompanyId(long companyId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			JournalContentSearch.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByCompanyId;

			finderArgs = new Object[] {companyId};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_JOURNALCONTENTSEARCH_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

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

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 =
		"journalContentSearch.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByPortletId;
	private FinderPath _finderPathWithoutPaginationFindByPortletId;
	private FinderPath _finderPathCountByPortletId;

	/**
	 * Returns all the journal content searches where portletId = &#63;.
	 *
	 * @param portletId the portlet ID
	 * @return the matching journal content searches
	 */
	@Override
	public List<JournalContentSearch> findByPortletId(String portletId) {
		return findByPortletId(
			portletId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal content searches where portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalContentSearchModelImpl</code>.
	 * </p>
	 *
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of journal content searches
	 * @param end the upper bound of the range of journal content searches (not inclusive)
	 * @return the range of matching journal content searches
	 */
	@Override
	public List<JournalContentSearch> findByPortletId(
		String portletId, int start, int end) {

		return findByPortletId(portletId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal content searches where portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalContentSearchModelImpl</code>.
	 * </p>
	 *
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of journal content searches
	 * @param end the upper bound of the range of journal content searches (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal content searches
	 */
	@Override
	public List<JournalContentSearch> findByPortletId(
		String portletId, int start, int end,
		OrderByComparator<JournalContentSearch> orderByComparator) {

		return findByPortletId(portletId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the journal content searches where portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalContentSearchModelImpl</code>.
	 * </p>
	 *
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of journal content searches
	 * @param end the upper bound of the range of journal content searches (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal content searches
	 */
	@Override
	public List<JournalContentSearch> findByPortletId(
		String portletId, int start, int end,
		OrderByComparator<JournalContentSearch> orderByComparator,
		boolean useFinderCache) {

		portletId = Objects.toString(portletId, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			JournalContentSearch.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByPortletId;
				finderArgs = new Object[] {portletId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByPortletId;
			finderArgs = new Object[] {
				portletId, start, end, orderByComparator
			};
		}

		List<JournalContentSearch> list = null;

		if (useFinderCache && productionMode) {
			list = (List<JournalContentSearch>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (JournalContentSearch journalContentSearch : list) {
					if (!portletId.equals(
							journalContentSearch.getPortletId())) {

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

			sb.append(_SQL_SELECT_JOURNALCONTENTSEARCH_WHERE);

			boolean bindPortletId = false;

			if (portletId.isEmpty()) {
				sb.append(_FINDER_COLUMN_PORTLETID_PORTLETID_3);
			}
			else {
				bindPortletId = true;

				sb.append(_FINDER_COLUMN_PORTLETID_PORTLETID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(JournalContentSearchModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindPortletId) {
					queryPos.add(portletId);
				}

				list = (List<JournalContentSearch>)QueryUtil.list(
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
	 * Returns the first journal content search in the ordered set where portletId = &#63;.
	 *
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal content search
	 * @throws NoSuchContentSearchException if a matching journal content search could not be found
	 */
	@Override
	public JournalContentSearch findByPortletId_First(
			String portletId,
			OrderByComparator<JournalContentSearch> orderByComparator)
		throws NoSuchContentSearchException {

		JournalContentSearch journalContentSearch = fetchByPortletId_First(
			portletId, orderByComparator);

		if (journalContentSearch != null) {
			return journalContentSearch;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("portletId=");
		sb.append(portletId);

		sb.append("}");

		throw new NoSuchContentSearchException(sb.toString());
	}

	/**
	 * Returns the first journal content search in the ordered set where portletId = &#63;.
	 *
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal content search, or <code>null</code> if a matching journal content search could not be found
	 */
	@Override
	public JournalContentSearch fetchByPortletId_First(
		String portletId,
		OrderByComparator<JournalContentSearch> orderByComparator) {

		List<JournalContentSearch> list = findByPortletId(
			portletId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last journal content search in the ordered set where portletId = &#63;.
	 *
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal content search
	 * @throws NoSuchContentSearchException if a matching journal content search could not be found
	 */
	@Override
	public JournalContentSearch findByPortletId_Last(
			String portletId,
			OrderByComparator<JournalContentSearch> orderByComparator)
		throws NoSuchContentSearchException {

		JournalContentSearch journalContentSearch = fetchByPortletId_Last(
			portletId, orderByComparator);

		if (journalContentSearch != null) {
			return journalContentSearch;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("portletId=");
		sb.append(portletId);

		sb.append("}");

		throw new NoSuchContentSearchException(sb.toString());
	}

	/**
	 * Returns the last journal content search in the ordered set where portletId = &#63;.
	 *
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal content search, or <code>null</code> if a matching journal content search could not be found
	 */
	@Override
	public JournalContentSearch fetchByPortletId_Last(
		String portletId,
		OrderByComparator<JournalContentSearch> orderByComparator) {

		int count = countByPortletId(portletId);

		if (count == 0) {
			return null;
		}

		List<JournalContentSearch> list = findByPortletId(
			portletId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the journal content searches before and after the current journal content search in the ordered set where portletId = &#63;.
	 *
	 * @param contentSearchId the primary key of the current journal content search
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal content search
	 * @throws NoSuchContentSearchException if a journal content search with the primary key could not be found
	 */
	@Override
	public JournalContentSearch[] findByPortletId_PrevAndNext(
			long contentSearchId, String portletId,
			OrderByComparator<JournalContentSearch> orderByComparator)
		throws NoSuchContentSearchException {

		portletId = Objects.toString(portletId, "");

		JournalContentSearch journalContentSearch = findByPrimaryKey(
			contentSearchId);

		Session session = null;

		try {
			session = openSession();

			JournalContentSearch[] array = new JournalContentSearchImpl[3];

			array[0] = getByPortletId_PrevAndNext(
				session, journalContentSearch, portletId, orderByComparator,
				true);

			array[1] = journalContentSearch;

			array[2] = getByPortletId_PrevAndNext(
				session, journalContentSearch, portletId, orderByComparator,
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

	protected JournalContentSearch getByPortletId_PrevAndNext(
		Session session, JournalContentSearch journalContentSearch,
		String portletId,
		OrderByComparator<JournalContentSearch> orderByComparator,
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

		sb.append(_SQL_SELECT_JOURNALCONTENTSEARCH_WHERE);

		boolean bindPortletId = false;

		if (portletId.isEmpty()) {
			sb.append(_FINDER_COLUMN_PORTLETID_PORTLETID_3);
		}
		else {
			bindPortletId = true;

			sb.append(_FINDER_COLUMN_PORTLETID_PORTLETID_2);
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
			sb.append(JournalContentSearchModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindPortletId) {
			queryPos.add(portletId);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						journalContentSearch)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<JournalContentSearch> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the journal content searches where portletId = &#63; from the database.
	 *
	 * @param portletId the portlet ID
	 */
	@Override
	public void removeByPortletId(String portletId) {
		for (JournalContentSearch journalContentSearch :
				findByPortletId(
					portletId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(journalContentSearch);
		}
	}

	/**
	 * Returns the number of journal content searches where portletId = &#63;.
	 *
	 * @param portletId the portlet ID
	 * @return the number of matching journal content searches
	 */
	@Override
	public int countByPortletId(String portletId) {
		portletId = Objects.toString(portletId, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			JournalContentSearch.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByPortletId;

			finderArgs = new Object[] {portletId};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_JOURNALCONTENTSEARCH_WHERE);

			boolean bindPortletId = false;

			if (portletId.isEmpty()) {
				sb.append(_FINDER_COLUMN_PORTLETID_PORTLETID_3);
			}
			else {
				bindPortletId = true;

				sb.append(_FINDER_COLUMN_PORTLETID_PORTLETID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindPortletId) {
					queryPos.add(portletId);
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

	private static final String _FINDER_COLUMN_PORTLETID_PORTLETID_2 =
		"journalContentSearch.portletId = ?";

	private static final String _FINDER_COLUMN_PORTLETID_PORTLETID_3 =
		"(journalContentSearch.portletId IS NULL OR journalContentSearch.portletId = '')";

	private FinderPath _finderPathWithPaginationFindByArticleId;
	private FinderPath _finderPathWithoutPaginationFindByArticleId;
	private FinderPath _finderPathCountByArticleId;

	/**
	 * Returns all the journal content searches where articleId = &#63;.
	 *
	 * @param articleId the article ID
	 * @return the matching journal content searches
	 */
	@Override
	public List<JournalContentSearch> findByArticleId(String articleId) {
		return findByArticleId(
			articleId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal content searches where articleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalContentSearchModelImpl</code>.
	 * </p>
	 *
	 * @param articleId the article ID
	 * @param start the lower bound of the range of journal content searches
	 * @param end the upper bound of the range of journal content searches (not inclusive)
	 * @return the range of matching journal content searches
	 */
	@Override
	public List<JournalContentSearch> findByArticleId(
		String articleId, int start, int end) {

		return findByArticleId(articleId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal content searches where articleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalContentSearchModelImpl</code>.
	 * </p>
	 *
	 * @param articleId the article ID
	 * @param start the lower bound of the range of journal content searches
	 * @param end the upper bound of the range of journal content searches (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal content searches
	 */
	@Override
	public List<JournalContentSearch> findByArticleId(
		String articleId, int start, int end,
		OrderByComparator<JournalContentSearch> orderByComparator) {

		return findByArticleId(articleId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the journal content searches where articleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalContentSearchModelImpl</code>.
	 * </p>
	 *
	 * @param articleId the article ID
	 * @param start the lower bound of the range of journal content searches
	 * @param end the upper bound of the range of journal content searches (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal content searches
	 */
	@Override
	public List<JournalContentSearch> findByArticleId(
		String articleId, int start, int end,
		OrderByComparator<JournalContentSearch> orderByComparator,
		boolean useFinderCache) {

		articleId = Objects.toString(articleId, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			JournalContentSearch.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByArticleId;
				finderArgs = new Object[] {articleId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByArticleId;
			finderArgs = new Object[] {
				articleId, start, end, orderByComparator
			};
		}

		List<JournalContentSearch> list = null;

		if (useFinderCache && productionMode) {
			list = (List<JournalContentSearch>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (JournalContentSearch journalContentSearch : list) {
					if (!articleId.equals(
							journalContentSearch.getArticleId())) {

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

			sb.append(_SQL_SELECT_JOURNALCONTENTSEARCH_WHERE);

			boolean bindArticleId = false;

			if (articleId.isEmpty()) {
				sb.append(_FINDER_COLUMN_ARTICLEID_ARTICLEID_3);
			}
			else {
				bindArticleId = true;

				sb.append(_FINDER_COLUMN_ARTICLEID_ARTICLEID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(JournalContentSearchModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindArticleId) {
					queryPos.add(articleId);
				}

				list = (List<JournalContentSearch>)QueryUtil.list(
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
	 * Returns the first journal content search in the ordered set where articleId = &#63;.
	 *
	 * @param articleId the article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal content search
	 * @throws NoSuchContentSearchException if a matching journal content search could not be found
	 */
	@Override
	public JournalContentSearch findByArticleId_First(
			String articleId,
			OrderByComparator<JournalContentSearch> orderByComparator)
		throws NoSuchContentSearchException {

		JournalContentSearch journalContentSearch = fetchByArticleId_First(
			articleId, orderByComparator);

		if (journalContentSearch != null) {
			return journalContentSearch;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("articleId=");
		sb.append(articleId);

		sb.append("}");

		throw new NoSuchContentSearchException(sb.toString());
	}

	/**
	 * Returns the first journal content search in the ordered set where articleId = &#63;.
	 *
	 * @param articleId the article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal content search, or <code>null</code> if a matching journal content search could not be found
	 */
	@Override
	public JournalContentSearch fetchByArticleId_First(
		String articleId,
		OrderByComparator<JournalContentSearch> orderByComparator) {

		List<JournalContentSearch> list = findByArticleId(
			articleId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last journal content search in the ordered set where articleId = &#63;.
	 *
	 * @param articleId the article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal content search
	 * @throws NoSuchContentSearchException if a matching journal content search could not be found
	 */
	@Override
	public JournalContentSearch findByArticleId_Last(
			String articleId,
			OrderByComparator<JournalContentSearch> orderByComparator)
		throws NoSuchContentSearchException {

		JournalContentSearch journalContentSearch = fetchByArticleId_Last(
			articleId, orderByComparator);

		if (journalContentSearch != null) {
			return journalContentSearch;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("articleId=");
		sb.append(articleId);

		sb.append("}");

		throw new NoSuchContentSearchException(sb.toString());
	}

	/**
	 * Returns the last journal content search in the ordered set where articleId = &#63;.
	 *
	 * @param articleId the article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal content search, or <code>null</code> if a matching journal content search could not be found
	 */
	@Override
	public JournalContentSearch fetchByArticleId_Last(
		String articleId,
		OrderByComparator<JournalContentSearch> orderByComparator) {

		int count = countByArticleId(articleId);

		if (count == 0) {
			return null;
		}

		List<JournalContentSearch> list = findByArticleId(
			articleId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the journal content searches before and after the current journal content search in the ordered set where articleId = &#63;.
	 *
	 * @param contentSearchId the primary key of the current journal content search
	 * @param articleId the article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal content search
	 * @throws NoSuchContentSearchException if a journal content search with the primary key could not be found
	 */
	@Override
	public JournalContentSearch[] findByArticleId_PrevAndNext(
			long contentSearchId, String articleId,
			OrderByComparator<JournalContentSearch> orderByComparator)
		throws NoSuchContentSearchException {

		articleId = Objects.toString(articleId, "");

		JournalContentSearch journalContentSearch = findByPrimaryKey(
			contentSearchId);

		Session session = null;

		try {
			session = openSession();

			JournalContentSearch[] array = new JournalContentSearchImpl[3];

			array[0] = getByArticleId_PrevAndNext(
				session, journalContentSearch, articleId, orderByComparator,
				true);

			array[1] = journalContentSearch;

			array[2] = getByArticleId_PrevAndNext(
				session, journalContentSearch, articleId, orderByComparator,
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

	protected JournalContentSearch getByArticleId_PrevAndNext(
		Session session, JournalContentSearch journalContentSearch,
		String articleId,
		OrderByComparator<JournalContentSearch> orderByComparator,
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

		sb.append(_SQL_SELECT_JOURNALCONTENTSEARCH_WHERE);

		boolean bindArticleId = false;

		if (articleId.isEmpty()) {
			sb.append(_FINDER_COLUMN_ARTICLEID_ARTICLEID_3);
		}
		else {
			bindArticleId = true;

			sb.append(_FINDER_COLUMN_ARTICLEID_ARTICLEID_2);
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
			sb.append(JournalContentSearchModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindArticleId) {
			queryPos.add(articleId);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						journalContentSearch)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<JournalContentSearch> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the journal content searches where articleId = &#63; from the database.
	 *
	 * @param articleId the article ID
	 */
	@Override
	public void removeByArticleId(String articleId) {
		for (JournalContentSearch journalContentSearch :
				findByArticleId(
					articleId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(journalContentSearch);
		}
	}

	/**
	 * Returns the number of journal content searches where articleId = &#63;.
	 *
	 * @param articleId the article ID
	 * @return the number of matching journal content searches
	 */
	@Override
	public int countByArticleId(String articleId) {
		articleId = Objects.toString(articleId, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			JournalContentSearch.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByArticleId;

			finderArgs = new Object[] {articleId};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_JOURNALCONTENTSEARCH_WHERE);

			boolean bindArticleId = false;

			if (articleId.isEmpty()) {
				sb.append(_FINDER_COLUMN_ARTICLEID_ARTICLEID_3);
			}
			else {
				bindArticleId = true;

				sb.append(_FINDER_COLUMN_ARTICLEID_ARTICLEID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindArticleId) {
					queryPos.add(articleId);
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

	private static final String _FINDER_COLUMN_ARTICLEID_ARTICLEID_2 =
		"journalContentSearch.articleId = ?";

	private static final String _FINDER_COLUMN_ARTICLEID_ARTICLEID_3 =
		"(journalContentSearch.articleId IS NULL OR journalContentSearch.articleId = '')";

	private FinderPath _finderPathWithPaginationFindByG_P;
	private FinderPath _finderPathWithoutPaginationFindByG_P;
	private FinderPath _finderPathCountByG_P;

	/**
	 * Returns all the journal content searches where groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @return the matching journal content searches
	 */
	@Override
	public List<JournalContentSearch> findByG_P(
		long groupId, boolean privateLayout) {

		return findByG_P(
			groupId, privateLayout, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal content searches where groupId = &#63; and privateLayout = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalContentSearchModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param start the lower bound of the range of journal content searches
	 * @param end the upper bound of the range of journal content searches (not inclusive)
	 * @return the range of matching journal content searches
	 */
	@Override
	public List<JournalContentSearch> findByG_P(
		long groupId, boolean privateLayout, int start, int end) {

		return findByG_P(groupId, privateLayout, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal content searches where groupId = &#63; and privateLayout = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalContentSearchModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param start the lower bound of the range of journal content searches
	 * @param end the upper bound of the range of journal content searches (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal content searches
	 */
	@Override
	public List<JournalContentSearch> findByG_P(
		long groupId, boolean privateLayout, int start, int end,
		OrderByComparator<JournalContentSearch> orderByComparator) {

		return findByG_P(
			groupId, privateLayout, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the journal content searches where groupId = &#63; and privateLayout = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalContentSearchModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param start the lower bound of the range of journal content searches
	 * @param end the upper bound of the range of journal content searches (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal content searches
	 */
	@Override
	public List<JournalContentSearch> findByG_P(
		long groupId, boolean privateLayout, int start, int end,
		OrderByComparator<JournalContentSearch> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			JournalContentSearch.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByG_P;
				finderArgs = new Object[] {groupId, privateLayout};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByG_P;
			finderArgs = new Object[] {
				groupId, privateLayout, start, end, orderByComparator
			};
		}

		List<JournalContentSearch> list = null;

		if (useFinderCache && productionMode) {
			list = (List<JournalContentSearch>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (JournalContentSearch journalContentSearch : list) {
					if ((groupId != journalContentSearch.getGroupId()) ||
						(privateLayout !=
							journalContentSearch.isPrivateLayout())) {

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

			sb.append(_SQL_SELECT_JOURNALCONTENTSEARCH_WHERE);

			sb.append(_FINDER_COLUMN_G_P_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_P_PRIVATELAYOUT_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(JournalContentSearchModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(privateLayout);

				list = (List<JournalContentSearch>)QueryUtil.list(
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
	 * Returns the first journal content search in the ordered set where groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal content search
	 * @throws NoSuchContentSearchException if a matching journal content search could not be found
	 */
	@Override
	public JournalContentSearch findByG_P_First(
			long groupId, boolean privateLayout,
			OrderByComparator<JournalContentSearch> orderByComparator)
		throws NoSuchContentSearchException {

		JournalContentSearch journalContentSearch = fetchByG_P_First(
			groupId, privateLayout, orderByComparator);

		if (journalContentSearch != null) {
			return journalContentSearch;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", privateLayout=");
		sb.append(privateLayout);

		sb.append("}");

		throw new NoSuchContentSearchException(sb.toString());
	}

	/**
	 * Returns the first journal content search in the ordered set where groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal content search, or <code>null</code> if a matching journal content search could not be found
	 */
	@Override
	public JournalContentSearch fetchByG_P_First(
		long groupId, boolean privateLayout,
		OrderByComparator<JournalContentSearch> orderByComparator) {

		List<JournalContentSearch> list = findByG_P(
			groupId, privateLayout, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last journal content search in the ordered set where groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal content search
	 * @throws NoSuchContentSearchException if a matching journal content search could not be found
	 */
	@Override
	public JournalContentSearch findByG_P_Last(
			long groupId, boolean privateLayout,
			OrderByComparator<JournalContentSearch> orderByComparator)
		throws NoSuchContentSearchException {

		JournalContentSearch journalContentSearch = fetchByG_P_Last(
			groupId, privateLayout, orderByComparator);

		if (journalContentSearch != null) {
			return journalContentSearch;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", privateLayout=");
		sb.append(privateLayout);

		sb.append("}");

		throw new NoSuchContentSearchException(sb.toString());
	}

	/**
	 * Returns the last journal content search in the ordered set where groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal content search, or <code>null</code> if a matching journal content search could not be found
	 */
	@Override
	public JournalContentSearch fetchByG_P_Last(
		long groupId, boolean privateLayout,
		OrderByComparator<JournalContentSearch> orderByComparator) {

		int count = countByG_P(groupId, privateLayout);

		if (count == 0) {
			return null;
		}

		List<JournalContentSearch> list = findByG_P(
			groupId, privateLayout, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the journal content searches before and after the current journal content search in the ordered set where groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param contentSearchId the primary key of the current journal content search
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal content search
	 * @throws NoSuchContentSearchException if a journal content search with the primary key could not be found
	 */
	@Override
	public JournalContentSearch[] findByG_P_PrevAndNext(
			long contentSearchId, long groupId, boolean privateLayout,
			OrderByComparator<JournalContentSearch> orderByComparator)
		throws NoSuchContentSearchException {

		JournalContentSearch journalContentSearch = findByPrimaryKey(
			contentSearchId);

		Session session = null;

		try {
			session = openSession();

			JournalContentSearch[] array = new JournalContentSearchImpl[3];

			array[0] = getByG_P_PrevAndNext(
				session, journalContentSearch, groupId, privateLayout,
				orderByComparator, true);

			array[1] = journalContentSearch;

			array[2] = getByG_P_PrevAndNext(
				session, journalContentSearch, groupId, privateLayout,
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

	protected JournalContentSearch getByG_P_PrevAndNext(
		Session session, JournalContentSearch journalContentSearch,
		long groupId, boolean privateLayout,
		OrderByComparator<JournalContentSearch> orderByComparator,
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

		sb.append(_SQL_SELECT_JOURNALCONTENTSEARCH_WHERE);

		sb.append(_FINDER_COLUMN_G_P_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_P_PRIVATELAYOUT_2);

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
			sb.append(JournalContentSearchModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(privateLayout);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						journalContentSearch)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<JournalContentSearch> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the journal content searches where groupId = &#63; and privateLayout = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 */
	@Override
	public void removeByG_P(long groupId, boolean privateLayout) {
		for (JournalContentSearch journalContentSearch :
				findByG_P(
					groupId, privateLayout, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(journalContentSearch);
		}
	}

	/**
	 * Returns the number of journal content searches where groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @return the number of matching journal content searches
	 */
	@Override
	public int countByG_P(long groupId, boolean privateLayout) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			JournalContentSearch.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_P;

			finderArgs = new Object[] {groupId, privateLayout};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_JOURNALCONTENTSEARCH_WHERE);

			sb.append(_FINDER_COLUMN_G_P_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_P_PRIVATELAYOUT_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(privateLayout);

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

	private static final String _FINDER_COLUMN_G_P_GROUPID_2 =
		"journalContentSearch.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_P_PRIVATELAYOUT_2 =
		"journalContentSearch.privateLayout = ?";

	private FinderPath _finderPathWithPaginationFindByG_A;
	private FinderPath _finderPathWithoutPaginationFindByG_A;
	private FinderPath _finderPathCountByG_A;

	/**
	 * Returns all the journal content searches where groupId = &#63; and articleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @return the matching journal content searches
	 */
	@Override
	public List<JournalContentSearch> findByG_A(
		long groupId, String articleId) {

		return findByG_A(
			groupId, articleId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal content searches where groupId = &#63; and articleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalContentSearchModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param start the lower bound of the range of journal content searches
	 * @param end the upper bound of the range of journal content searches (not inclusive)
	 * @return the range of matching journal content searches
	 */
	@Override
	public List<JournalContentSearch> findByG_A(
		long groupId, String articleId, int start, int end) {

		return findByG_A(groupId, articleId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal content searches where groupId = &#63; and articleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalContentSearchModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param start the lower bound of the range of journal content searches
	 * @param end the upper bound of the range of journal content searches (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal content searches
	 */
	@Override
	public List<JournalContentSearch> findByG_A(
		long groupId, String articleId, int start, int end,
		OrderByComparator<JournalContentSearch> orderByComparator) {

		return findByG_A(
			groupId, articleId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the journal content searches where groupId = &#63; and articleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalContentSearchModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param start the lower bound of the range of journal content searches
	 * @param end the upper bound of the range of journal content searches (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal content searches
	 */
	@Override
	public List<JournalContentSearch> findByG_A(
		long groupId, String articleId, int start, int end,
		OrderByComparator<JournalContentSearch> orderByComparator,
		boolean useFinderCache) {

		articleId = Objects.toString(articleId, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			JournalContentSearch.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByG_A;
				finderArgs = new Object[] {groupId, articleId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByG_A;
			finderArgs = new Object[] {
				groupId, articleId, start, end, orderByComparator
			};
		}

		List<JournalContentSearch> list = null;

		if (useFinderCache && productionMode) {
			list = (List<JournalContentSearch>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (JournalContentSearch journalContentSearch : list) {
					if ((groupId != journalContentSearch.getGroupId()) ||
						!articleId.equals(
							journalContentSearch.getArticleId())) {

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

			sb.append(_SQL_SELECT_JOURNALCONTENTSEARCH_WHERE);

			sb.append(_FINDER_COLUMN_G_A_GROUPID_2);

			boolean bindArticleId = false;

			if (articleId.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_A_ARTICLEID_3);
			}
			else {
				bindArticleId = true;

				sb.append(_FINDER_COLUMN_G_A_ARTICLEID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(JournalContentSearchModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindArticleId) {
					queryPos.add(articleId);
				}

				list = (List<JournalContentSearch>)QueryUtil.list(
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
	 * Returns the first journal content search in the ordered set where groupId = &#63; and articleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal content search
	 * @throws NoSuchContentSearchException if a matching journal content search could not be found
	 */
	@Override
	public JournalContentSearch findByG_A_First(
			long groupId, String articleId,
			OrderByComparator<JournalContentSearch> orderByComparator)
		throws NoSuchContentSearchException {

		JournalContentSearch journalContentSearch = fetchByG_A_First(
			groupId, articleId, orderByComparator);

		if (journalContentSearch != null) {
			return journalContentSearch;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", articleId=");
		sb.append(articleId);

		sb.append("}");

		throw new NoSuchContentSearchException(sb.toString());
	}

	/**
	 * Returns the first journal content search in the ordered set where groupId = &#63; and articleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal content search, or <code>null</code> if a matching journal content search could not be found
	 */
	@Override
	public JournalContentSearch fetchByG_A_First(
		long groupId, String articleId,
		OrderByComparator<JournalContentSearch> orderByComparator) {

		List<JournalContentSearch> list = findByG_A(
			groupId, articleId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last journal content search in the ordered set where groupId = &#63; and articleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal content search
	 * @throws NoSuchContentSearchException if a matching journal content search could not be found
	 */
	@Override
	public JournalContentSearch findByG_A_Last(
			long groupId, String articleId,
			OrderByComparator<JournalContentSearch> orderByComparator)
		throws NoSuchContentSearchException {

		JournalContentSearch journalContentSearch = fetchByG_A_Last(
			groupId, articleId, orderByComparator);

		if (journalContentSearch != null) {
			return journalContentSearch;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", articleId=");
		sb.append(articleId);

		sb.append("}");

		throw new NoSuchContentSearchException(sb.toString());
	}

	/**
	 * Returns the last journal content search in the ordered set where groupId = &#63; and articleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal content search, or <code>null</code> if a matching journal content search could not be found
	 */
	@Override
	public JournalContentSearch fetchByG_A_Last(
		long groupId, String articleId,
		OrderByComparator<JournalContentSearch> orderByComparator) {

		int count = countByG_A(groupId, articleId);

		if (count == 0) {
			return null;
		}

		List<JournalContentSearch> list = findByG_A(
			groupId, articleId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the journal content searches before and after the current journal content search in the ordered set where groupId = &#63; and articleId = &#63;.
	 *
	 * @param contentSearchId the primary key of the current journal content search
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal content search
	 * @throws NoSuchContentSearchException if a journal content search with the primary key could not be found
	 */
	@Override
	public JournalContentSearch[] findByG_A_PrevAndNext(
			long contentSearchId, long groupId, String articleId,
			OrderByComparator<JournalContentSearch> orderByComparator)
		throws NoSuchContentSearchException {

		articleId = Objects.toString(articleId, "");

		JournalContentSearch journalContentSearch = findByPrimaryKey(
			contentSearchId);

		Session session = null;

		try {
			session = openSession();

			JournalContentSearch[] array = new JournalContentSearchImpl[3];

			array[0] = getByG_A_PrevAndNext(
				session, journalContentSearch, groupId, articleId,
				orderByComparator, true);

			array[1] = journalContentSearch;

			array[2] = getByG_A_PrevAndNext(
				session, journalContentSearch, groupId, articleId,
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

	protected JournalContentSearch getByG_A_PrevAndNext(
		Session session, JournalContentSearch journalContentSearch,
		long groupId, String articleId,
		OrderByComparator<JournalContentSearch> orderByComparator,
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

		sb.append(_SQL_SELECT_JOURNALCONTENTSEARCH_WHERE);

		sb.append(_FINDER_COLUMN_G_A_GROUPID_2);

		boolean bindArticleId = false;

		if (articleId.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_A_ARTICLEID_3);
		}
		else {
			bindArticleId = true;

			sb.append(_FINDER_COLUMN_G_A_ARTICLEID_2);
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
			sb.append(JournalContentSearchModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		if (bindArticleId) {
			queryPos.add(articleId);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						journalContentSearch)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<JournalContentSearch> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the journal content searches where groupId = &#63; and articleId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 */
	@Override
	public void removeByG_A(long groupId, String articleId) {
		for (JournalContentSearch journalContentSearch :
				findByG_A(
					groupId, articleId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(journalContentSearch);
		}
	}

	/**
	 * Returns the number of journal content searches where groupId = &#63; and articleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @return the number of matching journal content searches
	 */
	@Override
	public int countByG_A(long groupId, String articleId) {
		articleId = Objects.toString(articleId, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			JournalContentSearch.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_A;

			finderArgs = new Object[] {groupId, articleId};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_JOURNALCONTENTSEARCH_WHERE);

			sb.append(_FINDER_COLUMN_G_A_GROUPID_2);

			boolean bindArticleId = false;

			if (articleId.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_A_ARTICLEID_3);
			}
			else {
				bindArticleId = true;

				sb.append(_FINDER_COLUMN_G_A_ARTICLEID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindArticleId) {
					queryPos.add(articleId);
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

	private static final String _FINDER_COLUMN_G_A_GROUPID_2 =
		"journalContentSearch.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_A_ARTICLEID_2 =
		"journalContentSearch.articleId = ?";

	private static final String _FINDER_COLUMN_G_A_ARTICLEID_3 =
		"(journalContentSearch.articleId IS NULL OR journalContentSearch.articleId = '')";

	private FinderPath _finderPathWithPaginationFindByG_P_L;
	private FinderPath _finderPathWithoutPaginationFindByG_P_L;
	private FinderPath _finderPathCountByG_P_L;

	/**
	 * Returns all the journal content searches where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @return the matching journal content searches
	 */
	@Override
	public List<JournalContentSearch> findByG_P_L(
		long groupId, boolean privateLayout, long layoutId) {

		return findByG_P_L(
			groupId, privateLayout, layoutId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal content searches where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalContentSearchModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param start the lower bound of the range of journal content searches
	 * @param end the upper bound of the range of journal content searches (not inclusive)
	 * @return the range of matching journal content searches
	 */
	@Override
	public List<JournalContentSearch> findByG_P_L(
		long groupId, boolean privateLayout, long layoutId, int start,
		int end) {

		return findByG_P_L(groupId, privateLayout, layoutId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal content searches where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalContentSearchModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param start the lower bound of the range of journal content searches
	 * @param end the upper bound of the range of journal content searches (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal content searches
	 */
	@Override
	public List<JournalContentSearch> findByG_P_L(
		long groupId, boolean privateLayout, long layoutId, int start, int end,
		OrderByComparator<JournalContentSearch> orderByComparator) {

		return findByG_P_L(
			groupId, privateLayout, layoutId, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the journal content searches where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalContentSearchModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param start the lower bound of the range of journal content searches
	 * @param end the upper bound of the range of journal content searches (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal content searches
	 */
	@Override
	public List<JournalContentSearch> findByG_P_L(
		long groupId, boolean privateLayout, long layoutId, int start, int end,
		OrderByComparator<JournalContentSearch> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			JournalContentSearch.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByG_P_L;
				finderArgs = new Object[] {groupId, privateLayout, layoutId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByG_P_L;
			finderArgs = new Object[] {
				groupId, privateLayout, layoutId, start, end, orderByComparator
			};
		}

		List<JournalContentSearch> list = null;

		if (useFinderCache && productionMode) {
			list = (List<JournalContentSearch>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (JournalContentSearch journalContentSearch : list) {
					if ((groupId != journalContentSearch.getGroupId()) ||
						(privateLayout !=
							journalContentSearch.isPrivateLayout()) ||
						(layoutId != journalContentSearch.getLayoutId())) {

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

			sb.append(_SQL_SELECT_JOURNALCONTENTSEARCH_WHERE);

			sb.append(_FINDER_COLUMN_G_P_L_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_P_L_PRIVATELAYOUT_2);

			sb.append(_FINDER_COLUMN_G_P_L_LAYOUTID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(JournalContentSearchModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(privateLayout);

				queryPos.add(layoutId);

				list = (List<JournalContentSearch>)QueryUtil.list(
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
	 * Returns the first journal content search in the ordered set where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal content search
	 * @throws NoSuchContentSearchException if a matching journal content search could not be found
	 */
	@Override
	public JournalContentSearch findByG_P_L_First(
			long groupId, boolean privateLayout, long layoutId,
			OrderByComparator<JournalContentSearch> orderByComparator)
		throws NoSuchContentSearchException {

		JournalContentSearch journalContentSearch = fetchByG_P_L_First(
			groupId, privateLayout, layoutId, orderByComparator);

		if (journalContentSearch != null) {
			return journalContentSearch;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", privateLayout=");
		sb.append(privateLayout);

		sb.append(", layoutId=");
		sb.append(layoutId);

		sb.append("}");

		throw new NoSuchContentSearchException(sb.toString());
	}

	/**
	 * Returns the first journal content search in the ordered set where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal content search, or <code>null</code> if a matching journal content search could not be found
	 */
	@Override
	public JournalContentSearch fetchByG_P_L_First(
		long groupId, boolean privateLayout, long layoutId,
		OrderByComparator<JournalContentSearch> orderByComparator) {

		List<JournalContentSearch> list = findByG_P_L(
			groupId, privateLayout, layoutId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last journal content search in the ordered set where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal content search
	 * @throws NoSuchContentSearchException if a matching journal content search could not be found
	 */
	@Override
	public JournalContentSearch findByG_P_L_Last(
			long groupId, boolean privateLayout, long layoutId,
			OrderByComparator<JournalContentSearch> orderByComparator)
		throws NoSuchContentSearchException {

		JournalContentSearch journalContentSearch = fetchByG_P_L_Last(
			groupId, privateLayout, layoutId, orderByComparator);

		if (journalContentSearch != null) {
			return journalContentSearch;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", privateLayout=");
		sb.append(privateLayout);

		sb.append(", layoutId=");
		sb.append(layoutId);

		sb.append("}");

		throw new NoSuchContentSearchException(sb.toString());
	}

	/**
	 * Returns the last journal content search in the ordered set where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal content search, or <code>null</code> if a matching journal content search could not be found
	 */
	@Override
	public JournalContentSearch fetchByG_P_L_Last(
		long groupId, boolean privateLayout, long layoutId,
		OrderByComparator<JournalContentSearch> orderByComparator) {

		int count = countByG_P_L(groupId, privateLayout, layoutId);

		if (count == 0) {
			return null;
		}

		List<JournalContentSearch> list = findByG_P_L(
			groupId, privateLayout, layoutId, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the journal content searches before and after the current journal content search in the ordered set where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	 *
	 * @param contentSearchId the primary key of the current journal content search
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal content search
	 * @throws NoSuchContentSearchException if a journal content search with the primary key could not be found
	 */
	@Override
	public JournalContentSearch[] findByG_P_L_PrevAndNext(
			long contentSearchId, long groupId, boolean privateLayout,
			long layoutId,
			OrderByComparator<JournalContentSearch> orderByComparator)
		throws NoSuchContentSearchException {

		JournalContentSearch journalContentSearch = findByPrimaryKey(
			contentSearchId);

		Session session = null;

		try {
			session = openSession();

			JournalContentSearch[] array = new JournalContentSearchImpl[3];

			array[0] = getByG_P_L_PrevAndNext(
				session, journalContentSearch, groupId, privateLayout, layoutId,
				orderByComparator, true);

			array[1] = journalContentSearch;

			array[2] = getByG_P_L_PrevAndNext(
				session, journalContentSearch, groupId, privateLayout, layoutId,
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

	protected JournalContentSearch getByG_P_L_PrevAndNext(
		Session session, JournalContentSearch journalContentSearch,
		long groupId, boolean privateLayout, long layoutId,
		OrderByComparator<JournalContentSearch> orderByComparator,
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

		sb.append(_SQL_SELECT_JOURNALCONTENTSEARCH_WHERE);

		sb.append(_FINDER_COLUMN_G_P_L_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_P_L_PRIVATELAYOUT_2);

		sb.append(_FINDER_COLUMN_G_P_L_LAYOUTID_2);

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
			sb.append(JournalContentSearchModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(privateLayout);

		queryPos.add(layoutId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						journalContentSearch)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<JournalContentSearch> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the journal content searches where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 */
	@Override
	public void removeByG_P_L(
		long groupId, boolean privateLayout, long layoutId) {

		for (JournalContentSearch journalContentSearch :
				findByG_P_L(
					groupId, privateLayout, layoutId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(journalContentSearch);
		}
	}

	/**
	 * Returns the number of journal content searches where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @return the number of matching journal content searches
	 */
	@Override
	public int countByG_P_L(
		long groupId, boolean privateLayout, long layoutId) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			JournalContentSearch.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_P_L;

			finderArgs = new Object[] {groupId, privateLayout, layoutId};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_JOURNALCONTENTSEARCH_WHERE);

			sb.append(_FINDER_COLUMN_G_P_L_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_P_L_PRIVATELAYOUT_2);

			sb.append(_FINDER_COLUMN_G_P_L_LAYOUTID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(privateLayout);

				queryPos.add(layoutId);

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

	private static final String _FINDER_COLUMN_G_P_L_GROUPID_2 =
		"journalContentSearch.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_P_L_PRIVATELAYOUT_2 =
		"journalContentSearch.privateLayout = ? AND ";

	private static final String _FINDER_COLUMN_G_P_L_LAYOUTID_2 =
		"journalContentSearch.layoutId = ?";

	private FinderPath _finderPathWithPaginationFindByG_P_A;
	private FinderPath _finderPathWithoutPaginationFindByG_P_A;
	private FinderPath _finderPathCountByG_P_A;

	/**
	 * Returns all the journal content searches where groupId = &#63; and privateLayout = &#63; and articleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param articleId the article ID
	 * @return the matching journal content searches
	 */
	@Override
	public List<JournalContentSearch> findByG_P_A(
		long groupId, boolean privateLayout, String articleId) {

		return findByG_P_A(
			groupId, privateLayout, articleId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal content searches where groupId = &#63; and privateLayout = &#63; and articleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalContentSearchModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param articleId the article ID
	 * @param start the lower bound of the range of journal content searches
	 * @param end the upper bound of the range of journal content searches (not inclusive)
	 * @return the range of matching journal content searches
	 */
	@Override
	public List<JournalContentSearch> findByG_P_A(
		long groupId, boolean privateLayout, String articleId, int start,
		int end) {

		return findByG_P_A(groupId, privateLayout, articleId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal content searches where groupId = &#63; and privateLayout = &#63; and articleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalContentSearchModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param articleId the article ID
	 * @param start the lower bound of the range of journal content searches
	 * @param end the upper bound of the range of journal content searches (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal content searches
	 */
	@Override
	public List<JournalContentSearch> findByG_P_A(
		long groupId, boolean privateLayout, String articleId, int start,
		int end, OrderByComparator<JournalContentSearch> orderByComparator) {

		return findByG_P_A(
			groupId, privateLayout, articleId, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the journal content searches where groupId = &#63; and privateLayout = &#63; and articleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalContentSearchModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param articleId the article ID
	 * @param start the lower bound of the range of journal content searches
	 * @param end the upper bound of the range of journal content searches (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal content searches
	 */
	@Override
	public List<JournalContentSearch> findByG_P_A(
		long groupId, boolean privateLayout, String articleId, int start,
		int end, OrderByComparator<JournalContentSearch> orderByComparator,
		boolean useFinderCache) {

		articleId = Objects.toString(articleId, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			JournalContentSearch.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByG_P_A;
				finderArgs = new Object[] {groupId, privateLayout, articleId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByG_P_A;
			finderArgs = new Object[] {
				groupId, privateLayout, articleId, start, end, orderByComparator
			};
		}

		List<JournalContentSearch> list = null;

		if (useFinderCache && productionMode) {
			list = (List<JournalContentSearch>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (JournalContentSearch journalContentSearch : list) {
					if ((groupId != journalContentSearch.getGroupId()) ||
						(privateLayout !=
							journalContentSearch.isPrivateLayout()) ||
						!articleId.equals(
							journalContentSearch.getArticleId())) {

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

			sb.append(_SQL_SELECT_JOURNALCONTENTSEARCH_WHERE);

			sb.append(_FINDER_COLUMN_G_P_A_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_P_A_PRIVATELAYOUT_2);

			boolean bindArticleId = false;

			if (articleId.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_P_A_ARTICLEID_3);
			}
			else {
				bindArticleId = true;

				sb.append(_FINDER_COLUMN_G_P_A_ARTICLEID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(JournalContentSearchModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(privateLayout);

				if (bindArticleId) {
					queryPos.add(articleId);
				}

				list = (List<JournalContentSearch>)QueryUtil.list(
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
	 * Returns the first journal content search in the ordered set where groupId = &#63; and privateLayout = &#63; and articleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param articleId the article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal content search
	 * @throws NoSuchContentSearchException if a matching journal content search could not be found
	 */
	@Override
	public JournalContentSearch findByG_P_A_First(
			long groupId, boolean privateLayout, String articleId,
			OrderByComparator<JournalContentSearch> orderByComparator)
		throws NoSuchContentSearchException {

		JournalContentSearch journalContentSearch = fetchByG_P_A_First(
			groupId, privateLayout, articleId, orderByComparator);

		if (journalContentSearch != null) {
			return journalContentSearch;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", privateLayout=");
		sb.append(privateLayout);

		sb.append(", articleId=");
		sb.append(articleId);

		sb.append("}");

		throw new NoSuchContentSearchException(sb.toString());
	}

	/**
	 * Returns the first journal content search in the ordered set where groupId = &#63; and privateLayout = &#63; and articleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param articleId the article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal content search, or <code>null</code> if a matching journal content search could not be found
	 */
	@Override
	public JournalContentSearch fetchByG_P_A_First(
		long groupId, boolean privateLayout, String articleId,
		OrderByComparator<JournalContentSearch> orderByComparator) {

		List<JournalContentSearch> list = findByG_P_A(
			groupId, privateLayout, articleId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last journal content search in the ordered set where groupId = &#63; and privateLayout = &#63; and articleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param articleId the article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal content search
	 * @throws NoSuchContentSearchException if a matching journal content search could not be found
	 */
	@Override
	public JournalContentSearch findByG_P_A_Last(
			long groupId, boolean privateLayout, String articleId,
			OrderByComparator<JournalContentSearch> orderByComparator)
		throws NoSuchContentSearchException {

		JournalContentSearch journalContentSearch = fetchByG_P_A_Last(
			groupId, privateLayout, articleId, orderByComparator);

		if (journalContentSearch != null) {
			return journalContentSearch;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", privateLayout=");
		sb.append(privateLayout);

		sb.append(", articleId=");
		sb.append(articleId);

		sb.append("}");

		throw new NoSuchContentSearchException(sb.toString());
	}

	/**
	 * Returns the last journal content search in the ordered set where groupId = &#63; and privateLayout = &#63; and articleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param articleId the article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal content search, or <code>null</code> if a matching journal content search could not be found
	 */
	@Override
	public JournalContentSearch fetchByG_P_A_Last(
		long groupId, boolean privateLayout, String articleId,
		OrderByComparator<JournalContentSearch> orderByComparator) {

		int count = countByG_P_A(groupId, privateLayout, articleId);

		if (count == 0) {
			return null;
		}

		List<JournalContentSearch> list = findByG_P_A(
			groupId, privateLayout, articleId, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the journal content searches before and after the current journal content search in the ordered set where groupId = &#63; and privateLayout = &#63; and articleId = &#63;.
	 *
	 * @param contentSearchId the primary key of the current journal content search
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param articleId the article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal content search
	 * @throws NoSuchContentSearchException if a journal content search with the primary key could not be found
	 */
	@Override
	public JournalContentSearch[] findByG_P_A_PrevAndNext(
			long contentSearchId, long groupId, boolean privateLayout,
			String articleId,
			OrderByComparator<JournalContentSearch> orderByComparator)
		throws NoSuchContentSearchException {

		articleId = Objects.toString(articleId, "");

		JournalContentSearch journalContentSearch = findByPrimaryKey(
			contentSearchId);

		Session session = null;

		try {
			session = openSession();

			JournalContentSearch[] array = new JournalContentSearchImpl[3];

			array[0] = getByG_P_A_PrevAndNext(
				session, journalContentSearch, groupId, privateLayout,
				articleId, orderByComparator, true);

			array[1] = journalContentSearch;

			array[2] = getByG_P_A_PrevAndNext(
				session, journalContentSearch, groupId, privateLayout,
				articleId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalContentSearch getByG_P_A_PrevAndNext(
		Session session, JournalContentSearch journalContentSearch,
		long groupId, boolean privateLayout, String articleId,
		OrderByComparator<JournalContentSearch> orderByComparator,
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

		sb.append(_SQL_SELECT_JOURNALCONTENTSEARCH_WHERE);

		sb.append(_FINDER_COLUMN_G_P_A_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_P_A_PRIVATELAYOUT_2);

		boolean bindArticleId = false;

		if (articleId.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_P_A_ARTICLEID_3);
		}
		else {
			bindArticleId = true;

			sb.append(_FINDER_COLUMN_G_P_A_ARTICLEID_2);
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
			sb.append(JournalContentSearchModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(privateLayout);

		if (bindArticleId) {
			queryPos.add(articleId);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						journalContentSearch)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<JournalContentSearch> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the journal content searches where groupId = &#63; and privateLayout = &#63; and articleId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param articleId the article ID
	 */
	@Override
	public void removeByG_P_A(
		long groupId, boolean privateLayout, String articleId) {

		for (JournalContentSearch journalContentSearch :
				findByG_P_A(
					groupId, privateLayout, articleId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(journalContentSearch);
		}
	}

	/**
	 * Returns the number of journal content searches where groupId = &#63; and privateLayout = &#63; and articleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param articleId the article ID
	 * @return the number of matching journal content searches
	 */
	@Override
	public int countByG_P_A(
		long groupId, boolean privateLayout, String articleId) {

		articleId = Objects.toString(articleId, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			JournalContentSearch.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_P_A;

			finderArgs = new Object[] {groupId, privateLayout, articleId};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_JOURNALCONTENTSEARCH_WHERE);

			sb.append(_FINDER_COLUMN_G_P_A_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_P_A_PRIVATELAYOUT_2);

			boolean bindArticleId = false;

			if (articleId.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_P_A_ARTICLEID_3);
			}
			else {
				bindArticleId = true;

				sb.append(_FINDER_COLUMN_G_P_A_ARTICLEID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(privateLayout);

				if (bindArticleId) {
					queryPos.add(articleId);
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

	private static final String _FINDER_COLUMN_G_P_A_GROUPID_2 =
		"journalContentSearch.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_P_A_PRIVATELAYOUT_2 =
		"journalContentSearch.privateLayout = ? AND ";

	private static final String _FINDER_COLUMN_G_P_A_ARTICLEID_2 =
		"journalContentSearch.articleId = ?";

	private static final String _FINDER_COLUMN_G_P_A_ARTICLEID_3 =
		"(journalContentSearch.articleId IS NULL OR journalContentSearch.articleId = '')";

	private FinderPath _finderPathWithPaginationFindByG_P_L_P;
	private FinderPath _finderPathWithoutPaginationFindByG_P_L_P;
	private FinderPath _finderPathCountByG_P_L_P;

	/**
	 * Returns all the journal content searches where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param portletId the portlet ID
	 * @return the matching journal content searches
	 */
	@Override
	public List<JournalContentSearch> findByG_P_L_P(
		long groupId, boolean privateLayout, long layoutId, String portletId) {

		return findByG_P_L_P(
			groupId, privateLayout, layoutId, portletId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal content searches where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalContentSearchModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of journal content searches
	 * @param end the upper bound of the range of journal content searches (not inclusive)
	 * @return the range of matching journal content searches
	 */
	@Override
	public List<JournalContentSearch> findByG_P_L_P(
		long groupId, boolean privateLayout, long layoutId, String portletId,
		int start, int end) {

		return findByG_P_L_P(
			groupId, privateLayout, layoutId, portletId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal content searches where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalContentSearchModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of journal content searches
	 * @param end the upper bound of the range of journal content searches (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal content searches
	 */
	@Override
	public List<JournalContentSearch> findByG_P_L_P(
		long groupId, boolean privateLayout, long layoutId, String portletId,
		int start, int end,
		OrderByComparator<JournalContentSearch> orderByComparator) {

		return findByG_P_L_P(
			groupId, privateLayout, layoutId, portletId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the journal content searches where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalContentSearchModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of journal content searches
	 * @param end the upper bound of the range of journal content searches (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal content searches
	 */
	@Override
	public List<JournalContentSearch> findByG_P_L_P(
		long groupId, boolean privateLayout, long layoutId, String portletId,
		int start, int end,
		OrderByComparator<JournalContentSearch> orderByComparator,
		boolean useFinderCache) {

		portletId = Objects.toString(portletId, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			JournalContentSearch.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByG_P_L_P;
				finderArgs = new Object[] {
					groupId, privateLayout, layoutId, portletId
				};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByG_P_L_P;
			finderArgs = new Object[] {
				groupId, privateLayout, layoutId, portletId, start, end,
				orderByComparator
			};
		}

		List<JournalContentSearch> list = null;

		if (useFinderCache && productionMode) {
			list = (List<JournalContentSearch>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (JournalContentSearch journalContentSearch : list) {
					if ((groupId != journalContentSearch.getGroupId()) ||
						(privateLayout !=
							journalContentSearch.isPrivateLayout()) ||
						(layoutId != journalContentSearch.getLayoutId()) ||
						!portletId.equals(
							journalContentSearch.getPortletId())) {

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

			sb.append(_SQL_SELECT_JOURNALCONTENTSEARCH_WHERE);

			sb.append(_FINDER_COLUMN_G_P_L_P_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_P_L_P_PRIVATELAYOUT_2);

			sb.append(_FINDER_COLUMN_G_P_L_P_LAYOUTID_2);

			boolean bindPortletId = false;

			if (portletId.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_P_L_P_PORTLETID_3);
			}
			else {
				bindPortletId = true;

				sb.append(_FINDER_COLUMN_G_P_L_P_PORTLETID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(JournalContentSearchModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(privateLayout);

				queryPos.add(layoutId);

				if (bindPortletId) {
					queryPos.add(portletId);
				}

				list = (List<JournalContentSearch>)QueryUtil.list(
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
	 * Returns the first journal content search in the ordered set where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal content search
	 * @throws NoSuchContentSearchException if a matching journal content search could not be found
	 */
	@Override
	public JournalContentSearch findByG_P_L_P_First(
			long groupId, boolean privateLayout, long layoutId,
			String portletId,
			OrderByComparator<JournalContentSearch> orderByComparator)
		throws NoSuchContentSearchException {

		JournalContentSearch journalContentSearch = fetchByG_P_L_P_First(
			groupId, privateLayout, layoutId, portletId, orderByComparator);

		if (journalContentSearch != null) {
			return journalContentSearch;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", privateLayout=");
		sb.append(privateLayout);

		sb.append(", layoutId=");
		sb.append(layoutId);

		sb.append(", portletId=");
		sb.append(portletId);

		sb.append("}");

		throw new NoSuchContentSearchException(sb.toString());
	}

	/**
	 * Returns the first journal content search in the ordered set where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal content search, or <code>null</code> if a matching journal content search could not be found
	 */
	@Override
	public JournalContentSearch fetchByG_P_L_P_First(
		long groupId, boolean privateLayout, long layoutId, String portletId,
		OrderByComparator<JournalContentSearch> orderByComparator) {

		List<JournalContentSearch> list = findByG_P_L_P(
			groupId, privateLayout, layoutId, portletId, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last journal content search in the ordered set where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal content search
	 * @throws NoSuchContentSearchException if a matching journal content search could not be found
	 */
	@Override
	public JournalContentSearch findByG_P_L_P_Last(
			long groupId, boolean privateLayout, long layoutId,
			String portletId,
			OrderByComparator<JournalContentSearch> orderByComparator)
		throws NoSuchContentSearchException {

		JournalContentSearch journalContentSearch = fetchByG_P_L_P_Last(
			groupId, privateLayout, layoutId, portletId, orderByComparator);

		if (journalContentSearch != null) {
			return journalContentSearch;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", privateLayout=");
		sb.append(privateLayout);

		sb.append(", layoutId=");
		sb.append(layoutId);

		sb.append(", portletId=");
		sb.append(portletId);

		sb.append("}");

		throw new NoSuchContentSearchException(sb.toString());
	}

	/**
	 * Returns the last journal content search in the ordered set where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal content search, or <code>null</code> if a matching journal content search could not be found
	 */
	@Override
	public JournalContentSearch fetchByG_P_L_P_Last(
		long groupId, boolean privateLayout, long layoutId, String portletId,
		OrderByComparator<JournalContentSearch> orderByComparator) {

		int count = countByG_P_L_P(groupId, privateLayout, layoutId, portletId);

		if (count == 0) {
			return null;
		}

		List<JournalContentSearch> list = findByG_P_L_P(
			groupId, privateLayout, layoutId, portletId, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the journal content searches before and after the current journal content search in the ordered set where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63;.
	 *
	 * @param contentSearchId the primary key of the current journal content search
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal content search
	 * @throws NoSuchContentSearchException if a journal content search with the primary key could not be found
	 */
	@Override
	public JournalContentSearch[] findByG_P_L_P_PrevAndNext(
			long contentSearchId, long groupId, boolean privateLayout,
			long layoutId, String portletId,
			OrderByComparator<JournalContentSearch> orderByComparator)
		throws NoSuchContentSearchException {

		portletId = Objects.toString(portletId, "");

		JournalContentSearch journalContentSearch = findByPrimaryKey(
			contentSearchId);

		Session session = null;

		try {
			session = openSession();

			JournalContentSearch[] array = new JournalContentSearchImpl[3];

			array[0] = getByG_P_L_P_PrevAndNext(
				session, journalContentSearch, groupId, privateLayout, layoutId,
				portletId, orderByComparator, true);

			array[1] = journalContentSearch;

			array[2] = getByG_P_L_P_PrevAndNext(
				session, journalContentSearch, groupId, privateLayout, layoutId,
				portletId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalContentSearch getByG_P_L_P_PrevAndNext(
		Session session, JournalContentSearch journalContentSearch,
		long groupId, boolean privateLayout, long layoutId, String portletId,
		OrderByComparator<JournalContentSearch> orderByComparator,
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

		sb.append(_SQL_SELECT_JOURNALCONTENTSEARCH_WHERE);

		sb.append(_FINDER_COLUMN_G_P_L_P_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_P_L_P_PRIVATELAYOUT_2);

		sb.append(_FINDER_COLUMN_G_P_L_P_LAYOUTID_2);

		boolean bindPortletId = false;

		if (portletId.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_P_L_P_PORTLETID_3);
		}
		else {
			bindPortletId = true;

			sb.append(_FINDER_COLUMN_G_P_L_P_PORTLETID_2);
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
			sb.append(JournalContentSearchModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(privateLayout);

		queryPos.add(layoutId);

		if (bindPortletId) {
			queryPos.add(portletId);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						journalContentSearch)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<JournalContentSearch> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the journal content searches where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param portletId the portlet ID
	 */
	@Override
	public void removeByG_P_L_P(
		long groupId, boolean privateLayout, long layoutId, String portletId) {

		for (JournalContentSearch journalContentSearch :
				findByG_P_L_P(
					groupId, privateLayout, layoutId, portletId,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(journalContentSearch);
		}
	}

	/**
	 * Returns the number of journal content searches where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param portletId the portlet ID
	 * @return the number of matching journal content searches
	 */
	@Override
	public int countByG_P_L_P(
		long groupId, boolean privateLayout, long layoutId, String portletId) {

		portletId = Objects.toString(portletId, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			JournalContentSearch.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_P_L_P;

			finderArgs = new Object[] {
				groupId, privateLayout, layoutId, portletId
			};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_COUNT_JOURNALCONTENTSEARCH_WHERE);

			sb.append(_FINDER_COLUMN_G_P_L_P_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_P_L_P_PRIVATELAYOUT_2);

			sb.append(_FINDER_COLUMN_G_P_L_P_LAYOUTID_2);

			boolean bindPortletId = false;

			if (portletId.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_P_L_P_PORTLETID_3);
			}
			else {
				bindPortletId = true;

				sb.append(_FINDER_COLUMN_G_P_L_P_PORTLETID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(privateLayout);

				queryPos.add(layoutId);

				if (bindPortletId) {
					queryPos.add(portletId);
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

	private static final String _FINDER_COLUMN_G_P_L_P_GROUPID_2 =
		"journalContentSearch.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_P_L_P_PRIVATELAYOUT_2 =
		"journalContentSearch.privateLayout = ? AND ";

	private static final String _FINDER_COLUMN_G_P_L_P_LAYOUTID_2 =
		"journalContentSearch.layoutId = ? AND ";

	private static final String _FINDER_COLUMN_G_P_L_P_PORTLETID_2 =
		"journalContentSearch.portletId = ?";

	private static final String _FINDER_COLUMN_G_P_L_P_PORTLETID_3 =
		"(journalContentSearch.portletId IS NULL OR journalContentSearch.portletId = '')";

	private FinderPath _finderPathFetchByG_P_L_P_A;
	private FinderPath _finderPathCountByG_P_L_P_A;

	/**
	 * Returns the journal content search where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63; and articleId = &#63; or throws a <code>NoSuchContentSearchException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param portletId the portlet ID
	 * @param articleId the article ID
	 * @return the matching journal content search
	 * @throws NoSuchContentSearchException if a matching journal content search could not be found
	 */
	@Override
	public JournalContentSearch findByG_P_L_P_A(
			long groupId, boolean privateLayout, long layoutId,
			String portletId, String articleId)
		throws NoSuchContentSearchException {

		JournalContentSearch journalContentSearch = fetchByG_P_L_P_A(
			groupId, privateLayout, layoutId, portletId, articleId);

		if (journalContentSearch == null) {
			StringBundler sb = new StringBundler(12);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("groupId=");
			sb.append(groupId);

			sb.append(", privateLayout=");
			sb.append(privateLayout);

			sb.append(", layoutId=");
			sb.append(layoutId);

			sb.append(", portletId=");
			sb.append(portletId);

			sb.append(", articleId=");
			sb.append(articleId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchContentSearchException(sb.toString());
		}

		return journalContentSearch;
	}

	/**
	 * Returns the journal content search where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63; and articleId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param portletId the portlet ID
	 * @param articleId the article ID
	 * @return the matching journal content search, or <code>null</code> if a matching journal content search could not be found
	 */
	@Override
	public JournalContentSearch fetchByG_P_L_P_A(
		long groupId, boolean privateLayout, long layoutId, String portletId,
		String articleId) {

		return fetchByG_P_L_P_A(
			groupId, privateLayout, layoutId, portletId, articleId, true);
	}

	/**
	 * Returns the journal content search where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63; and articleId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param portletId the portlet ID
	 * @param articleId the article ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching journal content search, or <code>null</code> if a matching journal content search could not be found
	 */
	@Override
	public JournalContentSearch fetchByG_P_L_P_A(
		long groupId, boolean privateLayout, long layoutId, String portletId,
		String articleId, boolean useFinderCache) {

		portletId = Objects.toString(portletId, "");
		articleId = Objects.toString(articleId, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			JournalContentSearch.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {
				groupId, privateLayout, layoutId, portletId, articleId
			};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(
				_finderPathFetchByG_P_L_P_A, finderArgs, this);
		}

		if (result instanceof JournalContentSearch) {
			JournalContentSearch journalContentSearch =
				(JournalContentSearch)result;

			if ((groupId != journalContentSearch.getGroupId()) ||
				(privateLayout != journalContentSearch.isPrivateLayout()) ||
				(layoutId != journalContentSearch.getLayoutId()) ||
				!Objects.equals(
					portletId, journalContentSearch.getPortletId()) ||
				!Objects.equals(
					articleId, journalContentSearch.getArticleId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(7);

			sb.append(_SQL_SELECT_JOURNALCONTENTSEARCH_WHERE);

			sb.append(_FINDER_COLUMN_G_P_L_P_A_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_P_L_P_A_PRIVATELAYOUT_2);

			sb.append(_FINDER_COLUMN_G_P_L_P_A_LAYOUTID_2);

			boolean bindPortletId = false;

			if (portletId.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_P_L_P_A_PORTLETID_3);
			}
			else {
				bindPortletId = true;

				sb.append(_FINDER_COLUMN_G_P_L_P_A_PORTLETID_2);
			}

			boolean bindArticleId = false;

			if (articleId.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_P_L_P_A_ARTICLEID_3);
			}
			else {
				bindArticleId = true;

				sb.append(_FINDER_COLUMN_G_P_L_P_A_ARTICLEID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(privateLayout);

				queryPos.add(layoutId);

				if (bindPortletId) {
					queryPos.add(portletId);
				}

				if (bindArticleId) {
					queryPos.add(articleId);
				}

				List<JournalContentSearch> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByG_P_L_P_A, finderArgs, list);
					}
				}
				else {
					JournalContentSearch journalContentSearch = list.get(0);

					result = journalContentSearch;

					cacheResult(journalContentSearch);
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
			return (JournalContentSearch)result;
		}
	}

	/**
	 * Removes the journal content search where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63; and articleId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param portletId the portlet ID
	 * @param articleId the article ID
	 * @return the journal content search that was removed
	 */
	@Override
	public JournalContentSearch removeByG_P_L_P_A(
			long groupId, boolean privateLayout, long layoutId,
			String portletId, String articleId)
		throws NoSuchContentSearchException {

		JournalContentSearch journalContentSearch = findByG_P_L_P_A(
			groupId, privateLayout, layoutId, portletId, articleId);

		return remove(journalContentSearch);
	}

	/**
	 * Returns the number of journal content searches where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63; and articleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param portletId the portlet ID
	 * @param articleId the article ID
	 * @return the number of matching journal content searches
	 */
	@Override
	public int countByG_P_L_P_A(
		long groupId, boolean privateLayout, long layoutId, String portletId,
		String articleId) {

		portletId = Objects.toString(portletId, "");
		articleId = Objects.toString(articleId, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			JournalContentSearch.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_P_L_P_A;

			finderArgs = new Object[] {
				groupId, privateLayout, layoutId, portletId, articleId
			};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_SQL_COUNT_JOURNALCONTENTSEARCH_WHERE);

			sb.append(_FINDER_COLUMN_G_P_L_P_A_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_P_L_P_A_PRIVATELAYOUT_2);

			sb.append(_FINDER_COLUMN_G_P_L_P_A_LAYOUTID_2);

			boolean bindPortletId = false;

			if (portletId.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_P_L_P_A_PORTLETID_3);
			}
			else {
				bindPortletId = true;

				sb.append(_FINDER_COLUMN_G_P_L_P_A_PORTLETID_2);
			}

			boolean bindArticleId = false;

			if (articleId.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_P_L_P_A_ARTICLEID_3);
			}
			else {
				bindArticleId = true;

				sb.append(_FINDER_COLUMN_G_P_L_P_A_ARTICLEID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(privateLayout);

				queryPos.add(layoutId);

				if (bindPortletId) {
					queryPos.add(portletId);
				}

				if (bindArticleId) {
					queryPos.add(articleId);
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

	private static final String _FINDER_COLUMN_G_P_L_P_A_GROUPID_2 =
		"journalContentSearch.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_P_L_P_A_PRIVATELAYOUT_2 =
		"journalContentSearch.privateLayout = ? AND ";

	private static final String _FINDER_COLUMN_G_P_L_P_A_LAYOUTID_2 =
		"journalContentSearch.layoutId = ? AND ";

	private static final String _FINDER_COLUMN_G_P_L_P_A_PORTLETID_2 =
		"journalContentSearch.portletId = ? AND ";

	private static final String _FINDER_COLUMN_G_P_L_P_A_PORTLETID_3 =
		"(journalContentSearch.portletId IS NULL OR journalContentSearch.portletId = '') AND ";

	private static final String _FINDER_COLUMN_G_P_L_P_A_ARTICLEID_2 =
		"journalContentSearch.articleId = ?";

	private static final String _FINDER_COLUMN_G_P_L_P_A_ARTICLEID_3 =
		"(journalContentSearch.articleId IS NULL OR journalContentSearch.articleId = '')";

	public JournalContentSearchPersistenceImpl() {
		setModelClass(JournalContentSearch.class);

		setModelImplClass(JournalContentSearchImpl.class);
		setModelPKClass(long.class);

		setTable(JournalContentSearchTable.INSTANCE);
	}

	/**
	 * Caches the journal content search in the entity cache if it is enabled.
	 *
	 * @param journalContentSearch the journal content search
	 */
	@Override
	public void cacheResult(JournalContentSearch journalContentSearch) {
		if (journalContentSearch.getCtCollectionId() != 0) {
			journalContentSearch.resetOriginalValues();

			return;
		}

		entityCache.putResult(
			JournalContentSearchImpl.class,
			journalContentSearch.getPrimaryKey(), journalContentSearch);

		finderCache.putResult(
			_finderPathFetchByG_P_L_P_A,
			new Object[] {
				journalContentSearch.getGroupId(),
				journalContentSearch.isPrivateLayout(),
				journalContentSearch.getLayoutId(),
				journalContentSearch.getPortletId(),
				journalContentSearch.getArticleId()
			},
			journalContentSearch);

		journalContentSearch.resetOriginalValues();
	}

	/**
	 * Caches the journal content searches in the entity cache if it is enabled.
	 *
	 * @param journalContentSearchs the journal content searches
	 */
	@Override
	public void cacheResult(List<JournalContentSearch> journalContentSearchs) {
		for (JournalContentSearch journalContentSearch :
				journalContentSearchs) {

			if (journalContentSearch.getCtCollectionId() != 0) {
				journalContentSearch.resetOriginalValues();

				continue;
			}

			if (entityCache.getResult(
					JournalContentSearchImpl.class,
					journalContentSearch.getPrimaryKey()) == null) {

				cacheResult(journalContentSearch);
			}
			else {
				journalContentSearch.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all journal content searches.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(JournalContentSearchImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the journal content search.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(JournalContentSearch journalContentSearch) {
		entityCache.removeResult(
			JournalContentSearchImpl.class,
			journalContentSearch.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(JournalContentSearchModelImpl)journalContentSearch, true);
	}

	@Override
	public void clearCache(List<JournalContentSearch> journalContentSearchs) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (JournalContentSearch journalContentSearch :
				journalContentSearchs) {

			entityCache.removeResult(
				JournalContentSearchImpl.class,
				journalContentSearch.getPrimaryKey());

			clearUniqueFindersCache(
				(JournalContentSearchModelImpl)journalContentSearch, true);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				JournalContentSearchImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		JournalContentSearchModelImpl journalContentSearchModelImpl) {

		Object[] args = new Object[] {
			journalContentSearchModelImpl.getGroupId(),
			journalContentSearchModelImpl.isPrivateLayout(),
			journalContentSearchModelImpl.getLayoutId(),
			journalContentSearchModelImpl.getPortletId(),
			journalContentSearchModelImpl.getArticleId()
		};

		finderCache.putResult(
			_finderPathCountByG_P_L_P_A, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByG_P_L_P_A, args, journalContentSearchModelImpl,
			false);
	}

	protected void clearUniqueFindersCache(
		JournalContentSearchModelImpl journalContentSearchModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				journalContentSearchModelImpl.getGroupId(),
				journalContentSearchModelImpl.isPrivateLayout(),
				journalContentSearchModelImpl.getLayoutId(),
				journalContentSearchModelImpl.getPortletId(),
				journalContentSearchModelImpl.getArticleId()
			};

			finderCache.removeResult(_finderPathCountByG_P_L_P_A, args);
			finderCache.removeResult(_finderPathFetchByG_P_L_P_A, args);
		}

		if ((journalContentSearchModelImpl.getColumnBitmask() &
			 _finderPathFetchByG_P_L_P_A.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				journalContentSearchModelImpl.getOriginalGroupId(),
				journalContentSearchModelImpl.getOriginalPrivateLayout(),
				journalContentSearchModelImpl.getOriginalLayoutId(),
				journalContentSearchModelImpl.getOriginalPortletId(),
				journalContentSearchModelImpl.getOriginalArticleId()
			};

			finderCache.removeResult(_finderPathCountByG_P_L_P_A, args);
			finderCache.removeResult(_finderPathFetchByG_P_L_P_A, args);
		}
	}

	/**
	 * Creates a new journal content search with the primary key. Does not add the journal content search to the database.
	 *
	 * @param contentSearchId the primary key for the new journal content search
	 * @return the new journal content search
	 */
	@Override
	public JournalContentSearch create(long contentSearchId) {
		JournalContentSearch journalContentSearch =
			new JournalContentSearchImpl();

		journalContentSearch.setNew(true);
		journalContentSearch.setPrimaryKey(contentSearchId);

		journalContentSearch.setCompanyId(CompanyThreadLocal.getCompanyId());

		return journalContentSearch;
	}

	/**
	 * Removes the journal content search with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param contentSearchId the primary key of the journal content search
	 * @return the journal content search that was removed
	 * @throws NoSuchContentSearchException if a journal content search with the primary key could not be found
	 */
	@Override
	public JournalContentSearch remove(long contentSearchId)
		throws NoSuchContentSearchException {

		return remove((Serializable)contentSearchId);
	}

	/**
	 * Removes the journal content search with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the journal content search
	 * @return the journal content search that was removed
	 * @throws NoSuchContentSearchException if a journal content search with the primary key could not be found
	 */
	@Override
	public JournalContentSearch remove(Serializable primaryKey)
		throws NoSuchContentSearchException {

		Session session = null;

		try {
			session = openSession();

			JournalContentSearch journalContentSearch =
				(JournalContentSearch)session.get(
					JournalContentSearchImpl.class, primaryKey);

			if (journalContentSearch == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchContentSearchException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(journalContentSearch);
		}
		catch (NoSuchContentSearchException noSuchEntityException) {
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
	protected JournalContentSearch removeImpl(
		JournalContentSearch journalContentSearch) {

		if (!ctPersistenceHelper.isRemove(journalContentSearch)) {
			return journalContentSearch;
		}

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(journalContentSearch)) {
				journalContentSearch = (JournalContentSearch)session.get(
					JournalContentSearchImpl.class,
					journalContentSearch.getPrimaryKeyObj());
			}

			if (journalContentSearch != null) {
				session.delete(journalContentSearch);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (journalContentSearch != null) {
			clearCache(journalContentSearch);
		}

		return journalContentSearch;
	}

	@Override
	public JournalContentSearch updateImpl(
		JournalContentSearch journalContentSearch) {

		boolean isNew = journalContentSearch.isNew();

		if (!(journalContentSearch instanceof JournalContentSearchModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(journalContentSearch.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					journalContentSearch);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in journalContentSearch proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom JournalContentSearch implementation " +
					journalContentSearch.getClass());
		}

		JournalContentSearchModelImpl journalContentSearchModelImpl =
			(JournalContentSearchModelImpl)journalContentSearch;

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(journalContentSearch)) {
				if (!isNew) {
					session.evict(
						JournalContentSearchImpl.class,
						journalContentSearch.getPrimaryKeyObj());
				}

				session.save(journalContentSearch);

				journalContentSearch.setNew(false);
			}
			else {
				journalContentSearch = (JournalContentSearch)session.merge(
					journalContentSearch);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (journalContentSearch.getCtCollectionId() != 0) {
			journalContentSearch.resetOriginalValues();

			return journalContentSearch;
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew) {
			Object[] args = new Object[] {
				journalContentSearchModelImpl.getCompanyId()
			};

			finderCache.removeResult(_finderPathCountByCompanyId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByCompanyId, args);

			args = new Object[] {journalContentSearchModelImpl.getPortletId()};

			finderCache.removeResult(_finderPathCountByPortletId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByPortletId, args);

			args = new Object[] {journalContentSearchModelImpl.getArticleId()};

			finderCache.removeResult(_finderPathCountByArticleId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByArticleId, args);

			args = new Object[] {
				journalContentSearchModelImpl.getGroupId(),
				journalContentSearchModelImpl.isPrivateLayout()
			};

			finderCache.removeResult(_finderPathCountByG_P, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByG_P, args);

			args = new Object[] {
				journalContentSearchModelImpl.getGroupId(),
				journalContentSearchModelImpl.getArticleId()
			};

			finderCache.removeResult(_finderPathCountByG_A, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByG_A, args);

			args = new Object[] {
				journalContentSearchModelImpl.getGroupId(),
				journalContentSearchModelImpl.isPrivateLayout(),
				journalContentSearchModelImpl.getLayoutId()
			};

			finderCache.removeResult(_finderPathCountByG_P_L, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByG_P_L, args);

			args = new Object[] {
				journalContentSearchModelImpl.getGroupId(),
				journalContentSearchModelImpl.isPrivateLayout(),
				journalContentSearchModelImpl.getArticleId()
			};

			finderCache.removeResult(_finderPathCountByG_P_A, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByG_P_A, args);

			args = new Object[] {
				journalContentSearchModelImpl.getGroupId(),
				journalContentSearchModelImpl.isPrivateLayout(),
				journalContentSearchModelImpl.getLayoutId(),
				journalContentSearchModelImpl.getPortletId()
			};

			finderCache.removeResult(_finderPathCountByG_P_L_P, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByG_P_L_P, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((journalContentSearchModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByCompanyId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					journalContentSearchModelImpl.getOriginalCompanyId()
				};

				finderCache.removeResult(_finderPathCountByCompanyId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByCompanyId, args);

				args = new Object[] {
					journalContentSearchModelImpl.getCompanyId()
				};

				finderCache.removeResult(_finderPathCountByCompanyId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByCompanyId, args);
			}

			if ((journalContentSearchModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByPortletId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					journalContentSearchModelImpl.getOriginalPortletId()
				};

				finderCache.removeResult(_finderPathCountByPortletId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByPortletId, args);

				args = new Object[] {
					journalContentSearchModelImpl.getPortletId()
				};

				finderCache.removeResult(_finderPathCountByPortletId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByPortletId, args);
			}

			if ((journalContentSearchModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByArticleId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					journalContentSearchModelImpl.getOriginalArticleId()
				};

				finderCache.removeResult(_finderPathCountByArticleId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByArticleId, args);

				args = new Object[] {
					journalContentSearchModelImpl.getArticleId()
				};

				finderCache.removeResult(_finderPathCountByArticleId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByArticleId, args);
			}

			if ((journalContentSearchModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_P.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					journalContentSearchModelImpl.getOriginalGroupId(),
					journalContentSearchModelImpl.getOriginalPrivateLayout()
				};

				finderCache.removeResult(_finderPathCountByG_P, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_P, args);

				args = new Object[] {
					journalContentSearchModelImpl.getGroupId(),
					journalContentSearchModelImpl.isPrivateLayout()
				};

				finderCache.removeResult(_finderPathCountByG_P, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_P, args);
			}

			if ((journalContentSearchModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_A.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					journalContentSearchModelImpl.getOriginalGroupId(),
					journalContentSearchModelImpl.getOriginalArticleId()
				};

				finderCache.removeResult(_finderPathCountByG_A, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_A, args);

				args = new Object[] {
					journalContentSearchModelImpl.getGroupId(),
					journalContentSearchModelImpl.getArticleId()
				};

				finderCache.removeResult(_finderPathCountByG_A, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_A, args);
			}

			if ((journalContentSearchModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_P_L.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					journalContentSearchModelImpl.getOriginalGroupId(),
					journalContentSearchModelImpl.getOriginalPrivateLayout(),
					journalContentSearchModelImpl.getOriginalLayoutId()
				};

				finderCache.removeResult(_finderPathCountByG_P_L, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_P_L, args);

				args = new Object[] {
					journalContentSearchModelImpl.getGroupId(),
					journalContentSearchModelImpl.isPrivateLayout(),
					journalContentSearchModelImpl.getLayoutId()
				};

				finderCache.removeResult(_finderPathCountByG_P_L, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_P_L, args);
			}

			if ((journalContentSearchModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_P_A.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					journalContentSearchModelImpl.getOriginalGroupId(),
					journalContentSearchModelImpl.getOriginalPrivateLayout(),
					journalContentSearchModelImpl.getOriginalArticleId()
				};

				finderCache.removeResult(_finderPathCountByG_P_A, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_P_A, args);

				args = new Object[] {
					journalContentSearchModelImpl.getGroupId(),
					journalContentSearchModelImpl.isPrivateLayout(),
					journalContentSearchModelImpl.getArticleId()
				};

				finderCache.removeResult(_finderPathCountByG_P_A, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_P_A, args);
			}

			if ((journalContentSearchModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_P_L_P.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					journalContentSearchModelImpl.getOriginalGroupId(),
					journalContentSearchModelImpl.getOriginalPrivateLayout(),
					journalContentSearchModelImpl.getOriginalLayoutId(),
					journalContentSearchModelImpl.getOriginalPortletId()
				};

				finderCache.removeResult(_finderPathCountByG_P_L_P, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_P_L_P, args);

				args = new Object[] {
					journalContentSearchModelImpl.getGroupId(),
					journalContentSearchModelImpl.isPrivateLayout(),
					journalContentSearchModelImpl.getLayoutId(),
					journalContentSearchModelImpl.getPortletId()
				};

				finderCache.removeResult(_finderPathCountByG_P_L_P, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_P_L_P, args);
			}
		}

		entityCache.putResult(
			JournalContentSearchImpl.class,
			journalContentSearch.getPrimaryKey(), journalContentSearch, false);

		clearUniqueFindersCache(journalContentSearchModelImpl, false);
		cacheUniqueFindersCache(journalContentSearchModelImpl);

		journalContentSearch.resetOriginalValues();

		return journalContentSearch;
	}

	/**
	 * Returns the journal content search with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the journal content search
	 * @return the journal content search
	 * @throws NoSuchContentSearchException if a journal content search with the primary key could not be found
	 */
	@Override
	public JournalContentSearch findByPrimaryKey(Serializable primaryKey)
		throws NoSuchContentSearchException {

		JournalContentSearch journalContentSearch = fetchByPrimaryKey(
			primaryKey);

		if (journalContentSearch == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchContentSearchException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return journalContentSearch;
	}

	/**
	 * Returns the journal content search with the primary key or throws a <code>NoSuchContentSearchException</code> if it could not be found.
	 *
	 * @param contentSearchId the primary key of the journal content search
	 * @return the journal content search
	 * @throws NoSuchContentSearchException if a journal content search with the primary key could not be found
	 */
	@Override
	public JournalContentSearch findByPrimaryKey(long contentSearchId)
		throws NoSuchContentSearchException {

		return findByPrimaryKey((Serializable)contentSearchId);
	}

	/**
	 * Returns the journal content search with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the journal content search
	 * @return the journal content search, or <code>null</code> if a journal content search with the primary key could not be found
	 */
	@Override
	public JournalContentSearch fetchByPrimaryKey(Serializable primaryKey) {
		if (ctPersistenceHelper.isProductionMode(JournalContentSearch.class)) {
			return super.fetchByPrimaryKey(primaryKey);
		}

		JournalContentSearch journalContentSearch = null;

		Session session = null;

		try {
			session = openSession();

			journalContentSearch = (JournalContentSearch)session.get(
				JournalContentSearchImpl.class, primaryKey);

			if (journalContentSearch != null) {
				cacheResult(journalContentSearch);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return journalContentSearch;
	}

	/**
	 * Returns the journal content search with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param contentSearchId the primary key of the journal content search
	 * @return the journal content search, or <code>null</code> if a journal content search with the primary key could not be found
	 */
	@Override
	public JournalContentSearch fetchByPrimaryKey(long contentSearchId) {
		return fetchByPrimaryKey((Serializable)contentSearchId);
	}

	@Override
	public Map<Serializable, JournalContentSearch> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (ctPersistenceHelper.isProductionMode(JournalContentSearch.class)) {
			return super.fetchByPrimaryKeys(primaryKeys);
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, JournalContentSearch> map =
			new HashMap<Serializable, JournalContentSearch>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			JournalContentSearch journalContentSearch = fetchByPrimaryKey(
				primaryKey);

			if (journalContentSearch != null) {
				map.put(primaryKey, journalContentSearch);
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

			for (JournalContentSearch journalContentSearch :
					(List<JournalContentSearch>)query.list()) {

				map.put(
					journalContentSearch.getPrimaryKeyObj(),
					journalContentSearch);

				cacheResult(journalContentSearch);
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
	 * Returns all the journal content searches.
	 *
	 * @return the journal content searches
	 */
	@Override
	public List<JournalContentSearch> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal content searches.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalContentSearchModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of journal content searches
	 * @param end the upper bound of the range of journal content searches (not inclusive)
	 * @return the range of journal content searches
	 */
	@Override
	public List<JournalContentSearch> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal content searches.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalContentSearchModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of journal content searches
	 * @param end the upper bound of the range of journal content searches (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of journal content searches
	 */
	@Override
	public List<JournalContentSearch> findAll(
		int start, int end,
		OrderByComparator<JournalContentSearch> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the journal content searches.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalContentSearchModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of journal content searches
	 * @param end the upper bound of the range of journal content searches (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of journal content searches
	 */
	@Override
	public List<JournalContentSearch> findAll(
		int start, int end,
		OrderByComparator<JournalContentSearch> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			JournalContentSearch.class);

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

		List<JournalContentSearch> list = null;

		if (useFinderCache && productionMode) {
			list = (List<JournalContentSearch>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_JOURNALCONTENTSEARCH);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_JOURNALCONTENTSEARCH;

				sql = sql.concat(JournalContentSearchModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<JournalContentSearch>)QueryUtil.list(
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
	 * Removes all the journal content searches from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (JournalContentSearch journalContentSearch : findAll()) {
			remove(journalContentSearch);
		}
	}

	/**
	 * Returns the number of journal content searches.
	 *
	 * @return the number of journal content searches
	 */
	@Override
	public int countAll() {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			JournalContentSearch.class);

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
					_SQL_COUNT_JOURNALCONTENTSEARCH);

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
		return "contentSearchId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_JOURNALCONTENTSEARCH;
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
		return JournalContentSearchModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "JournalContentSearch";
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
		ctStrictColumnNames.add("groupId");
		ctStrictColumnNames.add("companyId");
		ctStrictColumnNames.add("privateLayout");
		ctStrictColumnNames.add("layoutId");
		ctStrictColumnNames.add("portletId");
		ctStrictColumnNames.add("articleId");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK,
			Collections.singleton("contentSearchId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(
			new String[] {
				"groupId", "privateLayout", "layoutId", "portletId", "articleId"
			});
	}

	/**
	 * Initializes the journal content search persistence.
	 */
	@Activate
	public void activate() {
		_finderPathWithPaginationFindAll = new FinderPath(
			JournalContentSearchImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			JournalContentSearchImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByCompanyId = new FinderPath(
			JournalContentSearchImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByCompanyId = new FinderPath(
			JournalContentSearchImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] {Long.class.getName()},
			JournalContentSearchModelImpl.COMPANYID_COLUMN_BITMASK);

		_finderPathCountByCompanyId = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCompanyId", new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByPortletId = new FinderPath(
			JournalContentSearchImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByPortletId",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByPortletId = new FinderPath(
			JournalContentSearchImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByPortletId",
			new String[] {String.class.getName()},
			JournalContentSearchModelImpl.PORTLETID_COLUMN_BITMASK);

		_finderPathCountByPortletId = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByPortletId", new String[] {String.class.getName()});

		_finderPathWithPaginationFindByArticleId = new FinderPath(
			JournalContentSearchImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByArticleId",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByArticleId = new FinderPath(
			JournalContentSearchImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByArticleId",
			new String[] {String.class.getName()},
			JournalContentSearchModelImpl.ARTICLEID_COLUMN_BITMASK);

		_finderPathCountByArticleId = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByArticleId", new String[] {String.class.getName()});

		_finderPathWithPaginationFindByG_P = new FinderPath(
			JournalContentSearchImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_P",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_P = new FinderPath(
			JournalContentSearchImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_P",
			new String[] {Long.class.getName(), Boolean.class.getName()},
			JournalContentSearchModelImpl.GROUPID_COLUMN_BITMASK |
			JournalContentSearchModelImpl.PRIVATELAYOUT_COLUMN_BITMASK);

		_finderPathCountByG_P = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_P",
			new String[] {Long.class.getName(), Boolean.class.getName()});

		_finderPathWithPaginationFindByG_A = new FinderPath(
			JournalContentSearchImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_A",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_A = new FinderPath(
			JournalContentSearchImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_A",
			new String[] {Long.class.getName(), String.class.getName()},
			JournalContentSearchModelImpl.GROUPID_COLUMN_BITMASK |
			JournalContentSearchModelImpl.ARTICLEID_COLUMN_BITMASK);

		_finderPathCountByG_A = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_A",
			new String[] {Long.class.getName(), String.class.getName()});

		_finderPathWithPaginationFindByG_P_L = new FinderPath(
			JournalContentSearchImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_P_L",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_P_L = new FinderPath(
			JournalContentSearchImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_P_L",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName()
			},
			JournalContentSearchModelImpl.GROUPID_COLUMN_BITMASK |
			JournalContentSearchModelImpl.PRIVATELAYOUT_COLUMN_BITMASK |
			JournalContentSearchModelImpl.LAYOUTID_COLUMN_BITMASK);

		_finderPathCountByG_P_L = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_P_L",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName()
			});

		_finderPathWithPaginationFindByG_P_A = new FinderPath(
			JournalContentSearchImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_P_A",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_P_A = new FinderPath(
			JournalContentSearchImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_P_A",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName()
			},
			JournalContentSearchModelImpl.GROUPID_COLUMN_BITMASK |
			JournalContentSearchModelImpl.PRIVATELAYOUT_COLUMN_BITMASK |
			JournalContentSearchModelImpl.ARTICLEID_COLUMN_BITMASK);

		_finderPathCountByG_P_A = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_P_A",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName()
			});

		_finderPathWithPaginationFindByG_P_L_P = new FinderPath(
			JournalContentSearchImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_P_L_P",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_P_L_P = new FinderPath(
			JournalContentSearchImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_P_L_P",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName(), String.class.getName()
			},
			JournalContentSearchModelImpl.GROUPID_COLUMN_BITMASK |
			JournalContentSearchModelImpl.PRIVATELAYOUT_COLUMN_BITMASK |
			JournalContentSearchModelImpl.LAYOUTID_COLUMN_BITMASK |
			JournalContentSearchModelImpl.PORTLETID_COLUMN_BITMASK);

		_finderPathCountByG_P_L_P = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_P_L_P",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName(), String.class.getName()
			});

		_finderPathFetchByG_P_L_P_A = new FinderPath(
			JournalContentSearchImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByG_P_L_P_A",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			},
			JournalContentSearchModelImpl.GROUPID_COLUMN_BITMASK |
			JournalContentSearchModelImpl.PRIVATELAYOUT_COLUMN_BITMASK |
			JournalContentSearchModelImpl.LAYOUTID_COLUMN_BITMASK |
			JournalContentSearchModelImpl.PORTLETID_COLUMN_BITMASK |
			JournalContentSearchModelImpl.ARTICLEID_COLUMN_BITMASK);

		_finderPathCountByG_P_L_P_A = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_P_L_P_A",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			});
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(JournalContentSearchImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	@Reference(
		target = JournalPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = JournalPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = JournalPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
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

	private static final String _SQL_SELECT_JOURNALCONTENTSEARCH =
		"SELECT journalContentSearch FROM JournalContentSearch journalContentSearch";

	private static final String _SQL_SELECT_JOURNALCONTENTSEARCH_WHERE =
		"SELECT journalContentSearch FROM JournalContentSearch journalContentSearch WHERE ";

	private static final String _SQL_COUNT_JOURNALCONTENTSEARCH =
		"SELECT COUNT(journalContentSearch) FROM JournalContentSearch journalContentSearch";

	private static final String _SQL_COUNT_JOURNALCONTENTSEARCH_WHERE =
		"SELECT COUNT(journalContentSearch) FROM JournalContentSearch journalContentSearch WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"journalContentSearch.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No JournalContentSearch exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No JournalContentSearch exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		JournalContentSearchPersistenceImpl.class);

	static {
		try {
			Class.forName(JournalPersistenceConstants.class.getName());
		}
		catch (ClassNotFoundException classNotFoundException) {
			throw new ExceptionInInitializerError(classNotFoundException);
		}
	}

}