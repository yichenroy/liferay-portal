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

import com.liferay.portal.kernel.service.ServiceWrapper;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides a wrapper for {@link ProductPurchaseLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see ProductPurchaseLocalService
 * @generated
 */
@ProviderType
public class ProductPurchaseLocalServiceWrapper
	implements ProductPurchaseLocalService,
			   ServiceWrapper<ProductPurchaseLocalService> {

	public ProductPurchaseLocalServiceWrapper(
		ProductPurchaseLocalService productPurchaseLocalService) {

		_productPurchaseLocalService = productPurchaseLocalService;
	}

	@Override
	public com.liferay.osb.koroneiki.trunk.model.ProductPurchase
			addProductPurchase(
				long userId, long accountId, long productEntryId,
				java.util.Date startDate, java.util.Date endDate, int quantity,
				java.util.List
					<com.liferay.osb.koroneiki.trunk.model.ProductField>
						productFields)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _productPurchaseLocalService.addProductPurchase(
			userId, accountId, productEntryId, startDate, endDate, quantity,
			productFields);
	}

	/**
	 * Adds the product purchase to the database. Also notifies the appropriate model listeners.
	 *
	 * @param productPurchase the product purchase
	 * @return the product purchase that was added
	 */
	@Override
	public com.liferay.osb.koroneiki.trunk.model.ProductPurchase
		addProductPurchase(
			com.liferay.osb.koroneiki.trunk.model.ProductPurchase
				productPurchase) {

		return _productPurchaseLocalService.addProductPurchase(productPurchase);
	}

	/**
	 * Creates a new product purchase with the primary key. Does not add the product purchase to the database.
	 *
	 * @param productPurchaseId the primary key for the new product purchase
	 * @return the new product purchase
	 */
	@Override
	public com.liferay.osb.koroneiki.trunk.model.ProductPurchase
		createProductPurchase(long productPurchaseId) {

		return _productPurchaseLocalService.createProductPurchase(
			productPurchaseId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _productPurchaseLocalService.deletePersistedModel(
			persistedModel);
	}

	/**
	 * Deletes the product purchase with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param productPurchaseId the primary key of the product purchase
	 * @return the product purchase that was removed
	 * @throws PortalException if a product purchase with the primary key could not be found
	 */
	@Override
	public com.liferay.osb.koroneiki.trunk.model.ProductPurchase
			deleteProductPurchase(long productPurchaseId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _productPurchaseLocalService.deleteProductPurchase(
			productPurchaseId);
	}

	/**
	 * Deletes the product purchase from the database. Also notifies the appropriate model listeners.
	 *
	 * @param productPurchase the product purchase
	 * @return the product purchase that was removed
	 */
	@Override
	public com.liferay.osb.koroneiki.trunk.model.ProductPurchase
		deleteProductPurchase(
			com.liferay.osb.koroneiki.trunk.model.ProductPurchase
				productPurchase) {

		return _productPurchaseLocalService.deleteProductPurchase(
			productPurchase);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _productPurchaseLocalService.dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _productPurchaseLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.osb.koroneiki.trunk.model.impl.ProductPurchaseModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return _productPurchaseLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.osb.koroneiki.trunk.model.impl.ProductPurchaseModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return _productPurchaseLocalService.dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _productPurchaseLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return _productPurchaseLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.osb.koroneiki.trunk.model.ProductPurchase
		fetchProductPurchase(long productPurchaseId) {

		return _productPurchaseLocalService.fetchProductPurchase(
			productPurchaseId);
	}

	/**
	 * Returns the product purchase with the matching UUID and company.
	 *
	 * @param uuid the product purchase's UUID
	 * @param companyId the primary key of the company
	 * @return the matching product purchase, or <code>null</code> if a matching product purchase could not be found
	 */
	@Override
	public com.liferay.osb.koroneiki.trunk.model.ProductPurchase
		fetchProductPurchaseByUuidAndCompanyId(String uuid, long companyId) {

		return _productPurchaseLocalService.
			fetchProductPurchaseByUuidAndCompanyId(uuid, companyId);
	}

	@Override
	public java.util.List<com.liferay.osb.koroneiki.trunk.model.ProductPurchase>
		getAccountProductPurchases(long accountId, int start, int end) {

		return _productPurchaseLocalService.getAccountProductPurchases(
			accountId, start, end);
	}

	@Override
	public int getAccountProductPurchasesCount(long accountId) {
		return _productPurchaseLocalService.getAccountProductPurchasesCount(
			accountId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _productPurchaseLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _productPurchaseLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _productPurchaseLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _productPurchaseLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _productPurchaseLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Returns the product purchase with the primary key.
	 *
	 * @param productPurchaseId the primary key of the product purchase
	 * @return the product purchase
	 * @throws PortalException if a product purchase with the primary key could not be found
	 */
	@Override
	public com.liferay.osb.koroneiki.trunk.model.ProductPurchase
			getProductPurchase(long productPurchaseId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _productPurchaseLocalService.getProductPurchase(
			productPurchaseId);
	}

	@Override
	public com.liferay.osb.koroneiki.trunk.model.ProductPurchase
			getProductPurchase(String productPurchaseKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _productPurchaseLocalService.getProductPurchase(
			productPurchaseKey);
	}

	/**
	 * Returns the product purchase with the matching UUID and company.
	 *
	 * @param uuid the product purchase's UUID
	 * @param companyId the primary key of the company
	 * @return the matching product purchase
	 * @throws PortalException if a matching product purchase could not be found
	 */
	@Override
	public com.liferay.osb.koroneiki.trunk.model.ProductPurchase
			getProductPurchaseByUuidAndCompanyId(String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _productPurchaseLocalService.
			getProductPurchaseByUuidAndCompanyId(uuid, companyId);
	}

	/**
	 * Returns a range of all the product purchases.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.osb.koroneiki.trunk.model.impl.ProductPurchaseModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of product purchases
	 * @param end the upper bound of the range of product purchases (not inclusive)
	 * @return the range of product purchases
	 */
	@Override
	public java.util.List<com.liferay.osb.koroneiki.trunk.model.ProductPurchase>
		getProductPurchases(int start, int end) {

		return _productPurchaseLocalService.getProductPurchases(start, end);
	}

	/**
	 * Returns the number of product purchases.
	 *
	 * @return the number of product purchases
	 */
	@Override
	public int getProductPurchasesCount() {
		return _productPurchaseLocalService.getProductPurchasesCount();
	}

	@Override
	public com.liferay.osb.koroneiki.trunk.model.ProductPurchase reindex(
			long productPurchaseId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _productPurchaseLocalService.reindex(productPurchaseId);
	}

	@Override
	public com.liferay.portal.kernel.search.Hits search(
			long companyId, String keywords, int start, int end,
			com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _productPurchaseLocalService.search(
			companyId, keywords, start, end, sort);
	}

	@Override
	public com.liferay.osb.koroneiki.trunk.model.ProductPurchase
			updateProductPurchase(
				long userId, long productPurchaseId, java.util.Date startDate,
				java.util.Date endDate, int quantity,
				java.util.List
					<com.liferay.osb.koroneiki.trunk.model.ProductField>
						productFields)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _productPurchaseLocalService.updateProductPurchase(
			userId, productPurchaseId, startDate, endDate, quantity,
			productFields);
	}

	/**
	 * Updates the product purchase in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param productPurchase the product purchase
	 * @return the product purchase that was updated
	 */
	@Override
	public com.liferay.osb.koroneiki.trunk.model.ProductPurchase
		updateProductPurchase(
			com.liferay.osb.koroneiki.trunk.model.ProductPurchase
				productPurchase) {

		return _productPurchaseLocalService.updateProductPurchase(
			productPurchase);
	}

	@Override
	public ProductPurchaseLocalService getWrappedService() {
		return _productPurchaseLocalService;
	}

	@Override
	public void setWrappedService(
		ProductPurchaseLocalService productPurchaseLocalService) {

		_productPurchaseLocalService = productPurchaseLocalService;
	}

	private ProductPurchaseLocalService _productPurchaseLocalService;

}