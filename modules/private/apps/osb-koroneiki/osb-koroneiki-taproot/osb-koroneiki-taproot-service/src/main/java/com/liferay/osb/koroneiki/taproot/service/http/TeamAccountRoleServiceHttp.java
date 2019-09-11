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

import com.liferay.osb.koroneiki.taproot.service.TeamAccountRoleServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>TeamAccountRoleServiceUtil</code> service
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
 * @see TeamAccountRoleServiceSoap
 * @generated
 */
public class TeamAccountRoleServiceHttp {

	public static com.liferay.osb.koroneiki.taproot.model.TeamAccountRole
			addTeamAccountRole(
				HttpPrincipal httpPrincipal, long teamId, long accountId,
				long teamRoleId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				TeamAccountRoleServiceUtil.class, "addTeamAccountRole",
				_addTeamAccountRoleParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, teamId, accountId, teamRoleId);

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

			return (com.liferay.osb.koroneiki.taproot.model.TeamAccountRole)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.osb.koroneiki.taproot.model.TeamAccountRole
			addTeamAccountRole(
				HttpPrincipal httpPrincipal, String teamKey, String accountKey,
				String teamRoleKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				TeamAccountRoleServiceUtil.class, "addTeamAccountRole",
				_addTeamAccountRoleParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, teamKey, accountKey, teamRoleKey);

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

			return (com.liferay.osb.koroneiki.taproot.model.TeamAccountRole)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.osb.koroneiki.taproot.model.TeamAccountRole
			deleteTeamAccountRole(
				HttpPrincipal httpPrincipal, long teamId, long accountId,
				long teamRoleId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				TeamAccountRoleServiceUtil.class, "deleteTeamAccountRole",
				_deleteTeamAccountRoleParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, teamId, accountId, teamRoleId);

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

			return (com.liferay.osb.koroneiki.taproot.model.TeamAccountRole)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.osb.koroneiki.taproot.model.TeamAccountRole
			deleteTeamAccountRole(
				HttpPrincipal httpPrincipal, String teamKey, String accountKey,
				String teamRoleKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				TeamAccountRoleServiceUtil.class, "deleteTeamAccountRole",
				_deleteTeamAccountRoleParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, teamKey, accountKey, teamRoleKey);

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

			return (com.liferay.osb.koroneiki.taproot.model.TeamAccountRole)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteTeamAccountRoles(
			HttpPrincipal httpPrincipal, long teamId, long accountId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				TeamAccountRoleServiceUtil.class, "deleteTeamAccountRoles",
				_deleteTeamAccountRolesParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, teamId, accountId);

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

	private static Log _log = LogFactoryUtil.getLog(
		TeamAccountRoleServiceHttp.class);

	private static final Class<?>[] _addTeamAccountRoleParameterTypes0 =
		new Class[] {long.class, long.class, long.class};
	private static final Class<?>[] _addTeamAccountRoleParameterTypes1 =
		new Class[] {String.class, String.class, String.class};
	private static final Class<?>[] _deleteTeamAccountRoleParameterTypes2 =
		new Class[] {long.class, long.class, long.class};
	private static final Class<?>[] _deleteTeamAccountRoleParameterTypes3 =
		new Class[] {String.class, String.class, String.class};
	private static final Class<?>[] _deleteTeamAccountRolesParameterTypes4 =
		new Class[] {long.class, long.class};

}