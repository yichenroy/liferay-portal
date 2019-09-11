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

import com.liferay.osb.koroneiki.taproot.model.ContactTeamRole;
import com.liferay.osb.koroneiki.taproot.service.persistence.ContactTeamRolePK;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing ContactTeamRole in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class ContactTeamRoleCacheModel
	implements CacheModel<ContactTeamRole>, Externalizable {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ContactTeamRoleCacheModel)) {
			return false;
		}

		ContactTeamRoleCacheModel contactTeamRoleCacheModel =
			(ContactTeamRoleCacheModel)obj;

		if (contactTeamRolePK.equals(
				contactTeamRoleCacheModel.contactTeamRolePK)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, contactTeamRolePK);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(7);

		sb.append("{contactId=");
		sb.append(contactId);
		sb.append(", teamId=");
		sb.append(teamId);
		sb.append(", contactRoleId=");
		sb.append(contactRoleId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public ContactTeamRole toEntityModel() {
		ContactTeamRoleImpl contactTeamRoleImpl = new ContactTeamRoleImpl();

		contactTeamRoleImpl.setContactId(contactId);
		contactTeamRoleImpl.setTeamId(teamId);
		contactTeamRoleImpl.setContactRoleId(contactRoleId);

		contactTeamRoleImpl.resetOriginalValues();

		return contactTeamRoleImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		contactId = objectInput.readLong();

		teamId = objectInput.readLong();

		contactRoleId = objectInput.readLong();

		contactTeamRolePK = new ContactTeamRolePK(
			contactId, teamId, contactRoleId);
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(contactId);

		objectOutput.writeLong(teamId);

		objectOutput.writeLong(contactRoleId);
	}

	public long contactId;
	public long teamId;
	public long contactRoleId;
	public transient ContactTeamRolePK contactTeamRolePK;

}