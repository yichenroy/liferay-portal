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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.CompanyProvider;
import com.liferay.portal.kernel.service.persistence.CompanyProviderWrapper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import com.liferay.saml.persistence.exception.NoSuchIdpSpConnectionException;
import com.liferay.saml.persistence.model.SamlIdpSpConnection;
import com.liferay.saml.persistence.model.impl.SamlIdpSpConnectionImpl;
import com.liferay.saml.persistence.model.impl.SamlIdpSpConnectionModelImpl;
import com.liferay.saml.persistence.service.persistence.SamlIdpSpConnectionPersistence;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the saml idp sp connection service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Mika Koivisto
 * @see SamlIdpSpConnectionPersistence
 * @see com.liferay.saml.persistence.service.persistence.SamlIdpSpConnectionUtil
 * @generated
 */
@ProviderType
public class SamlIdpSpConnectionPersistenceImpl extends BasePersistenceImpl<SamlIdpSpConnection>
	implements SamlIdpSpConnectionPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link SamlIdpSpConnectionUtil} to access the saml idp sp connection persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = SamlIdpSpConnectionImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(SamlIdpSpConnectionModelImpl.ENTITY_CACHE_ENABLED,
			SamlIdpSpConnectionModelImpl.FINDER_CACHE_ENABLED,
			SamlIdpSpConnectionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(SamlIdpSpConnectionModelImpl.ENTITY_CACHE_ENABLED,
			SamlIdpSpConnectionModelImpl.FINDER_CACHE_ENABLED,
			SamlIdpSpConnectionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(SamlIdpSpConnectionModelImpl.ENTITY_CACHE_ENABLED,
			SamlIdpSpConnectionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(SamlIdpSpConnectionModelImpl.ENTITY_CACHE_ENABLED,
			SamlIdpSpConnectionModelImpl.FINDER_CACHE_ENABLED,
			SamlIdpSpConnectionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(SamlIdpSpConnectionModelImpl.ENTITY_CACHE_ENABLED,
			SamlIdpSpConnectionModelImpl.FINDER_CACHE_ENABLED,
			SamlIdpSpConnectionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] { Long.class.getName() },
			SamlIdpSpConnectionModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(SamlIdpSpConnectionModelImpl.ENTITY_CACHE_ENABLED,
			SamlIdpSpConnectionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the saml idp sp connections where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching saml idp sp connections
	 */
	@Override
	public List<SamlIdpSpConnection> findByCompanyId(long companyId) {
		return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the saml idp sp connections where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SamlIdpSpConnectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of saml idp sp connections
	 * @param end the upper bound of the range of saml idp sp connections (not inclusive)
	 * @return the range of matching saml idp sp connections
	 */
	@Override
	public List<SamlIdpSpConnection> findByCompanyId(long companyId, int start,
		int end) {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the saml idp sp connections where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SamlIdpSpConnectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of saml idp sp connections
	 * @param end the upper bound of the range of saml idp sp connections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching saml idp sp connections
	 */
	@Override
	public List<SamlIdpSpConnection> findByCompanyId(long companyId, int start,
		int end, OrderByComparator<SamlIdpSpConnection> orderByComparator) {
		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the saml idp sp connections where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SamlIdpSpConnectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of saml idp sp connections
	 * @param end the upper bound of the range of saml idp sp connections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching saml idp sp connections
	 */
	@Override
	public List<SamlIdpSpConnection> findByCompanyId(long companyId, int start,
		int end, OrderByComparator<SamlIdpSpConnection> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID;
			finderArgs = new Object[] { companyId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID;
			finderArgs = new Object[] { companyId, start, end, orderByComparator };
		}

		List<SamlIdpSpConnection> list = null;

		if (retrieveFromCache) {
			list = (List<SamlIdpSpConnection>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SamlIdpSpConnection samlIdpSpConnection : list) {
					if ((companyId != samlIdpSpConnection.getCompanyId())) {
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

			query.append(_SQL_SELECT_SAMLIDPSPCONNECTION_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(SamlIdpSpConnectionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (!pagination) {
					list = (List<SamlIdpSpConnection>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SamlIdpSpConnection>)QueryUtil.list(q,
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
	 * Returns the first saml idp sp connection in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching saml idp sp connection
	 * @throws NoSuchIdpSpConnectionException if a matching saml idp sp connection could not be found
	 */
	@Override
	public SamlIdpSpConnection findByCompanyId_First(long companyId,
		OrderByComparator<SamlIdpSpConnection> orderByComparator)
		throws NoSuchIdpSpConnectionException {
		SamlIdpSpConnection samlIdpSpConnection = fetchByCompanyId_First(companyId,
				orderByComparator);

		if (samlIdpSpConnection != null) {
			return samlIdpSpConnection;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchIdpSpConnectionException(msg.toString());
	}

	/**
	 * Returns the first saml idp sp connection in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching saml idp sp connection, or <code>null</code> if a matching saml idp sp connection could not be found
	 */
	@Override
	public SamlIdpSpConnection fetchByCompanyId_First(long companyId,
		OrderByComparator<SamlIdpSpConnection> orderByComparator) {
		List<SamlIdpSpConnection> list = findByCompanyId(companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last saml idp sp connection in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching saml idp sp connection
	 * @throws NoSuchIdpSpConnectionException if a matching saml idp sp connection could not be found
	 */
	@Override
	public SamlIdpSpConnection findByCompanyId_Last(long companyId,
		OrderByComparator<SamlIdpSpConnection> orderByComparator)
		throws NoSuchIdpSpConnectionException {
		SamlIdpSpConnection samlIdpSpConnection = fetchByCompanyId_Last(companyId,
				orderByComparator);

		if (samlIdpSpConnection != null) {
			return samlIdpSpConnection;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchIdpSpConnectionException(msg.toString());
	}

	/**
	 * Returns the last saml idp sp connection in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching saml idp sp connection, or <code>null</code> if a matching saml idp sp connection could not be found
	 */
	@Override
	public SamlIdpSpConnection fetchByCompanyId_Last(long companyId,
		OrderByComparator<SamlIdpSpConnection> orderByComparator) {
		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<SamlIdpSpConnection> list = findByCompanyId(companyId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the saml idp sp connections before and after the current saml idp sp connection in the ordered set where companyId = &#63;.
	 *
	 * @param samlIdpSpConnectionId the primary key of the current saml idp sp connection
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next saml idp sp connection
	 * @throws NoSuchIdpSpConnectionException if a saml idp sp connection with the primary key could not be found
	 */
	@Override
	public SamlIdpSpConnection[] findByCompanyId_PrevAndNext(
		long samlIdpSpConnectionId, long companyId,
		OrderByComparator<SamlIdpSpConnection> orderByComparator)
		throws NoSuchIdpSpConnectionException {
		SamlIdpSpConnection samlIdpSpConnection = findByPrimaryKey(samlIdpSpConnectionId);

		Session session = null;

		try {
			session = openSession();

			SamlIdpSpConnection[] array = new SamlIdpSpConnectionImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session, samlIdpSpConnection,
					companyId, orderByComparator, true);

			array[1] = samlIdpSpConnection;

			array[2] = getByCompanyId_PrevAndNext(session, samlIdpSpConnection,
					companyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SamlIdpSpConnection getByCompanyId_PrevAndNext(Session session,
		SamlIdpSpConnection samlIdpSpConnection, long companyId,
		OrderByComparator<SamlIdpSpConnection> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SAMLIDPSPCONNECTION_WHERE);

		query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

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
			query.append(SamlIdpSpConnectionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(samlIdpSpConnection);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SamlIdpSpConnection> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the saml idp sp connections where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (SamlIdpSpConnection samlIdpSpConnection : findByCompanyId(
				companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(samlIdpSpConnection);
		}
	}

	/**
	 * Returns the number of saml idp sp connections where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching saml idp sp connections
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMPANYID;

		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SAMLIDPSPCONNECTION_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

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

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "samlIdpSpConnection.companyId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_C_SSEI = new FinderPath(SamlIdpSpConnectionModelImpl.ENTITY_CACHE_ENABLED,
			SamlIdpSpConnectionModelImpl.FINDER_CACHE_ENABLED,
			SamlIdpSpConnectionImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByC_SSEI",
			new String[] { Long.class.getName(), String.class.getName() },
			SamlIdpSpConnectionModelImpl.COMPANYID_COLUMN_BITMASK |
			SamlIdpSpConnectionModelImpl.SAMLSPENTITYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_SSEI = new FinderPath(SamlIdpSpConnectionModelImpl.ENTITY_CACHE_ENABLED,
			SamlIdpSpConnectionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_SSEI",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns the saml idp sp connection where companyId = &#63; and samlSpEntityId = &#63; or throws a {@link NoSuchIdpSpConnectionException} if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param samlSpEntityId the saml sp entity ID
	 * @return the matching saml idp sp connection
	 * @throws NoSuchIdpSpConnectionException if a matching saml idp sp connection could not be found
	 */
	@Override
	public SamlIdpSpConnection findByC_SSEI(long companyId,
		String samlSpEntityId) throws NoSuchIdpSpConnectionException {
		SamlIdpSpConnection samlIdpSpConnection = fetchByC_SSEI(companyId,
				samlSpEntityId);

		if (samlIdpSpConnection == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", samlSpEntityId=");
			msg.append(samlSpEntityId);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchIdpSpConnectionException(msg.toString());
		}

		return samlIdpSpConnection;
	}

	/**
	 * Returns the saml idp sp connection where companyId = &#63; and samlSpEntityId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param samlSpEntityId the saml sp entity ID
	 * @return the matching saml idp sp connection, or <code>null</code> if a matching saml idp sp connection could not be found
	 */
	@Override
	public SamlIdpSpConnection fetchByC_SSEI(long companyId,
		String samlSpEntityId) {
		return fetchByC_SSEI(companyId, samlSpEntityId, true);
	}

	/**
	 * Returns the saml idp sp connection where companyId = &#63; and samlSpEntityId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param samlSpEntityId the saml sp entity ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching saml idp sp connection, or <code>null</code> if a matching saml idp sp connection could not be found
	 */
	@Override
	public SamlIdpSpConnection fetchByC_SSEI(long companyId,
		String samlSpEntityId, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { companyId, samlSpEntityId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_C_SSEI,
					finderArgs, this);
		}

		if (result instanceof SamlIdpSpConnection) {
			SamlIdpSpConnection samlIdpSpConnection = (SamlIdpSpConnection)result;

			if ((companyId != samlIdpSpConnection.getCompanyId()) ||
					!Objects.equals(samlSpEntityId,
						samlIdpSpConnection.getSamlSpEntityId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_SAMLIDPSPCONNECTION_WHERE);

			query.append(_FINDER_COLUMN_C_SSEI_COMPANYID_2);

			boolean bindSamlSpEntityId = false;

			if (samlSpEntityId == null) {
				query.append(_FINDER_COLUMN_C_SSEI_SAMLSPENTITYID_1);
			}
			else if (samlSpEntityId.equals("")) {
				query.append(_FINDER_COLUMN_C_SSEI_SAMLSPENTITYID_3);
			}
			else {
				bindSamlSpEntityId = true;

				query.append(_FINDER_COLUMN_C_SSEI_SAMLSPENTITYID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (bindSamlSpEntityId) {
					qPos.add(samlSpEntityId);
				}

				List<SamlIdpSpConnection> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_C_SSEI,
						finderArgs, list);
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							_log.warn(
								"SamlIdpSpConnectionPersistenceImpl.fetchByC_SSEI(long, String, boolean) with parameters (" +
								StringUtil.merge(finderArgs) +
								") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					SamlIdpSpConnection samlIdpSpConnection = list.get(0);

					result = samlIdpSpConnection;

					cacheResult(samlIdpSpConnection);
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_C_SSEI, finderArgs);

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
			return (SamlIdpSpConnection)result;
		}
	}

	/**
	 * Removes the saml idp sp connection where companyId = &#63; and samlSpEntityId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param samlSpEntityId the saml sp entity ID
	 * @return the saml idp sp connection that was removed
	 */
	@Override
	public SamlIdpSpConnection removeByC_SSEI(long companyId,
		String samlSpEntityId) throws NoSuchIdpSpConnectionException {
		SamlIdpSpConnection samlIdpSpConnection = findByC_SSEI(companyId,
				samlSpEntityId);

		return remove(samlIdpSpConnection);
	}

	/**
	 * Returns the number of saml idp sp connections where companyId = &#63; and samlSpEntityId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param samlSpEntityId the saml sp entity ID
	 * @return the number of matching saml idp sp connections
	 */
	@Override
	public int countByC_SSEI(long companyId, String samlSpEntityId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_SSEI;

		Object[] finderArgs = new Object[] { companyId, samlSpEntityId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SAMLIDPSPCONNECTION_WHERE);

			query.append(_FINDER_COLUMN_C_SSEI_COMPANYID_2);

			boolean bindSamlSpEntityId = false;

			if (samlSpEntityId == null) {
				query.append(_FINDER_COLUMN_C_SSEI_SAMLSPENTITYID_1);
			}
			else if (samlSpEntityId.equals("")) {
				query.append(_FINDER_COLUMN_C_SSEI_SAMLSPENTITYID_3);
			}
			else {
				bindSamlSpEntityId = true;

				query.append(_FINDER_COLUMN_C_SSEI_SAMLSPENTITYID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (bindSamlSpEntityId) {
					qPos.add(samlSpEntityId);
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

	private static final String _FINDER_COLUMN_C_SSEI_COMPANYID_2 = "samlIdpSpConnection.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_SSEI_SAMLSPENTITYID_1 = "samlIdpSpConnection.samlSpEntityId IS NULL";
	private static final String _FINDER_COLUMN_C_SSEI_SAMLSPENTITYID_2 = "samlIdpSpConnection.samlSpEntityId = ?";
	private static final String _FINDER_COLUMN_C_SSEI_SAMLSPENTITYID_3 = "(samlIdpSpConnection.samlSpEntityId IS NULL OR samlIdpSpConnection.samlSpEntityId = '')";

	public SamlIdpSpConnectionPersistenceImpl() {
		setModelClass(SamlIdpSpConnection.class);
	}

	/**
	 * Caches the saml idp sp connection in the entity cache if it is enabled.
	 *
	 * @param samlIdpSpConnection the saml idp sp connection
	 */
	@Override
	public void cacheResult(SamlIdpSpConnection samlIdpSpConnection) {
		entityCache.putResult(SamlIdpSpConnectionModelImpl.ENTITY_CACHE_ENABLED,
			SamlIdpSpConnectionImpl.class, samlIdpSpConnection.getPrimaryKey(),
			samlIdpSpConnection);

		finderCache.putResult(FINDER_PATH_FETCH_BY_C_SSEI,
			new Object[] {
				samlIdpSpConnection.getCompanyId(),
				samlIdpSpConnection.getSamlSpEntityId()
			}, samlIdpSpConnection);

		samlIdpSpConnection.resetOriginalValues();
	}

	/**
	 * Caches the saml idp sp connections in the entity cache if it is enabled.
	 *
	 * @param samlIdpSpConnections the saml idp sp connections
	 */
	@Override
	public void cacheResult(List<SamlIdpSpConnection> samlIdpSpConnections) {
		for (SamlIdpSpConnection samlIdpSpConnection : samlIdpSpConnections) {
			if (entityCache.getResult(
						SamlIdpSpConnectionModelImpl.ENTITY_CACHE_ENABLED,
						SamlIdpSpConnectionImpl.class,
						samlIdpSpConnection.getPrimaryKey()) == null) {
				cacheResult(samlIdpSpConnection);
			}
			else {
				samlIdpSpConnection.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all saml idp sp connections.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(SamlIdpSpConnectionImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the saml idp sp connection.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(SamlIdpSpConnection samlIdpSpConnection) {
		entityCache.removeResult(SamlIdpSpConnectionModelImpl.ENTITY_CACHE_ENABLED,
			SamlIdpSpConnectionImpl.class, samlIdpSpConnection.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((SamlIdpSpConnectionModelImpl)samlIdpSpConnection,
			true);
	}

	@Override
	public void clearCache(List<SamlIdpSpConnection> samlIdpSpConnections) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (SamlIdpSpConnection samlIdpSpConnection : samlIdpSpConnections) {
			entityCache.removeResult(SamlIdpSpConnectionModelImpl.ENTITY_CACHE_ENABLED,
				SamlIdpSpConnectionImpl.class,
				samlIdpSpConnection.getPrimaryKey());

			clearUniqueFindersCache((SamlIdpSpConnectionModelImpl)samlIdpSpConnection,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		SamlIdpSpConnectionModelImpl samlIdpSpConnectionModelImpl) {
		Object[] args = new Object[] {
				samlIdpSpConnectionModelImpl.getCompanyId(),
				samlIdpSpConnectionModelImpl.getSamlSpEntityId()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_C_SSEI, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_C_SSEI, args,
			samlIdpSpConnectionModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		SamlIdpSpConnectionModelImpl samlIdpSpConnectionModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					samlIdpSpConnectionModelImpl.getCompanyId(),
					samlIdpSpConnectionModelImpl.getSamlSpEntityId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_SSEI, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_C_SSEI, args);
		}

		if ((samlIdpSpConnectionModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_C_SSEI.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					samlIdpSpConnectionModelImpl.getOriginalCompanyId(),
					samlIdpSpConnectionModelImpl.getOriginalSamlSpEntityId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_SSEI, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_C_SSEI, args);
		}
	}

	/**
	 * Creates a new saml idp sp connection with the primary key. Does not add the saml idp sp connection to the database.
	 *
	 * @param samlIdpSpConnectionId the primary key for the new saml idp sp connection
	 * @return the new saml idp sp connection
	 */
	@Override
	public SamlIdpSpConnection create(long samlIdpSpConnectionId) {
		SamlIdpSpConnection samlIdpSpConnection = new SamlIdpSpConnectionImpl();

		samlIdpSpConnection.setNew(true);
		samlIdpSpConnection.setPrimaryKey(samlIdpSpConnectionId);

		samlIdpSpConnection.setCompanyId(companyProvider.getCompanyId());

		return samlIdpSpConnection;
	}

	/**
	 * Removes the saml idp sp connection with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param samlIdpSpConnectionId the primary key of the saml idp sp connection
	 * @return the saml idp sp connection that was removed
	 * @throws NoSuchIdpSpConnectionException if a saml idp sp connection with the primary key could not be found
	 */
	@Override
	public SamlIdpSpConnection remove(long samlIdpSpConnectionId)
		throws NoSuchIdpSpConnectionException {
		return remove((Serializable)samlIdpSpConnectionId);
	}

	/**
	 * Removes the saml idp sp connection with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the saml idp sp connection
	 * @return the saml idp sp connection that was removed
	 * @throws NoSuchIdpSpConnectionException if a saml idp sp connection with the primary key could not be found
	 */
	@Override
	public SamlIdpSpConnection remove(Serializable primaryKey)
		throws NoSuchIdpSpConnectionException {
		Session session = null;

		try {
			session = openSession();

			SamlIdpSpConnection samlIdpSpConnection = (SamlIdpSpConnection)session.get(SamlIdpSpConnectionImpl.class,
					primaryKey);

			if (samlIdpSpConnection == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchIdpSpConnectionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(samlIdpSpConnection);
		}
		catch (NoSuchIdpSpConnectionException nsee) {
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
	protected SamlIdpSpConnection removeImpl(
		SamlIdpSpConnection samlIdpSpConnection) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(samlIdpSpConnection)) {
				samlIdpSpConnection = (SamlIdpSpConnection)session.get(SamlIdpSpConnectionImpl.class,
						samlIdpSpConnection.getPrimaryKeyObj());
			}

			if (samlIdpSpConnection != null) {
				session.delete(samlIdpSpConnection);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (samlIdpSpConnection != null) {
			clearCache(samlIdpSpConnection);
		}

		return samlIdpSpConnection;
	}

	@Override
	public SamlIdpSpConnection updateImpl(
		SamlIdpSpConnection samlIdpSpConnection) {
		boolean isNew = samlIdpSpConnection.isNew();

		if (!(samlIdpSpConnection instanceof SamlIdpSpConnectionModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(samlIdpSpConnection.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(samlIdpSpConnection);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in samlIdpSpConnection proxy " +
					invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom SamlIdpSpConnection implementation " +
				samlIdpSpConnection.getClass());
		}

		SamlIdpSpConnectionModelImpl samlIdpSpConnectionModelImpl = (SamlIdpSpConnectionModelImpl)samlIdpSpConnection;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (samlIdpSpConnection.getCreateDate() == null)) {
			if (serviceContext == null) {
				samlIdpSpConnection.setCreateDate(now);
			}
			else {
				samlIdpSpConnection.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!samlIdpSpConnectionModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				samlIdpSpConnection.setModifiedDate(now);
			}
			else {
				samlIdpSpConnection.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (samlIdpSpConnection.isNew()) {
				session.save(samlIdpSpConnection);

				samlIdpSpConnection.setNew(false);
			}
			else {
				samlIdpSpConnection = (SamlIdpSpConnection)session.merge(samlIdpSpConnection);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!SamlIdpSpConnectionModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					samlIdpSpConnectionModelImpl.getCompanyId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((samlIdpSpConnectionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						samlIdpSpConnectionModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);

				args = new Object[] { samlIdpSpConnectionModelImpl.getCompanyId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);
			}
		}

		entityCache.putResult(SamlIdpSpConnectionModelImpl.ENTITY_CACHE_ENABLED,
			SamlIdpSpConnectionImpl.class, samlIdpSpConnection.getPrimaryKey(),
			samlIdpSpConnection, false);

		clearUniqueFindersCache(samlIdpSpConnectionModelImpl, false);
		cacheUniqueFindersCache(samlIdpSpConnectionModelImpl);

		samlIdpSpConnection.resetOriginalValues();

		return samlIdpSpConnection;
	}

	/**
	 * Returns the saml idp sp connection with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the saml idp sp connection
	 * @return the saml idp sp connection
	 * @throws NoSuchIdpSpConnectionException if a saml idp sp connection with the primary key could not be found
	 */
	@Override
	public SamlIdpSpConnection findByPrimaryKey(Serializable primaryKey)
		throws NoSuchIdpSpConnectionException {
		SamlIdpSpConnection samlIdpSpConnection = fetchByPrimaryKey(primaryKey);

		if (samlIdpSpConnection == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchIdpSpConnectionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return samlIdpSpConnection;
	}

	/**
	 * Returns the saml idp sp connection with the primary key or throws a {@link NoSuchIdpSpConnectionException} if it could not be found.
	 *
	 * @param samlIdpSpConnectionId the primary key of the saml idp sp connection
	 * @return the saml idp sp connection
	 * @throws NoSuchIdpSpConnectionException if a saml idp sp connection with the primary key could not be found
	 */
	@Override
	public SamlIdpSpConnection findByPrimaryKey(long samlIdpSpConnectionId)
		throws NoSuchIdpSpConnectionException {
		return findByPrimaryKey((Serializable)samlIdpSpConnectionId);
	}

	/**
	 * Returns the saml idp sp connection with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the saml idp sp connection
	 * @return the saml idp sp connection, or <code>null</code> if a saml idp sp connection with the primary key could not be found
	 */
	@Override
	public SamlIdpSpConnection fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(SamlIdpSpConnectionModelImpl.ENTITY_CACHE_ENABLED,
				SamlIdpSpConnectionImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		SamlIdpSpConnection samlIdpSpConnection = (SamlIdpSpConnection)serializable;

		if (samlIdpSpConnection == null) {
			Session session = null;

			try {
				session = openSession();

				samlIdpSpConnection = (SamlIdpSpConnection)session.get(SamlIdpSpConnectionImpl.class,
						primaryKey);

				if (samlIdpSpConnection != null) {
					cacheResult(samlIdpSpConnection);
				}
				else {
					entityCache.putResult(SamlIdpSpConnectionModelImpl.ENTITY_CACHE_ENABLED,
						SamlIdpSpConnectionImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(SamlIdpSpConnectionModelImpl.ENTITY_CACHE_ENABLED,
					SamlIdpSpConnectionImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return samlIdpSpConnection;
	}

	/**
	 * Returns the saml idp sp connection with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param samlIdpSpConnectionId the primary key of the saml idp sp connection
	 * @return the saml idp sp connection, or <code>null</code> if a saml idp sp connection with the primary key could not be found
	 */
	@Override
	public SamlIdpSpConnection fetchByPrimaryKey(long samlIdpSpConnectionId) {
		return fetchByPrimaryKey((Serializable)samlIdpSpConnectionId);
	}

	@Override
	public Map<Serializable, SamlIdpSpConnection> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, SamlIdpSpConnection> map = new HashMap<Serializable, SamlIdpSpConnection>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			SamlIdpSpConnection samlIdpSpConnection = fetchByPrimaryKey(primaryKey);

			if (samlIdpSpConnection != null) {
				map.put(primaryKey, samlIdpSpConnection);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(SamlIdpSpConnectionModelImpl.ENTITY_CACHE_ENABLED,
					SamlIdpSpConnectionImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (SamlIdpSpConnection)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_SAMLIDPSPCONNECTION_WHERE_PKS_IN);

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

			for (SamlIdpSpConnection samlIdpSpConnection : (List<SamlIdpSpConnection>)q.list()) {
				map.put(samlIdpSpConnection.getPrimaryKeyObj(),
					samlIdpSpConnection);

				cacheResult(samlIdpSpConnection);

				uncachedPrimaryKeys.remove(samlIdpSpConnection.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(SamlIdpSpConnectionModelImpl.ENTITY_CACHE_ENABLED,
					SamlIdpSpConnectionImpl.class, primaryKey, nullModel);
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
	 * Returns all the saml idp sp connections.
	 *
	 * @return the saml idp sp connections
	 */
	@Override
	public List<SamlIdpSpConnection> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the saml idp sp connections.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SamlIdpSpConnectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of saml idp sp connections
	 * @param end the upper bound of the range of saml idp sp connections (not inclusive)
	 * @return the range of saml idp sp connections
	 */
	@Override
	public List<SamlIdpSpConnection> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the saml idp sp connections.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SamlIdpSpConnectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of saml idp sp connections
	 * @param end the upper bound of the range of saml idp sp connections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of saml idp sp connections
	 */
	@Override
	public List<SamlIdpSpConnection> findAll(int start, int end,
		OrderByComparator<SamlIdpSpConnection> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the saml idp sp connections.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SamlIdpSpConnectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of saml idp sp connections
	 * @param end the upper bound of the range of saml idp sp connections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of saml idp sp connections
	 */
	@Override
	public List<SamlIdpSpConnection> findAll(int start, int end,
		OrderByComparator<SamlIdpSpConnection> orderByComparator,
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

		List<SamlIdpSpConnection> list = null;

		if (retrieveFromCache) {
			list = (List<SamlIdpSpConnection>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_SAMLIDPSPCONNECTION);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_SAMLIDPSPCONNECTION;

				if (pagination) {
					sql = sql.concat(SamlIdpSpConnectionModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<SamlIdpSpConnection>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SamlIdpSpConnection>)QueryUtil.list(q,
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
	 * Removes all the saml idp sp connections from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (SamlIdpSpConnection samlIdpSpConnection : findAll()) {
			remove(samlIdpSpConnection);
		}
	}

	/**
	 * Returns the number of saml idp sp connections.
	 *
	 * @return the number of saml idp sp connections
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_SAMLIDPSPCONNECTION);

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
	protected Map<String, Integer> getTableColumnsMap() {
		return SamlIdpSpConnectionModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the saml idp sp connection persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(SamlIdpSpConnectionImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = CompanyProviderWrapper.class)
	protected CompanyProvider companyProvider;
	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;
	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;
	private static final String _SQL_SELECT_SAMLIDPSPCONNECTION = "SELECT samlIdpSpConnection FROM SamlIdpSpConnection samlIdpSpConnection";
	private static final String _SQL_SELECT_SAMLIDPSPCONNECTION_WHERE_PKS_IN = "SELECT samlIdpSpConnection FROM SamlIdpSpConnection samlIdpSpConnection WHERE samlIdpSpConnectionId IN (";
	private static final String _SQL_SELECT_SAMLIDPSPCONNECTION_WHERE = "SELECT samlIdpSpConnection FROM SamlIdpSpConnection samlIdpSpConnection WHERE ";
	private static final String _SQL_COUNT_SAMLIDPSPCONNECTION = "SELECT COUNT(samlIdpSpConnection) FROM SamlIdpSpConnection samlIdpSpConnection";
	private static final String _SQL_COUNT_SAMLIDPSPCONNECTION_WHERE = "SELECT COUNT(samlIdpSpConnection) FROM SamlIdpSpConnection samlIdpSpConnection WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "samlIdpSpConnection.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No SamlIdpSpConnection exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No SamlIdpSpConnection exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(SamlIdpSpConnectionPersistenceImpl.class);
}