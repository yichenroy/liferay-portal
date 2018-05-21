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
String redirect = ParamUtil.getString(request, "redirect");

String modelResource = ParamUtil.getString(request, "modelResource");
String modelResourceName = ResourceActionsUtil.getModelResource(request, modelResource);
String displayStyle = ParamUtil.getString(request, "displayStyle", "list");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcPath", "/view_attributes.jsp");
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("modelResource", modelResource);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(modelResourceName);

ExpandoBridge expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(company.getCompanyId(), modelResource);

List<String> attributeNames = Collections.list(expandoBridge.getAttributeNames());

ExpandoDisplayContext expandoDisplayContext = new ExpandoDisplayContext(request);
%>

<clay:navigation-bar
	inverted="<%= true %>"
	navigationItems='<%= expandoDisplayContext.getNavigationItems("fields") %>'
/>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="customFields"
>
	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= PortletURLUtil.clone(portletURL, renderResponse) %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= PortletURLUtil.clone(portletURL, renderResponse) %>"
			selectedDisplayStyle="<%= displayStyle %>"
		/>

		<c:if test="<%= PortletPermissionUtil.contains(permissionChecker, ExpandoPortletKeys.EXPANDO, ActionKeys.ADD_EXPANDO) %>">
			<portlet:renderURL var="addExpandoURL">
				<portlet:param name="mvcPath" value="/edit_expando.jsp" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="modelResource" value="<%= modelResource %>" />
			</portlet:renderURL>

			<liferay-frontend:add-menu
				inline="<%= true %>"
			>
				<liferay-frontend:add-menu-item
					title='<%= LanguageUtil.get(request, "add-custom-field") %>'
					url="<%= addExpandoURL.toString() %>"
				/>
			</liferay-frontend:add-menu>
		</c:if>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-action-buttons>
		<aui:a cssClass="btn" href="javascript:;" iconCssClass="icon-trash" id="deleteCustomFields" />
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<aui:form action="<%= portletURL.toString() %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= portletURL.toString() %>" />
	<aui:input name="columnIds" type="hidden" />

	<liferay-ui:search-container
		emptyResultsMessage='<%= LanguageUtil.format(request, "no-custom-fields-are-defined-for-x", modelResourceName, false) %>'
		id="customFields"
		iteratorURL="<%= portletURL %>"
		rowChecker="<%= new CustomFieldChecker(renderRequest, renderResponse) %>"
		total="<%= attributeNames.size() %>"
	>
		<liferay-ui:search-container-results
			results="<%= attributeNames %>"
		/>

		<liferay-ui:search-container-row
			className="java.lang.String"
			modelVar="name"
			stringKey="<%= true %>"
		>

			<%
			int type = expandoBridge.getAttributeType(name);

			ExpandoColumn expandoColumn = ExpandoColumnLocalServiceUtil.getDefaultTableColumn(company.getCompanyId(), modelResource, name);

			UnicodeProperties typeSettings = expandoColumn.getTypeSettingsProperties();
			%>

			<portlet:renderURL var="rowURL">
				<portlet:param name="mvcPath" value="/edit_expando.jsp" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="columnId" value="<%= String.valueOf(expandoColumn.getColumnId()) %>" />
				<portlet:param name="modelResource" value="<%= modelResource %>" />
			</portlet:renderURL>

			<liferay-ui:search-container-row-parameter
				name="expandoColumn"
				value="<%= expandoColumn %>"
			/>

			<liferay-ui:search-container-row-parameter
				name="modelResource"
				value="<%= modelResource %>"
			/>

			<%@ include file="/attribute_columns.jspf" %>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
			paginate="<%= false %>"
		/>
	</liferay-ui:search-container>
</aui:form>

<aui:script>
	var deleteCustomFields = document.getElementById('<portlet:namespace />deleteCustomFields');

	if (deleteCustomFields) {
		var form = document.getElementById('<portlet:namespace />fm');

		deleteCustomFields.addEventListener(
		'click',
		function() {
			if (form) {
				var columnIds = form.querySelector('#<portlet:namespace />columnIds');

				if (columnIds) {
					var checkedIds = Liferay.Util.listCheckedExcept(form, '<portlet:namespace />allRowIds');

					columnIds.setAttribute('value', checkedIds);

					submitForm(form, '<portlet:actionURL name="deleteExpandos" />');
				}
			}
		}
	);
</aui:script>

<%
PortalUtil.addPortletBreadcrumbEntry(request, modelResourceName, portletURL.toString());
%>