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

import com.liferay.osb.koroneiki.taproot.model.ContactRole;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing ContactRole in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class ContactRoleCacheModel
	implements CacheModel<ContactRole>, Externalizable {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ContactRoleCacheModel)) {
			return false;
		}

		ContactRoleCacheModel contactRoleCacheModel =
			(ContactRoleCacheModel)obj;

		if (contactRoleId == contactRoleCacheModel.contactRoleId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, contactRoleId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(23);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", contactRoleId=");
		sb.append(contactRoleId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", contactRoleKey=");
		sb.append(contactRoleKey);
		sb.append(", name=");
		sb.append(name);
		sb.append(", description=");
		sb.append(description);
		sb.append(", type=");
		sb.append(type);
		sb.append(", system=");
		sb.append(system);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public ContactRole toEntityModel() {
		ContactRoleImpl contactRoleImpl = new ContactRoleImpl();

		if (uuid == null) {
			contactRoleImpl.setUuid("");
		}
		else {
			contactRoleImpl.setUuid(uuid);
		}

		contactRoleImpl.setContactRoleId(contactRoleId);
		contactRoleImpl.setCompanyId(companyId);
		contactRoleImpl.setUserId(userId);

		if (createDate == Long.MIN_VALUE) {
			contactRoleImpl.setCreateDate(null);
		}
		else {
			contactRoleImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			contactRoleImpl.setModifiedDate(null);
		}
		else {
			contactRoleImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (contactRoleKey == null) {
			contactRoleImpl.setContactRoleKey("");
		}
		else {
			contactRoleImpl.setContactRoleKey(contactRoleKey);
		}

		if (name == null) {
			contactRoleImpl.setName("");
		}
		else {
			contactRoleImpl.setName(name);
		}

		if (description == null) {
			contactRoleImpl.setDescription("");
		}
		else {
			contactRoleImpl.setDescription(description);
		}

		contactRoleImpl.setType(type);
		contactRoleImpl.setSystem(system);

		contactRoleImpl.resetOriginalValues();

		return contactRoleImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		contactRoleId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		contactRoleKey = objectInput.readUTF();
		name = objectInput.readUTF();
		description = objectInput.readUTF();

		type = objectInput.readInt();

		system = objectInput.readBoolean();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(contactRoleId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);
		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		if (contactRoleKey == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(contactRoleKey);
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

		objectOutput.writeBoolean(system);
	}

	public String uuid;
	public long contactRoleId;
	public long companyId;
	public long userId;
	public long createDate;
	public long modifiedDate;
	public String contactRoleKey;
	public String name;
	public String description;
	public int type;
	public boolean system;

}