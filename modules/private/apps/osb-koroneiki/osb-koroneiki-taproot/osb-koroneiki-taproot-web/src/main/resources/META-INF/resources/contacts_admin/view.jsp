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
ContactsDisplayContext contactsDisplayContext = new ContactsDisplayContext(renderRequest, renderResponse, request);

ViewContactsManagementToolbarDisplayContext viewContactsManagementToolbarDisplayContext = new ViewContactsManagementToolbarDisplayContext(liferayPortletRequest, liferayPortletResponse, request, contactsDisplayContext.getSearchContainer());
%>

<clay:navigation-bar
	inverted="<%= true %>"
	navigationItems="<%= viewContactsManagementToolbarDisplayContext.getNavigationItems() %>"
/>

<clay:management-toolbar
	displayContext="<%= viewContactsManagementToolbarDisplayContext %>"
/>

<div class="container-fluid-1280">
	<liferay-ui:search-container
		searchContainer="<%= contactsDisplayContext.getSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.osb.koroneiki.taproot.model.Contact"
			escapedModel="<%= true %>"
			keyProperty="contactId"
			modelVar="koroneikiContact"
		>
			<portlet:renderURL var="rowURL">
				<portlet:param name="mvcRenderCommandName" value="/contacts_admin/edit_contact" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="contactId" value="<%= String.valueOf(koroneikiContact.getContactId()) %>" />
			</portlet:renderURL>

			<liferay-ui:search-container-column-text
				href="<%= rowURL %>"
				name="name"
			>
				<span class="lfr-portal-tooltip" data-title="<liferay-ui:message key="user" />">
					<aui:icon cssClass="icon-monospaced" image="user" markupView="lexicon" />
				</span>

				<%= HtmlUtil.escape(koroneikiContact.getFullName()) %>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				href="<%= rowURL %>"
				name="email-address"
				value="<%= koroneikiContact.getEmailAddress() %>"
			/>

			<liferay-ui:search-container-column-text
				href="<%= rowURL %>"
				name="language"
				value="<%= koroneikiContact.getLanguageId() %>"
			/>

			<liferay-ui:search-container-column-jsp
				align="right"
				path="/contacts_admin/contact_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</div>