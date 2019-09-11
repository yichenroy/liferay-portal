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
	componentId="contactsManagementToolbar"
	creationMenu='<%=
		new JSPCreationMenu(pageContext) {
			{
				addDropdownItem(
					dropdownItem -> {
						dropdownItem.setLabel(LanguageUtil.get(request, "assign-contact"));
					});
			}
		}
	%>'
	selectable="<%= false %>"
	showCreationMenu="<%= true %>"
	showSearch="<%= false %>"
/>

<div class="container-fluid-1280">
	<liferay-ui:search-container
		emptyResultsMessage="no-contacts-were-found"
		headerNames="first-name,last-name,email-address"
		total="<%= ContactLocalServiceUtil.getTeamContactsCount(team.getTeamId()) %>"
	>
		<liferay-ui:search-container-results
			results="<%= ContactLocalServiceUtil.getTeamContacts(team.getTeamId(), searchContainer.getStart(), searchContainer.getEnd()) %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.osb.koroneiki.taproot.model.Contact"
			escapedModel="<%= true %>"
			keyProperty="contactId"
			modelVar="koroneikiContact"
		>
			<liferay-portlet:renderURL portletName="<%= TaprootPortletKeys.CONTACTS_ADMIN %>" var="rowURL">
				<portlet:param name="mvcRenderCommandName" value="/contacts_admin/edit_contact" />
				<portlet:param name="contactId" value="<%= String.valueOf(koroneikiContact.getContactId()) %>" />
			</liferay-portlet:renderURL>

			<liferay-ui:search-container-column-text
				href="<%= rowURL %>"
				name="name"
			>
				<span class="lfr-portal-tooltip" data-title="<liferay-ui:message key="user" />">
					<aui:icon cssClass="icon-monospaced" image="user" markupView="lexicon" />
				</span>

				<%= koroneikiContact.getFullName() %>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				href="<%= rowURL %>"
				name="email-address"
				value="<%= koroneikiContact.getEmailAddress() %>"
			/>

			<liferay-ui:search-container-column-text
				href="<%= rowURL %>"
				name="roles"
			>

				<%
				List<ContactRole> contactRoles = ContactRoleLocalServiceUtil.getContactTeamContactRoles(team.getTeamId(), koroneikiContact.getContactId());
				%>

				<%= ListUtil.toString(contactRoles, ContactRole.NAME_ACCESSOR, StringPool.COMMA_AND_SPACE) %>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-jsp
				align="right"
				path="/teams_admin/team_contact_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</div>

<aui:script require="metal-dom/src/all/dom as dom,frontend-js-web/liferay/modal/commands/OpenSimpleInputModal.es as modalCommands">
	function handleAddClick(event) {
		event.preventDefault();

		<portlet:actionURL name="/teams_admin/assign_team_contact" var="assignTeamContactURL">
			<portlet:param name="teamId" value="<%= String.valueOf(team.getTeamId()) %>" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
		</portlet:actionURL>

		modalCommands.openSimpleInputModal(
			{
				dialogTitle: '<liferay-ui:message key="assign-contact-to-this-team" />',
				formSubmitURL: '<%= assignTeamContactURL %>',
				mainFieldLabel: '<liferay-ui:message key="email-address" />',
				mainFieldName: 'emailAddress',
				mainFieldPlaceholder: '<liferay-ui:message key="email-address" />',
				namespace: '<portlet:namespace />',
				spritemap: '<%= themeDisplay.getPathThemeImages() %>/lexicon/icons.svg'
			}
		);
	}

	Liferay.componentReady('contactsManagementToolbar').then(
		function(managementToolbar) {
			managementToolbar.on('creationButtonClicked', handleAddClick);
		}
	);
</aui:script>