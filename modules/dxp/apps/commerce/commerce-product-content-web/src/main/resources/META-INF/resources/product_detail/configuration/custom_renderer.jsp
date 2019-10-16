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
CPContentHelper cpContentHelper = (CPContentHelper)request.getAttribute(CPContentWebKeys.CP_CONTENT_HELPER);
%>

<div id="<portlet:namespace/>configuration-tabs">
	<ul class="nav nav-tabs">

		<%
		for (CPType cpType : cpContentHelper.getCPTypes()) {
		%>

			<li>
				<a href="#<%= cpType.getName() %>"><%= cpType.getLabel(locale) %></a>
			</li>

		<%
		}
		%>

	</ul>

	<div class="tab-content">

		<%
		for (CPType cpType : cpContentHelper.getCPTypes()) {
		%>

			<div id="<%= cpType.getName() %>">
				<aui:fieldset markupView="lexicon">
					<aui:select label='<%= cpType.getLabel(locale) + StringPool.SPACE + LanguageUtil.get(request, "cp-type-renderer-key") %>' name='<%= "preferences--" + cpType.getName() + "--cpTypeRendererKey--" %>'>

						<%
						List<CPContentRenderer> cpContentRenderers = cpContentHelper.getCPContentRenderers(cpType.getName());

						for (CPContentRenderer cpContentRenderer : cpContentRenderers) {
							String key = cpContentRenderer.getKey();
						%>

							<aui:option label="<%= cpContentRenderer.getLabel(locale) %>" selected="<%= key.equals(cpContentHelper.getCPContentRendererKey(cpType.getName(), renderRequest)) %>" value="<%= key %>" />

						<%
						}
						%>

					</aui:select>
				</aui:fieldset>
			</div>

		<%
		}
		%>

	</div>
</div>

<aui:script use="aui-tabview">
	new A.TabView(
		{
			srcNode: '#<portlet:namespace/>configuration-tabs'
		}
	).render();
</aui:script>