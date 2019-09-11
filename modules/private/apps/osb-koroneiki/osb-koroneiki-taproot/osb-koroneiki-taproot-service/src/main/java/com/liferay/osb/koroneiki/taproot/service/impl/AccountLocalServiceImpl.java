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

package com.liferay.osb.koroneiki.taproot.service.impl;

import com.liferay.osb.koroneiki.root.service.ExternalLinkLocalService;
import com.liferay.osb.koroneiki.root.util.ModelKeyGenerator;
import com.liferay.osb.koroneiki.taproot.exception.AccountCodeException;
import com.liferay.osb.koroneiki.taproot.exception.AccountNameException;
import com.liferay.osb.koroneiki.taproot.exception.RequiredAccountException;
import com.liferay.osb.koroneiki.taproot.model.Account;
import com.liferay.osb.koroneiki.taproot.service.base.AccountLocalServiceBaseImpl;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Kyle Bischof
 */
@Component(
	property = "model.class.name=com.liferay.osb.koroneiki.taproot.model.Account",
	service = AopService.class
)
public class AccountLocalServiceImpl extends AccountLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public Account addAccount(Account account) {
		super.addAccount(account);

		try {
			addResources(
				account.getCompanyId(), account.getUserId(),
				account.getAccountId());
		}
		catch (PortalException pe) {
			throw new SystemException(pe);
		}

		return account;
	}

	@Indexable(type = IndexableType.REINDEX)
	public Account addAccount(
			long userId, long parentAccountId, String name, String code,
			String description, String notes, long logoId,
			String contactEmailAddress, String profileEmailAddress,
			String phoneNumber, String faxNumber, String website,
			String industry, String tier, String soldBy, int status)
		throws PortalException {

		User user = userLocalService.getUser(userId);
		code = StringUtil.toUpperCase(code);

		validate(0, name, code);

		long accountId = counterLocalService.increment();

		Account account = accountPersistence.create(accountId);

		account.setCompanyId(user.getCompanyId());
		account.setUserId(userId);
		account.setAccountKey(ModelKeyGenerator.generate(accountId));
		account.setParentAccountId(parentAccountId);
		account.setName(name);
		account.setCode(code);
		account.setDescription(description);
		account.setNotes(notes);
		account.setLogoId(logoId);
		account.setContactEmailAddress(contactEmailAddress);
		account.setProfileEmailAddress(profileEmailAddress);
		account.setPhoneNumber(phoneNumber);
		account.setFaxNumber(faxNumber);
		account.setWebsite(website);
		account.setIndustry(industry);
		account.setTier(tier);
		account.setSoldBy(soldBy);
		account.setStatus(status);
		account.setStatusByUserId(userId);
		account.setStatusByUserName(user.getFullName());
		account.setStatusDate(new Date());
		account.setStatusMessage(StringPool.BLANK);

		accountPersistence.update(account);

		addResources(account.getCompanyId(), userId, account.getAccountId());

		return account;
	}

	@Override
	public Account deleteAccount(Account account) throws PortalException {
		if (accountPersistence.countByParentAccountId(account.getAccountId()) >
				0) {

			throw new RequiredAccountException();
		}

		// Contact account roles

		contactAccountRolePersistence.removeByAccountId(account.getAccountId());

		// External links

		long classNameId = classNameLocalService.getClassNameId(Account.class);

		_externalLinkLocalService.deleteExternalLinks(
			classNameId, account.getAccountId());

		// Resources

		resourceLocalService.deleteResource(
			account.getCompanyId(), Account.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, account.getAccountId());

		// Teams

		teamPersistence.removeByAccountId(account.getAccountId());

		return accountPersistence.remove(account);
	}

	@Override
	public Account deleteAccount(long accountId) throws PortalException {
		return deleteAccount(accountLocalService.getAccount(accountId));
	}

	public Account getAccount(String accountKey) throws PortalException {
		return accountPersistence.findByAccountKey(accountKey);
	}

	public List<Account> getAccounts(long parentAccountId, int start, int end) {
		return accountPersistence.findByParentAccountId(
			parentAccountId, start, end);
	}

	public int getAccountsCount(long parentAccountId) {
		return accountPersistence.countByParentAccountId(parentAccountId);
	}

	public List<Account> getContactAccounts(
		long contactId, int start, int end) {

		return accountFinder.findByContact(contactId, start, end);
	}

	public int getContactAccountsCount(long contactId) {
		return accountFinder.countByContact(contactId);
	}

	@Indexable(type = IndexableType.REINDEX)
	public Account reindex(long accountId) throws PortalException {
		return accountPersistence.findByPrimaryKey(accountId);
	}

	public Hits search(
			long companyId, String keywords, int start, int end, Sort sort)
		throws PortalException {

		try {
			Indexer<Account> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
				Account.class);

			SearchContext searchContext = new SearchContext();

			searchContext.setAndSearch(false);

			Map<String, Serializable> attributes = new HashMap<>();

			attributes.put("code", keywords);
			attributes.put("contactUuids", keywords);
			attributes.put("description", keywords);
			attributes.put("externalLinkDomains", keywords);
			attributes.put("externalLinkEntityIds", keywords);
			attributes.put("externalLinkEntityNames", keywords);
			attributes.put("name", keywords);
			attributes.put("notes", keywords);
			attributes.put("productEntryKeys", keywords);

			searchContext.setAttributes(attributes);

			searchContext.setCompanyId(companyId);
			searchContext.setEnd(end);

			if (sort != null) {
				searchContext.setSorts(sort);
			}

			searchContext.setStart(start);

			QueryConfig queryConfig = searchContext.getQueryConfig();

			queryConfig.setHighlightEnabled(false);
			queryConfig.setScoreEnabled(false);

			return indexer.search(searchContext);
		}
		catch (Exception e) {
			throw new PortalException(e);
		}
	}

	@Indexable(type = IndexableType.REINDEX)
	public Account updateAccount(
			long userId, long accountId, long parentAccountId, String name,
			String code, String description, String notes, long logoId,
			String contactEmailAddress, String profileEmailAddress,
			String phoneNumber, String faxNumber, String website,
			String industry, String tier, String soldBy, int status)
		throws PortalException {

		User user = userLocalService.getUser(userId);
		code = StringUtil.toUpperCase(code);

		validate(accountId, name, code);

		Account account = accountPersistence.findByPrimaryKey(accountId);

		account.setParentAccountId(parentAccountId);
		account.setName(name);
		account.setCode(code);
		account.setDescription(description);
		account.setNotes(notes);
		account.setLogoId(logoId);
		account.setContactEmailAddress(contactEmailAddress);
		account.setProfileEmailAddress(profileEmailAddress);
		account.setPhoneNumber(phoneNumber);
		account.setFaxNumber(faxNumber);
		account.setWebsite(website);
		account.setIndustry(industry);
		account.setTier(tier);
		account.setSoldBy(soldBy);
		account.setStatus(status);
		account.setStatusByUserId(userId);
		account.setStatusByUserName(user.getFullName());
		account.setStatusDate(new Date());
		account.setStatusMessage(StringPool.BLANK);

		return accountPersistence.update(account);
	}

	protected void addResources(long companyId, long userId, long accountId)
		throws PortalException {

		resourceLocalService.addResources(
			companyId, 0, userId, Account.class.getName(), accountId, false,
			false, false);
	}

	protected void validate(long accountId, String name, String code)
		throws PortalException {

		if (Validator.isNull(name)) {
			throw new AccountNameException();
		}

		Account account = accountPersistence.fetchByName(name);

		if ((account != null) && (account.getAccountId() != accountId)) {
			throw new AccountNameException.MustNotBeDuplicate(name);
		}

		if (Validator.isNotNull(code)) {
			account = accountPersistence.fetchByCode(code);

			if ((account != null) && (account.getAccountId() != accountId)) {
				throw new AccountCodeException.MustNotBeDuplicate(code);
			}
		}
	}

	@Reference
	private ExternalLinkLocalService _externalLinkLocalService;

}