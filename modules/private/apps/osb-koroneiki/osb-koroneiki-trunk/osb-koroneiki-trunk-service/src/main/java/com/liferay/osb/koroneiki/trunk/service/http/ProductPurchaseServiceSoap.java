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

package com.liferay.osb.koroneiki.trunk.service.http;

import com.liferay.osb.koroneiki.trunk.service.ProductPurchaseServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the SOAP utility for the
 * <code>ProductPurchaseServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.osb.koroneiki.trunk.model.ProductPurchaseSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.osb.koroneiki.trunk.model.ProductPurchase</code>, that is translated to a
 * <code>com.liferay.osb.koroneiki.trunk.model.ProductPurchaseSoap</code>. Methods that SOAP
 * cannot safely wire are skipped.
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
 * @author Brian Wing Shun Chan
 * @see ProductPurchaseServiceHttp
 * @generated
 */
@ProviderType
public class ProductPurchaseServiceSoap {

	public static com.liferay.osb.koroneiki.trunk.model.ProductPurchaseSoap
			addProductPurchase(
				long accountId, long productEntryId, java.util.Date startDate,
				java.util.Date endDate, int quantity,
				com.liferay.osb.koroneiki.trunk.model.ProductFieldSoap[]
					productFields)
		throws RemoteException {

		try {
			com.liferay.osb.koroneiki.trunk.model.ProductPurchase returnValue =
				ProductPurchaseServiceUtil.addProductPurchase(
					accountId, productEntryId, startDate, endDate, quantity,
					com.liferay.osb.koroneiki.trunk.model.impl.
						ProductFieldModelImpl.toModels(productFields));

			return com.liferay.osb.koroneiki.trunk.model.ProductPurchaseSoap.
				toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.osb.koroneiki.trunk.model.ProductPurchaseSoap
			addProductPurchase(
				String accountKey, String productEntryKey,
				java.util.Date startDate, java.util.Date endDate, int quantity,
				com.liferay.osb.koroneiki.trunk.model.ProductFieldSoap[]
					productFields)
		throws RemoteException {

		try {
			com.liferay.osb.koroneiki.trunk.model.ProductPurchase returnValue =
				ProductPurchaseServiceUtil.addProductPurchase(
					accountKey, productEntryKey, startDate, endDate, quantity,
					com.liferay.osb.koroneiki.trunk.model.impl.
						ProductFieldModelImpl.toModels(productFields));

			return com.liferay.osb.koroneiki.trunk.model.ProductPurchaseSoap.
				toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.osb.koroneiki.trunk.model.ProductPurchaseSoap
			deleteProductPurchase(long productPurchaseId)
		throws RemoteException {

		try {
			com.liferay.osb.koroneiki.trunk.model.ProductPurchase returnValue =
				ProductPurchaseServiceUtil.deleteProductPurchase(
					productPurchaseId);

			return com.liferay.osb.koroneiki.trunk.model.ProductPurchaseSoap.
				toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.osb.koroneiki.trunk.model.ProductPurchaseSoap
			deleteProductPurchase(String productPurchaseKey)
		throws RemoteException {

		try {
			com.liferay.osb.koroneiki.trunk.model.ProductPurchase returnValue =
				ProductPurchaseServiceUtil.deleteProductPurchase(
					productPurchaseKey);

			return com.liferay.osb.koroneiki.trunk.model.ProductPurchaseSoap.
				toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.osb.koroneiki.trunk.model.ProductPurchaseSoap[]
			getAccountProductPurchases(long accountId, int start, int end)
		throws RemoteException {

		try {
			java.util.List
				<com.liferay.osb.koroneiki.trunk.model.ProductPurchase>
					returnValue =
						ProductPurchaseServiceUtil.getAccountProductPurchases(
							accountId, start, end);

			return com.liferay.osb.koroneiki.trunk.model.ProductPurchaseSoap.
				toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.osb.koroneiki.trunk.model.ProductPurchaseSoap[]
			getAccountProductPurchases(String accountKey, int start, int end)
		throws RemoteException {

		try {
			java.util.List
				<com.liferay.osb.koroneiki.trunk.model.ProductPurchase>
					returnValue =
						ProductPurchaseServiceUtil.getAccountProductPurchases(
							accountKey, start, end);

			return com.liferay.osb.koroneiki.trunk.model.ProductPurchaseSoap.
				toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getAccountProductPurchasesCount(long accountId)
		throws RemoteException {

		try {
			int returnValue =
				ProductPurchaseServiceUtil.getAccountProductPurchasesCount(
					accountId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getAccountProductPurchasesCount(String accountKey)
		throws RemoteException {

		try {
			int returnValue =
				ProductPurchaseServiceUtil.getAccountProductPurchasesCount(
					accountKey);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.osb.koroneiki.trunk.model.ProductPurchaseSoap
			getProductPurchase(long productPurchaseId)
		throws RemoteException {

		try {
			com.liferay.osb.koroneiki.trunk.model.ProductPurchase returnValue =
				ProductPurchaseServiceUtil.getProductPurchase(
					productPurchaseId);

			return com.liferay.osb.koroneiki.trunk.model.ProductPurchaseSoap.
				toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.osb.koroneiki.trunk.model.ProductPurchaseSoap
			getProductPurchase(String productPurchaseKey)
		throws RemoteException {

		try {
			com.liferay.osb.koroneiki.trunk.model.ProductPurchase returnValue =
				ProductPurchaseServiceUtil.getProductPurchase(
					productPurchaseKey);

			return com.liferay.osb.koroneiki.trunk.model.ProductPurchaseSoap.
				toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.osb.koroneiki.trunk.model.ProductPurchaseSoap
			updateProductPurchase(
				long productPurchaseId, java.util.Date startDate,
				java.util.Date endDate, int quantity,
				com.liferay.osb.koroneiki.trunk.model.ProductFieldSoap[]
					productFields)
		throws RemoteException {

		try {
			com.liferay.osb.koroneiki.trunk.model.ProductPurchase returnValue =
				ProductPurchaseServiceUtil.updateProductPurchase(
					productPurchaseId, startDate, endDate, quantity,
					com.liferay.osb.koroneiki.trunk.model.impl.
						ProductFieldModelImpl.toModels(productFields));

			return com.liferay.osb.koroneiki.trunk.model.ProductPurchaseSoap.
				toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		ProductPurchaseServiceSoap.class);

}