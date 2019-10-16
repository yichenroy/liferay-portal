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

package com.liferay.commerce.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.service.CommerceInventoryServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * {@link CommerceInventoryServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.commerce.model.CommerceInventorySoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.commerce.model.CommerceInventory}, that is translated to a
 * {@link com.liferay.commerce.model.CommerceInventorySoap}. Methods that SOAP cannot
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
 * @author Alessio Antonio Rendina
 * @see CommerceInventoryServiceHttp
 * @see com.liferay.commerce.model.CommerceInventorySoap
 * @see CommerceInventoryServiceUtil
 * @generated
 */
@ProviderType
public class CommerceInventoryServiceSoap {
	public static com.liferay.commerce.model.CommerceInventorySoap addCommerceInventory(
		long cpDefinitionId, java.lang.String commerceInventoryEngine,
		boolean displayAvailability, boolean displayStockQuantity,
		int minStockQuantity, boolean backOrders, int minCartQuantity,
		int maxCartQuantity, java.lang.String allowedCartQuantities,
		int multipleCartQuantity,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.commerce.model.CommerceInventory returnValue = CommerceInventoryServiceUtil.addCommerceInventory(cpDefinitionId,
					commerceInventoryEngine, displayAvailability,
					displayStockQuantity, minStockQuantity, backOrders,
					minCartQuantity, maxCartQuantity, allowedCartQuantities,
					multipleCartQuantity, serviceContext);

			return com.liferay.commerce.model.CommerceInventorySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteCommerceInventory(long commerceInventoryId)
		throws RemoteException {
		try {
			CommerceInventoryServiceUtil.deleteCommerceInventory(commerceInventoryId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceInventorySoap fetchCommerceInventory(
		long commerceInventoryId) throws RemoteException {
		try {
			com.liferay.commerce.model.CommerceInventory returnValue = CommerceInventoryServiceUtil.fetchCommerceInventory(commerceInventoryId);

			return com.liferay.commerce.model.CommerceInventorySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceInventorySoap getCommerceInventory(
		long groupId, long cpDefinitionId) throws RemoteException {
		try {
			com.liferay.commerce.model.CommerceInventory returnValue = CommerceInventoryServiceUtil.getCommerceInventory(groupId,
					cpDefinitionId);

			return com.liferay.commerce.model.CommerceInventorySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceInventorySoap updateCommerceInventory(
		long commerceInventoryId, boolean displayAvailability,
		boolean displayStockQuantity, int minStockQuantity, boolean backOrders,
		int minCartQuantity, int maxCartQuantity,
		java.lang.String allowedCartQuantities, int multipleCartQuantity,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.commerce.model.CommerceInventory returnValue = CommerceInventoryServiceUtil.updateCommerceInventory(commerceInventoryId,
					displayAvailability, displayStockQuantity,
					minStockQuantity, backOrders, minCartQuantity,
					maxCartQuantity, allowedCartQuantities,
					multipleCartQuantity, serviceContext);

			return com.liferay.commerce.model.CommerceInventorySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CommerceInventoryServiceSoap.class);
}