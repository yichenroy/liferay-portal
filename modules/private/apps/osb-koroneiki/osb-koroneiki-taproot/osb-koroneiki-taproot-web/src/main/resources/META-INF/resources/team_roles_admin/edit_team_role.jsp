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

TeamRole teamRole = (TeamRole)request.getAttribute(TaprootWebKeys.TEAM_ROLE);

long teamRoleId = BeanParamUtil.getLong(teamRole, request, "teamRoleId");
int type = BeanParamUtil.getInteger(teamRole, request, "type");
%>

<portlet:actionURL name="/team_roles_admin/edit_team_role" var="editTeamRoleURL" />

<aui:form action="<%= editTeamRoleURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="teamRoleId" type="hidden" value="<%= teamRoleId %>" />

	<liferay-ui:error exception="<%= TeamRoleNameException.class %>" message="please-enter-a-valid-name" />
	<liferay-ui:error exception="<%= TeamRoleTypeException.class %>" message="please-enter-a-valid-type" />

	<aui:model-context bean="<%= teamRole %>" model="<%= TeamRole.class %>" />

	<aui:fieldset-group>
		<aui:fieldset>
			<c:choose>
				<c:when test="<%= teamRole != null %>">
					<aui:input label="key" name="key" type="resource" value="<%= teamRole.getTeamRoleKey() %>" />

					<h5><liferay-ui:message key="type" /></h5>

					<p>
						<liferay-ui:message key="<%= TeamRoleType.getLabel(teamRole.getType()) %>" />
					</p>
				</c:when>
				<c:otherwise>
					<aui:select name="type">

						<%
						for (int curType : TeamRoleType.VALUES) {
						%>

							<aui:option label="<%= TeamRoleType.getLabel(curType) %>" value="<%= curType %>" />

						<%
						}
						%>

					</aui:select>
				</c:otherwise>
			</c:choose>

			<aui:input name="name" />

			<aui:input name="description" type="textarea" />
		</aui:fieldset>
	</aui:fieldset-group>

	<aui:button-row>
		<aui:button type="submit" />

		<aui:button href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>