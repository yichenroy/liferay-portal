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

package com.liferay.portal.workflow.kaleo.designer.web.internal.upgrade.v1_0_2;

import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.exportimport.kernel.staging.StagingUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinition;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionLocalService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * @author Rodrigo Paulino
 */
public class UpgradeKaleoDefinition extends UpgradeProcess {

	public UpgradeKaleoDefinition(
		CounterLocalService counterLocalService,
		KaleoDefinitionLocalService kaleoDefinitionLocalService,
		UserLocalService userLocalService) {

		_counterLocalService = counterLocalService;
		_kaleoDefinitionLocalService = kaleoDefinitionLocalService;
		_userLocalService = userLocalService;
	}

	protected void addKaleoDefinition(
			long groupId, long userId, Timestamp createDate,
			Timestamp modifiedDate, String name, String title, String content,
			int version)
		throws PortalException {

		User user = _userLocalService.getUser(userId);

		long liveGroupId = StagingUtil.getLiveGroupId(groupId);

		long kaleoDefinitionId = _counterLocalService.increment();

		KaleoDefinition kaleoDefinition =
			_kaleoDefinitionLocalService.createKaleoDefinition(
				kaleoDefinitionId);

		kaleoDefinition.setGroupId(liveGroupId);
		kaleoDefinition.setCompanyId(user.getCompanyId());
		kaleoDefinition.setUserId(user.getUserId());
		kaleoDefinition.setUserName(user.getFullName());
		kaleoDefinition.setCreateDate(createDate);
		kaleoDefinition.setModifiedDate(modifiedDate);
		kaleoDefinition.setName(name);
		kaleoDefinition.setTitle(title);
		kaleoDefinition.setContent(content);
		kaleoDefinition.setVersion(version);
		kaleoDefinition.setActive(false);

		_kaleoDefinitionLocalService.addKaleoDefinition(kaleoDefinition);
	}

	protected void addKaleoDefinitionsFromKaleoDefinitionVersion()
		throws PortalException, SQLException {

		StringBundler sb1 = new StringBundler(8);

		sb1.append("select KaleoDefinitionVersion.* from ");
		sb1.append("KaleoDefinitionVersion join (select name, max(version) ");
		sb1.append("as version from KaleoDefinitionVersion group by name) ");
		sb1.append("sub on sub.name = KaleoDefinitionVersion.name and sub.");
		sb1.append("version = KaleoDefinitionVersion.version left join ");
		sb1.append("KaleoDefinition on KaleoDefinitionVersion.name = ");
		sb1.append("KaleoDefinition.name where KaleoDefinition.");
		sb1.append("kaleoDefinitionId is null");

		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement ps1 = connection.prepareStatement(sb1.toString());
			ResultSet rs = ps1.executeQuery()) {

			while (rs.next()) {
				long groupId = rs.getLong("groupId");
				long userId = rs.getLong("userId");
				Timestamp createDate = rs.getTimestamp("createDate");
				Timestamp modifiedDate = rs.getTimestamp("modifiedDate");
				String name = rs.getString("name");
				String title = rs.getString("title");
				String content = rs.getString("content");
				int version = rs.getInt("version");

				addKaleoDefinition(
					groupId, userId, createDate, modifiedDate, name, title,
					content, version);
			}
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		if (hasTable("KaleoDraftDefinition")) {
			addKaleoDefinitionsFromKaleoDefinitionVersion();
		}
	}

	private final CounterLocalService _counterLocalService;
	private final KaleoDefinitionLocalService _kaleoDefinitionLocalService;
	private final UserLocalService _userLocalService;

}