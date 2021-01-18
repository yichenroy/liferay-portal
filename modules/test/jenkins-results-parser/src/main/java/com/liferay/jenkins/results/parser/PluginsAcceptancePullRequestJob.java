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

import java.util.ArrayList;
import java.util.List;

/**
 * @author Michael Hashimoto
 */
public class PluginsAcceptancePullRequestJob extends PluginsGitRepositoryJob {

	@Override
	public List<File> getPluginsTestBaseDirs() {
		return _pluginsTestBaseDirs;
	}

	protected PluginsAcceptancePullRequestJob(
		String jobName, BuildProfile buildProfile, String branchName) {

		super(jobName, buildProfile, branchName);

		_pluginsTestBaseDirs = _getPluginsTestBaseDirs();

		for (File pluginsTestBaseDir : _pluginsTestBaseDirs) {
			File testPropertiesFile = new File(
				pluginsTestBaseDir, "test.properties");

			if (testPropertiesFile.exists()) {
				jobPropertiesFiles.add(testPropertiesFile);
			}
		}

		readJobProperties();
	}

	private List<File> _getPluginsTestBaseDirs() {
		List<File> pluginsTestBaseDirs = new ArrayList<>();

		PluginsGitWorkingDirectory pluginsGitWorkingDirectory =
			portalGitWorkingDirectory.getPluginsGitWorkingDirectory();

		List<File> modifiedFilesList =
			pluginsGitWorkingDirectory.getModifiedFilesList();

		for (File modifiedFile : modifiedFilesList) {
			File parentDir = new File(modifiedFile.getPath());

			while (parentDir != null) {
				File testBaseDir = new File(parentDir, "test/functional");

				if (testBaseDir.exists()) {
					pluginsTestBaseDirs.add(testBaseDir);

					break;
				}

				parentDir = parentDir.getParentFile();
			}
		}

		return pluginsTestBaseDirs;
	}

	private final List<File> _pluginsTestBaseDirs;

}