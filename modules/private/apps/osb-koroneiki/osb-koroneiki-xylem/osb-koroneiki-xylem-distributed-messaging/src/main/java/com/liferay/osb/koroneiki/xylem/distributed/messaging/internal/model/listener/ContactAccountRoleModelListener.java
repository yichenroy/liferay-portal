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

package com.liferay.osb.koroneiki.xylem.distributed.messaging.internal.model.listener;

import com.liferay.osb.distributed.messaging.Message;
import com.liferay.osb.koroneiki.taproot.model.ContactAccountRole;
import com.liferay.osb.koroneiki.taproot.model.ContactRole;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ModelListener;

import org.osgi.service.component.annotations.Component;

/**
 * @author Amos Fong
 */
@Component(immediate = true, service = ModelListener.class)
public class ContactAccountRoleModelListener
	extends BaseXylemModelListener<ContactAccountRole> {

	@Override
	public Message createMessage(ContactAccountRole contactAccountRole)
		throws Exception {

		return messageFactory.create(contactAccountRole);
	}

	@Override
	protected String getCreateTopic(ContactAccountRole contactAccountRole)
		throws PortalException {

		ContactRole contactRole = contactAccountRole.getContactRole();

		if (contactRole.isMember()) {
			return "koroneiki.account.contact.assigned";
		}

		return "koroneiki.account.contactrole.assigned";
	}

	@Override
	protected String getRemoveTopic(ContactAccountRole contactAccountRole)
		throws PortalException {

		ContactRole contactRole = contactAccountRole.getContactRole();

		if (contactRole.isMember()) {
			return "koroneiki.account.contact.unassigned";
		}

		return "koroneiki.account.contactrole.unassigned";
	}

}