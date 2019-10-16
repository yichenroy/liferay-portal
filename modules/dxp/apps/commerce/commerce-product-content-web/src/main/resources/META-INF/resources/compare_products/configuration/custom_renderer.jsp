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
CPCompareContentDisplayContext cpCompareContentDisplayContext = (CPCompareContentDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

List<CPContentListRenderer> cpContentListRenderers = cpCompareContentDisplayContext.getCPContentListRenderers();
%>

<aui:fieldset markupView="lexicon">
	<aui:select label="product-list-renderer" name="preferences--cpContentListRendererKey--" onChange='<%= renderResponse.getNamespace() + "chooseCPContentListRendererKey();" %>'>

		<%
		for (CPContentListRenderer cpContentListRenderer : cpContentListRenderers) {
			String key = cpContentListRenderer.getKey();
		%>

			<aui:option label="<%= cpContentListRenderer.getLabel(locale) %>" selected="<%= key.equals(cpCompareContentDisplayContext.getCPContentListRendererKey()) %>" value="<%= key %>" />

		<%
		}
		%>

	</aui:select>
</aui:fieldset>

<div id="<portlet:namespace/>configuration-tabs">
	<ul class="nav nav-tabs">

		<%
		for (CPType cpType : cpCompareContentDisplayContext.getCPTypes()) {
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
		for (CPType cpType : cpCompareContentDisplayContext.getCPTypes()) {
		%>

			<div id="<%= cpType.getName() %>">
				<aui:fieldset markupView="lexicon">
					<aui:select label='<%= cpType.getLabel(locale) + StringPool.SPACE + LanguageUtil.get(request, "cp-type-list-entry-renderer-key") %>' name='<%= "preferences--" + cpType.getName() + "--cpTypeListEntryRendererKey--" %>'>

						<%
						List<CPContentListEntryRenderer> cpContentListEntryRenderers = cpCompareContentDisplayContext.getCPContentListEntryRenderers(cpType.getName());

						for (CPContentListEntryRenderer cpContentListEntryRenderer : cpContentListEntryRenderers) {
							String key = cpContentListEntryRenderer.getKey();
						%>

							<aui:option label="<%= cpContentListEntryRenderer.getLabel(locale) %>" selected="<%= key.equals(cpCompareContentDisplayContext.getCPTypeListEntryRendererKey(cpType.getName())) %>" value="<%= key %>" />

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

<aui:script>
	function <portlet:namespace />chooseCPContentListRendererKey() {
		var form = AUI.$(document.<portlet:namespace />fm);

		submitForm(form);
	}
</aui:script>