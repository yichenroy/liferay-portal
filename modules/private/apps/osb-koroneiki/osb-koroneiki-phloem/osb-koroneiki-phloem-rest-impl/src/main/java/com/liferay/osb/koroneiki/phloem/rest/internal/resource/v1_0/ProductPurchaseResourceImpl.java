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

package com.liferay.osb.koroneiki.phloem.rest.internal.resource.v1_0;

import com.liferay.osb.koroneiki.phloem.rest.dto.v1_0.ProductPurchase;
import com.liferay.osb.koroneiki.phloem.rest.dto.v1_0.util.ProductPurchaseUtil;
import com.liferay.osb.koroneiki.phloem.rest.internal.odata.entity.v1_0.ProductPurchaseEntityModel;
import com.liferay.osb.koroneiki.phloem.rest.resource.v1_0.ProductPurchaseResource;
import com.liferay.osb.koroneiki.trunk.model.ProductField;
import com.liferay.osb.koroneiki.trunk.service.ProductFieldLocalService;
import com.liferay.osb.koroneiki.trunk.service.ProductPurchaseLocalService;
import com.liferay.osb.koroneiki.trunk.service.ProductPurchaseService;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MultivaluedMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Amos Fong
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/product-purchase.properties",
	scope = ServiceScope.PROTOTYPE, service = ProductPurchaseResource.class
)
public class ProductPurchaseResourceImpl
	extends BaseProductPurchaseResourceImpl implements EntityModelResource {

	@Override
	public void deleteProductPurchase(String productPurchaseKey)
		throws Exception {

		_productPurchaseService.deleteProductPurchase(productPurchaseKey);
	}

	@Override
	public Page<ProductPurchase> getAccountAccountKeyProductPurchasesPage(
			String accountKey, Pagination pagination)
		throws Exception {

		return Page.of(
			transform(
				_productPurchaseService.getAccountProductPurchases(
					accountKey, pagination.getStartPosition(),
					pagination.getEndPosition()),
				ProductPurchaseUtil::toProductPurchase),
			pagination,
			_productPurchaseService.getAccountProductPurchasesCount(
				accountKey));
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap) {
		long classNameId = _classNameLocalService.getClassNameId(
			com.liferay.osb.koroneiki.trunk.model.ProductPurchase.class);

		return new ProductPurchaseEntityModel(
			_productFieldLocalService.getProductFieldNames(classNameId));
	}

	@Override
	public ProductPurchase getProductPurchase(String productPurchaseKey)
		throws Exception {

		return ProductPurchaseUtil.toProductPurchase(
			_productPurchaseService.getProductPurchase(productPurchaseKey));
	}

	@Override
	public Page<ProductPurchase> getProductPurchasesPage(
			String search, Filter filter, Pagination pagination, Sort[] sorts)
		throws Exception {

		return SearchUtil.search(
			booleanQuery -> {
			},
			filter, com.liferay.osb.koroneiki.trunk.model.ProductPurchase.class,
			search, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> searchContext.setCompanyId(
				contextCompany.getCompanyId()),
			document -> ProductPurchaseUtil.toProductPurchase(
				_productPurchaseLocalService.getProductPurchase(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)))),
			sorts);
	}

	@Override
	public ProductPurchase postAccountAccountKeyProductPurchase(
			String accountKey, ProductPurchase productPurchase)
		throws Exception {

		Date startDate = productPurchase.getStartDate();
		Date endDate = productPurchase.getEndDate();

		if ((productPurchase.getPerpetual() != null) &&
			productPurchase.getPerpetual()) {

			startDate = null;
			endDate = null;
		}

		int quantity = GetterUtil.getInteger(productPurchase.getQuantity(), 1);

		List<ProductField> productFields = getProductFields(
			productPurchase.getProperties(), null);

		return ProductPurchaseUtil.toProductPurchase(
			_productPurchaseService.addProductPurchase(
				accountKey, productPurchase.getProductKey(), startDate, endDate,
				quantity, productFields));
	}

	@Override
	public ProductPurchase putProductPurchase(
			String productPurchaseKey, ProductPurchase productPurchase)
		throws Exception {

		com.liferay.osb.koroneiki.trunk.model.ProductPurchase
			curProductPurchase =
				_productPurchaseLocalService.getProductPurchase(
					productPurchaseKey);

		Date startDate = productPurchase.getStartDate();

		if (startDate == null) {
			startDate = curProductPurchase.getStartDate();
		}

		Date endDate = productPurchase.getEndDate();

		if (endDate == null) {
			endDate = curProductPurchase.getEndDate();
		}

		if ((productPurchase.getPerpetual() != null) &&
			productPurchase.getPerpetual()) {

			startDate = null;
			endDate = null;
		}

		int quantity = GetterUtil.getInteger(
			productPurchase.getQuantity(), curProductPurchase.getQuantity());

		List<ProductField> productFields = getProductFields(
			productPurchase.getProperties(),
			curProductPurchase.getProductFields());

		return ProductPurchaseUtil.toProductPurchase(
			_productPurchaseService.updateProductPurchase(
				curProductPurchase.getProductPurchaseId(), startDate, endDate,
				quantity, productFields));
	}

	protected List<ProductField> getProductFields(
		Map<String, String> properties,
		List<ProductField> defaultProductFields) {

		if (properties == null) {
			return defaultProductFields;
		}

		List<ProductField> productFields = new ArrayList<>();

		for (Map.Entry<String, String> entry : properties.entrySet()) {
			ProductField productField =
				_productFieldLocalService.createProductField(0);

			productField.setName(entry.getKey());
			productField.setValue(entry.getValue());

			productFields.add(productField);
		}

		return productFields;
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private ProductFieldLocalService _productFieldLocalService;

	@Reference
	private ProductPurchaseLocalService _productPurchaseLocalService;

	@Reference
	private ProductPurchaseService _productPurchaseService;

}