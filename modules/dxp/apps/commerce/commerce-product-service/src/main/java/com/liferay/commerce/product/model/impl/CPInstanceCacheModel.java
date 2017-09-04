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

package com.liferay.commerce.product.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.model.CPInstance;

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
 * The cache model class for representing CPInstance in entity cache.
 *
 * @author Marco Leo
 * @see CPInstance
 * @generated
 */
@ProviderType
public class CPInstanceCacheModel implements CacheModel<CPInstance>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CPInstanceCacheModel)) {
			return false;
		}

		CPInstanceCacheModel cpInstanceCacheModel = (CPInstanceCacheModel)obj;

		if (CPInstanceId == cpInstanceCacheModel.CPInstanceId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, CPInstanceId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(63);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", CPInstanceId=");
		sb.append(CPInstanceId);
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
		sb.append(", CPDefinitionId=");
		sb.append(CPDefinitionId);
		sb.append(", sku=");
		sb.append(sku);
		sb.append(", gtin=");
		sb.append(gtin);
		sb.append(", manufacturerPartNumber=");
		sb.append(manufacturerPartNumber);
		sb.append(", DDMContent=");
		sb.append(DDMContent);
		sb.append(", overrideInventory=");
		sb.append(overrideInventory);
		sb.append(", minCartQuantity=");
		sb.append(minCartQuantity);
		sb.append(", maxCartQuantity=");
		sb.append(maxCartQuantity);
		sb.append(", allowedCartQuantities=");
		sb.append(allowedCartQuantities);
		sb.append(", multipleCartQuantity=");
		sb.append(multipleCartQuantity);
		sb.append(", width=");
		sb.append(width);
		sb.append(", height=");
		sb.append(height);
		sb.append(", depth=");
		sb.append(depth);
		sb.append(", weight=");
		sb.append(weight);
		sb.append(", cost=");
		sb.append(cost);
		sb.append(", price=");
		sb.append(price);
		sb.append(", displayDate=");
		sb.append(displayDate);
		sb.append(", expirationDate=");
		sb.append(expirationDate);
		sb.append(", lastPublishDate=");
		sb.append(lastPublishDate);
		sb.append(", status=");
		sb.append(status);
		sb.append(", statusByUserId=");
		sb.append(statusByUserId);
		sb.append(", statusByUserName=");
		sb.append(statusByUserName);
		sb.append(", statusDate=");
		sb.append(statusDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CPInstance toEntityModel() {
		CPInstanceImpl cpInstanceImpl = new CPInstanceImpl();

		if (uuid == null) {
			cpInstanceImpl.setUuid(StringPool.BLANK);
		}
		else {
			cpInstanceImpl.setUuid(uuid);
		}

		cpInstanceImpl.setCPInstanceId(CPInstanceId);
		cpInstanceImpl.setGroupId(groupId);
		cpInstanceImpl.setCompanyId(companyId);
		cpInstanceImpl.setUserId(userId);

		if (userName == null) {
			cpInstanceImpl.setUserName(StringPool.BLANK);
		}
		else {
			cpInstanceImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			cpInstanceImpl.setCreateDate(null);
		}
		else {
			cpInstanceImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			cpInstanceImpl.setModifiedDate(null);
		}
		else {
			cpInstanceImpl.setModifiedDate(new Date(modifiedDate));
		}

		cpInstanceImpl.setCPDefinitionId(CPDefinitionId);

		if (sku == null) {
			cpInstanceImpl.setSku(StringPool.BLANK);
		}
		else {
			cpInstanceImpl.setSku(sku);
		}

		if (gtin == null) {
			cpInstanceImpl.setGtin(StringPool.BLANK);
		}
		else {
			cpInstanceImpl.setGtin(gtin);
		}

		if (manufacturerPartNumber == null) {
			cpInstanceImpl.setManufacturerPartNumber(StringPool.BLANK);
		}
		else {
			cpInstanceImpl.setManufacturerPartNumber(manufacturerPartNumber);
		}

		if (DDMContent == null) {
			cpInstanceImpl.setDDMContent(StringPool.BLANK);
		}
		else {
			cpInstanceImpl.setDDMContent(DDMContent);
		}

		cpInstanceImpl.setOverrideInventory(overrideInventory);
		cpInstanceImpl.setMinCartQuantity(minCartQuantity);
		cpInstanceImpl.setMaxCartQuantity(maxCartQuantity);

		if (allowedCartQuantities == null) {
			cpInstanceImpl.setAllowedCartQuantities(StringPool.BLANK);
		}
		else {
			cpInstanceImpl.setAllowedCartQuantities(allowedCartQuantities);
		}

		cpInstanceImpl.setMultipleCartQuantity(multipleCartQuantity);
		cpInstanceImpl.setWidth(width);
		cpInstanceImpl.setHeight(height);
		cpInstanceImpl.setDepth(depth);
		cpInstanceImpl.setWeight(weight);
		cpInstanceImpl.setCost(cost);
		cpInstanceImpl.setPrice(price);

		if (displayDate == Long.MIN_VALUE) {
			cpInstanceImpl.setDisplayDate(null);
		}
		else {
			cpInstanceImpl.setDisplayDate(new Date(displayDate));
		}

		if (expirationDate == Long.MIN_VALUE) {
			cpInstanceImpl.setExpirationDate(null);
		}
		else {
			cpInstanceImpl.setExpirationDate(new Date(expirationDate));
		}

		if (lastPublishDate == Long.MIN_VALUE) {
			cpInstanceImpl.setLastPublishDate(null);
		}
		else {
			cpInstanceImpl.setLastPublishDate(new Date(lastPublishDate));
		}

		cpInstanceImpl.setStatus(status);
		cpInstanceImpl.setStatusByUserId(statusByUserId);

		if (statusByUserName == null) {
			cpInstanceImpl.setStatusByUserName(StringPool.BLANK);
		}
		else {
			cpInstanceImpl.setStatusByUserName(statusByUserName);
		}

		if (statusDate == Long.MIN_VALUE) {
			cpInstanceImpl.setStatusDate(null);
		}
		else {
			cpInstanceImpl.setStatusDate(new Date(statusDate));
		}

		cpInstanceImpl.resetOriginalValues();

		return cpInstanceImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		CPInstanceId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		CPDefinitionId = objectInput.readLong();
		sku = objectInput.readUTF();
		gtin = objectInput.readUTF();
		manufacturerPartNumber = objectInput.readUTF();
		DDMContent = objectInput.readUTF();

		overrideInventory = objectInput.readBoolean();

		minCartQuantity = objectInput.readInt();

		maxCartQuantity = objectInput.readInt();
		allowedCartQuantities = objectInput.readUTF();

		multipleCartQuantity = objectInput.readInt();

		width = objectInput.readDouble();

		height = objectInput.readDouble();

		depth = objectInput.readDouble();

		weight = objectInput.readDouble();

		cost = objectInput.readDouble();

		price = objectInput.readDouble();
		displayDate = objectInput.readLong();
		expirationDate = objectInput.readLong();
		lastPublishDate = objectInput.readLong();

		status = objectInput.readInt();

		statusByUserId = objectInput.readLong();
		statusByUserName = objectInput.readUTF();
		statusDate = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(CPInstanceId);

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

		objectOutput.writeLong(CPDefinitionId);

		if (sku == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(sku);
		}

		if (gtin == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(gtin);
		}

		if (manufacturerPartNumber == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(manufacturerPartNumber);
		}

		if (DDMContent == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(DDMContent);
		}

		objectOutput.writeBoolean(overrideInventory);

		objectOutput.writeInt(minCartQuantity);

		objectOutput.writeInt(maxCartQuantity);

		if (allowedCartQuantities == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(allowedCartQuantities);
		}

		objectOutput.writeInt(multipleCartQuantity);

		objectOutput.writeDouble(width);

		objectOutput.writeDouble(height);

		objectOutput.writeDouble(depth);

		objectOutput.writeDouble(weight);

		objectOutput.writeDouble(cost);

		objectOutput.writeDouble(price);
		objectOutput.writeLong(displayDate);
		objectOutput.writeLong(expirationDate);
		objectOutput.writeLong(lastPublishDate);

		objectOutput.writeInt(status);

		objectOutput.writeLong(statusByUserId);

		if (statusByUserName == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(statusByUserName);
		}

		objectOutput.writeLong(statusDate);
	}

	public String uuid;
	public long CPInstanceId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long CPDefinitionId;
	public String sku;
	public String gtin;
	public String manufacturerPartNumber;
	public String DDMContent;
	public boolean overrideInventory;
	public int minCartQuantity;
	public int maxCartQuantity;
	public String allowedCartQuantities;
	public int multipleCartQuantity;
	public double width;
	public double height;
	public double depth;
	public double weight;
	public double cost;
	public double price;
	public long displayDate;
	public long expirationDate;
	public long lastPublishDate;
	public int status;
	public long statusByUserId;
	public String statusByUserName;
	public long statusDate;
}