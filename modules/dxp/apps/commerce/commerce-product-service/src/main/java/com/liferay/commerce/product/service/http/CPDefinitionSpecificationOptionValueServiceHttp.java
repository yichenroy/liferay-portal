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

package com.liferay.commerce.product.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.service.CPDefinitionSpecificationOptionValueServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * {@link CPDefinitionSpecificationOptionValueServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it requires an additional
 * {@link HttpPrincipal} parameter.
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
 * @author Marco Leo
 * @see CPDefinitionSpecificationOptionValueServiceSoap
 * @see HttpPrincipal
 * @see CPDefinitionSpecificationOptionValueServiceUtil
 * @generated
 */
@ProviderType
public class CPDefinitionSpecificationOptionValueServiceHttp {
	public static com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue addCPDefinitionSpecificationOptionValue(
		HttpPrincipal httpPrincipal, long cpDefinitionId,
		long cpSpecificationOptionId, long cpOptionCategoryId,
		java.util.Map<java.util.Locale, String> valueMap, double priority,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPDefinitionSpecificationOptionValueServiceUtil.class,
					"addCPDefinitionSpecificationOptionValue",
					_addCPDefinitionSpecificationOptionValueParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpDefinitionId, cpSpecificationOptionId,
					cpOptionCategoryId, valueMap, priority, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteCPDefinitionSpecificationOptionValue(
		HttpPrincipal httpPrincipal, long cpDefinitionSpecificationOptionValueId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPDefinitionSpecificationOptionValueServiceUtil.class,
					"deleteCPDefinitionSpecificationOptionValue",
					_deleteCPDefinitionSpecificationOptionValueParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpDefinitionSpecificationOptionValueId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue fetchCPDefinitionSpecificationOptionValue(
		HttpPrincipal httpPrincipal, long cpDefinitionSpecificationOptionValueId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPDefinitionSpecificationOptionValueServiceUtil.class,
					"fetchCPDefinitionSpecificationOptionValue",
					_fetchCPDefinitionSpecificationOptionValueParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpDefinitionSpecificationOptionValueId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue fetchCPDefinitionSpecificationOptionValue(
		HttpPrincipal httpPrincipal, long cpDefinitionId,
		long cpSpecificationOptionId) {
		try {
			MethodKey methodKey = new MethodKey(CPDefinitionSpecificationOptionValueServiceUtil.class,
					"fetchCPDefinitionSpecificationOptionValue",
					_fetchCPDefinitionSpecificationOptionValueParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpDefinitionId, cpSpecificationOptionId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue getCPDefinitionSpecificationOptionValue(
		HttpPrincipal httpPrincipal, long cpDefinitionSpecificationOptionValueId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPDefinitionSpecificationOptionValueServiceUtil.class,
					"getCPDefinitionSpecificationOptionValue",
					_getCPDefinitionSpecificationOptionValueParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpDefinitionSpecificationOptionValueId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue> getCPDefinitionSpecificationOptionValues(
		HttpPrincipal httpPrincipal, long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPDefinitionSpecificationOptionValueServiceUtil.class,
					"getCPDefinitionSpecificationOptionValues",
					_getCPDefinitionSpecificationOptionValuesParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpDefinitionId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue> getCPDefinitionSpecificationOptionValues(
		HttpPrincipal httpPrincipal, long cpDefinitionId,
		long cpOptionCategoryId) {
		try {
			MethodKey methodKey = new MethodKey(CPDefinitionSpecificationOptionValueServiceUtil.class,
					"getCPDefinitionSpecificationOptionValues",
					_getCPDefinitionSpecificationOptionValuesParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpDefinitionId, cpOptionCategoryId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue updateCPDefinitionSpecificationOptionValue(
		HttpPrincipal httpPrincipal,
		long cpDefinitionSpecificationOptionValueId, long cpOptionCategoryId,
		java.util.Map<java.util.Locale, String> valueMap, double priority,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CPDefinitionSpecificationOptionValueServiceUtil.class,
					"updateCPDefinitionSpecificationOptionValue",
					_updateCPDefinitionSpecificationOptionValueParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					cpDefinitionSpecificationOptionValueId, cpOptionCategoryId,
					valueMap, priority, serviceContext);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CPDefinitionSpecificationOptionValueServiceHttp.class);
	private static final Class<?>[] _addCPDefinitionSpecificationOptionValueParameterTypes0 =
		new Class[] {
			long.class, long.class, long.class, java.util.Map.class,
			double.class, com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteCPDefinitionSpecificationOptionValueParameterTypes1 =
		new Class[] { long.class };
	private static final Class<?>[] _fetchCPDefinitionSpecificationOptionValueParameterTypes2 =
		new Class[] { long.class };
	private static final Class<?>[] _fetchCPDefinitionSpecificationOptionValueParameterTypes3 =
		new Class[] { long.class, long.class };
	private static final Class<?>[] _getCPDefinitionSpecificationOptionValueParameterTypes4 =
		new Class[] { long.class };
	private static final Class<?>[] _getCPDefinitionSpecificationOptionValuesParameterTypes5 =
		new Class[] { long.class };
	private static final Class<?>[] _getCPDefinitionSpecificationOptionValuesParameterTypes6 =
		new Class[] { long.class, long.class };
	private static final Class<?>[] _updateCPDefinitionSpecificationOptionValueParameterTypes7 =
		new Class[] {
			long.class, long.class, java.util.Map.class, double.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
}