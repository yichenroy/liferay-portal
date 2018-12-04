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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * @author Peter Yoo
 */
public class AutoCloseUtil {

	public static boolean debug = false;

	public static void autoClose(PullRequest pullRequest, Build build)
		throws Exception {

		pullRequest.close();

		StringBuilder sb = new StringBuilder();

		sb.append("<h1>The pull request tester is still running.</h1>");
		sb.append("<p>Please wait until you get the ");
		sb.append("<i><b>final report</b></i> before running 'ci:retest'.");
		sb.append("</p><p>See this link to check on the status of your ");
		sb.append("test:</p>");

		sb.append("<ul><li><a href=\"");
		sb.append(build.getBuildURL());
		sb.append("\">");
		sb.append(build.getJobName());
		sb.append("</a></li></ul><p>@");
		sb.append(pullRequest.getSenderUsername());
		sb.append("</p><hr />");

		sb.append("<h1>However, the pull request was closed.</h1>");
		sb.append("<p>The pull request was closed because the following ");
		sb.append("critical batches had failed:</p><ul>");

		String buildURL = build.getBuildURL();

		sb.append("<li><a href=\"");
		sb.append(buildURL);
		sb.append("\">");

		String jobVariant = build.getJobVariant();

		if ((jobVariant != null) && !jobVariant.isEmpty()) {
			sb.append(jobVariant);
		}
		else {
			sb.append(build.getJobName());
		}

		sb.append("</a></li></ul><p>For information as to why we ");
		sb.append("automatically close out certain pull requests see this ");
		sb.append("<a href=\"https://in.liferay.com/web/global.");
		sb.append("engineering/wiki/-/wiki/Quality+Assurance+Main/Test");
		sb.append("+Batch+Automatic+Close+List\">article</a>.</p><p");

		boolean sourceFormatBuild =
			build instanceof SourceFormatBuild;

		if (sourceFormatBuild) {
			sb.append("><strong><em>*");
		}
		else {
			sb.append(" auto-close=\"false\"><strong><em>*This pull will ");
			sb.append("no longer automatically close if this comment is ");
			sb.append("available. ");
		}

		sb.append("If you believe this is a mistake please reopen this ");
		sb.append("pull by entering the following command as a comment.");
		sb.append("</em></strong><pre>ci&#58;reopen</pre></p>");

		if (sourceFormatBuild) {
			sb.append("<strong><em>*The reopened pull request may ");
			sb.append("be automatically closed again if other critical ");
			sb.append("batches or tests fail.</em></strong>");
		}

		sb.append("<hr /><h3>Critical Failure Details:</h3>");

		try {
			sb.append(Dom4JUtil.format(build.getGitHubMessageElement(), false));
		}
		catch (Exception e) {
			e.printStackTrace();

			throw e;
		}

		if (!_autoCloseGitHubCommentMentionUsernames.isEmpty()) {
			sb.append("<div>cc");

			for (String autoCloseGitHubCommentMentionUsername :
					_autoCloseGitHubCommentMentionUsernames) {

				sb.append(" @");
				sb.append(autoCloseGitHubCommentMentionUsername);
			}

			sb.append("</div>");
		}

		pullRequest.addComment(sb.toString());
	}

	public static boolean autoCloseOnCriticalBatchFailures(
			PullRequest pullRequest, Build topLevelBuild)
		throws Exception {

		if (pullRequest.isAutoCloseCommentAvailable()) {
			return false;
		}

		String gitHubReceiverUsername = pullRequest.getOwnerUsername();
		String gitHubSenderUsername = pullRequest.getSenderUsername();

		if ((gitHubReceiverUsername == null) ||
			(gitHubSenderUsername == null) ||
			!_autoCloseReceiverUsernames.contains(gitHubReceiverUsername) ||
			gitHubReceiverUsername.equals(gitHubSenderUsername)) {

			return false;
		}

		List<AutoCloseRule> autoCloseRules = getAutoCloseRules(pullRequest);

		for (AutoCloseRule autoCloseRule : autoCloseRules) {
			List<Build> downstreamBuilds = topLevelBuild.getDownstreamBuilds(
				null);

			if (downstreamBuilds.isEmpty()) {
				downstreamBuilds = new ArrayList<>();

				downstreamBuilds.add(topLevelBuild);
			}

			List<Build> failedDownstreamBuilds = autoCloseRule.evaluate(
				downstreamBuilds);

			if (failedDownstreamBuilds.isEmpty()) {
				continue;
			}

			pullRequest.close();

			StringBuilder sb = new StringBuilder();

			sb.append("<h1>The pull request tester is still running.</h1>");
			sb.append("<p>Please wait until you get the ");
			sb.append("<i><b>final report</b></i> before running 'ci:retest'.");
			sb.append("</p><p>See this link to check on the status of your ");
			sb.append("test:</p>");

			sb.append("<ul><li><a href=\"");
			sb.append(topLevelBuild.getBuildURL());
			sb.append("\">");
			sb.append(topLevelBuild.getJobName());
			sb.append("</a></li></ul><p>@");
			sb.append(gitHubSenderUsername);
			sb.append("</p><hr />");

			sb.append("<h1>However, the pull request was closed.</h1>");
			sb.append("<p>The pull request was closed because the following ");
			sb.append("critical batches had failed:</p><ul>");

			String failureBuildURL = "";

			for (Build failedDownstreamBuild : failedDownstreamBuilds) {
				failureBuildURL = failedDownstreamBuild.getBuildURL();

				sb.append("<li><a href=\"");
				sb.append(failureBuildURL);
				sb.append("\">");

				String jobVariant = failedDownstreamBuild.getJobVariant();

				if ((jobVariant != null) && !jobVariant.isEmpty()) {
					sb.append(jobVariant);
				}
				else {
					sb.append(failedDownstreamBuild.getJobName());
				}

				sb.append("</a></li>");
			}

			sb.append("</ul><p>For information as to why we automatically ");
			sb.append("close out certain pull requests see this ");
			sb.append("<a href=\"https://in.liferay.com/web/global.");
			sb.append("engineering/wiki/-/wiki/Quality+Assurance+Main/Test");
			sb.append("+Batch+Automatic+Close+List\">article</a>.</p><p");

			boolean sourceFormatBuild =
				topLevelBuild instanceof SourceFormatBuild;

			if (sourceFormatBuild) {
				sb.append("><strong><em>*");
			}
			else {
				sb.append(" auto-close=\"false\"><strong><em>*This pull will ");
				sb.append("no longer automatically close if this comment is ");
				sb.append("available. ");
			}

			sb.append("If you believe this is a mistake please reopen this ");
			sb.append("pull by entering the following command as a comment.");
			sb.append("</em></strong><pre>ci&#58;reopen</pre></p>");

			if (sourceFormatBuild) {
				sb.append("<strong><em>*The reopened pull request may ");
				sb.append("be automatically closed again if other critical ");
				sb.append("batches or tests fail.</em></strong>");
			}

			sb.append("<hr /><h3>Critical Failure Details:</h3>");

			for (Build failedDownstreamBuild : failedDownstreamBuilds) {
				try {
					sb.append(
						Dom4JUtil.format(
							failedDownstreamBuild.getGitHubMessageElement(),
							false));
				}
				catch (Exception e) {
					e.printStackTrace();

					throw e;
				}
			}

			if (!_autoCloseGitHubCommentMentionUsernames.isEmpty()) {
				sb.append("<div>cc");

				for (String autoCloseGitHubCommentMentionUsername :
						_autoCloseGitHubCommentMentionUsernames) {

					sb.append(" @");
					sb.append(autoCloseGitHubCommentMentionUsername);
				}

				sb.append("</div>");
			}

			pullRequest.addComment(sb.toString());

			return true;
		}

		return false;
	}

	public static boolean autoCloseOnCriticalTestFailures(
			PullRequest pullRequest, Build topLevelBuild)
		throws Exception {

		if (pullRequest.isAutoCloseCommentAvailable() ||
			!isAutoCloseOnCriticalTestFailuresActive(pullRequest)) {

			return false;
		}

		String gitHubReceiverUsername = pullRequest.getOwnerUsername();
		String gitHubSenderUsername = pullRequest.getSenderUsername();

		if ((gitHubReceiverUsername == null) ||
			(gitHubSenderUsername == null) ||
			!_autoCloseReceiverUsernames.contains(gitHubReceiverUsername) ||
			gitHubReceiverUsername.equals(gitHubSenderUsername)) {

			return false;
		}

		Build failedDownstreamBuild = null;
		List<String> jenkinsJobFailureURLs = new ArrayList<>();

		List<Build> downstreamBuilds = topLevelBuild.getDownstreamBuilds(null);

		Properties localLiferayJenkinsEEBuildProperties =
			JenkinsResultsParserUtil.getLocalLiferayJenkinsEEBuildProperties();

		for (Build downstreamBuild : downstreamBuilds) {
			String batchName = downstreamBuild.getJobVariant();

			if (batchName == null) {
				continue;
			}

			if (!batchName.contains("integration") &&
				!batchName.contains("unit")) {

				continue;
			}

			String status = downstreamBuild.getStatus();

			if (!status.equals("completed")) {
				continue;
			}

			String result = downstreamBuild.getResult();

			if ((result == null) || !result.equals("UNSTABLE")) {
				continue;
			}

			String gitSubrepositoryPackageNames =
				JenkinsResultsParserUtil.getProperty(
					localLiferayJenkinsEEBuildProperties,
					"subrepository.package.names");

			if (gitSubrepositoryPackageNames != null) {
				for (String gitSubrepositoryPackageName :
						gitSubrepositoryPackageNames.split(",")) {

					if (!jenkinsJobFailureURLs.isEmpty()) {
						break;
					}

					List<TestResult> testResults = new ArrayList<>();

					testResults.addAll(
						downstreamBuild.getTestResults("FAILED"));
					testResults.addAll(
						downstreamBuild.getTestResults("REGRESSION"));

					for (TestResult testResult : testResults) {
						if (UpstreamFailureUtil.isTestFailingInUpstreamJob(
								testResult)) {

							continue;
						}

						String packageName = testResult.getPackageName();

						if (gitSubrepositoryPackageName.equals(packageName)) {
							failedDownstreamBuild = downstreamBuild;

							StringBuilder sb = new StringBuilder();

							sb.append("<a href=\"");
							sb.append(testResult.getTestReportURL());
							sb.append("\">");
							sb.append(testResult.getClassName());
							sb.append("</a>");

							jenkinsJobFailureURLs.add(sb.toString());
						}
					}
				}
			}
		}

		if (!jenkinsJobFailureURLs.isEmpty()) {
			pullRequest.close();

			StringBuilder sb = new StringBuilder();

			sb.append("<h1>The pull request tester is still running.</h1>");
			sb.append("<p>Please wait until you get the <i><b>final report");
			sb.append("</b></i> before running 'ci:retest'.</p><p>See this ");
			sb.append("link to check on the status of your test:</p>");

			sb.append("<ul><li><a href=\"");
			sb.append(topLevelBuild.getBuildURL());
			sb.append("\">");
			sb.append(topLevelBuild.getJobName());
			sb.append("</a></li></ul>@");
			sb.append(gitHubSenderUsername);
			sb.append("</p><hr />");

			sb.append("<h1>However, the pull request was closed.</h1>");
			sb.append("<p>The pull request was closed due to the following ");
			sb.append("integration/unit test failures:</p><ul>");

			for (String jenkinsJobFailureURL : jenkinsJobFailureURLs) {
				sb.append("<li>");
				sb.append(jenkinsJobFailureURL);
				sb.append("</li>");
			};

			sb.append("</ul><p>These test failures are a part of a ");
			sb.append("'module group'/'subrepository' that was changed in ");
			sb.append("this pull request.</p>");
			sb.append("<p auto-close=\"false\"><strong><em>*This pull will ");
			sb.append("no longer automatically close if this comment is ");
			sb.append("available. If you believe this is a mistake please ");
			sb.append("reopen this pull by entering the following command ");
			sb.append("as a comment.</em></strong></p><pre>ci&#58;reopen");
			sb.append("</pre><hr /><h3>Critical Failure Details:</h3>");

			try {
				sb.append(
					Dom4JUtil.format(
						failedDownstreamBuild.getGitHubMessageElement(),
						false));
			}
			catch (Exception e) {
				e.printStackTrace();

				throw e;
			}

			if (!_autoCloseGitHubCommentMentionUsernames.isEmpty()) {
				sb.append("<div>cc");

				for (String autoCloseGithubCommentMentionUsername :
						_autoCloseGitHubCommentMentionUsernames) {

					sb.append(" @");
					sb.append(autoCloseGithubCommentMentionUsername);
				}

				sb.append("</div>");
			}

			pullRequest.addComment(sb.toString());

			return true;
		}

		return false;
	}

	public static List<AutoCloseRule> getAutoCloseRules(PullRequest pullRequest)
		throws Exception {

		List<AutoCloseRule> list = new ArrayList<>();

		String propertyNameTemplate = JenkinsResultsParserUtil.combine(
			"test.batch.names.auto.close[",
			pullRequest.getGitHubRemoteGitRepositoryName(), "?]");

		String gitRepositoryBranchAutoClosePropertyName =
			propertyNameTemplate.replace(
				"?", "-" + pullRequest.getUpstreamBranchName());

		Properties localLiferayJenkinsEEBuildProperties =
			JenkinsResultsParserUtil.getLocalLiferayJenkinsEEBuildProperties();

		String testBatchNamesAutoClose = JenkinsResultsParserUtil.getProperty(
			localLiferayJenkinsEEBuildProperties,
			gitRepositoryBranchAutoClosePropertyName);

		if (testBatchNamesAutoClose == null) {
			String gitRepositoryAutoClosePropertyName =
				propertyNameTemplate.replace("?", "");

			testBatchNamesAutoClose = JenkinsResultsParserUtil.getProperty(
				localLiferayJenkinsEEBuildProperties,
				gitRepositoryAutoClosePropertyName);
		}

		if (testBatchNamesAutoClose != null) {
			if (debug) {
				System.out.println(
					JenkinsResultsParserUtil.combine(
						"Finding auto-close rules for ",
						gitRepositoryBranchAutoClosePropertyName, "."));
			}

			String[] autoCloseRuleDataArray = StringUtils.split(
				testBatchNamesAutoClose, ",");

			for (String autoCloseRuleData : autoCloseRuleDataArray) {
				if (autoCloseRuleData.startsWith("#")) {
					continue;
				}

				AutoCloseRule newAutoCloseRule = new AutoCloseRule(
					autoCloseRuleData);

				if (debug) {
					System.out.println("\t" + newAutoCloseRule.toString());
				}

				list.add(newAutoCloseRule);
			}

			if (debug) {
				System.out.println(
					JenkinsResultsParserUtil.combine(
						"Finished finding ",
						gitRepositoryBranchAutoClosePropertyName,
						" auto-close rules.\n"));
			}
		}

		return list;
	}

	public static boolean isAutoCloseBranch(PullRequest pullRequest) {
		String gitHubRemoteGitRepositoryName =
			pullRequest.getGitHubRemoteGitRepositoryName();

		Properties localLiferayJenkinsEEBuildProperties =
			JenkinsResultsParserUtil.getLocalLiferayJenkinsEEBuildProperties();

		String testBranchNamesAutoClose = JenkinsResultsParserUtil.getProperty(
			localLiferayJenkinsEEBuildProperties,
			JenkinsResultsParserUtil.combine(
				"test.branch.names.auto.close[", gitHubRemoteGitRepositoryName,
				"]"));

		String branchName = pullRequest.getUpstreamBranchName();

		if (testBranchNamesAutoClose == null) {
			if (debug) {
				System.out.println(
					JenkinsResultsParserUtil.combine(
						"Auto-close rules are deactivated for ",
						gitHubRemoteGitRepositoryName, "(", branchName, ")."));
			}

			return false;
		}

		List<String> testBranchNamesAutoCloseList = Arrays.asList(
			testBranchNamesAutoClose.split(","));

		return testBranchNamesAutoCloseList.contains(branchName);
	}

	public static boolean isAutoCloseOnCriticalTestFailuresActive(
		PullRequest pullRequest) {

		Properties localLiferayJenkinsEEBuildProperties =
			JenkinsResultsParserUtil.getLocalLiferayJenkinsEEBuildProperties();

		String criticalTestBranchesString =
			JenkinsResultsParserUtil.getProperty(
				localLiferayJenkinsEEBuildProperties,
				JenkinsResultsParserUtil.combine(
					"test.branch.names.critical.test[",
					pullRequest.getGitHubRemoteGitRepositoryName(), "]"));

		if ((criticalTestBranchesString == null) ||
			criticalTestBranchesString.isEmpty()) {

			return false;
		}

		String[] criticalTestBranches = StringUtils.split(
			criticalTestBranchesString, ",");

		for (String criticalTestBranch : criticalTestBranches) {
			if (criticalTestBranch.equals(
					pullRequest.getUpstreamBranchName())) {

				return true;
			}
		}

		return false;
	}

	private static List<String> _getBuildPropertyAsList(String propertyName) {
		try {
			return JenkinsResultsParserUtil.getBuildPropertyAsList(
				propertyName);
		}
		catch (IOException ioe) {
			throw new RuntimeException(
				"Unable to get property " + propertyName, ioe);
		}
	}

	private static final List<String> _autoCloseGitHubCommentMentionUsernames =
		_getBuildPropertyAsList("auto.close.github.comment.mention.usernames");
	private static final List<String> _autoCloseReceiverUsernames =
		_getBuildPropertyAsList("auto.close.receiver.usernames");

	private static class AutoCloseRule {

		public AutoCloseRule(String ruleData) {
			this.ruleData = ruleData;

			String[] ruleDataArray = ruleData.split("\\|");

			rulePattern = Pattern.compile(ruleDataArray[0]);

			if (ruleDataArray[1].endsWith("%")) {
				String percentageRule = ruleDataArray[1];

				maxFailPercentage =
					Integer.parseInt(
						percentageRule.substring(
							0, percentageRule.length() - 1)) / 100;
			}
			else {
				maxFailCount = Integer.parseInt(ruleDataArray[1]);
			}
		}

		public List<Build> evaluate(List<Build> downstreamBuilds) {
			if (debug) {
				System.out.println(
					JenkinsResultsParserUtil.combine(
						"Evaluating auto-close rule ", toString(), "."));
			}

			try {
				downstreamBuilds = getMatchingBuilds(downstreamBuilds);

				if (debug) {
					System.out.println(
						JenkinsResultsParserUtil.combine(
							"Found ", String.valueOf(downstreamBuilds.size()),
							" builds that match this rule."));
				}

				List<Build> failingInUpstreamJobDownstreamBuilds =
					new ArrayList<>(downstreamBuilds.size());

				for (Build downstreamBuild : downstreamBuilds) {
					if (UpstreamFailureUtil.isBuildFailingInUpstreamJob(
							downstreamBuild)) {

						failingInUpstreamJobDownstreamBuilds.add(
							downstreamBuild);

						continue;
					}

					List<TestResult> testResults = new ArrayList<>();

					testResults.addAll(
						downstreamBuild.getTestResults("FAILED"));
					testResults.addAll(
						downstreamBuild.getTestResults("REGRESSION"));

					boolean containsUniqueTestFailure = false;

					if (testResults.isEmpty()) {
						containsUniqueTestFailure = true;
					}
					else {
						for (TestResult testResult : testResults) {
							if (!UpstreamFailureUtil.isTestFailingInUpstreamJob(
									testResult)) {

								containsUniqueTestFailure = true;

								break;
							}
						}
					}

					if (!containsUniqueTestFailure) {
						failingInUpstreamJobDownstreamBuilds.add(
							downstreamBuild);
					}
				}

				if (debug) {
					System.out.println(
						JenkinsResultsParserUtil.combine(
							String.valueOf(
								failingInUpstreamJobDownstreamBuilds.size()),
							" downstream builds are also failing in ",
							"upstream."));
				}

				downstreamBuilds.removeAll(
					failingInUpstreamJobDownstreamBuilds);

				if (downstreamBuilds.isEmpty()) {
					if (debug) {
						System.out.println(toString() + " has PASSED.");
					}

					return Collections.emptyList();
				}

				List<Build> failedDownstreamBuilds = new ArrayList<>(
					downstreamBuilds.size());

				int failLimit = 0;

				if (maxFailPercentage != -1) {
					failLimit =
						(int)(maxFailPercentage * downstreamBuilds.size());

					if (failLimit > 0) {
						failLimit--;
					}
				}
				else {
					failLimit = maxFailCount;
				}

				if (debug) {
					System.out.println(
						JenkinsResultsParserUtil.combine(
							toString(), " fail limit is ",
							String.valueOf(failLimit)));
				}

				for (Build downstreamBuild : downstreamBuilds) {
					String status = downstreamBuild.getStatus();

					if (!status.equals("completed")) {
						continue;
					}

					String result = downstreamBuild.getResult();

					if ((result != null) && !result.equals("SUCCESS")) {
						if (debug) {
							System.out.println(
								JenkinsResultsParserUtil.combine(
									"Found a matching failed build. ",
									downstreamBuild.getDisplayName(),
									" has failed."));
						}

						failedDownstreamBuilds.add(downstreamBuild);
					}
				}

				if (failedDownstreamBuilds.size() > failLimit) {
					if (debug) {
						System.out.println(
							JenkinsResultsParserUtil.combine(
								"Found ",
								String.valueOf(failedDownstreamBuilds.size()),
								" matching failed builds.\n", toString(),
								" has FAILED."));
					}

					return failedDownstreamBuilds;
				}

				if (debug) {
					System.out.println(
						JenkinsResultsParserUtil.combine(
							"Found ",
							String.valueOf(failedDownstreamBuilds.size()),
							" matching failed builds.\n", toString(),
							" has PASSED."));
				}

				return Collections.emptyList();
			}
			finally {
				if (debug) {
					System.out.println(
						JenkinsResultsParserUtil.combine(
							"Finished evaluating rule ", toString(), "\n"));
				}
			}
		}

		@Override
		public String toString() {
			return ruleData;
		}

		protected List<Build> getMatchingBuilds(List<Build> downstreamBuilds) {
			List<Build> filteredDownstreamBuilds = new ArrayList<>(
				downstreamBuilds.size());

			for (Build downstreamBuild : downstreamBuilds) {
				String jobVariant = downstreamBuild.getJobVariant();

				if ((jobVariant != null) && !jobVariant.isEmpty()) {
					Matcher matcher = rulePattern.matcher(jobVariant);

					if (matcher.matches()) {
						filteredDownstreamBuilds.add(downstreamBuild);

						continue;
					}
				}

				String jobName = downstreamBuild.getJobName();

				if ((jobName != null) && !jobName.isEmpty()) {
					Matcher matcher = rulePattern.matcher(jobName);

					if (matcher.matches()) {
						filteredDownstreamBuilds.add(downstreamBuild);

						continue;
					}
				}
			}

			return filteredDownstreamBuilds;
		}

		protected int maxFailCount = -1;
		protected float maxFailPercentage = -1;
		protected String ruleData;
		protected Pattern rulePattern;

	}

}