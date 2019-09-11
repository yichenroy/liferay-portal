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

import com.liferay.osb.koroneiki.phloem.rest.dto.v1_0.ProductConsumption;
import com.liferay.osb.koroneiki.phloem.rest.dto.v1_0.util.ProductConsumptionUtil;
import com.liferay.osb.koroneiki.phloem.rest.internal.odata.entity.v1_0.ProductConsumptionEntityModel;
import com.liferay.osb.koroneiki.phloem.rest.resource.v1_0.ProductConsumptionResource;
import com.liferay.osb.koroneiki.trunk.model.ProductField;
import com.liferay.osb.koroneiki.trunk.service.ProductConsumptionLocalService;
import com.liferay.osb.koroneiki.trunk.service.ProductConsumptionService;
import com.liferay.osb.koroneiki.trunk.service.ProductFieldLocalService;
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
	properties = "OSGI-INF/liferay/rest/v1_0/product-consumption.properties",
	scope = ServiceScope.PROTOTYPE, service = ProductConsumptionResource.class
)
public class ProductConsumptionResourceImpl
	extends BaseProductConsumptionResourceImpl implements EntityModelResource {

	@Override
	public void deleteProductConsumption(String productConsumptionKey)
		throws Exception {

		_productConsumptionService.deleteProductConsumption(
			productConsumptionKey);
	}

	@Override
	public Page<ProductConsumption> getAccountAccountKeyProductConsumptionsPage(
			String accountKey, Pagination pagination)
		throws Exception {

		return Page.of(
			transform(
				_productConsumptionService.getAccountProductConsumptions(
					accountKey, pagination.getStartPosition(),
					pagination.getEndPosition()),
				ProductConsumptionUtil::toProductConsumption),
			pagination,
			_productConsumptionService.getAccountProductConsumptionsCount(
				accountKey));
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap) {
		long classNameId = _classNameLocalService.getClassNameId(
			com.liferay.osb.koroneiki.trunk.model.ProductConsumption.class);

		return new ProductConsumptionEntityModel(
			_productFieldLocalService.getProductFieldNames(classNameId));
	}

	@Override
	public ProductConsumption getProductConsumption(
			String productConsumptionKey)
		throws Exception {

		return ProductConsumptionUtil.toProductConsumption(
			_productConsumptionService.getProductConsumption(
				productConsumptionKey));
	}

	@Override
	public Page<ProductConsumption> getProductConsumptionsPage(
			String search, Filter filter, Pagination pagination, Sort[] sorts)
		throws Exception {

		return SearchUtil.search(
			booleanQuery -> {
			},
			filter,
			com.liferay.osb.koroneiki.trunk.model.ProductConsumption.class,
			search, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> searchContext.setCompanyId(
				contextCompany.getCompanyId()),
			document -> ProductConsumptionUtil.toProductConsumption(
				_productConsumptionLocalService.getProductConsumption(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)))),
			sorts);
	}

	@Override
	public ProductConsumption postAccountAccountKeyProductConsumption(
			String accountKey, ProductConsumption productConsumption)
		throws Exception {

		List<ProductField> productFields = getProductFields(
			productConsumption.getProperties());

		return ProductConsumptionUtil.toProductConsumption(
			_productConsumptionService.addProductConsumption(
				accountKey, productConsumption.getProductKey(), productFields));
	}

	protected List<ProductField> getProductFields(
		Map<String, String> properties) {

		List<ProductField> productFields = new ArrayList<>();

		if (properties == null) {
			return productFields;
		}

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
	private ProductConsumptionLocalService _productConsumptionLocalService;

	@Reference
	private ProductConsumptionService _productConsumptionService;

	@Reference
	private ProductFieldLocalService _productFieldLocalService;

}