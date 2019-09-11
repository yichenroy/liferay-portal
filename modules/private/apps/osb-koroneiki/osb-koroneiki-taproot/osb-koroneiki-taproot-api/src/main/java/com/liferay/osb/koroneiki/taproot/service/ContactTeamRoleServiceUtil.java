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

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for ContactTeamRole. This utility wraps
 * <code>com.liferay.osb.koroneiki.taproot.service.impl.ContactTeamRoleServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see ContactTeamRoleService
 * @generated
 */
public class ContactTeamRoleServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.osb.koroneiki.taproot.service.impl.ContactTeamRoleServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ContactTeamRoleServiceUtil} to access the contact team role remote service. Add custom service methods to <code>com.liferay.osb.koroneiki.taproot.service.impl.ContactTeamRoleServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static com.liferay.osb.koroneiki.taproot.model.ContactTeamRole
			addContactTeamRole(long contactId, long teamId, long contactRoleId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addContactTeamRole(
			contactId, teamId, contactRoleId);
	}

	public static com.liferay.osb.koroneiki.taproot.model.ContactTeamRole
			deleteContactTeamRole(
				long contactId, long teamId, long contactRoleId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteContactTeamRole(
			contactId, teamId, contactRoleId);
	}

	public static void deleteContactTeamRoles(long contactId, long teamId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteContactTeamRoles(contactId, teamId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static ContactTeamRoleService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<ContactTeamRoleService, ContactTeamRoleService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(ContactTeamRoleService.class);

		ServiceTracker<ContactTeamRoleService, ContactTeamRoleService>
			serviceTracker =
				new ServiceTracker
					<ContactTeamRoleService, ContactTeamRoleService>(
						bundle.getBundleContext(), ContactTeamRoleService.class,
						null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}