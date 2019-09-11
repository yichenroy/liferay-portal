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

package com.liferay.osb.koroneiki.scion.model.impl;

import com.liferay.osb.koroneiki.scion.model.AuthenticationToken;
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
 * The cache model class for representing AuthenticationToken in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public class AuthenticationTokenCacheModel
	implements CacheModel<AuthenticationToken>, Externalizable {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof AuthenticationTokenCacheModel)) {
			return false;
		}

		AuthenticationTokenCacheModel authenticationTokenCacheModel =
			(AuthenticationTokenCacheModel)obj;

		if (authenticationTokenId ==
				authenticationTokenCacheModel.authenticationTokenId) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, authenticationTokenId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(21);

		sb.append("{authenticationTokenId=");
		sb.append(authenticationTokenId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", serviceProducerId=");
		sb.append(serviceProducerId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", prefix=");
		sb.append(prefix);
		sb.append(", digest=");
		sb.append(digest);
		sb.append(", status=");
		sb.append(status);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public AuthenticationToken toEntityModel() {
		AuthenticationTokenImpl authenticationTokenImpl =
			new AuthenticationTokenImpl();

		authenticationTokenImpl.setAuthenticationTokenId(authenticationTokenId);
		authenticationTokenImpl.setCompanyId(companyId);
		authenticationTokenImpl.setUserId(userId);

		if (createDate == Long.MIN_VALUE) {
			authenticationTokenImpl.setCreateDate(null);
		}
		else {
			authenticationTokenImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			authenticationTokenImpl.setModifiedDate(null);
		}
		else {
			authenticationTokenImpl.setModifiedDate(new Date(modifiedDate));
		}

		authenticationTokenImpl.setServiceProducerId(serviceProducerId);

		if (name == null) {
			authenticationTokenImpl.setName("");
		}
		else {
			authenticationTokenImpl.setName(name);
		}

		if (prefix == null) {
			authenticationTokenImpl.setPrefix("");
		}
		else {
			authenticationTokenImpl.setPrefix(prefix);
		}

		if (digest == null) {
			authenticationTokenImpl.setDigest("");
		}
		else {
			authenticationTokenImpl.setDigest(digest);
		}

		authenticationTokenImpl.setStatus(status);

		authenticationTokenImpl.resetOriginalValues();

		return authenticationTokenImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		authenticationTokenId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		serviceProducerId = objectInput.readLong();
		name = objectInput.readUTF();
		prefix = objectInput.readUTF();
		digest = objectInput.readUTF();

		status = objectInput.readInt();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(authenticationTokenId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);
		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		objectOutput.writeLong(serviceProducerId);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (prefix == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(prefix);
		}

		if (digest == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(digest);
		}

		objectOutput.writeInt(status);
	}

	public long authenticationTokenId;
	public long companyId;
	public long userId;
	public long createDate;
	public long modifiedDate;
	public long serviceProducerId;
	public String name;
	public String prefix;
	public String digest;
	public int status;

}