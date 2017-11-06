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
BillingAddressCheckoutStepDisplayContext billingAddressCheckoutStepDisplayContext = (BillingAddressCheckoutStepDisplayContext)request.getAttribute("CommerceCheckoutStepDisplayContext");

List<CommerceAddress> commerceAddresses = billingAddressCheckoutStepDisplayContext.getCommerceAddresses();

long defaultBillingAddressId = 0;

for (CommerceAddress commerceAddress : commerceAddresses) {
	if (commerceAddress.isDefaultBilling()) {
		defaultBillingAddressId = commerceAddress.getCommerceAddressId();

		break;
	}
}

if ((defaultBillingAddressId == 0) && !commerceAddresses.isEmpty()) {
	CommerceAddress commerceAddress = commerceAddresses.get(0);

	defaultBillingAddressId = commerceAddress.getCommerceAddressId();
}

long billingAddressId = BeanParamUtil.getLong(billingAddressCheckoutStepDisplayContext.getCommerceCart(), request, "billingAddressId", defaultBillingAddressId);

long commerceCountryId = ParamUtil.getLong(request, "commerceCountryId");
long commerceRegionId = ParamUtil.getLong(request, "commerceRegionId");
%>

<h3><liferay-ui:message key="billing-address" /></h3>

<aui:fieldset>
	<div id="<portlet:namespace />billingAddressChoice">
		<div class="row">

			<%
			for (CommerceAddress commerceAddress : commerceAddresses) {
				commerceAddress = commerceAddress.toEscapedModel();
			%>

				<div class="col-md-4">
					<div class="radio radio-card radio-middle-left">
						<label>
							<aui:input checked="<%= billingAddressId == commerceAddress.getCommerceAddressId() %>" label="" name="billingAddressId" type="radio" value="<%= commerceAddress.getCommerceAddressId() %>" />

							<div class="card card-horizontal">
								<div class="card-row">
									<div class="card-col-content card-col-gutters">
										<h4><%= commerceAddress.getName() %></h4>
										<p><%= commerceAddress.getStreet1() %></p>

										<c:if test="<%= Validator.isNotNull(commerceAddress.getStreet2()) %>">
											<p><%= commerceAddress.getStreet2() %></p>
										</c:if>

										<c:if test="<%= Validator.isNotNull(commerceAddress.getStreet3()) %>">
											<p><%= commerceAddress.getStreet3() %></p>
										</c:if>

										<p><%= commerceAddress.getCity() %></p>

										<%
										CommerceCountry commerceCountry = commerceAddress.getCommerceCountry();
										%>

										<c:if test="<%= commerceCountry != null %>">
											<p><%= HtmlUtil.escape(commerceCountry.getName(locale)) %></p>
										</c:if>
									</div>
								</div>
							</div>
						</label>
					</div>
				</div>

			<%
			}
			%>

		</div>

		<c:if test="<%= !commerceAddresses.isEmpty() %>">
			<aui:button-row>
				<aui:button cssClass="btn-lg" name="addNewAddress" value="add-new-address" />
			</aui:button-row>
		</c:if>
	</div>

	<aui:fieldset cssClass='<%= commerceAddresses.isEmpty() ? StringPool.BLANK : "hide" %>' id='<%= renderResponse.getNamespace() + "newAddressContainer" %>'>
		<aui:input name="newAddress" type="hidden" value='<%= commerceAddresses.isEmpty() ? "1" : "0" %>' />

		<c:if test="<%= !commerceAddresses.isEmpty() %>">
			<aui:button-row>
				<aui:button cssClass="btn-lg" name="cancel" value="cancel" />
			</aui:button-row>
		</c:if>

		<liferay-ui:error exception="<%= CommerceAddressCityException.class %>" message="please-enter-a-valid-city" />
		<liferay-ui:error exception="<%= CommerceAddressCountryException.class %>" message="please-enter-a-valid-country" />
		<liferay-ui:error exception="<%= CommerceAddressNameException.class %>" message="please-enter-a-valid-name" />
		<liferay-ui:error exception="<%= CommerceAddressStreetException.class %>" message="please-enter-a-valid-street" />
		<liferay-ui:error exception="<%= CommerceCartBillingAddressException.class %>" message="please-enter-a-valid-address" />

		<div class="alert alert-info">
			<liferay-ui:message key="please-enter-your-personal-information-and-address" />
		</div>

		<aui:model-context model="<%= CommerceAddress.class %>" />

		<aui:col width="<%= 50 %>">
			<aui:input label="full-name" name="name" />

			<aui:input name="street1" />

			<aui:input name="street2" />

			<aui:input name="street3" />

			<aui:select label="country" name="commerceCountryId" />

			<aui:select label="region" name="commerceRegionId" />
		</aui:col>

		<aui:col width="<%= 50 %>">
			<aui:input label="postal-code" name="zip" />

			<aui:input name="city" />

			<aui:input name="phoneNumber" />
		</aui:col>
	</aui:fieldset>
</aui:fieldset>

<aui:script use="aui-base,liferay-dynamic-select">
	new Liferay.DynamicSelect(
		[
			{
				select: '<portlet:namespace />commerceCountryId',
				selectData: function(callback) {
					Liferay.Service(
						'/commerce.commercecountry/get-billing-commerce-countries',
						{
							groupId: <%= scopeGroupId %>,
							billingAllowed: true,
							active: true
						},
						callback
					);
				},
				selectDesc: 'nameCurrentValue',
				selectId: 'commerceCountryId',
				selectSort: '<%= true %>',
				selectVal: '<%= commerceCountryId %>'
			},
			{
				select: '<portlet:namespace />commerceRegionId',
				selectData: function(callback, selectKey) {
					Liferay.Service(
						'/commerce.commerceregion/get-commerce-regions',
						{
							commerceCountryId: Number(selectKey),
							active: true
						},
						callback
					);
				},
				selectDesc: 'name',
				selectId: 'commerceRegionId',
				selectVal: '<%= commerceRegionId %>'
			}
		]
	);

	Liferay.on(
	'form:registered',
	function(event) {
		if (event.formName === '<portlet:namespace />fm') {
			A.Do.before(
				function() {
					var newAddress = A.one('#<portlet:namespace />newAddress');
					if (!(newAddress.val() == '1')) {
						return new A.Do.Halt('', false);
					}
				},
				event.form.formValidator,
				'hasErrors'
			);
		}
	});

	var addNewAddress = A.one('#<portlet:namespace />addNewAddress')

	if (addNewAddress) {
		addNewAddress.on(
			'click',
			function(event) {
				A.one('#<portlet:namespace />newAddressContainer').show();
				A.one('#<portlet:namespace />newAddress').val('1');
				A.one('#<portlet:namespace />billingAddressChoice').hide();

			}
		);
	}

	var cancel = A.one('#<portlet:namespace />cancel')

	if (cancel) {
		A.one('#<portlet:namespace />cancel').on(
			'click',
			function(event) {
				A.one('#<portlet:namespace />newAddressContainer').hide();
				A.one('#<portlet:namespace />newAddress').val('0');
				A.one('#<portlet:namespace />billingAddressChoice').show();

			}
		);
	}
</aui:script>