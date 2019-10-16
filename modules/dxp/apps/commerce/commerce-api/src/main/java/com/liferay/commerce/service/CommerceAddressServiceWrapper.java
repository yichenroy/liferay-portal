/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.commerce.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceAddressService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceAddressService
 * @generated
 */
@ProviderType
public class CommerceAddressServiceWrapper implements CommerceAddressService,
	ServiceWrapper<CommerceAddressService> {
	public CommerceAddressServiceWrapper(
		CommerceAddressService commerceAddressService) {
		_commerceAddressService = commerceAddressService;
	}

	@Override
	public com.liferay.commerce.model.CommerceAddress addCommerceAddress(
		long addressUserId, java.lang.String name,
		java.lang.String description, java.lang.String street1,
		java.lang.String street2, java.lang.String street3,
		java.lang.String city, java.lang.String zip, long commerceRegionId,
		long commerceCountryId, java.lang.String phoneNumber,
		boolean defaultBilling, boolean defaultShipping,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceAddressService.addCommerceAddress(addressUserId, name,
			description, street1, street2, street3, city, zip,
			commerceRegionId, commerceCountryId, phoneNumber, defaultBilling,
			defaultShipping, serviceContext);
	}

	@Override
	public void deleteCommerceAddress(long commerceAddressId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_commerceAddressService.deleteCommerceAddress(commerceAddressId);
	}

	@Override
	public com.liferay.commerce.model.CommerceAddress getCommerceAddress(
		long commerceAddressId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceAddressService.getCommerceAddress(commerceAddressId);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceAddress> getCommerceAddresses(
		long groupId, long addressUserId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceAddressService.getCommerceAddresses(groupId,
			addressUserId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _commerceAddressService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.commerce.model.CommerceAddress updateCommerceAddress(
		long commerceAddressId, java.lang.String name,
		java.lang.String description, java.lang.String street1,
		java.lang.String street2, java.lang.String street3,
		java.lang.String city, java.lang.String zip, long commerceRegionId,
		long commerceCountryId, java.lang.String phoneNumber,
		boolean defaultBilling, boolean defaultShipping,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceAddressService.updateCommerceAddress(commerceAddressId,
			name, description, street1, street2, street3, city, zip,
			commerceRegionId, commerceCountryId, phoneNumber, defaultBilling,
			defaultShipping, serviceContext);
	}

	@Override
	public CommerceAddressService getWrappedService() {
		return _commerceAddressService;
	}

	@Override
	public void setWrappedService(CommerceAddressService commerceAddressService) {
		_commerceAddressService = commerceAddressService;
	}

	private CommerceAddressService _commerceAddressService;
}