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

import com.liferay.osb.koroneiki.trunk.model.ProductPurchase;
import com.liferay.osb.koroneiki.trunk.service.ProductPurchaseLocalService;
import com.liferay.portal.search.batch.BatchIndexingActionable;
import com.liferay.portal.search.batch.DynamicQueryBatchIndexingActionableFactory;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.index.contributor.helper.ModelIndexerWriterDocumentHelper;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Amos Fong
 */
@Component(
	immediate = true,
	property = "indexer.class.name=com.liferay.osb.koroneiki.trunk.model.ProductPurchase",
	service = ModelIndexerWriterContributor.class
)
public class ProductPurchaseModelIndexerWriterContributor
	implements ModelIndexerWriterContributor<ProductPurchase> {

	@Override
	public void customize(
		BatchIndexingActionable batchIndexingActionable,
		ModelIndexerWriterDocumentHelper modelIndexerWriterDocumentHelper) {

		batchIndexingActionable.setPerformActionMethod(
			(ProductPurchase productPurchase) ->
				batchIndexingActionable.addDocuments(
					modelIndexerWriterDocumentHelper.getDocument(
						productPurchase)));
	}

	@Override
	public BatchIndexingActionable getBatchIndexingActionable() {
		return _dynamicQueryBatchIndexingActionableFactory.
			getBatchIndexingActionable(
				_productPurchaseLocalService.
					getIndexableActionableDynamicQuery());
	}

	@Override
	public long getCompanyId(ProductPurchase productPurchase) {
		return productPurchase.getCompanyId();
	}

	@Reference
	private DynamicQueryBatchIndexingActionableFactory
		_dynamicQueryBatchIndexingActionableFactory;

	@Reference
	private ProductPurchaseLocalService _productPurchaseLocalService;

}