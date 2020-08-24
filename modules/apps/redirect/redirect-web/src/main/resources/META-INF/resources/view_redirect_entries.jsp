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
StagingGroupHelper stagingGroupHelper = StagingGroupHelperUtil.getStagingGroupHelper();

boolean stagingGroup = stagingGroupHelper.isLocalStagingGroup(themeDisplay.getScopeGroup()) || stagingGroupHelper.isRemoteStagingGroup(themeDisplay.getScopeGroup());

RedirectDisplayContext redirectDisplayContext = new RedirectDisplayContext(request, liferayPortletRequest, liferayPortletResponse);

SearchContainer<RedirectEntry> redirectSearchContainer = redirectDisplayContext.searchContainer();

RedirectManagementToolbarDisplayContext redirectManagementToolbarDisplayContext = new RedirectManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, redirectSearchContainer);
%>

<c:if test="<%= !stagingGroup %>">
	<clay:management-toolbar
		displayContext="<%= redirectManagementToolbarDisplayContext %>"
	/>
</c:if>

<clay:container-fluid
	cssClass="closed redirect-entries sidenav-container sidenav-right"
	id='<%= liferayPortletResponse.getNamespace() + "infoPanelId" %>'
>
	<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="/redirect/info_panel" var="sidebarPanelURL" />

	<liferay-frontend:sidebar-panel
		resourceURL="<%= sidebarPanelURL %>"
		searchContainerId="<%= redirectDisplayContext.getSearchContainerId() %>"
	>
		<liferay-util:include page="/info_panel.jsp" servletContext="<%= application %>" />
	</liferay-frontend:sidebar-panel>

	<div class="sidenav-content">
		<c:if test="<%= stagingGroup %>">
			<div class="lfr-search-container">
				<clay:alert
					displayType="info"
					message="redirections-are-unavailable-in-staged-sites"
				/>
			</div>
		</c:if>

		<aui:form action="<%= redirectSearchContainer.getIteratorURL() %>" cssClass="container-fluid-1280" name="fm">
			<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

			<liferay-ui:search-container
				id="<%= redirectDisplayContext.getSearchContainerId() %>"
				searchContainer="<%= redirectSearchContainer %>"
			>
				<liferay-ui:search-container-row
					className="com.liferay.redirect.model.RedirectEntry"
					keyProperty="redirectEntryId"
					modelVar="redirectEntry"
				>

					<%
					row.setData(
						HashMapBuilder.<String, Object>put(
							"actions", redirectManagementToolbarDisplayContext.getAvailableActions(redirectEntry)
						).build());
					%>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-content"
						name="source-url"
					>

						<%
						String sourceURL = RedirectUtil.getGroupBaseURL(themeDisplay) + StringPool.SLASH + redirectEntry.getSourceURL();
						%>

						<span data-title="<%= sourceURL %>">
							<%= HtmlUtil.escape(sourceURL) %>
						</span>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-content"
						name="destination-url"
					>

						<%
						String destinationURL = HtmlUtil.escape(redirectEntry.getDestinationURL());
						%>

						<span data-title="<%= destinationURL %>">
							<%= destinationURL %>
						</span>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand-smallest"
						name="type"
					>
						<liferay-ui:message key='<%= redirectEntry.isPermanent() ? "permanent" : "temporary" %>' />
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand-smallest"
						name="expiration"
					>
						<c:choose>
							<c:when test="<%= Validator.isNull(redirectEntry.getExpirationDate()) %>">
								<%= StringPool.DASH %>
							</c:when>
							<c:when test="<%= DateUtil.compareTo(redirectEntry.getExpirationDate(), DateUtil.newDate()) <= 0 %>">
								<strong><liferay-ui:message key="expired" /></strong>
							</c:when>
							<c:otherwise>
								<%= redirectDisplayContext.formatExpirationDate(redirectEntry.getExpirationDate()) %>
							</c:otherwise>
						</c:choose>
					</liferay-ui:search-container-column-text>

					<%
					List<DropdownItem> dropdownItems = redirectDisplayContext.getActionDropdownItems(redirectEntry);
					%>

					<c:if test="<%= ListUtil.isNotEmpty(dropdownItems) %>">
						<liferay-ui:search-container-column-text>
							<clay:dropdown-actions
								dropdownItems="<%= dropdownItems %>"
							/>
						</liferay-ui:search-container-column-text>
					</c:if>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator
					markupView="lexicon"
					searchContainer="<%= redirectSearchContainer %>"
				/>
			</liferay-ui:search-container>
		</aui:form>
	</div>
</clay:container-fluid>

<liferay-frontend:component
	componentId="<%= redirectManagementToolbarDisplayContext.getDefaultEventHandler() %>"
	context="<%= redirectManagementToolbarDisplayContext.getComponentContext() %>"
	module="js/RedirectManagementToolbarDefaultEventHandler.es"
/>

<aui:script require="metal-dom/src/all/dom as dom">
	dom.delegate(
		document.querySelector('#<portlet:namespace />fm'),
		'click',
		'.icon-shortcut',
		function (event) {
			var delegateTarget = event.delegateTarget;

			var destinationURL = delegateTarget.dataset.href;

			if (destinationURL) {
				window.open(destinationURL, '_blank');
			}
		}
	);
</aui:script>