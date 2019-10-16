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
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.service.CPDefinitionOptionRelLocalService;
import com.liferay.commerce.product.service.CPDefinitionOptionValueRelLocalService;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting;
import com.liferay.commerce.product.type.virtual.service.CPDefinitionVirtualSettingLocalService;
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
	property = {"model.class.name=com.liferay.commerce.product.model.CPDefinition"},
	service = BaseModelPermissionChecker.class
)
public class CPDefinitionPermission implements BaseModelPermissionChecker {

	public static void check(
			PermissionChecker permissionChecker, CPDefinition cpDefinition,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, cpDefinition, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, CPDefinition.class.getName(),
				cpDefinition.getCPDefinitionId(), actionId);
		}
	}

	public static void check(
			PermissionChecker permissionChecker, long cpDefinitionId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, cpDefinitionId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, CPDefinition.class.getName(), cpDefinitionId,
				actionId);
		}
	}

	public static void checkCPDefinitionOptionRel(
			PermissionChecker permissionChecker,
			CPDefinitionOptionRel cpDefinitionOptionRel, String actionId)
		throws PortalException {

		long cpDefinitionId = cpDefinitionOptionRel.getCPDefinitionId();

		check(permissionChecker, cpDefinitionId, actionId);
	}

	public static void checkCPDefinitionOptionRel(
			PermissionChecker permissionChecker, long cpDefinitionOptionRelId,
			String actionId)
		throws PortalException {

		CPDefinitionOptionRel cpDefinitionOptionRel =
			_cpDefinitionOptionRelLocalService.getCPDefinitionOptionRel(
				cpDefinitionOptionRelId);

		checkCPDefinitionOptionRel(
			permissionChecker, cpDefinitionOptionRel, actionId);
	}

	public static void checkCPDefinitionOptionValueRel(
			PermissionChecker permissionChecker,
			CPDefinitionOptionValueRel cpDefinitionOptionValueRel,
			String actionId)
		throws PortalException {

		long cpDefinitionOptionRelId =
			cpDefinitionOptionValueRel.getCPDefinitionOptionRelId();

		checkCPDefinitionOptionRel(
			permissionChecker, cpDefinitionOptionRelId, actionId);
	}

	public static void checkCPDefinitionOptionValueRel(
			PermissionChecker permissionChecker,
			long cpDefinitionOptionValueRelId, String actionId)
		throws PortalException {

		CPDefinitionOptionValueRel cpDefinitionOptionValueRel =
			_cpDefinitionOptionValueRelLocalService.
				getCPDefinitionOptionValueRel(cpDefinitionOptionValueRelId);

		checkCPDefinitionOptionValueRel(
			permissionChecker, cpDefinitionOptionValueRel, actionId);
	}

	public static void checkCPDefinitionVirtualSetting(
			PermissionChecker permissionChecker,
			CPDefinitionVirtualSetting cpDefinitionVirtualSetting, String actionId)
		throws PortalException {

		long cpDefinitionId = cpDefinitionVirtualSetting.getCPDefinitionId();

		check(permissionChecker, cpDefinitionId, actionId);
	}

	public static void checkCPDefinitionVirtualSetting(
			PermissionChecker permissionChecker,
			long cpDefinitionVirtualSettingId, String actionId)
		throws PortalException {

		CPDefinitionVirtualSetting cpDefinitionVirtualSetting =
			_cpDefinitionVirtualSettingLocalService.getCPDefinitionVirtualSetting(
					cpDefinitionVirtualSettingId);

		checkCPDefinitionVirtualSetting(
			permissionChecker, cpDefinitionVirtualSetting, actionId);
	}

	public static void checkCPInstance(
			PermissionChecker permissionChecker, CPInstance cpInstance,
			String actionId)
		throws PortalException {

		long cpDefinitionId = cpInstance.getCPDefinitionId();

		check(permissionChecker, cpDefinitionId, actionId);
	}

	public static void checkCPInstance(
			PermissionChecker permissionChecker, long cpInstanceId,
			String actionId)
		throws PortalException {

		CPInstance cpInstance = _cpInstanceLocalService.getCPInstance(
			cpInstanceId);

		checkCPInstance(permissionChecker, cpInstance, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, CPDefinition cpDefinition,
			String actionId)
		throws PortalException {

		Boolean hasPermission = StagingPermissionUtil.hasPermission(
			permissionChecker, cpDefinition.getGroupId(),
			CPDefinition.class.getName(), cpDefinition.getCPDefinitionId(),
			CPPortletKeys.COMMERCE_PRODUCT_DEFINITIONS, actionId);

		if (hasPermission != null) {
			return hasPermission.booleanValue();
		}

		if (permissionChecker.hasOwnerPermission(
				cpDefinition.getCompanyId(), CPDefinition.class.getName(),
				cpDefinition.getCPDefinitionId(), cpDefinition.getUserId(),
				actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			cpDefinition.getGroupId(), CPDefinition.class.getName(),
			cpDefinition.getCPDefinitionId(), actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long cpDefinitionId,
			String actionId)
		throws PortalException {

		CPDefinition cpDefinition = _cpDefinitionLocalService.getCPDefinition(
			cpDefinitionId);

		return contains(permissionChecker, cpDefinition, actionId);
	}

	@Override
	public void checkBaseModel(
			PermissionChecker permissionChecker, long groupId, long primaryKey,
			String actionId)
		throws PortalException {

		check(permissionChecker, primaryKey, actionId);
	}

	@Reference(unbind = "-")
	protected void setCPDefinitionLocalService(
		CPDefinitionLocalService cpDefinitionLocalService) {

		_cpDefinitionLocalService = cpDefinitionLocalService;
	}

	@Reference(unbind = "-")
	protected void setCPDefinitionOptionRelLocalService(
		CPDefinitionOptionRelLocalService cpDefinitionOptionRelLocalService) {

		_cpDefinitionOptionRelLocalService = cpDefinitionOptionRelLocalService;
	}

	@Reference(unbind = "-")
	protected void setCPDefinitionOptionValueRelLocalService(
		CPDefinitionOptionValueRelLocalService
			cpDefinitionOptionValueRelLocalService) {

		_cpDefinitionOptionValueRelLocalService =
			cpDefinitionOptionValueRelLocalService;
	}

	@Reference(unbind = "-")
	protected void setCPDefinitionVirtualSettingLocalService(
		CPDefinitionVirtualSettingLocalService cpDefinitionVirtualSettingLocalService) {

		_cpDefinitionVirtualSettingLocalService = cpDefinitionVirtualSettingLocalService;
	}

	@Reference(unbind = "-")
	protected void setCPInstanceLocalService(
		CPInstanceLocalService cpInstanceLocalService) {

		_cpInstanceLocalService = cpInstanceLocalService;
	}

	private static CPDefinitionLocalService _cpDefinitionLocalService;
	private static CPDefinitionOptionRelLocalService
		_cpDefinitionOptionRelLocalService;
	private static CPDefinitionOptionValueRelLocalService
		_cpDefinitionOptionValueRelLocalService;
	private static CPDefinitionVirtualSettingLocalService _cpDefinitionVirtualSettingLocalService;
	private static CPInstanceLocalService _cpInstanceLocalService;

}