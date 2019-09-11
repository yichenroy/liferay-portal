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

<c:if test="<%= !SessionErrors.isEmpty(renderRequest) %>">
	<div class="alert alert-danger">
		<liferay-ui:icon
			icon="exclamation-full"
			markupView="lexicon"
		/>

		<c:choose>
			<c:when test="<%= SessionErrors.contains(renderRequest, NoSuchProductEntryException.class.getName()) %>">
				<liferay-ui:message key="the-product-could-not-be-found" />
			</c:when>
		</c:choose>
	</div>
</c:if>