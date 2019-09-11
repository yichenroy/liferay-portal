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
 * Provides the local service utility for ContactTeamRole. This utility wraps
 * <code>com.liferay.osb.koroneiki.taproot.service.impl.ContactTeamRoleLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see ContactTeamRoleLocalService
 * @generated
 */
public class ContactTeamRoleLocalServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.osb.koroneiki.taproot.service.impl.ContactTeamRoleLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the contact team role to the database. Also notifies the appropriate model listeners.
	 *
	 * @param contactTeamRole the contact team role
	 * @return the contact team role that was added
	 */
	public static com.liferay.osb.koroneiki.taproot.model.ContactTeamRole
		addContactTeamRole(
			com.liferay.osb.koroneiki.taproot.model.ContactTeamRole
				contactTeamRole) {

		return getService().addContactTeamRole(contactTeamRole);
	}

	public static com.liferay.osb.koroneiki.taproot.model.ContactTeamRole
			addContactTeamRole(long contactId, long teamId, long contactRoleId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addContactTeamRole(
			contactId, teamId, contactRoleId);
	}

	/**
	 * Creates a new contact team role with the primary key. Does not add the contact team role to the database.
	 *
	 * @param contactTeamRolePK the primary key for the new contact team role
	 * @return the new contact team role
	 */
	public static com.liferay.osb.koroneiki.taproot.model.ContactTeamRole
		createContactTeamRole(
			com.liferay.osb.koroneiki.taproot.service.persistence.
				ContactTeamRolePK contactTeamRolePK) {

		return getService().createContactTeamRole(contactTeamRolePK);
	}

	/**
	 * Deletes the contact team role from the database. Also notifies the appropriate model listeners.
	 *
	 * @param contactTeamRole the contact team role
	 * @return the contact team role that was removed
	 */
	public static com.liferay.osb.koroneiki.taproot.model.ContactTeamRole
		deleteContactTeamRole(
			com.liferay.osb.koroneiki.taproot.model.ContactTeamRole
				contactTeamRole) {

		return getService().deleteContactTeamRole(contactTeamRole);
	}

	/**
	 * Deletes the contact team role with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param contactTeamRolePK the primary key of the contact team role
	 * @return the contact team role that was removed
	 * @throws PortalException if a contact team role with the primary key could not be found
	 */
	public static com.liferay.osb.koroneiki.taproot.model.ContactTeamRole
			deleteContactTeamRole(
				com.liferay.osb.koroneiki.taproot.service.persistence.
					ContactTeamRolePK contactTeamRolePK)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteContactTeamRole(contactTeamRolePK);
	}

	public static com.liferay.osb.koroneiki.taproot.model.ContactTeamRole
		deleteContactTeamRole(long contactId, long teamId, long contactRoleId) {

		return getService().deleteContactTeamRole(
			contactId, teamId, contactRoleId);
	}

	public static void deleteContactTeamRoles(long contactId, long teamId) {
		getService().deleteContactTeamRoles(contactId, teamId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.osb.koroneiki.taproot.model.impl.ContactTeamRoleModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.osb.koroneiki.taproot.model.impl.ContactTeamRoleModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.osb.koroneiki.taproot.model.ContactTeamRole
		fetchContactTeamRole(
			com.liferay.osb.koroneiki.taproot.service.persistence.
				ContactTeamRolePK contactTeamRolePK) {

		return getService().fetchContactTeamRole(contactTeamRolePK);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns the contact team role with the primary key.
	 *
	 * @param contactTeamRolePK the primary key of the contact team role
	 * @return the contact team role
	 * @throws PortalException if a contact team role with the primary key could not be found
	 */
	public static com.liferay.osb.koroneiki.taproot.model.ContactTeamRole
			getContactTeamRole(
				com.liferay.osb.koroneiki.taproot.service.persistence.
					ContactTeamRolePK contactTeamRolePK)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getContactTeamRole(contactTeamRolePK);
	}

	/**
	 * Returns a range of all the contact team roles.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.osb.koroneiki.taproot.model.impl.ContactTeamRoleModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of contact team roles
	 * @param end the upper bound of the range of contact team roles (not inclusive)
	 * @return the range of contact team roles
	 */
	public static java.util.List
		<com.liferay.osb.koroneiki.taproot.model.ContactTeamRole>
			getContactTeamRoles(int start, int end) {

		return getService().getContactTeamRoles(start, end);
	}

	public static java.util.List
		<com.liferay.osb.koroneiki.taproot.model.ContactTeamRole>
			getContactTeamRoles(long contactId) {

		return getService().getContactTeamRoles(contactId);
	}

	/**
	 * Returns the number of contact team roles.
	 *
	 * @return the number of contact team roles
	 */
	public static int getContactTeamRolesCount() {
		return getService().getContactTeamRolesCount();
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
	 * Updates the contact team role in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param contactTeamRole the contact team role
	 * @return the contact team role that was updated
	 */
	public static com.liferay.osb.koroneiki.taproot.model.ContactTeamRole
		updateContactTeamRole(
			com.liferay.osb.koroneiki.taproot.model.ContactTeamRole
				contactTeamRole) {

		return getService().updateContactTeamRole(contactTeamRole);
	}

	public static ContactTeamRoleLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<ContactTeamRoleLocalService, ContactTeamRoleLocalService>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			ContactTeamRoleLocalService.class);

		ServiceTracker<ContactTeamRoleLocalService, ContactTeamRoleLocalService>
			serviceTracker =
				new ServiceTracker
					<ContactTeamRoleLocalService, ContactTeamRoleLocalService>(
						bundle.getBundleContext(),
						ContactTeamRoleLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}