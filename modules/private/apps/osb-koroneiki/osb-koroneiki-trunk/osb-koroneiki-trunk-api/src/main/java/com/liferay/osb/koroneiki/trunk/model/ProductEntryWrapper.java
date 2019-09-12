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
 * This class is a wrapper for {@link ProductEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ProductEntry
 * @generated
 */
@ProviderType
public class ProductEntryWrapper
	extends BaseModelWrapper<ProductEntry>
	implements ProductEntry, ModelWrapper<ProductEntry> {

	public ProductEntryWrapper(ProductEntry productEntry) {
		super(productEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("productEntryId", getProductEntryId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("productEntryKey", getProductEntryKey());
		attributes.put("name", getName());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long productEntryId = (Long)attributes.get("productEntryId");

		if (productEntryId != null) {
			setProductEntryId(productEntryId);
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

		String productEntryKey = (String)attributes.get("productEntryKey");

		if (productEntryKey != null) {
			setProductEntryKey(productEntryKey);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}
	}

	/**
	 * Returns the company ID of this product entry.
	 *
	 * @return the company ID of this product entry
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this product entry.
	 *
	 * @return the create date of this product entry
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
	 * Returns the modified date of this product entry.
	 *
	 * @return the modified date of this product entry
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the name of this product entry.
	 *
	 * @return the name of this product entry
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the primary key of this product entry.
	 *
	 * @return the primary key of this product entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the product entry ID of this product entry.
	 *
	 * @return the product entry ID of this product entry
	 */
	@Override
	public long getProductEntryId() {
		return model.getProductEntryId();
	}

	/**
	 * Returns the product entry key of this product entry.
	 *
	 * @return the product entry key of this product entry
	 */
	@Override
	public String getProductEntryKey() {
		return model.getProductEntryKey();
	}

	/**
	 * Returns the user ID of this product entry.
	 *
	 * @return the user ID of this product entry
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user uuid of this product entry.
	 *
	 * @return the user uuid of this product entry
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this product entry.
	 *
	 * @return the uuid of this product entry
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
	 * Sets the company ID of this product entry.
	 *
	 * @param companyId the company ID of this product entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this product entry.
	 *
	 * @param createDate the create date of this product entry
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the modified date of this product entry.
	 *
	 * @param modifiedDate the modified date of this product entry
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the name of this product entry.
	 *
	 * @param name the name of this product entry
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the primary key of this product entry.
	 *
	 * @param primaryKey the primary key of this product entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the product entry ID of this product entry.
	 *
	 * @param productEntryId the product entry ID of this product entry
	 */
	@Override
	public void setProductEntryId(long productEntryId) {
		model.setProductEntryId(productEntryId);
	}

	/**
	 * Sets the product entry key of this product entry.
	 *
	 * @param productEntryKey the product entry key of this product entry
	 */
	@Override
	public void setProductEntryKey(String productEntryKey) {
		model.setProductEntryKey(productEntryKey);
	}

	/**
	 * Sets the user ID of this product entry.
	 *
	 * @param userId the user ID of this product entry
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user uuid of this product entry.
	 *
	 * @param userUuid the user uuid of this product entry
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this product entry.
	 *
	 * @param uuid the uuid of this product entry
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
	protected ProductEntryWrapper wrap(ProductEntry productEntry) {
		return new ProductEntryWrapper(productEntry);
	}

}