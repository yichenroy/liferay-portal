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

package com.liferay.commerce.product.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Alessio Antonio Rendina
 */
@ExtendedObjectClassDefinition(category = "catalog")
@Meta.OCD(
	id = "com.liferay.commerce.product.configuration.CPOptionConfiguration",
	localization = "content/Language", name = "cp-option-configuration-name"
)
public interface CPOptionConfiguration {

	@Meta.AD(
		deflt = "select|radio|date|checkbox|checkbox_multiple|numeric",
		name = "product-option-form-field-types-allowed", required = false
	)
	public String[] ddmFormFieldTypesAllowed();

}