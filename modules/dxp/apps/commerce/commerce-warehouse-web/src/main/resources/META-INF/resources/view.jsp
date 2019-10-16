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
CommerceWarehousesDisplayContext commerceWarehousesDisplayContext = (CommerceWarehousesDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

long commerceCountryId = commerceWarehousesDisplayContext.getCommerceCountryId();
List<ManagementBarFilterItem> managementBarFilterItems = commerceWarehousesDisplayContext.getManagementBarFilterItems();

String managementBarFilterValue = null;

for (ManagementBarFilterItem managementBarFilterItem : managementBarFilterItems) {
	if (commerceCountryId == Long.valueOf(managementBarFilterItem.getId())) {
		managementBarFilterValue = managementBarFilterItem.getLabel();

		break;
	}
}
%>

<liferay-frontend:management-bar
	searchContainerId="commerceWarehouses"
>
	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-filter
			label="country"
			managementBarFilterItems="<%= managementBarFilterItems %>"
			value="<%= managementBarFilterValue %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= commerceWarehousesDisplayContext.getOrderByCol() %>"
			orderByType="<%= commerceWarehousesDisplayContext.getOrderByType() %>"
			orderColumns='<%= new String[] {"city", "name"} %>'
			portletURL="<%= commerceWarehousesDisplayContext.getPortletURL() %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= renderResponse.createRenderURL() %>"
			selectedDisplayStyle="list"
		/>
	</liferay-frontend:management-bar-buttons>
</liferay-frontend:management-bar>

<div class="container-fluid-1280">
	<liferay-ui:search-container
		id="commerceWarehouses"
		searchContainer="<%= commerceWarehousesDisplayContext.getSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.commerce.model.CommerceWarehouse"
			keyProperty="commerceWarehouseId"
			modelVar="commerceWarehouse"
		>

			<%
			PortletURL rowURL = renderResponse.createRenderURL();

			rowURL.setParameter("mvcRenderCommandName", "editCommerceWarehouse");
			rowURL.setParameter("redirect", currentURL);
			rowURL.setParameter("commerceWarehouseId", String.valueOf(commerceWarehouse.getCommerceWarehouseId()));
			%>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				href="<%= rowURL %>"
				property="name"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				property="city"
			/>

			<liferay-ui:search-container-column-jsp
				cssClass="entry-action-column"
				path="/warehouse_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator markupView="lexicon" />
	</liferay-ui:search-container>
</div>

<c:if test="<%= commerceWarehousesDisplayContext.isShowAddButton() %>">
	<portlet:renderURL var="addCommerceWarehouseURL">
		<portlet:param name="mvcRenderCommandName" value="editCommerceWarehouse" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="commerceCountryId" value="<%= String.valueOf(commerceCountryId) %>" />
	</portlet:renderURL>

	<liferay-frontend:add-menu>
		<liferay-frontend:add-menu-item title='<%= LanguageUtil.get(request, "add-warehouse") %>' url="<%= addCommerceWarehouseURL.toString() %>" />
	</liferay-frontend:add-menu>
</c:if>