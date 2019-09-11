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

package com.liferay.osb.koroneiki.scion.service;

import org.osgi.annotation.versioning.ProviderType;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for ServiceProducer. This utility wraps
 * <code>com.liferay.osb.koroneiki.scion.service.impl.ServiceProducerLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see ServiceProducerLocalService
 * @generated
 */
@ProviderType
public class ServiceProducerLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.osb.koroneiki.scion.service.impl.ServiceProducerLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.osb.koroneiki.scion.model.ServiceProducer
			addServiceProducer(long userId, String name, String description)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addServiceProducer(userId, name, description);
	}

	/**
	 * Adds the service producer to the database. Also notifies the appropriate model listeners.
	 *
	 * @param serviceProducer the service producer
	 * @return the service producer that was added
	 */
	public static com.liferay.osb.koroneiki.scion.model.ServiceProducer
		addServiceProducer(
			com.liferay.osb.koroneiki.scion.model.ServiceProducer
				serviceProducer) {

		return getService().addServiceProducer(serviceProducer);
	}

	/**
	 * Creates a new service producer with the primary key. Does not add the service producer to the database.
	 *
	 * @param serviceProducerId the primary key for the new service producer
	 * @return the new service producer
	 */
	public static com.liferay.osb.koroneiki.scion.model.ServiceProducer
		createServiceProducer(long serviceProducerId) {

		return getService().createServiceProducer(serviceProducerId);
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

	/**
	 * Deletes the service producer with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param serviceProducerId the primary key of the service producer
	 * @return the service producer that was removed
	 * @throws PortalException if a service producer with the primary key could not be found
	 */
	public static com.liferay.osb.koroneiki.scion.model.ServiceProducer
			deleteServiceProducer(long serviceProducerId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteServiceProducer(serviceProducerId);
	}

	/**
	 * Deletes the service producer from the database. Also notifies the appropriate model listeners.
	 *
	 * @param serviceProducer the service producer
	 * @return the service producer that was removed
	 */
	public static com.liferay.osb.koroneiki.scion.model.ServiceProducer
		deleteServiceProducer(
			com.liferay.osb.koroneiki.scion.model.ServiceProducer
				serviceProducer) {

		return getService().deleteServiceProducer(serviceProducer);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.osb.koroneiki.scion.model.impl.ServiceProducerModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.osb.koroneiki.scion.model.impl.ServiceProducerModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.osb.koroneiki.scion.model.ServiceProducer
		fetchAuthorizedServiceProducer(long authorizationUserId) {

		return getService().fetchAuthorizedServiceProducer(authorizationUserId);
	}

	public static com.liferay.osb.koroneiki.scion.model.ServiceProducer
		fetchServiceProducer(long serviceProducerId) {

		return getService().fetchServiceProducer(serviceProducerId);
	}

	/**
	 * Returns the service producer with the matching UUID and company.
	 *
	 * @param uuid the service producer's UUID
	 * @param companyId the primary key of the company
	 * @return the matching service producer, or <code>null</code> if a matching service producer could not be found
	 */
	public static com.liferay.osb.koroneiki.scion.model.ServiceProducer
		fetchServiceProducerByUuidAndCompanyId(String uuid, long companyId) {

		return getService().fetchServiceProducerByUuidAndCompanyId(
			uuid, companyId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.osb.koroneiki.scion.model.ServiceProducer
			getAuthorizedServiceProducer(long authorizationUserId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getAuthorizedServiceProducer(authorizationUserId);
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
	 * Returns the service producer with the primary key.
	 *
	 * @param serviceProducerId the primary key of the service producer
	 * @return the service producer
	 * @throws PortalException if a service producer with the primary key could not be found
	 */
	public static com.liferay.osb.koroneiki.scion.model.ServiceProducer
			getServiceProducer(long serviceProducerId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getServiceProducer(serviceProducerId);
	}

	/**
	 * Returns the service producer with the matching UUID and company.
	 *
	 * @param uuid the service producer's UUID
	 * @param companyId the primary key of the company
	 * @return the matching service producer
	 * @throws PortalException if a matching service producer could not be found
	 */
	public static com.liferay.osb.koroneiki.scion.model.ServiceProducer
			getServiceProducerByUuidAndCompanyId(String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getServiceProducerByUuidAndCompanyId(
			uuid, companyId);
	}

	/**
	 * Returns a range of all the service producers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.osb.koroneiki.scion.model.impl.ServiceProducerModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of service producers
	 * @param end the upper bound of the range of service producers (not inclusive)
	 * @return the range of service producers
	 */
	public static java.util.List
		<com.liferay.osb.koroneiki.scion.model.ServiceProducer>
			getServiceProducers(int start, int end) {

		return getService().getServiceProducers(start, end);
	}

	/**
	 * Returns the number of service producers.
	 *
	 * @return the number of service producers
	 */
	public static int getServiceProducersCount() {
		return getService().getServiceProducersCount();
	}

	public static com.liferay.osb.koroneiki.scion.model.ServiceProducer
			updateServiceProducer(
				long serviceProducerId, String name, String description)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateServiceProducer(
			serviceProducerId, name, description);
	}

	/**
	 * Updates the service producer in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param serviceProducer the service producer
	 * @return the service producer that was updated
	 */
	public static com.liferay.osb.koroneiki.scion.model.ServiceProducer
		updateServiceProducer(
			com.liferay.osb.koroneiki.scion.model.ServiceProducer
				serviceProducer) {

		return getService().updateServiceProducer(serviceProducer);
	}

	public static ServiceProducerLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<ServiceProducerLocalService, ServiceProducerLocalService>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			ServiceProducerLocalService.class);

		ServiceTracker<ServiceProducerLocalService, ServiceProducerLocalService>
			serviceTracker =
				new ServiceTracker
					<ServiceProducerLocalService, ServiceProducerLocalService>(
						bundle.getBundleContext(),
						ServiceProducerLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}