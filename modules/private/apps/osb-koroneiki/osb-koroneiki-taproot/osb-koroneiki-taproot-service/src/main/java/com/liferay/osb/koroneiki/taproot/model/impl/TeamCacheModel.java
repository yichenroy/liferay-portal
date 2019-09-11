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

import com.liferay.osb.koroneiki.taproot.model.Team;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing Team in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class TeamCacheModel implements CacheModel<Team>, Externalizable {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof TeamCacheModel)) {
			return false;
		}

		TeamCacheModel teamCacheModel = (TeamCacheModel)obj;

		if (teamId == teamCacheModel.teamId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, teamId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(19);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", teamId=");
		sb.append(teamId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", teamKey=");
		sb.append(teamKey);
		sb.append(", accountId=");
		sb.append(accountId);
		sb.append(", name=");
		sb.append(name);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public Team toEntityModel() {
		TeamImpl teamImpl = new TeamImpl();

		if (uuid == null) {
			teamImpl.setUuid("");
		}
		else {
			teamImpl.setUuid(uuid);
		}

		teamImpl.setTeamId(teamId);
		teamImpl.setCompanyId(companyId);
		teamImpl.setUserId(userId);

		if (createDate == Long.MIN_VALUE) {
			teamImpl.setCreateDate(null);
		}
		else {
			teamImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			teamImpl.setModifiedDate(null);
		}
		else {
			teamImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (teamKey == null) {
			teamImpl.setTeamKey("");
		}
		else {
			teamImpl.setTeamKey(teamKey);
		}

		teamImpl.setAccountId(accountId);

		if (name == null) {
			teamImpl.setName("");
		}
		else {
			teamImpl.setName(name);
		}

		teamImpl.resetOriginalValues();

		return teamImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		teamId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		teamKey = objectInput.readUTF();

		accountId = objectInput.readLong();
		name = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(teamId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);
		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		if (teamKey == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(teamKey);
		}

		objectOutput.writeLong(accountId);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}
	}

	public String uuid;
	public long teamId;
	public long companyId;
	public long userId;
	public long createDate;
	public long modifiedDate;
	public String teamKey;
	public long accountId;
	public String name;

}