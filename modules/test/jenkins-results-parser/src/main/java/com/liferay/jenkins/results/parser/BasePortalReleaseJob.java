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
public abstract class BasePortalReleaseJob
	extends BaseJob implements BatchDependentJob, PortalTestClassJob {

	public BasePortalReleaseJob(
		String jobName, String portalBranchName, BuildProfile buildProfile) {

		super(jobName);

		if (buildProfile == null) {
			buildProfile = BuildProfile.PORTAL;
		}

		_buildProfile = buildProfile;

		_portalBranchName = portalBranchName;

		_jenkinsGitWorkingDirectory =
			GitWorkingDirectoryFactory.newJenkinsGitWorkingDirectory();

		_portalGitWorkingDirectory =
			GitWorkingDirectoryFactory.newPortalGitWorkingDirectory(
				portalBranchName);

		jobPropertiesFiles.add(
			new File(
				_portalGitWorkingDirectory.getWorkingDirectory(),
				"test.properties"));
	}

	@Override
	public Set<String> getBatchNames() {
		String testBatchNamesString = JenkinsResultsParserUtil.getProperty(
			getJobProperties(), "test.batch.names[" + _portalBranchName + "]");

		return getSetFromString(testBatchNamesString);
	}

	@Override
	public Set<String> getDependentBatchNames() {
		String testBatchNames = JenkinsResultsParserUtil.getProperty(
			getJobProperties(),
			"test.batch.names.smoke[" + _portalBranchName + "]");

		return getSetFromString(testBatchNames);
	}

	@Override
	public Set<String> getDistTypes() {
		return Collections.emptySet();
	}

	@Override
	public PortalGitWorkingDirectory getPortalGitWorkingDirectory() {
		return _portalGitWorkingDirectory;
	}

	public static enum BuildProfile {

		DXP {

			private static final String _TEXT = "dxp";

			@Override
			public String toString() {
				return _TEXT;
			}

		},
		PORTAL {

			private static final String _TEXT = "portal";

			@Override
			public String toString() {
				return _TEXT;
			}

		}

	}

	protected GitWorkingDirectory getJenkinsGitWorkingDirectory() {
		return _jenkinsGitWorkingDirectory;
	}

	private final BuildProfile _buildProfile;
	private final GitWorkingDirectory _jenkinsGitWorkingDirectory;
	private final String _portalBranchName;
	private final PortalGitWorkingDirectory _portalGitWorkingDirectory;

}