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

package com.liferay.portal.reports.engine.console.service.persistence.impl;

import com.liferay.portal.dao.orm.custom.sql.CustomSQLUtil;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.reports.engine.console.model.Entry;
import com.liferay.portal.reports.engine.console.model.impl.EntryImpl;
import com.liferay.portal.reports.engine.console.service.persistence.EntryFinder;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author Inácio Nery
 */
public class EntryFinderImpl
	extends EntryFinderBaseImpl implements EntryFinder {

	public static final String COUNT_BY_G_CD_N_SN =
		EntryFinder.class.getName() + ".countByG_CD_N_SN";

	public static final String FIND_BY_G_CD_N_SN =
		EntryFinder.class.getName() + ".findByG_CD_N_SN";

	@Override
	public int countByG_CD_N_SN(
		long groupId, String definitionName, String userName, Date createDateGT,
		Date createDateLT, boolean andOperator) {

		return doCountByG_CD_N_SN(
			groupId, definitionName, userName, createDateGT, createDateLT,
			andOperator, false);
	}

	@Override
	public int filterCountByG_CD_N_SN(
		long groupId, String definitionName, String userName, Date createDateGT,
		Date createDateLT, boolean andOperator) {

		return doCountByG_CD_N_SN(
			groupId, definitionName, userName, createDateGT, createDateLT,
			andOperator, true);
	}

	@Override
	public List<Entry> filterFindByG_CD_N_SN(
		long groupId, String definitionName, String userName, Date createDateGT,
		Date createDateLT, boolean andOperator, int start, int end,
		OrderByComparator<Entry> orderByComparator) {

		return doFindByG_CD_N_SN(
			groupId, definitionName, userName, createDateGT, createDateLT,
			andOperator, start, end, orderByComparator, true);
	}

	@Override
	public List<Entry> findByG_CD_N_SN(
		long groupId, String definitionName, String userName, Date createDateGT,
		Date createDateLT, boolean andOperator, int start, int end,
		OrderByComparator<Entry> orderByComparator) {

		return doFindByG_CD_N_SN(
			groupId, definitionName, userName, createDateGT, createDateLT,
			andOperator, start, end, orderByComparator, false);
	}

	protected int doCountByG_CD_N_SN(
		long groupId, String definitionName, String userName, Date createDateGT,
		Date createDateLT, boolean andOperator, boolean inlineSQLHelper) {

		if (Validator.isNull(definitionName) || Validator.isNull(userName)) {
			andOperator = true;
		}

		String[] definitionNames = CustomSQLUtil.keywords(definitionName);
		String[] userNames = CustomSQLUtil.keywords(userName);

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(getClass(), COUNT_BY_G_CD_N_SN);

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, Entry.class.getName(), "Reports_Entry.entryId",
					groupId);
			}

			if (groupId <= 0) {
				sql = StringUtil.replace(
					sql, "(Reports_Entry.groupId = ?) AND", StringPool.BLANK);
			}

			if (Validator.isNull(createDateGT)) {
				sql = StringUtil.replace(
					sql, "(Reports_Entry.createDate > ?) AND",
					StringPool.BLANK);
			}

			if (Validator.isNull(createDateLT)) {
				sql = StringUtil.replace(
					sql, "(Reports_Entry.createDate < ?) AND",
					StringPool.BLANK);
			}

			sql = CustomSQLUtil.replaceKeywords(
				sql, "lower(CAST_TEXT(Reports_Definition.name))",
				StringPool.LIKE, false, definitionNames);
			sql = CustomSQLUtil.replaceKeywords(
				sql, "lower(User_.screenName)", StringPool.LIKE, true,
				userNames);

			sql = CustomSQLUtil.replaceAndOperator(sql, andOperator);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			if (groupId > 0) {
				qPos.add(groupId);
			}

			if (Validator.isNotNull(createDateGT)) {
				qPos.add(createDateGT);
			}

			if (Validator.isNotNull(createDateLT)) {
				qPos.add(createDateLT);
			}

			qPos.add(definitionNames, 2);
			qPos.add(userNames, 2);

			Iterator<Long> itr = q.iterate();

			if (itr.hasNext()) {
				Long count = itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected List<Entry> doFindByG_CD_N_SN(
		long groupId, String definitionName, String userName, Date createDateGT,
		Date createDateLT, boolean andOperator, int start, int end,
		OrderByComparator<Entry> orderByComparator, boolean inlineSQLHelper) {

		if (Validator.isNull(definitionName) || Validator.isNull(userName)) {
			andOperator = true;
		}

		String[] definitionNames = CustomSQLUtil.keywords(definitionName);
		String[] userNames = CustomSQLUtil.keywords(userName);

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(getClass(), FIND_BY_G_CD_N_SN);

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, Entry.class.getName(), "Reports_Entry.entryId",
					groupId);
			}

			if (groupId <= 0) {
				sql = StringUtil.replace(
					sql, "(Reports_Entry.groupId = ?) AND", StringPool.BLANK);
			}

			if (Validator.isNull(createDateGT)) {
				sql = StringUtil.replace(
					sql, "(Reports_Entry.createDate > ?) AND ",
					StringPool.BLANK);
			}

			if (Validator.isNull(createDateLT)) {
				sql = StringUtil.replace(
					sql, "(Reports_Entry.createDate < ?) AND ",
					StringPool.BLANK);
			}

			sql = CustomSQLUtil.replaceKeywords(
				sql, "lower(CAST_TEXT(Reports_Definition.name))",
				StringPool.LIKE, false, definitionNames);
			sql = CustomSQLUtil.replaceKeywords(
				sql, "lower(User_.screenName)", StringPool.LIKE, true,
				userNames);
			sql = CustomSQLUtil.replaceAndOperator(sql, andOperator);

			if (orderByComparator != null) {
				sql = CustomSQLUtil.replaceOrderBy(sql, orderByComparator);
			}

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("Reports_Entry", EntryImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			if (groupId > 0) {
				qPos.add(groupId);
			}

			if (Validator.isNotNull(createDateGT)) {
				qPos.add(createDateGT);
			}

			if (Validator.isNotNull(createDateLT)) {
				qPos.add(createDateLT);
			}

			qPos.add(definitionNames, 2);
			qPos.add(userNames, 2);

			return (List<Entry>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

}