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
 * This class is a wrapper for {@link ProductPurchase}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ProductPurchase
 * @generated
 */
@ProviderType
public class ProductPurchaseWrapper
	extends BaseModelWrapper<ProductPurchase>
	implements ProductPurchase, ModelWrapper<ProductPurchase> {

	public ProductPurchaseWrapper(ProductPurchase productPurchase) {
		super(productPurchase);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("productPurchaseId", getProductPurchaseId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("productPurchaseKey", getProductPurchaseKey());
		attributes.put("accountId", getAccountId());
		attributes.put("productEntryId", getProductEntryId());
		attributes.put("startDate", getStartDate());
		attributes.put("endDate", getEndDate());
		attributes.put("quantity", getQuantity());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long productPurchaseId = (Long)attributes.get("productPurchaseId");

		if (productPurchaseId != null) {
			setProductPurchaseId(productPurchaseId);
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

		String productPurchaseKey = (String)attributes.get(
			"productPurchaseKey");

		if (productPurchaseKey != null) {
			setProductPurchaseKey(productPurchaseKey);
		}

		Long accountId = (Long)attributes.get("accountId");

		if (accountId != null) {
			setAccountId(accountId);
		}

		Long productEntryId = (Long)attributes.get("productEntryId");

		if (productEntryId != null) {
			setProductEntryId(productEntryId);
		}

		Date startDate = (Date)attributes.get("startDate");

		if (startDate != null) {
			setStartDate(startDate);
		}

		Date endDate = (Date)attributes.get("endDate");

		if (endDate != null) {
			setEndDate(endDate);
		}

		Integer quantity = (Integer)attributes.get("quantity");

		if (quantity != null) {
			setQuantity(quantity);
		}
	}

	@Override
	public com.liferay.osb.koroneiki.taproot.model.Account getAccount()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getAccount();
	}

	/**
	 * Returns the account ID of this product purchase.
	 *
	 * @return the account ID of this product purchase
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
	 * Returns the company ID of this product purchase.
	 *
	 * @return the company ID of this product purchase
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this product purchase.
	 *
	 * @return the create date of this product purchase
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the end date of this product purchase.
	 *
	 * @return the end date of this product purchase
	 */
	@Override
	public Date getEndDate() {
		return model.getEndDate();
	}

	@Override
	public java.util.List<com.liferay.osb.koroneiki.root.model.ExternalLink>
		getExternalLinks() {

		return model.getExternalLinks();
	}

	/**
	 * Returns the modified date of this product purchase.
	 *
	 * @return the modified date of this product purchase
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the primary key of this product purchase.
	 *
	 * @return the primary key of this product purchase
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	@Override
	public ProductEntry getProductEntry()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getProductEntry();
	}

	/**
	 * Returns the product entry ID of this product purchase.
	 *
	 * @return the product entry ID of this product purchase
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
	 * Returns the product purchase ID of this product purchase.
	 *
	 * @return the product purchase ID of this product purchase
	 */
	@Override
	public long getProductPurchaseId() {
		return model.getProductPurchaseId();
	}

	/**
	 * Returns the product purchase key of this product purchase.
	 *
	 * @return the product purchase key of this product purchase
	 */
	@Override
	public String getProductPurchaseKey() {
		return model.getProductPurchaseKey();
	}

	/**
	 * Returns the quantity of this product purchase.
	 *
	 * @return the quantity of this product purchase
	 */
	@Override
	public int getQuantity() {
		return model.getQuantity();
	}

	/**
	 * Returns the start date of this product purchase.
	 *
	 * @return the start date of this product purchase
	 */
	@Override
	public Date getStartDate() {
		return model.getStartDate();
	}

	/**
	 * Returns the user ID of this product purchase.
	 *
	 * @return the user ID of this product purchase
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user uuid of this product purchase.
	 *
	 * @return the user uuid of this product purchase
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this product purchase.
	 *
	 * @return the uuid of this product purchase
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	@Override
	public boolean isPerpetual() {
		return model.isPerpetual();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the account ID of this product purchase.
	 *
	 * @param accountId the account ID of this product purchase
	 */
	@Override
	public void setAccountId(long accountId) {
		model.setAccountId(accountId);
	}

	/**
	 * Sets the company ID of this product purchase.
	 *
	 * @param companyId the company ID of this product purchase
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this product purchase.
	 *
	 * @param createDate the create date of this product purchase
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the end date of this product purchase.
	 *
	 * @param endDate the end date of this product purchase
	 */
	@Override
	public void setEndDate(Date endDate) {
		model.setEndDate(endDate);
	}

	/**
	 * Sets the modified date of this product purchase.
	 *
	 * @param modifiedDate the modified date of this product purchase
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the primary key of this product purchase.
	 *
	 * @param primaryKey the primary key of this product purchase
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the product entry ID of this product purchase.
	 *
	 * @param productEntryId the product entry ID of this product purchase
	 */
	@Override
	public void setProductEntryId(long productEntryId) {
		model.setProductEntryId(productEntryId);
	}

	/**
	 * Sets the product purchase ID of this product purchase.
	 *
	 * @param productPurchaseId the product purchase ID of this product purchase
	 */
	@Override
	public void setProductPurchaseId(long productPurchaseId) {
		model.setProductPurchaseId(productPurchaseId);
	}

	/**
	 * Sets the product purchase key of this product purchase.
	 *
	 * @param productPurchaseKey the product purchase key of this product purchase
	 */
	@Override
	public void setProductPurchaseKey(String productPurchaseKey) {
		model.setProductPurchaseKey(productPurchaseKey);
	}

	/**
	 * Sets the quantity of this product purchase.
	 *
	 * @param quantity the quantity of this product purchase
	 */
	@Override
	public void setQuantity(int quantity) {
		model.setQuantity(quantity);
	}

	/**
	 * Sets the start date of this product purchase.
	 *
	 * @param startDate the start date of this product purchase
	 */
	@Override
	public void setStartDate(Date startDate) {
		model.setStartDate(startDate);
	}

	/**
	 * Sets the user ID of this product purchase.
	 *
	 * @param userId the user ID of this product purchase
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user uuid of this product purchase.
	 *
	 * @param userUuid the user uuid of this product purchase
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this product purchase.
	 *
	 * @param uuid the uuid of this product purchase
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
	protected ProductPurchaseWrapper wrap(ProductPurchase productPurchase) {
		return new ProductPurchaseWrapper(productPurchase);
	}

}