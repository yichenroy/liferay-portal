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

package com.liferay.knowledge.base.internal.upgrade.v1_1_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Peter Shin
 */
public class ClassNameUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		updateClassName(
			"com.liferay.knowledgebase.model.Article",
			"com.liferay.knowledgebase.model.KBArticle");
		updateClassName(
			"com.liferay.knowledgebase.model.Comment",
			"com.liferay.knowledgebase.model.KBComment");
		updateClassName(
			"com.liferay.knowledgebase.model.Template",
			"com.liferay.knowledgebase.model.KBTemplate");
	}

	protected long getClassNameId(String className) throws Exception {
		try (PreparedStatement ps = connection.prepareStatement(
				"select classNameId from ClassName_ where value = ?")) {

			ps.setString(1, className);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getLong("classNameId");
				}

				return 0;
			}
		}
	}

	protected void updateClassName(String oldClassName, String newClassName)
		throws Exception {

		long oldClassNameId = getClassNameId(oldClassName);

		if (oldClassNameId != 0) {
			long newClassNameId = getClassNameId(newClassName);

			runSQL(
				"delete from ClassName_ where classNameId = " + newClassNameId);

			runSQL(
				StringBundler.concat(
					"update ClassName_ set value = '", newClassName,
					"' where classNameId = ", oldClassNameId));
		}
	}

}