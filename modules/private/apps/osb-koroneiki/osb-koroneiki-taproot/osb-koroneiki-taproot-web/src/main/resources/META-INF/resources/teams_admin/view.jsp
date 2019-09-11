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
TeamsDisplayContext teamsDisplayContext = new TeamsDisplayContext(renderRequest, renderResponse, request);

ViewTeamsManagementToolbarDisplayContext viewTeamsManagementToolbarDisplayContext = new ViewTeamsManagementToolbarDisplayContext(liferayPortletRequest, liferayPortletResponse, request, teamsDisplayContext.getSearchContainer());
%>

<clay:navigation-bar
	inverted="<%= true %>"
	navigationItems="<%= viewTeamsManagementToolbarDisplayContext.getNavigationItems() %>"
/>

<clay:management-toolbar
	displayContext="<%= viewTeamsManagementToolbarDisplayContext %>"
/>

<div class="container-fluid-1280">
	<liferay-ui:search-container
		searchContainer="<%= teamsDisplayContext.getSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.osb.koroneiki.taproot.model.Team"
			escapedModel="<%= true %>"
			keyProperty="teamId"
			modelVar="team"
		>
			<portlet:renderURL var="rowURL">
				<portlet:param name="mvcRenderCommandName" value="/teams_admin/edit_team" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="teamId" value="<%= String.valueOf(team.getTeamId()) %>" />
			</portlet:renderURL>

			<liferay-ui:search-container-column-text
				href="<%= rowURL %>"
				name="name"
			>
				<span class="lfr-portal-tooltip" data-title="<liferay-ui:message key="team" />">
					<aui:icon cssClass="icon-monospaced" image="community" markupView="lexicon" />
				</span>

				<%= team.getName() %>
			</liferay-ui:search-container-column-text>

			<%
			Account koroneikiAccount = team.getAccount();
			%>

			<liferay-ui:search-container-column-text
				href="<%= rowURL %>"
				name="account"
				value="<%= HtmlUtil.escape(koroneikiAccount.getName()) %>"
			/>

			<liferay-ui:search-container-column-jsp
				align="right"
				path="/teams_admin/team_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</div>