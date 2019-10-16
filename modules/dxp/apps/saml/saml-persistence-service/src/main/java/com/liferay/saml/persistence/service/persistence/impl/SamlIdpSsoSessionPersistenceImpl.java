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

package com.liferay.saml.persistence.service.persistence.impl;

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
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.saml.persistence.exception.NoSuchIdpSsoSessionException;
import com.liferay.saml.persistence.model.SamlIdpSsoSession;
import com.liferay.saml.persistence.model.impl.SamlIdpSsoSessionImpl;
import com.liferay.saml.persistence.model.impl.SamlIdpSsoSessionModelImpl;
import com.liferay.saml.persistence.service.persistence.SamlIdpSsoSessionPersistence;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.sql.Timestamp;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence implementation for the saml idp sso session service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Mika Koivisto
 * @generated
 */
@ProviderType
public class SamlIdpSsoSessionPersistenceImpl
	extends BasePersistenceImpl<SamlIdpSsoSession>
	implements SamlIdpSsoSessionPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>SamlIdpSsoSessionUtil</code> to access the saml idp sso session persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		SamlIdpSsoSessionImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByCreateDate;
	private FinderPath _finderPathWithPaginationCountByCreateDate;

	/**
	 * Returns all the saml idp sso sessions where createDate &lt; &#63;.
	 *
	 * @param createDate the create date
	 * @return the matching saml idp sso sessions
	 */
	@Override
	public List<SamlIdpSsoSession> findByCreateDate(Date createDate) {
		return findByCreateDate(
			createDate, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the saml idp sso sessions where createDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SamlIdpSsoSessionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param createDate the create date
	 * @param start the lower bound of the range of saml idp sso sessions
	 * @param end the upper bound of the range of saml idp sso sessions (not inclusive)
	 * @return the range of matching saml idp sso sessions
	 */
	@Override
	public List<SamlIdpSsoSession> findByCreateDate(
		Date createDate, int start, int end) {

		return findByCreateDate(createDate, start, end, null);
	}

	/**
	 * Returns an ordered range of all the saml idp sso sessions where createDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SamlIdpSsoSessionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param createDate the create date
	 * @param start the lower bound of the range of saml idp sso sessions
	 * @param end the upper bound of the range of saml idp sso sessions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching saml idp sso sessions
	 */
	@Override
	public List<SamlIdpSsoSession> findByCreateDate(
		Date createDate, int start, int end,
		OrderByComparator<SamlIdpSsoSession> orderByComparator) {

		return findByCreateDate(
			createDate, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the saml idp sso sessions where createDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SamlIdpSsoSessionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param createDate the create date
	 * @param start the lower bound of the range of saml idp sso sessions
	 * @param end the upper bound of the range of saml idp sso sessions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching saml idp sso sessions
	 */
	@Override
	public List<SamlIdpSsoSession> findByCreateDate(
		Date createDate, int start, int end,
		OrderByComparator<SamlIdpSsoSession> orderByComparator,
		boolean useFinderCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByCreateDate;
		finderArgs = new Object[] {
			_getTime(createDate), start, end, orderByComparator
		};

		List<SamlIdpSsoSession> list = null;

		if (useFinderCache) {
			list = (List<SamlIdpSsoSession>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SamlIdpSsoSession samlIdpSsoSession : list) {
					if ((createDate.getTime() <=
							samlIdpSsoSession.getCreateDate().getTime())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_SAMLIDPSSOSESSION_WHERE);

			boolean bindCreateDate = false;

			if (createDate == null) {
				query.append(_FINDER_COLUMN_CREATEDATE_CREATEDATE_1);
			}
			else {
				bindCreateDate = true;

				query.append(_FINDER_COLUMN_CREATEDATE_CREATEDATE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(SamlIdpSsoSessionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindCreateDate) {
					qPos.add(new Timestamp(createDate.getTime()));
				}

				if (!pagination) {
					list = (List<SamlIdpSsoSession>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SamlIdpSsoSession>)QueryUtil.list(
						q, getDialect(), start, end);
				}

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first saml idp sso session in the ordered set where createDate &lt; &#63;.
	 *
	 * @param createDate the create date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching saml idp sso session
	 * @throws NoSuchIdpSsoSessionException if a matching saml idp sso session could not be found
	 */
	@Override
	public SamlIdpSsoSession findByCreateDate_First(
			Date createDate,
			OrderByComparator<SamlIdpSsoSession> orderByComparator)
		throws NoSuchIdpSsoSessionException {

		SamlIdpSsoSession samlIdpSsoSession = fetchByCreateDate_First(
			createDate, orderByComparator);

		if (samlIdpSsoSession != null) {
			return samlIdpSsoSession;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("createDate<");
		msg.append(createDate);

		msg.append("}");

		throw new NoSuchIdpSsoSessionException(msg.toString());
	}

	/**
	 * Returns the first saml idp sso session in the ordered set where createDate &lt; &#63;.
	 *
	 * @param createDate the create date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching saml idp sso session, or <code>null</code> if a matching saml idp sso session could not be found
	 */
	@Override
	public SamlIdpSsoSession fetchByCreateDate_First(
		Date createDate,
		OrderByComparator<SamlIdpSsoSession> orderByComparator) {

		List<SamlIdpSsoSession> list = findByCreateDate(
			createDate, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last saml idp sso session in the ordered set where createDate &lt; &#63;.
	 *
	 * @param createDate the create date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching saml idp sso session
	 * @throws NoSuchIdpSsoSessionException if a matching saml idp sso session could not be found
	 */
	@Override
	public SamlIdpSsoSession findByCreateDate_Last(
			Date createDate,
			OrderByComparator<SamlIdpSsoSession> orderByComparator)
		throws NoSuchIdpSsoSessionException {

		SamlIdpSsoSession samlIdpSsoSession = fetchByCreateDate_Last(
			createDate, orderByComparator);

		if (samlIdpSsoSession != null) {
			return samlIdpSsoSession;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("createDate<");
		msg.append(createDate);

		msg.append("}");

		throw new NoSuchIdpSsoSessionException(msg.toString());
	}

	/**
	 * Returns the last saml idp sso session in the ordered set where createDate &lt; &#63;.
	 *
	 * @param createDate the create date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching saml idp sso session, or <code>null</code> if a matching saml idp sso session could not be found
	 */
	@Override
	public SamlIdpSsoSession fetchByCreateDate_Last(
		Date createDate,
		OrderByComparator<SamlIdpSsoSession> orderByComparator) {

		int count = countByCreateDate(createDate);

		if (count == 0) {
			return null;
		}

		List<SamlIdpSsoSession> list = findByCreateDate(
			createDate, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the saml idp sso sessions before and after the current saml idp sso session in the ordered set where createDate &lt; &#63;.
	 *
	 * @param samlIdpSsoSessionId the primary key of the current saml idp sso session
	 * @param createDate the create date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next saml idp sso session
	 * @throws NoSuchIdpSsoSessionException if a saml idp sso session with the primary key could not be found
	 */
	@Override
	public SamlIdpSsoSession[] findByCreateDate_PrevAndNext(
			long samlIdpSsoSessionId, Date createDate,
			OrderByComparator<SamlIdpSsoSession> orderByComparator)
		throws NoSuchIdpSsoSessionException {

		SamlIdpSsoSession samlIdpSsoSession = findByPrimaryKey(
			samlIdpSsoSessionId);

		Session session = null;

		try {
			session = openSession();

			SamlIdpSsoSession[] array = new SamlIdpSsoSessionImpl[3];

			array[0] = getByCreateDate_PrevAndNext(
				session, samlIdpSsoSession, createDate, orderByComparator,
				true);

			array[1] = samlIdpSsoSession;

			array[2] = getByCreateDate_PrevAndNext(
				session, samlIdpSsoSession, createDate, orderByComparator,
				false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SamlIdpSsoSession getByCreateDate_PrevAndNext(
		Session session, SamlIdpSsoSession samlIdpSsoSession, Date createDate,
		OrderByComparator<SamlIdpSsoSession> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SAMLIDPSSOSESSION_WHERE);

		boolean bindCreateDate = false;

		if (createDate == null) {
			query.append(_FINDER_COLUMN_CREATEDATE_CREATEDATE_1);
		}
		else {
			bindCreateDate = true;

			query.append(_FINDER_COLUMN_CREATEDATE_CREATEDATE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

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
			query.append(SamlIdpSsoSessionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindCreateDate) {
			qPos.add(new Timestamp(createDate.getTime()));
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						samlIdpSsoSession)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<SamlIdpSsoSession> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the saml idp sso sessions where createDate &lt; &#63; from the database.
	 *
	 * @param createDate the create date
	 */
	@Override
	public void removeByCreateDate(Date createDate) {
		for (SamlIdpSsoSession samlIdpSsoSession :
				findByCreateDate(
					createDate, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(samlIdpSsoSession);
		}
	}

	/**
	 * Returns the number of saml idp sso sessions where createDate &lt; &#63;.
	 *
	 * @param createDate the create date
	 * @return the number of matching saml idp sso sessions
	 */
	@Override
	public int countByCreateDate(Date createDate) {
		FinderPath finderPath = _finderPathWithPaginationCountByCreateDate;

		Object[] finderArgs = new Object[] {_getTime(createDate)};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SAMLIDPSSOSESSION_WHERE);

			boolean bindCreateDate = false;

			if (createDate == null) {
				query.append(_FINDER_COLUMN_CREATEDATE_CREATEDATE_1);
			}
			else {
				bindCreateDate = true;

				query.append(_FINDER_COLUMN_CREATEDATE_CREATEDATE_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindCreateDate) {
					qPos.add(new Timestamp(createDate.getTime()));
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

	private static final String _FINDER_COLUMN_CREATEDATE_CREATEDATE_1 =
		"samlIdpSsoSession.createDate IS NULL";

	private static final String _FINDER_COLUMN_CREATEDATE_CREATEDATE_2 =
		"samlIdpSsoSession.createDate < ?";

	private FinderPath _finderPathFetchBySamlIdpSsoSessionKey;
	private FinderPath _finderPathCountBySamlIdpSsoSessionKey;

	/**
	 * Returns the saml idp sso session where samlIdpSsoSessionKey = &#63; or throws a <code>NoSuchIdpSsoSessionException</code> if it could not be found.
	 *
	 * @param samlIdpSsoSessionKey the saml idp sso session key
	 * @return the matching saml idp sso session
	 * @throws NoSuchIdpSsoSessionException if a matching saml idp sso session could not be found
	 */
	@Override
	public SamlIdpSsoSession findBySamlIdpSsoSessionKey(
			String samlIdpSsoSessionKey)
		throws NoSuchIdpSsoSessionException {

		SamlIdpSsoSession samlIdpSsoSession = fetchBySamlIdpSsoSessionKey(
			samlIdpSsoSessionKey);

		if (samlIdpSsoSession == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("samlIdpSsoSessionKey=");
			msg.append(samlIdpSsoSessionKey);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchIdpSsoSessionException(msg.toString());
		}

		return samlIdpSsoSession;
	}

	/**
	 * Returns the saml idp sso session where samlIdpSsoSessionKey = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param samlIdpSsoSessionKey the saml idp sso session key
	 * @return the matching saml idp sso session, or <code>null</code> if a matching saml idp sso session could not be found
	 */
	@Override
	public SamlIdpSsoSession fetchBySamlIdpSsoSessionKey(
		String samlIdpSsoSessionKey) {

		return fetchBySamlIdpSsoSessionKey(samlIdpSsoSessionKey, true);
	}

	/**
	 * Returns the saml idp sso session where samlIdpSsoSessionKey = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param samlIdpSsoSessionKey the saml idp sso session key
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching saml idp sso session, or <code>null</code> if a matching saml idp sso session could not be found
	 */
	@Override
	public SamlIdpSsoSession fetchBySamlIdpSsoSessionKey(
		String samlIdpSsoSessionKey, boolean useFinderCache) {

		samlIdpSsoSessionKey = Objects.toString(samlIdpSsoSessionKey, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {samlIdpSsoSessionKey};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchBySamlIdpSsoSessionKey, finderArgs, this);
		}

		if (result instanceof SamlIdpSsoSession) {
			SamlIdpSsoSession samlIdpSsoSession = (SamlIdpSsoSession)result;

			if (!Objects.equals(
					samlIdpSsoSessionKey,
					samlIdpSsoSession.getSamlIdpSsoSessionKey())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_SAMLIDPSSOSESSION_WHERE);

			boolean bindSamlIdpSsoSessionKey = false;

			if (samlIdpSsoSessionKey.isEmpty()) {
				query.append(
					_FINDER_COLUMN_SAMLIDPSSOSESSIONKEY_SAMLIDPSSOSESSIONKEY_3);
			}
			else {
				bindSamlIdpSsoSessionKey = true;

				query.append(
					_FINDER_COLUMN_SAMLIDPSSOSESSIONKEY_SAMLIDPSSOSESSIONKEY_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindSamlIdpSsoSessionKey) {
					qPos.add(samlIdpSsoSessionKey);
				}

				List<SamlIdpSsoSession> list = q.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchBySamlIdpSsoSessionKey, finderArgs,
							list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {
									samlIdpSsoSessionKey
								};
							}

							_log.warn(
								"SamlIdpSsoSessionPersistenceImpl.fetchBySamlIdpSsoSessionKey(String, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					SamlIdpSsoSession samlIdpSsoSession = list.get(0);

					result = samlIdpSsoSession;

					cacheResult(samlIdpSsoSession);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(
						_finderPathFetchBySamlIdpSsoSessionKey, finderArgs);
				}

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
			return (SamlIdpSsoSession)result;
		}
	}

	/**
	 * Removes the saml idp sso session where samlIdpSsoSessionKey = &#63; from the database.
	 *
	 * @param samlIdpSsoSessionKey the saml idp sso session key
	 * @return the saml idp sso session that was removed
	 */
	@Override
	public SamlIdpSsoSession removeBySamlIdpSsoSessionKey(
			String samlIdpSsoSessionKey)
		throws NoSuchIdpSsoSessionException {

		SamlIdpSsoSession samlIdpSsoSession = findBySamlIdpSsoSessionKey(
			samlIdpSsoSessionKey);

		return remove(samlIdpSsoSession);
	}

	/**
	 * Returns the number of saml idp sso sessions where samlIdpSsoSessionKey = &#63;.
	 *
	 * @param samlIdpSsoSessionKey the saml idp sso session key
	 * @return the number of matching saml idp sso sessions
	 */
	@Override
	public int countBySamlIdpSsoSessionKey(String samlIdpSsoSessionKey) {
		samlIdpSsoSessionKey = Objects.toString(samlIdpSsoSessionKey, "");

		FinderPath finderPath = _finderPathCountBySamlIdpSsoSessionKey;

		Object[] finderArgs = new Object[] {samlIdpSsoSessionKey};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SAMLIDPSSOSESSION_WHERE);

			boolean bindSamlIdpSsoSessionKey = false;

			if (samlIdpSsoSessionKey.isEmpty()) {
				query.append(
					_FINDER_COLUMN_SAMLIDPSSOSESSIONKEY_SAMLIDPSSOSESSIONKEY_3);
			}
			else {
				bindSamlIdpSsoSessionKey = true;

				query.append(
					_FINDER_COLUMN_SAMLIDPSSOSESSIONKEY_SAMLIDPSSOSESSIONKEY_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindSamlIdpSsoSessionKey) {
					qPos.add(samlIdpSsoSessionKey);
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

	private static final String
		_FINDER_COLUMN_SAMLIDPSSOSESSIONKEY_SAMLIDPSSOSESSIONKEY_2 =
			"samlIdpSsoSession.samlIdpSsoSessionKey = ?";

	private static final String
		_FINDER_COLUMN_SAMLIDPSSOSESSIONKEY_SAMLIDPSSOSESSIONKEY_3 =
			"(samlIdpSsoSession.samlIdpSsoSessionKey IS NULL OR samlIdpSsoSession.samlIdpSsoSessionKey = '')";

	public SamlIdpSsoSessionPersistenceImpl() {
		setModelClass(SamlIdpSsoSession.class);

		setModelImplClass(SamlIdpSsoSessionImpl.class);
		setModelPKClass(long.class);
		setEntityCacheEnabled(SamlIdpSsoSessionModelImpl.ENTITY_CACHE_ENABLED);
	}

	/**
	 * Caches the saml idp sso session in the entity cache if it is enabled.
	 *
	 * @param samlIdpSsoSession the saml idp sso session
	 */
	@Override
	public void cacheResult(SamlIdpSsoSession samlIdpSsoSession) {
		entityCache.putResult(
			SamlIdpSsoSessionModelImpl.ENTITY_CACHE_ENABLED,
			SamlIdpSsoSessionImpl.class, samlIdpSsoSession.getPrimaryKey(),
			samlIdpSsoSession);

		finderCache.putResult(
			_finderPathFetchBySamlIdpSsoSessionKey,
			new Object[] {samlIdpSsoSession.getSamlIdpSsoSessionKey()},
			samlIdpSsoSession);

		samlIdpSsoSession.resetOriginalValues();
	}

	/**
	 * Caches the saml idp sso sessions in the entity cache if it is enabled.
	 *
	 * @param samlIdpSsoSessions the saml idp sso sessions
	 */
	@Override
	public void cacheResult(List<SamlIdpSsoSession> samlIdpSsoSessions) {
		for (SamlIdpSsoSession samlIdpSsoSession : samlIdpSsoSessions) {
			if (entityCache.getResult(
					SamlIdpSsoSessionModelImpl.ENTITY_CACHE_ENABLED,
					SamlIdpSsoSessionImpl.class,
					samlIdpSsoSession.getPrimaryKey()) == null) {

				cacheResult(samlIdpSsoSession);
			}
			else {
				samlIdpSsoSession.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all saml idp sso sessions.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(SamlIdpSsoSessionImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the saml idp sso session.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(SamlIdpSsoSession samlIdpSsoSession) {
		entityCache.removeResult(
			SamlIdpSsoSessionModelImpl.ENTITY_CACHE_ENABLED,
			SamlIdpSsoSessionImpl.class, samlIdpSsoSession.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(SamlIdpSsoSessionModelImpl)samlIdpSsoSession, true);
	}

	@Override
	public void clearCache(List<SamlIdpSsoSession> samlIdpSsoSessions) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (SamlIdpSsoSession samlIdpSsoSession : samlIdpSsoSessions) {
			entityCache.removeResult(
				SamlIdpSsoSessionModelImpl.ENTITY_CACHE_ENABLED,
				SamlIdpSsoSessionImpl.class, samlIdpSsoSession.getPrimaryKey());

			clearUniqueFindersCache(
				(SamlIdpSsoSessionModelImpl)samlIdpSsoSession, true);
		}
	}

	protected void cacheUniqueFindersCache(
		SamlIdpSsoSessionModelImpl samlIdpSsoSessionModelImpl) {

		Object[] args = new Object[] {
			samlIdpSsoSessionModelImpl.getSamlIdpSsoSessionKey()
		};

		finderCache.putResult(
			_finderPathCountBySamlIdpSsoSessionKey, args, Long.valueOf(1),
			false);
		finderCache.putResult(
			_finderPathFetchBySamlIdpSsoSessionKey, args,
			samlIdpSsoSessionModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		SamlIdpSsoSessionModelImpl samlIdpSsoSessionModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				samlIdpSsoSessionModelImpl.getSamlIdpSsoSessionKey()
			};

			finderCache.removeResult(
				_finderPathCountBySamlIdpSsoSessionKey, args);
			finderCache.removeResult(
				_finderPathFetchBySamlIdpSsoSessionKey, args);
		}

		if ((samlIdpSsoSessionModelImpl.getColumnBitmask() &
			 _finderPathFetchBySamlIdpSsoSessionKey.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				samlIdpSsoSessionModelImpl.getOriginalSamlIdpSsoSessionKey()
			};

			finderCache.removeResult(
				_finderPathCountBySamlIdpSsoSessionKey, args);
			finderCache.removeResult(
				_finderPathFetchBySamlIdpSsoSessionKey, args);
		}
	}

	/**
	 * Creates a new saml idp sso session with the primary key. Does not add the saml idp sso session to the database.
	 *
	 * @param samlIdpSsoSessionId the primary key for the new saml idp sso session
	 * @return the new saml idp sso session
	 */
	@Override
	public SamlIdpSsoSession create(long samlIdpSsoSessionId) {
		SamlIdpSsoSession samlIdpSsoSession = new SamlIdpSsoSessionImpl();

		samlIdpSsoSession.setNew(true);
		samlIdpSsoSession.setPrimaryKey(samlIdpSsoSessionId);

		samlIdpSsoSession.setCompanyId(CompanyThreadLocal.getCompanyId());

		return samlIdpSsoSession;
	}

	/**
	 * Removes the saml idp sso session with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param samlIdpSsoSessionId the primary key of the saml idp sso session
	 * @return the saml idp sso session that was removed
	 * @throws NoSuchIdpSsoSessionException if a saml idp sso session with the primary key could not be found
	 */
	@Override
	public SamlIdpSsoSession remove(long samlIdpSsoSessionId)
		throws NoSuchIdpSsoSessionException {

		return remove((Serializable)samlIdpSsoSessionId);
	}

	/**
	 * Removes the saml idp sso session with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the saml idp sso session
	 * @return the saml idp sso session that was removed
	 * @throws NoSuchIdpSsoSessionException if a saml idp sso session with the primary key could not be found
	 */
	@Override
	public SamlIdpSsoSession remove(Serializable primaryKey)
		throws NoSuchIdpSsoSessionException {

		Session session = null;

		try {
			session = openSession();

			SamlIdpSsoSession samlIdpSsoSession =
				(SamlIdpSsoSession)session.get(
					SamlIdpSsoSessionImpl.class, primaryKey);

			if (samlIdpSsoSession == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchIdpSsoSessionException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(samlIdpSsoSession);
		}
		catch (NoSuchIdpSsoSessionException nsee) {
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
	protected SamlIdpSsoSession removeImpl(
		SamlIdpSsoSession samlIdpSsoSession) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(samlIdpSsoSession)) {
				samlIdpSsoSession = (SamlIdpSsoSession)session.get(
					SamlIdpSsoSessionImpl.class,
					samlIdpSsoSession.getPrimaryKeyObj());
			}

			if (samlIdpSsoSession != null) {
				session.delete(samlIdpSsoSession);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (samlIdpSsoSession != null) {
			clearCache(samlIdpSsoSession);
		}

		return samlIdpSsoSession;
	}

	@Override
	public SamlIdpSsoSession updateImpl(SamlIdpSsoSession samlIdpSsoSession) {
		boolean isNew = samlIdpSsoSession.isNew();

		if (!(samlIdpSsoSession instanceof SamlIdpSsoSessionModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(samlIdpSsoSession.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					samlIdpSsoSession);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in samlIdpSsoSession proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom SamlIdpSsoSession implementation " +
					samlIdpSsoSession.getClass());
		}

		SamlIdpSsoSessionModelImpl samlIdpSsoSessionModelImpl =
			(SamlIdpSsoSessionModelImpl)samlIdpSsoSession;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (samlIdpSsoSession.getCreateDate() == null)) {
			if (serviceContext == null) {
				samlIdpSsoSession.setCreateDate(now);
			}
			else {
				samlIdpSsoSession.setCreateDate(
					serviceContext.getCreateDate(now));
			}
		}

		if (!samlIdpSsoSessionModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				samlIdpSsoSession.setModifiedDate(now);
			}
			else {
				samlIdpSsoSession.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (samlIdpSsoSession.isNew()) {
				session.save(samlIdpSsoSession);

				samlIdpSsoSession.setNew(false);
			}
			else {
				samlIdpSsoSession = (SamlIdpSsoSession)session.merge(
					samlIdpSsoSession);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!SamlIdpSsoSessionModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}

		entityCache.putResult(
			SamlIdpSsoSessionModelImpl.ENTITY_CACHE_ENABLED,
			SamlIdpSsoSessionImpl.class, samlIdpSsoSession.getPrimaryKey(),
			samlIdpSsoSession, false);

		clearUniqueFindersCache(samlIdpSsoSessionModelImpl, false);
		cacheUniqueFindersCache(samlIdpSsoSessionModelImpl);

		samlIdpSsoSession.resetOriginalValues();

		return samlIdpSsoSession;
	}

	/**
	 * Returns the saml idp sso session with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the saml idp sso session
	 * @return the saml idp sso session
	 * @throws NoSuchIdpSsoSessionException if a saml idp sso session with the primary key could not be found
	 */
	@Override
	public SamlIdpSsoSession findByPrimaryKey(Serializable primaryKey)
		throws NoSuchIdpSsoSessionException {

		SamlIdpSsoSession samlIdpSsoSession = fetchByPrimaryKey(primaryKey);

		if (samlIdpSsoSession == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchIdpSsoSessionException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return samlIdpSsoSession;
	}

	/**
	 * Returns the saml idp sso session with the primary key or throws a <code>NoSuchIdpSsoSessionException</code> if it could not be found.
	 *
	 * @param samlIdpSsoSessionId the primary key of the saml idp sso session
	 * @return the saml idp sso session
	 * @throws NoSuchIdpSsoSessionException if a saml idp sso session with the primary key could not be found
	 */
	@Override
	public SamlIdpSsoSession findByPrimaryKey(long samlIdpSsoSessionId)
		throws NoSuchIdpSsoSessionException {

		return findByPrimaryKey((Serializable)samlIdpSsoSessionId);
	}

	/**
	 * Returns the saml idp sso session with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param samlIdpSsoSessionId the primary key of the saml idp sso session
	 * @return the saml idp sso session, or <code>null</code> if a saml idp sso session with the primary key could not be found
	 */
	@Override
	public SamlIdpSsoSession fetchByPrimaryKey(long samlIdpSsoSessionId) {
		return fetchByPrimaryKey((Serializable)samlIdpSsoSessionId);
	}

	/**
	 * Returns all the saml idp sso sessions.
	 *
	 * @return the saml idp sso sessions
	 */
	@Override
	public List<SamlIdpSsoSession> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the saml idp sso sessions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SamlIdpSsoSessionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of saml idp sso sessions
	 * @param end the upper bound of the range of saml idp sso sessions (not inclusive)
	 * @return the range of saml idp sso sessions
	 */
	@Override
	public List<SamlIdpSsoSession> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the saml idp sso sessions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SamlIdpSsoSessionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of saml idp sso sessions
	 * @param end the upper bound of the range of saml idp sso sessions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of saml idp sso sessions
	 */
	@Override
	public List<SamlIdpSsoSession> findAll(
		int start, int end,
		OrderByComparator<SamlIdpSsoSession> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the saml idp sso sessions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SamlIdpSsoSessionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of saml idp sso sessions
	 * @param end the upper bound of the range of saml idp sso sessions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of saml idp sso sessions
	 */
	@Override
	public List<SamlIdpSsoSession> findAll(
		int start, int end,
		OrderByComparator<SamlIdpSsoSession> orderByComparator,
		boolean useFinderCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindAll;
				finderArgs = FINDER_ARGS_EMPTY;
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<SamlIdpSsoSession> list = null;

		if (useFinderCache) {
			list = (List<SamlIdpSsoSession>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_SAMLIDPSSOSESSION);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_SAMLIDPSSOSESSION;

				if (pagination) {
					sql = sql.concat(SamlIdpSsoSessionModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<SamlIdpSsoSession>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SamlIdpSsoSession>)QueryUtil.list(
						q, getDialect(), start, end);
				}

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the saml idp sso sessions from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (SamlIdpSsoSession samlIdpSsoSession : findAll()) {
			remove(samlIdpSsoSession);
		}
	}

	/**
	 * Returns the number of saml idp sso sessions.
	 *
	 * @return the number of saml idp sso sessions
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_SAMLIDPSSOSESSION);

				count = (Long)q.uniqueResult();

				finderCache.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception e) {
				finderCache.removeResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY);

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
	protected String getPKDBName() {
		return "samlIdpSsoSessionId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_SAMLIDPSSOSESSION;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return SamlIdpSsoSessionModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the saml idp sso session persistence.
	 */
	public void afterPropertiesSet() {
		_finderPathWithPaginationFindAll = new FinderPath(
			SamlIdpSsoSessionModelImpl.ENTITY_CACHE_ENABLED,
			SamlIdpSsoSessionModelImpl.FINDER_CACHE_ENABLED,
			SamlIdpSsoSessionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			SamlIdpSsoSessionModelImpl.ENTITY_CACHE_ENABLED,
			SamlIdpSsoSessionModelImpl.FINDER_CACHE_ENABLED,
			SamlIdpSsoSessionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			SamlIdpSsoSessionModelImpl.ENTITY_CACHE_ENABLED,
			SamlIdpSsoSessionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByCreateDate = new FinderPath(
			SamlIdpSsoSessionModelImpl.ENTITY_CACHE_ENABLED,
			SamlIdpSsoSessionModelImpl.FINDER_CACHE_ENABLED,
			SamlIdpSsoSessionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCreateDate",
			new String[] {
				Date.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithPaginationCountByCreateDate = new FinderPath(
			SamlIdpSsoSessionModelImpl.ENTITY_CACHE_ENABLED,
			SamlIdpSsoSessionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByCreateDate",
			new String[] {Date.class.getName()});

		_finderPathFetchBySamlIdpSsoSessionKey = new FinderPath(
			SamlIdpSsoSessionModelImpl.ENTITY_CACHE_ENABLED,
			SamlIdpSsoSessionModelImpl.FINDER_CACHE_ENABLED,
			SamlIdpSsoSessionImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchBySamlIdpSsoSessionKey",
			new String[] {String.class.getName()},
			SamlIdpSsoSessionModelImpl.SAMLIDPSSOSESSIONKEY_COLUMN_BITMASK);

		_finderPathCountBySamlIdpSsoSessionKey = new FinderPath(
			SamlIdpSsoSessionModelImpl.ENTITY_CACHE_ENABLED,
			SamlIdpSsoSessionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countBySamlIdpSsoSessionKey",
			new String[] {String.class.getName()});
	}

	public void destroy() {
		entityCache.removeCache(SamlIdpSsoSessionImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private Long _getTime(Date date) {
		if (date == null) {
			return null;
		}

		return date.getTime();
	}

	private static final String _SQL_SELECT_SAMLIDPSSOSESSION =
		"SELECT samlIdpSsoSession FROM SamlIdpSsoSession samlIdpSsoSession";

	private static final String _SQL_SELECT_SAMLIDPSSOSESSION_WHERE =
		"SELECT samlIdpSsoSession FROM SamlIdpSsoSession samlIdpSsoSession WHERE ";

	private static final String _SQL_COUNT_SAMLIDPSSOSESSION =
		"SELECT COUNT(samlIdpSsoSession) FROM SamlIdpSsoSession samlIdpSsoSession";

	private static final String _SQL_COUNT_SAMLIDPSSOSESSION_WHERE =
		"SELECT COUNT(samlIdpSsoSession) FROM SamlIdpSsoSession samlIdpSsoSession WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "samlIdpSsoSession.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No SamlIdpSsoSession exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No SamlIdpSsoSession exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		SamlIdpSsoSessionPersistenceImpl.class);

}