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

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link TeamAccountRole}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see TeamAccountRole
 * @generated
 */
public class TeamAccountRoleWrapper
	extends BaseModelWrapper<TeamAccountRole>
	implements TeamAccountRole, ModelWrapper<TeamAccountRole> {

	public TeamAccountRoleWrapper(TeamAccountRole teamAccountRole) {
		super(teamAccountRole);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("teamId", getTeamId());
		attributes.put("accountId", getAccountId());
		attributes.put("teamRoleId", getTeamRoleId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long teamId = (Long)attributes.get("teamId");

		if (teamId != null) {
			setTeamId(teamId);
		}

		Long accountId = (Long)attributes.get("accountId");

		if (accountId != null) {
			setAccountId(accountId);
		}

		Long teamRoleId = (Long)attributes.get("teamRoleId");

		if (teamRoleId != null) {
			setTeamRoleId(teamRoleId);
		}
	}

	@Override
	public Account getAccount()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getAccount();
	}

	/**
	 * Returns the account ID of this team account role.
	 *
	 * @return the account ID of this team account role
	 */
	@Override
	public long getAccountId() {
		return model.getAccountId();
	}

	/**
	 * Returns the primary key of this team account role.
	 *
	 * @return the primary key of this team account role
	 */
	@Override
	public
		com.liferay.osb.koroneiki.taproot.service.persistence.TeamAccountRolePK
			getPrimaryKey() {

		return model.getPrimaryKey();
	}

	@Override
	public Team getTeam()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getTeam();
	}

	/**
	 * Returns the team ID of this team account role.
	 *
	 * @return the team ID of this team account role
	 */
	@Override
	public long getTeamId() {
		return model.getTeamId();
	}

	@Override
	public TeamRole getTeamRole()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getTeamRole();
	}

	/**
	 * Returns the team role ID of this team account role.
	 *
	 * @return the team role ID of this team account role
	 */
	@Override
	public long getTeamRoleId() {
		return model.getTeamRoleId();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a team account role model instance should use the <code>TeamAccountRole</code> interface instead.
	 */
	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the account ID of this team account role.
	 *
	 * @param accountId the account ID of this team account role
	 */
	@Override
	public void setAccountId(long accountId) {
		model.setAccountId(accountId);
	}

	/**
	 * Sets the primary key of this team account role.
	 *
	 * @param primaryKey the primary key of this team account role
	 */
	@Override
	public void setPrimaryKey(
		com.liferay.osb.koroneiki.taproot.service.persistence.TeamAccountRolePK
			primaryKey) {

		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the team ID of this team account role.
	 *
	 * @param teamId the team ID of this team account role
	 */
	@Override
	public void setTeamId(long teamId) {
		model.setTeamId(teamId);
	}

	/**
	 * Sets the team role ID of this team account role.
	 *
	 * @param teamRoleId the team role ID of this team account role
	 */
	@Override
	public void setTeamRoleId(long teamRoleId) {
		model.setTeamRoleId(teamRoleId);
	}

	@Override
	protected TeamAccountRoleWrapper wrap(TeamAccountRole teamAccountRole) {
		return new TeamAccountRoleWrapper(teamAccountRole);
	}

}