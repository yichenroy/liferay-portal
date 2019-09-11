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

package com.liferay.osb.koroneiki.taproot.model.impl;

import com.liferay.osb.koroneiki.taproot.model.TeamRole;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing TeamRole in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class TeamRoleCacheModel
	implements CacheModel<TeamRole>, Externalizable {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof TeamRoleCacheModel)) {
			return false;
		}

		TeamRoleCacheModel teamRoleCacheModel = (TeamRoleCacheModel)obj;

		if (teamRoleId == teamRoleCacheModel.teamRoleId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, teamRoleId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(21);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", teamRoleId=");
		sb.append(teamRoleId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", teamRoleKey=");
		sb.append(teamRoleKey);
		sb.append(", name=");
		sb.append(name);
		sb.append(", description=");
		sb.append(description);
		sb.append(", type=");
		sb.append(type);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public TeamRole toEntityModel() {
		TeamRoleImpl teamRoleImpl = new TeamRoleImpl();

		if (uuid == null) {
			teamRoleImpl.setUuid("");
		}
		else {
			teamRoleImpl.setUuid(uuid);
		}

		teamRoleImpl.setTeamRoleId(teamRoleId);
		teamRoleImpl.setCompanyId(companyId);
		teamRoleImpl.setUserId(userId);

		if (createDate == Long.MIN_VALUE) {
			teamRoleImpl.setCreateDate(null);
		}
		else {
			teamRoleImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			teamRoleImpl.setModifiedDate(null);
		}
		else {
			teamRoleImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (teamRoleKey == null) {
			teamRoleImpl.setTeamRoleKey("");
		}
		else {
			teamRoleImpl.setTeamRoleKey(teamRoleKey);
		}

		if (name == null) {
			teamRoleImpl.setName("");
		}
		else {
			teamRoleImpl.setName(name);
		}

		if (description == null) {
			teamRoleImpl.setDescription("");
		}
		else {
			teamRoleImpl.setDescription(description);
		}

		teamRoleImpl.setType(type);

		teamRoleImpl.resetOriginalValues();

		return teamRoleImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		teamRoleId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		teamRoleKey = objectInput.readUTF();
		name = objectInput.readUTF();
		description = objectInput.readUTF();

		type = objectInput.readInt();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(teamRoleId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);
		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		if (teamRoleKey == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(teamRoleKey);
		}

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (description == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(description);
		}

		objectOutput.writeInt(type);
	}

	public String uuid;
	public long teamRoleId;
	public long companyId;
	public long userId;
	public long createDate;
	public long modifiedDate;
	public String teamRoleKey;
	public String name;
	public String description;
	public int type;

}