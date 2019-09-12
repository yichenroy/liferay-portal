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
List<NavigationItem> navigationItems = new ArrayList<>();

NavigationItem entriesNavigationItem = new NavigationItem();

entriesNavigationItem.setActive(true);
entriesNavigationItem.setHref(StringPool.BLANK);
entriesNavigationItem.setLabel(LanguageUtil.get(request, "service-producers"));

navigationItems.add(entriesNavigationItem);
%>

<clay:navigation-bar
	inverted="<%= true %>"
	navigationItems="<%= navigationItems %>"
/>

<clay:management-toolbar
	creationMenu='<%=
		new JSPCreationMenu(pageContext) {
			{
				addDropdownItem(
					dropdownItem -> {
						dropdownItem.setHref(renderResponse.createRenderURL(), "mvcRenderCommandName", "/service_producers_admin/edit_service_producer", "redirect", PortalUtil.getCurrentURL(request));
						dropdownItem.setLabel(LanguageUtil.get(request, "new-service-producer"));
					});
			}
		}
	%>'
	selectable="<%= false %>"
	showSearch="<%= false %>"
/>

<div class="container-fluid-1280">
	<liferay-ui:search-container
		emptyResultsMessage="no-service-producers-were-found"
		headerNames="name,description,"
		iteratorURL="<%= renderResponse.createRenderURL() %>"
		total="<%= ServiceProducerLocalServiceUtil.getServiceProducersCount() %>"
	>
		<liferay-ui:search-container-results
			results="<%= ServiceProducerLocalServiceUtil.getServiceProducers(searchContainer.getStart(), searchContainer.getEnd()) %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.osb.koroneiki.scion.model.ServiceProducer"
			escapedModel="<%= true %>"
			keyProperty="serviceProducerId"
			modelVar="serviceProducer"
		>
			<portlet:renderURL var="rowURL">
				<portlet:param name="mvcRenderCommandName" value="/service_producers_admin/edit_service_producer" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="serviceProducerId" value="<%= String.valueOf(serviceProducer.getServiceProducerId()) %>" />
			</portlet:renderURL>

			<liferay-ui:search-container-column-text
				href="<%= rowURL %>"
				name="name"
				value="<%= serviceProducer.getName() %>"
			/>

			<liferay-ui:search-container-column-text
				href="<%= rowURL %>"
				name="description"
				value="<%= serviceProducer.getDescription() %>"
			/>

			<liferay-ui:search-container-column-jsp
				align="right"
				path="/service_producers_admin/service_producer_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</div>