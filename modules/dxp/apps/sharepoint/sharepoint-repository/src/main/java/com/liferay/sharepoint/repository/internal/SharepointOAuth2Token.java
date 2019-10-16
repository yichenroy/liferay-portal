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

package com.liferay.sharepoint.repository.internal;

import com.liferay.document.library.repository.authorization.oauth2.OAuth2AuthorizationException;
import com.liferay.document.library.repository.authorization.oauth2.Token;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.sharepoint.oauth2.model.SharepointOAuth2TokenEntry;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import java.util.Date;

/**
 * @author Adolfo Pérez
 */
public class SharepointOAuth2Token implements Token {

	public static final Token fromJSONString(String json)
		throws JSONException, OAuth2AuthorizationException {

		return fromJSONString(json, null);
	}

	public static final Token fromJSONString(String json, Token token)
		throws JSONException, OAuth2AuthorizationException {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(json);

		if (jsonObject.has("error")) {
			throw OAuth2AuthorizationException.getErrorException(
				jsonObject.getString("error"),
				jsonObject.getString("description"));
		}

		String accessToken = jsonObject.getString("access_token");

		if (Validator.isNull(accessToken)) {
			throw new IllegalArgumentException(
				String.format("Invalid JSON token: %s", json));
		}

		String refreshToken = jsonObject.getString("refresh_token");

		if ((token != null) && Validator.isNull(refreshToken)) {
			refreshToken = token.getRefreshToken();
		}

		Instant instant = Instant.now();

		long expiresIn = jsonObject.getLong("expires_in");

		Date expirationDate = Date.from(
			instant.plus(expiresIn, ChronoUnit.SECONDS));

		return new SharepointOAuth2Token(
			accessToken, refreshToken, expirationDate);
	}

	public static final Token fromModel(
		SharepointOAuth2TokenEntry sharepointOAuth2TokenEntry) {

		if (sharepointOAuth2TokenEntry == null) {
			return null;
		}

		return new SharepointOAuth2Token(
			sharepointOAuth2TokenEntry.getAccessToken(),
			sharepointOAuth2TokenEntry.getRefreshToken(),
			sharepointOAuth2TokenEntry.getExpirationDate());
	}

	@Override
	public String getAccessToken() {
		return _accessToken;
	}

	@Override
	public Date getExpirationDate() {
		return _expirationDate;
	}

	@Override
	public String getRefreshToken() {
		return _refreshToken;
	}

	@Override
	public boolean isExpired() {
		return _expirationDate.before(DateUtil.newDate());
	}

	private SharepointOAuth2Token(
		String accessToken, String refreshToken, Date expirationDate) {

		_accessToken = accessToken;
		_refreshToken = refreshToken;
		_expirationDate = expirationDate;
	}

	private final String _accessToken;
	private final Date _expirationDate;
	private final String _refreshToken;

}