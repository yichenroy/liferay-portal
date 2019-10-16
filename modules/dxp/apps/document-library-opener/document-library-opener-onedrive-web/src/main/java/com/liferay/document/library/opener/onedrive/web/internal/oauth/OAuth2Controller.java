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

package com.liferay.document.library.opener.onedrive.web.internal.oauth;

import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

/**
 * @author Cristina González
 */
public interface OAuth2Controller {

	public void execute(
			PortletRequest portletRequest, PortletResponse portletResponse,
			UnsafeFunction<PortletRequest, JSONObject, PortalException>
				unsafeFunction)
		throws PortalException;

}