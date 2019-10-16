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

package com.liferay.commerce.product.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for CPInstance. This utility wraps
 * {@link com.liferay.commerce.product.service.impl.CPInstanceServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Marco Leo
 * @see CPInstanceService
 * @see com.liferay.commerce.product.service.base.CPInstanceServiceBaseImpl
 * @see com.liferay.commerce.product.service.impl.CPInstanceServiceImpl
 * @generated
 */
@ProviderType
public class CPInstanceServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.product.service.impl.CPInstanceServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.product.model.CPInstance addCPInstance(
		long cpDefinitionId, String sku, String gtin,
		String manufacturerPartNumber, boolean purchasable, String ddmContent,
		boolean published, int displayDateMonth, int displayDateDay,
		int displayDateYear, int displayDateHour, int displayDateMinute,
		int expirationDateMonth, int expirationDateDay, int expirationDateYear,
		int expirationDateHour, int expirationDateMinute, boolean neverExpire,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCPInstance(cpDefinitionId, sku, gtin,
			manufacturerPartNumber, purchasable, ddmContent, published,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, serviceContext);
	}

	public static com.liferay.commerce.product.model.CPInstance addCPInstance(
		long cpDefinitionId, String sku, String gtin,
		String manufacturerPartNumber, boolean purchasable, String ddmContent,
		double width, double height, double depth, double weight,
		java.math.BigDecimal price, java.math.BigDecimal promoPrice,
		java.math.BigDecimal cost, boolean published,
		String externalReferenceCode, int displayDateMonth, int displayDateDay,
		int displayDateYear, int displayDateHour, int displayDateMinute,
		int expirationDateMonth, int expirationDateDay, int expirationDateYear,
		int expirationDateHour, int expirationDateMinute, boolean neverExpire,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCPInstance(cpDefinitionId, sku, gtin,
			manufacturerPartNumber, purchasable, ddmContent, width, height,
			depth, weight, price, promoPrice, cost, published,
			externalReferenceCode, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			serviceContext);
	}

	public static void buildCPInstances(long cpDefinitionId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().buildCPInstances(cpDefinitionId, serviceContext);
	}

	public static com.liferay.commerce.product.model.CPInstance deleteCPInstance(
		com.liferay.commerce.product.model.CPInstance cpInstance)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteCPInstance(cpInstance);
	}

	public static com.liferay.commerce.product.model.CPInstance deleteCPInstance(
		long cpInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteCPInstance(cpInstanceId);
	}

	public static com.liferay.commerce.product.model.CPInstance fetchCPInstance(
		long cpInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().fetchCPInstance(cpInstanceId);
	}

	public static java.util.List<com.liferay.commerce.product.model.CPInstance> getCPDefinitionInstances(
		long cpDefinitionId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCPDefinitionInstances(cpDefinitionId, start, end);
	}

	public static java.util.List<com.liferay.commerce.product.model.CPInstance> getCPDefinitionInstances(
		long cpDefinitionId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPInstance> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCPDefinitionInstances(cpDefinitionId, status, start,
			end, orderByComparator);
	}

	public static int getCPDefinitionInstancesCount(long cpDefinitionId,
		int status) throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCPDefinitionInstancesCount(cpDefinitionId, status);
	}

	public static com.liferay.commerce.product.model.CPInstance getCPInstance(
		long cpInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCPInstance(cpInstanceId);
	}

	public static com.liferay.commerce.product.model.CPInstance getCPInstance(
		long cpDefinitionId, String sku)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCPInstance(cpDefinitionId, sku);
	}

	public static com.liferay.commerce.product.model.CPInstance getCPInstance(
		String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCPInstance(externalReferenceCode);
	}

	public static java.util.List<com.liferay.commerce.product.model.CPInstance> getCPInstances(
		long groupId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPInstance> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCPInstances(groupId, status, start, end,
			orderByComparator);
	}

	public static int getCPInstancesCount(long groupId, int status)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCPInstancesCount(groupId, status);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.search.Hits search(
		com.liferay.portal.kernel.search.SearchContext searchContext) {
		return getService().search(searchContext);
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.product.model.CPInstance> searchCPDefinitionInstances(
		long companyId, long groupId, long cpDefinitionId, String keywords,
		int status, int start, int end,
		com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .searchCPDefinitionInstances(companyId, groupId,
			cpDefinitionId, keywords, status, start, end, sort);
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.product.model.CPInstance> searchCPInstances(
		long companyId, long groupId, String keywords, int status, int start,
		int end, com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .searchCPInstances(companyId, groupId, keywords, status,
			start, end, sort);
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.product.model.CPInstance> searchCPInstances(
		com.liferay.portal.kernel.search.SearchContext searchContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().searchCPInstances(searchContext);
	}

	public static com.liferay.commerce.product.model.CPInstance updateCPInstance(
		long cpInstanceId, String sku, String gtin,
		String manufacturerPartNumber, boolean purchasable, boolean published,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, int expirationDateMonth,
		int expirationDateDay, int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean neverExpire,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCPInstance(cpInstanceId, sku, gtin,
			manufacturerPartNumber, purchasable, published, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, serviceContext);
	}

	public static com.liferay.commerce.product.model.CPInstance updateCPInstance(
		long cpInstanceId, String sku, String gtin,
		String manufacturerPartNumber, boolean purchasable, double width,
		double height, double depth, double weight, java.math.BigDecimal price,
		java.math.BigDecimal promoPrice, java.math.BigDecimal cost,
		boolean published, int displayDateMonth, int displayDateDay,
		int displayDateYear, int displayDateHour, int displayDateMinute,
		int expirationDateMonth, int expirationDateDay, int expirationDateYear,
		int expirationDateHour, int expirationDateMinute, boolean neverExpire,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCPInstance(cpInstanceId, sku, gtin,
			manufacturerPartNumber, purchasable, width, height, depth, weight,
			price, promoPrice, cost, published, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, serviceContext);
	}

	public static com.liferay.commerce.product.model.CPInstance updatePricingInfo(
		long cpInstanceId, java.math.BigDecimal price,
		java.math.BigDecimal promoPrice, java.math.BigDecimal cost,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updatePricingInfo(cpInstanceId, price, promoPrice, cost,
			serviceContext);
	}

	public static com.liferay.commerce.product.model.CPInstance updateShippingInfo(
		long cpInstanceId, double width, double height, double depth,
		double weight,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateShippingInfo(cpInstanceId, width, height, depth,
			weight, serviceContext);
	}

	public static com.liferay.commerce.product.model.CPInstance updateStatus(
		long userId, long cpInstanceId, int status,
		com.liferay.portal.kernel.service.ServiceContext serviceContext,
		java.util.Map<String, java.io.Serializable> workflowContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateStatus(userId, cpInstanceId, status, serviceContext,
			workflowContext);
	}

	public static CPInstanceService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CPInstanceService, CPInstanceService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CPInstanceService.class);

		ServiceTracker<CPInstanceService, CPInstanceService> serviceTracker = new ServiceTracker<CPInstanceService, CPInstanceService>(bundle.getBundleContext(),
				CPInstanceService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}