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

ContactRole contactRole = (ContactRole)request.getAttribute(TaprootWebKeys.CONTACT_ROLE);

long contactRoleId = BeanParamUtil.getLong(contactRole, request, "contactRoleId");
int type = BeanParamUtil.getInteger(contactRole, request, "type");
%>

<portlet:actionURL name="/contact_roles_admin/edit_contact_role" var="editContactRoleURL" />

<aui:form action="<%= editContactRoleURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="contactRoleId" type="hidden" value="<%= contactRoleId %>" />
	<aui:input name="type" type="hidden" value="<%= type %>" />

	<liferay-ui:error exception="<%= ContactRoleNameException.class %>" message="please-enter-a-valid-name" />
	<liferay-ui:error exception="<%= ContactRoleTypeException.class %>" message="please-enter-a-valid-type" />

	<aui:model-context bean="<%= contactRole %>" model="<%= ContactRole.class %>" />

	<aui:fieldset-group>
		<aui:fieldset>
			<c:if test="<%= contactRole != null %>">
				<aui:input label="key" name="key" type="resource" value="<%= contactRole.getContactRoleKey() %>" />
			</c:if>

			<h5><liferay-ui:message key="type" /></h5>

			<p>
				<liferay-ui:message key="<%= ContactRoleType.getLabel(type) %>" />
			</p>

			<aui:input name="name" />

			<aui:input name="description" type="textarea" />
		</aui:fieldset>
	</aui:fieldset-group>

	<aui:button-row>
		<aui:button type="submit" />

		<aui:button href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>