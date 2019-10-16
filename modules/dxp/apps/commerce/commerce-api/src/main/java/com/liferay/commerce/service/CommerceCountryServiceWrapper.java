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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceCountryService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceCountryService
 * @generated
 */
@ProviderType
public class CommerceCountryServiceWrapper implements CommerceCountryService,
	ServiceWrapper<CommerceCountryService> {
	public CommerceCountryServiceWrapper(
		CommerceCountryService commerceCountryService) {
		_commerceCountryService = commerceCountryService;
	}

	@Override
	public com.liferay.commerce.model.CommerceCountry addCommerceCountry(
		java.util.Map<java.util.Locale, String> nameMap,
		boolean billingAllowed, boolean shippingAllowed,
		String twoLettersISOCode, String threeLettersISOCode,
		int numericISOCode, boolean subjectToVAT, double priority,
		boolean active,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceCountryService.addCommerceCountry(nameMap,
			billingAllowed, shippingAllowed, twoLettersISOCode,
			threeLettersISOCode, numericISOCode, subjectToVAT, priority,
			active, serviceContext);
	}

	@Override
	public void deleteCommerceCountry(long commerceCountryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_commerceCountryService.deleteCommerceCountry(commerceCountryId);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceCountry> getBillingCommerceCountries(
		long groupId, boolean billingAllowed, boolean active) {
		return _commerceCountryService.getBillingCommerceCountries(groupId,
			billingAllowed, active);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceCountry> getCommerceCountries(
		long groupId, boolean active) {
		return _commerceCountryService.getCommerceCountries(groupId, active);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceCountry> getCommerceCountries(
		long groupId, boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceCountry> orderByComparator) {
		return _commerceCountryService.getCommerceCountries(groupId, active,
			start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceCountry> getCommerceCountries(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceCountry> orderByComparator) {
		return _commerceCountryService.getCommerceCountries(groupId, start,
			end, orderByComparator);
	}

	@Override
	public int getCommerceCountriesCount(long groupId) {
		return _commerceCountryService.getCommerceCountriesCount(groupId);
	}

	@Override
	public int getCommerceCountriesCount(long groupId, boolean active) {
		return _commerceCountryService.getCommerceCountriesCount(groupId, active);
	}

	@Override
	public com.liferay.commerce.model.CommerceCountry getCommerceCountry(
		long commerceCountryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceCountryService.getCommerceCountry(commerceCountryId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceCountryService.getOSGiServiceIdentifier();
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceCountry> getShippingCommerceCountries(
		long groupId, boolean shippingAllowed, boolean active) {
		return _commerceCountryService.getShippingCommerceCountries(groupId,
			shippingAllowed, active);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceCountry> getWarehouseCommerceCountries(
		long groupId, boolean all)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceCountryService.getWarehouseCommerceCountries(groupId,
			all);
	}

	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.model.CommerceCountry> searchCommerceCountries(
		com.liferay.portal.kernel.search.SearchContext searchContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceCountryService.searchCommerceCountries(searchContext);
	}

	@Override
	public com.liferay.commerce.model.CommerceCountry updateCommerceCountry(
		long commerceCountryId,
		java.util.Map<java.util.Locale, String> nameMap,
		boolean billingAllowed, boolean shippingAllowed,
		String twoLettersISOCode, String threeLettersISOCode,
		int numericISOCode, boolean subjectToVAT, double priority,
		boolean active,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceCountryService.updateCommerceCountry(commerceCountryId,
			nameMap, billingAllowed, shippingAllowed, twoLettersISOCode,
			threeLettersISOCode, numericISOCode, subjectToVAT, priority,
			active, serviceContext);
	}

	@Override
	public CommerceCountryService getWrappedService() {
		return _commerceCountryService;
	}

	@Override
	public void setWrappedService(CommerceCountryService commerceCountryService) {
		_commerceCountryService = commerceCountryService;
	}

	private CommerceCountryService _commerceCountryService;
}