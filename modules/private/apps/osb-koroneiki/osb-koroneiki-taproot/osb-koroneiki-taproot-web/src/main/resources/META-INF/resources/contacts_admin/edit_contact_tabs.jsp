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

Contact koroneikiContact = (Contact)request.getAttribute(TaprootWebKeys.CONTACT);

long contactId = BeanParamUtil.getLong(koroneikiContact, request, "contactId");
%>

<clay:navigation-bar
	inverted="<%= true %>"
	navigationItems="<%=
		new JSPNavigationItemList(pageContext) {
			{
				add(
					navigationItem -> {
						navigationItem.setActive(tabs1.equals("details"));
						navigationItem.setHref(renderResponse.createRenderURL(), "mvcRenderCommandName", "/contacts_admin/edit_contact", "tabs1", "details", "contactId", contactId);
						navigationItem.setLabel(LanguageUtil.get(request, "details"));
					});

				add(
					navigationItem -> {
						navigationItem.setActive(tabs1.equals("accounts"));
						navigationItem.setHref(renderResponse.createRenderURL(), "mvcRenderCommandName", "/contacts_admin/edit_contact", "tabs1", "accounts", "contactId", contactId);
						navigationItem.setLabel(LanguageUtil.get(request, "accounts"));
					});

				add(
					navigationItem -> {
						navigationItem.setActive(tabs1.equals("external-links"));

						if (koroneikiContact != null) {
							navigationItem.setHref(renderResponse.createRenderURL(), "mvcRenderCommandName", "/contacts_admin/edit_contact", "tabs1", "external-links", "contactId", contactId);
						}

						navigationItem.setLabel(LanguageUtil.get(request, "external-links"));
					});
			}
		}
	%>"
/>