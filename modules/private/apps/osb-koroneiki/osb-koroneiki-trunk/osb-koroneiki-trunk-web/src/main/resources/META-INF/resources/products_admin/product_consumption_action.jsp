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

ProductConsumption productConsumption = (ProductConsumption)row.getObject();
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<portlet:renderURL var="editURL">
		<portlet:param name="mvcRenderCommandName" value="/products_admin/edit_product_consumption" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="productConsumptionId" value="<%= String.valueOf(productConsumption.getProductConsumptionId()) %>" />
	</portlet:renderURL>

	<liferay-ui:icon
		message="view"
		url="<%= editURL %>"
	/>

	<portlet:actionURL name="/products_admin/edit_product_consumption" var="deleteURL">
		<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="productConsumptionId" value="<%= String.valueOf(productConsumption.getProductConsumptionId()) %>" />
	</portlet:actionURL>

	<liferay-ui:icon-delete
		confirmation="are-you-sure-you-want-to-delete-this-consumption"
		url="<%= deleteURL %>"
	/>
</liferay-ui:icon-menu>