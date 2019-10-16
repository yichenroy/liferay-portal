/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.commerce.product.options.web.internal.portlet.action;

import com.liferay.commerce.product.constants.CPWebKeys;
import com.liferay.commerce.product.model.CPOption;
import com.liferay.commerce.product.model.CPOptionValue;
import com.liferay.commerce.product.service.CPOptionService;
import com.liferay.commerce.product.service.CPOptionValueService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.RenderRequest;
import javax.portlet.ResourceRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(service = ActionHelper.class)
public class ActionHelper {

	public CPOption getCPOption(RenderRequest renderRequest)
		throws PortalException {

		CPOption cpOption = (CPOption)renderRequest.getAttribute(
			CPWebKeys.COMMERCE_PRODUCT_OPTION);

		if (cpOption != null) {
			return cpOption;
		}

		long cpOptionId = ParamUtil.getLong(renderRequest, "cpOptionId");

		if (cpOptionId <= 0) {
			CPOptionValue cpOptionValue = getCPOptionValue(renderRequest);

			if (cpOptionValue != null) {
				cpOptionId = cpOptionValue.getCPOptionId();
			}
		}

		if (cpOptionId > 0) {
			cpOption = _cpOptionService.fetchCPOption(cpOptionId);
		}

		if (cpOption != null) {
			renderRequest.setAttribute(
				CPWebKeys.COMMERCE_PRODUCT_OPTION, cpOption);
		}

		return cpOption;
	}

	public List<CPOption> getCPOptions(ResourceRequest resourceRequest)
		throws Exception {

		List<CPOption> cpOptions = new ArrayList<>();

		long[] cpOptionsIds = ParamUtil.getLongValues(
			resourceRequest, "rowIds");

		for (long cpOptionsId : cpOptionsIds) {
			CPOption cpOption = _cpOptionService.getCPOption(cpOptionsId);

			cpOptions.add(cpOption);
		}

		return cpOptions;
	}

	public CPOptionValue getCPOptionValue(RenderRequest renderRequest)
		throws PortalException {

		CPOptionValue cpOptionValue = (CPOptionValue)renderRequest.getAttribute(
			CPWebKeys.COMMERCE_PRODUCT_OPTION_VALUE);

		if (cpOptionValue != null) {
			return cpOptionValue;
		}

		long cpOptionValueId = ParamUtil.getLong(
			renderRequest, "cpOptionValueId");

		if (cpOptionValueId > 0) {
			cpOptionValue = _cpOptionValueService.fetchCPOptionValue(
				cpOptionValueId);
		}

		if (cpOptionValue != null) {
			renderRequest.setAttribute(
				CPWebKeys.COMMERCE_PRODUCT_OPTION_VALUE, cpOptionValue);
		}

		return cpOptionValue;
	}

	public List<CPOptionValue> getCPOptionValues(
			ResourceRequest resourceRequest)
		throws Exception {

		List<CPOptionValue> cpOptionValues = new ArrayList<>();

		long[] cpOptionValuesIds = ParamUtil.getLongValues(
			resourceRequest, "rowIds");

		for (long cpOptionValuesId : cpOptionValuesIds) {
			CPOptionValue cpOptionValue =
				_cpOptionValueService.getCPOptionValue(cpOptionValuesId);

			cpOptionValues.add(cpOptionValue);
		}

		return cpOptionValues;
	}

	@Reference
	private CPOptionService _cpOptionService;

	@Reference
	private CPOptionValueService _cpOptionValueService;

}