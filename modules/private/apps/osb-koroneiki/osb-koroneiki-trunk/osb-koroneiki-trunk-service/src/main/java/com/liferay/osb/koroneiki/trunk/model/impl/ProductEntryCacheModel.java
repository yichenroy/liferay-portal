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

package com.liferay.osb.koroneiki.trunk.model.impl;

import com.liferay.osb.koroneiki.trunk.model.ProductEntry;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The cache model class for representing ProductEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public class ProductEntryCacheModel
	implements CacheModel<ProductEntry>, Externalizable {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ProductEntryCacheModel)) {
			return false;
		}

		ProductEntryCacheModel productEntryCacheModel =
			(ProductEntryCacheModel)obj;

		if (productEntryId == productEntryCacheModel.productEntryId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, productEntryId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(17);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", productEntryId=");
		sb.append(productEntryId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", productEntryKey=");
		sb.append(productEntryKey);
		sb.append(", name=");
		sb.append(name);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public ProductEntry toEntityModel() {
		ProductEntryImpl productEntryImpl = new ProductEntryImpl();

		if (uuid == null) {
			productEntryImpl.setUuid("");
		}
		else {
			productEntryImpl.setUuid(uuid);
		}

		productEntryImpl.setProductEntryId(productEntryId);
		productEntryImpl.setCompanyId(companyId);
		productEntryImpl.setUserId(userId);

		if (createDate == Long.MIN_VALUE) {
			productEntryImpl.setCreateDate(null);
		}
		else {
			productEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			productEntryImpl.setModifiedDate(null);
		}
		else {
			productEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (productEntryKey == null) {
			productEntryImpl.setProductEntryKey("");
		}
		else {
			productEntryImpl.setProductEntryKey(productEntryKey);
		}

		if (name == null) {
			productEntryImpl.setName("");
		}
		else {
			productEntryImpl.setName(name);
		}

		productEntryImpl.resetOriginalValues();

		return productEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		productEntryId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		productEntryKey = objectInput.readUTF();
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

		objectOutput.writeLong(productEntryId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);
		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		if (productEntryKey == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(productEntryKey);
		}

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}
	}

	public String uuid;
	public long productEntryId;
	public long companyId;
	public long userId;
	public long createDate;
	public long modifiedDate;
	public String productEntryKey;
	public String name;

}