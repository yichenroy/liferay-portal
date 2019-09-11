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

Team team = (Team)request.getAttribute(TaprootWebKeys.TEAM);

long teamId = BeanParamUtil.getLong(team, request, "teamId");
%>

<liferay-util:include page="/teams_admin/edit_team_tabs.jsp" servletContext="<%= application %>" />

<portlet:actionURL name="/teams_admin/edit_team" var="editTeamURL" />

<aui:form action="<%= editTeamURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="teamId" type="hidden" value="<%= teamId %>" />

	<liferay-ui:error exception="<%= NoSuchAccountException.class %>" message="please-select-an-account" />
	<liferay-ui:error exception="<%= TeamNameException.class %>" message="please-enter-a-valid-name" />

	<aui:model-context bean="<%= team %>" model="<%= Team.class %>" />

	<aui:fieldset-group>
		<aui:fieldset>
			<c:if test="<%= team != null %>">
				<aui:input label="key" name="key" type="resource" value="<%= team.getTeamKey() %>" />
			</c:if>

			<aui:input name="name" />

			<c:choose>
				<c:when test="<%= team == null %>">
					<aui:select label="account" name="accountId">
						<aui:option value="" />

						<%
						for (Account koroneikiAccount : AccountLocalServiceUtil.getAccounts(QueryUtil.ALL_POS, QueryUtil.ALL_POS)) {
						%>

							<aui:option label="<%= koroneikiAccount.getName() %>" value="<%= koroneikiAccount.getAccountId() %>" />

						<%
						}
						%>

					</aui:select>
				</c:when>
				<c:otherwise>
					<h5><liferay-ui:message key="account" /></h5>

					<p>

						<%
						Account koroneikiAccount = team.getAccount();
						%>

						<liferay-portlet:renderURL portletName="<%= TaprootPortletKeys.ACCOUNTS_ADMIN %>" var="accountURL">
							<portlet:param name="mvcRenderCommandName" value="/accounts_admin/edit_account" />
							<portlet:param name="accountId" value="<%= String.valueOf(koroneikiAccount.getAccountId()) %>" />
						</liferay-portlet:renderURL>

						<a href="<%= accountURL %>"><%= HtmlUtil.escape(koroneikiAccount.getName()) %></a>
					</p>
				</c:otherwise>
			</c:choose>
		</aui:fieldset>
	</aui:fieldset-group>

	<aui:button-row>
		<aui:button type="submit" />

		<aui:button href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>