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

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>TeamRoleServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.osb.koroneiki.taproot.model.TeamRoleSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.osb.koroneiki.taproot.model.TeamRole</code>, that is translated to a
 * <code>com.liferay.osb.koroneiki.taproot.model.TeamRoleSoap</code>. Methods that SOAP
 * cannot safely wire are skipped.
 * </p>
 *
 * <p>
 * The benefits of using the SOAP utility is that it is cross platform
 * compatible. SOAP allows different languages like Java, .NET, C++, PHP, and
 * even Perl, to call the generated services. One drawback of SOAP is that it is
 * slow because it needs to serialize all calls into a text format (XML).
 * </p>
 *
 * <p>
 * You can see a list of services at http://localhost:8080/api/axis. Set the
 * property <b>axis.servlet.hosts.allowed</b> in portal.properties to configure
 * security.
 * </p>
 *
 * <p>
 * The SOAP utility is only generated for remote services.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see TeamRoleServiceHttp
 * @generated
 */
public class TeamRoleServiceSoap {

	public static com.liferay.osb.koroneiki.taproot.model.TeamRoleSoap
			addTeamRole(String name, String description, int type)
		throws RemoteException {

		try {
			com.liferay.osb.koroneiki.taproot.model.TeamRole returnValue =
				TeamRoleServiceUtil.addTeamRole(name, description, type);

			return com.liferay.osb.koroneiki.taproot.model.TeamRoleSoap.
				toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.osb.koroneiki.taproot.model.TeamRoleSoap
			deleteTeamRole(long teamRoleId)
		throws RemoteException {

		try {
			com.liferay.osb.koroneiki.taproot.model.TeamRole returnValue =
				TeamRoleServiceUtil.deleteTeamRole(teamRoleId);

			return com.liferay.osb.koroneiki.taproot.model.TeamRoleSoap.
				toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.osb.koroneiki.taproot.model.TeamRoleSoap
			deleteTeamRole(String teamRoleKey)
		throws RemoteException {

		try {
			com.liferay.osb.koroneiki.taproot.model.TeamRole returnValue =
				TeamRoleServiceUtil.deleteTeamRole(teamRoleKey);

			return com.liferay.osb.koroneiki.taproot.model.TeamRoleSoap.
				toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.osb.koroneiki.taproot.model.TeamRoleSoap
			getTeamRole(long teamRoleId)
		throws RemoteException {

		try {
			com.liferay.osb.koroneiki.taproot.model.TeamRole returnValue =
				TeamRoleServiceUtil.getTeamRole(teamRoleId);

			return com.liferay.osb.koroneiki.taproot.model.TeamRoleSoap.
				toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.osb.koroneiki.taproot.model.TeamRoleSoap
			getTeamRole(String teamRoleKey)
		throws RemoteException {

		try {
			com.liferay.osb.koroneiki.taproot.model.TeamRole returnValue =
				TeamRoleServiceUtil.getTeamRole(teamRoleKey);

			return com.liferay.osb.koroneiki.taproot.model.TeamRoleSoap.
				toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.osb.koroneiki.taproot.model.TeamRoleSoap
			updateTeamRole(long teamRoleId, String name, String description)
		throws RemoteException {

		try {
			com.liferay.osb.koroneiki.taproot.model.TeamRole returnValue =
				TeamRoleServiceUtil.updateTeamRole(
					teamRoleId, name, description);

			return com.liferay.osb.koroneiki.taproot.model.TeamRoleSoap.
				toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(TeamRoleServiceSoap.class);

}