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

<liferay-util:html-top>
	<link href="<%= PortalUtil.getStaticResourceURL(request, PortalUtil.getPathModule() + "/data-engine-taglib/data_layout_builder/css/main.css") %>" rel="stylesheet" />
</liferay-util:html-top>

<div id="<portlet:namespace />-app-builder-root">
	<liferay-portlet:renderURL portletName="<%= AppBuilderPortletKeys.OBJECTS %>" var="objectsPortletURL" />

	<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" var="baseResourceURL" />

	<react:component
		module="js/pages/apps/index.es"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"appsTabs", request.getAttribute(AppBuilderWebKeys.APPS_TABS)
			).put(
				"basePortletURL", String.valueOf(renderResponse.createRenderURL())
			).put(
				"baseResourceURL", String.valueOf(baseResourceURL)
			).put(
				"defaultDelta", PropsValues.SEARCH_CONTAINER_PAGE_DEFAULT_DELTA
			).put(
				"deltaValues", PropsValues.SEARCH_CONTAINER_PAGE_DELTA_VALUES
			).put(
				"objectsPortletURL", String.valueOf(objectsPortletURL)
			).put(
				"pathFriendlyURLPublic", PortalUtil.getPathFriendlyURLPublic()
			).put(
				"userId", themeDisplay.getUserId()
			).build()
		%>'
	/>
</div>