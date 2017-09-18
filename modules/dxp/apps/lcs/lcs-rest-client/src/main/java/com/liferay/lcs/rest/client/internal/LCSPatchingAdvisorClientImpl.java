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

import com.liferay.lcs.rest.client.LCSPatchingAdvisorClient;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Riccardo Ferrari
 */
@Component(immediate = true, service = LCSPatchingAdvisorClient.class)
public class LCSPatchingAdvisorClientImpl
	extends BaseLCSServiceImpl implements LCSPatchingAdvisorClient {

	@Override
	public List<String> getInstallablePatchIds(
			String key, int patchingToolVersion, String[] installedPatchIds)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < installedPatchIds.length; i++) {
			sb.append(installedPatchIds[i]);

			if ((i + 1) < installedPatchIds.length) {
				sb.append(",");
			}
		}

		return doGetToList(
			String.class, _URL_PATCHING_ADVISOR, "key", key,
			"patchingToolVersion", String.valueOf(patchingToolVersion),
			"installedPatchIds", sb.toString());
	}

	private static final String _URL_PATCHING_ADVISOR =
		"/o/osb-lcs-rest/LCSPatchingAdvisor";

}