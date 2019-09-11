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

ProductConsumption productConsumption = (ProductConsumption)request.getAttribute(TrunkWebKeys.PRODUCT_CONSUMPTION);

renderResponse.setTitle((productConsumption == null) ? LanguageUtil.get(request, "new-consumption") : LanguageUtil.get(request, "consumption"));
%>

<liferay-util:include page="/products_admin/edit_product_consumption_tabs.jsp" servletContext="<%= application %>" />

<portlet:actionURL name="/products_admin/edit_product_consumption" var="editProductConsumptionURL" />

<aui:form action="<%= editProductConsumptionURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />

	<liferay-ui:error exception="<%= NoSuchAccountException.class %>" message="please-select-a-valid-account" />
	<liferay-ui:error exception="<%= NoSuchProductEntryException.class %>" message="please-select-a-valid-product" />

	<aui:model-context bean="<%= productConsumption %>" model="<%= ProductConsumption.class %>" />

	<aui:fieldset-group>
		<aui:fieldset>
			<c:choose>
				<c:when test="<%= productConsumption != null %>">
					<aui:input label="key" name="key" type="resource" value="<%= productConsumption.getProductConsumptionKey() %>" />

					<%
					Account koroneikiAccount = productConsumption.getAccount();
					%>

					<h5><liferay-ui:message key="account" /></h5>

					<p>
						<liferay-portlet:renderURL portletName="<%= TaprootPortletKeys.ACCOUNTS_ADMIN %>" var="accountURL">
							<portlet:param name="mvcRenderCommandName" value="/accounts_admin/edit_account" />
							<portlet:param name="accountId" value="<%= String.valueOf(koroneikiAccount.getAccountId()) %>" />
						</liferay-portlet:renderURL>

						<a href="<%= accountURL %>"><%= HtmlUtil.escape(koroneikiAccount.getName()) %></a>
					</p>

					<%
					ProductEntry productEntry = productConsumption.getProductEntry();
					%>

					<h5><liferay-ui:message key="product" /></h5>

					<p>
						<liferay-portlet:renderURL var="productEntryURL">
							<portlet:param name="mvcRenderCommandName" value="/products_admin/edit_product_entry" />
							<portlet:param name="productEntryId" value="<%= String.valueOf(productEntry.getProductEntryId()) %>" />
						</liferay-portlet:renderURL>

						<a href="<%= productEntryURL %>"><%= HtmlUtil.escape(productEntry.getName()) %></a>
					</p>
				</c:when>
				<c:otherwise>
					<aui:select label="account" name="accountId" onChange='<%= renderResponse.getNamespace() + "selectAccount(this);" %>'>
						<aui:option value="" />

						<%
						for (Account koroneikiAccount : AccountLocalServiceUtil.getAccounts(QueryUtil.ALL_POS, QueryUtil.ALL_POS)) {
							Map<String, Object> data = new HashMap<String, Object>();

							data.put("accountkey", koroneikiAccount.getAccountKey());
						%>

							<aui:option data="<%= data %>" label="<%= koroneikiAccount.getName() %>" value="<%= koroneikiAccount.getAccountId() %>" />

						<%
						}
						%>

					</aui:select>

					<aui:select label="product" name="productEntryId">
						<aui:option value="" />

						<%
						for (ProductEntry productEntry : ProductEntryLocalServiceUtil.getProductEntries(QueryUtil.ALL_POS, QueryUtil.ALL_POS)) {
						%>

							<aui:option label="<%= productEntry.getName() %>" value="<%= productEntry.getProductEntryId() %>" />

						<%
						}
						%>

					</aui:select>
				</c:otherwise>
			</c:choose>

			<div class="form-group">
				<h3 class="sheet-subtitle"><liferay-ui:message key="product-fields" /></h3>

				<aui:fieldset id='<%= renderResponse.getNamespace() + "productFields" %>'>

					<%
					List<ProductField> productFields = new ArrayList<>();

					if (productConsumption != null) {
						productFields.addAll(productConsumption.getProductFields());
					}

					if (productFields.isEmpty()) {
						productFields.add(ProductFieldLocalServiceUtil.createProductField(0));
					}

					int[] productFieldIndexes = new int[productFields.size()];

					for (int i = 0; i < productFields.size(); i++) {
						ProductField productField = productFields.get(i);

						productFieldIndexes[i] = i;
					%>

						<div class="lfr-form-row lfr-form-row-inline">
							<div class="row-fields">
								<aui:row>
									<aui:col md="5">
										<aui:input label="name" name='<%= "productFieldName_" + i %>' type="text" value="<%= productField.getName() %>" />
									</aui:col>

									<aui:col md="5">
										<aui:input label="value" name='<%= "productFieldValue_" + i %>' type="text" value="<%= productField.getValue() %>" />
									</aui:col>
								</aui:row>
							</div>
						</div>

					<%
					}
					%>

					<aui:input name="productFieldIndexes" type="hidden" value="<%= StringUtil.merge(productFieldIndexes) %>" />
				</aui:fieldset>
			</div>
		</aui:fieldset>
	</aui:fieldset-group>

	<aui:button-row>
		<c:if test="<%= productConsumption == null %>">
			<aui:button type="submit" />
		</c:if>

		<aui:button href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script use="liferay-auto-fields">
	var autoFields = new Liferay.AutoFields(
		{
			contentBox: 'fieldset#<portlet:namespace />productFields',
			fieldIndexes: '<portlet:namespace />productFieldIndexes',
			namespace: '<portlet:namespace />'
		}
	).render();
</aui:script>