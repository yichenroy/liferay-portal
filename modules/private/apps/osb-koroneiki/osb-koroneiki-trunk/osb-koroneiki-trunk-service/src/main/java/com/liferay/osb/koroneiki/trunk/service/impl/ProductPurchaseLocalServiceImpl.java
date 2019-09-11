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

package com.liferay.osb.koroneiki.trunk.service.impl;

import com.liferay.osb.koroneiki.root.service.ExternalLinkLocalService;
import com.liferay.osb.koroneiki.root.util.ModelKeyGenerator;
import com.liferay.osb.koroneiki.taproot.service.AccountLocalService;
import com.liferay.osb.koroneiki.trunk.exception.ProductPurchaseEndDateException;
import com.liferay.osb.koroneiki.trunk.exception.ProductPurchaseQuantityException;
import com.liferay.osb.koroneiki.trunk.model.ProductField;
import com.liferay.osb.koroneiki.trunk.model.ProductPurchase;
import com.liferay.osb.koroneiki.trunk.service.ProductFieldLocalService;
import com.liferay.osb.koroneiki.trunk.service.base.ProductPurchaseLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Kyle Bischof
 */
@Component(
	property = "model.class.name=com.liferay.osb.koroneiki.trunk.model.ProductPurchase",
	service = AopService.class
)
public class ProductPurchaseLocalServiceImpl
	extends ProductPurchaseLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	public ProductPurchase addProductPurchase(
			long userId, long accountId, long productEntryId, Date startDate,
			Date endDate, int quantity, List<ProductField> productFields)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		validate(accountId, productEntryId, startDate, endDate, quantity);

		long productPurchaseId = counterLocalService.increment();

		ProductPurchase productPurchase = productPurchasePersistence.create(
			productPurchaseId);

		productPurchase.setCompanyId(user.getCompanyId());
		productPurchase.setUserId(userId);
		productPurchase.setProductPurchaseKey(
			ModelKeyGenerator.generate(productPurchaseId));
		productPurchase.setAccountId(accountId);
		productPurchase.setProductEntryId(productEntryId);
		productPurchase.setStartDate(startDate);
		productPurchase.setEndDate(endDate);
		productPurchase.setQuantity(quantity);

		productPurchasePersistence.update(productPurchase);

		// Resources

		resourceLocalService.addResources(
			productPurchase.getCompanyId(), 0, userId,
			ProductPurchase.class.getName(),
			productPurchase.getProductPurchaseId(), false, false, false);

		// Product fields

		for (ProductField productField : productFields) {
			_productFieldLocalService.addProductField(
				userId, ProductPurchase.class.getName(), productPurchaseId,
				productField.getName(), productField.getValue());
		}

		_accountLocalService.reindex(accountId);

		return productPurchase;
	}

	@Override
	public ProductPurchase deleteProductPurchase(long productPurchaseId)
		throws PortalException {

		ProductPurchase productPurchase =
			productPurchaseLocalService.getProductPurchase(productPurchaseId);

		// External links

		long classNameId = classNameLocalService.getClassNameId(
			ProductPurchase.class);

		_externalLinkLocalService.deleteExternalLinks(
			classNameId, productPurchaseId);

		// Product fields

		productFieldPersistence.removeByC_C(classNameId, productPurchaseId);

		// Resources

		resourceLocalService.deleteResource(
			productPurchase.getCompanyId(), ProductPurchase.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			productPurchase.getProductPurchaseId());

		_accountLocalService.reindex(productPurchase.getAccountId());

		return productPurchasePersistence.remove(productPurchaseId);
	}

	public List<ProductPurchase> getAccountProductPurchases(
		long accountId, int start, int end) {

		return productPurchasePersistence.findByAccountId(
			accountId, start, end);
	}

	public int getAccountProductPurchasesCount(long accountId) {
		return productPurchasePersistence.countByAccountId(accountId);
	}

	public ProductPurchase getProductPurchase(String productPurchaseKey)
		throws PortalException {

		return productPurchasePersistence.findByProductPurchaseKey(
			productPurchaseKey);
	}

	@Indexable(type = IndexableType.REINDEX)
	public ProductPurchase reindex(long productPurchaseId)
		throws PortalException {

		return productPurchasePersistence.findByPrimaryKey(productPurchaseId);
	}

	public Hits search(
			long companyId, String keywords, int start, int end, Sort sort)
		throws PortalException {

		try {
			Indexer<ProductPurchase> indexer =
				IndexerRegistryUtil.nullSafeGetIndexer(ProductPurchase.class);

			SearchContext searchContext = new SearchContext();

			searchContext.setAndSearch(false);

			Map<String, Serializable> attributes = new HashMap<>();

			attributes.put("accountKey", keywords);
			attributes.put("externalLinkEntityIds", keywords);
			attributes.put("name", keywords);
			attributes.put("productEntryKey", keywords);

			searchContext.setAttributes(attributes);

			searchContext.setCompanyId(companyId);
			searchContext.setEnd(end);

			if (sort != null) {
				searchContext.setSorts(sort);
			}

			searchContext.setStart(start);

			QueryConfig queryConfig = searchContext.getQueryConfig();

			queryConfig.setHighlightEnabled(false);
			queryConfig.setScoreEnabled(false);

			return indexer.search(searchContext);
		}
		catch (Exception e) {
			throw new PortalException(e);
		}
	}

	@Indexable(type = IndexableType.REINDEX)
	public ProductPurchase updateProductPurchase(
			long userId, long productPurchaseId, Date startDate, Date endDate,
			int quantity, List<ProductField> productFields)
		throws PortalException {

		validate(startDate, endDate, quantity);

		ProductPurchase productPurchase =
			productPurchasePersistence.findByPrimaryKey(productPurchaseId);

		productPurchase.setStartDate(startDate);
		productPurchase.setEndDate(endDate);
		productPurchase.setQuantity(quantity);

		productPurchasePersistence.update(productPurchase);

		// Product fields

		long classNameId = classNameLocalService.getClassNameId(
			ProductPurchase.class);

		Map<String, ProductField> productFieldsMap = getProductFieldsMap(
			productPurchaseId);

		for (ProductField productField : productFields) {
			ProductField curProductField = productFieldsMap.remove(
				productField.getName());

			if (curProductField == null) {
				_productFieldLocalService.addProductField(
					userId, classNameId, productPurchaseId,
					productField.getName(), productField.getValue());
			}
			else {
				_productFieldLocalService.updateProductField(
					curProductField.getProductFieldId(),
					productField.getValue());
			}
		}

		for (ProductField productField : productFieldsMap.values()) {
			_productFieldLocalService.deleteProductField(
				productField.getProductFieldId());
		}

		return productPurchase;
	}

	protected Map<String, ProductField> getProductFieldsMap(
		long productPurchaseId) {

		Map<String, ProductField> productFieldsMap = new HashMap<>();

		long classNameId = classNameLocalService.getClassNameId(
			ProductPurchase.class);

		List<ProductField> productFields =
			_productFieldLocalService.getProductFields(
				classNameId, productPurchaseId);

		for (ProductField productField : productFields) {
			productFieldsMap.put(productField.getName(), productField);
		}

		return productFieldsMap;
	}

	protected void validate(Date startDate, Date endDate, int quantity)
		throws PortalException {

		if ((startDate != null) && (endDate != null) &&
			startDate.after(endDate)) {

			throw new ProductPurchaseEndDateException();
		}

		if (quantity <= 0) {
			throw new ProductPurchaseQuantityException();
		}
	}

	protected void validate(
			long accountId, long productEntryId, Date startDate, Date endDate,
			int quantity)
		throws PortalException {

		_accountLocalService.getAccount(accountId);

		productEntryPersistence.findByPrimaryKey(productEntryId);

		validate(startDate, endDate, quantity);
	}

	@Reference
	private AccountLocalService _accountLocalService;

	@Reference
	private ExternalLinkLocalService _externalLinkLocalService;

	@Reference
	private ProductFieldLocalService _productFieldLocalService;

}