<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
--%>

<%@ include file="/init.jsp" %>

<%
FedExCommerceShippingEngineGroupServiceConfiguration fedExCommerceShippingEngineGroupServiceConfiguration = (FedExCommerceShippingEngineGroupServiceConfiguration)request.getAttribute(FedExCommerceShippingEngineGroupServiceConfiguration.class.getName());

String messageXml = null;

LocalizedValuesMap messageLocalizedValuesMap = fedExCommerceShippingEngineGroupServiceConfiguration.message();

if (messageLocalizedValuesMap != null) {
	messageXml = LocalizationUtil.getXml(messageLocalizedValuesMap, "message");
}

String[] serviceTypes = StringUtil.split(fedExCommerceShippingEngineGroupServiceConfiguration.serviceTypes());
%>

<aui:fieldset>
	<aui:field-wrapper label="message">
		<liferay-ui:input-localized
			editorName="alloyeditor"
			fieldPrefix="settings"
			fieldPrefixSeparator="--"
			name="message"
			type="editor"
			xml="<%= messageXml %>"
		/>
	</aui:field-wrapper>

	<aui:input name="settings--url--" value="<%= fedExCommerceShippingEngineGroupServiceConfiguration.url() %>" />

	<aui:input name="settings--key--" value="<%= fedExCommerceShippingEngineGroupServiceConfiguration.key() %>" />

	<aui:input name="settings--password--" value="<%= fedExCommerceShippingEngineGroupServiceConfiguration.password() %>" />

	<aui:input label="account-number" name="settings--accountNumber--" value="<%= fedExCommerceShippingEngineGroupServiceConfiguration.accountNumber() %>" />

	<aui:input label="meter-number" name="settings--meterNumber--" value="<%= fedExCommerceShippingEngineGroupServiceConfiguration.meterNumber() %>" />

	<aui:select name="dropoff-type" name="settings--dropoffType--">

		<%
		for (String dropoffType : FedExCommerceShippingEngineConstants.DROPOFF_TYPES) {
		%>

			<aui:option label="<%= FedExCommerceShippingEngineConstants.getDropoffTypeLabel(dropoffType) %>" selected="<%= dropoffType.equals(fedExCommerceShippingEngineGroupServiceConfiguration.dropoffType()) %>" value="<%= dropoffType %>" />

		<%
		}
		%>

	</aui:select>

	<aui:field-wrapper label="service-types">

		<%
		for (String serviceType : FedExCommerceShippingEngineConstants.SERVICE_TYPES) {
		%>

			<div>
				<aui:input checked="<%= ArrayUtil.contains(serviceTypes, serviceType) %>" label="<%= FedExCommerceShippingEngineConstants.getServiceTypeLabel(serviceType) %>" name="settings--serviceTypes--" type="checkbox" value="<%= serviceType %>" />
			</div>

		<%
		}
		%>

	</aui:field-wrapper>

	<aui:select label="packing-type" name="settings--packingType--">

		<%
		for (String packingType : FedExCommerceShippingEngineConstants.PACKING_TYPES) {
		%>

			<aui:option label="<%= packingType %>" selected="<%= packingType.equals(fedExCommerceShippingEngineGroupServiceConfiguration.packingType()) %>" value="<%= packingType %>" />

		<%
		}
		%>

	</aui:select>
</aui:fieldset>