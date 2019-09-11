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
Team team = (Team)request.getAttribute(TaprootWebKeys.TEAM);

renderResponse.setTitle(team.getName());
%>

<liferay-util:include page="/teams_admin/edit_team_tabs.jsp" servletContext="<%= application %>" />

<clay:management-toolbar
	creationMenu='<%=
		new JSPCreationMenu(pageContext) {
			{
				addDropdownItem(
					dropdownItem -> {
						dropdownItem.setHref(renderResponse.createRenderURL(), "mvcRenderCommandName", "/edit_external_link", "redirect", PortalUtil.getCurrentURL(request), "classNameId", PortalUtil.getClassNameId(Team.class.getName()), "classPK", team.getTeamId(), "title", team.getName());
						dropdownItem.setLabel(LanguageUtil.get(request, "add"));
					});
			}
		}
	%>'
	selectable="<%= false %>"
	showSearch="<%= false %>"
/>

<div class="main-content-body">

	<%
	List<ExternalLink> externalLinks = team.getExternalLinks();
	%>

	<div class="container-fluid-1280">
		<liferay-ui:search-container
			emptyResultsMessage="no-external-links-were-found"
			headerNames="domain,entity-name,entity-id"
			total="<%= externalLinks.size() %>"
		>
			<liferay-ui:search-container-results
				results="<%= externalLinks %>"
			/>

			<liferay-ui:search-container-row
				className="com.liferay.osb.koroneiki.root.model.ExternalLink"
				escapedModel="<%= true %>"
				keyProperty="externalLinkId"
				modelVar="externalLink"
			>
				<liferay-ui:search-container-column-text
					name="domain"
				>
					<span class="lfr-portal-tooltip" data-title="<liferay-ui:message key="external-link" />">
						<aui:icon cssClass="icon-monospaced" image="third-party" markupView="lexicon" />
					</span>

					<%= externalLink.getDomain() %>
				</liferay-ui:search-container-column-text>

				<liferay-ui:search-container-column-text
					name="entity-name"
					value="<%= externalLink.getEntityName() %>"
				/>

				<liferay-ui:search-container-column-text
					name="entity-id"
					value="<%= externalLink.getEntityId() %>"
				/>

				<liferay-ui:search-container-column-jsp
					align="right"
					path="/external_link_action.jsp"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				markupView="lexicon"
				paginate="<%= false %>"
			/>
		</liferay-ui:search-container>
	</div>
</div>