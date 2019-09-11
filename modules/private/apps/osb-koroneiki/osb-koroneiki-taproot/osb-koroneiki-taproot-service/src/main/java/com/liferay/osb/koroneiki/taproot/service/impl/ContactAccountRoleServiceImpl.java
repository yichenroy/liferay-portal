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

package com.liferay.osb.koroneiki.taproot.service.impl;

import com.liferay.osb.koroneiki.taproot.constants.TaprootActionKeys;
import com.liferay.osb.koroneiki.taproot.model.Account;
import com.liferay.osb.koroneiki.taproot.model.Contact;
import com.liferay.osb.koroneiki.taproot.model.ContactAccountRole;
import com.liferay.osb.koroneiki.taproot.model.ContactRole;
import com.liferay.osb.koroneiki.taproot.permission.AccountPermission;
import com.liferay.osb.koroneiki.taproot.permission.ContactPermission;
import com.liferay.osb.koroneiki.taproot.permission.ContactRolePermission;
import com.liferay.osb.koroneiki.taproot.service.AccountLocalService;
import com.liferay.osb.koroneiki.taproot.service.ContactLocalService;
import com.liferay.osb.koroneiki.taproot.service.ContactRoleLocalService;
import com.liferay.osb.koroneiki.taproot.service.base.ContactAccountRoleServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Kyle Bischof
 * @author Amos Fong
 */
@Component(
	property = {
		"json.web.service.context.name=koroneiki",
		"json.web.service.context.path=ContactAccountRole"
	},
	service = AopService.class
)
public class ContactAccountRoleServiceImpl
	extends ContactAccountRoleServiceBaseImpl {

	public ContactAccountRole addContactAccountRole(
			long contactId, long accountId, long contactRoleId)
		throws PortalException {

		_contactPermission.check(
			getPermissionChecker(), contactId, ActionKeys.VIEW);

		_accountPermission.check(
			getPermissionChecker(), accountId,
			TaprootActionKeys.ASSIGN_CONTACT);

		_contactRolePermission.check(
			getPermissionChecker(), contactRoleId,
			TaprootActionKeys.ASSIGN_CONTACT);

		return contactAccountRoleLocalService.addContactAccountRole(
			contactId, accountId, contactRoleId);
	}

	public ContactAccountRole addContactAccountRole(
			String contactUuid, String accountKey, String contactRoleKey)
		throws PortalException {

		Contact contact = _contactLocalService.getContactByUuid(contactUuid);
		Account account = _accountLocalService.getAccount(accountKey);
		ContactRole contactRole = _contactRoleLocalService.getContactRole(
			contactRoleKey);

		_contactPermission.check(
			getPermissionChecker(), contact, ActionKeys.VIEW);

		_accountPermission.check(
			getPermissionChecker(), account, TaprootActionKeys.ASSIGN_CONTACT);

		_contactRolePermission.check(
			getPermissionChecker(), contactRole,
			TaprootActionKeys.ASSIGN_CONTACT);

		return contactAccountRoleLocalService.addContactAccountRole(
			contact.getContactId(), account.getAccountId(),
			contactRole.getContactRoleId());
	}

	public ContactAccountRole deleteContactAccountRole(
			long contactId, long accountId, long contactRoleId)
		throws PortalException {

		_contactPermission.check(
			getPermissionChecker(), contactId, ActionKeys.VIEW);

		_accountPermission.check(
			getPermissionChecker(), accountId,
			TaprootActionKeys.ASSIGN_CONTACT);

		_contactRolePermission.check(
			getPermissionChecker(), contactRoleId,
			TaprootActionKeys.ASSIGN_CONTACT);

		return contactAccountRoleLocalService.deleteContactAccountRole(
			contactId, accountId, contactRoleId);
	}

	public ContactAccountRole deleteContactAccountRole(
			String contactUuid, String accountKey, String contactRoleKey)
		throws PortalException {

		Contact contact = _contactLocalService.getContactByUuid(contactUuid);
		Account account = _accountLocalService.getAccount(accountKey);
		ContactRole contactRole = _contactRoleLocalService.getContactRole(
			contactRoleKey);

		_contactPermission.check(
			getPermissionChecker(), contact, ActionKeys.VIEW);

		_accountPermission.check(
			getPermissionChecker(), account, TaprootActionKeys.ASSIGN_CONTACT);

		_contactRolePermission.check(
			getPermissionChecker(), contactRole,
			TaprootActionKeys.ASSIGN_CONTACT);

		return contactAccountRoleLocalService.deleteContactAccountRole(
			contact.getContactId(), account.getAccountId(),
			contactRole.getContactRoleId());
	}

	public void deleteContactAccountRoles(long contactId, long accountId)
		throws PortalException {

		_contactPermission.check(
			getPermissionChecker(), contactId, ActionKeys.VIEW);

		_accountPermission.check(
			getPermissionChecker(), accountId,
			TaprootActionKeys.ASSIGN_CONTACT);

		List<ContactAccountRole> contactAccountRoles =
			contactAccountRolePersistence.findByCI_AI(contactId, accountId);

		for (ContactAccountRole contactAccountRole : contactAccountRoles) {
			_contactRolePermission.check(
				getPermissionChecker(), contactAccountRole.getContactRole(),
				TaprootActionKeys.ASSIGN_CONTACT);
		}

		contactAccountRoleLocalService.deleteContactAccountRoles(
			contactId, accountId);
	}

	public void deleteContactAccountRoles(String contactUuid, String accountKey)
		throws PortalException {

		Contact contact = _contactLocalService.getContactByUuid(contactUuid);
		Account account = _accountLocalService.getAccount(accountKey);

		_contactPermission.check(
			getPermissionChecker(), contact, ActionKeys.VIEW);

		_accountPermission.check(
			getPermissionChecker(), account, TaprootActionKeys.ASSIGN_CONTACT);

		List<ContactAccountRole> contactAccountRoles =
			contactAccountRolePersistence.findByCI_AI(
				contact.getContactId(), account.getAccountId());

		for (ContactAccountRole contactAccountRole : contactAccountRoles) {
			_contactRolePermission.check(
				getPermissionChecker(), contactAccountRole.getContactRole(),
				TaprootActionKeys.ASSIGN_CONTACT);
		}

		contactAccountRoleLocalService.deleteContactAccountRoles(
			contact.getContactId(), account.getAccountId());
	}

	@Reference
	private AccountLocalService _accountLocalService;

	@Reference
	private AccountPermission _accountPermission;

	@Reference
	private ContactLocalService _contactLocalService;

	@Reference
	private ContactPermission _contactPermission;

	@Reference
	private ContactRoleLocalService _contactRoleLocalService;

	@Reference
	private ContactRolePermission _contactRolePermission;

}