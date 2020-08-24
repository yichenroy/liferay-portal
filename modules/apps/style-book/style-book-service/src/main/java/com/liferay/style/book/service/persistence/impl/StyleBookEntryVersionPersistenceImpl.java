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

package com.liferay.style.book.service.persistence.impl;

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
import com.liferay.style.book.exception.NoSuchEntryVersionException;
import com.liferay.style.book.model.StyleBookEntryVersion;
import com.liferay.style.book.model.StyleBookEntryVersionTable;
import com.liferay.style.book.model.impl.StyleBookEntryVersionImpl;
import com.liferay.style.book.model.impl.StyleBookEntryVersionModelImpl;
import com.liferay.style.book.service.persistence.StyleBookEntryVersionPersistence;
import com.liferay.style.book.service.persistence.impl.constants.StyleBookPersistenceConstants;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

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
 * The persistence implementation for the style book entry version service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = StyleBookEntryVersionPersistence.class)
public class StyleBookEntryVersionPersistenceImpl
	extends BasePersistenceImpl<StyleBookEntryVersion>
	implements StyleBookEntryVersionPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>StyleBookEntryVersionUtil</code> to access the style book entry version persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		StyleBookEntryVersionImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByStyleBookEntryId;
	private FinderPath _finderPathWithoutPaginationFindByStyleBookEntryId;
	private FinderPath _finderPathCountByStyleBookEntryId;

	/**
	 * Returns all the style book entry versions where styleBookEntryId = &#63;.
	 *
	 * @param styleBookEntryId the style book entry ID
	 * @return the matching style book entry versions
	 */
	@Override
	public List<StyleBookEntryVersion> findByStyleBookEntryId(
		long styleBookEntryId) {

		return findByStyleBookEntryId(
			styleBookEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the style book entry versions where styleBookEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param styleBookEntryId the style book entry ID
	 * @param start the lower bound of the range of style book entry versions
	 * @param end the upper bound of the range of style book entry versions (not inclusive)
	 * @return the range of matching style book entry versions
	 */
	@Override
	public List<StyleBookEntryVersion> findByStyleBookEntryId(
		long styleBookEntryId, int start, int end) {

		return findByStyleBookEntryId(styleBookEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the style book entry versions where styleBookEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param styleBookEntryId the style book entry ID
	 * @param start the lower bound of the range of style book entry versions
	 * @param end the upper bound of the range of style book entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching style book entry versions
	 */
	@Override
	public List<StyleBookEntryVersion> findByStyleBookEntryId(
		long styleBookEntryId, int start, int end,
		OrderByComparator<StyleBookEntryVersion> orderByComparator) {

		return findByStyleBookEntryId(
			styleBookEntryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the style book entry versions where styleBookEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param styleBookEntryId the style book entry ID
	 * @param start the lower bound of the range of style book entry versions
	 * @param end the upper bound of the range of style book entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching style book entry versions
	 */
	@Override
	public List<StyleBookEntryVersion> findByStyleBookEntryId(
		long styleBookEntryId, int start, int end,
		OrderByComparator<StyleBookEntryVersion> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByStyleBookEntryId;
				finderArgs = new Object[] {styleBookEntryId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByStyleBookEntryId;
			finderArgs = new Object[] {
				styleBookEntryId, start, end, orderByComparator
			};
		}

		List<StyleBookEntryVersion> list = null;

		if (useFinderCache) {
			list = (List<StyleBookEntryVersion>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (StyleBookEntryVersion styleBookEntryVersion : list) {
					if (styleBookEntryId !=
							styleBookEntryVersion.getStyleBookEntryId()) {

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

			sb.append(_SQL_SELECT_STYLEBOOKENTRYVERSION_WHERE);

			sb.append(_FINDER_COLUMN_STYLEBOOKENTRYID_STYLEBOOKENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(StyleBookEntryVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(styleBookEntryId);

				list = (List<StyleBookEntryVersion>)QueryUtil.list(
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
	 * Returns the first style book entry version in the ordered set where styleBookEntryId = &#63;.
	 *
	 * @param styleBookEntryId the style book entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry version
	 * @throws NoSuchEntryVersionException if a matching style book entry version could not be found
	 */
	@Override
	public StyleBookEntryVersion findByStyleBookEntryId_First(
			long styleBookEntryId,
			OrderByComparator<StyleBookEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		StyleBookEntryVersion styleBookEntryVersion =
			fetchByStyleBookEntryId_First(styleBookEntryId, orderByComparator);

		if (styleBookEntryVersion != null) {
			return styleBookEntryVersion;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("styleBookEntryId=");
		sb.append(styleBookEntryId);

		sb.append("}");

		throw new NoSuchEntryVersionException(sb.toString());
	}

	/**
	 * Returns the first style book entry version in the ordered set where styleBookEntryId = &#63;.
	 *
	 * @param styleBookEntryId the style book entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry version, or <code>null</code> if a matching style book entry version could not be found
	 */
	@Override
	public StyleBookEntryVersion fetchByStyleBookEntryId_First(
		long styleBookEntryId,
		OrderByComparator<StyleBookEntryVersion> orderByComparator) {

		List<StyleBookEntryVersion> list = findByStyleBookEntryId(
			styleBookEntryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last style book entry version in the ordered set where styleBookEntryId = &#63;.
	 *
	 * @param styleBookEntryId the style book entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry version
	 * @throws NoSuchEntryVersionException if a matching style book entry version could not be found
	 */
	@Override
	public StyleBookEntryVersion findByStyleBookEntryId_Last(
			long styleBookEntryId,
			OrderByComparator<StyleBookEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		StyleBookEntryVersion styleBookEntryVersion =
			fetchByStyleBookEntryId_Last(styleBookEntryId, orderByComparator);

		if (styleBookEntryVersion != null) {
			return styleBookEntryVersion;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("styleBookEntryId=");
		sb.append(styleBookEntryId);

		sb.append("}");

		throw new NoSuchEntryVersionException(sb.toString());
	}

	/**
	 * Returns the last style book entry version in the ordered set where styleBookEntryId = &#63;.
	 *
	 * @param styleBookEntryId the style book entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry version, or <code>null</code> if a matching style book entry version could not be found
	 */
	@Override
	public StyleBookEntryVersion fetchByStyleBookEntryId_Last(
		long styleBookEntryId,
		OrderByComparator<StyleBookEntryVersion> orderByComparator) {

		int count = countByStyleBookEntryId(styleBookEntryId);

		if (count == 0) {
			return null;
		}

		List<StyleBookEntryVersion> list = findByStyleBookEntryId(
			styleBookEntryId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the style book entry versions before and after the current style book entry version in the ordered set where styleBookEntryId = &#63;.
	 *
	 * @param styleBookEntryVersionId the primary key of the current style book entry version
	 * @param styleBookEntryId the style book entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next style book entry version
	 * @throws NoSuchEntryVersionException if a style book entry version with the primary key could not be found
	 */
	@Override
	public StyleBookEntryVersion[] findByStyleBookEntryId_PrevAndNext(
			long styleBookEntryVersionId, long styleBookEntryId,
			OrderByComparator<StyleBookEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		StyleBookEntryVersion styleBookEntryVersion = findByPrimaryKey(
			styleBookEntryVersionId);

		Session session = null;

		try {
			session = openSession();

			StyleBookEntryVersion[] array = new StyleBookEntryVersionImpl[3];

			array[0] = getByStyleBookEntryId_PrevAndNext(
				session, styleBookEntryVersion, styleBookEntryId,
				orderByComparator, true);

			array[1] = styleBookEntryVersion;

			array[2] = getByStyleBookEntryId_PrevAndNext(
				session, styleBookEntryVersion, styleBookEntryId,
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

	protected StyleBookEntryVersion getByStyleBookEntryId_PrevAndNext(
		Session session, StyleBookEntryVersion styleBookEntryVersion,
		long styleBookEntryId,
		OrderByComparator<StyleBookEntryVersion> orderByComparator,
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

		sb.append(_SQL_SELECT_STYLEBOOKENTRYVERSION_WHERE);

		sb.append(_FINDER_COLUMN_STYLEBOOKENTRYID_STYLEBOOKENTRYID_2);

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
			sb.append(StyleBookEntryVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(styleBookEntryId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						styleBookEntryVersion)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<StyleBookEntryVersion> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the style book entry versions where styleBookEntryId = &#63; from the database.
	 *
	 * @param styleBookEntryId the style book entry ID
	 */
	@Override
	public void removeByStyleBookEntryId(long styleBookEntryId) {
		for (StyleBookEntryVersion styleBookEntryVersion :
				findByStyleBookEntryId(
					styleBookEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(styleBookEntryVersion);
		}
	}

	/**
	 * Returns the number of style book entry versions where styleBookEntryId = &#63;.
	 *
	 * @param styleBookEntryId the style book entry ID
	 * @return the number of matching style book entry versions
	 */
	@Override
	public int countByStyleBookEntryId(long styleBookEntryId) {
		FinderPath finderPath = _finderPathCountByStyleBookEntryId;

		Object[] finderArgs = new Object[] {styleBookEntryId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_STYLEBOOKENTRYVERSION_WHERE);

			sb.append(_FINDER_COLUMN_STYLEBOOKENTRYID_STYLEBOOKENTRYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(styleBookEntryId);

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
		_FINDER_COLUMN_STYLEBOOKENTRYID_STYLEBOOKENTRYID_2 =
			"styleBookEntryVersion.styleBookEntryId = ?";

	private FinderPath _finderPathFetchByStyleBookEntryId_Version;
	private FinderPath _finderPathCountByStyleBookEntryId_Version;

	/**
	 * Returns the style book entry version where styleBookEntryId = &#63; and version = &#63; or throws a <code>NoSuchEntryVersionException</code> if it could not be found.
	 *
	 * @param styleBookEntryId the style book entry ID
	 * @param version the version
	 * @return the matching style book entry version
	 * @throws NoSuchEntryVersionException if a matching style book entry version could not be found
	 */
	@Override
	public StyleBookEntryVersion findByStyleBookEntryId_Version(
			long styleBookEntryId, int version)
		throws NoSuchEntryVersionException {

		StyleBookEntryVersion styleBookEntryVersion =
			fetchByStyleBookEntryId_Version(styleBookEntryId, version);

		if (styleBookEntryVersion == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("styleBookEntryId=");
			sb.append(styleBookEntryId);

			sb.append(", version=");
			sb.append(version);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchEntryVersionException(sb.toString());
		}

		return styleBookEntryVersion;
	}

	/**
	 * Returns the style book entry version where styleBookEntryId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param styleBookEntryId the style book entry ID
	 * @param version the version
	 * @return the matching style book entry version, or <code>null</code> if a matching style book entry version could not be found
	 */
	@Override
	public StyleBookEntryVersion fetchByStyleBookEntryId_Version(
		long styleBookEntryId, int version) {

		return fetchByStyleBookEntryId_Version(styleBookEntryId, version, true);
	}

	/**
	 * Returns the style book entry version where styleBookEntryId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param styleBookEntryId the style book entry ID
	 * @param version the version
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching style book entry version, or <code>null</code> if a matching style book entry version could not be found
	 */
	@Override
	public StyleBookEntryVersion fetchByStyleBookEntryId_Version(
		long styleBookEntryId, int version, boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {styleBookEntryId, version};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByStyleBookEntryId_Version, finderArgs, this);
		}

		if (result instanceof StyleBookEntryVersion) {
			StyleBookEntryVersion styleBookEntryVersion =
				(StyleBookEntryVersion)result;

			if ((styleBookEntryId !=
					styleBookEntryVersion.getStyleBookEntryId()) ||
				(version != styleBookEntryVersion.getVersion())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_STYLEBOOKENTRYVERSION_WHERE);

			sb.append(
				_FINDER_COLUMN_STYLEBOOKENTRYID_VERSION_STYLEBOOKENTRYID_2);

			sb.append(_FINDER_COLUMN_STYLEBOOKENTRYID_VERSION_VERSION_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(styleBookEntryId);

				queryPos.add(version);

				List<StyleBookEntryVersion> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByStyleBookEntryId_Version,
							finderArgs, list);
					}
				}
				else {
					StyleBookEntryVersion styleBookEntryVersion = list.get(0);

					result = styleBookEntryVersion;

					cacheResult(styleBookEntryVersion);
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
			return (StyleBookEntryVersion)result;
		}
	}

	/**
	 * Removes the style book entry version where styleBookEntryId = &#63; and version = &#63; from the database.
	 *
	 * @param styleBookEntryId the style book entry ID
	 * @param version the version
	 * @return the style book entry version that was removed
	 */
	@Override
	public StyleBookEntryVersion removeByStyleBookEntryId_Version(
			long styleBookEntryId, int version)
		throws NoSuchEntryVersionException {

		StyleBookEntryVersion styleBookEntryVersion =
			findByStyleBookEntryId_Version(styleBookEntryId, version);

		return remove(styleBookEntryVersion);
	}

	/**
	 * Returns the number of style book entry versions where styleBookEntryId = &#63; and version = &#63;.
	 *
	 * @param styleBookEntryId the style book entry ID
	 * @param version the version
	 * @return the number of matching style book entry versions
	 */
	@Override
	public int countByStyleBookEntryId_Version(
		long styleBookEntryId, int version) {

		FinderPath finderPath = _finderPathCountByStyleBookEntryId_Version;

		Object[] finderArgs = new Object[] {styleBookEntryId, version};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_STYLEBOOKENTRYVERSION_WHERE);

			sb.append(
				_FINDER_COLUMN_STYLEBOOKENTRYID_VERSION_STYLEBOOKENTRYID_2);

			sb.append(_FINDER_COLUMN_STYLEBOOKENTRYID_VERSION_VERSION_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(styleBookEntryId);

				queryPos.add(version);

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
		_FINDER_COLUMN_STYLEBOOKENTRYID_VERSION_STYLEBOOKENTRYID_2 =
			"styleBookEntryVersion.styleBookEntryId = ? AND ";

	private static final String
		_FINDER_COLUMN_STYLEBOOKENTRYID_VERSION_VERSION_2 =
			"styleBookEntryVersion.version = ?";

	private FinderPath _finderPathWithPaginationFindByGroupId;
	private FinderPath _finderPathWithoutPaginationFindByGroupId;
	private FinderPath _finderPathCountByGroupId;

	/**
	 * Returns all the style book entry versions where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching style book entry versions
	 */
	@Override
	public List<StyleBookEntryVersion> findByGroupId(long groupId) {
		return findByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the style book entry versions where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of style book entry versions
	 * @param end the upper bound of the range of style book entry versions (not inclusive)
	 * @return the range of matching style book entry versions
	 */
	@Override
	public List<StyleBookEntryVersion> findByGroupId(
		long groupId, int start, int end) {

		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the style book entry versions where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of style book entry versions
	 * @param end the upper bound of the range of style book entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching style book entry versions
	 */
	@Override
	public List<StyleBookEntryVersion> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<StyleBookEntryVersion> orderByComparator) {

		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the style book entry versions where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of style book entry versions
	 * @param end the upper bound of the range of style book entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching style book entry versions
	 */
	@Override
	public List<StyleBookEntryVersion> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<StyleBookEntryVersion> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByGroupId;
				finderArgs = new Object[] {groupId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByGroupId;
			finderArgs = new Object[] {groupId, start, end, orderByComparator};
		}

		List<StyleBookEntryVersion> list = null;

		if (useFinderCache) {
			list = (List<StyleBookEntryVersion>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (StyleBookEntryVersion styleBookEntryVersion : list) {
					if (groupId != styleBookEntryVersion.getGroupId()) {
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

			sb.append(_SQL_SELECT_STYLEBOOKENTRYVERSION_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(StyleBookEntryVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				list = (List<StyleBookEntryVersion>)QueryUtil.list(
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
	 * Returns the first style book entry version in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry version
	 * @throws NoSuchEntryVersionException if a matching style book entry version could not be found
	 */
	@Override
	public StyleBookEntryVersion findByGroupId_First(
			long groupId,
			OrderByComparator<StyleBookEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		StyleBookEntryVersion styleBookEntryVersion = fetchByGroupId_First(
			groupId, orderByComparator);

		if (styleBookEntryVersion != null) {
			return styleBookEntryVersion;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchEntryVersionException(sb.toString());
	}

	/**
	 * Returns the first style book entry version in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry version, or <code>null</code> if a matching style book entry version could not be found
	 */
	@Override
	public StyleBookEntryVersion fetchByGroupId_First(
		long groupId,
		OrderByComparator<StyleBookEntryVersion> orderByComparator) {

		List<StyleBookEntryVersion> list = findByGroupId(
			groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last style book entry version in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry version
	 * @throws NoSuchEntryVersionException if a matching style book entry version could not be found
	 */
	@Override
	public StyleBookEntryVersion findByGroupId_Last(
			long groupId,
			OrderByComparator<StyleBookEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		StyleBookEntryVersion styleBookEntryVersion = fetchByGroupId_Last(
			groupId, orderByComparator);

		if (styleBookEntryVersion != null) {
			return styleBookEntryVersion;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchEntryVersionException(sb.toString());
	}

	/**
	 * Returns the last style book entry version in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry version, or <code>null</code> if a matching style book entry version could not be found
	 */
	@Override
	public StyleBookEntryVersion fetchByGroupId_Last(
		long groupId,
		OrderByComparator<StyleBookEntryVersion> orderByComparator) {

		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<StyleBookEntryVersion> list = findByGroupId(
			groupId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the style book entry versions before and after the current style book entry version in the ordered set where groupId = &#63;.
	 *
	 * @param styleBookEntryVersionId the primary key of the current style book entry version
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next style book entry version
	 * @throws NoSuchEntryVersionException if a style book entry version with the primary key could not be found
	 */
	@Override
	public StyleBookEntryVersion[] findByGroupId_PrevAndNext(
			long styleBookEntryVersionId, long groupId,
			OrderByComparator<StyleBookEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		StyleBookEntryVersion styleBookEntryVersion = findByPrimaryKey(
			styleBookEntryVersionId);

		Session session = null;

		try {
			session = openSession();

			StyleBookEntryVersion[] array = new StyleBookEntryVersionImpl[3];

			array[0] = getByGroupId_PrevAndNext(
				session, styleBookEntryVersion, groupId, orderByComparator,
				true);

			array[1] = styleBookEntryVersion;

			array[2] = getByGroupId_PrevAndNext(
				session, styleBookEntryVersion, groupId, orderByComparator,
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

	protected StyleBookEntryVersion getByGroupId_PrevAndNext(
		Session session, StyleBookEntryVersion styleBookEntryVersion,
		long groupId,
		OrderByComparator<StyleBookEntryVersion> orderByComparator,
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

		sb.append(_SQL_SELECT_STYLEBOOKENTRYVERSION_WHERE);

		sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

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
			sb.append(StyleBookEntryVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						styleBookEntryVersion)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<StyleBookEntryVersion> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the style book entry versions where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (StyleBookEntryVersion styleBookEntryVersion :
				findByGroupId(
					groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(styleBookEntryVersion);
		}
	}

	/**
	 * Returns the number of style book entry versions where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching style book entry versions
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = _finderPathCountByGroupId;

		Object[] finderArgs = new Object[] {groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_STYLEBOOKENTRYVERSION_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 =
		"styleBookEntryVersion.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByGroupId_Version;
	private FinderPath _finderPathWithoutPaginationFindByGroupId_Version;
	private FinderPath _finderPathCountByGroupId_Version;

	/**
	 * Returns all the style book entry versions where groupId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @return the matching style book entry versions
	 */
	@Override
	public List<StyleBookEntryVersion> findByGroupId_Version(
		long groupId, int version) {

		return findByGroupId_Version(
			groupId, version, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the style book entry versions where groupId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param start the lower bound of the range of style book entry versions
	 * @param end the upper bound of the range of style book entry versions (not inclusive)
	 * @return the range of matching style book entry versions
	 */
	@Override
	public List<StyleBookEntryVersion> findByGroupId_Version(
		long groupId, int version, int start, int end) {

		return findByGroupId_Version(groupId, version, start, end, null);
	}

	/**
	 * Returns an ordered range of all the style book entry versions where groupId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param start the lower bound of the range of style book entry versions
	 * @param end the upper bound of the range of style book entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching style book entry versions
	 */
	@Override
	public List<StyleBookEntryVersion> findByGroupId_Version(
		long groupId, int version, int start, int end,
		OrderByComparator<StyleBookEntryVersion> orderByComparator) {

		return findByGroupId_Version(
			groupId, version, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the style book entry versions where groupId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param start the lower bound of the range of style book entry versions
	 * @param end the upper bound of the range of style book entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching style book entry versions
	 */
	@Override
	public List<StyleBookEntryVersion> findByGroupId_Version(
		long groupId, int version, int start, int end,
		OrderByComparator<StyleBookEntryVersion> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByGroupId_Version;
				finderArgs = new Object[] {groupId, version};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByGroupId_Version;
			finderArgs = new Object[] {
				groupId, version, start, end, orderByComparator
			};
		}

		List<StyleBookEntryVersion> list = null;

		if (useFinderCache) {
			list = (List<StyleBookEntryVersion>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (StyleBookEntryVersion styleBookEntryVersion : list) {
					if ((groupId != styleBookEntryVersion.getGroupId()) ||
						(version != styleBookEntryVersion.getVersion())) {

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

			sb.append(_SQL_SELECT_STYLEBOOKENTRYVERSION_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_VERSION_GROUPID_2);

			sb.append(_FINDER_COLUMN_GROUPID_VERSION_VERSION_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(StyleBookEntryVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(version);

				list = (List<StyleBookEntryVersion>)QueryUtil.list(
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
	 * Returns the first style book entry version in the ordered set where groupId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry version
	 * @throws NoSuchEntryVersionException if a matching style book entry version could not be found
	 */
	@Override
	public StyleBookEntryVersion findByGroupId_Version_First(
			long groupId, int version,
			OrderByComparator<StyleBookEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		StyleBookEntryVersion styleBookEntryVersion =
			fetchByGroupId_Version_First(groupId, version, orderByComparator);

		if (styleBookEntryVersion != null) {
			return styleBookEntryVersion;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", version=");
		sb.append(version);

		sb.append("}");

		throw new NoSuchEntryVersionException(sb.toString());
	}

	/**
	 * Returns the first style book entry version in the ordered set where groupId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry version, or <code>null</code> if a matching style book entry version could not be found
	 */
	@Override
	public StyleBookEntryVersion fetchByGroupId_Version_First(
		long groupId, int version,
		OrderByComparator<StyleBookEntryVersion> orderByComparator) {

		List<StyleBookEntryVersion> list = findByGroupId_Version(
			groupId, version, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last style book entry version in the ordered set where groupId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry version
	 * @throws NoSuchEntryVersionException if a matching style book entry version could not be found
	 */
	@Override
	public StyleBookEntryVersion findByGroupId_Version_Last(
			long groupId, int version,
			OrderByComparator<StyleBookEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		StyleBookEntryVersion styleBookEntryVersion =
			fetchByGroupId_Version_Last(groupId, version, orderByComparator);

		if (styleBookEntryVersion != null) {
			return styleBookEntryVersion;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", version=");
		sb.append(version);

		sb.append("}");

		throw new NoSuchEntryVersionException(sb.toString());
	}

	/**
	 * Returns the last style book entry version in the ordered set where groupId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry version, or <code>null</code> if a matching style book entry version could not be found
	 */
	@Override
	public StyleBookEntryVersion fetchByGroupId_Version_Last(
		long groupId, int version,
		OrderByComparator<StyleBookEntryVersion> orderByComparator) {

		int count = countByGroupId_Version(groupId, version);

		if (count == 0) {
			return null;
		}

		List<StyleBookEntryVersion> list = findByGroupId_Version(
			groupId, version, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the style book entry versions before and after the current style book entry version in the ordered set where groupId = &#63; and version = &#63;.
	 *
	 * @param styleBookEntryVersionId the primary key of the current style book entry version
	 * @param groupId the group ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next style book entry version
	 * @throws NoSuchEntryVersionException if a style book entry version with the primary key could not be found
	 */
	@Override
	public StyleBookEntryVersion[] findByGroupId_Version_PrevAndNext(
			long styleBookEntryVersionId, long groupId, int version,
			OrderByComparator<StyleBookEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		StyleBookEntryVersion styleBookEntryVersion = findByPrimaryKey(
			styleBookEntryVersionId);

		Session session = null;

		try {
			session = openSession();

			StyleBookEntryVersion[] array = new StyleBookEntryVersionImpl[3];

			array[0] = getByGroupId_Version_PrevAndNext(
				session, styleBookEntryVersion, groupId, version,
				orderByComparator, true);

			array[1] = styleBookEntryVersion;

			array[2] = getByGroupId_Version_PrevAndNext(
				session, styleBookEntryVersion, groupId, version,
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

	protected StyleBookEntryVersion getByGroupId_Version_PrevAndNext(
		Session session, StyleBookEntryVersion styleBookEntryVersion,
		long groupId, int version,
		OrderByComparator<StyleBookEntryVersion> orderByComparator,
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

		sb.append(_SQL_SELECT_STYLEBOOKENTRYVERSION_WHERE);

		sb.append(_FINDER_COLUMN_GROUPID_VERSION_GROUPID_2);

		sb.append(_FINDER_COLUMN_GROUPID_VERSION_VERSION_2);

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
			sb.append(StyleBookEntryVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(version);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						styleBookEntryVersion)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<StyleBookEntryVersion> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the style book entry versions where groupId = &#63; and version = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 */
	@Override
	public void removeByGroupId_Version(long groupId, int version) {
		for (StyleBookEntryVersion styleBookEntryVersion :
				findByGroupId_Version(
					groupId, version, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(styleBookEntryVersion);
		}
	}

	/**
	 * Returns the number of style book entry versions where groupId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @return the number of matching style book entry versions
	 */
	@Override
	public int countByGroupId_Version(long groupId, int version) {
		FinderPath finderPath = _finderPathCountByGroupId_Version;

		Object[] finderArgs = new Object[] {groupId, version};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_STYLEBOOKENTRYVERSION_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_VERSION_GROUPID_2);

			sb.append(_FINDER_COLUMN_GROUPID_VERSION_VERSION_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(version);

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

	private static final String _FINDER_COLUMN_GROUPID_VERSION_GROUPID_2 =
		"styleBookEntryVersion.groupId = ? AND ";

	private static final String _FINDER_COLUMN_GROUPID_VERSION_VERSION_2 =
		"styleBookEntryVersion.version = ?";

	private FinderPath _finderPathWithPaginationFindByG_D;
	private FinderPath _finderPathWithoutPaginationFindByG_D;
	private FinderPath _finderPathCountByG_D;

	/**
	 * Returns all the style book entry versions where groupId = &#63; and defaultStyleBookEntry = &#63;.
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @return the matching style book entry versions
	 */
	@Override
	public List<StyleBookEntryVersion> findByG_D(
		long groupId, boolean defaultStyleBookEntry) {

		return findByG_D(
			groupId, defaultStyleBookEntry, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the style book entry versions where groupId = &#63; and defaultStyleBookEntry = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param start the lower bound of the range of style book entry versions
	 * @param end the upper bound of the range of style book entry versions (not inclusive)
	 * @return the range of matching style book entry versions
	 */
	@Override
	public List<StyleBookEntryVersion> findByG_D(
		long groupId, boolean defaultStyleBookEntry, int start, int end) {

		return findByG_D(groupId, defaultStyleBookEntry, start, end, null);
	}

	/**
	 * Returns an ordered range of all the style book entry versions where groupId = &#63; and defaultStyleBookEntry = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param start the lower bound of the range of style book entry versions
	 * @param end the upper bound of the range of style book entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching style book entry versions
	 */
	@Override
	public List<StyleBookEntryVersion> findByG_D(
		long groupId, boolean defaultStyleBookEntry, int start, int end,
		OrderByComparator<StyleBookEntryVersion> orderByComparator) {

		return findByG_D(
			groupId, defaultStyleBookEntry, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the style book entry versions where groupId = &#63; and defaultStyleBookEntry = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param start the lower bound of the range of style book entry versions
	 * @param end the upper bound of the range of style book entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching style book entry versions
	 */
	@Override
	public List<StyleBookEntryVersion> findByG_D(
		long groupId, boolean defaultStyleBookEntry, int start, int end,
		OrderByComparator<StyleBookEntryVersion> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByG_D;
				finderArgs = new Object[] {groupId, defaultStyleBookEntry};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByG_D;
			finderArgs = new Object[] {
				groupId, defaultStyleBookEntry, start, end, orderByComparator
			};
		}

		List<StyleBookEntryVersion> list = null;

		if (useFinderCache) {
			list = (List<StyleBookEntryVersion>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (StyleBookEntryVersion styleBookEntryVersion : list) {
					if ((groupId != styleBookEntryVersion.getGroupId()) ||
						(defaultStyleBookEntry !=
							styleBookEntryVersion.isDefaultStyleBookEntry())) {

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

			sb.append(_SQL_SELECT_STYLEBOOKENTRYVERSION_WHERE);

			sb.append(_FINDER_COLUMN_G_D_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_D_DEFAULTSTYLEBOOKENTRY_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(StyleBookEntryVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(defaultStyleBookEntry);

				list = (List<StyleBookEntryVersion>)QueryUtil.list(
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
	 * Returns the first style book entry version in the ordered set where groupId = &#63; and defaultStyleBookEntry = &#63;.
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry version
	 * @throws NoSuchEntryVersionException if a matching style book entry version could not be found
	 */
	@Override
	public StyleBookEntryVersion findByG_D_First(
			long groupId, boolean defaultStyleBookEntry,
			OrderByComparator<StyleBookEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		StyleBookEntryVersion styleBookEntryVersion = fetchByG_D_First(
			groupId, defaultStyleBookEntry, orderByComparator);

		if (styleBookEntryVersion != null) {
			return styleBookEntryVersion;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", defaultStyleBookEntry=");
		sb.append(defaultStyleBookEntry);

		sb.append("}");

		throw new NoSuchEntryVersionException(sb.toString());
	}

	/**
	 * Returns the first style book entry version in the ordered set where groupId = &#63; and defaultStyleBookEntry = &#63;.
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry version, or <code>null</code> if a matching style book entry version could not be found
	 */
	@Override
	public StyleBookEntryVersion fetchByG_D_First(
		long groupId, boolean defaultStyleBookEntry,
		OrderByComparator<StyleBookEntryVersion> orderByComparator) {

		List<StyleBookEntryVersion> list = findByG_D(
			groupId, defaultStyleBookEntry, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last style book entry version in the ordered set where groupId = &#63; and defaultStyleBookEntry = &#63;.
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry version
	 * @throws NoSuchEntryVersionException if a matching style book entry version could not be found
	 */
	@Override
	public StyleBookEntryVersion findByG_D_Last(
			long groupId, boolean defaultStyleBookEntry,
			OrderByComparator<StyleBookEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		StyleBookEntryVersion styleBookEntryVersion = fetchByG_D_Last(
			groupId, defaultStyleBookEntry, orderByComparator);

		if (styleBookEntryVersion != null) {
			return styleBookEntryVersion;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", defaultStyleBookEntry=");
		sb.append(defaultStyleBookEntry);

		sb.append("}");

		throw new NoSuchEntryVersionException(sb.toString());
	}

	/**
	 * Returns the last style book entry version in the ordered set where groupId = &#63; and defaultStyleBookEntry = &#63;.
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry version, or <code>null</code> if a matching style book entry version could not be found
	 */
	@Override
	public StyleBookEntryVersion fetchByG_D_Last(
		long groupId, boolean defaultStyleBookEntry,
		OrderByComparator<StyleBookEntryVersion> orderByComparator) {

		int count = countByG_D(groupId, defaultStyleBookEntry);

		if (count == 0) {
			return null;
		}

		List<StyleBookEntryVersion> list = findByG_D(
			groupId, defaultStyleBookEntry, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the style book entry versions before and after the current style book entry version in the ordered set where groupId = &#63; and defaultStyleBookEntry = &#63;.
	 *
	 * @param styleBookEntryVersionId the primary key of the current style book entry version
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next style book entry version
	 * @throws NoSuchEntryVersionException if a style book entry version with the primary key could not be found
	 */
	@Override
	public StyleBookEntryVersion[] findByG_D_PrevAndNext(
			long styleBookEntryVersionId, long groupId,
			boolean defaultStyleBookEntry,
			OrderByComparator<StyleBookEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		StyleBookEntryVersion styleBookEntryVersion = findByPrimaryKey(
			styleBookEntryVersionId);

		Session session = null;

		try {
			session = openSession();

			StyleBookEntryVersion[] array = new StyleBookEntryVersionImpl[3];

			array[0] = getByG_D_PrevAndNext(
				session, styleBookEntryVersion, groupId, defaultStyleBookEntry,
				orderByComparator, true);

			array[1] = styleBookEntryVersion;

			array[2] = getByG_D_PrevAndNext(
				session, styleBookEntryVersion, groupId, defaultStyleBookEntry,
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

	protected StyleBookEntryVersion getByG_D_PrevAndNext(
		Session session, StyleBookEntryVersion styleBookEntryVersion,
		long groupId, boolean defaultStyleBookEntry,
		OrderByComparator<StyleBookEntryVersion> orderByComparator,
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

		sb.append(_SQL_SELECT_STYLEBOOKENTRYVERSION_WHERE);

		sb.append(_FINDER_COLUMN_G_D_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_D_DEFAULTSTYLEBOOKENTRY_2);

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
			sb.append(StyleBookEntryVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(defaultStyleBookEntry);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						styleBookEntryVersion)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<StyleBookEntryVersion> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the style book entry versions where groupId = &#63; and defaultStyleBookEntry = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 */
	@Override
	public void removeByG_D(long groupId, boolean defaultStyleBookEntry) {
		for (StyleBookEntryVersion styleBookEntryVersion :
				findByG_D(
					groupId, defaultStyleBookEntry, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(styleBookEntryVersion);
		}
	}

	/**
	 * Returns the number of style book entry versions where groupId = &#63; and defaultStyleBookEntry = &#63;.
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @return the number of matching style book entry versions
	 */
	@Override
	public int countByG_D(long groupId, boolean defaultStyleBookEntry) {
		FinderPath finderPath = _finderPathCountByG_D;

		Object[] finderArgs = new Object[] {groupId, defaultStyleBookEntry};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_STYLEBOOKENTRYVERSION_WHERE);

			sb.append(_FINDER_COLUMN_G_D_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_D_DEFAULTSTYLEBOOKENTRY_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(defaultStyleBookEntry);

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

	private static final String _FINDER_COLUMN_G_D_GROUPID_2 =
		"styleBookEntryVersion.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_D_DEFAULTSTYLEBOOKENTRY_2 =
		"styleBookEntryVersion.defaultStyleBookEntry = ?";

	private FinderPath _finderPathWithPaginationFindByG_D_Version;
	private FinderPath _finderPathWithoutPaginationFindByG_D_Version;
	private FinderPath _finderPathCountByG_D_Version;

	/**
	 * Returns all the style book entry versions where groupId = &#63; and defaultStyleBookEntry = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param version the version
	 * @return the matching style book entry versions
	 */
	@Override
	public List<StyleBookEntryVersion> findByG_D_Version(
		long groupId, boolean defaultStyleBookEntry, int version) {

		return findByG_D_Version(
			groupId, defaultStyleBookEntry, version, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the style book entry versions where groupId = &#63; and defaultStyleBookEntry = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param version the version
	 * @param start the lower bound of the range of style book entry versions
	 * @param end the upper bound of the range of style book entry versions (not inclusive)
	 * @return the range of matching style book entry versions
	 */
	@Override
	public List<StyleBookEntryVersion> findByG_D_Version(
		long groupId, boolean defaultStyleBookEntry, int version, int start,
		int end) {

		return findByG_D_Version(
			groupId, defaultStyleBookEntry, version, start, end, null);
	}

	/**
	 * Returns an ordered range of all the style book entry versions where groupId = &#63; and defaultStyleBookEntry = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param version the version
	 * @param start the lower bound of the range of style book entry versions
	 * @param end the upper bound of the range of style book entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching style book entry versions
	 */
	@Override
	public List<StyleBookEntryVersion> findByG_D_Version(
		long groupId, boolean defaultStyleBookEntry, int version, int start,
		int end, OrderByComparator<StyleBookEntryVersion> orderByComparator) {

		return findByG_D_Version(
			groupId, defaultStyleBookEntry, version, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the style book entry versions where groupId = &#63; and defaultStyleBookEntry = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param version the version
	 * @param start the lower bound of the range of style book entry versions
	 * @param end the upper bound of the range of style book entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching style book entry versions
	 */
	@Override
	public List<StyleBookEntryVersion> findByG_D_Version(
		long groupId, boolean defaultStyleBookEntry, int version, int start,
		int end, OrderByComparator<StyleBookEntryVersion> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByG_D_Version;
				finderArgs = new Object[] {
					groupId, defaultStyleBookEntry, version
				};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByG_D_Version;
			finderArgs = new Object[] {
				groupId, defaultStyleBookEntry, version, start, end,
				orderByComparator
			};
		}

		List<StyleBookEntryVersion> list = null;

		if (useFinderCache) {
			list = (List<StyleBookEntryVersion>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (StyleBookEntryVersion styleBookEntryVersion : list) {
					if ((groupId != styleBookEntryVersion.getGroupId()) ||
						(defaultStyleBookEntry !=
							styleBookEntryVersion.isDefaultStyleBookEntry()) ||
						(version != styleBookEntryVersion.getVersion())) {

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

			sb.append(_SQL_SELECT_STYLEBOOKENTRYVERSION_WHERE);

			sb.append(_FINDER_COLUMN_G_D_VERSION_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_D_VERSION_DEFAULTSTYLEBOOKENTRY_2);

			sb.append(_FINDER_COLUMN_G_D_VERSION_VERSION_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(StyleBookEntryVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(defaultStyleBookEntry);

				queryPos.add(version);

				list = (List<StyleBookEntryVersion>)QueryUtil.list(
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
	 * Returns the first style book entry version in the ordered set where groupId = &#63; and defaultStyleBookEntry = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry version
	 * @throws NoSuchEntryVersionException if a matching style book entry version could not be found
	 */
	@Override
	public StyleBookEntryVersion findByG_D_Version_First(
			long groupId, boolean defaultStyleBookEntry, int version,
			OrderByComparator<StyleBookEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		StyleBookEntryVersion styleBookEntryVersion = fetchByG_D_Version_First(
			groupId, defaultStyleBookEntry, version, orderByComparator);

		if (styleBookEntryVersion != null) {
			return styleBookEntryVersion;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", defaultStyleBookEntry=");
		sb.append(defaultStyleBookEntry);

		sb.append(", version=");
		sb.append(version);

		sb.append("}");

		throw new NoSuchEntryVersionException(sb.toString());
	}

	/**
	 * Returns the first style book entry version in the ordered set where groupId = &#63; and defaultStyleBookEntry = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry version, or <code>null</code> if a matching style book entry version could not be found
	 */
	@Override
	public StyleBookEntryVersion fetchByG_D_Version_First(
		long groupId, boolean defaultStyleBookEntry, int version,
		OrderByComparator<StyleBookEntryVersion> orderByComparator) {

		List<StyleBookEntryVersion> list = findByG_D_Version(
			groupId, defaultStyleBookEntry, version, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last style book entry version in the ordered set where groupId = &#63; and defaultStyleBookEntry = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry version
	 * @throws NoSuchEntryVersionException if a matching style book entry version could not be found
	 */
	@Override
	public StyleBookEntryVersion findByG_D_Version_Last(
			long groupId, boolean defaultStyleBookEntry, int version,
			OrderByComparator<StyleBookEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		StyleBookEntryVersion styleBookEntryVersion = fetchByG_D_Version_Last(
			groupId, defaultStyleBookEntry, version, orderByComparator);

		if (styleBookEntryVersion != null) {
			return styleBookEntryVersion;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", defaultStyleBookEntry=");
		sb.append(defaultStyleBookEntry);

		sb.append(", version=");
		sb.append(version);

		sb.append("}");

		throw new NoSuchEntryVersionException(sb.toString());
	}

	/**
	 * Returns the last style book entry version in the ordered set where groupId = &#63; and defaultStyleBookEntry = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry version, or <code>null</code> if a matching style book entry version could not be found
	 */
	@Override
	public StyleBookEntryVersion fetchByG_D_Version_Last(
		long groupId, boolean defaultStyleBookEntry, int version,
		OrderByComparator<StyleBookEntryVersion> orderByComparator) {

		int count = countByG_D_Version(groupId, defaultStyleBookEntry, version);

		if (count == 0) {
			return null;
		}

		List<StyleBookEntryVersion> list = findByG_D_Version(
			groupId, defaultStyleBookEntry, version, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the style book entry versions before and after the current style book entry version in the ordered set where groupId = &#63; and defaultStyleBookEntry = &#63; and version = &#63;.
	 *
	 * @param styleBookEntryVersionId the primary key of the current style book entry version
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next style book entry version
	 * @throws NoSuchEntryVersionException if a style book entry version with the primary key could not be found
	 */
	@Override
	public StyleBookEntryVersion[] findByG_D_Version_PrevAndNext(
			long styleBookEntryVersionId, long groupId,
			boolean defaultStyleBookEntry, int version,
			OrderByComparator<StyleBookEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		StyleBookEntryVersion styleBookEntryVersion = findByPrimaryKey(
			styleBookEntryVersionId);

		Session session = null;

		try {
			session = openSession();

			StyleBookEntryVersion[] array = new StyleBookEntryVersionImpl[3];

			array[0] = getByG_D_Version_PrevAndNext(
				session, styleBookEntryVersion, groupId, defaultStyleBookEntry,
				version, orderByComparator, true);

			array[1] = styleBookEntryVersion;

			array[2] = getByG_D_Version_PrevAndNext(
				session, styleBookEntryVersion, groupId, defaultStyleBookEntry,
				version, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected StyleBookEntryVersion getByG_D_Version_PrevAndNext(
		Session session, StyleBookEntryVersion styleBookEntryVersion,
		long groupId, boolean defaultStyleBookEntry, int version,
		OrderByComparator<StyleBookEntryVersion> orderByComparator,
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

		sb.append(_SQL_SELECT_STYLEBOOKENTRYVERSION_WHERE);

		sb.append(_FINDER_COLUMN_G_D_VERSION_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_D_VERSION_DEFAULTSTYLEBOOKENTRY_2);

		sb.append(_FINDER_COLUMN_G_D_VERSION_VERSION_2);

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
			sb.append(StyleBookEntryVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(defaultStyleBookEntry);

		queryPos.add(version);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						styleBookEntryVersion)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<StyleBookEntryVersion> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the style book entry versions where groupId = &#63; and defaultStyleBookEntry = &#63; and version = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param version the version
	 */
	@Override
	public void removeByG_D_Version(
		long groupId, boolean defaultStyleBookEntry, int version) {

		for (StyleBookEntryVersion styleBookEntryVersion :
				findByG_D_Version(
					groupId, defaultStyleBookEntry, version, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(styleBookEntryVersion);
		}
	}

	/**
	 * Returns the number of style book entry versions where groupId = &#63; and defaultStyleBookEntry = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param version the version
	 * @return the number of matching style book entry versions
	 */
	@Override
	public int countByG_D_Version(
		long groupId, boolean defaultStyleBookEntry, int version) {

		FinderPath finderPath = _finderPathCountByG_D_Version;

		Object[] finderArgs = new Object[] {
			groupId, defaultStyleBookEntry, version
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_STYLEBOOKENTRYVERSION_WHERE);

			sb.append(_FINDER_COLUMN_G_D_VERSION_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_D_VERSION_DEFAULTSTYLEBOOKENTRY_2);

			sb.append(_FINDER_COLUMN_G_D_VERSION_VERSION_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(defaultStyleBookEntry);

				queryPos.add(version);

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

	private static final String _FINDER_COLUMN_G_D_VERSION_GROUPID_2 =
		"styleBookEntryVersion.groupId = ? AND ";

	private static final String
		_FINDER_COLUMN_G_D_VERSION_DEFAULTSTYLEBOOKENTRY_2 =
			"styleBookEntryVersion.defaultStyleBookEntry = ? AND ";

	private static final String _FINDER_COLUMN_G_D_VERSION_VERSION_2 =
		"styleBookEntryVersion.version = ?";

	private FinderPath _finderPathWithPaginationFindByG_LikeN;
	private FinderPath _finderPathWithoutPaginationFindByG_LikeN;
	private FinderPath _finderPathCountByG_LikeN;

	/**
	 * Returns all the style book entry versions where groupId = &#63; and name = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the matching style book entry versions
	 */
	@Override
	public List<StyleBookEntryVersion> findByG_LikeN(
		long groupId, String name) {

		return findByG_LikeN(
			groupId, name, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the style book entry versions where groupId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of style book entry versions
	 * @param end the upper bound of the range of style book entry versions (not inclusive)
	 * @return the range of matching style book entry versions
	 */
	@Override
	public List<StyleBookEntryVersion> findByG_LikeN(
		long groupId, String name, int start, int end) {

		return findByG_LikeN(groupId, name, start, end, null);
	}

	/**
	 * Returns an ordered range of all the style book entry versions where groupId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of style book entry versions
	 * @param end the upper bound of the range of style book entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching style book entry versions
	 */
	@Override
	public List<StyleBookEntryVersion> findByG_LikeN(
		long groupId, String name, int start, int end,
		OrderByComparator<StyleBookEntryVersion> orderByComparator) {

		return findByG_LikeN(
			groupId, name, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the style book entry versions where groupId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of style book entry versions
	 * @param end the upper bound of the range of style book entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching style book entry versions
	 */
	@Override
	public List<StyleBookEntryVersion> findByG_LikeN(
		long groupId, String name, int start, int end,
		OrderByComparator<StyleBookEntryVersion> orderByComparator,
		boolean useFinderCache) {

		name = Objects.toString(name, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByG_LikeN;
				finderArgs = new Object[] {groupId, name};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByG_LikeN;
			finderArgs = new Object[] {
				groupId, name, start, end, orderByComparator
			};
		}

		List<StyleBookEntryVersion> list = null;

		if (useFinderCache) {
			list = (List<StyleBookEntryVersion>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (StyleBookEntryVersion styleBookEntryVersion : list) {
					if ((groupId != styleBookEntryVersion.getGroupId()) ||
						!name.equals(styleBookEntryVersion.getName())) {

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

			sb.append(_SQL_SELECT_STYLEBOOKENTRYVERSION_WHERE);

			sb.append(_FINDER_COLUMN_G_LIKEN_GROUPID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_LIKEN_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_G_LIKEN_NAME_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(StyleBookEntryVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindName) {
					queryPos.add(name);
				}

				list = (List<StyleBookEntryVersion>)QueryUtil.list(
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
	 * Returns the first style book entry version in the ordered set where groupId = &#63; and name = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry version
	 * @throws NoSuchEntryVersionException if a matching style book entry version could not be found
	 */
	@Override
	public StyleBookEntryVersion findByG_LikeN_First(
			long groupId, String name,
			OrderByComparator<StyleBookEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		StyleBookEntryVersion styleBookEntryVersion = fetchByG_LikeN_First(
			groupId, name, orderByComparator);

		if (styleBookEntryVersion != null) {
			return styleBookEntryVersion;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", name=");
		sb.append(name);

		sb.append("}");

		throw new NoSuchEntryVersionException(sb.toString());
	}

	/**
	 * Returns the first style book entry version in the ordered set where groupId = &#63; and name = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry version, or <code>null</code> if a matching style book entry version could not be found
	 */
	@Override
	public StyleBookEntryVersion fetchByG_LikeN_First(
		long groupId, String name,
		OrderByComparator<StyleBookEntryVersion> orderByComparator) {

		List<StyleBookEntryVersion> list = findByG_LikeN(
			groupId, name, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last style book entry version in the ordered set where groupId = &#63; and name = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry version
	 * @throws NoSuchEntryVersionException if a matching style book entry version could not be found
	 */
	@Override
	public StyleBookEntryVersion findByG_LikeN_Last(
			long groupId, String name,
			OrderByComparator<StyleBookEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		StyleBookEntryVersion styleBookEntryVersion = fetchByG_LikeN_Last(
			groupId, name, orderByComparator);

		if (styleBookEntryVersion != null) {
			return styleBookEntryVersion;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", name=");
		sb.append(name);

		sb.append("}");

		throw new NoSuchEntryVersionException(sb.toString());
	}

	/**
	 * Returns the last style book entry version in the ordered set where groupId = &#63; and name = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry version, or <code>null</code> if a matching style book entry version could not be found
	 */
	@Override
	public StyleBookEntryVersion fetchByG_LikeN_Last(
		long groupId, String name,
		OrderByComparator<StyleBookEntryVersion> orderByComparator) {

		int count = countByG_LikeN(groupId, name);

		if (count == 0) {
			return null;
		}

		List<StyleBookEntryVersion> list = findByG_LikeN(
			groupId, name, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the style book entry versions before and after the current style book entry version in the ordered set where groupId = &#63; and name = &#63;.
	 *
	 * @param styleBookEntryVersionId the primary key of the current style book entry version
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next style book entry version
	 * @throws NoSuchEntryVersionException if a style book entry version with the primary key could not be found
	 */
	@Override
	public StyleBookEntryVersion[] findByG_LikeN_PrevAndNext(
			long styleBookEntryVersionId, long groupId, String name,
			OrderByComparator<StyleBookEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		name = Objects.toString(name, "");

		StyleBookEntryVersion styleBookEntryVersion = findByPrimaryKey(
			styleBookEntryVersionId);

		Session session = null;

		try {
			session = openSession();

			StyleBookEntryVersion[] array = new StyleBookEntryVersionImpl[3];

			array[0] = getByG_LikeN_PrevAndNext(
				session, styleBookEntryVersion, groupId, name,
				orderByComparator, true);

			array[1] = styleBookEntryVersion;

			array[2] = getByG_LikeN_PrevAndNext(
				session, styleBookEntryVersion, groupId, name,
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

	protected StyleBookEntryVersion getByG_LikeN_PrevAndNext(
		Session session, StyleBookEntryVersion styleBookEntryVersion,
		long groupId, String name,
		OrderByComparator<StyleBookEntryVersion> orderByComparator,
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

		sb.append(_SQL_SELECT_STYLEBOOKENTRYVERSION_WHERE);

		sb.append(_FINDER_COLUMN_G_LIKEN_GROUPID_2);

		boolean bindName = false;

		if (name.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_LIKEN_NAME_3);
		}
		else {
			bindName = true;

			sb.append(_FINDER_COLUMN_G_LIKEN_NAME_2);
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
			sb.append(StyleBookEntryVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		if (bindName) {
			queryPos.add(name);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						styleBookEntryVersion)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<StyleBookEntryVersion> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the style book entry versions where groupId = &#63; and name = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 */
	@Override
	public void removeByG_LikeN(long groupId, String name) {
		for (StyleBookEntryVersion styleBookEntryVersion :
				findByG_LikeN(
					groupId, name, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(styleBookEntryVersion);
		}
	}

	/**
	 * Returns the number of style book entry versions where groupId = &#63; and name = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the number of matching style book entry versions
	 */
	@Override
	public int countByG_LikeN(long groupId, String name) {
		name = Objects.toString(name, "");

		FinderPath finderPath = _finderPathCountByG_LikeN;

		Object[] finderArgs = new Object[] {groupId, name};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_STYLEBOOKENTRYVERSION_WHERE);

			sb.append(_FINDER_COLUMN_G_LIKEN_GROUPID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_LIKEN_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_G_LIKEN_NAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

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

	private static final String _FINDER_COLUMN_G_LIKEN_GROUPID_2 =
		"styleBookEntryVersion.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_LIKEN_NAME_2 =
		"styleBookEntryVersion.name = ?";

	private static final String _FINDER_COLUMN_G_LIKEN_NAME_3 =
		"(styleBookEntryVersion.name IS NULL OR styleBookEntryVersion.name = '')";

	private FinderPath _finderPathWithPaginationFindByG_LikeN_Version;
	private FinderPath _finderPathWithoutPaginationFindByG_LikeN_Version;
	private FinderPath _finderPathCountByG_LikeN_Version;

	/**
	 * Returns all the style book entry versions where groupId = &#63; and name = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param version the version
	 * @return the matching style book entry versions
	 */
	@Override
	public List<StyleBookEntryVersion> findByG_LikeN_Version(
		long groupId, String name, int version) {

		return findByG_LikeN_Version(
			groupId, name, version, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the style book entry versions where groupId = &#63; and name = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param version the version
	 * @param start the lower bound of the range of style book entry versions
	 * @param end the upper bound of the range of style book entry versions (not inclusive)
	 * @return the range of matching style book entry versions
	 */
	@Override
	public List<StyleBookEntryVersion> findByG_LikeN_Version(
		long groupId, String name, int version, int start, int end) {

		return findByG_LikeN_Version(groupId, name, version, start, end, null);
	}

	/**
	 * Returns an ordered range of all the style book entry versions where groupId = &#63; and name = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param version the version
	 * @param start the lower bound of the range of style book entry versions
	 * @param end the upper bound of the range of style book entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching style book entry versions
	 */
	@Override
	public List<StyleBookEntryVersion> findByG_LikeN_Version(
		long groupId, String name, int version, int start, int end,
		OrderByComparator<StyleBookEntryVersion> orderByComparator) {

		return findByG_LikeN_Version(
			groupId, name, version, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the style book entry versions where groupId = &#63; and name = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param version the version
	 * @param start the lower bound of the range of style book entry versions
	 * @param end the upper bound of the range of style book entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching style book entry versions
	 */
	@Override
	public List<StyleBookEntryVersion> findByG_LikeN_Version(
		long groupId, String name, int version, int start, int end,
		OrderByComparator<StyleBookEntryVersion> orderByComparator,
		boolean useFinderCache) {

		name = Objects.toString(name, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByG_LikeN_Version;
				finderArgs = new Object[] {groupId, name, version};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByG_LikeN_Version;
			finderArgs = new Object[] {
				groupId, name, version, start, end, orderByComparator
			};
		}

		List<StyleBookEntryVersion> list = null;

		if (useFinderCache) {
			list = (List<StyleBookEntryVersion>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (StyleBookEntryVersion styleBookEntryVersion : list) {
					if ((groupId != styleBookEntryVersion.getGroupId()) ||
						!name.equals(styleBookEntryVersion.getName()) ||
						(version != styleBookEntryVersion.getVersion())) {

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

			sb.append(_SQL_SELECT_STYLEBOOKENTRYVERSION_WHERE);

			sb.append(_FINDER_COLUMN_G_LIKEN_VERSION_GROUPID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_LIKEN_VERSION_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_G_LIKEN_VERSION_NAME_2);
			}

			sb.append(_FINDER_COLUMN_G_LIKEN_VERSION_VERSION_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(StyleBookEntryVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindName) {
					queryPos.add(name);
				}

				queryPos.add(version);

				list = (List<StyleBookEntryVersion>)QueryUtil.list(
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
	 * Returns the first style book entry version in the ordered set where groupId = &#63; and name = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry version
	 * @throws NoSuchEntryVersionException if a matching style book entry version could not be found
	 */
	@Override
	public StyleBookEntryVersion findByG_LikeN_Version_First(
			long groupId, String name, int version,
			OrderByComparator<StyleBookEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		StyleBookEntryVersion styleBookEntryVersion =
			fetchByG_LikeN_Version_First(
				groupId, name, version, orderByComparator);

		if (styleBookEntryVersion != null) {
			return styleBookEntryVersion;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", name=");
		sb.append(name);

		sb.append(", version=");
		sb.append(version);

		sb.append("}");

		throw new NoSuchEntryVersionException(sb.toString());
	}

	/**
	 * Returns the first style book entry version in the ordered set where groupId = &#63; and name = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry version, or <code>null</code> if a matching style book entry version could not be found
	 */
	@Override
	public StyleBookEntryVersion fetchByG_LikeN_Version_First(
		long groupId, String name, int version,
		OrderByComparator<StyleBookEntryVersion> orderByComparator) {

		List<StyleBookEntryVersion> list = findByG_LikeN_Version(
			groupId, name, version, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last style book entry version in the ordered set where groupId = &#63; and name = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry version
	 * @throws NoSuchEntryVersionException if a matching style book entry version could not be found
	 */
	@Override
	public StyleBookEntryVersion findByG_LikeN_Version_Last(
			long groupId, String name, int version,
			OrderByComparator<StyleBookEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		StyleBookEntryVersion styleBookEntryVersion =
			fetchByG_LikeN_Version_Last(
				groupId, name, version, orderByComparator);

		if (styleBookEntryVersion != null) {
			return styleBookEntryVersion;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", name=");
		sb.append(name);

		sb.append(", version=");
		sb.append(version);

		sb.append("}");

		throw new NoSuchEntryVersionException(sb.toString());
	}

	/**
	 * Returns the last style book entry version in the ordered set where groupId = &#63; and name = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry version, or <code>null</code> if a matching style book entry version could not be found
	 */
	@Override
	public StyleBookEntryVersion fetchByG_LikeN_Version_Last(
		long groupId, String name, int version,
		OrderByComparator<StyleBookEntryVersion> orderByComparator) {

		int count = countByG_LikeN_Version(groupId, name, version);

		if (count == 0) {
			return null;
		}

		List<StyleBookEntryVersion> list = findByG_LikeN_Version(
			groupId, name, version, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the style book entry versions before and after the current style book entry version in the ordered set where groupId = &#63; and name = &#63; and version = &#63;.
	 *
	 * @param styleBookEntryVersionId the primary key of the current style book entry version
	 * @param groupId the group ID
	 * @param name the name
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next style book entry version
	 * @throws NoSuchEntryVersionException if a style book entry version with the primary key could not be found
	 */
	@Override
	public StyleBookEntryVersion[] findByG_LikeN_Version_PrevAndNext(
			long styleBookEntryVersionId, long groupId, String name,
			int version,
			OrderByComparator<StyleBookEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		name = Objects.toString(name, "");

		StyleBookEntryVersion styleBookEntryVersion = findByPrimaryKey(
			styleBookEntryVersionId);

		Session session = null;

		try {
			session = openSession();

			StyleBookEntryVersion[] array = new StyleBookEntryVersionImpl[3];

			array[0] = getByG_LikeN_Version_PrevAndNext(
				session, styleBookEntryVersion, groupId, name, version,
				orderByComparator, true);

			array[1] = styleBookEntryVersion;

			array[2] = getByG_LikeN_Version_PrevAndNext(
				session, styleBookEntryVersion, groupId, name, version,
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

	protected StyleBookEntryVersion getByG_LikeN_Version_PrevAndNext(
		Session session, StyleBookEntryVersion styleBookEntryVersion,
		long groupId, String name, int version,
		OrderByComparator<StyleBookEntryVersion> orderByComparator,
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

		sb.append(_SQL_SELECT_STYLEBOOKENTRYVERSION_WHERE);

		sb.append(_FINDER_COLUMN_G_LIKEN_VERSION_GROUPID_2);

		boolean bindName = false;

		if (name.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_LIKEN_VERSION_NAME_3);
		}
		else {
			bindName = true;

			sb.append(_FINDER_COLUMN_G_LIKEN_VERSION_NAME_2);
		}

		sb.append(_FINDER_COLUMN_G_LIKEN_VERSION_VERSION_2);

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
			sb.append(StyleBookEntryVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		if (bindName) {
			queryPos.add(name);
		}

		queryPos.add(version);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						styleBookEntryVersion)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<StyleBookEntryVersion> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the style book entry versions where groupId = &#63; and name = &#63; and version = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param version the version
	 */
	@Override
	public void removeByG_LikeN_Version(
		long groupId, String name, int version) {

		for (StyleBookEntryVersion styleBookEntryVersion :
				findByG_LikeN_Version(
					groupId, name, version, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(styleBookEntryVersion);
		}
	}

	/**
	 * Returns the number of style book entry versions where groupId = &#63; and name = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param version the version
	 * @return the number of matching style book entry versions
	 */
	@Override
	public int countByG_LikeN_Version(long groupId, String name, int version) {
		name = Objects.toString(name, "");

		FinderPath finderPath = _finderPathCountByG_LikeN_Version;

		Object[] finderArgs = new Object[] {groupId, name, version};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_STYLEBOOKENTRYVERSION_WHERE);

			sb.append(_FINDER_COLUMN_G_LIKEN_VERSION_GROUPID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_LIKEN_VERSION_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_G_LIKEN_VERSION_NAME_2);
			}

			sb.append(_FINDER_COLUMN_G_LIKEN_VERSION_VERSION_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindName) {
					queryPos.add(name);
				}

				queryPos.add(version);

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

	private static final String _FINDER_COLUMN_G_LIKEN_VERSION_GROUPID_2 =
		"styleBookEntryVersion.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_LIKEN_VERSION_NAME_2 =
		"styleBookEntryVersion.name = ? AND ";

	private static final String _FINDER_COLUMN_G_LIKEN_VERSION_NAME_3 =
		"(styleBookEntryVersion.name IS NULL OR styleBookEntryVersion.name = '') AND ";

	private static final String _FINDER_COLUMN_G_LIKEN_VERSION_VERSION_2 =
		"styleBookEntryVersion.version = ?";

	private FinderPath _finderPathWithPaginationFindByG_SBEK;
	private FinderPath _finderPathWithoutPaginationFindByG_SBEK;
	private FinderPath _finderPathCountByG_SBEK;

	/**
	 * Returns all the style book entry versions where groupId = &#63; and styleBookEntryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param styleBookEntryKey the style book entry key
	 * @return the matching style book entry versions
	 */
	@Override
	public List<StyleBookEntryVersion> findByG_SBEK(
		long groupId, String styleBookEntryKey) {

		return findByG_SBEK(
			groupId, styleBookEntryKey, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the style book entry versions where groupId = &#63; and styleBookEntryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param styleBookEntryKey the style book entry key
	 * @param start the lower bound of the range of style book entry versions
	 * @param end the upper bound of the range of style book entry versions (not inclusive)
	 * @return the range of matching style book entry versions
	 */
	@Override
	public List<StyleBookEntryVersion> findByG_SBEK(
		long groupId, String styleBookEntryKey, int start, int end) {

		return findByG_SBEK(groupId, styleBookEntryKey, start, end, null);
	}

	/**
	 * Returns an ordered range of all the style book entry versions where groupId = &#63; and styleBookEntryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param styleBookEntryKey the style book entry key
	 * @param start the lower bound of the range of style book entry versions
	 * @param end the upper bound of the range of style book entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching style book entry versions
	 */
	@Override
	public List<StyleBookEntryVersion> findByG_SBEK(
		long groupId, String styleBookEntryKey, int start, int end,
		OrderByComparator<StyleBookEntryVersion> orderByComparator) {

		return findByG_SBEK(
			groupId, styleBookEntryKey, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the style book entry versions where groupId = &#63; and styleBookEntryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param styleBookEntryKey the style book entry key
	 * @param start the lower bound of the range of style book entry versions
	 * @param end the upper bound of the range of style book entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching style book entry versions
	 */
	@Override
	public List<StyleBookEntryVersion> findByG_SBEK(
		long groupId, String styleBookEntryKey, int start, int end,
		OrderByComparator<StyleBookEntryVersion> orderByComparator,
		boolean useFinderCache) {

		styleBookEntryKey = Objects.toString(styleBookEntryKey, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByG_SBEK;
				finderArgs = new Object[] {groupId, styleBookEntryKey};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByG_SBEK;
			finderArgs = new Object[] {
				groupId, styleBookEntryKey, start, end, orderByComparator
			};
		}

		List<StyleBookEntryVersion> list = null;

		if (useFinderCache) {
			list = (List<StyleBookEntryVersion>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (StyleBookEntryVersion styleBookEntryVersion : list) {
					if ((groupId != styleBookEntryVersion.getGroupId()) ||
						!styleBookEntryKey.equals(
							styleBookEntryVersion.getStyleBookEntryKey())) {

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

			sb.append(_SQL_SELECT_STYLEBOOKENTRYVERSION_WHERE);

			sb.append(_FINDER_COLUMN_G_SBEK_GROUPID_2);

			boolean bindStyleBookEntryKey = false;

			if (styleBookEntryKey.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_SBEK_STYLEBOOKENTRYKEY_3);
			}
			else {
				bindStyleBookEntryKey = true;

				sb.append(_FINDER_COLUMN_G_SBEK_STYLEBOOKENTRYKEY_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(StyleBookEntryVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindStyleBookEntryKey) {
					queryPos.add(styleBookEntryKey);
				}

				list = (List<StyleBookEntryVersion>)QueryUtil.list(
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
	 * Returns the first style book entry version in the ordered set where groupId = &#63; and styleBookEntryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param styleBookEntryKey the style book entry key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry version
	 * @throws NoSuchEntryVersionException if a matching style book entry version could not be found
	 */
	@Override
	public StyleBookEntryVersion findByG_SBEK_First(
			long groupId, String styleBookEntryKey,
			OrderByComparator<StyleBookEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		StyleBookEntryVersion styleBookEntryVersion = fetchByG_SBEK_First(
			groupId, styleBookEntryKey, orderByComparator);

		if (styleBookEntryVersion != null) {
			return styleBookEntryVersion;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", styleBookEntryKey=");
		sb.append(styleBookEntryKey);

		sb.append("}");

		throw new NoSuchEntryVersionException(sb.toString());
	}

	/**
	 * Returns the first style book entry version in the ordered set where groupId = &#63; and styleBookEntryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param styleBookEntryKey the style book entry key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry version, or <code>null</code> if a matching style book entry version could not be found
	 */
	@Override
	public StyleBookEntryVersion fetchByG_SBEK_First(
		long groupId, String styleBookEntryKey,
		OrderByComparator<StyleBookEntryVersion> orderByComparator) {

		List<StyleBookEntryVersion> list = findByG_SBEK(
			groupId, styleBookEntryKey, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last style book entry version in the ordered set where groupId = &#63; and styleBookEntryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param styleBookEntryKey the style book entry key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry version
	 * @throws NoSuchEntryVersionException if a matching style book entry version could not be found
	 */
	@Override
	public StyleBookEntryVersion findByG_SBEK_Last(
			long groupId, String styleBookEntryKey,
			OrderByComparator<StyleBookEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		StyleBookEntryVersion styleBookEntryVersion = fetchByG_SBEK_Last(
			groupId, styleBookEntryKey, orderByComparator);

		if (styleBookEntryVersion != null) {
			return styleBookEntryVersion;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", styleBookEntryKey=");
		sb.append(styleBookEntryKey);

		sb.append("}");

		throw new NoSuchEntryVersionException(sb.toString());
	}

	/**
	 * Returns the last style book entry version in the ordered set where groupId = &#63; and styleBookEntryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param styleBookEntryKey the style book entry key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry version, or <code>null</code> if a matching style book entry version could not be found
	 */
	@Override
	public StyleBookEntryVersion fetchByG_SBEK_Last(
		long groupId, String styleBookEntryKey,
		OrderByComparator<StyleBookEntryVersion> orderByComparator) {

		int count = countByG_SBEK(groupId, styleBookEntryKey);

		if (count == 0) {
			return null;
		}

		List<StyleBookEntryVersion> list = findByG_SBEK(
			groupId, styleBookEntryKey, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the style book entry versions before and after the current style book entry version in the ordered set where groupId = &#63; and styleBookEntryKey = &#63;.
	 *
	 * @param styleBookEntryVersionId the primary key of the current style book entry version
	 * @param groupId the group ID
	 * @param styleBookEntryKey the style book entry key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next style book entry version
	 * @throws NoSuchEntryVersionException if a style book entry version with the primary key could not be found
	 */
	@Override
	public StyleBookEntryVersion[] findByG_SBEK_PrevAndNext(
			long styleBookEntryVersionId, long groupId,
			String styleBookEntryKey,
			OrderByComparator<StyleBookEntryVersion> orderByComparator)
		throws NoSuchEntryVersionException {

		styleBookEntryKey = Objects.toString(styleBookEntryKey, "");

		StyleBookEntryVersion styleBookEntryVersion = findByPrimaryKey(
			styleBookEntryVersionId);

		Session session = null;

		try {
			session = openSession();

			StyleBookEntryVersion[] array = new StyleBookEntryVersionImpl[3];

			array[0] = getByG_SBEK_PrevAndNext(
				session, styleBookEntryVersion, groupId, styleBookEntryKey,
				orderByComparator, true);

			array[1] = styleBookEntryVersion;

			array[2] = getByG_SBEK_PrevAndNext(
				session, styleBookEntryVersion, groupId, styleBookEntryKey,
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

	protected StyleBookEntryVersion getByG_SBEK_PrevAndNext(
		Session session, StyleBookEntryVersion styleBookEntryVersion,
		long groupId, String styleBookEntryKey,
		OrderByComparator<StyleBookEntryVersion> orderByComparator,
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

		sb.append(_SQL_SELECT_STYLEBOOKENTRYVERSION_WHERE);

		sb.append(_FINDER_COLUMN_G_SBEK_GROUPID_2);

		boolean bindStyleBookEntryKey = false;

		if (styleBookEntryKey.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_SBEK_STYLEBOOKENTRYKEY_3);
		}
		else {
			bindStyleBookEntryKey = true;

			sb.append(_FINDER_COLUMN_G_SBEK_STYLEBOOKENTRYKEY_2);
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
			sb.append(StyleBookEntryVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		if (bindStyleBookEntryKey) {
			queryPos.add(styleBookEntryKey);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						styleBookEntryVersion)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<StyleBookEntryVersion> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the style book entry versions where groupId = &#63; and styleBookEntryKey = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param styleBookEntryKey the style book entry key
	 */
	@Override
	public void removeByG_SBEK(long groupId, String styleBookEntryKey) {
		for (StyleBookEntryVersion styleBookEntryVersion :
				findByG_SBEK(
					groupId, styleBookEntryKey, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(styleBookEntryVersion);
		}
	}

	/**
	 * Returns the number of style book entry versions where groupId = &#63; and styleBookEntryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param styleBookEntryKey the style book entry key
	 * @return the number of matching style book entry versions
	 */
	@Override
	public int countByG_SBEK(long groupId, String styleBookEntryKey) {
		styleBookEntryKey = Objects.toString(styleBookEntryKey, "");

		FinderPath finderPath = _finderPathCountByG_SBEK;

		Object[] finderArgs = new Object[] {groupId, styleBookEntryKey};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_STYLEBOOKENTRYVERSION_WHERE);

			sb.append(_FINDER_COLUMN_G_SBEK_GROUPID_2);

			boolean bindStyleBookEntryKey = false;

			if (styleBookEntryKey.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_SBEK_STYLEBOOKENTRYKEY_3);
			}
			else {
				bindStyleBookEntryKey = true;

				sb.append(_FINDER_COLUMN_G_SBEK_STYLEBOOKENTRYKEY_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindStyleBookEntryKey) {
					queryPos.add(styleBookEntryKey);
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

	private static final String _FINDER_COLUMN_G_SBEK_GROUPID_2 =
		"styleBookEntryVersion.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_SBEK_STYLEBOOKENTRYKEY_2 =
		"styleBookEntryVersion.styleBookEntryKey = ?";

	private static final String _FINDER_COLUMN_G_SBEK_STYLEBOOKENTRYKEY_3 =
		"(styleBookEntryVersion.styleBookEntryKey IS NULL OR styleBookEntryVersion.styleBookEntryKey = '')";

	private FinderPath _finderPathFetchByG_SBEK_Version;
	private FinderPath _finderPathCountByG_SBEK_Version;

	/**
	 * Returns the style book entry version where groupId = &#63; and styleBookEntryKey = &#63; and version = &#63; or throws a <code>NoSuchEntryVersionException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param styleBookEntryKey the style book entry key
	 * @param version the version
	 * @return the matching style book entry version
	 * @throws NoSuchEntryVersionException if a matching style book entry version could not be found
	 */
	@Override
	public StyleBookEntryVersion findByG_SBEK_Version(
			long groupId, String styleBookEntryKey, int version)
		throws NoSuchEntryVersionException {

		StyleBookEntryVersion styleBookEntryVersion = fetchByG_SBEK_Version(
			groupId, styleBookEntryKey, version);

		if (styleBookEntryVersion == null) {
			StringBundler sb = new StringBundler(8);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("groupId=");
			sb.append(groupId);

			sb.append(", styleBookEntryKey=");
			sb.append(styleBookEntryKey);

			sb.append(", version=");
			sb.append(version);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchEntryVersionException(sb.toString());
		}

		return styleBookEntryVersion;
	}

	/**
	 * Returns the style book entry version where groupId = &#63; and styleBookEntryKey = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param styleBookEntryKey the style book entry key
	 * @param version the version
	 * @return the matching style book entry version, or <code>null</code> if a matching style book entry version could not be found
	 */
	@Override
	public StyleBookEntryVersion fetchByG_SBEK_Version(
		long groupId, String styleBookEntryKey, int version) {

		return fetchByG_SBEK_Version(groupId, styleBookEntryKey, version, true);
	}

	/**
	 * Returns the style book entry version where groupId = &#63; and styleBookEntryKey = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param styleBookEntryKey the style book entry key
	 * @param version the version
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching style book entry version, or <code>null</code> if a matching style book entry version could not be found
	 */
	@Override
	public StyleBookEntryVersion fetchByG_SBEK_Version(
		long groupId, String styleBookEntryKey, int version,
		boolean useFinderCache) {

		styleBookEntryKey = Objects.toString(styleBookEntryKey, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {groupId, styleBookEntryKey, version};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByG_SBEK_Version, finderArgs, this);
		}

		if (result instanceof StyleBookEntryVersion) {
			StyleBookEntryVersion styleBookEntryVersion =
				(StyleBookEntryVersion)result;

			if ((groupId != styleBookEntryVersion.getGroupId()) ||
				!Objects.equals(
					styleBookEntryKey,
					styleBookEntryVersion.getStyleBookEntryKey()) ||
				(version != styleBookEntryVersion.getVersion())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_SELECT_STYLEBOOKENTRYVERSION_WHERE);

			sb.append(_FINDER_COLUMN_G_SBEK_VERSION_GROUPID_2);

			boolean bindStyleBookEntryKey = false;

			if (styleBookEntryKey.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_SBEK_VERSION_STYLEBOOKENTRYKEY_3);
			}
			else {
				bindStyleBookEntryKey = true;

				sb.append(_FINDER_COLUMN_G_SBEK_VERSION_STYLEBOOKENTRYKEY_2);
			}

			sb.append(_FINDER_COLUMN_G_SBEK_VERSION_VERSION_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindStyleBookEntryKey) {
					queryPos.add(styleBookEntryKey);
				}

				queryPos.add(version);

				List<StyleBookEntryVersion> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByG_SBEK_Version, finderArgs, list);
					}
				}
				else {
					StyleBookEntryVersion styleBookEntryVersion = list.get(0);

					result = styleBookEntryVersion;

					cacheResult(styleBookEntryVersion);
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
			return (StyleBookEntryVersion)result;
		}
	}

	/**
	 * Removes the style book entry version where groupId = &#63; and styleBookEntryKey = &#63; and version = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param styleBookEntryKey the style book entry key
	 * @param version the version
	 * @return the style book entry version that was removed
	 */
	@Override
	public StyleBookEntryVersion removeByG_SBEK_Version(
			long groupId, String styleBookEntryKey, int version)
		throws NoSuchEntryVersionException {

		StyleBookEntryVersion styleBookEntryVersion = findByG_SBEK_Version(
			groupId, styleBookEntryKey, version);

		return remove(styleBookEntryVersion);
	}

	/**
	 * Returns the number of style book entry versions where groupId = &#63; and styleBookEntryKey = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param styleBookEntryKey the style book entry key
	 * @param version the version
	 * @return the number of matching style book entry versions
	 */
	@Override
	public int countByG_SBEK_Version(
		long groupId, String styleBookEntryKey, int version) {

		styleBookEntryKey = Objects.toString(styleBookEntryKey, "");

		FinderPath finderPath = _finderPathCountByG_SBEK_Version;

		Object[] finderArgs = new Object[] {
			groupId, styleBookEntryKey, version
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_STYLEBOOKENTRYVERSION_WHERE);

			sb.append(_FINDER_COLUMN_G_SBEK_VERSION_GROUPID_2);

			boolean bindStyleBookEntryKey = false;

			if (styleBookEntryKey.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_SBEK_VERSION_STYLEBOOKENTRYKEY_3);
			}
			else {
				bindStyleBookEntryKey = true;

				sb.append(_FINDER_COLUMN_G_SBEK_VERSION_STYLEBOOKENTRYKEY_2);
			}

			sb.append(_FINDER_COLUMN_G_SBEK_VERSION_VERSION_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindStyleBookEntryKey) {
					queryPos.add(styleBookEntryKey);
				}

				queryPos.add(version);

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

	private static final String _FINDER_COLUMN_G_SBEK_VERSION_GROUPID_2 =
		"styleBookEntryVersion.groupId = ? AND ";

	private static final String
		_FINDER_COLUMN_G_SBEK_VERSION_STYLEBOOKENTRYKEY_2 =
			"styleBookEntryVersion.styleBookEntryKey = ? AND ";

	private static final String
		_FINDER_COLUMN_G_SBEK_VERSION_STYLEBOOKENTRYKEY_3 =
			"(styleBookEntryVersion.styleBookEntryKey IS NULL OR styleBookEntryVersion.styleBookEntryKey = '') AND ";

	private static final String _FINDER_COLUMN_G_SBEK_VERSION_VERSION_2 =
		"styleBookEntryVersion.version = ?";

	public StyleBookEntryVersionPersistenceImpl() {
		setModelClass(StyleBookEntryVersion.class);

		setModelImplClass(StyleBookEntryVersionImpl.class);
		setModelPKClass(long.class);

		setTable(StyleBookEntryVersionTable.INSTANCE);
	}

	/**
	 * Caches the style book entry version in the entity cache if it is enabled.
	 *
	 * @param styleBookEntryVersion the style book entry version
	 */
	@Override
	public void cacheResult(StyleBookEntryVersion styleBookEntryVersion) {
		entityCache.putResult(
			StyleBookEntryVersionImpl.class,
			styleBookEntryVersion.getPrimaryKey(), styleBookEntryVersion);

		finderCache.putResult(
			_finderPathFetchByStyleBookEntryId_Version,
			new Object[] {
				styleBookEntryVersion.getStyleBookEntryId(),
				styleBookEntryVersion.getVersion()
			},
			styleBookEntryVersion);

		finderCache.putResult(
			_finderPathFetchByG_SBEK_Version,
			new Object[] {
				styleBookEntryVersion.getGroupId(),
				styleBookEntryVersion.getStyleBookEntryKey(),
				styleBookEntryVersion.getVersion()
			},
			styleBookEntryVersion);
	}

	/**
	 * Caches the style book entry versions in the entity cache if it is enabled.
	 *
	 * @param styleBookEntryVersions the style book entry versions
	 */
	@Override
	public void cacheResult(
		List<StyleBookEntryVersion> styleBookEntryVersions) {

		for (StyleBookEntryVersion styleBookEntryVersion :
				styleBookEntryVersions) {

			if (entityCache.getResult(
					StyleBookEntryVersionImpl.class,
					styleBookEntryVersion.getPrimaryKey()) == null) {

				cacheResult(styleBookEntryVersion);
			}
		}
	}

	/**
	 * Clears the cache for all style book entry versions.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(StyleBookEntryVersionImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the style book entry version.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(StyleBookEntryVersion styleBookEntryVersion) {
		entityCache.removeResult(
			StyleBookEntryVersionImpl.class,
			styleBookEntryVersion.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(StyleBookEntryVersionModelImpl)styleBookEntryVersion, true);
	}

	@Override
	public void clearCache(List<StyleBookEntryVersion> styleBookEntryVersions) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (StyleBookEntryVersion styleBookEntryVersion :
				styleBookEntryVersions) {

			entityCache.removeResult(
				StyleBookEntryVersionImpl.class,
				styleBookEntryVersion.getPrimaryKey());

			clearUniqueFindersCache(
				(StyleBookEntryVersionModelImpl)styleBookEntryVersion, true);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				StyleBookEntryVersionImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		StyleBookEntryVersionModelImpl styleBookEntryVersionModelImpl) {

		Object[] args = new Object[] {
			styleBookEntryVersionModelImpl.getStyleBookEntryId(),
			styleBookEntryVersionModelImpl.getVersion()
		};

		finderCache.putResult(
			_finderPathCountByStyleBookEntryId_Version, args, Long.valueOf(1),
			false);
		finderCache.putResult(
			_finderPathFetchByStyleBookEntryId_Version, args,
			styleBookEntryVersionModelImpl, false);

		args = new Object[] {
			styleBookEntryVersionModelImpl.getGroupId(),
			styleBookEntryVersionModelImpl.getStyleBookEntryKey(),
			styleBookEntryVersionModelImpl.getVersion()
		};

		finderCache.putResult(
			_finderPathCountByG_SBEK_Version, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByG_SBEK_Version, args,
			styleBookEntryVersionModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		StyleBookEntryVersionModelImpl styleBookEntryVersionModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				styleBookEntryVersionModelImpl.getStyleBookEntryId(),
				styleBookEntryVersionModelImpl.getVersion()
			};

			finderCache.removeResult(
				_finderPathCountByStyleBookEntryId_Version, args);
			finderCache.removeResult(
				_finderPathFetchByStyleBookEntryId_Version, args);
		}

		if ((styleBookEntryVersionModelImpl.getColumnBitmask() &
			 _finderPathFetchByStyleBookEntryId_Version.getColumnBitmask()) !=
				 0) {

			Object[] args = new Object[] {
				styleBookEntryVersionModelImpl.getColumnOriginalValue(
					"styleBookEntryId"),
				styleBookEntryVersionModelImpl.getColumnOriginalValue("version")
			};

			finderCache.removeResult(
				_finderPathCountByStyleBookEntryId_Version, args);
			finderCache.removeResult(
				_finderPathFetchByStyleBookEntryId_Version, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
				styleBookEntryVersionModelImpl.getGroupId(),
				styleBookEntryVersionModelImpl.getStyleBookEntryKey(),
				styleBookEntryVersionModelImpl.getVersion()
			};

			finderCache.removeResult(_finderPathCountByG_SBEK_Version, args);
			finderCache.removeResult(_finderPathFetchByG_SBEK_Version, args);
		}

		if ((styleBookEntryVersionModelImpl.getColumnBitmask() &
			 _finderPathFetchByG_SBEK_Version.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				styleBookEntryVersionModelImpl.getColumnOriginalValue(
					"groupId"),
				styleBookEntryVersionModelImpl.getColumnOriginalValue(
					"styleBookEntryKey"),
				styleBookEntryVersionModelImpl.getColumnOriginalValue("version")
			};

			finderCache.removeResult(_finderPathCountByG_SBEK_Version, args);
			finderCache.removeResult(_finderPathFetchByG_SBEK_Version, args);
		}
	}

	/**
	 * Creates a new style book entry version with the primary key. Does not add the style book entry version to the database.
	 *
	 * @param styleBookEntryVersionId the primary key for the new style book entry version
	 * @return the new style book entry version
	 */
	@Override
	public StyleBookEntryVersion create(long styleBookEntryVersionId) {
		StyleBookEntryVersion styleBookEntryVersion =
			new StyleBookEntryVersionImpl();

		styleBookEntryVersion.setNew(true);
		styleBookEntryVersion.setPrimaryKey(styleBookEntryVersionId);

		styleBookEntryVersion.setCompanyId(CompanyThreadLocal.getCompanyId());

		return styleBookEntryVersion;
	}

	/**
	 * Removes the style book entry version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param styleBookEntryVersionId the primary key of the style book entry version
	 * @return the style book entry version that was removed
	 * @throws NoSuchEntryVersionException if a style book entry version with the primary key could not be found
	 */
	@Override
	public StyleBookEntryVersion remove(long styleBookEntryVersionId)
		throws NoSuchEntryVersionException {

		return remove((Serializable)styleBookEntryVersionId);
	}

	/**
	 * Removes the style book entry version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the style book entry version
	 * @return the style book entry version that was removed
	 * @throws NoSuchEntryVersionException if a style book entry version with the primary key could not be found
	 */
	@Override
	public StyleBookEntryVersion remove(Serializable primaryKey)
		throws NoSuchEntryVersionException {

		Session session = null;

		try {
			session = openSession();

			StyleBookEntryVersion styleBookEntryVersion =
				(StyleBookEntryVersion)session.get(
					StyleBookEntryVersionImpl.class, primaryKey);

			if (styleBookEntryVersion == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchEntryVersionException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(styleBookEntryVersion);
		}
		catch (NoSuchEntryVersionException noSuchEntityException) {
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
	protected StyleBookEntryVersion removeImpl(
		StyleBookEntryVersion styleBookEntryVersion) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(styleBookEntryVersion)) {
				styleBookEntryVersion = (StyleBookEntryVersion)session.get(
					StyleBookEntryVersionImpl.class,
					styleBookEntryVersion.getPrimaryKeyObj());
			}

			if (styleBookEntryVersion != null) {
				session.delete(styleBookEntryVersion);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (styleBookEntryVersion != null) {
			clearCache(styleBookEntryVersion);
		}

		return styleBookEntryVersion;
	}

	@Override
	public StyleBookEntryVersion updateImpl(
		StyleBookEntryVersion styleBookEntryVersion) {

		boolean isNew = styleBookEntryVersion.isNew();

		if (!(styleBookEntryVersion instanceof
				StyleBookEntryVersionModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(styleBookEntryVersion.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					styleBookEntryVersion);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in styleBookEntryVersion proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom StyleBookEntryVersion implementation " +
					styleBookEntryVersion.getClass());
		}

		StyleBookEntryVersionModelImpl styleBookEntryVersionModelImpl =
			(StyleBookEntryVersionModelImpl)styleBookEntryVersion;

		Session session = null;

		try {
			session = openSession();

			if (styleBookEntryVersion.isNew()) {
				session.save(styleBookEntryVersion);

				styleBookEntryVersion.setNew(false);
			}
			else {
				throw new IllegalArgumentException(
					"StyleBookEntryVersion is read only, create a new version instead");
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
				styleBookEntryVersionModelImpl.getStyleBookEntryId()
			};

			finderCache.removeResult(_finderPathCountByStyleBookEntryId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByStyleBookEntryId, args);

			args = new Object[] {styleBookEntryVersionModelImpl.getGroupId()};

			finderCache.removeResult(_finderPathCountByGroupId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByGroupId, args);

			args = new Object[] {
				styleBookEntryVersionModelImpl.getGroupId(),
				styleBookEntryVersionModelImpl.getVersion()
			};

			finderCache.removeResult(_finderPathCountByGroupId_Version, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByGroupId_Version, args);

			args = new Object[] {
				styleBookEntryVersionModelImpl.getGroupId(),
				styleBookEntryVersionModelImpl.isDefaultStyleBookEntry()
			};

			finderCache.removeResult(_finderPathCountByG_D, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByG_D, args);

			args = new Object[] {
				styleBookEntryVersionModelImpl.getGroupId(),
				styleBookEntryVersionModelImpl.isDefaultStyleBookEntry(),
				styleBookEntryVersionModelImpl.getVersion()
			};

			finderCache.removeResult(_finderPathCountByG_D_Version, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByG_D_Version, args);

			args = new Object[] {
				styleBookEntryVersionModelImpl.getGroupId(),
				styleBookEntryVersionModelImpl.getName()
			};

			finderCache.removeResult(_finderPathCountByG_LikeN, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByG_LikeN, args);

			args = new Object[] {
				styleBookEntryVersionModelImpl.getGroupId(),
				styleBookEntryVersionModelImpl.getName(),
				styleBookEntryVersionModelImpl.getVersion()
			};

			finderCache.removeResult(_finderPathCountByG_LikeN_Version, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByG_LikeN_Version, args);

			args = new Object[] {
				styleBookEntryVersionModelImpl.getGroupId(),
				styleBookEntryVersionModelImpl.getStyleBookEntryKey()
			};

			finderCache.removeResult(_finderPathCountByG_SBEK, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByG_SBEK, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((styleBookEntryVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByStyleBookEntryId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					styleBookEntryVersionModelImpl.getColumnOriginalValue(
						"styleBookEntryId")
				};

				finderCache.removeResult(
					_finderPathCountByStyleBookEntryId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByStyleBookEntryId, args);

				args = new Object[] {
					styleBookEntryVersionModelImpl.getStyleBookEntryId()
				};

				finderCache.removeResult(
					_finderPathCountByStyleBookEntryId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByStyleBookEntryId, args);
			}

			if ((styleBookEntryVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByGroupId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					styleBookEntryVersionModelImpl.getColumnOriginalValue(
						"groupId")
				};

				finderCache.removeResult(_finderPathCountByGroupId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByGroupId, args);

				args = new Object[] {
					styleBookEntryVersionModelImpl.getGroupId()
				};

				finderCache.removeResult(_finderPathCountByGroupId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByGroupId, args);
			}

			if ((styleBookEntryVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByGroupId_Version.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					styleBookEntryVersionModelImpl.getColumnOriginalValue(
						"groupId"),
					styleBookEntryVersionModelImpl.getColumnOriginalValue(
						"version")
				};

				finderCache.removeResult(
					_finderPathCountByGroupId_Version, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByGroupId_Version, args);

				args = new Object[] {
					styleBookEntryVersionModelImpl.getGroupId(),
					styleBookEntryVersionModelImpl.getVersion()
				};

				finderCache.removeResult(
					_finderPathCountByGroupId_Version, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByGroupId_Version, args);
			}

			if ((styleBookEntryVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_D.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					styleBookEntryVersionModelImpl.getColumnOriginalValue(
						"groupId"),
					styleBookEntryVersionModelImpl.getColumnOriginalValue(
						"defaultStyleBookEntry")
				};

				finderCache.removeResult(_finderPathCountByG_D, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_D, args);

				args = new Object[] {
					styleBookEntryVersionModelImpl.getGroupId(),
					styleBookEntryVersionModelImpl.isDefaultStyleBookEntry()
				};

				finderCache.removeResult(_finderPathCountByG_D, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_D, args);
			}

			if ((styleBookEntryVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_D_Version.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					styleBookEntryVersionModelImpl.getColumnOriginalValue(
						"groupId"),
					styleBookEntryVersionModelImpl.getColumnOriginalValue(
						"defaultStyleBookEntry"),
					styleBookEntryVersionModelImpl.getColumnOriginalValue(
						"version")
				};

				finderCache.removeResult(_finderPathCountByG_D_Version, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_D_Version, args);

				args = new Object[] {
					styleBookEntryVersionModelImpl.getGroupId(),
					styleBookEntryVersionModelImpl.isDefaultStyleBookEntry(),
					styleBookEntryVersionModelImpl.getVersion()
				};

				finderCache.removeResult(_finderPathCountByG_D_Version, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_D_Version, args);
			}

			if ((styleBookEntryVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_LikeN.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					styleBookEntryVersionModelImpl.getColumnOriginalValue(
						"groupId"),
					styleBookEntryVersionModelImpl.getColumnOriginalValue(
						"name")
				};

				finderCache.removeResult(_finderPathCountByG_LikeN, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_LikeN, args);

				args = new Object[] {
					styleBookEntryVersionModelImpl.getGroupId(),
					styleBookEntryVersionModelImpl.getName()
				};

				finderCache.removeResult(_finderPathCountByG_LikeN, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_LikeN, args);
			}

			if ((styleBookEntryVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_LikeN_Version.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					styleBookEntryVersionModelImpl.getColumnOriginalValue(
						"groupId"),
					styleBookEntryVersionModelImpl.getColumnOriginalValue(
						"name"),
					styleBookEntryVersionModelImpl.getColumnOriginalValue(
						"version")
				};

				finderCache.removeResult(
					_finderPathCountByG_LikeN_Version, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_LikeN_Version, args);

				args = new Object[] {
					styleBookEntryVersionModelImpl.getGroupId(),
					styleBookEntryVersionModelImpl.getName(),
					styleBookEntryVersionModelImpl.getVersion()
				};

				finderCache.removeResult(
					_finderPathCountByG_LikeN_Version, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_LikeN_Version, args);
			}

			if ((styleBookEntryVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_SBEK.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					styleBookEntryVersionModelImpl.getColumnOriginalValue(
						"groupId"),
					styleBookEntryVersionModelImpl.getColumnOriginalValue(
						"styleBookEntryKey")
				};

				finderCache.removeResult(_finderPathCountByG_SBEK, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_SBEK, args);

				args = new Object[] {
					styleBookEntryVersionModelImpl.getGroupId(),
					styleBookEntryVersionModelImpl.getStyleBookEntryKey()
				};

				finderCache.removeResult(_finderPathCountByG_SBEK, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_SBEK, args);
			}
		}

		entityCache.putResult(
			StyleBookEntryVersionImpl.class,
			styleBookEntryVersion.getPrimaryKey(), styleBookEntryVersion,
			false);

		clearUniqueFindersCache(styleBookEntryVersionModelImpl, false);
		cacheUniqueFindersCache(styleBookEntryVersionModelImpl);

		styleBookEntryVersion.resetOriginalValues();

		return styleBookEntryVersion;
	}

	/**
	 * Returns the style book entry version with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the style book entry version
	 * @return the style book entry version
	 * @throws NoSuchEntryVersionException if a style book entry version with the primary key could not be found
	 */
	@Override
	public StyleBookEntryVersion findByPrimaryKey(Serializable primaryKey)
		throws NoSuchEntryVersionException {

		StyleBookEntryVersion styleBookEntryVersion = fetchByPrimaryKey(
			primaryKey);

		if (styleBookEntryVersion == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchEntryVersionException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return styleBookEntryVersion;
	}

	/**
	 * Returns the style book entry version with the primary key or throws a <code>NoSuchEntryVersionException</code> if it could not be found.
	 *
	 * @param styleBookEntryVersionId the primary key of the style book entry version
	 * @return the style book entry version
	 * @throws NoSuchEntryVersionException if a style book entry version with the primary key could not be found
	 */
	@Override
	public StyleBookEntryVersion findByPrimaryKey(long styleBookEntryVersionId)
		throws NoSuchEntryVersionException {

		return findByPrimaryKey((Serializable)styleBookEntryVersionId);
	}

	/**
	 * Returns the style book entry version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param styleBookEntryVersionId the primary key of the style book entry version
	 * @return the style book entry version, or <code>null</code> if a style book entry version with the primary key could not be found
	 */
	@Override
	public StyleBookEntryVersion fetchByPrimaryKey(
		long styleBookEntryVersionId) {

		return fetchByPrimaryKey((Serializable)styleBookEntryVersionId);
	}

	/**
	 * Returns all the style book entry versions.
	 *
	 * @return the style book entry versions
	 */
	@Override
	public List<StyleBookEntryVersion> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the style book entry versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of style book entry versions
	 * @param end the upper bound of the range of style book entry versions (not inclusive)
	 * @return the range of style book entry versions
	 */
	@Override
	public List<StyleBookEntryVersion> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the style book entry versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of style book entry versions
	 * @param end the upper bound of the range of style book entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of style book entry versions
	 */
	@Override
	public List<StyleBookEntryVersion> findAll(
		int start, int end,
		OrderByComparator<StyleBookEntryVersion> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the style book entry versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of style book entry versions
	 * @param end the upper bound of the range of style book entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of style book entry versions
	 */
	@Override
	public List<StyleBookEntryVersion> findAll(
		int start, int end,
		OrderByComparator<StyleBookEntryVersion> orderByComparator,
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

		List<StyleBookEntryVersion> list = null;

		if (useFinderCache) {
			list = (List<StyleBookEntryVersion>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_STYLEBOOKENTRYVERSION);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_STYLEBOOKENTRYVERSION;

				sql = sql.concat(StyleBookEntryVersionModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<StyleBookEntryVersion>)QueryUtil.list(
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
	 * Removes all the style book entry versions from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (StyleBookEntryVersion styleBookEntryVersion : findAll()) {
			remove(styleBookEntryVersion);
		}
	}

	/**
	 * Returns the number of style book entry versions.
	 *
	 * @return the number of style book entry versions
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
					_SQL_COUNT_STYLEBOOKENTRYVERSION);

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
		return "styleBookEntryVersionId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_STYLEBOOKENTRYVERSION;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return StyleBookEntryVersionModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the style book entry version persistence.
	 */
	@Activate
	public void activate() {
		_finderPathWithPaginationFindAll = new FinderPath(
			StyleBookEntryVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			StyleBookEntryVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByStyleBookEntryId = new FinderPath(
			StyleBookEntryVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByStyleBookEntryId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByStyleBookEntryId = new FinderPath(
			StyleBookEntryVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByStyleBookEntryId",
			new String[] {Long.class.getName()},
			StyleBookEntryVersionModelImpl.getColumnBitmask(
				"styleBookEntryId") |
			StyleBookEntryVersionModelImpl.getColumnBitmask("version"));

		_finderPathCountByStyleBookEntryId = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByStyleBookEntryId", new String[] {Long.class.getName()});

		_finderPathFetchByStyleBookEntryId_Version = new FinderPath(
			StyleBookEntryVersionImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByStyleBookEntryId_Version",
			new String[] {Long.class.getName(), Integer.class.getName()},
			StyleBookEntryVersionModelImpl.getColumnBitmask(
				"styleBookEntryId") |
			StyleBookEntryVersionModelImpl.getColumnBitmask("version"));

		_finderPathCountByStyleBookEntryId_Version = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByStyleBookEntryId_Version",
			new String[] {Long.class.getName(), Integer.class.getName()});

		_finderPathWithPaginationFindByGroupId = new FinderPath(
			StyleBookEntryVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByGroupId = new FinderPath(
			StyleBookEntryVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] {Long.class.getName()},
			StyleBookEntryVersionModelImpl.getColumnBitmask("groupId") |
			StyleBookEntryVersionModelImpl.getColumnBitmask("version"));

		_finderPathCountByGroupId = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByGroupId", new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByGroupId_Version = new FinderPath(
			StyleBookEntryVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId_Version",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByGroupId_Version = new FinderPath(
			StyleBookEntryVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId_Version",
			new String[] {Long.class.getName(), Integer.class.getName()},
			StyleBookEntryVersionModelImpl.getColumnBitmask("groupId") |
			StyleBookEntryVersionModelImpl.getColumnBitmask("version"));

		_finderPathCountByGroupId_Version = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByGroupId_Version",
			new String[] {Long.class.getName(), Integer.class.getName()});

		_finderPathWithPaginationFindByG_D = new FinderPath(
			StyleBookEntryVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_D",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_D = new FinderPath(
			StyleBookEntryVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_D",
			new String[] {Long.class.getName(), Boolean.class.getName()},
			StyleBookEntryVersionModelImpl.getColumnBitmask("groupId") |
			StyleBookEntryVersionModelImpl.getColumnBitmask(
				"defaultStyleBookEntry") |
			StyleBookEntryVersionModelImpl.getColumnBitmask("version"));

		_finderPathCountByG_D = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_D",
			new String[] {Long.class.getName(), Boolean.class.getName()});

		_finderPathWithPaginationFindByG_D_Version = new FinderPath(
			StyleBookEntryVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_D_Version",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_D_Version = new FinderPath(
			StyleBookEntryVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_D_Version",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName()
			},
			StyleBookEntryVersionModelImpl.getColumnBitmask("groupId") |
			StyleBookEntryVersionModelImpl.getColumnBitmask(
				"defaultStyleBookEntry") |
			StyleBookEntryVersionModelImpl.getColumnBitmask("version"));

		_finderPathCountByG_D_Version = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_D_Version",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName()
			});

		_finderPathWithPaginationFindByG_LikeN = new FinderPath(
			StyleBookEntryVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_LikeN",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_LikeN = new FinderPath(
			StyleBookEntryVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_LikeN",
			new String[] {Long.class.getName(), String.class.getName()},
			StyleBookEntryVersionModelImpl.getColumnBitmask("groupId") |
			StyleBookEntryVersionModelImpl.getColumnBitmask("name") |
			StyleBookEntryVersionModelImpl.getColumnBitmask("version"));

		_finderPathCountByG_LikeN = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_LikeN",
			new String[] {Long.class.getName(), String.class.getName()});

		_finderPathWithPaginationFindByG_LikeN_Version = new FinderPath(
			StyleBookEntryVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_LikeN_Version",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_LikeN_Version = new FinderPath(
			StyleBookEntryVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_LikeN_Version",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName()
			},
			StyleBookEntryVersionModelImpl.getColumnBitmask("groupId") |
			StyleBookEntryVersionModelImpl.getColumnBitmask("name") |
			StyleBookEntryVersionModelImpl.getColumnBitmask("version"));

		_finderPathCountByG_LikeN_Version = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_LikeN_Version",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName()
			});

		_finderPathWithPaginationFindByG_SBEK = new FinderPath(
			StyleBookEntryVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_SBEK",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_SBEK = new FinderPath(
			StyleBookEntryVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_SBEK",
			new String[] {Long.class.getName(), String.class.getName()},
			StyleBookEntryVersionModelImpl.getColumnBitmask("groupId") |
			StyleBookEntryVersionModelImpl.getColumnBitmask(
				"styleBookEntryKey") |
			StyleBookEntryVersionModelImpl.getColumnBitmask("version"));

		_finderPathCountByG_SBEK = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_SBEK",
			new String[] {Long.class.getName(), String.class.getName()});

		_finderPathFetchByG_SBEK_Version = new FinderPath(
			StyleBookEntryVersionImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByG_SBEK_Version",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName()
			},
			StyleBookEntryVersionModelImpl.getColumnBitmask("groupId") |
			StyleBookEntryVersionModelImpl.getColumnBitmask(
				"styleBookEntryKey") |
			StyleBookEntryVersionModelImpl.getColumnBitmask("version"));

		_finderPathCountByG_SBEK_Version = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_SBEK_Version",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName()
			});
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(StyleBookEntryVersionImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	@Reference(
		target = StyleBookPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = StyleBookPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = StyleBookPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_STYLEBOOKENTRYVERSION =
		"SELECT styleBookEntryVersion FROM StyleBookEntryVersion styleBookEntryVersion";

	private static final String _SQL_SELECT_STYLEBOOKENTRYVERSION_WHERE =
		"SELECT styleBookEntryVersion FROM StyleBookEntryVersion styleBookEntryVersion WHERE ";

	private static final String _SQL_COUNT_STYLEBOOKENTRYVERSION =
		"SELECT COUNT(styleBookEntryVersion) FROM StyleBookEntryVersion styleBookEntryVersion";

	private static final String _SQL_COUNT_STYLEBOOKENTRYVERSION_WHERE =
		"SELECT COUNT(styleBookEntryVersion) FROM StyleBookEntryVersion styleBookEntryVersion WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"styleBookEntryVersion.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No StyleBookEntryVersion exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No StyleBookEntryVersion exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		StyleBookEntryVersionPersistenceImpl.class);

	static {
		try {
			Class.forName(StyleBookPersistenceConstants.class.getName());
		}
		catch (ClassNotFoundException classNotFoundException) {
			throw new ExceptionInInitializerError(classNotFoundException);
		}
	}

}