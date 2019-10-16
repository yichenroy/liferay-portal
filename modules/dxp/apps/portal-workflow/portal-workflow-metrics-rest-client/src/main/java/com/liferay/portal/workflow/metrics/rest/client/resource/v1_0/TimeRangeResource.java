/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.workflow.metrics.rest.client.resource.v1_0;

import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.TimeRange;
import com.liferay.portal.workflow.metrics.rest.client.http.HttpInvoker;
import com.liferay.portal.workflow.metrics.rest.client.pagination.Page;
import com.liferay.portal.workflow.metrics.rest.client.serdes.v1_0.TimeRangeSerDes;

import java.util.Locale;
import java.util.logging.Logger;

import javax.annotation.Generated;

/**
 * @author Rafael Praxedes
 * @generated
 */
@Generated("")
public interface TimeRangeResource {

	public static Builder builder() {
		return new Builder();
	}

	public Page<TimeRange> getTimeRangesPage() throws Exception;

	public HttpInvoker.HttpResponse getTimeRangesPageHttpResponse()
		throws Exception;

	public static class Builder {

		public Builder authentication(String login, String password) {
			_login = login;
			_password = password;

			return this;
		}

		public TimeRangeResource build() {
			return new TimeRangeResourceImpl(this);
		}

		public Builder endpoint(String host, int port, String scheme) {
			_host = host;
			_port = port;
			_scheme = scheme;

			return this;
		}

		public Builder locale(Locale locale) {
			_locale = locale;

			return this;
		}

		private Builder() {
		}

		private String _host = "localhost";
		private Locale _locale;
		private String _login = "test@liferay.com";
		private String _password = "test";
		private int _port = 8080;
		private String _scheme = "http";

	}

	public static class TimeRangeResourceImpl implements TimeRangeResource {

		public Page<TimeRange> getTimeRangesPage() throws Exception {
			HttpInvoker.HttpResponse httpResponse =
				getTimeRangesPageHttpResponse();

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			return Page.of(content, TimeRangeSerDes::toDTO);
		}

		public HttpInvoker.HttpResponse getTimeRangesPageHttpResponse()
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			if (_builder._locale != null) {
				httpInvoker.header(
					"Accept-Language", _builder._locale.toLanguageTag());
			}

			httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);

			httpInvoker.path(
				_builder._scheme + "://" + _builder._host + ":" +
					_builder._port +
						"/o/portal-workflow-metrics/v1.0/time-ranges");

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		private TimeRangeResourceImpl(Builder builder) {
			_builder = builder;
		}

		private static final Logger _logger = Logger.getLogger(
			TimeRangeResource.class.getName());

		private Builder _builder;

	}

}