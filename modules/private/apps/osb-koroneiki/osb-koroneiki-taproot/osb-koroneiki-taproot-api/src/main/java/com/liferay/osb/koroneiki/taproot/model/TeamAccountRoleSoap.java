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

package com.liferay.osb.koroneiki.taproot.model;

import com.liferay.osb.koroneiki.taproot.service.persistence.TeamAccountRolePK;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.osb.koroneiki.taproot.service.http.TeamAccountRoleServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class TeamAccountRoleSoap implements Serializable {

	public static TeamAccountRoleSoap toSoapModel(TeamAccountRole model) {
		TeamAccountRoleSoap soapModel = new TeamAccountRoleSoap();

		soapModel.setTeamId(model.getTeamId());
		soapModel.setAccountId(model.getAccountId());
		soapModel.setTeamRoleId(model.getTeamRoleId());

		return soapModel;
	}

	public static TeamAccountRoleSoap[] toSoapModels(TeamAccountRole[] models) {
		TeamAccountRoleSoap[] soapModels =
			new TeamAccountRoleSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static TeamAccountRoleSoap[][] toSoapModels(
		TeamAccountRole[][] models) {

		TeamAccountRoleSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new TeamAccountRoleSoap[models.length][models[0].length];
		}
		else {
			soapModels = new TeamAccountRoleSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static TeamAccountRoleSoap[] toSoapModels(
		List<TeamAccountRole> models) {

		List<TeamAccountRoleSoap> soapModels =
			new ArrayList<TeamAccountRoleSoap>(models.size());

		for (TeamAccountRole model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new TeamAccountRoleSoap[soapModels.size()]);
	}

	public TeamAccountRoleSoap() {
	}

	public TeamAccountRolePK getPrimaryKey() {
		return new TeamAccountRolePK(_teamId, _accountId, _teamRoleId);
	}

	public void setPrimaryKey(TeamAccountRolePK pk) {
		setTeamId(pk.teamId);
		setAccountId(pk.accountId);
		setTeamRoleId(pk.teamRoleId);
	}

	public long getTeamId() {
		return _teamId;
	}

	public void setTeamId(long teamId) {
		_teamId = teamId;
	}

	public long getAccountId() {
		return _accountId;
	}

	public void setAccountId(long accountId) {
		_accountId = accountId;
	}

	public long getTeamRoleId() {
		return _teamRoleId;
	}

	public void setTeamRoleId(long teamRoleId) {
		_teamRoleId = teamRoleId;
	}

	private long _teamId;
	private long _accountId;
	private long _teamRoleId;

}