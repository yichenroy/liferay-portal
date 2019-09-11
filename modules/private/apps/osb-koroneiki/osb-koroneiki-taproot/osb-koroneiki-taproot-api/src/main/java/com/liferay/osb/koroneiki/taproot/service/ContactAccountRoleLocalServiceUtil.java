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
 * Provides the local service utility for ContactAccountRole. This utility wraps
 * <code>com.liferay.osb.koroneiki.taproot.service.impl.ContactAccountRoleLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see ContactAccountRoleLocalService
 * @generated
 */
public class ContactAccountRoleLocalServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.osb.koroneiki.taproot.service.impl.ContactAccountRoleLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the contact account role to the database. Also notifies the appropriate model listeners.
	 *
	 * @param contactAccountRole the contact account role
	 * @return the contact account role that was added
	 */
	public static com.liferay.osb.koroneiki.taproot.model.ContactAccountRole
		addContactAccountRole(
			com.liferay.osb.koroneiki.taproot.model.ContactAccountRole
				contactAccountRole) {

		return getService().addContactAccountRole(contactAccountRole);
	}

	public static com.liferay.osb.koroneiki.taproot.model.ContactAccountRole
			addContactAccountRole(
				long contactId, long accountId, long contactRoleId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addContactAccountRole(
			contactId, accountId, contactRoleId);
	}

	/**
	 * Creates a new contact account role with the primary key. Does not add the contact account role to the database.
	 *
	 * @param contactAccountRolePK the primary key for the new contact account role
	 * @return the new contact account role
	 */
	public static com.liferay.osb.koroneiki.taproot.model.ContactAccountRole
		createContactAccountRole(
			com.liferay.osb.koroneiki.taproot.service.persistence.
				ContactAccountRolePK contactAccountRolePK) {

		return getService().createContactAccountRole(contactAccountRolePK);
	}

	/**
	 * Deletes the contact account role from the database. Also notifies the appropriate model listeners.
	 *
	 * @param contactAccountRole the contact account role
	 * @return the contact account role that was removed
	 */
	public static com.liferay.osb.koroneiki.taproot.model.ContactAccountRole
		deleteContactAccountRole(
			com.liferay.osb.koroneiki.taproot.model.ContactAccountRole
				contactAccountRole) {

		return getService().deleteContactAccountRole(contactAccountRole);
	}

	/**
	 * Deletes the contact account role with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param contactAccountRolePK the primary key of the contact account role
	 * @return the contact account role that was removed
	 * @throws PortalException if a contact account role with the primary key could not be found
	 */
	public static com.liferay.osb.koroneiki.taproot.model.ContactAccountRole
			deleteContactAccountRole(
				com.liferay.osb.koroneiki.taproot.service.persistence.
					ContactAccountRolePK contactAccountRolePK)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteContactAccountRole(contactAccountRolePK);
	}

	public static com.liferay.osb.koroneiki.taproot.model.ContactAccountRole
			deleteContactAccountRole(
				long contactId, long accountId, long contactRoleId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteContactAccountRole(
			contactId, accountId, contactRoleId);
	}

	public static void deleteContactAccountRoles(long contactId, long accountId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteContactAccountRoles(contactId, accountId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.osb.koroneiki.taproot.model.impl.ContactAccountRoleModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.osb.koroneiki.taproot.model.impl.ContactAccountRoleModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.osb.koroneiki.taproot.model.ContactAccountRole
		fetchContactAccountRole(
			com.liferay.osb.koroneiki.taproot.service.persistence.
				ContactAccountRolePK contactAccountRolePK) {

		return getService().fetchContactAccountRole(contactAccountRolePK);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns the contact account role with the primary key.
	 *
	 * @param contactAccountRolePK the primary key of the contact account role
	 * @return the contact account role
	 * @throws PortalException if a contact account role with the primary key could not be found
	 */
	public static com.liferay.osb.koroneiki.taproot.model.ContactAccountRole
			getContactAccountRole(
				com.liferay.osb.koroneiki.taproot.service.persistence.
					ContactAccountRolePK contactAccountRolePK)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getContactAccountRole(contactAccountRolePK);
	}

	/**
	 * Returns a range of all the contact account roles.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.osb.koroneiki.taproot.model.impl.ContactAccountRoleModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of contact account roles
	 * @param end the upper bound of the range of contact account roles (not inclusive)
	 * @return the range of contact account roles
	 */
	public static java.util.List
		<com.liferay.osb.koroneiki.taproot.model.ContactAccountRole>
			getContactAccountRoles(int start, int end) {

		return getService().getContactAccountRoles(start, end);
	}

	public static java.util.List
		<com.liferay.osb.koroneiki.taproot.model.ContactAccountRole>
			getContactAccountRoles(long contactId, long accountId) {

		return getService().getContactAccountRoles(contactId, accountId);
	}

	public static java.util.List
		<com.liferay.osb.koroneiki.taproot.model.ContactAccountRole>
			getContactAccountRolesByAccountId(long accountId) {

		return getService().getContactAccountRolesByAccountId(accountId);
	}

	/**
	 * Returns the number of contact account roles.
	 *
	 * @return the number of contact account roles
	 */
	public static int getContactAccountRolesCount() {
		return getService().getContactAccountRolesCount();
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

	/**
	 * Updates the contact account role in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param contactAccountRole the contact account role
	 * @return the contact account role that was updated
	 */
	public static com.liferay.osb.koroneiki.taproot.model.ContactAccountRole
		updateContactAccountRole(
			com.liferay.osb.koroneiki.taproot.model.ContactAccountRole
				contactAccountRole) {

		return getService().updateContactAccountRole(contactAccountRole);
	}

	public static ContactAccountRoleLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<ContactAccountRoleLocalService, ContactAccountRoleLocalService>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			ContactAccountRoleLocalService.class);

		ServiceTracker
			<ContactAccountRoleLocalService, ContactAccountRoleLocalService>
				serviceTracker =
					new ServiceTracker
						<ContactAccountRoleLocalService,
						 ContactAccountRoleLocalService>(
							 bundle.getBundleContext(),
							 ContactAccountRoleLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}