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
 * Provides the local service utility for Contact. This utility wraps
 * <code>com.liferay.osb.koroneiki.taproot.service.impl.ContactLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see ContactLocalService
 * @generated
 */
public class ContactLocalServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.osb.koroneiki.taproot.service.impl.ContactLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the contact to the database. Also notifies the appropriate model listeners.
	 *
	 * @param contact the contact
	 * @return the contact that was added
	 */
	public static com.liferay.osb.koroneiki.taproot.model.Contact addContact(
		com.liferay.osb.koroneiki.taproot.model.Contact contact) {

		return getService().addContact(contact);
	}

	public static com.liferay.osb.koroneiki.taproot.model.Contact addContact(
			String uuid, long userId, String oktaId, String firstName,
			String middleName, String lastName, String emailAddress,
			String languageId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addContact(
			uuid, userId, oktaId, firstName, middleName, lastName, emailAddress,
			languageId);
	}

	/**
	 * Creates a new contact with the primary key. Does not add the contact to the database.
	 *
	 * @param contactId the primary key for the new contact
	 * @return the new contact
	 */
	public static com.liferay.osb.koroneiki.taproot.model.Contact createContact(
		long contactId) {

		return getService().createContact(contactId);
	}

	/**
	 * Deletes the contact from the database. Also notifies the appropriate model listeners.
	 *
	 * @param contact the contact
	 * @return the contact that was removed
	 * @throws PortalException
	 */
	public static com.liferay.osb.koroneiki.taproot.model.Contact deleteContact(
			com.liferay.osb.koroneiki.taproot.model.Contact contact)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteContact(contact);
	}

	/**
	 * Deletes the contact with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param contactId the primary key of the contact
	 * @return the contact that was removed
	 * @throws PortalException if a contact with the primary key could not be found
	 */
	public static com.liferay.osb.koroneiki.taproot.model.Contact deleteContact(
			long contactId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteContact(contactId);
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			deletePersistedModel(
				com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery
		dynamicQuery() {

		return getService().dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.osb.koroneiki.taproot.model.impl.ContactModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.osb.koroneiki.taproot.model.impl.ContactModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static com.liferay.osb.koroneiki.taproot.model.Contact fetchContact(
		long contactId) {

		return getService().fetchContact(contactId);
	}

	public static com.liferay.osb.koroneiki.taproot.model.Contact
			fetchContactByUuid(String uuid)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().fetchContactByUuid(uuid);
	}

	/**
	 * Returns the contact with the matching UUID and company.
	 *
	 * @param uuid the contact's UUID
	 * @param companyId the primary key of the company
	 * @return the matching contact, or <code>null</code> if a matching contact could not be found
	 */
	public static com.liferay.osb.koroneiki.taproot.model.Contact
		fetchContactByUuidAndCompanyId(String uuid, long companyId) {

		return getService().fetchContactByUuidAndCompanyId(uuid, companyId);
	}

	public static java.util.List
		<com.liferay.osb.koroneiki.taproot.model.Contact> getAccountContacts(
			long accountId, int start, int end) {

		return getService().getAccountContacts(accountId, start, end);
	}

	public static int getAccountContactsCount(long accountId) {
		return getService().getAccountContactsCount(accountId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns the contact with the primary key.
	 *
	 * @param contactId the primary key of the contact
	 * @return the contact
	 * @throws PortalException if a contact with the primary key could not be found
	 */
	public static com.liferay.osb.koroneiki.taproot.model.Contact getContact(
			long contactId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getContact(contactId);
	}

	public static com.liferay.osb.koroneiki.taproot.model.Contact
			getContactByContactKey(String contactKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getContactByContactKey(contactKey);
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
	 * Returns the contact with the matching UUID and company.
	 *
	 * @param uuid the contact's UUID
	 * @param companyId the primary key of the company
	 * @return the matching contact
	 * @throws PortalException if a matching contact could not be found
	 */
	public static com.liferay.osb.koroneiki.taproot.model.Contact
			getContactByUuidAndCompanyId(String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getContactByUuidAndCompanyId(uuid, companyId);
	}

	/**
	 * Returns a range of all the contacts.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.osb.koroneiki.taproot.model.impl.ContactModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of contacts
	 * @param end the upper bound of the range of contacts (not inclusive)
	 * @return the range of contacts
	 */
	public static java.util.List
		<com.liferay.osb.koroneiki.taproot.model.Contact> getContacts(
			int start, int end) {

		return getService().getContacts(start, end);
	}

	/**
	 * Returns the number of contacts.
	 *
	 * @return the number of contacts
	 */
	public static int getContactsCount() {
		return getService().getContactsCount();
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.model.PersistedModel
			getPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	public static java.util.List
		<com.liferay.osb.koroneiki.taproot.model.Contact> getTeamContacts(
			long teamId, int start, int end) {

		return getService().getTeamContacts(teamId, start, end);
	}

	public static int getTeamContactsCount(long teamId) {
		return getService().getTeamContactsCount(teamId);
	}

	public static com.liferay.osb.koroneiki.taproot.model.Contact reindex(
			long contactId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().reindex(contactId);
	}

	public static com.liferay.portal.kernel.search.Hits search(
			long companyId, String keywords, int start, int end,
			com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().search(companyId, keywords, start, end, sort);
	}

	/**
	 * Updates the contact in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param contact the contact
	 * @return the contact that was updated
	 */
	public static com.liferay.osb.koroneiki.taproot.model.Contact updateContact(
		com.liferay.osb.koroneiki.taproot.model.Contact contact) {

		return getService().updateContact(contact);
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

	public static ContactLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<ContactLocalService, ContactLocalService>
		_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(ContactLocalService.class);

		ServiceTracker<ContactLocalService, ContactLocalService>
			serviceTracker =
				new ServiceTracker<ContactLocalService, ContactLocalService>(
					bundle.getBundleContext(), ContactLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}