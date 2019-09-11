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
AccountsDisplayContext accountsDisplayContext = new AccountsDisplayContext(renderRequest, renderResponse, request);

ViewAccountsManagementToolbarDisplayContext viewAccountsManagementToolbarDisplayContext = new ViewAccountsManagementToolbarDisplayContext(liferayPortletRequest, liferayPortletResponse, request, accountsDisplayContext.getSearchContainer());
%>

<clay:management-toolbar
	displayContext="<%= viewAccountsManagementToolbarDisplayContext %>"
/>

<aui:form cssClass="container-fluid-1280" name="selectAccountFm">
	<liferay-ui:search-container
		searchContainer="<%= accountsDisplayContext.getSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.osb.koroneiki.taproot.model.Account"
			escapedModel="<%= true %>"
			keyProperty="accountId"
			modelVar="koroneikiAccount"
		>
			<liferay-ui:search-container-column-text
				name="name"
			>
				<span class="lfr-portal-tooltip" data-title="<liferay-ui:message key="account" />">
					<aui:icon cssClass="icon-monospaced" image="users" markupView="lexicon" />
				</span>

				<%= koroneikiAccount.getName() %>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				name="code"
				value="<%= koroneikiAccount.getCode() %>"
			/>

			<liferay-ui:search-container-column-text
				name="description"
				value="<%= koroneikiAccount.getDescription() %>"
			/>

			<liferay-ui:search-container-column-status
				name="status"
				status="<%= koroneikiAccount.getStatus() %>"
			/>

			<liferay-ui:search-container-column-text>

				<%
				Map<String, Object> data = new HashMap<String, Object>();

				data.put("accountid", koroneikiAccount.getAccountId());
				data.put("accountname", koroneikiAccount.getName());
				%>

				<aui:button cssClass="selector-button" data="<%= data %>" value="select" />
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>

<aui:script>
	Liferay.Util.selectEntityHandler('#<portlet:namespace />selectAccountFm', '<portlet:namespace />selectAccount');
</aui:script>