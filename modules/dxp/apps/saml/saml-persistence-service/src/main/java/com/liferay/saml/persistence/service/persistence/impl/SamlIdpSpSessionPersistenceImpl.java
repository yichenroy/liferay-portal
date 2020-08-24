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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.saml.persistence.exception.NoSuchIdpSpSessionException;
import com.liferay.saml.persistence.model.SamlIdpSpSession;
import com.liferay.saml.persistence.model.SamlIdpSpSessionTable;
import com.liferay.saml.persistence.model.impl.SamlIdpSpSessionImpl;
import com.liferay.saml.persistence.model.impl.SamlIdpSpSessionModelImpl;
import com.liferay.saml.persistence.service.persistence.SamlIdpSpSessionPersistence;
import com.liferay.saml.persistence.service.persistence.impl.constants.SamlPersistenceConstants;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.sql.Timestamp;

import java.util.Collections;
import java.util.Date;
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
 * The persistence implementation for the saml idp sp session service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Mika Koivisto
 * @generated
 */
@Component(service = SamlIdpSpSessionPersistence.class)
public class SamlIdpSpSessionPersistenceImpl
	extends BasePersistenceImpl<SamlIdpSpSession>
	implements SamlIdpSpSessionPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>SamlIdpSpSessionUtil</code> to access the saml idp sp session persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		SamlIdpSpSessionImpl.class.getName();

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
	 * Returns all the saml idp sp sessions where createDate &lt; &#63;.
	 *
	 * @param createDate the create date
	 * @return the matching saml idp sp sessions
	 */
	@Override
	public List<SamlIdpSpSession> findByCreateDate(Date createDate) {
		return findByCreateDate(
			createDate, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the saml idp sp sessions where createDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SamlIdpSpSessionModelImpl</code>.
	 * </p>
	 *
	 * @param createDate the create date
	 * @param start the lower bound of the range of saml idp sp sessions
	 * @param end the upper bound of the range of saml idp sp sessions (not inclusive)
	 * @return the range of matching saml idp sp sessions
	 */
	@Override
	public List<SamlIdpSpSession> findByCreateDate(
		Date createDate, int start, int end) {

		return findByCreateDate(createDate, start, end, null);
	}

	/**
	 * Returns an ordered range of all the saml idp sp sessions where createDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SamlIdpSpSessionModelImpl</code>.
	 * </p>
	 *
	 * @param createDate the create date
	 * @param start the lower bound of the range of saml idp sp sessions
	 * @param end the upper bound of the range of saml idp sp sessions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching saml idp sp sessions
	 */
	@Override
	public List<SamlIdpSpSession> findByCreateDate(
		Date createDate, int start, int end,
		OrderByComparator<SamlIdpSpSession> orderByComparator) {

		return findByCreateDate(
			createDate, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the saml idp sp sessions where createDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SamlIdpSpSessionModelImpl</code>.
	 * </p>
	 *
	 * @param createDate the create date
	 * @param start the lower bound of the range of saml idp sp sessions
	 * @param end the upper bound of the range of saml idp sp sessions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching saml idp sp sessions
	 */
	@Override
	public List<SamlIdpSpSession> findByCreateDate(
		Date createDate, int start, int end,
		OrderByComparator<SamlIdpSpSession> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByCreateDate;
		finderArgs = new Object[] {
			_getTime(createDate), start, end, orderByComparator
		};

		List<SamlIdpSpSession> list = null;

		if (useFinderCache) {
			list = (List<SamlIdpSpSession>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SamlIdpSpSession samlIdpSpSession : list) {
					if (createDate.getTime() <=
							samlIdpSpSession.getCreateDate().getTime()) {

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

			sb.append(_SQL_SELECT_SAMLIDPSPSESSION_WHERE);

			boolean bindCreateDate = false;

			if (createDate == null) {
				sb.append(_FINDER_COLUMN_CREATEDATE_CREATEDATE_1);
			}
			else {
				bindCreateDate = true;

				sb.append(_FINDER_COLUMN_CREATEDATE_CREATEDATE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(SamlIdpSpSessionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindCreateDate) {
					queryPos.add(new Timestamp(createDate.getTime()));
				}

				list = (List<SamlIdpSpSession>)QueryUtil.list(
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
	 * Returns the first saml idp sp session in the ordered set where createDate &lt; &#63;.
	 *
	 * @param createDate the create date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching saml idp sp session
	 * @throws NoSuchIdpSpSessionException if a matching saml idp sp session could not be found
	 */
	@Override
	public SamlIdpSpSession findByCreateDate_First(
			Date createDate,
			OrderByComparator<SamlIdpSpSession> orderByComparator)
		throws NoSuchIdpSpSessionException {

		SamlIdpSpSession samlIdpSpSession = fetchByCreateDate_First(
			createDate, orderByComparator);

		if (samlIdpSpSession != null) {
			return samlIdpSpSession;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("createDate<");
		sb.append(createDate);

		sb.append("}");

		throw new NoSuchIdpSpSessionException(sb.toString());
	}

	/**
	 * Returns the first saml idp sp session in the ordered set where createDate &lt; &#63;.
	 *
	 * @param createDate the create date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching saml idp sp session, or <code>null</code> if a matching saml idp sp session could not be found
	 */
	@Override
	public SamlIdpSpSession fetchByCreateDate_First(
		Date createDate,
		OrderByComparator<SamlIdpSpSession> orderByComparator) {

		List<SamlIdpSpSession> list = findByCreateDate(
			createDate, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last saml idp sp session in the ordered set where createDate &lt; &#63;.
	 *
	 * @param createDate the create date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching saml idp sp session
	 * @throws NoSuchIdpSpSessionException if a matching saml idp sp session could not be found
	 */
	@Override
	public SamlIdpSpSession findByCreateDate_Last(
			Date createDate,
			OrderByComparator<SamlIdpSpSession> orderByComparator)
		throws NoSuchIdpSpSessionException {

		SamlIdpSpSession samlIdpSpSession = fetchByCreateDate_Last(
			createDate, orderByComparator);

		if (samlIdpSpSession != null) {
			return samlIdpSpSession;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("createDate<");
		sb.append(createDate);

		sb.append("}");

		throw new NoSuchIdpSpSessionException(sb.toString());
	}

	/**
	 * Returns the last saml idp sp session in the ordered set where createDate &lt; &#63;.
	 *
	 * @param createDate the create date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching saml idp sp session, or <code>null</code> if a matching saml idp sp session could not be found
	 */
	@Override
	public SamlIdpSpSession fetchByCreateDate_Last(
		Date createDate,
		OrderByComparator<SamlIdpSpSession> orderByComparator) {

		int count = countByCreateDate(createDate);

		if (count == 0) {
			return null;
		}

		List<SamlIdpSpSession> list = findByCreateDate(
			createDate, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the saml idp sp sessions before and after the current saml idp sp session in the ordered set where createDate &lt; &#63;.
	 *
	 * @param samlIdpSpSessionId the primary key of the current saml idp sp session
	 * @param createDate the create date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next saml idp sp session
	 * @throws NoSuchIdpSpSessionException if a saml idp sp session with the primary key could not be found
	 */
	@Override
	public SamlIdpSpSession[] findByCreateDate_PrevAndNext(
			long samlIdpSpSessionId, Date createDate,
			OrderByComparator<SamlIdpSpSession> orderByComparator)
		throws NoSuchIdpSpSessionException {

		SamlIdpSpSession samlIdpSpSession = findByPrimaryKey(
			samlIdpSpSessionId);

		Session session = null;

		try {
			session = openSession();

			SamlIdpSpSession[] array = new SamlIdpSpSessionImpl[3];

			array[0] = getByCreateDate_PrevAndNext(
				session, samlIdpSpSession, createDate, orderByComparator, true);

			array[1] = samlIdpSpSession;

			array[2] = getByCreateDate_PrevAndNext(
				session, samlIdpSpSession, createDate, orderByComparator,
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

	protected SamlIdpSpSession getByCreateDate_PrevAndNext(
		Session session, SamlIdpSpSession samlIdpSpSession, Date createDate,
		OrderByComparator<SamlIdpSpSession> orderByComparator,
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

		sb.append(_SQL_SELECT_SAMLIDPSPSESSION_WHERE);

		boolean bindCreateDate = false;

		if (createDate == null) {
			sb.append(_FINDER_COLUMN_CREATEDATE_CREATEDATE_1);
		}
		else {
			bindCreateDate = true;

			sb.append(_FINDER_COLUMN_CREATEDATE_CREATEDATE_2);
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
			sb.append(SamlIdpSpSessionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindCreateDate) {
			queryPos.add(new Timestamp(createDate.getTime()));
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						samlIdpSpSession)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SamlIdpSpSession> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the saml idp sp sessions where createDate &lt; &#63; from the database.
	 *
	 * @param createDate the create date
	 */
	@Override
	public void removeByCreateDate(Date createDate) {
		for (SamlIdpSpSession samlIdpSpSession :
				findByCreateDate(
					createDate, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(samlIdpSpSession);
		}
	}

	/**
	 * Returns the number of saml idp sp sessions where createDate &lt; &#63;.
	 *
	 * @param createDate the create date
	 * @return the number of matching saml idp sp sessions
	 */
	@Override
	public int countByCreateDate(Date createDate) {
		FinderPath finderPath = _finderPathWithPaginationCountByCreateDate;

		Object[] finderArgs = new Object[] {_getTime(createDate)};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_SAMLIDPSPSESSION_WHERE);

			boolean bindCreateDate = false;

			if (createDate == null) {
				sb.append(_FINDER_COLUMN_CREATEDATE_CREATEDATE_1);
			}
			else {
				bindCreateDate = true;

				sb.append(_FINDER_COLUMN_CREATEDATE_CREATEDATE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindCreateDate) {
					queryPos.add(new Timestamp(createDate.getTime()));
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

	private static final String _FINDER_COLUMN_CREATEDATE_CREATEDATE_1 =
		"samlIdpSpSession.createDate IS NULL";

	private static final String _FINDER_COLUMN_CREATEDATE_CREATEDATE_2 =
		"samlIdpSpSession.createDate < ?";

	private FinderPath _finderPathWithPaginationFindBySamlIdpSsoSessionId;
	private FinderPath _finderPathWithoutPaginationFindBySamlIdpSsoSessionId;
	private FinderPath _finderPathCountBySamlIdpSsoSessionId;

	/**
	 * Returns all the saml idp sp sessions where samlIdpSsoSessionId = &#63;.
	 *
	 * @param samlIdpSsoSessionId the saml idp sso session ID
	 * @return the matching saml idp sp sessions
	 */
	@Override
	public List<SamlIdpSpSession> findBySamlIdpSsoSessionId(
		long samlIdpSsoSessionId) {

		return findBySamlIdpSsoSessionId(
			samlIdpSsoSessionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the saml idp sp sessions where samlIdpSsoSessionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SamlIdpSpSessionModelImpl</code>.
	 * </p>
	 *
	 * @param samlIdpSsoSessionId the saml idp sso session ID
	 * @param start the lower bound of the range of saml idp sp sessions
	 * @param end the upper bound of the range of saml idp sp sessions (not inclusive)
	 * @return the range of matching saml idp sp sessions
	 */
	@Override
	public List<SamlIdpSpSession> findBySamlIdpSsoSessionId(
		long samlIdpSsoSessionId, int start, int end) {

		return findBySamlIdpSsoSessionId(samlIdpSsoSessionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the saml idp sp sessions where samlIdpSsoSessionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SamlIdpSpSessionModelImpl</code>.
	 * </p>
	 *
	 * @param samlIdpSsoSessionId the saml idp sso session ID
	 * @param start the lower bound of the range of saml idp sp sessions
	 * @param end the upper bound of the range of saml idp sp sessions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching saml idp sp sessions
	 */
	@Override
	public List<SamlIdpSpSession> findBySamlIdpSsoSessionId(
		long samlIdpSsoSessionId, int start, int end,
		OrderByComparator<SamlIdpSpSession> orderByComparator) {

		return findBySamlIdpSsoSessionId(
			samlIdpSsoSessionId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the saml idp sp sessions where samlIdpSsoSessionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SamlIdpSpSessionModelImpl</code>.
	 * </p>
	 *
	 * @param samlIdpSsoSessionId the saml idp sso session ID
	 * @param start the lower bound of the range of saml idp sp sessions
	 * @param end the upper bound of the range of saml idp sp sessions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching saml idp sp sessions
	 */
	@Override
	public List<SamlIdpSpSession> findBySamlIdpSsoSessionId(
		long samlIdpSsoSessionId, int start, int end,
		OrderByComparator<SamlIdpSpSession> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindBySamlIdpSsoSessionId;
				finderArgs = new Object[] {samlIdpSsoSessionId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindBySamlIdpSsoSessionId;
			finderArgs = new Object[] {
				samlIdpSsoSessionId, start, end, orderByComparator
			};
		}

		List<SamlIdpSpSession> list = null;

		if (useFinderCache) {
			list = (List<SamlIdpSpSession>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SamlIdpSpSession samlIdpSpSession : list) {
					if (samlIdpSsoSessionId !=
							samlIdpSpSession.getSamlIdpSsoSessionId()) {

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

			sb.append(_SQL_SELECT_SAMLIDPSPSESSION_WHERE);

			sb.append(_FINDER_COLUMN_SAMLIDPSSOSESSIONID_SAMLIDPSSOSESSIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(SamlIdpSpSessionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(samlIdpSsoSessionId);

				list = (List<SamlIdpSpSession>)QueryUtil.list(
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
	 * Returns the first saml idp sp session in the ordered set where samlIdpSsoSessionId = &#63;.
	 *
	 * @param samlIdpSsoSessionId the saml idp sso session ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching saml idp sp session
	 * @throws NoSuchIdpSpSessionException if a matching saml idp sp session could not be found
	 */
	@Override
	public SamlIdpSpSession findBySamlIdpSsoSessionId_First(
			long samlIdpSsoSessionId,
			OrderByComparator<SamlIdpSpSession> orderByComparator)
		throws NoSuchIdpSpSessionException {

		SamlIdpSpSession samlIdpSpSession = fetchBySamlIdpSsoSessionId_First(
			samlIdpSsoSessionId, orderByComparator);

		if (samlIdpSpSession != null) {
			return samlIdpSpSession;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("samlIdpSsoSessionId=");
		sb.append(samlIdpSsoSessionId);

		sb.append("}");

		throw new NoSuchIdpSpSessionException(sb.toString());
	}

	/**
	 * Returns the first saml idp sp session in the ordered set where samlIdpSsoSessionId = &#63;.
	 *
	 * @param samlIdpSsoSessionId the saml idp sso session ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching saml idp sp session, or <code>null</code> if a matching saml idp sp session could not be found
	 */
	@Override
	public SamlIdpSpSession fetchBySamlIdpSsoSessionId_First(
		long samlIdpSsoSessionId,
		OrderByComparator<SamlIdpSpSession> orderByComparator) {

		List<SamlIdpSpSession> list = findBySamlIdpSsoSessionId(
			samlIdpSsoSessionId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last saml idp sp session in the ordered set where samlIdpSsoSessionId = &#63;.
	 *
	 * @param samlIdpSsoSessionId the saml idp sso session ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching saml idp sp session
	 * @throws NoSuchIdpSpSessionException if a matching saml idp sp session could not be found
	 */
	@Override
	public SamlIdpSpSession findBySamlIdpSsoSessionId_Last(
			long samlIdpSsoSessionId,
			OrderByComparator<SamlIdpSpSession> orderByComparator)
		throws NoSuchIdpSpSessionException {

		SamlIdpSpSession samlIdpSpSession = fetchBySamlIdpSsoSessionId_Last(
			samlIdpSsoSessionId, orderByComparator);

		if (samlIdpSpSession != null) {
			return samlIdpSpSession;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("samlIdpSsoSessionId=");
		sb.append(samlIdpSsoSessionId);

		sb.append("}");

		throw new NoSuchIdpSpSessionException(sb.toString());
	}

	/**
	 * Returns the last saml idp sp session in the ordered set where samlIdpSsoSessionId = &#63;.
	 *
	 * @param samlIdpSsoSessionId the saml idp sso session ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching saml idp sp session, or <code>null</code> if a matching saml idp sp session could not be found
	 */
	@Override
	public SamlIdpSpSession fetchBySamlIdpSsoSessionId_Last(
		long samlIdpSsoSessionId,
		OrderByComparator<SamlIdpSpSession> orderByComparator) {

		int count = countBySamlIdpSsoSessionId(samlIdpSsoSessionId);

		if (count == 0) {
			return null;
		}

		List<SamlIdpSpSession> list = findBySamlIdpSsoSessionId(
			samlIdpSsoSessionId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the saml idp sp sessions before and after the current saml idp sp session in the ordered set where samlIdpSsoSessionId = &#63;.
	 *
	 * @param samlIdpSpSessionId the primary key of the current saml idp sp session
	 * @param samlIdpSsoSessionId the saml idp sso session ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next saml idp sp session
	 * @throws NoSuchIdpSpSessionException if a saml idp sp session with the primary key could not be found
	 */
	@Override
	public SamlIdpSpSession[] findBySamlIdpSsoSessionId_PrevAndNext(
			long samlIdpSpSessionId, long samlIdpSsoSessionId,
			OrderByComparator<SamlIdpSpSession> orderByComparator)
		throws NoSuchIdpSpSessionException {

		SamlIdpSpSession samlIdpSpSession = findByPrimaryKey(
			samlIdpSpSessionId);

		Session session = null;

		try {
			session = openSession();

			SamlIdpSpSession[] array = new SamlIdpSpSessionImpl[3];

			array[0] = getBySamlIdpSsoSessionId_PrevAndNext(
				session, samlIdpSpSession, samlIdpSsoSessionId,
				orderByComparator, true);

			array[1] = samlIdpSpSession;

			array[2] = getBySamlIdpSsoSessionId_PrevAndNext(
				session, samlIdpSpSession, samlIdpSsoSessionId,
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

	protected SamlIdpSpSession getBySamlIdpSsoSessionId_PrevAndNext(
		Session session, SamlIdpSpSession samlIdpSpSession,
		long samlIdpSsoSessionId,
		OrderByComparator<SamlIdpSpSession> orderByComparator,
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

		sb.append(_SQL_SELECT_SAMLIDPSPSESSION_WHERE);

		sb.append(_FINDER_COLUMN_SAMLIDPSSOSESSIONID_SAMLIDPSSOSESSIONID_2);

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
			sb.append(SamlIdpSpSessionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(samlIdpSsoSessionId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						samlIdpSpSession)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SamlIdpSpSession> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the saml idp sp sessions where samlIdpSsoSessionId = &#63; from the database.
	 *
	 * @param samlIdpSsoSessionId the saml idp sso session ID
	 */
	@Override
	public void removeBySamlIdpSsoSessionId(long samlIdpSsoSessionId) {
		for (SamlIdpSpSession samlIdpSpSession :
				findBySamlIdpSsoSessionId(
					samlIdpSsoSessionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(samlIdpSpSession);
		}
	}

	/**
	 * Returns the number of saml idp sp sessions where samlIdpSsoSessionId = &#63;.
	 *
	 * @param samlIdpSsoSessionId the saml idp sso session ID
	 * @return the number of matching saml idp sp sessions
	 */
	@Override
	public int countBySamlIdpSsoSessionId(long samlIdpSsoSessionId) {
		FinderPath finderPath = _finderPathCountBySamlIdpSsoSessionId;

		Object[] finderArgs = new Object[] {samlIdpSsoSessionId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_SAMLIDPSPSESSION_WHERE);

			sb.append(_FINDER_COLUMN_SAMLIDPSSOSESSIONID_SAMLIDPSSOSESSIONID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(samlIdpSsoSessionId);

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

	private static final String
		_FINDER_COLUMN_SAMLIDPSSOSESSIONID_SAMLIDPSSOSESSIONID_2 =
			"samlIdpSpSession.samlIdpSsoSessionId = ?";

	private FinderPath _finderPathFetchBySISSI_SSEI;
	private FinderPath _finderPathCountBySISSI_SSEI;

	/**
	 * Returns the saml idp sp session where samlIdpSsoSessionId = &#63; and samlSpEntityId = &#63; or throws a <code>NoSuchIdpSpSessionException</code> if it could not be found.
	 *
	 * @param samlIdpSsoSessionId the saml idp sso session ID
	 * @param samlSpEntityId the saml sp entity ID
	 * @return the matching saml idp sp session
	 * @throws NoSuchIdpSpSessionException if a matching saml idp sp session could not be found
	 */
	@Override
	public SamlIdpSpSession findBySISSI_SSEI(
			long samlIdpSsoSessionId, String samlSpEntityId)
		throws NoSuchIdpSpSessionException {

		SamlIdpSpSession samlIdpSpSession = fetchBySISSI_SSEI(
			samlIdpSsoSessionId, samlSpEntityId);

		if (samlIdpSpSession == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("samlIdpSsoSessionId=");
			sb.append(samlIdpSsoSessionId);

			sb.append(", samlSpEntityId=");
			sb.append(samlSpEntityId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchIdpSpSessionException(sb.toString());
		}

		return samlIdpSpSession;
	}

	/**
	 * Returns the saml idp sp session where samlIdpSsoSessionId = &#63; and samlSpEntityId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param samlIdpSsoSessionId the saml idp sso session ID
	 * @param samlSpEntityId the saml sp entity ID
	 * @return the matching saml idp sp session, or <code>null</code> if a matching saml idp sp session could not be found
	 */
	@Override
	public SamlIdpSpSession fetchBySISSI_SSEI(
		long samlIdpSsoSessionId, String samlSpEntityId) {

		return fetchBySISSI_SSEI(samlIdpSsoSessionId, samlSpEntityId, true);
	}

	/**
	 * Returns the saml idp sp session where samlIdpSsoSessionId = &#63; and samlSpEntityId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param samlIdpSsoSessionId the saml idp sso session ID
	 * @param samlSpEntityId the saml sp entity ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching saml idp sp session, or <code>null</code> if a matching saml idp sp session could not be found
	 */
	@Override
	public SamlIdpSpSession fetchBySISSI_SSEI(
		long samlIdpSsoSessionId, String samlSpEntityId,
		boolean useFinderCache) {

		samlSpEntityId = Objects.toString(samlSpEntityId, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {samlIdpSsoSessionId, samlSpEntityId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchBySISSI_SSEI, finderArgs, this);
		}

		if (result instanceof SamlIdpSpSession) {
			SamlIdpSpSession samlIdpSpSession = (SamlIdpSpSession)result;

			if ((samlIdpSsoSessionId !=
					samlIdpSpSession.getSamlIdpSsoSessionId()) ||
				!Objects.equals(
					samlSpEntityId, samlIdpSpSession.getSamlSpEntityId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_SAMLIDPSPSESSION_WHERE);

			sb.append(_FINDER_COLUMN_SISSI_SSEI_SAMLIDPSSOSESSIONID_2);

			boolean bindSamlSpEntityId = false;

			if (samlSpEntityId.isEmpty()) {
				sb.append(_FINDER_COLUMN_SISSI_SSEI_SAMLSPENTITYID_3);
			}
			else {
				bindSamlSpEntityId = true;

				sb.append(_FINDER_COLUMN_SISSI_SSEI_SAMLSPENTITYID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(samlIdpSsoSessionId);

				if (bindSamlSpEntityId) {
					queryPos.add(samlSpEntityId);
				}

				List<SamlIdpSpSession> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchBySISSI_SSEI, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {
									samlIdpSsoSessionId, samlSpEntityId
								};
							}

							_log.warn(
								"SamlIdpSpSessionPersistenceImpl.fetchBySISSI_SSEI(long, String, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					SamlIdpSpSession samlIdpSpSession = list.get(0);

					result = samlIdpSpSession;

					cacheResult(samlIdpSpSession);
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
			return (SamlIdpSpSession)result;
		}
	}

	/**
	 * Removes the saml idp sp session where samlIdpSsoSessionId = &#63; and samlSpEntityId = &#63; from the database.
	 *
	 * @param samlIdpSsoSessionId the saml idp sso session ID
	 * @param samlSpEntityId the saml sp entity ID
	 * @return the saml idp sp session that was removed
	 */
	@Override
	public SamlIdpSpSession removeBySISSI_SSEI(
			long samlIdpSsoSessionId, String samlSpEntityId)
		throws NoSuchIdpSpSessionException {

		SamlIdpSpSession samlIdpSpSession = findBySISSI_SSEI(
			samlIdpSsoSessionId, samlSpEntityId);

		return remove(samlIdpSpSession);
	}

	/**
	 * Returns the number of saml idp sp sessions where samlIdpSsoSessionId = &#63; and samlSpEntityId = &#63;.
	 *
	 * @param samlIdpSsoSessionId the saml idp sso session ID
	 * @param samlSpEntityId the saml sp entity ID
	 * @return the number of matching saml idp sp sessions
	 */
	@Override
	public int countBySISSI_SSEI(
		long samlIdpSsoSessionId, String samlSpEntityId) {

		samlSpEntityId = Objects.toString(samlSpEntityId, "");

		FinderPath finderPath = _finderPathCountBySISSI_SSEI;

		Object[] finderArgs = new Object[] {
			samlIdpSsoSessionId, samlSpEntityId
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_SAMLIDPSPSESSION_WHERE);

			sb.append(_FINDER_COLUMN_SISSI_SSEI_SAMLIDPSSOSESSIONID_2);

			boolean bindSamlSpEntityId = false;

			if (samlSpEntityId.isEmpty()) {
				sb.append(_FINDER_COLUMN_SISSI_SSEI_SAMLSPENTITYID_3);
			}
			else {
				bindSamlSpEntityId = true;

				sb.append(_FINDER_COLUMN_SISSI_SSEI_SAMLSPENTITYID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(samlIdpSsoSessionId);

				if (bindSamlSpEntityId) {
					queryPos.add(samlSpEntityId);
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

	private static final String
		_FINDER_COLUMN_SISSI_SSEI_SAMLIDPSSOSESSIONID_2 =
			"samlIdpSpSession.samlIdpSsoSessionId = ? AND ";

	private static final String _FINDER_COLUMN_SISSI_SSEI_SAMLSPENTITYID_2 =
		"samlIdpSpSession.samlSpEntityId = ?";

	private static final String _FINDER_COLUMN_SISSI_SSEI_SAMLSPENTITYID_3 =
		"(samlIdpSpSession.samlSpEntityId IS NULL OR samlIdpSpSession.samlSpEntityId = '')";

	public SamlIdpSpSessionPersistenceImpl() {
		setModelClass(SamlIdpSpSession.class);

		setModelImplClass(SamlIdpSpSessionImpl.class);
		setModelPKClass(long.class);

		setTable(SamlIdpSpSessionTable.INSTANCE);
	}

	/**
	 * Caches the saml idp sp session in the entity cache if it is enabled.
	 *
	 * @param samlIdpSpSession the saml idp sp session
	 */
	@Override
	public void cacheResult(SamlIdpSpSession samlIdpSpSession) {
		entityCache.putResult(
			SamlIdpSpSessionImpl.class, samlIdpSpSession.getPrimaryKey(),
			samlIdpSpSession);

		finderCache.putResult(
			_finderPathFetchBySISSI_SSEI,
			new Object[] {
				samlIdpSpSession.getSamlIdpSsoSessionId(),
				samlIdpSpSession.getSamlSpEntityId()
			},
			samlIdpSpSession);
	}

	/**
	 * Caches the saml idp sp sessions in the entity cache if it is enabled.
	 *
	 * @param samlIdpSpSessions the saml idp sp sessions
	 */
	@Override
	public void cacheResult(List<SamlIdpSpSession> samlIdpSpSessions) {
		for (SamlIdpSpSession samlIdpSpSession : samlIdpSpSessions) {
			if (entityCache.getResult(
					SamlIdpSpSessionImpl.class,
					samlIdpSpSession.getPrimaryKey()) == null) {

				cacheResult(samlIdpSpSession);
			}
		}
	}

	/**
	 * Clears the cache for all saml idp sp sessions.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(SamlIdpSpSessionImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the saml idp sp session.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(SamlIdpSpSession samlIdpSpSession) {
		entityCache.removeResult(
			SamlIdpSpSessionImpl.class, samlIdpSpSession.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(SamlIdpSpSessionModelImpl)samlIdpSpSession, true);
	}

	@Override
	public void clearCache(List<SamlIdpSpSession> samlIdpSpSessions) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (SamlIdpSpSession samlIdpSpSession : samlIdpSpSessions) {
			entityCache.removeResult(
				SamlIdpSpSessionImpl.class, samlIdpSpSession.getPrimaryKey());

			clearUniqueFindersCache(
				(SamlIdpSpSessionModelImpl)samlIdpSpSession, true);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(SamlIdpSpSessionImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		SamlIdpSpSessionModelImpl samlIdpSpSessionModelImpl) {

		Object[] args = new Object[] {
			samlIdpSpSessionModelImpl.getSamlIdpSsoSessionId(),
			samlIdpSpSessionModelImpl.getSamlSpEntityId()
		};

		finderCache.putResult(
			_finderPathCountBySISSI_SSEI, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchBySISSI_SSEI, args, samlIdpSpSessionModelImpl,
			false);
	}

	protected void clearUniqueFindersCache(
		SamlIdpSpSessionModelImpl samlIdpSpSessionModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				samlIdpSpSessionModelImpl.getSamlIdpSsoSessionId(),
				samlIdpSpSessionModelImpl.getSamlSpEntityId()
			};

			finderCache.removeResult(_finderPathCountBySISSI_SSEI, args);
			finderCache.removeResult(_finderPathFetchBySISSI_SSEI, args);
		}

		if ((samlIdpSpSessionModelImpl.getColumnBitmask() &
			 _finderPathFetchBySISSI_SSEI.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				samlIdpSpSessionModelImpl.getColumnOriginalValue(
					"samlIdpSsoSessionId"),
				samlIdpSpSessionModelImpl.getColumnOriginalValue(
					"samlSpEntityId")
			};

			finderCache.removeResult(_finderPathCountBySISSI_SSEI, args);
			finderCache.removeResult(_finderPathFetchBySISSI_SSEI, args);
		}
	}

	/**
	 * Creates a new saml idp sp session with the primary key. Does not add the saml idp sp session to the database.
	 *
	 * @param samlIdpSpSessionId the primary key for the new saml idp sp session
	 * @return the new saml idp sp session
	 */
	@Override
	public SamlIdpSpSession create(long samlIdpSpSessionId) {
		SamlIdpSpSession samlIdpSpSession = new SamlIdpSpSessionImpl();

		samlIdpSpSession.setNew(true);
		samlIdpSpSession.setPrimaryKey(samlIdpSpSessionId);

		samlIdpSpSession.setCompanyId(CompanyThreadLocal.getCompanyId());

		return samlIdpSpSession;
	}

	/**
	 * Removes the saml idp sp session with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param samlIdpSpSessionId the primary key of the saml idp sp session
	 * @return the saml idp sp session that was removed
	 * @throws NoSuchIdpSpSessionException if a saml idp sp session with the primary key could not be found
	 */
	@Override
	public SamlIdpSpSession remove(long samlIdpSpSessionId)
		throws NoSuchIdpSpSessionException {

		return remove((Serializable)samlIdpSpSessionId);
	}

	/**
	 * Removes the saml idp sp session with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the saml idp sp session
	 * @return the saml idp sp session that was removed
	 * @throws NoSuchIdpSpSessionException if a saml idp sp session with the primary key could not be found
	 */
	@Override
	public SamlIdpSpSession remove(Serializable primaryKey)
		throws NoSuchIdpSpSessionException {

		Session session = null;

		try {
			session = openSession();

			SamlIdpSpSession samlIdpSpSession = (SamlIdpSpSession)session.get(
				SamlIdpSpSessionImpl.class, primaryKey);

			if (samlIdpSpSession == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchIdpSpSessionException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(samlIdpSpSession);
		}
		catch (NoSuchIdpSpSessionException noSuchEntityException) {
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
	protected SamlIdpSpSession removeImpl(SamlIdpSpSession samlIdpSpSession) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(samlIdpSpSession)) {
				samlIdpSpSession = (SamlIdpSpSession)session.get(
					SamlIdpSpSessionImpl.class,
					samlIdpSpSession.getPrimaryKeyObj());
			}

			if (samlIdpSpSession != null) {
				session.delete(samlIdpSpSession);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (samlIdpSpSession != null) {
			clearCache(samlIdpSpSession);
		}

		return samlIdpSpSession;
	}

	@Override
	public SamlIdpSpSession updateImpl(SamlIdpSpSession samlIdpSpSession) {
		boolean isNew = samlIdpSpSession.isNew();

		if (!(samlIdpSpSession instanceof SamlIdpSpSessionModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(samlIdpSpSession.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					samlIdpSpSession);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in samlIdpSpSession proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom SamlIdpSpSession implementation " +
					samlIdpSpSession.getClass());
		}

		SamlIdpSpSessionModelImpl samlIdpSpSessionModelImpl =
			(SamlIdpSpSessionModelImpl)samlIdpSpSession;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (samlIdpSpSession.getCreateDate() == null)) {
			if (serviceContext == null) {
				samlIdpSpSession.setCreateDate(now);
			}
			else {
				samlIdpSpSession.setCreateDate(
					serviceContext.getCreateDate(now));
			}
		}

		if (!samlIdpSpSessionModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				samlIdpSpSession.setModifiedDate(now);
			}
			else {
				samlIdpSpSession.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (samlIdpSpSession.isNew()) {
				session.save(samlIdpSpSession);

				samlIdpSpSession.setNew(false);
			}
			else {
				samlIdpSpSession = (SamlIdpSpSession)session.merge(
					samlIdpSpSession);
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
				samlIdpSpSessionModelImpl.getSamlIdpSsoSessionId()
			};

			finderCache.removeResult(
				_finderPathCountBySamlIdpSsoSessionId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindBySamlIdpSsoSessionId, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((samlIdpSpSessionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindBySamlIdpSsoSessionId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					samlIdpSpSessionModelImpl.getColumnOriginalValue(
						"samlIdpSsoSessionId")
				};

				finderCache.removeResult(
					_finderPathCountBySamlIdpSsoSessionId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindBySamlIdpSsoSessionId,
					args);

				args = new Object[] {
					samlIdpSpSessionModelImpl.getSamlIdpSsoSessionId()
				};

				finderCache.removeResult(
					_finderPathCountBySamlIdpSsoSessionId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindBySamlIdpSsoSessionId,
					args);
			}
		}

		entityCache.putResult(
			SamlIdpSpSessionImpl.class, samlIdpSpSession.getPrimaryKey(),
			samlIdpSpSession, false);

		clearUniqueFindersCache(samlIdpSpSessionModelImpl, false);
		cacheUniqueFindersCache(samlIdpSpSessionModelImpl);

		samlIdpSpSession.resetOriginalValues();

		return samlIdpSpSession;
	}

	/**
	 * Returns the saml idp sp session with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the saml idp sp session
	 * @return the saml idp sp session
	 * @throws NoSuchIdpSpSessionException if a saml idp sp session with the primary key could not be found
	 */
	@Override
	public SamlIdpSpSession findByPrimaryKey(Serializable primaryKey)
		throws NoSuchIdpSpSessionException {

		SamlIdpSpSession samlIdpSpSession = fetchByPrimaryKey(primaryKey);

		if (samlIdpSpSession == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchIdpSpSessionException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return samlIdpSpSession;
	}

	/**
	 * Returns the saml idp sp session with the primary key or throws a <code>NoSuchIdpSpSessionException</code> if it could not be found.
	 *
	 * @param samlIdpSpSessionId the primary key of the saml idp sp session
	 * @return the saml idp sp session
	 * @throws NoSuchIdpSpSessionException if a saml idp sp session with the primary key could not be found
	 */
	@Override
	public SamlIdpSpSession findByPrimaryKey(long samlIdpSpSessionId)
		throws NoSuchIdpSpSessionException {

		return findByPrimaryKey((Serializable)samlIdpSpSessionId);
	}

	/**
	 * Returns the saml idp sp session with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param samlIdpSpSessionId the primary key of the saml idp sp session
	 * @return the saml idp sp session, or <code>null</code> if a saml idp sp session with the primary key could not be found
	 */
	@Override
	public SamlIdpSpSession fetchByPrimaryKey(long samlIdpSpSessionId) {
		return fetchByPrimaryKey((Serializable)samlIdpSpSessionId);
	}

	/**
	 * Returns all the saml idp sp sessions.
	 *
	 * @return the saml idp sp sessions
	 */
	@Override
	public List<SamlIdpSpSession> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the saml idp sp sessions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SamlIdpSpSessionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of saml idp sp sessions
	 * @param end the upper bound of the range of saml idp sp sessions (not inclusive)
	 * @return the range of saml idp sp sessions
	 */
	@Override
	public List<SamlIdpSpSession> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the saml idp sp sessions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SamlIdpSpSessionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of saml idp sp sessions
	 * @param end the upper bound of the range of saml idp sp sessions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of saml idp sp sessions
	 */
	@Override
	public List<SamlIdpSpSession> findAll(
		int start, int end,
		OrderByComparator<SamlIdpSpSession> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the saml idp sp sessions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SamlIdpSpSessionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of saml idp sp sessions
	 * @param end the upper bound of the range of saml idp sp sessions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of saml idp sp sessions
	 */
	@Override
	public List<SamlIdpSpSession> findAll(
		int start, int end,
		OrderByComparator<SamlIdpSpSession> orderByComparator,
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

		List<SamlIdpSpSession> list = null;

		if (useFinderCache) {
			list = (List<SamlIdpSpSession>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_SAMLIDPSPSESSION);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_SAMLIDPSPSESSION;

				sql = sql.concat(SamlIdpSpSessionModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<SamlIdpSpSession>)QueryUtil.list(
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
	 * Removes all the saml idp sp sessions from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (SamlIdpSpSession samlIdpSpSession : findAll()) {
			remove(samlIdpSpSession);
		}
	}

	/**
	 * Returns the number of saml idp sp sessions.
	 *
	 * @return the number of saml idp sp sessions
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_SAMLIDPSPSESSION);

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
		return "samlIdpSpSessionId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_SAMLIDPSPSESSION;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return SamlIdpSpSessionModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the saml idp sp session persistence.
	 */
	@Activate
	public void activate() {
		_finderPathWithPaginationFindAll = new FinderPath(
			SamlIdpSpSessionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			SamlIdpSpSessionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByCreateDate = new FinderPath(
			SamlIdpSpSessionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCreateDate",
			new String[] {
				Date.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithPaginationCountByCreateDate = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"countByCreateDate", new String[] {Date.class.getName()});

		_finderPathWithPaginationFindBySamlIdpSsoSessionId = new FinderPath(
			SamlIdpSpSessionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findBySamlIdpSsoSessionId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindBySamlIdpSsoSessionId = new FinderPath(
			SamlIdpSpSessionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findBySamlIdpSsoSessionId", new String[] {Long.class.getName()},
			SamlIdpSpSessionModelImpl.getColumnBitmask("samlIdpSsoSessionId"));

		_finderPathCountBySamlIdpSsoSessionId = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countBySamlIdpSsoSessionId", new String[] {Long.class.getName()});

		_finderPathFetchBySISSI_SSEI = new FinderPath(
			SamlIdpSpSessionImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchBySISSI_SSEI",
			new String[] {Long.class.getName(), String.class.getName()},
			SamlIdpSpSessionModelImpl.getColumnBitmask("samlIdpSsoSessionId") |
			SamlIdpSpSessionModelImpl.getColumnBitmask("samlSpEntityId"));

		_finderPathCountBySISSI_SSEI = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countBySISSI_SSEI",
			new String[] {Long.class.getName(), String.class.getName()});
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(SamlIdpSpSessionImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	@Reference(
		target = SamlPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = SamlPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = SamlPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private Long _getTime(Date date) {
		if (date == null) {
			return null;
		}

		return date.getTime();
	}

	private static final String _SQL_SELECT_SAMLIDPSPSESSION =
		"SELECT samlIdpSpSession FROM SamlIdpSpSession samlIdpSpSession";

	private static final String _SQL_SELECT_SAMLIDPSPSESSION_WHERE =
		"SELECT samlIdpSpSession FROM SamlIdpSpSession samlIdpSpSession WHERE ";

	private static final String _SQL_COUNT_SAMLIDPSPSESSION =
		"SELECT COUNT(samlIdpSpSession) FROM SamlIdpSpSession samlIdpSpSession";

	private static final String _SQL_COUNT_SAMLIDPSPSESSION_WHERE =
		"SELECT COUNT(samlIdpSpSession) FROM SamlIdpSpSession samlIdpSpSession WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "samlIdpSpSession.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No SamlIdpSpSession exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No SamlIdpSpSession exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		SamlIdpSpSessionPersistenceImpl.class);

	static {
		try {
			Class.forName(SamlPersistenceConstants.class.getName());
		}
		catch (ClassNotFoundException classNotFoundException) {
			throw new ExceptionInInitializerError(classNotFoundException);
		}
	}

}