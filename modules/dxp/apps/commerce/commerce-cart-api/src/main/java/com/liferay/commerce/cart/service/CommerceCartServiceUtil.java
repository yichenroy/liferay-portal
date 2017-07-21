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

package com.liferay.commerce.cart.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for CommerceCart. This utility wraps
 * {@link com.liferay.commerce.cart.service.impl.CommerceCartServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Marco Leo
 * @see CommerceCartService
 * @see com.liferay.commerce.cart.service.base.CommerceCartServiceBaseImpl
 * @see com.liferay.commerce.cart.service.impl.CommerceCartServiceImpl
 * @generated
 */
@ProviderType
public class CommerceCartServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.cart.service.impl.CommerceCartServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.cart.model.CommerceCart addCommerceCart(
		java.lang.String name, int type,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().addCommerceCart(name, type, serviceContext);
	}

	public static com.liferay.commerce.cart.model.CommerceCart fetchCommerceCart(
		long commerceCartId) {
		return getService().fetchCommerceCart(commerceCartId);
	}

	public static int getCommerceCartsCount(long groupId, int type) {
		return getService().getCommerceCartsCount(groupId, type);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static java.util.List<com.liferay.commerce.cart.model.CommerceCart> getCommerceCarts(
		long groupId, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.cart.model.CommerceCart> orderByComparator) {
		return getService()
				   .getCommerceCarts(groupId, type, start, end,
			orderByComparator);
	}

	public static CommerceCartService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceCartService, CommerceCartService> _serviceTracker =
		ServiceTrackerFactory.open(CommerceCartService.class);
}