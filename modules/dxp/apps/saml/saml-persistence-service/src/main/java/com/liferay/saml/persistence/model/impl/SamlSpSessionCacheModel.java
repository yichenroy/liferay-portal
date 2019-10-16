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

package com.liferay.saml.persistence.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.saml.persistence.model.SamlSpSession;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing SamlSpSession in entity cache.
 *
 * @author Mika Koivisto
 * @generated
 */
public class SamlSpSessionCacheModel
	implements CacheModel<SamlSpSession>, Externalizable {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof SamlSpSessionCacheModel)) {
			return false;
		}

		SamlSpSessionCacheModel samlSpSessionCacheModel =
			(SamlSpSessionCacheModel)obj;

		if (samlSpSessionId == samlSpSessionCacheModel.samlSpSessionId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, samlSpSessionId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(33);

		sb.append("{samlSpSessionId=");
		sb.append(samlSpSessionId);
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
		sb.append(", samlIdpEntityId=");
		sb.append(samlIdpEntityId);
		sb.append(", samlSpSessionKey=");
		sb.append(samlSpSessionKey);
		sb.append(", assertionXml=");
		sb.append(assertionXml);
		sb.append(", jSessionId=");
		sb.append(jSessionId);
		sb.append(", nameIdFormat=");
		sb.append(nameIdFormat);
		sb.append(", nameIdNameQualifier=");
		sb.append(nameIdNameQualifier);
		sb.append(", nameIdSPNameQualifier=");
		sb.append(nameIdSPNameQualifier);
		sb.append(", nameIdValue=");
		sb.append(nameIdValue);
		sb.append(", sessionIndex=");
		sb.append(sessionIndex);
		sb.append(", terminated=");
		sb.append(terminated);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public SamlSpSession toEntityModel() {
		SamlSpSessionImpl samlSpSessionImpl = new SamlSpSessionImpl();

		samlSpSessionImpl.setSamlSpSessionId(samlSpSessionId);
		samlSpSessionImpl.setCompanyId(companyId);
		samlSpSessionImpl.setUserId(userId);

		if (userName == null) {
			samlSpSessionImpl.setUserName("");
		}
		else {
			samlSpSessionImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			samlSpSessionImpl.setCreateDate(null);
		}
		else {
			samlSpSessionImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			samlSpSessionImpl.setModifiedDate(null);
		}
		else {
			samlSpSessionImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (samlIdpEntityId == null) {
			samlSpSessionImpl.setSamlIdpEntityId("");
		}
		else {
			samlSpSessionImpl.setSamlIdpEntityId(samlIdpEntityId);
		}

		if (samlSpSessionKey == null) {
			samlSpSessionImpl.setSamlSpSessionKey("");
		}
		else {
			samlSpSessionImpl.setSamlSpSessionKey(samlSpSessionKey);
		}

		if (assertionXml == null) {
			samlSpSessionImpl.setAssertionXml("");
		}
		else {
			samlSpSessionImpl.setAssertionXml(assertionXml);
		}

		if (jSessionId == null) {
			samlSpSessionImpl.setJSessionId("");
		}
		else {
			samlSpSessionImpl.setJSessionId(jSessionId);
		}

		if (nameIdFormat == null) {
			samlSpSessionImpl.setNameIdFormat("");
		}
		else {
			samlSpSessionImpl.setNameIdFormat(nameIdFormat);
		}

		if (nameIdNameQualifier == null) {
			samlSpSessionImpl.setNameIdNameQualifier("");
		}
		else {
			samlSpSessionImpl.setNameIdNameQualifier(nameIdNameQualifier);
		}

		if (nameIdSPNameQualifier == null) {
			samlSpSessionImpl.setNameIdSPNameQualifier("");
		}
		else {
			samlSpSessionImpl.setNameIdSPNameQualifier(nameIdSPNameQualifier);
		}

		if (nameIdValue == null) {
			samlSpSessionImpl.setNameIdValue("");
		}
		else {
			samlSpSessionImpl.setNameIdValue(nameIdValue);
		}

		if (sessionIndex == null) {
			samlSpSessionImpl.setSessionIndex("");
		}
		else {
			samlSpSessionImpl.setSessionIndex(sessionIndex);
		}

		samlSpSessionImpl.setTerminated(terminated);

		samlSpSessionImpl.resetOriginalValues();

		return samlSpSessionImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		samlSpSessionId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		samlIdpEntityId = objectInput.readUTF();
		samlSpSessionKey = objectInput.readUTF();
		assertionXml = objectInput.readUTF();
		jSessionId = objectInput.readUTF();
		nameIdFormat = objectInput.readUTF();
		nameIdNameQualifier = objectInput.readUTF();
		nameIdSPNameQualifier = objectInput.readUTF();
		nameIdValue = objectInput.readUTF();
		sessionIndex = objectInput.readUTF();

		terminated = objectInput.readBoolean();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(samlSpSessionId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		if (samlIdpEntityId == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(samlIdpEntityId);
		}

		if (samlSpSessionKey == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(samlSpSessionKey);
		}

		if (assertionXml == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(assertionXml);
		}

		if (jSessionId == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(jSessionId);
		}

		if (nameIdFormat == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(nameIdFormat);
		}

		if (nameIdNameQualifier == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(nameIdNameQualifier);
		}

		if (nameIdSPNameQualifier == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(nameIdSPNameQualifier);
		}

		if (nameIdValue == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(nameIdValue);
		}

		if (sessionIndex == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(sessionIndex);
		}

		objectOutput.writeBoolean(terminated);
	}

	public long samlSpSessionId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String samlIdpEntityId;
	public String samlSpSessionKey;
	public String assertionXml;
	public String jSessionId;
	public String nameIdFormat;
	public String nameIdNameQualifier;
	public String nameIdSPNameQualifier;
	public String nameIdValue;
	public String sessionIndex;
	public boolean terminated;

}