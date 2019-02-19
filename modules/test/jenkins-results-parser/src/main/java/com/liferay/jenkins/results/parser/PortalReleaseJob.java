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

import java.util.Collections;
import java.util.Set;

/**
 * @author Michael Hashimoto
 */
public class PortalReleaseJob extends BasePortalReleaseJob {

	public PortalReleaseJob(String jobName, String portalBranchName) {
		super(jobName, portalBranchName);

		GitWorkingDirectory jenkinsGitWorkingDirectory =
			getJenkinsGitWorkingDirectory();

		jobPropertiesFiles.add(
			new File(
				jenkinsGitWorkingDirectory.getWorkingDirectory(),
				"commands/dependencies/test-portal-release.properties"));

		readJobProperties();
	}

	@Override
	public Set<String> getBatchNames() {
		Set<String> testBatchNames = super.getBatchNames();

		testBatchNames.addAll(_getOptionalBatchNames());

		return testBatchNames;
	}

	public void setPortalReleaseRef(String portalReleaseRef) {
		_portalReleaseRef = portalReleaseRef;
	}

	private Set<String> _getOptionalBatchNames() {
		if (_portalReleaseRef == null) {
			return Collections.emptySet();
		}

		String testBatchNamesString = JenkinsResultsParserUtil.getProperty(
			getJobProperties(),
			"test.batch.names.optional[" + _portalReleaseRef + "]");

		return getSetFromString(testBatchNamesString);
	}

	private String _portalReleaseRef;

}