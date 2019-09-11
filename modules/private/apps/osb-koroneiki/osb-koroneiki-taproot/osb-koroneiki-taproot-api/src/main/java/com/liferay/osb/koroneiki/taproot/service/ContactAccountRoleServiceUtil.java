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
 * Provides the remote service utility for ContactAccountRole. This utility wraps
 * <code>com.liferay.osb.koroneiki.taproot.service.impl.ContactAccountRoleServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see ContactAccountRoleService
 * @generated
 */
public class ContactAccountRoleServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.osb.koroneiki.taproot.service.impl.ContactAccountRoleServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ContactAccountRoleServiceUtil} to access the contact account role remote service. Add custom service methods to <code>com.liferay.osb.koroneiki.taproot.service.impl.ContactAccountRoleServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static com.liferay.osb.koroneiki.taproot.model.ContactAccountRole
			addContactAccountRole(
				long contactId, long accountId, long contactRoleId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addContactAccountRole(
			contactId, accountId, contactRoleId);
	}

	public static com.liferay.osb.koroneiki.taproot.model.ContactAccountRole
			addContactAccountRole(
				String contactUuid, String accountKey, String contactRoleKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addContactAccountRole(
			contactUuid, accountKey, contactRoleKey);
	}

	public static com.liferay.osb.koroneiki.taproot.model.ContactAccountRole
			deleteContactAccountRole(
				long contactId, long accountId, long contactRoleId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteContactAccountRole(
			contactId, accountId, contactRoleId);
	}

	public static com.liferay.osb.koroneiki.taproot.model.ContactAccountRole
			deleteContactAccountRole(
				String contactUuid, String accountKey, String contactRoleKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteContactAccountRole(
			contactUuid, accountKey, contactRoleKey);
	}

	public static void deleteContactAccountRoles(long contactId, long accountId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteContactAccountRoles(contactId, accountId);
	}

	public static void deleteContactAccountRoles(
			String contactUuid, String accountKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteContactAccountRoles(contactUuid, accountKey);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static ContactAccountRoleService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<ContactAccountRoleService, ContactAccountRoleService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			ContactAccountRoleService.class);

		ServiceTracker<ContactAccountRoleService, ContactAccountRoleService>
			serviceTracker =
				new ServiceTracker
					<ContactAccountRoleService, ContactAccountRoleService>(
						bundle.getBundleContext(),
						ContactAccountRoleService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}