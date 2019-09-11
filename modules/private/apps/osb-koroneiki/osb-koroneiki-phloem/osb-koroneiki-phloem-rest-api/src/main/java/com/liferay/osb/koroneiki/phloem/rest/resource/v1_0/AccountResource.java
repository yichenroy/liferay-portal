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

package com.liferay.osb.koroneiki.phloem.rest.resource.v1_0;

import com.liferay.osb.koroneiki.phloem.rest.dto.v1_0.Account;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import javax.annotation.Generated;

import org.osgi.annotation.versioning.ProviderType;

/**
 * To access this resource, run:
 *
 *     curl -u your@email.com:yourpassword -D - http://localhost:8080/o/koroneiki-rest/v1.0
 *
 * @author Amos Fong
 * @generated
 */
@Generated("")
@ProviderType
public interface AccountResource {

	public Page<Account> getAccountsPage(
			String search, Filter filter, Pagination pagination, Sort[] sorts)
		throws Exception;

	public Account postAccount(Account account) throws Exception;

	public Page<Account> getAccountByExternalLinkDomainEntityNameEntity(
			String domain, String entityName, String entityId,
			Pagination pagination)
		throws Exception;

	public void deleteAccount(String accountKey) throws Exception;

	public Account getAccount(String accountKey, String[] includes)
		throws Exception;

	public Account putAccount(String accountKey, Account account)
		throws Exception;

	public Page<Account> getAccountChildAccountsPage(
			String accountKey, String[] includes, Pagination pagination)
		throws Exception;

	public Account postAccountChildAccount(String accountKey, Account account)
		throws Exception;

	public void deleteAccountContact(String accountKey, String[] contactUuids)
		throws Exception;

	public void putAccountContact(String accountKey, String[] contactUuids)
		throws Exception;

	public void deleteAccountContactContactUuidRole(
			String accountKey, String contactUuid, String[] contactRoleKeys)
		throws Exception;

	public void putAccountContactContactUuidRole(
			String accountKey, String contactUuid, String[] contactRoleKeys)
		throws Exception;

	public void deleteAccountTeamTeamKeyRole(
			String accountKey, String teamKey, String[] teamRoleKeys)
		throws Exception;

	public void putAccountTeamTeamKeyRole(
			String accountKey, String teamKey, String[] teamRoleKeys)
		throws Exception;

	public default void setContextAcceptLanguage(
		AcceptLanguage contextAcceptLanguage) {
	}

	public void setContextCompany(Company contextCompany);

	public void setContextUser(User contextUser);

}