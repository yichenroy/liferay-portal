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

package com.liferay.layout.seo.model;

import com.liferay.portal.kernel.bean.AutoEscape;
import com.liferay.portal.kernel.exception.LocaleException;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.LocalizedModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.portal.kernel.model.ShardedModel;
import com.liferay.portal.kernel.model.StagedGroupedModel;

import java.util.Date;
import java.util.Locale;
import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The base model interface for the LayoutSEOEntry service. Represents a row in the &quot;LayoutSEOEntry&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation <code>com.liferay.layout.seo.model.impl.LayoutSEOEntryModelImpl</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in <code>com.liferay.layout.seo.model.impl.LayoutSEOEntryImpl</code>.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutSEOEntry
 * @generated
 */
@ProviderType
public interface LayoutSEOEntryModel
	extends BaseModel<LayoutSEOEntry>, LocalizedModel, MVCCModel, ShardedModel,
			StagedGroupedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. All methods that expect a layout seo entry model instance should use the {@link LayoutSEOEntry} interface instead.
	 */

	/**
	 * Returns the primary key of this layout seo entry.
	 *
	 * @return the primary key of this layout seo entry
	 */
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this layout seo entry.
	 *
	 * @param primaryKey the primary key of this layout seo entry
	 */
	public void setPrimaryKey(long primaryKey);

	/**
	 * Returns the mvcc version of this layout seo entry.
	 *
	 * @return the mvcc version of this layout seo entry
	 */
	@Override
	public long getMvccVersion();

	/**
	 * Sets the mvcc version of this layout seo entry.
	 *
	 * @param mvccVersion the mvcc version of this layout seo entry
	 */
	@Override
	public void setMvccVersion(long mvccVersion);

	/**
	 * Returns the uuid of this layout seo entry.
	 *
	 * @return the uuid of this layout seo entry
	 */
	@AutoEscape
	@Override
	public String getUuid();

	/**
	 * Sets the uuid of this layout seo entry.
	 *
	 * @param uuid the uuid of this layout seo entry
	 */
	@Override
	public void setUuid(String uuid);

	/**
	 * Returns the layout seo entry ID of this layout seo entry.
	 *
	 * @return the layout seo entry ID of this layout seo entry
	 */
	public long getLayoutSEOEntryId();

	/**
	 * Sets the layout seo entry ID of this layout seo entry.
	 *
	 * @param layoutSEOEntryId the layout seo entry ID of this layout seo entry
	 */
	public void setLayoutSEOEntryId(long layoutSEOEntryId);

	/**
	 * Returns the group ID of this layout seo entry.
	 *
	 * @return the group ID of this layout seo entry
	 */
	@Override
	public long getGroupId();

	/**
	 * Sets the group ID of this layout seo entry.
	 *
	 * @param groupId the group ID of this layout seo entry
	 */
	@Override
	public void setGroupId(long groupId);

	/**
	 * Returns the company ID of this layout seo entry.
	 *
	 * @return the company ID of this layout seo entry
	 */
	@Override
	public long getCompanyId();

	/**
	 * Sets the company ID of this layout seo entry.
	 *
	 * @param companyId the company ID of this layout seo entry
	 */
	@Override
	public void setCompanyId(long companyId);

	/**
	 * Returns the user ID of this layout seo entry.
	 *
	 * @return the user ID of this layout seo entry
	 */
	@Override
	public long getUserId();

	/**
	 * Sets the user ID of this layout seo entry.
	 *
	 * @param userId the user ID of this layout seo entry
	 */
	@Override
	public void setUserId(long userId);

	/**
	 * Returns the user uuid of this layout seo entry.
	 *
	 * @return the user uuid of this layout seo entry
	 */
	@Override
	public String getUserUuid();

	/**
	 * Sets the user uuid of this layout seo entry.
	 *
	 * @param userUuid the user uuid of this layout seo entry
	 */
	@Override
	public void setUserUuid(String userUuid);

	/**
	 * Returns the user name of this layout seo entry.
	 *
	 * @return the user name of this layout seo entry
	 */
	@AutoEscape
	@Override
	public String getUserName();

	/**
	 * Sets the user name of this layout seo entry.
	 *
	 * @param userName the user name of this layout seo entry
	 */
	@Override
	public void setUserName(String userName);

	/**
	 * Returns the create date of this layout seo entry.
	 *
	 * @return the create date of this layout seo entry
	 */
	@Override
	public Date getCreateDate();

	/**
	 * Sets the create date of this layout seo entry.
	 *
	 * @param createDate the create date of this layout seo entry
	 */
	@Override
	public void setCreateDate(Date createDate);

	/**
	 * Returns the modified date of this layout seo entry.
	 *
	 * @return the modified date of this layout seo entry
	 */
	@Override
	public Date getModifiedDate();

	/**
	 * Sets the modified date of this layout seo entry.
	 *
	 * @param modifiedDate the modified date of this layout seo entry
	 */
	@Override
	public void setModifiedDate(Date modifiedDate);

	/**
	 * Returns the private layout of this layout seo entry.
	 *
	 * @return the private layout of this layout seo entry
	 */
	public boolean getPrivateLayout();

	/**
	 * Returns <code>true</code> if this layout seo entry is private layout.
	 *
	 * @return <code>true</code> if this layout seo entry is private layout; <code>false</code> otherwise
	 */
	public boolean isPrivateLayout();

	/**
	 * Sets whether this layout seo entry is private layout.
	 *
	 * @param privateLayout the private layout of this layout seo entry
	 */
	public void setPrivateLayout(boolean privateLayout);

	/**
	 * Returns the layout ID of this layout seo entry.
	 *
	 * @return the layout ID of this layout seo entry
	 */
	public long getLayoutId();

	/**
	 * Sets the layout ID of this layout seo entry.
	 *
	 * @param layoutId the layout ID of this layout seo entry
	 */
	public void setLayoutId(long layoutId);

	/**
	 * Returns the canonical url of this layout seo entry.
	 *
	 * @return the canonical url of this layout seo entry
	 */
	public String getCanonicalURL();

	/**
	 * Returns the localized canonical url of this layout seo entry in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized canonical url of this layout seo entry
	 */
	@AutoEscape
	public String getCanonicalURL(Locale locale);

	/**
	 * Returns the localized canonical url of this layout seo entry in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized canonical url of this layout seo entry. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@AutoEscape
	public String getCanonicalURL(Locale locale, boolean useDefault);

	/**
	 * Returns the localized canonical url of this layout seo entry in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized canonical url of this layout seo entry
	 */
	@AutoEscape
	public String getCanonicalURL(String languageId);

	/**
	 * Returns the localized canonical url of this layout seo entry in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized canonical url of this layout seo entry
	 */
	@AutoEscape
	public String getCanonicalURL(String languageId, boolean useDefault);

	@AutoEscape
	public String getCanonicalURLCurrentLanguageId();

	@AutoEscape
	public String getCanonicalURLCurrentValue();

	/**
	 * Returns a map of the locales and localized canonical urls of this layout seo entry.
	 *
	 * @return the locales and localized canonical urls of this layout seo entry
	 */
	public Map<Locale, String> getCanonicalURLMap();

	/**
	 * Sets the canonical url of this layout seo entry.
	 *
	 * @param canonicalURL the canonical url of this layout seo entry
	 */
	public void setCanonicalURL(String canonicalURL);

	/**
	 * Sets the localized canonical url of this layout seo entry in the language.
	 *
	 * @param canonicalURL the localized canonical url of this layout seo entry
	 * @param locale the locale of the language
	 */
	public void setCanonicalURL(String canonicalURL, Locale locale);

	/**
	 * Sets the localized canonical url of this layout seo entry in the language, and sets the default locale.
	 *
	 * @param canonicalURL the localized canonical url of this layout seo entry
	 * @param locale the locale of the language
	 * @param defaultLocale the default locale
	 */
	public void setCanonicalURL(
		String canonicalURL, Locale locale, Locale defaultLocale);

	public void setCanonicalURLCurrentLanguageId(String languageId);

	/**
	 * Sets the localized canonical urls of this layout seo entry from the map of locales and localized canonical urls.
	 *
	 * @param canonicalURLMap the locales and localized canonical urls of this layout seo entry
	 */
	public void setCanonicalURLMap(Map<Locale, String> canonicalURLMap);

	/**
	 * Sets the localized canonical urls of this layout seo entry from the map of locales and localized canonical urls, and sets the default locale.
	 *
	 * @param canonicalURLMap the locales and localized canonical urls of this layout seo entry
	 * @param defaultLocale the default locale
	 */
	public void setCanonicalURLMap(
		Map<Locale, String> canonicalURLMap, Locale defaultLocale);

	/**
	 * Returns the canonical url enabled of this layout seo entry.
	 *
	 * @return the canonical url enabled of this layout seo entry
	 */
	public boolean getCanonicalURLEnabled();

	/**
	 * Returns <code>true</code> if this layout seo entry is canonical url enabled.
	 *
	 * @return <code>true</code> if this layout seo entry is canonical url enabled; <code>false</code> otherwise
	 */
	public boolean isCanonicalURLEnabled();

	/**
	 * Sets whether this layout seo entry is canonical url enabled.
	 *
	 * @param canonicalURLEnabled the canonical url enabled of this layout seo entry
	 */
	public void setCanonicalURLEnabled(boolean canonicalURLEnabled);

	/**
	 * Returns the ddm storage ID of this layout seo entry.
	 *
	 * @return the ddm storage ID of this layout seo entry
	 */
	public long getDDMStorageId();

	/**
	 * Sets the ddm storage ID of this layout seo entry.
	 *
	 * @param DDMStorageId the ddm storage ID of this layout seo entry
	 */
	public void setDDMStorageId(long DDMStorageId);

	/**
	 * Returns the open graph description of this layout seo entry.
	 *
	 * @return the open graph description of this layout seo entry
	 */
	public String getOpenGraphDescription();

	/**
	 * Returns the localized open graph description of this layout seo entry in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized open graph description of this layout seo entry
	 */
	@AutoEscape
	public String getOpenGraphDescription(Locale locale);

	/**
	 * Returns the localized open graph description of this layout seo entry in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized open graph description of this layout seo entry. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@AutoEscape
	public String getOpenGraphDescription(Locale locale, boolean useDefault);

	/**
	 * Returns the localized open graph description of this layout seo entry in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized open graph description of this layout seo entry
	 */
	@AutoEscape
	public String getOpenGraphDescription(String languageId);

	/**
	 * Returns the localized open graph description of this layout seo entry in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized open graph description of this layout seo entry
	 */
	@AutoEscape
	public String getOpenGraphDescription(
		String languageId, boolean useDefault);

	@AutoEscape
	public String getOpenGraphDescriptionCurrentLanguageId();

	@AutoEscape
	public String getOpenGraphDescriptionCurrentValue();

	/**
	 * Returns a map of the locales and localized open graph descriptions of this layout seo entry.
	 *
	 * @return the locales and localized open graph descriptions of this layout seo entry
	 */
	public Map<Locale, String> getOpenGraphDescriptionMap();

	/**
	 * Sets the open graph description of this layout seo entry.
	 *
	 * @param openGraphDescription the open graph description of this layout seo entry
	 */
	public void setOpenGraphDescription(String openGraphDescription);

	/**
	 * Sets the localized open graph description of this layout seo entry in the language.
	 *
	 * @param openGraphDescription the localized open graph description of this layout seo entry
	 * @param locale the locale of the language
	 */
	public void setOpenGraphDescription(
		String openGraphDescription, Locale locale);

	/**
	 * Sets the localized open graph description of this layout seo entry in the language, and sets the default locale.
	 *
	 * @param openGraphDescription the localized open graph description of this layout seo entry
	 * @param locale the locale of the language
	 * @param defaultLocale the default locale
	 */
	public void setOpenGraphDescription(
		String openGraphDescription, Locale locale, Locale defaultLocale);

	public void setOpenGraphDescriptionCurrentLanguageId(String languageId);

	/**
	 * Sets the localized open graph descriptions of this layout seo entry from the map of locales and localized open graph descriptions.
	 *
	 * @param openGraphDescriptionMap the locales and localized open graph descriptions of this layout seo entry
	 */
	public void setOpenGraphDescriptionMap(
		Map<Locale, String> openGraphDescriptionMap);

	/**
	 * Sets the localized open graph descriptions of this layout seo entry from the map of locales and localized open graph descriptions, and sets the default locale.
	 *
	 * @param openGraphDescriptionMap the locales and localized open graph descriptions of this layout seo entry
	 * @param defaultLocale the default locale
	 */
	public void setOpenGraphDescriptionMap(
		Map<Locale, String> openGraphDescriptionMap, Locale defaultLocale);

	/**
	 * Returns the open graph description enabled of this layout seo entry.
	 *
	 * @return the open graph description enabled of this layout seo entry
	 */
	public boolean getOpenGraphDescriptionEnabled();

	/**
	 * Returns <code>true</code> if this layout seo entry is open graph description enabled.
	 *
	 * @return <code>true</code> if this layout seo entry is open graph description enabled; <code>false</code> otherwise
	 */
	public boolean isOpenGraphDescriptionEnabled();

	/**
	 * Sets whether this layout seo entry is open graph description enabled.
	 *
	 * @param openGraphDescriptionEnabled the open graph description enabled of this layout seo entry
	 */
	public void setOpenGraphDescriptionEnabled(
		boolean openGraphDescriptionEnabled);

	/**
	 * Returns the open graph image alt of this layout seo entry.
	 *
	 * @return the open graph image alt of this layout seo entry
	 */
	public String getOpenGraphImageAlt();

	/**
	 * Returns the localized open graph image alt of this layout seo entry in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized open graph image alt of this layout seo entry
	 */
	@AutoEscape
	public String getOpenGraphImageAlt(Locale locale);

	/**
	 * Returns the localized open graph image alt of this layout seo entry in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized open graph image alt of this layout seo entry. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@AutoEscape
	public String getOpenGraphImageAlt(Locale locale, boolean useDefault);

	/**
	 * Returns the localized open graph image alt of this layout seo entry in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized open graph image alt of this layout seo entry
	 */
	@AutoEscape
	public String getOpenGraphImageAlt(String languageId);

	/**
	 * Returns the localized open graph image alt of this layout seo entry in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized open graph image alt of this layout seo entry
	 */
	@AutoEscape
	public String getOpenGraphImageAlt(String languageId, boolean useDefault);

	@AutoEscape
	public String getOpenGraphImageAltCurrentLanguageId();

	@AutoEscape
	public String getOpenGraphImageAltCurrentValue();

	/**
	 * Returns a map of the locales and localized open graph image alts of this layout seo entry.
	 *
	 * @return the locales and localized open graph image alts of this layout seo entry
	 */
	public Map<Locale, String> getOpenGraphImageAltMap();

	/**
	 * Sets the open graph image alt of this layout seo entry.
	 *
	 * @param openGraphImageAlt the open graph image alt of this layout seo entry
	 */
	public void setOpenGraphImageAlt(String openGraphImageAlt);

	/**
	 * Sets the localized open graph image alt of this layout seo entry in the language.
	 *
	 * @param openGraphImageAlt the localized open graph image alt of this layout seo entry
	 * @param locale the locale of the language
	 */
	public void setOpenGraphImageAlt(String openGraphImageAlt, Locale locale);

	/**
	 * Sets the localized open graph image alt of this layout seo entry in the language, and sets the default locale.
	 *
	 * @param openGraphImageAlt the localized open graph image alt of this layout seo entry
	 * @param locale the locale of the language
	 * @param defaultLocale the default locale
	 */
	public void setOpenGraphImageAlt(
		String openGraphImageAlt, Locale locale, Locale defaultLocale);

	public void setOpenGraphImageAltCurrentLanguageId(String languageId);

	/**
	 * Sets the localized open graph image alts of this layout seo entry from the map of locales and localized open graph image alts.
	 *
	 * @param openGraphImageAltMap the locales and localized open graph image alts of this layout seo entry
	 */
	public void setOpenGraphImageAltMap(
		Map<Locale, String> openGraphImageAltMap);

	/**
	 * Sets the localized open graph image alts of this layout seo entry from the map of locales and localized open graph image alts, and sets the default locale.
	 *
	 * @param openGraphImageAltMap the locales and localized open graph image alts of this layout seo entry
	 * @param defaultLocale the default locale
	 */
	public void setOpenGraphImageAltMap(
		Map<Locale, String> openGraphImageAltMap, Locale defaultLocale);

	/**
	 * Returns the open graph image file entry ID of this layout seo entry.
	 *
	 * @return the open graph image file entry ID of this layout seo entry
	 */
	public long getOpenGraphImageFileEntryId();

	/**
	 * Sets the open graph image file entry ID of this layout seo entry.
	 *
	 * @param openGraphImageFileEntryId the open graph image file entry ID of this layout seo entry
	 */
	public void setOpenGraphImageFileEntryId(long openGraphImageFileEntryId);

	/**
	 * Returns the open graph title of this layout seo entry.
	 *
	 * @return the open graph title of this layout seo entry
	 */
	public String getOpenGraphTitle();

	/**
	 * Returns the localized open graph title of this layout seo entry in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized open graph title of this layout seo entry
	 */
	@AutoEscape
	public String getOpenGraphTitle(Locale locale);

	/**
	 * Returns the localized open graph title of this layout seo entry in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized open graph title of this layout seo entry. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@AutoEscape
	public String getOpenGraphTitle(Locale locale, boolean useDefault);

	/**
	 * Returns the localized open graph title of this layout seo entry in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized open graph title of this layout seo entry
	 */
	@AutoEscape
	public String getOpenGraphTitle(String languageId);

	/**
	 * Returns the localized open graph title of this layout seo entry in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized open graph title of this layout seo entry
	 */
	@AutoEscape
	public String getOpenGraphTitle(String languageId, boolean useDefault);

	@AutoEscape
	public String getOpenGraphTitleCurrentLanguageId();

	@AutoEscape
	public String getOpenGraphTitleCurrentValue();

	/**
	 * Returns a map of the locales and localized open graph titles of this layout seo entry.
	 *
	 * @return the locales and localized open graph titles of this layout seo entry
	 */
	public Map<Locale, String> getOpenGraphTitleMap();

	/**
	 * Sets the open graph title of this layout seo entry.
	 *
	 * @param openGraphTitle the open graph title of this layout seo entry
	 */
	public void setOpenGraphTitle(String openGraphTitle);

	/**
	 * Sets the localized open graph title of this layout seo entry in the language.
	 *
	 * @param openGraphTitle the localized open graph title of this layout seo entry
	 * @param locale the locale of the language
	 */
	public void setOpenGraphTitle(String openGraphTitle, Locale locale);

	/**
	 * Sets the localized open graph title of this layout seo entry in the language, and sets the default locale.
	 *
	 * @param openGraphTitle the localized open graph title of this layout seo entry
	 * @param locale the locale of the language
	 * @param defaultLocale the default locale
	 */
	public void setOpenGraphTitle(
		String openGraphTitle, Locale locale, Locale defaultLocale);

	public void setOpenGraphTitleCurrentLanguageId(String languageId);

	/**
	 * Sets the localized open graph titles of this layout seo entry from the map of locales and localized open graph titles.
	 *
	 * @param openGraphTitleMap the locales and localized open graph titles of this layout seo entry
	 */
	public void setOpenGraphTitleMap(Map<Locale, String> openGraphTitleMap);

	/**
	 * Sets the localized open graph titles of this layout seo entry from the map of locales and localized open graph titles, and sets the default locale.
	 *
	 * @param openGraphTitleMap the locales and localized open graph titles of this layout seo entry
	 * @param defaultLocale the default locale
	 */
	public void setOpenGraphTitleMap(
		Map<Locale, String> openGraphTitleMap, Locale defaultLocale);

	/**
	 * Returns the open graph title enabled of this layout seo entry.
	 *
	 * @return the open graph title enabled of this layout seo entry
	 */
	public boolean getOpenGraphTitleEnabled();

	/**
	 * Returns <code>true</code> if this layout seo entry is open graph title enabled.
	 *
	 * @return <code>true</code> if this layout seo entry is open graph title enabled; <code>false</code> otherwise
	 */
	public boolean isOpenGraphTitleEnabled();

	/**
	 * Sets whether this layout seo entry is open graph title enabled.
	 *
	 * @param openGraphTitleEnabled the open graph title enabled of this layout seo entry
	 */
	public void setOpenGraphTitleEnabled(boolean openGraphTitleEnabled);

	/**
	 * Returns the last publish date of this layout seo entry.
	 *
	 * @return the last publish date of this layout seo entry
	 */
	@Override
	public Date getLastPublishDate();

	/**
	 * Sets the last publish date of this layout seo entry.
	 *
	 * @param lastPublishDate the last publish date of this layout seo entry
	 */
	@Override
	public void setLastPublishDate(Date lastPublishDate);

	@Override
	public String[] getAvailableLanguageIds();

	@Override
	public String getDefaultLanguageId();

	@Override
	public void prepareLocalizedFieldsForImport() throws LocaleException;

	@Override
	public void prepareLocalizedFieldsForImport(Locale defaultImportLocale)
		throws LocaleException;

}