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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Michael Hashimoto
 */
public class JobFactory {

	public static Job newJob(BuildData buildData) {
		String portalUpstreamBranchName = null;

		if (buildData instanceof PortalBuildData) {
			PortalBuildData portalBuildData = (PortalBuildData)buildData;

			portalUpstreamBranchName =
				portalBuildData.getPortalUpstreamBranchName();
		}

		return _newJob(
			buildData.getJobName(), null, portalUpstreamBranchName, null, null);
	}

	public static Job newJob(String jobName) {
		return _newJob(jobName, null, null, null, null);
	}

	public static Job newJob(String jobName, String testSuiteName) {
		return _newJob(jobName, testSuiteName, null, null, null);
	}

	public static Job newJob(
		String jobName, String testSuiteName, String portalBranchName) {

		return _newJob(jobName, testSuiteName, portalBranchName, null, null);
	}

	public static Job newJob(
		String jobName, String testSuiteName, String portalBranchName,
		String repositoryName) {

		return _newJob(
			jobName, testSuiteName, portalBranchName, repositoryName, null);
	}

	public static Job newJob(
		String jobName, String testSuiteName, String portalBranchName,
		String repositoryName, Job.BuildProfile buildProfile) {

		return _newJob(
			jobName, testSuiteName, portalBranchName, repositoryName,
			buildProfile);
	}

	private static boolean _isCentralMergePullRequest(
		GitWorkingDirectory gitWorkingDirectory) {

		List<File> currentBranchModifiedFiles =
			gitWorkingDirectory.getModifiedFilesList();

		if (currentBranchModifiedFiles.size() == 1) {
			File modifiedFile = currentBranchModifiedFiles.get(0);

			String modifiedFileName = modifiedFile.getName();

			if (modifiedFileName.equals("ci-merge")) {
				return true;
			}
		}

		return false;
	}

	private static Job _newJob(
		String jobName, String testSuiteName, String portalBranchName,
		String repositoryName, Job.BuildProfile buildProfile) {

		if (buildProfile == null) {
			buildProfile = Job.BuildProfile.PORTAL;
		}

		String jobKey = JenkinsResultsParserUtil.combine(
			jobName, "-", buildProfile.toString());

		if ((testSuiteName != null) && !testSuiteName.isEmpty()) {
			jobKey = JenkinsResultsParserUtil.combine(
				jobName, "-", testSuiteName);
		}

		Job job = _jobs.get(jobKey);

		if (job != null) {
			return job;
		}

		if (jobName.equals("js-test-csv-report") ||
			jobName.equals("junit-test-csv-report")) {

			PortalGitRepositoryJob portalGitRepositoryJob =
				new PortalGitRepositoryJob(jobName, buildProfile) {

					@Override
					protected GitWorkingDirectory getNewGitWorkingDirectory() {
						return GitWorkingDirectoryFactory.
							newGitWorkingDirectory(
								getBranchName(),
								System.getProperty("user.dir"));
					}

				};

			_jobs.put(jobKey, portalGitRepositoryJob);

			return _jobs.get(jobKey);
		}

		if (jobName.equals("root-cause-analysis-tool")) {
			_jobs.put(
				jobKey,
				new RootCauseAnalysisToolJob(
					jobName, buildProfile, portalBranchName));

			return _jobs.get(jobKey);
		}

		if (jobName.equals("root-cause-analysis-tool-batch")) {
			_jobs.put(
				jobKey,
				new RootCauseAnalysisToolBatchJob(
					jobName, buildProfile, portalBranchName));

			return _jobs.get(jobKey);
		}

		if (jobName.equals("test-results-consistency-report-controller")) {
			_jobs.put(jobKey, new SimpleJob(jobName, buildProfile));

			return _jobs.get(jobKey);
		}

		if (jobName.startsWith("test-plugins-acceptance-pullrequest(")) {
			PluginsGitRepositoryJob pluginsGitRepositoryJob =
				new PluginsGitRepositoryJob(jobName, buildProfile);

			_jobs.put(jobKey, pluginsGitRepositoryJob);

			return pluginsGitRepositoryJob;
		}

		if (jobName.startsWith("test-portal-acceptance-pullrequest(")) {
			PortalAcceptancePullRequestJob portalAcceptancePullRequestJob =
				new PortalAcceptancePullRequestJob(
					jobName, buildProfile, testSuiteName);

			if (_isCentralMergePullRequest(
					portalAcceptancePullRequestJob.getGitWorkingDirectory())) {

				portalAcceptancePullRequestJob = new CentralMergePullRequestJob(
					jobName, buildProfile);
			}

			_jobs.put(jobKey, portalAcceptancePullRequestJob);

			return portalAcceptancePullRequestJob;
		}

		if (jobName.startsWith("test-portal-acceptance-upstream")) {
			_jobs.put(
				jobKey,
				new PortalAcceptanceUpstreamJob(
					jobName, buildProfile, testSuiteName));

			return _jobs.get(jobKey);
		}

		if (jobName.equals("test-portal-fixpack-release")) {
			_jobs.put(
				jobKey,
				new PortalFixpackReleaseJob(
					jobName, buildProfile, portalBranchName, testSuiteName));

			return _jobs.get(jobKey);
		}

		if (jobName.equals("test-portal-hotfix-release")) {
			_jobs.put(
				jobKey,
				new PortalHotfixReleaseJob(
					jobName, buildProfile, portalBranchName, testSuiteName));

			return _jobs.get(jobKey);
		}

		if (jobName.equals("test-portal-release")) {
			_jobs.put(
				jobKey,
				new PortalReleaseJob(
					jobName, buildProfile, portalBranchName, testSuiteName));

			return _jobs.get(jobKey);
		}

		if (jobName.startsWith("test-portal-testsuite-upstream(")) {
			_jobs.put(
				jobKey,
				new PortalTestSuiteUpstreamJob(
					jobName, buildProfile, testSuiteName));

			return _jobs.get(jobKey);
		}

		if (jobName.startsWith("test-portal-testsuite-upstream-controller(")) {
			_jobs.put(jobKey, new SimpleJob(jobName, buildProfile));

			return _jobs.get(jobKey);
		}

		if (jobName.startsWith("test-portal-upstream(")) {
			_jobs.put(jobKey, new PortalUpstreamJob(jobName, buildProfile));

			return _jobs.get(jobKey);
		}

		if (jobName.startsWith("test-subrepository-acceptance-pullrequest(")) {
			_jobs.put(
				jobKey,
				new SubrepositoryAcceptancePullRequestJob(
					jobName, buildProfile, testSuiteName, repositoryName));

			return _jobs.get(jobKey);
		}

		_jobs.put(
			jobKey, new DefaultPortalJob(jobName, buildProfile, testSuiteName));

		return _jobs.get(jobKey);
	}

	private static final Map<String, Job> _jobs = new HashMap<>();

}