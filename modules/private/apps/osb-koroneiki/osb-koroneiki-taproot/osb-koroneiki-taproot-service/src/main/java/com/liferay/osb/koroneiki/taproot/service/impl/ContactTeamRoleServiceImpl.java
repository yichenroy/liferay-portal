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
import com.liferay.osb.koroneiki.taproot.model.ContactTeamRole;
import com.liferay.osb.koroneiki.taproot.permission.ContactPermission;
import com.liferay.osb.koroneiki.taproot.permission.ContactRolePermission;
import com.liferay.osb.koroneiki.taproot.permission.TeamPermission;
import com.liferay.osb.koroneiki.taproot.service.base.ContactTeamRoleServiceBaseImpl;
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
		"json.web.service.context.path=ContactTeamRole"
	},
	service = AopService.class
)
public class ContactTeamRoleServiceImpl extends ContactTeamRoleServiceBaseImpl {

	public ContactTeamRole addContactTeamRole(
			long contactId, long teamId, long contactRoleId)
		throws PortalException {

		_contactPermission.check(
			getPermissionChecker(), contactId, ActionKeys.VIEW);

		_teamPermission.check(
			getPermissionChecker(), teamId, TaprootActionKeys.ASSIGN_CONTACT);

		_contactRolePermission.check(
			getPermissionChecker(), contactRoleId,
			TaprootActionKeys.ASSIGN_CONTACT);

		return contactTeamRoleLocalService.addContactTeamRole(
			contactId, teamId, contactRoleId);
	}

	public ContactTeamRole deleteContactTeamRole(
			long contactId, long teamId, long contactRoleId)
		throws PortalException {

		_contactPermission.check(
			getPermissionChecker(), contactId, ActionKeys.VIEW);

		_teamPermission.check(
			getPermissionChecker(), teamId, TaprootActionKeys.ASSIGN_CONTACT);

		_contactRolePermission.check(
			getPermissionChecker(), contactRoleId,
			TaprootActionKeys.ASSIGN_CONTACT);

		return contactTeamRoleLocalService.deleteContactTeamRole(
			contactId, teamId, contactRoleId);
	}

	public void deleteContactTeamRoles(long contactId, long teamId)
		throws PortalException {

		_contactPermission.check(
			getPermissionChecker(), contactId, ActionKeys.VIEW);

		_teamPermission.check(
			getPermissionChecker(), teamId, TaprootActionKeys.ASSIGN_CONTACT);

		List<ContactTeamRole> contactTeamRoles =
			contactTeamRolePersistence.findByCI_TI(contactId, teamId);

		for (ContactTeamRole contactTeamRole : contactTeamRoles) {
			_contactRolePermission.check(
				getPermissionChecker(), contactTeamRole.getContactRoleId(),
				TaprootActionKeys.ASSIGN_CONTACT);
		}

		contactTeamRoleLocalService.deleteContactTeamRoles(contactId, teamId);
	}

	@Reference
	private ContactPermission _contactPermission;

	@Reference
	private ContactRolePermission _contactRolePermission;

	@Reference
	private TeamPermission _teamPermission;

}