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

package com.liferay.portal.security.audit.router.internal;

import com.liferay.portal.kernel.audit.AuditMessage;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.security.audit.router.LogMessageFormatter;

import org.osgi.service.component.annotations.Component;

/**
 * @author Mika Koivisto
 */
@Component(
	immediate = true, property = "format=JSON",
	service = LogMessageFormatter.class
)
public class JSONLogMessageFormatter implements LogMessageFormatter {

	@Override
	public String format(AuditMessage auditMessage) {
		JSONObject jsonObject = auditMessage.toJSONObject();

		return jsonObject.toString();
	}

}