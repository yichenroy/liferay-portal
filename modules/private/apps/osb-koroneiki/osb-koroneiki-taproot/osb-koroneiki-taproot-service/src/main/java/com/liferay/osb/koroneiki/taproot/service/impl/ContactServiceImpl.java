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
import com.liferay.osb.koroneiki.taproot.permission.AccountPermission;
import com.liferay.osb.koroneiki.taproot.permission.ContactPermission;
import com.liferay.osb.koroneiki.taproot.service.AccountLocalService;
import com.liferay.osb.koroneiki.taproot.service.base.ContactServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Kyle Bischof
 */
@Component(
	property = {
		"json.web.service.context.name=koroneiki",
		"json.web.service.context.path=Contact"
	},
	service = AopService.class
)
public class ContactServiceImpl extends ContactServiceBaseImpl {

	public Contact addContact(
			String uuid, String oktaId, String firstName, String middleName,
			String lastName, String emailAddress, String languageId)
		throws PortalException {

		_contactPermission.check(
			getPermissionChecker(), TaprootActionKeys.ADD_CONTACT);

		return contactLocalService.addContact(
			uuid, getUserId(), oktaId, firstName, middleName, lastName,
			emailAddress, languageId);
	}

	public Contact deleteContact(long contactId) throws PortalException {
		_contactPermission.check(
			getPermissionChecker(), contactId, ActionKeys.DELETE);

		return contactLocalService.deleteContact(contactId);
	}

	public List<Contact> getAccountContacts(long accountId, int start, int end)
		throws PortalException {

		_accountPermission.check(
			getPermissionChecker(), accountId, ActionKeys.VIEW);

		return contactLocalService.getAccountContacts(accountId, start, end);
	}

	public List<Contact> getAccountContacts(
			String accountKey, int start, int end)
		throws PortalException {

		Account account = _accountLocalService.getAccount(accountKey);

		_accountPermission.check(
			getPermissionChecker(), account, ActionKeys.VIEW);

		return contactLocalService.getAccountContacts(
			account.getAccountId(), start, end);
	}

	public int getAccountContactsCount(long accountId) throws PortalException {
		_accountPermission.check(
			getPermissionChecker(), accountId, ActionKeys.VIEW);

		return contactLocalService.getAccountContactsCount(accountId);
	}

	public int getAccountContactsCount(String accountKey)
		throws PortalException {

		Account account = _accountLocalService.getAccount(accountKey);

		_accountPermission.check(
			getPermissionChecker(), account, ActionKeys.VIEW);

		return contactLocalService.getAccountContactsCount(
			account.getAccountId());
	}

	public Contact getContact(long contactId) throws PortalException {
		_contactPermission.check(
			getPermissionChecker(), contactId, ActionKeys.VIEW);

		return contactLocalService.getContact(contactId);
	}

	public Contact getContactByEmailAddress(String emailAddress)
		throws PortalException {

		Contact contact = contactLocalService.getContactByEmailAddress(
			emailAddress);

		_contactPermission.check(
			getPermissionChecker(), contact, ActionKeys.VIEW);

		return contact;
	}

	public Contact getContactByOktaId(String oktaId) throws PortalException {
		Contact contact = contactLocalService.getContactByOktaId(oktaId);

		_contactPermission.check(
			getPermissionChecker(), contact, ActionKeys.VIEW);

		return contact;
	}

	public Contact getContactByUuid(String uuid) throws PortalException {
		Contact contact = contactLocalService.getContactByUuid(uuid);

		_contactPermission.check(
			getPermissionChecker(), contact, ActionKeys.VIEW);

		return contact;
	}

	public Contact updateContact(
			long contactId, String uuid, String oktaId, String firstName,
			String middleName, String lastName, String emailAddress,
			String languageId)
		throws PortalException {

		_contactPermission.check(
			getPermissionChecker(), contactId, ActionKeys.UPDATE);

		return contactLocalService.updateContact(
			contactId, uuid, oktaId, firstName, middleName, lastName,
			emailAddress, languageId);
	}

	@Reference
	private AccountLocalService _accountLocalService;

	@Reference
	private AccountPermission _accountPermission;

	@Reference
	private ContactPermission _contactPermission;

}