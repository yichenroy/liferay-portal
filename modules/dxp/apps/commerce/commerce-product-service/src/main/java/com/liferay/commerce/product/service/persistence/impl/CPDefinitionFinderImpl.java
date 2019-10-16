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

package com.liferay.commerce.product.service.persistence.impl;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.impl.CPDefinitionImpl;
import com.liferay.commerce.product.service.persistence.CPDefinitionFinder;
import com.liferay.portal.dao.orm.custom.sql.CustomSQLUtil;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Iterator;
import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public class CPDefinitionFinderImpl
	extends CPDefinitionFinderBaseImpl implements CPDefinitionFinder {

	public static final String COUNT_BY_G_S =
		CPDefinitionFinder.class.getName() + ".countByG_S";

	public static final String FIND_BY_G_S =
		CPDefinitionFinder.class.getName() + ".findByG_S";

	@Override
	public int countByG_S(
		long groupId, String languageId, QueryDefinition<CPDefinition> queryDefinition) {

		return doCountByG_S(groupId, languageId, queryDefinition, false);
	}

	@Override
	public int filterCountByG_S(
		long groupId, String languageId, QueryDefinition<CPDefinition> queryDefinition) {

		return doCountByG_S(groupId, languageId, queryDefinition, true);
	}

	@Override
	public List<CPDefinition> filterFindByG_S(
		long groupId, String languageId, QueryDefinition<CPDefinition> queryDefinition) {

		return doFindByG_S(groupId, languageId, queryDefinition, true);
	}

	@Override
	public List<CPDefinition> findByG_S(
		long groupId, String languageId, QueryDefinition<CPDefinition> queryDefinition) {

		return doFindByG_S(groupId, languageId, queryDefinition, false);
	}

	protected int doCountByG_S(
		long groupId,String languageId, QueryDefinition<CPDefinition> queryDefinition,
		boolean inlineSQLHelper) {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(
				getClass(), COUNT_BY_G_S, queryDefinition,
				CPDefinitionImpl.TABLE_NAME);

			if (groupId <= 0) {
				sql = StringUtil.replace(
					sql, "(CPDefinition.groupId = ?) AND", StringPool.BLANK);
			}

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, CPDefinition.class.getName(),
					"CPDefinition.CPDefinitionId", groupId);
			}

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			if (groupId > 0) {
				qPos.add(groupId);
			}

			qPos.add(languageId);
			qPos.add(queryDefinition.getStatus());

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

	protected List<CPDefinition> doFindByG_S(
		long groupId, String languageId, QueryDefinition<CPDefinition> queryDefinition,
		boolean inlineSQLHelper) {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(
				getClass(), FIND_BY_G_S, queryDefinition,
				CPDefinitionImpl.TABLE_NAME);

			sql = CustomSQLUtil.replaceOrderBy(
				sql, queryDefinition.getOrderByComparator());

			if (groupId <= 0) {
				sql = StringUtil.replace(
					sql, "(CPDefinition.groupId = ?) AND", StringPool.BLANK);
			}

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, CPDefinition.class.getName(),
					"CPDefinition.CPDefinitionId", groupId);
			}

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity(CPDefinitionImpl.TABLE_NAME, CPDefinitionImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			if (groupId > 0) {
				qPos.add(groupId);
			}

			qPos.add(languageId);
			qPos.add(queryDefinition.getStatus());

			return (List<CPDefinition>)QueryUtil.list(
				q, getDialect(), queryDefinition.getStart(),
				queryDefinition.getEnd());
		}
		catch (Exception e) {
			try {
				throw new SystemException(e);
			}
			catch (SystemException se) {
				se.printStackTrace();
			}
		}
		finally {
			closeSession(session);
		}

		return null;
	}

}