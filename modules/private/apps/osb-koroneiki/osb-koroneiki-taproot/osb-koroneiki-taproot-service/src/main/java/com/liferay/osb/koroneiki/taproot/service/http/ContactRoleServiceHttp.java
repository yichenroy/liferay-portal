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

package com.liferay.osb.koroneiki.taproot.service.http;

import com.liferay.osb.koroneiki.taproot.service.ContactRoleServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>ContactRoleServiceUtil</code> service
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
 * @see ContactRoleServiceSoap
 * @generated
 */
public class ContactRoleServiceHttp {

	public static com.liferay.osb.koroneiki.taproot.model.ContactRole
			addContactRole(
				HttpPrincipal httpPrincipal, String name, String description,
				int type)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ContactRoleServiceUtil.class, "addContactRole",
				_addContactRoleParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, name, description, type);

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

			return (com.liferay.osb.koroneiki.taproot.model.ContactRole)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.osb.koroneiki.taproot.model.ContactRole
			deleteContactRole(HttpPrincipal httpPrincipal, long contactRoleId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ContactRoleServiceUtil.class, "deleteContactRole",
				_deleteContactRoleParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, contactRoleId);

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

			return (com.liferay.osb.koroneiki.taproot.model.ContactRole)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.osb.koroneiki.taproot.model.ContactRole
			deleteContactRole(
				HttpPrincipal httpPrincipal, String contactRoleKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ContactRoleServiceUtil.class, "deleteContactRole",
				_deleteContactRoleParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, contactRoleKey);

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

			return (com.liferay.osb.koroneiki.taproot.model.ContactRole)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List
		<com.liferay.osb.koroneiki.taproot.model.ContactRole>
				getContactAccountContactRoles(
					HttpPrincipal httpPrincipal, long accountId, long contactId,
					int start, int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ContactRoleServiceUtil.class, "getContactAccountContactRoles",
				_getContactAccountContactRolesParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, accountId, contactId, start, end);

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
				<com.liferay.osb.koroneiki.taproot.model.ContactRole>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getContactAccountContactRolesCount(
			HttpPrincipal httpPrincipal, long accountId, long contactId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ContactRoleServiceUtil.class,
				"getContactAccountContactRolesCount",
				_getContactAccountContactRolesCountParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, accountId, contactId);

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

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List
		<com.liferay.osb.koroneiki.taproot.model.ContactRole>
				getContactContactRoles(
					HttpPrincipal httpPrincipal, long contactId, int start,
					int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ContactRoleServiceUtil.class, "getContactContactRoles",
				_getContactContactRolesParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, contactId, start, end);

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
				<com.liferay.osb.koroneiki.taproot.model.ContactRole>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.osb.koroneiki.taproot.model.ContactRole
			getContactRole(HttpPrincipal httpPrincipal, long contactRoleId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ContactRoleServiceUtil.class, "getContactRole",
				_getContactRoleParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, contactRoleId);

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

			return (com.liferay.osb.koroneiki.taproot.model.ContactRole)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.osb.koroneiki.taproot.model.ContactRole
			getContactRole(HttpPrincipal httpPrincipal, String contactRoleKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ContactRoleServiceUtil.class, "getContactRole",
				_getContactRoleParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, contactRoleKey);

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

			return (com.liferay.osb.koroneiki.taproot.model.ContactRole)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.osb.koroneiki.taproot.model.ContactRole
			updateContactRole(
				HttpPrincipal httpPrincipal, long contactRoleId, String name,
				String description)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ContactRoleServiceUtil.class, "updateContactRole",
				_updateContactRoleParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, contactRoleId, name, description);

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

			return (com.liferay.osb.koroneiki.taproot.model.ContactRole)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		ContactRoleServiceHttp.class);

	private static final Class<?>[] _addContactRoleParameterTypes0 =
		new Class[] {String.class, String.class, int.class};
	private static final Class<?>[] _deleteContactRoleParameterTypes1 =
		new Class[] {long.class};
	private static final Class<?>[] _deleteContactRoleParameterTypes2 =
		new Class[] {String.class};
	private static final Class<?>[]
		_getContactAccountContactRolesParameterTypes3 = new Class[] {
			long.class, long.class, int.class, int.class
		};
	private static final Class<?>[]
		_getContactAccountContactRolesCountParameterTypes4 = new Class[] {
			long.class, long.class
		};
	private static final Class<?>[] _getContactContactRolesParameterTypes5 =
		new Class[] {long.class, int.class, int.class};
	private static final Class<?>[] _getContactRoleParameterTypes6 =
		new Class[] {long.class};
	private static final Class<?>[] _getContactRoleParameterTypes7 =
		new Class[] {String.class};
	private static final Class<?>[] _updateContactRoleParameterTypes8 =
		new Class[] {long.class, String.class, String.class};

}