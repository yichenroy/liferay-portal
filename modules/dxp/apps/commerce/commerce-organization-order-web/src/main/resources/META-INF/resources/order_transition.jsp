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
CommerceOrganizationOrderDisplayContext commerceOrganizationOrderDisplayContext = (CommerceOrganizationOrderDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

CommerceOrder commerceOrder = (CommerceOrder)row.getObject();

List<ObjectValuePair<Long, String>> transitionOVPs = commerceOrganizationOrderDisplayContext.getCommerceOrderTransitionOVPs(commerceOrder);
%>

<c:if test="<%= !transitionOVPs.isEmpty() %>">
	<c:if test="<%= transitionOVPs.size() > 1 %>">
		<div class="btn-group dropdown" role="group">
	</c:if>

	<%
	ObjectValuePair<Long, String> firstTransitionOVP = transitionOVPs.get(0);
	%>

	<button class="btn btn-secondary transition-link" data-commerceOrderId="<%= commerceOrder.getCommerceOrderId() %>" data-transitionName="<%= firstTransitionOVP.getValue() %>" data-workflowTaskId="<%= firstTransitionOVP.getKey() %>" type="button">
		<%= commerceOrganizationOrderDisplayContext.getCommerceOrderTransitionMessage(firstTransitionOVP.getValue()) %>
	</button>

	<c:if test="<%= transitionOVPs.size() > 1 %>">
		<button aria-expanded="false" aria-haspopup="true" class="btn btn-monospaced btn-secondary dropdown-toggle" data-toggle="dropdown" type="button">
			<clay:icon symbol="caret-bottom" />
		</button>

		<div class="dropdown-menu dropdown-menu-right">

			<%
			for (int i = 1; i < transitionOVPs.size(); i++) {
				ObjectValuePair<Long, String> transitionOVP = transitionOVPs.get(i);

				String transitionName = transitionOVP.getValue();
			%>

				<a class="dropdown-item transition-link" data-commerceOrderId="<%= commerceOrder.getCommerceOrderId() %>" data-transitionName="<%= transitionName %>" data-workflowTaskId="<%= firstTransitionOVP.getKey() %>" href="javascript:;"><%= commerceOrganizationOrderDisplayContext.getCommerceOrderTransitionMessage(transitionName) %></a>

			<%
			}
			%>

		</div>
	</c:if>
</c:if>