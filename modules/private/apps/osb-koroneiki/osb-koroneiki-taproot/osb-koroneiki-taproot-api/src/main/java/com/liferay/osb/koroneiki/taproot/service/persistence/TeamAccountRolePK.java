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

package com.liferay.osb.koroneiki.taproot.service.persistence;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;

import java.io.Serializable;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
public class TeamAccountRolePK
	implements Comparable<TeamAccountRolePK>, Serializable {

	public long teamId;
	public long accountId;
	public long teamRoleId;

	public TeamAccountRolePK() {
	}

	public TeamAccountRolePK(long teamId, long accountId, long teamRoleId) {
		this.teamId = teamId;
		this.accountId = accountId;
		this.teamRoleId = teamRoleId;
	}

	public long getTeamId() {
		return teamId;
	}

	public void setTeamId(long teamId) {
		this.teamId = teamId;
	}

	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	public long getTeamRoleId() {
		return teamRoleId;
	}

	public void setTeamRoleId(long teamRoleId) {
		this.teamRoleId = teamRoleId;
	}

	@Override
	public int compareTo(TeamAccountRolePK pk) {
		if (pk == null) {
			return -1;
		}

		int value = 0;

		if (teamId < pk.teamId) {
			value = -1;
		}
		else if (teamId > pk.teamId) {
			value = 1;
		}
		else {
			value = 0;
		}

		if (value != 0) {
			return value;
		}

		if (accountId < pk.accountId) {
			value = -1;
		}
		else if (accountId > pk.accountId) {
			value = 1;
		}
		else {
			value = 0;
		}

		if (value != 0) {
			return value;
		}

		if (teamRoleId < pk.teamRoleId) {
			value = -1;
		}
		else if (teamRoleId > pk.teamRoleId) {
			value = 1;
		}
		else {
			value = 0;
		}

		if (value != 0) {
			return value;
		}

		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof TeamAccountRolePK)) {
			return false;
		}

		TeamAccountRolePK pk = (TeamAccountRolePK)obj;

		if ((teamId == pk.teamId) && (accountId == pk.accountId) &&
			(teamRoleId == pk.teamRoleId)) {

			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		int hashCode = 0;

		hashCode = HashUtil.hash(hashCode, teamId);
		hashCode = HashUtil.hash(hashCode, accountId);
		hashCode = HashUtil.hash(hashCode, teamRoleId);

		return hashCode;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(8);

		sb.append("{");

		sb.append("teamId=");

		sb.append(teamId);
		sb.append(", accountId=");

		sb.append(accountId);
		sb.append(", teamRoleId=");

		sb.append(teamRoleId);

		sb.append("}");

		return sb.toString();
	}

}