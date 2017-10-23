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

import com.liferay.jenkins.results.parser.failure.message.generator.FailureMessageGenerator;
import com.liferay.jenkins.results.parser.failure.message.generator.GenericFailureMessageGenerator;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Element;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Kevin Yen
 */
public abstract class BaseBuild implements Build {

	@Override
	public void addDownstreamBuilds(String... urls) {
		for (String url : urls) {
			try {
				url = JenkinsResultsParserUtil.getLocalURL(
					JenkinsResultsParserUtil.decode(url));
			}
			catch (UnsupportedEncodingException uee) {
				throw new IllegalArgumentException(
					"Unable to decode " + url, uee);
			}

			if (!hasBuildURL(url)) {
				downstreamBuilds.add(BuildFactory.newBuild(url, this));
			}
		}
	}

	@Override
	public void archive(final String archiveName) {
		if (!_status.equals("completed")) {
			throw new RuntimeException("Invalid build status: " + _status);
		}

		this.archiveName = archiveName;

		File archiveDir = new File(getArchivePath());

		if (archiveDir.exists()) {
			archiveDir.delete();
		}

		if (downstreamBuilds != null) {
			ExecutorService executorService = getExecutorService();

			for (final Build downstreamBuild : downstreamBuilds) {
				if (executorService != null) {
					Runnable runnable = new Runnable() {

						@Override
						public void run() {
							downstreamBuild.archive(archiveName);
						}

					};

					executorService.execute(runnable);
				}
				else {
					downstreamBuild.archive(archiveName);
				}
			}

			if (executorService != null) {
				executorService.shutdown();

				while (!executorService.isTerminated()) {
					JenkinsResultsParserUtil.sleep(100);
				}
			}
		}

		try {
			writeArchiveFile(
				Long.toString(System.currentTimeMillis()),
				getArchivePath() + "/archive-marker");
		}
		catch (IOException ioe) {
			throw new RuntimeException(
				"Unable to to write archive-marker file", ioe);
		}

		archiveConsoleLog();
		archiveJSON();
	}

	@Override
	public String getAppServer() {
		return null;
	}

	@Override
	public String getArchivePath() {
		StringBuilder sb = new StringBuilder(archiveName);

		if (!archiveName.endsWith("/")) {
			sb.append("/");
		}

		sb.append(getMaster());
		sb.append("/");
		sb.append(getJobName());
		sb.append("/");
		sb.append(getBuildNumber());

		return sb.toString();
	}

	@Override
	public List<String> getBadBuildURLs() {
		List<String> badBuildURLs = new ArrayList<>();

		String jobURL = getJobURL();

		for (Integer badBuildNumber : badBuildNumbers) {
			badBuildURLs.add(
				JenkinsResultsParserUtil.combine(
					jobURL, "/", Integer.toString(badBuildNumber), "/"));
		}

		return badBuildURLs;
	}

	@Override
	public String getBaseRepositoryName() {
		if (repositoryName == null) {
			Properties buildProperties = null;

			try {
				buildProperties = JenkinsResultsParserUtil.getBuildProperties();
			}
			catch (IOException ioe) {
				throw new RuntimeException(
					"Unable to get build.properties", ioe);
			}

			TopLevelBuild topLevelBuild = getTopLevelBuild();

			repositoryName = topLevelBuild.getParameterValue("REPOSITORY_NAME");

			if ((repositoryName != null) && !repositoryName.isEmpty()) {
				return repositoryName;
			}

			repositoryName = buildProperties.getProperty(
				JenkinsResultsParserUtil.combine(
					"repository[", topLevelBuild.getJobName(), "]"));

			if (repositoryName == null) {
				throw new RuntimeException(
					"Unable to get repository name for job " +
						topLevelBuild.getJobName());
			}
		}

		return repositoryName;
	}

	@Override
	public String getBaseRepositorySHA(String repositoryName) {
		TopLevelBuild topLevelBuild = getTopLevelBuild();

		if (repositoryName.equals("liferay-jenkins-ee")) {
			Map<String, String> topLevelBuildStartPropertiesTempMap =
				topLevelBuild.getStartPropertiesTempMap();

			return topLevelBuildStartPropertiesTempMap.get(
				"JENKINS_GITHUB_UPSTREAM_BRANCH_SHA");
		}

		Map<String, String> repositoryGitDetailsTempMap =
			topLevelBuild.getBaseGitRepositoryDetailsTempMap();

		return repositoryGitDetailsTempMap.get("github.upstream.branch.sha");
	}

	@Override
	public String getBranchName() {
		return branchName;
	}

	@Override
	public String getBrowser() {
		return null;
	}

	@Override
	public JSONObject getBuildJSONObject() {
		try {
			return JenkinsResultsParserUtil.toJSONObject(
				JenkinsResultsParserUtil.getLocalURL(
					getBuildURL() + "api/json"),
				false);
		}
		catch (IOException ioe) {
			throw new RuntimeException("Unable to get build JSON object", ioe);
		}
	}

	@Override
	public int getBuildNumber() {
		return _buildNumber;
	}

	@Override
	public String getBuildURL() {
		String jobURL = getJobURL();

		if ((jobURL == null) || (_buildNumber == -1)) {
			return null;
		}

		if (fromArchive) {
			return jobURL + "/" + _buildNumber + "/";
		}

		try {
			jobURL = JenkinsResultsParserUtil.decode(jobURL);

			return JenkinsResultsParserUtil.encode(
				jobURL + "/" + _buildNumber + "/");
		}
		catch (MalformedURLException | URISyntaxException e) {
			throw new RuntimeException("Unable to encode build URL", e);
		}
		catch (UnsupportedEncodingException uee) {
			throw new RuntimeException(
				"Unable to decode job URL " + jobURL, uee);
		}
	}

	@Override
	public String getBuildURLRegex() {
		StringBuffer sb = new StringBuffer();

		sb.append("http[s]*:\\/\\/");
		sb.append(JenkinsResultsParserUtil.getRegexLiteral(getMaster()));
		sb.append("[^\\/]*");
		sb.append("[\\/]+job[\\/]+");

		String jobNameRegexLiteral = JenkinsResultsParserUtil.getRegexLiteral(
			getJobName());

		jobNameRegexLiteral = jobNameRegexLiteral.replace("\\(", "(\\(|%28)");
		jobNameRegexLiteral = jobNameRegexLiteral.replace("\\)", "(\\)|%29)");

		sb.append(jobNameRegexLiteral);

		sb.append("[\\/]+");
		sb.append(getBuildNumber());
		sb.append("[\\/]*");

		String buildURLRegex = sb.toString();

		return buildURLRegex;
	}

	@Override
	public String getConsoleText() {
		if (_consoleText != null) {
			return _consoleText;
		}

		String buildURL = getBuildURL();

		if (buildURL != null) {
			JenkinsConsoleTextLoader jenkinsConsoleTextLoader =
				new JenkinsConsoleTextLoader(getBuildURL());

			String consoleText = jenkinsConsoleTextLoader.getConsoleText();

			if (consoleText.contains("\nFinished:") &&
				(getParentBuild() == null)) {

				_consoleText = consoleText;
			}

			return consoleText;
		}

		return "";
	}

	@Override
	public String getDatabase() {
		return null;
	}

	@Override
	public String getDisplayName() {
		StringBuilder sb = new StringBuilder();

		sb.append(getJobName());

		String jobVariant = getParameterValue("JOB_VARIANT");

		if ((jobVariant != null) && !jobVariant.isEmpty()) {
			sb.append("/");
			sb.append(jobVariant);
		}

		return sb.toString();
	}

	@Override
	public int getDownstreamBuildCount(String status) {
		List<Build> downstreamBuilds = getDownstreamBuilds(status);

		return downstreamBuilds.size();
	}

	@Override
	public List<Build> getDownstreamBuilds(String status) {
		if (status == null) {
			return downstreamBuilds;
		}

		List<Build> filteredDownstreamBuilds = new ArrayList<>();

		for (Build downstreamBuild : downstreamBuilds) {
			if (status.equals(downstreamBuild.getStatus())) {
				filteredDownstreamBuilds.add(downstreamBuild);
			}
		}

		return filteredDownstreamBuilds;
	}

	@Override
	public long getDuration() {
		JSONObject buildJSONObject = getBuildJSONObject("duration,timestamp");

		long duration = buildJSONObject.getLong("duration");

		if (duration == 0) {
			long timestamp = buildJSONObject.getLong("timestamp");

			duration = System.currentTimeMillis() - timestamp;
		}

		return duration;
	}

	@Override
	public Element getGitHubMessageBuildAnchorElement() {
		getResult();

		int i = 0;

		while (result == null) {
			if (i == 20) {
				throw new RuntimeException(
					JenkinsResultsParserUtil.combine(
						"Unable to create build anchor element. The process ",
						"timed out while waiting for a build result for ",
						getBuildURL(), "."));
			}

			JenkinsResultsParserUtil.sleep(1000 * 30);

			getResult();

			i++;
		}

		if (result.equals("SUCCESS")) {
			return Dom4JUtil.getNewAnchorElement(
				getBuildURL(), getDisplayName());
		}

		return Dom4JUtil.getNewAnchorElement(
			getBuildURL(), null,
			Dom4JUtil.getNewElement(
				"strike", null,
				Dom4JUtil.getNewElement("strong", null, getDisplayName())));
	}

	@Override
	public Element getGitHubMessageElement() {
		return getGitHubMessageElement(false);
	}

	public Element getGitHubMessageElement(boolean showCommonFailuresCount) {
		String status = getStatus();

		if (!status.equals("completed") && (getParentBuild() != null)) {
			return null;
		}

		String result = getResult();

		if (result.equals("SUCCESS")) {
			return null;
		}

		Element messageElement = Dom4JUtil.getNewElement("div");

		Dom4JUtil.addToElement(
			messageElement,
			Dom4JUtil.getNewElement(
				"h5", null,
				Dom4JUtil.getNewAnchorElement(
					getBuildURL(), getDisplayName())));

		if (showCommonFailuresCount) {
			Dom4JUtil.addToElement(
				messageElement,
				getGitHubMessageJobResultsElement(showCommonFailuresCount));
		}
		else {
			Dom4JUtil.addToElement(
				messageElement, getGitHubMessageJobResultsElement());
		}

		if (result.equals("ABORTED") && (getDownstreamBuildCount(null) == 0)) {
			messageElement.add(
				Dom4JUtil.toCodeSnippetElement("Build was aborted"));

			return messageElement;
		}

		Element failureMessageElement = getFailureMessageElement();

		if (failureMessageElement != null) {
			messageElement.add(failureMessageElement);
		}

		return messageElement;
	}

	@Override
	public Element getGitHubMessageUpstreamJobFailureElement() {
		return upstreamJobFailureMessageElement;
	}

	@Override
	public String getInvocationURL() {
		String jobURL = getJobURL();

		if (jobURL == null) {
			return null;
		}

		StringBuffer sb = new StringBuffer(jobURL);

		sb.append("/buildWithParameters?");

		Map<String, String> parameters = getParameters();

		parameters.put("token", "raen3Aib");

		for (Map.Entry<String, String> parameter : parameters.entrySet()) {
			String value = parameter.getValue();

			if ((value != null) && !value.isEmpty()) {
				sb.append(parameter.getKey());
				sb.append("=");
				sb.append(parameter.getValue());
				sb.append("&");
			}
		}

		sb.deleteCharAt(sb.length() - 1);

		try {
			return JenkinsResultsParserUtil.encode(sb.toString());
		}
		catch (MalformedURLException | URISyntaxException e) {
			throw new RuntimeException(
				"Unable to encode URL " + sb.toString(), e);
		}
	}

	@Override
	public String getJDK() {
		return null;
	}

	@Override
	public String getJobName() {
		return jobName;
	}

	@Override
	public String getJobURL() {
		if ((master == null) || (jobName == null)) {
			return null;
		}

		if (fromArchive) {
			return "${dependencies.url}/" + archiveName + "/" + master + "/" +
				jobName;
		}

		String jobURL = JenkinsResultsParserUtil.combine(
			"https://", master, ".liferay.com/job/", jobName);

		try {
			return JenkinsResultsParserUtil.encode(jobURL);
		}
		catch (MalformedURLException | URISyntaxException e) {
			throw new RuntimeException("Unable to encode job URL " + jobURL, e);
		}
	}

	@Override
	public String getJobVariant() {
		String batchName = getParameterValue("JOB_VARIANT");

		if ((batchName == null) || batchName.isEmpty()) {
			batchName = getParameterValue("JENKINS_JOB_VARIANT");
		}

		return batchName;
	}

	@Override
	public int getJobVariantsDownstreamBuildCount(List<String> jobVariants) {
		List<Build> jobVariantsDownstreamBuilds =
			getJobVariantsDownstreamBuilds(jobVariants);

		return jobVariantsDownstreamBuilds.size();
	}

	@Override
	public List<Build> getJobVariantsDownstreamBuilds(
		List<String> jobVariants) {

		List<Build> jobVariantsDownstreamBuilds = new ArrayList<>();

		for (Build downstreamBuild : downstreamBuilds) {
			String downstreamBuildJobVariant = downstreamBuild.getJobVariant();

			for (String jobVariant : jobVariants) {
				if (downstreamBuildJobVariant.contains(jobVariant)) {
					jobVariantsDownstreamBuilds.add(downstreamBuild);

					break;
				}
			}
		}

		return jobVariantsDownstreamBuilds;
	}

	@Override
	public Long getLatestStartTimestamp() {
		Long latestStartTimestamp = getStartTimestamp();

		if (latestStartTimestamp == null) {
			return null;
		}

		for (Build downstreamBuild : getDownstreamBuilds(null)) {
			Long downstreamBuildLatestStartTimestamp =
				downstreamBuild.getLatestStartTimestamp();

			if (downstreamBuildLatestStartTimestamp == null) {
				return null;
			}

			latestStartTimestamp = Math.max(
				latestStartTimestamp,
				downstreamBuild.getLatestStartTimestamp());
		}

		return latestStartTimestamp;
	}

	@Override
	public String getMaster() {
		return master;
	}

	@Override
	public String getOperatingSystem() {
		return null;
	}

	@Override
	public Map<String, String> getParameters() {
		return new HashMap<>(_parameters);
	}

	@Override
	public String getParameterValue(String name) {
		return _parameters.get(name);
	}

	@Override
	public Build getParentBuild() {
		return _parentBuild;
	}

	@Override
	public String getResult() {
		if ((result == null) && (getBuildURL() != null)) {
			JSONObject resultJSONObject = getBuildJSONObject("result");

			result = resultJSONObject.optString("result");

			if (result.equals("")) {
				result = null;
			}
		}

		return result;
	}

	@Override
	public String getSlave() {
		if ((slave == null) && (getBuildURL() != null)) {
			JSONObject builtOnJSONObject = getBuildJSONObject("builtOn");

			slave = builtOnJSONObject.optString("builtOn");

			if (slave.equals("")) {
				slave = "master";
			}
		}

		return slave;
	}

	@Override
	public Map<String, String> getStartPropertiesTempMap() {
		return getTempMap("start.properties");
	}

	@Override
	public Long getStartTimestamp() {
		JSONObject buildJSONObject = getBuildJSONObject("timestamp");

		if (buildJSONObject == null) {
			return null;
		}

		long timestamp = buildJSONObject.getLong("timestamp");

		if (timestamp == 0) {
			return null;
		}

		return timestamp;
	}

	@Override
	public String getStatus() {
		return _status;
	}

	@Override
	public long getStatusAge() {
		return System.currentTimeMillis() - statusModifiedTime;
	}

	@Override
	public String getStatusReport() {
		return getStatusReport(0);
	}

	@Override
	public String getStatusReport(int indentSize) {
		StringBuffer indentStringBuffer = new StringBuffer();

		for (int i = 0; i < indentSize; i++) {
			indentStringBuffer.append(" ");
		}

		StringBuilder sb = new StringBuilder();

		sb.append(indentStringBuffer);
		sb.append("Build \"");
		sb.append(jobName);
		sb.append("\"");

		String status = getStatus();

		if (status.equals("completed")) {
			sb.append(" completed at ");
			sb.append(getBuildURL());
			sb.append(". ");
			sb.append(getResult());

			return sb.toString();
		}

		if (status.equals("missing")) {
			sb.append(" is missing ");
			sb.append(getJobURL());
			sb.append(".");

			return sb.toString();
		}

		if (status.equals("queued")) {
			sb.append(" is queued at ");
			sb.append(getJobURL());
			sb.append(".");

			return sb.toString();
		}

		if (status.equals("running")) {
			sb.append(" running at ");
			sb.append(getBuildURL());
			sb.append(".\n");

			if (getDownstreamBuildCount(null) > 0) {
				sb.append("\n");

				for (Build downstreamBuild : getDownstreamBuilds("running")) {
					sb.append(downstreamBuild.getStatusReport(indentSize + 4));
				}

				sb.append("\n");
				sb.append(indentStringBuffer);
				sb.append(getStatusSummary());
				sb.append("\n");
			}

			return sb.toString();
		}

		if (status.equals("starting")) {
			sb.append(" invoked at ");
			sb.append(getJobURL());
			sb.append(".");

			return sb.toString();
		}

		throw new RuntimeException("Unknown status: " + status);
	}

	@Override
	public String getStatusSummary() {
		return JenkinsResultsParserUtil.combine(
			Integer.toString(getDownstreamBuildCount("starting")),
			" Starting  ", "/ ",

			Integer.toString(getDownstreamBuildCount("missing")), " Missing  ",
			"/ ",

			Integer.toString(getDownstreamBuildCount("queued")), " Queued  ",
			"/ ",

			Integer.toString(getDownstreamBuildCount("running")), " Running  ",
			"/ ",

			Integer.toString(getDownstreamBuildCount("completed")),
			" Completed  ", "/ ",

			Integer.toString(getDownstreamBuildCount(null)), " Total ");
	}

	@Override
	public Map<String, String> getStopPropertiesTempMap() {
		return getTempMap("stop.properties");
	}

	@Override
	public JSONObject getTestReportJSONObject() {
		try {
			return JenkinsResultsParserUtil.toJSONObject(
				JenkinsResultsParserUtil.getLocalURL(
					getBuildURL() + "testReport/api/json"),
				false);
		}
		catch (IOException ioe) {
			throw new RuntimeException(
				"Unable to get test report JSON object", ioe);
		}
	}

	@Override
	public List<TestResult> getTestResults(String testStatus) {
		List<TestResult> testResults = new ArrayList<>();

		for (Build downstreamBuild : getDownstreamBuilds(null)) {
			testResults.addAll(downstreamBuild.getTestResults(testStatus));
		}

		return testResults;
	}

	@Override
	public TopLevelBuild getTopLevelBuild() {
		Build topLevelBuild = this;

		while ((topLevelBuild != null) &&
		 !(topLevelBuild instanceof TopLevelBuild)) {

			topLevelBuild = topLevelBuild.getParentBuild();
		}

		return (TopLevelBuild)topLevelBuild;
	}

	@Override
	public boolean hasBuildURL(String buildURL) {
		try {
			buildURL = JenkinsResultsParserUtil.decode(buildURL);
		}
		catch (UnsupportedEncodingException uee) {
			throw new RuntimeException("Unable to decode " + buildURL, uee);
		}

		buildURL = JenkinsResultsParserUtil.getLocalURL(buildURL);

		String thisBuildURL = getBuildURL();

		if (thisBuildURL != null) {
			thisBuildURL = JenkinsResultsParserUtil.getLocalURL(thisBuildURL);

			if (thisBuildURL.equals(buildURL)) {
				return true;
			}
		}

		for (Build downstreamBuild : downstreamBuilds) {
			if (downstreamBuild.hasBuildURL(buildURL)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public void reinvoke() {
		reinvoke(null);
	}

	@Override
	public void reinvoke(ReinvokeRule reinvokeRule) {
		String hostName = JenkinsResultsParserUtil.getHostName("");

		Build parentBuild = getParentBuild();

		String parentBuildStatus = parentBuild.getStatus();

		if (!parentBuildStatus.equals("running") ||
			!hostName.startsWith("cloud-10-0")) {

			return;
		}

		if ((reinvokeRule != null) && !fromArchive) {
			String message = JenkinsResultsParserUtil.combine(
				reinvokeRule.getName(), " failure detected at ", getBuildURL(),
				". This build will be reinvoked.\n\n", reinvokeRule.toString(),
				"\n\n");

			System.out.println(message);

			TopLevelBuild topLevelBuild = getTopLevelBuild();

			if (topLevelBuild != null) {
				message = JenkinsResultsParserUtil.combine(
					message, "Top Level Build URL: ",
					topLevelBuild.getBuildURL());
			}

			String notificationRecipients =
				reinvokeRule.getNotificationRecipients();

			if ((notificationRecipients != null) &&
				!notificationRecipients.isEmpty()) {

				try {
					JenkinsResultsParserUtil.sendEmail(
						message, "jenkins", "Build Reinvoked",
						reinvokeRule.notificationRecipients);
				}
				catch (
					InterruptedException | IOException | TimeoutException e) {

					throw new RuntimeException(
						"Unable to send reinvoke notification", e);
				}
			}
		}

		String invocationURL = getInvocationURL();

		try {
			JenkinsResultsParserUtil.toString(
				JenkinsResultsParserUtil.getLocalURL(invocationURL));
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}

		System.out.println(getReinvokedMessage());

		reset();
	}

	@Override
	public String replaceBuildURL(String text) {
		if ((text == null) || text.isEmpty()) {
			return text;
		}

		if (downstreamBuilds != null) {
			for (Build downstreamBuild : downstreamBuilds) {
				Build downstreamBaseBuild = downstreamBuild;

				text = downstreamBaseBuild.replaceBuildURL(text);
			}
		}

		text = text.replaceAll(
			getBuildURLRegex(),
			Matcher.quoteReplacement(
				"${dependencies.url}/" + getArchivePath()));

		Build parentBuild = getParentBuild();

		while (parentBuild != null) {
			text = text.replaceAll(
				parentBuild.getBuildURLRegex(),
				Matcher.quoteReplacement(
					"${dependencies.url}/" + parentBuild.getArchivePath()));

			parentBuild = parentBuild.getParentBuild();
		}

		return text;
	}

	@Override
	public void setCompareToUpstream(boolean compareToUpstream) {
	}

	@Override
	public void takeSlaveOffline(SlaveOfflineRule slaveOfflineRule) {
		if ((slaveOfflineRule == null) || fromArchive) {
			return;
		}

		slave = getSlave();

		if (slave == null) {
			return;
		}

		String message = JenkinsResultsParserUtil.combine(
			slaveOfflineRule.getName(), " failure detected at ", getBuildURL(),
			". ", slave, " will be taken offline.\n\n",
			slaveOfflineRule.toString(), "\n\n\nOffline Slave URL: https://",
			master, ".liferay.com/computer/", slave, "\n");

		System.out.println(message);

		TopLevelBuild topLevelBuild = getTopLevelBuild();

		if (topLevelBuild != null) {
			message = JenkinsResultsParserUtil.combine(
				message, "Top Level Build URL: ", topLevelBuild.getBuildURL());
		}

		JenkinsResultsParserUtil.takeSlavesOffline(master, message, slave);

		String notificationRecipients =
			slaveOfflineRule.getNotificationRecipients();

		if ((notificationRecipients != null) &&
			!notificationRecipients.isEmpty()) {

			try {
				JenkinsResultsParserUtil.sendEmail(
					message, "jenkins", "Slave Offline",
					slaveOfflineRule.notificationRecipients);
			}
			catch (InterruptedException | IOException | TimeoutException e) {
				throw new RuntimeException(
					"Unable to send offline slave notification", e);
			}
		}
	}

	@Override
	public void update() {
		String status = getStatus();

		if (!status.equals("completed")) {
			try {
				if (status.equals("missing") || status.equals("queued") ||
					status.equals("starting")) {

					JSONObject runningBuildJSONObject =
						getRunningBuildJSONObject();

					if (runningBuildJSONObject != null) {
						setBuildNumber(runningBuildJSONObject.getInt("number"));
					}
					else {
						JSONObject queueItemJSONObject =
							getQueueItemJSONObject();

						if (status.equals("starting") &&
							(queueItemJSONObject != null)) {

							setStatus("queued");
						}
						else if (status.equals("queued") &&
								 (queueItemJSONObject == null)) {

							setStatus("missing");
						}
					}
				}

				slave = getSlave();
				status = getStatus();

				if (downstreamBuilds != null) {
					ExecutorService executorService = getExecutorService();

					for (final Build downstreamBuild : downstreamBuilds) {
						if (executorService != null) {
							Runnable runnable = new Runnable() {

								@Override
								public void run() {
									downstreamBuild.update();
								}

							};

							executorService.execute(runnable);
						}
						else {
							downstreamBuild.update();
						}
					}

					if (executorService != null) {
						executorService.shutdown();

						while (!executorService.isTerminated()) {
							JenkinsResultsParserUtil.sleep(100);
						}
					}

					String result = getResult();

					if ((downstreamBuilds.size() ==
							getDownstreamBuildCount("completed")) &&
						(result != null)) {

						setStatus("completed");
					}

					findDownstreamBuilds();

					if ((result == null) || result.equals("SUCCESS")) {
						return;
					}

					if (!(this instanceof TopLevelBuild) && !fromArchive) {
						for (SlaveOfflineRule slaveOfflineRule :
								slaveOfflineRules) {

							if (!slaveOfflineRule.matches(this)) {
								continue;
							}

							takeSlaveOffline(slaveOfflineRule);

							break;
						}
					}

					if (this instanceof AxisBuild ||
						this instanceof BatchBuild ||
						this instanceof TopLevelBuild || fromArchive ||
						(badBuildNumbers.size() >= MAX_REINVOCATIONS)) {

						return;
					}

					for (ReinvokeRule reinvokeRule : reinvokeRules) {
						if (!reinvokeRule.matches(this)) {
							continue;
						}

						reinvoke(reinvokeRule);

						break;
					}
				}
			}
			catch (IOException ioe) {
				throw new RuntimeException(ioe);
			}
		}
	}

	public static class BuildDisplayNameComparator
		implements Comparator<Build> {

		@Override
		public int compare(Build build1, Build build2) {
			String displayName1 = build1.getDisplayName();
			String displayName2 = build2.getDisplayName();

			return displayName1.compareTo(displayName2);
		}

	}

	protected static List<String> getUpstreamJobFailures(String type) {
		List<String> upstreamFailures = new ArrayList<>();

		JSONArray failedBatchesJSONArray =
			upstreamFailuresJobJSONObject.getJSONArray("failedBatches");

		for (int i = 0; i < failedBatchesJSONArray.length(); i++) {
			JSONObject failedBatchJSONObject =
				failedBatchesJSONArray.getJSONObject(i);

			JSONArray failedTestsJSONArray = failedBatchJSONObject.getJSONArray(
				"failedTests");

			String jobVariant = failedBatchJSONObject.getString("jobVariant");

			if (type.equals("build")) {
				if (failedTestsJSONArray.length() == 0) {
					upstreamFailures.add(
						JenkinsResultsParserUtil.combine(
							jobVariant, ",",
							failedBatchJSONObject.getString("result")));
				}
			}
			else if (type.equals("test")) {
				for (int j = 0; j < failedTestsJSONArray.length(); j++) {
					Object object = failedTestsJSONArray.get(j);

					upstreamFailures.add(
						JenkinsResultsParserUtil.combine(
							object.toString(), ",", jobVariant));
				}
			}
		}

		return upstreamFailures;
	}

	protected static String getUpstreamJobFailuresSHA() {
		try {
			return upstreamFailuresJobJSONObject.getString("SHA");
		}
		catch (JSONException jsone) {
			System.out.println(
				"Unable to get upstream acceptance failure data");

			jsone.printStackTrace();

			return "";
		}
	}

	protected static boolean isBuildFailingInUpstreamJob(Build build) {
		try {
			List<TestResult> testResults = new ArrayList<>();

			testResults.addAll(build.getTestResults("FAILED"));
			testResults.addAll(build.getTestResults("REGRESSION"));

			if (testResults.isEmpty()) {
				String jobVariant = build.getJobVariant();
				String result = build.getResult();

				if (jobVariant.contains("/")) {
					int index = jobVariant.lastIndexOf("/");

					jobVariant = jobVariant.substring(0, index);
				}

				for (String upstreamJobFailure :
						getUpstreamJobFailures("build")) {

					if (upstreamJobFailure.contains(jobVariant) &&
						upstreamJobFailure.contains(result)) {

						return true;
					}
				}
			}

			return false;
		}
		catch (Exception e) {
			System.out.println(
				"Unable to get upstream acceptance failure data.");

			e.printStackTrace();

			return false;
		}
	}

	protected static boolean isHighPriorityBuildFailureElement(
		Element gitHubMessage) {

		String content = null;

		try {
			content = Dom4JUtil.format(gitHubMessage, false);
		}
		catch (IOException ioe) {
			throw new RuntimeException("Unable to format github message.", ioe);
		}

		for (String contentFlag : _HIGH_PRIORITY_CONTENT_FLAGS) {
			if (content.contains(contentFlag)) {
				return true;
			}
		}

		return false;
	}

	protected static boolean isTestFailingInUpstreamJob(TestResult testResult) {
		try {
			for (String failure : getUpstreamJobFailures("test")) {
				Build build = testResult.getBuild();

				String jobVariant = build.getJobVariant();

				if (jobVariant.contains("/")) {
					int index = jobVariant.lastIndexOf("/");

					jobVariant = jobVariant.substring(0, index);
				}

				if (failure.contains(jobVariant) &&
					failure.contains(testResult.getDisplayName())) {

					return true;
				}
			}

			return false;
		}
		catch (Exception e) {
			System.out.println(
				"Unable to get upstream acceptance failure data.");

			e.printStackTrace();

			return false;
		}
	}

	protected BaseBuild(String url) {
		this(url, null);
	}

	protected BaseBuild(String url, Build parentBuild) {
		_parentBuild = parentBuild;

		if (url.contains("buildWithParameters")) {
			setInvocationURL(url);
		}
		else {
			setBuildURL(url);
		}

		update();
	}

	protected void archiveConsoleLog() {
		downloadSampleURL(
			getArchivePath(), true, getBuildURL(), "/consoleText");
	}

	protected void archiveJSON() {
		downloadSampleURL(getArchivePath(), true, getBuildURL(), "api/json");
		downloadSampleURL(
			getArchivePath(), false, getBuildURL(), "testReport/api/json");

		if (!getStartPropertiesTempMap().isEmpty()) {
			try {
				JSONObject startPropertiesTempMapJSONObject =
					JenkinsResultsParserUtil.toJSONObject(
						getStartPropertiesTempMapURL());

				writeArchiveFile(
					startPropertiesTempMapJSONObject.toString(4),
					getArchivePath() + "/start.properties.json");
			}
			catch (IOException ioe) {
				throw new RuntimeException(
					"Unable to create start.properties.json", ioe);
			}
		}

		if (!getStopPropertiesTempMap().isEmpty()) {
			try {
				JSONObject stopPropertiesTempMapJSONObject =
					JenkinsResultsParserUtil.toJSONObject(
						getStopPropertiesTempMapURL());

				writeArchiveFile(
					stopPropertiesTempMapJSONObject.toString(4),
					getArchivePath() + "/stop.properties.json");
			}
			catch (IOException ioe) {
				throw new RuntimeException(
					"Unable to create stop.properties.json", ioe);
			}
		}
	}

	protected void checkForReinvocation(String consoleText) {
		if ((consoleText == null) || consoleText.isEmpty()) {
			return;
		}

		TopLevelBuild topLevelBuild = (TopLevelBuild)getTopLevelBuild();

		if ((topLevelBuild == null) || topLevelBuild.fromArchive) {
			return;
		}

		if (consoleText.contains(getReinvokedMessage())) {
			reset();

			update();
		}
	}

	protected void downloadSampleURL(
		String path, boolean required, String url, String urlSuffix) {

		String urlString = url + urlSuffix;

		if (urlString.endsWith("json")) {
			urlString += "?pretty";
		}

		urlSuffix = JenkinsResultsParserUtil.fixFileName(urlSuffix);

		String content = null;

		try {
			content = JenkinsResultsParserUtil.toString(
				JenkinsResultsParserUtil.getLocalURL(urlString), false, 0, 0,
				0);
		}
		catch (IOException ioe) {
			if (required) {
				throw new RuntimeException(
					"Unable to download sample " + urlString, ioe);
			}
			else {
				return;
			}
		}

		try {
			writeArchiveFile(content, path + "/" + urlSuffix);
		}
		catch (IOException ioe) {
			throw new RuntimeException("Unable to write file", ioe);
		}
	}

	protected void findDownstreamBuilds() {
		String consoleText = getConsoleText();

		List<String> foundDownstreamBuildURLs = new ArrayList<>(
			findDownstreamBuildsInConsoleText(consoleText));

		JSONObject buildJSONObject;

		buildJSONObject = getBuildJSONObject("runs[number,url]");

		if ((buildJSONObject != null) && buildJSONObject.has("runs")) {
			JSONArray runsJSONArray = buildJSONObject.getJSONArray("runs");

			if (runsJSONArray != null) {
				for (int i = 0; i < runsJSONArray.length(); i++) {
					JSONObject runJSONObject = runsJSONArray.getJSONObject(i);

					if (runJSONObject.getInt("number") == _buildNumber) {
						String url = runJSONObject.getString("url");

						if (!hasBuildURL(url) &&
							!foundDownstreamBuildURLs.contains(url)) {

							foundDownstreamBuildURLs.add(url);
						}
					}
				}
			}
		}

		addDownstreamBuilds(
			foundDownstreamBuildURLs.toArray(
				new String[foundDownstreamBuildURLs.size()]));

		for (Build downstreamBuild : downstreamBuilds) {
			BaseBuild downstreamBaseBuild = (BaseBuild)downstreamBuild;

			downstreamBaseBuild.checkForReinvocation(consoleText);
		}
	}

	protected List<String> findDownstreamBuildsInConsoleText(
		String consoleText) {

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
					consoleText.substring(_consoleReadCursor));

			_consoleReadCursor = consoleText.length();

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

	protected String getBaseRepositoryType() {
		if (jobName.startsWith("test-subrepository-acceptance-pullrequest")) {
			return getBaseRepositoryName();
		}

		if (jobName.contains("portal")) {
			return "portal";
		}

		if (jobName.contains("plugins")) {
			return "plugins";
		}

		return "jenkins";
	}

	protected JSONObject getBuildJSONObject(String tree) {
		if (getBuildURL() == null) {
			return null;
		}

		StringBuffer sb = new StringBuffer();

		sb.append(JenkinsResultsParserUtil.getLocalURL(getBuildURL()));
		sb.append("/api/json?pretty");

		if (tree != null) {
			sb.append("&tree=");
			sb.append(tree);
		}

		try {
			return JenkinsResultsParserUtil.toJSONObject(sb.toString(), false);
		}
		catch (IOException ioe) {
			throw new RuntimeException("Unable to get build JSON", ioe);
		}
	}

	protected String getBuildMessage() {
		if (jobName != null) {
			String status = getStatus();

			StringBuilder sb = new StringBuilder();

			sb.append("Build \"");
			sb.append(jobName);
			sb.append("\"");

			if (status.equals("completed")) {
				sb.append(" completed at ");
				sb.append(getBuildURL());
				sb.append(". ");
				sb.append(getResult());

				return sb.toString();
			}

			if (status.equals("missing")) {
				sb.append(" is missing ");
				sb.append(getJobURL());
				sb.append(".");

				return sb.toString();
			}

			if (status.equals("queued")) {
				sb.append(" is queued at ");
				sb.append(getJobURL());
				sb.append(".");

				return sb.toString();
			}

			if (status.equals("running")) {
				if (badBuildNumbers.size() > 0) {
					sb.append(" ");

					List<String> badBuildURLs = getBadBuildURLs();

					sb.append(badBuildURLs.get(badBuildNumbers.size() - 1));

					sb.append(" restarted at ");
				}
				else {
					sb.append(" started at ");
				}

				sb.append(getBuildURL());
				sb.append(".\n");

				return sb.toString();
			}

			if (status.equals("starting")) {
				sb.append(" invoked at ");
				sb.append(getJobURL());
				sb.append(".");

				return sb.toString();
			}

			throw new RuntimeException("Unknown status: " + status);
		}

		return "";
	}

	protected JSONArray getBuildsJSONArray() throws IOException {
		JSONObject jsonObject = JenkinsResultsParserUtil.toJSONObject(
			JenkinsResultsParserUtil.getLocalURL(
				JenkinsResultsParserUtil.combine(
					getJobURL(), "/api/json?tree=builds[actions[parameters",
					"[name,type,value]],building,duration,number,result,url]")),
			false);

		return jsonObject.getJSONArray("builds");
	}

	protected Element getBuildTimeElement() {
		return Dom4JUtil.getNewElement(
			"p", null, "Build Time: ",
			JenkinsResultsParserUtil.toDurationString(getDuration()));
	}

	protected int getDownstreamBuildCountByResult(String result) {
		int count = 0;

		List<Build> downstreamBuilds = getDownstreamBuilds(null);

		if (result == null) {
			return downstreamBuilds.size();
		}

		for (Build downstreamBuild : downstreamBuilds) {
			String downstreamBuildResult = downstreamBuild.getResult();

			if (downstreamBuildResult.equals(result)) {
				count++;
			}
		}

		return count;
	}

	protected ExecutorService getExecutorService() {
		return null;
	}

	protected Element getFailureMessageElement() {
		for (FailureMessageGenerator failureMessageGenerator :
				getFailureMessageGenerators()) {

			Element failureMessage = failureMessageGenerator.getMessageElement(
				this);

			if (failureMessage != null) {
				return failureMessage;
			}
		}

		return null;
	}

	protected FailureMessageGenerator[] getFailureMessageGenerators() {
		return _FAILURE_MESSAGE_GENERATORS;
	}

	protected abstract Element getGitHubMessageJobResultsElement();

	protected Element getGitHubMessageJobResultsElement(
		boolean showCommonFailuresCount) {

		return getGitHubMessageJobResultsElement();
	}

	protected Set<String> getJobParameterNames() {
		JSONObject jsonObject;

		try {
			jsonObject = JenkinsResultsParserUtil.toJSONObject(
				JenkinsResultsParserUtil.getLocalURL(
					JenkinsResultsParserUtil.combine(
						getJobURL(), "/api/json?tree=actions[",
						"parameterDefinitions[name,type,value]]")));
		}
		catch (IOException ioe) {
			throw new RuntimeException("Unable to get build JSON", ioe);
		}

		JSONArray actionsJSONArray = jsonObject.getJSONArray("actions");

		JSONObject firstActionJSONObject = actionsJSONArray.getJSONObject(0);

		JSONArray parameterDefinitionsJSONArray =
			firstActionJSONObject.getJSONArray("parameterDefinitions");

		Set<String> parameterNames = new HashSet<>(
			parameterDefinitionsJSONArray.length());

		for (int i = 0; i < parameterDefinitionsJSONArray.length(); i++) {
			JSONObject parameterDefinitionJSONObject =
				parameterDefinitionsJSONArray.getJSONObject(i);

			String type = parameterDefinitionJSONObject.getString("type");

			if (type.equals("StringParameterDefinition")) {
				parameterNames.add(
					parameterDefinitionJSONObject.getString("name"));
			}
		}

		return parameterNames;
	}

	protected Map<String, String> getParameters(JSONArray jsonArray) {
		Map<String, String> parameters = new HashMap<>(jsonArray.length());

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			if (jsonObject.opt("value") instanceof String) {
				String name = jsonObject.getString("name");
				String value = jsonObject.getString("value");

				if (!value.isEmpty()) {
					parameters.put(name, value);
				}
			}
		}

		return parameters;
	}

	protected Map<String, String> getParameters(JSONObject buildJSONObject) {
		JSONArray actionsJSONArray = buildJSONObject.getJSONArray("actions");

		if (actionsJSONArray.length() == 0) {
			return new HashMap<>();
		}

		JSONObject jsonObject = actionsJSONArray.getJSONObject(0);

		if (jsonObject.has("parameters")) {
			JSONArray parametersJSONArray = jsonObject.getJSONArray(
				"parameters");

			return getParameters(parametersJSONArray);
		}

		return new HashMap<>();
	}

	protected JSONObject getQueueItemJSONObject() throws IOException {
		JSONArray queueItemsJSONArray = getQueueItemsJSONArray();

		for (int i = 0; i < queueItemsJSONArray.length(); i++) {
			JSONObject queueItemJSONObject = queueItemsJSONArray.getJSONObject(
				i);

			JSONObject taskJSONObject = queueItemJSONObject.getJSONObject(
				"task");

			String queueItemName = taskJSONObject.getString("name");

			if (!queueItemName.equals(jobName)) {
				continue;
			}

			if (_parameters.equals(getParameters(queueItemJSONObject))) {
				return queueItemJSONObject;
			}
		}

		return null;
	}

	protected JSONArray getQueueItemsJSONArray() throws IOException {
		JSONObject jsonObject = JenkinsResultsParserUtil.toJSONObject(
			"http://" + master +
				"/queue/api/json?tree=items[actions[parameters" +
					"[name,value]],task[name,url]]",
			false);

		return jsonObject.getJSONArray("items");
	}

	protected String getReinvokedMessage() {
		StringBuffer sb = new StringBuffer();

		sb.append("Reinvoked: ");
		sb.append(getBuildURL());
		sb.append(" at ");
		sb.append(getInvocationURL());

		return sb.toString();
	}

	protected JSONObject getRunningBuildJSONObject() throws IOException {
		JSONArray buildsJSONArray = getBuildsJSONArray();

		for (int i = 0; i < buildsJSONArray.length(); i++) {
			JSONObject buildJSONObject = buildsJSONArray.getJSONObject(i);

			Map<String, String> parameters = getParameters();

			if (parameters.equals(getParameters(buildJSONObject)) &&
				!badBuildNumbers.contains(buildJSONObject.getInt("number"))) {

				return buildJSONObject;
			}
		}

		return null;
	}

	protected String getStartPropertiesTempMapURL() {
		if (fromArchive) {
			return getBuildURL() + "/start.properties.json";
		}

		return getParameterValue("JSON_MAP_URL");
	}

	protected String getStopPropertiesTempMapURL() {
		return null;
	}

	protected Map<String, String> getTempMap(String tempMapName) {
		JSONObject tempMapJSONObject = null;

		String tempMapURL = getTempMapURL(tempMapName);

		if (tempMapURL == null) {
			return Collections.emptyMap();
		}

		try {
			tempMapJSONObject = JenkinsResultsParserUtil.toJSONObject(
				JenkinsResultsParserUtil.getLocalURL(tempMapURL), false, 0, 0,
				0);
		}
		catch (IOException ioe) {
		}

		if ((tempMapJSONObject == null) ||
			!tempMapJSONObject.has("properties")) {

			return Collections.emptyMap();
		}

		JSONArray propertiesJSONArray = tempMapJSONObject.getJSONArray(
			"properties");

		Map<String, String> tempMap = new HashMap<>(
			propertiesJSONArray.length());

		for (int i = 0; i < propertiesJSONArray.length(); i++) {
			JSONObject propertyJSONObject = propertiesJSONArray.getJSONObject(
				i);

			String key = propertyJSONObject.getString("name");
			String value = propertyJSONObject.optString("value");

			if ((value != null) && !value.isEmpty()) {
				tempMap.put(key, value);
			}
		}

		return tempMap;
	}

	protected String getTempMapURL(String tempMapName) {
		if (tempMapName.equals("start.properties")) {
			return getStartPropertiesTempMapURL();
		}

		if (tempMapName.equals("stop.properties")) {
			return getStopPropertiesTempMapURL();
		}

		return null;
	}

	protected int getTestCountByStatus(String status) {
		JSONObject testReportJSONObject = getTestReportJSONObject();

		if (testReportJSONObject == null) {
			return 0;
		}

		int failCount = testReportJSONObject.getInt("failCount");
		int passCount = testReportJSONObject.getInt("passCount");

		if (status.equals("FAILURE")) {
			return failCount;
		}

		if (status.equals("SUCCESS")) {
			return passCount;
		}

		throw new IllegalArgumentException("Invalid status: " + status);
	}

	protected boolean isCompareToUpstream() {
		TopLevelBuild topLevelBuild = getTopLevelBuild();

		return topLevelBuild.isCompareToUpstream();
	}

	protected boolean isParentBuildRoot() {
		if (_parentBuild == null) {
			return false;
		}

		if ((_parentBuild.getParentBuild() == null) &&
			(_parentBuild instanceof TopLevelBuild)) {

			return true;
		}

		return false;
	}

	protected void loadParametersFromBuildJSONObject() {
		if (getBuildURL() == null) {
			return;
		}

		JSONObject buildJSONObject = getBuildJSONObject(
			"actions[parameters[*]]");

		JSONArray actionsJSONArray = buildJSONObject.getJSONArray("actions");

		if (actionsJSONArray.length() == 0) {
			_parameters = new HashMap<>(0);

			return;
		}

		JSONObject actionJSONObject = actionsJSONArray.getJSONObject(0);

		if (actionJSONObject.has("parameters")) {
			JSONArray parametersJSONArray = actionJSONObject.getJSONArray(
				"parameters");

			_parameters = new HashMap<>(parametersJSONArray.length());

			for (int i = 0; i < parametersJSONArray.length(); i++) {
				JSONObject parameterJSONObject =
					parametersJSONArray.getJSONObject(i);

				Object value = parameterJSONObject.opt("value");

				if (value instanceof String) {
					if (!value.toString().isEmpty()) {
						_parameters.put(
							parameterJSONObject.getString("name"),
							value.toString());
					}
				}
			}

			return;
		}

		_parameters = Collections.emptyMap();
	}

	protected void loadParametersFromQueryString(String queryString) {
		Set<String> jobParameterNames = getJobParameterNames();

		for (String parameter : queryString.split("&")) {
			String[] nameValueArray = parameter.split("=");

			if ((nameValueArray.length == 2) &&
				jobParameterNames.contains(nameValueArray[0])) {

				_parameters.put(nameValueArray[0], nameValueArray[1]);
			}
		}
	}

	protected void loadUpstreamJobFailuresJSONObject() {
		try {
			if (getJobName().contains("pullrequest")) {
				String upstreamJobName =
					getJobName().replace("pullrequest", "upstream");

				String url = JenkinsResultsParserUtil.getLocalURL(
					UPSTREAM_FAILURES_JOB_BASE_URL + upstreamJobName +
						"/builds/latest/test.results.json");

				upstreamFailuresJobJSONObject =
					JenkinsResultsParserUtil.toJSONObject(url);
			}
		}
		catch (IOException ioe) {
			System.out.println(
				"Unable to set upstream acceptance failure data.");

			ioe.printStackTrace();
		}
	}

	protected void reset() {
		result = null;

		badBuildNumbers.add(getBuildNumber());

		setBuildNumber(-1);

		downstreamBuilds.clear();
	}

	protected void setBuildNumber(int buildNumber) {
		if (_buildNumber != buildNumber) {
			_buildNumber = buildNumber;

			_consoleReadCursor = 0;
			_consoleText = null;

			if (_buildNumber == -1) {
				setStatus("starting");
			}
			else {
				setStatus("running");
			}
		}
	}

	protected void setBuildURL(String buildURL) {
		try {
			buildURL = JenkinsResultsParserUtil.decode(buildURL);
		}
		catch (UnsupportedEncodingException uee) {
			throw new IllegalArgumentException(
				"Unable to decode " + buildURL, uee);
		}

		try {
			BaseBuild parentBuild = (BaseBuild)getParentBuild();

			if (parentBuild != null) {
				fromArchive = parentBuild.fromArchive;
			}
			else {
				String archiveMarkerContent = JenkinsResultsParserUtil.toString(
					buildURL + "/archive-marker", false, 0, 0, 0);

				fromArchive =
					(archiveMarkerContent != null) &&
					!archiveMarkerContent.isEmpty();
			}
		}
		catch (IOException ioe) {
			fromArchive = false;
		}

		Matcher matcher = buildURLPattern.matcher(buildURL);

		if (!matcher.find()) {
			matcher = archiveBuildURLPattern.matcher(buildURL);

			if (!matcher.find()) {
				throw new IllegalArgumentException(
					"Invalid build URL " + buildURL);
			}

			archiveName = matcher.group("archiveName");
		}

		setJobName(matcher.group("jobName"));
		master = matcher.group("master");

		_buildNumber = Integer.parseInt(matcher.group("buildNumber"));

		loadParametersFromBuildJSONObject();

		_consoleReadCursor = 0;

		setStatus("running");
	}

	protected void setInvocationURL(String invocationURL) {
		if (getBuildURL() == null) {
			try {
				invocationURL = JenkinsResultsParserUtil.decode(invocationURL);
			}
			catch (UnsupportedEncodingException uee) {
				throw new IllegalArgumentException(
					"Unable to decode " + invocationURL, uee);
			}

			Matcher invocationURLMatcher = invocationURLPattern.matcher(
				invocationURL);

			if (!invocationURLMatcher.find()) {
				throw new RuntimeException("Invalid invocation URL");
			}

			setJobName(invocationURLMatcher.group("jobName"));
			master = invocationURLMatcher.group("master");

			loadParametersFromQueryString(invocationURL);

			setStatus("starting");
		}
	}

	protected void setJobName(String jobName) {
		this.jobName = jobName;

		Matcher matcher = jobNamePattern.matcher(jobName);

		if (matcher.find()) {
			branchName = matcher.group("branchName");

			return;
		}

		branchName = "master";
	}

	protected void setStatus(String status) {
		if (((status == null) && (_status != null)) ||
			!status.equals(_status)) {

			_status = status;

			statusModifiedTime = System.currentTimeMillis();

			if (isParentBuildRoot()) {
				System.out.println(getBuildMessage());
			}
		}
	}

	protected void writeArchiveFile(String content, String path)
		throws IOException {

		JenkinsResultsParserUtil.write(
			JenkinsResultsParserUtil.combine(
				JenkinsResultsParserUtil.DEPENDENCIES_URL_FILE.substring(
					"file:".length()),
				"/", path),
			JenkinsResultsParserUtil.redact(replaceBuildURL(content)));
	}

	protected static final int MAX_REINVOCATIONS = 1;

	protected static final String TEMP_MAP_BASE_URL =
		"http://cloud-10-0-0-31.lax.liferay.com/osb-jenkins-web/map/";

	protected static final String UPSTREAM_FAILURES_JOB_BASE_URL =
		"https://test-1-0.liferay.com/userContent/testResults/";

	protected static final Pattern archiveBuildURLPattern = Pattern.compile(
		JenkinsResultsParserUtil.combine(
			"(", Pattern.quote("${dependencies.url}"), "|",
			Pattern.quote(JenkinsResultsParserUtil.DEPENDENCIES_URL_FILE), "|",
			Pattern.quote(JenkinsResultsParserUtil.DEPENDENCIES_URL_HTTP),
			")/*(?<archiveName>.*)/(?<master>[^/]+)/+(?<jobName>[^/]+)",
			".*/(?<buildNumber>\\d+)/?"));
	protected static final Pattern buildURLPattern = Pattern.compile(
		JenkinsResultsParserUtil.combine(
			"\\w+://(?<master>[^/]+)/+job/+(?<jobName>[^/]+).*/(?<buildNumber>",
			"\\d+)/?"));
	protected static final Pattern downstreamBuildURLPattern = Pattern.compile(
		"[\\'\\\"].*[\\'\\\"] started at (?<url>.+)\\.");
	protected static final Pattern invocationURLPattern = Pattern.compile(
		JenkinsResultsParserUtil.combine(
			"\\w+://(?<master>[^/]+)/+job/+(?<jobName>[^/]+).*/",
			"buildWithParameters\\?(?<queryString>.*)"));
	protected static final Pattern jobNamePattern = Pattern.compile(
		"(?<baseJob>[^\\(]+)\\((?<branchName>[^\\)]+)\\)");
	protected static JSONObject upstreamFailuresJobJSONObject = new JSONObject(
		"{\"SHA\":\"\",\"failedBatches\":[]}");

	protected String archiveName;
	protected List<Integer> badBuildNumbers = new ArrayList<>();
	protected String branchName;
	protected List<Build> downstreamBuilds = new ArrayList<>();
	protected boolean fromArchive;
	protected String jobName;
	protected String master;
	protected List<ReinvokeRule> reinvokeRules =
		ReinvokeRule.getReinvokeRules();
	protected String repositoryName;
	protected String result;
	protected String slave;
	protected List<SlaveOfflineRule> slaveOfflineRules =
		SlaveOfflineRule.getSlaveOfflineRules();
	protected long statusModifiedTime;
	protected Element upstreamJobFailureMessageElement;

	private static final FailureMessageGenerator[] _FAILURE_MESSAGE_GENERATORS =
		{
			new GenericFailureMessageGenerator()
		};

	private static final String[] _HIGH_PRIORITY_CONTENT_FLAGS =
		{"compileJSP", "SourceFormatter.format", "Unable to compile JSPs"};

	private int _buildNumber = -1;
	private int _consoleReadCursor;
	private String _consoleText;
	private Map<String, String> _parameters = new HashMap<>();
	private final Build _parentBuild;
	private String _status;

}