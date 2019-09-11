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

package com.liferay.osb.koroneiki.scion.service.http;

import com.liferay.osb.koroneiki.scion.service.AuthenticationTokenServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the HTTP utility for the
 * <code>AuthenticationTokenServiceUtil</code> service
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
 * @author Brian Wing Shun Chan
 * @see AuthenticationTokenServiceSoap
 * @generated
 */
@ProviderType
public class AuthenticationTokenServiceHttp {

	public static com.liferay.osb.koroneiki.scion.model.AuthenticationToken
			addAuthenticationToken(
				HttpPrincipal httpPrincipal, long serviceProducerId,
				String name, String token)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AuthenticationTokenServiceUtil.class, "addAuthenticationToken",
				_addAuthenticationTokenParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, serviceProducerId, name, token);

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

			return (com.liferay.osb.koroneiki.scion.model.AuthenticationToken)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.osb.koroneiki.scion.model.AuthenticationToken
			deleteAuthenticationToken(
				HttpPrincipal httpPrincipal, long authenticationTokenId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AuthenticationTokenServiceUtil.class,
				"deleteAuthenticationToken",
				_deleteAuthenticationTokenParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, authenticationTokenId);

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

			return (com.liferay.osb.koroneiki.scion.model.AuthenticationToken)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.osb.koroneiki.scion.model.AuthenticationToken
			updateAuthenticationToken(
				HttpPrincipal httpPrincipal, long authenticationTokenId,
				String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AuthenticationTokenServiceUtil.class,
				"updateAuthenticationToken",
				_updateAuthenticationTokenParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, authenticationTokenId, name);

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

			return (com.liferay.osb.koroneiki.scion.model.AuthenticationToken)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.osb.koroneiki.scion.model.AuthenticationToken
			updateStatus(
				HttpPrincipal httpPrincipal, long authenticationTokenId,
				int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				AuthenticationTokenServiceUtil.class, "updateStatus",
				_updateStatusParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, authenticationTokenId, status);

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

			return (com.liferay.osb.koroneiki.scion.model.AuthenticationToken)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		AuthenticationTokenServiceHttp.class);

	private static final Class<?>[] _addAuthenticationTokenParameterTypes0 =
		new Class[] {long.class, String.class, String.class};
	private static final Class<?>[] _deleteAuthenticationTokenParameterTypes1 =
		new Class[] {long.class};
	private static final Class<?>[] _updateAuthenticationTokenParameterTypes2 =
		new Class[] {long.class, String.class};
	private static final Class<?>[] _updateStatusParameterTypes3 = new Class[] {
		long.class, int.class
	};

}