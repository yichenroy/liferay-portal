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

ExternalLink externalLink = (ExternalLink)request.getAttribute(RootWebKeys.EXTERNAL_LINK);

long externalLinkId = BeanParamUtil.getLong(externalLink, request, "externalLinkId");
long classNameId = BeanParamUtil.getLong(externalLink, request, "classNameId");
long classPK = BeanParamUtil.getLong(externalLink, request, "classPK");

String title = ParamUtil.getString(request, "title");

renderResponse.setTitle(title);
%>

<portlet:actionURL name="/edit_external_link" var="editExternalLinkURL" />

<aui:form action="<%= editExternalLinkURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="externalLinkId" type="hidden" value="<%= externalLinkId %>" />
	<aui:input name="classNameId" type="hidden" value="<%= classNameId %>" />
	<aui:input name="classPK" type="hidden" value="<%= classPK %>" />

	<liferay-ui:error exception="<%= ExternalLinkDomainException.class %>" message="please-enter-a-valid-domain" />
	<liferay-ui:error exception="<%= ExternalLinkEntityIdException.class %>" message="please-enter-a-valid-entity-id" />
	<liferay-ui:error exception="<%= ExternalLinkEntityNameException.class %>" message="please-enter-a-valid-entity-name" />

	<aui:model-context bean="<%= externalLink %>" model="<%= ExternalLink.class %>" />

	<aui:fieldset-group>
		<aui:fieldset>
			<c:if test="<%= externalLink != null %>">
				<aui:input label="key" name="key" type="resource" value="<%= externalLink.getExternalLinkKey() %>" />
			</c:if>

			<aui:input disabled="<%= externalLink != null %>" name="domain" />

			<aui:input disabled="<%= externalLink != null %>" name="entityName" />

			<aui:input name="entityId" />
		</aui:fieldset>
	</aui:fieldset-group>

	<aui:button-row>
		<aui:button type="submit" />

		<aui:button href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>