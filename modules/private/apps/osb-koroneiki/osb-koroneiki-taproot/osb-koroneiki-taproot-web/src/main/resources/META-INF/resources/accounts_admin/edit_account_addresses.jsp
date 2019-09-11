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
Account koroneikiAccount = (Account)request.getAttribute(TaprootWebKeys.ACCOUNT);

renderResponse.setTitle(koroneikiAccount.getName());
%>

<liferay-util:include page="/accounts_admin/edit_account_tabs.jsp" servletContext="<%= application %>" />

<clay:management-toolbar
	creationMenu='<%=
		new JSPCreationMenu(pageContext) {
			{
				addDropdownItem(
					dropdownItem -> {
						dropdownItem.setHref(renderResponse.createRenderURL(), "mvcRenderCommandName", "/accounts_admin/edit_address", "redirect", PortalUtil.getCurrentURL(request), "accountId", koroneikiAccount.getAccountId());
						dropdownItem.setLabel(LanguageUtil.get(request, "add"));
					});
			}
		}
	%>'
	selectable="<%= false %>"
	showSearch="<%= false %>"
/>

<div class="main-content-body">
	<div class="container-fluid-1280">

		<%
		List<Address> addresses = AddressLocalServiceUtil.getAddresses(koroneikiAccount.getCompanyId(), Account.class.getName(), koroneikiAccount.getAccountId());
		%>

		<c:if test="<%= addresses.isEmpty() %>">
			<liferay-ui:empty-result-message
				message="there-are-no-addresses"
			/>
		</c:if>

		<div class="<%= addresses.isEmpty() ? "hide" : "addresses-table-wrapper table-responsive" %>">
			<table class="table table-autofit">
				<tbody>

					<%
					for (Address address : addresses) {
					%>

						<tr>
							<td>
								<div class="sticker sticker-secondary sticker-static">
									<aui:icon image="picture" markupView="lexicon" />
								</div>
							</td>
							<td class="table-cell-expand">
								<h4>

									<%
									ListType listType = address.getType();
									%>

									<liferay-ui:message key="<%= listType.getName() %>" />
								</h4>

								<div class="address-display-wrapper">
									<liferay-text-localizer:address-display
										address="<%= address %>"
									/>
								</div>

								<c:if test="<%= address.isPrimary() %>">
									<div class="address-primary-label-wrapper">
										<span class="label label-primary">
											<span class="label-item label-item-expand"><%= StringUtil.toUpperCase(LanguageUtil.get(request, "primary"), locale) %></span>
										</span>
									</div>
								</c:if>
							</td>
							<td>
								<span class="autofit-col lfr-search-container-wrapper">
									<liferay-util:include page="/accounts_admin/account_address_action.jsp" servletContext="<%= application %>">
										<liferay-util:param name="addressId" value="<%= String.valueOf(address.getAddressId()) %>" />
									</liferay-util:include>
								</span>
							</td>
						</tr>

					<%
					}
					%>

				</tbody>
			</table>
		</div>
	</div>
</div>