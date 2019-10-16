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

package com.liferay.commerce.product.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.service.CPDefinitionServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;

import java.rmi.RemoteException;

import java.util.Locale;
import java.util.Map;

/**
 * Provides the SOAP utility for the
 * {@link CPDefinitionServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.commerce.product.model.CPDefinitionSoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.commerce.product.model.CPDefinition}, that is translated to a
 * {@link com.liferay.commerce.product.model.CPDefinitionSoap}. Methods that SOAP cannot
 * safely wire are skipped.
 * </p>
 *
 * <p>
 * The benefits of using the SOAP utility is that it is cross platform
 * compatible. SOAP allows different languages like Java, .NET, C++, PHP, and
 * even Perl, to call the generated services. One drawback of SOAP is that it is
 * slow because it needs to serialize all calls into a text format (XML).
 * </p>
 *
 * <p>
 * You can see a list of services at http://localhost:8080/api/axis. Set the
 * property <b>axis.servlet.hosts.allowed</b> in portal.properties to configure
 * security.
 * </p>
 *
 * <p>
 * The SOAP utility is only generated for remote services.
 * </p>
 *
 * @author Marco Leo
 * @see CPDefinitionServiceHttp
 * @see com.liferay.commerce.product.model.CPDefinitionSoap
 * @see CPDefinitionServiceUtil
 * @generated
 */
@ProviderType
public class CPDefinitionServiceSoap {
	public static com.liferay.commerce.product.model.CPDefinitionSoap addCPDefinition(
		java.lang.String[] titleMapLanguageIds,
		java.lang.String[] titleMapValues,
		java.lang.String[] shortDescriptionMapLanguageIds,
		java.lang.String[] shortDescriptionMapValues,
		java.lang.String[] descriptionMapLanguageIds,
		java.lang.String[] descriptionMapValues,
		java.lang.String[] metaTitleMapLanguageIds,
		java.lang.String[] metaTitleMapValues,
		java.lang.String[] metaKeywordsMapLanguageIds,
		java.lang.String[] metaKeywordsMapValues,
		java.lang.String[] metaDescriptionMapLanguageIds,
		java.lang.String[] metaDescriptionMapValues,
		java.lang.String layoutUuid, java.lang.String productTypeName,
		boolean ignoreSKUCombinations, double width, double height,
		double depth, double weight, java.lang.String ddmStructureKey,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, int expirationDateMonth,
		int expirationDateDay, int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean neverExpire,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(titleMapLanguageIds,
					titleMapValues);
			Map<Locale, String> shortDescriptionMap = LocalizationUtil.getLocalizationMap(shortDescriptionMapLanguageIds,
					shortDescriptionMapValues);
			Map<Locale, String> descriptionMap = LocalizationUtil.getLocalizationMap(descriptionMapLanguageIds,
					descriptionMapValues);
			Map<Locale, String> metaTitleMap = LocalizationUtil.getLocalizationMap(metaTitleMapLanguageIds,
					metaTitleMapValues);
			Map<Locale, String> metaKeywordsMap = LocalizationUtil.getLocalizationMap(metaKeywordsMapLanguageIds,
					metaKeywordsMapValues);
			Map<Locale, String> metaDescriptionMap = LocalizationUtil.getLocalizationMap(metaDescriptionMapLanguageIds,
					metaDescriptionMapValues);

			com.liferay.commerce.product.model.CPDefinition returnValue = CPDefinitionServiceUtil.addCPDefinition(titleMap,
					shortDescriptionMap, descriptionMap, metaTitleMap,
					metaKeywordsMap, metaDescriptionMap, layoutUuid,
					productTypeName, ignoreSKUCombinations, width, height,
					depth, weight, ddmStructureKey, displayDateMonth,
					displayDateDay, displayDateYear, displayDateHour,
					displayDateMinute, expirationDateMonth, expirationDateDay,
					expirationDateYear, expirationDateHour,
					expirationDateMinute, neverExpire, serviceContext);

			return com.liferay.commerce.product.model.CPDefinitionSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPDefinitionSoap addCPDefinition(
		java.lang.String[] titleMapLanguageIds,
		java.lang.String[] titleMapValues,
		java.lang.String[] shortDescriptionMapLanguageIds,
		java.lang.String[] shortDescriptionMapValues,
		java.lang.String[] descriptionMapLanguageIds,
		java.lang.String[] descriptionMapValues, java.lang.String layoutUuid,
		java.lang.String productTypeName, boolean ignoreSKUCombinations,
		java.lang.String ddmStructureKey, int displayDateMonth,
		int displayDateDay, int displayDateYear, int displayDateHour,
		int displayDateMinute, int expirationDateMonth, int expirationDateDay,
		int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean neverExpire,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(titleMapLanguageIds,
					titleMapValues);
			Map<Locale, String> shortDescriptionMap = LocalizationUtil.getLocalizationMap(shortDescriptionMapLanguageIds,
					shortDescriptionMapValues);
			Map<Locale, String> descriptionMap = LocalizationUtil.getLocalizationMap(descriptionMapLanguageIds,
					descriptionMapValues);

			com.liferay.commerce.product.model.CPDefinition returnValue = CPDefinitionServiceUtil.addCPDefinition(titleMap,
					shortDescriptionMap, descriptionMap, layoutUuid,
					productTypeName, ignoreSKUCombinations, ddmStructureKey,
					displayDateMonth, displayDateDay, displayDateYear,
					displayDateHour, displayDateMinute, expirationDateMonth,
					expirationDateDay, expirationDateYear, expirationDateHour,
					expirationDateMinute, neverExpire, serviceContext);

			return com.liferay.commerce.product.model.CPDefinitionSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteAssetCategoryCPDefinition(long cpDefinitionId,
		long categoryId) throws RemoteException {
		try {
			CPDefinitionServiceUtil.deleteAssetCategoryCPDefinition(cpDefinitionId,
				categoryId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPDefinitionSoap deleteCPDefinition(
		long cpDefinitionId) throws RemoteException {
		try {
			com.liferay.commerce.product.model.CPDefinition returnValue = CPDefinitionServiceUtil.deleteCPDefinition(cpDefinitionId);

			return com.liferay.commerce.product.model.CPDefinitionSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPDefinitionSoap fetchCPDefinition(
		long cpDefinitionId) throws RemoteException {
		try {
			com.liferay.commerce.product.model.CPDefinition returnValue = CPDefinitionServiceUtil.fetchCPDefinition(cpDefinitionId);

			return com.liferay.commerce.product.model.CPDefinitionSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPDefinitionSoap getCPDefinition(
		long cpDefinitionId) throws RemoteException {
		try {
			com.liferay.commerce.product.model.CPDefinition returnValue = CPDefinitionServiceUtil.getCPDefinition(cpDefinitionId);

			return com.liferay.commerce.product.model.CPDefinitionSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPDefinitionSoap[] getCPDefinitions(
		long groupId, java.lang.String productTypeName,
		java.lang.String languageId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPDefinition> orderByComparator)
		throws RemoteException {
		try {
			java.util.List<com.liferay.commerce.product.model.CPDefinition> returnValue =
				CPDefinitionServiceUtil.getCPDefinitions(groupId,
					productTypeName, languageId, status, start, end,
					orderByComparator);

			return com.liferay.commerce.product.model.CPDefinitionSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPDefinitionSoap[] getCPDefinitionsByCategoryId(
		long categoryId, int start, int end) throws RemoteException {
		try {
			java.util.List<com.liferay.commerce.product.model.CPDefinition> returnValue =
				CPDefinitionServiceUtil.getCPDefinitionsByCategoryId(categoryId,
					start, end);

			return com.liferay.commerce.product.model.CPDefinitionSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getCPDefinitionsCount(long groupId,
		java.lang.String productTypeName, java.lang.String languageId,
		int status) throws RemoteException {
		try {
			int returnValue = CPDefinitionServiceUtil.getCPDefinitionsCount(groupId,
					productTypeName, languageId, status);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getCPDefinitionsCountByCategoryId(long categoryId)
		throws RemoteException {
		try {
			int returnValue = CPDefinitionServiceUtil.getCPDefinitionsCountByCategoryId(categoryId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPAttachmentFileEntrySoap getDefaultImage(
		long cpDefinitionId) throws RemoteException {
		try {
			com.liferay.commerce.product.model.CPAttachmentFileEntry returnValue =
				CPDefinitionServiceUtil.getDefaultImage(cpDefinitionId);

			return com.liferay.commerce.product.model.CPAttachmentFileEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static java.lang.String getLayoutUuid(long cpDefinitionId)
		throws RemoteException {
		try {
			java.lang.String returnValue = CPDefinitionServiceUtil.getLayoutUuid(cpDefinitionId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static java.lang.String getUrlTitleMapAsXML(long cpDefinitionId)
		throws RemoteException {
		try {
			java.lang.String returnValue = CPDefinitionServiceUtil.getUrlTitleMapAsXML(cpDefinitionId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPDefinitionSoap moveCPDefinitionToTrash(
		long cpDefinitionId) throws RemoteException {
		try {
			com.liferay.commerce.product.model.CPDefinition returnValue = CPDefinitionServiceUtil.moveCPDefinitionToTrash(cpDefinitionId);

			return com.liferay.commerce.product.model.CPDefinitionSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void restoreCPDefinitionFromTrash(long cpDefinitionId)
		throws RemoteException {
		try {
			CPDefinitionServiceUtil.restoreCPDefinitionFromTrash(cpDefinitionId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPDefinitionSoap updateCPDefinition(
		long cpDefinitionId, java.lang.String[] titleMapLanguageIds,
		java.lang.String[] titleMapValues,
		java.lang.String[] shortDescriptionMapLanguageIds,
		java.lang.String[] shortDescriptionMapValues,
		java.lang.String[] descriptionMapLanguageIds,
		java.lang.String[] descriptionMapValues,
		java.lang.String[] urlTitleMapLanguageIds,
		java.lang.String[] urlTitleMapValues,
		java.lang.String[] metaTitleMapLanguageIds,
		java.lang.String[] metaTitleMapValues,
		java.lang.String[] metaKeywordsMapLanguageIds,
		java.lang.String[] metaKeywordsMapValues,
		java.lang.String[] metaDescriptionMapLanguageIds,
		java.lang.String[] metaDescriptionMapValues,
		java.lang.String layoutUuid, boolean ignoreSKUCombinations,
		double width, double height, double depth, double weight,
		java.lang.String ddmStructureKey, int displayDateMonth,
		int displayDateDay, int displayDateYear, int displayDateHour,
		int displayDateMinute, int expirationDateMonth, int expirationDateDay,
		int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean neverExpire,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(titleMapLanguageIds,
					titleMapValues);
			Map<Locale, String> shortDescriptionMap = LocalizationUtil.getLocalizationMap(shortDescriptionMapLanguageIds,
					shortDescriptionMapValues);
			Map<Locale, String> descriptionMap = LocalizationUtil.getLocalizationMap(descriptionMapLanguageIds,
					descriptionMapValues);
			Map<Locale, String> urlTitleMap = LocalizationUtil.getLocalizationMap(urlTitleMapLanguageIds,
					urlTitleMapValues);
			Map<Locale, String> metaTitleMap = LocalizationUtil.getLocalizationMap(metaTitleMapLanguageIds,
					metaTitleMapValues);
			Map<Locale, String> metaKeywordsMap = LocalizationUtil.getLocalizationMap(metaKeywordsMapLanguageIds,
					metaKeywordsMapValues);
			Map<Locale, String> metaDescriptionMap = LocalizationUtil.getLocalizationMap(metaDescriptionMapLanguageIds,
					metaDescriptionMapValues);

			com.liferay.commerce.product.model.CPDefinition returnValue = CPDefinitionServiceUtil.updateCPDefinition(cpDefinitionId,
					titleMap, shortDescriptionMap, descriptionMap, urlTitleMap,
					metaTitleMap, metaKeywordsMap, metaDescriptionMap,
					layoutUuid, ignoreSKUCombinations, width, height, depth,
					weight, ddmStructureKey, displayDateMonth, displayDateDay,
					displayDateYear, displayDateHour, displayDateMinute,
					expirationDateMonth, expirationDateDay, expirationDateYear,
					expirationDateHour, expirationDateMinute, neverExpire,
					serviceContext);

			return com.liferay.commerce.product.model.CPDefinitionSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPDefinitionSoap updateCPDefinition(
		long cpDefinitionId, java.lang.String[] titleMapLanguageIds,
		java.lang.String[] titleMapValues,
		java.lang.String[] shortDescriptionMapLanguageIds,
		java.lang.String[] shortDescriptionMapValues,
		java.lang.String[] descriptionMapLanguageIds,
		java.lang.String[] descriptionMapValues, java.lang.String layoutUuid,
		boolean ignoreSKUCombinations, java.lang.String ddmStructureKey,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, int expirationDateMonth,
		int expirationDateDay, int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean neverExpire,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(titleMapLanguageIds,
					titleMapValues);
			Map<Locale, String> shortDescriptionMap = LocalizationUtil.getLocalizationMap(shortDescriptionMapLanguageIds,
					shortDescriptionMapValues);
			Map<Locale, String> descriptionMap = LocalizationUtil.getLocalizationMap(descriptionMapLanguageIds,
					descriptionMapValues);

			com.liferay.commerce.product.model.CPDefinition returnValue = CPDefinitionServiceUtil.updateCPDefinition(cpDefinitionId,
					titleMap, shortDescriptionMap, descriptionMap, layoutUuid,
					ignoreSKUCombinations, ddmStructureKey, displayDateMonth,
					displayDateDay, displayDateYear, displayDateHour,
					displayDateMinute, expirationDateMonth, expirationDateDay,
					expirationDateYear, expirationDateHour,
					expirationDateMinute, neverExpire, serviceContext);

			return com.liferay.commerce.product.model.CPDefinitionSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPDefinitionSoap updateSEOInfo(
		long cpDefinitionId, java.lang.String[] urlTitleMapLanguageIds,
		java.lang.String[] urlTitleMapValues,
		java.lang.String[] metaTitleMapLanguageIds,
		java.lang.String[] metaTitleMapValues,
		java.lang.String[] metaKeywordsMapLanguageIds,
		java.lang.String[] metaKeywordsMapValues,
		java.lang.String[] metaDescriptionMapLanguageIds,
		java.lang.String[] metaDescriptionMapValues,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			Map<Locale, String> urlTitleMap = LocalizationUtil.getLocalizationMap(urlTitleMapLanguageIds,
					urlTitleMapValues);
			Map<Locale, String> metaTitleMap = LocalizationUtil.getLocalizationMap(metaTitleMapLanguageIds,
					metaTitleMapValues);
			Map<Locale, String> metaKeywordsMap = LocalizationUtil.getLocalizationMap(metaKeywordsMapLanguageIds,
					metaKeywordsMapValues);
			Map<Locale, String> metaDescriptionMap = LocalizationUtil.getLocalizationMap(metaDescriptionMapLanguageIds,
					metaDescriptionMapValues);

			com.liferay.commerce.product.model.CPDefinition returnValue = CPDefinitionServiceUtil.updateSEOInfo(cpDefinitionId,
					urlTitleMap, metaTitleMap, metaKeywordsMap,
					metaDescriptionMap, serviceContext);

			return com.liferay.commerce.product.model.CPDefinitionSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPDefinitionSoap updateShippingInfo(
		long cpDefinitionId, double width, double height, double depth,
		double weight,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.commerce.product.model.CPDefinition returnValue = CPDefinitionServiceUtil.updateShippingInfo(cpDefinitionId,
					width, height, depth, weight, serviceContext);

			return com.liferay.commerce.product.model.CPDefinitionSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CPDefinitionServiceSoap.class);
}