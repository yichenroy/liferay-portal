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

import com.liferay.osb.koroneiki.taproot.model.ContactAccountRole;
import com.liferay.osb.koroneiki.taproot.service.AccountLocalService;
import com.liferay.osb.koroneiki.taproot.service.base.ContactAccountRoleLocalServiceBaseImpl;
import com.liferay.osb.koroneiki.taproot.service.persistence.ContactAccountRolePK;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Kyle Bischof
 */
@Component(
	property = "model.class.name=com.liferay.osb.koroneiki.taproot.model.ContactAccountRole",
	service = AopService.class
)
public class ContactAccountRoleLocalServiceImpl
	extends ContactAccountRoleLocalServiceBaseImpl {

	public ContactAccountRole addContactAccountRole(
			long contactId, long accountId, long contactRoleId)
		throws PortalException {

		validate(contactId, accountId);

		ContactAccountRolePK contactAccountRolePK = new ContactAccountRolePK(
			contactId, accountId, contactRoleId);

		ContactAccountRole contactAccountRole =
			contactAccountRolePersistence.fetchByPrimaryKey(
				contactAccountRolePK);

		if (contactAccountRole == null) {
			contactAccountRole = contactAccountRolePersistence.create(
				contactAccountRolePK);

			contactAccountRolePersistence.update(contactAccountRole);

			_accountLocalService.reindex(accountId);
		}

		return contactAccountRole;
	}

	public ContactAccountRole deleteContactAccountRole(
			long contactId, long accountId, long contactRoleId)
		throws PortalException {

		ContactAccountRolePK contactAccountRolePK = new ContactAccountRolePK(
			contactId, accountId, contactRoleId);

		ContactAccountRole contactAccountRole =
			contactAccountRolePersistence.fetchByPrimaryKey(
				contactAccountRolePK);

		if (contactAccountRole != null) {
			deleteContactAccountRole(contactAccountRole);

			_accountLocalService.reindex(accountId);
		}

		return contactAccountRole;
	}

	public void deleteContactAccountRoles(long contactId, long accountId)
		throws PortalException {

		contactAccountRolePersistence.removeByCI_AI(contactId, accountId);

		_accountLocalService.reindex(accountId);
	}

	public List<ContactAccountRole> getContactAccountRoles(
		long contactId, long accountId) {

		return contactAccountRolePersistence.findByCI_AI(contactId, accountId);
	}

	public List<ContactAccountRole> getContactAccountRolesByAccountId(
		long accountId) {

		return contactAccountRolePersistence.findByAccountId(accountId);
	}

	protected void validate(long contactId, long accountId)
		throws PortalException {

		contactPersistence.findByPrimaryKey(contactId);

		accountPersistence.findByPrimaryKey(accountId);
	}

	@Reference
	private AccountLocalService _accountLocalService;

}