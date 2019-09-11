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

import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.ExternalLink;
import com.liferay.osb.koroneiki.phloem.rest.client.http.HttpInvoker;
import com.liferay.osb.koroneiki.phloem.rest.client.pagination.Page;
import com.liferay.osb.koroneiki.phloem.rest.client.pagination.Pagination;
import com.liferay.osb.koroneiki.phloem.rest.client.serdes.v1_0.ExternalLinkSerDes;

import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Generated;

/**
 * @author Amos Fong
 * @generated
 */
@Generated("")
public interface ExternalLinkResource {

	public static Builder builder() {
		return new Builder();
	}

	public Page<ExternalLink> getAccountAccountKeyExternalLinksPage(
			String accountKey, Pagination pagination)
		throws Exception;

	public HttpInvoker.HttpResponse
			getAccountAccountKeyExternalLinksPageHttpResponse(
				String accountKey, Pagination pagination)
		throws Exception;

	public ExternalLink postAccountAccountKeyExternalLink(
			String accountKey, ExternalLink externalLink)
		throws Exception;

	public HttpInvoker.HttpResponse
			postAccountAccountKeyExternalLinkHttpResponse(
				String accountKey, ExternalLink externalLink)
		throws Exception;

	public Page<ExternalLink> getContactByOktaExternalLinksPage(
			String oktaId, Pagination pagination)
		throws Exception;

	public HttpInvoker.HttpResponse
			getContactByOktaExternalLinksPageHttpResponse(
				String oktaId, Pagination pagination)
		throws Exception;

	public ExternalLink postContactByOktaExternalLink(
			String oktaId, ExternalLink externalLink)
		throws Exception;

	public HttpInvoker.HttpResponse postContactByOktaExternalLinkHttpResponse(
			String oktaId, ExternalLink externalLink)
		throws Exception;

	public Page<ExternalLink> getContactByUuidExternalLinksPage(
			String uuid, Pagination pagination)
		throws Exception;

	public HttpInvoker.HttpResponse
			getContactByUuidExternalLinksPageHttpResponse(
				String uuid, Pagination pagination)
		throws Exception;

	public ExternalLink postContactByUuidExternalLink(
			String uuid, ExternalLink externalLink)
		throws Exception;

	public HttpInvoker.HttpResponse postContactByUuidExternalLinkHttpResponse(
			String uuid, ExternalLink externalLink)
		throws Exception;

	public void deleteExternalLink(String externalLinkKey) throws Exception;

	public HttpInvoker.HttpResponse deleteExternalLinkHttpResponse(
			String externalLinkKey)
		throws Exception;

	public ExternalLink getExternalLink(String externalLinkKey)
		throws Exception;

	public HttpInvoker.HttpResponse getExternalLinkHttpResponse(
			String externalLinkKey)
		throws Exception;

	public Page<ExternalLink>
			getProductConsumptionProductConsumptionKeyExternalLinksPage(
				String productConsumptionKey, Pagination pagination)
		throws Exception;

	public HttpInvoker.HttpResponse
			getProductConsumptionProductConsumptionKeyExternalLinksPageHttpResponse(
				String productConsumptionKey, Pagination pagination)
		throws Exception;

	public ExternalLink postProductConsumptionProductConsumptionKeyExternalLink(
			String productConsumptionKey, ExternalLink externalLink)
		throws Exception;

	public HttpInvoker.HttpResponse
			postProductConsumptionProductConsumptionKeyExternalLinkHttpResponse(
				String productConsumptionKey, ExternalLink externalLink)
		throws Exception;

	public Page<ExternalLink>
			getProductPurchaseProductPurchaseKeyExternalLinksPage(
				String productPurchaseKey, Pagination pagination)
		throws Exception;

	public HttpInvoker.HttpResponse
			getProductPurchaseProductPurchaseKeyExternalLinksPageHttpResponse(
				String productPurchaseKey, Pagination pagination)
		throws Exception;

	public ExternalLink postProductPurchaseProductPurchaseKeyExternalLink(
			String productPurchaseKey, ExternalLink externalLink)
		throws Exception;

	public HttpInvoker.HttpResponse
			postProductPurchaseProductPurchaseKeyExternalLinkHttpResponse(
				String productPurchaseKey, ExternalLink externalLink)
		throws Exception;

	public Page<ExternalLink> getProductProductKeyExternalLinksPage(
			String productKey, Pagination pagination)
		throws Exception;

	public HttpInvoker.HttpResponse
			getProductProductKeyExternalLinksPageHttpResponse(
				String productKey, Pagination pagination)
		throws Exception;

	public ExternalLink postProductProductKeyExternalLink(
			String productKey, ExternalLink externalLink)
		throws Exception;

	public HttpInvoker.HttpResponse
			postProductProductKeyExternalLinkHttpResponse(
				String productKey, ExternalLink externalLink)
		throws Exception;

	public Page<ExternalLink> getTeamTeamKeyExternalLinksPage(
			String teamKey, Pagination pagination)
		throws Exception;

	public HttpInvoker.HttpResponse getTeamTeamKeyExternalLinksPageHttpResponse(
			String teamKey, Pagination pagination)
		throws Exception;

	public ExternalLink postTeamTeamKeyExternalLink(
			String teamKey, ExternalLink externalLink)
		throws Exception;

	public HttpInvoker.HttpResponse postTeamTeamKeyExternalLinkHttpResponse(
			String teamKey, ExternalLink externalLink)
		throws Exception;

	public static class Builder {

		public Builder authentication(String login, String password) {
			_login = login;
			_password = password;

			return this;
		}

		public ExternalLinkResource build() {
			return new ExternalLinkResourceImpl(this);
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

	public static class ExternalLinkResourceImpl
		implements ExternalLinkResource {

		public Page<ExternalLink> getAccountAccountKeyExternalLinksPage(
				String accountKey, Pagination pagination)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getAccountAccountKeyExternalLinksPageHttpResponse(
					accountKey, pagination);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			return Page.of(content, ExternalLinkSerDes::toDTO);
		}

		public HttpInvoker.HttpResponse
				getAccountAccountKeyExternalLinksPageHttpResponse(
					String accountKey, Pagination pagination)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			if (_builder._locale != null) {
				httpInvoker.header(
					"Accept-Language", _builder._locale.toLanguageTag());
			}

			httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);

			if (pagination != null) {
				httpInvoker.parameter(
					"page", String.valueOf(pagination.getPage()));
				httpInvoker.parameter(
					"pageSize", String.valueOf(pagination.getPageSize()));
			}

			httpInvoker.path(
				_builder._scheme + "://" + _builder._host + ":" +
					_builder._port +
						"/o/koroneiki-rest/v1.0/accounts/{accountKey}/external-links",
				accountKey);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public ExternalLink postAccountAccountKeyExternalLink(
				String accountKey, ExternalLink externalLink)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				postAccountAccountKeyExternalLinkHttpResponse(
					accountKey, externalLink);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return ExternalLinkSerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw e;
			}
		}

		public HttpInvoker.HttpResponse
				postAccountAccountKeyExternalLinkHttpResponse(
					String accountKey, ExternalLink externalLink)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.body(externalLink.toString(), "application/json");

			if (_builder._locale != null) {
				httpInvoker.header(
					"Accept-Language", _builder._locale.toLanguageTag());
			}

			httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);

			httpInvoker.path(
				_builder._scheme + "://" + _builder._host + ":" +
					_builder._port +
						"/o/koroneiki-rest/v1.0/accounts/{accountKey}/external-links",
				accountKey);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public Page<ExternalLink> getContactByOktaExternalLinksPage(
				String oktaId, Pagination pagination)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getContactByOktaExternalLinksPageHttpResponse(
					oktaId, pagination);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			return Page.of(content, ExternalLinkSerDes::toDTO);
		}

		public HttpInvoker.HttpResponse
				getContactByOktaExternalLinksPageHttpResponse(
					String oktaId, Pagination pagination)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			if (_builder._locale != null) {
				httpInvoker.header(
					"Accept-Language", _builder._locale.toLanguageTag());
			}

			httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);

			if (pagination != null) {
				httpInvoker.parameter(
					"page", String.valueOf(pagination.getPage()));
				httpInvoker.parameter(
					"pageSize", String.valueOf(pagination.getPageSize()));
			}

			httpInvoker.path(
				_builder._scheme + "://" + _builder._host + ":" +
					_builder._port +
						"/o/koroneiki-rest/v1.0/contacts/by-okta-id/{oktaId}/external-links",
				oktaId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public ExternalLink postContactByOktaExternalLink(
				String oktaId, ExternalLink externalLink)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				postContactByOktaExternalLinkHttpResponse(oktaId, externalLink);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return ExternalLinkSerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw e;
			}
		}

		public HttpInvoker.HttpResponse
				postContactByOktaExternalLinkHttpResponse(
					String oktaId, ExternalLink externalLink)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.body(externalLink.toString(), "application/json");

			if (_builder._locale != null) {
				httpInvoker.header(
					"Accept-Language", _builder._locale.toLanguageTag());
			}

			httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);

			httpInvoker.path(
				_builder._scheme + "://" + _builder._host + ":" +
					_builder._port +
						"/o/koroneiki-rest/v1.0/contacts/by-okta-id/{oktaId}/external-links",
				oktaId);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public Page<ExternalLink> getContactByUuidExternalLinksPage(
				String uuid, Pagination pagination)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getContactByUuidExternalLinksPageHttpResponse(uuid, pagination);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			return Page.of(content, ExternalLinkSerDes::toDTO);
		}

		public HttpInvoker.HttpResponse
				getContactByUuidExternalLinksPageHttpResponse(
					String uuid, Pagination pagination)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			if (_builder._locale != null) {
				httpInvoker.header(
					"Accept-Language", _builder._locale.toLanguageTag());
			}

			httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);

			if (pagination != null) {
				httpInvoker.parameter(
					"page", String.valueOf(pagination.getPage()));
				httpInvoker.parameter(
					"pageSize", String.valueOf(pagination.getPageSize()));
			}

			httpInvoker.path(
				_builder._scheme + "://" + _builder._host + ":" +
					_builder._port +
						"/o/koroneiki-rest/v1.0/contacts/by-uuid/{uuid}/external-links",
				uuid);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public ExternalLink postContactByUuidExternalLink(
				String uuid, ExternalLink externalLink)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				postContactByUuidExternalLinkHttpResponse(uuid, externalLink);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return ExternalLinkSerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw e;
			}
		}

		public HttpInvoker.HttpResponse
				postContactByUuidExternalLinkHttpResponse(
					String uuid, ExternalLink externalLink)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.body(externalLink.toString(), "application/json");

			if (_builder._locale != null) {
				httpInvoker.header(
					"Accept-Language", _builder._locale.toLanguageTag());
			}

			httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);

			httpInvoker.path(
				_builder._scheme + "://" + _builder._host + ":" +
					_builder._port +
						"/o/koroneiki-rest/v1.0/contacts/by-uuid/{uuid}/external-links",
				uuid);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public void deleteExternalLink(String externalLinkKey)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				deleteExternalLinkHttpResponse(externalLinkKey);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());
		}

		public HttpInvoker.HttpResponse deleteExternalLinkHttpResponse(
				String externalLinkKey)
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
						"/o/koroneiki-rest/v1.0/external-links/{externalLinkKey}",
				externalLinkKey);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public ExternalLink getExternalLink(String externalLinkKey)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse = getExternalLinkHttpResponse(
				externalLinkKey);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return ExternalLinkSerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw e;
			}
		}

		public HttpInvoker.HttpResponse getExternalLinkHttpResponse(
				String externalLinkKey)
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
						"/o/koroneiki-rest/v1.0/external-links/{externalLinkKey}",
				externalLinkKey);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public Page<ExternalLink>
				getProductConsumptionProductConsumptionKeyExternalLinksPage(
					String productConsumptionKey, Pagination pagination)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getProductConsumptionProductConsumptionKeyExternalLinksPageHttpResponse(
					productConsumptionKey, pagination);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			return Page.of(content, ExternalLinkSerDes::toDTO);
		}

		public HttpInvoker.HttpResponse
				getProductConsumptionProductConsumptionKeyExternalLinksPageHttpResponse(
					String productConsumptionKey, Pagination pagination)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			if (_builder._locale != null) {
				httpInvoker.header(
					"Accept-Language", _builder._locale.toLanguageTag());
			}

			httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);

			if (pagination != null) {
				httpInvoker.parameter(
					"page", String.valueOf(pagination.getPage()));
				httpInvoker.parameter(
					"pageSize", String.valueOf(pagination.getPageSize()));
			}

			httpInvoker.path(
				_builder._scheme + "://" + _builder._host + ":" +
					_builder._port +
						"/o/koroneiki-rest/v1.0/product-consumptions/{productConsumptionKey}/external-links",
				productConsumptionKey);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public ExternalLink
				postProductConsumptionProductConsumptionKeyExternalLink(
					String productConsumptionKey, ExternalLink externalLink)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				postProductConsumptionProductConsumptionKeyExternalLinkHttpResponse(
					productConsumptionKey, externalLink);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return ExternalLinkSerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw e;
			}
		}

		public HttpInvoker.HttpResponse
				postProductConsumptionProductConsumptionKeyExternalLinkHttpResponse(
					String productConsumptionKey, ExternalLink externalLink)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.body(externalLink.toString(), "application/json");

			if (_builder._locale != null) {
				httpInvoker.header(
					"Accept-Language", _builder._locale.toLanguageTag());
			}

			httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);

			httpInvoker.path(
				_builder._scheme + "://" + _builder._host + ":" +
					_builder._port +
						"/o/koroneiki-rest/v1.0/product-consumptions/{productConsumptionKey}/external-links",
				productConsumptionKey);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public Page<ExternalLink>
				getProductPurchaseProductPurchaseKeyExternalLinksPage(
					String productPurchaseKey, Pagination pagination)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getProductPurchaseProductPurchaseKeyExternalLinksPageHttpResponse(
					productPurchaseKey, pagination);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			return Page.of(content, ExternalLinkSerDes::toDTO);
		}

		public HttpInvoker.HttpResponse
				getProductPurchaseProductPurchaseKeyExternalLinksPageHttpResponse(
					String productPurchaseKey, Pagination pagination)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			if (_builder._locale != null) {
				httpInvoker.header(
					"Accept-Language", _builder._locale.toLanguageTag());
			}

			httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);

			if (pagination != null) {
				httpInvoker.parameter(
					"page", String.valueOf(pagination.getPage()));
				httpInvoker.parameter(
					"pageSize", String.valueOf(pagination.getPageSize()));
			}

			httpInvoker.path(
				_builder._scheme + "://" + _builder._host + ":" +
					_builder._port +
						"/o/koroneiki-rest/v1.0/product-purchases/{productPurchaseKey}/external-links",
				productPurchaseKey);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public ExternalLink postProductPurchaseProductPurchaseKeyExternalLink(
				String productPurchaseKey, ExternalLink externalLink)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				postProductPurchaseProductPurchaseKeyExternalLinkHttpResponse(
					productPurchaseKey, externalLink);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return ExternalLinkSerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw e;
			}
		}

		public HttpInvoker.HttpResponse
				postProductPurchaseProductPurchaseKeyExternalLinkHttpResponse(
					String productPurchaseKey, ExternalLink externalLink)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.body(externalLink.toString(), "application/json");

			if (_builder._locale != null) {
				httpInvoker.header(
					"Accept-Language", _builder._locale.toLanguageTag());
			}

			httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);

			httpInvoker.path(
				_builder._scheme + "://" + _builder._host + ":" +
					_builder._port +
						"/o/koroneiki-rest/v1.0/product-purchases/{productPurchaseKey}/external-links",
				productPurchaseKey);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public Page<ExternalLink> getProductProductKeyExternalLinksPage(
				String productKey, Pagination pagination)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getProductProductKeyExternalLinksPageHttpResponse(
					productKey, pagination);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			return Page.of(content, ExternalLinkSerDes::toDTO);
		}

		public HttpInvoker.HttpResponse
				getProductProductKeyExternalLinksPageHttpResponse(
					String productKey, Pagination pagination)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			if (_builder._locale != null) {
				httpInvoker.header(
					"Accept-Language", _builder._locale.toLanguageTag());
			}

			httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);

			if (pagination != null) {
				httpInvoker.parameter(
					"page", String.valueOf(pagination.getPage()));
				httpInvoker.parameter(
					"pageSize", String.valueOf(pagination.getPageSize()));
			}

			httpInvoker.path(
				_builder._scheme + "://" + _builder._host + ":" +
					_builder._port +
						"/o/koroneiki-rest/v1.0/products/{productKey}/external-links",
				productKey);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public ExternalLink postProductProductKeyExternalLink(
				String productKey, ExternalLink externalLink)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				postProductProductKeyExternalLinkHttpResponse(
					productKey, externalLink);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return ExternalLinkSerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw e;
			}
		}

		public HttpInvoker.HttpResponse
				postProductProductKeyExternalLinkHttpResponse(
					String productKey, ExternalLink externalLink)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.body(externalLink.toString(), "application/json");

			if (_builder._locale != null) {
				httpInvoker.header(
					"Accept-Language", _builder._locale.toLanguageTag());
			}

			httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);

			httpInvoker.path(
				_builder._scheme + "://" + _builder._host + ":" +
					_builder._port +
						"/o/koroneiki-rest/v1.0/products/{productKey}/external-links",
				productKey);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public Page<ExternalLink> getTeamTeamKeyExternalLinksPage(
				String teamKey, Pagination pagination)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				getTeamTeamKeyExternalLinksPageHttpResponse(
					teamKey, pagination);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			return Page.of(content, ExternalLinkSerDes::toDTO);
		}

		public HttpInvoker.HttpResponse
				getTeamTeamKeyExternalLinksPageHttpResponse(
					String teamKey, Pagination pagination)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			if (_builder._locale != null) {
				httpInvoker.header(
					"Accept-Language", _builder._locale.toLanguageTag());
			}

			httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);

			if (pagination != null) {
				httpInvoker.parameter(
					"page", String.valueOf(pagination.getPage()));
				httpInvoker.parameter(
					"pageSize", String.valueOf(pagination.getPageSize()));
			}

			httpInvoker.path(
				_builder._scheme + "://" + _builder._host + ":" +
					_builder._port +
						"/o/koroneiki-rest/v1.0/teams/{teamKey}/external-links",
				teamKey);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		public ExternalLink postTeamTeamKeyExternalLink(
				String teamKey, ExternalLink externalLink)
			throws Exception {

			HttpInvoker.HttpResponse httpResponse =
				postTeamTeamKeyExternalLinkHttpResponse(teamKey, externalLink);

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine(
				"HTTP response status code: " + httpResponse.getStatusCode());

			try {
				return ExternalLinkSerDes.toDTO(content);
			}
			catch (Exception e) {
				_logger.log(
					Level.WARNING,
					"Unable to process HTTP response: " + content, e);

				throw e;
			}
		}

		public HttpInvoker.HttpResponse postTeamTeamKeyExternalLinkHttpResponse(
				String teamKey, ExternalLink externalLink)
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			httpInvoker.body(externalLink.toString(), "application/json");

			if (_builder._locale != null) {
				httpInvoker.header(
					"Accept-Language", _builder._locale.toLanguageTag());
			}

			httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);

			httpInvoker.path(
				_builder._scheme + "://" + _builder._host + ":" +
					_builder._port +
						"/o/koroneiki-rest/v1.0/teams/{teamKey}/external-links",
				teamKey);

			httpInvoker.userNameAndPassword(
				_builder._login + ":" + _builder._password);

			return httpInvoker.invoke();
		}

		private ExternalLinkResourceImpl(Builder builder) {
			_builder = builder;
		}

		private static final Logger _logger = Logger.getLogger(
			ExternalLinkResource.class.getName());

		private Builder _builder;

	}

}