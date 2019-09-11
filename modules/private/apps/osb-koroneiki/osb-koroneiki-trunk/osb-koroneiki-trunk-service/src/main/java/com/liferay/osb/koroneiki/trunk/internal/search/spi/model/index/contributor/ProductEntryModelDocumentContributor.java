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

package com.liferay.osb.koroneiki.trunk.internal.search.spi.model.index.contributor;

import com.liferay.osb.koroneiki.root.model.ExternalLink;
import com.liferay.osb.koroneiki.root.service.ExternalLinkLocalService;
import com.liferay.osb.koroneiki.trunk.model.ProductEntry;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Amos Fong
 */
@Component(
	immediate = true,
	property = "indexer.class.name=com.liferay.osb.koroneiki.trunk.model.ProductEntry",
	service = ModelDocumentContributor.class
)
public class ProductEntryModelDocumentContributor
	implements ModelDocumentContributor<ProductEntry> {

	@Override
	public void contribute(Document document, ProductEntry productEntry) {
		try {
			_contribute(document, productEntry);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	private void _contribute(Document document, ProductEntry productEntry)
		throws PortalException {

		document.addKeyword(Field.COMPANY_ID, productEntry.getCompanyId());
		document.addDate(Field.CREATE_DATE, productEntry.getCreateDate());
		document.addDate(Field.MODIFIED_DATE, productEntry.getModifiedDate());
		document.addText(Field.NAME, productEntry.getName());

		document.addTextSortable(Field.NAME, productEntry.getName());

		_contributeExternalLinks(document, productEntry.getProductEntryId());
	}

	private void _contributeExternalLinks(
			Document document, long productEntryId)
		throws PortalException {

		Set<String> externalLinkDomains = new HashSet<>();
		Set<String> externalLinkEntityIds = new HashSet<>();
		Set<String> externalLinkEntityNames = new HashSet<>();

		List<ExternalLink> externalLinks =
			_externalLinkLocalService.getExternalLinks(
				ProductEntry.class.getName(), productEntryId, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		for (ExternalLink externalLink : externalLinks) {
			externalLinkDomains.add(externalLink.getDomain());
			externalLinkEntityIds.add(externalLink.getEntityId());
			externalLinkEntityNames.add(externalLink.getEntityName());
		}

		document.addKeyword(
			"externalLinkDomains",
			ArrayUtil.toStringArray(externalLinkDomains.toArray()));
		document.addKeyword(
			"externalLinkEntityIds",
			ArrayUtil.toStringArray(externalLinkEntityIds.toArray()));
		document.addKeyword(
			"externalLinkEntityNames",
			ArrayUtil.toStringArray(externalLinkEntityNames.toArray()));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ProductEntryModelDocumentContributor.class);

	@Reference
	private ExternalLinkLocalService _externalLinkLocalService;

}