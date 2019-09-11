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
import com.liferay.osb.koroneiki.taproot.model.ContactRole;
import com.liferay.osb.koroneiki.taproot.permission.AccountPermission;
import com.liferay.osb.koroneiki.taproot.permission.ContactPermission;
import com.liferay.osb.koroneiki.taproot.permission.ContactRolePermission;
import com.liferay.osb.koroneiki.taproot.service.base.ContactRoleServiceBaseImpl;
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
		"json.web.service.context.path=ContactRole"
	},
	service = AopService.class
)
public class ContactRoleServiceImpl extends ContactRoleServiceBaseImpl {

	public ContactRole addContactRole(String name, String description, int type)
		throws PortalException {

		_contactRolePermission.check(
			getPermissionChecker(), TaprootActionKeys.ADD_CONTACT_ROLE);

		return contactRoleLocalService.addContactRole(
			getUserId(), name, description, type);
	}

	public ContactRole deleteContactRole(long contactRoleId)
		throws PortalException {

		_contactRolePermission.check(
			getPermissionChecker(), contactRoleId, ActionKeys.DELETE);

		return contactRoleLocalService.deleteContactRole(contactRoleId);
	}

	public ContactRole deleteContactRole(String contactRoleKey)
		throws PortalException {

		ContactRole contactRole = contactRoleLocalService.getContactRole(
			contactRoleKey);

		_contactRolePermission.check(
			getPermissionChecker(), contactRole, ActionKeys.DELETE);

		return contactRoleLocalService.deleteContactRole(contactRole);
	}

	public List<ContactRole> getContactAccountContactRoles(
			long accountId, long contactId, int start, int end)
		throws PortalException {

		_contactPermission.check(
			getPermissionChecker(), contactId, ActionKeys.VIEW);

		_accountPermission.check(
			getPermissionChecker(), accountId, ActionKeys.VIEW);

		return contactRoleLocalService.getContactAccountContactRoles(
			accountId, contactId, start, end);
	}

	public int getContactAccountContactRolesCount(
			long accountId, long contactId)
		throws PortalException {

		_contactPermission.check(
			getPermissionChecker(), contactId, ActionKeys.VIEW);

		_accountPermission.check(
			getPermissionChecker(), accountId, ActionKeys.VIEW);

		return contactRoleLocalService.getContactAccountContactRolesCount(
			accountId, contactId);
	}

	public List<ContactRole> getContactContactRoles(
			long contactId, int start, int end)
		throws PortalException {

		_contactPermission.check(
			getPermissionChecker(), contactId, ActionKeys.VIEW);

		return contactRoleLocalService.getContactContactRoles(
			contactId, start, end);
	}

	public ContactRole getContactRole(long contactRoleId)
		throws PortalException {

		_contactRolePermission.check(
			getPermissionChecker(), contactRoleId, ActionKeys.VIEW);

		return contactRoleLocalService.getContactRole(contactRoleId);
	}

	public ContactRole getContactRole(String contactRoleKey)
		throws PortalException {

		ContactRole contactRole = contactRoleLocalService.getContactRole(
			contactRoleKey);

		_contactRolePermission.check(
			getPermissionChecker(), contactRole, ActionKeys.VIEW);

		return contactRole;
	}

	public ContactRole updateContactRole(
			long contactRoleId, String name, String description)
		throws PortalException {

		_contactRolePermission.check(
			getPermissionChecker(), contactRoleId, ActionKeys.UPDATE);

		return contactRoleLocalService.updateContactRole(
			contactRoleId, name, description);
	}

	@Reference
	private AccountPermission _accountPermission;

	@Reference
	private ContactPermission _contactPermission;

	@Reference
	private ContactRolePermission _contactRolePermission;

}