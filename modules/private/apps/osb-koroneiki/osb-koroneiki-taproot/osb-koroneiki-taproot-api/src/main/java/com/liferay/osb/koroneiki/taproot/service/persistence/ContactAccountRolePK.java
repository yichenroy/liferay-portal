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
public class ContactAccountRolePK
	implements Comparable<ContactAccountRolePK>, Serializable {

	public long contactId;
	public long accountId;
	public long contactRoleId;

	public ContactAccountRolePK() {
	}

	public ContactAccountRolePK(
		long contactId, long accountId, long contactRoleId) {

		this.contactId = contactId;
		this.accountId = accountId;
		this.contactRoleId = contactRoleId;
	}

	public long getContactId() {
		return contactId;
	}

	public void setContactId(long contactId) {
		this.contactId = contactId;
	}

	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	public long getContactRoleId() {
		return contactRoleId;
	}

	public void setContactRoleId(long contactRoleId) {
		this.contactRoleId = contactRoleId;
	}

	@Override
	public int compareTo(ContactAccountRolePK pk) {
		if (pk == null) {
			return -1;
		}

		int value = 0;

		if (contactId < pk.contactId) {
			value = -1;
		}
		else if (contactId > pk.contactId) {
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

		if (contactRoleId < pk.contactRoleId) {
			value = -1;
		}
		else if (contactRoleId > pk.contactRoleId) {
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

		if (!(obj instanceof ContactAccountRolePK)) {
			return false;
		}

		ContactAccountRolePK pk = (ContactAccountRolePK)obj;

		if ((contactId == pk.contactId) && (accountId == pk.accountId) &&
			(contactRoleId == pk.contactRoleId)) {

			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		int hashCode = 0;

		hashCode = HashUtil.hash(hashCode, contactId);
		hashCode = HashUtil.hash(hashCode, accountId);
		hashCode = HashUtil.hash(hashCode, contactRoleId);

		return hashCode;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(8);

		sb.append("{");

		sb.append("contactId=");

		sb.append(contactId);
		sb.append(", accountId=");

		sb.append(accountId);
		sb.append(", contactRoleId=");

		sb.append(contactRoleId);

		sb.append("}");

		return sb.toString();
	}

}