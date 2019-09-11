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

import com.liferay.osb.koroneiki.trunk.model.ProductField;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The cache model class for representing ProductField in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public class ProductFieldCacheModel
	implements CacheModel<ProductField>, Externalizable {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ProductFieldCacheModel)) {
			return false;
		}

		ProductFieldCacheModel productFieldCacheModel =
			(ProductFieldCacheModel)obj;

		if (productFieldId == productFieldCacheModel.productFieldId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, productFieldId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(15);

		sb.append("{productFieldId=");
		sb.append(productFieldId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", name=");
		sb.append(name);
		sb.append(", value=");
		sb.append(value);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public ProductField toEntityModel() {
		ProductFieldImpl productFieldImpl = new ProductFieldImpl();

		productFieldImpl.setProductFieldId(productFieldId);
		productFieldImpl.setCompanyId(companyId);
		productFieldImpl.setUserId(userId);
		productFieldImpl.setClassNameId(classNameId);
		productFieldImpl.setClassPK(classPK);

		if (name == null) {
			productFieldImpl.setName("");
		}
		else {
			productFieldImpl.setName(name);
		}

		if (value == null) {
			productFieldImpl.setValue("");
		}
		else {
			productFieldImpl.setValue(value);
		}

		productFieldImpl.resetOriginalValues();

		return productFieldImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		productFieldId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();
		name = objectInput.readUTF();
		value = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(productFieldId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);

		objectOutput.writeLong(classNameId);

		objectOutput.writeLong(classPK);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (value == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(value);
		}
	}

	public long productFieldId;
	public long companyId;
	public long userId;
	public long classNameId;
	public long classPK;
	public String name;
	public String value;

}