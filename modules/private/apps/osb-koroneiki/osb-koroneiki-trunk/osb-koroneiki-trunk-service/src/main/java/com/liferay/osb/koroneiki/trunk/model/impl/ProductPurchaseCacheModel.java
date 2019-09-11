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

import com.liferay.osb.koroneiki.trunk.model.ProductPurchase;
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
 * The cache model class for representing ProductPurchase in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public class ProductPurchaseCacheModel
	implements CacheModel<ProductPurchase>, Externalizable {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ProductPurchaseCacheModel)) {
			return false;
		}

		ProductPurchaseCacheModel productPurchaseCacheModel =
			(ProductPurchaseCacheModel)obj;

		if (productPurchaseId == productPurchaseCacheModel.productPurchaseId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, productPurchaseId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(25);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", productPurchaseId=");
		sb.append(productPurchaseId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", productPurchaseKey=");
		sb.append(productPurchaseKey);
		sb.append(", accountId=");
		sb.append(accountId);
		sb.append(", productEntryId=");
		sb.append(productEntryId);
		sb.append(", startDate=");
		sb.append(startDate);
		sb.append(", endDate=");
		sb.append(endDate);
		sb.append(", quantity=");
		sb.append(quantity);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public ProductPurchase toEntityModel() {
		ProductPurchaseImpl productPurchaseImpl = new ProductPurchaseImpl();

		if (uuid == null) {
			productPurchaseImpl.setUuid("");
		}
		else {
			productPurchaseImpl.setUuid(uuid);
		}

		productPurchaseImpl.setProductPurchaseId(productPurchaseId);
		productPurchaseImpl.setCompanyId(companyId);
		productPurchaseImpl.setUserId(userId);

		if (createDate == Long.MIN_VALUE) {
			productPurchaseImpl.setCreateDate(null);
		}
		else {
			productPurchaseImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			productPurchaseImpl.setModifiedDate(null);
		}
		else {
			productPurchaseImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (productPurchaseKey == null) {
			productPurchaseImpl.setProductPurchaseKey("");
		}
		else {
			productPurchaseImpl.setProductPurchaseKey(productPurchaseKey);
		}

		productPurchaseImpl.setAccountId(accountId);
		productPurchaseImpl.setProductEntryId(productEntryId);

		if (startDate == Long.MIN_VALUE) {
			productPurchaseImpl.setStartDate(null);
		}
		else {
			productPurchaseImpl.setStartDate(new Date(startDate));
		}

		if (endDate == Long.MIN_VALUE) {
			productPurchaseImpl.setEndDate(null);
		}
		else {
			productPurchaseImpl.setEndDate(new Date(endDate));
		}

		productPurchaseImpl.setQuantity(quantity);

		productPurchaseImpl.resetOriginalValues();

		return productPurchaseImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		productPurchaseId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		productPurchaseKey = objectInput.readUTF();

		accountId = objectInput.readLong();

		productEntryId = objectInput.readLong();
		startDate = objectInput.readLong();
		endDate = objectInput.readLong();

		quantity = objectInput.readInt();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(productPurchaseId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);
		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		if (productPurchaseKey == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(productPurchaseKey);
		}

		objectOutput.writeLong(accountId);

		objectOutput.writeLong(productEntryId);
		objectOutput.writeLong(startDate);
		objectOutput.writeLong(endDate);

		objectOutput.writeInt(quantity);
	}

	public String uuid;
	public long productPurchaseId;
	public long companyId;
	public long userId;
	public long createDate;
	public long modifiedDate;
	public String productPurchaseKey;
	public long accountId;
	public long productEntryId;
	public long startDate;
	public long endDate;
	public int quantity;

}