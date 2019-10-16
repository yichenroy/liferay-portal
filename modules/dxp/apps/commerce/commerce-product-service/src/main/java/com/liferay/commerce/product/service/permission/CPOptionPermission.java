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

package com.liferay.commerce.product.service.permission;

import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.model.CPOption;
import com.liferay.commerce.product.model.CPOptionValue;
import com.liferay.commerce.product.service.CPOptionLocalService;
import com.liferay.commerce.product.service.CPOptionValueLocalService;
import com.liferay.exportimport.kernel.staging.permission.StagingPermissionUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.BaseModelPermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	property = {"model.class.name=com.liferay.commerce.product.model.CPOption"},
	service = BaseModelPermissionChecker.class
)
public class CPOptionPermission implements BaseModelPermissionChecker {

	public static void check(
			PermissionChecker permissionChecker, CPOption cpOption,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, cpOption, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, CPOption.class.getName(),
				cpOption.getCPOptionId(), actionId);
		}
	}

	public static void check(
			PermissionChecker permissionChecker, long cpOptionId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, cpOptionId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, CPOption.class.getName(), cpOptionId,
				actionId);
		}
	}

	public static void checkCPOptionValue(
			PermissionChecker permissionChecker, CPOptionValue cpOptionValue,
			String actionId)
		throws PortalException {

		long cpOptionId = cpOptionValue.getCPOptionId();

		check(permissionChecker, cpOptionId, actionId);
	}

	public static void checkCPOptionValue(
			PermissionChecker permissionChecker, long cpOptionValueId,
			String actionId)
		throws PortalException {

		CPOptionValue cpOptionValue =
			_cpOptionValueLocalService.getCPOptionValue(cpOptionValueId);

		checkCPOptionValue(permissionChecker, cpOptionValue, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, CPOption cpOption,
			String actionId)
		throws PortalException {

		Boolean hasPermission = StagingPermissionUtil.hasPermission(
			permissionChecker, cpOption.getGroupId(), CPOption.class.getName(),
			cpOption.getCPOptionId(), CPPortletKeys.COMMERCE_PRODUCT_OPTIONS,
			actionId);

		if (hasPermission != null) {
			return hasPermission.booleanValue();
		}

		if (permissionChecker.hasOwnerPermission(
				cpOption.getCompanyId(), CPOption.class.getName(),
				cpOption.getCPOptionId(), cpOption.getUserId(), actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			cpOption.getGroupId(), CPOption.class.getName(),
			cpOption.getCPOptionId(), actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long cpOptionId,
			String actionId)
		throws PortalException {

		CPOption cpOption = _cpOptionLocalService.getCPOption(cpOptionId);

		return contains(permissionChecker, cpOption, actionId);
	}

	@Override
	public void checkBaseModel(
			PermissionChecker permissionChecker, long groupId, long primaryKey,
			String actionId)
		throws PortalException {

		check(permissionChecker, primaryKey, actionId);
	}

	@Reference(unbind = "-")
	protected void setCPOptionLocalService(
		CPOptionLocalService cpOptionLocalService) {

		_cpOptionLocalService = cpOptionLocalService;
	}

	@Reference(unbind = "-")
	protected void setCPOptionValueLocalService(
		CPOptionValueLocalService
			cpOptionValueLocalService) {

		_cpOptionValueLocalService = cpOptionValueLocalService;
	}

	private static CPOptionLocalService _cpOptionLocalService;
	private static CPOptionValueLocalService _cpOptionValueLocalService;

}