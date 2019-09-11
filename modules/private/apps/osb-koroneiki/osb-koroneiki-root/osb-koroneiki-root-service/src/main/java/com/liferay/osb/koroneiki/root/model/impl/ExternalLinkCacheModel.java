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

package com.liferay.osb.koroneiki.root.model.impl;

import com.liferay.osb.koroneiki.root.model.ExternalLink;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing ExternalLink in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class ExternalLinkCacheModel
	implements CacheModel<ExternalLink>, Externalizable {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ExternalLinkCacheModel)) {
			return false;
		}

		ExternalLinkCacheModel externalLinkCacheModel =
			(ExternalLinkCacheModel)obj;

		if (externalLinkId == externalLinkCacheModel.externalLinkId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, externalLinkId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(21);

		sb.append("{externalLinkId=");
		sb.append(externalLinkId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", externalLinkKey=");
		sb.append(externalLinkKey);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", domain=");
		sb.append(domain);
		sb.append(", entityName=");
		sb.append(entityName);
		sb.append(", entityId=");
		sb.append(entityId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public ExternalLink toEntityModel() {
		ExternalLinkImpl externalLinkImpl = new ExternalLinkImpl();

		externalLinkImpl.setExternalLinkId(externalLinkId);
		externalLinkImpl.setCompanyId(companyId);

		if (createDate == Long.MIN_VALUE) {
			externalLinkImpl.setCreateDate(null);
		}
		else {
			externalLinkImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			externalLinkImpl.setModifiedDate(null);
		}
		else {
			externalLinkImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (externalLinkKey == null) {
			externalLinkImpl.setExternalLinkKey("");
		}
		else {
			externalLinkImpl.setExternalLinkKey(externalLinkKey);
		}

		externalLinkImpl.setClassNameId(classNameId);
		externalLinkImpl.setClassPK(classPK);

		if (domain == null) {
			externalLinkImpl.setDomain("");
		}
		else {
			externalLinkImpl.setDomain(domain);
		}

		if (entityName == null) {
			externalLinkImpl.setEntityName("");
		}
		else {
			externalLinkImpl.setEntityName(entityName);
		}

		if (entityId == null) {
			externalLinkImpl.setEntityId("");
		}
		else {
			externalLinkImpl.setEntityId(entityId);
		}

		externalLinkImpl.resetOriginalValues();

		return externalLinkImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		externalLinkId = objectInput.readLong();

		companyId = objectInput.readLong();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		externalLinkKey = objectInput.readUTF();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();
		domain = objectInput.readUTF();
		entityName = objectInput.readUTF();
		entityId = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(externalLinkId);

		objectOutput.writeLong(companyId);
		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		if (externalLinkKey == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(externalLinkKey);
		}

		objectOutput.writeLong(classNameId);

		objectOutput.writeLong(classPK);

		if (domain == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(domain);
		}

		if (entityName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(entityName);
		}

		if (entityId == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(entityId);
		}
	}

	public long externalLinkId;
	public long companyId;
	public long createDate;
	public long modifiedDate;
	public String externalLinkKey;
	public long classNameId;
	public long classPK;
	public String domain;
	public String entityName;
	public String entityId;

}