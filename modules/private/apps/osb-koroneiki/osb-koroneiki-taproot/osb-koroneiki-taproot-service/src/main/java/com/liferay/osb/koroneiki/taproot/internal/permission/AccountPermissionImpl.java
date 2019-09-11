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

package com.liferay.osb.koroneiki.taproot.internal.permission;

import com.liferay.osb.koroneiki.root.permission.ModelPermission;
import com.liferay.osb.koroneiki.taproot.model.Account;
import com.liferay.osb.koroneiki.taproot.permission.AccountPermission;
import com.liferay.osb.koroneiki.taproot.service.AccountLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.ArrayUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Kyle Bischof
 */
@Component(
	immediate = true, service = {AccountPermission.class, ModelPermission.class}
)
public class AccountPermissionImpl
	implements AccountPermission, ModelPermission {

	public static final String RESOURCE_NAME_ACCOUNTS =
		"com.liferay.osb.koroneiki.taproot.accounts";

	@Override
	public void check(
			PermissionChecker permissionChecker, Account account,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, account, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, Account.class.getName(),
				account.getAccountId(), actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long accountId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, accountId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, Account.class.getName(), accountId,
				actionId);
		}
	}

	@Override
	public void check(PermissionChecker permissionChecker, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, RESOURCE_NAME_ACCOUNTS, 0, actionId);
		}
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, Account account,
			String actionId)
		throws PortalException {

		if (permissionChecker.hasOwnerPermission(
				account.getCompanyId(), Account.class.getName(),
				account.getAccountId(), account.getUserId(), actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			0, Account.class.getName(), account.getAccountId(), actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long accountId,
			String actionId)
		throws PortalException {

		if (contains(
				permissionChecker, _accountLocalService.getAccount(accountId),
				actionId)) {

			return true;
		}

		return false;
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long[] accountIds,
			String actionId)
		throws PortalException {

		if (ArrayUtil.isEmpty(accountIds)) {
			return false;
		}

		for (long accountId : accountIds) {
			if (!contains(permissionChecker, accountId, actionId)) {
				return false;
			}
		}

		return true;
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, String actionId)
		throws PortalException {

		return permissionChecker.hasPermission(
			0, RESOURCE_NAME_ACCOUNTS, RESOURCE_NAME_ACCOUNTS, actionId);
	}

	@Override
	public String getClassName() {
		return Account.class.getName();
	}

	@Reference
	private AccountLocalService _accountLocalService;

}