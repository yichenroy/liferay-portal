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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.style.book.exception.NoSuchEntryException;
import com.liferay.style.book.model.StyleBookEntry;
import com.liferay.style.book.model.StyleBookEntryTable;
import com.liferay.style.book.model.impl.StyleBookEntryImpl;
import com.liferay.style.book.model.impl.StyleBookEntryModelImpl;
import com.liferay.style.book.service.persistence.StyleBookEntryPersistence;
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
 * The persistence implementation for the style book entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = StyleBookEntryPersistence.class)
public class StyleBookEntryPersistenceImpl
	extends BasePersistenceImpl<StyleBookEntry>
	implements StyleBookEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>StyleBookEntryUtil</code> to access the style book entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		StyleBookEntryImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByGroupId;
	private FinderPath _finderPathWithoutPaginationFindByGroupId;
	private FinderPath _finderPathCountByGroupId;

	/**
	 * Returns all the style book entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching style book entries
	 */
	@Override
	public List<StyleBookEntry> findByGroupId(long groupId) {
		return findByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the style book entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @return the range of matching style book entries
	 */
	@Override
	public List<StyleBookEntry> findByGroupId(
		long groupId, int start, int end) {

		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the style book entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching style book entries
	 */
	@Override
	public List<StyleBookEntry> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<StyleBookEntry> orderByComparator) {

		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the style book entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching style book entries
	 */
	@Override
	public List<StyleBookEntry> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<StyleBookEntry> orderByComparator,
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

		List<StyleBookEntry> list = null;

		if (useFinderCache) {
			list = (List<StyleBookEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (StyleBookEntry styleBookEntry : list) {
					if (groupId != styleBookEntry.getGroupId()) {
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

			sb.append(_SQL_SELECT_STYLEBOOKENTRY_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(StyleBookEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				list = (List<StyleBookEntry>)QueryUtil.list(
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
	 * Returns the first style book entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry findByGroupId_First(
			long groupId, OrderByComparator<StyleBookEntry> orderByComparator)
		throws NoSuchEntryException {

		StyleBookEntry styleBookEntry = fetchByGroupId_First(
			groupId, orderByComparator);

		if (styleBookEntry != null) {
			return styleBookEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first style book entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry fetchByGroupId_First(
		long groupId, OrderByComparator<StyleBookEntry> orderByComparator) {

		List<StyleBookEntry> list = findByGroupId(
			groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last style book entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry findByGroupId_Last(
			long groupId, OrderByComparator<StyleBookEntry> orderByComparator)
		throws NoSuchEntryException {

		StyleBookEntry styleBookEntry = fetchByGroupId_Last(
			groupId, orderByComparator);

		if (styleBookEntry != null) {
			return styleBookEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last style book entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry fetchByGroupId_Last(
		long groupId, OrderByComparator<StyleBookEntry> orderByComparator) {

		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<StyleBookEntry> list = findByGroupId(
			groupId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the style book entries before and after the current style book entry in the ordered set where groupId = &#63;.
	 *
	 * @param styleBookEntryId the primary key of the current style book entry
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next style book entry
	 * @throws NoSuchEntryException if a style book entry with the primary key could not be found
	 */
	@Override
	public StyleBookEntry[] findByGroupId_PrevAndNext(
			long styleBookEntryId, long groupId,
			OrderByComparator<StyleBookEntry> orderByComparator)
		throws NoSuchEntryException {

		StyleBookEntry styleBookEntry = findByPrimaryKey(styleBookEntryId);

		Session session = null;

		try {
			session = openSession();

			StyleBookEntry[] array = new StyleBookEntryImpl[3];

			array[0] = getByGroupId_PrevAndNext(
				session, styleBookEntry, groupId, orderByComparator, true);

			array[1] = styleBookEntry;

			array[2] = getByGroupId_PrevAndNext(
				session, styleBookEntry, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected StyleBookEntry getByGroupId_PrevAndNext(
		Session session, StyleBookEntry styleBookEntry, long groupId,
		OrderByComparator<StyleBookEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_STYLEBOOKENTRY_WHERE);

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
			sb.append(StyleBookEntryModelImpl.ORDER_BY_JPQL);
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
						styleBookEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<StyleBookEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the style book entries where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (StyleBookEntry styleBookEntry :
				findByGroupId(
					groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(styleBookEntry);
		}
	}

	/**
	 * Returns the number of style book entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching style book entries
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = _finderPathCountByGroupId;

		Object[] finderArgs = new Object[] {groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_STYLEBOOKENTRY_WHERE);

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
		"styleBookEntry.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByGroupId_Head;
	private FinderPath _finderPathWithoutPaginationFindByGroupId_Head;
	private FinderPath _finderPathCountByGroupId_Head;

	/**
	 * Returns all the style book entries where groupId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @return the matching style book entries
	 */
	@Override
	public List<StyleBookEntry> findByGroupId_Head(long groupId, boolean head) {
		return findByGroupId_Head(
			groupId, head, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the style book entries where groupId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @return the range of matching style book entries
	 */
	@Override
	public List<StyleBookEntry> findByGroupId_Head(
		long groupId, boolean head, int start, int end) {

		return findByGroupId_Head(groupId, head, start, end, null);
	}

	/**
	 * Returns an ordered range of all the style book entries where groupId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching style book entries
	 */
	@Override
	public List<StyleBookEntry> findByGroupId_Head(
		long groupId, boolean head, int start, int end,
		OrderByComparator<StyleBookEntry> orderByComparator) {

		return findByGroupId_Head(
			groupId, head, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the style book entries where groupId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching style book entries
	 */
	@Override
	public List<StyleBookEntry> findByGroupId_Head(
		long groupId, boolean head, int start, int end,
		OrderByComparator<StyleBookEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByGroupId_Head;
				finderArgs = new Object[] {groupId, head};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByGroupId_Head;
			finderArgs = new Object[] {
				groupId, head, start, end, orderByComparator
			};
		}

		List<StyleBookEntry> list = null;

		if (useFinderCache) {
			list = (List<StyleBookEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (StyleBookEntry styleBookEntry : list) {
					if ((groupId != styleBookEntry.getGroupId()) ||
						(head != styleBookEntry.isHead())) {

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

			sb.append(_SQL_SELECT_STYLEBOOKENTRY_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_HEAD_GROUPID_2);

			sb.append(_FINDER_COLUMN_GROUPID_HEAD_HEAD_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(StyleBookEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(head);

				list = (List<StyleBookEntry>)QueryUtil.list(
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
	 * Returns the first style book entry in the ordered set where groupId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry findByGroupId_Head_First(
			long groupId, boolean head,
			OrderByComparator<StyleBookEntry> orderByComparator)
		throws NoSuchEntryException {

		StyleBookEntry styleBookEntry = fetchByGroupId_Head_First(
			groupId, head, orderByComparator);

		if (styleBookEntry != null) {
			return styleBookEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", head=");
		sb.append(head);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first style book entry in the ordered set where groupId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry fetchByGroupId_Head_First(
		long groupId, boolean head,
		OrderByComparator<StyleBookEntry> orderByComparator) {

		List<StyleBookEntry> list = findByGroupId_Head(
			groupId, head, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last style book entry in the ordered set where groupId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry findByGroupId_Head_Last(
			long groupId, boolean head,
			OrderByComparator<StyleBookEntry> orderByComparator)
		throws NoSuchEntryException {

		StyleBookEntry styleBookEntry = fetchByGroupId_Head_Last(
			groupId, head, orderByComparator);

		if (styleBookEntry != null) {
			return styleBookEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", head=");
		sb.append(head);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last style book entry in the ordered set where groupId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry fetchByGroupId_Head_Last(
		long groupId, boolean head,
		OrderByComparator<StyleBookEntry> orderByComparator) {

		int count = countByGroupId_Head(groupId, head);

		if (count == 0) {
			return null;
		}

		List<StyleBookEntry> list = findByGroupId_Head(
			groupId, head, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the style book entries before and after the current style book entry in the ordered set where groupId = &#63; and head = &#63;.
	 *
	 * @param styleBookEntryId the primary key of the current style book entry
	 * @param groupId the group ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next style book entry
	 * @throws NoSuchEntryException if a style book entry with the primary key could not be found
	 */
	@Override
	public StyleBookEntry[] findByGroupId_Head_PrevAndNext(
			long styleBookEntryId, long groupId, boolean head,
			OrderByComparator<StyleBookEntry> orderByComparator)
		throws NoSuchEntryException {

		StyleBookEntry styleBookEntry = findByPrimaryKey(styleBookEntryId);

		Session session = null;

		try {
			session = openSession();

			StyleBookEntry[] array = new StyleBookEntryImpl[3];

			array[0] = getByGroupId_Head_PrevAndNext(
				session, styleBookEntry, groupId, head, orderByComparator,
				true);

			array[1] = styleBookEntry;

			array[2] = getByGroupId_Head_PrevAndNext(
				session, styleBookEntry, groupId, head, orderByComparator,
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

	protected StyleBookEntry getByGroupId_Head_PrevAndNext(
		Session session, StyleBookEntry styleBookEntry, long groupId,
		boolean head, OrderByComparator<StyleBookEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_STYLEBOOKENTRY_WHERE);

		sb.append(_FINDER_COLUMN_GROUPID_HEAD_GROUPID_2);

		sb.append(_FINDER_COLUMN_GROUPID_HEAD_HEAD_2);

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
			sb.append(StyleBookEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(head);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						styleBookEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<StyleBookEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the style book entries where groupId = &#63; and head = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param head the head
	 */
	@Override
	public void removeByGroupId_Head(long groupId, boolean head) {
		for (StyleBookEntry styleBookEntry :
				findByGroupId_Head(
					groupId, head, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(styleBookEntry);
		}
	}

	/**
	 * Returns the number of style book entries where groupId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @return the number of matching style book entries
	 */
	@Override
	public int countByGroupId_Head(long groupId, boolean head) {
		FinderPath finderPath = _finderPathCountByGroupId_Head;

		Object[] finderArgs = new Object[] {groupId, head};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_STYLEBOOKENTRY_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_HEAD_GROUPID_2);

			sb.append(_FINDER_COLUMN_GROUPID_HEAD_HEAD_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(head);

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

	private static final String _FINDER_COLUMN_GROUPID_HEAD_GROUPID_2 =
		"styleBookEntry.groupId = ? AND ";

	private static final String _FINDER_COLUMN_GROUPID_HEAD_HEAD_2 =
		"styleBookEntry.head = ?";

	private FinderPath _finderPathWithPaginationFindByG_D;
	private FinderPath _finderPathWithoutPaginationFindByG_D;
	private FinderPath _finderPathCountByG_D;

	/**
	 * Returns all the style book entries where groupId = &#63; and defaultStyleBookEntry = &#63;.
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @return the matching style book entries
	 */
	@Override
	public List<StyleBookEntry> findByG_D(
		long groupId, boolean defaultStyleBookEntry) {

		return findByG_D(
			groupId, defaultStyleBookEntry, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the style book entries where groupId = &#63; and defaultStyleBookEntry = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @return the range of matching style book entries
	 */
	@Override
	public List<StyleBookEntry> findByG_D(
		long groupId, boolean defaultStyleBookEntry, int start, int end) {

		return findByG_D(groupId, defaultStyleBookEntry, start, end, null);
	}

	/**
	 * Returns an ordered range of all the style book entries where groupId = &#63; and defaultStyleBookEntry = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching style book entries
	 */
	@Override
	public List<StyleBookEntry> findByG_D(
		long groupId, boolean defaultStyleBookEntry, int start, int end,
		OrderByComparator<StyleBookEntry> orderByComparator) {

		return findByG_D(
			groupId, defaultStyleBookEntry, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the style book entries where groupId = &#63; and defaultStyleBookEntry = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching style book entries
	 */
	@Override
	public List<StyleBookEntry> findByG_D(
		long groupId, boolean defaultStyleBookEntry, int start, int end,
		OrderByComparator<StyleBookEntry> orderByComparator,
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

		List<StyleBookEntry> list = null;

		if (useFinderCache) {
			list = (List<StyleBookEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (StyleBookEntry styleBookEntry : list) {
					if ((groupId != styleBookEntry.getGroupId()) ||
						(defaultStyleBookEntry !=
							styleBookEntry.isDefaultStyleBookEntry())) {

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

			sb.append(_SQL_SELECT_STYLEBOOKENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_D_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_D_DEFAULTSTYLEBOOKENTRY_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(StyleBookEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(defaultStyleBookEntry);

				list = (List<StyleBookEntry>)QueryUtil.list(
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
	 * Returns the first style book entry in the ordered set where groupId = &#63; and defaultStyleBookEntry = &#63;.
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry findByG_D_First(
			long groupId, boolean defaultStyleBookEntry,
			OrderByComparator<StyleBookEntry> orderByComparator)
		throws NoSuchEntryException {

		StyleBookEntry styleBookEntry = fetchByG_D_First(
			groupId, defaultStyleBookEntry, orderByComparator);

		if (styleBookEntry != null) {
			return styleBookEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", defaultStyleBookEntry=");
		sb.append(defaultStyleBookEntry);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first style book entry in the ordered set where groupId = &#63; and defaultStyleBookEntry = &#63;.
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry fetchByG_D_First(
		long groupId, boolean defaultStyleBookEntry,
		OrderByComparator<StyleBookEntry> orderByComparator) {

		List<StyleBookEntry> list = findByG_D(
			groupId, defaultStyleBookEntry, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last style book entry in the ordered set where groupId = &#63; and defaultStyleBookEntry = &#63;.
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry findByG_D_Last(
			long groupId, boolean defaultStyleBookEntry,
			OrderByComparator<StyleBookEntry> orderByComparator)
		throws NoSuchEntryException {

		StyleBookEntry styleBookEntry = fetchByG_D_Last(
			groupId, defaultStyleBookEntry, orderByComparator);

		if (styleBookEntry != null) {
			return styleBookEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", defaultStyleBookEntry=");
		sb.append(defaultStyleBookEntry);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last style book entry in the ordered set where groupId = &#63; and defaultStyleBookEntry = &#63;.
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry fetchByG_D_Last(
		long groupId, boolean defaultStyleBookEntry,
		OrderByComparator<StyleBookEntry> orderByComparator) {

		int count = countByG_D(groupId, defaultStyleBookEntry);

		if (count == 0) {
			return null;
		}

		List<StyleBookEntry> list = findByG_D(
			groupId, defaultStyleBookEntry, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the style book entries before and after the current style book entry in the ordered set where groupId = &#63; and defaultStyleBookEntry = &#63;.
	 *
	 * @param styleBookEntryId the primary key of the current style book entry
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next style book entry
	 * @throws NoSuchEntryException if a style book entry with the primary key could not be found
	 */
	@Override
	public StyleBookEntry[] findByG_D_PrevAndNext(
			long styleBookEntryId, long groupId, boolean defaultStyleBookEntry,
			OrderByComparator<StyleBookEntry> orderByComparator)
		throws NoSuchEntryException {

		StyleBookEntry styleBookEntry = findByPrimaryKey(styleBookEntryId);

		Session session = null;

		try {
			session = openSession();

			StyleBookEntry[] array = new StyleBookEntryImpl[3];

			array[0] = getByG_D_PrevAndNext(
				session, styleBookEntry, groupId, defaultStyleBookEntry,
				orderByComparator, true);

			array[1] = styleBookEntry;

			array[2] = getByG_D_PrevAndNext(
				session, styleBookEntry, groupId, defaultStyleBookEntry,
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

	protected StyleBookEntry getByG_D_PrevAndNext(
		Session session, StyleBookEntry styleBookEntry, long groupId,
		boolean defaultStyleBookEntry,
		OrderByComparator<StyleBookEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_STYLEBOOKENTRY_WHERE);

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
			sb.append(StyleBookEntryModelImpl.ORDER_BY_JPQL);
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
						styleBookEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<StyleBookEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the style book entries where groupId = &#63; and defaultStyleBookEntry = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 */
	@Override
	public void removeByG_D(long groupId, boolean defaultStyleBookEntry) {
		for (StyleBookEntry styleBookEntry :
				findByG_D(
					groupId, defaultStyleBookEntry, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(styleBookEntry);
		}
	}

	/**
	 * Returns the number of style book entries where groupId = &#63; and defaultStyleBookEntry = &#63;.
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @return the number of matching style book entries
	 */
	@Override
	public int countByG_D(long groupId, boolean defaultStyleBookEntry) {
		FinderPath finderPath = _finderPathCountByG_D;

		Object[] finderArgs = new Object[] {groupId, defaultStyleBookEntry};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_STYLEBOOKENTRY_WHERE);

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
		"styleBookEntry.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_D_DEFAULTSTYLEBOOKENTRY_2 =
		"styleBookEntry.defaultStyleBookEntry = ?";

	private FinderPath _finderPathWithPaginationFindByG_D_Head;
	private FinderPath _finderPathWithoutPaginationFindByG_D_Head;
	private FinderPath _finderPathCountByG_D_Head;

	/**
	 * Returns all the style book entries where groupId = &#63; and defaultStyleBookEntry = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param head the head
	 * @return the matching style book entries
	 */
	@Override
	public List<StyleBookEntry> findByG_D_Head(
		long groupId, boolean defaultStyleBookEntry, boolean head) {

		return findByG_D_Head(
			groupId, defaultStyleBookEntry, head, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the style book entries where groupId = &#63; and defaultStyleBookEntry = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param head the head
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @return the range of matching style book entries
	 */
	@Override
	public List<StyleBookEntry> findByG_D_Head(
		long groupId, boolean defaultStyleBookEntry, boolean head, int start,
		int end) {

		return findByG_D_Head(
			groupId, defaultStyleBookEntry, head, start, end, null);
	}

	/**
	 * Returns an ordered range of all the style book entries where groupId = &#63; and defaultStyleBookEntry = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param head the head
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching style book entries
	 */
	@Override
	public List<StyleBookEntry> findByG_D_Head(
		long groupId, boolean defaultStyleBookEntry, boolean head, int start,
		int end, OrderByComparator<StyleBookEntry> orderByComparator) {

		return findByG_D_Head(
			groupId, defaultStyleBookEntry, head, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the style book entries where groupId = &#63; and defaultStyleBookEntry = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param head the head
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching style book entries
	 */
	@Override
	public List<StyleBookEntry> findByG_D_Head(
		long groupId, boolean defaultStyleBookEntry, boolean head, int start,
		int end, OrderByComparator<StyleBookEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByG_D_Head;
				finderArgs = new Object[] {
					groupId, defaultStyleBookEntry, head
				};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByG_D_Head;
			finderArgs = new Object[] {
				groupId, defaultStyleBookEntry, head, start, end,
				orderByComparator
			};
		}

		List<StyleBookEntry> list = null;

		if (useFinderCache) {
			list = (List<StyleBookEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (StyleBookEntry styleBookEntry : list) {
					if ((groupId != styleBookEntry.getGroupId()) ||
						(defaultStyleBookEntry !=
							styleBookEntry.isDefaultStyleBookEntry()) ||
						(head != styleBookEntry.isHead())) {

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

			sb.append(_SQL_SELECT_STYLEBOOKENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_D_HEAD_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_D_HEAD_DEFAULTSTYLEBOOKENTRY_2);

			sb.append(_FINDER_COLUMN_G_D_HEAD_HEAD_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(StyleBookEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(defaultStyleBookEntry);

				queryPos.add(head);

				list = (List<StyleBookEntry>)QueryUtil.list(
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
	 * Returns the first style book entry in the ordered set where groupId = &#63; and defaultStyleBookEntry = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry findByG_D_Head_First(
			long groupId, boolean defaultStyleBookEntry, boolean head,
			OrderByComparator<StyleBookEntry> orderByComparator)
		throws NoSuchEntryException {

		StyleBookEntry styleBookEntry = fetchByG_D_Head_First(
			groupId, defaultStyleBookEntry, head, orderByComparator);

		if (styleBookEntry != null) {
			return styleBookEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", defaultStyleBookEntry=");
		sb.append(defaultStyleBookEntry);

		sb.append(", head=");
		sb.append(head);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first style book entry in the ordered set where groupId = &#63; and defaultStyleBookEntry = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry fetchByG_D_Head_First(
		long groupId, boolean defaultStyleBookEntry, boolean head,
		OrderByComparator<StyleBookEntry> orderByComparator) {

		List<StyleBookEntry> list = findByG_D_Head(
			groupId, defaultStyleBookEntry, head, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last style book entry in the ordered set where groupId = &#63; and defaultStyleBookEntry = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry findByG_D_Head_Last(
			long groupId, boolean defaultStyleBookEntry, boolean head,
			OrderByComparator<StyleBookEntry> orderByComparator)
		throws NoSuchEntryException {

		StyleBookEntry styleBookEntry = fetchByG_D_Head_Last(
			groupId, defaultStyleBookEntry, head, orderByComparator);

		if (styleBookEntry != null) {
			return styleBookEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", defaultStyleBookEntry=");
		sb.append(defaultStyleBookEntry);

		sb.append(", head=");
		sb.append(head);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last style book entry in the ordered set where groupId = &#63; and defaultStyleBookEntry = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry fetchByG_D_Head_Last(
		long groupId, boolean defaultStyleBookEntry, boolean head,
		OrderByComparator<StyleBookEntry> orderByComparator) {

		int count = countByG_D_Head(groupId, defaultStyleBookEntry, head);

		if (count == 0) {
			return null;
		}

		List<StyleBookEntry> list = findByG_D_Head(
			groupId, defaultStyleBookEntry, head, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the style book entries before and after the current style book entry in the ordered set where groupId = &#63; and defaultStyleBookEntry = &#63; and head = &#63;.
	 *
	 * @param styleBookEntryId the primary key of the current style book entry
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next style book entry
	 * @throws NoSuchEntryException if a style book entry with the primary key could not be found
	 */
	@Override
	public StyleBookEntry[] findByG_D_Head_PrevAndNext(
			long styleBookEntryId, long groupId, boolean defaultStyleBookEntry,
			boolean head, OrderByComparator<StyleBookEntry> orderByComparator)
		throws NoSuchEntryException {

		StyleBookEntry styleBookEntry = findByPrimaryKey(styleBookEntryId);

		Session session = null;

		try {
			session = openSession();

			StyleBookEntry[] array = new StyleBookEntryImpl[3];

			array[0] = getByG_D_Head_PrevAndNext(
				session, styleBookEntry, groupId, defaultStyleBookEntry, head,
				orderByComparator, true);

			array[1] = styleBookEntry;

			array[2] = getByG_D_Head_PrevAndNext(
				session, styleBookEntry, groupId, defaultStyleBookEntry, head,
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

	protected StyleBookEntry getByG_D_Head_PrevAndNext(
		Session session, StyleBookEntry styleBookEntry, long groupId,
		boolean defaultStyleBookEntry, boolean head,
		OrderByComparator<StyleBookEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		sb.append(_SQL_SELECT_STYLEBOOKENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_D_HEAD_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_D_HEAD_DEFAULTSTYLEBOOKENTRY_2);

		sb.append(_FINDER_COLUMN_G_D_HEAD_HEAD_2);

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
			sb.append(StyleBookEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(defaultStyleBookEntry);

		queryPos.add(head);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						styleBookEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<StyleBookEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the style book entries where groupId = &#63; and defaultStyleBookEntry = &#63; and head = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param head the head
	 */
	@Override
	public void removeByG_D_Head(
		long groupId, boolean defaultStyleBookEntry, boolean head) {

		for (StyleBookEntry styleBookEntry :
				findByG_D_Head(
					groupId, defaultStyleBookEntry, head, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(styleBookEntry);
		}
	}

	/**
	 * Returns the number of style book entries where groupId = &#63; and defaultStyleBookEntry = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param head the head
	 * @return the number of matching style book entries
	 */
	@Override
	public int countByG_D_Head(
		long groupId, boolean defaultStyleBookEntry, boolean head) {

		FinderPath finderPath = _finderPathCountByG_D_Head;

		Object[] finderArgs = new Object[] {
			groupId, defaultStyleBookEntry, head
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_STYLEBOOKENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_D_HEAD_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_D_HEAD_DEFAULTSTYLEBOOKENTRY_2);

			sb.append(_FINDER_COLUMN_G_D_HEAD_HEAD_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(defaultStyleBookEntry);

				queryPos.add(head);

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

	private static final String _FINDER_COLUMN_G_D_HEAD_GROUPID_2 =
		"styleBookEntry.groupId = ? AND ";

	private static final String
		_FINDER_COLUMN_G_D_HEAD_DEFAULTSTYLEBOOKENTRY_2 =
			"styleBookEntry.defaultStyleBookEntry = ? AND ";

	private static final String _FINDER_COLUMN_G_D_HEAD_HEAD_2 =
		"styleBookEntry.head = ?";

	private FinderPath _finderPathWithPaginationFindByG_LikeN;
	private FinderPath _finderPathWithPaginationCountByG_LikeN;

	/**
	 * Returns all the style book entries where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the matching style book entries
	 */
	@Override
	public List<StyleBookEntry> findByG_LikeN(long groupId, String name) {
		return findByG_LikeN(
			groupId, name, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the style book entries where groupId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @return the range of matching style book entries
	 */
	@Override
	public List<StyleBookEntry> findByG_LikeN(
		long groupId, String name, int start, int end) {

		return findByG_LikeN(groupId, name, start, end, null);
	}

	/**
	 * Returns an ordered range of all the style book entries where groupId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching style book entries
	 */
	@Override
	public List<StyleBookEntry> findByG_LikeN(
		long groupId, String name, int start, int end,
		OrderByComparator<StyleBookEntry> orderByComparator) {

		return findByG_LikeN(
			groupId, name, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the style book entries where groupId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching style book entries
	 */
	@Override
	public List<StyleBookEntry> findByG_LikeN(
		long groupId, String name, int start, int end,
		OrderByComparator<StyleBookEntry> orderByComparator,
		boolean useFinderCache) {

		name = Objects.toString(name, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByG_LikeN;
		finderArgs = new Object[] {
			groupId, name, start, end, orderByComparator
		};

		List<StyleBookEntry> list = null;

		if (useFinderCache) {
			list = (List<StyleBookEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (StyleBookEntry styleBookEntry : list) {
					if ((groupId != styleBookEntry.getGroupId()) ||
						!StringUtil.wildcardMatches(
							styleBookEntry.getName(), name, '_', '%', '\\',
							true)) {

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

			sb.append(_SQL_SELECT_STYLEBOOKENTRY_WHERE);

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
				sb.append(StyleBookEntryModelImpl.ORDER_BY_JPQL);
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

				list = (List<StyleBookEntry>)QueryUtil.list(
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
	 * Returns the first style book entry in the ordered set where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry findByG_LikeN_First(
			long groupId, String name,
			OrderByComparator<StyleBookEntry> orderByComparator)
		throws NoSuchEntryException {

		StyleBookEntry styleBookEntry = fetchByG_LikeN_First(
			groupId, name, orderByComparator);

		if (styleBookEntry != null) {
			return styleBookEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", nameLIKE");
		sb.append(name);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first style book entry in the ordered set where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry fetchByG_LikeN_First(
		long groupId, String name,
		OrderByComparator<StyleBookEntry> orderByComparator) {

		List<StyleBookEntry> list = findByG_LikeN(
			groupId, name, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last style book entry in the ordered set where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry findByG_LikeN_Last(
			long groupId, String name,
			OrderByComparator<StyleBookEntry> orderByComparator)
		throws NoSuchEntryException {

		StyleBookEntry styleBookEntry = fetchByG_LikeN_Last(
			groupId, name, orderByComparator);

		if (styleBookEntry != null) {
			return styleBookEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", nameLIKE");
		sb.append(name);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last style book entry in the ordered set where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry fetchByG_LikeN_Last(
		long groupId, String name,
		OrderByComparator<StyleBookEntry> orderByComparator) {

		int count = countByG_LikeN(groupId, name);

		if (count == 0) {
			return null;
		}

		List<StyleBookEntry> list = findByG_LikeN(
			groupId, name, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the style book entries before and after the current style book entry in the ordered set where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param styleBookEntryId the primary key of the current style book entry
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next style book entry
	 * @throws NoSuchEntryException if a style book entry with the primary key could not be found
	 */
	@Override
	public StyleBookEntry[] findByG_LikeN_PrevAndNext(
			long styleBookEntryId, long groupId, String name,
			OrderByComparator<StyleBookEntry> orderByComparator)
		throws NoSuchEntryException {

		name = Objects.toString(name, "");

		StyleBookEntry styleBookEntry = findByPrimaryKey(styleBookEntryId);

		Session session = null;

		try {
			session = openSession();

			StyleBookEntry[] array = new StyleBookEntryImpl[3];

			array[0] = getByG_LikeN_PrevAndNext(
				session, styleBookEntry, groupId, name, orderByComparator,
				true);

			array[1] = styleBookEntry;

			array[2] = getByG_LikeN_PrevAndNext(
				session, styleBookEntry, groupId, name, orderByComparator,
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

	protected StyleBookEntry getByG_LikeN_PrevAndNext(
		Session session, StyleBookEntry styleBookEntry, long groupId,
		String name, OrderByComparator<StyleBookEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_STYLEBOOKENTRY_WHERE);

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
			sb.append(StyleBookEntryModelImpl.ORDER_BY_JPQL);
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
						styleBookEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<StyleBookEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the style book entries where groupId = &#63; and name LIKE &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 */
	@Override
	public void removeByG_LikeN(long groupId, String name) {
		for (StyleBookEntry styleBookEntry :
				findByG_LikeN(
					groupId, name, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(styleBookEntry);
		}
	}

	/**
	 * Returns the number of style book entries where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the number of matching style book entries
	 */
	@Override
	public int countByG_LikeN(long groupId, String name) {
		name = Objects.toString(name, "");

		FinderPath finderPath = _finderPathWithPaginationCountByG_LikeN;

		Object[] finderArgs = new Object[] {groupId, name};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_STYLEBOOKENTRY_WHERE);

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
		"styleBookEntry.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_LIKEN_NAME_2 =
		"styleBookEntry.name LIKE ?";

	private static final String _FINDER_COLUMN_G_LIKEN_NAME_3 =
		"(styleBookEntry.name IS NULL OR styleBookEntry.name LIKE '')";

	private FinderPath _finderPathWithPaginationFindByG_LikeN_Head;
	private FinderPath _finderPathWithPaginationCountByG_LikeN_Head;

	/**
	 * Returns all the style book entries where groupId = &#63; and name LIKE &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param head the head
	 * @return the matching style book entries
	 */
	@Override
	public List<StyleBookEntry> findByG_LikeN_Head(
		long groupId, String name, boolean head) {

		return findByG_LikeN_Head(
			groupId, name, head, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the style book entries where groupId = &#63; and name LIKE &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param head the head
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @return the range of matching style book entries
	 */
	@Override
	public List<StyleBookEntry> findByG_LikeN_Head(
		long groupId, String name, boolean head, int start, int end) {

		return findByG_LikeN_Head(groupId, name, head, start, end, null);
	}

	/**
	 * Returns an ordered range of all the style book entries where groupId = &#63; and name LIKE &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param head the head
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching style book entries
	 */
	@Override
	public List<StyleBookEntry> findByG_LikeN_Head(
		long groupId, String name, boolean head, int start, int end,
		OrderByComparator<StyleBookEntry> orderByComparator) {

		return findByG_LikeN_Head(
			groupId, name, head, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the style book entries where groupId = &#63; and name LIKE &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param head the head
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching style book entries
	 */
	@Override
	public List<StyleBookEntry> findByG_LikeN_Head(
		long groupId, String name, boolean head, int start, int end,
		OrderByComparator<StyleBookEntry> orderByComparator,
		boolean useFinderCache) {

		name = Objects.toString(name, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByG_LikeN_Head;
		finderArgs = new Object[] {
			groupId, name, head, start, end, orderByComparator
		};

		List<StyleBookEntry> list = null;

		if (useFinderCache) {
			list = (List<StyleBookEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (StyleBookEntry styleBookEntry : list) {
					if ((groupId != styleBookEntry.getGroupId()) ||
						!StringUtil.wildcardMatches(
							styleBookEntry.getName(), name, '_', '%', '\\',
							true) ||
						(head != styleBookEntry.isHead())) {

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

			sb.append(_SQL_SELECT_STYLEBOOKENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_LIKEN_HEAD_GROUPID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_LIKEN_HEAD_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_G_LIKEN_HEAD_NAME_2);
			}

			sb.append(_FINDER_COLUMN_G_LIKEN_HEAD_HEAD_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(StyleBookEntryModelImpl.ORDER_BY_JPQL);
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

				queryPos.add(head);

				list = (List<StyleBookEntry>)QueryUtil.list(
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
	 * Returns the first style book entry in the ordered set where groupId = &#63; and name LIKE &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry findByG_LikeN_Head_First(
			long groupId, String name, boolean head,
			OrderByComparator<StyleBookEntry> orderByComparator)
		throws NoSuchEntryException {

		StyleBookEntry styleBookEntry = fetchByG_LikeN_Head_First(
			groupId, name, head, orderByComparator);

		if (styleBookEntry != null) {
			return styleBookEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", nameLIKE");
		sb.append(name);

		sb.append(", head=");
		sb.append(head);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first style book entry in the ordered set where groupId = &#63; and name LIKE &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry fetchByG_LikeN_Head_First(
		long groupId, String name, boolean head,
		OrderByComparator<StyleBookEntry> orderByComparator) {

		List<StyleBookEntry> list = findByG_LikeN_Head(
			groupId, name, head, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last style book entry in the ordered set where groupId = &#63; and name LIKE &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry findByG_LikeN_Head_Last(
			long groupId, String name, boolean head,
			OrderByComparator<StyleBookEntry> orderByComparator)
		throws NoSuchEntryException {

		StyleBookEntry styleBookEntry = fetchByG_LikeN_Head_Last(
			groupId, name, head, orderByComparator);

		if (styleBookEntry != null) {
			return styleBookEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", nameLIKE");
		sb.append(name);

		sb.append(", head=");
		sb.append(head);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last style book entry in the ordered set where groupId = &#63; and name LIKE &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry fetchByG_LikeN_Head_Last(
		long groupId, String name, boolean head,
		OrderByComparator<StyleBookEntry> orderByComparator) {

		int count = countByG_LikeN_Head(groupId, name, head);

		if (count == 0) {
			return null;
		}

		List<StyleBookEntry> list = findByG_LikeN_Head(
			groupId, name, head, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the style book entries before and after the current style book entry in the ordered set where groupId = &#63; and name LIKE &#63; and head = &#63;.
	 *
	 * @param styleBookEntryId the primary key of the current style book entry
	 * @param groupId the group ID
	 * @param name the name
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next style book entry
	 * @throws NoSuchEntryException if a style book entry with the primary key could not be found
	 */
	@Override
	public StyleBookEntry[] findByG_LikeN_Head_PrevAndNext(
			long styleBookEntryId, long groupId, String name, boolean head,
			OrderByComparator<StyleBookEntry> orderByComparator)
		throws NoSuchEntryException {

		name = Objects.toString(name, "");

		StyleBookEntry styleBookEntry = findByPrimaryKey(styleBookEntryId);

		Session session = null;

		try {
			session = openSession();

			StyleBookEntry[] array = new StyleBookEntryImpl[3];

			array[0] = getByG_LikeN_Head_PrevAndNext(
				session, styleBookEntry, groupId, name, head, orderByComparator,
				true);

			array[1] = styleBookEntry;

			array[2] = getByG_LikeN_Head_PrevAndNext(
				session, styleBookEntry, groupId, name, head, orderByComparator,
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

	protected StyleBookEntry getByG_LikeN_Head_PrevAndNext(
		Session session, StyleBookEntry styleBookEntry, long groupId,
		String name, boolean head,
		OrderByComparator<StyleBookEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		sb.append(_SQL_SELECT_STYLEBOOKENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_LIKEN_HEAD_GROUPID_2);

		boolean bindName = false;

		if (name.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_LIKEN_HEAD_NAME_3);
		}
		else {
			bindName = true;

			sb.append(_FINDER_COLUMN_G_LIKEN_HEAD_NAME_2);
		}

		sb.append(_FINDER_COLUMN_G_LIKEN_HEAD_HEAD_2);

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
			sb.append(StyleBookEntryModelImpl.ORDER_BY_JPQL);
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

		queryPos.add(head);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						styleBookEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<StyleBookEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the style book entries where groupId = &#63; and name LIKE &#63; and head = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param head the head
	 */
	@Override
	public void removeByG_LikeN_Head(long groupId, String name, boolean head) {
		for (StyleBookEntry styleBookEntry :
				findByG_LikeN_Head(
					groupId, name, head, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(styleBookEntry);
		}
	}

	/**
	 * Returns the number of style book entries where groupId = &#63; and name LIKE &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param head the head
	 * @return the number of matching style book entries
	 */
	@Override
	public int countByG_LikeN_Head(long groupId, String name, boolean head) {
		name = Objects.toString(name, "");

		FinderPath finderPath = _finderPathWithPaginationCountByG_LikeN_Head;

		Object[] finderArgs = new Object[] {groupId, name, head};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_STYLEBOOKENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_LIKEN_HEAD_GROUPID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_LIKEN_HEAD_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_G_LIKEN_HEAD_NAME_2);
			}

			sb.append(_FINDER_COLUMN_G_LIKEN_HEAD_HEAD_2);

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

				queryPos.add(head);

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

	private static final String _FINDER_COLUMN_G_LIKEN_HEAD_GROUPID_2 =
		"styleBookEntry.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_LIKEN_HEAD_NAME_2 =
		"styleBookEntry.name LIKE ? AND ";

	private static final String _FINDER_COLUMN_G_LIKEN_HEAD_NAME_3 =
		"(styleBookEntry.name IS NULL OR styleBookEntry.name LIKE '') AND ";

	private static final String _FINDER_COLUMN_G_LIKEN_HEAD_HEAD_2 =
		"styleBookEntry.head = ?";

	private FinderPath _finderPathWithPaginationFindByG_SBEK;
	private FinderPath _finderPathWithoutPaginationFindByG_SBEK;
	private FinderPath _finderPathCountByG_SBEK;

	/**
	 * Returns all the style book entries where groupId = &#63; and styleBookEntryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param styleBookEntryKey the style book entry key
	 * @return the matching style book entries
	 */
	@Override
	public List<StyleBookEntry> findByG_SBEK(
		long groupId, String styleBookEntryKey) {

		return findByG_SBEK(
			groupId, styleBookEntryKey, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the style book entries where groupId = &#63; and styleBookEntryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param styleBookEntryKey the style book entry key
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @return the range of matching style book entries
	 */
	@Override
	public List<StyleBookEntry> findByG_SBEK(
		long groupId, String styleBookEntryKey, int start, int end) {

		return findByG_SBEK(groupId, styleBookEntryKey, start, end, null);
	}

	/**
	 * Returns an ordered range of all the style book entries where groupId = &#63; and styleBookEntryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param styleBookEntryKey the style book entry key
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching style book entries
	 */
	@Override
	public List<StyleBookEntry> findByG_SBEK(
		long groupId, String styleBookEntryKey, int start, int end,
		OrderByComparator<StyleBookEntry> orderByComparator) {

		return findByG_SBEK(
			groupId, styleBookEntryKey, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the style book entries where groupId = &#63; and styleBookEntryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param styleBookEntryKey the style book entry key
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching style book entries
	 */
	@Override
	public List<StyleBookEntry> findByG_SBEK(
		long groupId, String styleBookEntryKey, int start, int end,
		OrderByComparator<StyleBookEntry> orderByComparator,
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

		List<StyleBookEntry> list = null;

		if (useFinderCache) {
			list = (List<StyleBookEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (StyleBookEntry styleBookEntry : list) {
					if ((groupId != styleBookEntry.getGroupId()) ||
						!styleBookEntryKey.equals(
							styleBookEntry.getStyleBookEntryKey())) {

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

			sb.append(_SQL_SELECT_STYLEBOOKENTRY_WHERE);

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
				sb.append(StyleBookEntryModelImpl.ORDER_BY_JPQL);
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

				list = (List<StyleBookEntry>)QueryUtil.list(
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
	 * Returns the first style book entry in the ordered set where groupId = &#63; and styleBookEntryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param styleBookEntryKey the style book entry key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry findByG_SBEK_First(
			long groupId, String styleBookEntryKey,
			OrderByComparator<StyleBookEntry> orderByComparator)
		throws NoSuchEntryException {

		StyleBookEntry styleBookEntry = fetchByG_SBEK_First(
			groupId, styleBookEntryKey, orderByComparator);

		if (styleBookEntry != null) {
			return styleBookEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", styleBookEntryKey=");
		sb.append(styleBookEntryKey);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first style book entry in the ordered set where groupId = &#63; and styleBookEntryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param styleBookEntryKey the style book entry key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry fetchByG_SBEK_First(
		long groupId, String styleBookEntryKey,
		OrderByComparator<StyleBookEntry> orderByComparator) {

		List<StyleBookEntry> list = findByG_SBEK(
			groupId, styleBookEntryKey, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last style book entry in the ordered set where groupId = &#63; and styleBookEntryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param styleBookEntryKey the style book entry key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry findByG_SBEK_Last(
			long groupId, String styleBookEntryKey,
			OrderByComparator<StyleBookEntry> orderByComparator)
		throws NoSuchEntryException {

		StyleBookEntry styleBookEntry = fetchByG_SBEK_Last(
			groupId, styleBookEntryKey, orderByComparator);

		if (styleBookEntry != null) {
			return styleBookEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", styleBookEntryKey=");
		sb.append(styleBookEntryKey);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last style book entry in the ordered set where groupId = &#63; and styleBookEntryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param styleBookEntryKey the style book entry key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry fetchByG_SBEK_Last(
		long groupId, String styleBookEntryKey,
		OrderByComparator<StyleBookEntry> orderByComparator) {

		int count = countByG_SBEK(groupId, styleBookEntryKey);

		if (count == 0) {
			return null;
		}

		List<StyleBookEntry> list = findByG_SBEK(
			groupId, styleBookEntryKey, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the style book entries before and after the current style book entry in the ordered set where groupId = &#63; and styleBookEntryKey = &#63;.
	 *
	 * @param styleBookEntryId the primary key of the current style book entry
	 * @param groupId the group ID
	 * @param styleBookEntryKey the style book entry key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next style book entry
	 * @throws NoSuchEntryException if a style book entry with the primary key could not be found
	 */
	@Override
	public StyleBookEntry[] findByG_SBEK_PrevAndNext(
			long styleBookEntryId, long groupId, String styleBookEntryKey,
			OrderByComparator<StyleBookEntry> orderByComparator)
		throws NoSuchEntryException {

		styleBookEntryKey = Objects.toString(styleBookEntryKey, "");

		StyleBookEntry styleBookEntry = findByPrimaryKey(styleBookEntryId);

		Session session = null;

		try {
			session = openSession();

			StyleBookEntry[] array = new StyleBookEntryImpl[3];

			array[0] = getByG_SBEK_PrevAndNext(
				session, styleBookEntry, groupId, styleBookEntryKey,
				orderByComparator, true);

			array[1] = styleBookEntry;

			array[2] = getByG_SBEK_PrevAndNext(
				session, styleBookEntry, groupId, styleBookEntryKey,
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

	protected StyleBookEntry getByG_SBEK_PrevAndNext(
		Session session, StyleBookEntry styleBookEntry, long groupId,
		String styleBookEntryKey,
		OrderByComparator<StyleBookEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_STYLEBOOKENTRY_WHERE);

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
			sb.append(StyleBookEntryModelImpl.ORDER_BY_JPQL);
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
						styleBookEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<StyleBookEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the style book entries where groupId = &#63; and styleBookEntryKey = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param styleBookEntryKey the style book entry key
	 */
	@Override
	public void removeByG_SBEK(long groupId, String styleBookEntryKey) {
		for (StyleBookEntry styleBookEntry :
				findByG_SBEK(
					groupId, styleBookEntryKey, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(styleBookEntry);
		}
	}

	/**
	 * Returns the number of style book entries where groupId = &#63; and styleBookEntryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param styleBookEntryKey the style book entry key
	 * @return the number of matching style book entries
	 */
	@Override
	public int countByG_SBEK(long groupId, String styleBookEntryKey) {
		styleBookEntryKey = Objects.toString(styleBookEntryKey, "");

		FinderPath finderPath = _finderPathCountByG_SBEK;

		Object[] finderArgs = new Object[] {groupId, styleBookEntryKey};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_STYLEBOOKENTRY_WHERE);

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
		"styleBookEntry.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_SBEK_STYLEBOOKENTRYKEY_2 =
		"styleBookEntry.styleBookEntryKey = ?";

	private static final String _FINDER_COLUMN_G_SBEK_STYLEBOOKENTRYKEY_3 =
		"(styleBookEntry.styleBookEntryKey IS NULL OR styleBookEntry.styleBookEntryKey = '')";

	private FinderPath _finderPathFetchByG_SBEK_Head;
	private FinderPath _finderPathCountByG_SBEK_Head;

	/**
	 * Returns the style book entry where groupId = &#63; and styleBookEntryKey = &#63; and head = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param styleBookEntryKey the style book entry key
	 * @param head the head
	 * @return the matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry findByG_SBEK_Head(
			long groupId, String styleBookEntryKey, boolean head)
		throws NoSuchEntryException {

		StyleBookEntry styleBookEntry = fetchByG_SBEK_Head(
			groupId, styleBookEntryKey, head);

		if (styleBookEntry == null) {
			StringBundler sb = new StringBundler(8);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("groupId=");
			sb.append(groupId);

			sb.append(", styleBookEntryKey=");
			sb.append(styleBookEntryKey);

			sb.append(", head=");
			sb.append(head);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchEntryException(sb.toString());
		}

		return styleBookEntry;
	}

	/**
	 * Returns the style book entry where groupId = &#63; and styleBookEntryKey = &#63; and head = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param styleBookEntryKey the style book entry key
	 * @param head the head
	 * @return the matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry fetchByG_SBEK_Head(
		long groupId, String styleBookEntryKey, boolean head) {

		return fetchByG_SBEK_Head(groupId, styleBookEntryKey, head, true);
	}

	/**
	 * Returns the style book entry where groupId = &#63; and styleBookEntryKey = &#63; and head = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param styleBookEntryKey the style book entry key
	 * @param head the head
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry fetchByG_SBEK_Head(
		long groupId, String styleBookEntryKey, boolean head,
		boolean useFinderCache) {

		styleBookEntryKey = Objects.toString(styleBookEntryKey, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {groupId, styleBookEntryKey, head};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByG_SBEK_Head, finderArgs, this);
		}

		if (result instanceof StyleBookEntry) {
			StyleBookEntry styleBookEntry = (StyleBookEntry)result;

			if ((groupId != styleBookEntry.getGroupId()) ||
				!Objects.equals(
					styleBookEntryKey, styleBookEntry.getStyleBookEntryKey()) ||
				(head != styleBookEntry.isHead())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_SELECT_STYLEBOOKENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_SBEK_HEAD_GROUPID_2);

			boolean bindStyleBookEntryKey = false;

			if (styleBookEntryKey.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_SBEK_HEAD_STYLEBOOKENTRYKEY_3);
			}
			else {
				bindStyleBookEntryKey = true;

				sb.append(_FINDER_COLUMN_G_SBEK_HEAD_STYLEBOOKENTRYKEY_2);
			}

			sb.append(_FINDER_COLUMN_G_SBEK_HEAD_HEAD_2);

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

				queryPos.add(head);

				List<StyleBookEntry> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByG_SBEK_Head, finderArgs, list);
					}
				}
				else {
					StyleBookEntry styleBookEntry = list.get(0);

					result = styleBookEntry;

					cacheResult(styleBookEntry);
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
			return (StyleBookEntry)result;
		}
	}

	/**
	 * Removes the style book entry where groupId = &#63; and styleBookEntryKey = &#63; and head = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param styleBookEntryKey the style book entry key
	 * @param head the head
	 * @return the style book entry that was removed
	 */
	@Override
	public StyleBookEntry removeByG_SBEK_Head(
			long groupId, String styleBookEntryKey, boolean head)
		throws NoSuchEntryException {

		StyleBookEntry styleBookEntry = findByG_SBEK_Head(
			groupId, styleBookEntryKey, head);

		return remove(styleBookEntry);
	}

	/**
	 * Returns the number of style book entries where groupId = &#63; and styleBookEntryKey = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param styleBookEntryKey the style book entry key
	 * @param head the head
	 * @return the number of matching style book entries
	 */
	@Override
	public int countByG_SBEK_Head(
		long groupId, String styleBookEntryKey, boolean head) {

		styleBookEntryKey = Objects.toString(styleBookEntryKey, "");

		FinderPath finderPath = _finderPathCountByG_SBEK_Head;

		Object[] finderArgs = new Object[] {groupId, styleBookEntryKey, head};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_STYLEBOOKENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_SBEK_HEAD_GROUPID_2);

			boolean bindStyleBookEntryKey = false;

			if (styleBookEntryKey.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_SBEK_HEAD_STYLEBOOKENTRYKEY_3);
			}
			else {
				bindStyleBookEntryKey = true;

				sb.append(_FINDER_COLUMN_G_SBEK_HEAD_STYLEBOOKENTRYKEY_2);
			}

			sb.append(_FINDER_COLUMN_G_SBEK_HEAD_HEAD_2);

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

				queryPos.add(head);

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

	private static final String _FINDER_COLUMN_G_SBEK_HEAD_GROUPID_2 =
		"styleBookEntry.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_SBEK_HEAD_STYLEBOOKENTRYKEY_2 =
		"styleBookEntry.styleBookEntryKey = ? AND ";

	private static final String _FINDER_COLUMN_G_SBEK_HEAD_STYLEBOOKENTRYKEY_3 =
		"(styleBookEntry.styleBookEntryKey IS NULL OR styleBookEntry.styleBookEntryKey = '') AND ";

	private static final String _FINDER_COLUMN_G_SBEK_HEAD_HEAD_2 =
		"styleBookEntry.head = ?";

	private FinderPath _finderPathFetchByHeadId;
	private FinderPath _finderPathCountByHeadId;

	/**
	 * Returns the style book entry where headId = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param headId the head ID
	 * @return the matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry findByHeadId(long headId)
		throws NoSuchEntryException {

		StyleBookEntry styleBookEntry = fetchByHeadId(headId);

		if (styleBookEntry == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("headId=");
			sb.append(headId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchEntryException(sb.toString());
		}

		return styleBookEntry;
	}

	/**
	 * Returns the style book entry where headId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param headId the head ID
	 * @return the matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry fetchByHeadId(long headId) {
		return fetchByHeadId(headId, true);
	}

	/**
	 * Returns the style book entry where headId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param headId the head ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry fetchByHeadId(long headId, boolean useFinderCache) {
		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {headId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByHeadId, finderArgs, this);
		}

		if (result instanceof StyleBookEntry) {
			StyleBookEntry styleBookEntry = (StyleBookEntry)result;

			if (headId != styleBookEntry.getHeadId()) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_SELECT_STYLEBOOKENTRY_WHERE);

			sb.append(_FINDER_COLUMN_HEADID_HEADID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(headId);

				List<StyleBookEntry> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByHeadId, finderArgs, list);
					}
				}
				else {
					StyleBookEntry styleBookEntry = list.get(0);

					result = styleBookEntry;

					cacheResult(styleBookEntry);
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
			return (StyleBookEntry)result;
		}
	}

	/**
	 * Removes the style book entry where headId = &#63; from the database.
	 *
	 * @param headId the head ID
	 * @return the style book entry that was removed
	 */
	@Override
	public StyleBookEntry removeByHeadId(long headId)
		throws NoSuchEntryException {

		StyleBookEntry styleBookEntry = findByHeadId(headId);

		return remove(styleBookEntry);
	}

	/**
	 * Returns the number of style book entries where headId = &#63;.
	 *
	 * @param headId the head ID
	 * @return the number of matching style book entries
	 */
	@Override
	public int countByHeadId(long headId) {
		FinderPath finderPath = _finderPathCountByHeadId;

		Object[] finderArgs = new Object[] {headId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_STYLEBOOKENTRY_WHERE);

			sb.append(_FINDER_COLUMN_HEADID_HEADID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(headId);

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

	private static final String _FINDER_COLUMN_HEADID_HEADID_2 =
		"styleBookEntry.headId = ?";

	public StyleBookEntryPersistenceImpl() {
		setModelClass(StyleBookEntry.class);

		setModelImplClass(StyleBookEntryImpl.class);
		setModelPKClass(long.class);

		setTable(StyleBookEntryTable.INSTANCE);
	}

	/**
	 * Caches the style book entry in the entity cache if it is enabled.
	 *
	 * @param styleBookEntry the style book entry
	 */
	@Override
	public void cacheResult(StyleBookEntry styleBookEntry) {
		entityCache.putResult(
			StyleBookEntryImpl.class, styleBookEntry.getPrimaryKey(),
			styleBookEntry);

		finderCache.putResult(
			_finderPathFetchByG_SBEK_Head,
			new Object[] {
				styleBookEntry.getGroupId(),
				styleBookEntry.getStyleBookEntryKey(), styleBookEntry.isHead()
			},
			styleBookEntry);

		finderCache.putResult(
			_finderPathFetchByHeadId, new Object[] {styleBookEntry.getHeadId()},
			styleBookEntry);
	}

	/**
	 * Caches the style book entries in the entity cache if it is enabled.
	 *
	 * @param styleBookEntries the style book entries
	 */
	@Override
	public void cacheResult(List<StyleBookEntry> styleBookEntries) {
		for (StyleBookEntry styleBookEntry : styleBookEntries) {
			if (entityCache.getResult(
					StyleBookEntryImpl.class, styleBookEntry.getPrimaryKey()) ==
						null) {

				cacheResult(styleBookEntry);
			}
		}
	}

	/**
	 * Clears the cache for all style book entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(StyleBookEntryImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the style book entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(StyleBookEntry styleBookEntry) {
		entityCache.removeResult(
			StyleBookEntryImpl.class, styleBookEntry.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((StyleBookEntryModelImpl)styleBookEntry, true);
	}

	@Override
	public void clearCache(List<StyleBookEntry> styleBookEntries) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (StyleBookEntry styleBookEntry : styleBookEntries) {
			entityCache.removeResult(
				StyleBookEntryImpl.class, styleBookEntry.getPrimaryKey());

			clearUniqueFindersCache(
				(StyleBookEntryModelImpl)styleBookEntry, true);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(StyleBookEntryImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		StyleBookEntryModelImpl styleBookEntryModelImpl) {

		Object[] args = new Object[] {
			styleBookEntryModelImpl.getGroupId(),
			styleBookEntryModelImpl.getStyleBookEntryKey(),
			styleBookEntryModelImpl.isHead()
		};

		finderCache.putResult(
			_finderPathCountByG_SBEK_Head, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByG_SBEK_Head, args, styleBookEntryModelImpl,
			false);

		args = new Object[] {styleBookEntryModelImpl.getHeadId()};

		finderCache.putResult(
			_finderPathCountByHeadId, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByHeadId, args, styleBookEntryModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		StyleBookEntryModelImpl styleBookEntryModelImpl, boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				styleBookEntryModelImpl.getGroupId(),
				styleBookEntryModelImpl.getStyleBookEntryKey(),
				styleBookEntryModelImpl.isHead()
			};

			finderCache.removeResult(_finderPathCountByG_SBEK_Head, args);
			finderCache.removeResult(_finderPathFetchByG_SBEK_Head, args);
		}

		if ((styleBookEntryModelImpl.getColumnBitmask() &
			 _finderPathFetchByG_SBEK_Head.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				styleBookEntryModelImpl.getColumnOriginalValue("groupId"),
				styleBookEntryModelImpl.getColumnOriginalValue(
					"styleBookEntryKey"),
				styleBookEntryModelImpl.getColumnOriginalValue("head")
			};

			finderCache.removeResult(_finderPathCountByG_SBEK_Head, args);
			finderCache.removeResult(_finderPathFetchByG_SBEK_Head, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {styleBookEntryModelImpl.getHeadId()};

			finderCache.removeResult(_finderPathCountByHeadId, args);
			finderCache.removeResult(_finderPathFetchByHeadId, args);
		}

		if ((styleBookEntryModelImpl.getColumnBitmask() &
			 _finderPathFetchByHeadId.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				styleBookEntryModelImpl.getColumnOriginalValue("headId")
			};

			finderCache.removeResult(_finderPathCountByHeadId, args);
			finderCache.removeResult(_finderPathFetchByHeadId, args);
		}
	}

	/**
	 * Creates a new style book entry with the primary key. Does not add the style book entry to the database.
	 *
	 * @param styleBookEntryId the primary key for the new style book entry
	 * @return the new style book entry
	 */
	@Override
	public StyleBookEntry create(long styleBookEntryId) {
		StyleBookEntry styleBookEntry = new StyleBookEntryImpl();

		styleBookEntry.setNew(true);
		styleBookEntry.setPrimaryKey(styleBookEntryId);

		styleBookEntry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return styleBookEntry;
	}

	/**
	 * Removes the style book entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param styleBookEntryId the primary key of the style book entry
	 * @return the style book entry that was removed
	 * @throws NoSuchEntryException if a style book entry with the primary key could not be found
	 */
	@Override
	public StyleBookEntry remove(long styleBookEntryId)
		throws NoSuchEntryException {

		return remove((Serializable)styleBookEntryId);
	}

	/**
	 * Removes the style book entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the style book entry
	 * @return the style book entry that was removed
	 * @throws NoSuchEntryException if a style book entry with the primary key could not be found
	 */
	@Override
	public StyleBookEntry remove(Serializable primaryKey)
		throws NoSuchEntryException {

		Session session = null;

		try {
			session = openSession();

			StyleBookEntry styleBookEntry = (StyleBookEntry)session.get(
				StyleBookEntryImpl.class, primaryKey);

			if (styleBookEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(styleBookEntry);
		}
		catch (NoSuchEntryException noSuchEntityException) {
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
	protected StyleBookEntry removeImpl(StyleBookEntry styleBookEntry) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(styleBookEntry)) {
				styleBookEntry = (StyleBookEntry)session.get(
					StyleBookEntryImpl.class,
					styleBookEntry.getPrimaryKeyObj());
			}

			if (styleBookEntry != null) {
				session.delete(styleBookEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (styleBookEntry != null) {
			clearCache(styleBookEntry);
		}

		return styleBookEntry;
	}

	@Override
	public StyleBookEntry updateImpl(StyleBookEntry styleBookEntry) {
		boolean isNew = styleBookEntry.isNew();

		if (!(styleBookEntry instanceof StyleBookEntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(styleBookEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					styleBookEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in styleBookEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom StyleBookEntry implementation " +
					styleBookEntry.getClass());
		}

		StyleBookEntryModelImpl styleBookEntryModelImpl =
			(StyleBookEntryModelImpl)styleBookEntry;

		Session session = null;

		try {
			session = openSession();

			if (styleBookEntry.isNew()) {
				session.save(styleBookEntry);

				styleBookEntry.setNew(false);
			}
			else {
				styleBookEntry = (StyleBookEntry)session.merge(styleBookEntry);
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
			Object[] args = new Object[] {styleBookEntryModelImpl.getGroupId()};

			finderCache.removeResult(_finderPathCountByGroupId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByGroupId, args);

			args = new Object[] {
				styleBookEntryModelImpl.getGroupId(),
				styleBookEntryModelImpl.isHead()
			};

			finderCache.removeResult(_finderPathCountByGroupId_Head, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByGroupId_Head, args);

			args = new Object[] {
				styleBookEntryModelImpl.getGroupId(),
				styleBookEntryModelImpl.isDefaultStyleBookEntry()
			};

			finderCache.removeResult(_finderPathCountByG_D, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByG_D, args);

			args = new Object[] {
				styleBookEntryModelImpl.getGroupId(),
				styleBookEntryModelImpl.isDefaultStyleBookEntry(),
				styleBookEntryModelImpl.isHead()
			};

			finderCache.removeResult(_finderPathCountByG_D_Head, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByG_D_Head, args);

			args = new Object[] {
				styleBookEntryModelImpl.getGroupId(),
				styleBookEntryModelImpl.getStyleBookEntryKey()
			};

			finderCache.removeResult(_finderPathCountByG_SBEK, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByG_SBEK, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((styleBookEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByGroupId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					styleBookEntryModelImpl.getColumnOriginalValue("groupId")
				};

				finderCache.removeResult(_finderPathCountByGroupId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByGroupId, args);

				args = new Object[] {styleBookEntryModelImpl.getGroupId()};

				finderCache.removeResult(_finderPathCountByGroupId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByGroupId, args);
			}

			if ((styleBookEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByGroupId_Head.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					styleBookEntryModelImpl.getColumnOriginalValue("groupId"),
					styleBookEntryModelImpl.getColumnOriginalValue("head")
				};

				finderCache.removeResult(_finderPathCountByGroupId_Head, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByGroupId_Head, args);

				args = new Object[] {
					styleBookEntryModelImpl.getGroupId(),
					styleBookEntryModelImpl.isHead()
				};

				finderCache.removeResult(_finderPathCountByGroupId_Head, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByGroupId_Head, args);
			}

			if ((styleBookEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_D.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					styleBookEntryModelImpl.getColumnOriginalValue("groupId"),
					styleBookEntryModelImpl.getColumnOriginalValue(
						"defaultStyleBookEntry")
				};

				finderCache.removeResult(_finderPathCountByG_D, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_D, args);

				args = new Object[] {
					styleBookEntryModelImpl.getGroupId(),
					styleBookEntryModelImpl.isDefaultStyleBookEntry()
				};

				finderCache.removeResult(_finderPathCountByG_D, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_D, args);
			}

			if ((styleBookEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_D_Head.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					styleBookEntryModelImpl.getColumnOriginalValue("groupId"),
					styleBookEntryModelImpl.getColumnOriginalValue(
						"defaultStyleBookEntry"),
					styleBookEntryModelImpl.getColumnOriginalValue("head")
				};

				finderCache.removeResult(_finderPathCountByG_D_Head, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_D_Head, args);

				args = new Object[] {
					styleBookEntryModelImpl.getGroupId(),
					styleBookEntryModelImpl.isDefaultStyleBookEntry(),
					styleBookEntryModelImpl.isHead()
				};

				finderCache.removeResult(_finderPathCountByG_D_Head, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_D_Head, args);
			}

			if ((styleBookEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_SBEK.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					styleBookEntryModelImpl.getColumnOriginalValue("groupId"),
					styleBookEntryModelImpl.getColumnOriginalValue(
						"styleBookEntryKey")
				};

				finderCache.removeResult(_finderPathCountByG_SBEK, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_SBEK, args);

				args = new Object[] {
					styleBookEntryModelImpl.getGroupId(),
					styleBookEntryModelImpl.getStyleBookEntryKey()
				};

				finderCache.removeResult(_finderPathCountByG_SBEK, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_SBEK, args);
			}
		}

		entityCache.putResult(
			StyleBookEntryImpl.class, styleBookEntry.getPrimaryKey(),
			styleBookEntry, false);

		clearUniqueFindersCache(styleBookEntryModelImpl, false);
		cacheUniqueFindersCache(styleBookEntryModelImpl);

		styleBookEntry.resetOriginalValues();

		return styleBookEntry;
	}

	/**
	 * Returns the style book entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the style book entry
	 * @return the style book entry
	 * @throws NoSuchEntryException if a style book entry with the primary key could not be found
	 */
	@Override
	public StyleBookEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchEntryException {

		StyleBookEntry styleBookEntry = fetchByPrimaryKey(primaryKey);

		if (styleBookEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return styleBookEntry;
	}

	/**
	 * Returns the style book entry with the primary key or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param styleBookEntryId the primary key of the style book entry
	 * @return the style book entry
	 * @throws NoSuchEntryException if a style book entry with the primary key could not be found
	 */
	@Override
	public StyleBookEntry findByPrimaryKey(long styleBookEntryId)
		throws NoSuchEntryException {

		return findByPrimaryKey((Serializable)styleBookEntryId);
	}

	/**
	 * Returns the style book entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param styleBookEntryId the primary key of the style book entry
	 * @return the style book entry, or <code>null</code> if a style book entry with the primary key could not be found
	 */
	@Override
	public StyleBookEntry fetchByPrimaryKey(long styleBookEntryId) {
		return fetchByPrimaryKey((Serializable)styleBookEntryId);
	}

	/**
	 * Returns all the style book entries.
	 *
	 * @return the style book entries
	 */
	@Override
	public List<StyleBookEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the style book entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @return the range of style book entries
	 */
	@Override
	public List<StyleBookEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the style book entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of style book entries
	 */
	@Override
	public List<StyleBookEntry> findAll(
		int start, int end,
		OrderByComparator<StyleBookEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the style book entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of style book entries
	 */
	@Override
	public List<StyleBookEntry> findAll(
		int start, int end, OrderByComparator<StyleBookEntry> orderByComparator,
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

		List<StyleBookEntry> list = null;

		if (useFinderCache) {
			list = (List<StyleBookEntry>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_STYLEBOOKENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_STYLEBOOKENTRY;

				sql = sql.concat(StyleBookEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<StyleBookEntry>)QueryUtil.list(
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
	 * Removes all the style book entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (StyleBookEntry styleBookEntry : findAll()) {
			remove(styleBookEntry);
		}
	}

	/**
	 * Returns the number of style book entries.
	 *
	 * @return the number of style book entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_STYLEBOOKENTRY);

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
		return "styleBookEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_STYLEBOOKENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return StyleBookEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the style book entry persistence.
	 */
	@Activate
	public void activate() {
		_finderPathWithPaginationFindAll = new FinderPath(
			StyleBookEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			StyleBookEntryImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findAll", new String[0]);

		_finderPathCountAll = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByGroupId = new FinderPath(
			StyleBookEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByGroupId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByGroupId = new FinderPath(
			StyleBookEntryImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByGroupId", new String[] {Long.class.getName()},
			StyleBookEntryModelImpl.getColumnBitmask("groupId") |
			StyleBookEntryModelImpl.getColumnBitmask("createDate"));

		_finderPathCountByGroupId = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByGroupId", new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByGroupId_Head = new FinderPath(
			StyleBookEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByGroupId_Head",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByGroupId_Head = new FinderPath(
			StyleBookEntryImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByGroupId_Head",
			new String[] {Long.class.getName(), Boolean.class.getName()},
			StyleBookEntryModelImpl.getColumnBitmask("groupId") |
			StyleBookEntryModelImpl.getColumnBitmask("head") |
			StyleBookEntryModelImpl.getColumnBitmask("createDate"));

		_finderPathCountByGroupId_Head = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByGroupId_Head",
			new String[] {Long.class.getName(), Boolean.class.getName()});

		_finderPathWithPaginationFindByG_D = new FinderPath(
			StyleBookEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_D",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_D = new FinderPath(
			StyleBookEntryImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByG_D",
			new String[] {Long.class.getName(), Boolean.class.getName()},
			StyleBookEntryModelImpl.getColumnBitmask("groupId") |
			StyleBookEntryModelImpl.getColumnBitmask("defaultStyleBookEntry") |
			StyleBookEntryModelImpl.getColumnBitmask("createDate"));

		_finderPathCountByG_D = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_D",
			new String[] {Long.class.getName(), Boolean.class.getName()});

		_finderPathWithPaginationFindByG_D_Head = new FinderPath(
			StyleBookEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_D_Head",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Boolean.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_D_Head = new FinderPath(
			StyleBookEntryImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByG_D_Head",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Boolean.class.getName()
			},
			StyleBookEntryModelImpl.getColumnBitmask("groupId") |
			StyleBookEntryModelImpl.getColumnBitmask("defaultStyleBookEntry") |
			StyleBookEntryModelImpl.getColumnBitmask("head") |
			StyleBookEntryModelImpl.getColumnBitmask("createDate"));

		_finderPathCountByG_D_Head = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_D_Head",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Boolean.class.getName()
			});

		_finderPathWithPaginationFindByG_LikeN = new FinderPath(
			StyleBookEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_LikeN",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithPaginationCountByG_LikeN = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"countByG_LikeN",
			new String[] {Long.class.getName(), String.class.getName()});

		_finderPathWithPaginationFindByG_LikeN_Head = new FinderPath(
			StyleBookEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_LikeN_Head",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Boolean.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithPaginationCountByG_LikeN_Head = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"countByG_LikeN_Head",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Boolean.class.getName()
			});

		_finderPathWithPaginationFindByG_SBEK = new FinderPath(
			StyleBookEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_SBEK",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_SBEK = new FinderPath(
			StyleBookEntryImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByG_SBEK",
			new String[] {Long.class.getName(), String.class.getName()},
			StyleBookEntryModelImpl.getColumnBitmask("groupId") |
			StyleBookEntryModelImpl.getColumnBitmask("styleBookEntryKey") |
			StyleBookEntryModelImpl.getColumnBitmask("createDate"));

		_finderPathCountByG_SBEK = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_SBEK",
			new String[] {Long.class.getName(), String.class.getName()});

		_finderPathFetchByG_SBEK_Head = new FinderPath(
			StyleBookEntryImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByG_SBEK_Head",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Boolean.class.getName()
			},
			StyleBookEntryModelImpl.getColumnBitmask("groupId") |
			StyleBookEntryModelImpl.getColumnBitmask("styleBookEntryKey") |
			StyleBookEntryModelImpl.getColumnBitmask("head"));

		_finderPathCountByG_SBEK_Head = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_SBEK_Head",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Boolean.class.getName()
			});

		_finderPathFetchByHeadId = new FinderPath(
			StyleBookEntryImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByHeadId",
			new String[] {Long.class.getName()},
			StyleBookEntryModelImpl.getColumnBitmask("headId"));

		_finderPathCountByHeadId = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByHeadId", new String[] {Long.class.getName()});
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(StyleBookEntryImpl.class.getName());
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

	private static final String _SQL_SELECT_STYLEBOOKENTRY =
		"SELECT styleBookEntry FROM StyleBookEntry styleBookEntry";

	private static final String _SQL_SELECT_STYLEBOOKENTRY_WHERE =
		"SELECT styleBookEntry FROM StyleBookEntry styleBookEntry WHERE ";

	private static final String _SQL_COUNT_STYLEBOOKENTRY =
		"SELECT COUNT(styleBookEntry) FROM StyleBookEntry styleBookEntry";

	private static final String _SQL_COUNT_STYLEBOOKENTRY_WHERE =
		"SELECT COUNT(styleBookEntry) FROM StyleBookEntry styleBookEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "styleBookEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No StyleBookEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No StyleBookEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		StyleBookEntryPersistenceImpl.class);

	static {
		try {
			Class.forName(StyleBookPersistenceConstants.class.getName());
		}
		catch (ClassNotFoundException classNotFoundException) {
			throw new ExceptionInInitializerError(classNotFoundException);
		}
	}

}