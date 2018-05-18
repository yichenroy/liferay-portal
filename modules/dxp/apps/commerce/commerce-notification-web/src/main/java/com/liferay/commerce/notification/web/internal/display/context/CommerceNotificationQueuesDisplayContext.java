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

package com.liferay.commerce.notification.web.internal.display.context;

import com.liferay.commerce.notification.model.CommerceNotificationQueue;
import com.liferay.commerce.notification.service.CommerceNotificationQueueService;
import com.liferay.commerce.notification.web.internal.admin.NotificationsCommerceAdminModule;
import com.liferay.commerce.notification.web.internal.display.context.util.CommerceNotificationsRequestHelper;
import com.liferay.commerce.notification.web.internal.util.CommerceNotificationsUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceNotificationQueuesDisplayContext {

	public CommerceNotificationQueuesDisplayContext(
		CommerceNotificationQueueService commerceNotificationQueueService,
		HttpServletRequest httpServletRequest,
		PortletResourcePermission portletResourcePermission) {

		_commerceNotificationQueueService = commerceNotificationQueueService;
		_portletResourcePermission = portletResourcePermission;

		_commerceNotificationsRequestHelper =
			new CommerceNotificationsRequestHelper(httpServletRequest);
	}

	public String getOrderByCol() {
		return ParamUtil.getString(
			_commerceNotificationsRequestHelper.getRequest(),
			SearchContainer.DEFAULT_ORDER_BY_COL_PARAM, "priority");
	}

	public String getOrderByType() {
		return ParamUtil.getString(
			_commerceNotificationsRequestHelper.getRequest(),
			SearchContainer.DEFAULT_ORDER_BY_TYPE_PARAM, "desc");
	}

	public PortletURL getPortletURL() {
		LiferayPortletResponse liferayPortletResponse =
			_commerceNotificationsRequestHelper.getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter(
			"commerceAdminModuleKey", NotificationsCommerceAdminModule.KEY);

		String delta = ParamUtil.getString(
			_commerceNotificationsRequestHelper.getRequest(), "delta");

		if (Validator.isNotNull(delta)) {
			portletURL.setParameter("delta", delta);
		}

		portletURL.setParameter("orderByCol", getOrderByCol());
		portletURL.setParameter("orderByType", getOrderByType());

		return portletURL;
	}

	public SearchContainer<CommerceNotificationQueue> getSearchContainer()
		throws PortalException {

		if (_searchContainer != null) {
			return _searchContainer;
		}

		_searchContainer = new SearchContainer<>(
			_commerceNotificationsRequestHelper.getLiferayPortletRequest(),
			getPortletURL(), null, "there-are-no-notification-queues");

		String orderByCol = getOrderByCol();
		String orderByType = getOrderByType();

		OrderByComparator<CommerceNotificationQueue> orderByComparator =
			CommerceNotificationsUtil.
				getCommerceNotificationQueueOrderByComparator(
					orderByCol, orderByType);

		_searchContainer.setOrderByCol(orderByCol);
		_searchContainer.setOrderByComparator(orderByComparator);
		_searchContainer.setOrderByType(orderByType);

		int total =
			_commerceNotificationQueueService.
				getCommerceNotificationQueuesCount(
					_commerceNotificationsRequestHelper.getScopeGroupId());
		List<CommerceNotificationQueue> results =
			_commerceNotificationQueueService.getCommerceNotificationQueues(
				_commerceNotificationsRequestHelper.getScopeGroupId(),
				_searchContainer.getStart(), _searchContainer.getEnd(),
				orderByComparator);

		_searchContainer.setTotal(total);
		_searchContainer.setResults(results);

		return _searchContainer;
	}

	public boolean hasPermission(String actionId) {
		return _portletResourcePermission.contains(
			_commerceNotificationsRequestHelper.getPermissionChecker(),
			_commerceNotificationsRequestHelper.getScopeGroupId(), actionId);
	}

	private final CommerceNotificationQueueService
		_commerceNotificationQueueService;
	private final CommerceNotificationsRequestHelper
		_commerceNotificationsRequestHelper;
	private final PortletResourcePermission _portletResourcePermission;
	private SearchContainer<CommerceNotificationQueue> _searchContainer;

}