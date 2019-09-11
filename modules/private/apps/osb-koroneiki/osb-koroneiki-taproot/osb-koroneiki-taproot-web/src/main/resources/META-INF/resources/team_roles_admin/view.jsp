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
List<NavigationItem> navigationItems = new ArrayList<>();

NavigationItem entriesNavigationItem = new NavigationItem();

entriesNavigationItem.setActive(true);
entriesNavigationItem.setHref(StringPool.BLANK);
entriesNavigationItem.setLabel(LanguageUtil.get(request, "team-roles"));

navigationItems.add(entriesNavigationItem);
%>

<clay:navigation-bar
	inverted="<%= true %>"
	navigationItems="<%= navigationItems %>"
/>

<clay:management-toolbar
	creationMenu='<%=
		new JSPCreationMenu(pageContext) {
			{
				addDropdownItem(
					dropdownItem -> {
						dropdownItem.setHref(renderResponse.createRenderURL(), "mvcRenderCommandName", "/team_roles_admin/edit_team_role", "redirect", PortalUtil.getCurrentURL(request));
						dropdownItem.setLabel(LanguageUtil.get(request, "add"));
					});
			}
		}
	%>'
	selectable="<%= false %>"
	showSearch="<%= false %>"
/>

<%
PortletURL portletURL = renderResponse.createRenderURL();
%>

<div class="container-fluid-1280">
	<liferay-ui:search-container
		emptyResultsMessage="no-team-roles-were-found"
		headerNames="name,type"
		iteratorURL="<%= portletURL %>"
		total="<%= TeamRoleLocalServiceUtil.getTeamRolesCount() %>"
	>
		<liferay-ui:search-container-results
			results="<%= TeamRoleLocalServiceUtil.getTeamRoles(searchContainer.getStart(), searchContainer.getEnd()) %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.osb.koroneiki.taproot.model.TeamRole"
			escapedModel="<%= true %>"
			keyProperty="teamRoleId"
			modelVar="teamRole"
		>
			<portlet:renderURL var="rowURL">
				<portlet:param name="mvcRenderCommandName" value="/team_roles_admin/edit_team_role" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="teamRoleId" value="<%= String.valueOf(teamRole.getTeamRoleId()) %>" />
			</portlet:renderURL>

			<liferay-ui:search-container-column-text
				href="<%= rowURL %>"
				name="name"
				value="<%= teamRole.getName() %>"
			/>

			<liferay-ui:search-container-column-text
				href="<%= rowURL %>"
				name="type"
				translate="<%= true %>"
				value="<%= TeamRoleType.getLabel(teamRole.getType()) %>"
			/>

			<liferay-ui:search-container-column-jsp
				align="right"
				path="/team_roles_admin/team_role_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</div>