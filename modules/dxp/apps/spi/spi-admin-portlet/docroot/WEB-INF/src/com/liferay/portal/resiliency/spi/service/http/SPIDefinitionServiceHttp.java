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
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.resiliency.spi.service.SPIDefinitionServiceUtil;

/**
 * Provides the HTTP utility for the
 * <code>SPIDefinitionServiceUtil</code> service
 * utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it requires an additional
 * <code>HttpPrincipal</code> parameter.
 *
 * <p>
 * The benefits of using the HTTP utility is that it is fast and allows for
 * tunneling without the cost of serializing to text. The drawback is that it
 * only works with Java.
 * </p>
 *
 * <p>
 * Set the property <b>tunnel.servlet.hosts.allowed</b> in portal.properties to
 * configure security.
 * </p>
 *
 * <p>
 * The HTTP utility is only generated for remote services.
 * </p>
 *
 * @author Michael C. Han
 * @see SPIDefinitionServiceSoap
 * @generated
 */
@ProviderType
public class SPIDefinitionServiceHttp {

	public static com.liferay.portal.resiliency.spi.model.SPIDefinition
			addSPIDefinition(
				HttpPrincipal httpPrincipal, String name,
				String connectorAddress, int connectorPort, String description,
				String jvmArguments, String portletIds,
				String servletContextNames, String typeSettings,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SPIDefinitionServiceUtil.class, "addSPIDefinition",
				_addSPIDefinitionParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, name, connectorAddress, connectorPort, description,
				jvmArguments, portletIds, servletContextNames, typeSettings,
				serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.portal.resiliency.spi.model.SPIDefinition)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.resiliency.spi.model.SPIDefinition
			deleteSPIDefinition(
				HttpPrincipal httpPrincipal, long spiDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SPIDefinitionServiceUtil.class, "deleteSPIDefinition",
				_deleteSPIDefinitionParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, spiDefinitionId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.portal.resiliency.spi.model.SPIDefinition)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.util.Tuple
			getPortletIdsAndServletContextNames(HttpPrincipal httpPrincipal)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SPIDefinitionServiceUtil.class,
				"getPortletIdsAndServletContextNames",
				_getPortletIdsAndServletContextNamesParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.portal.kernel.util.Tuple)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.resiliency.spi.model.SPIDefinition
			getSPIDefinition(HttpPrincipal httpPrincipal, long spiDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SPIDefinitionServiceUtil.class, "getSPIDefinition",
				_getSPIDefinitionParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, spiDefinitionId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.portal.resiliency.spi.model.SPIDefinition)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.resiliency.spi.model.SPIDefinition
			getSPIDefinition(
				HttpPrincipal httpPrincipal, long companyId, String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SPIDefinitionServiceUtil.class, "getSPIDefinition",
				_getSPIDefinitionParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, name);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.portal.resiliency.spi.model.SPIDefinition)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List
		<com.liferay.portal.resiliency.spi.model.SPIDefinition>
				getSPIDefinitions(HttpPrincipal httpPrincipal)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SPIDefinitionServiceUtil.class, "getSPIDefinitions",
				_getSPIDefinitionsParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List
				<com.liferay.portal.resiliency.spi.model.SPIDefinition>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void startSPI(
			HttpPrincipal httpPrincipal, long spiDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SPIDefinitionServiceUtil.class, "startSPI",
				_startSPIParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, spiDefinitionId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static long startSPIinBackground(
			HttpPrincipal httpPrincipal, long spiDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SPIDefinitionServiceUtil.class, "startSPIinBackground",
				_startSPIinBackgroundParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, spiDefinitionId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return ((Long)returnObj).longValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void stopSPI(
			HttpPrincipal httpPrincipal, long spiDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SPIDefinitionServiceUtil.class, "stopSPI",
				_stopSPIParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, spiDefinitionId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static long stopSPIinBackground(
			HttpPrincipal httpPrincipal, long spiDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SPIDefinitionServiceUtil.class, "stopSPIinBackground",
				_stopSPIinBackgroundParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, spiDefinitionId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return ((Long)returnObj).longValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.resiliency.spi.model.SPIDefinition
			updateSPIDefinition(
				HttpPrincipal httpPrincipal, long spiDefinitionId,
				String connectorAddress, int connectorPort, String description,
				String jvmArguments, String portletIds,
				String servletContextNames, String typeSettings,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SPIDefinitionServiceUtil.class, "updateSPIDefinition",
				_updateSPIDefinitionParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, spiDefinitionId, connectorAddress, connectorPort,
				description, jvmArguments, portletIds, servletContextNames,
				typeSettings, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.portal.resiliency.spi.model.SPIDefinition)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.resiliency.spi.model.SPIDefinition
			updateTypeSettings(
				HttpPrincipal httpPrincipal, long userId, long spiDefinitionId,
				String recoveryOptions,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				SPIDefinitionServiceUtil.class, "updateTypeSettings",
				_updateTypeSettingsParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, userId, spiDefinitionId, recoveryOptions,
				serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.portal.resiliency.spi.model.SPIDefinition)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		SPIDefinitionServiceHttp.class);

	private static final Class<?>[] _addSPIDefinitionParameterTypes0 =
		new Class[] {
			String.class, String.class, int.class, String.class, String.class,
			String.class, String.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteSPIDefinitionParameterTypes1 =
		new Class[] {long.class};
	private static final Class<?>[]
		_getPortletIdsAndServletContextNamesParameterTypes2 = new Class[] {};
	private static final Class<?>[] _getSPIDefinitionParameterTypes3 =
		new Class[] {long.class};
	private static final Class<?>[] _getSPIDefinitionParameterTypes4 =
		new Class[] {long.class, String.class};
	private static final Class<?>[] _getSPIDefinitionsParameterTypes5 =
		new Class[] {};
	private static final Class<?>[] _startSPIParameterTypes6 = new Class[] {
		long.class
	};
	private static final Class<?>[] _startSPIinBackgroundParameterTypes7 =
		new Class[] {long.class};
	private static final Class<?>[] _stopSPIParameterTypes8 = new Class[] {
		long.class
	};
	private static final Class<?>[] _stopSPIinBackgroundParameterTypes9 =
		new Class[] {long.class};
	private static final Class<?>[] _updateSPIDefinitionParameterTypes10 =
		new Class[] {
			long.class, String.class, int.class, String.class, String.class,
			String.class, String.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _updateTypeSettingsParameterTypes11 =
		new Class[] {
			long.class, long.class, String.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};

}