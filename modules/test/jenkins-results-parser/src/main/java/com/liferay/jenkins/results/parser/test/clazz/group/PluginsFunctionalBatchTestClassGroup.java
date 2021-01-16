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

package com.liferay.jenkins.results.parser.test.clazz.group;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.PluginsGitRepositoryJob;
import com.liferay.jenkins.results.parser.PortalTestClassJob;

import java.io.File;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Michael Hashimoto
 */
public class PluginsFunctionalBatchTestClassGroup
	extends FunctionalBatchTestClassGroup {

	@Override
	public List<File> getTestBaseDirs() {
		if (!(portalTestClassJob instanceof PluginsGitRepositoryJob)) {
			return new ArrayList<>();
		}

		PluginsGitRepositoryJob pluginsGitRepositoryJob =
			(PluginsGitRepositoryJob)portalTestClassJob;

		return pluginsGitRepositoryJob.getPluginsTestBaseDirs();
	}

	protected PluginsFunctionalBatchTestClassGroup(
		String batchName, PortalTestClassJob portalTestClassJob) {

		super(batchName, portalTestClassJob);
	}

	@Override
	protected String getDefaultTestBatchRunPropertyQuery(
		File testBaseDir, String testSuiteName) {

		String propertyQuery = System.getenv("TEST_BATCH_RUN_PROPERTY_QUERY");

		if ((propertyQuery != null) && !propertyQuery.isEmpty()) {
			return propertyQuery;
		}

		return JenkinsResultsParserUtil.getProperty(
			jobProperties, "test.batch.run.property.query", batchName,
			_getPortletName(testBaseDir), testSuiteName, getJobName());
	}

	private String _getPortletName(File testBaseDir) {
		String testBaseDirPath = JenkinsResultsParserUtil.getCanonicalPath(
			testBaseDir);

		if (JenkinsResultsParserUtil.isNullOrEmpty(testBaseDirPath)) {
			return null;
		}

		Matcher matcher = _pattern.matcher(testBaseDirPath);

		if (!matcher.find()) {
			return null;
		}

		return matcher.group("portletName");
	}

	private static final Pattern _pattern = Pattern.compile(
		".*/portlets/(?<portletName>[^/]+-portlet)/.*");

}