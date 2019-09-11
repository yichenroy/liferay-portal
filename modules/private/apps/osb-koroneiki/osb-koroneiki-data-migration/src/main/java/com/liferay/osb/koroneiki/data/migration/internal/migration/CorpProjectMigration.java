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

package com.liferay.osb.koroneiki.data.migration.internal.migration;

import com.liferay.osb.koroneiki.root.model.ExternalLink;
import com.liferay.osb.koroneiki.root.service.ExternalLinkLocalService;
import com.liferay.osb.koroneiki.taproot.model.Account;
import com.liferay.osb.koroneiki.taproot.service.AccountLocalService;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Amos Fong
 */
@Component(immediate = true, service = CorpProjectMigration.class)
public class CorpProjectMigration {

	public void migrate(long userId) throws Exception {
		User user = _userLocalService.getUser(userId);

		StringBundler sb = new StringBundler(4);

		sb.append("select OSB_CorpProject.*, ");
		sb.append("OSB_AccountEntry.dossieraAccountKey from OSB_CorpProject ");
		sb.append("inner join OSB_AccountEntry on ");
		sb.append("OSB_AccountEntry.corpProjectUuid = OSB_CorpProject.uuid_");

		try (Connection connection = DataAccess.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				sb.toString());
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				Account account = _accountLocalService.createAccount(
					resultSet.getLong("corpProjectId"));

				account.setCompanyId(user.getCompanyId());
				account.setUserId(userId);
				account.setCreateDate(resultSet.getTimestamp("createDate"));
				account.setModifiedDate(resultSet.getTimestamp("modifiedDate"));
				account.setAccountKey(resultSet.getString("uuid_"));

				long parentAccountId = _getParentAccountId(
					resultSet.getString("dossieraAccountKey"));

				account.setParentAccountId(parentAccountId);

				account.setName(resultSet.getString("name"));
				account.setStatus(WorkflowConstants.STATUS_APPROVED);
				account.setStatusByUserId(userId);
				account.setStatusByUserName(user.getFullName());
				account.setStatusDate(new Date());
				account.setStatusMessage(StringPool.BLANK);

				_accountLocalService.addAccount(account);

				if (Validator.isNotNull(
						resultSet.getString("dossieraProjectKey"))) {

					_externalLinkLocalService.addExternalLink(
						userId, Account.class.getName(), account.getAccountId(),
						"dossiera", "project",
						resultSet.getString("dossieraProjectKey"));
				}

				if (Validator.isNotNull(
						resultSet.getString("salesforceProjectKey"))) {

					_externalLinkLocalService.addExternalLink(
						userId, Account.class.getName(), account.getAccountId(),
						"salesforce", "project",
						resultSet.getString("salesforceProjectKey"));
				}

				if (_log.isInfoEnabled()) {
					_log.info("Migrated CorpProject " + account.getAccountId());
				}
			}
		}
	}

	private long _getParentAccountId(String dossieraAccountKey)
		throws Exception {

		if (Validator.isNull(dossieraAccountKey)) {
			return 0;
		}

		long classNameId = _classNameLocalService.getClassNameId(Account.class);

		List<ExternalLink> externalLinks =
			_externalLinkLocalService.getExternalLinks(
				classNameId, "dossiera", "account", dossieraAccountKey, 0, 1);

		if (!externalLinks.isEmpty()) {
			ExternalLink externalLink = externalLinks.get(0);

			return externalLink.getClassPK();
		}

		if (_log.isWarnEnabled()) {
			_log.warn(
				"Unable to find account with dossiera account key " +
					dossieraAccountKey);
		}

		return 0;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CorpProjectMigration.class);

	@Reference
	private AccountLocalService _accountLocalService;

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private ExternalLinkLocalService _externalLinkLocalService;

	@Reference
	private UserLocalService _userLocalService;

}