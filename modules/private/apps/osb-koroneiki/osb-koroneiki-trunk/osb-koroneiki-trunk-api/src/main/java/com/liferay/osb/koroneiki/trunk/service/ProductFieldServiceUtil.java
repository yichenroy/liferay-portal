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
 * Provides the remote service utility for ProductField. This utility wraps
 * <code>com.liferay.osb.koroneiki.trunk.service.impl.ProductFieldServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see ProductFieldService
 * @generated
 */
@ProviderType
public class ProductFieldServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.osb.koroneiki.trunk.service.impl.ProductFieldServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.osb.koroneiki.trunk.model.ProductField
			addProductField(
				long classNameId, long classPK, String name, String value)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addProductField(classNameId, classPK, name, value);
	}

	public static com.liferay.osb.koroneiki.trunk.model.ProductField
			deleteProductField(long productFieldId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteProductField(productFieldId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.osb.koroneiki.trunk.model.ProductField
			updateProductField(long productFieldId, String value)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateProductField(productFieldId, value);
	}

	public static ProductFieldService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<ProductFieldService, ProductFieldService>
		_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(ProductFieldService.class);

		ServiceTracker<ProductFieldService, ProductFieldService>
			serviceTracker =
				new ServiceTracker<ProductFieldService, ProductFieldService>(
					bundle.getBundleContext(), ProductFieldService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}