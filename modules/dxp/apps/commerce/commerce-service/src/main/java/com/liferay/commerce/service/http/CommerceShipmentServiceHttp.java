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

import com.liferay.commerce.service.CommerceShipmentServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * {@link CommerceShipmentServiceUtil} service utility. The
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
 * @see CommerceShipmentServiceSoap
 * @see HttpPrincipal
 * @see CommerceShipmentServiceUtil
 * @generated
 */
@ProviderType
public class CommerceShipmentServiceHttp {
	public static com.liferay.commerce.model.CommerceShipment addCommerceShipment(
		HttpPrincipal httpPrincipal, long shipmentUserId,
		long commerceAddressId, long commerceShippingMethodId,
		long commerceWarehouseId, String carrier, String trackingNumber,
		int expectedDuration, int status, int shippingDateMonth,
		int shippingDateDay, int shippingDateYear, int shippingDateHour,
		int shippingDateMinute, int expectedDateMonth, int expectedDateDay,
		int expectedDateYear, int expectedDateHour, int expectedDateMinute,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceShipmentServiceUtil.class,
					"addCommerceShipment", _addCommerceShipmentParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					shipmentUserId, commerceAddressId,
					commerceShippingMethodId, commerceWarehouseId, carrier,
					trackingNumber, expectedDuration, status,
					shippingDateMonth, shippingDateDay, shippingDateYear,
					shippingDateHour, shippingDateMinute, expectedDateMonth,
					expectedDateDay, expectedDateYear, expectedDateHour,
					expectedDateMinute, serviceContext);

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

			return (com.liferay.commerce.model.CommerceShipment)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteCommerceShipment(HttpPrincipal httpPrincipal,
		long commerceShipmentId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceShipmentServiceUtil.class,
					"deleteCommerceShipment",
					_deleteCommerceShipmentParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceShipmentId);

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

	public static com.liferay.commerce.model.CommerceShipment getCommerceShipment(
		HttpPrincipal httpPrincipal, long commerceShipmentId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceShipmentServiceUtil.class,
					"getCommerceShipment", _getCommerceShipmentParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceShipmentId);

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

			return (com.liferay.commerce.model.CommerceShipment)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.commerce.model.CommerceShipment> getCommerceShipments(
		HttpPrincipal httpPrincipal, long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceShipment> orderByComparator) {
		try {
			MethodKey methodKey = new MethodKey(CommerceShipmentServiceUtil.class,
					"getCommerceShipments", _getCommerceShipmentsParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.commerce.model.CommerceShipment>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getCommerceShipmentsCount(HttpPrincipal httpPrincipal,
		long groupId) {
		try {
			MethodKey methodKey = new MethodKey(CommerceShipmentServiceUtil.class,
					"getCommerceShipmentsCount",
					_getCommerceShipmentsCountParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId);

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

	public static com.liferay.commerce.model.CommerceShipment updateCommerceShipment(
		HttpPrincipal httpPrincipal, long commerceShipmentId,
		long shipmentUserId, long commerceAddressId,
		long commerceShippingMethodId, String carrier, String trackingNumber,
		int expectedDuration, int status, int shippingDateMonth,
		int shippingDateDay, int shippingDateYear, int shippingDateHour,
		int shippingDateMinute, int expectedDateMonth, int expectedDateDay,
		int expectedDateYear, int expectedDateHour, int expectedDateMinute)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceShipmentServiceUtil.class,
					"updateCommerceShipment",
					_updateCommerceShipmentParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceShipmentId, shipmentUserId, commerceAddressId,
					commerceShippingMethodId, carrier, trackingNumber,
					expectedDuration, status, shippingDateMonth,
					shippingDateDay, shippingDateYear, shippingDateHour,
					shippingDateMinute, expectedDateMonth, expectedDateDay,
					expectedDateYear, expectedDateHour, expectedDateMinute);

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

			return (com.liferay.commerce.model.CommerceShipment)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CommerceShipmentServiceHttp.class);
	private static final Class<?>[] _addCommerceShipmentParameterTypes0 = new Class[] {
			long.class, long.class, long.class, long.class, String.class,
			String.class, int.class, int.class, int.class, int.class, int.class,
			int.class, int.class, int.class, int.class, int.class, int.class,
			int.class, com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteCommerceShipmentParameterTypes1 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getCommerceShipmentParameterTypes2 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getCommerceShipmentsParameterTypes3 = new Class[] {
			long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getCommerceShipmentsCountParameterTypes4 = new Class[] {
			long.class
		};
	private static final Class<?>[] _updateCommerceShipmentParameterTypes5 = new Class[] {
			long.class, long.class, long.class, long.class, String.class,
			String.class, int.class, int.class, int.class, int.class, int.class,
			int.class, int.class, int.class, int.class, int.class, int.class,
			int.class
		};
}