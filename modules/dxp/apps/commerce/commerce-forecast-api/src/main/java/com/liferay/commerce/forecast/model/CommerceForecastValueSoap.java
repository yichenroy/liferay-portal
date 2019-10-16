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
public class CommerceForecastValueSoap implements Serializable {
	public static CommerceForecastValueSoap toSoapModel(
		CommerceForecastValue model) {
		CommerceForecastValueSoap soapModel = new CommerceForecastValueSoap();

		soapModel.setCommerceForecastValueId(model.getCommerceForecastValueId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setCommerceForecastEntryId(model.getCommerceForecastEntryId());
		soapModel.setTime(model.getTime());
		soapModel.setLowerValue(model.getLowerValue());
		soapModel.setValue(model.getValue());
		soapModel.setUpperValue(model.getUpperValue());

		return soapModel;
	}

	public static CommerceForecastValueSoap[] toSoapModels(
		CommerceForecastValue[] models) {
		CommerceForecastValueSoap[] soapModels = new CommerceForecastValueSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CommerceForecastValueSoap[][] toSoapModels(
		CommerceForecastValue[][] models) {
		CommerceForecastValueSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CommerceForecastValueSoap[models.length][models[0].length];
		}
		else {
			soapModels = new CommerceForecastValueSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CommerceForecastValueSoap[] toSoapModels(
		List<CommerceForecastValue> models) {
		List<CommerceForecastValueSoap> soapModels = new ArrayList<CommerceForecastValueSoap>(models.size());

		for (CommerceForecastValue model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CommerceForecastValueSoap[soapModels.size()]);
	}

	public CommerceForecastValueSoap() {
	}

	public long getPrimaryKey() {
		return _commerceForecastValueId;
	}

	public void setPrimaryKey(long pk) {
		setCommerceForecastValueId(pk);
	}

	public long getCommerceForecastValueId() {
		return _commerceForecastValueId;
	}

	public void setCommerceForecastValueId(long commerceForecastValueId) {
		_commerceForecastValueId = commerceForecastValueId;
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

	public long getCommerceForecastEntryId() {
		return _commerceForecastEntryId;
	}

	public void setCommerceForecastEntryId(long commerceForecastEntryId) {
		_commerceForecastEntryId = commerceForecastEntryId;
	}

	public long getTime() {
		return _time;
	}

	public void setTime(long time) {
		_time = time;
	}

	public BigDecimal getLowerValue() {
		return _lowerValue;
	}

	public void setLowerValue(BigDecimal lowerValue) {
		_lowerValue = lowerValue;
	}

	public BigDecimal getValue() {
		return _value;
	}

	public void setValue(BigDecimal value) {
		_value = value;
	}

	public BigDecimal getUpperValue() {
		return _upperValue;
	}

	public void setUpperValue(BigDecimal upperValue) {
		_upperValue = upperValue;
	}

	private long _commerceForecastValueId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _commerceForecastEntryId;
	private long _time;
	private BigDecimal _lowerValue;
	private BigDecimal _value;
	private BigDecimal _upperValue;
}