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

package com.liferay.oauth2.provider.rest.internal.endpoint.liferay;

import com.liferay.oauth2.provider.configuration.OAuth2ProviderConfiguration;
import com.liferay.oauth2.provider.constants.GrantType;
import com.liferay.oauth2.provider.constants.OAuth2ProviderConstants;
import com.liferay.oauth2.provider.model.OAuth2Application;
import com.liferay.oauth2.provider.model.OAuth2ApplicationScopeAliases;
import com.liferay.oauth2.provider.model.OAuth2Authorization;
import com.liferay.oauth2.provider.rest.internal.endpoint.authorize.configuration.OAuth2AuthorizationFlowConfiguration;
import com.liferay.oauth2.provider.rest.internal.endpoint.constants.OAuth2ProviderRestEndpointConstants;
import com.liferay.oauth2.provider.rest.spi.bearer.token.provider.BearerTokenProvider;
import com.liferay.oauth2.provider.rest.spi.bearer.token.provider.BearerTokenProviderAccessor;
import com.liferay.oauth2.provider.scope.liferay.LiferayOAuth2Scope;
import com.liferay.oauth2.provider.scope.liferay.ScopeLocator;
import com.liferay.oauth2.provider.scope.liferay.ScopedServiceTrackerMapFactory;
import com.liferay.oauth2.provider.service.OAuth2ApplicationLocalService;
import com.liferay.oauth2.provider.service.OAuth2ApplicationScopeAliasesLocalService;
import com.liferay.oauth2.provider.service.OAuth2AuthorizationLocalService;
import com.liferay.oauth2.provider.service.OAuth2ScopeGrantLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.cxf.jaxrs.ext.MessageContext;
import org.apache.cxf.rs.security.oauth2.common.AccessTokenRegistration;
import org.apache.cxf.rs.security.oauth2.common.Client;
import org.apache.cxf.rs.security.oauth2.common.OAuthPermission;
import org.apache.cxf.rs.security.oauth2.common.ServerAccessToken;
import org.apache.cxf.rs.security.oauth2.common.UserSubject;
import org.apache.cxf.rs.security.oauth2.grants.code.AbstractAuthorizationCodeDataProvider;
import org.apache.cxf.rs.security.oauth2.grants.code.AuthorizationCodeRegistration;
import org.apache.cxf.rs.security.oauth2.grants.code.ServerAuthorizationCodeGrant;
import org.apache.cxf.rs.security.oauth2.provider.OAuthServiceException;
import org.apache.cxf.rs.security.oauth2.tokens.bearer.BearerAccessToken;
import org.apache.cxf.rs.security.oauth2.tokens.refresh.RefreshToken;
import org.apache.cxf.rs.security.oauth2.utils.OAuthConstants;
import org.apache.cxf.rs.security.oauth2.utils.OAuthUtils;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Carlos Sierra Andrés
 * @author Tomas Polesovsky
 */
@Component(
	configurationPid = {
		"com.liferay.oauth2.provider.configuration.OAuth2ProviderConfiguration",
		"com.liferay.oauth2.provider.rest.internal.endpoint.authorize.configuration.OAuth2AuthorizationFlowConfiguration"
	},
	service = LiferayOAuthDataProvider.class
)
public class LiferayOAuthDataProvider
	extends AbstractAuthorizationCodeDataProvider {

	public LiferayOAuthDataProvider() {
		setInvisibleToClientScopes(
			Collections.singletonList(OAuthConstants.REFRESH_TOKEN_SCOPE));
	}

	@Override
	public List<OAuthPermission> convertScopeToPermissions(
		Client client, List<String> requestedScopes) {

		List<OAuthPermission> oAuth2Permissions = new ArrayList<>();

		List<String> invisibleToClientScopes = getInvisibleToClientScopes();

		for (String requestedScope : requestedScopes) {
			OAuthPermission oAuthPermission = new OAuthPermission(
				requestedScope);

			if (invisibleToClientScopes.contains(
					oAuthPermission.getPermission())) {

				oAuthPermission.setInvisibleToClient(true);
			}

			oAuth2Permissions.add(oAuthPermission);
		}

		return oAuth2Permissions;
	}

	@Override
	public ServerAccessToken createAccessToken(
			AccessTokenRegistration accessTokenRegistration)
		throws OAuthServiceException {

		List<String> approvedScope = new ArrayList<>(
			accessTokenRegistration.getRequestedScope());

		if (approvedScope.isEmpty()) {
			Client client = accessTokenRegistration.getClient();

			approvedScope.addAll(client.getRegisteredScopes());
		}

		accessTokenRegistration.setApprovedScope(approvedScope);

		if (!OAuthConstants.CLIENT_CREDENTIALS_GRANT.equals(
				accessTokenRegistration.getGrantType())) {

			approvedScope.add(OAuthConstants.REFRESH_TOKEN_SCOPE);
		}

		return super.createAccessToken(accessTokenRegistration);
	}

	@Override
	public ServerAuthorizationCodeGrant createCodeGrant(
			AuthorizationCodeRegistration authorizationCodeRegistration)
		throws OAuthServiceException {

		ServerAuthorizationCodeGrant serverAuthorizationCodeGrant =
			super.createCodeGrant(authorizationCodeRegistration);

		serverAuthorizationCodeGrant.setRequestedScopes(
			authorizationCodeRegistration.getRequestedScope());

		_codeGrantsPortalCache.put(
			serverAuthorizationCodeGrant.getCode(),
			serverAuthorizationCodeGrant,
			Math.toIntExact(serverAuthorizationCodeGrant.getExpiresIn()));

		return serverAuthorizationCodeGrant;
	}

	@Override
	public void doRevokeAccessToken(ServerAccessToken accessToken) {
		OAuth2Authorization oAuth2Authorization =
			_oAuth2AuthorizationLocalService.
				fetchOAuth2AuthorizationByAccessTokenContent(
					accessToken.getTokenKey());

		if (oAuth2Authorization == null) {
			return;
		}

		oAuth2Authorization.setAccessTokenContent(
			OAuth2ProviderConstants.EXPIRED_TOKEN);

		_oAuth2AuthorizationLocalService.updateOAuth2Authorization(
			oAuth2Authorization);
	}

	@Override
	public void doRevokeRefreshToken(RefreshToken refreshToken) {
		OAuth2Authorization oAuth2Authorization =
			_oAuth2AuthorizationLocalService.
				fetchOAuth2AuthorizationByRefreshTokenContent(
					refreshToken.getTokenKey());

		if (oAuth2Authorization == null) {
			return;
		}

		oAuth2Authorization.setRefreshTokenContent(
			OAuth2ProviderConstants.EXPIRED_TOKEN);

		_oAuth2AuthorizationLocalService.updateOAuth2Authorization(
			oAuth2Authorization);
	}

	public BearerTokenProvider.AccessToken fromCXFAccessToken(
		ServerAccessToken serverAccessToken) {

		OAuth2Application oAuth2Application = resolveOAuth2Application(
			serverAccessToken.getClient());
		UserSubject userSubject = serverAccessToken.getSubject();

		return new BearerTokenProvider.AccessToken(
			oAuth2Application, serverAccessToken.getAudiences(),
			serverAccessToken.getClientCodeVerifier(),
			serverAccessToken.getExpiresIn(),
			serverAccessToken.getExtraProperties(),
			serverAccessToken.getGrantCode(), serverAccessToken.getGrantType(),
			serverAccessToken.getIssuedAt(), serverAccessToken.getIssuer(),
			serverAccessToken.getNonce(), serverAccessToken.getParameters(),
			serverAccessToken.getRefreshToken(),
			serverAccessToken.getResponseType(),
			OAuthUtils.convertPermissionsToScopeList(
				serverAccessToken.getScopes()),
			serverAccessToken.getTokenKey(), serverAccessToken.getTokenType(),
			GetterUtil.getLong(userSubject.getId()), userSubject.getLogin());
	}

	public BearerTokenProvider.RefreshToken fromCXFRefreshToken(
		RefreshToken refreshToken) {

		OAuth2Application oAuth2Application = resolveOAuth2Application(
			refreshToken.getClient());
		UserSubject userSubject = refreshToken.getSubject();

		return new BearerTokenProvider.RefreshToken(
			oAuth2Application, refreshToken.getAudiences(),
			refreshToken.getClientCodeVerifier(), refreshToken.getExpiresIn(),
			refreshToken.getGrantType(), refreshToken.getIssuedAt(),
			OAuthUtils.convertPermissionsToScopeList(refreshToken.getScopes()),
			refreshToken.getTokenKey(), refreshToken.getTokenType(),
			GetterUtil.getLong(userSubject.getId()), userSubject.getLogin());
	}

	@Override
	public ServerAccessToken getAccessToken(String accessToken)
		throws OAuthServiceException {

		if (Validator.isBlank(accessToken)) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Remote client ", _getRemoteIP(),
						" tried to use empty OAuth 2 access token"));
			}

			return null;
		}

		OAuth2Authorization oAuth2Authorization =
			_oAuth2AuthorizationLocalService.
				fetchOAuth2AuthorizationByAccessTokenContent(accessToken);

		if (oAuth2Authorization == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Remote client ", _getRemoteIP(),
						" used unknown OAuth 2 token. Repeating report may be ",
						"a sign of a brute-force attack."));
			}

			return null;
		}

		if (OAuth2ProviderConstants.EXPIRED_TOKEN.equals(
				oAuth2Authorization.getAccessTokenContent())) {

			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"Remote client ", _getRemoteIP(),
						" tried to use expired or revoked OAuth 2 token for ",
						"Liferay OAuth 2 application ",
						oAuth2Authorization.getOAuth2ApplicationId(),
						" and user ", oAuth2Authorization.getUserId()));
			}

			return null;
		}

		try {
			return populateAccessToken(oAuth2Authorization);
		}
		catch (PortalException pe) {
			_log.error("Unable to populate access token", pe);

			throw new OAuthServiceException(pe);
		}
	}

	@Override
	public List<ServerAccessToken> getAccessTokens(
			Client client, UserSubject subject)
		throws OAuthServiceException {

		throw new UnsupportedOperationException();
	}

	public BearerTokenProvider getBearerTokenProvider(
		long companyId, String clientId) {

		return _bearerTokenProviderAccessor.getBearerTokenProvider(
			companyId, clientId);
	}

	@Override
	public Client getClient(String clientId) {
		long companyId = CompanyThreadLocal.getCompanyId();

		OAuth2Application oAuth2Application =
			_oAuth2ApplicationLocalService.fetchOAuth2Application(
				companyId, clientId);

		if (oAuth2Application == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Remote client ", _getRemoteIP(),
						" tried to use a nonexistent OAuth 2 client ID ",
						clientId));
			}

			return null;
		}

		MessageContext messageContext = getMessageContext();

		messageContext.put(OAuthConstants.CLIENT_ID, clientId);

		return populateClient(oAuth2Application);
	}

	@Override
	public List<Client> getClients(UserSubject resourceOwner) {
		throw new UnsupportedOperationException();
	}

	public ServerAuthorizationCodeGrant getCodeGrant(String code) {
		if (code == null) {
			return null;
		}

		return _codeGrantsPortalCache.get(code);
	}

	@Override
	public List<ServerAuthorizationCodeGrant> getCodeGrants(
			Client client, UserSubject subject)
		throws OAuthServiceException {

		List<ServerAuthorizationCodeGrant> serverAuthorizationCodeGrants =
			new ArrayList<>();

		List<String> keys = _codeGrantsPortalCache.getKeys();

		for (String key : keys) {
			ServerAuthorizationCodeGrant serverAuthorizationCodeGrant =
				_codeGrantsPortalCache.get(key);

			if (serverAuthorizationCodeGrant == null) {
				continue;
			}

			if (client.equals(serverAuthorizationCodeGrant.getClient()) &&
				subject.equals(serverAuthorizationCodeGrant.getSubject())) {

				serverAuthorizationCodeGrants.add(serverAuthorizationCodeGrant);
			}
		}

		return serverAuthorizationCodeGrants;
	}

	@Override
	public RefreshToken getRefreshToken(String refreshTokenKey) {
		if (Validator.isBlank(refreshTokenKey)) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Remote client ", _getRemoteIP(),
						" tried to use empty OAuth 2 refresh token"));
			}

			return null;
		}

		try {
			OAuth2Authorization oAuth2Authorization =
				_oAuth2AuthorizationLocalService.
					fetchOAuth2AuthorizationByRefreshTokenContent(
						refreshTokenKey);

			if (oAuth2Authorization == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						StringBundler.concat(
							"Remote client ", _getRemoteIP(),
							" used unknown OAuth 2 refresh token. Repeating ",
							"report may be a sign of a brute force attack."));
				}

				return null;
			}

			if (OAuth2ProviderConstants.EXPIRED_TOKEN.equals(
					oAuth2Authorization.getRefreshTokenContent())) {

				if (_log.isDebugEnabled()) {
					_log.debug(
						StringBundler.concat(
							"Remote client ", _getRemoteIP(),
							" tried to use expired or revoked OAuth 2 refresh ",
							"token for Liferay OAuth 2 application ",
							oAuth2Authorization.getOAuth2ApplicationId(),
							" and user ", oAuth2Authorization.getUserId()));
				}

				return null;
			}

			OAuth2Application oAuth2Application =
				_oAuth2ApplicationLocalService.getOAuth2Application(
					oAuth2Authorization.getOAuth2ApplicationId());

			long expires = toCXFTime(
				oAuth2Authorization.getRefreshTokenExpirationDate());
			long issuedAt = toCXFTime(
				oAuth2Authorization.getRefreshTokenCreateDate());

			long lifetime = expires - issuedAt;

			RefreshToken refreshToken = new RefreshToken(
				populateClient(oAuth2Application), refreshTokenKey, lifetime,
				issuedAt);

			refreshToken.setSubject(
				populateUserSubject(
					oAuth2Authorization.getCompanyId(),
					oAuth2Authorization.getUserId(),
					oAuth2Authorization.getUserName()));

			List<String> accessTokens = Collections.singletonList(
				oAuth2Authorization.getAccessTokenContent());

			refreshToken.setAccessTokens(accessTokens);

			OAuth2ApplicationScopeAliases oAuth2ApplicationScopeAliases =
				_oAuth2ApplicationScopeAliasesLocalService.
					getOAuth2ApplicationScopeAliases(
						oAuth2Authorization.
							getOAuth2ApplicationScopeAliasesId());

			refreshToken.setScopes(
				convertScopeToPermissions(
					refreshToken.getClient(),
					oAuth2ApplicationScopeAliases.getScopeAliasesList()));

			Map<String, String> extraProperties =
				refreshToken.getExtraProperties();

			extraProperties.put(
				OAuth2ProviderRestEndpointConstants.PROPERTY_KEY_COMPANY_ID,
				String.valueOf(oAuth2Authorization.getCompanyId()));

			return refreshToken;
		}
		catch (PortalException pe) {
			throw new OAuthServiceException(pe);
		}
	}

	@Override
	public List<RefreshToken> getRefreshTokens(
			Client client, UserSubject subject)
		throws OAuthServiceException {

		return null;
	}

	@Override
	public ServerAccessToken refreshAccessToken(
			Client client, String refreshTokenKey,
			List<String> restrictedScopes)
		throws OAuthServiceException {

		RefreshToken oldRefreshToken = getRefreshToken(refreshTokenKey);

		if (oldRefreshToken == null) {
			throw new OAuthServiceException(OAuthConstants.ACCESS_DENIED);
		}

		if (OAuthUtils.isExpired(
				oldRefreshToken.getIssuedAt(),
				oldRefreshToken.getExpiresIn())) {

			doRevokeRefreshToken(oldRefreshToken);

			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"Remote client ", _getRemoteIP(),
						" tried to use an expired OAuth 2 refresh token for ",
						"OAuth 2 client ID ", client.getClientId()));
			}

			throw new OAuthServiceException(OAuthConstants.ACCESS_DENIED);
		}

		OAuth2Application oAuth2Application = resolveOAuth2Application(client);

		BearerTokenProvider bearerTokenProvider = getBearerTokenProvider(
			oAuth2Application.getCompanyId(), oAuth2Application.getClientId());

		if (!bearerTokenProvider.isValid(
				fromCXFRefreshToken(oldRefreshToken))) {

			doRevokeRefreshToken(oldRefreshToken);

			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Remote client ", _getRemoteIP(),
						" tried to use an invalid OAuth 2 token for OAuth 2 ",
						"client ID ", client.getClientId()));
			}

			throw new OAuthServiceException(OAuthConstants.ACCESS_DENIED);
		}

		OAuth2Authorization oAuth2Authorization =
			_oAuth2AuthorizationLocalService.
				fetchOAuth2AuthorizationByRefreshTokenContent(refreshTokenKey);

		if (oAuth2Authorization == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Remote client ", _getRemoteIP(),
						" used unknown OAuth 2 refresh token for OAuth 2 ",
						"client ID ", client.getClientId(),
						". Repeating report may be a sign of a brute force ",
						"attack."));
			}

			throw new OAuthServiceException(OAuthConstants.ACCESS_DENIED);
		}

		ServerAccessToken accessToken = doRefreshAccessToken(
			client, oldRefreshToken, Collections.emptyList());

		accessToken.setRefreshToken(oldRefreshToken.getTokenKey());

		RefreshToken newRefreshToken = doCreateNewRefreshToken(accessToken);

		List<String> accessTokens = newRefreshToken.getAccessTokens();

		accessTokens.add(accessToken.getTokenKey());

		try {
			_invokeTransactionally(
				() -> {
					saveAccessToken(accessToken);
					saveRefreshToken(newRefreshToken);
				});
		}
		catch (Throwable throwable) {
			throw new OAuthServiceException(throwable);
		}

		accessToken.setRefreshToken(newRefreshToken.getTokenKey());

		return accessToken;
	}

	@Override
	public ServerAuthorizationCodeGrant removeCodeGrant(String code)
		throws OAuthServiceException {

		if (code == null) {
			return null;
		}

		ServerAuthorizationCodeGrant serverAuthorizationCodeGrant =
			_codeGrantsPortalCache.get(code);

		_codeGrantsPortalCache.remove(code);

		return serverAuthorizationCodeGrant;
	}

	public OAuth2Application resolveOAuth2Application(Client client) {
		Map<String, String> properties = client.getProperties();

		long companyId = GetterUtil.getLong(
			properties.get(
				OAuth2ProviderRestEndpointConstants.PROPERTY_KEY_COMPANY_ID));

		OAuth2Application oAuth2Application =
			_oAuth2ApplicationLocalService.fetchOAuth2Application(
				companyId, client.getClientId());

		if (oAuth2Application == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Remote client ", _getRemoteIP(),
						" tried to use a nonexistent OAuth 2 client ID ",
						client.getClientId()));
			}

			return null;
		}

		return oAuth2Application;
	}

	@Override
	public void setClient(Client client) {
		throw new UnsupportedOperationException();
	}

	@Activate
	@SuppressWarnings("unchecked")
	protected void activate(Map<String, Object> properties) {
		_codeGrantsPortalCache =
			(PortalCache<String, ServerAuthorizationCodeGrant>)
				_multiVMPool.getPortalCache("oauth2-provider-code-grants");
		_oAuth2AuthorizeFlowConfiguration = ConfigurableUtil.createConfigurable(
			OAuth2AuthorizationFlowConfiguration.class, properties);
		_oAuth2ProviderConfiguration = ConfigurableUtil.createConfigurable(
			OAuth2ProviderConfiguration.class, properties);

		setGrantLifetime(
			_oAuth2AuthorizeFlowConfiguration.authorizationCodeGrantTTL());
	}

	@Override
	protected ServerAccessToken doCreateAccessToken(
		AccessTokenRegistration accessTokenRegistration) {

		ServerAccessToken serverAccessToken = super.doCreateAccessToken(
			accessTokenRegistration);

		BearerTokenProvider.AccessToken accessToken = fromCXFAccessToken(
			serverAccessToken);

		OAuth2Application oAuth2Application =
			accessToken.getOAuth2Application();

		BearerTokenProvider bearerTokenProvider = getBearerTokenProvider(
			oAuth2Application.getCompanyId(), oAuth2Application.getClientId());

		bearerTokenProvider.onBeforeCreate(accessToken);

		serverAccessToken.setAudiences(accessToken.getAudiences());
		serverAccessToken.setClientCodeVerifier(
			accessToken.getClientCodeVerifier());
		serverAccessToken.setExpiresIn(accessToken.getExpiresIn());
		serverAccessToken.setExtraProperties(accessToken.getExtraProperties());
		serverAccessToken.setGrantCode(accessToken.getGrantCode());
		serverAccessToken.setGrantType(accessToken.getGrantType());
		serverAccessToken.setIssuedAt(accessToken.getIssuedAt());
		serverAccessToken.setIssuer(accessToken.getIssuer());
		serverAccessToken.setNonce(accessToken.getNonce());
		serverAccessToken.setParameters(accessToken.getParameters());
		serverAccessToken.setRefreshToken(accessToken.getRefreshToken());
		serverAccessToken.setResponseType(accessToken.getResponseType());
		serverAccessToken.setScopes(
			convertScopeToPermissions(
				serverAccessToken.getClient(), accessToken.getScopes()));
		serverAccessToken.setTokenKey(accessToken.getTokenKey());
		serverAccessToken.setTokenType(accessToken.getTokenType());

		UserSubject userSubject = serverAccessToken.getSubject();

		userSubject.setId(String.valueOf(accessToken.getUserId()));
		userSubject.setLogin(accessToken.getUserName());

		return serverAccessToken;
	}

	@Override
	protected RefreshToken doCreateNewRefreshToken(
		ServerAccessToken serverAccessToken) {

		RefreshToken cxfRefreshToken = super.doCreateNewRefreshToken(
			serverAccessToken);

		BearerTokenProvider.RefreshToken refreshToken = fromCXFRefreshToken(
			cxfRefreshToken);

		OAuth2Application oAuth2Application =
			refreshToken.getOAuth2Application();

		BearerTokenProvider bearerTokenProvider = getBearerTokenProvider(
			oAuth2Application.getCompanyId(), oAuth2Application.getClientId());

		bearerTokenProvider.onBeforeCreate(refreshToken);

		cxfRefreshToken.setAudiences(refreshToken.getAudiences());
		cxfRefreshToken.setClientCodeVerifier(
			refreshToken.getClientCodeVerifier());
		cxfRefreshToken.setExpiresIn(refreshToken.getExpiresIn());
		cxfRefreshToken.setGrantType(refreshToken.getGrantType());
		cxfRefreshToken.setIssuedAt(refreshToken.getIssuedAt());
		cxfRefreshToken.setScopes(
			convertScopeToPermissions(
				serverAccessToken.getClient(), refreshToken.getScopes()));
		cxfRefreshToken.setTokenKey(refreshToken.getTokenKey());
		cxfRefreshToken.setTokenType(refreshToken.getTokenType());

		UserSubject userSubject = cxfRefreshToken.getSubject();

		userSubject.setId(String.valueOf(refreshToken.getUserId()));
		userSubject.setLogin(refreshToken.getUserName());

		return cxfRefreshToken;
	}

	@Override
	protected ServerAccessToken doRefreshAccessToken(
		Client client, RefreshToken oldRefreshToken,
		List<String> restrictedScopes) {

		ServerAccessToken serverAccessToken = super.doRefreshAccessToken(
			client, oldRefreshToken, restrictedScopes);

		BearerTokenProvider.AccessToken accessToken = fromCXFAccessToken(
			serverAccessToken);

		OAuth2Application oAuth2Application =
			accessToken.getOAuth2Application();

		BearerTokenProvider bearerTokenProvider = getBearerTokenProvider(
			oAuth2Application.getCompanyId(), oAuth2Application.getClientId());

		bearerTokenProvider.onBeforeCreate(accessToken);

		serverAccessToken.setAudiences(accessToken.getAudiences());
		serverAccessToken.setClientCodeVerifier(
			accessToken.getClientCodeVerifier());
		serverAccessToken.setExpiresIn(accessToken.getExpiresIn());
		serverAccessToken.setExtraProperties(accessToken.getExtraProperties());
		serverAccessToken.setGrantCode(accessToken.getGrantCode());
		serverAccessToken.setGrantType(accessToken.getGrantType());
		serverAccessToken.setIssuedAt(accessToken.getIssuedAt());
		serverAccessToken.setIssuer(accessToken.getIssuer());
		serverAccessToken.setNonce(accessToken.getNonce());
		serverAccessToken.setParameters(accessToken.getParameters());
		serverAccessToken.setRefreshToken(accessToken.getRefreshToken());
		serverAccessToken.setResponseType(accessToken.getResponseType());
		serverAccessToken.setScopes(
			convertScopeToPermissions(
				serverAccessToken.getClient(), accessToken.getScopes()));
		serverAccessToken.setTokenKey(accessToken.getTokenKey());
		serverAccessToken.setTokenType(accessToken.getTokenType());

		UserSubject userSubject = serverAccessToken.getSubject();

		userSubject.setId(String.valueOf(accessToken.getUserId()));
		userSubject.setLogin(accessToken.getUserName());

		return serverAccessToken;
	}

	@Override
	protected void doRemoveClient(Client c) {
		throw new UnsupportedOperationException();
	}

	protected Date fromCXFTime(long issuedAt) {
		return new Date(issuedAt * 1000);
	}

	protected ServerAccessToken populateAccessToken(
			OAuth2Authorization oAuth2Authorization)
		throws PortalException {

		OAuth2Application oAuth2Application =
			_oAuth2ApplicationLocalService.fetchOAuth2Application(
				oAuth2Authorization.getOAuth2ApplicationId());

		if (oAuth2Application == null) {
			throw new SystemException(
				"No application found for authorization " +
					oAuth2Authorization);
		}

		Client client = getClient(oAuth2Application.getClientId());

		long expires = toCXFTime(
			oAuth2Authorization.getAccessTokenExpirationDate());
		long issuedAt = toCXFTime(
			oAuth2Authorization.getAccessTokenCreateDate());

		long lifetime = expires - issuedAt;

		ServerAccessToken serverAccessToken = new BearerAccessToken(
			client, oAuth2Authorization.getAccessTokenContent(), lifetime,
			issuedAt);

		serverAccessToken.setSubject(
			populateUserSubject(
				oAuth2Authorization.getCompanyId(),
				oAuth2Authorization.getUserId(),
				oAuth2Authorization.getUserName()));

		OAuth2ApplicationScopeAliases oAuth2ApplicationScopeAliases =
			_oAuth2ApplicationScopeAliasesLocalService.
				getOAuth2ApplicationScopeAliases(
					oAuth2Authorization.getOAuth2ApplicationScopeAliasesId());

		List<OAuthPermission> oAuth2Permissions = convertScopeToPermissions(
			client, oAuth2ApplicationScopeAliases.getScopeAliasesList());

		serverAccessToken.setScopes(oAuth2Permissions);

		Map<String, String> extraProperties =
			serverAccessToken.getExtraProperties();

		extraProperties.put(
			OAuth2ProviderRestEndpointConstants.PROPERTY_KEY_COMPANY_ID,
			String.valueOf(oAuth2Authorization.getCompanyId()));

		return serverAccessToken;
	}

	protected Client populateClient(OAuth2Application oAuth2Application) {
		String clientSecret = oAuth2Application.getClientSecret();

		if (Validator.isBlank(clientSecret)) {
			clientSecret = null;
		}

		Client client = new Client(
			oAuth2Application.getClientId(), clientSecret,
			!Validator.isBlank(clientSecret), oAuth2Application.getName(),
			oAuth2Application.getHomePageURL());

		List<String> clientGrantTypes = client.getAllowedGrantTypes();

		for (GrantType allowedGrantType :
				oAuth2Application.getAllowedGrantTypesList()) {

			if (_oAuth2ProviderConfiguration.allowAuthorizationCodeGrant() &&
				(allowedGrantType == GrantType.AUTHORIZATION_CODE)) {

				clientGrantTypes.add(OAuthConstants.AUTHORIZATION_CODE_GRANT);
			}
			else if (_oAuth2ProviderConfiguration.
						 allowAuthorizationCodePKCEGrant() &&
					 (allowedGrantType == GrantType.AUTHORIZATION_CODE_PKCE)) {

				clientGrantTypes.add(OAuthConstants.AUTHORIZATION_CODE_GRANT);
				clientGrantTypes.add(
					OAuth2ProviderRestEndpointConstants.
						AUTHORIZATION_CODE_PKCE_GRANT);
			}
			else if (_oAuth2ProviderConfiguration.
						 allowClientCredentialsGrant() &&
					 (allowedGrantType == GrantType.CLIENT_CREDENTIALS)) {

				clientGrantTypes.add(OAuthConstants.CLIENT_CREDENTIALS_GRANT);
			}
			else if (_oAuth2ProviderConfiguration.
						 allowResourceOwnerPasswordCredentialsGrant() &&
					 (allowedGrantType == GrantType.RESOURCE_OWNER_PASSWORD)) {

				clientGrantTypes.add(OAuthConstants.RESOURCE_OWNER_GRANT);
			}
			else if (_oAuth2ProviderConfiguration.allowRefreshTokenGrant() &&
					 (allowedGrantType == GrantType.REFRESH_TOKEN)) {

				clientGrantTypes.add(OAuthConstants.REFRESH_TOKEN_GRANT);
			}
			else {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Unknown or disabled grant type " + allowedGrantType);
				}
			}
		}

		// CXF considers no allowed grant types as allow all

		if (clientGrantTypes.isEmpty()) {
			clientGrantTypes.add(StringPool.BLANK);
		}

		client.setApplicationDescription(oAuth2Application.getDescription());

		if (oAuth2Application.getOAuth2ApplicationScopeAliasesId() > 0) {
			try {
				OAuth2ApplicationScopeAliases oAuth2ApplicationScopeAliases =
					_oAuth2ApplicationScopeAliasesLocalService.
						getOAuth2ApplicationScopeAliases(
							oAuth2Application.
								getOAuth2ApplicationScopeAliasesId());

				client.setRegisteredScopes(
					oAuth2ApplicationScopeAliases.getScopeAliasesList());
			}
			catch (PortalException pe) {
				_log.error(
					"Unable to find associated application scope aliases", pe);

				throw new OAuthServiceException(pe);
			}
		}

		client.setRedirectUris(oAuth2Application.getRedirectURIsList());
		client.setSubject(
			populateUserSubject(
				oAuth2Application.getCompanyId(), oAuth2Application.getUserId(),
				oAuth2Application.getUserName()));

		Map<String, String> properties = client.getProperties();

		properties.put(
			OAuth2ProviderRestEndpointConstants.PROPERTY_KEY_COMPANY_ID,
			String.valueOf(oAuth2Application.getCompanyId()));
		properties.put(
			OAuth2ProviderRestEndpointConstants.PROPERTY_KEY_CLIENT_FEATURES,
			oAuth2Application.getFeatures());

		for (String feature : oAuth2Application.getFeaturesList()) {
			properties.put(
				OAuth2ProviderRestEndpointConstants.
					PROPERTY_KEY_CLIENT_FEATURE_PREFIX +
						feature,
				feature);
		}

		return client;
	}

	protected UserSubject populateUserSubject(
		long companyId, long userId, String username) {

		UserSubject userSubject = new UserSubject(
			username, String.valueOf(userId));

		Map<String, String> properties = userSubject.getProperties();

		properties.put(
			OAuth2ProviderRestEndpointConstants.PROPERTY_KEY_COMPANY_ID,
			String.valueOf(companyId));

		return userSubject;
	}

	@Override
	protected void saveAccessToken(ServerAccessToken serverAccessToken) {
		try {
			_invokeTransactionally(
				() -> _transactionalSaveServerAccessToken(serverAccessToken));
		}
		catch (Throwable throwable) {
			throw new OAuthServiceException(throwable);
		}
	}

	@Override
	protected void saveRefreshToken(RefreshToken refreshToken) {
		List<String> accessTokens = refreshToken.getAccessTokens();

		if ((accessTokens == null) || accessTokens.isEmpty()) {
			throw new OAuthServiceException("Unable to find granted token");
		}

		Iterator<String> iterator = accessTokens.iterator();

		String accessTokenContent = iterator.next();

		OAuth2Authorization oAuth2Authorization =
			_oAuth2AuthorizationLocalService.
				fetchOAuth2AuthorizationByAccessTokenContent(
					accessTokenContent);

		oAuth2Authorization.setRefreshTokenContent(refreshToken.getTokenKey());

		Date createDate = fromCXFTime(refreshToken.getIssuedAt());

		oAuth2Authorization.setRefreshTokenCreateDate(createDate);

		Date expirationDate = fromCXFTime(
			refreshToken.getIssuedAt() + refreshToken.getExpiresIn());

		oAuth2Authorization.setRefreshTokenExpirationDate(expirationDate);

		_oAuth2AuthorizationLocalService.updateOAuth2Authorization(
			oAuth2Authorization);
	}

	protected long toCXFTime(Date dateCreated) {
		return dateCreated.getTime() / 1000;
	}

	private static void _invokeTransactionally(Runnable runnable)
		throws Throwable {

		TransactionInvokerUtil.invoke(
			TransactionConfig.Factory.create(
				Propagation.REQUIRED, new Class<?>[] {Exception.class}),
			() -> {
				runnable.run();

				return null;
			});
	}

	private String _getRemoteIP() {
		MessageContext messageContext = getMessageContext();

		HttpServletRequest httpServletRequest =
			messageContext.getHttpServletRequest();

		return httpServletRequest.getRemoteAddr() + " - " +
			httpServletRequest.getRemoteHost();
	}

	private void _transactionalSaveServerAccessToken(
		ServerAccessToken serverAccessToken) {

		Client client = serverAccessToken.getClient();
		Date createDate = fromCXFTime(serverAccessToken.getIssuedAt());
		Date expirationDate = fromCXFTime(
			serverAccessToken.getIssuedAt() + serverAccessToken.getExpiresIn());

		if (serverAccessToken.getRefreshToken() != null) {
			OAuth2Authorization oAuth2Authorization =
				_oAuth2AuthorizationLocalService.
					fetchOAuth2AuthorizationByRefreshTokenContent(
						serverAccessToken.getRefreshToken());

			oAuth2Authorization.setAccessTokenContent(
				serverAccessToken.getTokenKey());
			oAuth2Authorization.setAccessTokenCreateDate(createDate);
			oAuth2Authorization.setAccessTokenExpirationDate(expirationDate);

			_oAuth2AuthorizationLocalService.updateOAuth2Authorization(
				oAuth2Authorization);

			return;
		}

		OAuth2Application oAuth2Application = resolveOAuth2Application(client);

		long userId = 0;
		String userName = StringPool.BLANK;

		UserSubject userSubject = serverAccessToken.getSubject();

		if (userSubject != null) {
			try {
				userId = GetterUtil.getLong(userSubject.getId());

				User user = _userLocalService.getUser(userId);

				userName = user.getFullName();
			}
			catch (Exception e) {
				_log.error("Unable to load user " + userSubject.getId(), e);

				throw new RuntimeException(e);
			}
		}

		Map<String, String> properties = client.getProperties();

		String remoteAddr = properties.get(
			OAuth2ProviderRestEndpointConstants.
				PROPERTY_KEY_CLIENT_REMOTE_ADDR);
		String remoteHost = properties.get(
			OAuth2ProviderRestEndpointConstants.
				PROPERTY_KEY_CLIENT_REMOTE_HOST);

		OAuth2Authorization oAuth2Authorization =
			_oAuth2AuthorizationLocalService.addOAuth2Authorization(
				oAuth2Application.getCompanyId(), userId, userName,
				oAuth2Application.getOAuth2ApplicationId(),
				oAuth2Application.getOAuth2ApplicationScopeAliasesId(),
				serverAccessToken.getTokenKey(), createDate, expirationDate,
				remoteAddr + ", " + remoteHost, null, null, null);

		Set<LiferayOAuth2Scope> liferayOAuth2Scopes = new HashSet<>();

		List<String> scopesList = OAuthUtils.convertPermissionsToScopeList(
			serverAccessToken.getScopes());

		for (String scope : scopesList) {
			liferayOAuth2Scopes.addAll(
				_scopeFinderLocator.getLiferayOAuth2Scopes(
					oAuth2Application.getCompanyId(), scope));
		}

		try {
			_oAuth2ScopeGrantLocalService.grantLiferayOAuth2Scopes(
				oAuth2Authorization.getOAuth2AuthorizationId(),
				liferayOAuth2Scopes);
		}
		catch (PortalException pe) {
			_log.error("Unable to find authorization " + oAuth2Authorization);

			throw new OAuthServiceException(
				"Unable to grant scope for token", pe);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		LiferayOAuthDataProvider.class);

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	private volatile BearerTokenProviderAccessor _bearerTokenProviderAccessor;

	private PortalCache<String, ServerAuthorizationCodeGrant>
		_codeGrantsPortalCache;

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private MultiVMPool _multiVMPool;

	@Reference
	private OAuth2ApplicationLocalService _oAuth2ApplicationLocalService;

	@Reference
	private OAuth2ApplicationScopeAliasesLocalService
		_oAuth2ApplicationScopeAliasesLocalService;

	@Reference
	private OAuth2AuthorizationLocalService _oAuth2AuthorizationLocalService;

	private OAuth2AuthorizationFlowConfiguration
		_oAuth2AuthorizeFlowConfiguration;
	private OAuth2ProviderConfiguration _oAuth2ProviderConfiguration;

	@Reference
	private OAuth2ScopeGrantLocalService _oAuth2ScopeGrantLocalService;

	@Reference
	private ScopedServiceTrackerMapFactory _scopedServiceTrackerMapFactory;

	@Reference
	private ScopeLocator _scopeFinderLocator;

	@Reference
	private UserLocalService _userLocalService;

}