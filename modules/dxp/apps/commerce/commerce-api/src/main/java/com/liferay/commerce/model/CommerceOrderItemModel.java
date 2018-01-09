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

package com.liferay.commerce.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.expando.kernel.model.ExpandoBridge;

import com.liferay.portal.kernel.bean.AutoEscape;
import com.liferay.portal.kernel.exception.LocaleException;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.GroupedModel;
import com.liferay.portal.kernel.model.LocalizedModel;
import com.liferay.portal.kernel.model.ShardedModel;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Date;
import java.util.Locale;
import java.util.Map;

/**
 * The base model interface for the CommerceOrderItem service. Represents a row in the &quot;CommerceOrderItem&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation {@link com.liferay.commerce.model.impl.CommerceOrderItemModelImpl} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link com.liferay.commerce.model.impl.CommerceOrderItemImpl}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceOrderItem
 * @see com.liferay.commerce.model.impl.CommerceOrderItemImpl
 * @see com.liferay.commerce.model.impl.CommerceOrderItemModelImpl
 * @generated
 */
@ProviderType
public interface CommerceOrderItemModel extends BaseModel<CommerceOrderItem>,
	GroupedModel, LocalizedModel, ShardedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. All methods that expect a commerce order item model instance should use the {@link CommerceOrderItem} interface instead.
	 */

	/**
	 * Returns the primary key of this commerce order item.
	 *
	 * @return the primary key of this commerce order item
	 */
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this commerce order item.
	 *
	 * @param primaryKey the primary key of this commerce order item
	 */
	public void setPrimaryKey(long primaryKey);

	/**
	 * Returns the commerce order item ID of this commerce order item.
	 *
	 * @return the commerce order item ID of this commerce order item
	 */
	public long getCommerceOrderItemId();

	/**
	 * Sets the commerce order item ID of this commerce order item.
	 *
	 * @param commerceOrderItemId the commerce order item ID of this commerce order item
	 */
	public void setCommerceOrderItemId(long commerceOrderItemId);

	/**
	 * Returns the group ID of this commerce order item.
	 *
	 * @return the group ID of this commerce order item
	 */
	@Override
	public long getGroupId();

	/**
	 * Sets the group ID of this commerce order item.
	 *
	 * @param groupId the group ID of this commerce order item
	 */
	@Override
	public void setGroupId(long groupId);

	/**
	 * Returns the company ID of this commerce order item.
	 *
	 * @return the company ID of this commerce order item
	 */
	@Override
	public long getCompanyId();

	/**
	 * Sets the company ID of this commerce order item.
	 *
	 * @param companyId the company ID of this commerce order item
	 */
	@Override
	public void setCompanyId(long companyId);

	/**
	 * Returns the user ID of this commerce order item.
	 *
	 * @return the user ID of this commerce order item
	 */
	@Override
	public long getUserId();

	/**
	 * Sets the user ID of this commerce order item.
	 *
	 * @param userId the user ID of this commerce order item
	 */
	@Override
	public void setUserId(long userId);

	/**
	 * Returns the user uuid of this commerce order item.
	 *
	 * @return the user uuid of this commerce order item
	 */
	@Override
	public String getUserUuid();

	/**
	 * Sets the user uuid of this commerce order item.
	 *
	 * @param userUuid the user uuid of this commerce order item
	 */
	@Override
	public void setUserUuid(String userUuid);

	/**
	 * Returns the user name of this commerce order item.
	 *
	 * @return the user name of this commerce order item
	 */
	@AutoEscape
	@Override
	public String getUserName();

	/**
	 * Sets the user name of this commerce order item.
	 *
	 * @param userName the user name of this commerce order item
	 */
	@Override
	public void setUserName(String userName);

	/**
	 * Returns the create date of this commerce order item.
	 *
	 * @return the create date of this commerce order item
	 */
	@Override
	public Date getCreateDate();

	/**
	 * Sets the create date of this commerce order item.
	 *
	 * @param createDate the create date of this commerce order item
	 */
	@Override
	public void setCreateDate(Date createDate);

	/**
	 * Returns the modified date of this commerce order item.
	 *
	 * @return the modified date of this commerce order item
	 */
	@Override
	public Date getModifiedDate();

	/**
	 * Sets the modified date of this commerce order item.
	 *
	 * @param modifiedDate the modified date of this commerce order item
	 */
	@Override
	public void setModifiedDate(Date modifiedDate);

	/**
	 * Returns the commerce order ID of this commerce order item.
	 *
	 * @return the commerce order ID of this commerce order item
	 */
	public long getCommerceOrderId();

	/**
	 * Sets the commerce order ID of this commerce order item.
	 *
	 * @param commerceOrderId the commerce order ID of this commerce order item
	 */
	public void setCommerceOrderId(long commerceOrderId);

	/**
	 * Returns the cp instance ID of this commerce order item.
	 *
	 * @return the cp instance ID of this commerce order item
	 */
	public long getCPInstanceId();

	/**
	 * Sets the cp instance ID of this commerce order item.
	 *
	 * @param CPInstanceId the cp instance ID of this commerce order item
	 */
	public void setCPInstanceId(long CPInstanceId);

	/**
	 * Returns the quantity of this commerce order item.
	 *
	 * @return the quantity of this commerce order item
	 */
	public int getQuantity();

	/**
	 * Sets the quantity of this commerce order item.
	 *
	 * @param quantity the quantity of this commerce order item
	 */
	public void setQuantity(int quantity);

	/**
	 * Returns the shipped quantity of this commerce order item.
	 *
	 * @return the shipped quantity of this commerce order item
	 */
	public int getShippedQuantity();

	/**
	 * Sets the shipped quantity of this commerce order item.
	 *
	 * @param shippedQuantity the shipped quantity of this commerce order item
	 */
	public void setShippedQuantity(int shippedQuantity);

	/**
	 * Returns the json of this commerce order item.
	 *
	 * @return the json of this commerce order item
	 */
	@AutoEscape
	public String getJson();

	/**
	 * Sets the json of this commerce order item.
	 *
	 * @param json the json of this commerce order item
	 */
	public void setJson(String json);

	/**
	 * Returns the title of this commerce order item.
	 *
	 * @return the title of this commerce order item
	 */
	public String getTitle();

	/**
	 * Returns the localized title of this commerce order item in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized title of this commerce order item
	 */
	@AutoEscape
	public String getTitle(Locale locale);

	/**
	 * Returns the localized title of this commerce order item in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized title of this commerce order item. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@AutoEscape
	public String getTitle(Locale locale, boolean useDefault);

	/**
	 * Returns the localized title of this commerce order item in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized title of this commerce order item
	 */
	@AutoEscape
	public String getTitle(String languageId);

	/**
	 * Returns the localized title of this commerce order item in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized title of this commerce order item
	 */
	@AutoEscape
	public String getTitle(String languageId, boolean useDefault);

	@AutoEscape
	public String getTitleCurrentLanguageId();

	@AutoEscape
	public String getTitleCurrentValue();

	/**
	 * Returns a map of the locales and localized titles of this commerce order item.
	 *
	 * @return the locales and localized titles of this commerce order item
	 */
	public Map<Locale, String> getTitleMap();

	/**
	 * Sets the title of this commerce order item.
	 *
	 * @param title the title of this commerce order item
	 */
	public void setTitle(String title);

	/**
	 * Sets the localized title of this commerce order item in the language.
	 *
	 * @param title the localized title of this commerce order item
	 * @param locale the locale of the language
	 */
	public void setTitle(String title, Locale locale);

	/**
	 * Sets the localized title of this commerce order item in the language, and sets the default locale.
	 *
	 * @param title the localized title of this commerce order item
	 * @param locale the locale of the language
	 * @param defaultLocale the default locale
	 */
	public void setTitle(String title, Locale locale, Locale defaultLocale);

	public void setTitleCurrentLanguageId(String languageId);

	/**
	 * Sets the localized titles of this commerce order item from the map of locales and localized titles.
	 *
	 * @param titleMap the locales and localized titles of this commerce order item
	 */
	public void setTitleMap(Map<Locale, String> titleMap);

	/**
	 * Sets the localized titles of this commerce order item from the map of locales and localized titles, and sets the default locale.
	 *
	 * @param titleMap the locales and localized titles of this commerce order item
	 * @param defaultLocale the default locale
	 */
	public void setTitleMap(Map<Locale, String> titleMap, Locale defaultLocale);

	/**
	 * Returns the sku of this commerce order item.
	 *
	 * @return the sku of this commerce order item
	 */
	@AutoEscape
	public String getSku();

	/**
	 * Sets the sku of this commerce order item.
	 *
	 * @param sku the sku of this commerce order item
	 */
	public void setSku(String sku);

	/**
	 * Returns the price of this commerce order item.
	 *
	 * @return the price of this commerce order item
	 */
	public double getPrice();

	/**
	 * Sets the price of this commerce order item.
	 *
	 * @param price the price of this commerce order item
	 */
	public void setPrice(double price);

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
	public int compareTo(CommerceOrderItem commerceOrderItem);

	@Override
	public int hashCode();

	@Override
	public CacheModel<CommerceOrderItem> toCacheModel();

	@Override
	public CommerceOrderItem toEscapedModel();

	@Override
	public CommerceOrderItem toUnescapedModel();

	@Override
	public String toString();

	@Override
	public String toXmlString();
}