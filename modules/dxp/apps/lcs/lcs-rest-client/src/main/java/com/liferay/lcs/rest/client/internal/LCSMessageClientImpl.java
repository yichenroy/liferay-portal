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

package com.liferay.lcs.rest.client.internal;

import com.liferay.lcs.rest.client.LCSMessageClient;
import com.liferay.petra.json.web.service.client.JSONWebServiceClient;
import com.liferay.petra.json.web.service.client.JSONWebServiceInvocationException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
@Component(immediate = true, service = LCSMessageClient.class)
public class LCSMessageClientImpl implements LCSMessageClient {

	@Override
	public void addCorpProjectLCSMessage(
			long corpProjectId, long sourceMessageId, String content, int type)
		throws JSONWebServiceInvocationException {

		_jsonWebServiceClient.doPost(
			_URL_LCS_MESSAGE, "corpProjectId", String.valueOf(corpProjectId),
			"sourceMessageId", String.valueOf(sourceMessageId), "content",
			content, "type", String.valueOf(type));
	}

	@Override
	public void deleteCorpProjectLCSMessage(
			long corpProjectId, long sourceMessageId)
		throws JSONWebServiceInvocationException {

		_jsonWebServiceClient.doDelete(
			_URL_LCS_MESSAGE + "/" + corpProjectId + "/" + sourceMessageId);
	}

	public void setJSONWebServiceClient(
		JSONWebServiceClient jsonWebServiceClient) {

		_jsonWebServiceClient = jsonWebServiceClient;
	}

	private static final String _URL_LCS_MESSAGE = "/o/osb-lcs-rest/LCSMessage";

	@Reference(target = "(component.name=OSBLCSJSONWebServiceClient)")
	private JSONWebServiceClient _jsonWebServiceClient;

}