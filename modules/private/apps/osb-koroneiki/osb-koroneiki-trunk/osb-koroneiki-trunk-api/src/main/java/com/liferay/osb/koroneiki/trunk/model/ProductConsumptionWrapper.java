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

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <p>
 * This class is a wrapper for {@link ProductConsumption}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ProductConsumption
 * @generated
 */
@ProviderType
public class ProductConsumptionWrapper
	extends BaseModelWrapper<ProductConsumption>
	implements ProductConsumption, ModelWrapper<ProductConsumption> {

	public ProductConsumptionWrapper(ProductConsumption productConsumption) {
		super(productConsumption);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("productConsumptionId", getProductConsumptionId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("productConsumptionKey", getProductConsumptionKey());
		attributes.put("accountId", getAccountId());
		attributes.put("productEntryId", getProductEntryId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long productConsumptionId = (Long)attributes.get(
			"productConsumptionId");

		if (productConsumptionId != null) {
			setProductConsumptionId(productConsumptionId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		String productConsumptionKey = (String)attributes.get(
			"productConsumptionKey");

		if (productConsumptionKey != null) {
			setProductConsumptionKey(productConsumptionKey);
		}

		Long accountId = (Long)attributes.get("accountId");

		if (accountId != null) {
			setAccountId(accountId);
		}

		Long productEntryId = (Long)attributes.get("productEntryId");

		if (productEntryId != null) {
			setProductEntryId(productEntryId);
		}
	}

	@Override
	public com.liferay.osb.koroneiki.taproot.model.Account getAccount()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getAccount();
	}

	/**
	 * Returns the account ID of this product consumption.
	 *
	 * @return the account ID of this product consumption
	 */
	@Override
	public long getAccountId() {
		return model.getAccountId();
	}

	@Override
	public String getAccountKey()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getAccountKey();
	}

	/**
	 * Returns the company ID of this product consumption.
	 *
	 * @return the company ID of this product consumption
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this product consumption.
	 *
	 * @return the create date of this product consumption
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	@Override
	public java.util.List<com.liferay.osb.koroneiki.root.model.ExternalLink>
		getExternalLinks() {

		return model.getExternalLinks();
	}

	/**
	 * Returns the modified date of this product consumption.
	 *
	 * @return the modified date of this product consumption
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the primary key of this product consumption.
	 *
	 * @return the primary key of this product consumption
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the product consumption ID of this product consumption.
	 *
	 * @return the product consumption ID of this product consumption
	 */
	@Override
	public long getProductConsumptionId() {
		return model.getProductConsumptionId();
	}

	/**
	 * Returns the product consumption key of this product consumption.
	 *
	 * @return the product consumption key of this product consumption
	 */
	@Override
	public String getProductConsumptionKey() {
		return model.getProductConsumptionKey();
	}

	@Override
	public ProductEntry getProductEntry()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getProductEntry();
	}

	/**
	 * Returns the product entry ID of this product consumption.
	 *
	 * @return the product entry ID of this product consumption
	 */
	@Override
	public long getProductEntryId() {
		return model.getProductEntryId();
	}

	@Override
	public String getProductEntryKey()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getProductEntryKey();
	}

	@Override
	public java.util.List<ProductField> getProductFields() {
		return model.getProductFields();
	}

	@Override
	public Map<String, String> getProductFieldsMap() {
		return model.getProductFieldsMap();
	}

	/**
	 * Returns the user ID of this product consumption.
	 *
	 * @return the user ID of this product consumption
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user uuid of this product consumption.
	 *
	 * @return the user uuid of this product consumption
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this product consumption.
	 *
	 * @return the uuid of this product consumption
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the account ID of this product consumption.
	 *
	 * @param accountId the account ID of this product consumption
	 */
	@Override
	public void setAccountId(long accountId) {
		model.setAccountId(accountId);
	}

	/**
	 * Sets the company ID of this product consumption.
	 *
	 * @param companyId the company ID of this product consumption
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this product consumption.
	 *
	 * @param createDate the create date of this product consumption
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the modified date of this product consumption.
	 *
	 * @param modifiedDate the modified date of this product consumption
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the primary key of this product consumption.
	 *
	 * @param primaryKey the primary key of this product consumption
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the product consumption ID of this product consumption.
	 *
	 * @param productConsumptionId the product consumption ID of this product consumption
	 */
	@Override
	public void setProductConsumptionId(long productConsumptionId) {
		model.setProductConsumptionId(productConsumptionId);
	}

	/**
	 * Sets the product consumption key of this product consumption.
	 *
	 * @param productConsumptionKey the product consumption key of this product consumption
	 */
	@Override
	public void setProductConsumptionKey(String productConsumptionKey) {
		model.setProductConsumptionKey(productConsumptionKey);
	}

	/**
	 * Sets the product entry ID of this product consumption.
	 *
	 * @param productEntryId the product entry ID of this product consumption
	 */
	@Override
	public void setProductEntryId(long productEntryId) {
		model.setProductEntryId(productEntryId);
	}

	/**
	 * Sets the user ID of this product consumption.
	 *
	 * @param userId the user ID of this product consumption
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user uuid of this product consumption.
	 *
	 * @param userUuid the user uuid of this product consumption
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this product consumption.
	 *
	 * @param uuid the uuid of this product consumption
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected ProductConsumptionWrapper wrap(
		ProductConsumption productConsumption) {

		return new ProductConsumptionWrapper(productConsumption);
	}

}