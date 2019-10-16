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

package com.liferay.portal.resiliency.spi.service.http;

import org.osgi.annotation.versioning.ProviderType;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.resiliency.spi.service.SPIDefinitionServiceUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>SPIDefinitionServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.portal.resiliency.spi.model.SPIDefinitionSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.portal.resiliency.spi.model.SPIDefinition</code>, that is translated to a
 * <code>com.liferay.portal.resiliency.spi.model.SPIDefinitionSoap</code>. Methods that SOAP
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
 * @author Michael C. Han
 * @see SPIDefinitionServiceHttp
 * @generated
 */
@ProviderType
public class SPIDefinitionServiceSoap {

	public static com.liferay.portal.resiliency.spi.model.SPIDefinitionSoap
			addSPIDefinition(
				String name, String connectorAddress, int connectorPort,
				String description, String jvmArguments, String portletIds,
				String servletContextNames, String typeSettings,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.portal.resiliency.spi.model.SPIDefinition returnValue =
				SPIDefinitionServiceUtil.addSPIDefinition(
					name, connectorAddress, connectorPort, description,
					jvmArguments, portletIds, servletContextNames, typeSettings,
					serviceContext);

			return com.liferay.portal.resiliency.spi.model.SPIDefinitionSoap.
				toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.resiliency.spi.model.SPIDefinitionSoap
			deleteSPIDefinition(long spiDefinitionId)
		throws RemoteException {

		try {
			com.liferay.portal.resiliency.spi.model.SPIDefinition returnValue =
				SPIDefinitionServiceUtil.deleteSPIDefinition(spiDefinitionId);

			return com.liferay.portal.resiliency.spi.model.SPIDefinitionSoap.
				toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.kernel.util.Tuple
			getPortletIdsAndServletContextNames()
		throws RemoteException {

		try {
			com.liferay.portal.kernel.util.Tuple returnValue =
				SPIDefinitionServiceUtil.getPortletIdsAndServletContextNames();

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.resiliency.spi.model.SPIDefinitionSoap
			getSPIDefinition(long spiDefinitionId)
		throws RemoteException {

		try {
			com.liferay.portal.resiliency.spi.model.SPIDefinition returnValue =
				SPIDefinitionServiceUtil.getSPIDefinition(spiDefinitionId);

			return com.liferay.portal.resiliency.spi.model.SPIDefinitionSoap.
				toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.resiliency.spi.model.SPIDefinitionSoap
			getSPIDefinition(long companyId, String name)
		throws RemoteException {

		try {
			com.liferay.portal.resiliency.spi.model.SPIDefinition returnValue =
				SPIDefinitionServiceUtil.getSPIDefinition(companyId, name);

			return com.liferay.portal.resiliency.spi.model.SPIDefinitionSoap.
				toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.resiliency.spi.model.SPIDefinitionSoap[]
			getSPIDefinitions()
		throws RemoteException {

		try {
			java.util.List
				<com.liferay.portal.resiliency.spi.model.SPIDefinition>
					returnValue = SPIDefinitionServiceUtil.getSPIDefinitions();

			return com.liferay.portal.resiliency.spi.model.SPIDefinitionSoap.
				toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void startSPI(long spiDefinitionId) throws RemoteException {
		try {
			SPIDefinitionServiceUtil.startSPI(spiDefinitionId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static long startSPIinBackground(long spiDefinitionId)
		throws RemoteException {

		try {
			long returnValue = SPIDefinitionServiceUtil.startSPIinBackground(
				spiDefinitionId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void stopSPI(long spiDefinitionId) throws RemoteException {
		try {
			SPIDefinitionServiceUtil.stopSPI(spiDefinitionId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static long stopSPIinBackground(long spiDefinitionId)
		throws RemoteException {

		try {
			long returnValue = SPIDefinitionServiceUtil.stopSPIinBackground(
				spiDefinitionId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.resiliency.spi.model.SPIDefinitionSoap
			updateSPIDefinition(
				long spiDefinitionId, String connectorAddress,
				int connectorPort, String description, String jvmArguments,
				String portletIds, String servletContextNames,
				String typeSettings,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.portal.resiliency.spi.model.SPIDefinition returnValue =
				SPIDefinitionServiceUtil.updateSPIDefinition(
					spiDefinitionId, connectorAddress, connectorPort,
					description, jvmArguments, portletIds, servletContextNames,
					typeSettings, serviceContext);

			return com.liferay.portal.resiliency.spi.model.SPIDefinitionSoap.
				toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.resiliency.spi.model.SPIDefinitionSoap
			updateTypeSettings(
				long userId, long spiDefinitionId, String recoveryOptions,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.portal.resiliency.spi.model.SPIDefinition returnValue =
				SPIDefinitionServiceUtil.updateTypeSettings(
					userId, spiDefinitionId, recoveryOptions, serviceContext);

			return com.liferay.portal.resiliency.spi.model.SPIDefinitionSoap.
				toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		SPIDefinitionServiceSoap.class);

}