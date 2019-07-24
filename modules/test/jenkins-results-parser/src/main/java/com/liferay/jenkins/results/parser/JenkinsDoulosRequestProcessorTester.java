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

import com.googlecode.concurrentlinkedhashmap.ConcurrentLinkedHashMap;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.JenkinsStopBuildUtil;
import com.liferay.jenkins.results.parser.LoadBalancerUtil;
import com.liferay.petra.doulos.processor.BaseDoulosRequestProcessor;

import java.io.FileReader;
import java.io.StringReader;

import java.net.NoRouteToHostException;
import java.net.URLEncoder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.EntityEnclosingMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Yi-Chen Tsai
 */
public class JenkinsDoulosRequestProcessorTester {
	public static void main(String[] args) throws Exception {
		JenkinsDoulosRequestProcessor jdrp =
			new JenkinsDoulosRequestProcessor();

		String pullRequestURL =
			"https://api.github.com/repos/yichenroy/liferay-portal/pulls/" +
				"156.json";

		jdrp.testPullRequest(pullRequestURL);
	}
}

class JenkinsDoulosRequestProcessor extends BaseDoulosRequestProcessor {

	public JenkinsDoulosRequestProcessor() throws Exception {
		Class<?> clazz = getClass();

		_jenkinsProperties.load(
			new FileReader(
				"/opt/dev/projects/github/liferay-jenkins-ee/commands/" +
					"build.properties"));

		JenkinsResultsParserUtil.setBuildProperties(
			_URLS_JENKINS_BUILD_PROPERTIES);

		_jenkinsBuildProperties = JenkinsResultsParserUtil.getBuildProperties();

		ConcurrentLinkedHashMap.Builder<String, Long> builder =
			new ConcurrentLinkedHashMap.Builder<String, Long>();

		builder.maximumWeightedCapacity(100);

		_testPullRequestQueryStrings = builder.build();
		_testPullRequestURLs = builder.build();
	}

	public void addTestPullRequestQueryString(String queryString) {
		_testPullRequestQueryStrings.put(
			queryString, System.currentTimeMillis());
	}

	public void addTestPullRequestURL(String url) {
		_testPullRequestURLs.put(url, System.currentTimeMillis());
	}

	public List<String> getTestPullRequestQueryStrings() {
		long expiredTime = getTestPullRequestQueryStringExpiredTime();

		for (String testPullRequestQueryString :
			_testPullRequestQueryStrings.keySet()) {

			long time = _testPullRequestQueryStrings.get(
				testPullRequestQueryString);

			if (time < expiredTime) {
				_testPullRequestQueryStrings.remove(testPullRequestQueryString);
			}
		}

		return new ArrayList<String>(_testPullRequestQueryStrings.keySet());
	}

	public List<String> getTestPullRequestURLs() {
		long expiredTime = getTestPullRequestURLExpiredTime();

		for (String testPullRequestURL : _testPullRequestURLs.keySet()) {
			long time = _testPullRequestURLs.get(testPullRequestURL);

			if (time < expiredTime) {
				_testPullRequestURLs.remove(testPullRequestURL);
			}
		}

		return new ArrayList<String>(_testPullRequestURLs.keySet());
	}

	public boolean isGitHubAutopullEnabled() {
		String gitHubAutopullEnabled = _jenkinsProperties.getProperty(
			"github.autopull.enabled");

		if (!isBlank(gitHubAutopullEnabled) &&
			gitHubAutopullEnabled.equals("true")) {

			return true;
		}

		return false;
	}

	public boolean isGitHubCacheEnabled() {
		String gitHubCacheEnabled = _jenkinsProperties.getProperty(
			"github.cache.enabled");

		if (!isBlank(gitHubCacheEnabled) && gitHubCacheEnabled.equals("true")) {
			return true;
		}

		return false;
	}

	public boolean isGitHubMirrorEnabled() {
		String gitHubMirrorEnabled = _jenkinsProperties.getProperty(
			"github.mirror.enabled");

		if (!isBlank(gitHubMirrorEnabled) &&
			gitHubMirrorEnabled.equals("true")) {

			return true;
		}

		return false;
	}

	public boolean isGitHubPostEnabled() {
		String gitHubPostEnabled = _jenkinsProperties.getProperty(
			"github.post.enabled");

		if (!isBlank(gitHubPostEnabled) && gitHubPostEnabled.equals("true")) {
			return true;
		}

		return false;
	}

	public boolean isGitHubSubrepoSyncEnabled() {
		String gitHubSubrepoSyncEnabled = _jenkinsProperties.getProperty(
			"github.subrepo.sync.enabled");

		if (!isBlank(gitHubSubrepoSyncEnabled) &&
			gitHubSubrepoSyncEnabled.equals("true")) {

			return true;
		}

		return false;
	}

	public boolean isJenkinsJobEnabled() {
		String jenkinsJobEnabled = _jenkinsProperties.getProperty(
			"jenkins.job.enabled");

		if (!isBlank(jenkinsJobEnabled) && jenkinsJobEnabled.equals("true")) {
			return true;
		}

		return false;
	}

	public boolean isValidAutopull(String branch, String repo) {
		if (!repo.startsWith("com-liferay-")) {
			return false;
		}

		if ((branch.equals("master") || branch.equals("7.1.x")) &&
			!repo.endsWith("-private")) {

			return true;
		}
		else if ((branch.equals("7.0.x-private") ||
			branch.equals("7.1.x-private") ||
			branch.equals("master-private")) &&
			repo.endsWith("-private")) {

			return true;
		}

		return false;
	}

	@Override
	public void process(
		String method, String pathInfo, Map<String, String[]> parameterMap,
		JSONObject payloadJSONObject, JSONObject responseJSONObject)
		throws Exception {

		_jenkinsBuildProperties = JenkinsResultsParserUtil.getBuildProperties();

		JSONObject pusherJSONObject = payloadJSONObject.optJSONObject("pusher");

		if (pusherJSONObject != null) {
			syncAutopull(payloadJSONObject);
			syncMirror(payloadJSONObject);
			syncSubrepo(payloadJSONObject);
		}

		String action = payloadJSONObject.optString("action");

		if (action == null) {
			return;
		}

		JSONObject commentJSONObject = payloadJSONObject.optJSONObject(
			"comment");

		if ((commentJSONObject != null) && action.equals("created")) {
			String body = commentJSONObject.getString("body");

			JSONObject userJSONObject = commentJSONObject.getJSONObject("user");

			String login = userJSONObject.getString("login");

			if (body.startsWith("ci:") && !body.contains("ci:help") &&
				!isLiferayUser(login)) {

				if (_log.isInfoEnabled()) {
					_log.info(
						"Skip CI action because " + login + " is not a " +
							"Liferay member");
				}

				if (hasLiferayEmailAddress(login)) {
					JSONObject issueJSONObject =
						payloadJSONObject.getJSONObject("issue");

					JSONObject pullRequestJSONObject =
						issueJSONObject.getJSONObject("pull_request");

					String url = pullRequestJSONObject.getString("url");

					PullRequest pullRequest = new PullRequest(
						new JSONObject(processURL(url + ".json")));

					StringBuilder sb = new StringBuilder();

					sb.append("You cannot perform that action because you ");
					sb.append("are not a member of the Liferay organization. ");
					sb.append("Please make sure that you have been added and ");
					sb.append("that your organization membership is set as ");
					sb.append("Public. See https://help.github.com/articles");
					sb.append("/publicizing-or-hiding-organization-");
					sb.append("membership for more information.");

					commentPullRequest(pullRequest, sb.toString());
				}

				return;
			}

			if (body.startsWith("ci:close")) {
				JSONObject issueJSONObject = payloadJSONObject.getJSONObject(
					"issue");

				JSONObject pullRequestJSONObject =
					issueJSONObject.getJSONObject("pull_request");

				String url = pullRequestJSONObject.getString("url");

				PullRequest pullRequest = new PullRequest(
					new JSONObject(processURL(url + ".json")));

				if (_log.isInfoEnabled()) {
					_log.info("Comment triggered close pull request");
				}

				closePullRequest(pullRequest);
			}

			if (body.startsWith("ci:help")) {
				JSONObject issueJSONObject = payloadJSONObject.getJSONObject(
					"issue");

				JSONObject pullRequestJSONObject =
					issueJSONObject.getJSONObject("pull_request");

				String url = pullRequestJSONObject.getString("url");

				PullRequest pullRequest = new PullRequest(
					new JSONObject(processURL(url + ".json")));

				if (_log.isInfoEnabled()) {
					_log.info("Comment triggered help message");
				}

				StringBuilder sb = new StringBuilder();

				sb.append("## Available CI commands:\n");
				sb.append("#### ci:close\n");
				sb.append("&nbsp;&nbsp;&nbsp;&nbsp;Close the pull request.\n");
				sb.append("#### ci:merge[:force]\n");
				sb.append("&nbsp;&nbsp;&nbsp;&nbsp;Merge in the changes from ");
				sb.append("the subrepo. All tests must pass before this ");
				sb.append("command will successfully run. Optionally use the ");
				sb.append("force flag to bypass failed tests.\n");
				sb.append("#### ci:reopen\n");
				sb.append("&nbsp;&nbsp;&nbsp;&nbsp;Reopen the pull request.\n");
				sb.append("#### ci:stop\n");
				sb.append("&nbsp;&nbsp;&nbsp;&nbsp;Stop all currrently ");
				sb.append("running tests.\n");

				String ciTestAvailableSuites = pullRequest.getCIProperty(
					"ci.test.available.suites");

				if (ciTestAvailableSuites != null) {
					sb.append("#### ci:test[:suite][:SHA|norebase]\n");
					sb.append("&nbsp;&nbsp;&nbsp;&nbsp;Test the pull request.");
					sb.append("Optionally specify the name of the test suite ");
					sb.append("and/or optionally specify the upstream SHA to ");
					sb.append("test against or use \"norebase\" to test ");
					sb.append("without rebasing.");
					sb.append("\n\n&nbsp;&nbsp;&nbsp;&nbsp;List of ");
					sb.append("available test suites:\n");

					for (String ciTestAvailableSuite :
						ciTestAvailableSuites.split(",")) {

						sb.append("- &nbsp;&nbsp;&nbsp;&nbsp;ci:test:**");
						sb.append(ciTestAvailableSuite);
						sb.append("** - ");

						String ciTestSuiteDescription =
							pullRequest.getCIProperty(
								"ci.test.suite.description[" +
									ciTestAvailableSuite + "]");

						if (ciTestSuiteDescription != null) {
							sb.append(ciTestSuiteDescription);
						}
						else {
							sb.append("No description is available.");
						}

						sb.append("\n");
					}
				}
				else {
					sb.append("#### ci:test[:SHA|norebase]\n");
					sb.append("&nbsp;&nbsp;&nbsp;&nbsp;Test the pull request.");
					sb.append("Optionally specify the upstream SHA to test ");
					sb.append("against or use \"norebase\" to test without ");
					sb.append("rebasing.\n");
				}

				sb.append("\n");
				sb.append("For more details, see ");
				sb.append("[GROW](https://grow.liferay.com/share/CI+");
				sb.append("liferay-continuous-integration+GitHub+Commands).");

				commentPullRequest(pullRequest, sb.toString());
			}

			if (body.startsWith("ci:merge")) {
				JSONObject issueJSONObject = payloadJSONObject.getJSONObject(
					"issue");

				JSONObject pullRequestJSONObject =
					issueJSONObject.getJSONObject("pull_request");

				String url = pullRequestJSONObject.getString("url");

				PullRequest pullRequest = new PullRequest(
					new JSONObject(processURL(url + ".json")));

				if (_log.isInfoEnabled()) {
					_log.info("Comment triggered merge subrepo");
				}

				if (body.startsWith("ci:merge:force")) {
					if (!login.equals("brianchandotcom") &&
						!login.equals("brianwulbern") &&
						!login.equals("jpince") &&
						!login.equals("pyoo47") &&
						!login.equals("shuyangzhou") &&
						!login.equals("stsquared99")) {

						String message = "Only Brian Chan can force a merge";

						if (_log.isInfoEnabled()) {
							_log.info(message);
						}

						commentPullRequest(pullRequest, message + ".");

						return;
					}

					mergeSubrepo(pullRequest, true);
				}
				else {
					mergeSubrepo(pullRequest, false);
				}
			}

			if (body.startsWith("ci:reopen")) {
				JSONObject issueJSONObject = payloadJSONObject.getJSONObject(
					"issue");

				JSONObject pullRequestJSONObject =
					issueJSONObject.getJSONObject("pull_request");

				String url = pullRequestJSONObject.getString("url");

				PullRequest pullRequest = new PullRequest(
					new JSONObject(processURL(url + ".json")));

				if (_log.isInfoEnabled()) {
					_log.info("Comment triggered open pull request");
				}

				openPullRequest(pullRequest);
			}

			if (body.startsWith("ci:retest") || body.startsWith("ci:test")) {
				JSONObject issueJSONObject = payloadJSONObject.getJSONObject(
					"issue");

				JSONObject pullRequestJSONObject =
					issueJSONObject.getJSONObject("pull_request");

				String url = pullRequestJSONObject.getString("url");

				PullRequest pullRequest = new PullRequest(
					new JSONObject(processURL(url + ".json")));

				pullRequest.setTesterName(login);

				Matcher matcher = _testPattern.matcher(body);

				if (matcher.find()) {
					String testOption1 = matcher.group("testOption1");

					String rebaseSHA = null;
					String testSuite = null;

					if (testOption1.matches("[0-9a-f]{7,40}")) {
						if (isValidPullRequestRefSHA(
							pullRequest, testOption1)) {

							rebaseSHA = testOption1;
						}
						else {
							StringBuilder sb = new StringBuilder();

							sb.append("The test option '");
							sb.append(testOption1);
							sb.append("' matching SHA pattern [0-9a-f]{7,40}");
							sb.append(" is not a valid upstream SHA.\n");
							sb.append("The test will start with '");
							sb.append(testOption1);
							sb.append("' as the test suite");

							String message = sb.toString();

							if (_log.isInfoEnabled()) {
								_log.info(message);
							}

							commentPullRequest(pullRequest, message + ".");

							testSuite = testOption1;
						}
					}
					else if (testOption1.equals("norebase")) {
						rebaseSHA = testOption1;
					}
					else {
						testSuite = testOption1;
					}

					String testOption2 = matcher.group("testOption2");

					if (testOption2 != null) {
						if (testOption2.matches("[0-9a-f]{7,40}") ||
							testOption2.equals("norebase")) {

							rebaseSHA = testOption2;
						}
					}

					if ((rebaseSHA != null) && rebaseSHA.equals("norebase")) {
						String message = "The test will run without rebasing.";

						if (_log.isInfoEnabled()) {
							_log.info(message);
						}

						commentPullRequest(pullRequest, message + ".");

						rebaseSHA = pullRequest.getCommonParentSHA();
					}

					if (rebaseSHA != null) {
						pullRequest.setRefSHA(rebaseSHA);
					}

					if (testSuite != null) {
						pullRequest.setCITestSuite(testSuite);

						if (testSuite.equals("gauntlet") &&
							!login.equals("CsabaTurcsan") &&
							!login.equals("Hanlf") &&
							!login.equals("HarryC0204") &&
							!login.equals("Songyuewen") &&
							!login.equals("SylviaLuan") &&
							!login.equals("ZoltanTakacs") &&
							!login.equals("brianchandotcom") &&
							!login.equals("brianwulbern") &&
							!login.equals("ctampoya") &&
							!login.equals("gergelyszaz") &&
							!login.equals("jpince") &&
							!login.equals("kevin-yen") &&
							!login.equals("kiyoshilee") &&
							!login.equals("lesliewong92") &&
							!login.equals(
								"liferay-continuous-integration-hu") &&
							!login.equals("michaelhashimoto") &&
							!login.equals("michaelprigge") &&
							!login.equals("pyoo47") &&
							!login.equals("sharonchoi") &&
							!login.equals("shuyangzhou") &&
							!login.equals("stsquared99") &&
							!login.equals("suilin") &&
							!login.equals("xbrianlee") &&
							!login.equals("yunlinsun")) {

							String message =
								"You do not have permission to run the test " +
									"gauntlet";

							if (_log.isInfoEnabled()) {
								_log.info(message);
							}

							commentPullRequest(pullRequest, message + ".");

							return;
						}
					}
				}

				if (_log.isInfoEnabled()) {
					_log.info("Comment triggered test");
				}

				testPullRequest(pullRequest);
			}

			if (body.startsWith("ci:stop")) {
				if (_log.isInfoEnabled()) {
					_log.info("Comment triggered stop");
				}

				stopJenkinsTests(payloadJSONObject);
			}

			return;
		}

		JSONObject pullRequestJSONObject = payloadJSONObject.optJSONObject(
			"pull_request");

		if (pullRequestJSONObject != null) {
			PullRequest pullRequest = new PullRequest(pullRequestJSONObject);

			if (action.equals("opened")) {
				String ownerName = pullRequest.getOwnerName();
				String refName = pullRequest.getRefName();
				String repositoryName = pullRequest.getRepositoryName();
				String subrepoCentralMergePullRequestRecipientName =
					getSubrepoCentralMergePullRequestRecipientName(refName);

				if (ownerName.equals(
					subrepoCentralMergePullRequestRecipientName) &&
					pullRequest.isMergeSubrepoRequest()) {

					commentMergeSubrepoPullRequest(pullRequest);

					pullRequest.setCITestSuite("relevant");

					testPullRequest(pullRequest);

					pullRequest.setCITestSuite("sf");

					testPullRequest(pullRequest);
				}
				else if (ownerName.equals("brianchandotcom") &&
					repositoryName.contains("liferay-portal")) {

					if (!isTestablePullRequest(pullRequest)) {
						return;
					}

					if (refName.equals("ee-6.1.30") ||
						refName.equals("ee-6.2.10")) {

						StringBuilder sb = new StringBuilder(2);

						sb.append("CI is not configured to run tests against ");
						sb.append("this branch.");

						commentPullRequest(pullRequest, sb.toString());

						return;
					}

					if (refName.contains("master") ||
						refName.contains("7.0.x") ||
						refName.contains("7.1.x") ||
						refName.contains("7.2.x")) {

						StringBuilder sb = new StringBuilder(6);

						sb.append("CI is automatically triggering &quot;ci:");
						sb.append("test:sf&quot; and &quot;ci:test:relevant");
						sb.append("&quot; for this pull to run Source ");
						sb.append("Formatter and relevant tests.\n\nComment ");
						sb.append("&quot;ci:test&quot; to run the full PR ");
						sb.append("Tester for this pull.");

						commentPullRequest(pullRequest, sb.toString());

						pullRequest.setCITestSuite("relevant");

						testPullRequest(pullRequest);
					}
					else {
						StringBuilder sb = new StringBuilder(4);

						sb.append("CI is automatically triggering &quot;ci:");
						sb.append("test:sf&quot; for this pull to run Source ");
						sb.append("Formatter.\n\nComment &quot;ci:test&quot; ");
						sb.append("to run the full PR Tester for this pull.");

						commentPullRequest(pullRequest, sb.toString());
					}

					pullRequest.setCITestSuite("sf");

					testPullRequest(pullRequest);
				}
				else if (isLiferayUser(pullRequest.getSenderName())) {
					StringBuilder sb = new StringBuilder(7);

					sb.append("To conserve resources, the PR Tester does not ");
					sb.append("automatically run for every pull.\n\nIf your ");
					sb.append("code changes were already tested in another ");
					sb.append("pull, reference that pull in this pull so ");
					sb.append("the test results can be analyzed.\n\nIf your ");
					sb.append("pull was never tested, comment ");
					sb.append("&quot;ci:test&quot; to run the PR Tester for ");
					sb.append("this pull.");

					if (!ownerName.equals("liferay") ||
						pullRequest.isSubrepo() ||
						repositoryName.equals("liferay-portal-ee")) {

						commentPullRequest(pullRequest, sb.toString());
					}
				}
			}
			else if (action.equals("synchronize")) {
				if (pullRequest.isSynchronizeAllowed()) {
					return;
				}

				if (_log.isInfoEnabled()) {
					_log.info("Synchronize triggered close pull request");
				}

				String message =
					"Closing and locking pull request because pull requests " +
						"sent to this user may not be updated. Please resend " +
						"this pull request.";

				closePullRequest(pullRequest, message);

				lockPullRequest(pullRequest);

				stopJenkinsTests(pullRequestJSONObject);
			}
		}
	}

	public String processURL(String url) throws Exception {
		return processURL(url, null, "GET");
	}

	public String processURL(String url, String body) throws Exception {
		return processURL(url, body, "POST");
	}

	public String processURL(String url, String body, String method)
		throws Exception {

		if (_log.isInfoEnabled()) {
			_log.info("Processing URL: " + url);
		}

		HttpClient httpClient = new HttpClient();

		HttpMethod httpMethod = null;

		if (method.equals("GET")) {
			if (body != null) {
				throw new IllegalArgumentException(
					"GET method should not have a body");
			}

			httpMethod = new GetMethod(url);
		}
		else if (method.equals("PATCH")) {
			if (body == null) {
				throw new IllegalArgumentException(
					"PATCH method requires a body");
			}

			httpMethod = new PatchMethod(url);
		}
		else if (method.equals("POST")) {
			if (body == null) {
				throw new IllegalArgumentException(
					"POST method requires a body");
			}

			httpMethod = new PostMethod(url);
		}
		else if (method.equals("PUT")) {
			if (body != null) {
				throw new IllegalArgumentException(
					"PUT method should not have a body");
			}

			httpMethod = new PutMethod(url);
		}
		else {
			throw new IllegalArgumentException("Invalid method " + method);
		}

		for (int i = 0; i < 3; i++) {
			try {
				String gitHubAccessToken = _jenkinsProperties.getProperty(
					"github.access.token");

				httpMethod.addRequestHeader(
					"Accept", "application/vnd.github.moondragon+json");
				httpMethod.addRequestHeader(
					"Authorization", "token " + gitHubAccessToken);

				if (_log.isDebugEnabled()) {
					_log.debug("GitHub access token: " + gitHubAccessToken);
				}

				HttpMethodParams httpMethodParams = httpMethod.getParams();

				httpMethodParams.setParameter(
					HttpMethodParams.RETRY_HANDLER,
					new DefaultHttpMethodRetryHandler(3, false));

				if (body != null) {
					RequestEntity requestEntity = new StringRequestEntity(
						body, "application/json", "UTF-8");

					EntityEnclosingMethod entityEnclosingMethod =
						(EntityEnclosingMethod)httpMethod;

					entityEnclosingMethod.setRequestEntity(requestEntity);
				}

				httpClient.executeMethod(httpMethod);

				String responseBody = new String(httpMethod.getResponseBody());

				if (_log.isInfoEnabled()) {
					_log.info("Response body: " + responseBody);
				}

				return responseBody;
			}
			catch (NoRouteToHostException nrthe) {
				if (_log.isInfoEnabled()) {
					_log.info("Retrying " + url, nrthe);
				}

				Thread.sleep(1000);
			}
			finally {
				httpMethod.releaseConnection();
			}
		}

		throw new RuntimeException("Too many retries");
	}

	public void removeTestPullRequestQueryString(String queryString) {
		_testPullRequestQueryStrings.remove(queryString);
	}

	public void removeTestPullRequestURL(String url) {
		_testPullRequestURLs.remove(url);
	}

	protected void closePullRequest(PullRequest pullRequest) throws Exception {
		if (!isGitHubPostEnabled()) {
			return;
		}

		JSONObject jsonObject = new JSONObject();

		jsonObject.put("state", "closed");

		processURL(
			"https://api.github.com/repos/" + pullRequest.getOwnerName() +
				"/" + pullRequest.getRepositoryName() + "/pulls/" +
				pullRequest.getNumber(),
			jsonObject.toString(), "PATCH");
	}

	protected void closePullRequest(PullRequest pullRequest, String message)
		throws Exception {

		if (!isGitHubPostEnabled()) {
			return;
		}

		commentPullRequest(pullRequest, message);

		closePullRequest(pullRequest);
	}

	protected void commentMergeSubrepoPullRequest(PullRequest pullRequest) {
		try {
			String currentSHA = "";

			StringBuilder sb = new StringBuilder();

			sb.append("https://raw.githubusercontent.com/liferay/");
			sb.append(pullRequest.getRepositoryName());
			sb.append("/");
			sb.append(pullRequest.getRefName());
			sb.append("/");
			sb.append(pullRequest.getCIMergeSubrepo());
			sb.append("/.gitrepo");

			String gitrepoContent = processURL(sb.toString());

			Matcher matcher = _gitrepoSHAPattern.matcher(gitrepoContent);

			while (matcher.find()) {
				currentSHA = matcher.group(1);
			}

			matcher = _gitrepoRepoPattern.matcher(gitrepoContent);

			String repo = "";

			while (matcher.find()) {
				repo = matcher.group(1);
			}

			String mergeSHA = pullRequest.getCIMergeSha();

			String compareURL =
				"https://github.com/liferay/" + repo + "/compare/" +
					currentSHA + "..." + mergeSHA;

			if (_log.isInfoEnabled()) {
				_log.info("Subrepo compare URL: " + compareURL);
			}

			String message =
				"Subrepo changes: " + compareURL +
					"\n\nci:test:sf and ci:test:relevant must pass in order " +
					"for auto-merge to initiate.";

			commentPullRequest(pullRequest, message);
		}
		catch (Exception e) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Skip generation of the ci:merge diff because of an " +
						"exception",
					e);
			}
		}
	}

	protected void commentPullRequest(PullRequest pullRequest, String message)
		throws Exception {

		if (!isGitHubPostEnabled()) {
			return;
		}

		JSONObject jsonObject = new JSONObject();

		jsonObject.put("body", message);

		processURL(
			"https://api.github.com/repos/" + pullRequest.getOwnerName() +
				"/" + pullRequest.getRepositoryName() + "/issues/" +
				pullRequest.getNumber() + "/comments",
			jsonObject.toString());
	}

	protected String encode(String string) throws Exception {
		return URLEncoder.encode(string, "UTF-8");
	}

	protected String formatCSV(String string) {
		string = string.replace(",", ", ");

		if (StringUtils.countMatches(string, ", ") == 1) {
			string = string.replace(", ", " or ");
		}
		else if (StringUtils.countMatches(string, ", ") > 1) {
			int index = string.lastIndexOf(", ");

			string =
				string.substring(0, index) + ", or " +
					string.substring(index + 2);
		}

		return string;
	}

	protected String getBuildURL(JSONObject payloadJSONObject)
		throws Exception {

		String commentsURL = payloadJSONObject.optString("comments_url");

		if (isBlank(commentsURL)) {
			JSONObject issueJSONObject = payloadJSONObject.optJSONObject(
				"issue");

			commentsURL = issueJSONObject.getString("comments_url");
		}

		JSONArray commentsJSONArray = new JSONArray(processURL(commentsURL));

		for (int i = 0; i < commentsJSONArray.length(); i++) {
			JSONObject commentJSONObject = commentsJSONArray.getJSONObject(i);

			String body = commentJSONObject.getString("body");

			Matcher buildURLMatcher = _buildURLPattern.matcher(body);

			if (buildURLMatcher.find()) {
				return buildURLMatcher.group("buildURL");
			}
		}

		return null;
	}

	protected long getK8sPullRequestTestSuiteFrequency(String testSuite) {
		long frequencyMinutes = Long.parseLong(
			_jenkinsBuildProperties.getProperty(
				"github.webhook.pullrequest.frequency[" + testSuite + "]",
				"0"));

		return 1000 * 60 * frequencyMinutes;
	}

	protected String getSubrepoCentralMergePullRequestRecipientName(
		String refName) {

		if (refName.equals("7.2.x") ||
			refName.equals("7.2.x-private") ||
			refName.equals("master") ||
			refName.equals("master-private")) {

			return "brianchandotcom";
		}

		return "liferay";
	}

	protected String getSubrepoPath(JSONObject payloadJSONObject)
		throws Exception {

		JSONObject headCommitJSONObject = payloadJSONObject.optJSONObject(
			"head_commit");

		if (headCommitJSONObject == null) {
			return null;
		}

		String commitMessage = headCommitJSONObject.optString("message");

		if ((commitMessage != null) && commitMessage.contains("LPS-0 Clear")) {
			String subrepoPath = commitMessage.replaceAll(".* ", "");

			if (subrepoPath.startsWith("modules/apps") ||
				subrepoPath.startsWith("modules/private/apps")) {

				return subrepoPath;
			}
		}

		JSONArray modifiedJSONArray = headCommitJSONObject.getJSONArray(
			"modified");

		for (int i = 0; i < modifiedJSONArray.length(); i++) {
			String fileName = modifiedJSONArray.getString(i);

			if (fileName.endsWith(".gitrepo")) {
				String subrepoPath = fileName.replaceAll("/.gitrepo", "");

				if (subrepoPath.startsWith("modules/apps") ||
					subrepoPath.startsWith("modules/private/apps")) {

					return subrepoPath;
				}
			}
		}

		return null;
	}

	protected long getTestPullRequestQueryStringExpiredTime() {
		//return System.currentTimeMillis() - 3600000; // 1 hour
		return System.currentTimeMillis() - 21600000; // 6 hours
	}

	protected long getTestPullRequestURLExpiredTime() {
		//return System.currentTimeMillis() - 3600000; // 1 hour
		return System.currentTimeMillis() - 21600000; // 6 hours
	}

	protected boolean hasLiferayEmailAddress(String login) throws Exception {
		if (login.equals("liferay")) {
			return true;
		}

		JSONObject jsonObject = new JSONObject(
			processURL("https://api.github.com/users/" + login));

		String emailAddress = jsonObject.optString("email");

		if ((emailAddress != null) && emailAddress.endsWith("@liferay.com")) {
			return true;
		}

		return false;
	}

	protected boolean isBotPush(JSONObject payloadJSONObject) throws Exception {
		String subrepoPath = getSubrepoPath(payloadJSONObject);

		if (isBlank(subrepoPath)) {
			return false;
		}

		return true;
	}

	protected boolean isK8sReady(String testSuite) {
		long currentTimestamp = System.currentTimeMillis();

		if (!_k8sPullRequestTestSuiteTimestampMap.containsKey(testSuite)) {
			_k8sPullRequestTestSuiteTimestampMap.put(
				testSuite, currentTimestamp);
		}

		long frequency = getK8sPullRequestTestSuiteFrequency(testSuite);

		long timestamp = _k8sPullRequestTestSuiteTimestampMap.get(testSuite);

		if (currentTimestamp < (timestamp + frequency)) {
			return true;
		}

		return false;
	}

	protected boolean isLiferayUser(String userLogin) throws Exception {
		/*JSONObject jsonObject = new JSONObject(
			processURL(
				"https://api.github.com/orgs/liferay/memberships/" +
					userLogin));

		String state = jsonObject.optString("state");

		if ((state != null) && state.equals("active")) {
			if (_log.isInfoEnabled()) {
				_log.info("Valid Liferay member: " + userLogin);
			}

			return true;
		}

		if (_log.isInfoEnabled()) {
			_log.info("Invalid Liferay member: " + userLogin);
		}

		return false;*/

		if (userLogin.equals("liferay")) {
			return true;
		}

		JSONArray jsonArray = new JSONArray(
			processURL("https://api.github.com/users/" + userLogin + "/orgs"));

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			String organizationLogin = jsonObject.getString("login");

			if (organizationLogin.equals("liferay")) {
				if (_log.isInfoEnabled()) {
					_log.info("Valid Liferay member: " + userLogin);
				}

				return true;
			}
		}

		if (_log.isInfoEnabled()) {
			_log.info("Invalid Liferay member: " + userLogin);
		}

		return false;
	}

	protected boolean isTestablePullRequest(PullRequest pullRequest)
		throws Exception {

		if (!pullRequest.isValidRef()) {
			StringBuilder sb = new StringBuilder(4);

			sb.append("Closing pull request because pulls for reference ");
			sb.append(pullRequest.getRefName());
			sb.append(" should not be sent to repository ");
			sb.append(pullRequest.getRepositoryName());

			String message = sb.toString();

			if (_log.isInfoEnabled()) {
				_log.info(message);
			}

			closePullRequest(pullRequest, message);

			return false;
		}

		if (!pullRequest.isWhitelistedOwner()) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Skip pull request because the owner " +
						pullRequest.getOwnerName() +
						" is not on the whitelist");
			}

			String message =
				"Pull request tests have been temporarily suspended.";

			commentPullRequest(pullRequest, message);

			return false;
		}

		String ownerName = pullRequest.getOwnerName();
		String repositoryName = pullRequest.getRepositoryName();
		String subrepoCentralMergePullRequestRecipientName =
			getSubrepoCentralMergePullRequestRecipientName(
				pullRequest.getRefName());

		if (ownerName.equals(subrepoCentralMergePullRequestRecipientName)) {
			if (pullRequest.isMergeSubrepoRequest()) {
				if (!pullRequest.isValidCIMergeFile()) {
					String message =
						"Closing pull request because a subrepo merge " +
							"request must only contain a single change to a " +
							"single ci-merge file.";

					if (_log.isInfoEnabled()) {
						_log.info(message);
					}

					closePullRequest(pullRequest, message);

					return false;
				}

				String sha = pullRequest.getCIMergeSha();

				if (sha.equals("")) {
					String message =
						"Closing pull request because the ci-merge file " +
							"modification is missing or incorrectly formatted";

					if (_log.isInfoEnabled()) {
						_log.info(message);
					}

					closePullRequest(pullRequest, message);

					return false;
				}
			}
		}

		if (ownerName.equals("liferay")) {
			if (!pullRequest.isSubrepo() &&
				!repositoryName.equals("liferay-portal-ee")) {

				if (_log.isInfoEnabled()) {
					_log.info(
						"Skip pull request because it is a pull sent to " +
							"liferay that is not a subrepo request");
				}

				return false;
			}
		}

		if (!pullRequest.isWhitelistedRepository()) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Skip pull request because the repository " +
						pullRequest.getRepositoryName() +
						" is not on the whitelist");
			}

			return false;
		}

		if (!isLiferayUser(pullRequest.getOwnerName())) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Skip pull request because the owner " +
						pullRequest.getOwnerName() + " does not have access");
			}

			return false;
		}

		if (!isLiferayUser(pullRequest.getTesterName())) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Skip pull request because the tester " +
						pullRequest.getSenderName() + " does not have access");
			}

			if (hasLiferayEmailAddress(pullRequest.getSenderName())) {
				StringBuilder sb = new StringBuilder();

				sb.append("Your pull request was not tested because you ");
				sb.append("are not a member of the Liferay organization. ");
				sb.append("Please make sure that you have been added and ");
				sb.append("that your organization membership is set as ");
				sb.append("Public. See https://help.github.com/articles");
				sb.append("/publicizing-or-hiding-organization-");
				sb.append("membership for more information.");

				commentPullRequest(pullRequest, sb.toString());
			}

			return false;
		}

		if (!pullRequest.isAllowedSender()) {
			StringBuilder sb = new StringBuilder(7);

			sb.append("Closing pull request because ");
			sb.append(pullRequest.getSenderName());
			sb.append(" is not an allowed sender on this branch. Please ");
			sb.append(" resend this pull request to one of the following ");
			sb.append(" allowed senders: ");
			sb.append(formatCSV(pullRequest.getAllowedSenderNames()));
			sb.append(".");

			String message = sb.toString();

			if (_log.isInfoEnabled()) {
				_log.info(message);
			}

			closePullRequest(pullRequest, message);

			return false;
		}

		if (!pullRequest.hasRequiredCollaborators()) {
			if (_log.isInfoEnabled()) {
				StringBuilder sb = new StringBuilder(4);

				sb.append("Skip pull request because ");
				sb.append("liferay-continuous-integration does not have ");
				sb.append("write access to ");
				sb.append(pullRequest.getRepoHTMLURL());

				_log.info(sb.toString());
			}

			StringBuilder sb = new StringBuilder(5);

			sb.append("Your pull request was not tested because your ");
			sb.append("repository has not set liferay-continuous-integration ");
			sb.append("as a collaborator. See https://grow.liferay.com/share/");
			sb.append("Pull+Request+Tester+for+Liferay+Developers for more ");
			sb.append("information.");

			commentPullRequest(pullRequest, sb.toString());

			return false;
		}

		if (!pullRequest.hasValidJIRAReferences()) {
			StringBuilder sb = new StringBuilder(7);

			sb.append("Closing pull request because at least one commit ");
			sb.append("message is missing a reference to a required JIRA ");
			sb.append("project: ");
			sb.append(join(pullRequest.getJIRAProjectKeys()));
			sb.append(". Please verify that the JIRA project keys are ");
			sb.append("specified in ci.properties in the liferay-portal ");
			sb.append("repository.");

			String message = sb.toString();

			if (_log.isInfoEnabled()) {
				_log.info(message);
			}

			closePullRequest(pullRequest, message);

			return false;
		}

		// TODO: Ensure every commit has a required key. Make sure all keys are
		// valid via https://issues.liferay.com/rest/api/2/issue/LPS-5331. Make
		// sure sender is a team member of the component via
		// https://api.github.com/search/users?q=brian.chan@liferay.com+in%3A
		// email&type=Users

		return true;
	}

	protected boolean isValidPullRequestRefSHA(
		PullRequest pullRequest, String refSHA)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		sb.append("https://api.github.com/repos/liferay/");
		sb.append(pullRequest.getRepositoryName());
		sb.append("/commits/");
		sb.append(refSHA);

		JSONObject commitJSONObject = new JSONObject(processURL(sb.toString()));

		if (!commitJSONObject.has("sha")) {
			return false;
		}

		return true;
	}

	protected String join(String[] array) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < array.length; i++) {
			if ((i + 1) == array.length) {
				sb.append(" or ");
			}

			sb.append(array[i]);

			if ((i + 1) < array.length) {
				sb.append(", ");
			}
		}

		return sb.toString();
	}

	protected void lockPullRequest(PullRequest pullRequest) throws Exception {
		if (!isGitHubPostEnabled()) {
			return;
		}

		processURL(pullRequest.getIssueURL() + "/" + "lock", null, "PUT");
	}

	protected void lockPullRequest(PullRequest pullRequest, String message)
		throws Exception {

		if (!isGitHubPostEnabled()) {
			return;
		}

		commentPullRequest(pullRequest, message);

		lockPullRequest(pullRequest);
	}

	protected void mergeSubrepo(PullRequest pullRequest, boolean force)
		throws Exception {

		String ownerName = pullRequest.getOwnerName();
		String refName = pullRequest.getRefName();

		String subrepoCentralMergePullRequestRecipientName =
			getSubrepoCentralMergePullRequestRecipientName(refName);

		if (!ownerName.equals(subrepoCentralMergePullRequestRecipientName)) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Skip merge subrepo because the user is not " +
						subrepoCentralMergePullRequestRecipientName);
			}
		}

		JSONObject jsonObject = new JSONObject();

		jsonObject.put("branch", refName);
		jsonObject.put("command", "pull");
		jsonObject.put("pullRequestNumber", pullRequest.getNumber());
		jsonObject.put("repo", pullRequest.getRepositoryName());

		try {
			if (!pullRequest.isValidCIMergeFile()) {
				String message =
					"Closing pull request because a subrepo merge " +
						"request must only contain a single change to a " +
						"single ci-merge file";

				if (_log.isInfoEnabled()) {
					_log.info(message);
				}

				closePullRequest(pullRequest, message);

				return;
			}
		}
		catch (Exception e) {
			String message = "Skip merge subrepo because of a GitHub error";

			if (_log.isInfoEnabled()) {
				_log.info(message, e);
			}

			commentPullRequest(pullRequest, message + ".");

			return;
		}

		String sha = pullRequest.getCIMergeSha();

		if (sha.equals("")) {
			String message =
				"Closing pull request because the ci-merge file modification " +
					"is missing or incorrectly formatted";

			if (_log.isInfoEnabled()) {
				_log.info(message);
			}

			closePullRequest(pullRequest, message);

			return;
		}

		if (_log.isInfoEnabled()) {
			_log.info("Merge subrepo sha: " + sha);
		}

		jsonObject.put("sha", sha);

		String subrepo = pullRequest.getCIMergeSubrepo();

		if (_log.isInfoEnabled()) {
			_log.info("Merge subrepo name: " + subrepo);
		}

		jsonObject.put("subrepo", subrepo);

		String statusURL =
			"https://api.github.com/repos/" +
				subrepoCentralMergePullRequestRecipientName + "/" +
				pullRequest.getRepositoryName() + "/commits/" +
				pullRequest.getSenderRefSHA() + "/status";

		JSONArray statusesJSONArray = null;

		for (int i = 0; i < 3; i++) {
			try {
				JSONObject statusJSONObject = new JSONObject(
					processURL(statusURL));

				statusesJSONArray = statusJSONObject.getJSONArray("statuses");

				break;
			}
			catch (Exception e) {
				if (_log.isInfoEnabled()) {
					_log.info("Retrying " + statusURL, e);
				}

				Thread.sleep(1000);
			}
		}

		if (statusesJSONArray == null) {
			String message = "Skip merge subrepo because of a GitHub error";

			if (_log.isInfoEnabled()) {
				_log.info(message);
			}

			commentPullRequest(pullRequest, message + ".");

			return;
		}

		if (_log.isInfoEnabled()) {
			_log.info("Merge subrepo force: " + force);
		}

		if (!force) {
			Map<String, String> statuses = new HashMap();

			for (int i = 0; i < statusesJSONArray.length(); i++) {
				JSONObject statusJSONObject = statusesJSONArray.getJSONObject(
					i);

				String context = statusJSONObject.getString("context");
				String state = statusJSONObject.getString("state");

				statuses.put(context, state);
			}

			boolean validStatus = false;

			if (statuses.containsKey("liferay/ci:test:relevant") &&
				statuses.containsKey("liferay/ci:test:sf")) {

				String relevantStatus = statuses.get(
					"liferay/ci:test:relevant");
				String sfStatus = statuses.get("liferay/ci:test:sf");

				if (relevantStatus.equals("success") &&
					sfStatus.equals("success")) {

					validStatus = true;
				}
			}

			if (!validStatus) {
				String message =
					"Skip merge subrepo because tests have not passed";

				if (_log.isInfoEnabled()) {
					_log.info(message);
				}

				commentPullRequest(pullRequest, message + ".");

				return;
			}
		}

		String gitHubWebSubrepoHostname = _jenkinsProperties.getProperty(
			"github.web.subrepo.hostname");

		try {
			jsonObject.put("remove", "true");

			processURL(
				"http://" + gitHubWebSubrepoHostname +
					"/osb-github-web/subrepo",
				jsonObject.toString());
		}
		catch (Exception e) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Unable to remove key from subrepo processor queue", e);
			}
		}

		jsonObject.remove("remove");

		try {
			String subrepoJSON = processURL(
				"http://" + gitHubWebSubrepoHostname +
					"/osb-github-web/subrepo",
				jsonObject.toString());

			JSONObject subrepoJSONObject = new JSONObject(subrepoJSON);

			int queueSize = subrepoJSONObject.getInt("queueSize");

			String message =
				"This subrepo merge request was added to the processor queue " +
					"at position " + queueSize;

			if (_log.isInfoEnabled()) {
				_log.info(message);
			}

			commentPullRequest(pullRequest, message + ".");
		}
		catch (Exception e) {
			e.printStackTrace();

			String message = "Skip merge subrepo because of an internal error";

			if (_log.isInfoEnabled()) {
				_log.info(message, e);
			}

			commentPullRequest(pullRequest, message + ".");
		}

		return;
	}

	protected void openPullRequest(PullRequest pullRequest) throws Exception {
		if (!isGitHubPostEnabled()) {
			return;
		}

		JSONObject jsonObject = new JSONObject();

		jsonObject.put("state", "open");

		String pullRequestJSON = processURL(
			"https://api.github.com/repos/" + pullRequest.getOwnerName() +
				"/" + pullRequest.getRepositoryName() + "/pulls/" +
				pullRequest.getNumber(),
			jsonObject.toString(), "PATCH");

		JSONObject pullRequestJSONObject = new JSONObject(pullRequestJSON);

		JSONArray errorsJSONArray = pullRequestJSONObject.optJSONArray(
			"errors");

		if (errorsJSONArray == null) {
			return;
		}

		for (int i = 0; i < errorsJSONArray.length(); i++) {
			JSONObject errorJSONObject = errorsJSONArray.getJSONObject(i);

			String message = errorJSONObject.optString("message");

			if (message == null) {
				continue;
			}

			commentPullRequest(pullRequest, "GitHub error message: " + message);
		}
	}

	protected void stopJenkinsTests(JSONObject payloadJSONObject)
		throws Exception {

		String buildURL = getBuildURL(payloadJSONObject);

		if (buildURL != null) {
			String jenkinsAdminUserToken = _jenkinsProperties.getProperty(
				"jenkins.admin.user.token");

			JenkinsStopBuildUtil.stopBuild(
				buildURL, "jenkins-admin", jenkinsAdminUserToken);
		}
	}

	protected void syncAutopull(JSONObject payloadJSONObject) throws Exception {
		String ref = payloadJSONObject.getString("ref");

		if (!ref.startsWith("refs/heads/")) {
			return;
		}

		String branch = StringUtils.replace(ref, "refs/heads/", "");

		if (_log.isInfoEnabled()) {
			_log.info("Sync autopull branch: " + branch);
		}

		JSONObject repositoryJSONObject = payloadJSONObject.getJSONObject(
			"repository");

		JSONObject ownerJSONObject = repositoryJSONObject.getJSONObject(
			"owner");

		String ownerName = ownerJSONObject.getString("name");

		if (_log.isInfoEnabled()) {
			_log.info("Sync autopull owner: " + ownerName);
		}

		if (!ownerName.equals("liferay")) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Skip sync autopull because the owner " + ownerName +
						" is not on the whitelist");
			}

			return;
		}

		String repo = repositoryJSONObject.getString("name");

		if (_log.isInfoEnabled()) {
			_log.info("Sync autopull repo: " + repo);
		}

		if (!isValidAutopull(branch, repo)) {
			_log.info(
				"Skip sync autopull because the branch or repo name is " +
					"invalid");

			return;
		}

		String jenkinsAccessToken = _jenkinsProperties.getProperty(
			"jenkins.access.token");

		String url =
			"http://test-1-0/job/merge-central-subrepository(" +
				branch + ")/buildWithParameters?token=" + jenkinsAccessToken;

		if (isGitHubAutopullEnabled()) {
			try {
				processURL(url);
			}
			catch (Exception e) {
				if (_log.isInfoEnabled()) {
					_log.info("Skip sync autopull", e);
				}

				return;
			}
		}
		else if (_log.isInfoEnabled()) {
			_log.info("Sync autopull URL: " + url);
		}
	}

	protected void syncMirror(JSONObject payloadJSONObject) throws Exception {
		JSONObject jsonObject = new JSONObject();

		if (!isGitHubMirrorEnabled()) {
			return;
		}

		JSONObject repositoryJSONObject = payloadJSONObject.getJSONObject(
			"repository");

		JSONObject ownerJSONObject = repositoryJSONObject.getJSONObject(
			"owner");

		String ownerName = ownerJSONObject.getString("name");

		if (!ownerName.equals("liferay")) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Skip sync mirror because the owner " + ownerName +
						" is not on the whitelist");
			}

			return;
		}

		String repo = repositoryJSONObject.getString("name");

		if (_log.isInfoEnabled()) {
			_log.info("Sync mirror repo: " + repo);
		}

		jsonObject.put("repo", repo);

		String sha = payloadJSONObject.getString("after");

		if (_log.isInfoEnabled()) {
			_log.info("Sync mirror sha: " + sha);
		}

		jsonObject.put("sha", sha);

		String gitHubWebMirrorHostname = _jenkinsProperties.getProperty(
			"github.web.mirror.hostname");

		try {
			processURL(
				"http://" + gitHubWebMirrorHostname + "/osb-github-web/mirror",
				jsonObject.toString());
		}
		catch (Exception e) {
			if (_log.isInfoEnabled()) {
				_log.info("Skip sync mirror", e);
			}
		}
	}

	protected void syncSubrepo(JSONObject payloadJSONObject) throws Exception {
		JSONObject jsonObject = new JSONObject();

		if (!isGitHubSubrepoSyncEnabled()) {
			return;
		}

		String ref = payloadJSONObject.getString("ref");

		if (!ref.startsWith("refs/heads/")) {
			return;
		}

		String branch = StringUtils.replace(ref, "refs/heads/", "");

		if (_log.isInfoEnabled()) {
			_log.info("Sync subrepo branch: " + branch);
		}

		jsonObject.put("branch", branch);

		JSONObject repositoryJSONObject = payloadJSONObject.getJSONObject(
			"repository");

		JSONObject ownerJSONObject = repositoryJSONObject.getJSONObject(
			"owner");

		String ownerName = ownerJSONObject.getString("name");

		if (!ownerName.equals("liferay")) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Skip sync subrepo because the owner " + ownerName +
						" is not on the whitelist");
			}

			return;
		}

		String repo = repositoryJSONObject.getString("name");

		if (_log.isInfoEnabled()) {
			_log.info("Sync subrepo repo: " + repo);
		}

		jsonObject.put("repo", repo);

		String sha = payloadJSONObject.getString("after");

		if (_log.isInfoEnabled()) {
			_log.info("Sync subrepo sha: " + sha);
		}

		jsonObject.put("sha", sha);

		jsonObject.put("pullRequestNumber", "0");

		String command = "push";
		String propertyName = "github.web.subrepo.hostname";
		String subrepo = "all";

		if (isBotPush(payloadJSONObject)) {
			command = "release";
			propertyName = "github.web.release.hostname";
			subrepo = getSubrepoPath(payloadJSONObject);
		}

		jsonObject.put("command", command);

		if (_log.isInfoEnabled()) {
			_log.info("Sync subrepo command: " + command);
		}

		jsonObject.put("subrepo", subrepo);

		if (_log.isInfoEnabled()) {
			_log.info("Sync subrepo argument: " + subrepo);
		}

		String gitHubWebSubrepoHostname = _jenkinsProperties.getProperty(
			propertyName);

		try {
			processURL(
				"http://" + gitHubWebSubrepoHostname +
					"/osb-github-web/subrepo",
				jsonObject.toString());
		}
		catch (Exception e) {
			if (_log.isInfoEnabled()) {
				_log.info("Skip sync subrepo", e);
			}
		}
	}

	public void testPullRequest(String pullRequestURL) throws Exception {
		testPullRequest(new JSONObject(processURL(pullRequestURL)));
	}

	protected void testPullRequest(JSONObject pullRequestJSONObject)
		throws Exception {

		PullRequest pullRequest = new PullRequest(pullRequestJSONObject);

		testPullRequest(pullRequest);
	}

	protected void testPullRequest(PullRequest pullRequest) throws Exception {
		if (!isTestablePullRequest(pullRequest)) {
			return;
		}

		StringBuilder sb = new StringBuilder();

		sb.append("CI_TEST_SUITE=");
		sb.append(encode(pullRequest.getCITestSuite()));
		sb.append("&GITHUB_PULL_REQUEST_NUMBER=");
		sb.append(pullRequest.getNumber());
		sb.append("&GITHUB_ORIGIN_NAME=");
		sb.append(encode(pullRequest.getOriginName()));
		sb.append("&GITHUB_RECEIVER_USERNAME=");
		sb.append(encode(pullRequest.getOwnerName()));
		sb.append("&GITHUB_SENDER_BRANCH_NAME=");
		sb.append(encode(pullRequest.getSenderRefName()));
		sb.append("&GITHUB_SENDER_BRANCH_SHA=");
		sb.append(pullRequest.getSenderRefSHA());
		sb.append("&GITHUB_SENDER_USERNAME=");
		sb.append(encode(pullRequest.getSenderName()));
		sb.append("&GITHUB_UPSTREAM_BRANCH_NAME=");
		sb.append(encode(pullRequest.getRefName()));
		sb.append("&GITHUB_UPSTREAM_BRANCH_SHA=");
		sb.append(pullRequest.getRefSHA());
		sb.append("&PULL_REQUEST_URL=");
		sb.append(pullRequest.getHtmlURL());
		sb.append("&REPOSITORY_NAME=");
		sb.append(encode(pullRequest.getRepositoryName()));

		String testPullRequestQueryString = sb.toString();

		addTestPullRequestQueryString(testPullRequestQueryString);

		boolean gitHubWebhookPullRequestMirroring =
			Boolean.parseBoolean(
				_jenkinsBuildProperties.getProperty(
					"github.webhook.pullrequest.mirroring",
					Boolean.FALSE.toString()));

		boolean invokedOnK8s = false;

		if (pullRequest.isK8sEligible() &&
			isK8sReady(pullRequest.getCITestSuite())) {

			try {
				pullRequest.invoke(
					"http://test-4-1.k8s-2.liferay.com",
					testPullRequestQueryString);

				invokedOnK8s = true;
			}
			catch (Exception e) {
				if (_log.isInfoEnabled()) {
					_log.info(
						"Unable to invoke pull request test on K8s cluster", e);
				}
			}
		}

		if (gitHubWebhookPullRequestMirroring || !invokedOnK8s) {
			String masterURL;

			try {
				masterURL =
					LoadBalancerUtil.getMostAvailableMasterURL(
						"base.invocation.url", "http://test-1.liferay.com");
			}
			catch (Exception e) {
				if (_log.isInfoEnabled()) {
					_log.info(
						"Setting base invocation URL to " +
							"http://test-1.liferay.com because load balancer " +
							"threw an exception");
				}

				masterURL = "http://test-1.liferay.com";
			}

			pullRequest.invoke(masterURL, testPullRequestQueryString);
		}
	}

	private static final String[] _URLS_JENKINS_BUILD_PROPERTIES = {
		"http://mirrors-no-cache.lax.liferay.com/github.com/liferay" +
			"/liferay-jenkins-ee/build.properties",
		"http://mirrors-no-cache.lax.liferay.com/github.com/liferay" +
			"/liferay-jenkins-ee/commands/build.properties"
	};

	private static Log _log = LogFactory.getLog(
		JenkinsDoulosRequestProcessor.class);

	private static Pattern _buildURLPattern = Pattern.compile(
		"Build[\\w\\s]*started.*Job Link: <a href=\"(?<buildURL>[^\"]+)\"");
	private static Map<String, Long> _k8sPullRequestTestSuiteTimestampMap =
		new HashMap();
	private static Pattern _refNamePattern = Pattern.compile(
		"(?<branchName>(ee-)?\\w+(\\.\\w+)*)(?<private>-private)?(-.*)?");
	private static Pattern _repositoryNamePattern = Pattern.compile(
		"(?<public>.*?)(-ee|liferay-jenkins-ee|-private)?$");
	private static Pattern _testPattern = Pattern.compile(
		"ci:(re)?test:(?<testOption1>[^:\\s]+)(:(?<testOption2>[^:\\s]+))?");

	private Pattern _ciMergeSHAPattern = Pattern.compile("\\+([0-9a-f]{40})");
	private Pattern _gitrepoRepoPattern = Pattern.compile(
		"remote = .*/([^\\.]*)\\.git");
	private Pattern _gitrepoSHAPattern = Pattern.compile(
		"commit = ([0-9a-f]{40})");
	private Properties _jenkinsBuildProperties = null;
	private Properties _jenkinsProperties = new Properties();
	private Map<String, Long> _testPullRequestQueryStrings;
	private Map<String, Long> _testPullRequestURLs;

	private class Commit {

		public Commit(JSONObject commitJSONObject) {
			_commitJSONObject = commitJSONObject;
		}

		public String getEmailAddress() throws Exception {
			JSONObject authorJSONObject = _commitJSONObject.getJSONObject(
				"author");

			return authorJSONObject.getString("email");
		}

		public String getMessage() throws Exception {
			return _commitJSONObject.getString("message");
		}

		private JSONObject _commitJSONObject;

	}

	private class GitHubAPICallException extends Exception {
	}

	private class PatchMethod extends PostMethod {

		public PatchMethod(String url) {
			super(url);
		}

		@Override
		public String getName() {
			return "PATCH";
		}

	}

	private class PullRequest {

		public PullRequest(JSONObject pullRequestJSONObject) throws Exception {
			_pullRequestJSONObject = pullRequestJSONObject;

			JSONObject baseJSONObject = pullRequestJSONObject.getJSONObject(
				"base");

			_number = pullRequestJSONObject.getInt("number");

			if (_log.isInfoEnabled()) {
				_log.info("Pull request number: " + _number);
			}

			String url = pullRequestJSONObject.getString("url");

			_url = url;

			int x = url.indexOf("repos/");

			x = url.indexOf("/", x) + 1;

			int y = url.indexOf("/", x);

			_ownerName = url.substring(x, y);

			if (_log.isInfoEnabled()) {
				_log.info("Pull request owner: " + _ownerName);
			}

			_refName = baseJSONObject.getString("ref");

			if (_log.isInfoEnabled()) {
				_log.info("Pull request ref name: " + _refName);
			}

			Matcher matcher = _refNamePattern.matcher(_refName);

			if (matcher.find()) {
				String portalRefName = matcher.group("branchName");

				String privateGroup = matcher.group("private");

				if (privateGroup != null) {
					portalRefName += matcher.group("private");
				}

				_portalRefName = portalRefName;

				if (_log.isInfoEnabled()) {
					_log.info(
						"Pull request portal ref name: " + _portalRefName);
				}
			}

			x = y + 1;

			y = url.indexOf("/", x);

			_repositoryName = url.substring(x, y);

			String branchURL =
				"https://api.github.com/repos/liferay/" + _repositoryName +
					"/branches/" + _refName;

			if (_repositoryName.startsWith("com-liferay-") &&
				!_repositoryName.endsWith("-private")) {

				branchURL =
					"https://api.github.com/repos/liferay/" + _repositoryName +
						"-private/branches/" + _refName;
			}

			JSONObject branchJSONObject = new JSONObject(processURL(branchURL));

			JSONObject commitJSONObject = null;

			try {
				commitJSONObject = branchJSONObject.getJSONObject("commit");
			}
			catch (JSONException jsone) {
				jsone.printStackTrace();
			}

			if (commitJSONObject != null) {
				_refSHA = commitJSONObject.getString("sha");
			}

			if (_log.isInfoEnabled()) {
				_log.info("Pull request ref sha: " + _refSHA);
			}

			if (_log.isInfoEnabled()) {
				_log.info("Pull request repository: " + _repositoryName);
			}

			JSONObject userJSONObject = pullRequestJSONObject.getJSONObject(
				"user");

			_senderName = userJSONObject.getString("login");

			if (_log.isInfoEnabled()) {
				_log.info("Pull request sender name: " + _senderName);
			}

			_testerName = _senderName;

			JSONObject headJSONObject = pullRequestJSONObject.getJSONObject(
				"head");

			userJSONObject = headJSONObject.getJSONObject("user");

			_originName = userJSONObject.getString("login");

			if (_log.isInfoEnabled()) {
				_log.info("Pull request origin name: " + _originName);
			}

			_senderRefName = headJSONObject.getString("ref");

			if (_log.isInfoEnabled()) {
				_log.info("Pull request sender ref name: " + _senderRefName);
			}

			_senderRefSHA = headJSONObject.getString("sha");

			if (_log.isInfoEnabled()) {
				_log.info("Pull request sender ref sha: " + _senderRefSHA);
			}

			JSONArray commitsJSONArray = new JSONArray(
				processURL(_pullRequestJSONObject.getString("commits_url")));

			JSONObject firstCommitJSONObject = commitsJSONArray.getJSONObject(
				0);

			JSONArray parentsJSONArray = firstCommitJSONObject.getJSONArray(
				"parents");

			JSONObject firstParentJSONObject = parentsJSONArray.getJSONObject(
				0);

			_commonParentSHA = firstParentJSONObject.getString("sha");
		}

		public String getAllowedSenderNames() throws Exception {
			return getCIProperty("allowed.sender.names[" + _ownerName + "]");
		}

		public String getBaseBranchDescriptionHTML() {
			StringBuilder sb = new StringBuilder();

			sb.append("<h4>Base Branch:</h4>");
			sb.append("Branch Name:<a href=\"https://github.com/liferay/");
			sb.append(_repositoryName);
			sb.append("/tree/");
			sb.append(_refName);
			sb.append("\">");
			sb.append(_refName);
			sb.append("</a><br />Branch GIT ID: ");
			sb.append("<a href=\"https://github.com/liferay/");
			sb.append(_repositoryName);
			sb.append("/commit/");
			sb.append(_refSHA);
			sb.append("\">");
			sb.append(_refSHA);
			sb.append("</a>");

			return sb.toString();
		}

		public String getCIJobName() {
			if (_ciTestSuite.equals("sf")) {
				return "test-portal-source-format";
			}

			if (_repositoryName.equals("liferay-jenkins-ee")) {
				return "test-jenkins-acceptance-pullrequest";
			}

			StringBuilder sb = new StringBuilder();

			if (_repositoryName.startsWith("com-liferay-")) {
				sb.append("test-subrepository-acceptance-pullrequest");
			}
			else if (_repositoryName.startsWith("liferay-plugins")) {
				sb.append("test-plugins-acceptance-pullrequest");
			}
			else if (_repositoryName.startsWith("liferay-portal")) {
				sb.append("test-portal-acceptance-pullrequest");
			}

			sb.append("(");

			if (isSubrepo() && (_portalRefName != null)) {
				sb.append(_portalRefName);
			}
			else {
				sb.append(_refName);
			}

			sb.append(")");

			return sb.toString();
		}

		public String getCIMergeSha() throws Exception {
			List<JSONObject> fileJSONObjects = null;

			try {
				fileJSONObjects = getFileJSONObjects();
			}
			catch (Exception e) {
				return "";
			}

			JSONObject fileJSONObject = fileJSONObjects.get(0);

			String fileName = fileJSONObject.getString("filename");

			if (!fileName.endsWith("/ci-merge")) {
				return "";
			}

			String patch = fileJSONObject.getString("patch");

			Matcher matcher = _ciMergeSHAPattern.matcher(patch);

			int counter = 0;
			String sha = "";

			while (matcher.find()) {
				sha = matcher.group(1);

				counter++;
			}

			if (counter != 1) {
				return "";
			}

			return sha;
		}

		public String getCIMergeSubrepo() throws Exception {
			List<JSONObject> fileJSONObjects = getFileJSONObjects();

			JSONObject fileJSONObject = fileJSONObjects.get(0);

			String fileName = fileJSONObject.getString("filename");

			return StringUtils.replace(fileName, "/ci-merge", "");
		}

		public String getCIProperty(String key) throws Exception {
			if (_ciProperties == null) {
				Matcher matcher = _refNamePattern.matcher(_refName);

				if (matcher.find()) {
					String baseRefName = matcher.group("branchName");

					String repositoryName = _repositoryName;

					matcher = _repositoryNamePattern.matcher(_repositoryName);

					if (matcher.find()) {
						repositoryName = matcher.group("public");
					}

					if ((repositoryName == null) || repositoryName.isEmpty()) {
						return null;
					}

					StringBuilder sb = new StringBuilder();

					sb.append("https://raw.githubusercontent.com/liferay/");
					sb.append(repositoryName);
					sb.append("/");
					sb.append(baseRefName);
					sb.append("/ci.properties");

					try {
						String ciPropertiesString = processURL(sb.toString());

						Properties ciProperties = new Properties();

						ciProperties.load(new StringReader(ciPropertiesString));

						_ciProperties = ciProperties;
					}
					catch (Exception e) {
						System.out.println(
							"Unable to load ci.properties from " +
								sb.toString());

						return null;
					}
				}
			}

			return _ciProperties.getProperty(key);
		}

		public String getCITestSuite() {
			return _ciTestSuite;
		}

		public String getCollaboratorsURL() throws Exception {
			JSONObject baseJSONObject = _pullRequestJSONObject.getJSONObject(
				"base");

			JSONObject repoJSONObject = baseJSONObject.getJSONObject("repo");

			return repoJSONObject.getString("collaborators_url");
		}

		public List<Commit> getCommits() throws Exception {
			if (_commits != null) {
				return _commits;
			}

			List<Commit> commits = new ArrayList<Commit>();

			String commitsURL = _pullRequestJSONObject.getString("commits_url");

			for (int i = 0; i < 6; i++) {
				try {
					JSONArray commitsJSONArray = new JSONArray(
						processURL(commitsURL));

					for (int j = 0; j < commitsJSONArray.length(); j++) {
						JSONObject jsonObject = commitsJSONArray.getJSONObject(
							j);

						JSONObject commitJSONObject = jsonObject.getJSONObject(
							"commit");

						Commit commit = new Commit(commitJSONObject);

						commits.add(commit);
					}

					_commits = commits;

					return _commits;
				}
				catch (Exception e) {
					if (_log.isInfoEnabled()) {
						_log.info(
							"Retrying " + commitsURL +
								" because the JSON response was not an array",
							e);
					}

					Thread.sleep(5000);
				}
			}

			throw new RuntimeException("Unable to process commits URL");
		}

		public String getCommonParentSHA() {
			return _commonParentSHA;
		}

		public List<JSONObject> getFileJSONObjects() throws Exception {
			if (_fileJSONObjects != null) {
				return _fileJSONObjects;
			}

			List<JSONObject> fileJSONObjects = new ArrayList<JSONObject>();

			String filesURL =
				"https://api.github.com/repos/" + _ownerName + "/" +
					_repositoryName + "/pulls/" + _number + "/files";

			for (int i = 0; i < 6; i++) {
				try {
					JSONArray filesJSONArray = new JSONArray(
						processURL(filesURL));

					for (int j = 0; j < filesJSONArray.length(); j++) {
						JSONObject fileJSONObject =
							filesJSONArray.getJSONObject(j);

						fileJSONObjects.add(fileJSONObject);
					}

					_fileJSONObjects = fileJSONObjects;

					return _fileJSONObjects;
				}
				catch (Exception e) {
					if (_log.isInfoEnabled()) {
						_log.info(
							"Retrying " + filesURL +
								" because the JSON response was not an array",
							e);
					}

					Thread.sleep(5000);
				}
			}

			throw new RuntimeException("Unable to process files URL");
		}

		public String getHtmlURL() throws Exception {
			return _pullRequestJSONObject.getString("html_url");
		}

		public String getIssueURL() throws Exception {
			return _pullRequestJSONObject.getString("issue_url");
		}

		public String[] getJIRAProjectKeys() {
			return _jiraProjectKeys;
		}

		public List<String> getLabelNames() throws Exception {
			List<String> labelNames = new ArrayList<String>();

			JSONArray labelJSONArray = _pullRequestJSONObject.getJSONArray(
				"labels");

			for (int i = 0; i < labelJSONArray.length(); i++) {
				JSONObject labelJSONObject = labelJSONArray.getJSONObject(i);

				labelNames.add(labelJSONObject.getString("name"));
			}

			return labelNames;
		}

		public int getNumber() {
			return _number;
		}

		public String getOriginName() {
			return _originName;
		}

		public String getOwnerName() {
			return _ownerName;
		}

		public String getRefName() {
			return _refName;
		}

		public String getRefSHA() {
			return _refSHA;
		}

		public String getRepoHTMLURL() throws Exception {
			JSONObject baseJSONObject = _pullRequestJSONObject.getJSONObject(
				"base");

			JSONObject repoJSONObject = baseJSONObject.getJSONObject("repo");

			return repoJSONObject.getString("html_url");
		}

		public String getRepositoryName() {
			return _repositoryName;
		}

		public String getSenderName() {
			return _senderName;
		}

		public String getSenderRefName() {
			return _senderRefName;
		}

		public String getSenderRefSHA() {
			return _senderRefSHA;
		}

		public String getStatusesURL() throws Exception {
			return _pullRequestJSONObject.getString("statuses_url");
		}

		public String getTesterName() {
			return _testerName;
		}

		public boolean hasRequiredCollaborators() throws Exception {
			String collaboratorsURL = getCollaboratorsURL();

			collaboratorsURL = collaboratorsURL.replace(
				"{/collaborator}", "liferay-continuous-integration");

			try {
				processURL(collaboratorsURL);
			}
			catch (Exception e) {
				return false;
			}

			return true;
		}

		public boolean hasValidJIRAReferences() throws Exception {
			if (!_ownerName.equals("brianchandotcom")) {
				return true;
			}

			String jiraProjectKeysString = getCIProperty("jira.project.keys");

			if (_log.isInfoEnabled()) {
				_log.info("JIRA project keys: " + jiraProjectKeysString);
			}

			if (jiraProjectKeysString == null) {
				return true;
			}

			_jiraProjectKeys = jiraProjectKeysString.split(",");

			if (_jiraProjectKeys.length == 0) {
				return true;
			}

			for (Commit commit : getCommits()) {
				boolean hasJIRAProjectKey = false;

				String message = commit.getMessage();

				if (message.contains("subrepo:ignore")) {
					return true;
				}

				for (int i = 0; i < _jiraProjectKeys.length; i++) {
					if (message.contains(_jiraProjectKeys[i] + "-")) {
						if (_log.isInfoEnabled()) {
							_log.info(
								"Contains JIRA project keys " +
									_jiraProjectKeys[i]);
						}

						hasJIRAProjectKey = true;
					}
				}

				String emailAddress = commit.getEmailAddress();

				if (emailAddress.equals("brian.chan@liferay.com") ||
					emailAddress.equals("continuous-integration@liferay.com") ||
					emailAddress.equals("samuel.tran@liferay.com")) {

					if (_log.isInfoEnabled()) {
						_log.info("Allow commit from Brian, Sam, or CI");
					}

					continue;
				}

				if (!hasJIRAProjectKey) {
					return false;
				}
			}

			return true;
		}

		public void invoke(String masterURL, String testPullRequestQueryString)
			throws Exception {

			String invocationURL = StringUtils.join(
				masterURL, "/job/", getCIJobName(), "/buildWithParameters?",
				testPullRequestQueryString, "&token=", "#add-token-here#");

			addTestPullRequestURL(invocationURL);

			if (true) {
				processURL(invocationURL);

				String publicMasterURL = masterURL.replace("http:", "https:");

				if (!publicMasterURL.contains(".liferay.com")) {
					publicMasterURL = publicMasterURL + ".liferay.com";
				}

				String publicJobURL = StringUtils.join(
					publicMasterURL, "/job/", getCIJobName());

				if (_log.isInfoEnabled()) {
					_log.info(
						StringUtils.join(
							"Pull request test invoked at ", publicJobURL,
							"."));
				}

				_updateInvocationStatus(publicJobURL);
			}
			else {
				if (_log.isInfoEnabled()) {
					_log.info("Pull request test URL: " + invocationURL);
				}
			}
		}

		public boolean isAllowedSender() throws Exception {
			String allowedSenderNamesString = getAllowedSenderNames();

			if (_log.isInfoEnabled()) {
				_log.info("Allowed sender names: " + allowedSenderNamesString);
			}

			if (allowedSenderNamesString == null) {
				return true;
			}

			_allowedSenderNames = allowedSenderNamesString.split(",");

			if (_allowedSenderNames.length == 0) {
				return true;
			}

			for (String allowedSenderName : _allowedSenderNames) {
				if (StringUtils.equalsIgnoreCase(
					_senderName, allowedSenderName)) {

					return true;
				}
			}

			return false;
		}

		public boolean isK8sEligible() throws Exception {
			if (!_isValidK8sPullRequestSender()) {
				return false;
			}

			if (!_isValidK8sPullRequestTestSuite()) {
				return false;
			}

			return true;
		}

		public boolean isMergeSubrepoRequest() throws Exception {
			for (JSONObject file : getFileJSONObjects()) {
				String fileName = file.getString("filename");

				if (fileName.endsWith("/ci-merge")) {
					return true;
				}
			}

			return false;
		}

		public boolean isSubrepo() {
			if (_repositoryName.startsWith("com-liferay-")) {
				return true;
			}

			return false;
		}

		public boolean isSynchronizeAllowed() throws Exception {
			if (_ownerName.equals("brianchandotcom")) {
				return false;
			}

			return true;
		}

		public boolean isValidCIMergeFile() throws Exception {
			List<JSONObject> fileJSONObjects = getFileJSONObjects();

			if (fileJSONObjects.size() > 1) {
				return false;
			}

			JSONObject fileJSONObject = fileJSONObjects.get(0);

			String fileName = fileJSONObject.getString("filename");

			if (fileName.endsWith("/ci-merge")) {
				return true;
			}

			return false;
		}

		public boolean isValidRef() {
			if (_refSHA == null) {
				return false;
			}

			if (_repositoryName.equals("liferay-jenkins-ee")) {
				if (!_refName.equals("master")) {
					return false;
				}
			}
			else if (_repositoryName.equals("liferay-portal") &&
				(_refName.equals("7.0.x") || _refName.equals("7.1.x") ||
					_refName.equals("7.2.x"))) {

				return false;
			}
			else if (_repositoryName.equals("liferay-portal-ee") &&
				(_refName.equals("7.0.x") || _refName.equals("7.1.x") ||
					_refName.equals("7.2.x"))) {

				return true;
			}
			else if (isSubrepo()) {
				if (_portalRefName == null) {
					return false;
				}
			}
			else {
				if (_repositoryName.endsWith("-ee") ||
					_repositoryName.endsWith("-private")) {

					if (!_refName.startsWith("ee-") &&
						!_refName.endsWith("-private")) {

						return false;
					}
				}
				else {
					if (_refName.startsWith("ee-") ||
						_refName.endsWith("-private")) {

						return false;
					}
				}
			}

			return true;
		}

		public boolean isWhitelistedOwner() {
			/*if (_ownerName.equals("brianchandotcom") ||
				_ownerName.equals("liferay-continuous-integration") ||
				_ownerName.equals("liferay") ||
				_ownerName.equals("pyoo47") ||
				_ownerName.equals("shuyangzhou") ||
				_ownerName.equals("stsquared99")) {

				return true;
			}

			return false;*/

			return true;
		}

		public boolean isWhitelistedRepository() {
			if (_repositoryName.equals("liferay-jenkins-ee") ||
				_repositoryName.equals("liferay-plugins") ||
				_repositoryName.equals("liferay-plugins-ee") ||
				_repositoryName.equals("liferay-portal") ||
				_repositoryName.equals("liferay-portal-ee") ||
				_repositoryName.startsWith("com-liferay-")) {

				return true;
			}

			return false;
		}

		public void setCITestSuite(String ciTestSuite) {
			_ciTestSuite = ciTestSuite;
		}

		public void setRefSHA(String refSHA) {
			_refSHA = refSHA;
		}

		public void setTesterName(String testerName) {
			_testerName = testerName;
		}

		private boolean _isValidK8sPullRequestSender() {
			String validK8sPullRequestSenderNames =
				_jenkinsBuildProperties.getProperty(
					"github.webhook.pullrequest.sender.names", "");

			if (validK8sPullRequestSenderNames.isEmpty()) {
				return true;
			}

			for (String validK8sPullRequestSenderName :
				validK8sPullRequestSenderNames.split("\\s*,\\s*")) {

				if (validK8sPullRequestSenderName.equals(_senderName)) {
					return true;
				}
			}

			return false;
		}

		private boolean _isValidK8sPullRequestTestSuite() {
			if (_jenkinsBuildProperties.containsKey(
				"github.webhook.pullrequest.frequency[" + _ciTestSuite +
					"]")) {

				return true;
			}

			return false;
		}

		private void _updateInvocationStatus(String targetURL)
			throws Exception {

			String command = "ci:test";
			String context =_ciTestSuite;

			if (!context.equals("default")) {
				command = "ci:test:" + context;

				context = "liferay/ci:test:" + context;
			}

			String repositoryName = getRepositoryName();

			if (repositoryName.startsWith("com-liferay-")) {
				return;
			}

			JSONObject jsonObject = new JSONObject();

			jsonObject.put("context", context);
			jsonObject.put("target_url", targetURL);
			jsonObject.put(
				"description", "\"" + command + "\" was invoked on Jenkins.");
			jsonObject.put("state", "pending");

			processURL(getStatusesURL(), jsonObject.toString());
		}

		private String[] _allowedSenderNames = {};
		private Properties _ciProperties;
		private String _ciTestSuite = "relevant";
		private List<Commit> _commits;
		private String _commonParentSHA;
		private List<JSONObject> _fileJSONObjects;
		private String[] _jiraProjectKeys = {};
		private int _number;
		private String _originName;
		private String _ownerName;
		private String _portalRefName;
		private JSONObject _pullRequestJSONObject;
		private String _refName;
		private String _refSHA;
		private String _repositoryName;
		private String _senderName;
		private String _senderRefName;
		private String _senderRefSHA;
		private String _testerName;
		private String _url;

	}

}