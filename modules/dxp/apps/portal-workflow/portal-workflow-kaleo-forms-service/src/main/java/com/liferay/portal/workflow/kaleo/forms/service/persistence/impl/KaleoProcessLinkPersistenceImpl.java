/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.workflow.kaleo.forms.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.petra.string.StringBundler;

import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.portal.workflow.kaleo.forms.exception.NoSuchKaleoProcessLinkException;
import com.liferay.portal.workflow.kaleo.forms.model.KaleoProcessLink;
import com.liferay.portal.workflow.kaleo.forms.model.impl.KaleoProcessLinkImpl;
import com.liferay.portal.workflow.kaleo.forms.model.impl.KaleoProcessLinkModelImpl;
import com.liferay.portal.workflow.kaleo.forms.service.persistence.KaleoProcessLinkPersistence;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the kaleo process link service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marcellus Tavares
 * @see KaleoProcessLinkPersistence
 * @see com.liferay.portal.workflow.kaleo.forms.service.persistence.KaleoProcessLinkUtil
 * @generated
 */
@ProviderType
public class KaleoProcessLinkPersistenceImpl extends BasePersistenceImpl<KaleoProcessLink>
	implements KaleoProcessLinkPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link KaleoProcessLinkUtil} to access the kaleo process link persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = KaleoProcessLinkImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(KaleoProcessLinkModelImpl.ENTITY_CACHE_ENABLED,
			KaleoProcessLinkModelImpl.FINDER_CACHE_ENABLED,
			KaleoProcessLinkImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(KaleoProcessLinkModelImpl.ENTITY_CACHE_ENABLED,
			KaleoProcessLinkModelImpl.FINDER_CACHE_ENABLED,
			KaleoProcessLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(KaleoProcessLinkModelImpl.ENTITY_CACHE_ENABLED,
			KaleoProcessLinkModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_KALEOPROCESSID =
		new FinderPath(KaleoProcessLinkModelImpl.ENTITY_CACHE_ENABLED,
			KaleoProcessLinkModelImpl.FINDER_CACHE_ENABLED,
			KaleoProcessLinkImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByKaleoProcessId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_KALEOPROCESSID =
		new FinderPath(KaleoProcessLinkModelImpl.ENTITY_CACHE_ENABLED,
			KaleoProcessLinkModelImpl.FINDER_CACHE_ENABLED,
			KaleoProcessLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByKaleoProcessId",
			new String[] { Long.class.getName() },
			KaleoProcessLinkModelImpl.KALEOPROCESSID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_KALEOPROCESSID = new FinderPath(KaleoProcessLinkModelImpl.ENTITY_CACHE_ENABLED,
			KaleoProcessLinkModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByKaleoProcessId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the kaleo process links where kaleoProcessId = &#63;.
	 *
	 * @param kaleoProcessId the kaleo process ID
	 * @return the matching kaleo process links
	 */
	@Override
	public List<KaleoProcessLink> findByKaleoProcessId(long kaleoProcessId) {
		return findByKaleoProcessId(kaleoProcessId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the kaleo process links where kaleoProcessId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoProcessLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param kaleoProcessId the kaleo process ID
	 * @param start the lower bound of the range of kaleo process links
	 * @param end the upper bound of the range of kaleo process links (not inclusive)
	 * @return the range of matching kaleo process links
	 */
	@Override
	public List<KaleoProcessLink> findByKaleoProcessId(long kaleoProcessId,
		int start, int end) {
		return findByKaleoProcessId(kaleoProcessId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the kaleo process links where kaleoProcessId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoProcessLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param kaleoProcessId the kaleo process ID
	 * @param start the lower bound of the range of kaleo process links
	 * @param end the upper bound of the range of kaleo process links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo process links
	 */
	@Override
	public List<KaleoProcessLink> findByKaleoProcessId(long kaleoProcessId,
		int start, int end,
		OrderByComparator<KaleoProcessLink> orderByComparator) {
		return findByKaleoProcessId(kaleoProcessId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the kaleo process links where kaleoProcessId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoProcessLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param kaleoProcessId the kaleo process ID
	 * @param start the lower bound of the range of kaleo process links
	 * @param end the upper bound of the range of kaleo process links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching kaleo process links
	 */
	@Override
	public List<KaleoProcessLink> findByKaleoProcessId(long kaleoProcessId,
		int start, int end,
		OrderByComparator<KaleoProcessLink> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_KALEOPROCESSID;
			finderArgs = new Object[] { kaleoProcessId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_KALEOPROCESSID;
			finderArgs = new Object[] {
					kaleoProcessId,
					
					start, end, orderByComparator
				};
		}

		List<KaleoProcessLink> list = null;

		if (retrieveFromCache) {
			list = (List<KaleoProcessLink>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (KaleoProcessLink kaleoProcessLink : list) {
					if ((kaleoProcessId != kaleoProcessLink.getKaleoProcessId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_KALEOPROCESSLINK_WHERE);

			query.append(_FINDER_COLUMN_KALEOPROCESSID_KALEOPROCESSID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(KaleoProcessLinkModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(kaleoProcessId);

				if (!pagination) {
					list = (List<KaleoProcessLink>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<KaleoProcessLink>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first kaleo process link in the ordered set where kaleoProcessId = &#63;.
	 *
	 * @param kaleoProcessId the kaleo process ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo process link
	 * @throws NoSuchKaleoProcessLinkException if a matching kaleo process link could not be found
	 */
	@Override
	public KaleoProcessLink findByKaleoProcessId_First(long kaleoProcessId,
		OrderByComparator<KaleoProcessLink> orderByComparator)
		throws NoSuchKaleoProcessLinkException {
		KaleoProcessLink kaleoProcessLink = fetchByKaleoProcessId_First(kaleoProcessId,
				orderByComparator);

		if (kaleoProcessLink != null) {
			return kaleoProcessLink;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("kaleoProcessId=");
		msg.append(kaleoProcessId);

		msg.append("}");

		throw new NoSuchKaleoProcessLinkException(msg.toString());
	}

	/**
	 * Returns the first kaleo process link in the ordered set where kaleoProcessId = &#63;.
	 *
	 * @param kaleoProcessId the kaleo process ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo process link, or <code>null</code> if a matching kaleo process link could not be found
	 */
	@Override
	public KaleoProcessLink fetchByKaleoProcessId_First(long kaleoProcessId,
		OrderByComparator<KaleoProcessLink> orderByComparator) {
		List<KaleoProcessLink> list = findByKaleoProcessId(kaleoProcessId, 0,
				1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last kaleo process link in the ordered set where kaleoProcessId = &#63;.
	 *
	 * @param kaleoProcessId the kaleo process ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo process link
	 * @throws NoSuchKaleoProcessLinkException if a matching kaleo process link could not be found
	 */
	@Override
	public KaleoProcessLink findByKaleoProcessId_Last(long kaleoProcessId,
		OrderByComparator<KaleoProcessLink> orderByComparator)
		throws NoSuchKaleoProcessLinkException {
		KaleoProcessLink kaleoProcessLink = fetchByKaleoProcessId_Last(kaleoProcessId,
				orderByComparator);

		if (kaleoProcessLink != null) {
			return kaleoProcessLink;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("kaleoProcessId=");
		msg.append(kaleoProcessId);

		msg.append("}");

		throw new NoSuchKaleoProcessLinkException(msg.toString());
	}

	/**
	 * Returns the last kaleo process link in the ordered set where kaleoProcessId = &#63;.
	 *
	 * @param kaleoProcessId the kaleo process ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo process link, or <code>null</code> if a matching kaleo process link could not be found
	 */
	@Override
	public KaleoProcessLink fetchByKaleoProcessId_Last(long kaleoProcessId,
		OrderByComparator<KaleoProcessLink> orderByComparator) {
		int count = countByKaleoProcessId(kaleoProcessId);

		if (count == 0) {
			return null;
		}

		List<KaleoProcessLink> list = findByKaleoProcessId(kaleoProcessId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the kaleo process links before and after the current kaleo process link in the ordered set where kaleoProcessId = &#63;.
	 *
	 * @param kaleoProcessLinkId the primary key of the current kaleo process link
	 * @param kaleoProcessId the kaleo process ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo process link
	 * @throws NoSuchKaleoProcessLinkException if a kaleo process link with the primary key could not be found
	 */
	@Override
	public KaleoProcessLink[] findByKaleoProcessId_PrevAndNext(
		long kaleoProcessLinkId, long kaleoProcessId,
		OrderByComparator<KaleoProcessLink> orderByComparator)
		throws NoSuchKaleoProcessLinkException {
		KaleoProcessLink kaleoProcessLink = findByPrimaryKey(kaleoProcessLinkId);

		Session session = null;

		try {
			session = openSession();

			KaleoProcessLink[] array = new KaleoProcessLinkImpl[3];

			array[0] = getByKaleoProcessId_PrevAndNext(session,
					kaleoProcessLink, kaleoProcessId, orderByComparator, true);

			array[1] = kaleoProcessLink;

			array[2] = getByKaleoProcessId_PrevAndNext(session,
					kaleoProcessLink, kaleoProcessId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected KaleoProcessLink getByKaleoProcessId_PrevAndNext(
		Session session, KaleoProcessLink kaleoProcessLink,
		long kaleoProcessId,
		OrderByComparator<KaleoProcessLink> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_KALEOPROCESSLINK_WHERE);

		query.append(_FINDER_COLUMN_KALEOPROCESSID_KALEOPROCESSID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(KaleoProcessLinkModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(kaleoProcessId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(kaleoProcessLink);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<KaleoProcessLink> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the kaleo process links where kaleoProcessId = &#63; from the database.
	 *
	 * @param kaleoProcessId the kaleo process ID
	 */
	@Override
	public void removeByKaleoProcessId(long kaleoProcessId) {
		for (KaleoProcessLink kaleoProcessLink : findByKaleoProcessId(
				kaleoProcessId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(kaleoProcessLink);
		}
	}

	/**
	 * Returns the number of kaleo process links where kaleoProcessId = &#63;.
	 *
	 * @param kaleoProcessId the kaleo process ID
	 * @return the number of matching kaleo process links
	 */
	@Override
	public int countByKaleoProcessId(long kaleoProcessId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_KALEOPROCESSID;

		Object[] finderArgs = new Object[] { kaleoProcessId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_KALEOPROCESSLINK_WHERE);

			query.append(_FINDER_COLUMN_KALEOPROCESSID_KALEOPROCESSID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(kaleoProcessId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_KALEOPROCESSID_KALEOPROCESSID_2 = "kaleoProcessLink.kaleoProcessId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_KPI_WTN = new FinderPath(KaleoProcessLinkModelImpl.ENTITY_CACHE_ENABLED,
			KaleoProcessLinkModelImpl.FINDER_CACHE_ENABLED,
			KaleoProcessLinkImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByKPI_WTN",
			new String[] { Long.class.getName(), String.class.getName() },
			KaleoProcessLinkModelImpl.KALEOPROCESSID_COLUMN_BITMASK |
			KaleoProcessLinkModelImpl.WORKFLOWTASKNAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_KPI_WTN = new FinderPath(KaleoProcessLinkModelImpl.ENTITY_CACHE_ENABLED,
			KaleoProcessLinkModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByKPI_WTN",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns the kaleo process link where kaleoProcessId = &#63; and workflowTaskName = &#63; or throws a {@link NoSuchKaleoProcessLinkException} if it could not be found.
	 *
	 * @param kaleoProcessId the kaleo process ID
	 * @param workflowTaskName the workflow task name
	 * @return the matching kaleo process link
	 * @throws NoSuchKaleoProcessLinkException if a matching kaleo process link could not be found
	 */
	@Override
	public KaleoProcessLink findByKPI_WTN(long kaleoProcessId,
		String workflowTaskName) throws NoSuchKaleoProcessLinkException {
		KaleoProcessLink kaleoProcessLink = fetchByKPI_WTN(kaleoProcessId,
				workflowTaskName);

		if (kaleoProcessLink == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("kaleoProcessId=");
			msg.append(kaleoProcessId);

			msg.append(", workflowTaskName=");
			msg.append(workflowTaskName);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchKaleoProcessLinkException(msg.toString());
		}

		return kaleoProcessLink;
	}

	/**
	 * Returns the kaleo process link where kaleoProcessId = &#63; and workflowTaskName = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param kaleoProcessId the kaleo process ID
	 * @param workflowTaskName the workflow task name
	 * @return the matching kaleo process link, or <code>null</code> if a matching kaleo process link could not be found
	 */
	@Override
	public KaleoProcessLink fetchByKPI_WTN(long kaleoProcessId,
		String workflowTaskName) {
		return fetchByKPI_WTN(kaleoProcessId, workflowTaskName, true);
	}

	/**
	 * Returns the kaleo process link where kaleoProcessId = &#63; and workflowTaskName = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param kaleoProcessId the kaleo process ID
	 * @param workflowTaskName the workflow task name
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching kaleo process link, or <code>null</code> if a matching kaleo process link could not be found
	 */
	@Override
	public KaleoProcessLink fetchByKPI_WTN(long kaleoProcessId,
		String workflowTaskName, boolean retrieveFromCache) {
		workflowTaskName = Objects.toString(workflowTaskName, "");

		Object[] finderArgs = new Object[] { kaleoProcessId, workflowTaskName };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_KPI_WTN,
					finderArgs, this);
		}

		if (result instanceof KaleoProcessLink) {
			KaleoProcessLink kaleoProcessLink = (KaleoProcessLink)result;

			if ((kaleoProcessId != kaleoProcessLink.getKaleoProcessId()) ||
					!Objects.equals(workflowTaskName,
						kaleoProcessLink.getWorkflowTaskName())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_KALEOPROCESSLINK_WHERE);

			query.append(_FINDER_COLUMN_KPI_WTN_KALEOPROCESSID_2);

			boolean bindWorkflowTaskName = false;

			if (workflowTaskName.isEmpty()) {
				query.append(_FINDER_COLUMN_KPI_WTN_WORKFLOWTASKNAME_3);
			}
			else {
				bindWorkflowTaskName = true;

				query.append(_FINDER_COLUMN_KPI_WTN_WORKFLOWTASKNAME_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(kaleoProcessId);

				if (bindWorkflowTaskName) {
					qPos.add(workflowTaskName);
				}

				List<KaleoProcessLink> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_KPI_WTN,
						finderArgs, list);
				}
				else {
					KaleoProcessLink kaleoProcessLink = list.get(0);

					result = kaleoProcessLink;

					cacheResult(kaleoProcessLink);
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_KPI_WTN,
					finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (KaleoProcessLink)result;
		}
	}

	/**
	 * Removes the kaleo process link where kaleoProcessId = &#63; and workflowTaskName = &#63; from the database.
	 *
	 * @param kaleoProcessId the kaleo process ID
	 * @param workflowTaskName the workflow task name
	 * @return the kaleo process link that was removed
	 */
	@Override
	public KaleoProcessLink removeByKPI_WTN(long kaleoProcessId,
		String workflowTaskName) throws NoSuchKaleoProcessLinkException {
		KaleoProcessLink kaleoProcessLink = findByKPI_WTN(kaleoProcessId,
				workflowTaskName);

		return remove(kaleoProcessLink);
	}

	/**
	 * Returns the number of kaleo process links where kaleoProcessId = &#63; and workflowTaskName = &#63;.
	 *
	 * @param kaleoProcessId the kaleo process ID
	 * @param workflowTaskName the workflow task name
	 * @return the number of matching kaleo process links
	 */
	@Override
	public int countByKPI_WTN(long kaleoProcessId, String workflowTaskName) {
		workflowTaskName = Objects.toString(workflowTaskName, "");

		FinderPath finderPath = FINDER_PATH_COUNT_BY_KPI_WTN;

		Object[] finderArgs = new Object[] { kaleoProcessId, workflowTaskName };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_KALEOPROCESSLINK_WHERE);

			query.append(_FINDER_COLUMN_KPI_WTN_KALEOPROCESSID_2);

			boolean bindWorkflowTaskName = false;

			if (workflowTaskName.isEmpty()) {
				query.append(_FINDER_COLUMN_KPI_WTN_WORKFLOWTASKNAME_3);
			}
			else {
				bindWorkflowTaskName = true;

				query.append(_FINDER_COLUMN_KPI_WTN_WORKFLOWTASKNAME_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(kaleoProcessId);

				if (bindWorkflowTaskName) {
					qPos.add(workflowTaskName);
				}

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_KPI_WTN_KALEOPROCESSID_2 = "kaleoProcessLink.kaleoProcessId = ? AND ";
	private static final String _FINDER_COLUMN_KPI_WTN_WORKFLOWTASKNAME_1 = "kaleoProcessLink.workflowTaskName IS NULL";
	private static final String _FINDER_COLUMN_KPI_WTN_WORKFLOWTASKNAME_2 = "kaleoProcessLink.workflowTaskName = ?";
	private static final String _FINDER_COLUMN_KPI_WTN_WORKFLOWTASKNAME_3 = "(kaleoProcessLink.workflowTaskName IS NULL OR kaleoProcessLink.workflowTaskName = '')";

	public KaleoProcessLinkPersistenceImpl() {
		setModelClass(KaleoProcessLink.class);

		setModelImplClass(KaleoProcessLinkImpl.class);
		setEntityCacheEnabled(KaleoProcessLinkModelImpl.ENTITY_CACHE_ENABLED);
	}

	/**
	 * Caches the kaleo process link in the entity cache if it is enabled.
	 *
	 * @param kaleoProcessLink the kaleo process link
	 */
	@Override
	public void cacheResult(KaleoProcessLink kaleoProcessLink) {
		entityCache.putResult(KaleoProcessLinkModelImpl.ENTITY_CACHE_ENABLED,
			KaleoProcessLinkImpl.class, kaleoProcessLink.getPrimaryKey(),
			kaleoProcessLink);

		finderCache.putResult(FINDER_PATH_FETCH_BY_KPI_WTN,
			new Object[] {
				kaleoProcessLink.getKaleoProcessId(),
				kaleoProcessLink.getWorkflowTaskName()
			}, kaleoProcessLink);

		kaleoProcessLink.resetOriginalValues();
	}

	/**
	 * Caches the kaleo process links in the entity cache if it is enabled.
	 *
	 * @param kaleoProcessLinks the kaleo process links
	 */
	@Override
	public void cacheResult(List<KaleoProcessLink> kaleoProcessLinks) {
		for (KaleoProcessLink kaleoProcessLink : kaleoProcessLinks) {
			if (entityCache.getResult(
						KaleoProcessLinkModelImpl.ENTITY_CACHE_ENABLED,
						KaleoProcessLinkImpl.class,
						kaleoProcessLink.getPrimaryKey()) == null) {
				cacheResult(kaleoProcessLink);
			}
			else {
				kaleoProcessLink.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all kaleo process links.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(KaleoProcessLinkImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the kaleo process link.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(KaleoProcessLink kaleoProcessLink) {
		entityCache.removeResult(KaleoProcessLinkModelImpl.ENTITY_CACHE_ENABLED,
			KaleoProcessLinkImpl.class, kaleoProcessLink.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((KaleoProcessLinkModelImpl)kaleoProcessLink,
			true);
	}

	@Override
	public void clearCache(List<KaleoProcessLink> kaleoProcessLinks) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (KaleoProcessLink kaleoProcessLink : kaleoProcessLinks) {
			entityCache.removeResult(KaleoProcessLinkModelImpl.ENTITY_CACHE_ENABLED,
				KaleoProcessLinkImpl.class, kaleoProcessLink.getPrimaryKey());

			clearUniqueFindersCache((KaleoProcessLinkModelImpl)kaleoProcessLink,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		KaleoProcessLinkModelImpl kaleoProcessLinkModelImpl) {
		Object[] args = new Object[] {
				kaleoProcessLinkModelImpl.getKaleoProcessId(),
				kaleoProcessLinkModelImpl.getWorkflowTaskName()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_KPI_WTN, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_KPI_WTN, args,
			kaleoProcessLinkModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		KaleoProcessLinkModelImpl kaleoProcessLinkModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					kaleoProcessLinkModelImpl.getKaleoProcessId(),
					kaleoProcessLinkModelImpl.getWorkflowTaskName()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_KPI_WTN, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_KPI_WTN, args);
		}

		if ((kaleoProcessLinkModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_KPI_WTN.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					kaleoProcessLinkModelImpl.getOriginalKaleoProcessId(),
					kaleoProcessLinkModelImpl.getOriginalWorkflowTaskName()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_KPI_WTN, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_KPI_WTN, args);
		}
	}

	/**
	 * Creates a new kaleo process link with the primary key. Does not add the kaleo process link to the database.
	 *
	 * @param kaleoProcessLinkId the primary key for the new kaleo process link
	 * @return the new kaleo process link
	 */
	@Override
	public KaleoProcessLink create(long kaleoProcessLinkId) {
		KaleoProcessLink kaleoProcessLink = new KaleoProcessLinkImpl();

		kaleoProcessLink.setNew(true);
		kaleoProcessLink.setPrimaryKey(kaleoProcessLinkId);

		return kaleoProcessLink;
	}

	/**
	 * Removes the kaleo process link with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoProcessLinkId the primary key of the kaleo process link
	 * @return the kaleo process link that was removed
	 * @throws NoSuchKaleoProcessLinkException if a kaleo process link with the primary key could not be found
	 */
	@Override
	public KaleoProcessLink remove(long kaleoProcessLinkId)
		throws NoSuchKaleoProcessLinkException {
		return remove((Serializable)kaleoProcessLinkId);
	}

	/**
	 * Removes the kaleo process link with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the kaleo process link
	 * @return the kaleo process link that was removed
	 * @throws NoSuchKaleoProcessLinkException if a kaleo process link with the primary key could not be found
	 */
	@Override
	public KaleoProcessLink remove(Serializable primaryKey)
		throws NoSuchKaleoProcessLinkException {
		Session session = null;

		try {
			session = openSession();

			KaleoProcessLink kaleoProcessLink = (KaleoProcessLink)session.get(KaleoProcessLinkImpl.class,
					primaryKey);

			if (kaleoProcessLink == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchKaleoProcessLinkException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(kaleoProcessLink);
		}
		catch (NoSuchKaleoProcessLinkException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected KaleoProcessLink removeImpl(KaleoProcessLink kaleoProcessLink) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(kaleoProcessLink)) {
				kaleoProcessLink = (KaleoProcessLink)session.get(KaleoProcessLinkImpl.class,
						kaleoProcessLink.getPrimaryKeyObj());
			}

			if (kaleoProcessLink != null) {
				session.delete(kaleoProcessLink);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (kaleoProcessLink != null) {
			clearCache(kaleoProcessLink);
		}

		return kaleoProcessLink;
	}

	@Override
	public KaleoProcessLink updateImpl(KaleoProcessLink kaleoProcessLink) {
		boolean isNew = kaleoProcessLink.isNew();

		if (!(kaleoProcessLink instanceof KaleoProcessLinkModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(kaleoProcessLink.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(kaleoProcessLink);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in kaleoProcessLink proxy " +
					invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom KaleoProcessLink implementation " +
				kaleoProcessLink.getClass());
		}

		KaleoProcessLinkModelImpl kaleoProcessLinkModelImpl = (KaleoProcessLinkModelImpl)kaleoProcessLink;

		Session session = null;

		try {
			session = openSession();

			if (kaleoProcessLink.isNew()) {
				session.save(kaleoProcessLink);

				kaleoProcessLink.setNew(false);
			}
			else {
				kaleoProcessLink = (KaleoProcessLink)session.merge(kaleoProcessLink);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!KaleoProcessLinkModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					kaleoProcessLinkModelImpl.getKaleoProcessId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_KALEOPROCESSID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_KALEOPROCESSID,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((kaleoProcessLinkModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_KALEOPROCESSID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						kaleoProcessLinkModelImpl.getOriginalKaleoProcessId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_KALEOPROCESSID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_KALEOPROCESSID,
					args);

				args = new Object[] {
						kaleoProcessLinkModelImpl.getKaleoProcessId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_KALEOPROCESSID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_KALEOPROCESSID,
					args);
			}
		}

		entityCache.putResult(KaleoProcessLinkModelImpl.ENTITY_CACHE_ENABLED,
			KaleoProcessLinkImpl.class, kaleoProcessLink.getPrimaryKey(),
			kaleoProcessLink, false);

		clearUniqueFindersCache(kaleoProcessLinkModelImpl, false);
		cacheUniqueFindersCache(kaleoProcessLinkModelImpl);

		kaleoProcessLink.resetOriginalValues();

		return kaleoProcessLink;
	}

	/**
	 * Returns the kaleo process link with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the kaleo process link
	 * @return the kaleo process link
	 * @throws NoSuchKaleoProcessLinkException if a kaleo process link with the primary key could not be found
	 */
	@Override
	public KaleoProcessLink findByPrimaryKey(Serializable primaryKey)
		throws NoSuchKaleoProcessLinkException {
		KaleoProcessLink kaleoProcessLink = fetchByPrimaryKey(primaryKey);

		if (kaleoProcessLink == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchKaleoProcessLinkException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return kaleoProcessLink;
	}

	/**
	 * Returns the kaleo process link with the primary key or throws a {@link NoSuchKaleoProcessLinkException} if it could not be found.
	 *
	 * @param kaleoProcessLinkId the primary key of the kaleo process link
	 * @return the kaleo process link
	 * @throws NoSuchKaleoProcessLinkException if a kaleo process link with the primary key could not be found
	 */
	@Override
	public KaleoProcessLink findByPrimaryKey(long kaleoProcessLinkId)
		throws NoSuchKaleoProcessLinkException {
		return findByPrimaryKey((Serializable)kaleoProcessLinkId);
	}

	/**
	 * Returns the kaleo process link with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param kaleoProcessLinkId the primary key of the kaleo process link
	 * @return the kaleo process link, or <code>null</code> if a kaleo process link with the primary key could not be found
	 */
	@Override
	public KaleoProcessLink fetchByPrimaryKey(long kaleoProcessLinkId) {
		return fetchByPrimaryKey((Serializable)kaleoProcessLinkId);
	}

	@Override
	public Map<Serializable, KaleoProcessLink> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, KaleoProcessLink> map = new HashMap<Serializable, KaleoProcessLink>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			KaleoProcessLink kaleoProcessLink = fetchByPrimaryKey(primaryKey);

			if (kaleoProcessLink != null) {
				map.put(primaryKey, kaleoProcessLink);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(KaleoProcessLinkModelImpl.ENTITY_CACHE_ENABLED,
					KaleoProcessLinkImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (KaleoProcessLink)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_KALEOPROCESSLINK_WHERE_PKS_IN);

		for (Serializable primaryKey : uncachedPrimaryKeys) {
			query.append((long)primaryKey);

			query.append(",");
		}

		query.setIndex(query.index() - 1);

		query.append(")");

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			for (KaleoProcessLink kaleoProcessLink : (List<KaleoProcessLink>)q.list()) {
				map.put(kaleoProcessLink.getPrimaryKeyObj(), kaleoProcessLink);

				cacheResult(kaleoProcessLink);

				uncachedPrimaryKeys.remove(kaleoProcessLink.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(KaleoProcessLinkModelImpl.ENTITY_CACHE_ENABLED,
					KaleoProcessLinkImpl.class, primaryKey, nullModel);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		return map;
	}

	/**
	 * Returns all the kaleo process links.
	 *
	 * @return the kaleo process links
	 */
	@Override
	public List<KaleoProcessLink> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the kaleo process links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoProcessLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo process links
	 * @param end the upper bound of the range of kaleo process links (not inclusive)
	 * @return the range of kaleo process links
	 */
	@Override
	public List<KaleoProcessLink> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the kaleo process links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoProcessLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo process links
	 * @param end the upper bound of the range of kaleo process links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of kaleo process links
	 */
	@Override
	public List<KaleoProcessLink> findAll(int start, int end,
		OrderByComparator<KaleoProcessLink> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the kaleo process links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoProcessLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo process links
	 * @param end the upper bound of the range of kaleo process links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of kaleo process links
	 */
	@Override
	public List<KaleoProcessLink> findAll(int start, int end,
		OrderByComparator<KaleoProcessLink> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_ALL;
			finderArgs = new Object[] { start, end, orderByComparator };
		}

		List<KaleoProcessLink> list = null;

		if (retrieveFromCache) {
			list = (List<KaleoProcessLink>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_KALEOPROCESSLINK);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_KALEOPROCESSLINK;

				if (pagination) {
					sql = sql.concat(KaleoProcessLinkModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<KaleoProcessLink>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<KaleoProcessLink>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the kaleo process links from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (KaleoProcessLink kaleoProcessLink : findAll()) {
			remove(kaleoProcessLink);
		}
	}

	/**
	 * Returns the number of kaleo process links.
	 *
	 * @return the number of kaleo process links
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_KALEOPROCESSLINK);

				count = (Long)q.uniqueResult();

				finderCache.putResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY,
					count);
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY);

				throw processException(e);
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
	protected Map<String, Integer> getTableColumnsMap() {
		return KaleoProcessLinkModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the kaleo process link persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(KaleoProcessLinkImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;
	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;
	private static final String _SQL_SELECT_KALEOPROCESSLINK = "SELECT kaleoProcessLink FROM KaleoProcessLink kaleoProcessLink";
	private static final String _SQL_SELECT_KALEOPROCESSLINK_WHERE_PKS_IN = "SELECT kaleoProcessLink FROM KaleoProcessLink kaleoProcessLink WHERE kaleoProcessLinkId IN (";
	private static final String _SQL_SELECT_KALEOPROCESSLINK_WHERE = "SELECT kaleoProcessLink FROM KaleoProcessLink kaleoProcessLink WHERE ";
	private static final String _SQL_COUNT_KALEOPROCESSLINK = "SELECT COUNT(kaleoProcessLink) FROM KaleoProcessLink kaleoProcessLink";
	private static final String _SQL_COUNT_KALEOPROCESSLINK_WHERE = "SELECT COUNT(kaleoProcessLink) FROM KaleoProcessLink kaleoProcessLink WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "kaleoProcessLink.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No KaleoProcessLink exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No KaleoProcessLink exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(KaleoProcessLinkPersistenceImpl.class);
}