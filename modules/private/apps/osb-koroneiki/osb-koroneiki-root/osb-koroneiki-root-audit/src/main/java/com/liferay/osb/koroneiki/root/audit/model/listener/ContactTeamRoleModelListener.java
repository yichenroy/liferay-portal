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

package com.liferay.osb.koroneiki.root.audit.model.listener;

import com.liferay.osb.koroneiki.root.audit.model.BaseAuditModelListener;
import com.liferay.osb.koroneiki.taproot.model.Contact;
import com.liferay.osb.koroneiki.taproot.model.ContactTeamRole;
import com.liferay.osb.koroneiki.taproot.model.Team;
import com.liferay.portal.kernel.model.ModelListener;

import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Kyle Bischof
 */
@Component(immediate = true, service = ModelListener.class)
public class ContactTeamRoleModelListener
	extends BaseAuditModelListener<ContactTeamRole> {

	@Override
	protected long getClassNameId(ContactTeamRole model) {
		return classNameLocalService.getClassNameId(Contact.class);
	}

	@Override
	protected long getClassPK(ContactTeamRole model) {
		Map<String, Object> attributes = model.getModelAttributes();

		return (Long)attributes.get("contactId");
	}

	@Override
	protected long getFieldClassNameId(ContactTeamRole model) {
		return classNameLocalService.getClassNameId(Team.class);
	}

	@Override
	protected long getFieldClassPK(ContactTeamRole model) {
		Map<String, Object> attributes = model.getModelAttributes();

		return (Long)attributes.get("teamId");
	}

}