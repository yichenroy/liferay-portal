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

package com.liferay.commerce.discount.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.discount.service.CommerceDiscountUserSegmentRelServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * {@link CommerceDiscountUserSegmentRelServiceUtil} service utility. The
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
 * @see CommerceDiscountUserSegmentRelServiceSoap
 * @see HttpPrincipal
 * @see CommerceDiscountUserSegmentRelServiceUtil
 * @generated
 */
@ProviderType
public class CommerceDiscountUserSegmentRelServiceHttp {
	public static com.liferay.commerce.discount.model.CommerceDiscountUserSegmentRel addCommerceDiscountUserSegmentRel(
		HttpPrincipal httpPrincipal, long commerceDiscountId,
		long commerceUserSegmentEntryId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceDiscountUserSegmentRelServiceUtil.class,
					"addCommerceDiscountUserSegmentRel",
					_addCommerceDiscountUserSegmentRelParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceDiscountId, commerceUserSegmentEntryId,
					serviceContext);

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

			return (com.liferay.commerce.discount.model.CommerceDiscountUserSegmentRel)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteCommerceDiscountUserSegmentRel(
		HttpPrincipal httpPrincipal, long commerceDiscountUserSegmentRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceDiscountUserSegmentRelServiceUtil.class,
					"deleteCommerceDiscountUserSegmentRel",
					_deleteCommerceDiscountUserSegmentRelParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceDiscountUserSegmentRelId);

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

	public static java.util.List<com.liferay.commerce.discount.model.CommerceDiscountUserSegmentRel> getCommerceDiscountUserSegmentRels(
		HttpPrincipal httpPrincipal, long commerceDiscountId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.discount.model.CommerceDiscountUserSegmentRel> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		try {
			MethodKey methodKey = new MethodKey(CommerceDiscountUserSegmentRelServiceUtil.class,
					"getCommerceDiscountUserSegmentRels",
					_getCommerceDiscountUserSegmentRelsParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					commerceDiscountId, start, end, orderByComparator);

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

			return (java.util.List<com.liferay.commerce.discount.model.CommerceDiscountUserSegmentRel>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CommerceDiscountUserSegmentRelServiceHttp.class);
	private static final Class<?>[] _addCommerceDiscountUserSegmentRelParameterTypes0 =
		new Class[] {
			long.class, long.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteCommerceDiscountUserSegmentRelParameterTypes1 =
		new Class[] { long.class };
	private static final Class<?>[] _getCommerceDiscountUserSegmentRelsParameterTypes2 =
		new Class[] {
			long.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
}