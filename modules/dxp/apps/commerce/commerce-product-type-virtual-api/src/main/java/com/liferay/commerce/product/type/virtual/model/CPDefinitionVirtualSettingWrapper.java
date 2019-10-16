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

package com.liferay.commerce.product.type.virtual.model;

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
 * This class is a wrapper for {@link CPDefinitionVirtualSetting}.
 * </p>
 *
 * @author Marco Leo
 * @see CPDefinitionVirtualSetting
 * @generated
 */
@ProviderType
public class CPDefinitionVirtualSettingWrapper
	implements CPDefinitionVirtualSetting,
		ModelWrapper<CPDefinitionVirtualSetting> {
	public CPDefinitionVirtualSettingWrapper(
		CPDefinitionVirtualSetting cpDefinitionVirtualSetting) {
		_cpDefinitionVirtualSetting = cpDefinitionVirtualSetting;
	}

	@Override
	public Class<?> getModelClass() {
		return CPDefinitionVirtualSetting.class;
	}

	@Override
	public String getModelClassName() {
		return CPDefinitionVirtualSetting.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("CPDefinitionVirtualSettingId",
			getCPDefinitionVirtualSettingId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("CPDefinitionId", getCPDefinitionId());
		attributes.put("fileEntryId", getFileEntryId());
		attributes.put("url", getUrl());
		attributes.put("activationStatus", getActivationStatus());
		attributes.put("duration", getDuration());
		attributes.put("maxUsages", getMaxUsages());
		attributes.put("useSample", getUseSample());
		attributes.put("sampleFileEntryId", getSampleFileEntryId());
		attributes.put("sampleUrl", getSampleUrl());
		attributes.put("termsOfUseRequired", getTermsOfUseRequired());
		attributes.put("termsOfUseContent", getTermsOfUseContent());
		attributes.put("termsOfUseJournalArticleResourcePrimKey",
			getTermsOfUseJournalArticleResourcePrimKey());
		attributes.put("lastPublishDate", getLastPublishDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long CPDefinitionVirtualSettingId = (Long)attributes.get(
				"CPDefinitionVirtualSettingId");

		if (CPDefinitionVirtualSettingId != null) {
			setCPDefinitionVirtualSettingId(CPDefinitionVirtualSettingId);
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

		Long CPDefinitionId = (Long)attributes.get("CPDefinitionId");

		if (CPDefinitionId != null) {
			setCPDefinitionId(CPDefinitionId);
		}

		Long fileEntryId = (Long)attributes.get("fileEntryId");

		if (fileEntryId != null) {
			setFileEntryId(fileEntryId);
		}

		String url = (String)attributes.get("url");

		if (url != null) {
			setUrl(url);
		}

		String activationStatus = (String)attributes.get("activationStatus");

		if (activationStatus != null) {
			setActivationStatus(activationStatus);
		}

		Long duration = (Long)attributes.get("duration");

		if (duration != null) {
			setDuration(duration);
		}

		Integer maxUsages = (Integer)attributes.get("maxUsages");

		if (maxUsages != null) {
			setMaxUsages(maxUsages);
		}

		Boolean useSample = (Boolean)attributes.get("useSample");

		if (useSample != null) {
			setUseSample(useSample);
		}

		Long sampleFileEntryId = (Long)attributes.get("sampleFileEntryId");

		if (sampleFileEntryId != null) {
			setSampleFileEntryId(sampleFileEntryId);
		}

		String sampleUrl = (String)attributes.get("sampleUrl");

		if (sampleUrl != null) {
			setSampleUrl(sampleUrl);
		}

		Boolean termsOfUseRequired = (Boolean)attributes.get(
				"termsOfUseRequired");

		if (termsOfUseRequired != null) {
			setTermsOfUseRequired(termsOfUseRequired);
		}

		String termsOfUseContent = (String)attributes.get("termsOfUseContent");

		if (termsOfUseContent != null) {
			setTermsOfUseContent(termsOfUseContent);
		}

		Long termsOfUseJournalArticleResourcePrimKey = (Long)attributes.get(
				"termsOfUseJournalArticleResourcePrimKey");

		if (termsOfUseJournalArticleResourcePrimKey != null) {
			setTermsOfUseJournalArticleResourcePrimKey(termsOfUseJournalArticleResourcePrimKey);
		}

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
		}
	}

	@Override
	public CPDefinitionVirtualSetting toEscapedModel() {
		return new CPDefinitionVirtualSettingWrapper(_cpDefinitionVirtualSetting.toEscapedModel());
	}

	@Override
	public CPDefinitionVirtualSetting toUnescapedModel() {
		return new CPDefinitionVirtualSettingWrapper(_cpDefinitionVirtualSetting.toUnescapedModel());
	}

	/**
	* Returns the terms of use required of this cp definition virtual setting.
	*
	* @return the terms of use required of this cp definition virtual setting
	*/
	@Override
	public boolean getTermsOfUseRequired() {
		return _cpDefinitionVirtualSetting.getTermsOfUseRequired();
	}

	/**
	* Returns the use sample of this cp definition virtual setting.
	*
	* @return the use sample of this cp definition virtual setting
	*/
	@Override
	public boolean getUseSample() {
		return _cpDefinitionVirtualSetting.getUseSample();
	}

	@Override
	public boolean isCachedModel() {
		return _cpDefinitionVirtualSetting.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _cpDefinitionVirtualSetting.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _cpDefinitionVirtualSetting.isNew();
	}

	/**
	* Returns <code>true</code> if this cp definition virtual setting is terms of use required.
	*
	* @return <code>true</code> if this cp definition virtual setting is terms of use required; <code>false</code> otherwise
	*/
	@Override
	public boolean isTermsOfUseRequired() {
		return _cpDefinitionVirtualSetting.isTermsOfUseRequired();
	}

	/**
	* Returns <code>true</code> if this cp definition virtual setting is use sample.
	*
	* @return <code>true</code> if this cp definition virtual setting is use sample; <code>false</code> otherwise
	*/
	@Override
	public boolean isUseSample() {
		return _cpDefinitionVirtualSetting.isUseSample();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _cpDefinitionVirtualSetting.getExpandoBridge();
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CPDefinitionVirtualSetting> toCacheModel() {
		return _cpDefinitionVirtualSetting.toCacheModel();
	}

	@Override
	public int compareTo(CPDefinitionVirtualSetting cpDefinitionVirtualSetting) {
		return _cpDefinitionVirtualSetting.compareTo(cpDefinitionVirtualSetting);
	}

	/**
	* Returns the max usages of this cp definition virtual setting.
	*
	* @return the max usages of this cp definition virtual setting
	*/
	@Override
	public int getMaxUsages() {
		return _cpDefinitionVirtualSetting.getMaxUsages();
	}

	@Override
	public int hashCode() {
		return _cpDefinitionVirtualSetting.hashCode();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _cpDefinitionVirtualSetting.getPrimaryKeyObj();
	}

	@Override
	public java.lang.Object clone() {
		return new CPDefinitionVirtualSettingWrapper((CPDefinitionVirtualSetting)_cpDefinitionVirtualSetting.clone());
	}

	/**
	* Returns the activation status of this cp definition virtual setting.
	*
	* @return the activation status of this cp definition virtual setting
	*/
	@Override
	public java.lang.String getActivationStatus() {
		return _cpDefinitionVirtualSetting.getActivationStatus();
	}

	@Override
	public java.lang.String getDefaultLanguageId() {
		return _cpDefinitionVirtualSetting.getDefaultLanguageId();
	}

	/**
	* Returns the sample url of this cp definition virtual setting.
	*
	* @return the sample url of this cp definition virtual setting
	*/
	@Override
	public java.lang.String getSampleUrl() {
		return _cpDefinitionVirtualSetting.getSampleUrl();
	}

	/**
	* Returns the terms of use content of this cp definition virtual setting.
	*
	* @return the terms of use content of this cp definition virtual setting
	*/
	@Override
	public java.lang.String getTermsOfUseContent() {
		return _cpDefinitionVirtualSetting.getTermsOfUseContent();
	}

	/**
	* Returns the localized terms of use content of this cp definition virtual setting in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @return the localized terms of use content of this cp definition virtual setting
	*/
	@Override
	public java.lang.String getTermsOfUseContent(java.lang.String languageId) {
		return _cpDefinitionVirtualSetting.getTermsOfUseContent(languageId);
	}

	/**
	* Returns the localized terms of use content of this cp definition virtual setting in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param languageId the ID of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized terms of use content of this cp definition virtual setting
	*/
	@Override
	public java.lang.String getTermsOfUseContent(java.lang.String languageId,
		boolean useDefault) {
		return _cpDefinitionVirtualSetting.getTermsOfUseContent(languageId,
			useDefault);
	}

	/**
	* Returns the localized terms of use content of this cp definition virtual setting in the language. Uses the default language if no localization exists for the requested language.
	*
	* @param locale the locale of the language
	* @return the localized terms of use content of this cp definition virtual setting
	*/
	@Override
	public java.lang.String getTermsOfUseContent(java.util.Locale locale) {
		return _cpDefinitionVirtualSetting.getTermsOfUseContent(locale);
	}

	/**
	* Returns the localized terms of use content of this cp definition virtual setting in the language, optionally using the default language if no localization exists for the requested language.
	*
	* @param locale the local of the language
	* @param useDefault whether to use the default language if no localization exists for the requested language
	* @return the localized terms of use content of this cp definition virtual setting. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	*/
	@Override
	public java.lang.String getTermsOfUseContent(java.util.Locale locale,
		boolean useDefault) {
		return _cpDefinitionVirtualSetting.getTermsOfUseContent(locale,
			useDefault);
	}

	@Override
	public java.lang.String getTermsOfUseContentCurrentLanguageId() {
		return _cpDefinitionVirtualSetting.getTermsOfUseContentCurrentLanguageId();
	}

	@Override
	public java.lang.String getTermsOfUseContentCurrentValue() {
		return _cpDefinitionVirtualSetting.getTermsOfUseContentCurrentValue();
	}

	/**
	* Returns the url of this cp definition virtual setting.
	*
	* @return the url of this cp definition virtual setting
	*/
	@Override
	public java.lang.String getUrl() {
		return _cpDefinitionVirtualSetting.getUrl();
	}

	/**
	* Returns the user name of this cp definition virtual setting.
	*
	* @return the user name of this cp definition virtual setting
	*/
	@Override
	public java.lang.String getUserName() {
		return _cpDefinitionVirtualSetting.getUserName();
	}

	/**
	* Returns the user uuid of this cp definition virtual setting.
	*
	* @return the user uuid of this cp definition virtual setting
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _cpDefinitionVirtualSetting.getUserUuid();
	}

	/**
	* Returns the uuid of this cp definition virtual setting.
	*
	* @return the uuid of this cp definition virtual setting
	*/
	@Override
	public java.lang.String getUuid() {
		return _cpDefinitionVirtualSetting.getUuid();
	}

	@Override
	public java.lang.String toString() {
		return _cpDefinitionVirtualSetting.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _cpDefinitionVirtualSetting.toXmlString();
	}

	@Override
	public java.lang.String[] getAvailableLanguageIds() {
		return _cpDefinitionVirtualSetting.getAvailableLanguageIds();
	}

	/**
	* Returns the create date of this cp definition virtual setting.
	*
	* @return the create date of this cp definition virtual setting
	*/
	@Override
	public Date getCreateDate() {
		return _cpDefinitionVirtualSetting.getCreateDate();
	}

	/**
	* Returns the last publish date of this cp definition virtual setting.
	*
	* @return the last publish date of this cp definition virtual setting
	*/
	@Override
	public Date getLastPublishDate() {
		return _cpDefinitionVirtualSetting.getLastPublishDate();
	}

	/**
	* Returns the modified date of this cp definition virtual setting.
	*
	* @return the modified date of this cp definition virtual setting
	*/
	@Override
	public Date getModifiedDate() {
		return _cpDefinitionVirtualSetting.getModifiedDate();
	}

	/**
	* Returns a map of the locales and localized terms of use contents of this cp definition virtual setting.
	*
	* @return the locales and localized terms of use contents of this cp definition virtual setting
	*/
	@Override
	public Map<java.util.Locale, java.lang.String> getTermsOfUseContentMap() {
		return _cpDefinitionVirtualSetting.getTermsOfUseContentMap();
	}

	/**
	* Returns the cp definition ID of this cp definition virtual setting.
	*
	* @return the cp definition ID of this cp definition virtual setting
	*/
	@Override
	public long getCPDefinitionId() {
		return _cpDefinitionVirtualSetting.getCPDefinitionId();
	}

	/**
	* Returns the cp definition virtual setting ID of this cp definition virtual setting.
	*
	* @return the cp definition virtual setting ID of this cp definition virtual setting
	*/
	@Override
	public long getCPDefinitionVirtualSettingId() {
		return _cpDefinitionVirtualSetting.getCPDefinitionVirtualSettingId();
	}

	/**
	* Returns the company ID of this cp definition virtual setting.
	*
	* @return the company ID of this cp definition virtual setting
	*/
	@Override
	public long getCompanyId() {
		return _cpDefinitionVirtualSetting.getCompanyId();
	}

	/**
	* Returns the duration of this cp definition virtual setting.
	*
	* @return the duration of this cp definition virtual setting
	*/
	@Override
	public long getDuration() {
		return _cpDefinitionVirtualSetting.getDuration();
	}

	/**
	* Returns the file entry ID of this cp definition virtual setting.
	*
	* @return the file entry ID of this cp definition virtual setting
	*/
	@Override
	public long getFileEntryId() {
		return _cpDefinitionVirtualSetting.getFileEntryId();
	}

	/**
	* Returns the group ID of this cp definition virtual setting.
	*
	* @return the group ID of this cp definition virtual setting
	*/
	@Override
	public long getGroupId() {
		return _cpDefinitionVirtualSetting.getGroupId();
	}

	/**
	* Returns the primary key of this cp definition virtual setting.
	*
	* @return the primary key of this cp definition virtual setting
	*/
	@Override
	public long getPrimaryKey() {
		return _cpDefinitionVirtualSetting.getPrimaryKey();
	}

	/**
	* Returns the sample file entry ID of this cp definition virtual setting.
	*
	* @return the sample file entry ID of this cp definition virtual setting
	*/
	@Override
	public long getSampleFileEntryId() {
		return _cpDefinitionVirtualSetting.getSampleFileEntryId();
	}

	/**
	* Returns the terms of use journal article resource prim key of this cp definition virtual setting.
	*
	* @return the terms of use journal article resource prim key of this cp definition virtual setting
	*/
	@Override
	public long getTermsOfUseJournalArticleResourcePrimKey() {
		return _cpDefinitionVirtualSetting.getTermsOfUseJournalArticleResourcePrimKey();
	}

	/**
	* Returns the user ID of this cp definition virtual setting.
	*
	* @return the user ID of this cp definition virtual setting
	*/
	@Override
	public long getUserId() {
		return _cpDefinitionVirtualSetting.getUserId();
	}

	@Override
	public void persist() {
		_cpDefinitionVirtualSetting.persist();
	}

	@Override
	public void prepareLocalizedFieldsForImport()
		throws com.liferay.portal.kernel.exception.LocaleException {
		_cpDefinitionVirtualSetting.prepareLocalizedFieldsForImport();
	}

	@Override
	public void prepareLocalizedFieldsForImport(
		java.util.Locale defaultImportLocale)
		throws com.liferay.portal.kernel.exception.LocaleException {
		_cpDefinitionVirtualSetting.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	/**
	* Sets the activation status of this cp definition virtual setting.
	*
	* @param activationStatus the activation status of this cp definition virtual setting
	*/
	@Override
	public void setActivationStatus(java.lang.String activationStatus) {
		_cpDefinitionVirtualSetting.setActivationStatus(activationStatus);
	}

	/**
	* Sets the cp definition ID of this cp definition virtual setting.
	*
	* @param CPDefinitionId the cp definition ID of this cp definition virtual setting
	*/
	@Override
	public void setCPDefinitionId(long CPDefinitionId) {
		_cpDefinitionVirtualSetting.setCPDefinitionId(CPDefinitionId);
	}

	/**
	* Sets the cp definition virtual setting ID of this cp definition virtual setting.
	*
	* @param CPDefinitionVirtualSettingId the cp definition virtual setting ID of this cp definition virtual setting
	*/
	@Override
	public void setCPDefinitionVirtualSettingId(
		long CPDefinitionVirtualSettingId) {
		_cpDefinitionVirtualSetting.setCPDefinitionVirtualSettingId(CPDefinitionVirtualSettingId);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_cpDefinitionVirtualSetting.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this cp definition virtual setting.
	*
	* @param companyId the company ID of this cp definition virtual setting
	*/
	@Override
	public void setCompanyId(long companyId) {
		_cpDefinitionVirtualSetting.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this cp definition virtual setting.
	*
	* @param createDate the create date of this cp definition virtual setting
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_cpDefinitionVirtualSetting.setCreateDate(createDate);
	}

	/**
	* Sets the duration of this cp definition virtual setting.
	*
	* @param duration the duration of this cp definition virtual setting
	*/
	@Override
	public void setDuration(long duration) {
		_cpDefinitionVirtualSetting.setDuration(duration);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_cpDefinitionVirtualSetting.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_cpDefinitionVirtualSetting.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_cpDefinitionVirtualSetting.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the file entry ID of this cp definition virtual setting.
	*
	* @param fileEntryId the file entry ID of this cp definition virtual setting
	*/
	@Override
	public void setFileEntryId(long fileEntryId) {
		_cpDefinitionVirtualSetting.setFileEntryId(fileEntryId);
	}

	/**
	* Sets the group ID of this cp definition virtual setting.
	*
	* @param groupId the group ID of this cp definition virtual setting
	*/
	@Override
	public void setGroupId(long groupId) {
		_cpDefinitionVirtualSetting.setGroupId(groupId);
	}

	/**
	* Sets the last publish date of this cp definition virtual setting.
	*
	* @param lastPublishDate the last publish date of this cp definition virtual setting
	*/
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		_cpDefinitionVirtualSetting.setLastPublishDate(lastPublishDate);
	}

	/**
	* Sets the max usages of this cp definition virtual setting.
	*
	* @param maxUsages the max usages of this cp definition virtual setting
	*/
	@Override
	public void setMaxUsages(int maxUsages) {
		_cpDefinitionVirtualSetting.setMaxUsages(maxUsages);
	}

	/**
	* Sets the modified date of this cp definition virtual setting.
	*
	* @param modifiedDate the modified date of this cp definition virtual setting
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_cpDefinitionVirtualSetting.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_cpDefinitionVirtualSetting.setNew(n);
	}

	/**
	* Sets the primary key of this cp definition virtual setting.
	*
	* @param primaryKey the primary key of this cp definition virtual setting
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_cpDefinitionVirtualSetting.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_cpDefinitionVirtualSetting.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the sample file entry ID of this cp definition virtual setting.
	*
	* @param sampleFileEntryId the sample file entry ID of this cp definition virtual setting
	*/
	@Override
	public void setSampleFileEntryId(long sampleFileEntryId) {
		_cpDefinitionVirtualSetting.setSampleFileEntryId(sampleFileEntryId);
	}

	/**
	* Sets the sample url of this cp definition virtual setting.
	*
	* @param sampleUrl the sample url of this cp definition virtual setting
	*/
	@Override
	public void setSampleUrl(java.lang.String sampleUrl) {
		_cpDefinitionVirtualSetting.setSampleUrl(sampleUrl);
	}

	/**
	* Sets the terms of use content of this cp definition virtual setting.
	*
	* @param termsOfUseContent the terms of use content of this cp definition virtual setting
	*/
	@Override
	public void setTermsOfUseContent(java.lang.String termsOfUseContent) {
		_cpDefinitionVirtualSetting.setTermsOfUseContent(termsOfUseContent);
	}

	/**
	* Sets the localized terms of use content of this cp definition virtual setting in the language.
	*
	* @param termsOfUseContent the localized terms of use content of this cp definition virtual setting
	* @param locale the locale of the language
	*/
	@Override
	public void setTermsOfUseContent(java.lang.String termsOfUseContent,
		java.util.Locale locale) {
		_cpDefinitionVirtualSetting.setTermsOfUseContent(termsOfUseContent,
			locale);
	}

	/**
	* Sets the localized terms of use content of this cp definition virtual setting in the language, and sets the default locale.
	*
	* @param termsOfUseContent the localized terms of use content of this cp definition virtual setting
	* @param locale the locale of the language
	* @param defaultLocale the default locale
	*/
	@Override
	public void setTermsOfUseContent(java.lang.String termsOfUseContent,
		java.util.Locale locale, java.util.Locale defaultLocale) {
		_cpDefinitionVirtualSetting.setTermsOfUseContent(termsOfUseContent,
			locale, defaultLocale);
	}

	@Override
	public void setTermsOfUseContentCurrentLanguageId(
		java.lang.String languageId) {
		_cpDefinitionVirtualSetting.setTermsOfUseContentCurrentLanguageId(languageId);
	}

	/**
	* Sets the localized terms of use contents of this cp definition virtual setting from the map of locales and localized terms of use contents.
	*
	* @param termsOfUseContentMap the locales and localized terms of use contents of this cp definition virtual setting
	*/
	@Override
	public void setTermsOfUseContentMap(
		Map<java.util.Locale, java.lang.String> termsOfUseContentMap) {
		_cpDefinitionVirtualSetting.setTermsOfUseContentMap(termsOfUseContentMap);
	}

	/**
	* Sets the localized terms of use contents of this cp definition virtual setting from the map of locales and localized terms of use contents, and sets the default locale.
	*
	* @param termsOfUseContentMap the locales and localized terms of use contents of this cp definition virtual setting
	* @param defaultLocale the default locale
	*/
	@Override
	public void setTermsOfUseContentMap(
		Map<java.util.Locale, java.lang.String> termsOfUseContentMap,
		java.util.Locale defaultLocale) {
		_cpDefinitionVirtualSetting.setTermsOfUseContentMap(termsOfUseContentMap,
			defaultLocale);
	}

	/**
	* Sets the terms of use journal article resource prim key of this cp definition virtual setting.
	*
	* @param termsOfUseJournalArticleResourcePrimKey the terms of use journal article resource prim key of this cp definition virtual setting
	*/
	@Override
	public void setTermsOfUseJournalArticleResourcePrimKey(
		long termsOfUseJournalArticleResourcePrimKey) {
		_cpDefinitionVirtualSetting.setTermsOfUseJournalArticleResourcePrimKey(termsOfUseJournalArticleResourcePrimKey);
	}

	/**
	* Sets whether this cp definition virtual setting is terms of use required.
	*
	* @param termsOfUseRequired the terms of use required of this cp definition virtual setting
	*/
	@Override
	public void setTermsOfUseRequired(boolean termsOfUseRequired) {
		_cpDefinitionVirtualSetting.setTermsOfUseRequired(termsOfUseRequired);
	}

	/**
	* Sets the url of this cp definition virtual setting.
	*
	* @param url the url of this cp definition virtual setting
	*/
	@Override
	public void setUrl(java.lang.String url) {
		_cpDefinitionVirtualSetting.setUrl(url);
	}

	/**
	* Sets whether this cp definition virtual setting is use sample.
	*
	* @param useSample the use sample of this cp definition virtual setting
	*/
	@Override
	public void setUseSample(boolean useSample) {
		_cpDefinitionVirtualSetting.setUseSample(useSample);
	}

	/**
	* Sets the user ID of this cp definition virtual setting.
	*
	* @param userId the user ID of this cp definition virtual setting
	*/
	@Override
	public void setUserId(long userId) {
		_cpDefinitionVirtualSetting.setUserId(userId);
	}

	/**
	* Sets the user name of this cp definition virtual setting.
	*
	* @param userName the user name of this cp definition virtual setting
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_cpDefinitionVirtualSetting.setUserName(userName);
	}

	/**
	* Sets the user uuid of this cp definition virtual setting.
	*
	* @param userUuid the user uuid of this cp definition virtual setting
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_cpDefinitionVirtualSetting.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this cp definition virtual setting.
	*
	* @param uuid the uuid of this cp definition virtual setting
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_cpDefinitionVirtualSetting.setUuid(uuid);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CPDefinitionVirtualSettingWrapper)) {
			return false;
		}

		CPDefinitionVirtualSettingWrapper cpDefinitionVirtualSettingWrapper = (CPDefinitionVirtualSettingWrapper)obj;

		if (Objects.equals(_cpDefinitionVirtualSetting,
					cpDefinitionVirtualSettingWrapper._cpDefinitionVirtualSetting)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _cpDefinitionVirtualSetting.getStagedModelType();
	}

	@Override
	public CPDefinitionVirtualSetting getWrappedModel() {
		return _cpDefinitionVirtualSetting;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _cpDefinitionVirtualSetting.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _cpDefinitionVirtualSetting.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_cpDefinitionVirtualSetting.resetOriginalValues();
	}

	private final CPDefinitionVirtualSetting _cpDefinitionVirtualSetting;
}