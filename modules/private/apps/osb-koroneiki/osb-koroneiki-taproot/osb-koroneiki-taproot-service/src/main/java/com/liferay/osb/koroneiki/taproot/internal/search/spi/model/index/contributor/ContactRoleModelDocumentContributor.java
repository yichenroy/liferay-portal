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

package com.liferay.osb.koroneiki.taproot.internal.search.spi.model.index.contributor;

import com.liferay.osb.koroneiki.taproot.model.ContactRole;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import org.osgi.service.component.annotations.Component;

/**
 * @author Amos Fong
 */
@Component(
	immediate = true,
	property = "indexer.class.name=com.liferay.osb.koroneiki.taproot.model.ContactRole",
	service = ModelDocumentContributor.class
)
public class ContactRoleModelDocumentContributor
	implements ModelDocumentContributor<ContactRole> {

	@Override
	public void contribute(Document document, ContactRole contactRole) {
		document.addKeyword(Field.COMPANY_ID, contactRole.getCompanyId());
		document.addDate(Field.CREATE_DATE, contactRole.getCreateDate());
		document.addText(Field.DESCRIPTION, contactRole.getDescription());
		document.addDate(Field.MODIFIED_DATE, contactRole.getModifiedDate());
		document.addText(Field.NAME, contactRole.getName());
		document.addKeyword(Field.TYPE, contactRole.getTypeLabel());
		document.addKeyword(Field.USER_ID, contactRole.getUserId());

		document.addKeyword("contactRoleKey", contactRole.getContactRoleKey());
		document.addKeyword("system", contactRole.isSystem());

		document.addDateSortable(
			Field.CREATE_DATE, contactRole.getCreateDate());
		document.addDateSortable(
			Field.MODIFIED_DATE, contactRole.getModifiedDate());
		document.addTextSortable(Field.NAME, contactRole.getName());
	}

}