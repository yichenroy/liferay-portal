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

ServiceProducer serviceProducer = (ServiceProducer)request.getAttribute(ScionWebKeys.SERVICE_PRODUCER);

long serviceProducerId = BeanParamUtil.getLong(serviceProducer, request, "serviceProducerId");

renderResponse.setTitle((serviceProducer == null) ? LanguageUtil.get(request, "new-service-producer") : serviceProducer.getName());
%>

<portlet:actionURL name="/service_producers_admin/edit_service_producer" var="editServiceProducerURL" />

<aui:form action="<%= editServiceProducerURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="serviceProducerId" type="hidden" value="<%= serviceProducerId %>" />

	<liferay-ui:error exception="<%= ServiceProducerNameException.class %>" message="please-enter-a-valid-name" />

	<aui:model-context bean="<%= serviceProducer %>" model="<%= ServiceProducer.class %>" />

	<aui:fieldset-group>
		<aui:fieldset>
			<c:if test="<%= serviceProducer != null %>">
				<aui:input label="uuid" name="uuidLabel" type="resource" value="<%= serviceProducer.getUuid() %>" />

				<%
				User authorizationUser = UserLocalServiceUtil.getUser(serviceProducer.getAuthorizationUserId());
				%>

				<h5><liferay-ui:message key="authorization-user" /></h5>

				<p>
					<%= authorizationUser.getEmailAddress() %>
				</p>
			</c:if>

			<aui:input name="name" />

			<aui:input name="description" type="textarea" />
		</aui:fieldset>
	</aui:fieldset-group>

	<aui:button-row>
		<aui:button type="submit" />

		<aui:button href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>