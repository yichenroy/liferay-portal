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

package com.liferay.commerce.cloud.client.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for CommerceCloudForecastOrder. This utility wraps
 * {@link com.liferay.commerce.cloud.client.service.impl.CommerceCloudForecastOrderLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Andrea Di Giorgi
 * @see CommerceCloudForecastOrderLocalService
 * @see com.liferay.commerce.cloud.client.service.base.CommerceCloudForecastOrderLocalServiceBaseImpl
 * @see com.liferay.commerce.cloud.client.service.impl.CommerceCloudForecastOrderLocalServiceImpl
 * @generated
 */
@ProviderType
public class CommerceCloudForecastOrderLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.cloud.client.service.impl.CommerceCloudForecastOrderLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the commerce cloud forecast order to the database. Also notifies the appropriate model listeners.
	*
	* @param commerceCloudForecastOrder the commerce cloud forecast order
	* @return the commerce cloud forecast order that was added
	*/
	public static com.liferay.commerce.cloud.client.model.CommerceCloudForecastOrder addCommerceCloudForecastOrder(
		com.liferay.commerce.cloud.client.model.CommerceCloudForecastOrder commerceCloudForecastOrder) {
		return getService()
				   .addCommerceCloudForecastOrder(commerceCloudForecastOrder);
	}

	public static com.liferay.commerce.cloud.client.model.CommerceCloudForecastOrder addCommerceCloudForecastOrder(
		long commerceOrderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().addCommerceCloudForecastOrder(commerceOrderId);
	}

	public static void checkCommerceCloudForecastOrders() {
		getService().checkCommerceCloudForecastOrders();
	}

	/**
	* Creates a new commerce cloud forecast order with the primary key. Does not add the commerce cloud forecast order to the database.
	*
	* @param commerceCloudForecastOrderId the primary key for the new commerce cloud forecast order
	* @return the new commerce cloud forecast order
	*/
	public static com.liferay.commerce.cloud.client.model.CommerceCloudForecastOrder createCommerceCloudForecastOrder(
		long commerceCloudForecastOrderId) {
		return getService()
				   .createCommerceCloudForecastOrder(commerceCloudForecastOrderId);
	}

	/**
	* Deletes the commerce cloud forecast order from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceCloudForecastOrder the commerce cloud forecast order
	* @return the commerce cloud forecast order that was removed
	*/
	public static com.liferay.commerce.cloud.client.model.CommerceCloudForecastOrder deleteCommerceCloudForecastOrder(
		com.liferay.commerce.cloud.client.model.CommerceCloudForecastOrder commerceCloudForecastOrder) {
		return getService()
				   .deleteCommerceCloudForecastOrder(commerceCloudForecastOrder);
	}

	/**
	* Deletes the commerce cloud forecast order with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceCloudForecastOrderId the primary key of the commerce cloud forecast order
	* @return the commerce cloud forecast order that was removed
	* @throws PortalException if a commerce cloud forecast order with the primary key could not be found
	*/
	public static com.liferay.commerce.cloud.client.model.CommerceCloudForecastOrder deleteCommerceCloudForecastOrder(
		long commerceCloudForecastOrderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .deleteCommerceCloudForecastOrder(commerceCloudForecastOrderId);
	}

	public static com.liferay.commerce.cloud.client.model.CommerceCloudForecastOrder deleteCommerceCloudForecastOrderByCommerceOrderId(
		long commerceOrderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .deleteCommerceCloudForecastOrderByCommerceOrderId(commerceOrderId);
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.cloud.client.model.impl.CommerceCloudForecastOrderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.cloud.client.model.impl.CommerceCloudForecastOrderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
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

	public static com.liferay.commerce.cloud.client.model.CommerceCloudForecastOrder fetchCommerceCloudForecastOrder(
		long commerceCloudForecastOrderId) {
		return getService()
				   .fetchCommerceCloudForecastOrder(commerceCloudForecastOrderId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	/**
	* Returns the commerce cloud forecast order with the primary key.
	*
	* @param commerceCloudForecastOrderId the primary key of the commerce cloud forecast order
	* @return the commerce cloud forecast order
	* @throws PortalException if a commerce cloud forecast order with the primary key could not be found
	*/
	public static com.liferay.commerce.cloud.client.model.CommerceCloudForecastOrder getCommerceCloudForecastOrder(
		long commerceCloudForecastOrderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCommerceCloudForecastOrder(commerceCloudForecastOrderId);
	}

	/**
	* Returns a range of all the commerce cloud forecast orders.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.cloud.client.model.impl.CommerceCloudForecastOrderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce cloud forecast orders
	* @param end the upper bound of the range of commerce cloud forecast orders (not inclusive)
	* @return the range of commerce cloud forecast orders
	*/
	public static java.util.List<com.liferay.commerce.cloud.client.model.CommerceCloudForecastOrder> getCommerceCloudForecastOrders(
		int start, int end) {
		return getService().getCommerceCloudForecastOrders(start, end);
	}

	/**
	* Returns the number of commerce cloud forecast orders.
	*
	* @return the number of commerce cloud forecast orders
	*/
	public static int getCommerceCloudForecastOrdersCount() {
		return getService().getCommerceCloudForecastOrdersCount();
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
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

	public static com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Updates the commerce cloud forecast order in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commerceCloudForecastOrder the commerce cloud forecast order
	* @return the commerce cloud forecast order that was updated
	*/
	public static com.liferay.commerce.cloud.client.model.CommerceCloudForecastOrder updateCommerceCloudForecastOrder(
		com.liferay.commerce.cloud.client.model.CommerceCloudForecastOrder commerceCloudForecastOrder) {
		return getService()
				   .updateCommerceCloudForecastOrder(commerceCloudForecastOrder);
	}

	public static CommerceCloudForecastOrderLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceCloudForecastOrderLocalService, CommerceCloudForecastOrderLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommerceCloudForecastOrderLocalService.class);

		ServiceTracker<CommerceCloudForecastOrderLocalService, CommerceCloudForecastOrderLocalService> serviceTracker =
			new ServiceTracker<CommerceCloudForecastOrderLocalService, CommerceCloudForecastOrderLocalService>(bundle.getBundleContext(),
				CommerceCloudForecastOrderLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}