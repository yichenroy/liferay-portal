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

package com.liferay.commerce.product.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel;
import com.liferay.commerce.product.model.CommerceProductDefinitionOptionRelModel;
import com.liferay.commerce.product.model.CommerceProductDefinitionOptionRelSoap;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.LocaleException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSON;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * The base model implementation for the CommerceProductDefinitionOptionRel service. Represents a row in the &quot;CommerceProductDefinitionOptionRel&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link CommerceProductDefinitionOptionRelModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link CommerceProductDefinitionOptionRelImpl}.
 * </p>
 *
 * @author Marco Leo
 * @see CommerceProductDefinitionOptionRelImpl
 * @see CommerceProductDefinitionOptionRel
 * @see CommerceProductDefinitionOptionRelModel
 * @generated
 */
@JSON(strict = true)
@ProviderType
public class CommerceProductDefinitionOptionRelModelImpl extends BaseModelImpl<CommerceProductDefinitionOptionRel>
	implements CommerceProductDefinitionOptionRelModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a commerce product definition option rel model instance should use the {@link CommerceProductDefinitionOptionRel} interface instead.
	 */
	public static final String TABLE_NAME = "CommerceProductDefinitionOptionRel";
	public static final Object[][] TABLE_COLUMNS = {
			{ "definitionOptionRelId", Types.BIGINT },
			{ "groupId", Types.BIGINT },
			{ "companyId", Types.BIGINT },
			{ "userId", Types.BIGINT },
			{ "userName", Types.VARCHAR },
			{ "createDate", Types.TIMESTAMP },
			{ "modifiedDate", Types.TIMESTAMP },
			{ "commerceProductDefinitionId", Types.BIGINT },
			{ "commerceProductOptionId", Types.BIGINT },
			{ "name", Types.VARCHAR },
			{ "description", Types.VARCHAR },
			{ "DDMFormFieldTypeName", Types.VARCHAR },
			{ "priority", Types.INTEGER }
		};
	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("definitionOptionRelId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("commerceProductDefinitionId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("commerceProductOptionId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("description", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("DDMFormFieldTypeName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("priority", Types.INTEGER);
	}

	public static final String TABLE_SQL_CREATE = "create table CommerceProductDefinitionOptionRel (definitionOptionRelId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,commerceProductDefinitionId LONG,commerceProductOptionId LONG,name STRING null,description STRING null,DDMFormFieldTypeName VARCHAR(75) null,priority INTEGER)";
	public static final String TABLE_SQL_DROP = "drop table CommerceProductDefinitionOptionRel";
	public static final String ORDER_BY_JPQL = " ORDER BY commerceProductDefinitionOptionRel.priority DESC";
	public static final String ORDER_BY_SQL = " ORDER BY CommerceProductDefinitionOptionRel.priority DESC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.commerce.product.service.util.ServiceProps.get(
				"value.object.entity.cache.enabled.com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.commerce.product.service.util.ServiceProps.get(
				"value.object.finder.cache.enabled.com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel"),
			true);
	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(com.liferay.commerce.product.service.util.ServiceProps.get(
				"value.object.column.bitmask.enabled.com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel"),
			true);
	public static final long COMMERCEPRODUCTDEFINITIONID_COLUMN_BITMASK = 1L;
	public static final long COMPANYID_COLUMN_BITMASK = 2L;
	public static final long GROUPID_COLUMN_BITMASK = 4L;
	public static final long PRIORITY_COLUMN_BITMASK = 8L;

	/**
	 * Converts the soap model instance into a normal model instance.
	 *
	 * @param soapModel the soap model instance to convert
	 * @return the normal model instance
	 */
	public static CommerceProductDefinitionOptionRel toModel(
		CommerceProductDefinitionOptionRelSoap soapModel) {
		if (soapModel == null) {
			return null;
		}

		CommerceProductDefinitionOptionRel model = new CommerceProductDefinitionOptionRelImpl();

		model.setCommerceProductDefinitionOptionRelId(soapModel.getCommerceProductDefinitionOptionRelId());
		model.setGroupId(soapModel.getGroupId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setUserName(soapModel.getUserName());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setCommerceProductDefinitionId(soapModel.getCommerceProductDefinitionId());
		model.setCommerceProductOptionId(soapModel.getCommerceProductOptionId());
		model.setName(soapModel.getName());
		model.setDescription(soapModel.getDescription());
		model.setDDMFormFieldTypeName(soapModel.getDDMFormFieldTypeName());
		model.setPriority(soapModel.getPriority());

		return model;
	}

	/**
	 * Converts the soap model instances into normal model instances.
	 *
	 * @param soapModels the soap model instances to convert
	 * @return the normal model instances
	 */
	public static List<CommerceProductDefinitionOptionRel> toModels(
		CommerceProductDefinitionOptionRelSoap[] soapModels) {
		if (soapModels == null) {
			return null;
		}

		List<CommerceProductDefinitionOptionRel> models = new ArrayList<CommerceProductDefinitionOptionRel>(soapModels.length);

		for (CommerceProductDefinitionOptionRelSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.commerce.product.service.util.ServiceProps.get(
				"lock.expiration.time.com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel"));

	public CommerceProductDefinitionOptionRelModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _commerceProductDefinitionOptionRelId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setCommerceProductDefinitionOptionRelId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceProductDefinitionOptionRelId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceProductDefinitionOptionRel.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceProductDefinitionOptionRel.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("commerceProductDefinitionOptionRelId",
			getCommerceProductDefinitionOptionRelId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("commerceProductDefinitionId",
			getCommerceProductDefinitionId());
		attributes.put("commerceProductOptionId", getCommerceProductOptionId());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("DDMFormFieldTypeName", getDDMFormFieldTypeName());
		attributes.put("priority", getPriority());

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long commerceProductDefinitionOptionRelId = (Long)attributes.get(
				"commerceProductDefinitionOptionRelId");

		if (commerceProductDefinitionOptionRelId != null) {
			setCommerceProductDefinitionOptionRelId(commerceProductDefinitionOptionRelId);
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

		Long commerceProductDefinitionId = (Long)attributes.get(
				"commerceProductDefinitionId");

		if (commerceProductDefinitionId != null) {
			setCommerceProductDefinitionId(commerceProductDefinitionId);
		}

		Long commerceProductOptionId = (Long)attributes.get(
				"commerceProductOptionId");

		if (commerceProductOptionId != null) {
			setCommerceProductOptionId(commerceProductOptionId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		String DDMFormFieldTypeName = (String)attributes.get(
				"DDMFormFieldTypeName");

		if (DDMFormFieldTypeName != null) {
			setDDMFormFieldTypeName(DDMFormFieldTypeName);
		}

		Integer priority = (Integer)attributes.get("priority");

		if (priority != null) {
			setPriority(priority);
		}
	}

	@JSON
	@Override
	public long getCommerceProductDefinitionOptionRelId() {
		return _commerceProductDefinitionOptionRelId;
	}

	@Override
	public void setCommerceProductDefinitionOptionRelId(
		long commerceProductDefinitionOptionRelId) {
		_commerceProductDefinitionOptionRelId = commerceProductDefinitionOptionRelId;
	}

	@JSON
	@Override
	public long getGroupId() {
		return _groupId;
	}

	@Override
	public void setGroupId(long groupId) {
		_columnBitmask |= GROUPID_COLUMN_BITMASK;

		if (!_setOriginalGroupId) {
			_setOriginalGroupId = true;

			_originalGroupId = _groupId;
		}

		_groupId = groupId;
	}

	public long getOriginalGroupId() {
		return _originalGroupId;
	}

	@JSON
	@Override
	public long getCompanyId() {
		return _companyId;
	}

	@Override
	public void setCompanyId(long companyId) {
		_columnBitmask |= COMPANYID_COLUMN_BITMASK;

		if (!_setOriginalCompanyId) {
			_setOriginalCompanyId = true;

			_originalCompanyId = _companyId;
		}

		_companyId = companyId;
	}

	public long getOriginalCompanyId() {
		return _originalCompanyId;
	}

	@JSON
	@Override
	public long getUserId() {
		return _userId;
	}

	@Override
	public void setUserId(long userId) {
		_userId = userId;
	}

	@Override
	public String getUserUuid() {
		try {
			User user = UserLocalServiceUtil.getUserById(getUserId());

			return user.getUuid();
		}
		catch (PortalException pe) {
			return StringPool.BLANK;
		}
	}

	@Override
	public void setUserUuid(String userUuid) {
	}

	@JSON
	@Override
	public String getUserName() {
		if (_userName == null) {
			return StringPool.BLANK;
		}
		else {
			return _userName;
		}
	}

	@Override
	public void setUserName(String userName) {
		_userName = userName;
	}

	@JSON
	@Override
	public Date getCreateDate() {
		return _createDate;
	}

	@Override
	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	@JSON
	@Override
	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public boolean hasSetModifiedDate() {
		return _setModifiedDate;
	}

	@Override
	public void setModifiedDate(Date modifiedDate) {
		_setModifiedDate = true;

		_modifiedDate = modifiedDate;
	}

	@JSON
	@Override
	public long getCommerceProductDefinitionId() {
		return _commerceProductDefinitionId;
	}

	@Override
	public void setCommerceProductDefinitionId(long commerceProductDefinitionId) {
		_columnBitmask |= COMMERCEPRODUCTDEFINITIONID_COLUMN_BITMASK;

		if (!_setOriginalCommerceProductDefinitionId) {
			_setOriginalCommerceProductDefinitionId = true;

			_originalCommerceProductDefinitionId = _commerceProductDefinitionId;
		}

		_commerceProductDefinitionId = commerceProductDefinitionId;
	}

	public long getOriginalCommerceProductDefinitionId() {
		return _originalCommerceProductDefinitionId;
	}

	@JSON
	@Override
	public long getCommerceProductOptionId() {
		return _commerceProductOptionId;
	}

	@Override
	public void setCommerceProductOptionId(long commerceProductOptionId) {
		_commerceProductOptionId = commerceProductOptionId;
	}

	@JSON
	@Override
	public String getName() {
		if (_name == null) {
			return StringPool.BLANK;
		}
		else {
			return _name;
		}
	}

	@Override
	public String getName(Locale locale) {
		String languageId = LocaleUtil.toLanguageId(locale);

		return getName(languageId);
	}

	@Override
	public String getName(Locale locale, boolean useDefault) {
		String languageId = LocaleUtil.toLanguageId(locale);

		return getName(languageId, useDefault);
	}

	@Override
	public String getName(String languageId) {
		return LocalizationUtil.getLocalization(getName(), languageId);
	}

	@Override
	public String getName(String languageId, boolean useDefault) {
		return LocalizationUtil.getLocalization(getName(), languageId,
			useDefault);
	}

	@Override
	public String getNameCurrentLanguageId() {
		return _nameCurrentLanguageId;
	}

	@JSON
	@Override
	public String getNameCurrentValue() {
		Locale locale = getLocale(_nameCurrentLanguageId);

		return getName(locale);
	}

	@Override
	public Map<Locale, String> getNameMap() {
		return LocalizationUtil.getLocalizationMap(getName());
	}

	@Override
	public void setName(String name) {
		_name = name;
	}

	@Override
	public void setName(String name, Locale locale) {
		setName(name, locale, LocaleUtil.getSiteDefault());
	}

	@Override
	public void setName(String name, Locale locale, Locale defaultLocale) {
		String languageId = LocaleUtil.toLanguageId(locale);
		String defaultLanguageId = LocaleUtil.toLanguageId(defaultLocale);

		if (Validator.isNotNull(name)) {
			setName(LocalizationUtil.updateLocalization(getName(), "Name",
					name, languageId, defaultLanguageId));
		}
		else {
			setName(LocalizationUtil.removeLocalization(getName(), "Name",
					languageId));
		}
	}

	@Override
	public void setNameCurrentLanguageId(String languageId) {
		_nameCurrentLanguageId = languageId;
	}

	@Override
	public void setNameMap(Map<Locale, String> nameMap) {
		setNameMap(nameMap, LocaleUtil.getSiteDefault());
	}

	@Override
	public void setNameMap(Map<Locale, String> nameMap, Locale defaultLocale) {
		if (nameMap == null) {
			return;
		}

		setName(LocalizationUtil.updateLocalization(nameMap, getName(), "Name",
				LocaleUtil.toLanguageId(defaultLocale)));
	}

	@JSON
	@Override
	public String getDescription() {
		if (_description == null) {
			return StringPool.BLANK;
		}
		else {
			return _description;
		}
	}

	@Override
	public String getDescription(Locale locale) {
		String languageId = LocaleUtil.toLanguageId(locale);

		return getDescription(languageId);
	}

	@Override
	public String getDescription(Locale locale, boolean useDefault) {
		String languageId = LocaleUtil.toLanguageId(locale);

		return getDescription(languageId, useDefault);
	}

	@Override
	public String getDescription(String languageId) {
		return LocalizationUtil.getLocalization(getDescription(), languageId);
	}

	@Override
	public String getDescription(String languageId, boolean useDefault) {
		return LocalizationUtil.getLocalization(getDescription(), languageId,
			useDefault);
	}

	@Override
	public String getDescriptionCurrentLanguageId() {
		return _descriptionCurrentLanguageId;
	}

	@JSON
	@Override
	public String getDescriptionCurrentValue() {
		Locale locale = getLocale(_descriptionCurrentLanguageId);

		return getDescription(locale);
	}

	@Override
	public Map<Locale, String> getDescriptionMap() {
		return LocalizationUtil.getLocalizationMap(getDescription());
	}

	@Override
	public void setDescription(String description) {
		_description = description;
	}

	@Override
	public void setDescription(String description, Locale locale) {
		setDescription(description, locale, LocaleUtil.getSiteDefault());
	}

	@Override
	public void setDescription(String description, Locale locale,
		Locale defaultLocale) {
		String languageId = LocaleUtil.toLanguageId(locale);
		String defaultLanguageId = LocaleUtil.toLanguageId(defaultLocale);

		if (Validator.isNotNull(description)) {
			setDescription(LocalizationUtil.updateLocalization(
					getDescription(), "Description", description, languageId,
					defaultLanguageId));
		}
		else {
			setDescription(LocalizationUtil.removeLocalization(
					getDescription(), "Description", languageId));
		}
	}

	@Override
	public void setDescriptionCurrentLanguageId(String languageId) {
		_descriptionCurrentLanguageId = languageId;
	}

	@Override
	public void setDescriptionMap(Map<Locale, String> descriptionMap) {
		setDescriptionMap(descriptionMap, LocaleUtil.getSiteDefault());
	}

	@Override
	public void setDescriptionMap(Map<Locale, String> descriptionMap,
		Locale defaultLocale) {
		if (descriptionMap == null) {
			return;
		}

		setDescription(LocalizationUtil.updateLocalization(descriptionMap,
				getDescription(), "Description",
				LocaleUtil.toLanguageId(defaultLocale)));
	}

	@JSON
	@Override
	public String getDDMFormFieldTypeName() {
		if (_DDMFormFieldTypeName == null) {
			return StringPool.BLANK;
		}
		else {
			return _DDMFormFieldTypeName;
		}
	}

	@Override
	public void setDDMFormFieldTypeName(String DDMFormFieldTypeName) {
		_DDMFormFieldTypeName = DDMFormFieldTypeName;
	}

	@JSON
	@Override
	public int getPriority() {
		return _priority;
	}

	@Override
	public void setPriority(int priority) {
		_columnBitmask = -1L;

		_priority = priority;
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(getCompanyId(),
			CommerceProductDefinitionOptionRel.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public String[] getAvailableLanguageIds() {
		Set<String> availableLanguageIds = new TreeSet<String>();

		Map<Locale, String> nameMap = getNameMap();

		for (Map.Entry<Locale, String> entry : nameMap.entrySet()) {
			Locale locale = entry.getKey();
			String value = entry.getValue();

			if (Validator.isNotNull(value)) {
				availableLanguageIds.add(LocaleUtil.toLanguageId(locale));
			}
		}

		Map<Locale, String> descriptionMap = getDescriptionMap();

		for (Map.Entry<Locale, String> entry : descriptionMap.entrySet()) {
			Locale locale = entry.getKey();
			String value = entry.getValue();

			if (Validator.isNotNull(value)) {
				availableLanguageIds.add(LocaleUtil.toLanguageId(locale));
			}
		}

		return availableLanguageIds.toArray(new String[availableLanguageIds.size()]);
	}

	@Override
	public String getDefaultLanguageId() {
		String xml = getName();

		if (xml == null) {
			return StringPool.BLANK;
		}

		Locale defaultLocale = LocaleUtil.getSiteDefault();

		return LocalizationUtil.getDefaultLanguageId(xml, defaultLocale);
	}

	@Override
	public void prepareLocalizedFieldsForImport() throws LocaleException {
		Locale defaultLocale = LocaleUtil.fromLanguageId(getDefaultLanguageId());

		Locale[] availableLocales = LocaleUtil.fromLanguageIds(getAvailableLanguageIds());

		Locale defaultImportLocale = LocalizationUtil.getDefaultImportLocale(CommerceProductDefinitionOptionRel.class.getName(),
				getPrimaryKey(), defaultLocale, availableLocales);

		prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	@Override
	@SuppressWarnings("unused")
	public void prepareLocalizedFieldsForImport(Locale defaultImportLocale)
		throws LocaleException {
		Locale defaultLocale = LocaleUtil.getSiteDefault();

		String modelDefaultLanguageId = getDefaultLanguageId();

		String name = getName(defaultLocale);

		if (Validator.isNull(name)) {
			setName(getName(modelDefaultLanguageId), defaultLocale);
		}
		else {
			setName(getName(defaultLocale), defaultLocale, defaultLocale);
		}

		String description = getDescription(defaultLocale);

		if (Validator.isNull(description)) {
			setDescription(getDescription(modelDefaultLanguageId), defaultLocale);
		}
		else {
			setDescription(getDescription(defaultLocale), defaultLocale,
				defaultLocale);
		}
	}

	@Override
	public CommerceProductDefinitionOptionRel toEscapedModel() {
		if (_escapedModel == null) {
			_escapedModel = (CommerceProductDefinitionOptionRel)ProxyUtil.newProxyInstance(_classLoader,
					_escapedModelInterfaces, new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	@Override
	public Object clone() {
		CommerceProductDefinitionOptionRelImpl commerceProductDefinitionOptionRelImpl =
			new CommerceProductDefinitionOptionRelImpl();

		commerceProductDefinitionOptionRelImpl.setCommerceProductDefinitionOptionRelId(getCommerceProductDefinitionOptionRelId());
		commerceProductDefinitionOptionRelImpl.setGroupId(getGroupId());
		commerceProductDefinitionOptionRelImpl.setCompanyId(getCompanyId());
		commerceProductDefinitionOptionRelImpl.setUserId(getUserId());
		commerceProductDefinitionOptionRelImpl.setUserName(getUserName());
		commerceProductDefinitionOptionRelImpl.setCreateDate(getCreateDate());
		commerceProductDefinitionOptionRelImpl.setModifiedDate(getModifiedDate());
		commerceProductDefinitionOptionRelImpl.setCommerceProductDefinitionId(getCommerceProductDefinitionId());
		commerceProductDefinitionOptionRelImpl.setCommerceProductOptionId(getCommerceProductOptionId());
		commerceProductDefinitionOptionRelImpl.setName(getName());
		commerceProductDefinitionOptionRelImpl.setDescription(getDescription());
		commerceProductDefinitionOptionRelImpl.setDDMFormFieldTypeName(getDDMFormFieldTypeName());
		commerceProductDefinitionOptionRelImpl.setPriority(getPriority());

		commerceProductDefinitionOptionRelImpl.resetOriginalValues();

		return commerceProductDefinitionOptionRelImpl;
	}

	@Override
	public int compareTo(
		CommerceProductDefinitionOptionRel commerceProductDefinitionOptionRel) {
		int value = 0;

		if (getPriority() < commerceProductDefinitionOptionRel.getPriority()) {
			value = -1;
		}
		else if (getPriority() > commerceProductDefinitionOptionRel.getPriority()) {
			value = 1;
		}
		else {
			value = 0;
		}

		value = value * -1;

		if (value != 0) {
			return value;
		}

		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceProductDefinitionOptionRel)) {
			return false;
		}

		CommerceProductDefinitionOptionRel commerceProductDefinitionOptionRel = (CommerceProductDefinitionOptionRel)obj;

		long primaryKey = commerceProductDefinitionOptionRel.getPrimaryKey();

		if (getPrimaryKey() == primaryKey) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return (int)getPrimaryKey();
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return ENTITY_CACHE_ENABLED;
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return FINDER_CACHE_ENABLED;
	}

	@Override
	public void resetOriginalValues() {
		CommerceProductDefinitionOptionRelModelImpl commerceProductDefinitionOptionRelModelImpl =
			this;

		commerceProductDefinitionOptionRelModelImpl._originalGroupId = commerceProductDefinitionOptionRelModelImpl._groupId;

		commerceProductDefinitionOptionRelModelImpl._setOriginalGroupId = false;

		commerceProductDefinitionOptionRelModelImpl._originalCompanyId = commerceProductDefinitionOptionRelModelImpl._companyId;

		commerceProductDefinitionOptionRelModelImpl._setOriginalCompanyId = false;

		commerceProductDefinitionOptionRelModelImpl._setModifiedDate = false;

		commerceProductDefinitionOptionRelModelImpl._originalCommerceProductDefinitionId = commerceProductDefinitionOptionRelModelImpl._commerceProductDefinitionId;

		commerceProductDefinitionOptionRelModelImpl._setOriginalCommerceProductDefinitionId = false;

		commerceProductDefinitionOptionRelModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<CommerceProductDefinitionOptionRel> toCacheModel() {
		CommerceProductDefinitionOptionRelCacheModel commerceProductDefinitionOptionRelCacheModel =
			new CommerceProductDefinitionOptionRelCacheModel();

		commerceProductDefinitionOptionRelCacheModel.commerceProductDefinitionOptionRelId = getCommerceProductDefinitionOptionRelId();

		commerceProductDefinitionOptionRelCacheModel.groupId = getGroupId();

		commerceProductDefinitionOptionRelCacheModel.companyId = getCompanyId();

		commerceProductDefinitionOptionRelCacheModel.userId = getUserId();

		commerceProductDefinitionOptionRelCacheModel.userName = getUserName();

		String userName = commerceProductDefinitionOptionRelCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			commerceProductDefinitionOptionRelCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			commerceProductDefinitionOptionRelCacheModel.createDate = createDate.getTime();
		}
		else {
			commerceProductDefinitionOptionRelCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			commerceProductDefinitionOptionRelCacheModel.modifiedDate = modifiedDate.getTime();
		}
		else {
			commerceProductDefinitionOptionRelCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		commerceProductDefinitionOptionRelCacheModel.commerceProductDefinitionId = getCommerceProductDefinitionId();

		commerceProductDefinitionOptionRelCacheModel.commerceProductOptionId = getCommerceProductOptionId();

		commerceProductDefinitionOptionRelCacheModel.name = getName();

		String name = commerceProductDefinitionOptionRelCacheModel.name;

		if ((name != null) && (name.length() == 0)) {
			commerceProductDefinitionOptionRelCacheModel.name = null;
		}

		commerceProductDefinitionOptionRelCacheModel.description = getDescription();

		String description = commerceProductDefinitionOptionRelCacheModel.description;

		if ((description != null) && (description.length() == 0)) {
			commerceProductDefinitionOptionRelCacheModel.description = null;
		}

		commerceProductDefinitionOptionRelCacheModel.DDMFormFieldTypeName = getDDMFormFieldTypeName();

		String DDMFormFieldTypeName = commerceProductDefinitionOptionRelCacheModel.DDMFormFieldTypeName;

		if ((DDMFormFieldTypeName != null) &&
				(DDMFormFieldTypeName.length() == 0)) {
			commerceProductDefinitionOptionRelCacheModel.DDMFormFieldTypeName = null;
		}

		commerceProductDefinitionOptionRelCacheModel.priority = getPriority();

		return commerceProductDefinitionOptionRelCacheModel;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(27);

		sb.append("{commerceProductDefinitionOptionRelId=");
		sb.append(getCommerceProductDefinitionOptionRelId());
		sb.append(", groupId=");
		sb.append(getGroupId());
		sb.append(", companyId=");
		sb.append(getCompanyId());
		sb.append(", userId=");
		sb.append(getUserId());
		sb.append(", userName=");
		sb.append(getUserName());
		sb.append(", createDate=");
		sb.append(getCreateDate());
		sb.append(", modifiedDate=");
		sb.append(getModifiedDate());
		sb.append(", commerceProductDefinitionId=");
		sb.append(getCommerceProductDefinitionId());
		sb.append(", commerceProductOptionId=");
		sb.append(getCommerceProductOptionId());
		sb.append(", name=");
		sb.append(getName());
		sb.append(", description=");
		sb.append(getDescription());
		sb.append(", DDMFormFieldTypeName=");
		sb.append(getDDMFormFieldTypeName());
		sb.append(", priority=");
		sb.append(getPriority());
		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		StringBundler sb = new StringBundler(43);

		sb.append("<model><model-name>");
		sb.append(
			"com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>commerceProductDefinitionOptionRelId</column-name><column-value><![CDATA[");
		sb.append(getCommerceProductDefinitionOptionRelId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>groupId</column-name><column-value><![CDATA[");
		sb.append(getGroupId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>companyId</column-name><column-value><![CDATA[");
		sb.append(getCompanyId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userId</column-name><column-value><![CDATA[");
		sb.append(getUserId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userName</column-name><column-value><![CDATA[");
		sb.append(getUserName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>createDate</column-name><column-value><![CDATA[");
		sb.append(getCreateDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>modifiedDate</column-name><column-value><![CDATA[");
		sb.append(getModifiedDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>commerceProductDefinitionId</column-name><column-value><![CDATA[");
		sb.append(getCommerceProductDefinitionId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>commerceProductOptionId</column-name><column-value><![CDATA[");
		sb.append(getCommerceProductOptionId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>name</column-name><column-value><![CDATA[");
		sb.append(getName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>description</column-name><column-value><![CDATA[");
		sb.append(getDescription());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>DDMFormFieldTypeName</column-name><column-value><![CDATA[");
		sb.append(getDDMFormFieldTypeName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>priority</column-name><column-value><![CDATA[");
		sb.append(getPriority());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private static final ClassLoader _classLoader = CommerceProductDefinitionOptionRel.class.getClassLoader();
	private static final Class<?>[] _escapedModelInterfaces = new Class[] {
			CommerceProductDefinitionOptionRel.class
		};
	private long _commerceProductDefinitionOptionRelId;
	private long _groupId;
	private long _originalGroupId;
	private boolean _setOriginalGroupId;
	private long _companyId;
	private long _originalCompanyId;
	private boolean _setOriginalCompanyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private boolean _setModifiedDate;
	private long _commerceProductDefinitionId;
	private long _originalCommerceProductDefinitionId;
	private boolean _setOriginalCommerceProductDefinitionId;
	private long _commerceProductOptionId;
	private String _name;
	private String _nameCurrentLanguageId;
	private String _description;
	private String _descriptionCurrentLanguageId;
	private String _DDMFormFieldTypeName;
	private int _priority;
	private long _columnBitmask;
	private CommerceProductDefinitionOptionRel _escapedModel;
}