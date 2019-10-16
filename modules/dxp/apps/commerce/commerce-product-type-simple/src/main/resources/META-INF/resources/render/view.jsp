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
CPTypeDisplayContext cpTypeDisplayContext = (CPTypeDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

Map<String, Object> contextObjects = new HashMap<>();

contextObjects.put("simpleCPTypeDisplayContext", cpTypeDisplayContext);

CPDefinition cpDefinition = cpTypeDisplayContext.getCPDefinition();
%>

<liferay-ddm:template-renderer
	className="<%= SimpleCPType.class.getName() %>"
	contextObjects="<%= contextObjects %>"
	displayStyle="<%= cpTypeDisplayContext.getDisplayStyle() %>"
	displayStyleGroupId="<%= cpTypeDisplayContext.getDisplayStyleGroupId() %>"
	entries="<%= Collections.singletonList(cpDefinition) %>"
>
	<div class="container-fluid product-detail" data-cp-definition-id="<%= cpDefinition.getCPDefinitionId() %>">
		<div class="row">
			<div class="col-lg-6 col-md-7">
				<div class="row">
					<div class="col-lg-2 col-md-3 col-xs-2">
						<div id="<portlet:namespace />thumbs-container">

							<%
							for (CPAttachmentFileEntry cpAttachmentFileEntry : cpTypeDisplayContext.getImages()) {
								String url = cpTypeDisplayContext.getImageURL(cpAttachmentFileEntry.getFileEntry(), themeDisplay);
							%>

								<div class="card thumb" data-url="<%= url %>">
									<img class="center-block img-responsive" src="<%= url %>">
								</div>

							<%
							}
							%>

						</div>
					</div>

					<div class="col-lg-10 col-md-9 col-xs-10 full-image">

						<%
						CPAttachmentFileEntry cpAttachmentFileEntry = cpTypeDisplayContext.getDefaultImage();
						%>

						<c:if test="<%= cpAttachmentFileEntry != null %>">
							<img class="center-block img-responsive" id="<portlet:namespace />full-image" src="<%= cpTypeDisplayContext.getImageURL(cpAttachmentFileEntry.getFileEntry(), themeDisplay) %>">
						</c:if>
					</div>
				</div>
			</div>

			<div class="col-lg-6 col-md-5">
				<h1><%= cpDefinition.getTitle(languageId) %></h1>

				<div class="row">
					<div class="col-md-12">
						<div class="options">
							<%= cpTypeDisplayContext.renderOptions(renderRequest, renderResponse) %>
						</div>
					</div>
				</div>

				<div class="row">
					<div class="col-md-12">
						<liferay-util:dynamic-include key="com.liferay.commerce.product.content.web#/add_to_cart#" />
					</div>
				</div>

				<div class="row">
					<div class="col-md-12">
						<liferay-util:dynamic-include key="com.liferay.commerce.product.content.web#/add_to_wish_list#" />
					</div>
				</div>
			</div>
		</div>

		<%
			List<CPDefinitionSpecificationOptionValue> cpDefinitionSpecificationOptionValues = cpTypeDisplayContext.getCPDefinitionSpecificationOptionValues();
		%>

		<div class="nav-tabs-centered">
			<ul class="nav nav-tabs" role="tablist">
				<li class="active" role="presentation">
					<a aria-controls="<portlet:namespace />description" aria-expanded="true" data-toggle="tab" href="#<portlet:namespace />description" role="tab">
						<%= LanguageUtil.get(resourceBundle, "description") %>
					</a>
				</li>

				<c:if test="<%= !cpDefinitionSpecificationOptionValues.isEmpty() %>">
					<li role="presentation">
						<a aria-controls="<portlet:namespace />specification" aria-expanded="false" data-toggle="tab" href="#<portlet:namespace />specification" role="tab">
							<%= LanguageUtil.get(resourceBundle, "specification-options") %>
						</a>
					</li>
				</c:if>
			</ul>

			<div class="tab-content">
				<div class="active tab-pane" id="<portlet:namespace />description">
					<p><%= cpDefinition.getDescription(languageId) %></p>
				</div>

				<c:if test="<%= !cpDefinitionSpecificationOptionValues.isEmpty() %>">
					<div class="tab-pane" id="<portlet:namespace />specification">
						<div class="table-responsive">
							<table class="table table-bordered table-striped">

								<%
								for (CPDefinitionSpecificationOptionValue cpDefinitionSpecificationOptionValue : cpDefinitionSpecificationOptionValues) {
									CPSpecificationOption cpSpecificationOption = cpDefinitionSpecificationOptionValue.getCPSpecificationOption();
								%>

									<tr>
										<td><%= cpSpecificationOption.getTitle(languageId) %></td>
										<td><%= cpDefinitionSpecificationOptionValue.getValue(languageId) %></td>
									</tr>

								<%
								}
								%>

							</table>
						</div>
					</div>
				</c:if>
			</div>
		</div>
	</div>

	<aui:script>
		$(document).ready(
			function() {
				$(".thumb").click(
					function() {
						$("#full-image").attr("src",$(this).attr("data-url"));
				});
			});
	</aui:script>

	<aui:script use="liferay-commerce-product-content">
		var productContent = new Liferay.Portlet.ProductContent(
			{
				cpDefinitionId: <%= cpTypeDisplayContext.getCPDefinitionId() %>,
				fullImageSelector : '#<portlet:namespace />full-image',
				namespace: '<portlet:namespace />',
				thumbsContainerSelector : '#<portlet:namespace />thumbs-container',
				viewAttachmentURL: '<%= cpTypeDisplayContext.getViewAttachmentURL().toString() %>'
			}
		);

		Liferay.component('<portlet:namespace /><%= cpDefinition.getCPDefinitionId() %>ProductContent', productContent);
	</aui:script>
</liferay-ddm:template-renderer>