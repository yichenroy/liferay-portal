<%
/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
%>

<%@ include file="/html/portlet/enterprise_admin/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

Role role = (Role)row.getObject();
%>

<c:if test="<%= !PortalUtil.isSystemRole(role.getName()) && RolePermissionUtil.contains(permissionChecker, role.getRoleId(), ActionKeys.UPDATE) %>">
	<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="editURL ">
		<portlet:param name="struts_action" value="/enterprise_admin/edit_role" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="roleId" value="<%= String.valueOf(role.getRoleId()) %>" />
	</portlet:renderURL>

	<liferay-ui:icon image="edit" url="<%= editURL %>" />
</c:if>

<c:if test="<%= RolePermissionUtil.contains(permissionChecker, role.getRoleId(), ActionKeys.PERMISSIONS) %>">
	<liferay-security:permissionsURL
		modelResource="<%= Role.class.getName() %>"
		modelResourceDescription="<%= role.getName() %>"
		resourcePrimKey="<%= String.valueOf(role.getRoleId()) %>"
		var="permissionsURL"
	/>

	<liferay-ui:icon image="permissions" url="<%= permissionsURL %>" />
</c:if>

<c:if test="<%= !role.getName().equals(RoleImpl.ADMINISTRATOR) && !role.getName().equals(RoleImpl.COMMUNITY_ADMINISTRATOR) && !role.getName().equals(RoleImpl.COMMUNITY_OWNER) && RolePermissionUtil.contains(permissionChecker, role.getRoleId(), ActionKeys.ADD_PERMISSIONS) %>">
	<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="editRolePermissionsURL">
		<portlet:param name="struts_action" value="/enterprise_admin/edit_role_permissions" />
		<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.VIEW %>" />
		<portlet:param name="roleId" value="<%= String.valueOf(role.getRoleId()) %>" />
	</portlet:renderURL>

	<liferay-ui:icon image="define_permissions" url="<%= editRolePermissionsURL %>" />
</c:if>

<c:if test="<%= (role.getType() == RoleImpl.TYPE_REGULAR) && RolePermissionUtil.contains(permissionChecker, role.getRoleId(), ActionKeys.ASSIGN_USERS) %>">
	<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="assignMembersURL">
		<portlet:param name="struts_action" value="/enterprise_admin/edit_role_assignments" />
		<portlet:param name="roleId" value="<%= String.valueOf(role.getRoleId()) %>" />
	</portlet:renderURL>

	<liferay-ui:icon image="assign" message="assign-members" url="<%= assignMembersURL %>" />
</c:if>

<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="viewUsersURL">
	<portlet:param name="struts_action" value="/enterprise_admin/view" />
	<portlet:param name="tabs1" value="users" />

	<c:choose>
		<c:when test="<%= portletName.equals(PortletKeys.ORGANIZATION_ADMIN) %>">
			<portlet:param name="organizationId" value="<%= String.valueOf(user.getOrganization().getOrganizationId()) %>" />
		</c:when>
		<c:when test="<%= portletName.equals(PortletKeys.LOCATION_ADMIN) %>">
			<portlet:param name="organizationId" value="<%= String.valueOf(user.getLocation().getOrganizationId()) %>" />
		</c:when>
	</c:choose>

	<portlet:param name="roleId" value="<%= String.valueOf(role.getRoleId()) %>" />
</portlet:renderURL>

<liferay-ui:icon image="view_users" message="view-users" url="<%= viewUsersURL %>" />

<c:if test="<%= !PortalUtil.isSystemRole(role.getName()) && RolePermissionUtil.contains(permissionChecker, role.getRoleId(), ActionKeys.DELETE) %>">
	<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="deleteURL">
		<portlet:param name="struts_action" value="/enterprise_admin/edit_role" />
		<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="roleId" value="<%= String.valueOf(role.getRoleId()) %>" />
	</portlet:actionURL>

	<liferay-ui:icon-delete url="<%= deleteURL %>" />
</c:if>