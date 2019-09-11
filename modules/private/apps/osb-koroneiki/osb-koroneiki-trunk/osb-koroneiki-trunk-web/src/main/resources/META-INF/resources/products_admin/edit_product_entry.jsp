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
String redirect = ParamUtil.getString(request, "redirect");

ProductEntry productEntry = (ProductEntry)request.getAttribute(TrunkWebKeys.PRODUCT_ENTRY);

long productEntryId = BeanParamUtil.getLong(productEntry, request, "productEntryId");

renderResponse.setTitle((productEntry == null) ? LanguageUtil.get(request, "new-product") : productEntry.getName());
%>

<liferay-util:include page="/products_admin/edit_product_entry_tabs.jsp" servletContext="<%= application %>" />

<portlet:actionURL name="/products_admin/edit_product_entry" var="editProductEntryURL" />

<aui:form action="<%= editProductEntryURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="productEntryId" type="hidden" value="<%= productEntryId %>" />

	<liferay-ui:error exception="<%= ProductEntryNameException.class %>" message="please-enter-a-valid-name" />

	<aui:model-context bean="<%= productEntry %>" model="<%= ProductEntry.class %>" />

	<aui:fieldset-group>
		<aui:fieldset>
			<c:if test="<%= productEntry != null %>">
				<aui:input label="key" name="key" type="resource" value="<%= productEntry.getProductEntryKey() %>" />
			</c:if>

			<aui:input name="name" />
		</aui:fieldset>
	</aui:fieldset-group>

	<aui:button-row>
		<aui:button type="submit" />

		<aui:button href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>