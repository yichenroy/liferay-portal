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

package com.liferay.osb.koroneiki.root.internal.permission;

import com.liferay.osb.koroneiki.root.permission.ModelPermission;
import com.liferay.osb.koroneiki.root.permission.ModelPermissionRegistry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.ClassNameLocalService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Amos Fong
 */
@Component(immediate = true, service = ModelPermissionRegistry.class)
public class ModelPermissionRegistryImpl implements ModelPermissionRegistry {

	public void check(
			PermissionChecker permissionChecker, long classNameId, long classPK,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, classNameId, classPK, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, String.valueOf(classNameId), classPK,
				actionId);
		}
	}

	public boolean contains(
			PermissionChecker permissionChecker, long classNameId, long classPK,
			String actionId)
		throws PortalException {

		ModelPermission modelPermission = _modelPermissions.get(classNameId);

		if (modelPermission != null) {
			return modelPermission.contains(
				permissionChecker, classPK, actionId);
		}

		return false;
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void registerModelPermission(ModelPermission modelPermission) {
		long classNameId = _classNameLocalService.getClassNameId(
			modelPermission.getClassName());

		_modelPermissions.put(classNameId, modelPermission);
	}

	protected void unregisterModelPermission(ModelPermission modelPermission) {
		long classNameId = _classNameLocalService.getClassNameId(
			modelPermission.getClassName());

		_modelPermissions.remove(classNameId);
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

	private final Map<Long, ModelPermission> _modelPermissions =
		new ConcurrentHashMap<>();

}