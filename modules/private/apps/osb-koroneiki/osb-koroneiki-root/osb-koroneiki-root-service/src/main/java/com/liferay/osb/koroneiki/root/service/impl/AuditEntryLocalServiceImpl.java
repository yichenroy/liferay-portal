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

package com.liferay.osb.koroneiki.root.service.impl;

import com.liferay.osb.koroneiki.root.model.AuditEntry;
import com.liferay.osb.koroneiki.root.service.base.AuditEntryLocalServiceBaseImpl;
import com.liferay.osb.koroneiki.root.util.ModelKeyGenerator;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Kyle Bischof
 */
@Component(
	property = "model.class.name=com.liferay.osb.koroneiki.root.model.AuditEntry",
	service = AopService.class
)
public class AuditEntryLocalServiceImpl extends AuditEntryLocalServiceBaseImpl {

	public AuditEntry addAuditEntry(
			long userId, long classNameId, long classPK, long auditSetId,
			long fieldClassNameId, long fieldClassPK, String action,
			String field, String oldLabel, String oldValue, String newLabel,
			String newValue, String description)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		if (auditSetId <= 0) {
			auditSetId = getNextAuditSetId(classNameId, classPK);
		}

		long auditEntryId = counterLocalService.increment();

		AuditEntry auditEntry = auditEntryPersistence.create(auditEntryId);

		auditEntry.setCompanyId(user.getCompanyId());
		auditEntry.setUserId(userId);
		auditEntry.setUserName(user.getFullName());
		auditEntry.setAuditEntryKey(ModelKeyGenerator.generate(auditEntryId));
		auditEntry.setClassNameId(classNameId);
		auditEntry.setClassPK(classPK);
		auditEntry.setAuditSetId(auditSetId);
		auditEntry.setFieldClassNameId(fieldClassNameId);
		auditEntry.setFieldClassPK(fieldClassPK);
		auditEntry.setAction(action);
		auditEntry.setField(field);
		auditEntry.setOldLabel(oldLabel);
		auditEntry.setOldValue(oldValue);
		auditEntry.setNewLabel(newLabel);
		auditEntry.setNewValue(newValue);
		auditEntry.setDescription(description);

		return auditEntryPersistence.update(auditEntry);
	}

	public List<AuditEntry> getAuditEntries(
		long classNameId, long classPK, int start, int end) {

		return auditEntryPersistence.findByC_C(
			classNameId, classPK, start, end);
	}

	public int getAuditEntriesCount(long classNameId, long classPK) {
		return auditEntryPersistence.countByC_C(classNameId, classPK);
	}

	public AuditEntry getAuditEntry(String auditEntryKey)
		throws PortalException {

		return auditEntryPersistence.findByAuditEntryKey(auditEntryKey);
	}

	public long getNextAuditSetId(long classNameId, long classPK) {
		StringBundler sb = new StringBundler(5);

		sb.append(AuditEntry.class.getName());
		sb.append(StringPool.POUND);
		sb.append(classNameId);
		sb.append(StringPool.POUND);
		sb.append(classPK);

		return counterLocalService.increment(sb.toString());
	}

}