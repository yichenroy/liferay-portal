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

package com.liferay.osb.koroneiki.phloem.rest.internal.graphql.query.v1_0;

import com.liferay.osb.koroneiki.phloem.rest.dto.v1_0.Account;
import com.liferay.osb.koroneiki.phloem.rest.dto.v1_0.AuditEntry;
import com.liferay.osb.koroneiki.phloem.rest.dto.v1_0.Contact;
import com.liferay.osb.koroneiki.phloem.rest.dto.v1_0.ContactRole;
import com.liferay.osb.koroneiki.phloem.rest.dto.v1_0.ExternalLink;
import com.liferay.osb.koroneiki.phloem.rest.dto.v1_0.PostalAddress;
import com.liferay.osb.koroneiki.phloem.rest.dto.v1_0.Product;
import com.liferay.osb.koroneiki.phloem.rest.dto.v1_0.ProductConsumption;
import com.liferay.osb.koroneiki.phloem.rest.dto.v1_0.ProductPurchase;
import com.liferay.osb.koroneiki.phloem.rest.dto.v1_0.Team;
import com.liferay.osb.koroneiki.phloem.rest.dto.v1_0.TeamRole;
import com.liferay.osb.koroneiki.phloem.rest.resource.v1_0.AccountResource;
import com.liferay.osb.koroneiki.phloem.rest.resource.v1_0.AuditEntryResource;
import com.liferay.osb.koroneiki.phloem.rest.resource.v1_0.ContactResource;
import com.liferay.osb.koroneiki.phloem.rest.resource.v1_0.ContactRoleResource;
import com.liferay.osb.koroneiki.phloem.rest.resource.v1_0.ExternalLinkResource;
import com.liferay.osb.koroneiki.phloem.rest.resource.v1_0.PostalAddressResource;
import com.liferay.osb.koroneiki.phloem.rest.resource.v1_0.ProductConsumptionResource;
import com.liferay.osb.koroneiki.phloem.rest.resource.v1_0.ProductPurchaseResource;
import com.liferay.osb.koroneiki.phloem.rest.resource.v1_0.ProductResource;
import com.liferay.osb.koroneiki.phloem.rest.resource.v1_0.TeamResource;
import com.liferay.osb.koroneiki.phloem.rest.resource.v1_0.TeamRoleResource;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import javax.annotation.Generated;

import org.osgi.service.component.ComponentServiceObjects;

/**
 * @author Amos Fong
 * @generated
 */
@Generated("")
public class Query {

	public static void setAccountResourceComponentServiceObjects(
		ComponentServiceObjects<AccountResource>
			accountResourceComponentServiceObjects) {

		_accountResourceComponentServiceObjects =
			accountResourceComponentServiceObjects;
	}

	public static void setAuditEntryResourceComponentServiceObjects(
		ComponentServiceObjects<AuditEntryResource>
			auditEntryResourceComponentServiceObjects) {

		_auditEntryResourceComponentServiceObjects =
			auditEntryResourceComponentServiceObjects;
	}

	public static void setContactResourceComponentServiceObjects(
		ComponentServiceObjects<ContactResource>
			contactResourceComponentServiceObjects) {

		_contactResourceComponentServiceObjects =
			contactResourceComponentServiceObjects;
	}

	public static void setContactRoleResourceComponentServiceObjects(
		ComponentServiceObjects<ContactRoleResource>
			contactRoleResourceComponentServiceObjects) {

		_contactRoleResourceComponentServiceObjects =
			contactRoleResourceComponentServiceObjects;
	}

	public static void setExternalLinkResourceComponentServiceObjects(
		ComponentServiceObjects<ExternalLinkResource>
			externalLinkResourceComponentServiceObjects) {

		_externalLinkResourceComponentServiceObjects =
			externalLinkResourceComponentServiceObjects;
	}

	public static void setPostalAddressResourceComponentServiceObjects(
		ComponentServiceObjects<PostalAddressResource>
			postalAddressResourceComponentServiceObjects) {

		_postalAddressResourceComponentServiceObjects =
			postalAddressResourceComponentServiceObjects;
	}

	public static void setProductResourceComponentServiceObjects(
		ComponentServiceObjects<ProductResource>
			productResourceComponentServiceObjects) {

		_productResourceComponentServiceObjects =
			productResourceComponentServiceObjects;
	}

	public static void setProductConsumptionResourceComponentServiceObjects(
		ComponentServiceObjects<ProductConsumptionResource>
			productConsumptionResourceComponentServiceObjects) {

		_productConsumptionResourceComponentServiceObjects =
			productConsumptionResourceComponentServiceObjects;
	}

	public static void setProductPurchaseResourceComponentServiceObjects(
		ComponentServiceObjects<ProductPurchaseResource>
			productPurchaseResourceComponentServiceObjects) {

		_productPurchaseResourceComponentServiceObjects =
			productPurchaseResourceComponentServiceObjects;
	}

	public static void setTeamResourceComponentServiceObjects(
		ComponentServiceObjects<TeamResource>
			teamResourceComponentServiceObjects) {

		_teamResourceComponentServiceObjects =
			teamResourceComponentServiceObjects;
	}

	public static void setTeamRoleResourceComponentServiceObjects(
		ComponentServiceObjects<TeamRoleResource>
			teamRoleResourceComponentServiceObjects) {

		_teamRoleResourceComponentServiceObjects =
			teamRoleResourceComponentServiceObjects;
	}

	@GraphQLField
	public AccountPage getAccountsPage(
			@GraphQLName("search") String search,
			@GraphQLName("filter") Filter filter,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page, @GraphQLName("sorts") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_accountResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountResource -> new AccountPage(
				accountResource.getAccountsPage(
					search, filter, Pagination.of(page, pageSize), sorts)));
	}

	@GraphQLField
	public AccountPage getAccountByExternalLinkDomainEntityNameEntity(
			@GraphQLName("domain") String domain,
			@GraphQLName("entityName") String entityName,
			@GraphQLName("entityId") String entityId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_accountResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountResource -> new AccountPage(
				accountResource.getAccountByExternalLinkDomainEntityNameEntity(
					domain, entityName, entityId,
					Pagination.of(page, pageSize))));
	}

	@GraphQLField
	public Account getAccount(
			@GraphQLName("accountKey") String accountKey,
			@GraphQLName("includes") String[] includes)
		throws Exception {

		return _applyComponentServiceObjects(
			_accountResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountResource -> accountResource.getAccount(
				accountKey, includes));
	}

	@GraphQLField
	public AccountPage getAccountChildAccountsPage(
			@GraphQLName("accountKey") String accountKey,
			@GraphQLName("includes") String[] includes,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_accountResourceComponentServiceObjects,
			this::_populateResourceContext,
			accountResource -> new AccountPage(
				accountResource.getAccountChildAccountsPage(
					accountKey, includes, Pagination.of(page, pageSize))));
	}

	@GraphQLField
	public AuditEntryPage getAccountAccountKeyAuditEntriesPage(
			@GraphQLName("accountKey") String accountKey,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_auditEntryResourceComponentServiceObjects,
			this::_populateResourceContext,
			auditEntryResource -> new AuditEntryPage(
				auditEntryResource.getAccountAccountKeyAuditEntriesPage(
					accountKey, Pagination.of(page, pageSize))));
	}

	@GraphQLField
	public AuditEntry getAuditEntry(
			@GraphQLName("auditEntryKey") String auditEntryKey)
		throws Exception {

		return _applyComponentServiceObjects(
			_auditEntryResourceComponentServiceObjects,
			this::_populateResourceContext,
			auditEntryResource -> auditEntryResource.getAuditEntry(
				auditEntryKey));
	}

	@GraphQLField
	public AuditEntryPage getContactRoleContactRoleKeyAuditEntriesPage(
			@GraphQLName("contactRoleKey") String contactRoleKey,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_auditEntryResourceComponentServiceObjects,
			this::_populateResourceContext,
			auditEntryResource -> new AuditEntryPage(
				auditEntryResource.getContactRoleContactRoleKeyAuditEntriesPage(
					contactRoleKey, Pagination.of(page, pageSize))));
	}

	@GraphQLField
	public AuditEntryPage getContactByOktaAuditEntriesPage(
			@GraphQLName("oktaId") String oktaId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_auditEntryResourceComponentServiceObjects,
			this::_populateResourceContext,
			auditEntryResource -> new AuditEntryPage(
				auditEntryResource.getContactByOktaAuditEntriesPage(
					oktaId, Pagination.of(page, pageSize))));
	}

	@GraphQLField
	public AuditEntryPage getContactByUuidAuditEntriesPage(
			@GraphQLName("uuid") String uuid,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_auditEntryResourceComponentServiceObjects,
			this::_populateResourceContext,
			auditEntryResource -> new AuditEntryPage(
				auditEntryResource.getContactByUuidAuditEntriesPage(
					uuid, Pagination.of(page, pageSize))));
	}

	@GraphQLField
	public AuditEntryPage getTeamRoleTeamRoleKeyAuditEntriesPage(
			@GraphQLName("teamRoleKey") String teamRoleKey,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_auditEntryResourceComponentServiceObjects,
			this::_populateResourceContext,
			auditEntryResource -> new AuditEntryPage(
				auditEntryResource.getTeamRoleTeamRoleKeyAuditEntriesPage(
					teamRoleKey, Pagination.of(page, pageSize))));
	}

	@GraphQLField
	public AuditEntryPage getTeamTeamKeyAuditEntriesPage(
			@GraphQLName("teamKey") String teamKey,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_auditEntryResourceComponentServiceObjects,
			this::_populateResourceContext,
			auditEntryResource -> new AuditEntryPage(
				auditEntryResource.getTeamTeamKeyAuditEntriesPage(
					teamKey, Pagination.of(page, pageSize))));
	}

	@GraphQLField
	public ContactPage getAccountAccountKeyContactsPage(
			@GraphQLName("accountKey") String accountKey,
			@GraphQLName("includes") String[] includes,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_contactResourceComponentServiceObjects,
			this::_populateResourceContext,
			contactResource -> new ContactPage(
				contactResource.getAccountAccountKeyContactsPage(
					accountKey, includes, Pagination.of(page, pageSize))));
	}

	@GraphQLField
	public ContactPage getContactsPage(
			@GraphQLName("search") String search,
			@GraphQLName("filter") Filter filter,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page, @GraphQLName("sorts") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_contactResourceComponentServiceObjects,
			this::_populateResourceContext,
			contactResource -> new ContactPage(
				contactResource.getContactsPage(
					search, filter, Pagination.of(page, pageSize), sorts)));
	}

	@GraphQLField
	public Contact getContactByEmailAddresEmailAddress(
			@GraphQLName("emailAddress") String emailAddress)
		throws Exception {

		return _applyComponentServiceObjects(
			_contactResourceComponentServiceObjects,
			this::_populateResourceContext,
			contactResource ->
				contactResource.getContactByEmailAddresEmailAddress(
					emailAddress));
	}

	@GraphQLField
	public Contact getContactByOkta(@GraphQLName("oktaId") String oktaId)
		throws Exception {

		return _applyComponentServiceObjects(
			_contactResourceComponentServiceObjects,
			this::_populateResourceContext,
			contactResource -> contactResource.getContactByOkta(oktaId));
	}

	@GraphQLField
	public Contact getContactByUuid(@GraphQLName("uuid") String uuid)
		throws Exception {

		return _applyComponentServiceObjects(
			_contactResourceComponentServiceObjects,
			this::_populateResourceContext,
			contactResource -> contactResource.getContactByUuid(uuid));
	}

	@GraphQLField
	public ContactRolePage getAccountAccountKeyContactContactUuidRolesPage(
			@GraphQLName("accountKey") String accountKey,
			@GraphQLName("contactUuid") String contactUuid,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_contactRoleResourceComponentServiceObjects,
			this::_populateResourceContext,
			contactRoleResource -> new ContactRolePage(
				contactRoleResource.
					getAccountAccountKeyContactContactUuidRolesPage(
						accountKey, contactUuid,
						Pagination.of(page, pageSize))));
	}

	@GraphQLField
	public ContactRolePage getContactRolesPage(
			@GraphQLName("search") String search,
			@GraphQLName("filter") Filter filter,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page, @GraphQLName("sorts") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_contactRoleResourceComponentServiceObjects,
			this::_populateResourceContext,
			contactRoleResource -> new ContactRolePage(
				contactRoleResource.getContactRolesPage(
					search, filter, Pagination.of(page, pageSize), sorts)));
	}

	@GraphQLField
	public ContactRole getContactRole(
			@GraphQLName("contactRoleKey") String contactRoleKey)
		throws Exception {

		return _applyComponentServiceObjects(
			_contactRoleResourceComponentServiceObjects,
			this::_populateResourceContext,
			contactRoleResource -> contactRoleResource.getContactRole(
				contactRoleKey));
	}

	@GraphQLField
	public ExternalLinkPage getAccountAccountKeyExternalLinksPage(
			@GraphQLName("accountKey") String accountKey,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_externalLinkResourceComponentServiceObjects,
			this::_populateResourceContext,
			externalLinkResource -> new ExternalLinkPage(
				externalLinkResource.getAccountAccountKeyExternalLinksPage(
					accountKey, Pagination.of(page, pageSize))));
	}

	@GraphQLField
	public ExternalLinkPage getContactByOktaExternalLinksPage(
			@GraphQLName("oktaId") String oktaId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_externalLinkResourceComponentServiceObjects,
			this::_populateResourceContext,
			externalLinkResource -> new ExternalLinkPage(
				externalLinkResource.getContactByOktaExternalLinksPage(
					oktaId, Pagination.of(page, pageSize))));
	}

	@GraphQLField
	public ExternalLinkPage getContactByUuidExternalLinksPage(
			@GraphQLName("uuid") String uuid,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_externalLinkResourceComponentServiceObjects,
			this::_populateResourceContext,
			externalLinkResource -> new ExternalLinkPage(
				externalLinkResource.getContactByUuidExternalLinksPage(
					uuid, Pagination.of(page, pageSize))));
	}

	@GraphQLField
	public ExternalLink getExternalLink(
			@GraphQLName("externalLinkKey") String externalLinkKey)
		throws Exception {

		return _applyComponentServiceObjects(
			_externalLinkResourceComponentServiceObjects,
			this::_populateResourceContext,
			externalLinkResource -> externalLinkResource.getExternalLink(
				externalLinkKey));
	}

	@GraphQLField
	public ExternalLinkPage
			getProductConsumptionProductConsumptionKeyExternalLinksPage(
				@GraphQLName("productConsumptionKey") String
					productConsumptionKey,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_externalLinkResourceComponentServiceObjects,
			this::_populateResourceContext,
			externalLinkResource -> new ExternalLinkPage(
				externalLinkResource.
					getProductConsumptionProductConsumptionKeyExternalLinksPage(
						productConsumptionKey, Pagination.of(page, pageSize))));
	}

	@GraphQLField
	public ExternalLinkPage
			getProductPurchaseProductPurchaseKeyExternalLinksPage(
				@GraphQLName("productPurchaseKey") String productPurchaseKey,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_externalLinkResourceComponentServiceObjects,
			this::_populateResourceContext,
			externalLinkResource -> new ExternalLinkPage(
				externalLinkResource.
					getProductPurchaseProductPurchaseKeyExternalLinksPage(
						productPurchaseKey, Pagination.of(page, pageSize))));
	}

	@GraphQLField
	public ExternalLinkPage getProductProductKeyExternalLinksPage(
			@GraphQLName("productKey") String productKey,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_externalLinkResourceComponentServiceObjects,
			this::_populateResourceContext,
			externalLinkResource -> new ExternalLinkPage(
				externalLinkResource.getProductProductKeyExternalLinksPage(
					productKey, Pagination.of(page, pageSize))));
	}

	@GraphQLField
	public ExternalLinkPage getTeamTeamKeyExternalLinksPage(
			@GraphQLName("teamKey") String teamKey,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_externalLinkResourceComponentServiceObjects,
			this::_populateResourceContext,
			externalLinkResource -> new ExternalLinkPage(
				externalLinkResource.getTeamTeamKeyExternalLinksPage(
					teamKey, Pagination.of(page, pageSize))));
	}

	@GraphQLField
	public PostalAddressPage getAccountAccountKeyPostalAddressesPage(
			@GraphQLName("accountKey") String accountKey)
		throws Exception {

		return _applyComponentServiceObjects(
			_postalAddressResourceComponentServiceObjects,
			this::_populateResourceContext,
			postalAddressResource -> new PostalAddressPage(
				postalAddressResource.getAccountAccountKeyPostalAddressesPage(
					accountKey)));
	}

	@GraphQLField
	public PostalAddress getPostalAddress(
			@GraphQLName("postalAddressId") Long postalAddressId)
		throws Exception {

		return _applyComponentServiceObjects(
			_postalAddressResourceComponentServiceObjects,
			this::_populateResourceContext,
			postalAddressResource -> postalAddressResource.getPostalAddress(
				postalAddressId));
	}

	@GraphQLField
	public ProductPage getProductsPage(
			@GraphQLName("search") String search,
			@GraphQLName("filter") Filter filter,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page, @GraphQLName("sorts") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_productResourceComponentServiceObjects,
			this::_populateResourceContext,
			productResource -> new ProductPage(
				productResource.getProductsPage(
					search, filter, Pagination.of(page, pageSize), sorts)));
	}

	@GraphQLField
	public Product getProduct(@GraphQLName("productKey") String productKey)
		throws Exception {

		return _applyComponentServiceObjects(
			_productResourceComponentServiceObjects,
			this::_populateResourceContext,
			productResource -> productResource.getProduct(productKey));
	}

	@GraphQLField
	public ProductConsumptionPage getAccountAccountKeyProductConsumptionsPage(
			@GraphQLName("accountKey") String accountKey,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_productConsumptionResourceComponentServiceObjects,
			this::_populateResourceContext,
			productConsumptionResource -> new ProductConsumptionPage(
				productConsumptionResource.
					getAccountAccountKeyProductConsumptionsPage(
						accountKey, Pagination.of(page, pageSize))));
	}

	@GraphQLField
	public ProductConsumptionPage getProductConsumptionsPage(
			@GraphQLName("search") String search,
			@GraphQLName("filter") Filter filter,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page, @GraphQLName("sorts") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_productConsumptionResourceComponentServiceObjects,
			this::_populateResourceContext,
			productConsumptionResource -> new ProductConsumptionPage(
				productConsumptionResource.getProductConsumptionsPage(
					search, filter, Pagination.of(page, pageSize), sorts)));
	}

	@GraphQLField
	public ProductConsumption getProductConsumption(
			@GraphQLName("productConsumptionKey") String productConsumptionKey)
		throws Exception {

		return _applyComponentServiceObjects(
			_productConsumptionResourceComponentServiceObjects,
			this::_populateResourceContext,
			productConsumptionResource ->
				productConsumptionResource.getProductConsumption(
					productConsumptionKey));
	}

	@GraphQLField
	public ProductPurchasePage getAccountAccountKeyProductPurchasesPage(
			@GraphQLName("accountKey") String accountKey,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_productPurchaseResourceComponentServiceObjects,
			this::_populateResourceContext,
			productPurchaseResource -> new ProductPurchasePage(
				productPurchaseResource.
					getAccountAccountKeyProductPurchasesPage(
						accountKey, Pagination.of(page, pageSize))));
	}

	@GraphQLField
	public ProductPurchasePage getProductPurchasesPage(
			@GraphQLName("search") String search,
			@GraphQLName("filter") Filter filter,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page, @GraphQLName("sorts") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_productPurchaseResourceComponentServiceObjects,
			this::_populateResourceContext,
			productPurchaseResource -> new ProductPurchasePage(
				productPurchaseResource.getProductPurchasesPage(
					search, filter, Pagination.of(page, pageSize), sorts)));
	}

	@GraphQLField
	public ProductPurchase getProductPurchase(
			@GraphQLName("productPurchaseKey") String productPurchaseKey)
		throws Exception {

		return _applyComponentServiceObjects(
			_productPurchaseResourceComponentServiceObjects,
			this::_populateResourceContext,
			productPurchaseResource ->
				productPurchaseResource.getProductPurchase(productPurchaseKey));
	}

	@GraphQLField
	public TeamPage getAccountAccountKeyTeamsPage(
			@GraphQLName("accountKey") String accountKey,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_teamResourceComponentServiceObjects,
			this::_populateResourceContext,
			teamResource -> new TeamPage(
				teamResource.getAccountAccountKeyTeamsPage(
					accountKey, Pagination.of(page, pageSize))));
	}

	@GraphQLField
	public TeamPage getTeamsPage(
			@GraphQLName("search") String search,
			@GraphQLName("filter") Filter filter,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page, @GraphQLName("sorts") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_teamResourceComponentServiceObjects,
			this::_populateResourceContext,
			teamResource -> new TeamPage(
				teamResource.getTeamsPage(
					search, filter, Pagination.of(page, pageSize), sorts)));
	}

	@GraphQLField
	public TeamPage getTeamByExternalLinkDomainEntityNameEntity(
			@GraphQLName("domain") String domain,
			@GraphQLName("entityName") String entityName,
			@GraphQLName("entityId") String entityId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_teamResourceComponentServiceObjects,
			this::_populateResourceContext,
			teamResource -> new TeamPage(
				teamResource.getTeamByExternalLinkDomainEntityNameEntity(
					domain, entityName, entityId,
					Pagination.of(page, pageSize))));
	}

	@GraphQLField
	public Team getTeam(@GraphQLName("teamKey") String teamKey)
		throws Exception {

		return _applyComponentServiceObjects(
			_teamResourceComponentServiceObjects,
			this::_populateResourceContext,
			teamResource -> teamResource.getTeam(teamKey));
	}

	@GraphQLField
	public TeamRole getTeamRole(@GraphQLName("teamRoleKey") String teamRoleKey)
		throws Exception {

		return _applyComponentServiceObjects(
			_teamRoleResourceComponentServiceObjects,
			this::_populateResourceContext,
			teamRoleResource -> teamRoleResource.getTeamRole(teamRoleKey));
	}

	@GraphQLName("AccountPage")
	public class AccountPage {

		public AccountPage(Page accountPage) {
			items = accountPage.getItems();
			page = accountPage.getPage();
			pageSize = accountPage.getPageSize();
			totalCount = accountPage.getTotalCount();
		}

		@GraphQLField
		protected java.util.Collection<Account> items;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("AuditEntryPage")
	public class AuditEntryPage {

		public AuditEntryPage(Page auditEntryPage) {
			items = auditEntryPage.getItems();
			page = auditEntryPage.getPage();
			pageSize = auditEntryPage.getPageSize();
			totalCount = auditEntryPage.getTotalCount();
		}

		@GraphQLField
		protected java.util.Collection<AuditEntry> items;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("ContactPage")
	public class ContactPage {

		public ContactPage(Page contactPage) {
			items = contactPage.getItems();
			page = contactPage.getPage();
			pageSize = contactPage.getPageSize();
			totalCount = contactPage.getTotalCount();
		}

		@GraphQLField
		protected java.util.Collection<Contact> items;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("ContactRolePage")
	public class ContactRolePage {

		public ContactRolePage(Page contactRolePage) {
			items = contactRolePage.getItems();
			page = contactRolePage.getPage();
			pageSize = contactRolePage.getPageSize();
			totalCount = contactRolePage.getTotalCount();
		}

		@GraphQLField
		protected java.util.Collection<ContactRole> items;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("ExternalLinkPage")
	public class ExternalLinkPage {

		public ExternalLinkPage(Page externalLinkPage) {
			items = externalLinkPage.getItems();
			page = externalLinkPage.getPage();
			pageSize = externalLinkPage.getPageSize();
			totalCount = externalLinkPage.getTotalCount();
		}

		@GraphQLField
		protected java.util.Collection<ExternalLink> items;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("PostalAddressPage")
	public class PostalAddressPage {

		public PostalAddressPage(Page postalAddressPage) {
			items = postalAddressPage.getItems();
			page = postalAddressPage.getPage();
			pageSize = postalAddressPage.getPageSize();
			totalCount = postalAddressPage.getTotalCount();
		}

		@GraphQLField
		protected java.util.Collection<PostalAddress> items;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("ProductPage")
	public class ProductPage {

		public ProductPage(Page productPage) {
			items = productPage.getItems();
			page = productPage.getPage();
			pageSize = productPage.getPageSize();
			totalCount = productPage.getTotalCount();
		}

		@GraphQLField
		protected java.util.Collection<Product> items;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("ProductConsumptionPage")
	public class ProductConsumptionPage {

		public ProductConsumptionPage(Page productConsumptionPage) {
			items = productConsumptionPage.getItems();
			page = productConsumptionPage.getPage();
			pageSize = productConsumptionPage.getPageSize();
			totalCount = productConsumptionPage.getTotalCount();
		}

		@GraphQLField
		protected java.util.Collection<ProductConsumption> items;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("ProductPurchasePage")
	public class ProductPurchasePage {

		public ProductPurchasePage(Page productPurchasePage) {
			items = productPurchasePage.getItems();
			page = productPurchasePage.getPage();
			pageSize = productPurchasePage.getPageSize();
			totalCount = productPurchasePage.getTotalCount();
		}

		@GraphQLField
		protected java.util.Collection<ProductPurchase> items;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("TeamPage")
	public class TeamPage {

		public TeamPage(Page teamPage) {
			items = teamPage.getItems();
			page = teamPage.getPage();
			pageSize = teamPage.getPageSize();
			totalCount = teamPage.getTotalCount();
		}

		@GraphQLField
		protected java.util.Collection<Team> items;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("TeamRolePage")
	public class TeamRolePage {

		public TeamRolePage(Page teamRolePage) {
			items = teamRolePage.getItems();
			page = teamRolePage.getPage();
			pageSize = teamRolePage.getPageSize();
			totalCount = teamRolePage.getTotalCount();
		}

		@GraphQLField
		protected java.util.Collection<TeamRole> items;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	private <T, R, E1 extends Throwable, E2 extends Throwable> R
			_applyComponentServiceObjects(
				ComponentServiceObjects<T> componentServiceObjects,
				UnsafeConsumer<T, E1> unsafeConsumer,
				UnsafeFunction<T, R, E2> unsafeFunction)
		throws E1, E2 {

		T resource = componentServiceObjects.getService();

		try {
			unsafeConsumer.accept(resource);

			return unsafeFunction.apply(resource);
		}
		finally {
			componentServiceObjects.ungetService(resource);
		}
	}

	private void _populateResourceContext(AccountResource accountResource)
		throws Exception {

		accountResource.setContextAcceptLanguage(_acceptLanguage);
		accountResource.setContextCompany(_company);
		accountResource.setContextUser(_user);
	}

	private void _populateResourceContext(AuditEntryResource auditEntryResource)
		throws Exception {

		auditEntryResource.setContextAcceptLanguage(_acceptLanguage);
		auditEntryResource.setContextCompany(_company);
		auditEntryResource.setContextUser(_user);
	}

	private void _populateResourceContext(ContactResource contactResource)
		throws Exception {

		contactResource.setContextAcceptLanguage(_acceptLanguage);
		contactResource.setContextCompany(_company);
		contactResource.setContextUser(_user);
	}

	private void _populateResourceContext(
			ContactRoleResource contactRoleResource)
		throws Exception {

		contactRoleResource.setContextAcceptLanguage(_acceptLanguage);
		contactRoleResource.setContextCompany(_company);
		contactRoleResource.setContextUser(_user);
	}

	private void _populateResourceContext(
			ExternalLinkResource externalLinkResource)
		throws Exception {

		externalLinkResource.setContextAcceptLanguage(_acceptLanguage);
		externalLinkResource.setContextCompany(_company);
		externalLinkResource.setContextUser(_user);
	}

	private void _populateResourceContext(
			PostalAddressResource postalAddressResource)
		throws Exception {

		postalAddressResource.setContextAcceptLanguage(_acceptLanguage);
		postalAddressResource.setContextCompany(_company);
		postalAddressResource.setContextUser(_user);
	}

	private void _populateResourceContext(ProductResource productResource)
		throws Exception {

		productResource.setContextAcceptLanguage(_acceptLanguage);
		productResource.setContextCompany(_company);
		productResource.setContextUser(_user);
	}

	private void _populateResourceContext(
			ProductConsumptionResource productConsumptionResource)
		throws Exception {

		productConsumptionResource.setContextAcceptLanguage(_acceptLanguage);
		productConsumptionResource.setContextCompany(_company);
		productConsumptionResource.setContextUser(_user);
	}

	private void _populateResourceContext(
			ProductPurchaseResource productPurchaseResource)
		throws Exception {

		productPurchaseResource.setContextAcceptLanguage(_acceptLanguage);
		productPurchaseResource.setContextCompany(_company);
		productPurchaseResource.setContextUser(_user);
	}

	private void _populateResourceContext(TeamResource teamResource)
		throws Exception {

		teamResource.setContextAcceptLanguage(_acceptLanguage);
		teamResource.setContextCompany(_company);
		teamResource.setContextUser(_user);
	}

	private void _populateResourceContext(TeamRoleResource teamRoleResource)
		throws Exception {

		teamRoleResource.setContextAcceptLanguage(_acceptLanguage);
		teamRoleResource.setContextCompany(_company);
		teamRoleResource.setContextUser(_user);
	}

	private static ComponentServiceObjects<AccountResource>
		_accountResourceComponentServiceObjects;
	private static ComponentServiceObjects<AuditEntryResource>
		_auditEntryResourceComponentServiceObjects;
	private static ComponentServiceObjects<ContactResource>
		_contactResourceComponentServiceObjects;
	private static ComponentServiceObjects<ContactRoleResource>
		_contactRoleResourceComponentServiceObjects;
	private static ComponentServiceObjects<ExternalLinkResource>
		_externalLinkResourceComponentServiceObjects;
	private static ComponentServiceObjects<PostalAddressResource>
		_postalAddressResourceComponentServiceObjects;
	private static ComponentServiceObjects<ProductResource>
		_productResourceComponentServiceObjects;
	private static ComponentServiceObjects<ProductConsumptionResource>
		_productConsumptionResourceComponentServiceObjects;
	private static ComponentServiceObjects<ProductPurchaseResource>
		_productPurchaseResourceComponentServiceObjects;
	private static ComponentServiceObjects<TeamResource>
		_teamResourceComponentServiceObjects;
	private static ComponentServiceObjects<TeamRoleResource>
		_teamRoleResourceComponentServiceObjects;

	private AcceptLanguage _acceptLanguage;
	private Company _company;
	private User _user;

}