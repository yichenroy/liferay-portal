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

package com.liferay.portal.security.service.access.policy.model.impl;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.LocaleException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSON;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.service.access.policy.model.SAPEntry;
import com.liferay.portal.security.service.access.policy.model.SAPEntryModel;
import com.liferay.portal.security.service.access.policy.model.SAPEntrySoap;

import java.io.Serializable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * The base model implementation for the SAPEntry service. Represents a row in the &quot;SAPEntry&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface <code>SAPEntryModel</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link SAPEntryImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SAPEntryImpl
 * @generated
 */
@JSON(strict = true)
public class SAPEntryModelImpl
	extends BaseModelImpl<SAPEntry> implements SAPEntryModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a sap entry model instance should use the <code>SAPEntry</code> interface instead.
	 */
	public static final String TABLE_NAME = "SAPEntry";

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", Types.VARCHAR}, {"sapEntryId", Types.BIGINT},
		{"companyId", Types.BIGINT}, {"userId", Types.BIGINT},
		{"userName", Types.VARCHAR}, {"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"allowedServiceSignatures", Types.VARCHAR},
		{"defaultSAPEntry", Types.BOOLEAN}, {"enabled", Types.BOOLEAN},
		{"name", Types.VARCHAR}, {"title", Types.VARCHAR}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
		new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("sapEntryId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("allowedServiceSignatures", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("defaultSAPEntry", Types.BOOLEAN);
		TABLE_COLUMNS_MAP.put("enabled", Types.BOOLEAN);
		TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("title", Types.VARCHAR);
	}

	public static final String TABLE_SQL_CREATE =
		"create table SAPEntry (uuid_ VARCHAR(75) null,sapEntryId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,allowedServiceSignatures STRING null,defaultSAPEntry BOOLEAN,enabled BOOLEAN,name VARCHAR(75) null,title STRING null)";

	public static final String TABLE_SQL_DROP = "drop table SAPEntry";

	public static final String ORDER_BY_JPQL =
		" ORDER BY sapEntry.sapEntryId ASC";

	public static final String ORDER_BY_SQL =
		" ORDER BY SAPEntry.sapEntryId ASC";

	public static final String DATA_SOURCE = "liferayDataSource";

	public static final String SESSION_FACTORY = "liferaySessionFactory";

	public static final String TX_MANAGER = "liferayTransactionManager";

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)
	 */
	@Deprecated
	public static final long COMPANYID_COLUMN_BITMASK = 1L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)
	 */
	@Deprecated
	public static final long DEFAULTSAPENTRY_COLUMN_BITMASK = 2L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)
	 */
	@Deprecated
	public static final long NAME_COLUMN_BITMASK = 4L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)
	 */
	@Deprecated
	public static final long UUID_COLUMN_BITMASK = 8L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *		#getColumnBitmask(String)
	 */
	@Deprecated
	public static final long SAPENTRYID_COLUMN_BITMASK = 16L;

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public static void setEntityCacheEnabled(boolean entityCacheEnabled) {
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public static void setFinderCacheEnabled(boolean finderCacheEnabled) {
	}

	/**
	 * Converts the soap model instance into a normal model instance.
	 *
	 * @param soapModel the soap model instance to convert
	 * @return the normal model instance
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public static SAPEntry toModel(SAPEntrySoap soapModel) {
		if (soapModel == null) {
			return null;
		}

		SAPEntry model = new SAPEntryImpl();

		model.setUuid(soapModel.getUuid());
		model.setSapEntryId(soapModel.getSapEntryId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setUserName(soapModel.getUserName());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setAllowedServiceSignatures(
			soapModel.getAllowedServiceSignatures());
		model.setDefaultSAPEntry(soapModel.isDefaultSAPEntry());
		model.setEnabled(soapModel.isEnabled());
		model.setName(soapModel.getName());
		model.setTitle(soapModel.getTitle());

		return model;
	}

	/**
	 * Converts the soap model instances into normal model instances.
	 *
	 * @param soapModels the soap model instances to convert
	 * @return the normal model instances
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public static List<SAPEntry> toModels(SAPEntrySoap[] soapModels) {
		if (soapModels == null) {
			return null;
		}

		List<SAPEntry> models = new ArrayList<SAPEntry>(soapModels.length);

		for (SAPEntrySoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public SAPEntryModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _sapEntryId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setSapEntryId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _sapEntryId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return SAPEntry.class;
	}

	@Override
	public String getModelClassName() {
		return SAPEntry.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<SAPEntry, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		for (Map.Entry<String, Function<SAPEntry, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<SAPEntry, Object> attributeGetterFunction =
				entry.getValue();

			attributes.put(
				attributeName, attributeGetterFunction.apply((SAPEntry)this));
		}

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<SAPEntry, Object>> attributeSetterBiConsumers =
			getAttributeSetterBiConsumers();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();

			BiConsumer<SAPEntry, Object> attributeSetterBiConsumer =
				attributeSetterBiConsumers.get(attributeName);

			if (attributeSetterBiConsumer != null) {
				attributeSetterBiConsumer.accept(
					(SAPEntry)this, entry.getValue());
			}
		}
	}

	public Map<String, Function<SAPEntry, Object>>
		getAttributeGetterFunctions() {

		return _attributeGetterFunctions;
	}

	public Map<String, BiConsumer<SAPEntry, Object>>
		getAttributeSetterBiConsumers() {

		return _attributeSetterBiConsumers;
	}

	private static Function<InvocationHandler, SAPEntry>
		_getProxyProviderFunction() {

		Class<?> proxyClass = ProxyUtil.getProxyClass(
			SAPEntry.class.getClassLoader(), SAPEntry.class,
			ModelWrapper.class);

		try {
			Constructor<SAPEntry> constructor =
				(Constructor<SAPEntry>)proxyClass.getConstructor(
					InvocationHandler.class);

			return invocationHandler -> {
				try {
					return constructor.newInstance(invocationHandler);
				}
				catch (ReflectiveOperationException
							reflectiveOperationException) {

					throw new InternalError(reflectiveOperationException);
				}
			};
		}
		catch (NoSuchMethodException noSuchMethodException) {
			throw new InternalError(noSuchMethodException);
		}
	}

	private static final Map<String, Function<SAPEntry, Object>>
		_attributeGetterFunctions;
	private static final Map<String, BiConsumer<SAPEntry, Object>>
		_attributeSetterBiConsumers;

	static {
		Map<String, Function<SAPEntry, Object>> attributeGetterFunctions =
			new LinkedHashMap<String, Function<SAPEntry, Object>>();
		Map<String, BiConsumer<SAPEntry, ?>> attributeSetterBiConsumers =
			new LinkedHashMap<String, BiConsumer<SAPEntry, ?>>();

		attributeGetterFunctions.put("uuid", SAPEntry::getUuid);
		attributeSetterBiConsumers.put(
			"uuid", (BiConsumer<SAPEntry, String>)SAPEntry::setUuid);
		attributeGetterFunctions.put("sapEntryId", SAPEntry::getSapEntryId);
		attributeSetterBiConsumers.put(
			"sapEntryId", (BiConsumer<SAPEntry, Long>)SAPEntry::setSapEntryId);
		attributeGetterFunctions.put("companyId", SAPEntry::getCompanyId);
		attributeSetterBiConsumers.put(
			"companyId", (BiConsumer<SAPEntry, Long>)SAPEntry::setCompanyId);
		attributeGetterFunctions.put("userId", SAPEntry::getUserId);
		attributeSetterBiConsumers.put(
			"userId", (BiConsumer<SAPEntry, Long>)SAPEntry::setUserId);
		attributeGetterFunctions.put("userName", SAPEntry::getUserName);
		attributeSetterBiConsumers.put(
			"userName", (BiConsumer<SAPEntry, String>)SAPEntry::setUserName);
		attributeGetterFunctions.put("createDate", SAPEntry::getCreateDate);
		attributeSetterBiConsumers.put(
			"createDate", (BiConsumer<SAPEntry, Date>)SAPEntry::setCreateDate);
		attributeGetterFunctions.put("modifiedDate", SAPEntry::getModifiedDate);
		attributeSetterBiConsumers.put(
			"modifiedDate",
			(BiConsumer<SAPEntry, Date>)SAPEntry::setModifiedDate);
		attributeGetterFunctions.put(
			"allowedServiceSignatures", SAPEntry::getAllowedServiceSignatures);
		attributeSetterBiConsumers.put(
			"allowedServiceSignatures",
			(BiConsumer<SAPEntry, String>)
				SAPEntry::setAllowedServiceSignatures);
		attributeGetterFunctions.put(
			"defaultSAPEntry", SAPEntry::getDefaultSAPEntry);
		attributeSetterBiConsumers.put(
			"defaultSAPEntry",
			(BiConsumer<SAPEntry, Boolean>)SAPEntry::setDefaultSAPEntry);
		attributeGetterFunctions.put("enabled", SAPEntry::getEnabled);
		attributeSetterBiConsumers.put(
			"enabled", (BiConsumer<SAPEntry, Boolean>)SAPEntry::setEnabled);
		attributeGetterFunctions.put("name", SAPEntry::getName);
		attributeSetterBiConsumers.put(
			"name", (BiConsumer<SAPEntry, String>)SAPEntry::setName);
		attributeGetterFunctions.put("title", SAPEntry::getTitle);
		attributeSetterBiConsumers.put(
			"title", (BiConsumer<SAPEntry, String>)SAPEntry::setTitle);

		_attributeGetterFunctions = Collections.unmodifiableMap(
			attributeGetterFunctions);
		_attributeSetterBiConsumers = Collections.unmodifiableMap(
			(Map)attributeSetterBiConsumers);
	}

	@JSON
	@Override
	public String getUuid() {
		if (_uuid == null) {
			return "";
		}
		else {
			return _uuid;
		}
	}

	@Override
	public void setUuid(String uuid) {
		if (_columnOriginalValues != null) {
			_columnBitmask |= _columnBitmasks.get("uuid_");

			if (_columnOriginalValues == Collections.EMPTY_MAP) {
				_setColumnOriginalValues();
			}
		}

		_uuid = uuid;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public String getOriginalUuid() {
		return getColumnOriginalValue("uuid_");
	}

	@JSON
	@Override
	public long getSapEntryId() {
		return _sapEntryId;
	}

	@Override
	public void setSapEntryId(long sapEntryId) {
		if (_columnOriginalValues != null) {
			_columnBitmask |= _columnBitmasks.get("sapEntryId");

			if (_columnOriginalValues == Collections.EMPTY_MAP) {
				_setColumnOriginalValues();
			}
		}

		_sapEntryId = sapEntryId;
	}

	@JSON
	@Override
	public long getCompanyId() {
		return _companyId;
	}

	@Override
	public void setCompanyId(long companyId) {
		if (_columnOriginalValues != null) {
			_columnBitmask |= _columnBitmasks.get("companyId");

			if (_columnOriginalValues == Collections.EMPTY_MAP) {
				_setColumnOriginalValues();
			}
		}

		_companyId = companyId;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public long getOriginalCompanyId() {
		return GetterUtil.getLong(getColumnOriginalValue("companyId"));
	}

	@JSON
	@Override
	public long getUserId() {
		return _userId;
	}

	@Override
	public void setUserId(long userId) {
		if (_columnOriginalValues != null) {
			_columnBitmask |= _columnBitmasks.get("userId");

			if (_columnOriginalValues == Collections.EMPTY_MAP) {
				_setColumnOriginalValues();
			}
		}

		_userId = userId;
	}

	@Override
	public String getUserUuid() {
		try {
			User user = UserLocalServiceUtil.getUserById(getUserId());

			return user.getUuid();
		}
		catch (PortalException portalException) {
			return "";
		}
	}

	@Override
	public void setUserUuid(String userUuid) {
	}

	@JSON
	@Override
	public String getUserName() {
		if (_userName == null) {
			return "";
		}
		else {
			return _userName;
		}
	}

	@Override
	public void setUserName(String userName) {
		if (_columnOriginalValues != null) {
			_columnBitmask |= _columnBitmasks.get("userName");

			if (_columnOriginalValues == Collections.EMPTY_MAP) {
				_setColumnOriginalValues();
			}
		}

		_userName = userName;
	}

	@JSON
	@Override
	public Date getCreateDate() {
		return _createDate;
	}

	@Override
	public void setCreateDate(Date createDate) {
		if (_columnOriginalValues != null) {
			_columnBitmask |= _columnBitmasks.get("createDate");

			if (_columnOriginalValues == Collections.EMPTY_MAP) {
				_setColumnOriginalValues();
			}
		}

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

		if (_columnOriginalValues != null) {
			_columnBitmask |= _columnBitmasks.get("modifiedDate");

			if (_columnOriginalValues == Collections.EMPTY_MAP) {
				_setColumnOriginalValues();
			}
		}

		_modifiedDate = modifiedDate;
	}

	@JSON
	@Override
	public String getAllowedServiceSignatures() {
		if (_allowedServiceSignatures == null) {
			return "";
		}
		else {
			return _allowedServiceSignatures;
		}
	}

	@Override
	public void setAllowedServiceSignatures(String allowedServiceSignatures) {
		if (_columnOriginalValues != null) {
			_columnBitmask |= _columnBitmasks.get("allowedServiceSignatures");

			if (_columnOriginalValues == Collections.EMPTY_MAP) {
				_setColumnOriginalValues();
			}
		}

		_allowedServiceSignatures = allowedServiceSignatures;
	}

	@JSON
	@Override
	public boolean getDefaultSAPEntry() {
		return _defaultSAPEntry;
	}

	@JSON
	@Override
	public boolean isDefaultSAPEntry() {
		return _defaultSAPEntry;
	}

	@Override
	public void setDefaultSAPEntry(boolean defaultSAPEntry) {
		if (_columnOriginalValues != null) {
			_columnBitmask |= _columnBitmasks.get("defaultSAPEntry");

			if (_columnOriginalValues == Collections.EMPTY_MAP) {
				_setColumnOriginalValues();
			}
		}

		_defaultSAPEntry = defaultSAPEntry;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public boolean getOriginalDefaultSAPEntry() {
		return GetterUtil.getBoolean(getColumnOriginalValue("defaultSAPEntry"));
	}

	@JSON
	@Override
	public boolean getEnabled() {
		return _enabled;
	}

	@JSON
	@Override
	public boolean isEnabled() {
		return _enabled;
	}

	@Override
	public void setEnabled(boolean enabled) {
		if (_columnOriginalValues != null) {
			_columnBitmask |= _columnBitmasks.get("enabled");

			if (_columnOriginalValues == Collections.EMPTY_MAP) {
				_setColumnOriginalValues();
			}
		}

		_enabled = enabled;
	}

	@JSON
	@Override
	public String getName() {
		if (_name == null) {
			return "";
		}
		else {
			return _name;
		}
	}

	@Override
	public void setName(String name) {
		if (_columnOriginalValues != null) {
			_columnBitmask |= _columnBitmasks.get("name");

			if (_columnOriginalValues == Collections.EMPTY_MAP) {
				_setColumnOriginalValues();
			}
		}

		_name = name;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public String getOriginalName() {
		return getColumnOriginalValue("name");
	}

	@JSON
	@Override
	public String getTitle() {
		if (_title == null) {
			return "";
		}
		else {
			return _title;
		}
	}

	@Override
	public String getTitle(Locale locale) {
		String languageId = LocaleUtil.toLanguageId(locale);

		return getTitle(languageId);
	}

	@Override
	public String getTitle(Locale locale, boolean useDefault) {
		String languageId = LocaleUtil.toLanguageId(locale);

		return getTitle(languageId, useDefault);
	}

	@Override
	public String getTitle(String languageId) {
		return LocalizationUtil.getLocalization(getTitle(), languageId);
	}

	@Override
	public String getTitle(String languageId, boolean useDefault) {
		return LocalizationUtil.getLocalization(
			getTitle(), languageId, useDefault);
	}

	@Override
	public String getTitleCurrentLanguageId() {
		return _titleCurrentLanguageId;
	}

	@JSON
	@Override
	public String getTitleCurrentValue() {
		Locale locale = getLocale(_titleCurrentLanguageId);

		return getTitle(locale);
	}

	@Override
	public Map<Locale, String> getTitleMap() {
		return LocalizationUtil.getLocalizationMap(getTitle());
	}

	@Override
	public void setTitle(String title) {
		if (_columnOriginalValues != null) {
			_columnBitmask |= _columnBitmasks.get("title");

			if (_columnOriginalValues == Collections.EMPTY_MAP) {
				_setColumnOriginalValues();
			}
		}

		_title = title;
	}

	@Override
	public void setTitle(String title, Locale locale) {
		setTitle(title, locale, LocaleUtil.getDefault());
	}

	@Override
	public void setTitle(String title, Locale locale, Locale defaultLocale) {
		String languageId = LocaleUtil.toLanguageId(locale);
		String defaultLanguageId = LocaleUtil.toLanguageId(defaultLocale);

		if (Validator.isNotNull(title)) {
			setTitle(
				LocalizationUtil.updateLocalization(
					getTitle(), "Title", title, languageId, defaultLanguageId));
		}
		else {
			setTitle(
				LocalizationUtil.removeLocalization(
					getTitle(), "Title", languageId));
		}
	}

	@Override
	public void setTitleCurrentLanguageId(String languageId) {
		_titleCurrentLanguageId = languageId;
	}

	@Override
	public void setTitleMap(Map<Locale, String> titleMap) {
		setTitleMap(titleMap, LocaleUtil.getDefault());
	}

	@Override
	public void setTitleMap(
		Map<Locale, String> titleMap, Locale defaultLocale) {

		if (titleMap == null) {
			return;
		}

		setTitle(
			LocalizationUtil.updateLocalization(
				titleMap, getTitle(), "Title",
				LocaleUtil.toLanguageId(defaultLocale)));
	}

	@Override
	public StagedModelType getStagedModelType() {
		return new StagedModelType(
			PortalUtil.getClassNameId(SAPEntry.class.getName()));
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(
			getCompanyId(), SAPEntry.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public String[] getAvailableLanguageIds() {
		Set<String> availableLanguageIds = new TreeSet<String>();

		Map<Locale, String> titleMap = getTitleMap();

		for (Map.Entry<Locale, String> entry : titleMap.entrySet()) {
			Locale locale = entry.getKey();
			String value = entry.getValue();

			if (Validator.isNotNull(value)) {
				availableLanguageIds.add(LocaleUtil.toLanguageId(locale));
			}
		}

		return availableLanguageIds.toArray(
			new String[availableLanguageIds.size()]);
	}

	@Override
	public String getDefaultLanguageId() {
		String xml = getTitle();

		if (xml == null) {
			return "";
		}

		Locale defaultLocale = LocaleUtil.getDefault();

		return LocalizationUtil.getDefaultLanguageId(xml, defaultLocale);
	}

	@Override
	public void prepareLocalizedFieldsForImport() throws LocaleException {
		Locale defaultLocale = LocaleUtil.fromLanguageId(
			getDefaultLanguageId());

		Locale[] availableLocales = LocaleUtil.fromLanguageIds(
			getAvailableLanguageIds());

		Locale defaultImportLocale = LocalizationUtil.getDefaultImportLocale(
			SAPEntry.class.getName(), getPrimaryKey(), defaultLocale,
			availableLocales);

		prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	@Override
	@SuppressWarnings("unused")
	public void prepareLocalizedFieldsForImport(Locale defaultImportLocale)
		throws LocaleException {

		Locale defaultLocale = LocaleUtil.getDefault();

		String modelDefaultLanguageId = getDefaultLanguageId();

		String title = getTitle(defaultLocale);

		if (Validator.isNull(title)) {
			setTitle(getTitle(modelDefaultLanguageId), defaultLocale);
		}
		else {
			setTitle(getTitle(defaultLocale), defaultLocale, defaultLocale);
		}
	}

	@Override
	public SAPEntry toEscapedModel() {
		if (_escapedModel == null) {
			Function<InvocationHandler, SAPEntry>
				escapedModelProxyProviderFunction =
					EscapedModelProxyProviderFunctionHolder.
						_escapedModelProxyProviderFunction;

			_escapedModel = escapedModelProxyProviderFunction.apply(
				new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	@Override
	public Object clone() {
		SAPEntryImpl sapEntryImpl = new SAPEntryImpl();

		sapEntryImpl.setUuid(getUuid());
		sapEntryImpl.setSapEntryId(getSapEntryId());
		sapEntryImpl.setCompanyId(getCompanyId());
		sapEntryImpl.setUserId(getUserId());
		sapEntryImpl.setUserName(getUserName());
		sapEntryImpl.setCreateDate(getCreateDate());
		sapEntryImpl.setModifiedDate(getModifiedDate());
		sapEntryImpl.setAllowedServiceSignatures(getAllowedServiceSignatures());
		sapEntryImpl.setDefaultSAPEntry(isDefaultSAPEntry());
		sapEntryImpl.setEnabled(isEnabled());
		sapEntryImpl.setName(getName());
		sapEntryImpl.setTitle(getTitle());

		sapEntryImpl.resetOriginalValues();

		return sapEntryImpl;
	}

	@Override
	public int compareTo(SAPEntry sapEntry) {
		long primaryKey = sapEntry.getPrimaryKey();

		if (getPrimaryKey() < primaryKey) {
			return -1;
		}
		else if (getPrimaryKey() > primaryKey) {
			return 1;
		}
		else {
			return 0;
		}
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof SAPEntry)) {
			return false;
		}

		SAPEntry sapEntry = (SAPEntry)object;

		long primaryKey = sapEntry.getPrimaryKey();

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

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public boolean isEntityCacheEnabled() {
		return true;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public boolean isFinderCacheEnabled() {
		return true;
	}

	@Override
	public void resetOriginalValues() {
		_columnOriginalValues = Collections.emptyMap();

		_setModifiedDate = false;

		_columnBitmask = 0;
	}

	@Override
	public CacheModel<SAPEntry> toCacheModel() {
		SAPEntryCacheModel sapEntryCacheModel = new SAPEntryCacheModel();

		sapEntryCacheModel.uuid = getUuid();

		String uuid = sapEntryCacheModel.uuid;

		if ((uuid != null) && (uuid.length() == 0)) {
			sapEntryCacheModel.uuid = null;
		}

		sapEntryCacheModel.sapEntryId = getSapEntryId();

		sapEntryCacheModel.companyId = getCompanyId();

		sapEntryCacheModel.userId = getUserId();

		sapEntryCacheModel.userName = getUserName();

		String userName = sapEntryCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			sapEntryCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			sapEntryCacheModel.createDate = createDate.getTime();
		}
		else {
			sapEntryCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			sapEntryCacheModel.modifiedDate = modifiedDate.getTime();
		}
		else {
			sapEntryCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		sapEntryCacheModel.allowedServiceSignatures =
			getAllowedServiceSignatures();

		String allowedServiceSignatures =
			sapEntryCacheModel.allowedServiceSignatures;

		if ((allowedServiceSignatures != null) &&
			(allowedServiceSignatures.length() == 0)) {

			sapEntryCacheModel.allowedServiceSignatures = null;
		}

		sapEntryCacheModel.defaultSAPEntry = isDefaultSAPEntry();

		sapEntryCacheModel.enabled = isEnabled();

		sapEntryCacheModel.name = getName();

		String name = sapEntryCacheModel.name;

		if ((name != null) && (name.length() == 0)) {
			sapEntryCacheModel.name = null;
		}

		sapEntryCacheModel.title = getTitle();

		String title = sapEntryCacheModel.title;

		if ((title != null) && (title.length() == 0)) {
			sapEntryCacheModel.title = null;
		}

		return sapEntryCacheModel;
	}

	@Override
	public String toString() {
		Map<String, Function<SAPEntry, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			4 * attributeGetterFunctions.size() + 2);

		sb.append("{");

		for (Map.Entry<String, Function<SAPEntry, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<SAPEntry, Object> attributeGetterFunction =
				entry.getValue();

			sb.append(attributeName);
			sb.append("=");
			sb.append(attributeGetterFunction.apply((SAPEntry)this));
			sb.append(", ");
		}

		if (sb.index() > 1) {
			sb.setIndex(sb.index() - 1);
		}

		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		Map<String, Function<SAPEntry, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			5 * attributeGetterFunctions.size() + 4);

		sb.append("<model><model-name>");
		sb.append(getModelClassName());
		sb.append("</model-name>");

		for (Map.Entry<String, Function<SAPEntry, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<SAPEntry, Object> attributeGetterFunction =
				entry.getValue();

			sb.append("<column><column-name>");
			sb.append(attributeName);
			sb.append("</column-name><column-value><![CDATA[");
			sb.append(attributeGetterFunction.apply((SAPEntry)this));
			sb.append("]]></column-value></column>");
		}

		sb.append("</model>");

		return sb.toString();
	}

	private static class EscapedModelProxyProviderFunctionHolder {

		private static final Function<InvocationHandler, SAPEntry>
			_escapedModelProxyProviderFunction = _getProxyProviderFunction();

	}

	private String _uuid;
	private long _sapEntryId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private boolean _setModifiedDate;
	private String _allowedServiceSignatures;
	private boolean _defaultSAPEntry;
	private boolean _enabled;
	private String _name;
	private String _title;
	private String _titleCurrentLanguageId;

	public static long getColumnBitmask(String columnName) {
		return _columnBitmasks.get(columnName);
	}

	public <T> T getColumnOriginalValue(String columnName) {
		if (_columnOriginalValues == null) {
			return null;
		}

		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		return (T)_columnOriginalValues.get(columnName);
	}

	private void _setColumnOriginalValues() {
		_columnOriginalValues = new HashMap<String, Object>();

		_columnOriginalValues.put("uuid_", _uuid);
		_columnOriginalValues.put("sapEntryId", _sapEntryId);
		_columnOriginalValues.put("companyId", _companyId);
		_columnOriginalValues.put("userId", _userId);
		_columnOriginalValues.put("userName", _userName);
		_columnOriginalValues.put("createDate", _createDate);
		_columnOriginalValues.put("modifiedDate", _modifiedDate);
		_columnOriginalValues.put(
			"allowedServiceSignatures", _allowedServiceSignatures);
		_columnOriginalValues.put("defaultSAPEntry", _defaultSAPEntry);
		_columnOriginalValues.put("enabled", _enabled);
		_columnOriginalValues.put("name", _name);
		_columnOriginalValues.put("title", _title);
	}

	private static final Map<String, Long> _columnBitmasks;

	static {
		Map<String, Long> columnBitmasks = new LinkedHashMap<>();

		columnBitmasks.put("uuid_", 1L);

		columnBitmasks.put("sapEntryId", 2L);

		columnBitmasks.put("companyId", 4L);

		columnBitmasks.put("userId", 8L);

		columnBitmasks.put("userName", 16L);

		columnBitmasks.put("createDate", 32L);

		columnBitmasks.put("modifiedDate", 64L);

		columnBitmasks.put("allowedServiceSignatures", 128L);

		columnBitmasks.put("defaultSAPEntry", 256L);

		columnBitmasks.put("enabled", 512L);

		columnBitmasks.put("name", 1024L);

		columnBitmasks.put("title", 2048L);

		_columnBitmasks = Collections.unmodifiableMap(columnBitmasks);
	}

	private transient Map<String, Object> _columnOriginalValues;
	private long _columnBitmask;
	private SAPEntry _escapedModel;

}