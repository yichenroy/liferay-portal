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

package com.liferay.dynamic.data.mapping.data.provider.internal.rest;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import com.liferay.dynamic.data.mapping.data.provider.DDMDataProvider;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderException;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderInstanceSettings;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderOutputParametersSettings;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderRequest;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderResponse;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderResponseStatus;
import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance;
import com.liferay.dynamic.data.mapping.service.DDMDataProviderInstanceService;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.net.ConnectException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import jodd.http.HttpException;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(immediate = true, property = "ddm.data.provider.type=rest")
public class DDMRESTDataProvider implements DDMDataProvider {

	@Override
	public DDMDataProviderResponse getData(
			DDMDataProviderRequest ddmDataProviderRequest)
		throws DDMDataProviderException {

		try {
			return doGetData(ddmDataProviderRequest);
		}
		catch (HttpException he) {
			Throwable cause = he.getCause();

			if (cause instanceof ConnectException) {
				DDMDataProviderResponse.Builder builder =
					DDMDataProviderResponse.Builder.newBuilder();

				return builder.withStatus(
					DDMDataProviderResponseStatus.SERVICE_UNAVAILABLE
				).build();
			}
			else {
				throw new DDMDataProviderException(he);
			}
		}
		catch (Exception e) {
			throw new DDMDataProviderException(e);
		}
	}

	@Override
	public Class<?> getSettings() {
		return DDMRESTDataProviderSettings.class;
	}

	protected String buildURL(
		DDMDataProviderRequest ddmDataProviderRequest,
		DDMRESTDataProviderSettings ddmRESTDataProviderSettings) {

		Map<String, String> pathParameters = getPathParameters(
			ddmDataProviderRequest, ddmRESTDataProviderSettings);

		String url = ddmRESTDataProviderSettings.url();

		for (Map.Entry<String, String> pathParameter :
				pathParameters.entrySet()) {

			url = StringUtil.replaceFirst(
				url, String.format("{%s}", pathParameter.getKey()),
				pathParameter.getValue());
		}

		return url;
	}

	protected DDMDataProviderResponse createDDMDataProviderResponse(
		DocumentContext documentContext,
		DDMDataProviderRequest ddmDataProviderRequest,
		DDMRESTDataProviderSettings ddmRESTDataProviderSettings) {

		DDMDataProviderOutputParametersSettings[] outputParameterSettingsArray =
			ddmRESTDataProviderSettings.outputParameters();

		DDMDataProviderResponse.Builder builder =
			DDMDataProviderResponse.Builder.newBuilder();

		if ((outputParameterSettingsArray == null) ||
			(outputParameterSettingsArray.length == 0)) {

			return builder.build();
		}

		for (DDMDataProviderOutputParametersSettings outputParameterSettings :
				outputParameterSettingsArray) {

			String name = outputParameterSettings.outputParameterName();
			String type = outputParameterSettings.outputParameterType();
			String path = outputParameterSettings.outputParameterPath();

			if (Objects.equals(type, "text")) {
				String value = documentContext.read(
					normalizePath(path), String.class);

				builder = builder.withOutput(name, value);
			}
			else if (Objects.equals(type, "number")) {
				Number value = documentContext.read(
					normalizePath(path), Number.class);

				builder = builder.withOutput(name, value);
			}
			else if (Objects.equals(type, "list")) {
				String[] paths = StringUtil.split(path, CharPool.SEMICOLON);

				String normalizedValuePath = normalizePath(paths[0]);

				String normalizedKeyPath = normalizedValuePath;

				List<String> values = documentContext.read(
					normalizedValuePath, List.class);

				if (values == null) {
					continue;
				}

				List<String> keys = new ArrayList<>(values);

				if (paths.length >= 2) {
					normalizedKeyPath = normalizePath(paths[1]);

					keys = documentContext.read(normalizedKeyPath);
				}

				List<KeyValuePair> keyValuePairs = new ArrayList<>();

				for (int i = 0; i < values.size(); i++) {
					keyValuePairs.add(
						new KeyValuePair(keys.get(i), values.get(i)));
				}

				if (ddmRESTDataProviderSettings.pagination()) {
					Optional<String> paginationStartOptional =
						ddmDataProviderRequest.getParameter(
							"paginationStart", String.class);

					int start = Integer.valueOf(
						paginationStartOptional.orElse("1"));

					Optional<String> paginationEndOptional =
						ddmDataProviderRequest.getParameter(
							"paginationEnd", String.class);

					int end = Integer.valueOf(
						paginationEndOptional.orElse("10"));

					if (keyValuePairs.size() > (end - start)) {
						List<KeyValuePair> sublist = ListUtil.subList(
							keyValuePairs, start, end);

						builder = builder.withOutput(name, sublist);
					}
				}
				else {
					builder = builder.withOutput(name, keyValuePairs);
				}
			}
		}

		return builder.build();
	}

	protected DDMDataProviderResponse doGetData(
			DDMDataProviderRequest ddmDataProviderRequest)
		throws Exception {

		preparePermissionThreadLocal(ddmDataProviderRequest);

		Optional<DDMDataProviderInstance> ddmDataProviderInstance =
			fetchDDMDataProviderInstance(
				ddmDataProviderRequest.getDDMDataProviderId());

		if (!ddmDataProviderInstance.isPresent()) {
			DDMDataProviderResponse.Builder builder =
				DDMDataProviderResponse.Builder.newBuilder();

			return builder.withStatus(
				DDMDataProviderResponseStatus.SERVICE_UNAVAILABLE
			).build();
		}

		DDMRESTDataProviderSettings ddmRESTDataProviderSettings =
			ddmDataProviderInstanceSettings.getSettings(
				ddmDataProviderInstance.get(),
				DDMRESTDataProviderSettings.class);

		HttpRequest httpRequest = HttpRequest.get(
			buildURL(ddmDataProviderRequest, ddmRESTDataProviderSettings));

		if (StringUtil.startsWith(
				ddmRESTDataProviderSettings.url(), Http.HTTPS)) {

			httpRequest.trustAllCerts(true);
		}

		if (Validator.isNotNull(ddmRESTDataProviderSettings.username())) {
			httpRequest.basicAuthentication(
				ddmRESTDataProviderSettings.username(),
				ddmRESTDataProviderSettings.password());
		}

		setRequestParameters(
			ddmDataProviderRequest, ddmRESTDataProviderSettings, httpRequest);

		String cacheKey = getCacheKey(httpRequest);

		DDMDataProviderResponse ddmDataProviderResponse = _portalCache.get(
			cacheKey);

		if ((ddmDataProviderResponse != null) &&
			ddmRESTDataProviderSettings.cacheable()) {

			return ddmDataProviderResponse;
		}

		HttpResponse httpResponse = httpRequest.send();

		DocumentContext documentContext = JsonPath.parse(
			httpResponse.bodyText());

		ddmDataProviderResponse = createDDMDataProviderResponse(
			documentContext, ddmDataProviderRequest,
			ddmRESTDataProviderSettings);

		if (ddmRESTDataProviderSettings.cacheable()) {
			_portalCache.put(cacheKey, ddmDataProviderResponse);
		}

		return ddmDataProviderResponse;
	}

	protected Optional<DDMDataProviderInstance> fetchDDMDataProviderInstance(
			String ddmDataProviderInstanceId)
		throws PortalException {

		DDMDataProviderInstance ddmDataProviderInstance =
			ddmDataProviderInstanceService.fetchDataProviderInstanceByUuid(
				ddmDataProviderInstanceId);

		if ((ddmDataProviderInstance == null) &&
			Validator.isNumber(ddmDataProviderInstanceId)) {

			ddmDataProviderInstance =
				ddmDataProviderInstanceService.fetchDataProviderInstance(
					Long.valueOf(ddmDataProviderInstanceId));
		}

		return Optional.ofNullable(ddmDataProviderInstance);
	}

	protected String getCacheKey(HttpRequest httpRequest) {
		return httpRequest.url();
	}

	protected Map<String, String> getPathParameters(
		DDMDataProviderRequest ddmDataProviderRequest,
		DDMRESTDataProviderSettings ddmRESTDataProviderSettings) {

		Map<String, Object> parameters = ddmDataProviderRequest.getParameters();

		Map<String, String> pathParameters = new HashMap<>();

		Matcher matcher = _pathParameterPattern.matcher(
			ddmRESTDataProviderSettings.url());

		while (matcher.find()) {
			String parameterName = matcher.group(1);

			if (parameters.containsKey(parameterName)) {
				pathParameters.put(
					parameterName,
					GetterUtil.getString(parameters.get(parameterName)));
			}
		}

		return pathParameters;
	}

	protected Map<String, String> getQueryParameters(
		DDMDataProviderRequest ddmDataProviderRequest,
		DDMRESTDataProviderSettings ddmRESTDataProviderSettings) {

		Map<String, String> pathParameters = getPathParameters(
			ddmDataProviderRequest, ddmRESTDataProviderSettings);

		Map<String, Object> parametersMap =
			ddmDataProviderRequest.getParameters();

		Set<Map.Entry<String, Object>> entrySet = parametersMap.entrySet();

		Stream<Map.Entry<String, Object>> entryStream = entrySet.stream();

		Map<String, String> parameters = new HashMap<>();

		entryStream.forEach(
			entry -> parameters.put(
				entry.getKey(), String.valueOf(entry.getValue())));

		return MapUtil.filter(
			parameters,
			parameter -> !pathParameters.containsKey(parameter.getKey()));
	}

	protected String normalizePath(String path) {
		if (StringUtil.startsWith(path, StringPool.DOLLAR) ||
			StringUtil.startsWith(path, StringPool.PERIOD) ||
			StringUtil.startsWith(path, StringPool.STAR)) {

			return path;
		}

		return StringPool.PERIOD.concat(path);
	}

	protected void preparePermissionThreadLocal(
			DDMDataProviderRequest ddmDataProviderRequest)
		throws Exception {

		if (PermissionThreadLocal.getPermissionChecker() == null) {
			Optional<HttpServletRequest> httpServletRequestOptional =
				ddmDataProviderRequest.getParameter(
					"httpServletRequest", HttpServletRequest.class);

			if (httpServletRequestOptional.isPresent()) {
				HttpServletRequest httpServletRequest =
					httpServletRequestOptional.get();

				User user = portal.getUser(httpServletRequest);

				if (user == null) {
					user = userLocalService.getDefaultUser(
						portal.getCompanyId(httpServletRequest));
				}

				PermissionChecker permissionChecker =
					PermissionCheckerFactoryUtil.create(user);

				PermissionThreadLocal.setPermissionChecker(permissionChecker);
			}
		}
	}

	@Reference(unbind = "-")
	protected void setMultiVMPool(MultiVMPool multiVMPool) {
		_portalCache =
			(PortalCache<String, DDMDataProviderResponse>)
				multiVMPool.getPortalCache(DDMRESTDataProvider.class.getName());
	}

	protected void setRequestParameters(
		DDMDataProviderRequest ddmDataProviderRequest,
		DDMRESTDataProviderSettings ddmRESTDataProviderSettings,
		HttpRequest httpRequest) {

		if (ddmRESTDataProviderSettings.filterable()) {
			Optional<String> filterParameterValue =
				ddmDataProviderRequest.getParameter(
					"filterParameterValue", String.class);

			if (filterParameterValue.isPresent()) {
				httpRequest.query(
					ddmRESTDataProviderSettings.filterParameterName(),
					filterParameterValue.get());
			}
		}

		if (ddmRESTDataProviderSettings.pagination()) {
			Optional<String> paginationStart =
				ddmDataProviderRequest.getParameter(
					"paginationStart", String.class);

			if (paginationStart.isPresent()) {
				httpRequest.query(
					ddmRESTDataProviderSettings.paginationStartParameterName(),
					paginationStart.get());
			}

			Optional<String> paginationEnd =
				ddmDataProviderRequest.getParameter(
					"paginationEnd", String.class);

			if (paginationEnd.isPresent()) {
				httpRequest.query(
					ddmRESTDataProviderSettings.paginationEndParameterName(),
					paginationEnd.get());
			}
		}

		httpRequest.query(
			getQueryParameters(
				ddmDataProviderRequest, ddmRESTDataProviderSettings));
	}

	@Reference
	protected DDMDataProviderInstanceService ddmDataProviderInstanceService;

	@Reference
	protected DDMDataProviderInstanceSettings ddmDataProviderInstanceSettings;

	@Reference
	protected Portal portal;

	@Reference
	protected UserLocalService userLocalService;

	private final Pattern _pathParameterPattern = Pattern.compile("\\{(.*)\\}");
	private PortalCache<String, DDMDataProviderResponse> _portalCache;

}