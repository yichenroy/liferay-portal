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

import com.liferay.osb.koroneiki.taproot.service.TeamServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>TeamServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.osb.koroneiki.taproot.model.TeamSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.osb.koroneiki.taproot.model.Team</code>, that is translated to a
 * <code>com.liferay.osb.koroneiki.taproot.model.TeamSoap</code>. Methods that SOAP
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
 * @see TeamServiceHttp
 * @generated
 */
public class TeamServiceSoap {

	public static com.liferay.osb.koroneiki.taproot.model.TeamSoap addTeam(
			long accountId, String name)
		throws RemoteException {

		try {
			com.liferay.osb.koroneiki.taproot.model.Team returnValue =
				TeamServiceUtil.addTeam(accountId, name);

			return com.liferay.osb.koroneiki.taproot.model.TeamSoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.osb.koroneiki.taproot.model.TeamSoap addTeam(
			String accountKey, String name)
		throws RemoteException {

		try {
			com.liferay.osb.koroneiki.taproot.model.Team returnValue =
				TeamServiceUtil.addTeam(accountKey, name);

			return com.liferay.osb.koroneiki.taproot.model.TeamSoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.osb.koroneiki.taproot.model.TeamSoap deleteTeam(
			long teamId)
		throws RemoteException {

		try {
			com.liferay.osb.koroneiki.taproot.model.Team returnValue =
				TeamServiceUtil.deleteTeam(teamId);

			return com.liferay.osb.koroneiki.taproot.model.TeamSoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.osb.koroneiki.taproot.model.TeamSoap deleteTeam(
			String teamKey)
		throws RemoteException {

		try {
			com.liferay.osb.koroneiki.taproot.model.Team returnValue =
				TeamServiceUtil.deleteTeam(teamKey);

			return com.liferay.osb.koroneiki.taproot.model.TeamSoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.osb.koroneiki.taproot.model.TeamSoap[]
			getAccountTeams(long accountId, int start, int end)
		throws RemoteException {

		try {
			java.util.List<com.liferay.osb.koroneiki.taproot.model.Team>
				returnValue = TeamServiceUtil.getAccountTeams(
					accountId, start, end);

			return com.liferay.osb.koroneiki.taproot.model.TeamSoap.
				toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.osb.koroneiki.taproot.model.TeamSoap[]
			getAccountTeams(String accountKey, int start, int end)
		throws RemoteException {

		try {
			java.util.List<com.liferay.osb.koroneiki.taproot.model.Team>
				returnValue = TeamServiceUtil.getAccountTeams(
					accountKey, start, end);

			return com.liferay.osb.koroneiki.taproot.model.TeamSoap.
				toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getAccountTeamsCount(long accountId)
		throws RemoteException {

		try {
			int returnValue = TeamServiceUtil.getAccountTeamsCount(accountId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getAccountTeamsCount(String accountKey)
		throws RemoteException {

		try {
			int returnValue = TeamServiceUtil.getAccountTeamsCount(accountKey);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.osb.koroneiki.taproot.model.TeamSoap getTeam(
			long teamId)
		throws RemoteException {

		try {
			com.liferay.osb.koroneiki.taproot.model.Team returnValue =
				TeamServiceUtil.getTeam(teamId);

			return com.liferay.osb.koroneiki.taproot.model.TeamSoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.osb.koroneiki.taproot.model.TeamSoap getTeam(
			String teamKey)
		throws RemoteException {

		try {
			com.liferay.osb.koroneiki.taproot.model.Team returnValue =
				TeamServiceUtil.getTeam(teamKey);

			return com.liferay.osb.koroneiki.taproot.model.TeamSoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.osb.koroneiki.taproot.model.TeamSoap[] getTeams(
			String domain, String entityName, String entityId, int start,
			int end)
		throws RemoteException {

		try {
			java.util.List<com.liferay.osb.koroneiki.taproot.model.Team>
				returnValue = TeamServiceUtil.getTeams(
					domain, entityName, entityId, start, end);

			return com.liferay.osb.koroneiki.taproot.model.TeamSoap.
				toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getTeamsCount(
			String domain, String entityName, String entityId)
		throws RemoteException {

		try {
			int returnValue = TeamServiceUtil.getTeamsCount(
				domain, entityName, entityId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.osb.koroneiki.taproot.model.TeamSoap updateTeam(
			long teamId, String name)
		throws RemoteException {

		try {
			com.liferay.osb.koroneiki.taproot.model.Team returnValue =
				TeamServiceUtil.updateTeam(teamId, name);

			return com.liferay.osb.koroneiki.taproot.model.TeamSoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.osb.koroneiki.taproot.model.TeamSoap updateTeam(
			String teamKey, String name)
		throws RemoteException {

		try {
			com.liferay.osb.koroneiki.taproot.model.Team returnValue =
				TeamServiceUtil.updateTeam(teamKey, name);

			return com.liferay.osb.koroneiki.taproot.model.TeamSoap.toSoapModel(
				returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(TeamServiceSoap.class);

}