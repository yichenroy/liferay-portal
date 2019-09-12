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

import com.liferay.osb.koroneiki.phloem.rest.dto.v1_0.AuditEntry;
import com.liferay.osb.koroneiki.phloem.rest.dto.v1_0.util.AuditEntryUtil;
import com.liferay.osb.koroneiki.phloem.rest.resource.v1_0.AuditEntryResource;
import com.liferay.osb.koroneiki.root.service.AuditEntryService;
import com.liferay.osb.koroneiki.taproot.model.Account;
import com.liferay.osb.koroneiki.taproot.model.Contact;
import com.liferay.osb.koroneiki.taproot.model.ContactRole;
import com.liferay.osb.koroneiki.taproot.model.Team;
import com.liferay.osb.koroneiki.taproot.model.TeamRole;
import com.liferay.osb.koroneiki.taproot.service.AccountLocalService;
import com.liferay.osb.koroneiki.taproot.service.ContactLocalService;
import com.liferay.osb.koroneiki.taproot.service.ContactRoleLocalService;
import com.liferay.osb.koroneiki.taproot.service.TeamLocalService;
import com.liferay.osb.koroneiki.taproot.service.TeamRoleLocalService;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Amos Fong
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/audit-entry.properties",
	scope = ServiceScope.PROTOTYPE, service = AuditEntryResource.class
)
public class AuditEntryResourceImpl extends BaseAuditEntryResourceImpl {

	@Override
	public Page<AuditEntry> getAccountAccountKeyAuditEntriesPage(
			String accountKey, Pagination pagination)
		throws Exception {

		Account account = _accountLocalService.getAccount(accountKey);

		return getAuditEntriesPage(
			Account.class, account.getAccountId(), pagination);
	}

	@Override
	public AuditEntry getAuditEntry(String auditEntryKey) throws Exception {
		return AuditEntryUtil.toAuditEntry(
			_auditEntryService.getAuditEntry(auditEntryKey));
	}

	@Override
	public Page<AuditEntry> getContactByOktaAuditEntriesPage(
			String oktaId, Pagination pagination)
		throws Exception {

		Contact contact = _contactLocalService.getContactByOktaId(oktaId);

		return getAuditEntriesPage(
			Contact.class, contact.getContactId(), pagination);
	}

	@Override
	public Page<AuditEntry> getContactByUuidAuditEntriesPage(
			String uuid, Pagination pagination)
		throws Exception {

		Contact contact = _contactLocalService.getContactByUuid(uuid);

		return getAuditEntriesPage(
			Contact.class, contact.getContactId(), pagination);
	}

	@Override
	public Page<AuditEntry> getContactRoleContactRoleKeyAuditEntriesPage(
			String contactRoleKey, Pagination pagination)
		throws Exception {

		ContactRole contactRole = _contactRoleLocalService.getContactRole(
			contactRoleKey);

		return getAuditEntriesPage(
			ContactRole.class, contactRole.getContactRoleId(), pagination);
	}

	@Override
	public Page<AuditEntry> getTeamRoleTeamRoleKeyAuditEntriesPage(
			String teamRoleKey, Pagination pagination)
		throws Exception {

		TeamRole teamRole = _teamRoleLocalService.getTeamRole(teamRoleKey);

		return getAuditEntriesPage(
			TeamRole.class, teamRole.getTeamRoleId(), pagination);
	}

	@Override
	public Page<AuditEntry> getTeamTeamKeyAuditEntriesPage(
			String teamKey, Pagination pagination)
		throws Exception {

		Team team = _teamLocalService.getTeam(teamKey);

		return getAuditEntriesPage(Team.class, team.getTeamId(), pagination);
	}

	protected Page<AuditEntry> getAuditEntriesPage(
			Class<?> clazz, long classPK, Pagination pagination)
		throws Exception {

		long classNameId = _classNameLocalService.getClassNameId(clazz);

		return Page.of(
			transform(
				_auditEntryService.getAuditEntries(
					classNameId, classPK, pagination.getStartPosition(),
					pagination.getEndPosition()),
				AuditEntryUtil::toAuditEntry),
			pagination,
			_auditEntryService.getAuditEntriesCount(classNameId, classPK));
	}

	@Reference
	private AccountLocalService _accountLocalService;

	@Reference
	private AuditEntryService _auditEntryService;

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private ContactLocalService _contactLocalService;

	@Reference
	private ContactRoleLocalService _contactRoleLocalService;

	@Reference
	private TeamLocalService _teamLocalService;

	@Reference
	private TeamRoleLocalService _teamRoleLocalService;

}