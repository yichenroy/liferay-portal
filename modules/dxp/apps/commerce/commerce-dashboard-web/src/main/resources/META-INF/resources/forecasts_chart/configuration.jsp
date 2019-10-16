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
CommerceDashboardForecastsChartPortletInstanceConfiguration commerceDashboardForecastsChartPortletInstanceConfiguration = portletDisplay.getPortletInstanceConfiguration(CommerceDashboardForecastsChartPortletInstanceConfiguration.class);

commerceDashboardForecastsChartPortletInstanceConfiguration = ParameterMapUtil.setParameterMap(CommerceDashboardForecastsChartPortletInstanceConfiguration.class, commerceDashboardForecastsChartPortletInstanceConfiguration, request.getParameterMap(), "preferences--", "--");

String redirect = ParamUtil.getString(request, "redirect");
%>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL" />

<liferay-portlet:renderURL portletConfiguration="<%= true %>" varImpl="configurationRenderURL" />

<liferay-frontend:edit-form
	action="<%= configurationActionURL %>"
	method="post"
	name="fm"
>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />

	<liferay-frontend:edit-form-body>
		<liferay-frontend:fieldset-group>
			<liferay-frontend:fieldset>
				<aui:input checked="<%= commerceDashboardForecastsChartPortletInstanceConfiguration.filterBySKU() %>" label="filter-by-sku" name="preferences--filterBySKU--" type="checkbox" />

				<aui:select name="preferences--target--">

					<%
					for (int curTarget : CommerceForecastEntryConstants.TARGETS) {
					%>

						<aui:option label="<%= CommerceForecastEntryConstants.getTargetLabel(curTarget) %>" selected="<%= curTarget == commerceDashboardForecastsChartPortletInstanceConfiguration.target() %>" value="<%= curTarget %>" />

					<%
					}
					%>

				</aui:select>
			</liferay-frontend:fieldset>
		</liferay-frontend:fieldset-group>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<aui:button type="submit" />

		<aui:button href="<%= redirect %>" type="cancel" />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>