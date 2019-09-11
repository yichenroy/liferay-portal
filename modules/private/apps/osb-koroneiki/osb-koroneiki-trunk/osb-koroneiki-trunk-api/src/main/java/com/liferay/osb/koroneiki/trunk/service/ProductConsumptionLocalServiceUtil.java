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
 * Provides the local service utility for ProductConsumption. This utility wraps
 * <code>com.liferay.osb.koroneiki.trunk.service.impl.ProductConsumptionLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see ProductConsumptionLocalService
 * @generated
 */
@ProviderType
public class ProductConsumptionLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.osb.koroneiki.trunk.service.impl.ProductConsumptionLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.osb.koroneiki.trunk.model.ProductConsumption
			addProductConsumption(
				long userId, long accountId, long productEntryId,
				java.util.List
					<com.liferay.osb.koroneiki.trunk.model.ProductField>
						productFields)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addProductConsumption(
			userId, accountId, productEntryId, productFields);
	}

	/**
	 * Adds the product consumption to the database. Also notifies the appropriate model listeners.
	 *
	 * @param productConsumption the product consumption
	 * @return the product consumption that was added
	 */
	public static com.liferay.osb.koroneiki.trunk.model.ProductConsumption
		addProductConsumption(
			com.liferay.osb.koroneiki.trunk.model.ProductConsumption
				productConsumption) {

		return getService().addProductConsumption(productConsumption);
	}

	/**
	 * Creates a new product consumption with the primary key. Does not add the product consumption to the database.
	 *
	 * @param productConsumptionId the primary key for the new product consumption
	 * @return the new product consumption
	 */
	public static com.liferay.osb.koroneiki.trunk.model.ProductConsumption
		createProductConsumption(long productConsumptionId) {

		return getService().createProductConsumption(productConsumptionId);
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
	 * Deletes the product consumption with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param productConsumptionId the primary key of the product consumption
	 * @return the product consumption that was removed
	 * @throws PortalException if a product consumption with the primary key could not be found
	 */
	public static com.liferay.osb.koroneiki.trunk.model.ProductConsumption
			deleteProductConsumption(long productConsumptionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteProductConsumption(productConsumptionId);
	}

	public static com.liferay.osb.koroneiki.trunk.model.ProductConsumption
			deleteProductConsumption(
				long userId, long accountId, long productEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteProductConsumption(
			userId, accountId, productEntryId);
	}

	/**
	 * Deletes the product consumption from the database. Also notifies the appropriate model listeners.
	 *
	 * @param productConsumption the product consumption
	 * @return the product consumption that was removed
	 * @throws PortalException
	 */
	public static com.liferay.osb.koroneiki.trunk.model.ProductConsumption
			deleteProductConsumption(
				com.liferay.osb.koroneiki.trunk.model.ProductConsumption
					productConsumption)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteProductConsumption(productConsumption);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.osb.koroneiki.trunk.model.impl.ProductConsumptionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.osb.koroneiki.trunk.model.impl.ProductConsumptionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.osb.koroneiki.trunk.model.ProductConsumption
		fetchProductConsumption(long productConsumptionId) {

		return getService().fetchProductConsumption(productConsumptionId);
	}

	/**
	 * Returns the product consumption with the matching UUID and company.
	 *
	 * @param uuid the product consumption's UUID
	 * @param companyId the primary key of the company
	 * @return the matching product consumption, or <code>null</code> if a matching product consumption could not be found
	 */
	public static com.liferay.osb.koroneiki.trunk.model.ProductConsumption
		fetchProductConsumptionByUuidAndCompanyId(String uuid, long companyId) {

		return getService().fetchProductConsumptionByUuidAndCompanyId(
			uuid, companyId);
	}

	public static java.util.List
		<com.liferay.osb.koroneiki.trunk.model.ProductConsumption>
				getAccountProductConsumptions(
					long accountId, int start, int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getAccountProductConsumptions(
			accountId, start, end);
	}

	public static int getAccountProductConsumptionsCount(long accountId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getAccountProductConsumptionsCount(accountId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
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

	/**
	 * Returns the product consumption with the primary key.
	 *
	 * @param productConsumptionId the primary key of the product consumption
	 * @return the product consumption
	 * @throws PortalException if a product consumption with the primary key could not be found
	 */
	public static com.liferay.osb.koroneiki.trunk.model.ProductConsumption
			getProductConsumption(long productConsumptionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getProductConsumption(productConsumptionId);
	}

	public static com.liferay.osb.koroneiki.trunk.model.ProductConsumption
			getProductConsumption(String productConsumptionKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getProductConsumption(productConsumptionKey);
	}

	/**
	 * Returns the product consumption with the matching UUID and company.
	 *
	 * @param uuid the product consumption's UUID
	 * @param companyId the primary key of the company
	 * @return the matching product consumption
	 * @throws PortalException if a matching product consumption could not be found
	 */
	public static com.liferay.osb.koroneiki.trunk.model.ProductConsumption
			getProductConsumptionByUuidAndCompanyId(String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getProductConsumptionByUuidAndCompanyId(
			uuid, companyId);
	}

	/**
	 * Returns a range of all the product consumptions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.osb.koroneiki.trunk.model.impl.ProductConsumptionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of product consumptions
	 * @param end the upper bound of the range of product consumptions (not inclusive)
	 * @return the range of product consumptions
	 */
	public static java.util.List
		<com.liferay.osb.koroneiki.trunk.model.ProductConsumption>
			getProductConsumptions(int start, int end) {

		return getService().getProductConsumptions(start, end);
	}

	public static java.util.List
		<com.liferay.osb.koroneiki.trunk.model.ProductConsumption>
				getProductConsumptions(
					long userId, long accountId, long productEntryId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getProductConsumptions(
			userId, accountId, productEntryId);
	}

	/**
	 * Returns the number of product consumptions.
	 *
	 * @return the number of product consumptions
	 */
	public static int getProductConsumptionsCount() {
		return getService().getProductConsumptionsCount();
	}

	public static com.liferay.osb.koroneiki.trunk.model.ProductConsumption
			reindex(long productConsumptionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().reindex(productConsumptionId);
	}

	public static com.liferay.portal.kernel.search.Hits search(
			long companyId, String keywords, int start, int end,
			com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().search(companyId, keywords, start, end, sort);
	}

	/**
	 * Updates the product consumption in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param productConsumption the product consumption
	 * @return the product consumption that was updated
	 */
	public static com.liferay.osb.koroneiki.trunk.model.ProductConsumption
		updateProductConsumption(
			com.liferay.osb.koroneiki.trunk.model.ProductConsumption
				productConsumption) {

		return getService().updateProductConsumption(productConsumption);
	}

	public static ProductConsumptionLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<ProductConsumptionLocalService, ProductConsumptionLocalService>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			ProductConsumptionLocalService.class);

		ServiceTracker
			<ProductConsumptionLocalService, ProductConsumptionLocalService>
				serviceTracker =
					new ServiceTracker
						<ProductConsumptionLocalService,
						 ProductConsumptionLocalService>(
							 bundle.getBundleContext(),
							 ProductConsumptionLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}