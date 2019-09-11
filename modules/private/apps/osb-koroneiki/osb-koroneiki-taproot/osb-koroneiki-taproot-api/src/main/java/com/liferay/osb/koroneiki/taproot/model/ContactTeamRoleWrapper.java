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
 * This class is a wrapper for {@link ContactTeamRole}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ContactTeamRole
 * @generated
 */
public class ContactTeamRoleWrapper
	extends BaseModelWrapper<ContactTeamRole>
	implements ContactTeamRole, ModelWrapper<ContactTeamRole> {

	public ContactTeamRoleWrapper(ContactTeamRole contactTeamRole) {
		super(contactTeamRole);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("contactId", getContactId());
		attributes.put("teamId", getTeamId());
		attributes.put("contactRoleId", getContactRoleId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long contactId = (Long)attributes.get("contactId");

		if (contactId != null) {
			setContactId(contactId);
		}

		Long teamId = (Long)attributes.get("teamId");

		if (teamId != null) {
			setTeamId(teamId);
		}

		Long contactRoleId = (Long)attributes.get("contactRoleId");

		if (contactRoleId != null) {
			setContactRoleId(contactRoleId);
		}
	}

	/**
	 * Returns the contact ID of this contact team role.
	 *
	 * @return the contact ID of this contact team role
	 */
	@Override
	public long getContactId() {
		return model.getContactId();
	}

	/**
	 * Returns the contact role ID of this contact team role.
	 *
	 * @return the contact role ID of this contact team role
	 */
	@Override
	public long getContactRoleId() {
		return model.getContactRoleId();
	}

	/**
	 * Returns the primary key of this contact team role.
	 *
	 * @return the primary key of this contact team role
	 */
	@Override
	public
		com.liferay.osb.koroneiki.taproot.service.persistence.ContactTeamRolePK
			getPrimaryKey() {

		return model.getPrimaryKey();
	}

	/**
	 * Returns the team ID of this contact team role.
	 *
	 * @return the team ID of this contact team role
	 */
	@Override
	public long getTeamId() {
		return model.getTeamId();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a contact team role model instance should use the <code>ContactTeamRole</code> interface instead.
	 */
	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the contact ID of this contact team role.
	 *
	 * @param contactId the contact ID of this contact team role
	 */
	@Override
	public void setContactId(long contactId) {
		model.setContactId(contactId);
	}

	/**
	 * Sets the contact role ID of this contact team role.
	 *
	 * @param contactRoleId the contact role ID of this contact team role
	 */
	@Override
	public void setContactRoleId(long contactRoleId) {
		model.setContactRoleId(contactRoleId);
	}

	/**
	 * Sets the primary key of this contact team role.
	 *
	 * @param primaryKey the primary key of this contact team role
	 */
	@Override
	public void setPrimaryKey(
		com.liferay.osb.koroneiki.taproot.service.persistence.ContactTeamRolePK
			primaryKey) {

		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the team ID of this contact team role.
	 *
	 * @param teamId the team ID of this contact team role
	 */
	@Override
	public void setTeamId(long teamId) {
		model.setTeamId(teamId);
	}

	@Override
	protected ContactTeamRoleWrapper wrap(ContactTeamRole contactTeamRole) {
		return new ContactTeamRoleWrapper(contactTeamRole);
	}

}