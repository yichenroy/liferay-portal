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

package com.liferay.commerce.discount.internal.target;

import com.liferay.commerce.discount.model.CommerceDiscountConstants;
import com.liferay.commerce.discount.target.CommerceDiscountTargetType;
import com.liferay.portal.kernel.language.LanguageUtil;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"commerce.discount.target.type.key=" + CommerceDiscountConstants.TARGET_SHIPMENT,
		"commerce.discount.target.type.order:Integer=30"
	},
	service = CommerceDiscountTargetType.class
)
public class ApplyToShipmentCommerceDiscountTargetTypeImpl
	implements CommerceDiscountTargetType {

	@Override
	public String getKey() {
		return CommerceDiscountConstants.TARGET_SHIPMENT;
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, "apply-to-shipment");
	}

}