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

package com.liferay.osb.koroneiki.phloem.rest.internal.resource.v1_0;

import com.liferay.osb.koroneiki.phloem.rest.dto.v1_0.ContactRole;
import com.liferay.osb.koroneiki.phloem.rest.dto.v1_0.util.ContactRoleUtil;
import com.liferay.osb.koroneiki.phloem.rest.internal.odata.entity.v1_0.ContactRoleEntityModel;
import com.liferay.osb.koroneiki.phloem.rest.resource.v1_0.ContactRoleResource;
import com.liferay.osb.koroneiki.taproot.constants.ContactRoleType;
import com.liferay.osb.koroneiki.taproot.model.Account;
import com.liferay.osb.koroneiki.taproot.model.Contact;
import com.liferay.osb.koroneiki.taproot.service.AccountLocalService;
import com.liferay.osb.koroneiki.taproot.service.ContactLocalService;
import com.liferay.osb.koroneiki.taproot.service.ContactRoleLocalService;
import com.liferay.osb.koroneiki.taproot.service.ContactRoleService;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.vulcan.util.SearchUtil;

import javax.ws.rs.core.MultivaluedMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Amos Fong
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/contact-role.properties",
	scope = ServiceScope.PROTOTYPE, service = ContactRoleResource.class
)
public class ContactRoleResourceImpl
	extends BaseContactRoleResourceImpl implements EntityModelResource {

	@Override
	public void deleteContactRole(String contactRoleKey) throws Exception {
		_contactRoleService.deleteContactRole(contactRoleKey);
	}

	@Override
	public Page<ContactRole> getAccountAccountKeyContactContactUuidRolesPage(
			String accountKey, String contactUuid, Pagination pagination)
		throws Exception {

		Account account = _accountLocalService.getAccount(accountKey);
		Contact contact = _contactLocalService.getContactByUuid(contactUuid);

		return Page.of(
			transform(
				_contactRoleService.getContactAccountContactRoles(
					account.getAccountId(), contact.getContactId(),
					pagination.getStartPosition(), pagination.getEndPosition()),
				contactRole -> ContactRoleUtil.toContactRole(contactRole)),
			pagination,
			_contactRoleService.getContactAccountContactRolesCount(
				account.getAccountId(), contact.getContactId()));
	}

	@Override
	public ContactRole getContactRole(String contactRoleKey) throws Exception {
		return ContactRoleUtil.toContactRole(
			_contactRoleService.getContactRole(contactRoleKey));
	}

	@Override
	public Page<ContactRole> getContactRolesPage(
			String search, Filter filter, Pagination pagination, Sort[] sorts)
		throws Exception {

		return SearchUtil.search(
			booleanQuery -> {
			},
			filter, com.liferay.osb.koroneiki.taproot.model.ContactRole.class,
			search, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> searchContext.setCompanyId(
				contextCompany.getCompanyId()),
			document -> ContactRoleUtil.toContactRole(
				_contactRoleLocalService.getContactRole(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)))),
			sorts);
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap) {
		return _entityModel;
	}

	@Override
	public ContactRole postContactRole(ContactRole contactRole)
		throws Exception {

		ContactRole.Type contactRoleType = contactRole.getType();

		int type = ContactRoleType.fromLabel(contactRoleType.toString());

		return ContactRoleUtil.toContactRole(
			_contactRoleService.addContactRole(
				contactRole.getName(), contactRole.getDescription(), type));
	}

	@Override
	public ContactRole putContactRole(
			String contactRoleKey, ContactRole contactRole)
		throws Exception {

		com.liferay.osb.koroneiki.taproot.model.ContactRole curContactRole =
			_contactRoleLocalService.getContactRole(contactRoleKey);

		String description = GetterUtil.getString(
			contactRole.getDescription(), curContactRole.getDescription());

		return ContactRoleUtil.toContactRole(
			_contactRoleService.updateContactRole(
				curContactRole.getContactRoleId(), contactRole.getName(),
				description));
	}

	private static final EntityModel _entityModel =
		new ContactRoleEntityModel();

	@Reference
	private AccountLocalService _accountLocalService;

	@Reference
	private ContactLocalService _contactLocalService;

	@Reference
	private ContactRoleLocalService _contactRoleLocalService;

	@Reference
	private ContactRoleService _contactRoleService;

}