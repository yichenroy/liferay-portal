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

import com.liferay.osb.koroneiki.taproot.model.Account;
import com.liferay.osb.koroneiki.taproot.permission.AccountPermission;
import com.liferay.osb.koroneiki.taproot.service.AccountLocalService;
import com.liferay.osb.koroneiki.trunk.constants.TrunkActionKeys;
import com.liferay.osb.koroneiki.trunk.exception.NoSuchProductConsumptionException;
import com.liferay.osb.koroneiki.trunk.model.ProductConsumption;
import com.liferay.osb.koroneiki.trunk.model.ProductEntry;
import com.liferay.osb.koroneiki.trunk.model.ProductField;
import com.liferay.osb.koroneiki.trunk.permission.ProductConsumptionPermission;
import com.liferay.osb.koroneiki.trunk.permission.ProductEntryPermission;
import com.liferay.osb.koroneiki.trunk.service.ProductEntryLocalService;
import com.liferay.osb.koroneiki.trunk.service.base.ProductConsumptionServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Kyle Bischof
 */
@Component(
	property = {
		"json.web.service.context.name=koroneiki",
		"json.web.service.context.path=ProductConsumption"
	},
	service = AopService.class
)
public class ProductConsumptionServiceImpl
	extends ProductConsumptionServiceBaseImpl {

	public ProductConsumption addProductConsumption(
			long accountId, long productEntryId,
			List<ProductField> productFields)
		throws PortalException {

		_productEntryPermission.check(
			getPermissionChecker(), productEntryId, TrunkActionKeys.CONSUME);

		return productConsumptionLocalService.addProductConsumption(
			getUserId(), accountId, productEntryId, productFields);
	}

	public ProductConsumption addProductConsumption(
			String accountKey, String productEntryKey,
			List<ProductField> productFields)
		throws PortalException {

		ProductEntry productEntry = _productEntryLocalService.getProductEntry(
			productEntryKey);

		_productEntryPermission.check(
			getPermissionChecker(), productEntry, TrunkActionKeys.CONSUME);

		Account account = _accountLocalService.getAccount(accountKey);

		return productConsumptionLocalService.addProductConsumption(
			getUserId(), account.getAccountId(),
			productEntry.getProductEntryId(), productFields);
	}

	public ProductConsumption deleteProductConsumption(
			long productConsumptionId)
		throws PortalException {

		_productConsumptionPermission.check(
			getPermissionChecker(), productConsumptionId, ActionKeys.DELETE);

		return productConsumptionLocalService.deleteProductConsumption(
			productConsumptionId);
	}

	public ProductConsumption deleteProductConsumption(
			long accountId, long productEntryId)
		throws PortalException {

		List<ProductConsumption> productConsumptions =
			productConsumptionLocalService.getProductConsumptions(
				getUserId(), accountId, productEntryId);

		if (productConsumptions.isEmpty()) {
			throw new NoSuchProductConsumptionException();
		}

		ProductConsumption productConsumption = productConsumptions.get(0);

		_productConsumptionPermission.check(
			getPermissionChecker(), productConsumption, ActionKeys.DELETE);

		return productConsumptionLocalService.deleteProductConsumption(
			productConsumption.getProductConsumptionId());
	}

	public ProductConsumption deleteProductConsumption(
			String productConsumptionKey)
		throws PortalException {

		ProductConsumption productConsumption =
			productConsumptionLocalService.getProductConsumption(
				productConsumptionKey);

		_productConsumptionPermission.check(
			getPermissionChecker(), productConsumption, ActionKeys.DELETE);

		return productConsumptionLocalService.deleteProductConsumption(
			productConsumption);
	}

	public List<ProductConsumption> getAccountProductConsumptions(
			long accountId, int start, int end)
		throws PortalException {

		_accountPermission.check(
			getPermissionChecker(), accountId, ActionKeys.VIEW);

		return productConsumptionLocalService.getAccountProductConsumptions(
			accountId, start, end);
	}

	public List<ProductConsumption> getAccountProductConsumptions(
			String accountKey, int start, int end)
		throws PortalException {

		Account account = _accountLocalService.getAccount(accountKey);

		_accountPermission.check(
			getPermissionChecker(), account, ActionKeys.VIEW);

		return productConsumptionLocalService.getAccountProductConsumptions(
			account.getAccountId(), start, end);
	}

	public int getAccountProductConsumptionsCount(long accountId)
		throws PortalException {

		_accountPermission.check(
			getPermissionChecker(), accountId, ActionKeys.VIEW);

		return productConsumptionLocalService.
			getAccountProductConsumptionsCount(accountId);
	}

	public int getAccountProductConsumptionsCount(String accountKey)
		throws PortalException {

		Account account = _accountLocalService.getAccount(accountKey);

		_accountPermission.check(
			getPermissionChecker(), account, ActionKeys.VIEW);

		return productConsumptionLocalService.
			getAccountProductConsumptionsCount(account.getAccountId());
	}

	public ProductConsumption getProductConsumption(long productConsumptionId)
		throws PortalException {

		_productConsumptionPermission.check(
			getPermissionChecker(), productConsumptionId, ActionKeys.VIEW);

		return productConsumptionLocalService.getProductConsumption(
			productConsumptionId);
	}

	public ProductConsumption getProductConsumption(
			String productConsumptionKey)
		throws PortalException {

		ProductConsumption productConsumption =
			productConsumptionLocalService.getProductConsumption(
				productConsumptionKey);

		_productConsumptionPermission.check(
			getPermissionChecker(), productConsumption, ActionKeys.VIEW);

		return productConsumption;
	}

	@Reference
	private AccountLocalService _accountLocalService;

	@Reference
	private AccountPermission _accountPermission;

	@Reference
	private ProductConsumptionPermission _productConsumptionPermission;

	@Reference
	private ProductEntryLocalService _productEntryLocalService;

	@Reference
	private ProductEntryPermission _productEntryPermission;

}