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

package com.liferay.commerce.vat.model;

import aQute.bnd.annotation.ProviderType;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.vat.service.http.CommerceVatNumberServiceSoap}.
 *
 * @author Alessio Antonio Rendina
 * @see com.liferay.commerce.vat.service.http.CommerceVatNumberServiceSoap
 * @generated
 */
@ProviderType
public class CommerceVatNumberSoap implements Serializable {
	public static CommerceVatNumberSoap toSoapModel(CommerceVatNumber model) {
		CommerceVatNumberSoap soapModel = new CommerceVatNumberSoap();

		soapModel.setCommerceVatNumberId(model.getCommerceVatNumberId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setClassNameId(model.getClassNameId());
		soapModel.setClassPK(model.getClassPK());
		soapModel.setValue(model.getValue());
		soapModel.setValid(model.getValid());

		return soapModel;
	}

	public static CommerceVatNumberSoap[] toSoapModels(
		CommerceVatNumber[] models) {
		CommerceVatNumberSoap[] soapModels = new CommerceVatNumberSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CommerceVatNumberSoap[][] toSoapModels(
		CommerceVatNumber[][] models) {
		CommerceVatNumberSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CommerceVatNumberSoap[models.length][models[0].length];
		}
		else {
			soapModels = new CommerceVatNumberSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CommerceVatNumberSoap[] toSoapModels(
		List<CommerceVatNumber> models) {
		List<CommerceVatNumberSoap> soapModels = new ArrayList<CommerceVatNumberSoap>(models.size());

		for (CommerceVatNumber model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CommerceVatNumberSoap[soapModels.size()]);
	}

	public CommerceVatNumberSoap() {
	}

	public long getPrimaryKey() {
		return _commerceVatNumberId;
	}

	public void setPrimaryKey(long pk) {
		setCommerceVatNumberId(pk);
	}

	public long getCommerceVatNumberId() {
		return _commerceVatNumberId;
	}

	public void setCommerceVatNumberId(long commerceVatNumberId) {
		_commerceVatNumberId = commerceVatNumberId;
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

	public long getClassNameId() {
		return _classNameId;
	}

	public void setClassNameId(long classNameId) {
		_classNameId = classNameId;
	}

	public long getClassPK() {
		return _classPK;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	public String getValue() {
		return _value;
	}

	public void setValue(String value) {
		_value = value;
	}

	public boolean getValid() {
		return _valid;
	}

	public boolean isValid() {
		return _valid;
	}

	public void setValid(boolean valid) {
		_valid = valid;
	}

	private long _commerceVatNumberId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _classNameId;
	private long _classPK;
	private String _value;
	private boolean _valid;
}