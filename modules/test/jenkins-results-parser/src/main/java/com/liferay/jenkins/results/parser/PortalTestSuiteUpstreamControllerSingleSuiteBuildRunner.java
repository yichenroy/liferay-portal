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

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

/**
 * @author Yi-Chen Tsai
 */
public class PortalTestSuiteUpstreamControllerSingleSuiteBuildRunner
	<S extends PortalTestSuiteUpstreamControllerBuildData>
		extends PortalTestSuiteUpstreamControllerBuildRunner<S> {

	@Override
	public void run() {
		retirePreviousBuilds();

		if (_allowConcurrentBuilds()) {
			super.run();

			return;
		}

		S buildData = getBuildData();

		if (_previousBuildHasCurrentSHA()) {
			buildData.setBuildDescription(
				JenkinsResultsParserUtil.combine(
					"<strong>SKIPPED</strong> - <a href=\"https://github.com/",
					"liferay/liferay-portal/commit/",
					buildData.getPortalBranchSHA(), "\">",
					_getPortalBranchAbbreviatedSHA(), "</a> was already ran"));

			super.updateBuildDescription();

			return;
		}

		if (_previousBuildHasExistingInvocation()) {
			buildData.setBuildDescription(
				"<strong>SKIPPED</strong> - Job was already invoked");

			super.updateBuildDescription();

			return;
		}

		if (_previousBuildHasRunningInvocation()) {
			buildData.setBuildDescription(
				"<strong>SKIPPED</strong> - Job is already running");

			super.updateBuildDescription();

			return;
		}

		super.run();
	}

	protected PortalTestSuiteUpstreamControllerSingleSuiteBuildRunner(
		S buildData) {

		super(buildData);
	}

	@Override
	protected void invokeTestSuiteBuilds() {
		String jobURL = getJobURL();

		StringBuilder sb = new StringBuilder();

		sb.append(jobURL);

		sb.append("/buildWithParameters?");

		String jenkinsAuthenticationToken;

		try {
			Properties buildProperties =
				JenkinsResultsParserUtil.getBuildProperties();

			jenkinsAuthenticationToken = buildProperties.getProperty(
				"jenkins.authentication.token");
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		sb.append("token=");
		sb.append(jenkinsAuthenticationToken);

		S buildData = getBuildData();

		sb.append("&CI_TEST_SUITE=");

		String testSuiteName = buildData.getTestSuiteName();

		sb.append(testSuiteName);

		sb.append("&CONTROLLER_BUILD_URL=");
		sb.append(buildData.getBuildURL());
		sb.append("&JENKINS_GITHUB_BRANCH_NAME=");
		sb.append(buildData.getJenkinsGitHubBranchName());
		sb.append("&JENKINS_GITHUB_BRANCH_USERNAME=");
		sb.append(buildData.getJenkinsGitHubUsername());
		sb.append("&PORTAL_GIT_COMMIT=");
		sb.append(buildData.getPortalBranchSHA());

		String portalGitHubCompareURL = _getPortalGitHubCompareURL();

		if (portalGitHubCompareURL != null) {
			sb.append("&PORTAL_GITHUB_COMPARE_URL=");
			sb.append(portalGitHubCompareURL);
		}

		sb.append("&PORTAL_GITHUB_URL=");
		sb.append(buildData.getPortalGitHubURL());

		String testPortalBuildProfile = getTestPortalBuildProfile(
			testSuiteName);

		if (testPortalBuildProfile != null) {
			sb.append("&TEST_PORTAL_BUILD_PROFILE=");
			sb.append(testPortalBuildProfile);
		}

		String testrayProjectName = buildData.getTestrayProjectName();

		if (testrayProjectName != null) {
			sb.append("&TESTRAY_BUILD_NAME=");
			sb.append(buildData.getTestrayBuildName());
			sb.append("&TESTRAY_BUILD_TYPE=");
			sb.append(buildData.getTestrayBuildType());
			sb.append("&TESTRAY_PROJECT_NAME=");
			sb.append(testrayProjectName);
		}

		try {
			JenkinsResultsParserUtil.toString(sb.toString());
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		sb = new StringBuilder();

		sb.append("<a href=\"");
		sb.append(JenkinsResultsParserUtil.getRemoteURL(jobURL));
		sb.append("\"><strong>IN QUEUE</strong></a>");
		sb.append("<ul><li><strong>Git ID:</strong> ");
		sb.append("<a href=\"https://github.com/");
		sb.append(buildData.getPortalGitHubUsername());
		sb.append("/");
		sb.append(buildData.getPortalGitHubRepositoryName());
		sb.append("/commit/");
		sb.append(buildData.getPortalBranchSHA());
		sb.append("\">");
		sb.append(_getPortalBranchAbbreviatedSHA());
		sb.append("</a></li>");

		if (portalGitHubCompareURL != null) {
			sb.append("<li><strong>Git Compare:</strong> <a href=\"");
			sb.append(_getPortalGitHubCompareURL());
			sb.append("\">??? commits</a></li>");
		}

		sb.append("</ul>");

		buildData.setBuildDescription(sb.toString());

		updateBuildDescription();
	}

	private boolean _allowConcurrentBuilds() {
		String allowConcurrentBuildsString = System.getenv(
			"ALLOW_CONCURRENT_BUILDS");

		if (allowConcurrentBuildsString == null) {
			return false;
		}

		allowConcurrentBuildsString = allowConcurrentBuildsString.toLowerCase();
		allowConcurrentBuildsString = allowConcurrentBuildsString.trim();

		if (!allowConcurrentBuildsString.equals("true")) {
			return false;
		}

		return true;
	}

	private String _getPortalBranchAbbreviatedSHA() {
		S buildData = getBuildData();

		String portalBranchSHA = buildData.getPortalBranchSHA();

		return portalBranchSHA.substring(0, 7);
	}

	private String _getPortalGitHubCompareURL() {
		S buildData = getBuildData();

		return buildData.getPortalGitHubCompareURL(
			_getPreviousBuildPortalBranchSHA());
	}

	private String _getPreviousBuildPortalBranchSHA() {
		S buildData = getBuildData();

		String currentPortalBranchSHA = buildData.getPortalBranchSHA();

		for (JSONObject previousBuildJSONObject :
				getPreviousBuildJSONObjects()) {

			String description = previousBuildJSONObject.optString(
				"description", "");

			Matcher matcher = _portalBranchSHAPattern.matcher(description);

			if (!matcher.find()) {
				continue;
			}

			String previousPortalBranchSHA = matcher.group("branchSHA");

			if (currentPortalBranchSHA.equals(previousPortalBranchSHA)) {
				continue;
			}

			return previousPortalBranchSHA;
		}

		return null;
	}

	private boolean _previousBuildHasCurrentSHA() {
		String portalBranchSHA = _getPortalBranchAbbreviatedSHA();

		for (JSONObject previousBuildJSONObject :
				getPreviousBuildJSONObjects()) {

			String description = previousBuildJSONObject.optString(
				"description", "");

			if (description.contains(portalBranchSHA)) {
				return true;
			}
		}

		return false;
	}

	private boolean _previousBuildHasExistingInvocation() {
		for (JSONObject previousBuildJSONObject :
				getPreviousBuildJSONObjects()) {

			String description = previousBuildJSONObject.optString(
				"description", "");

			if (description.contains("IN QUEUE")) {
				return true;
			}
		}

		return false;
	}

	private boolean _previousBuildHasRunningInvocation() {
		for (JSONObject previousBuildJSONObject :
				getPreviousBuildJSONObjects()) {

			String description = previousBuildJSONObject.optString(
				"description", "");

			if (!description.contains("IN PROGRESS")) {
				continue;
			}

			Matcher buildURLMatcher = _buildURLPattern.matcher(description);

			if (!buildURLMatcher.find()) {
				continue;
			}

			String buildURL = buildURLMatcher.group("buildURL");

			try {
				JSONObject jsonObject = JenkinsResultsParserUtil.toJSONObject(
					JenkinsResultsParserUtil.getLocalURL(
						buildURL + "/api/json?tree=result"));

				Object result = jsonObject.get("result");

				if (result.equals(JSONObject.NULL)) {
					return true;
				}

				JSONObject injectedEnvVarsJSONObject =
					JenkinsResultsParserUtil.toJSONObject(
						JenkinsResultsParserUtil.getLocalURL(
							previousBuildJSONObject.getString("url") +
								"/injectedEnvVars/api/json"));

				JSONObject envMapJSONObject =
					injectedEnvVarsJSONObject.getJSONObject("envMap");

				StringBuilder sb = new StringBuilder();

				sb.append("<strong style=\"color: red\">FAILURE</strong> - ");
				sb.append(buildURLMatcher.group());

				Matcher portalBranchSHAMatcher =
					_portalBranchSHAPattern.matcher(description);
				Matcher portalGitHubCompareURLMatcher =
					_portalGitHubCompareURLPattern.matcher(description);

				if (portalBranchSHAMatcher.find() ||
					portalGitHubCompareURLMatcher.find()) {

					sb.append("<ul>");

					if (portalBranchSHAMatcher.find()) {
						sb.append("<li>");
						sb.append(portalBranchSHAMatcher.group());
						sb.append("</li>");
					}

					if (portalGitHubCompareURLMatcher.find()) {
						sb.append("<li>");
						sb.append(portalGitHubCompareURLMatcher.group());
						sb.append("</li>");
					}

					sb.append("</ul>");
				}

				JenkinsResultsParserUtil.updateBuildDescription(
					sb.toString(),
					Integer.valueOf(envMapJSONObject.getString("BUILD_NUMBER")),
					envMapJSONObject.getString("JOB_NAME"),
					envMapJSONObject.getString("HOSTNAME"));
			}
			catch (IOException ioException) {
				throw new RuntimeException(ioException);
			}
		}

		return false;
	}

	private static final Pattern _buildURLPattern = Pattern.compile(
		"<a href=\"(?<buildURL>[^\"]+)\">Build URL</a>");
	private static final Pattern _portalBranchSHAPattern = Pattern.compile(
		"<strong>Git ID:</strong> <a href=\"https://github.com/[^/]+/[^/]+/" +
			"commit/(?<branchSHA>[0-9a-f]{40})\">[0-9a-f]{7}</a>");
	private static final Pattern _portalGitHubCompareURLPattern =
		Pattern.compile(
			"<strong>Git Compare:</strong> <a href=\"[^\"]+\">[^<]+</a>");

}