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

import com.liferay.osb.koroneiki.taproot.model.ContactTeamRole;
import com.liferay.osb.koroneiki.taproot.service.base.ContactTeamRoleLocalServiceBaseImpl;
import com.liferay.osb.koroneiki.taproot.service.persistence.ContactTeamRolePK;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Kyle Bischof
 */
@Component(
	property = "model.class.name=com.liferay.osb.koroneiki.taproot.model.ContactTeamRole",
	service = AopService.class
)
public class ContactTeamRoleLocalServiceImpl
	extends ContactTeamRoleLocalServiceBaseImpl {

	public ContactTeamRole addContactTeamRole(
			long contactId, long teamId, long contactRoleId)
		throws PortalException {

		validate(contactId, teamId);

		ContactTeamRolePK contactTeamRolePK = new ContactTeamRolePK(
			contactId, teamId, contactRoleId);

		ContactTeamRole contactTeamRole =
			contactTeamRolePersistence.fetchByPrimaryKey(contactTeamRolePK);

		if (contactTeamRole == null) {
			contactTeamRole = contactTeamRolePersistence.create(
				contactTeamRolePK);

			contactTeamRolePersistence.update(contactTeamRole);
		}

		return contactTeamRole;
	}

	public ContactTeamRole deleteContactTeamRole(
		long contactId, long teamId, long contactRoleId) {

		ContactTeamRolePK contactTeamRolePK = new ContactTeamRolePK(
			contactId, teamId, contactRoleId);

		ContactTeamRole contactTeamRole =
			contactTeamRolePersistence.fetchByPrimaryKey(contactTeamRolePK);

		if (contactTeamRole != null) {
			deleteContactTeamRole(contactTeamRole);
		}

		return contactTeamRole;
	}

	public void deleteContactTeamRoles(long contactId, long teamId) {
		contactTeamRolePersistence.removeByCI_TI(contactId, teamId);
	}

	public List<ContactTeamRole> getContactTeamRoles(long contactId) {
		return contactTeamRolePersistence.findByContactId(contactId);
	}

	protected void validate(long contactId, long teamId)
		throws PortalException {

		contactPersistence.findByPrimaryKey(contactId);

		teamPersistence.findByPrimaryKey(teamId);
	}

}