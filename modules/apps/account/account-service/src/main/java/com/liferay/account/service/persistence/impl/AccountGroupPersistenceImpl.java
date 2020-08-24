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

package com.liferay.account.service.persistence.impl;

import com.liferay.account.exception.NoSuchGroupException;
import com.liferay.account.model.AccountGroup;
import com.liferay.account.model.AccountGroupTable;
import com.liferay.account.model.impl.AccountGroupImpl;
import com.liferay.account.model.impl.AccountGroupModelImpl;
import com.liferay.account.service.persistence.AccountGroupPersistence;
import com.liferay.account.service.persistence.impl.constants.AccountPersistenceConstants;
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

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

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
 * The persistence implementation for the account group service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = AccountGroupPersistence.class)
public class AccountGroupPersistenceImpl
	extends BasePersistenceImpl<AccountGroup>
	implements AccountGroupPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>AccountGroupUtil</code> to access the account group persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		AccountGroupImpl.class.getName();

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
	 * Returns all the account groups where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching account groups
	 */
	@Override
	public List<AccountGroup> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the account groups where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of account groups
	 * @param end the upper bound of the range of account groups (not inclusive)
	 * @return the range of matching account groups
	 */
	@Override
	public List<AccountGroup> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the account groups where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of account groups
	 * @param end the upper bound of the range of account groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching account groups
	 */
	@Override
	public List<AccountGroup> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<AccountGroup> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the account groups where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of account groups
	 * @param end the upper bound of the range of account groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching account groups
	 */
	@Override
	public List<AccountGroup> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<AccountGroup> orderByComparator,
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

		List<AccountGroup> list = null;

		if (useFinderCache) {
			list = (List<AccountGroup>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AccountGroup accountGroup : list) {
					if (companyId != accountGroup.getCompanyId()) {
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

			sb.append(_SQL_SELECT_ACCOUNTGROUP_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AccountGroupModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				list = (List<AccountGroup>)QueryUtil.list(
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
	 * Returns the first account group in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching account group
	 * @throws NoSuchGroupException if a matching account group could not be found
	 */
	@Override
	public AccountGroup findByCompanyId_First(
			long companyId, OrderByComparator<AccountGroup> orderByComparator)
		throws NoSuchGroupException {

		AccountGroup accountGroup = fetchByCompanyId_First(
			companyId, orderByComparator);

		if (accountGroup != null) {
			return accountGroup;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchGroupException(sb.toString());
	}

	/**
	 * Returns the first account group in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching account group, or <code>null</code> if a matching account group could not be found
	 */
	@Override
	public AccountGroup fetchByCompanyId_First(
		long companyId, OrderByComparator<AccountGroup> orderByComparator) {

		List<AccountGroup> list = findByCompanyId(
			companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last account group in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching account group
	 * @throws NoSuchGroupException if a matching account group could not be found
	 */
	@Override
	public AccountGroup findByCompanyId_Last(
			long companyId, OrderByComparator<AccountGroup> orderByComparator)
		throws NoSuchGroupException {

		AccountGroup accountGroup = fetchByCompanyId_Last(
			companyId, orderByComparator);

		if (accountGroup != null) {
			return accountGroup;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchGroupException(sb.toString());
	}

	/**
	 * Returns the last account group in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching account group, or <code>null</code> if a matching account group could not be found
	 */
	@Override
	public AccountGroup fetchByCompanyId_Last(
		long companyId, OrderByComparator<AccountGroup> orderByComparator) {

		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<AccountGroup> list = findByCompanyId(
			companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the account groups before and after the current account group in the ordered set where companyId = &#63;.
	 *
	 * @param accountGroupId the primary key of the current account group
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next account group
	 * @throws NoSuchGroupException if a account group with the primary key could not be found
	 */
	@Override
	public AccountGroup[] findByCompanyId_PrevAndNext(
			long accountGroupId, long companyId,
			OrderByComparator<AccountGroup> orderByComparator)
		throws NoSuchGroupException {

		AccountGroup accountGroup = findByPrimaryKey(accountGroupId);

		Session session = null;

		try {
			session = openSession();

			AccountGroup[] array = new AccountGroupImpl[3];

			array[0] = getByCompanyId_PrevAndNext(
				session, accountGroup, companyId, orderByComparator, true);

			array[1] = accountGroup;

			array[2] = getByCompanyId_PrevAndNext(
				session, accountGroup, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected AccountGroup getByCompanyId_PrevAndNext(
		Session session, AccountGroup accountGroup, long companyId,
		OrderByComparator<AccountGroup> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_ACCOUNTGROUP_WHERE);

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
			sb.append(AccountGroupModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(accountGroup)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AccountGroup> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the account groups where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (AccountGroup accountGroup :
				findByCompanyId(
					companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(accountGroup);
		}
	}

	/**
	 * Returns the number of account groups where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching account groups
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = _finderPathCountByCompanyId;

		Object[] finderArgs = new Object[] {companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_ACCOUNTGROUP_WHERE);

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

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 =
		"accountGroup.companyId = ?";

	private FinderPath _finderPathFetchByC_ERC;
	private FinderPath _finderPathCountByC_ERC;

	/**
	 * Returns the account group where companyId = &#63; and externalReferenceCode = &#63; or throws a <code>NoSuchGroupException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching account group
	 * @throws NoSuchGroupException if a matching account group could not be found
	 */
	@Override
	public AccountGroup findByC_ERC(
			long companyId, String externalReferenceCode)
		throws NoSuchGroupException {

		AccountGroup accountGroup = fetchByC_ERC(
			companyId, externalReferenceCode);

		if (accountGroup == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("companyId=");
			sb.append(companyId);

			sb.append(", externalReferenceCode=");
			sb.append(externalReferenceCode);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchGroupException(sb.toString());
		}

		return accountGroup;
	}

	/**
	 * Returns the account group where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching account group, or <code>null</code> if a matching account group could not be found
	 */
	@Override
	public AccountGroup fetchByC_ERC(
		long companyId, String externalReferenceCode) {

		return fetchByC_ERC(companyId, externalReferenceCode, true);
	}

	/**
	 * Returns the account group where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching account group, or <code>null</code> if a matching account group could not be found
	 */
	@Override
	public AccountGroup fetchByC_ERC(
		long companyId, String externalReferenceCode, boolean useFinderCache) {

		externalReferenceCode = Objects.toString(externalReferenceCode, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {companyId, externalReferenceCode};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByC_ERC, finderArgs, this);
		}

		if (result instanceof AccountGroup) {
			AccountGroup accountGroup = (AccountGroup)result;

			if ((companyId != accountGroup.getCompanyId()) ||
				!Objects.equals(
					externalReferenceCode,
					accountGroup.getExternalReferenceCode())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_ACCOUNTGROUP_WHERE);

			sb.append(_FINDER_COLUMN_C_ERC_COMPANYID_2);

			boolean bindExternalReferenceCode = false;

			if (externalReferenceCode.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_ERC_EXTERNALREFERENCECODE_3);
			}
			else {
				bindExternalReferenceCode = true;

				sb.append(_FINDER_COLUMN_C_ERC_EXTERNALREFERENCECODE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindExternalReferenceCode) {
					queryPos.add(externalReferenceCode);
				}

				List<AccountGroup> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByC_ERC, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {
									companyId, externalReferenceCode
								};
							}

							_log.warn(
								"AccountGroupPersistenceImpl.fetchByC_ERC(long, String, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					AccountGroup accountGroup = list.get(0);

					result = accountGroup;

					cacheResult(accountGroup);
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
			return (AccountGroup)result;
		}
	}

	/**
	 * Removes the account group where companyId = &#63; and externalReferenceCode = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the account group that was removed
	 */
	@Override
	public AccountGroup removeByC_ERC(
			long companyId, String externalReferenceCode)
		throws NoSuchGroupException {

		AccountGroup accountGroup = findByC_ERC(
			companyId, externalReferenceCode);

		return remove(accountGroup);
	}

	/**
	 * Returns the number of account groups where companyId = &#63; and externalReferenceCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the number of matching account groups
	 */
	@Override
	public int countByC_ERC(long companyId, String externalReferenceCode) {
		externalReferenceCode = Objects.toString(externalReferenceCode, "");

		FinderPath finderPath = _finderPathCountByC_ERC;

		Object[] finderArgs = new Object[] {companyId, externalReferenceCode};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_ACCOUNTGROUP_WHERE);

			sb.append(_FINDER_COLUMN_C_ERC_COMPANYID_2);

			boolean bindExternalReferenceCode = false;

			if (externalReferenceCode.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_ERC_EXTERNALREFERENCECODE_3);
			}
			else {
				bindExternalReferenceCode = true;

				sb.append(_FINDER_COLUMN_C_ERC_EXTERNALREFERENCECODE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindExternalReferenceCode) {
					queryPos.add(externalReferenceCode);
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

	private static final String _FINDER_COLUMN_C_ERC_COMPANYID_2 =
		"accountGroup.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_ERC_EXTERNALREFERENCECODE_2 =
		"accountGroup.externalReferenceCode = ?";

	private static final String _FINDER_COLUMN_C_ERC_EXTERNALREFERENCECODE_3 =
		"(accountGroup.externalReferenceCode IS NULL OR accountGroup.externalReferenceCode = '')";

	public AccountGroupPersistenceImpl() {
		setModelClass(AccountGroup.class);

		setModelImplClass(AccountGroupImpl.class);
		setModelPKClass(long.class);

		setTable(AccountGroupTable.INSTANCE);
	}

	/**
	 * Caches the account group in the entity cache if it is enabled.
	 *
	 * @param accountGroup the account group
	 */
	@Override
	public void cacheResult(AccountGroup accountGroup) {
		entityCache.putResult(
			AccountGroupImpl.class, accountGroup.getPrimaryKey(), accountGroup);

		finderCache.putResult(
			_finderPathFetchByC_ERC,
			new Object[] {
				accountGroup.getCompanyId(),
				accountGroup.getExternalReferenceCode()
			},
			accountGroup);
	}

	/**
	 * Caches the account groups in the entity cache if it is enabled.
	 *
	 * @param accountGroups the account groups
	 */
	@Override
	public void cacheResult(List<AccountGroup> accountGroups) {
		for (AccountGroup accountGroup : accountGroups) {
			if (entityCache.getResult(
					AccountGroupImpl.class, accountGroup.getPrimaryKey()) ==
						null) {

				cacheResult(accountGroup);
			}
		}
	}

	/**
	 * Clears the cache for all account groups.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(AccountGroupImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the account group.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(AccountGroup accountGroup) {
		entityCache.removeResult(
			AccountGroupImpl.class, accountGroup.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((AccountGroupModelImpl)accountGroup, true);
	}

	@Override
	public void clearCache(List<AccountGroup> accountGroups) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (AccountGroup accountGroup : accountGroups) {
			entityCache.removeResult(
				AccountGroupImpl.class, accountGroup.getPrimaryKey());

			clearUniqueFindersCache((AccountGroupModelImpl)accountGroup, true);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(AccountGroupImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		AccountGroupModelImpl accountGroupModelImpl) {

		Object[] args = new Object[] {
			accountGroupModelImpl.getCompanyId(),
			accountGroupModelImpl.getExternalReferenceCode()
		};

		finderCache.putResult(
			_finderPathCountByC_ERC, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByC_ERC, args, accountGroupModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		AccountGroupModelImpl accountGroupModelImpl, boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				accountGroupModelImpl.getCompanyId(),
				accountGroupModelImpl.getExternalReferenceCode()
			};

			finderCache.removeResult(_finderPathCountByC_ERC, args);
			finderCache.removeResult(_finderPathFetchByC_ERC, args);
		}

		if ((accountGroupModelImpl.getColumnBitmask() &
			 _finderPathFetchByC_ERC.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				accountGroupModelImpl.getColumnOriginalValue("companyId"),
				accountGroupModelImpl.getColumnOriginalValue(
					"externalReferenceCode")
			};

			finderCache.removeResult(_finderPathCountByC_ERC, args);
			finderCache.removeResult(_finderPathFetchByC_ERC, args);
		}
	}

	/**
	 * Creates a new account group with the primary key. Does not add the account group to the database.
	 *
	 * @param accountGroupId the primary key for the new account group
	 * @return the new account group
	 */
	@Override
	public AccountGroup create(long accountGroupId) {
		AccountGroup accountGroup = new AccountGroupImpl();

		accountGroup.setNew(true);
		accountGroup.setPrimaryKey(accountGroupId);

		accountGroup.setCompanyId(CompanyThreadLocal.getCompanyId());

		return accountGroup;
	}

	/**
	 * Removes the account group with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param accountGroupId the primary key of the account group
	 * @return the account group that was removed
	 * @throws NoSuchGroupException if a account group with the primary key could not be found
	 */
	@Override
	public AccountGroup remove(long accountGroupId)
		throws NoSuchGroupException {

		return remove((Serializable)accountGroupId);
	}

	/**
	 * Removes the account group with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the account group
	 * @return the account group that was removed
	 * @throws NoSuchGroupException if a account group with the primary key could not be found
	 */
	@Override
	public AccountGroup remove(Serializable primaryKey)
		throws NoSuchGroupException {

		Session session = null;

		try {
			session = openSession();

			AccountGroup accountGroup = (AccountGroup)session.get(
				AccountGroupImpl.class, primaryKey);

			if (accountGroup == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchGroupException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(accountGroup);
		}
		catch (NoSuchGroupException noSuchEntityException) {
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
	protected AccountGroup removeImpl(AccountGroup accountGroup) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(accountGroup)) {
				accountGroup = (AccountGroup)session.get(
					AccountGroupImpl.class, accountGroup.getPrimaryKeyObj());
			}

			if (accountGroup != null) {
				session.delete(accountGroup);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (accountGroup != null) {
			clearCache(accountGroup);
		}

		return accountGroup;
	}

	@Override
	public AccountGroup updateImpl(AccountGroup accountGroup) {
		boolean isNew = accountGroup.isNew();

		if (!(accountGroup instanceof AccountGroupModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(accountGroup.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					accountGroup);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in accountGroup proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom AccountGroup implementation " +
					accountGroup.getClass());
		}

		AccountGroupModelImpl accountGroupModelImpl =
			(AccountGroupModelImpl)accountGroup;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (accountGroup.getCreateDate() == null)) {
			if (serviceContext == null) {
				accountGroup.setCreateDate(now);
			}
			else {
				accountGroup.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!accountGroupModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				accountGroup.setModifiedDate(now);
			}
			else {
				accountGroup.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (accountGroup.isNew()) {
				session.save(accountGroup);

				accountGroup.setNew(false);
			}
			else {
				accountGroup = (AccountGroup)session.merge(accountGroup);
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
			Object[] args = new Object[] {accountGroupModelImpl.getCompanyId()};

			finderCache.removeResult(_finderPathCountByCompanyId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByCompanyId, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((accountGroupModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByCompanyId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					accountGroupModelImpl.getColumnOriginalValue("companyId")
				};

				finderCache.removeResult(_finderPathCountByCompanyId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByCompanyId, args);

				args = new Object[] {accountGroupModelImpl.getCompanyId()};

				finderCache.removeResult(_finderPathCountByCompanyId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByCompanyId, args);
			}
		}

		entityCache.putResult(
			AccountGroupImpl.class, accountGroup.getPrimaryKey(), accountGroup,
			false);

		clearUniqueFindersCache(accountGroupModelImpl, false);
		cacheUniqueFindersCache(accountGroupModelImpl);

		accountGroup.resetOriginalValues();

		return accountGroup;
	}

	/**
	 * Returns the account group with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the account group
	 * @return the account group
	 * @throws NoSuchGroupException if a account group with the primary key could not be found
	 */
	@Override
	public AccountGroup findByPrimaryKey(Serializable primaryKey)
		throws NoSuchGroupException {

		AccountGroup accountGroup = fetchByPrimaryKey(primaryKey);

		if (accountGroup == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchGroupException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return accountGroup;
	}

	/**
	 * Returns the account group with the primary key or throws a <code>NoSuchGroupException</code> if it could not be found.
	 *
	 * @param accountGroupId the primary key of the account group
	 * @return the account group
	 * @throws NoSuchGroupException if a account group with the primary key could not be found
	 */
	@Override
	public AccountGroup findByPrimaryKey(long accountGroupId)
		throws NoSuchGroupException {

		return findByPrimaryKey((Serializable)accountGroupId);
	}

	/**
	 * Returns the account group with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param accountGroupId the primary key of the account group
	 * @return the account group, or <code>null</code> if a account group with the primary key could not be found
	 */
	@Override
	public AccountGroup fetchByPrimaryKey(long accountGroupId) {
		return fetchByPrimaryKey((Serializable)accountGroupId);
	}

	/**
	 * Returns all the account groups.
	 *
	 * @return the account groups
	 */
	@Override
	public List<AccountGroup> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the account groups.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of account groups
	 * @param end the upper bound of the range of account groups (not inclusive)
	 * @return the range of account groups
	 */
	@Override
	public List<AccountGroup> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the account groups.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of account groups
	 * @param end the upper bound of the range of account groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of account groups
	 */
	@Override
	public List<AccountGroup> findAll(
		int start, int end, OrderByComparator<AccountGroup> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the account groups.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AccountGroupModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of account groups
	 * @param end the upper bound of the range of account groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of account groups
	 */
	@Override
	public List<AccountGroup> findAll(
		int start, int end, OrderByComparator<AccountGroup> orderByComparator,
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

		List<AccountGroup> list = null;

		if (useFinderCache) {
			list = (List<AccountGroup>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_ACCOUNTGROUP);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_ACCOUNTGROUP;

				sql = sql.concat(AccountGroupModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<AccountGroup>)QueryUtil.list(
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
	 * Removes all the account groups from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (AccountGroup accountGroup : findAll()) {
			remove(accountGroup);
		}
	}

	/**
	 * Returns the number of account groups.
	 *
	 * @return the number of account groups
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_ACCOUNTGROUP);

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
		return "accountGroupId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_ACCOUNTGROUP;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return AccountGroupModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the account group persistence.
	 */
	@Activate
	public void activate() {
		_finderPathWithPaginationFindAll = new FinderPath(
			AccountGroupImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			AccountGroupImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findAll", new String[0]);

		_finderPathCountAll = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByCompanyId = new FinderPath(
			AccountGroupImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCompanyId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByCompanyId = new FinderPath(
			AccountGroupImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByCompanyId", new String[] {Long.class.getName()},
			AccountGroupModelImpl.getColumnBitmask("companyId"));

		_finderPathCountByCompanyId = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCompanyId", new String[] {Long.class.getName()});

		_finderPathFetchByC_ERC = new FinderPath(
			AccountGroupImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByC_ERC",
			new String[] {Long.class.getName(), String.class.getName()},
			AccountGroupModelImpl.getColumnBitmask("companyId") |
			AccountGroupModelImpl.getColumnBitmask("externalReferenceCode"));

		_finderPathCountByC_ERC = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByC_ERC",
			new String[] {Long.class.getName(), String.class.getName()});
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(AccountGroupImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	@Reference(
		target = AccountPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = AccountPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = AccountPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_ACCOUNTGROUP =
		"SELECT accountGroup FROM AccountGroup accountGroup";

	private static final String _SQL_SELECT_ACCOUNTGROUP_WHERE =
		"SELECT accountGroup FROM AccountGroup accountGroup WHERE ";

	private static final String _SQL_COUNT_ACCOUNTGROUP =
		"SELECT COUNT(accountGroup) FROM AccountGroup accountGroup";

	private static final String _SQL_COUNT_ACCOUNTGROUP_WHERE =
		"SELECT COUNT(accountGroup) FROM AccountGroup accountGroup WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "accountGroup.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No AccountGroup exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No AccountGroup exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		AccountGroupPersistenceImpl.class);

	static {
		try {
			Class.forName(AccountPersistenceConstants.class.getName());
		}
		catch (ClassNotFoundException classNotFoundException) {
			throw new ExceptionInInitializerError(classNotFoundException);
		}
	}

}