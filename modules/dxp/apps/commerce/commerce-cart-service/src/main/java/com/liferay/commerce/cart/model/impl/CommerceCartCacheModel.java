/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.commerce.cart.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.cart.model.CommerceCart;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CommerceCart in entity cache.
 *
 * @author Marco Leo
 * @see CommerceCart
 * @generated
 */
@ProviderType
public class CommerceCartCacheModel implements CacheModel<CommerceCart>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceCartCacheModel)) {
			return false;
		}

		CommerceCartCacheModel commerceCartCacheModel = (CommerceCartCacheModel)obj;

		if (CommerceCartId == commerceCartCacheModel.CommerceCartId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, CommerceCartId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(19);

		sb.append("{CommerceCartId=");
		sb.append(CommerceCartId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", userName=");
		sb.append(userName);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", name=");
		sb.append(name);
		sb.append(", type=");
		sb.append(type);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceCart toEntityModel() {
		CommerceCartImpl commerceCartImpl = new CommerceCartImpl();

		commerceCartImpl.setCommerceCartId(CommerceCartId);
		commerceCartImpl.setGroupId(groupId);
		commerceCartImpl.setCompanyId(companyId);
		commerceCartImpl.setUserId(userId);

		if (userName == null) {
			commerceCartImpl.setUserName(StringPool.BLANK);
		}
		else {
			commerceCartImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceCartImpl.setCreateDate(null);
		}
		else {
			commerceCartImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceCartImpl.setModifiedDate(null);
		}
		else {
			commerceCartImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (name == null) {
			commerceCartImpl.setName(StringPool.BLANK);
		}
		else {
			commerceCartImpl.setName(name);
		}

		commerceCartImpl.setType(type);

		commerceCartImpl.resetOriginalValues();

		return commerceCartImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		CommerceCartId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		name = objectInput.readUTF();

		type = objectInput.readInt();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(CommerceCartId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		if (name == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(name);
		}

		objectOutput.writeInt(type);
	}

	public long CommerceCartId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String name;
	public int type;
}