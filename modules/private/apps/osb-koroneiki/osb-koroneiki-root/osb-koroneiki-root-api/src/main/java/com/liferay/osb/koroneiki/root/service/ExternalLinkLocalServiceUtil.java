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

package com.liferay.osb.koroneiki.root.service;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for ExternalLink. This utility wraps
 * <code>com.liferay.osb.koroneiki.root.service.impl.ExternalLinkLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see ExternalLinkLocalService
 * @generated
 */
public class ExternalLinkLocalServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.osb.koroneiki.root.service.impl.ExternalLinkLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the external link to the database. Also notifies the appropriate model listeners.
	 *
	 * @param externalLink the external link
	 * @return the external link that was added
	 */
	public static com.liferay.osb.koroneiki.root.model.ExternalLink
		addExternalLink(
			com.liferay.osb.koroneiki.root.model.ExternalLink externalLink) {

		return getService().addExternalLink(externalLink);
	}

	public static com.liferay.osb.koroneiki.root.model.ExternalLink
			addExternalLink(
				long userId, long classNameId, long classPK, String domain,
				String entityName, String entityId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addExternalLink(
			userId, classNameId, classPK, domain, entityName, entityId);
	}

	public static com.liferay.osb.koroneiki.root.model.ExternalLink
			addExternalLink(
				long userId, String className, long classPK, String domain,
				String entityName, String entityId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addExternalLink(
			userId, className, classPK, domain, entityName, entityId);
	}

	/**
	 * Creates a new external link with the primary key. Does not add the external link to the database.
	 *
	 * @param externalLinkId the primary key for the new external link
	 * @return the new external link
	 */
	public static com.liferay.osb.koroneiki.root.model.ExternalLink
		createExternalLink(long externalLinkId) {

		return getService().createExternalLink(externalLinkId);
	}

	/**
	 * Deletes the external link from the database. Also notifies the appropriate model listeners.
	 *
	 * @param externalLink the external link
	 * @return the external link that was removed
	 */
	public static com.liferay.osb.koroneiki.root.model.ExternalLink
		deleteExternalLink(
			com.liferay.osb.koroneiki.root.model.ExternalLink externalLink) {

		return getService().deleteExternalLink(externalLink);
	}

	/**
	 * Deletes the external link with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param externalLinkId the primary key of the external link
	 * @return the external link that was removed
	 * @throws PortalException if a external link with the primary key could not be found
	 */
	public static com.liferay.osb.koroneiki.root.model.ExternalLink
			deleteExternalLink(long externalLinkId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteExternalLink(externalLinkId);
	}

	public static void deleteExternalLinks(long classNameId, long classPK) {
		getService().deleteExternalLinks(classNameId, classPK);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.osb.koroneiki.root.model.impl.ExternalLinkModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.osb.koroneiki.root.model.impl.ExternalLinkModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.osb.koroneiki.root.model.ExternalLink
		fetchExternalLink(long externalLinkId) {

		return getService().fetchExternalLink(externalLinkId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns the external link with the primary key.
	 *
	 * @param externalLinkId the primary key of the external link
	 * @return the external link
	 * @throws PortalException if a external link with the primary key could not be found
	 */
	public static com.liferay.osb.koroneiki.root.model.ExternalLink
			getExternalLink(long externalLinkId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getExternalLink(externalLinkId);
	}

	public static com.liferay.osb.koroneiki.root.model.ExternalLink
			getExternalLink(String externalLinkKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getExternalLink(externalLinkKey);
	}

	/**
	 * Returns a range of all the external links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.osb.koroneiki.root.model.impl.ExternalLinkModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of external links
	 * @param end the upper bound of the range of external links (not inclusive)
	 * @return the range of external links
	 */
	public static java.util.List
		<com.liferay.osb.koroneiki.root.model.ExternalLink> getExternalLinks(
			int start, int end) {

		return getService().getExternalLinks(start, end);
	}

	public static java.util.List
		<com.liferay.osb.koroneiki.root.model.ExternalLink> getExternalLinks(
			long classNameId, long classPK, int start, int end) {

		return getService().getExternalLinks(classNameId, classPK, start, end);
	}

	public static java.util.List
		<com.liferay.osb.koroneiki.root.model.ExternalLink> getExternalLinks(
				long classNameId, String domain, String entityName,
				String entityId, int start, int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getExternalLinks(
			classNameId, domain, entityName, entityId, start, end);
	}

	public static java.util.List
		<com.liferay.osb.koroneiki.root.model.ExternalLink> getExternalLinks(
			String className, long classPK, int start, int end) {

		return getService().getExternalLinks(className, classPK, start, end);
	}

	/**
	 * Returns the number of external links.
	 *
	 * @return the number of external links
	 */
	public static int getExternalLinksCount() {
		return getService().getExternalLinksCount();
	}

	public static int getExternalLinksCount(long classNameId, long classPK) {
		return getService().getExternalLinksCount(classNameId, classPK);
	}

	public static int getExternalLinksCount(
			long classNameId, String domain, String entityName, String entityId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getExternalLinksCount(
			classNameId, domain, entityName, entityId);
	}

	public static int getExternalLinksCount(String className, long classPK) {
		return getService().getExternalLinksCount(className, classPK);
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
	 * Updates the external link in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param externalLink the external link
	 * @return the external link that was updated
	 */
	public static com.liferay.osb.koroneiki.root.model.ExternalLink
		updateExternalLink(
			com.liferay.osb.koroneiki.root.model.ExternalLink externalLink) {

		return getService().updateExternalLink(externalLink);
	}

	public static com.liferay.osb.koroneiki.root.model.ExternalLink
			updateExternalLink(long externalLinkId, String entityId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateExternalLink(externalLinkId, entityId);
	}

	public static ExternalLinkLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<ExternalLinkLocalService, ExternalLinkLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(ExternalLinkLocalService.class);

		ServiceTracker<ExternalLinkLocalService, ExternalLinkLocalService>
			serviceTracker =
				new ServiceTracker
					<ExternalLinkLocalService, ExternalLinkLocalService>(
						bundle.getBundleContext(),
						ExternalLinkLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}