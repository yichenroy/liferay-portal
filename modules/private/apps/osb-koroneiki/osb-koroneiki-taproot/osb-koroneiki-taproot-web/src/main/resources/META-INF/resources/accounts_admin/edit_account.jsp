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

Account koroneikiAccount = (Account)request.getAttribute(TaprootWebKeys.ACCOUNT);

long accountId = BeanParamUtil.getLong(koroneikiAccount, request, "accountId");

renderResponse.setTitle((koroneikiAccount == null) ? LanguageUtil.get(request, "new-account") : koroneikiAccount.getName());
%>

<liferay-util:include page="/accounts_admin/edit_account_tabs.jsp" servletContext="<%= application %>" />

<portlet:actionURL name="/accounts_admin/edit_account" var="editAccountURL" />

<aui:form action="<%= editAccountURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="accountId" type="hidden" value="<%= accountId %>" />

	<liferay-ui:error exception="<%= AccountNameException.class %>" message="please-enter-a-valid-name" />

	<aui:model-context bean="<%= koroneikiAccount %>" model="<%= Account.class %>" />

	<aui:fieldset-group>
		<aui:fieldset>
			<c:if test="<%= koroneikiAccount != null %>">
				<aui:input label="key" name="key" type="resource" value="<%= koroneikiAccount.getAccountKey() %>" />
			</c:if>

			<h5><liferay-ui:message key="parent-account" /></h5>

			<p>

				<%
				Account parentAccount = null;

				if (koroneikiAccount != null) {
					parentAccount = koroneikiAccount.getParentAccount();
				}
				%>

				<aui:input name="parentAccountId" type="hidden" value='<%= (parentAccount != null) ? parentAccount.getAccountId() : "" %>' />

				<span id="<portlet:namespace />parentAccountName">
					<c:if test="<%= parentAccount != null %>">
						<liferay-portlet:renderURL var="parentAccountURL">
							<portlet:param name="mvcRenderCommandName" value="/accounts_admin/edit_account" />
							<portlet:param name="accountId" value="<%= String.valueOf(parentAccount .getAccountId()) %>" />
						</liferay-portlet:renderURL>

						<a href="<%= parentAccountURL %>"><%= HtmlUtil.escape(parentAccount.getName()) %></a>
					</c:if>
				</span>

				<aui:button onClick='<%= renderResponse.getNamespace() + "openAccountSelector();" %>' value="select" />

				<aui:button onClick='<%= renderResponse.getNamespace() + "removeParentAccount();" %>' value="remove" />
			</p>

			<aui:input name="name" />

			<aui:input name="code" />

			<aui:input name="description" type="textarea" />

			<aui:input name="notes" type="textarea" />

			<aui:input name="contactEmailAddress" />

			<aui:input name="profileEmailAddress" />

			<aui:input name="phoneNumber" />

			<aui:input name="faxNumber" />

			<aui:input name="website" />

			<aui:select name="industry">
				<aui:option value="" />

				<%
				for (String industry : AccountIndustry.VALUES) {
				%>

					<aui:option label="<%= industry %>" value="<%= industry %>" />

				<%
				}
				%>

			</aui:select>

			<aui:select name="tier">
				<aui:option value="" />

				<%
				for (String tier : AccountTier.VALUES) {
				%>

					<aui:option label="<%= tier %>" value="<%= tier %>" />

				<%
				}
				%>

			</aui:select>

			<aui:input name="soldBy" />

			<aui:select name="status">
				<aui:option value="" />

				<%
				for (int status : WorkflowConstants.VALUES) {
				%>

					<aui:option label="<%= WorkflowConstants.getStatusLabel(status) %>" value="<%= status %>" />

				<%
				}
				%>

			</aui:select>
		</aui:fieldset>
	</aui:fieldset-group>

	<aui:button-row>
		<aui:button type="submit" />

		<aui:button href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script use="aui-base">
	<portlet:namespace />openAccountSelector = function() {
		Liferay.Util.selectEntity(
			{
				dialog: {
					constrain: true,
					modal: true
				},
				eventName: '<portlet:namespace />selectAccount',
				title: '<%= UnicodeLanguageUtil.get(request, "accounts") %>',
				uri: '<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="mvcPath" value="/accounts_admin/select_account.jsp" /><portlet:param name="accountId" value="<%= String.valueOf(accountId) %>" /></portlet:renderURL>'
			},
			function(event) {
				A.one('#<portlet:namespace />parentAccountName').html(event.accountname);
				A.one('#<portlet:namespace />parentAccountId').val(event.accountid);
			}
		);
	}

	<portlet:namespace />removeParentAccount = function() {
		A.one('#<portlet:namespace />parentAccountId').val('');
		A.one('#<portlet:namespace />parentAccountName').html('');
	}
</aui:script>