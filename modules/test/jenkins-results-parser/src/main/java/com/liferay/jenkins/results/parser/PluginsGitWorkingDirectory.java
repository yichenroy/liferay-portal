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

import java.io.IOException;

/**
 * @author Michael Hashimoto
 * @author Peter Yoo
 */
public class PluginsGitWorkingDirectory extends GitWorkingDirectory {

	protected PluginsGitWorkingDirectory(
			String portalUpstreamBranchName, String workingDirectoryPath)
		throws IOException {

		super(
			_getPluginsUpstreamBranchName(portalUpstreamBranchName),
			workingDirectoryPath);
	}

	protected PluginsGitWorkingDirectory(
			String portalUpstreamBranchName, String workingDirectoryPath,
			String gitRepositoryName)
		throws IOException {

		super(
			_getPluginsUpstreamBranchName(portalUpstreamBranchName),
			workingDirectoryPath, gitRepositoryName);
	}

	private static String _getPluginsUpstreamBranchName(
		String portalUpstreamBranchName) {

		if (portalUpstreamBranchName.contains("7.0.x") ||
			portalUpstreamBranchName.contains("7.1.x") ||
			portalUpstreamBranchName.contains("7.2.x") ||
			portalUpstreamBranchName.contains("master")) {

			return "7.0.x";
		}

		return portalUpstreamBranchName;
	}

}