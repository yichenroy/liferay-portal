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

package com.liferay.commerce.product.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CPDefinitionService}.
 *
 * @author Marco Leo
 * @see CPDefinitionService
 * @generated
 */
@ProviderType
public class CPDefinitionServiceWrapper implements CPDefinitionService,
	ServiceWrapper<CPDefinitionService> {
	public CPDefinitionServiceWrapper(CPDefinitionService cpDefinitionService) {
		_cpDefinitionService = cpDefinitionService;
	}

	@Override
	public com.liferay.commerce.product.model.CPDefinition addCPDefinition(
		java.lang.String baseSKU,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Map<java.util.Locale, java.lang.String> shortDescriptionMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.util.Map<java.util.Locale, java.lang.String> urlTitleMap,
		java.util.Map<java.util.Locale, java.lang.String> metaTitleMap,
		java.util.Map<java.util.Locale, java.lang.String> metaKeywordsMap,
		java.util.Map<java.util.Locale, java.lang.String> metaDescriptionMap,
		java.lang.String layoutUuid, java.lang.String productTypeName,
		java.lang.String gtin, java.lang.String manufacturerPartNumber,
		int minCartQuantity, int maxCartQuantity,
		java.lang.String allowedCartQuantities, int multipleCartQuantity,
		double width, double height, double depth, double weight,
		java.lang.String ddmStructureKey, int displayDateMonth,
		int displayDateDay, int displayDateYear, int displayDateHour,
		int displayDateMinute, int expirationDateMonth, int expirationDateDay,
		int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean neverExpire,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionService.addCPDefinition(baseSKU, titleMap,
			shortDescriptionMap, descriptionMap, urlTitleMap, metaTitleMap,
			metaKeywordsMap, metaDescriptionMap, layoutUuid, productTypeName,
			gtin, manufacturerPartNumber, minCartQuantity, maxCartQuantity,
			allowedCartQuantities, multipleCartQuantity, width, height, depth,
			weight, ddmStructureKey, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			serviceContext);
	}

	@Override
	public com.liferay.commerce.product.model.CPDefinition addCPDefinition(
		java.lang.String baseSKU,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Map<java.util.Locale, java.lang.String> shortDescriptionMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.util.Map<java.util.Locale, java.lang.String> urlTitleMap,
		java.util.Map<java.util.Locale, java.lang.String> metaTitleMap,
		java.util.Map<java.util.Locale, java.lang.String> metaKeywordsMap,
		java.util.Map<java.util.Locale, java.lang.String> metaDescriptionMap,
		java.lang.String layoutUuid, java.lang.String productTypeName,
		java.lang.String gtin, java.lang.String manufacturerPartNumber,
		int minCartQuantity, int maxCartQuantity,
		java.lang.String allowedCartQuantities, int multipleCartQuantity,
		java.lang.String ddmStructureKey, int displayDateMonth,
		int displayDateDay, int displayDateYear, int displayDateHour,
		int displayDateMinute, int expirationDateMonth, int expirationDateDay,
		int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean neverExpire,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionService.addCPDefinition(baseSKU, titleMap,
			shortDescriptionMap, descriptionMap, urlTitleMap, metaTitleMap,
			metaKeywordsMap, metaDescriptionMap, layoutUuid, productTypeName,
			gtin, manufacturerPartNumber, minCartQuantity, maxCartQuantity,
			allowedCartQuantities, multipleCartQuantity, ddmStructureKey,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, serviceContext);
	}

	@Override
	public void deleteAssetCategoryCPDefinition(long cpDefinitionId,
		long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_cpDefinitionService.deleteAssetCategoryCPDefinition(cpDefinitionId,
			categoryId);
	}

	@Override
	public com.liferay.commerce.product.model.CPDefinition deleteCPDefinition(
		long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionService.deleteCPDefinition(cpDefinitionId);
	}

	@Override
	public com.liferay.commerce.product.model.CPDefinition fetchCPDefinition(
		long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionService.fetchCPDefinition(cpDefinitionId);
	}

	@Override
	public com.liferay.commerce.product.model.CPDefinition getCPDefinition(
		long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionService.getCPDefinition(cpDefinitionId);
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CPDefinition> getCPDefinitions(
		long groupId, java.lang.String productTypeName,
		java.lang.String languageId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPDefinition> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionService.getCPDefinitions(groupId, productTypeName,
			languageId, status, start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CPDefinition> getCPDefinitionsByCategoryId(
		long categoryId, int start, int end) {
		return _cpDefinitionService.getCPDefinitionsByCategoryId(categoryId,
			start, end);
	}

	@Override
	public int getCPDefinitionsCount(long groupId,
		java.lang.String productTypeName, java.lang.String languageId,
		int status) {
		return _cpDefinitionService.getCPDefinitionsCount(groupId,
			productTypeName, languageId, status);
	}

	@Override
	public int getCPDefinitionsCountByCategoryId(long categoryId) {
		return _cpDefinitionService.getCPDefinitionsCountByCategoryId(categoryId);
	}

	@Override
	public com.liferay.commerce.product.model.CPAttachmentFileEntry getDefaultImage(
		long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionService.getDefaultImage(cpDefinitionId);
	}

	@Override
	public java.lang.String getLayoutUuid(long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionService.getLayoutUuid(cpDefinitionId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _cpDefinitionService.getOSGiServiceIdentifier();
	}

	@Override
	public java.lang.String getUrlTitleMapAsXML(long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionService.getUrlTitleMapAsXML(cpDefinitionId);
	}

	@Override
	public com.liferay.commerce.product.model.CPDefinition moveCPDefinitionToTrash(
		long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionService.moveCPDefinitionToTrash(cpDefinitionId);
	}

	@Override
	public void restoreCPDefinitionFromTrash(long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_cpDefinitionService.restoreCPDefinitionFromTrash(cpDefinitionId);
	}

	@Override
	public com.liferay.portal.kernel.search.Hits search(
		com.liferay.portal.kernel.search.SearchContext searchContext) {
		return _cpDefinitionService.search(searchContext);
	}

	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.product.model.CPDefinition> searchCPDefinitions(
		long companyId, long groupId, java.lang.String keywords, int status,
		int start, int end, com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionService.searchCPDefinitions(companyId, groupId,
			keywords, status, start, end, sort);
	}

	@Override
	public com.liferay.commerce.product.model.CPDefinition updateCPDefinition(
		long cpDefinitionId, java.lang.String baseSKU,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Map<java.util.Locale, java.lang.String> shortDescriptionMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.util.Map<java.util.Locale, java.lang.String> urlTitleMap,
		java.util.Map<java.util.Locale, java.lang.String> metaTitleMap,
		java.util.Map<java.util.Locale, java.lang.String> metaKeywordsMap,
		java.util.Map<java.util.Locale, java.lang.String> metaDescriptionMap,
		java.lang.String layoutUuid, java.lang.String gtin,
		java.lang.String manufacturerPartNumber, int minCartQuantity,
		int maxCartQuantity, java.lang.String allowedCartQuantities,
		int multipleCartQuantity, double width, double height, double depth,
		double weight, java.lang.String ddmStructureKey, int displayDateMonth,
		int displayDateDay, int displayDateYear, int displayDateHour,
		int displayDateMinute, int expirationDateMonth, int expirationDateDay,
		int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean neverExpire,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionService.updateCPDefinition(cpDefinitionId, baseSKU,
			titleMap, shortDescriptionMap, descriptionMap, urlTitleMap,
			metaTitleMap, metaKeywordsMap, metaDescriptionMap, layoutUuid,
			gtin, manufacturerPartNumber, minCartQuantity, maxCartQuantity,
			allowedCartQuantities, multipleCartQuantity, width, height, depth,
			weight, ddmStructureKey, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			serviceContext);
	}

	@Override
	public com.liferay.commerce.product.model.CPDefinition updateCPDefinition(
		long cpDefinitionId, java.lang.String baseSKU,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Map<java.util.Locale, java.lang.String> shortDescriptionMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.util.Map<java.util.Locale, java.lang.String> urlTitleMap,
		java.util.Map<java.util.Locale, java.lang.String> metaTitleMap,
		java.util.Map<java.util.Locale, java.lang.String> metaKeywordsMap,
		java.util.Map<java.util.Locale, java.lang.String> metaDescriptionMap,
		java.lang.String layoutUuid, java.lang.String gtin,
		java.lang.String manufacturerPartNumber, int minCartQuantity,
		int maxCartQuantity, java.lang.String allowedCartQuantities,
		int multipleCartQuantity, java.lang.String ddmStructureKey,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, int expirationDateMonth,
		int expirationDateDay, int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean neverExpire,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionService.updateCPDefinition(cpDefinitionId, baseSKU,
			titleMap, shortDescriptionMap, descriptionMap, urlTitleMap,
			metaTitleMap, metaKeywordsMap, metaDescriptionMap, layoutUuid,
			gtin, manufacturerPartNumber, minCartQuantity, maxCartQuantity,
			allowedCartQuantities, multipleCartQuantity, ddmStructureKey,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, serviceContext);
	}

	@Override
	public com.liferay.commerce.product.model.CPDefinition updateShippingInfo(
		long cpDefinitionId, double width, double height, double depth,
		double weight)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionService.updateShippingInfo(cpDefinitionId, width,
			height, depth, weight);
	}

	@Override
	public com.liferay.commerce.product.model.CPDefinition updateStatus(
		long userId, long cpDefinitionId, int status,
		com.liferay.portal.kernel.service.ServiceContext serviceContext,
		java.util.Map<java.lang.String, java.io.Serializable> workflowContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionService.updateStatus(userId, cpDefinitionId,
			status, serviceContext, workflowContext);
	}

	@Override
	public CPDefinitionService getWrappedService() {
		return _cpDefinitionService;
	}

	@Override
	public void setWrappedService(CPDefinitionService cpDefinitionService) {
		_cpDefinitionService = cpDefinitionService;
	}

	private CPDefinitionService _cpDefinitionService;
}