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

import com.liferay.osb.koroneiki.taproot.service.TeamRoleServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>TeamRoleServiceUtil</code> service
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
 * @see TeamRoleServiceSoap
 * @generated
 */
public class TeamRoleServiceHttp {

	public static com.liferay.osb.koroneiki.taproot.model.TeamRole addTeamRole(
			HttpPrincipal httpPrincipal, String name, String description,
			int type)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				TeamRoleServiceUtil.class, "addTeamRole",
				_addTeamRoleParameterTypes0);

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

			return (com.liferay.osb.koroneiki.taproot.model.TeamRole)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.osb.koroneiki.taproot.model.TeamRole
			deleteTeamRole(HttpPrincipal httpPrincipal, long teamRoleId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				TeamRoleServiceUtil.class, "deleteTeamRole",
				_deleteTeamRoleParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, teamRoleId);

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

			return (com.liferay.osb.koroneiki.taproot.model.TeamRole)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.osb.koroneiki.taproot.model.TeamRole
			deleteTeamRole(HttpPrincipal httpPrincipal, String teamRoleKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				TeamRoleServiceUtil.class, "deleteTeamRole",
				_deleteTeamRoleParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, teamRoleKey);

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

			return (com.liferay.osb.koroneiki.taproot.model.TeamRole)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.osb.koroneiki.taproot.model.TeamRole getTeamRole(
			HttpPrincipal httpPrincipal, long teamRoleId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				TeamRoleServiceUtil.class, "getTeamRole",
				_getTeamRoleParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, teamRoleId);

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

			return (com.liferay.osb.koroneiki.taproot.model.TeamRole)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.osb.koroneiki.taproot.model.TeamRole getTeamRole(
			HttpPrincipal httpPrincipal, String teamRoleKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				TeamRoleServiceUtil.class, "getTeamRole",
				_getTeamRoleParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, teamRoleKey);

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

			return (com.liferay.osb.koroneiki.taproot.model.TeamRole)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.osb.koroneiki.taproot.model.TeamRole
			updateTeamRole(
				HttpPrincipal httpPrincipal, long teamRoleId, String name,
				String description)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				TeamRoleServiceUtil.class, "updateTeamRole",
				_updateTeamRoleParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, teamRoleId, name, description);

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

			return (com.liferay.osb.koroneiki.taproot.model.TeamRole)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(TeamRoleServiceHttp.class);

	private static final Class<?>[] _addTeamRoleParameterTypes0 = new Class[] {
		String.class, String.class, int.class
	};
	private static final Class<?>[] _deleteTeamRoleParameterTypes1 =
		new Class[] {long.class};
	private static final Class<?>[] _deleteTeamRoleParameterTypes2 =
		new Class[] {String.class};
	private static final Class<?>[] _getTeamRoleParameterTypes3 = new Class[] {
		long.class
	};
	private static final Class<?>[] _getTeamRoleParameterTypes4 = new Class[] {
		String.class
	};
	private static final Class<?>[] _updateTeamRoleParameterTypes5 =
		new Class[] {long.class, String.class, String.class};

}