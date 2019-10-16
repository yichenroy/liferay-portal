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

package com.liferay.commerce.internal.util;

import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.service.CommerceCurrencyService;
import com.liferay.commerce.util.CommercePriceFormatter;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringPool;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.http.HttpServletRequest;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * @author Alessio Antonio Rendina
 */
@Component(immediate = true)
public class CommercePriceFormatterImpl implements CommercePriceFormatter {

	@Override
	public String format(HttpServletRequest httpServletRequest, double price)
		throws PortalException {

		Locale locale = _portal.getLocale(httpServletRequest);
		long groupId = _portal.getScopeGroupId(httpServletRequest);

		return format(locale, groupId, price);
	}

	@Override
	public String format(Locale locale, long groupId, double price)
		throws PortalException {

		StringBuilder sb = new StringBuilder();

		CommerceCurrency commerceCurrency =
			_commerceCurrencyService.fetchPrimaryCommerceCurrency(groupId);

		String code = StringPool.BLANK;

		if (commerceCurrency != null) {
			code = commerceCurrency.getCodeCurrentValue();
		}

		sb.append(code);
		sb.append(StringPool.SPACE);

		NumberFormat numberFormat = NumberFormat.getNumberInstance(locale);

		sb.append(numberFormat.format(price));

		return sb.toString();
	}

	@Reference
	private CommerceCurrencyService _commerceCurrencyService;

	@Reference
	private Portal _portal;

}