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

package com.liferay.osb.koroneiki.trunk.service;

import org.osgi.annotation.versioning.ProviderType;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for ProductPurchase. This utility wraps
 * <code>com.liferay.osb.koroneiki.trunk.service.impl.ProductPurchaseServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see ProductPurchaseService
 * @generated
 */
@ProviderType
public class ProductPurchaseServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.osb.koroneiki.trunk.service.impl.ProductPurchaseServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.osb.koroneiki.trunk.model.ProductPurchase
			addProductPurchase(
				long accountId, long productEntryId, java.util.Date startDate,
				java.util.Date endDate, int quantity,
				java.util.List
					<com.liferay.osb.koroneiki.trunk.model.ProductField>
						productFields)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addProductPurchase(
			accountId, productEntryId, startDate, endDate, quantity,
			productFields);
	}

	public static com.liferay.osb.koroneiki.trunk.model.ProductPurchase
			addProductPurchase(
				String accountKey, String productEntryKey,
				java.util.Date startDate, java.util.Date endDate, int quantity,
				java.util.List
					<com.liferay.osb.koroneiki.trunk.model.ProductField>
						productFields)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addProductPurchase(
			accountKey, productEntryKey, startDate, endDate, quantity,
			productFields);
	}

	public static com.liferay.osb.koroneiki.trunk.model.ProductPurchase
			deleteProductPurchase(long productPurchaseId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteProductPurchase(productPurchaseId);
	}

	public static com.liferay.osb.koroneiki.trunk.model.ProductPurchase
			deleteProductPurchase(String productPurchaseKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteProductPurchase(productPurchaseKey);
	}

	public static java.util.List
		<com.liferay.osb.koroneiki.trunk.model.ProductPurchase>
				getAccountProductPurchases(long accountId, int start, int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getAccountProductPurchases(accountId, start, end);
	}

	public static java.util.List
		<com.liferay.osb.koroneiki.trunk.model.ProductPurchase>
				getAccountProductPurchases(
					String accountKey, int start, int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getAccountProductPurchases(accountKey, start, end);
	}

	public static int getAccountProductPurchasesCount(long accountId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getAccountProductPurchasesCount(accountId);
	}

	public static int getAccountProductPurchasesCount(String accountKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getAccountProductPurchasesCount(accountKey);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.osb.koroneiki.trunk.model.ProductPurchase
			getProductPurchase(long productPurchaseId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getProductPurchase(productPurchaseId);
	}

	public static com.liferay.osb.koroneiki.trunk.model.ProductPurchase
			getProductPurchase(String productPurchaseKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getProductPurchase(productPurchaseKey);
	}

	public static com.liferay.osb.koroneiki.trunk.model.ProductPurchase
			updateProductPurchase(
				long productPurchaseId, java.util.Date startDate,
				java.util.Date endDate, int quantity,
				java.util.List
					<com.liferay.osb.koroneiki.trunk.model.ProductField>
						productFields)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateProductPurchase(
			productPurchaseId, startDate, endDate, quantity, productFields);
	}

	public static ProductPurchaseService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<ProductPurchaseService, ProductPurchaseService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(ProductPurchaseService.class);

		ServiceTracker<ProductPurchaseService, ProductPurchaseService>
			serviceTracker =
				new ServiceTracker
					<ProductPurchaseService, ProductPurchaseService>(
						bundle.getBundleContext(), ProductPurchaseService.class,
						null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}