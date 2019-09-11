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
 * Provides a wrapper for {@link ProductFieldService}.
 *
 * @author Brian Wing Shun Chan
 * @see ProductFieldService
 * @generated
 */
@ProviderType
public class ProductFieldServiceWrapper
	implements ProductFieldService, ServiceWrapper<ProductFieldService> {

	public ProductFieldServiceWrapper(ProductFieldService productFieldService) {
		_productFieldService = productFieldService;
	}

	@Override
	public com.liferay.osb.koroneiki.trunk.model.ProductField addProductField(
			long classNameId, long classPK, String name, String value)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _productFieldService.addProductField(
			classNameId, classPK, name, value);
	}

	@Override
	public com.liferay.osb.koroneiki.trunk.model.ProductField
			deleteProductField(long productFieldId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _productFieldService.deleteProductField(productFieldId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _productFieldService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.osb.koroneiki.trunk.model.ProductField
			updateProductField(long productFieldId, String value)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _productFieldService.updateProductField(productFieldId, value);
	}

	@Override
	public ProductFieldService getWrappedService() {
		return _productFieldService;
	}

	@Override
	public void setWrappedService(ProductFieldService productFieldService) {
		_productFieldService = productFieldService;
	}

	private ProductFieldService _productFieldService;

}