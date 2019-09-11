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

package com.liferay.osb.koroneiki.trunk.permission;

import com.liferay.osb.koroneiki.trunk.model.ProductConsumption;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

/**
 * @author Kyle Bischof
 */
public interface ProductConsumptionPermission {

	public void check(
			PermissionChecker permissionChecker, long productConsumptionId,
			String actionId)
		throws PortalException;

	public void check(
			PermissionChecker permissionChecker,
			ProductConsumption productConsumption, String actionId)
		throws PortalException;

	public boolean contains(
			PermissionChecker permissionChecker, long productConsumptionId,
			String actionId)
		throws PortalException;

	public boolean contains(
			PermissionChecker permissionChecker, long[] productConsumptionIds,
			String actionId)
		throws PortalException;

	public boolean contains(
			PermissionChecker permissionChecker,
			ProductConsumption productConsumption, String actionId)
		throws PortalException;

}