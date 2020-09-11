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

package com.liferay.microblogs.model.impl;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.microblogs.model.MicroblogsEntry;
import com.liferay.microblogs.model.MicroblogsEntryModel;
import com.liferay.microblogs.model.MicroblogsEntrySoap;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSON;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProxyUtil;

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
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * The base model implementation for the MicroblogsEntry service. Represents a row in the &quot;MicroblogsEntry&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface <code>MicroblogsEntryModel</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link MicroblogsEntryImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see MicroblogsEntryImpl
 * @generated
 */
@JSON(strict = true)
public class MicroblogsEntryModelImpl
	extends BaseModelImpl<MicroblogsEntry> implements MicroblogsEntryModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a microblogs entry model instance should use the <code>MicroblogsEntry</code> interface instead.
	 */
	public static final String TABLE_NAME = "MicroblogsEntry";

	public static final Object[][] TABLE_COLUMNS = {
		{"microblogsEntryId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP}, {"modifiedDate", Types.TIMESTAMP},
		{"creatorClassNameId", Types.BIGINT}, {"creatorClassPK", Types.BIGINT},
		{"content", Types.VARCHAR}, {"type_", Types.INTEGER},
		{"parentMicroblogsEntryId", Types.BIGINT},
		{"socialRelationType", Types.INTEGER}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
		new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("microblogsEntryId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("creatorClassNameId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("creatorClassPK", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("content", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("type_", Types.INTEGER);
		TABLE_COLUMNS_MAP.put("parentMicroblogsEntryId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("socialRelationType", Types.INTEGER);
	}

	public static final String TABLE_SQL_CREATE =
		"create table MicroblogsEntry (microblogsEntryId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,creatorClassNameId LONG,creatorClassPK LONG,content STRING null,type_ INTEGER,parentMicroblogsEntryId LONG,socialRelationType INTEGER)";

	public static final String TABLE_SQL_DROP = "drop table MicroblogsEntry";

	public static final String ORDER_BY_JPQL =
		" ORDER BY microblogsEntry.createDate DESC";

	public static final String ORDER_BY_SQL =
		" ORDER BY MicroblogsEntry.createDate DESC";

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
	public static final long CREATEDATE_COLUMN_BITMASK = 2L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)
	 */
	@Deprecated
	public static final long CREATORCLASSNAMEID_COLUMN_BITMASK = 4L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)
	 */
	@Deprecated
	public static final long CREATORCLASSPK_COLUMN_BITMASK = 8L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)
	 */
	@Deprecated
	public static final long PARENTMICROBLOGSENTRYID_COLUMN_BITMASK = 16L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)
	 */
	@Deprecated
	public static final long SOCIALRELATIONTYPE_COLUMN_BITMASK = 32L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)
	 */
	@Deprecated
	public static final long TYPE_COLUMN_BITMASK = 64L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)
	 */
	@Deprecated
	public static final long USERID_COLUMN_BITMASK = 128L;

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
	public static MicroblogsEntry toModel(MicroblogsEntrySoap soapModel) {
		if (soapModel == null) {
			return null;
		}

		MicroblogsEntry model = new MicroblogsEntryImpl();

		model.setMicroblogsEntryId(soapModel.getMicroblogsEntryId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setUserName(soapModel.getUserName());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setCreatorClassNameId(soapModel.getCreatorClassNameId());
		model.setCreatorClassPK(soapModel.getCreatorClassPK());
		model.setContent(soapModel.getContent());
		model.setType(soapModel.getType());
		model.setParentMicroblogsEntryId(
			soapModel.getParentMicroblogsEntryId());
		model.setSocialRelationType(soapModel.getSocialRelationType());

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
	public static List<MicroblogsEntry> toModels(
		MicroblogsEntrySoap[] soapModels) {

		if (soapModels == null) {
			return null;
		}

		List<MicroblogsEntry> models = new ArrayList<MicroblogsEntry>(
			soapModels.length);

		for (MicroblogsEntrySoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public MicroblogsEntryModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _microblogsEntryId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setMicroblogsEntryId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _microblogsEntryId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return MicroblogsEntry.class;
	}

	@Override
	public String getModelClassName() {
		return MicroblogsEntry.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<MicroblogsEntry, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		for (Map.Entry<String, Function<MicroblogsEntry, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<MicroblogsEntry, Object> attributeGetterFunction =
				entry.getValue();

			attributes.put(
				attributeName,
				attributeGetterFunction.apply((MicroblogsEntry)this));
		}

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<MicroblogsEntry, Object>>
			attributeSetterBiConsumers = getAttributeSetterBiConsumers();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();

			BiConsumer<MicroblogsEntry, Object> attributeSetterBiConsumer =
				attributeSetterBiConsumers.get(attributeName);

			if (attributeSetterBiConsumer != null) {
				attributeSetterBiConsumer.accept(
					(MicroblogsEntry)this, entry.getValue());
			}
		}
	}

	public Map<String, Function<MicroblogsEntry, Object>>
		getAttributeGetterFunctions() {

		return _attributeGetterFunctions;
	}

	public Map<String, BiConsumer<MicroblogsEntry, Object>>
		getAttributeSetterBiConsumers() {

		return _attributeSetterBiConsumers;
	}

	private static Function<InvocationHandler, MicroblogsEntry>
		_getProxyProviderFunction() {

		Class<?> proxyClass = ProxyUtil.getProxyClass(
			MicroblogsEntry.class.getClassLoader(), MicroblogsEntry.class,
			ModelWrapper.class);

		try {
			Constructor<MicroblogsEntry> constructor =
				(Constructor<MicroblogsEntry>)proxyClass.getConstructor(
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

	private static final Map<String, Function<MicroblogsEntry, Object>>
		_attributeGetterFunctions;
	private static final Map<String, BiConsumer<MicroblogsEntry, Object>>
		_attributeSetterBiConsumers;

	static {
		Map<String, Function<MicroblogsEntry, Object>>
			attributeGetterFunctions =
				new LinkedHashMap<String, Function<MicroblogsEntry, Object>>();
		Map<String, BiConsumer<MicroblogsEntry, ?>> attributeSetterBiConsumers =
			new LinkedHashMap<String, BiConsumer<MicroblogsEntry, ?>>();

		attributeGetterFunctions.put(
			"microblogsEntryId", MicroblogsEntry::getMicroblogsEntryId);
		attributeSetterBiConsumers.put(
			"microblogsEntryId",
			(BiConsumer<MicroblogsEntry, Long>)
				MicroblogsEntry::setMicroblogsEntryId);
		attributeGetterFunctions.put(
			"companyId", MicroblogsEntry::getCompanyId);
		attributeSetterBiConsumers.put(
			"companyId",
			(BiConsumer<MicroblogsEntry, Long>)MicroblogsEntry::setCompanyId);
		attributeGetterFunctions.put("userId", MicroblogsEntry::getUserId);
		attributeSetterBiConsumers.put(
			"userId",
			(BiConsumer<MicroblogsEntry, Long>)MicroblogsEntry::setUserId);
		attributeGetterFunctions.put("userName", MicroblogsEntry::getUserName);
		attributeSetterBiConsumers.put(
			"userName",
			(BiConsumer<MicroblogsEntry, String>)MicroblogsEntry::setUserName);
		attributeGetterFunctions.put(
			"createDate", MicroblogsEntry::getCreateDate);
		attributeSetterBiConsumers.put(
			"createDate",
			(BiConsumer<MicroblogsEntry, Date>)MicroblogsEntry::setCreateDate);
		attributeGetterFunctions.put(
			"modifiedDate", MicroblogsEntry::getModifiedDate);
		attributeSetterBiConsumers.put(
			"modifiedDate",
			(BiConsumer<MicroblogsEntry, Date>)
				MicroblogsEntry::setModifiedDate);
		attributeGetterFunctions.put(
			"creatorClassNameId", MicroblogsEntry::getCreatorClassNameId);
		attributeSetterBiConsumers.put(
			"creatorClassNameId",
			(BiConsumer<MicroblogsEntry, Long>)
				MicroblogsEntry::setCreatorClassNameId);
		attributeGetterFunctions.put(
			"creatorClassPK", MicroblogsEntry::getCreatorClassPK);
		attributeSetterBiConsumers.put(
			"creatorClassPK",
			(BiConsumer<MicroblogsEntry, Long>)
				MicroblogsEntry::setCreatorClassPK);
		attributeGetterFunctions.put("content", MicroblogsEntry::getContent);
		attributeSetterBiConsumers.put(
			"content",
			(BiConsumer<MicroblogsEntry, String>)MicroblogsEntry::setContent);
		attributeGetterFunctions.put("type", MicroblogsEntry::getType);
		attributeSetterBiConsumers.put(
			"type",
			(BiConsumer<MicroblogsEntry, Integer>)MicroblogsEntry::setType);
		attributeGetterFunctions.put(
			"parentMicroblogsEntryId",
			MicroblogsEntry::getParentMicroblogsEntryId);
		attributeSetterBiConsumers.put(
			"parentMicroblogsEntryId",
			(BiConsumer<MicroblogsEntry, Long>)
				MicroblogsEntry::setParentMicroblogsEntryId);
		attributeGetterFunctions.put(
			"socialRelationType", MicroblogsEntry::getSocialRelationType);
		attributeSetterBiConsumers.put(
			"socialRelationType",
			(BiConsumer<MicroblogsEntry, Integer>)
				MicroblogsEntry::setSocialRelationType);

		_attributeGetterFunctions = Collections.unmodifiableMap(
			attributeGetterFunctions);
		_attributeSetterBiConsumers = Collections.unmodifiableMap(
			(Map)attributeSetterBiConsumers);
	}

	@JSON
	@Override
	public long getMicroblogsEntryId() {
		return _microblogsEntryId;
	}

	@Override
	public void setMicroblogsEntryId(long microblogsEntryId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_microblogsEntryId = microblogsEntryId;
	}

	@JSON
	@Override
	public long getCompanyId() {
		return _companyId;
	}

	@Override
	public void setCompanyId(long companyId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_companyId = companyId;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public long getOriginalCompanyId() {
		return GetterUtil.getLong(
			this.<Long>getColumnOriginalValue("companyId"));
	}

	@JSON
	@Override
	public long getUserId() {
		return _userId;
	}

	@Override
	public void setUserId(long userId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
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

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public long getOriginalUserId() {
		return GetterUtil.getLong(this.<Long>getColumnOriginalValue("userId"));
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
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
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
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_createDate = createDate;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public Date getOriginalCreateDate() {
		return getColumnOriginalValue("createDate");
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

		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_modifiedDate = modifiedDate;
	}

	@JSON
	@Override
	public long getCreatorClassNameId() {
		return _creatorClassNameId;
	}

	@Override
	public void setCreatorClassNameId(long creatorClassNameId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_creatorClassNameId = creatorClassNameId;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public long getOriginalCreatorClassNameId() {
		return GetterUtil.getLong(
			this.<Long>getColumnOriginalValue("creatorClassNameId"));
	}

	@JSON
	@Override
	public long getCreatorClassPK() {
		return _creatorClassPK;
	}

	@Override
	public void setCreatorClassPK(long creatorClassPK) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_creatorClassPK = creatorClassPK;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public long getOriginalCreatorClassPK() {
		return GetterUtil.getLong(
			this.<Long>getColumnOriginalValue("creatorClassPK"));
	}

	@JSON
	@Override
	public String getContent() {
		if (_content == null) {
			return "";
		}
		else {
			return _content;
		}
	}

	@Override
	public void setContent(String content) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_content = content;
	}

	@JSON
	@Override
	public int getType() {
		return _type;
	}

	@Override
	public void setType(int type) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_type = type;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public int getOriginalType() {
		return GetterUtil.getInteger(
			this.<Integer>getColumnOriginalValue("type_"));
	}

	@JSON
	@Override
	public long getParentMicroblogsEntryId() {
		return _parentMicroblogsEntryId;
	}

	@Override
	public void setParentMicroblogsEntryId(long parentMicroblogsEntryId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_parentMicroblogsEntryId = parentMicroblogsEntryId;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public long getOriginalParentMicroblogsEntryId() {
		return GetterUtil.getLong(
			this.<Long>getColumnOriginalValue("parentMicroblogsEntryId"));
	}

	@JSON
	@Override
	public int getSocialRelationType() {
		return _socialRelationType;
	}

	@Override
	public void setSocialRelationType(int socialRelationType) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_socialRelationType = socialRelationType;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public int getOriginalSocialRelationType() {
		return GetterUtil.getInteger(
			this.<Integer>getColumnOriginalValue("socialRelationType"));
	}

	public long getColumnBitmask() {
		if (_columnBitmask > 0) {
			return _columnBitmask;
		}

		if ((_columnOriginalValues == null) ||
			(_columnOriginalValues == Collections.EMPTY_MAP)) {

			return 0;
		}

		for (Map.Entry<String, Object> entry :
				_columnOriginalValues.entrySet()) {

			if (entry.getValue() != getColumnValue(entry.getKey())) {
				_columnBitmask |= _columnBitmasks.get(entry.getKey());
			}
		}

		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(
			getCompanyId(), MicroblogsEntry.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public MicroblogsEntry toEscapedModel() {
		if (_escapedModel == null) {
			Function<InvocationHandler, MicroblogsEntry>
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
		MicroblogsEntryImpl microblogsEntryImpl = new MicroblogsEntryImpl();

		microblogsEntryImpl.setMicroblogsEntryId(getMicroblogsEntryId());
		microblogsEntryImpl.setCompanyId(getCompanyId());
		microblogsEntryImpl.setUserId(getUserId());
		microblogsEntryImpl.setUserName(getUserName());
		microblogsEntryImpl.setCreateDate(getCreateDate());
		microblogsEntryImpl.setModifiedDate(getModifiedDate());
		microblogsEntryImpl.setCreatorClassNameId(getCreatorClassNameId());
		microblogsEntryImpl.setCreatorClassPK(getCreatorClassPK());
		microblogsEntryImpl.setContent(getContent());
		microblogsEntryImpl.setType(getType());
		microblogsEntryImpl.setParentMicroblogsEntryId(
			getParentMicroblogsEntryId());
		microblogsEntryImpl.setSocialRelationType(getSocialRelationType());

		microblogsEntryImpl.resetOriginalValues();

		return microblogsEntryImpl;
	}

	@Override
	public int compareTo(MicroblogsEntry microblogsEntry) {
		int value = 0;

		value = DateUtil.compareTo(
			getCreateDate(), microblogsEntry.getCreateDate());

		value = value * -1;

		if (value != 0) {
			return value;
		}

		return 0;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof MicroblogsEntry)) {
			return false;
		}

		MicroblogsEntry microblogsEntry = (MicroblogsEntry)object;

		long primaryKey = microblogsEntry.getPrimaryKey();

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
	public CacheModel<MicroblogsEntry> toCacheModel() {
		MicroblogsEntryCacheModel microblogsEntryCacheModel =
			new MicroblogsEntryCacheModel();

		microblogsEntryCacheModel.microblogsEntryId = getMicroblogsEntryId();

		microblogsEntryCacheModel.companyId = getCompanyId();

		microblogsEntryCacheModel.userId = getUserId();

		microblogsEntryCacheModel.userName = getUserName();

		String userName = microblogsEntryCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			microblogsEntryCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			microblogsEntryCacheModel.createDate = createDate.getTime();
		}
		else {
			microblogsEntryCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			microblogsEntryCacheModel.modifiedDate = modifiedDate.getTime();
		}
		else {
			microblogsEntryCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		microblogsEntryCacheModel.creatorClassNameId = getCreatorClassNameId();

		microblogsEntryCacheModel.creatorClassPK = getCreatorClassPK();

		microblogsEntryCacheModel.content = getContent();

		String content = microblogsEntryCacheModel.content;

		if ((content != null) && (content.length() == 0)) {
			microblogsEntryCacheModel.content = null;
		}

		microblogsEntryCacheModel.type = getType();

		microblogsEntryCacheModel.parentMicroblogsEntryId =
			getParentMicroblogsEntryId();

		microblogsEntryCacheModel.socialRelationType = getSocialRelationType();

		return microblogsEntryCacheModel;
	}

	@Override
	public String toString() {
		Map<String, Function<MicroblogsEntry, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			4 * attributeGetterFunctions.size() + 2);

		sb.append("{");

		for (Map.Entry<String, Function<MicroblogsEntry, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<MicroblogsEntry, Object> attributeGetterFunction =
				entry.getValue();

			sb.append(attributeName);
			sb.append("=");
			sb.append(attributeGetterFunction.apply((MicroblogsEntry)this));
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
		Map<String, Function<MicroblogsEntry, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			5 * attributeGetterFunctions.size() + 4);

		sb.append("<model><model-name>");
		sb.append(getModelClassName());
		sb.append("</model-name>");

		for (Map.Entry<String, Function<MicroblogsEntry, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<MicroblogsEntry, Object> attributeGetterFunction =
				entry.getValue();

			sb.append("<column><column-name>");
			sb.append(attributeName);
			sb.append("</column-name><column-value><![CDATA[");
			sb.append(attributeGetterFunction.apply((MicroblogsEntry)this));
			sb.append("]]></column-value></column>");
		}

		sb.append("</model>");

		return sb.toString();
	}

	private static class EscapedModelProxyProviderFunctionHolder {

		private static final Function<InvocationHandler, MicroblogsEntry>
			_escapedModelProxyProviderFunction = _getProxyProviderFunction();

	}

	private long _microblogsEntryId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private boolean _setModifiedDate;
	private long _creatorClassNameId;
	private long _creatorClassPK;
	private String _content;
	private int _type;
	private long _parentMicroblogsEntryId;
	private int _socialRelationType;

	public <T> T getColumnValue(String columnName) {
		columnName = _attributeNames.getOrDefault(columnName, columnName);

		Function<MicroblogsEntry, Object> function =
			_attributeGetterFunctions.get(columnName);

		if (function == null) {
			throw new IllegalArgumentException(
				"No attribute getter function found for " + columnName);
		}

		return (T)function.apply((MicroblogsEntry)this);
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

		_columnOriginalValues.put("microblogsEntryId", _microblogsEntryId);
		_columnOriginalValues.put("companyId", _companyId);
		_columnOriginalValues.put("userId", _userId);
		_columnOriginalValues.put("userName", _userName);
		_columnOriginalValues.put("createDate", _createDate);
		_columnOriginalValues.put("modifiedDate", _modifiedDate);
		_columnOriginalValues.put("creatorClassNameId", _creatorClassNameId);
		_columnOriginalValues.put("creatorClassPK", _creatorClassPK);
		_columnOriginalValues.put("content", _content);
		_columnOriginalValues.put("type_", _type);
		_columnOriginalValues.put(
			"parentMicroblogsEntryId", _parentMicroblogsEntryId);
		_columnOriginalValues.put("socialRelationType", _socialRelationType);
	}

	private static final Map<String, String> _attributeNames;

	static {
		Map<String, String> attributeNames = new HashMap<>();

		attributeNames.put("type_", "type");

		_attributeNames = Collections.unmodifiableMap(attributeNames);
	}

	private transient Map<String, Object> _columnOriginalValues;

	public static long getColumnBitmask(String columnName) {
		return _columnBitmasks.get(columnName);
	}

	private static final Map<String, Long> _columnBitmasks;

	static {
		Map<String, Long> columnBitmasks = new HashMap<>();

		columnBitmasks.put("microblogsEntryId", 1L);

		columnBitmasks.put("companyId", 2L);

		columnBitmasks.put("userId", 4L);

		columnBitmasks.put("userName", 8L);

		columnBitmasks.put("createDate", 16L);

		columnBitmasks.put("modifiedDate", 32L);

		columnBitmasks.put("creatorClassNameId", 64L);

		columnBitmasks.put("creatorClassPK", 128L);

		columnBitmasks.put("content", 256L);

		columnBitmasks.put("type_", 512L);

		columnBitmasks.put("parentMicroblogsEntryId", 1024L);

		columnBitmasks.put("socialRelationType", 2048L);

		_columnBitmasks = Collections.unmodifiableMap(columnBitmasks);
	}

	private long _columnBitmask;
	private MicroblogsEntry _escapedModel;

}