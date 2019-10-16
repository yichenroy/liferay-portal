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

package com.liferay.commerce.currency.internal.model;

import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.currency.util.CommercePriceFormatter;
import com.liferay.portal.kernel.exception.PortalException;

import java.math.BigDecimal;

import java.util.Locale;

/**
 * @author Marco Leo
 */
public class CommerceMoneyImpl implements CommerceMoney {

	public CommerceMoneyImpl(CommercePriceFormatter commercePriceFormatter) {
		_commercePriceFormatter = commercePriceFormatter;
	}

	@Override
	public String format(Locale locale) throws PortalException {
		return _commercePriceFormatter.format(
			getCommerceCurrency(), getPrice(), locale);
	}

	@Override
	public CommerceCurrency getCommerceCurrency() {
		return _commerceCurrency;
	}

	@Override
	public BigDecimal getPrice() {
		return _price;
	}

	public void setCommerceCurrency(CommerceCurrency commerceCurrency) {
		_commerceCurrency = commerceCurrency;
	}

	public void setPrice(BigDecimal price) {
		_price = price;
	}

	private CommerceCurrency _commerceCurrency;
	private final CommercePriceFormatter _commercePriceFormatter;
	private BigDecimal _price;

}