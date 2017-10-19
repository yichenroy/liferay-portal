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
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
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

	@Override
	public Element getJenkinsReportBuildInfoElement() {
		String topLevelName = getDisplayName();

		topLevelName = topLevelName.replace(jobName + "/", "");

		return super.getJenkinsReportBuildInfoElement(this, topLevelName, true);
	}

	@Override
	public Element getJenkinsReportElement() {
		Element headElement = getJenkinsReportHeadElement();

		Element bodyElement = getJenkinsReportBodyElement();

		Element jenkinsReportElement = Dom4JUtil.getNewElement("html");

		Dom4JUtil.addToElement(jenkinsReportElement, headElement, bodyElement);

		return jenkinsReportElement;
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
		Map<String, Build> nonBatchBuilds = new TreeMap<>();
		Map<String, Build> downstreamBuilds = new TreeMap<>();

		nonBatchBuilds.put(getDisplayName(), this);

		for (Build downstreamBuild : getDownstreamBuilds(null)) {
			String downstreamBuildDisplayName =
				downstreamBuild.getDisplayName();

			if (downstreamBuildDisplayName == null) {
				return null;
			}

			downstreamBuilds.put(downstreamBuildDisplayName, downstreamBuild);

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

		Element h1Element = Dom4JUtil.getNewElement("h1");

		String buildURL = getBuildURL();

		Dom4JUtil.addToElement(
			h1Element, "Jenkins report for ",
			Dom4JUtil.getNewAnchorElement(buildURL, buildURL));

		JSONObject jobJSONObject = getBuildJSONObject();

		String jobDescription = "";

		try {
			jobDescription = jobJSONObject.getString("description");
		}
		catch (JSONException jsone) {
			jsone.printStackTrace();
		}

		Element h2Element = Dom4JUtil.getNewElement("h2");

		Dom4JUtil.addToElement(h2Element, jobDescription);

		Element bodyElement = Dom4JUtil.getNewElement("body");

		Dom4JUtil.addToElement(
			bodyElement, h1Element, h2Element,
			getJenkinsReportSummaryElement(nonBatchBuilds),
			getJenkinsReportTimelineElement(nonBatchBuilds),
			getJenkinsReportTopLevelTableElement(),
			getJenkinsReportDownstreamElement(downstreamBuilds));

		return bodyElement;
	}

	protected Element getJenkinsReportChartJsScriptElement(
		String xData, String y1Data, String y2Data) {

		String resource = null;

		try {
			Class<?> clazz = JenkinsResultsParserUtil.class;

			resource = JenkinsResultsParserUtil.readInputStream(
				clazz.getResourceAsStream("chart-template.js"));
		}
		catch (IOException ioe) {
		}

		resource = resource.replace("'xData'", xData);
		resource = resource.replace("'y1Data'", y1Data);
		resource = resource.replace("'y2Data'", y2Data);

		Element scriptElement = Dom4JUtil.getNewElement("script");

		scriptElement.addText(resource);

		return scriptElement;
	}

	protected Element getJenkinsReportDownstreamElement(
		Map<String, Build> downstreamBuilds) {

		List<Build> abortedBuilds = new ArrayList();

		List<Build> failureBuilds = new ArrayList();

		List<Build> missingBuilds = new ArrayList();

		List<Build> queuedBuilds = new ArrayList();

		List<Build> runningBuilds = new ArrayList();

		List<Build> startingBuilds = new ArrayList();

		List<Build> successBuilds = new ArrayList();

		Set<String> downstreamBuildsKeySet = downstreamBuilds.keySet();

		for (String key : downstreamBuildsKeySet) {
			Build build = downstreamBuilds.get(key);

			switch (build.getStatus()) {

				case "completed":
					String result = build.getResult();

					if (result.equals("SUCCESS")) {
						successBuilds.add(build);
					}
					else if (result.equals("FAILURE") ||
							 result.equals("UNSTABLE")) {

						failureBuilds.add(build);
					}
					else if (result.equals("ABORTED")) {
						abortedBuilds.add(build);
					}

					break;

				case "missing":
					missingBuilds.add(build);
					break;

				case "queued":
					queuedBuilds.add(build);
					break;

				case "running":
					runningBuilds.add(build);
					break;

				case "starting":
					startingBuilds.add(build);
					break;
			}
		}

		Element abortedBatchElement = getJenkinsReportDownstreamTableElement(
			abortedBuilds, "---- Aborted: " + abortedBuilds.size());

		Element failureBatchElement = getJenkinsReportDownstreamTableElement(
			failureBuilds, "---- Failure: " + failureBuilds.size());

		Element successBatchElement = getJenkinsReportDownstreamTableElement(
			successBuilds, "---- Success: " + successBuilds.size());

		Element completedBatchElement = Dom4JUtil.getNewElement("table");

		completedBatchElement.add(
			Dom4JUtil.getNewElement("caption", null, "Completed: "));

		Dom4JUtil.addToElement(
			completedBatchElement, abortedBatchElement, failureBatchElement,
			successBatchElement);

		Element missingBatchElement = getJenkinsReportDownstreamTableElement(
			missingBuilds, "Missing: " + missingBuilds.size());

		Element queuedBatchElement = getJenkinsReportDownstreamTableElement(
			queuedBuilds, "Queued: " + queuedBuilds.size());

		Element runningBatchElement = getJenkinsReportDownstreamTableElement(
			runningBuilds, "Running: " + runningBuilds.size());

		Element startingBatchElement = getJenkinsReportDownstreamTableElement(
			startingBuilds, "Starting: " + startingBuilds.size());

		Element divElement = Dom4JUtil.getNewElement("div");

		Dom4JUtil.addToElement(
			divElement, queuedBatchElement, startingBatchElement,
			runningBatchElement, missingBatchElement, completedBatchElement);

		return divElement;
	}

	protected Element getJenkinsReportDownstreamTableElement(
		List<Build> downstreamBuilds, String statusCountCaption) {

		Element tableElement = Dom4JUtil.getNewElement("table");

		tableElement.add(
			Dom4JUtil.getNewElement("caption", null, statusCountCaption));

		if (!downstreamBuilds.isEmpty()) {
			tableElement.add(getJenkinsReportTableColumnHeaderElement());
		}

		for (Build batchBuild : downstreamBuilds) {
			Element trBatchElement = Dom4JUtil.getNewElement("tr");

			trBatchElement.add(batchBuild.getJenkinsReportBuildInfoElement());

			tableElement.add(trBatchElement);

			for (Build downstreamBuild : batchBuild.getDownstreamBuilds(null)) {
				tableElement.add(
					downstreamBuild.getJenkinsReportBuildInfoElement());
			}
		}

		return tableElement;
	}

	protected Element getJenkinsReportHeadElement() {
		Element headElement = Dom4JUtil.getNewElement("head");

		String style = JenkinsResultsParserUtil.combine(
			"caption, table, td, th {", "text-align: left;", "padding: .5em;",
			"white-space: nowrap;", "}", "th:first-child {",
			"text-indent: 1em;", "}", "td:first-child {", "text-indent: 4em;",
			"}", "caption {", "font-size: 150%;", "font-weight: bold;", "}");

		Dom4JUtil.addToElement(
			headElement, Dom4JUtil.getNewElement("style", null, style));

		return headElement;
	}

	protected Element getJenkinsReportSummaryElement(
		Map<String, Build> nonBatchBuildss) {

		Element buildTimeElement = Dom4JUtil.getNewElement(
			"p", null, "Build Time: ",
			JenkinsResultsParserUtil.toDurationString(getDuration()));

		Element ciUsageElement = getJenkinsReportTotalCIUsageElement(
			nonBatchBuildss);

		Element longestAxisElement = getLongestRunningDownstreamBuildElement();

		Element longestBatchElement = getLongestRunningBatchElement();

		Element longestTestElement = getLongestRunningTestElement();

		Date startTime = new Date(getStartTimestamp());

		Element startTimeElement = Dom4JUtil.getNewElement(
			"p", null, "Start Time: ",
			JenkinsResultsParserUtil.toDateString(startTime));

		Element vmUsageElement = getJenkinsReportTotalVMUSageElement(
			nonBatchBuildss);

		Element divElement = Dom4JUtil.getNewElement("div");

		Dom4JUtil.addToElement(
			divElement, startTimeElement, buildTimeElement, ciUsageElement,
			vmUsageElement, longestBatchElement, longestAxisElement,
			longestTestElement);

		return divElement;
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
			topLevelTableElement, getJenkinsReportTableColumnHeaderElement(),
			getJenkinsReportBuildInfoElement());

		return topLevelTableElement;
	}

	protected Element getJenkinsReportTotalCIUsageElement(
		Map<String, Build> nonBatchBuilds) {

		long totalTime = 0;

		for (Build nonBatchBuild : nonBatchBuilds.values()) {
			long axisDuration = nonBatchBuild.getDuration();

			totalTime = totalTime + axisDuration;
		}

		long hoursTotalTime = totalTime / 3600000;

		Element totalCIUsageElement = Dom4JUtil.getNewElement(
			"p", null, "Total CI Usage: " + hoursTotalTime + " server hours");

		return totalCIUsageElement;
	}

	protected Element getJenkinsReportTotalVMUSageElement(
		Map<String, Build> nonBatchBuilds) {

		long totalVMUsed = nonBatchBuilds.size();

		Element totalVMUsageElement = Dom4JUtil.getNewElement(
			"p", null, "Total VM used: " + totalVMUsed + " slaves");

		return totalVMUsageElement;
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

		long longestBatchDuration = 0;

		Build longestRunningBatchBuild = null;

		for (Build downstreamBuild : downstreamBuilds) {
			if (downstreamBuild instanceof TopLevelBuild) {
				downstreamBuild =
					((TopLevelBuild)
						downstreamBuild).getLongestRunningBatchBuild();
			}

			if (downstreamBuild instanceof BatchBuild) {
				long batchDuration = downstreamBuild.getDuration();

				if (batchDuration > longestBatchDuration) {
					longestBatchDuration = batchDuration;

					longestRunningBatchBuild = downstreamBuild;
				}
			}
		}

		return longestRunningBatchBuild;
	}

	protected Element getLongestRunningBatchElement() {
		String jobName = "Unavailable";

		long longestBatchDuration = 0;

		String longestBatchName = "Unavailable";

		String longestBatchURL = "Unavailable";

		Build longestRunningBatchBuild = getLongestRunningBatchBuild();

		if (!(longestRunningBatchBuild == null)) {
			jobName = longestRunningBatchBuild.getJobName();

			longestBatchDuration = longestRunningBatchBuild.getDuration();

			longestBatchName = longestRunningBatchBuild.getDisplayName();

			longestBatchURL = longestRunningBatchBuild.getBuildURL();
		}

		String longestBatchDisplayName = longestBatchName.replace(
			jobName + "/", "");

		Element longestBatchElement = Dom4JUtil.getNewElement(
			"p", null, "Longest Batch: ",
			Dom4JUtil.getNewAnchorElement(
				longestBatchURL, longestBatchDisplayName),
			" in: ",
			JenkinsResultsParserUtil.toDurationString(longestBatchDuration));

		return longestBatchElement;
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