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

import com.liferay.osb.koroneiki.root.util.ModelKeyGenerator;
import com.liferay.osb.koroneiki.taproot.constants.ContactRoleSystem;
import com.liferay.osb.koroneiki.taproot.constants.ContactRoleType;
import com.liferay.osb.koroneiki.taproot.exception.ContactRoleNameException;
import com.liferay.osb.koroneiki.taproot.exception.ContactRoleSystemException;
import com.liferay.osb.koroneiki.taproot.exception.ContactRoleTypeException;
import com.liferay.osb.koroneiki.taproot.model.ContactRole;
import com.liferay.osb.koroneiki.taproot.service.base.ContactRoleLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.instances.service.PortalInstancesLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanClauseFactoryUtil;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.util.ArrayUtil;
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
	property = "model.class.name=com.liferay.osb.koroneiki.taproot.model.ContactRole",
	service = AopService.class
)
public class ContactRoleLocalServiceImpl
	extends ContactRoleLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	public ContactRole addContactRole(
			long userId, String name, String description, int type)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		validate(0, name, type);

		long contactRoleId = counterLocalService.increment();

		ContactRole contactRole = contactRolePersistence.create(contactRoleId);

		contactRole.setCompanyId(user.getCompanyId());
		contactRole.setUserId(userId);
		contactRole.setContactRoleKey(
			ModelKeyGenerator.generate(contactRoleId));
		contactRole.setName(name);
		contactRole.setDescription(description);
		contactRole.setType(type);

		contactRolePersistence.update(contactRole);

		// Resources

		resourceLocalService.addResources(
			contactRole.getCompanyId(), 0, userId, ContactRole.class.getName(),
			contactRole.getContactRoleId(), false, false, false);

		return contactRole;
	}

	public void checkMemberRoles() throws PortalException {
		long companyId = _portalInstancesLocalService.getDefaultCompanyId();

		User user = userLocalService.getDefaultUser(companyId);

		for (int type : ContactRoleType.VALUES) {
			ContactRole contactRole = contactRolePersistence.fetchByN_T(
				ContactRoleSystem.NAME_MEMBER, type);

			if (contactRole == null) {
				String description =
					"All users assigned to a " +
						ContactRoleType.getLabel(type) + " have this role.";

				contactRole = addContactRole(
					user.getUserId(), ContactRoleSystem.NAME_MEMBER,
					description, type);

				contactRole.setSystem(true);

				contactRole = contactRolePersistence.update(contactRole);

				contactRoleLocalService.reindex(contactRole.getContactRoleId());
			}

			_memberContactRoles.put(type, contactRole);
		}
	}

	@Override
	public ContactRole deleteContactRole(ContactRole contactRole)
		throws PortalException {

		if (contactRole.isSystem()) {
			throw new ContactRoleSystemException();
		}

		// Contact account roles

		contactAccountRolePersistence.removeByContactRoleId(
			contactRole.getContactRoleId());

		// Contact team roles

		contactTeamRolePersistence.removeByContactRoleId(
			contactRole.getContactRoleId());

		// Resources

		resourceLocalService.deleteResource(
			contactRole.getCompanyId(), ContactRole.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, contactRole.getContactRoleId());

		return contactRolePersistence.remove(contactRole);
	}

	@Override
	public ContactRole deleteContactRole(long contactRoleId)
		throws PortalException {

		return deleteContactRole(
			contactRoleLocalService.getContactRole(contactRoleId));
	}

	public List<ContactRole> getContactAccountContactRoles(
		long accountId, long contactId, int start, int end) {

		LinkedHashMap<String, Object> params = new LinkedHashMap<>();

		params.put("accountContact", new Long[] {accountId, contactId});

		return contactRoleFinder.findByName(null, params, start, end);
	}

	public int getContactAccountContactRolesCount(
		long accountId, long contactId) {

		LinkedHashMap<String, Object> params = new LinkedHashMap<>();

		params.put("accountContact", new Long[] {accountId, contactId});

		return contactRoleFinder.countByName(null, params);
	}

	public List<ContactRole> getContactContactRoles(
		long contactId, int start, int end) {

		LinkedHashMap<String, Object> params = new LinkedHashMap<>();

		params.put("contact", new Long[] {contactId, contactId});

		return contactRoleFinder.findByName(null, params, start, end);
	}

	public ContactRole getContactRole(String contactRoleKey)
		throws PortalException {

		return contactRolePersistence.findByContactRoleKey(contactRoleKey);
	}

	public List<ContactRole> getContactRoles(int type, int start, int end) {
		return contactRolePersistence.findByType(type, start, end);
	}

	public int getContactRolesCount(int type) {
		return contactRolePersistence.countByType(type);
	}

	public List<ContactRole> getContactTeamContactRoles(
		long teamId, long contactId) {

		LinkedHashMap<String, Object> params = new LinkedHashMap<>();

		params.put("teamContact", new Long[] {teamId, contactId});

		return contactRoleFinder.findByName(
			null, params, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public ContactRole getMemberContactRole(int type) {
		return _memberContactRoles.get(type);
	}

	@Indexable(type = IndexableType.REINDEX)
	public ContactRole reindex(long contactRoleId) throws PortalException {
		return contactRolePersistence.findByPrimaryKey(contactRoleId);
	}

	public Hits search(
			long companyId, int type, String keywords, int start, int end,
			Sort sort)
		throws PortalException {

		try {
			Indexer<ContactRole> indexer =
				IndexerRegistryUtil.nullSafeGetIndexer(ContactRole.class);

			SearchContext searchContext = new SearchContext();

			searchContext.setAndSearch(false);

			Map<String, Serializable> attributes = new HashMap<>();

			attributes.put("description", keywords);
			attributes.put("name", keywords);

			searchContext.setAttributes(attributes);

			BooleanQuery booleanQuery = new BooleanQueryImpl();

			booleanQuery.addExactTerm("type", ContactRoleType.getLabel(type));

			BooleanClause booleanClause = BooleanClauseFactoryUtil.create(
				booleanQuery, BooleanClauseOccur.MUST.getName());

			searchContext.setBooleanClauses(
				new BooleanClause[] {booleanClause});

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
	public ContactRole updateContactRole(
			long contactRoleId, String name, String description)
		throws PortalException {

		ContactRole contactRole = contactRolePersistence.findByPrimaryKey(
			contactRoleId);

		validate(contactRoleId, name, contactRole.getType());

		contactRole.setName(name);
		contactRole.setDescription(description);

		return contactRolePersistence.update(contactRole);
	}

	protected void validate(long contactRoleId, String name, int type)
		throws PortalException {

		if (Validator.isNull(name)) {
			throw new ContactRoleNameException();
		}

		if (!ArrayUtil.contains(ContactRoleType.VALUES, type)) {
			throw new ContactRoleTypeException();
		}

		ContactRole contactRole = contactRolePersistence.fetchByN_T(name, type);

		if ((contactRole != null) &&
			(contactRole.getContactRoleId() != contactRoleId)) {

			throw new ContactRoleNameException.MustNotBeDuplicate(name, type);
		}
	}

	private final Map<Integer, ContactRole> _memberContactRoles =
		new HashMap<>();

	@Reference
	private PortalInstancesLocalService _portalInstancesLocalService;

}