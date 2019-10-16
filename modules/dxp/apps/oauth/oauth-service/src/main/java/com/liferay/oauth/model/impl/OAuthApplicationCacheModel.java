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

package com.liferay.oauth.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.oauth.model.OAuthApplication;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;

import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing OAuthApplication in entity cache.
 *
 * @author Ivica Cardic
 * @generated
 */
@ProviderType
public class OAuthApplicationCacheModel implements CacheModel<OAuthApplication>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof OAuthApplicationCacheModel)) {
			return false;
		}

		OAuthApplicationCacheModel oAuthApplicationCacheModel = (OAuthApplicationCacheModel)obj;

		if (oAuthApplicationId == oAuthApplicationCacheModel.oAuthApplicationId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, oAuthApplicationId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(31);

		sb.append("{oAuthApplicationId=");
		sb.append(oAuthApplicationId);
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
		sb.append(", description=");
		sb.append(description);
		sb.append(", consumerKey=");
		sb.append(consumerKey);
		sb.append(", consumerSecret=");
		sb.append(consumerSecret);
		sb.append(", accessLevel=");
		sb.append(accessLevel);
		sb.append(", logoId=");
		sb.append(logoId);
		sb.append(", shareableAccessToken=");
		sb.append(shareableAccessToken);
		sb.append(", callbackURI=");
		sb.append(callbackURI);
		sb.append(", websiteURL=");
		sb.append(websiteURL);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public OAuthApplication toEntityModel() {
		OAuthApplicationImpl oAuthApplicationImpl = new OAuthApplicationImpl();

		oAuthApplicationImpl.setOAuthApplicationId(oAuthApplicationId);
		oAuthApplicationImpl.setCompanyId(companyId);
		oAuthApplicationImpl.setUserId(userId);

		if (userName == null) {
			oAuthApplicationImpl.setUserName("");
		}
		else {
			oAuthApplicationImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			oAuthApplicationImpl.setCreateDate(null);
		}
		else {
			oAuthApplicationImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			oAuthApplicationImpl.setModifiedDate(null);
		}
		else {
			oAuthApplicationImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (name == null) {
			oAuthApplicationImpl.setName("");
		}
		else {
			oAuthApplicationImpl.setName(name);
		}

		if (description == null) {
			oAuthApplicationImpl.setDescription("");
		}
		else {
			oAuthApplicationImpl.setDescription(description);
		}

		if (consumerKey == null) {
			oAuthApplicationImpl.setConsumerKey("");
		}
		else {
			oAuthApplicationImpl.setConsumerKey(consumerKey);
		}

		if (consumerSecret == null) {
			oAuthApplicationImpl.setConsumerSecret("");
		}
		else {
			oAuthApplicationImpl.setConsumerSecret(consumerSecret);
		}

		oAuthApplicationImpl.setAccessLevel(accessLevel);
		oAuthApplicationImpl.setLogoId(logoId);
		oAuthApplicationImpl.setShareableAccessToken(shareableAccessToken);

		if (callbackURI == null) {
			oAuthApplicationImpl.setCallbackURI("");
		}
		else {
			oAuthApplicationImpl.setCallbackURI(callbackURI);
		}

		if (websiteURL == null) {
			oAuthApplicationImpl.setWebsiteURL("");
		}
		else {
			oAuthApplicationImpl.setWebsiteURL(websiteURL);
		}

		oAuthApplicationImpl.resetOriginalValues();

		return oAuthApplicationImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		oAuthApplicationId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		name = objectInput.readUTF();
		description = objectInput.readUTF();
		consumerKey = objectInput.readUTF();
		consumerSecret = objectInput.readUTF();

		accessLevel = objectInput.readInt();

		logoId = objectInput.readLong();

		shareableAccessToken = objectInput.readBoolean();
		callbackURI = objectInput.readUTF();
		websiteURL = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(oAuthApplicationId);

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

		if (consumerKey == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(consumerKey);
		}

		if (consumerSecret == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(consumerSecret);
		}

		objectOutput.writeInt(accessLevel);

		objectOutput.writeLong(logoId);

		objectOutput.writeBoolean(shareableAccessToken);

		if (callbackURI == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(callbackURI);
		}

		if (websiteURL == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(websiteURL);
		}
	}

	public long oAuthApplicationId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String name;
	public String description;
	public String consumerKey;
	public String consumerSecret;
	public int accessLevel;
	public long logoId;
	public boolean shareableAccessToken;
	public String callbackURI;
	public String websiteURL;
}