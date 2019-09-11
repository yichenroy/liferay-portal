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
ServiceProducer serviceProducer = (ServiceProducer)request.getAttribute(ScionWebKeys.SERVICE_PRODUCER);
%>

<div class="button-holder">
	<portlet:renderURL var="addAuthenticationTokenURL">
		<portlet:param name="mvcRenderCommandName" value="/authentication_token_manager/edit_authentication_token" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
	</portlet:renderURL>

	<aui:button href="<%= addAuthenticationTokenURL %>" icon="icon-plus" value="add-authentication-token" />
</div>

<liferay-ui:search-container
	emptyResultsMessage="no-authentication-tokens-were-found"
	headerNames="name,token,status,"
	iteratorURL="<%= renderResponse.createRenderURL() %>"
	total="<%= AuthenticationTokenLocalServiceUtil.getAuthenticationTokensCount(serviceProducer.getServiceProducerId()) %>"
>
	<liferay-ui:search-container-results
		results="<%= AuthenticationTokenLocalServiceUtil.getAuthenticationTokens(serviceProducer.getServiceProducerId(), searchContainer.getStart(), searchContainer.getEnd()) %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.osb.koroneiki.scion.model.AuthenticationToken"
		escapedModel="<%= true %>"
		keyProperty="authenticationTokenId"
		modelVar="authenticationToken"
	>
		<portlet:renderURL var="rowURL">
			<portlet:param name="mvcRenderCommandName" value="/authentication_token_manager/edit_authentication_token" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="authenticationTokenId" value="<%= String.valueOf(authenticationToken.getAuthenticationTokenId()) %>" />
		</portlet:renderURL>

		<liferay-ui:search-container-column-text
			href="<%= rowURL %>"
			name="name"
			value="<%= authenticationToken.getName() %>"
		/>

		<liferay-ui:search-container-column-text
			href="<%= rowURL %>"
			name="token"
			value="<%= authenticationToken.getMaskedToken() %>"
		/>

		<liferay-ui:search-container-column-status
			href="<%= rowURL %>"
			name="status"
			status="<%= authenticationToken.getStatus() %>"
		/>

		<liferay-ui:search-container-column-jsp
			align="right"
			path="/authentication_token_manager/authentication_token_action.jsp"
		/>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator
		markupView="lexicon"
	/>
</liferay-ui:search-container>