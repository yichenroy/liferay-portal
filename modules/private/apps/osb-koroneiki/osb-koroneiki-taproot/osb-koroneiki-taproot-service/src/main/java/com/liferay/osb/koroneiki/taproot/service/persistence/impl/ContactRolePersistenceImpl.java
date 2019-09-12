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

package com.liferay.osb.koroneiki.taproot.service.persistence.impl;

import com.liferay.osb.koroneiki.taproot.exception.NoSuchContactRoleException;
import com.liferay.osb.koroneiki.taproot.model.ContactRole;
import com.liferay.osb.koroneiki.taproot.model.impl.ContactRoleImpl;
import com.liferay.osb.koroneiki.taproot.model.impl.ContactRoleModelImpl;
import com.liferay.osb.koroneiki.taproot.service.persistence.ContactRolePersistence;
import com.liferay.osb.koroneiki.taproot.service.persistence.impl.constants.KoroneikiPersistenceConstants;
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
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
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
 * The persistence implementation for the contact role service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = ContactRolePersistence.class)
public class ContactRolePersistenceImpl
	extends BasePersistenceImpl<ContactRole> implements ContactRolePersistence {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>ContactRoleUtil</code> to access the contact role persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		ContactRoleImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByUuid;
	private FinderPath _finderPathWithoutPaginationFindByUuid;
	private FinderPath _finderPathCountByUuid;

	/**
	 * Returns all the contact roles where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching contact roles
	 */
	@Override
	public List<ContactRole> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the contact roles where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ContactRoleModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of contact roles
	 * @param end the upper bound of the range of contact roles (not inclusive)
	 * @return the range of matching contact roles
	 */
	@Override
	public List<ContactRole> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the contact roles where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ContactRoleModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of contact roles
	 * @param end the upper bound of the range of contact roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching contact roles
	 */
	@Override
	public List<ContactRole> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ContactRole> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the contact roles where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ContactRoleModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of contact roles
	 * @param end the upper bound of the range of contact roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching contact roles
	 */
	@Override
	public List<ContactRole> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ContactRole> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUuid;
				finderArgs = new Object[] {uuid};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUuid;
			finderArgs = new Object[] {uuid, start, end, orderByComparator};
		}

		List<ContactRole> list = null;

		if (useFinderCache) {
			list = (List<ContactRole>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ContactRole contactRole : list) {
					if (!uuid.equals(contactRole.getUuid())) {
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

			query.append(_SQL_SELECT_CONTACTROLE_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				query.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(ContactRoleModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				if (!pagination) {
					list = (List<ContactRole>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<ContactRole>)QueryUtil.list(
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
	 * Returns the first contact role in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching contact role
	 * @throws NoSuchContactRoleException if a matching contact role could not be found
	 */
	@Override
	public ContactRole findByUuid_First(
			String uuid, OrderByComparator<ContactRole> orderByComparator)
		throws NoSuchContactRoleException {

		ContactRole contactRole = fetchByUuid_First(uuid, orderByComparator);

		if (contactRole != null) {
			return contactRole;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchContactRoleException(msg.toString());
	}

	/**
	 * Returns the first contact role in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching contact role, or <code>null</code> if a matching contact role could not be found
	 */
	@Override
	public ContactRole fetchByUuid_First(
		String uuid, OrderByComparator<ContactRole> orderByComparator) {

		List<ContactRole> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last contact role in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching contact role
	 * @throws NoSuchContactRoleException if a matching contact role could not be found
	 */
	@Override
	public ContactRole findByUuid_Last(
			String uuid, OrderByComparator<ContactRole> orderByComparator)
		throws NoSuchContactRoleException {

		ContactRole contactRole = fetchByUuid_Last(uuid, orderByComparator);

		if (contactRole != null) {
			return contactRole;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchContactRoleException(msg.toString());
	}

	/**
	 * Returns the last contact role in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching contact role, or <code>null</code> if a matching contact role could not be found
	 */
	@Override
	public ContactRole fetchByUuid_Last(
		String uuid, OrderByComparator<ContactRole> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<ContactRole> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the contact roles before and after the current contact role in the ordered set where uuid = &#63;.
	 *
	 * @param contactRoleId the primary key of the current contact role
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next contact role
	 * @throws NoSuchContactRoleException if a contact role with the primary key could not be found
	 */
	@Override
	public ContactRole[] findByUuid_PrevAndNext(
			long contactRoleId, String uuid,
			OrderByComparator<ContactRole> orderByComparator)
		throws NoSuchContactRoleException {

		uuid = Objects.toString(uuid, "");

		ContactRole contactRole = findByPrimaryKey(contactRoleId);

		Session session = null;

		try {
			session = openSession();

			ContactRole[] array = new ContactRoleImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, contactRole, uuid, orderByComparator, true);

			array[1] = contactRole;

			array[2] = getByUuid_PrevAndNext(
				session, contactRole, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected ContactRole getByUuid_PrevAndNext(
		Session session, ContactRole contactRole, String uuid,
		OrderByComparator<ContactRole> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_CONTACTROLE_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			query.append(_FINDER_COLUMN_UUID_UUID_3);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_UUID_2);
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
			query.append(ContactRoleModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindUuid) {
			qPos.add(uuid);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(contactRole)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<ContactRole> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the contact roles that the user has permission to view where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching contact roles that the user has permission to view
	 */
	@Override
	public List<ContactRole> filterFindByUuid(String uuid) {
		return filterFindByUuid(
			uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the contact roles that the user has permission to view where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ContactRoleModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of contact roles
	 * @param end the upper bound of the range of contact roles (not inclusive)
	 * @return the range of matching contact roles that the user has permission to view
	 */
	@Override
	public List<ContactRole> filterFindByUuid(String uuid, int start, int end) {
		return filterFindByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the contact roles that the user has permissions to view where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ContactRoleModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of contact roles
	 * @param end the upper bound of the range of contact roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching contact roles that the user has permission to view
	 */
	@Override
	public List<ContactRole> filterFindByUuid(
		String uuid, int start, int end,
		OrderByComparator<ContactRole> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByUuid(uuid, start, end, orderByComparator);
		}

		uuid = Objects.toString(uuid, "");

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				3 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			query = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_CONTACTROLE_WHERE);
		}
		else {
			query.append(
				_FILTER_SQL_SELECT_CONTACTROLE_NO_INLINE_DISTINCT_WHERE_1);
		}

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			query.append(_FINDER_COLUMN_UUID_UUID_3_SQL);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_UUID_2_SQL);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(
				_FILTER_SQL_SELECT_CONTACTROLE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(ContactRoleModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(ContactRoleModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), ContactRole.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, ContactRoleImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, ContactRoleImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			if (bindUuid) {
				qPos.add(uuid);
			}

			return (List<ContactRole>)QueryUtil.list(
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
	 * Returns the contact roles before and after the current contact role in the ordered set of contact roles that the user has permission to view where uuid = &#63;.
	 *
	 * @param contactRoleId the primary key of the current contact role
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next contact role
	 * @throws NoSuchContactRoleException if a contact role with the primary key could not be found
	 */
	@Override
	public ContactRole[] filterFindByUuid_PrevAndNext(
			long contactRoleId, String uuid,
			OrderByComparator<ContactRole> orderByComparator)
		throws NoSuchContactRoleException {

		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByUuid_PrevAndNext(
				contactRoleId, uuid, orderByComparator);
		}

		uuid = Objects.toString(uuid, "");

		ContactRole contactRole = findByPrimaryKey(contactRoleId);

		Session session = null;

		try {
			session = openSession();

			ContactRole[] array = new ContactRoleImpl[3];

			array[0] = filterGetByUuid_PrevAndNext(
				session, contactRole, uuid, orderByComparator, true);

			array[1] = contactRole;

			array[2] = filterGetByUuid_PrevAndNext(
				session, contactRole, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected ContactRole filterGetByUuid_PrevAndNext(
		Session session, ContactRole contactRole, String uuid,
		OrderByComparator<ContactRole> orderByComparator, boolean previous) {

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
			query.append(_FILTER_SQL_SELECT_CONTACTROLE_WHERE);
		}
		else {
			query.append(
				_FILTER_SQL_SELECT_CONTACTROLE_NO_INLINE_DISTINCT_WHERE_1);
		}

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			query.append(_FINDER_COLUMN_UUID_UUID_3_SQL);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_UUID_2_SQL);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(
				_FILTER_SQL_SELECT_CONTACTROLE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(ContactRoleModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(ContactRoleModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), ContactRole.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, ContactRoleImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, ContactRoleImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindUuid) {
			qPos.add(uuid);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(contactRole)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<ContactRole> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the contact roles where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (ContactRole contactRole :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(contactRole);
		}
	}

	/**
	 * Returns the number of contact roles where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching contact roles
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_CONTACTROLE_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				query.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
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

	/**
	 * Returns the number of contact roles that the user has permission to view where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching contact roles that the user has permission to view
	 */
	@Override
	public int filterCountByUuid(String uuid) {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByUuid(uuid);
		}

		uuid = Objects.toString(uuid, "");

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_CONTACTROLE_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			query.append(_FINDER_COLUMN_UUID_UUID_3_SQL);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_UUID_2_SQL);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), ContactRole.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			if (bindUuid) {
				qPos.add(uuid);
			}

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

	private static final String _FINDER_COLUMN_UUID_UUID_2 =
		"contactRole.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(contactRole.uuid IS NULL OR contactRole.uuid = '')";

	private static final String _FINDER_COLUMN_UUID_UUID_2_SQL =
		"contactRole.uuid_ = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3_SQL =
		"(contactRole.uuid_ IS NULL OR contactRole.uuid_ = '')";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the contact roles where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching contact roles
	 */
	@Override
	public List<ContactRole> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the contact roles where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ContactRoleModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of contact roles
	 * @param end the upper bound of the range of contact roles (not inclusive)
	 * @return the range of matching contact roles
	 */
	@Override
	public List<ContactRole> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the contact roles where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ContactRoleModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of contact roles
	 * @param end the upper bound of the range of contact roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching contact roles
	 */
	@Override
	public List<ContactRole> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ContactRole> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the contact roles where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ContactRoleModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of contact roles
	 * @param end the upper bound of the range of contact roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching contact roles
	 */
	@Override
	public List<ContactRole> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ContactRole> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUuid_C;
				finderArgs = new Object[] {uuid, companyId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUuid_C;
			finderArgs = new Object[] {
				uuid, companyId, start, end, orderByComparator
			};
		}

		List<ContactRole> list = null;

		if (useFinderCache) {
			list = (List<ContactRole>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ContactRole contactRole : list) {
					if (!uuid.equals(contactRole.getUuid()) ||
						(companyId != contactRole.getCompanyId())) {

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

			query.append(_SQL_SELECT_CONTACTROLE_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(ContactRoleModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(companyId);

				if (!pagination) {
					list = (List<ContactRole>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<ContactRole>)QueryUtil.list(
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
	 * Returns the first contact role in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching contact role
	 * @throws NoSuchContactRoleException if a matching contact role could not be found
	 */
	@Override
	public ContactRole findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<ContactRole> orderByComparator)
		throws NoSuchContactRoleException {

		ContactRole contactRole = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (contactRole != null) {
			return contactRole;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchContactRoleException(msg.toString());
	}

	/**
	 * Returns the first contact role in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching contact role, or <code>null</code> if a matching contact role could not be found
	 */
	@Override
	public ContactRole fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<ContactRole> orderByComparator) {

		List<ContactRole> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last contact role in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching contact role
	 * @throws NoSuchContactRoleException if a matching contact role could not be found
	 */
	@Override
	public ContactRole findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<ContactRole> orderByComparator)
		throws NoSuchContactRoleException {

		ContactRole contactRole = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (contactRole != null) {
			return contactRole;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchContactRoleException(msg.toString());
	}

	/**
	 * Returns the last contact role in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching contact role, or <code>null</code> if a matching contact role could not be found
	 */
	@Override
	public ContactRole fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<ContactRole> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<ContactRole> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the contact roles before and after the current contact role in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param contactRoleId the primary key of the current contact role
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next contact role
	 * @throws NoSuchContactRoleException if a contact role with the primary key could not be found
	 */
	@Override
	public ContactRole[] findByUuid_C_PrevAndNext(
			long contactRoleId, String uuid, long companyId,
			OrderByComparator<ContactRole> orderByComparator)
		throws NoSuchContactRoleException {

		uuid = Objects.toString(uuid, "");

		ContactRole contactRole = findByPrimaryKey(contactRoleId);

		Session session = null;

		try {
			session = openSession();

			ContactRole[] array = new ContactRoleImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, contactRole, uuid, companyId, orderByComparator, true);

			array[1] = contactRole;

			array[2] = getByUuid_C_PrevAndNext(
				session, contactRole, uuid, companyId, orderByComparator,
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

	protected ContactRole getByUuid_C_PrevAndNext(
		Session session, ContactRole contactRole, String uuid, long companyId,
		OrderByComparator<ContactRole> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_CONTACTROLE_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_3);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_C_UUID_2);
		}

		query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

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
			query.append(ContactRoleModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindUuid) {
			qPos.add(uuid);
		}

		qPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(contactRole)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<ContactRole> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the contact roles that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching contact roles that the user has permission to view
	 */
	@Override
	public List<ContactRole> filterFindByUuid_C(String uuid, long companyId) {
		return filterFindByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the contact roles that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ContactRoleModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of contact roles
	 * @param end the upper bound of the range of contact roles (not inclusive)
	 * @return the range of matching contact roles that the user has permission to view
	 */
	@Override
	public List<ContactRole> filterFindByUuid_C(
		String uuid, long companyId, int start, int end) {

		return filterFindByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the contact roles that the user has permissions to view where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ContactRoleModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of contact roles
	 * @param end the upper bound of the range of contact roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching contact roles that the user has permission to view
	 */
	@Override
	public List<ContactRole> filterFindByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ContactRole> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByUuid_C(uuid, companyId, start, end, orderByComparator);
		}

		uuid = Objects.toString(uuid, "");

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			query = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_CONTACTROLE_WHERE);
		}
		else {
			query.append(
				_FILTER_SQL_SELECT_CONTACTROLE_NO_INLINE_DISTINCT_WHERE_1);
		}

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_3_SQL);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_C_UUID_2_SQL);
		}

		query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(
				_FILTER_SQL_SELECT_CONTACTROLE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(ContactRoleModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(ContactRoleModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), ContactRole.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, ContactRoleImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, ContactRoleImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			if (bindUuid) {
				qPos.add(uuid);
			}

			qPos.add(companyId);

			return (List<ContactRole>)QueryUtil.list(
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
	 * Returns the contact roles before and after the current contact role in the ordered set of contact roles that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param contactRoleId the primary key of the current contact role
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next contact role
	 * @throws NoSuchContactRoleException if a contact role with the primary key could not be found
	 */
	@Override
	public ContactRole[] filterFindByUuid_C_PrevAndNext(
			long contactRoleId, String uuid, long companyId,
			OrderByComparator<ContactRole> orderByComparator)
		throws NoSuchContactRoleException {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByUuid_C_PrevAndNext(
				contactRoleId, uuid, companyId, orderByComparator);
		}

		uuid = Objects.toString(uuid, "");

		ContactRole contactRole = findByPrimaryKey(contactRoleId);

		Session session = null;

		try {
			session = openSession();

			ContactRole[] array = new ContactRoleImpl[3];

			array[0] = filterGetByUuid_C_PrevAndNext(
				session, contactRole, uuid, companyId, orderByComparator, true);

			array[1] = contactRole;

			array[2] = filterGetByUuid_C_PrevAndNext(
				session, contactRole, uuid, companyId, orderByComparator,
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

	protected ContactRole filterGetByUuid_C_PrevAndNext(
		Session session, ContactRole contactRole, String uuid, long companyId,
		OrderByComparator<ContactRole> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_CONTACTROLE_WHERE);
		}
		else {
			query.append(
				_FILTER_SQL_SELECT_CONTACTROLE_NO_INLINE_DISTINCT_WHERE_1);
		}

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_3_SQL);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_C_UUID_2_SQL);
		}

		query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(
				_FILTER_SQL_SELECT_CONTACTROLE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(ContactRoleModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(ContactRoleModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), ContactRole.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, ContactRoleImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, ContactRoleImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindUuid) {
			qPos.add(uuid);
		}

		qPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(contactRole)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<ContactRole> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the contact roles where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (ContactRole contactRole :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(contactRole);
		}
	}

	/**
	 * Returns the number of contact roles where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching contact roles
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_CONTACTROLE_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

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

	/**
	 * Returns the number of contact roles that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching contact roles that the user has permission to view
	 */
	@Override
	public int filterCountByUuid_C(String uuid, long companyId) {
		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return countByUuid_C(uuid, companyId);
		}

		uuid = Objects.toString(uuid, "");

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_CONTACTROLE_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_3_SQL);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_C_UUID_2_SQL);
		}

		query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), ContactRole.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			if (bindUuid) {
				qPos.add(uuid);
			}

			qPos.add(companyId);

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

	private static final String _FINDER_COLUMN_UUID_C_UUID_2 =
		"contactRole.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(contactRole.uuid IS NULL OR contactRole.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_2_SQL =
		"contactRole.uuid_ = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3_SQL =
		"(contactRole.uuid_ IS NULL OR contactRole.uuid_ = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"contactRole.companyId = ?";

	private FinderPath _finderPathFetchByContactRoleKey;
	private FinderPath _finderPathCountByContactRoleKey;

	/**
	 * Returns the contact role where contactRoleKey = &#63; or throws a <code>NoSuchContactRoleException</code> if it could not be found.
	 *
	 * @param contactRoleKey the contact role key
	 * @return the matching contact role
	 * @throws NoSuchContactRoleException if a matching contact role could not be found
	 */
	@Override
	public ContactRole findByContactRoleKey(String contactRoleKey)
		throws NoSuchContactRoleException {

		ContactRole contactRole = fetchByContactRoleKey(contactRoleKey);

		if (contactRole == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("contactRoleKey=");
			msg.append(contactRoleKey);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchContactRoleException(msg.toString());
		}

		return contactRole;
	}

	/**
	 * Returns the contact role where contactRoleKey = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param contactRoleKey the contact role key
	 * @return the matching contact role, or <code>null</code> if a matching contact role could not be found
	 */
	@Override
	public ContactRole fetchByContactRoleKey(String contactRoleKey) {
		return fetchByContactRoleKey(contactRoleKey, true);
	}

	/**
	 * Returns the contact role where contactRoleKey = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param contactRoleKey the contact role key
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching contact role, or <code>null</code> if a matching contact role could not be found
	 */
	@Override
	public ContactRole fetchByContactRoleKey(
		String contactRoleKey, boolean useFinderCache) {

		contactRoleKey = Objects.toString(contactRoleKey, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {contactRoleKey};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByContactRoleKey, finderArgs, this);
		}

		if (result instanceof ContactRole) {
			ContactRole contactRole = (ContactRole)result;

			if (!Objects.equals(
					contactRoleKey, contactRole.getContactRoleKey())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_CONTACTROLE_WHERE);

			boolean bindContactRoleKey = false;

			if (contactRoleKey.isEmpty()) {
				query.append(_FINDER_COLUMN_CONTACTROLEKEY_CONTACTROLEKEY_3);
			}
			else {
				bindContactRoleKey = true;

				query.append(_FINDER_COLUMN_CONTACTROLEKEY_CONTACTROLEKEY_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindContactRoleKey) {
					qPos.add(contactRoleKey);
				}

				List<ContactRole> list = q.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByContactRoleKey, finderArgs, list);
					}
				}
				else {
					ContactRole contactRole = list.get(0);

					result = contactRole;

					cacheResult(contactRole);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(
						_finderPathFetchByContactRoleKey, finderArgs);
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
			return (ContactRole)result;
		}
	}

	/**
	 * Removes the contact role where contactRoleKey = &#63; from the database.
	 *
	 * @param contactRoleKey the contact role key
	 * @return the contact role that was removed
	 */
	@Override
	public ContactRole removeByContactRoleKey(String contactRoleKey)
		throws NoSuchContactRoleException {

		ContactRole contactRole = findByContactRoleKey(contactRoleKey);

		return remove(contactRole);
	}

	/**
	 * Returns the number of contact roles where contactRoleKey = &#63;.
	 *
	 * @param contactRoleKey the contact role key
	 * @return the number of matching contact roles
	 */
	@Override
	public int countByContactRoleKey(String contactRoleKey) {
		contactRoleKey = Objects.toString(contactRoleKey, "");

		FinderPath finderPath = _finderPathCountByContactRoleKey;

		Object[] finderArgs = new Object[] {contactRoleKey};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_CONTACTROLE_WHERE);

			boolean bindContactRoleKey = false;

			if (contactRoleKey.isEmpty()) {
				query.append(_FINDER_COLUMN_CONTACTROLEKEY_CONTACTROLEKEY_3);
			}
			else {
				bindContactRoleKey = true;

				query.append(_FINDER_COLUMN_CONTACTROLEKEY_CONTACTROLEKEY_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindContactRoleKey) {
					qPos.add(contactRoleKey);
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

	private static final String _FINDER_COLUMN_CONTACTROLEKEY_CONTACTROLEKEY_2 =
		"contactRole.contactRoleKey = ?";

	private static final String _FINDER_COLUMN_CONTACTROLEKEY_CONTACTROLEKEY_3 =
		"(contactRole.contactRoleKey IS NULL OR contactRole.contactRoleKey = '')";

	private FinderPath _finderPathWithPaginationFindByType;
	private FinderPath _finderPathWithoutPaginationFindByType;
	private FinderPath _finderPathCountByType;

	/**
	 * Returns all the contact roles where type = &#63;.
	 *
	 * @param type the type
	 * @return the matching contact roles
	 */
	@Override
	public List<ContactRole> findByType(int type) {
		return findByType(type, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the contact roles where type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ContactRoleModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param type the type
	 * @param start the lower bound of the range of contact roles
	 * @param end the upper bound of the range of contact roles (not inclusive)
	 * @return the range of matching contact roles
	 */
	@Override
	public List<ContactRole> findByType(int type, int start, int end) {
		return findByType(type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the contact roles where type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ContactRoleModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param type the type
	 * @param start the lower bound of the range of contact roles
	 * @param end the upper bound of the range of contact roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching contact roles
	 */
	@Override
	public List<ContactRole> findByType(
		int type, int start, int end,
		OrderByComparator<ContactRole> orderByComparator) {

		return findByType(type, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the contact roles where type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ContactRoleModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param type the type
	 * @param start the lower bound of the range of contact roles
	 * @param end the upper bound of the range of contact roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching contact roles
	 */
	@Override
	public List<ContactRole> findByType(
		int type, int start, int end,
		OrderByComparator<ContactRole> orderByComparator,
		boolean useFinderCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByType;
				finderArgs = new Object[] {type};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByType;
			finderArgs = new Object[] {type, start, end, orderByComparator};
		}

		List<ContactRole> list = null;

		if (useFinderCache) {
			list = (List<ContactRole>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ContactRole contactRole : list) {
					if ((type != contactRole.getType())) {
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

			query.append(_SQL_SELECT_CONTACTROLE_WHERE);

			query.append(_FINDER_COLUMN_TYPE_TYPE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(ContactRoleModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(type);

				if (!pagination) {
					list = (List<ContactRole>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<ContactRole>)QueryUtil.list(
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
	 * Returns the first contact role in the ordered set where type = &#63;.
	 *
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching contact role
	 * @throws NoSuchContactRoleException if a matching contact role could not be found
	 */
	@Override
	public ContactRole findByType_First(
			int type, OrderByComparator<ContactRole> orderByComparator)
		throws NoSuchContactRoleException {

		ContactRole contactRole = fetchByType_First(type, orderByComparator);

		if (contactRole != null) {
			return contactRole;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("type=");
		msg.append(type);

		msg.append("}");

		throw new NoSuchContactRoleException(msg.toString());
	}

	/**
	 * Returns the first contact role in the ordered set where type = &#63;.
	 *
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching contact role, or <code>null</code> if a matching contact role could not be found
	 */
	@Override
	public ContactRole fetchByType_First(
		int type, OrderByComparator<ContactRole> orderByComparator) {

		List<ContactRole> list = findByType(type, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last contact role in the ordered set where type = &#63;.
	 *
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching contact role
	 * @throws NoSuchContactRoleException if a matching contact role could not be found
	 */
	@Override
	public ContactRole findByType_Last(
			int type, OrderByComparator<ContactRole> orderByComparator)
		throws NoSuchContactRoleException {

		ContactRole contactRole = fetchByType_Last(type, orderByComparator);

		if (contactRole != null) {
			return contactRole;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("type=");
		msg.append(type);

		msg.append("}");

		throw new NoSuchContactRoleException(msg.toString());
	}

	/**
	 * Returns the last contact role in the ordered set where type = &#63;.
	 *
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching contact role, or <code>null</code> if a matching contact role could not be found
	 */
	@Override
	public ContactRole fetchByType_Last(
		int type, OrderByComparator<ContactRole> orderByComparator) {

		int count = countByType(type);

		if (count == 0) {
			return null;
		}

		List<ContactRole> list = findByType(
			type, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the contact roles before and after the current contact role in the ordered set where type = &#63;.
	 *
	 * @param contactRoleId the primary key of the current contact role
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next contact role
	 * @throws NoSuchContactRoleException if a contact role with the primary key could not be found
	 */
	@Override
	public ContactRole[] findByType_PrevAndNext(
			long contactRoleId, int type,
			OrderByComparator<ContactRole> orderByComparator)
		throws NoSuchContactRoleException {

		ContactRole contactRole = findByPrimaryKey(contactRoleId);

		Session session = null;

		try {
			session = openSession();

			ContactRole[] array = new ContactRoleImpl[3];

			array[0] = getByType_PrevAndNext(
				session, contactRole, type, orderByComparator, true);

			array[1] = contactRole;

			array[2] = getByType_PrevAndNext(
				session, contactRole, type, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected ContactRole getByType_PrevAndNext(
		Session session, ContactRole contactRole, int type,
		OrderByComparator<ContactRole> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_CONTACTROLE_WHERE);

		query.append(_FINDER_COLUMN_TYPE_TYPE_2);

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
			query.append(ContactRoleModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(type);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(contactRole)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<ContactRole> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the contact roles that the user has permission to view where type = &#63;.
	 *
	 * @param type the type
	 * @return the matching contact roles that the user has permission to view
	 */
	@Override
	public List<ContactRole> filterFindByType(int type) {
		return filterFindByType(
			type, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the contact roles that the user has permission to view where type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ContactRoleModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param type the type
	 * @param start the lower bound of the range of contact roles
	 * @param end the upper bound of the range of contact roles (not inclusive)
	 * @return the range of matching contact roles that the user has permission to view
	 */
	@Override
	public List<ContactRole> filterFindByType(int type, int start, int end) {
		return filterFindByType(type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the contact roles that the user has permissions to view where type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ContactRoleModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param type the type
	 * @param start the lower bound of the range of contact roles
	 * @param end the upper bound of the range of contact roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching contact roles that the user has permission to view
	 */
	@Override
	public List<ContactRole> filterFindByType(
		int type, int start, int end,
		OrderByComparator<ContactRole> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByType(type, start, end, orderByComparator);
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
			query.append(_FILTER_SQL_SELECT_CONTACTROLE_WHERE);
		}
		else {
			query.append(
				_FILTER_SQL_SELECT_CONTACTROLE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_TYPE_TYPE_2_SQL);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(
				_FILTER_SQL_SELECT_CONTACTROLE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(ContactRoleModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(ContactRoleModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), ContactRole.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, ContactRoleImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, ContactRoleImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(type);

			return (List<ContactRole>)QueryUtil.list(
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
	 * Returns the contact roles before and after the current contact role in the ordered set of contact roles that the user has permission to view where type = &#63;.
	 *
	 * @param contactRoleId the primary key of the current contact role
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next contact role
	 * @throws NoSuchContactRoleException if a contact role with the primary key could not be found
	 */
	@Override
	public ContactRole[] filterFindByType_PrevAndNext(
			long contactRoleId, int type,
			OrderByComparator<ContactRole> orderByComparator)
		throws NoSuchContactRoleException {

		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByType_PrevAndNext(
				contactRoleId, type, orderByComparator);
		}

		ContactRole contactRole = findByPrimaryKey(contactRoleId);

		Session session = null;

		try {
			session = openSession();

			ContactRole[] array = new ContactRoleImpl[3];

			array[0] = filterGetByType_PrevAndNext(
				session, contactRole, type, orderByComparator, true);

			array[1] = contactRole;

			array[2] = filterGetByType_PrevAndNext(
				session, contactRole, type, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected ContactRole filterGetByType_PrevAndNext(
		Session session, ContactRole contactRole, int type,
		OrderByComparator<ContactRole> orderByComparator, boolean previous) {

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
			query.append(_FILTER_SQL_SELECT_CONTACTROLE_WHERE);
		}
		else {
			query.append(
				_FILTER_SQL_SELECT_CONTACTROLE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_TYPE_TYPE_2_SQL);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(
				_FILTER_SQL_SELECT_CONTACTROLE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(ContactRoleModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(ContactRoleModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), ContactRole.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, ContactRoleImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, ContactRoleImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(type);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(contactRole)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<ContactRole> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the contact roles where type = &#63; from the database.
	 *
	 * @param type the type
	 */
	@Override
	public void removeByType(int type) {
		for (ContactRole contactRole :
				findByType(type, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(contactRole);
		}
	}

	/**
	 * Returns the number of contact roles where type = &#63;.
	 *
	 * @param type the type
	 * @return the number of matching contact roles
	 */
	@Override
	public int countByType(int type) {
		FinderPath finderPath = _finderPathCountByType;

		Object[] finderArgs = new Object[] {type};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_CONTACTROLE_WHERE);

			query.append(_FINDER_COLUMN_TYPE_TYPE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(type);

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
	 * Returns the number of contact roles that the user has permission to view where type = &#63;.
	 *
	 * @param type the type
	 * @return the number of matching contact roles that the user has permission to view
	 */
	@Override
	public int filterCountByType(int type) {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByType(type);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_CONTACTROLE_WHERE);

		query.append(_FINDER_COLUMN_TYPE_TYPE_2_SQL);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), ContactRole.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(type);

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

	private static final String _FINDER_COLUMN_TYPE_TYPE_2 =
		"contactRole.type = ?";

	private static final String _FINDER_COLUMN_TYPE_TYPE_2_SQL =
		"contactRole.type_ = ?";

	private FinderPath _finderPathFetchByN_T;
	private FinderPath _finderPathCountByN_T;

	/**
	 * Returns the contact role where name = &#63; and type = &#63; or throws a <code>NoSuchContactRoleException</code> if it could not be found.
	 *
	 * @param name the name
	 * @param type the type
	 * @return the matching contact role
	 * @throws NoSuchContactRoleException if a matching contact role could not be found
	 */
	@Override
	public ContactRole findByN_T(String name, int type)
		throws NoSuchContactRoleException {

		ContactRole contactRole = fetchByN_T(name, type);

		if (contactRole == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("name=");
			msg.append(name);

			msg.append(", type=");
			msg.append(type);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchContactRoleException(msg.toString());
		}

		return contactRole;
	}

	/**
	 * Returns the contact role where name = &#63; and type = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param name the name
	 * @param type the type
	 * @return the matching contact role, or <code>null</code> if a matching contact role could not be found
	 */
	@Override
	public ContactRole fetchByN_T(String name, int type) {
		return fetchByN_T(name, type, true);
	}

	/**
	 * Returns the contact role where name = &#63; and type = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param name the name
	 * @param type the type
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching contact role, or <code>null</code> if a matching contact role could not be found
	 */
	@Override
	public ContactRole fetchByN_T(
		String name, int type, boolean useFinderCache) {

		name = Objects.toString(name, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {name, type};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByN_T, finderArgs, this);
		}

		if (result instanceof ContactRole) {
			ContactRole contactRole = (ContactRole)result;

			if (!Objects.equals(name, contactRole.getName()) ||
				(type != contactRole.getType())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_CONTACTROLE_WHERE);

			boolean bindName = false;

			if (name.isEmpty()) {
				query.append(_FINDER_COLUMN_N_T_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_N_T_NAME_2);
			}

			query.append(_FINDER_COLUMN_N_T_TYPE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindName) {
					qPos.add(name);
				}

				qPos.add(type);

				List<ContactRole> list = q.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByN_T, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {name, type};
							}

							_log.warn(
								"ContactRolePersistenceImpl.fetchByN_T(String, int, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					ContactRole contactRole = list.get(0);

					result = contactRole;

					cacheResult(contactRole);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(_finderPathFetchByN_T, finderArgs);
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
			return (ContactRole)result;
		}
	}

	/**
	 * Removes the contact role where name = &#63; and type = &#63; from the database.
	 *
	 * @param name the name
	 * @param type the type
	 * @return the contact role that was removed
	 */
	@Override
	public ContactRole removeByN_T(String name, int type)
		throws NoSuchContactRoleException {

		ContactRole contactRole = findByN_T(name, type);

		return remove(contactRole);
	}

	/**
	 * Returns the number of contact roles where name = &#63; and type = &#63;.
	 *
	 * @param name the name
	 * @param type the type
	 * @return the number of matching contact roles
	 */
	@Override
	public int countByN_T(String name, int type) {
		name = Objects.toString(name, "");

		FinderPath finderPath = _finderPathCountByN_T;

		Object[] finderArgs = new Object[] {name, type};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_CONTACTROLE_WHERE);

			boolean bindName = false;

			if (name.isEmpty()) {
				query.append(_FINDER_COLUMN_N_T_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_N_T_NAME_2);
			}

			query.append(_FINDER_COLUMN_N_T_TYPE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindName) {
					qPos.add(name);
				}

				qPos.add(type);

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

	private static final String _FINDER_COLUMN_N_T_NAME_2 =
		"contactRole.name = ? AND ";

	private static final String _FINDER_COLUMN_N_T_NAME_3 =
		"(contactRole.name IS NULL OR contactRole.name = '') AND ";

	private static final String _FINDER_COLUMN_N_T_TYPE_2 =
		"contactRole.type = ?";

	public ContactRolePersistenceImpl() {
		setModelClass(ContactRole.class);

		setModelImplClass(ContactRoleImpl.class);
		setModelPKClass(long.class);

		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put("type", "type_");
		dbColumnNames.put("system", "system_");

		setDBColumnNames(dbColumnNames);
	}

	/**
	 * Caches the contact role in the entity cache if it is enabled.
	 *
	 * @param contactRole the contact role
	 */
	@Override
	public void cacheResult(ContactRole contactRole) {
		entityCache.putResult(
			entityCacheEnabled, ContactRoleImpl.class,
			contactRole.getPrimaryKey(), contactRole);

		finderCache.putResult(
			_finderPathFetchByContactRoleKey,
			new Object[] {contactRole.getContactRoleKey()}, contactRole);

		finderCache.putResult(
			_finderPathFetchByN_T,
			new Object[] {contactRole.getName(), contactRole.getType()},
			contactRole);

		contactRole.resetOriginalValues();
	}

	/**
	 * Caches the contact roles in the entity cache if it is enabled.
	 *
	 * @param contactRoles the contact roles
	 */
	@Override
	public void cacheResult(List<ContactRole> contactRoles) {
		for (ContactRole contactRole : contactRoles) {
			if (entityCache.getResult(
					entityCacheEnabled, ContactRoleImpl.class,
					contactRole.getPrimaryKey()) == null) {

				cacheResult(contactRole);
			}
			else {
				contactRole.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all contact roles.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(ContactRoleImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the contact role.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ContactRole contactRole) {
		entityCache.removeResult(
			entityCacheEnabled, ContactRoleImpl.class,
			contactRole.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((ContactRoleModelImpl)contactRole, true);
	}

	@Override
	public void clearCache(List<ContactRole> contactRoles) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (ContactRole contactRole : contactRoles) {
			entityCache.removeResult(
				entityCacheEnabled, ContactRoleImpl.class,
				contactRole.getPrimaryKey());

			clearUniqueFindersCache((ContactRoleModelImpl)contactRole, true);
		}
	}

	protected void cacheUniqueFindersCache(
		ContactRoleModelImpl contactRoleModelImpl) {

		Object[] args = new Object[] {contactRoleModelImpl.getContactRoleKey()};

		finderCache.putResult(
			_finderPathCountByContactRoleKey, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByContactRoleKey, args, contactRoleModelImpl,
			false);

		args = new Object[] {
			contactRoleModelImpl.getName(), contactRoleModelImpl.getType()
		};

		finderCache.putResult(
			_finderPathCountByN_T, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByN_T, args, contactRoleModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		ContactRoleModelImpl contactRoleModelImpl, boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				contactRoleModelImpl.getContactRoleKey()
			};

			finderCache.removeResult(_finderPathCountByContactRoleKey, args);
			finderCache.removeResult(_finderPathFetchByContactRoleKey, args);
		}

		if ((contactRoleModelImpl.getColumnBitmask() &
			 _finderPathFetchByContactRoleKey.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				contactRoleModelImpl.getOriginalContactRoleKey()
			};

			finderCache.removeResult(_finderPathCountByContactRoleKey, args);
			finderCache.removeResult(_finderPathFetchByContactRoleKey, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
				contactRoleModelImpl.getName(), contactRoleModelImpl.getType()
			};

			finderCache.removeResult(_finderPathCountByN_T, args);
			finderCache.removeResult(_finderPathFetchByN_T, args);
		}

		if ((contactRoleModelImpl.getColumnBitmask() &
			 _finderPathFetchByN_T.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				contactRoleModelImpl.getOriginalName(),
				contactRoleModelImpl.getOriginalType()
			};

			finderCache.removeResult(_finderPathCountByN_T, args);
			finderCache.removeResult(_finderPathFetchByN_T, args);
		}
	}

	/**
	 * Creates a new contact role with the primary key. Does not add the contact role to the database.
	 *
	 * @param contactRoleId the primary key for the new contact role
	 * @return the new contact role
	 */
	@Override
	public ContactRole create(long contactRoleId) {
		ContactRole contactRole = new ContactRoleImpl();

		contactRole.setNew(true);
		contactRole.setPrimaryKey(contactRoleId);

		String uuid = PortalUUIDUtil.generate();

		contactRole.setUuid(uuid);

		contactRole.setCompanyId(CompanyThreadLocal.getCompanyId());

		return contactRole;
	}

	/**
	 * Removes the contact role with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param contactRoleId the primary key of the contact role
	 * @return the contact role that was removed
	 * @throws NoSuchContactRoleException if a contact role with the primary key could not be found
	 */
	@Override
	public ContactRole remove(long contactRoleId)
		throws NoSuchContactRoleException {

		return remove((Serializable)contactRoleId);
	}

	/**
	 * Removes the contact role with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the contact role
	 * @return the contact role that was removed
	 * @throws NoSuchContactRoleException if a contact role with the primary key could not be found
	 */
	@Override
	public ContactRole remove(Serializable primaryKey)
		throws NoSuchContactRoleException {

		Session session = null;

		try {
			session = openSession();

			ContactRole contactRole = (ContactRole)session.get(
				ContactRoleImpl.class, primaryKey);

			if (contactRole == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchContactRoleException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(contactRole);
		}
		catch (NoSuchContactRoleException nsee) {
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
	protected ContactRole removeImpl(ContactRole contactRole) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(contactRole)) {
				contactRole = (ContactRole)session.get(
					ContactRoleImpl.class, contactRole.getPrimaryKeyObj());
			}

			if (contactRole != null) {
				session.delete(contactRole);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (contactRole != null) {
			clearCache(contactRole);
		}

		return contactRole;
	}

	@Override
	public ContactRole updateImpl(ContactRole contactRole) {
		boolean isNew = contactRole.isNew();

		if (!(contactRole instanceof ContactRoleModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(contactRole.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(contactRole);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in contactRole proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom ContactRole implementation " +
					contactRole.getClass());
		}

		ContactRoleModelImpl contactRoleModelImpl =
			(ContactRoleModelImpl)contactRole;

		if (Validator.isNull(contactRole.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			contactRole.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (contactRole.getCreateDate() == null)) {
			if (serviceContext == null) {
				contactRole.setCreateDate(now);
			}
			else {
				contactRole.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!contactRoleModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				contactRole.setModifiedDate(now);
			}
			else {
				contactRole.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (contactRole.isNew()) {
				session.save(contactRole);

				contactRole.setNew(false);
			}
			else {
				contactRole = (ContactRole)session.merge(contactRole);
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
			Object[] args = new Object[] {contactRoleModelImpl.getUuid()};

			finderCache.removeResult(_finderPathCountByUuid, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid, args);

			args = new Object[] {
				contactRoleModelImpl.getUuid(),
				contactRoleModelImpl.getCompanyId()
			};

			finderCache.removeResult(_finderPathCountByUuid_C, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid_C, args);

			args = new Object[] {contactRoleModelImpl.getType()};

			finderCache.removeResult(_finderPathCountByType, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByType, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((contactRoleModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					contactRoleModelImpl.getOriginalUuid()
				};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);

				args = new Object[] {contactRoleModelImpl.getUuid()};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);
			}

			if ((contactRoleModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					contactRoleModelImpl.getOriginalUuid(),
					contactRoleModelImpl.getOriginalCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);

				args = new Object[] {
					contactRoleModelImpl.getUuid(),
					contactRoleModelImpl.getCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);
			}

			if ((contactRoleModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByType.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					contactRoleModelImpl.getOriginalType()
				};

				finderCache.removeResult(_finderPathCountByType, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByType, args);

				args = new Object[] {contactRoleModelImpl.getType()};

				finderCache.removeResult(_finderPathCountByType, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByType, args);
			}
		}

		entityCache.putResult(
			entityCacheEnabled, ContactRoleImpl.class,
			contactRole.getPrimaryKey(), contactRole, false);

		clearUniqueFindersCache(contactRoleModelImpl, false);
		cacheUniqueFindersCache(contactRoleModelImpl);

		contactRole.resetOriginalValues();

		return contactRole;
	}

	/**
	 * Returns the contact role with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the contact role
	 * @return the contact role
	 * @throws NoSuchContactRoleException if a contact role with the primary key could not be found
	 */
	@Override
	public ContactRole findByPrimaryKey(Serializable primaryKey)
		throws NoSuchContactRoleException {

		ContactRole contactRole = fetchByPrimaryKey(primaryKey);

		if (contactRole == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchContactRoleException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return contactRole;
	}

	/**
	 * Returns the contact role with the primary key or throws a <code>NoSuchContactRoleException</code> if it could not be found.
	 *
	 * @param contactRoleId the primary key of the contact role
	 * @return the contact role
	 * @throws NoSuchContactRoleException if a contact role with the primary key could not be found
	 */
	@Override
	public ContactRole findByPrimaryKey(long contactRoleId)
		throws NoSuchContactRoleException {

		return findByPrimaryKey((Serializable)contactRoleId);
	}

	/**
	 * Returns the contact role with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param contactRoleId the primary key of the contact role
	 * @return the contact role, or <code>null</code> if a contact role with the primary key could not be found
	 */
	@Override
	public ContactRole fetchByPrimaryKey(long contactRoleId) {
		return fetchByPrimaryKey((Serializable)contactRoleId);
	}

	/**
	 * Returns all the contact roles.
	 *
	 * @return the contact roles
	 */
	@Override
	public List<ContactRole> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the contact roles.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ContactRoleModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of contact roles
	 * @param end the upper bound of the range of contact roles (not inclusive)
	 * @return the range of contact roles
	 */
	@Override
	public List<ContactRole> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the contact roles.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ContactRoleModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of contact roles
	 * @param end the upper bound of the range of contact roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of contact roles
	 */
	@Override
	public List<ContactRole> findAll(
		int start, int end, OrderByComparator<ContactRole> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the contact roles.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ContactRoleModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of contact roles
	 * @param end the upper bound of the range of contact roles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of contact roles
	 */
	@Override
	public List<ContactRole> findAll(
		int start, int end, OrderByComparator<ContactRole> orderByComparator,
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

		List<ContactRole> list = null;

		if (useFinderCache) {
			list = (List<ContactRole>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_CONTACTROLE);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_CONTACTROLE;

				if (pagination) {
					sql = sql.concat(ContactRoleModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<ContactRole>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<ContactRole>)QueryUtil.list(
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
	 * Removes all the contact roles from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (ContactRole contactRole : findAll()) {
			remove(contactRole);
		}
	}

	/**
	 * Returns the number of contact roles.
	 *
	 * @return the number of contact roles
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_CONTACTROLE);

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
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "contactRoleId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_CONTACTROLE;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return ContactRoleModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the contact role persistence.
	 */
	@Activate
	public void activate() {
		ContactRoleModelImpl.setEntityCacheEnabled(entityCacheEnabled);
		ContactRoleModelImpl.setFinderCacheEnabled(finderCacheEnabled);

		_finderPathWithPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, ContactRoleImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, ContactRoleImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByUuid = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, ContactRoleImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, ContactRoleImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] {String.class.getName()},
			ContactRoleModelImpl.UUID_COLUMN_BITMASK |
			ContactRoleModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByUuid = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] {String.class.getName()});

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, ContactRoleImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, ContactRoleImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			ContactRoleModelImpl.UUID_COLUMN_BITMASK |
			ContactRoleModelImpl.COMPANYID_COLUMN_BITMASK |
			ContactRoleModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByUuid_C = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathFetchByContactRoleKey = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, ContactRoleImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByContactRoleKey",
			new String[] {String.class.getName()},
			ContactRoleModelImpl.CONTACTROLEKEY_COLUMN_BITMASK);

		_finderPathCountByContactRoleKey = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByContactRoleKey",
			new String[] {String.class.getName()});

		_finderPathWithPaginationFindByType = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, ContactRoleImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByType",
			new String[] {
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByType = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, ContactRoleImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByType",
			new String[] {Integer.class.getName()},
			ContactRoleModelImpl.TYPE_COLUMN_BITMASK |
			ContactRoleModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByType = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByType",
			new String[] {Integer.class.getName()});

		_finderPathFetchByN_T = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, ContactRoleImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByN_T",
			new String[] {String.class.getName(), Integer.class.getName()},
			ContactRoleModelImpl.NAME_COLUMN_BITMASK |
			ContactRoleModelImpl.TYPE_COLUMN_BITMASK);

		_finderPathCountByN_T = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByN_T",
			new String[] {String.class.getName(), Integer.class.getName()});
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(ContactRoleImpl.class.getName());
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
				"value.object.column.bitmask.enabled.com.liferay.osb.koroneiki.taproot.model.ContactRole"),
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

	private static final String _SQL_SELECT_CONTACTROLE =
		"SELECT contactRole FROM ContactRole contactRole";

	private static final String _SQL_SELECT_CONTACTROLE_WHERE =
		"SELECT contactRole FROM ContactRole contactRole WHERE ";

	private static final String _SQL_COUNT_CONTACTROLE =
		"SELECT COUNT(contactRole) FROM ContactRole contactRole";

	private static final String _SQL_COUNT_CONTACTROLE_WHERE =
		"SELECT COUNT(contactRole) FROM ContactRole contactRole WHERE ";

	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN =
		"contactRole.contactRoleId";

	private static final String _FILTER_SQL_SELECT_CONTACTROLE_WHERE =
		"SELECT DISTINCT {contactRole.*} FROM Koroneiki_ContactRole contactRole WHERE ";

	private static final String
		_FILTER_SQL_SELECT_CONTACTROLE_NO_INLINE_DISTINCT_WHERE_1 =
			"SELECT {Koroneiki_ContactRole.*} FROM (SELECT DISTINCT contactRole.contactRoleId FROM Koroneiki_ContactRole contactRole WHERE ";

	private static final String
		_FILTER_SQL_SELECT_CONTACTROLE_NO_INLINE_DISTINCT_WHERE_2 =
			") TEMP_TABLE INNER JOIN Koroneiki_ContactRole ON TEMP_TABLE.contactRoleId = Koroneiki_ContactRole.contactRoleId";

	private static final String _FILTER_SQL_COUNT_CONTACTROLE_WHERE =
		"SELECT COUNT(DISTINCT contactRole.contactRoleId) AS COUNT_VALUE FROM Koroneiki_ContactRole contactRole WHERE ";

	private static final String _FILTER_ENTITY_ALIAS = "contactRole";

	private static final String _FILTER_ENTITY_TABLE = "Koroneiki_ContactRole";

	private static final String _ORDER_BY_ENTITY_ALIAS = "contactRole.";

	private static final String _ORDER_BY_ENTITY_TABLE =
		"Koroneiki_ContactRole.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No ContactRole exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No ContactRole exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		ContactRolePersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid", "type", "system"});

	static {
		try {
			Class.forName(KoroneikiPersistenceConstants.class.getName());
		}
		catch (ClassNotFoundException cnfe) {
			throw new ExceptionInInitializerError(cnfe);
		}
	}

}