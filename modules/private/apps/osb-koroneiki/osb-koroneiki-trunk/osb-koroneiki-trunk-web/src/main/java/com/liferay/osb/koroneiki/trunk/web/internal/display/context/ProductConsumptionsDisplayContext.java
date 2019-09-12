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

package com.liferay.osb.koroneiki.trunk.web.internal.display.context;

import com.liferay.osb.koroneiki.trunk.model.ProductConsumption;
import com.liferay.osb.koroneiki.trunk.service.ProductConsumptionLocalServiceUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.SortFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Amos Fong
 */
public class ProductConsumptionsDisplayContext
	extends BaseSearchDisplayContext {

	public ProductConsumptionsDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse,
		HttpServletRequest httpServletRequest) {

		super(renderRequest, renderResponse, httpServletRequest);
	}

	@Override
	protected SearchContainer createSearchContainer() throws Exception {
		SearchContainer searchContainer = new SearchContainer(
			renderRequest, getPortletURL(), Collections.emptyList(),
			"no-product-consumption-were-found");

		Sort sort = SortFactoryUtil.getSort(
			ProductConsumption.class, Sort.STRING_TYPE, getOrderByCol(),
			getOrderByType());

		Hits hits = ProductConsumptionLocalServiceUtil.search(
			themeDisplay.getCompanyId(), getKeywords(),
			searchContainer.getStart(), searchContainer.getEnd(), sort);

		List<ProductConsumption> results = new ArrayList<>();

		for (Document document : hits.getDocs()) {
			long productConsumptionId = GetterUtil.getLong(
				document.get(Field.ENTRY_CLASS_PK));

			results.add(
				ProductConsumptionLocalServiceUtil.getProductConsumption(
					productConsumptionId));
		}

		searchContainer.setResults(results);
		searchContainer.setTotal(hits.getLength());

		return searchContainer;
	}

}