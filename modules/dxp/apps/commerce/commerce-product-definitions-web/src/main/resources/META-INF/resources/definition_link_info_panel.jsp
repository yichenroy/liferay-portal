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
List<CPDefinitionLink> cpDefinitionLinks = (List<CPDefinitionLink>)request.getAttribute(CPWebKeys.CP_DEFINITION_LINKS);

if (ListUtil.isEmpty(cpDefinitionLinks)) {
	cpDefinitionLinks = Collections.emptyList();
}
%>

<c:choose>
	<c:when test="<%= cpDefinitionLinks.size() == 1 %>">

		<%
		CPDefinitionLink cpDefinitionLink = cpDefinitionLinks.get(0);

		request.setAttribute("info_panel.jsp-entry", cpDefinitionLink);

		CPDefinition cpDefinitionEntry = cpDefinitionLink.getCPDefinitionEntry();
		%>

		<div class="sidebar-header">
			<ul class="sidebar-header-actions">
				<li>
					<liferay-util:include
						page="/definition_link_action.jsp"
						servletContext="<%= application %>"
					/>
				</li>
			</ul>

			<h4><%= HtmlUtil.escape(cpDefinitionEntry.getTitle(languageId)) %></h4>
		</div>

		<aui:nav-bar markupView="lexicon">
			<aui:nav cssClass="navbar-nav">
				<aui:nav-item label="details" selected="<%= true %>" />
			</aui:nav>
		</aui:nav-bar>

		<div class="sidebar-body">
			<h5><liferay-ui:message key="product-id" /></h5>

			<p>
				<%= HtmlUtil.escape(String.valueOf(cpDefinitionEntry.getCPDefinitionId())) %>
			</p>
		</div>
	</c:when>
	<c:otherwise>
		<aui:nav-bar>
			<aui:nav cssClass="navbar-nav">
				<aui:nav-item label="details" selected="<%= true %>" />
			</aui:nav>
		</aui:nav-bar>

		<div class="sidebar-header">
			<h4><liferay-ui:message arguments="<%= cpDefinitionLinks.size() %>" key="x-items-are-selected" /></h4>
		</div>
	</c:otherwise>
</c:choose>