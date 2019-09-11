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
 * Provides the remote service utility for ProductConsumption. This utility wraps
 * <code>com.liferay.osb.koroneiki.trunk.service.impl.ProductConsumptionServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see ProductConsumptionService
 * @generated
 */
@ProviderType
public class ProductConsumptionServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.osb.koroneiki.trunk.service.impl.ProductConsumptionServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.osb.koroneiki.trunk.model.ProductConsumption
			addProductConsumption(
				long accountId, long productEntryId,
				java.util.List
					<com.liferay.osb.koroneiki.trunk.model.ProductField>
						productFields)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addProductConsumption(
			accountId, productEntryId, productFields);
	}

	public static com.liferay.osb.koroneiki.trunk.model.ProductConsumption
			addProductConsumption(
				String accountKey, String productEntryKey,
				java.util.List
					<com.liferay.osb.koroneiki.trunk.model.ProductField>
						productFields)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addProductConsumption(
			accountKey, productEntryKey, productFields);
	}

	public static com.liferay.osb.koroneiki.trunk.model.ProductConsumption
			deleteProductConsumption(long productConsumptionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteProductConsumption(productConsumptionId);
	}

	public static com.liferay.osb.koroneiki.trunk.model.ProductConsumption
			deleteProductConsumption(long accountId, long productEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteProductConsumption(accountId, productEntryId);
	}

	public static com.liferay.osb.koroneiki.trunk.model.ProductConsumption
			deleteProductConsumption(String productConsumptionKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteProductConsumption(productConsumptionKey);
	}

	public static java.util.List
		<com.liferay.osb.koroneiki.trunk.model.ProductConsumption>
				getAccountProductConsumptions(
					long accountId, int start, int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getAccountProductConsumptions(
			accountId, start, end);
	}

	public static java.util.List
		<com.liferay.osb.koroneiki.trunk.model.ProductConsumption>
				getAccountProductConsumptions(
					String accountKey, int start, int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getAccountProductConsumptions(
			accountKey, start, end);
	}

	public static int getAccountProductConsumptionsCount(long accountId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getAccountProductConsumptionsCount(accountId);
	}

	public static int getAccountProductConsumptionsCount(String accountKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getAccountProductConsumptionsCount(accountKey);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.osb.koroneiki.trunk.model.ProductConsumption
			getProductConsumption(long productConsumptionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getProductConsumption(productConsumptionId);
	}

	public static com.liferay.osb.koroneiki.trunk.model.ProductConsumption
			getProductConsumption(String productConsumptionKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getProductConsumption(productConsumptionKey);
	}

	public static ProductConsumptionService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<ProductConsumptionService, ProductConsumptionService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			ProductConsumptionService.class);

		ServiceTracker<ProductConsumptionService, ProductConsumptionService>
			serviceTracker =
				new ServiceTracker
					<ProductConsumptionService, ProductConsumptionService>(
						bundle.getBundleContext(),
						ProductConsumptionService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}