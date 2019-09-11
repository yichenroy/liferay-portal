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

package com.liferay.osb.koroneiki.taproot.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link ContactTeamRoleService}.
 *
 * @author Brian Wing Shun Chan
 * @see ContactTeamRoleService
 * @generated
 */
public class ContactTeamRoleServiceWrapper
	implements ContactTeamRoleService, ServiceWrapper<ContactTeamRoleService> {

	public ContactTeamRoleServiceWrapper(
		ContactTeamRoleService contactTeamRoleService) {

		_contactTeamRoleService = contactTeamRoleService;
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ContactTeamRoleServiceUtil} to access the contact team role remote service. Add custom service methods to <code>com.liferay.osb.koroneiki.taproot.service.impl.ContactTeamRoleServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Override
	public com.liferay.osb.koroneiki.taproot.model.ContactTeamRole
			addContactTeamRole(long contactId, long teamId, long contactRoleId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _contactTeamRoleService.addContactTeamRole(
			contactId, teamId, contactRoleId);
	}

	@Override
	public com.liferay.osb.koroneiki.taproot.model.ContactTeamRole
			deleteContactTeamRole(
				long contactId, long teamId, long contactRoleId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _contactTeamRoleService.deleteContactTeamRole(
			contactId, teamId, contactRoleId);
	}

	@Override
	public void deleteContactTeamRoles(long contactId, long teamId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_contactTeamRoleService.deleteContactTeamRoles(contactId, teamId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _contactTeamRoleService.getOSGiServiceIdentifier();
	}

	@Override
	public ContactTeamRoleService getWrappedService() {
		return _contactTeamRoleService;
	}

	@Override
	public void setWrappedService(
		ContactTeamRoleService contactTeamRoleService) {

		_contactTeamRoleService = contactTeamRoleService;
	}

	private ContactTeamRoleService _contactTeamRoleService;

}