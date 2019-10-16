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

package com.liferay.commerce.cart.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.cart.service.CommerceCartServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * {@link CommerceCartServiceUtil} service utility. The
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
 * @see CommerceCartServiceSoap
 * @see HttpPrincipal
 * @see CommerceCartServiceUtil
 * @generated
 */
@ProviderType
public class CommerceCartServiceHttp {
	public static com.liferay.commerce.cart.model.CommerceCart addCommerceCart(
		HttpPrincipal httpPrincipal, java.lang.String name, int type,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceCartServiceUtil.class,
					"addCommerceCart", _addCommerceCartParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey, name,
					type, serviceContext);

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

			return (com.liferay.commerce.cart.model.CommerceCart)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteCommerceCart(HttpPrincipal httpPrincipal,
		long commerceCartId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceCartServiceUtil.class,
					"deleteCommerceCart", _deleteCommerceCartParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceCartId);

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

	public static com.liferay.commerce.cart.model.CommerceCart fetchCommerceCart(
		HttpPrincipal httpPrincipal, long commerceCartId) {
		try {
			MethodKey methodKey = new MethodKey(CommerceCartServiceUtil.class,
					"fetchCommerceCart", _fetchCommerceCartParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceCartId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.commerce.cart.model.CommerceCart)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.commerce.cart.model.CommerceCart getCommerceCart(
		HttpPrincipal httpPrincipal, long commerceCartId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceCartServiceUtil.class,
					"getCommerceCart", _getCommerceCartParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceCartId);

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

			return (com.liferay.commerce.cart.model.CommerceCart)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.commerce.cart.model.CommerceCart> getCommerceCarts(
		HttpPrincipal httpPrincipal, long groupId, int type, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.cart.model.CommerceCart> orderByComparator) {
		try {
			MethodKey methodKey = new MethodKey(CommerceCartServiceUtil.class,
					"getCommerceCarts", _getCommerceCartsParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					type, start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.commerce.cart.model.CommerceCart>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getCommerceCartsCount(HttpPrincipal httpPrincipal,
		long groupId, int type) {
		try {
			MethodKey methodKey = new MethodKey(CommerceCartServiceUtil.class,
					"getCommerceCartsCount",
					_getCommerceCartsCountParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(methodKey, groupId,
					type);

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

	private static Log _log = LogFactoryUtil.getLog(CommerceCartServiceHttp.class);
	private static final Class<?>[] _addCommerceCartParameterTypes0 = new Class[] {
			java.lang.String.class, int.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteCommerceCartParameterTypes1 = new Class[] {
			long.class
		};
	private static final Class<?>[] _fetchCommerceCartParameterTypes2 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getCommerceCartParameterTypes3 = new Class[] {
			long.class
		};
	private static final Class<?>[] _getCommerceCartsParameterTypes4 = new Class[] {
			long.class, int.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getCommerceCartsCountParameterTypes5 = new Class[] {
			long.class, int.class
		};
}