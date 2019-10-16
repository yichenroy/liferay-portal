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
CPAttachmentFileEntriesDisplayContext cpAttachmentFileEntriesDisplayContext = (CPAttachmentFileEntriesDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPAttachmentFileEntry cpAttachmentFileEntry = cpAttachmentFileEntriesDisplayContext.getCPAttachmentFileEntry();

long cpDefinitionId = cpAttachmentFileEntriesDisplayContext.getCPDefinitionId();

long fileEntryId = BeanParamUtil.getLong(cpAttachmentFileEntry, request, "fileEntryId");
%>

<liferay-ui:error-marker key="<%= WebKeys.ERROR_SECTION %>" value="details" />

<aui:model-context bean="<%= cpAttachmentFileEntry %>" model="<%= CPAttachmentFileEntry.class %>" />

<div class="lfr-attachment-cover-image-selector">
	<portlet:actionURL name="uploadTempAttachment" var="uploadCoverImageURL">
		<portlet:param name="cpDefinitionId" value="<%= String.valueOf(cpDefinitionId) %>" />
	</portlet:actionURL>

	<liferay-item-selector:image-selector
		draggableImage="vertical"
		fileEntryId="<%= fileEntryId %>"
		itemSelectorEventName='<%= "addCPAttachmentFileEntry" %>'
		itemSelectorURL="<%= cpAttachmentFileEntriesDisplayContext.getItemSelectorUrl() %>"
		maxFileSize="<%= cpAttachmentFileEntriesDisplayContext.getImageMaxSize() %>"
		paramName="fileEntry"
		uploadURL="<%= uploadCoverImageURL %>"
		validExtensions='<%= StringUtil.merge(cpAttachmentFileEntriesDisplayContext.getImageExtensions(), ", ") %>'
	/>
</div>