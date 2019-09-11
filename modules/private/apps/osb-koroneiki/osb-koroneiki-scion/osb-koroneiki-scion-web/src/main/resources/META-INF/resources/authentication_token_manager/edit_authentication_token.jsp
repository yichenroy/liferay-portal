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

AuthenticationToken authenticationToken = (AuthenticationToken)request.getAttribute(ScionWebKeys.AUTHENTICATION_TOKEN);

long authenticationTokenId = BeanParamUtil.getLong(authenticationToken, request, "authenticationTokenId");
%>

<liferay-ui:header
	backURL="<%= redirect %>"
	title="authentication-token"
/>

<portlet:actionURL name="/authentication_token_manager/edit_authentication_token" var="editAuthenticationTokenURL" />

<aui:form action="<%= editAuthenticationTokenURL %>" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="authenticationTokenId" type="hidden" value="<%= authenticationTokenId %>" />

	<aui:model-context bean="<%= authenticationToken %>" model="<%= AuthenticationToken.class %>" />

	<aui:fieldset-group>
		<aui:fieldset>
			<aui:input name="name" />

			<c:choose>
				<c:when test="<%= authenticationToken != null %>">
					<aui:input name="token" type="resource" value="<%= authenticationToken.getMaskedToken() %>" />
				</c:when>
				<c:otherwise>

					<%
					String token = (String)request.getAttribute(ScionWebKeys.TOKEN);
					%>

					<aui:input name="token" type="hidden" value="<%= token %>" />

					<aui:input label="token" name="tokenDisplay" type="resource" value="<%= token %>" />

					<div class="alert alert-warning">
						<liferay-ui:message key="make-sure-to-copy-this-authentication-token-now.-you-won't-be-able-to-see-it-again-after-you-save" />
					</div>
				</c:otherwise>
			</c:choose>
		</aui:fieldset>
	</aui:fieldset-group>

	<aui:button-row>
		<aui:button type="submit" />

		<aui:button href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>