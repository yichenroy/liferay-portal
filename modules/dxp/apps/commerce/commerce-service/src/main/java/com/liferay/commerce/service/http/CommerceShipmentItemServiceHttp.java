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

import com.liferay.commerce.service.CommerceShipmentItemServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * {@link CommerceShipmentItemServiceUtil} service utility. The
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
 * @author Alessio Antonio Rendina
 * @see CommerceShipmentItemServiceSoap
 * @see HttpPrincipal
 * @see CommerceShipmentItemServiceUtil
 * @generated
 */
@ProviderType
public class CommerceShipmentItemServiceHttp {
	public static com.liferay.commerce.model.CommerceShipmentItem addCommerceShipmentItem(
		HttpPrincipal httpPrincipal, long commerceShipmentId,
		long commerceOrderItemId, long commerceWarehouseId, int quantity,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceShipmentItemServiceUtil.class,
					"addCommerceShipmentItem",
					_addCommerceShipmentItemParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceShipmentId, commerceOrderItemId,
					commerceWarehouseId, quantity, serviceContext);

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

			return (com.liferay.commerce.model.CommerceShipmentItem)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteCommerceShipmentItem(HttpPrincipal httpPrincipal,
		long commerceShipmentItemId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceShipmentItemServiceUtil.class,
					"deleteCommerceShipmentItem",
					_deleteCommerceShipmentItemParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceShipmentItemId);

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

	public static com.liferay.commerce.model.CommerceShipmentItem fetchCommerceShipmentItem(
		HttpPrincipal httpPrincipal, long commerceShipmentItemId) {
		try {
			MethodKey methodKey = new MethodKey(CommerceShipmentItemServiceUtil.class,
					"fetchCommerceShipmentItem",
					_fetchCommerceShipmentItemParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceShipmentItemId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.commerce.model.CommerceShipmentItem)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.commerce.model.CommerceShipmentItem> getCommerceShipmentItems(
		HttpPrincipal httpPrincipal, long commerceOrderItemId) {
		try {
			MethodKey methodKey = new MethodKey(CommerceShipmentItemServiceUtil.class,
					"getCommerceShipmentItems",
					_getCommerceShipmentItemsParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceOrderItemId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.commerce.model.CommerceShipmentItem>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.commerce.model.CommerceShipmentItem> getCommerceShipmentItems(
		HttpPrincipal httpPrincipal, long commerceShipmentId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceShipmentItem> orderByComparator) {
		try {
			MethodKey methodKey = new MethodKey(CommerceShipmentItemServiceUtil.class,
					"getCommerceShipmentItems",
					_getCommerceShipmentItemsParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceShipmentId, start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.commerce.model.CommerceShipmentItem>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getCommerceShipmentItemsCount(
		HttpPrincipal httpPrincipal, long commerceShipmentId) {
		try {
			MethodKey methodKey = new MethodKey(CommerceShipmentItemServiceUtil.class,
					"getCommerceShipmentItemsCount",
					_getCommerceShipmentItemsCountParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceShipmentId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.model.CommerceShipmentItem updateCommerceShipmentItem(
		HttpPrincipal httpPrincipal, long commerceShipmentItemId, int quantity)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceShipmentItemServiceUtil.class,
					"updateCommerceShipmentItem",
					_updateCommerceShipmentItemParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceShipmentItemId, quantity);

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

			return (com.liferay.commerce.model.CommerceShipmentItem)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CommerceShipmentItemServiceHttp.class);
	private static final Class<?>[] _addCommerceShipmentItemParameterTypes0 = new Class[] {
			long.class, long.class, long.class, int.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteCommerceShipmentItemParameterTypes1 = new Class[] {
			long.class
		};
	private static final Class<?>[] _fetchCommerceShipmentItemParameterTypes2 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getCommerceShipmentItemsParameterTypes3 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getCommerceShipmentItemsParameterTypes4 = new Class[] {
			long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getCommerceShipmentItemsCountParameterTypes5 =
		new Class[] { long.class };
	private static final Class<?>[] _updateCommerceShipmentItemParameterTypes6 = new Class[] {
			long.class, int.class
		};
}