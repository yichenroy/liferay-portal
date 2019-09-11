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

import com.liferay.osb.koroneiki.trunk.service.ProductConsumptionServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the SOAP utility for the
 * <code>ProductConsumptionServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.osb.koroneiki.trunk.model.ProductConsumptionSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.osb.koroneiki.trunk.model.ProductConsumption</code>, that is translated to a
 * <code>com.liferay.osb.koroneiki.trunk.model.ProductConsumptionSoap</code>. Methods that SOAP
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
 * @see ProductConsumptionServiceHttp
 * @generated
 */
@ProviderType
public class ProductConsumptionServiceSoap {

	public static com.liferay.osb.koroneiki.trunk.model.ProductConsumptionSoap
			addProductConsumption(
				long accountId, long productEntryId,
				com.liferay.osb.koroneiki.trunk.model.ProductFieldSoap[]
					productFields)
		throws RemoteException {

		try {
			com.liferay.osb.koroneiki.trunk.model.ProductConsumption
				returnValue =
					ProductConsumptionServiceUtil.addProductConsumption(
						accountId, productEntryId,
						com.liferay.osb.koroneiki.trunk.model.impl.
							ProductFieldModelImpl.toModels(productFields));

			return com.liferay.osb.koroneiki.trunk.model.ProductConsumptionSoap.
				toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.osb.koroneiki.trunk.model.ProductConsumptionSoap
			addProductConsumption(
				String accountKey, String productEntryKey,
				com.liferay.osb.koroneiki.trunk.model.ProductFieldSoap[]
					productFields)
		throws RemoteException {

		try {
			com.liferay.osb.koroneiki.trunk.model.ProductConsumption
				returnValue =
					ProductConsumptionServiceUtil.addProductConsumption(
						accountKey, productEntryKey,
						com.liferay.osb.koroneiki.trunk.model.impl.
							ProductFieldModelImpl.toModels(productFields));

			return com.liferay.osb.koroneiki.trunk.model.ProductConsumptionSoap.
				toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.osb.koroneiki.trunk.model.ProductConsumptionSoap
			deleteProductConsumption(long productConsumptionId)
		throws RemoteException {

		try {
			com.liferay.osb.koroneiki.trunk.model.ProductConsumption
				returnValue =
					ProductConsumptionServiceUtil.deleteProductConsumption(
						productConsumptionId);

			return com.liferay.osb.koroneiki.trunk.model.ProductConsumptionSoap.
				toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.osb.koroneiki.trunk.model.ProductConsumptionSoap
			deleteProductConsumption(long accountId, long productEntryId)
		throws RemoteException {

		try {
			com.liferay.osb.koroneiki.trunk.model.ProductConsumption
				returnValue =
					ProductConsumptionServiceUtil.deleteProductConsumption(
						accountId, productEntryId);

			return com.liferay.osb.koroneiki.trunk.model.ProductConsumptionSoap.
				toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.osb.koroneiki.trunk.model.ProductConsumptionSoap
			deleteProductConsumption(String productConsumptionKey)
		throws RemoteException {

		try {
			com.liferay.osb.koroneiki.trunk.model.ProductConsumption
				returnValue =
					ProductConsumptionServiceUtil.deleteProductConsumption(
						productConsumptionKey);

			return com.liferay.osb.koroneiki.trunk.model.ProductConsumptionSoap.
				toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.osb.koroneiki.trunk.model.ProductConsumptionSoap[]
			getAccountProductConsumptions(long accountId, int start, int end)
		throws RemoteException {

		try {
			java.util.List
				<com.liferay.osb.koroneiki.trunk.model.ProductConsumption>
					returnValue =
						ProductConsumptionServiceUtil.
							getAccountProductConsumptions(
								accountId, start, end);

			return com.liferay.osb.koroneiki.trunk.model.ProductConsumptionSoap.
				toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.osb.koroneiki.trunk.model.ProductConsumptionSoap[]
			getAccountProductConsumptions(String accountKey, int start, int end)
		throws RemoteException {

		try {
			java.util.List
				<com.liferay.osb.koroneiki.trunk.model.ProductConsumption>
					returnValue =
						ProductConsumptionServiceUtil.
							getAccountProductConsumptions(
								accountKey, start, end);

			return com.liferay.osb.koroneiki.trunk.model.ProductConsumptionSoap.
				toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getAccountProductConsumptionsCount(long accountId)
		throws RemoteException {

		try {
			int returnValue =
				ProductConsumptionServiceUtil.
					getAccountProductConsumptionsCount(accountId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getAccountProductConsumptionsCount(String accountKey)
		throws RemoteException {

		try {
			int returnValue =
				ProductConsumptionServiceUtil.
					getAccountProductConsumptionsCount(accountKey);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.osb.koroneiki.trunk.model.ProductConsumptionSoap
			getProductConsumption(long productConsumptionId)
		throws RemoteException {

		try {
			com.liferay.osb.koroneiki.trunk.model.ProductConsumption
				returnValue =
					ProductConsumptionServiceUtil.getProductConsumption(
						productConsumptionId);

			return com.liferay.osb.koroneiki.trunk.model.ProductConsumptionSoap.
				toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.osb.koroneiki.trunk.model.ProductConsumptionSoap
			getProductConsumption(String productConsumptionKey)
		throws RemoteException {

		try {
			com.liferay.osb.koroneiki.trunk.model.ProductConsumption
				returnValue =
					ProductConsumptionServiceUtil.getProductConsumption(
						productConsumptionKey);

			return com.liferay.osb.koroneiki.trunk.model.ProductConsumptionSoap.
				toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		ProductConsumptionServiceSoap.class);

}