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
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * {@link CPDefinitionServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it requires an additional
 * {@link HttpPrincipal} parameter.
 *
 * <p>
 * The benefits of using the HTTP utility is that it is fast and allows for
 * tunneling without the cost of serializing to text. The drawback is that it
 * only works with Java.
 * </p>
 *
 * <p>
 * Set the property <b>tunnel.servlet.hosts.allowed</b> in portal.properties to
 * configure security.
 * </p>
 *
 * <p>
 * The HTTP utility is only generated for remote services.
 * </p>
 *
 * @author Marco Leo
 * @see CPDefinitionServiceSoap
 * @see HttpPrincipal
 * @see CPDefinitionServiceUtil
 * @generated
 */
@ProviderType
public class CPDefinitionServiceHttp {
	public static com.liferay.commerce.product.model.CPDefinition addCPDefinition(
		HttpPrincipal httpPrincipal, java.lang.String baseSKU,
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
		double width, double height, double depth, double weight, double cost,
		double price, java.lang.String ddmStructureKey, int displayDateMonth,
		int displayDateDay, int displayDateYear, int displayDateHour,
		int displayDateMinute, int expirationDateMonth, int expirationDateDay,
		int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean neverExpire,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPDefinitionServiceUtil.class,
					"addCPDefinition", _addCPDefinitionParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey, baseSKU,
					titleMap, shortDescriptionMap, descriptionMap, urlTitleMap,
					metaTitleMap, metaKeywordsMap, metaDescriptionMap,
					layoutUuid, productTypeName, gtin, manufacturerPartNumber,
					minCartQuantity, maxCartQuantity, allowedCartQuantities,
					multipleCartQuantity, width, height, depth, weight, cost,
					price, ddmStructureKey, displayDateMonth, displayDateDay,
					displayDateYear, displayDateHour, displayDateMinute,
					expirationDateMonth, expirationDateDay, expirationDateYear,
					expirationDateHour, expirationDateMinute, neverExpire,
					serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.commerce.product.model.CPDefinition)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.product.model.CPDefinition addCPDefinition(
		HttpPrincipal httpPrincipal, java.lang.String baseSKU,
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
		try {
			MethodKey methodKey = new MethodKey(CPDefinitionServiceUtil.class,
					"addCPDefinition", _addCPDefinitionParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey, baseSKU,
					titleMap, shortDescriptionMap, descriptionMap, urlTitleMap,
					metaTitleMap, metaKeywordsMap, metaDescriptionMap,
					layoutUuid, productTypeName, gtin, manufacturerPartNumber,
					minCartQuantity, maxCartQuantity, allowedCartQuantities,
					multipleCartQuantity, ddmStructureKey, displayDateMonth,
					displayDateDay, displayDateYear, displayDateHour,
					displayDateMinute, expirationDateMonth, expirationDateDay,
					expirationDateYear, expirationDateHour,
					expirationDateMinute, neverExpire, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.commerce.product.model.CPDefinition)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteAssetCategoryCPDefinition(
		HttpPrincipal httpPrincipal, long cpDefinitionId, long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPDefinitionServiceUtil.class,
					"deleteAssetCategoryCPDefinition",
					_deleteAssetCategoryCPDefinitionParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpDefinitionId, categoryId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.product.model.CPDefinition deleteCPDefinition(
		HttpPrincipal httpPrincipal, long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPDefinitionServiceUtil.class,
					"deleteCPDefinition", _deleteCPDefinitionParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpDefinitionId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.commerce.product.model.CPDefinition)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.product.model.CPDefinition fetchCPDefinition(
		HttpPrincipal httpPrincipal, long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPDefinitionServiceUtil.class,
					"fetchCPDefinition", _fetchCPDefinitionParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpDefinitionId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.commerce.product.model.CPDefinition)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.product.model.CPDefinition getCPDefinition(
		HttpPrincipal httpPrincipal, long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPDefinitionServiceUtil.class,
					"getCPDefinition", _getCPDefinitionParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpDefinitionId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.commerce.product.model.CPDefinition)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.commerce.product.model.CPDefinition> getCPDefinitions(
		HttpPrincipal httpPrincipal, long groupId,
		java.lang.String productTypeName, java.lang.String languageId,
		int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPDefinition> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPDefinitionServiceUtil.class,
					"getCPDefinitions", _getCPDefinitionsParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					productTypeName, languageId, status, start, end,
					orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.commerce.product.model.CPDefinition>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.commerce.product.model.CPDefinition> getCPDefinitionsByCategoryId(
		HttpPrincipal httpPrincipal, long categoryId, int start, int end) {
		try {
			MethodKey methodKey = new MethodKey(CPDefinitionServiceUtil.class,
					"getCPDefinitionsByCategoryId",
					_getCPDefinitionsByCategoryIdParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					categoryId, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.commerce.product.model.CPDefinition>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getCPDefinitionsCount(HttpPrincipal httpPrincipal,
		long groupId, java.lang.String productTypeName,
		java.lang.String languageId, int status) {
		try {
			MethodKey methodKey = new MethodKey(CPDefinitionServiceUtil.class,
					"getCPDefinitionsCount",
					_getCPDefinitionsCountParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					productTypeName, languageId, status);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getCPDefinitionsCountByCategoryId(
		HttpPrincipal httpPrincipal, long categoryId) {
		try {
			MethodKey methodKey = new MethodKey(CPDefinitionServiceUtil.class,
					"getCPDefinitionsCountByCategoryId",
					_getCPDefinitionsCountByCategoryIdParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					categoryId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.product.model.CPAttachmentFileEntry getDefaultImage(
		HttpPrincipal httpPrincipal, long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPDefinitionServiceUtil.class,
					"getDefaultImage", _getDefaultImageParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpDefinitionId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.commerce.product.model.CPAttachmentFileEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.lang.String getLayoutUuid(HttpPrincipal httpPrincipal,
		long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPDefinitionServiceUtil.class,
					"getLayoutUuid", _getLayoutUuidParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpDefinitionId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.lang.String)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.lang.String getUrlTitleMapAsXML(
		HttpPrincipal httpPrincipal, long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPDefinitionServiceUtil.class,
					"getUrlTitleMapAsXML", _getUrlTitleMapAsXMLParameterTypes12);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpDefinitionId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.lang.String)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.product.model.CPDefinition moveCPDefinitionToTrash(
		HttpPrincipal httpPrincipal, long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPDefinitionServiceUtil.class,
					"moveCPDefinitionToTrash",
					_moveCPDefinitionToTrashParameterTypes13);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpDefinitionId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.commerce.product.model.CPDefinition)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void restoreCPDefinitionFromTrash(
		HttpPrincipal httpPrincipal, long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPDefinitionServiceUtil.class,
					"restoreCPDefinitionFromTrash",
					_restoreCPDefinitionFromTrashParameterTypes14);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpDefinitionId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.search.Hits search(
		HttpPrincipal httpPrincipal,
		com.liferay.portal.kernel.search.SearchContext searchContext) {
		try {
			MethodKey methodKey = new MethodKey(CPDefinitionServiceUtil.class,
					"search", _searchParameterTypes15);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					searchContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portal.kernel.search.Hits)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.product.model.CPDefinition> searchCPDefinitions(
		HttpPrincipal httpPrincipal, long companyId, long groupId,
		java.lang.String keywords, int status, int start, int end,
		com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPDefinitionServiceUtil.class,
					"searchCPDefinitions", _searchCPDefinitionsParameterTypes16);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					companyId, groupId, keywords, status, start, end, sort);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.product.model.CPDefinition>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.product.model.CPDefinition updateCPDefinition(
		HttpPrincipal httpPrincipal, long cpDefinitionId,
		java.lang.String baseSKU,
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
		double weight, double cost, double price,
		java.lang.String ddmStructureKey, int displayDateMonth,
		int displayDateDay, int displayDateYear, int displayDateHour,
		int displayDateMinute, int expirationDateMonth, int expirationDateDay,
		int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean neverExpire,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPDefinitionServiceUtil.class,
					"updateCPDefinition", _updateCPDefinitionParameterTypes17);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpDefinitionId, baseSKU, titleMap, shortDescriptionMap,
					descriptionMap, urlTitleMap, metaTitleMap, metaKeywordsMap,
					metaDescriptionMap, layoutUuid, gtin,
					manufacturerPartNumber, minCartQuantity, maxCartQuantity,
					allowedCartQuantities, multipleCartQuantity, width, height,
					depth, weight, cost, price, ddmStructureKey,
					displayDateMonth, displayDateDay, displayDateYear,
					displayDateHour, displayDateMinute, expirationDateMonth,
					expirationDateDay, expirationDateYear, expirationDateHour,
					expirationDateMinute, neverExpire, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.commerce.product.model.CPDefinition)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.product.model.CPDefinition updateCPDefinition(
		HttpPrincipal httpPrincipal, long cpDefinitionId,
		java.lang.String baseSKU,
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
		try {
			MethodKey methodKey = new MethodKey(CPDefinitionServiceUtil.class,
					"updateCPDefinition", _updateCPDefinitionParameterTypes18);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpDefinitionId, baseSKU, titleMap, shortDescriptionMap,
					descriptionMap, urlTitleMap, metaTitleMap, metaKeywordsMap,
					metaDescriptionMap, layoutUuid, gtin,
					manufacturerPartNumber, minCartQuantity, maxCartQuantity,
					allowedCartQuantities, multipleCartQuantity,
					ddmStructureKey, displayDateMonth, displayDateDay,
					displayDateYear, displayDateHour, displayDateMinute,
					expirationDateMonth, expirationDateDay, expirationDateYear,
					expirationDateHour, expirationDateMinute, neverExpire,
					serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.commerce.product.model.CPDefinition)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.product.model.CPDefinition updatePricingInfo(
		HttpPrincipal httpPrincipal, long cpDefinitionId, double cost,
		double price)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPDefinitionServiceUtil.class,
					"updatePricingInfo", _updatePricingInfoParameterTypes19);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpDefinitionId, cost, price);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.commerce.product.model.CPDefinition)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.product.model.CPDefinition updateShippingInfo(
		HttpPrincipal httpPrincipal, long cpDefinitionId, double width,
		double height, double depth, double weight)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPDefinitionServiceUtil.class,
					"updateShippingInfo", _updateShippingInfoParameterTypes20);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpDefinitionId, width, height, depth, weight);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.commerce.product.model.CPDefinition)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.product.model.CPDefinition updateStatus(
		HttpPrincipal httpPrincipal, long userId, long cpDefinitionId,
		int status,
		com.liferay.portal.kernel.service.ServiceContext serviceContext,
		java.util.Map<java.lang.String, java.io.Serializable> workflowContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPDefinitionServiceUtil.class,
					"updateStatus", _updateStatusParameterTypes21);

			MethodHandler methodHandler = new MethodHandler(methodKey, userId,
					cpDefinitionId, status, serviceContext, workflowContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.commerce.product.model.CPDefinition)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CPDefinitionServiceHttp.class);
	private static final Class<?>[] _addCPDefinitionParameterTypes0 = new Class[] {
			java.lang.String.class, java.util.Map.class, java.util.Map.class,
			java.util.Map.class, java.util.Map.class, java.util.Map.class,
			java.util.Map.class, java.util.Map.class, java.lang.String.class,
			java.lang.String.class, java.lang.String.class,
			java.lang.String.class, int.class, int.class, java.lang.String.class,
			int.class, double.class, double.class, double.class, double.class,
			double.class, double.class, java.lang.String.class, int.class,
			int.class, int.class, int.class, int.class, int.class, int.class,
			int.class, int.class, int.class, boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _addCPDefinitionParameterTypes1 = new Class[] {
			java.lang.String.class, java.util.Map.class, java.util.Map.class,
			java.util.Map.class, java.util.Map.class, java.util.Map.class,
			java.util.Map.class, java.util.Map.class, java.lang.String.class,
			java.lang.String.class, java.lang.String.class,
			java.lang.String.class, int.class, int.class, java.lang.String.class,
			int.class, java.lang.String.class, int.class, int.class, int.class,
			int.class, int.class, int.class, int.class, int.class, int.class,
			int.class, boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteAssetCategoryCPDefinitionParameterTypes2 =
		new Class[] { long.class, long.class };
	private static final Class<?>[] _deleteCPDefinitionParameterTypes3 = new Class[] {
			long.class
		};
	private static final Class<?>[] _fetchCPDefinitionParameterTypes4 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getCPDefinitionParameterTypes5 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getCPDefinitionsParameterTypes6 = new Class[] {
			long.class, java.lang.String.class, java.lang.String.class,
			int.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getCPDefinitionsByCategoryIdParameterTypes7 =
		new Class[] { long.class, int.class, int.class };
	private static final Class<?>[] _getCPDefinitionsCountParameterTypes8 = new Class[] {
			long.class, java.lang.String.class, java.lang.String.class,
			int.class
		};
	private static final Class<?>[] _getCPDefinitionsCountByCategoryIdParameterTypes9 =
		new Class[] { long.class };
	private static final Class<?>[] _getDefaultImageParameterTypes10 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getLayoutUuidParameterTypes11 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getUrlTitleMapAsXMLParameterTypes12 = new Class[] {
			long.class
		};
	private static final Class<?>[] _moveCPDefinitionToTrashParameterTypes13 = new Class[] {
			long.class
		};
	private static final Class<?>[] _restoreCPDefinitionFromTrashParameterTypes14 =
		new Class[] { long.class };
	private static final Class<?>[] _searchParameterTypes15 = new Class[] {
			com.liferay.portal.kernel.search.SearchContext.class
		};
	private static final Class<?>[] _searchCPDefinitionsParameterTypes16 = new Class[] {
			long.class, long.class, java.lang.String.class, int.class, int.class,
			int.class, com.liferay.portal.kernel.search.Sort.class
		};
	private static final Class<?>[] _updateCPDefinitionParameterTypes17 = new Class[] {
			long.class, java.lang.String.class, java.util.Map.class,
			java.util.Map.class, java.util.Map.class, java.util.Map.class,
			java.util.Map.class, java.util.Map.class, java.util.Map.class,
			java.lang.String.class, java.lang.String.class,
			java.lang.String.class, int.class, int.class, java.lang.String.class,
			int.class, double.class, double.class, double.class, double.class,
			double.class, double.class, java.lang.String.class, int.class,
			int.class, int.class, int.class, int.class, int.class, int.class,
			int.class, int.class, int.class, boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _updateCPDefinitionParameterTypes18 = new Class[] {
			long.class, java.lang.String.class, java.util.Map.class,
			java.util.Map.class, java.util.Map.class, java.util.Map.class,
			java.util.Map.class, java.util.Map.class, java.util.Map.class,
			java.lang.String.class, java.lang.String.class,
			java.lang.String.class, int.class, int.class, java.lang.String.class,
			int.class, java.lang.String.class, int.class, int.class, int.class,
			int.class, int.class, int.class, int.class, int.class, int.class,
			int.class, boolean.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _updatePricingInfoParameterTypes19 = new Class[] {
			long.class, double.class, double.class
		};
	private static final Class<?>[] _updateShippingInfoParameterTypes20 = new Class[] {
			long.class, double.class, double.class, double.class, double.class
		};
	private static final Class<?>[] _updateStatusParameterTypes21 = new Class[] {
			long.class, long.class, int.class,
			com.liferay.portal.kernel.service.ServiceContext.class,
			java.util.Map.class
		};
}