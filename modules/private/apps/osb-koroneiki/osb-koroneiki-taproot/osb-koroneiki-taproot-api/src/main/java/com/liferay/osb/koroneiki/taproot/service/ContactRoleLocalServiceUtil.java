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
 * Provides the local service utility for ContactRole. This utility wraps
 * <code>com.liferay.osb.koroneiki.taproot.service.impl.ContactRoleLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see ContactRoleLocalService
 * @generated
 */
public class ContactRoleLocalServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.osb.koroneiki.taproot.service.impl.ContactRoleLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the contact role to the database. Also notifies the appropriate model listeners.
	 *
	 * @param contactRole the contact role
	 * @return the contact role that was added
	 */
	public static com.liferay.osb.koroneiki.taproot.model.ContactRole
		addContactRole(
			com.liferay.osb.koroneiki.taproot.model.ContactRole contactRole) {

		return getService().addContactRole(contactRole);
	}

	public static com.liferay.osb.koroneiki.taproot.model.ContactRole
			addContactRole(
				long userId, String name, String description, int type)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addContactRole(userId, name, description, type);
	}

	public static void checkMemberRoles()
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().checkMemberRoles();
	}

	/**
	 * Creates a new contact role with the primary key. Does not add the contact role to the database.
	 *
	 * @param contactRoleId the primary key for the new contact role
	 * @return the new contact role
	 */
	public static com.liferay.osb.koroneiki.taproot.model.ContactRole
		createContactRole(long contactRoleId) {

		return getService().createContactRole(contactRoleId);
	}

	/**
	 * Deletes the contact role from the database. Also notifies the appropriate model listeners.
	 *
	 * @param contactRole the contact role
	 * @return the contact role that was removed
	 * @throws PortalException
	 */
	public static com.liferay.osb.koroneiki.taproot.model.ContactRole
			deleteContactRole(
				com.liferay.osb.koroneiki.taproot.model.ContactRole contactRole)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteContactRole(contactRole);
	}

	/**
	 * Deletes the contact role with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param contactRoleId the primary key of the contact role
	 * @return the contact role that was removed
	 * @throws PortalException if a contact role with the primary key could not be found
	 */
	public static com.liferay.osb.koroneiki.taproot.model.ContactRole
			deleteContactRole(long contactRoleId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteContactRole(contactRoleId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.osb.koroneiki.taproot.model.impl.ContactRoleModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.osb.koroneiki.taproot.model.impl.ContactRoleModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.osb.koroneiki.taproot.model.ContactRole
		fetchContactRole(long contactRoleId) {

		return getService().fetchContactRole(contactRoleId);
	}

	/**
	 * Returns the contact role with the matching UUID and company.
	 *
	 * @param uuid the contact role's UUID
	 * @param companyId the primary key of the company
	 * @return the matching contact role, or <code>null</code> if a matching contact role could not be found
	 */
	public static com.liferay.osb.koroneiki.taproot.model.ContactRole
		fetchContactRoleByUuidAndCompanyId(String uuid, long companyId) {

		return getService().fetchContactRoleByUuidAndCompanyId(uuid, companyId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static java.util.List
		<com.liferay.osb.koroneiki.taproot.model.ContactRole>
			getContactAccountContactRoles(
				long accountId, long contactId, int start, int end) {

		return getService().getContactAccountContactRoles(
			accountId, contactId, start, end);
	}

	public static int getContactAccountContactRolesCount(
		long accountId, long contactId) {

		return getService().getContactAccountContactRolesCount(
			accountId, contactId);
	}

	public static java.util.List
		<com.liferay.osb.koroneiki.taproot.model.ContactRole>
			getContactContactRoles(long contactId, int start, int end) {

		return getService().getContactContactRoles(contactId, start, end);
	}

	/**
	 * Returns the contact role with the primary key.
	 *
	 * @param contactRoleId the primary key of the contact role
	 * @return the contact role
	 * @throws PortalException if a contact role with the primary key could not be found
	 */
	public static com.liferay.osb.koroneiki.taproot.model.ContactRole
			getContactRole(long contactRoleId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getContactRole(contactRoleId);
	}

	public static com.liferay.osb.koroneiki.taproot.model.ContactRole
			getContactRole(String contactRoleKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getContactRole(contactRoleKey);
	}

	/**
	 * Returns the contact role with the matching UUID and company.
	 *
	 * @param uuid the contact role's UUID
	 * @param companyId the primary key of the company
	 * @return the matching contact role
	 * @throws PortalException if a matching contact role could not be found
	 */
	public static com.liferay.osb.koroneiki.taproot.model.ContactRole
			getContactRoleByUuidAndCompanyId(String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getContactRoleByUuidAndCompanyId(uuid, companyId);
	}

	/**
	 * Returns a range of all the contact roles.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.osb.koroneiki.taproot.model.impl.ContactRoleModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of contact roles
	 * @param end the upper bound of the range of contact roles (not inclusive)
	 * @return the range of contact roles
	 */
	public static java.util.List
		<com.liferay.osb.koroneiki.taproot.model.ContactRole> getContactRoles(
			int start, int end) {

		return getService().getContactRoles(start, end);
	}

	public static java.util.List
		<com.liferay.osb.koroneiki.taproot.model.ContactRole> getContactRoles(
			int type, int start, int end) {

		return getService().getContactRoles(type, start, end);
	}

	/**
	 * Returns the number of contact roles.
	 *
	 * @return the number of contact roles
	 */
	public static int getContactRolesCount() {
		return getService().getContactRolesCount();
	}

	public static int getContactRolesCount(int type) {
		return getService().getContactRolesCount(type);
	}

	public static java.util.List
		<com.liferay.osb.koroneiki.taproot.model.ContactRole>
			getContactTeamContactRoles(long teamId, long contactId) {

		return getService().getContactTeamContactRoles(teamId, contactId);
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

	public static com.liferay.osb.koroneiki.taproot.model.ContactRole
		getMemberContactRole(int type) {

		return getService().getMemberContactRole(type);
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

	public static com.liferay.osb.koroneiki.taproot.model.ContactRole reindex(
			long contactRoleId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().reindex(contactRoleId);
	}

	public static com.liferay.portal.kernel.search.Hits search(
			long companyId, int type, String keywords, int start, int end,
			com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().search(companyId, type, keywords, start, end, sort);
	}

	/**
	 * Updates the contact role in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param contactRole the contact role
	 * @return the contact role that was updated
	 */
	public static com.liferay.osb.koroneiki.taproot.model.ContactRole
		updateContactRole(
			com.liferay.osb.koroneiki.taproot.model.ContactRole contactRole) {

		return getService().updateContactRole(contactRole);
	}

	public static com.liferay.osb.koroneiki.taproot.model.ContactRole
			updateContactRole(
				long contactRoleId, String name, String description)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateContactRole(contactRoleId, name, description);
	}

	public static ContactRoleLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<ContactRoleLocalService, ContactRoleLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(ContactRoleLocalService.class);

		ServiceTracker<ContactRoleLocalService, ContactRoleLocalService>
			serviceTracker =
				new ServiceTracker
					<ContactRoleLocalService, ContactRoleLocalService>(
						bundle.getBundleContext(),
						ContactRoleLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}