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

package com.liferay.osb.koroneiki.phloem.rest.client.resource.v1_0;

import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.PostalAddress;
import com.liferay.osb.koroneiki.phloem.rest.client.http.HttpInvoker;
import com.liferay.osb.koroneiki.phloem.rest.client.pagination.Page;
import com.liferay.osb.koroneiki.phloem.rest.client.serdes.v1_0.PostalAddressSerDes;

import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Generated;

/**
 * @author Amos Fong
 * @generated
 */
@Generated("")
public interface PostalAddressResource {

	public static Builder builder() {
		return new Builder();
	}

	public Page<PostalAddress> getAccountAccountKeyPostalAddressesPage(
			String accountKey)
		throws Exception;

	public HttpInvoker.HttpResponse
			getAccountAccountKeyPostalAddressesPageHttpResponse(
				String accountKey)
		throws Exception;

	public PostalAddress postAccountAccountKeyPostalAddress(
			String accountKey, PostalAddress postalAddress)
		throws Exception;

	public HttpInvoker.HttpResponse
			postAccountAccountKeyPostalAddressHttpResponse(
				String accountKey, PostalAddress postalAddress)
		throws Exception;

	public void deletePostalAddress(Long postalAddressId) throws Exception;

	public HttpInvoker.HttpResponse deletePostalAddressHttpResponse(
			Long postalAddressId)
		throws Exception;

	public PostalAddress getPostalAddress(Long postalAddressId)
		throws Exception;

	public HttpInvoker.HttpResponse getPostalAddressHttpResponse(
			Long postalAddressId)
		throws Exception;

	public PostalAddress putPostalAddress(
			Long postalAddressId, PostalAddress postalAddress)
		throws Exception;

	public HttpInvoker.HttpResponse putPostalAddressHttpResponse(
			Long postalAddressId, PostalAddress postalAddress)
		throws Exception;

	public static class Builder {

		public Builder authentication(String login, String password) {
			_login = login;
			_password = password;

			return this;
		}

		public PostalAddressResource build() {
			return new PostalAddressResourceImpl(this);
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

	public static class PostalAddressResourceImpl
		implements PostalAddressResource {

		public Page<PostalAddress> getAccountAccountKeyPostalAddressesPage(
				String accountKey)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getAccountAccountKeyPostalAddressesPageHttpResponse(accountKey);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			return Page.of(content, PostalAddressSerDes::toDTO);
		}

		public HttpInvoker.HttpResponse
				getAccountAccountKeyPostalAddressesPageHttpResponse(
					String accountKey)
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
						"/o/koroneiki-rest/v1.0/accounts/{accountKey}/postal-addresses",
				accountKey);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public PostalAddress postAccountAccountKeyPostalAddress(
				String accountKey, PostalAddress postalAddress)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				postAccountAccountKeyPostalAddressHttpResponse(
					accountKey, postalAddress);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return PostalAddressSerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw e;
			}
		}

		public HttpInvoker.HttpResponse
				postAccountAccountKeyPostalAddressHttpResponse(
					String accountKey, PostalAddress postalAddress)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.body(postalAddress.toString(), "application/json");

			if (_builder._locale != null) {
				httpInvoker.header(
					"Accept-Language", _builder._locale.toLanguageTag());
			}

			httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);

			httpInvoker.path(
				_builder._scheme + "://" + _builder._host + ":" +
					_builder._port +
						"/o/koroneiki-rest/v1.0/accounts/{accountKey}/postal-addresses",
				accountKey);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public void deletePostalAddress(Long postalAddressId) throws Exception {
			HttpInvoker.HttpResponse httpResponse =
				deletePostalAddressHttpResponse(postalAddressId);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());
		}

		public HttpInvoker.HttpResponse deletePostalAddressHttpResponse(
				Long postalAddressId)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			if (_builder._locale != null) {
				httpInvoker.header(
					"Accept-Language", _builder._locale.toLanguageTag());
			}

			httpInvoker.httpMethod(HttpInvoker.HttpMethod.DELETE);

			httpInvoker.path(
				_builder._scheme + "://" + _builder._host + ":" +
					_builder._port +
						"/o/koroneiki-rest/v1.0/postal-addresses/{postalAddressId}",
				postalAddressId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public PostalAddress getPostalAddress(Long postalAddressId)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getPostalAddressHttpResponse(postalAddressId);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return PostalAddressSerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw e;
			}
		}

		public HttpInvoker.HttpResponse getPostalAddressHttpResponse(
				Long postalAddressId)
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
						"/o/koroneiki-rest/v1.0/postal-addresses/{postalAddressId}",
				postalAddressId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public PostalAddress putPostalAddress(
				Long postalAddressId, PostalAddress postalAddress)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				putPostalAddressHttpResponse(postalAddressId, postalAddress);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return PostalAddressSerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw e;
			}
		}

		public HttpInvoker.HttpResponse putPostalAddressHttpResponse(
				Long postalAddressId, PostalAddress postalAddress)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.body(postalAddress.toString(), "application/json");

			if (_builder._locale != null) {
				httpInvoker.header(
					"Accept-Language", _builder._locale.toLanguageTag());
			}

			httpInvoker.httpMethod(HttpInvoker.HttpMethod.PUT);

			httpInvoker.path(
				_builder._scheme + "://" + _builder._host + ":" +
					_builder._port +
						"/o/koroneiki-rest/v1.0/postal-addresses/{postalAddressId}",
				postalAddressId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		private PostalAddressResourceImpl(Builder builder) {
			_builder = builder;
		}

		private static final Logger _logger = Logger.getLogger(
			PostalAddressResource.class.getName());

		private Builder _builder;

	}

}