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

import com.liferay.osb.koroneiki.root.service.ExternalLinkLocalService;
import com.liferay.osb.koroneiki.taproot.constants.ContactRoleType;
import com.liferay.osb.koroneiki.taproot.model.Account;
import com.liferay.osb.koroneiki.taproot.model.Contact;
import com.liferay.osb.koroneiki.taproot.model.ContactRole;
import com.liferay.osb.koroneiki.taproot.service.AccountLocalService;
import com.liferay.osb.koroneiki.taproot.service.ContactAccountRoleLocalService;
import com.liferay.osb.koroneiki.taproot.service.ContactLocalService;
import com.liferay.osb.koroneiki.taproot.service.ContactRoleLocalService;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Amos Fong
 */
@Component(immediate = true, service = UserMigration.class)
public class UserMigration {

	@Activate
	public void activate() {
		_memberContactRole = _contactRoleLocalService.getMemberContactRole(
			ContactRoleType.ACCOUNT);
	}

	public void migrate(long userId) throws Exception {
		try (Connection connection = DataAccess.getConnection()) {
			_migrateCorpEntries(connection, userId);

			_migrateCorpProjects(connection, userId);
		}
	}

	private void _migrateContactAccountRoles(
			Connection connection, long webUserId, long webOrganizationId,
			long contactId, long accountId)
		throws Exception {

		_contactAccountRoleLocalService.addContactAccountRole(
			contactId, accountId, _memberContactRole.getContactRoleId());

		StringBundler sb = new StringBundler(6);

		sb.append("select roleId from WEB_UserGroupRole inner join WEB_Group ");
		sb.append("on WEB_Group.groupId = WEB_UserGroupRole.groupId where ");
		sb.append("userId = ");
		sb.append(webUserId);
		sb.append(" and WEB_Group.classPK = ");
		sb.append(webOrganizationId);

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				sb.toString());
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				long roleId = resultSet.getLong("roleId");

				ContactRole contactRole =
					_contactRoleLocalService.fetchContactRole(roleId);

				if (contactRole == null) {
					_log.error("Unable to find contact role " + roleId);

					continue;
				}

				_contactAccountRoleLocalService.addContactAccountRole(
					contactId, accountId, contactRole.getContactRoleId());
			}
		}
	}

	private void _migrateCorpEntries(Connection connection, long userId)
		throws Exception {

		StringBundler sb = new StringBundler(8);

		sb.append("select WEB_User.userId, OSB_CorpEntry.organizationId, ");
		sb.append("WEB_User.uuid_, WEB_User.firstName, WEB_User.middleName, ");
		sb.append("WEB_User.lastName, WEB_User.emailAddress, ");
		sb.append("WEB_User.languageId, OSB_CorpEntry.corpEntryId from ");
		sb.append("WEB_User inner join WEB_Users_Orgs on ");
		sb.append("WEB_Users_Orgs.userId = WEB_User.userId inner join ");
		sb.append("OSB_CorpEntry on OSB_CorpEntry.organizationId = ");
		sb.append("WEB_Users_Orgs.organizationId");

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				sb.toString());
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				long webUserId = resultSet.getLong(1);
				long webOrganizationId = resultSet.getLong(2);
				String contactUuid = resultSet.getString(3);
				String contactFirstName = resultSet.getString(4);
				String contactMiddleName = resultSet.getString(5);
				String contactLastName = resultSet.getString(6);
				String contactEmailAddress = resultSet.getString(7);
				String contactLanguageId = resultSet.getString(8);
				long accountId = resultSet.getLong(9);

				Contact contact = _contactLocalService.fetchContactByUuid(
					contactUuid);

				if (contact == null) {
					contact = _contactLocalService.addContact(
						contactUuid, userId, StringPool.BLANK, contactFirstName,
						contactMiddleName, contactLastName, contactEmailAddress,
						contactLanguageId);
				}

				_migrateContactAccountRoles(
					connection, webUserId, webOrganizationId,
					contact.getContactId(), accountId);
			}
		}
	}

	private void _migrateCorpProjects(Connection connection, long userId)
		throws Exception {

		StringBundler sb = new StringBundler(10);

		sb.append("select WEB_User.userId, OSB_CorpProject.organizationId, ");
		sb.append("WEB_User.uuid_, WEB_User.firstName, WEB_User.middleName, ");
		sb.append("WEB_User.lastName, WEB_User.emailAddress, ");
		sb.append("WEB_User.languageId, OSB_CorpProject.uuid_ from WEB_User ");
		sb.append("inner join WEB_Users_Orgs on WEB_Users_Orgs.userId = ");
		sb.append("WEB_User.userId inner join OSB_CorpProject on ");
		sb.append("OSB_CorpProject.organizationId = ");
		sb.append("WEB_Users_Orgs.organizationId inner join OSB_AccountEntry ");
		sb.append("on OSB_AccountEntry.corpProjectUuid = ");
		sb.append("OSB_CorpProject.uuid_");

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				sb.toString());
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				long webUserId = resultSet.getLong(1);
				long webOrganizationId = resultSet.getLong(2);
				String contactUuid = resultSet.getString(3);
				String contactFirstName = resultSet.getString(4);
				String contactMiddleName = resultSet.getString(5);
				String contactLastName = resultSet.getString(6);
				String contactEmailAddress = resultSet.getString(7);
				String contactLanguageId = resultSet.getString(8);
				String accountKey = resultSet.getString(9);

				Contact contact = _contactLocalService.fetchContactByUuid(
					contactUuid);

				if (contact == null) {
					contact = _contactLocalService.addContact(
						contactUuid, userId, StringPool.BLANK, contactFirstName,
						contactMiddleName, contactLastName, contactEmailAddress,
						contactLanguageId);
				}

				Account account = _accountLocalService.getAccount(accountKey);

				_migrateContactAccountRoles(
					connection, webUserId, webOrganizationId,
					contact.getContactId(), account.getAccountId());
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(UserMigration.class);

	@Reference
	private AccountLocalService _accountLocalService;

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private ContactAccountRoleLocalService _contactAccountRoleLocalService;

	@Reference
	private ContactLocalService _contactLocalService;

	@Reference
	private ContactRoleLocalService _contactRoleLocalService;

	@Reference
	private ExternalLinkLocalService _externalLinkLocalService;

	private ContactRole _memberContactRole;

	@Reference
	private UserLocalService _userLocalService;

}