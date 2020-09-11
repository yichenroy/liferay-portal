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

package com.liferay.jenkins.results.parser;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Michael Hashimoto
 */
public class PortalReleasePortalTopLevelBuild
	extends PortalTopLevelBuild implements PortalReleaseBuild {

	public PortalReleasePortalTopLevelBuild(
		String url, TopLevelBuild topLevelBuild) {

		super(url, topLevelBuild);
	}

	@Override
	public PortalRelease getPortalRelease() {
		if (_portalRelease != null) {
			return _portalRelease;
		}

		try {
			URL portalReleaseTomcatURL = new URL(
				getParameterValue("TEST_PORTAL_RELEASE_TOMCAT_URL"));

			_portalRelease = new PortalRelease(portalReleaseTomcatURL);
		}
		catch (MalformedURLException malformedURLException) {
			throw new RuntimeException(malformedURLException);
		}

		return _portalRelease;
	}

	private PortalRelease _portalRelease;

}