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

package com.liferay.osb.koroneiki.scion.service.persistence.impl;

import com.liferay.osb.koroneiki.scion.exception.NoSuchAuthenticationTokenException;
import com.liferay.osb.koroneiki.scion.model.AuthenticationToken;
import com.liferay.osb.koroneiki.scion.model.impl.AuthenticationTokenImpl;
import com.liferay.osb.koroneiki.scion.model.impl.AuthenticationTokenModelImpl;
import com.liferay.osb.koroneiki.scion.service.persistence.AuthenticationTokenPersistence;
import com.liferay.osb.koroneiki.scion.service.persistence.impl.constants.KoroneikiPersistenceConstants;
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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.sql.DataSource;

import org.osgi.annotation.versioning.ProviderType;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the authentication token service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = AuthenticationTokenPersistence.class)
@ProviderType
public class AuthenticationTokenPersistenceImpl
	extends BasePersistenceImpl<AuthenticationToken>
	implements AuthenticationTokenPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>AuthenticationTokenUtil</code> to access the authentication token persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		AuthenticationTokenImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByServiceProducerId;
	private FinderPath _finderPathWithoutPaginationFindByServiceProducerId;
	private FinderPath _finderPathCountByServiceProducerId;

	/**
	 * Returns all the authentication tokens where serviceProducerId = &#63;.
	 *
	 * @param serviceProducerId the service producer ID
	 * @return the matching authentication tokens
	 */
	@Override
	public List<AuthenticationToken> findByServiceProducerId(
		long serviceProducerId) {

		return findByServiceProducerId(
			serviceProducerId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the authentication tokens where serviceProducerId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AuthenticationTokenModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param serviceProducerId the service producer ID
	 * @param start the lower bound of the range of authentication tokens
	 * @param end the upper bound of the range of authentication tokens (not inclusive)
	 * @return the range of matching authentication tokens
	 */
	@Override
	public List<AuthenticationToken> findByServiceProducerId(
		long serviceProducerId, int start, int end) {

		return findByServiceProducerId(serviceProducerId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the authentication tokens where serviceProducerId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AuthenticationTokenModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param serviceProducerId the service producer ID
	 * @param start the lower bound of the range of authentication tokens
	 * @param end the upper bound of the range of authentication tokens (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching authentication tokens
	 */
	@Override
	public List<AuthenticationToken> findByServiceProducerId(
		long serviceProducerId, int start, int end,
		OrderByComparator<AuthenticationToken> orderByComparator) {

		return findByServiceProducerId(
			serviceProducerId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the authentication tokens where serviceProducerId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AuthenticationTokenModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param serviceProducerId the service producer ID
	 * @param start the lower bound of the range of authentication tokens
	 * @param end the upper bound of the range of authentication tokens (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching authentication tokens
	 */
	@Override
	public List<AuthenticationToken> findByServiceProducerId(
		long serviceProducerId, int start, int end,
		OrderByComparator<AuthenticationToken> orderByComparator,
		boolean useFinderCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByServiceProducerId;
				finderArgs = new Object[] {serviceProducerId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByServiceProducerId;
			finderArgs = new Object[] {
				serviceProducerId, start, end, orderByComparator
			};
		}

		List<AuthenticationToken> list = null;

		if (useFinderCache) {
			list = (List<AuthenticationToken>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AuthenticationToken authenticationToken : list) {
					if ((serviceProducerId !=
							authenticationToken.getServiceProducerId())) {

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

			query.append(_SQL_SELECT_AUTHENTICATIONTOKEN_WHERE);

			query.append(_FINDER_COLUMN_SERVICEPRODUCERID_SERVICEPRODUCERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(AuthenticationTokenModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(serviceProducerId);

				if (!pagination) {
					list = (List<AuthenticationToken>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<AuthenticationToken>)QueryUtil.list(
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
	 * Returns the first authentication token in the ordered set where serviceProducerId = &#63;.
	 *
	 * @param serviceProducerId the service producer ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching authentication token
	 * @throws NoSuchAuthenticationTokenException if a matching authentication token could not be found
	 */
	@Override
	public AuthenticationToken findByServiceProducerId_First(
			long serviceProducerId,
			OrderByComparator<AuthenticationToken> orderByComparator)
		throws NoSuchAuthenticationTokenException {

		AuthenticationToken authenticationToken =
			fetchByServiceProducerId_First(
				serviceProducerId, orderByComparator);

		if (authenticationToken != null) {
			return authenticationToken;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("serviceProducerId=");
		msg.append(serviceProducerId);

		msg.append("}");

		throw new NoSuchAuthenticationTokenException(msg.toString());
	}

	/**
	 * Returns the first authentication token in the ordered set where serviceProducerId = &#63;.
	 *
	 * @param serviceProducerId the service producer ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching authentication token, or <code>null</code> if a matching authentication token could not be found
	 */
	@Override
	public AuthenticationToken fetchByServiceProducerId_First(
		long serviceProducerId,
		OrderByComparator<AuthenticationToken> orderByComparator) {

		List<AuthenticationToken> list = findByServiceProducerId(
			serviceProducerId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last authentication token in the ordered set where serviceProducerId = &#63;.
	 *
	 * @param serviceProducerId the service producer ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching authentication token
	 * @throws NoSuchAuthenticationTokenException if a matching authentication token could not be found
	 */
	@Override
	public AuthenticationToken findByServiceProducerId_Last(
			long serviceProducerId,
			OrderByComparator<AuthenticationToken> orderByComparator)
		throws NoSuchAuthenticationTokenException {

		AuthenticationToken authenticationToken = fetchByServiceProducerId_Last(
			serviceProducerId, orderByComparator);

		if (authenticationToken != null) {
			return authenticationToken;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("serviceProducerId=");
		msg.append(serviceProducerId);

		msg.append("}");

		throw new NoSuchAuthenticationTokenException(msg.toString());
	}

	/**
	 * Returns the last authentication token in the ordered set where serviceProducerId = &#63;.
	 *
	 * @param serviceProducerId the service producer ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching authentication token, or <code>null</code> if a matching authentication token could not be found
	 */
	@Override
	public AuthenticationToken fetchByServiceProducerId_Last(
		long serviceProducerId,
		OrderByComparator<AuthenticationToken> orderByComparator) {

		int count = countByServiceProducerId(serviceProducerId);

		if (count == 0) {
			return null;
		}

		List<AuthenticationToken> list = findByServiceProducerId(
			serviceProducerId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the authentication tokens before and after the current authentication token in the ordered set where serviceProducerId = &#63;.
	 *
	 * @param authenticationTokenId the primary key of the current authentication token
	 * @param serviceProducerId the service producer ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next authentication token
	 * @throws NoSuchAuthenticationTokenException if a authentication token with the primary key could not be found
	 */
	@Override
	public AuthenticationToken[] findByServiceProducerId_PrevAndNext(
			long authenticationTokenId, long serviceProducerId,
			OrderByComparator<AuthenticationToken> orderByComparator)
		throws NoSuchAuthenticationTokenException {

		AuthenticationToken authenticationToken = findByPrimaryKey(
			authenticationTokenId);

		Session session = null;

		try {
			session = openSession();

			AuthenticationToken[] array = new AuthenticationTokenImpl[3];

			array[0] = getByServiceProducerId_PrevAndNext(
				session, authenticationToken, serviceProducerId,
				orderByComparator, true);

			array[1] = authenticationToken;

			array[2] = getByServiceProducerId_PrevAndNext(
				session, authenticationToken, serviceProducerId,
				orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected AuthenticationToken getByServiceProducerId_PrevAndNext(
		Session session, AuthenticationToken authenticationToken,
		long serviceProducerId,
		OrderByComparator<AuthenticationToken> orderByComparator,
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

		query.append(_SQL_SELECT_AUTHENTICATIONTOKEN_WHERE);

		query.append(_FINDER_COLUMN_SERVICEPRODUCERID_SERVICEPRODUCERID_2);

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
			query.append(AuthenticationTokenModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(serviceProducerId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						authenticationToken)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<AuthenticationToken> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the authentication tokens that the user has permission to view where serviceProducerId = &#63;.
	 *
	 * @param serviceProducerId the service producer ID
	 * @return the matching authentication tokens that the user has permission to view
	 */
	@Override
	public List<AuthenticationToken> filterFindByServiceProducerId(
		long serviceProducerId) {

		return filterFindByServiceProducerId(
			serviceProducerId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the authentication tokens that the user has permission to view where serviceProducerId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AuthenticationTokenModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param serviceProducerId the service producer ID
	 * @param start the lower bound of the range of authentication tokens
	 * @param end the upper bound of the range of authentication tokens (not inclusive)
	 * @return the range of matching authentication tokens that the user has permission to view
	 */
	@Override
	public List<AuthenticationToken> filterFindByServiceProducerId(
		long serviceProducerId, int start, int end) {

		return filterFindByServiceProducerId(
			serviceProducerId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the authentication tokens that the user has permissions to view where serviceProducerId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AuthenticationTokenModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param serviceProducerId the service producer ID
	 * @param start the lower bound of the range of authentication tokens
	 * @param end the upper bound of the range of authentication tokens (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching authentication tokens that the user has permission to view
	 */
	@Override
	public List<AuthenticationToken> filterFindByServiceProducerId(
		long serviceProducerId, int start, int end,
		OrderByComparator<AuthenticationToken> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByServiceProducerId(
				serviceProducerId, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				3 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			query = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_AUTHENTICATIONTOKEN_WHERE);
		}
		else {
			query.append(
				_FILTER_SQL_SELECT_AUTHENTICATIONTOKEN_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_SERVICEPRODUCERID_SERVICEPRODUCERID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(
				_FILTER_SQL_SELECT_AUTHENTICATIONTOKEN_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(AuthenticationTokenModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(AuthenticationTokenModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), AuthenticationToken.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(
					_FILTER_ENTITY_ALIAS, AuthenticationTokenImpl.class);
			}
			else {
				q.addEntity(
					_FILTER_ENTITY_TABLE, AuthenticationTokenImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(serviceProducerId);

			return (List<AuthenticationToken>)QueryUtil.list(
				q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the authentication tokens before and after the current authentication token in the ordered set of authentication tokens that the user has permission to view where serviceProducerId = &#63;.
	 *
	 * @param authenticationTokenId the primary key of the current authentication token
	 * @param serviceProducerId the service producer ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next authentication token
	 * @throws NoSuchAuthenticationTokenException if a authentication token with the primary key could not be found
	 */
	@Override
	public AuthenticationToken[] filterFindByServiceProducerId_PrevAndNext(
			long authenticationTokenId, long serviceProducerId,
			OrderByComparator<AuthenticationToken> orderByComparator)
		throws NoSuchAuthenticationTokenException {

		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByServiceProducerId_PrevAndNext(
				authenticationTokenId, serviceProducerId, orderByComparator);
		}

		AuthenticationToken authenticationToken = findByPrimaryKey(
			authenticationTokenId);

		Session session = null;

		try {
			session = openSession();

			AuthenticationToken[] array = new AuthenticationTokenImpl[3];

			array[0] = filterGetByServiceProducerId_PrevAndNext(
				session, authenticationToken, serviceProducerId,
				orderByComparator, true);

			array[1] = authenticationToken;

			array[2] = filterGetByServiceProducerId_PrevAndNext(
				session, authenticationToken, serviceProducerId,
				orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected AuthenticationToken filterGetByServiceProducerId_PrevAndNext(
		Session session, AuthenticationToken authenticationToken,
		long serviceProducerId,
		OrderByComparator<AuthenticationToken> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_AUTHENTICATIONTOKEN_WHERE);
		}
		else {
			query.append(
				_FILTER_SQL_SELECT_AUTHENTICATIONTOKEN_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_SERVICEPRODUCERID_SERVICEPRODUCERID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(
				_FILTER_SQL_SELECT_AUTHENTICATIONTOKEN_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByConditionFields[i],
							true));
				}
				else {
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByConditionFields[i],
							true));
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByFields[i], true));
				}
				else {
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByFields[i], true));
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				query.append(AuthenticationTokenModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(AuthenticationTokenModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), AuthenticationToken.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, AuthenticationTokenImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, AuthenticationTokenImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(serviceProducerId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						authenticationToken)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<AuthenticationToken> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the authentication tokens where serviceProducerId = &#63; from the database.
	 *
	 * @param serviceProducerId the service producer ID
	 */
	@Override
	public void removeByServiceProducerId(long serviceProducerId) {
		for (AuthenticationToken authenticationToken :
				findByServiceProducerId(
					serviceProducerId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(authenticationToken);
		}
	}

	/**
	 * Returns the number of authentication tokens where serviceProducerId = &#63;.
	 *
	 * @param serviceProducerId the service producer ID
	 * @return the number of matching authentication tokens
	 */
	@Override
	public int countByServiceProducerId(long serviceProducerId) {
		FinderPath finderPath = _finderPathCountByServiceProducerId;

		Object[] finderArgs = new Object[] {serviceProducerId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_AUTHENTICATIONTOKEN_WHERE);

			query.append(_FINDER_COLUMN_SERVICEPRODUCERID_SERVICEPRODUCERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(serviceProducerId);

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

	/**
	 * Returns the number of authentication tokens that the user has permission to view where serviceProducerId = &#63;.
	 *
	 * @param serviceProducerId the service producer ID
	 * @return the number of matching authentication tokens that the user has permission to view
	 */
	@Override
	public int filterCountByServiceProducerId(long serviceProducerId) {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByServiceProducerId(serviceProducerId);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_AUTHENTICATIONTOKEN_WHERE);

		query.append(_FINDER_COLUMN_SERVICEPRODUCERID_SERVICEPRODUCERID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), AuthenticationToken.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(serviceProducerId);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String
		_FINDER_COLUMN_SERVICEPRODUCERID_SERVICEPRODUCERID_2 =
			"authenticationToken.serviceProducerId = ?";

	private FinderPath _finderPathFetchByD_S;
	private FinderPath _finderPathCountByD_S;

	/**
	 * Returns the authentication token where digest = &#63; and status = &#63; or throws a <code>NoSuchAuthenticationTokenException</code> if it could not be found.
	 *
	 * @param digest the digest
	 * @param status the status
	 * @return the matching authentication token
	 * @throws NoSuchAuthenticationTokenException if a matching authentication token could not be found
	 */
	@Override
	public AuthenticationToken findByD_S(String digest, int status)
		throws NoSuchAuthenticationTokenException {

		AuthenticationToken authenticationToken = fetchByD_S(digest, status);

		if (authenticationToken == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("digest=");
			msg.append(digest);

			msg.append(", status=");
			msg.append(status);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchAuthenticationTokenException(msg.toString());
		}

		return authenticationToken;
	}

	/**
	 * Returns the authentication token where digest = &#63; and status = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param digest the digest
	 * @param status the status
	 * @return the matching authentication token, or <code>null</code> if a matching authentication token could not be found
	 */
	@Override
	public AuthenticationToken fetchByD_S(String digest, int status) {
		return fetchByD_S(digest, status, true);
	}

	/**
	 * Returns the authentication token where digest = &#63; and status = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param digest the digest
	 * @param status the status
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching authentication token, or <code>null</code> if a matching authentication token could not be found
	 */
	@Override
	public AuthenticationToken fetchByD_S(
		String digest, int status, boolean useFinderCache) {

		digest = Objects.toString(digest, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {digest, status};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByD_S, finderArgs, this);
		}

		if (result instanceof AuthenticationToken) {
			AuthenticationToken authenticationToken =
				(AuthenticationToken)result;

			if (!Objects.equals(digest, authenticationToken.getDigest()) ||
				(status != authenticationToken.getStatus())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_AUTHENTICATIONTOKEN_WHERE);

			boolean bindDigest = false;

			if (digest.isEmpty()) {
				query.append(_FINDER_COLUMN_D_S_DIGEST_3);
			}
			else {
				bindDigest = true;

				query.append(_FINDER_COLUMN_D_S_DIGEST_2);
			}

			query.append(_FINDER_COLUMN_D_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindDigest) {
					qPos.add(digest);
				}

				qPos.add(status);

				List<AuthenticationToken> list = q.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByD_S, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {digest, status};
							}

							_log.warn(
								"AuthenticationTokenPersistenceImpl.fetchByD_S(String, int, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					AuthenticationToken authenticationToken = list.get(0);

					result = authenticationToken;

					cacheResult(authenticationToken);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(_finderPathFetchByD_S, finderArgs);
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
			return (AuthenticationToken)result;
		}
	}

	/**
	 * Removes the authentication token where digest = &#63; and status = &#63; from the database.
	 *
	 * @param digest the digest
	 * @param status the status
	 * @return the authentication token that was removed
	 */
	@Override
	public AuthenticationToken removeByD_S(String digest, int status)
		throws NoSuchAuthenticationTokenException {

		AuthenticationToken authenticationToken = findByD_S(digest, status);

		return remove(authenticationToken);
	}

	/**
	 * Returns the number of authentication tokens where digest = &#63; and status = &#63;.
	 *
	 * @param digest the digest
	 * @param status the status
	 * @return the number of matching authentication tokens
	 */
	@Override
	public int countByD_S(String digest, int status) {
		digest = Objects.toString(digest, "");

		FinderPath finderPath = _finderPathCountByD_S;

		Object[] finderArgs = new Object[] {digest, status};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_AUTHENTICATIONTOKEN_WHERE);

			boolean bindDigest = false;

			if (digest.isEmpty()) {
				query.append(_FINDER_COLUMN_D_S_DIGEST_3);
			}
			else {
				bindDigest = true;

				query.append(_FINDER_COLUMN_D_S_DIGEST_2);
			}

			query.append(_FINDER_COLUMN_D_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindDigest) {
					qPos.add(digest);
				}

				qPos.add(status);

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

	private static final String _FINDER_COLUMN_D_S_DIGEST_2 =
		"authenticationToken.digest = ? AND ";

	private static final String _FINDER_COLUMN_D_S_DIGEST_3 =
		"(authenticationToken.digest IS NULL OR authenticationToken.digest = '') AND ";

	private static final String _FINDER_COLUMN_D_S_STATUS_2 =
		"authenticationToken.status = ?";

	public AuthenticationTokenPersistenceImpl() {
		setModelClass(AuthenticationToken.class);

		setModelImplClass(AuthenticationTokenImpl.class);
		setModelPKClass(long.class);
	}

	/**
	 * Caches the authentication token in the entity cache if it is enabled.
	 *
	 * @param authenticationToken the authentication token
	 */
	@Override
	public void cacheResult(AuthenticationToken authenticationToken) {
		entityCache.putResult(
			entityCacheEnabled, AuthenticationTokenImpl.class,
			authenticationToken.getPrimaryKey(), authenticationToken);

		finderCache.putResult(
			_finderPathFetchByD_S,
			new Object[] {
				authenticationToken.getDigest(), authenticationToken.getStatus()
			},
			authenticationToken);

		authenticationToken.resetOriginalValues();
	}

	/**
	 * Caches the authentication tokens in the entity cache if it is enabled.
	 *
	 * @param authenticationTokens the authentication tokens
	 */
	@Override
	public void cacheResult(List<AuthenticationToken> authenticationTokens) {
		for (AuthenticationToken authenticationToken : authenticationTokens) {
			if (entityCache.getResult(
					entityCacheEnabled, AuthenticationTokenImpl.class,
					authenticationToken.getPrimaryKey()) == null) {

				cacheResult(authenticationToken);
			}
			else {
				authenticationToken.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all authentication tokens.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(AuthenticationTokenImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the authentication token.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(AuthenticationToken authenticationToken) {
		entityCache.removeResult(
			entityCacheEnabled, AuthenticationTokenImpl.class,
			authenticationToken.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(AuthenticationTokenModelImpl)authenticationToken, true);
	}

	@Override
	public void clearCache(List<AuthenticationToken> authenticationTokens) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (AuthenticationToken authenticationToken : authenticationTokens) {
			entityCache.removeResult(
				entityCacheEnabled, AuthenticationTokenImpl.class,
				authenticationToken.getPrimaryKey());

			clearUniqueFindersCache(
				(AuthenticationTokenModelImpl)authenticationToken, true);
		}
	}

	protected void cacheUniqueFindersCache(
		AuthenticationTokenModelImpl authenticationTokenModelImpl) {

		Object[] args = new Object[] {
			authenticationTokenModelImpl.getDigest(),
			authenticationTokenModelImpl.getStatus()
		};

		finderCache.putResult(
			_finderPathCountByD_S, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByD_S, args, authenticationTokenModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		AuthenticationTokenModelImpl authenticationTokenModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				authenticationTokenModelImpl.getDigest(),
				authenticationTokenModelImpl.getStatus()
			};

			finderCache.removeResult(_finderPathCountByD_S, args);
			finderCache.removeResult(_finderPathFetchByD_S, args);
		}

		if ((authenticationTokenModelImpl.getColumnBitmask() &
			 _finderPathFetchByD_S.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				authenticationTokenModelImpl.getOriginalDigest(),
				authenticationTokenModelImpl.getOriginalStatus()
			};

			finderCache.removeResult(_finderPathCountByD_S, args);
			finderCache.removeResult(_finderPathFetchByD_S, args);
		}
	}

	/**
	 * Creates a new authentication token with the primary key. Does not add the authentication token to the database.
	 *
	 * @param authenticationTokenId the primary key for the new authentication token
	 * @return the new authentication token
	 */
	@Override
	public AuthenticationToken create(long authenticationTokenId) {
		AuthenticationToken authenticationToken = new AuthenticationTokenImpl();

		authenticationToken.setNew(true);
		authenticationToken.setPrimaryKey(authenticationTokenId);

		authenticationToken.setCompanyId(CompanyThreadLocal.getCompanyId());

		return authenticationToken;
	}

	/**
	 * Removes the authentication token with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param authenticationTokenId the primary key of the authentication token
	 * @return the authentication token that was removed
	 * @throws NoSuchAuthenticationTokenException if a authentication token with the primary key could not be found
	 */
	@Override
	public AuthenticationToken remove(long authenticationTokenId)
		throws NoSuchAuthenticationTokenException {

		return remove((Serializable)authenticationTokenId);
	}

	/**
	 * Removes the authentication token with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the authentication token
	 * @return the authentication token that was removed
	 * @throws NoSuchAuthenticationTokenException if a authentication token with the primary key could not be found
	 */
	@Override
	public AuthenticationToken remove(Serializable primaryKey)
		throws NoSuchAuthenticationTokenException {

		Session session = null;

		try {
			session = openSession();

			AuthenticationToken authenticationToken =
				(AuthenticationToken)session.get(
					AuthenticationTokenImpl.class, primaryKey);

			if (authenticationToken == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchAuthenticationTokenException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(authenticationToken);
		}
		catch (NoSuchAuthenticationTokenException nsee) {
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
	protected AuthenticationToken removeImpl(
		AuthenticationToken authenticationToken) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(authenticationToken)) {
				authenticationToken = (AuthenticationToken)session.get(
					AuthenticationTokenImpl.class,
					authenticationToken.getPrimaryKeyObj());
			}

			if (authenticationToken != null) {
				session.delete(authenticationToken);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (authenticationToken != null) {
			clearCache(authenticationToken);
		}

		return authenticationToken;
	}

	@Override
	public AuthenticationToken updateImpl(
		AuthenticationToken authenticationToken) {

		boolean isNew = authenticationToken.isNew();

		if (!(authenticationToken instanceof AuthenticationTokenModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(authenticationToken.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					authenticationToken);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in authenticationToken proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom AuthenticationToken implementation " +
					authenticationToken.getClass());
		}

		AuthenticationTokenModelImpl authenticationTokenModelImpl =
			(AuthenticationTokenModelImpl)authenticationToken;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (authenticationToken.getCreateDate() == null)) {
			if (serviceContext == null) {
				authenticationToken.setCreateDate(now);
			}
			else {
				authenticationToken.setCreateDate(
					serviceContext.getCreateDate(now));
			}
		}

		if (!authenticationTokenModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				authenticationToken.setModifiedDate(now);
			}
			else {
				authenticationToken.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (authenticationToken.isNew()) {
				session.save(authenticationToken);

				authenticationToken.setNew(false);
			}
			else {
				authenticationToken = (AuthenticationToken)session.merge(
					authenticationToken);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!_columnBitmaskEnabled) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {
				authenticationTokenModelImpl.getServiceProducerId()
			};

			finderCache.removeResult(_finderPathCountByServiceProducerId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByServiceProducerId, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((authenticationTokenModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByServiceProducerId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					authenticationTokenModelImpl.getOriginalServiceProducerId()
				};

				finderCache.removeResult(
					_finderPathCountByServiceProducerId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByServiceProducerId, args);

				args = new Object[] {
					authenticationTokenModelImpl.getServiceProducerId()
				};

				finderCache.removeResult(
					_finderPathCountByServiceProducerId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByServiceProducerId, args);
			}
		}

		entityCache.putResult(
			entityCacheEnabled, AuthenticationTokenImpl.class,
			authenticationToken.getPrimaryKey(), authenticationToken, false);

		clearUniqueFindersCache(authenticationTokenModelImpl, false);
		cacheUniqueFindersCache(authenticationTokenModelImpl);

		authenticationToken.resetOriginalValues();

		return authenticationToken;
	}

	/**
	 * Returns the authentication token with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the authentication token
	 * @return the authentication token
	 * @throws NoSuchAuthenticationTokenException if a authentication token with the primary key could not be found
	 */
	@Override
	public AuthenticationToken findByPrimaryKey(Serializable primaryKey)
		throws NoSuchAuthenticationTokenException {

		AuthenticationToken authenticationToken = fetchByPrimaryKey(primaryKey);

		if (authenticationToken == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchAuthenticationTokenException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return authenticationToken;
	}

	/**
	 * Returns the authentication token with the primary key or throws a <code>NoSuchAuthenticationTokenException</code> if it could not be found.
	 *
	 * @param authenticationTokenId the primary key of the authentication token
	 * @return the authentication token
	 * @throws NoSuchAuthenticationTokenException if a authentication token with the primary key could not be found
	 */
	@Override
	public AuthenticationToken findByPrimaryKey(long authenticationTokenId)
		throws NoSuchAuthenticationTokenException {

		return findByPrimaryKey((Serializable)authenticationTokenId);
	}

	/**
	 * Returns the authentication token with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param authenticationTokenId the primary key of the authentication token
	 * @return the authentication token, or <code>null</code> if a authentication token with the primary key could not be found
	 */
	@Override
	public AuthenticationToken fetchByPrimaryKey(long authenticationTokenId) {
		return fetchByPrimaryKey((Serializable)authenticationTokenId);
	}

	/**
	 * Returns all the authentication tokens.
	 *
	 * @return the authentication tokens
	 */
	@Override
	public List<AuthenticationToken> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the authentication tokens.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AuthenticationTokenModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of authentication tokens
	 * @param end the upper bound of the range of authentication tokens (not inclusive)
	 * @return the range of authentication tokens
	 */
	@Override
	public List<AuthenticationToken> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the authentication tokens.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AuthenticationTokenModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of authentication tokens
	 * @param end the upper bound of the range of authentication tokens (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of authentication tokens
	 */
	@Override
	public List<AuthenticationToken> findAll(
		int start, int end,
		OrderByComparator<AuthenticationToken> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the authentication tokens.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AuthenticationTokenModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of authentication tokens
	 * @param end the upper bound of the range of authentication tokens (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of authentication tokens
	 */
	@Override
	public List<AuthenticationToken> findAll(
		int start, int end,
		OrderByComparator<AuthenticationToken> orderByComparator,
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

		List<AuthenticationToken> list = null;

		if (useFinderCache) {
			list = (List<AuthenticationToken>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_AUTHENTICATIONTOKEN);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_AUTHENTICATIONTOKEN;

				if (pagination) {
					sql = sql.concat(
						AuthenticationTokenModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<AuthenticationToken>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<AuthenticationToken>)QueryUtil.list(
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
	 * Removes all the authentication tokens from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (AuthenticationToken authenticationToken : findAll()) {
			remove(authenticationToken);
		}
	}

	/**
	 * Returns the number of authentication tokens.
	 *
	 * @return the number of authentication tokens
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_AUTHENTICATIONTOKEN);

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
		return "authenticationTokenId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_AUTHENTICATIONTOKEN;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return AuthenticationTokenModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the authentication token persistence.
	 */
	@Activate
	public void activate() {
		AuthenticationTokenModelImpl.setEntityCacheEnabled(entityCacheEnabled);
		AuthenticationTokenModelImpl.setFinderCacheEnabled(finderCacheEnabled);

		_finderPathWithPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			AuthenticationTokenImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			AuthenticationTokenImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByServiceProducerId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			AuthenticationTokenImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByServiceProducerId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByServiceProducerId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			AuthenticationTokenImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByServiceProducerId", new String[] {Long.class.getName()},
			AuthenticationTokenModelImpl.SERVICEPRODUCERID_COLUMN_BITMASK |
			AuthenticationTokenModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByServiceProducerId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByServiceProducerId", new String[] {Long.class.getName()});

		_finderPathFetchByD_S = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			AuthenticationTokenImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByD_S",
			new String[] {String.class.getName(), Integer.class.getName()},
			AuthenticationTokenModelImpl.DIGEST_COLUMN_BITMASK |
			AuthenticationTokenModelImpl.STATUS_COLUMN_BITMASK);

		_finderPathCountByD_S = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByD_S",
			new String[] {String.class.getName(), Integer.class.getName()});
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(AuthenticationTokenImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	@Reference(
		target = KoroneikiPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
		super.setConfiguration(configuration);

		_columnBitmaskEnabled = GetterUtil.getBoolean(
			configuration.get(
				"value.object.column.bitmask.enabled.com.liferay.osb.koroneiki.scion.model.AuthenticationToken"),
			true);
	}

	@Override
	@Reference(
		target = KoroneikiPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = KoroneikiPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	private boolean _columnBitmaskEnabled;

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_AUTHENTICATIONTOKEN =
		"SELECT authenticationToken FROM AuthenticationToken authenticationToken";

	private static final String _SQL_SELECT_AUTHENTICATIONTOKEN_WHERE =
		"SELECT authenticationToken FROM AuthenticationToken authenticationToken WHERE ";

	private static final String _SQL_COUNT_AUTHENTICATIONTOKEN =
		"SELECT COUNT(authenticationToken) FROM AuthenticationToken authenticationToken";

	private static final String _SQL_COUNT_AUTHENTICATIONTOKEN_WHERE =
		"SELECT COUNT(authenticationToken) FROM AuthenticationToken authenticationToken WHERE ";

	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN =
		"authenticationToken.authenticationTokenId";

	private static final String _FILTER_SQL_SELECT_AUTHENTICATIONTOKEN_WHERE =
		"SELECT DISTINCT {authenticationToken.*} FROM Koroneiki_AuthenticationToken authenticationToken WHERE ";

	private static final String
		_FILTER_SQL_SELECT_AUTHENTICATIONTOKEN_NO_INLINE_DISTINCT_WHERE_1 =
			"SELECT {Koroneiki_AuthenticationToken.*} FROM (SELECT DISTINCT authenticationToken.authenticationTokenId FROM Koroneiki_AuthenticationToken authenticationToken WHERE ";

	private static final String
		_FILTER_SQL_SELECT_AUTHENTICATIONTOKEN_NO_INLINE_DISTINCT_WHERE_2 =
			") TEMP_TABLE INNER JOIN Koroneiki_AuthenticationToken ON TEMP_TABLE.authenticationTokenId = Koroneiki_AuthenticationToken.authenticationTokenId";

	private static final String _FILTER_SQL_COUNT_AUTHENTICATIONTOKEN_WHERE =
		"SELECT COUNT(DISTINCT authenticationToken.authenticationTokenId) AS COUNT_VALUE FROM Koroneiki_AuthenticationToken authenticationToken WHERE ";

	private static final String _FILTER_ENTITY_ALIAS = "authenticationToken";

	private static final String _FILTER_ENTITY_TABLE =
		"Koroneiki_AuthenticationToken";

	private static final String _ORDER_BY_ENTITY_ALIAS = "authenticationToken.";

	private static final String _ORDER_BY_ENTITY_TABLE =
		"Koroneiki_AuthenticationToken.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No AuthenticationToken exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No AuthenticationToken exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		AuthenticationTokenPersistenceImpl.class);

	static {
		try {
			Class.forName(KoroneikiPersistenceConstants.class.getName());
		}
		catch (ClassNotFoundException cnfe) {
			throw new ExceptionInInitializerError(cnfe);
		}
	}

}