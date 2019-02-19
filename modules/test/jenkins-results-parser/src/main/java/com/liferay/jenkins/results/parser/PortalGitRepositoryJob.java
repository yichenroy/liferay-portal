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

import java.io.File;

import java.util.Properties;
import java.util.Set;

/**
 * @author Michael Hashimoto
 */
public abstract class PortalGitRepositoryJob
	extends GitRepositoryJob implements PortalTestClassJob {

	@Override
	public Set<String> getBatchNames() {
		String testBatchNames = JenkinsResultsParserUtil.getProperty(
			getJobProperties(), "test.batch.names");

		return getSetFromString(testBatchNames);
	}

	@Override
	public Set<String> getDistTypes() {
		String testBatchDistAppServers = JenkinsResultsParserUtil.getProperty(
			getJobProperties(), "test.batch.dist.app.servers");

		return getSetFromString(testBatchDistAppServers);
	}

	@Override
	public PortalGitWorkingDirectory getPortalGitWorkingDirectory() {
		GitWorkingDirectory gitWorkingDirectory = getGitWorkingDirectory();

		if (!(gitWorkingDirectory instanceof PortalGitWorkingDirectory)) {
			throw new RuntimeException("Invalid portal Git working directory");
		}

		return (PortalGitWorkingDirectory)gitWorkingDirectory;
	}

	public String getPoshiQuery(String testBatchName) {
		Properties jobProperties = getJobProperties();

		String propertyName = JenkinsResultsParserUtil.combine(
			"test.batch.run.property.query[", testBatchName, "]");

		if (jobProperties.containsKey(propertyName)) {
			String propertyValue = JenkinsResultsParserUtil.getProperty(
				jobProperties, propertyName);

			if ((propertyValue != null) && !propertyValue.isEmpty()) {
				return propertyValue;
			}
		}

		return null;
	}

	protected PortalGitRepositoryJob(String jobName) {
		super(jobName);

		gitWorkingDirectory = getNewGitWorkingDirectory();

		setGitRepositoryDir(gitWorkingDirectory.getWorkingDirectory());

		checkGitRepositoryDir();

		jobPropertiesFiles.add(
			new File(gitRepositoryDir, "tools/sdk/build.properties"));
		jobPropertiesFiles.add(new File(gitRepositoryDir, "build.properties"));
		jobPropertiesFiles.add(new File(gitRepositoryDir, "test.properties"));

		readJobProperties();
	}

	protected GitWorkingDirectory getNewGitWorkingDirectory() {
		return JenkinsResultsParserUtil.getPortalGitWorkingDirectory(
			getBranchName());
	}

}