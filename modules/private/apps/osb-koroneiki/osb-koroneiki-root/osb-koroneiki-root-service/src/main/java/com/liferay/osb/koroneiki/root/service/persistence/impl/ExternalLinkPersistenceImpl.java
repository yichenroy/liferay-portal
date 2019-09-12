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

package com.liferay.osb.koroneiki.root.service.persistence.impl;

import com.liferay.osb.koroneiki.root.exception.NoSuchExternalLinkException;
import com.liferay.osb.koroneiki.root.model.ExternalLink;
import com.liferay.osb.koroneiki.root.model.impl.ExternalLinkImpl;
import com.liferay.osb.koroneiki.root.model.impl.ExternalLinkModelImpl;
import com.liferay.osb.koroneiki.root.service.persistence.ExternalLinkPersistence;
import com.liferay.osb.koroneiki.root.service.persistence.impl.constants.KoroneikiPersistenceConstants;
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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the external link service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = ExternalLinkPersistence.class)
public class ExternalLinkPersistenceImpl
	extends BasePersistenceImpl<ExternalLink>
	implements ExternalLinkPersistence {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>ExternalLinkUtil</code> to access the external link persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		ExternalLinkImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathFetchByExternalLinkKey;
	private FinderPath _finderPathCountByExternalLinkKey;

	/**
	 * Returns the external link where externalLinkKey = &#63; or throws a <code>NoSuchExternalLinkException</code> if it could not be found.
	 *
	 * @param externalLinkKey the external link key
	 * @return the matching external link
	 * @throws NoSuchExternalLinkException if a matching external link could not be found
	 */
	@Override
	public ExternalLink findByExternalLinkKey(String externalLinkKey)
		throws NoSuchExternalLinkException {

		ExternalLink externalLink = fetchByExternalLinkKey(externalLinkKey);

		if (externalLink == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("externalLinkKey=");
			msg.append(externalLinkKey);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchExternalLinkException(msg.toString());
		}

		return externalLink;
	}

	/**
	 * Returns the external link where externalLinkKey = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param externalLinkKey the external link key
	 * @return the matching external link, or <code>null</code> if a matching external link could not be found
	 */
	@Override
	public ExternalLink fetchByExternalLinkKey(String externalLinkKey) {
		return fetchByExternalLinkKey(externalLinkKey, true);
	}

	/**
	 * Returns the external link where externalLinkKey = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param externalLinkKey the external link key
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching external link, or <code>null</code> if a matching external link could not be found
	 */
	@Override
	public ExternalLink fetchByExternalLinkKey(
		String externalLinkKey, boolean useFinderCache) {

		externalLinkKey = Objects.toString(externalLinkKey, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {externalLinkKey};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByExternalLinkKey, finderArgs, this);
		}

		if (result instanceof ExternalLink) {
			ExternalLink externalLink = (ExternalLink)result;

			if (!Objects.equals(
					externalLinkKey, externalLink.getExternalLinkKey())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_EXTERNALLINK_WHERE);

			boolean bindExternalLinkKey = false;

			if (externalLinkKey.isEmpty()) {
				query.append(_FINDER_COLUMN_EXTERNALLINKKEY_EXTERNALLINKKEY_3);
			}
			else {
				bindExternalLinkKey = true;

				query.append(_FINDER_COLUMN_EXTERNALLINKKEY_EXTERNALLINKKEY_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindExternalLinkKey) {
					qPos.add(externalLinkKey);
				}

				List<ExternalLink> list = q.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByExternalLinkKey, finderArgs,
							list);
					}
				}
				else {
					ExternalLink externalLink = list.get(0);

					result = externalLink;

					cacheResult(externalLink);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(
						_finderPathFetchByExternalLinkKey, finderArgs);
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
			return (ExternalLink)result;
		}
	}

	/**
	 * Removes the external link where externalLinkKey = &#63; from the database.
	 *
	 * @param externalLinkKey the external link key
	 * @return the external link that was removed
	 */
	@Override
	public ExternalLink removeByExternalLinkKey(String externalLinkKey)
		throws NoSuchExternalLinkException {

		ExternalLink externalLink = findByExternalLinkKey(externalLinkKey);

		return remove(externalLink);
	}

	/**
	 * Returns the number of external links where externalLinkKey = &#63;.
	 *
	 * @param externalLinkKey the external link key
	 * @return the number of matching external links
	 */
	@Override
	public int countByExternalLinkKey(String externalLinkKey) {
		externalLinkKey = Objects.toString(externalLinkKey, "");

		FinderPath finderPath = _finderPathCountByExternalLinkKey;

		Object[] finderArgs = new Object[] {externalLinkKey};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_EXTERNALLINK_WHERE);

			boolean bindExternalLinkKey = false;

			if (externalLinkKey.isEmpty()) {
				query.append(_FINDER_COLUMN_EXTERNALLINKKEY_EXTERNALLINKKEY_3);
			}
			else {
				bindExternalLinkKey = true;

				query.append(_FINDER_COLUMN_EXTERNALLINKKEY_EXTERNALLINKKEY_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindExternalLinkKey) {
					qPos.add(externalLinkKey);
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
		_FINDER_COLUMN_EXTERNALLINKKEY_EXTERNALLINKKEY_2 =
			"externalLink.externalLinkKey = ?";

	private static final String
		_FINDER_COLUMN_EXTERNALLINKKEY_EXTERNALLINKKEY_3 =
			"(externalLink.externalLinkKey IS NULL OR externalLink.externalLinkKey = '')";

	private FinderPath _finderPathWithPaginationFindByC_C;
	private FinderPath _finderPathWithoutPaginationFindByC_C;
	private FinderPath _finderPathCountByC_C;

	/**
	 * Returns all the external links where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching external links
	 */
	@Override
	public List<ExternalLink> findByC_C(long classNameId, long classPK) {
		return findByC_C(
			classNameId, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the external links where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ExternalLinkModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of external links
	 * @param end the upper bound of the range of external links (not inclusive)
	 * @return the range of matching external links
	 */
	@Override
	public List<ExternalLink> findByC_C(
		long classNameId, long classPK, int start, int end) {

		return findByC_C(classNameId, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the external links where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ExternalLinkModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of external links
	 * @param end the upper bound of the range of external links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching external links
	 */
	@Override
	public List<ExternalLink> findByC_C(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<ExternalLink> orderByComparator) {

		return findByC_C(
			classNameId, classPK, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the external links where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ExternalLinkModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of external links
	 * @param end the upper bound of the range of external links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching external links
	 */
	@Override
	public List<ExternalLink> findByC_C(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<ExternalLink> orderByComparator,
		boolean useFinderCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC_C;
				finderArgs = new Object[] {classNameId, classPK};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC_C;
			finderArgs = new Object[] {
				classNameId, classPK, start, end, orderByComparator
			};
		}

		List<ExternalLink> list = null;

		if (useFinderCache) {
			list = (List<ExternalLink>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ExternalLink externalLink : list) {
					if ((classNameId != externalLink.getClassNameId()) ||
						(classPK != externalLink.getClassPK())) {

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
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_EXTERNALLINK_WHERE);

			query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(ExternalLinkModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				if (!pagination) {
					list = (List<ExternalLink>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<ExternalLink>)QueryUtil.list(
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
	 * Returns the first external link in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching external link
	 * @throws NoSuchExternalLinkException if a matching external link could not be found
	 */
	@Override
	public ExternalLink findByC_C_First(
			long classNameId, long classPK,
			OrderByComparator<ExternalLink> orderByComparator)
		throws NoSuchExternalLinkException {

		ExternalLink externalLink = fetchByC_C_First(
			classNameId, classPK, orderByComparator);

		if (externalLink != null) {
			return externalLink;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append("}");

		throw new NoSuchExternalLinkException(msg.toString());
	}

	/**
	 * Returns the first external link in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching external link, or <code>null</code> if a matching external link could not be found
	 */
	@Override
	public ExternalLink fetchByC_C_First(
		long classNameId, long classPK,
		OrderByComparator<ExternalLink> orderByComparator) {

		List<ExternalLink> list = findByC_C(
			classNameId, classPK, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last external link in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching external link
	 * @throws NoSuchExternalLinkException if a matching external link could not be found
	 */
	@Override
	public ExternalLink findByC_C_Last(
			long classNameId, long classPK,
			OrderByComparator<ExternalLink> orderByComparator)
		throws NoSuchExternalLinkException {

		ExternalLink externalLink = fetchByC_C_Last(
			classNameId, classPK, orderByComparator);

		if (externalLink != null) {
			return externalLink;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append("}");

		throw new NoSuchExternalLinkException(msg.toString());
	}

	/**
	 * Returns the last external link in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching external link, or <code>null</code> if a matching external link could not be found
	 */
	@Override
	public ExternalLink fetchByC_C_Last(
		long classNameId, long classPK,
		OrderByComparator<ExternalLink> orderByComparator) {

		int count = countByC_C(classNameId, classPK);

		if (count == 0) {
			return null;
		}

		List<ExternalLink> list = findByC_C(
			classNameId, classPK, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the external links before and after the current external link in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param externalLinkId the primary key of the current external link
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next external link
	 * @throws NoSuchExternalLinkException if a external link with the primary key could not be found
	 */
	@Override
	public ExternalLink[] findByC_C_PrevAndNext(
			long externalLinkId, long classNameId, long classPK,
			OrderByComparator<ExternalLink> orderByComparator)
		throws NoSuchExternalLinkException {

		ExternalLink externalLink = findByPrimaryKey(externalLinkId);

		Session session = null;

		try {
			session = openSession();

			ExternalLink[] array = new ExternalLinkImpl[3];

			array[0] = getByC_C_PrevAndNext(
				session, externalLink, classNameId, classPK, orderByComparator,
				true);

			array[1] = externalLink;

			array[2] = getByC_C_PrevAndNext(
				session, externalLink, classNameId, classPK, orderByComparator,
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

	protected ExternalLink getByC_C_PrevAndNext(
		Session session, ExternalLink externalLink, long classNameId,
		long classPK, OrderByComparator<ExternalLink> orderByComparator,
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

		query.append(_SQL_SELECT_EXTERNALLINK_WHERE);

		query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

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
			query.append(ExternalLinkModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(classNameId);

		qPos.add(classPK);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(externalLink)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<ExternalLink> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the external links where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	@Override
	public void removeByC_C(long classNameId, long classPK) {
		for (ExternalLink externalLink :
				findByC_C(
					classNameId, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(externalLink);
		}
	}

	/**
	 * Returns the number of external links where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching external links
	 */
	@Override
	public int countByC_C(long classNameId, long classPK) {
		FinderPath finderPath = _finderPathCountByC_C;

		Object[] finderArgs = new Object[] {classNameId, classPK};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_EXTERNALLINK_WHERE);

			query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

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

	private static final String _FINDER_COLUMN_C_C_CLASSNAMEID_2 =
		"externalLink.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_CLASSPK_2 =
		"externalLink.classPK = ?";

	private FinderPath _finderPathWithPaginationFindByC_D_EN_EI;
	private FinderPath _finderPathWithoutPaginationFindByC_D_EN_EI;
	private FinderPath _finderPathCountByC_D_EN_EI;

	/**
	 * Returns all the external links where classNameId = &#63; and domain = &#63; and entityName = &#63; and entityId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param domain the domain
	 * @param entityName the entity name
	 * @param entityId the entity ID
	 * @return the matching external links
	 */
	@Override
	public List<ExternalLink> findByC_D_EN_EI(
		long classNameId, String domain, String entityName, String entityId) {

		return findByC_D_EN_EI(
			classNameId, domain, entityName, entityId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the external links where classNameId = &#63; and domain = &#63; and entityName = &#63; and entityId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ExternalLinkModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param domain the domain
	 * @param entityName the entity name
	 * @param entityId the entity ID
	 * @param start the lower bound of the range of external links
	 * @param end the upper bound of the range of external links (not inclusive)
	 * @return the range of matching external links
	 */
	@Override
	public List<ExternalLink> findByC_D_EN_EI(
		long classNameId, String domain, String entityName, String entityId,
		int start, int end) {

		return findByC_D_EN_EI(
			classNameId, domain, entityName, entityId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the external links where classNameId = &#63; and domain = &#63; and entityName = &#63; and entityId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ExternalLinkModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param domain the domain
	 * @param entityName the entity name
	 * @param entityId the entity ID
	 * @param start the lower bound of the range of external links
	 * @param end the upper bound of the range of external links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching external links
	 */
	@Override
	public List<ExternalLink> findByC_D_EN_EI(
		long classNameId, String domain, String entityName, String entityId,
		int start, int end, OrderByComparator<ExternalLink> orderByComparator) {

		return findByC_D_EN_EI(
			classNameId, domain, entityName, entityId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the external links where classNameId = &#63; and domain = &#63; and entityName = &#63; and entityId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ExternalLinkModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param domain the domain
	 * @param entityName the entity name
	 * @param entityId the entity ID
	 * @param start the lower bound of the range of external links
	 * @param end the upper bound of the range of external links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching external links
	 */
	@Override
	public List<ExternalLink> findByC_D_EN_EI(
		long classNameId, String domain, String entityName, String entityId,
		int start, int end, OrderByComparator<ExternalLink> orderByComparator,
		boolean useFinderCache) {

		domain = Objects.toString(domain, "");
		entityName = Objects.toString(entityName, "");
		entityId = Objects.toString(entityId, "");

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC_D_EN_EI;
				finderArgs = new Object[] {
					classNameId, domain, entityName, entityId
				};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC_D_EN_EI;
			finderArgs = new Object[] {
				classNameId, domain, entityName, entityId, start, end,
				orderByComparator
			};
		}

		List<ExternalLink> list = null;

		if (useFinderCache) {
			list = (List<ExternalLink>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ExternalLink externalLink : list) {
					if ((classNameId != externalLink.getClassNameId()) ||
						!domain.equals(externalLink.getDomain()) ||
						!entityName.equals(externalLink.getEntityName()) ||
						!entityId.equals(externalLink.getEntityId())) {

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
					6 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(6);
			}

			query.append(_SQL_SELECT_EXTERNALLINK_WHERE);

			query.append(_FINDER_COLUMN_C_D_EN_EI_CLASSNAMEID_2);

			boolean bindDomain = false;

			if (domain.isEmpty()) {
				query.append(_FINDER_COLUMN_C_D_EN_EI_DOMAIN_3);
			}
			else {
				bindDomain = true;

				query.append(_FINDER_COLUMN_C_D_EN_EI_DOMAIN_2);
			}

			boolean bindEntityName = false;

			if (entityName.isEmpty()) {
				query.append(_FINDER_COLUMN_C_D_EN_EI_ENTITYNAME_3);
			}
			else {
				bindEntityName = true;

				query.append(_FINDER_COLUMN_C_D_EN_EI_ENTITYNAME_2);
			}

			boolean bindEntityId = false;

			if (entityId.isEmpty()) {
				query.append(_FINDER_COLUMN_C_D_EN_EI_ENTITYID_3);
			}
			else {
				bindEntityId = true;

				query.append(_FINDER_COLUMN_C_D_EN_EI_ENTITYID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(ExternalLinkModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				if (bindDomain) {
					qPos.add(domain);
				}

				if (bindEntityName) {
					qPos.add(entityName);
				}

				if (bindEntityId) {
					qPos.add(entityId);
				}

				if (!pagination) {
					list = (List<ExternalLink>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<ExternalLink>)QueryUtil.list(
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
	 * Returns the first external link in the ordered set where classNameId = &#63; and domain = &#63; and entityName = &#63; and entityId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param domain the domain
	 * @param entityName the entity name
	 * @param entityId the entity ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching external link
	 * @throws NoSuchExternalLinkException if a matching external link could not be found
	 */
	@Override
	public ExternalLink findByC_D_EN_EI_First(
			long classNameId, String domain, String entityName, String entityId,
			OrderByComparator<ExternalLink> orderByComparator)
		throws NoSuchExternalLinkException {

		ExternalLink externalLink = fetchByC_D_EN_EI_First(
			classNameId, domain, entityName, entityId, orderByComparator);

		if (externalLink != null) {
			return externalLink;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(", domain=");
		msg.append(domain);

		msg.append(", entityName=");
		msg.append(entityName);

		msg.append(", entityId=");
		msg.append(entityId);

		msg.append("}");

		throw new NoSuchExternalLinkException(msg.toString());
	}

	/**
	 * Returns the first external link in the ordered set where classNameId = &#63; and domain = &#63; and entityName = &#63; and entityId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param domain the domain
	 * @param entityName the entity name
	 * @param entityId the entity ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching external link, or <code>null</code> if a matching external link could not be found
	 */
	@Override
	public ExternalLink fetchByC_D_EN_EI_First(
		long classNameId, String domain, String entityName, String entityId,
		OrderByComparator<ExternalLink> orderByComparator) {

		List<ExternalLink> list = findByC_D_EN_EI(
			classNameId, domain, entityName, entityId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last external link in the ordered set where classNameId = &#63; and domain = &#63; and entityName = &#63; and entityId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param domain the domain
	 * @param entityName the entity name
	 * @param entityId the entity ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching external link
	 * @throws NoSuchExternalLinkException if a matching external link could not be found
	 */
	@Override
	public ExternalLink findByC_D_EN_EI_Last(
			long classNameId, String domain, String entityName, String entityId,
			OrderByComparator<ExternalLink> orderByComparator)
		throws NoSuchExternalLinkException {

		ExternalLink externalLink = fetchByC_D_EN_EI_Last(
			classNameId, domain, entityName, entityId, orderByComparator);

		if (externalLink != null) {
			return externalLink;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(", domain=");
		msg.append(domain);

		msg.append(", entityName=");
		msg.append(entityName);

		msg.append(", entityId=");
		msg.append(entityId);

		msg.append("}");

		throw new NoSuchExternalLinkException(msg.toString());
	}

	/**
	 * Returns the last external link in the ordered set where classNameId = &#63; and domain = &#63; and entityName = &#63; and entityId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param domain the domain
	 * @param entityName the entity name
	 * @param entityId the entity ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching external link, or <code>null</code> if a matching external link could not be found
	 */
	@Override
	public ExternalLink fetchByC_D_EN_EI_Last(
		long classNameId, String domain, String entityName, String entityId,
		OrderByComparator<ExternalLink> orderByComparator) {

		int count = countByC_D_EN_EI(classNameId, domain, entityName, entityId);

		if (count == 0) {
			return null;
		}

		List<ExternalLink> list = findByC_D_EN_EI(
			classNameId, domain, entityName, entityId, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the external links before and after the current external link in the ordered set where classNameId = &#63; and domain = &#63; and entityName = &#63; and entityId = &#63;.
	 *
	 * @param externalLinkId the primary key of the current external link
	 * @param classNameId the class name ID
	 * @param domain the domain
	 * @param entityName the entity name
	 * @param entityId the entity ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next external link
	 * @throws NoSuchExternalLinkException if a external link with the primary key could not be found
	 */
	@Override
	public ExternalLink[] findByC_D_EN_EI_PrevAndNext(
			long externalLinkId, long classNameId, String domain,
			String entityName, String entityId,
			OrderByComparator<ExternalLink> orderByComparator)
		throws NoSuchExternalLinkException {

		domain = Objects.toString(domain, "");
		entityName = Objects.toString(entityName, "");
		entityId = Objects.toString(entityId, "");

		ExternalLink externalLink = findByPrimaryKey(externalLinkId);

		Session session = null;

		try {
			session = openSession();

			ExternalLink[] array = new ExternalLinkImpl[3];

			array[0] = getByC_D_EN_EI_PrevAndNext(
				session, externalLink, classNameId, domain, entityName,
				entityId, orderByComparator, true);

			array[1] = externalLink;

			array[2] = getByC_D_EN_EI_PrevAndNext(
				session, externalLink, classNameId, domain, entityName,
				entityId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected ExternalLink getByC_D_EN_EI_PrevAndNext(
		Session session, ExternalLink externalLink, long classNameId,
		String domain, String entityName, String entityId,
		OrderByComparator<ExternalLink> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				7 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(6);
		}

		query.append(_SQL_SELECT_EXTERNALLINK_WHERE);

		query.append(_FINDER_COLUMN_C_D_EN_EI_CLASSNAMEID_2);

		boolean bindDomain = false;

		if (domain.isEmpty()) {
			query.append(_FINDER_COLUMN_C_D_EN_EI_DOMAIN_3);
		}
		else {
			bindDomain = true;

			query.append(_FINDER_COLUMN_C_D_EN_EI_DOMAIN_2);
		}

		boolean bindEntityName = false;

		if (entityName.isEmpty()) {
			query.append(_FINDER_COLUMN_C_D_EN_EI_ENTITYNAME_3);
		}
		else {
			bindEntityName = true;

			query.append(_FINDER_COLUMN_C_D_EN_EI_ENTITYNAME_2);
		}

		boolean bindEntityId = false;

		if (entityId.isEmpty()) {
			query.append(_FINDER_COLUMN_C_D_EN_EI_ENTITYID_3);
		}
		else {
			bindEntityId = true;

			query.append(_FINDER_COLUMN_C_D_EN_EI_ENTITYID_2);
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
			query.append(ExternalLinkModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(classNameId);

		if (bindDomain) {
			qPos.add(domain);
		}

		if (bindEntityName) {
			qPos.add(entityName);
		}

		if (bindEntityId) {
			qPos.add(entityId);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(externalLink)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<ExternalLink> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the external links where classNameId = &#63; and domain = &#63; and entityName = &#63; and entityId = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param domain the domain
	 * @param entityName the entity name
	 * @param entityId the entity ID
	 */
	@Override
	public void removeByC_D_EN_EI(
		long classNameId, String domain, String entityName, String entityId) {

		for (ExternalLink externalLink :
				findByC_D_EN_EI(
					classNameId, domain, entityName, entityId,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(externalLink);
		}
	}

	/**
	 * Returns the number of external links where classNameId = &#63; and domain = &#63; and entityName = &#63; and entityId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param domain the domain
	 * @param entityName the entity name
	 * @param entityId the entity ID
	 * @return the number of matching external links
	 */
	@Override
	public int countByC_D_EN_EI(
		long classNameId, String domain, String entityName, String entityId) {

		domain = Objects.toString(domain, "");
		entityName = Objects.toString(entityName, "");
		entityId = Objects.toString(entityId, "");

		FinderPath finderPath = _finderPathCountByC_D_EN_EI;

		Object[] finderArgs = new Object[] {
			classNameId, domain, entityName, entityId
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_EXTERNALLINK_WHERE);

			query.append(_FINDER_COLUMN_C_D_EN_EI_CLASSNAMEID_2);

			boolean bindDomain = false;

			if (domain.isEmpty()) {
				query.append(_FINDER_COLUMN_C_D_EN_EI_DOMAIN_3);
			}
			else {
				bindDomain = true;

				query.append(_FINDER_COLUMN_C_D_EN_EI_DOMAIN_2);
			}

			boolean bindEntityName = false;

			if (entityName.isEmpty()) {
				query.append(_FINDER_COLUMN_C_D_EN_EI_ENTITYNAME_3);
			}
			else {
				bindEntityName = true;

				query.append(_FINDER_COLUMN_C_D_EN_EI_ENTITYNAME_2);
			}

			boolean bindEntityId = false;

			if (entityId.isEmpty()) {
				query.append(_FINDER_COLUMN_C_D_EN_EI_ENTITYID_3);
			}
			else {
				bindEntityId = true;

				query.append(_FINDER_COLUMN_C_D_EN_EI_ENTITYID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				if (bindDomain) {
					qPos.add(domain);
				}

				if (bindEntityName) {
					qPos.add(entityName);
				}

				if (bindEntityId) {
					qPos.add(entityId);
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

	private static final String _FINDER_COLUMN_C_D_EN_EI_CLASSNAMEID_2 =
		"externalLink.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_D_EN_EI_DOMAIN_2 =
		"externalLink.domain = ? AND ";

	private static final String _FINDER_COLUMN_C_D_EN_EI_DOMAIN_3 =
		"(externalLink.domain IS NULL OR externalLink.domain = '') AND ";

	private static final String _FINDER_COLUMN_C_D_EN_EI_ENTITYNAME_2 =
		"externalLink.entityName = ? AND ";

	private static final String _FINDER_COLUMN_C_D_EN_EI_ENTITYNAME_3 =
		"(externalLink.entityName IS NULL OR externalLink.entityName = '') AND ";

	private static final String _FINDER_COLUMN_C_D_EN_EI_ENTITYID_2 =
		"externalLink.entityId = ?";

	private static final String _FINDER_COLUMN_C_D_EN_EI_ENTITYID_3 =
		"(externalLink.entityId IS NULL OR externalLink.entityId = '')";

	public ExternalLinkPersistenceImpl() {
		setModelClass(ExternalLink.class);

		setModelImplClass(ExternalLinkImpl.class);
		setModelPKClass(long.class);
	}

	/**
	 * Caches the external link in the entity cache if it is enabled.
	 *
	 * @param externalLink the external link
	 */
	@Override
	public void cacheResult(ExternalLink externalLink) {
		entityCache.putResult(
			entityCacheEnabled, ExternalLinkImpl.class,
			externalLink.getPrimaryKey(), externalLink);

		finderCache.putResult(
			_finderPathFetchByExternalLinkKey,
			new Object[] {externalLink.getExternalLinkKey()}, externalLink);

		externalLink.resetOriginalValues();
	}

	/**
	 * Caches the external links in the entity cache if it is enabled.
	 *
	 * @param externalLinks the external links
	 */
	@Override
	public void cacheResult(List<ExternalLink> externalLinks) {
		for (ExternalLink externalLink : externalLinks) {
			if (entityCache.getResult(
					entityCacheEnabled, ExternalLinkImpl.class,
					externalLink.getPrimaryKey()) == null) {

				cacheResult(externalLink);
			}
			else {
				externalLink.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all external links.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(ExternalLinkImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the external link.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ExternalLink externalLink) {
		entityCache.removeResult(
			entityCacheEnabled, ExternalLinkImpl.class,
			externalLink.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((ExternalLinkModelImpl)externalLink, true);
	}

	@Override
	public void clearCache(List<ExternalLink> externalLinks) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (ExternalLink externalLink : externalLinks) {
			entityCache.removeResult(
				entityCacheEnabled, ExternalLinkImpl.class,
				externalLink.getPrimaryKey());

			clearUniqueFindersCache((ExternalLinkModelImpl)externalLink, true);
		}
	}

	protected void cacheUniqueFindersCache(
		ExternalLinkModelImpl externalLinkModelImpl) {

		Object[] args = new Object[] {
			externalLinkModelImpl.getExternalLinkKey()
		};

		finderCache.putResult(
			_finderPathCountByExternalLinkKey, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByExternalLinkKey, args, externalLinkModelImpl,
			false);
	}

	protected void clearUniqueFindersCache(
		ExternalLinkModelImpl externalLinkModelImpl, boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				externalLinkModelImpl.getExternalLinkKey()
			};

			finderCache.removeResult(_finderPathCountByExternalLinkKey, args);
			finderCache.removeResult(_finderPathFetchByExternalLinkKey, args);
		}

		if ((externalLinkModelImpl.getColumnBitmask() &
			 _finderPathFetchByExternalLinkKey.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				externalLinkModelImpl.getOriginalExternalLinkKey()
			};

			finderCache.removeResult(_finderPathCountByExternalLinkKey, args);
			finderCache.removeResult(_finderPathFetchByExternalLinkKey, args);
		}
	}

	/**
	 * Creates a new external link with the primary key. Does not add the external link to the database.
	 *
	 * @param externalLinkId the primary key for the new external link
	 * @return the new external link
	 */
	@Override
	public ExternalLink create(long externalLinkId) {
		ExternalLink externalLink = new ExternalLinkImpl();

		externalLink.setNew(true);
		externalLink.setPrimaryKey(externalLinkId);

		externalLink.setCompanyId(CompanyThreadLocal.getCompanyId());

		return externalLink;
	}

	/**
	 * Removes the external link with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param externalLinkId the primary key of the external link
	 * @return the external link that was removed
	 * @throws NoSuchExternalLinkException if a external link with the primary key could not be found
	 */
	@Override
	public ExternalLink remove(long externalLinkId)
		throws NoSuchExternalLinkException {

		return remove((Serializable)externalLinkId);
	}

	/**
	 * Removes the external link with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the external link
	 * @return the external link that was removed
	 * @throws NoSuchExternalLinkException if a external link with the primary key could not be found
	 */
	@Override
	public ExternalLink remove(Serializable primaryKey)
		throws NoSuchExternalLinkException {

		Session session = null;

		try {
			session = openSession();

			ExternalLink externalLink = (ExternalLink)session.get(
				ExternalLinkImpl.class, primaryKey);

			if (externalLink == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchExternalLinkException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(externalLink);
		}
		catch (NoSuchExternalLinkException nsee) {
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
	protected ExternalLink removeImpl(ExternalLink externalLink) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(externalLink)) {
				externalLink = (ExternalLink)session.get(
					ExternalLinkImpl.class, externalLink.getPrimaryKeyObj());
			}

			if (externalLink != null) {
				session.delete(externalLink);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (externalLink != null) {
			clearCache(externalLink);
		}

		return externalLink;
	}

	@Override
	public ExternalLink updateImpl(ExternalLink externalLink) {
		boolean isNew = externalLink.isNew();

		if (!(externalLink instanceof ExternalLinkModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(externalLink.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					externalLink);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in externalLink proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom ExternalLink implementation " +
					externalLink.getClass());
		}

		ExternalLinkModelImpl externalLinkModelImpl =
			(ExternalLinkModelImpl)externalLink;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (externalLink.getCreateDate() == null)) {
			if (serviceContext == null) {
				externalLink.setCreateDate(now);
			}
			else {
				externalLink.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!externalLinkModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				externalLink.setModifiedDate(now);
			}
			else {
				externalLink.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (externalLink.isNew()) {
				session.save(externalLink);

				externalLink.setNew(false);
			}
			else {
				externalLink = (ExternalLink)session.merge(externalLink);
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
				externalLinkModelImpl.getClassNameId(),
				externalLinkModelImpl.getClassPK()
			};

			finderCache.removeResult(_finderPathCountByC_C, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByC_C, args);

			args = new Object[] {
				externalLinkModelImpl.getClassNameId(),
				externalLinkModelImpl.getDomain(),
				externalLinkModelImpl.getEntityName(),
				externalLinkModelImpl.getEntityId()
			};

			finderCache.removeResult(_finderPathCountByC_D_EN_EI, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByC_D_EN_EI, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((externalLinkModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByC_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					externalLinkModelImpl.getOriginalClassNameId(),
					externalLinkModelImpl.getOriginalClassPK()
				};

				finderCache.removeResult(_finderPathCountByC_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByC_C, args);

				args = new Object[] {
					externalLinkModelImpl.getClassNameId(),
					externalLinkModelImpl.getClassPK()
				};

				finderCache.removeResult(_finderPathCountByC_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByC_C, args);
			}

			if ((externalLinkModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByC_D_EN_EI.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					externalLinkModelImpl.getOriginalClassNameId(),
					externalLinkModelImpl.getOriginalDomain(),
					externalLinkModelImpl.getOriginalEntityName(),
					externalLinkModelImpl.getOriginalEntityId()
				};

				finderCache.removeResult(_finderPathCountByC_D_EN_EI, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByC_D_EN_EI, args);

				args = new Object[] {
					externalLinkModelImpl.getClassNameId(),
					externalLinkModelImpl.getDomain(),
					externalLinkModelImpl.getEntityName(),
					externalLinkModelImpl.getEntityId()
				};

				finderCache.removeResult(_finderPathCountByC_D_EN_EI, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByC_D_EN_EI, args);
			}
		}

		entityCache.putResult(
			entityCacheEnabled, ExternalLinkImpl.class,
			externalLink.getPrimaryKey(), externalLink, false);

		clearUniqueFindersCache(externalLinkModelImpl, false);
		cacheUniqueFindersCache(externalLinkModelImpl);

		externalLink.resetOriginalValues();

		return externalLink;
	}

	/**
	 * Returns the external link with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the external link
	 * @return the external link
	 * @throws NoSuchExternalLinkException if a external link with the primary key could not be found
	 */
	@Override
	public ExternalLink findByPrimaryKey(Serializable primaryKey)
		throws NoSuchExternalLinkException {

		ExternalLink externalLink = fetchByPrimaryKey(primaryKey);

		if (externalLink == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchExternalLinkException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return externalLink;
	}

	/**
	 * Returns the external link with the primary key or throws a <code>NoSuchExternalLinkException</code> if it could not be found.
	 *
	 * @param externalLinkId the primary key of the external link
	 * @return the external link
	 * @throws NoSuchExternalLinkException if a external link with the primary key could not be found
	 */
	@Override
	public ExternalLink findByPrimaryKey(long externalLinkId)
		throws NoSuchExternalLinkException {

		return findByPrimaryKey((Serializable)externalLinkId);
	}

	/**
	 * Returns the external link with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param externalLinkId the primary key of the external link
	 * @return the external link, or <code>null</code> if a external link with the primary key could not be found
	 */
	@Override
	public ExternalLink fetchByPrimaryKey(long externalLinkId) {
		return fetchByPrimaryKey((Serializable)externalLinkId);
	}

	/**
	 * Returns all the external links.
	 *
	 * @return the external links
	 */
	@Override
	public List<ExternalLink> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the external links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ExternalLinkModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of external links
	 * @param end the upper bound of the range of external links (not inclusive)
	 * @return the range of external links
	 */
	@Override
	public List<ExternalLink> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the external links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ExternalLinkModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of external links
	 * @param end the upper bound of the range of external links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of external links
	 */
	@Override
	public List<ExternalLink> findAll(
		int start, int end, OrderByComparator<ExternalLink> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the external links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ExternalLinkModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of external links
	 * @param end the upper bound of the range of external links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of external links
	 */
	@Override
	public List<ExternalLink> findAll(
		int start, int end, OrderByComparator<ExternalLink> orderByComparator,
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

		List<ExternalLink> list = null;

		if (useFinderCache) {
			list = (List<ExternalLink>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_EXTERNALLINK);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_EXTERNALLINK;

				if (pagination) {
					sql = sql.concat(ExternalLinkModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<ExternalLink>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<ExternalLink>)QueryUtil.list(
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
	 * Removes all the external links from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (ExternalLink externalLink : findAll()) {
			remove(externalLink);
		}
	}

	/**
	 * Returns the number of external links.
	 *
	 * @return the number of external links
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_EXTERNALLINK);

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
		return "externalLinkId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_EXTERNALLINK;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return ExternalLinkModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the external link persistence.
	 */
	@Activate
	public void activate() {
		ExternalLinkModelImpl.setEntityCacheEnabled(entityCacheEnabled);
		ExternalLinkModelImpl.setFinderCacheEnabled(finderCacheEnabled);

		_finderPathWithPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, ExternalLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, ExternalLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathFetchByExternalLinkKey = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, ExternalLinkImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByExternalLinkKey",
			new String[] {String.class.getName()},
			ExternalLinkModelImpl.EXTERNALLINKKEY_COLUMN_BITMASK);

		_finderPathCountByExternalLinkKey = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByExternalLinkKey",
			new String[] {String.class.getName()});

		_finderPathWithPaginationFindByC_C = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, ExternalLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByC_C = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, ExternalLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			ExternalLinkModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			ExternalLinkModelImpl.CLASSPK_COLUMN_BITMASK);

		_finderPathCountByC_C = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C",
			new String[] {Long.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByC_D_EN_EI = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, ExternalLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_D_EN_EI",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByC_D_EN_EI = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, ExternalLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_D_EN_EI",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName(), String.class.getName()
			},
			ExternalLinkModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			ExternalLinkModelImpl.DOMAIN_COLUMN_BITMASK |
			ExternalLinkModelImpl.ENTITYNAME_COLUMN_BITMASK |
			ExternalLinkModelImpl.ENTITYID_COLUMN_BITMASK);

		_finderPathCountByC_D_EN_EI = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_D_EN_EI",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName(), String.class.getName()
			});
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(ExternalLinkImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	@Reference(
		target = KoroneikiPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
		super.setConfiguration(configuration);

		_columnBitmaskEnabled = GetterUtil.getBoolean(
			configuration.get(
				"value.object.column.bitmask.enabled.com.liferay.osb.koroneiki.root.model.ExternalLink"),
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

	private static final String _SQL_SELECT_EXTERNALLINK =
		"SELECT externalLink FROM ExternalLink externalLink";

	private static final String _SQL_SELECT_EXTERNALLINK_WHERE =
		"SELECT externalLink FROM ExternalLink externalLink WHERE ";

	private static final String _SQL_COUNT_EXTERNALLINK =
		"SELECT COUNT(externalLink) FROM ExternalLink externalLink";

	private static final String _SQL_COUNT_EXTERNALLINK_WHERE =
		"SELECT COUNT(externalLink) FROM ExternalLink externalLink WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "externalLink.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No ExternalLink exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No ExternalLink exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		ExternalLinkPersistenceImpl.class);

	static {
		try {
			Class.forName(KoroneikiPersistenceConstants.class.getName());
		}
		catch (ClassNotFoundException cnfe) {
			throw new ExceptionInInitializerError(cnfe);
		}
	}

}