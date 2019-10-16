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

package com.liferay.commerce.forecast.model;

import aQute.bnd.annotation.ProviderType;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Andrea Di Giorgi
 * @generated
 */
@ProviderType
public class CommerceForecastEntrySoap implements Serializable {
	public static CommerceForecastEntrySoap toSoapModel(
		CommerceForecastEntry model) {
		CommerceForecastEntrySoap soapModel = new CommerceForecastEntrySoap();

		soapModel.setCommerceForecastEntryId(model.getCommerceForecastEntryId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setTime(model.getTime());
		soapModel.setPeriod(model.getPeriod());
		soapModel.setTarget(model.getTarget());
		soapModel.setCustomerId(model.getCustomerId());
		soapModel.setCPInstanceId(model.getCPInstanceId());
		soapModel.setAssertivity(model.getAssertivity());

		return soapModel;
	}

	public static CommerceForecastEntrySoap[] toSoapModels(
		CommerceForecastEntry[] models) {
		CommerceForecastEntrySoap[] soapModels = new CommerceForecastEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CommerceForecastEntrySoap[][] toSoapModels(
		CommerceForecastEntry[][] models) {
		CommerceForecastEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CommerceForecastEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new CommerceForecastEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CommerceForecastEntrySoap[] toSoapModels(
		List<CommerceForecastEntry> models) {
		List<CommerceForecastEntrySoap> soapModels = new ArrayList<CommerceForecastEntrySoap>(models.size());

		for (CommerceForecastEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CommerceForecastEntrySoap[soapModels.size()]);
	}

	public CommerceForecastEntrySoap() {
	}

	public long getPrimaryKey() {
		return _commerceForecastEntryId;
	}

	public void setPrimaryKey(long pk) {
		setCommerceForecastEntryId(pk);
	}

	public long getCommerceForecastEntryId() {
		return _commerceForecastEntryId;
	}

	public void setCommerceForecastEntryId(long commerceForecastEntryId) {
		_commerceForecastEntryId = commerceForecastEntryId;
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

	public long getTime() {
		return _time;
	}

	public void setTime(long time) {
		_time = time;
	}

	public int getPeriod() {
		return _period;
	}

	public void setPeriod(int period) {
		_period = period;
	}

	public int getTarget() {
		return _target;
	}

	public void setTarget(int target) {
		_target = target;
	}

	public long getCustomerId() {
		return _customerId;
	}

	public void setCustomerId(long customerId) {
		_customerId = customerId;
	}

	public long getCPInstanceId() {
		return _CPInstanceId;
	}

	public void setCPInstanceId(long CPInstanceId) {
		_CPInstanceId = CPInstanceId;
	}

	public BigDecimal getAssertivity() {
		return _assertivity;
	}

	public void setAssertivity(BigDecimal assertivity) {
		_assertivity = assertivity;
	}

	private long _commerceForecastEntryId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _time;
	private int _period;
	private int _target;
	private long _customerId;
	private long _CPInstanceId;
	private BigDecimal _assertivity;
}