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

CommerceWarehouseItemDisplayContext commerceWarehouseItemDisplayContext = (CommerceWarehouseItemDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceWarehouseItem commerceWarehouseItem = commerceWarehouseItemDisplayContext.getCommerceWarehouseItem();

CommerceWarehouse commerceWarehouse = commerceWarehouseItem.getCommerceWarehouse();

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(LanguageUtil.format(request, "edit-x", commerceWarehouse.getName(), false));
%>

<portlet:actionURL name="editCommerceWarehouseItem" var="editCommerceWarehouseItemActionURL" />

<aui:form action="<%= editCommerceWarehouseItemActionURL %>" cssClass="container-fluid-1280" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveCommerceWarehouseItem();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="commerceWarehouseItemId" type="hidden" value="<%= commerceWarehouseItem.getCommerceWarehouseItemId() %>" />

	<div class="lfr-form-content">
		<aui:model-context bean="<%= commerceWarehouseItem %>" model="<%= CommerceWarehouseItem.class %>" />

		<aui:fieldset-group markupView="lexicon">
			<aui:fieldset>
				<aui:input name="quantity" />
			</aui:fieldset>
		</aui:fieldset-group>
	</div>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" />

		<aui:button cssClass="btn-lg" href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script>
	function <portlet:namespace />saveCommerceWarehouseItem() {
		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>