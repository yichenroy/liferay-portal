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

import com.liferay.osb.koroneiki.phloem.rest.dto.v1_0.Contact;
import com.liferay.osb.koroneiki.phloem.rest.dto.v1_0.ContactRole;
import com.liferay.osb.koroneiki.phloem.rest.dto.v1_0.ExternalLink;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.util.Map;

/**
 * @author Amos Fong
 */
public class ContactUtil {

	public static Contact toContact(
			com.liferay.osb.koroneiki.taproot.model.Contact contact,
			Map<String, Object> includesContext)
		throws Exception {

		return new Contact() {
			{
				dateCreated = contact.getCreateDate();
				dateModified = contact.getModifiedDate();
				emailAddress = contact.getEmailAddress();
				externalLinks = TransformUtil.transformToArray(
					contact.getExternalLinks(),
					ExternalLinkUtil::toExternalLink, ExternalLink.class);
				firstName = contact.getFirstName();
				key = contact.getContactKey();
				languageId = contact.getLanguageId();
				lastName = contact.getLastName();
				oktaId = contact.getOktaId();
				uuid = contact.getUuid();

				setContactRoles(
					() -> {
						if (includesContext == null) {
							return null;
						}

						String[] includes = (String[])includesContext.get(
							"includes");

						if (!ArrayUtil.contains(includes, "contact-roles")) {
							return null;
						}

						String accountKey = (String)includesContext.get(
							"accountKey");

						return TransformUtil.transformToArray(
							contact.getContactRoles(accountKey),
							ContactRoleUtil::toContactRole, ContactRole.class);
					});
			}
		};
	}

}