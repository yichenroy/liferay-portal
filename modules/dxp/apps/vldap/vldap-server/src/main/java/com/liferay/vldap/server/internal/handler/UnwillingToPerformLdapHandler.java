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

package com.liferay.vldap.server.internal.handler;

import com.liferay.vldap.server.internal.handler.util.LdapHandlerContext;

import java.util.ArrayList;
import java.util.List;

import org.apache.directory.api.ldap.model.message.Request;
import org.apache.directory.api.ldap.model.message.Response;
import org.apache.directory.api.ldap.model.message.ResultCodeEnum;
import org.apache.directory.api.ldap.model.message.ResultResponseRequest;
import org.apache.mina.core.session.IoSession;

/**
 * @author Brian Wing Shun Chan
 */
public class UnwillingToPerformLdapHandler extends BaseLdapHandler {

	@Override
	public List<Response> messageReceived(
		Request request, IoSession ioSession,
		LdapHandlerContext ldapHandlerContext) {

		List<Response> responses = new ArrayList<>();

		if (request instanceof ResultResponseRequest) {
			ResultResponseRequest resultResponseRequest =
				(ResultResponseRequest)request;

			Response response = getResultResponse(
				resultResponseRequest, ResultCodeEnum.UNWILLING_TO_PERFORM);

			responses.add(response);
		}

		return responses;
	}

}