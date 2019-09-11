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

import com.liferay.osb.koroneiki.phloem.rest.dto.v1_0.ContactRole;

/**
 * @author Amos Fong
 */
public class ContactRoleUtil {

	public static ContactRole toContactRole(
			com.liferay.osb.koroneiki.taproot.model.ContactRole contactRole)
		throws Exception {

		return new ContactRole() {
			{
				dateCreated = contactRole.getCreateDate();
				dateModified = contactRole.getModifiedDate();
				description = contactRole.getDescription();
				key = contactRole.getContactRoleKey();
				name = contactRole.getName();
				system = contactRole.getSystem();
				type = Type.create(contactRole.getTypeLabel());
			}
		};
	}

}