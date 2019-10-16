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
CPTaxCategoryDisplayContext cpTaxCategoryDisplayContext = (CPTaxCategoryDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="cpTaxCategories"
>
	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= cpTaxCategoryDisplayContext.getPortletURL() %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= cpTaxCategoryDisplayContext.getOrderByCol() %>"
			orderByType="<%= cpTaxCategoryDisplayContext.getOrderByType() %>"
			orderColumns='<%= new String[] {"create-date"} %>'
			portletURL="<%= cpTaxCategoryDisplayContext.getPortletURL() %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= cpTaxCategoryDisplayContext.getPortletURL() %>"
			selectedDisplayStyle="list"
		/>

		<c:if test="<%= hasManageCPTaxCategoriesPermission %>">
			<portlet:renderURL var="addCPTaxCategoryURL">
				<portlet:param name="mvcRenderCommandName" value="editCPTaxCategory" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
			</portlet:renderURL>

			<liferay-frontend:add-menu
				inline="<%= true %>"
			>
				<liferay-frontend:add-menu-item
					title='<%= LanguageUtil.get(request, "add-tax-category") %>'
					url="<%= addCPTaxCategoryURL.toString() %>"
				/>
			</liferay-frontend:add-menu>
		</c:if>
	</liferay-frontend:management-bar-buttons>

	<c:if test="<%= hasManageCPTaxCategoriesPermission %>">
		<liferay-frontend:management-bar-action-buttons>
			<liferay-frontend:management-bar-button
				href='<%= "javascript:" + renderResponse.getNamespace() + "deleteCPTaxCategories();" %>'
				icon="times"
				label="delete"
			/>
		</liferay-frontend:management-bar-action-buttons>
	</c:if>
</liferay-frontend:management-bar>

<div class="container-fluid-1280">
	<portlet:actionURL name="editCPTaxCategory" var="editCPTaxCategoryActionURL" />

	<aui:form action="<%= editCPTaxCategoryActionURL %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.DELETE %>" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="deleteCPTaxCategoryIds" type="hidden" />

		<liferay-ui:search-container
			id="cpTaxCategories"
			searchContainer="<%= cpTaxCategoryDisplayContext.getSearchContainer() %>"
		>
			<liferay-ui:search-container-row
				className="com.liferay.commerce.product.model.CPTaxCategory"
				keyProperty="CPTaxCategoryId"
				modelVar="cpTaxCategory"
			>

				<%
				PortletURL rowURL = renderResponse.createRenderURL();

				rowURL.setParameter("mvcRenderCommandName", "editCPTaxCategory");
				rowURL.setParameter("redirect", currentURL);
				rowURL.setParameter("cpTaxCategoryId", String.valueOf(cpTaxCategory.getCPTaxCategoryId()));
				%>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-content"
					href="<%= rowURL %>"
					name="name"
					value="<%= HtmlUtil.escape(cpTaxCategory.getName(languageId)) %>"
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-content"
					name="description"
					value="<%= HtmlUtil.escape(cpTaxCategory.getDescription(languageId)) %>"
				/>

				<liferay-ui:search-container-column-date
					cssClass="table-cell-content"
					name="create-date"
					property="createDate"
				/>

				<liferay-ui:search-container-column-jsp
					cssClass="entry-action-column"
					path="/tax_category_action.jsp"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				markupView="lexicon"
			/>
		</liferay-ui:search-container>
	</aui:form>
</div>

<aui:script>
	function <portlet:namespace />deleteCPTaxCategories() {
		if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-the-selected-tax-categories" />')) {
			var form = AUI.$(document.<portlet:namespace />fm);

			form.fm('deleteCPTaxCategoryIds').val(Liferay.Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));

			submitForm(form);
		}
	}
</aui:script>