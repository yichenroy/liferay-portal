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

package com.liferay.dispatch.model.impl;

import com.liferay.dispatch.model.DispatchTrigger;
import com.liferay.dispatch.model.DispatchTriggerModel;
import com.liferay.dispatch.model.DispatchTriggerSoap;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
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
 * The base model implementation for the DispatchTrigger service. Represents a row in the &quot;DispatchTrigger&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface <code>DispatchTriggerModel</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link DispatchTriggerImpl}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see DispatchTriggerImpl
 * @generated
 */
@JSON(strict = true)
public class DispatchTriggerModelImpl
	extends BaseModelImpl<DispatchTrigger> implements DispatchTriggerModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a dispatch trigger model instance should use the <code>DispatchTrigger</code> interface instead.
	 */
	public static final String TABLE_NAME = "DispatchTrigger";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"dispatchTriggerId", Types.BIGINT},
		{"companyId", Types.BIGINT}, {"userId", Types.BIGINT},
		{"userName", Types.VARCHAR}, {"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP}, {"active_", Types.BOOLEAN},
		{"cronExpression", Types.VARCHAR}, {"endDate", Types.TIMESTAMP},
		{"name", Types.VARCHAR}, {"startDate", Types.TIMESTAMP},
		{"system_", Types.BOOLEAN}, {"type_", Types.VARCHAR},
		{"typeSettings", Types.CLOB}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
		new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("dispatchTriggerId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("active_", Types.BOOLEAN);
		TABLE_COLUMNS_MAP.put("cronExpression", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("endDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("startDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("system_", Types.BOOLEAN);
		TABLE_COLUMNS_MAP.put("type_", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("typeSettings", Types.CLOB);
	}

	public static final String TABLE_SQL_CREATE =
		"create table DispatchTrigger (mvccVersion LONG default 0 not null,dispatchTriggerId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,active_ BOOLEAN,cronExpression VARCHAR(75) null,endDate DATE null,name VARCHAR(75) null,startDate DATE null,system_ BOOLEAN,type_ VARCHAR(75) null,typeSettings TEXT null)";

	public static final String TABLE_SQL_DROP = "drop table DispatchTrigger";

	public static final String ORDER_BY_JPQL =
		" ORDER BY dispatchTrigger.modifiedDate DESC";

	public static final String ORDER_BY_SQL =
		" ORDER BY DispatchTrigger.modifiedDate DESC";

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
	public static final long NAME_COLUMN_BITMASK = 2L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)
	 */
	@Deprecated
	public static final long TYPE_COLUMN_BITMASK = 4L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *		#getColumnBitmask(String)
	 */
	@Deprecated
	public static final long MODIFIEDDATE_COLUMN_BITMASK = 8L;

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
	public static DispatchTrigger toModel(DispatchTriggerSoap soapModel) {
		if (soapModel == null) {
			return null;
		}

		DispatchTrigger model = new DispatchTriggerImpl();

		model.setMvccVersion(soapModel.getMvccVersion());
		model.setDispatchTriggerId(soapModel.getDispatchTriggerId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setUserName(soapModel.getUserName());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setActive(soapModel.isActive());
		model.setCronExpression(soapModel.getCronExpression());
		model.setEndDate(soapModel.getEndDate());
		model.setName(soapModel.getName());
		model.setStartDate(soapModel.getStartDate());
		model.setSystem(soapModel.isSystem());
		model.setType(soapModel.getType());
		model.setTypeSettings(soapModel.getTypeSettings());

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
	public static List<DispatchTrigger> toModels(
		DispatchTriggerSoap[] soapModels) {

		if (soapModels == null) {
			return null;
		}

		List<DispatchTrigger> models = new ArrayList<DispatchTrigger>(
			soapModels.length);

		for (DispatchTriggerSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public DispatchTriggerModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _dispatchTriggerId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setDispatchTriggerId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _dispatchTriggerId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return DispatchTrigger.class;
	}

	@Override
	public String getModelClassName() {
		return DispatchTrigger.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<DispatchTrigger, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		for (Map.Entry<String, Function<DispatchTrigger, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<DispatchTrigger, Object> attributeGetterFunction =
				entry.getValue();

			attributes.put(
				attributeName,
				attributeGetterFunction.apply((DispatchTrigger)this));
		}

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<DispatchTrigger, Object>>
			attributeSetterBiConsumers = getAttributeSetterBiConsumers();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();

			BiConsumer<DispatchTrigger, Object> attributeSetterBiConsumer =
				attributeSetterBiConsumers.get(attributeName);

			if (attributeSetterBiConsumer != null) {
				attributeSetterBiConsumer.accept(
					(DispatchTrigger)this, entry.getValue());
			}
		}
	}

	public Map<String, Function<DispatchTrigger, Object>>
		getAttributeGetterFunctions() {

		return _attributeGetterFunctions;
	}

	public Map<String, BiConsumer<DispatchTrigger, Object>>
		getAttributeSetterBiConsumers() {

		return _attributeSetterBiConsumers;
	}

	private static Function<InvocationHandler, DispatchTrigger>
		_getProxyProviderFunction() {

		Class<?> proxyClass = ProxyUtil.getProxyClass(
			DispatchTrigger.class.getClassLoader(), DispatchTrigger.class,
			ModelWrapper.class);

		try {
			Constructor<DispatchTrigger> constructor =
				(Constructor<DispatchTrigger>)proxyClass.getConstructor(
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

	private static final Map<String, Function<DispatchTrigger, Object>>
		_attributeGetterFunctions;
	private static final Map<String, BiConsumer<DispatchTrigger, Object>>
		_attributeSetterBiConsumers;

	static {
		Map<String, Function<DispatchTrigger, Object>>
			attributeGetterFunctions =
				new LinkedHashMap<String, Function<DispatchTrigger, Object>>();
		Map<String, BiConsumer<DispatchTrigger, ?>> attributeSetterBiConsumers =
			new LinkedHashMap<String, BiConsumer<DispatchTrigger, ?>>();

		attributeGetterFunctions.put(
			"mvccVersion", DispatchTrigger::getMvccVersion);
		attributeSetterBiConsumers.put(
			"mvccVersion",
			(BiConsumer<DispatchTrigger, Long>)DispatchTrigger::setMvccVersion);
		attributeGetterFunctions.put(
			"dispatchTriggerId", DispatchTrigger::getDispatchTriggerId);
		attributeSetterBiConsumers.put(
			"dispatchTriggerId",
			(BiConsumer<DispatchTrigger, Long>)
				DispatchTrigger::setDispatchTriggerId);
		attributeGetterFunctions.put(
			"companyId", DispatchTrigger::getCompanyId);
		attributeSetterBiConsumers.put(
			"companyId",
			(BiConsumer<DispatchTrigger, Long>)DispatchTrigger::setCompanyId);
		attributeGetterFunctions.put("userId", DispatchTrigger::getUserId);
		attributeSetterBiConsumers.put(
			"userId",
			(BiConsumer<DispatchTrigger, Long>)DispatchTrigger::setUserId);
		attributeGetterFunctions.put("userName", DispatchTrigger::getUserName);
		attributeSetterBiConsumers.put(
			"userName",
			(BiConsumer<DispatchTrigger, String>)DispatchTrigger::setUserName);
		attributeGetterFunctions.put(
			"createDate", DispatchTrigger::getCreateDate);
		attributeSetterBiConsumers.put(
			"createDate",
			(BiConsumer<DispatchTrigger, Date>)DispatchTrigger::setCreateDate);
		attributeGetterFunctions.put(
			"modifiedDate", DispatchTrigger::getModifiedDate);
		attributeSetterBiConsumers.put(
			"modifiedDate",
			(BiConsumer<DispatchTrigger, Date>)
				DispatchTrigger::setModifiedDate);
		attributeGetterFunctions.put("active", DispatchTrigger::getActive);
		attributeSetterBiConsumers.put(
			"active",
			(BiConsumer<DispatchTrigger, Boolean>)DispatchTrigger::setActive);
		attributeGetterFunctions.put(
			"cronExpression", DispatchTrigger::getCronExpression);
		attributeSetterBiConsumers.put(
			"cronExpression",
			(BiConsumer<DispatchTrigger, String>)
				DispatchTrigger::setCronExpression);
		attributeGetterFunctions.put("endDate", DispatchTrigger::getEndDate);
		attributeSetterBiConsumers.put(
			"endDate",
			(BiConsumer<DispatchTrigger, Date>)DispatchTrigger::setEndDate);
		attributeGetterFunctions.put("name", DispatchTrigger::getName);
		attributeSetterBiConsumers.put(
			"name",
			(BiConsumer<DispatchTrigger, String>)DispatchTrigger::setName);
		attributeGetterFunctions.put(
			"startDate", DispatchTrigger::getStartDate);
		attributeSetterBiConsumers.put(
			"startDate",
			(BiConsumer<DispatchTrigger, Date>)DispatchTrigger::setStartDate);
		attributeGetterFunctions.put("system", DispatchTrigger::getSystem);
		attributeSetterBiConsumers.put(
			"system",
			(BiConsumer<DispatchTrigger, Boolean>)DispatchTrigger::setSystem);
		attributeGetterFunctions.put("type", DispatchTrigger::getType);
		attributeSetterBiConsumers.put(
			"type",
			(BiConsumer<DispatchTrigger, String>)DispatchTrigger::setType);
		attributeGetterFunctions.put(
			"typeSettings", DispatchTrigger::getTypeSettings);
		attributeSetterBiConsumers.put(
			"typeSettings",
			(BiConsumer<DispatchTrigger, String>)
				DispatchTrigger::setTypeSettings);

		_attributeGetterFunctions = Collections.unmodifiableMap(
			attributeGetterFunctions);
		_attributeSetterBiConsumers = Collections.unmodifiableMap(
			(Map)attributeSetterBiConsumers);
	}

	@JSON
	@Override
	public long getMvccVersion() {
		return _mvccVersion;
	}

	@Override
	public void setMvccVersion(long mvccVersion) {
		if (_columnOriginalValues != null) {
			_columnBitmask |= _columnBitmasks.get("mvccVersion");

			if (_columnOriginalValues == Collections.EMPTY_MAP) {
				_setColumnOriginalValues();
			}
		}

		_mvccVersion = mvccVersion;
	}

	@JSON
	@Override
	public long getDispatchTriggerId() {
		return _dispatchTriggerId;
	}

	@Override
	public void setDispatchTriggerId(long dispatchTriggerId) {
		if (_columnOriginalValues != null) {
			_columnBitmask |= _columnBitmasks.get("dispatchTriggerId");

			if (_columnOriginalValues == Collections.EMPTY_MAP) {
				_setColumnOriginalValues();
			}
		}

		_dispatchTriggerId = dispatchTriggerId;
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
	public boolean getActive() {
		return _active;
	}

	@JSON
	@Override
	public boolean isActive() {
		return _active;
	}

	@Override
	public void setActive(boolean active) {
		if (_columnOriginalValues != null) {
			_columnBitmask |= _columnBitmasks.get("active_");

			if (_columnOriginalValues == Collections.EMPTY_MAP) {
				_setColumnOriginalValues();
			}
		}

		_active = active;
	}

	@JSON
	@Override
	public String getCronExpression() {
		if (_cronExpression == null) {
			return "";
		}
		else {
			return _cronExpression;
		}
	}

	@Override
	public void setCronExpression(String cronExpression) {
		if (_columnOriginalValues != null) {
			_columnBitmask |= _columnBitmasks.get("cronExpression");

			if (_columnOriginalValues == Collections.EMPTY_MAP) {
				_setColumnOriginalValues();
			}
		}

		_cronExpression = cronExpression;
	}

	@JSON
	@Override
	public Date getEndDate() {
		return _endDate;
	}

	@Override
	public void setEndDate(Date endDate) {
		if (_columnOriginalValues != null) {
			_columnBitmask |= _columnBitmasks.get("endDate");

			if (_columnOriginalValues == Collections.EMPTY_MAP) {
				_setColumnOriginalValues();
			}
		}

		_endDate = endDate;
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
	public Date getStartDate() {
		return _startDate;
	}

	@Override
	public void setStartDate(Date startDate) {
		if (_columnOriginalValues != null) {
			_columnBitmask |= _columnBitmasks.get("startDate");

			if (_columnOriginalValues == Collections.EMPTY_MAP) {
				_setColumnOriginalValues();
			}
		}

		_startDate = startDate;
	}

	@JSON
	@Override
	public boolean getSystem() {
		return _system;
	}

	@JSON
	@Override
	public boolean isSystem() {
		return _system;
	}

	@Override
	public void setSystem(boolean system) {
		if (_columnOriginalValues != null) {
			_columnBitmask |= _columnBitmasks.get("system_");

			if (_columnOriginalValues == Collections.EMPTY_MAP) {
				_setColumnOriginalValues();
			}
		}

		_system = system;
	}

	@JSON
	@Override
	public String getType() {
		if (_type == null) {
			return "";
		}
		else {
			return _type;
		}
	}

	@Override
	public void setType(String type) {
		if (_columnOriginalValues != null) {
			_columnBitmask |= _columnBitmasks.get("type_");

			if (_columnOriginalValues == Collections.EMPTY_MAP) {
				_setColumnOriginalValues();
			}
		}

		_type = type;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public String getOriginalType() {
		return getColumnOriginalValue("type_");
	}

	@JSON
	@Override
	public String getTypeSettings() {
		if (_typeSettings == null) {
			return "";
		}
		else {
			return _typeSettings;
		}
	}

	@Override
	public void setTypeSettings(String typeSettings) {
		if (_columnOriginalValues != null) {
			_columnBitmask |= _columnBitmasks.get("typeSettings");

			if (_columnOriginalValues == Collections.EMPTY_MAP) {
				_setColumnOriginalValues();
			}
		}

		_typeSettings = typeSettings;
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(
			getCompanyId(), DispatchTrigger.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public DispatchTrigger toEscapedModel() {
		if (_escapedModel == null) {
			Function<InvocationHandler, DispatchTrigger>
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
		DispatchTriggerImpl dispatchTriggerImpl = new DispatchTriggerImpl();

		dispatchTriggerImpl.setMvccVersion(getMvccVersion());
		dispatchTriggerImpl.setDispatchTriggerId(getDispatchTriggerId());
		dispatchTriggerImpl.setCompanyId(getCompanyId());
		dispatchTriggerImpl.setUserId(getUserId());
		dispatchTriggerImpl.setUserName(getUserName());
		dispatchTriggerImpl.setCreateDate(getCreateDate());
		dispatchTriggerImpl.setModifiedDate(getModifiedDate());
		dispatchTriggerImpl.setActive(isActive());
		dispatchTriggerImpl.setCronExpression(getCronExpression());
		dispatchTriggerImpl.setEndDate(getEndDate());
		dispatchTriggerImpl.setName(getName());
		dispatchTriggerImpl.setStartDate(getStartDate());
		dispatchTriggerImpl.setSystem(isSystem());
		dispatchTriggerImpl.setType(getType());
		dispatchTriggerImpl.setTypeSettings(getTypeSettings());

		dispatchTriggerImpl.resetOriginalValues();

		return dispatchTriggerImpl;
	}

	@Override
	public int compareTo(DispatchTrigger dispatchTrigger) {
		int value = 0;

		value = DateUtil.compareTo(
			getModifiedDate(), dispatchTrigger.getModifiedDate());

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

		if (!(object instanceof DispatchTrigger)) {
			return false;
		}

		DispatchTrigger dispatchTrigger = (DispatchTrigger)object;

		long primaryKey = dispatchTrigger.getPrimaryKey();

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
	public CacheModel<DispatchTrigger> toCacheModel() {
		DispatchTriggerCacheModel dispatchTriggerCacheModel =
			new DispatchTriggerCacheModel();

		dispatchTriggerCacheModel.mvccVersion = getMvccVersion();

		dispatchTriggerCacheModel.dispatchTriggerId = getDispatchTriggerId();

		dispatchTriggerCacheModel.companyId = getCompanyId();

		dispatchTriggerCacheModel.userId = getUserId();

		dispatchTriggerCacheModel.userName = getUserName();

		String userName = dispatchTriggerCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			dispatchTriggerCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			dispatchTriggerCacheModel.createDate = createDate.getTime();
		}
		else {
			dispatchTriggerCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			dispatchTriggerCacheModel.modifiedDate = modifiedDate.getTime();
		}
		else {
			dispatchTriggerCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		dispatchTriggerCacheModel.active = isActive();

		dispatchTriggerCacheModel.cronExpression = getCronExpression();

		String cronExpression = dispatchTriggerCacheModel.cronExpression;

		if ((cronExpression != null) && (cronExpression.length() == 0)) {
			dispatchTriggerCacheModel.cronExpression = null;
		}

		Date endDate = getEndDate();

		if (endDate != null) {
			dispatchTriggerCacheModel.endDate = endDate.getTime();
		}
		else {
			dispatchTriggerCacheModel.endDate = Long.MIN_VALUE;
		}

		dispatchTriggerCacheModel.name = getName();

		String name = dispatchTriggerCacheModel.name;

		if ((name != null) && (name.length() == 0)) {
			dispatchTriggerCacheModel.name = null;
		}

		Date startDate = getStartDate();

		if (startDate != null) {
			dispatchTriggerCacheModel.startDate = startDate.getTime();
		}
		else {
			dispatchTriggerCacheModel.startDate = Long.MIN_VALUE;
		}

		dispatchTriggerCacheModel.system = isSystem();

		dispatchTriggerCacheModel.type = getType();

		String type = dispatchTriggerCacheModel.type;

		if ((type != null) && (type.length() == 0)) {
			dispatchTriggerCacheModel.type = null;
		}

		dispatchTriggerCacheModel.typeSettings = getTypeSettings();

		String typeSettings = dispatchTriggerCacheModel.typeSettings;

		if ((typeSettings != null) && (typeSettings.length() == 0)) {
			dispatchTriggerCacheModel.typeSettings = null;
		}

		return dispatchTriggerCacheModel;
	}

	@Override
	public String toString() {
		Map<String, Function<DispatchTrigger, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			4 * attributeGetterFunctions.size() + 2);

		sb.append("{");

		for (Map.Entry<String, Function<DispatchTrigger, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<DispatchTrigger, Object> attributeGetterFunction =
				entry.getValue();

			sb.append(attributeName);
			sb.append("=");
			sb.append(attributeGetterFunction.apply((DispatchTrigger)this));
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
		Map<String, Function<DispatchTrigger, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			5 * attributeGetterFunctions.size() + 4);

		sb.append("<model><model-name>");
		sb.append(getModelClassName());
		sb.append("</model-name>");

		for (Map.Entry<String, Function<DispatchTrigger, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<DispatchTrigger, Object> attributeGetterFunction =
				entry.getValue();

			sb.append("<column><column-name>");
			sb.append(attributeName);
			sb.append("</column-name><column-value><![CDATA[");
			sb.append(attributeGetterFunction.apply((DispatchTrigger)this));
			sb.append("]]></column-value></column>");
		}

		sb.append("</model>");

		return sb.toString();
	}

	private static class EscapedModelProxyProviderFunctionHolder {

		private static final Function<InvocationHandler, DispatchTrigger>
			_escapedModelProxyProviderFunction = _getProxyProviderFunction();

	}

	private long _mvccVersion;
	private long _dispatchTriggerId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private boolean _setModifiedDate;
	private boolean _active;
	private String _cronExpression;
	private Date _endDate;
	private String _name;
	private Date _startDate;
	private boolean _system;
	private String _type;
	private String _typeSettings;

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

		_columnOriginalValues.put("mvccVersion", _mvccVersion);
		_columnOriginalValues.put("dispatchTriggerId", _dispatchTriggerId);
		_columnOriginalValues.put("companyId", _companyId);
		_columnOriginalValues.put("userId", _userId);
		_columnOriginalValues.put("userName", _userName);
		_columnOriginalValues.put("createDate", _createDate);
		_columnOriginalValues.put("modifiedDate", _modifiedDate);
		_columnOriginalValues.put("active_", _active);
		_columnOriginalValues.put("cronExpression", _cronExpression);
		_columnOriginalValues.put("endDate", _endDate);
		_columnOriginalValues.put("name", _name);
		_columnOriginalValues.put("startDate", _startDate);
		_columnOriginalValues.put("system_", _system);
		_columnOriginalValues.put("type_", _type);
		_columnOriginalValues.put("typeSettings", _typeSettings);
	}

	private static final Map<String, Long> _columnBitmasks;

	static {
		Map<String, Long> columnBitmasks = new LinkedHashMap<>();

		columnBitmasks.put("mvccVersion", 1L);

		columnBitmasks.put("dispatchTriggerId", 2L);

		columnBitmasks.put("companyId", 4L);

		columnBitmasks.put("userId", 8L);

		columnBitmasks.put("userName", 16L);

		columnBitmasks.put("createDate", 32L);

		columnBitmasks.put("modifiedDate", 64L);

		columnBitmasks.put("active_", 128L);

		columnBitmasks.put("cronExpression", 256L);

		columnBitmasks.put("endDate", 512L);

		columnBitmasks.put("name", 1024L);

		columnBitmasks.put("startDate", 2048L);

		columnBitmasks.put("system_", 4096L);

		columnBitmasks.put("type_", 8192L);

		columnBitmasks.put("typeSettings", 16384L);

		_columnBitmasks = Collections.unmodifiableMap(columnBitmasks);
	}

	private transient Map<String, Object> _columnOriginalValues;
	private long _columnBitmask;
	private DispatchTrigger _escapedModel;

}