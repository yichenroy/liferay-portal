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

package com.liferay.osb.koroneiki.trunk.service.impl;

import com.liferay.osb.koroneiki.trunk.constants.TrunkActionKeys;
import com.liferay.osb.koroneiki.trunk.model.ProductEntry;
import com.liferay.osb.koroneiki.trunk.permission.ProductEntryPermission;
import com.liferay.osb.koroneiki.trunk.service.base.ProductEntryServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Kyle Bischof
 */
@Component(
	property = {
		"json.web.service.context.name=koroneiki",
		"json.web.service.context.path=ProductEntry"
	},
	service = AopService.class
)
public class ProductEntryServiceImpl extends ProductEntryServiceBaseImpl {

	public ProductEntry addProductEntry(String name) throws PortalException {
		_productEntryPermission.check(
			getPermissionChecker(), TrunkActionKeys.ADD_PRODUCT_ENTRY);

		return productEntryLocalService.addProductEntry(getUserId(), name);
	}

	@Override
	public ProductEntry deleteProductEntry(long productEntryId)
		throws PortalException {

		_productEntryPermission.check(
			getPermissionChecker(), productEntryId, ActionKeys.DELETE);

		return productEntryLocalService.deleteProductEntry(productEntryId);
	}

	@Override
	public ProductEntry deleteProductEntry(String productEntryKey)
		throws PortalException {

		ProductEntry productEntry = productEntryLocalService.getProductEntry(
			productEntryKey);

		_productEntryPermission.check(
			getPermissionChecker(), productEntry, ActionKeys.DELETE);

		return productEntryLocalService.deleteProductEntry(productEntry);
	}

	public List<ProductEntry> getProductEntries(int start, int end)
		throws PortalException {

		PermissionChecker permissionChecker = getPermissionChecker();

		if (!permissionChecker.isSignedIn()) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, ProductEntry.class.getName(), 0,
				ActionKeys.VIEW);
		}

		return productEntryLocalService.getProductEntries(start, end);
	}

	public int getProductEntriesCount() throws PortalException {
		PermissionChecker permissionChecker = getPermissionChecker();

		if (!permissionChecker.isSignedIn()) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, ProductEntry.class.getName(), 0,
				ActionKeys.VIEW);
		}

		return productEntryLocalService.getProductEntriesCount();
	}

	public ProductEntry getProductEntry(long productEntryId)
		throws PortalException {

		_productEntryPermission.check(
			getPermissionChecker(), productEntryId, ActionKeys.VIEW);

		return productEntryLocalService.getProductEntry(productEntryId);
	}

	public ProductEntry getProductEntry(String productEntryKey)
		throws PortalException {

		ProductEntry productEntry = productEntryLocalService.getProductEntry(
			productEntryKey);

		_productEntryPermission.check(
			getPermissionChecker(), productEntry, ActionKeys.VIEW);

		return productEntry;
	}

	public ProductEntry updateProductEntry(long productEntryId, String name)
		throws PortalException {

		_productEntryPermission.check(
			getPermissionChecker(), productEntryId, ActionKeys.UPDATE);

		return productEntryLocalService.updateProductEntry(
			productEntryId, name);
	}

	public ProductEntry updateProductEntry(String productEntryKey, String name)
		throws PortalException {

		ProductEntry productEntry = productEntryLocalService.getProductEntry(
			productEntryKey);

		_productEntryPermission.check(
			getPermissionChecker(), productEntry, ActionKeys.UPDATE);

		return productEntryLocalService.updateProductEntry(
			productEntry.getProductEntryId(), name);
	}

	@Reference
	private ProductEntryPermission _productEntryPermission;

}