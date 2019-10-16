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

package com.liferay.commerce.util;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.model.CommerceCart;
import com.liferay.portal.kernel.exception.PortalException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Marco Leo
 */
@ProviderType
public interface CommerceCartHelper {

	public CommerceCart getCurrentCommerceCart(
			HttpServletRequest httpServletRequest)
		throws PortalException;

	public CommerceCart getCurrentCommerceCart(
			HttpServletRequest httpServletRequest, int type)
		throws PortalException;

	public int getCurrentCommerceCartItemsCount(
			HttpServletRequest httpServletRequest, int type)
		throws PortalException;

	public void updateCurrentCommerceCart(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, CommerceCart commerceCart);

}