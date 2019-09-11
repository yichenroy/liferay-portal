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
import com.liferay.osb.koroneiki.taproot.exception.ContactEmailAddressException;
import com.liferay.osb.koroneiki.taproot.exception.ContactOktaIdException;
import com.liferay.osb.koroneiki.taproot.model.Contact;
import com.liferay.osb.koroneiki.taproot.service.base.ContactLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
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
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Kyle Bischof
 */
@Component(
	property = "model.class.name=com.liferay.osb.koroneiki.taproot.model.Contact",
	service = AopService.class
)
public class ContactLocalServiceImpl extends ContactLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	public Contact addContact(
			String uuid, long userId, String oktaId, String firstName,
			String middleName, String lastName, String emailAddress,
			String languageId)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		validate(0, oktaId, emailAddress);

		long contactId = counterLocalService.increment();

		Contact contact = contactPersistence.create(contactId);

		contact.setUuid(uuid);
		contact.setCompanyId(user.getCompanyId());
		contact.setUserId(userId);
		contact.setContactKey(ModelKeyGenerator.generate(contactId));
		contact.setOktaId(oktaId);
		contact.setFirstName(firstName);
		contact.setMiddleName(middleName);
		contact.setLastName(lastName);
		contact.setEmailAddress(emailAddress);
		contact.setLanguageId(languageId);

		contactPersistence.update(contact);

		// Resources

		resourceLocalService.addResources(
			contact.getCompanyId(), 0, userId, Contact.class.getName(),
			contact.getContactId(), false, false, false);

		return contact;
	}

	@Override
	public Contact deleteContact(Contact contact) throws PortalException {

		// Contact account roles

		contactAccountRolePersistence.removeByContactId(contact.getContactId());

		// Contact team roles

		contactTeamRolePersistence.removeByContactId(contact.getContactId());

		// External links

		long classNameId = classNameLocalService.getClassNameId(Contact.class);

		_externalLinkLocalService.deleteExternalLinks(
			classNameId, contact.getContactId());

		// Resources

		resourceLocalService.deleteResource(
			contact.getCompanyId(), Contact.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, contact.getContactId());

		return contactPersistence.remove(contact);
	}

	@Override
	public Contact deleteContact(long contactId) throws PortalException {
		return deleteContact(contactLocalService.getContact(contactId));
	}

	public Contact fetchContactByUuid(String uuid) throws PortalException {
		return contactPersistence.fetchByUuid_First(uuid, null);
	}

	public List<Contact> getAccountContacts(
		long accountId, int start, int end) {

		LinkedHashMap<String, Object> params = new LinkedHashMap<>();

		params.put("account", accountId);

		return contactFinder.findByFN_MN_LN_E(
			null, null, null, null, params, start, end);
	}

	public int getAccountContactsCount(long accountId) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<>();

		params.put("account", accountId);

		return contactFinder.countByFN_MN_LN_E(null, null, null, null, params);
	}

	public Contact getContactByContactKey(String contactKey)
		throws PortalException {

		return contactPersistence.findByContactKey(contactKey);
	}

	public Contact getContactByEmailAddress(String emailAddress)
		throws PortalException {

		return contactPersistence.findByEmailAddress(emailAddress);
	}

	public Contact getContactByOktaId(String oktaId) throws PortalException {
		return contactPersistence.findByOktaId(oktaId);
	}

	public Contact getContactByUuid(String uuid) throws PortalException {
		return contactPersistence.findByUuid_First(uuid, null);
	}

	public List<Contact> getTeamContacts(long teamId, int start, int end) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<>();

		params.put("team", teamId);

		return contactFinder.findByFN_MN_LN_E(
			null, null, null, null, params, start, end);
	}

	public int getTeamContactsCount(long teamId) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<>();

		params.put("team", teamId);

		return contactFinder.countByFN_MN_LN_E(null, null, null, null, params);
	}

	@Indexable(type = IndexableType.REINDEX)
	public Contact reindex(long contactId) throws PortalException {
		return contactPersistence.findByPrimaryKey(contactId);
	}

	public Hits search(
			long companyId, String keywords, int start, int end, Sort sort)
		throws PortalException {

		try {
			Indexer<Contact> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
				Contact.class);

			SearchContext searchContext = new SearchContext();

			searchContext.setAndSearch(false);

			Map<String, Serializable> attributes = new HashMap<>();

			attributes.put("contactRoleKeys", keywords);
			attributes.put("emailAddress", keywords);
			attributes.put("externalLinkEntityIds", keywords);
			attributes.put("firstName", keywords);
			attributes.put("lastName", keywords);
			attributes.put("middleName", keywords);
			attributes.put("oktaId", keywords);
			attributes.put("uuid", keywords);

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
	public Contact updateContact(
			long contactId, String uuid, String oktaId, String firstName,
			String middleName, String lastName, String emailAddress,
			String languageId)
		throws PortalException {

		validate(contactId, oktaId, emailAddress);

		Contact contact = contactPersistence.findByPrimaryKey(contactId);

		contact.setUuid(uuid);
		contact.setOktaId(oktaId);
		contact.setFirstName(firstName);
		contact.setMiddleName(middleName);
		contact.setLastName(lastName);
		contact.setEmailAddress(emailAddress);
		contact.setLanguageId(languageId);

		return contactPersistence.update(contact);
	}

	protected void validate(long contactId, String oktaId, String emailAddress)
		throws PortalException {

		if (Validator.isNull(emailAddress)) {
			throw new ContactEmailAddressException();
		}

		Contact contact = null;

		if (Validator.isNotNull(oktaId)) {
			contact = contactPersistence.fetchByOktaId(oktaId);
		}

		if ((contact != null) && (contact.getContactId() != contactId)) {
			throw new ContactOktaIdException.MustNotBeDuplicate(oktaId);
		}

		contact = contactPersistence.fetchByEmailAddress(emailAddress);

		if ((contact != null) && (contact.getContactId() != contactId)) {
			throw new ContactEmailAddressException.MustNotBeDuplicate(
				emailAddress);
		}
	}

	@Reference
	private ExternalLinkLocalService _externalLinkLocalService;

}