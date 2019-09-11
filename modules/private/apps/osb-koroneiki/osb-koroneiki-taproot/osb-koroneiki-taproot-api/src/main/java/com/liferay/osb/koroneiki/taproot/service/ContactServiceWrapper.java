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
 * Provides a wrapper for {@link ContactService}.
 *
 * @author Brian Wing Shun Chan
 * @see ContactService
 * @generated
 */
public class ContactServiceWrapper
	implements ContactService, ServiceWrapper<ContactService> {

	public ContactServiceWrapper(ContactService contactService) {
		_contactService = contactService;
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ContactServiceUtil} to access the contact remote service. Add custom service methods to <code>com.liferay.osb.koroneiki.taproot.service.impl.ContactServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Override
	public com.liferay.osb.koroneiki.taproot.model.Contact addContact(
			String uuid, String oktaId, String firstName, String middleName,
			String lastName, String emailAddress, String languageId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _contactService.addContact(
			uuid, oktaId, firstName, middleName, lastName, emailAddress,
			languageId);
	}

	@Override
	public com.liferay.osb.koroneiki.taproot.model.Contact deleteContact(
			long contactId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _contactService.deleteContact(contactId);
	}

	@Override
	public java.util.List<com.liferay.osb.koroneiki.taproot.model.Contact>
			getAccountContacts(long accountId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _contactService.getAccountContacts(accountId, start, end);
	}

	@Override
	public java.util.List<com.liferay.osb.koroneiki.taproot.model.Contact>
			getAccountContacts(String accountKey, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _contactService.getAccountContacts(accountKey, start, end);
	}

	@Override
	public int getAccountContactsCount(long accountId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _contactService.getAccountContactsCount(accountId);
	}

	@Override
	public int getAccountContactsCount(String accountKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _contactService.getAccountContactsCount(accountKey);
	}

	@Override
	public com.liferay.osb.koroneiki.taproot.model.Contact getContact(
			long contactId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _contactService.getContact(contactId);
	}

	@Override
	public com.liferay.osb.koroneiki.taproot.model.Contact
			getContactByEmailAddress(String emailAddress)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _contactService.getContactByEmailAddress(emailAddress);
	}

	@Override
	public com.liferay.osb.koroneiki.taproot.model.Contact getContactByOktaId(
			String oktaId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _contactService.getContactByOktaId(oktaId);
	}

	@Override
	public com.liferay.osb.koroneiki.taproot.model.Contact getContactByUuid(
			String uuid)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _contactService.getContactByUuid(uuid);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _contactService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.osb.koroneiki.taproot.model.Contact updateContact(
			long contactId, String uuid, String oktaId, String firstName,
			String middleName, String lastName, String emailAddress,
			String languageId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _contactService.updateContact(
			contactId, uuid, oktaId, firstName, middleName, lastName,
			emailAddress, languageId);
	}

	@Override
	public ContactService getWrappedService() {
		return _contactService;
	}

	@Override
	public void setWrappedService(ContactService contactService) {
		_contactService = contactService;
	}

	private ContactService _contactService;

}