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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Michael Hashimoto
 */
public class MarketplaceAppPluginsTopLevelBuild extends PluginsTopLevelBuild {

	public MarketplaceAppPluginsTopLevelBuild(
		String url, TopLevelBuild topLevelBuild) {

		super(url, topLevelBuild);
	}

	@Override
	public String getBaseGitRepositoryName() {
		String branchName = getBranchName();

		if (branchName.equals("master")) {
			return "liferay-portal";
		}

		return "liferay-portal-ee";
	}

	@Override
	public String getBranchName() {
		Matcher matcher = _pattern.matcher(
			getParameterValue("TEST_PORTAL_BUILD_NUMBER"));

		if (!matcher.find()) {
			throw new RuntimeException("Please set 'TEST_PORTAL_BUILD_NUMBER'");
		}

		return JenkinsResultsParserUtil.combine(
			matcher.group("major"), ".", matcher.group("minor"), ".x");
	}

	@Override
	public Job.BuildProfile getBuildProfile() {
		Matcher matcher = _pattern.matcher(
			getParameterValue("TEST_PORTAL_BUILD_NUMBER"));

		if (!matcher.find()) {
			throw new RuntimeException("Please set 'TEST_PORTAL_BUILD_NUMBER'");
		}

		String fix = matcher.group("fix");

		if (fix.startsWith("1")) {
			return Job.BuildProfile.DXP;
		}

		return Job.BuildProfile.PORTAL;
	}

	@Override
	public String getPluginName() {
		return getParameterValue("TEST_PACKAGE_FILE_NAME");
	}

	private static final Pattern _pattern = Pattern.compile(
		"(?<major>\\d)(?<minor>\\d)(?<fix>\\d+)");

}