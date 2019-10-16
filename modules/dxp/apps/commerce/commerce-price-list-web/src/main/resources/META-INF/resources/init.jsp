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

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %><%@
taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.commerce.constants.CommercePortletKeys" %><%@
page import="com.liferay.commerce.constants.CommerceWebKeys" %><%@
page import="com.liferay.commerce.currency.model.CommerceCurrency" %><%@
page import="com.liferay.commerce.exception.CommercePriceListExpirationDateException" %><%@
page import="com.liferay.commerce.exception.NoSuchPriceEntryException" %><%@
page import="com.liferay.commerce.exception.NoSuchPriceListException" %><%@
page import="com.liferay.commerce.exception.NoSuchTierPriceEntryException" %><%@
page import="com.liferay.commerce.model.CommercePriceEntry" %><%@
page import="com.liferay.commerce.model.CommercePriceList" %><%@
page import="com.liferay.commerce.model.CommerceTierPriceEntry" %><%@
page import="com.liferay.commerce.price.list.web.internal.display.context.CPInstanceCommercePriceEntryDisplayContext" %><%@
page import="com.liferay.commerce.price.list.web.internal.display.context.CPInstanceCommerceTierPriceEntryDisplayContext" %><%@
page import="com.liferay.commerce.price.list.web.internal.display.context.CommercePriceEntryDisplayContext" %><%@
page import="com.liferay.commerce.price.list.web.internal.display.context.CommercePriceListDisplayContext" %><%@
page import="com.liferay.commerce.price.list.web.internal.display.context.CommerceTierPriceEntryDisplayContext" %><%@
page import="com.liferay.commerce.price.list.web.internal.servlet.taglib.ui.CommercePriceEntryFormNavigatorConstants" %><%@
page import="com.liferay.commerce.price.list.web.internal.servlet.taglib.ui.CommercePriceListFormNavigatorConstants" %><%@
page import="com.liferay.commerce.product.constants.CPPortletKeys" %><%@
page import="com.liferay.commerce.product.exception.NoSuchCPInstanceException" %><%@
page import="com.liferay.commerce.product.model.CPDefinition" %><%@
page import="com.liferay.commerce.product.model.CPInstance" %><%@
page import="com.liferay.portal.kernel.dao.search.ResultRow" %><%@
page import="com.liferay.portal.kernel.dao.search.SearchContainer" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.language.UnicodeLanguageUtil" %><%@
page import="com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.PortalUtil" %><%@
page import="com.liferay.portal.kernel.util.StringPool" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %><%@
page import="com.liferay.portal.kernel.workflow.WorkflowConstants" %>

<%@ page import="java.util.Collections" %><%@
page import="java.util.List" %>

<%@ page import="javax.portlet.PortletURL" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
String lifecycle = (String)request.getAttribute(liferayPortletRequest.LIFECYCLE_PHASE);

PortletURL priceListsURLObj = PortalUtil.getControlPanelPortletURL(request, CommercePortletKeys.COMMERCE_PRICE_LIST, lifecycle);

String priceListsURL = priceListsURLObj.toString();

String languageId = LanguageUtil.getLanguageId(locale);
%>