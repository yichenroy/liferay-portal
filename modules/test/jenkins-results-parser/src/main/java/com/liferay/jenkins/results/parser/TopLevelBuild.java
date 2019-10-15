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

import com.liferay.jenkins.results.parser.failure.message.generator.CIFailureMessageGenerator;
import com.liferay.jenkins.results.parser.failure.message.generator.CITestSuiteValidationFailureMessageGenerator;
import com.liferay.jenkins.results.parser.failure.message.generator.CompileFailureMessageGenerator;
import com.liferay.jenkins.results.parser.failure.message.generator.DownstreamFailureMessageGenerator;
import com.liferay.jenkins.results.parser.failure.message.generator.FailureMessageGenerator;
import com.liferay.jenkins.results.parser.failure.message.generator.GenericFailureMessageGenerator;
import com.liferay.jenkins.results.parser.failure.message.generator.GitLPushFailureMessageGenerator;
import com.liferay.jenkins.results.parser.failure.message.generator.GradleTaskFailureMessageGenerator;
import com.liferay.jenkins.results.parser.failure.message.generator.InvalidGitCommitSHAFailureMessageGenerator;
import com.liferay.jenkins.results.parser.failure.message.generator.InvalidSenderSHAFailureMessageGenerator;
import com.liferay.jenkins.results.parser.failure.message.generator.JenkinsRegenFailureMessageGenerator;
import com.liferay.jenkins.results.parser.failure.message.generator.JenkinsSourceFormatFailureMessageGenerator;
import com.liferay.jenkins.results.parser.failure.message.generator.PoshiTestFailureMessageGenerator;
import com.liferay.jenkins.results.parser.failure.message.generator.PoshiValidationFailureMessageGenerator;
import com.liferay.jenkins.results.parser.failure.message.generator.RebaseFailureMessageGenerator;

import java.io.IOException;
import java.io.StringWriter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import org.dom4j.DocumentException;
import org.dom4j.Element;

import org.json.JSONObject;

/**
 * @author Kevin Yen
 */
public abstract class TopLevelBuild extends BaseBuild {

	@Override
	public void addDownstreamBuilds(String... urls) {
		super.addDownstreamBuilds(urls);

		if (getDownstreamBuildCount("completed") < getDownstreamBuildCount(
				null)) {

			setResult(null);
		}
	}

	@Override
	public void addTimelineData(BaseBuild.TimelineData timelineData) {
		timelineData.addTimelineData(this);

		if (getTopLevelBuild() == this) {
			addDownstreamBuildsTimelineData(timelineData);
		}
	}

	@Override
	public void archive(String archiveName) {
		super.archive(archiveName);

		if (getParentBuild() == null) {
			Properties archiveProperties = new Properties();

			archiveProperties.setProperty(
				"top.level.build.url", replaceBuildURL(getBuildURL()));

			try {
				StringWriter sw = new StringWriter();

				archiveProperties.store(sw, null);

				writeArchiveFile(
					sw.toString(), archiveName + "/archive.properties");
			}
			catch (IOException ioe) {
				throw new RuntimeException(
					"Unable to write archive properties");
			}
		}

		try {
			writeArchiveFile(
				getJenkinsReport(), getArchivePath() + "/jenkins-report.html");
		}
		catch (IOException ioe) {
			throw new RuntimeException("Unable to archive Jenkins report", ioe);
		}
	}

	public String getAcceptanceUpstreamJobURL() {
		String jobName = getJobName();

		if (jobName.contains("pullrequest")) {
			String acceptanceUpstreamJobURL = JenkinsResultsParserUtil.combine(
				"https://test-1-1.liferay.com/job/",
				jobName.replace("pullrequest", "upstream"));

			try {
				JenkinsResultsParserUtil.toString(
					JenkinsResultsParserUtil.getLocalURL(
						acceptanceUpstreamJobURL),
					false, 0, 0, 0);
			}
			catch (IOException ioe) {
				return null;
			}

			return acceptanceUpstreamJobURL;
		}

		return null;
	}

	public Map<String, String> getBaseGitRepositoryDetailsTempMap() {
		String gitRepositoryType = getBaseGitRepositoryType();

		String tempMapName = "git." + gitRepositoryType + ".properties";

		return getTempMap(tempMapName);
	}

	public String getCompanionBranchName() {
		TopLevelBuild topLevelBuild = getTopLevelBuild();

		Map<String, String> gitRepositoryGitDetailsTempMap =
			topLevelBuild.getCompanionGitRepositoryDetailsTempMap();

		return gitRepositoryGitDetailsTempMap.get("github.sender.branch.name");
	}

	public Map<String, String> getCompanionGitRepositoryDetailsTempMap() {
		String branchName = getBranchName();
		String branchType = "ee";
		String gitRepositoryType = getBaseGitRepositoryType();

		if (branchName.endsWith("-private")) {
			branchType = "base";
		}

		String tempMapName = JenkinsResultsParserUtil.combine(
			"git.", gitRepositoryType, ".", branchType, ".properties");

		return getTempMap(tempMapName);
	}

	public String getCompanionGitRepositorySHA() {
		TopLevelBuild topLevelBuild = getTopLevelBuild();

		Map<String, String> gitRepositoryGitDetailsTempMap =
			topLevelBuild.getCompanionGitRepositoryDetailsTempMap();

		return gitRepositoryGitDetailsTempMap.get("github.sender.branch.sha");
	}

	public String getCompanionUsername() {
		TopLevelBuild topLevelBuild = getTopLevelBuild();

		Map<String, String> gitRepositoryGitDetailsTempMap =
			topLevelBuild.getCompanionGitRepositoryDetailsTempMap();

		return gitRepositoryGitDetailsTempMap.get("github.sender.username");
	}

	@Override
	public String getDisplayName() {
		String displayName = super.getDisplayName();

		if (getParentBuild() != null) {
			displayName += "/" + getParameterValue("JENKINS_JOB_VARIANT");
		}

		return displayName;
	}

	@Override
	public Element getGitHubMessageElement() {
		Collections.sort(
			downstreamBuilds, new BaseBuild.BuildDisplayNameComparator());

		if (getParentBuild() == null) {
			return getTopGitHubMessageElement();
		}

		return super.getGitHubMessageElement();
	}

	public String getJenkinsReport() {
		try {
			return JenkinsResultsParserUtil.toString(
				JenkinsResultsParserUtil.getLocalURL(getJenkinsReportURL()));
		}
		catch (IOException ioe) {
			throw new RuntimeException("Unable to get Jenkins report", ioe);
		}
	}

	public Element getJenkinsReportElement() {
		return Dom4JUtil.getNewElement(
			"html", null, getJenkinsReportHeadElement(),
			getJenkinsReportBodyElement());
	}

	public String getJenkinsReportURL() {
		if (fromArchive) {
			return getBuildURL() + "/jenkins-report.html";
		}

		JenkinsMaster jenkinsMaster = getJenkinsMaster();

		return JenkinsResultsParserUtil.combine(
			"https://", jenkinsMaster.getName(), ".liferay.com/",
			"userContent/jobs/", getJobName(), "/builds/",
			String.valueOf(getBuildNumber()), "/jenkins-report.html");
	}

	@Override
	public Map<String, String> getMetricLabels() {
		Map<String, String> metricLabels = new TreeMap<>();

		metricLabels.put("job_type", "top-level");
		metricLabels.put("top_level_job_name", getJobName());

		return metricLabels;
	}

	@Override
	public String getStatusReport(int indentSize) {
		String statusReport = super.getStatusReport(indentSize);

		if (getDownstreamBuildCount(null) > 0) {
			while (statusReport.endsWith("\n")) {
				statusReport = statusReport.substring(
					0, statusReport.length() - 1);
			}

			statusReport += " / ";
		}

		return statusReport + "Update took " + _updateDuration +
			" milliseconds.\n";
	}

	@Override
	public String getStatusSummary() {
		long currentTimeMillis = System.currentTimeMillis();

		if ((currentTimeMillis - _MILLIS_DOWNSTREAM_BUILDS_LISTING_INTERVAL) >=
				_lastDownstreamBuildsListingTimestamp) {

			StringBuilder sb = new StringBuilder(super.getStatusSummary());

			sb.append("\nRunning Builds: ");

			_lastDownstreamBuildsListingTimestamp = System.currentTimeMillis();

			for (Build downstreamBuild : getDownstreamBuilds("running")) {
				sb.append("\n");
				sb.append(downstreamBuild.getBuildURL());
			}

			return sb.toString();
		}

		return super.getStatusSummary();
	}

	@Override
	public JSONObject getTestReportJSONObject() {
		return null;
	}

	public BaseBuild.TimelineData getTimelineData() {
		return new BaseBuild.TimelineData(500, this);
	}

	public Element getValidationGitHubMessageElement() {
		ValidationBuild validationBuild = null;

		for (Build downstreamBuild : downstreamBuilds) {
			if (downstreamBuild instanceof ValidationBuild) {
				validationBuild = (ValidationBuild)downstreamBuild;
			}
		}

		if (validationBuild == null) {
			throw new RuntimeException("Unable to find a validation build");
		}

		return validationBuild.getGitHubMessageElement();
	}

	@Override
	public void setCompareToUpstream(boolean compareToUpstream) {
		_compareToUpstream = compareToUpstream;
	}

	@Override
	public void takeSlaveOffline(SlaveOfflineRule slaveOfflineRule) {
	}

	@Override
	public void update() {
		long start = System.currentTimeMillis();

		super.update();

		_updateDuration = System.currentTimeMillis() - start;

		if (_sendBuildMetrics && !fromArchive && (getParentBuild() == null)) {
			if (!fromCompletedBuild) {
				sendBuildMetricsOnModifiedBuilds();
			}
			else {
				sendBuildMetrics(
					StatsDMetricsUtil.generateGaugeDeltaMetric(
						"build_slave_usage_gauge", -1, getMetricLabels()));
			}
		}
	}

	protected TopLevelBuild(String url) {
		this(url, null);
	}

	protected TopLevelBuild(String url, TopLevelBuild topLevelBuild) {
		super(url, topLevelBuild);

		Properties buildProperties = null;

		try {
			buildProperties = JenkinsResultsParserUtil.getBuildProperties();
		}
		catch (IOException ioe) {
			throw new RuntimeException("Unable to get build.properties", ioe);
		}

		_sendBuildMetrics = Boolean.valueOf(
			buildProperties.getProperty("build.metrics.send"));

		if (_sendBuildMetrics) {
			_metricsHostName = buildProperties.getProperty(
				"build.metrics.host.name");

			String metricsHostPortString = buildProperties.getProperty(
				"build.metrics.host.port");

			if ((_metricsHostName == null) || (metricsHostPortString == null)) {
				throw new IllegalArgumentException(
					"Properties \"build.metrics.host.name\" and " +
						"\"build.metrics.host.port\" must be set to send " +
							"build metrics");
			}

			try {
				_metricsHostPort = Integer.parseInt(metricsHostPortString);
			}
			catch (NumberFormatException nfe) {
				throw new IllegalArgumentException(
					"Please set \"build.metrics.host.port\" to an integer");
			}

			if (topLevelBuild == null) {
				sendBuildMetrics(
					StatsDMetricsUtil.generateGaugeDeltaMetric(
						"build_slave_usage_gauge", 1, getMetricLabels()));
			}
		}
	}

	@Override
	protected void archiveJSON() {
		super.archiveJSON();

		try {
			Properties buildProperties =
				JenkinsResultsParserUtil.getBuildProperties();

			String gitRepositoryTypes = buildProperties.getProperty(
				"repository.types");

			if (jobName.startsWith(
					"test-subrepository-acceptance-pullrequest")) {

				gitRepositoryTypes += "," + getBaseGitRepositoryName();
			}

			for (String gitRepositoryType : gitRepositoryTypes.split(",")) {
				try {
					JSONObject gitRepositoryDetailsJSONObject =
						JenkinsResultsParserUtil.toJSONObject(
							getGitRepositoryDetailsPropertiesTempMapURL(
								gitRepositoryType));

					Set<?> set = gitRepositoryDetailsJSONObject.keySet();

					if (set.isEmpty()) {
						continue;
					}

					writeArchiveFile(
						gitRepositoryDetailsJSONObject.toString(4),
						getArchivePath() + "/git." + gitRepositoryType +
							".properties.json");
				}
				catch (IOException ioe) {
					throw new RuntimeException(
						"Unable to create git." + gitRepositoryType +
							".properties.json",
						ioe);
				}
			}
		}
		catch (IOException ioe) {
			throw new RuntimeException("Unable to get build properties", ioe);
		}
	}

	@Override
	protected void findDownstreamBuilds() {
		if (getParentBuild() != null) {
			return;
		}

		super.findDownstreamBuilds();

		String consoleText = getConsoleText();

		for (Build downstreamBuild : downstreamBuilds) {
			BaseBuild downstreamBaseBuild = (BaseBuild)downstreamBuild;

			downstreamBaseBuild.checkForReinvocation(consoleText);
		}
	}

	@Override
	protected List<String> findDownstreamBuildsInConsoleText() {
		if (getParentBuild() != null) {
			return Collections.emptyList();
		}

		String consoleText = getConsoleText();

		List<String> foundDownstreamBuildURLs = new ArrayList<>();

		if ((consoleText == null) || consoleText.isEmpty()) {
			return foundDownstreamBuildURLs;
		}

		Set<String> downstreamBuildURLs = new HashSet<>();

		for (Build downstreamBuild : getDownstreamBuilds(null)) {
			String downstreamBuildURL = downstreamBuild.getBuildURL();

			if (downstreamBuildURL != null) {
				downstreamBuildURLs.add(downstreamBuildURL);
			}
		}

		if (getBuildURL() != null) {
			int i = consoleText.lastIndexOf("\nstop-current-job:");

			if (i != -1) {
				consoleText = consoleText.substring(0, i);
			}

			Matcher downstreamBuildURLMatcher =
				downstreamBuildURLPattern.matcher(
					consoleText.substring(consoleReadCursor));

			consoleReadCursor = consoleText.length();

			while (downstreamBuildURLMatcher.find()) {
				String url = downstreamBuildURLMatcher.group("url");

				Pattern reinvocationPattern = Pattern.compile(
					Pattern.quote(url) + " restarted at (?<url>[^\\s]*)\\.");

				Matcher reinvocationMatcher = reinvocationPattern.matcher(
					consoleText);

				while (reinvocationMatcher.find()) {
					url = reinvocationMatcher.group("url");
				}

				if (!foundDownstreamBuildURLs.contains(url) &&
					!downstreamBuildURLs.contains(url)) {

					foundDownstreamBuildURLs.add(url);
				}
			}
		}

		return foundDownstreamBuildURLs;
	}

	protected Element getBaseBranchDetailsElement() {
		String baseBranchURL = JenkinsResultsParserUtil.combine(
			"https://github.com/liferay/", getBaseGitRepositoryName(), "/tree/",
			getBranchName());

		String baseGitRepositoryName = getBaseGitRepositoryName();

		String baseGitRepositorySHA = null;

		if (!baseGitRepositoryName.equals("liferay-jenkins-ee") &&
			baseGitRepositoryName.endsWith("-ee")) {

			baseGitRepositorySHA = getBaseGitRepositorySHA(
				baseGitRepositoryName.substring(
					0, baseGitRepositoryName.length() - 3));
		}
		else {
			baseGitRepositorySHA = getBaseGitRepositorySHA(
				baseGitRepositoryName);
		}

		String baseGitRepositoryCommitURL =
			"https://github.com/liferay/" + baseGitRepositoryName + "/commit/" +
				baseGitRepositorySHA;

		Element baseGitBranchDetailsElement = Dom4JUtil.getNewElement(
			"p", null, "Branch Name: ",
			Dom4JUtil.getNewAnchorElement(baseBranchURL, getBranchName()));

		if (baseGitRepositorySHA != null) {
			Dom4JUtil.addToElement(
				baseGitBranchDetailsElement, Dom4JUtil.getNewElement("br"),
				"Branch GIT ID: ",
				Dom4JUtil.getNewAnchorElement(
					baseGitRepositoryCommitURL, baseGitRepositorySHA));
		}

		return baseGitBranchDetailsElement;
	}

	protected Element[] getBuildFailureElements() {
		Map<Build, Element> downstreamBuildFailureMessages =
			getDownstreamBuildMessages("ABORTED", "FAILURE", "UNSTABLE");

		List<Element> allCurrentBuildFailureElements = new ArrayList<>();
		List<Element> upstreamBuildFailureElements = new ArrayList<>();

		int maxFailureCount = 5;

		for (Map.Entry<Build, Element> entry :
				downstreamBuildFailureMessages.entrySet()) {

			Build failedDownstreamBuild = entry.getKey();

			Element failureElement = entry.getValue();

			if (failureElement != null) {
				if (UpstreamFailureUtil.isBuildFailingInUpstreamJob(
						failedDownstreamBuild)) {

					upstreamBuildFailureElements.add(failureElement);

					continue;
				}

				if (isHighPriorityBuildFailureElement(failureElement)) {
					allCurrentBuildFailureElements.add(0, failureElement);

					continue;
				}

				allCurrentBuildFailureElements.add(failureElement);
			}

			Element upstreamJobFailureElement =
				failedDownstreamBuild.
					getGitHubMessageUpstreamJobFailureElement();

			if (upstreamJobFailureElement != null) {
				upstreamBuildFailureElements.add(upstreamJobFailureElement);
			}
		}

		List<Element> buildFailureElements = new ArrayList<>();

		buildFailureElements.add(Dom4JUtil.getNewElement("hr"));

		if (allCurrentBuildFailureElements.isEmpty() &&
			upstreamBuildFailureElements.isEmpty()) {

			allCurrentBuildFailureElements.add(
				0, super.getGitHubMessageElement());
		}

		if (allCurrentBuildFailureElements.isEmpty() &&
			!upstreamBuildFailureElements.isEmpty()) {

			buildFailureElements.add(
				Dom4JUtil.getNewElement(
					"h4", null, "This pull contains no unique failures."));
		}
		else {
			String failureTitle = "Failures unique to this pull:";

			if (!UpstreamFailureUtil.isUpstreamComparisonAvailable() &&
				isCompareToUpstream()) {

				failureTitle =
					"Failures (upstream comparison is not available):";
			}

			buildFailureElements.add(
				Dom4JUtil.getNewElement("h4", null, failureTitle));

			buildFailureElements.add(
				Dom4JUtil.getOrderedListElement(
					allCurrentBuildFailureElements, maxFailureCount));
		}

		String acceptanceUpstreamJobURL = getAcceptanceUpstreamJobURL();

		if ((allCurrentBuildFailureElements.size() < maxFailureCount) &&
			!upstreamBuildFailureElements.isEmpty()) {

			Element acceptanceUpstreamJobLinkElement =
				Dom4JUtil.getNewAnchorElement(
					acceptanceUpstreamJobURL, "acceptance upstream results");

			Element upstreamJobFailureElement = Dom4JUtil.getNewElement(
				"details", null,
				Dom4JUtil.getNewElement(
					"summary", null,
					Dom4JUtil.getNewElement(
						"strong", null, "Failures in common with ",
						acceptanceUpstreamJobLinkElement, " at ",
						UpstreamFailureUtil.getUpstreamJobFailuresSHA(this),
						":")));

			int remainingFailureCount =
				maxFailureCount - allCurrentBuildFailureElements.size();

			Dom4JUtil.getOrderedListElement(
				upstreamBuildFailureElements, upstreamJobFailureElement,
				remainingFailureCount);

			buildFailureElements.add(Dom4JUtil.getNewElement("hr"));

			buildFailureElements.add(upstreamJobFailureElement);
		}

		if (jobName.contains("pullrequest") &&
			upstreamBuildFailureElements.isEmpty() &&
			(acceptanceUpstreamJobURL != null)) {

			Element upstreamResultElement = Dom4JUtil.getNewElement("h4");

			Dom4JUtil.addToElement(
				upstreamResultElement, "For upstream results, click ",
				Dom4JUtil.getNewAnchorElement(acceptanceUpstreamJobURL, "here"),
				".");

			buildFailureElements.add(upstreamResultElement);

			Map<String, String> startPropertiesTempMap =
				getStartPropertiesTempMap();

			String subrepositoryMergePullMentionUsers =
				startPropertiesTempMap.get(
					"SUBREPOSITORY_MERGE_PULL_MENTION_USERS");

			if (subrepositoryMergePullMentionUsers != null) {
				StringBuilder sb = new StringBuilder();

				sb.append("cc");

				for (String subrepositoryMergePullMentionUser :
						subrepositoryMergePullMentionUsers.split(",")) {

					sb.append(" @");
					sb.append(subrepositoryMergePullMentionUser);
				}

				buildFailureElements.add(
					Dom4JUtil.getNewElement("div", null, sb.toString()));
			}
		}

		return buildFailureElements.toArray(new Element[0]);
	}

	protected Element getCompanionBranchDetailsElement() {
		String baseGitRepositoryName = getBaseGitRepositoryName();
		String branchName = getBranchName();

		String companionGitRepositoryName = baseGitRepositoryName;

		if (branchName.equals("master")) {
			companionGitRepositoryName = companionGitRepositoryName + "-ee";
		}

		if (branchName.endsWith("-private")) {
			companionGitRepositoryName = baseGitRepositoryName.substring(
				0, baseGitRepositoryName.indexOf("-ee"));
		}

		String companionUsername = getCompanionUsername();

		String companionBranchURL = JenkinsResultsParserUtil.combine(
			"https://github.com/", companionUsername, "/",
			companionGitRepositoryName, "/tree/", getCompanionBranchName());

		String companionGitRepositorySHA = getCompanionGitRepositorySHA();

		String companionGitRepositoryCommitURL =
			JenkinsResultsParserUtil.combine(
				"https://github.com/", companionUsername, "/",
				companionGitRepositoryName, "/commit/",
				companionGitRepositorySHA);

		Element companionBranchDetailsElement = Dom4JUtil.getNewElement(
			"p", null, "Branch Name: ",
			Dom4JUtil.getNewAnchorElement(
				companionBranchURL, getCompanionBranchName()));

		if (companionGitRepositorySHA != null) {
			Dom4JUtil.addToElement(
				companionBranchDetailsElement, Dom4JUtil.getNewElement("br"),
				"Branch GIT ID: ",
				Dom4JUtil.getNewAnchorElement(
					companionGitRepositoryCommitURL,
					companionGitRepositorySHA));
		}

		return companionBranchDetailsElement;
	}

	protected Element getDownstreamGitHubMessageElement() {
		String status = getStatus();

		if (!status.equals("completed") && (getParentBuild() != null)) {
			return null;
		}

		String result = getResult();

		if (result.equals("SUCCESS")) {
			return null;
		}

		Element messageElement = Dom4JUtil.getNewElement(
			"div", null,
			Dom4JUtil.getNewAnchorElement(
				getBuildURL(), null, getDisplayName()));

		if (result.equals("ABORTED")) {
			messageElement.add(
				Dom4JUtil.toCodeSnippetElement("Build was aborted"));
		}

		if (result.equals("FAILURE")) {
			Element failureMessageElement = getFailureMessageElement();

			if (failureMessageElement != null) {
				messageElement.add(failureMessageElement);
			}
		}

		return messageElement;
	}

	@Override
	protected ExecutorService getExecutorService() {
		return _executorService;
	}

	protected Element getFailedJobSummaryElement() {
		Element jobSummaryListElement = getJobSummaryListElement(false, null);

		int failCount =
			getDownstreamBuildCount(null) -
				getDownstreamBuildCountByResult("SUCCESS") + 1;

		return Dom4JUtil.getNewElement(
			"div", null,
			Dom4JUtil.getNewElement(
				"h4", null, String.valueOf(failCount), " Failed Jobs:"),
			jobSummaryListElement);
	}

	@Override
	protected FailureMessageGenerator[] getFailureMessageGenerators() {
		return _FAILURE_MESSAGE_GENERATORS;
	}

	@Override
	protected Element getGitHubMessageJobResultsElement() {
		int successCount = getDownstreamBuildCountByResult("SUCCESS");

		int failCount = getDownstreamBuildCount(null) - successCount + 1;

		return Dom4JUtil.getNewElement(
			"div", null, Dom4JUtil.getNewElement("h6", null, "Job Results:"),
			Dom4JUtil.getNewElement(
				"p", null, String.valueOf(successCount),
				JenkinsResultsParserUtil.getNounForm(
					successCount, " Jobs", " Job"),
				" Passed.", Dom4JUtil.getNewElement("br"),
				String.valueOf(failCount),
				JenkinsResultsParserUtil.getNounForm(
					failCount, " Jobs", " Job"),
				" Failed."));
	}

	protected String getGitRepositoryDetailsPropertiesTempMapURL(
		String gitRepositoryType) {

		if (fromArchive) {
			return JenkinsResultsParserUtil.combine(
				getBuildURL(), "git.", gitRepositoryType, ".properties.json");
		}

		TopLevelBuild topLevelBuild = getTopLevelBuild();

		JenkinsMaster topLevelBuildJenkinsMaster =
			topLevelBuild.getJenkinsMaster();

		return JenkinsResultsParserUtil.combine(
			URL_BASE_TEMP_MAP, topLevelBuildJenkinsMaster.getName(), "/",
			topLevelBuild.getJobName(), "/",
			String.valueOf(topLevelBuild.getBuildNumber()), "/",
			topLevelBuild.getJobName(), "/git.", gitRepositoryType,
			".properties");
	}

	protected Element getJenkinsReportBodyElement() {
		String buildURL = getBuildURL();

		Element headingElement = Dom4JUtil.getNewElement(
			"h1", null, "Jenkins report for ",
			Dom4JUtil.getNewAnchorElement(buildURL, buildURL));

		Element subheadingElement = null;

		JSONObject jobJSONObject = getBuildJSONObject();

		String description = jobJSONObject.optString("description");

		if (!description.isEmpty()) {
			subheadingElement = Dom4JUtil.getNewElement("h2");

			try {
				Dom4JUtil.addRawXMLToElement(subheadingElement, description);
			}
			catch (DocumentException de) {
				throw new RuntimeException(
					"Unable to parse description HTML " + description, de);
			}
		}

		return Dom4JUtil.getNewElement(
			"body", null, headingElement, subheadingElement,
			getJenkinsReportSummaryElement(), getJenkinsReportTimelineElement(),
			getJenkinsReportTopLevelTableElement(),
			getJenkinsReportDownstreamElement());
	}

	@Override
	protected String getJenkinsReportBuildInfoCellElementTagName() {
		return "th";
	}

	protected Element getJenkinsReportChartJsScriptElement(
		String xData, String y1Data, String y2Data) {

		String resourceFileContent = null;

		try {
			resourceFileContent =
				JenkinsResultsParserUtil.getResourceFileContent(
					"dependencies/chart_template.js");
		}
		catch (IOException ioe) {
			throw new RuntimeException(
				"Unable to load resource chart_template.js", ioe);
		}

		resourceFileContent = resourceFileContent.replace("'xData'", xData);

		resourceFileContent = resourceFileContent.replace("'y1Data'", y1Data);

		resourceFileContent = resourceFileContent.replace("'y2Data'", y2Data);

		Element scriptElement = Dom4JUtil.getNewElement("script");

		scriptElement.addText(resourceFileContent);

		return scriptElement;
	}

	protected Element getJenkinsReportDownstreamElement() {
		return Dom4JUtil.getNewElement(
			"div", null,
			getJenkinsReportDownstreamTableElement(null, "queued", "Queued: "),
			getJenkinsReportDownstreamTableElement(
				null, "starting", "Starting: "),
			getJenkinsReportDownstreamTableElement(
				null, "running", "Running: "),
			getJenkinsReportDownstreamTableElement(
				null, "missing", "Missing: "),
			Dom4JUtil.getNewElement("h2", null, "Completed: "),
			getJenkinsReportDownstreamTableElement(
				"ABORTED", "completed", "---- Aborted: "),
			getJenkinsReportDownstreamTableElement(
				"FAILURE", "completed", "---- Failure: "),
			getJenkinsReportDownstreamTableElement(
				"UNSTABLE", "completed", "---- Unstable: "),
			getJenkinsReportDownstreamTableElement(
				"SUCCESS", "completed", "---- Success: "));
	}

	protected Element getJenkinsReportDownstreamTableElement(
		String result, String status, String captionText) {

		List<Element> tableRowElements = getJenkinsReportTableRowElements(
			result, status);

		if (tableRowElements.isEmpty()) {
			return null;
		}

		return Dom4JUtil.getNewElement(
			"table", null,
			Dom4JUtil.getNewElement(
				"caption", null, captionText,
				String.valueOf(getDownstreamBuildCount(result, status))),
			getJenkinsReportTableColumnHeadersElement(),
			tableRowElements.toArray(new Element[0]));
	}

	protected Element getJenkinsReportHeadElement() {
		Element headElement = Dom4JUtil.getNewElement("head");

		getResourceFileContentAsElement(
			"style", headElement, "dependencies/jenkins_report.css");

		Element scriptElement = getResourceFileContentAsElement(
			"script", headElement, "dependencies/jenkins_report.js");

		scriptElement.addAttribute("language", "javascript");

		return headElement;
	}

	protected Element getJenkinsReportSummaryElement() {
		Element summaryElement = Dom4JUtil.getNewElement(
			"div", null,
			Dom4JUtil.getNewElement(
				"p", null, "Start Time: ",
				toJenkinsReportDateString(
					new Date(getStartTime()), getJenkinsReportTimeZoneName())),
			Dom4JUtil.getNewElement(
				"p", null, "Build Time: ",
				JenkinsResultsParserUtil.toDurationString(getDuration())),
			Dom4JUtil.getNewElement(
				"p", null, "Total CPU Usage Time: ",
				JenkinsResultsParserUtil.toDurationString(getTotalDuration())),
			Dom4JUtil.getNewElement(
				"p", null, "Total number of Jenkins slaves used: ",
				String.valueOf(getTotalSlavesUsedCount())),
			Dom4JUtil.getNewElement(
				"p", null, "Average delay time for invoked build to start: ",
				JenkinsResultsParserUtil.toDurationString(
					getAverageDelayTime())));

		Build longestDelayedDownstreamBuild =
			getLongestDelayedDownstreamBuild();

		if (longestDelayedDownstreamBuild != null) {
			Dom4JUtil.getNewElement(
				"p", summaryElement,
				"Longest delay time for invoked build to start: ",
				Dom4JUtil.getNewAnchorElement(
					longestDelayedDownstreamBuild.getBuildURL(),
					longestDelayedDownstreamBuild.getDisplayName()),
				" in: ",
				JenkinsResultsParserUtil.toDurationString(
					longestDelayedDownstreamBuild.getDelayTime()));
		}

		Build longestRunningDownstreamBuild =
			getLongestRunningDownstreamBuild();

		if (longestRunningDownstreamBuild != null) {
			Dom4JUtil.getNewElement(
				"p", summaryElement, "Longest Running Downstream Build: ",
				Dom4JUtil.getNewAnchorElement(
					longestRunningDownstreamBuild.getBuildURL(),
					longestRunningDownstreamBuild.getDisplayName()),
				" in: ",
				JenkinsResultsParserUtil.toDurationString(
					longestRunningDownstreamBuild.getDuration()));
		}

		try {
			Properties buildProperties =
				JenkinsResultsParserUtil.getBuildProperties();

			String longestRunningTestEnabled = buildProperties.getProperty(
				"jenkins.report.longest.running.test.enabled", "false");

			if (longestRunningTestEnabled.equals("true")) {
				TestResult longestRunningTest = getLongestRunningTest();

				if (longestRunningTest != null) {
					Dom4JUtil.getNewElement(
						"p", summaryElement, "Longest Running Test: ",
						Dom4JUtil.getNewAnchorElement(
							longestRunningTest.getTestReportURL(),
							longestRunningTest.getDisplayName()),
						" in: ",
						JenkinsResultsParserUtil.toDurationString(
							longestRunningTest.getDuration()));
				}
			}
		}
		catch (IOException ioe) {
			throw new RuntimeException("Unable to get build properties", ioe);
		}

		return summaryElement;
	}

	protected Element getJenkinsReportTableColumnHeadersElement() {
		Element nameElement = Dom4JUtil.getNewElement("th", null, "Name");

		Element consoleElement = Dom4JUtil.getNewElement("th", null, "Console");

		Element testReportElement = Dom4JUtil.getNewElement(
			"th", null, "Test Report");

		Element startTimeElement = Dom4JUtil.getNewElement(
			"th", null, "Start Time");

		Element buildTimeElement = Dom4JUtil.getNewElement(
			"th", null, "Build Time");

		Element statusElement = Dom4JUtil.getNewElement("th", null, "Status");

		Element resultElement = Dom4JUtil.getNewElement("th", null, "Result");

		Element tableColumnHeaderElement = Dom4JUtil.getNewElement("tr");

		Dom4JUtil.addToElement(
			tableColumnHeaderElement, nameElement, consoleElement,
			testReportElement, startTimeElement, buildTimeElement,
			statusElement, resultElement);

		return tableColumnHeaderElement;
	}

	protected Element getJenkinsReportTimelineElement() {
		Element canvasElement = Dom4JUtil.getNewElement("canvas");

		canvasElement.addAttribute("height", "300");
		canvasElement.addAttribute("id", "timeline");

		Element scriptElement = Dom4JUtil.getNewElement("script");

		scriptElement.addAttribute("src", _URL_CHART_JS);
		scriptElement.addText("");

		BaseBuild.TimelineData timelineData = getTimelineData();

		Element chartJSScriptElement = getJenkinsReportChartJsScriptElement(
			Arrays.toString(timelineData.getIndexData()),
			Arrays.toString(timelineData.getSlaveUsageData()),
			Arrays.toString(timelineData.getInvocationsData()));

		return Dom4JUtil.getNewElement(
			"div", null, canvasElement, scriptElement, chartJSScriptElement);
	}

	protected Element getJenkinsReportTopLevelTableElement() {
		Element topLevelTableElement = Dom4JUtil.getNewElement("table");

		String result = getResult();

		if (result != null) {
			Dom4JUtil.getNewElement(
				"caption", topLevelTableElement, "Top Level Build - ",
				Dom4JUtil.getNewElement("strong", null, getResult()));
		}
		else {
			Dom4JUtil.getNewElement(
				"caption", topLevelTableElement, "Top Level Build - ",
				Dom4JUtil.getNewElement(
					"strong", null, StringUtils.upperCase(getStatus())));
		}

		Dom4JUtil.addToElement(
			topLevelTableElement, getJenkinsReportTableColumnHeadersElement(),
			getJenkinsReportTableRowElement());

		List<Element> jenkinsReportStopWatchRecordElements =
			getJenkinsReportStopWatchRecordElements();

		Dom4JUtil.addToElement(
			topLevelTableElement,
			jenkinsReportStopWatchRecordElements.toArray());

		return topLevelTableElement;
	}

	protected Element getJobSummaryElement() {
		int successCount = getDownstreamBuildCountByResult("SUCCESS");

		String result = getResult();

		if ((result != null) && result.equals("SUCCESS")) {
			successCount++;
		}

		Element detailsElement = Dom4JUtil.getNewElement(
			"details", null,
			Dom4JUtil.getNewElement(
				"summary", null,
				Dom4JUtil.getNewElement(
					"strong", null, "ci:test:", getTestSuiteName(), " - ",
					String.valueOf(successCount), " out of ",
					String.valueOf(getDownstreamBuildCount(null) + 1),
					" jobs PASSED")));

		if ((result != null) && !result.equals("SUCCESS")) {
			Dom4JUtil.addToElement(
				detailsElement, getFailedJobSummaryElement());
		}

		if (getDownstreamBuildCountByResult("SUCCESS") > 0) {
			Dom4JUtil.addToElement(
				detailsElement, getSuccessfulJobSummaryElement());
		}

		return detailsElement;
	}

	protected Element getJobSummaryListElement() {
		Element jobSummaryListElement = Dom4JUtil.getNewElement("ul");

		List<Build> builds = new ArrayList<>();

		builds.add(this);

		builds.addAll(getDownstreamBuilds(null));

		for (Build build : builds) {
			Element jobSummaryListItemElement = Dom4JUtil.getNewElement(
				"li", jobSummaryListElement);

			jobSummaryListItemElement.add(
				build.getGitHubMessageBuildAnchorElement());
		}

		return jobSummaryListElement;
	}

	protected Element getJobSummaryListElement(
		boolean success, List<String> jobVariants) {

		Element jobSummaryListElement = Dom4JUtil.getNewElement("ul");

		List<Build> builds = new ArrayList<>();

		if (jobVariants != null) {
			builds.addAll(
				getJobVariantsDownstreamBuilds(jobVariants, null, null));
		}
		else {
			builds.add(this);

			builds.addAll(getDownstreamBuilds(null));
		}

		for (Build build : builds) {
			String result = build.getResult();

			if (result.equals("SUCCESS") == success) {
				Element jobSummaryListItemElement = Dom4JUtil.getNewElement(
					"li", jobSummaryListElement);

				jobSummaryListItemElement.add(
					build.getGitHubMessageBuildAnchorElement());
			}
		}

		return jobSummaryListElement;
	}

	protected Element getMoreDetailsElement() {
		return Dom4JUtil.getNewElement(
			"h5", null, "For more details click ",
			Dom4JUtil.getNewAnchorElement(getJenkinsReportURL(), "here"), ".");
	}

	protected Element getResourceFileContentAsElement(
		String tagName, Element parentElement, String resourceName) {

		String resourceFileContent = null;

		try {
			resourceFileContent =
				JenkinsResultsParserUtil.getResourceFileContent(resourceName);
		}
		catch (IOException ioe) {
			throw new RuntimeException(
				"Unable to load resource " + resourceName, ioe);
		}

		return Dom4JUtil.getNewElement(
			tagName, parentElement, resourceFileContent);
	}

	protected Element getResultElement() {
		StringBuilder sb = new StringBuilder();

		String result = getResult();

		int successCount = getDownstreamBuildCountByResult("SUCCESS");

		if ((result != null) && result.equals("SUCCESS")) {
			successCount++;

			sb.append(":heavy_check_mark: ");
		}
		else {
			sb.append(":x: ");
		}

		sb.append("ci:test:");
		sb.append(getTestSuiteName());
		sb.append(" - ");
		sb.append(String.valueOf(successCount));
		sb.append(" out of ");
		sb.append(String.valueOf(getDownstreamBuildCountByResult(null) + 1));
		sb.append(" jobs passed in ");
		sb.append(JenkinsResultsParserUtil.toDurationString(getDuration()));

		return Dom4JUtil.getNewElement("h3", null, sb.toString());
	}

	@Override
	protected String getStartPropertiesTempMapURL() {
		if (fromArchive) {
			return getBuildURL() + "/start.properties.json";
		}

		JenkinsMaster jenkinsMaster = getJenkinsMaster();

		return JenkinsResultsParserUtil.combine(
			URL_BASE_TEMP_MAP, jenkinsMaster.getName(), "/", getJobName(), "/",
			String.valueOf(getBuildNumber()), "/", getJobName(), "/",
			"start.properties");
	}

	@Override
	protected String getStopPropertiesTempMapURL() {
		if (fromArchive) {
			return getBuildURL() + "/stop.properties.json";
		}

		JenkinsMaster jenkinsMaster = getJenkinsMaster();

		return JenkinsResultsParserUtil.combine(
			URL_BASE_TEMP_MAP, jenkinsMaster.getName(), "/", getJobName(), "/",
			String.valueOf(getBuildNumber()), "/", getJobName(), "/",
			"stop.properties");
	}

	protected Element getSuccessfulJobSummaryElement() {
		Element jobSummaryListElement = getJobSummaryListElement(true, null);

		int successCount = getDownstreamBuildCountByResult("SUCCESS");

		String result = getResult();

		if ((result != null) && result.equals("SUCCESS")) {
			successCount++;
		}

		return Dom4JUtil.getNewElement(
			"details", null,
			Dom4JUtil.getNewElement(
				"summary", null,
				Dom4JUtil.getNewElement(
					"strong", null, String.valueOf(successCount),
					" Successful Jobs:")),
			jobSummaryListElement);
	}

	@Override
	protected String getTempMapURL(String tempMapName) {
		String tempMapURL = super.getTempMapURL(tempMapName);

		if (tempMapURL != null) {
			return tempMapURL;
		}

		Matcher matcher = gitRepositoryTempMapNamePattern.matcher(tempMapName);

		if (matcher.find()) {
			return getGitRepositoryDetailsPropertiesTempMapURL(
				matcher.group("gitRepositoryType"));
		}

		return null;
	}

	@Override
	protected int getTestCountByStatus(String status) {
		int testCount = 0;

		for (Build downstreamBuild : getDownstreamBuilds(null)) {
			if (!(downstreamBuild instanceof BaseBuild)) {
				continue;
			}

			BaseBuild downstreamBaseBuild = (BaseBuild)downstreamBuild;

			testCount += downstreamBaseBuild.getTestCountByStatus(status);
		}

		return testCount;
	}

	protected String getTestSuiteName() {
		String testSuiteName = getParameterValue("CI_TEST_SUITE");

		if (testSuiteName == null) {
			testSuiteName = "default";
		}

		return testSuiteName;
	}

	protected Element getTopGitHubMessageElement() {
		update();

		Element rootElement = Dom4JUtil.getNewElement("html");

		rootElement.add(getResultElement());

		Element detailsElement = Dom4JUtil.getNewElement(
			"details", rootElement,
			Dom4JUtil.getNewElement(
				"summary", null, "Click here for more details."),
			Dom4JUtil.getNewElement("h4", null, "Base Branch:"),
			getBaseBranchDetailsElement());

		String branchName = getBranchName();
		String companionBranchLabel = "Copied in Private Modules Branch:";

		if (branchName.endsWith("-private")) {
			companionBranchLabel = "Built off of Portal Core Branch:";
		}

		if (!branchName.startsWith("ee-") &&
			getBaseGitRepositoryName().contains("liferay-portal")) {

			try {
				Dom4JUtil.addToElement(
					detailsElement,
					Dom4JUtil.getNewElement("h4", null, companionBranchLabel),
					getCompanionBranchDetailsElement());
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}

		Dom4JUtil.addToElement(
			detailsElement, getJobSummaryElement(), getMoreDetailsElement());

		String result = getResult();

		if ((result != null) && !result.equals("SUCCESS")) {
			Dom4JUtil.addToElement(
				detailsElement, (Object[])getBuildFailureElements());
		}

		return rootElement;
	}

	protected String getUpstreamBranchSHA() {
		String upstreamBranchSHA = getParameterValue(
			"GITHUB_UPSTREAM_BRANCH_SHA");

		if ((upstreamBranchSHA == null) || upstreamBranchSHA.isEmpty()) {
			Map<String, String> startPropertiesTempMap =
				getStartPropertiesTempMap();

			upstreamBranchSHA = startPropertiesTempMap.get(
				"GITHUB_UPSTREAM_BRANCH_SHA");
		}

		return upstreamBranchSHA;
	}

	@Override
	protected boolean isCompareToUpstream() {
		return _compareToUpstream;
	}

	protected void sendBuildMetrics(String message) {
		if (_sendBuildMetrics) {
			DatagramRequestUtil.send(
				message.trim(), _metricsHostName, _metricsHostPort);
		}
	}

	protected void sendBuildMetricsOnModifiedBuilds() {
		StringBuilder sb = new StringBuilder();

		Map<Map<String, String>, Integer> slaveUsages =
			_getSlaveUsageByLabels();

		for (Map.Entry<Map<String, String>, Integer> slaveUsageEntry :
				slaveUsages.entrySet()) {

			Map<String, String> metricLabels = slaveUsageEntry.getKey();
			Integer slaveUsage = slaveUsageEntry.getValue();

			String buildMetricMessage =
				StatsDMetricsUtil.generateGaugeDeltaMetric(
					"build_slave_usage_gauge", slaveUsage, metricLabels);

			if (buildMetricMessage != null) {
				sb.append(buildMetricMessage);
				sb.append("\n");
			}
		}

		if (sb.length() > 0) {
			sendBuildMetrics(sb.toString());
		}

		sendBuildMetricsOnModifiedCompletedBuilds();
	}

	protected void sendBuildMetricsOnModifiedCompletedBuilds() {
		List<Build> modifiedCompletedBuilds =
			getModifiedDownstreamBuildsByStatus("completed");

		for (Build modifiedCompletedBuild : modifiedCompletedBuilds) {
			if (modifiedCompletedBuild instanceof BatchBuild) {
				continue;
			}

			sendBuildMetrics(
				StatsDMetricsUtil.generateTimerMetric(
					"jenkins_job_build_duration",
					modifiedCompletedBuild.getDuration(),
					modifiedCompletedBuild.getMetricLabels()));
		}
	}

	protected static final Pattern gitRepositoryTempMapNamePattern =
		Pattern.compile("git\\.(?<gitRepositoryType>.*)\\.properties");

	private Map<Map<String, String>, Integer> _getSlaveUsageByLabels() {
		Map<Map<String, String>, Integer> slaveUsages = new HashMap<>();

		List<Build> modifiedDownstreamBuilds = getModifiedDownstreamBuilds();

		for (Build modifiedDownstreamBuild : modifiedDownstreamBuilds) {
			Map<String, String> metricLabels =
				modifiedDownstreamBuild.getMetricLabels();

			Integer slaveUsage = slaveUsages.get(metricLabels);

			if (slaveUsage == null) {
				slaveUsage = 0;
			}

			slaveUsage += modifiedDownstreamBuild.getTotalSlavesUsedCount(
				"running", true);
			slaveUsage -= modifiedDownstreamBuild.getTotalSlavesUsedCount(
				"completed", true);

			slaveUsages.put(metricLabels, slaveUsage);
		}

		return slaveUsages;
	}

	private static final FailureMessageGenerator[] _FAILURE_MESSAGE_GENERATORS =
		{
			new CITestSuiteValidationFailureMessageGenerator(),
			new CompileFailureMessageGenerator(),
			new GitLPushFailureMessageGenerator(),
			new JenkinsRegenFailureMessageGenerator(),
			new JenkinsSourceFormatFailureMessageGenerator(),
			new InvalidGitCommitSHAFailureMessageGenerator(),
			new InvalidSenderSHAFailureMessageGenerator(),
			new RebaseFailureMessageGenerator(),
			//
			new PoshiValidationFailureMessageGenerator(),
			new PoshiTestFailureMessageGenerator(),
			//
			new GradleTaskFailureMessageGenerator(),
			//
			new DownstreamFailureMessageGenerator(),
			//
			new CIFailureMessageGenerator(),
			//
			new GenericFailureMessageGenerator()
		};

	// Skip JavaParser

	private static final long _MILLIS_DOWNSTREAM_BUILDS_LISTING_INTERVAL =
		1000 * 60 * 5;

	private static final String _URL_CHART_JS =
		"https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.5.0/Chart.min.js";

	private static ExecutorService _executorService =
		JenkinsResultsParserUtil.getNewThreadPoolExecutor(20, true);

	private boolean _compareToUpstream = true;
	private long _lastDownstreamBuildsListingTimestamp = -1L;
	private String _metricsHostName;
	private int _metricsHostPort;
	private final boolean _sendBuildMetrics;
	private long _updateDuration;

}