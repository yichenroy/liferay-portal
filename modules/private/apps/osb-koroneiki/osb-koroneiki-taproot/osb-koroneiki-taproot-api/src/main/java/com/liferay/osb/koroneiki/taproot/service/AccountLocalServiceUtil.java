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
 * Provides the local service utility for Account. This utility wraps
 * <code>com.liferay.osb.koroneiki.taproot.service.impl.AccountLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see AccountLocalService
 * @generated
 */
public class AccountLocalServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.osb.koroneiki.taproot.service.impl.AccountLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the account to the database. Also notifies the appropriate model listeners.
	 *
	 * @param account the account
	 * @return the account that was added
	 */
	public static com.liferay.osb.koroneiki.taproot.model.Account addAccount(
		com.liferay.osb.koroneiki.taproot.model.Account account) {

		return getService().addAccount(account);
	}

	public static com.liferay.osb.koroneiki.taproot.model.Account addAccount(
			long userId, long parentAccountId, String name, String code,
			String description, String notes, long logoId,
			String contactEmailAddress, String profileEmailAddress,
			String phoneNumber, String faxNumber, String website,
			String industry, String tier, String soldBy, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addAccount(
			userId, parentAccountId, name, code, description, notes, logoId,
			contactEmailAddress, profileEmailAddress, phoneNumber, faxNumber,
			website, industry, tier, soldBy, status);
	}

	/**
	 * Creates a new account with the primary key. Does not add the account to the database.
	 *
	 * @param accountId the primary key for the new account
	 * @return the new account
	 */
	public static com.liferay.osb.koroneiki.taproot.model.Account createAccount(
		long accountId) {

		return getService().createAccount(accountId);
	}

	/**
	 * Deletes the account from the database. Also notifies the appropriate model listeners.
	 *
	 * @param account the account
	 * @return the account that was removed
	 * @throws PortalException
	 */
	public static com.liferay.osb.koroneiki.taproot.model.Account deleteAccount(
			com.liferay.osb.koroneiki.taproot.model.Account account)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteAccount(account);
	}

	/**
	 * Deletes the account with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param accountId the primary key of the account
	 * @return the account that was removed
	 * @throws PortalException if a account with the primary key could not be found
	 */
	public static com.liferay.osb.koroneiki.taproot.model.Account deleteAccount(
			long accountId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteAccount(accountId);
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			deletePersistedModel(
				com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery
		dynamicQuery() {

		return getService().dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.osb.koroneiki.taproot.model.impl.AccountModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.osb.koroneiki.taproot.model.impl.AccountModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static com.liferay.osb.koroneiki.taproot.model.Account fetchAccount(
		long accountId) {

		return getService().fetchAccount(accountId);
	}

	/**
	 * Returns the account with the matching UUID and company.
	 *
	 * @param uuid the account's UUID
	 * @param companyId the primary key of the company
	 * @return the matching account, or <code>null</code> if a matching account could not be found
	 */
	public static com.liferay.osb.koroneiki.taproot.model.Account
		fetchAccountByUuidAndCompanyId(String uuid, long companyId) {

		return getService().fetchAccountByUuidAndCompanyId(uuid, companyId);
	}

	/**
	 * Returns the account with the primary key.
	 *
	 * @param accountId the primary key of the account
	 * @return the account
	 * @throws PortalException if a account with the primary key could not be found
	 */
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

	/**
	 * Returns the account with the matching UUID and company.
	 *
	 * @param uuid the account's UUID
	 * @param companyId the primary key of the company
	 * @return the matching account
	 * @throws PortalException if a matching account could not be found
	 */
	public static com.liferay.osb.koroneiki.taproot.model.Account
			getAccountByUuidAndCompanyId(String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getAccountByUuidAndCompanyId(uuid, companyId);
	}

	/**
	 * Returns a range of all the accounts.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.osb.koroneiki.taproot.model.impl.AccountModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of accounts
	 * @param end the upper bound of the range of accounts (not inclusive)
	 * @return the range of accounts
	 */
	public static java.util.List
		<com.liferay.osb.koroneiki.taproot.model.Account> getAccounts(
			int start, int end) {

		return getService().getAccounts(start, end);
	}

	public static java.util.List
		<com.liferay.osb.koroneiki.taproot.model.Account> getAccounts(
			long parentAccountId, int start, int end) {

		return getService().getAccounts(parentAccountId, start, end);
	}

	/**
	 * Returns the number of accounts.
	 *
	 * @return the number of accounts
	 */
	public static int getAccountsCount() {
		return getService().getAccountsCount();
	}

	public static int getAccountsCount(long parentAccountId) {
		return getService().getAccountsCount(parentAccountId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static java.util.List
		<com.liferay.osb.koroneiki.taproot.model.Account> getContactAccounts(
			long contactId, int start, int end) {

		return getService().getContactAccounts(contactId, start, end);
	}

	public static int getContactAccountsCount(long contactId) {
		return getService().getContactAccountsCount(contactId);
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.model.PersistedModel
			getPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	public static com.liferay.osb.koroneiki.taproot.model.Account reindex(
			long accountId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().reindex(accountId);
	}

	public static com.liferay.portal.kernel.search.Hits search(
			long companyId, String keywords, int start, int end,
			com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().search(companyId, keywords, start, end, sort);
	}

	/**
	 * Updates the account in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param account the account
	 * @return the account that was updated
	 */
	public static com.liferay.osb.koroneiki.taproot.model.Account updateAccount(
		com.liferay.osb.koroneiki.taproot.model.Account account) {

		return getService().updateAccount(account);
	}

	public static com.liferay.osb.koroneiki.taproot.model.Account updateAccount(
			long userId, long accountId, long parentAccountId, String name,
			String code, String description, String notes, long logoId,
			String contactEmailAddress, String profileEmailAddress,
			String phoneNumber, String faxNumber, String website,
			String industry, String tier, String soldBy, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateAccount(
			userId, accountId, parentAccountId, name, code, description, notes,
			logoId, contactEmailAddress, profileEmailAddress, phoneNumber,
			faxNumber, website, industry, tier, soldBy, status);
	}

	public static AccountLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<AccountLocalService, AccountLocalService>
		_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(AccountLocalService.class);

		ServiceTracker<AccountLocalService, AccountLocalService>
			serviceTracker =
				new ServiceTracker<AccountLocalService, AccountLocalService>(
					bundle.getBundleContext(), AccountLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}