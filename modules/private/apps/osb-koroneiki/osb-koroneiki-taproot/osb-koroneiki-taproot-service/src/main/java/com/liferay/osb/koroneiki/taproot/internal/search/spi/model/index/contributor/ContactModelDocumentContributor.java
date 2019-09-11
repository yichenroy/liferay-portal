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
import com.liferay.osb.koroneiki.taproot.model.Contact;
import com.liferay.osb.koroneiki.taproot.model.ContactRole;
import com.liferay.osb.koroneiki.taproot.service.ContactRoleLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.ArrayUtil;
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
	property = "indexer.class.name=com.liferay.osb.koroneiki.taproot.model.Contact",
	service = ModelDocumentContributor.class
)
public class ContactModelDocumentContributor
	implements ModelDocumentContributor<Contact> {

	@Override
	public void contribute(Document document, Contact contact) {
		try {
			_contribute(document, contact);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	private void _contribute(Document document, Contact contact)
		throws PortalException {

		document.addKeyword(Field.COMPANY_ID, contact.getCompanyId());
		document.addDate(Field.CREATE_DATE, contact.getCreateDate());
		document.addDate(Field.MODIFIED_DATE, contact.getModifiedDate());

		document.addKeyword("contactKey", contact.getContactKey());
		document.addText("emailAddress", contact.getEmailAddress());
		document.addText("firstName", contact.getFirstName());
		document.addText("lastName", contact.getLastName());
		document.addText("middleName", contact.getMiddleName());
		document.addKeyword("oktaId", contact.getOktaId());
		document.addKeyword("uuid", contact.getUuid());

		document.addTextSortable("emailAddress", contact.getEmailAddress());
		document.addTextSortable("firstName", contact.getFirstName());
		document.addTextSortable("lastName", contact.getLastName());

		_contributeContactRoles(document, contact.getContactId());
		_contributeExternalLinks(document, contact.getContactId());
	}

	private void _contributeContactRoles(Document document, long contactId)
		throws PortalException {

		Set<String> contactRoleKeys = new HashSet<>();

		List<ContactRole> contactRoles =
			_contactRoleLocalService.getContactContactRoles(
				contactId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (ContactRole contactRole : contactRoles) {
			contactRoleKeys.add(contactRole.getContactRoleKey());
		}

		document.addKeyword(
			"contactRoleKeys", ArrayUtil.toStringArray(contactRoles.toArray()));
	}

	private void _contributeExternalLinks(Document document, long contactId)
		throws PortalException {

		Set<String> externalLinkDomains = new HashSet<>();
		Set<String> externalLinkEntityIds = new HashSet<>();
		Set<String> externalLinkEntityNames = new HashSet<>();

		List<ExternalLink> externalLinks =
			_externalLinkLocalService.getExternalLinks(
				Contact.class.getName(), contactId, QueryUtil.ALL_POS,
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

	private static final Log _log = LogFactoryUtil.getLog(
		ContactModelDocumentContributor.class);

	@Reference
	private ContactRoleLocalService _contactRoleLocalService;

	@Reference
	private ExternalLinkLocalService _externalLinkLocalService;

}