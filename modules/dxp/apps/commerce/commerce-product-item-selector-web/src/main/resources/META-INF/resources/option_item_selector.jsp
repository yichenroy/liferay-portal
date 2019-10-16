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
CPOptionItemSelectorViewDisplayContext cpOptionItemSelectorViewDisplayContext = (CPOptionItemSelectorViewDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

SearchContainer cpOptionSearchContainer = cpOptionItemSelectorViewDisplayContext.getSearchContainer();

String displayStyle = cpOptionItemSelectorViewDisplayContext.getDisplayStyle();

String itemSelectedEventName = cpOptionItemSelectorViewDisplayContext.getItemSelectedEventName();

PortletURL portletURL = cpOptionItemSelectorViewDisplayContext.getPortletURL();
%>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="cpOptions"
>
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= portletURL %>"
			selectedDisplayStyle="<%= displayStyle %>"
		/>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-sort
			orderByCol="<%= cpOptionItemSelectorViewDisplayContext.getOrderByCol() %>"
			orderByType="<%= cpOptionItemSelectorViewDisplayContext.getOrderByType() %>"
			orderColumns='<%= new String[] {"name"} %>'
			portletURL="<%= portletURL %>"
		/>
	</liferay-frontend:management-bar-filters>
</liferay-frontend:management-bar>

<div class="container-fluid-1280" id="<portlet:namespace />cpOptionSelectorWrapper">
	<liferay-ui:search-container
		id="cpOptions"
		searchContainer="<%= cpOptionSearchContainer %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.commerce.product.model.CPOption"
			cssClass="commerce-product-option-row"
			keyProperty="CPOptionId"
			modelVar="cpOption"
		>
			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="name"
			>
				<div class="commerce-product-option-name"
					data-id="<%= cpOption.getCPOptionId() %>">

					<%= HtmlUtil.escape(cpOption.getName(themeDisplay.getLanguageId())) %>
				</div>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="facetable"
				property="facetable"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="sku-contributor"
				property="skuContributor"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator displayStyle="<%= displayStyle %>" markupView="lexicon" searchContainer="<%= cpOptionSearchContainer %>" />

		<liferay-ui:search-paginator searchContainer="<%= cpOptionSearchContainer %>" />
	</liferay-ui:search-container>
</div>

<aui:script use="liferay-search-container">
	var cpOptionSelectorWrapper = A.one("#<portlet:namespace />cpOptionSelectorWrapper");

	var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />cpOptions');

	searchContainer.on(
		'rowToggled',
		function(event) {
			Liferay.Util.getOpener().Liferay.fire(
				'<%= HtmlUtil.escapeJS(itemSelectedEventName) %>',
				{
					data: Liferay.Util.listCheckedExcept(cpOptionSelectorWrapper, '<portlet:namespace />allRowIds')
				}
			);
		}
	);
</aui:script>