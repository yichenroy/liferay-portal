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

package com.liferay.commerce.checkout.web.internal.display.context;

import com.liferay.commerce.checkout.web.constants.CommerceCheckoutWebKeys;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceCart;
import com.liferay.commerce.service.CommerceAddressService;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

import javax.portlet.RenderRequest;

/**
 * @author Andrea Di Giorgi
 */
public class CheckoutStepBillingDisplayContext {

	public CheckoutStepBillingDisplayContext(
		CommerceAddressService commerceAddressService,
		RenderRequest renderRequest) {

		_commerceAddressService = commerceAddressService;

		_commerceCart = (CommerceCart)renderRequest.getAttribute(
			CommerceCheckoutWebKeys.COMMERCE_CART);
	}

	public List<CommerceAddress> getCommerceAddresses() throws PortalException {
		return _commerceAddressService.getCommerceAddresses(
			_commerceCart.getGroupId(), _commerceCart.getUserId());
	}

	private final CommerceAddressService _commerceAddressService;
	private final CommerceCart _commerceCart;

}