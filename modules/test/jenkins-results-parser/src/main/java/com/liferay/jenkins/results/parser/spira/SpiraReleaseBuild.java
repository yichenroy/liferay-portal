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

package com.liferay.jenkins.results.parser.spira;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil.HttpRequestMethod;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class SpiraReleaseBuild extends BaseSpiraArtifact {

	public static SpiraReleaseBuild createSpiraReleaseBuild(
		SpiraProject spiraProject, SpiraRelease spiraRelease,
		String releaseBuildName, String releaseBuildDescription,
		Status releaseBuildStatus, long releaseBuildStartTime) {

		List<SpiraReleaseBuild> spiraReleaseBuilds = getSpiraReleaseBuilds(
			spiraProject, spiraRelease,
			new SearchQuery.SearchParameter("ProjectId", spiraProject.getID()),
			new SearchQuery.SearchParameter("ReleaseId", spiraRelease.getID()),
			new SearchQuery.SearchParameter("Name", releaseBuildName));

		if (!spiraReleaseBuilds.isEmpty()) {
			return spiraReleaseBuilds.get(0);
		}

		Map<String, String> urlPathReplacements = new HashMap<>();

		urlPathReplacements.put(
			"project_id", String.valueOf(spiraProject.getID()));
		urlPathReplacements.put(
			"release_id", String.valueOf(spiraRelease.getID()));

		JSONObject requestJSONObject = new JSONObject();

		requestJSONObject.put(SpiraProject.KEY_ID, spiraProject.getID());
		requestJSONObject.put(SpiraRelease.KEY_ID, spiraRelease.getID());
		requestJSONObject.put("BuildStatusId", releaseBuildStatus.getID());
		requestJSONObject.put(
			"CreationDate",
			SpiraRestAPIUtil.toDateString(releaseBuildStartTime));
		requestJSONObject.put("Name", releaseBuildName);

		if (releaseBuildDescription != null) {
			requestJSONObject.put("Description", releaseBuildDescription);
		}

		try {
			JSONObject responseJSONObject = SpiraRestAPIUtil.requestJSONObject(
				"projects/{project_id}/releases/{release_id}/builds", null,
				urlPathReplacements, HttpRequestMethod.POST,
				requestJSONObject.toString());

			return spiraRelease.getSpiraReleaseBuildByID(
				responseJSONObject.getInt(KEY_ID));
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	public static List<SpiraReleaseBuild> getSpiraReleaseBuilds(
		final SpiraProject spiraProject, final SpiraRelease spiraRelease,
		final SearchQuery.SearchParameter... searchParameters) {

		return getSpiraArtifacts(
			SpiraReleaseBuild.class,
			new Supplier<List<JSONObject>>() {

				@Override
				public List<JSONObject> get() {
					return _requestSpiraReleaseBuilds(
						spiraProject, spiraRelease, searchParameters);
				}

			},
			new Function<JSONObject, SpiraReleaseBuild>() {

				@Override
				public SpiraReleaseBuild apply(JSONObject jsonObject) {
					return new SpiraReleaseBuild(jsonObject);
				}

			},
			searchParameters);
	}

	public String getBuildStatusName() {
		return jsonObject.getString("BuildStatusName");
	}

	public String getCreationDate() {
		return jsonObject.getString("CreationDate");
	}

	public List<SpiraTestCaseRun> getSpiraTestCaseRuns() {
		SearchQuery.SearchParameter searchParameter =
			new SearchQuery.SearchParameter(KEY_ID, getID());

		return SpiraTestCaseRun.getSpiraTestCaseRuns(
			getSpiraProject(), searchParameter);
	}

	@Override
	public String getURL() {
		SpiraProject spiraProject = getSpiraProject();

		return JenkinsResultsParserUtil.combine(
			SPIRA_BASE_URL, String.valueOf(spiraProject.getID()), "/Build/",
			String.valueOf(getID()), ".aspx");
	}

	public static enum Status {

		ABORTED(4), FAILED(1), SUCCEEDED(2), UNSTABLE(3);

		public Integer getID() {
			return _id;
		}

		private Status(Integer id) {
			_id = id;
		}

		private final Integer _id;

	}

	protected static final String ARTIFACT_TYPE_NAME = "build";

	protected static final String KEY_ID = "BuildId";

	private static List<JSONObject> _requestSpiraReleaseBuilds(
		SpiraProject spiraProject, SpiraRelease spiraRelease,
		SearchQuery.SearchParameter... searchParameters) {

		Map<String, String> urlParameters = new HashMap<>();

		urlParameters.put("number_of_rows", String.valueOf(15000));
		urlParameters.put("sort_by", "BuildId DESC");
		urlParameters.put("starting_row", String.valueOf(1));

		Map<String, String> urlPathReplacements = new HashMap<>();

		urlPathReplacements.put(
			"project_id", String.valueOf(spiraProject.getID()));
		urlPathReplacements.put(
			"release_id", String.valueOf(spiraRelease.getID()));

		JSONArray requestJSONArray = new JSONArray();

		for (SearchQuery.SearchParameter searchParameter : searchParameters) {
			requestJSONArray.put(searchParameter.toFilterJSONObject());
		}

		List<JSONObject> spiraReleaseBuilds = new ArrayList<>();

		try {
			JSONArray responseJSONArray = SpiraRestAPIUtil.requestJSONArray(
				"projects/{project_id}/releases/{release_id}/builds",
				urlParameters, urlPathReplacements, HttpRequestMethod.POST,
				requestJSONArray.toString());

			for (int i = 0; i < responseJSONArray.length(); i++) {
				spiraReleaseBuilds.add(responseJSONArray.getJSONObject(i));
			}

			return spiraReleaseBuilds;
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private SpiraReleaseBuild(JSONObject jsonObject) {
		super(jsonObject);

		cacheSpiraArtifact(SpiraReleaseBuild.class, this);
	}

}