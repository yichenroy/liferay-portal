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

import com.liferay.osb.koroneiki.taproot.model.ContactAccountRole;
import com.liferay.osb.koroneiki.taproot.service.persistence.ContactAccountRolePK;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing ContactAccountRole in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class ContactAccountRoleCacheModel
	implements CacheModel<ContactAccountRole>, Externalizable {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ContactAccountRoleCacheModel)) {
			return false;
		}

		ContactAccountRoleCacheModel contactAccountRoleCacheModel =
			(ContactAccountRoleCacheModel)obj;

		if (contactAccountRolePK.equals(
				contactAccountRoleCacheModel.contactAccountRolePK)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, contactAccountRolePK);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(7);

		sb.append("{contactId=");
		sb.append(contactId);
		sb.append(", accountId=");
		sb.append(accountId);
		sb.append(", contactRoleId=");
		sb.append(contactRoleId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public ContactAccountRole toEntityModel() {
		ContactAccountRoleImpl contactAccountRoleImpl =
			new ContactAccountRoleImpl();

		contactAccountRoleImpl.setContactId(contactId);
		contactAccountRoleImpl.setAccountId(accountId);
		contactAccountRoleImpl.setContactRoleId(contactRoleId);

		contactAccountRoleImpl.resetOriginalValues();

		return contactAccountRoleImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		contactId = objectInput.readLong();

		accountId = objectInput.readLong();

		contactRoleId = objectInput.readLong();

		contactAccountRolePK = new ContactAccountRolePK(
			contactId, accountId, contactRoleId);
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(contactId);

		objectOutput.writeLong(accountId);

		objectOutput.writeLong(contactRoleId);
	}

	public long contactId;
	public long accountId;
	public long contactRoleId;
	public transient ContactAccountRolePK contactAccountRolePK;

}