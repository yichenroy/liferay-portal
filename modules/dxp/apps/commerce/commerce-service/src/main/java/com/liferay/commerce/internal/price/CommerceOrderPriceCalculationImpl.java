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

package com.liferay.commerce.internal.price;

import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.price.CommerceOrderPriceCalculation;
import com.liferay.portal.kernel.exception.PortalException;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alessio Antonio Rendins
 */
@Component
public class CommerceOrderPriceCalculationImpl
	implements CommerceOrderPriceCalculation {

	@Override
	public CommerceMoney getShippingValue(long commerceOrderId)
		throws PortalException {

		return null;
	}

	@Override
	public CommerceMoney getSubtotal(long commerceOrderId)
		throws PortalException {

		return null;
	}

	@Override
	public CommerceMoney getTaxValue(long commerceOrderId)
		throws PortalException {

		return null;
	}

	@Override
	public CommerceMoney getTotal(long commerceOrderId) throws PortalException {
		return null;
	}

}