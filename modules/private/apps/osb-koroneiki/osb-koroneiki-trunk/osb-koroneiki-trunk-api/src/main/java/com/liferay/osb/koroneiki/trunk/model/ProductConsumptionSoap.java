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

package com.liferay.osb.koroneiki.trunk.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.osb.koroneiki.trunk.service.http.ProductConsumptionServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public class ProductConsumptionSoap implements Serializable {

	public static ProductConsumptionSoap toSoapModel(ProductConsumption model) {
		ProductConsumptionSoap soapModel = new ProductConsumptionSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setProductConsumptionId(model.getProductConsumptionId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setProductConsumptionKey(model.getProductConsumptionKey());
		soapModel.setAccountId(model.getAccountId());
		soapModel.setProductEntryId(model.getProductEntryId());

		return soapModel;
	}

	public static ProductConsumptionSoap[] toSoapModels(
		ProductConsumption[] models) {

		ProductConsumptionSoap[] soapModels =
			new ProductConsumptionSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static ProductConsumptionSoap[][] toSoapModels(
		ProductConsumption[][] models) {

		ProductConsumptionSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new ProductConsumptionSoap[models.length][models[0].length];
		}
		else {
			soapModels = new ProductConsumptionSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static ProductConsumptionSoap[] toSoapModels(
		List<ProductConsumption> models) {

		List<ProductConsumptionSoap> soapModels =
			new ArrayList<ProductConsumptionSoap>(models.size());

		for (ProductConsumption model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new ProductConsumptionSoap[soapModels.size()]);
	}

	public ProductConsumptionSoap() {
	}

	public long getPrimaryKey() {
		return _productConsumptionId;
	}

	public void setPrimaryKey(long pk) {
		setProductConsumptionId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getProductConsumptionId() {
		return _productConsumptionId;
	}

	public void setProductConsumptionId(long productConsumptionId) {
		_productConsumptionId = productConsumptionId;
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

	public String getProductConsumptionKey() {
		return _productConsumptionKey;
	}

	public void setProductConsumptionKey(String productConsumptionKey) {
		_productConsumptionKey = productConsumptionKey;
	}

	public long getAccountId() {
		return _accountId;
	}

	public void setAccountId(long accountId) {
		_accountId = accountId;
	}

	public long getProductEntryId() {
		return _productEntryId;
	}

	public void setProductEntryId(long productEntryId) {
		_productEntryId = productEntryId;
	}

	private String _uuid;
	private long _productConsumptionId;
	private long _companyId;
	private long _userId;
	private Date _createDate;
	private Date _modifiedDate;
	private String _productConsumptionKey;
	private long _accountId;
	private long _productEntryId;

}