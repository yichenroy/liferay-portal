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
 * This class is used by SOAP remote services, specifically {@link com.liferay.osb.koroneiki.trunk.service.http.ProductPurchaseServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public class ProductPurchaseSoap implements Serializable {

	public static ProductPurchaseSoap toSoapModel(ProductPurchase model) {
		ProductPurchaseSoap soapModel = new ProductPurchaseSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setProductPurchaseId(model.getProductPurchaseId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setProductPurchaseKey(model.getProductPurchaseKey());
		soapModel.setAccountId(model.getAccountId());
		soapModel.setProductEntryId(model.getProductEntryId());
		soapModel.setStartDate(model.getStartDate());
		soapModel.setEndDate(model.getEndDate());
		soapModel.setQuantity(model.getQuantity());

		return soapModel;
	}

	public static ProductPurchaseSoap[] toSoapModels(ProductPurchase[] models) {
		ProductPurchaseSoap[] soapModels =
			new ProductPurchaseSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static ProductPurchaseSoap[][] toSoapModels(
		ProductPurchase[][] models) {

		ProductPurchaseSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new ProductPurchaseSoap[models.length][models[0].length];
		}
		else {
			soapModels = new ProductPurchaseSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static ProductPurchaseSoap[] toSoapModels(
		List<ProductPurchase> models) {

		List<ProductPurchaseSoap> soapModels =
			new ArrayList<ProductPurchaseSoap>(models.size());

		for (ProductPurchase model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new ProductPurchaseSoap[soapModels.size()]);
	}

	public ProductPurchaseSoap() {
	}

	public long getPrimaryKey() {
		return _productPurchaseId;
	}

	public void setPrimaryKey(long pk) {
		setProductPurchaseId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getProductPurchaseId() {
		return _productPurchaseId;
	}

	public void setProductPurchaseId(long productPurchaseId) {
		_productPurchaseId = productPurchaseId;
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

	public String getProductPurchaseKey() {
		return _productPurchaseKey;
	}

	public void setProductPurchaseKey(String productPurchaseKey) {
		_productPurchaseKey = productPurchaseKey;
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

	public Date getStartDate() {
		return _startDate;
	}

	public void setStartDate(Date startDate) {
		_startDate = startDate;
	}

	public Date getEndDate() {
		return _endDate;
	}

	public void setEndDate(Date endDate) {
		_endDate = endDate;
	}

	public int getQuantity() {
		return _quantity;
	}

	public void setQuantity(int quantity) {
		_quantity = quantity;
	}

	private String _uuid;
	private long _productPurchaseId;
	private long _companyId;
	private long _userId;
	private Date _createDate;
	private Date _modifiedDate;
	private String _productPurchaseKey;
	private long _accountId;
	private long _productEntryId;
	private Date _startDate;
	private Date _endDate;
	private int _quantity;

}