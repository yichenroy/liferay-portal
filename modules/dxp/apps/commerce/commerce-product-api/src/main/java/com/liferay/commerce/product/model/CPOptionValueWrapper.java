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
 * This class is a wrapper for {@link CPOptionValue}.
 * </p>
 *
 * @author Marco Leo
 * @see CPOptionValue
 * @generated
 */
@ProviderType
public class CPOptionValueWrapper implements CPOptionValue,
	ModelWrapper<CPOptionValue> {
	public CPOptionValueWrapper(CPOptionValue cpOptionValue) {
		_cpOptionValue = cpOptionValue;
	}

	@Override
	public Class<?> getModelClass() {
		return CPOptionValue.class;
	}

	@Override
	public String getModelClassName() {
		return CPOptionValue.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("CPOptionValueId", getCPOptionValueId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("CPOptionId", getCPOptionId());
		attributes.put("title", getTitle());
		attributes.put("priority", getPriority());
		attributes.put("key", getKey());
		attributes.put("lastPublishDate", getLastPublishDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long CPOptionValueId = (Long)attributes.get("CPOptionValueId");

		if (CPOptionValueId != null) {
			setCPOptionValueId(CPOptionValueId);
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

		Long CPOptionId = (Long)attributes.get("CPOptionId");

		if (CPOptionId != null) {
			setCPOptionId(CPOptionId);
		}

		String title = (String)attributes.get("title");

		if (title != null) {
			setTitle(title);
		}

		Double priority = (Double)attributes.get("priority");

		if (priority != null) {
			setPriority(priority);
		}

		String key = (String)attributes.get("key");

		if (key != null) {
			setKey(key);
		}

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
		}
	}

	@Override
	public CPOption getCPOption()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpOptionValue.getCPOption();
	}

	@Override
	public CPOptionValue toEscapedModel() {
		return new CPOptionValueWrapper(_cpOptionValue.toEscapedModel());
	}

	@Override
	public CPOptionValue toUnescapedModel() {
		return new CPOptionValueWrapper(_cpOptionValue.toUnescapedModel());
	}

	@Override
	public boolean isCachedModel() {
		return _cpOptionValue.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _cpOptionValue.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _cpOptionValue.isNew();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _cpOptionValue.getExpandoBridge();
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CPOptionValue> toCacheModel() {
		return _cpOptionValue.toCacheModel();
	}

	/**
	* Returns the priority of this cp option value.
	*
	* @return the priority of this cp option value
	*/
	@Override
	public double getPriority() {
		return _cpOptionValue.getPriority();
	}

	@Override
	public int compareTo(CPOptionValue cpOptionValue) {
		return _cpOptionValue.compareTo(cpOptionValue);
	}

	@Override
	public int hashCode() {
		return _cpOptionValue.hashCode();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _cpOptionValue.getPrimaryKeyObj();
	}

	@Override
	public java.lang.Object clone() {
		return new CPOptionValueWrapper((CPOptionValue)_cpOptionValue.clone());
	}

	@Override
	public java.lang.String getDefaultLanguageId() {
		return _cpOptionValue.getDefaultLanguageId();
	}

	/**
	* Returns the key of this cp option value.
	*
	* @return the key of this cp option value
	*/
	@Override
	public java.lang.String getKey() {
		return _cpOptionValue.getKey();
	}

	/**
	* Returns the title of this cp option value.
	*
	* @return the title of this cp option value
	*/
	@Override
	public java.lang.String getTitle() {
		return _cpOptionValue.getTitle();
	}

	/**
	* Returns the localized title of this cp option value in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized title of this cp option value
	*/
	@Override
	public java.lang.String getTitle(java.lang.String languageId) {
		return _cpOptionValue.getTitle(languageId);
	}

	/**
	* Returns the localized title of this cp option value in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized title of this cp option value
	*/
	@Override
	public java.lang.String getTitle(java.lang.String languageId,
		boolean useDefault) {
		return _cpOptionValue.getTitle(languageId, useDefault);
	}

	/**
	* Returns the localized title of this cp option value in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized title of this cp option value
	*/
	@Override
	public java.lang.String getTitle(java.util.Locale locale) {
		return _cpOptionValue.getTitle(locale);
	}

	/**
	* Returns the localized title of this cp option value in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized title of this cp option value. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public java.lang.String getTitle(java.util.Locale locale, boolean useDefault) {
		return _cpOptionValue.getTitle(locale, useDefault);
	}

	@Override
	public java.lang.String getTitleCurrentLanguageId() {
		return _cpOptionValue.getTitleCurrentLanguageId();
	}

	@Override
	public java.lang.String getTitleCurrentValue() {
		return _cpOptionValue.getTitleCurrentValue();
	}

	/**
	* Returns the user name of this cp option value.
	*
	* @return the user name of this cp option value
	*/
	@Override
	public java.lang.String getUserName() {
		return _cpOptionValue.getUserName();
	}

	/**
	* Returns the user uuid of this cp option value.
	*
	* @return the user uuid of this cp option value
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _cpOptionValue.getUserUuid();
	}

	/**
	* Returns the uuid of this cp option value.
	*
	* @return the uuid of this cp option value
	*/
	@Override
	public java.lang.String getUuid() {
		return _cpOptionValue.getUuid();
	}

	@Override
	public java.lang.String toString() {
		return _cpOptionValue.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _cpOptionValue.toXmlString();
	}

	@Override
	public java.lang.String[] getAvailableLanguageIds() {
		return _cpOptionValue.getAvailableLanguageIds();
	}

	/**
	* Returns the create date of this cp option value.
	*
	* @return the create date of this cp option value
	*/
	@Override
	public Date getCreateDate() {
		return _cpOptionValue.getCreateDate();
	}

	/**
	* Returns the last publish date of this cp option value.
	*
	* @return the last publish date of this cp option value
	*/
	@Override
	public Date getLastPublishDate() {
		return _cpOptionValue.getLastPublishDate();
	}

	/**
	* Returns the modified date of this cp option value.
	*
	* @return the modified date of this cp option value
	*/
	@Override
	public Date getModifiedDate() {
		return _cpOptionValue.getModifiedDate();
	}

	/**
	* Returns a map of the locales and localized titles of this cp option value.
	*
	* @return the locales and localized titles of this cp option value
	*/
	@Override
	public Map<java.util.Locale, java.lang.String> getTitleMap() {
		return _cpOptionValue.getTitleMap();
	}

	/**
	* Returns the cp option ID of this cp option value.
	*
	* @return the cp option ID of this cp option value
	*/
	@Override
	public long getCPOptionId() {
		return _cpOptionValue.getCPOptionId();
	}

	/**
	* Returns the cp option value ID of this cp option value.
	*
	* @return the cp option value ID of this cp option value
	*/
	@Override
	public long getCPOptionValueId() {
		return _cpOptionValue.getCPOptionValueId();
	}

	/**
	* Returns the company ID of this cp option value.
	*
	* @return the company ID of this cp option value
	*/
	@Override
	public long getCompanyId() {
		return _cpOptionValue.getCompanyId();
	}

	/**
	* Returns the group ID of this cp option value.
	*
	* @return the group ID of this cp option value
	*/
	@Override
	public long getGroupId() {
		return _cpOptionValue.getGroupId();
	}

	/**
	* Returns the primary key of this cp option value.
	*
	* @return the primary key of this cp option value
	*/
	@Override
	public long getPrimaryKey() {
		return _cpOptionValue.getPrimaryKey();
	}

	/**
	* Returns the user ID of this cp option value.
	*
	* @return the user ID of this cp option value
	*/
	@Override
	public long getUserId() {
		return _cpOptionValue.getUserId();
	}

	@Override
	public void persist() {
		_cpOptionValue.persist();
	}

	@Override
	public void prepareLocalizedFieldsForImport()
		throws com.liferay.portal.kernel.exception.LocaleException {
		_cpOptionValue.prepareLocalizedFieldsForImport();
	}

	@Override
	public void prepareLocalizedFieldsForImport(
		java.util.Locale defaultImportLocale)
		throws com.liferay.portal.kernel.exception.LocaleException {
		_cpOptionValue.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	/**
	* Sets the cp option ID of this cp option value.
	*
	* @param CPOptionId the cp option ID of this cp option value
	*/
	@Override
	public void setCPOptionId(long CPOptionId) {
		_cpOptionValue.setCPOptionId(CPOptionId);
	}

	/**
	* Sets the cp option value ID of this cp option value.
	*
	* @param CPOptionValueId the cp option value ID of this cp option value
	*/
	@Override
	public void setCPOptionValueId(long CPOptionValueId) {
		_cpOptionValue.setCPOptionValueId(CPOptionValueId);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_cpOptionValue.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this cp option value.
	*
	* @param companyId the company ID of this cp option value
	*/
	@Override
	public void setCompanyId(long companyId) {
		_cpOptionValue.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this cp option value.
	*
	* @param createDate the create date of this cp option value
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_cpOptionValue.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_cpOptionValue.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_cpOptionValue.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_cpOptionValue.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this cp option value.
	*
	* @param groupId the group ID of this cp option value
	*/
	@Override
	public void setGroupId(long groupId) {
		_cpOptionValue.setGroupId(groupId);
	}

	/**
	* Sets the key of this cp option value.
	*
	* @param key the key of this cp option value
	*/
	@Override
	public void setKey(java.lang.String key) {
		_cpOptionValue.setKey(key);
	}

	/**
	* Sets the last publish date of this cp option value.
	*
	* @param lastPublishDate the last publish date of this cp option value
	*/
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		_cpOptionValue.setLastPublishDate(lastPublishDate);
	}

	/**
	* Sets the modified date of this cp option value.
	*
	* @param modifiedDate the modified date of this cp option value
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_cpOptionValue.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_cpOptionValue.setNew(n);
	}

	/**
	* Sets the primary key of this cp option value.
	*
	* @param primaryKey the primary key of this cp option value
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_cpOptionValue.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_cpOptionValue.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the priority of this cp option value.
	*
	* @param priority the priority of this cp option value
	*/
	@Override
	public void setPriority(double priority) {
		_cpOptionValue.setPriority(priority);
	}

	/**
	* Sets the title of this cp option value.
	*
	* @param title the title of this cp option value
	*/
	@Override
	public void setTitle(java.lang.String title) {
		_cpOptionValue.setTitle(title);
	}

	/**
	* Sets the localized title of this cp option value in the language.
	*
	* @param title the localized title of this cp option value
	* @param locale the locale of the language
	*/
	@Override
	public void setTitle(java.lang.String title, java.util.Locale locale) {
		_cpOptionValue.setTitle(title, locale);
	}

	/**
	* Sets the localized title of this cp option value in the language, and sets the default locale.
	*
	* @param title the localized title of this cp option value
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setTitle(java.lang.String title, java.util.Locale locale,
		java.util.Locale defaultLocale) {
		_cpOptionValue.setTitle(title, locale, defaultLocale);
	}

	@Override
	public void setTitleCurrentLanguageId(java.lang.String languageId) {
		_cpOptionValue.setTitleCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized titles of this cp option value from the map of locales and localized titles.
	*
	* @param titleMap the locales and localized titles of this cp option value
	*/
	@Override
	public void setTitleMap(Map<java.util.Locale, java.lang.String> titleMap) {
		_cpOptionValue.setTitleMap(titleMap);
	}

	/**
	* Sets the localized titles of this cp option value from the map of locales and localized titles, and sets the default locale.
	*
	* @param titleMap the locales and localized titles of this cp option value
	* @param defaultLocale the default locale
	*/
	@Override
	public void setTitleMap(Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Locale defaultLocale) {
		_cpOptionValue.setTitleMap(titleMap, defaultLocale);
	}

	/**
	* Sets the user ID of this cp option value.
	*
	* @param userId the user ID of this cp option value
	*/
	@Override
	public void setUserId(long userId) {
		_cpOptionValue.setUserId(userId);
	}

	/**
	* Sets the user name of this cp option value.
	*
	* @param userName the user name of this cp option value
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_cpOptionValue.setUserName(userName);
	}

	/**
	* Sets the user uuid of this cp option value.
	*
	* @param userUuid the user uuid of this cp option value
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_cpOptionValue.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this cp option value.
	*
	* @param uuid the uuid of this cp option value
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_cpOptionValue.setUuid(uuid);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CPOptionValueWrapper)) {
			return false;
		}

		CPOptionValueWrapper cpOptionValueWrapper = (CPOptionValueWrapper)obj;

		if (Objects.equals(_cpOptionValue, cpOptionValueWrapper._cpOptionValue)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _cpOptionValue.getStagedModelType();
	}

	@Override
	public CPOptionValue getWrappedModel() {
		return _cpOptionValue;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _cpOptionValue.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _cpOptionValue.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_cpOptionValue.resetOriginalValues();
	}

	private final CPOptionValue _cpOptionValue;
}