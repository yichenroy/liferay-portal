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

package com.liferay.commerce.currency.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Andrea Di Giorgi
 */
public class NoSuchCurrencyException extends NoSuchModelException {

	public NoSuchCurrencyException() {
	}

	public NoSuchCurrencyException(String msg) {
		super(msg);
	}

	public NoSuchCurrencyException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchCurrencyException(Throwable throwable) {
		super(throwable);
	}

}