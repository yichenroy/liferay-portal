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

package com.liferay.commerce.product.type.virtual.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.expando.kernel.model.ExpandoBridge;

import com.liferay.portal.kernel.bean.AutoEscape;
import com.liferay.portal.kernel.exception.LocaleException;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.LocalizedModel;
import com.liferay.portal.kernel.model.ShardedModel;
import com.liferay.portal.kernel.model.StagedGroupedModel;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Date;
import java.util.Locale;
import java.util.Map;

/**
 * The base model interface for the CPDefinitionVirtualSetting service. Represents a row in the &quot;CPDefinitionVirtualSetting&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation {@link com.liferay.commerce.product.type.virtual.model.impl.CPDefinitionVirtualSettingModelImpl} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link com.liferay.commerce.product.type.virtual.model.impl.CPDefinitionVirtualSettingImpl}.
 * </p>
 *
 * @author Marco Leo
 * @see CPDefinitionVirtualSetting
 * @see com.liferay.commerce.product.type.virtual.model.impl.CPDefinitionVirtualSettingImpl
 * @see com.liferay.commerce.product.type.virtual.model.impl.CPDefinitionVirtualSettingModelImpl
 * @generated
 */
@ProviderType
public interface CPDefinitionVirtualSettingModel extends BaseModel<CPDefinitionVirtualSetting>,
	LocalizedModel, ShardedModel, StagedGroupedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. All methods that expect a cp definition virtual setting model instance should use the {@link CPDefinitionVirtualSetting} interface instead.
	 */

	/**
	 * Returns the primary key of this cp definition virtual setting.
	 *
	 * @return the primary key of this cp definition virtual setting
	 */
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this cp definition virtual setting.
	 *
	 * @param primaryKey the primary key of this cp definition virtual setting
	 */
	public void setPrimaryKey(long primaryKey);

	/**
	 * Returns the uuid of this cp definition virtual setting.
	 *
	 * @return the uuid of this cp definition virtual setting
	 */
	@AutoEscape
	@Override
	public String getUuid();

	/**
	 * Sets the uuid of this cp definition virtual setting.
	 *
	 * @param uuid the uuid of this cp definition virtual setting
	 */
	@Override
	public void setUuid(String uuid);

	/**
	 * Returns the cp definition virtual setting ID of this cp definition virtual setting.
	 *
	 * @return the cp definition virtual setting ID of this cp definition virtual setting
	 */
	public long getCPDefinitionVirtualSettingId();

	/**
	 * Sets the cp definition virtual setting ID of this cp definition virtual setting.
	 *
	 * @param CPDefinitionVirtualSettingId the cp definition virtual setting ID of this cp definition virtual setting
	 */
	public void setCPDefinitionVirtualSettingId(
		long CPDefinitionVirtualSettingId);

	/**
	 * Returns the group ID of this cp definition virtual setting.
	 *
	 * @return the group ID of this cp definition virtual setting
	 */
	@Override
	public long getGroupId();

	/**
	 * Sets the group ID of this cp definition virtual setting.
	 *
	 * @param groupId the group ID of this cp definition virtual setting
	 */
	@Override
	public void setGroupId(long groupId);

	/**
	 * Returns the company ID of this cp definition virtual setting.
	 *
	 * @return the company ID of this cp definition virtual setting
	 */
	@Override
	public long getCompanyId();

	/**
	 * Sets the company ID of this cp definition virtual setting.
	 *
	 * @param companyId the company ID of this cp definition virtual setting
	 */
	@Override
	public void setCompanyId(long companyId);

	/**
	 * Returns the user ID of this cp definition virtual setting.
	 *
	 * @return the user ID of this cp definition virtual setting
	 */
	@Override
	public long getUserId();

	/**
	 * Sets the user ID of this cp definition virtual setting.
	 *
	 * @param userId the user ID of this cp definition virtual setting
	 */
	@Override
	public void setUserId(long userId);

	/**
	 * Returns the user uuid of this cp definition virtual setting.
	 *
	 * @return the user uuid of this cp definition virtual setting
	 */
	@Override
	public String getUserUuid();

	/**
	 * Sets the user uuid of this cp definition virtual setting.
	 *
	 * @param userUuid the user uuid of this cp definition virtual setting
	 */
	@Override
	public void setUserUuid(String userUuid);

	/**
	 * Returns the user name of this cp definition virtual setting.
	 *
	 * @return the user name of this cp definition virtual setting
	 */
	@AutoEscape
	@Override
	public String getUserName();

	/**
	 * Sets the user name of this cp definition virtual setting.
	 *
	 * @param userName the user name of this cp definition virtual setting
	 */
	@Override
	public void setUserName(String userName);

	/**
	 * Returns the create date of this cp definition virtual setting.
	 *
	 * @return the create date of this cp definition virtual setting
	 */
	@Override
	public Date getCreateDate();

	/**
	 * Sets the create date of this cp definition virtual setting.
	 *
	 * @param createDate the create date of this cp definition virtual setting
	 */
	@Override
	public void setCreateDate(Date createDate);

	/**
	 * Returns the modified date of this cp definition virtual setting.
	 *
	 * @return the modified date of this cp definition virtual setting
	 */
	@Override
	public Date getModifiedDate();

	/**
	 * Sets the modified date of this cp definition virtual setting.
	 *
	 * @param modifiedDate the modified date of this cp definition virtual setting
	 */
	@Override
	public void setModifiedDate(Date modifiedDate);

	/**
	 * Returns the cp definition ID of this cp definition virtual setting.
	 *
	 * @return the cp definition ID of this cp definition virtual setting
	 */
	public long getCPDefinitionId();

	/**
	 * Sets the cp definition ID of this cp definition virtual setting.
	 *
	 * @param CPDefinitionId the cp definition ID of this cp definition virtual setting
	 */
	public void setCPDefinitionId(long CPDefinitionId);

	/**
	 * Returns the file entry ID of this cp definition virtual setting.
	 *
	 * @return the file entry ID of this cp definition virtual setting
	 */
	public long getFileEntryId();

	/**
	 * Sets the file entry ID of this cp definition virtual setting.
	 *
	 * @param fileEntryId the file entry ID of this cp definition virtual setting
	 */
	public void setFileEntryId(long fileEntryId);

	/**
	 * Returns the url of this cp definition virtual setting.
	 *
	 * @return the url of this cp definition virtual setting
	 */
	@AutoEscape
	public String getUrl();

	/**
	 * Sets the url of this cp definition virtual setting.
	 *
	 * @param url the url of this cp definition virtual setting
	 */
	public void setUrl(String url);

	/**
	 * Returns the activation status of this cp definition virtual setting.
	 *
	 * @return the activation status of this cp definition virtual setting
	 */
	public int getActivationStatus();

	/**
	 * Sets the activation status of this cp definition virtual setting.
	 *
	 * @param activationStatus the activation status of this cp definition virtual setting
	 */
	public void setActivationStatus(int activationStatus);

	/**
	 * Returns the duration of this cp definition virtual setting.
	 *
	 * @return the duration of this cp definition virtual setting
	 */
	public long getDuration();

	/**
	 * Sets the duration of this cp definition virtual setting.
	 *
	 * @param duration the duration of this cp definition virtual setting
	 */
	public void setDuration(long duration);

	/**
	 * Returns the max usages of this cp definition virtual setting.
	 *
	 * @return the max usages of this cp definition virtual setting
	 */
	public int getMaxUsages();

	/**
	 * Sets the max usages of this cp definition virtual setting.
	 *
	 * @param maxUsages the max usages of this cp definition virtual setting
	 */
	public void setMaxUsages(int maxUsages);

	/**
	 * Returns the use sample of this cp definition virtual setting.
	 *
	 * @return the use sample of this cp definition virtual setting
	 */
	public boolean getUseSample();

	/**
	 * Returns <code>true</code> if this cp definition virtual setting is use sample.
	 *
	 * @return <code>true</code> if this cp definition virtual setting is use sample; <code>false</code> otherwise
	 */
	public boolean isUseSample();

	/**
	 * Sets whether this cp definition virtual setting is use sample.
	 *
	 * @param useSample the use sample of this cp definition virtual setting
	 */
	public void setUseSample(boolean useSample);

	/**
	 * Returns the sample file entry ID of this cp definition virtual setting.
	 *
	 * @return the sample file entry ID of this cp definition virtual setting
	 */
	public long getSampleFileEntryId();

	/**
	 * Sets the sample file entry ID of this cp definition virtual setting.
	 *
	 * @param sampleFileEntryId the sample file entry ID of this cp definition virtual setting
	 */
	public void setSampleFileEntryId(long sampleFileEntryId);

	/**
	 * Returns the sample url of this cp definition virtual setting.
	 *
	 * @return the sample url of this cp definition virtual setting
	 */
	@AutoEscape
	public String getSampleUrl();

	/**
	 * Sets the sample url of this cp definition virtual setting.
	 *
	 * @param sampleUrl the sample url of this cp definition virtual setting
	 */
	public void setSampleUrl(String sampleUrl);

	/**
	 * Returns the terms of use required of this cp definition virtual setting.
	 *
	 * @return the terms of use required of this cp definition virtual setting
	 */
	public boolean getTermsOfUseRequired();

	/**
	 * Returns <code>true</code> if this cp definition virtual setting is terms of use required.
	 *
	 * @return <code>true</code> if this cp definition virtual setting is terms of use required; <code>false</code> otherwise
	 */
	public boolean isTermsOfUseRequired();

	/**
	 * Sets whether this cp definition virtual setting is terms of use required.
	 *
	 * @param termsOfUseRequired the terms of use required of this cp definition virtual setting
	 */
	public void setTermsOfUseRequired(boolean termsOfUseRequired);

	/**
	 * Returns the terms of use content of this cp definition virtual setting.
	 *
	 * @return the terms of use content of this cp definition virtual setting
	 */
	public String getTermsOfUseContent();

	/**
	 * Returns the localized terms of use content of this cp definition virtual setting in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized terms of use content of this cp definition virtual setting
	 */
	@AutoEscape
	public String getTermsOfUseContent(Locale locale);

	/**
	 * Returns the localized terms of use content of this cp definition virtual setting in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized terms of use content of this cp definition virtual setting. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@AutoEscape
	public String getTermsOfUseContent(Locale locale, boolean useDefault);

	/**
	 * Returns the localized terms of use content of this cp definition virtual setting in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized terms of use content of this cp definition virtual setting
	 */
	@AutoEscape
	public String getTermsOfUseContent(String languageId);

	/**
	 * Returns the localized terms of use content of this cp definition virtual setting in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized terms of use content of this cp definition virtual setting
	 */
	@AutoEscape
	public String getTermsOfUseContent(String languageId, boolean useDefault);

	@AutoEscape
	public String getTermsOfUseContentCurrentLanguageId();

	@AutoEscape
	public String getTermsOfUseContentCurrentValue();

	/**
	 * Returns a map of the locales and localized terms of use contents of this cp definition virtual setting.
	 *
	 * @return the locales and localized terms of use contents of this cp definition virtual setting
	 */
	public Map<Locale, String> getTermsOfUseContentMap();

	/**
	 * Sets the terms of use content of this cp definition virtual setting.
	 *
	 * @param termsOfUseContent the terms of use content of this cp definition virtual setting
	 */
	public void setTermsOfUseContent(String termsOfUseContent);

	/**
	 * Sets the localized terms of use content of this cp definition virtual setting in the language.
	 *
	 * @param termsOfUseContent the localized terms of use content of this cp definition virtual setting
	 * @param locale the locale of the language
	 */
	public void setTermsOfUseContent(String termsOfUseContent, Locale locale);

	/**
	 * Sets the localized terms of use content of this cp definition virtual setting in the language, and sets the default locale.
	 *
	 * @param termsOfUseContent the localized terms of use content of this cp definition virtual setting
	 * @param locale the locale of the language
	 * @param defaultLocale the default locale
	 */
	public void setTermsOfUseContent(String termsOfUseContent, Locale locale,
		Locale defaultLocale);

	public void setTermsOfUseContentCurrentLanguageId(String languageId);

	/**
	 * Sets the localized terms of use contents of this cp definition virtual setting from the map of locales and localized terms of use contents.
	 *
	 * @param termsOfUseContentMap the locales and localized terms of use contents of this cp definition virtual setting
	 */
	public void setTermsOfUseContentMap(
		Map<Locale, String> termsOfUseContentMap);

	/**
	 * Sets the localized terms of use contents of this cp definition virtual setting from the map of locales and localized terms of use contents, and sets the default locale.
	 *
	 * @param termsOfUseContentMap the locales and localized terms of use contents of this cp definition virtual setting
	 * @param defaultLocale the default locale
	 */
	public void setTermsOfUseContentMap(
		Map<Locale, String> termsOfUseContentMap, Locale defaultLocale);

	/**
	 * Returns the terms of use journal article resource prim key of this cp definition virtual setting.
	 *
	 * @return the terms of use journal article resource prim key of this cp definition virtual setting
	 */
	public long getTermsOfUseJournalArticleResourcePrimKey();

	/**
	 * Sets the terms of use journal article resource prim key of this cp definition virtual setting.
	 *
	 * @param termsOfUseJournalArticleResourcePrimKey the terms of use journal article resource prim key of this cp definition virtual setting
	 */
	public void setTermsOfUseJournalArticleResourcePrimKey(
		long termsOfUseJournalArticleResourcePrimKey);

	/**
	 * Returns the last publish date of this cp definition virtual setting.
	 *
	 * @return the last publish date of this cp definition virtual setting
	 */
	@Override
	public Date getLastPublishDate();

	/**
	 * Sets the last publish date of this cp definition virtual setting.
	 *
	 * @param lastPublishDate the last publish date of this cp definition virtual setting
	 */
	@Override
	public void setLastPublishDate(Date lastPublishDate);

	@Override
	public boolean isNew();

	@Override
	public void setNew(boolean n);

	@Override
	public boolean isCachedModel();

	@Override
	public void setCachedModel(boolean cachedModel);

	@Override
	public boolean isEscapedModel();

	@Override
	public Serializable getPrimaryKeyObj();

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj);

	@Override
	public ExpandoBridge getExpandoBridge();

	@Override
	public void setExpandoBridgeAttributes(BaseModel<?> baseModel);

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge);

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext);

	@Override
	public String[] getAvailableLanguageIds();

	@Override
	public String getDefaultLanguageId();

	@Override
	public void prepareLocalizedFieldsForImport() throws LocaleException;

	@Override
	public void prepareLocalizedFieldsForImport(Locale defaultImportLocale)
		throws LocaleException;

	@Override
	public Object clone();

	@Override
	public int compareTo(CPDefinitionVirtualSetting cpDefinitionVirtualSetting);

	@Override
	public int hashCode();

	@Override
	public CacheModel<CPDefinitionVirtualSetting> toCacheModel();

	@Override
	public CPDefinitionVirtualSetting toEscapedModel();

	@Override
	public CPDefinitionVirtualSetting toUnescapedModel();

	@Override
	public String toString();

	@Override
	public String toXmlString();
}