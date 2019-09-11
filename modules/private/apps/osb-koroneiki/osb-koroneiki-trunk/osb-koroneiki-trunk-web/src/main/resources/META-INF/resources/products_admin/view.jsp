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
String tabs1 = ParamUtil.getString(request, "tabs1", "products");
%>

<c:choose>
	<c:when test='<%= tabs1.equals("consumption") %>'>
		<liferay-util:include page="/products_admin/view_product_consumptions.jsp" servletContext="<%= application %>" />
	</c:when>
	<c:when test='<%= tabs1.equals("purchases") %>'>
		<liferay-util:include page="/products_admin/view_product_purchases.jsp" servletContext="<%= application %>" />
	</c:when>
	<c:otherwise>
		<liferay-util:include page="/products_admin/view_product_entries.jsp" servletContext="<%= application %>" />
	</c:otherwise>
</c:choose>