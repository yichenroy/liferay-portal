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

package com.liferay.commerce.data.integration.apio.internal.resource;

import com.liferay.apio.architect.functional.Try;
import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.NestedCollectionResource;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.commerce.data.integration.apio.identifiers.ProductOptionIdentifier;
import com.liferay.commerce.data.integration.apio.identifiers.ProductOptionValueIdentifier;
import com.liferay.commerce.data.integration.apio.internal.util.ProductIndexerHelper;
import com.liferay.commerce.data.integration.apio.internal.util.ProductOptionValueHelper;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.search.CPDefinitionOptionValueRelIndexer;
import com.liferay.commerce.product.service.CPDefinitionOptionValueRelService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.ServerErrorException;
import java.util.Collections;
import java.util.List;

/**
 * @author Zoltán Takács
 */
@Component(immediate = true)
public class ProductOptionValueNestedCollectionResource
	implements
		NestedCollectionResource<Document, Long,
			ProductOptionValueIdentifier, Long, ProductOptionIdentifier> {

	@Override
	public NestedCollectionRoutes<Document, Long, Long> collectionRoutes(
		NestedCollectionRoutes.Builder<Document, Long, Long> builder) {

		return builder.addGetter(
			this::_getPageItems
		).build();
	}

	@Override
	public String getName() {
		return "product-option-values";
	}

	@Override
	public ItemRoutes<Document, Long> itemRoutes(
		ItemRoutes.Builder<Document, Long> builder) {

		return builder.addGetter(
			this::_getCPDefinitionOptionValueRel
		).build();
	}

	@Override
	public Representor<Document> representor(
		Representor.Builder<Document, Long> builder) {

		return builder.types(
			"ProductOptionValue"
		).identifier(
			document -> GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK))
		).addBidirectionalModel(
			"productOption", "productOptionValues",
			ProductOptionIdentifier.class,
			document -> GetterUtil.getLong(
				document.get(
					CPDefinitionOptionValueRelIndexer.
						FIELD_CP_DEFINITION_OPTION_REL_ID))
		).addDate(
			"dateCreated",
			document -> Try.fromFallible(
				() -> document.getDate(Field.CREATE_DATE)
			).getUnchecked()
		).addDate(
			"dateModified",
			document -> Try.fromFallible(
				() -> document.getDate(Field.MODIFIED_DATE)
			).getUnchecked()
		).addString(
			"name", document -> document.get(Field.TITLE)
		).build();
	}

	private Document _getCPDefinitionOptionValueRel(
		Long cpDefinitionOptionValueRelId) {

		try {
			ServiceContext serviceContext =
				_productIndexerHelper.getServiceContext();

			SearchContext searchContext =
				ProductOptionValueHelper.buildSearchContext(
					String.valueOf(cpDefinitionOptionValueRelId), null,
					String.valueOf(cpDefinitionOptionValueRelId),
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null, serviceContext);

			Indexer<CPDefinitionOptionValueRel> indexer =
				_productIndexerHelper.getIndexer(
					CPDefinitionOptionValueRel.class);

			Hits hits = indexer.search(searchContext);

			if (hits.getLength() == 0) {
				throw new NotFoundException(
					"Unable to find product option value with ID " +
						cpDefinitionOptionValueRelId);
			}

			if (hits.getLength() > 1) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"More than one document found for product option " +
							"value with ID " + cpDefinitionOptionValueRelId);
				}

				CPDefinitionOptionValueRel cpDefinitionOptionValueRel =
					_cpDefinitionOptionValueRelService.
						getCPDefinitionOptionValueRel(
							cpDefinitionOptionValueRelId);

				return indexer.getDocument(cpDefinitionOptionValueRel);
			}

			List<Document> documents = hits.toList();

			return documents.get(0);
		}
		catch (PortalException pe) {
			throw new ServerErrorException(500, pe);
		}
	}

	private PageItems<Document> _getPageItems(
		Pagination pagination, Long cpDefinitionOptionRelId) {

		try {
			ServiceContext serviceContext =
				_productIndexerHelper.getServiceContext();

			SearchContext searchContext =
				ProductOptionValueHelper.buildSearchContext(
					null, String.valueOf(cpDefinitionOptionRelId), null,
					pagination.getStartPosition(), pagination.getEndPosition(),
					null, serviceContext);

			Indexer<CPDefinitionOptionValueRel> indexer =
				_productIndexerHelper.getIndexer(
					CPDefinitionOptionValueRel.class);

			Hits hits = indexer.search(searchContext);

			List<Document> documents = Collections.<Document>emptyList();

			if (hits.getLength() > 0) {
				documents = hits.toList();
			}

			return new PageItems<>(documents, hits.getLength());
		}
		catch (PortalException pe) {
			throw new ServerErrorException(500, pe);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ProductOptionValueNestedCollectionResource.class);

	@Reference
	private CPDefinitionOptionValueRelService
		_cpDefinitionOptionValueRelService;

	@Reference
	private ProductIndexerHelper _productIndexerHelper;

}