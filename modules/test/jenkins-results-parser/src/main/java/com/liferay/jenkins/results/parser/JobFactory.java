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

	public static Job newJob(Build build) {
		TopLevelBuild topLevelBuild = build.getTopLevelBuild();

		String topLevelJobName = topLevelBuild.getJobName();

		Map<String, String> buildParameters = topLevelBuild.getParameters();

		String testSuiteName;

		if (topLevelJobName.equals("test-plugins-release") ||
			topLevelJobName.equals("test-plugins-upstream")) {

			testSuiteName = buildParameters.get("TEST_PLUGIN_NAME");
		}
		else if (topLevelJobName.equals("test-qa-websites-functional-daily") ||
				 topLevelJobName.equals(
					 "test-qa-websites-functional-environment")) {

			testSuiteName = buildParameters.get("PROJECT_NAMES");
		}
		else {
			testSuiteName = topLevelBuild.getTestSuiteName();
		}

		String branchName = topLevelBuild.getBranchName();

		String repositoryName = null;

		if (topLevelJobName.contains("subrepository")) {
			repositoryName = topLevelBuild.getBaseGitRepositoryName();
		}

		String buildProfile;

		if (topLevelJobName.contains("aws") ||
			topLevelJobName.contains("environment")) {

			if (!branchName.startsWith("ee-")) {
				buildProfile = "dxp";
			}
			else {
				buildProfile = "portal";
			}
		}
		else {
			buildProfile = buildParameters.get("TEST_PORTAL_BUILD_PROFILE");

			if ((buildProfile == null) || !buildProfile.equals("dxp")) {
				buildProfile = "portal";
			}

			if (branchName.startsWith("ee-")) {
				buildProfile = "portal";
			}
		}

		return _newJob(
			topLevelJobName, testSuiteName, branchName, repositoryName,
			Job.BuildProfile.valueOf(buildProfile.toUpperCase()));
	}

	public static Job newJob(BuildData buildData) {
		String upstreamBranchName = null;

		if (buildData instanceof PortalBuildData) {
			PortalBuildData portalBuildData = (PortalBuildData)buildData;

			upstreamBranchName = portalBuildData.getPortalUpstreamBranchName();
		}

		return _newJob(
			buildData.getJobName(), null, upstreamBranchName, null, null);
	}

	public static Job newJob(String jobName) {
		return _newJob(jobName, null, null, null, null);
	}

	public static Job newJob(String jobName, String testSuiteName) {
		return _newJob(jobName, testSuiteName, null, null, null);
	}

	public static Job newJob(
		String jobName, String testSuiteName, String branchName) {

		return _newJob(jobName, testSuiteName, branchName, null, null);
	}

	public static Job newJob(
		String jobName, String testSuiteName, String branchName,
		String repositoryName) {

		return _newJob(
			jobName, testSuiteName, branchName, repositoryName, null);
	}

	public static Job newJob(
		String jobName, String testSuiteName, String branchName,
		String repositoryName, Job.BuildProfile buildProfile) {

		return _newJob(
			jobName, testSuiteName, branchName, repositoryName, buildProfile);
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
		String jobName, String testSuiteName, String branchName,
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
					jobName, buildProfile, branchName));

			return _jobs.get(jobKey);
		}

		if (jobName.equals("root-cause-analysis-tool-batch")) {
			_jobs.put(
				jobKey,
				new RootCauseAnalysisToolBatchJob(
					jobName, buildProfile, branchName));

			return _jobs.get(jobKey);
		}

		if (jobName.startsWith("test-plugins-acceptance-pullrequest(")) {
			PluginsAcceptancePullRequestJob pluginsAcceptancePullRequestJob =
				new PluginsAcceptancePullRequestJob(
					jobName, buildProfile, branchName);

			_jobs.put(jobKey, pluginsAcceptancePullRequestJob);

			return pluginsAcceptancePullRequestJob;
		}

		if (jobName.equals("test-plugins-extraapps")) {
			PluginsExtraAppsJob pluginsExtraAppsJob = new PluginsExtraAppsJob(
				jobName, buildProfile, branchName);

			_jobs.put(jobKey, pluginsExtraAppsJob);

			return pluginsExtraAppsJob;
		}

		if (jobName.equals("test-plugins-marketplaceapp")) {
			PluginsMarketplaceAppJob pluginsMarketplaceAppJob =
				new PluginsMarketplaceAppJob(
					jobName, testSuiteName, buildProfile, branchName);

			_jobs.put(jobKey, pluginsMarketplaceAppJob);

			return pluginsMarketplaceAppJob;
		}

		if (jobName.equals("test-plugins-release")) {
			PluginsReleaseJob pluginsReleaseJob = new PluginsReleaseJob(
				jobName, testSuiteName, buildProfile, branchName);

			_jobs.put(jobKey, pluginsReleaseJob);

			return pluginsReleaseJob;
		}

		if (jobName.equals("test-plugins-upstream")) {
			PluginsUpstreamJob pluginsUpstreamJob = new PluginsUpstreamJob(
				jobName, testSuiteName, buildProfile, branchName);

			_jobs.put(jobKey, pluginsUpstreamJob);

			return pluginsUpstreamJob;
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

		if (jobName.equals("test-portal-app-release")) {
			_jobs.put(
				jobKey,
				new PortalAppReleaseJob(jobName, buildProfile, branchName));

			return _jobs.get(jobKey);
		}

		if (jobName.startsWith("test-portal-aws(")) {
			_jobs.put(
				jobKey, new PortalAWSJob(jobName, buildProfile, branchName));

			return _jobs.get(jobKey);
		}

		if (jobName.startsWith("test-portal-environment(")) {
			_jobs.put(
				jobKey,
				new PortalEnvironmentJob(jobName, buildProfile, branchName));

			return _jobs.get(jobKey);
		}

		if (jobName.startsWith("test-portal-environment-release(")) {
			_jobs.put(
				jobKey,
				new PortalReleaseEnvironmentJob(
					jobName, buildProfile, branchName));

			return _jobs.get(jobKey);
		}

		if (jobName.startsWith("test-portal-fixpack-environment(")) {
			_jobs.put(
				jobKey,
				new PortalFixpackEnvironmentJob(
					jobName, buildProfile, branchName));

			return _jobs.get(jobKey);
		}

		if (jobName.equals("test-portal-fixpack-release")) {
			_jobs.put(
				jobKey,
				new PortalFixpackReleaseJob(
					jobName, buildProfile, branchName, testSuiteName));

			return _jobs.get(jobKey);
		}

		if (jobName.equals("test-portal-hotfix-release")) {
			_jobs.put(
				jobKey,
				new PortalHotfixReleaseJob(
					jobName, buildProfile, branchName, testSuiteName));

			return _jobs.get(jobKey);
		}

		if (jobName.equals("test-portal-release")) {
			_jobs.put(
				jobKey,
				new PortalReleaseJob(
					jobName, buildProfile, branchName, testSuiteName));

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

		if (jobName.equals("test-qa-websites-functional-daily") ||
			jobName.equals("test-qa-websites-functional-environment")) {

			_jobs.put(
				jobKey,
				new QAWebsitesGitRepositoryJob(
					jobName, buildProfile, testSuiteName, branchName));

			return _jobs.get(jobKey);
		}

		if (jobName.equals("test-results-consistency-report-controller")) {
			_jobs.put(jobKey, new SimpleJob(jobName, buildProfile));

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