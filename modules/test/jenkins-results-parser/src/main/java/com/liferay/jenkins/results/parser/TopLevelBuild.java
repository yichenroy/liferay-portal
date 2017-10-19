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

import com.liferay.jenkins.results.parser.failure.message.generator.DownstreamFailureMessageGenerator;
import com.liferay.jenkins.results.parser.failure.message.generator.FailureMessageGenerator;
import com.liferay.jenkins.results.parser.failure.message.generator.GenericFailureMessageGenerator;
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
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import org.dom4j.Element;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Kevin Yen
 */
public class TopLevelBuild extends BaseBuild {

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

	public String getAcceptanceUpstreamURL() {
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
		String repositoryType = getBaseRepositoryType();

		String tempMapName = "git." + repositoryType + ".properties";

		return getTempMap(tempMapName);
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

		return JenkinsResultsParserUtil.combine(
			"https://", getMaster(), ".liferay.com/", "userContent/jobs/",
			getJobName(), "/builds/", Integer.toString(getBuildNumber()),
			"/jenkins-report.html");
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

		if ((currentTimeMillis - _DOWNSTREAM_BUILDS_LISTING_INTERVAL) >=
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

	@Override
	public void setCompareToUpstream(boolean compareToUpstream) {
		_compareToUpstream = compareToUpstream;
	}

	@Override
	public void takeSlaveOffline(SlaveOfflineRule slaveOfflineRule) {
		throw new RuntimeException(
			"Top Level Build slaves should not be taken offline");
	}

	@Override
	public void update() {
		long start = System.currentTimeMillis();

		super.update();

		_updateDuration = System.currentTimeMillis() - start;
	}

	protected TopLevelBuild(String url) {
		this(url, null);
	}

	protected TopLevelBuild(String url, TopLevelBuild topLevelBuild) {
		super(url, topLevelBuild);
	}

	@Override
	protected void archiveJSON() {
		super.archiveJSON();

		try {
			Properties buildProperties =
				JenkinsResultsParserUtil.getBuildProperties();

			String repositoryTypes = buildProperties.getProperty(
				"repository.types");

			if (jobName.startsWith(
					"test-subrepository-acceptance-pullrequest")) {

				repositoryTypes += "," + getBaseRepositoryName();
			}

			for (String repositoryType : repositoryTypes.split(",")) {
				try {
					JSONObject gitRepositoryDetailsJSONObject =
						JenkinsResultsParserUtil.toJSONObject(
							getGitRepositoryDetailsPropertiesTempMapURL(
								repositoryType));

					Set<?> set = gitRepositoryDetailsJSONObject.keySet();

					if (set.isEmpty()) {
						continue;
					}

					writeArchiveFile(
						gitRepositoryDetailsJSONObject.toString(4),
						getArchivePath() + "/git." + repositoryType +
							".properties.json");
				}
				catch (IOException ioe) {
					throw new RuntimeException(
						"Unable to create git." + repositoryType +
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
	}

	@Override
	protected List<String> findDownstreamBuildsInConsoleText(
		String consoleText) {

		if (getParentBuild() != null) {
			return Collections.emptyList();
		}

		return super.findDownstreamBuildsInConsoleText(consoleText);
	}

	protected Element getBaseBranchDetailsElement() {
		String baseBranchURL =
			"https://github.com/liferay/" + getBaseRepositoryName() + "/tree/" +
				getBranchName();

		String baseRepositoryName = getBaseRepositoryName();

		String baseRepositorySHA = null;

		if (!baseRepositoryName.equals("liferay-jenkins-ee") &&
			baseRepositoryName.endsWith("-ee")) {

			baseRepositorySHA = getBaseRepositorySHA(
				baseRepositoryName.substring(
					0, baseRepositoryName.length() - 3));
		}
		else {
			baseRepositorySHA = getBaseRepositorySHA(baseRepositoryName);
		}

		String baseRepositoryCommitURL =
			"https://github.com/liferay/" + baseRepositoryName + "/commit/" +
				baseRepositorySHA;

		Element baseBranchDetailsElement = Dom4JUtil.getNewElement(
			"p", null, "Branch Name: ",
			Dom4JUtil.getNewAnchorElement(baseBranchURL, getBranchName()));

		if (baseRepositorySHA != null) {
			Dom4JUtil.addToElement(
				baseBranchDetailsElement, Dom4JUtil.getNewElement("br"),
				"Branch GIT ID: ",
				Dom4JUtil.getNewAnchorElement(
					baseRepositoryCommitURL, baseRepositorySHA));
		}

		return baseBranchDetailsElement;
	}

	protected Element getBuildTimeElement() {
		return Dom4JUtil.getNewElement(
			"p", null, "Build Time: ",
			JenkinsResultsParserUtil.toDurationString(getDuration()));
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
		return Executors.newFixedThreadPool(20);
	}

	protected Element getFailedJobSummaryElement() {
		Element jobSummaryListElement = getJobSummaryListElement(false);

		int failCount =
			getDownstreamBuildCount(null) -
				getDownstreamBuildCountByResult("SUCCESS") + 1;

		return Dom4JUtil.getNewElement(
			"div", null,
			Dom4JUtil.getNewElement(
				"h4", null, Integer.toString(failCount), " Failed Jobs:"),
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
				"p", null, Integer.toString(successCount),
				JenkinsResultsParserUtil.getNounForm(
					successCount, " Jobs", " Job"),
				" Passed.", Dom4JUtil.getNewElement("br"),
				Integer.toString(failCount),
				JenkinsResultsParserUtil.getNounForm(
					failCount, " Jobs", " Job"),
				" Failed."));
	}

	protected String getGitRepositoryDetailsPropertiesTempMapURL(
		String repositoryType) {

		if (fromArchive) {
			return JenkinsResultsParserUtil.combine(
				getBuildURL(), "git.", repositoryType, ".properties.json");
		}

		TopLevelBuild topLevelBuild = getTopLevelBuild();

		return JenkinsResultsParserUtil.combine(
			TEMP_MAP_BASE_URL, topLevelBuild.getMaster(), "/",
			topLevelBuild.getJobName(), "/",
			Integer.toString(topLevelBuild.getBuildNumber()), "/",
			topLevelBuild.getJobName(), "/git.", repositoryType, ".properties");
	}

	protected Element getJenkinsReportBodyElement() {
		Map<String, Build> nonBatchBuilds = new HashMap<>();

		nonBatchBuilds.put(getDisplayName(), this);

		for (Build downstreamBuild : getDownstreamBuilds(null)) {
			String downstreamBuildDisplayName =
				downstreamBuild.getDisplayName();

			if (downstreamBuildDisplayName == null) {
				return null;
			}

			if (downstreamBuild instanceof BatchBuild) {
				for (Build nonBatchBuild :
						downstreamBuild.getDownstreamBuilds(null)) {

					nonBatchBuilds.put(
						nonBatchBuild.getDisplayName(), nonBatchBuild);
				}
			}
			else {
				nonBatchBuilds.put(
					downstreamBuild.getDisplayName(), downstreamBuild);
			}
		}

		String buildURL = getBuildURL();

		Element headingElement = Dom4JUtil.getNewElement(
			"h1", null, "Jenkins report for ",
			Dom4JUtil.getNewAnchorElement(buildURL, buildURL));

		JSONObject jobJSONObject = getBuildJSONObject();

		Element subheadingElement = null;

		try {
			subheadingElement = Dom4JUtil.getNewElement(
				"h2", null, jobJSONObject.getString("description"));
		}
		catch (JSONException jsone) {
			jsone.printStackTrace();
		}

		return Dom4JUtil.getNewElement(
			"body", null, headingElement, subheadingElement,
			getJenkinsReportSummaryElement(),
			getJenkinsReportTimelineElement(nonBatchBuilds),
			getJenkinsReportTopLevelTableElement(),
			getJenkinsReportDownstreamElement());
	}

	protected String getJenkinsReportBuildInfoCellElementTagName() {
		return "th";
	}

	protected Element getJenkinsReportChartJsScriptElement(
		String xData, String y1Data, String y2Data) {

		String resourceFileContent = null;

		try {
			resourceFileContent =
				JenkinsResultsParserUtil.getResourceFileContent(
					"chart-template.js");
		}
		catch (IOException ioe) {
			throw new RuntimeException(
				"Unable to load resource chart-template.js", ioe);
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
			getJenkinsReportDownstreamTableElement("queued", "Queued: "),
			getJenkinsReportDownstreamTableElement("starting", "Starting: "),
			getJenkinsReportDownstreamTableElement("running", "Running: "),
			getJenkinsReportDownstreamTableElement("missing", "Missing: "),
			Dom4JUtil.getNewElement(
				"caption", null, "Completed: ",
				getJenkinsReportDownstreamTableElement(
					"completed/ABORTED", "---- Aborted: "),
				getJenkinsReportDownstreamTableElement(
					"completed/FAILURE", "---- Failure: "),
				getJenkinsReportDownstreamTableElement(
					"completed/SUCCESS", "---- Success: ")));
	}

	protected Element getJenkinsReportDownstreamTableElement(
		String status, String statusCountCaption) {

		List<Element> tableRowElements = getJenkinsReportTableRowsElements(
			status);

		return Dom4JUtil.getNewElement(
			"table", null,
			Dom4JUtil.getNewElement(
				"caption", null, statusCountCaption,
				Integer.toString(tableRowElements.size())),
			getJenkinsReportTableColumnHeadersElement(),
			tableRowElements.toArray(new Element[tableRowElements.size()]));
	}

	protected Element getJenkinsReportHeadElement() {
		Element headElement = Dom4JUtil.getNewElement("head");

		String resourceFileContent = null;

		try {
			resourceFileContent =
				JenkinsResultsParserUtil.getResourceFileContent(
					"jenkins-report.css");
		}
		catch (IOException ioe) {
			throw new RuntimeException(
				"Unable to load resource jenkins-report.css", ioe);
		}

		Dom4JUtil.addToElement(
			headElement,
			Dom4JUtil.getNewElement("style", null, resourceFileContent));

		return headElement;
	}

	protected Element getJenkinsReportSummaryElement() {
		Element summaryElement = Dom4JUtil.getNewElement(
			"div", null,
			Dom4JUtil.getNewElement(
				"p", null, "Start Time: ",
				JenkinsResultsParserUtil.toDateString(
					new Date(getStartTimestamp()))),
			Dom4JUtil.getNewElement(
				"p", null, "Build Time: ",
				JenkinsResultsParserUtil.toDurationString(getDuration())),
			Dom4JUtil.getNewElement(
				"p", null, "Total CPU Usage Time: ",
				JenkinsResultsParserUtil.toDurationString(getTotalDuration())),
			Dom4JUtil.getNewElement(
				"p", null, "Total number of Jenkins slaves used: ",
				Integer.toString(getTotalSlavesUsedCount())));

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

		TestResult longestRunningTest = getLongestRunningTest();

		if (longestRunningTest != null) {
			Dom4JUtil.getNewElement(
				"p", null, "Longest Running Test: ",
				Dom4JUtil.getNewAnchorElement(
					longestRunningTest.getTestReportURL(),
					longestRunningTest.getDisplayName()),
				" in: ",
				JenkinsResultsParserUtil.toDurationString(
					longestRunningTest.getDuration()));
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

	protected Element getJenkinsReportTimelineElement(
		Map<String, Build> nonBatchBuilds) {

		long topLevelDuration = getDuration();
		long topLevelStartTime = getStartTimestamp();

		int dataPoints = 500;

		long[] invocationData = new long[dataPoints];
		long[] slaveUsageData = new long[dataPoints];

		for (String key : nonBatchBuilds.keySet()) {
			Build build = nonBatchBuilds.get(key);

			long buildDuration = build.getDuration();
			long buildStartTime = build.getStartTimestamp();

			long buildEndTime = buildDuration + buildStartTime;

			long dataEnd =
				(buildEndTime - topLevelStartTime) * dataPoints /
					topLevelDuration;

			if (dataEnd > dataPoints) {
				dataEnd = dataPoints;
			}

			long dataStart =
				(buildStartTime - topLevelStartTime) * dataPoints /
					topLevelDuration;

			for (int i = (int)dataStart; i < dataEnd; i++) {
				slaveUsageData[i] = ++slaveUsageData[i];
			}

			invocationData[(int)dataStart] = ++invocationData[(int)dataStart];
		}

		long[] timeData = new long[dataPoints];

		timeData[0] = 0;

		for (int i = 1; i < timeData.length; i++) {
			timeData[i] = timeData[0] + i * topLevelDuration / dataPoints;
		}

		Element canvasElement = Dom4JUtil.getNewElement("canvas");

		canvasElement.addAttribute("height", "300");
		canvasElement.addAttribute("id", "timeline");

		Element scriptElement = Dom4JUtil.getNewElement("script");

		scriptElement.addAttribute("src", _CHART_JS_FILE);
		scriptElement.addText("");

		Element chartJSScriptElement = getJenkinsReportChartJsScriptElement(
			Arrays.toString(timeData), Arrays.toString(slaveUsageData),
			Arrays.toString(invocationData));

		return Dom4JUtil.getNewElement(
			"div", null, canvasElement, scriptElement, chartJSScriptElement);
	}

	protected Element getJenkinsReportTopLevelTableElement() {
		Element topLevelTableElement = Dom4JUtil.getNewElement("table");

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

		return topLevelTableElement;
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

	protected Element getJobSummaryListElement(boolean success) {
		Element jobSummaryListElement = Dom4JUtil.getNewElement("ul");

		List<Build> builds = new ArrayList<>();

		builds.add(this);

		builds.addAll(getDownstreamBuilds(null));

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

	protected Build getLongestRunningBatchBuild() {
		List<Build> downstreamBuilds = getDownstreamBuilds(null);

		long longestDownstreamBuildDuration = 0;

		Build longestRunningDownstreamBuild = null;

		for (Build downstreamBuild : downstreamBuilds) {
			if (downstreamBuild instanceof BatchBuild ||
				downstreamBuild instanceof TopLevelBuild) {

				long downstreamBuildDuration = downstreamBuild.getDuration();

				if (downstreamBuildDuration > longestDownstreamBuildDuration) {
					longestDownstreamBuildDuration = downstreamBuildDuration;

					longestRunningDownstreamBuild = downstreamBuild;
				}
			}
		}

		return longestRunningDownstreamBuild;
	}

	protected Element getMoreDetailsElement() {
		Element moreDetailsElement = Dom4JUtil.getNewElement(
			"h5", null, "For more details click ",
			Dom4JUtil.getNewAnchorElement(getJenkinsReportURL(), "here"), ".");

		return moreDetailsElement;
	}

	protected Element getResultElement() {
		Element resultElement = Dom4JUtil.getNewElement("h1");

		String result = getResult();

		if (!result.equals("SUCCESS")) {
			resultElement.addText("Some tests FAILED.");
		}
		else {
			resultElement.addText("All tests PASSED.");
		}

		return resultElement;
	}

	@Override
	protected String getStartPropertiesTempMapURL() {
		if (fromArchive) {
			return getBuildURL() + "/start.properties.json";
		}

		return JenkinsResultsParserUtil.combine(
			TEMP_MAP_BASE_URL, getMaster(), "/", getJobName(), "/",
			Integer.toString(getBuildNumber()), "/", getJobName(), "/",
			"start.properties");
	}

	@Override
	protected String getStopPropertiesTempMapURL() {
		if (fromArchive) {
			return getBuildURL() + "/stop.properties.json";
		}

		return JenkinsResultsParserUtil.combine(
			TEMP_MAP_BASE_URL, getMaster(), "/", getJobName(), "/",
			Integer.toString(getBuildNumber()), "/", getJobName(), "/",
			"stop.properties");
	}

	protected Element getSuccessfulJobSummaryElement() {
		Element jobSummaryListElement = getJobSummaryListElement(true);

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
					"strong", null, Integer.toString(successCount),
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
				matcher.group("repositoryType"));
		}

		return null;
	}

	protected Element getTopGitHubMessageElement() {
		update();

		Element rootElement = Dom4JUtil.getNewElement(
			"html", null, getResultElement(), getBuildTimeElement(),
			Dom4JUtil.getNewElement("h4", null, "Base Branch:"),
			getBaseBranchDetailsElement());

		int successCount = getDownstreamBuildCountByResult("SUCCESS");

		String result = getResult();

		if ((result != null) && result.equals("SUCCESS")) {
			successCount++;
		}

		Dom4JUtil.addToElement(
			rootElement, Integer.toString(successCount), " out of ",
			Integer.toString(getDownstreamBuildCountByResult(null) + 1),
			" jobs PASSED");

		if (!result.equals("SUCCESS")) {
			Dom4JUtil.addToElement(rootElement, getFailedJobSummaryElement());
		}

		if (getDownstreamBuildCountByResult("SUCCESS") > 0) {
			Dom4JUtil.addToElement(
				rootElement, getSuccessfulJobSummaryElement());
		}

		Dom4JUtil.addToElement(rootElement, getMoreDetailsElement());

		if (!result.equals("SUCCESS")) {
			if (isCompareToUpstream()) {
				loadUpstreamJobFailuresJSONObject();
			}

			Dom4JUtil.addToElement(
				rootElement, Dom4JUtil.getNewElement("hr"),
				Dom4JUtil.getNewElement(
					"h4", null, "Failures unique to this pull:"));

			List<Element> failureElements = new ArrayList<>();
			List<Element> upstreamJobFailureElements = new ArrayList<>();

			for (Build downstreamBuild : getDownstreamBuilds(null)) {
				String downstreamBuildResult = downstreamBuild.getResult();

				if (downstreamBuildResult.equals("SUCCESS")) {
					continue;
				}

				Element failureElement =
					downstreamBuild.getGitHubMessageElement();

				if (failureElement != null) {
					if (isBuildFailingInUpstreamJob(downstreamBuild)) {
						upstreamJobFailureElements.add(failureElement);

						continue;
					}

					if (isHighPriorityBuildFailureElement(failureElement)) {
						failureElements.add(0, failureElement);

						continue;
					}

					failureElements.add(failureElement);
				}

				Element upstreamJobFailureElement =
					downstreamBuild.getGitHubMessageUpstreamJobFailureElement();

				if (upstreamJobFailureElement != null) {
					upstreamJobFailureElements.add(upstreamJobFailureElement);
				}
			}

			failureElements.add(0, super.getGitHubMessageElement());

			int maxFailureCount = 5;

			Dom4JUtil.getOrderedListElement(
				failureElements, rootElement, maxFailureCount);

			String acceptanceUpstreamJobURL = getAcceptanceUpstreamURL();

			if ((failureElements.size() < maxFailureCount) &&
				!upstreamJobFailureElements.isEmpty()) {

				Element acceptanceUpstreamJobLinkElement =
					Dom4JUtil.getNewAnchorElement(
						acceptanceUpstreamJobURL,
						"acceptance upstream results");

				Element upstreamJobFailureElement = Dom4JUtil.getNewElement(
					"details", null,
					Dom4JUtil.getNewElement(
						"summary", null,
						Dom4JUtil.getNewElement(
							"strong", null, "Failures in common with ",
							acceptanceUpstreamJobLinkElement, " at ",
							getUpstreamJobFailuresSHA(), ":")));

				int remainingFailureCount =
					maxFailureCount - failureElements.size();

				Dom4JUtil.getOrderedListElement(
					upstreamJobFailureElements, upstreamJobFailureElement,
					remainingFailureCount);

				Dom4JUtil.addToElement(
					rootElement, Dom4JUtil.getNewElement("hr"),
					upstreamJobFailureElement);
			}

			if (jobName.contains("pullrequest") &&
				upstreamJobFailureElements.isEmpty() &&
				(acceptanceUpstreamJobURL != null)) {

				Dom4JUtil.addToElement(
					Dom4JUtil.getNewElement("h4", rootElement),
					"For upstream results, click ",
					Dom4JUtil.getNewAnchorElement(
						acceptanceUpstreamJobURL, "here"),
					".");
			}
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

	protected static final Pattern gitRepositoryTempMapNamePattern =
		Pattern.compile("git\\.(?<repositoryType>.*)\\.properties");

	private static final String _CHART_JS_FILE =
		"https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.5.0/Chart.min.js";

	private static final long _DOWNSTREAM_BUILDS_LISTING_INTERVAL =
		1000 * 60 * 5;

	private static final FailureMessageGenerator[] _FAILURE_MESSAGE_GENERATORS =
		{
			new PoshiTestFailureMessageGenerator(),
			new PoshiValidationFailureMessageGenerator(),
			new RebaseFailureMessageGenerator(),

			new DownstreamFailureMessageGenerator(),

			new GenericFailureMessageGenerator()
		};

	private boolean _compareToUpstream = true;
	private long _lastDownstreamBuildsListingTimestamp = -1L;
	private long _updateDuration;

}