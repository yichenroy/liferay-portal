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

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.osb.koroneiki.taproot.service.http.TeamRoleServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class TeamRoleSoap implements Serializable {

	public static TeamRoleSoap toSoapModel(TeamRole model) {
		TeamRoleSoap soapModel = new TeamRoleSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setTeamRoleId(model.getTeamRoleId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setTeamRoleKey(model.getTeamRoleKey());
		soapModel.setName(model.getName());
		soapModel.setDescription(model.getDescription());
		soapModel.setType(model.getType());

		return soapModel;
	}

	public static TeamRoleSoap[] toSoapModels(TeamRole[] models) {
		TeamRoleSoap[] soapModels = new TeamRoleSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static TeamRoleSoap[][] toSoapModels(TeamRole[][] models) {
		TeamRoleSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new TeamRoleSoap[models.length][models[0].length];
		}
		else {
			soapModels = new TeamRoleSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static TeamRoleSoap[] toSoapModels(List<TeamRole> models) {
		List<TeamRoleSoap> soapModels = new ArrayList<TeamRoleSoap>(
			models.size());

		for (TeamRole model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new TeamRoleSoap[soapModels.size()]);
	}

	public TeamRoleSoap() {
	}

	public long getPrimaryKey() {
		return _teamRoleId;
	}

	public void setPrimaryKey(long pk) {
		setTeamRoleId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getTeamRoleId() {
		return _teamRoleId;
	}

	public void setTeamRoleId(long teamRoleId) {
		_teamRoleId = teamRoleId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public String getTeamRoleKey() {
		return _teamRoleKey;
	}

	public void setTeamRoleKey(String teamRoleKey) {
		_teamRoleKey = teamRoleKey;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getDescription() {
		return _description;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public int getType() {
		return _type;
	}

	public void setType(int type) {
		_type = type;
	}

	private String _uuid;
	private long _teamRoleId;
	private long _companyId;
	private long _userId;
	private Date _createDate;
	private Date _modifiedDate;
	private String _teamRoleKey;
	private String _name;
	private String _description;
	private int _type;

}