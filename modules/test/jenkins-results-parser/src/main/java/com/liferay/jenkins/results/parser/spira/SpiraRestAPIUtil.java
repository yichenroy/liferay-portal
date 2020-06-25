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

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class SpiraRestAPIUtil {

	protected static String getDateString(long timestamp) {
		Date date = new Date(timestamp);

		return _UtcIso8601SimpleDateFormat.format(date);
	}

	protected static String request(
			String urlPath, Map<String, String> urlParameters,
			Map<String, String> urlPathReplacements,
			HttpRequestMethod httpRequestMethod, String requestData)
		throws IOException {

		String spiraRestAPIURL =
			SpiraArtifact.SPIRA_BASE_URL + "services/v6_0/RestService.svc";

		if (urlPath.contains("/test-sets/search")) {
			spiraRestAPIURL = spiraRestAPIURL.replace("v6_0", "v5_0");
		}

		return JenkinsResultsParserUtil.toString(
			JenkinsResultsParserUtil.combine(
				spiraRestAPIURL,
				_applyURLPathReplacements(urlPath, urlPathReplacements),
				_toURLParametersString(urlParameters)),
			false, httpRequestMethod, requestData);
	}

	protected static JSONArray requestJSONArray(
			String urlPath, Map<String, String> urlParameters,
			Map<String, String> urlPathReplacements,
			HttpRequestMethod httpRequestMethod, String requestData)
		throws IOException {

		return JenkinsResultsParserUtil.createJSONArray(
			request(
				urlPath, urlParameters, urlPathReplacements, httpRequestMethod,
				requestData));
	}

	protected static JSONObject requestJSONObject(
			String urlPath, Map<String, String> urlParameters,
			Map<String, String> urlPathReplacements,
			HttpRequestMethod httpRequestMethod, String requestData)
		throws IOException {

		return JenkinsResultsParserUtil.createJSONObject(
			request(
				urlPath, urlParameters, urlPathReplacements, httpRequestMethod,
				requestData));
	}

	private static String _applyURLPathReplacements(
		String urlPath, Map<String, String> urlPathReplacements) {

		if (urlPath == null) {
			return "";
		}

		if (urlPathReplacements != null) {
			for (Map.Entry<String, String> urlPathReplacement :
					urlPathReplacements.entrySet()) {

				urlPath = urlPath.replace(
					"{" + urlPathReplacement.getKey() + "}",
					urlPathReplacement.getValue());
			}
		}

		if (urlPath.matches(".*\\{[^\\}]+\\}.*")) {
			throw new RuntimeException("Invalid url path " + urlPath);
		}

		if (urlPath.contains("?")) {
			throw new RuntimeException("Invalid url path " + urlPath);
		}

		if (!urlPath.startsWith("/")) {
			urlPath = "/" + urlPath;
		}

		return urlPath;
	}

	private static String _toURLParametersString(
		Map<String, String> urlParameters) {

		if ((urlParameters == null) || urlParameters.isEmpty()) {
			return "";
		}

		List<String> urlParameterStrings = new ArrayList<>();

		for (Map.Entry<String, String> urlParameter :
				urlParameters.entrySet()) {

			urlParameterStrings.add(
				urlParameter.getKey() + "=" + urlParameter.getValue());
		}

		return "?" + JenkinsResultsParserUtil.join("&", urlParameterStrings);
	}

	private static final SimpleDateFormat _UtcIso8601SimpleDateFormat;

	static {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss");

		simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

		_UtcIso8601SimpleDateFormat = simpleDateFormat;
	}

}