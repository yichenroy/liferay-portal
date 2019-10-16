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

package com.liferay.commerce.checkout.web.util;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Marco Leo
 */
public interface CommerceCheckoutStep {

	public boolean action(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception;

	public String getLabel(Locale locale);

	public boolean isActive(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception;

	public boolean isVisible(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception;

	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception;

}