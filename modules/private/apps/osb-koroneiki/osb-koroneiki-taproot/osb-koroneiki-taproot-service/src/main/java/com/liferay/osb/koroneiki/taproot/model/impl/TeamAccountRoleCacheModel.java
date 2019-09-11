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

import com.liferay.osb.koroneiki.taproot.model.TeamAccountRole;
import com.liferay.osb.koroneiki.taproot.service.persistence.TeamAccountRolePK;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing TeamAccountRole in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class TeamAccountRoleCacheModel
	implements CacheModel<TeamAccountRole>, Externalizable {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof TeamAccountRoleCacheModel)) {
			return false;
		}

		TeamAccountRoleCacheModel teamAccountRoleCacheModel =
			(TeamAccountRoleCacheModel)obj;

		if (teamAccountRolePK.equals(
				teamAccountRoleCacheModel.teamAccountRolePK)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, teamAccountRolePK);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(7);

		sb.append("{teamId=");
		sb.append(teamId);
		sb.append(", accountId=");
		sb.append(accountId);
		sb.append(", teamRoleId=");
		sb.append(teamRoleId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public TeamAccountRole toEntityModel() {
		TeamAccountRoleImpl teamAccountRoleImpl = new TeamAccountRoleImpl();

		teamAccountRoleImpl.setTeamId(teamId);
		teamAccountRoleImpl.setAccountId(accountId);
		teamAccountRoleImpl.setTeamRoleId(teamRoleId);

		teamAccountRoleImpl.resetOriginalValues();

		return teamAccountRoleImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		teamId = objectInput.readLong();

		accountId = objectInput.readLong();

		teamRoleId = objectInput.readLong();

		teamAccountRolePK = new TeamAccountRolePK(
			teamId, accountId, teamRoleId);
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(teamId);

		objectOutput.writeLong(accountId);

		objectOutput.writeLong(teamRoleId);
	}

	public long teamId;
	public long accountId;
	public long teamRoleId;
	public transient TeamAccountRolePK teamAccountRolePK;

}