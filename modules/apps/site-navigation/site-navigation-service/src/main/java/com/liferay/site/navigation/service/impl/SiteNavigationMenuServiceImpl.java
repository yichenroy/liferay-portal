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

package com.liferay.site.navigation.service.impl;

import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.WildcardMode;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermissionFactory;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.site.navigation.constants.SiteNavigationActionKeys;
import com.liferay.site.navigation.constants.SiteNavigationConstants;
import com.liferay.site.navigation.model.SiteNavigationMenu;
import com.liferay.site.navigation.service.base.SiteNavigationMenuServiceBaseImpl;

import java.util.List;

/**
 * @author Pavel Savinov
 */
public class SiteNavigationMenuServiceImpl
	extends SiteNavigationMenuServiceBaseImpl {

	@Override
	public SiteNavigationMenu addSiteNavigationMenu(
			long groupId, String name, int type, ServiceContext serviceContext)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), groupId,
			SiteNavigationActionKeys.ADD_SITE_NAVIGATION_MENU);

		return siteNavigationMenuLocalService.addSiteNavigationMenu(
			getUserId(), groupId, name, type, serviceContext);
	}

	@Override
	public SiteNavigationMenu addSiteNavigationMenu(
			long groupId, String name, ServiceContext serviceContext)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), groupId,
			SiteNavigationActionKeys.ADD_SITE_NAVIGATION_MENU);

		return siteNavigationMenuLocalService.addSiteNavigationMenu(
			getUserId(), groupId, name, serviceContext);
	}

	@Override
	public SiteNavigationMenu deleteSiteNavigationMenu(
			long siteNavigationMenuId)
		throws PortalException {

		_siteNavigationMenuModelResourcePermission.check(
			getPermissionChecker(), siteNavigationMenuId, ActionKeys.DELETE);

		return siteNavigationMenuLocalService.deleteSiteNavigationMenu(
			siteNavigationMenuId);
	}

	@Override
	public SiteNavigationMenu fetchSiteNavigationMenu(long siteNavigationMenuId)
		throws PortalException {

		_siteNavigationMenuModelResourcePermission.check(
			getPermissionChecker(), siteNavigationMenuId, ActionKeys.VIEW);

		return siteNavigationMenuLocalService.fetchSiteNavigationMenu(
			siteNavigationMenuId);
	}

	@Override
	public List<SiteNavigationMenu> getSiteNavigationMenus(long groupId) {
		return siteNavigationMenuPersistence.filterFindByGroupId(groupId);
	}

	@Override
	public List<SiteNavigationMenu> getSiteNavigationMenus(
		long groupId, int start, int end, OrderByComparator orderByComparator) {

		return siteNavigationMenuPersistence.filterFindByGroupId(
			groupId, start, end, orderByComparator);
	}

	@Override
	public List<SiteNavigationMenu> getSiteNavigationMenus(
		long groupId, String keywords, int start, int end,
		OrderByComparator orderByComparator) {

		return siteNavigationMenuPersistence.filterFindByG_N(
			groupId, _customSQL.keywords(keywords, WildcardMode.SURROUND)[0],
			start, end, orderByComparator);
	}

	@Override
	public int getSiteNavigationMenusCount(long groupId) {
		return siteNavigationMenuPersistence.filterCountByGroupId(groupId);
	}

	@Override
	public int getSiteNavigationMenusCount(long groupId, String keywords) {
		return siteNavigationMenuPersistence.filterCountByG_N(
			groupId, _customSQL.keywords(keywords, WildcardMode.SURROUND)[0]);
	}

	@Override
	public SiteNavigationMenu updateSiteNavigationMenu(
			long siteNavigationMenuId, int type, boolean auto,
			ServiceContext serviceContext)
		throws PortalException {

		_siteNavigationMenuModelResourcePermission.check(
			getPermissionChecker(), siteNavigationMenuId, ActionKeys.UPDATE);

		return siteNavigationMenuLocalService.updateSiteNavigationMenu(
			getUserId(), siteNavigationMenuId, type, auto, serviceContext);
	}

	@Override
	public SiteNavigationMenu updateSiteNavigationMenu(
			long siteNavigationMenuId, String name,
			ServiceContext serviceContext)
		throws PortalException {

		_siteNavigationMenuModelResourcePermission.check(
			getPermissionChecker(), siteNavigationMenuId, ActionKeys.UPDATE);

		return siteNavigationMenuLocalService.updateSiteNavigationMenu(
			getUserId(), siteNavigationMenuId, name, serviceContext);
	}

	private static volatile PortletResourcePermission
		_portletResourcePermission =
			PortletResourcePermissionFactory.getInstance(
				SiteNavigationMenuServiceImpl.class,
				"_portletResourcePermission",
				SiteNavigationConstants.RESOURCE_NAME);
	private static volatile ModelResourcePermission<SiteNavigationMenu>
		_siteNavigationMenuModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				SiteNavigationMenuServiceImpl.class,
				"_siteNavigationMenuModelResourcePermission",
				SiteNavigationMenu.class);

	@ServiceReference(type = CustomSQL.class)
	private CustomSQL _customSQL;

}