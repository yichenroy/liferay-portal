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

package com.liferay.commerce.product.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.expando.kernel.model.ExpandoBridge;

import com.liferay.exportimport.kernel.lar.StagedModelType;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link CPDefinition}.
 * </p>
 *
 * @author Marco Leo
 * @see CPDefinition
 * @generated
 */
@ProviderType
public class CPDefinitionWrapper implements CPDefinition,
	ModelWrapper<CPDefinition> {
	public CPDefinitionWrapper(CPDefinition cpDefinition) {
		_cpDefinition = cpDefinition;
	}

	@Override
	public Class<?> getModelClass() {
		return CPDefinition.class;
	}

	@Override
	public String getModelClassName() {
		return CPDefinition.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("CPDefinitionId", getCPDefinitionId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("productTypeName", getProductTypeName());
		attributes.put("availableIndividually", getAvailableIndividually());
		attributes.put("canSellWithoutOptions", getCanSellWithoutOptions());
		attributes.put("displayAvailability", getDisplayAvailability());
		attributes.put("displayStockQuantity", getDisplayStockQuantity());
		attributes.put("minCartQuantity", getMinCartQuantity());
		attributes.put("maxCartQuantity", getMaxCartQuantity());
		attributes.put("allowedCartQuantities", getAllowedCartQuantities());
		attributes.put("multipleCartQuantity", getMultipleCartQuantity());
		attributes.put("width", getWidth());
		attributes.put("height", getHeight());
		attributes.put("depth", getDepth());
		attributes.put("weight", getWeight());
		attributes.put("DDMStructureKey", getDDMStructureKey());
		attributes.put("displayDate", getDisplayDate());
		attributes.put("expirationDate", getExpirationDate());
		attributes.put("lastPublishDate", getLastPublishDate());
		attributes.put("status", getStatus());
		attributes.put("statusByUserId", getStatusByUserId());
		attributes.put("statusByUserName", getStatusByUserName());
		attributes.put("statusDate", getStatusDate());
		attributes.put("defaultLanguageId", getDefaultLanguageId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long CPDefinitionId = (Long)attributes.get("CPDefinitionId");

		if (CPDefinitionId != null) {
			setCPDefinitionId(CPDefinitionId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String userName = (String)attributes.get("userName");

		if (userName != null) {
			setUserName(userName);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		String productTypeName = (String)attributes.get("productTypeName");

		if (productTypeName != null) {
			setProductTypeName(productTypeName);
		}

		Boolean availableIndividually = (Boolean)attributes.get(
				"availableIndividually");

		if (availableIndividually != null) {
			setAvailableIndividually(availableIndividually);
		}

		Boolean canSellWithoutOptions = (Boolean)attributes.get(
				"canSellWithoutOptions");

		if (canSellWithoutOptions != null) {
			setCanSellWithoutOptions(canSellWithoutOptions);
		}

		Boolean displayAvailability = (Boolean)attributes.get(
				"displayAvailability");

		if (displayAvailability != null) {
			setDisplayAvailability(displayAvailability);
		}

		Boolean displayStockQuantity = (Boolean)attributes.get(
				"displayStockQuantity");

		if (displayStockQuantity != null) {
			setDisplayStockQuantity(displayStockQuantity);
		}

		Integer minCartQuantity = (Integer)attributes.get("minCartQuantity");

		if (minCartQuantity != null) {
			setMinCartQuantity(minCartQuantity);
		}

		Integer maxCartQuantity = (Integer)attributes.get("maxCartQuantity");

		if (maxCartQuantity != null) {
			setMaxCartQuantity(maxCartQuantity);
		}

		String allowedCartQuantities = (String)attributes.get(
				"allowedCartQuantities");

		if (allowedCartQuantities != null) {
			setAllowedCartQuantities(allowedCartQuantities);
		}

		Integer multipleCartQuantity = (Integer)attributes.get(
				"multipleCartQuantity");

		if (multipleCartQuantity != null) {
			setMultipleCartQuantity(multipleCartQuantity);
		}

		Double width = (Double)attributes.get("width");

		if (width != null) {
			setWidth(width);
		}

		Double height = (Double)attributes.get("height");

		if (height != null) {
			setHeight(height);
		}

		Double depth = (Double)attributes.get("depth");

		if (depth != null) {
			setDepth(depth);
		}

		Double weight = (Double)attributes.get("weight");

		if (weight != null) {
			setWeight(weight);
		}

		String DDMStructureKey = (String)attributes.get("DDMStructureKey");

		if (DDMStructureKey != null) {
			setDDMStructureKey(DDMStructureKey);
		}

		Date displayDate = (Date)attributes.get("displayDate");

		if (displayDate != null) {
			setDisplayDate(displayDate);
		}

		Date expirationDate = (Date)attributes.get("expirationDate");

		if (expirationDate != null) {
			setExpirationDate(expirationDate);
		}

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
		}

		Integer status = (Integer)attributes.get("status");

		if (status != null) {
			setStatus(status);
		}

		Long statusByUserId = (Long)attributes.get("statusByUserId");

		if (statusByUserId != null) {
			setStatusByUserId(statusByUserId);
		}

		String statusByUserName = (String)attributes.get("statusByUserName");

		if (statusByUserName != null) {
			setStatusByUserName(statusByUserName);
		}

		Date statusDate = (Date)attributes.get("statusDate");

		if (statusDate != null) {
			setStatusDate(statusDate);
		}

		String defaultLanguageId = (String)attributes.get("defaultLanguageId");

		if (defaultLanguageId != null) {
			setDefaultLanguageId(defaultLanguageId);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new CPDefinitionWrapper((CPDefinition)_cpDefinition.clone());
	}

	@Override
	public int compareTo(CPDefinition cpDefinition) {
		return _cpDefinition.compareTo(cpDefinition);
	}

	/**
	* Returns the allowed cart quantities of this cp definition.
	*
	* @return the allowed cart quantities of this cp definition
	*/
	@Override
	public java.lang.String getAllowedCartQuantities() {
		return _cpDefinition.getAllowedCartQuantities();
	}

	/**
	* Returns the available individually of this cp definition.
	*
	* @return the available individually of this cp definition
	*/
	@Override
	public boolean getAvailableIndividually() {
		return _cpDefinition.getAvailableIndividually();
	}

	@Override
	public java.lang.String[] getAvailableLanguageIds() {
		return _cpDefinition.getAvailableLanguageIds();
	}

	/**
	* Returns the can sell without options of this cp definition.
	*
	* @return the can sell without options of this cp definition
	*/
	@Override
	public boolean getCanSellWithoutOptions() {
		return _cpDefinition.getCanSellWithoutOptions();
	}

	/**
	* Returns the company ID of this cp definition.
	*
	* @return the company ID of this cp definition
	*/
	@Override
	public long getCompanyId() {
		return _cpDefinition.getCompanyId();
	}

	/**
	* Returns the cp definition ID of this cp definition.
	*
	* @return the cp definition ID of this cp definition
	*/
	@Override
	public long getCPDefinitionId() {
		return _cpDefinition.getCPDefinitionId();
	}

	@Override
	public java.util.List<CPDefinitionOptionRel> getCPDefinitionOptionRels() {
		return _cpDefinition.getCPDefinitionOptionRels();
	}

	@Override
	public java.util.List<CPDefinitionSpecificationOptionValue> getCPDefinitionSpecificationOptionValues() {
		return _cpDefinition.getCPDefinitionSpecificationOptionValues();
	}

	/**
	* Returns the create date of this cp definition.
	*
	* @return the create date of this cp definition
	*/
	@Override
	public Date getCreateDate() {
		return _cpDefinition.getCreateDate();
	}

	/**
	* Returns the ddm structure key of this cp definition.
	*
	* @return the ddm structure key of this cp definition
	*/
	@Override
	public java.lang.String getDDMStructureKey() {
		return _cpDefinition.getDDMStructureKey();
	}

	@Override
	public java.lang.String getDefaultImageThumbnailSrc(
		com.liferay.portal.kernel.theme.ThemeDisplay themeDisplay)
		throws java.lang.Exception {
		return _cpDefinition.getDefaultImageThumbnailSrc(themeDisplay);
	}

	/**
	* Returns the default language ID of this cp definition.
	*
	* @return the default language ID of this cp definition
	*/
	@Override
	public java.lang.String getDefaultLanguageId() {
		return _cpDefinition.getDefaultLanguageId();
	}

	/**
	* Returns the depth of this cp definition.
	*
	* @return the depth of this cp definition
	*/
	@Override
	public double getDepth() {
		return _cpDefinition.getDepth();
	}

	@Override
	public java.lang.String getDescription() {
		return _cpDefinition.getDescription();
	}

	@Override
	public java.lang.String getDescription(java.lang.String languageId) {
		return _cpDefinition.getDescription(languageId);
	}

	@Override
	public java.lang.String getDescription(java.lang.String languageId,
		boolean useDefault) {
		return _cpDefinition.getDescription(languageId, useDefault);
	}

	@Override
	public Map<java.util.Locale, java.lang.String> getDescriptionMap() {
		return _cpDefinition.getDescriptionMap();
	}

	@Override
	public java.lang.String getDescriptionMapAsXML() {
		return _cpDefinition.getDescriptionMapAsXML();
	}

	/**
	* Returns the display availability of this cp definition.
	*
	* @return the display availability of this cp definition
	*/
	@Override
	public boolean getDisplayAvailability() {
		return _cpDefinition.getDisplayAvailability();
	}

	/**
	* Returns the display date of this cp definition.
	*
	* @return the display date of this cp definition
	*/
	@Override
	public Date getDisplayDate() {
		return _cpDefinition.getDisplayDate();
	}

	/**
	* Returns the display stock quantity of this cp definition.
	*
	* @return the display stock quantity of this cp definition
	*/
	@Override
	public boolean getDisplayStockQuantity() {
		return _cpDefinition.getDisplayStockQuantity();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _cpDefinition.getExpandoBridge();
	}

	/**
	* Returns the expiration date of this cp definition.
	*
	* @return the expiration date of this cp definition
	*/
	@Override
	public Date getExpirationDate() {
		return _cpDefinition.getExpirationDate();
	}

	/**
	* Returns the group ID of this cp definition.
	*
	* @return the group ID of this cp definition
	*/
	@Override
	public long getGroupId() {
		return _cpDefinition.getGroupId();
	}

	/**
	* Returns the height of this cp definition.
	*
	* @return the height of this cp definition
	*/
	@Override
	public double getHeight() {
		return _cpDefinition.getHeight();
	}

	@Override
	public Map<java.lang.String, java.lang.String> getLanguageIdToDescriptionMap() {
		return _cpDefinition.getLanguageIdToDescriptionMap();
	}

	@Override
	public Map<java.lang.String, java.lang.String> getLanguageIdToMetaDescriptionMap() {
		return _cpDefinition.getLanguageIdToMetaDescriptionMap();
	}

	@Override
	public Map<java.lang.String, java.lang.String> getLanguageIdToMetaKeywordsMap() {
		return _cpDefinition.getLanguageIdToMetaKeywordsMap();
	}

	@Override
	public Map<java.lang.String, java.lang.String> getLanguageIdToMetaTitleMap() {
		return _cpDefinition.getLanguageIdToMetaTitleMap();
	}

	@Override
	public Map<java.lang.String, java.lang.String> getLanguageIdToShortDescriptionMap() {
		return _cpDefinition.getLanguageIdToShortDescriptionMap();
	}

	@Override
	public Map<java.lang.String, java.lang.String> getLanguageIdToTitleMap() {
		return _cpDefinition.getLanguageIdToTitleMap();
	}

	/**
	* Returns the last publish date of this cp definition.
	*
	* @return the last publish date of this cp definition
	*/
	@Override
	public Date getLastPublishDate() {
		return _cpDefinition.getLastPublishDate();
	}

	@Override
	public java.lang.String getLayoutUuid() {
		return _cpDefinition.getLayoutUuid();
	}

	/**
	* Returns the max cart quantity of this cp definition.
	*
	* @return the max cart quantity of this cp definition
	*/
	@Override
	public int getMaxCartQuantity() {
		return _cpDefinition.getMaxCartQuantity();
	}

	@Override
	public java.lang.String getMetaDescription() {
		return _cpDefinition.getMetaDescription();
	}

	@Override
	public java.lang.String getMetaDescription(java.lang.String languageId) {
		return _cpDefinition.getMetaDescription(languageId);
	}

	@Override
	public java.lang.String getMetaDescription(java.lang.String languageId,
		boolean useDefault) {
		return _cpDefinition.getMetaDescription(languageId, useDefault);
	}

	@Override
	public Map<java.util.Locale, java.lang.String> getMetaDescriptionMap() {
		return _cpDefinition.getMetaDescriptionMap();
	}

	@Override
	public java.lang.String getMetaDescriptionMapAsXML() {
		return _cpDefinition.getMetaDescriptionMapAsXML();
	}

	@Override
	public java.lang.String getMetaKeywords() {
		return _cpDefinition.getMetaKeywords();
	}

	@Override
	public java.lang.String getMetaKeywords(java.lang.String languageId) {
		return _cpDefinition.getMetaKeywords(languageId);
	}

	@Override
	public java.lang.String getMetaKeywords(java.lang.String languageId,
		boolean useDefault) {
		return _cpDefinition.getMetaKeywords(languageId, useDefault);
	}

	@Override
	public Map<java.util.Locale, java.lang.String> getMetaKeywordsMap() {
		return _cpDefinition.getMetaKeywordsMap();
	}

	@Override
	public java.lang.String getMetaKeywordsMapAsXML() {
		return _cpDefinition.getMetaKeywordsMapAsXML();
	}

	@Override
	public java.lang.String getMetaTitle() {
		return _cpDefinition.getMetaTitle();
	}

	@Override
	public java.lang.String getMetaTitle(java.lang.String languageId) {
		return _cpDefinition.getMetaTitle(languageId);
	}

	@Override
	public java.lang.String getMetaTitle(java.lang.String languageId,
		boolean useDefault) {
		return _cpDefinition.getMetaTitle(languageId, useDefault);
	}

	@Override
	public Map<java.util.Locale, java.lang.String> getMetaTitleMap() {
		return _cpDefinition.getMetaTitleMap();
	}

	@Override
	public java.lang.String getMetaTitleMapAsXML() {
		return _cpDefinition.getMetaTitleMapAsXML();
	}

	/**
	* Returns the min cart quantity of this cp definition.
	*
	* @return the min cart quantity of this cp definition
	*/
	@Override
	public int getMinCartQuantity() {
		return _cpDefinition.getMinCartQuantity();
	}

	/**
	* Returns the modified date of this cp definition.
	*
	* @return the modified date of this cp definition
	*/
	@Override
	public Date getModifiedDate() {
		return _cpDefinition.getModifiedDate();
	}

	/**
	* Returns the multiple cart quantity of this cp definition.
	*
	* @return the multiple cart quantity of this cp definition
	*/
	@Override
	public int getMultipleCartQuantity() {
		return _cpDefinition.getMultipleCartQuantity();
	}

	/**
	* Returns the primary key of this cp definition.
	*
	* @return the primary key of this cp definition
	*/
	@Override
	public long getPrimaryKey() {
		return _cpDefinition.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _cpDefinition.getPrimaryKeyObj();
	}

	/**
	* Returns the product type name of this cp definition.
	*
	* @return the product type name of this cp definition
	*/
	@Override
	public java.lang.String getProductTypeName() {
		return _cpDefinition.getProductTypeName();
	}

	@Override
	public java.lang.String getShortDescription() {
		return _cpDefinition.getShortDescription();
	}

	@Override
	public java.lang.String getShortDescription(java.lang.String languageId) {
		return _cpDefinition.getShortDescription(languageId);
	}

	@Override
	public java.lang.String getShortDescription(java.lang.String languageId,
		boolean useDefault) {
		return _cpDefinition.getShortDescription(languageId, useDefault);
	}

	@Override
	public Map<java.util.Locale, java.lang.String> getShortDescriptionMap() {
		return _cpDefinition.getShortDescriptionMap();
	}

	@Override
	public java.lang.String getShortDescriptionMapAsXML() {
		return _cpDefinition.getShortDescriptionMapAsXML();
	}

	/**
	* Returns the status of this cp definition.
	*
	* @return the status of this cp definition
	*/
	@Override
	public int getStatus() {
		return _cpDefinition.getStatus();
	}

	/**
	* Returns the status by user ID of this cp definition.
	*
	* @return the status by user ID of this cp definition
	*/
	@Override
	public long getStatusByUserId() {
		return _cpDefinition.getStatusByUserId();
	}

	/**
	* Returns the status by user name of this cp definition.
	*
	* @return the status by user name of this cp definition
	*/
	@Override
	public java.lang.String getStatusByUserName() {
		return _cpDefinition.getStatusByUserName();
	}

	/**
	* Returns the status by user uuid of this cp definition.
	*
	* @return the status by user uuid of this cp definition
	*/
	@Override
	public java.lang.String getStatusByUserUuid() {
		return _cpDefinition.getStatusByUserUuid();
	}

	/**
	* Returns the status date of this cp definition.
	*
	* @return the status date of this cp definition
	*/
	@Override
	public Date getStatusDate() {
		return _cpDefinition.getStatusDate();
	}

	@Override
	public java.lang.String getTitle() {
		return _cpDefinition.getTitle();
	}

	@Override
	public java.lang.String getTitle(java.lang.String languageId) {
		return _cpDefinition.getTitle(languageId);
	}

	@Override
	public java.lang.String getTitle(java.lang.String languageId,
		boolean useDefault) {
		return _cpDefinition.getTitle(languageId, useDefault);
	}

	@Override
	public java.lang.String getTitleCurrentValue() {
		return _cpDefinition.getTitleCurrentValue();
	}

	@Override
	public Map<java.util.Locale, java.lang.String> getTitleMap() {
		return _cpDefinition.getTitleMap();
	}

	@Override
	public java.lang.String getTitleMapAsXML() {
		return _cpDefinition.getTitleMapAsXML();
	}

	/**
	* Returns the trash entry created when this cp definition was moved to the Recycle Bin. The trash entry may belong to one of the ancestors of this cp definition.
	*
	* @return the trash entry created when this cp definition was moved to the Recycle Bin
	*/
	@Override
	public com.liferay.trash.kernel.model.TrashEntry getTrashEntry()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinition.getTrashEntry();
	}

	/**
	* Returns the class primary key of the trash entry for this cp definition.
	*
	* @return the class primary key of the trash entry for this cp definition
	*/
	@Override
	public long getTrashEntryClassPK() {
		return _cpDefinition.getTrashEntryClassPK();
	}

	/**
	* Returns the trash handler for this cp definition.
	*
	* @return the trash handler for this cp definition
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@Deprecated
	@Override
	public com.liferay.portal.kernel.trash.TrashHandler getTrashHandler() {
		return _cpDefinition.getTrashHandler();
	}

	@Override
	public Map<java.util.Locale, java.lang.String> getUrlTitleMap() {
		return _cpDefinition.getUrlTitleMap();
	}

	/**
	* Returns the user ID of this cp definition.
	*
	* @return the user ID of this cp definition
	*/
	@Override
	public long getUserId() {
		return _cpDefinition.getUserId();
	}

	/**
	* Returns the user name of this cp definition.
	*
	* @return the user name of this cp definition
	*/
	@Override
	public java.lang.String getUserName() {
		return _cpDefinition.getUserName();
	}

	/**
	* Returns the user uuid of this cp definition.
	*
	* @return the user uuid of this cp definition
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _cpDefinition.getUserUuid();
	}

	/**
	* Returns the uuid of this cp definition.
	*
	* @return the uuid of this cp definition
	*/
	@Override
	public java.lang.String getUuid() {
		return _cpDefinition.getUuid();
	}

	/**
	* Returns the weight of this cp definition.
	*
	* @return the weight of this cp definition
	*/
	@Override
	public double getWeight() {
		return _cpDefinition.getWeight();
	}

	/**
	* Returns the width of this cp definition.
	*
	* @return the width of this cp definition
	*/
	@Override
	public double getWidth() {
		return _cpDefinition.getWidth();
	}

	@Override
	public int hashCode() {
		return _cpDefinition.hashCode();
	}

	/**
	* Returns <code>true</code> if this cp definition is approved.
	*
	* @return <code>true</code> if this cp definition is approved; <code>false</code> otherwise
	*/
	@Override
	public boolean isApproved() {
		return _cpDefinition.isApproved();
	}

	/**
	* Returns <code>true</code> if this cp definition is available individually.
	*
	* @return <code>true</code> if this cp definition is available individually; <code>false</code> otherwise
	*/
	@Override
	public boolean isAvailableIndividually() {
		return _cpDefinition.isAvailableIndividually();
	}

	@Override
	public boolean isCachedModel() {
		return _cpDefinition.isCachedModel();
	}

	/**
	* Returns <code>true</code> if this cp definition is can sell without options.
	*
	* @return <code>true</code> if this cp definition is can sell without options; <code>false</code> otherwise
	*/
	@Override
	public boolean isCanSellWithoutOptions() {
		return _cpDefinition.isCanSellWithoutOptions();
	}

	/**
	* Returns <code>true</code> if this cp definition is denied.
	*
	* @return <code>true</code> if this cp definition is denied; <code>false</code> otherwise
	*/
	@Override
	public boolean isDenied() {
		return _cpDefinition.isDenied();
	}

	/**
	* Returns <code>true</code> if this cp definition is display availability.
	*
	* @return <code>true</code> if this cp definition is display availability; <code>false</code> otherwise
	*/
	@Override
	public boolean isDisplayAvailability() {
		return _cpDefinition.isDisplayAvailability();
	}

	/**
	* Returns <code>true</code> if this cp definition is display stock quantity.
	*
	* @return <code>true</code> if this cp definition is display stock quantity; <code>false</code> otherwise
	*/
	@Override
	public boolean isDisplayStockQuantity() {
		return _cpDefinition.isDisplayStockQuantity();
	}

	/**
	* Returns <code>true</code> if this cp definition is a draft.
	*
	* @return <code>true</code> if this cp definition is a draft; <code>false</code> otherwise
	*/
	@Override
	public boolean isDraft() {
		return _cpDefinition.isDraft();
	}

	@Override
	public boolean isEscapedModel() {
		return _cpDefinition.isEscapedModel();
	}

	/**
	* Returns <code>true</code> if this cp definition is expired.
	*
	* @return <code>true</code> if this cp definition is expired; <code>false</code> otherwise
	*/
	@Override
	public boolean isExpired() {
		return _cpDefinition.isExpired();
	}

	/**
	* Returns <code>true</code> if this cp definition is inactive.
	*
	* @return <code>true</code> if this cp definition is inactive; <code>false</code> otherwise
	*/
	@Override
	public boolean isInactive() {
		return _cpDefinition.isInactive();
	}

	/**
	* Returns <code>true</code> if this cp definition is incomplete.
	*
	* @return <code>true</code> if this cp definition is incomplete; <code>false</code> otherwise
	*/
	@Override
	public boolean isIncomplete() {
		return _cpDefinition.isIncomplete();
	}

	/**
	* Returns <code>true</code> if this cp definition is in the Recycle Bin.
	*
	* @return <code>true</code> if this cp definition is in the Recycle Bin; <code>false</code> otherwise
	*/
	@Override
	public boolean isInTrash() {
		return _cpDefinition.isInTrash();
	}

	/**
	* Returns <code>true</code> if the parent of this cp definition is in the Recycle Bin.
	*
	* @return <code>true</code> if the parent of this cp definition is in the Recycle Bin; <code>false</code> otherwise
	*/
	@Override
	public boolean isInTrashContainer() {
		return _cpDefinition.isInTrashContainer();
	}

	@Override
	public boolean isInTrashExplicitly() {
		return _cpDefinition.isInTrashExplicitly();
	}

	@Override
	public boolean isInTrashImplicitly() {
		return _cpDefinition.isInTrashImplicitly();
	}

	@Override
	public boolean isNew() {
		return _cpDefinition.isNew();
	}

	/**
	* Returns <code>true</code> if this cp definition is pending.
	*
	* @return <code>true</code> if this cp definition is pending; <code>false</code> otherwise
	*/
	@Override
	public boolean isPending() {
		return _cpDefinition.isPending();
	}

	/**
	* Returns <code>true</code> if this cp definition is scheduled.
	*
	* @return <code>true</code> if this cp definition is scheduled; <code>false</code> otherwise
	*/
	@Override
	public boolean isScheduled() {
		return _cpDefinition.isScheduled();
	}

	@Override
	public void persist() {
		_cpDefinition.persist();
	}

	/**
	* Sets the allowed cart quantities of this cp definition.
	*
	* @param allowedCartQuantities the allowed cart quantities of this cp definition
	*/
	@Override
	public void setAllowedCartQuantities(java.lang.String allowedCartQuantities) {
		_cpDefinition.setAllowedCartQuantities(allowedCartQuantities);
	}

	/**
	* Sets whether this cp definition is available individually.
	*
	* @param availableIndividually the available individually of this cp definition
	*/
	@Override
	public void setAvailableIndividually(boolean availableIndividually) {
		_cpDefinition.setAvailableIndividually(availableIndividually);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_cpDefinition.setCachedModel(cachedModel);
	}

	/**
	* Sets whether this cp definition is can sell without options.
	*
	* @param canSellWithoutOptions the can sell without options of this cp definition
	*/
	@Override
	public void setCanSellWithoutOptions(boolean canSellWithoutOptions) {
		_cpDefinition.setCanSellWithoutOptions(canSellWithoutOptions);
	}

	/**
	* Sets the company ID of this cp definition.
	*
	* @param companyId the company ID of this cp definition
	*/
	@Override
	public void setCompanyId(long companyId) {
		_cpDefinition.setCompanyId(companyId);
	}

	/**
	* Sets the cp definition ID of this cp definition.
	*
	* @param CPDefinitionId the cp definition ID of this cp definition
	*/
	@Override
	public void setCPDefinitionId(long CPDefinitionId) {
		_cpDefinition.setCPDefinitionId(CPDefinitionId);
	}

	/**
	* Sets the create date of this cp definition.
	*
	* @param createDate the create date of this cp definition
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_cpDefinition.setCreateDate(createDate);
	}

	/**
	* Sets the ddm structure key of this cp definition.
	*
	* @param DDMStructureKey the ddm structure key of this cp definition
	*/
	@Override
	public void setDDMStructureKey(java.lang.String DDMStructureKey) {
		_cpDefinition.setDDMStructureKey(DDMStructureKey);
	}

	/**
	* Sets the default language ID of this cp definition.
	*
	* @param defaultLanguageId the default language ID of this cp definition
	*/
	@Override
	public void setDefaultLanguageId(java.lang.String defaultLanguageId) {
		_cpDefinition.setDefaultLanguageId(defaultLanguageId);
	}

	/**
	* Sets the depth of this cp definition.
	*
	* @param depth the depth of this cp definition
	*/
	@Override
	public void setDepth(double depth) {
		_cpDefinition.setDepth(depth);
	}

	@Override
	public void setDescriptionMap(
		Map<java.util.Locale, java.lang.String> descriptionMap) {
		_cpDefinition.setDescriptionMap(descriptionMap);
	}

	/**
	* Sets whether this cp definition is display availability.
	*
	* @param displayAvailability the display availability of this cp definition
	*/
	@Override
	public void setDisplayAvailability(boolean displayAvailability) {
		_cpDefinition.setDisplayAvailability(displayAvailability);
	}

	/**
	* Sets the display date of this cp definition.
	*
	* @param displayDate the display date of this cp definition
	*/
	@Override
	public void setDisplayDate(Date displayDate) {
		_cpDefinition.setDisplayDate(displayDate);
	}

	/**
	* Sets whether this cp definition is display stock quantity.
	*
	* @param displayStockQuantity the display stock quantity of this cp definition
	*/
	@Override
	public void setDisplayStockQuantity(boolean displayStockQuantity) {
		_cpDefinition.setDisplayStockQuantity(displayStockQuantity);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_cpDefinition.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_cpDefinition.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_cpDefinition.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the expiration date of this cp definition.
	*
	* @param expirationDate the expiration date of this cp definition
	*/
	@Override
	public void setExpirationDate(Date expirationDate) {
		_cpDefinition.setExpirationDate(expirationDate);
	}

	/**
	* Sets the group ID of this cp definition.
	*
	* @param groupId the group ID of this cp definition
	*/
	@Override
	public void setGroupId(long groupId) {
		_cpDefinition.setGroupId(groupId);
	}

	/**
	* Sets the height of this cp definition.
	*
	* @param height the height of this cp definition
	*/
	@Override
	public void setHeight(double height) {
		_cpDefinition.setHeight(height);
	}

	/**
	* Sets the last publish date of this cp definition.
	*
	* @param lastPublishDate the last publish date of this cp definition
	*/
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		_cpDefinition.setLastPublishDate(lastPublishDate);
	}

	@Override
	public void setLayoutUuid(java.lang.String layoutUuid) {
		_cpDefinition.setLayoutUuid(layoutUuid);
	}

	/**
	* Sets the max cart quantity of this cp definition.
	*
	* @param maxCartQuantity the max cart quantity of this cp definition
	*/
	@Override
	public void setMaxCartQuantity(int maxCartQuantity) {
		_cpDefinition.setMaxCartQuantity(maxCartQuantity);
	}

	/**
	* Sets the min cart quantity of this cp definition.
	*
	* @param minCartQuantity the min cart quantity of this cp definition
	*/
	@Override
	public void setMinCartQuantity(int minCartQuantity) {
		_cpDefinition.setMinCartQuantity(minCartQuantity);
	}

	/**
	* Sets the modified date of this cp definition.
	*
	* @param modifiedDate the modified date of this cp definition
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_cpDefinition.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the multiple cart quantity of this cp definition.
	*
	* @param multipleCartQuantity the multiple cart quantity of this cp definition
	*/
	@Override
	public void setMultipleCartQuantity(int multipleCartQuantity) {
		_cpDefinition.setMultipleCartQuantity(multipleCartQuantity);
	}

	@Override
	public void setNew(boolean n) {
		_cpDefinition.setNew(n);
	}

	/**
	* Sets the primary key of this cp definition.
	*
	* @param primaryKey the primary key of this cp definition
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_cpDefinition.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_cpDefinition.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the product type name of this cp definition.
	*
	* @param productTypeName the product type name of this cp definition
	*/
	@Override
	public void setProductTypeName(java.lang.String productTypeName) {
		_cpDefinition.setProductTypeName(productTypeName);
	}

	@Override
	public void setShortDescriptionMap(
		Map<java.util.Locale, java.lang.String> shortDescriptionMap) {
		_cpDefinition.setShortDescriptionMap(shortDescriptionMap);
	}

	/**
	* Sets the status of this cp definition.
	*
	* @param status the status of this cp definition
	*/
	@Override
	public void setStatus(int status) {
		_cpDefinition.setStatus(status);
	}

	/**
	* Sets the status by user ID of this cp definition.
	*
	* @param statusByUserId the status by user ID of this cp definition
	*/
	@Override
	public void setStatusByUserId(long statusByUserId) {
		_cpDefinition.setStatusByUserId(statusByUserId);
	}

	/**
	* Sets the status by user name of this cp definition.
	*
	* @param statusByUserName the status by user name of this cp definition
	*/
	@Override
	public void setStatusByUserName(java.lang.String statusByUserName) {
		_cpDefinition.setStatusByUserName(statusByUserName);
	}

	/**
	* Sets the status by user uuid of this cp definition.
	*
	* @param statusByUserUuid the status by user uuid of this cp definition
	*/
	@Override
	public void setStatusByUserUuid(java.lang.String statusByUserUuid) {
		_cpDefinition.setStatusByUserUuid(statusByUserUuid);
	}

	/**
	* Sets the status date of this cp definition.
	*
	* @param statusDate the status date of this cp definition
	*/
	@Override
	public void setStatusDate(Date statusDate) {
		_cpDefinition.setStatusDate(statusDate);
	}

	@Override
	public void setTitleMap(Map<java.util.Locale, java.lang.String> titleMap) {
		_cpDefinition.setTitleMap(titleMap);
	}

	@Override
	public void setUrlTitleMap(
		Map<java.util.Locale, java.lang.String> urlTitleMap) {
		_cpDefinition.setUrlTitleMap(urlTitleMap);
	}

	/**
	* Sets the user ID of this cp definition.
	*
	* @param userId the user ID of this cp definition
	*/
	@Override
	public void setUserId(long userId) {
		_cpDefinition.setUserId(userId);
	}

	/**
	* Sets the user name of this cp definition.
	*
	* @param userName the user name of this cp definition
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_cpDefinition.setUserName(userName);
	}

	/**
	* Sets the user uuid of this cp definition.
	*
	* @param userUuid the user uuid of this cp definition
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_cpDefinition.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this cp definition.
	*
	* @param uuid the uuid of this cp definition
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_cpDefinition.setUuid(uuid);
	}

	/**
	* Sets the weight of this cp definition.
	*
	* @param weight the weight of this cp definition
	*/
	@Override
	public void setWeight(double weight) {
		_cpDefinition.setWeight(weight);
	}

	/**
	* Sets the width of this cp definition.
	*
	* @param width the width of this cp definition
	*/
	@Override
	public void setWidth(double width) {
		_cpDefinition.setWidth(width);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CPDefinition> toCacheModel() {
		return _cpDefinition.toCacheModel();
	}

	@Override
	public CPDefinition toEscapedModel() {
		return new CPDefinitionWrapper(_cpDefinition.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _cpDefinition.toString();
	}

	@Override
	public CPDefinition toUnescapedModel() {
		return new CPDefinitionWrapper(_cpDefinition.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _cpDefinition.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CPDefinitionWrapper)) {
			return false;
		}

		CPDefinitionWrapper cpDefinitionWrapper = (CPDefinitionWrapper)obj;

		if (Objects.equals(_cpDefinition, cpDefinitionWrapper._cpDefinition)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _cpDefinition.getStagedModelType();
	}

	@Override
	public CPDefinition getWrappedModel() {
		return _cpDefinition;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _cpDefinition.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _cpDefinition.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_cpDefinition.resetOriginalValues();
	}

	private final CPDefinition _cpDefinition;
}