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
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
Account koroneikiAccount = (Account)request.getAttribute(TaprootWebKeys.ACCOUNT);

Contact koroneikiContact = (Contact)row.getObject();
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<portlet:renderURL var="assignURL">
		<portlet:param name="mvcRenderCommandName" value="/accounts_admin/assign_account_contact_roles" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="accountId" value="<%= String.valueOf(koroneikiAccount.getAccountId()) %>" />
		<portlet:param name="contactId" value="<%= String.valueOf(koroneikiContact.getContactId()) %>" />
	</portlet:renderURL>

	<liferay-ui:icon
		message="assign-contact-roles"
		url="<%= assignURL %>"
	/>

	<portlet:actionURL name="/accounts_admin/unassign_account_contact" var="deleteURL">
		<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="accountId" value="<%= String.valueOf(koroneikiAccount.getAccountId()) %>" />
		<portlet:param name="contactId" value="<%= String.valueOf(koroneikiContact.getContactId()) %>" />
	</portlet:actionURL>

	<liferay-ui:icon-delete
		confirmation="are-you-sure-you-want-to-remove-this-contact"
		message="remove-membership"
		url="<%= deleteURL %>"
	/>
</liferay-ui:icon-menu>