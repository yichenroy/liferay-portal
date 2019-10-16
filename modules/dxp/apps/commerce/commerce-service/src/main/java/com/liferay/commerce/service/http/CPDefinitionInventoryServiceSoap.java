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

package com.liferay.commerce.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.service.CPDefinitionInventoryServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * {@link CPDefinitionInventoryServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.commerce.model.CPDefinitionInventorySoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.commerce.model.CPDefinitionInventory}, that is translated to a
 * {@link com.liferay.commerce.model.CPDefinitionInventorySoap}. Methods that SOAP cannot
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
 * @see CPDefinitionInventoryServiceHttp
 * @see com.liferay.commerce.model.CPDefinitionInventorySoap
 * @see CPDefinitionInventoryServiceUtil
 * @generated
 */
@ProviderType
public class CPDefinitionInventoryServiceSoap {
	public static com.liferay.commerce.model.CPDefinitionInventorySoap addCPDefinitionInventory(
		long cpDefinitionId, String cpDefinitionInventoryEngine,
		String lowStockActivity, boolean displayAvailability,
		boolean displayStockQuantity, int minStockQuantity, boolean backOrders,
		int minOrderQuantity, int maxOrderQuantity,
		String allowedOrderQuantities, int multipleOrderQuantity,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.commerce.model.CPDefinitionInventory returnValue = CPDefinitionInventoryServiceUtil.addCPDefinitionInventory(cpDefinitionId,
					cpDefinitionInventoryEngine, lowStockActivity,
					displayAvailability, displayStockQuantity,
					minStockQuantity, backOrders, minOrderQuantity,
					maxOrderQuantity, allowedOrderQuantities,
					multipleOrderQuantity, serviceContext);

			return com.liferay.commerce.model.CPDefinitionInventorySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteCPDefinitionInventory(long cpDefinitionInventoryId)
		throws RemoteException {
		try {
			CPDefinitionInventoryServiceUtil.deleteCPDefinitionInventory(cpDefinitionInventoryId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.model.CPDefinitionInventorySoap fetchCPDefinitionInventory(
		long cpDefinitionInventoryId) throws RemoteException {
		try {
			com.liferay.commerce.model.CPDefinitionInventory returnValue = CPDefinitionInventoryServiceUtil.fetchCPDefinitionInventory(cpDefinitionInventoryId);

			return com.liferay.commerce.model.CPDefinitionInventorySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.model.CPDefinitionInventorySoap fetchCPDefinitionInventoryByCPDefinitionId(
		long cpDefinitionId) throws RemoteException {
		try {
			com.liferay.commerce.model.CPDefinitionInventory returnValue = CPDefinitionInventoryServiceUtil.fetchCPDefinitionInventoryByCPDefinitionId(cpDefinitionId);

			return com.liferay.commerce.model.CPDefinitionInventorySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.model.CPDefinitionInventorySoap updateCPDefinitionInventory(
		long cpDefinitionInventoryId, String cpDefinitionInventoryEngine,
		String lowStockActivity, boolean displayAvailability,
		boolean displayStockQuantity, int minStockQuantity, boolean backOrders,
		int minOrderQuantity, int maxOrderQuantity,
		String allowedOrderQuantities, int multipleOrderQuantity,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.commerce.model.CPDefinitionInventory returnValue = CPDefinitionInventoryServiceUtil.updateCPDefinitionInventory(cpDefinitionInventoryId,
					cpDefinitionInventoryEngine, lowStockActivity,
					displayAvailability, displayStockQuantity,
					minStockQuantity, backOrders, minOrderQuantity,
					maxOrderQuantity, allowedOrderQuantities,
					multipleOrderQuantity, serviceContext);

			return com.liferay.commerce.model.CPDefinitionInventorySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CPDefinitionInventoryServiceSoap.class);
}