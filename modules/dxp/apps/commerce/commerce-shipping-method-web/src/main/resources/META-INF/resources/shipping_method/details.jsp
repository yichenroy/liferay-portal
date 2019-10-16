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
CommerceShippingMethodsDisplayContext commerceShippingMethodsDisplayContext = (CommerceShippingMethodsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceShippingMethod commerceShippingMethod = commerceShippingMethodsDisplayContext.getCommerceShippingMethod();
%>

<liferay-ui:error-marker key="<%= WebKeys.ERROR_SECTION %>" value="details" />

<liferay-ui:error exception="<%= CommerceShippingMethodNameException.class %>" message="please-enter-a-valid-name" />

<aui:model-context bean="<%= commerceShippingMethod %>" model="<%= CommerceShippingMethod.class %>" />

<aui:fieldset>
	<aui:input name="name" />

	<aui:input name="description" />

	<aui:input name="priority" />

	<aui:input name="active" />
</aui:fieldset>