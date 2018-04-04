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

package com.liferay.commerce.internal.model.listener;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.service.CPDefinitionAvailabilityRangeLocalService;
import com.liferay.commerce.service.CPDefinitionInventoryLocalService;
import com.liferay.commerce.service.CommerceTaxCategoryRelLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 * @author Andrea Di Giorgi
 * @author Marco Leo
 */
@Component(immediate = true, service = ModelListener.class)
public class CPDefinitionModelListener extends BaseModelListener<CPDefinition> {

	@Override
	public void onBeforeRemove(CPDefinition cpDefinition) {
		long cpDefinitionId = cpDefinition.getCPDefinitionId();

		try {
			_commerceTaxCategoryRelLocalService.deleteCommerceTaxCategoryRel(
				cpDefinition.getModelClassName(), cpDefinitionId);
			_cpDefinitionAvailabilityRangeLocalService.
				deleteCPDefinitionAvailabilityRangesByCPDefinitionId(
					cpDefinitionId);
			_cpDefinitionInventoryLocalService.
				deleteCPDefinitionInventoryByCPDefinitionId(cpDefinitionId);
		}
		catch (PortalException pe) {
			_log.error(pe, pe);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CPDefinitionModelListener.class);

	@Reference
	private CommerceTaxCategoryRelLocalService
		_commerceTaxCategoryRelLocalService;

	@Reference
	private CPDefinitionAvailabilityRangeLocalService
		_cpDefinitionAvailabilityRangeLocalService;

	@Reference
	private CPDefinitionInventoryLocalService
		_cpDefinitionInventoryLocalService;

}
