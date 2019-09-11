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
 * Provides a wrapper for {@link ProductConsumptionService}.
 *
 * @author Brian Wing Shun Chan
 * @see ProductConsumptionService
 * @generated
 */
@ProviderType
public class ProductConsumptionServiceWrapper
	implements ProductConsumptionService,
			   ServiceWrapper<ProductConsumptionService> {

	public ProductConsumptionServiceWrapper(
		ProductConsumptionService productConsumptionService) {

		_productConsumptionService = productConsumptionService;
	}

	@Override
	public com.liferay.osb.koroneiki.trunk.model.ProductConsumption
			addProductConsumption(
				long accountId, long productEntryId,
				java.util.List
					<com.liferay.osb.koroneiki.trunk.model.ProductField>
						productFields)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _productConsumptionService.addProductConsumption(
			accountId, productEntryId, productFields);
	}

	@Override
	public com.liferay.osb.koroneiki.trunk.model.ProductConsumption
			addProductConsumption(
				String accountKey, String productEntryKey,
				java.util.List
					<com.liferay.osb.koroneiki.trunk.model.ProductField>
						productFields)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _productConsumptionService.addProductConsumption(
			accountKey, productEntryKey, productFields);
	}

	@Override
	public com.liferay.osb.koroneiki.trunk.model.ProductConsumption
			deleteProductConsumption(long productConsumptionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _productConsumptionService.deleteProductConsumption(
			productConsumptionId);
	}

	@Override
	public com.liferay.osb.koroneiki.trunk.model.ProductConsumption
			deleteProductConsumption(long accountId, long productEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _productConsumptionService.deleteProductConsumption(
			accountId, productEntryId);
	}

	@Override
	public com.liferay.osb.koroneiki.trunk.model.ProductConsumption
			deleteProductConsumption(String productConsumptionKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _productConsumptionService.deleteProductConsumption(
			productConsumptionKey);
	}

	@Override
	public java.util.List
		<com.liferay.osb.koroneiki.trunk.model.ProductConsumption>
				getAccountProductConsumptions(
					long accountId, int start, int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _productConsumptionService.getAccountProductConsumptions(
			accountId, start, end);
	}

	@Override
	public java.util.List
		<com.liferay.osb.koroneiki.trunk.model.ProductConsumption>
				getAccountProductConsumptions(
					String accountKey, int start, int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _productConsumptionService.getAccountProductConsumptions(
			accountKey, start, end);
	}

	@Override
	public int getAccountProductConsumptionsCount(long accountId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _productConsumptionService.getAccountProductConsumptionsCount(
			accountId);
	}

	@Override
	public int getAccountProductConsumptionsCount(String accountKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _productConsumptionService.getAccountProductConsumptionsCount(
			accountKey);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _productConsumptionService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.osb.koroneiki.trunk.model.ProductConsumption
			getProductConsumption(long productConsumptionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _productConsumptionService.getProductConsumption(
			productConsumptionId);
	}

	@Override
	public com.liferay.osb.koroneiki.trunk.model.ProductConsumption
			getProductConsumption(String productConsumptionKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _productConsumptionService.getProductConsumption(
			productConsumptionKey);
	}

	@Override
	public ProductConsumptionService getWrappedService() {
		return _productConsumptionService;
	}

	@Override
	public void setWrappedService(
		ProductConsumptionService productConsumptionService) {

		_productConsumptionService = productConsumptionService;
	}

	private ProductConsumptionService _productConsumptionService;

}