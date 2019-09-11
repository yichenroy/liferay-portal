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

import com.liferay.osb.koroneiki.trunk.model.ProductConsumption;
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
 * The cache model class for representing ProductConsumption in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public class ProductConsumptionCacheModel
	implements CacheModel<ProductConsumption>, Externalizable {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ProductConsumptionCacheModel)) {
			return false;
		}

		ProductConsumptionCacheModel productConsumptionCacheModel =
			(ProductConsumptionCacheModel)obj;

		if (productConsumptionId ==
				productConsumptionCacheModel.productConsumptionId) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, productConsumptionId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(19);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", productConsumptionId=");
		sb.append(productConsumptionId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", productConsumptionKey=");
		sb.append(productConsumptionKey);
		sb.append(", accountId=");
		sb.append(accountId);
		sb.append(", productEntryId=");
		sb.append(productEntryId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public ProductConsumption toEntityModel() {
		ProductConsumptionImpl productConsumptionImpl =
			new ProductConsumptionImpl();

		if (uuid == null) {
			productConsumptionImpl.setUuid("");
		}
		else {
			productConsumptionImpl.setUuid(uuid);
		}

		productConsumptionImpl.setProductConsumptionId(productConsumptionId);
		productConsumptionImpl.setCompanyId(companyId);
		productConsumptionImpl.setUserId(userId);

		if (createDate == Long.MIN_VALUE) {
			productConsumptionImpl.setCreateDate(null);
		}
		else {
			productConsumptionImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			productConsumptionImpl.setModifiedDate(null);
		}
		else {
			productConsumptionImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (productConsumptionKey == null) {
			productConsumptionImpl.setProductConsumptionKey("");
		}
		else {
			productConsumptionImpl.setProductConsumptionKey(
				productConsumptionKey);
		}

		productConsumptionImpl.setAccountId(accountId);
		productConsumptionImpl.setProductEntryId(productEntryId);

		productConsumptionImpl.resetOriginalValues();

		return productConsumptionImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		productConsumptionId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		productConsumptionKey = objectInput.readUTF();

		accountId = objectInput.readLong();

		productEntryId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(productConsumptionId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);
		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		if (productConsumptionKey == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(productConsumptionKey);
		}

		objectOutput.writeLong(accountId);

		objectOutput.writeLong(productEntryId);
	}

	public String uuid;
	public long productConsumptionId;
	public long companyId;
	public long userId;
	public long createDate;
	public long modifiedDate;
	public String productConsumptionKey;
	public long accountId;
	public long productEntryId;

}