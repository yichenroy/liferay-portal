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

import com.liferay.osb.koroneiki.scion.model.ServiceProducer;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The cache model class for representing ServiceProducer in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public class ServiceProducerCacheModel
	implements CacheModel<ServiceProducer>, Externalizable {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ServiceProducerCacheModel)) {
			return false;
		}

		ServiceProducerCacheModel serviceProducerCacheModel =
			(ServiceProducerCacheModel)obj;

		if (serviceProducerId == serviceProducerCacheModel.serviceProducerId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, serviceProducerId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(15);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", serviceProducerId=");
		sb.append(serviceProducerId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", authorizationUserId=");
		sb.append(authorizationUserId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", description=");
		sb.append(description);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public ServiceProducer toEntityModel() {
		ServiceProducerImpl serviceProducerImpl = new ServiceProducerImpl();

		if (uuid == null) {
			serviceProducerImpl.setUuid("");
		}
		else {
			serviceProducerImpl.setUuid(uuid);
		}

		serviceProducerImpl.setServiceProducerId(serviceProducerId);
		serviceProducerImpl.setCompanyId(companyId);
		serviceProducerImpl.setUserId(userId);
		serviceProducerImpl.setAuthorizationUserId(authorizationUserId);

		if (name == null) {
			serviceProducerImpl.setName("");
		}
		else {
			serviceProducerImpl.setName(name);
		}

		if (description == null) {
			serviceProducerImpl.setDescription("");
		}
		else {
			serviceProducerImpl.setDescription(description);
		}

		serviceProducerImpl.resetOriginalValues();

		return serviceProducerImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		serviceProducerId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();

		authorizationUserId = objectInput.readLong();
		name = objectInput.readUTF();
		description = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(serviceProducerId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);

		objectOutput.writeLong(authorizationUserId);

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
	}

	public String uuid;
	public long serviceProducerId;
	public long companyId;
	public long userId;
	public long authorizationUserId;
	public String name;
	public String description;

}