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
 * Provides the local service utility for ProductEntry. This utility wraps
 * <code>com.liferay.osb.koroneiki.trunk.service.impl.ProductEntryLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see ProductEntryLocalService
 * @generated
 */
@ProviderType
public class ProductEntryLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.osb.koroneiki.trunk.service.impl.ProductEntryLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.osb.koroneiki.trunk.model.ProductEntry
			addProductEntry(long userId, String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addProductEntry(userId, name);
	}

	/**
	 * Adds the product entry to the database. Also notifies the appropriate model listeners.
	 *
	 * @param productEntry the product entry
	 * @return the product entry that was added
	 */
	public static com.liferay.osb.koroneiki.trunk.model.ProductEntry
		addProductEntry(
			com.liferay.osb.koroneiki.trunk.model.ProductEntry productEntry) {

		return getService().addProductEntry(productEntry);
	}

	/**
	 * Creates a new product entry with the primary key. Does not add the product entry to the database.
	 *
	 * @param productEntryId the primary key for the new product entry
	 * @return the new product entry
	 */
	public static com.liferay.osb.koroneiki.trunk.model.ProductEntry
		createProductEntry(long productEntryId) {

		return getService().createProductEntry(productEntryId);
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
	 * Deletes the product entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param productEntryId the primary key of the product entry
	 * @return the product entry that was removed
	 * @throws PortalException if a product entry with the primary key could not be found
	 */
	public static com.liferay.osb.koroneiki.trunk.model.ProductEntry
			deleteProductEntry(long productEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteProductEntry(productEntryId);
	}

	/**
	 * Deletes the product entry from the database. Also notifies the appropriate model listeners.
	 *
	 * @param productEntry the product entry
	 * @return the product entry that was removed
	 * @throws PortalException
	 */
	public static com.liferay.osb.koroneiki.trunk.model.ProductEntry
			deleteProductEntry(
				com.liferay.osb.koroneiki.trunk.model.ProductEntry productEntry)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteProductEntry(productEntry);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.osb.koroneiki.trunk.model.impl.ProductEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.osb.koroneiki.trunk.model.impl.ProductEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.osb.koroneiki.trunk.model.ProductEntry
		fetchProductEntry(long productEntryId) {

		return getService().fetchProductEntry(productEntryId);
	}

	/**
	 * Returns the product entry with the matching UUID and company.
	 *
	 * @param uuid the product entry's UUID
	 * @param companyId the primary key of the company
	 * @return the matching product entry, or <code>null</code> if a matching product entry could not be found
	 */
	public static com.liferay.osb.koroneiki.trunk.model.ProductEntry
		fetchProductEntryByUuidAndCompanyId(String uuid, long companyId) {

		return getService().fetchProductEntryByUuidAndCompanyId(
			uuid, companyId);
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
	 * Returns a range of all the product entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.osb.koroneiki.trunk.model.impl.ProductEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of product entries
	 * @param end the upper bound of the range of product entries (not inclusive)
	 * @return the range of product entries
	 */
	public static java.util.List
		<com.liferay.osb.koroneiki.trunk.model.ProductEntry> getProductEntries(
			int start, int end) {

		return getService().getProductEntries(start, end);
	}

	/**
	 * Returns the number of product entries.
	 *
	 * @return the number of product entries
	 */
	public static int getProductEntriesCount() {
		return getService().getProductEntriesCount();
	}

	/**
	 * Returns the product entry with the primary key.
	 *
	 * @param productEntryId the primary key of the product entry
	 * @return the product entry
	 * @throws PortalException if a product entry with the primary key could not be found
	 */
	public static com.liferay.osb.koroneiki.trunk.model.ProductEntry
			getProductEntry(long productEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getProductEntry(productEntryId);
	}

	public static com.liferay.osb.koroneiki.trunk.model.ProductEntry
			getProductEntry(String productEntryKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getProductEntry(productEntryKey);
	}

	/**
	 * Returns the product entry with the matching UUID and company.
	 *
	 * @param uuid the product entry's UUID
	 * @param companyId the primary key of the company
	 * @return the matching product entry
	 * @throws PortalException if a matching product entry could not be found
	 */
	public static com.liferay.osb.koroneiki.trunk.model.ProductEntry
			getProductEntryByUuidAndCompanyId(String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getProductEntryByUuidAndCompanyId(uuid, companyId);
	}

	public static com.liferay.osb.koroneiki.trunk.model.ProductEntry reindex(
			long productEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().reindex(productEntryId);
	}

	public static com.liferay.portal.kernel.search.Hits search(
			long companyId, String keywords, int start, int end,
			com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().search(companyId, keywords, start, end, sort);
	}

	public static com.liferay.osb.koroneiki.trunk.model.ProductEntry
			updateProductEntry(long productEntryId, String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateProductEntry(productEntryId, name);
	}

	/**
	 * Updates the product entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param productEntry the product entry
	 * @return the product entry that was updated
	 */
	public static com.liferay.osb.koroneiki.trunk.model.ProductEntry
		updateProductEntry(
			com.liferay.osb.koroneiki.trunk.model.ProductEntry productEntry) {

		return getService().updateProductEntry(productEntry);
	}

	public static ProductEntryLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<ProductEntryLocalService, ProductEntryLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(ProductEntryLocalService.class);

		ServiceTracker<ProductEntryLocalService, ProductEntryLocalService>
			serviceTracker =
				new ServiceTracker
					<ProductEntryLocalService, ProductEntryLocalService>(
						bundle.getBundleContext(),
						ProductEntryLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}