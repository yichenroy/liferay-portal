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

package com.liferay.osb.koroneiki.taproot.internal.search.spi.model.index.contributor;

import com.liferay.osb.koroneiki.root.model.ExternalLink;
import com.liferay.osb.koroneiki.root.service.ExternalLinkLocalService;
import com.liferay.osb.koroneiki.taproot.model.Account;
import com.liferay.osb.koroneiki.taproot.model.Contact;
import com.liferay.osb.koroneiki.taproot.model.ContactAccountRole;
import com.liferay.osb.koroneiki.taproot.model.ContactRole;
import com.liferay.osb.koroneiki.taproot.service.ContactAccountRoleLocalService;
import com.liferay.osb.koroneiki.taproot.service.ContactLocalService;
import com.liferay.osb.koroneiki.taproot.service.ContactRoleLocalService;
import com.liferay.osb.koroneiki.trunk.model.ProductPurchase;
import com.liferay.osb.koroneiki.trunk.service.ProductPurchaseLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Amos Fong
 */
@Component(
	immediate = true,
	property = "indexer.class.name=com.liferay.osb.koroneiki.taproot.model.Account",
	service = ModelDocumentContributor.class
)
public class AccountModelDocumentContributor
	implements ModelDocumentContributor<Account> {

	@Override
	public void contribute(Document document, Account account) {
		try {
			_contribute(document, account);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	private void _contribute(Document document, Account account)
		throws PortalException {

		document.addKeyword(Field.COMPANY_ID, account.getCompanyId());
		document.addDate(Field.CREATE_DATE, account.getCreateDate());
		document.addText(Field.DESCRIPTION, account.getDescription());
		document.addDate(Field.MODIFIED_DATE, account.getModifiedDate());
		document.addText(Field.NAME, account.getName());
		document.addKeyword(Field.STATUS, account.getStatus());
		document.addKeyword(Field.USER_ID, account.getUserId());

		document.addKeyword("accountKey", account.getAccountKey());
		document.addText("code", account.getCode());
		document.addKeyword(
			"contactEmailAddress", account.getContactEmailAddress());
		document.addKeyword("faxNumber", account.getFaxNumber());
		document.addKeyword("industry", account.getIndustry());
		document.addText("notes", account.getNotes());
		document.addKeyword("phoneNumber", account.getPhoneNumber());
		document.addKeyword(
			"profileEmailAddress", account.getProfileEmailAddress());
		document.addKeyword("soldBy", account.getSoldBy());
		document.addKeyword("tier", account.getTier());
		document.addKeyword("website", account.getWebsite());

		document.addDateSortable(Field.CREATE_DATE, account.getCreateDate());
		document.addDateSortable(
			Field.MODIFIED_DATE, account.getModifiedDate());
		document.addTextSortable(Field.NAME, account.getName());

		document.addTextSortable("code", account.getCode());

		_contributeContacts(document, account.getAccountId());
		_contributeExternalLinks(document, account.getAccountId());
		_contributeProductEntries(document, account.getAccountId());
	}

	private void _contributeContacts(Document document, long accountId)
		throws PortalException {

		Set<String> contactOktaIdContactRoleKeys = new HashSet<>();
		Set<String> contactOktaIds = new HashSet<>();
		Set<String> contactUuidContactRoleKeys = new HashSet<>();
		Set<String> contactUuids = new HashSet<>();

		List<ContactAccountRole> contactAccountRoles =
			_contactAccountRoleLocalService.getContactAccountRolesByAccountId(
				accountId);

		for (ContactAccountRole contactAccountRole : contactAccountRoles) {
			Contact contact = _contactLocalService.getContact(
				contactAccountRole.getContactId());
			ContactRole contactRole = _contactRoleLocalService.getContactRole(
				contactAccountRole.getContactRoleId());

			contactOktaIdContactRoleKeys.add(
				contact.getOktaId() + StringPool.UNDERLINE +
					contactRole.getContactRoleKey());

			contactOktaIds.add(contact.getOktaId());

			contactUuidContactRoleKeys.add(
				contact.getUuid() + StringPool.UNDERLINE +
					contactRole.getContactRoleKey());

			contactUuids.add(contact.getUuid());
		}

		document.addKeyword(
			"contactOktaIdContactRoleKeys",
			ArrayUtil.toStringArray(contactOktaIdContactRoleKeys.toArray()));
		document.addKeyword(
			"contactOktaIds",
			ArrayUtil.toStringArray(contactOktaIds.toArray()));
		document.addKeyword(
			"contactUuidContactRoleKeys",
			ArrayUtil.toStringArray(contactUuidContactRoleKeys.toArray()));
		document.addKeyword(
			"contactUuids", ArrayUtil.toStringArray(contactUuids.toArray()));
	}

	private void _contributeExternalLinks(Document document, long accountId)
		throws PortalException {

		Set<String> externalLinkDomains = new HashSet<>();
		Set<String> externalLinkEntityIds = new HashSet<>();
		Set<String> externalLinkEntityNames = new HashSet<>();

		List<ExternalLink> externalLinks =
			_externalLinkLocalService.getExternalLinks(
				Account.class.getName(), accountId, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		for (ExternalLink externalLink : externalLinks) {
			externalLinkDomains.add(externalLink.getDomain());
			externalLinkEntityIds.add(externalLink.getEntityId());
			externalLinkEntityNames.add(externalLink.getEntityName());
		}

		document.addKeyword(
			"externalLinkDomains",
			ArrayUtil.toStringArray(externalLinkDomains.toArray()));
		document.addKeyword(
			"externalLinkEntityIds",
			ArrayUtil.toStringArray(externalLinkEntityIds.toArray()));
		document.addKeyword(
			"externalLinkEntityNames",
			ArrayUtil.toStringArray(externalLinkEntityNames.toArray()));
	}

	private void _contributeProductEntries(Document document, long accountId)
		throws PortalException {

		Set<String> productEntryKeys = new HashSet<>();

		List<ProductPurchase> productPurchases =
			_productPurchaseLocalService.getAccountProductPurchases(
				accountId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (ProductPurchase productPurchase : productPurchases) {
			productEntryKeys.add(productPurchase.getProductEntryKey());
		}

		document.addKeyword(
			"productEntryKeys",
			ArrayUtil.toStringArray(productEntryKeys.toArray()));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AccountModelDocumentContributor.class);

	@Reference
	private ContactAccountRoleLocalService _contactAccountRoleLocalService;

	@Reference
	private ContactLocalService _contactLocalService;

	@Reference
	private ContactRoleLocalService _contactRoleLocalService;

	@Reference
	private ExternalLinkLocalService _externalLinkLocalService;

	@Reference
	private ProductPurchaseLocalService _productPurchaseLocalService;

}