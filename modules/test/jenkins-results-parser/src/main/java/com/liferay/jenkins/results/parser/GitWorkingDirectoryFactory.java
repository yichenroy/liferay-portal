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
import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Michael Hashimoto
 */
public class GitWorkingDirectoryFactory {

	public static GitWorkingDirectory newGitWorkingDirectory(
		String upstreamBranchName, File gitRepositoryDir,
		String gitRepositoryName) {

		if (!gitRepositoryDir.exists()) {
			throw new RuntimeException(
				"Directory path not found " + gitRepositoryDir);
		}

		try {
			String gitRepositoryDirPath = gitRepositoryDir.getCanonicalPath();

			if (_gitWorkingDirectories.containsKey(gitRepositoryDirPath)) {
				return _gitWorkingDirectories.get(gitRepositoryDirPath);
			}

			if (gitRepositoryName == null) {
				gitRepositoryName = gitRepositoryDir.getName();
			}

			GitWorkingDirectory gitWorkingDirectory = null;

			if (gitRepositoryName.startsWith("com-liferay-")) {
				gitWorkingDirectory = new SubrepositoryGitWorkingDirectory(
					upstreamBranchName, gitRepositoryDirPath,
					gitRepositoryName);
			}
			else if (gitRepositoryName.startsWith("liferay-plugins")) {
				gitWorkingDirectory = new PluginsGitWorkingDirectory(
					upstreamBranchName, gitRepositoryDirPath,
					gitRepositoryName);
			}
			else if (gitRepositoryName.startsWith("liferay-portal")) {
				gitWorkingDirectory = new PortalGitWorkingDirectory(
					upstreamBranchName, gitRepositoryDirPath,
					gitRepositoryName);
			}
			else {
				gitWorkingDirectory = new GitWorkingDirectory(
					upstreamBranchName, gitRepositoryDirPath,
					gitRepositoryName);
			}

			_gitWorkingDirectories.put(
				gitRepositoryDirPath, gitWorkingDirectory);

			return _gitWorkingDirectories.get(gitRepositoryDirPath);
		}
		catch (IOException ioe) {
			throw new RuntimeException(
				JenkinsResultsParserUtil.combine(
					"Unable to create Git working directory for directory ",
					gitRepositoryDir.getPath()),
				ioe);
		}
	}

	public static GitWorkingDirectory newGitWorkingDirectory(
		String upstreamBranchName, String gitepositoryDirPath) {

		return newGitWorkingDirectory(
			upstreamBranchName, new File(gitepositoryDirPath), null);
	}

	public static GitWorkingDirectory newGitWorkingDirectory(
		String upstreamBranchName, String gitRepositoryDirPath,
		String gitRepositoryName) {

		return newGitWorkingDirectory(
			upstreamBranchName, new File(gitRepositoryDirPath),
			gitRepositoryName);
	}

	private static final Map<String, GitWorkingDirectory>
		_gitWorkingDirectories = new HashMap<>();

}