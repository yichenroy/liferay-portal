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
 * Provides the remote service utility for Contact. This utility wraps
 * <code>com.liferay.osb.koroneiki.taproot.service.impl.ContactServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see ContactService
 * @generated
 */
public class ContactServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.osb.koroneiki.taproot.service.impl.ContactServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ContactServiceUtil} to access the contact remote service. Add custom service methods to <code>com.liferay.osb.koroneiki.taproot.service.impl.ContactServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static com.liferay.osb.koroneiki.taproot.model.Contact addContact(
			String uuid, String oktaId, String firstName, String middleName,
			String lastName, String emailAddress, String languageId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addContact(
			uuid, oktaId, firstName, middleName, lastName, emailAddress,
			languageId);
	}

	public static com.liferay.osb.koroneiki.taproot.model.Contact deleteContact(
			long contactId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteContact(contactId);
	}

	public static java.util.List
		<com.liferay.osb.koroneiki.taproot.model.Contact> getAccountContacts(
				long accountId, int start, int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getAccountContacts(accountId, start, end);
	}

	public static java.util.List
		<com.liferay.osb.koroneiki.taproot.model.Contact> getAccountContacts(
				String accountKey, int start, int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getAccountContacts(accountKey, start, end);
	}

	public static int getAccountContactsCount(long accountId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getAccountContactsCount(accountId);
	}

	public static int getAccountContactsCount(String accountKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getAccountContactsCount(accountKey);
	}

	public static com.liferay.osb.koroneiki.taproot.model.Contact getContact(
			long contactId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getContact(contactId);
	}

	public static com.liferay.osb.koroneiki.taproot.model.Contact
			getContactByEmailAddress(String emailAddress)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getContactByEmailAddress(emailAddress);
	}

	public static com.liferay.osb.koroneiki.taproot.model.Contact
			getContactByOktaId(String oktaId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getContactByOktaId(oktaId);
	}

	public static com.liferay.osb.koroneiki.taproot.model.Contact
			getContactByUuid(String uuid)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getContactByUuid(uuid);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.osb.koroneiki.taproot.model.Contact updateContact(
			long contactId, String uuid, String oktaId, String firstName,
			String middleName, String lastName, String emailAddress,
			String languageId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateContact(
			contactId, uuid, oktaId, firstName, middleName, lastName,
			emailAddress, languageId);
	}

	public static ContactService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<ContactService, ContactService>
		_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(ContactService.class);

		ServiceTracker<ContactService, ContactService> serviceTracker =
			new ServiceTracker<ContactService, ContactService>(
				bundle.getBundleContext(), ContactService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}