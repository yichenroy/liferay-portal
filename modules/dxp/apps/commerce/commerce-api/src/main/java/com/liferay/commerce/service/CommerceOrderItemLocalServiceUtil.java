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

package com.liferay.commerce.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for CommerceOrderItem. This utility wraps
 * {@link com.liferay.commerce.service.impl.CommerceOrderItemLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceOrderItemLocalService
 * @see com.liferay.commerce.service.base.CommerceOrderItemLocalServiceBaseImpl
 * @see com.liferay.commerce.service.impl.CommerceOrderItemLocalServiceImpl
 * @generated
 */
@ProviderType
public class CommerceOrderItemLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.service.impl.CommerceOrderItemLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the commerce order item to the database. Also notifies the appropriate model listeners.
	*
	* @param commerceOrderItem the commerce order item
	* @return the commerce order item that was added
	*/
	public static com.liferay.commerce.model.CommerceOrderItem addCommerceOrderItem(
		com.liferay.commerce.model.CommerceOrderItem commerceOrderItem) {
		return getService().addCommerceOrderItem(commerceOrderItem);
	}

	public static com.liferay.commerce.model.CommerceOrderItem addCommerceOrderItem(
		long commerceOrderId, long cpInstanceId, int quantity,
		int shippedQuantity, String json, java.math.BigDecimal price,
		com.liferay.commerce.context.CommerceContext commerceContext,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCommerceOrderItem(commerceOrderId, cpInstanceId,
			quantity, shippedQuantity, json, price, commerceContext,
			serviceContext);
	}

	/**
	* Creates a new commerce order item with the primary key. Does not add the commerce order item to the database.
	*
	* @param commerceOrderItemId the primary key for the new commerce order item
	* @return the new commerce order item
	*/
	public static com.liferay.commerce.model.CommerceOrderItem createCommerceOrderItem(
		long commerceOrderItemId) {
		return getService().createCommerceOrderItem(commerceOrderItemId);
	}

	/**
	* Deletes the commerce order item from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceOrderItem the commerce order item
	* @return the commerce order item that was removed
	* @throws PortalException
	*/
	public static com.liferay.commerce.model.CommerceOrderItem deleteCommerceOrderItem(
		com.liferay.commerce.model.CommerceOrderItem commerceOrderItem)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteCommerceOrderItem(commerceOrderItem);
	}

	/**
	* Deletes the commerce order item with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceOrderItemId the primary key of the commerce order item
	* @return the commerce order item that was removed
	* @throws PortalException if a commerce order item with the primary key could not be found
	*/
	public static com.liferay.commerce.model.CommerceOrderItem deleteCommerceOrderItem(
		long commerceOrderItemId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteCommerceOrderItem(commerceOrderItemId);
	}

	public static void deleteCommerceOrderItems(long commerceOrderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteCommerceOrderItems(commerceOrderId);
	}

	public static void deleteCommerceOrderItemsByCPInstanceId(long cpInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteCommerceOrderItemsByCPInstanceId(cpInstanceId);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceOrderItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceOrderItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.commerce.model.CommerceOrderItem fetchCommerceOrderItem(
		long commerceOrderItemId) {
		return getService().fetchCommerceOrderItem(commerceOrderItemId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	public static java.util.List<com.liferay.commerce.model.CommerceOrderItem> getAvailableForShipmentCommerceOrderItems(
		long commerceOrderId) {
		return getService()
				   .getAvailableForShipmentCommerceOrderItems(commerceOrderId);
	}

	/**
	* Returns the commerce order item with the primary key.
	*
	* @param commerceOrderItemId the primary key of the commerce order item
	* @return the commerce order item
	* @throws PortalException if a commerce order item with the primary key could not be found
	*/
	public static com.liferay.commerce.model.CommerceOrderItem getCommerceOrderItem(
		long commerceOrderItemId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCommerceOrderItem(commerceOrderItemId);
	}

	/**
	* Returns a range of all the commerce order items.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceOrderItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce order items
	* @param end the upper bound of the range of commerce order items (not inclusive)
	* @return the range of commerce order items
	*/
	public static java.util.List<com.liferay.commerce.model.CommerceOrderItem> getCommerceOrderItems(
		int start, int end) {
		return getService().getCommerceOrderItems(start, end);
	}

	public static java.util.List<com.liferay.commerce.model.CommerceOrderItem> getCommerceOrderItems(
		long commerceOrderId, int start, int end) {
		return getService().getCommerceOrderItems(commerceOrderId, start, end);
	}

	public static java.util.List<com.liferay.commerce.model.CommerceOrderItem> getCommerceOrderItems(
		long commerceOrderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceOrderItem> orderByComparator) {
		return getService()
				   .getCommerceOrderItems(commerceOrderId, start, end,
			orderByComparator);
	}

	/**
	* Returns the number of commerce order items.
	*
	* @return the number of commerce order items
	*/
	public static int getCommerceOrderItemsCount() {
		return getService().getCommerceOrderItemsCount();
	}

	public static int getCommerceOrderItemsCount(long commerceOrderId) {
		return getService().getCommerceOrderItemsCount(commerceOrderId);
	}

	public static int getCommerceOrderItemsQuantity(long commerceOrderId) {
		return getService().getCommerceOrderItemsQuantity(commerceOrderId);
	}

	public static int getCommerceWarehouseItemQuantity(
		long commerceOrderItemId, long commerceWarehouseId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCommerceWarehouseItemQuantity(commerceOrderItemId,
			commerceWarehouseId);
	}

	public static int getCPInstanceQuantity(long cpInstanceId, int orderStatus) {
		return getService().getCPInstanceQuantity(cpInstanceId, orderStatus);
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

	public static com.liferay.commerce.model.CommerceOrderItem incrementShippedQuantity(
		long commerceOrderItemId, int shippedQuantity)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .incrementShippedQuantity(commerceOrderItemId,
			shippedQuantity);
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.model.CommerceOrderItem> search(
		long commerceOrderId, String keywords, int start, int end,
		com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().search(commerceOrderId, keywords, start, end, sort);
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.model.CommerceOrderItem> search(
		long commerceOrderId, String sku, String name, boolean andOperator,
		int start, int end, com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .search(commerceOrderId, sku, name, andOperator, start, end,
			sort);
	}

	/**
	* Updates the commerce order item in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commerceOrderItem the commerce order item
	* @return the commerce order item that was updated
	*/
	public static com.liferay.commerce.model.CommerceOrderItem updateCommerceOrderItem(
		com.liferay.commerce.model.CommerceOrderItem commerceOrderItem) {
		return getService().updateCommerceOrderItem(commerceOrderItem);
	}

	public static com.liferay.commerce.model.CommerceOrderItem updateCommerceOrderItem(
		long commerceOrderItemId, int quantity)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCommerceOrderItem(commerceOrderItemId, quantity);
	}

	public static com.liferay.commerce.model.CommerceOrderItem updateCommerceOrderItem(
		long commerceOrderItemId, int quantity, String json,
		java.math.BigDecimal price)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCommerceOrderItem(commerceOrderItemId, quantity,
			json, price);
	}

	public static CommerceOrderItemLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceOrderItemLocalService, CommerceOrderItemLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommerceOrderItemLocalService.class);

		ServiceTracker<CommerceOrderItemLocalService, CommerceOrderItemLocalService> serviceTracker =
			new ServiceTracker<CommerceOrderItemLocalService, CommerceOrderItemLocalService>(bundle.getBundleContext(),
				CommerceOrderItemLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}