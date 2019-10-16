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

package com.liferay.multi.factor.authentication.spi.checker.browser;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Tomas Polesovsky
 */
public interface BrowserMFAChecker {

	public void includeBrowserVerification(
			HttpServletRequest request, HttpServletResponse response,
			long userId)
		throws IOException;

	public boolean isBrowserVerified(HttpServletRequest request, long userId);

	public boolean verifyBrowserRequest(
		HttpServletRequest request, HttpServletResponse response, long userId);

}