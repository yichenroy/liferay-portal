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

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/clay" prefix="clay" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.frontend.taglib.clay.servlet.taglib.util.JSPCreationMenu" %><%@
page import="com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem" %><%@
page import="com.liferay.osb.koroneiki.scion.constants.ScionWebKeys" %><%@
page import="com.liferay.osb.koroneiki.scion.exception.NoSuchAuthenticationTokenException" %><%@
page import="com.liferay.osb.koroneiki.scion.exception.NoSuchServiceProducerException" %><%@
page import="com.liferay.osb.koroneiki.scion.exception.ServiceProducerNameException" %><%@
page import="com.liferay.osb.koroneiki.scion.model.AuthenticationToken" %><%@
page import="com.liferay.osb.koroneiki.scion.model.ServiceProducer" %><%@
page import="com.liferay.osb.koroneiki.scion.service.AuthenticationTokenLocalServiceUtil" %><%@
page import="com.liferay.osb.koroneiki.scion.service.ServiceProducerLocalServiceUtil" %><%@
page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.kernel.bean.BeanParamUtil" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.model.User" %><%@
page import="com.liferay.portal.kernel.service.UserLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.servlet.SessionErrors" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.PortalUtil" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %><%@
page import="com.liferay.portal.kernel.workflow.WorkflowConstants" %><%@
page import="com.liferay.taglib.search.ResultRow" %>

<%@ page import="java.util.ArrayList" %><%@
page import="java.util.List" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />