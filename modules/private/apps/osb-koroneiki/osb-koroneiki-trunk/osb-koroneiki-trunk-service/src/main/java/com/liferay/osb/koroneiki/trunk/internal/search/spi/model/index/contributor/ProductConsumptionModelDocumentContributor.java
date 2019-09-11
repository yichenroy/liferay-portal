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
import com.liferay.osb.koroneiki.trunk.model.ProductConsumption;
import com.liferay.osb.koroneiki.trunk.model.ProductEntry;
import com.liferay.osb.koroneiki.trunk.model.ProductField;
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
	property = "indexer.class.name=com.liferay.osb.koroneiki.trunk.model.ProductConsumption",
	service = ModelDocumentContributor.class
)
public class ProductConsumptionModelDocumentContributor
	implements ModelDocumentContributor<ProductConsumption> {

	@Override
	public void contribute(
		Document document, ProductConsumption productConsumption) {

		try {
			_contribute(document, productConsumption);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	private void _contribute(
			Document document, ProductConsumption productConsumption)
		throws PortalException {

		document.addKeyword(
			Field.COMPANY_ID, productConsumption.getCompanyId());
		document.addDate(Field.CREATE_DATE, productConsumption.getCreateDate());
		document.addDate(
			Field.MODIFIED_DATE, productConsumption.getModifiedDate());

		ProductEntry productEntry = productConsumption.getProductEntry();

		document.addText(Field.NAME, productEntry.getName());

		document.addKeyword("accountKey", productConsumption.getAccountKey());
		document.addKeyword(
			"productEntryKey", productEntry.getProductEntryKey());

		document.addTextSortable(Field.NAME, productEntry.getName());

		_contributeExternalLinks(
			document, productConsumption.getProductConsumptionId());
		_contributeProductFields(document, productConsumption);
	}

	private void _contributeExternalLinks(
			Document document, long productConsumptionId)
		throws PortalException {

		Set<String> externalLinkDomains = new HashSet<>();
		Set<String> externalLinkEntityIds = new HashSet<>();
		Set<String> externalLinkEntityNames = new HashSet<>();

		List<ExternalLink> externalLinks =
			_externalLinkLocalService.getExternalLinks(
				ProductConsumption.class.getName(), productConsumptionId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

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

	private void _contributeProductFields(
			Document document, ProductConsumption productConsumption)
		throws PortalException {

		List<ProductField> productFields =
			productConsumption.getProductFields();

		for (ProductField productField : productFields) {
			document.addKeyword(
				"property_" + productField.getName(), productField.getValue());
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ProductConsumptionModelDocumentContributor.class);

	@Reference
	private ExternalLinkLocalService _externalLinkLocalService;

}