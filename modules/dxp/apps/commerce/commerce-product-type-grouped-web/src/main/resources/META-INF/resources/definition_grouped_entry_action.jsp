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
String toolbarItem = ParamUtil.getString(request, "toolbarItem");

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

CPDefinitionGroupedEntry cpDefinitionGroupedEntry = null;

if (row != null) {
	cpDefinitionGroupedEntry = (CPDefinitionGroupedEntry)row.getObject();
}
else {
	cpDefinitionGroupedEntry = (CPDefinitionGroupedEntry)request.getAttribute("info_panel.jsp-entry");
}
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<portlet:renderURL var="editURL">
		<portlet:param name="mvcRenderCommandName" value="editCPDefinitionGroupedEntry" />
		<portlet:param name="cpDefinitionId" value="<%= String.valueOf(cpDefinitionGroupedEntry.getCPDefinitionId()) %>" />
		<portlet:param name="cpDefinitionGroupedEntryId" value="<%= String.valueOf(cpDefinitionGroupedEntry.getCPDefinitionGroupedEntryId()) %>" />
		<portlet:param name="toolbarItem" value="<%= toolbarItem %>" />
	</portlet:renderURL>

	<liferay-ui:icon
		message="edit"
		url="<%= editURL %>"
	/>

	<portlet:actionURL name="editCPDefinitionGroupedEntry" var="deleteURL">
		<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="cpDefinitionGroupedEntryId" value="<%= String.valueOf(cpDefinitionGroupedEntry.getCPDefinitionGroupedEntryId()) %>" />
	</portlet:actionURL>

	<liferay-ui:icon-delete
		url="<%= deleteURL %>"
	/>
</liferay-ui:icon-menu>