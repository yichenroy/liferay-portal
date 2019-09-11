<%--
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
--%>

<%@ include file="/init.jsp" %>

<%
String tabs1 = ParamUtil.getString(request, "tabs1", "details");

Account koroneikiAccount = (Account)request.getAttribute(TaprootWebKeys.ACCOUNT);

long accountId = BeanParamUtil.getLong(koroneikiAccount, request, "accountId");
%>

<clay:navigation-bar
	inverted="<%= true %>"
	navigationItems="<%=
		new JSPNavigationItemList(pageContext) {
			{
				add(
					navigationItem -> {
						navigationItem.setActive(tabs1.equals("details"));
						navigationItem.setHref(renderResponse.createRenderURL(), "mvcRenderCommandName", "/accounts_admin/edit_account", "tabs1", "details", "accountId", accountId);
						navigationItem.setLabel(LanguageUtil.get(request, "details"));
					});

				add(
					navigationItem -> {
						navigationItem.setActive(tabs1.equals("child-accounts"));

						if (koroneikiAccount != null) {
							navigationItem.setHref(renderResponse.createRenderURL(), "mvcRenderCommandName", "/accounts_admin/edit_account", "tabs1", "child-accounts", "accountId", accountId);
						}

						navigationItem.setLabel(LanguageUtil.get(request, "child-accounts"));
					});

				add(
					navigationItem -> {
						navigationItem.setActive(tabs1.equals("contact-roles"));

						if (koroneikiAccount != null) {
							navigationItem.setHref(renderResponse.createRenderURL(), "mvcRenderCommandName", "/accounts_admin/edit_account", "tabs1", "contact-roles", "accountId", accountId);
						}

						navigationItem.setLabel(LanguageUtil.get(request, "contact-roles"));
					});

				add(
					navigationItem -> {
						navigationItem.setActive(tabs1.equals("teams"));

						if (koroneikiAccount != null) {
							navigationItem.setHref(renderResponse.createRenderURL(), "mvcRenderCommandName", "/accounts_admin/edit_account", "tabs1", "teams", "accountId", accountId);
						}

						navigationItem.setLabel(LanguageUtil.get(request, "teams"));
					});

				add(
					navigationItem -> {
						navigationItem.setActive(tabs1.equals("assigned-teams"));

						if (koroneikiAccount != null) {
							navigationItem.setHref(renderResponse.createRenderURL(), "mvcRenderCommandName", "/accounts_admin/edit_account", "tabs1", "assigned-teams", "accountId", accountId);
						}

						navigationItem.setLabel(LanguageUtil.get(request, "assigned-teams"));
					});

				add(
					navigationItem -> {
						navigationItem.setActive(tabs1.equals("addresses"));

						if (koroneikiAccount != null) {
							navigationItem.setHref(renderResponse.createRenderURL(), "mvcRenderCommandName", "/accounts_admin/edit_account", "tabs1", "addresses", "accountId", accountId);
						}

						navigationItem.setLabel(LanguageUtil.get(request, "addresses"));
					});

				add(
					navigationItem -> {
						navigationItem.setActive(tabs1.equals("external-links"));

						if (koroneikiAccount != null) {
							navigationItem.setHref(renderResponse.createRenderURL(), "mvcRenderCommandName", "/accounts_admin/edit_account", "tabs1", "external-links", "accountId", accountId);
						}

						navigationItem.setLabel(LanguageUtil.get(request, "external-links"));
					});
			}
		}
	%>"
/>