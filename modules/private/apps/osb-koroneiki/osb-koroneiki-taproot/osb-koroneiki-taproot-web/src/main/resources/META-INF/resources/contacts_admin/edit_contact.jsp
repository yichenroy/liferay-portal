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

Contact koroneikiContact = (Contact)request.getAttribute(TaprootWebKeys.CONTACT);

long contactId = BeanParamUtil.getLong(koroneikiContact, request, "contactId");
%>

<liferay-util:include page="/contacts_admin/edit_contact_tabs.jsp" servletContext="<%= application %>" />

<portlet:actionURL name="/contacts_admin/edit_contact" var="editContactURL" />

<aui:form action="<%= editContactURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="contactId" type="hidden" value="<%= contactId %>" />

	<liferay-ui:error exception="<%= ContactEmailAddressException.class %>" message="please-enter-a-valid-email-address" />
	<liferay-ui:error exception="<%= ContactEmailAddressException.MustNotBeDuplicate.class %>" message="the-email-address-you-requested-is-already-taken" />

	<aui:model-context bean="<%= koroneikiContact %>" model="<%= Contact.class %>" />

	<aui:fieldset-group>
		<aui:fieldset>
			<c:if test="<%= koroneikiContact != null %>">
				<aui:input label="key" name="key" type="resource" value="<%= koroneikiContact.getContactKey() %>" />
			</c:if>

			<aui:input name="uuid" />

			<aui:input name="oktaId" />

			<aui:input name="firstName" />

			<aui:input name="middleName" />

			<aui:input name="lastName" />

			<aui:input name="emailAddress" />
		</aui:fieldset>
	</aui:fieldset-group>

	<aui:button-row>
		<aui:button type="submit" />

		<aui:button href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>