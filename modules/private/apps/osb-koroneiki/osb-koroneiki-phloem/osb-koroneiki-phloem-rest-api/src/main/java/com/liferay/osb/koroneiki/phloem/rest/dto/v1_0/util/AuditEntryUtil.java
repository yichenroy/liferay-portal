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

package com.liferay.osb.koroneiki.phloem.rest.dto.v1_0.util;

import com.liferay.osb.koroneiki.phloem.rest.dto.v1_0.AuditEntry;
import com.liferay.portal.kernel.util.PortalUtil;

/**
 * @author Amos Fong
 */
public class AuditEntryUtil {

	public static AuditEntry toAuditEntry(
			com.liferay.osb.koroneiki.root.model.AuditEntry auditEntry)
		throws Exception {

		return new AuditEntry() {
			{
				action = Action.create(auditEntry.getAction());
				auditSetId = auditEntry.getAuditSetId();
				className = auditEntry.getClassName();
				classPK = auditEntry.getClassPK();
				dateCreated = auditEntry.getCreateDate();
				description = auditEntry.getDescription();
				field = auditEntry.getField();
				fieldClassName = PortalUtil.getClassName(
					auditEntry.getFieldClassNameId());
				fieldClassPK = auditEntry.getClassPK();
				key = auditEntry.getAuditEntryKey();
				newValue = auditEntry.getNewValue();
				oldValue = auditEntry.getOldValue();
				userId = auditEntry.getUserId();
				userName = auditEntry.getUserName();
			}
		};
	}

}