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

package com.liferay.portal.workflow.kaleo.model.impl;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.workflow.kaleo.model.KaleoTimerInstanceToken;
import com.liferay.portal.workflow.kaleo.model.KaleoTimerInstanceTokenModel;

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
 * The base model implementation for the KaleoTimerInstanceToken service. Represents a row in the &quot;KaleoTimerInstanceToken&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface <code>KaleoTimerInstanceTokenModel</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link KaleoTimerInstanceTokenImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see KaleoTimerInstanceTokenImpl
 * @generated
 */
public class KaleoTimerInstanceTokenModelImpl
	extends BaseModelImpl<KaleoTimerInstanceToken>
	implements KaleoTimerInstanceTokenModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a kaleo timer instance token model instance should use the <code>KaleoTimerInstanceToken</code> interface instead.
	 */
	public static final String TABLE_NAME = "KaleoTimerInstanceToken";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT},
		{"kaleoTimerInstanceTokenId", Types.BIGINT}, {"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT}, {"userId", Types.BIGINT},
		{"userName", Types.VARCHAR}, {"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP}, {"kaleoClassName", Types.VARCHAR},
		{"kaleoClassPK", Types.BIGINT}, {"kaleoDefinitionId", Types.BIGINT},
		{"kaleoDefinitionVersionId", Types.BIGINT},
		{"kaleoInstanceId", Types.BIGINT},
		{"kaleoInstanceTokenId", Types.BIGINT},
		{"kaleoTaskInstanceTokenId", Types.BIGINT},
		{"kaleoTimerId", Types.BIGINT}, {"kaleoTimerName", Types.VARCHAR},
		{"blocking", Types.BOOLEAN}, {"completionUserId", Types.BIGINT},
		{"completed", Types.BOOLEAN}, {"completionDate", Types.TIMESTAMP},
		{"workflowContext", Types.CLOB}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
		new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("kaleoTimerInstanceTokenId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("kaleoClassName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("kaleoClassPK", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("kaleoDefinitionId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("kaleoDefinitionVersionId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("kaleoInstanceId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("kaleoInstanceTokenId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("kaleoTaskInstanceTokenId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("kaleoTimerId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("kaleoTimerName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("blocking", Types.BOOLEAN);
		TABLE_COLUMNS_MAP.put("completionUserId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("completed", Types.BOOLEAN);
		TABLE_COLUMNS_MAP.put("completionDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("workflowContext", Types.CLOB);
	}

	public static final String TABLE_SQL_CREATE =
		"create table KaleoTimerInstanceToken (mvccVersion LONG default 0 not null,kaleoTimerInstanceTokenId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(200) null,createDate DATE null,modifiedDate DATE null,kaleoClassName VARCHAR(200) null,kaleoClassPK LONG,kaleoDefinitionId LONG,kaleoDefinitionVersionId LONG,kaleoInstanceId LONG,kaleoInstanceTokenId LONG,kaleoTaskInstanceTokenId LONG,kaleoTimerId LONG,kaleoTimerName VARCHAR(200) null,blocking BOOLEAN,completionUserId LONG,completed BOOLEAN,completionDate DATE null,workflowContext TEXT null)";

	public static final String TABLE_SQL_DROP =
		"drop table KaleoTimerInstanceToken";

	public static final String ORDER_BY_JPQL =
		" ORDER BY kaleoTimerInstanceToken.kaleoTimerInstanceTokenId ASC";

	public static final String ORDER_BY_SQL =
		" ORDER BY KaleoTimerInstanceToken.kaleoTimerInstanceTokenId ASC";

	public static final String DATA_SOURCE = "liferayDataSource";

	public static final String SESSION_FACTORY = "liferaySessionFactory";

	public static final String TX_MANAGER = "liferayTransactionManager";

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)
	 */
	@Deprecated
	public static final long BLOCKING_COLUMN_BITMASK = 1L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)
	 */
	@Deprecated
	public static final long COMPLETED_COLUMN_BITMASK = 2L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)
	 */
	@Deprecated
	public static final long KALEOINSTANCEID_COLUMN_BITMASK = 4L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)
	 */
	@Deprecated
	public static final long KALEOINSTANCETOKENID_COLUMN_BITMASK = 8L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)
	 */
	@Deprecated
	public static final long KALEOTIMERID_COLUMN_BITMASK = 16L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *		#getColumnBitmask(String)
	 */
	@Deprecated
	public static final long KALEOTIMERINSTANCETOKENID_COLUMN_BITMASK = 32L;

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

	public KaleoTimerInstanceTokenModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _kaleoTimerInstanceTokenId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setKaleoTimerInstanceTokenId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _kaleoTimerInstanceTokenId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return KaleoTimerInstanceToken.class;
	}

	@Override
	public String getModelClassName() {
		return KaleoTimerInstanceToken.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<KaleoTimerInstanceToken, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		for (Map.Entry<String, Function<KaleoTimerInstanceToken, Object>>
				entry : attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<KaleoTimerInstanceToken, Object> attributeGetterFunction =
				entry.getValue();

			attributes.put(
				attributeName,
				attributeGetterFunction.apply((KaleoTimerInstanceToken)this));
		}

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<KaleoTimerInstanceToken, Object>>
			attributeSetterBiConsumers = getAttributeSetterBiConsumers();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();

			BiConsumer<KaleoTimerInstanceToken, Object>
				attributeSetterBiConsumer = attributeSetterBiConsumers.get(
					attributeName);

			if (attributeSetterBiConsumer != null) {
				attributeSetterBiConsumer.accept(
					(KaleoTimerInstanceToken)this, entry.getValue());
			}
		}
	}

	public Map<String, Function<KaleoTimerInstanceToken, Object>>
		getAttributeGetterFunctions() {

		return _attributeGetterFunctions;
	}

	public Map<String, BiConsumer<KaleoTimerInstanceToken, Object>>
		getAttributeSetterBiConsumers() {

		return _attributeSetterBiConsumers;
	}

	private static Function<InvocationHandler, KaleoTimerInstanceToken>
		_getProxyProviderFunction() {

		Class<?> proxyClass = ProxyUtil.getProxyClass(
			KaleoTimerInstanceToken.class.getClassLoader(),
			KaleoTimerInstanceToken.class, ModelWrapper.class);

		try {
			Constructor<KaleoTimerInstanceToken> constructor =
				(Constructor<KaleoTimerInstanceToken>)proxyClass.getConstructor(
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

	private static final Map<String, Function<KaleoTimerInstanceToken, Object>>
		_attributeGetterFunctions;
	private static final Map
		<String, BiConsumer<KaleoTimerInstanceToken, Object>>
			_attributeSetterBiConsumers;

	static {
		Map<String, Function<KaleoTimerInstanceToken, Object>>
			attributeGetterFunctions =
				new LinkedHashMap
					<String, Function<KaleoTimerInstanceToken, Object>>();
		Map<String, BiConsumer<KaleoTimerInstanceToken, ?>>
			attributeSetterBiConsumers =
				new LinkedHashMap
					<String, BiConsumer<KaleoTimerInstanceToken, ?>>();

		attributeGetterFunctions.put(
			"mvccVersion", KaleoTimerInstanceToken::getMvccVersion);
		attributeSetterBiConsumers.put(
			"mvccVersion",
			(BiConsumer<KaleoTimerInstanceToken, Long>)
				KaleoTimerInstanceToken::setMvccVersion);
		attributeGetterFunctions.put(
			"kaleoTimerInstanceTokenId",
			KaleoTimerInstanceToken::getKaleoTimerInstanceTokenId);
		attributeSetterBiConsumers.put(
			"kaleoTimerInstanceTokenId",
			(BiConsumer<KaleoTimerInstanceToken, Long>)
				KaleoTimerInstanceToken::setKaleoTimerInstanceTokenId);
		attributeGetterFunctions.put(
			"groupId", KaleoTimerInstanceToken::getGroupId);
		attributeSetterBiConsumers.put(
			"groupId",
			(BiConsumer<KaleoTimerInstanceToken, Long>)
				KaleoTimerInstanceToken::setGroupId);
		attributeGetterFunctions.put(
			"companyId", KaleoTimerInstanceToken::getCompanyId);
		attributeSetterBiConsumers.put(
			"companyId",
			(BiConsumer<KaleoTimerInstanceToken, Long>)
				KaleoTimerInstanceToken::setCompanyId);
		attributeGetterFunctions.put(
			"userId", KaleoTimerInstanceToken::getUserId);
		attributeSetterBiConsumers.put(
			"userId",
			(BiConsumer<KaleoTimerInstanceToken, Long>)
				KaleoTimerInstanceToken::setUserId);
		attributeGetterFunctions.put(
			"userName", KaleoTimerInstanceToken::getUserName);
		attributeSetterBiConsumers.put(
			"userName",
			(BiConsumer<KaleoTimerInstanceToken, String>)
				KaleoTimerInstanceToken::setUserName);
		attributeGetterFunctions.put(
			"createDate", KaleoTimerInstanceToken::getCreateDate);
		attributeSetterBiConsumers.put(
			"createDate",
			(BiConsumer<KaleoTimerInstanceToken, Date>)
				KaleoTimerInstanceToken::setCreateDate);
		attributeGetterFunctions.put(
			"modifiedDate", KaleoTimerInstanceToken::getModifiedDate);
		attributeSetterBiConsumers.put(
			"modifiedDate",
			(BiConsumer<KaleoTimerInstanceToken, Date>)
				KaleoTimerInstanceToken::setModifiedDate);
		attributeGetterFunctions.put(
			"kaleoClassName", KaleoTimerInstanceToken::getKaleoClassName);
		attributeSetterBiConsumers.put(
			"kaleoClassName",
			(BiConsumer<KaleoTimerInstanceToken, String>)
				KaleoTimerInstanceToken::setKaleoClassName);
		attributeGetterFunctions.put(
			"kaleoClassPK", KaleoTimerInstanceToken::getKaleoClassPK);
		attributeSetterBiConsumers.put(
			"kaleoClassPK",
			(BiConsumer<KaleoTimerInstanceToken, Long>)
				KaleoTimerInstanceToken::setKaleoClassPK);
		attributeGetterFunctions.put(
			"kaleoDefinitionId", KaleoTimerInstanceToken::getKaleoDefinitionId);
		attributeSetterBiConsumers.put(
			"kaleoDefinitionId",
			(BiConsumer<KaleoTimerInstanceToken, Long>)
				KaleoTimerInstanceToken::setKaleoDefinitionId);
		attributeGetterFunctions.put(
			"kaleoDefinitionVersionId",
			KaleoTimerInstanceToken::getKaleoDefinitionVersionId);
		attributeSetterBiConsumers.put(
			"kaleoDefinitionVersionId",
			(BiConsumer<KaleoTimerInstanceToken, Long>)
				KaleoTimerInstanceToken::setKaleoDefinitionVersionId);
		attributeGetterFunctions.put(
			"kaleoInstanceId", KaleoTimerInstanceToken::getKaleoInstanceId);
		attributeSetterBiConsumers.put(
			"kaleoInstanceId",
			(BiConsumer<KaleoTimerInstanceToken, Long>)
				KaleoTimerInstanceToken::setKaleoInstanceId);
		attributeGetterFunctions.put(
			"kaleoInstanceTokenId",
			KaleoTimerInstanceToken::getKaleoInstanceTokenId);
		attributeSetterBiConsumers.put(
			"kaleoInstanceTokenId",
			(BiConsumer<KaleoTimerInstanceToken, Long>)
				KaleoTimerInstanceToken::setKaleoInstanceTokenId);
		attributeGetterFunctions.put(
			"kaleoTaskInstanceTokenId",
			KaleoTimerInstanceToken::getKaleoTaskInstanceTokenId);
		attributeSetterBiConsumers.put(
			"kaleoTaskInstanceTokenId",
			(BiConsumer<KaleoTimerInstanceToken, Long>)
				KaleoTimerInstanceToken::setKaleoTaskInstanceTokenId);
		attributeGetterFunctions.put(
			"kaleoTimerId", KaleoTimerInstanceToken::getKaleoTimerId);
		attributeSetterBiConsumers.put(
			"kaleoTimerId",
			(BiConsumer<KaleoTimerInstanceToken, Long>)
				KaleoTimerInstanceToken::setKaleoTimerId);
		attributeGetterFunctions.put(
			"kaleoTimerName", KaleoTimerInstanceToken::getKaleoTimerName);
		attributeSetterBiConsumers.put(
			"kaleoTimerName",
			(BiConsumer<KaleoTimerInstanceToken, String>)
				KaleoTimerInstanceToken::setKaleoTimerName);
		attributeGetterFunctions.put(
			"blocking", KaleoTimerInstanceToken::getBlocking);
		attributeSetterBiConsumers.put(
			"blocking",
			(BiConsumer<KaleoTimerInstanceToken, Boolean>)
				KaleoTimerInstanceToken::setBlocking);
		attributeGetterFunctions.put(
			"completionUserId", KaleoTimerInstanceToken::getCompletionUserId);
		attributeSetterBiConsumers.put(
			"completionUserId",
			(BiConsumer<KaleoTimerInstanceToken, Long>)
				KaleoTimerInstanceToken::setCompletionUserId);
		attributeGetterFunctions.put(
			"completed", KaleoTimerInstanceToken::getCompleted);
		attributeSetterBiConsumers.put(
			"completed",
			(BiConsumer<KaleoTimerInstanceToken, Boolean>)
				KaleoTimerInstanceToken::setCompleted);
		attributeGetterFunctions.put(
			"completionDate", KaleoTimerInstanceToken::getCompletionDate);
		attributeSetterBiConsumers.put(
			"completionDate",
			(BiConsumer<KaleoTimerInstanceToken, Date>)
				KaleoTimerInstanceToken::setCompletionDate);
		attributeGetterFunctions.put(
			"workflowContext", KaleoTimerInstanceToken::getWorkflowContext);
		attributeSetterBiConsumers.put(
			"workflowContext",
			(BiConsumer<KaleoTimerInstanceToken, String>)
				KaleoTimerInstanceToken::setWorkflowContext);

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
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_mvccVersion = mvccVersion;
	}

	@Override
	public long getKaleoTimerInstanceTokenId() {
		return _kaleoTimerInstanceTokenId;
	}

	@Override
	public void setKaleoTimerInstanceTokenId(long kaleoTimerInstanceTokenId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_kaleoTimerInstanceTokenId = kaleoTimerInstanceTokenId;
	}

	@Override
	public long getGroupId() {
		return _groupId;
	}

	@Override
	public void setGroupId(long groupId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_groupId = groupId;
	}

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

	@Override
	public String getKaleoClassName() {
		if (_kaleoClassName == null) {
			return "";
		}
		else {
			return _kaleoClassName;
		}
	}

	@Override
	public void setKaleoClassName(String kaleoClassName) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_kaleoClassName = kaleoClassName;
	}

	@Override
	public long getKaleoClassPK() {
		return _kaleoClassPK;
	}

	@Override
	public void setKaleoClassPK(long kaleoClassPK) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_kaleoClassPK = kaleoClassPK;
	}

	@Override
	public long getKaleoDefinitionId() {
		return _kaleoDefinitionId;
	}

	@Override
	public void setKaleoDefinitionId(long kaleoDefinitionId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_kaleoDefinitionId = kaleoDefinitionId;
	}

	@Override
	public long getKaleoDefinitionVersionId() {
		return _kaleoDefinitionVersionId;
	}

	@Override
	public void setKaleoDefinitionVersionId(long kaleoDefinitionVersionId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_kaleoDefinitionVersionId = kaleoDefinitionVersionId;
	}

	@Override
	public long getKaleoInstanceId() {
		return _kaleoInstanceId;
	}

	@Override
	public void setKaleoInstanceId(long kaleoInstanceId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_kaleoInstanceId = kaleoInstanceId;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public long getOriginalKaleoInstanceId() {
		return GetterUtil.getLong(
			this.<Long>getColumnOriginalValue("kaleoInstanceId"));
	}

	@Override
	public long getKaleoInstanceTokenId() {
		return _kaleoInstanceTokenId;
	}

	@Override
	public void setKaleoInstanceTokenId(long kaleoInstanceTokenId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_kaleoInstanceTokenId = kaleoInstanceTokenId;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public long getOriginalKaleoInstanceTokenId() {
		return GetterUtil.getLong(
			this.<Long>getColumnOriginalValue("kaleoInstanceTokenId"));
	}

	@Override
	public long getKaleoTaskInstanceTokenId() {
		return _kaleoTaskInstanceTokenId;
	}

	@Override
	public void setKaleoTaskInstanceTokenId(long kaleoTaskInstanceTokenId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_kaleoTaskInstanceTokenId = kaleoTaskInstanceTokenId;
	}

	@Override
	public long getKaleoTimerId() {
		return _kaleoTimerId;
	}

	@Override
	public void setKaleoTimerId(long kaleoTimerId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_kaleoTimerId = kaleoTimerId;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public long getOriginalKaleoTimerId() {
		return GetterUtil.getLong(
			this.<Long>getColumnOriginalValue("kaleoTimerId"));
	}

	@Override
	public String getKaleoTimerName() {
		if (_kaleoTimerName == null) {
			return "";
		}
		else {
			return _kaleoTimerName;
		}
	}

	@Override
	public void setKaleoTimerName(String kaleoTimerName) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_kaleoTimerName = kaleoTimerName;
	}

	@Override
	public boolean getBlocking() {
		return _blocking;
	}

	@Override
	public boolean isBlocking() {
		return _blocking;
	}

	@Override
	public void setBlocking(boolean blocking) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_blocking = blocking;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public boolean getOriginalBlocking() {
		return GetterUtil.getBoolean(
			this.<Boolean>getColumnOriginalValue("blocking"));
	}

	@Override
	public long getCompletionUserId() {
		return _completionUserId;
	}

	@Override
	public void setCompletionUserId(long completionUserId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_completionUserId = completionUserId;
	}

	@Override
	public String getCompletionUserUuid() {
		try {
			User user = UserLocalServiceUtil.getUserById(getCompletionUserId());

			return user.getUuid();
		}
		catch (PortalException portalException) {
			return "";
		}
	}

	@Override
	public void setCompletionUserUuid(String completionUserUuid) {
	}

	@Override
	public boolean getCompleted() {
		return _completed;
	}

	@Override
	public boolean isCompleted() {
		return _completed;
	}

	@Override
	public void setCompleted(boolean completed) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_completed = completed;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public boolean getOriginalCompleted() {
		return GetterUtil.getBoolean(
			this.<Boolean>getColumnOriginalValue("completed"));
	}

	@Override
	public Date getCompletionDate() {
		return _completionDate;
	}

	@Override
	public void setCompletionDate(Date completionDate) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_completionDate = completionDate;
	}

	@Override
	public String getWorkflowContext() {
		if (_workflowContext == null) {
			return "";
		}
		else {
			return _workflowContext;
		}
	}

	@Override
	public void setWorkflowContext(String workflowContext) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_workflowContext = workflowContext;
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
			getCompanyId(), KaleoTimerInstanceToken.class.getName(),
			getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public KaleoTimerInstanceToken toEscapedModel() {
		if (_escapedModel == null) {
			Function<InvocationHandler, KaleoTimerInstanceToken>
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
		KaleoTimerInstanceTokenImpl kaleoTimerInstanceTokenImpl =
			new KaleoTimerInstanceTokenImpl();

		kaleoTimerInstanceTokenImpl.setMvccVersion(getMvccVersion());
		kaleoTimerInstanceTokenImpl.setKaleoTimerInstanceTokenId(
			getKaleoTimerInstanceTokenId());
		kaleoTimerInstanceTokenImpl.setGroupId(getGroupId());
		kaleoTimerInstanceTokenImpl.setCompanyId(getCompanyId());
		kaleoTimerInstanceTokenImpl.setUserId(getUserId());
		kaleoTimerInstanceTokenImpl.setUserName(getUserName());
		kaleoTimerInstanceTokenImpl.setCreateDate(getCreateDate());
		kaleoTimerInstanceTokenImpl.setModifiedDate(getModifiedDate());
		kaleoTimerInstanceTokenImpl.setKaleoClassName(getKaleoClassName());
		kaleoTimerInstanceTokenImpl.setKaleoClassPK(getKaleoClassPK());
		kaleoTimerInstanceTokenImpl.setKaleoDefinitionId(
			getKaleoDefinitionId());
		kaleoTimerInstanceTokenImpl.setKaleoDefinitionVersionId(
			getKaleoDefinitionVersionId());
		kaleoTimerInstanceTokenImpl.setKaleoInstanceId(getKaleoInstanceId());
		kaleoTimerInstanceTokenImpl.setKaleoInstanceTokenId(
			getKaleoInstanceTokenId());
		kaleoTimerInstanceTokenImpl.setKaleoTaskInstanceTokenId(
			getKaleoTaskInstanceTokenId());
		kaleoTimerInstanceTokenImpl.setKaleoTimerId(getKaleoTimerId());
		kaleoTimerInstanceTokenImpl.setKaleoTimerName(getKaleoTimerName());
		kaleoTimerInstanceTokenImpl.setBlocking(isBlocking());
		kaleoTimerInstanceTokenImpl.setCompletionUserId(getCompletionUserId());
		kaleoTimerInstanceTokenImpl.setCompleted(isCompleted());
		kaleoTimerInstanceTokenImpl.setCompletionDate(getCompletionDate());
		kaleoTimerInstanceTokenImpl.setWorkflowContext(getWorkflowContext());

		kaleoTimerInstanceTokenImpl.resetOriginalValues();

		return kaleoTimerInstanceTokenImpl;
	}

	@Override
	public int compareTo(KaleoTimerInstanceToken kaleoTimerInstanceToken) {
		int value = 0;

		if (getKaleoTimerInstanceTokenId() <
				kaleoTimerInstanceToken.getKaleoTimerInstanceTokenId()) {

			value = -1;
		}
		else if (getKaleoTimerInstanceTokenId() >
					kaleoTimerInstanceToken.getKaleoTimerInstanceTokenId()) {

			value = 1;
		}
		else {
			value = 0;
		}

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

		if (!(object instanceof KaleoTimerInstanceToken)) {
			return false;
		}

		KaleoTimerInstanceToken kaleoTimerInstanceToken =
			(KaleoTimerInstanceToken)object;

		long primaryKey = kaleoTimerInstanceToken.getPrimaryKey();

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
	public CacheModel<KaleoTimerInstanceToken> toCacheModel() {
		KaleoTimerInstanceTokenCacheModel kaleoTimerInstanceTokenCacheModel =
			new KaleoTimerInstanceTokenCacheModel();

		kaleoTimerInstanceTokenCacheModel.mvccVersion = getMvccVersion();

		kaleoTimerInstanceTokenCacheModel.kaleoTimerInstanceTokenId =
			getKaleoTimerInstanceTokenId();

		kaleoTimerInstanceTokenCacheModel.groupId = getGroupId();

		kaleoTimerInstanceTokenCacheModel.companyId = getCompanyId();

		kaleoTimerInstanceTokenCacheModel.userId = getUserId();

		kaleoTimerInstanceTokenCacheModel.userName = getUserName();

		String userName = kaleoTimerInstanceTokenCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			kaleoTimerInstanceTokenCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			kaleoTimerInstanceTokenCacheModel.createDate = createDate.getTime();
		}
		else {
			kaleoTimerInstanceTokenCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			kaleoTimerInstanceTokenCacheModel.modifiedDate =
				modifiedDate.getTime();
		}
		else {
			kaleoTimerInstanceTokenCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		kaleoTimerInstanceTokenCacheModel.kaleoClassName = getKaleoClassName();

		String kaleoClassName =
			kaleoTimerInstanceTokenCacheModel.kaleoClassName;

		if ((kaleoClassName != null) && (kaleoClassName.length() == 0)) {
			kaleoTimerInstanceTokenCacheModel.kaleoClassName = null;
		}

		kaleoTimerInstanceTokenCacheModel.kaleoClassPK = getKaleoClassPK();

		kaleoTimerInstanceTokenCacheModel.kaleoDefinitionId =
			getKaleoDefinitionId();

		kaleoTimerInstanceTokenCacheModel.kaleoDefinitionVersionId =
			getKaleoDefinitionVersionId();

		kaleoTimerInstanceTokenCacheModel.kaleoInstanceId =
			getKaleoInstanceId();

		kaleoTimerInstanceTokenCacheModel.kaleoInstanceTokenId =
			getKaleoInstanceTokenId();

		kaleoTimerInstanceTokenCacheModel.kaleoTaskInstanceTokenId =
			getKaleoTaskInstanceTokenId();

		kaleoTimerInstanceTokenCacheModel.kaleoTimerId = getKaleoTimerId();

		kaleoTimerInstanceTokenCacheModel.kaleoTimerName = getKaleoTimerName();

		String kaleoTimerName =
			kaleoTimerInstanceTokenCacheModel.kaleoTimerName;

		if ((kaleoTimerName != null) && (kaleoTimerName.length() == 0)) {
			kaleoTimerInstanceTokenCacheModel.kaleoTimerName = null;
		}

		kaleoTimerInstanceTokenCacheModel.blocking = isBlocking();

		kaleoTimerInstanceTokenCacheModel.completionUserId =
			getCompletionUserId();

		kaleoTimerInstanceTokenCacheModel.completed = isCompleted();

		Date completionDate = getCompletionDate();

		if (completionDate != null) {
			kaleoTimerInstanceTokenCacheModel.completionDate =
				completionDate.getTime();
		}
		else {
			kaleoTimerInstanceTokenCacheModel.completionDate = Long.MIN_VALUE;
		}

		kaleoTimerInstanceTokenCacheModel.workflowContext =
			getWorkflowContext();

		String workflowContext =
			kaleoTimerInstanceTokenCacheModel.workflowContext;

		if ((workflowContext != null) && (workflowContext.length() == 0)) {
			kaleoTimerInstanceTokenCacheModel.workflowContext = null;
		}

		return kaleoTimerInstanceTokenCacheModel;
	}

	@Override
	public String toString() {
		Map<String, Function<KaleoTimerInstanceToken, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			4 * attributeGetterFunctions.size() + 2);

		sb.append("{");

		for (Map.Entry<String, Function<KaleoTimerInstanceToken, Object>>
				entry : attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<KaleoTimerInstanceToken, Object> attributeGetterFunction =
				entry.getValue();

			sb.append(attributeName);
			sb.append("=");
			sb.append(
				attributeGetterFunction.apply((KaleoTimerInstanceToken)this));
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
		Map<String, Function<KaleoTimerInstanceToken, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			5 * attributeGetterFunctions.size() + 4);

		sb.append("<model><model-name>");
		sb.append(getModelClassName());
		sb.append("</model-name>");

		for (Map.Entry<String, Function<KaleoTimerInstanceToken, Object>>
				entry : attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<KaleoTimerInstanceToken, Object> attributeGetterFunction =
				entry.getValue();

			sb.append("<column><column-name>");
			sb.append(attributeName);
			sb.append("</column-name><column-value><![CDATA[");
			sb.append(
				attributeGetterFunction.apply((KaleoTimerInstanceToken)this));
			sb.append("]]></column-value></column>");
		}

		sb.append("</model>");

		return sb.toString();
	}

	private static class EscapedModelProxyProviderFunctionHolder {

		private static final Function
			<InvocationHandler, KaleoTimerInstanceToken>
				_escapedModelProxyProviderFunction =
					_getProxyProviderFunction();

	}

	private long _mvccVersion;
	private long _kaleoTimerInstanceTokenId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private boolean _setModifiedDate;
	private String _kaleoClassName;
	private long _kaleoClassPK;
	private long _kaleoDefinitionId;
	private long _kaleoDefinitionVersionId;
	private long _kaleoInstanceId;
	private long _kaleoInstanceTokenId;
	private long _kaleoTaskInstanceTokenId;
	private long _kaleoTimerId;
	private String _kaleoTimerName;
	private boolean _blocking;
	private long _completionUserId;
	private boolean _completed;
	private Date _completionDate;
	private String _workflowContext;

	public <T> T getColumnValue(String columnName) {
		Function<KaleoTimerInstanceToken, Object> function =
			_attributeGetterFunctions.get(columnName);

		if (function == null) {
			throw new IllegalArgumentException(
				"No attribute getter function found for " + columnName);
		}

		return (T)function.apply((KaleoTimerInstanceToken)this);
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
		_columnOriginalValues.put(
			"kaleoTimerInstanceTokenId", _kaleoTimerInstanceTokenId);
		_columnOriginalValues.put("groupId", _groupId);
		_columnOriginalValues.put("companyId", _companyId);
		_columnOriginalValues.put("userId", _userId);
		_columnOriginalValues.put("userName", _userName);
		_columnOriginalValues.put("createDate", _createDate);
		_columnOriginalValues.put("modifiedDate", _modifiedDate);
		_columnOriginalValues.put("kaleoClassName", _kaleoClassName);
		_columnOriginalValues.put("kaleoClassPK", _kaleoClassPK);
		_columnOriginalValues.put("kaleoDefinitionId", _kaleoDefinitionId);
		_columnOriginalValues.put(
			"kaleoDefinitionVersionId", _kaleoDefinitionVersionId);
		_columnOriginalValues.put("kaleoInstanceId", _kaleoInstanceId);
		_columnOriginalValues.put(
			"kaleoInstanceTokenId", _kaleoInstanceTokenId);
		_columnOriginalValues.put(
			"kaleoTaskInstanceTokenId", _kaleoTaskInstanceTokenId);
		_columnOriginalValues.put("kaleoTimerId", _kaleoTimerId);
		_columnOriginalValues.put("kaleoTimerName", _kaleoTimerName);
		_columnOriginalValues.put("blocking", _blocking);
		_columnOriginalValues.put("completionUserId", _completionUserId);
		_columnOriginalValues.put("completed", _completed);
		_columnOriginalValues.put("completionDate", _completionDate);
		_columnOriginalValues.put("workflowContext", _workflowContext);
	}

	private transient Map<String, Object> _columnOriginalValues;

	public static long getColumnBitmask(String columnName) {
		return _columnBitmasks.get(columnName);
	}

	private static final Map<String, Long> _columnBitmasks;

	static {
		Map<String, Long> columnBitmasks = new HashMap<>();

		columnBitmasks.put("mvccVersion", 1L);

		columnBitmasks.put("kaleoTimerInstanceTokenId", 2L);

		columnBitmasks.put("groupId", 4L);

		columnBitmasks.put("companyId", 8L);

		columnBitmasks.put("userId", 16L);

		columnBitmasks.put("userName", 32L);

		columnBitmasks.put("createDate", 64L);

		columnBitmasks.put("modifiedDate", 128L);

		columnBitmasks.put("kaleoClassName", 256L);

		columnBitmasks.put("kaleoClassPK", 512L);

		columnBitmasks.put("kaleoDefinitionId", 1024L);

		columnBitmasks.put("kaleoDefinitionVersionId", 2048L);

		columnBitmasks.put("kaleoInstanceId", 4096L);

		columnBitmasks.put("kaleoInstanceTokenId", 8192L);

		columnBitmasks.put("kaleoTaskInstanceTokenId", 16384L);

		columnBitmasks.put("kaleoTimerId", 32768L);

		columnBitmasks.put("kaleoTimerName", 65536L);

		columnBitmasks.put("blocking", 131072L);

		columnBitmasks.put("completionUserId", 262144L);

		columnBitmasks.put("completed", 524288L);

		columnBitmasks.put("completionDate", 1048576L);

		columnBitmasks.put("workflowContext", 2097152L);

		_columnBitmasks = Collections.unmodifiableMap(columnBitmasks);
	}

	private long _columnBitmask;
	private KaleoTimerInstanceToken _escapedModel;

}