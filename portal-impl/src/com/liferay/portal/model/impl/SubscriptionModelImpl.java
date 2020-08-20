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

package com.liferay.portal.model.impl;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.Subscription;
import com.liferay.portal.kernel.model.SubscriptionModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;

import java.sql.Types;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * The base model implementation for the Subscription service. Represents a row in the &quot;Subscription&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface <code>SubscriptionModel</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link SubscriptionImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SubscriptionImpl
 * @deprecated
 * @generated
 */
@Deprecated
public class SubscriptionModelImpl
	extends BaseModelImpl<Subscription> implements SubscriptionModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a subscription model instance should use the <code>Subscription</code> interface instead.
	 */
	public static final String TABLE_NAME = "Subscription";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"subscriptionId", Types.BIGINT},
		{"groupId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP}, {"modifiedDate", Types.TIMESTAMP},
		{"classNameId", Types.BIGINT}, {"classPK", Types.BIGINT},
		{"frequency", Types.VARCHAR}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
		new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("subscriptionId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("classNameId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("classPK", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("frequency", Types.VARCHAR);
	}

	public static final String TABLE_SQL_CREATE =
		"create table Subscription (mvccVersion LONG default 0 not null,subscriptionId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,classNameId LONG,classPK LONG,frequency VARCHAR(75) null)";

	public static final String TABLE_SQL_DROP = "drop table Subscription";

	public static final String ORDER_BY_JPQL =
		" ORDER BY subscription.subscriptionId ASC";

	public static final String ORDER_BY_SQL =
		" ORDER BY Subscription.subscriptionId ASC";

	public static final String DATA_SOURCE = "liferayDataSource";

	public static final String SESSION_FACTORY = "liferaySessionFactory";

	public static final String TX_MANAGER = "liferayTransactionManager";

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public static final boolean ENTITY_CACHE_ENABLED = true;

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public static final boolean FINDER_CACHE_ENABLED = true;

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public static final boolean COLUMN_BITMASK_ENABLED = true;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)
	 */
	@Deprecated
	public static final long CLASSNAMEID_COLUMN_BITMASK = 1L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)
	 */
	@Deprecated
	public static final long CLASSPK_COLUMN_BITMASK = 2L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)
	 */
	@Deprecated
	public static final long COMPANYID_COLUMN_BITMASK = 4L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)
	 */
	@Deprecated
	public static final long GROUPID_COLUMN_BITMASK = 8L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)
	 */
	@Deprecated
	public static final long USERID_COLUMN_BITMASK = 16L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *		#getColumnBitmask(String)
	 */
	@Deprecated
	public static final long SUBSCRIPTIONID_COLUMN_BITMASK = 32L;

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(
		com.liferay.portal.util.PropsUtil.get(
			"lock.expiration.time.com.liferay.portal.kernel.model.Subscription"));

	public SubscriptionModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _subscriptionId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setSubscriptionId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _subscriptionId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return Subscription.class;
	}

	@Override
	public String getModelClassName() {
		return Subscription.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<Subscription, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		for (Map.Entry<String, Function<Subscription, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<Subscription, Object> attributeGetterFunction =
				entry.getValue();

			attributes.put(
				attributeName,
				attributeGetterFunction.apply((Subscription)this));
		}

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<Subscription, Object>>
			attributeSetterBiConsumers = getAttributeSetterBiConsumers();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();

			BiConsumer<Subscription, Object> attributeSetterBiConsumer =
				attributeSetterBiConsumers.get(attributeName);

			if (attributeSetterBiConsumer != null) {
				attributeSetterBiConsumer.accept(
					(Subscription)this, entry.getValue());
			}
		}
	}

	public Map<String, Function<Subscription, Object>>
		getAttributeGetterFunctions() {

		return _attributeGetterFunctions;
	}

	public Map<String, BiConsumer<Subscription, Object>>
		getAttributeSetterBiConsumers() {

		return _attributeSetterBiConsumers;
	}

	private static Function<InvocationHandler, Subscription>
		_getProxyProviderFunction() {

		Class<?> proxyClass = ProxyUtil.getProxyClass(
			Subscription.class.getClassLoader(), Subscription.class,
			ModelWrapper.class);

		try {
			Constructor<Subscription> constructor =
				(Constructor<Subscription>)proxyClass.getConstructor(
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

	private static final Map<String, Function<Subscription, Object>>
		_attributeGetterFunctions;
	private static final Map<String, BiConsumer<Subscription, Object>>
		_attributeSetterBiConsumers;

	static {
		Map<String, Function<Subscription, Object>> attributeGetterFunctions =
			new LinkedHashMap<String, Function<Subscription, Object>>();
		Map<String, BiConsumer<Subscription, ?>> attributeSetterBiConsumers =
			new LinkedHashMap<String, BiConsumer<Subscription, ?>>();

		attributeGetterFunctions.put(
			"mvccVersion", Subscription::getMvccVersion);
		attributeSetterBiConsumers.put(
			"mvccVersion",
			(BiConsumer<Subscription, Long>)Subscription::setMvccVersion);
		attributeGetterFunctions.put(
			"subscriptionId", Subscription::getSubscriptionId);
		attributeSetterBiConsumers.put(
			"subscriptionId",
			(BiConsumer<Subscription, Long>)Subscription::setSubscriptionId);
		attributeGetterFunctions.put("groupId", Subscription::getGroupId);
		attributeSetterBiConsumers.put(
			"groupId",
			(BiConsumer<Subscription, Long>)Subscription::setGroupId);
		attributeGetterFunctions.put("companyId", Subscription::getCompanyId);
		attributeSetterBiConsumers.put(
			"companyId",
			(BiConsumer<Subscription, Long>)Subscription::setCompanyId);
		attributeGetterFunctions.put("userId", Subscription::getUserId);
		attributeSetterBiConsumers.put(
			"userId", (BiConsumer<Subscription, Long>)Subscription::setUserId);
		attributeGetterFunctions.put("userName", Subscription::getUserName);
		attributeSetterBiConsumers.put(
			"userName",
			(BiConsumer<Subscription, String>)Subscription::setUserName);
		attributeGetterFunctions.put("createDate", Subscription::getCreateDate);
		attributeSetterBiConsumers.put(
			"createDate",
			(BiConsumer<Subscription, Date>)Subscription::setCreateDate);
		attributeGetterFunctions.put(
			"modifiedDate", Subscription::getModifiedDate);
		attributeSetterBiConsumers.put(
			"modifiedDate",
			(BiConsumer<Subscription, Date>)Subscription::setModifiedDate);
		attributeGetterFunctions.put(
			"classNameId", Subscription::getClassNameId);
		attributeSetterBiConsumers.put(
			"classNameId",
			(BiConsumer<Subscription, Long>)Subscription::setClassNameId);
		attributeGetterFunctions.put("classPK", Subscription::getClassPK);
		attributeSetterBiConsumers.put(
			"classPK",
			(BiConsumer<Subscription, Long>)Subscription::setClassPK);
		attributeGetterFunctions.put("frequency", Subscription::getFrequency);
		attributeSetterBiConsumers.put(
			"frequency",
			(BiConsumer<Subscription, String>)Subscription::setFrequency);

		_attributeGetterFunctions = Collections.unmodifiableMap(
			attributeGetterFunctions);
		_attributeSetterBiConsumers = Collections.unmodifiableMap(
			(Map)attributeSetterBiConsumers);
	}

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

	@Override
	public long getSubscriptionId() {
		return _subscriptionId;
	}

	@Override
	public void setSubscriptionId(long subscriptionId) {
		if (_columnOriginalValues != null) {
			_columnBitmask |= _columnBitmasks.get("subscriptionId");

			if (_columnOriginalValues == Collections.EMPTY_MAP) {
				_setColumnOriginalValues();
			}
		}

		_subscriptionId = subscriptionId;
	}

	@Override
	public long getGroupId() {
		return _groupId;
	}

	@Override
	public void setGroupId(long groupId) {
		if (_columnOriginalValues != null) {
			_columnBitmask |= _columnBitmasks.get("groupId");

			if (_columnOriginalValues == Collections.EMPTY_MAP) {
				_setColumnOriginalValues();
			}
		}

		_groupId = groupId;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public long getOriginalGroupId() {
		return GetterUtil.getLong(getColumnOriginalValue("groupId"));
	}

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

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public long getOriginalUserId() {
		return GetterUtil.getLong(getColumnOriginalValue("userId"));
	}

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

	@Override
	public String getClassName() {
		if (getClassNameId() <= 0) {
			return "";
		}

		return PortalUtil.getClassName(getClassNameId());
	}

	@Override
	public void setClassName(String className) {
		long classNameId = 0;

		if (Validator.isNotNull(className)) {
			classNameId = PortalUtil.getClassNameId(className);
		}

		setClassNameId(classNameId);
	}

	@Override
	public long getClassNameId() {
		return _classNameId;
	}

	@Override
	public void setClassNameId(long classNameId) {
		if (_columnOriginalValues != null) {
			_columnBitmask |= _columnBitmasks.get("classNameId");

			if (_columnOriginalValues == Collections.EMPTY_MAP) {
				_setColumnOriginalValues();
			}
		}

		_classNameId = classNameId;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public long getOriginalClassNameId() {
		return GetterUtil.getLong(getColumnOriginalValue("classNameId"));
	}

	@Override
	public long getClassPK() {
		return _classPK;
	}

	@Override
	public void setClassPK(long classPK) {
		if (_columnOriginalValues != null) {
			_columnBitmask |= _columnBitmasks.get("classPK");

			if (_columnOriginalValues == Collections.EMPTY_MAP) {
				_setColumnOriginalValues();
			}
		}

		_classPK = classPK;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public long getOriginalClassPK() {
		return GetterUtil.getLong(getColumnOriginalValue("classPK"));
	}

	@Override
	public String getFrequency() {
		if (_frequency == null) {
			return "";
		}
		else {
			return _frequency;
		}
	}

	@Override
	public void setFrequency(String frequency) {
		if (_columnOriginalValues != null) {
			_columnBitmask |= _columnBitmasks.get("frequency");

			if (_columnOriginalValues == Collections.EMPTY_MAP) {
				_setColumnOriginalValues();
			}
		}

		_frequency = frequency;
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(
			getCompanyId(), Subscription.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public Subscription toEscapedModel() {
		if (_escapedModel == null) {
			Function<InvocationHandler, Subscription>
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
		SubscriptionImpl subscriptionImpl = new SubscriptionImpl();

		subscriptionImpl.setMvccVersion(getMvccVersion());
		subscriptionImpl.setSubscriptionId(getSubscriptionId());
		subscriptionImpl.setGroupId(getGroupId());
		subscriptionImpl.setCompanyId(getCompanyId());
		subscriptionImpl.setUserId(getUserId());
		subscriptionImpl.setUserName(getUserName());
		subscriptionImpl.setCreateDate(getCreateDate());
		subscriptionImpl.setModifiedDate(getModifiedDate());
		subscriptionImpl.setClassNameId(getClassNameId());
		subscriptionImpl.setClassPK(getClassPK());
		subscriptionImpl.setFrequency(getFrequency());

		subscriptionImpl.resetOriginalValues();

		return subscriptionImpl;
	}

	@Override
	public int compareTo(Subscription subscription) {
		long primaryKey = subscription.getPrimaryKey();

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

		if (!(object instanceof Subscription)) {
			return false;
		}

		Subscription subscription = (Subscription)object;

		long primaryKey = subscription.getPrimaryKey();

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
		return ENTITY_CACHE_ENABLED;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public boolean isFinderCacheEnabled() {
		return FINDER_CACHE_ENABLED;
	}

	@Override
	public void resetOriginalValues() {
		_columnOriginalValues = Collections.emptyMap();

		_setModifiedDate = false;

		_columnBitmask = 0;
	}

	@Override
	public CacheModel<Subscription> toCacheModel() {
		SubscriptionCacheModel subscriptionCacheModel =
			new SubscriptionCacheModel();

		subscriptionCacheModel.mvccVersion = getMvccVersion();

		subscriptionCacheModel.subscriptionId = getSubscriptionId();

		subscriptionCacheModel.groupId = getGroupId();

		subscriptionCacheModel.companyId = getCompanyId();

		subscriptionCacheModel.userId = getUserId();

		subscriptionCacheModel.userName = getUserName();

		String userName = subscriptionCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			subscriptionCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			subscriptionCacheModel.createDate = createDate.getTime();
		}
		else {
			subscriptionCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			subscriptionCacheModel.modifiedDate = modifiedDate.getTime();
		}
		else {
			subscriptionCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		subscriptionCacheModel.classNameId = getClassNameId();

		subscriptionCacheModel.classPK = getClassPK();

		subscriptionCacheModel.frequency = getFrequency();

		String frequency = subscriptionCacheModel.frequency;

		if ((frequency != null) && (frequency.length() == 0)) {
			subscriptionCacheModel.frequency = null;
		}

		return subscriptionCacheModel;
	}

	@Override
	public String toString() {
		Map<String, Function<Subscription, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			4 * attributeGetterFunctions.size() + 2);

		sb.append("{");

		for (Map.Entry<String, Function<Subscription, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<Subscription, Object> attributeGetterFunction =
				entry.getValue();

			sb.append(attributeName);
			sb.append("=");
			sb.append(attributeGetterFunction.apply((Subscription)this));
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
		Map<String, Function<Subscription, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			5 * attributeGetterFunctions.size() + 4);

		sb.append("<model><model-name>");
		sb.append(getModelClassName());
		sb.append("</model-name>");

		for (Map.Entry<String, Function<Subscription, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<Subscription, Object> attributeGetterFunction =
				entry.getValue();

			sb.append("<column><column-name>");
			sb.append(attributeName);
			sb.append("</column-name><column-value><![CDATA[");
			sb.append(attributeGetterFunction.apply((Subscription)this));
			sb.append("]]></column-value></column>");
		}

		sb.append("</model>");

		return sb.toString();
	}

	private static class EscapedModelProxyProviderFunctionHolder {

		private static final Function<InvocationHandler, Subscription>
			_escapedModelProxyProviderFunction = _getProxyProviderFunction();

	}

	private long _mvccVersion;
	private long _subscriptionId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private boolean _setModifiedDate;
	private long _classNameId;
	private long _classPK;
	private String _frequency;

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
		_columnOriginalValues.put("subscriptionId", _subscriptionId);
		_columnOriginalValues.put("groupId", _groupId);
		_columnOriginalValues.put("companyId", _companyId);
		_columnOriginalValues.put("userId", _userId);
		_columnOriginalValues.put("userName", _userName);
		_columnOriginalValues.put("createDate", _createDate);
		_columnOriginalValues.put("modifiedDate", _modifiedDate);
		_columnOriginalValues.put("classNameId", _classNameId);
		_columnOriginalValues.put("classPK", _classPK);
		_columnOriginalValues.put("frequency", _frequency);
	}

	private static final Map<String, Long> _columnBitmasks;

	static {
		Map<String, Long> columnBitmasks = new LinkedHashMap<>();

		columnBitmasks.put("mvccVersion", 1L);

		columnBitmasks.put("subscriptionId", 2L);

		columnBitmasks.put("groupId", 4L);

		columnBitmasks.put("companyId", 8L);

		columnBitmasks.put("userId", 16L);

		columnBitmasks.put("userName", 32L);

		columnBitmasks.put("createDate", 64L);

		columnBitmasks.put("modifiedDate", 128L);

		columnBitmasks.put("classNameId", 256L);

		columnBitmasks.put("classPK", 512L);

		columnBitmasks.put("frequency", 1024L);

		_columnBitmasks = Collections.unmodifiableMap(columnBitmasks);
	}

	private transient Map<String, Object> _columnOriginalValues;
	private long _columnBitmask;
	private Subscription _escapedModel;

}