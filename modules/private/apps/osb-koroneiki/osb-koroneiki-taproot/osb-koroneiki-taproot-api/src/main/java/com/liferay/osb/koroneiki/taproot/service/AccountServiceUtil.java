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

package com.liferay.osb.koroneiki.taproot.service;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for Account. This utility wraps
 * <code>com.liferay.osb.koroneiki.taproot.service.impl.AccountServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see AccountService
 * @generated
 */
public class AccountServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.osb.koroneiki.taproot.service.impl.AccountServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AccountServiceUtil} to access the account remote service. Add custom service methods to <code>com.liferay.osb.koroneiki.taproot.service.impl.AccountServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static com.liferay.osb.koroneiki.taproot.model.Account addAccount(
			long parentAccountId, String name, String code, String description,
			String notes, long logoId, String contactEmailAddress,
			String profileEmailAddress, String phoneNumber, String faxNumber,
			String website, String industry, String tier, String soldBy,
			int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addAccount(
			parentAccountId, name, code, description, notes, logoId,
			contactEmailAddress, profileEmailAddress, phoneNumber, faxNumber,
			website, industry, tier, soldBy, status);
	}

	public static com.liferay.osb.koroneiki.taproot.model.Account deleteAccount(
			long accountId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteAccount(accountId);
	}

	public static com.liferay.osb.koroneiki.taproot.model.Account deleteAccount(
			String accountKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteAccount(accountKey);
	}

	public static com.liferay.osb.koroneiki.taproot.model.Account getAccount(
			long accountId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getAccount(accountId);
	}

	public static com.liferay.osb.koroneiki.taproot.model.Account getAccount(
			String accountKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getAccount(accountKey);
	}

	public static java.util.List
		<com.liferay.osb.koroneiki.taproot.model.Account> getAccounts(
				long parentAccountId, int start, int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getAccounts(parentAccountId, start, end);
	}

	public static java.util.List
		<com.liferay.osb.koroneiki.taproot.model.Account> getAccounts(
				String domain, String entityName, String entityId, int start,
				int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getAccounts(
			domain, entityName, entityId, start, end);
	}

	public static int getAccountsCount(long parentAccountId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getAccountsCount(parentAccountId);
	}

	public static int getAccountsCount(
			String domain, String entityName, String entityId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getAccountsCount(domain, entityName, entityId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.osb.koroneiki.taproot.model.Account updateAccount(
			long accountId, long parentAccountId, String name, String code,
			String description, String notes, long logoId,
			String contactEmailAddress, String profileEmailAddress,
			String phoneNumber, String faxNumber, String website,
			String industry, String tier, String soldBy, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateAccount(
			accountId, parentAccountId, name, code, description, notes, logoId,
			contactEmailAddress, profileEmailAddress, phoneNumber, faxNumber,
			website, industry, tier, soldBy, status);
	}

	public static com.liferay.osb.koroneiki.taproot.model.Account updateAccount(
			String accountKey, long parentAccountId, String name, String code,
			String description, String notes, long logoId,
			String contactEmailAddress, String profileEmailAddress,
			String phoneNumber, String faxNumber, String website,
			String industry, String tier, String soldBy, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateAccount(
			accountKey, parentAccountId, name, code, description, notes, logoId,
			contactEmailAddress, profileEmailAddress, phoneNumber, faxNumber,
			website, industry, tier, soldBy, status);
	}

	public static AccountService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<AccountService, AccountService>
		_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(AccountService.class);

		ServiceTracker<AccountService, AccountService> serviceTracker =
			new ServiceTracker<AccountService, AccountService>(
				bundle.getBundleContext(), AccountService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}