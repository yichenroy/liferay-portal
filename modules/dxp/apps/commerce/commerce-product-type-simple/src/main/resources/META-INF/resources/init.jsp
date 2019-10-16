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

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/ddm" prefix="liferay-ddm" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.commerce.product.content.web.display.context.CPTypeDisplayContext" %><%@
page import="com.liferay.commerce.product.model.CPAttachmentFileEntry" %><%@
page import="com.liferay.commerce.product.model.CPDefinition" %><%@
page import="com.liferay.commerce.product.type.simple.internal.SimpleCPType" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %>

<%@ page import="java.util.Collections" %><%@
page import="java.util.HashMap" %><%@
page import="java.util.Map" %>

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
String languageId = LanguageUtil.getLanguageId(locale);
%>