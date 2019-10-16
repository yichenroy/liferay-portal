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

package com.liferay.portal.reports.engine.console.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.reports.engine.console.model.Entry;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing Entry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see Entry
 * @generated
 */
@ProviderType
public class EntryCacheModel implements CacheModel<Entry>, Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof EntryCacheModel)) {
			return false;
		}

		EntryCacheModel entryCacheModel = (EntryCacheModel)obj;

		if (entryId == entryCacheModel.entryId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, entryId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(43);

		sb.append("{entryId=");
		sb.append(entryId);
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
		sb.append(", definitionId=");
		sb.append(definitionId);
		sb.append(", format=");
		sb.append(format);
		sb.append(", scheduleRequest=");
		sb.append(scheduleRequest);
		sb.append(", startDate=");
		sb.append(startDate);
		sb.append(", endDate=");
		sb.append(endDate);
		sb.append(", repeating=");
		sb.append(repeating);
		sb.append(", recurrence=");
		sb.append(recurrence);
		sb.append(", emailNotifications=");
		sb.append(emailNotifications);
		sb.append(", emailDelivery=");
		sb.append(emailDelivery);
		sb.append(", portletId=");
		sb.append(portletId);
		sb.append(", pageURL=");
		sb.append(pageURL);
		sb.append(", reportParameters=");
		sb.append(reportParameters);
		sb.append(", status=");
		sb.append(status);
		sb.append(", errorMessage=");
		sb.append(errorMessage);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public Entry toEntityModel() {
		EntryImpl entryImpl = new EntryImpl();

		entryImpl.setEntryId(entryId);
		entryImpl.setGroupId(groupId);
		entryImpl.setCompanyId(companyId);
		entryImpl.setUserId(userId);

		if (userName == null) {
			entryImpl.setUserName(StringPool.BLANK);
		}
		else {
			entryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			entryImpl.setCreateDate(null);
		}
		else {
			entryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			entryImpl.setModifiedDate(null);
		}
		else {
			entryImpl.setModifiedDate(new Date(modifiedDate));
		}

		entryImpl.setDefinitionId(definitionId);

		if (format == null) {
			entryImpl.setFormat(StringPool.BLANK);
		}
		else {
			entryImpl.setFormat(format);
		}

		entryImpl.setScheduleRequest(scheduleRequest);

		if (startDate == Long.MIN_VALUE) {
			entryImpl.setStartDate(null);
		}
		else {
			entryImpl.setStartDate(new Date(startDate));
		}

		if (endDate == Long.MIN_VALUE) {
			entryImpl.setEndDate(null);
		}
		else {
			entryImpl.setEndDate(new Date(endDate));
		}

		entryImpl.setRepeating(repeating);

		if (recurrence == null) {
			entryImpl.setRecurrence(StringPool.BLANK);
		}
		else {
			entryImpl.setRecurrence(recurrence);
		}

		if (emailNotifications == null) {
			entryImpl.setEmailNotifications(StringPool.BLANK);
		}
		else {
			entryImpl.setEmailNotifications(emailNotifications);
		}

		if (emailDelivery == null) {
			entryImpl.setEmailDelivery(StringPool.BLANK);
		}
		else {
			entryImpl.setEmailDelivery(emailDelivery);
		}

		if (portletId == null) {
			entryImpl.setPortletId(StringPool.BLANK);
		}
		else {
			entryImpl.setPortletId(portletId);
		}

		if (pageURL == null) {
			entryImpl.setPageURL(StringPool.BLANK);
		}
		else {
			entryImpl.setPageURL(pageURL);
		}

		if (reportParameters == null) {
			entryImpl.setReportParameters(StringPool.BLANK);
		}
		else {
			entryImpl.setReportParameters(reportParameters);
		}

		if (status == null) {
			entryImpl.setStatus(StringPool.BLANK);
		}
		else {
			entryImpl.setStatus(status);
		}

		if (errorMessage == null) {
			entryImpl.setErrorMessage(StringPool.BLANK);
		}
		else {
			entryImpl.setErrorMessage(errorMessage);
		}

		entryImpl.resetOriginalValues();

		return entryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		entryId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		definitionId = objectInput.readLong();
		format = objectInput.readUTF();

		scheduleRequest = objectInput.readBoolean();
		startDate = objectInput.readLong();
		endDate = objectInput.readLong();

		repeating = objectInput.readBoolean();
		recurrence = objectInput.readUTF();
		emailNotifications = objectInput.readUTF();
		emailDelivery = objectInput.readUTF();
		portletId = objectInput.readUTF();
		pageURL = objectInput.readUTF();
		reportParameters = objectInput.readUTF();
		status = objectInput.readUTF();
		errorMessage = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(entryId);

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

		objectOutput.writeLong(definitionId);

		if (format == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(format);
		}

		objectOutput.writeBoolean(scheduleRequest);
		objectOutput.writeLong(startDate);
		objectOutput.writeLong(endDate);

		objectOutput.writeBoolean(repeating);

		if (recurrence == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(recurrence);
		}

		if (emailNotifications == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(emailNotifications);
		}

		if (emailDelivery == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(emailDelivery);
		}

		if (portletId == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(portletId);
		}

		if (pageURL == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(pageURL);
		}

		if (reportParameters == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(reportParameters);
		}

		if (status == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(status);
		}

		if (errorMessage == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(errorMessage);
		}
	}

	public long entryId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long definitionId;
	public String format;
	public boolean scheduleRequest;
	public long startDate;
	public long endDate;
	public boolean repeating;
	public String recurrence;
	public String emailNotifications;
	public String emailDelivery;
	public String portletId;
	public String pageURL;
	public String reportParameters;
	public String status;
	public String errorMessage;
}