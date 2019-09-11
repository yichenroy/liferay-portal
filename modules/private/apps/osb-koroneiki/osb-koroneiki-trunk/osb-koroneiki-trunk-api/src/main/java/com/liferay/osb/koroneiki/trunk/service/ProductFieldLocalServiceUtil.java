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

package com.liferay.osb.koroneiki.trunk.service;

import org.osgi.annotation.versioning.ProviderType;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for ProductField. This utility wraps
 * <code>com.liferay.osb.koroneiki.trunk.service.impl.ProductFieldLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see ProductFieldLocalService
 * @generated
 */
@ProviderType
public class ProductFieldLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.osb.koroneiki.trunk.service.impl.ProductFieldLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.osb.koroneiki.trunk.model.ProductField
			addProductField(
				long userId, long classNameId, long classPK, String name,
				String value)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addProductField(
			userId, classNameId, classPK, name, value);
	}

	public static com.liferay.osb.koroneiki.trunk.model.ProductField
			addProductField(
				long userId, String className, long classPK, String name,
				String value)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addProductField(
			userId, className, classPK, name, value);
	}

	/**
	 * Adds the product field to the database. Also notifies the appropriate model listeners.
	 *
	 * @param productField the product field
	 * @return the product field that was added
	 */
	public static com.liferay.osb.koroneiki.trunk.model.ProductField
		addProductField(
			com.liferay.osb.koroneiki.trunk.model.ProductField productField) {

		return getService().addProductField(productField);
	}

	/**
	 * Creates a new product field with the primary key. Does not add the product field to the database.
	 *
	 * @param productFieldId the primary key for the new product field
	 * @return the new product field
	 */
	public static com.liferay.osb.koroneiki.trunk.model.ProductField
		createProductField(long productFieldId) {

		return getService().createProductField(productFieldId);
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
	 * Deletes the product field with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param productFieldId the primary key of the product field
	 * @return the product field that was removed
	 * @throws PortalException if a product field with the primary key could not be found
	 */
	public static com.liferay.osb.koroneiki.trunk.model.ProductField
			deleteProductField(long productFieldId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteProductField(productFieldId);
	}

	/**
	 * Deletes the product field from the database. Also notifies the appropriate model listeners.
	 *
	 * @param productField the product field
	 * @return the product field that was removed
	 */
	public static com.liferay.osb.koroneiki.trunk.model.ProductField
		deleteProductField(
			com.liferay.osb.koroneiki.trunk.model.ProductField productField) {

		return getService().deleteProductField(productField);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.osb.koroneiki.trunk.model.impl.ProductFieldModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.osb.koroneiki.trunk.model.impl.ProductFieldModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.osb.koroneiki.trunk.model.ProductField
		fetchProductField(long productFieldId) {

		return getService().fetchProductField(productFieldId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
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
	 * Returns the product field with the primary key.
	 *
	 * @param productFieldId the primary key of the product field
	 * @return the product field
	 * @throws PortalException if a product field with the primary key could not be found
	 */
	public static com.liferay.osb.koroneiki.trunk.model.ProductField
			getProductField(long productFieldId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getProductField(productFieldId);
	}

	public static java.util.List<String> getProductFieldNames(
		long classNameId) {

		return getService().getProductFieldNames(classNameId);
	}

	/**
	 * Returns a range of all the product fields.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.osb.koroneiki.trunk.model.impl.ProductFieldModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of product fields
	 * @param end the upper bound of the range of product fields (not inclusive)
	 * @return the range of product fields
	 */
	public static java.util.List
		<com.liferay.osb.koroneiki.trunk.model.ProductField> getProductFields(
			int start, int end) {

		return getService().getProductFields(start, end);
	}

	public static java.util.List
		<com.liferay.osb.koroneiki.trunk.model.ProductField> getProductFields(
			long classNameId, long classPK) {

		return getService().getProductFields(classNameId, classPK);
	}

	public static java.util.List
		<com.liferay.osb.koroneiki.trunk.model.ProductField> getProductFields(
			String className, long classPK) {

		return getService().getProductFields(className, classPK);
	}

	/**
	 * Returns the number of product fields.
	 *
	 * @return the number of product fields
	 */
	public static int getProductFieldsCount() {
		return getService().getProductFieldsCount();
	}

	public static com.liferay.osb.koroneiki.trunk.model.ProductField
			updateProductField(long productFieldId, String value)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateProductField(productFieldId, value);
	}

	/**
	 * Updates the product field in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param productField the product field
	 * @return the product field that was updated
	 */
	public static com.liferay.osb.koroneiki.trunk.model.ProductField
		updateProductField(
			com.liferay.osb.koroneiki.trunk.model.ProductField productField) {

		return getService().updateProductField(productField);
	}

	public static ProductFieldLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<ProductFieldLocalService, ProductFieldLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(ProductFieldLocalService.class);

		ServiceTracker<ProductFieldLocalService, ProductFieldLocalService>
			serviceTracker =
				new ServiceTracker
					<ProductFieldLocalService, ProductFieldLocalService>(
						bundle.getBundleContext(),
						ProductFieldLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}