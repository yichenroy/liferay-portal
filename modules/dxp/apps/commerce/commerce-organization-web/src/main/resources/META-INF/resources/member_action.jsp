<%--
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
--%>

<%@ include file="/init.jsp" %>

<%
CommerceOrganizationMembersDisplayContext commerceOrganizationMembersDisplayContext = (CommerceOrganizationMembersDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

Organization organization = commerceOrganizationMembersDisplayContext.getCurrentOrganization();

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

User organizationUser = (User)row.getObject();

String editURL = commerceOrganizationMembersDisplayContext.getEditURL(organizationUser);
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<c:if test="<%= Validator.isNotNull(editURL) %>">
		<liferay-ui:icon
			message="edit"
			url="<%= editURL %>"
		/>
	</c:if>

	<c:choose>
		<c:when test="<%= organizationUser.isActive() %>">
			<portlet:actionURL name="editUser" var="deactivateURL">
				<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DEACTIVATE %>" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="userId" value="<%= String.valueOf(organizationUser.getUserId()) %>" />
			</portlet:actionURL>

			<liferay-ui:icon-deactivate
				url="<%= deactivateURL %>"
			/>
		</c:when>
		<c:otherwise>
			<portlet:actionURL name="editUser" var="restoreURL">
				<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.RESTORE %>" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="userId" value="<%= String.valueOf(organizationUser.getUserId()) %>" />
			</portlet:actionURL>

			<liferay-ui:icon
				message="activate"
				url="<%= restoreURL %>"
			/>
		</c:otherwise>
	</c:choose>

	<portlet:actionURL name="inviteUser" var="deleteURL">
		<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.REMOVE %>" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="organizationId" value="<%= String.valueOf(organization.getOrganizationId()) %>" />
		<portlet:param name="userId" value="<%= String.valueOf(organizationUser.getUserId()) %>" />
	</portlet:actionURL>

	<liferay-ui:icon-delete
		message="delete"
		url="<%= deleteURL %>"
	/>
</liferay-ui:icon-menu>