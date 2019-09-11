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
 * Provides the remote service utility for ProductEntry. This utility wraps
 * <code>com.liferay.osb.koroneiki.trunk.service.impl.ProductEntryServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see ProductEntryService
 * @generated
 */
@ProviderType
public class ProductEntryServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.osb.koroneiki.trunk.service.impl.ProductEntryServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.osb.koroneiki.trunk.model.ProductEntry
			addProductEntry(String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addProductEntry(name);
	}

	public static com.liferay.osb.koroneiki.trunk.model.ProductEntry
			deleteProductEntry(long productEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteProductEntry(productEntryId);
	}

	public static com.liferay.osb.koroneiki.trunk.model.ProductEntry
			deleteProductEntry(String productEntryKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteProductEntry(productEntryKey);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static java.util.List
		<com.liferay.osb.koroneiki.trunk.model.ProductEntry> getProductEntries(
				int start, int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getProductEntries(start, end);
	}

	public static int getProductEntriesCount()
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getProductEntriesCount();
	}

	public static com.liferay.osb.koroneiki.trunk.model.ProductEntry
			getProductEntry(long productEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getProductEntry(productEntryId);
	}

	public static com.liferay.osb.koroneiki.trunk.model.ProductEntry
			getProductEntry(String productEntryKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getProductEntry(productEntryKey);
	}

	public static com.liferay.osb.koroneiki.trunk.model.ProductEntry
			updateProductEntry(long productEntryId, String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateProductEntry(productEntryId, name);
	}

	public static com.liferay.osb.koroneiki.trunk.model.ProductEntry
			updateProductEntry(String productEntryKey, String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateProductEntry(productEntryKey, name);
	}

	public static ProductEntryService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<ProductEntryService, ProductEntryService>
		_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(ProductEntryService.class);

		ServiceTracker<ProductEntryService, ProductEntryService>
			serviceTracker =
				new ServiceTracker<ProductEntryService, ProductEntryService>(
					bundle.getBundleContext(), ProductEntryService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}