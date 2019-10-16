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

package com.liferay.commerce.product.type.grouped.model;

import aQute.bnd.annotation.ProviderType;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.product.type.grouped.service.http.CPDefinitionGroupedEntryServiceSoap}.
 *
 * @author Andrea Di Giorgi
 * @see com.liferay.commerce.product.type.grouped.service.http.CPDefinitionGroupedEntryServiceSoap
 * @generated
 */
@ProviderType
public class CPDefinitionGroupedEntrySoap implements Serializable {
	public static CPDefinitionGroupedEntrySoap toSoapModel(
		CPDefinitionGroupedEntry model) {
		CPDefinitionGroupedEntrySoap soapModel = new CPDefinitionGroupedEntrySoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setCPDefinitionGroupedEntryId(model.getCPDefinitionGroupedEntryId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setCPDefinitionId(model.getCPDefinitionId());
		soapModel.setEntryCPDefinitionId(model.getEntryCPDefinitionId());
		soapModel.setPriority(model.getPriority());
		soapModel.setQuantity(model.getQuantity());

		return soapModel;
	}

	public static CPDefinitionGroupedEntrySoap[] toSoapModels(
		CPDefinitionGroupedEntry[] models) {
		CPDefinitionGroupedEntrySoap[] soapModels = new CPDefinitionGroupedEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CPDefinitionGroupedEntrySoap[][] toSoapModels(
		CPDefinitionGroupedEntry[][] models) {
		CPDefinitionGroupedEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CPDefinitionGroupedEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new CPDefinitionGroupedEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CPDefinitionGroupedEntrySoap[] toSoapModels(
		List<CPDefinitionGroupedEntry> models) {
		List<CPDefinitionGroupedEntrySoap> soapModels = new ArrayList<CPDefinitionGroupedEntrySoap>(models.size());

		for (CPDefinitionGroupedEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CPDefinitionGroupedEntrySoap[soapModels.size()]);
	}

	public CPDefinitionGroupedEntrySoap() {
	}

	public long getPrimaryKey() {
		return _CPDefinitionGroupedEntryId;
	}

	public void setPrimaryKey(long pk) {
		setCPDefinitionGroupedEntryId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getCPDefinitionGroupedEntryId() {
		return _CPDefinitionGroupedEntryId;
	}

	public void setCPDefinitionGroupedEntryId(long CPDefinitionGroupedEntryId) {
		_CPDefinitionGroupedEntryId = CPDefinitionGroupedEntryId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public String getUserName() {
		return _userName;
	}

	public void setUserName(String userName) {
		_userName = userName;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public long getCPDefinitionId() {
		return _CPDefinitionId;
	}

	public void setCPDefinitionId(long CPDefinitionId) {
		_CPDefinitionId = CPDefinitionId;
	}

	public long getEntryCPDefinitionId() {
		return _entryCPDefinitionId;
	}

	public void setEntryCPDefinitionId(long entryCPDefinitionId) {
		_entryCPDefinitionId = entryCPDefinitionId;
	}

	public double getPriority() {
		return _priority;
	}

	public void setPriority(double priority) {
		_priority = priority;
	}

	public int getQuantity() {
		return _quantity;
	}

	public void setQuantity(int quantity) {
		_quantity = quantity;
	}

	private String _uuid;
	private long _CPDefinitionGroupedEntryId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _CPDefinitionId;
	private long _entryCPDefinitionId;
	private double _priority;
	private int _quantity;
}